package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Asin extends UnaryOperation {

	public Asin(Operation op, boolean delayed) {
		super(op, delayed);
	}
	public Asin(Operation op) {
		super(op, false);
	}


	public String toString(){
		return "asin(" + op.toString() + ")";
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
		return Math.asin(op.getNumericResult(val));
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Asin)) return false;
		Asin abs = (Asin) o;
		return (op.equals(abs.op));
	}
	
	public int hashCode(){
		return 13 * op.hashCode();
	}
}
