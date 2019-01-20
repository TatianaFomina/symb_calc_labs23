package ast;

import visitor.Visitor;
import writer.Writer;
import computator.Computator;

public interface Operation {
	Operation accept(Visitor v);
	Operation accept(Computator v);
	String acceptWriter(Writer w);
	Double getNumericResult(Double val);
}
