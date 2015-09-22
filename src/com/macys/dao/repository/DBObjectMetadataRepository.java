package com.macys.dao.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.macys.dao.database.DBObjectMetadata;
import com.macys.dao.database.pk.DBObjectMetadataPK;

@Repository
@Transactional
public interface DBObjectMetadataRepository extends CrudRepository<DBObjectMetadata, DBObjectMetadataPK> {
	
	DBObjectMetadata findByPK(DBObjectMetadataPK pk);
	
	@Query(name="findByNameAndValue", value="from DBObjectMetadata o where o.pk.name=?1 and o.value=?2")
	List<DBObjectMetadata> findByNameAndValue(String name, String value);
	
	@Query(name="findMetadatasByUuid", value="from DBObjectMetadata o where o.pk.uuid=?1")
	List<DBObjectMetadata> findMetadatasByUuid(String uuid);
	
	@Modifying
	@Query(name="deleteMetadatasByUuid", value="delete DBObjectMetadata o where o.pk.uuid=?1")
	void deleteMetadatasByUuid(String uuid);

}
