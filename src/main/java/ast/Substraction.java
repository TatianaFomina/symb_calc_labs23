package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Substraction extends BinaryOperation {

	public Substraction(Operation left, Operation right, boolean delayed) {
		super(left, right, delayed);
	}

	public Substraction(Operation left, Operation right) {
		super(left, right);
	}

	public Operation getLeft(){
		return left;
	}
	
	public Operation getRight(){
		return right;
	}
	
	public String toString(){
		return left.toString() + "-" + right.toString();
	}

	@Override
	public Operation accept(Visitor v) {
		return v.visit(this);
	}

	@Override
	public String acceptWriter(Writer w) {
		return w.visit(this);
	}

	@Override
	public Operation accept(Computator c) {
		return c.compute(this);
	}

	@Override
	public Double getNumericResult(Double val) {
		return left.getNumericResult(val) - right.getNumericResult(val);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Substraction)) return false;
		Substraction abs = (Substraction) o;
		return (left.equals(abs.left) && right.equals(abs.right));
	}
	
	public int hashCode(){
		return 79 * (left.hashCode() + right.hashCode());
	}
}
