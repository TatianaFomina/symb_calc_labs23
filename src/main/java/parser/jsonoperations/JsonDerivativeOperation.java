package parser.jsonoperations;

import ast.Operation;
import parser.JsonParser;

public class JsonDerivativeOperation extends JsonOperation {

    public JsonDerivativeOperation(String content) {
        super(content);
    }

    @Override
    public Operation accept(JsonParser p) {
        return p.parse(this);
    }
}
