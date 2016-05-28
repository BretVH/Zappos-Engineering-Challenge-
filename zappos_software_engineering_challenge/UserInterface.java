/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zappos;

import java.util.Scanner;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Bret
 */
public class UserInterface {

    private Scanner in;

    /**
     * Method: instantiateScannerObject instantiates a scanner using system.in
     */
    public void instantiateScanner() {
        in = new Scanner(System.in);
    }

    /**
     * Method: closeScanner Closes scanner.
     */
    public void closeScanner() {
        in.close();
    }

    public void promptNumberOfProducts() {
        System.out.println("Please enter the number of products you"
                + " wish to buy: ");
    }

    public void numberOfProductsError() {
        System.out.println("The number of products must be an integer"
                + ", entered using digits [0-9]");
    }

    public void promptAmmountToSpend(int numberOfProducts) {
        System.out.println("You wish to buy " + numberOfProducts + " products."
                + " How much do you want to spend? :");
    }

    public void ammountToSpendError() {
        System.out.println("The amount you wish to spend must be an "
                + "double in the form 0.00, entered using digits "
                + "[0-9]");
    }

    public int getNumberOfProducts() {
        String numberOfProducts = in.nextLine();
        while(numberOfProducts.charAt(0) == '0'){
            numberOfProducts = numberOfProducts.substring(1);
        }
        while(!validateNumberOfProducts(numberOfProducts)){
            numberOfProductsError();
            numberOfProducts = in.nextLine();
        }
        return Integer.parseInt(numberOfProducts);
    }

    public double getAmmountToSpend() {
        String ammountToSpend = in.nextLine();
        while(!validateAmmountToSpend(ammountToSpend)){
            ammountToSpendError();
            ammountToSpend = in.nextLine();
        }
        return Double.parseDouble(ammountToSpend);
    }
  
    private boolean validateNumberOfProducts(String numberOfProducts) {
        return NumberUtils.isNumber(numberOfProducts);
    }

    private boolean validateAmmountToSpend(String ammountToSpend) {
        return NumberUtils.isParsable(ammountToSpend);
    }
}
