package com.macys.dao.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.macys.dao.database.DBObjectRelationship;
import com.macys.dao.database.pk.DBObjectRelationshipPK;

@Repository
@Transactional
public interface DBObjectRelationshipRepository extends CrudRepository<DBObjectRelationship, DBObjectRelationshipPK> {
	
	DBObjectRelationship findByPK(DBObjectRelationshipPK pk);
	
	@Query("select u from DBObjectRelationship u where u.pk.pUuid = ?1")
	List<DBObjectRelationship> findChildren(String parentUuid);
	
	@Query("select u from DBObjectRelationship u where u.pk.pUuid = ?1 and u.pk.relationshipType = ?2")
	List<DBObjectRelationship> findChildrenWithRelationshipType(String parentUuid, String relationshipType);
	
}