package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class SimpleVar extends Operation {
	
	public String toString(){
		return "x";
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
		if (val == null) throw new NullPointerException("Variable set to null!");
		return val;
	}

	@Override
	public Operation accept(Computator c) {
		return c.compute(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		return (o instanceof SimpleVar);
	}
	
	public int hashCode(){
		return 43;
	}
}
