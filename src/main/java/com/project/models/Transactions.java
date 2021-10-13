package com.project.models;

import java.io.Serializable;

public class Transactions implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int transaction_id;
	private String transaction_type;
	private int transaction_acc;
	private String transaction_status;
	
	public Transactions(int transaction_id, String transaction_type, int transaction_acc, String transaction_status) {
		super();
		this.transaction_id = transaction_id;
		this.transaction_type = transaction_type;
		this.transaction_acc = transaction_acc;
		this.transaction_status = transaction_status;
	}

	public Transactions(String transaction_type, int transaction_acc, String transaction_status) {
		super();
		this.transaction_type = transaction_type;
		this.transaction_acc = transaction_acc;
		this.transaction_status = transaction_status;
	}

	public int getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public int getTransaction_acc() {
		return transaction_acc;
	}

	public void setTransaction_acc(int transaction_acc) {
		this.transaction_acc = transaction_acc;
	}

	public String getTransaction_status() {
		return transaction_status;
	}

	public void setTransaction_status(String transaction_status) {
		this.transaction_status = transaction_status;
	}

	@Override
	public String toString() {
		return "Transactions [transaction_id=" + transaction_id + ", transaction_type=" + transaction_type
				+ ", transaction_acc=" + transaction_acc + ", transaction_status=" + transaction_status + "]";
	}

}
