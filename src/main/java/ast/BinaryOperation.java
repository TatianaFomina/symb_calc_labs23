package ast;

import static java.util.Objects.requireNonNull;

public abstract class BinaryOperation extends Operation {
	final protected Operation left ;
	final protected Operation right;
	
	public BinaryOperation(Operation left, Operation right, boolean delayed) {
		super(delayed);
		this.left = requireNonNull(left);
		this.right = requireNonNull(right);
	}

    public BinaryOperation(Operation left, Operation right) {
        super(false);
        this.left = requireNonNull(left);
        this.right = requireNonNull(right);
    }





	public Operation getLeft(){
		return left;
	}
	
	public Operation getRight(){
		return right;
	}
}
