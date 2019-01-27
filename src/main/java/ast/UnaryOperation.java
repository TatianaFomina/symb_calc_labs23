package ast;

import static java.util.Objects.requireNonNull;

public abstract class UnaryOperation extends Operation {
	final protected Operation op;
	
	public UnaryOperation(Operation op, boolean delayed) {
		super(delayed);
		this.op = requireNonNull(op);
	}
	public UnaryOperation(Operation op) {
		super(false);
		this.op = requireNonNull(op);
	}

	public Operation getOp(){
		return op;
	}
}
