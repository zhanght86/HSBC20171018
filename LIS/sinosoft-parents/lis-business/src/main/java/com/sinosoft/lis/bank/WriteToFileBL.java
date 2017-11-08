package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import java.util.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.io.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 银行数据转换到文件模块</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class WriteToFileBL {
private static Logger logger = Logger.getLogger(WriteToFileBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 传出数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 错误处理类 */
  public  CErrors mErrors = new CErrors();

  //业务数据
  private LYSendToBankSchema inLYSendToBankSchema = new LYSendToBankSchema();
  private TransferData inTransferData = new TransferData();
  private String fileName = "";
  private String basePath = "";
  private String subPath = "";
  private GlobalInput inGlobalInput = new GlobalInput();

  private LYSendToBankSet outLYSendToBankSet = new LYSendToBankSet();
  private LYBankLogSet outLYBankLogSet = new LYBankLogSet();

  public WriteToFileBL() {
  }

  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"WRITE"和""
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate) {
    //将操作数据拷贝到本类中
    this.mInputData = (VData)cInputData.clone();
    this.mOperate = cOperate;

    //得到外部传入的数据,将数据备份到本类中
    if (!getInputData()) return false;
    logger.debug("---End getInputData---");
		// 增加并发校验
	  String serialNo=inLYSendToBankSchema.getSerialNo();
	String key = "Bank" + serialNo;
	PubLock tPubLock = new PubLock();
	if (!tPubLock.lock(key, "准备" + serialNo + "发盘文件")) {
		this.mErrors.copyAllErrors(tPubLock.mErrors);
		return false;
	}
	try{
    //进行业务处理
    if (!dealData()) return false;
    logger.debug("---End dealData---");

    //银行代收
    if (mOperate.equals("WRITE")) {
      //准备往后台的数据
      if (!prepareOutputData()) return false;
      logger.debug("---End prepareOutputData---");

      logger.debug("Start WriteToFile BLS Submit...");
      WriteToFileBLS tWriteToFileBLS = new WriteToFileBLS();
      if(tWriteToFileBLS.submitData(mInputData, cOperate) == false)	{
        // @@错误处理
        this.mErrors.copyAllErrors(tWriteToFileBLS.mErrors);
        mResult.clear();
        return false;
      }
      logger.debug("End WriteToFile BLS Submit...");

      //如果有需要处理的错误，则返回
      if (tWriteToFileBLS.mErrors .needDealError())  {
        this.mErrors.copyAllErrors(tWriteToFileBLS.mErrors ) ;
      }
    }
    //银行代付
    else if (mOperate.equals("")) {
    }
	}finally{
		if (!tPubLock.unLock(key)) {
			CError.buildErr(this, serialNo + "解锁失败:"
					+ tPubLock.mErrors.getFirstError());
			return false;
		}
  	}
    return true;
  }

  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData()	{
    try {
      inLYSendToBankSchema = (LYSendToBankSchema)mInputData.getObjectByObjectName("LYSendToBankSchema", 0);
      inTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
      inGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
    }
    catch (Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }

  /**
   * 获取发送银行数据信息
   * @param tLYSendToBankSchema
   * @return
   */
  private LYSendToBankSet getLYSendToBank(LYSendToBankSchema tLYSendToBankSchema) {
    LYSendToBankSet tLYSendToBankSet = tLYSendToBankSchema.getDB().query();

    return tLYSendToBankSet;
  }

  /**
   * 生成银行日志数据
   * @param tLYSendToBankSchema
   * @return
   */
  private LYBankLogSet getLYBankLog(LYSendToBankSchema tLYSendToBankSchema) {
    LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();
    LYBankLogSet tLYBankLogSet = new LYBankLogSet();

    //获取日志记录
    tLYBankLogSchema.setSerialNo(tLYSendToBankSchema.getSerialNo());
    tLYBankLogSet.set(tLYBankLogSchema.getDB().query());

    if (tLYBankLogSet.size() > 0) {
      tLYBankLogSchema.setSchema(tLYBankLogSet.get(1));

      //构建文件名，未加后缀名
      fileName = "B" + tLYBankLogSchema.getBankCode() + tLYBankLogSchema.getLogType() + tLYBankLogSchema.getSerialNo() + "(" + PubFun.getCurrentDate() + ")";
      basePath=getFilePath();
      subPath=getSubDir();
      
      //修改日志
      tLYBankLogSchema.setSendDate(PubFun.getCurrentDate());
      tLYBankLogSchema.setSendOperator(inGlobalInput.Operator);
      tLYBankLogSchema.setOutFile(new File(subPath,fileName + ".z").getPath().replaceAll("\\\\", "/"));

      tLYBankLogSet.clear();
      tLYBankLogSet.add(tLYBankLogSchema);
    }

    return tLYBankLogSet;
  }

  private String getFilePath() {
    LDSysVarSchema tLDSysVarSchema = new LDSysVarSchema();

    tLDSysVarSchema.setSysVar("SendToBankFilePath");
    tLDSysVarSchema = tLDSysVarSchema.getDB().query().get(1);

    String basepath= tLDSysVarSchema.getSysVarValue();
    if(basepath==null || basepath.equals("")){
    	throw new IllegalArgumentException("系统发盘基路径为空");
    }
    return basepath;
  }

  private String getSubDir() {
		String inputDate = PubFun.getCurrentDate();
		return inputDate.substring(0, 4) + "/" + inputDate.substring(5, 7)
				+ "/" + inputDate.substring(8, 10);
}

/**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    try {
      //生成银行文件数据
      if (mOperate.equals("WRITE")) {

        //获取发送银行数据信息
        LYSendToBankSet tLYSendToBankSet = getLYSendToBank(inLYSendToBankSchema);
        if (tLYSendToBankSet.size() == 0) throw new NullPointerException("无发送银行数据！");

        //生成银行日志数据,构建文件名
        LYBankLogSet tLYBankLogSet = getLYBankLog(inLYSendToBankSchema);
        if (tLYBankLogSet.size() == 0) throw new NullPointerException("无银行日志数据！");

        //生成发送文件全路径
//        fileName = new File(getFilePath(), fileName).getAbsolutePath();
        String bankCode = tLYBankLogSet.get(1).getBankCode();          //因为传入的inLYSendToBankSchema只有serialno字段被赋值了
        String busstype = tLYBankLogSet.get(1).getLogType();

        outLYSendToBankSet.set(tLYSendToBankSet);
        outLYBankLogSet.set(tLYBankLogSet);
        inTransferData.setNameAndValue("fileName", fileName);
        inTransferData.setNameAndValue("basePath", basePath);
        inTransferData.setNameAndValue("subPath", subPath);
        inTransferData.setNameAndValue("bankCode", bankCode);
        inTransferData.setNameAndValue("busstype", busstype);          //增加业务处理类型标志
      
      }
    }
    catch(Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "WriteToFileBL";
      tError.functionName = "dealData";
      tError.errorMessage = "数据处理错误! " + e.getMessage();
      this.mErrors .addOneError(tError);
      return false;
    }

    return true;
  }

  /**
   * 准备往后层输出所需要的数据
   * @return 如果准备数据时发生错误则返回false,否则返回true
   */
  private boolean prepareOutputData() {
    mInputData = new VData();

    try {
      mInputData.add(outLYSendToBankSet);
      mInputData.add(outLYBankLogSet);
      mInputData.add(inTransferData);
    }
    catch(Exception ex) {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="WriteToFileBL";
      tError.functionName="prepareOutputData";
      tError.errorMessage="在准备往后层处理所需要的数据时出错。";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }

  /**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    mResult.add(fileName);
    return mResult;
  }

  public static void main(String[] args) {
    WriteToFileBL writeToFileBL1 = new WriteToFileBL();
  }
}
