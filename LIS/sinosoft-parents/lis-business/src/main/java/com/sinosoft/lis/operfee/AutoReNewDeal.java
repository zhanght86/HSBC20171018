package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPRNPolAmntDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCRnewStateLogSchema;
import com.sinosoft.lis.tb.CalBL;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPRNPolAmntSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 自动续保核心处理
 * <p>
 * Description:
 * </p>
 * 重新计算保额保费，更新lcpol\lcduty\lcprem\lcget
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * sinosoft
 * <p>
 * Company:
 * </p>
 * sinosoft
 * 
 * @author gaoht
 * @version 1.0
 */
public class AutoReNewDeal {
private static Logger logger = Logger.getLogger(AutoReNewDeal.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 保单 */
	private LCPolBL mLCPolBL = new LCPolBL();

	// private LCPolSet NotRenewLCPolSet = new LCPolSet();

	/** 责任 */
	private LCDutyBL mLCDutyBL = new LCDutyBL();

	LCDutyBLSet mLCDutyBLSet = new LCDutyBLSet();

	private LCPolSchema mLCPolSchema = new LCPolSchema();

	private LCPremSet mLCPremSet = new LCPremSet();

	private LCPremSet mAddLCPremSet = new LCPremSet();

	private LCGetSet mLCGetSet = new LCGetSet();

	private LCDutySet mLCDutySet = new LCDutySet();

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	// /@todo*****************保全要调用此类，这里标志是否是保全调用**********************\
	private String mBQUsingFlag = "";

	private String mEdorNo = "";

	private String mEdorType = "";

	String main_xb_flag ="0";//主险续保标记 0，非主险续保，1，主险续保
	String mainpol_flag ="0";//主险标记 0，非主险，1，主险
    /** 是否需要续保标志,如果不满足续保条件,正常退出(例如:被保人年龄超出险种最大被保人年龄,则将mNoNeedFlag置为 Y ,直接进行主险的催收) */
    private String mNoNeedFlag = "N";
	ExeSQL check_ExeSQL = new ExeSQL();
	// \***********************************************************************/

	public AutoReNewDeal() {
		mBQUsingFlag = "";
		mEdorNo = "";
		mEdorType = "";
	}

	// /************************保全要调用此类，添加构造器*************************\
	public AutoReNewDeal(String vEdorNo, String vEdorType) {
		mBQUsingFlag = "BQUsing";
		mEdorNo = vEdorNo;
		mEdorType = vEdorType;
	}

	// \***********************************************************************/

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (getInputData(cInputData) == false) {
			return false;
		}
		// 数据操作校验
		if (checkData() == false) {
			return false;
		}

		// 进行业务处理
		if (dealData() == false) {
			return false;
		}
		if (prepareOutputData() == false) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLCPolBL.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
				"LCPolSchema", 0));
		return true;
	}

	private boolean checkData() {
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(mLCPolBL.getPolNo());
		LCDutySet tLCDutySet = new LCDutySet();
		tLCDutySet = tLCDutyDB.query();

		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(mLCPolBL.getPolNo());
		LCPremSet tLCPremSet = new LCPremSet();
		tLCPremSet = tLCPremDB.query();

		if (tLCDutySet.size() == 0 || tLCPremSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoReNewDeal";
			tError.functionName = "dealData";
			tError.errorMessage = "未找到有效的责任保费信息";
			this.mErrors.addOneError(tError);
			return false;
		}
       //置上主险的续保收费标记
		String Rnew_check_Sql = "select count(*) from LCPol a where AppFlag='1' "
            + "and PayToDate = PayEndDate "
            + "and RnewFlag = '-1' "
            + "and (StopFlag='0' or StopFlag is null) and GrpPolNo='00000000000000000000' "
            + "and contno='?contno?' and polno=mainpolno "
            + "and exists (select 1 from lmrisk where riskcode=a.riskcode and rnewflag='Y') " ;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(Rnew_check_Sql);
		sqlbv.put("contno", mLCPolBL.getContNo());
		int RnewCheck = Integer.parseInt( check_ExeSQL.getOneValue(sqlbv) );
		if(RnewCheck>0)
		{
			main_xb_flag="1";
		}
	   //主险标记判断
		if(mLCPolBL.getPolNo().equals(mLCPolBL.getMainPolNo()))
		{
			mainpol_flag="1";
		}

		return true;
	}

	private boolean dealData() {
		SSRS nSSRS = new SSRS();
		// 原来的保单号
		String SPolNo = mLCPolBL.getPolNo();
		//取出原来的标准保费
	    double OldStandPrem= mLCPolBL.getStandPrem();//纪录原标准保费(不含加费)
		// 最大交费次数
		String paytimes = "";

		/***********************************************************************
		 * Date 2006-3-21 Add by GaoHT Description 查询计算要素：职业类别 Reason
		 * 保全职业类别变更，只更新主险 Method 取被保人职业类别
		 **********************************************************************/
		// 取被保人职业类别
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLCPolBL.getContNo());
		tLCInsuredDB.setInsuredNo(mLCPolBL.getInsuredNo());
		if (!tLCInsuredDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "AutoReNewDeal";
			tError.functionName = "dealData";
			tError.errorMessage = "未找到有效的被保人信息";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (tLCInsuredDB.getOccupationType() != null
				&& !tLCInsuredDB.getOccupationType().equals("")) {
			mLCPolBL.setOccupationType(tLCInsuredDB.getOccupationType());
		}
		// End Job --GaoHT

		// 找到保费表的加费项提取出来
		String PremSql = "select * from lcprem where PayPlanType<>'0' and substr(PayPlanCode,1,6)='000000'"
			//如果续保险种加费终止的话，不再生成相应的投保记录，终止的加费其paytodate=paystartdate,而正常的才是paytodate=payenddate
				+" and paytodate=payenddate" 
				+" and polno='?SPolNo?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(PremSql);
		sqlbv1.put("SPolNo", SPolNo);
		logger.debug("查询险种的加费:::::::::::::" + PremSql);
		LCPremDB tLCPremDB = new LCPremDB();
		mAddLCPremSet = tLCPremDB.executeQuery(sqlbv1);
		if (mAddLCPremSet.size() > 0) {
			logger.debug("该保单下有加费");
		}

		// /************获得险种销售方式，"1"--按保额销售，"2"--按份数销售***********\
		String tBQSql = "SELECT (case when exists(select 'x' from LCDuty b where ContNo='?ContNo?' and PolNo='?SPolNo?' and exists(select 'y' from LMDuty where DutyCode=b.DutyCode and AmntFlag='1')) then '1'"
				+ "  else '2'" + "  end)" + "FROM dual";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tBQSql);
		sqlbv2.put("ContNo", mLCPolBL.getContNo());
		sqlbv2.put("SPolNo", SPolNo);
		SSRS tBQSSRS = new SSRS();
		ExeSQL tBQExeSQL = new ExeSQL();
		tBQSSRS = tBQExeSQL.execSQL(sqlbv2);
		if (tBQSSRS == null || tBQSSRS.MaxRow <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoReNewDeal";
			tError.functionName = "dealData";
			tError.errorMessage = "获得险种销售方式时产生错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tSellMode = StrTool.cTrim(tBQSSRS.GetText(1, 1));
		// \********************************************************************/

		// 新生成续保保单号--彭千让加上投保单号
		String tLimit = PubFun.getNoLimit(mLCPolBL.getManageCom());
		String tPolNo = PubFun1.CreateMaxNo("POLNO", tLimit);

		// 保全项目减保与现保额对比,取最小值
		/* @todo*************************GuanXD******************************** */
		String BQsql = "select * from LPRNPolAmnt where polno = '?SPolNo?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.put("SPolNo", SPolNo);
		if ("BQUsing".equals(this.mBQUsingFlag)) {
			BQsql = BQsql + " and EdorNo='?EdorNo?' and EdorType='?EdorType?'";
			sqlbv3.put("EdorNo", this.mEdorNo);
			sqlbv3.put("EdorType", this.mEdorType);
		} else {
			BQsql = BQsql + " and State ='1'";
		}
		sqlbv3.sql(BQsql);
		LPRNPolAmntDB tLPRNPolAmntDB = new LPRNPolAmntDB();
		LPRNPolAmntSet tLPRNPolAmntSet = new LPRNPolAmntSet();
		tLPRNPolAmntSet = tLPRNPolAmntDB.executeQuery(sqlbv3);

		// 查询交费次数
		String LCPremSql = "select max(paytimes) from lcprem where polno = '?SPolNo?'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(LCPremSql);
		sqlbv4.put("SPolNo", SPolNo);
		ExeSQL pExeSQL = new ExeSQL();
		nSSRS = pExeSQL.execSQL(sqlbv4);
		if (nSSRS.getMaxRow() > 0) {
			paytimes = nSSRS.GetText(1, 1);
		}

		LCDutyDB tLCDutyDB = new LCDutyDB();
		LCDutySet tLCDutySet = new LCDutySet();
		String tsql = "select * from lcduty where polno ='?SPolNo?'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tsql);
		sqlbv5.put("SPolNo", SPolNo);
		tLCDutySet = tLCDutyDB.executeQuery(sqlbv5);
		mLCDutyBLSet.add(tLCDutySet);

		if (tLPRNPolAmntSet.size() > 0) {
			for (int t = 1; t <= mLCDutyBLSet.size(); t++) {
				for (int n = 1; n <= tLPRNPolAmntSet.size(); n++) {
					String Dutycode = tLPRNPolAmntSet.get(n).getDutyCode();
					double BQAmnt = tLPRNPolAmntSet.get(n).getAmnt();
					if (mLCDutyBLSet.get(t).getDutyCode().equals(Dutycode)) {
						if ("2".equals(tSellMode)) {
							// 按份数销售
							mLCDutyBLSet.get(t).setMult(BQAmnt);
						} else {
							// 按保额销售
							mLCDutyBLSet.get(t).setAmnt(BQAmnt);
						}
					} else {
						mLCDutyBLSet.remove(mLCDutyBLSet.get(t));
					}
				}
			}
		}

		nSSRS = new SSRS();
		tsql = "select (case when sum(Amnt) is not null then sum(Amnt) else 0 end) from LPRNPolAmnt where polno = '?SPolNo?'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.put("SPolNo", SPolNo);
		if ("BQUsing".equals(this.mBQUsingFlag)) {
			tsql = tsql + " and EdorNo='?EdorNo?' and EdorType='?EdorType?'";
			sqlbv6.put("EdorNo", this.mEdorNo);
			sqlbv6.put("EdorType", this.mEdorType);
		} else {
			tsql = tsql + " and State ='1'";
		}
		sqlbv6.sql(tsql);
		ExeSQL zExeSQL = new ExeSQL();
		nSSRS = zExeSQL.execSQL(sqlbv6);

		if (nSSRS.getMaxRow() > 0) {
			String Amnt = nSSRS.GetText(1, 1);
			if (!Amnt.equals("null")) {
				double tAmnt = Double.parseDouble(Amnt);

				if (tAmnt < mLCPolBL.getAmnt())
					if ("2".equals(tSellMode)) {
						// 按份数销售
						mLCPolBL.setMult(tAmnt);
					} else {
						// 按保额销售
						mLCPolBL.setAmnt(tAmnt);
					}
			}
		}

		// 自动续保生效日对应主险续期时的缴费对应日
		mLCPolBL.setCValiDate(mLCPolBL.getEndDate());

		// 自动续保保费保额计算要素变化
		// 被保人年龄
		int tInsuredAge = PubFun.calInterval(mLCPolBL.getInsuredBirthday(),
				mLCPolBL.getCValiDate(), "Y");
		logger.debug("保单生效对应日=========" + mLCPolBL.getCValiDate());
		logger.debug("被保人生日=======" + mLCPolBL.getInsuredBirthday());

		// 校验现被保人年龄是否符合条款
		// String Sql = "select maxinsuredage from lmriskapp where riskcode = '"
		// + mLCPolBL.getRiskCode() + "'";
		String Sql = "select a.maxrnewage from LMRiskRole a where a.riskcode = '?riskcode?' and a.riskrole = '01' and a.riskrolesex = '2'";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(Sql);
		sqlbv7.put("riskcode", mLCPolBL.getRiskCode());
		zExeSQL = new ExeSQL();
		nSSRS = zExeSQL.execSQL(sqlbv7);
		if(nSSRS.MaxRow>=1)//如果有条款限制，才需要判断。
		{
			String MaxInsuredAge = nSSRS.GetText(1, 1);
			int MaxAge = Integer.parseInt(MaxInsuredAge);
			if (tInsuredAge > MaxAge) {
				CError tError = new CError();
				tError.moduleName = "AutoRenewDeal";
				tError.functionName = "getInputData";
				tError.errorMessage = "保单" + mLCPolBL.getContNo() + "被保人年龄"
						+ tInsuredAge + "岁，大于条款的被保人最大年龄" + MaxAge + "岁";
				this.mErrors.addOneError(tError);
				// NotRenewLCPolSet.add(mLCPolBL.getSchema());
				mNoNeedFlag="Y";
				mTransferData.setNameAndValue("NoNeedFlag", mNoNeedFlag);
				return true;
			}
		}

		mLCPolBL.setPolNo(tPolNo); 
		if(main_xb_flag.equals("1")) //主险续保
		{
			if(mainpol_flag.equals("1")) //处理主险的话，需要置上mainpolno，同新polno
			{
			   mLCPolBL.setMainPolNo(tPolNo);
			}
			else//处理附加险，需要查询主险投保单记录
			{
				String tMainPolno="";
				String str_mainpolno="select mainpolno from lcpol where contno='?contno?' and polno=mainpolno and appflag='9'";
				SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
				sqlbv8.sql(str_mainpolno);
				sqlbv8.put("contno", mLCPolBL.getContNo());
				tMainPolno=check_ExeSQL.getOneValue(sqlbv8);
				mLCPolBL.setMainPolNo(tMainPolno);
			}
		}
		mLCPolBL.setProposalNo(tPolNo); //续保件投保单记录，保单号和投保单号一致，都是新生成的投保单号
		mLCPolBL.setInsuredAppAge(tInsuredAge);
		mLCPolBL.setMakeDate(CurrentDate);
		mLCPolBL.setMakeTime(CurrentTime);
		mLCPolBL.setModifyDate(CurrentDate);
		mLCPolBL.setModifyTime(CurrentTime);
		mLCPolBL.setAppFlag("9");
		mLCPolBL.setRenewCount(mLCPolBL.getRenewCount() + 1);
		mLCPolBL.setRnewFlag(-1); // 自动续保标志

		// 通过险种号查询责任
		for (int a = 1; a <= mLCDutyBLSet.size(); a++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tLCDutySchema = mLCDutyBLSet.get(a).getSchema();
			tLCDutySchema.setPolNo(tPolNo);
			mLCDutyBLSet.set(a, tLCDutySchema);
		}

		/** @todo 通过保额计算保费* */
		CalBL tCalBL;
		tCalBL = new CalBL(mLCPolBL, mLCDutyBLSet, "");
		if (tCalBL.calPol() == false) {
			CError.buildErr(this, "险种保单重新计算时失败:"
					+ tCalBL.mErrors.getFirstError());
			return false;
		}

		// 得到续保保单相关信息
		mLCPolSchema = tCalBL.getLCPol().getSchema();
		logger.debug("new CValiDate()=" + mLCPolSchema.getCValiDate());

		// 处理附加险续保的paytodate
		String newpaytodate = mLCPolSchema.getPayEndDate();
		mLCPolSchema.setPaytoDate(newpaytodate);

		mLCPolSchema.setAppFlag("9"); // 续保处理的中间过程，业务操作完成后应制回“1”
		logger.debug("结束时间===========" + mLCPolSchema.getPayEndDate());
		logger.debug("应缴时间===========" + mLCPolSchema.getPaytoDate());

		// 保费表
		mLCPremSet = tCalBL.getLCPrem();

		mLCPolBL.setOperator(mGlobalInput.Operator);

		for (int b = 1; b <= mLCPremSet.size(); b++) {
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLCPremSchema.setSchema(mLCPremSet.get(b));
			if (paytimes.equals("null") && paytimes != null)
				paytimes = "0";
			int u = Integer.parseInt(paytimes);
			tLCPremSchema.setPayTimes(u);
			tLCPremSchema.setOperator(mGlobalInput.Operator);
			tLCPremSchema.setMakeDate(CurrentDate);
			tLCPremSchema.setMakeTime(CurrentTime);
			tLCPremSchema.setModifyDate(CurrentDate);
			tLCPremSchema.setModifyTime(CurrentTime);
			tLCPremSchema.setPaytoDate(mLCPolSchema.getPaytoDate());
			mLCPremSet.set(b, tLCPremSchema);
		}

		// 保费加费
		if (mAddLCPremSet.size() > 0) 
		{
			double AddPrem = 0;
			double addRate=mLCPolBL.getStandPrem()/OldStandPrem;
			for (int i = 1; i <= mAddLCPremSet.size(); i++) 
			{
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tLCPremSchema = mAddLCPremSet.get(i);
				tLCPremSchema.setPrem(tLCPremSchema.getPrem()*addRate);
				tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem()*addRate);
				tLCPremSchema.setPayTimes(paytimes);
				tLCPremSchema.setOperator(mGlobalInput.Operator);
				tLCPremSchema.setMakeDate(CurrentDate);
				tLCPremSchema.setMakeTime(CurrentTime);
				tLCPremSchema.setModifyDate(CurrentDate);
				tLCPremSchema.setModifyTime(CurrentTime);
				tLCPremSchema.setPaytoDate(mLCPolSchema.getPaytoDate());
				tLCPremSchema.setPolNo(mLCPolSchema.getPolNo());
				tLCPremSchema.setPayEndDate(mLCPremSet.get(1).getPayEndDate());
				tLCPremSchema.setPayStartDate(mLCPremSet.get(1)
						.getPayStartDate());
				mAddLCPremSet.set(i, tLCPremSchema);
				AddPrem = AddPrem + tLCPremSchema.getPrem();
			}
			mLCPolSchema.setPrem(mLCPolSchema.getPrem() + AddPrem);
			mLCPremSet.add(mAddLCPremSet);
		}

		// 领取表
		mLCGetSet = tCalBL.getLCGet();
		for (int b = 1; b <= mLCGetSet.size(); b++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLCGetSchema = mLCGetSet.get(b).getSchema();
			tLCGetSchema.setOperator(mGlobalInput.Operator);
			tLCGetSchema.setMakeDate(CurrentDate);
			tLCGetSchema.setMakeTime(CurrentTime);
			tLCGetSchema.setModifyDate(CurrentDate);
			tLCGetSchema.setModifyTime(CurrentTime);
			tLCGetSchema.setGetStartDate(mLCPolSchema.getCValiDate());
			tLCGetSchema.setGettoDate(mLCPolSchema.getCValiDate());
			tLCGetSchema.setGetEndDate(mLCPolSchema.getPaytoDate());
			tLCGetSchema.setGetEndState("0");
			mLCGetSet.set(b, tLCGetSchema);
		}

		// 责任表
		mLCDutySet = tCalBL.getLCDuty();
		for (int i = 1; i <= mLCDutySet.size(); i++) {
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tLCDutySchema = mLCDutySet.get(i).getSchema();
			tLCDutySchema.setOperator(mGlobalInput.Operator);
			mLCDutySet.set(i, tLCDutySchema);
		}
		mTransferData.setNameAndValue("NoNeedFlag", mNoNeedFlag);  // 一旦能走到此处说明mNoNeedFlag='N'
		mTransferData.setNameAndValue("OsPolNo", SPolNo);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// public LCPolSet getNotRenewPol()
	// {
	// return NotRenewLCPolSet;
	// }

	private boolean prepareOutputData() {
		mResult.clear();
		logger.debug("payenddate=====" + mLCPolSchema.getPayEndDate());
		logger.debug("paytodate======" + mLCPolSchema.getPaytoDate());
		mResult.add(mTransferData);
		mResult.add(mLCPolSchema);
		mResult.add(mLCPremSet);
		mResult.add(mLCGetSet);
		mResult.add(mLCDutySet);
		return true;
	}

}
