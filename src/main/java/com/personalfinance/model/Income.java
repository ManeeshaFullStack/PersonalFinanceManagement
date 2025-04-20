package com.personalfinance.model;

import java.sql.Date;

public class Income {
	private int incomeId;
    private String source;
    private double amount;
    private String category;
    private Date receivedDate;

    // Getters and Setters
    public int getIncomeId() { return incomeId; }
    public void setIncomeId(int incomeId) { this.incomeId = incomeId; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Date getReceivedDate() { return receivedDate; }
    public void setReceivedDate(Date receivedDate) { this.receivedDate = receivedDate; }
}
