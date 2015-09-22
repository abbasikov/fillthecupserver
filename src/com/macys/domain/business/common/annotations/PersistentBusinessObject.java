package com.macys.domain.business.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.macys.domain.business.common.BusinessObjectTypeEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PersistentBusinessObject {
	
	BusinessObjectTypeEnum type();
	
}