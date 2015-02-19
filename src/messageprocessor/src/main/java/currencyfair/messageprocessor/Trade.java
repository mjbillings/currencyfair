package currencyfair.messageprocessor;

import java.util.Date;

public class Trade {
	private long tradeId;
	private String userId;
	private String currencyFrom;
	private String currencyTo;
	private double amountSell;
	private double amountBuy;
	private double rate;
	private Date timePlaced;
	private String originatingCountry;
	private Date timeReceived;
	private int processingStatus;
	private Date timeProcessed;
	private boolean exception;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCurrencyFrom() {
		return currencyFrom;
	}
	public void setCurrencyFrom(String currencyFrom) {
		this.currencyFrom = currencyFrom;
	}
	public String getCurrencyTo() {
		return currencyTo;
	}
	public void setCurrencyTo(String currencyTo) {
		this.currencyTo = currencyTo;
	}
	public double getAmountSell() {
		return amountSell;
	}
	public void setAmountSell(double amountSell) {
		this.amountSell = amountSell;
	}
	public double getAmountBuy() {
		return amountBuy;
	}
	public void setAmountBuy(double amountBuy) {
		this.amountBuy = amountBuy;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public Date getTimePlaced() {
		return timePlaced;
	}
	public void setTimePlaced(Date timePlaced) {
		this.timePlaced = timePlaced;
	}
	public String getOriginatingCountry() {
		return originatingCountry;
	}
	public void setOriginatingCountry(String originatingCountry) {
		this.originatingCountry = originatingCountry;
	}
	public Date getTimeReceived() {
		return timeReceived;
	}
	public void setTimeReceived(Date timeReceived) {
		this.timeReceived = timeReceived;
	}
	public int getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(int processingStatus) {
		this.processingStatus = processingStatus;
	}
	public Date getTimeProcessed() {
		return timeProcessed;
	}
	public void setTimeProcessed(Date timeProcessed) {
		this.timeProcessed = timeProcessed;
	}
	public boolean isException() {
		return exception;
	}
	public void setException(boolean exception) {
		this.exception = exception;
	}
	public long getTradeId() {
		return tradeId;
	}
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
}
