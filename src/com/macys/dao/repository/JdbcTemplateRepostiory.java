package com.macys.dao.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import com.macys.dao.DAOUtils;
import com.macys.dao.database.DBObject;
import com.macys.domain.business.common.BusinessObjectTypeEnum;


public class JdbcTemplateRepostiory extends JdbcDaoSupport {
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public JdbcTemplateRepostiory(){ 
		
	}
	
	public DBObject findByUuid(String uuid) {
		
		String sql = "select * from dbobject where uuid = ?";
		
		DBObject dbObject = this.getJdbcTemplate().queryForObject(sql, new Object[]{uuid}, new BeanPropertyRowMapper<DBObject>(DBObject.class));
		
		return dbObject;
	}
	
	public DBObject findByTypeAndName(BusinessObjectTypeEnum type,String name){
		
		String sql = "select * from dbobjecct where type=? and name=?";
		
		DBObject dbObject = this.getJdbcTemplate().queryForObject(sql, new Object[]{type.getType(),name}, new BeanPropertyRowMapper<DBObject>(DBObject.class));
		
		return dbObject;
	}
	
	public List<String> findUuidsByType(BusinessObjectTypeEnum type){
		String sql = "select distinct uuid from dbobject where type=?";
		
		List<String> uuids = this.getJdbcTemplate().queryForList(sql, new Object[]{type.getType()}, String.class);
		
		return uuids;
	}
	
	public List<String> findUuidsByMetadata(BusinessObjectTypeEnum type, String name, String value) {
		
		String sql = "select distinct uuid from dbobject_metadata where type=? and name = ? and value = ?";
		
		List<String> uuids = this.getJdbcTemplate().queryForList(sql, new Object[]{type.getType(), name, value}, String.class);
		
		return uuids;
		
	}
	
	@Transactional
	public int updateMetadataByUuid(String uuid, String type, String name, String value){
		
		try{
//			String selectCheck = "select dbobject_metadata where type=? and uuid = ? and name = ?";
//			List<Map<String,Object>> list =  this.getJdbcTemplate().queryForList(selectCheck, new Object[]{value,type,uuid,name});
//			if(list==null || list.size() <1){
//				String insertSql = 
//			}
//			else{
				String sql = "update dbobject_metadata set value=? where type=? and uuid = ? and name = ?";
				
				int rowsEffected =  this.getJdbcTemplate().update(sql, new Object[]{value,type,uuid,name});
				
				return rowsEffected;
			//}
			
		}
		catch(Exception exc){
			//ignore expcetion
		}
		
		return -1;
		
	}
	
	public List<String> findChildUuids(String parentUuid, BusinessObjectTypeEnum[] objectTypeEnums) {
		
		if(objectTypeEnums == null) {
			String sql = "select c_uuid from dbobject_relationship where p_uuid = ? ";
			
			List<String> uuids = this.getJdbcTemplate().queryForList(sql, new Object[]{parentUuid}, String.class);
			
			return uuids;
		} else {
			
			String sql = "select c_uuid from dbobject_relationship where p_uuid = :pUuid and substring(c_uuid, 1, 5) in (:types)  ";
			List<String> types = DAOUtils.asListOfStrings(objectTypeEnums);
		
			
			Map<String, List<String>> paramMap = new HashMap<String, List<String>>();
			paramMap.put("pUuid", Arrays.asList(new String[]{parentUuid}));
			paramMap.put("types", types);
			
			List<String> uuids = this.namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
			
			return uuids;
			
		}
		
	}

}
