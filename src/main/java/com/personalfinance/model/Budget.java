package com.personalfinance.model;

import java.sql.Date;

public class Budget {
	 private int budgetId;
	    private int userId;
	    private double amount;
	    private String description;
	    private Date startDate;
	    private Date endDate;

	    // Getters and Setters
	    public int getBudgetId() {
	        return budgetId;
	    }

	    public void setBudgetId(int budgetId) {
	        this.budgetId = budgetId;
	    }

	    public int getUserId() {
	        return userId;
	    }

	    public void setUserId(int userId) {
	        this.userId = userId;
	    }

	    public double getAmount() {
	        return amount;
	    }

	    public void setAmount(double amount) {
	        this.amount = amount;
	    }

	   

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public Date getStartDate() {
	        return startDate;
	    }

	    public void setStartDate(Date startDate) {
	        this.startDate = startDate;
	    }

	    public Date getEndDate() {
	        return endDate;
	    }

	    public void setEndDate(Date endDate) {
	        this.endDate = endDate;
	    }
}
