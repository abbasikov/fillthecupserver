package com.macys.dao.database.pk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ObjectUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Embeddable
//@Configurable
public final class DBObjectRelationshipPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "p_uuid", nullable = false, length=32)
    private String pUuid;

	@Column(name = "c_uuid", nullable = false, length=32)
    private String cUuid;

	@Column(name = "relationship_type", nullable = false, length=32)
    private String relationshipType;
	
	public DBObjectRelationshipPK() {
	}

	public DBObjectRelationshipPK(String pUuid, String cUuid, String relationshipType) {
        super();
        this.pUuid = pUuid;
        this.cUuid = cUuid;
        this.relationshipType = relationshipType;
    }
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObjectRelationshipPK) {
			DBObjectRelationshipPK that = (DBObjectRelationshipPK) obj;
			return ObjectUtils.nullSafeEquals(this.pUuid, that.pUuid) && 
					ObjectUtils.nullSafeEquals(this.cUuid, that.cUuid) &&
					ObjectUtils.nullSafeEquals(this.relationshipType, that.relationshipType);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.pUuid) + 
				ObjectUtils.nullSafeHashCode(this.cUuid) + 
				ObjectUtils.nullSafeHashCode(this.relationshipType);
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getPUuid() {
        return pUuid;
    }

	public String getCUuid() {
        return cUuid;
    }

	public String getRelationshipType() {
        return relationshipType;
    }


	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

	public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }

	public static DBObjectRelationshipPK fromJsonToDbobjectRelationshipPK(String json) {
        return new JSONDeserializer<DBObjectRelationshipPK>()
        .use(null, DBObjectRelationshipPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<DBObjectRelationshipPK> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<DBObjectRelationshipPK> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<DBObjectRelationshipPK> fromJsonArrayToDbobjectRelationshipPKs(String json) {
        return new JSONDeserializer<List<DBObjectRelationshipPK>>()
        .use("values", DBObjectRelationshipPK.class).deserialize(json);
    }
}
