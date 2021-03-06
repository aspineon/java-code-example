package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OcOrderVoucher generated by hbm2java
 */
@Entity
@Table(name = "oc_order_voucher", catalog = "opencart")
public class OrderVoucher implements java.io.Serializable {

	private Integer orderVoucherId;
	private int orderId;
	private int voucherId;
	private String description;
	private String code;
	private String fromName;
	private String fromEmail;
	private String toName;
	private String toEmail;
	private int voucherThemeId;
	private String message;
	private BigDecimal amount;

	public OrderVoucher() {
	}

	public OrderVoucher(int orderId, int voucherId, String description,
			String code, String fromName, String fromEmail, String toName,
			String toEmail, int voucherThemeId, String message,
			BigDecimal amount) {
		this.orderId = orderId;
		this.voucherId = voucherId;
		this.description = description;
		this.code = code;
		this.fromName = fromName;
		this.fromEmail = fromEmail;
		this.toName = toName;
		this.toEmail = toEmail;
		this.voucherThemeId = voucherThemeId;
		this.message = message;
		this.amount = amount;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "order_voucher_id", unique = true, nullable = false)
	public Integer getOrderVoucherId() {
		return this.orderVoucherId;
	}

	public void setOrderVoucherId(Integer orderVoucherId) {
		this.orderVoucherId = orderVoucherId;
	}

	@Column(name = "order_id", nullable = false)
	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Column(name = "voucher_id", nullable = false)
	public int getVoucherId() {
		return this.voucherId;
	}

	public void setVoucherId(int voucherId) {
		this.voucherId = voucherId;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "code", nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "from_name", nullable = false, length = 64)
	public String getFromName() {
		return this.fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	@Column(name = "from_email", nullable = false, length = 96)
	public String getFromEmail() {
		return this.fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	@Column(name = "to_name", nullable = false, length = 64)
	public String getToName() {
		return this.toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	@Column(name = "to_email", nullable = false, length = 96)
	public String getToEmail() {
		return this.toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	@Column(name = "voucher_theme_id", nullable = false)
	public int getVoucherThemeId() {
		return this.voucherThemeId;
	}

	public void setVoucherThemeId(int voucherThemeId) {
		this.voucherThemeId = voucherThemeId;
	}

	@Column(name = "message", nullable = false, length = 65535)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "amount", nullable = false, precision = 15, scale = 4)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
