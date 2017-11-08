/*
 * <p>ClassName: LLClaimScanInputBL </p>
 * <p>Description: LLClaimScanInputBL类文件 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: sinosoft </p>
 * @Database: 单证补扫
 * @CreateDate：2005-08-28
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.Date;
import java.lang.Long;

public class LLClaimScanInputBL  {
private static Logger logger = Logger.getLogger(LLClaimScanInputBL.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  private VData mResult = new VData();
  /** 往后面传输数据的容器 */
  private VData mInputData;
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  /** 数据操作字符串 */
  private String mOperate;
  /** 业务处理相关变量 */
  private Es_IssueDocSchema mEs_IssueDocSchema=new Es_IssueDocSchema();
  private Es_IssueDocSet mEs_IssueDocSet=new Es_IssueDocSet();
  public LLClaimScanInputBL() {}
  public static void main(String[] args) {}
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
    if (!dealData())
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "LLClaimScanInputBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据处理失败LLClaimScanInputBL-->dealData!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    //准备往后台的数据
    if (!prepareOutputData())
      return false;
    if (this.mOperate.equals("QUERY||MAIN"))
    {
      this.submitquery();
    }
    else
    {
      logger.debug("Start tLLClaimScanInputBL Submit...");
      LLClaimScanInputBLS tLLClaimScanInputBLS=new LLClaimScanInputBLS();
      tLLClaimScanInputBLS.submitData(mInputData,cOperate);
      logger.debug("End tLLClaimScanInputBL Submit...");
      //如果有需要处理的错误，则返回
      if (tLLClaimScanInputBLS.mErrors.needDealError())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLLClaimScanInputBLS.mErrors);
        CError tError = new CError();
        tError.moduleName = "tLLClaimScanInputBL";
        tError.functionName = "submitDat";
        tError.errorMessage ="数据提交失败!";
        this.mErrors .addOneError(tError) ;
        return false;
      }
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
    boolean tReturn =true;
    String currentDate = PubFun.getCurrentDate();
    String currentTime = PubFun.getCurrentTime();
    if(this.mOperate.equals("INSERT"))
        {//确定录入的赔案号确实存在
            String tBussnotype =this.mEs_IssueDocSchema.getBussNoType().trim();
            String tCaseNo =this.mEs_IssueDocSchema.getBussNo().trim();
            String tSubtype =this.mEs_IssueDocSchema.getSubType().trim();
            String tCount;
            String tCount1;
            int i;


            ES_DOC_RELATIONDB tES_DOC_RELATIONDB = new ES_DOC_RELATIONDB();

            String sql = "select * from ES_DOC_RELATION where"
                                      + " Bussnotype ='" +tBussnotype + "'"
                                      + " and Bussno = '" + tCaseNo + "'"
                                      + " and Subtype = '" + tSubtype + "'";
            logger.debug("Start query LLCase:" + sql);
            ES_DOC_RELATIONSet tLLCaseSet = (ES_DOC_RELATIONSet) tES_DOC_RELATIONDB.executeQuery(sql);

            //

            String tSQL = "select MAX(to_number(IssueDocID)) from Es_IssueDoc where BussNo is not null";
            ExeSQL tExeSQL = new ExeSQL();
            tCount1 = tExeSQL.getOneValue(tSQL);

            //


        if (tCount1 != null && !tCount1.equals(""))
            {

              Integer tInteger = new Integer(tCount1);
              i = tInteger.intValue();
              i = i + 1;
            }

            else
            {       i = 1 ;
            }
        if (tLLCaseSet == null || tLLCaseSet.size() == 0)
        {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimScanInputBL";
                tError.functionName = "dealData";
                tError.errorMessage = "此赔案号不存在!";
                this.mErrors.addOneError(tError);
                tReturn = false;
            }
         else
         {
             this.mEs_IssueDocSchema.setIssueDocID(String.valueOf(i));
             this.mEs_IssueDocSchema.setMakeDate(currentDate);
             this.mEs_IssueDocSchema.setMakeTime(currentTime);
             this.mEs_IssueDocSchema.setModifyDate(currentDate);
             this.mEs_IssueDocSchema.setModifyTime(currentTime);
             this.mEs_IssueDocSchema.setPromptOperator(this.mGlobalInput.Operator);
         }

    }
    /*else if (this.mOperate.equals("UPDATE||MAIN"))
    {

       LATrainDB tLATrainDB = new LATrainDB();
       tLATrainDB.setAgentCode(mLATrainSchema.getAgentCode());
       tLATrainDB.setIdx(mLATrainSchema.getIdx());
       if (!tLATrainDB.getInfo())
       {
         // @@错误处理
         CError tError = new CError();
         tError.moduleName = "LATrainBL";
         tError.functionName = "dealData";
         tError.errorMessage = "原培训信息查询失败!";
         this.mErrors.addOneError(tError);
         return false;
       }
       this.mLATrainSchema.setMakeDate(tLATrainDB.getMakeDate());
       this.mLATrainSchema.setMakeTime(tLATrainDB.getMakeTime());
       this.mLATrainSchema.setModifyDate(currentDate);
       this.mLATrainSchema.setModifyTime(currentTime);
       this.mLATrainSchema.setOperator(this.mGlobalInput.Operator);
    }*/
  //  tReturn=true;
    return tReturn ;
  }

  /**
  * 从输入数据中得到所有对象
  *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
  */
  private boolean getInputData(VData cInputData)
  {
    this.mEs_IssueDocSchema.setSchema((Es_IssueDocSchema)cInputData.getObjectByObjectName("Es_IssueDocSchema",0));
    this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }

  /**
  * 准备往后层输出所需要的数据
  * 输出：如果准备数据时发生错误则返回false,否则返回true
  */
  private boolean submitquery()
  {
    this.mResult.clear();
    logger.debug("Start LLClaimScanInputBLQuery Submit...");
    Es_IssueDocDB tEs_IssueDocDB=new Es_IssueDocDB();
    tEs_IssueDocDB.setSchema(this.mEs_IssueDocSchema);
    this.mEs_IssueDocSet=tEs_IssueDocDB.query();
    ////
    this.mResult.add(this.mEs_IssueDocSet);
    logger.debug("End LLClaimScanInputBLQuery Submit...");
    //如果有需要处理的错误，则返回
    if (tEs_IssueDocDB.mErrors.needDealError())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tEs_IssueDocDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "LLClaimScanInputBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    mInputData=null;
    return true;
  }


  private boolean prepareOutputData()
  {
    try
    {
      this.mInputData=new VData();
      this.mInputData.add(this.mGlobalInput);
      this.mInputData.add(this.mEs_IssueDocSchema);
    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="LLClaimScanInputBL";
      tError.functionName="prepareData";
      tError.errorMessage="在准备往后层处理所需要的数据时出错。";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    return true;
  }


  public VData getResult()
  {
    return this.mResult;
  }

}
