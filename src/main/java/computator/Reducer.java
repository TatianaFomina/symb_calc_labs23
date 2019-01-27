package computator;

import ast.*;

public class Reducer implements Computator {

    @Override
    public Operation compute(Abs operation) {
        Operation operand = operation.getOp();
        if (operand instanceof Constant){
            Double numValue = operand.getNumericResult(0.0);
            if (numValue >= 0 )
                return operand;
            else return new Constant(String.valueOf(-numValue));
        }else if (operand instanceof Negate){
            Negate expr = (Negate)operand;
            return expr.getOp().accept(this);
        }
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Acos operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Addition operation) {
        Operation left = operation.getLeft(),
                right = operation.getRight();
        if (left instanceof Constant && left.equals(new Constant("0")) )
            return right.accept(this);
        else if (right instanceof Constant && right.equals(new Constant("0")))
            return left.accept(this);
        else if (right instanceof Constant && left instanceof Constant)
            return new Constant(String.valueOf(operation.getNumericResult(0.0)));
        else return new Addition(left.accept(this), right.accept(this));
    }

    @Override
    public Operation compute(Asin operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Atan operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Constant operation) {
        return operation;
    }

    @Override
    public Operation compute(Cos operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Division operation) {
        Operation left = operation.getLeft(),
                right = operation.getRight();
        if (left.equals(new Constant("0")))
            return new Constant("0");
        else if (right.equals(new Constant("0")))
            return new Constant("Infinity");
        else if (right.equals(new Constant("1")))
            return left.accept(this);
        else if (right instanceof Constant && left instanceof Constant)
            return new Constant(String.valueOf(operation.getNumericResult(0.0)));
        else return new Division(left.accept(this), right.accept(this));
    }

    @Override
    public Operation compute(Exp operation) {
        if (operation.getOp().equals(new Constant("0")))
            return new Constant("1");
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Log operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Negate operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Pow operation) {
        Operation left = operation.getLeft(),
                right = operation.getRight();

        if (left.equals(new Constant("0")))
            return new Constant("0");
        else if (right.equals(new Constant("0")))
            return new Constant("1");
        else if (right.equals(new Constant("1")))
            return left.accept(this);
        return new Pow(left.accept(this), right.accept(this));
    }

    @Override
    public Operation compute(Product operation) {
        Operation left = operation.getLeft(),
                right = operation.getRight();
        if (left.equals(new Constant("0")) || right.equals(new Constant("0")))
            return new Constant("0");
        else if (right.equals(new Constant("1")))
            return left.accept(this);
        else if (left.equals(new Constant("1")))
            return right.accept(this);
        else if (right instanceof Constant && left instanceof Constant)
            return new Constant(String.valueOf(operation.getNumericResult(0.0)));
        else return new Product(left.accept(this), right.accept(this));
    }

    @Override
    public Operation compute(SimpleVar operation) {
        return operation;
    }

    @Override
    public Operation compute(Sin operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Sqrt operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(Substraction operation) {
        Operation left = operation.getLeft(),
                right = operation.getRight();
        if (left.equals(new Constant("0")))
            return new Negate(right.accept(this));
        else if (right.equals(new Constant("0")))
            return left.accept(this);
        else if (right instanceof Constant && left instanceof Constant)
            return new Constant(String.valueOf(operation.getNumericResult(0.0)));
        else return new Substraction(left.accept(this), right.accept(this));
    }

    @Override
    public Operation compute(Tan operation) {
        return operation.getOp().accept(this);
    }

    @Override
    public Operation compute(DelayedDifferentiation operation) {
        return null;
    }
}
