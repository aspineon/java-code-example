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
 * OcVoucherHistory generated by hbm2java
 */
@Entity
@Table(name = "oc_voucher_history", catalog = "opencart")
public class VoucherHistory implements java.io.Serializable {

	private Integer voucherHistoryId;
	private int voucherId;
	private int orderId;
	private BigDecimal amount;
	private Date dateAdded;

	public VoucherHistory() {
	}

	public VoucherHistory(int voucherId, int orderId, BigDecimal amount,
			Date dateAdded) {
		this.voucherId = voucherId;
		this.orderId = orderId;
		this.amount = amount;
		this.dateAdded = dateAdded;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "voucher_history_id", unique = true, nullable = false)
	public Integer getVoucherHistoryId() {
		return this.voucherHistoryId;
	}

	public void setVoucherHistoryId(Integer voucherHistoryId) {
		this.voucherHistoryId = voucherHistoryId;
	}

	@Column(name = "voucher_id", nullable = false)
	public int getVoucherId() {
		return this.voucherId;
	}

	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
	}

	@Column(name = "order_id", nullable = false)
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
