package parser.jsonoperations;

import ast.Operation;
import parser.JsonParser;

public class JsonDerivativeOperation extends JsonOperation {

    public JsonDerivativeOperation(String content, boolean delayed) {
        super(content, delayed);
    }

    @Override
    public Operation accept(JsonParser p) {
        return p.parse(this);
    }
}
