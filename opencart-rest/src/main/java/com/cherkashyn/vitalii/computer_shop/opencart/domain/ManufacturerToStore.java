package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcManufacturerToStore generated by hbm2java
 */
@Entity
@Table(name = "oc_manufacturer_to_store", catalog = "opencart")
public class ManufacturerToStore implements java.io.Serializable {

	private ManufacturerToStoreId id;

	public ManufacturerToStore() {
	}

	public ManufacturerToStore(ManufacturerToStoreId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "manufacturerId", column = @Column(name = "manufacturer_id", nullable = false)),
			@AttributeOverride(name = "storeId", column = @Column(name = "store_id", nullable = false)) })
	public ManufacturerToStoreId getId() {
		return this.id;
	}

	public void setId(ManufacturerToStoreId id) {
		this.id = id;
	}

}
