package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface ReleaseCup extends BusinessObject{

	public abstract void setRegressionDays(String regressionDays);

	public abstract String getRegressionDays();

	public abstract void setDevDays(String devDays);

	public abstract String getDevDays();

	public abstract void setAvailableDevDays(String availableDevDays);

	public abstract String getAvailableDevDays();

	public abstract void setLabUuid(String labUuid);

	public abstract String getLabUuid();

	public abstract void setReleaseUuid(String releaseUuid);

	public abstract String getReleaseUuid();

	public abstract void setSysComponents(String sysComponents);

	public abstract String getSysComponents();

	public abstract void setMatrixJson(String matrixJson);

	public abstract String getMatrixJson();

	public abstract void setLastClicked(String lastClicked);

	public abstract String getLastClicked();

	public abstract void setIpmTree(String ipmTree);

	public abstract String getIpmTree();

}
