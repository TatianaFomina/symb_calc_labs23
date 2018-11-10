package writer;

import ast.*;

public class EqnWriter implements Writer {
    @Override
    public String visit(Abs operation) {
        return "|" + operation.getOp().acceptWriter(this) + "|";
    }

    @Override
    public String visit(Acos operation) {
        return "acos(" + operation.getOp().acceptWriter(this) + ")";
    }

    @Override
    public String visit(Addition operation) {
        Operation left = operation.getLeft();
        Operation right = operation.getRight();
        String leftStr = left.acceptWriter(this);
        String rightStr = right.acceptWriter(this);
        boolean leftEquals0 = leftStr.equals("0"),
                rightEquals0 = rightStr.equals("0");
        if (!leftEquals0 && !rightEquals0)
            return leftStr + " + " + rightStr;
        else
        if (leftEquals0 && !rightEquals0)
            return rightStr;
        else
            return leftStr;
    }

    @Override
    public String visit(Asin operation) {
        return "asin(" + operation.getOp().acceptWriter(this) + ")";
    }

    @Override
    public String visit(Atan operation) {
        return "atan(" + operation.getOp().acceptWriter(this) + ")";
    }

    @Override
    public String visit(Constant operation) {
        return operation.toString();
    }

    @Override
    public String visit(Cos operation) {
        return "cos(" + operation.getOp().acceptWriter(this) + ")";
    }

    @Override
    public String visit(Division operation) {
        Operation left = operation.getLeft();
        Operation right = operation.getRight();
        boolean leftEquals0 = left.equals(new Constant("0"));
        if (leftEquals0)
            return "0";
        else return left.acceptWriter(this) + " over {" + right.acceptWriter(this) + "}";
    }

    @Override
    public String visit(Exp operation) {
        return "exp(" + operation.getOp().acceptWriter(this) + ")";
    }

    @Override
    public String visit(Log operation) {
        return "log(" + operation.getOp().acceptWriter(this) + ")";
    }

    @Override
    public String visit(Negate operation) {
        Operation operand = operation.getOp();
        if (operand instanceof UnaryOperation || operand instanceof SimpleVar
                || operand instanceof Constant || operand instanceof Product)
            return "-" + operand.acceptWriter(this);
        return "-(" + operand.acceptWriter(this) + ")";
    }

    @Override
    public String visit(Pow operation) {
        Operation left = operation.getLeft();
        Operation right = operation.getRight();
        boolean leftEquals0 = left.equals(new Constant("0")),
                rightEquals0 = right.equals(new Constant("0"));
        if (leftEquals0)
            return "0";
        else if (rightEquals0)
            return "1";
        else return left.acceptWriter(this) + " sup " + right.acceptWriter(this);
    }

    @Override
    public String visit(Product operation) {
        Operation left = operation.getLeft();
        Operation right = operation.getRight();
        String leftStr = left.acceptWriter(this),
                rightStr = right.acceptWriter(this);

        boolean leftEquals0 = leftStr.equals("0"),
                rightEquals0 = rightStr.equals("0");
        boolean leftEquals1 = leftStr.equals("1"),
                rightEquals1 = rightStr.equals("1");

        if (!leftEquals0 && !rightEquals0)
            if (rightEquals1)
                return left.acceptWriter(this);
            else if (leftEquals1)
                return right.acceptWriter(this);
            else{

                if (right instanceof Constant || rightStr.matches("-?\\d+(\\.\\d+)?"))
                    return rightStr + " " + leftStr;
                return leftStr + " " + rightStr;
            }

        else return "0";
    }

    @Override
    public String visit(SimpleVar operation) {
        return operation.toString();
    }

    @Override
    public String visit(Sin operation) {
        return "sin(" + operation.getOp().acceptWriter(this) + ")";
    }

    @Override
    public String visit(Sqrt operation) {
        return "sqrt {" + operation.getOp().acceptWriter(this) + "}";
    }

    @Override
    public String visit(Substraction operation) {
        Operation left = operation.getLeft();
        Operation right = operation.getRight();
        boolean leftEquals0 = left.equals(new Constant("0")),
                rightEquals0 = right.equals(new Constant("0"));
        if (!leftEquals0 && !rightEquals0)
            return left.acceptWriter(this) + " - " + right.acceptWriter(this);
        else
        if (leftEquals0 && !rightEquals0)
            return right.acceptWriter(this);
        else return left.acceptWriter(this);
    }

    @Override
    public String visit(Tan operation) {
        return "tan(" + operation.getOp().acceptWriter(this) + ")";
    }
}
