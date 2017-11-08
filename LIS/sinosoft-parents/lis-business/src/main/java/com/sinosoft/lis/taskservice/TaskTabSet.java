/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LDUSERToTaskTabSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 后台任务处理基本任务管理
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @version 1.0
 */
public class TaskTabSet {
private static Logger logger = Logger.getLogger(TaskTabSet.class);
	public CErrors mErrors = new CErrors();
	private String mOperate = "";
	private String mOperator = "";

	private MMap mMap = new MMap();
	private VData mResult = new VData();

	String mUserCode = "";
	private LDUSERToTaskTabSet mLDUSERToTaskTabSet = new LDUSERToTaskTabSet();
	
	public TaskTabSet() {
	}

	public boolean submitData(VData aInputData, String aOperate) {
		mOperate = aOperate;

		if (!getInputData(aInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		mResult.add(mMap);

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mResult, "")) {
			mErrors.addOneError(new CError("任务数据提交失败！"));

			return false;
		}

		return true;
	}

	private boolean getInputData(VData aInputData) {
		GlobalInput tGI = (GlobalInput) aInputData.getObjectByObjectName(
				"GlobalInput", 0);
		String tUserCode = (String) aInputData.getObjectByObjectName(
				"String", 0);
		this.mUserCode = tUserCode;
		if ((tGI == null) || (tUserCode == null) ||tUserCode.equals("")) {
			mErrors.addOneError(new CError("传入数据不完全！"));

			return false;
		}
		mOperator = tGI.Operator;

		if(mOperate.toUpperCase().equals("INSERTUSERTAB"))
		{
			this.mLDUSERToTaskTabSet = (LDUSERToTaskTabSet)aInputData.getObjectByObjectName(
					"LDUSERToTaskTabSet", 0);
		}
		
		return true;
	}

	private boolean dealData() {
		if (mOperate.equals("INSERTUSERTAB")) {
			//判断是否有重复
			Hashtable tHashtable = new Hashtable();
			for(int i=1;i<=mLDUSERToTaskTabSet.size();i++)
			{
				String tTaskTabID = mLDUSERToTaskTabSet.get(i).getTaskTabID();
				if(tHashtable.containsKey(tTaskTabID))
				{
					CError.buildErr(this, "不允许添加重复的标签页");
					return false;
				}
				tHashtable.put(tTaskTabID, tTaskTabID);
			}
			
			String tSQL = "delete from LDUSERToTaskTab where userid='"+"?userid?"+"'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("userid", this.mUserCode);
			mMap.put(sqlbv1, "DELETE");
			mMap.put(this.mLDUSERToTaskTabSet, "DELETE&INSERT");
			
		} else if (mOperate.equals("DELETEUSERTAB")) {
				
			String tSQL = "delete from LDUSERToTaskTab where userid='"+"?userid?"+"'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			sqlbv2.put("userid", this.mUserCode);
			mMap.put(sqlbv2, "DELETE");
		}
		
		
		return true;
	}
}
