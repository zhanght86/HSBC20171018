package com.sinosoft.lis.xbcheck;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCRnewStateHistoryDB;
import com.sinosoft.lis.db.LCUWErrorDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LMUWDB;
import com.sinosoft.lis.db.RnewIndUWErrorDB;
import com.sinosoft.lis.db.RnewIndUWMasterDB;
import com.sinosoft.lis.db.RnewIndUWSubDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCRnewStateHistorySchema;
import com.sinosoft.lis.schema.LCUWErrorSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LMRiskSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.schema.RnewIndUWErrorSchema;
import com.sinosoft.lis.schema.RnewIndUWMasterSchema;
import com.sinosoft.lis.schema.RnewIndUWSubSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCRnewStateHistorySet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LMRiskSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.lis.vschema.RnewIndUWErrorSet;
import com.sinosoft.lis.vschema.RnewIndUWMasterSet;
import com.sinosoft.lis.vschema.RnewIndUWSubSet;
import com.sinosoft.service.BusinessDelegate;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflowengine.WorkFlowUI;


/**
 * <p>Title: Web业务系统承保个人单自动核保部分</p>
 * <p>Description: 逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author WHN
 * @version 1.0
 */
public class PRnewUWAutoChkBL
{
private static Logger logger = Logger.getLogger(PRnewUWAutoChkBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  private MMap xMMap = new MMap();  //最后用来统一提交数据库
  /** 数据操作字符串 */
  private String mOperate;
  private String mpassflag; //通过标记
  private String muwgrade = "";
  private String mpassflag2="0"; //附加险通过标记
  private String muwgrade2=""; //附加未自动核保通过,其核保级别
  private int merrcount; //错误条数
  private String mCalCode; //计算编码
  private String mUser;
  private FDate fDate = new FDate();
  private double mValue;
  private String mStartDate ="";
  private String mEndDate ="";
  private List mBomList = new ArrayList();

  /** 业务处理相关变量 */
  private LCPolSet mLCPolSet = new LCPolSet();
  private LCPolSet mmLCPolSet = new LCPolSet();
  private LCPolSet mAllLCPolSet = new LCPolSet();
  private LCPolSchema mLCPolSchema = new LCPolSchema();
  private String mPolNo = "";
  private String mOldPolNo = "";


  /** 核保主表 */
  private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
  private LCUWMasterSet mAllLCUWMasterSet = new LCUWMasterSet();
  private LCUWMasterSchema mLCUWMasterSchema = new LCUWMasterSchema();
  /** 核保子表 */
  private LCUWSubSet mLCUWSubSet = new LCUWSubSet();
  private LCUWSubSet mAllLCUWSubSet = new LCUWSubSet();
  private LCUWSubSchema mLCUWSubSchema = new LCUWSubSchema();
  /** 核保错误信息表 */
  private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
  private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();
  
  /** 被保人核保主表 */
  private RnewIndUWMasterSet mRnewIndUWMasterSet = new RnewIndUWMasterSet();
  private RnewIndUWMasterSet mAllRnewIndUWMasterSet = new RnewIndUWMasterSet();
  private RnewIndUWMasterSchema mRnewIndUWMasterSchema = new RnewIndUWMasterSchema();
  /** 被保人核保子表 */
  private RnewIndUWSubSet mRnewIndUWSubSet = new RnewIndUWSubSet();
  private RnewIndUWSubSet mAllRnewIndUWSubSet = new RnewIndUWSubSet();
  private RnewIndUWSubSchema mRnewIndUWSubSchema = new RnewIndUWSubSchema();
  /** 被保人核保错误信息表 */
  private RnewIndUWErrorSet mRnewIndUWErrorSet = new RnewIndUWErrorSet();
  private RnewIndUWErrorSet mAllRnewIndUWErrorSet = new RnewIndUWErrorSet();
  
  /**计算公式表**/
  private LMUWSchema mLMUWSchema = new LMUWSchema();
  private LMUWSet mLMUWSet = new LMUWSet();
  private LMUWSet m2LMUWSet = new LMUWSet();
  private LMUWSet mmLMUWSet = new LMUWSet();
  /**续保人工核保工作流数据*/
  private VData tWorkFlowVData = new VData();
  private TransferData tWorkFlowTransferData = new TransferData();
  private LCRnewStateHistorySet mLCRnewStateHistorySet = new LCRnewStateHistorySet();
  /** 全局数据 */
  private GlobalInput mGlobalInput = new GlobalInput();
  private Reflections mReflections = new Reflections();
  private String CurrentDate = PubFun.getCurrentDate();
  private String CurrentTime = PubFun.getCurrentTime();


  private CalBase mCalBase = new CalBase();

  public PRnewUWAutoChkBL() {}

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate)
  {
	 int flag = 0; //判断是不是所有数据都不成功
	 int j = 0; //符合条件数据个数

	 //将操作数据拷贝到本类中
	 mInputData = (VData)cInputData.clone();

	 logger.debug("---1---");
	 //得到外部传入的数据,将数据备份到本类中
	 if (!getInputData(cInputData))
     return false;
     logger.debug("---PRnewUWAutoChkBL getInputData---");
     //此处屏蔽核保级别校验，催收时续保自核不需要核保资格的校验
     /*
     //校验核保级别
     if (!checkUWGrade())
	 {
	   return false;
	 }
	 */


	  int n = mLCPolSet.size();
	  for(int i = 1;i <= n;i++)
	  {
		  LCPolSchema tLCPolSchema = new LCPolSchema();
		  mLCPolSchema = new LCPolSchema();
		  tLCPolSchema = ( LCPolSchema )mLCPolSet.get( i );
		  mLCPolSchema = ( LCPolSchema )mLCPolSet.get( i );
		  String tProposalNo = tLCPolSchema.getProposalNo();
		  //查找原险种记录
		  LCPolDB xxLCPolDB = new LCPolDB();
		  xxLCPolDB.setContNo(tLCPolSchema.getContNo());
		  xxLCPolDB.setRiskCode(tLCPolSchema.getRiskCode());
		  xxLCPolDB.setAppFlag("1");
		  if(xxLCPolDB.query().size()==0)
		  {
			  logger.debug("查找保单号下"+tLCPolSchema.getContNo()+"原险种记录"+tLCPolSchema.getRiskCode()+"出错！");
			  continue ;
		  }
		  mOldPolNo = xxLCPolDB.query().get(1).getPolNo();  //原险种号
		  //由于自核规则有上一年度的限制，所以取时间范围
		  mStartDate = xxLCPolDB.query().get(1).getCValiDate(); //自核规则中会用到的起始时间
		  mEndDate = xxLCPolDB.query().get(1).getPaytoDate(); 
		  mPolNo = tLCPolSchema.getProposalNo();
	
	   // 校验数据
	   if (!checkApprove(tLCPolSchema))
	   {
		 if (flag == 1)
		 {
		   flag = 2;
		 }
		 continue ;
		 //return false;
	   }
	   else
	   {
		 flag = 1;
		 j++;
	  }
	
	   //校验主附险
	   if (!checkMain())
		 continue;
	    //return false;
	   else
	   {
		 flag = 1;
		 j++;
	   }
	
	   logger.debug("---PRnewUWAutoChkBL checkData---");
	   // 数据操作业务处理
	   if (!dealData(tLCPolSchema))
		 continue;
	   else
	   {
		 flag = 1;
		 j++;
	   }
	 }

	 if (flag == 0)
	 {
	   CError tError = new CError();
	   tError.moduleName = "PRnewUWAutoChkBL";
	   tError.functionName = "submitData";
	   tError.errorMessage = "没有自动通过保单!";
	   this.mErrors .addOneError(tError) ;
	   return false;
	 }


	 logger.debug("---PRnewUWAutoChkBL dealData---");
	 //准备给后台的数据
	 prepareOutputData();
	 
     //	提交数据
	 mResult = new VData();
	 mResult.add(xMMap);
	 PubSubmit tSubmit = new PubSubmit();
	 if (!tSubmit.submitData(mResult, "")) 
	 {
		logger.debug("投保单号"+mPolNo+"在续保自核提交数据时失败！");
		this.mErrors.copyAllErrors(tSubmit.mErrors);
		//ErrCount++;
		mResult.clear();
		return false;
	 }
	 mResult.clear();
	
	 logger.debug("---PRnewUWAutoChkBL prepareOutputData---");
	 /*
	 //数据提交
	 PRnewUWAutoChkBLS tPRnewUWAutoChkBLS = new PRnewUWAutoChkBLS();
	 logger.debug("Start PRnewUWAutoChkBL Submit...");
	 if (!tPRnewUWAutoChkBLS.submitData(mInputData,mOperate))
	 {
	   // @@错误处理
	   this.mErrors.copyAllErrors(tPRnewUWAutoChkBLS.mErrors);
	   CError tError = new CError();
	   tError.moduleName = "PRnewUWAutoChkBL";
	   tError.functionName = "submitData";
	   tError.errorMessage = "数据提交失败!";
	   //this.mErrors .addOneError(tError) ;
	   return false;
	 }
	 logger.debug("---PRnewUWAutoChkBL commitData---");
	 */
	 return true;
  }

  /**
   * 数据操作类业务处理
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean dealData(LCPolSchema tLCPolSchema)
  {
	mOldPolNo = tLCPolSchema.getPolNo();
	String tkinds = tLCPolSchema.getRiskCode();

	//准备算法
	if (CheckKinds(tLCPolSchema) == false)
       return  false;

    if (CheckKinds2(tLCPolSchema) == false)
       return false;

	 //准备数据
	 CheckPolInit(tLCPolSchema);
	
	 //个人单核保
	 mpassflag = "0";
	 int n = mmLMUWSet.size();
	 if (n == 0)
	 {
	   mpassflag = "9";
	 }
	 else//目前目前所有的险种均有一些公共的核保规则,所以必定走该分枝
	 {
	   int j = 0;
	   mLMUWSet.clear();
	   for (int i = 1;i<=n;i++)
	   {
		 //取计算编码
		 LMUWSchema tLMUWSchema = new LMUWSchema();
		 tLMUWSchema = mmLMUWSet.get(i);
		 mCalCode = tLMUWSchema.getCalCode();
		 if (CheckPol() == 0)
		 {
		   //mLMUWSet.remove(tLMUWSchema);
		 }
		 else
		 {
		   j++;
		   mLMUWSet.add(tLMUWSchema);
		   mpassflag  = "5"; //待人工核保
	
		   //取核保级别
		   if (j == 1)
		   {
			 muwgrade = tLMUWSchema.getUWGrade();
		   }
		   else
		   {
			 String tuwgrade = tLMUWSchema.getUWGrade();
			 if(muwgrade.compareTo(tuwgrade) < 0)
			 {
			   muwgrade = tuwgrade;
			 }
		   }
		 }
	   }
	
	   //需要人工核保时候，校验核保返回核保员核保级别
	   if(mLMUWSet.size()>0&&m2LMUWSet.size()>0)
	   {
		 for(int k = 1;k <= m2LMUWSet.size();k++)
		 {
		   LMUWSchema t2LMUWSchema = new LMUWSchema();
		   t2LMUWSchema = m2LMUWSet.get(k);
		   mCalCode = t2LMUWSchema.getCalCode();
	
		   String tempuwgrade = checkRiskAmnt();
		   if(tempuwgrade != null)
		   {
			 if(muwgrade==null || muwgrade.compareTo(tempuwgrade) < 0)//当需要人工核保时候当即mLMUWSet.size()>0时,muwgrade应该不为null,否则是自动核保规则中核保级别字段缺少了数据
			 {
			   muwgrade = tempuwgrade;
			 }
		   }
		 }
	   }
	   else//当所有的自动核保不成功规则均不与该投保单匹配时核保级别会为空,但一旦要进行核保订正会出现无核保级别的异常保错.所以给所有无核保级别的投保单一个最低默认级别
	   {
		 if(muwgrade == null||muwgrade.equals(""))
		 {
		   muwgrade="A";
		 }
	
	   }
	   logger.debug("匹配数:"+mLMUWSet.size()+"级别计算:"+m2LMUWSet.size()+"级别:"+muwgrade) ;
	   if (mpassflag.equals("0") == true)
	   {
		 mpassflag = "9";
	   }
	
	   if (mpassflag2.equals("1"))//有需要人工核保的附加险,则主险投保单必须经过人工核保
	   {
		 mpassflag = "5";
		 if(muwgrade.compareTo(muwgrade2)<0 )//主险级别取自己与其附加险的核保级别中的最高值
		muwgrade = muwgrade2;
	   }
	
	   logger.debug("匹配数:"+mLMUWSet.size()+"级别计算:"+m2LMUWSet.size()+"级别:"+muwgrade) ;
	
	
	 }
	
	 if (dealOnePol() == false)
	   return false;
	
	 return true;
  }

  /**
   * 根据保额校验核保级别
   * @return
   */
  private String checkRiskAmnt()
  {
	String tUWGrade = "";
	// 计算
	Calculator mCalculator = new Calculator();
	mCalculator.setCalCode( mCalCode );

	//增加基本要素
	mCalculator.addBasicFactor("Get", mCalBase.getGet() );
	mCalculator.addBasicFactor("Mult", mCalBase.getMult() );
	mCalculator.addBasicFactor("Prem", mCalBase.getPrem() );
	//mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv() );
	//mCalculator.addBasicFactor("GetIntv", mCalBase.getGetIntv() );
	mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge() );
	mCalculator.addBasicFactor("Sex", mCalBase.getSex() );
	mCalculator.addBasicFactor("Job", mCalBase.getJob() );
	mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear() );
	mCalculator.addBasicFactor("GetStartDate", "");
	//mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear() );
	mCalculator.addBasicFactor("Years", mCalBase.getYears() );
	mCalculator.addBasicFactor("Grp","");
	mCalculator.addBasicFactor("GetFlag","");
	mCalculator.addBasicFactor("ValiDate","");
	mCalculator.addBasicFactor("Count", mCalBase.getCount() );
	mCalculator.addBasicFactor("FirstPayDate","");
	//mCalculator.addBasicFactor("AddRate", mCalBase.getAddRate() );
	//mCalculator.addBasicFactor("GDuty", mCalBase.getGDuty() );
	mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo() );
	mCalculator.addBasicFactor("InsuredNo",mLCPolSchema.getInsuredNo());;
	PrepareBOMXBUWBL PrepareBOMXBUWBL=new PrepareBOMXBUWBL();
	mBomList=PrepareBOMXBUWBL.dealData(mLCPolSchema);
	mCalculator.setBOMList(mBomList);
	String tStr = "";
	tStr = mCalculator.calculate() ;
	if (tStr.trim().equals(""))
          tUWGrade = "";
    else
          tUWGrade = tStr.trim();

 logger.debug("AmntGrade:"+tUWGrade);

 return tUWGrade;
  }

  /**
   * 操作一张保单的业务处理
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean dealOnePol()
  {
	LCRnewStateHistorySchema tLCRnewStateHistorySchema=null;
	// 保单
	if (preparePol() == false)
    return false;

	 // 核保信息
	 if (prepareUW() == false)
	   return false;
	
	 //自动核保不通过时进入续保人工核保工作流承中
	 if (mpassflag.equals("5")){
	   if (prepareWorkFlowData() == false)
		return false;
	 }
	 if(mpassflag.equals("5"))
	 {
	   tLCRnewStateHistorySchema = new LCRnewStateHistorySchema();
	   LCRnewStateHistoryDB tLCRnewStateHistoryDB = new LCRnewStateHistoryDB();
	   LCRnewStateHistorySet tLCRnewStateHistorySet = new LCRnewStateHistorySet();
	   tLCRnewStateHistoryDB.setProposalNo(mLCPolSchema.getPolNo() );
	   tLCRnewStateHistorySet = tLCRnewStateHistoryDB.query() ;
	   if(tLCRnewStateHistorySet==null ||tLCRnewStateHistorySet.size() !=1)
	   {
		 return false;
	   }
	   tLCRnewStateHistorySchema=tLCRnewStateHistorySet.get(1) ;
	   tLCRnewStateHistorySchema.setModifyDate(CurrentDate) ;
	   tLCRnewStateHistorySchema.setState("2") ;
	 }
	 if(mpassflag.equals("9"))
	 {
	   tLCRnewStateHistorySchema = new LCRnewStateHistorySchema();
	   LCRnewStateHistoryDB tLCRnewStateHistoryDB = new LCRnewStateHistoryDB();
	   LCRnewStateHistorySet tLCRnewStateHistorySet = new LCRnewStateHistorySet();
	   tLCRnewStateHistoryDB.setProposalNo(mLCPolSchema.getPolNo() );
	   tLCRnewStateHistorySet = tLCRnewStateHistoryDB.query() ;
	   if(tLCRnewStateHistorySet==null ||tLCRnewStateHistorySet.size() !=1)
	   {
		 return false;
	   }
	   tLCRnewStateHistorySchema=tLCRnewStateHistorySet.get(1) ;
	   tLCRnewStateHistorySchema.setModifyDate(CurrentDate) ;
	   tLCRnewStateHistorySchema.setState("3") ;
	 }
	
	
	 LCPolSchema tLCPolSchema = new LCPolSchema();
	 tLCPolSchema.setSchema( mLCPolSchema );
	 mAllLCPolSet.add( tLCPolSchema );
	
	 LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	 tLCUWMasterSet.set( mLCUWMasterSet );
	 mAllLCUWMasterSet.add( tLCUWMasterSet );
	
	 LCUWSubSet tLCUWSubSet = new LCUWSubSet();
	 tLCUWSubSet.set( mLCUWSubSet );
	 mAllLCUWSubSet.add( tLCUWSubSet );
	
	 LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
	 tLCUWErrorSet.set( mLCUWErrorSet );
	 mAllLCErrSet.add( tLCUWErrorSet );
	 
	 RnewIndUWMasterSet tRnewIndUWMasterSet = new RnewIndUWMasterSet();
	 tRnewIndUWMasterSet.set( mRnewIndUWMasterSet );
	 mAllRnewIndUWMasterSet.add( tRnewIndUWMasterSet );
	
	 RnewIndUWSubSet tRnewIndUWSubSet = new RnewIndUWSubSet();
	 tRnewIndUWSubSet.set( mRnewIndUWSubSet );
	 mAllRnewIndUWSubSet.add( tRnewIndUWSubSet );
	
	 RnewIndUWErrorSet tRnewIndUWErrorSet = new RnewIndUWErrorSet();
	 tRnewIndUWErrorSet.set( mRnewIndUWErrorSet );
	 mAllRnewIndUWErrorSet.add( tRnewIndUWErrorSet );
	
	 if( tLCRnewStateHistorySchema != null  && !tLCRnewStateHistorySchema.getProposalNo().equals("") )
	 {
	   mLCRnewStateHistorySet.add(tLCRnewStateHistorySchema) ;
	 }
	
	 return true;
  }

  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
	mOperate = tGlobalInput.Operator;
	mGlobalInput = ((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));

	mmLCPolSet.set((LCPolSet)cInputData.getObjectByObjectName("LCPolSet",0));
	int n = mmLCPolSet.size();
	int flag = 0; //怕判断是不是所有保单都失败
	int j = 0; //符合条件保单个数


	for (int i = 1; i <= n; i++)
	{
	  LCPolDB tLCPolDB = new LCPolDB();
	  LCPolSchema tLCPolSchema = mmLCPolSet.get(i);
	  tLCPolDB.setPolNo( tLCPolSchema.getPolNo());
	  logger.debug("--BL--Pol--"+tLCPolSchema.getPolNo());
	  String temp = tLCPolSchema.getPolNo();
	  logger.debug("temp"+temp);
	  if (tLCPolDB.getInfo() == false)
	  {
		// @@错误处理
		this.mErrors.copyAllErrors( tLCPolDB.mErrors );
		CError tError = new CError();
		tError.moduleName = "PRnewUWAutoChkBL";
		tError.functionName = "getInputData";
		tError.errorMessage = temp+"投保单查询失败!";
		this.mErrors .addOneError(tError) ;
		return false;
	  }
	  else
	  {
		flag = 1;
		j++;
		tLCPolSchema.setSchema( tLCPolDB );
		mLCPolSet.add(tLCPolSchema);
	  }

	}

	if (flag == 0)
	{
	  return false;
	}
	else
	{
	  return true;
	}
  }

  /**
   * 校验投保单是否复核
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean checkApprove(LCPolSchema tLCPolSchema)
  {
//	if (tLCPolSchema.getApproveCode() == null || tLCPolSchema.getApproveDate() == null)
//	{
//	  // @@错误处理
//	  CError tError = new CError();
//	  tError.moduleName = "PRnewUWAutoChkBL";
//	  tError.functionName = "checkApprove";
//	  tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号：" + tLCPolSchema.getPolNo().trim() + "）";
//	  this.mErrors .addOneError(tError) ;
//	  return false;
//	}
	return true;
  }

  /**
   * 校验核保员级别
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean checkUWGrade()
  {
	LDUserDB tLDUserDB = new LDUserDB();
	tLDUserDB.setUserCode(mOperate);

	if(!tLDUserDB.getInfo())
	{
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAutoChkBL";
	  tError.functionName = "checkUWGrade";
	  tError.errorMessage = "无此操作员信息，不能核保!（操作员：" + mOperate + "）";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}

	String tUWPopedom = tLDUserDB.getUWPopedom();
	if (tUWPopedom==null || "".equals(tUWPopedom))
	{
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAutoChkBL";
	  tError.functionName = "checkUWGrade";
	  tError.errorMessage = "操作员无核保权限，不能核保!（操作员：" + mOperate + "）";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}

	return true;
  }



  /**
   * 校验附险是否全部自动核保
   */
  private boolean checkMain()
  {
	String tflag = "0";
	mpassflag2 = "0";//级别附加险是否通过标志
	muwgrade2 = "A";
	if(mLCPolSchema.getMainPolNo().equals(mLCPolSchema.getPolNo()))
	{
	  String tsql = "select * from lcpol where mainpolno = '?mPolNo?' and polno <> mainpolno";
	  SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	  sqlbv.sql(tsql);
	  sqlbv.put("mPolNo", mPolNo);
	  LCPolDB tLCPolDB = new LCPolDB();
	  LCPolSet tLCPolSet = new LCPolSet();

	  tLCPolSet = tLCPolDB.executeQuery(sqlbv);

	  if(tLCPolSet.size()>0)
	  {
		for(int i = 1;i<=tLCPolSet.size();i++)
		{
		  LCPolSchema tLCPolSchema = new LCPolSchema();

		  tLCPolSchema = tLCPolSet.get(i);
		  if(tLCPolSchema.getUWFlag().equals("0"))//有未进行自动核保的附加险
		  {
			tflag = "1";
		  }

		  if(tLCPolSchema.getUWFlag().equals("5"))//有需要人工核保的附加险
		  {
			mpassflag2 = "1";
			String tSql = "select * from LCUWMaster where proposalno = '?proposalno?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("proposalno", tLCPolSchema.getProposalNo());
			LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
			LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
			if(tLCUWMasterSet.size()== 1)
			{
			  LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
			  tLCUWMasterSchema = (LCUWMasterSchema)tLCUWMasterSet.get(1);
			  if(tLCUWMasterSchema.getAppGrade() != null && muwgrade2.compareTo(tLCUWMasterSchema.getAppGrade())<0)
			  {
				//获取附加险核保级别中的最高级别
				muwgrade2 = tLCUWMasterSchema.getAppGrade();
			  }
			}
		  }
		}
	  }
	}

	if(tflag.equals("1"))
	{
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAutoChkBL";
	  tError.functionName = "checkMain";
	  tError.errorMessage = "该主险投保单"+mPolNo+"有附加险尚未核保！";
	  this.mErrors .addOneError(tError);
	  return false;
	}
	return true;
  }
  /**
   * 校验投保单是否核保
   * 输出：如果发生错误则返回false,否则返回true
   */
 /*whn
 private boolean checkUW(LCPolSchema tLCPolSchema)
 {
	if (tLCPolSchema.getUWCode() != null || tLCPolSchema.getUWDate() != null)
	{
	// @@错误处理
   CError tError = new CError();
   tError.moduleName = "UWAutoChkBL";
   tError.functionName = "checkApprove";
   tError.errorMessage = "投保单已经自动核保，请订正后重新再核!（投保单号：" + tLCPolSchema.getPolNo().trim() + "）";
   this.mErrors .addOneError(tError) ;
   return false;
  }
  return true;
 }
 whn*/

/**
 * 核保险种信息校验,准备核保算法
 * 输出：如果发生错误则返回false,否则返回true
 */
  private boolean CheckKinds(LCPolSchema tLCPolSchema)
  {
	String tsql = "";
	mLMUWSet.clear();
	LMUWSchema tLMUWSchema = new LMUWSchema();
	//查询算法编码
	tsql = "select * from lmuw where riskcode = '000000' and relapoltype = 'I' and uwtype = '3' order by calcode";
	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	sqlbv2.sql(tsql);
	//tLMUWSchema.setRiskCode(tLCPolSchema.getRiskCode().trim());
	//tLMUWSchema.setRelaPolType("I");
	//tLMUWSchema.setUWType("1");
	logger.debug(tsql) ;
	LMUWDB tLMUWDB = new LMUWDB();
	//tLMUWDB.setSchema(tLMUWSchema);

	//LMUWDBSet tLMUWDBSet = new LMUWDBSet();
	mmLMUWSet = tLMUWDB.executeQuery(sqlbv2);
	mLMUWSet = tLMUWDB.executeQuery(sqlbv2);
	if (tLMUWDB.mErrors.needDealError() == true)
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors(tLMUWDB.mErrors);
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAutoChkBL";
	  tError.functionName = "CheckKinds";
	  tError.errorMessage = tLCPolSchema.getRiskCode().trim()+"险种信息查询失败!";
	  this.mErrors.addOneError(tError);
	  mLMUWSet.clear();
	  return false;
	}
	return true;
  }

  /**
   * 核保险种信息校验,准备核保算法
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean CheckKinds2(LCPolSchema tLCPolSchema)
  {
	String tsql = "";
	mLMUWSet.clear();
	LMUWSchema tLMUWSchema = new LMUWSchema();
	//查询算法编码
	//tsql = "select * from lmuw where riskcode in ('"+tLCPolSchema.getRiskCode().trim()+"','100000') and relapoltype = 'I' and uwtype = '1'";
	tsql = "select * from lmuw where riskcode = '000000' and relapoltype = 'I' and uwtype = '12'";
	SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	sqlbv3.sql(tsql);

	LMUWDB tLMUWDB = new LMUWDB();

	m2LMUWSet = tLMUWDB.executeQuery(sqlbv3);
	if (tLMUWDB.mErrors.needDealError() == true)
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors(tLMUWDB.mErrors);
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAutoChkBL";
	  tError.functionName = "CheckKinds2";
	  tError.errorMessage = tLCPolSchema.getRiskCode().trim()+"险种信息查询失败!";
	  this.mErrors.addOneError(tError);
	  m2LMUWSet.clear();
	  return false;
	}
	return true;
  }


  /**
   * 个人单核保数据准备
   * 输出：如果发生错误则返回false,否则返回true
   */
  private void CheckPolInit(LCPolSchema tLCPolSchema)
  {
	mCalBase = new CalBase();
	mCalBase.setPrem( tLCPolSchema.getPrem() );
	mCalBase.setGet( tLCPolSchema.getAmnt() );
	mCalBase.setMult( tLCPolSchema.getMult() );
	//mCalBase.setYears( tLCPolSchema.getYears() );
	mCalBase.setAppAge( tLCPolSchema.getInsuredAppAge() );
	mCalBase.setSex( tLCPolSchema.getInsuredSex() );
	mCalBase.setJob( tLCPolSchema.getOccupationType() );
	mCalBase.setCount( tLCPolSchema.getInsuredPeoples() );
	mCalBase.setPolNo( tLCPolSchema.getPolNo() );
	mCalBase.setStandbyFlag1( mStartDate );
	mCalBase.setStandbyFlag2(mEndDate);
  }

  /**
   * 个人单核保
   * 输出：如果发生错误则返回false,否则返回true
   */
  private double CheckPol()
  {
	// 计算
	Calculator mCalculator = new Calculator();
	mCalculator.setCalCode( mCalCode );
	//增加基本要素
	mCalculator.addBasicFactor("Get", mCalBase.getGet() );
	mCalculator.addBasicFactor("Mult", mCalBase.getMult() );
	mCalculator.addBasicFactor("Prem", mCalBase.getPrem() );
	//mCalculator.addBasicFactor("PayIntv", mCalBase.getPayIntv() );
	//mCalculator.addBasicFactor("GetIntv", mCalBase.getGetIntv() );
	mCalculator.addBasicFactor("AppAge", mCalBase.getAppAge() );
	mCalculator.addBasicFactor("Sex", mCalBase.getSex() );
	mCalculator.addBasicFactor("Job", mCalBase.getJob() );
	mCalculator.addBasicFactor("PayEndYear", mCalBase.getPayEndYear() );
	mCalculator.addBasicFactor("GetStartDate", "");
	//mCalculator.addBasicFactor("GetYear", mCalBase.getGetYear() );
	mCalculator.addBasicFactor("Years", mCalBase.getYears() );
	mCalculator.addBasicFactor("Grp","");
	mCalculator.addBasicFactor("GetFlag","");
	mCalculator.addBasicFactor("ValiDate","");
	mCalculator.addBasicFactor("Count", mCalBase.getCount() );
	mCalculator.addBasicFactor("FirstPayDate","");
	//mCalculator.addBasicFactor("AddRate", mCalBase.getAddRate() );
	//mCalculator.addBasicFactor("GDuty", mCalBase.getGDuty() );
	mCalculator.addBasicFactor("PolNo", mCalBase.getPolNo() );
	mCalculator.addBasicFactor("OldPolNo", mOldPolNo);
	mCalculator.addBasicFactor("ContNo", mLCPolSchema.getContNo() );
	mCalculator.addBasicFactor("InsuredNo",mLCPolSchema.getInsuredNo());
	mCalculator.addBasicFactor("RiskCode",mLCPolSchema.getRiskCode());
	mCalculator.addBasicFactor("StartDate",mStartDate);
	mCalculator.addBasicFactor("EndDate",mEndDate);
	String tStr = "";
	PrepareBOMXBUWBL PrepareBOMXBUWBL=new PrepareBOMXBUWBL();
	mBomList=PrepareBOMXBUWBL.dealData(mLCPolSchema);
	mCalculator.setBOMList(mBomList);
	tStr = mCalculator.calculate() ;
	if (tStr == null || tStr.trim().equals(""))
   mValue = 0;
 else
   mValue = Double.parseDouble( tStr );

 logger.debug(mValue);
 return mValue;
  }


  /**
   * 准备保单信息
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean preparePol()
  {
	logger.debug("核保标志"+mpassflag);
	mLCPolSchema.setUWFlag(mpassflag); //待人工核保
	mLCPolSchema.setUWCode(mOperate);
	mLCPolSchema.setUWDate(PubFun.getCurrentDate());

	return true;
  }

  /**
   * 准备核保信息
   * 输出：如果发生错误则返回false,否则返回true
   */
  private boolean prepareUW()
  {
	LCContDB tLCContDB = new LCContDB();
	LCContSchema tLCContSchema = new LCContSchema();
	tLCContDB.setContNo(mLCPolSchema.getContNo());
	tLCContSchema=tLCContDB.query().get(1);
	
	int tuwno = 0;
	LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
	LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
	tLCUWMasterDB.setProposalNo( mPolNo );
	tLCUWMasterDB.setUWType("1");//续保二核
	LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
	tLCUWMasterSet = tLCUWMasterDB.query();
	if (tLCUWMasterDB.mErrors.needDealError())
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tLCUWMasterDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"个人核保总表取数失败!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}



	int n = tLCUWMasterSet.size();
	if (n == 0)
	{
	  
	  tLCUWMasterSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
	  tLCUWMasterSchema.setContNo(mLCPolSchema.getContNo());
	  tLCUWMasterSchema.setProposalContNo(tLCContSchema.getProposalContNo());
	  tLCUWMasterSchema.setPolNo( mOldPolNo );
	  tLCUWMasterSchema.setProposalNo( mPolNo);
	  tLCUWMasterSchema.setUWNo(1);
	  
	  tLCUWMasterSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
	  tLCUWMasterSchema.setInsuredName(mLCPolSchema.getInsuredName());
	  tLCUWMasterSchema.setAppntNo(mLCPolSchema.getAppntNo());
	  tLCUWMasterSchema.setAppntName(mLCPolSchema.getAppntName());
	  tLCUWMasterSchema.setAgentCode(mLCPolSchema.getAgentCode());
	  tLCUWMasterSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
	  tLCUWMasterSchema.setPassFlag(mpassflag); //通过标志
	  tLCUWMasterSchema.setUWGrade(muwgrade); //核保级别
	  tLCUWMasterSchema.setAppGrade(muwgrade); //申报级别
	  tLCUWMasterSchema.setPostponeDay("");
	  tLCUWMasterSchema.setPostponeDate("");
	  tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
	  tLCUWMasterSchema.setState(mpassflag);
	  tLCUWMasterSchema.setHealthFlag("0");
	  tLCUWMasterSchema.setSpecFlag("0");
	  tLCUWMasterSchema.setQuesFlag("0");
	  tLCUWMasterSchema.setReportFlag("0");
	  tLCUWMasterSchema.setChangePolFlag("0");
	  tLCUWMasterSchema.setPrintFlag("0");
	  tLCUWMasterSchema.setManageCom(mLCPolSchema.getManageCom());
	  tLCUWMasterSchema.setUWIdea("");
	  tLCUWMasterSchema.setUpReportContent("");
	  tLCUWMasterSchema.setBatchNo("1"); //续保只会有一次自核
	  tLCUWMasterSchema.setUWType("1");//续保二核
	  tLCUWMasterSchema.setOperator(mOperate); //操作员
	  tLCUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
	  tLCUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
	  tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
	  tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
	}
	else if (n == 1)
	{

	  tLCUWMasterSchema = tLCUWMasterSet.get(1);

	  tuwno = tLCUWMasterSchema.getUWNo();
	  tuwno = tuwno + 1;

	  tLCUWMasterSchema.setUWNo(tuwno);
	  tLCUWMasterSchema.setPassFlag(mpassflag); //通过标志
	  tLCUWMasterSchema.setState(mpassflag);
	  tLCUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
	  tLCUWMasterSchema.setUWGrade(muwgrade); //核保级别
	  tLCUWMasterSchema.setAppGrade(muwgrade); //申报级别
	  tLCUWMasterSchema.setOperator(mOperate); //操作员
	  tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
	  tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
	}
	else
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tLCUWMasterDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"个人核保总表取数据不唯一!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}

	mLCUWMasterSet.clear();
	mLCUWMasterSet.add(tLCUWMasterSchema);


	// 核保轨迹表
	LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
	LCUWSubDB tLCUWSubDB = new LCUWSubDB();
	tLCUWSubDB.setProposalNo( mPolNo );
	tLCUWSubDB.setUWType("1");
	LCUWSubSet tLCUWSubSet = new LCUWSubSet();
	tLCUWSubSet = tLCUWSubDB.query();
	if (tLCUWSubDB.mErrors.needDealError())
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tLCUWSubDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"个人核保轨迹表查失败!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}

	int m = tLCUWSubSet.size();
	if (m > 0)
	{
	  m++; //核保次数
	  tLCUWSubSchema = tLCUWSubSet.get(1);

	  tLCUWSubSchema.setUWNo(m); //第几次核保

	  tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
	  tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
	  tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema.getProposalContNo());
	  tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo()) ;
	  tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo()) ;

	  tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); //核保级别
	  tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); //申请级别
	  tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
	  tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
	  tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
	  tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); //操作员
	  tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom()) ;
	  tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate()) ;
	  tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime()) ;
	  tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate() );
	  tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());


	}
	else
	{
	  tLCUWSubSchema.setProposalNo( mPolNo );
	  tLCUWSubSchema.setUWNo(1);
	  tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
	  tLCUWSubSchema.setContNo(tLCUWMasterSchema.getContNo());
	  tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema.getProposalContNo());  
	  tLCUWSubSchema.setPolNo(tLCUWMasterSchema.getPolNo()) ;
	  
	  tLCUWSubSchema.setManageCom(mLCPolSchema.getManageCom());
	  tLCUWSubSchema.setUWGrade(muwgrade); //核保级别
	  tLCUWSubSchema.setAppGrade(muwgrade); //申请级别
	  tLCUWSubSchema.setPostponeDay("");
	  tLCUWSubSchema.setPostponeDate("");
	  tLCUWSubSchema.setAutoUWFlag("1");
	  tLCUWSubSchema.setUWIdea("");
	  tLCUWSubSchema.setUpReportContent("");
	  tLCUWSubSchema.setState(mpassflag);
	  tLCUWSubSchema.setUWType("1");
	  tLCUWSubSchema.setOperator(mOperate); //操作员
	  tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
	  tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
	  tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
	  tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
	}

	mLCUWSubSet.clear();
	mLCUWSubSet.add(tLCUWSubSchema);


	// 核保错误信息表
	LCUWErrorSchema tLCUWErrorSchema = new LCUWErrorSchema();
	LCUWErrorDB tLCUWErrorDB = new LCUWErrorDB();
	tLCUWErrorDB.setProposalNo( mPolNo );
	tLCUWErrorDB.setUWType("1");
	LCUWErrorSet tLCUWErrorSet = new LCUWErrorSet();
	tLCUWErrorSet = tLCUWErrorDB.query();
	if (tLCUWErrorDB.mErrors.needDealError())
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tLCUWErrorDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"个人错误信息表查询失败!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}


	tLCUWErrorSchema.setSerialNo("0");
	if (m>0)
	  {
		tLCUWErrorSchema.setUWNo(m);
	  }
	else
	  {
		tLCUWErrorSchema.setUWNo(1);
	  }
	tLCUWErrorSchema.setPolNo(mOldPolNo);
	tLCUWErrorSchema.setProposalNo(mPolNo);
	tLCUWErrorSchema.setContNo(mLCPolSchema.getContNo());
	tLCUWErrorSchema.setProposalContNo(tLCContSchema.getProposalContNo());
	tLCUWErrorSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
	tLCUWErrorSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
	tLCUWErrorSchema.setInsuredName(mLCPolSchema.getInsuredName());
    tLCUWErrorSchema.setAppntNo(mLCPolSchema.getAppntNo());
    tLCUWErrorSchema.setAppntName(mLCPolSchema.getAppntName());
    tLCUWErrorSchema.setManageCom(mLCPolSchema.getManageCom());
    tLCUWErrorSchema.setUWRuleCode(""); //核保规则编码
    tLCUWErrorSchema.setUWError(""); //核保出错信息
    tLCUWErrorSchema.setCurrValue(""); //当前值
    tLCUWErrorSchema.setUWType("1");
    tLCUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
    tLCUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
    tLCUWErrorSchema.setUWPassFlag(mpassflag);

  //}

    //取核保错误信息
    mLCUWErrorSet.clear();
    merrcount = mLMUWSet.size();
    if ( merrcount >0 )
	 {
		for (int i = 1;i<=merrcount;i++)
		{
		  //取出错信息
		  LMUWSchema tLMUWSchema = new LMUWSchema();
		  tLMUWSchema = mLMUWSet.get(i);
		  //生成流水号
		  String tserialno = ""+ i;
	
		  tLCUWErrorSchema.setSerialNo( tserialno );
		  tLCUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); //核保规则编码
		  tLCUWErrorSchema.setUWError(tLMUWSchema.getRemark()); //核保出错信息
		  tLCUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
		  tLCUWErrorSchema.setCurrValue(""); //当前值
	
		  LCUWErrorSchema ttLCUWErrorSchema = new LCUWErrorSchema();
		  ttLCUWErrorSchema.setSchema(tLCUWErrorSchema);
		  mLCUWErrorSet.add(ttLCUWErrorSchema);
		}
	  }
    //然后处理 个单被保人核保最近结果表（RnewIndUWMaster）系列表
    //目前个险处理的一个合同下的险种只会有一个被保人。
    RnewIndUWMasterSchema tRnewIndUWMasterSchema = new RnewIndUWMasterSchema();
    RnewIndUWMasterDB tRnewIndUWMasterDB = new RnewIndUWMasterDB();
    
	tRnewIndUWMasterDB.setContNo(tLCContSchema.getContNo());
	tRnewIndUWMasterDB.setInsuredNo(tLCContSchema.getInsuredNo());
	tRnewIndUWMasterDB.setUWType("1");
	
	RnewIndUWMasterSet tRnewIndUWMasterSet = new RnewIndUWMasterSet();
	tRnewIndUWMasterSet = tRnewIndUWMasterDB.query();
	if (tRnewIndUWMasterDB.mErrors.needDealError())
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tRnewIndUWMasterDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"个人核保总表取数失败!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}



	int x = tRnewIndUWMasterSet.size();
	if (x == 0)
	{	  
	  tRnewIndUWMasterSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
	  tRnewIndUWMasterSchema.setContNo(mLCPolSchema.getContNo());
	  tRnewIndUWMasterSchema.setProposalContNo(tLCContSchema.getProposalContNo());
	  tRnewIndUWMasterSchema.setUWNo(1);
	  
	  tRnewIndUWMasterSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
	  tRnewIndUWMasterSchema.setInsuredName(mLCPolSchema.getInsuredName());
	  tRnewIndUWMasterSchema.setAppntNo(mLCPolSchema.getAppntNo());
	  tRnewIndUWMasterSchema.setAppntName(mLCPolSchema.getAppntName());
	  
	  tRnewIndUWMasterSchema.setAgentCode(mLCPolSchema.getAgentCode());
	  tRnewIndUWMasterSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
	  tRnewIndUWMasterSchema.setPassFlag(mpassflag); //通过标志
	  tRnewIndUWMasterSchema.setUWGrade(muwgrade); //核保级别
	  tRnewIndUWMasterSchema.setAppGrade(muwgrade); //申报级别
	  tRnewIndUWMasterSchema.setPostponeDay("");
	  tRnewIndUWMasterSchema.setPostponeDate("");
	  tRnewIndUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
	  tRnewIndUWMasterSchema.setState(mpassflag);
	  
	  tRnewIndUWMasterSchema.setManageCom(mLCPolSchema.getManageCom());
	  tRnewIndUWMasterSchema.setUWIdea("");
	  tRnewIndUWMasterSchema.setUpReportContent("");
	  tRnewIndUWMasterSchema.setOperator(mOperate); //操作员
	  
	  tRnewIndUWMasterSchema.setHealthFlag("0");
	  tRnewIndUWMasterSchema.setSpecFlag("0");
	  tRnewIndUWMasterSchema.setQuesFlag("0");
	  tRnewIndUWMasterSchema.setReportFlag("0");
	  tRnewIndUWMasterSchema.setChangePolFlag("0");
	  tRnewIndUWMasterSchema.setPrintFlag("0");
	  tRnewIndUWMasterSchema.setUWType("1");
	  
	  tRnewIndUWMasterSchema.setMakeDate(PubFun.getCurrentDate());
	  tRnewIndUWMasterSchema.setMakeTime(PubFun.getCurrentTime());
	  tRnewIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
	  tRnewIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
	}
	else if (x == 1)
	{

	  tRnewIndUWMasterSchema = tRnewIndUWMasterSet.get(1);

	  int tindno = tRnewIndUWMasterSchema.getUWNo();
	  tindno = tindno + 1;

	  tRnewIndUWMasterSchema.setUWNo(tindno);
	  tRnewIndUWMasterSchema.setPassFlag(mpassflag); //通过标志
	  tRnewIndUWMasterSchema.setState(mpassflag);
	  tRnewIndUWMasterSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
	  tRnewIndUWMasterSchema.setUWGrade(muwgrade); //核保级别
	  tRnewIndUWMasterSchema.setAppGrade(muwgrade); //申报级别
	  tRnewIndUWMasterSchema.setOperator(mOperate); //操作员
	  tRnewIndUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
	  tRnewIndUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
	}
	else
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tRnewIndUWMasterDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"被保人核保总表取数据不唯一!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}

	mRnewIndUWMasterSet.clear();
	mRnewIndUWMasterSet.add(tRnewIndUWMasterSchema);


	// 被保人核保轨迹表
	RnewIndUWSubSchema tRnewIndUWSubSchema = new RnewIndUWSubSchema();
	RnewIndUWSubDB tRnewIndUWSubDB = new RnewIndUWSubDB();
	
	tRnewIndUWSubDB.setContNo( tLCContSchema.getContNo() );
	tRnewIndUWSubDB.setInsuredNo(mLCPolSchema.getInsuredNo());
	tRnewIndUWSubDB.setUWType("1");
	
	RnewIndUWSubSet tRnewIndUWSubSet = new RnewIndUWSubSet();
	tRnewIndUWSubSet = tRnewIndUWSubDB.query();
	if (tRnewIndUWSubDB.mErrors.needDealError())
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tRnewIndUWSubDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"个人核保轨迹表查失败!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}

	int mm = tRnewIndUWSubSet.size();
	if (mm > 0)
	{
	  mm++; //核保次数
	  tRnewIndUWSubSchema = tRnewIndUWSubSet.get(1);

	  tRnewIndUWSubSchema.setUWNo(mm); //第几次核保

	  tRnewIndUWSubSchema.setGrpContNo(tRnewIndUWMasterSchema.getGrpContNo());
	  tRnewIndUWSubSchema.setContNo(tRnewIndUWMasterSchema.getContNo());
	  tRnewIndUWSubSchema.setProposalContNo(tRnewIndUWMasterSchema.getProposalContNo());
	  
	  tRnewIndUWSubSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
	  tRnewIndUWSubSchema.setInsuredName(mLCPolSchema.getInsuredName());
	  tRnewIndUWSubSchema.setAppntNo(mLCPolSchema.getAppntNo());
	  tRnewIndUWSubSchema.setAppntName(mLCPolSchema.getAppntName());
	  
	  tRnewIndUWSubSchema.setAgentCode(mLCPolSchema.getAgentCode());
	  tRnewIndUWSubSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
	  tRnewIndUWSubSchema.setPassFlag(mpassflag); //通过标志
	  tRnewIndUWSubSchema.setUWGrade(muwgrade); //核保级别
	  tRnewIndUWSubSchema.setAppGrade(muwgrade); //申报级别
	  tRnewIndUWSubSchema.setPostponeDay("");
	  tRnewIndUWSubSchema.setPostponeDate("");
	  tRnewIndUWSubSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
	  tRnewIndUWSubSchema.setState(mpassflag);
	  
	  tRnewIndUWSubSchema.setManageCom(mLCPolSchema.getManageCom());
	  tRnewIndUWSubSchema.setUWIdea("");
	  tRnewIndUWSubSchema.setUpReportContent("");
	  tRnewIndUWSubSchema.setOperator(mOperate); //操作员
	  
	  tRnewIndUWSubSchema.setHealthFlag("0");
	  tRnewIndUWSubSchema.setSpecFlag("0");
	  tRnewIndUWSubSchema.setQuesFlag("0");
	  tRnewIndUWSubSchema.setReportFlag("0");
	  tRnewIndUWSubSchema.setChangePolFlag("0");
	  tRnewIndUWSubSchema.setPrintFlag("0");

	  tRnewIndUWSubSchema.setMakeDate(PubFun.getCurrentDate()) ;
	  tRnewIndUWSubSchema.setMakeTime(PubFun.getCurrentTime()) ;
	  tRnewIndUWSubSchema.setModifyDate(PubFun.getCurrentDate() );
	  tRnewIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());

	}
	else
	{
	  tRnewIndUWSubSchema.setUWNo(1);
	  tRnewIndUWSubSchema.setGrpContNo(tRnewIndUWMasterSchema.getGrpContNo());
	  tRnewIndUWSubSchema.setContNo(tRnewIndUWMasterSchema.getContNo());
	  tRnewIndUWSubSchema.setProposalContNo(tRnewIndUWMasterSchema.getProposalContNo());  
	  
	  tRnewIndUWSubSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
	  tRnewIndUWSubSchema.setInsuredName(mLCPolSchema.getInsuredName());
	  tRnewIndUWSubSchema.setAppntNo(mLCPolSchema.getAppntNo());
	  tRnewIndUWSubSchema.setAppntName(mLCPolSchema.getAppntName());
	  
	  tRnewIndUWSubSchema.setAgentCode(mLCPolSchema.getAgentCode());
	  tRnewIndUWSubSchema.setAgentGroup(mLCPolSchema.getAgentGroup());
	  tRnewIndUWSubSchema.setPassFlag(mpassflag); //通过标志
	  tRnewIndUWSubSchema.setUWGrade(muwgrade); //核保级别
	  tRnewIndUWSubSchema.setAppGrade(muwgrade); //申报级别
	  tRnewIndUWSubSchema.setPostponeDay("");
	  tRnewIndUWSubSchema.setPostponeDate("");
	  tRnewIndUWSubSchema.setAutoUWFlag("1"); // 1 自动核保 2 人工核保
	  tRnewIndUWSubSchema.setState(mpassflag);
	  
	  tRnewIndUWSubSchema.setManageCom(mLCPolSchema.getManageCom());
	  tRnewIndUWSubSchema.setUWIdea("");
	  tRnewIndUWSubSchema.setUpReportContent("");
	  tRnewIndUWSubSchema.setOperator(mOperate); //操作员
	  
	  tRnewIndUWSubSchema.setHealthFlag("0");
	  tRnewIndUWSubSchema.setSpecFlag("0");
	  tRnewIndUWSubSchema.setQuesFlag("0");
	  tRnewIndUWSubSchema.setReportFlag("0");
	  tRnewIndUWSubSchema.setChangePolFlag("0");
	  tRnewIndUWSubSchema.setPrintFlag("0");
	  tRnewIndUWSubSchema.setUWType("1");

	  tRnewIndUWSubSchema.setMakeDate(PubFun.getCurrentDate()) ;
	  tRnewIndUWSubSchema.setMakeTime(PubFun.getCurrentTime()) ;
	  tRnewIndUWSubSchema.setModifyDate(PubFun.getCurrentDate() );
	  tRnewIndUWSubSchema.setModifyTime(PubFun.getCurrentTime());
	}

	mRnewIndUWSubSet.clear();
	mRnewIndUWSubSet.add(tRnewIndUWSubSchema);


	// 核保错误信息表
	RnewIndUWErrorSchema tRnewIndUWErrorSchema = new RnewIndUWErrorSchema();
	RnewIndUWErrorDB tRnewIndUWErrorDB = new RnewIndUWErrorDB();
	
	tRnewIndUWErrorDB.setContNo( tLCContSchema.getContNo() );
	tRnewIndUWErrorDB.setInsuredNo(mLCPolSchema.getInsuredNo());
	tRnewIndUWErrorDB.setUWType("1");
	
	RnewIndUWErrorSet tRnewIndUWErrorSet = new RnewIndUWErrorSet();
	tRnewIndUWErrorSet = tRnewIndUWErrorDB.query();
	if (tRnewIndUWErrorDB.mErrors.needDealError())
	{
	  // @@错误处理
	  this.mErrors.copyAllErrors( tRnewIndUWErrorDB.mErrors );
	  CError tError = new CError();
	  tError.moduleName = "PRnewUWAtuoChkBL";
	  tError.functionName = "prepareUW";
	  tError.errorMessage = mOldPolNo+"个人错误信息表查询失败!";
	  this.mErrors .addOneError(tError) ;
	  return false;
	}

	String mserialno = "0";
	tRnewIndUWErrorSchema.setSerialNo(mserialno);  //具体的流水号会在下面的取核保错误信息中置上
	if (mm>0)
	  {
		tRnewIndUWErrorSchema.setUWNo(mm);
	  }
	else
	  {
		tRnewIndUWErrorSchema.setUWNo(1);
	  }

	tRnewIndUWErrorSchema.setContNo(mLCPolSchema.getContNo());
	tRnewIndUWErrorSchema.setProposalContNo(tLCContSchema.getProposalContNo());
	tRnewIndUWErrorSchema.setGrpContNo(mLCPolSchema.getGrpContNo());
	
	tRnewIndUWErrorSchema.setInsuredNo(mLCPolSchema.getInsuredNo());
	tRnewIndUWErrorSchema.setInsuredName(mLCPolSchema.getInsuredName());
	tRnewIndUWErrorSchema.setAppntNo(mLCPolSchema.getAppntNo());
	tRnewIndUWErrorSchema.setAppntName(mLCPolSchema.getAppntName());
	tRnewIndUWErrorSchema.setManageCom(mLCPolSchema.getManageCom());
    tRnewIndUWErrorSchema.setUWRuleCode(""); //核保规则编码
    tRnewIndUWErrorSchema.setUWError(""); //核保出错信息
    tRnewIndUWErrorSchema.setCurrValue(""); //当前值
    tRnewIndUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
    tRnewIndUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
    tRnewIndUWErrorSchema.setUWPassFlag(mpassflag);
    tRnewIndUWErrorSchema.setUWType("1");

  //}

    //取核保错误信息
    mRnewIndUWErrorSet.clear();
    merrcount = mLMUWSet.size();
    if ( merrcount >0 )
	 {
		for (int i = 1;i<=merrcount;i++)
		{
		  //取出错信息
		  LMUWSchema tLMUWSchema = new LMUWSchema();
		  tLMUWSchema = mLMUWSet.get(i);
		  //生成流水号
		  mserialno = PubFun1.CreateMaxNo("RnewIndUWError", 20);
	
		  tRnewIndUWErrorSchema.setSerialNo( mserialno );
		  tRnewIndUWErrorSchema.setUWRuleCode(tLMUWSchema.getUWCode()); //核保规则编码
		  tRnewIndUWErrorSchema.setUWError(tLMUWSchema.getRemark()); //核保出错信息
		  tRnewIndUWErrorSchema.setUWGrade(tLMUWSchema.getUWGrade());
		  tRnewIndUWErrorSchema.setCurrValue(""); //当前值
	
		  RnewIndUWErrorSchema ttRnewIndUWErrorSchema = new RnewIndUWErrorSchema();
		  ttRnewIndUWErrorSchema.setSchema(tRnewIndUWErrorSchema);
		  mRnewIndUWErrorSet.add(ttRnewIndUWErrorSchema);
		}
	  }
    return true;
 }


  /**
* 为自动核保后,需进入人工核保的保全准备转入工作流的数据
*/
private boolean prepareWorkFlowData()
{

 // 准备传输工作流数据 VData
 //校验保单信息
  LCPolDB tLCPolDB = new LCPolDB();
  LCPolSet tLCPolSet = new LCPolSet();
  String sql = "select * from LCPol " + " where polno='?polno?' " ;
  SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
  sqlbv4.sql(sql);
  sqlbv4.put("polno", mLCPolSchema.getPolNo());
  tLCPolSet = tLCPolDB.executeQuery(sqlbv4) ;
  if( tLCPolSet == null || tLCPolSet.size() != 1)
  {
	 CError tError = new CError();
	  tError.moduleName = "PRnewUWAutoChkBL";
	  tError.functionName = "prepareWorkFlowData";
	  tError.errorMessage = "保单"+mPolNo+"信息查询失败!";
	  this.mErrors .addOneError(tError) ;
	  return false;
  }

 LCPolSchema tLCPolSchema = new LCPolSchema();
 tLCPolSchema = tLCPolSet.get(1);

 LMRiskDB tLMRiskDB = new LMRiskDB();
 LMRiskSet tLMRiskSet = new LMRiskSet();
 tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode()) ;
 tLMRiskSet = tLMRiskDB.query() ;
 if( tLMRiskSet == null || tLMRiskSet.size()!=1)
  {
   CError tError = new CError();
   tError.moduleName = "PRnewUWAutoChkBL";
   tError.functionName = "prepareWorkFlowData";
   tError.errorMessage = "保单"+mPolNo+"险种信息查询失败!";
   this.mErrors .addOneError(tError) ;
   return false;
  }

  LMRiskSchema tLMRiskSchema = new LMRiskSchema();
  tLMRiskSchema = tLMRiskSet.get(1);
  tWorkFlowTransferData = new TransferData();
  tWorkFlowTransferData.setNameAndValue("ContNo",tLCPolSchema.getContNo() ) ;
  tWorkFlowTransferData.setNameAndValue("PrtNo",tLCPolSchema.getPrtNo() ) ;
  tWorkFlowTransferData.setNameAndValue("PolNo",tLCPolSchema.getPolNo());
  tWorkFlowTransferData.setNameAndValue("RiskCode",tLMRiskSchema.getRiskCode());
  tWorkFlowTransferData.setNameAndValue("RiskName",tLMRiskSchema.getRiskName());
  tWorkFlowTransferData.setNameAndValue("ManageCom",tLCPolSchema.getManageCom());
  tWorkFlowTransferData.setNameAndValue("AppntNo",tLCPolSchema.getAppntNo());
  tWorkFlowTransferData.setNameAndValue("AppntName",tLCPolSchema.getAppntName());
  tWorkFlowTransferData.setNameAndValue("InsuredNo",tLCPolSchema.getInsuredNo());
  tWorkFlowTransferData.setNameAndValue("InsuredName",tLCPolSchema.getInsuredName());
  tWorkFlowTransferData.setNameAndValue("Uw_State","1");  //核保状态 1未处理 2已回复 3未回复
  tWorkFlowTransferData.setNameAndValue("ApplyDate",this.CurrentDate);  //自核转人工核保(二核)提交日期
  tWorkFlowTransferData.setNameAndValue("Content",""); //核保员说明
  tWorkFlowTransferData.setNameAndValue("BackDate",""); //最后回复日期
  tWorkFlowTransferData.setNameAndValue("BackTime",""); //最后回复时间
  tWorkFlowVData = new VData();
  tWorkFlowVData.add(mGlobalInput);
  tWorkFlowVData.add(tWorkFlowTransferData);
  logger.debug("ok-prepareWorkFlowData") ;
  tWorkFlowTransferData.setNameAndValue("BusiType", "1004");
  tWorkFlowTransferData.setNameAndValue("ActivityID","0000007001");
  String busiName = "WorkFlowUI";
  BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
  //WorkFlowUI tWorkFlowUI  = new WorkFlowUI();
  if(! tBusinessDelegate.submitData(tWorkFlowVData, "create",busiName)){
	  	this.mErrors.copyAllErrors( tBusinessDelegate.getCErrors());
		return false;
  }
//  ActivityOperator tActivityOperator = new ActivityOperator();
//  //执行发放体检通知书虚拟任务(当产生任务与执行任务为同一事务时,采用执行虚拟任务模式工作)
//  try
//  {
//	//产生执行完发放体检通知书任务后的下一打印体检通知书任务节点
//	LWMissionSchema tLWMissionSchema = new LWMissionSchema();
//	if(!tActivityOperator.CreateStartMission("0000000007", "0000007001", tWorkFlowVData))
//	{
//		CError tError = new CError();
//		tError.moduleName = "PEdorManuUWWorkFlowBL";
//		tError.functionName = "Execute9999999999";
//		tError.errorMessage = "创建工作流起始节点失败！";
//		this.mErrors .addOneError(tError) ;
//		return false;
//	}
//	else
//	{
//		VData xResult = new VData();
//		xResult=tActivityOperator.getResult();  //获取工作流起始节点
//		xMMap = ((MMap)xResult.getObjectByObjectName("MMap",0));
//	}
//  }
//  catch (Exception ex)
//  {
//	// @@错误处理
//	//this.mErrors.copyAllErrors( mActivityOperator.mErrors );
//	CError tError = new CError();
//	tError.moduleName = "PEdorManuUWWorkFlowBL";
//	tError.functionName = "Execute9999999999";
//	tError.errorMessage = "工作流引擎工作出现异常!";
//	this.mErrors .addOneError(tError) ;
//	return false;
//  }


 return true;

}


  /**
   * 准备需要保存的数据
   */
  private void prepareOutputData()
  {
	//将核保信息加到包含工作流信息的map中，便于统一提交。
	if(mAllLCPolSet != null && mAllLCPolSet.size() >0)
	   xMMap.put(mAllLCPolSet, "UPDATE");
    if(mAllLCUWMasterSet != null && mAllLCUWMasterSet.size() >0)
       xMMap.put(mAllLCUWMasterSet, "DELETE&INSERT");
    if(mAllLCUWSubSet != null && mAllLCUWSubSet.size() >0)
       xMMap.put(mAllLCUWSubSet, "INSERT");
	if(mAllLCErrSet != null && mAllLCErrSet.size() >0)
	   xMMap.put(mAllLCErrSet, "INSERT");
	
	if(mAllRnewIndUWMasterSet != null && mAllRnewIndUWMasterSet.size() >0)
	   xMMap.put(mAllRnewIndUWMasterSet, "DELETE&INSERT");
    if(mAllRnewIndUWSubSet != null && mAllRnewIndUWSubSet.size() >0)
       xMMap.put(mAllRnewIndUWSubSet, "INSERT");
	if(mAllRnewIndUWErrorSet != null && mAllRnewIndUWErrorSet.size() >0)
	   xMMap.put(mAllRnewIndUWErrorSet, "INSERT");
	
	if(mLCRnewStateHistorySet!=null && mLCRnewStateHistorySet.size() >0)
	{
	   xMMap.put(mLCRnewStateHistorySet, "UPDATE");
	}

  }

}
