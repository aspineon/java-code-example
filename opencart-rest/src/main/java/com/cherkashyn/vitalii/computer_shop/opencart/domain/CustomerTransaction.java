package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * OcCustomerTransaction generated by hbm2java
 */
@Entity
@Table(name = "oc_customer_transaction", catalog = "opencart")
public class CustomerTransaction implements java.io.Serializable {

	private Integer customerTransactionId;
	private int customerId;
	private int orderId;
	private String description;
	private BigDecimal amount;
	private Date dateAdded;

	public CustomerTransaction() {
	}

	public CustomerTransaction(int customerId, int orderId,
			String description, BigDecimal amount, Date dateAdded) {
		this.customerId = customerId;
		this.orderId = orderId;
		this.description = description;
		this.amount = amount;
		this.dateAdded = dateAdded;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "customer_transaction_id", unique = true, nullable = false)
	public Integer getCustomerTransactionId() {
		return this.customerTransactionId;
	}

	public void setCustomerTransactionId(Integer customerTransactionId) {
		this.customerTransactionId = customerTransactionId;
	}

	@Column(name = "customer_id", nullable = false)
	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Column(name = "order_id", nullable = false)
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Column(name = "description", nullable = false, length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "amount", nullable = false, precision = 15, scale = 4)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_added", nullable = false, length = 19)
	public Date getDateAdded() {
		return this.dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}
