package com.macys.dao.database.pk;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ObjectUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

//@Embeddable
//@Configurable
public final class DBQueuePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "reference_uuid", nullable = false, length=32)
	private String referenceUuid;

	@Column(name = "state", nullable = false, length=32)
	private String state;
	
	public DBQueuePK() {
	}

	public DBQueuePK(String uuid, String state) {
		super();
		this.referenceUuid = uuid;
		this.state = state;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBQueuePK) {
			DBQueuePK that = (DBQueuePK) obj;
			return ObjectUtils.nullSafeEquals(this.referenceUuid, that.referenceUuid) && ObjectUtils.nullSafeEquals(this.state, that.state);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.referenceUuid);
		
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String getReferenceUuid() {
		return referenceUuid;
	}

	public void setReferenceUuid(String uuid) {
		this.referenceUuid = uuid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static DBQueuePK fromJsonToDbobjectAclPK(String json) {
		return new JSONDeserializer<DBQueuePK>().use(null,
				DBQueuePK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<DBQueuePK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<DBQueuePK> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<DBQueuePK> fromJsonArrayToDbobjectAclPKs(
			String json) {
		return new JSONDeserializer<List<DBQueuePK>>().use("values",
				DBQueuePK.class).deserialize(json);
	}
}
