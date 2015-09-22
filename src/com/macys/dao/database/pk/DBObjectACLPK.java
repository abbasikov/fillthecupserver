package com.macys.dao.database.pk;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import org.springframework.util.ObjectUtils;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public final class DBObjectACLPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "principal_uuid", nullable = false, length=32)
	private String principalUuid;

	@Column(name = "uuid", nullable = false, length=32)
	private String uuid;
	
	public DBObjectACLPK() {
	}

	public DBObjectACLPK(String principalUuid, String uuid) {
		super();
		this.principalUuid = principalUuid;
		this.uuid = uuid;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObjectACLPK) {
			DBObjectACLPK that = (DBObjectACLPK) obj;
			return ObjectUtils.nullSafeEquals(this.principalUuid, that.principalUuid) && ObjectUtils.nullSafeEquals(this.uuid, that.uuid);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.principalUuid) + ObjectUtils.nullSafeHashCode(this.uuid);
		
	}

	public String getPrincipalUuid() {
		return principalUuid;
	}

	public String getUuid() {
		return uuid;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toJson(String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(this);
	}

	public static DBObjectACLPK fromJsonToDbobjectAclPK(String json) {
		return new JSONDeserializer<DBObjectACLPK>().use(null,
				DBObjectACLPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<DBObjectACLPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static String toJsonArray(Collection<DBObjectACLPK> collection,
			String[] fields) {
		return new JSONSerializer().include(fields).exclude("*.class")
				.serialize(collection);
	}

	public static Collection<DBObjectACLPK> fromJsonArrayToDbobjectAclPKs(
			String json) {
		return new JSONDeserializer<List<DBObjectACLPK>>().use("values",
				DBObjectACLPK.class).deserialize(json);
	}
}
