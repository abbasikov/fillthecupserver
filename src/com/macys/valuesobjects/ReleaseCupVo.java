package com.macys.valuesobjects;

import java.util.List;

public class ReleaseCupVo extends BaseDTO {

	public ReleaseVo release;
	
	public LabVo lab;
	
	public String availableDevDays;
	
	public String devDays;
	
	public String regressionDays;
	
	public List<SystemComponentVo> sysComponents;
	
	public String matrix;
	
	public String lastClicked;

	public String ipmTree;
}
