package com.sinosoft.lis.tb;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>
 * Title: 新契约投保规则
 * </p>
 * 
 * <p>
 * Description: 新契约投保规则
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
 * @author zhangfh
 * @version 6.0
 */

public class PrepareBOMTBBL {
private static Logger logger = Logger.getLogger(PrepareBOMTBBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private CachedRiskInfo mCRI = CachedRiskInfo.getInstance();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ExeSQL mExeSQL = new ExeSQL();
	private String theDate = "";
	private static GlobalCheckSpot mGlobalCheckSpot = GlobalCheckSpot.getInstance();
	private List tPolList = new ArrayList();//存放险种自核不通过信息的list
	private List tContList = new ArrayList();//存放合同自核不通过提示信息的list
	private List tInsList = new ArrayList();//存放被保人自核不通过提示信息的list
	
	private List mBomList = new ArrayList();
	
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCInsuredSchema xLCInsuredSchema = new LCInsuredSchema();

	private String mContNo = "";

	
	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCInsuredSet mAllInsuredSet = new LCInsuredSet();


	
	/** 发送拒保通知书 */
	LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private double LSumDangerAmnt = 0; // 同一被保险人下寿险类的累计危险保额add by yaory
	private double DSumDangerAmnt = 0; // 同一被保险人下重大疾病类的累计危险保额add by yaory
	private double ASumDangerAmnt = 0; // 同一被保险人下人身意外伤害类的累计危险保额add by yaory
	private double MSumDangerAmnt = 0; // 同一被保险人下人身意外医疗类的累计危险保额add by yaory
	private double SSumDangerAmnt = 0; // 同一被保险人下住院医疗类的累计危险保额add by yaory
	private double SSumDieAmnt = 0; // 同一被保险人下累计身故风险保额
	private double AllSumAmnt = 0;
	private String mBankInsu = "0";
	private String tEndDate= PubFun.getCurrentDate();
	private int insuredCount=0;

	private String locCurrency = "";//本币信息
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	private String ContPlanCode="";
	private String ISPlanRiskFlag="";
	private String GetDutyKind="";
	private String Mult="";
	/**
	 * sql绑定变量类
	 */
	private SQLwithBindVariables sqlbv= new SQLwithBindVariables();
//	private int theCount = 0;
	public PrepareBOMTBBL() {
	}


	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            输入数据
	 * @return boolean
	 */
	public List dealData(LCPolSchema tLCPolSchema,LCDutySchema tLCDutySchema,LCInsuredSchema hLCInsuredSchema,VData tData) {
		
		if (!initTran(tData))
		{
			CError.buildErr(this, "调用PrepareBOMTBBL初始化错误！");
		}
		/** 将该合同下的所有信息查询出来,并将相应的数据转储到相应的BOM中*/
		try {
			LCContDB tLCContDB = new LCContDB();
			xLCInsuredSchema=hLCInsuredSchema;
			//赋给保单级
			tEndDate=tLCDutySchema.getEndDate();
			//得到保单数据
			mLCPolSchema=tLCPolSchema;
			mContNo = tLCPolSchema.getContNo(); // 获得保单号
			tLCContDB.setContNo(mContNo);
			LCContSchema tLCContSchema = tLCContDB.query().get(1);
			mLCContSchema=tLCContSchema;
			//被保人信息
			LCInsuredDB tFreeLCInsuredDB = new LCInsuredDB();
			tFreeLCInsuredDB.setContNo(mContNo);
			tFreeLCInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
			insuredCount= tFreeLCInsuredDB.query().size();
			LCInsuredSchema tLCInsuredSchema = tFreeLCInsuredDB.query().get(1);
			//投保人信息
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mContNo);
			if(!tLCAppntDB.getInfo()){
				//CError.buildErr(this, "查询投保人信息失败！");
				return this.mBomList;
			}
			
			//准备BOMCont数据
			BOMCont cont = new BOMCont();
			cont = DealBOMCont(tLCContSchema);
			mBomList.add(cont);
			
			//准备被保人BOMInsured数据
			BOMInsured insureds = new  BOMInsured();
			insureds = DealBOMInsured(hLCInsuredSchema,tLCContSchema);
			mBomList.add(insureds);

			//准备投保人BOMAppnt数据
			BOMAppnt appnt = new  BOMAppnt();
			appnt = DealBOMAppnt(tLCAppntDB.getSchema(),tLCContSchema);
			mBomList.add(appnt);
			
			//准备险种BOMPol数据
			BOMPol pol = new BOMPol();
			pol = DealBOMPol(tLCPolSchema,insureds,tLCDutySchema);
			mBomList.add(pol);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return this.mBomList;
		}
		return this.mBomList;
	}
	private boolean initTran(VData InputData)
	{
		if (InputData == null) {
			CError.buildErr("initTran", "传入参数(VData)不能为空！");
		}
		// 参数载体传入
		ContPlanCode = (String) InputData.getObjectByObjectName(
			"ContPlanCode", 0);
		ISPlanRiskFlag = (String)InputData.getObjectByObjectName("ISPlanRiskFlag", 0);
		GetDutyKind = (String) InputData.getObjectByObjectName(
				"GetDutyKind", 0);
		Mult = (String) InputData.getObjectByObjectName(
				"Mult", 0);
		return true;
	}
		
	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont cont = new BOMCont();
		try{
			//保单号码
			cont.setContNo(tLCContSchema.getContNo());
			//保费
			cont.setPrem(new Double(mLCContSchema.getPrem()));
			//份数
			cont.setMult(new Double(mLCContSchema.getMult()));
			//保额
			cont.setAmnt(Double.valueOf(tLCContSchema.getAmnt()));
			//自动垫交标记
			cont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			//银行账号
			cont.setBankAccNo(tLCContSchema.getBankAccNo());
			//帐户名
			cont.setAccName(tLCContSchema.getAccName());
			//开户行
			cont.setBankCode(tLCContSchema.getBankCode());
			//卡单标记
			cont.setCardFlag(tLCContSchema.getCardFlag());
			//终止日期
			if(!((tEndDate==null)||"".equals(tEndDate))){
				tEndDate = tEndDate+" "+" 00:00:00";
				cont.setEndDate(sdf.parse(tEndDate));//责任终止日期
			}
			if(!(tLCContSchema.getCValiDate()==null||"".equals(tLCContSchema.getCValiDate()))){
				theDate=tLCContSchema.getCValiDate()+" 00:00:00";
				cont.setCvalidate(sdf.parse(theDate));
			}
			//被保人数目
			cont.setInsuredPeoples(Double.valueOf(String.valueOf(insuredCount)));
			//状态
			cont.setAppFlag(tLCContSchema.getAppFlag());//责任终止日期
			
			cont.setUWFlag(tLCContSchema.getUWFlag());
			if(!((tLCContSchema.getMakeDate()==null)||"".equals(tLCContSchema.getMakeDate()))){
				theDate = tLCContSchema.getMakeDate()+" "+tLCContSchema.getMakeTime();
				cont.setMakeDate(sdf.parse(theDate));
			}
			
			//管理机构
			cont.setManageCom(tLCContSchema.getManageCom());
			//需要考虑加在程序里的函数。
			String tPayIntv=String.valueOf(mLCPolSchema.getPayIntv());
			//交费间隔
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				cont.setPayIntv("0");
			}
			else
			{
				cont.setPayIntv(tPayIntv);
			}
			//交费方式
			String tNewPayMode = tLCContSchema.getNewPayMode();
			String tPayMode = tLCContSchema.getPayLocation();
			String tAccName = "";
			if(tNewPayMode!=null&&tNewPayMode.equals("0")){
				cont.setPayMode("0");
				tAccName = tLCContSchema.getNewAccName();
			}else if(tPayMode!=null&&tPayMode.equals("0")) {
				 cont.setPayMode("0");
				 tAccName = tLCContSchema.getAccName();
			}
			else
			{
				cont.setPayMode(tLCContSchema.getPayMode());
			}
			if(!((tLCContSchema.getPolApplyDate()==null)||"".equals(tLCContSchema.getPolApplyDate()))){
				theDate=tLCContSchema.getPolApplyDate()+" 00:00:00";
				cont.setPolApplyDate(sdf.parse(theDate));
				logger.debug(sdf.parse(theDate));
			}
			if(!(tLCContSchema.getRemark()==null||"".equals(tLCContSchema.getRemark()))){
				cont.setRemarkFlag("1");
			}else{
				cont.setRemarkFlag("0");
			}
			//出单方式
			cont.setSellType(tLCContSchema.getSellType());
			//自动续保标记
			cont.setRnewFlag(String.valueOf(tLCContSchema.getRnewFlag()));
			//销售渠道
			cont.setSaleChnl(tLCContSchema.getSaleChnl());
			cont.setSecondaryManagecom(getManagetCom(tLCContSchema.getManageCom(),4));//二级机构
			cont.setThirdStageManagecom(getManagetCom(tLCContSchema.getManageCom(),6));//三级机构
			cont.setFourStageManagecom(getManagetCom(tLCContSchema.getManageCom(),8));//四级机构
			cont.setFirstTrialDate(sdf.parse(tLCContSchema.getFirstTrialDate()+ " "+ "00:00:00"));//合同的【初审日期】

		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return cont;
	}
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema,LCContSchema tLCContSchema){
		BOMInsured insured= new  BOMInsured();
		try{
			insured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			ExeSQL tExeSQL2 = new ExeSQL();			
			
			if(!((tLCInsuredSchema.getBirthday()==null))||"".equals(tLCInsuredSchema.getBirthday())){
					theDate=tLCInsuredSchema.getBirthday()+" 00:00:00";
					insured.setBirthday(sdf.parse(theDate));
			}

			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			//被保人投保年龄
			insured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));
			if(!((tLCInsuredSchema.getJoinCompanyDate()==null))||"".equals(tLCInsuredSchema.getJoinCompanyDate())){
				theDate=tLCInsuredSchema.getJoinCompanyDate()+" 00:00:00";
				insured.setJoinCompanyDate(sdf.parse(theDate));
			}
			//驾照
			insured.setLicense(tLCInsuredSchema.getLicense());
			//驾照类型
			insured.setLicenseType(tLCInsuredSchema.getLicenseType());
			//婚姻状况
			insured.setMarriage(tLCInsuredSchema.getMarriage());
			//婚姻日期
			if(!(tLCInsuredSchema.getMarriageDate()==null||"".equals(tLCInsuredSchema.getMarriageDate()))){
				theDate=tLCInsuredSchema.getMarriageDate()+" 00:00:00";
				insured.setMarriageDate(sdf.parse(theDate));
			}
			if(!(ISPlanRiskFlag==null||"".equals(ISPlanRiskFlag))){
				if (ISPlanRiskFlag.equals("1")) {
					insured.setContPlanCode(xLCInsuredSchema.getContPlanCode());
				}
			}
			insured.setCreditGrade(tLCInsuredSchema.getCreditGrade());
			insured.setDegree(tLCInsuredSchema.getDegree());
			//民族
			insured.setNationality(tLCInsuredSchema.getNationality());
			//国籍
			insured.setNativePlace(tLCInsuredSchema.getNativePlace());
			//职位
			insured.setPosition(tLCInsuredSchema.getPosition());
			//职业类型
			insured.setOccupationType(tLCInsuredSchema.getOccupationType());
			//职业代码
			insured.setOccupationCode(tLCInsuredSchema.getOccupationCode());
			//与投保人关系
			insured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			//户口所在地
			insured.setRgtAddress(tLCInsuredSchema.getRgtAddress());
			//工资
			insured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			//性别
			insured.setSex(tLCInsuredSchema.getSex());
//			if(ISPlanRiskFlag)
//			{
//				insured.setContPlanCode(ContPlanCode);
//			}
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMInsured出错！");
			e.printStackTrace();
		}
		return insured;
	}
	
	
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema,BOMInsured insureds,LCDutySchema tLCDutySchema){
		BOMPol pol = new BOMPol();//多个险种
		try{
			ExeSQL tempExeSQL = new ExeSQL();
			boolean FreeFlag = false;
			BOMInsured tCurrentInsured = new BOMInsured();
			BOMInsured insured = new BOMInsured();
			insured=insureds;
			if(tLCPolSchema.getRiskCode().equals("121301"))
			{
				if(insured.getInsuredNo().equals(tLCPolSchema.getAppntNo())){
					pol.setFatherBOM(insured);
					tCurrentInsured = insured;
					FreeFlag = true;
	
				}
			}
			else if(insured.getInsuredNo().equals(tLCPolSchema.getInsuredNo())){
				pol.setFatherBOM(insured);
				tCurrentInsured = insured;
			}
			if(!(ISPlanRiskFlag==null||"".equals(ISPlanRiskFlag))){
					if (ISPlanRiskFlag.equals("1")) {
				// 对于产品组合要将份数设为传入的产品组合份数
				// 产品组合如果某险种有多个责任那么lcpol中的份数是多个责任的份数的和
				// 所以要传入产品组合的份数
						String tMult = "";
						tMult = (String) mTransferData.getValueByName("Mult");
						if (tMult != null && !"".equals(tMult)) {
							pol.setMult(Double.parseDouble(tMult));
						}
					}
			}
			
			pol.setPeakLine(tLCDutySchema.getPeakLine());
			// logger.debug("保单类型为："+mLCPolBL.getPolTypeFlag());
			pol.setPolTypeFlag(tLCPolSchema.getPolTypeFlag());
			pol.setGetLimit(tLCDutySchema.getGetLimit());
			
			pol.setUWFlag(tLCPolSchema.getUWFlag());
			//终止日期
			//终止日期
			if(!((tEndDate==null)||"".equals(tEndDate))){
				tEndDate = tEndDate+" "+" 00:00:00";
				pol.setEndDate(sdf.parse(tEndDate));//责任终止日期
			}
			//终止日期
			pol.setAppFlag(tLCPolSchema.getAppFlag());//责任终止日期
			//总基本保额	
			pol.setAmnt(Double.valueOf(String.valueOf(tLCPolSchema.getAmnt())));
			//红利金领取方式
			pol.setBonusGetMode(tLCPolSchema.getBonusGetMode());
			//红利领取人类型
			pol.setBonusManType(tLCPolSchema.getBonusMan());
			//险种生效日期
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				pol.setCValiDate(sdf.parse(theDate));
			}
			
			//浮动费率
			pol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
			//被保人号码
			pol.setInsuredNo(tLCPolSchema.getInsuredNo());
			//保险期间
			pol.setInsuYear(Double.valueOf(String.valueOf(tLCPolSchema.getInsuYear())));
			//交费期间
			pol.setPayYears(new Double(tLCPolSchema.getPayYears()));
			//保险期间单位
			pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			//给付间隔
			pol.setGetIntv(new Double(tLCDutySchema.getGetIntv())); // 2006-4-19
			//终交年龄年期
			pol.setPayEndYear(new Double(tLCPolSchema.getPayEndYear()));
			//终交年龄年期标志
			pol.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
			//起领期间
			pol.setGetYear(new Double(tLCPolSchema.getGetYear()));
			//起领期间单位
			pol.setGetYearFlag(tLCPolSchema.getGetYearFlag());
			//生存金领取方式
			pol.setLiveGetMode(tLCPolSchema.getLiveGetMode());
			//主险号码
			pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			//总份数
			pol.setMult(new Double(tLCPolSchema.getMult()));

			//险种号码
			pol.setPolNo(tLCPolSchema.getPolNo());
			//主险号
			pol.setMainPolNo(tLCPolSchema.getMainPolNo());
			//保费
			pol.setPrem(new Double(tLCPolSchema.getPrem()));
			//险种编码
			pol.setRiskCode(tLCPolSchema.getRiskCode());
			//停售标记
			pol.setStopFlag(tLCPolSchema.getStopFlag());

			//单位保额
			LMDutySchema tLMDutySchema = mCRI.findDutyByDutyCodeClone(tLCDutySchema
					.getDutyCode().substring(0, 6));
			if (tLMDutySchema == null) {
				// @@错误处理
				this.mErrors.copyAllErrors(mCRI.mErrors);
				mCRI.mErrors.clearErrors();

				CError.buildErr(this, "LMDuty表查询失败!");
			}
			
			pol.setVPU(new Double(tLMDutySchema.getVPU()));
			//币别
			pol.setCurrency(tLCPolSchema.getCurrency());
			//备用1
			pol.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());
			//备用2
			pol.setStandbyFlag2(tLCPolSchema.getStandbyFlag2());
			//备用3
			pol.setStandbyFlag3(tLCPolSchema.getStandbyFlag3());
			
			pol.setGetDutyKind(GetDutyKind);
			if(!(ISPlanRiskFlag==null||"".equals(ISPlanRiskFlag))){
				if (ISPlanRiskFlag.equals("1")) 
				{
						pol.setMult(new Double(Mult));
				}
			}
	
	
	}catch(Exception e){
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}
		return pol;
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

	private double getStandPrem(double Prem,double FlooRate)
	{

		 if(Prem <= 200)
		     return 200;
		 else if(Prem <= 400 && Prem>200)
			 return 400;
		 else if (Prem <= 800 && Prem>400)
		  	 return 800;
		 
		 return 0;
	}

	private BOMAppnt DealBOMAppnt(LCAppntSchema tLCAppntSchema,LCContSchema tLCContSchema){
		BOMAppnt appnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			int tAppAge = PubFun.calInterval2(tLCAppntSchema.getAppntBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			appnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if(!((tLCAppntSchema.getAppntBirthday()==null))||"".equals(tLCAppntSchema.getAppntBirthday())){
				theDate=tLCAppntSchema.getAppntBirthday()+" 00:00:00";
				appnt.setAppntBirthday(sdf.parse(theDate));
			}
			appnt.setAppntName(tLCAppntSchema.getAppntName());
			appnt.setAppntNo(tLCAppntSchema.getAppntNo());
			appnt.setAppntSex(tLCAppntSchema.getAppntSex());
			//update by cxq 修改绑定变量
			sqlbv = new SQLwithBindVariables();
			String tBlackList="select CASE WHEN BlacklistFlag IS NOT NULL THEN BlacklistFlag ELSE '0' END from ldperson where customerno='?AppntNo?'";
			sqlbv.sql(tBlackList);
			sqlbv.put("AppntNo", tLCAppntSchema.getAppntNo());
			String tBlackFlag = tempExeSQL.getOneValue(sqlbv);
			
			appnt.setBlackListFlag(tBlackFlag);//黑名单标记
			appnt.setCreditGrade(tLCAppntSchema.getCreditGrade());
			appnt.setDegree(tLCAppntSchema.getDegree());			


			if(!((tLCAppntSchema.getJoinCompanyDate()==null)||"".equals(tLCAppntSchema.getJoinCompanyDate()))){
				theDate=tLCAppntSchema.getJoinCompanyDate()+" 00:00:00";
				appnt.setJoinCompanyDate(sdf.parse(theDate));
			}
			appnt.setMarriage(tLCAppntSchema.getMarriage());
			if(!((tLCAppntSchema.getMarriageDate()==null))||"".equals(tLCAppntSchema.getMarriageDate())){
				theDate=tLCAppntSchema.getMarriageDate()+" 00:00:00";
				appnt.setMarriageDate(sdf.parse(theDate));
			}
			appnt.setNationality(tLCAppntSchema.getNationality());
			appnt.setNativePlace(tLCAppntSchema.getNativePlace());
			appnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			appnt.setOccupationType(tLCAppntSchema.getOccupationType());
			appnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			appnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			if(!((tLCAppntSchema.getStartWorkDate()==null))||"".equals(tLCAppntSchema.getStartWorkDate())){
				theDate=tLCAppntSchema.getStartWorkDate()+" 00:00:00";
				appnt.setStartWorkDate(sdf.parse(theDate));
			}
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return appnt;
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
}