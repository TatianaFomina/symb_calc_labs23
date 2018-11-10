package ast;

import visitor.Visitor;
import writer.Writer;

public interface Operation {
	Operation accept(Visitor v);
	String acceptWriter(Writer w);
	Double getNumericResult(Double val);
}
