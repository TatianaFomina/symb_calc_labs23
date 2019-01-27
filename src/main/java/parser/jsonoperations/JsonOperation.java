package parser.jsonoperations;

import ast.Operation;
import org.json.JSONObject;
import parser.JsonParser;

public abstract class JsonOperation {
    private String content;
    protected boolean delayed;

    public JsonOperation(String content, boolean delayed) {
        this.content = content;
        this.delayed = delayed;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public String getContent() {
        return content;
    }

    public abstract Operation accept(JsonParser p);
}
