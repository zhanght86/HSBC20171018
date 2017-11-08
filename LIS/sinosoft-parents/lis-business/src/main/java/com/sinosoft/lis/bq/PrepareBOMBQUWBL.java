package com.sinosoft.lis.bq;
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

//import com.ibm.db2.jcc.c.w;
import com.sinosoft.ibrms.RuleUI;
import com.sinosoft.ibrms.SQLTaskResult;
import com.sinosoft.ibrms.bom.BOMAgent;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMBnf;
import com.sinosoft.ibrms.bom.BOMBqAppnt;
import com.sinosoft.ibrms.bom.BOMBqBnf;
import com.sinosoft.ibrms.bom.BOMBqCont;
import com.sinosoft.ibrms.bom.BOMBqInsured;
import com.sinosoft.ibrms.bom.BOMBqPol;
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
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPContSchema;
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
import com.sinosoft.lis.vschema.LPEdorItemSet;
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
 * Title: 保全项目自核-投保计算规则引擎
 * </p>
 * 
 * <p>
 * Description: 保全项目明细-自核-投保计算规则引擎
 * 结论：为什么不写成传递LCContSchema的规则处理类,然后在类中计算得到词条值，这样非隅合性比较好？
 * 		1、保全BOM词条非常多，得到词条的程序特别复杂，如EdorCalZT.JAVA
 *      2、同一个词条值在每个保全项目明细类中，不同的得到方式值方式，或在一个保全明细处理类中就有好几个得值方式。
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2012
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangfh
 * @version 6.0
 */
public class PrepareBOMBQUWBL {
private static Logger logger = Logger.getLogger(PrepareBOMBQEdorBL.class);
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
	BqCalBase mBqCalBase=new BqCalBase();
	Reflections tRef = new Reflections();
	String tCurrentDate = PubFun.getCurrentDate();
	private List mBomList = new ArrayList();
	/** 保单表 */
	private LCContSchema mLCContSchema = new LCContSchema();
	/** 险种表 */
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCInsuredSchema mLCInsuredSchema=new LCInsuredSchema();
	/** 公共变量 */
	private String mContNo = "";
	private String tRiskCode="";
	private String mEdorNo="";
	private String mEdorAcceptNo="";
	/** 保单险种被保人数据 */
	private LCContSet mAllLCContSet = new LCContSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LPEdorItemSchema mLPEdorItemSchema=new LPEdorItemSchema();
	private LCInsuredSet mAllInsuredSet = new LCInsuredSet();
	/** 判断变量 */
	private Boolean initFlag=false;
	private Boolean initDuty=true;
	private Boolean newInitDuty=false;
	private String tEndDate= PubFun.getCurrentDate();
	private int insuredCount=0;
	private String mDutyCode="";
	/** 发送拒保通知书 */
	private String locCurrency = "";//本币信息
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();
	/**保单级BOM变量*/
	BOMCont cont = new BOMCont();
	BOMInsured insured = new  BOMInsured();
	BOMAppnt appnt = new  BOMAppnt();
	BOMPol pol = new BOMPol();
	/**保全级BOM变量*/
	BOMBqCont bqcont = new BOMBqCont();
	BOMBqInsured bqinsured= new  BOMBqInsured();
	BOMBqPol bqpol = new BOMBqPol();
	BOMBqAppnt bqappnt = new  BOMBqAppnt();
	BOMBqBnf bqbnf = new  BOMBqBnf();
	
	public PrepareBOMBQUWBL() {
	}


	/**
	 * 数据操作类业务处理 输出：List BOM词条数据
	 * 
	 * @param 
	 *            
	 * @return List
	 */
	public List dealData(LPEdorItemSchema tLPEdorItemSchema) {
		
		mLPEdorItemSchema=tLPEdorItemSchema;
		
		/** 传递数据时有POLNO,ContNo数据执行准备Bom数据**/
		
		dealContPol(tLPEdorItemSchema);
		
		/**返回结果集**/
		return this.mBomList;
	}
	
	
	private void dealContPol(LPEdorItemSchema tLPEdorItemSchema)
	{
		/**险种级初始化新契约BOM和保全BOM数据，第二次初始化时不执行!*/
		InitBOM(tLPEdorItemSchema);	
		/**保全处理过程序中传入的数据*/
		try {
			//准备保全BOMCont数据
			DealBOMBqCont();
			if(mBomList.contains(bqcont))
			{
				mBomList.remove(bqcont);
			}
			mBomList.add(bqcont);
			
			//准备保全BOMPol数据
			DealBOMBqPol();
			if(mBomList.contains(bqpol))
			{
				mBomList.remove(bqpol);
			}
			mBomList.add(bqpol);
			
			//准备保全被保人BOMInsured数据
			DealBOMBqInsured();
			if(mBomList.contains(bqinsured))
			{
				mBomList.remove(bqinsured);
			}
			mBomList.add(bqinsured);
			
			//准备投保人BOMAppnt数据
			DealBOMBqAppnt();
			if(mBomList.contains(bqappnt))
			{
				mBomList.remove(bqappnt);
			}
			mBomList.add(bqappnt);
			
			//准备受益人BOMBqBnf数据
			DealBOMBqBnf();
			if(mBomList.contains(bqbnf))
			{
				mBomList.remove(bqbnf);
			}
			mBomList.add(bqbnf);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	/**初始化保单及保全数据*/
	private void InitBOM(LPEdorItemSchema tLPEdorItemSchema)
	{
		/**保单及险种数据,赋给全局变量*/		
		LCContDB tLCContDB = new LCContDB();
		mContNo = mBqCalBase.getContNo(); 
		tLCContDB.setContNo(mBqCalBase.getContNo());
		
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mBqCalBase.getPolNo());
		
		LCPolSchema tLCPolSchema=new LCPolSchema();
		LCContSchema tLCContSchema=new LCContSchema();
		if(tLCContDB.getInfo()){
			tLCContSchema = tLCContDB.query().get(1);
		}
		if(tLCPolDB.getInfo())
		{
			tLCContDB.setContNo(mBqCalBase.getContNo());
			tLCPolDB.setPolNo(mBqCalBase.getPolNo());
			tLCPolSchema=tLCPolDB.query().get(1);
			tEndDate=tLCPolSchema.getEndDate();
			tLCContDB.setContNo(tLCPolSchema.getContNo());
			tLCContSchema = tLCContDB.query().get(1);
		}

		mLCContSchema=tLCContSchema;
		mLCPolSchema=tLCPolSchema;
		
		
		/**责任级**/
//		LCDutyDB tLCDutyDB= new LCDutyDB();
//		tLCDutyDB.setPolNo(mBqCalBase.getPolNo());
//		tLCDutyDB.setContNo(mBqCalBase.getContNo());
//		LCDutySchema tLCDutySchema;
//		if(mBqCalBase.getDutyCode()==null||mBqCalBase.getDutyCode().equals(""))
//		{
//			tLCDutySchema=new LCDutySchema();
//			/**是否要对责任进行初始化判断条件**/
//			initDuty=false;
//		}
//		else
//		{
//			tLCDutyDB.setDutyCode(mBqCalBase.getDutyCode());
//			tLCDutySchema=tLCDutyDB.query().get(1);
//			tEndDate=tLCDutySchema.getEndDate();
//			mDutyCode=tLCDutySchema.getDutyCode();
//		}

		/**被保人信息**/
		LCInsuredDB tFreeLCInsuredDB = new LCInsuredDB();
		tFreeLCInsuredDB.setContNo(mContNo);
			/**保单级被保人数**/
		insuredCount= tFreeLCInsuredDB.query().size();
		tFreeLCInsuredDB.setInsuredNo(tLCPolSchema.getInsuredNo());
		LCInsuredSchema tFreeLCInsuredSchema=tFreeLCInsuredDB.query().get(1);
		mLCInsuredSchema=tFreeLCInsuredSchema;
		
		/**投保人信息**/
		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mContNo);
		if(!tLCAppntDB.getInfo()){
			CError.buildErr(this, "查询投保人信息失败！");
		}
		
		/**受益人信息**/
		LCBnfSet tLCBnfSet = new LCBnfSet();
		LCBnfDB tLCBnfDB = new LCBnfDB();
		tLCBnfDB.setContNo(mContNo);
		tLCBnfDB.setPolNo(tLCPolSchema.getPolNo());
		tLCBnfDB.setInsuredNo(tLCPolSchema.getInsuredNo());
		tLCBnfSet = tLCBnfDB.query();
		
		mEdorAcceptNo=tLPEdorItemSchema.getEdorAcceptNo();
		mEdorNo=tLPEdorItemSchema.getEdorNo();
		
		/**准备BOMCont\BOMBqCont数据**/
		if(tLCContSchema!=null && !tLCContSchema.equals(""))
		{
			cont = DealBOMCont(tLCContSchema);
			mBomList.add(cont);
			bqcont = DealBOMBqCont(tLCContSchema);
		}

		if(tLCPolSchema!=null)
		{
			/**准备被保人BOMInsured\BOMBqInsured数据**/
			insured=DealBOMInsured(tFreeLCInsuredSchema,tLCContSchema);
			mBomList.add(insured);
			bqinsured = DealBOMBqInsured(tFreeLCInsuredSchema,tLCContSchema);

			/**准备被保人BOMAppnt\BOMBqAppnt数据**/
			appnt = DealBOMAppnt(tLCAppntDB.getSchema(),tLCContSchema);
			mBomList.add(appnt);
			/**准备被保人BOMBqAppnt数据**/
			bqappnt = DealBOMBqAppnt(tLCAppntDB.getSchema(),tLCContSchema);

			/**准备被保人BOMPol\BOMBqPol数据**/
			pol=DealBOMPol(tLCPolSchema,insured);
			mBomList.add(pol);
			//准备险种BOMPol数据
			DealBOMBqPol(tLCPolSchema,insured);
			
			if(tLCBnfSet.size()>0)
			{
				/**准备被保人BOMBnf\BOMBqBnf数据**/
				BOMBnf bnf = new BOMBnf();//多个受益人
				bnf = DealBOMBnf(tLCBnfSet.get(1),insured);
				BOMBqBnf bqbnf = new BOMBqBnf();//多个受益人
				bqbnf = DealBOMBqBnf(tLCBnfSet.get(1),bqinsured);
				mBomList.add(bnf);
			}
		}
		
		/**已初始化判断标记**/
		initFlag=true;
	}
	
	/***准备BOMCont数据*/
	private BOMCont DealBOMCont(LCContSchema tLCContSchema){
		BOMCont tcont = new BOMCont();
		try{

			//保单号码
			tcont.setContNo(tLCContSchema.getContNo());
			tcont.setAppFlag(tLCContSchema.getAppFlag());
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
			//出单方式
			tcont.setSellType(tLCContSchema.getSellType());
			//终止日期
			if(!((tEndDate==null)||"".equals(tEndDate))){
				tEndDate = tEndDate+" "+" 00:00:00";
				tcont.setEndDate(sdf.parse(tEndDate));//责任终止日期
			}
			//保单生效日期
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
			//收费方式及帐户名
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
			//投保日期
			if(!((tLCContSchema.getPolApplyDate()==null)||"".equals(tLCContSchema.getPolApplyDate()))){
				theDate=tLCContSchema.getPolApplyDate()+" 00:00:00";
				tcont.setPolApplyDate(sdf.parse(theDate));
				logger.debug(sdf.parse(theDate));
			}
			//保单备注
			if(!(tLCContSchema.getRemark()==null||"".equals(tLCContSchema.getRemark()))){
				tcont.setRemarkFlag("1");
			}else{
				tcont.setRemarkFlag("0");
			}
			//自动续保标记
			tcont.setRnewFlag(String.valueOf(tLCContSchema.getRnewFlag()));
			//销售渠道
			tcont.setSaleChnl(tLCContSchema.getSaleChnl());
			tcont.setSecondaryManagecom(getManagetCom(tLCContSchema.getManageCom(),4));
			//二级机构
			tcont.setThirdStageManagecom(getManagetCom(tLCContSchema.getManageCom(),6));
			//三级机构
			tcont.setFourStageManagecom(getManagetCom(tLCContSchema.getManageCom(),8));		
			//合同的【初审日期】
			tcont.setFirstTrialDate(sdf.parse(tLCContSchema.getFirstTrialDate()+ " "+ "00:00:00"));
		}catch(Exception e){
			CError.buildErr(this, "准备BOMCont时出错！");
			e.printStackTrace();
		}
		return tcont;
	}
	private void DealBOMBqCont(){
		try{
			//保全申请机构
			if(mBqCalBase.getManageCom() != null && !(mBqCalBase.getManageCom().equals("")))
				bqcont.setManageCom(mBqCalBase.getManageCom());
			//保全项目
			if(mBqCalBase.getEdorType() != null && !mBqCalBase.getEdorType().equals(""))
				bqcont.setEdorType(mBqCalBase.getEdorType());
			//保额
			if(Double.valueOf(mBqCalBase.getAmnt())!= 0||bqcont.getAmnt()==null)
				bqcont.setAmnt(Double.valueOf(mBqCalBase.getAmnt()));//保单
			//保费
			if(Double.valueOf(mBqCalBase.getPrem())!=0||bqcont.getPrem()==null)
				bqcont.setPrem(Double.valueOf(mBqCalBase.getPrem()));//保单
			//受理号
			if(mBqCalBase.getEdorAcceptNo() != null && !mBqCalBase.getEdorAcceptNo().equals(""))
				bqcont.setEdorAcceptNo(mBqCalBase.getEdorAcceptNo());
			//保单号码
			if(mBqCalBase.getContNo() != null && !mBqCalBase.getContNo().equals(""))
				bqcont.setContNo(mBqCalBase.getContNo());
			//保全受理号
			if(mBqCalBase.getEdorNo() != null && !mBqCalBase.getEdorNo().equals(""))
				bqcont.setEdorNo(mBqCalBase.getEdorNo());
			//退保点所在保单年度的经过天数
			if(Double.valueOf(mBqCalBase.getThroughDay())!= 0||bqcont.getThroughDay()==null)
				bqcont.setThroughDay(Double.valueOf(mBqCalBase.getThroughDay()));
			//保全申请日期
			if(mBqCalBase.getEdorAppDate() != null && !mBqCalBase.getEdorAppDate().equals(""))
			{
				theDate=mBqCalBase.getEdorAppDate()+" 00:00:00";
				bqcont.setEdorAppDate(sdf.parse(theDate));
			}
			//保全生效日期
			if(mBqCalBase.getEdorValiDate() != null && !mBqCalBase.getEdorValiDate().equals(""))
			{
				theDate=mBqCalBase.getEdorValiDate()+" 00:00:00";
				bqcont.setEdorValiDate(sdf.parse(theDate));
			}
			//退保点
			if(mBqCalBase.getZTPoint() != null && !mBqCalBase.getZTPoint().equals(""))
			{
				theDate=mBqCalBase.getZTPoint()+" 00:00:00";
				bqcont.setZTPoint(sdf.parse(theDate));
			}
			//当前日期
			if(mBqCalBase.getCURValidate() != null && !mBqCalBase.getCURValidate().equals(""))
			{
				theDate=mBqCalBase.getCURValidate()+" 00:00:00";
				bqcont.setCURValidate(sdf.parse(theDate));
			}
			//是否强制分红标志
			if(mBqCalBase.getForceDVFlag() != null && !mBqCalBase.getForceDVFlag().equals(""))
				bqcont.setForceDVFlag(mBqCalBase.getForceDVFlag());
			//应缴费次数
			if(Double.valueOf(mBqCalBase.getMonsRate())!= 0||bqcont.getMonsRate()==null)
				bqcont.setMonsRate(Double.valueOf(mBqCalBase.getMonsRate()));
			//期初时刻保单经过天数
			if(Double.valueOf(mBqCalBase.getThroughDays())!=0||bqcont.getThroughDays()==null)
				bqcont.setThroughDays(mBqCalBase.getThroughDays());
			//期末时刻保单经过天数
			if(Double.valueOf(mBqCalBase.getThroughDays1())!= 0||bqcont.getThroughDays1()==null)
				bqcont.setThroughDays1(Double.valueOf(mBqCalBase.getThroughDays1()));
			//保单年度
			if(Double.valueOf(mBqCalBase.getInterval())!= 0||bqcont.getInterval()==null)
				bqcont.setInterval(Double.valueOf(mBqCalBase.getInterval()));
			//补/退费金额
			if(Double.valueOf(mBqCalBase.getGetMoney())!= 0||bqcont.getGetMoney()==null)
				bqcont.setGetMoney(Double.valueOf(mBqCalBase.getGetMoney()));
			//当期保费是否已交标记
			if(mBqCalBase.getPayNextFlag() != null && !mBqCalBase.getPayNextFlag().equals(""))
				bqcont.setPayNextFlag(mBqCalBase.getPayNextFlag());
			//交费间隔
			if(Double.valueOf(mBqCalBase.getPayIntv())!= 0||bqcont.getPayIntv()==null)
				bqcont.setPayIntv(mBqCalBase.getPayIntv());
			String validateFlagSql="select 1 from lpedoritem l where edoracceptno ='?edoracceptno?' and datediff(edorvalidate,edorappdate)<=30 and edorvalidate<>edorappdate";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(validateFlagSql);
			sqlbv.put("edoracceptno", mBqCalBase.getEdorAcceptNo());
			ExeSQL tExeSQL = new ExeSQL();		
			String ValidateFlag = tExeSQL.getOneValue(sqlbv);
			if(!(ValidateFlag==null||"".equals(ValidateFlag.trim())))
			{
				bqcont.setValidateFlag(ValidateFlag);
			}
			else{
				bqcont.setValidateFlag("0");
			}
			//需要进行审批标志
			if(mBqCalBase.getOperator() != null && !mBqCalBase.getOperator().equals("")){
				bqcont.setOperator(mBqCalBase.getOperator());

				String edorPopedomFlagSql="select 1 from LDEdorUser a where usertype='1' and userstate='0' and usercode='?usercode?' and a.edorpopedom < 'D'";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(edorPopedomFlagSql);
				sqlbv1.put("usercode", mBqCalBase.getOperator());
				String edorPopedomFlag = tExeSQL.getOneValue(sqlbv1);
				if(!(edorPopedomFlag==null||"".equals(edorPopedomFlag.trim())))
				{
					bqcont.setEdorPopedomFlag(edorPopedomFlag);
				}
				else
				{
					bqcont.setEdorPopedomFlag("0");
				}

			

				String PEdorPopedomSql="select 1 from lduser where usercode = '?usercode?' and edorpopedom>='O'";
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql(PEdorPopedomSql);
				sqlbv2.put("usercode", mBqCalBase.getOperator());
				String PEdorPopedomFlag = tExeSQL.getOneValue(sqlbv2);
				if(!(PEdorPopedomFlag==null||"".equals(PEdorPopedomFlag.trim())))
				{
					bqcont.setUserPopedomFlag(PEdorPopedomFlag);
				}
				else
				{
					bqcont.setUserPopedomFlag("0");
				}
			

				String readyAllInformationSql="select 1 from loaccunitprice where state='0' and startdate<=to_date('?startdate1?','yyyy-mm-dd') and to_date('?startdate2?', 'yyyy-mm-dd')<=startdate";
				SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
				sqlbv3.sql(readyAllInformationSql);
				sqlbv3.put("startdate1", mBqCalBase.getCURValidate());
				sqlbv3.put("startdate2", mBqCalBase.getEdorAppDate());
				String readyAllInformationFlag = tExeSQL.getOneValue(sqlbv3);
				if(!(readyAllInformationFlag==null||"".equals(readyAllInformationFlag.trim())))
				{
					bqcont.setReadyAllInforFlag(readyAllInformationFlag);
				}
				else
				{
					bqcont.setReadyAllInforFlag("0");
				}
			}
			/** 自核 -犹豫期内不允许进行该保全操作 1-表示犹内 0-否*/
			String customgetpoldatSql="select count(*) from lccont where contno='?contno?'  and datediff(to_date('?Validate?', 'yyyy-mm-dd'),(select customgetpoldate from lccont where contno='?contno?'))<=10  and exists (select 'X' from lcpol where contno='?contno?' and uintlinkvaliflag='4')";
            SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
            sqlbv4.sql(customgetpoldatSql);
            sqlbv4.put("contno", mBqCalBase.getContNo());
            sqlbv4.put("Validate", mBqCalBase.getCURValidate());
			String customgetPolDateSqlFlag= tExeSQL.getOneValue(sqlbv4);
			
			if(customgetPolDateSqlFlag!=null&&!customgetPolDateSqlFlag.equals("")&&Integer.parseInt(customgetPolDateSqlFlag)>0){
				bqcont.setCustomgetPolDateFlag("1");
			}
			else
			{
				bqcont.setCustomgetPolDateFlag("0");
			}
			/** 限制时间间隔 */
			if(Double.valueOf(mBqCalBase.getLimitDay())!= 0||bqcont.getLimitDay()==null)
			{
				bqcont.setLimitDay(Double.valueOf(mBqCalBase.getLimitDay()));
			}

			
		}catch(Exception e){
		CError.buildErr(this, "准备BOMCont时出错！");
		e.printStackTrace();
		}
	}
	private BOMBqCont DealBOMBqCont(LCContSchema tLCContSchema){
		BOMBqCont tbqcont = new BOMBqCont();
		LPContDB tLPContDB=new LPContDB();
		tLPContDB.setContNo(tLCContSchema.getContNo());
		tLPContDB.setEdorNo(mEdorNo);
		//查询得到变更之后的保全保单数据，
		if(tLPContDB.getInfo())
		{
			tRef.transFields(tLCContSchema,tLPContDB.query().get(1));
		}
		try{
				tbqcont.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tbqcont.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
				//保全生效日期
				if(mLPEdorItemSchema.getEdorValiDate() != null && !mLPEdorItemSchema.getEdorValiDate().equals(""))
				{
					theDate=mLPEdorItemSchema.getEdorValiDate()+" 00:00:00";
					tbqcont.setEdorValiDate(sdf.parse(theDate));
				}
					//保全申请日期
				if(mLPEdorItemSchema.getEdorAppDate() != null && !mLPEdorItemSchema.getEdorAppDate().equals(""))
				{
					theDate=mLPEdorItemSchema.getEdorAppDate()+" 00:00:00";
					tbqcont.setEdorValiDate(sdf.parse(theDate));
				}
				//保全项目
				if(mLPEdorItemSchema.getEdorType() != null && !mLPEdorItemSchema.getEdorType().equals(""))
				{
						tbqcont.setEdorType(mLPEdorItemSchema.getEdorType());
				}
			
			//保全申请机构
			tbqcont.setManageCom(tLCContSchema.getManageCom());
			//保额
			tbqcont.setAmnt(Double.valueOf(tLCContSchema.getAmnt()));
			//系统日期
			if(!((tCurrentDate==null)||"".equals(tCurrentDate))){
				tCurrentDate = tCurrentDate+" "+" 00:00:00";
				tbqcont.setEndDate(sdf.parse(tCurrentDate));//责任终止日期
			}
			//保费
			tbqcont.setPrem(Double.valueOf(tLCContSchema.getPrem()));
			//本币信息
			tbqcont.setCurrency(tLCContSchema.getCurrency());
			//保单号码
			tbqcont.setContNo(tLCContSchema.getContNo());

			//终止日期
			if(!((tEndDate==null)||"".equals(tEndDate))){
				tEndDate = tEndDate+" "+" 00:00:00";
				tbqcont.setEndDate(sdf.parse(tEndDate));//责任终止日期
			}
			//管理机构
			tbqcont.setManageCom(tLCContSchema.getManageCom());
			//需要考虑加在程序里的函数。
			String tPayIntv=String.valueOf(tLCContSchema.getPayIntv());
			//交费间隔
			if(tPayIntv==null||tPayIntv.equals(""))
			{
				tbqcont.setPayIntv("0");
			}
			else
			{
				tbqcont.setPayIntv(tPayIntv);
			}
			if(!((tLCContSchema.getPolApplyDate()==null)||"".equals(tLCContSchema.getPolApplyDate()))){
				theDate=tLCContSchema.getPolApplyDate()+" 00:00:00";
				tbqcont.setPolApplyDate(sdf.parse(theDate));
				logger.debug(sdf.parse(theDate));
			}
			//销售渠道
			tbqcont.setSaleChnl(tLCContSchema.getSaleChnl());
		
		}catch(Exception e){
		CError.buildErr(this, "准备BOMCont时出错！");
		e.printStackTrace();
	}
		return tbqcont;
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
	
	private BOMBqInsured DealBOMBqInsured(LCInsuredSchema tLCInsuredSchema,LCContSchema tLCContSchema){
		LPInsuredDB tLPInsuredDB=new LPInsuredDB();
		tLPInsuredDB.setContNo(tLCContSchema.getContNo());
		tLPInsuredDB.setInsuredNo(tLCContSchema.getInsuredNo());
		tLPInsuredDB.setEdorNo(mEdorNo);
		if(tLPInsuredDB.query().get(1)!=null)
		{
			tRef.transFields(tLCInsuredSchema,tLPInsuredDB.query().get(1));
		}
		BOMBqInsured tBqinsured=new BOMBqInsured();
		try{
			tBqinsured.setInsuredNo(tLCInsuredSchema.getInsuredNo());
			ExeSQL tExeSQL2 = new ExeSQL();			
			
			if(!((tLCInsuredSchema.getBirthday()==null))||"".equals(tLCInsuredSchema.getBirthday())){
					theDate=tLCInsuredSchema.getBirthday()+" 00:00:00";
					tBqinsured.setBirthday(sdf.parse(theDate));
			}

			int tInsAge = PubFun.calInterval(tLCInsuredSchema.getBirthday(), mLCContSchema.getPolApplyDate(), "Y");
			//被保人投保年龄
			tBqinsured.setInsuredAppAge(Double.valueOf(String.valueOf(tInsAge)));
			if(!((tLCInsuredSchema.getJoinCompanyDate()==null))||"".equals(tLCInsuredSchema.getJoinCompanyDate())){
				theDate=tLCInsuredSchema.getJoinCompanyDate()+" 00:00:00";
				tBqinsured.setJoinCompanyDate(sdf.parse(theDate));
			}
			//驾照
			tBqinsured.setLicense(tLCInsuredSchema.getLicense());
			//驾照类型
			tBqinsured.setLicenseType(tLCInsuredSchema.getLicenseType());
			//婚姻状况
			tBqinsured.setMarriage(tLCInsuredSchema.getMarriage());
			//婚姻日期
			if(!(tLCInsuredSchema.getMarriageDate()==null||"".equals(tLCInsuredSchema.getMarriageDate()))){
				theDate=tLCInsuredSchema.getMarriageDate()+" 00:00:00";
				tBqinsured.setMarriageDate(sdf.parse(theDate));
			}
			
			tBqinsured.setCreditGrade(tLCInsuredSchema.getCreditGrade());
			
			tBqinsured.setDegree(tLCInsuredSchema.getDegree());
			//民族
			tBqinsured.setNationality(tLCInsuredSchema.getNationality());
			//国籍
			tBqinsured.setNativePlace(tLCInsuredSchema.getNativePlace());
			//职位
			tBqinsured.setPosition(tLCInsuredSchema.getPosition());
			//职业类型
			tBqinsured.setOccupationType(tLCInsuredSchema.getOccupationType());
			//职业代码
			tBqinsured.setOccupationCode(tLCInsuredSchema.getOccupationCode());
			//与投保人关系
			tBqinsured.setRelationToAppnt(tLCInsuredSchema.getRelationToAppnt());
			//户口所在地
			tBqinsured.setRgtAddress(tLCInsuredSchema.getRgtAddress());
			//工资
			tBqinsured.setSalary(Double.valueOf(String.valueOf(tLCInsuredSchema.getSalary())));
			//性别
			tBqinsured.setSex(tLCInsuredSchema.getSex());

		}catch(Exception e){
			CError.buildErr(this, "准备BOMInsured出错！");
			e.printStackTrace();
		}
		return tBqinsured;
	}
	
	
	private void DealBOMBqInsured(){
	try{
		
		bqinsured.setInsuredNo(mBqCalBase.getInsuredNo());
		bqinsured.setOccupationType(mBqCalBase.getJob());
//		bqinsured.setOccupationCode(mBqCalBase.)
		//性别
		ExeSQL tExeSQL = new ExeSQL();
		//还需要调整
		String occupationSql="select 1 from ldoccupation where occupationcode='?occupationcode?' and medrate>1 and occupationver='002'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(occupationSql);
		sqlbv5.put("occupationcode", mLCInsuredSchema.getOccupationCode());
		String occupaToexamineFlag = tExeSQL.getOneValue(sqlbv5);
		if(!(occupaToexamineFlag==null||"".equals(occupaToexamineFlag.trim())))
		{							
			bqinsured.setOccupaToExamineFlag(occupaToexamineFlag);
		}
		if(mBqCalBase.getSex() != null && !mBqCalBase.getSex().equals(""))
		{
			bqinsured.setSex(mBqCalBase.getSex());
		}
		if(Double.valueOf(mBqCalBase.getSumDangerAmnt())!=0||bqinsured.getSumDangerAmnt()==null)
		{
			bqinsured.setSumDangerAmnt(Double.valueOf(mBqCalBase.getSumDangerAmnt()));
		}
		if(mBqCalBase.getSecondInsuredNo() != null && !mBqCalBase.getSecondInsuredNo().equals(""))
		{
			bqinsured.setSecondInsuredNo(mBqCalBase.getSecondInsuredNo());
		}
		
		
		}catch(Exception e){
		CError.buildErr(this, "准备BOMInsured出错！");
		e.printStackTrace();
		}
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
			pol.setYears(new Double(tLCPolSchema.getYears()));
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
//			pol.setGetIntv(new Double(tLCPolSchema.getGetIntv())); // 2006-4-19
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
//			pol.setPeakLine(tLCPolSchema.getPeakLine());
//			pol.setGetLimit(tLCPolSchema.getGetLimit());
			//险种号码
			pol.setPolNo(tLCPolSchema.getPolNo());
			pol.setInsuredPeoples(new Double(tLCPolSchema.getInsuredPeoples()));
			//保费
			pol.setPrem(new Double(tLCPolSchema.getPrem()));
			
			//险种编码
			pol.setRiskCode(tLCPolSchema.getRiskCode());
			//停售标记
			pol.setStopFlag(tLCPolSchema.getStopFlag());
			//赔付比例
//			pol.setGetRate(tLCPolSchema.getGetRate());
			//给付代码
//			pol.setGetDutyCode(tLCDutySchema.getGetDutyCode());
			//单位保额
//			LMDutySchema tLMDutySchema=new LMDutySchema();
//			if(tLCDutySchema.getDutyCode()!=null&&!tLCDutySchema.getDutyCode().equals(""))
//			{
//				 tLMDutySchema = mCRI.findDutyByDutyCodeClone(tLCDutySchema
//					.getDutyCode().substring(0, 6));
//				if (tLMDutySchema == null) {
//				// @@错误处理
//				this.mErrors.copyAllErrors(mCRI.mErrors);
//				mCRI.mErrors.clearErrors();
//
//				CError.buildErr(this, "LMDuty表查询失败!");
//				}
//			}
			//给付责任类型
//			pol.setGetDutyKind(tLCGetSchema.getGetDutyKind());

//			pol.setVPU(new Double(tLCPolSchema.VPU()));
			//币别
			pol.setCurrency(tLCPolSchema.getCurrency());
			//备用1
			pol.setStandbyFlag1(tLCPolSchema.getStandbyFlag1());
			//备用2
			pol.setStandbyFlag2(tLCPolSchema.getStandbyFlag2());
			//备用3
			pol.setStandbyFlag3(tLCPolSchema.getStandbyFlag3());
		}catch(Exception e){
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}
		return pol;
	}
	/** 初始化把新契约数据放入保全BOM词条数据中*/
	private void  DealBOMBqPol(LCPolSchema tLCPolSchema,BOMInsured insureds){
		LPPolDB tLPPolDB=new LPPolDB();
		tLPPolDB.setContNo(tLCPolSchema.getContNo());
		tLPPolDB.setPolNo(tLCPolSchema.getPolNo());
		tLPPolDB.setEdorNo(mEdorNo);
		if(tLPPolDB.query().get(1)!=null)
		{
			tRef.transFields(tLCPolSchema,tLPPolDB.query().get(1));
		}
//		LPDutyDB tLPDutyDB=new LPDutyDB();
//		tLPDutyDB.setContNo(tLCPolSchema.getContNo());
////		tLPContDB.setPolNo(tLCContSchema.getPolNo());
//		tLPDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
//		if(tLPDutyDB.query().get(1)!=null)
//		{
//			tRef.transFields(tLCDutySchema,tLPDutyDB.query().get(1));
//		}
		try{
			ExeSQL tempExeSQL = new ExeSQL();
			boolean FreeFlag = false;
			BOMInsured tCurrentInsured = new BOMInsured();
			BOMInsured insured = new BOMInsured();
			insured=insureds;
			if(insured.getInsuredNo().equals(tLCPolSchema.getInsuredNo())){
				bqpol.setFatherBOM(insured);
				tCurrentInsured = insured;
			}
			//险种生效日期
			if(!(tLCPolSchema.getCValiDate()==null||"".equals(tLCPolSchema.getCValiDate()))){
				theDate=tLCPolSchema.getCValiDate()+" 00:00:00";
				bqpol.setCValiDate(sdf.parse(theDate));
			}
			bqpol.setInsuredNo(tLCPolSchema.getInsuredNo());
			bqpol.setPolNo(tLCPolSchema.getPolNo());
			//主险号码
			bqpol.setMainPolNo(tLCPolSchema.getMainPolNo());
			//险种编码
			bqpol.setRiskCode(tLCPolSchema.getRiskCode());
			bqpol.setCurrency(tLCPolSchema.getCurrency());
			bqpol.setInsuYear(Double.valueOf(tLCPolSchema.getInsuYear()));
			bqpol.setInsuYearFlag(tLCPolSchema.getInsuYearFlag());
			//总基本保额

			bqpol.setAmnt(tLCPolSchema.getAmnt());
			//浮动费率
			bqpol.setFloatRate(new Double(tLCPolSchema.getFloatRate()));
			//交费期间
			bqpol.setPayYears(new Double(tLCPolSchema.getPayYears()));
			//给付间隔
//			bqpol.setGetIntv(new Double(tLCPolSchema.getGetIntv())); // 2006-4-19
			//终交年龄年期
			bqpol.setPayEndYear(new Double(tLCPolSchema.getPayEndYear()));
			//终交年龄年期标志
			bqpol.setPayEndYearFlag(tLCPolSchema.getPayEndYearFlag());
			//起领期间
			bqpol.setGetYear(new Double(tLCPolSchema.getGetYear()));
			//起领期间单位
			bqpol.setGetYearFlag(tLCPolSchema.getGetYearFlag());
			//总份数
			bqpol.setMult(new Double(tLCPolSchema.getMult()));
//			bqpol.setGetLimit(tLCPolSchema.getGetLimit());
			//险种号码
			bqpol.setPrem(new Double(tLCPolSchema.getPrem()));
			

			//赔付比例
//			bqpol.setGetRate(tLCPolSchema.getGetRate());
				//给付代码
				//			pol.setGetDutyCode(tLCDutySchema());
				//单位保额
//			LMDutySchema tLMDutySchema=new LMDutySchema();
//			if(tLCDutySchema.getDutyCode()!=null&&!tLCDutySchema.getDutyCode().equals(""))
//			{
//				tLMDutySchema = mCRI.findDutyByDutyCodeClone(tLCDutySchema
//					.getDutyCode().substring(0, 6));
//				if (tLMDutySchema == null) {
//					// @@错误处理
//					this.mErrors.copyAllErrors(mCRI.mErrors);
//					mCRI.mErrors.clearErrors();
//
//					CError.buildErr(this, "LMDuty表查询失败!");
//				}
//			}
				//给付责任类型
			//pol.setsetGetDutyKind(tLCDutySchema.getGetDutyKind());
//			bqpol.setVPU(new Double(tLMDutySchema.getVPU()));
			//币别
			
			//备用1
			bqpol.setStandByFlag1(tLCPolSchema.getStandbyFlag1());
		}catch(Exception e){
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}

	}
	
	private void DealBOMBqPol(){
		try{
			if(Double.valueOf(mBqCalBase.getMult())!=0||bqpol.getMult()==null)
				bqpol.setMult(Double.valueOf(mBqCalBase.getMult()));
			
			if(!(mBqCalBase.getStandByFlag1()==null|| mBqCalBase.getStandByFlag1().equals("")))
				bqpol.setStandByFlag1(mBqCalBase.getStandByFlag1());
			if(!(mBqCalBase.getEdorTypeCal()==null|| mBqCalBase.getEdorTypeCal().equals("")))
				bqpol.setEdorTypeCal(mBqCalBase.getEdorTypeCal());
			if(mBqCalBase.getSignDate()!= null&&!mBqCalBase.getSignDate().equals(""))
			{
				theDate=mBqCalBase.getSignDate()+" 00:00:00";
				bqpol.setSignDate(sdf.parse(theDate));
			}
			//终了红利率
			if(Double.valueOf(mBqCalBase.getTBRate())!=0||bqpol.getTBRate()==null)
				bqpol.setTBRate(Double.valueOf(mBqCalBase.getTBRate()));
			if(Double.valueOf(mBqCalBase.getGetIntv())!=0||bqpol.getGetIntv()==null)
				bqpol.setGetIntv(Double.valueOf(mBqCalBase.getGetIntv()));
			if(Double.valueOf(mBqCalBase.getGetLimit())!=0||bqpol.getGetLimit()==null)
				bqpol.setGetLimit(Double.valueOf(mBqCalBase.getGetLimit()));
			if(Double.valueOf(mBqCalBase.getGetRate())!=0||bqpol.getGetRate()==null)
				bqpol.setGetRate(Double.valueOf(mBqCalBase.getGetRate()));
			if(Double.valueOf(mBqCalBase.getSumPrem())!=0||bqpol.getSumPrem()==null)
				bqpol.setSumPrem(Double.valueOf(mBqCalBase.getSumPrem()));
			if(mBqCalBase.getPayEndDate()!=null&&!mBqCalBase.getPayEndDate().equals(""))
			{
				theDate=mBqCalBase.getPayEndDate()+" 00:00:00";
				bqpol.setPayEndDate(sdf.parse(theDate));
			}
			//未过月数
			if(Double.valueOf(mBqCalBase.getAAYears())!=0||bqpol.getAAYears()==null)
			{
				bqpol.setAAYears(mBqCalBase.getAAYears());
			}
			if(Double.valueOf(mBqCalBase.getGetYear())!=0||bqpol.getGetYear()==null)
				bqpol.setGetYear(Double.valueOf(mBqCalBase.getGetYear()));
//			bqpol.setCurrency(mBqCalBase.getg);
//			bqpol.setSubRiskFlag(mBqCalBase.getsu);
//			bqpol.setPayYearFlag();
//			bqpol.setPayYears(mBqCalBase.getpay);
			if(mBqCalBase.getStartDate()!=null&&!mBqCalBase.getStartDate().equals(""))
			{
				theDate=mBqCalBase.getStartDate()+" 00:00:00";
				bqpol.setStartDate(sdf.parse(theDate));
			}
//			bqpol.setUWFlag(mBqCalBase.get);
			//保单已过月数
			if(Double.valueOf(mBqCalBase.getIntervalM())!=0||bqpol.getIntervalM()==null)
				bqpol.setIntervalM(Double.valueOf(mBqCalBase.getIntervalM()));
			if(mBqCalBase.getEndDate()!=null&&!mBqCalBase.getEndDate().equals(""))
			{
				theDate=mBqCalBase.getEndDate()+" 00:00:00";
				bqpol.setEndDate(sdf.parse(theDate));
			}
			if(mBqCalBase.getInsuYearFlag()!=null&&!mBqCalBase.getInsuYearFlag().equals(""))
				bqpol.setInsuYearFlag(mBqCalBase.getInsuYearFlag());
			if(Double.valueOf(mBqCalBase.getInsuYear())!=0||bqpol.getInsuYear()==null)
				bqpol.setInsuYear(Double.valueOf(mBqCalBase.getInsuYear()));
			if(mBqCalBase.getCValiDate()!=null&&!mBqCalBase.getCValiDate().equals(""))
			{
				theDate=mBqCalBase.getCValiDate()+" 00:00:00";
				bqpol.setCValiDate(sdf.parse(theDate));
			}
			if(Double.valueOf(mBqCalBase.getPrem())!=0||bqpol.getPrem()==null)
				bqpol.setPrem(Double.valueOf(mBqCalBase.getPrem()));
			if(Double.valueOf(mBqCalBase.getAmnt())!=0||bqpol.getAmnt()==null)
				bqpol.setAmnt(Double.valueOf(mBqCalBase.getAmnt()));
			if(mBqCalBase.getMainPolNo()!=null&&!mBqCalBase.getMainPolNo().equals(""))
				bqpol.setMainPolNo(mBqCalBase.getMainPolNo());
			if(mBqCalBase.getPolNo()!=null&&!mBqCalBase.getPolNo().equals(""))
				bqpol.setPolNo(mBqCalBase.getPolNo());
			if(mBqCalBase.getInsuredNo()!=null&&!mBqCalBase.getInsuredNo().equals(""))
				bqpol.setInsuredNo(mBqCalBase.getInsuredNo());
			if(mBqCalBase.getRiskCode()!=null&&!mBqCalBase.getRiskCode().equals(""))
//			bqpol.setGetYearFlag(mBqCalBase.getgety);
				bqpol.setRiskCode(mBqCalBase.getRiskCode());
			if(mBqCalBase.getPayToDate()!=null&&!mBqCalBase.getPayToDate().equals(""))
			{
				theDate=mBqCalBase.getPayToDate()+" 00:00:00";
				bqpol.setPayToDate(sdf.parse(theDate));
			}
			if(mBqCalBase.getPayEndYearFlag()!=null&&!mBqCalBase.getPayEndYearFlag().equals(""))
				bqpol.setPayEndYearFlag(mBqCalBase.getPayEndYearFlag());
			if(Double.valueOf(mBqCalBase.getPayEndYear())!=0||bqpol.getPayEndYear()==null)
				bqpol.setPayEndYear(Double.valueOf(mBqCalBase.getPayEndYear()));
			if(Double.valueOf(mBqCalBase.getCVt1GetDraw())!=0||bqpol.getCVt1GetDraw()==null)
				bqpol.setCVt1GetDraw(Double.valueOf(mBqCalBase.getCVt1GetDraw()));
			if(Double.valueOf(mBqCalBase.getGVt())!=0||bqpol.getGVt()==null)
				bqpol.setGVt(Double.valueOf(mBqCalBase.getGVt()));
			if(Double.valueOf(mBqCalBase.getGVt1())!=0||bqpol.getGVt1()==null)
				bqpol.setGVt1(Double.valueOf(mBqCalBase.getGVt1()));
			if(Double.valueOf(mBqCalBase.getNextPrem())!=0||bqpol.getNextPrem()==null)
				bqpol.setNextPrem(Double.valueOf(mBqCalBase.getNextPrem()));
			if(Double.valueOf(mBqCalBase.getPayTimes())!=0||bqpol.getPayTimes()==null)
				bqpol.setPayTimes(Double.valueOf(mBqCalBase.getPayTimes()));
			if(Double.valueOf(mBqCalBase.getAliveGet())!=0||bqpol.getAliveGet()==null)
				bqpol.setAliveGet(Double.valueOf(mBqCalBase.getAliveGet()));
			if(Double.valueOf(mBqCalBase.getliveGet())!=0||bqpol.getliveGet()==null)
				bqpol.setliveGet(Double.valueOf(mBqCalBase.getliveGet()));
			if(Double.valueOf(mBqCalBase.getSumAmntBonus())!=0||bqpol.getSumAmntBonus()==null)
				bqpol.setSumAmntBonus(Double.valueOf(mBqCalBase.getSumAmntBonus()));
			if(Double.valueOf(mBqCalBase.getSumYearGetRate())!=0||bqpol.getSumYearGetRate()==null)
				bqpol.setSumYearGetRate(Double.valueOf(mBqCalBase.getSumYearGetRate()));
			if(Double.valueOf(mBqCalBase.getSumYearGet())!=0||bqpol.getSumYearGet()==null)
				bqpol.setSumYearGet(Double.valueOf(mBqCalBase.getSumYearGet()));
			if(Double.valueOf(mBqCalBase.getAliveGetRate())!=0||bqpol.getAliveGetRate()==null)
				bqpol.setAliveGetRate(Double.valueOf(mBqCalBase.getAliveGetRate()));
			if(mBqCalBase.getEdorReasonCode()!=null&&!mBqCalBase.getEdorReasonCode().equals(""))
				bqpol.setEdorReasonCode(mBqCalBase.getEdorReasonCode());
			if(Double.valueOf(mBqCalBase.getZTMoneyByAcc())!=0||bqpol.getZTMoneyByAcc()==null)
				bqpol.setZTMoneyByAcc(Double.valueOf(mBqCalBase.getZTMoneyByAcc()));
			if(Double.valueOf(mBqCalBase.getTrayMoney())!=0||bqpol.getTrayMoney()==null)
				bqpol.setTrayMoney(Double.valueOf(mBqCalBase.getTrayMoney()));
			if(Double.valueOf(mBqCalBase.getAddRate())!=0||bqpol.getAddRate()==null)
				bqpol.setAddRate(Double.valueOf(mBqCalBase.getAddRate()));
			if(Double.valueOf(mBqCalBase.getBonusRate())!=0||bqpol.getBonusRate()==null)
				bqpol.setBonusRate(mBqCalBase.getBonusRate());
			if(Double.valueOf(mBqCalBase.getPolValiFlag())!=0||bqpol.getPolValiFlag()==null)
				bqpol.setPolValiFlag(mBqCalBase.getPolValiFlag());
//			bqpol.setRiskSort(mBqCalBase.getri);
//			bqpol.setBalaDate(mBqCalBase.getGetBalance());
//			bqpol.setLastBalaDate();
			if(Double.valueOf(mBqCalBase.getReduceAmnt())!=0||bqpol.getReduceAmnt()==null)
				bqpol.setReduceAmnt(Double.valueOf(mBqCalBase.getReduceAmnt()));
			if(Double.valueOf(mBqCalBase.getCVt1())!=0||bqpol.getCVt1()==null)
				bqpol.setCVt1(Double.valueOf(mBqCalBase.getCVt1()));
			if(Double.valueOf(mBqCalBase.getCVt())!=0||bqpol.getCVt()==null)
				bqpol.setCVt(Double.valueOf(mBqCalBase.getCVt()));
			if(Double.valueOf(mBqCalBase.getCVtGetDraw())!=0||bqpol.getCVtGetDraw()==null)
				bqpol.setCVtGetDraw(Double.valueOf(mBqCalBase.getCVtGetDraw()));
			if(Double.valueOf(mBqCalBase.getLoanMoney())!=0||bqpol.getLoanMoney()==null)
				bqpol.setLoanMoney(Double.valueOf(mBqCalBase.getLoanMoney()));
			
			if(!(mBqCalBase.getLoanDate()==null||mBqCalBase.getLoanDate().equals("")))
			{
				theDate=mBqCalBase.getLoanDate()+" 00:00:00";
				bqpol.setLoanDate(sdf.parse(theDate));
			}
			String PolClaimFlagSql="select 1 from ljagetclaim where polno='?polno?' and feeoperationtype in('A','B') and pay<>0";
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(PolClaimFlagSql);
			sqlbv6.put("polno", bqpol.getPolNo());
			ExeSQL tExeSQL = new ExeSQL();
			String PolClaimFlag = tExeSQL.getOneValue(sqlbv6);
			if(!(PolClaimFlag==null||"".equals(PolClaimFlag.trim())))
			{							
				bqpol.setPolClaimFlag(PolClaimFlag);
			}
			else
			{
				bqpol.setPolClaimFlag("0");
			}
			
			String riskSql="select 1 from lmriskapp where riskcode='?riskcode?' and RiskPeriod='M'";
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
			sqlbv7.sql(riskSql);
			sqlbv7.put("riskcode", bqpol.getRiskCode());
			String riskCode = tExeSQL.getOneValue(sqlbv7);
			if(!(riskCode==null||"".equals(riskCode.trim())))
			{
				//短期退保费率
				String shortriskCTRateSql="select rate from LMShortRiskCTRate where months=floor(months_between(to_date('?date1?','yyyy-mm-dd'),to_DATE('?date2?','yyyy-mm-dd')))";
				SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
				sqlbv8.sql(shortriskCTRateSql);
				sqlbv8.put("date1", mBqCalBase.getZTPoint());
				sqlbv8.put("date2", bqpol.getCValiDate());
				String shortriskCTRate= tExeSQL.getOneValue(sqlbv8);
				if(!(shortriskCTRate==null&&"".equals(shortriskCTRate.trim())))
				{							
					bqpol.setShortRate(Double.valueOf(shortriskCTRate));
				}
				else
				{
					bqpol.setShortRate(0.00);
				}
				//保险期间计算短期费率
				String shortRiskRateSql="select Rate from RateShort where Startmonth<=shortRateCalMonth('?Year?','?YearFlag?') and shortRateCalMonth('?Year?','?YearFlag?')<Endmonth";
				SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
				sqlbv9.sql(shortRiskRateSql);
				sqlbv9.put("Year", bqpol.getInsuYear());
				sqlbv9.put("YearFlag", bqpol.getInsuYearFlag());
				String ShortRate = tExeSQL.getOneValue(sqlbv9);
				if(!(ShortRate==null&&"".equals(ShortRate.trim())))
				{							
					bqpol.setShortRate(Double.valueOf(ShortRate));
				}else
				{
					bqpol.setShortRate(0.00);
				}
			}
			//险种不支持选择的红利领取方式标记
			String BonusGetModeSql="select 1 from lppol where edorno='?edorno?' and  polno='?polno?' and bonusgetmode in (select paramscode from LMRiskParamsDef where paramstype = 'bonusgetmode' and riskcode='?riskcode?')";
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql(BonusGetModeSql);
			sqlbv10.put("edorno", mBqCalBase.getEdorNo());
			sqlbv10.put("polno", bqpol.getPolNo());
			sqlbv10.put("riskcode", bqpol.getRiskCode());
			String BonusGetModeFlag = tExeSQL.getOneValue(sqlbv10);
			if(!(BonusGetModeFlag==null||"".equals(BonusGetModeFlag.trim())))
			{
				bqpol.setBonusGetModeFlag(BonusGetModeFlag);
			}
			else
			{
				bqpol.setBonusGetModeFlag("0");
			}
			//该险种只能附加于1年期以上（不含1年期）个人寿险、年金险、教育金以及重大疾病险的主险之下
			if((!(riskCode==null||"".equals(riskCode.trim())))&& bqpol.getRiskCode().equals("121301"))
			{
				String mainSubLRiskSql="select 1 from lmriskapp where riskperiod='L' and risktype8 in ('00','01','02','03','05') and riskcode='?riskcode?'";
				SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
				sqlbv11.sql(mainSubLRiskSql);
				sqlbv11.put("riskcode", bqpol.getRiskCode());
				String mainSubLRiskFlag = tExeSQL.getOneValue(sqlbv11);
				bqpol.setMainSubLRiskFlag(mainSubLRiskFlag);
			}else
			{
				bqpol.setMainSubLRiskFlag("0");
			}
			
			String CTVersionDateSql ="select sysvarvalue sdate from ldsysvar where sysvar='CTVersionDate'";
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
			sqlbv12.sql(CTVersionDateSql);
			String CTVersionDate= tExeSQL.getOneValue(sqlbv12);
			if(!(CTVersionDate==null||CTVersionDate.equals("")))
			{
				theDate=CTVersionDate+" 00:00:00";
				bqpol.setCTVersionDate(sdf.parse(theDate));
				
			}
			/**团险需处理，LCDUTY
			if(bqpol.getRiskCode().equals("221601"))
			{
				//增人：意外伤害住院日给付--60周岁以下(含)200元为限
				String NIAccHospDayGet="select 1 from lpduty p where edorno='?EdorNo?' and grpcontno='?GrpContNo?' and contno='?ContNo?' and dutycode='640003' and insuredappage>60 and amnt>100";	
				//增人：意外医疗--70周岁(含)以上被保险人商业运营飞机最高保额为4万元
				select 1 from lpduty where edorno='?EdorNo?' and grpcontno='?GrpContNo?' and contno='?ContNo?' and dutycode='640002' and insuredappage>=70 and amnt>40000
				//增人：意外身故、残疾--商业运营飞机最高保额为40万元
				select 1 from lpduty where edorno='?EdorNo?' and grpcontno='?GrpContNo?' and contno='?ContNo?' and dutycode='640001' and amnt>400000	
				//增人：意外身故、残疾--70周岁(含)以上被保险人商业运营飞机最高保额为20万元
				select 1 from lpduty where edorno='?EdorNo?' and grpcontno='?GrpContNo?' and contno='?ContNo?' and dutycode='640001' and insuredappage>=70 and amnt>200000
				//增人：意外医疗--商业运营飞机最高保额为10万元
				select 1 from lpduty where edorno='?EdorNo?' and grpcontno='?GrpContNo?' and contno='?ContNo?' and dutycode='640002' and amnt>100000
				//增人：意外伤害住院日给付--60周岁以上100元为限
				select 1 from lpduty where edorno='?EdorNo?' and grpcontno='?GrpContNo?' and contno='?ContNo?' and dutycode='640003' and amnt>200
			}
			**/

	}catch(Exception e){
			CError.buildErr(this, "准备BOMPol出错！");
			e.printStackTrace();
		}
	}
	
	/**保全级受益人信息*/
	private BOMBqBnf DealBOMBqBnf(LCBnfSchema tLCBnfSchema,BOMBqInsured insured){
		LPBnfDB tLPBnfDB=new LPBnfDB();
		tLPBnfDB.setContNo(tLCBnfSchema.getContNo());
		tLPBnfDB.setPolNo(tLCBnfSchema.getPolNo());
		tLPBnfDB.setInsuredNo(tLCBnfSchema.getInsuredNo());
		tLPBnfDB.setEdorNo(mEdorNo);
		if(tLPBnfDB.query().get(1)!=null)
		{
			tRef.transFields(tLCBnfSchema,tLPBnfDB.query().get(1));
		}
		BOMBqBnf bqbnf = new BOMBqBnf();//多个受益人
		try{

				if(!(tLCBnfSchema.getBirthday()==null||"".equals(tLCBnfSchema.getBirthday()))){
					theDate=tLCBnfSchema.getBirthday()+" 00:00:00";
					bqbnf.setBirthday(sdf.parse(theDate));
				}
				bqbnf.setBnfGrade(tLCBnfSchema.getBnfGrade());
				bqbnf.setBnfLot(new Double(tLCBnfSchema.getBnfLot()));
				bqbnf.setBnfType(tLCBnfSchema.getBnfType());
				bqbnf.setBnfName(tLCBnfSchema.getName());
				bqbnf.setCustomerNo(tLCBnfSchema.getCustomerNo());
				bqbnf.setInsuredNo(tLCBnfSchema.getInsuredNo());

				insured=insured;
				if(insured.getInsuredNo().equals(bqbnf.getInsuredNo())){
					bqbnf.setFatherBOM(insured);
				}
				bqbnf.setRelationToInsured(tLCBnfSchema.getRelationToInsured());
				bqbnf.setSex(tLCBnfSchema.getSex());

		}catch(Exception e){
			CError.buildErr(this, "准备BOMBnf时出错！");
			e.printStackTrace();
		}
		return bqbnf;
	}
	public void setBqCalBase(BqCalBase tBqCalBase)
	{
		this.mBqCalBase = tBqCalBase;
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
			SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
			sqlbv13.sql(tBlackList);
			sqlbv13.put("customerno", tLCAppntSchema.getAppntNo());
			String tBlackFlag = tempExeSQL.getOneValue(sqlbv13);
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
	

	private BOMBqAppnt DealBOMBqAppnt(LCAppntSchema tLCAppntSchema,LCContSchema tLCContSchema){
		BOMBqAppnt tbqappnt = new BOMBqAppnt();
		LPAppntDB tLPAppntDB=new LPAppntDB();
		tLPAppntDB.setContNo(tLCAppntSchema.getContNo());
//		tLPContDB.setPolNo(tLCContSchema.getPolNo());
		tLPAppntDB.setAppntNo(tLCAppntSchema.getAppntNo());
		tLPAppntDB.setEdorNo(mEdorNo);
		if(tLPAppntDB.query().get(1)!=null)
		{
			tRef.transFields(tLCAppntSchema,tLPAppntDB.query().get(1));
		}
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			int tAppAge = PubFun.calInterval2(tLCAppntSchema.getAppntBirthday(), mLCContSchema.getPolApplyDate(), "Y");
			tbqappnt.setAppntAge(Double.valueOf(String.valueOf(tAppAge)));
			if(!((tLCAppntSchema.getAppntBirthday()==null))||"".equals(tLCAppntSchema.getAppntBirthday())){
				theDate=tLCAppntSchema.getAppntBirthday()+" 00:00:00";
				tbqappnt.setAppntBirthday(sdf.parse(theDate));
			}
			tbqappnt.setAppntName(tLCAppntSchema.getAppntName());
			tbqappnt.setAppntNo(tLCAppntSchema.getAppntNo());
			tbqappnt.setAppntSex(tLCAppntSchema.getAppntSex());			
			String tBlackList="select (case when BlacklistFlag is not null then BlacklistFlag else '0' end) from ldperson where customerno='?customerno?'";
			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
			sqlbv14.sql(tBlackList);
			sqlbv14.put("customerno", tLCAppntSchema.getAppntNo());
			String tBlackFlag = tempExeSQL.getOneValue(sqlbv14);
			tbqappnt.setBlackListFlag(tBlackFlag);//黑名单标记
			tbqappnt.setCreditGrade(tLCAppntSchema.getCreditGrade());
			tbqappnt.setDegree(tLCAppntSchema.getDegree());			


			if(!((tLCAppntSchema.getJoinCompanyDate()==null)||"".equals(tLCAppntSchema.getJoinCompanyDate()))){
				theDate=tLCAppntSchema.getJoinCompanyDate()+" 00:00:00";
				tbqappnt.setJoinCompanyDate(sdf.parse(theDate));
			}
			tbqappnt.setMarriage(tLCAppntSchema.getMarriage());
			if(!((tLCAppntSchema.getMarriageDate()==null))||"".equals(tLCAppntSchema.getMarriageDate())){
				theDate=tLCAppntSchema.getMarriageDate()+" 00:00:00";
				tbqappnt.setMarriageDate(sdf.parse(theDate));
			}
			tbqappnt.setNationality(tLCAppntSchema.getNationality());
			tbqappnt.setNativePlace(tLCAppntSchema.getNativePlace());
			tbqappnt.setOccupationCode(tLCAppntSchema.getOccupationCode());
			tbqappnt.setOccupationType(tLCAppntSchema.getOccupationType());
			tbqappnt.setRelationToInsured(tLCAppntSchema.getRelationToInsured());
			tbqappnt.setSalary(new Double(tLCAppntSchema.getSalary()));
			if(!((tLCAppntSchema.getStartWorkDate()==null))||"".equals(tLCAppntSchema.getStartWorkDate())){
				theDate=tLCAppntSchema.getStartWorkDate()+" 00:00:00";
				tbqappnt.setStartWorkDate(sdf.parse(theDate));
			}
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
		return tbqappnt;
	}
	
	/**契约受益人信息**/
	private BOMBnf DealBOMBnf(LCBnfSchema tLCBnfSchema,BOMInsured insured){
		BOMBnf bnf = new BOMBnf();
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
	private void DealBOMBqAppnt(){
		ExeSQL tempExeSQL = new ExeSQL();
		try{
			bqappnt.setAppntAge(Double.valueOf(mBqCalBase.getAppAge()));
			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
	}
	
	private void DealBOMBqBnf(){
		ExeSQL tempExeSQL = new ExeSQL();
		try{

			
		}catch(Exception e){
			CError.buildErr(this, "准备BOMAppnt时出错");
		}
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
					+ "  and enddate is null and polno = '?polno?' " + " union select to_date('?sCalDate?','yyyy-mm-dd') from dual where 1=1 ) g";
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			sqlbv15.sql(sql);
			sqlbv15.put("polno", mLCPolSchema.getPolNo());
			sqlbv15.put("sCalDate", sCalDate);
			ExeSQL tExeSQL = new ExeSQL();
			sCalDate = tExeSQL.getOneValue(sqlbv15);
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
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(sql);
		sqlbv16.put("sCalDate", sCalDate);
		sqlbv16.put("polno", pLCPolSchema.getPolNo());
		ExeSQL tExeSQL = new ExeSQL();
		String sdisAvailable = tExeSQL.getOneValue(sqlbv16);
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
		String sql = " select 1 from dual where '"
				+ "?sRiskCode?"
				+ "' in (select trim(riskcode) from lmrisk where trim(riskcode) like concat(concat('%00','?sRisk?'),'%')) ";
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		sqlbv17.sql(sql);
		sqlbv17.put("sRiskCode", sRiskCode);
		sqlbv17.put("sRisk", sRisk);
		String sResult = tExeSQL.getOneValue(sqlbv17);
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
