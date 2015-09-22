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

import com.macys.dao.database.pk.DBObjectACLPK;

@Entity
@Table(name = "dbobject_acl")
@NamedQuery(name = "DBObjectACL.findByPK", query = "from DBObjectACL o where o.pk = ?1")
public class DBObjectACL extends DBAbstractBase {

	private static final long serialVersionUID = 1L;
	
//	@Column(name="id", unique=true, insertable=false, updatable=false)
//	private Integer counter;

	@EmbeddedId
    private DBObjectACLPK pk;

//	@ManyToOne
//    @JoinColumn(name = "uuid", referencedColumnName = "uuid", nullable = false, insertable = false, updatable = false)
//    private DBObject object;

	@Column(name = "permission", length=32)
    @NotNull
    private String permission;

	@Column(name = "created_by", length=32)
    @NotNull
    private String createdBy;

	@Column(name = "created_on")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
	
	public DBObjectACL() {
		this(null);
		this.createdOn = new Date();
	}
	
	public DBObjectACL(DBObjectACLPK id) {
		this.pk = id;
		this.createdOn = new Date();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObjectACL) {
			DBObjectACL that = (DBObjectACL) obj;
			return ObjectUtils.nullSafeEquals(this.pk, that.pk);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.pk);
	}
	
	public DBObjectACLPK getPk() {
		return pk;
	}

	public void setPk(DBObjectACLPK pk) {
		this.pk = pk;
	}
	
//	public DBObject getObject() {
//		return object;
//	}
//
//	public void setObject(DBObject object) {
//		this.object = object;
//	}

	public String getPermission() {
        return permission;
    }

	public void setPermission(String permission) {
        this.permission = permission;
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
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(new String[]{"uuid"}).toString();
    }
	
}
