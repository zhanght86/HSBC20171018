/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.taskservice;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LDTaskGroupDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDTaskGroupSchema;
import com.sinosoft.lis.vschema.LDTaskGroupDetailSet;
import com.sinosoft.lis.vschema.LDTaskGroupSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 后台任务处理基本任务管理
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author ZhangRong
 * @version 1.0
 */
public class TaskGroupSet {
private static Logger logger = Logger.getLogger(TaskGroupSet.class);
	public CErrors mErrors = new CErrors();
	private String mOperate = "";
	private String mOperator = "";
	private LDTaskGroupSchema mLDTaskGroupSchema;
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	private LDTaskGroupDetailSet mLDTaskGroupDetailSet = new LDTaskGroupDetailSet();
	
	public TaskGroupSet() {
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
		LDTaskGroupSet tLDTaskGroupSet = (LDTaskGroupSet) aInputData.getObjectByObjectName(
				"LDTaskGroupSet", 0);

		if ((tGI == null) || (tLDTaskGroupSet == null) || (tLDTaskGroupSet.size() <= 0)) {
			mErrors.addOneError(new CError("传入数据不完全！"));

			return false;
		}

		mLDTaskGroupSchema = tLDTaskGroupSet.get(1);
		mOperator = tGI.Operator;

		if(mOperate.toUpperCase().equals("INSERTTASKGROUPDETAIL"))
		{
			this.mLDTaskGroupDetailSet = (LDTaskGroupDetailSet)aInputData.getObjectByObjectName(
					"LDTaskGroupDetailSet", 0);
		}
		
		return true;
	}

	private boolean dealData() {
		if (mOperate.equals("INSERTTASKGROUP")) {
			if ((mLDTaskGroupSchema.getTaskDescribe() == null)
					|| mLDTaskGroupSchema.getTaskDescribe().equals("")) {
				mErrors.addOneError(new CError("数据不完全！"));

				return false;
			}

			int tCodeInt = 0;
			String tCodeString = "00000";
			LDTaskGroupDB tLDTaskGroupDB = new LDTaskGroupDB();
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("select * from LDTaskGroup where TaskGroupCode = (select MAX(TaskGroupCode) from LDTaskGroup)");
			LDTaskGroupSet tLDTaskGroupSet = tLDTaskGroupDB
					.executeQuery(sqlbv1); // 取最大序列号

			if ((tLDTaskGroupSet != null) && (tLDTaskGroupSet.size() > 0)) {
				tCodeString = tLDTaskGroupSet.get(1).getTaskGroupCode().substring(1);
			}

			try {
				tCodeInt = Integer.parseInt(tCodeString);
				tCodeInt++;
				tCodeString = Integer.toString(tCodeInt);
				tCodeString = "00000".substring(1,
						5 - tCodeString.length() + 1)
						+ tCodeString; // 生成新任务编码
				
				tCodeString = "G"+tCodeString;
				
			} catch (Exception ex) {
				return false;
			}

			mLDTaskGroupSchema.setTaskGroupCode(tCodeString);
			mLDTaskGroupSchema.setOperator(mOperator);
			mLDTaskGroupSchema.setMakeDate(PubFun.getCurrentDate());
			mLDTaskGroupSchema.setMakeTime(PubFun.getCurrentTime());
			mLDTaskGroupSchema.setModifyDate(PubFun.getCurrentDate());
			mLDTaskGroupSchema.setModifyTime(PubFun.getCurrentTime());
			mMap.put(mLDTaskGroupSchema, "INSERT");
		} else if (mOperate.equals("DELETETASKGROUP")) {
			//LDTaskGroupDB tLDTaskGroupDB = new LDTaskGroupDB();

			if ((mLDTaskGroupSchema.getTaskGroupCode() == null)
					|| mLDTaskGroupSchema.getTaskGroupCode().equals("")) {
				mErrors.addOneError(new CError("数据不完全！"));

				return false;
			}
			
			//校验
			
			String tSQL1 = "select 1 from ldtaskplan where taskcode='"+"?mLDTaskGroupSchema?"+"' ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSQL1);
			sqlbv1.put("mLDTaskGroupSchema", mLDTaskGroupSchema.getTaskGroupCode());
			ExeSQL tExeSQL = new ExeSQL();
			String tFlag = tExeSQL.getOneValue(sqlbv1);
			if(tFlag!=null&&tFlag.equals("1"))
			{
				mErrors.addOneError(new CError("当前任务已定义运行计划,请先将计划删除!"));

				return false;
			}
			
			String tSQL = "delete from ldtaskgroupdetail where taskgroupcode='"+"?mLDTaskGroupSchema?"+"'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("mLDTaskGroupSchema", mLDTaskGroupSchema.getTaskGroupCode());
			mMap.put(mLDTaskGroupSchema, "DELETE");
			mMap.put(sqlbv, "DELETE");
		}
		
		else if (mOperate.equals("INSERTTASKGROUPDETAIL")) {
			if ((mLDTaskGroupSchema.getTaskDescribe() == null)
					|| mLDTaskGroupSchema.getTaskDescribe().equals("")) {
				mErrors.addOneError(new CError("数据不完全！"));

				return false;
			}
			if(this.mLDTaskGroupDetailSet.size()<=0)
			{
				CError.buildErr(this,"没有配置任务明细");
				return false;
			}
			
			ExeSQL tExeSQL = new  ExeSQL();
			String tSQL_DateTime = "select makedate,maketime from ldtaskgroupdetail where taskgroupcode='"+"?mLDTaskGroupSchema?"+"' ";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSQL_DateTime);
			sqlbv3.put("mLDTaskGroupSchema", mLDTaskGroupSchema.getTaskGroupCode());
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv3);
			String tMakeDate = "";
			String tMakeTime = "";
			String tModifyDate = PubFun.getCurrentDate();
			String tModifyTime = PubFun.getCurrentTime();
			if(tSSRS.getMaxRow()<=0)
			{
				tMakeDate = tModifyDate;
				tMakeTime = tModifyTime;
			}
			else
			{
				tMakeDate = tSSRS.GetText(1, 1);
				tMakeTime = tSSRS.GetText(1, 2);
			}
			
			Hashtable tHashtable = new Hashtable();
			for(int i=1;i<=this.mLDTaskGroupDetailSet.size();i++)
			{
				mLDTaskGroupDetailSet.get(i).setMakeDate(tMakeDate);
				mLDTaskGroupDetailSet.get(i).setMakeTime(tMakeTime);
				mLDTaskGroupDetailSet.get(i).setModifyDate(tModifyDate);
				mLDTaskGroupDetailSet.get(i).setModifyTime(tModifyTime);
				mLDTaskGroupDetailSet.get(i).setOperator(mOperator);
				if(tHashtable.containsKey(mLDTaskGroupDetailSet.get(i).getTaskCode()))
				{
					CError.buildErr(this,"同一任务队列不允许重复添加同一任务 !");
					return false;
				}
				tHashtable.put(mLDTaskGroupDetailSet.get(i).getTaskCode(), mLDTaskGroupDetailSet.get(i).getTaskCode());
			}

			String tSQL_Detail = "delete from LDTaskGroupDetail where taskgroupcode='"+"?taskgroupcode?"+"'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSQL_Detail);
			sqlbv4.put("taskgroupcode", mLDTaskGroupSchema.getTaskGroupCode());
			mMap.put(sqlbv4, "DELETE");
			mMap.put(mLDTaskGroupDetailSet, "INSERT");
		} 
		
		else if (mOperate.equals("DELETETASKGROUPDETAIL")) {
			if ((mLDTaskGroupSchema.getTaskDescribe() == null)
					|| mLDTaskGroupSchema.getTaskDescribe().equals("")) {
				mErrors.addOneError(new CError("数据不完全！"));

				return false;
			}
			
			
			
			String tSQL_Detail = "delete from LDTaskGroupDetail where taskgroupcode='"+"?taskgroupcode?"+"'";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tSQL_Detail);
			sqlbv5.put("taskgroupcode", mLDTaskGroupSchema.getTaskGroupCode());
			mMap.put(sqlbv5, "DELETE");
		} 

		return true;
	}
}
