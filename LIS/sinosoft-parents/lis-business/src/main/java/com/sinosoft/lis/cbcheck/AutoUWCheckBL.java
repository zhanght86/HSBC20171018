package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Calendar;
import com.sinosoft.lis.pubfun.FDate;
import java.util.Date;
import java.util.GregorianCalendar;
import com.sinosoft.lis.pubfun.Calculator;

import com.sinosoft.ibrms.RuleUI;
import com.sinosoft.ibrms.SQLTaskResult;
import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMBnf;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMMainPol;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.ibrms.bom.BOMSubPol;
import com.sinosoft.ibrms.bom.BOMSubPol2;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCCUWErrorDB;
import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCCUWSubDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCIndUWErrorDB;
import com.sinosoft.lis.db.LCIndUWMasterDB;
import com.sinosoft.lis.db.LCIndUWSubDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCCUWErrorSchema;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCCUWSubSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCIndUWErrorSchema;
import com.sinosoft.lis.schema.LCIndUWMasterSchema;
import com.sinosoft.lis.schema.LCIndUWSubSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.tb.GlobalCheckSpot;
import com.sinosoft.lis.tb.PrepareBOMUWBL;
import com.sinosoft.lis.tb.ProductSaleControlBL;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCUWErrorSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCIndUWErrorSet;
import com.sinosoft.lis.vschema.LCIndUWMasterSet;
import com.sinosoft.lis.vschema.LCIndUWSubSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.vschema.LDCode1Set;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>
 * Title: 自动核保校验业务类
 * </p>
 * 
 * <p>
 * Description: 自动核保校验业务类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HL
 * @version 6.0
 */
public class AutoUWCheckBL {
private static Logger logger = Logger.getLogger(AutoUWCheckBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private static GlobalCheckSpot mGlobalCheckSpot = GlobalCheckSpot.getInstance();
	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ExeSQL mExeSQL = new ExeSQL();
	private String theDate = "";

	private List tPolList = new ArrayList();//存放险种自核不通过信息的list
	private List tContList = new ArrayList();//存放合同自核不通过提示信息的list
	private List tInsList = new ArrayList();//存放被保人自核不通过提示信息的list
	
	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mAllPolPassFlag = "9";
	/** 业务数据操作字符串 */
	// private String mMissionID;
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private String mPolPassFlag = "0"; // 险种通过标记 初始为未核保
	private String mContPassFlag = "0"; // 合同通过标记 初始为未核保
	private String mInsPassFlag = "0";
	private String mUWGrade = "";//规则引擎接口返回的核保级别
	private String mContNo = "";
	private String mPContNo = "";
	private String mOldPolNo = "";//获得的保单号码 
	private boolean FirstUW = true;
	private String ProductSaleFlag = "0";//产品上市停售规则标记，如果不符合规则则置为1
	private int LCCUWNO=0;
	private int LCUWNO=0;
	private int LCINDUWNO=0;
	private int LCBatchNo=0;
	private int LCCBatchNo=0;
	private int LCIndBatchNo=0;
	private boolean mISAdoptAppnt = false;
	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCInsuredSet mAllInsuredSet = new LCInsuredSet();
	private int mAppntAge = 0;
	private CalBase mCalBase = new CalBase();
	private String secondPeople = "";
	private String fisrtPeople = "";
	private String mCalCode; // 计算编码
	private String UWCode; // add by ml 2006-02-23 for function(CheckPol2)
	private String PassFlag; // add by ml 2006-02-23 for function(CheckPol2)
	private boolean mISPE = false;
	private String mValue;
	/** 2008-11-20，自动核保改调用保险业务规则管理功能来完成自动核保*/
	private String mAutoUWFlag = "";    //自核通过标识，Y-通过，N-不通过
	private String mPEType = "";
	private MMap updatemap = new MMap();
	private List mBomList = new ArrayList();
	private MMap feeMap = new MMap();//存放暂交费，应收数据
	/* 转换精确位数的对象 */
	private String FORMATMODOL = "0.00"; // 保费保额计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();
	private Hashtable m_custPEInfo = new Hashtable(); // 保存客户体检套数信息
	private RuleUI mRuleUI = new RuleUI();//
	
	/** 初始化保险业务规则管理所需要的相应的数据*/
	
	/** 合同核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCCUWMasterSet = new LCCUWMasterSet();

	/** 合同核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();

	/** 合同核保错误信息表 */
	private LCCUWErrorSet mLCCUWErrorSet = new LCCUWErrorSet();
	private LCCUWErrorSet mAllLCCUWErrorSet = new LCCUWErrorSet();

	/** 各险种核保主表 */
	private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
	private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();

	/** 各险种核保子表 */
	private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
	private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();

	/** 被保人核保主表*/
	private LCIndUWMasterSet mLCIndUWMasterSet = new LCIndUWMasterSet();
	
	/** 被保人核保子表*/
	private LCIndUWSubSet mLCIndUWSubSet = new LCIndUWSubSet();
	
	/** 被保人核保错误信息表*/
	private LCIndUWErrorSet mLCIndUWErrorSet = new LCIndUWErrorSet();
	
	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();

	private LCAppntSchema mLCAppntSchema = new LCAppntSchema();
	
	/** 发送拒保通知书 */
	LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private String hierarhy = ""; // 核保级别 add by yaory
	private String reDistribute = ""; // 分保标志 add by yaory
	private double LSumDangerAmnt = 0; // 同一被保险人下寿险类的累计危险保额add by yaory
	private double DSumDangerAmnt = 0; // 同一被保险人下重大疾病类的累计危险保额add by yaory
	private double ASumDangerAmnt = 0; // 同一被保险人下人身意外伤害类的累计危险保额add by yaory
	private double MSumDangerAmnt = 0; // 同一被保险人下人身意外医疗类的累计危险保额add by yaory
	private double SSumDangerAmnt = 0; // 同一被保险人下住院医疗类的累计危险保额add by yaory
	private double SSumDieAmnt = 0; // 同一被保险人下累计身故风险保额
	private double AllSumAmnt = 0;
	private String mBankInsu = "0";
	private int insuredCount=0;
	private boolean HasAddFreeFlag = false;
	PrepareBOMUWBL tPrepareBOMUWBL = new PrepareBOMUWBL();
	private String locCurrency = "";//本币信息
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	/** 屏蔽规则引擎新添变量 */
	private String mLifeFlag = "0";
//	private int theCount = 0;
	public AutoUWCheckBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据
	 * @param cOperate
	 *            数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验是否有未打印的体检通知书
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");
		
		
		
		// 进行业务处理
		if (!dealData(mLCContSchema)) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {			
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据
		if (cOperate.equals("submit")) {
			PubSubmit tSubmit = new PubSubmit();
			if (!tSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "LCInsuredUWBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		
	

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            输入数据
	 * @return boolean
	 */
	private boolean dealData(LCContSchema tLCContSchema) {
		/** 将该合同下的所有信息查询出来,并将相应的数据转储到相应的BOM中*/
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = null;

		mContNo = tLCContSchema.getContNo(); // 获得保单号
		
		 // 日志监控,过程监控
		if(mGlobalInput.LogID!=null&&!mGlobalInput.LogID.equals(""))
		{
			PubFun.logTrack (mGlobalInput,mContNo,"投保单"+mContNo+"核保开始");
		}
		mPContNo = tLCContSchema.getProposalContNo();
		tLCPolDB.setContNo(mContNo);
		mAllLCPolSet = tLCPolDB.query();
		int polCount = mAllLCPolSet.size();
		
		/** 
		 * 自核前首先校验险种是否符合产品上市停售规则，
		 * 如不符合不调用规则引擎接口，ProductSaleFlag置为1 
		 * 直接流转到销售限制节点
		 * */
		VData PolVData = new VData();
		PolVData.add(mAllLCPolSet);
		ProductSaleControlBL tProductSaleControlBL = new ProductSaleControlBL();
		if(!tProductSaleControlBL.submitData(PolVData, "")){
			int tCount = tProductSaleControlBL.mErrors.getErrorCount();
			for(int i=1; i <= tCount;i++){
				CError.buildErr(this, tProductSaleControlBL.mErrors.getFirstError().toString());
			}
		}
		VData Result = tProductSaleControlBL.getResult();
		if(Result.size()>0){
			for(int i=0;i<Result.size();i++){
				String tRe = (String)Result.get(i);
				logger.debug(tRe);
			}
			ProductSaleFlag="1";
			CError.buildErr(this, (String)Result.get(0));
			return false;
		}else{
			//LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mContNo);
			if(!tLCAppntDB.getInfo()){
				CError.buildErr(this, "查询投保人信息失败！");
				return false;
			}
			
			//LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(mContNo);
			mAllInsuredSet = tLCInsuredDB.query();
			
			//tongmeng 2009-04-28 add
			//对于投保人豁免险121301 需要复制投保人数据作为被保人
			//借用grpcontno记录是否是复制的被保人			
			LCInsuredSet tFreeLCInsuredSet = new LCInsuredSet();
			LCInsuredDB tFreeLCInsuredDB = new LCInsuredDB();
//			String tSQL_Appnt = "select * from lcinsured a where  a.contno='"+"?contno?"+"' and "+"?size?"+"<=1 " 
//			                  + " and exists (select '1' from lcpol where contno=a.contno and riskcode='121301' ) ";
//			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
//			sqlbv.sql(tSQL_Appnt);
//			sqlbv.put("contno", mContNo);
//			sqlbv.put("size", mAllInsuredSet.size());
//			tFreeLCInsuredSet = tFreeLCInsuredDB.executeQuery(sqlbv);
//			if(tFreeLCInsuredSet.size()>0)
//			{
//				LCInsuredSchema tempLCInsuredSchema = tFreeLCInsuredSet.get(1);
//				tempLCInsuredSchema.setGrpContNo("FREE");
//				tempLCInsuredSchema.setInsuredNo(tLCAppntDB.getAppntNo());
//				tempLCInsuredSchema.setRelationToAppnt("00");
//				tempLCInsuredSchema.setName(tLCAppntDB.getAppntName());
//				tempLCInsuredSchema.setSex(tLCAppntDB.getAppntSex());
//				tempLCInsuredSchema.setBirthday(tLCAppntDB.getAppntBirthday());
//				tempLCInsuredSchema.setIDType(tLCAppntDB.getIDType());
//				tempLCInsuredSchema.setIDNo(tLCAppntDB.getIDNo());
//				tempLCInsuredSchema.setNativePlace(tLCAppntDB.getNativePlace());
//				tempLCInsuredSchema.setNationality(tLCAppntDB.getNationality());
//				tempLCInsuredSchema.setRgtAddress(tLCAppntDB.getRgtAddress());
//				tempLCInsuredSchema.setMarriage(tLCAppntDB.getMarriage());
//				tempLCInsuredSchema.setMarriageDate(tLCAppntDB.getMarriageDate());
//				tempLCInsuredSchema.setHealth(tLCAppntDB.getHealth());
//				tempLCInsuredSchema.setStature(tLCAppntDB.getStature());
//				tempLCInsuredSchema.setAvoirdupois(tLCAppntDB.getAvoirdupois());
//				tempLCInsuredSchema.setDegree(tLCAppntDB.getDegree());
//				tempLCInsuredSchema.setCreditGrade(tLCAppntDB.getCreditGrade());
//				tempLCInsuredSchema.setBankCode(tLCAppntDB.getBankCode());
//				tempLCInsuredSchema.setBankAccNo(tLCAppntDB.getBankAccNo());
//				tempLCInsuredSchema.setAccName(tLCAppntDB.getAccName());
//				tempLCInsuredSchema.setJoinCompanyDate(tLCAppntDB.getJoinCompanyDate());
//				tempLCInsuredSchema.setStartWorkDate(tLCAppntDB.getStartWorkDate());
//				tempLCInsuredSchema.setPosition(tLCAppntDB.getPosition());
//				tempLCInsuredSchema.setSalary(tLCAppntDB.getSalary());
//				tempLCInsuredSchema.setOccupationCode(tLCAppntDB.getOccupationCode());
//				tempLCInsuredSchema.setOccupationType(tLCAppntDB.getOccupationType());
//				tempLCInsuredSchema.setWorkType(tLCAppntDB.getWorkType());
//				tempLCInsuredSchema.setPluralityType(tLCAppntDB.getPluralityType());
//				tempLCInsuredSchema.setSmokeFlag(tLCAppntDB.getSmokeFlag());
//				tempLCInsuredSchema.setLicense(tLCAppntDB.getLicense());
//				tempLCInsuredSchema.setLicenseType(tLCAppntDB.getLicenseType());
//				tempLCInsuredSchema.setSocialInsuFlag(tLCAppntDB.getSocialInsuFlag());
//				HasAddFreeFlag = true;
//				mAllInsuredSet.add(tempLCInsuredSchema);
//			}
			
			insuredCount=mAllInsuredSet.size();
			//LCPOL的UWFLAG需要特殊处理，如果是第一次自核，无论是否通过UWFLAG都会置成9
			//06-26  由于外包保存会将主险的uwflag置为9 故将此处注掉，改查lbmission
//			for(int a=1;a<=mAllLCPolSet.size();a++){
//				if(mAllLCPolSet.get(a).getUWFlag()==null||"".equals(mAllLCPolSet.get(a).getUWFlag())
//						||"0".equals(mAllLCPolSet.get(a).getUWFlag())){
//					FirstUW=true;
//				}else{
//					FirstUW=false;
//				}
//			}
			
			/*
			String tFirstSql = "select count(1) from lbmission where missionprop1='"+mContNo+"' and activityid='0000001003'";
			ExeSQL firstExeSQL = new ExeSQL();
			String tFirst = firstExeSQL.getOneValue(tFirstSql);
			if(tFirst!=null&&!tFirst.equals("")&&Integer.parseInt(tFirst)>0){
				FirstUW=false;
			}else{
				FirstUW=true;
			}
			*/
			
			LCBnfSet tLCBnfSet = new LCBnfSet();
			LCBnfDB tLCBnfDB = new LCBnfDB();
			tLCBnfDB.setContNo(mContNo);
			tLCBnfSet = tLCBnfDB.query();
			int bnfCount = tLCBnfSet.size();
			
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
			boolean tAgent = true;
			if(!tLAAgentDB.getInfo()){
				logger.debug("未查询到代理人信息！");
				tAgent = false;
			}
			
			
			//原程序不是LMUW取得算法，现修改为从LMUW里取  MODIFY ZHANGFH

			int nPolIndex = 0;
			LMUWSet tLMUWSetUnpass = new LMUWSet(); // 未通过的核保规则
			LMUWSet tLMUWSetAll = null; // 所有核保规则
			LMUWSchema tLMUWSchema = null;
			
			 // 日志监控,性能监控		
			PubFun.logPerformance(mGlobalInput,mContNo,"投保单"+mContNo+"险种核保开始","0");
			// 对险种保单进行循环险种
			for (nPolIndex = 1; nPolIndex <= polCount; nPolIndex++) {
				tLCPolSchema = mAllLCPolSet.get(nPolIndex);
				mOldPolNo = tLCPolSchema.getPolNo(); // 获得保单险种号

				// 准备算法，获取某险种的所有核保规则的集合
				tLMUWSetUnpass.clear();
				if (tLMUWSetAll != null) {
					tLMUWSetAll.clear();
				}
				// if (tLMUWSetSpecial != null) {
				// tLMUWSetSpecial.clear();
				// }
				tLMUWSetAll = CheckKinds(tLCPolSchema); // 获取该险种的所有核保规则的集合
				if (tLMUWSetAll == null) {
					return false;
				}

				/**
				 * delete by wangxiongzhou 20071227 太平洋暂没有特殊规则
				 */
				// tLMUWSetSpecial = CheckKinds2(tLCPolSchema); // 特殊核保规则集合
				// logger.debug("in autowucheck======"+tLMUWSetSpecial);
				// if (tLMUWSetSpecial == null) {
				// return false;
				// }
				// logger.debug(tLMUWSetSpecial.size());
				// 准备数据，从险种信息中获取各项计算信息
				CheckPolInit(tLCPolSchema);// mCalBase 的一些设置。

				// 个人单核保
				mPolPassFlag = "0"; // 核保通过标志，初始为未核保
				int n = tLMUWSetAll.size(); // 核保规则数量
				//若为IPA续保，附加险无需跑自核规则 add by gujx 2010-4-9
//				String tRiskCode = PubFun1.getMainRiskCode(mContNo);
//				if ((tRiskCode.equals("IPA22") || tRiskCode.equals("IPA11")) && !tLCPolSchema.getRiskCode().equals(tRiskCode)) {
//					n = 0;
//				}
				//end 20090402
				if (n == 0) {
					mPolPassFlag = "9"; // 无核保规则则置标志为通过
				} else { // 目前所有的险种均有一些公共的核保规则,所以必定走该分枝

					//dealIsPE(tLCPolSchema); // add by liwb 2009-9-1 抽样体检 
					// 先判断是否需要进行自核校验，如果不用直接跳过
					LMRiskDB aLMRiskDB = new LMRiskDB();
					aLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
					aLMRiskDB.getInfo();
					this.mBomList = tPrepareBOMUWBL.dealData(tLCContSchema,tLCPolSchema);
					int j = 0;
					for (int i = 1; i <= n; i++) { // 对每个核保规则逐个校验。
						logger.debug("<><><<><><"+i);
						if (aLMRiskDB.getUWFlag() != null
								&& aLMRiskDB.getUWFlag().equals("N")) {
							mPolPassFlag = "9";
							mContPassFlag = "9";
							continue;
						} else if (aLMRiskDB.getUWFlag() != null
								&& aLMRiskDB.getUWFlag().equals("Y")) {
							// 取计算编码
							tLMUWSchema = new LMUWSchema();
							tLMUWSchema = tLMUWSetAll.get(i);
							mCalCode = tLMUWSchema.getCalCode();
							UWCode = tLMUWSchema.getUWCode();
							String aCheckPol = CheckPol(
									tLCPolSchema.getInsuredNo(), tLCPolSchema
											.getRiskCode());

							String aCheckPol2 = CheckPol2(UWCode);
							if (!aCheckPol.equals("")&& aCheckPol!=null) {
								j++;
								tLMUWSetUnpass.add(tLMUWSchema);
								if (aCheckPol2.equals("1")) {
									mPolPassFlag = "1"; // 拒保
									mContPassFlag = "1";
									mAllPolPassFlag = "1";
									this.mAutoUWFlag = "N";
								} else {
									if (!mPolPassFlag.equals("1")) {
										mPolPassFlag = "9"; // 待人工核保
										mContPassFlag = "5";
										mAllPolPassFlag = "5";
										this.mAutoUWFlag = "N";
									}
								}

								String tUWGrade1=aCheckPol;
								//取最高的核保级别决定核保师
								if (mUWGrade.compareTo(tUWGrade1) < 0) {
									mUWGrade = tUWGrade1; 
								}
//							if (!aCheckPol.equals("0")) {
//								j++;
//								tLMUWSetUnpass.add(tLMUWSchema);
//								mPolPassFlag="5";
//								mInsPassFlag="5";
//								mContPassFlag="5";
//										
//							}
							}
						} 
						else if (aLMRiskDB.getUWFlag() != null
								&& aLMRiskDB.getUWFlag().equals("X")) {
							// 取计算编码
							tLMUWSchema = new LMUWSchema();
							tLMUWSchema = tLMUWSetAll.get(i);
							mCalCode = tLMUWSchema.getCalCode();
							UWCode = tLMUWSchema.getUWCode();
							String aCheckPol = CheckPol(tLCPolSchema.getInsuredNo(), tLCPolSchema.getRiskCode());
							String aCheckPol2 = CheckPol2(UWCode);
							if (!aCheckPol.equals("")&& aCheckPol!=null) {
								j++;
								tLMUWSetUnpass.add(tLMUWSchema);
								if (aCheckPol2.equals("1")) {
									mPolPassFlag = "1"; // 拒保
									mContPassFlag = "1";
									mAllPolPassFlag = "1";
									this.mAutoUWFlag = "N";
								}
							}
						}

					} // end of for(对每个核保规则逐个校验)

					if (mPolPassFlag.equals("0")) {
						mPolPassFlag = "9";
					}

					logger.debug("匹配数:" + tLMUWSetAll.size() + "级别:"
							+ mUWGrade);
				}
				
				
			
				if (dealOnePol(tLCPolSchema, tLMUWSetUnpass) == false) {
					return false;
				}
				 // 日志监控,过程监控
				if (mPolPassFlag.equals("1")) {					
				PubFun.logTrack(mGlobalInput,mContNo,"投保单"+mContNo+"的险种级核保结果为，"+tLCPolSchema.getRiskCode()+"险种拒保");
				}
				 // 日志监控,过程监控
				if (mPolPassFlag.equals("5")) {
					PubFun.logTrack(mGlobalInput,mContNo,"投保单"+mContNo+"的险种级核保结果为，"+tLCPolSchema.getRiskCode()+"险种转人工核保");
				}
				 // 日志监控,过程监控
				if (mPolPassFlag.equals("9")) {
					PubFun.logTrack(mGlobalInput,mContNo,"投保单"+mContNo+"的险种级核保结果为，"+tLCPolSchema.getRiskCode()+"险种核保通过");
				}
			}		
//			if (mISPE) {
//				mPEMap = new MMap();
//				for (Iterator its = m_custPEInfo.keySet().iterator(); its.hasNext();) {
//					String tKey = (String) its.next();
//					String tPEType = (String) m_custPEInfo.get(tKey);
//					dealPENote(tPEType, tLCContSchema, tKey);
//				}
//			}

			/* 合同核保 */
			 // 日志监控,性能监控							
			PubFun.logPerformance(mGlobalInput,mContNo,"投保单"+mContNo+"合同核保开始","1");
			LMUWSet tLMUWSetContUnpass = new LMUWSet(); // 未通过的合同核保规则
			LMUWSet tLMUWSetContAll = CheckKinds4(tLCPolSchema); // 所有合同核保规则

			// 准备数据，从险种信息中获取各项计算信息
			CheckContInit(tLCContSchema); // 设置mCalBase的一些值。
			
			// 个人合同核保
			int tCount = tLMUWSetContAll.size(); // 核保规则数量
			if (tCount == 0) {
				if (!mContPassFlag.equals("1")) {
					mContPassFlag = "9";
				} // 无核保规则则置标志为通过
			} else { // 目前所有的险种均有一些公共的核保规则,所以必定走该分枝
				int j = 0;
				for (int index = 1; index <= tCount; index++) {
					// 取计算编码
					tLMUWSchema = new LMUWSchema();
					tLMUWSchema = tLMUWSetContAll.get(index);
					mCalCode = tLMUWSchema.getCalCode();
					String aCheckPol=CheckPol(tLCContSchema.getInsuredNo(), "000000");
					if(!aCheckPol.equals("")&& aCheckPol!=null) {
						j++;
						tLMUWSetContUnpass.add(tLMUWSchema);
						if (!mContPassFlag.equals("1")) {
							mContPassFlag = "5"; // 核保不通过，待人工核保
						}
						String tUWGrade1=aCheckPol;
						//取最高的核保级别决定核保师
						if (mUWGrade.compareTo(tUWGrade1) < 0) {
							mUWGrade = tUWGrade1; 
						}
					}
				}

				if (mContPassFlag.equals("0")) {
					mContPassFlag = "9";
				}
				logger.debug("合同核保匹配数:" + tLMUWSetContAll.size()
						+ "合同核保未通过数:" + tLMUWSetContUnpass.size() + "级别:"
						+ mUWGrade);
			}
			
			if ("5".equals(mAllPolPassFlag)) {
				mContPassFlag = "5";
				mUWGrade = getUWGrade(tLCContSchema.getInsuredNo());
			}
			
			dealOneCont(tLCContSchema, tLMUWSetContUnpass);
			// 如果mContPassFlag = "1" ,则发送拒保通知书.
			if (mContPassFlag.equals("1")) {
				 // 日志监控,性能监控							
				PubFun.logPerformance(mGlobalInput,mContNo,"发送拒保通知书","2");
				SendRefuseNotice(tLCContSchema.getContNo()); // 准备打印管理表的一些数据.
			}
			
			//如果mContPassFlag = "9" ,核保通过,则产生应收数据.add by zhangzq 20090227
			if (mContPassFlag.equals("9")) {
				 // 日志监控,性能监控							
				PubFun.logPerformance(mGlobalInput,mContNo,"产生应收数据","3");
				if (prepareFee() == false) {
					CError tError = new CError();
					tError.moduleName = "AutoUWCheckBL";
					tError.functionName = "dealData";
					tError.errorMessage = "产生应收数据失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			hierarhy=mUWGrade;//将最高的核保级别返回给服务类
			 // 日志监控,状态监控
			if (mContPassFlag.equals("1")) {				
			PubFun.logState(mGlobalInput,mContNo,"投保单"+mContNo+"自动核保结果为拒保","1");
			}
			 // 日志监控,状态监控
			if (mContPassFlag.equals("5")) {
			PubFun.logState(mGlobalInput,mContNo,"投保单"+mContNo+"自动核保结果为转人工核保","5");
			}
			 // 日志监控,状态监控
			if (mContPassFlag.equals("9")) {
			PubFun.logState(mGlobalInput,mContNo,"投保单"+mContNo+"自动核保结果为核保通过","9");
			}
			
			//从LMUW里取算法结束
			
			
			String tMainPolSql = "select * from lcpol where mainpolno=polno and contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tMainPolSql);
			sqlbv1.put("contno", mContNo);
			LCPolSet tmainLCPolSet = new LCPolSet();
			tmainLCPolSet=tLCPolDB.executeQuery(sqlbv1);
			int mainCount=tmainLCPolSet.size();
			
			String tSubPolSql = "select * from lcpol where mainpolno!=polno and contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSubPolSql);
			sqlbv2.put("contno", mContNo);
			LCPolSet tSubLCPolSet = new LCPolSet();
			tSubLCPolSet = tLCPolDB.executeQuery(sqlbv2);
			int subCount=tSubLCPolSet.size();
		}
		 // 日志监控,过程监控	
		PubFun.logTrack(mGlobalInput,mContNo,"投保单"+mContNo+"自动核保成功");
		return true;
	}

		/**
		 * add by ml 2006-02-23 reason: 根据要求核保结论需要从产品定义描述表LMUW中直接取值，不再需要经过计算 个人单核保
		 * return：返回相应的核保结论 PassFlag
		 */
		private String CheckPol2(String tUWCode) {
			String tSql = "";
			tSql = "select PassFlag from LMUW where UWCode = '" + "?UWCode?" + "'";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tSql);
			sqlbv3.put("UWCode", tUWCode);
			ExeSQL exeSql = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql.execSQL(sqlbv3);
			if (tSSRS.MaxRow == 0) {
				logger.debug("没有查询到对应的核保级别,核保编码：" + tUWCode);
				PassFlag = "0"; // 无法找到正确的核保结论，值置为0
				return PassFlag;
			} else if (tSSRS.MaxRow > 1) {
				logger.debug("查找出错：找到的核保结论不唯一！核保编码：" + tUWCode);
				PassFlag = "0"; // 无法找到正确的核保结论，值置为0
				return PassFlag;
			} else {
				if (tSSRS.GetText(1, 1) == "" || tSSRS.GetText(1, 1) == null) {
					PassFlag = "0"; // 无法找到正确的核保结论，值置为0
					return PassFlag;
				} else {
					PassFlag = tSSRS.GetText(1, 1);
				}
			}
			return PassFlag;
		}

	/**
	 * 计算是否需要临分
	 * 
	 * @return boolean
	 */
	private boolean CheckFB() {
		VData tVData = new VData();
		tVData.add(mLCContSchema);
		UWFBCal tUWFBCal = new UWFBCal();
		if (!tUWFBCal.submitData(tVData, "")) {
			logger.debug("不需要临分");
			return false;
		} else {
			logger.debug("需要临分");
			return true;
		}
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();

		map.put(mLCContSchema, "UPDATE");
		// if(mLCCUWMasterSet.size() ==1)
		map.put(mLCCUWMasterSet.get(1), "DELETE&INSERT");
		map.put(mLCCUWSubSet, "INSERT");
		map.put(mLCCUWErrorSet, "INSERT");
		
		map.put(mAllLCPolSet, "UPDATE");
		map.put(mAllInsuredSet, "UPDATE");
		int n = mLCUWMasterSet.size();
		for (int i = 1; i <= n; i++) {
			LCUWMasterSchema tLCUWMasterSchema = mLCUWMasterSet.get(i);
			map.put(tLCUWMasterSchema, "DELETE&INSERT");
		}
		map.put(mLCUWSubSet, "INSERT");
		map.put(mLCUWErrorSet, "INSERT");

		for (int i = 1; i <= mLCIndUWMasterSet.size(); i++) {
			LCIndUWMasterSchema tLCIndUWMasterSchema = mLCIndUWMasterSet.get(i);
			map.put(tLCIndUWMasterSchema, "DELETE&INSERT");
		}
		map.put(mLCIndUWSubSet, "INSERT");
		map.put(mLCIndUWErrorSet, "INSERT");
		/** 发送拒保通知书 */
		if (mContPassFlag.equals("1")) {
			map.put(mLOPRTManagerSchema, "INSERT");
		}

		mResult.add(map);		
		logger.debug("this.mPolPassFlag==" + this.mPolPassFlag);
		logger.debug("this.mAllPolPassFlag==" + this.mAllPolPassFlag);
		logger.debug("this.mContPassFlag==" + this.mContPassFlag);
		TransferData aTransferData = new TransferData();
		aTransferData.setNameAndValue("PolPassFlag", this.mPolPassFlag);
		aTransferData.setNameAndValue("ContPassFlag", this.mContPassFlag);
		aTransferData.setNameAndValue("ProductSaleFlag", ProductSaleFlag);
		mResult.add(aTransferData);
		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            LCContSchema
	 * @param tLMUWSetContUnpass
	 *            LMUWSetContUnpass
	 * @return boolean
	 */
	private boolean dealOneCont(LCContSchema tLCContSchema) {
		prepareContUW(tLCContSchema);

		LCContSchema tLCContSchemaDup = new LCContSchema();
		tLCContSchemaDup.setSchema(tLCContSchema);
		mAllLCContSet.add(tLCContSchemaDup);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet.set(mLCCUWMasterSet);
		mAllLCCUWMasterSet.add(tLCCUWMasterSet);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet.set(mLCCUWSubSet);
		mAllLCCUWSubSet.add(tLCCUWSubSet);

		LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
		tLCCUWErrorSet.set(mLCCUWErrorSet);
		mAllLCCUWErrorSet.add(tLCCUWErrorSet);

		return true;
	}

	
	/**
	 * 为个单被保人核保错误信息表准备数据
	 * 
	 * @param tLCContSchema
	 *            LCContSchema
	 * @param tLMUWSetContUnpass
	 *            LMUWSetContUnpass
	 * @return boolean
	 */
	
	/**
	 * 校验核保员级别 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(mOperator);

		if (!tLDUserDB.getInfo()) {
			CError.buildErr(this,"无此操作员信息，不能操作!（操作员：" + mOperator + "）");
			
			
		//	return false;
		}

		return true;
	}

	/**
	 * 准备合同核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareContUW(LCContSchema tLCContSchema) {
		int batchNo=0;
		// 合同核保主表
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();
		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLCCUWMasterSet.size() == 0) {
			tLCCUWMasterSchema.setContNo(mContNo);
			tLCCUWMasterSchema.setGrpContNo(tLCContSchema.getGrpContNo());
			tLCCUWMasterSchema.setProposalContNo(tLCContSchema
					.getProposalContNo());
			tLCCUWMasterSchema.setUWNo(1);
			tLCCUWMasterSchema.setInsuredNo(tLCContSchema.getInsuredNo());
			tLCCUWMasterSchema.setInsuredName(tLCContSchema.getInsuredName());
			tLCCUWMasterSchema.setAppntNo(tLCContSchema.getAppntNo());
			tLCCUWMasterSchema.setAppntName(tLCContSchema.getAppntName());
			tLCCUWMasterSchema.setAgentCode(tLCContSchema.getAgentCode());
			tLCCUWMasterSchema.setAgentGroup(tLCContSchema.getAgentGroup());
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setPostponeDay("");
			tLCCUWMasterSchema.setPostponeDate("");
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setHealthFlag("0");
			tLCCUWMasterSchema.setSpecFlag("0");
			tLCCUWMasterSchema.setQuesFlag("0");
			tLCCUWMasterSchema.setReportFlag("0");
			tLCCUWMasterSchema.setChangePolFlag("0");
			tLCCUWMasterSchema.setPrintFlag("0");
			tLCCUWMasterSchema.setPrintFlag2("0");
			tLCCUWMasterSchema.setManageCom(tLCContSchema.getManageCom());
			tLCCUWMasterSchema.setUWIdea("");
			tLCCUWMasterSchema.setUpReportContent("");
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			tLCCUWMasterSchema.setUWNo(tLCCUWMasterSchema.getUWNo() + 1);
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}
		

		// 合同核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int nUWNo = tLCCUWSubSet.size();
		
		tLCCUWSubSchema.setUWNo(nUWNo+1); // 第几次核保

		tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
		tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
		tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
				.getProposalContNo());
		tLCCUWSubSchema.setInsuredNo(tLCCUWMasterSchema.getInsuredNo());
		tLCCUWSubSchema.setInsuredName(tLCCUWMasterSchema.getInsuredName());
		tLCCUWSubSchema.setAppntNo(tLCCUWMasterSchema.getAppntNo());
		tLCCUWSubSchema.setAppntName(tLCCUWMasterSchema.getAppntName());
		tLCCUWSubSchema.setAgentCode(tLCCUWMasterSchema.getAgentCode());
		tLCCUWSubSchema.setAgentGroup(tLCCUWMasterSchema.getAgentGroup());
		tLCCUWSubSchema.setUWGrade(tLCCUWMasterSchema.getUWGrade()); // 核保级别
		tLCCUWSubSchema.setAppGrade(tLCCUWMasterSchema.getAppGrade()); // 申请级别
		tLCCUWSubSchema.setAutoUWFlag(tLCCUWMasterSchema.getAutoUWFlag());
		tLCCUWSubSchema.setState(tLCCUWMasterSchema.getState());
		tLCCUWSubSchema.setPassFlag(tLCCUWMasterSchema.getState());
		tLCCUWSubSchema.setPostponeDay(tLCCUWMasterSchema.getPostponeDay());
		tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema.getPostponeDate());
		tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
				.getUpReportContent());
		tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
		tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
		tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
		tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
		tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
		tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema.getChangePolFlag());
		tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
				.getChangePolReason());
		tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema.getAddPremReason());
		tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
		tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
		tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
		tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
		tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
		tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		mLCCUWSubSet.add(tLCCUWSubSchema);

		if("5".equals(mContPassFlag)){
			// 核保错误信息表
			LCCUWErrorSchema tLCCUWErrorSchema = new LCCUWErrorSchema();
			LCCUWErrorDB tLCCUWErrorDB = new LCCUWErrorDB();
			String UWNoSql="select * from lccuwerror where contno='" +"?contno?"+
					"' order by uwno desc";
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(UWNoSql);
			sqlbv4.put("contno", mContNo);
			LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
			tLCCUWErrorSet = tLCCUWErrorDB.executeQuery(sqlbv4);
			if (tLCCUWErrorDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCCUWErrorDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkAfterInitService";
				tError.functionName = "prepareContUW";
				tError.errorMessage = mContNo + "合同错误信息表查询失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if(tLCCUWErrorSet.size()==0){
				nUWNo=0;
			}else{
				nUWNo=tLCCUWErrorSet.get(1).getUWNo();
			}
			batchNo=nUWNo+1;
			tLCCUWErrorSchema.setUWNo(nUWNo+1);
			tLCCUWErrorSchema.setContNo(mContNo);
			tLCCUWErrorSchema.setGrpContNo(tLCCUWSubSchema.getGrpContNo());
			tLCCUWErrorSchema
					.setProposalContNo(tLCCUWSubSchema.getProposalContNo());
			tLCCUWErrorSchema.setInsuredNo(tLCCUWSubSchema.getInsuredNo());
			tLCCUWErrorSchema.setInsuredName(tLCCUWSubSchema.getInsuredName());
			tLCCUWErrorSchema.setAppntNo(tLCCUWSubSchema.getAppntNo());
			tLCCUWErrorSchema.setAppntName(tLCCUWSubSchema.getAppntName());
			tLCCUWErrorSchema.setManageCom(tLCCUWSubSchema.getManageCom());
			tLCCUWErrorSchema.setUWRuleCode(""); // 核保规则编码
			tLCCUWErrorSchema.setUWError(""); // 核保出错信息
			tLCCUWErrorSchema.setCurrValue(""); // 当前值
			tLCCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
			tLCCUWErrorSchema.setUWPassFlag(mPolPassFlag);
			//tongmeng 2009-03-24 modify
			//去除重复信息
			Hashtable tContHashtable = new Hashtable();
			for(int i=0;i<tContList.size();i++){
				TransferData contTransferData = (TransferData) tContList.get(i);
				// 生成流水号
				String tserialno = PubFun1.CreateMaxNo("LCCUWERROR", 20);
				
				String tRuleid = (String) contTransferData.getValueByName("tRuleid");
				String templateId = (String) contTransferData.getValueByName("templateId");
				String result = (String) contTransferData.getValueByName("result");
				if(tContHashtable.containsKey(result))
				{
					continue;
				}
				else
				{
					tContHashtable.put(result, result);
				}
				
				tLCCUWErrorSchema.setSerialNo(tserialno);
				tLCCUWErrorSchema.setUWRuleCode(tRuleid); // 转储规则引擎接口返回的模板的RuleId
				tLCCUWErrorSchema.setUWError(result); // 核保出错信息，即核保规则的文字描述内容
				tLCCUWErrorSchema.setUWGrade(mUWGrade);
				tLCCUWErrorSchema.setCurrValue(templateId); //转储规则引擎接口返回的模板的模板号
				LCCUWErrorSchema ttLCCUWErrorSchema = new LCCUWErrorSchema();
				ttLCCUWErrorSchema.setSchema(tLCCUWErrorSchema);
				mLCCUWErrorSet.add(ttLCCUWErrorSchema);
			}
		}
		tLCCUWMasterSchema.setBatchNo(batchNo);
		mLCCUWMasterSet.add(tLCCUWMasterSchema);
		return true;
	}

	/**
	 * 准备险种核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW(LCPolSet tLCPolSet) {
		for(int i=1;i<=tLCPolSet.size();i++){
			String tAddFeeFlag="0";
			int batchNo=0;
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tLCPolSchema = tLCPolSet.get(i);
			int tuwno = 0;
			LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			tLCUWMasterDB.setPolNo(tLCPolSchema.getPolNo());
			LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
			tLCUWMasterSet = tLCUWMasterDB.query();
			if (tLCUWMasterDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保总表取数失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			//判断是否有加费信息
			/*String addFeeSql="select * from lcprem  where polno='"+tLCPolSchema.getPolNo()+"' and payplancode like '000000%%'";
			SSRS tAddFee = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tAddFee = tExeSQL.execSQL(addFeeSql);
			if(tAddFee.MaxRow>0){
				tAddFeeFlag="1";
			}*/
			int n = tLCUWMasterSet.size();
			if (n == 0) {
				tLCUWMasterSchema.setContNo(mContNo);
				tLCUWMasterSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLCUWMasterSchema.setPolNo(tLCPolSchema.getPolNo());
				tLCUWMasterSchema.setProposalContNo(mPContNo);
				tLCUWMasterSchema.setProposalNo(tLCPolSchema.getProposalNo());
				tLCUWMasterSchema.setUWNo(1);
				tLCUWMasterSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLCUWMasterSchema.setInsuredName(tLCPolSchema.getInsuredName());
				tLCUWMasterSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLCUWMasterSchema.setAppntName(tLCPolSchema.getAppntName());
				tLCUWMasterSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLCUWMasterSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
				tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
				tLCUWMasterSchema.setPostponeDay("");
				tLCUWMasterSchema.setPostponeDate("");
				tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLCUWMasterSchema.setState(mPolPassFlag);
				tLCUWMasterSchema.setPassFlag(mPolPassFlag);
				tLCUWMasterSchema.setHealthFlag("0");
				tLCUWMasterSchema.setSpecFlag("0");
				tLCUWMasterSchema.setQuesFlag("0");
				tLCUWMasterSchema.setReportFlag("0");
				tLCUWMasterSchema.setChangePolFlag("0");
				tLCUWMasterSchema.setPrintFlag("0");
				tLCUWMasterSchema.setManageCom(tLCPolSchema.getManageCom());
				tLCUWMasterSchema.setUWIdea("");
				tLCUWMasterSchema.setUpReportContent("");
				tLCUWMasterSchema.setOperator(mOperator); // 操作员
				tLCUWMasterSchema.setAddPremFlag("0");
				tLCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
				tLCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
				tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else if (n == 1) {
				tLCUWMasterSchema = tLCUWMasterSet.get(1);
	
				tuwno = tLCUWMasterSchema.getUWNo();
				tuwno = tuwno + 1;
	
				tLCUWMasterSchema.setUWNo(tuwno);
				tLCUWMasterSchema.setProposalContNo(mPContNo);
				tLCUWMasterSchema.setState(mPolPassFlag);
				tLCUWMasterSchema.setPassFlag(mPolPassFlag);
				//tLCUWMasterSchema.setAddPremFlag(tAddFeeFlag);
				tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
				tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
				tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
				tLCUWMasterSchema.setOperator(mOperator); // 操作员
				tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
			} else {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保总表取数据不唯一!";
				this.mErrors.addOneError(tError);
				return false;
			}
	
			
	
			// 核保轨迹表 ln 2008-10-21 modify uwno计算方法修改
			LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
			LCUWSubDB tLCUWSubDB = new LCUWSubDB();
			//tLCUWSubDB.setPolNo(mOldPolNo);
			String sqlUwno = "select * from lcuwsub where polno ='"+ "?polno?" +"' order by uwno desc ";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(sqlUwno);
			sqlbv5.put("polno", tLCPolSchema.getPolNo());
			LCUWSubSet tLCUWSubSet = new LCUWSubSet();
			tLCUWSubSet = tLCUWSubDB.executeQuery(sqlbv5);
			if (tLCUWSubDB.mErrors.needDealError()) {
				// @@错误处理
				this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "UWAtuoChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = mOldPolNo + "个人核保轨迹表查失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
	
			int m = tLCUWSubSet.size();
			int uwNo = 0;
			
			if(m>0){
				uwNo=tLCUWSubSet.get(1).getUWNo();
				tLCUWSubSchema.setUWNo(uwNo+1);
			}else{
				tLCUWSubSchema.setUWNo(1); // 第1次核保
			}
	
			tLCUWSubSchema.setContNo(mContNo);
			tLCUWSubSchema.setPolNo(tLCPolSchema.getPolNo());
			tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
			tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema.getProposalContNo());
			tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
			tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
			tLCUWSubSchema.setInsuredName(tLCUWMasterSchema.getInsuredName());
			tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
			tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
			tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
			tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
			tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
			tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
			tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
			tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema.getPostponeDay());
			tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema.getPostponeDate());
			tLCUWSubSchema.setUpReportContent(tLCUWMasterSchema
					.getUpReportContent());
			tLCUWSubSchema.setHealthFlag(tLCUWMasterSchema.getHealthFlag());
			tLCUWSubSchema.setSpecFlag(tLCUWMasterSchema.getSpecFlag());
			tLCUWSubSchema.setSpecReason(tLCUWMasterSchema.getSpecReason());
			tLCUWSubSchema.setQuesFlag(tLCUWMasterSchema.getQuesFlag());
			tLCUWSubSchema.setReportFlag(tLCUWMasterSchema.getReportFlag());
			tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema.getChangePolFlag());
			tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
					.getChangePolReason());
			tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema.getAddPremReason());
			tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
			tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
			tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
			tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
			tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
			tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
	
			
			if("5".equals(mPolPassFlag)){
				// 核保错误信息表
				LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
				LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
				String UWNoSql="select * from lcuwerror where PolNo='" +"?polno?"+
						"' order by uwno desc";
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(UWNoSql);
				sqlbv6.put("polno", tLCPolSchema.getPolNo());
				LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
				tLCUWErrorSet = tLCUWErrorDB.executeQuery(sqlbv6);
				if (tLCUWErrorDB.mErrors.needDealError()) {
					// @@错误处理
					this.mErrors.copyAllErrors(tLCUWErrorDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "UWAtuoChkBL";
					tError.functionName = "prepareUW";
					tError.errorMessage = mOldPolNo + "个人错误信息表查询失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				if(tLCUWErrorSet.size()==0){
					m=0;
				}else{
					m=tLCUWErrorSet.get(1).getUWNo();
				}
				batchNo=m+1;
				tLCUWErrorSchema.setUWNo(m+1);
				tLCUWErrorSchema.setContNo(mContNo);
				tLCUWErrorSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
				tLCUWErrorSchema.setProposalContNo(mPContNo);
				tLCUWErrorSchema.setPolNo(tLCPolSchema.getPolNo());
				tLCUWErrorSchema.setProposalNo(tLCPolSchema.getProposalNo());
				tLCUWErrorSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLCUWErrorSchema.setInsuredName(tLCPolSchema.getInsuredName());
				tLCUWErrorSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLCUWErrorSchema.setAppntName(tLCPolSchema.getAppntName());
				tLCUWErrorSchema.setManageCom(tLCPolSchema.getManageCom());
				tLCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
				tLCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
				tLCUWErrorSchema.setUWPassFlag(mPolPassFlag);
				
				for(int a=0;a<tPolList.size();a++){
					TransferData PolTransferData = (TransferData) tPolList.get(a);
					String tPolNo = (String) PolTransferData.getValueByName("polno");
					if(tPolNo.equals(tLCPolSchema.getPolNo())){
						
						String templateId = (String) PolTransferData.getValueByName("templateId");
						String result = (String) PolTransferData.getValueByName("result");
						String tRuleid = (String) PolTransferData.getValueByName("tRuleid");
						String tserialno = PubFun1.CreateMaxNo("LCUWERROR", 20);
				
						tLCUWErrorSchema.setSerialNo(tserialno);
						tLCUWErrorSchema.setUWRuleCode(tRuleid); //转储规则引擎接口返回的模板的Ruleid
						tLCUWErrorSchema.setUWError(result); // 核保出错信息，即核保规则的文字描述内容
						tLCUWErrorSchema.setUWGrade(mUWGrade);
						tLCUWErrorSchema.setCurrValue(templateId); //  转储规则引擎接口返回的模板号
				//		tLCUWErrorSchema.setSugPassFlag(tLMUWSchema.getPassFlag()); // picch需求对自核规则分类（体检、契调）
				
						LCUWErrorSchema ttLCUWErrorSchema = new LCUWErrorSchema();
						ttLCUWErrorSchema.setSchema(tLCUWErrorSchema);
						mLCUWErrorSet.add(ttLCUWErrorSchema);
					}
				}
			}
			tLCUWMasterSchema.setBatchNo(batchNo);
			mLCUWSubSet.add(tLCUWSubSchema);
			mLCUWMasterSet.add(tLCUWMasterSchema);
		}
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验核保级别
		if (!checkUWGrade()) {
			return false;
		}

		// 校验是否复核
		// if (!checkApprove(mLCContSchema))
		// return false;

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

		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据失败!");
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this,"前台传输全局公共数据Operate失败!");
			return false;
		}

		// 获得登陆机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCPolDB.mErrors );
			CError.buildErr(this,"前台传输全局公共数据ManageCom失败!");
			return false;
		}

		// 获得当前工作任务的任务ID
		mContNo = (String) mTransferData.getValueByName("ContNo");
		if (mContNo == null) {
			// @@错误处理
			CError.buildErr(this,"前台传输业务数据中mContNo失败!");
			return false;
		}	
		

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (tLCContDB.getInfo()) { // 验证LCCont表中是否存在该合同项记录
			mLCContSchema.setSchema(tLCContDB.getSchema());
		} else {
			CError.buildErr(this, "合同号为" + mLCContSchema.getContNo() + "未查询到!");
			
			return false;
		}
		//获取投保人年龄
		this.mAppntAge = this.calAppAge(mLCContSchema.getAppntBirthday(),
				mLCContSchema.getPolApplyDate(), "Y");
		// 银保通标志
		mBankInsu = (String) mTransferData.getValueByName("BankInsu");
		if (mBankInsu == null) {
			mBankInsu = "0";
		}
		
//		theCount = Integer.parseInt((String) mTransferData.getValueByName("totalCount"));
		
		// 判断国家及本币
		String CurrString = "select codename from ldcode1 where codetype = 'currencyprecision' " 
			+" and code1 = (select sysvarvalue from ldsysvar where sysvar='nativeplace')";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(CurrString);
		ExeSQL tExeSQL = new ExeSQL();
		locCurrency = tExeSQL.getOneValue(sqlbv);
		if(locCurrency==null||"".equals(locCurrency)){
			CError.buildErr(this, "查询本币信息失败！");			
		
			return false;
		}		 
		
		
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

	public float parseFloat(String s) {
		if (s.length() < 0 || s.equals("")) {
			return 0;
		}
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		return f1;
	}

	public String getHierarhy() {
		// String tsql = "select RiskSortValue from lmrisksort where
		// riskcode='"+tLCPolSchema.getRiskCode()+"' and risksorttype='5'";
		// ExeSQL riskSql = new ExeSQL();
		// SSRS sumAmntSSRS = new SSRS();
		// sumAmntSSRS = riskSql.execSQL(tsql);
		// String riskty=sumAmntSSRS.GetText(1,1);
		// tsql = "select uwpopedom from lduwgradeperson where uwtype=1 and
		// maxamnt>'"+riskamnt+"' and uwkind='"+riskty+"' order by maxamnt";
		// sumAmntSSRS = riskSql.execSQL(tsql);
		return hierarhy; // 返回几级核保师
	}

	public String getReDistribute() {
		logger.debug("返回分保标志前====" + reDistribute);
		return reDistribute; // 返回是否需要分保--1:需要0:不需要

	}

	public boolean SendRefuseNotice(String tContNo) {
		String strNoLimit = PubFun.getNoLimit(mGlobalInput.ComCode);
		String mPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
		mLOPRTManagerSchema.setPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setOtherNo(tContNo);
		mLOPRTManagerSchema.setOtherNoType("02");
		mLOPRTManagerSchema.setCode("JB00");
		mLOPRTManagerSchema.setManageCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setAgentCode(mLCContSchema.getAgentCode());
		mLOPRTManagerSchema.setReqCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setExeCom(mLCContSchema.getManageCom());
		mLOPRTManagerSchema.setExeOperator(mGlobalInput.Operator);
		mLOPRTManagerSchema.setPrtType("0");
		mLOPRTManagerSchema.setStateFlag("0");
		mLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
		mLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
		mLOPRTManagerSchema.setOldPrtSeq(mPrtSeq);
		mLOPRTManagerSchema.setPatchFlag("0");
		return true;
	}

	private void GetAllSumAmnt(String tInsuredNo) {
		// //为自核服务 write by yaory///////
		String risktype = "";
		try {
			logger.debug(mContNo);
			ExeSQL riskSql = new ExeSQL();
			//tongmeng 2009-03-12 modify
			//使用新的自核风险保额计算规则
			String tsql = "";
			//寿险风险保额
			/*
			   -- tRiskType = 1 寿险风险保额
			   -- tRiskType = 2 重疾险风险保额
               -- tRiskType = 3 医疗险风险保额
               -- tRiskType = 4 意外险风险保额
               -- tRiskType = 12 身故风险保额
               -- tRiskType = 13 寿险体检额度
               -- tRiskType = 14 重疾体检额度
               -- tRiskType = 15 医疗体检额度

			 */
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','1','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','1','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','1','1') }";
			}
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tsql);
			sqlbv7.put("tInsuredNo", tInsuredNo);
			String tempAmnt = riskSql.getOneValue(sqlbv7);			
			LSumDangerAmnt = parseFloat(tempAmnt);//寿险
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','2','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','2','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','2','1') }";
			}
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(tsql);
			sqlbv8.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv8);
			DSumDangerAmnt = parseFloat(tempAmnt); //重疾
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','3','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','3','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','3','1') }";
			}
			SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
			sqlbv9.sql(tsql);
			sqlbv9.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv9);
			SSumDangerAmnt = parseFloat(tempAmnt); //医疗
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','4','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','4','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','4','1') }";
			}
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tsql);
			sqlbv10.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv10);
			MSumDangerAmnt = parseFloat(tempAmnt); //意外
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','12','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','12','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','12','1') }";
			}
			SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
			sqlbv11.sql(tsql);
			sqlbv11.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv11);
			SSumDieAmnt = parseFloat(tempAmnt); //身故
			AllSumAmnt=LSumDangerAmnt+DSumDangerAmnt+MSumDangerAmnt;//2009-3-5 modify累计风险保额=累计寿险风险保额+累计重疾风险保额+累计意外伤害风险保额
			
		} catch (Exception ex) {
		}
	}
	/**
	 * @param 根据数据库中的描述算出核保级别 执行getUWGrade函数
	 * */
	private String getUWGrade(String tInsuredNo){
		String tUWGrade="";
		String tempUWGrade = "";
		try {
			logger.debug(mContNo);
			ExeSQL riskSql = new ExeSQL();
			double RiskAmnt1=0;//累计寿险风险保额
			double RiskAmnt2=0;//累积重疾风险保额
			double RiskAmnt4=0;//累积意外风险保额
			double RiskAmnt6=0;		
			//duanyh 2009-03-14 modify
			//使用新的自核风险保额计算规则
			String tsql = "";
			//寿险风险保额
			/*
			   -- tRiskType = 1 寿险风险保额
			   -- tRiskType = 2 重疾险风险保额
               -- tRiskType = 3 医疗险风险保额
               -- tRiskType = 4 意外险风险保额
               -- tRiskType = 12 身故风险保额
               -- tRiskType = 13 寿险体检额度
               -- tRiskType = 14 重疾体检额度
               -- tRiskType = 15 医疗体检额度

			 */
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','1','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','1','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','1','1') }";
			}
			SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
			sqlbv12.sql(tsql);
			sqlbv12.put("tInsuredNo", tInsuredNo);
			String tempAmnt = riskSql.getOneValue(sqlbv12);			
			RiskAmnt1 = parseFloat(tempAmnt);//寿险
			
			tsql = "select healthyamnt2('" + "?tInsuredNo?"
			+ "','2','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','2','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','2','1') }";
			}
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tsql);
			sqlbv13.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv13);
			RiskAmnt2 = parseFloat(tempAmnt); //重疾
			
//			tsql = "select healthyamnt2('" + "?tInsuredNo?"
//			+ "','4','1') from dual";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','4','1') from dual";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','4','1') }";
			}
			SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
			sqlbv14.sql(tsql);
			sqlbv14.put("tInsuredNo", tInsuredNo);
			tempAmnt = riskSql.getOneValue(sqlbv14);
			RiskAmnt4 = parseFloat(tempAmnt); //意外			

			RiskAmnt6 = RiskAmnt1+RiskAmnt2+RiskAmnt4;
			logger.debug("RiskAmnt1:"+RiskAmnt1+"  RiskAmnt2:"+RiskAmnt2+" " +
					" RiskAmnt4:"+RiskAmnt4+"  RiskAmnt6:"+RiskAmnt6);
			//执行getUWGrade函数，返回核保级别
			String UWGradeSql = "select trim(case when getUWGrade('1','?RiskAmnt1?','2','?RiskAmnt2?'," +
					"'4','?RiskAmnt4?','6','?RiskAmnt6?') is null then '1' else getUWGrade('1','?RiskAmnt1?','2','?RiskAmnt2?'," +
					"'4','?RiskAmnt4?','6','?RiskAmnt6?') end) from dual";
			SQLwithBindVariables sqlbv63 = new SQLwithBindVariables();
			sqlbv63.sql(UWGradeSql);
			sqlbv63.put("RiskAmnt1", RiskAmnt1);
			sqlbv63.put("RiskAmnt2", RiskAmnt2);
			sqlbv63.put("RiskAmnt4", RiskAmnt4);
			sqlbv63.put("RiskAmnt6", RiskAmnt6);
			tempUWGrade = riskSql.getOneValue(sqlbv63);
			if(tUWGrade.compareTo(tempUWGrade) < 0){
				tUWGrade=tempUWGrade;
			}
		} catch (Exception ex) {
		}
		return tUWGrade;
	}
	
	/**
	 * 判断被保人的BMI是否在标准范围内
	 * @param tCountNo
	 * @param insured
	 * @param tLCContSchema
	 * @param tFlag 0-身高标准 1 体重标准 2 BMI标准
	 * @return
	 */
	private String getBMIFlag(String tCountNo,LCInsuredSchema tLCInsuredSchema,
			BOMInsured insured,
			LCContSchema tLCContSchema,String tFlag 
			)
	{
		
		/*
		 select decode(count(*),0,0,1) from BMIStandard where 1=1
and Sex = ''
and StartAge>='' and StartAgeFlag='' 
and EndAge<'' and EndAgeFlag=''
and StartStature>=''
and EndStature<=''
and StartAvoirdupois>=''
and EndAvoirdupois<=''
and StartBMI>=''
and EndBMI<=''
		 */
		try {
			String tRes = "";
			String tAgeFlag = "";
			int tAge = 0;
			String tSex = tLCInsuredSchema.getSex();
			double tStature = (insured.getStature()).doubleValue();
			double tAvoirdupois = (insured.getAvoirdupois()).doubleValue();
			double tBMI = (insured.getBMI()).doubleValue();
			String tBirthDay = tLCInsuredSchema.getBirthday();
			String tPolApplyDate = tLCContSchema.getPolApplyDate();
			tAge = PubFun.calInterval(tBirthDay,tPolApplyDate, "Y");
			if(tAge==0)
			{
				//如果为0的话,计算几个月
				tAge = PubFun.calInterval(tBirthDay,tPolApplyDate, "M");
				tAgeFlag = "M";
				//如果0个月则需判断多少天
				if(tAge==0){
					tAge = PubFun.calInterval(tBirthDay,tPolApplyDate, "D");
					//如果年龄小于28天则按0个月计算 
					if(tAge<28){
						tAge = 0;
						tAgeFlag = "M";
					} else {
						tAgeFlag = "D";
					}
				}
			}
			else
			{
				tAgeFlag = "A";
			}
			String tSQL = "select (case count(*) when 0 then 0 else 1 end) from BMIStandard where 1=1 "
						+ " and Sex = '"+"?sex?"+"' "
						+ " and StartAge<='"+"?age?"+"' and StartAgeFlag='"+"?ageflag?"+"' " 
						+ " and EndAge>'"+"?age?"+"' and EndAgeFlag='"+"?ageflag?"+"' ";
						if(tFlag.equals("0"))
						{
							tSQL = tSQL + " and StartStature<='"+"?stature?"+"' "
										+ " and EndStature>='"+"?stature?"+"' ";
						}
						else if(tFlag.equals("1"))
						{
							tSQL = tSQL + " and StartAvoirdupois<='"+"?avoirdupois?"+"' "
										+ " and EndAvoirdupois>='"+"?avoirdupois?"+"' ";
						}
						else if(tFlag.equals("2"))
						{
							tSQL = tSQL + " and StartBMI<='"+"?BMI?"+"' "
										+ " and EndBMI>='"+"?BMI?"+"' ";
						}
						SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
						sqlbv15.sql(tSQL);
						sqlbv15.put("sex", tSex);
						sqlbv15.put("age", tAge);
						sqlbv15.put("ageflag", tAgeFlag);
						sqlbv15.put("stature", tStature);
						sqlbv15.put("avoirdupois", tAvoirdupois);
						sqlbv15.put("BMI", tBMI);
			logger.debug("BMISQL:"+tSQL);				
			ExeSQL tExeSQL = new ExeSQL();
			String tValue = tExeSQL.getOneValue(sqlbv15);
			if(tValue!=null&&!tValue.equals("")&&Integer.parseInt(tValue)>0)
			{
				tRes = "1";
			}
			else
			{
				tRes = "0";
			}
			
			return tRes;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1";
		}
	}
	
	private String getZBFlag(double SX,double ZJ,double YW,String EM)
	{
		/*
		累计寿险风险保额大于230万的投保单
	 	累计重疾风险保额大于120万的投保单
		累计意外险风险保额大于250万的投保单
		累计寿险风险保额大于30万元且EM评点大于150点的投保单
		累计重疾风险保额大于20万元且EM评点大于125点的投保单
		 */
		String tRes = "0";
		try {
			String tSQL = "select (case (case when sum(A.x) is null then 0 else sum(A.x) end) when 0 then 0 else 1 end) from  "
				        + "( select 1 x from dual where "
				        + SX + " >2300000 or "
				        + ZJ + " >1200000 or "
				        + YW + " >2500000 or "
				        + "("
				        + SX + " >300000  and "+EM+" >150) "
				        + " or "
				        + "("
				        + ZJ + " >200000  and "+EM+" >125 "
				        + ")"
				        + ") A";
			SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
			sqlbv16.sql(tSQL);
			sqlbv16.put("SX", SX);
			sqlbv16.put("ZJ", ZJ);
			sqlbv16.put("YW", YW);
			sqlbv16.put("EM", EM);
			ExeSQL tExeSQL = new ExeSQL();
			tRes = tExeSQL.getOneValue(sqlbv16);
			logger.debug("ZB tSQL:"+tSQL + ":Value:"+tRes);
			if(tRes==null||tRes.equals(""))
			{
				tRes = "0";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return "1";
		}
		return tRes;
	}
	
	/**
	 * 业务员告知异常
	 * @param tContNo
	 * @return
	 */
	private String getSpecAImpart(String tContNo)
	{
		String tRes = "0";
//		try {
//			String tSQL = "select (case when sum(A.x) is null then 0 else sum(A.x) end) from ( "
//				        + " select (case when count(*)>0 then 0 else 1 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+"?contno?"+"' "
//				        + " and impartcode in ('A0152','D0153') "
//				        + " union "
//				        + " select ( case when count(*)>0 then 1 else 0 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+"?contno?"+"' "
//				        + " and impartcode in ('A0153','A0154','D0154','D0155') "
//				       /* + " union "
//				        + " select ( case when count(*)>0 then 0 else 1 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+tContNo+"' "
//				        + " and impartcode in ('A0156','A0157','D0151','D0157','D0158') "
//				        + " and regexp_replace(impartparammodle,'/','') is not null "
//				        */
//				        /*
//				        + " union "
//				        + " select (case when count(*)>0 then 0 else 1 end ) x "
//				        + " from lccustomerimpart where customernotype='2' "
//				        + " and contno='"+tContNo+"' "
//				        + " and impartcode in ('A0155','D0156') and "
//				        + " (regexp_replace(impartparammodle,'/','')='业务员推销' "
//				        + " or regexp_replace(impartparammodle,'/','')='同事朋友推荐' ) "
//				        */
//				        + ") A ";
//			SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
//			sqlbv17.sql(tSQL);
//			sqlbv17.put("contno", tContNo);
//			ExeSQL tExeSQL = new ExeSQL();
//			tRes = tExeSQL.getOneValue(sqlbv17);
//			logger.debug(":tSQL:"+tSQL);
//			if(tRes==null||tRes.equals("")||Integer.parseInt(tRes)==0)
//			{
//				return "0";
//			}
//			else
//			{
//				return "1";
//			}
		return "0";
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "1";
//		}
		
	}
	/**
	 * 代理人的_告知投保人投保经过异常
	 * @param tContNo
	 * @return
	 */
	private String getProAImpart(String tContNo)
	{
		String tRes = "0";
		try {
			String tSQL = "select (case when sum(A.x) is null then 0 else sum(A.x) end) from ( "
				        + " select (case when count(*)>0 then 0 else 1 end ) x "
				        + " from lccustomerimpart where customernotype='2' "
				        + " and contno='"+"?contno?"+"' "
				        + " and impartcode in ('A0155','D0156') and "
				        + " (regexp_replace(impartparammodle,'/','')='业务员推销' "
				        + " or regexp_replace(impartparammodle,'/','')='同事朋友推荐' )) A ";
			SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
			sqlbv18.sql(tSQL);
			sqlbv18.put("contno", tContNo);
			ExeSQL tExeSQL = new ExeSQL();
			tRes = tExeSQL.getOneValue(sqlbv18);
			logger.debug(":tSQL1:"+tSQL);
			if(tRes==null||tRes.equals("")||Integer.parseInt(tRes)==0)
			{
				return "0";
			}
			else
			{
				return "1";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "1";
		}
	}
	
	/**
	 * 怀孕周数
	 * 默认返回0
	 * 
	 * @param  customerno
	 * @return Double
	 * */
	private Double getPregnancyWeeks(String tCustomerno){
		Double tPregnancyWeeks = null;
		try {
			String ttPregnancyWeeksSql = "select impartparammodle from lccustomerimpart where 1=1"
						+ " and customerno='"+"?customerno?"+"' and prtno='"+"?prtno?"+"'"
						+ " and impartver in('A05','A01','D01')"
						+ " and impartcode in('A0521','A0113a','D0112a')";
			SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
			sqlbv19.sql(ttPregnancyWeeksSql);
			sqlbv19.put("customerno", tCustomerno);
			sqlbv19.put("prtno", mContNo);
			ExeSQL mExeSQl = new ExeSQL();
			String tPreWeeks = mExeSQl.getOneValue(sqlbv19);
			if (!(tPreWeeks == null || "".equals(tPreWeeks.trim()))) {
				tPregnancyWeeks = Double.valueOf(tPreWeeks);
			}else{
				tPregnancyWeeks = Double.valueOf("0");
			}
		} catch (Exception e) {
			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerno+"怀孕周数数据有误！");
			e.printStackTrace();
			tPregnancyWeeks = Double.valueOf("0");
		}
		return tPregnancyWeeks;
	}
	
	/**
	 * 吸烟年数 
	 * @param tCustomerType  A-投保人  I-被保人  FreeFlag 豁免标记
	 * @return 默认返回0
	 * */
	private Double getSmokeYears(String tCustomerno,String tCustomerType,boolean FreeFlag){
		Double tSmokeYears = null;
		String impartparamno = "";
		if(tCustomerType.equals("A")||FreeFlag == true){
			impartparamno = "4";
		}else{
			impartparamno = "2";
		}
		try {
			String tSmokeYearsSql = "select impartparam from lccustomerimpartparams where"
						+ " impartver in('A05','A01','D01') and impartcode in('A0502','A0102','D0102')"
						+ " and impartparamno='"+"?impartparamno?"+"'"
						+ "and prtno='"+"?prtno?"+"' and customerno='"+"?customerno?"+"'";
			SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
			sqlbv20.sql(tSmokeYearsSql);
			sqlbv20.put("impartparamno", impartparamno);
			sqlbv20.put("prtno", mContNo);
			sqlbv20.put("customerno", tCustomerno);
			ExeSQL mExeSQl = new ExeSQL();
			String tSmoYears = mExeSQl.getOneValue(sqlbv20);
			if (!(tSmoYears == null || "".equals(tSmoYears.trim()))) {
				tSmokeYears = Double.valueOf(tSmoYears);
			}else{
				tSmokeYears = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerno+"吸烟年数数据有误！");
			e.printStackTrace();
			tSmokeYears  = Double.valueOf("0");
		}
		return tSmokeYears;
	}
	
	/**
	 * 饮酒类型 1-啤酒  2-红酒  3-白酒  4-其他   
	 *  默认返回0
	 *  
	 *  09-09-27  目前规则引擎只支持一种饮酒类型
	 *  故注掉原逻辑 与业管陈军朝沟通后确认目前只取白酒
	 * */
	private String getDrinkType(String tCustomerNo){
		String tDrinkType = "";
//		String tDrinkTypeSql = "select case  when impartparamno='2' then 1 when impartparamno='3' then 2"
//						+ " when impartparamno='4' then 3 when impartparamno='5' then 4 else 0 end"
//						+ " from lccustomerimpartparams where impartver = 'A05' and impartcode='A0503'"
//						+ " and impartparam !='0'"
//						+ " and impartparamno in ('2','3','4','5')"
//						+ " and customerno='"+tCustomerNo+"' and prtno='"+mContNo+"' ";
//		tDrinkType = mExeSQL.getOneValue(tDrinkTypeSql);  09-09-27  目前规则引擎只支持一种饮酒类型
		//故注掉原逻辑 与业管陈军朝沟通后确认目前只取白酒
		String tDrinkTypeSql = "select count(1) from lccustomerimpartparams where impartparamno='4' and impartcode='A0503'"
						+ " and impartparam !='0' and customerno='"+"?customerno?"+"'"
						+ " and prtno='"+"?prtno?"+"'";
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(tDrinkTypeSql);
		sqlbv21.put("prtno", mContNo);
		sqlbv21.put("customerno", tCustomerNo);
		tDrinkType = mExeSQL.getOneValue(sqlbv21);
		if(tDrinkType==null||tDrinkType.equals("")||Integer.parseInt(tDrinkType)==0){
			tDrinkType = "0";
		}
		else {
			tDrinkType = "3";
		}
		return tDrinkType;
	}
	
	/**
	 * 计算饮酒年数
	 * 默认返回0
	 * */
	private Double getDrinkYears(String tCustomerNo){
		Double tDrinkYears = null;
		tDrinkYears = Double.valueOf("0");
//
//		try {
//			String tDrinkYearsSql = "select impartparam from lccustomerimpartparams where impartver='A05'"
//						+ " and impartcode='A0503' and impartparamno='1'"
//						+ " and customerno='"+"?customerno?"+"' and prtno='"+"?prtno?"+"'"
//						+ " union"
//						+ " select impartparam from lccustomerimpartparams where impartver in ('A01','D01')"
//						+ " and impartcode in ('A0103','D0103') and impartparamno='3'"
//						+ " and customerno='"+"?customerno?"+"' and prtno='"+"?prtno?"+"'";
//			SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
//			sqlbv22.sql(tDrinkYearsSql);
//			sqlbv22.put("prtno", mContNo);
//			sqlbv22.put("customerno", tCustomerNo);
//			String tDriYears = mExeSQL.getOneValue(sqlbv22);
//			tDriYears = mExeSQL.getOneValue(sqlbv22);
//			if (!(tDriYears == null || "".equals(tDriYears.trim()))) {
//				tDrinkYears = Double.valueOf(tDriYears);
//			}else{
//				tDrinkYears = Double.valueOf("0");
//			}
//		} catch (NumberFormatException e) {
//			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerNo+"饮酒年数数据有误！");
//			e.printStackTrace();
//			tDrinkYears = Double.valueOf("0");
//		}
		return tDrinkYears;
	}
	
	/**
	 * 获得各种告知 
	 * @param  impartType  01-孕妇告知  02-危险运动爱好  03-交通事故告知
	 * 					   04-出国意向告知
	 * 
	 * @return 0-否  1-是
	 * */
	private String getISImpart(String tCustomerNo,String impartType){
		String tReturn = "";
		StringBuffer tReturnSql = new StringBuffer();
		tReturnSql.append("select (case count(1) when 0 then 0 else 1 end) from lccustomerimpart where");
		if(impartType.equals("01")){
			//孕妇告知
			tReturnSql.append(" impartver in ('A01','A05','D01') and impartcode in ('A0521','A0113a','D0112a')");
		} else if(impartType.equals("02")){
			//危险运动爱好
			tReturnSql.append(" impartver in ('A01','A06','D01','C01') and impartcode in ('A0105','A0530','D0105','C0105')");
		} else if(impartType.equals("03")){
			//交通事故告知
			tReturnSql.append(" impartver in ('A06') and impartcode in ('A0532')");
		} else if(impartType.equals("04")){
			//出国意向告知
			tReturnSql.append(" impartver in ('A06','D02') and impartcode in ('A0533','D0117')");
		}
		tReturnSql.append("and contno='"+"?contno?"+"' and customerno='"+"?customerno?"+"'");
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(tReturnSql.toString());
		sqlbv23.put("contno", mContNo);
		sqlbv23.put("customerno", tCustomerNo);
		tReturn = mExeSQL.getOneValue(sqlbv23);
		return tReturn;
	}
	
	/**
	 * 康顺、每日给付住院合同份数
	 * @param tRiskType: 1-康顺   2-每日住院
	 * */
	private Double getSumCont(String tCustomerNo,String tCalType){
		Double SumKSCont = null;
		String tRiskType = "";
		if("1".equals(tCalType)){
			tRiskType = "'141803','111602'";
		} else {
			tRiskType = "'121701','121704'";
		}
		SumKSCont = Double.valueOf("0");
//
//		try {
//			String tSumKSContSql = "select count(prtno) from lccont a where exists ("
//							+ " select 1 from lcpol b where b.appflag not in ('4','9')" 
//							+ " and b.uwflag not in ('1','2','a')" 
//							+ " and b.insuredno='"+"?insuredno?"+"'" 
//							+ " and b.riskcode in ("+"?riskcode?"+")" 
//							+ " and a.contno=b.contno)";
//			SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
//			sqlbv24.sql(tSumKSContSql);
//			sqlbv24.put("insuredno", tCustomerNo);
//			sqlbv24.put("riskcode", tRiskType);
//			String tSumKSCont = mExeSQL.getOneValue(sqlbv24);
//			if (!(tSumKSCont == null || "".equals(tSumKSCont.trim()))) {
//				SumKSCont = Double.valueOf(tSumKSCont);
//			}else{
//				SumKSCont = Double.valueOf("0");
//			}
//		} catch (NumberFormatException e) {
//			e.printStackTrace();
//			SumKSCont = Double.valueOf("0");
//		}
		return SumKSCont;
	}
	
	/**
	 * 被保险人的金玉满堂系列产品份数
	 * @param tCustomerNo: 被保人客户号
	 * */
	private Double getSumJYMTCount(String tCustomerNo){
		Double SumJYMTCount = null;
		try {
			String tSumJYMTCountSql = "select sum(mult) from lcpol a where appflag not in ('4','9') "
						            + " and uwflag not in ('1','2','a') "
						            + " and riskcode in ('312201','312202','312203','312204','312206') "
						            + " and insuredno='"+"?insuredno?"+"' " ;
			SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
			sqlbv25.sql(tSumJYMTCountSql);
			sqlbv25.put("insuredno", tCustomerNo);
			String tSumJYMTCount = mExeSQL.getOneValue(sqlbv25);
			if (!(tSumJYMTCount == null || "".equals(tSumJYMTCount.trim()))) {
				SumJYMTCount = Double.valueOf(tSumJYMTCount);
			}else{
				SumJYMTCount = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			SumJYMTCount = Double.valueOf("0");
		}
		return SumJYMTCount;
	}
	
	/**
	 * 被保险人的富贵盈门系列产品保费
	 * @param tCustomerNo: 被保人客户号
	 * */
	private Double getSumFGYMPrem(String tCustomerNo){
		Double SumFGYMPrem = null;
		try {
//			String tSumFGYMPremSql = "select sum(Prem) from lcpol a where  appflag not in ('4','9') "
//						            + " and uwflag not in ('1','2','a') "
//						            + " and riskcode in ('314301','314302') "
//						            + " and insuredno='"+tCustomerNo+"' " ;
//			String tSumFGYMPrem = mExeSQL.getOneValue(tSumFGYMPremSql);
//			if (!(tSumFGYMPrem == null || "".equals(tSumFGYMPrem.trim()))) {
//				SumFGYMPrem = Double.valueOf(tSumFGYMPrem);
//			}else{
//				SumFGYMPrem = Double.valueOf("0");
//			}
			//有多币种险种的情况
			String currString = "select * from lcpol where appflag not in ('4','9') "
						            + " and uwflag not in ('1','2','a') "
						            + " and riskcode in ('314301','314302') "
						            + " and insuredno='"+"?insuredno?"+"' ";
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(currString);
			sqlbv26.put("insuredno", tCustomerNo);
			LDExch tLDExch = new LDExch();
			LCPolDB currLCPolDB = new LCPolDB();
			LCPolSet currLCPolSet = new LCPolSet();
			currLCPolSet = currLCPolDB.executeQuery(sqlbv26);
			double totalCurr = 0.00;
			for (int j = 1; j <= currLCPolSet.size(); j++) {
				totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet.get(j).getAmnt());
			}
			SumFGYMPrem = totalCurr;
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			SumFGYMPrem = Double.valueOf("0");
		}
		return SumFGYMPrem;
	}
	
	/**
	 * 既往告知事项不全为否
	 * */
	private String getOImpart(String tCustomerNo){
		String OImpart = "";
		String tOImpartSql = "select count(*) from lccustomerimpart where impartcode in"
						+ "('A0105','A0110','A0111e','A0111j','A0106','A0111a','A0111f',"
						+ "'A0112','A0107','A0111b','A0111g','A0108','A0111c','A0111h','A0109','A0111d',"
						+ "'A0111i','D0105','D0110a','D0110e','D0110i','D0106','D0110b','D0110f','D0110j',"
						+ "'D0107','D0110c','D0110g','D0111','D0108','D0110d','D0110h','D0122','D0109',"
						+ "'C0101','C0102','C0103','C0104','C0105','C0106','B0101','B0102','B0103','B0104',"
						+ "'A0113a','A0113b','D0112a','D0112b','A0114b','D0121','A0115a','A0115b','D0113a',"
						+ "'D0113b','A0117','A0118','D0115','D0116','D0117','C0108','A0512','A0513','A0510',"
						+"'A0511','A0516','A0517','A0514','A0515','A0507','A0506','A0509','A0508','A0505',"
						+"'A0504','A0524','A0526','A0525','A0522','A0519','A0518','A0520','A0532','A0529',"
						+"'A0530','A0528','B0504','B0505','B0503','B0501','B0502','B0506','B0508','B0507') "
						+"and customerno='"+"?customerno?"+"' and contno <> '"+"?contno?"+"'";
		SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
		sqlbv27.sql(tOImpartSql);
		sqlbv27.put("customerno", tCustomerNo);
		sqlbv27.put("contno", mContNo);
		String tOImpart = mExeSQL.getOneValue(sqlbv27);
		if(tOImpart!=null&&!tOImpart.equals("")&&Integer.parseInt(tOImpart)>0){
			OImpart = "1";
		}else{
			OImpart = "0";
		}
		return OImpart;
	}
	
	/**
	 * 查询被保人年收入
	 * */
	private Double getYearIncome(String tCustomerNo){
		Double YearIncome = null;
		YearIncome = Double.valueOf("0");
//		try {
//			//09-10-10 与陈军朝确定后被保人年收入逻辑改为如果投被关系为本人则取年收入数值最大的
//			//同时为了对豁免险的支持，查询的sql改为查0534的impartparamno in ('1','3')并且取最大值
//			String tYearIncomeSql = "select (case when max(A.x) is null then 0 else max(A.x) end) from (select (case when max(ImpartParam*1) is null then 0 else max(ImpartParam*1) end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode in ('A0120', 'D0119')"
//						+ " and impartver in ('A02', 'D02')"
//						+ " and impartparamno in ('1','3')"
//						+ " and contno = '"+"?contno?"+"'"
//						+ " and customerno = '"+"?customerno?"+"'"
//						+ " union"
//						+ " select (case when max(impartparam * 10000) is null then 0 else max(impartparam * 10000) end) x"
//						+ " from lccustomerimpartparams"
//						+ " where impartcode = 'A0534'"
//						+ " and impartparamno in ('1','3')"
//						+ " and contno = '"+"?contno?"+"'"
//						+ " and customerno = '"+"?customerno?"+"') A";
//			SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
//			sqlbv28.sql(tYearIncomeSql);
//			sqlbv28.put("customerno", tCustomerNo);
//			sqlbv28.put("contno", mContNo);
//			String tYearIncome = mExeSQL.getOneValue(sqlbv28);
//			if (!(tYearIncome == null || "".equals(tYearIncome.trim()))) {
//				YearIncome = Double.valueOf(tYearIncome);
//			}else{
//				YearIncome = Double.valueOf("0");
//			}
//		} catch (NumberFormatException e) {
//			CError.buildErr(this, "印刷号："+mContNo+"客户："+tCustomerNo+"年收入数据有误！");
//			e.printStackTrace();
//			YearIncome = Double.valueOf("0");
//		}
		return YearIncome;
	}
	
	/**
	 * 交通事故记录
	 * */
	private String getTrafficAccident(String tCustomerNo){
		String TrafficAccident = "";
		String tTrafficAccidentSql = "select (case count(1) when 0 then 0 else 1 end) from lccustomerimpart"
						+ " where impartcode in ('A0104','D0104','A0532')"
						+ " and contno='"+"?contno?"+"' and customerno='"+"?customerno?"+"'";
		SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
		sqlbv29.sql(tTrafficAccidentSql);
		sqlbv29.put("customerno", tCustomerNo);
		sqlbv29.put("contno", mContNo);
		TrafficAccident = mExeSQL.getOneValue(sqlbv29);
//		if(TrafficAccident==null||"".equals(TrafficAccident)){
//			TrafficAccident = "0";
//		}
		return TrafficAccident;
	}
	
//	/**
//	 * 本单财务风险保额
//	 * */
//	private Double getFinSumAmnt(tCustomerNo){
//		
//	}
	
	
	
	/**
	 * 补充告知问卷标记
	 * */
	private String getReinImpart(){
		String ReinImpart = "";
		String tReinImpartSql = 
			//09-09-27 与陈军朝确认后此处不取核保问卷   只取扫描件
//			"select decode(count(1),0,0,1) from LCQuestionnaire"
//						+ " where AskContentNo='026' and proposalcontno =("
//						+ " select proposalcontno from lccont where contno='"+mContNo+"'"
//						+ " )";
						"select (case count(1) when 0 then 0 else 1 end) from es_doc_main"
						+ " where subtype='UR212' and doccode ='"+"?contno?"+"' ";
		SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
		sqlbv30.sql(tReinImpartSql);
		sqlbv30.put("contno", mContNo);
		ReinImpart = mExeSQL.getOneValue(sqlbv30);
		return ReinImpart;
	}
	
	/**
	 * 备注栏的字数
	 * */
	private Double getRemarkCount(){
		Double RemarkCount = null;
		try {
			String tRemarkCountSql = "select (case when char_length(remark) is null then 0 else char_length(remark) end) from lccont"
							+" where contno='"+"?contno?"+"'";
			SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
			sqlbv31.sql(tRemarkCountSql);
			sqlbv31.put("contno", mContNo);
			String tRemarkCount = mExeSQL.getOneValue(sqlbv31);
			if (!(tRemarkCount == null || "".equals(tRemarkCount.trim()))) {
				RemarkCount = Double.valueOf(tRemarkCount);
			}else{
				RemarkCount = Double.valueOf("0");
			}
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号："+mContNo+"备注栏字数取数异常！");
			e.printStackTrace();
			RemarkCount = Double.valueOf("0");
		}
		return RemarkCount;
	}
	
	/**
	 * 是否有陪检记录
	 * */
	private String getAccoBodyCheck(){
		String AccoBodyCheck = "";
		String tAccoBodyCheckSql = "";
		if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
			tAccoBodyCheckSql = "select (case count(1) when 0 then 0 else 1 end) from lcpenotice"
					+" where agentname is not null and  rownum=1 and contno='"+"?contno?"+"'"
					+" order by makedate";
		}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
			tAccoBodyCheckSql = "select (case count(1) when 0 then 0 else 1 end) from lcpenotice"
					+" where agentname is not null  and contno='"+"?contno?"+"'"
					+" order by makedate limit 1";
		}
		
		SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
		sqlbv32.sql(tAccoBodyCheckSql);
		sqlbv32.put("contno", mContNo);
		AccoBodyCheck = mExeSQL.getOneValue(sqlbv32);
		return AccoBodyCheck;
	}
	
	/**
	 * 体检医院是否是定点医院
	 * */
	private String getIsAppointHos(){
		String IsAppointHos = "";
		String tIsAppointHosSql = "";
		if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
			tIsAppointHosSql = "select (case count(1) when 0 then 0 else 1 end) from ldhospital where hosstate='0'"
					+ " and hospitcode = ( select A.x from" 
					+ " ("
					+ " select hospitcode x from lcpenotice a where a.contno='"+"?contno?"+"'"
					+ " order by a.makedate desc"
					+ " )A where rownum=1"
					+ " )";
		}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
			tIsAppointHosSql = "select (case count(1) when 0 then 0 else 1 end) from ldhospital where hosstate='0'"
					+ " and hospitcode = ( select A.x from" 
					+ " ("
					+ " select hospitcode x from lcpenotice a where a.contno='"+"?contno?"+"'"
					+ " order by a.makedate desc"
					+ " )A limit 1"
					+ " )";
		}
		SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
		sqlbv33.sql(tIsAppointHosSql);
		sqlbv33.put("contno", mContNo);
		IsAppointHos = mExeSQL.getOneValue(sqlbv33);
		return IsAppointHos;
	}
	
	/**
	 * 系统抽检标记
	 * */
	private String getSpotCheckFlag(){
		String SpotCheckFlag = "";
		String tSpotCheckFlagSql = "select (case count(1) when 0 then 0 else 1 end) from bpomissionstate"
						+" where bussno='"+"?contno?"+"' and dealtype='01'";
		SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
		sqlbv34.sql(tSpotCheckFlagSql);
		sqlbv34.put("contno", mContNo);
		SpotCheckFlag = mExeSQL.getOneValue(sqlbv34);
		return SpotCheckFlag;
	}
	
	/**
	 * 生调回复人员是否与系统定义不一致
	 * */
	private String getMOpeIsNotDefined(){
		String MOpeIsNotDefined = "";
		String tMOpeIsNotDefinedSql = "";
		if(SysConst.DBTYPE_ORACLE.equals(SysConst.DBTYPE)){
			tMOpeIsNotDefinedSql = " select 1 from dual where"
					+ " (case when (select username from lduser where usercode =("
					+ " select A.x from ("
					+ " select replyoperator x from lcrreport where contno='"+"?contno?"+"'"
					+ "  order by makedate desc) A"
					+ " where rownum=1"
					+ " )) is null then 'xx' else (select username from lduser where usercode =("
					+ " select A.x from ("
					+ " select replyoperator x from lcrreport where contno='"+"?contno?"+"'"
					+ "  order by makedate desc) A"
					+ " where rownum=1"
					+ " )) end)=(case when ("
					+ " select A.x from ("
					+ " select remark x from lcrreport where contno='"+"?contno?"+"'"
					+ " order by makedate desc) A where rownum=1) is null then 'xx' else ("
					+ " select A.x from ("
					+ " select remark x from lcrreport where contno='"+"?contno?"+"'"
					+ " order by makedate desc) A where rownum=1) end)";
		}else if(SysConst.DBTYPE_MYSQL.equals(SysConst.DBTYPE)){
			tMOpeIsNotDefinedSql = " select 1 from dual where"
					+ " (case when (select username from lduser where usercode =("
					+ " select A.x from ("
					+ " select replyoperator x from lcrreport where contno='"+"?contno?"+"'"
					+ "  order by makedate desc) A"
					+ " limit 1"
					+ " )) is null then 'xx' else (select username from lduser where usercode =("
					+ " select A.x from ("
					+ " select replyoperator x from lcrreport where contno='"+"?contno?"+"'"
					+ "  order by makedate desc) A"
					+ " limit 1"
					+ " )) end)=(case when ("
					+ " select A.x from ("
					+ " select remark x from lcrreport where contno='"+"?contno?"+"'"
					+ " order by makedate desc) A limit 1) is null then 'xx' else ("
					+ " select A.x from ("
					+ " select remark x from lcrreport where contno='"+"?contno?"+"'"
					+ " order by makedate desc) A limit 1) end)";
		}
		SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
		sqlbv35.sql(tMOpeIsNotDefinedSql);
		sqlbv35.put("contno", mContNo);
		String tMOpeIsNotDefined = mExeSQL.getOneValue(sqlbv35);
		if(!"1".equals(tMOpeIsNotDefined)){
			MOpeIsNotDefined = "1";
		} else {
			MOpeIsNotDefined = "0";
		}
		return MOpeIsNotDefined;
	}
	
	/**
	 * 累计该险种保费
	 * @param   riskcode   insurdno
	 * */
	private Double getSumThisPrem(String tRiskCode,String tInsuredNo){
		Double SumThisPrem = null;
		try {
//			String tSumThisPremSql = "select sum(prem) from lcpol where"
//							+ " uwflag not in ('1','2','a') and appflag not in ('4','9')"
//							+ " and insuredno='"+tInsuredNo+"'"
//							+ " and riskcode='"+tRiskCode+"'";
//			String tSumThisPrem = mExeSQL.getOneValue(tSumThisPremSql);
//			if (!(tSumThisPrem == null || "".equals(tSumThisPrem.trim()))) {
//				SumThisPrem = Double.valueOf(tSumThisPrem);
//			}else{
//				SumThisPrem = Double.valueOf("0");
//			}
			//有多币种险种的情况
			String currString = "select * from lcpol where"
							+ " uwflag not in ('1','2','a') and appflag not in ('4','9')"
							+ " and insuredno='"+"?insuredno?"+"'"
							+ " and riskcode='"+"?riskcode?"+"'";
			SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
			sqlbv36.sql(currString);
			sqlbv36.put("insuredno", tInsuredNo);
			sqlbv36.put("riskcode", tRiskCode);
			LDExch tLDExch = new LDExch();
			LCPolDB currLCPolDB = new LCPolDB();
			LCPolSet currLCPolSet = new LCPolSet();
			currLCPolSet = currLCPolDB.executeQuery(sqlbv36);
			double totalCurr = 0.00;
			for (int j = 1; j <= currLCPolSet.size(); j++) {
				totalCurr += tLDExch.toBaseCur(currLCPolSet.get(j).getCurrency(), locCurrency, theCurrentDate,currLCPolSet.get(j).getPrem());
			}
			SumThisPrem = totalCurr ; 
			
		} catch (NumberFormatException e) {
			CError.buildErr(this, "印刷号："+mContNo+"客户："+tInsuredNo+"累计该险种保费数据有误！");
			e.printStackTrace();
			SumThisPrem = Double.valueOf("0");
		}
		return SumThisPrem;
	}
	
	/**
	 * 残疾事项告知
	 * */
	private String getDisabilityImpart(String tCustomerNo){
		String DisabilityImpart = "";
		String tDisabilityImpartSql = "select (case count(1) when 0 then 0 else 1 end) from lccustomerimpart"
						+ " where impartcode='A0508' and contno='"+"?contno?"+"' and customerno='"+"?customerno?"+"'";
		SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
		sqlbv37.sql(tDisabilityImpartSql);
		sqlbv37.put("customerno", tCustomerNo);
		sqlbv37.put("contno", mContNo);
		DisabilityImpart = mExeSQL.getOneValue(sqlbv37);
		return DisabilityImpart;
	}
	
	/**
	 * 取保单管理机构的前*位
	 * 
	 * 
	 * 如果个人保单的管理机构为6位时默认在后面补“4”(反正系统里面不会有以两个4结尾的机构，
	 * 所以就用4补齐了-_-||)
	 * 
	 * */
	private String getManagetCom(String tManageCom,int tLength){
		int strLen = tManageCom.length();

		StringBuffer strReturn = new StringBuffer();
		if (strLen > tLength) {
			strReturn.append(tManageCom.substring(0, tLength));
		} else {
			if (strLen == 0) {
				strReturn.append("");
			} else {
				strReturn.append(tManageCom);
			}

			for (int i = strLen; i < tLength; i++) {
				strReturn.append("4");
			}
		}
		return strReturn.toString();
	}
	
	/**
	 * 获得一个0~100的随机数
	 * */
	private Double getSamplingFactor(){
		Double SamplingFactor;
		Random rand = new Random();
		int tSelect = rand.nextInt(100)+1;//修改抽检因子为1至100的数字
		SamplingFactor = Double.valueOf(String.valueOf(tSelect));
		return SamplingFactor;
	}
	
	/**
	 * 保单的机构差异化等级
	 * */
	private String getContUWLevel(String tComCode){
		String tUWLevel = "";
		String getSql = "select case othersign when 'A' then 1 when 'B' then 2 when 'C' then 3 when 'D' then 4 end"
						+" from ldcode where codetype='station' and code='"+"?code?"+"'";
		SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
		sqlbv38.sql(getSql);
		sqlbv38.put("code", tComCode);
		ExeSQL tExeSQL = new ExeSQL();
		tUWLevel = tExeSQL.getOneValue(sqlbv38);
		return tUWLevel;
	}
	/**
	 * 获得个单进入自核的日期
	 */
	private String getUWDate(String tPrtNo){
		String tUWDate = "";
		//String getSql = "select makedate||' '||maketime  from lwmission where processid = '0000000003' and activityid = '0000001003' and missionprop1 = '"+tPrtNo+"'";
		String getSql = "select concat(makedate,concat(' ',maketime))  from lwmission where  activityid in (select activityid from lwactivity  where functionid ='10010005') and missionprop1 = '"+"?missionprop1?"+"'";		
		SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
		sqlbv39.sql(getSql);
		sqlbv39.put("missionprop1", tPrtNo);
		ExeSQL tExeSQL = new ExeSQL();
		tUWDate = tExeSQL.getOneValue(sqlbv39);
		return tUWDate;
	}
	/**
	 * 业务员差异化等级
	 * */
	private String getAgentUWLevel(String tAgentCode){
		String tUWLevel = "";
		String getSql = "select uwlevel from latree where agentcode='"+"?agentcode?"+"'";
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		sqlbv40.sql(getSql);
		sqlbv40.put("agentcode", tAgentCode);
		ExeSQL tExeSQL = new ExeSQL();
		tUWLevel = tExeSQL.getOneValue(sqlbv40);
		return tUWLevel;
	}
	
	/**
	 * 代理人与被保人关系
	 * */
	private String getRelToInsured(){
		String tRelToInsured = "";
		String tRelToInsuredSql = "select (case (select code from ldcode where codetype = 'relagenttoins' and codename = impartparammodle)"
						+ "when '' then '5' else (select code from ldcode where codetype = 'relagenttoins' and codename = impartparammodle) end)" 
						+ " from lccustomerimpart where impartver = 'A03' and impartcode='A0151'"
						+ " and prtno='"+"?prtno?"+"'";
		SQLwithBindVariables sqlbv41 = new SQLwithBindVariables();
		sqlbv41.sql(tRelToInsuredSql);
		sqlbv41.put("prtno", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		tRelToInsured = tExeSQL.getOneValue(sqlbv41);
		if("".equals(StrTool.cTrim(tRelToInsured))){
			//如果查询结果为空则认为是 6-否
			tRelToInsured = "6";
		}
		return tRelToInsured;
	}
	
	/**
	 * 投(被)保人联系电话与业务员联系电话是否一致
	 * 2010-3-26与陈军朝电话沟通 业务员电话取值为最新增加的告知项“业务员电话”
	 * @return 0-否  1-是
	 * */
	private String getSamePhone(String tCustomerno,String tAddressno){
		String tSamePhone = "0";
		String tPhone = "";
		String tMobile = "";
		String tAgentPhoneSql = "select impartparammodle from lccustomerimpart"
						+ " where impartver='A03' and impartcode='A0158' "
						+ " and prtno='"+"?prtno?"+"'";
		SQLwithBindVariables sqlbv42 = new SQLwithBindVariables();
		sqlbv42.sql(tAgentPhoneSql);
		sqlbv42.put("prtno", mContNo);
		ExeSQL tExeSQL = new ExeSQL();
		tPhone = tExeSQL.getOneValue(sqlbv42);
		String tSamePhoneSql = "";
		if(tPhone!=null&&!"".equals(tPhone)){
			if(!"".equals(tPhone.replaceAll("[^0-9]", ""))){
				tSamePhoneSql = " select 1 from lcaddress a where customerno='"
							+ "?customerno?" + "'"
							+ " and addressno='"+"?addressno?"+"' and ("
							+ " a.phone='"+"?phone?"+"' or a.mobile='"+"?phone?"+"')";
				SQLwithBindVariables sqlbv43 = new SQLwithBindVariables();
				sqlbv43.sql(tSamePhoneSql);
				sqlbv43.put("customerno", tCustomerno);
				sqlbv43.put("addressno", tAddressno);
				sqlbv43.put("phone", tPhone.replaceAll("[^0-9]", ""));

				tSamePhone = tExeSQL.getOneValue(sqlbv43);
				if("1".equals(tSamePhone)){
					return "1";
				}
			}
		} else {
			tSamePhone = "0";
		}
		return tSamePhone;
	}
	
	/**
	 * 一年内职业代码是否一致
	 * @return 0-否  1-是
	 * */
	private String getSameOccuCode(String tInsuredNo){
		String tSameOccuCode = "0";
		ExeSQL tExeSQL = new ExeSQL();
		String tCurrentDate = PubFun.getCurrentDate();
		String tOneYearBefore = PubFun.calDate(tCurrentDate, -1, "Y", "");
		String tSql = "select distinct occupationcode from lcinsured where"
						+ " insuredno='"+"?insuredno?"+"' "
						+ " and occupationcode is not null"
						+ " and (makedate>='"+"?date1?"+"' "
						+ " and makedate<='"+"?date2?"+"')";
		SQLwithBindVariables sqlbv44 = new SQLwithBindVariables();
		sqlbv44.sql(tSql);
		sqlbv44.put("insuredno", tInsuredNo);
		sqlbv44.put("date1", tOneYearBefore);
		sqlbv44.put("date2", tCurrentDate);
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv44);
		if(tSSRS.getMaxRow()>1){
			tSameOccuCode = "0";
		} else {
			tSameOccuCode = "1";
		}
		return tSameOccuCode;
	}
	
	/**
	 * 管理机构百团标记 
	 * 
	 * @return 0-否 1-是
	 * */
	private String getManaSpecFlag(String tManageCom){
		String tSql = "select (case when comareatype1 is null then '0' else comareatype1 end) from ldcom where "
						+ " comcode = '"+"?comcode?"+"'";
		SQLwithBindVariables sqlbv45 = new SQLwithBindVariables();
		sqlbv45.sql(tSql);
		sqlbv45.put("comcode", tManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		return tExeSQL.getOneValue(sqlbv45);
	}
	
	public static void main(String[] agrs) {
//		String tResult = "被保险人";
//		logger.debug(tResult.replaceAll("被保人|被保险人", "投保人"));
//		
//		GlobalInput tG = new GlobalInput();
//		tG.Operator = "001";
//		tG.ManageCom = "86";
//		tG.ComCode = "86";
//
//		TransferData tTransferData = new TransferData();
//		tTransferData.setNameAndValue("MissionID", "00000000000000354833");
//		tTransferData.setNameAndValue("SubMissionID", "1");
//		tTransferData.setNameAndValue("ActivityID", "0000001003");
//		tTransferData.setNameAndValue("ContNo", "32110000000035");
//
//		// 准备传输数据 VData
//		VData tVData = new VData();
//		tVData.add(tTransferData);
//		tVData.add(tG);
//
//		AutoUWCheckBL tAutoUWCheckBL = new AutoUWCheckBL();
//
//		String Content = "";
//		if (tAutoUWCheckBL.submitData(tVData, "") == false) {
//			int n = tAutoUWCheckBL.mErrors.getErrorCount();
//			logger.debug("n==" + n);
//			for (int j = 0; j < n; j++) {
//				logger.debug("Error: "
//						+ tAutoUWCheckBL.mErrors.getError(j).errorMessage);
//			}
//			Content = " 自动核保失败，原因是: "
//					+ tAutoUWCheckBL.mErrors.getError(0).errorMessage;
//		}
		ExeSQL riskSql = new ExeSQL();
		String tsql="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tsql = "select healthyamnt2('" + "?tInsuredNo?" + "','1','1') from dual";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tsql = "{ call healthyamnt2( ?#@d#?,'" + "?tInsuredNo?" + "','1','1') }";
		}
		SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
		sqlbv12.sql(tsql);
		sqlbv12.put("tInsuredNo", "0000015843");
		String tempAmnt = riskSql.getOneValue(sqlbv12);	
		logger.debug(tempAmnt);
	}

	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds(LCPolSchema tLCPolSchema) {
		// String tsql = "";
		String tRiskCode = tLCPolSchema.getRiskCode().trim();

		/**
		 * delete by wangxiongzhou 20071227
		 * 
		 * uwtype: 1--个单、团单险种的承保核保 11--个单、团单险种承保核保的公共规则 12--个单、团单人工核保上报核保级别
		 * 13--个单人工核保非标准体核保级别校验 14--个单人工核保拒保延期级别校验 15--团体下个人核保规则 16--团体下个人上报核保级别
		 * 19--个单、团单合同核保规则
		 */
		// 查询算法编码
		// if ((tRiskCode.equals("BILA11") || tRiskCode.equals("IADD21"))
		// && "3".equals(tLCPolSchema.getSaleChnl())) {
		// tsql = "select * from lmuw where (riskcode = '"
		// + tLCPolSchema.getRiskCode().trim()
		// + "' and relapoltype='B' and uwtype = '1') order by calcode";
		//
		// } else {
		// tsql = "select * from lmuw where (riskcode = '000000' and
		// relapoltype=(select mngcom from lmriskapp where riskcode='"
		// + tLCPolSchema.getRiskCode().trim()
		// + "') and uwtype = '11') or (riskcode='000000' and
		// relapoltype='A') or (riskcode = '"
		// + tLCPolSchema.getRiskCode().trim()
		// + "' and relapoltype=(select mngcom from lmriskapp where
		// riskcode='"
		// + tLCPolSchema.getRiskCode().trim()
		// + "') and uwtype = '1') order by calcode";
		// midify by wangxiongzhou 20071227
		// modify by liwb for ping 银保通需要跑特殊的规则 
		/*
		 * 公共规则和险种规则
		 */
		StringBuffer tsql = new StringBuffer();
//		tsql.append("select * from lmuw where (riskcode = '000000' and relapoltype=(select mngcom from lmriskapp where riskcode='");
//		tsql.append(tRiskCode);
//		tsql.append("') and uwtype = '11') or (riskcode = '");
//		tsql.append(tRiskCode);
//		tsql.append("' and relapoltype=(select mngcom from lmriskapp where riskcode='");
//		tsql.append(tRiskCode);
//		tsql.append("') and uwtype = '1') or (riskcode = '000000'");
//		tsql.append(" and relapoltype=(select mngcom from lmriskapp where riskcode='");
//		tsql.append(tRiskCode);
//		tsql.append("') and uwtype = 'LF')  order by calcode");
		String tYBTSql = "select mngcom from lmriskapp where riskcode='" + "?riskcode?" + "'";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv46 = new SQLwithBindVariables();
		sqlbv46.sql(tYBTSql);
		sqlbv46.put("riskcode", tRiskCode);
		String tRelaPolType = tExeSQL.getOneValue(sqlbv46);
		
		// add by liwb  20101013 2010-PRJ-0007 电销产品跑指定的核保规则
		if (mTransferData.getValueByName("YBTFlag") != null
				&& "DX".equals(mTransferData.getValueByName("YBTFlag"))) {
			LDCode1DB tLDCode1DB = new LDCode1DB();
			tLDCode1DB.setCodeType("dxuwcode");
			tLDCode1DB.setCode(tRiskCode);
			LDCode1Set tLDCode1Set = new LDCode1Set();
			tLDCode1Set = tLDCode1DB.query();
			String tUWCode = "";
			if (tLDCode1Set != null && tLDCode1Set.size() > 0) {
				int tLDCodeSetSize = tLDCode1Set.size();
				String tempUWCode = "";
				tUWCode = tUWCode + "('";
				for (int i = 1; i <= tLDCodeSetSize; i++) {
					if (i != tLDCodeSetSize) {
						tempUWCode = tLDCode1Set.get(i).getCode1();
						tUWCode = tUWCode + tempUWCode + "','";
					} else {
						tempUWCode = tLDCode1Set.get(i).getCode1();
						tUWCode = tUWCode + tempUWCode + "')";
					}
				}
			} else {
				tUWCode = "('')";
			}
			tsql.append("select * from lmuw where uwcode in ");
			tsql.append(tUWCode);
		}
		//IPA自核规则 by gujx 2010-2-20
		else if ("IPA22".equals(tRiskCode) || "IPA11".equals(tRiskCode)) {
			tsql.append("select * from lmuw where (uwcode='IU0005')");
			tsql.append(" or (riskcode = '" + "?riskcode?" + "'");
			tsql.append(" and uwcode in ('IPAU06','IPAU02')) order by calcode");
		//'B'为银代险，其中包括银保通和普通的银代险，通过passflag区分，'Y'的为银保通，'B'为普通的银代险
		}else if(tRelaPolType != null && "B".equals(tRelaPolType))
		{
			if (mTransferData.getValueByName("YBTFlag") != null && "YBT".equals(mTransferData.getValueByName("YBTFlag")))
			{
				tsql.append("select * from lmuw where (riskcode = '000000' and relapoltype= '");
				tsql.append("?relapoltype?");
				tsql.append("' and uwtype = '11' and (passflag='Y' or passflag='A')) or (riskcode = '");
				tsql.append("?riskcode?");
				tsql.append("' and relapoltype='");
				tsql.append("?relapoltype?");
				tsql.append("' and uwtype = '1' and (passflag='Y' or passflag='A')) or (riskcode = '000000'");
				tsql.append(" and relapoltype='");
				tsql.append("?relapoltype?");
				tsql.append("' and uwtype = 'LF' and (passflag='Y' or passflag='A'))  order by calcode");
			}
			else
			{
				tsql.append("select * from lmuw where (riskcode = '000000' and relapoltype= '");
				tsql.append("?relapoltype?");
				tsql.append("' and uwtype = '11' and (passflag='B' or passflag='A')) or (riskcode = '");
				tsql.append("?riskcode?");
				tsql.append("' and relapoltype='");
				tsql.append("?relapoltype?");
				tsql.append("' and uwtype = '1' and (passflag='B' or passflag='A')) or (riskcode = '000000'");
				tsql.append(" and relapoltype='");
				tsql.append("?relapoltype?");
				tsql.append("' and uwtype = 'LF' and (passflag='B' or passflag='A'))  order by calcode");
			}
		}
		else
		{
			tsql.append("select * from lmuw where (riskcode = '000000' and relapoltype= '");
			tsql.append("?relapoltype?");
			tsql.append("' and uwtype = '11') or (riskcode = '");
			tsql.append("?riskcode?");
			tsql.append("' and relapoltype='");
			tsql.append("?relapoltype?");
			tsql.append("' and uwtype = '1') or (riskcode = '000000'");
			tsql.append(" and relapoltype='");
			tsql.append("?relapoltype?");
			tsql.append("' and uwtype = 'LF')  order by calcode");
		}
		// end 2009-8-27
		// }
		logger.debug("新契约自动核保->获取自核规则编码语句：" + tsql.toString());
		SQLwithBindVariables sqlbv47 = new SQLwithBindVariables();
		sqlbv47.sql(tsql.toString());
		sqlbv47.put("riskcode", tRiskCode);
		sqlbv47.put("relapoltype", tRelaPolType);
		LMUWDB tLMUWDB = new LMUWDB();

		LMUWSet tLMUWSet = tLMUWDB.executeQuery(sqlbv47);
		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "CheckKinds";
			tError.errorMessage = tLCPolSchema.getRiskCode().trim()
					+ "险种核保信息查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();
			return null;
		}
		return tLMUWSet;
	}
	
	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckPolInit(LCPolSchema tLCPolSchema) {
		// 获取险种类别
		String tRiskAmntType = getRiskAmntType(tLCPolSchema.getRiskCode());
		logger.debug("InsuredNo=" + tLCPolSchema.getInsuredNo());
		LCInsuredSchema tLCInsuredSchema = getInsuredInfo(tLCPolSchema
				.getInsuredNo(), tLCPolSchema.getContNo());

		if ("13".equals(tRiskAmntType)) {
			mISAdoptAppnt = true;
		} else {
			mISAdoptAppnt = false;
		}

		int tInsuAge = 0;
		String tInsuSex = "";
		String tJob = "";
		String tInsuredNo = "";
		String tRelationToAppnt = "";
		String tOccupationType = "";

		if (mISAdoptAppnt) {
			tInsuAge = mAppntAge;
			tInsuSex = mLCAppntSchema.getAppntSex();
			tJob = mLCAppntSchema.getOccupationCode();
			tInsuredNo = mLCAppntSchema.getAppntNo();
			tRelationToAppnt = "00";
		} else {
			tInsuAge = tLCPolSchema.getInsuredAppAge();
			tInsuSex = tLCPolSchema.getInsuredSex();
			tJob = tLCInsuredSchema.getOccupationCode();
			tInsuredNo = tLCPolSchema.getInsuredNo();
			tRelationToAppnt = tLCInsuredSchema.getRelationToAppnt();
			tOccupationType = tLCPolSchema.getOccupationType();
		}

		mCalBase = new CalBase();
		mCalBase.setPrem(tLCPolSchema.getPrem());
		mCalBase.setGet(tLCPolSchema.getAmnt());
		mCalBase.setMult(tLCPolSchema.getMult());

		mCalBase.setAppAge(tInsuAge);
		mCalBase.setSex(tInsuSex);
		mCalBase.setJob(tJob);
		mCalBase.setCount(tLCPolSchema.getInsuredPeoples());
		mCalBase.setPolNo(tLCPolSchema.getPolNo());
		mCalBase.setContNo(mContNo);
		mCalBase.setCValiDate(tLCPolSchema.getCValiDate());
		mCalBase.setInsuYear(tLCPolSchema.getInsuYear());
		mCalBase.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
		mCalBase.setPayEndYear(tLCPolSchema.getPayEndYear());
		mCalBase.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
		mCalBase.setGetYear(tLCPolSchema.getGetYear());
		mCalBase.setGetYearFlag(tLCPolSchema.getGetYearFlag());
		mCalBase.setYears(tLCPolSchema.getYears());
		mCalBase.setOccupation(tOccupationType);
		// add by yaory
		// begin calculate something.........
		if (deal(tLCPolSchema) != "1") { // 查找fisrtPeople、secondPeople并返回"1"。
			logger.debug("error");
		}
		// mCalBase.setInsuredNo(tLCPolSchema.getInsuredNo());
		mCalBase.setInsuredNo(tInsuredNo);
		mCalBase.setSecondInsuredNo(secondPeople);
		// 查询第二被保人年龄

		mCalBase.setAppAg2("" + queryAge(secondPeople, tLCPolSchema, "I"));
		// 投保人
		mCalBase.setAppAge2("" + queryAge("", tLCPolSchema, "A"));

		mCalBase.setMainPolNo(tLCPolSchema.getMainPolNo());
//		if (mBankInsu == null || mBankInsu.equals("0")) {
//			// 得到以下各种累计危险保额
//			GetAllSumAmnt(tLCPolSchema);
//		}
		
		// mCalBase.setLSumDangerAmnt(String.valueOf(LSumDangerAmnt)); //
		// 同一被保险人下寿险类的累计危险保额
		// logger.debug(LSumDangerAmnt);
		
		// mCalBase.setDSumDangerAmnt(String.valueOf(DSumDangerAmnt)); //
		// 同一被保险人下重大疾病类的累计危险保额
		// mCalBase.setASumDangerAmnt(String.valueOf(ASumDangerAmnt)); //
		// 同一被保险人下人身意外伤害类的累计危险保额
		// mCalBase.setMSumDangerAmnt(String.valueOf(MSumDangerAmnt)); //
		// 同一被保险人下人身意外医疗类的累计危险保额
		// mCalBase.setSSumDangerAmnt(String.valueOf(SSumDangerAmnt)); //
		// 同一被保险人下人身意外医疗类的累计危险保额
		mCalBase.setManageCom(tLCPolSchema.getManageCom());
		mCalBase.setAppntJob(mLCAppntSchema.getOccupationCode()); // 投保人职业类别
//		mCalBase.setMainRiskGet(GetMainAmnt(tLCPolSchema)); // 是附加险--//主险保额
		mCalBase.setRiskSort(tRiskAmntType); // 险种类别
//		mCalBase.setLifeFlag(mLifeFlag);
//		mCalBase.setRiskChannel(tLCPolSchema.getSaleChnl());
//		mCalBase.setAppntNo(tLCPolSchema.getAppntNo());
		mCalBase.setAppntAge(mAppntAge);
//		mCalBase.setRelationToAppnt(tRelationToAppnt);
//		mCalBase.setAppntNativePlace(mLCAppntSchema.getNativePlace());
//		mCalBase.setInsuredNativePlace(tLCInsuredSchema.getNativePlace());
		mCalBase.setPayIntv(tLCPolSchema.getPayIntv());

		// logger.debug("险种分类=====" + GetRiskcode(tLCPolSchema));
           
		// add end
		//FEC检核名称与国家,接口设置 add by wanglili 
        //      调用FEC接口方法
		 try {
             // 获取IP相关参数
             String IPSql = "select codename,codealias,othersign from ldcode where codetype = 'FECIP'";
             SQLwithBindVariables sqlbv48 = new SQLwithBindVariables();
     		 sqlbv48.sql(IPSql);
             ExeSQL tExeSQL = new ExeSQL();
             SSRS tSSRS = tExeSQL.execSQL(sqlbv48);
             if (tExeSQL.mErrors.needDealError()) {
                 CError.buildErr(this, "IP相关参数查询失败!");
                 return;
             }
             logger.debug(tSSRS.GetText(1, 1));
             logger.debug(tSSRS.GetText(1, 2));
             logger.debug(tSSRS.GetText(1, 3));
             String IP = tSSRS.GetText(1, 1);
             String Port = tSSRS.GetText(1, 2);
             String Type = tSSRS.GetText(1, 3);

             /*List result = new WSClient().checkCountryCode(IP, Port,
                    new String[] { mLCAppntSchema.getNativePlace() }, Type);
             logger.debug("投保人国家="+mLCAppntSchema.getNativePlace());
             if (result.size()>0)
                mCalBase.setFECAppNativePlace("0"); 
             else
            	mCalBase.setFECAppNativePlace("1"); 
             List result1 = new WSClient().checkCountryCode(IP, Port,
                     new String[] {tLCInsuredSchema.getNativePlace()}, Type);
             logger.debug("被保人国家="+tLCInsuredSchema.getNativePlace());
             if (result1.size()>0)
               mCalBase.setFECInsuNativePlace("0"); 
             else
               mCalBase.setFECInsuNativePlace("1"); 
             List result2 = new WSClient().checkPeopleName(IP, Port,
                     new String[] { mLCAppntSchema.getAppntName() });
              logger.debug("投保人姓名="+mLCAppntSchema.getAppntName());
              if (result2.size()>0)
                mCalBase.setFECAppCustomerName("0"); 
              else
            	mCalBase.setFECAppCustomerName("1");  
              List result3 = new WSClient().checkPeopleName(IP, Port,
                      new String[] {tLCInsuredSchema.getName()});
              logger.debug("被保人姓名="+tLCInsuredSchema.getName());
              if (result3.size()>0)
                mCalBase.setFECInsuCustomerName("0"); 
              else 
                mCalBase.setFECInsuCustomerName("1"); */
              } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
          }
	}
	/**
	 * 获取险种按照自动核保（风险累计分类）分类类型
	 * @param cRiskCode 险种编码
	 * @return 返回险种按照自动核保（风险累计分类）分类类型编码
	 */
	private String getRiskAmntType(String cRiskCode) {
		mLifeFlag = "0";
		ExeSQL tExeSQL = new ExeSQL();
		String tSQL = "";
		String tResult = "";
		SSRS tSSRS = new SSRS();

		tSQL = "select risksortvalue from lmrisksort where " + "riskcode='"
				+ "?riskcode?" + "' and risksorttype='2'";
		SQLwithBindVariables sqlbv49 = new SQLwithBindVariables();
		sqlbv49.sql(tSQL);
		sqlbv49.put("riskcode", cRiskCode);
		tSSRS = tExeSQL.execSQL(sqlbv49);

		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				String tTmpType = tSSRS.GetText(i, 1);
				if ("15".equals(tTmpType)) {
					mLifeFlag = "1";
				} else {
					tResult = tTmpType;
				}
			}
		}

		if (tResult == null || tResult.equals("")) {
			tResult = "0";
		}

		return tResult;
	}
	
	/**
	 * 获取被保人信息
	 * @param cInsuredNo 被保人客户编号
	 * @param cContNo 保单号
	 * @return 返回被保人Schema
	 */
	private LCInsuredSchema getInsuredInfo(String cInsuredNo, String cContNo) {
		String sql = "";
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		sql = "select * From lcinsured where InsuredNo='" + "?InsuredNo?"
				+ "' and ContNo='" + "?ContNo?" + "'";
		SQLwithBindVariables sqlbv50 = new SQLwithBindVariables();
		sqlbv50.sql(sql);
		sqlbv50.put("InsuredNo", cInsuredNo);
		sqlbv50.put("ContNo", cContNo);
		tLCInsuredSet = tLCInsuredDB.executeQuery(sqlbv50);
		if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
			this.buildError("getInsuredInfo", "被保人查询失败！");
			return null;
		}
		tLCInsuredSchema = tLCInsuredSet.get(1);

		return tLCInsuredSchema;

	}
	

	private int calAppAge(String cstartDate, String cendDate, String unit) {
		FDate fDate = new FDate();
		Date startDate = fDate.getDate(cstartDate);
		Date endDate = fDate.getDate(cendDate);
		if (fDate.mErrors.needDealError()) {
			return 0;
		}

		int interval = 0;

		GregorianCalendar sCalendar = new GregorianCalendar();
		sCalendar.setTime(startDate);
		int sYears = sCalendar.get(Calendar.YEAR);
		int sMonths = sCalendar.get(Calendar.MONTH);
		int sDays = sCalendar.get(Calendar.DAY_OF_MONTH);

		GregorianCalendar eCalendar = new GregorianCalendar();
		eCalendar.setTime(endDate);
		int eYears = eCalendar.get(Calendar.YEAR);
		int eMonths = eCalendar.get(Calendar.MONTH);
		int eDays = eCalendar.get(Calendar.DAY_OF_MONTH);

		if (StrTool.cTrim(unit).equals("Y")) {
			interval = eYears - sYears;

			if (eMonths < sMonths) {
				interval--;
			} else {
				if (eMonths == sMonths && eDays < sDays) {
					interval--;
				}
			}
		}
		if (StrTool.cTrim(unit).equals("M")) {
			interval = eYears - sYears;
			interval *= 12;
			interval += eMonths - sMonths;

			if (eDays < sDays) {
				interval--;
				int maxDate = eCalendar.getActualMaximum(Calendar.DATE);
				if (eDays == maxDate) {
					interval++;
				}
			}
		}
		if (StrTool.cTrim(unit).equals("D")) {

			sCalendar.set(sYears, sMonths, sDays);
			eCalendar.set(eYears, eMonths, eDays);
			long lInterval = (eCalendar.getTime().getTime() - sCalendar
					.getTime().getTime()) / 86400000;
			interval = (int) lInterval;

		}
		return interval;
	}

	private String deal(LCPolSchema tLCPolSchema) {
		String sql = "";
		sql = "select insuredno,sequenceno From lcinsured where contno='"
				+ "?contno?" + "'";
		SQLwithBindVariables sqlbv51 = new SQLwithBindVariables();
		sqlbv51.sql(sql);
		sqlbv51.put("contno", tLCPolSchema.getContNo());
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = exeSql.execSQL(sqlbv51);
		if (tSSRS.MaxRow == 0) {
			fisrtPeople = "0000000000"; // 如果没有查询到设置一个
			secondPeople = "0000000000";
		} else if (tSSRS.MaxRow == 1) {
			fisrtPeople = tSSRS.GetText(1, 1);
			secondPeople = "0000000000";
		} else {
			if (tSSRS.GetText(1, 2).equals("1")) {
				fisrtPeople = tSSRS.GetText(1, 1);
				secondPeople = tSSRS.GetText(2, 1);
			} else {
				fisrtPeople = tSSRS.GetText(2, 1);
				secondPeople = tSSRS.GetText(1, 1);
			}
		}
		return "1";
	}
	
	/**
	 * 查询被保人或投保人年龄
	 * @param InsuredNo 被保人客户号
	 * @param tLCPolSchema 保单险种信息
	 * @param flag 被保人标识
	 * @return 当被保人标识为“I”时，返回被保人年龄，其他返回投保人年龄
	 */
	private int queryAge(String InsuredNo, LCPolSchema tLCPolSchema, String flag) {
		String sql = "";
		if (flag != null && flag.equals("I")) {
			sql = "select birthday From lcinsured where InsuredNo='"
					+ "?InsuredNo?" + "' and ContNo='" + "?ContNo?"
					+ "'";
		} else {
			sql = "select appntBirthday From lcappnt where ContNo='"
					+ "?ContNo?" + "'";
		}
		SQLwithBindVariables sqlbv52 = new SQLwithBindVariables();
		sqlbv52.sql(sql);
		sqlbv52.put("InsuredNo", InsuredNo);
		sqlbv52.put("ContNo", tLCPolSchema.getContNo());
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = exeSql.execSQL(sqlbv52);
		int tAppAge = 0;
		if (tSSRS.MaxRow == 1) {
			tAppAge = PubFun.calInterval(tSSRS.GetText(1, 1), mCalBase
					.getCValiDate(), "Y");
		}
		return tAppAge;
	}
	
	/**
	 * 错误信息处理方法
	 * @param cFuncName 方法名
	 * @param cMsg 错误信息
	 */
	private void buildError(String cFuncName, String cMsg) {
		CError tError = new CError();
		tError.moduleName = "AutoUWCheckBL";
		tError.functionName = cFuncName;
		tError.errorMessage = cMsg;
		this.mErrors.addOneError(tError);
	}
	
	/**
	 * 个人单核保 输出：如果发生错误则返回false,否则返回true
	 */
	private String CheckPol(String tInsuredNo, String tRiskCode) { // LCPolSchema
		// tLCPolSchema)
		// 计算
		Calculator mCalculator = new Calculator();
		mCalculator.setBOMList(this.mBomList);
		mCalculator.setCalCode(mCalCode);
		// 增加基本要素
		mCalculator.addBasicFactor("Get", mCalBase.getGet());
		mCalculator.addBasicFactor("Amnt", mCalBase.getGet());//add by tansz on 20111207 for 增加该要素
		mCalculator.addBasicFactor("Mult", mCalBase.getMult());
		mCalculator.addBasicFactor("Prem", mCalBase.getPrem());
		mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge());
		mCalculator.addBasicFactor("Sex", mCalBase.getSex());
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("OccupationType", mCalBase.getOccupation());
		mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear());
		mCalculator.addBasicFactor("PayEndYearFlag", mCalBase
				.getPayEndYearFlag());
		mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear());
		mCalculator.addBasicFactor("GetYearFlag", mCalBase.getGetYearFlag());
		mCalculator.addBasicFactor("GetStartDate", "");
		mCalculator.addBasicFactor("Years", mCalBase.getYears());
		mCalculator.addBasicFactor("Grp", "");
		mCalculator.addBasicFactor("GetFlag", "");
		mCalculator.addBasicFactor("CValiDate", mCalBase.getCValiDate());
		mCalculator.addBasicFactor("Count", mCalBase.getCount());
		mCalculator.addBasicFactor("FirstPayDate", "");
		mCalculator.addBasicFactor("ContNo", mCalBase.getContNo());
		mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo());
		// mCalculator.addBasicFactor("InsuredNo", tInsuredNo);
		// //tLCPolSchema.getInsuredNo());;
		mCalculator.addBasicFactor("InsuredNo", mCalBase.getInsuredNo());
		mCalculator.addBasicFactor("SecondInsuredNo", mCalBase
				.getSecondInsuredNo());
		mCalculator.addBasicFactor("RiskCode", tRiskCode); // tLCPolSchema.getRiskCode());;

		// start add by wangxiongzhou
		/*mCalculator.addBasicFactor("AddAddcAmnt", mCalBase.getAddAddcAmnt());
		mCalculator.addBasicFactor("DisAmnt", mCalBase.getDisAmnt());
		mCalculator
				.addBasicFactor("AptcIaptcAmnt", mCalBase.getAptcIaptcAmnt());
		mCalculator.addBasicFactor("IpaIpacAmnt", mCalBase.getIpaIpacAmnt());

		mCalculator.addBasicFactor("LifeAmnt", mCalBase.getLifeAmnt());
		mCalculator.addBasicFactor("LifeMedAmnt", mCalBase.getLifeMedAmnt());
		mCalculator.addBasicFactor("AccAmnt", mCalBase.getAccAmnt());
		mCalculator.addBasicFactor("MedAmnt", mCalBase.getMedAmnt());
		mCalculator.addBasicFactor("AppntUniPrem", mCalBase.getAppntUniPrem());
		mCalculator.addBasicFactor("AppntInvPrem", mCalBase.getAppntInvPrem());
		mCalculator
				.addBasicFactor("AppntLifePrem", mCalBase.getAppntLifePrem());
		mCalculator
				.addBasicFactor("AppntBankPrem", mCalBase.getAppntBankPrem());
		mCalculator.addBasicFactor("LifePhysicalAmnt", mCalBase.getLifePhysicalAmnt());
		mCalculator.addBasicFactor("LifeMedPhysicalAmnt", mCalBase.getLifeMedPhysicalAmnt());*/
		// end add

		// //////////add by yaor for riskamnt///////////
		mCalculator.addBasicFactor("Job", mCalBase.getJob());
		mCalculator.addBasicFactor("LSumDangerAmnt", mCalBase
				.getLSumDangerAmnt());
		mCalculator.addBasicFactor("DSumDangerAmnt", mCalBase
				.getDSumDangerAmnt());
		mCalculator.addBasicFactor("ASumDangerAmnt", mCalBase
				.getASumDangerAmnt());
		mCalculator.addBasicFactor("MSumDangerAmnt", mCalBase
				.getMSumDangerAmnt());
		mCalculator.addBasicFactor("ManageCom", mCalBase.getManageCom());
		mCalculator.addBasicFactor("AppntJob", mCalBase.getAppntJob());
		mCalculator.addBasicFactor("MainRiskGet", mCalBase.getMainRiskGet());
		mCalculator.addBasicFactor("RiskSort", mCalBase.getRiskSort());
		mCalculator.addBasicFactor("CustomerNo", mCalBase.getCustomerNo());
		mCalculator.addBasicFactor("MainPolNo", mCalBase.getMainPolNo());
		mCalculator.addBasicFactor("AppAg2", mCalBase.getAppAg2());
		mCalculator.addBasicFactor("AppAge2", mCalBase.getAppAge2());
		//mCalculator.addBasicFactor("AppntAge", mCalBase.getAppntAge());
		mCalculator.addBasicFactor("InsuYear", mCalBase.getInsuYear());
		mCalculator.addBasicFactor("InsuYearFlag", mCalBase.getInsuYearFlag());
		//mCalculator.addBasicFactor("LifeFlag", mCalBase.getLifeFlag());
		//mCalculator.addBasicFactor("RelationToAppnt", mCalBase.getRelationToAppnt());
		//mCalculator.addBasicFactor("AppntNativePlace", mCalBase.getAppntNativePlace());
		//mCalculator.addBasicFactor("InsuredNativePlace", mCalBase.getInsuredNativePlace());
		mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv());
		//mCalculator.addBasicFactor("SaleChnl", mCalBase.getRiskChannel());
		//mCalculator.addBasicFactor("PayYears", String.valueOf(mCalBase.getPayYears()));
		//mCalculator.addBasicFactor("AppntNo", mCalBase.getAppntNo());

		// /////////add end
		
		// add by liwb 2009-9-1 for ping 为抽样体检设置要素
		String tSql = "select distinct(code) from ldcode1 where codetype = 'ispetype'";
		SQLwithBindVariables sqlbv53 = new SQLwithBindVariables();
		sqlbv53.sql(tSql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv53);
		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
				if (mCalBase.getOtherParmVlaueByName("IsPEFlag" + tSSRS.GetText(i, 1)) != null
						&& "1".endsWith(mCalBase.getOtherParmVlaueByName("IsPEFlag" + tSSRS.GetText(i, 1)))) {
					mCalculator.addBasicFactor("IsPEFlag" + tSSRS.GetText(i, 1), "1");
				}
				else {
					mCalculator.addBasicFactor("IsPEFlag" + tSSRS.GetText(i, 1), "0");
				}
			}
		}
		// end 2009-9-1
		//add by wll 2009-9-29 for ping FEC检核国家及保险人提供判断要素
//      mCalculator.addBasicFactor("FECAppNativePlace", mCalBase
//				.getFECAppNativePlace());
//		mCalculator.addBasicFactor("FECInsuNativePlace", mCalBase
//				.getFECInsuNativePlace());
//		mCalculator.addBasicFactor("FECAppCustomerName", mCalBase
//				.getFECAppCustomerName());
//		mCalculator.addBasicFactor("FECInsuCustomerName", mCalBase
//				.getFECInsuCustomerName());
        // end 2009-9-29
		String tStr = "";
		tStr = mCalculator.calculate();
		if (tStr == null || tStr.trim().equals("")) {
			mValue = "0";
		} else {
			mValue = tStr;
		}
		if(!mValue.equals("0"))
		{
			String tUWGrade="";
//			String tUWGrade = ((SQLTaskResult)mCalculator.getCalResult().get(mCalCode)).getUWLevel();
			return tUWGrade="A01";

		}
		else
		{
			return "";
		}
	}
	
	
	
	private String getCustPE(String cCustNo) {
		String str = (String) m_custPEInfo.get(cCustNo);
		if (str != null && !str.equals("")) {
			return str;
		} else {
			return "";
		}
	}
	private void setCustPE(String cCustNo, String cValue) {
		m_custPEInfo.put(cCustNo, String.valueOf(cValue));
	}
	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCPolSchema
	 *            LCPolSchema
	 * @param tLMUWSetUnpass
	 *            LMUWSetUnpass
	 * @return boolean
	 */
	private boolean dealOnePol(LCPolSchema tLCPolSchema, LMUWSet tLMUWSetUnpass) {
		// 保单
		if (preparePol(tLCPolSchema) == false) { // 设置tLCPolSchema数据。
			return false;
		}
		// 核保信息
		if (preparePolUW(tLCPolSchema, tLMUWSetUnpass) == false) { // 准备险种核保主表和核保子表的信息。
			return false;
		}

		LCPolSchema tLCPolSchemaDup = new LCPolSchema();
		tLCPolSchemaDup.setSchema(tLCPolSchema);
		mAllLCPolSet.add(tLCPolSchemaDup);

		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet.set(mLCUWMasterSet);
		mAllLCUWMasterSet.add(tLCUWMasterSet);

		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet.set(mLCUWSubSet);
		mAllLCUWSubSet.add(tLCUWSubSet);

		LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
		tLCUWErrorSet.set(mLCUWErrorSet);
		mAllLCErrSet.add(tLCUWErrorSet);

		return true;
	}
	/**
	 * 核保险种信息校验,准备核保算法 输出：如果发生错误则返回false,否则返回true
	 */
	private LMUWSet CheckKinds4(LCPolSchema tLCPolSchema) {
		String tsql = "";
		LMUWSchema tLMUWSchema = new LMUWSchema();
		// 查询算法编码
		tsql = "select * from lmuw where riskcode = '000000' and relapoltype in (select mngcom from lmriskapp where riskcode='"
				+ "?riskcode?" + "') and uwtype = '19'";
		SQLwithBindVariables sqlbv54 = new SQLwithBindVariables();
		sqlbv54.sql(tsql);
		sqlbv54.put("riskcode", tLCPolSchema.getRiskCode().trim());
		LMUWDB tLMUWDB = new LMUWDB();

		LMUWSet tLMUWSet = tLMUWDB.executeQuery(sqlbv54);
		if (tLMUWDB.mErrors.needDealError() == true) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLMUWDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAutoChkBL";
			tError.functionName = "CheckKinds3";
			tError.errorMessage = "合同险种核保信息查询失败!";
			this.mErrors.addOneError(tError);
			tLMUWSet.clear();
			return null;
		}
		return tLMUWSet;
	}
	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol(LCPolSchema tLCPolSchema) {
		logger.debug("险种核保标志" + mPolPassFlag);
		tLCPolSchema.setUWFlag(mPolPassFlag); // 险种自核不通过的在数据表中也存为保准体，这样核保师可能根据审核主界面就能对合同进行核保结论下发，而不用进入险种界面
		tLCPolSchema.setUWCode(mOperator);
		tLCPolSchema.setUWDate(PubFun.getCurrentDate());
		tLCPolSchema.setUWTime(PubFun.getCurrentTime());
		tLCPolSchema.setModifyDate(PubFun.getCurrentDate());
		tLCPolSchema.setModifyTime(PubFun.getCurrentTime());
		
		//用sql语句更新，不用Schema 20080818 因为可能与客户合并同时执行，保存了错误的客户数据	
		String updatepolsql = "update lcpol set UWFlag='" + "?UWFlag?"
							 + "',UWCode='" + "?UWCode?"
							 + "',UWDate='" + "?getCurrentDate?"
							 + "',UWTime='" + "?getCurrentTime?"
							 + "',ModifyDate='" + "?getCurrentDate?"
							 + "',ModifyTime='" + "?getCurrentTime?"
							 + "' where polno='" + "?polno?" +"'";
		SQLwithBindVariables sqlbv55 = new SQLwithBindVariables();
		sqlbv55.sql(updatepolsql);
		sqlbv55.put("UWFlag", mPolPassFlag);
		sqlbv55.put("UWCode", mOperator);
		sqlbv55.put("getCurrentDate", PubFun.getCurrentDate());
		sqlbv55.put("getCurrentTime", PubFun.getCurrentTime());
		sqlbv55.put("polno", tLCPolSchema.getPolNo());
		updatemap.put(sqlbv55, "UPDATE");
		
		return true;
	}

	/**
	 * 准备险种核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePolUW(LCPolSchema tLCPolSchema,LMUWSet tLMUWSetUnpass) {
		int tuwno = 0;
		int batchNo=0;
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
		tLCUWMasterDB.setPolNo(mOldPolNo);
		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		tLCUWMasterSet = tLCUWMasterDB.query();
		if (tLCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int n = tLCUWMasterSet.size();
		if (n == 0) {
			tLCUWMasterSchema.setContNo(mContNo);
			tLCUWMasterSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
			tLCUWMasterSchema.setPolNo(mOldPolNo);
			tLCUWMasterSchema.setProposalContNo(mPContNo);
			tLCUWMasterSchema.setProposalNo(tLCPolSchema.getProposalNo());
			tLCUWMasterSchema.setUWNo(1);
			tLCUWMasterSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
			tLCUWMasterSchema.setInsuredName(tLCPolSchema.getInsuredName());
			tLCUWMasterSchema.setAppntNo(tLCPolSchema.getAppntNo());
			tLCUWMasterSchema.setAppntName(tLCPolSchema.getAppntName());
			tLCUWMasterSchema.setAgentCode(tLCPolSchema.getAgentCode());
			tLCUWMasterSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
			tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCUWMasterSchema.setPostponeDay("");
			tLCUWMasterSchema.setPostponeDate("");
			tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCUWMasterSchema.setState(mPolPassFlag);
			tLCUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCUWMasterSchema.setHealthFlag("0");
			tLCUWMasterSchema.setSpecFlag("0");
			tLCUWMasterSchema.setQuesFlag("0");
			tLCUWMasterSchema.setReportFlag("0");
			tLCUWMasterSchema.setChangePolFlag("0");
			tLCUWMasterSchema.setPrintFlag("0");
			tLCUWMasterSchema.setManageCom(tLCPolSchema.getManageCom());
			tLCUWMasterSchema.setUWIdea("");
			tLCUWMasterSchema.setUpReportContent("");
			tLCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else if (n == 1) {
			tLCUWMasterSchema = tLCUWMasterSet.get(1);

			tuwno = tLCUWMasterSchema.getUWNo();
			tuwno = tuwno + 1;

			tLCUWMasterSchema.setUWNo(tuwno);
			tLCUWMasterSchema.setProposalContNo(mPContNo);
			tLCUWMasterSchema.setState(mPolPassFlag);
			tLCUWMasterSchema.setPassFlag(mPolPassFlag);
			tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人核保总表取数据不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}


		// 核保轨迹表
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		tLCUWSubDB.setPolNo(mOldPolNo);
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		tLCUWSubSet = tLCUWSubDB.query();
		if (tLCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int m = tLCUWSubSet.size();
		if (m > 0) {
			tLCUWSubSchema.setUWNo(++m); // 第几次核保
		} else {
			tLCUWSubSchema.setUWNo(1); // 第1次核保
		}
		

		tLCUWSubSchema.setContNo(mContNo);
		tLCUWSubSchema.setPolNo(mOldPolNo);
		tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
		tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema.getProposalContNo());
		tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
		tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
		tLCUWSubSchema.setInsuredName(tLCUWMasterSchema.getInsuredName());
		tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
		tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
		tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
		tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
		tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
		tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
		tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
		tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
		tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
		tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema.getPostponeDay());
		tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema.getPostponeDate());
		tLCUWSubSchema.setUpReportContent(tLCUWMasterSchema
				.getUpReportContent());
		tLCUWSubSchema.setHealthFlag(tLCUWMasterSchema.getHealthFlag());
		tLCUWSubSchema.setSpecFlag(tLCUWMasterSchema.getSpecFlag());
		tLCUWSubSchema.setSpecReason(tLCUWMasterSchema.getSpecReason());
		tLCUWSubSchema.setQuesFlag(tLCUWMasterSchema.getQuesFlag());
		tLCUWSubSchema.setReportFlag(tLCUWMasterSchema.getReportFlag());
		tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema.getChangePolFlag());
		tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
				.getChangePolReason());
		tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema.getAddPremReason());
		tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
		tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
		tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
		tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
		tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
		tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		//mLCUWSubSet.clear();      //Modify 2011-11-10 cuilong
		mLCUWSubSet.add(tLCUWSubSchema);

		// 核保错误信息表
		LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
		LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
		tLCUWErrorDB.setPolNo(mOldPolNo);
		LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
		tLCUWErrorSet = tLCUWErrorDB.query();
		if (tLCUWErrorDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWErrorDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = mOldPolNo + "个人错误信息表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tLCUWErrorSchema.setSerialNo("0");
		if (m > 0) {
			tLCUWErrorSchema.setUWNo(m);
		} else {
			tLCUWErrorSchema.setUWNo(1);
		}
		
		if(m > 0){
			batchNo=m;
		}else{
			batchNo=1;
		}
		
		tLCUWErrorSchema.setContNo(mContNo);
		tLCUWErrorSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
		tLCUWErrorSchema.setProposalContNo(mPContNo);
		tLCUWErrorSchema.setPolNo(mOldPolNo);
		tLCUWErrorSchema.setProposalNo(tLCPolSchema.getProposalNo());
		tLCUWErrorSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLCUWErrorSchema.setInsuredName(tLCPolSchema.getInsuredName());
		tLCUWErrorSchema.setAppntNo(tLCPolSchema.getAppntNo());
		tLCUWErrorSchema.setAppntName(tLCPolSchema.getAppntName());
		tLCUWErrorSchema.setManageCom(tLCPolSchema.getManageCom());
		tLCUWErrorSchema.setUWRuleCode(""); // 核保规则编码
		tLCUWErrorSchema.setUWError(""); // 核保出错信息
		tLCUWErrorSchema.setCurrValue(""); // 当前值
		tLCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tLCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		tLCUWErrorSchema.setUWPassFlag(mPolPassFlag);

		int jj = 0;
		// 取核保错误信息
		//mLCUWErrorSet.clear();    //Modify 2011-11-10 cuilong  
		int merrcount = tLMUWSetUnpass.size();
		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetUnpass.get(i);
				// 生成流水号
				String tserialno = "" + i;

				tLCUWErrorSchema.setSerialNo(tserialno);
				tLCUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); // 核保规则编码
				tLCUWErrorSchema.setUWError(tLMUWSchema.getRemark().trim()); // 核保出错信息，即核保规则的文字描述内容
				tLCUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
				tLCUWErrorSchema.setCurrValue(""); // 当前值
				tLCUWErrorSchema.setSugPassFlag(tLMUWSchema.getPassFlag()); // picch需求对自核规则分类（体检、契调）

				LCUWErrorSchema ttLCUWErrorSchema = new LCUWErrorSchema();
				ttLCUWErrorSchema.setSchema(tLCUWErrorSchema);
				mLCUWErrorSet.add(ttLCUWErrorSchema);
				jj = i;
			}
		}

		//mLCUWMasterSet.clear();     //Modify 2011-11-10 cuilong
		tLCUWMasterSchema.setBatchNo(batchNo);
		mLCUWMasterSet.add(tLCUWMasterSchema);
		// add by liwb 2009-12-18 for cpic-ing 2009-FEC-SIG0005
//		StringBuffer tStringBuffer = new StringBuffer();
//		tStringBuffer.append("select distinct workno,");
//		tStringBuffer.append("                '业务来源' || (case systemcode");
//		tStringBuffer.append("                  when 'LIS' then");
//		tStringBuffer.append("                   '个险'");
//		tStringBuffer.append("                  when 'GRP' then");
//		tStringBuffer.append("                   '团险'");
//		tStringBuffer.append("                  else");
//		tStringBuffer.append("                   ''");
//		tStringBuffer.append("                end) || '，' || '作业来源' || (case source");
//		tStringBuffer.append("                  when 'NB' then");
//		tStringBuffer.append("                   '核保作业'");
//		tStringBuffer.append("                  when 'POS' then");
//		tStringBuffer.append("                   '保全作业'");
//		tStringBuffer.append("                  when 'CL' then");
//		tStringBuffer.append("                   '理赔作业'");
//		tStringBuffer.append("                  when 'FF' then");
//		tStringBuffer.append("                   '付费作业'");
//		tStringBuffer.append("                  else");
//		tStringBuffer.append("                   ''");
//		tStringBuffer.append("                end) || '，' || '作业控制编号' || workno");
//		tStringBuffer.append("  from lddubiousinfo");
//		tStringBuffer.append(" where (appntno in ('");
//		tStringBuffer.append(mLCContSchema.getAppntNo());
//		tStringBuffer.append("', '");
//		tStringBuffer.append(mLCContSchema.getInsuredNo());
//		tStringBuffer.append("')");
//		tStringBuffer.append("    or insuredno in ('");
//		tStringBuffer.append(mLCContSchema.getAppntNo());
//		tStringBuffer.append("', '");
//		tStringBuffer.append(mLCContSchema.getInsuredNo());
//		tStringBuffer.append("'))");
//		tStringBuffer.append("   and status = '20'");
//		tStringBuffer.append("   and workno != '");
//		tStringBuffer.append(mLCContSchema.getContNo());
//		tStringBuffer.append("'");
//		logger.debug(tStringBuffer.toString());
//		ExeSQL tExeSQL = new ExeSQL();
//		SSRS tSSRS = new SSRS();
//		tSSRS = tExeSQL.execSQL(tStringBuffer.toString());
//		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
//			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
//				// 生成流水号
//				String tserialno = "" + (jj + i);
//
//				tLCUWErrorSchema.setSerialNo(tserialno);
//				tLCUWErrorSchema.setUWRuleCode("FEC200"); // 核保规则编码
//				tLCUWErrorSchema.setUWError("客户涉嫌可疑交易，请综合判断是否中止操作。" + tSSRS.GetText(i, 2)); // 核保出错信息，即核保规则的文字描述内容
//
//				LCUWErrorSchema ttLCUWErrorSchema = new LCUWErrorSchema();
//				ttLCUWErrorSchema.setSchema(tLCUWErrorSchema);
//				mLCUWErrorSet.add(ttLCUWErrorSchema);
//			}
//		}
		// end 2009-12-18
		return true;
	}

	/**
	 * 个人单核保数据准备 输出：如果发生错误则返回false,否则返回true
	 */
	private void CheckContInit(LCContSchema tLCContSchema) {
		mCalBase = new CalBase();
		mCalBase.setPrem(tLCContSchema.getPrem());
		mCalBase.setGet(tLCContSchema.getAmnt());
		mCalBase.setMult(tLCContSchema.getMult());
		// mCalBase.setAppAge( tLCContSchema.getInsuredAppAge() );
		mCalBase.setSex(tLCContSchema.getInsuredSex());
		mCalBase.setCValiDate(tLCContSchema.getCValiDate());
		mCalBase.setAppntAge(mAppntAge);
		// mCalBase.setJob( tLCContSchema.getOccupationType() );
		// mCalBase.setCount( tLCContSchema.getInsuredPeoples() );
		mCalBase.setContNo(mContNo);
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            LCContSchema
	 * @param tLMUWSetContUnpass
	 *            LMUWSetContUnpass
	 * @return boolean
	 */
	private boolean dealOneCont(LCContSchema tLCContSchema,
			LMUWSet tLMUWSetContUnpass) {
		prepareContUW(tLCContSchema, tLMUWSetContUnpass);

		LCContSchema tLCContSchemaDup = new LCContSchema();
		tLCContSchemaDup.setSchema(tLCContSchema);
		mAllLCContSet.add(tLCContSchemaDup);

		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet.set(mLCCUWMasterSet);
		mAllLCCUWMasterSet.add(tLCCUWMasterSet);

		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet.set(mLCCUWSubSet);
		mAllLCCUWSubSet.add(tLCCUWSubSet);

		LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
		tLCCUWErrorSet.set(mLCCUWErrorSet);
		mAllLCCUWErrorSet.add(tLCCUWErrorSet);

		return true;
	}

	/**
	 * 准备合同核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareContUW(LCContSchema tLCContSchema,
			LMUWSet tLMUWSetContUnpass) {

		tLCContSchema.setUWFlag(mContPassFlag);
		tLCContSchema.setUWOperator(mOperator);
		tLCContSchema.setUWDate(PubFun.getCurrentDate());
		tLCContSchema.setUWTime(PubFun.getCurrentTime());
		tLCContSchema.setModifyDate(PubFun.getCurrentDate());
		tLCContSchema.setModifyTime(PubFun.getCurrentTime());
		
		//用sql语句更新，不用Schema 20080818 因为可能与客户合并同时执行，保存了错误的客户数据	
		String updatecontsql = "update lccont set UWFlag='" + "?UWFlag?"
							 + "',UWOperator='" + "?UWCode?"
							 + "',UWDate='" + "?getCurrentDate?"
							 + "',UWTime='" + "?getCurrentTime?"
							 + "',ModifyDate='" + "?getCurrentDate?"
							 + "',ModifyTime='" + "?getCurrentTime?"
							 + "' where contno='" + "?contno?" +"'";
		SQLwithBindVariables sqlbv56 = new SQLwithBindVariables();
		sqlbv56.sql(updatecontsql);
		sqlbv56.put("UWFlag", mPolPassFlag);
		sqlbv56.put("UWCode", mOperator);
		sqlbv56.put("getCurrentDate", PubFun.getCurrentDate());
		sqlbv56.put("getCurrentTime", PubFun.getCurrentTime());
		sqlbv56.put("contno", tLCContSchema.getContNo());
		updatemap.put(sqlbv56, "UPDATE");

		// 合同核保主表
		boolean firstUW = true;
		int batchNo=0;
		LCCUWMasterSchema tLCCUWMasterSchema = new LCCUWMasterSchema();
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mContNo);
		LCCUWMasterSet tLCCUWMasterSet = new LCCUWMasterSet();
		tLCCUWMasterSet = tLCCUWMasterDB.query();
		if (tLCCUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保总表取数失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (tLCCUWMasterSet.size() == 0) {
			tLCCUWMasterSchema.setContNo(mContNo);
			tLCCUWMasterSchema.setGrpContNo(tLCContSchema.getGrpContNo());
			tLCCUWMasterSchema.setProposalContNo(tLCContSchema
					.getProposalContNo());
			tLCCUWMasterSchema.setUWNo(1);
			tLCCUWMasterSchema.setInsuredNo(tLCContSchema.getInsuredNo());
			tLCCUWMasterSchema.setInsuredName(tLCContSchema.getInsuredName());
			tLCCUWMasterSchema.setAppntNo(tLCContSchema.getAppntNo());
			tLCCUWMasterSchema.setAppntName(tLCContSchema.getAppntName());
			tLCCUWMasterSchema.setAgentCode(tLCContSchema.getAgentCode());
			tLCCUWMasterSchema.setAgentGroup(tLCContSchema.getAgentGroup());
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setPostponeDay("");
			tLCCUWMasterSchema.setPostponeDate("");
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setHealthFlag("0");
			tLCCUWMasterSchema.setSpecFlag("0");
			tLCCUWMasterSchema.setQuesFlag("0");
			tLCCUWMasterSchema.setReportFlag("0");
			tLCCUWMasterSchema.setChangePolFlag("0");
			tLCCUWMasterSchema.setPrintFlag("0");
			tLCCUWMasterSchema.setPrintFlag2("0");
			tLCCUWMasterSchema.setManageCom(tLCContSchema.getManageCom());
			tLCCUWMasterSchema.setUWIdea("");
			tLCCUWMasterSchema.setUpReportContent("");
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			firstUW = false;
			tLCCUWMasterSchema = tLCCUWMasterSet.get(1);
			tLCCUWMasterSchema.setUWNo(tLCCUWMasterSchema.getUWNo() + 1);
			tLCCUWMasterSchema.setState(mContPassFlag);
			tLCCUWMasterSchema.setPassFlag(mContPassFlag);
			tLCCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
			tLCCUWMasterSchema.setUWGrade(mUWGrade); // 核保级别
			tLCCUWMasterSchema.setAppGrade(mUWGrade); // 申报级别
			tLCCUWMasterSchema.setOperator(mOperator); // 操作员
			tLCCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		}
//		mLCCUWMasterSet.clear();
//		mLCCUWMasterSet.add(tLCCUWMasterSchema);

		// 合同核保轨迹表
		LCCUWSubSchema tLCCUWSubSchema = new LCCUWSubSchema();
		LCCUWSubDB tLCCUWSubDB = new LCCUWSubDB();
		tLCCUWSubDB.setContNo(mContNo);
		LCCUWSubSet tLCCUWSubSet = new LCCUWSubSet();
		tLCCUWSubSet = tLCCUWSubDB.query();
		if (tLCCUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWSubDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同核保轨迹表查失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int nUWNo = tLCCUWSubSet.size();
		if (nUWNo > 0) {
			tLCCUWSubSchema.setUWNo(++nUWNo); // 第几次核保
		} else {
			tLCCUWSubSchema.setUWNo(1); // 第1次核保
		}

		tLCCUWSubSchema.setContNo(tLCCUWMasterSchema.getContNo());
		tLCCUWSubSchema.setGrpContNo(tLCCUWMasterSchema.getGrpContNo());
		tLCCUWSubSchema.setProposalContNo(tLCCUWMasterSchema
				.getProposalContNo());
		tLCCUWSubSchema.setInsuredNo(tLCCUWMasterSchema.getInsuredNo());
		tLCCUWSubSchema.setInsuredName(tLCCUWMasterSchema.getInsuredName());
		tLCCUWSubSchema.setAppntNo(tLCCUWMasterSchema.getAppntNo());
		tLCCUWSubSchema.setAppntName(tLCCUWMasterSchema.getAppntName());
		tLCCUWSubSchema.setAgentCode(tLCCUWMasterSchema.getAgentCode());
		tLCCUWSubSchema.setAgentGroup(tLCCUWMasterSchema.getAgentGroup());
		tLCCUWSubSchema.setUWGrade(tLCCUWMasterSchema.getUWGrade()); // 核保级别
		tLCCUWSubSchema.setAppGrade(tLCCUWMasterSchema.getAppGrade()); // 申请级别
		tLCCUWSubSchema.setAutoUWFlag(tLCCUWMasterSchema.getAutoUWFlag());
		tLCCUWSubSchema.setState(tLCCUWMasterSchema.getState());
		tLCCUWSubSchema.setPassFlag(tLCCUWMasterSchema.getState());
		tLCCUWSubSchema.setPostponeDay(tLCCUWMasterSchema.getPostponeDay());
		tLCCUWSubSchema.setPostponeDate(tLCCUWMasterSchema.getPostponeDate());
		tLCCUWSubSchema.setUpReportContent(tLCCUWMasterSchema
				.getUpReportContent());
		tLCCUWSubSchema.setHealthFlag(tLCCUWMasterSchema.getHealthFlag());
		tLCCUWSubSchema.setSpecFlag(tLCCUWMasterSchema.getSpecFlag());
		tLCCUWSubSchema.setSpecReason(tLCCUWMasterSchema.getSpecReason());
		tLCCUWSubSchema.setQuesFlag(tLCCUWMasterSchema.getQuesFlag());
		tLCCUWSubSchema.setReportFlag(tLCCUWMasterSchema.getReportFlag());
		tLCCUWSubSchema.setChangePolFlag(tLCCUWMasterSchema.getChangePolFlag());
		tLCCUWSubSchema.setChangePolReason(tLCCUWMasterSchema
				.getChangePolReason());
		tLCCUWSubSchema.setAddPremReason(tLCCUWMasterSchema.getAddPremReason());
		tLCCUWSubSchema.setPrintFlag(tLCCUWMasterSchema.getPrintFlag());
		tLCCUWSubSchema.setPrintFlag2(tLCCUWMasterSchema.getPrintFlag2());
		tLCCUWSubSchema.setUWIdea(tLCCUWMasterSchema.getUWIdea());
		tLCCUWSubSchema.setOperator(tLCCUWMasterSchema.getOperator()); // 操作员
		tLCCUWSubSchema.setManageCom(tLCCUWMasterSchema.getManageCom());
		tLCCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
		tLCCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWSubSchema.setModifyTime(PubFun.getCurrentTime());

		//mLCCUWSubSet.clear();     //Modify 2011-11-10 cuilong
		mLCCUWSubSet.add(tLCCUWSubSchema);

		// 核保错误信息表
		LCCUWErrorSchema tLCCUWErrorSchema = new LCCUWErrorSchema();
		LCCUWErrorDB tLCCUWErrorDB = new LCCUWErrorDB();
		tLCCUWErrorDB.setContNo(mContNo);
		LCCUWErrorSet tLCCUWErrorSet = new LCCUWErrorSet();
		tLCCUWErrorSet = tLCCUWErrorDB.query();
		if (tLCCUWErrorDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCCUWErrorDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWAtuoChkAfterInitService";
			tError.functionName = "prepareContUW";
			tError.errorMessage = mContNo + "合同错误信息表查询失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		tLCCUWErrorSchema.setSerialNo("0");
		if (nUWNo > 0) {
			tLCCUWErrorSchema.setUWNo(nUWNo);
		} else {
			tLCCUWErrorSchema.setUWNo(1);
		}
		batchNo=nUWNo;
		tLCCUWErrorSchema.setContNo(mContNo);
		tLCCUWErrorSchema.setGrpContNo(tLCCUWSubSchema.getGrpContNo());
		tLCCUWErrorSchema
				.setProposalContNo(tLCCUWSubSchema.getProposalContNo());
		tLCCUWErrorSchema.setInsuredNo(tLCCUWSubSchema.getInsuredNo());
		tLCCUWErrorSchema.setInsuredName(tLCCUWSubSchema.getInsuredName());
		tLCCUWErrorSchema.setAppntNo(tLCCUWSubSchema.getAppntNo());
		tLCCUWErrorSchema.setAppntName(tLCCUWSubSchema.getAppntName());
		tLCCUWErrorSchema.setManageCom(tLCCUWSubSchema.getManageCom());
		tLCCUWErrorSchema.setUWRuleCode(""); // 核保规则编码
		tLCCUWErrorSchema.setUWError(""); // 核保出错信息
		tLCCUWErrorSchema.setCurrValue(""); // 当前值
		tLCCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tLCCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		tLCCUWErrorSchema.setUWPassFlag(mPolPassFlag);

		// 取核保错误信息
		//mLCCUWErrorSet.clear();     //Modify 2011-11-10 cuilong
		int merrcount = tLMUWSetContUnpass.size();
		if (merrcount > 0) {
			for (int i = 1; i <= merrcount; i++) {
				// 取出错信息
				LMUWSchema tLMUWSchema = new LMUWSchema();
				tLMUWSchema = tLMUWSetContUnpass.get(i);
				// 生成流水号
				String tserialno = "" + i;

				tLCCUWErrorSchema.setSerialNo(tserialno);
				tLCCUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); // 核保规则编码
				tLCCUWErrorSchema.setUWError(tLMUWSchema.getRemark().trim()); // 核保出错信息，即核保规则的文字描述内容
				tLCCUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
				tLCCUWErrorSchema.setCurrValue(""); // 当前值

				LCCUWErrorSchema ttLCCUWErrorSchema = new LCCUWErrorSchema();
				ttLCCUWErrorSchema.setSchema(tLCCUWErrorSchema);
				mLCCUWErrorSet.add(ttLCCUWErrorSchema);
			}
		}
		//mLCCUWMasterSet.clear();     //Modify 2011-11-10 cuilong
		tLCCUWMasterSchema.setBatchNo(batchNo);
		mLCCUWMasterSet.add(tLCCUWMasterSchema);

		return true;
	}
	

	
	/**
	 * 准备收费数据，核保通过后，根据加费情况产生应收
	 * 
	 * @return 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareFee() {
		//首期缴费方式不为转账，无需生成应收数据
		if (!"7".equals(mLCContSchema.getNewPayMode())) {
			return true;
		}
		
		StringBuffer strSql = new StringBuffer();
		ExeSQL exesql = new ExeSQL();
		SSRS tempSSRS = null;
		double totalPrem = 0;
		double havedPayPrem = 0;
		
		//应收保费
		strSql.setLength(0);
		// modify by liwb 20101014 2010-PRJ-0007 电销产品首期请款两期保费
		if (mTransferData.getValueByName("YBTFlag") != null
				&& "DX".equals(mTransferData.getValueByName("YBTFlag"))
				&& mLCContSchema.getPayIntv() == 1) {
			strSql.append("select sum(prem)*2 from lcpol where contno='");
		} else {
			strSql.append("select sum(prem) from lcpol where contno='");
		}
		strSql.append("?contno?");
		strSql.append("'");
		SQLwithBindVariables sqlbv57 = new SQLwithBindVariables();
		sqlbv57.put("contno", mContNo);
		sqlbv57.sql(strSql.toString());
		tempSSRS = exesql.execSQL(sqlbv57);
		if(tempSSRS.getMaxRow()==0){
			CError tError = new CError();
			tError.moduleName = "UWConfirmBL";
			tError.functionName = "prepareFee";
			tError.errorMessage = "保费信息查询失败!";
			this.mErrors.addOneError(tError);
		}
		
		totalPrem = Double.parseDouble(tempSSRS.GetText(1, 1));//总付款金额
		
		//已缴保费
		strSql.setLength(0);
		strSql.append("select (case when sum(paymoney) is null then 0 else sum(paymoney) end) from ljtempfee where otherno='");
		strSql.append("?otherno?");
		strSql.append("' and confdate is null and (EnterAccDate is not null and EnterAccDate <> '3000-01-01')");
		SQLwithBindVariables sqlbv58 = new SQLwithBindVariables();
		sqlbv58.sql(strSql.toString());
		sqlbv58.put("otherno", mContNo);
		tempSSRS = exesql.execSQL(sqlbv58);
		if(tempSSRS.getMaxRow()>0){
			havedPayPrem = Double.parseDouble(tempSSRS.GetText(1, 1));//已缴保费
		}
		
		double amount = totalPrem - havedPayPrem;
		//由于精度问题添加该段代码
		String differ = mDecimalFormat.format(amount);
		amount = Double.parseDouble(differ);
		
		//已缴保费已足够承保，不用产生应收
		if (amount <= 0) {
			 // 日志监控,过程监控
			if(mGlobalInput.LogID!=null&&!mGlobalInput.LogID.equals(""))
			{
				PubFun.logTrack (mGlobalInput,mContNo,"投保单"+mContNo+"已缴保费足够承保，不用产生应收");	
			}
			return true;
		}

		String getNoticeNo = "";//暂交费收据号码
		String serialNo = "";//流水号
		String otherNoType = "";//其它号码类型
		String bankOnTheWayFlag = "";//银行在途标志
		String enterAcctDate = "";//到账日期
		String currentDate = PubFun.getCurrentDate();//确认日期
		String currentTime = PubFun.getCurrentTime();//确认时间

		//判断数据库中是否已经存在应收记录
		strSql.setLength(0);
		strSql.append("select a.getnoticeno,a.bankonthewayflag,b.enteraccdate from ljspay a left join ljtempfee b on a.getnoticeno=b.tempfeeno where b.otherno='");
		strSql.append("?otherno?");
		strSql.append("' and b.othernotype in ('6','7','0')");
		SQLwithBindVariables sqlbv59 = new SQLwithBindVariables();
		sqlbv59.sql(strSql.toString());
		sqlbv59.put("otherno", mContNo);
		tempSSRS = exesql.execSQL(sqlbv59);
		//已有数据，更新；无数据，增加
		if(tempSSRS.getMaxRow()>0){
			getNoticeNo = tempSSRS.GetText(1, 1);
			bankOnTheWayFlag = tempSSRS.GetText(1, 2);
			enterAcctDate = tempSSRS.GetText(1, 3);
			//银行在途，或者该笔收费已到帐，不能修改应收等数据
			if ((bankOnTheWayFlag != null && "1".equals(bankOnTheWayFlag))
					|| (enterAcctDate != null && !enterAcctDate.equals(""))) {
				 // 日志监控,过程监控
				if(mGlobalInput.LogID!=null&&!mGlobalInput.LogID.equals(""))
				{
					PubFun.logTrack (mGlobalInput,mContNo,"投保单"+mContNo+"已有应收，银行在途，或者该笔收费已到帐，不能修改应收等数据");
				}
				return true;
			}
			//已有应收，只需更新金额等数据
			String feeSQL = "update ljTempFee set payMoney = "+"?amount?"+",operator='"
			       + "?operator?" +"',ModifyDate = '"+"?currentDate?"+"',modifyTime='"+"?currentTime?"+"'" 
			       + " where tempfeeno = '"+"?getnoticeno?"+"' and tempfeetype='1'";
			String feeClassSQL = "update ljtempfeeclass set payMoney = "+"?amount?"+",operator='"
		       + "?operator?" +"',ModifyDate = '"+"?currentDate?"+"',modifyTime='"+"?currentTime?"+"'" 
		       + " where tempfeeno = '"+"?getnoticeno?"+"' and paymode='7'";
			String ljsPaySQL = "update ljspay set sumduepaymoney = "+"?amount?"+",operator='"
		       + "?operator?" +"',ModifyDate = '"+"?currentDate?"+"',modifyTime='"+"?currentTime?"+"'" 
		       + " where getnoticeno = '"+"?getnoticeno?"+"'";
			
			SQLwithBindVariables sqlbv60 = new SQLwithBindVariables();
			sqlbv60.sql(feeSQL);
			sqlbv60.put("amount", amount);
			sqlbv60.put("operator", mOperator);
			sqlbv60.put("currentDate", currentDate);
			sqlbv60.put("currentTime", currentTime);
			sqlbv60.put("getnoticeno", getNoticeNo);
			feeMap.put(sqlbv60, "UPDATE");
			SQLwithBindVariables sqlbv61 = new SQLwithBindVariables();
			sqlbv61.sql(ljsPaySQL);
			sqlbv61.put("amount", amount);
			sqlbv61.put("operator", mOperator);
			sqlbv61.put("currentDate", currentDate);
			sqlbv61.put("currentTime", currentTime);
			sqlbv61.put("getnoticeno", getNoticeNo);
			feeMap.put(sqlbv61, "UPDATE");
			SQLwithBindVariables sqlbv62 = new SQLwithBindVariables();
			sqlbv62.sql(ljsPaySQL);
			sqlbv62.put("amount", amount);
			sqlbv62.put("operator", mOperator);
			sqlbv62.put("currentDate", currentDate);
			sqlbv62.put("currentTime", currentTime);
			sqlbv62.put("getnoticeno", getNoticeNo);
			feeMap.put(sqlbv62, "UPDATE");
			 // 日志监控,过程监控
			if(mGlobalInput.LogID!=null&&!mGlobalInput.LogID.equals(""))
			{
				PubFun.logTrack (mGlobalInput,mContNo,"投保单"+mContNo+"已有应收，修改应收金额成功");
			}
			
		} else {
			String tLimit = PubFun.getNoLimit(mLCContSchema.getManageCom());
			getNoticeNo = PubFun1.CreateMaxNo("PAYNOTICENO", tLimit);
			serialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
			
			if(mLCContSchema.getSaleChnl().equals("1")){
				otherNoType = "6";
			} else if(mLCContSchema.getSaleChnl().equals("3")){
				otherNoType = "7";
			} else {
				otherNoType = "0";
			}
			
			LJTempFeeSchema feeSchema = new LJTempFeeSchema();
			feeSchema.setTempFeeNo(getNoticeNo);
			feeSchema.setTempFeeType("1");
			feeSchema.setPayIntv("0");//交费间隔
			feeSchema.setRiskCode("000000");
			feeSchema.setSaleChnl(mLCContSchema.getSaleChnl());//销售渠道
			feeSchema.setAgentCom("");
			feeSchema.setOtherNo(mLCContSchema.getContNo());
			feeSchema.setOtherNoType(otherNoType);	
			feeSchema.setPayMoney(amount);
			feeSchema.setPayDate(currentDate);
			feeSchema.setManageCom(mLCContSchema.getManageCom());
			feeSchema.setPolicyCom(mLCContSchema.getManageCom());
			feeSchema.setAgentGroup(mLCContSchema.getAgentGroup());
			feeSchema.setAgentCode(mLCContSchema.getAgentCode());
			feeSchema.setSerialNo(serialNo);
			feeSchema.setConfFlag("0");
			feeSchema.setOperator(mOperator);
			feeSchema.setMakeDate(currentDate);
			feeSchema.setMakeTime(currentTime);
			feeSchema.setModifyDate(currentDate);
			feeSchema.setModifyTime(currentTime);
			feeSchema.setAgentType("");//代理机构内部分类
			feeSchema.setAPPntName("");//投保人名称
			feeSchema.setState("");//状态
			feeSchema.setContCom("");//保单所属机构
			feeSchema.setPayEndYear("");//交费年期
			feeSchema.setStandPrem("");//预收标保
			feeSchema.setRemark("");//备注
			feeSchema.setDistict("");//代理人所在区
			feeSchema.setDepartment("");//代理人所在部
			feeSchema.setBranchCode("");//代理人所在组
			feeMap.put(feeSchema,"INSERT");	
			
			LJTempFeeClassSchema feeClassSchema = new LJTempFeeClassSchema();
			feeClassSchema.setTempFeeNo(getNoticeNo);
			feeClassSchema.setPayMode("7");
			feeClassSchema.setChequeNo("000000");
			feeClassSchema.setBankCode(mLCContSchema.getNewBankCode());
			feeClassSchema.setBankAccNo(mLCContSchema.getBankAccNo());
			feeClassSchema.setAccName(mLCContSchema.getAccName());
			feeClassSchema.setPayMoney(amount);
			feeClassSchema.setPayDate(currentDate);
			feeClassSchema.setSerialNo(serialNo);
			feeClassSchema.setManageCom(mLCContSchema.getManageCom());
			feeClassSchema.setPolicyCom(mLCContSchema.getManageCom());
			feeClassSchema.setConfFlag("0");
			feeClassSchema.setOperator(mOperator);
			feeClassSchema.setMakeDate(currentDate);
			feeClassSchema.setMakeTime(currentTime);
			feeClassSchema.setModifyDate(currentDate);
			feeClassSchema.setModifyTime(currentTime);
			feeClassSchema.setOtherNo(mLCContSchema.getContNo());
			feeClassSchema.setOtherNoType(otherNoType);
			feeMap.put(feeClassSchema, "INSERT");
			
			LJSPaySchema paySchema = new LJSPaySchema();
			paySchema.setGetNoticeNo(getNoticeNo);
			paySchema.setOtherNo(mLCContSchema.getContNo());
			paySchema.setOtherNoType(otherNoType);
			paySchema.setAppntNo(mLCContSchema.getContNo());
			paySchema.setSumDuePayMoney(amount);
			paySchema.setPayDate(currentDate);
			paySchema.setBankOnTheWayFlag("0");
			paySchema.setBankSuccFlag("0");
			paySchema.setSendBankCount("0");
			paySchema.setSerialNo(serialNo);
			paySchema.setOperator(mOperator);
			paySchema.setMakeDate(currentDate);
			paySchema.setMakeTime(currentTime);
			paySchema.setModifyDate(currentDate);
			paySchema.setModifyTime(currentTime);
			paySchema.setManageCom(mLCContSchema.getManageCom());
			paySchema.setAgentCode(mLCContSchema.getAgentCode());
			paySchema.setAgentCom(mLCContSchema.getManageCom());
			paySchema.setAgentGroup(mLCContSchema.getAgentGroup());
			paySchema.setAgentType("");
			paySchema.setRiskCode("000000");
			paySchema.setBankCode(mLCContSchema.getNewBankCode());
			paySchema.setBankAccNo(mLCContSchema.getBankAccNo());
			paySchema.setAccName(mLCContSchema.getAccName());
			paySchema.setStartPayDate(currentDate);
			feeMap.put(paySchema, "INSERT");
//			 日志监控,过程监控
			if(mGlobalInput.LogID!=null&&!mGlobalInput.LogID.equals(""))
			{
				PubFun.logTrack (mGlobalInput,mContNo,"投保单"+mContNo+"无既往应收，生成应收数据成功");
			}
		}
		
		return true;
	}
	
//	public static void main(String[] args) {
//		
//		String tResult = "被保险人";
//		logger.debug(tResult.replaceAll("被保人|被保险人", "投保人"));
//		
//		GlobalInput tG = new GlobalInput();
//		tG.Operator = "001";
//		tG.ManageCom = "86";
//		tG.ComCode = "86";
//		
//		TransferData tTransferData = new TransferData();
//		tTransferData.setNameAndValue("MissionID", "00000000000000354833");
//		tTransferData.setNameAndValue("SubMissionID", "1");
//		tTransferData.setNameAndValue("ActivityID", "0000001003");
//		tTransferData.setNameAndValue("ContNo", "32110000000035");
//		
//		// 准备传输数据 VData
//		VData tVData = new VData();
//		tVData.add(tTransferData);
//		tVData.add(tG);
//		
//		AutoUWCheckBL tAutoUWCheckBL = new AutoUWCheckBL();
//		
//		String Content = "";
//		if (tAutoUWCheckBL.submitData(tVData, "") == false) {
//			int n = tAutoUWCheckBL.mErrors.getErrorCount();
//			logger.debug("n==" + n);
//			for (int j = 0; j < n; j++) {
//				logger.debug("Error: "
//						+ tAutoUWCheckBL.mErrors.getError(j).errorMessage);
//			}
//			Content = " 自动核保失败，原因是: "
//					+ tAutoUWCheckBL.mErrors.getError(0).errorMessage;
//		}
//	}


}
