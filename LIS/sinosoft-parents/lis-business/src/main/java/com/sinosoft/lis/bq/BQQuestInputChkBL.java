package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPIssuePolDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGCUWMasterSchema;
import com.sinosoft.lis.schema.LCQuestionnaireSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPIssuePolSchema;
import com.sinosoft.lis.vschema.LCQuestionnaireSet;
import com.sinosoft.lis.vschema.LPIssuePolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统问题件录入部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @modified by ZhangRong 2004.11
 * @version 1.0
 */
public class BQQuestInputChkBL {
private static Logger logger = Logger.getLogger(BQQuestInputChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();

	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator;

	private String mIsueManageCom;

	private String mManageCom;

	private FDate fDate = new FDate();

	private int mflag = 0; // 1:个单 2:团单

	private String Flag = "";

	private TransferData mTransferData = new TransferData();

	private GlobalInput mGlobalInput = new GlobalInput();

	/** 业务处理相关变量 */

	private LPContSchema mLPContSchema = new LPContSchema();
	
	private String mContNo = "";

	private String mEdorNo = "";
	private String mEdorType = "";


	/** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = null; // new

	// LCCUWMasterSchema();

	private LCGCUWMasterSchema mLCGCUWMasterSchema = null; // new

	// LCGCUWMasterSchema();

	/** 问题件表 */
	private LPIssuePolSet mLPIssuePolSet = new LPIssuePolSet();

	private LPIssuePolSet mmLPIssuePolSet = new LPIssuePolSet();

	private LPIssuePolSet tAllLPIssuePolSet = new LPIssuePolSet();
	
	private LCQuestionnaireSet mLCQuestionnaireSet =new LCQuestionnaireSet();

	private String mOperate = "";

	public BQQuestInputChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		this.mOperate = cOperate;
		// 将操作数据拷贝到本类中，此类做数据提交使用
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");

		// 得到外部传入的数据,将数据备份到本类中
		logger.debug("---QuestInputChkBL calling getInputData---");

		if (!getInputData(cInputData)) {
			return false;
		}
		
		if (!checkData()) {
			return false;
		}
		
		// 数据操作业务处理
		if (!dealData()) {
			return false;
		} else {
			flag = 1;
		}

		if (flag == 0) {
			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "没有自动通过保单!";
			this.mErrors.addOneError(tError);

			return false;
		}
		logger.debug("---QuestInputChkBL dealData---");

		// 准备给后台的数据
		prepareOutputData(cOperate);

		logger.debug("---QuestInputChkBL prepareOutputData---");

		// 数据提交
		mResult.add(mMap);

		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		logger.debug("---QuestInputChkBL commitData---");

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (dealOnePol() == false) {
			return false;
		}
		
		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 健康信息
		if (this.mOperate.equals("INSERT")) {
			if (prepareQuest() == false) {
				return false;
			}
		} else if (this.mOperate.equals("UPDATE")) {
			if (prepareUpdate() == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 准备问题件修改数据
	 * 
	 * @return
	 */
	private boolean prepareUpdate() {//只修改下发标记
		for (int i = 1; i <= this.mLPIssuePolSet.size(); i++) {
			LPIssuePolDB tempLPIssuePolDB = new LPIssuePolDB();
			LPIssuePolSet tempLPIssuePolSet = new LPIssuePolSet();
			tempLPIssuePolDB.setEdorNo(mEdorNo);
			tempLPIssuePolDB.setEdorType(mEdorType);
			tempLPIssuePolDB.setProposalContNo(mLPIssuePolSet.get(i)
					.getProposalContNo());
			tempLPIssuePolDB.setSerialNo(mLPIssuePolSet.get(i).getSerialNo());
			tempLPIssuePolSet = tempLPIssuePolDB.query();
			if (tempLPIssuePolSet.size() <= 0) {
				CError.buildErr(this, "查询需要修改的问题件数据出错!") ;
				return false;
			}
			String tNeedPrintFlag = mLPIssuePolSet.get(i).getNeedPrint();
			mLPIssuePolSet.get(i).setSchema(
					tempLPIssuePolSet.get(1).getSchema());
			mLPIssuePolSet.get(i).setNeedPrint(tNeedPrintFlag);
			mLPIssuePolSet.get(i).setModifyDate(PubFun.getCurrentDate());
			mLPIssuePolSet.get(i).setModifyTime(PubFun.getCurrentTime());
		}
		tAllLPIssuePolSet.clear();
		tAllLPIssuePolSet.set(mLPIssuePolSet);
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mGlobalInput = tGlobalInput;
		mOperator = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;
		
		TransferData tTransferData = new TransferData();
		tTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if(tTransferData==null)
		{
			CError.buildErr(this, "没有传入tTransferData!") ;

			return false;
		}
		mContNo = (String)tTransferData.getValueByName("ContNo");
		mEdorNo = (String)tTransferData.getValueByName("EdorNo");
		mEdorType = (String)tTransferData.getValueByName("EdorType");

		mLPIssuePolSet.set((LPIssuePolSet) cInputData.getObjectByObjectName(
				"LPIssuePolSet", 0));
	
		if (mLPIssuePolSet.size() <= 0) {
			// @@错误处理
			CError.buildErr(this, "没有传入问题件信息!") ;

			return false;
		}

		return true;
	}

	private boolean checkData1() {
		LPIssuePolDB tLPIssuePolDB = new LPIssuePolDB();
		// tLPIssuePolDB.setContNo(mContNo);
		// tLPIssuePolDB.setState("");
		// tLPIssuePolDB.setIssueType("99");
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String tSQL = "select * from LPIssuePol where contno='" + "?mContNo?" + "'"
				+ " and issuetype='99' and state is null ";
		logger.debug("**********判断是否存在还未回复的客户合并问题件=========" + tSQL);
		sqlbv.sql(tSQL);
		sqlbv.put("mContNo", mContNo);
		LPIssuePolSet tLPIssuePolSet = new LPIssuePolSet();
		// tLPIssuePolSet = tLPIssuePolDB.query();
		tLPIssuePolSet.set(tLPIssuePolDB.executeQuery(sqlbv));
		if (tLPIssuePolSet.size() >= 1) {
			CError tError = new CError();
			tError.moduleName = "QuestInputChkBL";
			tError.functionName = "prepareQuest";
			tError.errorMessage = "已经发送客户合并问题件，不允许再次发送!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean checkData() {
		logger.debug("para:contno =="+mContNo+"    edorno="+mEdorNo+"    edortype=" +mEdorType );
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mContNo);
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setEdorType(mEdorType);
		if(!tLPContDB.getInfo())
		{
			CError.buildErr(this, "保全合同信息查询失败!") ;

			return false;
		}
		mLPContSchema.setSchema(tLPContDB.getSchema());		
		
		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareQuest() {

		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();
		LPIssuePolSchema tLPIssuePolSchema = mLPIssuePolSet.get(1);

		String tSerialNo = PubFun1.CreateMaxNo("QustSerlNo", 20);

		// 操作员
		tLPIssuePolSchema.setBackObj(mLPContSchema.getOperator());
		tLPIssuePolSchema.setContNo(mContNo);
		tLPIssuePolSchema.setGrpContNo(mLPContSchema.getGrpContNo());
		tLPIssuePolSchema.setProposalContNo(mLPContSchema.getProposalContNo());
//		tLPIssuePolSchema.setIssueType(tLPIssuePolSchema.getIssueType().trim()); // 设置问题类型
		tLPIssuePolSchema.setIssueType(mLPContSchema.getInsuredIDType()); // 设置问题类型   //wyc
		tLPIssuePolSchema.setSerialNo(tSerialNo.toString());
		tLPIssuePolSchema.setIsueManageCom(mLPContSchema.getManageCom());
		tLPIssuePolSchema.setPrintCount(0);
		tLPIssuePolSchema.setReplyMan("");
		tLPIssuePolSchema.setReplyResult("");
		tLPIssuePolSchema.setState("");
		tLPIssuePolSchema.setOperator(mOperator);
		tLPIssuePolSchema.setManageCom(mManageCom);
		tLPIssuePolSchema.setMakeDate(tNowDate);
		tLPIssuePolSchema.setMakeTime(tNowTime);
		tLPIssuePolSchema.setModifyDate(tNowDate);
		tLPIssuePolSchema.setModifyTime(tNowTime);

		tAllLPIssuePolSet.clear();
		tAllLPIssuePolSet.add(tLPIssuePolSchema);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData(String cOperate) {
		logger.debug("cOperate:" + cOperate);
		if (cOperate.equals("DELETE")) {
			mMap.put(mLPIssuePolSet, "DELETE");
		} else if (cOperate.equals("UPDATE")) {
			mMap.put(tAllLPIssuePolSet, "UPDATE");
		} else {
			mMap.put(tAllLPIssuePolSet, "INSERT");
		}

	}
	public static void main(String[] agr)
	{
		String[] tHQ =null;
		String tCode_name = "001-残疾问卷";
		logger.debug("tCode_name:"+tCode_name);
		tHQ = tCode_name.split("-");
		logger.debug("Over!");
	}
}
