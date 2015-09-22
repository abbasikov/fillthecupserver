package com.macys.dao.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.macys.dao.database.pk.DBObjectContentStorePK;

@Entity
@Table(name = "dbobject_content_store")
public class DBObjectContentStore extends DBAbstractBase {
	
	private static final long serialVersionUID = 1L;
	
//	@Column(name="id", unique=true, insertable=false, updatable=false)
//	private Integer counter;

	@EmbeddedId
	private DBObjectContentStorePK pk;

	@Column(name = "uri", length = 255)
	private String uri;

	@Column(name = "data")
	private byte[] data;

	@Column(name = "created_by", length=32)
	@NotNull
	private String createdBy;

	@Column(name = "created_on")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "extension", length=32)
	private String extension;

	@Column(name = "content_size")
	private Integer contentSize;

	@Column(name = "content_size_enc")
	private Integer contentSizeEnc;

	@Column(name = "checksum", length=32, nullable=true)
	private String checksum;
	
	public DBObjectContentStore() {
		this(null);
		this.createdOn = new Date();
	}
	
	public DBObjectContentStore(DBObjectContentStorePK id) {
		this.pk = id;
		this.createdOn = new Date();
	}
	
	public DBObjectContentStorePK getPk() {
		return pk;
	}

	public void setPk(DBObjectContentStorePK pk) {
		this.pk = pk;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Integer getContentSize() {
		return contentSize;
	}

	public void setContentSize(Integer contentSize) {
		this.contentSize = contentSize;
	}

	public Integer getContentSizeEnc() {
		return contentSizeEnc;
	}

	public void setContentSizeEnc(Integer contentSizeEnc) {
		this.contentSizeEnc = contentSizeEnc;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
