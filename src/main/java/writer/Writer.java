package writer;

import ast.*;

public interface Writer {
    String visit(Abs operation);
    String visit(Acos operation);
    String visit(Addition operation);
    String visit(Asin operation);
    String visit(Atan operation);
    String visit(Constant operation);
    String visit(Cos operation);
    String visit(Division operation);
    String visit(Exp operation);
    String visit(Log operation);
    String visit(Negate operation);
    String visit(Pow operation);
    String visit(Product operation);
    String visit(SimpleVar operation);
    String visit(Sin operation);
    String visit(Sqrt operation);
    String visit(Substraction operation);
    String visit(Tan operation);
}
