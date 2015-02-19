package currencyfair.messageprocessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.plaf.SliderUI;

public class MessageProcessor {
	Connection conn = null;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/currency_fair";
	static final String USER = "currency_fair";
	static final String PASS = "hire_me";

	private final static String selectTradeStatement = "SELECT trade_id " + 
			"FROM trade " + 
			"WHERE trade_id=(" +
				"SELECT max(trade_id) FROM trade WHERE processingStatus=0" +
			")";

	private final static String assignTradeStatement = "UPDATE trade " + 
			"SET processingStatus=2 " + 
			"WHERE trade_id=?";
	
	private final static String selectFullTradeStatement = "SELECT trade_id," +
	    "userId," +
	    "currencyFrom," +
	    "currencyTo," +
	    "amountSell," +
	    "amountBuy," +
	    "rate," +
	    "timePlaced," +
	    "originatingCountry," +
	    "timeReceived," +
	    "processingStatus," +
	    "timeProcessed," +
	    "exception " +
	    "FROM trade " +
	    "WHERE trade_id=?";
	
	private final static String completeTradeStatement = "UPDATE trade " + 
		"SET processingStatus=3," +
		"timeProcessed=?," +
		"exception=? " +
		"WHERE trade_id=?";
	
	private final static String updateUserDimStatement = "" +
		"INSERT INTO user_dim " +
		"(userId,count)" +
		"VALUES (?,?)" +
		"ON DUPLICATE KEY UPDATE count=count+?";
	
	private final static String updateCurrencyDimStatement = "" +
		"INSERT INTO currency_dim " +
		"(currency,amount,bought,sold)" +
		"VALUES (?,?,?,?)" +
		"ON DUPLICATE KEY UPDATE amount=amount+?, bought=bought+?, sold=sold+?";
		
	private final static int COMPLETE = 3;

	public static void main(String[] args) {
		MessageProcessor mp = new MessageProcessor();

		try {
			mp.init();
			mp.start();
		} catch (Exception e) {
			System.out.println("Error during processing...");
			e.printStackTrace();
		} finally {
			mp.stop();
		}

	}

	private void start() throws SQLException, InterruptedException {
		System.out.println("Starting main loop...");
		while(true) {
			PreparedStatement selectPS = conn.prepareStatement(selectTradeStatement);
			ResultSet selectRS = selectPS.executeQuery();

			long tradeId=0;
			if(!selectRS.first()) {
				System.out.println("No messages found to process...sleeping");
				Thread.sleep(5000);
				System.out.println("Waking up");
				continue;
			};
			tradeId = selectRS.getLong("trade_id");

			selectRS.close();
			selectPS.close();

			PreparedStatement assignPS = conn.prepareStatement(assignTradeStatement);
			assignPS.setLong(1, tradeId);
			int assignResult = assignPS.executeUpdate();
			conn.commit();
			if(assignResult == 1) {
				PreparedStatement selectFullPS = conn.prepareStatement(selectFullTradeStatement);
				selectFullPS.setLong(1, tradeId);
				ResultSet selectFullRS = selectFullPS.executeQuery();
				selectFullRS.first();
				Trade aTrade = createTrade(selectFullRS);
				
				aTrade.setProcessingStatus(COMPLETE);
				aTrade.setTimeProcessed(Calendar.getInstance().getTime());
				
				PreparedStatement completeTradePS = conn.prepareStatement(completeTradeStatement);
				completeTradePS.setDate(1, new java.sql.Date(aTrade.getTimeProcessed().getTime()));
				completeTradePS.setBoolean(2, aTrade.isException());
				completeTradePS.setLong(3, aTrade.getTradeId());
				completeTradePS.executeUpdate();
				
				PreparedStatement updateUserDimPS = conn.prepareStatement(updateUserDimStatement);
				updateUserDimPS.setString(1, aTrade.getUserId());
				updateUserDimPS.setLong(2, 1);
				updateUserDimPS.setLong(3, 1);
				updateUserDimPS.executeUpdate();
				
				PreparedStatement updateCurrencyDimPS = conn.prepareStatement(updateCurrencyDimStatement);
				updateCurrencyDimPS.setString(1, aTrade.getCurrencyFrom());
				updateCurrencyDimPS.setDouble(2, aTrade.getAmountBuy());
				updateCurrencyDimPS.setDouble(3, aTrade.getAmountBuy());
				updateCurrencyDimPS.setDouble(4, 0);
				updateCurrencyDimPS.setDouble(5, aTrade.getAmountBuy());
				updateCurrencyDimPS.setDouble(6, aTrade.getAmountBuy());
				updateCurrencyDimPS.setDouble(7, 0);
				updateCurrencyDimPS.executeUpdate();
				
				updateCurrencyDimPS = conn.prepareStatement(updateCurrencyDimStatement);
				updateCurrencyDimPS.clearParameters();
				updateCurrencyDimPS.setString(1, aTrade.getCurrencyTo());
				updateCurrencyDimPS.setDouble(2, aTrade.getAmountSell()*-1);
				updateCurrencyDimPS.setDouble(3, 0);
				updateCurrencyDimPS.setDouble(4, aTrade.getAmountSell());
				updateCurrencyDimPS.setDouble(5, aTrade.getAmountSell()*-1);
				updateCurrencyDimPS.setDouble(6, 0);
				updateCurrencyDimPS.setDouble(7, aTrade.getAmountSell());
				updateCurrencyDimPS.executeUpdate();
				
				selectFullRS.close();
				selectFullPS.close();
				
				completeTradePS.close();
				updateUserDimPS.close();
				updateCurrencyDimPS.close();
				
				conn.commit();
			}
		}
	}

	private Trade createTrade(ResultSet selectFullRS) throws SQLException {
		Trade aTrade = new Trade();
		aTrade.setTradeId(selectFullRS.getLong("trade_id"));
		aTrade.setUserId(selectFullRS.getString("userId"));
		aTrade.setCurrencyFrom(selectFullRS.getString("currencyFrom"));
		aTrade.setCurrencyTo(selectFullRS.getString("currencyTo"));
		aTrade.setAmountSell(selectFullRS.getDouble("amountSell"));
		aTrade.setAmountBuy(selectFullRS.getDouble("amountBuy"));
		aTrade.setRate(selectFullRS.getDouble("rate"));
		aTrade.setTimePlaced(selectFullRS.getTimestamp("timePlaced"));
		aTrade.setOriginatingCountry(selectFullRS.getString("originatingCountry"));
		aTrade.setTimeReceived(selectFullRS.getTimestamp("timeReceived"));
		aTrade.setProcessingStatus(selectFullRS.getInt("processingStatus"));
		aTrade.setTimeProcessed(selectFullRS.getTimestamp("timeProcessed"));
		aTrade.setException(selectFullRS.getBoolean("exception"));
		
		return aTrade;
	}

	private void init() throws ClassNotFoundException, SQLException {
		System.out.println("Initializing MessageProcessor...");
		Class.forName("com.mysql.jdbc.Driver");

		System.out.println("Connecting to database...");
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}
	
	private void stop() {
		System.out.println("Stopping message processor...");
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
