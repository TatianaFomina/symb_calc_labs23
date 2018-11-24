package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public abstract class JsonOperation {
    private String content;

    public JsonOperation(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public abstract Operation accept(JsonParser p);
}
