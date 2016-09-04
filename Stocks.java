package dk.test.GBCE;

import java.util.ArrayList;

/*
 * Main class for Super Simple Stocks test assignment
 * Allows creation of stock objects which may contain trade objects
 * 
 * Program will exit with system code 0 if execution OK
 * @author OBS
 */
public class Stocks {
	
	public static void main(String[] args) {
		ArrayList<Stock> st = new ArrayList<Stock>();			// Array list of stock objects
		Double[] stockprices = new Double[5];					// Array to hold stock prices when calculating All share index

// Create stock objects and add to list of stocks in GBCE
		st.add(new Stock("TEA", "Common", 0, 0, 100));
		st.add(new Stock("POP", "Common", 8, 0, 100));
		st.add(new Stock("ALE", "Common", 23, 0, 60));
		st.add(new Stock("GIN", "Preferred", 8, 2, 100));
		st.add(new Stock("JOE", "Common", 13, 0, 250));
		
		// Thread sleep to make sure that ticker price has been generated and set
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		for (Stock stock : st) {
			if(stock.getSymbol() == "POP") {
// 1.a.1: Calculate dividend yield for stock
				System.out.println("Dividend yield for " + stock.getSymbol() + ": " + String.format( "%.3f", stock.calculateDividendYield()));
// 1.a.2: Calculate P/E ratio
				System.out.println("P/E ratio for " + stock.getSymbol() + ": " + String.format( "%.3f", stock.calculatePERatio()));
// 1.a.3: Record trade in stock
				try {
					stock.createTrade(2000, "Buy");
					stock.createTrade(500, "Buy");
					stock.createTrade(100, "Buy");
					stock.createTrade(2000, "Sell");
					stock.createTrade(200, "Sell");
					stock.createTrade(200, "Sell");
				} catch (IllegalArgumentException e) {
					System.out.println("Trade could not be created");
					e.printStackTrace();
					System.exit(1);
				}
				
// 1.a.4.: Calculate stock price for trades in last 15 minutes timeframe
				System.out.println("Stock price for " + stock.getSymbol() + ": " + String.format( "%.3f", stock.calculateStockPrice15Minutes()));
			}
		}
// 1.b.  : Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
//			Array [5] containing stock price 
		int i = 0;
		for (Stock stock : st) {
			stockprices[i] = stock.calculateStockPrice15Minutes();
			i++;
		}
		
		double p = 0;										// Product of stock prices
		for (int j = 0; j < stockprices.length; j++) {
			if (j == 0) {
				p = stockprices[j];
			} 
			else {
				p = p * stockprices[j];
			}
		}
		double geoMean = Math.pow(Math.E, Math.log(p)/stockprices.length);
		System.out.println("GBCE All Share Index geometric mean: " + String.format( "%.3f", geoMean ));
		
		System.exit(0);
	}

}
