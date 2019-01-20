package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Sin extends UnaryOperation {

	public Sin(Operation op) {
		super(op);
	}


	public String toString(){
		return "sin(" + op.toString() + ")";
 	}

	@Override
	public Operation accept(Visitor v) {
		return v.visit(this);
	}

	@Override
	public Double getNumericResult(Double val) {
		return Math.sin(op.getNumericResult(val));
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
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Sin)) return false;
		Sin abs = (Sin) o;
		return (op.equals(abs.op));
	}
	
	public int hashCode(){
		return 47 * op.hashCode();
	}
}
