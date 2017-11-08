/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */
package com.sinosoft.lis.workflowmanage;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;


public class ActivityDefBL
{
private static Logger logger = Logger.getLogger(ActivityDefBL.class);

	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private VData CommitData = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();
	/**
	 * 往后面传输数据的容器
	 */
	private TransferData mTransferData = new TransferData();
	private MMap map = new MMap();
	/** 全局数据 */
	// private GlobalInput mGlobalInput =new GlobalInput() ;
	/**
	 * 数据操作字符串
	 */
	private String mOperate;

	public ActivityDefBL()
	{
	}

	public static void main(String[] args)
	{
	}

	/**
	 * 传输数据的公共方法
	 *
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "COMBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败COMBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(CommitData, ""))
		{
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		if ("INSERT||Activity".equalsIgnoreCase(this.mOperate))
		{
			String tActivityID = (String) mTransferData.getValueByName("ActivityID");
			String tActivityName = (String) mTransferData.getValueByName("ActivityName");
			String tActivityType = (String) mTransferData.getValueByName("ActivityType");
			String tActivityDesc = (String) mTransferData.getValueByName("ActivityDesc");
			String tBeforeInit = (String) mTransferData.getValueByName("BeforeInit");
			String tBeforeInitType = (String) mTransferData.getValueByName("BeforeInitType");
			String tAfterInit = (String) mTransferData.getValueByName("AfterInit");
			String tAfterInitType = (String) mTransferData.getValueByName("AfterInitType");
			String tBeforeEnd = (String) mTransferData.getValueByName("BeforeEnd");
			String tBeforeEndType = (String) mTransferData.getValueByName("BeforeEndType");
			String tAfterEnd = (String) mTransferData.getValueByName("AfterEnd");
			String tAfterEndType = (String) mTransferData.getValueByName("AfterEndType");
			String tWatingTime = (String) mTransferData.getValueByName("WatingTime");
			String tWorkingTime = (String) mTransferData.getValueByName("WorkingTime");
			String tTimeOut = (String) mTransferData.getValueByName("TimeOut");
			String tOperator = (String) mTransferData.getValueByName("Operator");
			String tMakeDate = (String) mTransferData.getValueByName("MakeDate");
			String tMakeTime = (String) mTransferData.getValueByName("MakeTime");
			String tModifyDate = (String) mTransferData.getValueByName("ModifyDate");
			String tModifyTime = (String) mTransferData.getValueByName("ModifyTime");
			String tBusiType = (String) mTransferData.getValueByName("BusiType");
			String tIsNeed = (String) mTransferData.getValueByName("IsNeed");
			String tActivityFlag = (String) mTransferData.getValueByName("ActivityFlag");
			String tImpDegree = (String) mTransferData.getValueByName("ImpDegree");
			String tFunctionID = (String) mTransferData.getValueByName("FunctionID");
			//增加创建、分配、删除，聚合模式   jiyongtian 
			String tCreateActionType = (String) mTransferData.getValueByName("CreateActionType");
			String tCreateAction = (String) mTransferData.getValueByName("CreateAction");
			String tApplyActionType = (String) mTransferData.getValueByName("ApplyActionType");
			String tApplyAction = (String) mTransferData.getValueByName("ApplyAction");
			String tDeleteActionType = (String) mTransferData.getValueByName("DeleteActionType");
			String tDeleteAction = (String) mTransferData.getValueByName("DeleteAction");
			String tTogether = (String) mTransferData.getValueByName("Together");
            //end
			LWActivitySchema tLWActivitySchema = new LWActivitySchema();
			tLWActivitySchema.setActivityID(tActivityID);
			tLWActivitySchema.setActivityName(tActivityName);
			tLWActivitySchema.setActivityType(tActivityType);
			tLWActivitySchema.setActivityDesc(tActivityDesc);
			tLWActivitySchema.setBeforeInit(tBeforeInit);
			tLWActivitySchema.setBeforeInitType(tBeforeInitType);
			tLWActivitySchema.setAfterInit(tAfterInit);
			tLWActivitySchema.setAfterInitType(tAfterInitType);
			tLWActivitySchema.setBeforeEnd(tBeforeEnd);
			tLWActivitySchema.setBeforeEndType(tBeforeEndType);
			tLWActivitySchema.setAfterEnd(tAfterEnd);
			tLWActivitySchema.setAfterEndType(tAfterEndType);
			tLWActivitySchema.setWatingTime(tWatingTime);
			tLWActivitySchema.setWorkingTime(tWorkingTime);
			tLWActivitySchema.setTimeOut(tTimeOut);
			tLWActivitySchema.setOperator(mGlobalInput.Operator);
			tLWActivitySchema.setMakeDate(PubFun.getCurrentDate());
			tLWActivitySchema.setMakeTime(PubFun.getCurrentTime());
			tLWActivitySchema.setModifyDate(PubFun.getCurrentDate());
			tLWActivitySchema.setModifyTime(PubFun.getCurrentTime());
			tLWActivitySchema.setBusiType(tBusiType);
			tLWActivitySchema.setIsNeed(tIsNeed);
			tLWActivitySchema.setActivityFlag(tActivityFlag);
			tLWActivitySchema.setImpDegree(tImpDegree);
			tLWActivitySchema.setFunctionID(tFunctionID);
			tLWActivitySchema.setCreateActionType(tCreateActionType);
			tLWActivitySchema.setCreateAction(tCreateAction);
			tLWActivitySchema.setApplyActionType(tApplyActionType);
			tLWActivitySchema.setApplyAction(tApplyAction);
			tLWActivitySchema.setDeleteActionType(tDeleteActionType);
			tLWActivitySchema.setDeleteAction(tDeleteAction);
			tLWActivitySchema.setTogether(tTogether);
			map.put(tLWActivitySchema, "INSERT");
		}
		else if ("MODIFY||Activity".equalsIgnoreCase(this.mOperate))
		{
			String tActivityID = (String) mTransferData.getValueByName("ActivityID");
			String tActivityName = (String) mTransferData.getValueByName("ActivityName");
			String tActivityType = (String) mTransferData.getValueByName("ActivityType");
			String tActivityDesc = (String) mTransferData.getValueByName("ActivityDesc");
			String tBeforeInit = (String) mTransferData.getValueByName("BeforeInit");
			String tBeforeInitType = (String) mTransferData.getValueByName("BeforeInitType");
			String tAfterInit = (String) mTransferData.getValueByName("AfterInit");
			String tAfterInitType = (String) mTransferData.getValueByName("AfterInitType");
			String tBeforeEnd = (String) mTransferData.getValueByName("BeforeEnd");
			String tBeforeEndType = (String) mTransferData.getValueByName("BeforeEndType");
			String tAfterEnd = (String) mTransferData.getValueByName("AfterEnd");
			String tAfterEndType = (String) mTransferData.getValueByName("AfterEndType");
			String tWatingTime = (String) mTransferData.getValueByName("WatingTime");
			String tWorkingTime = (String) mTransferData.getValueByName("WorkingTime");
			String tTimeOut = (String) mTransferData.getValueByName("TimeOut");
			String tOperator = (String) mTransferData.getValueByName("Operator");
			String tMakeDate = (String) mTransferData.getValueByName("MakeDate");
			String tMakeTime = (String) mTransferData.getValueByName("MakeTime");
			String tModifyDate = (String) mTransferData.getValueByName("ModifyDate");
			String tModifyTime = (String) mTransferData.getValueByName("ModifyTime");
			String tBusiType = (String) mTransferData.getValueByName("BusiType");
			String tIsNeed = (String) mTransferData.getValueByName("IsNeed");
			String tActivityFlag = (String) mTransferData.getValueByName("ActivityFlag");
			String tImpDegree = (String) mTransferData.getValueByName("ImpDegree");
			String tFunctionID = (String) mTransferData.getValueByName("FunctionID");
			//增加创建、分配、删除，聚合模式   jiyongtian 
			String tCreateActionType = (String) mTransferData.getValueByName("CreateActionType");
			String tCreateAction = (String) mTransferData.getValueByName("CreateAction");
			String tApplyActionType = (String) mTransferData.getValueByName("ApplyActionType");
			String tApplyAction = (String) mTransferData.getValueByName("ApplyAction");
			String tDeleteActionType = (String) mTransferData.getValueByName("DeleteActionType");
			String tDeleteAction = (String) mTransferData.getValueByName("DeleteAction");
			String tTogether = (String) mTransferData.getValueByName("Together");
			//end
			LWActivitySchema tLWActivitySchema = new LWActivitySchema();
			LWActivityDB tLWActivityDB = new LWActivityDB();
			tLWActivityDB.setActivityID(tActivityID);
			if (!tLWActivityDB.getInfo())
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLWActivitySchema = tLWActivityDB.getSchema();
 
			tLWActivitySchema.setActivityID(tActivityID);
			tLWActivitySchema.setActivityName(tActivityName);
			tLWActivitySchema.setActivityType(tActivityType);
			tLWActivitySchema.setActivityDesc(tActivityDesc);
			tLWActivitySchema.setBeforeInit(tBeforeInit);
			tLWActivitySchema.setBeforeInitType(tBeforeInitType);
			tLWActivitySchema.setAfterInit(tAfterInit);
			tLWActivitySchema.setAfterInitType(tAfterInitType);
			tLWActivitySchema.setBeforeEnd(tBeforeEnd);
			tLWActivitySchema.setBeforeEndType(tBeforeEndType);
			tLWActivitySchema.setAfterEnd(tAfterEnd);
			tLWActivitySchema.setAfterEndType(tAfterEndType);
			tLWActivitySchema.setWatingTime(tWatingTime);
			tLWActivitySchema.setWorkingTime(tWorkingTime);
			tLWActivitySchema.setTimeOut(tTimeOut);
			tLWActivitySchema.setOperator(mGlobalInput.Operator);

			tLWActivitySchema.setModifyDate(PubFun.getCurrentDate());
			tLWActivitySchema.setModifyTime(PubFun.getCurrentTime());
			tLWActivitySchema.setBusiType(tBusiType);
			tLWActivitySchema.setIsNeed(tIsNeed);
			tLWActivitySchema.setActivityFlag(tActivityFlag);
			tLWActivitySchema.setImpDegree(tImpDegree);
			tLWActivitySchema.setFunctionID(tFunctionID);
			
			tLWActivitySchema.setCreateActionType(tCreateActionType);
			tLWActivitySchema.setCreateAction(tCreateAction);
			tLWActivitySchema.setApplyActionType(tApplyActionType);
			tLWActivitySchema.setApplyAction(tApplyAction);
			tLWActivitySchema.setDeleteActionType(tDeleteActionType);
			tLWActivitySchema.setDeleteAction(tDeleteAction);
			tLWActivitySchema.setTogether(tTogether);
			
			map.put(tLWActivitySchema, "UPDATE");
		}
		else if ("DELETE||Activity".equalsIgnoreCase(this.mOperate))
		{
			String tActivityID = (String) mTransferData.getValueByName("ActivityID");

			LWActivitySchema tLWActivitySchema = new LWActivitySchema();
			LWActivityDB tLWActivityDB = new LWActivityDB();
			tLWActivityDB.setActivityID(tActivityID);
			if (!tLWActivityDB.getInfo())
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLWActivitySchema = tLWActivityDB.getSchema();
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("delete from lwfieldmap where activityid='?tActivityID?'");
			sqlbv.put("tActivityID", tActivityID);
 
			map.put(sqlbv, "DELETE");
			map.put(tLWActivitySchema, "DELETE");
		}
		else if ("INSERT||Param".equalsIgnoreCase(this.mOperate))
		{
			String tActivityID = (String) mTransferData.getValueByName("ActivityID");
			String tActivityName = (String) mTransferData.getValueByName("ActivityName");
			String tFieldOrder = (String) mTransferData.getValueByName("FieldOrder");
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql("select COUNT(ActivityID) from LWFieldMap where ActivityID='?tActivityID?'");
			sqlbv1.put("tActivityID", tActivityID);
			tFieldOrder = new ExeSQL().getOneValue(sqlbv1);
			try
			{
				int temp = Integer.parseInt(tFieldOrder) + 1;
				tFieldOrder = String.valueOf(temp);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			String tSourFieldName = (String) mTransferData.getValueByName("SourFieldName");
			String tSourFieldCName = (String) mTransferData.getValueByName("SourFieldCName");
			String tDestFieldName = (String) mTransferData.getValueByName("DestFieldName");
			String tDestFieldCName = (String) mTransferData.getValueByName("DestFieldCName");
			LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
			tLWFieldMapSchema.setActivityID(tActivityID);
			tLWFieldMapSchema.setFieldOrder(tFieldOrder);
			tLWFieldMapSchema.setSourFieldName(tSourFieldName);
			tLWFieldMapSchema.setSourFieldCName(tSourFieldCName);
			tLWFieldMapSchema.setDestFieldName(tDestFieldName);
			tLWFieldMapSchema.setDestFieldCName(tDestFieldCName);
			map.put(tLWFieldMapSchema, "INSERT");
		}
		else if ("MODIFY||Param".equalsIgnoreCase(this.mOperate))
		{
			String tActivityID = (String) mTransferData.getValueByName("ActivityID");
			String tActivityName = (String) mTransferData.getValueByName("ActivityName");
			String tFieldOrder = (String) mTransferData.getValueByName("FieldOrder");
			String tSourFieldName = (String) mTransferData.getValueByName("SourFieldName");
			String tSourFieldCName = (String) mTransferData.getValueByName("SourFieldCName");
			String tDestFieldName = (String) mTransferData.getValueByName("DestFieldName");
			String tDestFieldCName = (String) mTransferData.getValueByName("DestFieldCName");
			LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
			LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
			tLWFieldMapDB.setActivityID(tActivityID);
			tLWFieldMapDB.setFieldOrder(tFieldOrder);
			if (tLWFieldMapDB.query().size()==0)
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLWFieldMapSchema = tLWFieldMapDB.query().get(1);
			tLWFieldMapSchema.setActivityID(tActivityID);
			tLWFieldMapSchema.setFieldOrder(tFieldOrder);
			tLWFieldMapSchema.setSourFieldName(tSourFieldName);
			tLWFieldMapSchema.setSourFieldCName(tSourFieldCName);
			tLWFieldMapSchema.setDestFieldName(tDestFieldName);
			tLWFieldMapSchema.setDestFieldCName(tDestFieldCName);
			map.put(tLWFieldMapSchema, "UPDATE");
		}
		else if ("DELETE||Param".equalsIgnoreCase(this.mOperate))
		{
			String tActivityID = (String) mTransferData.getValueByName("ActivityID");
			String tActivityName = (String) mTransferData.getValueByName("ActivityName");
			String tFieldOrder = (String) mTransferData.getValueByName("FieldOrder");
			String tSourFieldName = (String) mTransferData.getValueByName("SourFieldName");
			String tSourFieldCName = (String) mTransferData.getValueByName("SourFieldCName");
			String tDestFieldName = (String) mTransferData.getValueByName("DestFieldName");
			String tDestFieldCName = (String) mTransferData.getValueByName("DestFieldCName");
			LWFieldMapSchema tLWFieldMapSchema = new LWFieldMapSchema();
			LWFieldMapDB tLWFieldMapDB = new LWFieldMapDB();
			tLWFieldMapDB.setActivityID(tActivityID);
			tLWFieldMapDB.setFieldOrder(tFieldOrder);
			if (tLWFieldMapDB.query().size()==0)
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLWFieldMapSchema = tLWFieldMapDB.query().get(1);
			map.put(tLWFieldMapSchema, "DELETE");
		}
		else if ("INSERT||Condition".equalsIgnoreCase(this.mOperate))
		{
			String tBusiType = (String) mTransferData.getValueByName("BusiType");
			String tBusiTypeName = (String) mTransferData.getValueByName("BusiTypeName");
			String tStartActivityID = (String) mTransferData.getValueByName("StartActivityID");
			String tStartActivityName = (String) mTransferData.getValueByName("StartActivityName");
			String tEndActivityID = (String) mTransferData.getValueByName("EndActivityID");
			String tEndActivityName = (String) mTransferData.getValueByName("EndActivityName");
			String tTransitionCondT = (String) mTransferData.getValueByName("TransitionCondT");
			String tTransitionCondTName = (String) mTransferData.getValueByName("TransitionCondTName");
			String tTransitionCond = (String) mTransferData.getValueByName("TransitionCond");
			tTransitionCond = tTransitionCond.replaceAll("'","''");
			String tCondDesc = (String) mTransferData.getValueByName("CondDesc");
			LWConditionSchema tLWConditionSchema = new LWConditionSchema();
			tLWConditionSchema.setBusiType(tBusiType);
			tLWConditionSchema.setTransitionStart(tStartActivityID);
			tLWConditionSchema.setTransitionEnd(tEndActivityID);
			tLWConditionSchema.setTransitionCondT(tTransitionCondT);
			tLWConditionSchema.setTransitionCond(tTransitionCond);
			tLWConditionSchema.setCondDesc(tCondDesc);
			map.put(tLWConditionSchema, "INSERT");
		}
		else if ("MODIFY||Condition".equalsIgnoreCase(this.mOperate))
		{
			String tBusiType = (String) mTransferData.getValueByName("BusiType");
			String tBusiTypeName = (String) mTransferData.getValueByName("BusiTypeName");
			String tStartActivityID = (String) mTransferData.getValueByName("StartActivityID");
			String tStartActivityName = (String) mTransferData.getValueByName("StartActivityName");
			String tEndActivityID = (String) mTransferData.getValueByName("EndActivityID");
			String tEndActivityName = (String) mTransferData.getValueByName("EndActivityName");
			String tTransitionCondT = (String) mTransferData.getValueByName("TransitionCondT");
			String tTransitionCondTName = (String) mTransferData.getValueByName("TransitionCondTName");
			String tTransitionCond = (String) mTransferData.getValueByName("TransitionCond");
			tTransitionCond = tTransitionCond.replaceAll("'","''");
			String tCondDesc = (String) mTransferData.getValueByName("CondDesc");
			LWConditionSchema tLWConditionSchema = new LWConditionSchema();
			LWConditionDB tLWConditionDB = new LWConditionDB();
			tLWConditionDB.setBusiType(tBusiType);
			tLWConditionDB.setTransitionStart(tStartActivityID);
			tLWConditionDB.setTransitionEnd(tEndActivityID);
			if (tLWConditionDB.query().size()==0)
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLWConditionSchema  = tLWConditionDB.query().get(1);
			tLWConditionSchema.setBusiType(tBusiType);
			tLWConditionSchema.setTransitionStart(tStartActivityID);
			tLWConditionSchema.setTransitionEnd(tEndActivityID);
			tLWConditionSchema.setTransitionCondT(tTransitionCondT);
			tLWConditionSchema.setTransitionCond(tTransitionCond);
			tLWConditionSchema.setCondDesc(tCondDesc);
			map.put(tLWConditionSchema, "UPDATE");
		}
		else if ("DELETE||Condition".equalsIgnoreCase(this.mOperate))
		{
			String tBusiType = (String) mTransferData.getValueByName("BusiType");
			String tStartActivityID = (String) mTransferData.getValueByName("StartActivityID");
			String tEndActivityID = (String) mTransferData.getValueByName("EndActivityID");
			LWConditionSchema tLWConditionSchema = new LWConditionSchema();
			LWConditionDB tLWConditionDB = new LWConditionDB();
			tLWConditionDB.setBusiType(tBusiType);
			tLWConditionDB.setTransitionStart(tStartActivityID);
			tLWConditionDB.setTransitionEnd(tEndActivityID);
			if (tLWConditionDB.query().size()==0)
			{
				CError tError = new CError();
				tError.moduleName = this.getClass().getName();
				tError.functionName = "dealdata";
				tError.errorMessage = "query data from database fail!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLWConditionSchema  = tLWConditionDB.query().get(1);
			map.put(tLWConditionSchema, "DELETE");
		}
		else
		{
			CError tError = new CError();
			tError.moduleName = this.getClass().getName();
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mTransferData = ((TransferData) cInputData.getObjectByObjectName("TransferData", 0));
		this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		return true;
	}

	private boolean prepareOutputData()
	{
		try
		{
			mResult.clear();
			CommitData.clear();
			CommitData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			ex.printStackTrace();
			CError tError = new CError();
			tError.moduleName = this.getClass().getName();
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}
}
