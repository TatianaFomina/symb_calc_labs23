package ast;

import visitor.Visitor;
import writer.Writer;

public class Atan extends UnaryOperation {

	public Atan(Operation op) {
		super(op);
	}


	public String toString(){
		return "atan(" + op.toString() + ")";
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
	public Double getNumericResult(Double val) {
		return Math.atan(op.getNumericResult(val));
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Atan)) return false;
		Atan abs = (Atan) o;
		return (op.equals(abs.op));
	}
	
	public int hashCode(){
		return 17 * op.hashCode();
	}
}
