package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public class JsonIdentifier extends JsonOperation {
    public JsonIdentifier(String content, boolean delayed) {
        super(content, delayed);
    }

    public Operation accept(JsonParser p) {
        return p.parse(this);
    }
}
