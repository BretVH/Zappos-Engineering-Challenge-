/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zappos_software_engineering_challenge;

/**
 *
 * @author Bret
 */
class Product 
{
    Product(){};

    private String styleId;
    private String price;
    private String originalPrice;
    private String productUrl;
    private String colorId;
    private String productName;
    private String brandName;
    private String thumbnailImageUrl;
    private String percentOff;
    private String productId;
    
    public double getPrice()
    {
        int TEN = 10;
	int ASCII_OFFSET = 48;
	int DECIMAL = 46;
	int DOLLAR = 36;
	int NINE = 57;
	int stringLength = this.price.length();
	int offset = 1;
	for(int j = 1; j < stringLength; j++)
	{
		offset *= TEN;
	}
	int answer = 0;
	int digit;
	boolean isDollar = false;
	for(int i = 0; i < stringLength; i++)
	{
		digit = (int)this.price.charAt(i);
		if(i == 0 && digit == DOLLAR)//test for negative condition 
		{	                    //and set boolean and correct offset	
			isDollar = true;
			offset /=TEN;
		}
		else if( digit == DECIMAL )//return answer, long can't hold floating point
		{
			int fail = stringLength - i;  //find out how many powers of 10 off
			double divisor = Math.pow(TEN, fail); //get correction divisor 
			answer /= divisor;   //correct answer
			break;
		}
		else if( digit < ASCII_OFFSET || digit > NINE )//only accept decimal numbers
		{
			return -1;
		}
		else
		{
			digit -= ASCII_OFFSET;  //get numerical value for ascii char
			digit *= offset; //set to correct power of 10 for digit
			offset /= TEN; //correct offset to deal with next digit, one less power of ten
			answer += digit; //compile answer
		}
	}/* code goes here to convert a string to a long */ 
        return answer;
    }
    @Override
    public String toString()
    {
        return "Zappos Product:\n" + productName + "\nURL:\n" + productUrl +
                "\nprice:\n" + price + "\noriginalPrice:\n" + originalPrice + 
                "\ncolor:\n" + colorId + "\nbrand:\n" + brandName + 
                "\npercent off:\n"
                + percentOff + "\nTumbnail Image Url:\n" + thumbnailImageUrl +
                "\nZappos Product ID:\n" + productId;
                
    }
}
