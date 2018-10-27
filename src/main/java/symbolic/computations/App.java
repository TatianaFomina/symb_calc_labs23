package symbolic.computations;

import ast.Operation;
import parser.ParserException;
import parser.ParsingTool;
import writer.WritingTool;

import java.io.IOException;
import java.util.Scanner;


public class App 
{
    public static void main( String[] args ) throws IOException, ParserException {
//        System.out.print("Input path to the file which contains JSON expression tree: ");
//        Scanner sc = new Scanner(System.in);
//        String filename = sc.next();

        //ParsingTool parsingTool = new ParsingTool(filename);
        ParsingTool parsingTool = new ParsingTool("exp_tree.json"); //stub

        Operation expressionTree = parsingTool.getTree();
        Operation derivative = expressionTree.getDerivative();
        System.out.println(derivative);

        WritingTool writer = new WritingTool(derivative);
        writer.writeTo("result.eqn");

    }
}