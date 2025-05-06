package com.erencsahin.rest.dto;

public class RateData {
    private String symbol;
    private double ask;
    private double bid;
    private String timestamp;

   public RateData(String symbol, double ask, double bid, String timestamp) {
        this.symbol = symbol;
        this.ask = ask;
        this.bid = bid;
        this.timestamp = timestamp;
   }

   public String getSymbol() {
       return symbol;
   }

   public double getAsk() {
       return ask;
   }

   public double getBid() {
       return bid;
   }

   public String getTimestamp() {
       return timestamp;
   }
}
