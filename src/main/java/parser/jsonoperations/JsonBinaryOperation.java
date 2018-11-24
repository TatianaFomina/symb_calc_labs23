package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public class JsonBinaryOperation extends JsonOperation{

    public JsonBinaryOperation(String content) {
        super(content);

    }
    public Operation accept(JsonParser p) {
        return p.parse(this);
    }
}
