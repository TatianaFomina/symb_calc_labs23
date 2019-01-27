package parser;

import ast.Operation;
import parser.jsonoperations.*;

public interface Parser {
    Operation parse(JsonBinaryOperation operation, boolean delayed);
    Operation parse(JsonUnaryOperation operation, boolean delayed);
    Operation parse(JsonFunction operation, boolean delayed);
    Operation parse(JsonIdentifier operation);
    Operation parse(JsonConstant operation);
}
