package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDiscountDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LMRiskRoleDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDiscountSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCDiscountSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LMRiskRoleSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDiscountSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
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
 * Description: 个单被保人重要资料变更项目明细
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorICDetailBL implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorICDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();

	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/**  */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();

	private LPPremSet mLPPremSet = new LPPremSet();

	private LPGetSet mLPGetSet = new LPGetSet();

	private LPDutySet mLPDutySet = new LPDutySet();

	private LPPolSet mLPPolSet = new LPPolSet();

	private LPContSet mLPContSet = new LPContSet();

	private LPDiscountSet mLPDiscountSet = new LPDiscountSet();

	private double mGetMoney = 0.0;

	private double mGetInterest = 0.0;

	private double mChgPrem = 0.0;

	private double mChgAmnt = 0.0;

	private double mSourcePrem = 0.0;

	private double mSourceAmnt = 0.0;

	private LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	
	private LJSGetEndorseSet aZKLJSGetEndorseSet = new LJSGetEndorseSet();
	

	// private BqCode BqCode = new BqCode();
	private BqCalBL mBqCalBL = new BqCalBL();

	private Reflections tRef = new Reflections();

	/**存放此保单在做CM项目时的批单号*/
	private String tCMEdorNo = "";

	private String tCustmerNo = "";

	//重算加费时传给加费处理程序的被保人性别和职业类别
	private String InsuredSex="";
	private String InsuredOccupationType="";

	// 获得此时的日期和时间
	private String strCurrentDate = PubFun.getCurrentDate();
	private String strCurrentTime = PubFun.getCurrentTime();
	
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	
	private String mAppntAge = "";
	
	/**如果保单在两年之外给出提示的标记*/	
	private String tISErrFlag="0";
	
	//投联险标识，投联险客户资料变更与普通险处理不同(注意这里SQL的条件)
	private boolean tTLFlag = false;
	//投连为主险产生的所有补退费合计,用来判断预估帐户价值是否够扣
	private double mTLMainMoney = 0.0;

	public PEdorICDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorICDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public boolean getSubmitData(VData cInputData, String cOperate) // 不含数据提交的数据处理
	{
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData() {
		mLPInsuredSchema = (LPInsuredSchema) mInputData.getObjectByObjectName(
				"LPInsuredSchema", 0);
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mLPInsuredSchema == null || mLPEdorItemSchema == null
				|| mGlobalInput == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		tCMEdorNo = mLPEdorItemSchema.getStandbyFlag1();
		tCustmerNo = mLPEdorItemSchema.getStandbyFlag2();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		//tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));

		mLPEdorItemSchema.setInsuredNo(tCustmerNo);
		mLPEdorItemSchema.setStandbyFlag1(tCMEdorNo);
		mLPEdorItemSchema.setStandbyFlag2(tCustmerNo);
		if (tCMEdorNo == null || "".equals(tCMEdorNo)) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		//取出CM变更后的被保人性别和职业类别
		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		//tLPInsuredDB.setInsuredNo(tCustmerNo);
		tLPInsuredDB.setEdorType("CM");
		tLPInsuredDB.setEdorNo(tCMEdorNo);
		
		if(tLPInsuredDB.query().size()==0)
		{
			LCInsuredDB tLCInsuredDB = new LCInsuredDB();
			tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
			InsuredSex = tLCInsuredDB.query().get(1).getSex();
			InsuredOccupationType = tLCInsuredDB.query().get(1).getOccupationType();
		}
		else
		{
			mLPInsuredSchema.setSchema(tLPInsuredDB.query().get(1));
			InsuredSex= mLPInsuredSchema.getSex();
			InsuredOccupationType = mLPInsuredSchema.getOccupationType();
		}
		
		logger.debug("被保险人性别："+InsuredSex+";被保险人职业类别："+InsuredOccupationType);
		
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {

		
		String tlSQL="select case when (select count(distinct contno) from lcpol " +
		"where appflag='1' and insuredno = '?insuredno?')=1 " +
		"and (select count(1) from lcpol where appflag='1' and insuredno = '?insuredno?'and riskcode in (select riskcode from lmriskapp where risktype3 = '3'))>0 then '1' else '0' end from dual ";


		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tlSQL);
		sbv1.put("insuredno", mLPEdorItemSchema.getInsuredNo());
		ExeSQL tExeSQL0 = new ExeSQL();
		if("1".equals(tExeSQL0.getOneValue(sbv1))){
			tTLFlag = true;
		}
		
		//删除本次补退费
		String delLJSSql = " delete from LJSGetEndorse where EndorsementNo = '?EndorsementNo?' and FeeOperationType = '?FeeOperationType?' and contno = '?contno?' ";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(delLJSSql);
		sbv2.put("EndorsementNo", mLPEdorItemSchema.getEdorNo());
		sbv2.put("FeeOperationType", mLPEdorItemSchema.getEdorType());
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
     	mMap.put(sbv2, "DELETE");
		// 准备个人批改主表的信息

		// 查询客户作为主被保人的险种,以及有效的保单
		String sql = " select * from lcpol where contno = '?contno?' "
				//cvalidate可能排除了新增附加险的预约生效的polno 此处的逻辑需要讨论
				+ " and appflag in ('1') and cvalidate <= '"
				//enddate排除的趸交的附加险
				+ "?date?" + "' and enddate > '"
				//order by 确保豁免险最后处理
				+ "?date?" + "' order by (case when exists(select 1 from lmriskapp where riskcode=lcpol.riskcode and risktype7 in ('1','2')) then 1 else 0 end) ";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sql);
		sbv3.put("contno", mLPEdorItemSchema.getContNo());
		sbv3.put("date", mLPEdorItemSchema.getEdorValiDate());
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sbv3);
		if (tLCPolDB.mErrors.needDealError()) {
			CError.buildErr(this, "被保人险种查询失败!");
			return false;
		}
		if (tLCPolSet == null || tLCPolSet.size() <= 0) {
			CError.buildErr(this, "没有满足条件保单，此合同下保单均已经终止或者失效!");
			return false;
		}


		// 准备保单的客户相关信息
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LPContSchema tLPContSchema = new LPContSchema();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "查询保单号为" + tLCContDB.getContNo()
					+ "的保单信息时失败!");
			return false;
		}
		//取得CM生成的LPCont记录，复制并修改edortype,edorno
		LPContDB xLPContDB = new LPContDB();
		xLPContDB.setEdorNo(this.tCMEdorNo);
		xLPContDB.setEdorType("CM");
		xLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		if(!xLPContDB.getInfo())
		{
			CError.buildErr(this, "查询保单号"+mLPEdorItemSchema.getContNo()+"的CM保全LPCONT记录失败！");
			return false;
		}
		tLPContSchema = xLPContDB.getSchema();
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType("IC");
		
		LPEdorItemSchema tLPEdorItemSchema = null;
	
		for (int i = 1; i <= tLCPolSet.size(); i++) {
			tLPEdorItemSchema = mLPEdorItemSchema.getSchema();
			tLPEdorItemSchema.setPolNo(tLCPolSet.get(i).getPolNo());
			//以险种号取得CM生成的LPPol记录，复制并修改edortype,edorno
			LPPolDB xLPPolDB = new LPPolDB();
			xLPPolDB.setEdorNo(this.tCMEdorNo);
			xLPPolDB.setEdorType("CM");
			xLPPolDB.setPolNo(tLCPolSet.get(i).getPolNo());
			if(!xLPPolDB.getInfo())
			{
				CError.buildErr(this, "查询险种号"+tLCPolSet.get(i).getPolNo()+"的CM保全LPPOL记录失败！");
				return false;
			}
			tLPPolSchema = xLPPolDB.getSchema();
			tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPolSchema.setEdorType("IC");
			tLPPolSchema.setMakeDate(strCurrentDate);
			tLPPolSchema.setMakeTime(strCurrentTime);
			tLPPolSchema.setModifyDate(strCurrentDate);
			tLPPolSchema.setModifyTime(strCurrentTime);
			
			
			int tAppAge = 0;
			ExeSQL tExeSQL = new ExeSQL();
			//判断是否是续保险种
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql("select 'Y' from lmrisk where rnewflag='Y' and riskcode='?riskcode?' ");
			sbv4.put("riskcode", tLPPolSchema.getRiskCode());
			String RnewFlag = tExeSQL.getOneValue(sbv4);
			int tInsuredAge=0;
			int tCurrentAge=0;
			int tInsuredYear=0;
			mAppntAge = "";
			String BirthDay = "";
			String CValiDay = "";
			String tRole="";//角色标记，00，投保人，01被保人
            String tSQL="select riskcode from lmriskapp where "
                + " risktype7 in ('1') and riskcode='?riskcode?'";
            SQLwithBindVariables sbv5=new SQLwithBindVariables();
            sbv5.sql(tSQL);
            sbv5.put("riskcode", tLPPolSchema.getRiskCode());
   			     String tHMFlag=tExeSQL.getOneValue(sbv5);
   			//投保人豁免险，取投保人年龄
   			if(!"".equals(tHMFlag))
   			{
	              	//对投保人年龄进行校验
	  		     tInsuredAge = PubFun.calInterval(tLPContSchema.getAppntBirthday(),
	  		    		tLPPolSchema.getCValiDate(), "Y");
				tCurrentAge = PubFun.calInterval(tLPContSchema.getAppntBirthday(),
						mLPEdorItemSchema.getEdorAppDate(), "Y");
				tInsuredYear = PubFun.calInterval2(tLPContSchema.getCValiDate(),
						mLPEdorItemSchema.getEdorAppDate(), "Y");
				mAppntAge = String.valueOf(tInsuredAge);
//				tAppAge = PubFun.calInterval(tLCContSchema.getAppntBirthday(),
//						tLPPolSchema.getCValiDate(), "Y");
				BirthDay=tLPContSchema.getAppntBirthday();
				CValiDay=tLPPolSchema.getCValiDate();
				
				//对于续保件，应该取首次投保对应的生效日
				if(!"".equals(RnewFlag)&&"Y".equals(RnewFlag))
				{
					String csql = "select cvalidate from lcpol where contno='?contno?' and riskcode='?riskcode?' and renewcount='0' order by cvalidate desc";
					SQLwithBindVariables sbv6=new SQLwithBindVariables();
					sbv6.sql(csql);
					sbv6.put("contno", tLPPolSchema.getContNo());
					sbv6.put("riskcode", tLPPolSchema.getRiskCode());
					CValiDay = tExeSQL.getOneValue(sbv6);
					tInsuredAge = PubFun.calInterval(tLPContSchema.getAppntBirthday(),CValiDay, "Y");
					tInsuredYear = PubFun.calInterval2(CValiDay,mLPEdorItemSchema.getEdorAppDate(), "Y");
				}
						
				tRole="00";
   			}else
   			{
			// 校验被保人投保年龄
			tInsuredAge = PubFun.calInterval(tLPPolSchema.getInsuredBirthday(),
					tLPPolSchema.getCValiDate(), "Y");
			tCurrentAge = PubFun.calInterval(tLPPolSchema.getInsuredBirthday(),
					mLPEdorItemSchema.getEdorAppDate(), "Y");
			tInsuredYear = PubFun.calInterval2(tLPPolSchema.getCValiDate(),
					mLPEdorItemSchema.getEdorAppDate(), "Y");
//			tAppAge = PubFun.calInterval(tLPPolSchema.getInsuredBirthday(),
//					tLPPolSchema.getCValiDate(), "Y");
			BirthDay=tLPPolSchema.getInsuredBirthday();
			CValiDay=tLPPolSchema.getCValiDate();
			
			//对于续保件，应该取首次投保对应的生效日
			if(!"".equals(RnewFlag)&&"Y".equals(RnewFlag))
			{
				String csql = "select cvalidate from lcpol where contno='?contno?' and riskcode='?riskcode?' and renewcount='0' order by cvalidate desc";
				SQLwithBindVariables sbv7=new SQLwithBindVariables();
				sbv7.sql(csql);
				sbv7.put("contno", tLPPolSchema.getContNo());
				sbv7.put("riskcode", tLPPolSchema.getRiskCode());
				CValiDay = tExeSQL.getOneValue(sbv7);
				tInsuredAge = PubFun.calInterval(tLPPolSchema.getInsuredBirthday(),CValiDay, "Y");
				tInsuredYear = PubFun.calInterval2(CValiDay,mLPEdorItemSchema.getEdorAppDate(), "Y");
			}
			tRole="01";
   			}
			logger.debug("投保年龄111tInsuredAge: " + tInsuredAge);
			logger.debug("投保年龄222tCurrentAge: " + tCurrentAge);
			logger.debug("承保两年tInsuredYear: " + tInsuredYear);
			
			if(tAppAge==34)
			{
				logger.debug("承保两年tInsuredYear: " + tInsuredYear);			
			}

			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			tLMRiskAppDB.setRiskCode(tLPPolSchema.getRiskCode());
			if (!tLMRiskAppDB.getInfo()) {
				CError.buildErr(this, "查询险种承保定义表失败！");
				return false;
			}
			logger.debug("承保两年Min: " + tLMRiskAppDB.getMinInsuredAge());
			logger.debug("承保两年Max: " + tLMRiskAppDB.getMaxInsuredAge());
			if ((tInsuredAge < tLMRiskAppDB.getMinInsuredAge())
					|| ((tInsuredAge > tLMRiskAppDB.getMaxInsuredAge()) && (tLMRiskAppDB
							.getMaxInsuredAge() > 0))
					|| !checkLMRiskRole(tLPPolSchema, tRole, BirthDay, CValiDay)) // 如果变更后投保年龄在承保范围之外
			{
				if (tInsuredYear <= 2) // 如果承保两年内
				{					
					CError.buildErr(this, "保单" + tLPPolSchema.getContNo()
							+ "下险种" + tLPPolSchema.getRiskCode()
							+ " 投保未满两年，且变更后的投保年龄和当前年龄不在承保范围内，请进行公司解约处理。");
					return false;
				}else
				{
					tISErrFlag="1";
				}				
			}
			
			//续保年龄是否符合续保年龄要求
			if(!"".equals(RnewFlag)&&"Y".equals(RnewFlag))
			{
				int CurrInsuredAge = PubFun.calInterval(tLPPolSchema.getInsuredBirthday(),tLPPolSchema.getCValiDate(), "Y");
				
				String Sql = "select a.maxrnewage from LMRiskRole a where a.riskcode = '?riskcode?' and a.riskrole = '01' ";
				SQLwithBindVariables sbv8=new SQLwithBindVariables();
				sbv8.sql(Sql);
				sbv8.put("riskcode", tLPPolSchema.getRiskCode());
				SSRS nSSRS = tExeSQL.execSQL(sbv8);
				if(nSSRS.MaxRow>=1)//如果有条款限制，才需要判断
				{
					String MaxInsuredAge = nSSRS.GetText(1, 1);
					int MaxAge = Integer.parseInt(MaxInsuredAge);
					if (CurrInsuredAge > MaxAge)
					{					
						CError.buildErr(this, "保单" + tLPPolSchema.getContNo()
								+ "下险种" + tLPPolSchema.getRiskCode()
								+ " 变更后的被保人年龄"+ CurrInsuredAge + "岁，大于条款的被保人最大年龄" + MaxAge + "岁");
						return false;
					}
				}			
			}
			
			mSourcePrem = tLPPolSchema.getPrem();
			mSourceAmnt = tLPPolSchema.getAmnt();

//			if (mLPInsuredSchema.getInsuredNo().equals(
//					tLPPolSchema.getInsuredNo())
//					&& !tLPPolSchema.getAppFlag().equals("4")) // 第一被保人，并且保单未终止
//			{
//				tLPPolSchema.setInsuredSex(mLPInsuredSchema.getSex());
//				tLPPolSchema.setInsuredBirthday(mLPInsuredSchema.getBirthday());
//				tLPPolSchema.setInsuredAppAge(tAppAge);
//			}
			// add by jiaqiangli 2009-02-16 豁免联动处理
			if (judgeExempt(tLPPolSchema.getPolNo()) == true) {
				//保费变化
				tLPPolSchema.setAmnt(this.getExemptAMNT(mLPPolSet, tLPPolSchema
						.getRiskCode()));
				logger.debug("tLPPolSchema.amnt" + tLPPolSchema.getAmnt());

				if (tLPPolSchema.getAmnt() <= 0) {
					CError.buildErr(this, "计算豁免保额失败");
					return false;
				}
			}
			if (!tLPPolSchema.getAppFlag().equals("4")) {
				
				//added by shihui 主险只与风险保费有关，附加险重算
				if(tTLFlag && tLPPolSchema.getPolNo().equals(tLPPolSchema.getMainPolNo()))
				{
					logger.debug("是投连,计算风险保费!!");
					if(!dealRiskFee(tLPPolSchema))
					{
						return false;
					}
					if(!prepareTLMainPol(tLPPolSchema))
					{
						return false;
					}
				}else{
					if (!ReCalculate(tLPPolSchema, tLPEdorItemSchema)) // 重算保费、保额，并计算各期保费的补退费和利息。
					{
						return false;
					}
				}
			}
			else //appflag为4的险种记录，虽然不需要重算，但是由于涉及到合并客户号后信息需要修改，仍然需要放到集合中
			{
   			   mLPPolSet.add(tLPPolSchema);
			}

			//保存变化值
			mChgPrem += tLPPolSchema.getPrem() - mSourcePrem;
			mChgAmnt += tLPPolSchema.getAmnt() - mSourceAmnt;

			
		}
		
		if(tTLFlag)
		{
			//账户预估
			if(!PreAssessAccValue())
			{
				return false;
			}
		}
		
		tLPContSchema.setPrem(Double.parseDouble(new DecimalFormat("0.00")
				.format(mChgPrem + tLPContSchema.getPrem())));
		tLPContSchema.setSumPrem(Double.parseDouble(new DecimalFormat("0.00")
				.format(mChgPrem + tLPContSchema.getSumPrem())));
		tLPContSchema.setAmnt(Double.parseDouble(new DecimalFormat("0.00")
				.format(mChgAmnt + tLPContSchema.getAmnt())));
		tLPContSchema.setMakeDate(strCurrentDate);
		tLPContSchema.setMakeTime(strCurrentTime);
		tLPContSchema.setModifyDate(strCurrentDate);
		tLPContSchema.setModifyTime(strCurrentTime);

		mLPContSet.add(tLPContSchema);

		String UPDATEStr = " update LPEdorItem " + " set "
				+ " EdorState='3',ChgPrem= ?mChgPrem?,"
				+ " GrpContNo         = '?GrpContNo?'," + " ChgAmnt         = ?mChgAmnt?,"
				+ " GetMoney        = ?GetMoney?,"
				+ " GetInterest     = ?mGetInterest?,"
				+ " StandbyFlag1    = '?tCMEdorNo?',"
				+ " StandbyFlag2    = '?tCustmerNo?',"
				+ " StandbyFlag3    = '?tISErrFlag?'" //出错但在两年之外的提示
				+ " where EdorAcceptNo ='?EdorAcceptNo?'" + " and EdorNo ='?EdorNo?'" + " and EdorType ='?EdorType?'" + " and ContNo ='?ContNo?'";

		SQLwithBindVariables sbv9=new SQLwithBindVariables();
		sbv9.sql(UPDATEStr);
		sbv9.put("mChgPrem", mChgPrem);
		sbv9.put("GrpContNo", tLPEdorItemSchema.getGrpContNo());
		sbv9.put("mChgAmnt", mChgAmnt);
		sbv9.put("GetMoney", (mGetMoney+mGetInterest));
		sbv9.put("mGetInterest", mGetInterest);
		sbv9.put("tCMEdorNo", tCMEdorNo);
		sbv9.put("tCustmerNo", tCustmerNo);
		sbv9.put("tISErrFlag", tISErrFlag);
		sbv9.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		sbv9.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv9.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv9.put("ContNo", mLPEdorItemSchema.getContNo());
		mMap.put(sbv9, "UPDATE");

		return true;
	}
	
	private boolean prepareTLMainPol(LPPolSchema tLPPolSchema)
	{

		//投连主险不重算，直接保存
		String sDelLPDuty = " delete from lpduty where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' and polno='?polno?'";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(sDelLPDuty);
        sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv1.put("edortype", mLPEdorItemSchema.getEdorType());
        sbv1.put("contno", mLPEdorItemSchema.getContNo());
        sbv1.put("polno", tLPPolSchema.getPolNo());
		
		String sDelLPPrem = " delete from lpprem where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' and polno='?polno?'";
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql(sDelLPPrem);
        sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
        sbv2.put("contno", mLPEdorItemSchema.getContNo());
        sbv2.put("polno", tLPPolSchema.getPolNo());

		String sDelLPGet = " delete from lpget where edorno = '?edorno?' and edortype = '?edortype?' and contno = '?contno?' and polno='?polno?'";
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql(sDelLPGet);
        sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv3.put("edortype", mLPEdorItemSchema.getEdorType());
        sbv3.put("contno", mLPEdorItemSchema.getContNo());
        sbv3.put("polno", tLPPolSchema.getPolNo());


		String sInsertLPDuty = " insert into LPDuty ( select " + "'?edorno?', "
		+ "'?edortype?', " + " c.* " + " from LCDuty c where c.contno = '?contno?' and polno='?polno?')";
        SQLwithBindVariables sbv4=new SQLwithBindVariables();
        sbv4.sql(sInsertLPDuty);
        sbv4.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv4.put("edortype", mLPEdorItemSchema.getEdorType());
        sbv4.put("contno", mLPEdorItemSchema.getContNo());
        sbv4.put("polno", tLPPolSchema.getPolNo());

		String sInsertLPPrem = " insert into LPPrem ( select " + "'?edorno?', "
		+ "'?edortype?', " + " c.* " + " from LCPrem c where c.contno = '?contno?' and polno='?polno?')";
        SQLwithBindVariables sbv5=new SQLwithBindVariables();
        sbv5.sql(sInsertLPPrem);
        sbv5.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv5.put("edortype", mLPEdorItemSchema.getEdorType());
        sbv5.put("contno", mLPEdorItemSchema.getContNo());
        sbv5.put("polno", tLPPolSchema.getPolNo());

		String sInsertLPGet = " insert into LPGet ( select " + "'?edorno?', "
		+ "'?edortype?', " + " c.* " + " from LCGet c where c.contno = '?contno?' and polno='?polno?')";
        SQLwithBindVariables sbv6=new SQLwithBindVariables();
        sbv6.sql(sInsertLPGet);
        sbv6.put("edorno", mLPEdorItemSchema.getEdorNo());
        sbv6.put("edortype", mLPEdorItemSchema.getEdorType());
        sbv6.put("contno", mLPEdorItemSchema.getContNo());
        sbv6.put("polno", tLPPolSchema.getPolNo());

		mMap.put(sbv1, "DELETE");
		mMap.put(sbv2, "DELETE");
		mMap.put(sbv3, "DELETE");
		mMap.put(sbv4, "INSERT");
		mMap.put(sbv5, "INSERT");
		mMap.put(sbv6, "INSERT");

		return true;
	}

	private boolean ReCalculate(LPPolSchema aLPPolSchema,
			LPEdorItemSchema aLPEdorItemSchema) {
		FDate tD = new FDate();

		LPPolSet insrtLPPolSet = new LPPolSet();
		LPPremSet insrtLPPremSet = new LPPremSet();
		LPGetSet insrtLPGetSet = new LPGetSet();
		LPDutySet insrtLPDutySet = new LPDutySet();


		LDCode1DB tLDCode1DB = new LDCode1DB();


		
		ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, aLPEdorItemSchema);
		// 准备重算需要的保单表数据
		LCPolBL tLCPolBL = tReCalBL.getRecalPol(aLPPolSchema);
		// 准备重算需要的责任表数据
		LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(aLPEdorItemSchema);
		// 准备重算需要的保费项表数据
		LCPremSet rLCPremSet = tReCalBL.getRecalPrem(aLPEdorItemSchema);
		// 准备重算需要的领取项表数据
		LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(aLPEdorItemSchema);
		
		String tSeatNo = tLCPolBL.getSeatNo();
		tLCPolBL.setSeatNo(mAppntAge);
		for (int i = 1; i <= tLCDutyBLSet.size(); i++) {
			// comment by jiaqiangli 主要是豁免联动需要处理这个条件
			tLCDutyBLSet.get(i).setAmnt(aLPPolSchema.getAmnt());
			tLCDutyBLSet.get(i).setPayEndYear(aLPPolSchema.getPayEndYear());
			tLCDutyBLSet.get(i).setPayEndYearFlag(aLPPolSchema.getPayEndYearFlag());
		}

		// 重算 得到保费结构
		if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, rLCPremSet, tLCGetBLSet, aLPEdorItemSchema)) {
			this.mErrors.copyAllErrors(tReCalBL.mErrors);
			CError.buildErr(this, "保费重算失败,可能是有险种不支持此变更的重要要素!");
			return false;
		}
		
		//重算后的保费结构
		aLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1));
		insrtLPDutySet.add(tReCalBL.aftLPDutySet);
		insrtLPGetSet.add(tReCalBL.aftLPGetSet);
		

		// 重新计算加费金额
		AddPremReCalBQInterface tAddPremReCalBQInterface = new AddPremReCalBQInterface(mGlobalInput);
		LPPremSet afterLPPremSet = tAddPremReCalBQInterface.recalAddPrem2(aLPPolSchema, tReCalBL.aftLPPremSet, tLCDutyBLSet,this.InsuredSex,this.InsuredOccupationType);
		if (afterLPPremSet == null || afterLPPremSet.size() == 0) {
			return false;
		}
		
		aLPPolSchema.setPrem(aLPPolSchema.getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
		insrtLPPremSet.add(afterLPPremSet);
	
		aLPPolSchema.setEdorNo(aLPEdorItemSchema.getEdorNo());
		aLPPolSchema.setEdorType(aLPEdorItemSchema.getEdorType());
		aLPPolSchema.setModifyDate(PubFun.getCurrentDate());
		aLPPolSchema.setModifyTime(PubFun.getCurrentTime());
		
		
		//lcduty
		for (int i = 1; i <= insrtLPDutySet.size(); i++) {
			//LCDUTY的standprem重算已经置上 此处prem需要累加加费重算的差额 同lcpol的处理
			insrtLPDutySet.get(i).setPrem(insrtLPDutySet.get(i).getPrem()+tAddPremReCalBQInterface.getAddFeeMinus());
			insrtLPDutySet.get(i).setModifyDate(PubFun.getCurrentDate());
			insrtLPDutySet.get(i).setModifyTime(PubFun.getCurrentTime());			
		}
		aLPPolSchema.setPrem(aLPPolSchema.getPrem()
				+ tAddPremReCalBQInterface.getAddFeeMinus());
		aLPPolSchema.setSeatNo(tSeatNo);
		insrtLPPolSet.add(aLPPolSchema.getSchema());

		mLPPolSet.add(insrtLPPolSet);
		mLPDutySet.add(insrtLPDutySet);
		mLPPremSet.add(insrtLPPremSet);
		mLPGetSet.add(insrtLPGetSet);
		
		// 按照最新交费间隔计算交费期数
		int premNum = 0;

		if (aLPPolSchema.getPayIntv() == 0) {
			premNum = 1;
		} else {
			premNum = PubFun.calInterval2(aLPPolSchema.getCValiDate(),
					aLPPolSchema.getPaytoDate(), "M")
					/ aLPPolSchema.getPayIntv();
		}

		double intervalMoney = 0; // 保费差额变量
		double interestMoney = 0; // 总利息变量
		Date tPayDate = tD.getDate(aLPPolSchema.getCValiDate()); // 各期交费对应日，初始化为首期交费日

		AccountManage tAccountManage = new AccountManage();
		tAccountManage.setPayEndYear(Integer.toString(aLPPolSchema
				.getPayEndYear()));
		tAccountManage.setPayIntv(Integer.toString(aLPPolSchema
				.getPayIntv()));

		tLDCode1DB.setCodeType(aLPEdorItemSchema.getEdorType());
		tLDCode1DB.setCode("BF");
		tLDCode1DB.setCode1("000000");
		if (!tLDCode1DB.getInfo()) // 获取补费会计科目
		{
			mErrors.copyAllErrors(tLDCode1DB.mErrors);
			mErrors.addOneError(new CError("获取财务代码失败！"));
			return false;
		}
		String BFSubject = tLDCode1DB.getCodeName();

		tLDCode1DB.setCode("LX");
		if (!tLDCode1DB.getInfo()) // 获取利息会计科目
		{
			mErrors.copyAllErrors(tLDCode1DB.mErrors);
			mErrors.addOneError(new CError("获取财务代码失败！"));
			return false;
		}
		String LXSubject = tLDCode1DB.getCodeName();

		tLDCode1DB.setCode("TF");
		if (!tLDCode1DB.getInfo()) // 获取退费会计科目
		{
			mErrors.copyAllErrors(tLDCode1DB.mErrors);
			mErrors.addOneError(new CError("获取财务代码失败！"));
			return false;
		}
		String TFSubject = tLDCode1DB.getCodeName();

		double tSumGetMoney=0;
		double tSumGetInterest=0;
		LCPremDB tLCPremDB = new LCPremDB();
		LCPremSet tLCPremSet = new LCPremSet();
		for (int i = 0; i < premNum; i++) {
			try {
				intervalMoney = 0; // 保费差额变量
				interestMoney = 0; // 总利息变量
				String tSql = "select * from LCPrem where PolNo = '?PolNo?' ";
				tSql += " and PayStartDate <= '?tPayDate?' and PayEndDate >= '?tPayDate?'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("PolNo", aLPPolSchema.getPolNo());
				sqlbv.put("tPayDate", tD.getString(tPayDate));
				tLCPremSet = tLCPremDB.executeQuery(sqlbv);
				String sFreeFlag = ""; // 豁免标志
				String sFreeStartDate = ""; // 豁免起期
				String sFreeEndDate = ""; // 豁免止期
				for (int j = 1; j <= mLPPremSet.size(); j++) {
					intervalMoney = 0; // 保费差额变量
					interestMoney = 0; // 总利息变量
					for (int k = 1; k <= tLCPremSet.size(); k++) {
						if (mLPPremSet.get(j).getPayPlanCode().substring(0,
								6).equals(
								tLCPremSet.get(k).getPayPlanCode()
										.substring(0, 6))) {
							intervalMoney = PubFun.round(mLPPremSet.get(j).getPrem(),2) //在内存中进行格式化，否则可能出现1分钱的情况
									- tLCPremSet.get(k).getPrem();

							sFreeFlag = tLCPremSet.get(k).getFreeFlag();
							sFreeStartDate = tLCPremSet.get(k)
									.getFreeStartDate();
							sFreeEndDate = tLCPremSet.get(k)
									.getFreeEndDate();

							break;
						}
					}
					//add by xiongzh 2010-3-26
					//对于申请日期在保单生效日期之前的，例如保单续保，保单生效日期2009-3-4，CM申请日期2010-3-3，然后IC申请日期借用CM申请日期，
					//也是2010-3-3，但是实际上IC操作时间是2010-3-4,而此时保单已核销，生效日变为2010-3-4，这就出现申请日期在生效日期之前的情况
					//或者操作员在2010-3-5进行CM,IC操作，但是申请的时候把申请日期前置为2010-3-3，也会出现以上问题
					//此处添加特殊处理，申请日期在保单生效日期之前的，利息记0
					double tRate = 0.0;
					if(tD.getString(tPayDate).compareTo(aLPEdorItemSchema.getEdorAppDate())>0)
					{
						tRate = 0.0;
					}
					else
					{
						 tRate = AccountManage.calMultiRateMS(tD
								.getString(tPayDate), aLPEdorItemSchema
								.getEdorAppDate(), "000000", "IC", "L", "C",
								"Y",aLPPolSchema.getCurrency());
						if (tRate + 1 == 0) {
							mErrors.copyAllErrors(tAccountManage.mErrors);
							CError.buildErr(this, "利息计算失败！");
							return false;
						}
					}
					interestMoney = intervalMoney * tRate;
					// 判断本期该保费项是否豁免
					if (sFreeFlag != null && sFreeFlag.equals("1")) // 1-豁免
					{
						int intvStart = PubFun.calInterval(sFreeStartDate,
								tD.getString(tPayDate), "D");
						int intvEnd = PubFun.calInterval(sFreeEndDate, tD
								.getString(tPayDate), "D");
						if (intvStart >= 0 && intvEnd <= 0) // 该期保费豁免
						{
							continue;
						}
					}
				}

				tSumGetMoney += intervalMoney;
				tSumGetInterest += interestMoney;
				tPayDate = PubFun.calDate(tPayDate, aLPPolSchema
						.getPayIntv(), "M", null);
			} catch (Exception ex) {
				CError.buildErr(this, "保费补退费计算异常！");
				return false;
			}

		}
		String tSubOperType = "";
		String tSubOperTypeInterest = "";
		if (tSumGetMoney >= 0) {
			tSubOperType = BqCode.Pay_Prem;
			tSubOperTypeInterest = BqCode.Pay_PremInterest;
		} else if (tSumGetMoney < 0) {
			tSubOperType = BqCode.Get_Prem;
		}
		if (tSumGetMoney > 0) // 实交保费少于应交保费，则补交保费及利息
		{

			tSumGetMoney = Double
					.parseDouble(new DecimalFormat("0.00")
							.format(tSumGetMoney));
			tSumGetInterest = Double.parseDouble(new DecimalFormat(
					"0.00").format(tSumGetInterest));

			if (tSumGetMoney != 0) {
				addLJSGetEndorse(aLPEdorItemSchema, aLPPolSchema,
						"000000", "000000", BFSubject,
						tSubOperType, tSumGetMoney, "0"); // 在批改补退费表中增加单期补交保费记录
			}
			if (tSumGetInterest != 0) {
				addLJSGetEndorse(aLPEdorItemSchema, aLPPolSchema,
						"000000", "000000", LXSubject,
						tSubOperTypeInterest, tSumGetInterest, "0"); // 在批改补退费表中增加单期补交利息记录
			}
			mGetMoney += tSumGetMoney;
			mGetInterest += tSumGetInterest;
		} else
		// 实交保费多于应交保费，则退还保费
		{
			tSumGetMoney = Double
					.parseDouble(new DecimalFormat("0.00")
							.format(tSumGetMoney));
			if (tSumGetMoney != 0) {
				addLJSGetEndorse(aLPEdorItemSchema, aLPPolSchema,
						"000000", "000000", TFSubject,
						tSubOperType, tSumGetMoney, "1"); // 在批改补退费表中增加单期退还保费记录
			}
			mGetMoney += tSumGetMoney;
		}
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		LCDiscountSet tLCDiscountSet = new LCDiscountSet();
		LCDiscountDB tLCDiscountDB = new LCDiscountDB();
		tLCDiscountDB.setPolNo(aLPPolSchema.getPolNo());
		tLCDiscountSet = tLCDiscountDB.query();
		if(tLCDiscountSet!=null && tLCDiscountSet.size()>0){
			LPDiscountSet tLPDiscountSet = new LPDiscountSet();
			for(int i=1;i<=tLCDiscountSet.size();i++){
				LPDiscountSchema tLPDiscountSchema = new LPDiscountSchema();
				tRef.transFields(tLPDiscountSchema, tLCDiscountSet.get(i));
				tLPDiscountSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPDiscountSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPDiscountSet.add(tLPDiscountSchema);
			}
			TransferData tTransferData = new TransferData();
			tTransferData.setNameAndValue("PayCount", "1");
			tTransferData.setNameAndValue("PayIntv", String.valueOf(aLPPolSchema.getPayIntv()));
			tTransferData.setNameAndValue("Operator", mGlobalInput.Operator);
			PEdorDiscountCalBL tDiscountCalBL = new PEdorDiscountCalBL();
			VData tzkVData = new VData();
			tzkVData.add(aLPPolSchema);
			tzkVData.add(insrtLPPremSet);
			tzkVData.add(tLPDiscountSet);
			tzkVData.add(mLPEdorItemSchema);
			tzkVData.add(tTransferData);
			//得到该保单折扣减去的钱 ，为负值
			if(!tDiscountCalBL.calculate(tzkVData))
			{
				CError.buildErr(this, "折扣计算失败！");
				return false;
			}
			VData rVData = tDiscountCalBL.getResult();
			tLJSPayPersonSet = (LJSPayPersonSet)rVData.getObjectByObjectName("LJSPayPersonSet", 0);
			if(tLJSPayPersonSet!=null && tLJSPayPersonSet.size()>0)
			{
				// 添加保费LJSGetEndorse
				for(int i=1;i<=tLJSPayPersonSet.size();i++)
				{
					if (!createLJSGetEndorseSchemaZK(mLPEdorItemSchema,aLPPolSchema,tLJSPayPersonSet.get(i))) {
						mErrors.addOneError("添加保费LJSGetEndorse!");
					}
				}			
			}
			mLPDiscountSet.add(tLPDiscountSet);
			
		}
		
	
		return true;
	}


	private boolean  PreAssessAccValue()
	{
		ExeSQL tExeSQL = new ExeSQL();
		logger.debug("补退费 合计=================="+mTLMainMoney);
		//预估帐户价值,同时也要算上追加未到帐和续期未到帐的


		String CHKACCSQL = "select (" +
		//计算一个insuaccno下的未计价的非转换的单位
		"select (case when sum(((case when unitcount is not null then unitcount else 0 end)+(select (case when sum(unitcount) is not null then sum(unitcount) else 0 end) from lcinsureacctrace where contno =a.contno and state = '0' and insuaccno = a.insuaccno and busytype != 'TI'))" +
		"*" +
		//取该帐户的价格
		"(select UnitPriceSell from loaccunitprice where insuaccno = a.insuaccno and startdate = (select max(startdate) from loaccunitprice where startdate <= now() and insuaccno = a.insuaccno ))) is not null then sum(((case when unitcount is not null then unitcount else 0 end)+(select (case when sum(unitcount) is not null then sum(unitcount) else 0 end) from lcinsureacctrace where contno =a.contno and state = '0' and insuaccno = a.insuaccno and busytype != 'TI'))" +
		"*" +
		//取该帐户的价格
		"(select UnitPriceSell from loaccunitprice where insuaccno = a.insuaccno and startdate = (select max(startdate) from loaccunitprice where startdate <= now() and insuaccno = a.insuaccno ))) else 0 end) " +
		"from lcinsureacc a where contno = '?contno?') +" +
		//去trace里的没计价的钱
		"(select (case when sum(money) is not null then sum(money) else 0 end) from lcinsureacctrace where contno = '?contno?' and state = '0') " +
		"from dual";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(CHKACCSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		
		double accSumMoney = Double.parseDouble(tExeSQL.getOneValue(sqlbv));

		if(mTLMainMoney>accSumMoney)
		{
			mErrors.addOneError(new CError("保单号为"+mLPEdorItemSchema.getContNo()+"的保单预估帐户余额不足,无法完成本次保全！请先追加保费!"));
			return false;
		}

		return true;
	}
	
	private boolean dealRiskFee(LPPolSchema aLPPolSchema)
	{
		String tSql ="select paydate,abs(sum(money)) from lcinsureacctrace where riskcode ='RPUL' and moneytype='GL' and polno = '?polno?' group by paydate order by paydate ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("polno", aLPPolSchema.getPolNo());
		double tTotalRiskFee=0.00;
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		tLCInsureAccDB.setContNo(aLPPolSchema.getContNo());
		tLCInsureAccDB.setPolNo(aLPPolSchema.getPolNo());
		tLCInsureAccSet = tLCInsureAccDB.query();


		// 组织计算要素
		CalBase tCalBase = new CalBase();
		tCalBase.setSex(aLPPolSchema.getInsuredSex());
		logger.debug("组织计算要素保额============================"+aLPPolSchema.getAmnt());
		tCalBase.setAmnt(aLPPolSchema.getAmnt());
		tCalBase.setOccupation(aLPPolSchema.getOccupationType());
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql("select (case when sum(SuppRiskScore) is not null then sum(SuppRiskScore) else 0 end) from lcprem where polno='?polno?'");
		sbv.put("polno", aLPPolSchema.getPolNo());
		tCalBase.setSuppRiskScore(Double.parseDouble(tExeSQL
				.getOneValue(sbv)));
		for (int i =1;i<=tSSRS.getMaxRow();i++)
		{
			String tDealDate =tSSRS.GetText(i, 1);

			double tOldRiskFee = Double.parseDouble(tSSRS.GetText(i, 2));

			tCalBase.setAppntAge(PubFun.calAppAge(aLPPolSchema.getInsuredBirthday(), tDealDate, "Y"));
			double tNewManageFee =PEdorAADetailBL.calInsuAccManageFee(tLCInsureAccSet.get(1),tCalBase,mLPEdorItemSchema.getEdorValiDate());

			logger.debug("原管理费============================"+tOldRiskFee);
			logger.debug("变更后管理费============================"+tNewManageFee);

			tTotalRiskFee +=(tNewManageFee -tOldRiskFee);

		}
//			是投连,合计补退费
		mTLMainMoney+=tTotalRiskFee;

		if(tTotalRiskFee!=0)
		{
			LDCode1DB tLDCode1DB = new LDCode1DB();
			tLDCode1DB.setCodeType(mLPEdorItemSchema.getEdorType());

			String PremType = "";

			if(tTotalRiskFee>0)
			{
				tLDCode1DB.setCode("BF");
				PremType = BqCode.Pay_MangeFee;
			}
			else
			{
				tLDCode1DB.setCode("TF");
				PremType = BqCode.Get_MangeFee;
			}
			tLDCode1DB.setCode1("000000");
			if (!tLDCode1DB.getInfo()) // 获取退费会计科目
			{
				mErrors.copyAllErrors(tLDCode1DB.mErrors);
				mErrors.addOneError(new CError("获取财务代码失败！"));
				return false;
			}
			String FSubject = tLDCode1DB.getCodeName();

			createLJSGetEndorse(mLPEdorItemSchema, aLPPolSchema , FSubject,PremType , tTotalRiskFee); // 在批改补退费表中增加单期退还保费记录
			if(tTLFlag)
			{
				createLJSGetEndorse(mLPEdorItemSchema, aLPPolSchema , FSubject,BqCode.Pos_Adjust , -tTotalRiskFee); // 在批改补退费表中增加单期退还保费记录
			}
		}

		return true;
	}

	/**
	 * 往批改补退费表（应收/应付）新增数据
	 * 
	 * @return
	 */
	private boolean addLJSGetEndorse(LPEdorItemSchema aLPEdorItemSchema,
			LPPolSchema aLPPolSchema, String aDutyCode, String aPayPlanCode,
			String feeType, String subType, double feeMoney, String tGetFLag) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = mBqCalBL.initLJSGetEndorse(
				aLPEdorItemSchema, aLPPolSchema, aDutyCode, aPayPlanCode, subType,
				feeType, Math.abs(feeMoney), mGlobalInput);
		if (tLJSGetEndorseSchema != null) {
			tLJSGetEndorseSchema.setInsuredNo(aLPEdorItemSchema.getInsuredNo());
			tLJSGetEndorseSchema.setGetFlag(tGetFLag);
			//营改增 add zhangyingfeng 2016-07-07
			//价税分离 计算器
			TaxCalculator.calBySchema(tLJSGetEndorseSchema);
			//end zhangyingfeng 2016-07-07
			mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}

		return true;
	}

	private double getExemptAMNT(LPPolSet insrtLPPolSet, String tRiskCode) {
		//累加豁免险保费
		double tAllExemptPrem = 0.00;
		//豁免累加
		String tExemptSQL = "select polno from lcpol where  appflag='1' and contno = '?contno?' and payintv > 0 and riskcode in (select code1 from ldcode1 where codetype = 'freerisk' and code='?code?')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		sqlbv.put("code", tRiskCode);
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		//查询重算后的保费
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			for (int j = 1; j <= insrtLPPolSet.size(); j++) {
				if (tSSRS.GetText(i, 1).equals(insrtLPPolSet.get(j).getPolNo())) {
					//取prem而非standprem
					tAllExemptPrem += insrtLPPolSet.get(j).getPrem();
				}
			}
		}
		return tAllExemptPrem;
	}

	private boolean judgeExempt(String tPolNo) {
		//add by jiaqiangli 附加豁免联动处理 2008-12-22
		LCPolDB tExemptLCPolDB = new LCPolDB();
		LCPolSet tExemptLCPolSet = new LCPolSet();
		//判断是否有附加豁免关联
		//更简洁的判断只需要第一个条件即可
		String tExemptSQL = "select * from lcpol a where a.appflag='1' and  polno = '?tPolNo?' and contno = '?contno?' and riskcode in (select riskcode from lmriskapp where risktype7 in ('1','2')) "
				// exists 作关联的判断逻辑
				+ "and payintv > 0 and exists (select 1 from lcpol b,ldcode1 c where contno = '?contno?' and c.code=a.riskcode and b.riskcode=c.code1)";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tExemptSQL);
		sqlbv.put("tPolNo", tPolNo);
		sqlbv.put("contno", mLPEdorItemSchema.getContNo());
		tExemptLCPolSet = tExemptLCPolDB.executeQuery(sqlbv);
		logger.debug("tExemptLCPolSet.size()" + tExemptLCPolSet.size());
		if (tExemptLCPolSet.size() == 1)
			return true;
		else
			return false;
	}

	/***************************************************************************
	 * 角色表LMRiskRole表校验方法
	 */
	private boolean checkLMRiskRole(LPPolSchema aLPPolSchema, String aRiskRole,
			String aDate, String bDate) {
		LMRiskRoleDB aLMRiskRoleDB = new LMRiskRoleDB();
		LMRiskRoleSet aLMRiskRoleSet = new LMRiskRoleSet();
		aLMRiskRoleDB.setRiskCode(aLPPolSchema.getRiskCode());
		aLMRiskRoleDB.setRiskRole(aRiskRole);
		aLMRiskRoleSet = aLMRiskRoleDB.query();
		if (aLMRiskRoleSet.size() > 0) {
			int maxAgeIntv = PubFun.calInterval(aDate, bDate, aLMRiskRoleSet
					.get(1).getMAXAppAgeFlag().trim());
			int minAgeIntv = PubFun.calInterval(aDate, bDate, aLMRiskRoleSet
					.get(1).getMinAppAgeFlag().trim());
			logger.debug("判断年龄000maxAgeIntv: " + maxAgeIntv);
			logger.debug("判断年龄000minAgeIntv: " + minAgeIntv);
			// 判断年龄
			if (minAgeIntv < aLMRiskRoleSet.get(1).getMinAppAge()) {
				logger.debug("判断年龄111minAgeIntv: " + minAgeIntv);
				return false;
			}
			if (maxAgeIntv > aLMRiskRoleSet.get(1).getMAXAppAge()) {
				logger.debug("判断年龄111maxAgeIntv: " + maxAgeIntv);
				return false;
			}
		}
		return true;
	}

	
	private boolean prepareOutputData() {

		String tCustomerNo="";//由于可能是合并之后的客户号，所以需要从CM记录中获取
		String IsAppntFlag="0";
		String IsInsuredFlag="0";
		String BothAppntInsuredFlag="0";
		//投保人  为核保准备数据
        //以险种号取得CM生成的LPAppnt记录，复制并修改edortype,edorno
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntDB xLPAppntDB = new LPAppntDB();
		xLPAppntDB.setEdorNo(this.tCMEdorNo);
		xLPAppntDB.setEdorType("CM");
		xLPAppntDB.setContNo(mLPEdorItemSchema.getContNo());
		if(xLPAppntDB.query().size()>0)
		{
			LPAppntSchema tLPAppntSchema = null;
			tLPAppntSchema = xLPAppntDB.query().get(1);
			tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPAppntSchema.setEdorType("IC");
			tLPAppntSchema.setMakeDate(strCurrentDate);
			tLPAppntSchema.setMakeTime(strCurrentTime);
			tLPAppntSchema.setModifyDate(strCurrentDate);
			tLPAppntSchema.setModifyTime(strCurrentTime);
			
			IsAppntFlag="1";
			tCustomerNo = tLPAppntSchema.getAppntNo();
			tLPAppntSet.add(tLPAppntSchema);
		}

		
		//被保人
		// 查询客户所有相关保单（客户为投保人）
        //以险种号取得CM生成的LPInsured记录，复制并修改edortype,edorno
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LPInsuredDB xLPInsuredDB = new LPInsuredDB();
		xLPInsuredDB.setEdorNo(this.tCMEdorNo);
		xLPInsuredDB.setEdorType("CM");
		xLPInsuredDB.setContNo(mLPEdorItemSchema.getContNo());
		//xLPInsuredDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		
		if(xLPInsuredDB.query().size()>0)
		{	
			LPInsuredSchema tLPInsuredSchema = null;
			tLPInsuredSchema = xLPInsuredDB.query().get(1);
			tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPInsuredSchema.setEdorType("IC");
			tLPInsuredSchema.setMakeDate(strCurrentDate);
			tLPInsuredSchema.setMakeTime(strCurrentTime);
			tLPInsuredSchema.setModifyDate(strCurrentDate);
			tLPInsuredSchema.setModifyTime(strCurrentTime);
			
			IsInsuredFlag="1";
			tCustomerNo = tLPInsuredSchema.getInsuredNo();
			if("1".equals(IsAppntFlag))
			{
				BothAppntInsuredFlag="1";
			}
			
			tLPInsuredSet.add(tLPInsuredSchema);
		}

		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		if (tLCCSpecSet.size() > 0) {
			for (int k = 1; k <= tLCCSpecSet.size(); k++) {
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				tRef.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);
			}
			mMap.put(mLPCSpecSet, "DELETE&INSERT");
		}
		//之前为了防止合并客户号影响保费重算，lppol中涉及到客户号的都没有修改，需要在此统一修改
		for(int i=1;i<=mLPPolSet.size();i++)
		{
			if("1".equals(IsAppntFlag)||"1".equals(BothAppntInsuredFlag))
			{
			   mLPPolSet.get(i).setAppntNo(tCustomerNo);
			}
			if("1".equals(IsInsuredFlag)||"1".equals(BothAppntInsuredFlag))
			{
			   mLPPolSet.get(i).setInsuredNo(tCustomerNo);
			}	
			if("1".equals(BothAppntInsuredFlag))
			{
				mLPPolSet.get(i).setAppntNo(tCustomerNo);
				mLPPolSet.get(i).setInsuredNo(tCustomerNo);
			}
		}

		if(aZKLJSGetEndorseSet.size()>0){
			mLJSGetEndorseSet.add(aZKLJSGetEndorseSet);
		}

		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLPAppntSet, "DELETE&INSERT");

		mMap.put(mLPContSet, "DELETE&INSERT");
		mMap.put(mLPPolSet, "DELETE&INSERT");
		mMap.put(mLPDutySet, "DELETE&INSERT");
		mMap.put(mLPPremSet, "DELETE&INSERT");
		mMap.put(mLPGetSet, "DELETE&INSERT");
		mMap.put(mLJSGetEndorseSet, "DELETE&INSERT");
		if(mLPDiscountSet.size()>0){
			mMap.put(mLPDiscountSet, "DELETE&INSERT");
		}
		mResult.clear();
		mResult.add(mMap);
		mResult.add(mTransferData);
		return true;
	}
	
	private boolean createLJSGetEndorse(LPEdorItemSchema aLPEdorItemSchema, LPPolSchema aLPPolSchema, String feeType, String subType, double feeMoney)
	{
		LJSGetEndorseSchema tLJSGetEndorseSchema = mBqCalBL.initLJSGetEndorse(aLPEdorItemSchema, aLPPolSchema,
				null, subType, feeType, feeMoney, mGlobalInput);
		if (tLJSGetEndorseSchema != null)
		{
			tLJSGetEndorseSchema.setInsuredNo(aLPEdorItemSchema.getInsuredNo());
			//营改增  add  zhangyingfeng 2016-07-07
			//价税分离 计算器
			TaxCalculator.calBySchema(tLJSGetEndorseSchema);
			//end  zhangyingfeng 2016-07-07
			mLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		}

		return true;
	}
	
	private boolean createLJSGetEndorseSchemaZK(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, LJSPayPersonSchema cLJSPayPersonSchema) {

		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema = this.initLJSGetEndorse(tLPEdorItemSchema,tLPPolSchema,"ZK");
		tRef.transFields(tLJSGetEndorseSchema, cLJSPayPersonSchema);
		tLJSGetEndorseSchema.setGetMoney(cLJSPayPersonSchema.getSumDuePayMoney());

		tLJSGetEndorseSchema.setSubFeeOperationType(cLJSPayPersonSchema.getPayType());
		//营改增  add  zhangyingfeng 2016-07-07
		//价税分离 计算器
		TaxCalculator.calBySchema(tLJSGetEndorseSchema);
		//end  zhangyingfeng 2016-07-07
		aZKLJSGetEndorseSet.add(tLJSGetEndorseSchema);
		return true;
	}
	
	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */
	private LJSGetEndorseSchema initLJSGetEndorse(LPEdorItemSchema tLPEdorItemSchema, LPPolSchema tLPPolSchema, String strfinType) {
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();
		tLJSGetEndorseSchema.setGetNoticeNo(tLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setEndorsementNo(tLPEdorItemSchema.getEdorNo());
		tLJSGetEndorseSchema.setFeeOperationType(tLPEdorItemSchema.getEdorType());
		tLJSGetEndorseSchema.setFeeFinaType(strfinType);
		tLJSGetEndorseSchema.setDutyCode("0");
		tLJSGetEndorseSchema.setPayPlanCode("0");
		// 走保全交费财务流程
		tLJSGetEndorseSchema.setOtherNo(tLPEdorItemSchema.getEdorNo()); // 保全给付用批单号
		tLJSGetEndorseSchema.setOtherNoType("3"); // 保全给付
		tRef.transFields(tLJSGetEndorseSchema, tLPPolSchema);
		tLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		tLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());
		tLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		tLJSGetEndorseSchema.setGetDate(tLPEdorItemSchema.getEdorValiDate());
		// 0为补费1为退费
		tLJSGetEndorseSchema.setGetFlag("0");

		return tLJSGetEndorseSchema;
	}

}
