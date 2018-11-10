package ast;

import visitor.Visitor;
import writer.Writer;

public class Exp extends UnaryOperation {
	
	public Exp(Operation op) {
		super(op);
	}


	public String toString(){
		return "e^(" + op.toString() + ")";
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
		return Math.exp(op.getNumericResult(val));
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Exp)) return false;
		Exp abs = (Exp) o;
		return (op.equals(abs.op));
	}
	
	public int hashCode(){
		return 31 * op.hashCode();
	}
}
