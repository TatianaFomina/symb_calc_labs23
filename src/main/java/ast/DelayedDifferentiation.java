package ast;

import computator.Computator;
import visitor.Visitor;
import writer.Writer;

public class DelayedDifferentiation extends UnaryOperation {
    public DelayedDifferentiation(Operation op) {
        super(op);
    }

    @Override
    public String toString() {
        return "("+ op + ")\'";
    }

    @Override
    public Operation accept(Visitor v) {
        return null;
    }

    @Override
    public Operation accept(Computator c) {
        return c.compute(this);
    }

    @Override
    public String acceptWriter(Writer w) {
        return null;
    }

    @Override
    public Double getNumericResult(Double val) {
        return null;
    }
}
