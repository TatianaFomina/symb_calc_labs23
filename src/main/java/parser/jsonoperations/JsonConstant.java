package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public class JsonConstant extends JsonOperation {
    public JsonConstant(String content, boolean delayed) {
        super(content, delayed);
    }
    public Operation accept(JsonParser p) {
        return p.parse(this);
    }
}
