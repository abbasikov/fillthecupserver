package com.macys.dao.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.macys.dao.database.DBObject;

@Repository
@Transactional
public interface DBObjectRepository extends CrudRepository<DBObject, String> {
	
	DBObject findByUuid(String uuid);
	
	List<DBObject> findByTypeAndName(String type, String name);
	
	@Query("select u from DBObject u where u.uuid = ?1")
	DBObject findUsingPK(String uuid);
	
	@Query("select u from DBObject u where u.uuid in ?1")
	List<DBObject> findUsingUuids(List<String> uuids);
	
	
}
