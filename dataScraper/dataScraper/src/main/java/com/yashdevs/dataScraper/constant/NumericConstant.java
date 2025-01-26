package com.yashdevs.dataScraper.constant;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class NumericConstant {
	
	public static final int PAST_TRADE_DAYS = 1000; // each year on avg has 240 - 250 trade days
	public static final String DEFAULT_ZERO = "0.0000";
	public static final int DEFAULT_SCALE = 2;
	public static final int ANNUAL_TRADE_DAYS = 248;
	public static final RoundingMode HALF_EVEN = RoundingMode.HALF_EVEN;
	public static final int SMA_MAX_PLOT_POINT = 20;
	
	public static final SimpleDateFormat DATE_DD_MM_YYYY = new SimpleDateFormat("dd-MM-yyyy");
	
	public static final List<Integer> SMA_DAYS = Arrays.asList(50, 200, 500, 700);
	public static final List<Integer> RSI_DAYS = Arrays.asList(30, 90, 200, 500, 700);
	public static final List<Integer> ROLLING_RETURN_DAYS = Arrays.asList(22, 66, 110, 176, 264, 396, 548, 792);
	
	public static final int MACD_SHORT = 15;
	public static final int MACD_LONG = 45;
	public static final int MACD_SIGNAL = 12;
}
