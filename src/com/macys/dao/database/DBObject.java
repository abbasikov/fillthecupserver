package com.macys.dao.database;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ObjectUtils;

import com.macys.domain.business.common.BusinessObjectTypeEnum;

@Entity
@Table(name = "dbobject")
public class DBObject extends DBAbstractBase {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="counter", unique=true, insertable=false, updatable=false)
	private Integer counter;
	
	@Column(name = "client_id")
	@NotNull
	private Short clientId = 0; //default value is 0
	
	@Column(name = "type", length=5)
	@NotNull
	private String type;
	
	@Id
	@Column(name="uuid", unique=true, updatable=false, length=32)
	@NotNull
    private String uuid;

	@Version
    @Column(name = "version")
    private Integer version = 0; //default value is 0

	@Column(name = "name", length = 255)
    private String name;

	@Column(name = "created_by", length=32)
    @NotNull
    private String createdBy;

	@Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

	@Column(name = "modified_by", length=32)
    private String modifiedBy;

	@Column(name = "modified_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedOn;

	@Column(name = "status")
    private Short status;

	@Column(name = "status_modified_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusModifiedOn;
	
	@OneToMany(mappedBy="object", fetch=FetchType.EAGER)
	private Set<DBObjectMetadata> metadatas;
	
	public DBObject() {
		this.createdOn = new Date();
	}
	
	public DBObject(BusinessObjectTypeEnum type) {
		this.type = type.getType();
		setUuid(type.newRandomUuid());
		this.createdOn = new Date();
	}
	
	public DBObject(BusinessObjectTypeEnum type, String name, String createdBy) {
		setUuid(type.newRandomUuid());
//		this.clientId = 0;
		this.type = type.getType();
		this.name = name;
		this.createdBy = createdBy;
		this.status = 0;
		this.createdOn = new Date();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObject) {
			return ObjectUtils.nullSafeEquals(this.uuid, ((DBObject)obj).uuid);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.uuid);
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getStatusModifiedOn() {
		return statusModifiedOn;
	}

	public void setStatusModifiedOn(Date statusModifiedOn) {
		this.statusModifiedOn = statusModifiedOn;
	}

	public Set<DBObjectMetadata> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(Set<DBObjectMetadata> metadatas) {
		this.metadatas = metadatas;
	}
	
	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(new String[]{""}).toString();
    }
	
}
