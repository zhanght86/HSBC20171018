package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;



/**
 * <p>Title: 银行接口</p>
 * <p>Description: 银行返回文件撤销模块</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Sinosoft</p>
 * @author yanglh
 * @version 1.0
 */

public class CancelReadFromFileBL {
private static Logger logger = Logger.getLogger(CancelReadFromFileBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 传出数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 错误处理类 */
  public  CErrors mErrors = new CErrors();
  MMap map = new MMap();
  //业务数据
  private TransferData inTransferData = new TransferData();
  private GlobalInput inGlobalInput = new GlobalInput();

  private String serialNo = "";//批次号
  private String bankCode = "";//银行代码
  private String backReason = null;//回盘原因



  public CancelReadFromFileBL() {
  }

  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"READ"和""
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate) {
		PubLock tPubLock = new PubLock();
		String[] key = new String[2];
		try {
			this.mInputData = (VData) cInputData.clone();
			this.mOperate = cOperate;

			// 得到外部传入的数据,将数据备份到本类中
			if (!getInputData()) {
				return false;
			}

			logger.debug("---End getInputData---");

			// 增加并发校验
			key[0] = "Bank" + serialNo;
			key[1] = "SendS" + bankCode;

			if (!tPubLock.lock(key, "撤销" + serialNo + "的回盘数据")) {
				this.mErrors.copyAllErrors(tPubLock.mErrors);
				return false;
			}
				// 业务处理
				if (!dealData()) {
					return false;
				}
				logger.debug("---End dealData---");

			return true;
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this, "CancelReadFromFileBL");
			return false;
		}finally {
			if (!tPubLock.unLock(key)) {
				CError.buildErr(this, serialNo + "解锁失败:" + tPubLock.mErrors.getFirstError());
				return false;
			}
		}
	}

  /**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData()	{

      inTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
      inGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);

      serialNo = (String)inTransferData.getValueByName("serialNo");
      if("".equals(serialNo)){
    	  CError.buildErr(this, "未传入批次号，无法撤销！");
    	  return false;
      }
      bankCode = (String)inTransferData.getValueByName("bankCode");
      if("".equals(bankCode)){
    	  CError.buildErr(this, "未传入银行编码，无法撤销！");
    	  return false;
      }
      backReason = (String)inTransferData.getValueByName("backReason");
 

    return true;
  }

  /**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {

	  //	判断该条需要撤销的文件是否可以进行撤销
	  //	数据校验1	银行日志记录表LYBankLog存在该批次号的记录,且DealState字段必须为空
	  LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();
	  tLYBankLogSchema.setSerialNo(serialNo);
	  LYBankLogDB tLYBankLogDB = new LYBankLogDB();
	  tLYBankLogDB.setSerialNo(serialNo);
	  if(!tLYBankLogDB.getInfo()){
		  CError.buildErr(this, "银行日志记录表LYBankLog不存在该批次号的记录!");
		  return false;
	  }
	  if(tLYBankLogDB.getDealState()!=null){	
		  CError.buildErr(this, "该批次号的记录已经完成了回盘!");
		  return false;
	  }
	 
	  //	数据校验2银行返回盘记录备份表LYReturnFromBankB不存在该批次的记录；
	  LYReturnFromBankBDB tLYReturnFromBankBDB = new LYReturnFromBankBDB();
	  tLYReturnFromBankBDB.setSerialNo(serialNo);
	  LYReturnFromBankBSet tLYReturnFromBankBSet = new LYReturnFromBankBSet();
	  tLYReturnFromBankBSet = tLYReturnFromBankBDB.query();
	  if(tLYReturnFromBankBSet.size()>0){
		  CError.buildErr(this, "该批次号的记录已经完成了回盘!");
		  return false;
	  }
	  //	数据校验3 银行返回盘记录表LYReturnFromBank存在该批次的记录。
	  LYReturnFromBankDB tLYReturnFromBankDB = new LYReturnFromBankDB();
	  tLYReturnFromBankDB.setSerialNo(serialNo);
	  LYReturnFromBankSet tLYReturnFromBankSet = new LYReturnFromBankSet();
	  tLYReturnFromBankSet = tLYReturnFromBankDB.query();
	  if(tLYReturnFromBankSet.size()==0){
		  CError.buildErr(this, "该批次号未进行文件返回操作!");
		  return false;
	  }
	  //准备提交数据库的数据
	  //	将该批次号的银行日志表LYBankLog的记录保存到银行日志备份表中，同时在银行日志记录备份表中记录操作员、操作日期、操作机构、回滚原因信息
	  tLYBankLogSchema = tLYBankLogDB.getSchema();
	  LYBankLogBSchema tLYBankLogBSchema = new LYBankLogBSchema();
	  PubFun.copySchema(tLYBankLogBSchema,tLYBankLogSchema);
	  String maxNo = PubFun1.CreateMaxNo("BACKUPNO", 20);
	  tLYBankLogBSchema.setBackUpNo(maxNo);
	  tLYBankLogBSchema.setBackReason(backReason);
	  tLYBankLogBSchema.setBackOperator(inGlobalInput.Operator);
	  tLYBankLogBSchema.setBackCom(inGlobalInput.ManageCom);
	  tLYBankLogBSchema.setModifyDate(PubFun.getCurrentDate());
	  tLYBankLogBSchema.setModifyTime(PubFun.getCurrentTime());
	  map.put(tLYBankLogBSchema, "INSERT");
	  
	  //	将银行日志记录表LYBankLog的ReturnDate字段、ReturnOperator字段、InFile字段置空；
	  tLYBankLogSchema.setReturnDate("");
	  tLYBankLogSchema.setReturnOperator("");
	  tLYBankLogSchema.setInFile("");
	  map.put(tLYBankLogSchema,"UPDATE");
	  //	删除银行回盘记录表LYReturnFromBank的数据。
	  map.put(tLYReturnFromBankSet,"DELETE");
	  //提交数据库
	  mInputData.clear();
	  mInputData.add(map);
	  PubSubmit tPubSubmit = new PubSubmit();
	  if (!tPubSubmit.submitData(mInputData, ""))
      {
          // @@错误处理
          this.mErrors.copyAllErrors(tPubSubmit.mErrors);
          CError tError = new CError();
          tError.moduleName = "CancelReadFromFileBL";
          tError.functionName = "submitData";
          tError.errorMessage = "数据提交失败!";
          this.mErrors.addOneError(tError);
          return false;
      }
	  return true;
  }

  /**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    return mResult;
  }

  public static void main(String[] args)
  {}

  
  
}
