package ast;

import visitor.Visitor;
import writer.Writer;

public class Cos extends UnaryOperation {

	public Cos(Operation op) {
		super(op);
	}


	public String toString(){
		return "cos(" + op.toString() + ")";
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
		return Math.cos(op.getNumericResult(val));
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Cos)) return false;
		Cos abs = (Cos) o;
		return (op.equals(abs.op));
	}
	
	public int hashCode(){
		return 29 * op.hashCode();
	}

}
