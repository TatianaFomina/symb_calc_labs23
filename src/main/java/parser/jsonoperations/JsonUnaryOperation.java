package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public class JsonUnaryOperation extends JsonOperation {
    public JsonUnaryOperation(String content) {
        super(content);
    }

    public Operation accept(JsonParser p) {
        return p.parse(this);
    }
}
