package com.sinosoft.lis.bank;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LJSPayGrpDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubLock;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LJSPayGrpSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LYBankLogSchema;
import com.sinosoft.lis.schema.LYDupPaySchema;
import com.sinosoft.lis.schema.LYReturnFromBankBSchema;
import com.sinosoft.lis.schema.LYReturnFromBankSchema;
import com.sinosoft.lis.schema.LYSendToBankBSchema;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vschema.LJSPayGrpSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYDupPaySet;
import com.sinosoft.lis.vschema.LYReturnFromBankBSet;
import com.sinosoft.lis.vschema.LYReturnFromBankSet;
import com.sinosoft.lis.vschema.LYSendToBankBSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 银行数据转换到业务系统</p>
 * <p>Description: 转换标记（ConvertFlag）说明：
 * 初始（null）
 * 扣款失败（1）
 * 重复交费（2）
 * 首期直接交费（5）
 * 催收交费且已经交费核销（3）
 * 首期事后交费（6）
 * 事后选银行转账（9）
 * 正常催收且已经在柜台交费（4）
 * 续期催收（7）</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class GetReturnFromBankBL {
private static Logger logger = Logger.getLogger(GetReturnFromBankBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 传出数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 错误处理类 */
  public  CErrors mErrors = new CErrors();

  //业务数据
  private String totalMoney = "";
  private int sumNum = 0;
  private String serialNo = "";
  private Reflections mReflections = new Reflections();
  private GlobalInput mGlobalInput = new GlobalInput();

  private LYReturnFromBankSchema inLYReturnFromBankSchema = new LYReturnFromBankSchema();
  private TransferData inTransferData = new TransferData();
  private LYReturnFromBankSet inLYReturnFromBankSet = new LYReturnFromBankSet();

  private LYDupPaySet outLYDupPaySet = new LYDupPaySet();
  private LYBankLogSet outLYBankLogSet = new LYBankLogSet();
  private LYReturnFromBankSet outDelLYReturnFromBankSet = new LYReturnFromBankSet();
  private LJSPaySet outDelLJSPaySet = new LJSPaySet();
  private LJTempFeeSet outLJTempFeeSet = new LJTempFeeSet();
  private LJSPaySet outLJSPaySet = new LJSPaySet();
  private LYReturnFromBankBSet outLYReturnFromBankBSet = new LYReturnFromBankBSet();
  private LYSendToBankSet outDelLYSendToBankSet = new LYSendToBankSet();
  private LYSendToBankBSet outLYSendToBankBSet = new LYSendToBankBSet();
  private LJTempFeeClassSet outLJTempFeeClassSet = new LJTempFeeClassSet();
  private LJTempFeeSet outUpdateLJTempFeeSet = new LJTempFeeSet();
  private LJTempFeeClassSet outUpdateLJTempFeeClassSet = new LJTempFeeClassSet();

  private LOPRTManagerSet outLOPRTManagerSet = new LOPRTManagerSet();

  public GetReturnFromBankBL() {
  }

  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
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
	  String bankcode=getBankCode(inLYReturnFromBankSchema);
	  if(bankcode.equals("")){
		  CError.buildErr(this, "BankCode error");
		  return false;
	  }
	String key = "SendS" + bankcode;
	PubLock tPubLock = new PubLock();
	if (!tPubLock.lock(key, "准备" + bankcode + "回盘确认数据")) {
		this.mErrors.copyAllErrors(tPubLock.mErrors);
		return false;
	}
	try{
    //进行业务处理
    if (!dealData()) return false;
    logger.debug("---End dealData---");

    //银行代收
    if (mOperate.equals("GETMONEY")) {
      //准备往后台的数据
      if (!prepareOutputData()) return false;
      logger.debug("---End prepareOutputData---");

      PubSubmit tPubSubmit=new PubSubmit();
      if(!tPubSubmit.submitData(mInputData)){
    	  CError.buildErr(this, "数据提交失败!"+tPubSubmit.mErrors.getFirstError());
    	  return false;
      }
    }
    //银行代付
    else if (mOperate.equals("PAYMONEY")) {
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

  private String getBankCode(LYReturnFromBankSchema tLYReturnFromBankSchema) {
	String sql="select comcode from ldbank where exists(select * from lybanklog where bankcode=ldbank.bankcode and serialno='"+"?serialno?"+"')";
	try{
	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	sqlbv1.sql(sql);
	sqlbv1.put("serialno", tLYReturnFromBankSchema.getSerialNo());
	return new ExeSQL().getOneValue(sqlbv1);
	}catch(Exception ex){
		return "";
	}
}

/**
   * 将外部传入的数据分解到本类的属性中
   * @param: 无
   * @return: boolean
   */
  private boolean getInputData()	{
    try {
      inLYReturnFromBankSchema = (LYReturnFromBankSchema)mInputData.getObjectByObjectName("LYReturnFromBankSchema", 0);
      inTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);

      mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
    }
    catch (Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "GetReturnFromBankBL";
      tError.functionName = "getInputData";
      tError.errorMessage = "接收数据失败!!";
      this.mErrors .addOneError(tError) ;
      return false;
    }

    return true;
  }

  /**
   * 获取交费日期在设置的日期区间内的总应付表记录
   * @param startDate
   * @param endDate
   * @return
   */
//  private LJSPaySet getLJSPayByPaydate(String startDate, String endDate) {
//    String tSql = "select * from LJSPay where PayDate >= '" + startDate
//                + "' and PayDate <= '" + endDate + "'"
//                + " and BankOnTheWayFlag = 0";
//
//    LJSPayDB tLJSPayDB = new LJSPayDB();
//    LJSPaySet tLJSPaySet = tLJSPayDB.executeQuery(tSql);
//
//    if (tLJSPayDB.mErrors.getErrorCount()>0) return null;
//    else return tLJSPaySet;
//  }

  /**
   * 生成送银行表数据
   * @param tLJSPaySet
   * @return
   */
//  private LYReturnFromBankSet getReturnFromBank(LJSPaySet tLJSPaySet) {
//    int i;
//    serialNo = PubFun1.CreateMaxNo("1", 20);
//    LYReturnFromBankSet tLYReturnFromBankSet = new LYReturnFromBankSet();
//
//    for (i=0; i<tLJSPaySet.size(); i++) {
//      LJSPaySchema tLJSPaySchema = tLJSPaySet.get(i + 1);
//
//      //生成送银行表数据
//      LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();
//
//      tLYReturnFromBankSet.add(tLYReturnFromBankSchema);
//
//    }
//
//    return tLYReturnFromBankSet;
//  }

  /**
   * 获取银行返回数据
   * @param inLYReturnFromBankSchema
   * @return
   */
  private LYReturnFromBankSet getLYReturnFromBank(LYReturnFromBankSchema inLYReturnFromBankSchema) {
    LYReturnFromBankSet tLYReturnFromBankSet = inLYReturnFromBankSchema.getDB().query();

    return tLYReturnFromBankSet;
  }

  /**
   * 获取银行扣款成功标志信息
   * @param tLYReturnFromBankSchema
   * @return
   */
  private String getBankSuccFlag(LYReturnFromBankSchema tLYReturnFromBankSchema) {
    try {
      LDBankSchema tLDBankSchema = new LDBankSchema();

      tLDBankSchema.setBankCode(tLYReturnFromBankSchema.getBankCode());
      tLDBankSchema.setSchema(tLDBankSchema.getDB().query().get(1));

      return tLDBankSchema.getAgentPaySuccFlag();
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new NullPointerException("获取银行扣款成功标志信息失败！(getBankSuccFlag) " + e.getMessage());
    }
  }

  /**
   * 校验银行扣款成功标志
   * @param bankSuccFlag
   * @param tLYReturnFromBankSchema
   * @return
   */
  private boolean verifyBankSuccFlag(String bankSuccFlag, LYReturnFromBankSchema tLYReturnFromBankSchema) {
    int i;
    boolean passFlag = false;

    String[] arrSucc = PubFun.split(bankSuccFlag, ";");
    String tSucc = tLYReturnFromBankSchema.getBankSuccFlag();

    for (i=0; i<arrSucc.length; i++) {
      if (arrSucc[i].equals(tSucc)) {
        passFlag = true;
        break;
      }
    }

    return passFlag;
  }

  /**
   * 校验银行扣款成功与否
   */
  private void verifyBankSucc() {
//    String bankSuccFlag = getBankSuccFlag(inLYReturnFromBankSet.get(1));

    for (int i = 0; i < inLYReturnFromBankSet.size(); i++) {
			LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet
					.get(i + 1);
			if (tLYReturnFromBankSchema.getConvertFlag() != null)
				continue;

			// 如果扣款失败，则设置转换标记为1
//			if (!verifyBankSuccFlag(bankSuccFlag, tLYReturnFromBankSchema)) {
			if(!"0000".equals(tLYReturnFromBankSchema.getBankSuccFlag())){	//在读入文件时使用统一正确编码
				tLYReturnFromBankSchema.setConvertFlag("1");
			}
		}
  }

  /**
   * 校验财务总金额
   * @param tLYReturnFromBankSet
   * @param totalMoney
   * @return
   */
  private boolean confirmTotalMoney(LYReturnFromBankSet tLYReturnFromBankSet, String totalMoney) {
    int i;
    double sumMoney = 0;
    double fTotalMoney = Double.valueOf(totalMoney).doubleValue();

    for (i=0; i<tLYReturnFromBankSet.size(); i++) {
      LYReturnFromBankSchema tLYReturnFromBankSchema = tLYReturnFromBankSet.get(i + 1);
      if (tLYReturnFromBankSchema.getConvertFlag() != null) continue;

      sumMoney = sumMoney + tLYReturnFromBankSchema.getPayMoney();
      //转换精度
      sumMoney = Double.parseDouble((new DecimalFormat("0.00")).format(sumMoney));
      sumNum = sumNum + 1;
    }

    return (fTotalMoney == sumMoney);
  }

  /**
   * 记录入重复交费记录表
   * @param tLYReturnFromBankSchema
   */
  private void setLYDupPay(LYReturnFromBankSchema tLYReturnFromBankSchema) {
    LYDupPaySchema tLYDupPaySchema = new LYDupPaySchema();
    mReflections.transFields(tLYDupPaySchema, tLYReturnFromBankSchema);
    tLYDupPaySchema.setDataType("S");
    tLYDupPaySchema.setMakeDate(PubFun.getCurrentDate());
    tLYDupPaySchema.setMakeTime(PubFun.getCurrentTime());
    outLYDupPaySet.add(tLYDupPaySchema);
  }

  /**
   * 校验每笔金额
   */
  private void verifyUnitMoney() {
    int i;
    ExeSQL tExeSQL = new ExeSQL();
    for (i=0; i<inLYReturnFromBankSet.size(); i++) {
      LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet.get(i+1);
      if (tLYReturnFromBankSchema.getConvertFlag() != null) continue;

      //tongmeng 2011-01-24 add
      //新契约不生成应收
      
      if(tLYReturnFromBankSchema.getNoType()!=null&&!tLYReturnFromBankSchema.getNoType().equals("9"))
      {
    	  
    	  LJSPaySchema tLJSPaySchema = new LJSPaySchema();
    	  tLJSPaySchema.setGetNoticeNo(tLYReturnFromBankSchema.getPayCode());
    	  tLJSPaySchema.setSchema(tLJSPaySchema.getDB().query().get(1)); //需要判断ljspay记录是否存在，否则直接取get(1)会出错

    	  //扣款小于应收表中的信息，则转入重复交费表（设置转换标记为2），否则通过
    	  //使用客户账户后,可能存在以下情况.取消下面的校验
    	  /*
    	  if (tLJSPaySchema.getSumDuePayMoney() > tLYReturnFromBankSchema.getPayMoney()
    			  && tLYReturnFromBankSchema.getPayMoney() > 0)              //回盘信息不含扣款金额时交费字段默认为0
    	  {
    		  setLYDupPay(tLYReturnFromBankSchema);
    		  tLYReturnFromBankSchema.setConvertFlag("2");
    	  }*/
      }
      else
      {
    	  //如果是新契约,校验暂收费
    	  String tSQL_TempfeeClass = "select sum(paymoney) from ljtempfeeclass where tempfeeno='"+"?tempfeeno?"+"' and paymode='4'";
    	  SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    		sqlbv2.sql(tSQL_TempfeeClass);
    		sqlbv2.put("tempfeeno", tLYReturnFromBankSchema.getPayCode());
    	  String tShouldPayMoney = tExeSQL.getOneValue(sqlbv2);
    	  if (Double.parseDouble(tShouldPayMoney) > tLYReturnFromBankSchema.getPayMoney()
    			  && tLYReturnFromBankSchema.getPayMoney() > 0)              //回盘信息不含扣款金额时交费字段默认为0
    	  {
    		  setLYDupPay(tLYReturnFromBankSchema);
    		  tLYReturnFromBankSchema.setConvertFlag("2");
    	  }
      }
    }
  }

  /**
   * 校验暂交费表
   * @param tLYReturnFromBankSchema
   * @return
   */
  private boolean verifyLJTempFee(LYReturnFromBankSchema tLYReturnFromBankSchema) {
    LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();

    tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
    LJTempFeeSet tLJTempFeeSet = tLJTempFeeSchema.getDB().query();

    if (tLJTempFeeSet.size() > 0) return true;
    else return false;
  }

  /**
   * 通过银行返回数据获取应收表数据
   * @param tLYReturnFromBankSchema
   * @return
   */
  private LJSPaySchema getLJSPayByLYReturnFromBank(LYReturnFromBankSchema tLYReturnFromBankSchema) {
    try {
      LJSPaySchema tLJSPaySchema = new LJSPaySchema();

      tLJSPaySchema.setGetNoticeNo(tLYReturnFromBankSchema.getPayCode());
      return tLJSPaySchema.getDB().query().get(1);
    }
    catch (Exception e) {
      //总应收表无数据，表示扣款失败，并且已经用其它方式交费
    	throw new RuntimeException("总应收表无数据"+tLYReturnFromBankSchema.getPayCode());
    }
  }

  private void setLJTempFeeCont(LYReturnFromBankSchema tLYReturnFromBankSchema)
  {
    LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
    LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(tLYReturnFromBankSchema);
    String tLimit = PubFun.getNoLimit(tLJSPaySchema.getManageCom());
    String serNo = PubFun1.CreateMaxNo("SERIALNO",tLimit);
    String tOtherNo = "";
    if (tLJSPaySchema.getOtherNoType().equals("1")) //集体保单号
    {
      LJSPayGrpDB tLJSPayGrpDB = new LJSPayGrpDB();
      LJSPayGrpSet tLJSPayGrpSet = new LJSPayGrpSet();
      tLJSPayGrpDB.setGetNoticeNo(tLJSPaySchema.getGetNoticeNo());
      tLJSPayGrpSet=tLJSPayGrpDB.query();
      for (int i=1;i<=tLJSPayGrpSet.size();i++)
      {
        LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
        LJSPayGrpSchema tLJSPayGrpSchema = new LJSPayGrpSchema();
        tLJSPayGrpSchema=tLJSPayGrpSet.get(i);
        tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
        //首期直接交费
        if (tLYReturnFromBankSchema.getDoType().equals("2")) {
          tLJTempFeeSchema.setTempFeeType("1");
          tLJTempFeeSchema.setOtherNoType("0");
        }
        else if (tLYReturnFromBankSchema.getDoType().equals("1")) {
          //首期事后交费
          if (tLYReturnFromBankSchema.getNoType().equals("6")) {
            tLJTempFeeSchema.setTempFeeType("1");
            tLJTempFeeSchema.setOtherNoType("0");
          }
          //正常续期催收
          else if (tLYReturnFromBankSchema.getNoType().equals("2")) {
            tLJTempFeeSchema.setTempFeeType("2");
            tLJTempFeeSchema.setOtherNoType("1");
          }
        }
        tLJTempFeeSchema.setRiskCode(tLJSPayGrpSchema.getRiskCode());
        tLJTempFeeSchema.setPayIntv(tLJSPayGrpSchema.getPayIntv());
        tLJTempFeeSchema.setOtherNo(tLJSPayGrpSchema.getGrpPolNo());
        tLJTempFeeSchema.setPayMoney(tLJSPayGrpSchema.getSumDuePayMoney());
        tLJTempFeeSchema.setPayDate(tLYReturnFromBankSchema.getSendDate());
         if(tLYReturnFromBankSchema.getBankDealDate()!=null && !tLYReturnFromBankSchema.getBankDealDate().equals(""))
        	 tLJTempFeeSchema.setEnterAccDate(tLYReturnFromBankSchema.getBankDealDate());
         else
        	 tLJTempFeeSchema.setEnterAccDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setManageCom(tLJSPayGrpSchema.getManageCom());
        tLJTempFeeSchema.setAgentGroup(tLJSPayGrpSchema.getAgentGroup());
        tLJTempFeeSchema.setAgentCode(tLJSPayGrpSchema.getAgentCode());
        tLJTempFeeSchema.setCurrency(tLJSPayGrpSchema.getCurrency());
        tLJTempFeeSchema.setConfFlag("0");
        tLJTempFeeSchema.setSerialNo(serNo);
        tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
        tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
        tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
        tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
        outLJTempFeeSet.add(tLJTempFeeSchema);
     }
    }
    else if (tLJSPaySchema.getOtherNoType().equals("2") ||tLJSPaySchema.getOtherNoType().equals("6")) //个人保单号
    {
      ExeSQL tExeSQL = new ExeSQL();
      SSRS tSSRS = new SSRS();
      String tSql="select riskcode,otherno,SumDuePayMoney from LJSPay where GetNoticeNo='"+"?GetNoticeNo?"+"'";
      SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("GetNoticeNo", tLJSPaySchema.getGetNoticeNo());
      tSSRS = tExeSQL.execSQL(sqlbv3);
      for (int i=1;i<=tSSRS.MaxRow;i++)
      {
        LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
        tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
        //首期直接交费
        if (tLYReturnFromBankSchema.getDoType().equals("2")) {
          tLJTempFeeSchema.setTempFeeType("1");
          tLJTempFeeSchema.setOtherNoType("0");
        }
        else if (tLYReturnFromBankSchema.getDoType().equals("1")) {
          //首期事后交费
          if (tLYReturnFromBankSchema.getNoType().equals("6")) {
            tLJTempFeeSchema.setTempFeeType("1");
            tLJTempFeeSchema.setOtherNoType("0");
          }
          //正常续期催收
          else if (tLYReturnFromBankSchema.getNoType().equals("2")) {
            tLJTempFeeSchema.setTempFeeType("2");
            tLJTempFeeSchema.setOtherNoType("0");
          }
        }

        LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
        LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
        tLJSPayPersonDB.setGetNoticeNo(tLJSPaySchema.getGetNoticeNo());
//        tLJSPayPersonDB.setRiskCode(tSSRS.GetText(i,1));
        tLJSPayPersonSet=tLJSPayPersonDB.query();
        LJSPayPersonSchema tLJSPayPersonSchema=tLJSPayPersonSet.get(1);

        tLJTempFeeSchema.setRiskCode(tSSRS.GetText(i,1));
        tLJTempFeeSchema.setOtherNo(tLJSPayPersonSchema.getContNo());
        tOtherNo = tLJSPayPersonSchema.getContNo();
        //tLYReturnFromBankSchema
        //tLJTempFeeSchema.setPayMoney(tSSRS.GetText(i,3));
        tLJTempFeeSchema.setPayMoney(tLYReturnFromBankSchema.getPayMoney());
        tLJTempFeeSchema.setPayDate(tLYReturnFromBankSchema.getSendDate());
        tLJTempFeeSchema.setEnterAccDate(tLYReturnFromBankSchema.getBankDealDate());
        tLJTempFeeSchema.setPayIntv(tLJSPayPersonSchema.getPayIntv());
        tLJTempFeeSchema.setManageCom(tLJSPayPersonSchema.getManageCom());
        tLJTempFeeSchema.setAgentGroup(tLJSPayPersonSchema.getAgentGroup());
        tLJTempFeeSchema.setAgentCode(tLJSPayPersonSchema.getAgentCode());
        tLJTempFeeSchema.setCurrency(tLJSPayPersonSchema.getCurrency());
        tLJTempFeeSchema.setConfFlag("0");
        tLJTempFeeSchema.setSerialNo(serNo);
        tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
        tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
        tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
        tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
        outLJTempFeeSet.add(tLJTempFeeSchema);
     }
    } else if (tLJSPaySchema.getOtherNoType().equals("10")||tLJSPaySchema.getOtherNoType().equals("5")) { // 保全,lp
			LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
			tLJTempFeeSchema.setTempFeeNo(tLJSPaySchema.getGetNoticeNo());
			tLJTempFeeSchema.setTempFeeType("4");
			if(tLJSPaySchema.getOtherNoType().equals("10"))
				tLJTempFeeSchema.setOtherNoType("10");
			else if(tLJSPaySchema.getOtherNoType().equals("5"))
				tLJTempFeeSchema.setOtherNoType("2");

			tLJTempFeeSchema.setRiskCode(tLJSPaySchema.getRiskCode());
			tLJTempFeeSchema.setOtherNo(tLJSPaySchema.getOtherNo());
			tOtherNo = tLJSPaySchema.getOtherNo();
			tLJTempFeeSchema.setPayMoney(tLJSPaySchema.getSumDuePayMoney());
			tLJTempFeeSchema.setPayDate(tLYReturnFromBankSchema.getSendDate());
			tLJTempFeeSchema.setEnterAccDate(tLYReturnFromBankSchema
					.getBankDealDate());
			// tLJTempFeeSchema.setPayIntv(tLJSPayPersonSchema.getPayIntv());
			tLJTempFeeSchema.setManageCom(tLJSPaySchema.getManageCom());
			tLJTempFeeSchema.setAgentGroup(tLJSPaySchema.getAgentGroup());
			tLJTempFeeSchema.setAgentCode(tLJSPaySchema.getAgentCode());
			tLJTempFeeSchema.setCurrency(tLJSPaySchema.getCurrency());
			tLJTempFeeSchema.setConfFlag("0");
			tLJTempFeeSchema.setSerialNo(serNo);
			tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
			tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
			tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
			tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
			tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
			outLJTempFeeSet.add(tLJTempFeeSchema);
		}
    //为暂交费分类表设置数据
    tLJTempFeeClassSchema.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
    tLJTempFeeClassSchema.setPayMode("4");
    tLJTempFeeClassSchema.setPayMoney(tLYReturnFromBankSchema.getPayMoney());
    tLJTempFeeClassSchema.setPayDate(tLYReturnFromBankSchema.getSendDate());
    tLJTempFeeClassSchema.setEnterAccDate(tLYReturnFromBankSchema.getBankDealDate());
    tLJTempFeeClassSchema.setConfFlag("0");
    tLJTempFeeClassSchema.setSerialNo(serNo);
    tLJTempFeeClassSchema.setOperator(this.mGlobalInput.Operator);
    tLJTempFeeClassSchema.setMakeDate(PubFun.getCurrentDate());
    tLJTempFeeClassSchema.setMakeTime(PubFun.getCurrentTime());
    tLJTempFeeClassSchema.setModifyDate(PubFun.getCurrentDate());
    tLJTempFeeClassSchema.setModifyTime(PubFun.getCurrentTime());
    tLJTempFeeClassSchema.setManageCom(tLJSPaySchema.getManageCom());
    tLJTempFeeClassSchema.setCurrency(tLJSPaySchema.getCurrency());
    tLJTempFeeClassSchema.setBankCode(tLYReturnFromBankSchema.getBankCode());
    tLJTempFeeClassSchema.setOtherNo(tOtherNo);
    tLJTempFeeClassSchema.setBankAccNo(tLYReturnFromBankSchema.getAccNo());
    tLJTempFeeClassSchema.setAccName(tLYReturnFromBankSchema.getAccName());
    tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
    tLJTempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
    tLJTempFeeClassSchema.setChequeNo("000000");
    outLJTempFeeClassSet.add(tLJTempFeeClassSchema);
  }

  /**
   * 转入暂交费表
   * @param tLYReturnFromBankSchema
   */
  private void setLJTempFee(LYReturnFromBankSchema tLYReturnFromBankSchema) {
    LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
    LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
    LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(tLYReturnFromBankSchema);

    tLJTempFeeSchema.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
    //首期直接交费
    if (tLYReturnFromBankSchema.getDoType().equals("2")) {
      tLJTempFeeSchema.setTempFeeType("1");
      tLJTempFeeSchema.setOtherNoType("0");
    }
    else if (tLYReturnFromBankSchema.getDoType().equals("1")) {
      //首期事后交费
      if (tLYReturnFromBankSchema.getNoType().equals("6")) {
        tLJTempFeeSchema.setTempFeeType("1");
        tLJTempFeeSchema.setOtherNoType("0");
      }
      //正常续期催收
      else if (tLYReturnFromBankSchema.getNoType().equals("2")) {
        tLJTempFeeSchema.setTempFeeType("2");
        tLJTempFeeSchema.setOtherNoType("0");
      }
    }

    tLJTempFeeSchema.setOtherNo(tLJSPaySchema.getOtherNo());
    tLJTempFeeSchema.setEnterAccDate(tLYReturnFromBankSchema.getBankDealDate());
    tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
    tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
    tLJTempFeeSchema.setConfFlag("0");
    //流水号
    String tLimit = PubFun.getNoLimit(tLJSPaySchema.getManageCom());
    String serNo = PubFun1.CreateMaxNo("SERIALNO",tLimit);
    tLJTempFeeSchema.setSerialNo(serNo);
    tLJTempFeeSchema.setManageCom(tLJSPaySchema.getManageCom());
    tLJTempFeeSchema.setAgentGroup(tLJSPaySchema.getAgentGroup());
    tLJTempFeeSchema.setAgentCode(tLJSPaySchema.getAgentCode());
    tLJTempFeeSchema.setCurrency(tLJSPaySchema.getCurrency());
    tLJTempFeeSchema.setRiskCode(tLJSPaySchema.getRiskCode());
    tLJTempFeeSchema.setPayMoney(tLYReturnFromBankSchema.getPayMoney());
    tLJTempFeeSchema.setPayDate(tLYReturnFromBankSchema.getSendDate());
    tLJTempFeeSchema.setOperator(mGlobalInput.Operator);
    tLJTempFeeSchema.setMakeDate(PubFun.getCurrentDate());
    tLJTempFeeSchema.setMakeTime(PubFun.getCurrentTime());
    tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
    tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());

    //为暂交费分类表设置数据
    mReflections.transFields(tLJTempFeeClassSchema, tLJTempFeeSchema);
    tLJTempFeeClassSchema.setPayMode("4");
    tLJTempFeeClassSchema.setBankCode(tLYReturnFromBankSchema.getBankCode());
    tLJTempFeeClassSchema.setBankAccNo(tLYReturnFromBankSchema.getAccNo());
    tLJTempFeeClassSchema.setAccName(tLYReturnFromBankSchema.getAccName());

    tLJTempFeeClassSchema.setEnterAccDate(tLYReturnFromBankSchema.getBankDealDate());
    tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
    tLJTempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
    tLJTempFeeClassSchema.setConfFlag("0");

    //流水号
    tLJTempFeeClassSchema.setSerialNo(serNo);
    tLJTempFeeClassSchema.setManageCom(tLJSPaySchema.getManageCom());

    outLJTempFeeSet.add(tLJTempFeeSchema);
    outLJTempFeeClassSet.add(tLJTempFeeClassSchema);
  }

  /**
   * 更新暂交费表，适用于事后选择银行转账
   * @param tLYReturnFromBankSchema
   */
  private void updateLJTempFee(LYReturnFromBankSchema tLYReturnFromBankSchema) {
//    LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(tLYReturnFromBankSchema);

    //核销暂交费分类表，设置到帐日期
    LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
    tLJTempFeeClassDB.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
    tLJTempFeeClassDB.setPayMode("4");  //银行转账
    LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.query();

    for (int i=0; i<tLJTempFeeClassSet.size(); i++) {
      LJTempFeeClassSchema tLJTempFeeClassSchema = tLJTempFeeClassSet.get(i + 1);

      //修改到帐日期
      tLJTempFeeClassSchema.setEnterAccDate(tLYReturnFromBankSchema.getBankDealDate());
      tLJTempFeeClassSchema.setConfMakeDate(PubFun.getCurrentDate());
      tLJTempFeeClassSchema.setConfMakeTime(PubFun.getCurrentTime());
      tLJTempFeeClassSchema.setModifyDate(PubFun.getCurrentDate());
      tLJTempFeeClassSchema.setModifyTime(PubFun.getCurrentTime());
    }

    //校验该暂交费号是否已经全部到帐，是则设置暂交费表的到帐日期，否则不设置
    LJTempFeeClassDB tLJTempFeeClassDB2 = new LJTempFeeClassDB();
    String strSql = "select * from LJTempFeeClass where TempFeeNo='"
                  + "?TempFeeNo?" + "' and PayMode<>'4'";
    SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	sqlbv4.sql(strSql);
	sqlbv4.put("TempFeeNo", tLYReturnFromBankSchema.getPayCode());
    LJTempFeeClassSet tLJTempFeeClassSet2 = tLJTempFeeClassDB2.executeQuery(sqlbv4);

    boolean isAllEnterAcc = true;
    for (int j=0; j<tLJTempFeeClassSet2.size(); j++) {
      if (tLJTempFeeClassSet2.get(j+1).getEnterAccDate() == null) {
        isAllEnterAcc = false;
        break;
      }
    }

    if (isAllEnterAcc) {
      LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
      tLJTempFeeDB.setTempFeeNo(tLYReturnFromBankSchema.getPayCode());
      LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.query();

      for (int i=0; i<tLJTempFeeSet.size(); i++) {
        LJTempFeeSchema tLJTempFeeSchema = tLJTempFeeSet.get(i + 1);

        //修改到帐日期
        tLJTempFeeSchema.setEnterAccDate(tLYReturnFromBankSchema.getBankDealDate());
        tLJTempFeeSchema.setConfMakeDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setConfMakeTime(PubFun.getCurrentTime());
        tLJTempFeeSchema.setModifyDate(PubFun.getCurrentDate());
        tLJTempFeeSchema.setModifyTime(PubFun.getCurrentTime());
      }

      outUpdateLJTempFeeSet.add(tLJTempFeeSet);
    }

    outUpdateLJTempFeeClassSet.add(tLJTempFeeClassSet);
  }

  /**
   * 校验应收表
   */
  private void verifyLJSPay() {
    int i;

    try {
      for (i=0; i<inLYReturnFromBankSet.size(); i++) {
        //遍历每一条返回盘记录
        LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet.get(i + 1);
        if (tLYReturnFromBankSchema.getConvertFlag() != null) continue;

        //校验是否首期直接交费
        if (tLYReturnFromBankSchema.getDoType().equals("2")) {
          //记录暂交费表，和暂交费分类表
          setLJTempFee(tLYReturnFromBankSchema);
          tLYReturnFromBankSchema.setConvertFlag("5");
          continue;
        }

        //属于应收表中的催收交费
        if (tLYReturnFromBankSchema.getDoType().equals("1")) {
          //查询应收表
        	//tongmeng 2011-01-25 modify
        	//新契约不生成应收
//          //事后选银行转账
          if (tLYReturnFromBankSchema.getNoType().equals("9")) {
           // outDelLJSPaySet.add(tLJSPaySchema);
            tLYReturnFromBankSchema.setConvertFlag("9");

            //更新暂交费表
            updateLJTempFee(tLYReturnFromBankSchema);
          }
          else
          {
          LJSPaySchema tLJSPaySchema = new LJSPaySchema();
          tLJSPaySchema.setGetNoticeNo(tLYReturnFromBankSchema.getPayCode());
          LJSPaySet tLJSPaySet = tLJSPaySchema.getDB().query();

          //应收表不存在，表示已经交费核销，记录入重复交费记录表
          if (tLJSPaySet.size() == 0) {
            setLYDupPay(tLYReturnFromBankSchema);
            tLYReturnFromBankSchema.setConvertFlag("3");
            continue;
          }

          //应收表记录存在
          if (tLJSPaySet.size() > 0) {
            tLJSPaySchema = tLJSPaySet.get(1);

            //首期事后交费
            if (tLYReturnFromBankSchema.getNoType().equals("6")) {
              outDelLJSPaySet.add(tLJSPaySchema);
              tLYReturnFromBankSchema.setConvertFlag("6");
            }

//            //事后选银行转账
//            if (tLYReturnFromBankSchema.getNoType().equals("9")) {
//              outDelLJSPaySet.add(tLJSPaySchema);
//              tLYReturnFromBankSchema.setConvertFlag("9");
//
//              //更新暂交费表
//              updateLJTempFee(tLYReturnFromBankSchema);
//            }

            //续期催收
            if (tLYReturnFromBankSchema.getNoType().equals("2")
            		|| tLYReturnFromBankSchema.getNoType().equals("10")
            		|| tLYReturnFromBankSchema.getNoType().equals("5")) {
              tLYReturnFromBankSchema.setConvertFlag("7");

              //生成续期划帐成功通知书
              setLOPRTManager(tLYReturnFromBankSchema, "48");
            }

            //正常催收
            if (tLYReturnFromBankSchema.getNoType().equals("2") || tLYReturnFromBankSchema.getNoType().equals("6") 
            		|| tLYReturnFromBankSchema.getNoType().equals("10")/*保全*/
            		|| tLYReturnFromBankSchema.getNoType().equals("5")/*保全*/) {
              //校验暂交费表，有数据表示已经在柜台交费，转入重复交费表
              if (verifyLJTempFee(tLYReturnFromBankSchema)) {
                setLYDupPay(tLYReturnFromBankSchema);
                tLYReturnFromBankSchema.setConvertFlag("4");
              }
              //记录暂交费表
              else
              {
                setLJTempFeeCont(tLYReturnFromBankSchema);
              }
            }
          }
        }
        }
      }
    }
    catch(Exception e) {
      throw new NullPointerException("银行返回盘数据校验失败！(verifyLJSPay) " + e.getMessage());
    }
  }

  /**
     * 生成打印管理表数据
     * @param tLJSPaySchema
     * @param tEdorNo
     * @param type
     * @return
     */
    private void setLOPRTManager(LYReturnFromBankSchema tLYReturnFromBankSchema, String type) {
        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

        String mLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
        String serNo = PubFun1.CreateMaxNo("PRTSEQNO", mLimit);
        tLOPRTManagerSchema.setPrtSeq(serNo);

        tLOPRTManagerSchema.setOtherNo(tLYReturnFromBankSchema.getPolNo());
        tLOPRTManagerSchema.setOtherNoType("00"); //个人保单号
        tLOPRTManagerSchema.setCode(type);
        tLOPRTManagerSchema.setManageCom(tLYReturnFromBankSchema.getComCode());
        tLOPRTManagerSchema.setAgentCode(tLYReturnFromBankSchema.getAgentCode());
        tLOPRTManagerSchema.setReqCom(mGlobalInput.ManageCom);
        tLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
        tLOPRTManagerSchema.setPrtType("0"); //前台打印
        tLOPRTManagerSchema.setStateFlag("0"); //提交打印
        tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
        tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());

        outLOPRTManagerSet.add(tLOPRTManagerSchema);
    }

  /**
   * 备份银行返回盘记录表到B表
   */
  private void getLYReturnFromBankB() {
    int i;

    for (i=0; i<inLYReturnFromBankSet.size(); i++) {
      LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet.get(i + 1);

      LYReturnFromBankBSchema tLYReturnFromBankBSchema = new LYReturnFromBankBSchema();
      mReflections.transFields(tLYReturnFromBankBSchema, tLYReturnFromBankSchema);
      //因为没有设计操作员字段，所以暂时保存在备注字段中，add by Minim at 2004-2-5
      tLYReturnFromBankBSchema.setRemark(mGlobalInput.Operator);
      tLYReturnFromBankBSchema.setModifyDate(PubFun.getCurrentDate());
      tLYReturnFromBankBSchema.setModifyTime(PubFun.getCurrentTime());
      outLYReturnFromBankBSet.add(tLYReturnFromBankBSchema);
    }
  }

  /**
   * 备份银行发盘记录表到B表
   */
  private void getLYSendToBankB()
  {
      LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();
      tLYSendToBankSchema.setSerialNo(serialNo);
      LYSendToBankSet tLYSendToBankSet = tLYSendToBankSchema.getDB().query();

      for(int i = 1 ; i <= tLYSendToBankSet.size() ; i ++)
      {
          LYSendToBankBSchema tLYSendToBankBSchema = new LYSendToBankBSchema();
          LYSendToBankSchema tmpLYSendToBankSchema = tLYSendToBankSet.get(i);

          mReflections.transFields(tLYSendToBankBSchema, tmpLYSendToBankSchema);

//          tLYSendToBankBSchema.setOperator(mGlobalInput.Operator);
          tLYSendToBankBSchema.setModifyDate(PubFun.getCurrentDate());
          tLYSendToBankBSchema.setModifyTime(PubFun.getCurrentTime());

          outLYSendToBankBSet.add(tLYSendToBankBSchema);
      }
  }

  /**
   * 获取发送盘数据，用于删除
   */
  private void getLYSendToBank() {
    LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();

    tLYSendToBankSchema.setSerialNo(serialNo);
    outDelLYSendToBankSet = tLYSendToBankSchema.getDB().query();
  }

  /**
   * 获取应收表数据，修改银行在途标志
   */
  private void getLJSPay() {
    int i;

    for (i=0; i<inLYReturnFromBankSet.size(); i++) {
      LYReturnFromBankSchema tLYReturnFromBankSchema = inLYReturnFromBankSet.get(i + 1);

      //如果DoType＝2，表示首期银行直接交费，应收表中无数据，故不做银行在途标志的修改
      if (tLYReturnFromBankSchema.getDoType().equals("2")) return;

      if (tLYReturnFromBankSchema.getConvertFlag().equals("1")||tLYReturnFromBankSchema.getConvertFlag().equals("7")) {
        LJSPaySchema tLJSPaySchema = getLJSPayByLYReturnFromBank(tLYReturnFromBankSchema);
        //总应收表无数据，表示扣款失败，并且已经用其它方式交费
        if (tLJSPaySchema == null) continue;

        tLJSPaySchema.setBankOnTheWayFlag("0");
        tLJSPaySchema.setApproveDate(PubFun.getCurrentDate());
        tLJSPaySchema.setModifyDate(PubFun.getCurrentDate());
        tLJSPaySchema.setModifyTime(PubFun.getCurrentTime());
        outLJSPaySet.add(tLJSPaySchema);
      }
    }
  }

  /**
   * 生成银行日志数据
   * @param tLYSendToBankSchema
   * @return
   */
  private LYBankLogSet getLYBankLog() {
    LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();
    LYBankLogSet tLYBankLogSet = new LYBankLogSet();

    //获取日志记录
    tLYBankLogSchema.setSerialNo(serialNo);
    tLYBankLogSet.set(tLYBankLogSchema.getDB().query());

    if (tLYBankLogSet.size() > 0) {
      tLYBankLogSchema.setSchema(tLYBankLogSet.get(1));

      //修改日志
      tLYBankLogSchema.setAccTotalMoney(totalMoney);                 //财务确认总金额
      tLYBankLogSchema.setBankSuccMoney(totalMoney);                 //银行成功总金额
      tLYBankLogSchema.setBankSuccNum(sumNum + "");                  //银行成功总数量
      tLYBankLogSchema.setDealState("1");                            //处理状态
      tLYBankLogSchema.setModifyDate(PubFun.getCurrentDate());
      tLYBankLogSchema.setModifyTime(PubFun.getCurrentTime());

      tLYBankLogSet.clear();
      tLYBankLogSet.add(tLYBankLogSchema);
    }

    return tLYBankLogSet;
  }

  /**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    try {
      //银行代收
      if (mOperate.equals("GETMONEY")) {
        //获取银行返回数据
        inLYReturnFromBankSet = getLYReturnFromBank(inLYReturnFromBankSchema);
        if (inLYReturnFromBankSet.size() == 0) throw new NullPointerException("无银行返回数据！");
        logger.debug("---End getLYReturnFromBank---");

        //记录批次号、总金额、备份银行返回盘表
        serialNo = inLYReturnFromBankSet.get(1).getSerialNo();
        totalMoney = (String)inTransferData.getValueByName("totalMoney");
        outDelLYReturnFromBankSet.set(inLYReturnFromBankSet);

        //校验银行扣款成功与否
        verifyBankSucc();
        logger.debug("---End verifyBankSucc---");

        //校验财务总金额
        if (!confirmTotalMoney(inLYReturnFromBankSet, totalMoney))
          throw new IllegalArgumentException("财务总金额确认失败！请与银行对帐！");
        logger.debug("---End confirmTotalMoney---");

        //校验每笔金额
        verifyUnitMoney();
        logger.debug("---End verifyUnitMoney---");

        //校验应收表
        verifyLJSPay();
        logger.debug("---End verifyLJSPay---");

        //备份返回盘数据到返回盘B表
        getLYReturnFromBankB();
        logger.debug("---End getLYReturnFromBankB---");

        //备份发盘数据到发盘表
        getLYSendToBankB();
        logger.debug("---End getLYSendToBankB---");

        //获取发送盘数据，用于删除
        getLYSendToBank();
        logger.debug("---End getLYSendToBank---");

        //获取应收表数据，修改银行在途标志
        getLJSPay();
        logger.debug("---End getLJSPay---");

        //记录日志
        outLYBankLogSet = getLYBankLog();
        if (outLYBankLogSet.size() == 0) throw new NullPointerException("无银行日志数据！");
        logger.debug("---End verifyUnitMoney---");
      }

      //银行代付
      else if (mOperate.equals("PAYMONEY")) {

      }
    }
    catch(Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "GetReturnFromBankBL";
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
    MMap tMMap=new MMap();
    try {
    	tMMap.put(outLYDupPaySet, "INSERT");
    	tMMap.put(outLYBankLogSet, "UPDATE");
    	tMMap.put(outDelLJSPaySet, "DELETE");
    	tMMap.put(outLJSPaySet, "UPDATE");
    	tMMap.put(outLJTempFeeSet, "INSERT");
    	tMMap.put(outLJTempFeeClassSet, "INSERT");
    	tMMap.put(outUpdateLJTempFeeSet, "UPDATE");
    	tMMap.put(outUpdateLJTempFeeClassSet, "UPDATE");
    	tMMap.put(outDelLYReturnFromBankSet, "DELETE");
    	tMMap.put(outLYReturnFromBankBSet, "INSERT");
    	tMMap.put(outDelLYSendToBankSet, "DELETE");
    	tMMap.put(outLYSendToBankBSet, "INSERT");
    	tMMap.put(outLOPRTManagerSet, "INSERT");
    	
    	
    	// /////////////
		// 添加客户账户处理
		VData nInputData1 = new VData();
		nInputData1.add(outLJTempFeeSet);
		nInputData1.add(outLJTempFeeClassSet);
		nInputData1.add(mGlobalInput);
		FICustomerMain tFICustomerMain = new FICustomerMain();
		// 调用客户账户收费接口，传入财务标志FI
		if (tFICustomerMain.submitData(nInputData1, "FI"))
		{// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
			tMMap.add(tFICustomerMain.getMMap());
		}
		else
		{
			mErrors.copyAllErrors(tFICustomerMain.mErrors);
			return false;
		}
		
		
		// /////////////
    	
      mInputData.add(tMMap);
    }
    catch(Exception ex) {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="GetReturnFromBankBL";
      tError.functionName="prepareOutputData";
      tError.errorMessage="在准备往后层处理所需要的数据时出错。";
      this.mErrors .addOneError(tError);
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
  {
      GetReturnFromBankBL getReturnFromBankBL1 = new GetReturnFromBankBL();

      TransferData transferData1 = new TransferData();
      transferData1.setNameAndValue("totalMoney", "550");

      LYReturnFromBankSchema tLYReturnFromBankSchema = new LYReturnFromBankSchema();
      tLYReturnFromBankSchema.setSerialNo("00000000000000002122");

      GlobalInput tGlobalInput = new GlobalInput();
      tGlobalInput.Operator = "001";

      VData tVData = new VData();
      tVData.add(transferData1);
      tVData.add(tLYReturnFromBankSchema);
      tVData.add(tGlobalInput);

      if (!getReturnFromBankBL1.submitData(tVData, "GETMONEY")) {
          VData rVData = getReturnFromBankBL1.getResult();
          logger.debug("Submit Failed! " + (String)rVData.get(0));
      }
      else logger.debug("Submit Succed!");
  }
}
