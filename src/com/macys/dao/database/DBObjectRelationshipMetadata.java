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
import org.springframework.util.ObjectUtils;

import com.macys.dao.database.pk.DBObjectRelationshipMetadataPK;

@Entity
@Table(name = "dbobject_relationship_metadata")
public class DBObjectRelationshipMetadata extends DBAbstractBase {

	private static final long serialVersionUID = 1L;
	
//	@Column(name="id", unique=true, insertable=false, updatable=false)
//	private Integer counter;

	@EmbeddedId
	private DBObjectRelationshipMetadataPK pk;

	@Column(name = "value")
	private String value;

	@Column(name = "created_by", length=32)
	@NotNull
	private String createdBy;

	@Column(name = "created_on_")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	public DBObjectRelationshipMetadata() {
		this(null);
		this.createdOn = new Date();
	}
	
	public DBObjectRelationshipMetadata(DBObjectRelationshipMetadataPK id) {
		this.pk = id;
		this.createdOn = new Date();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof DBObjectRelationshipMetadata) {
			DBObjectRelationshipMetadata that = (DBObjectRelationshipMetadata) obj;

			return ObjectUtils.nullSafeEquals(this.pk, that.pk);
		}

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.pk);
	}

	public DBObjectRelationshipMetadataPK getPk() {
		return pk;
	}

	public void setPk(DBObjectRelationshipMetadataPK pk) {
		this.pk = pk;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}