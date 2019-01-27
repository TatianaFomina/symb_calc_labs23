package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class Constant extends Operation {
	private String c;
	
	public Constant(String c) {
		super(false);
		this.c = c;
	}

	public String toString(){
		return c;
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
		return Double.parseDouble(c);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Constant)) return false;
		Constant abs = (Constant) o;
		return (c.equals(abs.c));
	}
	
	public int hashCode(){
		return 23 * (int) Double.parseDouble(c);
	}
}
