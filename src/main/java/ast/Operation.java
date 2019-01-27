package ast;

import visitor.Visitor;
import writer.Writer;
import computator.Computator;

public abstract class Operation {
	final protected boolean delayed;

	public Operation(boolean delayed) {
		this.delayed = delayed;
	}

    public Operation() {
	    this.delayed = false;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public abstract Operation accept(Visitor v);
    public abstract Operation accept(Computator v);
	public abstract String acceptWriter(Writer w);
    public abstract Double getNumericResult(Double val);

}
