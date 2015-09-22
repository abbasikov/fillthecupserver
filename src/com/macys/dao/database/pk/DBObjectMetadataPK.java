package com.macys.dao.database.pk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ObjectUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


@Embeddable
public final class DBObjectMetadataPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "uuid", nullable = false, length=32)
    private String uuid;

	@Column(name = "name", nullable = false, length = 255)
    private String name;

	public DBObjectMetadataPK() {
	}
	
	public DBObjectMetadataPK(String uuid, String name) {
        super();
        this.uuid = uuid;
        this.name = name;
    }
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObjectMetadataPK) {
			DBObjectMetadataPK that = (DBObjectMetadataPK) obj;
			return ObjectUtils.nullSafeEquals(this.uuid, that.uuid) && ObjectUtils.nullSafeEquals(this.name, that.name);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.uuid) + ObjectUtils.nullSafeHashCode(this.name);
	}

	public String getUuid() {
        return uuid;
    }

	public String getName() {
        return name;
    }
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static DBObjectMetadataPK fromJsonToDbobjectMetadataPK(String json) {
        return new JSONDeserializer<DBObjectMetadataPK>()
        .use(null, DBObjectMetadataPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<DBObjectMetadataPK> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<DBObjectMetadataPK> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<DBObjectMetadataPK> fromJsonArrayToDbobjectMetadataPKs(String json) {
        return new JSONDeserializer<List<DBObjectMetadataPK>>()
        .use("values", DBObjectMetadataPK.class).deserialize(json);
    }
}
