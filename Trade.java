package dk.test.GBCE;
import java.sql.Timestamp;

public class Trade {
	private Timestamp tmp;
	private int quantity;
	private char buySell;
	private double price;
	
	public Trade(int _quantity, char _buySell, double _price) {
		// Set current time for trade
		tmp = new Timestamp(System.currentTimeMillis());
		quantity = _quantity;
		
		if(_buySell == 'B' || _buySell == 'S') {
			buySell = _buySell;			// Value may either 'B' or 'S'
		}
		else {
			// throw exception for false input
		}
			
		price = _price;
		
	}
	

	public char getBuySell() {
		return buySell;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public Timestamp getTmp() {
		return tmp;
	}
	
	public void setBuySell(char buySell) {
		this.buySell = buySell;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setTmp(Timestamp tmp) {
		this.tmp = tmp;
	}
	
}
