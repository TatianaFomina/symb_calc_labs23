package computator;

import ast.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import parser.JsonParser;
import parser.jsonoperations.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonComputator implements Computator {

    private String rulesFileName;
    private JSONArray rulesJsonArray = null;

    public JsonComputator(String rulesFileName) {
        this.rulesFileName = rulesFileName;
        JSONObject rulesJsonObj = getJSONObject(rulesFileName);
        rulesJsonArray = rulesJsonObj.getJSONArray("Differentiation");
    }

    private JSONObject getJSONObject(String filename){
        String fileString = null;
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filename));
            fileString = new String(encoded, Charset.defaultCharset());
            return new JSONObject(fileString);

        }catch (IOException e){
            //throw new ParserException("Unable to read file: " + filename);
        }
        return null;
    }


    private JSONObject getRule(String key, JSONArray rulesJsonArray){
        Iterator rulesIterator = rulesJsonArray.iterator();
        while (rulesIterator.hasNext()){
            JSONObject next = (JSONObject)rulesIterator.next();
            try{
                return (JSONObject)next.get(key);
            }catch(JSONException e){
                //return null;
                continue;
            }
        }
        return null;
    }


    @Override
    public Operation compute(Abs operation) {
        //rulesJsonArray
        JSONObject rule = getRule("abs", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Acos operation) {
        JSONObject rule = getRule("acos", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Addition operation) {
        JSONObject rule = getRule("Addition", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_left", operation.getLeft());
        context.put("this_right", operation.getRight());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Asin operation) {
        JSONObject rule = getRule("asin", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Atan operation) {
        JSONObject rule = getRule("atan", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Constant operation) {
        JSONObject rule = getRule("Number", rulesJsonArray);
        return (new JsonParser()).getOperation(rule);

    }

    @Override
    public Operation compute(Cos operation) {
        JSONObject rule = getRule("cos", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Division operation) {
        JSONObject rule = getRule("Division", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_left", operation.getLeft());
        context.put("this_right", operation.getRight());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Exp operation) {
        JSONObject rule = getRule("exp", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Log operation) {
        JSONObject rule = getRule("log", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }


    @Override
    public Operation compute(Negate operation) {
        JSONObject rule = getRule("Negate", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_expression", operation.getOp());
        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        Operation readyTree = rawTree.accept(new DelayedEnforcer(rulesFileName));
        return readyTree;

    }

    @Override
    public Operation compute(Pow operation) {
        JSONObject rule = getRule("Pow", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_left", operation.getLeft());
        context.put("this_right", operation.getRight());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Product operation) {
        JSONObject rule = getRule("Product", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_left", operation.getLeft());
        context.put("this_right", operation.getRight());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(SimpleVar operation) {
        JSONObject rule = getRule("Identifier", rulesJsonArray);
        return (new JsonParser()).getOperation(rule);
    }

    @Override
    public Operation compute(Sin operation) {
        JSONObject rule = getRule("sin", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Sqrt operation) {
        JSONObject rule = getRule("sqrt", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Substraction operation) {
        JSONObject rule = getRule("Subtraction", rulesJsonArray);
        Map<String, Operation> context = new HashMap<>();
        context.put("this_left", operation.getLeft());
        context.put("this_right", operation.getRight());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(Tan operation) {
        JSONObject rule = getRule("tan", rulesJsonArray);

        Map<String, Operation> context = new HashMap<>();
        context.put("this_arg", operation.getOp());

        Operation rawTree = (new JsonParser(context)).getOperation(rule);
        return rawTree.accept(new DelayedEnforcer(rulesFileName));
    }

    @Override
    public Operation compute(DelayedDifferentiation operation) {
        Operation expression = operation.getOp();
        return expression.accept(this);
    }
}
