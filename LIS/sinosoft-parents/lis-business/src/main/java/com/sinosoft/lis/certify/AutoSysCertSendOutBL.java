package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
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

public class AutoSysCertSendOutBL {
private static Logger logger = Logger.getLogger(AutoSysCertSendOutBL.class);

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
  private Schema LOPRTManager;
  private LOPRTManager2Schema mLOPRTManager2Schema=new LOPRTManager2Schema();
  private LOPRTManagerSchema mLOPRTManagerSchema=new LOPRTManagerSchema();
  private boolean flag = true;
  private LZSysCertifySet mLZSysCertifySet=new LZSysCertifySet();
  private String CertifyCode="";

  public AutoSysCertSendOutBL() {
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
    if (!dealData())
    {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "CodeBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据处理失败CodeBL-->dealData!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    if (flag == true)
    {
      //准备往后台的数据
      if (!prepareOutputData())
        return false;

      logger.debug("Start SysCertSendOutBL Submit...");
      SysCertSendOutBL tSysCertSendOutBL = new SysCertSendOutBL();
      tSysCertSendOutBL.submitData(mInputData,"INSERT||MAIN");
      logger.debug("End SysCertSendOutBL Submit...");
      //如果有需要处理的错误，则返回
      if (tSysCertSendOutBL.mErrors.needDealError())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tSysCertSendOutBL.mErrors);
        CError tError = new CError();
        tError.moduleName = "SysCertSendOutBL";
        tError.functionName = "submitDat";
        tError.errorMessage ="数据提交失败!";
        this.mErrors.addOneError(tError) ;
        return false;
      }
      mResult = tSysCertSendOutBL.getResult();
      mResult.add("Succ");
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
    if (mOperate.equals("ASCSO"))
    {
      LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
      tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
      if (!tLOPRTManagerDB.getInfo())
      {
        // @@错误处理
        this.mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
        CError tError = new CError();
        tError.moduleName = "AutoSysCertSendOutBL";
        tError.functionName = "dealData";
        tError.errorMessage = "查询信息失败!";
        this.mErrors.addOneError(tError);
        return false;
      }
      mLOPRTManagerSchema.setSchema(tLOPRTManagerDB);
      if (!JudgeAutoSend(mLOPRTManagerSchema))
      return false;
    }
    else if (mOperate.equals("ASCSO2"))
    {
      if (!JudgeAutoSend(mLOPRTManager2Schema))
      return false;
    }
    if (flag == false)
    {
      mResult.clear();
      mResult.add("Nothing");
    }
    else if (flag == true)
    {
      LZSysCertifySchema tLZSysCertifyschema = new LZSysCertifySchema();
      tLZSysCertifyschema.setCertifyCode(CertifyCode);
      tLZSysCertifyschema.setCertifyNo(LOPRTManager.getV("PrtSeq"));
      tLZSysCertifyschema.setSendOutCom("A" + mGlobalInput.ManageCom);
      tLZSysCertifyschema.setReceiveCom("D" + LOPRTManager.getV("AgentCode"));
      logger.debug("D" + LOPRTManager.getV("AgentCode"));
      tLZSysCertifyschema.setHandler("SYS");
      tLZSysCertifyschema.setHandleDate(PubFun.getCurrentDate());

      mLZSysCertifySet.add(tLZSysCertifyschema);
    }
    return true;
  }

  /**
   * 判断打印过的单据是否需要自动发放
   */
  private boolean JudgeAutoSend(Schema s)
  {
    LOPRTManager = s ;
    LDCodeDB tLDCodeDB = new LDCodeDB();
    tLDCodeDB.setCodeType("syscertifycode");
    tLDCodeDB.setCode(s.getV("Code"));
    if (!tLDCodeDB.getInfo())
    {
//      // @@错误处理
//      this.mErrors.copyAllErrors(tLDCodeDB.mErrors);
//      CError tError = new CError();
//      tError.moduleName = "AutoSysCertSendOutBL";
//      tError.functionName = "JudgeAutoSend";
//      tError.errorMessage = "查询信息失败!";
//      this.mErrors.addOneError(tError)
//      return false;
      flag = false;
      return true;
    }
    CertifyCode = tLDCodeDB.getCodeName();
    LMCertifySubDB tLMCertifySubDB = new LMCertifySubDB();
    tLMCertifySubDB.setCertifyCode(tLDCodeDB.getCodeName());
    if (!tLMCertifySubDB.getInfo())
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tLMCertifySubDB.mErrors);
      CError tError = new CError();
      tError.moduleName = "AutoSysCertSendOutBL";
      tError.functionName = "JudgeAutoSend";
      tError.errorMessage = "查询信息失败!";
      this.mErrors.addOneError(tError);
      return false;
    }
    String tflag = tLMCertifySubDB.getAutoSend();
    if (StrTool.cTrim(tflag).equals("") || StrTool.cTrim(tflag).equals("N"))
    {
      flag = false;
    }
//    if (tflag.equals("Y"))
//    {
//
//    }
////    else if (tflag.equals("N"))
//    else
//    {
//      flag = false;
//    }
    return true;
  }

  /**
   * 从输入数据中得到所有对象
   *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    if (mOperate.equals("ASCSO"))
    {
      this.mLOPRTManagerSchema.setSchema((LOPRTManagerSchema)cInputData.getObjectByObjectName("LOPRTManagerSchema",0));
    }
    else if (mOperate.equals("ASCSO2"))
    {
      this.mLOPRTManager2Schema.setSchema((LOPRTManager2Schema)cInputData.getObjectByObjectName("LOPRTManager2Schema",0));
    }
    this.mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
    return true;
  }

  private boolean prepareOutputData()
  {
    try
    {
      this.mInputData.clear();
      this.mInputData.add(mGlobalInput);
      this.mInputData.add(mLZSysCertifySet);
//      mResult.clear();
//      mResult.add(this.mLDCodeSchema);
    }
    catch(Exception ex)
    {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="AutoSysCertSendOutBL";
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

  public static void main(String[] args) {
    GlobalInput tGI = new GlobalInput();
    tGI.ComCode="86110000";
    tGI.ManageCom="86110000";
    tGI.Operator="001";
    LOPRTManager2Schema tLOPRTManagerSchema = new LOPRTManager2Schema();
    tLOPRTManagerSchema.setCode("13");
    VData tVData = new VData();
    tVData.add(tLOPRTManagerSchema);
	   tVData.add(tGI);
    AutoSysCertSendOutBL tAutoSysCertSendOutBL = new AutoSysCertSendOutBL();
    tAutoSysCertSendOutBL.submitData(tVData,"ASCSO2");
  }
}
