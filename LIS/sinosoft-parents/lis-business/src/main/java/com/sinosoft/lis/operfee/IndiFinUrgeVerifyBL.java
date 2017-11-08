package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LCPremBL;
import com.sinosoft.lis.bl.LJAPayBL;
import com.sinosoft.lis.bl.LJAPayPersonBL;
import com.sinosoft.lis.bl.LJSPayBL;
import com.sinosoft.lis.bl.LJSPayPersonBL;
import com.sinosoft.lis.bl.LJTempFeeBL;
import com.sinosoft.lis.bl.LJTempFeeClassBL;
import com.sinosoft.lis.customer.FICustomer;
import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.customer.FICustomerRN;
import com.sinosoft.lis.db.LASPayPersonDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCRnewStateHistoryDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.finfee.TempFeeQueryForUrgeGetUI;
import com.sinosoft.lis.finfee.TempFeeQueryUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LASPayPersonSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCCustomerAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LASPayPersonSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCCustomerAccTraceSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCRnewStateHistorySet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LOPRTManagerSubSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeTraceSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HZM
 * @author Yuanaq
 * @version 1.0
 */

public class IndiFinUrgeVerifyBL {
private static Logger logger = Logger.getLogger(IndiFinUrgeVerifyBL.class);
	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	// 保存操作员和管理机构的类
	private GlobalInput mGI = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	private String CurrentDate = PubFun.getCurrentDate();

	private String CurrentTime = PubFun.getCurrentTime();

	private String serNo = ""; // 流水号

	private String tLimit = "";

	private String payNo = ""; // 交费收据号
	
	private boolean actuVerifyFlag = false;

	private MMap map = new MMap();

	// 暂收费表
	private LJTempFeeBL mLJTempFeeBL = new LJTempFeeBL();

	private LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();
	private LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet(); //查询出的需要核销的暂收费记录
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet(); //最后传入后台更新的暂收费记录
	private double tempMoney = 0.0; //暂收费金额总额
	private String mTempFeeNo=""; //暂收费号

	private Reflections mReflections = new Reflections();
	private ExeSQL xExeSQL = new ExeSQL();
	// 暂收费分类表
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();

	private LJTempFeeClassSet mLJTempFeeClassNewSet = new LJTempFeeClassSet();

	// 应收个人交费表
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();

	private LASPayPersonSet mLASPayPersonSet = new LASPayPersonSet();

	// 应收总表
	private LJSPayBL mLJSPayBL = new LJSPayBL();
	private LJSPaySet mLJSPaySet = new LJSPaySet();

	private LJSPaySchema mLJSPaySchema = new LJSPaySchema();

	// 实收个人交费表
	private LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();

	// 实收总表
	private LJAPayBL mLJAPayBL = new LJAPayBL();

	private LJAPaySchema mLJAPaySchema = new LJAPaySchema();
	
	private LJAPaySet mLJAPaySet = new LJAPaySet();

	// 个人合同表
	private LCContSchema mLCContSchema = new LCContSchema();

	// 个人保单表
	private LCPolSet mLCPolSet = new LCPolSet();

	// 保费项表
	private LCPremSet mLCPremSet = new LCPremSet();

	private LCPremSet mLCPremNewSet = new LCPremSet();

	// 保险责任表LCDuty
	private LCDutySet mLCDutySet = new LCDutySet();

	private LCDutySet mLCDutyNewSet = new LCDutySet();

	private LCGetSet mLCGetSet = new LCGetSet();
	
	//续保轨迹表
	private LCRnewStateHistorySet mLCRnewStateHistorySet = new LCRnewStateHistorySet();
	//保单状态表
	private LCContStateSet mLCContStateSet_INSERT = new LCContStateSet();
	private LCContStateSet mLCContStateSet_UPDATE = new LCContStateSet();
	private LCContStateSet mLCContStateSet_DEL = new LCContStateSet();
	//打印管理表
	LOPRTManagerSet mLOPRTManagerSet_UPDATE = new LOPRTManagerSet();
	LOPRTManagerSet mLOPRTManagerSet_INSERT = new LOPRTManagerSet();
	
	LOPRTManagerSubSet mLOPRTManagerSubSet_INSERT = new LOPRTManagerSubSet();

	// 自动续保需要处理的原始数据容器
	private LCDutySet mLCDutySetOLD = new LCDutySet();

	private LCPremSet mLCPremSetOLD = new LCPremSet();

	private LCGetSet mLCGetSetOLD = new LCGetSet();

	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();

	// 保险帐户表
	// private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();

	// 保险帐户表记价履历表
	// private LCInsureAccTraceSet mLCInsureAccTraceSet = new
	// LCInsureAccTraceSet();

	private LCCustomerAccTraceSet mLCCustomerAccTraceSet = new LCCustomerAccTraceSet();
	
    /*费用*/
    private LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
    private LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
    private LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();

    /*帐户*/
    private LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();
    private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
    private LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();

    //主险币种
    private String main_Currency = "";
	// 自动续保核销处理用到的保单状态表

	// 业务处理相关变量
	public IndiFinUrgeVerifyBL() {
	}

	// 传输数据的公共方法
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("After getInputData");

		// 加锁contno，LR0002：续期核销
		if (!mPubLock.lock(mLJTempFeeBL.getOtherNo(), "LR0002", mGI.Operator)) {
			CError tError = new CError(mPubLock.mErrors.getLastError());
			this.mErrors.addOneError(tError);
			return false;
		}

		try {
			// 进行业务处理
			if (!checkdata()) 
			{
				return false;
			}
			if (!dealData())
				return false;
			logger.debug("After dealData()");

			// 准备往后台的数据
			if (!prepareOutputData())
				return false;
			logger.debug("After prepareOutputData()");

			// 保全无此动作
			if (!mBQUseFlag.equals("BQ")) {
				PubSubmit tPubSubmit = new PubSubmit();
				if (tPubSubmit.submitData(mInputData, mOperate)) {
					return true;
				} else {
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					CError tError = new CError();
					tError.moduleName = "ContBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			logger.debug("After submitData()");
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, e.toString());
			return false;
		} finally {
			mPubLock.unLock();// 解锁
		}

		return true;
	}
	
	private boolean checkdata() 
	{
		ExeSQL tExeSQL = new ExeSQL();
		//校验保单是否有死亡报案，若有，则不允许催收
		String DeadFlag_STR="";
		DeadFlag_STR=" select count(x.contno) from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
	    +  "  and substr(y.ReasonCode, 2) = '02' and x.contno ='?contno?' " ;
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(DeadFlag_STR);
		sqlbv.put("contno", mLJTempFeeBL.getOtherNo());
		if (Integer.parseInt(tExeSQL.getOneValue(sqlbv))>0) 
		{
			this.mErrors.addOneError("保单号:" + mLJTempFeeBL.getOtherNo()
					+ "存在死亡报案，不允许核销！");
			return false;
		}
		//add by xiongzh 09-7-30 增加核销前校验
		//校验ljspayperson的sum(sumduepaymoney)是否等于ljspay的sumduepaymoney
		for(int i=1;i<=mLJSPaySet.size();i++)
		{
			String sumsubpay_sql="select (case when sum(a.sumduepaymoney) is not null then sum(a.sumduepaymoney) else 0 end) from ljspayperson a  where a.getnoticeno='?getnoticeno?' and currency='?currency?' ";
			double sum_SubDueMoney =0.0;
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sumsubpay_sql);
			sqlbv1.put("getnoticeno", mLJSPaySet.get(i).getGetNoticeNo());
			sqlbv1.put("currency", mLJSPaySet.get(i).getCurrency());
			sum_SubDueMoney = Double.parseDouble(tExeSQL.getOneValue(sqlbv1));
			logger.debug("应收子表总金额:"+sum_SubDueMoney+"@@@应收总表金额:"+tempMoney);
			// 4-比较两个金额值，相等则核销
			if ((sum_SubDueMoney -mLJSPaySet.get(i).getSumDuePayMoney())!=0) 
			{
				CError.buildErr(this, " 核销前校验失败，原因是:应收子表和主表总交费金额不一致！");
				return false;
			}
		}	

		//校验ljspayperson中polno在lcpol中都存在
		String polno_check=" select count(*) from ljspayperson a where a.getnoticeno='?getnoticeno?' "
		+" and polno not in (select polno from lcpol where contno='?contno?')" ;
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(polno_check);
		sqlbv2.put("getnoticeno", mLJSPaySet.get(1).getGetNoticeNo());
		sqlbv2.put("contno", mLJSPaySet.get(1).getOtherNo());
		int check_count2=0;
		check_count2 = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if (check_count2>0) 
		{
			CError.buildErr(this, " 核销前校验失败，原因是:应收子表险种号在保单表中未能完全找到！");
			return false;
		}
		return true;
	}

	// 根据前面的输入数据，进行逻辑处理
	// 如果在处理过程中出错，则返回false,否则返回true
	private boolean dealData() {
		boolean tReturn = false;
		int i, iMax;
		// step one-查询数据
		String sqlStr = "";
		String ContNo = "";
		String mainPayToDate = "";

		if (!mBQUseFlag.equals("BQ")) {
			ContNo = mLJSPaySet.get(1).getOtherNo();
		} else {
			ContNo = mLJTempFeeBL.getOtherNo();
		}

		String GetNoticeNo = mLJSPaySet.get(1).getGetNoticeNo();
		double ContactuMoney = 0;

		if (mLJSPaySet.get(1).getSumDuePayMoney() == 0) {// 0-判断应收总表中的应收是否=0
			// actuVerifyFlag = true;
			// mLJTempFeeBL = new LJTempFeeBL();
			// "应收总表应收款为0!请执行自动核销";
		} 
		else 
		{
			if (!mBQUseFlag.equals("BQ")) 
			{
				// 保全不需要此判断
				for(int j=1;j<=this.tLJTempFeeSet.size();j++)
				{
					if (tLJTempFeeSet.get(j).getEnterAccDate() == null||tLJTempFeeSet.get(j).getEnterAccDate().equals("")) 
					{
						CError tError = new CError();
						tError.moduleName = "IndiFinUrgeVerifyBL";
						tError.functionName = "dealData";
						tError.errorMessage = "财务缴费还没有到帐!（暂交费收据号："
								+ tLJTempFeeSet.get(j).getTempFeeNo().trim() + "）";
						this.mErrors.addOneError(tError);
						return false;
					}
				}
				
			}

			// if(PubFun.calInterval(PubFun.getCurrentDate(),mLJSPayBL.getPayDate(),"D")<0)
			// {
			// CError tError = new CError();
			// tError.moduleName = "IndiFinUrgeVerifyBL";
			// tError.functionName = "ReturnData";
			// tError.errorMessage = "保单已过失效日期:应收纪录超过失效期核销 ";
			// this.mErrors .addOneError(tError) ;
			// return false;
			// }

			// 校验险种是否中止、失效
			String StateSql = "select * from LCContState where StateType in ('Available','Terminate','PayPrem') and State='1' and contno='?contno?' and enddate is null and exists (select 1 from lcpol where appflag='1' and polno=lccontstate.polno and contno='?contno?' and exists (select 1 from ljspayperson where contno=lcpol.contno and riskcode=lcpol.riskcode))";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(StateSql);
			sqlbv3.put("contno", mLJSPaySet.get(1).getOtherNo());

			logger.debug("校验险种是否中止、失效:::::::::::::::::::" + StateSql);
			LCContStateDB tLCContStateDB = new LCContStateDB();
			LCContStateSet tLCContStateSet = new LCContStateSet();
			tLCContStateSet = tLCContStateDB.executeQuery(sqlbv3);
			if (tLCContStateSet.size() > 0) {
				CError tError = new CError();
				tError.moduleName = "IndiFinUrgeVerifyBL";
				tError.functionName = "ReturnData";
				tError.errorMessage = "保单已经失效，不能核销 ";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 校验交费对应日才能核销
			if (CurrentDate.compareTo(mLJSPaySet.get(1).getStartPayDate()) < 0) {
				CError tError = new CError();
				tError.moduleName = "IndiFinUrgeVerifyBL";
				tError.functionName = "ReturnData";
				tError.errorMessage = "保单未到缴费对应日，不能核销 ";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 校验保单是否被挂起
			RNHangUp tRNHangUp = new RNHangUp(mGI);
			if (!tRNHangUp.checkHangUP(mLJSPaySet.get(1).getOtherNo())) {
				this.mErrors.copyAllErrors(tRNHangUp.mErrors);
				return false;
			}
		}

		// 0-查询保单表
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		sqlStr = "select * from LCCont where ContNo='?ContNo?'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sqlStr);
		sqlbv4.put("ContNo", ContNo);
		logger.debug("查询保单表:" + sqlStr);
		tLCContSet = tLCContDB.executeQuery(sqlbv4);
		if (tLCContDB.mErrors.needDealError() == true) {
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiFinUrgeVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "个人保单表查询失败!";
			this.mErrors.addOneError(tError);
			tLCContSet.clear();
			return false;
		} else {
			mLCContSchema.setSchema(tLCContSet.get(1));
			tReturn = true;
		}
		//查询主险币种
		ExeSQL xExeSQL = new ExeSQL();
		main_Currency = "";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql("select Currency from lcpol where contno='?ContNo?' and polno=mainpolno and appflag='1' ");
		sqlbv5.put("ContNo", ContNo);
		main_Currency = xExeSQL.getOneValue(sqlbv5);
		if(main_Currency==null ||"".equals(main_Currency)){
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiFinUrgeVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "保单主险币种类型查询失败!";
			this.mErrors.addOneError(tError);
			tLCContSet.clear();
			return false;
		}
			

		// 产生流水号
		tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		// 产生交费收据号
        payNo = PubFun1.CreateMaxNo("PayNo", tLimit);
		logger.debug("本次核销实收号码==" + payNo);

		// 1-查询保单险种表
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		
		//查询需要核销的险种，包括自垫核销
		sqlStr = "select * from LCPol where ContNo='?ContNo?' and polno in (select polno from ljspayperson where contNo='?ContNo?' and getnoticeno='?GetNoticeNo?')";
//		if (!mBQUseFlag.equals("BQ")) {
//
//		} 
//		else {
//			sqlStr = "SELECT *"
//					+ " FROM LCPol a"
//					+ " WHERE ContNo='"
//					+ ContNo
//					+ "' and not exists(select 'Y' from LCPol where PolNo=a.PolNo and InsuYearFlag='Y' and InsuYear<=1)"
//					+ " and not exists(select 'M' from LCPol where PolNo=a.PolNo and InsuYearFlag='M' and InsuYear<=12)"
//					+ " and not exists(select 'D' from LCPol where PolNo=a.PolNo and InsuYearFlag='D' and InsuYear<=365)"
//					+ " and not exists(select 'A' from LCPol where PolNo=a.PolNo and InsuYearFlag='A' and (InsuYear-(months_between(CValiDate,InsuredBirthday)/12))<=1)";
//		}
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sqlStr);
		sqlbv6.put("ContNo", ContNo);
		sqlbv6.put("GetNoticeNo", GetNoticeNo);
		logger.debug("查询保单险种表==" + sqlStr);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv6);
		if (tLCPolDB.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiFinUrgeVerifyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "个人保单险种表查询失败!";
			this.mErrors.addOneError(tError);
			tLCPolSet.clear();
			return false;
		} else {
			for (int n = 1; n <= tLCPolSet.size(); n++) 
			{
				LCPolBL mLCPolBL = new LCPolBL();
				mLCPolBL.setSchema(tLCPolSet.get(n));
				String PolNo = tLCPolSet.get(n).getPolNo();

				// 2-查询应收个人表
				LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
				sqlStr = "select * from LJSPayPerson where PolNo='?PolNo?' and Paytype in ('ZC','HF','HM')";
				SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
				sqlbv7.sql(sqlStr);
				sqlbv7.put("PolNo", PolNo);
				logger.debug("查询正常应收个人表:" + sqlStr);
				LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
				tLJSPayPersonSet = tLJSPayPersonDB.executeQuery(sqlbv7);
				if (tLJSPayPersonDB.mErrors.needDealError()) {
					this.mErrors.copyAllErrors(tLJSPayPersonDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "IndiFinUrgeVerifyBL";
					tError.functionName = "dealData";
					tError.errorMessage = "应收个人表查询失败!";
					this.mErrors.addOneError(tError);
					mLJSPayPersonSet.clear();
					return false;
				} else if (tLJSPayPersonSet.size() <= 0) {
					continue;  //主险没有ZC,HM,HF,但是有YEL,YET的会在处理完正常的应收后做特殊判定及处理
				} else {
					tReturn = true;
				}

				// 3-查询保费项表,根据应收个人交费表查询该表项
				logger.debug("查询保费项表:");
				LJSPayPersonBL tLJSPayPersonBL;
				LCPremSet tLCPremSet;
				LCPremBL tLCPremBL;
				LCPremDB tLCPremDB = new LCPremDB();
				double actuMoney = 0;
				mLCPremSet.clear();
				for (int num = 1; num <= tLJSPayPersonSet.size(); num++) {
					tLJSPayPersonBL = new LJSPayPersonBL();
					tLJSPayPersonBL.setSchema(tLJSPayPersonSet.get(num));
					double single_actuMoney = 0;
					if(!tLJSPayPersonBL.getCurrency().equals(mLCPolBL.getCurrency()))
					{
						LDExch tLDExch =new LDExch();
						single_actuMoney = tLDExch.toOtherCur(tLJSPayPersonBL.getCurrency(),mLCPolBL.getCurrency(),PubFun.getCurrentDate(),tLJSPayPersonBL.getSumDuePayMoney());
					}
					else
					{
						single_actuMoney = tLJSPayPersonBL.getSumDuePayMoney();
					}
					actuMoney = actuMoney + single_actuMoney;
					sqlStr = "select * from LCPrem where PolNo='?PolNo?' and DutyCode='?DutyCode?' and PayPlanCode='?PayPlanCode?'";
					SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
					sqlbv8.sql(sqlStr);
					sqlbv8.put("PolNo", tLJSPayPersonBL.getPolNo());
					sqlbv8.put("DutyCode", tLJSPayPersonBL.getDutyCode());
					sqlbv8.put("PayPlanCode", tLJSPayPersonBL.getPayPlanCode());
					tLCPremSet = new LCPremSet();
					tLCPremDB = new LCPremDB();
					tLCPremSet = tLCPremDB.executeQuery(sqlbv8);	
					logger.debug("查询保费项表sql:" + sqlStr);
					if (tLCPremDB.mErrors.needDealError()) {
						this.mErrors.copyAllErrors(tLCPremDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "IndiFinUrgeVerifyBL";
						tError.functionName = "dealData";
						tError.errorMessage = "保费项表查询失败!";
						this.mErrors.addOneError(tError);
						tLCPremSet.clear();
						mLCPremSet.clear();
						return false;
					}
					tLCPremBL = new LCPremBL();
					tLCPremBL.setSchema(tLCPremSet.get(1).getSchema());
					mLCPremSet.add(tLCPremBL);
				}

				// 4-查询保险责任表
				LCDutyDB tLCDutyDB = new LCDutyDB();
				sqlStr = "select * from LCDuty where PolNo='?PolNo?'";
				SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
				sqlbv9.sql(sqlStr);
				sqlbv9.put("PolNo", PolNo);
				logger.debug("查询保险责任表:" + sqlStr);
				mLCDutySet = tLCDutyDB.executeQuery(sqlbv9);
				if (tLCDutyDB.mErrors.needDealError()) {
					this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "IndiFinUrgeVerifyBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询保险责任表!";
					this.mErrors.addOneError(tError);
					mLCDutySet.clear();
					return false;
				}

				// 5-自动续保更改保单状态
				logger.debug("处理续保保单：");
				String Spolno = ""; // 需要转换的保单号
				LCPolBL nLCPolBL = new LCPolBL();

				String xRnewFlag="";  //续保标志
				if (mLCPolBL.getAppFlag().equals("9")) {// 9 - 附加险自动续保期间
					xRnewFlag="-1";
					String Qsql = "select * from lcpol where contno = '?contno?' and appflag = '1'" + " and riskcode = '?riskcode?'";
					SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
					sqlbv10.sql(Qsql);
					sqlbv10.put("contno", tLCPolSet.get(n).getContNo());
					sqlbv10.put("riskcode", tLCPolSet.get(n).getRiskCode());
					LCPolDB sLCPolDB = new LCPolDB();
					LCPolSet sLCPolSet = new LCPolSet();
					sLCPolSet = sLCPolDB.executeQuery(sqlbv10); // 旧的附加险保单
					if (sLCPolDB.mErrors.needDealError()) {
						this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "IndiFinUrgeVerifyBL";
						tError.functionName = "dealData";
						tError.errorMessage = "查询保险表失败!";
						this.mErrors.addOneError(tError);
						sLCPolSet.clear();
						return false;
					}
					if (sLCPolSet.size() == 0) {
						this.mErrors.copyAllErrors(tLCDutyDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "IndiFinUrgeVerifyBL";
						tError.functionName = "dealData";
						tError.errorMessage = "未找到有效的附加险信息!";
						this.mErrors.addOneError(tError);
						sLCPolSet.clear();
						return false;
					}

					for (int s = 1; s <= sLCPolSet.size(); s++) {// 换号操作，把原保单号的值赋于新生成的保单号
						logger.debug("=========续期核销处理更换号码========");
						nLCPolBL.setSchema(sLCPolSet.get(s).getSchema());

						nLCPolBL.setAppFlag("4");// 4 - 终止   老数据
						nLCPolBL.setModifyDate(this.CurrentDate);
						nLCPolBL.setModifyTime(this.CurrentTime);
						
						mLCPolBL.setAppFlag("1");// 1 - 承保   新数据
						//续保件置上签单日期等信息
						mLCPolBL.setSignCom(this.mGI.ManageCom);
						mLCPolBL.setSignDate(this.CurrentDate);
						mLCPolBL.setLastRevDate(mLCPolBL.getCValiDate()); // 把最近复效日期置为起保日期

						String OldPolNo = nLCPolBL.getPolNo();
						logger.debug("旧的保单号码==" + OldPolNo);

						String NewPolNo = mLCPolBL.getPolNo();
						logger.debug("新的保单号码==" + NewPolNo);

//						nLCPolBL.setPolNo(mLCPolBL.getPolNo());//新号+老数据
//						mLCPolBL.setPolNo(OldPolNo);//旧号+新数据

						for (int t = 1; t <= mLCDutySet.size(); t++) {
							mLCDutySet.get(t).setFirstPayDate(
									mLCPolBL.getCValiDate());
							logger.debug("FirstPayDate=="
									+ mLCPolBL.getCValiDate());

							mLCDutySet.get(t).setCValiDate(
									mLCPolBL.getCValiDate());
							logger.debug("CValiDate=="
									+ mLCPolBL.getCValiDate());

							mLCDutySet.get(t).setPaytoDate(
									mLCPolBL.getPaytoDate());
							logger.debug("PaytoDate=="
									+ mLCPolBL.getPaytoDate());

							mLCDutySet.get(t).setPayEndDate(
									mLCPolBL.getPayEndDate());
							logger.debug("PayEndDate=="
									+ mLCPolBL.getPayEndDate());
						}


						for (int t = 1; t <= mLCPremSet.size(); t++) {
							mLCPremSet.get(t).setPayStartDate(
									mLCPolBL.getCValiDate());
							logger.debug("PayStartDate=="
									+ mLCPolBL.getCValiDate());

							mLCPremSet.get(t).setPaytoDate(
									mLCPolBL.getPaytoDate());
							logger.debug("PaytoDate=="
									+ mLCPolBL.getPaytoDate());

							mLCPremSet.get(t).setPayEndDate(
									mLCPolBL.getPayEndDate());
							logger.debug("PayEndDate=="
									+ mLCPolBL.getPayEndDate());
						}

						LCGetDB sLCGetDB = new LCGetDB();
						sLCGetDB = new LCGetDB();
						String RNsql = "select * from lcget where polno='?PolNo?'";
						SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
						sqlbv11.sql(RNsql);
						sqlbv11.put("PolNo", PolNo);
						LCGetSet tLCGetSet = new LCGetSet();
						tLCGetSet = sLCGetDB.executeQuery(sqlbv11);
						for (int t = 1; t <= tLCGetSet.size(); t++) {					
							tLCGetSet.get(t).setGetEndDate(
									mLCPolBL.getEndDate());
							logger.debug("GetStartDate=="
									+ tLCGetSet.get(t).getGetStartDate());
							logger.debug("GetEndDate=="
									+ tLCGetSet.get(t).getGetEndDate());
						}
						mLCGetSet.add(tLCGetSet);

						// 更新理赔表
//						logger.debug("自动续保开始更新理赔表---------------");
//						TransferData tTransferData = new TransferData();
//						tTransferData.setNameAndValue("NewPolNo", NewPolNo);
//						tTransferData.setNameAndValue("OldPolNo", OldPolNo);
//						VData tVData = new VData();
//						tVData.addElement(tTransferData);
//						LLClaimUpdatePolNoBL tLLClaimUpdatePolNoBL = new LLClaimUpdatePolNoBL();
//						if (!tLLClaimUpdatePolNoBL.submitData(tVData, "UpDate")) {
//							this.mErrors
//									.copyAllErrors(tLLClaimUpdatePolNoBL.mErrors);
//							CError tError = new CError();
//							tError.moduleName = "LLClaimUpdatePolNoBL";
//							tError.functionName = "submitData";
//							tError.errorMessage = "更新理赔表失败!";
//							this.mErrors.addOneError(tError);
//							return false;
//						} else {
//							map.add(tLLClaimUpdatePolNoBL.getMMap());
//						}
//						logger.debug("更新理赔表完毕------------------");

						//现行处理ljapayperson时存入的已经保证是老保单号，为了避免出现不一致的情况，不再对扎帐表做换号操作。
						/*
						logger.debug("开始更新渠道表----------------------");
						map.put("update LACommision set polno ='" + NewPolNo
								+ "' where polno='" + OldPolNo + "'", "UPDATE");
						logger.debug("渠道表更新完毕------------------------");
						*/
                        //更新续保轨迹表lcrnewstatehistory
						logger.debug("自动续保开始更新续保轨迹表lcrnewstatehistory---------------");
						LCRnewStateHistoryDB tLCRnewStateHistoryDB = new LCRnewStateHistoryDB();
						LCRnewStateHistorySet tLCRnewStateHistorySet = new LCRnewStateHistorySet();
						String RnewHistory = "select * from LCRnewStateHistory where proposalno='?NewPolNo?' and state='4' ";
						SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
						sqlbv12.sql(RnewHistory);
						sqlbv12.put("NewPolNo", NewPolNo);
						logger.debug("查询续保轨迹状态表 ==" + RnewHistory);
						tLCRnewStateHistorySet = tLCRnewStateHistoryDB.executeQuery(sqlbv12);
						if (tLCRnewStateHistorySet.size() <= 0) {
							CError tError = new CError();
							tError.moduleName = "VerifyBl";
							tError.functionName = "submitData";
							tError.errorMessage = "查询续保轨迹失败!";
							this.mErrors.addOneError(tError);
							continue;
						}

						logger.debug("更新LCRnewStateHistory续保状态");
						for (int t = 1; t <= tLCRnewStateHistorySet.size(); t++) 
						{
							logger.debug("将投保单号为:"
								+ tLCRnewStateHistorySet.get(t).getProposalNo() + "状态置为5");
							tLCRnewStateHistorySet.get(t).setState("5");
							tLCRnewStateHistorySet.get(t).setModifyDate(CurrentDate);
							
							mLCRnewStateHistorySet.add(tLCRnewStateHistorySet.get(t));
						}
						//处理续保件的lccontstate记录
						logger.debug("自动续保开始处理续保件的lccontstate------------");
						//先以旧保单号插入一笔终止的记录
						LCContStateSchema tLCContStateSchema = new LCContStateSchema();

						tLCContStateSchema.setContNo(ContNo);
						tLCContStateSchema.setInsuredNo(nLCPolBL.getInsuredNo());
						tLCContStateSchema.setPolNo(OldPolNo);
						tLCContStateSchema.setStateType("Terminate");
						tLCContStateSchema.setStateReason("01");//满期终止
						tLCContStateSchema.setState("1");
						tLCContStateSchema.setStartDate(nLCPolBL.getEndDate());
						tLCContStateSchema.setOperator(mGI.Operator);
						tLCContStateSchema.setMakeDate(CurrentDate);
						tLCContStateSchema.setMakeTime(CurrentTime);
						tLCContStateSchema.setModifyDate(CurrentDate);
						tLCContStateSchema.setModifyTime(CurrentTime);
						mLCContStateSet_INSERT.add(tLCContStateSchema);
						//然后新保单号插入新的有效纪录
						LCContStateSchema xLCContStateSchema = new LCContStateSchema();
						xLCContStateSchema.setContNo(ContNo);
						xLCContStateSchema.setInsuredNo(mLCPolBL.getInsuredNo());
						xLCContStateSchema.setPolNo(NewPolNo);
						xLCContStateSchema.setStateType("Available");
						xLCContStateSchema.setState("0");
						xLCContStateSchema.setStartDate(mLCPolBL.getCValiDate());
						xLCContStateSchema.setOperator(mGI.Operator);
						xLCContStateSchema.setMakeDate(CurrentDate);
						xLCContStateSchema.setMakeTime(CurrentTime);
						xLCContStateSchema.setModifyDate(CurrentDate);
						xLCContStateSchema.setModifyTime(CurrentTime);
						mLCContStateSet_INSERT.add(xLCContStateSchema);
					}
					
				}

				logger.debug("更新保险责任表:");
				// 更新保险责任表
				iMax = mLCDutySet.size();
				for (i = 1; i <= iMax; i++) {
					boolean needDutyFlag = false;
					double ActuPrem = 0.0;
					String PaytoDate = "";
					LCDutyBL tLCDutyBL = new LCDutyBL();
					tLCDutyBL.setSchema(mLCDutySet.get(i));
					// 6-更新保费项表字段
					for (int num = 1; num <= mLCPremSet.size(); num++) {
						tLJSPayPersonBL = new LJSPayPersonBL();
						tLJSPayPersonBL.setSchema(tLJSPayPersonSet.get(1));
						tLCPremBL = new LCPremBL();
						tLCPremBL.setPolNo(Spolno);
						tLCPremBL.setSchema(mLCPremSet.get(num));
						if(!xRnewFlag.equals("-1")) //非续保件，需要累加交费次数
						{
							tLCPremBL.setPayTimes(tLCPremBL.getPayTimes() + 1); // 已交费次数	
						}				
						tLCPremBL.setSumPrem(tLCPremBL.getSumPrem()
								+ tLCPremBL.getPrem()); // 累计保费
						tLCPremBL.setPaytoDate(tLJSPayPersonBL
								.getCurPayToDate()); // 交至日期
						tLCPremBL.setModifyDate(CurrentDate); // 最后一次修改日期
						tLCPremBL.setModifyTime(CurrentTime); // 最后一次修改时间
						mLCPremNewSet.add(tLCPremBL);

						if (tLCPremBL.getPolNo().equals(tLCDutyBL.getPolNo())
								&& tLCPremBL.getDutyCode().equals(
										tLCDutyBL.getDutyCode())) {
							ActuPrem = ActuPrem + tLCPremBL.getPrem();
							PaytoDate = tLCPremBL.getPaytoDate();
							needDutyFlag = true;
						} // end if
					} // end for

					if (needDutyFlag) {
						logger.debug("更新保险责任表4:");
						tLCDutyBL.setPrem(ActuPrem); // 实际保费
						if(!xRnewFlag.equals("-1")) //非续保件，需要累加交费次数及保费
						{
						  tLCDutyBL.setSumPrem(tLCDutyBL.getSumPrem() + ActuPrem); // 累计保费
						}
						tLCDutyBL.setPaytoDate(PaytoDate); // 交至日期
						tLCDutyBL.setModifyDate(PubFun.getCurrentDate()); // 最后一次修改日期
						tLCDutyBL.setModifyTime(PubFun.getCurrentTime()); // 最后一次修改时间
						mLCDutyNewSet.add(tLCDutyBL);
					}
				} // end for

				logger.debug("测试paytodate==" + mLCPolBL.getPaytoDate());

				// 应收个人表填充实收个人表
				logger.debug("处理实收个人表");
				sqlStr = "select * from LJSPayPerson where PolNo='?PolNo?' and Paytype in ('ZC','HM','HF','YEL','YET')";
				SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
				sqlbv13.sql(sqlStr);
				sqlbv13.put("PolNo", PolNo);
				LJSPayPersonDB allLJSPayPersonDB = new LJSPayPersonDB();
				LJSPayPersonSet allLJSPayPersonSet = new LJSPayPersonSet();
				allLJSPayPersonSet = allLJSPayPersonDB.executeQuery(sqlbv13);
				iMax = allLJSPayPersonSet.size();
				LJAPayPersonBL tLJAPayPersonBL;
				for (i = 1; i <= iMax; i++) {
					tLJSPayPersonBL = new LJSPayPersonBL();
					tLJAPayPersonBL = new LJAPayPersonBL();
					tLJSPayPersonBL.setSchema(allLJSPayPersonSet.get(i)
							.getSchema());
					tLJAPayPersonBL = getActPay(tLJSPayPersonBL);
					mLJAPayPersonSet.add(tLJAPayPersonBL);
					tReturn = true;
				}

				mLJSPayPersonSet.add(allLJSPayPersonSet);
				// 5-更新保单表字段，取第一个应收个人交费纪录
				tLJSPayPersonBL = new LJSPayPersonBL();
				for (int t = 1; t <= mLJSPayPersonSet.size(); t++) {
					tLJSPayPersonBL.setSchema(mLJSPayPersonSet.get(t)
							.getSchema());
					if (tLJSPayPersonBL.getPolNo().equals(mLCPolBL.getPolNo())
							&& mLCPolBL.getAppFlag().equals("1")){
						mLCPolBL
								.setPaytoDate(tLJSPayPersonBL.getCurPayToDate()); // 交至日期
						if (mLCPolBL.getPolNo().equals(mLCPolBL.getMainPolNo()))
						{
							mainPayToDate = mLCPolBL.getPaytoDate();
						}
					}
				}

				if (mLCPolBL.getRenewCount() <= 0)
				{
					mLCPolBL.setSumPrem(mLCPolBL.getSumPrem() + actuMoney); // 总累计保费
				}
					
				logger.debug("mLCPolBL.getPaytoDate()=="
						+ mLCPolBL.getPaytoDate());

				// 求总余额:如果应收总表应收款=0，表明有余额，且这次的余额值存放在责任编码为
				// "yet"的应收个人交费纪录中（见个人催收流程图）,取出放在个人保单表的余额字段中
				// 否则，个人保单表余额纪录置为0
				mLCPolBL.setModifyDate(CurrentDate); // 最后一次修改日期
				mLCPolBL.setModifyTime(CurrentTime); // 最后一次修改时间
				mLCPolSet.add(mLCPolBL); // gaoht
				logger.debug("测试paytodate==" + mLCPolBL.getPaytoDate());
				if (nLCPolBL.getPolNo() != null) {
					mLCPolSet.add(nLCPolBL); // old-
					logger.debug("测试paytodate" + nLCPolBL.getPaytoDate());
				}

				logger.debug("开始看里面的paytodate");
				for (int y = 1; y <= mLCPolSet.size(); y++) {
					logger.debug("保单" + mLCPolSet.get(y).getPolNo() + "第"
							+ y + "次循环的时间PaytoDate为"
							+ mLCPolSet.get(y).getPaytoDate());
				}
				//累加lccont的prem和
				if(mLCPolBL.getCurrency().equals(main_Currency))
					ContactuMoney = ContactuMoney + actuMoney;
				else
				{
					LDExch tLDExch =new LDExch();
					ContactuMoney = ContactuMoney + tLDExch.toOtherCur(mLCPolBL.getCurrency(),main_Currency,PubFun.getCurrentDate(),actuMoney);
				}
			}

			// 处理余额.余额存于主险LCPol.LeavingMoney
			// 催收所有险种余额之和都记在主险上面，所以只要查出YET的这条记录存于lcpol主险上即可
			///////////////////////////////////////////////////////////////
			//tongmeng 2012-02-01 
			//使用客户账户后,余额都放在客户账户中,以下逻辑暂时注释掉
			/*
			sqlStr = "select * from LJSPayPerson where ContNo='" + ContNo
					+ "' and Paytype in ('YET')";
			logger.debug("查询余额应收个人表:" + sqlStr);
			LJSPayPersonDB tyetLJSPayPersonDB = new LJSPayPersonDB();
			LJSPayPersonSet tyetLJSPayPersonSet = tyetLJSPayPersonDB
					.executeQuery(sqlStr);
			//此处的mLCPolSet只包含有ZC,HM,HF记录的险种
			for (int k = 1; k <= mLCPolSet.size(); k++) {
				if (tyetLJSPayPersonSet.size() > 0
						&& mLCPolSet.get(k).getPolNo().equals(
								mLCPolSet.get(k).getMainPolNo())) {
					mLCPolSet.get(k).setLeavingMoney(
							tyetLJSPayPersonSet.get(1).getSumActuPayMoney());
				} else {
					mLCPolSet.get(k).setLeavingMoney(0.0);
				}
			}*/
			/////////////////////////////////////////////////////////////////
			/* add by xiongzh 2010-1-13
			* 以上只能处理主险也有ZC,HM,HF记录时的情况
			还需要增加对于主险没有ZC,HM,HF类型ljspayperson记录时的处理
			
			由于催收时可能会有某些险种有余额，假设为险种a，但是当期催收并无a的应收记录，老的催收处理程序无法处理此种情况，具体见LIS-10251
			因此催收程序修改对余额的处理，将所有余额统计挂在主险上生成一笔YEL（类似于YET），不过在核销的时候需要额外将a的余额置为0。
			此次修改是在尽量不影响前期应收数据核销的情况下处理的
            */
			/*
			 * 查询保单下没有ZC，HF,HM类型应收的险种，分两种情况：
			 * 1，主险，可能有YEL,YET记录，此时需要生成ljapayperson记录，有YET的还需要将余额挂在主险上。
			 * 2，附加险，没有任何应收记录，但是如果余额大于0的需要将余额置0
			 */
			String query2="select * from lcpol a where a.appflag='1' and contno='?ContNo?' and (polno=mainpolno or (polno<>mainpolno and leavingmoney>0) )" 
			+" and not exists(select 1 from ljspayperson b where a.contno=b.contno and b.riskcode=a.riskcode" 
			+" and b.paytype in ('ZC','HF','HM') )";
			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
			sqlbv14.sql(query2);
			sqlbv14.put("ContNo", ContNo);
			logger.debug("查询保单下没有ZC，HF,HM类型应收的险种"+query2);
			////////////////////////////////////////////////////////////////////////////
			//修改后的逻辑应该都会生成催收数据了
			/*
			LCPolDB other_LCPolDB = new LCPolDB();
			LCPolSet other_LCPolSet = new LCPolSet();
			other_LCPolSet = other_LCPolDB.executeQuery(query2);
			LJSPayPersonDB otherLJSPayPersonDB = new LJSPayPersonDB();
			LJSPayPersonSet otherLJSPayPersonSet = new LJSPayPersonSet();
			LJSPayPersonBL xLJSPayPersonBL;
			LJAPayPersonBL xLJAPayPersonBL;
			for(int k=1;k<=other_LCPolSet.size();k++)
			{
				logger.debug("存在挂余额但是没有催收的险种");
				LCPolSchema xLCPolSchema = new LCPolSchema();
				xLCPolSchema = other_LCPolSet.get(k);
				//如果是主险，需要进行此处处理的主险一般是非续保趸交主险
				if(xLCPolSchema.getPolNo().equals(xLCPolSchema.getMainPolNo()))  
				{
					double main_leavingmoney=0.00;
					String query3="select * from ljspayperson a where a.ContNo='" + ContNo
					+ "' and a.polno='"+xLCPolSchema.getPolNo()+"' and a.Paytype in ('YEL','YET')";
					otherLJSPayPersonSet = otherLJSPayPersonDB.executeQuery(query3);
					if(otherLJSPayPersonSet.size()>0)
					{
					    for(int e=1;e<=otherLJSPayPersonSet.size();e++)
					    {
							xLJSPayPersonBL = new LJSPayPersonBL();
							xLJAPayPersonBL = new LJAPayPersonBL();
							xLJSPayPersonBL.setSchema(otherLJSPayPersonSet.get(e).getSchema());
							if(xLJSPayPersonBL.getPayType().equals("YET"))
							{
								main_leavingmoney=xLJSPayPersonBL.getSumActuPayMoney();
							}
							xLJAPayPersonBL = getActPay(xLJSPayPersonBL);
							mLJAPayPersonSet.add(xLJAPayPersonBL);
					    }
					    mLJSPayPersonSet.add(otherLJSPayPersonSet);
					}
					xLCPolSchema.setLeavingMoney(main_leavingmoney);
				}
				else //附加险直接将余额置0
				{
					xLCPolSchema.setLeavingMoney(0);
				}
				xLCPolSchema.setModifyDate(CurrentDate);
				xLCPolSchema.setModifyTime(CurrentTime);
				mLCPolSet.add(xLCPolSchema);
			}
			*/
			/////////////////////////////////////////////////////////////////////////////////
			//加入账户处理（万能和投连险需要处理）
			if (!DealAccount())
			{
	            return false;
	        }
			if (!mainPayToDate.equals("")) {
				mLCContSchema.setPaytoDate(mainPayToDate);
			}
			mLCContSchema.setPrem(ContactuMoney);
			mLCContSchema
					.setSumPrem(mLCContSchema.getSumPrem() + ContactuMoney);
			mLCContSchema.setModifyDate(CurrentDate); // 最后一次修改日期
			mLCContSchema.setModifyTime(CurrentTime); // 最后一次修改时间

			/*******************************************************************
			 * 预交续期保费处理账户轨迹
			 ******************************************************************/
			if (mLJTempFeeBL.getOtherNoType() != null
					&& mLJTempFeeBL.getOtherNoType().equals("0")) {
				for(i=1;i<=mLJSPaySet.size();i++)
				{
					LCCustomerAccTraceSchema mLCCustomerAccTraceSchema = new LCCustomerAccTraceSchema();
					mLCCustomerAccTraceSchema.setAccNo(i);
					mLCCustomerAccTraceSchema.setAccType("001");
					mLCCustomerAccTraceSchema.setCustomerNo(mLCContSchema
							.getAppntNo());
					mLCCustomerAccTraceSchema.setAccHappenNo(mLJTempFeeBL
							.getTempFeeNo());
					mLCCustomerAccTraceSchema.setInsuAccNo(mLCContSchema
							.getContNo());
					mLCCustomerAccTraceSchema.setOtherNo(mLJTempFeeBL.getOtherNo());
					mLCCustomerAccTraceSchema.setOtherNoType(mLJTempFeeBL
							.getOtherNoType());
					mLCCustomerAccTraceSchema.setOperationType("YS");
					mLCCustomerAccTraceSchema.setMoneyType("BF");
					mLCCustomerAccTraceSchema.setOperator(mGI.Operator);
					mLCCustomerAccTraceSchema.setOperFlag("2");
					mLCCustomerAccTraceSchema.setMakeDate(CurrentDate);
					mLCCustomerAccTraceSchema.setMakeTime(CurrentTime);
					mLCCustomerAccTraceSchema.setModifyDate(CurrentDate);
					mLCCustomerAccTraceSchema.setModifyTime(CurrentTime);
					mLCCustomerAccTraceSchema.setMoney(mLJSPaySet.get(i).getSumDuePayMoney());
					mLCCustomerAccTraceSchema.setCustomerType("1");
					mLCCustomerAccTraceSet.add(mLCCustomerAccTraceSchema);
				}				
			}

			//处理主险续保情况下的打印表
			int m_RnewMainRiskFlag = 0;
			String check_sql="select count(*) from lcpol where contno='?ContNo?' and polno=mainpolno and paytodate=payenddate and appflag='1'";
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			sqlbv15.sql(check_sql);
			sqlbv15.put("ContNo", ContNo);
			m_RnewMainRiskFlag=Integer.parseInt( xExeSQL.getOneValue(sqlbv15) );
			if(m_RnewMainRiskFlag>=1)
			{
				PrintManagerBL tPrintManagerBL=new PrintManagerBL();
                LOPRTManagerDB tLOPRTManagerDB=new LOPRTManagerDB();
                tLOPRTManagerDB.setCode(tPrintManagerBL.CODE_PRnewNotice);//续保催收通知书类型
                tLOPRTManagerDB.setOtherNo(ContNo);
                tLOPRTManagerDB.setStandbyFlag2(mLJSPaySet.get(1).getGetNoticeNo());
                LOPRTManagerSet tLOPRTManagerSet=tLOPRTManagerDB.query();

                if(tLOPRTManagerSet.size()==0)
                {
                    CError.buildErr(this, "没有查询到该保单号对应的催收交费通知书！");
                    return false;
                }

                LOPRTManagerSchema tLOPRTManagerSchema=tLOPRTManagerSet.get(1);
                //产生打印流水号
                String strNoLimit = PubFun.getNoLimit( mLJSPaySet.get(1).getManageCom() );
                String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit);
                tLOPRTManagerSchema.setCode(tPrintManagerBL.CODE_PRnewSure);
                tLOPRTManagerSchema.setPrtSeq(tPrtSeq);
                tLOPRTManagerSchema.setStateFlag("0");
                tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
                tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
                mLOPRTManagerSet_INSERT.add(tLOPRTManagerSchema);        
			}
            //如果签单时续期交费通知书还未打印，则强制将loprtmanager.StateFlag其置为1(已打印状态)，不然影响下一期的续保操作（续保催收校验）
	        //这种强制打印的情况将表中的备用字段StandbyFlag4置为1
	        String str3="update loprtmanager set StateFlag='1',StandbyFlag4='1' where OtherNo='?ContNo?' and OtherNoType='02' and Code='47' and StateFlag ='0'";
	        SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
	        sqlbv16.sql(str3);
	        sqlbv16.put("ContNo", ContNo);
	        map.put(sqlbv16,"UPDATE");
	        
			if (!mBQUseFlag.equals("BQ")) {
				if (actuVerifyFlag == false) { // 如果不需要自动核销
					// 4-查询暂交费分类表
					LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
					sqlStr = "select * from LJTempFeeClass where TempFeeNo='?TempFeeNo?'";
					SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
					sqlbv17.sql(sqlStr);
					sqlbv17.put("TempFeeNo", mLJTempFeeBL.getTempFeeNo());
					logger.debug("查询暂交费分类表:" + sqlStr);
					mLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv17);
					if (tLJTempFeeClassDB.mErrors.needDealError() == true) {
						this.mErrors.copyAllErrors(tLJTempFeeClassDB.mErrors);
						CError tError = new CError();
						tError.moduleName = "IndiFinUrgeVerifyBL";
						tError.functionName = "dealData";
						tError.errorMessage = "暂交费分类表表查询失败!";
						this.mErrors.addOneError(tError);
						mLJTempFeeClassSet.clear();
						return false;
					}
					tReturn = true;
				}
			}

			// step two-处理数据
			// 实收款=actuMoney
			// 1 ZC=100 YEL=0 YET=0 actuMoney=100
			// 2 ZC=100 YEL=-50 YET=0 actuMoney=50
			// 3 ZC=100 YEL=-200 YET=100 actuMoney=0

			// 添加纪录
			if (this.mOperate.equals("VERIFY")) {
				if (actuVerifyFlag == false)
				{ // 如果不需要自动核销
					// 2-暂交费表核销标志置为1
					for(int k=1;k<=this.tLJTempFeeSet.size();k++)
					{
						LJTempFeeSchema xLJTempFeeSchema = new LJTempFeeSchema();
						xLJTempFeeSchema=tLJTempFeeSet.get(k);
						xLJTempFeeSchema.setConfFlag("1"); // 核销标志置为1
						xLJTempFeeSchema.setConfDate(CurrentDate);
						
						mLJTempFeeSet.add(xLJTempFeeSchema);
					}
					
					tReturn = true;

					if (!mBQUseFlag.equals("BQ")) 
					{
						logger.debug("暂交费分类表，核销标志置为1:");
						iMax = mLJTempFeeClassSet.size();
						LJTempFeeClassBL tLJTempFeeClassBL = new LJTempFeeClassBL();
						for (i = 1; i <= iMax; i++) {
							tLJTempFeeClassBL = new LJTempFeeClassBL();
							tLJTempFeeClassBL.setSchema(mLJTempFeeClassSet.get(
									i).getSchema());
							tLJTempFeeClassBL.setConfFlag("1"); // 暂交费分类表，核销标志置为1
							tLJTempFeeClassBL.setConfDate(CurrentDate);
							mLJTempFeeClassNewSet.add(tLJTempFeeClassBL);
							tReturn = true;
						}
					}
				}			
				//处理余额,核销客户账户
				//////////////////////////////////////////////////////////////////////////
				//1-计算需要使用的 余额
				//2-从客户账户获取余额,并生成暂收数据.客户账户相应减少
				//3-核销这些暂收数据.
				for(i=1;i<=this.mLJSPaySet.size();i++)
				{
					LJSPaySchema tempLJSPaySchema = new LJSPaySchema();
					tempLJSPaySchema = mLJSPaySet.get(i);
					String tCurrency = tempLJSPaySchema.getCurrency();
					double tShouldMoney = tempLJSPaySchema.getSumDuePayMoney();
					
					//String t =  tempLJSPaySchema.getGetNoticeNo();
					LJTempFeeSchema xLJTempFeeSchema = new LJTempFeeSchema();
					for(int k=1;k<=this.tLJTempFeeSet.size();k++)
					{
						
						
						if(tLJTempFeeSet.get(k).getCurrency().equals(tCurrency))
						{
							xLJTempFeeSchema=tLJTempFeeSet.get(k);
							break;
						}
					}
					double tTempFeeMoney = xLJTempFeeSchema.getPayMoney();
					
					logger.debug("tCurrency:"+tCurrency+":tShouldMoney:"+tShouldMoney+":tTempFeeMoney:"+tTempFeeMoney);
					double tYEShouleUseMoney = tShouldMoney - tTempFeeMoney ;
					//double tYEShouleUseMoney = tShouldMoney ;
					logger.debug("开始续期核销客户账户处理....");
					// 添加客户账户处理
					VData nInputData = new VData();
					TransferData nTransferData = new TransferData();
					// 指定用途
					nTransferData.setNameAndValue("OperationType", "2");
					// 传入本次核销金额
					nTransferData.setNameAndValue("SumDuePayMoney", tShouldMoney);
					//传入使用余额
					nTransferData.setNameAndValue("YEShouleUseMoney", tYEShouleUseMoney);
					//传入本次使用的币种
					nTransferData.setNameAndValue("Currency", tCurrency);
					nTransferData.setNameAndValue("GetNoticeNo", xLJTempFeeSchema.getTempFeeNo());
					nInputData.add(mLJTempFeeSet);
					nInputData.add(mLJTempFeeClassSet);
					nInputData.add(nTransferData);
					FICustomerMain tFICustomerMain = new FICustomerMain();
					// 调用客户账户收费接口，传入财务标志RN
					if (tFICustomerMain.submitData(nInputData, "RN"))
					{
						// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
						map.add(tFICustomerMain.getMMap());
					}
					else
					{
						mErrors.copyAllErrors(tFICustomerMain.mErrors);
					}
					
					logger.debug("结束续期核销客户账户处理....");
				}
				
				//////////////////////////////////////////////////////////////////////////
				
				TaxCalculator.calBySchemaSet(mLJAPayPersonSet);
				TaxCalculator.calBySchemaSet(mLASPayPersonSet);
				for(i=1;i<=mLJSPaySet.size();i++)
				{
					// 1-应收总表和暂交费表数据填充实收总表
					LJAPaySchema tLJAPaySchema = new LJAPaySchema();
					tLJAPaySchema.setPayNo(payNo); // 交费收据号码
					tLJAPaySchema.setIncomeNo(mLJSPaySet.get(i).getOtherNo()); // 应收/实收编号
					tLJAPaySchema.setIncomeType(mLJSPaySet.get(i).getOtherNoType()); // 应收/实收编号类型
					tLJAPaySchema.setAppntNo(mLJSPaySet.get(i).getAppntNo()); // 投保人客户号码

					if (actuVerifyFlag == false) { // 如果不需要自动核销
						tLJAPaySchema.setSumActuPayMoney(mLJSPaySet.get(i).getSumDuePayMoney()); // 总实交金额
						tLJAPaySchema.setEnterAccDate(mLJTempFeeBL.getEnterAccDate()); // 到帐日期
						tLJAPaySchema.setConfDate(CurrentDate); // 确认日期
					} else {
						tLJAPaySchema.setSumActuPayMoney(0); // 总实交金额
						tLJAPaySchema.setEnterAccDate(CurrentDate); // 到帐日期
						tLJAPaySchema.setConfDate(CurrentDate); // 确认日期
					}

					tLJAPaySchema.setCurrency(mLJSPaySet.get(i).getCurrency());
					tLJAPaySchema.setGetNoticeNo(mLJSPaySet.get(i).getGetNoticeNo()); // 交费通知书号码
					tLJAPaySchema.setPayTypeFlag(mLJSPaySet.get(i).getPayTypeFlag()); //主险续保标记
					tLJAPaySchema.setPayDate(mLJSPaySet.get(i).getPayDate()); // 交费日期
					tLJAPaySchema.setApproveCode(mLJSPaySet.get(i).getApproveCode()); // 复核人编码
					tLJAPaySchema.setApproveDate(mLJSPaySet.get(i).getApproveDate()); // 复核日期
					tLJAPaySchema.setSerialNo(serNo); // 流水号
					tLJAPaySchema.setStartPayDate(mLJSPaySet.get(i).getStartPayDate());
					//加上保单投保人证件类型及证件号
					tLJAPaySchema.setIDType(mLJSPaySet.get(i).getIDType());
					tLJAPaySchema.setIDNo(mLJSPaySet.get(i).getIDNo());
					
					tLJAPaySchema.setOperator(mGI.Operator); // 操作员
					tLJAPaySchema.setMakeDate(CurrentDate); // 入机时间
					tLJAPaySchema.setMakeTime(CurrentTime); // 入机时间
					tLJAPaySchema.setModifyDate(CurrentDate); // 最后一次修改日期
					tLJAPaySchema.setModifyTime(CurrentTime); // 最后一次修改时间
					tLJAPaySchema.setBankCode(mLJSPaySet.get(i).getBankCode()); // 银行编码
					tLJAPaySchema.setBankAccNo(mLJSPaySet.get(i).getBankAccNo()); // 银行帐号
					tLJAPaySchema.setAccName(mLJSPaySet.get(i).getAccName());//帐户名
					tLJAPaySchema.setRiskCode(mLJSPaySet.get(i).getRiskCode()); // 险种编码
					tLJAPaySchema.setOtherNo(mLJSPaySet.get(i).getOtherNo());
					tLJAPaySchema.setOtherNoType(mLJSPaySet.get(i).getOtherNoType());
					tLJAPaySchema.setManageCom(mLJSPaySet.get(i).getManageCom());
					tLJAPaySchema.setAgentCode(mLJSPaySet.get(i).getAgentCode());
					tLJAPaySchema.setAgentCom(mLJSPaySet.get(i).getAgentCom());
					tLJAPaySchema.setAgentGroup(mLJSPaySet.get(i).getAgentGroup());
					tLJAPaySchema.setTax(mLJSPaySet.get(i).getTax());
					tLJAPaySchema.setTaxAmount(mLJSPaySet.get(i).getTaxAmount());
					tLJAPaySchema.setNetAmount(mLJSPaySet.get(i).getNetAmount());
					mLJAPaySet.add(tLJAPaySchema);
				}
				
				tReturn = true;
			}
			tReturn = true;
		}
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData mInputData) {
		mGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mLJTempFeeBL.setSchema((LJTempFeeSchema) mInputData
				.getObjectByObjectName("LJTempFeeSchema", 0));

		if (mLJTempFeeBL == null || mGI == null) {
			CError.buildErr(this, "没有得到足够的数据!");
			return false;
		}

		// 传入保单号或者暂交费号
		if (mLJTempFeeBL.getTempFeeNo() != null || mLJTempFeeBL.getOtherNo() != null) 
		{
			// /*********************************BQ*************************************\
			// 保全不做此校验，因为这里的暂交费数据是造的，并无真实数据。
			if (!mBQUseFlag.equals("BQ")) 
			{
				// \*********************************BQ*************************************/
				VData tempVData = new VData();
				double sumDueMoney;
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSet = new LJTempFeeSet();
				LJSPaySchema tLJSPaySchema = new LJSPaySchema();
				LJSPaySet tLJSPaySet = new LJSPaySet();

				if (mLJTempFeeBL.getTempFeeNo() != null)
					tLJTempFeeSchema.setTempFeeNo(mLJTempFeeBL.getTempFeeNo());
				if (mLJTempFeeBL.getOtherNo() != null)
					tLJTempFeeSchema.setOtherNo(mLJTempFeeBL.getOtherNo());

				tLJTempFeeSchema.setTempFeeType("2"); // 交费类型为2：续期催收交费
				tLJTempFeeSchema.setConfFlag("0"); // add by heyq

				// 续期核销时需要查询未核销的暂交费记录进行核销
				tempVData.add(tLJTempFeeSchema);

				TempFeeQueryForUrgeGetUI tTempFeeQueryForUrgeGetUI = new TempFeeQueryForUrgeGetUI();
				if (!tTempFeeQueryForUrgeGetUI.submitData(tempVData, "QUERY")) 
				{
					// 查询是否因为完全豁免的情况
					String tSql = "select sum(SumDuePayMoney) from ljspayperson where paytype in ('HF','HM') and contno='?contno?'";
					SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
					sqlbv18.sql(tSql);
					sqlbv18.put("contno", mLJTempFeeBL.getOtherNo());
					SSRS nSSRS = new SSRS();
					ExeSQL tExeSQL = new ExeSQL();
					nSSRS = tExeSQL.execSQL(sqlbv18);
					String SumMoney = nSSRS.GetText(1, 1);
					if (SumMoney.equals("0")) 
					{
						String ySql = "select * from ljspay where otherno='?otherno?'";
						SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
						sqlbv19.sql(ySql);
						sqlbv19.put("otherno", mLJTempFeeBL.getOtherNo());
						LJSPayDB tLJSPayDB = new LJSPayDB();
						mLJSPaySet = tLJSPayDB.executeQuery(sqlbv19);
						if (mLJSPaySet.size() == 0) 
						{
							CError.buildErr(this, "应收费用查询失败:"
									+ tTempFeeQueryForUrgeGetUI.mErrors
											.getFirstError());
							return false;
						}

						tLJSPaySchema = mLJSPaySet.get(1);
						tLJTempFeeSchema.setTempFeeNo(tLJSPaySchema
								.getGetNoticeNo());
						tLJTempFeeSchema.setOtherNo(tLJSPaySchema.getOtherNo());
						tLJTempFeeSchema.setEnterAccDate(CurrentDate);
						tLJTempFeeSchema.setOtherNoType("01");
					} 
					else 
					{
						CError.buildErr(this, "暂交费查询失败:"
								+ tTempFeeQueryForUrgeGetUI.mErrors
										.getFirstError());
						return false;
					}
				} 
				else 
				{
					tempVData.clear();
					tempVData = tTempFeeQueryForUrgeGetUI.getResult();
					tLJTempFeeSet.set((LJTempFeeSet) tempVData
							.getObjectByObjectName("LJTempFeeSet", 0));

					for (int i = 1; i <= tLJTempFeeSet.size(); i++) 
					{
						mTempFeeNo=tLJTempFeeSet.get(i).getTempFeeNo();  //获取暂收费号
						LJSPayDB xLJSPayDB = new LJSPayDB();
						xLJSPayDB.setGetNoticeNo(mTempFeeNo);
						xLJSPayDB.setCurrency(tLJTempFeeSet.get(i).getCurrency());
						if(!xLJSPayDB.getInfo())
						{
							CError.buildErr(this, "应收总表查询失败");
							return false;
						}
						
						tLJSPaySchema = new LJSPaySchema();
						tLJSPaySchema = xLJSPayDB.getSchema();
						
						sumDueMoney = tLJSPaySchema.getSumDuePayMoney();// 续期催收金额
						
						//获取客户账户的余额.
						FICustomer tFICustomer = new FICustomerRN();
						double mDif = tFICustomer.queryAccount(tLJTempFeeSet.get(i).getOtherNo(), "2",tLJTempFeeSet.get(i).getCurrency());
						logger.debug("mTempFeeNo:"+ mTempFeeNo + ":currency:"+tLJTempFeeSet.get(i).getCurrency()+":mDif:"+mDif);
						
						// 4-比较两个金额值，相等则核销
						//tongmeng 2012-01-31 modify
						//以下的差值有可能小于0,即在缴费后又做了预收 .
						//mDif 中已经包含了最近一次保费缴费的数据
						//if ((sumDueMoney -tLJTempFeeSet.get(i).getPayMoney()-mDif)>0) 
						if ((sumDueMoney -mDif)>0)
						{
							CError.buildErr(this, " 核销失败，原因是:暂交费金额和续期保费不一致！");
							return false;
						}
						mLJSPaySet.add(tLJSPaySchema);
					}
					//有可能 存在应收表中有,暂收费表中没有的数据.
					String tSQL = "select * from ljspay a where otherno='?otherno?'   "
						        + " and concat(otherno,currency) not in "
						        + " ( "
						        + " select concat(otherno,currency) from ljtempfee where otherno=a.otherno "
						        + " and enteraccdate is not null and enteraccdate <> '3000-1-1' "
						        + " and (ConfFlag='0' or ConfFlag is null) "
						        + " )";
					SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
					sqlbv20.sql(tSQL);
					sqlbv20.put("otherno", tLJTempFeeSet.get(1).getOtherNo());
					
					LJSPayDB tLJSPayDB = new LJSPayDB();
					LJSPaySet tempLJSPaySet = new LJSPaySet();
					tempLJSPaySet = tLJSPayDB.executeQuery(sqlbv20);
					for(int i=1;i<=tempLJSPaySet.size();i++)
					{
						LJSPaySchema tempLJSPaySchema = new LJSPaySchema();
						tempLJSPaySchema = tempLJSPaySet.get(i);
						double tsumDueMoney = tempLJSPaySchema.getSumDuePayMoney();// 续期催收金额
						//获取客户账户的余额.
						FICustomer tFICustomer = new FICustomerRN();
						double mDif = tFICustomer.queryAccount(tempLJSPaySet.get(i).getOtherNo(), "2",tempLJSPaySet.get(i).getCurrency());
						logger.debug(":currency:"+tempLJSPaySet.get(i).getCurrency()+":mDif:"+mDif);
						
						// 4-比较两个金额值，相等则核销
						//tongmeng 2012-01-31 modify
						//以下的差值有可能小于0,即在缴费后又做了预收 .
						//mDif 中已经包含了最近一次保费缴费的数据
						//if ((sumDueMoney -tLJTempFeeSet.get(i).getPayMoney()-mDif)>0) 
						if ((tsumDueMoney -mDif)>0)
						{
							CError.buildErr(this, " 核销失败，原因是:暂交费金额和续期保费不一致！");
							return false;
						}
						mLJSPaySet.add(tempLJSPaySchema);
					}
					
					
					
				}
				mLJTempFeeBL.setSchema(tLJTempFeeSet.get(1));
				// /*********************************BQ*************************************\
			} 
			else 
			{
				//非续期的处理
				mLJTempFeeBL.setSchema(mLJTempFeeBL.getSchema());
				LJSPayDB tBQLJSPayDB = new LJSPayDB();
				tBQLJSPayDB.setGetNoticeNo(mBQGetNoticeNo);
				tBQLJSPayDB.setCurrency(mLJTempFeeBL.getCurrency());
				//tBQLJSPayDB.setCurrency("01");	//TODO
				LJSPaySet ljsPays = tBQLJSPayDB.query();
				if (ljsPays.size() != 1) 
				{
					CError tError = new CError();
					tError.moduleName = "IndiFinUrgeVerifyBL";
					tError.functionName = "getInputData";
					tError.errorMessage = "未查询到应收总表信息！通知书号码：" + mBQGetNoticeNo;
					this.mErrors.addOneError(tError);
					return false;
				}
				mLJSPayBL.setSchema(ljsPays.get(1));
				tempMoney=mLJSPayBL.getSumDuePayMoney();
				mLJSPaySet.add(ljsPays);	//TODO
				double sumDueMoney=mLJTempFeeBL.getPayMoney();
				// 4-比较两个金额值，相等则核销
				if ((sumDueMoney - tempMoney)!=0) 
				{
					CError.buildErr(this, " 核销失败，原因是:暂交费金额和续期保费不一致！");
					return false;
				}
			}
			// \*********************************BQ*************************************/
		} else {
			CError.buildErr(this, "没有得到足够的数据:请传入暂交费费号或保单号");
			return false;
		}

		return true;
	}

	// 准备往后层输出所需要的数据
	// 输出：如果准备数据时发生错误则返回false,否则返回true
	private boolean prepareOutputData() {
		logger.debug("after prepareOutputData()");
		mInputData = new VData();

		/** ===================检验郁闷的错误===============================* */
		for (int i = 1; i <= mLJAPayPersonSet.size(); i++) {
			LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
			tLJAPayPersonSchema = mLJAPayPersonSet.get(i);
			String ErrorSql = "select * from ljapayperson where polno='?polno?' and payplancode='?payplancode?' and dutycode='?dutycode?' and paytype='?paytype?' and SumActuPayMoney='?SumActuPayMoney?' and contno='?contno?' and payno<>'?payno?' and getnoticeno='?getnoticeno?'";
			SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
			sqlbv21.sql(ErrorSql);
			sqlbv21.put("polno", tLJAPayPersonSchema.getPolNo());
			sqlbv21.put("payplancode", tLJAPayPersonSchema.getPayPlanCode());
			sqlbv21.put("dutycode", tLJAPayPersonSchema.getDutyCode());
			sqlbv21.put("", tLJAPayPersonSchema.getPayType());
			sqlbv21.put("SumActuPayMoney", tLJAPayPersonSchema.getSumActuPayMoney());
			sqlbv21.put("contno", tLJAPayPersonSchema.getContNo());
			sqlbv21.put("payno", tLJAPayPersonSchema.getPayNo());
			sqlbv21.put("getnoticeno", tLJAPayPersonSchema.getGetNoticeNo());
			LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
			LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
			tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv21);
			if (tLJAPayPersonSet.size() > 0) {
				CError tError = new CError();
				tError.moduleName = "IndiFinUrgeVerifyBL";
				tError.functionName = "prepareData";
				tError.errorMessage = "双击核销严重影响效率,下次批量核销时请不要双击";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		for (int i = 1; i <= mLCPolSet.size(); i++) {
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = mLCPolSet.get(i);
			if (tLCPolSchema.getAppFlag() != null) {
				if (mLCPolSet.get(i).getAppFlag().equals("9")) {
					CError tError = new CError();
					tError.moduleName = "IndiFinUrgeVerifyBL";
					tError.functionName = "prepareData";
					tError.errorMessage = "续保焕号失败";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}

		/* ================================================================ */
		try {
			// 保全无此动作
			if (!mBQUseFlag.equals("BQ")) {
				//mLJTempFeeSchema.setSchema(mLJTempFeeBL);

				// =============================更新渠道表================================
				String LASPaySql = "select * from laspayperson where getnoticeno='?getnoticeno?' and contno = '?contno?'";
				SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
				sqlbv22.sql(LASPaySql);
				sqlbv22.put("getnoticeno", mLJAPaySet.get(1).getGetNoticeNo());
				sqlbv22.put("contno", mLJAPaySet.get(1).getOtherNo());
				logger.debug("更新渠道表================" + LASPaySql);
				LASPayPersonDB tLASPayPersonDB = new LASPayPersonDB();
				LASPayPersonSet tLASPayPersonSet = new LASPayPersonSet();
				tLASPayPersonSet = tLASPayPersonDB.executeQuery(sqlbv22);
				map.put(tLASPayPersonSet, "DELETE");

				for (int t = 1; t <= mLJAPayPersonSet.size(); t++) {
					LASPayPersonSchema tLASPayPersonSchema = new LASPayPersonSchema();
					LJAPayPersonSchema tLJAPayPersonSchema = mLJAPayPersonSet
							.get(t);
					tLASPayPersonSchema.setPolNo(tLJAPayPersonSchema.getPolNo());
					tLASPayPersonSchema.setActuPayFlag("1");
					tLASPayPersonSchema.setRiskCode(tLJAPayPersonSchema
							.getRiskCode());
					tLASPayPersonSchema.setPayCount(tLJAPayPersonSchema
							.getPayCount());
					tLASPayPersonSchema.setMainPolYear(tLJAPayPersonSchema
							.getMainPolYear());
					tLASPayPersonSchema.setContNo(tLJAPayPersonSchema
							.getContNo());
					tLASPayPersonSchema.setAgentCode(tLJAPayPersonSchema
							.getAgentCode());
					tLASPayPersonSchema.setAgentGroup(tLJAPayPersonSchema
							.getAgentGroup());
					tLASPayPersonSchema.setSumActuPayMoney(tLJAPayPersonSchema
							.getSumActuPayMoney());
					tLASPayPersonSchema.setSumDuePayMoney(tLJAPayPersonSchema
							.getSumDuePayMoney());
					tLASPayPersonSchema.setLastPayToDate(tLJAPayPersonSchema
							.getLastPayToDate());
					tLASPayPersonSchema.setCurPayToDate(tLJAPayPersonSchema
							.getCurPayToDate());
					tLASPayPersonSchema.setDutyCode(tLJAPayPersonSchema
							.getDutyCode());
					tLASPayPersonSchema.setPayPlanCode(tLJAPayPersonSchema
							.getPayPlanCode());
					tLASPayPersonSchema.setPayType(tLJAPayPersonSchema
							.getPayType());
					tLASPayPersonSchema.setPayIntv(tLJAPayPersonSchema
							.getPayIntv());
					tLASPayPersonSchema.setPayAimClass("1");
					tLASPayPersonSchema.setGetNoticeNo(tLJAPayPersonSchema
							.getGetNoticeNo());
					tLASPayPersonSchema.setManageCom(tLJAPayPersonSchema
							.getManageCom());
					tLASPayPersonSchema.setGrpContNo(tLJAPayPersonSchema
							.getGrpContNo());
					tLASPayPersonSchema.setGrpPolNo(tLJAPayPersonSchema
							.getGrpPolNo());
					tLASPayPersonSchema.setPayDate(tLJAPayPersonSchema
							.getPayDate());
					tLASPayPersonSchema.setOperator(mGI.Operator);
					tLASPayPersonSchema.setMakeDate(CurrentDate);
					tLASPayPersonSchema.setMakeTime(CurrentTime);
					tLASPayPersonSchema.setModifyDate(CurrentDate);
					tLASPayPersonSchema.setModifyTime(CurrentTime);

					String tContNo = tLJAPayPersonSchema.getContNo();
					String tSql = "select poltype from lacommisiondetail where grpcontno = '?tContNo?'";
					SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
					sqlbv23.sql(tSql);
					sqlbv23.put("tContNo", tContNo);
					SSRS nSSRS = new SSRS();
					ExeSQL tExeSQL = new ExeSQL();
					nSSRS = tExeSQL.execSQL(sqlbv23);
					if (nSSRS.getMaxRow() > 0) {
						String tpoltype = nSSRS.GetText(1, 1);
						tLASPayPersonSchema.setPolType(tpoltype);
					}

					// tSql = "select BranchSeries from LABranchGroup where
					// AgentGroup = '"
					// + tLJAPayPersonSchema.getAgentGroup() + "'";
					// nSSRS = new SSRS();
					// tExeSQL = new ExeSQL();
					// nSSRS = tExeSQL.execSQL(tSql);
					// if (nSSRS.getMaxRow() > 0) {
					// String tBranchSeries = nSSRS.GetText(1, 1);
					// tLASPayPersonSchema.setBranchSeries(tBranchSeries);
					// }

					tLASPayPersonSet.add(tLASPayPersonSchema);
					mLASPayPersonSet.add(tLASPayPersonSchema);
				}

				// 自动续保要更新的表
				if (mLCDutySetOLD.size() > 0)
					map.put(mLCDutySetOLD, "UPDATE");
				if (mLCPremSetOLD.size() > 0)
					map.put(mLCPremSetOLD, "UPDATE");
				if (mLCGetSetOLD.size() > 0)
					map.put(mLCGetSetOLD, "UPDATE");
				if (mLCGetSet.size() > 0)
					map.put(mLCGetSet, "UPDATE");

				map.put(mLJAPaySet, "INSERT");
				map.put(mLJSPaySet, "DELETE");

//				if (!mLJTempFeeSchema.getOtherNoType().equals("00")
//						&& !mLJTempFeeSchema.getOtherNoType().equals("01")) {
//					map.put(mLJTempFeeSchema, "UPDATE");
//					map.put(mLJTempFeeClassNewSet, "UPDATE");
//				}
//				if (mLJTempFeeSchema.getOtherNoType().equals("00"))
//					map.put(mLCCustomerAccTraceSchema, "INSERT");
				
				map.put(mLJTempFeeSet, "UPDATE");
				map.put(mLJTempFeeClassNewSet, "UPDATE");
				if (mLJTempFeeBL.getOtherNoType() != null
						&& mLJTempFeeBL.getOtherNoType().equals("0"))
				{
				  map.put(mLCCustomerAccTraceSet, "INSERT");
				}
				
				map.put(mLJAPayPersonSet, "INSERT");
				map.put(mLASPayPersonSet, "INSERT");
				map.put(mLJSPayPersonSet, "DELETE");
				map.put(mLCContSchema, "UPDATE");
				map.put(mLCPolSet, "UPDATE");
				map.put(mLCPremNewSet, "UPDATE");
				map.put(mLCDutyNewSet, "UPDATE");
				map.put(mLCRnewStateHistorySet, "UPDATE");
				map.put(mLCContStateSet_UPDATE, "UPDATE");
				map.put(mLCContStateSet_INSERT, "INSERT");
				map.put(mLOPRTManagerSet_INSERT, "INSERT");
				
				// map.put(mLCInsureAccSet, "DELETE&INSERT");
				// map.put(mLCInsureAccTraceSet, "INSERT");
				//增加账户处理
		        if (mLCInsureAccClassFeeSet != null && mLCInsureAccClassFeeSet.size() > 0){
		        	TaxCalculator.calBySchemaSet(mLCInsureAccFeeTraceSet);
		        	map.put(mLCInsureAccFeeTraceSet, "INSERT");
		        	map.put(mLCInsureAccTraceSet, "INSERT");
		        	map.put(mLCInsureAccClassSet, "UPDATE");
		        	map.put(mLCInsureAccClassFeeSet, "UPDATE");
		        	map.put(mLCInsureAccSet, "UPDATE");
		        	map.put(mLCInsureAccFeeSet, "UPDATE");
		        }
				mInputData.add(map);
			} else {
				MMap tBQMMap = new MMap();
//				mLJAPaySchema.setSchema(mLJAPayBL); //TODO
				mLJSPaySchema.setSchema(mLJSPayBL);
//				tBQMMap.put(mLJAPaySchema, "INSERT");  //TODO: 需要确认，是否需要持久化数据库。
				tBQMMap.put(mLJSPaySchema, "DELETE");
				//tBQMMap.put(mLJAPayPersonSet, "INSERT");
				tBQMMap.put(mLJSPayPersonSet, "DELETE");
				tBQMMap.put(mLCContSchema, "UPDATE");
				tBQMMap.put(mLCPolSet, "UPDATE");
				tBQMMap.put(mLCPremNewSet, "UPDATE");
				tBQMMap.put(mLCDutyNewSet, "UPDATE");
				tBQMMap.put(mLCRnewStateHistorySet, "UPDATE");
				tBQMMap.put(mLCContStateSet_UPDATE, "UPDATE");
				tBQMMap.put(mLCContStateSet_INSERT, "INSERT");
				tBQMMap.put(mLOPRTManagerSet_INSERT, "INSERT");
				mResult.add(tBQMMap); // 传出结果
				TaxCalculator.calBySchemaSet(mLJAPayPersonSet);
				mResult.add(mLJAPayPersonSet);
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "IndiFinUrgeVerifyBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("after prepareOutputData()");
		return true;
	}
	
    /**--------------------------
     * 帐户处理－－针对投连和期缴万能
     *
     */
    public boolean DealAccount()
    {
        RnAccountDeal mRnAccountDeal = new RnAccountDeal();

        VData xVData = new VData();
        xVData.add(mGI);
        logger.debug(mGI.Operator);
        //add ZC LJAPayPerson
        LJAPayPersonSet nLJAPayPersonSet = new LJAPayPersonSet();
        for (int j = 1; j <= mLJAPayPersonSet.size(); j++){
            LJAPayPersonSchema mLJAPayPersonSchema = new LJAPayPersonSchema();
            mLJAPayPersonSchema = mLJAPayPersonSet.get(j);

            if (mLJAPayPersonSchema.getPayType().equals("ZC")){
                nLJAPayPersonSet.add(mLJAPayPersonSchema);
            }
        }
        TaxCalculator.calBySchemaSet(nLJAPayPersonSet);
        xVData.add(nLJAPayPersonSet);

        if (!mRnAccountDeal.submitData(xVData, "")){
            this.mErrors.copyAllErrors(mRnAccountDeal.mErrors);
            return false;
        }

        /**@todo 生成帐户处理新数据**/
        mLCInsureAccClassFeeSet = (LCInsureAccClassFeeSet) mRnAccountDeal.getResult().getObjectByObjectName("LCInsureAccClassFeeSet", 0);
        mLCInsureAccFeeTraceSet = (LCInsureAccFeeTraceSet) mRnAccountDeal.getResult().getObjectByObjectName("LCInsureAccFeeTraceSet", 0);
        mLCInsureAccFeeSet = (LCInsureAccFeeSet) mRnAccountDeal.getResult().getObjectByObjectName("LCInsureAccFeeSet", 0);
        mLCInsureAccClassSet = (LCInsureAccClassSet) mRnAccountDeal.getResult().getObjectByObjectName("LCInsureAccClassSet", 0);
        mLCInsureAccTraceSet = (LCInsureAccTraceSet) mRnAccountDeal.getResult().getObjectByObjectName("LCInsureAccTraceSet", 0);
        mLCInsureAccSet = (LCInsureAccSet) mRnAccountDeal.getResult().getObjectByObjectName("LCInsureAccSet", 0);

        return true;
    }
    
	/**
	 * 公共核销程序
	 * 
	 * @param TempFeeNo
	 *            暂交费号
	 * @return 包含纪录的集合(纪录如何处理具体见prepareOutputData函数)
	 */
	public VData ReturnData(String TempFeeNo) {
		if (TempFeeNo == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IndiFinUrgeVerifyBL";
			tError.functionName = "ReturnData";
			tError.errorMessage = "传入暂交费号不能为空";
			this.mErrors.addOneError(tError);
			return null;
		}

		// 1-查询暂交费表，将TempFeeNo输入Schema中传入，查询得到Set集
		VData tVData = new VData();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		TempFeeQueryUI tTempFeeQueryUI = new TempFeeQueryUI();
		tLJTempFeeSchema.setTempFeeNo(TempFeeNo);
		tLJTempFeeSchema.setTempFeeType("2"); // 交费类型为2：续期催收交费
		tVData.add(tLJTempFeeSchema);
		if (!tTempFeeQueryUI.submitData(tVData, "QUERY")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tTempFeeQueryUI.mErrors);
			CError tError = new CError();
			tError.moduleName = "IndiFinUrgeVerifyBL";
			tError.functionName = "ReturnData";
			tError.errorMessage = "暂交费查询失败";
			this.mErrors.addOneError(tError);
			return null;
		}
		tVData.clear();
		tVData = tTempFeeQueryUI.getResult();
		tLJTempFeeSet.set((LJTempFeeSet) tVData.getObjectByObjectName(
				"LJTempFeeSet", 0));
		
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();
		for(int i=1;i<=tLJTempFeeSet.size();i++)
		{
			tLJTempFeeSchema = (LJTempFeeSchema) tLJTempFeeSet.get(1);
			double tempMoney = tLJTempFeeSchema.getPayMoney();
			// 2-查询应收总表
			tVData.clear();
			tLJSPaySchema = new LJSPaySchema();
			LJSPaySet tLJSPaySet = new LJSPaySet();
			VerDuePayFeeQueryUI tVerDuePayFeeQueryUI = new VerDuePayFeeQueryUI();
			tLJSPaySchema.setGetNoticeNo(TempFeeNo);
			tLJSPaySchema.setCurrency(tLJTempFeeSet.get(i).getCurrency());
			tVData.add(tLJSPaySchema);
			if (!tVerDuePayFeeQueryUI.submitData(tVData, "QUERY")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tVerDuePayFeeQueryUI.mErrors);
				CError tError = new CError();
				tError.moduleName = "IndiFinUrgeVerifyBL";
				tError.functionName = "ReturnData";
				tError.errorMessage = "应收总查询失败";
				this.mErrors.addOneError(tError);
				return null;
			}
			tVData.clear();
			tVData = tVerDuePayFeeQueryUI.getResult();
			tLJSPaySet
					.set((LJSPaySet) tVData.getObjectByObjectName("LJSPaySet", 0));
			tLJSPaySchema = (LJSPaySchema) tLJSPaySet.get(1);
			double sumDueMoney = tLJSPaySchema.getSumDuePayMoney();
			if (sumDueMoney != tempMoney) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "IndiFinUrgeVerifyBL";
				tError.functionName = "ReturnData";
				tError.errorMessage = "应收总表纪录中的金额和暂交费纪录中的金额不相等！";
				this.mErrors.addOneError(tError);
				return null;
			}
			if (tLJTempFeeSchema.getEnterAccDate() == null) {
				CError tError = new CError();
				tError.moduleName = "IndiFinUrgeVerifyBL";
				tError.functionName = "ReturnData";
				tError.errorMessage = "财务缴费还没有到帐!（暂交费收据号："
						+ tLJTempFeeSchema.getTempFeeNo().trim() + "）";
				this.mErrors.addOneError(tError);
				return null;
			}
		}
		
		// if(PubFun.calInterval(PubFun.getCurrentDate(),tLJSPaySchema.getPayDate(),"D")<0)
		// {
		// CError tError = new CError();
		// tError.moduleName = "IndiFinUrgeVerifyBL";
		// tError.functionName = "ReturnData";
		// tError.errorMessage = "保单已过失效日期:应收纪录超过失效期核销 ";
		// this.mErrors .addOneError(tError) ;
		// return null;
		// }
		tVData.clear();
		tVData.add(tLJTempFeeSchema);
		tVData.add(tLJSPaySchema);
		// 3-调用核销程序
		this.mOperate = "VERIFY";
		if (!getInputData(tVData))
			return null;

		// 进行业务处理
		if (!dealData())
			return null;
		logger.debug("After dealData！");
		// 准备往后台的数据
		if (!prepareOutputData())
			return null;

		return mInputData;
	}


	// /*********************************BQ*************************************\
	// 保全用接口标志。保全保费自垫要复用这个类，因此添加此接口。
	// 设置了这个接口，就按保全要求处理数据，不影响其它程序使用本类。
	private String mBQUseFlag = "";

	public void setBQUseFlag() {
		mBQUseFlag = "BQ";
	}

	private String mBQGetNoticeNo = "";

	public void setBQGetNoticeNo(String tValue) {
		mBQGetNoticeNo = tValue;
	}
	
	private LJAPayPersonBL getActPay(LJSPayPersonBL xLJSPayPersonBL)
	{
		LJAPayPersonBL xLJAPayPersonBL = new LJAPayPersonBL();
		xLJAPayPersonBL.setPolNo(xLJSPayPersonBL.getPolNo()); // 核销以后用新号，不再换号
		xLJAPayPersonBL.setPayCount(xLJSPayPersonBL.getPayCount()); // 第几次交费
		xLJAPayPersonBL.setMainPolYear(xLJSPayPersonBL
				.getMainPolYear());
		xLJAPayPersonBL
				.setGrpContNo(xLJSPayPersonBL.getGrpContNo()); // 集体保单号码
		xLJAPayPersonBL.setGrpPolNo(xLJSPayPersonBL.getGrpPolNo()); // 集体保单号码
		xLJAPayPersonBL.setContNo(xLJSPayPersonBL.getContNo()); // 总单/合同号码
		xLJAPayPersonBL.setAppntNo(xLJSPayPersonBL.getAppntNo()); // 投保人客户号码
		xLJAPayPersonBL.setPayNo(payNo); // 交费收据号码
		xLJAPayPersonBL.setPayAimClass(xLJSPayPersonBL
				.getPayAimClass()); // 交费目的分类
		xLJAPayPersonBL.setDutyCode(xLJSPayPersonBL.getDutyCode()); // 责任编码
		xLJAPayPersonBL.setPayPlanCode(xLJSPayPersonBL
				.getPayPlanCode()); // 交费计划编码
		xLJAPayPersonBL.setCurrency(xLJSPayPersonBL.getCurrency());
		xLJAPayPersonBL.setSumDuePayMoney(xLJSPayPersonBL
				.getSumDuePayMoney()); // 总应交金额
		xLJAPayPersonBL.setSumActuPayMoney(xLJSPayPersonBL
				.getSumActuPayMoney()); // 总实交金额
		xLJAPayPersonBL.setPayIntv(xLJSPayPersonBL.getPayIntv()); // 交费间隔
		xLJAPayPersonBL.setPayDate(xLJSPayPersonBL.getPayDate()); // 交费日期
		xLJAPayPersonBL.setPayType(xLJSPayPersonBL.getPayType()); // 交费类型
		if (actuVerifyFlag == false) 
		{

			xLJAPayPersonBL.setEnterAccDate(mLJTempFeeBL.getEnterAccDate());
		} 
		else 
		{
			xLJAPayPersonBL.setEnterAccDate(CurrentDate); // 到帐日期
		}

		xLJAPayPersonBL.setConfDate(CurrentDate); // 确认日期
		xLJAPayPersonBL.setLastPayToDate(xLJSPayPersonBL
				.getLastPayToDate()); // 原交至日期
		xLJAPayPersonBL.setCurPayToDate(xLJSPayPersonBL
				.getCurPayToDate()); // 现交至日期
		xLJAPayPersonBL.setInInsuAccState(xLJSPayPersonBL
				.getInInsuAccState()); // 转入保险帐户状态
		xLJAPayPersonBL.setApproveCode(xLJSPayPersonBL
				.getApproveCode()); // 复核人编码
		xLJAPayPersonBL.setApproveDate(xLJSPayPersonBL
				.getApproveDate()); // 复核日期
		// 更改完毕
		xLJAPayPersonBL
				.setAgentCode(xLJSPayPersonBL.getAgentCode());
		xLJAPayPersonBL.setAgentGroup(xLJSPayPersonBL
				.getAgentGroup());
		xLJAPayPersonBL.setSerialNo(serNo); // 流水号
		xLJAPayPersonBL.setOperator(mGI.Operator); // 操作员
		xLJAPayPersonBL.setMakeDate(CurrentDate); // 入机日期
		xLJAPayPersonBL.setMakeTime(CurrentTime); // 入机时间
		xLJAPayPersonBL.setGetNoticeNo(xLJSPayPersonBL
				.getGetNoticeNo()); // 通知书号码
		xLJAPayPersonBL.setPayTypeFlag(xLJSPayPersonBL.getPayTypeFlag()); //主险续保标记
		xLJAPayPersonBL.setModifyDate(CurrentDate); // 最后一次修改日期
		xLJAPayPersonBL.setModifyTime(CurrentTime); // 最后一次修改时间
		xLJAPayPersonBL
				.setManageCom(xLJSPayPersonBL.getManageCom()); // 管理机构
		xLJAPayPersonBL.setAgentCom(xLJSPayPersonBL.getAgentCom()); // 代理机构
		xLJAPayPersonBL
				.setAgentType(xLJSPayPersonBL.getAgentType()); // 代理机构内部分类
		xLJAPayPersonBL.setRiskCode(xLJSPayPersonBL.getRiskCode()); // 险种编码
		
		return xLJAPayPersonBL;
	}

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	public VData getResult() {
		return mResult;
	}

	// \*********************************BQ*************************************/

	public static void main(String[] args) {
		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "DEV";
		mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";
		VData tVData = new VData();
		tVData.add(mGlobalInput);
		IndiFinUrgeVerifyBL tIndiFinUrgeVerifyBL = new IndiFinUrgeVerifyBL();
		LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setTempFeeNo("86110020040310000478");
		tLJTempFeeSchema.setOtherNo("86310020080210015027");
		tVData.add(tLJTempFeeSchema);
		if (!tIndiFinUrgeVerifyBL.submitData(tVData, "VERIFY")) {
		}
	}

}
