package com.macys.dao.database;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ObjectUtils;

import com.macys.dao.database.pk.DBObjectRelationshipPK;

@Entity
@Table(name = "dbobject_relationship")
@NamedQuery(name = "DBObjectRelationship.findByPK", query = "from DBObjectRelationship a where a.pk = ?1")
public class DBObjectRelationship extends DBAbstractBase  {
	
	private static final long serialVersionUID = 1L;
	
//	@Id
//	@Column(name="id", unique=true, insertable=false, updatable=false)
//	private Integer counter;
	
	@Column(name = "client_id")
	@NotNull
	private Short clientId = 0;

	@EmbeddedId
	private DBObjectRelationshipPK pk;

	@Column(name = "sortorder")
    private Short sortorder;

	@Column(name = "created_by", length=32)
    @NotNull
    private String createdBy;

	@Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	@Column(name = "status")
    private Short status = 0;

	@Column(name = "status_modified_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusModifiedOn;
	
	public DBObjectRelationship() {
		this.createdOn = new Date();
	}
	
	public DBObjectRelationship(DBObjectRelationshipPK id) {
		this.pk = id;
		this.createdOn = new Date();
	}
	
	public DBObjectRelationship(DBObjectRelationshipPK id, String createdBy) {
		this.pk = id;
		this.createdBy = createdBy;
		this.createdOn = new Date();
	}
	
//	@Override
//	public DBObjectRelationshipPK getId() {
//		return this.pk;
//	}
//	
//	@Override
//	protected void setId(DBObjectRelationshipPK id) {
//		this.pk = (DBObjectRelationshipPK) id;
//	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObjectRelationship) {
			DBObjectRelationship that = (DBObjectRelationship) obj;
			return ObjectUtils.nullSafeEquals(this.pk, that.pk);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.pk);
	}
	
//	@ManyToOne
//  @JoinColumn(name = "p_uuid", referencedColumnName = "uuid", nullable = false, insertable = false, updatable = false)
//  private DBObject pUuid;
//
//	@ManyToOne
//  @JoinColumn(name = "c_uuid", referencedColumnName = "uuid", nullable = false, insertable = false, updatable = false)
//  private DBObject cUuid;


	public Short getSortorder() {
        return sortorder;
    }

	public void setSortorder(Short sortorder) {
        this.sortorder = sortorder;
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

	public DBObjectRelationshipPK getPk() {
		return pk;
	}

	public void setPk(DBObjectRelationshipPK pk) {
		this.pk = pk;
	}

	public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(new String[]{"pUuid", "cUuid"}).toString();
    }
}
