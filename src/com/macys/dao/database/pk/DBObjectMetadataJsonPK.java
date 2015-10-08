package com.macys.dao.database.pk;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.ObjectUtils;

import flexjson.JSONSerializer;


@Embeddable
public final class DBObjectMetadataJsonPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "uuid", nullable = false, length=32)
    private String uuid;

	@Column(name = "property_name", nullable = false, length = 255)
    private String property_name;

	public DBObjectMetadataJsonPK() {
	}
	
	public DBObjectMetadataJsonPK(String uuid, String propertyName) {
        super();
        this.uuid = uuid;
        this.property_name = propertyName;
    }
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof DBObjectMetadataJsonPK) {
			DBObjectMetadataJsonPK that = (DBObjectMetadataJsonPK) obj;
			return ObjectUtils.nullSafeEquals(this.uuid, that.uuid) && ObjectUtils.nullSafeEquals(this.property_name, that.property_name);
		}
		
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.uuid) + ObjectUtils.nullSafeHashCode(this.property_name);
	}

	public String getUuid() {
        return uuid;
    }

	public String getProperty_name() {
		return property_name;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }	
}
