package com.macys.dao.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.ObjectUtils;

import com.macys.dao.database.pk.DBObjectMetadataPK;
import com.macys.domain.business.common.BusinessObjectTypeEnum;

@Entity
@Table(name = "dbobject_metadata")
@NamedQuery(name="DBObjectMetadata.findByPK", query="from DBObjectMetadata o where o.pk = ?1")
public class DBObjectMetadata extends DBAbstractBase {

	private static final long serialVersionUID = 1L;
	
//	@Column(name="id", unique=true, insertable=false, updatable=false)
//	private Integer counter;
	
	@Column(name = "client_id")
	@NotNull
	private Short clientId = 0;
	
	@Column(name = "type", length=5)
	private String type;

	@EmbeddedId
	private DBObjectMetadataPK pk;

	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name = "uuid", nullable = true, insertable = false, updatable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	private DBObject object;

	@Column(name = "value")
	private String value;

	@Column(name = "created_by", length=32)
	@NotNull
	private String createdBy;

	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	public DBObjectMetadata() {
		this(null);
		this.createdOn = new Date();
	}
	
	public DBObjectMetadata(DBObjectMetadataPK id) {
		this.pk = id;
		this.type = clipTypeFromUuid();
		this.createdOn = new Date();
	}
	
	public DBObjectMetadata(DBObjectMetadataPK id, String value, String createdBy) {
		this.pk = id;
		this.value = value;
		this.createdBy = createdBy;
		this.type = clipTypeFromUuid();
		this.createdOn = new Date();
	}
	
	private String clipTypeFromUuid() {
		
		if(this.pk != null && this.getPk().getUuid() != null) {
			for(BusinessObjectTypeEnum enum1:BusinessObjectTypeEnum.values()) {
				if(this.getPk().getUuid().startsWith(enum1.getType())) {
					return enum1.getType();
				}
			}
		}
		
		return null;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof DBObjectMetadata) {
			DBObjectMetadata that = (DBObjectMetadata) obj;
			return ObjectUtils.nullSafeEquals(this.pk, that.pk) && ObjectUtils.nullSafeEquals(this.value, that.value);
		}

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.pk);
	}
	
	public DBObject getObject() {
		return object;
	}

	public void setObject(DBObject object) {
		this.object = object;
	}

	public Short getClientId() {
		return clientId;
	}

	public DBObjectMetadataPK getPk() {
		return pk;
	}

	public void setPk(DBObjectMetadataPK pk) {
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

	public String getType() {
		return type;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
				new String[] { "uuid" }).toString();
	}
}
