package writer;

import ast.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WritingTool {

    private Operation expressionTree;

    public WritingTool(Operation expressionTree) {
        this.expressionTree = expressionTree;
    }

    public void writeTo(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(".EQ\n");

        //write tree here
        writer.write(writeEqn(expressionTree) + "\n");
        writer.write(".EN");
        writer.close();
    }

    private String writeEqn(Operation operation){
        if (operation instanceof BinaryOperation){
            if (operation instanceof Addition ){
                Addition addition = (Addition)operation;
                Operation left = addition.getLeft();
                Operation right = addition.getRight();
                String leftStr = writeEqn(left);
                String rightStr = writeEqn(right);
//                boolean leftEquals0 = left.equals(new Constant("0")),
//                        rightEquals0 = right.equals(new Constant("0"));
                boolean leftEquals0 = leftStr.equals("0"),
                        rightEquals0 = rightStr.equals("0");
                if (!leftEquals0 && !rightEquals0)
//                    return writeEqn(left) + " + " + writeEqn(right);
                    return leftStr + " + " + rightStr;
                else
                    if (leftEquals0 && !rightEquals0)
//                        return writeEqn(right);
                        return rightStr;
                else
//                    return writeEqn(left);
                     return leftStr;

            } else
                if (operation instanceof Substraction ){
                    Substraction addition = (Substraction)operation;
                    Operation left = addition.getLeft();
                    Operation right = addition.getRight();
                    boolean leftEquals0 = left.equals(new Constant("0")),
                            rightEquals0 = right.equals(new Constant("0"));
                    if (!leftEquals0 && !rightEquals0)
                        return writeEqn(left) + " - " + writeEqn(right);
                    else
                    if (leftEquals0 && !rightEquals0)
                        return writeEqn(right);
                    else return writeEqn(left);
                }
                else
                    if (operation instanceof Product ){
                        Product product = (Product)operation;
                        Operation left = product.getLeft();
                        Operation right = product.getRight();
                        boolean leftEquals0 = left.equals(new Constant("0")),
                                rightEquals0 = right.equals(new Constant("0"));
                        boolean leftEquals1 = left.equals(new Constant("1")),
                                rightEquals1 = right.equals(new Constant("1"));
                        if (!leftEquals0 && !rightEquals0)
                            if (rightEquals1)
                                return writeEqn(left);
                            else if (leftEquals1)
                                return writeEqn(right);
                            else
                                return writeEqn(left) + "" + writeEqn(right);
                        else return "0";
                    }
                    else if (operation instanceof Division ){
                        Division division = (Division)operation;
                        Operation left = division.getLeft();
                        Operation right = division.getRight();
                        boolean leftEquals0 = left.equals(new Constant("0"));
                        if (leftEquals0)
                            return "0";
                        else return writeEqn(left) + " over " + writeEqn(right);
                    }
                    else if (operation instanceof Pow ){
                        Pow pow = (Pow)operation;
                        Operation left = pow.getLeft();
                        Operation right = pow.getRight();
                        boolean leftEquals0 = left.equals(new Constant("0")),
                                rightEquals0 = right.equals(new Constant("0"));
                        if (leftEquals0)
                            return "0";
                        else if (rightEquals0)
                            return "1";
                        else return writeEqn(left) + " sup " + writeEqn(right);
                    }

        }else if(operation instanceof UnaryOperation){
            UnaryOperation unaryOperation = (UnaryOperation)operation;
            String operationStr = operation.toString();
            String operationName = operationStr.substring(0, operationStr.indexOf('('));
            //return operation.toString();// + writeEqn(unaryOperation.getOp());
            return operationName +"{" + writeEqn(unaryOperation.getOp()) + "}";
        }else return operation.toString();

        return null;
    }
}
