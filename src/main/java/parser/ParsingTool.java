package parser;

import ast.*;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class ParsingTool {

    private String fileString;

    public ParsingTool()throws IOException, ParserException {


    }

    public Operation parse(String filename) throws ParserException {
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

        return getTree();
    }


    private String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


    private BinaryOperation getOperation(String operatorSymbol, Operation left, Operation right) throws ParserException {
        switch (operatorSymbol)
        {
            case "+":	return new Addition(left, right);
            case "-":	return new Substraction(left, right);
            case "*":	return new Product(left, right);
            case "/":	return new Division(left, right);
            case "^":	return new Pow(left, right);
            default: throw new ParserException("Binary Operation error");
        }
    }

    private UnaryOperation getFunction(String functionName, Operation arg) throws ParserException {
        switch (functionName)
        {
            case "acos": return new Acos(arg);
            case "asin": return new Asin(arg);
            case "atan": return new Atan(arg);
            case "log": return new Log(arg);
            case "cos": return new Cos(arg);
            case "sin": return new Sin(arg);
            case "sqrt": return new Sqrt(arg);
            case "tan": return new Tan(arg);
            case "exp": return new Exp(arg);
            case "abs": return new Abs(arg);
            default: throw new ParserException("Function error");
        }
    }

    private Operation getTree() throws ParserException {
        JSONObject obj = new JSONObject(fileString);
        return getTree((JSONObject)obj);
    }

    private Operation getTree(JSONObject jsonObject) throws ParserException {  //fix it
        Operation returnTree = null;
        Iterator<String> keys = jsonObject.keys();
        String key = keys.next();
        switch(key){
            case "Expression":
                returnTree = getTree((JSONObject)jsonObject.get(key));
                break;
            case "Binary":
                JSONObject operationJSON = (JSONObject)jsonObject.get(key);
                String operatorSymbol = (String)operationJSON.get("operator");
                Operation left = getTree((JSONObject)operationJSON.get("left"));
                Operation right = getTree((JSONObject)operationJSON.get("right"));
                BinaryOperation binaryOperation = getOperation(operatorSymbol, left, right);
                returnTree = binaryOperation;
                break;
            case "FunctionCall":
                JSONObject functionJSON = (JSONObject)jsonObject.get(key);
                String functionName = (String)functionJSON.get("name");
                JSONArray operandsArray = functionJSON.getJSONArray("args");
                Iterator operandsIterator = operandsArray.iterator();
                JSONObject index = null;
                if (operandsIterator.hasNext())
                    index = (JSONObject)operandsIterator.next();
                else throw new ParserException("Invalid function call");
                JSONObject operand = (JSONObject)index.get("0");
                UnaryOperation function = getFunction(functionName, getTree(operand));

                returnTree = function;
                break;
            case "Unary":
                JSONObject unaryJSON = (JSONObject)jsonObject.get(key);
                JSONObject negOperand = (JSONObject)unaryJSON.get("expression");
                Negate negate = new Negate(getTree(negOperand));
                returnTree = negate;
                break;
            case "Identifier":
                returnTree = new SimpleVar(); //add support of different variables
                break;
            case "Number":
                String constant = (String)jsonObject.get(key);
                returnTree = new Constant(constant);
                break;
            default:
                throw new ParserException("Unable to parse");

        }

        return returnTree;
    }


}
