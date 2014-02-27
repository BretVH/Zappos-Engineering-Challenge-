/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zappos_software_engineering_challenge;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Bret
 */
public class Products_Value_Matcher {

    /**
     * @param args the command line arguments

     */
    public static void main(String[] args) throws IOException  
    {
        try {
            String theData;
            theData = Simple_Http_Connection.httpGet(
                    "http://api.zappos.com/Search?"
                            + "key=67d92579a32ecef2694b74abfc00e0f26b10d623"
                            + "&term&limit=100");
            Pattern pattern;
            Matcher matcher;
            pattern = Pattern.compile("\\B\\[");
            matcher = pattern.matcher(theData);
            matcher.find();
            theData = theData.substring(matcher.start());
            pattern = Pattern.compile("\\B,\"term\":*");
            
            matcher = pattern.matcher(theData);
            matcher.find();
            theData = theData.substring(0, matcher.start());
            Product[] product = new Product[100];
            Gson gson = new Gson();
            product = gson.fromJson(theData, Product[].class);
            Double[] forDomain = new Double[100];
            int j = 0;
            for(Product a: product)
            {
                Double myInt = a.getPrice();
                forDomain[j++] = myInt;
                
            }
            Domain myDomain = new Domain(forDomain);
            List<Variable> myVariables = new LinkedList<Variable>();
            Scanner in = new Scanner(System.in);
            int numOfProducts = 0;
            double ammountToSpend = 0.00;
            System.out.println("Please enter the number of products you"
                    + " wish to buy: ");
            if(in.hasNextInt())
            {
                numOfProducts = in.nextInt();
                while(numOfProducts > 100)
                {
                     System.out.println("Please use less than 100, try again: "
                             + "");
                     if(in.hasNextInt())
                     {
                        numOfProducts = in.nextInt();
                     }
                     else
                     {
                        System.out.println
                            ("The number of products must be an integer"
                                + ", entered using digits [0-9]");
                    }
                }
            }
            else
            {
                System.out.println("The number of products must be an integer"
                        + ", entered using digits [0-9]");
            }
            System.out.println("You wish to buy " + numOfProducts + " products."
                + " How much do you want to spend? :");
            if(in.hasNextDouble())
            {
                ammountToSpend = in.nextDouble();
            }
            else
            {
                System.out.println("The amount you wish to spend must be an "
                        + "double in the form 0.00, entered using digits "
                        + "[0-9]");
            }
            for(int i = 0; i < numOfProducts; i++)
            {
                String myChar = "a";
                Variable myVar;
                if(i < 26)
                {   
                    char theChar = myChar.charAt(0);
                    int aChar = theChar + i;
                    myChar = "" + (char)aChar;
                    myVar = new Variable(myChar);
                }
                else if(i < 53)
                {
                    int aChar = i - 27 + (int)'a';
                    myChar = "" + (char)aChar + (char)aChar;
                    myVar = new Variable(myChar);
                }
                else
                {
                    int aChar = i - 54 + (int)'a';
                    myChar = "" + (char)aChar + (char)aChar + (char)aChar;
                    myVar = new Variable(myChar);
                }
                myVariables.add(myVar);
            }
            CSP theProducts = new CSP(myVariables);
            for(Variable theVar : myVariables)
            {
                theProducts.setDomain(theVar, myDomain);
            }
            IsLessThanConstraint constraint1 = 
                    new IsLessThanConstraint(myVariables, ammountToSpend * 1.05);
            GreaterThanConstraint constraint2 = new GreaterThanConstraint
                    (myVariables, ammountToSpend * .95);
            theProducts.addConstraint(constraint2);
            theProducts.addConstraint(constraint1);
            SolutionStrategy solveIt = new MinConflictsStrategy(1000);
            Assignment[] solutions = new Assignment[3];
            for(int k = 0; k < 3; k++)
            {
                solutions[k] = solveIt.solve(theProducts);
            }
            Product[][] finalList = new Product[3][numOfProducts];
            for(Product inSet: product)
            {
                for(int h = 0; h < 3; h++)
                {
                    for(int g = 0; g < numOfProducts; g++ )
                    {
                        if(solutions[h] != null && inSet != null && 
                                solutions[h].getAssignment
                                    (myVariables.get(g)) != null)
                            if(inSet.getPrice() == (Double)solutions[h].getAssignment
                                (myVariables.get(g)))
                                    finalList[h][g] = inSet;
                    }
                }
                    
            }
            for(int f = 0; f < 3; f++)
            {
                double sum = 0;
                System.out.println("Products:\n");
                for(int d = 0; d < numOfProducts; d++ )
                {
                    System.out.println(d + 1 + ".) " + finalList[f][d] + "\n");
                    if(finalList[f][d] != null)
                        sum += finalList[f][d].getPrice();
                }
                System.out.println("SUM TOTAL: $"  + sum);
            }
        }
        catch (IOException ex) {
          Logger.getLogger(Products_Value_Matcher.class.getName()).log
                (Level.SEVERE, null, ex);
            
       }
    }
}
    
