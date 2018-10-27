package ast;

import visitor.Visitor;

public interface Operation {
	Operation accept(Visitor v);
	Double getNumericResult(Double val);
}
