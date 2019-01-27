package symbolic.computations;

import ast.Operation;
import computator.JsonComputator;
import parser.JsonParser;
import parser.ParserException;
import visitor.Visitor;
import writer.EqnWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.beryx.textio.*;


public class App 
{
    public static void main( String[] args ) throws IOException, ParserException {
        //cli
//        TextIO textIO = TextIoFactory.getTextIO();
//
//        String inputsFileName = textIO.newStringInputReader()
//                .withDefaultValue("input.json")
//                .read("Enter name of the file containing list of expressions:");


        JsonParser parser = new JsonParser();
//        Operation expressionTree = parser.getAbstractSyntaxTree("exp_tree.json");

        Map<String, Operation> context = parser.getASTs("input.json");
        for (Map.Entry<String, Operation> expressionEntry : context.entrySet()){
            String name = expressionEntry.getKey();
            Operation expression = expressionEntry.getValue();
            if (!expression.isDelayed()){
                Operation derivaive = expression.accept(new JsonComputator("rules.json"));
                context.put(name, derivaive);
            }

        }
//        Operation derivative = expressionTree.accept(new JsonComputator("rules.json"));
//
//        //writing result
//        BufferedWriter writer = new BufferedWriter(new FileWriter("result3.eqn"));
//        writer.write(".EQ\n");
//        writer.write(derivative.acceptWriter(new EqnWriter()));
//        writer.write("\n.EN");
//        writer.close();
        System.out.println();

    }
}
