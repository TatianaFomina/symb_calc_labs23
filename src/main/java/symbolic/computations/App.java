package symbolic.computations;

import ast.Operation;
import computator.JsonComputator;
import computator.Reducer;
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

//import static symbolic.computations.App.MenuItem.EXIT;


public class App
{
    private static void printContext(Map<String, Operation> context, TextTerminal<?> terminal){
        terminal.println("\nContext:");

        if (context.isEmpty())
            terminal.println("Context is empty");

        for (Map.Entry<String, Operation> expressionEntry : context.entrySet()){
            String name = expressionEntry.getKey();
            Operation expr = expressionEntry.getValue();
            if (expr.isDelayed())
                terminal.println(name + " := " + expr);
            else terminal.println(name + " = " + expr);
        }
    }
    public static enum MenuItem{
        INPUT, CONTEXT, EVALUATE
    }
    public static enum ContextMenuItem{
        PRINT, RESET, BACK
    }
    public static void main( String[] args ) throws IOException, ParserException {

        Map<String, Operation> context = new HashMap<>();
        JsonParser parser = new JsonParser();
        //cli
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();

//

        MenuItem menuItem = null;

        while (true){
            menuItem = textIO.newEnumInputReader(MenuItem.class)
                    .read("\nMenu");

            switch (menuItem){
                case INPUT:
                    String inputsFileName = textIO.newStringInputReader()
                            .withDefaultValue("input.json")
                            .read("Enter name of the file containing list of expressions");
                    context.putAll(parser.getASTs("input.json"));
                    break;
                case CONTEXT:
                    ContextMenuItem contextMenuItem = textIO.newEnumInputReader(ContextMenuItem.class)
                            .read("\nContext");

                    switch (contextMenuItem){
                        case PRINT:
                            printContext(context, terminal);
                            break;
                        case RESET:
                            context.clear();
                            terminal.println("Context has been reset");
                            break;
                        case BACK: break;
                    }
                    break;
                case EVALUATE:

                    printContext(context, terminal);
                    String exprName = textIO.newStringInputReader()
                            .read("Name of expression to evaluate");
                    Operation expression = context.get(exprName);
                    if (expression != null){
                        expression = expression.accept(new JsonComputator("rules.json"));
                        expression = expression.accept(new Reducer());
                        terminal.println(exprName + " = " + expression);
                        context.put(exprName, expression);
                    }

                    break;
            }
        }



//        Operation expressionTree = parser.getAbstractSyntaxTree("exp_tree.json");


//        for (Map.Entry<String, Operation> expressionEntry : context.entrySet()){
//            String name = expressionEntry.getKey();
//            Operation expression = expressionEntry.getValue();
//            if (!expression.isDelayed()){
//                Operation derivaive = expression.accept(new JsonComputator("rules.json"));
//                context.put(name, derivaive);
//            }
//
//        }
//        Operation derivative = expressionTree.accept(new JsonComputator("rules.json"));
//
//        //writing result
//        BufferedWriter writer = new BufferedWriter(new FileWriter("result3.eqn"));
//        writer.write(".EQ\n");
//        writer.write(derivative.acceptWriter(new EqnWriter()));
//        writer.write("\n.EN");
//        writer.close();
//        System.out.println();

    }
}
