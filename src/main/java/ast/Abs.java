package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Abs extends UnaryOperation {
	
	public Abs(Operation op, boolean delayed) {
		super(op, delayed);
	}
	public Abs(Operation op) {
		super(op, false);
	}

	public String toString(){
		return "|" + op.toString() + "|";
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
		return Math.abs(op.getNumericResult(val));
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Abs)) return false;
		Abs abs = (Abs) o;
		return (op.equals(abs.op));
	}
	
	public int hashCode(){
		return 7 * op.hashCode();
	}
}
