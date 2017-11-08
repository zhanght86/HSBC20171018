package com.sinosoft.workflow.ask;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContPlanDB;
import com.sinosoft.lis.db.LCContPlanRiskDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCContPlanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.AfterInitService;

/**
 * <p>
 * Title: 团体询价录入完毕
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author heyq
 * @version 1.0
 */

public class AskInputConfirmAfterInitService implements AfterInitService {
private static Logger logger = Logger.getLogger(AskInputConfirmAfterInitService.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCContPlanSet mLCContPlanSet = new LCContPlanSet();
	private LCContPlanRiskSet mLCContPlanRiskSet = new LCContPlanRiskSet();
	private LCContPlanSet mNewLCContPlanSet = new LCContPlanSet();
	private LCContPlanRiskSet mNewLCContPlanRiskSet = new LCContPlanRiskSet();
	/** 数据操作字符串 */
	private String mOperater;
	private String mManageCom;
	private String mOperate;
	private String mMissionID;
	private String mSubMissionID;
	private String mGrpContNo;
	private String mContSql;
	private String mUWGrade;
	private SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	public AskInputConfirmAfterInitService() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate))
			return false;

		// 校验是否有未打印的体检通知书
		if (!checkData())
			return false;

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData())
			return false;

		logger.debug("dealData successful!");

		// 为工作流下一节点属性字段准备数据
		if (!prepareTransferData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		logger.debug("Start  Submit...");

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(mLCGrpContSchema, "UPDATE");
		map.put(sqlbv1, "UPDATE");
		map.put(mLCContPlanSet, "UPDATE");
		map.put(mLCContPlanRiskSet, "UPDATE");
		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "checkData";
			tError.errorMessage = "查询集体合同信息失败，请确认是否录入正确!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema();

		// 校验此险种是否已经录入被保人
		// for (int i = 1; i <= tLCGrpPolSet.size(); i++)
		// {
		// if (tLCGrpPolSet.get(i).getPeoples2() != 0)
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpFirstWorkFlowCheck";
		// tError.functionName = "checkData";
		// tError.errorMessage = "险种" + tLCGrpPolSet.get(i).getRiskCode() +
		// "下未录入被保人，请删除此险种或录入被保险人！";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		// }
		//
		// ExeSQL tExeSQL = new ExeSQL();
		// String tSql = "select distinct 1 from lccont where 1=1 "
		// + " and grpcontno = '" + mGrpContNo + "'"
		// ;
		// String rs = tExeSQL.getOneValue(tSql);
		// if (rs == null || rs.length() != 0)
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpFirstWorkFlowCheck";
		// tError.functionName = "checkData";
		// tError.errorMessage = "集体险种下未录入个人合同信息！";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// tSql = "select distinct 1 from lcpol where 1=1 "
		// + " and grpcontno = '" + mGrpContNo + "'"
		// ;
		// rs = tExeSQL.getOneValue(tSql);
		// if (rs == null || rs.length() != 0)
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpFirstWorkFlowCheck";
		// tError.functionName = "checkData";
		// tError.errorMessage = "集体险种下未录入个人险种信息！";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mInputData = cInputData;
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperater = mGlobalInput.Operator;
		if (mOperater == null || mOperater.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据Operate失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mOperate = cOperate;
		// 获得当前工作任务的任务ID
		mMissionID = (String) mTransferData.getValueByName("MissionID");
		if (mMissionID == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中MissionID失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得当前工作任务的任务ID
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		if (mGrpContNo == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输业务数据中mContNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		// 为团体合同表存入录入人、录入时间
		mLCGrpContSchema.setInputOperator(mOperater);
		mLCGrpContSchema.setInputDate(PubFun.getCurrentDate());
		mLCGrpContSchema.setInputTime(PubFun.getCurrentTime());

		// 为合同表存入录入人、录入时间
		mContSql = "update lccont set InputOperator ='" + mOperater + "',"
				+ "InputDate = '" + "?InputDate?" + "',"
				+ "InputTime = '" + "?InputTime?" + "'"
				+ " where grpcontno = '" + "?mGrpContNo?" + "'";
		sqlbv1.sql(mContSql);
		sqlbv1.put("InputDate",PubFun.getCurrentDate());
		sqlbv1.put("InputTime",PubFun.getCurrentTime());
		sqlbv1.put("mGrpContNo",mGrpContNo);
		

		// 为产品定价准备数据
		// ---- modify by haopan
		// if(!prepareContPlan())
		// return false;
		calculateUWGrade(mLCGrpContSchema);
		return true;

	}

	/**
	 * prepareContPlan 为产品定价准备数据 询价录入完毕后，需要再在保险计划表中插入PlanType=4的相同的记录
	 * 
	 * @return boolean
	 */
	private boolean prepareContPlan() {
		LCContPlanSchema tLCContPlanSchema;
		LCContPlanDB tLCContPlanDB = new LCContPlanDB();
		tLCContPlanDB.setGrpContNo(mGrpContNo);

		mLCContPlanSet = tLCContPlanDB.query();
		if (mLCContPlanSet == null || mLCContPlanSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "prepareContPlan";
			tError.errorMessage = "未录入保险计划不能保存!";
			this.mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= mLCContPlanSet.size(); i++) {
			mLCContPlanSet.get(i).setPlanType("4"); // 核保测算
			// mNewLCContPlanSet.get(i).setPlanType("5"); //为产品定价作准备
		}

		LCContPlanRiskDB tLCContPlanRiskDB = new LCContPlanRiskDB();
		tLCContPlanRiskDB.setGrpContNo(mGrpContNo);
		mLCContPlanRiskSet = tLCContPlanRiskDB.query();
		if (mLCContPlanRiskSet == null || mLCContPlanRiskSet.size() <= 0) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCContDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "GrpInputConfirmAfterInitService";
			tError.functionName = "prepareContPlan";
			tError.errorMessage = "未录入保险计划不能保存!";
			this.mErrors.addOneError(tError);
			return false;
		}

		for (int i = 1; i <= mLCContPlanRiskSet.size(); i++) {
			mLCContPlanRiskSet.get(i).setPlanType("4");
		}

		return true;
	}

	/**
	 * 为公共传输数据集合中添加工作流下一节点属性字段数据
	 * 
	 * @return
	 */
	private boolean prepareTransferData() {
		mTransferData.setNameAndValue("GrpContNo", mLCGrpContSchema
				.getGrpContNo());
		mTransferData.setNameAndValue("PrtNo", mLCGrpContSchema.getPrtNo());
		mTransferData.setNameAndValue("SaleChnl", mLCGrpContSchema
				.getSaleChnl());
		mTransferData.setNameAndValue("ManageCom", mLCGrpContSchema
				.getManageCom());
		mTransferData.setNameAndValue("AgentCode", mLCGrpContSchema
				.getAgentCode());
		mTransferData.setNameAndValue("AgentGroup", mLCGrpContSchema
				.getAgentGroup());
		mTransferData.setNameAndValue("GrpName", mLCGrpContSchema.getGrpName());
		mTransferData.setNameAndValue("GrpNo", mLCGrpContSchema.getAppntNo());
		mTransferData.setNameAndValue("CValiDate", mLCGrpContSchema
				.getCValiDate());
		mTransferData.setNameAndValue("ReportType", "0");

		/***********************************************************************
		 * 添加核保员最低级别
		 **********************************************************************/
		// ExeSQL tExeSQL = new ExeSQL();
		// String tSql = "select comcode from lduser where
		// usercode='"+mGlobalInput.Operator+"'";
		// String tComCode = tExeSQL.getOneValue(tSql);
		// tSql = "select min(uwpopedom) from LDUWUser where uwtype='2' and
		// managecom like '"+tComCode+"%'";
		// tExeSQL = new ExeSQL();
		// String tUWAuthority = tExeSQL.getOneValue(tSql);
		// if(tUWAuthority==null||tUWAuthority.equals(""))
		// {
		// CError tError = new CError();
		// tError.moduleName = "GrpInputConfirmAfterInitService";
		// tError.functionName = "prepareContPlan";
		// tError.errorMessage = "未找到本机构下核保权限信息";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		mTransferData.setNameAndValue("UWAuthority", mUWGrade);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public TransferData getReturnTransferData() {
		return mTransferData;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * add by:chenrong add date:2006.12.19 整单核保级别数据准备
	 */
	private void calculateUWGrade(LCGrpContSchema tLCGrpContSchema) {
		String tSQL = "";
		ExeSQL tExeSQL = new ExeSQL();
		tSQL = "select max(w.b) from (select uwkind as a ,min(uwpopedom) as b "
				+ "from lduwgradeperson "
				+ " where uwtype='2' and HEALTHAMNTGASK('"
				+ "?value2?" + "',uwkind)<=maxamnt  "
				+ " group by uwkind) w";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSQL);
		sqlbv2.put("value2",tLCGrpContSchema.getGrpContNo());
		mUWGrade = tExeSQL.getOneValue(sqlbv2);
		if (mUWGrade == null || mUWGrade.equals("") || mUWGrade.equals(null)) {
			tSQL = " select (case when min(b.uwpopedom) is not null then min(b.uwpopedom) else 'U18B01' end) from lduwgradeperson b "
					+ "where b.uwtype='2' "
					+ " and b.maxamnt>0 and HEALTHAMNTGASK('"
					+ "?value3?" + "',b.uwkind)>0 ";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSQL);
			sqlbv3.put("value3",tLCGrpContSchema.getGrpContNo());
			mUWGrade = tExeSQL.getOneValue(sqlbv3);
		}

	}

}
