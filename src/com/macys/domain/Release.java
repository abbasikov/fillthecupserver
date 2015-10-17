package com.macys.domain;

import com.macys.domain.business.BusinessObject;

public interface Release extends BusinessObject {

	public abstract void setBcomDate(String bcomDate);

	public abstract String getBcomDate();

	public abstract void setMcomDate(String mcomDate);

	public abstract String getMcomDate();

	public abstract void setBranchHardLockDate(String branchHardLockDate);

	public abstract String getBranchHardLockDate();

	public abstract void setBranchCutDate(String branchCutDate);

	public abstract String getBranchCutDate();

	public abstract void setBranchProductionDate(String branchProductionDate);

	public abstract String getBranchProductionDate();

	public abstract void setBranchFreezeDate(String branchFreezeDate);

	public abstract String getBranchFreezeDate();

	public abstract void setIsActivated(String isActivated);

	public abstract String getIsActivated();

}
