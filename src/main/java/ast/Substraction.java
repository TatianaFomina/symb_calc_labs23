package ast;

public class Substraction extends BinaryOperation {

	public Substraction(Operation left, Operation right) {
		super(left, right);
	}



	public Operation getLeft(){
		return left;
	}
	
	public Operation getRight(){
		return right;
	}
	
	public String toString(){
		return left.toString() + "-" + right.toString();
	}

	@Override
	public Double getNumericResult(Double val) {
		return left.getNumericResult(val) - right.getNumericResult(val);
	}

	@Override
	public Operation getDerivative() {
		return new Substraction(left.getDerivative(), right.getDerivative());
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Substraction)) return false;
		Substraction abs = (Substraction) o;
		return (left.equals(abs.left) && right.equals(abs.right));
	}
	
	public int hashCode(){
		return 79 * (left.hashCode() + right.hashCode());
	}
}
