package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Product extends BinaryOperation {

	public Product(Operation left, Operation right, boolean delayed) {
		super(left, right, delayed);
	}

	public Product(Operation left, Operation right) {
		super(left, right);
	}

	public Operation getLeft(){
		return left;
	}
	
	public Operation getRight(){
		return right;
	}

	public String toString(){
		String leftStr = left instanceof Constant || left instanceof SimpleVar ||
				left instanceof UnaryOperation ?
				left.toString() : "(" + left.toString() + ")";
		String rightStr = right instanceof Constant || right instanceof SimpleVar ||
				right instanceof UnaryOperation ?
				right.toString() : "(" + right.toString() + ")";
		return leftStr + "*" + rightStr;
	}

	@Override
	public Operation accept(Visitor v) {
		return v.visit(this);
	}

	@Override
	public Operation accept(Computator c) {
		return c.compute(this);
	}

	@Override
	public String acceptWriter(Writer w) {
		return w.visit(this);
	}

	@Override
	public Double getNumericResult(Double val) {
		return left.getNumericResult(val) * right.getNumericResult(val);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Product)) return false;
		Product abs = (Product) o;
		return (left.equals(abs.left) && right.equals(abs.right));
	}
	
	public int hashCode(){
		return 73 * (left.hashCode() + right.hashCode());
	}
}
