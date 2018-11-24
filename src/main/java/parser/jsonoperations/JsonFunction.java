package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public class JsonFunction extends JsonOperation {

    public JsonFunction(String content) {
        super(content);
    }

    public Operation accept(JsonParser p) {
        return p.parse(this);
    }
}
