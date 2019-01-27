package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public class JsonBinaryOperation extends JsonOperation{

    public JsonBinaryOperation(String content, boolean delayed) {
        super(content, delayed);

    }
    public Operation accept(JsonParser p) {
        return p.parse(this, delayed);
    }
}
