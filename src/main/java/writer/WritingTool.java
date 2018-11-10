package writer;

import ast.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WritingTool {

    private Operation expressionTree;

    public WritingTool(Operation expressionTree) {
        this.expressionTree = expressionTree;
    }

    public void writeTo(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(".EQ\n");
        writer.write(expressionTree.acceptWriter(new EqnWriter()));
        writer.write("\n.EN");
        writer.close();
    }

}
