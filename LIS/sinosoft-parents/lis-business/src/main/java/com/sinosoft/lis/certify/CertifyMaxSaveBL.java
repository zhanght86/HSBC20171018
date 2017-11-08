package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 立案业务逻辑保存处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 刘岩松
 * @version 1.0
 */
public class CertifyMaxSaveBL
{
private static Logger logger = Logger.getLogger(CertifyMaxSaveBL.class);

  public  CErrors mErrors = new CErrors();
  private VData mInputData ;
  private VData mResult = new VData();
  private String mOperate;
  private String mAgentCode = "";
  private String mManageCom = "";
  private String mAgentGrade = "";
  private String mOperateType = "";
  private String mQueryType = "";
  private String mUpdateType = "";
  private String transact = "";
  private LMCertifyDesSet mLMCertifyDesSet = new LMCertifyDesSet();//单证描述表
  private LDAgentCardCountSet mLDAgentCardCountSet = new LDAgentCardCountSet();//代理人最大领取表 //  private LLReportSet mLLReportSet = new LLReportSet();
  public CertifyMaxSaveBL() {}

  public boolean submitData(VData cInputData,String cOperate)
  {
    logger.debug("CertifyMaxSaveBL.java");
    mInputData = (VData)cInputData.clone();
    this.mOperate = cOperate;

    if (!getInputData(cInputData))
      return false;

    if (mOperate.equals("QUERY"))
    {
      if(!queryData())
        return false;
      logger.debug("Begin query data");
    }

    if (mOperate.equals("UPDATE"))
    {
      logger.debug("开始执行到修改的阶段！！！");
      if (!dealData())
        return false;
      logger.debug("Begin Updata Data");
    }
    return true;
  }
  public VData getResult()
  {
    return mResult;
  }
  private boolean getInputData(VData cInputData)
  {
    logger.debug("getinputdata（）");
    if (mOperate.equals("QUERY"))
    {
      logger.debug("Query()");
      mAgentCode   = (String)cInputData.get(0);
      mManageCom   = (String)cInputData.get(1);
      mAgentGrade  = (String)cInputData.get(2);
      mOperateType = (String)cInputData.get(3);
      mQueryType   = (String)cInputData.get(4);
      transact     = (String)cInputData.get(5);
      logger.debug("QueryType is "+mQueryType);
      logger.debug("QueryType is A means query data based on AgentCode "
                         +" and QueryType is M means query data based on ManageCom");
      logger.debug("transact is Q means operate --Query data");
      logger.debug("transact is U means operate --Update data");
    }
    if (mOperate.equals("UPDATE"))
    {
      logger.debug("开始执行到update的函数");
      mOperateType = (String)cInputData.get(0);
      mUpdateType = (String)cInputData.get(1);
      mLDAgentCardCountSet.set((LDAgentCardCountSet)cInputData.getObjectByObjectName("LDAgentCardCountSet",0));
      logger.debug("操作的类型是……修改还是查询"+mOperateType);
      logger.debug("修改的类型是……按照代理人还是按照管理机构");
    }
    return true;
  }

  private boolean queryData()
  {
    SSRS t_ssrs = new SSRS();
    ExeSQL t_exesql = new ExeSQL();
    String t_sql = "";
    //LMCertifyDes 单证描述表；
    //LDAgentCardCount代理人最大领取表
    if(mQueryType.equals("A"))
    {
      logger.debug("根据代理人进行查询");
      t_sql = "select LDAgentCardCount.CertifyCode,LMCertifyDes.CertifyName,LMCertifyDes.CertifyClass ,"+"'"+"?AgentCode?"+"'"+",LDAgentCardCount.MaxCount "
            +"from LMCertifyDes , LDAgentCardCount where LDAgentCardCount.CertifyCode = LMCertifyDes.CertifyCode and (LMCertifyDes.CertifyClass='D' or LMCertifyDes.CertifyClass = 'P' ) "
            +"and LDAgentCardCount.AgentCode = '"
            +"?AgentCode?"+"' order by LDAgentCardCount.AgentGrade,LMCertifyDes.CertifyClass,LDAgentCardCount.MaxCount";
    }
    if(mQueryType.equals("M"))
    {
      logger.debug("根据管理机构进行查询！！");
      t_sql = "Select LDAgentCardCount.AgentGrade,LMCertifyDes.CertifyName,LMCertifyDes.CertifyCode,LDAgentCardCount.ManageCom,LDAgentCardCount.MaxCount"
            +" From LMCertifyDes , LDAgentCardCount where LDAgentCardCount.CertifyCode = LMCertifyDes.CertifyCode and (LMCertifyDes.CertifyClass='D' or LMCertifyDes.CertifyClass = 'P' ) "
            +" and LDAgentCardCount.ManageCom like '"
            +"?ManageCom?"
            +"' and LDAgentCardCount.AgentGrade = '"
            +"?AgentGrade?"+"' order by LDAgentCardCount.AgentGrade,LMCertifyDes.CertifyClass,LDAgentCardCount.MaxCount";
    }
    SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    sqlbv.sql();
    sqlbv.put("AgentCode", mAgentCode);
    sqlbv.put("ManageCom", mManageCom);
    sqlbv.put("AgentGrade", mAgentGrade);
    t_ssrs = t_exesql.execSQL(sqlbv);
    if(t_ssrs.getMaxRow()==0)
    {
      CError tError = new CError();
      tError.moduleName = "CertifyMaxSaveBL";
      tError.functionName = "queryData";
      tError.errorMessage = "没有满足条件的信息！！！";
      this.mErrors.addOneError(tError);
      return false;
    }
    mResult.clear();
    mResult.add(t_ssrs);
    return true;
  }
  /**
   * 数据操作类业务处理
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */

  private boolean dealData()
  {
    QueryOldData();

    CertifyMaxSaveBLS tCertifyMaxSaveBLS = new CertifyMaxSaveBLS();
    logger.debug("Start CertifyMaxSaveBLS Submit...");
    if (!tCertifyMaxSaveBLS.submitData(mInputData,mOperate))
    {
      this.mErrors.copyAllErrors(tCertifyMaxSaveBLS.mErrors);
      CError tError = new CError();
      tError.moduleName = "CertifyMaxSaveBLS";
      tError.functionName = "submitData";
      tError.errorMessage = "修改失败！！！";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    return true;
  }
  private void  QueryOldData()
  {
    logger.debug("￥￥￥￥￥￥￥￥2003-05-20");
    logger.debug("开始执行QueryOldData操作！！！");
    int n = mLDAgentCardCountSet.size();
    logger.debug("在bl中接收的变量是"+n+"条记录！！！");
    LDAgentCardCountSet yLDAgentCardCountSet = new LDAgentCardCountSet();
    for(int i = 1; i <=n; i++)
    {
      logger.debug("2003-04-22");
      LDAgentCardCountSchema pLDAgentCardCountSchema = new LDAgentCardCountSchema();//接收jsp中传递进来的数据
      pLDAgentCardCountSchema.setSchema(mLDAgentCardCountSet.get(i));

      logger.debug("代理人级别是"+pLDAgentCardCountSchema.getAgentGrade());
      logger.debug("单证类型是"+pLDAgentCardCountSchema.getCertifyCode());

      //pLDAgentCardCountSchema 中春存放的是从jsp中得到的数据
      //      logger.debug("SSRS");
      //      SSRS y_ssrs = new SSRS();
      //      ExeSQL y_exesql = new ExeSQL();
      //      logger.debug("开始定义sql");
      //      String y_sql = null;
      try
      {
        logger.debug("在代理人最大领取表中查询出最的值的信息！！");
        logger.debug("根据两种情况分别进行查询");
        if(mUpdateType.equals("A"))
        {
          pLDAgentCardCountSchema.setAgentGrade("*");
          pLDAgentCardCountSchema.setManageCom("*");

          logger.debug("当修改标志是A时的信息如下：");
          logger.debug("代理人编码是"+pLDAgentCardCountSchema.getAgentCode());
          logger.debug("单证号码是"+pLDAgentCardCountSchema.getCertifyCode());
          logger.debug("最大数量是"+pLDAgentCardCountSchema.getMaxCount());
          logger.debug("管理机构是"+pLDAgentCardCountSchema.getManageCom());
          logger.debug("代理人级别是"+pLDAgentCardCountSchema.getAgentGrade());

          //          y_sql = " Select * From LDAgentCardCount where AgentGrade = '"
          //                +pLDAgentCardCountSchema.getAgentGrade()
          //                +"' and CertifyCode = '"
          //                +pLDAgentCardCountSchema.getCertifyCode()+"'";
        }
        if(mUpdateType.equals("M"))
        {
          pLDAgentCardCountSchema.setAgentCode("*");
          logger.debug("当修改标志是A时的信息如下：");
          logger.debug("代理人编码是"+pLDAgentCardCountSchema.getAgentCode());
          logger.debug("单证号码是"+pLDAgentCardCountSchema.getCertifyCode());
          logger.debug("最大数量是"+pLDAgentCardCountSchema.getMaxCount());
          logger.debug("管理机构是"+pLDAgentCardCountSchema.getManageCom());
          logger.debug("代理人级别是"+pLDAgentCardCountSchema.getAgentGrade());
//          //          y_sql = "Select * From LDAgentCardCount Where ManageCom = '"
          //                +pLDAgentCardCountSchema.getManageCom()
          //                +"' and AgentGrade = '"
          //                +pLDAgentCardCountSchema.getAgentGrade()
          //                +"' and CertifyCode = '"
          //                +pLDAgentCardCountSchema.getCertifyCode()+" ' ";
          //
        }
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
      //      logger.debug("sql定义完毕！！！");
      //      y_ssrs = y_exesql.execSQL(y_sql);
      //      logger.debug("您执行的sql已经是"+y_sql);
      //      pLDAgentCardCountSchema.setAgentCode(y_ssrs.GetText(1,1));
      //      pLDAgentCardCountSchema.setAgentGrade(y_ssrs.GetText(1,5));
      //      pLDAgentCardCountSchema.setCertifyCode(y_ssrs.GetText(1,2));
      //      pLDAgentCardCountSchema.setManageCom(y_ssrs.GetText(1,4));
      yLDAgentCardCountSet.add(pLDAgentCardCountSchema);
    }
    mInputData.clear();
    mInputData.addElement(mUpdateType);
    mInputData.add(yLDAgentCardCountSet);
    logger.debug("查询后加入的数据是"+yLDAgentCardCountSet.size()+"条记录！！！");
    logger.debug("传入bls中的修改类型是"+mUpdateType);
  }
  /**
   * 对新增的数据进行测试，若违反了唯一操作则提前退出该程序
   * 当操作标志上“A”时，按照代理人进行新增
   * 当操作标志是“M”时，按照管理机构进行新增。
   *
   */
/*********************
  private boolean checkData()
  {
    logger.debug("开始执行checkData（）函数操作！！！");
    logger.debug("@@@@@@@@@@@@@@checkData（）");
    int n = mLDAgentCardCountSet.size();
    LDAgentCardCountSchema tLDAgentCardCountSchema = new LDAgentCardCountSchema();
    if(mUpdateType.equals("A"))
    {
      for(int i = 1; i < n ;i++)
      {
        logger.debug("代理人代码是"+mLDAgentCardCountSet.get(i).getAgentCode());
        if(mLDAgentCardCountSet.get(i).getAgentCode().equals(mLDAgentCardCountSet.get(i+1).getAgentCode())
           &&mLDAgentCardCountSet.get(i).getCertifyCode().equals(mLDAgentCardCountSet.get(i+1).getCertifyCode()))
        {
          CError tError = new CError();
          tError.moduleName = "CertifyMaxSaveBL";
          tError.functionName = "queryData";
          tError.errorMessage = "在代理人代码和单证代码录入中出现了重复的信息，请仔细查看！！！";
          this.mErrors.addOneError(tError);
          return false;
        }
      }
    }
    if(mUpdateType.equals("M"))
    {
      for(int i = 1; i < n;i++)
      {
        logger.debug("1111×××××××××××   %%"+(mLDAgentCardCountSet.get(i).getManageCom().equals(mLDAgentCardCountSet.get(i+1).getManageCom())
             &&mLDAgentCardCountSet.get(i).getAgentGrade().equals(mLDAgentCardCountSet.get(i+1).getAgentCode())
           &&mLDAgentCardCountSet.get(i).getCertifyCode().equals(mLDAgentCardCountSet.get(i+1).getCertifyCode())));
        if(mLDAgentCardCountSet.get(i).getManageCom().equals(mLDAgentCardCountSet.get(i+1).getManageCom())
           &&mLDAgentCardCountSet.get(i).getAgentGrade().equals(mLDAgentCardCountSet.get(i+1).getAgentCode())
           &&mLDAgentCardCountSet.get(i).getCertifyCode().equals(mLDAgentCardCountSet.get(i+1).getCertifyCode()))
      {
          CError tError = new CError();
          tError.moduleName = "CertifyMaxSaveBL";
          tError.functionName = "queryData";
          tError.errorMessage = "在管理机构、代理人级别和单证代码录入中出现了重复的信息，请仔细查看！！！";
          this.mErrors.addOneError(tError);
          return false;
        }
      }
    }
    return true;
  }
********************/
  public static void main(String[] args)
  {
    LDAgentCardCountSchema aLDAgentCardCountSchema = new LDAgentCardCountSchema();
    LDAgentCardCountSet aLDAgentCardCountSet = new LDAgentCardCountSet();
    String tOperateType = "UPDATE";
    String tUpdateType = "A";
    aLDAgentCardCountSchema.setAgentCode("111000");
    aLDAgentCardCountSchema.setCertifyCode("01");
    aLDAgentCardCountSchema.setMaxCount("23");
    aLDAgentCardCountSet.add(aLDAgentCardCountSchema);
    VData tVData = new VData();
    tVData.addElement(tOperateType);
    tVData.addElement(tUpdateType);
    tVData.addElement(aLDAgentCardCountSet);
    CertifyMaxSaveUI tCertifyMaxSaveUI = new CertifyMaxSaveUI();
    tCertifyMaxSaveUI.submitData(tVData,"UPDATE");
  }
}
