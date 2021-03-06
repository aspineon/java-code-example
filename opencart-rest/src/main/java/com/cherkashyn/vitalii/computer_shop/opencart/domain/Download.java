package com.cherkashyn.vitalii.computer_shop.opencart.domain;

// Generated Sep 27, 2013 3:55:56 AM by Hibernate Tools 3.4.0.CR1

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
 * OcDownload generated by hbm2java
 */
@Entity
@Table(name = "oc_download", catalog = "opencart")
public class Download implements java.io.Serializable {

	private Integer downloadId;
	private String filename;
	private String mask;
	private int remaining;
	private Date dateAdded;

	public Download() {
	}

	public Download(String filename, String mask, int remaining,
			Date dateAdded) {
		this.filename = filename;
		this.mask = mask;
		this.remaining = remaining;
		this.dateAdded = dateAdded;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "download_id", unique = true, nullable = false)
	public Integer getDownloadId() {
		return this.downloadId;
	}

	public void setDownloadId(Integer downloadId) {
		this.downloadId = downloadId;
	}

	@Column(name = "filename", nullable = false, length = 128)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "mask", nullable = false, length = 128)
	public String getMask() {
		return this.mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	@Column(name = "remaining", nullable = false)
	public int getRemaining() {
		return this.remaining;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
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
