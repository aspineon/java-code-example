package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OcCustomerGroupDescriptionId generated by hbm2java
 */
@Embeddable
public class CustomerGroupDescriptionId implements java.io.Serializable {

	private int customerGroupId;
	private int languageId;

	public CustomerGroupDescriptionId() {
	}

	public CustomerGroupDescriptionId(int customerGroupId, int languageId) {
		this.customerGroupId = customerGroupId;
		this.languageId = languageId;
	}

	@Column(name = "customer_group_id", nullable = false)
	public int getCustomerGroupId() {
		return this.customerGroupId;
	}

	public void setCustomerGroupId(int customerGroupId) {
		this.customerGroupId = customerGroupId;
	}

	@Column(name = "language_id", nullable = false)
	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CustomerGroupDescriptionId))
			return false;
		CustomerGroupDescriptionId castOther = (CustomerGroupDescriptionId) other;

		return (this.getCustomerGroupId() == castOther.getCustomerGroupId())
				&& (this.getLanguageId() == castOther.getLanguageId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCustomerGroupId();
		result = 37 * result + this.getLanguageId();
		return result;
	}

}
