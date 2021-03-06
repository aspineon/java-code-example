package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcProductAttribute generated by hbm2java
 */
@Entity
@Table(name = "oc_product_attribute", catalog = "opencart")
public class ProductAttribute implements java.io.Serializable {

	private ProductAttributeId id;
	private String text;

	public ProductAttribute() {
	}

	public ProductAttribute(ProductAttributeId id, String text) {
		this.id = id;
		this.text = text;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false)),
			@AttributeOverride(name = "attributeId", column = @Column(name = "attribute_id", nullable = false)),
			@AttributeOverride(name = "languageId", column = @Column(name = "language_id", nullable = false)) })
	public ProductAttributeId getId() {
		return this.id;
	}

	public void setId(ProductAttributeId id) {
		this.id = id;
	}

	@Column(name = "text", nullable = false, length = 65535)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
