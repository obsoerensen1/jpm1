package dk.test.GBCE;

import java.util.ArrayList;
import java.util.Random;

/**
 * Stock object
 * 
 * @author OBS
 */
public class Stock {

	private final String symbol;		
	private double tickerPrice;
	private double stockPrice;
	private final String type;
	private double lastDividend;
	private int fixedDividendPct;
	private int parValue;
	private Thread sub1;

	private ArrayList<Trade> trading = new ArrayList<Trade>	();
	
	public Stock(String _symbol, String _type, double _lastDividend, int _fixedDividendPct, int _parValue) {
		this.symbol = _symbol;
		this.type = _type;
		this.lastDividend = _lastDividend;
		this.fixedDividendPct = _fixedDividendPct;
		this.parValue = _parValue;
		
		StartGenerateTickerPrice();
		this.stockPrice = (double)this.parValue/100;
	}

	private void StartGenerateTickerPrice() {
/*
 * Create new subthread to run generation of random stock price 
 */
		sub1 = new Thread() {
		    public void run() {
		        while(true) {
		        	generateTickerPrice();
		        	try {
						Thread.sleep(3000);					// Thread sleep for 0.1 seconds
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        }
		    }  
		};
		sub1.start();
	}
	
/*
 * Create a new trade in stock
 * Timestamp is created during creation of Trade object
 */
	public void createTrade(int quantity, String tradeType) throws IllegalArgumentException {
		char tr = ' ';
		switch (tradeType) {
		case "Buy": tr = 'B';
			break;
		case "Sell": tr = 'S';
			break;
		default: 
			throw new IllegalArgumentException("Illegal Buy/Sell indicator: " + tradeType);
			
		}
		try {
			trading.add(new Trade(quantity, tr, this.tickerPrice));
		} catch (IllegalArgumentException e) {
			System.out.println("An invalid argument was passed");
			e.printStackTrace();
		}
	}

/*
 * Calculation of stock dividend yield
 */
	public double calculateDividendYield() {
		
		double divYield = 0.0;
		
		if (this.tickerPrice == 0) {
			return 0;
		}
		
		if(this.type == "Common") {
			divYield = (this.lastDividend / this.tickerPrice);
		}
		else if(this.type == "Preferred") { 
			divYield = ((this.parValue*(this.fixedDividendPct/100))/this.tickerPrice);
		}
		return divYield;
	}
	
/*
 * Calculation of stock P/E ratio
 */
	public double calculatePERatio() {

		if (this.tickerPrice > 0.0 && this.lastDividend > 0.0) {
			return (this.tickerPrice / this.lastDividend);
		}
		return 0;
	}
	
/*
 * Calculation of stock price during last 15 minute timeframe
 */
	public double calculateStockPrice15Minutes() {
		double sumPriceQuantity = 0.0;						// Multiplication of stock price and quantity
		double sumQuantity = 0.0;							// Sum of stock quantities sold

		long currentTime = System.currentTimeMillis();		// Get current time for calculation of 15 minute timeframe
		long startTimeFrame = currentTime - (15 * 60000);	// Set beginning of 15 minute timeframe
		
		for (Trade trade : trading) {
			if((currentTime - trade.getTmp().getTime()) <= startTimeFrame) {
				sumPriceQuantity += (trade.getPrice() * trade.getQuantity());
				sumQuantity += trade.getQuantity();	
			}
		}
		
		if(sumPriceQuantity > 0.0 && sumQuantity > 0.0) {
			this.stockPrice = sumPriceQuantity / sumQuantity;
		}
		
		return this.stockPrice;
	}

/*
 * Calculate random stock price in range 0.00 - 4.99
 */
	public void generateTickerPrice() {
		Random ran = new Random();
		double ran1 = ran.nextInt(4);
		double ran2 = ran.nextInt(100);
		
		this.tickerPrice = ran1 + (ran2/100);

	}
		
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Stock) {
			Stock st = (Stock) obj;
			if (st.symbol.equals(this.symbol)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Symbol: " + this.symbol + ", type: " + this.type + ". Last Dividend: " + this.lastDividend + ". Fixed dividend: " + this.fixedDividendPct + "%. Par value: " + this.parValue;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public double getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(double tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	public String getType() {
		return type;
	}

	public double getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(double lastDividend) {
		this.lastDividend = lastDividend;
	}

	public int getFixedDividendPct() {
		return fixedDividendPct;
	}

	public void setFixedDividendPct(int fixedDividendPct) {
		this.fixedDividendPct = fixedDividendPct;
	}

	public int getParValue() {
		return parValue;
	}

	public void setParValue(int parValue) {
		this.parValue = parValue;
	}
}
