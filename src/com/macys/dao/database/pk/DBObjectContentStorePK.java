package com.macys.dao.database.pk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;

import org.springframework.util.ObjectUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

//@Configurable
//@Embeddable
public final class DBObjectContentStorePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "uuid", nullable = false, length=32)
    private String uuid;

	@Column(name = "content_type", nullable = false, length=32)
    private String contentType;

	public DBObjectContentStorePK() {
	}
	
	public DBObjectContentStorePK(String uuid, String contentType) {
        super();
        this.uuid = uuid;
        this.contentType = contentType;
    }
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof DBObjectContentStorePK) {
			DBObjectContentStorePK that = (DBObjectContentStorePK) obj;
			return ObjectUtils.nullSafeEquals(this.uuid, that.uuid) && ObjectUtils.nullSafeEquals(this.contentType, that.contentType);
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.uuid);
	}

	public String getUuid() {
        return uuid;
    }

	public String getContentType() {
        return contentType;
    }

	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static DBObjectContentStorePK fromJsonToDbobjectContentStorePK(String json) {
        return new JSONDeserializer<DBObjectContentStorePK>()
        .use(null, DBObjectContentStorePK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<DBObjectContentStorePK> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<DBObjectContentStorePK> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<DBObjectContentStorePK> fromJsonArrayToDbobjectContentStorePKs(String json) {
        return new JSONDeserializer<List<DBObjectContentStorePK>>()
        .use("values", DBObjectContentStorePK.class).deserialize(json);
    }
}
