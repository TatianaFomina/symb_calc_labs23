package ast;

import visitor.Visitor;

public class Asin extends UnaryOperation {

	public Asin(Operation op) {
		super(op);
	}


	public String toString(){
		return "asin(" + op.toString() + ")";
 	}

	@Override
	public Operation accept(Visitor v) {
		return v.visit(this);
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
