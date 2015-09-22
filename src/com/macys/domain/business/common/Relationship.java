package com.macys.domain.business.common;


import org.springframework.util.ObjectUtils;

import com.macys.dao.database.DBObjectRelationship;
import com.macys.dao.database.pk.DBObjectRelationshipPK;

public class Relationship {
	
	private DBObjectRelationship relationship;
	
	public Relationship(String parentUuid, String childUuid, String type, String createdBy) {
		this.relationship = new DBObjectRelationship(new DBObjectRelationshipPK(parentUuid, childUuid, type), createdBy);
	}
	
	public Relationship(DBObjectRelationship relationship) {
		this.relationship = relationship;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Relationship) {
			Relationship that = (Relationship) obj;
			return ObjectUtils.nullSafeEquals(this.relationship, that.relationship);
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.nullSafeHashCode(this.relationship);
	}

	public String getParentUuid() {
		return this.relationship.getPk().getPUuid();
	}
	
	public String getChildUuid() {
		return this.relationship.getPk().getCUuid();
	}
	
	public String getType() {
		return this.relationship.getPk().getRelationshipType();
	}
	

}
