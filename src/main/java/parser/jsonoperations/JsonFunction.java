package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public class JsonFunction extends JsonOperation {

    public JsonFunction(String content, boolean delayed) {
        super(content, delayed);
    }

    public Operation accept(JsonParser p) {
        return p.parse(this, delayed);
    }
}
