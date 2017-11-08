/**
 * Copyright (c) 2008 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.service;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:该接口为web server 和App server传递业务等信息的载体</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author lk
 * @version 1.0
 * @date: 2008-05-20
 */
public interface IMessage extends java.io.Serializable
{
	
	public void setVData(VData aVData);

	public VData getVData();	
	
	public void setOperate(String aOperate);

	public String getOperate();	
	
	public void setBusiName(String aBusiName);
	
	public String getBusiName();		
	
	public void setSuccess(boolean aSuccess);
	
	public boolean getSuccess();	
	
	public void setUser(String aUser);
	
	public String getUser();		
	
	public void setManageCom(String aManageCom);
	
	public String getManageCom();	
	
	public void setCErrors(CErrors aCErrors);
	
	public CErrors getCErrors();	
	
	public void setResult(VData aResult);
	
	public VData getResult();		
}
