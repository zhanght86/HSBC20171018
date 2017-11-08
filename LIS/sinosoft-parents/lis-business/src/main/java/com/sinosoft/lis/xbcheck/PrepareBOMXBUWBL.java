package com.sinosoft.lis.xbcheck;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCIssuePolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDOccupationDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMDutySchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.lis.tb.GlobalCheckSpot;
import com.sinosoft.lis.tb.ProductSaleControlBL;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>
 * Title: 续保自核规则引擎
 * </p>
 * 
 * <p>
 * Description: 续保自核规则引擎
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
public class PrepareBOMXBUWBL {
private static Logger logger = Logger.getLogger(PrepareBOMXBUWBL.class);
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
	Reflections tRef = new Reflections();
	private List mBomList = new ArrayList();
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	/** 险种表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	/** 公共变量 */
	private String mContNo = "";
	private String tRiskCode="";
	private String mEdorNo="";
	/** 保单险种被保人数据 */
	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCInsuredSet mAllInsuredSet = new LCInsuredSet();
	private String tEndDate= PubFun.getCurrentDate();
	private int insuredCount=0;
	private String mDutyCode="";
	/** 发送拒保通知书 */
	private String locCurrency = "";//本币信息
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	
	BOMCont cont = new BOMCont();
	BOMInsured insured = new  BOMInsured();
	BOMAppnt appnt = new  BOMAppnt();
	BOMPol pol = new BOMPol();
	
	public PrepareBOMXBUWBL() {
	}


	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param tLCContSchema
	 *            输入数据
	 * @return boolean
	 */
	public List dealData(LCPolSchema tLCPolSchema) {
		
		/** 初始化把新契约数据放入保全BOM词条数据中*/
//		保单数据
		LCContDB tLCContDB = new LCContDB();
		mContNo = tLCPolSchema.getContNo(); // 获得保单号
		tLCContDB.setContNo(tLCPolSchema.getContNo());
		LCContSchema tLCContSchema = tLCContDB.query().get(1);
		//赋给全局变量
		mLCContSchema=tLCContSchema;
		tEndDate=tLCPolSchema.getEndDate();

		//被保人信息
		LCInsuredDB tFreeLCInsuredDB = new LCInsuredDB();
		tFreeLCInsuredDB.setContNo(mContNo);
		insuredCount= tFreeLCInsuredDB.query().size();
		tFreeLCInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
		LCInsuredSchema tFreeLCInsuredSchema=tFreeLCInsuredDB.query().get(1);
		//投保人信息
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mContNo);
		if(!tLCAppntDB.getInfo()){
			CError.buildErr(this, "查询投保人信息失败！");
		}
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setContNo(mContNo);
		tLCBnfDB.setPolNo(tLCPolSchema.getPolNo());
		tLCBnfDB.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLCBnfSet = tLCBnfDB.query();
		//准备BOMCont数据
		if(tLCContSchema!=null && !tLCContSchema.equals(""))
		{
			cont = DealBOMCont(tLCContSchema);
			mBomList.add(cont);
		}
		
		//准备被保人BOMInsured数据

		if(tLCPolSchema!=null)
		{	
			insured=DealBOMInsured(tFreeLCInsuredSchema,tLCContSchema);
			mBomList.add(insured);

		    //准备投保人BOMAppnt数据
	
			appnt = DealBOMAppnt(tLCAppntDB.getSchema(),tLCContSchema);
			mBomList.add(appnt);

			pol=DealBOMPol(tLCPolSchema,insured);
			mBomList.add(pol);

			BOMBnf bnf = new BOMBnf();//多个受益人
			bnf = DealBOMBnf(tLCBnfSet.get(1),insured);
		}
		return this.mBomList;
	}

	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont tcont = new BOMCont();
		try{

			//保单号码
			tcont.setContNo(tLCContSchema.getContNo());
			tcont.setAppFlag(mLCContSchema.getAppFlag());
			//保费
			tcont.setPrem(new Double(mLCContSchema.getPrem()));
			//份数
			tcont.setMult(new Double(mLCContSchema.getMult()));
			//保额
			tcont.setAmnt(Double.valueOf(tLCContSchema.getAmnt()));
			//自动垫交标记
			tcont.setAutoPayFlag(tLCContSchema.getAutoPayFlag());
			//银行账号
			tcont.setBankAccNo(tLCContSchema.getBankAccNo());
			//帐户名
			tcont.setAccName(tLCContSchema.getAccName());
			//开户行
			tcont.setBankCode(tLCContSchema.getBankCode());
			//卡单标记
			tcont.setCardFlag(tLCContSchema.getCardFlag());
			tcont.setSellType(tLCContSchema.getSellType());//出单方式
			//终止日期
			if(!((tEndDate==null)||"".equals(tEndDate))){
				tEndDate = tEndDate+" "+" 00:00:00";
				tcont.setEndDate(sdf.parse(tEndDate));//责任终止日期
			}
			if(!(tLCContSchema.getCValiDate()==null||"".equals(tLCContSchema.getCValiDate()))){
				theDate=tLCContSchema.getCValiDate()+" 00:00:00";
				tcont.setCvalidate(sdf.parse(theDate));
			}
			//被保人数目
			tcont.setInsuredPeoples(Double.valueOf(String.valueOf(insuredCount)));
			if(!((tLCContSchema.getMakeDate()==null)||"".equals(tLCContSchema.getMakeDate()))){
				theDate = tLCContSchema.getMakeDate()+" "+tLCContSchema.getMakeTime();
				tcont.setMakeDate(sdf.parse(theDate));
			}
			//管理机构
			tcont.setManageCom(tLCContSchema.getManageCom());
			//需要考虑加在程序里的函数。
			String tPayIntv=String.valueOf(tLCContSchema.getPayIntv());
			//交费间隔
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				tcont.setPayIntv("0");
			}
			else
			{
				tcont.setPayIntv(tPayIntv);
			}
			String tNewPayMode = tLCContSchema.getNewPayMode();
			String tPayMode = tLCContSchema.getPayLocation();
			String tAccName = "";
			if(tNewPayMode!=null&&tNewPayMode.equals("0")){
				tcont.setPayMode("0");
				tAccName = tLCContSchema.getNewAccName();
			}else if(tPayMode!=null&&tPayMode.equals("0")) {
				tcont.setPayMode("0");
				 tAccName = tLCContSchema.getAccName();
			}
			else
			{
				tcont.setPayMode(tLCContSchema.getPayMode());
			}
			if(!((tLCContSchema.getPolApplyDate()==null)||"".equals(tLCContSchema.getPolApplyDate()))){
				theDate=tLCContSchema.getPolApplyDate()+" 00:00:00";
				tcont.setPolApplyDate(sdf.parse(theDate));
				logger.debug(sdf.parse(theDate));
			}
			if(!(tLCContSchema.getRemark()==null||"".equals(tLCContSchema.getRemark()))){
				tcont.setRemarkFlag("1");
			}else{
				tcont.setRemarkFlag("0");
			}
			//自动续保标记
			tcont.setRnewFlag(String.valueOf(tLCContSchema.getRnewFlag()));
			//销售渠道
			tcont.setSaleChnl(tLCContSchema.getSaleChnl());
			tcont.setSecondaryManagecom(getManagetCom(tLCContSchema.getManageCom(),4));//二级机构
			tcont.setThirdStageManagecom(getManagetCom(tLCContSchema.getManageCom(),6));//三级机构
			tcont.setFourStageManagecom(getManagetCom(tLCContSchema.getManageCom(),8));//四级机构
			tcont.setFirstTrialDate(sdf.parse(tLCContSchema.getFirstTrialDate()+ " "+ "00:00:00"));//合同的【初审日期】
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return tcont;
	}
	
	private BOMInsured DealBOMInsured(LCInsuredSchema tLCInsuredSchema,LCContSchema tLCContSchema){
		BOMInsured tinsured= new  BOMInsured();
		try{
			tinsured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			ExeSQL tExeSQL2 = new ExeSQL();			
			
			if(!((tLCInsuredSchema.getBirthday()==null))||"".equals(tLCInsuredSchema.getBirthday())){
					theDate=tLCInsuredSchema.getBirthday()+" 00:00:00";
					tinsured.setBirthday(sdf.parse(theDate));
			}

			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			//被保人投保年龄
			tinsured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));
			if(!((tLCInsuredSchema.getJoinCompanyDate()==null))||"".equals(tLCInsuredSchema.getJoinCompanyDate())){
				theDate=tLCInsuredSchema.getJoinCompanyDate()+" 00:00:00";
				tinsured.setJoinCompanyDate(sdf.parse(theDate));
			}
			//驾照
			tinsured.setLicense(tLCInsuredSchema.getLicense());
			//驾照类型
			tinsured.setLicenseType(tLCInsuredSchema.getLicenseType());
			//婚姻状况
			tinsured.setMarriage(tLCInsuredSchema.getMarriage());
			//婚姻日期
			if(!(tLCInsuredSchema.getMarriageDate()==null||"".equals(tLCInsuredSchema.getMarriageDate()))){
				theDate=tLCInsuredSchema.getMarriageDate()+" 00:00:00";
				tinsured.setMarriageDate(sdf.parse(theDate));
			}
			
			tinsured.setCreditGrade(tLCInsuredSchema.getCreditGrade());
			
			tinsured.setDegree(tLCInsuredSchema.getDegree());
			//民族
			tinsured.setNationality(tLCInsuredSchema.getNationality());
			//国籍
			tinsured.setNativePlace(tLCInsuredSchema.getNativePlace());
			//职位
			tinsured.setPosition(tLCInsuredSchema.getPosition());
			//职业类型
			tinsured.setOccupationType(tLCInsuredSchema.getOccupationType());
			//职业代码
			tinsured.setOccupationCode(tLCInsuredSchema.getOccupationCode());
			//与投保人关系
			tinsured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			//户口所在地
			tinsured.setRgtAddress(tLCInsuredSchema.getRgtAddress());
			//工资
			tinsured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			//性别
			tinsured.setSex(tLCInsuredSchema.getSex());

		}catch(Exception e){
			CError.buildErr(this, "准备BOMInsured出错！");
			e.printStackTrace();
		}
		return tinsured;
	}
	
	
	private BOMPol DealBOMPol(LCPolSchema tLCPolSchema,BOMInsured insureds){
		try{
			ExeSQL tempExeSQL = new ExeSQL();
			boolean FreeFlag = false;
			BOMInsured tCurrentInsured = new BOMInsured();
			BOMInsured insured = new BOMInsured();
			insured=insureds;
			if(insured.getInsuredNo().equals(tLCPolSchema.getInsuredNo())){
				pol.setFatherBOM(insured);
				tCurrentInsured = insured;
			}
				
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
			pol.setYears(new Double(tLCPolSchema.getYears()));
			//保险期间单位
			pol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			//终交年龄年期
			pol.setPayEndYear(new Double(tLCPolSchema.getPayEndYear()));
			//终交年龄年期标志
			pol.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
			pol.setPolState(tLCPolSchema.getPolState());
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
			pol.setInsuredPeoples(new Double(tLCPolSchema.getInsuredPeoples()));
			//保费
			pol.setPrem(new Double(tLCPolSchema.getPrem()));
			//险种编码
			pol.setRiskCode(tLCPolSchema.getRiskCode());
			pol.setAppFlag(tLCPolSchema.getAppFlag());
			//停售标记
			pol.setStopFlag(tLCPolSchema.getStopFlag());
			//给付代码
//			pol.setGetDutyCode(tLCDutySchema.getGetDutyCode());
			//币别
			pol.setCurrency(tLCPolSchema.getCurrency());
			//备用1
			pol.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());
			//备用2
			pol.setStandbyFlag2(tLCPolSchema.getStandbyFlag2());
			//备用3
			pol.setStandbyFlag3(tLCPolSchema.getStandbyFlag3());
			String tProposalNo = tLCPolSchema.getProposalNo();
			pol.setOldPolNo(tLCPolSchema.getPolNo());
			  //查找原险种记录
			LCPolDB xxLCPolDB = new LCPolDB();
			xxLCPolDB.setContNo(tLCPolSchema.getContNo());
			xxLCPolDB.setRiskCode(tLCPolSchema.getRiskCode());
			xxLCPolDB.setAppFlag("1");
			if(xxLCPolDB.query().size()==0)
			{
				logger.debug("查找保单号下"+tLCPolSchema.getContNo()+"原险种记录"+tLCPolSchema.getRiskCode()+"出错！");
			}
			
			  //由于自核规则有上一年度的限制，所以取时间范围
			 String mStartDate = xxLCPolDB.query().get(1).getCValiDate(); //自核规则中会用到的起始时间
			 pol.setStartDate(sdf.parse(mStartDate));
			 String mEndDate = xxLCPolDB.query().get(1).getPaytoDate(); 
			 pol.setEndDate(sdf.parse(mEndDate));
			 String claimToXBFlag="select count(*) from ldsystrace where polstate='4002' and polno in (select polno from lcpol where insuredno =(select InsuredNo from lcpol where polno='?polno?' and appflag='9' ))";
			 SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			 sqlbv.sql(claimToXBFlag);
			 sqlbv.put("polno", tLCPolSchema.getPolNo());
			 ExeSQL tExeSQL = new ExeSQL();
			 SSRS tSSRS = tExeSQL.execSQL(sqlbv);
			 if(Integer.parseInt(tSSRS.GetText(1, 1))>0)
			 {
				 pol.setClaimToXBFlag("1");
			 }
			 else
			 {
				 pol.setClaimToXBFlag("0");
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
		BOMAppnt tappnt = new BOMAppnt();
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			int tAppAge = PubFun.calInterval2(tLCAppntSchema.getAppntBirthday(), tLCContSchema.getPolApplyDate(), "Y");
			tappnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if(!((tLCAppntSchema.getAppntBirthday()==null))||"".equals(tLCAppntSchema.getAppntBirthday())){
				theDate=tLCAppntSchema.getAppntBirthday()+" 00:00:00";
				tappnt.setAppntBirthday(sdf.parse(theDate));
			}
			tappnt.setAppntName(tLCAppntSchema.getAppntName());
			tappnt.setAppntNo(tLCAppntSchema.getAppntNo());
			tappnt.setAppntSex(tLCAppntSchema.getAppntSex());			
			String tBlackList="select (case when BlacklistFlag is not null then BlacklistFlag else '0' end) from ldperson where customerno='?customerno?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tBlackList);
			sqlbv1.put("customerno", tLCAppntSchema.getAppntNo());
			String tBlackFlag = tempExeSQL.getOneValue(sqlbv1);
			tappnt.setBlackListFlag(tBlackFlag);//黑名单标记
			tappnt.setCreditGrade(tLCAppntSchema.getCreditGrade());
			tappnt.setDegree(tLCAppntSchema.getDegree());			


			if(!((tLCAppntSchema.getJoinCompanyDate()==null)||"".equals(tLCAppntSchema.getJoinCompanyDate()))){
				theDate=tLCAppntSchema.getJoinCompanyDate()+" 00:00:00";
				tappnt.setJoinCompanyDate(sdf.parse(theDate));
			}
			tappnt.setMarriage(tLCAppntSchema.getMarriage());
			if(!((tLCAppntSchema.getMarriageDate()==null))||"".equals(tLCAppntSchema.getMarriageDate())){
				theDate=tLCAppntSchema.getMarriageDate()+" 00:00:00";
				tappnt.setMarriageDate(sdf.parse(theDate));
			}
			tappnt.setNationality(tLCAppntSchema.getNationality());
			tappnt.setNativePlace(tLCAppntSchema.getNativePlace());
			tappnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			tappnt.setOccupationType(tLCAppntSchema.getOccupationType());
			tappnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			tappnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			if(!((tLCAppntSchema.getStartWorkDate()==null))||"".equals(tLCAppntSchema.getStartWorkDate())){
				theDate=tLCAppntSchema.getStartWorkDate()+" 00:00:00";
				tappnt.setStartWorkDate(sdf.parse(theDate));
			}
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return tappnt;
	}
	

	private BOMBnf DealBOMBnf(LCBnfSchema tLCBnfSchema,BOMInsured insured){
		BOMBnf bnf = new BOMBnf();//多个受益人
		try{

				if(!(tLCBnfSchema.getBirthday()==null||"".equals(tLCBnfSchema.getBirthday()))){
					theDate=tLCBnfSchema.getBirthday()+" 00:00:00";
					bnf.setBirthday(sdf.parse(theDate));
				}
				bnf.setBnfGrade(tLCBnfSchema.getBnfGrade());
				bnf.setBnfLot(new Double(tLCBnfSchema.getBnfLot()));
				bnf.setBnfType(tLCBnfSchema.getBnfType());
				bnf.setBnfName(tLCBnfSchema.getName());
				bnf.setCustomerNo(tLCBnfSchema.getCustomerNo());
				bnf.setInsuredNo(tLCBnfSchema.getInsuredNo());

				insured=insured;
				if(insured.getInsuredNo().equals(bnf.getInsuredNo())){
					bnf.setFatherBOM(insured);
				}
				bnf.setRelationToInsured(tLCBnfSchema.getRelationToInsured());
				bnf.setSex(tLCBnfSchema.getSex());

		}catch(Exception e){
			CError.buildErr(this, "准备BOMBnf时出错！");
			e.printStackTrace();
		}
		return bnf;
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

	// ====add====zhangtao====2006-08-03===账户价值计算==============END=================

	/**
	 * 根据传入的保单结算时点计算退保点
	 * 
	 * @param sCalDate
	 * @return String
	 */
	private String getZTPoint(LCPolSchema pLCPolSchema, String sCalDate) {
		if (mLCPolSchema.getAppFlag().trim().equals("4")) {
			String sql = "select min(startdate) from ( "
					+ " select startdate from lccontstate where statetype = 'Terminate' and state = '1' "
					+ "  and enddate is null and polno = '?polno?' " + " union select to_date('?sCalDate?','YYYY-MM-DD') from dual where 1=1 ) g";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql);
			sqlbv2.put("polno", mLCPolSchema.getPolNo());
			sqlbv2.put("sCalDate", sCalDate);
			ExeSQL tExeSQL = new ExeSQL();
			sCalDate = tExeSQL.getOneValue(sqlbv2);
			if (tExeSQL.mErrors.needDealError()) {
				CError.buildErr(this, "保单终止日期查询失败!");
				return null;
			}
			if (sCalDate == null || sCalDate.equals("")) {
				CError.buildErr(this, "保单终止日期查询失败!");
				return null;
			}
			if (sCalDate.length() > 10) {
				sCalDate = sCalDate.substring(0, 10);
			}
		} // 到此，sCalDate 为退保时点与终止时点较早者

		String sZTPoint = "";

		if (pLCPolSchema.getPayIntv() == 0 || pLCPolSchema.getPayIntv() == -1) {
			return sCalDate; // 趸交、不定期交
		}
		int intv = PubFun.calInterval(pLCPolSchema.getPayEndDate(),
				pLCPolSchema.getPaytoDate(), "D");
		if (intv >= 0) // 交费年期已满
		{
			return sCalDate;
		}
		// 判断保单是否失效
		String sql = " select count(*) from lccontstate "
				+ " where statetype in('Available')  "
				+ " and state = '1' and " + " ( (startdate <= '?sCalDate?' and enddate >= '?sCalDate?' ) or (startdate <= '?sCalDate?' and enddate is null ))  " + " and polno = '?polno?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("sCalDate", sCalDate);
		sqlbv3.put("polno", pLCPolSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		String sdisAvailable = tExeSQL.getOneValue(sqlbv3);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "查询保单状态失败!");
			return null;
		}
		if (sdisAvailable == null || sdisAvailable.trim().equals("")) {
			CError.buildErr(this, "查询保单状态失败!");
			return null;
		}
		if (sdisAvailable.equals("0")) // 保单有效
		{
			// 判断是否交费
			String sPayNextFlag = getPayNextFlag(pLCPolSchema, sCalDate);
			if (sPayNextFlag == null) {
				return null;
			}
			if (sPayNextFlag.equals("1")) // 1-应交已交
			{
				sZTPoint = sCalDate;
			} else if (sPayNextFlag.equals("0")) // 0-应交未交
			{
				// 宽限期起期（交费对应日次日）前一日
				// modify by jiaqiangli 2008-12-18 交至日期前一天
				sZTPoint = PubFun.calDate(pLCPolSchema.getPaytoDate(), -1, "D",
						null);
			}
		} else // 保单失效
		{
			// 279险种特殊处理
			String is279 = isRisk(mLCPolSchema.getRiskCode(), "279");
			if (is279 == null) {
				return null;
			} else if (is279.equals("Y")) {
				// 退保计算时点应该为申请时点
				sZTPoint = sCalDate;
				// sZTPoint = CalLapseDate(mLCPolSchema.getRiskCode(),
				// pLCPolSchema.getPaytoDate());
				// if (sZTPoint == null)
				// {
				// return null;
				// }
			} else {
				// 宽限期起期（交费对应日次日）前一日
				// modify by jiaqiangli 2008-12-18 交至日期前一天
				sZTPoint = PubFun.calDate(pLCPolSchema.getPaytoDate(), -1, "D",
						null);

				intv = PubFun.calInterval(sCalDate, sZTPoint, "D");
				if (intv >= 0) // 交至比计算时点还晚，直接返回计算时点（失效后又做复效的情况）
				{
					return sCalDate;
				}
			}
		}

		// comment by jiaqiangli 2008-12-18 退保生效日规则点总结
		// 保险期内趸交或交费期满 ztpoint=edorappdate
		// 保险期内期交且未交费满期 ztpoint=paytodate-1(约进算法)
		// comment by jiaqiangli 2008-12-18 退保生效日规则点总结

		logger.debug("== 现金价值计算时点：" + sZTPoint);
		return sZTPoint;
	}
	/**
	 * 判断当期保费是否已交 1-应交已交 0-应交未交
	 * 
	 * @param pLCPolSchema
	 *            保单信息
	 * @return String
	 */
	private String getPayNextFlag(LCPolSchema pLCPolSchema, String sCalDate) {
		// 暂时只是判断交至日期与退保点的关系-- 以及是否交费期满
		// 疑问：是否要去判断应收实收和暂交费？ 如果有暂交费没有核销算不算已交 zhangtao 2005-07-11
		// 确认：暂交费未核销的不算已交
		int intv = PubFun.calInterval(pLCPolSchema.getPayEndDate(),
				pLCPolSchema.getPaytoDate(), "D");
		if (intv >= 0) // 交费年期已满
		{
			return "2";
		}
		String sPayNextFlag = "";
		if (pLCPolSchema.getPaytoDate() == null
				|| pLCPolSchema.getPaytoDate().equals("")) {
			CError.buildErr(this, "保单交费对应日为空!");
			return null;
		}

		if (pLCPolSchema.getPayIntv() == 0) // 趸交
		{
			sPayNextFlag = "2"; // 1-应交已交
			return sPayNextFlag;
		}

		int intval = PubFun.calInterval(sCalDate, pLCPolSchema.getPaytoDate(),
				"D");

		if (intval > 0) {
			sPayNextFlag = "1"; // 1-应交已交
		} else {
			sPayNextFlag = "0"; // 0-应交未交
		}

		return sPayNextFlag;
	}
	/**
	 * 判断险种是否是*险
	 * 
	 * @return String
	 */
	private String isRisk(String sRiskCode, String sRisk) {
		ExeSQL tExeSQL = new ExeSQL();
		String sql = " select 1 from dual where '?sRiskCode?' in (select trim(riskcode) from lmrisk where trim(riskcode) like concat(concat('%00','?sRisk?'),'%')) ";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("sRiskCode", sRiskCode);
		sqlbv4.put("sRisk", sRisk);
		String sResult = tExeSQL.getOneValue(sqlbv4);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "险种定义查询失败!");
			return null;
		}
		if (sResult != null && sResult.equals("1")) {
			return "Y";
		} else {
			return "N";
		}
	}

}
