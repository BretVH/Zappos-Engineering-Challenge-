package zappos;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bret
 */
public class Products_Value_Matcher {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     *
     */
    public static void main(String[] args) throws IOException {
        UserInterface userInterface = new UserInterface();
        userInterface.instantiateScanner();
        userInterface.promptNumberOfProducts();
        int numberOfProducts = userInterface.getNumberOfProducts();
        userInterface.promptAmmountToSpend(numberOfProducts);
        double ammountToSpend = userInterface.getAmmountToSpend();
        userInterface.closeScanner();
        String theData = "";
        try {
            theData = SimpleHttpConnection.httpGet(
                    "http://api.zappos.com/Search?"
                    + "key=67d92579a32ecef2694b74abfc00e0f26b10d623"
                    + "&term&limit=100");
            } catch (IOException ex) {
            Logger.getLogger(Products_Value_Matcher.class.getName()).log
                (Level.SEVERE, null, ex);
        }
        
            JsonParser parser = new JsonParser();
            JsonObject theJson = (JsonObject) parser.parse(theData);
            JsonArray arrayOfItems = theJson.getAsJsonArray("results");
            Product[] product = new Product[100];
            Gson gson = new Gson();
            product = gson.fromJson(arrayOfItems, Product[].class);
            Double[] forDomain = new Double[100];
            int j = 0;
            for (Product a : product) {
                Double myInt = a.getPrice();
                forDomain[j++] = myInt;

            }
            Domain myDomain = new Domain(forDomain);
            List<Variable> myVariables = new LinkedList<>();

            
            for (int i = 0; i < numberOfProducts; i++) {
                String myChar = "a" + i;
                Variable myVar = new Variable(myChar);
                myVariables.add(myVar);
            }
            CSP theProducts = new CSP(myVariables);
            myVariables.stream().forEach((theVar) -> {
                theProducts.setDomain(theVar, myDomain);
            });
            IsLessThanConstraint constraint1
                    = new IsLessThanConstraint
                        (myVariables, ammountToSpend * 1.05);
            GreaterThanConstraint constraint2 = new GreaterThanConstraint
                (myVariables, ammountToSpend * .95);
            theProducts.addConstraint(constraint2);
            theProducts.addConstraint(constraint1);
            SolutionStrategy solveIt = new MinConflictsStrategy(1000);
            Assignment[] solutions = new Assignment[3];
            for (int k = 0; k < 3; k++) {
                solutions[k] = solveIt.solve(theProducts);
            }
            Product[][] finalList = new Product[3][numberOfProducts];
            for (Product inSet : product) {
                for (int h = 0; h < 3; h++) {
                    for (int g = 0; g < numberOfProducts; g++) {
                        if (solutions[h] != null && inSet != null
                                && solutions[h].getAssignment
                                    (myVariables.get(g)) != null) {
                            if (inSet.getPrice() == 
                                    (Double)solutions[h].getAssignment
                                        (myVariables.get(g))) {
                                finalList[h][g] = inSet;
                            }
                        }
                    }
                }

            }
            for (int f = 0; f < 3; f++) {
                double sum = 0;
                System.out.println("Products:\n");
                for (int d = 0; d < numberOfProducts; d++) {
                    System.out.println(d + 1 + ".) " + finalList[f][d] + "\n");
                    if (finalList[f][d] != null) {
                        sum += finalList[f][d].getPrice();
                    }
                }
                System.out.println("SUM TOTAL: $" + sum);
            }
        
    }
}
