package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * OcProductToLayout generated by hbm2java
 */
@Entity
@Table(name = "oc_product_to_layout", catalog = "opencart")
public class ProductToLayout implements java.io.Serializable {

	private ProductToLayoutId id;
	private int layoutId;

	public ProductToLayout() {
	}

	public ProductToLayout(ProductToLayoutId id, int layoutId) {
		this.id = id;
		this.layoutId = layoutId;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "productId", column = @Column(name = "product_id", nullable = false)),
			@AttributeOverride(name = "storeId", column = @Column(name = "store_id", nullable = false)) })
	public ProductToLayoutId getId() {
		return this.id;
	}

	public void setId(ProductToLayoutId id) {
		this.id = id;
	}

	@Column(name = "layout_id", nullable = false)
	public int getLayoutId() {
		return this.layoutId;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}

}
