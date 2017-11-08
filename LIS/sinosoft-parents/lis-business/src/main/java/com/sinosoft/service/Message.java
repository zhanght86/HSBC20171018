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
 * <p>Description:该类为web server 和App server传递业务等信息的载体</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author lk
 * @version 1.0
 * @date: 2008-05-20
 */
public class Message implements IMessage 
{
private static Logger logger = Logger.getLogger(Message.class);

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
     * web端业务数据
     */	
	private VData mVData=null;
    /**
     * 操作类型
     */		
	private String mOperate=null;
    /**
     * 业务处理类
     */		
	private String mBusiName=null;
    /**
     * 操作是否成功
     */		
	private boolean mSuccess=false;
    /**
     * 操作员
     */	
	private String mUser=null;
    /**
     * 管理机构
     */		
	private String mManageCom=null;
    /**
     * 处理时错误
     */		
	private CErrors mCErrors=null;
    /**
     * 处理结果
     */		
	private VData mResult=null;
	
	public String getBusiName() 
	{
		return this.mBusiName;
	}
	
	public CErrors getCErrors() 
	{
		return this.mCErrors;
	}

	public String getManageCom() 
	{
		return this.mManageCom;
	}
	
	public String getOperate() 
	{
		return this.mOperate;
	}
	
	public VData getResult() 
	{
		return this.mResult;
	}

	public boolean getSuccess() 
	{
		return this.mSuccess;
	}

	public String getUser() 
	{
		return this.mUser;
	}

	public VData getVData() 
	{
		return this.mVData;
	}
	
	public void setBusiName(String aBusiName) 
	{
	    this.mBusiName=aBusiName;
	}

	public void setCErrors(CErrors aCErrors) 
	{
        this.mCErrors=aCErrors;
	}

	public void setManageCom(String aManageCom) 
	{
        this.mManageCom=aManageCom;
	}

	public void setOperate(String aOperate) 
	{
	    this.mOperate=aOperate;
	}

	public void setResult(VData aResult) 
	{
        this.mResult=aResult;
	}

	public void setSuccess(boolean aSuccess) 
	{
        this.mSuccess=aSuccess;
	}

	public void setUser(String aUser)
	{
        this.mUser=aUser;
	}

	public void setVData(VData aVData) 
	{
        this.mVData=aVData;
	}

}
