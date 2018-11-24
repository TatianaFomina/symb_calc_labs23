package parser;

import ast.Operation;
import parser.jsonoperations.*;

public interface Parser {
    Operation parse(JsonBinaryOperation operation);
    Operation parse(JsonUnaryOperation operation);
    Operation parse(JsonFunction operation);
    Operation parse(JsonIdentifier operation);
    Operation parse(JsonConstant operation);
}
