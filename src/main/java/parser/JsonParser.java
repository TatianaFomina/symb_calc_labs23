package parser;

import ast.*;
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

public class JsonParser implements Parser {

    private Map<String, Operation> context = new HashMap<>();

    public JsonParser(Map<String, Operation> context){
        this.context = context;
    }

    public JsonParser(){}

    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    private JSONObject getJSONObject(String filename) throws ParserException {
        String fileString = null;
        try {
            fileString = readFile(filename, Charset.defaultCharset());
            JSONObject rawSchema = new JSONObject(readFile("schema.json", Charset.defaultCharset()));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(fileString)); // throws a ValidationException if this object is invalid

        }catch (ValidationException e){
            throw new ParserException("File " + filename + " contains invalid JSON format");
        }catch (IOException e){
            throw new ParserException("Unable to read file: " + filename);
        }
        return new JSONObject(fileString);
    }

    public Operation getAbstractSyntaxTree(String filename) throws ParserException {
        JSONObject documentJsonObj = getJSONObject(filename);
        return getOperation((JSONObject)documentJsonObj.get("Expression"));
    }

    private BinaryOperation getOperation(String operatorSymbol, Operation left, Operation right) {
        switch (operatorSymbol)
        {
            case "+":	return new Addition(left, right);
            case "-":	return new Substraction(left, right);
            case "*":	return new Product(left, right);
            case "/":	return new Division(left, right);
            case "^":	return new Pow(left, right);
            default:    return null;
        }
    }

    public Operation getOperation(JSONObject jsonObject){
        Iterator<String> keys = jsonObject.keys();
        String key = keys.next();
        //JSONObject jsonContent = (JSONObject)jsonObject.get(key);
       // if (jsonContent.get("Differentiation"))
        String content = jsonObject.get(key).toString(); //if contains this -> save to context
        switch(key){
            case "Binary":          return (new JsonBinaryOperation(content)).accept(this);
            case "FunctionCall":    return (new JsonFunction(content)).accept(this);
            case "Unary":           return (new JsonUnaryOperation(content)).accept(this);
            case "Identifier":      return (new JsonIdentifier(content)).accept(this);
            case "Number":          return (new JsonConstant(content)).accept(this);
            case "Differentiation": return (new JsonDerivativeOperation(content).accept(this));
            default:                return null;
        }
    }

    private Operation getFunction(String functionName, Operation arg){
        switch (functionName)
        {
            case "acos":    return new Acos(arg);
            case "asin":    return new Asin(arg);
            case "atan":    return new Atan(arg);
            case "log":     return new Log(arg);
            case "cos":     return new Cos(arg);
            case "sin":     return new Sin(arg);
            case "sqrt":    return new Sqrt(arg);
            case "tan":     return new Tan(arg);
            case "exp":     return new Exp(arg);
            case "abs":     return new Abs(arg);
            default:        return null;
        }
    }

    @Override
    public Operation parse(JsonBinaryOperation operation) {
        JSONObject operationJSON = new JSONObject(operation.getContent());
        String operatorSymbol = (String)operationJSON.get("operator");
        Operation left, right;
        try{
            String key = operationJSON.getString("left"); //this_left
            left = context.get(key);
        }catch(JSONException e){
            left = getOperation((JSONObject)operationJSON.get("left"));
        }

        try{
            String key = operationJSON.getString("right");  //this_right
            right = context.get(key);
        }catch(JSONException e){
            right = getOperation((JSONObject)operationJSON.get("right"));
        }
         //left = getOperation((JSONObject)operationJSON.get("left"));
        //Operation right = getOperation((JSONObject)operationJSON.get("right"));
        return getOperation(operatorSymbol, left, right);
    }

    @Override
    public Operation parse(JsonUnaryOperation operation) {
        JSONObject unaryJSON = new JSONObject(operation.getContent());
        Operation negOperand = getOperation((JSONObject)unaryJSON.get("expression"));
        return new Negate(negOperand);
    }

    @Override
    public Operation parse(JsonFunction operation) {
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
            operand = context.get(key);
        }catch(JSONException e){
            operand = getOperation((JSONObject)index.get("0"));
        }
        /*Operation */
        return getFunction(functionName, operand);
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
        //JSONObject content = context.get(key);
        return new DelayedDifferentiation(context.get(key));
    }
}
