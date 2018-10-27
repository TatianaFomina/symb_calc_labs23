package visitor;

import ast.*;

public interface Visitor {

    Operation visit(Abs operation);
    Operation visit(Acos operation);
    Operation visit(Addition operation);
    Operation visit(Asin operation);
    Operation visit(Atan operation);
    Operation visit(Constant operation);
    Operation visit(Cos operation);
    Operation visit(Division operation);
    Operation visit(Exp operation);
    Operation visit(Log operation);
    Operation visit(Negate operation);
    Operation visit(Pow operation);
    Operation visit(Product operation);
    Operation visit(SimpleVar operation);
    Operation visit(Sin operation);
    Operation visit(Sqrt operation);
    Operation visit(Substraction operation);
    Operation visit(Tan operation);

    //Operation visit(Operation operation); //stub
}
