package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Addition extends BinaryOperation {

	public Addition(Operation left, Operation right, boolean delayed) {
		super(left, right, delayed);
	}

	public Addition(Operation left, Operation right) {
		super(left, right, false);
	}

	public Operation getLeft(){
		return left;
	}
	
	public Operation getRight(){
		return right;
	}
	
	@Override
	public String toString(){
		return left.toString() + "+" + right.toString();
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

	public Double getNumericResult(Double val)
	{
		return left.getNumericResult(val) + right.getNumericResult(val);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Addition)) return false;
		Addition abs = (Addition) o;
		return (left.equals(abs.left) && right.equals(abs.right));
	}
	
	public int hashCode(){
		return 61 * (left.hashCode() + right.hashCode());
	}
}
