package com.macys.dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.macys.dao.database.DBObject;
import com.macys.dao.database.DBObjectMetadata;
import com.macys.dao.database.pk.DBObjectMetadataPK;
import com.macys.domain.business.BusinessObject;
import com.macys.domain.business.BusinessObjectImpl;
import com.macys.domain.business.common.BusinessObjectTypeEnum;
import com.macys.domain.business.common.annotations.PersistentBusinessObject;
import com.macys.domain.business.common.annotations.PersistentMetadata;
import com.macys.exceptions.ErrorCodeEnum;
import com.macys.exceptions.ServiceException;


public class ReflectionUtils {
	
	static Reflections reflections = new Reflections("com.macys");
	
	static Set<Class<? extends BusinessObjectImpl>> subTypesOfBusinessObjectImpl = reflections.getSubTypesOf(BusinessObjectImpl.class);
	
	public static BusinessObject constructBusinessObject(BusinessObjectTypeEnum type) throws ServiceException {
		return constructBusinessObject(type, new DBObject(type));
	}
	
	public static BusinessObject constructBusinessObject(BusinessObjectTypeEnum type, DBObject dbObject) throws ServiceException {
		
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(PersistentBusinessObject.class);
		
		Class<?> clazzOfBO = null;
		for(Class<?> clazz:classes) {
			for(Annotation annotation:clazz.getAnnotations()) {
				if(annotation instanceof PersistentBusinessObject) {
					PersistentBusinessObject annotation2 = (PersistentBusinessObject) annotation;
					if(annotation2.type() == type) {
						clazzOfBO = clazz;
						break;
					}
				}
			}
			
			if(clazzOfBO != null) {
				break;
			}
		}
		
		if(clazzOfBO == null) {
			//clazzOfBO = TestBOImpl.class;
			return null;
		}
		
		BusinessObject bo = null;
		try {
			Constructor<?> ctor = clazzOfBO.getDeclaredConstructor(DBObject.class);
			ctor.setAccessible(true);
			bo = (BusinessObject) ctor.newInstance(dbObject);
			((BusinessObjectImpl)bo).setType(type.getType());
			
		} catch (InstantiationException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		} 
		
		if(bo == null) {
			throw new ServiceException("Error init BusinessObject type=" + type+ ". Make Sure the BusinessObject is annotated with OTPersistentBusinessObject",ErrorCodeEnum.MISSING_ANNOTATION);
		}
		
		return bo;
	}
	
	public static List<PropertyDescriptor> beanPropertyDescriptorsWithPersistentMetadataAnnotation(BusinessObject businessObject) throws IntrospectionException {
		
		List<PropertyDescriptor> getterSettorMethodDescriptors = new ArrayList<PropertyDescriptor>();
		
		Field[] fields = businessObject.getClass().getDeclaredFields();
		
		for(Field field:fields) {
			if(field.isAnnotationPresent(PersistentMetadata.class)) {
				if(String.class.equals(field.getType())) {
					String name = field.getName();
					getterSettorMethodDescriptors.add(new PropertyDescriptor(name, businessObject.getClass()));
					
				}
			}
		}
		
		return getterSettorMethodDescriptors;

	}
	
	public static List<DBObjectMetadata> annotatedMetadata(BusinessObject businessObject) throws ServiceException {
		
		try {
			List<PropertyDescriptor> metadataPropertyDescriptors = beanPropertyDescriptorsWithPersistentMetadataAnnotation(businessObject);
			
			List<DBObjectMetadata> metadata = new ArrayList<DBObjectMetadata>();
			
			for(PropertyDescriptor propertyDescriptor:metadataPropertyDescriptors) {
				
				String name = propertyDescriptor.getName();
				
				try {
					Method getter = propertyDescriptor.getReadMethod();
					
					Object object = getter.invoke(businessObject);
					
					String value = (String) object;
					if(value != null) {
						
						DBObjectMetadataPK pk = new DBObjectMetadataPK(businessObject.getUuid(), name);
						DBObjectMetadata md = new DBObjectMetadata(pk, value, "");
						metadata.add(md);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("Make sure field " + name + " has public getter and setter",  e, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
				}
				
			}
			
			return metadata;
		} catch (IntrospectionException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		

	}
	
	/**
	 * 
	 * @param metadatas
	 * @param targetBusinessObject
	 * @param setMissingMetadataToNull
	 * @return
	 * @throws ServiceException 
	 */
	public static BusinessObject setMetadataToBusinessObject(Set<DBObjectMetadata> metadatas, BusinessObject targetBusinessObject, boolean setMissingMetadataToNull) throws ServiceException {
		
		if(metadatas == null) {
			metadatas = Collections.emptySet();
		}
		
		Map<String, DBObjectMetadata> nameValueMap = new HashMap<String, DBObjectMetadata>();
		
		for(DBObjectMetadata md:metadatas) {
			nameValueMap.put(md.getPk().getName(), md);
		}
		
		try {
			List<PropertyDescriptor> metadataPropertyDescriptors = beanPropertyDescriptorsWithPersistentMetadataAnnotation(targetBusinessObject);
			
			for(PropertyDescriptor propertyDescriptor:metadataPropertyDescriptors) {
				String name = propertyDescriptor.getName();
				DBObjectMetadata metadata = nameValueMap.get(name);
				Method setter = propertyDescriptor.getWriteMethod();
				
				String value = null;
				if(metadata != null) {
					value = metadata.getValue();
				} else {
					if(setMissingMetadataToNull == false) {
						continue;
					}
				}

				setter.invoke(targetBusinessObject, value);
			}
			
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e, ErrorCodeEnum.INTERNAL_SERVER_ERROR);
		}
		
		
		return targetBusinessObject;		
	}
	
}
