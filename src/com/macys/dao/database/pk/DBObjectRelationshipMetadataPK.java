package com.macys.dao.database.pk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;

import org.springframework.util.ObjectUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

//@Embeddable
//@Configurable
public final class DBObjectRelationshipMetadataPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "p_uuid", nullable = false, length=32)
    private String pUuid;

	@Column(name = "c_uuid", nullable = false, length=32)
    private String cUuid;

	@Column(name = "name", nullable = false, length = 255)
    private String name;

	@Column(name = "relationship_type", nullable = false, length=32)
    private String relationshipType;
	
	public DBObjectRelationshipMetadataPK() {
	}

	public DBObjectRelationshipMetadataPK(String pUuid, String cUuid, String name, String relationshipType) {
        super();
        this.pUuid = pUuid;
        this.cUuid = cUuid;
        this.name = name;
        this.relationshipType = relationshipType;
    }
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObjectRelationshipMetadataPK) {
			DBObjectRelationshipMetadataPK that = (DBObjectRelationshipMetadataPK) obj;
			
			return ObjectUtils.nullSafeEquals(this.pUuid, that.pUuid) && 
					ObjectUtils.nullSafeEquals(this.cUuid, that.cUuid) &&
					ObjectUtils.nullSafeEquals(this.name, that.name) &&
					ObjectUtils.nullSafeEquals(this.relationshipType, that.relationshipType);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.pUuid) + ObjectUtils.nullSafeHashCode(this.cUuid);
	}

	public String getPUuid() {
        return pUuid;
    }

	public String getCUuid() {
        return cUuid;
    }

	public String getName() {
        return name;
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

	public static DBObjectRelationshipMetadataPK fromJsonToDbobjectRelationshipMetadataPK(String json) {
        return new JSONDeserializer<DBObjectRelationshipMetadataPK>()
        .use(null, DBObjectRelationshipMetadataPK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<DBObjectRelationshipMetadataPK> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

	public static String toJsonArray(Collection<DBObjectRelationshipMetadataPK> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

	public static Collection<DBObjectRelationshipMetadataPK> fromJsonArrayToDbobjectRelationshipMetadataPKs(String json) {
        return new JSONDeserializer<List<DBObjectRelationshipMetadataPK>>()
        .use("values", DBObjectRelationshipMetadataPK.class).deserialize(json);
    }

}
