package symbolic.computations;

import ast.BinaryOperation;
import ast.Operation;
import ast.UnaryOperation;
import computator.JsonComputator;
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
        Operation expressionTree = parser.getAbstractSyntaxTree("exp_tree.json");
//        Operation derivative = expressionTree.accept(new DerivativeVisitor());
//        System.out.println(derivative);


        Operation derivative = expressionTree.accept(new JsonComputator("rules.json"));
        //writing result
        BufferedWriter writer = new BufferedWriter(new FileWriter("result3.eqn"));
        writer.write(".EQ\n");
        writer.write(derivative.acceptWriter(new EqnWriter()));
        writer.write("\n.EN");
        writer.close();

    }
}
