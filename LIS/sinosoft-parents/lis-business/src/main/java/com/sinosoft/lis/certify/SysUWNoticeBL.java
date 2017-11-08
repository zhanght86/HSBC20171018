package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class SysUWNoticeBL implements CertifyService {
private static Logger logger = Logger.getLogger(SysUWNoticeBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  private VData mResult = new VData();
  /** 往后面传输数据的容器 */
  private VData mInputData= new VData();
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  /** 数据操作字符串 */
  private String mOperate;
  /** 业务处理相关变量 */
  private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
  private LCUWMasterSet mLCUWMasterSet = new LCUWMasterSet();
  private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();

  public SysUWNoticeBL() {
  }

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   *         cOperate 数据操作
   * @return:
   */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;
    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData(cInputData))
      return false;
    //进行业务处理
    if (mOperate.equals("TakeBack")){
      if (!dealData()){
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "SysUWNoticeBL";
        tError.functionName = "submitData";
        tError.errorMessage = "数据处理失败SysUWNoticeBL-->dealData!";
        this.mErrors.addOneError(tError) ;
        return false;
      }
    }

    //准备往后台的数据
    if (!prepareOutputData())
      return false;

      logger.debug("Start SysUWNoticeBL Submit...");
      SysUWNoticeBLS tSysUWNoticeBLS=new SysUWNoticeBLS();
      tSysUWNoticeBLS.submitData(mInputData,mOperate);
      logger.debug("End SysUWNoticeBL Submit...");
      //如果有需要处理的错误，则返回
      if (tSysUWNoticeBLS.mErrors.needDealError())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tSysUWNoticeBLS.mErrors);
        CError tError = new CError();
        tError.moduleName = "SysUWNoticeBL";
        tError.functionName = "submitDat";
        tError.errorMessage ="数据提交失败!";
        this.mErrors.addOneError(tError) ;
        return false;
      }

    mInputData=null;
    return true;
  }
  /**
   * 根据前面的输入数据，进行BL逻辑处理
   * 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData()
  {
    String tPolNo = mLOPRTManagerSchema.getOtherNo();
    String tsql = "Select * from lcuwmaster where proposalno in (select proposalno from lcpol where mainpolno = (select mainpolno from lcpol where proposalno = '"+"?proposalno?"+"')) ";
    logger.debug(tsql);
    SQLwithBindVariables sqlbv = new SQLwithBindVariables() ;
    sqlbv.sql(tsql);
    sqlbv.put("proposalno", tPolNo);
    LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();
    LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
    tLCUWMasterSet = tLCUWMasterDB.executeQuery(sqlbv);
    if(tLCUWMasterSet.size() > 0)
    {
      for(int i = 1;i <= tLCUWMasterSet.size();i++)
      {
        LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();

        tLCUWMasterSchema = tLCUWMasterSet.get(i);

          //核保通知标记回复
		if(tLCUWMasterSchema.getSpecFlag()!=null)
		  {
		    if(tLCUWMasterSchema.getSpecFlag().equals("1"))
			     {
				      tLCUWMasterSchema.setSpecFlag("2");
				 }
				  //sxy---添加(2003-07-30)(以前是在打印核保通知书以后就置此标记,现改为回收核保通知书后再置
				  //(SpecFlag:0 ---非 1 ---是，但是没有回复 2 ---是，而且已经回复)
		  }
	    if(tLCUWMasterSchema.getChangePolFlag()!=null)
		  {
		    if(tLCUWMasterSchema.getChangePolFlag().equals("1"))
				  {
				    tLCUWMasterSchema.setChangePolFlag("2");
				  }
				  //sxy---将其注释(2003-07-30)(以前是在打印核保通知书以后就置此标记,现改为回收核保通知书后再置
				  //(ChangePolFlag:0 ---非 1 ---是，但是没有回复 2 ---是，而且已经回复)
          }
	  if((tLCUWMasterSchema.getPrintFlag().equals("1")||tLCUWMasterSchema.getPrintFlag().equals("2"))&&!tLCUWMasterSchema.getSpecFlag().equals("1")&&!tLCUWMasterSchema.getChangePolFlag().equals("1"))
          tLCUWMasterSchema.setPrintFlag("3");

          LCIssuePolSet tLCIssuePolSet = new LCIssuePolSet();
          LCIssuePolDB tLCIssuePolDB = new LCIssuePolDB();
          //tLCIssuePolDB.setProposalNo(tLCUWMasterSchema.getProposalNo());
          tLCIssuePolDB.setBackObjType("3");
          tLCIssuePolDB.setNeedPrint("Y");
          tLCIssuePolSet.set(tLCIssuePolDB.query());
          //如果有需要处理的错误，则返回
          if (tLCIssuePolDB.mErrors.needDealError()) {
            // @@错误处理
            mErrors.copyAllErrors(tLCIssuePolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "SysUWNoticeBL";
            tError.functionName = "DealData";
            tError.errorMessage ="数据查询失败!";
            mErrors.addOneError(tError) ;
            return false;
          }

          for (int j=1;j<=tLCIssuePolSet.size();j++)
          {
			LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
            tLCIssuePolSchema=tLCIssuePolSet.get(j);
            tLCIssuePolSchema.setReplyMan("SYS002");
            tLCIssuePolSchema.setReplyResult("自动回复");
            tLCIssuePolSchema.setNeedPrint("P");
			tLCIssuePolSchema.setModifyDate(PubFun.getCurrentDate());
			tLCIssuePolSchema.setModifyTime(PubFun.getCurrentTime());
            mLCIssuePolSet.add(tLCIssuePolSchema);
          }

          ExeSQL tExeSQL = new ExeSQL();
          String asql = "select count(1) from LCIssuePol where ProposalNo='" + "?ProposalNo?" + "' and ((backobjtype = '2' and needprint = 'Y') or (backobjtype = '4')) and ReplyResult is null";  //不考虑操作员问题件，对返回给业务员需要打印的还有返回给机构的问题件校验，判断是不是有问题件没有回复
          SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
          sqlbv1.sql(asql);
          sqlbv1.put("ProposalNo", tLCUWMasterSchema.getProposalNo());
          String tNumber = tExeSQL.getOneValue(sqlbv1);
          if (tNumber.equals("0"))
            tLCUWMasterSchema.setQuesFlag("2");
          mLCUWMasterSet.add(tLCUWMasterSchema);
      }
    }
//    else
//    {
//      tsql = "Select * from lcuwmaster where proposalno in (select proposalno from lcpol where mainpolno = (select mainpolno from lcpol where proposalno = '"+tPolNo+"'))";
//      logger.debug(tsql);
//      LCUWMasterDB ttLCUWMasterDB = new LCUWMasterDB();
//      LCUWMasterSet ttLCUWMasterSet = new LCUWMasterSet();
//
//      ttLCUWMasterSet = ttLCUWMasterDB.executeQuery(tsql);
//
//      for(int i = 1;i <= ttLCUWMasterSet.size();i++)
//      {
//        LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
//
//        tLCUWMasterSchema = ttLCUWMasterSet.get(i);
//
//        //核保通知标记回复
//        if((tLCUWMasterSchema.getPrintFlag().equals("1")&&!tLCUWMasterSchema.getSpecFlag().equals("1")&&!tLCUWMasterSchema.getChangePolFlag().equals("1"))||tLCUWMasterSchema.getPrintFlag().equals("3"))
//          tLCUWMasterSchema.setPrintFlag("2");
//
//        if(tLCUWMasterSchema.getQuesFlag().equals("1"))
//          tLCUWMasterSchema.setQuesFlag("2");
//
//        mLCUWMasterSet.add(tLCUWMasterSchema);
//      }
//    }
    return true;
  }




  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    this.mLOPRTManagerSchema.setSchema((LOPRTManagerSchema)cInputData.getObjectByObjectName("LOPRTManagerSchema",0));
    this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }

  private boolean prepareOutputData()
  {
    try
    {
      this.mInputData.clear();
      this.mInputData.add(this.mLCUWMasterSet);
      this.mInputData.add(this.mLCIssuePolSet);

      this.mResult.clear();
      this.mResult.add(this.mLCUWMasterSet);
      this.mResult.add(this.mLCIssuePolSet);
    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="SysUWNoticeBL";
      tError.functionName="prepareData";
      tError.errorMessage="在准备往后层处理所需要的数据时出错。";
      this.mErrors.addOneError(tError) ;
      return false;
    }
    return true;
  }
  public VData getResult()
  {
    return this.mResult;
  }
  public CErrors getErrors() {
    return mErrors;
  }
  public static void main(String[] args) {
	GlobalInput mGlobalInput=new GlobalInput();
	mGlobalInput.Operator="001" ;
	LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	mLOPRTManagerSchema.setOtherNo("86110020040110000395");
    VData tVData = new VData();
	tVData.add(mLOPRTManagerSchema) ;
	tVData.add(mGlobalInput) ;
    SysUWNoticeBL sysUWNoticeBL1 = new SysUWNoticeBL();
	sysUWNoticeBL1.submitData(tVData,"TakeBack") ;

  }
}
