/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.lis.bl.LCAppntBL;
import com.sinosoft.lis.bl.LCBankAccBL;
import com.sinosoft.lis.bl.LCBankAuthBL;
import com.sinosoft.lis.bl.LCBnfBL;
import com.sinosoft.lis.bl.LCCustomerImpartBL;
import com.sinosoft.lis.bl.LCDutyBL;
import com.sinosoft.lis.bl.LCGetBL;
import com.sinosoft.lis.bl.LCGrpAppntBL;
import com.sinosoft.lis.bl.LCInsuredBL;
import com.sinosoft.lis.bl.LCInsuredRelatedBL;
import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LCSpecBL;
import com.sinosoft.lis.bq.BqCalBL;
import com.sinosoft.lis.bq.GrpEdorNIDetailBL;
import com.sinosoft.lis.certify.PubCertifyTakeBack;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContPlanDutyParamDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMRiskRoleDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.CheckFieldCom;
import com.sinosoft.lis.pubfun.FieldCarrier;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsuredRelatedSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolOtherSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.vbl.LCBnfBLSet;
import com.sinosoft.lis.vbl.LCCustomerImpartBLSet;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vbl.LCInsuredBLSet;
import com.sinosoft.lis.vbl.LCPremBLSet;
import com.sinosoft.lis.vbl.LCSpecBLSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsuredRelatedSet;
import com.sinosoft.lis.vschema.LCPolOtherSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.lis.vschema.LMRiskDutySet;
import com.sinosoft.lis.vschema.LMRiskRoleSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 投保业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author YT
 * @version 1.0
 */
public class ProposalBL {
private static Logger logger = Logger.getLogger(ProposalBL.class);
	/*
	 * 测试使用
	 */
	public static void main(String[] args) {
		ProposalBL ProposalBL1 = new ProposalBL();

		GlobalInput tG = new GlobalInput();
		/** 全局变量 */
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setContNo("130210000018412");

		LCPolSchema tLCPolSchema = new LCPolSchema();
		tLCPolSchema
				.decode("20061026000001||130210000018412||110210000025706|20061026000001||0|110210000025706|||00334000|2002|86210000|fa"
						+ "lse|01|00000001|000000000037||2||||||21|1||||2006-10-26||||2006-10-26|||||||0||0||0||0||N|||0|0|0|0|1.0||0|0|2.0|0|10000.0|"
						+ "0|0|0|0|0|0||||||-2|||||||N||||||||||||||||||||2006-10-26|||1|10000|||||||0||||||||");

		LCDutySchema tLCDutySchema = new LCDutySchema();
		tLCDutySchema
				.decode("|||0|0|2.0|0|10000.0|0|0|0|0|1.0||0|||M|12|Y|0|D|12||0||||0|||||||||0|0|0||G|1|10000||||||||0");
		// LCDutySet tLCDutySet = new LCDutySet();
		// tLCDutySet.decode(
		// "tLCDutySet:|001100||0|0|0|0|0|0|0|0|0|0||0||||0||0||0||0||||0|||||||||0|0|0|||1|"
		// +
		// "||||||||0^|001101||0|0|0|0|0|0|0|0|0|0||0||||0||0||0||0||||0|||||||||0|0|0|||1||"
		// +
		// "|||||||0");

		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntSchema
				.decode("|130210000018412||false||China BeiJing SinoSoftCo.Ltd|false||||||||||||0|0|||||||||0||||||||||||0||");

		LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
		tLCGrpAppntSchema.decode("20061026000001|0000000010||||||||||||||||");

		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredSchema
				.decode("|130210000018412|0000000010|||||||||||||||||||||0|0|||||||||0|||||||||||||||||0|0|||0");

		// LCPremSet tLCPremSet = new LCPremSet();
		// LCPremSchema tLCPremSchema1 = new LCPremSchema();
		// LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
		// LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();
		// tLCPremSchema1.decode(
		// "|||692001|692101||||||0|0||||0|200.0|200.0|0|0||0|||||||||||0");
		// tLCPremToAccSchema.decode("|692001|692101||0|||||||||");
		// tLCPremSet.add(tLCPremSchema1);
		// tLCPremToAccSet.add(tLCPremToAccSchema);

		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("getIntv", 0); // 领取间隔（方式）
		tTransferData.setNameAndValue("GetDutyKind", null);
		tTransferData.setNameAndValue("samePersonFlag", null);
		tTransferData.setNameAndValue("deleteAccNo", "1");
		tTransferData.setNameAndValue("ChangePlanFlag", "1");
		tTransferData.setNameAndValue("LoadFlag", null);
		tTransferData.setNameAndValue("SavePolType", null); // 保全保存标记
		tTransferData.setNameAndValue("EdorType", null);

		// tTransferData.setNameAndValue("SavePolType", "0");
		// //保全保存标记，默认为0，标识非保全
		// //tTransferData.setNameAndValue("FamilyType","0");//家庭单标记
		// tTransferData.setNameAndValue("ContType", "2"); //团单，个人单标记
		// tTransferData.setNameAndValue("PolTypeFlag", "2"); //无名单标记
		// tTransferData.setNameAndValue("InsuredPeoples", 0); //被保险人人数
		// tTransferData.setNameAndValue("PubAmntFlag", "N"); //公共保额标记

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.addElement(tLCContSchema);
		tVData.addElement(tLCPolSchema);
		tVData.addElement(tLCAppntSchema);
		tVData.addElement(tLCGrpAppntSchema);
		tVData.addElement(tLCInsuredSchema);
		// tVData.addElement(tLCDutySet);
		tVData.addElement(tLCDutySchema);
		// tVData.addElement(tLCPremSet);
		// tVData.addElement(tLCPremToAccSet);

		tVData.addElement(tG);
		tVData.addElement(tTransferData);

		ProposalBL1.submitData(tVData, "UPDATE||PROPOSAL");
		if (ProposalBL1.mErrors.needDealError()) {
			logger.debug(ProposalBL1.mErrors.getFirstError());
		}
	}

	private boolean autoCalFloatRateFlag = false; // 是否自动计算浮动费率,如果界面传入浮动费率=
	// ConstRate，自动计算

	private final double ConstRate = -1; // 如果标明需要从输入的保费保额计算浮动费率，那么界面传入的浮动费率值为常量
	private boolean deleteAccNo = false;
	private String EdorType;
	/* 处理浮动费率的变量 */
	private double FloatRate = 0; // 浮动费率
	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数

	private String FRateFORMATMODOL = "0.000000000000"; // 浮动费率计算出来后的精确位数
	private double InputAmnt = 0; // 保存界面录入的保额
	private double InputPrem = 0; // 保存界面录入的保费
	private String insuredno;
	/** 交叉销售标志 * */
	private boolean isCrossSaleChnl = false;
	// 主险保单
	private LCPolBL mainLCPolBL = new LCPolBL();
	private MMap map = new MMap(); // add by yaory

	/** 投保单类型 */
	private String mAppntType = "1"; // 默认为个人投保人
	private String mark = "";
	private String mChangePlanFlag = "1";
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();
	private String mDealFlag = "1"; // 处理逻辑，1为个单，2为团单
	private String mAutoPayFlag = "";
	
	private List mBomList = new ArrayList();
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象
	private LCPremBLSet mDiskLCPremBLSet = new LCPremBLSet(); // 保存特殊的保费项数据(
	// 目前针对磁盘投保
	// ，不用计算保费保额类型)
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private DecimalFormat mFRDecimalFormat = new DecimalFormat(FRateFORMATMODOL); // 数字转换对象

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 是否文件投保标记
	boolean mGrpImportFlag = false;

	/** 往后面传输数据的容器 */
	private VData mInputData;
	boolean mISPlanRiskFlag = false;
	/** 投保人 */
	private LCAppntBL mLCAppntBL = new LCAppntBL();
	/** 银行账户表 */
	private LCBankAccBL mLCBankAccBL = new LCBankAccBL();
	/** 银行授权书表 */
	private LCBankAuthBL mLCBankAuthBL = new LCBankAuthBL();
	/** 受益人 */
	private LCBnfBLSet mLCBnfBLSet = new LCBnfBLSet();
	/** 折扣信息 */
	private LCDiscountSet mLCDiscountSet = new LCDiscountSet();
	/** 应收子表 */
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	
	private LCBnfBLSet mLCBnfBLSetNew = new LCBnfBLSet();
	/** 合同表 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 告知信息 */
	private LCCustomerImpartBLSet mLCCustomerImpartBLSet = new LCCustomerImpartBLSet();

	private LCCustomerImpartBLSet mLCCustomerImpartBLSetNew = new LCCustomerImpartBLSet();

	// 一般的责任信息
	private LCDutyBL mLCDutyBL = new LCDutyBL();

	/** 责任表 */
	private LCDutyBLSet mLCDutyBLSet = new LCDutyBLSet();

	/** 可选责任信息 */
	private LCDutyBLSet mLCDutyBLSet1 = new LCDutyBLSet();
	/** 给付项表 */
	private LCGetBLSet mLCGetBLSet = new LCGetBLSet();
	private LCGrpAppntBL mLCGrpAppntBL = new LCGrpAppntBL();
	// private LCGrpPolBL mLCGrpPolBL = new LCGrpPolBL();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema(); // 磁盘投保时，
	// 该集体信息从外部传入
	// ，
	// 否则内部查询
	private String EdorTypeCal = "";
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema(); // 磁盘投保时，
	// 该集体信息从外部传入
	// ，否则内部查询
	/** 被保人 */
	private LCInsuredBL mLCInsuredBL = new LCInsuredBL();

	private LCInsuredBLSet mLCInsuredBLSet = new LCInsuredBLSet();

	private LCInsuredBLSet mLCInsuredBLSetNew = new LCInsuredBLSet();
	/** 连带，连生被保人 */
	private LCInsuredRelatedSet mLCInsuredRelatedSet = new LCInsuredRelatedSet();

	/** 保单 */
	private LCPolBL mLCPolBL = new LCPolBL();
	private LCPolOtherSchema mLCPolOtherSchema = new LCPolOtherSchema();

	// 其他险种信息
	private LCPolOtherSet mLCPolOtherSet = new LCPolOtherSet();
	/** 保费项表 */
	private LCPremBLSet mLCPremBLSet = new LCPremBLSet();
	/** 特别约定 */
	private LCSpecBLSet mLCSpecBLSet = new LCSpecBLSet();

	private LCSpecSet mLCSpecBLSetNew = new LCSpecBLSet();
	private LDGrpSchema mLDGrpSchema = new LDGrpSchema();

	/** 个人客户表 */
	private LDPersonSet mLDPersonSet = new LDPersonSet();
	/** 应收总表 * */
	LJSPaySet mLJSPaySet = new LJSPaySet();

	/** 险种承保描述表 */
	private LMRiskAppSchema mLMRiskAppSchema = new LMRiskAppSchema();
	/** 险种描述表 */
	private LMRiskSchema mLMRiskSchema = new LMRiskSchema();
	/** 计划参数表*/
	private LCContPlanDutyParamSet mLCContPlanDutyParamSet = new LCContPlanDutyParamSet();

	private boolean mNeedDuty = false;
	/** 数据操作字符串 */
	private String mOperate;
	private String aCurrency="";//币种信息
	private String locCurrency = "";//本币信息
	private String mPolType = "1"; // 默认为个人投保单类型
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	// 保存投保单对应的操作类型,默认是投保单标记(其他可能是保全类型)
	private String mSavePolType = "0";

	/** 业务处理相关变量 */
	/** 接受前台传输数据的容器 */
	private TransferData mTransferData = new TransferData();

	private LCInsuredRelatedSet newLCInsuredRelatedSet = new LCInsuredRelatedSet();

	/* 不需要保费保额计算的标记，用于特殊的险种。譬如：众悦年金分红 */
	private boolean noCalFlag = false;
	private String OriginOperator = ""; // 更新时保存原先保单的操作员

	private double OriginPrem = 0; // 更新时保存原先保单的实际保费

	private double OriginStandPrem = 0; // 更新时保存原先保单的标准保费

	private VData rResult = new VData(); // add by yaory

	private boolean TakeBackCertifyFalg = false; // 是否回收单证标记
	
	private String YBTFlag = "";
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	private TransferData tTransferData = new TransferData();

	//tongmeng 2010-12-02 modify
	LDExch mLDExch = new LDExch();
	public ProposalBL() {
	}

	/**
	 * 不包含校验的承保数据处理，不保存数据
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public VData appNoChk(VData cInputData, String cOperate) {
		VData tReturn = null;

		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (getInputData(cInputData) == false) {
			return null;
		}

		// 进行业务处理
		if (dealData() == false) {
			// @@错误处理
			return null;
		}

		// 准备往后台的数据
		if (prepareOutputData() == false) {
			return null;
		}

		// 检查是否出错
		if (this.mErrors.needDealError() == false) {
			tReturn = mInputData;
		}

		return tReturn;
	}

	public boolean BqCalShortRate() {
		// LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		// String bqSql = "select * from LPGrpEdorItem where grpcontno='"
		// + mLCGrpPolSchema.getGrpContNo() +
		// "' and edorstate!='0' and edortype='"
		// + EdorType + "'";
		// LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(
		// bqSql);
		// if (tLPGrpEdorItemSet != null && tLPGrpEdorItemSet.size() > 0) {
		// mLCPolBL.setCValiDate(tLPGrpEdorItemSet.get(1).getEdorValiDate());
		// }
		String tCalRule = mLCDutyBLSet.get(1).getCalRule();
		if("3".equals(tCalRule)){
			//约定保额保费不管它
			return true;
		}
		GrpEdorNIDetailBL tGrpEdorNIDetailBL = new GrpEdorNIDetailBL();
		String NICValiDate = tGrpEdorNIDetailBL.cal_CValiDate(mLCPolBL,
				EdorType);
		if (NICValiDate != null && !NICValiDate.equals("")) {
			mLCPolBL.setCValiDate(NICValiDate);
		}
		BqCalBase tBqCalBase = new BqCalBase();
		tBqCalBase.setEdorValiDate(mLCPolBL.getCValiDate());
		//
		//tBqCalBase.setCValiDate(mLCGrpPolSchema.getCValiDate());
		tBqCalBase.setCValiDate(mLCPolBL.getCValiDate());
		logger.debug(tBqCalBase.getCValiDate());
		tBqCalBase.setEndDate(mLCPolBL.getPayEndDate());
		//tBqCalBase.setEndDate(mLCGrpPolSchema.getPayEndDate());

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		String bqSql = "select * from LCGrpPol where grppolno='"
			+ "?grppolno?" + "' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(bqSql);
		sqlbv1.put("grppolno", mLCPolBL.getGrpPolNo());
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.executeQuery(sqlbv1);
		if (tLCGrpPolSet != null && tLCGrpPolSet.size() > 0) {
			String tEndDate = tLCGrpPolSet.get(1).getPayEndDate();
			tBqCalBase.setPayToDate(tEndDate);
			tBqCalBase.setGrpCValiDate(tLCGrpPolSet.get(1).getCValiDate());
			if(EdorType.equals("NR")){

				String tSQL = "select max(enddate) from lcpol where contno = '"+"?contno?"+"' and appflag = '1'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(tSQL);
				sqlbv2.put("contno", mLCPolBL.getContNo());
				ExeSQL tExeSQL = new ExeSQL();
				String tContPayToDate = tExeSQL.getOneValue(sqlbv2);
				if(tContPayToDate == null ||tContPayToDate.equals(""))
				{
					CError.buildErr(this, "查询被保人合同终止日期失败!");
					return false;
				}
				int intv = PubFun.calInterval(mLCPolBL.getCValiDate(), tContPayToDate, "D");
				if(intv<=0)
				{
					CError.buildErr(this, "被保人合同已经过期,不能添加!");
					return false;
				}
				intv = PubFun.calInterval(tContPayToDate, tEndDate, "D");
				if(intv>=0)
				{
					tBqCalBase.setPayToDate(tContPayToDate.substring(0,10));
				}
			}
		}

		tBqCalBase.setPayIntv(mLCPolBL.getPayIntv());
		tBqCalBase.setPolNo(mLCPolBL.getPolNo());
		tBqCalBase.setEdorTypeCal(EdorTypeCal);
		tBqCalBase.setGrpPolNo(mLCPolBL.getGrpPolNo());

		LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
		tLMEdorCalDB.setRiskCode(mLCPolBL.getRiskCode());
		tLMEdorCalDB.setCalType("Rate");
		tLMEdorCalDB.setEdorType(EdorType);
		LMEdorCalSet tLMEdorCalSet = tLMEdorCalDB.query(); // 查找保费累计变动金额计算信息
		if (tLMEdorCalDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLMEdorCalDB.mErrors);
			mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
			return false;
		}
		if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0) {
			return true; // 没有计算信息，则不作计算
		} else {
			BqCalBL tBqCalBL = new BqCalBL();
			double tValue = tBqCalBL.calShortRate(tLMEdorCalSet.get(1)
					.getCalCode(), tBqCalBase); // 计算各险种保费补退费

			if (tBqCalBL.mErrors.needDealError()) {
				mErrors.copyAllErrors(tBqCalBL.mErrors);
				mErrors.addOneError(new CError("计算短期费率失败！"));
				return false;
			}

			for (int i = 1; i <= mLCPremBLSet.size(); i++) {

				if (mLCPremBLSet.get(i).getPayIntv() == 0) {
					mLCPremBLSet.get(i).setStandPrem(
							mLCPremBLSet.get(i).getPrem() * tValue);
					mLCPremBLSet.get(i).setPrem(
							mLCPremBLSet.get(i).getPrem() * tValue);
				}
				mLCPremBLSet.get(i).setSumPrem(
						mLCPremBLSet.get(i).getPrem() * tValue);

			}
			for (int j = 1; j <= mLCDutyBLSet.size(); j++) {

				if (mLCDutyBLSet.get(j).getPayIntv() == 0) {
					mLCDutyBLSet.get(j).setStandPrem(
							mLCDutyBLSet.get(j).getPrem() * tValue);
					mLCDutyBLSet.get(j).setPrem(
							mLCDutyBLSet.get(j).getPrem() * tValue);
				}
				mLCDutyBLSet.get(j).setSumPrem(
						mLCDutyBLSet.get(j).getPrem() * tValue);

			}
			if (mLCPolBL.getPayIntv() == 0) {
				mLCPolBL.setStandPrem(mLCPolBL.getPrem() * tValue);
				mLCPolBL.setPrem(mLCPolBL.getPrem() * tValue);
			}
			mLCPolBL.setSumPrem(mLCPolBL.getPrem() * tValue);
		}
		return true;
	}

	public boolean BqTransEndDate() {
		//mLCPolBL.setEndDate(mLCGrpPolSchema.getPayEndDate());
		mLCPolBL.setPayEndDate(mLCPolBL.getEndDate());
		for (int i = 1; i <= mLCPremBLSet.size(); i++) {
			mLCPremBLSet.get(i).setPayEndDate(mLCPolBL.getEndDate());
		}
		return true;
	}

	/**
	 * 数据处理校验 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean checkData() {

		// 判断投保单的类型 1--个人 2--集体 3--合同 //PQ 应该没有mPolType='3'的情况
		if (!mDealFlag.equals("1")) {
			mPolType = "2";
			mAppntType = "2";
		}

		if (mOperate.equals("INSERT||PROPOSAL")
				|| mOperate.equals("UPDATE||PROPOSAL")) {
			if (mLCPolBL.getPolTypeFlag() == null) { // 保单类型标记0--个人单，1--无名单，2
				// ---公共帐户)
				mLCPolBL.setPolTypeFlag("0"); // 缺省设为个人单
			}
			if (mLCPolBL.getAppFlag() == null
					|| mLCPolBL.getAppFlag().equals("")
					|| "".equals(mLCGrpPolSchema.getAppFlag())) { // 签单标记为空时，补为0
				String tSavePolType = (String) mTransferData
						.getValueByName("SavePolType");
				if (tSavePolType != null) {
					if (tSavePolType.trim().equals("")) {
						mLCPolBL.setAppFlag("0");
						mLCPolBL.setPolState("0");
					} else {
						mLCPolBL.setAppFlag(tSavePolType);
						mLCPolBL.setPolState("0");
					}
				} else {
					mLCPolBL.setAppFlag("0");
					mLCPolBL.setPolState("0");
				}

			}
			if (mPolType.equals("2")) {
				if (mLCGrpContSchema == null) {
					// 如果是团单，从外界传入的险种承保描述信息和险种描述信息为空，那么从数据库查询
					// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
					LCGrpContDB tLCGrpContDB = new LCGrpContDB();
					tLCGrpContDB.setGrpContNo(mLCPolBL.getGrpContNo());

					if (tLCGrpContDB.getInfo() == false) {
						// @@错误处理
						this.mErrors.copyAllErrors(tLCGrpContDB.mErrors);
						CError.buildErr(this, "集体(投)合同表取数失败！");
						return false;
					}
					mLCGrpContSchema = tLCGrpContDB.getSchema();
					// mLCGrpContSchema.setSchema(tLCGrpContDB);

				}
				if (mLCGrpPolSchema == null) {
					// 如果是团单，从外界传入的险种承保描述信息和险种描述信息为空，那么从数据库查询
					// LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
					LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
					tLCGrpPolDB.setGrpContNo(mLCPolBL.getGrpContNo());
					tLCGrpPolDB.setRiskCode(mLCPolBL.getRiskCode());

					LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
					if (tLCGrpPolDB.mErrors.needDealError()
							|| tLCGrpPolSet.size() == 0) {
						// @@错误处理
						this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
						CError.buildErr(this, "集体险种表取数失败！");
						return false;
					}
					mLCGrpPolSchema = tLCGrpPolSet.get(1).getSchema();
				}
				mLCPolBL.setGrpPolNo(mLCGrpPolSchema.getGrpPolNo());
				mLCPolBL.setAgentCode(mLCContSchema.getAgentCode());
				mLCPolBL.setAgentGroup(mLCContSchema.getAgentGroup());
				mLCPolBL.setAgentCom(mLCContSchema.getAgentCom());
				mLCPolBL.setSaleChnl(mLCContSchema.getSaleChnl());
				mLCPolBL.setPayIntv(mLCGrpPolSchema.getPayIntv());
			}

			// 如果从外界传入的险种承保描述信息和险种描述信息为空，那么从数据库查询
			if (mLMRiskAppSchema == null) {
				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(mLCPolBL.getRiskCode());
				if (tLMRiskAppDB.getInfo() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);

					CError.buildErr(this, "LMRiskApp表查询失败！");
					return false;
				}
				mLMRiskAppSchema = tLMRiskAppDB.getSchema();
			}
			if (mLMRiskSchema == null) {
				LMRiskDB tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(mLCPolBL.getRiskCode());
				if (tLMRiskDB.getInfo() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLMRiskDB.mErrors);

					CError.buildErr(this, "LMRiskApp表查询失败！");
					return false;
				}
				mLMRiskSchema = tLMRiskDB.getSchema();
			}

			// 如果是个单校验代理人编码并置上代理人组别
			if (mPolType.equals("1")) {

				if ((mLCPolBL.getSaleChnl() == null)
						|| mLCPolBL.getSaleChnl().equals("")) {
					// @@错误处理
					CError.buildErr(this, "在投保数据接受时没有得到足够的数据，请您确认有：销售渠道编码！");
					return false;
				}
				if ((mLCPolBL.getAgentCode() == null)
						|| mLCPolBL.getAgentCode().equals("")) {
					// @@错误处理
					CError.buildErr(this, "在投保数据接受时没有得到足够的数据，请您确认有：代理人编码！");
					return false;
				} else {
					LAAgentDB tLAAgentDB = new LAAgentDB();
					tLAAgentDB.setAgentCode(mLCPolBL.getAgentCode());
					if (tLAAgentDB.getInfo() == false) {
						// @@错误处理
						CError.buildErr(this,
								"在投保数据接受时没有得到正确的数据，请您确认：代理人编码没有输入错误！");
						return false;
					}
					/**
					 * 保全新增附加险不进行此校验 Modidy by lizhuo at 2005-10-19
					 */
					logger.debug("================== 保全新增附加险不校验“代理人编码对应管理机构和保全管理机构是否符合”==============");
					logger.debug("================== Modify at 2005-10-19 by lizhuo ======================================");
					logger.debug("================== EdorType ======================="
									+ EdorType);
					if (EdorType == null || !EdorType.trim().equals("NS")) {
						String tSaleChnl = mLCPolBL.getSaleChnl();
						if(tSaleChnl!=null&&!tSaleChnl.equals("03")&&!tSaleChnl.equals("05"))
						{
						if ((tLAAgentDB.getManageCom() == null)
								|| !tLAAgentDB.getManageCom().equals(
										mLCPolBL.getManageCom())) {
							// @@错误处理
							CError.buildErr(this,
									"代理人编码对应数据库中的管理机构为空或者和您录入的管理机构不符合！");
							return false;
						}
						}
					}
					mLCPolBL.setAgentGroup(tLAAgentDB.getBranchCode());
					/**
					 * 根据业务员的branchtype决定保单的salechnl
					 * branchtype：1-个险，2-团险，3-银代，4-收费员 salechnl：1-个险，2-团险，3-银代
					 */
					// tongmeng 2007-09-10
					// 增加注释：6.0是按照代理人销售渠道设置保单的渠道的，此在升级时需要做讨论
					// 销售渠道取录单界面的值，下面的程序注释掉
//					if (tLAAgentDB.getBranchType().equals("1")
//							|| tLAAgentDB.getBranchType().equals("7")
//							|| tLAAgentDB.getBranchType().equals("4")) {
//						mLCPolBL.setSaleChnl("01");
//					} else if (tLAAgentDB.getBranchType().equals("2")) {
//						mLCPolBL.setSaleChnl("02");
//					} else if (tLAAgentDB.getBranchType().equals("3")) {
//						mLCPolBL.setSaleChnl("03");
//					} else if (tLAAgentDB.getBranchType().equals("5")) {
//						mLCContSchema.setSaleChnl("05");
//					} else {
//						// @@错误处理
//						CError.buildErr(this, "业务员销售渠道超出投保单的销售渠道的范围！");
//						return false;
//
//					}

					/**
					 * @todo 进行业务员交叉销售的判断
					 */
					// 判断该张保单是否属于交叉销售，如果属于交叉销售则进行业务员销售资格判断，否则不需要进行判断
					/**
					 * 利用lmriskapp表中的mngcom字段进行判断 G－团险 I－个险 B－银代
					 */
					// 判断险种的销售渠道
					if (mLMRiskAppSchema == null) {
						LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
						tLMRiskAppDB.setRiskCode(mLCPolBL.getRiskCode());
						if (tLMRiskAppDB.getInfo() == false) {
							// @@错误处理
							this.mErrors.copyAllErrors(tLMRiskAppDB.mErrors);

							CError.buildErr(this, "LMRiskApp表查询失败！");
							return false;
						}
						mLMRiskAppSchema = tLMRiskAppDB.getSchema();
					}
					if (mLMRiskAppSchema.getMngCom() != null
							&& !mLMRiskAppSchema.getMngCom().equals("")) {
						// I-个险
						if (mLMRiskAppSchema.getMngCom().equals("I")) {
							if (tLAAgentDB.getBranchType().equals("1")) {
								isCrossSaleChnl = false;
							} else if (tLAAgentDB.getBranchType().equals("2")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("3")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("4")) {
								isCrossSaleChnl = false;
							} else if (tLAAgentDB.getBranchType().equals("5")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("7")) {
								isCrossSaleChnl = false;
							} else {
							}
						}
						// G-团险
						else if (mLMRiskAppSchema.getMngCom().equals("G")) {
							if (tLAAgentDB.getBranchType().equals("1")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("2")) {
								isCrossSaleChnl = false;
							} else if (tLAAgentDB.getBranchType().equals("3")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("4")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("5")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("7")) {
								isCrossSaleChnl = true;
							} else {
							}
						}
						// B-银代
						else if (mLMRiskAppSchema.getMngCom().equals("B")) {
							if (tLAAgentDB.getBranchType().equals("1")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("2")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("3")) {
								isCrossSaleChnl = false;
							} else if (tLAAgentDB.getBranchType().equals("4")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("5")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("7")) {
								isCrossSaleChnl = true;
							} else {
							}
						}
						// T-直销
						else if (mLMRiskAppSchema.getMngCom().equals("T")) {
							if (tLAAgentDB.getBranchType().equals("1")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("2")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("3")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("4")) {
								isCrossSaleChnl = true;
							} else if (tLAAgentDB.getBranchType().equals("5")) {
								isCrossSaleChnl = false;
							} else if (tLAAgentDB.getBranchType().equals("7")) {
								isCrossSaleChnl = true;
							} else {
							}
						} else {
						}
					}
					// 对于属于交叉销售的险种进行业务员销售权限的判断
					//2009-02-07 放开对交叉销售的权限校验
					isCrossSaleChnl = false;
					if (isCrossSaleChnl) {
						String sAgentCode = tLAAgentDB.getAgentCode(); //
						String sRiskCode = mLMRiskAppSchema.getRiskCode(); //
						String tSQL = "select * from laauthorize where authorobj in "
								+ " (select trim(agentcom) from lacomtoagent where agentcode = '"
								+ "?agentcode?"
								+ "')"
								+ " and authortype = '2' and riskcode = '"
								+ "?riskcode?"
								+ "'"
								+ " union "
								+ " select * from laauthorize where  authortype = '0'"
								+ " and authorobj='"
								+ "?authorobj?"
								+ "' and riskcode = '" + "?riskcode1?" + "' ";
						SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
						sqlbv3.sql(tSQL);
						sqlbv3.put("agentcode", sAgentCode);
						sqlbv3.put("riskcode",sRiskCode);
						sqlbv3.put("authorobj",sAgentCode);
						sqlbv3.put("riskcode1",sRiskCode);
						ExeSQL tExeSQL = new ExeSQL();
						SSRS tSSRS = tExeSQL.execSQL(sqlbv3);
						if (tSSRS.getMaxRow() <= 0) {
							// @@错误处理
							CError.buildErr(this, "该业务员（业务员号："
									+ tLAAgentDB.getAgentCode()
									+ "）没有授权销售该险种（险种代码："
									+ mLMRiskAppSchema.getRiskCode() + "）!");

							return false;

						}

					}

				}
			}
		}

		if (mOperate.equals("INSERT||PROPOSAL")) {
			String tSQL = "";
			ExeSQL exeSql = new ExeSQL();
			SSRS tmpSSRS = new SSRS();
			logger.debug("mLMRiskAppSchema==="
					+ mLMRiskAppSchema.getRiskCode() + "---"
					+ mLMRiskAppSchema.getSubRiskFlag());
			//团险校验
			if(mPolType.equals("2")){
				String tMainRiskcode = "";
				String tRiskCode = mLMRiskAppSchema.getRiskCode();
				String tInsuredNo = mLCInsuredBL.getInsuredNo();
					tSQL = "select riskcode,insuredno from LCPol where contno='"
						+ "?contno?" + "' and InsuredNo ='"
						+ "?InsuredNo?" + "' and polno=mainpolno and appflag <> '4'";
					SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
					sqlbv4.sql(tSQL);
					sqlbv4.put("contno", mLCPolBL.getContNo());
					sqlbv4.put("InsuredNo",tInsuredNo);
				tmpSSRS = exeSql.execSQL(sqlbv4);
				if ((tmpSSRS.getMaxRow() >= 1) && (tmpSSRS != null)) {
					for (int k = 1; k <= tmpSSRS.getMaxRow(); k++) {
						tMainRiskcode = tmpSSRS.GetText(k, 1);
						// tMainInsuredNo = tmpSSRS.GetText(k, 2);
						// 校验该险种是否录入过
						if (tMainRiskcode.equals(tRiskCode)
						// && tMainInsuredNo.equals(tInsuredNo)
								&& !tMainRiskcode.trim().equals("")) {
							CError.buildErr(this, "该投保单下该被保险人已经录入过该险种！");
							return false;
						}
					}
				}
			}else{
				//个险险种校验
				if (mLMRiskAppSchema.getSubRiskFlag().equals("M")) {
					// 如果是个人投保单且是主险，则将单证回收的标记为真，用于保存投保单信息后的单证回收模块
					TakeBackCertifyFalg = true;
					String tMainRiskcode = "";
					// String tMainInsuredNo = "";
					
					String tRiskCode = mLMRiskAppSchema.getRiskCode();
					String tInsuredNo = mLCInsuredBL.getInsuredNo();
					tSQL = "select riskcode,insuredno from LCPol where contno='"
						+ "?contno1?" + "' and InsuredNo ='"
						+ "?InsuredNo1?" + "' and polno=mainpolno and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
					SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
					sqlbv5.sql(tSQL);
					sqlbv5.put("contno1", mLCPolBL.getContNo());
					sqlbv5.put("InsuredNo1",tInsuredNo);
					tSQL = "select riskcode,insuredno from LCPol where contno='"
						+ "?contno2?" + "' and InsuredNo ='"
						+ "?InsuredNo2?" + "' and polno=mainpolno ";
					SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
					sqlbv6.sql(tSQL);
					sqlbv6.put("contno2", mLCPolBL.getContNo());
					sqlbv6.put("InsuredNo2",tInsuredNo);
					tmpSSRS = exeSql.execSQL(sqlbv6);
					if ((tmpSSRS.getMaxRow() >= 1) && (tmpSSRS != null)) {
						for (int k = 1; k <= tmpSSRS.getMaxRow(); k++) {
							tMainRiskcode = tmpSSRS.GetText(k, 1);
							// tMainInsuredNo = tmpSSRS.GetText(k, 2);
							// 校验该险种是否录入过
							if (tMainRiskcode.equals(tRiskCode)
									// && tMainInsuredNo.equals(tInsuredNo)
									&& !tMainRiskcode.trim().equals("")) {
								CError.buildErr(this, "该投保单下该被保险人已经录入过该险种！");
								return false;
							}
							// 判断校验多主险，根据保单里原来存在的主险判断当前录入主险是否可以录入
							if (!tMainRiskcode.equals("")
									&& !tMainRiskcode.equals(tRiskCode)) {
								String sSQL = " select * from lmriskrela where  relacode='02'"
									+ " and riskcode='"
									+ "?riskcode?"
									+ "' "
									+ " and relariskcode in('"
									+ "?in?"
									+ "','000000') ";
								SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
								sqlbv7.sql(sSQL);
								sqlbv7.put("riskcode", tMainRiskcode);
								sqlbv7.put("in",tRiskCode);
								SSRS ttSSRS = new SSRS();
								ttSSRS = exeSql.execSQL(sqlbv7);
								if (ttSSRS.getMaxRow() < 1) {
									CError.buildErr(this, "该投保单下已经录入主险["
											+ tMainRiskcode + "]，不能同时录入主险["
											+ tRiskCode + "]！");
									
									return false;
								}
							}
						}
					}
					
					if (mGrpImportFlag) {
						if ((mLCPolBL.getPolNo() != null && !mLCPolBL.getPolNo()
								.equals(mLCPolBL.getMainPolNo()))
								|| (mLCPolBL.getMainPolNo() != null && !mLCPolBL
										.getMainPolNo().equals(""))) {
							// @@错误处理
							CError.buildErr(this, mLCPolBL.getRiskCode()
									+ "主险不能以附加险形式录入！");
							
							return false;
						}
					}
					
					// tSQL = "select * from LCPol" + " where PrtNo = '"
					// + mLCPolBL.getPrtNo() + "' "
					// + " and RiskCode='" + mLMRiskAppSchema.getRiskCode() +
					// "'"
					// + " and InsuredNo='" + mLCInsuredBL.getInsuredNo() +
					// "'";
					// //PQ 印刷号可以有多个主险
					// //一个合同下一个被保险人同一个险种只能录入一个
					// logger.debug(tSQL);
					// LCPolDB tLCPolDB = new LCPolDB();
					// LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSQL);
					// if (tLCPolDB.mErrors.needDealError() == true) {
					// // @@错误处理
					// this.mErrors.copyAllErrors(tLCPolDB.mErrors);
					//
					// CError tError = new CError();
					// tError.moduleName = "ProposalBL";
					// tError.functionName = "checkData";
					// tError.errorMessage = "LCPol表查询失败!";
					// this.mErrors.addOneError(tError);
					//
					// return false;
					// }
					// if (tLCPolSet.size() > 0) {
					// // @@错误处理
					// CError tError = new CError();
					// tError.moduleName = "ProposalBL";
					// tError.functionName = "checkData";
					// tError.errorMessage = "该投保单下该被保险人已经录入过该险种！";
					// this.mErrors.addOneError(tError);
					//
					// return false;
					// }
				}
				// end of if
				else {
					// 如果是附加险，校验
					// (1)如果当前要保存的附加险对应的主险下已经存在相同附加险的纪录，那么提示：已经存在不许录入。
					// (2)如果要保存的附加险的被保人和主险不同，那么不许保存--暂时不做
					// 因为保全要做特殊处理
					if (mSavePolType.equals("0")&& !mGrpImportFlag) {
						if(!mPolType.equals("2")){
							// tongmeng 2008-07-04 modify
							// 支持多主险和多被保人,
							String tInsuredNo = mLCInsuredBL.getInsuredNo();
							logger.debug("@@@Mult tInsuredNo:" + tInsuredNo);
							/** @todo 判断是否添加主险并且附加险和主险是否一致 */
							// ----------------------------------------Beg
							// Add by:chenrong
							// 判断是否添加主险，所录入附加险和主险是否一致
							tSQL = "select 1 from lcpol b "
								+ "where b.contno='"
								+ "?contno?"
								+ "' and b.polno='"
								+ "?polno?"
								+ "' and b.insuredno='"
								+ "?insuredno?" + "' ";
							SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
							sqlbv8.sql(tSQL);
							sqlbv8.put("contno", mLCPolBL.getContNo());
							sqlbv8.put("polno",mLCPolBL.getMainPolNo());
							sqlbv8.put("insuredno",tInsuredNo);
							tmpSSRS = exeSql.execSQL(sqlbv8);
							if ((tmpSSRS.getMaxRow() < 1) || (tmpSSRS == null)) {
								// @@错误处理
								CError.buildErr(this, "没有录入主险！");
								return false;
							}
							
							tSQL = "select b.polno,b.riskcode from lmriskrela a,lcpol b "
								+ "where a.relacode='01' and a.relariskcode='"
								+ "?relariskcode?"
								+ "' and a.riskcode=b.riskcode and "
								+ "b.contno='"
								+ "?contno?"
								+ "' and b.polno='"
								+ "?polno?"
								+ "' and b.insuredno='"
								+ "?insuredno2?" + "' ";
							SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
							sqlbv9.sql(tSQL);
							sqlbv9.put("relariskcode", mLCPolBL.getRiskCode());
							sqlbv9.put("contno",mLCPolBL.getContNo());
							sqlbv9.put("polno",mLCPolBL.getMainPolNo());
							sqlbv9.put("insuredno2",tInsuredNo);
							tmpSSRS = exeSql.execSQL(sqlbv9);
							if ((tmpSSRS.getMaxRow() < 1) || (tmpSSRS == null)) {
								// @@错误处理
								//CError.buildErr(this, "所录入附加险和主险不一致！");
								CError.buildErr(this, "该附加险不能附加于此主险下！");
								return false;
							}
							
							mLCPolBL.setMainPolNo(tmpSSRS.GetText(1, 1));
							// -----------------------------------End
							
							// 判断此附加险是否已经添加
							LCPolDB tempLCpolDB = new LCPolDB();
							LCPolSet tempLCPolSet = new LCPolSet();
							tempLCpolDB.setContNo(mLCPolBL.getContNo());
							tempLCpolDB.setMainPolNo(mLCPolBL.getMainPolNo());
							tempLCpolDB.setRiskCode(mLCPolBL.getRiskCode());
							tempLCpolDB.setInsuredNo(tInsuredNo);
							if (mSavePolType.equals("2")) {
								String sql = "select * from LCPol where 1 = 1 and ContNo = '"
									+ "?ContNo?"
									+ "' and MainPolNo = '"
									+ "?MainPolNo?"
									+ "' and RiskCode = '"
									+ "?RiskCode?"
									+ "' and AppFlag in ('1', '2')";
								SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
								sqlbv10.sql(sql);
								sqlbv10.put("ContNo",mLCPolBL.getContNo());
								sqlbv10.put("MainPolNo",mLCPolBL.getMainPolNo());
								sqlbv10.put("RiskCode", mLCPolBL.getRiskCode());
								tempLCPolSet = tempLCpolDB.executeQuery(sqlbv10);
							} else {
								tempLCPolSet = tempLCpolDB.query();
							}
							
							if ((tempLCPolSet != null) && (tempLCPolSet.size() > 0)) {
								// @@错误处理
								CError.buildErr(this, "该主险下已经存在相同的附加险！");
								return false;
							}
						}
						if(mSavePolType.equals("2")&&!mGrpImportFlag)
						{
							
							//判断此附加险是否已经添加
							LCPolDB tempLCpolDB = new LCPolDB();
							LCPolSet tempLCPolSet = new LCPolSet();
							tempLCpolDB.setContNo(mLCPolBL.getContNo());
							tempLCpolDB.setRiskCode(mLCPolBL.getRiskCode());
							tempLCpolDB.setInsuredNo(mLCInsuredBL.getInsuredNo());
							tempLCPolSet = tempLCpolDB.query();
							if ((tempLCPolSet != null) && (tempLCPolSet.size() > 0)) {
								// @@错误处理
								CError.buildErr(this, "该主险下已经存在相同的附加险！");
								return false;
							}
						}
						if (mGrpImportFlag && !mISPlanRiskFlag) {
							
							/** @todo 判断附加险和主险是否一致 */
							// ----------------------------------------Beg
							// Add by:chenrong
							// 判断录入附加险和主险是否一致
							if (mLCPolBL.getMainPolNo() != null
									&& !"".equals(mLCPolBL.getMainPolNo())) {
								if(!"2".equals(mLCContSchema.getContType())){
									tSQL = "select * from lmriskrela a "
										+ "where a.relacode='01' and a.relariskcode='"
										+ "?code?" + "' and a.riskcode='"
										+ "?code1?" + "'";
									SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
									sqlbv11.sql(tSQL);
									sqlbv11.put("code",mLCPolBL.getRiskCode());
									sqlbv11.put("code1", mainLCPolBL.getRiskCode());
									logger.debug("relariskcode check:" + tSQL);
									tmpSSRS = exeSql.execSQL(sqlbv11);
									if ((tmpSSRS.getMaxRow() < 1) || (tmpSSRS == null)) {
										// @@错误处理
										CError.buildErr(this, mLCPolBL.getRiskCode()
												+ "录入附加险和主险不一致!");
										
										return false;
									}
								}
							} else {
								if(!"2".equals(mLCContSchema.getContType())){
									CError.buildErr(this, mLCPolBL.getRiskCode()
											+ "附加险不能做为主险单保存!");
									return false;
								}
								//团险将附加险放开
								
							}
						}
					}
				}
			}

			// 检验生效日期是否小于开办日期
			if (mLMRiskAppSchema.getStartDate() == null) {
				// @@错误处理
				CError.buildErr(this, "该投保单的险种对应的开办日期在险种描述中不存在！");

				return false;
			} else {
				if (PubFun.calInterval(mLMRiskAppSchema.getStartDate(),
						mLCPolBL.getCValiDate(), "D") < 0) {
					// @@错误处理
					CError.buildErr(this, "该投保单录入的生效日期小于该险种的开办日期，请确认！");
					return false;
				}
			}

			// 检验生效日期是否大于停办日期
			if (mLMRiskAppSchema.getEndDate() != null) {
				if (!mLMRiskAppSchema.getEndDate().trim().equals("")) {
					// add by yaory 如果已经交费就不用校验
					exeSql = new ExeSQL();
					SSRS tSSRS = new SSRS();
					String sql;
					sql = "select * From ljtempfee where otherno='"
							+ "?otherno?" + "' and confdate is null";
					SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
					sqlbv12.sql(sql);
					sqlbv12.put("otherno",mLCPolBL.getContNo());
					logger.debug("交费判断 by yaory===="
							+ mLCPolBL.getContNo());
					tSSRS = exeSql.execSQL(sqlbv12);
					if (tSSRS.MaxRow == 0) {
						if (PubFun.calInterval(mLMRiskAppSchema.getEndDate(),
								mLCPolBL.getCValiDate(), "D") > 0) {
							// @@错误处理
							CError.buildErr(this, "该投保单录入的生效日期超过该险种的停办日期，请确认！");
							return false;
						}
					}
				}
			}

		}

		// //检查被保人录入的数据是否正确
		// if (checkLCInsured() == false)
		// {
		// return false;
		// }
		// // end of if

		if (mOperate.equals("UPDATE||PROPOSAL")) {
			String tProposalNo = mLCPolBL.getProposalNo();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tProposalNo);
			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);

				CError.buildErr(this, "LCPol表查询失败！");
				return false;
			}
			OriginOperator = tLCPolDB.getOperator();
			OriginPrem = tLCPolDB.getPrem();
			OriginStandPrem = tLCPolDB.getStandPrem(); // 保存更新前的标准保费

			mLCPolBL.setMakeDate(tLCPolDB.getMakeDate());
			mLCPolBL.setMakeTime(tLCPolDB.getMakeTime());
			mLCPolBL.setModifyDate(theCurrentDate);
			mLCPolBL.setModifyTime(theCurrentTime);

			// 校验
			if (StrTool.cTrim(tLCPolDB.getAppFlag()).equals("1")) {
				CError.buildErr(this, "此投保单已经签发保单，不能进行此项操作！");

				return false;
			}

			// 如果是主险复核或核保成功，不能做修改
			if (tLCPolDB.getPolNo().equals(tLCPolDB.getMainPolNo())) {
				// if (StrTool.cTrim(tLCPolDB.getApproveFlag()).equals("9"))
				// {
				// CError tError = new CError();
				// tError.moduleName = "ProposalBL";
				// tError.functionName = "checkData";
				// tError.errorMessage = "此投保单已经复核成功，不能进行此项操作!";
				// this.mErrors.addOneError(tError);
				//
				// return false;
				// }
				// if (StrTool.cTrim(tLCPolDB.getUWFlag()).equals("9")) {
				// CError tError = new CError();
				// tError.moduleName = "ProposalBL";
				// tError.functionName = "checkData";
				// tError.errorMessage = "此投保单已经核保成功，不能进行此项操作!";
				// this.mErrors.addOneError(tError);
				//
				// return false;
				// }
			} else {
				// 如果是附加险，不管复核或核保状态。容许修改,将其状态置为初始状态
				mLCPolBL.setApproveFlag("0");
				mLCPolBL.setUWFlag("0");
			}

			if (mChangePlanFlag.equals("1")) {
				// 如果该投保单还有未回复的问题件,则不容许进行修改
				String tStr = "select count(*) from  lcissuepol where ProposalContNo='"
						+ "?ProposalContNo?"
						+ "'  and BackObjType in('1') and ReplyMan is null";
				SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
				sqlbv13.sql(tStr);
				sqlbv13.put("ProposalContNo",tLCPolDB.getProposalNo());
				// LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
				ExeSQL tExeSQL = new ExeSQL();
				int tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv13));
				// logger.debug("======tCount=======" + tCount);
				if (tCount == 0) {
					// logger.debug("======tCount=======" + tCount);
				} else {
					CError.buildErr(this, "此投保单有操作员的问题件未回复，不能进行此项修改操作,请先回复！");

					return false;
				}
			}
		}
		// end of if

		if (mOperate.equals("DELETE||PROPOSAL")) { // sun要求修改校验规则，只限制是否签单！
			String tProposalNo = mLCPolBL.getProposalNo();
			LCPolSchema tLCPolSchema = new LCPolSchema();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tProposalNo);
			if (tLCPolDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);

				CError.buildErr(this, "LCPol表查询失败！");

				return false;
			}

			// 校验
			if (StrTool.cTrim(tLCPolDB.getAppFlag()).equals("1")) {
				CError.buildErr(this, "此投保单已经签发保单保单，不能进行此项操作！");

				return false;
			}
			if (tLCPolDB.getPolNo().equals(tLCPolDB.getMainPolNo())) {
				LCPolDB tLCPolDB1 = new LCPolDB();
				tLCPolDB1.setMainPolNo(tLCPolDB.getMainPolNo());
				int tPolCount = tLCPolDB1.getCount();
				if (tLCPolDB1.mErrors.needDealError()) {
					CError.buildErr(this, "查询投保单失败！");
					return false;
				}
				if (tPolCount > 1) {
					CError.buildErr(this, "该主险下还有附加险，请先删除附加险！");
					return false;
				}

			}
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 * 判断录入的险种是否在销－－－续涛加于2005-09-28加
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		if (!this.mISPlanRiskFlag) {
//			if (!checkRiskStop(mLCPolBL.getContNo(), mLCPolBL.getRiskCode(),
//					mLCContSchema.getManageCom())) {
//				return false;
//			}
//		}
		return true;
	}

	/**
	 * 校验集体投保人信息（暂不用）
	 * 
	 * @return
	 */
	private boolean checkLCAppntGrp() {
		// 集体客户;
		if (mLCGrpAppntBL != null) {
			if (mLCGrpAppntBL.getCustomerNo() != null) { // 如果集体客户号不为空
				if (!mLCGrpAppntBL.getCustomerNo().equals("")) {
					LDGrpDB tLDGrpDB = new LDGrpDB();
					// tLDGrpDB.setGrpNo(mLCGrpAppntBL.getGrpNo());
					if (tLDGrpDB.getInfo() == false) {
						CError.buildErr(this, "数据库查询失败！");

						return false;
					}
					if (mLCGrpAppntBL.getName() != null) {
						String Name = StrTool.GBKToUnicode(tLDGrpDB
								.getGrpName().trim());
						String NewName = StrTool.GBKToUnicode(mLCGrpAppntBL
								.getName().trim());
						if (!Name.equals(NewName)) {
							CError.buildErr(this, "您输入的集体客户号对应在数据库中的客户姓名("
									+ Name + ")与您录入的集体客户姓名(" + NewName
									+ ")不匹配！");

							return false;
						}
					}
				}
			} else { // 如果集体客户号为空。待补充
			}
		}

		return true;
	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－结束－－－－－判断录入的险种是否在销－－－续涛加于2005-09-28加－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */

	/**
	 * 检查被保人录入的数据是否正确(适用于个人投保单和集体下的个单)
	 * 
	 * @return
	 */
	private boolean checkLCInsured() {
		// 如果是无名单或者公共帐户的个人，不校验返回
		if (mLCPolBL.getPolTypeFlag().equals("1")
				|| mLCPolBL.getPolTypeFlag().equals("2")) {
			return true;
		}

		// 被保人
		if (mLCInsuredBLSet != null) {
			/*
			 * Lis5.3 upgrade set for (int i = 1; i <= mLCInsuredBLSet.size();
			 * i++) { LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			 * tLCInsuredSchema = mLCInsuredBLSet.get(i); if (tLCInsuredSchema !=
			 * null) { if (tLCInsuredSchema.getName() != null) { //去空格
			 * tLCInsuredSchema.setName(tLCInsuredSchema.getName() .trim()); }
			 * if (tLCInsuredSchema.getSex() != null) {
			 * tLCInsuredSchema.setSex(tLCInsuredSchema.getSex().trim()); } if
			 * (tLCInsuredSchema.getIDNo() != null) {
			 * tLCInsuredSchema.setIDNo(tLCInsuredSchema.getIDNo() .trim()); }
			 * if (tLCInsuredSchema.getIDType() != null) {
			 * tLCInsuredSchema.setIDType(tLCInsuredSchema.getIDType() .trim()); }
			 * if (tLCInsuredSchema.getBirthday() != null) {
			 * tLCInsuredSchema.setBirthday(tLCInsuredSchema.getBirthday()
			 * .trim()); }
			 * 
			 * //如果没有录入被保人标记是主被保人还是从被保人，则默认是主被保人 if
			 * ((tLCInsuredSchema.getInsuredGrade() == null) ||
			 * tLCInsuredSchema.getInsuredGrade().trim().equals("")) {
			 * tLCInsuredSchema.setInsuredGrade("M"); }
			 * 
			 * //有客户号 if ((tLCInsuredSchema.getCustomerNo() != null) &&
			 * !tLCInsuredSchema.getCustomerNo().trim().equals("")) { LDPersonDB
			 * tLDPersonDB = new LDPersonDB();
			 * tLDPersonDB.setCustomerNo(tLCInsuredSchema .getCustomerNo()); if
			 * (tLDPersonDB.getInfo() == false) { CError tError = new CError();
			 * tError.moduleName = "ProposalBL"; tError.functionName =
			 * "checkPerson"; tError.errorMessage = "数据库查询失败!";
			 * this.mErrors.addOneError(tError);
			 * 
			 * return false; } if (tLCInsuredSchema.getName() != null) { String
			 * Name = StrTool.GBKToUnicode(tLDPersonDB.getName() .trim());
			 * String NewName = StrTool.GBKToUnicode(tLCInsuredSchema.getName()
			 * .trim());
			 * 
			 * if (!Name.equals(NewName)) { CError tError = new CError();
			 * tError.moduleName = "ProposalBL"; tError.functionName =
			 * "checkPerson"; tError.errorMessage = "您输入的被保人客户号对应在数据库中的客户姓名(" +
			 * Name + ")与您录入的客户姓名(" + NewName + ")不匹配！";
			 * this.mErrors.addOneError(tError);
			 * 
			 * return false; } } if (tLCInsuredSchema.getSex() != null) { if
			 * (!tLDPersonDB.getSex().equals(tLCInsuredSchema .getSex())) {
			 * CError tError = new CError(); tError.moduleName = "ProposalBL";
			 * tError.functionName = "checkPerson"; tError.errorMessage =
			 * "您输入的被保人客户号对应在数据库中的客户性别(" + tLDPersonDB.getSex() + ")与您录入的客户性别(" +
			 * tLCInsuredSchema.getSex() + ")不匹配！";
			 * this.mErrors.addOneError(tError);
			 * 
			 * return false; } } } else
			 * //如果没有客户号,查找客户信息表是否有相同名字，性别，出生年月，身份证号的纪录，若有，取客户号 { if
			 * ((tLCInsuredSchema.getName() != null) &&
			 * (tLCInsuredSchema.getIDNo() != null) &&
			 * (tLCInsuredSchema.getSex() != null)) { if
			 * (tLCInsuredSchema.getName().trim().equals("") &&
			 * tLCInsuredSchema.getIDNo().trim().equals("")) { CError tError =
			 * new CError(); tError.moduleName = "ProposalBL";
			 * tError.functionName = "checkPerson"; tError.errorMessage =
			 * "请至少输入的被保人的姓名或ID号！"; this.mErrors.addOneError(tError);
			 * 
			 * return false; }
			 * 
			 * LDPersonDB tLDPersonDB = new LDPersonDB();
			 * tLDPersonDB.setName(tLCInsuredSchema.getName());
			 * tLDPersonDB.setSex(tLCInsuredSchema.getSex()); if
			 * (tLCInsuredSchema.getBirthday() != null) {
			 * tLDPersonDB.setBirthday(tLCInsuredSchema .getBirthday()); }
			 * 
			 * //tLDPersonDB.setIDType("0");
			 * tLDPersonDB.setIDNo(tLCInsuredSchema.getIDNo());
			 * 
			 * LDPersonSet tLDPersonSet = tLDPersonDB.query(); if (tLDPersonSet !=
			 * null) { if (tLDPersonSet.size() > 0) {
			 * tLCInsuredSchema.setCustomerNo(tLDPersonSet.get(1)
			 * .getCustomerNo()); mLCInsuredBLSet.set(i, tLCInsuredSchema); } } } } } }
			 */
		}

		return true;
	}

	/**
	 * 校验个人投保单的投保人
	 * 
	 * @return
	 */
	// private boolean checkLCAppnt()
	// {
	// //如果是无名单或者公共帐户的个人，不校验返回
	// if (mLCPolBL.getPolTypeFlag().equals("1")
	// || mLCPolBL.getPolTypeFlag().equals("2"))
	// {
	// return true;
	// }
	//
	// // 投保人-个人客户--如果是集体下个人，该投保人为空,所以个人时校验个人投保人
	// if (mPolType.equals("1"))
	// {
	// if (mLCAppntBL != null)
	// {
	// if (mLCAppntBL.getAppntName() != null)
	// { //去空格
	// mLCAppntBL.setAppntName(mLCAppntBL.getAppntName().trim());
	// }
	// if (mLCAppntBL.getAppntSex() != null)
	// {
	// mLCAppntBL.setSex(mLCAppntBL.getAppntSex().trim());
	// }
	// if (mLCAppntBL.getAppntIDNo() != null)
	// {
	// mLCAppntBL.setIDNo(mLCAppntBL.getidgetAppntIDNo().trim());
	// }
	// if (mLCAppntBL.getIDType() != null)
	// {
	// mLCAppntBL.setIDType(mLCAppntBL.getIDType().trim());
	// }
	// if (mLCAppntBL.getBirthday() != null)
	// {
	// mLCAppntBL.setBirthday(mLCAppntBL.getBirthday().trim());
	// }
	//
	// if (mLCAppntBL.getCustomerNo() != null)
	// {
	// //如果有客户号
	// if (!mLCAppntBL.getCustomerNo().equals(""))
	// {
	// LDPersonDB tLDPersonDB = new LDPersonDB();
	//
	// tLDPersonDB.setCustomerNo(mLCAppntBL.getCustomerNo());
	// if (tLDPersonDB.getInfo() == false)
	// {
	// CError tError = new CError();
	// tError.moduleName = "ProposalBL";
	// tError.functionName = "checkPerson";
	// tError.errorMessage = "数据库查询失败!";
	// this.mErrors.addOneError(tError);
	//
	// return false;
	// }
	// if (mLCAppntBL.getName() != null)
	// {
	// String Name = StrTool.GBKToUnicode(tLDPersonDB.getName()
	// .trim());
	// String NewName = StrTool.GBKToUnicode(mLCAppntBL.getName()
	// .trim());
	//
	// if (!Name.equals(NewName))
	// {
	// CError tError = new CError();
	// tError.moduleName = "ProposalBL";
	// tError.functionName = "checkPerson";
	// tError.errorMessage = "您输入的投保人客户号对应在数据库中的客户姓名("
	// + Name + ")与您录入的客户姓名("
	// + NewName + ")不匹配！";
	// this.mErrors.addOneError(tError);
	//
	// return false;
	// }
	// }
	// if (mLCAppntBL.getSex() != null)
	// {
	// if (!tLDPersonDB.getSex().equals(mLCAppntBL
	// .getSex()))
	// {
	// CError tError = new CError();
	// tError.moduleName = "ProposalBL";
	// tError.functionName = "checkPerson";
	// tError.errorMessage = "您输入的投保人客户号对应在数据库中的客户性别("
	// + tLDPersonDB.getSex()
	// + ")与您录入的客户性别("
	// + mLCAppntBL.getSex()
	// + ")不匹配！";
	// this.mErrors.addOneError(tError);
	//
	// return false;
	// }
	// }
	// }
	// else //如果没有客户号,查找客户信息表是否有相同名字，性别，出生年月，身份证号的纪录，若有，取客户号
	// {
	// if ((mLCAppntBL.getName() != null)
	// && (mLCAppntBL.getSex() != null)
	// && (mLCAppntBL.getIDNo() != null))
	// {
	// LDPersonDB tLDPersonDB = new LDPersonDB();
	// tLDPersonDB.setName(mLCAppntBL.getName());
	// tLDPersonDB.setSex(mLCAppntBL.getSex());
	// tLDPersonDB.setBirthday(mLCAppntBL.getBirthday());
	//
	// //tLDPersonDB.setIDType("0");
	// tLDPersonDB.setIDNo(mLCAppntBL.getIDNo());
	//
	// LDPersonSet tLDPersonSet = tLDPersonDB.query();
	// if (tLDPersonSet != null)
	// {
	// if (tLDPersonSet.size() > 0)
	// {
	// mLCAppntBL.setCustomerNo(tLDPersonSet.get(1)
	// .getCustomerNo());
	// }
	// }
	// }
	// }
	// }
	// }
	// }
	//
	// return true;
	// }
	/***************************************************************************
	 * 角色表LMRiskRole表校验方法
	 * 
	 */
	private boolean checkLMRiskRole(String aRiskRole) {
		LMRiskRoleDB aLMRiskRoleDB = new LMRiskRoleDB();
		LMRiskRoleSet aLMRiskRoleSet = new LMRiskRoleSet();
		aLMRiskRoleDB.setRiskCode(mLCPolBL.getRiskCode());
		aLMRiskRoleDB.setRiskRole(aRiskRole);
		aLMRiskRoleSet = aLMRiskRoleDB.query();

		boolean appntSex = false; // 投保人性别校验是否通过
		boolean maxAppntAge = false; // 投保人年龄校验是否通过
		boolean minAppntAge = false; // 投保人年龄校验是否通过
		boolean insuSex = false; // 被保人性别校验是否通过
		boolean maxInsuAge = false; // 被保人年龄校验是否通过
		boolean minInsuAge = false; // 被保人年龄校验是否通过
		boolean haveRelateInsu = false;
		boolean insuRelateSex = false; // 连生被保人性别校验是否通过
		boolean maxInsuRelateAge = false; // 连生被保人年龄校验是否通过
		boolean minInsuRelateAge = false; // 连生被保人年龄校验是否通过

		if (aRiskRole.equals("00")) {
			// 查询投保人信息
			LCAppntDB aLCAppntDB = new LCAppntDB();
			aLCAppntDB.setContNo(mLCPolBL.getContNo());
			if(!aLCAppntDB.getInfo())
			{
				return true;
			}
			if (aLMRiskRoleSet.size() > 0) {
				for (int i = 0; i < aLMRiskRoleSet.size(); i++) {
					// 判断性别
					if (aLMRiskRoleSet.get(i + 1).getRiskRoleSex().equals("2")
							|| aLCAppntDB.getAppntSex().equals(
									aLMRiskRoleSet.get(i + 1).getRiskRoleSex())) {
						appntSex = true;
					}
					int maxAppntAgeIntv = PubFun
							.calInterval(aLCAppntDB.getAppntBirthday(),
									mLCPolBL.getCValiDate(), aLMRiskRoleSet
											.get(i + 1).getMAXAppAgeFlag()
											.trim());
					int minAppntAgeIntv = PubFun
							.calInterval(aLCAppntDB.getAppntBirthday(),
									mLCPolBL.getCValiDate(), aLMRiskRoleSet
											.get(i + 1).getMinAppAgeFlag()
											.trim());
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("minAgeIntv==" + minAppntAgeIntv);
					logger.debug("maxAgeIntv==" + maxAppntAgeIntv);
					// 判断年龄
					if (minAppntAgeIntv >= aLMRiskRoleSet.get(i + 1)
							.getMinAppAge()) {
						minAppntAge = true;
					}
					if (maxAppntAgeIntv <= aLMRiskRoleSet.get(i + 1)
							.getMAXAppAge()) {
						maxAppntAge = true;
					}

				}
			} else {
				appntSex = true; // 投保人性别校验是否通过
				maxAppntAge = true; // 投保人年龄校验是否通过
				minAppntAge = true; // 投保人年龄校验是否通过

			}

			// 校验完毕进行判断是否通过角色校验
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("appntSex==" + appntSex);
			logger.debug("maxAppntAge==" + maxAppntAge);
			logger.debug("minAppntAge==" + minAppntAge);

			if (!appntSex) {
				// @@错误处理
				CError.buildErr(this, "投保人性别不符合投保规则要求!");
				return false;
			}
			if (!maxAppntAge) {
				// @@错误处理
				CError.buildErr(this, "投保人年龄不符合最大投保年龄要求!");
				return false;
			}
			if (!minAppntAge) {
				// @@错误处理
				CError.buildErr(this, "投保人年龄不符合最小投保年龄要求!");
				return false;
			}

		}
		if (aRiskRole.equals("01")) {

			if (aLMRiskRoleSet.size() > 0) {
				for (int i = 0; i < aLMRiskRoleSet.size(); i++) {
					LCInsuredDB aLCInsuredDB = new LCInsuredDB();
					aLCInsuredDB.setInsuredNo(mLCPolBL.getInsuredNo());
					aLCInsuredDB.setContNo(mLCPolBL.getContNo());
					aLCInsuredDB.getInfo();
					logger.debug("mLCInsuredBL.getInsuredNo()=="
							+ mLCInsuredBL.getInsuredNo());
					logger.debug("mLCInsuredBL.getSex()=="
							+ mLCInsuredBL.getSex());
					logger.debug("mLCPolBL.getCValiDate()=="
							+ mLCPolBL.getCValiDate());
					logger.debug(aLMRiskRoleSet.get(i + 1)
							.getRiskRoleSex().equals("2"));
					// 判断性别
					if ((aLMRiskRoleSet.get(i + 1).getRiskRoleSex().equals("2"))
							|| (mLCInsuredBL.getSex().equals(aLMRiskRoleSet
									.get(i + 1).getRiskRoleSex()))) {
						insuSex = true;
					}
					logger.debug("===="
							+ aLMRiskRoleSet.get(i + 1).getMAXAppAgeFlag());
					logger.debug("===="
							+ aLMRiskRoleSet.get(i + 1).getMinAppAgeFlag()
									.trim());
					int maxAgeIntv = PubFun
							.calInterval(mLCInsuredBL.getBirthday(), mLCPolBL
									.getCValiDate(), aLMRiskRoleSet.get(i + 1)
									.getMAXAppAgeFlag().trim());
					//个险卡单阳光旅程 年龄已录入为准 不需要计算 特殊处理
					int minAgeIntv =0;
					if("card".equals(this.mark)&&"311603".equals(mLCPolBL.getRiskCode())){
						minAgeIntv=mLCPolBL.getInsuredAppAge();
					}else{
						minAgeIntv=PubFun
						.calInterval(mLCInsuredBL.getBirthday(), mLCPolBL
								.getCValiDate(), aLMRiskRoleSet.get(i + 1)
								.getMinAppAgeFlag().trim());
					}
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					logger.debug("minAgeIntv==" + minAgeIntv);
					logger.debug("maxAgeIntv==" + maxAgeIntv);
					logger.debug("mLCInsuredBL.getBirthday()=="
							+ mLCInsuredBL.getBirthday());

					// 判断年龄
					if (minAgeIntv >= aLMRiskRoleSet.get(i + 1).getMinAppAge()) {
						minInsuAge = true;
					}
					if (maxAgeIntv <= aLMRiskRoleSet.get(i + 1).getMAXAppAge()) {
						maxInsuAge = true;
					}
					// 判断联生被保人
					//当前mLCInsuredRelatedSet中存储的可能不止是连带被保人 因为主被保人第一次也会存在
					if (mLCInsuredRelatedSet.size() != 0) {
						for (int j = 0; j < mLCInsuredRelatedSet.size(); j++) {
							String tSequenceNo = "";
							//modify by liuqh 2009-02-11 因为riskroleno为01和02 RelationToInsured为“00”为 01 否则都设为02
							if(!"".equals(mLCInsuredRelatedSet.get(j+1).getRelationToInsured())){
								if("00".equals(mLCInsuredRelatedSet.get(j+1).getRelationToInsured())){
									//是本人 
									tSequenceNo="01";
								}else{
									tSequenceNo="02";
									haveRelateInsu = true;
								}
							}
							logger.debug("======"
									+ mLCInsuredRelatedSet.get(j + 1)
											.getSequenceNo());
							logger.debug("======"
											+ aLMRiskRoleSet.get(i + 1)
													.getRiskRoleNo());
							logger.debug("======"
									+ mLCInsuredRelatedSet.get(j + 1).getSex());

							if (tSequenceNo
									.equals(
											aLMRiskRoleSet.get(i + 1)
													.getRiskRoleNo().trim())) {
								if (aLMRiskRoleSet.get(i + 1).getRiskRoleSex()
										.equals("2")
										|| mLCInsuredRelatedSet
												.get(j + 1)
												.getSex()
												.equals(
														aLMRiskRoleSet
																.get(i + 1)
																.getRiskRoleSex())) {
									insuRelateSex = true;

								}
								int maxRelateAge = PubFun.calInterval(
										mLCInsuredRelatedSet.get(j + 1)
												.getBirthday(), mLCPolBL
												.getCValiDate(), aLMRiskRoleSet
												.get(i + 1).getMAXAppAgeFlag()
												.trim());
								int minRelateAge = PubFun.calInterval(
										mLCInsuredRelatedSet.get(j + 1)
												.getBirthday(), mLCPolBL
												.getCValiDate(), aLMRiskRoleSet
												.get(i + 1).getMinAppAgeFlag()
												.trim());
								logger.debug("----" + minRelateAge);
								logger.debug("----" + maxRelateAge);

								if (minRelateAge >= aLMRiskRoleSet.get(i + 1)
										.getMinAppAge()) {
									minInsuRelateAge = true;

								}
								if (maxRelateAge <= aLMRiskRoleSet.get(i + 1)
										.getMAXAppAge()) {
									maxInsuRelateAge = true;

								}

							}
						}
					}

				}
			} else {
				insuSex = true; // 被保人性别校验是否通过
				maxInsuAge = true; // 被保人年龄校验是否通过
				minInsuAge = true; // 被保人年龄校验是否通过
				insuRelateSex = true; // 连生被保人性别校验是否通过
				maxInsuRelateAge = true; // 连生被保人年龄校验是否通过
				minInsuRelateAge = true; // 连生被保人年龄校验是否通过

			}
			// 校验完毕进行判断是否通过角色校验
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			logger.debug("insuSex==" + insuSex);
			logger.debug("maxInsuAge==" + maxInsuAge);
			logger.debug("minInsuAge==" + minInsuAge);
			logger.debug("haveRelateInsu==" + haveRelateInsu);
			logger.debug("insuRelateSex==" + insuRelateSex);
			logger.debug("maxInsuRelateAge==" + maxInsuRelateAge);
			logger.debug("minInsuRelateAge==" + minInsuRelateAge);

			if (!insuSex) {
				// @@错误处理
				CError.buildErr(this, "被保人性别不符合投保规则要求!");
				return false;
			}
			if (!maxInsuAge) {
				// @@错误处理
				CError.buildErr(this, "被保人年龄不符合最大投保年龄要求!");
				return false;
			}
			if (!minInsuAge) {
				// @@错误处理
				CError.buildErr(this, "被保人年龄不符合最小投保年龄要求!");
				return false;
			}
			// 如果有连生被保人
			if (haveRelateInsu) {

				if (!insuRelateSex) {
					// @@错误处理
					CError.buildErr(this, "连生被保人性别不符合投保规则要求!");
					return false;
				}
				if (!maxInsuRelateAge) {
					// @@错误处理
					CError.buildErr(this, "连生被保人年龄不符合最大投保年龄要求!");
					return false;
				}
				if (!minInsuRelateAge) {
					// @@错误处理
					CError.buildErr(this, "连生被保人年龄不符合最小投保年龄要求!");
					return false;
				}

			}
		}
		return true;

	}

	/**
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 * 
	 * －－－－－－－－开始－－－－－判断录入的险种是否在销－－－续涛加于2005-09-28加－－－
	 * 
	 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	 */
	/**
	 * TRUE： 险种在销，可以录单 FALSE：险种停售，不允许录单
	 */
	private boolean checkRiskStop(String pContNo, String pRiskCode,
			String pComCode) {

		logger.debug("-----------------校验该险种是否在销----开始-----------------");
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.01
		 * 找出该合同，该险种在暂交费表的缴费日期,如果没有，采用当前系统日期
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */

		String tCurrentDate = PubFun.getCurrentDate();// 当前操作日期
		logger.debug("*****当前操作日期tCurrentDate====" + tCurrentDate);
		String tCVDate = PubFun.getCurrentDate();// 当前操作日期

		if (EdorType != null && !EdorType.trim().equals("")) {
			// 保全新增附加险
			String tEdorValiDate = mLCPolBL.getCValiDate();
			if (tEdorValiDate == null || tEdorValiDate.trim().equals("")) {
				tEdorValiDate = tCurrentDate;
			}
			// tCVDate = PubFun.getLaterDate(tEdorValiDate, tCurrentDate);
			tCVDate = tEdorValiDate;
		} else { // 新契约时
			String tPayDate = "";// 交费日期
			String tPolApplyDate = "";// 投保日期
			ExeSQL ttExeSQL = new ExeSQL();
			String tStrSQL = "";
			// 取交费日期
			tStrSQL = "select min(paydate) from ljtempfee where otherno='"
					+ "?otherno?"
					+ "'"
					+ " and (enteraccdate is  null or (enteraccdate is not null and enteraccdate<>'3000-01-01')) "
					+ " and riskcode='" + "?riskcode?" + "' and confdate is null";
			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
			sqlbv14.sql(tStrSQL);
			sqlbv14.put("otherno",pContNo);
			sqlbv14.put("riskcode", pRiskCode);
			tPayDate = ttExeSQL.getOneValue(sqlbv14);
			logger.debug("*****交费日期tPayDate====" + tPayDate);
			// 取投保日期
			tStrSQL = "select polapplydate from lccont where contno='"
					+ "?contno?" + "'";
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			sqlbv15.sql(tStrSQL);
			sqlbv15.put("contno",pContNo);
			tPolApplyDate = ttExeSQL.getOneValue(sqlbv15);
			logger.debug("*****投保日期tPolApplyDate====" + tPolApplyDate);
			// 取三个日期中最小的日期
			tCVDate = PubFun.getBeforeDate(PubFun.getBeforeDate(tPayDate,
					tPolApplyDate), tCurrentDate);
		}

		try {
			tCVDate = tCVDate.substring(0, 10);
		} catch (Exception ex) {
			tCVDate = tCurrentDate;
		}
		logger.debug("*************tCVDate=====" + tCVDate);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.02 校验是否有该险种的有效信息 select
		 * riskcode,riskname from lmriskapp where subriskflag='M' and riskprop
		 * in ('I','C','D','A') and poltype='P' and riskcode in (select distinct
		 * riskcode from lmriskcomctrl where trim(managecomgrp) in (select
		 * trim(comgrpcode) from ldcomgrptocom where comcode=?comcode?)) order
		 * by riskcode －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tComCode4 = "";
		String tComCode6 = "";
		String tComCode8 = "";
		// String tComCode = "";
		pComCode = StrTool.cTrim(pComCode);

		if (pComCode.length() == 8) {
			tComCode4 = pComCode.substring(0, 4);
			tComCode6 = pComCode.substring(0, 6);
			tComCode8 = pComCode;
		} else if (pComCode.length() == 6) {
			tComCode4 = pComCode.substring(0, 4);
			tComCode6 = pComCode;
			tComCode8 = pComCode;
		} else {
			tComCode4 = pComCode;
			tComCode6 = pComCode;
			tComCode8 = pComCode;
		}

		/**
		 * @todo 通过查询工作流节点判断，是否是在人工核保的承保计划变更中调用的该class，如果是则不进行校验
		 */
		String tSQL = "select '25' from lwmission where missionprop1='"
//				+ pContNo + "' and activityid='0000001100'";
				+ "?contno1?" + "' and activityid in (select activityid from lwactivity  where functionid ='10010028')";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(tSQL);
		sqlbv16.put("contno1",pContNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv16);

		// String tLoadFlag = StrTool.cTrim( (String)
		// mTransferData.getValueByName(
		// "LoadFlag"));
		// String tLoadFlag = tSSRS.GetText(1,1);
		logger.debug("------------------------------------------------------");
		logger.debug("--校验该险种是否在销，载入位置:" + tSSRS.getMaxRow());
		logger.debug("------------------------------------------------------");

		// 25--承保计划变更
		if (tSSRS.getMaxRow() <= 0) {
			if (pComCode.length() == 2) {
				mErrors.addOneError("校验该险种是否在销，登录机构为总公司，总公司不允许录单!!!");
				return false;
			}
		}

		String tSql = "select * from lmriskapp where 1 =1 "
				// +" and riskprop in ('I','C','D','A','Y') "
				// +" and poltype='P' "
				+ " and RiskCode = '"
				+ "?Code?"
				+ "'"
				+ " and riskcode in (select distinct riskcode from lmriskcomctrl where 1=1 "
				+ " and StartDate<= to_date('" + "?date?" + "','yyyy-mm-dd') "
				+ " and (EndDate >= to_date('" + "?date1?"
				+ "','yyyy-mm-dd') or EndDate is null) "
				+ " and trim(managecomgrp) in " + " (select trim(comgrpcode) "
				+ " from ldcomgrptocom where comcode in ('?comcode?'))) order by riskcode ";
		ArrayList<String> strArr=new ArrayList<String>();
		strArr.add(tComCode4);
		strArr.add(tComCode6);
		strArr.add(tComCode8);
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		sqlbv17.sql(tSql);
		sqlbv17.put("Code",pRiskCode);
		sqlbv17.put("date",tCVDate);
		sqlbv17.put("date1",tCVDate);
		sqlbv17.put("comcode",strArr);
		logger.debug("tSql==" + tSql);

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		LMRiskAppSet tLMRiskAppSet = tLMRiskAppDB.executeQuery(sqlbv17);

		logger.debug("------------------------------------------------------");
		logger.debug("--校验该险种是否在销，查询险种描述信息" + tSql);
		logger.debug("------------------------------------------------------");

		if (tLMRiskAppDB.mErrors.needDealError()) {
			mErrors.addOneError("校验该险种是否在销，查询险种描述信息失败!!!"
					+ tLMRiskAppDB.mErrors.getError(0).errorMessage);
			return false;
		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No.03
		 * 没有查到该险种的在销信息，返回为false －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		logger.debug("------------------------------------------------------");
		logger.debug("--校验该险种是否在销，查询险种描述信息的数量：" + tLMRiskAppSet.size());
		logger.debug("------------------------------------------------------");

		if (tLMRiskAppSet.size() == 0) {
			mErrors.addOneError("险种[" + pRiskCode + "]不是本机构[" + pComCode
					+ "]的在销险种!!!");
			return false;
		}

		logger.debug("-----------------校验该险种是否在销----结束-----------------");
		return true;
	}

	/**
	 * 校验连带被保险人年龄
	 * 
	 * @param mLCInsuredBLSet
	 * @return
	 */
	private boolean checkSLCInsured(LCInsuredRelatedSet mLCInsuredRelatedSet) {
		int tInsuredAge = 0;
		LCInsuredRelatedBL tLCInsuredRelatedBL = new LCInsuredRelatedBL();
		for (int i = 1; i <= mLCInsuredRelatedSet.size(); i++) {
			tLCInsuredRelatedBL.setSchema(mLCInsuredRelatedSet.get(i));

			tInsuredAge = PubFun.calInterval(tLCInsuredRelatedBL.getBirthday(),
					mLCPolBL.getCValiDate(), "Y");
			if ((tInsuredAge < mLMRiskAppSchema.getMinInsuredAge())
					|| ((tInsuredAge > mLMRiskAppSchema.getMaxInsuredAge()) && (mLMRiskAppSchema
							.getMaxInsuredAge() > 0))) {
				if(mDealFlag.equals("2")&&("".equals(mLCInsuredRelatedSet.get(i).getRelationToInsured())
						||"00".equals(mLCInsuredRelatedSet.get(i).getRelationToInsured()))){
					//团险，并且磁盘文件录入的不是连带被保人 不做此校验
					CError.buildErr(this, "被保人投保年龄不符合投保要求！");
				}else{
					// @@错误处理
					CError.buildErr(this, "连带被保人投保年龄不符合投保要求！");
				}

				return false;
			}

		}

		return true;
	}

	/**
	 * 校验字段
	 * 
	 * @return
	 */
	private boolean CheckTBField(String operType) {
		// 保单 mLCPolBL mLCGrpPolBL
		// 投保人 mLCAppntBL mLCAppntGrpBL
		// 被保人 mLCInsuredBLSet mLCInsuredBLSetNew
		// 受益人 mLCBnfBLSet mLCBnfBLSetNew
		// 告知信息 mLCCustomerImpartBLSet mLCCustomerImpartBLSetNew
		// 特别约定 mLCSpecBLSet mLCSpecBLSetNew
		// 保费项表 mLCPremBLSet 保存特殊的保费项数据(目前针对磁盘投保，不用计算保费保额类型)
		// 给付项表 mLCGetBLSet
		// 一般的责任信息 mLCDutyBL
		// 责任表 mLCDutyBLSet
		String strMsg = "";
		boolean MsgFlag = false;
		//调用规则引擎-zhangfh
		LCInsuredSchema tLCInsuredSchema = mLCInsuredBLSetNew.get(1);
		LCDutySchema tLCDutySchema = mLCDutyBLSet.get(1);
		String noPlanRiskFlag="0";
		if(mISPlanRiskFlag)
		{
			noPlanRiskFlag="1";
		}
		else
		{
			noPlanRiskFlag="0";
		}
		PrepareBOMTBBL tPrepareBOMTBBL = new PrepareBOMTBBL();
		LCPolSchema tLCPolSchema=mLCPolBL.getSchema();
		TransferData  tTransferData=new TransferData();
		tTransferData.setNameAndValue("ContPlanCode",(String)mLCInsuredBL.getContPlanCode());
		tTransferData.setNameAndValue("ISPlanRiskFlag",noPlanRiskFlag);
		tTransferData.setNameAndValue("GetDutyKind", (String) mTransferData.getValueByName("GetDutyKind"));
		tTransferData.setNameAndValue("Mult", (String)mTransferData.getValueByName("Mult"));
		VData hVData = new VData();
		hVData.add(mTransferData);
		
		this.mBomList = tPrepareBOMTBBL.dealData(tLCPolSchema,tLCDutySchema,mLCInsuredBL.getSchema(),hVData);
		//end
		String RiskCode = mLCPolBL.getRiskCode();
		// logger.debug("测试 by yaory riskcode="+RiskCode);
		// logger.debug("测试 by yaory"+mLCPolBL.getPrem());
		try {
			VData tVData = new VData();
			CheckFieldCom tCheckFieldCom = new CheckFieldCom();

			// 计算要素
			FieldCarrier tFieldCarrier = new FieldCarrier();
			tFieldCarrier.setPrem(mLCPolBL.getPrem()); // 保费
			tFieldCarrier.setInsuredNo(mLCPolBL.getInsuredNo()); // add by
			// yaory
			logger.debug("测试 fieldcarrier====" + mLCPolBL.getInsuredNo());
			logger.debug("测试 fieldcarrier====mLCPolBL.getOperator():"
					+ mLCPolBL.getOperator());
			logger.debug("测试 fieldcarrier====mLCPolBL.getPolNo():"
					+ mLCPolBL.getPolNo());
			tFieldCarrier.setAppAge(mLCPolBL.getInsuredAppAge()); // 被保人年龄
			tFieldCarrier.setInsuredName(mLCPolBL.getInsuredName()); // 被保人姓名
			tFieldCarrier.setSex(mLCPolBL.getInsuredSex()); // 被保人性别
			tFieldCarrier.setMult(mLCPolBL.getMult()); // 投保份数
			tFieldCarrier.setPolNo(mLCPolBL.getPolNo()); // 投保单号码
			tFieldCarrier.setMainPolNo(mLCPolBL.getMainPolNo()); // 主险号码
			tFieldCarrier.setRiskCode(mLCPolBL.getRiskCode()); // 险种编码
			tFieldCarrier.setCValiDate(mLCPolBL.getCValiDate()); // 生效日期
			tFieldCarrier.setAmnt(mLCPolBL.getAmnt()); // 保额
			tFieldCarrier.setInsuredBirthday(mLCPolBL.getInsuredBirthday()); // 被保人出生日期
			tFieldCarrier.setInsuYear(mLCPolBL.getInsuYear()); // 保险期间
			tFieldCarrier.setInsuYearFlag(mLCPolBL.getInsuYearFlag()); // 保险期间单位
			tFieldCarrier.setPayEndYear(mLCPolBL.getPayEndYear()); // 交费期间
			tFieldCarrier.setPayEndYearFlag(mLCPolBL.getPayEndYearFlag()); // 交费期间单位
			tFieldCarrier.setPayIntv(mLCPolBL.getPayIntv()); // 交费方式
			tFieldCarrier.setPayYears(mLCPolBL.getPayYears()); // 交费年期
			tFieldCarrier.setOccupationType(mLCPolBL.getOccupationType()); // 被保人职业类别
			tFieldCarrier.setGetYear(mLCPolBL.getGetYear()); // 领取年龄
			tFieldCarrier.setGetIntv(tLCDutySchema.getGetIntv());
			tFieldCarrier.setGrpPolNo(mLCPolBL.getGrpPolNo());
			tFieldCarrier.setContNo(mLCPolBL.getContNo());
			tFieldCarrier.setEndDate(mLCPolBL.getEndDate());
			tFieldCarrier.setEdorType(EdorType);
			
			tFieldCarrier.setPeakLine(tLCDutySchema.getPeakLine());
			// logger.debug("保单类型为："+mLCPolBL.getPolTypeFlag());
			tFieldCarrier.setPolTypeFlag(mLCPolBL.getPolTypeFlag());
			tFieldCarrier.setGetLimit(tLCDutySchema.getGetLimit());
			
			tFieldCarrier.setLiveGetMode(mLCPolBL.getLiveGetMode());
			tFieldCarrier.setGetDutyKind((String) mTransferData
					.getValueByName("GetDutyKind")); // 用于115被保险投保年龄和领取年龄的校验
			tFieldCarrier.setBonusGetMode(mLCPolBL.getBonusGetMode());// zhangzheng
			tFieldCarrier.setPolapplydate(mLCPolBL.getPolApplyDate());
			// if (mark != null && mark.equals("card")) {
			tFieldCarrier.setOccupationCode(mLCInsuredBL.getOccupationCode());
			// 传入全局变量Operator
			logger.debug("mGlobalInput.Operator=" + mGlobalInput.Operator);
			tFieldCarrier.setOperator(mGlobalInput.Operator);
			tFieldCarrier.setSellType(mLCContSchema.getSellType());
			
			tFieldCarrier.setIDNo(mLCInsuredBL.getIDNo());
			tFieldCarrier.setAgentCode(mLCPolBL.getAgentCode());
			//
			logger.debug("ProposalBL中销售方式：" + mLCContSchema.getSellType());
			if (mISPlanRiskFlag) {
				// 对于产品组合要将份数设为传入的产品组合份数
				// 产品组合如果某险种有多个责任那么lcpol中的份数是多个责任的份数的和
				// 所以要传入产品组合的份数
				String tMult = "";
				tMult = (String) mTransferData.getValueByName("Mult");
				if (tMult != null && !"".equals(tMult)) {
					tFieldCarrier.setMult(Double.parseDouble(tMult));
				}
				tFieldCarrier.setContPlanCode(mLCInsuredBL.getContPlanCode());
			}
			// }

			LCPremSchema tLCPremSchema = null;
			// logger.debug("测试 by yaory mlcpremblset="+mLCPremBLSet.size(
			// ));
			for (int i = 1; i <= mLCPremBLSet.size(); i++) {

				tLCPremSchema = mLCPremBLSet.get(i);

			}
			/*
			 * Lis5.3 upgrade get
			 * logger.debug("进入算法表的管理费比例为"+tLCPremSchema
			 * .getManageFeeRate());
			 * tFieldCarrier.setManageFeeRate(tLCPremSchema.getManageFeeRate());
			 */
			// logger.debug("进入算法表的责任编码为"+tLCPremSchema.getDutyCode());
			// tLCPremSchema可能为空，不能直接使用的，，edit by yaory,,hehe,,,,,
			try {
				tFieldCarrier.setDutyCode(tLCPremSchema.getDutyCode());
			} catch (Exception ex) {
				logger.debug("lmcheckfield中没有责任代码 ");
			}
			
			//适合单责任险种-因现在只有乘意险及贷款无忧需此要素，这两个险种都是单责任的，所以加入
			tFieldCarrier.setFloatRate(mLCPolBL.getFloatRate());
		
			// edit by yaory logger.debug("没有执行以下");
			if (mLCPolBL.getStandbyFlag1() != null) {
				tFieldCarrier.setStandbyFlag1(mLCPolBL.getStandbyFlag1());
			}
			if (mLCPolBL.getStandbyFlag2() != null) {
				tFieldCarrier.setStandbyFlag2(mLCPolBL.getStandbyFlag2());
			}
			if (mLCPolBL.getStandbyFlag3() != null) {
				tFieldCarrier.setStandbyFlag3(mLCPolBL.getStandbyFlag3());
			}

			// 为卡单地域控制加入计算要素
			if (mLCPolBL.getManageCom() != null) {
				tFieldCarrier.setManageCom(mLCPolBL.getManageCom());
			}
			tFieldCarrier.setSaleChnl(mLCPolBL.getSaleChnl());
			/*
			 * Lis5.3 upgrade get if (mLCPolBL.getStandbyFlag4() != null) {
			 * tFieldCarrier.setStandbyFlag4(mLCPolBL.getStandbyFlag4()); } if
			 * (tLCInsuredSchema != null) {
			 * tFieldCarrier.setIDNo(tLCInsuredSchema.getIDNo()); //被保人证件号码（身份证）
			 * tFieldCarrier.setWorkType(tLCInsuredSchema.getWorkType()); //职业工种
			 * tFieldCarrier.setGrpName(tLCInsuredSchema.getGrpName()); //单位名称 }
			 */
			// logger.debug("Prepare Data");
			tVData.add(tFieldCarrier);

			LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
			tLMCheckFieldSchema.setRiskCode(RiskCode);
			if (mISPlanRiskFlag) {
				tLMCheckFieldSchema.setFieldName("ZH" + operType); // 投保
			} else {
				tLMCheckFieldSchema.setFieldName("TB" + operType); // 投保
			}			
		
			tVData.add(tLMCheckFieldSchema);

			// 扩展，将一些不能直接从lcpol取到的参数要素传入，
			// 这些要素可以在外部放入mTransferData里
			// 如：多个被保人时将所有被保人信息传入
			tVData.add(mTransferData);
			tVData.add(mBomList);
			if (tCheckFieldCom.CheckField(tVData) == false) {
				logger.debug("hehehehehe");
				this.mErrors.copyAllErrors(tCheckFieldCom.mErrors);

				return false;
			} else {
				logger.debug("Check Data");

				LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom
						.GetCheckFieldSet();
				logger.debug("@@@@@@@@@@@@@mLMCheckFieldSet.size():"
						+ mLMCheckFieldSet.size());
				for (int n = 1; n <= mLMCheckFieldSet.size(); n++) {
					LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);
					if ((tField.getReturnValiFlag() != null)
							&& tField.getReturnValiFlag().equals("N")) {
						if ((tField.getMsgFlag() != null)
								&& tField.getMsgFlag().equals("Y")) {
							MsgFlag = true;
							strMsg = strMsg + tField.getMsg() + " ; ";

							break;
						}
					}
				}
				if (MsgFlag == true) {
					// @@错误处理
					CError.buildErr(this, "数据有误：" + strMsg);

					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "发生错误，请检验CheckField模块:" + ex);

			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 处理个人新增业务
		if (this.mOperate.equals("INSERT||PROPOSAL")
				|| this.mOperate.equals("UPDATE||PROPOSAL")) {
			if (dealDataPerson() == false) {
				return false;
			}
		}
		if (this.mOperate.equals("DELETE||PROPOSAL")) {
			if (dealDataDel() == false) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行个人保单删除的逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealDataDel() {
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mLCPolBL.getProposalNo());
		if (tLCPolDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCPolDB.mErrors);
			return false;
		}
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(tLCPolDB.getContNo());
		if (tLCContDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCContDB.mErrors);
			return false;
		}

		// 由于精度问题注释该段代码 modify by sunxy 2005-01-16
		// tLCContDB.setPrem(tLCContDB.getPrem()
		// - tLCPolDB.getPrem());
		// tLCContDB.setAmnt(tLCContDB.getAmnt()
		// - tLCPolDB.getAmnt());

		// 由于精度问题添加该段代码 modify by sunxy 2005-01-16
		double ExchPrem = 0.00;
		double ExchAmnt = 0.00;
		LDExch tLDExch = new LDExch();
		ExchPrem = tLDExch.toBaseCur(tLCPolDB.getCurrency(), locCurrency, theCurrentDate, tLCPolDB.getPrem());		
		ExchAmnt = tLDExch.toBaseCur(tLCPolDB.getCurrency(), locCurrency, theCurrentDate, tLCPolDB.getAmnt());
//		String strCalContPrem = mDecimalFormat.format(tLCContDB.getPrem()
//				- tLCPolDB.getPrem()); // 转换计算后的保额
//		String strCalContAmnt = mDecimalFormat.format(tLCContDB.getAmnt()
//				- tLCPolDB.getAmnt()); // 转换计算后的保额
		String strCalContPrem = mDecimalFormat.format(tLCContDB.getPrem() - ExchPrem); // 转换计算后的保额
		String strCalContAmnt = mDecimalFormat.format(tLCContDB.getAmnt() - ExchAmnt); // 转换计算后的保额
		double calPolPrem = Double.parseDouble(strCalContPrem);
		double calPolAmnt = Double.parseDouble(strCalContAmnt);
		tLCContDB.setPrem(calPolPrem);
		tLCContDB.setAmnt(calPolAmnt);
		
		
		mLCContSchema.setSchema(tLCContDB.getSchema());
		if (mDealFlag.equals("2")) {
			mPolType = "2";
		}

		if (mPolType.equals("2")) { // 集体下的个人
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpContNo(tLCPolDB.getGrpContNo());
			tLCGrpPolDB.setRiskCode(tLCPolDB.getRiskCode());

			LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
			if (tLCGrpPolSet.size() == 0) {

				this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);

				return false;

			} else {
				mLCGrpPolSchema = tLCGrpPolSet.get(1);
			}
			mLCGrpPolSchema.setModifyDate(theCurrentDate);
			mLCGrpPolSchema.setModifyTime(theCurrentTime);

			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tLCPolDB.getGrpContNo());
			if (tLCGrpContDB.getInfo() == false) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCPolDB.mErrors);

				return false;
			}
			tLCGrpContDB.setModifyDate(theCurrentDate);
			tLCGrpContDB.setModifyTime(theCurrentTime);

			int NONAMENUM = 1;

			if ((tLCPolDB.getPolTypeFlag() == null)
					|| tLCPolDB.getPolTypeFlag().equals("")) {
				tLCPolDB.setPolTypeFlag("0");
			}

			// 如果不是集体下的无名单加人--类型是4
			if (!mSavePolType.equals("4")) {
				// 保单类型为无名单（投保人数>0）的或者为公共帐户（投保人数为0）的
				if (tLCPolDB.getPolTypeFlag().equals("1")
						|| tLCPolDB.getPolTypeFlag().equals("2")) {
					mLCGrpPolSchema.setPeoples2(mLCGrpPolSchema.getPeoples2()
							- tLCPolDB.getInsuredPeoples());
					// tLCGrpContDB.setPeoples2(tLCGrpContDB.getPeoples2()
					// - tLCPolDB.getInsuredPeoples());
					NONAMENUM = tLCPolDB.getInsuredPeoples();
				} else {
					mLCGrpPolSchema
							.setPeoples2(mLCGrpPolSchema.getPeoples2() - 1);
					// tLCGrpContDB.setPeoples2(tLCGrpContDB.getPeoples2()
					// - 1);
				}

				// 由于精度问题添加该段代码 modify by sunxy 2005-01-16
				String strCalGrpPolPrem = mDecimalFormat.format(mLCGrpPolSchema
						.getPrem()
						- tLCPolDB.getPrem()); // 转换计算后的保费(规定的精度)
				String strCalGrpPolAmnt = mDecimalFormat.format(mLCGrpPolSchema
						.getAmnt()
						- tLCPolDB.getAmnt()); // 转换计算后的保额

				String strCalGrpContPrem = mDecimalFormat.format(tLCGrpContDB
						.getPrem()
						- tLCPolDB.getPrem()); // 转换计算后的保费(规定的精度)
				String strCalGrpContAmnt = mDecimalFormat.format(tLCGrpContDB
						.getAmnt()
						- tLCPolDB.getAmnt()); // 转换计算后的保额

				double calGrpPolPrem = Double.parseDouble(strCalGrpPolPrem);
				double calGrpPolAmnt = Double.parseDouble(strCalGrpPolAmnt);

				double calGrpContPrem = Double.parseDouble(strCalGrpContPrem);
				double calGrpContAmnt = Double.parseDouble(strCalGrpContAmnt);

				mLCGrpPolSchema.setPrem(calGrpPolPrem);
				mLCGrpPolSchema.setAmnt(calGrpPolAmnt);
				tLCGrpContDB.setPrem(calGrpContPrem);
				tLCGrpContDB.setAmnt(calGrpContAmnt);

			}
			mLCGrpContSchema = tLCGrpContDB.getSchema();
		}

		return true;
	}

	/**
	 * 银行出单特殊处理
	 * 
	 * @return
	 */
	private boolean dealDataForBank() {
		/*
		 * Lis5.3 upgrade get if ((mLCPolBL.getStandbyFlag3() != null) &&
		 * (mLCPolBL.getStandbyFlag4() != null)) { if
		 * (mLCPolBL.getStandbyFlag3().equals("Y")) { if
		 * (mLCPolBL.getStandbyFlag4().trim().length() != 20) { CError tError =
		 * new CError(); tError.moduleName = "ProposalBL"; tError.functionName =
		 * "dealDataForBank"; tError.errorMessage =
		 * "银行出单标记为Y时必须录入长度为20位的银行出单保单号"; this.mErrors.addOneError(tError);
		 * 
		 * return false; } else { LCPolDB tLCPolDB = new LCPolDB();
		 * tLCPolDB.setStandbyFlag4(mLCPolBL.getStandbyFlag4());
		 * 
		 * LCPolSet tLCPolSet = new LCPolSet(); tLCPolSet = tLCPolDB.query(); if
		 * (tLCPolSet.size() > 0) { CError tError = new CError();
		 * tError.moduleName = "ProposalBL"; tError.functionName =
		 * "dealDataForBank"; tError.errorMessage = "录入的银行出单保单号已经存在,不能保存!";
		 * this.mErrors.addOneError(tError);
		 * 
		 * return false; } } }
		 * 
		 * if (mLCPolBL.getStandbyFlag3().equals("N") &&
		 * (mLCPolBL.getStandbyFlag4().trim().length() > 0)) { CError tError =
		 * new CError(); tError.moduleName = "ProposalBL"; tError.functionName =
		 * "dealDataForBank"; tError.errorMessage = "银行出单标记为N时不能录入银行出单的保单号!";
		 * this.mErrors.addOneError(tError);
		 * 
		 * return false; }
		 * 
		 * //如果银行出单标记为真且银行出单保单号录入，在不打印保单 if
		 * (mLCPolBL.getStandbyFlag3().equals("Y") &&
		 * (mLCPolBL.getStandbyFlag4().trim().length() > 0)) {
		 * mLCPolBL.setPrintCount(1); mLCPolBL.setApproveFlag("9");
		 * mLCPolBL.setApproveCode("000");
		 * mLCPolBL.setApproveDate(theCurrentDate); } }
		 */
		return true;
	}

	/**
	 * 根据前面的输入数据，进行个人保单录入的逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealDataPerson() {
		int i;
		int iMax;
		String tStr = "";
		String tNo = "";
		String tLimit = "";
		Reflections tReflections = new Reflections();

		// 产生投保单号码
		tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
		if (mOperate.equals("INSERT||PROPOSAL")) {			
			tNo = PubFun1.CreateMaxNo("ProposalNo", tLimit);
			mLCPolBL.setProposalNo(tNo);
		} else if (mOperate.equals("UPDATE||PROPOSAL")) {
			// 投保申请日期处理:1 没有该投保单，属于新增，取录入值；
			// 2 数据库中有该投保单，若数据库的指定生效日期值为N，取录入值；若数据库的指定生效日期值为Y，保持申请日期。
			LCPolDB xLCPolDB = new LCPolDB();
			xLCPolDB.setPolNo(mLCPolBL.getPolNo());
			if (xLCPolDB.getInfo()) {
				if ((xLCPolDB.getSpecifyValiDate() != null)
						&& xLCPolDB.getSpecifyValiDate().equals("Y")) {
					mLCPolBL.setPolApplyDate(xLCPolDB.getPolApplyDate());
				}
			}
		}
		mLCPolBL.setPolNo(mLCPolBL.getProposalNo());

		// 如果是否指定生效日期在界面上没有传入，那么默认是否
		if ((mLCPolBL.getSpecifyValiDate() == null)
				|| mLCPolBL.getSpecifyValiDate().equals("")) {
			mLCPolBL.setSpecifyValiDate("N");
		}

		// 主险保单号为空,默认该险种为主险,并用投保单号作为主险保单号
		if ((mLCPolBL.getMainPolNo() == null)
				|| mLCPolBL.getMainPolNo().trim().equals("")) {
			mLCPolBL.setMainPolNo(mLCPolBL.getProposalNo());
			mainLCPolBL.setSchema(mLCPolBL);
		}

		// 查询险种描述表
		// 查询险种承保描述表
		// 投保人
		String tAppntCustomerNo = "";
		String tAppntName = "";
		if (mAppntType.equals("1")) { // 个人投保人

			if (StrTool.cTrim(mLCAppntBL.getAppntNo()).equals("")) {
				tLimit = "SN";

				String tAppntNo = PubFun1.CreateMaxNo("CustomerNo", tLimit);
				mLCAppntBL.setAppntNo(tAppntNo);

				// 客户表同步数据
				LDPersonSchema tLDPersonSchema = new LDPersonSchema();
				tReflections.transFields(tLDPersonSchema, mLCAppntBL
						.getSchema());
				tLDPersonSchema.setOperator(mGlobalInput.Operator);
				tLDPersonSchema.setMakeDate(theCurrentDate);
				tLDPersonSchema.setMakeTime(theCurrentTime);
				tLDPersonSchema.setModifyDate(theCurrentDate);
				tLDPersonSchema.setModifyTime(theCurrentTime);
				mLDPersonSet.add(tLDPersonSchema);
			} else {
				LCAppntDB mLCAppntDB = new LCAppntDB();
				mLCAppntDB.setSchema(mLCAppntBL);
				mLCAppntDB.getInfo();
				mLCAppntBL.setSchema(mLCAppntDB);
			}
			/*******************************************************************
			 * 加入lmriskrole表的校验 by hl
			 * 
			 * **************************************
			 */
			String KDCheckFlag=(String)mTransferData.getValueByName("KDCheckFlag");
        	logger.debug("自助卡单是否校验标志:"+KDCheckFlag);
        	
        	//当KDCheckFlag不为null时,表明是走自助卡单核销程序进行核销,不进行投保规则校验,否则表明是走核心柜面出单,必须要走核保规则校验
			if (KDCheckFlag == null && !checkLMRiskRole("00")) {
				return false;
			} // 校验投保人

			/*******************************************************************
			 * 结束
			 * 
			 * *************************
			 */
			LMRiskRoleDB aLMRiskRoleDB = new LMRiskRoleDB();
			LMRiskRoleSet aLMRiskRoleSet = new LMRiskRoleSet();
			aLMRiskRoleDB.setRiskCode(mLCPolBL.getRiskCode());
			aLMRiskRoleDB.setRiskRole("00");
			aLMRiskRoleSet = aLMRiskRoleDB.query();
			// 校验投保人投保年龄
			int tAppntAge = 0;
			tAppntAge = PubFun.calInterval(mLCAppntBL.getAppntBirthday(),
					mLCPolBL.getCValiDate(), "Y");
			logger.debug("投保年龄:" + tAppntAge);
			logger.debug("投保年龄描述:" + mLMRiskAppSchema.getMinAppntAge());

			// 团体卡单无名单不进行投保人校验
			//tongmeng 2009-04-20 
			//在checkLMRiskRole中校验
			/*
			if (aLMRiskRoleSet.size() == 0
					&& !mLCContSchema.getPolType().equals("1")) {
				if (tAppntAge < mLMRiskAppSchema.getMinAppntAge()) {
					// @@错误处理
					CError.buildErr(this, "投保人投保年龄不符合最小投保年龄要求!");
					return false;
				}
				if ((tAppntAge > mLMRiskAppSchema.getMaxAppntAge())
						&& (mLMRiskAppSchema.getMaxAppntAge() > 0)) {
					// @@错误处理
					CError.buildErr(this, "投保人投保年龄不符合最大投保年龄要求!");
					return false;

				}
			}*/
			tAppntCustomerNo = mLCAppntBL.getAppntNo();
			tAppntName = mLCAppntBL.getAppntName();
		}
		if (mAppntType.equals("2")) { // 集体投保人
			// mLCGrpAppntBL.setPolNo(mLCPolBL.getPolNo());
			// mLCAppntGrpBL.setAppntGrade("M");
			// mLCAppntGrpBL.setOperator(mGlobalInput.Operator);
			// mLCAppntGrpBL.setMakeDate(theCurrentDate);
			// mLCAppntGrpBL.setMakeTime(theCurrentTime);
			// mLCAppntGrpBL.setModifyDate(theCurrentDate);
			// mLCAppntGrpBL.setModifyTime(theCurrentTime);

			// 如果从外部接收的数据为空，那么从数据库查询
			// if (mLDGrpSchema == null)
			// {
			// LDGrpDB tLDGrpDB = new LDGrpDB();
			// //PQ 需要恢复
			// tLDGrpDB.setCustomerNo(mLCGrpAppntBL.getCustomerNo());
			// if (tLDGrpDB.getInfo() == false)
			// {
			// // @@错误处理
			// this.mErrors.copyAllErrors(tLDGrpDB.mErrors);
			//
			// CError tError = new CError();
			// tError.moduleName = "ProposalBL";
			// tError.functionName = "dealDataPerson";
			// tError.errorMessage = "LDGrp表查询失败!";
			// this.mErrors.addOneError(tError);
			//
			// return false;
			// }
			// mLDGrpSchema = tLDGrpDB.getSchema();
			// }

			tAppntCustomerNo = mLCContSchema.getAppntNo();
			tAppntName = mLCContSchema.getAppntName();

			// tAppntCustomerNo = mLCGrpAppntBL.getCustomerNo();
			// tAppntName = mLCGrpAppntBL.getName();
		}

		// 添加投保人名称和客户号
		logger.debug("第3步");

		mLCPolBL.setAppntNo(tAppntCustomerNo);
		mLCPolBL.setAppntName(tAppntName);

		// 被保人-如果有多条，即有连带被保人，则将主被保人放在第一位
		// LCInsuredBL tLCInsuredBL1 = new LCInsuredBL();
		// if (mLCInsuredBLSet.size() > 1)
		// {
		// LCInsuredBL tempLCInsuredBL = new LCInsuredBL();
		// for (int n = 1; n <= mLCInsuredBLSet.size(); n++)
		// {
		// /*Lis5.3 upgrade get
		// String InsuredGrade = mLCInsuredBLSet.get(n).getInsuredGrade();
		// */
		// //PQ 被保险人处理，连带和主被保险人要分开
		// String InsuredGrade ="";
		// if ((InsuredGrade != null) && InsuredGrade.equals("M"))
		// {
		// if (n == 1)
		// {
		// break;
		// }
		//
		// //得到排序第n位的数据和第1位互换
		// tempLCInsuredBL.setSchema(mLCInsuredBLSet.get(n).getSchema());
		// mLCInsuredBLSet.set(n, mLCInsuredBLSet.get(1));
		// mLCInsuredBLSet.set(1, tempLCInsuredBL);
		//
		// break;
		// }
		// }
		// }
		// tLCInsuredBL1.setSchema((LCInsuredSchema) mLCInsuredBLSet.get(1));

		// 判断投保人是否和主被保人相同，集体投保人不判断
		// 0 表示投保人和主被保人不相同 1 表示投保人和主被保人相同

		// 被保险人处理
		if (StrTool.cTrim(mLCInsuredBL.getInsuredNo()).equals("")) {
			tLimit = "SN";

			String tInsuredNo = PubFun1.CreateMaxNo("CustomerNo", tLimit);
			mLCInsuredBL.setInsuredNo(tInsuredNo);

			// 如果投保单类型是无名单或者公共帐户，不添加客户信息，补充投保人信息
			if (mLCPolBL.getPolTypeFlag().equals("1")
					|| mLCPolBL.getPolTypeFlag().equals("2")) {
				// 如果是附加险，查出主险的被保人号码赋给附加险
				if (mLMRiskAppSchema.getSubRiskFlag().equals("S")) {
					LCPolDB tempLCpolDB = new LCPolDB();
					tempLCpolDB.setPolNo(mLCPolBL.getMainPolNo());
					if (tempLCpolDB.getInfo() == false) {
						// @@错误处理
						CError.buildErr(this, "未查询到该投保单的主险！");
						return false;
					}

					// 如果主险也是无名单（因该是），那么取主险的被保人信息
					if (tempLCpolDB.getPolTypeFlag().equals("1")
							|| tempLCpolDB.getPolTypeFlag().equals("2")) {
						mLCInsuredBL.setInsuredNo(tempLCpolDB.getInsuredNo());
						mLCInsuredBL.setName(tempLCpolDB.getInsuredName());
						mLCInsuredBL.setBirthday(tempLCpolDB
								.getInsuredBirthday());
						mLCInsuredBL.setSex(tempLCpolDB.getInsuredSex());
					}
				}
				if (mLCPolBL.getPolTypeFlag().equals("2")) {
					mLCInsuredBL.setName("公共账户");
				} else {
					mLCInsuredBL.setName("无名单");
				}

				if (mLCInsuredBL.getSex() == null) {
					mLCInsuredBL.setSex("0");
				}
				if (mLCInsuredBL.getBirthday() == null) { // 如果是无名单，没有录入年龄，默认是30
					int tInsuredAge = 0;
					if (mLCPolBL.getInsuredAppAge() <= 0) { // 如果投保年龄没有录入
						tInsuredAge = 30;
						mLCPolBL.setInsuredAppAge(tInsuredAge);
					} else {
						tInsuredAge = mLCPolBL.getInsuredAppAge(); // 前台录入年龄
					}

					// 年龄所在年=系统年-年龄
					String year = Integer.toString(Integer.parseInt(StrTool
							.getYear())
							- tInsuredAge);
					String brithday = StrTool.getDate(year, StrTool.getMonth(),
							StrTool.getDay());
					brithday = StrTool.replace(brithday, "/", "-");
					mLCInsuredBL.setBirthday(brithday);
				}
			} else {
				// 客户表同步数据
				LDPersonSchema tLDPersonSchema = new LDPersonSchema();
				tReflections.transFields(tLDPersonSchema, mLCInsuredBL
						.getSchema());
				tLDPersonSchema.setOperator(mGlobalInput.Operator);
				tLDPersonSchema.setMakeDate(theCurrentDate);
				tLDPersonSchema.setMakeTime(theCurrentTime);
				tLDPersonSchema.setModifyDate(theCurrentDate);
				tLDPersonSchema.setModifyTime(theCurrentTime);
				mLDPersonSet.add(tLDPersonSchema);
			}

			mLCInsuredBLSet.set(1, mLCInsuredBL);
		} else {
			if (mark != null && mark.equals("card")) {
				// 卡单不用查询
			} else {
				//???/
				LCInsuredDB mLCInsuredDB = new LCInsuredDB();
				mLCInsuredDB.setSchema(mLCInsuredBL);
				mLCInsuredDB.getInfo();
				//;
				//tongmeng 2009-03-30
				//如果是团险,不能直接用被保人数据覆盖!
				if(mDealFlag.equals("2"))
				{
					
				}
				else
				{
					mLCInsuredBL.setSchema(mLCInsuredDB);
				}
			}
		}

		// mLCInsuredBL.setPolNo(mLCPolBL.getPolNo());
		logger.debug("第4步");
		mLCInsuredBL.setDefaultFields();
		// mLCInsuredBLSetNew.add(mLCInsuredBL);

		logger.debug("第5步");
		// PQ 连带或连生被保险人处理
		// 校验连带被保人投保年龄
		if (mLCInsuredRelatedSet.size() >= 1) {
			if (checkSLCInsured(mLCInsuredRelatedSet) == false) {
				return false;
			}

			for (i = 1; i <= mLCInsuredRelatedSet.size(); i++) {
				LCInsuredRelatedSchema tLCInsuredRelatedSchema = new LCInsuredRelatedSchema();
				tLCInsuredRelatedSchema = mLCInsuredRelatedSet.get(i);

				tLCInsuredRelatedSchema.setPolNo(mLCPolBL.getPolNo());
				// tLCInsuredRelatedBL.setPrtNo(mLCPolBL.getPrtNo());
//				tLCInsuredRelatedSchema.setMainCustomerNo(mLCInsuredBL
//						.getInsuredNo());
				tLCInsuredRelatedSchema.setOperator(mGlobalInput.Operator);
				tLCInsuredRelatedSchema.setMakeDate(theCurrentDate);
				tLCInsuredRelatedSchema.setMakeTime(theCurrentTime);
				tLCInsuredRelatedSchema.setModifyDate(theCurrentDate);
				tLCInsuredRelatedSchema.setModifyTime(theCurrentTime);

				if (StrTool.cTrim(tLCInsuredRelatedSchema.getCustomerNo())
						.equals("")) {
					tLimit = "SN";

					String tInsuredNo = PubFun1.CreateMaxNo("CustomerNo",
							tLimit);
					tLCInsuredRelatedSchema.setCustomerNo(tInsuredNo);

					// 客户表同步数据
					LDPersonSchema tLDPersonSchema = new LDPersonSchema();
					tReflections.transFields(tLDPersonSchema,
							tLCInsuredRelatedSchema);
					tLDPersonSchema.setOperator(mGlobalInput.Operator);
					tLDPersonSchema.setMakeDate(theCurrentDate);
					tLDPersonSchema.setMakeTime(theCurrentTime);
					tLDPersonSchema.setModifyDate(theCurrentDate);
					tLDPersonSchema.setModifyTime(theCurrentTime);
					mLDPersonSet.add(tLDPersonSchema);
				}
				// LCInsuredRelatedSet.set(i,tLCInsuredRelatedBL.getSchema());
				// newLCInsuredRelatedSet.add(tLCInsuredRelatedBL);
			}
		}

		/***********************************************************************
		 * 在此校验被保人角色信息 by hl
		 * 
		 * *******************
		 */

		if (mAppntType.equals("1") || mLCPolBL.getPolTypeFlag().equals("0")) {
			String KDCheckFlag=(String)mTransferData.getValueByName("KDCheckFlag");
        	logger.debug("自助卡单是否校验标志:"+KDCheckFlag);
        	
        	//当KDCheckFlag不为null时,表明是走自助卡单核销程序进行核销,不进行投保规则校验,否则表明是走核心柜面出单,必须要走核保规则校验
			if (KDCheckFlag == null && !checkLMRiskRole("01")) {
				return false;
			}
		}
		/***********************************************************************
		
		 * 系统按照生成的生效日期计算客户年龄和保费，同时系统判断费率是否有误，并按照生效日期
		 * 更新被保险人年龄。如果没到帐，就设置为空，这时系统仍按照投保日期计算客户的年龄和保 费。 ******************
		 */
		/** @todo 生效日赋值 */
		// String strSQL = "select * from ljtempfee where 1=1 "
		// +" and otherno = '"+mLCPolBL.getContNo()+"'"
		// +" and enteraccdate is not null "
		// +" and confdate is null "
		// +" and confflag = '0'"
		// ;
		// LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		// LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(strSQL);
		// if(tLJTempFeeSet==null || tLJTempFeeSet.size() < 1)
		// {
		// logger.debug("财务未录入收费……");
		// }
		// else
		// {
		// mLCPolBL.setCValiDate(tLJTempFeeSet.get(1).getPayDate());
		// }
		logger.debug("第6步");
		// 校验被保人投保年龄
		int tInsuredAge = 0;
		// 只有ContType为团单标志为2，且为无名单时进入“前台录入年龄”分支 modify by lizhuo at 2005-11-24
		if (mLCPolBL.getPolTypeFlag().equals("1") && mDealFlag.equals("2")) { // houzm
			if (mLCPolBL.getInsuredAppAge() <= 0) {
				tInsuredAge = 30;
			} else {
				tInsuredAge = mLCPolBL.getInsuredAppAge(); // 前台录入年龄
			}
		} else { // 通过前台录入出生日期及计算
			/** @todo age */
			tInsuredAge = PubFun.calInterval(mLCInsuredBL.getBirthday(),
					mLCPolBL.getCValiDate(), "Y");
		}
		logger.debug("第7步");
		// logger.debug("被保投保年龄:" + tInsuredAge);
		// logger.debug("被保投保年龄描述:" +
		// mLMRiskAppSchema.getMinInsuredAge()+"|"
		// +mLMRiskAppSchema.getMaxInsuredAge());
		//tongmeng 2009-04-20 modify
		//checkLMRiskRole中校验
		/*
		LMRiskRoleDB bLMRiskRoleDB = new LMRiskRoleDB();
		LMRiskRoleSet bLMRiskRoleSet = new LMRiskRoleSet();
		bLMRiskRoleDB.setRiskCode(mLCPolBL.getRiskCode());
		bLMRiskRoleDB.setRiskRole("01");
		bLMRiskRoleSet = bLMRiskRoleDB.query();
		if (bLMRiskRoleSet.size() == 0) {
			if (tInsuredAge < mLMRiskAppSchema.getMinInsuredAge()) {
				// @@错误处理
				CError.buildErr(this, "被保人投保年龄不符合最小投保年龄要求!");
				return false;

			}
			if ((tInsuredAge > mLMRiskAppSchema.getMaxInsuredAge())
					&& (mLMRiskAppSchema.getMaxInsuredAge() > 0)) {
				// @@错误处理
				CError.buildErr(this, "被保人投保年龄不符合最大投保年龄要求!");
				return false;

			}
		}*/
		mLCPolBL.setInsuredNo(mLCInsuredBL.getInsuredNo());
		logger.debug("mLCPolBL.getInsuredPeoples()"
				+ mLCPolBL.getInsuredPeoples());
		if (mLCPolBL.getInsuredPeoples() == 0) {
			mLCPolBL.setInsuredPeoples(mLCInsuredBL.getInsuredPeoples());
		}
		logger.debug("mLCPolBL.getInsuredPeoples()"
				+ mLCPolBL.getInsuredPeoples());
		// logger.debug("mLCInsuredBL.getInsuredNo()"+mLCInsuredBL.
		// getInsuredNo());
		mLCPolBL.setInsuredName(mLCInsuredBL.getName());
		mLCPolBL.setInsuredSex(mLCInsuredBL.getSex());
		mLCPolBL.setInsuredBirthday(mLCInsuredBL.getBirthday());
		// mLCPolBL.setInsuredHealth(mLCInsuredBL.getHealth());
		mLCPolBL.setInsuredAppAge(tInsuredAge);
		mLCPolBL.setOccupationType(mLCInsuredBL.getOccupationType());
		//add by 2011-09-29    HY团险增加对
		if("2".equals(mLCPolBL.getContType())){
			if("".equals(aCurrency) || aCurrency == null ){
				aCurrency = mLCGrpPolSchema.getCurrency();
			}
			mLCPolBL.setCurrency(mLCGrpPolSchema.getCurrency());
		}

		//2008-09-17 zhangzheng 模板注释有误,应该保持和5.3一致，0-不自动垫交,1-自动垫交
		if (mLMRiskAppSchema.getAutoPayFlag().equals("1")) {
			if("".equals(mAutoPayFlag)&&null==mAutoPayFlag){
				mLCPolBL.setAutoPayFlag("1");
			}else{
				mLCPolBL.setAutoPayFlag(mAutoPayFlag);
			}
		} else {
			mLCPolBL.setAutoPayFlag("0");

		}
		mLCPolBL.setStandPrem(mLCPolBL.getPrem()); // 投保时总保费和每期保费相同

		// 如果不是无名单或者不是公共帐户，那么存放主被保人
		if (!(mLCPolBL.getPolTypeFlag().equals("1")
				|| mLCPolBL.getPolTypeFlag().equals("2") || mLCPolBL
				.getPolTypeFlag().equals("3"))) {
			mLCPolBL.setInsuredPeoples(1);
		}

		if (mOperate.equals("INSERT||PROPOSAL")) {
			// //houzm添加：如果是集体下的个人录入保存，判断
			// //如果姓名，性别，出生日期相同的，并且在同一印刷号，同一主附险中的个人信息，不能保存
			// //if( !mLCPolBL.getGrpPolNo().equals( SysConst.ZERONO
			// )&&mLCPolBL.getContNo().equals( SysConst.ZERONO ))
			if (mPolType.equals("2")) {
				String strSql = "select * from LCPol where "
						+ " InsuredName = '" + "?name?" + "' "
						+ " and InsuredSex = '" + "?sex?"
						+ "' " + " and InsuredBirthday = '"
						+ "?date?" + "' "
						+ " and RiskCode = '" + "?code?" + "' "
						+ " and PrtNo = '" + "?PrtNo?"
						+ "' and polstate<>'4' and conttype='2' and appflag <> '4'";
				SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
				sqlbv18.sql(strSql);
				sqlbv18.put("name",mLCPolBL.getInsuredName());
				sqlbv18.put("sex",mLCPolBL.getInsuredSex());
				sqlbv18.put("date",mLCPolBL.getInsuredBirthday());
				sqlbv18.put("code",mLCPolBL.getRiskCode());
				sqlbv18.put("PrtNo",mLCPolBL.getPrtNo());
				LCPolDB tempLCPolDB = new LCPolDB();
				LCPolSet tempLCPolSet = tempLCPolDB.executeQuery(sqlbv18);
				if (tempLCPolDB.mErrors.needDealError() == true) {
					// @@错误处理
					this.mErrors.copyAllErrors(tempLCPolDB.mErrors);

					CError.buildErr(this, "LCPol表查询失败！");

					return false;
				}
				if (tempLCPolSet.size() > 0) {
					if (mLCInsuredBL.getIDType().equals("0")) {
						for (i = 1; i <= tempLCPolSet.size(); i++) {
							LCContDB tempLCContDB = new LCContDB();
							tempLCContDB.setContNo(tempLCPolSet.get(i)
									.getContNo());
							if (mLCInsuredBL.getIDNo().toUpperCase().equals(
									tempLCContDB.query().get(1)
											.getInsuredIDNo().toUpperCase())) {
								// @@错误处理
								CError
										.buildErr(this,
												"该个人的姓名，性别，出生日期及保单的印刷号和险种编码和已存在的保单数据重复！不能录入");

								return false;
							}

						}
					} else {
						// @@错误处理
						CError.buildErr(this,
								"该个人的姓名，性别，出生日期及保单的印刷号和险种编码和已存在的保单数据重复！不能录入");

						return false;
					}
				}
			}

		}
		if (mOperate.equals("INSERT||PROPOSAL")) {
			mLCPolBL.setOperator(mGlobalInput.Operator);
			mLCPolBL.setPCValiDate(mLCPolBL.getCValiDate());
		}
		if (mOperate.equals("UPDATE||PROPOSAL")) {
			mLCPolBL.setOperator(OriginOperator);
			mLCPolBL.setPCValiDate(mLCPolBL.getCValiDate());
		}

		// logger.debug("保费保额计算调用部分");
		/** @todo 核心计算 */
		if (pubCal() == false) {
			return false;
		}
		
		/** @todo 2----生成本次处理交费号* */
		String tGetNoticeNo ="";
		//如果有交费号，则使用旧的，否则重新生成
		LJSPaySet qLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setOtherNo(mLCContSchema.getContNo());
		tLJSPayDB.setOtherNoType("2");
		qLJSPaySet = tLJSPayDB.query();
		if(qLJSPaySet!=null && qLJSPaySet.size()>0)
		{
			tGetNoticeNo=qLJSPaySet.get(1).getGetNoticeNo();
		}
		else
		{
			tGetNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
		}		
		logger.debug("本次处理交费号:::::::::::::::::::" + tGetNoticeNo);
		LCPremBLSet tLCPremBLSet = new LCPremBLSet();
		for(int k=1;k<=mLCPremBLSet.size();k++)
		{
			if(mLCPremBLSet.get(k).getPayStartDate().equals(mLCPolBL.getCValiDate()))
				tLCPremBLSet.add(mLCPremBLSet.get(k));
		}
		//************增加折扣处理 start
		if(mLCDiscountSet!=null && mLCDiscountSet.size()>0)
		{
			DiscountCalBL tDiscountCalBL = new DiscountCalBL();
			VData tzkVData = new VData();
			tzkVData.add(mLCPolBL.getSchema());
			tzkVData.add(tLCPremBLSet);
			tzkVData.add(mLCDiscountSet);
			tzkVData.add(tGetNoticeNo);
			//得到该保单折扣减去的钱 ，为负值
			if(!tDiscountCalBL.calculate(tzkVData))
			{
				CError.buildErr(this, "折扣计算失败！");
				return false;
			}
			
			//得到折扣和应收子表数据
			VData rVData = tDiscountCalBL.getResult();
			mLCDiscountSet = new LCDiscountSet();
			mLCDiscountSet = (LCDiscountSet)rVData.getObjectByObjectName("LCDiscountSet", 0);
			LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
			if(mLCDiscountSet==null)
			{
				CError.buildErr(this, "折扣信息返回失败！");
				return false;
			}
			if(tLJSPayPersonSet!=null)
				mLJSPayPersonSet.add(tLJSPayPersonSet);						
		}		
		//************增加折扣处理 end
		//************增加应收总表和子表处理 start
		TBPrepareLJS tTBPrepareLJS = new TBPrepareLJS();
		VData tzkVData = new VData();
		tzkVData.add(mLCPolBL.getSchema());
		tzkVData.add(tLCPremBLSet);
		tzkVData.add(mLJSPayPersonSet);
		tzkVData.add(tGetNoticeNo);
		if(!tTBPrepareLJS.prepare(tzkVData,"1"))
		{
			CError.buildErr(this, "准备应收信息失败！");
			return false;
		}
		
		//得到折扣和应收数据
		VData rVData = tTBPrepareLJS.getResult();		
		LJSPayPersonSet tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
		LJSPaySet tLJSPaySet = (LJSPaySet)rVData.getObjectByObjectName("LJSPaySet", 0);
		if(tLJSPayPersonSet==null ||tLJSPaySet==null)
		{
			CError.buildErr(this, "应收信息返回失败！");
			return false;
		}
		mLJSPayPersonSet = new LJSPayPersonSet();
		mLJSPayPersonSet.add(tLJSPayPersonSet);
		mLJSPaySet.add(tLJSPaySet);
		//************增加应收处理 end

		if (EdorType != null &&"2".equals(mLCPolBL.getContType())) {
			//团险短期费率折扣
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(mLCPolBL.getRiskCode());
			String InsuAccFlag = tLMRiskDB.query().get(1).getInsuAccFlag();
			if (!InsuAccFlag.trim().equals("Y")) {
				if (BqCalShortRate() == false) {
					return false;
				}
			}
			// comment by jiaqiangli 2009-04-16 
			// 此处代码不知在干嘛，为什么要重置，proposalbl.java的pubcal不是都已经计算出来了么 什么情况下重置? payenddate > enddate
//			if (BqTransEndDate() == false) {
//				return false;
//			}
		}
		// 保单表
		mLCPolBL.setRiskVersion(mLMRiskAppSchema.getRiskVer().trim());
		mLCPolBL.setKindCode(mLMRiskAppSchema.getKindCode().trim());

		if (mSavePolType.equals("0")) {
			mLCPolBL.setAppFlag("0"); // 投保单/保单标志
			mLCPolBL.setPolState("0"); // 投保单/保单标志
		} else {
			mLCPolBL.setAppFlag(mSavePolType); // 投保单/保单标志
			mLCPolBL.setPolState("0"); // 投保单/保单标志
		}
		mLCPolBL.setUWFlag("0"); // 核保状态，0为未核保
		mLCPolBL.setApproveFlag("0"); // 复核保状态，0为未复核

		if (mLMRiskSchema.getRnewFlag().equals("N")) {
			mLCPolBL.setRnewFlag(-2); // 续保标记，－2非续保
		} else {
			mLCPolBL.setRnewFlag(-1); // 续保标记，－1续保
		}

		if (this.mLCCustomerImpartBLSet.size() > 0) { // 是否有告知信息
			mLCPolBL.setImpartFlag("1");
		} else {
			mLCPolBL.setImpartFlag("0");
		}
		if (this.mLCBnfBLSet.size() > 0) { // 是否有受益人信息
			mLCPolBL.setBnfFlag("1");
		} else {
			mLCPolBL.setBnfFlag("0");
		}

		// logger.debug("承保描述表完成！");
		boolean bankFlag = true;

		// }

		// 受益人
		iMax = mLCBnfBLSet.size();
		for (i = 1; i <= iMax; i++) {
			LCBnfBL tLCBnfBL = new LCBnfBL();
			tLCBnfBL.setSchema(mLCBnfBLSet.get(i));
			if (tLCBnfBL.getInsuredNo() == null
					|| tLCBnfBL.getInsuredNo().trim().equals("")
					|| "".equals(tLCBnfBL.getInsuredNo().trim())) {
				tLCBnfBL.setInsuredNo(mLCInsuredBL.getInsuredNo());
			}
			tLCBnfBL.setContNo(mLCContSchema.getContNo());
			tLCBnfBL.setPolNo(mLCPolBL.getPolNo());
			tLCBnfBL.setOperator(mGlobalInput.Operator);
			tLCBnfBL.setMakeDate(theCurrentDate);
			tLCBnfBL.setMakeTime(theCurrentTime);
			tStr = tStr.valueOf(i);
			tLCBnfBL.setBnfNo(tStr);
			tLCBnfBL.setDefaultFields();
			mLCBnfBLSetNew.add(tLCBnfBL);
		}

		// 处理告知信息190
		LCCustomerImpartBL tLCCustomerImpartBL;
		iMax = mLCCustomerImpartBLSet.size();
		for (i = 1; i <= iMax; i++) {
			tLCCustomerImpartBL = new LCCustomerImpartBL();
			tLCCustomerImpartBL.setSchema(mLCCustomerImpartBLSet.get(i)
					.getSchema());
			/*
			 * Lis5.3 upgrade set
			 * tLCCustomerImpartBL.setPolNo(mLCPolBL.getPolNo());
			 */
			if ((tLCCustomerImpartBL.getCustomerNoType() != null)
					&& tLCCustomerImpartBL.getCustomerNoType().equals("A")) { // 投保人告知
				tLCCustomerImpartBL.setCustomerNo(mLCPolBL.getInsuredNo());
			}
			if ((tLCCustomerImpartBL.getCustomerNoType() != null)
					&& tLCCustomerImpartBL.getCustomerNoType().equals("I")) { // 被保人告知
				tLCCustomerImpartBL.setCustomerNo(mLCPolBL.getAppntNo());
			}
			tLCCustomerImpartBL.setDefaultFields();

			mLCCustomerImpartBLSetNew.add(tLCCustomerImpartBL);
		}

		// 特别约定
		LCSpecBL tLCSpecBL;
		iMax = mLCSpecBLSet.size();
		for (i = 1; i <= iMax; i++) {
			tLCSpecBL = new LCSpecBL();
			tLCSpecBL.setSchema(mLCSpecBLSet.get(i).getSchema());
			tStr = tStr.valueOf(i);
			tLCSpecBL.setSerialNo(PubFun1.CreateMaxNo("SpecNo", PubFun
					.getNoLimit(mGlobalInput.ComCode)));
			tLCSpecBL.setContNo(mLCPolBL.getPolNo());
			tLCSpecBL.setGrpContNo(mLCPolBL.getGrpContNo());
			tLCSpecBL.setPolNo(mLCPolBL.getPolNo());
			tLCSpecBL.setProposalNo(mLCPolBL.getProposalNo());

			tLCSpecBL.setOperator(mGlobalInput.Operator);
			tLCSpecBL.setDefaultFields();
			tLCSpecBL.setMakeDate(tLCSpecBL.getModifyDate());
			tLCSpecBL.setMakeTime(tLCSpecBL.getModifyTime());
			mLCSpecBLSetNew.add(tLCSpecBL);
		}
		// 更新合同表相关信息
		mLCContSchema.setModifyDate(theCurrentDate);
		mLCContSchema.setModifyDate(theCurrentDate);
		// mLCContSchema.PayIntv
		// mLCContSchema.setPayMode
		// mLCContSchema.setPayLocation
		// mLCContSchema.setDisputedFlag
		// mLCContSchema.setOutPayFlag
		// mLCContSchema.setGetPolMode
		// mLCContSchema.setPeoples(mLCContSchema.getPeoples()+mLCPolBL.
		// getInsuredPeoples());

		// logger.debug("sunxy:mLCContSchema.getPrem()"+mLCContSchema.
		// getPrem()) ;
		// logger.debug("sunxy:mLCPolBL.getPrem()"+mLCPolBL.getPrem()) ;
		//如果是新增  把lccont与lcpol相加 如果是修改 需减去原来的并加上重新计算后的
		if(mOperate.equals("INSERT||PROPOSAL")){
			mLCContSchema.setMult(mLCContSchema.getMult() + mLCPolBL.getMult());
//			mLCContSchema.setPrem(mLCContSchema.getPrem() + mLCPolBL.getPrem());
//			mLCContSchema.setAmnt(mLCContSchema.getAmnt() + mLCPolBL.getAmnt());
//			mLCContSchema.setSumPrem(mLCContSchema.getSumPrem()
//					+ mLCPolBL.getSumPrem());
			double ExchPrem = 0.00;
			double ExchAmnt = 0.00;
			double ExchSumPrem = 0.00;
			LDExch tLDExch = new LDExch();
			//TODO: update by YaoYi for temp
			//if (null == mLCPolBL.getCurrency()) mLCPolBL.setCurrency("01");
			ExchPrem = tLDExch.toBaseCur(mLCPolBL.getCurrency(), locCurrency, theCurrentDate, mLCPolBL.getPrem());		
			ExchAmnt = tLDExch.toBaseCur(mLCPolBL.getCurrency(), locCurrency, theCurrentDate, mLCPolBL.getAmnt());
			ExchSumPrem = tLDExch.toBaseCur(mLCPolBL.getCurrency(), locCurrency, theCurrentDate, mLCPolBL.getSumPrem());
			mLCContSchema.setPrem(mLCContSchema.getPrem() + ExchPrem);
			mLCContSchema.setAmnt(mLCContSchema.getAmnt() + ExchAmnt);
			mLCContSchema.setSumPrem(mLCContSchema.getSumPrem() + ExchSumPrem);
		}
		if(mOperate.equals("UPDATE||PROPOSAL")){
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLCPolBL.getPolNo());
			if(!tLCPolDB.getInfo()){
				CError.buildErr(this, "查询原险种信息失败！");
				return false;
			}
			mLCContSchema.setMult(mLCContSchema.getMult() + mLCPolBL.getMult()-tLCPolDB.getMult());
//			mLCContSchema.setPrem(mLCContSchema.getPrem() + mLCPolBL.getPrem()-tLCPolDB.getPrem());
//			mLCContSchema.setAmnt(mLCContSchema.getAmnt() + mLCPolBL.getAmnt()-tLCPolDB.getAmnt());
//			mLCContSchema.setSumPrem(mLCContSchema.getSumPrem() + mLCPolBL.getSumPrem()-tLCPolDB.getSumPrem());
			double oldExchPrem = 0.00;
			double oldExchAmnt = 0.00;
			double oldExchSumPrem = 0.00;
			double ExchPrem = 0.00;
			double ExchAmnt = 0.00;
			double ExchSumPrem = 0.00;
			LDExch tLDExch = new LDExch();
			oldExchPrem = tLDExch.toBaseCur(tLCPolDB.getCurrency(), locCurrency, theCurrentDate, tLCPolDB.getPrem());		
			oldExchAmnt = tLDExch.toBaseCur(tLCPolDB.getCurrency(), locCurrency, theCurrentDate, tLCPolDB.getAmnt());
			oldExchSumPrem = tLDExch.toBaseCur(tLCPolDB.getCurrency(), locCurrency, theCurrentDate, tLCPolDB.getSumPrem());
			ExchPrem = tLDExch.toBaseCur(mLCPolBL.getCurrency(), locCurrency, theCurrentDate, mLCPolBL.getPrem());		
			ExchAmnt = tLDExch.toBaseCur(mLCPolBL.getCurrency(), locCurrency, theCurrentDate, mLCPolBL.getAmnt());
			ExchSumPrem = tLDExch.toBaseCur(mLCPolBL.getCurrency(), locCurrency, theCurrentDate, mLCPolBL.getSumPrem());
			mLCContSchema.setPrem(mLCContSchema.getPrem() + ExchPrem - oldExchPrem);
			mLCContSchema.setAmnt(mLCContSchema.getAmnt() + ExchAmnt - oldExchAmnt);
			mLCContSchema.setSumPrem(mLCContSchema.getSumPrem() + ExchSumPrem - oldExchSumPrem);
		}

		// if
		// (mLCPolBL.getPaytoDate().compareTo(mLCContSchema.getPaytoDate())>0)
		// mLCContSchema.setPaytoDate (mLCPolBL.getPaytoDate()); //最大交至日期
		// if
		// (mLCPolBL.getFirstPayDate().compareTo(mLCContSchema.getFirstPayDate
		// ())<0)
		// mLCContSchema.setFirstPayDate(mLCPolBL.getFirstPayDate()); //最小首期缴费日期
		// if
		// (mLCPolBL.getCValiDate().compareTo(mLCContSchema.getCValiDate())<0)
		// mLCContSchema.setCValiDate(mLCPolBL.getCValiDate()); //最小生效日期

		// 集体下的个人投保单信息
		if (mPolType.equals("2")) {
			mLCGrpPolSchema.setModifyDate(theCurrentDate);
			mLCGrpPolSchema.setModifyDate(theCurrentDate);
			mLCGrpPolSchema.setModifyDate(theCurrentDate);
			mLCGrpPolSchema.setModifyDate(theCurrentDate);
			mLCPolBL.setGrpPolNo(mLCGrpPolSchema.getGrpPolNo());

			if (mOperate.equals("INSERT||PROPOSAL")) {
				// 如果添加个人，则更新集体状态
				mLCGrpContSchema.setApproveFlag("0"); // 复核状态
				mLCGrpContSchema.setUWFlag("0"); // 核保状态

				mLCGrpPolSchema.setApproveFlag("0"); // 复核状态
				mLCGrpPolSchema.setUWFlag("0"); // 核保状态

				// 普通的集体下个人被保人数为1
				int NONAMENUM = 1;

				// 如果保单类型是无名单：被保人人数不定，根据录入决定;如果保单类型是公共帐户：被保人人数为0，因为只是为生成集体帐户而用
				if (mLCPolBL.getPolTypeFlag().equals("1")
						|| mLCPolBL.getPolTypeFlag().equals("2")
						|| mLCPolBL.getPolTypeFlag().equals("3")) {
					NONAMENUM = mLCPolBL.getInsuredPeoples();
				}

				// 如果不是集体下无名单加人--如果是--是否也应该加人，加费
				if (!mSavePolType.equals("4")) {
					// mLCGrpContSchema.setPeoples(mLCGrpContSchema.getPeoples2()
					// + (1 * NONAMENUM));

					// 由于精度问题添加该段代码 modify by sunxy 2005-01-16
					// mLCGrpContSchema.setPrem(mLCGrpContSchema.getPrem()
					// + mLCPolBL.getPrem());
					// mLCGrpContSchema.setAmnt(mLCGrpContSchema.getAmnt()
					// + mLCPolBL.getAmnt());
					// mLCGrpContSchema.setMult(mLCGrpContSchema.getMult()
					// + mLCPolBL.getMult());
					//
					// mLCGrpPolSchema.setPeoples2(mLCGrpPolSchema.getPeoples2()
					// + (1 * NONAMENUM));
					// mLCGrpPolSchema.setPrem(mLCGrpPolSchema.getPrem()
					// + mLCPolBL.getPrem());
					// mLCGrpPolSchema.setAmnt(mLCGrpPolSchema.getAmnt()
					// + mLCPolBL.getAmnt());

					// 由于精度问题添加该段代码 add by sunxy 2005-01-16
					String strCalGrpPolPrem = mDecimalFormat
							.format(mLCGrpPolSchema.getPrem()
									+ mLCPolBL.getPrem()); // 转换计算后的保费(规定的精度)
					// String tExpPrem =
					// mDecimalFormat.format(mLCGrpPolSchema.getExpPremium());
					String strCalGrpPolAmnt = mDecimalFormat
							.format(mLCGrpPolSchema.getAmnt()
									+ mLCPolBL.getAmnt()); // 转换计算后的保额
					String strCalGrpContPrem = mDecimalFormat
							.format(mLCGrpContSchema.getPrem()
									+ mLCPolBL.getPrem()); // 转换计算后的保费(规定的精度)
					String strCalGrpContAmnt = mDecimalFormat
							.format(mLCGrpContSchema.getAmnt()
									+ mLCPolBL.getAmnt()); // 转换计算后的保额
					double calGrpPolPrem = Double.parseDouble(strCalGrpPolPrem);
					double calGrpPolAmnt = Double.parseDouble(strCalGrpPolAmnt);

					double calGrpContPrem = Double
							.parseDouble(strCalGrpContPrem);
					double calGrpContAmnt = Double
							.parseDouble(strCalGrpContAmnt);

					// //只做提示
					// if (Double.parseDouble(tExpPrem) < calGrpPolPrem){
					// CError tError = new CError();
					// tError.moduleName = "ProposalBL";
					// tError.functionName = "dealDataPerson";
					// tError.errorMessage = "团单险种" +
					// mLCGrpPolSchema.getRiskCode() +
					// "被保人保费和" + strCalGrpPolPrem +
					// "高于团单预期保费" + tExpPrem + "！";
					// this.mErrors.addOneError(tError);
					// }
					mLCGrpPolSchema.setPrem(calGrpPolPrem);
					mLCGrpPolSchema.setAmnt(calGrpPolAmnt);
					mLCGrpContSchema.setPrem(calGrpContPrem);
					mLCGrpContSchema.setAmnt(calGrpContAmnt);

					mLCGrpContSchema.setMult(mLCGrpContSchema.getMult()
							+ mLCPolBL.getMult());

					mLCGrpPolSchema.setPeoples2(mLCGrpPolSchema.getPeoples2()
							+ (1 * NONAMENUM));
					if (EdorType == null
							&& mLCGrpPolSchema.getPayEndDate() != null
							&& mLCGrpPolSchema.getPayEndDate().compareTo(
									mLCPolBL.getPayEndDate()) < 0) {

						mLCGrpPolSchema.setPayEndDate(mLCPolBL.getPayEndDate());
					}

					if (EdorType == null
							&& mLCGrpPolSchema.getPayEndDate() == null) {
						mLCGrpPolSchema.setPayEndDate(mLCPolBL.getPayEndDate());
					}

				}
			}
			if (mOperate.equals("UPDATE||PROPOSAL")) {
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(mLCPolBL.getPolNo());
				if (tLCPolDB.getInfo() == false) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCPolDB.mErrors);

					CError.buildErr(this, "个人投保单表取数失败！");

					return false;
				}
				if ((tLCPolDB.getApproveFlag() == null)
						|| (tLCPolDB.getUWFlag() == null)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCPolDB.mErrors);
					CError.buildErr(this, "该个人投保单的复核状态或者核保状态存在问题！请告知信息技术部核实");
					return false;
				}

				// 判断如果更新的个人的复核状态或核保状态发生变化，更新集体状态
				if (!(tLCPolDB.getApproveFlag().equals(
						mLCPolBL.getApproveFlag()) && tLCPolDB.getApproveFlag()
						.equals(mLCPolBL.getApproveFlag()))) {

					mLCGrpContSchema.setApproveFlag("0"); // 复核状态
					mLCGrpContSchema.setUWFlag("0"); // 核保状态

					mLCGrpPolSchema.setApproveFlag("0"); // 复核状态
					mLCGrpPolSchema.setUWFlag("0"); // 核保状态

				}

				int NONAMENUM = 1;

				// 如果不是集体下无名单加人
				if (!mSavePolType.equals("4")) {
					// 上边已经加过mLCPolBL的prem,amnt,mult,sumprem,所以在这里不再加了
					String strCalContPrem = mDecimalFormat.format(mLCContSchema
							.getPrem()
							- tLCPolDB.getPrem()+mLCPolBL.getPrem()); // 转换计算后的保费(规定的精度)
					String strCalContAmnt = mDecimalFormat.format(mLCContSchema
							.getAmnt()
							- tLCPolDB.getAmnt()+mLCPolBL.getAmnt()); // 转换计算后的保额
					String strCalContSumPrem = mDecimalFormat
							.format(mLCContSchema.getSumPrem()
									- tLCPolDB.getSumPrem()+mLCPolBL.getAmnt());
					double calContPrem = Double.parseDouble(strCalContPrem);
					double calContAmnt = Double.parseDouble(strCalContAmnt);
					double calContSumPrem = Double
							.parseDouble(strCalContSumPrem);
					mLCContSchema.setPrem(calContPrem);
					mLCContSchema.setAmnt(calContAmnt);
					mLCContSchema.setMult(mLCContSchema.getMult()
							- tLCPolDB.getMult());
					mLCContSchema.setSumPrem(calContSumPrem);

					// 修改集体下个人时，只有无名单可能对被保人数变化
					if (mLCPolBL.getPolTypeFlag().equals("1")) {
						// mLCGrpContSchema.setPeoples2((mLCGrpContSchema
						// .getPeoples2()
						// + mLCPolBL
						// .getInsuredPeoples())
						// - tLCPolDB
						// .getInsuredPeoples()
						// );
						mLCGrpPolSchema.setPeoples2((mLCGrpPolSchema
								.getPeoples2() + mLCPolBL.getInsuredPeoples())
								- tLCPolDB.getInsuredPeoples());
						NONAMENUM = mLCPolBL.getInsuredPeoples();
					} else {
						// mLCGrpContSchema.setPeoples2(
						// mLCGrpContSchema.getPeoples2() + 1 );
//						mLCGrpPolSchema.setPeoples2(mLCGrpPolSchema
//								.getPeoples2() + 1);
					}
					if (EdorType == null
							&& mLCGrpPolSchema.getPayEndDate() != null
							&& mLCGrpPolSchema.getPayEndDate().compareTo(
									mLCPolBL.getPayEndDate()) < 0) {

						mLCGrpPolSchema.setPayEndDate(mLCPolBL.getPayEndDate());
					}

					if (EdorType == null
							&& mLCGrpPolSchema.getPayEndDate() == null) {
						mLCGrpPolSchema.setPayEndDate(mLCPolBL.getPayEndDate());
					}

					// 由于精度问题注释调该段代码 add by sunxy 2005-01-16
					// mLCGrpContSchema.setPrem((mLCGrpContSchema.getPrem()
					// + mLCPolBL.getPrem())
					// - tLCPolDB.getPrem());
					// mLCGrpContSchema.setAmnt((mLCGrpContSchema.getAmnt()
					// + mLCPolBL.getAmnt())
					// - tLCPolDB.getAmnt());
					// mLCGrpContSchema.setMult((mLCGrpContSchema.getMult()
					// + mLCPolBL.getMult())
					// - tLCPolDB.getMult());
					//
					//
					// mLCGrpPolSchema.setPrem((mLCGrpPolSchema.getPrem()
					// + mLCPolBL.getPrem())
					// - tLCPolDB.getPrem());
					// mLCGrpPolSchema.setAmnt((mLCGrpPolSchema.getAmnt()
					// + mLCPolBL.getAmnt())
					// - tLCPolDB.getAmnt());

					// 由于精度问题添加该段代码 add by sunxy 2005-01-16
					String strCalGrpPolPrem = mDecimalFormat
							.format((mLCGrpPolSchema.getPrem() + mLCPolBL
									.getPrem())
									- tLCPolDB.getPrem()); // 转换计算后的保费(规定的精度)
					String strCalGrpPolAmnt = mDecimalFormat
							.format((mLCGrpPolSchema.getAmnt() + mLCPolBL
									.getAmnt())
									- tLCPolDB.getAmnt()); // 转换计算后的保额

					String strCalGrpContPrem = mDecimalFormat
							.format((mLCGrpContSchema.getPrem() + mLCPolBL
									.getPrem())
									- tLCPolDB.getPrem()); // 转换计算后的保费(规定的精度)
					String strCalGrpContAmnt = mDecimalFormat
							.format((mLCGrpContSchema.getAmnt() + mLCPolBL
									.getAmnt())
									- tLCPolDB.getAmnt()); // 转换计算后的保额

					double calGrpPolPrem = Double.parseDouble(strCalGrpPolPrem);
					// double calGrpPolAmnt =
					double calGrpPolAmnt = Double.parseDouble(strCalGrpPolAmnt);
					// Double.parseDouble(strCalGrpPolAmnt);

					double calGrpContPrem = Double
							.parseDouble(strCalGrpContPrem);
					double calGrpContAmnt = Double
							.parseDouble(strCalGrpContAmnt);

					// if (Double.parseDouble(tExpPrem) < calGrpPolPrem){
					// CError tError = new CError();
					// tError.moduleName = "ProposalBL";
					// tError.functionName = "dealDataPerson";
					// tError.errorMessage = "团单险种" +
					// mLCGrpPolSchema.getRiskCode() +
					// "被保人保费和" + strCalGrpPolPrem +
					// "高于团单预期保费" + tExpPrem + "！";
					// this.mErrors.addOneError(tError);
					//
					// }

					mLCGrpContSchema.setPrem(calGrpContPrem);
					mLCGrpContSchema.setAmnt(calGrpContAmnt);
					mLCGrpContSchema
							.setMult((mLCGrpContSchema.getMult() + mLCPolBL
									.getMult())
									- tLCPolDB.getMult());
					mLCGrpPolSchema.setPrem(calGrpPolPrem);
					mLCGrpPolSchema.setAmnt(calGrpContAmnt);

				}
			}
		}

		return true;
	}

	/**
	 * 判断是否需要处理浮动附率：如果需要，将相关数据变更
	 * 只处理页面录入保费，保额而没有录入浮动费率的情况：录入浮动费率的情况已经在险种计算公式描述中完成
	 * 在此之前，如果传入浮动费率，计算公式中已经计算过浮动费率
	 * 
	 * @param pLCPolBL
	 *            个人保单纪录
	 * @param pLCPremBLSet
	 *            保费项
	 * @param pLCGetBLSet
	 *            领取项
	 * @param pLCDutyBLSet
	 *            责任项
	 * @return
	 */
	private boolean dealFloatRate(LCPolBL pLCPolBL, LCPremBLSet pLCPremBLSet,
			LCGetBLSet pLCGetBLSet, LCDutyBLSet pLCDutyBLSet) {
		// 如果界面上录入保费和保额：且浮动费率==0(不计算浮动费率,校验录入的保费保额是否和计算得到的数据匹配
		if ((FloatRate == 0) && (InputPrem > 0) && (InputAmnt > 0)) {
			dealFormatModol(pLCPolBL, pLCPremBLSet, pLCGetBLSet, pLCDutyBLSet); // 转换精度
			if ((InputPrem != pLCPolBL.getPrem())
					|| (InputAmnt != pLCPolBL.getAmnt())) {
				CError.buildErr(this, "您录入的保费保额（" + InputPrem + "," + InputAmnt
						+ "）与计算得到的保费保额（" + pLCPolBL.getPrem() + ","
						+ pLCPolBL.getAmnt() + "）不符合！");

				return false;
			}

			return true;
		}

		// 如果界面上录入保费和保额：且浮动费率==规定的常量,那么认为该浮动费率需要自动计算得到
		// 如果界面上录入保费和保额：且浮动费率<>规定的常量,那么认为浮动费率已经给出，且在前面的保费保额计算中用到。此处不用处理
		// 该种险种的计算公式需要描述
		if ((autoCalFloatRateFlag == true) && (InputPrem > 0)
				&& (InputAmnt > 0)) {
			String strCalPrem = mDecimalFormat.format(pLCPolBL.getPrem()); // 转换计算后的保费
			// (
			// 规定的精度
			// )
			String strCalAmnt = mDecimalFormat.format(pLCPolBL.getAmnt()); // 转换计算后的保额
			double calPrem = Double.parseDouble(strCalPrem);
			double calAmnt = Double.parseDouble(strCalAmnt);

			// 如果界面录入的保费保额与计算出来的保费保额相等，则认为浮动费率=1,不做任何处理返回
			if ((calPrem == InputPrem) && (calAmnt == InputAmnt)) {
				return true;
			}

			if (pLCPolBL.getPrem() == 0) {
				CError.buildErr(this, "计算得到的保费为0,不能做浮动费率计算！");

				return false;
			}

			// 浮动费率的计算公式为：P(现)*A(原)/(A(现)*P(原))
			double formatFloatRate = 0;
			FloatRate = (InputPrem * pLCPolBL.getAmnt())
					/ (InputAmnt * pLCPolBL.getPrem());
			formatFloatRate = Double.parseDouble(mFRDecimalFormat
					.format(FloatRate));

			// 更新个人保单
			pLCPolBL.setPrem(pLCPolBL.getPrem() * FloatRate);
			pLCPolBL.setStandPrem(pLCPolBL.getStandPrem() * FloatRate);
			pLCPolBL.setFloatRate(formatFloatRate); // 存储转换后的浮动费率精度

			// 更新保费项
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {
				LCPremSchema tLCPremSchema = (LCPremSchema) pLCPremBLSet.get(j);
				if (!tLCPremSchema.getPayPlanCode().substring(0, 6).equals(
						"000000")) {
					tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem()
							* FloatRate);
				}

				pLCPremBLSet.set(j, tLCPremSchema);
			}

			// 更新责任项
			for (int j = 1; j <= pLCDutyBLSet.size(); j++) {
				LCDutySchema tLCDutySchema = (LCDutySchema) pLCDutyBLSet.get(j);
				tLCDutySchema.setStandPrem(tLCDutySchema.getStandPrem()
						* FloatRate);
				pLCDutyBLSet.set(j, tLCDutySchema);
			}

			dealFormatModol(pLCPolBL, pLCPremBLSet, pLCGetBLSet, pLCDutyBLSet); // 转换精度

			// 因为累计和可能存在进位误差（可能会有0.01元的），所以需要调整
			double sumDutyPrem = 0.0;
			for (int j = 1; j <= pLCDutyBLSet.size(); j++) {
				LCDutySchema tLCDutySchema = (LCDutySchema) pLCDutyBLSet.get(j);
				sumDutyPrem = sumDutyPrem + tLCDutySchema.getStandPrem();
			}

			if (sumDutyPrem != InputPrem) {
				double diffMoney = sumDutyPrem - InputPrem;

				// 调整责任表
				for (int j = 1; j <= pLCDutyBLSet.size(); j++) {
					if (pLCDutyBLSet.get(j).getStandPrem() > 0.0) {
						pLCDutyBLSet.get(j).setStandPrem(
								pLCDutyBLSet.get(j).getStandPrem() - diffMoney);

						break;
					}
				}

				// 调整保费表
				for (int j = 1; j <= pLCPremBLSet.size(); j++) {
					if (pLCPremBLSet.get(j).getStandPrem() > 0) {
						pLCPremBLSet.get(j).setStandPrem(
								pLCPremBLSet.get(j).getStandPrem() - diffMoney);

						break;
					}
				}
			}

			return true;
		}

		// 如果界面上录入保费.而保额为0：且浮动费率==规定的常量,那么认为录入的保费即为经过计算后得到的保费，
		// 此时，需要自动计算得到该浮动费率
		if ((autoCalFloatRateFlag == true) && (InputPrem > 0)
				&& (InputAmnt == 0)) {
			String strCalPrem = mDecimalFormat.format(pLCPolBL.getPrem()); // 转换计算后的保费
			// (
			// 规定的精度
			// )
			double calPrem = Double.parseDouble(strCalPrem);

			// 如果界面录入的保费与计算出来的保费相等，则认为浮动费率=1,不做任何处理返回
			if (calPrem == InputPrem) {
				return true;
			}

			if (pLCPolBL.getPrem() == 0) {
				CError.buildErr(this, "计算得到的保费为0,不能做浮动费率计算！");

				return false;
			}

			// 浮动费率的计算公式为：P(现)/P(原)
			double formatFloatRate = 0;
			FloatRate = InputPrem / (pLCPolBL.getPrem());
			formatFloatRate = Double.parseDouble(mFRDecimalFormat
					.format(FloatRate));

			// 更新个人保单
			pLCPolBL.setPrem(InputPrem);
			pLCPolBL.setStandPrem(InputPrem);
			pLCPolBL.setFloatRate(formatFloatRate); // 存储转换后的浮动费率精度

			// 更新保费项
			for (int j = 1; j <= pLCPremBLSet.size(); j++) {
				LCPremSchema tLCPremSchema = (LCPremSchema) pLCPremBLSet.get(j);
				if (!tLCPremSchema.getPayPlanCode().substring(0, 6).equals(
						"000000")) {
					tLCPremSchema.setStandPrem(tLCPremSchema.getStandPrem()
							* FloatRate);
				}

				pLCPremBLSet.set(j, tLCPremSchema);
			}

			// 更新责任项
			for (int j = 1; j <= pLCDutyBLSet.size(); j++) {
				LCDutySchema tLCDutySchema = (LCDutySchema) pLCDutyBLSet.get(j);
				tLCDutySchema.setStandPrem(tLCDutySchema.getStandPrem()
						* FloatRate);
				pLCDutyBLSet.set(j, tLCDutySchema);

				if ((pLCDutyBLSet.get(j).getStandPrem() == 0)
						&& (pLCPremBLSet.get(j).getStandPrem() != 0)) {
					pLCPremBLSet.get(j).setStandPrem(0);
				}
			}

			dealFormatModol(pLCPolBL, pLCPremBLSet, pLCGetBLSet, pLCDutyBLSet); // 转换精度

			// 因为累计和可能存在进位误差（可能会有0.01元的），所以需要调整
			double sumDutyPrem = 0.0;
			for (int j = 1; j <= pLCDutyBLSet.size(); j++) {
				LCDutySchema tLCDutySchema = (LCDutySchema) pLCDutyBLSet.get(j);
				sumDutyPrem = sumDutyPrem + tLCDutySchema.getStandPrem();
			}

			if (sumDutyPrem != InputPrem) {
				double diffMoney = sumDutyPrem - InputPrem;

				// 调整责任表
				for (int j = 1; j <= pLCDutyBLSet.size(); j++) {
					if (pLCDutyBLSet.get(j).getStandPrem() > 0.0) {
						pLCDutyBLSet.get(j).setStandPrem(
								pLCDutyBLSet.get(j).getStandPrem() - diffMoney);

						break;
					}
				}

				// 调整保费表
				for (int j = 1; j <= pLCPremBLSet.size(); j++) {
					if (pLCPremBLSet.get(j).getStandPrem() > 0) {
						pLCPremBLSet.get(j).setStandPrem(
								pLCPremBLSet.get(j).getStandPrem() - diffMoney);

						break;
					}
				}
			}

			return true;
		}

		// 否则，直接转换精度
		dealFormatModol(pLCPolBL, pLCPremBLSet, pLCGetBLSet, pLCDutyBLSet);

		return true;
	}

	/**
	 * 处理传入的数据，转换合适的精度
	 * 
	 * @param pLCPolBL
	 * @param pLCPremBLSet
	 * @param pLCGetBLSet
	 * @param pLCDutyBLSet
	 * @return
	 */
	private boolean dealFormatModol(LCPolBL pLCPolBL, LCPremBLSet pLCPremBLSet,
			LCGetBLSet pLCGetBLSet, LCDutyBLSet pLCDutyBLSet) {
		String strCalPrem = mDecimalFormat.format(pLCPolBL.getPrem()); // 转换计算后的保费
		// (
		// 规定的精度
		// )
		String strCalAmnt = mDecimalFormat.format(pLCPolBL.getAmnt()); // 转换计算后的保额
		double calPrem = Double.parseDouble(strCalPrem);
		double calAmnt = Double.parseDouble(strCalAmnt);

		// 更新个人保单
		pLCPolBL.setPrem(calPrem);
		pLCPolBL.setStandPrem(calPrem);
		pLCPolBL.setAmnt(calAmnt);

		// 更新保费项
		double tempPrem = 0;

		// double tempAmnt=0;
		for (int j = 1; j <= pLCPremBLSet.size(); j++) {
			LCPremSchema tLCPremSchema = (LCPremSchema) pLCPremBLSet.get(j);
			tempPrem = Double.parseDouble(mDecimalFormat.format(tLCPremSchema
					.getStandPrem()));
			tLCPremSchema.setStandPrem(tempPrem);
			pLCPremBLSet.set(j, tLCPremSchema);
		}

		// 更新责任项
		for (int j = 1; j <= mLCDutyBLSet.size(); j++) {
			LCDutySchema tLCDutySchema = (LCDutySchema) mLCDutyBLSet.get(j);
			tempPrem = Double.parseDouble(mDecimalFormat.format(tLCDutySchema
					.getStandPrem()));
			tLCDutySchema.setStandPrem(tempPrem);
			mLCDutyBLSet.set(j, tLCDutySchema);
		}

		return true;
	}

	public VData getCardResult() {
		return this.rResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 判断国家及本币
		String CurrString = "select codename from ldcode1 where codetype = 'currencyprecision' " 
			+" and code1 = (select sysvarvalue from ldsysvar where sysvar='nativeplace')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(CurrString);
		ExeSQL tExeSQL = new ExeSQL();
		locCurrency = tExeSQL.getOneValue(sqlbv);
		if(locCurrency==null||"".equals(locCurrency)){
			CError.buildErr(this, "查询本币信息失败！");
			return false;
		}
		
		// 删除险种
		if (mOperate.equals("DELETE||PROPOSAL")) {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));

			// 保单
			mLCPolBL.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
					"LCPolSchema", 0));
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLCPolBL.getProposalNo());

			if (!tLCPolDB.getInfo()) {
				CError.buildErr(this, "查询险种保单(" + tLCPolDB.getPolNo() + ")失败！");
				return false;
			}
			mLCPolBL.setSchema(tLCPolDB.getSchema());
		}
		// 修改新增险种
		else if (mOperate.equals("INSERT||PROPOSAL")
				|| mOperate.equals("UPDATE||PROPOSAL")) {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));

			// 保单
			mLCPolBL.setSchema((LCPolSchema) cInputData.getObjectByObjectName(
					"LCPolSchema", 0));

			// 险种其他信息
			mLCPolOtherSet.set((LCPolOtherSet) cInputData
					.getObjectByObjectName("LCPolOtherSet", 0));
			if (mLCPolOtherSet != null && mLCPolOtherSet.size() >= 1) {
				mLCPolOtherSchema.setSchema(mLCPolOtherSet.get(1));
			} else {
				mLCPolOtherSchema = null;
			}

			// 前台向后台传递的数据容器：放置一些不能通过Schema传递的变量
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			if (mTransferData == null) {
				// @@错误处理
				CError.buildErr(this, "在接受数据时没有得到TransferData的数据!");

				return false;
			}

			mark = (String) mTransferData.getValueByName("mark"); // add by
			mAutoPayFlag = (String) mTransferData.getValueByName("AutoPayFlag"); // add by			
			// yaory
			logger.debug("卡单标志 in proposalbl====" + mark);
			
			YBTFlag=(String)mTransferData.getValueByName("YBTFlag");// add bu duanyh

			if (mTransferData.getValueByName("ISPlanRisk") != null
					&& "Y".equals(mTransferData.getValueByName("ISPlanRisk"))) {
				mISPlanRiskFlag = true;
			}// ??险种计划??

			// 团体保单磁盘投保标记(待检查)
			if (mTransferData.getValueByName("GrpImport") != null) {
				mGrpImportFlag = true;
				// 传入主险保单,因为前面有LCPolSchema,为区别索引，特从第10个为止开始查找
				LCPolBL tMainPolBL = null;
				if (mark != null && mark.equals("card")) {
					tMainPolBL = (LCPolBL) cInputData.getObjectByObjectName(
							"LCPolBL", 6);
				} else {
					tMainPolBL = (LCPolBL) cInputData.getObjectByObjectName(
							"LCPolBL", 10);
				}
				if (tMainPolBL == null) {
					CError.buildErr(this, "传入主险保单不能为空!");

					return false;

				}
				mainLCPolBL = tMainPolBL;
				// mainLCPolBL .setSchema( tMainPol );
			}

			// 合同
			mLCContSchema.setSchema((LCContSchema) cInputData
					.getObjectByObjectName("LCContSchema", 0));
			if (mLCContSchema.getContNo() == null) {
				CError.buildErr(this, "在接受数据时没有得到合同的数据!");

				return false;
			}

			// 个人保单或团体卡单获取合同表信息
			if (!mGrpImportFlag
					|| (mGrpImportFlag && mark != null && mark.equals("card"))) {
				LCContDB mLCContDB = new LCContDB();
				mLCContDB.setSchema(mLCContSchema);
				mLCContDB.getInfo();
				if (mLCContDB.mErrors.needDealError()) {
					CError.buildErr(this, "查询合同的数据失败!");

					return false;
				}
				mLCContSchema.setSchema(mLCContDB);
				//为避免影响到其他功能，注释掉此处，该为在录单点击录入"录入完毕"时写sql修改
				//如果是修改险种信息，先将LCCont表中的保额保费数据清0，在下面的程序中会重新赋值
				//老系统中的处理方式是将保额保费累加，只修改lcpol表中的数据，lccont中则为累加
				//if(mOperate.equals("UPDATE||PROPOSAL")){
				//	mLCContSchema.setAmnt(0.0);
				//	mLCContSchema.setPrem(0.0);
				//	mLCContSchema.setMult(0.0);
				//	
				//}
			}

			// 如果保单类型为集体总单且不是卡单则走团单处理逻辑
			// 否则为个单逻辑:2-团单 1-个单
			if ("2".equals(mLCContSchema.getContType()) && !"card".equals(mark)) {
				this.mDealFlag = "2";
			} else {
				this.mDealFlag = "1";
			}

			logger.debug("处理投报人信息====" + mGrpImportFlag);
			// 处理个人保单或团体卡单
			if (("1").equals(mDealFlag)) {
				// 投保人信息，被保险人信息从数据库里查询
				if (!mGrpImportFlag
						|| (mGrpImportFlag && mark != null && mark
								.equals("card"))) {
					mLCAppntBL.setSchema((LCAppntSchema) cInputData
							.getObjectByObjectName("LCAppntSchema", 0));
					LCAppntDB tLCAppntDB = new LCAppntDB();
					// edit by yaory

					logger.debug("投报人查询====" + mLCAppntBL.getContNo()
							+ "-" + mLCAppntBL.getAppntNo());
					tLCAppntDB.setContNo(mLCAppntBL.getContNo());
					tLCAppntDB.setAppntNo(mLCAppntBL.getAppntNo());
					if (mark != null && mark.equals("card")) {
						logger.debug("不用校验投报人信息");
					} else {

						if (tLCAppntDB.getInfo() == false) {
							// @@错误处理
							CError.buildErr(this, "没有查到传入的投保人客户信息!");
							return false;
						}
						mLCAppntBL.setSchema(tLCAppntDB);
					}
				}
			} else {

				mLCGrpAppntBL.setSchema((LCGrpAppntSchema) cInputData
						.getObjectByObjectName("LCGrpAppntSchema", 0));
				LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
				tLCGrpAppntDB.setGrpContNo(mLCGrpAppntBL.getGrpContNo());
				tLCGrpAppntDB.setCustomerNo(mLCGrpAppntBL.getCustomerNo());

				if (tLCGrpAppntDB.getInfo() == false) {
					// @@错误处理
					CError.buildErr(this, "没有查到传入的集体投保人客户信息!");

					return false;
				}
				mLCGrpAppntBL.setSchema(tLCGrpAppntDB);
			}

			// 被保险人
			// mLCInsuredBLSet.set((LCInsuredSet)
			// cInputData.getObjectByObjectName("LCInsuredSet",
			// 0));

			if (mark != null && mark.equals("card")) {
				mLCInsuredBL.setSchema((LCInsuredBL) cInputData
						.getObjectByObjectName("LCInsuredBL", 0));
			} else {

				mLCInsuredBL.setSchema((LCInsuredSchema) cInputData
						.getObjectByObjectName("LCInsuredSchema", 0));
			}

			if (!mGrpImportFlag) {
				LCInsuredDB tLCInsuredDB = new LCInsuredDB();
				tLCInsuredDB.setContNo(mLCInsuredBL.getContNo());
				tLCInsuredDB.setInsuredNo(mLCInsuredBL.getInsuredNo());

				if (mark != null && mark.equals("card")) {
					logger.debug("不用被保人信息");

					logger.debug("被保人处理完毕");
				} else {

					if (tLCInsuredDB.getInfo() == false) {

						// @@错误处理
						CError.buildErr(this, "没有查到传入的被保险人客户信息!");

						return false;
					}
					mLCInsuredBL.setSchema(tLCInsuredDB);
					if (mLCInsuredBL.getBirthday() != null) {
						mLCPolBL.setInsuredAppAge(PubFun.calInterval(
								mLCInsuredBL.getBirthday(), mLCPolBL
										.getCValiDate(), "Y"));
						mLCPolBL.setInsuredBirthday(mLCInsuredBL.getBirthday());
					}

				}
			}

			// 连带被保险人
			mLCInsuredRelatedSet.set((LCInsuredRelatedSet) cInputData
					.getObjectByObjectName("LCInsuredRelatedSet", 0));
			// 受益人
			mLCBnfBLSet.set((LCBnfSet) cInputData.getObjectByObjectName(
					"LCBnfSet", 0));

			// 告知信息
			mLCCustomerImpartBLSet.set((LCCustomerImpartSet) cInputData
					.getObjectByObjectName("LCCustomerImpartSet", 0));
			
			// 折扣信息
			mLCDiscountSet.set((LCDiscountSet) cInputData
					.getObjectByObjectName("LCDiscountSet", 0));

			if (mLCCustomerImpartBLSet != null) {
				int impartCount = mLCCustomerImpartBLSet.size();
				for (int i = 1; i <= impartCount; i++) {
					LCCustomerImpartSchema tImpart = (LCCustomerImpartSchema) mLCCustomerImpartBLSet
							.get(i);
					String impartCode = tImpart.getImpartCode();
					String impartVer = tImpart.getImpartVer();
					String customerType = tImpart.getCustomerNoType();
					for (int j = i + 1; j <= impartCount; j++) {
						LCCustomerImpartSchema tImpart1 = (LCCustomerImpartSchema) mLCCustomerImpartBLSet
								.get(j);
						String impartCode1 = tImpart1.getImpartCode();
						String impartVer1 = tImpart1.getImpartVer();
						String customerType1 = tImpart1.getCustomerNoType();
						if (impartCode.equals(impartCode1)
								&& impartVer.equals(impartVer1)
								&& customerType.equals(customerType1)) {
							// @@错误处理
							CError.buildErr(this, "客户告知录入重复!");

							return false;
						}
						// end of if
					}
					// end of for
				}
				// end of for
			} // end of if

			// 特别约定
			mLCSpecBLSet.set((LCSpecSet) cInputData.getObjectByObjectName(
					"LCSpecSet", 0));

			// 一般责任信息(接收传入的单个责任信息)
			if (cInputData.getObjectByObjectName("LCDutySchema", 0) != null) {
				mLCDutyBL.setSchema((LCDutySchema) cInputData
						.getObjectByObjectName("LCDutySchema", 0));
			}

			// 可选责任(接收传入的多个责任信息)
			LCDutySet tLCDutySet = (LCDutySet) cInputData
					.getObjectByObjectName("LCDutySet", 0);
			// 初始化不需要责任的标记为假-该标记针对传入的单个责任做判断
			boolean isNullDuty = false;
			if ((mLCDutyBL.getPayEndDate() == null)
					&& (mLCDutyBL.getPayEndYear() == 0)
					&& (mLCDutyBL.getPayEndYearFlag() == null)
					&& (mLCDutyBL.getGetYearFlag() == null)
					&& (mLCDutyBL.getGetStartType() == null)
					&& (mLCDutyBL.getGetYear() == 0)
					&& (mLCDutyBL.getEndDate() == null)
					&& (mLCDutyBL.getInsuYear() == 0)
					&& (mLCDutyBL.getInsuYearFlag() == null)
					&& (mLCDutyBL.getPayIntv() < 0)) {
				isNullDuty = true;
			}

			// 如果传入的多个责任信息和传入的单个责任信息为空，则需要责任标记为假。
			if ((tLCDutySet == null) && isNullDuty) {
				mNeedDuty = false;
			} else {
				// 如果传入的可选责任集合为空，保存录入的单个责任
				if (tLCDutySet == null) {
					mLCDutyBLSet1.add(mLCDutyBL.getSchema());
				} else {
					// 如果有可选责任，保存可选责任
					for (int z = 1; z <= tLCDutySet.size(); z++) {
						// *************
						if (mGrpImportFlag == false) {
							if (tLCDutySet.get(z).getAscriptionRuleCode() != null) {

								if (!tLCDutySet.get(z).getAscriptionRuleCode()
										.equals("")
										&& !tLCDutySet.get(z)
												.getAscriptionRuleCode()
												.equals("null")) {

									String Ascripsql = null;

									Ascripsql = "select FACTORYTYPE from lcascriptionrulefactory where grpcontno='"
											+ "?grpcontno?"
											+ "' and '"
											+ "?and?"
											+ "' in (ASCRIPTIONRULECODE)";
									SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
									sqlbv19.sql(Ascripsql);
									sqlbv19.put("grpcontno",mLCPolBL.getGrpContNo());
									sqlbv19.put("and",tLCDutySet.get(z)
											.getAscriptionRuleCode());
									ExeSQL AscripExeSQL = new ExeSQL();
									SSRS Ascripssrs = new SSRS();
									Ascripssrs = AscripExeSQL
											.execSQL(sqlbv19);
									if (Ascripssrs.getMaxRow() >= 1) {
										String Ascrip = null;
										Ascrip = Ascripssrs.GetText(1, 1);

										if (Ascrip.equals("000006")
												&& (mLCInsuredBL
														.getJoinCompanyDate() == null || mLCInsuredBL
														.getJoinCompanyDate()
														.equals(""))) {
											CError.buildErr(this,
													"未录入入司日期，请补全！");
											return false;
										}

										// ****************
									} else {
										CError.buildErr(this, "未查询到对应的归属规则！");
										return false;
									}
								}
							}

						}

					}
					mLCDutyBLSet1.set(tLCDutySet);
				}
				mNeedDuty = true;
			}

			if ((mLCPolBL == null) || (mLCInsuredBLSet == null)
					|| (mLCBnfBLSet == null)
					|| (mLCCustomerImpartBLSet == null)
					|| (mLCSpecBLSet == null) || (mLCContSchema == null)) {
				// @@错误处理
				CError
						.buildErr(this,
								"在投保数据接受时没有得到足够的数据，请您确认有：合同，险种,投保人,被保人,受益人,告知信息,特别约定的信息!");
				return false;
			}

			// PQ 现在根据LCCont里的账户信息判断
			// Lis5.3 upgrade get
			if (StrTool.compareString(mLCPolBL.getPayLocation(), "0")
					&& (StrTool.cTrim(mLCContSchema.getBankCode()).equals("") || StrTool
							.cTrim(mLCContSchema.getBankAccNo()).equals(""))) {
				// @@错误处理
				CError.buildErr(this, "收费方式选择了银行自动转账，但是合同信息中没有录入开户行和银行账号信息!");
				return false;
			}

			// 判断保存的类型是
			if (mTransferData.getValueByName("SavePolType") != null) {
				mSavePolType = (String) mTransferData
						.getValueByName("SavePolType");
				if ((mSavePolType == null) || mSavePolType.trim().equals("")) {
					mSavePolType = "0";
				}
			}
			//
			if (mTransferData.getValueByName("EdorType") != null) {
				EdorType = (String) mTransferData.getValueByName("EdorType");
				if ((EdorType == null) || EdorType.trim().equals("")) {
					EdorType = "";
				}
			}

			if (mTransferData.getValueByName("EdorTypeCal") != null) {
				EdorTypeCal = (String) mTransferData
						.getValueByName("EdorTypeCal");
				if ((EdorTypeCal == null) || EdorTypeCal.trim().equals("")) {
					EdorTypeCal = "";
				}
			}
			mLCPolBL.setContNo(mLCContSchema.getContNo());
			mLCPolBL.setPrtNo(mLCContSchema.getPrtNo());
			// mLCPolBL.setproposal(mLCContSchema.getGrpPolNo());
			mLCPolBL.setAgentCode(mLCContSchema.getAgentCode());
			mLCPolBL.setAgentGroup(mLCContSchema.getAgentGroup());
			mLCPolBL.setGrpContNo(mLCContSchema.getGrpContNo());
			mLCPolBL.setContType(mLCContSchema.getContType());
			mLCPolBL.setPolTypeFlag(mLCContSchema.getPolType());
			mLCPolBL.setPayMode(mLCContSchema.getPayMode());
			mLCPolBL.setPaytoDate(mLCContSchema.getPaytoDate());
			// 判断个单时，置团体险种保单号全0
			if (mDealFlag.equals("1")) {
				mLCPolBL.setGrpPolNo(SysConst.ZERONO);
			}

		} else {
			CError.buildErr(this, "传入操作动作标记非法!");
		}

		aCurrency = mLCPolBL.getSchema().getCurrency();//币种信息
		// 得到磁盘投保传来的集体投保单信息，注意，这里是引用，后面对该对象的修改会保留下来
		mLCGrpPolSchema = (LCGrpPolSchema) cInputData.getObjectByObjectName(
				"LCGrpPolSchema", 0);
		mLCGrpContSchema = (LCGrpContSchema) cInputData.getObjectByObjectName(
				"LCGrpContSchema", 0);
		// 得到需要特殊处理的保费项纪录，如果存在这样的纪录(譬如：众悦年金分红)
		mDiskLCPremBLSet.set((LCPremSet) cInputData.getObjectByObjectName(
				"LCPremSet", 0));
		if (mDiskLCPremBLSet != null) {
			if (mDiskLCPremBLSet.size() > 0) {
				noCalFlag = true;

				// 保单传入的保费项置为0--否则在修改的时候会翻倍
				mLCPolBL.setPrem(0);
				mLCPolBL.setStandPrem(0);
			}
		}

		// 当磁盘投保时，会从外部传入该值-后面会判断
		mLMRiskAppSchema = (LMRiskAppSchema) cInputData.getObjectByObjectName(
				"LMRiskAppSchema", 0);
		mLMRiskSchema = (LMRiskSchema) cInputData.getObjectByObjectName(
				"LMRiskSchema", 0);
		mLDGrpSchema = (LDGrpSchema) cInputData.getObjectByObjectName(
				"LDGrpSchema", 0);

		// //如果界面没有录入浮动费率，此时mLCPolBL的FloatRate=0，那么在后面的程序中，
		// 会将mLCPolBL的FloatRate字段置为1
		// //保存浮动费率,录入的保费，保额:用于后面的浮动费率的计算
		// //PQ 浮动费率改变到责任级，需要改变
		// FloatRate = mLCPolBL.getFloatRate();
		//
		// //如果界面录入浮动费率=-1，则计算浮动费率的标记为真
		// if (FloatRate == ConstRate)
		// {
		// autoCalFloatRateFlag = true;
		//
		// //将保单中的该子段设为1，后面描述算法中计算
		// mLCPolBL.setFloatRate(1);
		// }
		// InputPrem = mLCPolBL.getPrem();
		// InputAmnt = mLCPolBL.getAmnt();
		// //浮动费率处理END
		// 获得是否为保单计划变更标志:0 表示是:1 表示否
		if (mTransferData.getValueByName("ChangePlanFlag") != null) {
			// logger.debug("----ChangePlanFlag------"
			// + mTransferData.getValueByName("ChangePlanFlag"));
			mChangePlanFlag = (String) mTransferData
					.getValueByName("ChangePlanFlag");
		}
		//获取通过保障计划传过来的参数信息以便获取GetDutyKind
		mLCContPlanDutyParamSet =(LCContPlanDutyParamSet)cInputData.getObjectByObjectName(
				"LCContPlanDutyParamSet", 0);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 和PrepareSubmitData配合使用，返回准备数据
	 * 
	 * @return
	 */
	public VData getSubmitResult() {
		return mInputData;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		int i;
		int iMax;
		mInputData = new VData();
		try {
			// 不能改变顺序!!

			mInputData.add(mAppntType);
			mInputData.add(mPolType);
			mInputData.add(mLCContSchema);

			logger.debug("--------续涛---加于---2005-09-23-14：50-----开始----------------------------------");
			logger.debug("--ProposalBL.prepareOutputData--判断如果agentcom为false，则将其置为空!!!/n"
							+ mLCPolBL.getAgentCom());
			logger.debug("--ProposalBL.prepareOutputData--判断如果AgentType为false，则将其置为空!!!/n"
							+ mLCPolBL.getAgentType());
			logger.debug("--ProposalBL.prepareOutputData--判断如果AgentCode1为false，则将其置为空!!!/n"
							+ mLCPolBL.getAgentCode1());

			if (StrTool.cTrim(mLCPolBL.getAgentCom()).equals("false")
					|| StrTool.cTrim(mLCPolBL.getAgentCom()).equals("null")) {
				mLCPolBL.setAgentCom("");
			}

			if (StrTool.cTrim(mLCPolBL.getAgentType()).equals("false")
					|| StrTool.cTrim(mLCPolBL.getAgentType()).equals("null")) {
				mLCPolBL.setAgentType("");
			}
			if (StrTool.cTrim(mLCPolBL.getAgentCode1()).equals("false")
					|| StrTool.cTrim(mLCPolBL.getAgentCode1()).equals("null")) {
				mLCPolBL.setAgentCode1("");
			}

			logger.debug("--ProposalBL.prepareOutputData--判断如果agentcom为false，则将其置为空!!!/n"
							+ mLCPolBL.getAgentCom());
			logger.debug("--ProposalBL.prepareOutputData--判断如果AgentType为false，则将其置为空!!!/n"
							+ mLCPolBL.getAgentType());
			logger.debug("--ProposalBL.prepareOutputData--判断如果AgentCode1为false，则将其置为空!!!/n"
							+ mLCPolBL.getAgentCode1());
			logger.debug("--------续涛---加于---2005-09-23-14：50-----结束----------------------------------");

			mInputData.add(mLCPolBL.getSchema());
			mInputData.add(mLCAppntBL.getSchema());
			mInputData.add(mLCGrpAppntBL.getSchema());
			// add by yaory for card
			if (mark == null) {
				map.put(mLCContSchema, "UPDATE");
			}
			mLCPolBL.setUWFlag("9");
			map.put(mLCPolBL.getSchema(), "INSERT");

			if (mLCPolOtherSchema != null) {
				mLCPolOtherSchema.setGrpContNo(mLCPolBL.getGrpContNo());
				mLCPolOtherSchema.setGrpPolNo(mLCPolBL.getGrpPolNo());
				mLCPolOtherSchema.setContNo(mLCPolBL.getContNo());
				mLCPolOtherSchema.setPolNo(mLCPolBL.getPolNo());
				mLCPolOtherSchema.setOperator(mGlobalInput.Operator);
				mLCPolOtherSchema.setMakeDate(theCurrentDate);
				mLCPolOtherSchema.setMakeTime(theCurrentTime);
				mLCPolOtherSchema.setModifyDate(theCurrentDate);
				mLCPolOtherSchema.setModifyTime(theCurrentTime);
				map.put(mLCPolOtherSchema.getSchema(), "INSERT");
				mInputData.add(mLCPolOtherSchema);
			}
			logger.debug("准备数据第一步");
			// if (mLCBankAuthBL != null)
			// {
			// mInputData.add(mLCBankAuthBL.getSchema());
			// }
			//
			// if (mLCBankAccBL != null)
			// {
			// mInputData.add(mLCBankAccBL.getSchema());
			// }

			// LCInsuredSet tLCInsuredSet = new LCInsuredSet();
			// tLCInsuredSet = (LCInsuredSet) mLCInsuredBLSetNew;
			// mInputData.add(tLCInsuredSet);

			LCBnfSet tLCBnfSet = new LCBnfSet();
			tLCBnfSet = (LCBnfSet) mLCBnfBLSetNew;
			mInputData.add(tLCBnfSet);
			if (tLCBnfSet.size() > 0) {
				for (int j = 1; j <= tLCBnfSet.size(); j++) {
					map.put(tLCBnfSet.get(j).getSchema(), "INSERT"); // add
					// by
					// yaory
					// 在cardproposalbl中处理
					// modify
					// by
					// zhangxing
				}
			}
			logger.debug("准备数据第2步");
			LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
			tLCCustomerImpartSet.set(mLCCustomerImpartBLSetNew);
			mInputData.add(mLCCustomerImpartBLSetNew);

			LCSpecSet tLCSpecSet = new LCSpecSet();
			tLCSpecSet = (LCSpecSet) mLCSpecBLSetNew;
			mInputData.add(tLCSpecSet);

			LCPremSet tLCPremSet = new LCPremSet();
			tLCPremSet = (LCPremSet) mLCPremBLSet;
			mInputData.add(tLCPremSet);
			// 由于在问题件修改时有些信息为空，例如这个就是所有用TRY结构必开 edit by yaory
			try {
				// map.put(tLCPremSet.get(1).getSchema(), "INSERT"); //add by
				// yaory
				if (tLCPremSet.size() > 0) {
					for (int j = 1; j <= tLCPremSet.size(); j++) {
						map.put(tLCPremSet.get(j).getSchema(), "INSERT");
					}
				}

			} catch (Exception ex) {

			}
			logger.debug("准备数据第3步");
			LCGetSet tLCGetSet = new LCGetSet();
			tLCGetSet = (LCGetSet) mLCGetBLSet;
			mInputData.add(tLCGetSet);
			// 问题件修改时关于保费信息都为空的 edit by yaory
			try {
				// map.put(mLCGetBLSet.get(1).getSchema(), "INSERT"); //add by
				// yaory
				if (mLCGetBLSet.size() > 0) {
					for (int j = 1; j <= mLCGetBLSet.size(); j++) {
						map.put(mLCGetBLSet.get(j).getSchema(), "INSERT");
					}
				}

				mInputData.add(mLCDutyBLSet);
				// map.put(mLCDutyBLSet.get(1).getSchema(), "INSERT");
				if (mLCDutyBLSet.size() > 0) {
					for (int j = 1; j <= mLCDutyBLSet.size(); j++) {
						map.put(mLCDutyBLSet.get(j).getSchema(), "INSERT");
					}
				}

			} catch (Exception ex) {

			}
			logger.debug("准备数据第4步");
			if (mPolType.equals("2")) {
				mInputData.add(mLCGrpPolSchema);
				mInputData.add(mLCGrpContSchema);
				if (!mOperate.equals("DELETE||PROPOSAL")) {
					// 如果个人标记不为0-即保全标记,则此时保证团单的状态不变
					if (!mLCPolBL.getAppFlag().equals("0")) {
						LCGrpPolDB tempLCGrpPolDB = new LCGrpPolDB();
						tempLCGrpPolDB.setGrpPolNo(mLCGrpPolSchema
								.getGrpPolNo());
						if (tempLCGrpPolDB.getInfo()) {
							mLCGrpPolSchema.setApproveFlag(tempLCGrpPolDB
									.getApproveFlag());
							mLCGrpPolSchema.setUWFlag(tempLCGrpPolDB
									.getUWFlag());
						} else {
							mLCGrpPolSchema.setApproveFlag("9");
							mLCGrpPolSchema.setUWFlag("9");
						}
					}
				}
			}
			mInputData.add(mLCInsuredRelatedSet);
			if (mLCInsuredRelatedSet.size() > 0) {
				for (int j = 1; j <= mLCInsuredRelatedSet.size(); j++) {
					map.put(mLCInsuredRelatedSet.get(j).getSchema(), "INSERT");
				}
			}

			if (mark == null) { // yaory
				mInputData.add(mLDPersonSet);
			}
			if(mLCDiscountSet.size()>0)
				mInputData.add(mLCDiscountSet);
			mInputData.add(mLJSPayPersonSet);
			mInputData.add(mLJSPaySet);
			mInputData.add(mGlobalInput);
			logger.debug("准备数据第5步");
			rResult.clear();
			rResult.add(map);
			rResult.add(mLCPolBL.getSchema());
			logger.debug("准备数据第6步");
		} catch (Exception ex) {
			// @@错误处理
			CError.buildErr(this, "在准备往后层处理所需要的数据时出错。");
			return false;
		}

		// 准备往前面传输的数据
		mResult.clear();
		mResult.add(mLCPolBL.getSchema());
		mResult.add(mLCDutyBLSet);

		return true;
	}

	/**
	 * 准备处理数据
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean PrepareSubmitData(VData cInputData, String cOperate) {
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
			// @@错误处理
			CError.buildErr(this, "数据处理失败ProposalBL-->dealData！");

			return false;
		}

		if (!this.mOperate.equals("DELETE||PROPOSAL")) {
			if (this.mOperate.equals("INSERT||PROPOSAL")) {				
				if (CheckTBField("INSERT") == false) {
					return false;
				}
			} else {
				if (CheckTBField("UPDATE") == false) {
					return false;
				}
			}
		}

		// 准备往后台的数据
		if (prepareOutputData() == false) {
			return false;
		}

		return true;
	}

	/**
	 * 特殊处理，可屏蔽
	 */
	private boolean preSpecCheck() {
		if (mOperate.equals("INSERT||PROPOSAL")
				|| mOperate.equals("UPDATE||PROPOSAL")) {
			// 目前没有
		}

		return true;
	}

	/**
	 * 保费保额计算调用部分
	 * 
	 * @return
	 */
	public boolean pubCal() {
		// 是否传入领取项的标记getDutykind
		String getDutykind = (String) mTransferData
				.getValueByName("GetDutyKind");
		LCGetBL tLCGetBL = new LCGetBL();
		CalBL tCalBL;
		if (mNeedDuty) {

			LMRiskDutySet tLMRiskDutySet = mCRI.findRiskDutyByRiskCode(mLCPolBL
					.getRiskCode());

			if (tLMRiskDutySet == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mCRI.mErrors);
				mCRI.mErrors.clearErrors();

				CError.buildErr(this, "LMRiskDuty表查询失败!");
				return false;
			}

			// 统一费率处理
			for (int i = 1; i <= mLCDutyBLSet1.size(); i++) {
				// 大BUG!!!!!!!!!!!!!!
				LCDutySchema tLCDutySchema = mLCDutyBLSet1.get(i);
				String dutyCode = tLMRiskDutySet.get(i).getDutyCode();
				//获取GetDutyKind
				if(mLCContPlanDutyParamSet==null||mLCContPlanDutyParamSet.size()<=0){
					//不是通过保障计划保存个人信息
				}else{
					LCContPlanDutyParamDB ttLCContPlanDutyParamDB = new LCContPlanDutyParamDB();
					//LCContPlanDutyParamDB tLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
					ttLCContPlanDutyParamDB.setDutyCode(dutyCode);
					ttLCContPlanDutyParamDB.setRiskCode(mLCContPlanDutyParamSet.get(i).getRiskCode());
					ttLCContPlanDutyParamDB.setGrpContNo(mLCContPlanDutyParamSet.get(i).getGrpContNo());
					ttLCContPlanDutyParamDB.setContPlanCode(mLCContPlanDutyParamSet.get(i).getContPlanCode());
					ttLCContPlanDutyParamDB.setMainRiskCode(mLCContPlanDutyParamSet.get(i).getMainRiskCode());
					ttLCContPlanDutyParamDB.setCalFactor(mLCContPlanDutyParamSet.get(i).getCalFactor());
					ttLCContPlanDutyParamDB.setPlanType(mLCContPlanDutyParamSet.get(i).getPlanType());
					if(!ttLCContPlanDutyParamDB.getInfo()){
						CError.buildErr(this, "查询计划参数表错误！");
					}else{
						getDutykind =ttLCContPlanDutyParamDB.getCalFactorValue();
					}
				}
				// String calMode = "";

				// 如果界面没有录入浮动费率，此时mLCDutySchema的FloatRate=0，那么在后面的程序中，
				// 会将mLCPolBL的FloatRate字段置为1
				// 保存浮动费率,录入的保费，保额:用于后面的浮动费率的计算
				// FloatRate = tLCDutySchema.getFloatRate();

				InputPrem = tLCDutySchema.getPrem();
				InputAmnt = tLCDutySchema.getAmnt();
				if (mLCDutyBLSet1.size() == 1 && InputPrem == 0.0
						&& InputAmnt == 0.0) {
					InputPrem = mLCPolBL.getPrem();
					InputAmnt = mLCPolBL.getAmnt();
				}
				// 保存计算方向
				String calRule = tLCDutySchema.getCalRule();
				if (calRule == null) {
					// 默认为表定费率
					tLCDutySchema.setCalRule("0");
				}
				// 如果是统一费率，需要去默认值或者保险计划里取出统一费率的值,算出保费保额
				else if (calRule.equals("1")) {
					
					
					
					LCContPlanDutyParamDB tLCContPlanDutyParamDB = new LCContPlanDutyParamDB();
					LCContPlanDutyParamSet tLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
					tLCContPlanDutyParamDB
							.setGrpContNo(mLCPolBL.getGrpContNo());
					tLCContPlanDutyParamDB.setDutyCode(dutyCode);
					tLCContPlanDutyParamDB.setRiskCode(mLCPolBL.getRiskCode());
					tLCContPlanDutyParamDB.setMainRiskCode(mainLCPolBL
							.getRiskCode());
					tLCContPlanDutyParamDB.setCalFactor("FloatRate");
					if (StrTool.compareString(mLCInsuredBL.getContPlanCode(),
							"")) {
						tLCContPlanDutyParamDB.setContPlanCode("00");
					} else {
						tLCContPlanDutyParamDB.setContPlanCode(mLCInsuredBL
								.getContPlanCode());
					}
					tLCContPlanDutyParamSet = tLCContPlanDutyParamDB.query();
					if (tLCContPlanDutyParamDB.mErrors.needDealError()) {
						// @@错误处理
						CError.buildErr(this, "LCContPlanDutyParam查询失败!");
						return false;
					}
					// 如果在计划中没有取到,需要去默认值里去
					if (tLCContPlanDutyParamSet.size() <= 0
							|| (!StrTool.compareString(mLCInsuredBL
									.getContPlanCode(), "") && StrTool
									.compareString(tLCContPlanDutyParamSet.get(
											1).getCalFactorValue(), ""))) {
						tLCContPlanDutyParamDB.setContPlanCode("00");
						tLCContPlanDutyParamSet = tLCContPlanDutyParamDB
								.query();
						if (tLCContPlanDutyParamDB.mErrors.needDealError()) {
							// @@错误处理
							CError.buildErr(this, "LCContPlanDutyParam查询失败!");
							return false;
						}

					}
					double oneFloatRate = 0;
					if (tLCContPlanDutyParamSet.size() <= 0
							|| StrTool.compareString(tLCContPlanDutyParamSet
									.get(1).getCalFactorValue(), "")) {
						// @@错误处理
						//tongmeng 2009-03-31 modify
						//如果是约定费率.但是没有取到默认值.那么直接用界面录入的数值
//						CError.buildErr(this,
//								"计算方向为统一费率，但是在保险计划于默认值中均未查到统一费率值!");
//						return false;
						oneFloatRate = tLCDutySchema.getFloatRate();
						
					}
					else
					{

					// 取出统一费率
					oneFloatRate = Double
							.parseDouble(tLCContPlanDutyParamSet.get(1)
									.getCalFactorValue());
					}
					
					if(oneFloatRate<=0)
					{
						CError.buildErr(this,"统一费率录入有误!");
						return false;
					}
					
					// 如果界面上录入保费：
					if ((InputPrem > 0) && (InputAmnt > 0)) {
						if (InputPrem != Double.parseDouble(mDecimalFormat
								.format(InputAmnt * oneFloatRate))) {
							CError.buildErr(this, "您录入的保费保额（" + InputPrem + ","
									+ InputAmnt + "）与统一费率（" + oneFloatRate
									+ "）不符合！");
							return false;
						}
					} 
					
					//2008-09-09 zhangzheng 当计算方向选择1--约定费率时,是录入的费率,此处不需要再将FloatRate置默认值1
//					else if ((InputPrem > 0)) {
//						InputAmnt = Double.parseDouble(mDecimalFormat
//								.format(InputPrem / oneFloatRate));
//					} else if ((InputAmnt > 0)) {
//						InputPrem = Double.parseDouble(mDecimalFormat
//								.format(InputAmnt * oneFloatRate));
//					}
					// autoCalFloatRateFlag = true;

					//tLCDutySchema.setFloatRate(1);
					
					tLCDutySchema.setPrem(InputPrem);
					tLCDutySchema.setStandPrem(InputPrem);
					tLCDutySchema.setAmnt(InputAmnt);
					if (mLCDutyBLSet1.size() == 1) {
						mLCPolBL.setPrem(InputPrem);
						mLCPolBL.setStandPrem(InputPrem);
						mLCPolBL.setAmnt(InputAmnt);
					}

				}

			}
			// 需要传入责任计算
			if (StrTool.cTrim(getDutykind).equals("")) {
				// 传入LCPol的和页面录入的责任信息（单个或可选责任）数据,保存在tCalBL对象的内部成员中，后续将用到
				tCalBL = new CalBL(mLCPolBL, mLCDutyBLSet1, mTransferData,
						EdorType);
			} else {
				// 传入投保单和领取项和责任项的数据
				tLCGetBL.setGetDutyKind(getDutykind);

				LCGetBLSet tLCGetBLSet = new LCGetBLSet();
				tLCGetBLSet.add(tLCGetBL);
				logger.debug("计算开始2$$$$$$$$$$$");
				// mLCPolBL-传入的投保单信息 ， mLCDutyBLSet1-传入的责任信息 ，
				// tLCGetBLSet-保存传入的getDutykind字段，其它为空。
				tCalBL = new CalBL(mLCPolBL, mLCDutyBLSet1, tLCGetBLSet,
						EdorType);
			}
		} else {
			// 不传入责任计算
			if (StrTool.cTrim(getDutykind).equals("")) {
				// 只传入LCPol的数据
				logger.debug("计算开始3$$$$$$$$$$$" + EdorType);
				tCalBL = new CalBL(mLCPolBL, EdorType);

			} else {
				// 传入LCPol和LCGet的数据
				tLCGetBL.setGetDutyKind(getDutykind);

				LCGetBLSet tLCGetBLSet = new LCGetBLSet();
				tLCGetBLSet.add(tLCGetBL);
				logger.debug("计算开始1$$$$$$$$$$$");
				tCalBL = new CalBL(mLCPolBL, tLCGetBLSet, EdorType);
			}
		}
		// 传入其他信息
		if (mLCPolOtherSchema != null) {
			tCalBL.SetLCPolOther(mLCPolOtherSchema);
		}
		//
		tCalBL.setNoCalFalg(noCalFlag); // 将是否需要计算的标记传入计算类中
		if (!noCalFlag) { // 如果需要计算
			// logger.debug("before tCalBL.calPol()");

			if (tCalBL.calPol() == false) { // 前面tCalBL的成员变量保存了传入的数据后，
				// 这里计算将用到这些成员变量
				// @@错误处理
				CError
						.buildErr(
								this,
								tCalBL.mErrors.getFirstError()
										+ "(如果没有具体错误原因，请依次检查：1 交费方式和交费期间（或保险期间）是否匹配 2 保费，保额是否输入 3 相关要素（譬如年龄、被保人工资等）是否超过费率计算范围 4 职业代码是否和计算相关) ");
				return false;
			}
		} else {
			//tongmeng 2010-11-29 modify
			if (tCalBL.calPol2(mDiskLCPremBLSet) == false) { // 前面tCalBL的成员变量保存了传入的数据后
				// ，
				// 这里计算将用到这些成员变量
				// @@错误处理
				CError
						.buildErr(
								this,
								tCalBL.mErrors.getFirstError()
										+ "(如果没有具体错误原因，请依次检查：1 交费方式和交费期间（或保险期间）是否匹配 2 保费，保额是否输入 3 相关要素（譬如年龄、被保人工资等）是否超过费率计算范围 4 职业代码是否和计算相关) ");
				return false;
			}
		}

		// 取出计算的结果
		mLCPolBL.setSchema(tCalBL.getLCPol().getSchema());

		mLCPremBLSet = tCalBL.getLCPrem(); // 得到的保费项集合不包括加费的保费项，所以在后面处理
		mLCGetBLSet = tCalBL.getLCGet();
		mLCDutyBLSet = tCalBL.getLCDuty();
		mLCPremBLSet.setPolNo(mLCPolBL.getPolNo());
		mLCGetBLSet.setPolNo(mLCPolBL.getPolNo());

		if (mOperate.equals("INSERT||PROPOSAL")) {
			mLCPolBL.setDefaultFields();
		}
		
		for (int i = 1; i <= mLCPremBLSet.size(); i++) {
			mLCPremBLSet.get(i).setOperator(mGlobalInput.Operator);
			mLCPremBLSet.get(i).setMakeDate(theCurrentDate);
			mLCPremBLSet.get(i).setMakeTime(theCurrentTime);
			mLCPremBLSet.get(i).setModifyDate(theCurrentDate);
			mLCPremBLSet.get(i).setModifyTime(theCurrentTime);
			if(mLCPremBLSet.get(i).getCurrency()==null||mLCPremBLSet.get(i).getCurrency().equals(""))
			{
				mLCPremBLSet.get(i).setCurrency(aCurrency);
			}
		}
		for (int i = 1; i <= mLCGetBLSet.size(); i++) {
			mLCGetBLSet.get(i).setOperator(mGlobalInput.Operator);
			mLCGetBLSet.get(i).setMakeDate(theCurrentDate);
			mLCGetBLSet.get(i).setMakeTime(theCurrentTime);
			mLCGetBLSet.get(i).setModifyDate(theCurrentDate);
			mLCGetBLSet.get(i).setModifyTime(theCurrentTime);
			mLCGetBLSet.get(i).setCurrency(aCurrency);
		}

		// 将加费的保费项查询出来,并加入到保费项集合中
		String strSql = "select * from lcprem where polno='"
				+ "?grpcontno?" + "' and PayPlanCode like '000000%'";
		SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
		sqlbv20.sql(strSql);
		sqlbv20.put("grpcontno",mLCPolBL.getPolNo());
		LCPremDB tempLCPremDB = new LCPremDB();
		LCPremSet tempLCPremSet = new LCPremSet();
		tempLCPremSet = tempLCPremDB.executeQuery(sqlbv20);
		mLCPremBLSet.add(tempLCPremSet);

		// //处理浮动费率--houzm add
		// if (!dealFloatRate(mLCPolBL, mLCPremBLSet, mLCGetBLSet,
		// mLCDutyBLSet))
		// {
		// return false;
		// }

		// 保费项
		/** @todo 保费项表修改 */
		// for (int j = 1; j <= mLCPremBLSet.size(); j++) {
		// LCPremSchema tLCPremSchema = (LCPremSchema) mLCPremBLSet.get(j);
		// if (!tLCPremSchema.getPayPlanCode().substring(0, 6).equals("000000"))
		// {
		// tLCPremSchema.setPrem(tLCPremSchema.getStandPrem());
		// }
		// mLCPremBLSet.set(j, tLCPremSchema);
		// }
		// 领取项
		for (int j = 1; j <= mLCGetBLSet.size(); j++) {
			LCGetSchema tLCGetSchema = (LCGetSchema) mLCGetBLSet.get(j);
			mLCGetBLSet.set(j, tLCGetSchema);
		}

		double sumPremLCPol = 0; // 保单中的实际保费和
		String tTransDate = this.mLCPolBL.getCValiDate()==null?this.mLCPolBL.getPolApplyDate():this.mLCPolBL.getCValiDate();

		// 责任项
		for (int j = 1; j <= mLCDutyBLSet.size(); j++) {
			LCDutySchema tLCDutySchema = (LCDutySchema) mLCDutyBLSet.get(j);

			double sumPremDuty = 0;
			for (int m = 1; m <= mLCPremBLSet.size(); m++) {
				if (mLCPremBLSet.get(m).getDutyCode().equals(
						tLCDutySchema.getDutyCode())&&mLCPolBL.getCValiDate().equals(mLCPremBLSet.get(m).getPayStartDate())) {
					// 将该责任下的所有保费项累加
					/** @todo 修改prem存实际保费 */
					// sumPremDuty = sumPremDuty
					// + mLCPremBLSet.get(m).getStandPrem();
					
					double newSumPrem = 0;
					/*
					 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
					 * 如果返回值小于0，则有错误数据
					 * orgitype 传入的币种
					 * destype 需要转换的币种
					 * transdate 转换日期
					 * amnt 转换金额
					 */
					String tCurrency = mLCPremBLSet.get(m).getCurrency();
					
					newSumPrem = this.mLDExch.toOtherCur(tCurrency, tLCDutySchema.getCurrency(), tTransDate, mLCPremBLSet.get(m).getPrem());

							
					sumPremDuty = PubFun.round(sumPremDuty,2) + PubFun.round(newSumPrem,2);

				}
			}
			tLCDutySchema.setPrem(sumPremDuty);
			tLCDutySchema.setFirstPayDate(mLCPolBL.getCValiDate());
			tLCDutySchema.setModifyDate(theCurrentDate);
			tLCDutySchema.setModifyTime(theCurrentTime);
			tLCDutySchema.setCurrency(aCurrency);

			mLCDutyBLSet.set(j, tLCDutySchema);
			sumPremLCPol = PubFun.round(sumPremLCPol,2) + PubFun.round(sumPremDuty,2);
		}
		mLCPolBL.setPrem(sumPremLCPol); // 保存实际保费

		// 从责任表中取出第一条数据填充LCPol中的部分字段
		LCDutySchema tLCDutySchema1 = new LCDutySchema();
		// logger.debug("mLCDutyBLSet"+mLCDutyBLSet.get(1));
		tLCDutySchema1.setSchema((LCDutySchema) mLCDutyBLSet.get(1));
		// logger.debug("lcpol payintv:" + mLCPolBL.getPayIntv());
		// logger.debug("lcduty payintv:" + tLCDutySchema1.getPayIntv());

		if (mLCPolBL.getPayIntv() == 0) {
			mLCPolBL.setPayIntv(tLCDutySchema1.getPayIntv());
		}
		if (mLCPolBL.getInsuYear() == 0) {
			mLCPolBL.setInsuYear(tLCDutySchema1.getInsuYear());
		}
		if (mLCPolBL.getPayEndYear() == 0) {
			mLCPolBL.setPayEndYear(tLCDutySchema1.getPayEndYear());
		}
		if (mLCPolBL.getGetYear() == 0) {
			mLCPolBL.setGetYear(tLCDutySchema1.getGetYear());
		}
		if (mLCPolBL.getAcciYear() == 0) {
			mLCPolBL.setAcciYear(tLCDutySchema1.getAcciYear());
		}
		if (((mLCPolBL.getInsuYearFlag() == null) || ("".equals(mLCPolBL
				.getInsuYearFlag())))
				&& (tLCDutySchema1.getInsuYearFlag() != null)) {
			mLCPolBL.setInsuYearFlag(tLCDutySchema1.getInsuYearFlag());
		}
		if (((mLCPolBL.getGetStartType() == null) || ("".equals(mLCPolBL
				.getGetStartType())))
				&& (tLCDutySchema1.getGetStartType() != null)) {
			mLCPolBL.setGetStartType(tLCDutySchema1.getGetStartType());
		}
		if (((mLCPolBL.getGetYearFlag() == null) || "".equals(mLCPolBL
				.getGetYearFlag()))
				&& (tLCDutySchema1.getGetYearFlag() != null)) {
			mLCPolBL.setGetYearFlag(tLCDutySchema1.getGetYearFlag());
		}
		if ((mLCPolBL.getPayEndYearFlag() == null || "".equals(mLCPolBL
				.getPayEndYearFlag()))
				&& (tLCDutySchema1.getPayEndYearFlag() != null)) {
			mLCPolBL.setPayEndYearFlag(tLCDutySchema1.getPayEndYearFlag());
		}
		if ((mLCPolBL.getAcciYearFlag() == null || "".equals(mLCPolBL
				.getAcciYearFlag()))
				&& (tLCDutySchema1.getAcciYearFlag() != null)) {
			mLCPolBL.setAcciYearFlag(tLCDutySchema1.getAcciYearFlag());
		}

		return true;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		// 合并完
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据操作校验
		// 不做合并
		if (!checkData()) {
			return false;
		}
		// 特殊校验处理针对险种－对MS用，可单独屏蔽不用,不影响后面
		if (!preSpecCheck()) {
			return false;
		}

		// 进行险种保存业务逻辑处理
		// 合并完
		if (!dealData()) {
			// @@错误处理
			return false;
		}

		if (!this.mOperate.equals("DELETE||PROPOSAL")) {
			if (this.mOperate.equals("INSERT||PROPOSAL")) {
            	String KDCheckFlag=(String)mTransferData.getValueByName("KDCheckFlag");
            	logger.debug("自助卡单是否校验标志:"+KDCheckFlag);
            	//当KDCheckFlag不为null时,表明是走自助卡单核销程序进行核销,不进行投保规则校验,否则表明是走核心柜面出单,必须要走核保规则校验

            	//modify by duanyh 2009-02-19 银保通校验放在数据提交之后
            	if(KDCheckFlag == null && YBTFlag == null && CheckTBField("INSERT") == false)
            	{
            		return false;
            	} 
			} else {
				if (!CheckTBField("UPDATE")) {
					return false;
				}
			}
		}

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}
		// add by yaory
		if (mark != null) {
			if (mark.equals("card")) {
				logger.debug("in proposalbl 是卡单！");
				return true;
			}
		}
		ProposalBLS tProposalBLS = new ProposalBLS();
		tProposalBLS.submitData(mInputData, cOperate);

		// 如果有需要处理的错误，则返回
		if (tProposalBLS.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tProposalBLS.mErrors);

			CError.buildErr(this, "数据提交失败！");

			return false;
		}

		// logger.debug("TakeBackCertifyFalg:"+TakeBackCertifyFalg);
		if (TakeBackCertifyFalg) { // 如果回收单证标记为真(针对个人投保单)
			try {
				// 单证回收
				PubCertifyTakeBack tPubCertifyTakeBack = new PubCertifyTakeBack();
				if (tPubCertifyTakeBack
						.CheckNewType(tPubCertifyTakeBack.CERTIFY_CheckNo2)) { // 如果需要单证回收
					String operator = mGlobalInput.Operator;
					String CertifyType = "";

					if (mLCPolBL.getPrtNo().substring(2, 4).equals("15")) { // 银行险的编码15
						// ，
						// 个险11
						// ，
						// 团险12
						CertifyType = tPubCertifyTakeBack.CERTIFY_ProposalBank;
					} else {
						if (mLCPolBL.getPrtNo().substring(2, 4).equals("14")) {
							CertifyType = tPubCertifyTakeBack.CERTIFY_ProSpec;
						} else if (mLCPolBL.getPrtNo().substring(2, 4).equals(
								"16")) {
							CertifyType = tPubCertifyTakeBack.CERTIFY_ProSimple;
						} else {
							CertifyType = tPubCertifyTakeBack.CERTIFY_Proposal;
						}
					}

					// logger.debug("CertifyType:"+CertifyType);
					if (!tPubCertifyTakeBack.CertifyTakeBack_A(mLCPolBL
							.getPrtNo(), mLCPolBL.getPrtNo(), CertifyType,
							mGlobalInput)) {
						logger.debug("单证回收错误（个人投保单）:"
								+ mLCPolBL.getPrtNo());
						logger.debug("错误原因："
								+ tPubCertifyTakeBack.mErrors.getFirstError()
										.toString());

						// 保存错误信息
					}
				}
			} catch (Exception e) {
				mErrors.addOneError(e.toString());
			}
		}
		
		//add by duanyh 银保通在数据提交之后再校验
		if(YBTFlag != null && YBTFlag.equals("1"))
		{
			VData tVData = new VData();
			CheckFieldCom tCheckFieldCom = new CheckFieldCom();
			String strMsg = "";
			boolean MsgFlag = false;

			// 计算要素
			FieldCarrier tFieldCarrier = new FieldCarrier();			
			tFieldCarrier.setRiskCode(mLCPolBL.getRiskCode()); // 险种编码
			tFieldCarrier.setContNo(mLCContSchema.getContNo()); // 合同号	
			tFieldCarrier.setSellType(mLCContSchema.getSellType()); 	
			tVData.add(tFieldCarrier);
			LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
			tLMCheckFieldSchema.setRiskCode(mLCPolBL.getRiskCode());
			tLMCheckFieldSchema.setFieldName("YBTINSERT"); // 投保
			tVData.add(tLMCheckFieldSchema);
			if (tCheckFieldCom.CheckField(tVData) == false) 
			{
				logger.debug("hehehehehe");
				this.mErrors.copyAllErrors(tCheckFieldCom.mErrors);
				return false;
			} 
			else 
			{
				LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom.GetCheckFieldSet();
				for (int n = 1; n <= mLMCheckFieldSet.size(); n++) 
				{
					LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);
					if ((tField.getReturnValiFlag() != null)&& tField.getReturnValiFlag().equals("N")) 
					{
						if ((tField.getMsgFlag() != null)&&tField.getMsgFlag().equals("Y")) 
						{
							MsgFlag = true;
							strMsg = strMsg + tField.getMsg();
							break;
						}
					}
				}
				if (MsgFlag == true) 
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "ProposalBL";
					tError.functionName = "submitData";
					tError.errorMessage = strMsg;
					this.mErrors.addOneError(tError);
					return false;
				}
			}
		}
		mInputData = null;

		return true;
	}

}
