package computator;


import ast.*;

public interface Computator {

    Operation compute(Abs operation);
    Operation compute(Acos operation);
    Operation compute(Addition operation);
    Operation compute(Asin operation);
    Operation compute(Atan operation);
    Operation compute(Constant operation);
    Operation compute(Cos operation);
    Operation compute(Division operation);
    Operation compute(Exp operation);
    Operation compute(Log operation);
    Operation compute(Negate operation);
    Operation compute(Pow operation);
    Operation compute(Product operation);
    Operation compute(SimpleVar operation);
    Operation compute(Sin operation);
    Operation compute(Sqrt operation);
    Operation compute(Substraction operation);
    Operation compute(Tan operation);

    Operation compute(DelayedDifferentiation operation);
}
