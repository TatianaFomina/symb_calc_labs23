package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Tan extends UnaryOperation {

	public Tan(Operation op) {
		super(op);
	}


	public String toString(){
		return "tan(" + op.toString() + ")";
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
		return Math.tan(op.getNumericResult(val));
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Tan)) return false;
		Tan abs = (Tan) o;
		return (op.equals(abs.op));
	}
	
	public int hashCode(){
		return 59 * op.hashCode();
	}
}
