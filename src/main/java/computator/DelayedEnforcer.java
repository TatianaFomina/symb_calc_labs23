package computator;

import ast.*;

public class DelayedEnforcer implements Computator {

    private String rulesFile;

    public DelayedEnforcer(String rulesFile) {
        this.rulesFile = rulesFile;
    }

    @Override
    public Operation compute(Abs operation) {
        //operation.getOp().accept(this);
        return new Abs(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Acos operation) {
        return new Acos(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Addition operation) {
        return new Addition(operation.getLeft().accept(this), operation.getRight().accept(this));
    }

    @Override
    public Operation compute(Asin operation) {
        return new Asin(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Atan operation) {
        return new Atan(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Constant operation) {
        return operation;
    }

    @Override
    public Operation compute(Cos operation) {
        return new Cos(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Division operation) {
        return new Division(operation.getLeft().accept(this), operation.getRight().accept(this));
    }

    @Override
    public Operation compute(Exp operation) {
        return new Exp(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Log operation) {
        return new Log(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Negate operation) {
        return new Negate(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Pow operation) {
        return new Pow(operation.getLeft().accept(this), operation.getRight().accept(this));
    }

    @Override
    public Operation compute(Product operation) {
        return new Product(operation.getLeft().accept(this), operation.getRight().accept(this));
    }

    @Override
    public Operation compute(SimpleVar operation) {
        return operation;
    }

    @Override
    public Operation compute(Sin operation) {
        return new Sin(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Sqrt operation) {
        return new Sqrt(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(Substraction operation) {
        return new Substraction(operation.getLeft().accept(this), operation.getRight().accept(this));
    }

    @Override
    public Operation compute(Tan operation) {
        return new Tan(operation.getOp().accept(this));
    }

    @Override
    public Operation compute(DelayedDifferentiation operation) {
        //return operation.getOp().accept(new JsonComputator(rulesFile)).accept(this);
        Operation computed = operation.getOp().accept(new JsonComputator(rulesFile)).accept(this);
        return computed.accept(this);
    }
}
