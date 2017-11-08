package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.Hashtable;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.RnewPENoticeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPPersonDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.RnewPENoticeItemSchema;
import com.sinosoft.lis.schema.RnewPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.RnewPENoticeItemSet;
import com.sinosoft.lis.vschema.RnewPENoticeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统体检资料录入部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 */
public class RnewAutoHealthBL {
private static Logger logger = Logger.getLogger(RnewAutoHealthBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mManageCom;
	private String mpassflag; // 通过标记

	private String mEdorNo = ""; // 批单号
	private String mEdorType = ""; // 批改类型

	private FDate fDate = new FDate();

	private String mInsuredNo = "";

	/** 业务处理相关变量 */
	private LCContSet mLCContSet = new LCContSet();

	private LCContSchema mLCContSchema = new LCContSchema();

	/** 核保主表 */
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();

	/** 体检资料主表 */
	private RnewPENoticeSet mRnewPENoticeSet = new RnewPENoticeSet();
	private RnewPENoticeSet mAllRnewPENoticeSet = new RnewPENoticeSet();

	private RnewPENoticeSchema mRnewPENoticeSchema = new RnewPENoticeSchema();
	/** 体检资料项目表 */
	private RnewPENoticeItemSet mRnewPENoticeItemSet = new RnewPENoticeItemSet();
	private RnewPENoticeItemSet mAllRnewPENoticeItemSet = new RnewPENoticeItemSet();

	private GlobalInput mGlobalInput = new GlobalInput();
	
	//tongmeng 2008-10-13 add
	//增加体检类型和体检项目
	private String mHealthCode = "";
	private String mHealthItem = "";
	private String mOtherItem = "";
	private TransferData mTransferData = new TransferData();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private String mAction = "";
	//缓存体检项目
	private Hashtable mItemHashtable = new Hashtable();
	public RnewAutoHealthBL() {
	}

	String mPrtSeq;

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数
		mAction = cOperate;
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		logger.debug("---UWAutoHealthBL getInputData---");
		if(!this.mAction.equals("DELETE"))
		{
		// 校验数据是否打印
		if (!checkPrint()) {
			return false;
		}
		}
		logger.debug("--checkPrintFalse-");
		logger.debug("---UWAutoHealthBL checkData---");
		if(!this.mAction.equals("DELETE"))
		{
		// 数据操作业务处理
		if (!dealData()) {
				// continue;
				return false;
			} else {
				flag = 1;
				j++;

			}
		}
		else
		{
			flag = 1;
		}

		if (flag == 0) {
			return false;
		}

		logger.debug("---UWAutoHealthBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---UWAutoHealthBL prepareOutputData---");
		// 数据提交
		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError.buildErr(this,"数据提交失败");
			return false;
		}
		logger.debug("UWManuAddChkBL Submit OK!");
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

		//先获取体检项目编码和名称,缓存起来,以备后面使用
		String tSQL = "select subhealthcode,subhealthname from ldhealthsub ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		for(int i=1;i<=tSSRS.getMaxRow();i++)
		{
			this.mItemHashtable.put(tSSRS.GetText(i,1),tSSRS.GetText(i,2));
		}
		// 健康信息
		if (prepareHealth() == false) {
			return false;
		}

		// 打印队列
		/*
		if (print() == false) {
			return false;
		}*/
		/*
		if (mEdorNo == null || mEdorNo.equals("") || mEdorNo.equals("null")) {
			if (modifyActivityStatus() == false) {
				return false;
			}
		}*/

		mAllRnewPENoticeSet.add(mRnewPENoticeSet);
		mAllRnewPENoticeItemSet.add(mRnewPENoticeItemSet);

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0));
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			
			mOperate = mGlobalInput.Operator;
			mManageCom = mGlobalInput.ManageCom;
			mEdorNo = (String)mTransferData.getValueByName("EdorNo");
			mEdorType = (String) mTransferData.getValueByName("EdorType");
			this.mHealthCode = (String) mTransferData.getValueByName("HealthCode");
			this.mHealthItem = (String) mTransferData.getValueByName("HealthItem");
			mOtherItem = (String)mTransferData.getValueByName("OtherItem");
			if((mHealthItem==null||mHealthItem.equals("")||mHealthItem.equals("#"))&&(mOtherItem==null||mOtherItem.trim().equals("")))
			{
				CError .buildErr(this,"请选择体检项目!");
				return false;
			}
			logger.debug("EdorNo= " + mEdorNo + "EdorType=" + mEdorType);
			this.mRnewPENoticeSchema.setSchema((RnewPENoticeSchema) cInputData
					.getObjectByObjectName("RnewPENoticeSchema", 0));

			mInsuredNo = mRnewPENoticeSchema.getCustomerNo();
			int flag = 0; // 怕判断是不是所有保单都失败
			int j = 0; // 符合条件保单个数

				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mRnewPENoticeSchema.getContNo());
				mLCContSet = tLCContDB.query();
				if (mLCContSet.size() == 0) {
					// @@错误处理
					CError .buildErr(this,"投保单查询失败");
					return false;
				} else {
					mLCContSchema = mLCContSet.get(1);
					flag = 1;
					j++;

				}


			if (flag == 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 校验是否打印
	 * 
	 * @return
	 */
	private boolean checkPrint() {
		RnewPENoticeDB tRnewPENoticeDB = new RnewPENoticeDB();
		tRnewPENoticeDB.setContNo(this.mRnewPENoticeSchema.getContNo());
		tRnewPENoticeDB.setCustomerNo(this.mRnewPENoticeSchema.getCustomerNo());
		tRnewPENoticeDB.setCustomerType(this.mRnewPENoticeSchema.getCustomerType());
		RnewPENoticeSet tRnewPENoticeSet = tRnewPENoticeDB.query();
		String tCustomerType = mRnewPENoticeSchema.getCustomerType();
		if(tCustomerType.equals("A"))
		{
			tCustomerType = "投保人";
		}
		else
		{
			tCustomerType = "被保人";
		}
		if (tRnewPENoticeSet.size() == 0) {
			if(mAction.equals("UPDATE"))
			{
				CError.buildErr(this,tCustomerType+mRnewPENoticeSchema.getCustomerNo()+"没有该条体检记录,不能修改!");
				return false;

			}
			logger.debug("come in if 9");
		} else {
			logger.debug("come in if 7");
			RnewPENoticeSchema tRnewPENoticeSchema ;
			for(int i=1; i<=tRnewPENoticeSet.size();i++ )
			{
				tRnewPENoticeSchema = new RnewPENoticeSchema();
				tRnewPENoticeSchema = tRnewPENoticeSet.get(i);
				if(tRnewPENoticeSchema.getPrintFlag()==null&&this.mAction.equals("INSERT"))
				{
					CError.buildErr(this,tCustomerType+mRnewPENoticeSchema.getCustomerNo()+"体检通知已经录入,不能录入新体检资料!");
					return false;
				}
				else if (tRnewPENoticeSchema.getPrintFlag()!=null&&!tRnewPENoticeSchema.getPrintFlag().equals("2")) {
					CError.buildErr(this,tCustomerType+mRnewPENoticeSchema.getCustomerNo()+"体检通知已经打印未回收,不能新增和修改新体检资料!");
					return false;
				}
			}
			
				
		}
		logger.debug("come in if 8");
		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean print() {

		// 处于未打印状态的通知书在打印队列中只能有一个
		// 条件：同一个单据类型，同一个其它号码，同一个其它号码类型
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		logger.debug("@@@@@@@@@@@@@@@@@@@@@@@mEdorNo=" + mEdorNo);
		if (mEdorNo == null || mEdorNo.equals("") || mEdorNo.equals("null")) {
			tLOPRTManagerDB.setCode(PrintManagerBL.CODE_GRP_PE); // 团单体检
			tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 保单号
		} else {
			tLOPRTManagerDB.setCode(PrintManagerBL.CODE_GRP_EDORPE); // 团单体检
			tLOPRTManagerDB.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
		}
	//	tLOPRTManagerDB.setOtherNo(mPolNo);

		tLOPRTManagerDB.setStandbyFlag1(mInsuredNo);
		tLOPRTManagerDB.setStateFlag("0");

		LOPRTManagerSet tLOPRTManagerSet = tLOPRTManagerDB.query();
		if (tLOPRTManagerSet == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "preparePrint";
			tError.errorMessage = "查询打印管理表信息出错!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLOPRTManagerSet.size() != 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorUWAutoHealthAfterInitService";
			tError.functionName = "preparePrint";
			tError.errorMessage = "在打印队列中已有一个处于未打印状态的体检通知书!";
			this.mErrors.addOneError(tError);
			return false;
		}

		RnewPENoticeDB tRnewPENoticeDB = new RnewPENoticeDB();
	//	tRnewPENoticeDB.setContNo(mPolNo);
		tRnewPENoticeDB.setCustomerNo(mInsuredNo);
		RnewPENoticeSet tRnewPENoticeSet = tRnewPENoticeDB.query();
		if (tRnewPENoticeSet.size() > 0) {
			for (int i = 1; i <= tRnewPENoticeSet.size(); i++) {
				if (!tRnewPENoticeSet.get(i).getPrintFlag().equals("2")) {
					CError tError = new CError();
					tError.moduleName = "UWAutoHealthBL";
					tError.functionName = "preparePrint";
					tError.errorMessage = "已经存在未回销的体检通知!";
					this.mErrors.addOneError(tError);
					return false;

				}
			}
		}

		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("URGEInterval");

		if (tLDSysVarDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "UWSendPrintBL";
			tError.functionName = "prepareURGE";
			tError.errorMessage = "没有描述催发间隔!";
			this.mErrors.addOneError(tError);
			return false;
		}
		FDate tFDate = new FDate();
		int tInterval = Integer.parseInt(tLDSysVarDB.getSysVarValue());
		logger.debug(tInterval);

		Date tDate = PubFun.calDate(tFDate.getDate(PubFun.getCurrentDate()),
				tInterval, "D", null);
		logger.debug(tDate); // 取预计催办日期

		// 准备打印管理表数据
		LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
		logger.debug(mGlobalInput.ComCode);

		logger.debug("mPrtSeq=" + mPrtSeq);
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
	//	mLOPRTManagerSchema.setOtherNo(mPolNo);

		mLOPRTManagerSchema.setStandbyFlag3(mLCContSchema.getGrpContNo()); // modify
																			// by
																			// zhangxing
		logger.debug("StandbyFlag3="
				+ mLOPRTManagerSchema.getStandbyFlag3());
		if (mEdorNo == null || mEdorNo.equals("") || mEdorNo.equals("null")) {
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_INDPOL); // 新契约保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_PE); // 体检
		} else {
			mLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 保单号
			mLOPRTManagerSchema.setCode(PrintManagerBL.CODE_GRP_EDORPE); // 保全体检
		}
		mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);

		mLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setPatchFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

		mLOPRTManagerSchema.setForMakeDate(tDate);

		mLOPRTManagerSchema.setStandbyFlag1(mInsuredNo); // 被保险人编码
		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);

	//	mLOPRTManagerSet.add(mLOPRTManagerSchema);
		return true;
	}

	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareHealth() {

		RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();
		tRnewPENoticeSchema.setSchema(this.mRnewPENoticeSchema);

		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String tPrtSeq = "";
		if(this.mAction.equals("INSERT"))
		{
			//tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
			tPrtSeq = PubFun1.CreateMaxNo("UWPRTSEQ", PrintManagerBL.CODE_PRnewPE);
		}
		else
		{
			tPrtSeq = mRnewPENoticeSchema.getPrtSeq();
		}
		mPrtSeq = tPrtSeq;
		tRnewPENoticeSchema.setPrtSeq(mPrtSeq);
		logger.debug("mPrtSeq=" + mPrtSeq);
		
		RnewPENoticeDB tRnewPENoticeDB = new RnewPENoticeDB();
		if(this.mAction.equals("UPDATE"))
		{
			tRnewPENoticeDB.setSchema(tRnewPENoticeSchema);
			tRnewPENoticeDB.setProposalContNo(this.mLCContSchema.getProposalContNo());
			if(!tRnewPENoticeDB.getInfo())
			{
				CError.buildErr(this,"该被保人没有录过体检信息,不能修改!");
				return false;
			}
		}
		// 取体检人姓名
		String PEName = "";
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(mInsuredNo);
		if (!tLDPersonDB.getInfo()) {
			LPPersonDB tLPPersonDB = new LPPersonDB();
			tLPPersonDB.setEdorNo(mEdorNo);
			tLPPersonDB.setEdorType(mEdorType);
			tLPPersonDB.setCustomerNo(mInsuredNo);
			if (!tLPPersonDB.getInfo()) {
				// @@错误处理
				CError.buildErr(this,"取被体检客户姓名失败");
				return false;
			} else {
				PEName = tLPPersonDB.getName();
			}
		} else {
			PEName = tLDPersonDB.getName();
		}
		
		//处理体检总表
		tRnewPENoticeSchema.setGrpContNo(mLCContSchema.getGrpContNo());
		tRnewPENoticeSchema.setProposalContNo(mLCContSchema.getProposalContNo());
		tRnewPENoticeSchema.setName(PEName);
		//tongmeng 2008-10-14 modify
		//体检通知书标记设置为空,在发送核保通知书处统一发放
		tRnewPENoticeSchema.setPrintFlag(null);
		tRnewPENoticeSchema.setAppName(mLCContSchema.getAppntName());
		tRnewPENoticeSchema.setAgentCode(mLCContSchema.getAgentCode());
		tRnewPENoticeSchema.setManageCom(mLCContSchema.getManageCom());
		tRnewPENoticeSchema.setOperator(mOperate); // 操作员
		if(!this.mAction.equals("UPDATE"))
		{
			tRnewPENoticeSchema.setMakeDate(mCurrentDate);
			tRnewPENoticeSchema.setMakeTime(mCurrentTime);
		}
		else
		{
			tRnewPENoticeSchema.setMakeDate(tRnewPENoticeDB.getMakeDate());
			tRnewPENoticeSchema.setMakeTime(tRnewPENoticeDB.getMakeTime());
			
		}
		tRnewPENoticeSchema.setModifyDate(mCurrentDate);
		tRnewPENoticeSchema.setModifyTime(mCurrentTime);


		mRnewPENoticeSet.clear();
		mRnewPENoticeSet.add(tRnewPENoticeSchema);

		// 取体检资料项目信息
		//获取体检项目
		mRnewPENoticeItemSet.clear();
		String[] tPEItem = null;
		if(this.mHealthItem!=null&&!this.mHealthItem.equals("#"))
		{
			tPEItem = mHealthItem.split("#");
			for(int n=0;n<tPEItem.length;n++)
			{
				String tCurrItem = tPEItem[n];
				String tCurrItemName = (String)this.mItemHashtable.get(tCurrItem);
				if(!tCurrItem.equals(""))
				{
					RnewPENoticeItemSchema tRnewPENoticeItemSchema = new RnewPENoticeItemSchema();
					tRnewPENoticeItemSchema.setProposalContNo(mLCContSchema.getProposalContNo());
					tRnewPENoticeItemSchema.setContNo(mLCContSchema.getContNo());
					tRnewPENoticeItemSchema.setPrtSeq(mPrtSeq);
					tRnewPENoticeItemSchema.setPEItemCode(tCurrItem); // 核保规则编码
					tRnewPENoticeItemSchema.setPEItemName(tCurrItemName); // 核保出错信息
					tRnewPENoticeItemSchema.setModifyDate(this.mCurrentDate); // 当前值
					tRnewPENoticeItemSchema.setModifyTime(this.mCurrentTime);
					mRnewPENoticeItemSet.add(tRnewPENoticeItemSchema);
				}
			}
		}
		if(this.mOtherItem!=null&&!this.mOtherItem.equals(""))
		{
			RnewPENoticeItemSchema tRnewPENoticeItemSchema = new RnewPENoticeItemSchema();
			tRnewPENoticeItemSchema.setProposalContNo(mLCContSchema.getProposalContNo());
			tRnewPENoticeItemSchema.setContNo(mLCContSchema.getContNo());
			tRnewPENoticeItemSchema.setPrtSeq(mPrtSeq);
			tRnewPENoticeItemSchema.setPEItemCode("other"); // 核保规则编码
			tRnewPENoticeItemSchema.setPEItemName(mOtherItem); // 核保出错信息
			tRnewPENoticeItemSchema.setModifyDate(this.mCurrentDate); // 当前值
			tRnewPENoticeItemSchema.setModifyTime(this.mCurrentTime);
			mRnewPENoticeItemSet.add(tRnewPENoticeItemSchema);
		}
		
		/*
		if (mEdorNo == null || mEdorNo.equals("") || mEdorNo.equals("null")) {
			// 核保主表信息			LCCWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setContNo(mPolNo);

			if (tLCUWMasterDB.getInfo() == false) {
				// @@错误处理
				CError .buildErr(this,"无核保主表信息");
				return false;
			}

			mLCCUWMasterSchema = tLCUWMasterDB.getSchema();
*/
			// 校验是否已经发放核保通知书
			/*
			if (mLCCUWMasterSchema.getPrintFlag().equals("Y")) {
				CError tError = new CError();
				tError.moduleName = "UWAutoHealthBL";
				tError.functionName = "prepareHealth";
				tError.errorMessage = "已经发核保通知不可录入!";
				this.mErrors.addOneError(tError);
				return false;
			}*/
		//	mLCCUWMasterSchema.setHealthFlag("1");
	//	}
		logger.debug(" end prepareHealth()");

		return true;
	}

	/*
	 * 修改核保任务状态
	 */
	/*
	private boolean modifyActivityStatus() {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		tLWMissionDB.setMissionProp1(mLCContSchema.getGrpContNo());
		tLWMissionDB.setActivityID("0000002004");
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionSet = tLWMissionDB.query();
		if (tLWMissionSet == null || tLWMissionSet.size() != 1) {
			CError tError = new CError();
			tError.moduleName = "UWAutoHealthBL";
			tError.functionName = "prepareHealth";
			tError.errorMessage = "查询核保任务结点失败!";
			this.mErrors.addOneError(tError);
			return false;

		}
		mLWMissionSchema = tLWMissionSet.get(1);
		mLWMissionSchema.setActivityStatus("2");
		return true;
	}*/

	/**
	 * a 准备需要保存的数据
	 */
	private boolean prepareOutputData() {

		MMap map = new MMap();
		if(this.mAction.equals("DELETE"))
		{
			this.mPrtSeq = mRnewPENoticeSchema.getPrtSeq();
		}
		String tSQL = "delete from RnewPENotice where contno='?contno?' "
		            + " and prtseq ='?prtseq?' ";
		String tSQL1 = "delete from RnewPENoticeItem where contno='?contno?' "
        + " and prtseq ='?prtseq?' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSQL);
		sqlbv1.put("contno", this.mLCContSchema.getContNo());
		sqlbv1.put("prtseq", this.mPrtSeq);
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSQL1);
		sqlbv2.put("contno", this.mLCContSchema.getContNo());
		sqlbv2.put("prtseq", this.mPrtSeq);
		map.put(sqlbv1, "DELETE");
		if(!this.mAction.equals("DELETE"))
		{
			map.put(mAllRnewPENoticeSet, "INSERT");
		}
		
			map.put(sqlbv2, "DELETE");
		if(!this.mAction.equals("DELETE"))
		{
			map.put(mAllRnewPENoticeItemSet, "INSERT");
		}
	/*	if (mEdorNo == null || mEdorNo.equals("") || mEdorNo.equals("null")) {
			//map.put(mLWMissionSchema, "UPDATE");
			map.put(mLCCUWMasterSchema, "UPDATE");
		}*/
		//map.put(mLOPRTManagerSet, "INSERT");

		mResult.add(map);

		return true;

	}

}
