package parser;

import ast.*;
import computator.Reducer;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import parser.jsonoperations.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class JsonParser implements Parser {

    private Map<String, Operation> scope = new HashMap<>();
    private Map<String, Operation> context = new HashMap<>();

    public JsonParser(Map<String, Operation> scope){
        this.scope = scope;
    }

    public JsonParser(){}

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private Map<String, JSONObject> getJSONObjects(String filename) throws ParserException {
        String fileString = null;
        Map<String, JSONObject> scope = null;
        try {
            scope = new HashMap<>();
            fileString = readFile(filename, Charset.defaultCharset());
            JSONObject rawSchema = new JSONObject(readFile("schema.json", Charset.defaultCharset()));
            Schema schema = SchemaLoader.load(rawSchema);

            JSONObject documentObject = new JSONObject(fileString);
            JSONArray exprArray = documentObject.getJSONArray("Input");

            Iterator exprIterator = exprArray.iterator();
            while (exprIterator.hasNext()) {
                JSONObject next = (JSONObject) exprIterator.next();
                JSONObject expression = new JSONObject();
                expression.put("Expression", next.getJSONObject("Expression"));
                schema.validate(expression);
                expression.put("Delayed", next.getBoolean("Delayed"));
                scope.put(next.getString("Name"), expression);
            }

        }catch (ValidationException e){
            throw new ParserException("File " + filename + " contains invalid JSON format");
        }catch (IOException e){
            throw new ParserException("Unable to read file: " + filename);
        }
        return scope;
    }

//    public Operation getAbstractSyntaxTree(String filename) throws ParserException {
//        JSONObject documentJsonObj = getJSONObject(filename);
//        return getOperation((JSONObject)documentJsonObj.get("Expression"));
//    }

    public Map<String,Operation> getASTs(String filename) throws ParserException {
//        Map<String, Operation> context = new HashMap<>();
        Map<String, JSONObject> jsonObjList = getJSONObjects(filename);

        Reducer reducer = new Reducer();
        Iterator<String> jsonObjIterator = jsonObjList.keySet().iterator();
        while (jsonObjIterator.hasNext()) {
            String name = jsonObjIterator.next();
            JSONObject obj = jsonObjList.get(name);
            boolean delayed = obj.getBoolean("Delayed");
            Operation expression = getOperation(obj.getJSONObject("Expression"), delayed);
            if (!delayed)
                expression.accept(reducer);
            //reduce expression
            context.put(name, expression);
        }

        return context;
    }

    private BinaryOperation getOperation(String operatorSymbol, Operation left, Operation right, boolean delayed) {
        switch (operatorSymbol)
        {
            case "+":	return new Addition(left, right, delayed);
            case "-":	return new Substraction(left, right, delayed);
            case "*":	return new Product(left, right, delayed);
            case "/":	return new Division(left, right, delayed);
            case "^":	return new Pow(left, right, delayed);
            default:    return null;
        }
    }

    public Operation getOperation(JSONObject jsonObject, boolean delayed){
        Iterator<String> keys = jsonObject.keys();
        String key = keys.next();
        //JSONObject jsonContent = (JSONObject)jsonObject.get(key);
       // if (jsonContent.get("Differentiation"))
        String content = jsonObject.get(key).toString(); //if contains this -> save to scope
        switch(key){
            case "Binary":          return (new JsonBinaryOperation(content, delayed)).accept(this);
            case "FunctionCall":    return (new JsonFunction(content, delayed)).accept(this);
            case "Unary":           return (new JsonUnaryOperation(content, delayed)).accept(this);
            case "Identifier":      return (new JsonIdentifier(content, delayed)).accept(this);
            case "Number":          return (new JsonConstant(content, delayed)).accept(this);
            case "Predefined":      Operation predefinedOp = context.get(jsonObject.getString(key));
                                    if (predefinedOp.isDelayed())
                                        predefinedOp = predefinedOp.accept(new Reducer());
                                    return predefinedOp;

            case "Differentiation": return (new JsonDerivativeOperation(content, delayed).accept(this));
            default:                return null;
        }
    }

    private Operation getFunction(String functionName, Operation arg, boolean delayed){
        switch (functionName)
        {
            case "acos":    return new Acos(arg, delayed);
            case "asin":    return new Asin(arg, delayed);
            case "atan":    return new Atan(arg, delayed);
            case "log":     return new Log(arg, delayed);
            case "cos":     return new Cos(arg, delayed);
            case "sin":     return new Sin(arg, delayed);
            case "sqrt":    return new Sqrt(arg, delayed);
            case "tan":     return new Tan(arg, delayed);
            case "exp":     return new Exp(arg, delayed);
            case "abs":     return new Abs(arg, delayed);
            default:        return null;
        }
    }

    @Override
    public Operation parse(JsonBinaryOperation operation, boolean delayed) {
        JSONObject operationJSON = new JSONObject(operation.getContent());
        String operatorSymbol = (String)operationJSON.get("operator");
        Operation left, right;
        try{
            String key = operationJSON.getString("left"); //this_left
            left = scope.get(key);
        }catch(JSONException e){
            left = getOperation((JSONObject)operationJSON.get("left"), false);
        }

        try{
            String key = operationJSON.getString("right");  //this_right
            right = scope.get(key);
        }catch(JSONException e){
            right = getOperation((JSONObject)operationJSON.get("right"), false);
        }
        return getOperation(operatorSymbol, left, right, delayed);
    }

    @Override
    public Operation parse(JsonUnaryOperation operation, boolean delayed) {
        JSONObject unaryJSON = new JSONObject(operation.getContent());
        Operation negOperand = getOperation((JSONObject)unaryJSON.get("expression"), false);
        return new Negate(negOperand, delayed);
    }

    @Override
    public Operation parse(JsonFunction operation, boolean delayed) {
        JSONObject functionJSON = new JSONObject(operation.getContent());
        String functionName = (String)functionJSON.get("name");
        JSONArray operandsArray = functionJSON.getJSONArray("args");
        Iterator operandsIterator = operandsArray.iterator();
        JSONObject index = null;
        if (operandsIterator.hasNext())
            index = (JSONObject)operandsIterator.next();
        else return null;
        Operation operand;
        try{
            String key = index.getString("0");
            operand = scope.get(key);
        }catch(JSONException e){
            operand = getOperation((JSONObject)index.get("0"), false);
        }
        /*Operation */
        return getFunction(functionName, operand, delayed);
    }

    @Override
    public Operation parse(JsonIdentifier operation) {
        return new SimpleVar();
    }

    @Override
    public Operation parse(JsonConstant operation) {
        String constant = operation.getContent();
        return new Constant(constant);
    }

    public Operation parse(JsonDerivativeOperation operation) {
        JSONObject expressionJSON = new JSONObject(operation.getContent());
        String key = expressionJSON.getString("key");
        //JSONObject content = scope.get(key);
        return new DelayedDifferentiation(scope.get(key), false);
    }


}
