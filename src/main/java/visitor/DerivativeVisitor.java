package visitor;

import ast.*;

public class DerivativeVisitor implements Visitor {

    @Override
    public Operation visit(Abs operation) {
        Operation operand = operation.getOp();
        return new Division(new Product(new Abs(operand, false), operand.accept(this)), operand);
    }

    @Override
    public Operation visit(Acos operation) {
        Operation operand = operation.getOp();
        return new Negate(new Division(operand.accept(this), new Sqrt(new Substraction(new Constant("1"), new Pow(operand, new Constant("2"))))));
    }

    @Override
    public Operation visit(Addition operation) {
        Operation left = operation.getLeft(),
                  right = operation.getRight();
        return new Addition(left.accept(this), right.accept(this));
    }

    @Override
    public Operation visit(Asin operation) {
        Operation operand = operation.getOp();
        return new Division(operand.accept(this), new Sqrt(new Substraction(new Constant("1"), new Pow(operand, new Constant("2")))));
    }

    @Override
    public Operation visit(Atan operation) {
        Operation operand = operation.getOp();
        return new Division(operand.accept(this), new Addition(new Constant("1"), new Pow(new Cos(operand), new Constant("2"))));
    }

    @Override
    public Operation visit(Constant operation) {
        return new Constant("0");
    }

    @Override
    public Operation visit(Cos operation) {
        Operation operand = operation.getOp();
        return new Negate(new Product(new Sin(operand), operand.accept(this)));
    }

    @Override
    public Operation visit(Division operation) {
        Operation left = operation.getLeft(),
                right = operation.getRight();
        Operation numerator = new Substraction(new Product(left.accept(this), right), new Product(left, right.accept(this)));
        Operation denominator = new Pow(operation.getRight(), new Constant("2"));
        return new Division(numerator, denominator);
    }

    @Override
    public Operation visit(Exp operation) {
        Operation operand = operation.getOp();
        return new Product(new Exp(operand), operand.accept(this));
    }

    @Override
    public Operation visit(Log operation) {
        Operation operand = operation.getOp();
        return new Division(operand.accept(this), operand);
    }

    @Override
    public Operation visit(Negate operation) {
        Operation operand = operation.getOp();
        return new Negate(operand.accept(this));
    }

    @Override
    public Operation visit(Pow operation) {
        Operation left = operation.getLeft(),
                  right = operation.getRight();
        Operation firstTerm = new Pow(left, right);
        Operation secondTerm = new Addition(new Product(right.accept(this), new Log(left)), new Division(new Product(left.accept(this), right) , left));
        return new Product(firstTerm, secondTerm);
    }

    @Override
    public Operation visit(Product operation) {
        Operation left = operation.getLeft(),
                  right = operation.getRight();
        return new Addition(new Product(left.accept(this), right), new Product(left, right.accept(this)));
       // return new Addition(new Product(visit(operation.getLeft()), operation.getRight()), new Product(operation.getLeft(), visit(operation.getRight())));
    }

    @Override
    public Operation visit(SimpleVar operation) {
        return new Constant("1");
    }

    @Override
    public Operation visit(Sin operation) {
        Operation operand = operation.getOp();
        return new Product(new Cos(operand),/* op.getDerivative()*/ operand.accept(this));
    }

    @Override
    public Operation visit(Sqrt operation) {
        Operation operand = operation.getOp();
        return new Division(operand.accept(this), new Product(new Constant("2"), new Sqrt(operand)));
    }

    @Override
    public Operation visit(Substraction operation) {
        Operation left = operation.getLeft(),
                  right = operation.getRight();
        return new Substraction(left.accept(this), right.accept(this));
    }

    @Override
    public Operation visit(Tan operation) {
        Operation operand = operation.getOp();
        return new Division(operand.accept(this), new Pow(new Cos(operand),new Constant("2")));
    }

//    @Override
//    public Operation visit(Operation operation) {
//        //return visit(operation);
//        return null;
//    }
}
