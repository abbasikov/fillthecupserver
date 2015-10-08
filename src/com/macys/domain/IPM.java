package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface IPM extends BusinessObject{

	public abstract void setMvpMatrixJson(String mvpMatrixJson);

	public abstract String getMvpMatrixJson();

}
