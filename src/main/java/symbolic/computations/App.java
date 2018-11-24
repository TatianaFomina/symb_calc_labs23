package symbolic.computations;

import ast.BinaryOperation;
import ast.Operation;
import ast.UnaryOperation;
import parser.JsonParser;
import parser.ParserException;
import visitor.DerivativeVisitor;
import visitor.Visitor;
import writer.EqnWriter;
import writer.WritingTool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class App 
{
    public static void main( String[] args ) throws IOException, ParserException {
        //ParsingTool parsingTool = new ParsingTool(); //stub
        JsonParser parser = new JsonParser();
        Operation expressionTree = parser.getAbstractSyntaxTree("exp_tree2.json");
        Operation derivative = expressionTree.accept(new DerivativeVisitor());
        System.out.println(derivative);

        //writing result
        BufferedWriter writer = new BufferedWriter(new FileWriter("result1.eqn"));
        writer.write(".EQ\n");
        writer.write(expressionTree.acceptWriter(new EqnWriter()));
        writer.write("\n.EN");
        writer.close();

    }
}
