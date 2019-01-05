package com.ts.olx.dto;

import java.util.Date;

public class Interest {

	private Item item;
	private User expressedBy;
	private Date expressedDate;
	private double bidAmount;
	private boolean bidStatus;
	
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public User getExpressedBy() {
		return expressedBy;
	}

	public void setExpressedBy(User expressedBy) {
		this.expressedBy = expressedBy;
	}

	public Date getExpressedDate() {
		return expressedDate;
	}

	public void setExpressedDate(Date expressedDate) {
		this.expressedDate = expressedDate;
	}

	public double getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}

	public boolean isBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(boolean bidStatus) {
		this.bidStatus = bidStatus;
	}
	
	

}
