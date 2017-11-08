package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.io.*;

import javax.xml.parsers.*;
import java.util.*;
import org.w3c.dom.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;
import com.sinosoft.workflow.claimgrp.*;

public class GrpCustomerGuideReportIn {
private static Logger logger = Logger.getLogger(GrpCustomerGuideReportIn.class);
  //错误处理类
  public CErrors logErrors = new CErrors();
  public CErrors mErrors = new CErrors();
  //导入时从页面取的值
  public String GrpContNo;
  public String trgtstate;
  public String GrpCustomerNo;
  public String GrpName;
  public String RptorName;
  public String RptorPhone;
  public String RptorAddress;
  public String Polno;
  public String AccFlag;
  public String RptNo;
  private VData mInputData = new VData();
  /** 往前面传输数据的容器 */
  private VData mResult = new VData();
  private MMap tMap = new MMap();

  private String FileName;
  private String XmlFileName;
  private String mPreFilePath;

  private String FilePath = "E:/temp/";
  private String ParseRootPath = "/DATASET/BATCHID";
  private String ParsePath = "/DATASET/LLCASETABLE/ROW";
  private String ParsePath2 = "/DATASET/LLFEEMAINTABLE/ROW";

  //配置文件Xml节点描述
  private String ImportFileName;
  private String ConfigFileName;
  private String mBatchNo = "";

  private String[] m_strDataFiles = null;
  private GlobalInput mGlobalInput = new GlobalInput();
  private String mRgtNo = "";
  private String AccNo = "";
  private int mSumCount = 0;
  private int mTCount = 0;
  private TransferData mTransferData = new TransferData();
  private String mContent = "";
  private TransferData mReturnData = new TransferData();
  private org.w3c.dom.Document m_doc = null;

  //private LLCaseSet mLLCaseSet = new LLCaseSet();
  private LLSubReportSet mLLSubReportSet = new LLSubReportSet();
  private LLReportSchema mLLReportSchema = new LLReportSchema();
  LCGrpCustomerImportLogSchema delLCGrpCustomerImportLogSchema = new  LCGrpCustomerImportLogSchema();
  LCGrpCustomerImportLogSchema InLCGrpCustomerImportLogSchema = new  LCGrpCustomerImportLogSchema();


  public GrpCustomerGuideReportIn() {
    bulidDocument();
  }

  public boolean submitData(VData cInputData, String cOperate) {
    CErrors mErrors = new CErrors();
    mInputData = (VData) cInputData.clone();
    //得到数据
    getInputData();
    logger.debug("----------GrpCustomerGuideReportIn after getInputData----------");
    //检查数据
    if (!checkData()) {
      return false;
    }
    logger.debug("----------GrpCustomerGuideReportIn after checkData----------");
    logger.debug("开始时间:" + PubFun.getCurrentTime());
    try {
      //把Excel转化为Xml
      if (!parseVts()) {
        return false;
      }
      logger.debug("----------GrpCustomerGuideReportIn after parseVts----------");
      for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
        //把Xml转化为Schema
        XmlFileName = m_strDataFiles[nIndex];
        if (!ParseXml()) {
          return false;
        }
      }
      logger.debug("----------GrpCustomerGuideReportIn after ParseXml----------");
    }
    catch (Exception ex) {
      ex.printStackTrace();
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "LCParseGuideIn";
      tError.functionName = "checkData";
      tError.errorMessage = "导入文件格式有误!";
      logErrors.addOneError(tError);
    }

    mErrors = logErrors;
    logger.debug("结束时间:" + PubFun.getCurrentTime());
//    if (mErrors.getErrorCount() > 0) {
//      return false;
//    }

    return true;
  }

  /**
   * 检验文件是否存在
   * @return
   */
  private boolean checkXmlFileName()
  {
      File tFile = new File(XmlFileName);
      if (!tFile.exists())
      {
          LDSysVarDB tLDSysVarDB = new LDSysVarDB();
          tLDSysVarDB.setSysVar("XmlPath");
          if (!tLDSysVarDB.getInfo())
          {
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "ParseGuideIn";
              tError.functionName = "checkData";
              tError.errorMessage = "缺少文件导入路径!";
              this.mErrors.addOneError(tError);
              return false;
          }
          else
              FilePath = tLDSysVarDB.getSysVarValue();

          File tFile1 = new File(FilePath);
          if (!tFile1.exists())
          {
              tFile1.mkdirs();
          }
          XmlFileName = FilePath + XmlFileName;
          File tFile2 = new File(XmlFileName);
          if (!tFile2.exists())
          {
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "ParseGuideIn";
              tError.functionName = "checkData";
              tError.errorMessage = "请上传相应的数据文件到指定路径" +
                                    FilePath + "!";
              this.mErrors.addOneError(tError);
              return false;
          }
      }
      return true;
  }


  /**
   * 解析xml
   * @return
   */
  private boolean ParseXml()
  {

      LLReportSchema tLLReportSchema = new LLReportSchema();
      String ttID="";
      int I=0;
      String ttcustomerNo="";
      String ttcustomerName="";
      String ttflagRgtNo="";
      Vector vec=new Vector();
      if (!checkXmlFileName())
          return false;
      File tFile = new File(XmlFileName);
      XMLPathTool tXPT = new XMLPathTool(XmlFileName);
      this.mErrors.clearErrors();

      String tBatchNo = "";
      try
      {
          //批次号
          tBatchNo = tXPT.parseX(ParseRootPath).getFirstChild().getNodeValue();
          mBatchNo = tBatchNo;
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
      }


      NodeList nodeList = tXPT.parseN(ParsePath);
      int nNodeCount = 0;

      if( nodeList != null ) {
          nNodeCount = nodeList.getLength();
      }
  mSumCount=nNodeCount; //记录导入前总数
  GrpCustomerParser tGrpCustomerParser = new GrpCustomerParser();
      //单次提交
  for (int i = 0; i < nNodeCount; i++)
  {
      Node node = nodeList.item(i);
      I=i+1;
      try
      {
          node = transformNode(node);
      }
      catch (Exception ex)
      {
          ex.printStackTrace();
          node = null;
      }

      String nodeName = "";
      nodeName = node.getNodeName();
      ///////////////////////////////////////////////////////////////////////////////////////////////////
      String cGrpContNo     = this.getGrpContNo(); //团体客户号
      String tGrpCustomerNo = this.getGrpCustomerNo(); //团体客户号*
      String tRiskCode      = this.getPolno();     //保单险种号
      String cRptorName     = this.getRptorName();//报案人姓名
      String cRptorPhone    = this.getRptorPhone();//报案人电话
      String cRptorAddress  = this.getRptorAddress();//报案人通讯地址
      String crgtstate      = this.gettrgtstate();//报案类型--正常,批量,账户
      String cAccFlag       = this.getAccFlag();//是否使用团体金额标志
      String tRptNo         = this.getRptNo();//界面上的赔案号
      ///////////////////////////////////////////////////////////////////////////////////////////////////
      String cCustomerNo    = parseNode(node, "CustomerNo"); //投保人号码
      String tCustomerName  = parseNode(node, "CustomerName");  //出险人名称
      String tAccidentDate  = parseNode(node, "AccidentDate");  //出险日期*
      String tRgtNo         = parseNode(node, "RgtNo");  //模板中的赔案号*
      logger.debug("20070614QQ"+cRptorName);
      VData tLLCaseVData = new VData();
      logger.debug("crgtstate-------------------------------"+crgtstate);
      String ID = "";
      String customerNo = "";
      String customerName = "";
      String flagRgtNo = "";
      boolean tErrorState = false;

    if (nodeName.equals("#text"))continue;
    if (nodeName.equals("ROW")) {

        //解析案件XML
        mErrors.clearErrors();
        logErrors.clearErrors();
        tLLCaseVData = new VData();//出险信息/帐单信息的结果集
        tLLCaseVData = tGrpCustomerParser.getLLCaseDataReport(node,cGrpContNo,tGrpCustomerNo,tRiskCode,cRptorName,cRptorPhone,cRptorAddress,crgtstate,cAccFlag,I);//进行一些非空校验

        //---------------jinsh--导入的非空校验-------------//
        if(tLLCaseVData==null)
        {
            CError tError = new CError();
            tError.moduleName = "GrpCustomerParser";
            tError.functionName = "getLLCaseData";
            tError.errorMessage = "有必录项没有录入,请重新检查录入！";
            this.mErrors.addOneError(tError);
            return false;
        }
       //---------------jinsh--导入的非空校验-------------//

        logErrors = tGrpCustomerParser.mErrors;
        String a = Integer.toString(i+1);
        if (logErrors != null && !logErrors.equals("") && logErrors.getErrorCount()>0)
        {
            if(mRgtNo!=null && !mRgtNo.equals(""))
            {
                LogError(mRgtNo, mBatchNo, a, cCustomerNo, tCustomerName,
                         mGlobalInput.Operator, false, logErrors,"2");
            }
            else
            {
                LogError("000000", mBatchNo, a, cCustomerNo, tCustomerName,
                         mGlobalInput.Operator, false, logErrors,"2");
            }
            continue;
       }

        if((tRptNo.equals("")&&!tRgtNo.trim().equals(""))||(!tRptNo.equals("")&&tRgtNo.trim().equals(""))||(!tRptNo.equals(tRgtNo.trim())))
        {
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "模板中的赔案号与界面上的赔案号不一致,不能导入！";
            this.mErrors.addOneError(tError);
            return false;
        }
        String sql139="select distinct riskcode from lcgrppol where grpcontno='"+cGrpContNo+"' ";
        ExeSQL riskExeSQL = new ExeSQL();
        SSRS riskSSRS = new SSRS();
        String triskcod139=riskExeSQL.getOneValue(sql139);
        riskSSRS=riskExeSQL.execSQL(sql139);
        String accSql="select risktype6 from lmriskapp where riskcode ='"+triskcod139+"' "; //risktype6 in ('1') 代表139，151险种
        String trisktype6 = riskExeSQL.getOneValue(accSql);

        if(tRgtNo.trim().equals("") && !crgtstate.equals("02") && riskSSRS.getMaxRow()==1 && trisktype6.equals("1"))
        {
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "该保单只有帐户型险种，请选择账户案件报案！";
            this.mErrors.addOneError(tError);
            return false;
        }

//     -----------liuyu---2007九一八----校验保单授权机构
       String ComSql=" select count(*) from lcinsured where grpcontno='"+cGrpContNo+"' and insuredno='"+cCustomerNo+"' and managecom like '"+mGlobalInput.ManageCom+"%'   ";
       ExeSQL ComExeSQL = new ExeSQL();
       String tComCount=ComExeSQL.getOneValue(ComSql);
       if (Integer.parseInt(tComCount)==0)
       {
           String ComSql2=" select count(*) from lcinsured where grpcontno='"+cGrpContNo+"' and insuredno='"+cCustomerNo+"' and ExecuteCom like '"+mGlobalInput.ManageCom+"%'   ";
           String tComCount2=ComExeSQL.getOneValue(ComSql2);
           if (Integer.parseInt(tComCount2)==0)
           {
               CError tError = new CError();
               tError.moduleName = "ParseGuideIn";
               tError.functionName = "checkData";
               tError.errorMessage = "该客户(" + cCustomerNo + ")，没有授权给"+mGlobalInput.ManageCom+"机构！";
               this.mErrors.addOneError(tError);
               LogError("000000", mBatchNo, Integer.toString(I), cCustomerNo, tCustomerName,
                      mGlobalInput.Operator, false, mErrors,"1");
               //return false;
               continue;
           }
       }

//      -------------------jinsh---2007-06-13-------------------------------//
        //增加对出险人的报案情况进行判断，如果针对一次案件已经有相应的报案记录,---------//
        //就不允许再对此人做报案,避免重复报案--------------------------------------//
        //String repStrSQL = "select subrptno from llsubreport where  not exists ( select 'X' from llregister where rgtno = llsubreport.subrptno ) and customerno='"+cCustomerNo+"' and rownum<=1";
        String repStrSQL ="select subrptno from llsubreport a ,llreport b where a.subrptno=b.rptno and b.grpcontno='"+cGrpContNo+"' and a.customerno='"+cCustomerNo+"' and not exists ( select 'X' from llregister where rgtno = a.subrptno ) ";
        ExeSQL reptExeSQL = new ExeSQL();
        String reptCont = reptExeSQL.getOneValue(repStrSQL);
        if (!(reptCont==null||reptCont.equals("")))
        //if (Integer.parseInt(reptCont)>0)
        {
          //String temRptNo = reptExeSQL.getOneValue("select subrptno  from llsubreport where  not exists ( select 'X' from llregister where rgtno = llsubreport.subrptno ) and customerno='"+cCustomerNo+"' and rownum<=1" );
          CError tError = new CError();
          tError.moduleName = "ParseGuideIn";
          tError.functionName = "checkData";
          tError.errorMessage = "该客户(" + cCustomerNo + ")在赔案(" +reptCont+")有报案数据，请不要重复报案！";
          this.mErrors.addOneError(tError);
          LogError("000000", mBatchNo, Integer.toString(I), cCustomerNo, tCustomerName,
                      mGlobalInput.Operator, false, mErrors,"1");
          //return false;
          continue;
        }
        //-------------------jinsh---2007-06-13-------------------------------//


        //将原来对导入案件的校验放到这里..

        if(crgtstate.equals("02")||crgtstate=="02")//导入的账户案件
        {
            /**
             * 如果是账户--查询lcinsureaccclass表
             * **/
            String Querylcinsureaccclass="select count(*) from lcinsureaccclass where grpcontno='"+cGrpContNo+"' and insuredno='"+cCustomerNo+"'";
            String QuerylcinsureaccclassNum=reptExeSQL.getOneValue(Querylcinsureaccclass);
            logger.debug(Querylcinsureaccclass);
            if(Integer.parseInt(QuerylcinsureaccclassNum)==0||QuerylcinsureaccclassNum==null)
            {
                CError tError = new CError();
                tError.moduleName = "ParseGuideIn";
                tError.functionName = "checkData";
                tError.errorMessage = "该客户(" + cCustomerNo + ")没有账户信息!不允许做新增！";
                this.mErrors.addOneError(tError);
                LogError("000000", mBatchNo, Integer.toString(I), cCustomerNo, tCustomerName,
                      mGlobalInput.Operator, false, mErrors,"1");
                //return false;
                continue;
            }
        }
        /**
         * 增加对做完生存给付（趸领）的被保险人的判断不允许理赔
         * */
        String LifeJustSql=" select count(*) From lcpol where polstate = '6'"
                   + " and  insuredno = '"+cCustomerNo+"'"
                   + " and  grpcontno = '"+cGrpContNo+"'";
        if(tRiskCode!=null&&!tRiskCode.equals(""))
        {
            LifeJustSql+=" and riskcode = '"+tRiskCode+"'";
        }
        ExeSQL ttExeSQL = new ExeSQL();
        String NumOfLifeJust=ttExeSQL.getOneValue(LifeJustSql);
        if(Integer.parseInt(NumOfLifeJust)>0)
        {
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "该客户(" + cCustomerNo + ")已经做了生存给付，不允许做新增！";
            this.mErrors.addOneError(tError);
            LogError("000000", mBatchNo, Integer.toString(I), cCustomerNo, tCustomerName,
                      mGlobalInput.Operator, false, mErrors,"1");
            //return false;
            continue;
        }

        /**
         * 增加对出险人是否作过死亡理赔的判断，如作过不允许再作理赔
         * ***/
        String JustDeath=" select count(*) From lcpol where polstate = '7'"
                   + " and  insuredno = '"+cCustomerNo+"'"
                   + " and  grpcontno = '"+cGrpContNo+"'";
        String JustDeathNum=ttExeSQL.getOneValue(JustDeath);
        if(Integer.parseInt(JustDeathNum)>0)
        {
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "该客户(" + cCustomerNo + ")已经做了死亡理赔，不允许做新增！";
            this.mErrors.addOneError(tError);
            LogError("000000", mBatchNo, Integer.toString(I), cCustomerNo, tCustomerName,
                      mGlobalInput.Operator, false, mErrors,"1");
            //return false;
            continue;
        }

        //-------------------jinsh---2007-06-28-------------------------------//
        //增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增
        String StrSQL = " select a.caseno from llcase a,llregister b where a.rgtno=b.rgtno and b.grpcontno='"+cGrpContNo+"' and a.customerno = '"+cCustomerNo+"' and  a.rgtstate not in ('60','80','50','70') and rownum<=1";
        ExeSQL tExeSQL = new ExeSQL();
        String tCont = tExeSQL.getOneValue(StrSQL);
        if (!(tCont==null||tCont.equals("")))
        {
          //String tmpCaseNo = tExeSQL.getOneValue("select a.caseno from llcase a,llregister b where a.rgtno=b.rgtno and b.grpcontno='"+cGrpContNo+"' and a.customerno = '"+cCustomerNo+"' and  a.rgtstate not in ('60','80','50','70') and rownum<=1");
          CError tError = new CError();
          tError.moduleName = "ParseGuideIn";
          tError.functionName = "checkData";
          tError.errorMessage = "该客户(" + cCustomerNo + ")在赔案（" +tCont+")有未结案件，请结案后再做新增！";
          this.mErrors.addOneError(tError);
          LogError("000000", mBatchNo, Integer.toString(I), cCustomerNo, tCustomerName,
                      mGlobalInput.Operator, false, mErrors,"1");
          //return false;
          continue;
        }

    }

    if(tLLCaseVData.size()==0)
    {
//        CError tError = new CError();
//        tError.moduleName = "GrpCustomerParser";
//        tError.functionName = "getLLCaseData";
//        tError.errorMessage = "有必录项没有录入,请重新检查录入！";
//        this.mErrors.addOneError(tError);
//        return false;
        continue;
    }
    else
    {
        vec.add(tLLCaseVData);
    }

 }
/**
  * jinsh-------2007-07-03---------------------------------------
  * 修改原因:原导入功能是针对每一个人,只要这个人通过了验证,就生成数据,----
  * 现在改成对所有的人进行验证,全部通过后再进行提交,这样就避免了生成------
  * 有一部分人验证不通过时的一些无用的数据,影响导入功能.----------------
  * **/
 VData ttLLCaseVData = new VData();//出险信息/帐单信息的结果集
 mTCount=vec.size(); //记录导入后的总数
 for (int i=0 ;i<vec.size();i++)
 {
     boolean ttErrorState = false;
     ttLLCaseVData=(VData)vec.get(i);
     if (ttLLCaseVData != null && ttLLCaseVData.size() > 0)
     {
         ttLLCaseVData.add(mGlobalInput);
         ttID = (String) ttLLCaseVData.getObjectByObjectName("String", 0);
         ttcustomerNo = ((String) ttLLCaseVData.getObjectByObjectName("String", 1)). trim();
         ttcustomerName = (String) ttLLCaseVData.getObjectByObjectName("String", 2);
         ttflagRgtNo= (String) ttLLCaseVData.getObjectByObjectName("String", 3);
         if(!ttflagRgtNo.equals(""))
         {
             mRgtNo=ttflagRgtNo;
         }
         //LLGrpClaimRegisterBL tLLGrpClaimRegisterBL = new LLGrpClaimRegisterBL();
         LLReportBL tLLReportBL = new LLReportBL();
         //准备数据
         LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();
         tLLSubReportSchema = (LLSubReportSchema) ttLLCaseVData.getObjectByObjectName( "LLSubReportSchema", 0);
         if (tLLSubReportSchema != null && !tLLSubReportSchema.getCustomerNo().equals(""))
         {
             if (mRgtNo != null && !mRgtNo.equals(""))
             {
                 if(AccNo==null||AccNo.equals(""))
                 {
                     String sql="";
                     sql="select CaseRelaNo from llcaserela where caseno='"+mRgtNo+"'";
                     ExeSQL tExeSQL=new ExeSQL();
                     AccNo = tExeSQL.getOneValue(sql);
                 }
                 LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
                 LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //理赔类型集合
                 LLReportReasonSet tLLReportReasonSet2 = new LLReportReasonSet(); //理赔类型集合

                 tLLAccidentSchema = (LLAccidentSchema) ttLLCaseVData.getObjectByObjectName("LLAccidentSchema", 0);
                 tLLReportSchema = (LLReportSchema) ttLLCaseVData.getObjectByObjectName("LLReportSchema", 0);
                 tLLReportReasonSet = (LLReportReasonSet) ttLLCaseVData.getObjectByObjectName("LLReportReasonSet", 0);
                 tLLAccidentSchema.setAccNo(AccNo); //事件号
                 tLLReportSchema.setRptNo(mRgtNo); //报案号=赔案号
                 tLLSubReportSchema.setSubRptNo(mRgtNo); //分案号=报案号=赔案号
                 mLLSubReportSet.add(tLLSubReportSchema); //用于帐单客户号保存

                 for (int j = 1; j <= tLLReportReasonSet.size(); j++)
                 {
                     LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //理赔类型表
                     tLLReportReasonSchema = tLLReportReasonSet.get(j);
                     tLLReportReasonSchema.setRpNo(mRgtNo); //报案号=赔案号
                     tLLReportReasonSet2.add(tLLReportReasonSchema);
                 }
                 ttLLCaseVData.add(tLLSubReportSchema);
                 ttLLCaseVData.add(tLLReportSchema);
                 ttLLCaseVData.add(tLLAccidentSchema);
                 ttLLCaseVData.add(tLLReportReasonSet2);

                 ttErrorState = tLLReportBL.submitData(ttLLCaseVData,"REPORTCUSTOMER");
             }
             else
             {
                 ttErrorState = tLLReportBL.submitData(ttLLCaseVData, "REPORTCUSTOMER");
                 tLLReportSchema = (LLReportSchema)tLLReportBL.getResult().getObjectByObjectName("LLReportSchema", 0);
                 LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
                 tLLAccidentSchema = (LLAccidentSchema)tLLReportBL.getResult().getObjectByObjectName("LLAccidentSchema", 0);

                 mLLSubReportSet.add(tLLSubReportSchema);
                 mRgtNo = tLLReportSchema.getRptNo(); //返回生成的赔案号
                 AccNo = tLLAccidentSchema.getAccNo(); //返回生成的事件号
             }
         }
     }

    }

  if(ttflagRgtNo==null || ttflagRgtNo.equals(""))
  {
      //liuyu 2007-07-11 将生成工作流与导入同时完成
      String wFlag = "9999999999";
      String GrpContNo = this.getGrpContNo(); //团体客户号
      String GrpCustomerNo = this.getGrpCustomerNo(); //团体客户号
      String GrpCustomerName = this.getGrpName(); //团体客户号
      String RptorName = this.getRptorName(); //报案人姓名
      String RgtState = this.gettrgtstate(); //报案类型--正常,批量,账户

      mTransferData.setNameAndValue("RptorName", RptorName); //报案人姓名
      mTransferData.setNameAndValue("CustomerNo", GrpCustomerNo); //团体客户号
      mTransferData.setNameAndValue("CustomerName", GrpCustomerName); //单位名称
      mTransferData.setNameAndValue("CustomerSex", ""); //出险人性别
      mTransferData.setNameAndValue("AccDate", PubFun.getCurrentDate()); //报案日期
      mTransferData.setNameAndValue("GrpContNo", GrpContNo); //团体保单号

      mTransferData.setNameAndValue("RptNo", mRgtNo); //赔案号
      mTransferData.setNameAndValue("MngCom", mGlobalInput.ManageCom);
      mTransferData.setNameAndValue("RgtState", RgtState); //报案类型
      ttLLCaseVData.add(mTransferData);
      ClaimWorkFlowBL tClaimWorkFlowBL = new ClaimWorkFlowBL();
      logger.debug("---ClaimWorkFlowBL submitData---");
      PubSubmit tPubSubmit = new PubSubmit();
      if (tClaimWorkFlowBL.submitData(ttLLCaseVData, wFlag)) {
          VData tempVData1 = new VData();
          tempVData1 = tClaimWorkFlowBL.getResult();
          mTransferData = null;
          mTransferData = (TransferData) tempVData1.getObjectByObjectName(
                  "TransferData", 0);
      } else {
          CError tError = new CError();
          tError.moduleName = "GrpCustomerGuideReportIn";
          tError.functionName = "parserXml";
          tError.errorMessage = "创建工作流节点失败！";
          this.mErrors.addOneError(tError);

          tMap = null;
          if (mRgtNo != null &&!mRgtNo.equals("")) {
              tMap.put("delete from llreport where rptno='" + mRgtNo + "' ",
                       "DELETE");
              tMap.put("delete from llsubreport where subrptno='" + mRgtNo + "' ",
                       "DELETE");
              tMap.put("delete from llreportreason where rpno='" + mRgtNo + "' ",
                       "DELETE");
              tMap.put("delete from llaccident where accno in (select subrptno from llreportrela rpno='" +
                       mRgtNo + "' )", "DELETE");
              tMap.put("delete from llaccidentsub where accno in (select subrptno from llreportrela rpno='" +
                       mRgtNo + "' ) ", "DELETE");
              tMap.put("delete from llreportrela where rptno='" + mRgtNo + "' ",
                       "DELETE");
              tMap.put("delete from llcaserela where caseno='" + mRgtNo + "' ",
                       "DELETE");
              mInputData.clear();
              mInputData.add(tMap);
              tPubSubmit.submitData(mInputData, "");
          }

      }
  }
      if (this.mErrors.needDealError())
      {
          logger.debug(this.mErrors.getErrorCount() +
                             "error:" + this.mErrors.getFirstError());
      }
      //解析完删除XML文件
        if (!tFile.delete())
        {
            // @@错误处理
//            CError tError = new CError();
//            tError.moduleName = "ParseGuideIn";
//            tError.functionName = "checkData";
//            tError.errorMessage = "Xml文件删除失败!";
//            this.mErrors.addOneError(tError);
//            return false;
      }
      return true;
  }
  //创建导入日志
  public void LogError(String tRgtNo,String tBatchNo,String ID ,
                          String tCustomerNo,String customerName,
                          String operator,boolean tErrorState,CErrors tErrors,String ErrorFlag )
  {
   delLCGrpCustomerImportLogSchema = new  LCGrpCustomerImportLogSchema();
   InLCGrpCustomerImportLogSchema = new  LCGrpCustomerImportLogSchema();
   //删除日志
   delLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo);
   delLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo);
   delLCGrpCustomerImportLogSchema.setID(ID);
   //插入日志
   InLCGrpCustomerImportLogSchema.setRgtNo(tRgtNo);
   InLCGrpCustomerImportLogSchema.setBatchNo(tBatchNo);
   InLCGrpCustomerImportLogSchema.setID(ID);
   InLCGrpCustomerImportLogSchema.setCustomerNo(tCustomerNo);
   InLCGrpCustomerImportLogSchema.setCustomerName(customerName);
   InLCGrpCustomerImportLogSchema.setOperator(operator);
   if(tErrorState)
   InLCGrpCustomerImportLogSchema.setErrorState("1");
   else
   InLCGrpCustomerImportLogSchema.setErrorState("0");
   InLCGrpCustomerImportLogSchema.setMakeDate(PubFun.getCurrentDate());
   InLCGrpCustomerImportLogSchema.setMakeTime(PubFun.getCurrentTime());
   String errorMess = "";
   if (ErrorFlag.equals("2"))
   {
       for (int i = 0;i<logErrors.getErrorCount();i++)
       {
           errorMess +=logErrors.getError(i).errorMessage;
       }
   }else
   {
       for (int i = 0;i<mErrors.getErrorCount();i++)
       {
           errorMess +=mErrors.getError(i).errorMessage;
       }
   }

   if("".equals(errorMess))errorMess = "未捕捉到错误";
   InLCGrpCustomerImportLogSchema.setErrorInfo(errorMess);

   tMap.put(delLCGrpCustomerImportLogSchema, "DELETE");
   tMap.put(InLCGrpCustomerImportLogSchema, "INSERT");
   PubSubmit tPubSubmit = new PubSubmit();
   mInputData.clear();
   mInputData.add(tMap);
   if (!tPubSubmit.submitData(mInputData, ""))
   {
        // @@错误处理
        CError tError = new CError();
        tError.moduleName = "GrpCustomerGuideReportIn";
        tError.functionName = "parserXml";
        tError.errorMessage = "数据提交失败！客户号：" + tCustomerNo + "!";
        this.logErrors.addOneError(tError);

    }

  }
  //取得日志信息
  public LCGrpCustomerImportLogSchema getLogInfo(String mRgtNo,String batchNo, String ID)
  {
      LCGrpCustomerImportLogDB tLCGrpCustomerImportLogDB = new LCGrpCustomerImportLogDB();
      tLCGrpCustomerImportLogDB.setRgtNo(mRgtNo);
      tLCGrpCustomerImportLogDB.setBatchNo(batchNo);
      tLCGrpCustomerImportLogDB.setID(ID);
      if (tLCGrpCustomerImportLogDB.getInfo())
      {
          return tLCGrpCustomerImportLogDB.getSchema();
      }
      return null;
  }

  /**
   * Detach node from original document, fast XPathAPI process.
   * @param node
   * @return
   * @throws Exception
   */
  private org.w3c.dom.Node transformNode(org.w3c.dom.Node node) throws
          Exception
  {
      Node nodeNew = m_doc.importNode(node, true);

      return nodeNew;
  }

  /**
   * 解析excel并转换成xml文件
   * @return
   */
  private boolean parseVts() throws Exception
  {
      //初始化导入文件
      if (!this.initImportFile()) return false;
      logger.debug("----------GrpCustomerGuideReportIn initImportFile()----------");
      //检查导入配置文件是否存在
      if (!this.checkImportConfig()) return false;
      logger.debug("----------GrpCustomerGuideReportIn checkImportConfig()----------");
      GrpCustomerVTSParser GCvp = new GrpCustomerVTSParser();

      if (!GCvp.setFileName(ImportFileName))
      {
          mErrors.copyAllErrors(GCvp.mErrors);
          return false;
      }
       logger.debug("----------GrpCustomerGuideReportIn setFileName()----------");
      if (!GCvp.setConfigFileName(ConfigFileName))
      {
          mErrors.copyAllErrors(GCvp.mErrors);
          return false;
      }
       logger.debug("----------GrpCustomerGuideReportIn setConfigFileName()----------");
      //转换excel到xml
      if (!GCvp.transform())
      {
          mErrors.copyAllErrors(GCvp.mErrors);
          return false;
      }
      logger.debug("----------GrpCustomerGuideReportIn transform()----------");
      // 得到生成的XML文件名列表
      m_strDataFiles = GCvp.getDataFiles();

      return true;
  }
  /**
   * 检查导入配置文件是否存在
   * @return
   */
  private boolean checkImportConfig()
  {
      this.getFilePath();

      String filePath = mPreFilePath + FilePath;
      File tFile1 = new File(filePath);
      if (!tFile1.exists())
      {
          //初始化创建目录
          tFile1.mkdirs();
      }

      ConfigFileName = filePath + "GrpCustomerImportReport.xml";
      File tFile2 = new File(ConfigFileName);
      if (!tFile2.exists())
      {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ParseGuideIn";
          tError.functionName = "checkData";
          tError.errorMessage = "请上传配置文件到指定路径" +
                                FilePath + "!";
          this.mErrors.addOneError(tError);
          return false;
      }

      return true;
  }

  /**
   * 初始化上传文件
   * @return
   */
  private boolean initImportFile()
  {
      this.getFilePath();
      ImportFileName = mPreFilePath + FilePath + FileName;
      //ImportFileName = "E:/temp/" + FileName;
      logger.debug("开始导入文件-----"+ImportFileName);
      File tFile = new File(ImportFileName);
      if (!tFile.exists())
      {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ParseGuideIn";
          tError.functionName = "checkData";
          tError.errorMessage = "未上传文件到指定路径" +
                                FilePath + "!";
          this.mErrors.addOneError(tError);
          return false;
      }
      logger.debug("-----导入文件");
      return true;
  }

  /**
   * 得到生成文件路径
   * @return
   */
  private boolean getFilePath()
  {
      LDSysVarDB tLDSysVarDB = new LDSysVarDB();
      tLDSysVarDB.setSysVar("XmlPath");
      if (!tLDSysVarDB.getInfo())
      {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "ParseGuideIn";
          tError.functionName = "checkData";
          tError.errorMessage = "缺少文件导入路径!";
          this.mErrors.addOneError(tError);
          return false;
      }
      else
          FilePath = tLDSysVarDB.getSysVarValue();

      return true;
  }

  /**
   * checkData
   *
   * @return boolean
   */
  private boolean checkData() {
    if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无操作员信息，请重新登录!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mTransferData == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "ParseGuideIn";
            tError.functionName = "checkData";
            tError.errorMessage = "无导入文件信息，请重新导入!";
            this.mErrors.addOneError(tError);
            return false;
        }
        else
        {
            FileName = (String) mTransferData.getValueByName("FileName");
        }
        return true;
  }

  /**
   * getInputData
   */
  private void getInputData()
  {
    mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput",0);
    mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
    mRgtNo = (String)mInputData.getObjectByObjectName("String", 0);
    mPreFilePath =(String) mTransferData.getValueByName("FilePath");
    logger.debug(mPreFilePath);
    logger.debug(mRgtNo);
    //jinsh ---2007-07----05---add
    GrpContNo=(String) mTransferData.getValueByName("GrpContNo");
    trgtstate=(String) mTransferData.getValueByName("trgtstate");
    GrpCustomerNo=(String) mTransferData.getValueByName("GrpCustomerNo");
    GrpName=(String) mTransferData.getValueByName("GrpName");
    RptorName=(String) mTransferData.getValueByName("RptorName");
    RptorPhone=(String) mTransferData.getValueByName("RptorPhone");
    RptorAddress=(String) mTransferData.getValueByName("RptorAddress");
    Polno=(String) mTransferData.getValueByName("Polno");
    AccFlag=(String) mTransferData.getValueByName("AccFlag");
    RptNo=(String) mTransferData.getValueByName("RptNo");

  }
  public String  getGrpContNo()
  {
      return this.GrpContNo;
  }
  public String  gettrgtstate()
  {
      return this.trgtstate;
  }
  public String  getGrpCustomerNo()
  {
      return this.GrpCustomerNo;
  }
  public String  getGrpName()
  {
      return this.GrpName;
  }
  public String  getRptorName()
  {
      return this.RptorName;
  }
  public String  getRptorPhone()
  {
      return this.RptorPhone;
  }
  public String  getRptorAddress()
  {
      return this.RptorAddress;
  }
  public String  getPolno()
  {
      return this.Polno;
  }
  public String  getAccFlag()
  {
      return this.AccFlag;
  }
  public String  getRptNo()
  {
      return this.RptNo;
  }


  /**
 * 得到日志显示结果
 * @return VData
 */
public VData getResult()
{
    mReturnData.setNameAndValue("Content",mContent);
    mResult.add(mRgtNo);
    mResult.add(mLLSubReportSet);
    mResult.add(mReturnData);
    mResult.add(String.valueOf(mSumCount));
    mResult.add(String.valueOf(mTCount));

    return mResult;

}

  public static void main(String[] args)
   {
       GrpCustomerGuideReportIn tPGI = new GrpCustomerGuideReportIn();
       VData tVData = new VData();
       TransferData tTransferData = new TransferData();
       tTransferData.setNameAndValue("FileName", "20050426.xls");
       tTransferData.setNameAndValue("FilePath", "E:");
       GlobalInput tG = new GlobalInput();
       tG.Operator = "claim";
       tG.ManageCom = "86";

       long a = System.currentTimeMillis();
       logger.debug(a);
       tVData.add(tTransferData);
       tVData.add(tG);
       tPGI.submitData(tVData, "");
       logger.debug(System.currentTimeMillis() - a);
   }
   /**
    * 利用XPathAPI取得某个节点的节点值
    * @param node
    * @param strPath
    * @return
    */
   private static String parseNode(Node node,
                                   String strPath)
   {
       String strValue = "";

       try
       {
           XObject xobj = XPathAPI.eval(node, strPath);
           strValue = xobj == null ? "" : xobj.toString();

       }
       catch (Exception ex)
       {
           ex.printStackTrace();
       }

       return strValue;
   }

   /**
    * Build a instance document object for function transfromNode()
    */
   private void bulidDocument() {
       DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
       dfactory.setNamespaceAware(true);

       try {
           m_doc = dfactory.newDocumentBuilder().newDocument();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }


}
