package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJAGetDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubLock;
import com.sinosoft.lis.schema.LCBankAccSchema;
import com.sinosoft.lis.schema.LCBankAuthSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LYBankLogSchema;
import com.sinosoft.lis.schema.LYSendToBankSchema;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.lis.vschema.LYSendToBankSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 业务数据转换到银行系统</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class PaySendToBankBL {
private static Logger logger = Logger.getLogger(PaySendToBankBL.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 传出数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 错误处理类 */
  public  CErrors mErrors = new CErrors();

  //业务数据
  private String startDate = "";
  private String endDate = "";
  private String bankCode = "";
  private double totalMoney = 0;
  private int sumNum = 0;
  private String serialNo = "";
  private GlobalInput mGlobalInput = new GlobalInput();

  private LJAGetSet outLJAGetSet = new LJAGetSet();
  private LYSendToBankSet outLYSendToBankSet = new LYSendToBankSet();
  private LYBankLogSet outLYBankLogSet = new LYBankLogSet();
  private PubConcurrencyLock mLock = new PubConcurrencyLock();
  
  public PaySendToBankBL() {
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
		String key = "SendF" + bankCode;
		PubLock tPubLock = new PubLock();
		if (!tPubLock.lock(key, "准备" + bankCode + "代付发盘数据")) {
			this.mErrors.copyAllErrors(tPubLock.mErrors);
			return false;
		}
		try {
    //进行业务处理
    if (!dealData()) return false;
    logger.debug("---End dealData---");

    //银行代付
    if (mOperate.equals("PAYMONEY")) {
      //准备往后台的数据
      if (!prepareOutputData()) return false;
      logger.debug("---End prepareOutputData---");

      logger.debug("Start PaySendToBank BLS Submit...");
      PaySendToBankBLS tPaySendToBankBLS = new PaySendToBankBLS();
      if(tPaySendToBankBLS.submitData(mInputData, cOperate) == false)	{
        // @@错误处理
        this.mErrors.copyAllErrors(tPaySendToBankBLS.mErrors);
        mResult.clear();
        return false;
      }
      logger.debug("End PaySendToBank BLS Submit...");

      //如果有需要处理的错误，则返回
      if (tPaySendToBankBLS.mErrors .needDealError())  {
        this.mErrors.copyAllErrors(tPaySendToBankBLS.mErrors ) ;
      }
    }
    //银行代付
    else if (mOperate.equals("PAYMONEY")) {
    }

    return true;
	} finally {
			mLock.unLock();
			if (!tPubLock.unLock(key)) {
				CError.buildErr(this, "银行" + bankCode + "解锁失败:"
						+ tPubLock.mErrors.getFirstError());
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
    try {
      TransferData tTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
      startDate = (String)tTransferData.getValueByName("startDate");
      endDate = (String)tTransferData.getValueByName("endDate");
      bankCode = (String)tTransferData.getValueByName("bankCode");

      mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
    }
    catch (Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PaySendToBankBL";
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
  private LJAGetSet getLJAGetByPaydate(String startDate, String endDate) {
    String tSql = "";
	String bqothernotype = "10";
	String claimothernotype = "5";
	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    if (startDate.equals("")) {
      tSql = "select * from LJAGet where "
//           + " and StartGetDate <= '" + endDate + "'"
//           + " and ShouldDate <= '" + endDate + "'"
           + " BankCode = '" + "?BankCode?" + "' and sumgetmoney>0 "
           + " and ManageCom like concat('" + "?like?" + "','%')"
           + " and PayMode='4'"
           + " and EnterAccDate is null and ConfDate is null"
           + " and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null)"
           + " and (BankAccNo is not null) "
           + " and ((OtherNoType<>'"+"?OtherNoType1?"+"' and OtherNoType<>'"+"?OtherNoType2?"+"') or " 
           + " (OtherNoType ='"+"?OtherNoType3?"+"' and SendBankCount<3)"
           + " or(OtherNoType ='"+"?OtherNoType4?"+"' and SendBankCount<3 "
           + " and exists(select 1 from lpedorapp where edorconfno=LJAGet.otherno and othernotype in ('1','3','4'))))";
      
      sqlbv1.sql(tSql);
      sqlbv1.put("BankCode", bankCode);
      sqlbv1.put("like", mGlobalInput.ComCode);
      sqlbv1.put("OtherNoType1", bqothernotype);
      sqlbv1.put("OtherNoType2", claimothernotype);
      sqlbv1.put("OtherNoType3", claimothernotype);
      sqlbv1.put("OtherNoType4", bqothernotype);

    }
    else {
      tSql = "select * from LJAGet where "
//           + " and StartGetDate <= '" + endDate + "'"
//           + " and ShouldDate >= '" + startDate + "'"
//           + " and ShouldDate <= '" + endDate + "'"
           + " BankCode = '" + "?BankCode1?" + "' and sumgetmoney>0  "
           + " and ManageCom like concat('" + "?like1?" + "','%')"
           + " and PayMode='4'"
           + " and EnterAccDate is null and ConfDate is null"
           + " and (BankOnTheWayFlag = '0' or BankOnTheWayFlag is null)"
           + " and (BankAccNo is not null) "
           + " and ((OtherNoType<>'"+"?OtherNoType5?"+"' and OtherNoType<>'"+"?OtherNoType6?"+"') or " 
           + " (OtherNoType ='"+"?OtherNoType7?"+"' and SendBankCount<3)"
           + " or(OtherNoType ='"+"?OtherNoType8?"+"' and SendBankCount<3 "
           + " and exists(select 1 from lpedorapp where edorconfno=LJAGet.otherno and othernotype in ('1','3','4'))))";
     
      sqlbv1.sql(tSql);
      sqlbv1.put("BankCode1", bankCode);
      sqlbv1.put("like1", mGlobalInput.ComCode);
      sqlbv1.put("OtherNoType5", bqothernotype);
      sqlbv1.put("OtherNoType6", claimothernotype);
      sqlbv1.put("OtherNoType7", claimothernotype);
      sqlbv1.put("OtherNoType8", bqothernotype);
    }
    logger.debug(tSql);

    LJAGetDB tLJAGetDB = new LJAGetDB();
    LJAGetSet tLJAGetSet = tLJAGetDB.executeQuery(sqlbv1);

    if (tLJAGetDB.mErrors.getErrorCount()>0) return null;
    else return tLJAGetSet;
  }

  /**
   * 校验银行授权
   * @param tLJAGetSet
   */
  private LJAGetSet verifyBankAuth(LJAGetSet tLJAGetSet) {
    int i;
    LJAGetSet bankAuthLJAGetSet = new LJAGetSet();

    for (i=0; i<tLJAGetSet.size(); i++) {
      LJAGetSchema tLJAGetSchema = tLJAGetSet.get(i+1);

      LCBankAuthSchema tLCBankAuthSchema = new LCBankAuthSchema();
      tLCBankAuthSchema.setPolNo(tLJAGetSchema.getOtherNo());  //保单号
      tLCBankAuthSchema.setPayGetFlag("0");                    //收付费标志(0---表示收费)
      tLCBankAuthSchema.setPayValidFlag("1");                  //正常交费/领取代付开通标志(1 -- 开通银行代付)
      tLCBankAuthSchema.setBankCode(bankCode);                 //匹配银行编码

      //找到需要处理的数据
      if (tLCBankAuthSchema.getDB().query().size() > 0) {
        bankAuthLJAGetSet.add(tLJAGetSchema);
      }
    }

    if (bankAuthLJAGetSet.size() > 0) return bankAuthLJAGetSet;
    else return null;
  }

  /**
   * 获取银行账号信息
   * @param tLJAGetSchema
   * @return
   */
  private LCBankAccSchema getBankAcc(LJAGetSchema tLJAGetSchema) {
    try {
      LCBankAccSchema tLCBankAccSchema = new LCBankAccSchema();
      tLCBankAccSchema.setBankCode(bankCode);
      tLCBankAccSchema.setBankAccNo(tLJAGetSchema.getBankAccNo());

      tLCBankAccSchema.setSchema(tLCBankAccSchema.getDB().query().get(1));

      return tLCBankAccSchema;
    }
    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 生成送银行表数据
   * @param tLJAGetSet
   * @return
   */
  private LYSendToBankSet getSendToBank(LJAGetSet tLJAGetSet) {
    int i;
    serialNo = PubFun1.CreateMaxNo("1", 20);
    LYSendToBankSet tLYSendToBankSet = new LYSendToBankSet();

    for (i=0; i<tLJAGetSet.size(); i++) {
      LJAGetSchema tLJAGetSchema = tLJAGetSet.get(i + 1);

      //获取银行账号信息
//      LCBankAccSchema tLCBankAccSchema = getBankAcc(tLJAGetSchema);
//      if (tLCBankAccSchema == null) return null;

      //生成送银行表数据
      LYSendToBankSchema tLYSendToBankSchema = new LYSendToBankSchema();
      tLYSendToBankSchema.setSerialNo(serialNo);
      tLYSendToBankSchema.setDealType("F");
      tLYSendToBankSchema.setPayCode(tLJAGetSchema.getActuGetNo());
      tLYSendToBankSchema.setBankCode(bankCode);
//      tLYSendToBankSchema.setAccType(tLCBankAccSchema.getAccType());
      tLYSendToBankSchema.setAccName(tLJAGetSchema.getAccName());
      tLYSendToBankSchema.setAccNo(tLJAGetSchema.getBankAccNo());
      tLYSendToBankSchema.setPolNo(tLJAGetSchema.getOtherNo());
      //代付没有首续期之分，为了满足商业银行需要，先全部设置成0
      tLYSendToBankSchema.setNoType(tLJAGetSchema.getOtherNoType());
      tLYSendToBankSchema.setComCode(tLJAGetSchema.getManageCom());
      tLYSendToBankSchema.setAgentCode(tLJAGetSchema.getAgentCode());
      tLYSendToBankSchema.setPayMoney(tLJAGetSchema.getSumGetMoney());
      tLYSendToBankSchema.setSendDate(PubFun.getCurrentDate());
      tLYSendToBankSchema.setModifyDate(PubFun.getCurrentDate());
      tLYSendToBankSchema.setModifyTime(PubFun.getCurrentTime());
      tLYSendToBankSchema.setIDNo(tLJAGetSchema.getDrawerID());
      tLYSendToBankSchema.setIDType(tLJAGetSchema.getDrawerType());
//      if("2".equals(tLJAGetSchema.getOtherNoType())){//续期
//    	  String sql="select idtype,idno from lcappntind where polno='"+tLJAGetSchema.getOtherNo()+"'";
//    	  SSRS tSSRS=new ExeSQL().execSQL(sql);
//    	  if(tSSRS.MaxRow>0){
//    		  tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//    		  tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//    	  }else{
//    		  sql="select idtype,idno from lbappntind where polno='"+tLJAGetSchema.getOtherNo()+"'";
//    		  tSSRS=new ExeSQL().execSQL(sql);
//        	  if(tSSRS.MaxRow>0){
//        		  tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//        		  tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//        	  }
//    	  }
//      }else if("10".equals(tLJAGetSchema.getOtherNoType())){//保全
//    	  String sql="select idtype,idno from lcappntind where polno in(select polno from lpedormain where edorno='"+tLJAGetSchema.getOtherNo()+"')";
//    	  SSRS tSSRS=new ExeSQL().execSQL(sql);
//    	  if(tSSRS.MaxRow>0){
//    		  tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//    		  tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//    	  }else{
//    		  sql="select idtype,idno from lbappntind where polno in(select polno from lpedormain where edorno='"+tLJAGetSchema.getOtherNo()+"')";
//    		  tSSRS=new ExeSQL().execSQL(sql);
//        	  if(tSSRS.MaxRow>0){
//        		  tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//        		  tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//        	  }
//    	  }
//      }else{//首期
//    	  String sql="select '0',idno from ljtempfeeclass where tempfeeno='"+tLJAGetSchema.getGetNoticeNo()+"'";
//    	  SSRS tSSRS=new ExeSQL().execSQL(sql);
//    	  if(tSSRS.MaxRow>0){
//    		  tLYSendToBankSchema.setIDType(tSSRS.GetText(1, 1));
//    		  tLYSendToBankSchema.setIDNo(tSSRS.GetText(1, 2));
//    	  }
//      }
      tLYSendToBankSet.add(tLYSendToBankSchema);

      //累加总金额和总数量
      totalMoney = totalMoney + tLJAGetSchema.getSumGetMoney();
      sumNum = sumNum + 1;
    }

    return tLYSendToBankSet;
  }

  /**
   * 修改实付表银行在途标志
   * @param tLJAGetSet
   * @return
   */
  private LJAGetSet modifyBankFlag(LJAGetSet tLJAGetSet) {
    int i;

    for (i=0; i<tLJAGetSet.size(); i++) {
      LJAGetSchema tLJAGetSchema = tLJAGetSet.get(i+1);

      tLJAGetSchema.setBankOnTheWayFlag("1");
      tLJAGetSchema.setSendBankCount(tLJAGetSchema.getSendBankCount() + 1);
      tLJAGetSet.set(i+1, tLJAGetSchema);
    }

    return tLJAGetSet;
  }

  /**
   * 生成银行日志表数据
   * @return
   */
  private LYBankLogSchema getBankLog() {
    LYBankLogSchema tLYBankLogSchema = new LYBankLogSchema();

    tLYBankLogSchema.setSerialNo(serialNo);
    tLYBankLogSchema.setBankCode(bankCode);
    tLYBankLogSchema.setLogType("F");
    tLYBankLogSchema.setStartDate(PubFun.getCurrentDate());
    tLYBankLogSchema.setMakeDate(PubFun.getCurrentDate());
    tLYBankLogSchema.setTotalMoney(String.valueOf(totalMoney));
    tLYBankLogSchema.setTotalNum(sumNum);
    tLYBankLogSchema.setModifyDate(PubFun.getCurrentDate());
    tLYBankLogSchema.setModifyTime(PubFun.getCurrentTime());
    tLYBankLogSchema.setComCode(mGlobalInput.ComCode);

    return tLYBankLogSchema;
  }


  /**
   * 根据前面的输入数据，进行逻辑处理
   * @return 如果在处理过程中出错，则返回false,否则返回true
   */
  private boolean dealData() {
    try {
      //银行代付
      if (mOperate.equals("PAYMONEY")) {
        //总实付表处理（获取应付日期在设置的日期区间内的记录；获取银行在途标志为N的记录）
        LJAGetSet tLJAGetSet = getLJAGetByPaydate(startDate, endDate);
        if (tLJAGetSet == null) throw new NullPointerException("总实付表处理失败！");
        if (tLJAGetSet.size() == 0) throw new NullPointerException("总实付表无数据！");
        
      //校验建行不能超过10000条
		String sql = "select 1 from ldcode1 where codetype ='YBTBatBank' and code='03' and othersign='1' and code1='"+"?code1?"+"'";
		 SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	      sqlbv3.sql(sql);
	      sqlbv3.put("code1", bankCode);
		SSRS tSSRS = new ExeSQL().execSQL(sqlbv3);
		if (tSSRS.MaxRow > 0 && tLJAGetSet.size()>10000) {
			throw new NullPointerException("数据不能超过10000条！");
		}
        
        //logger.debug("tLJAGetSet:" + tLJAGetSet.encode());
        logger.debug("---End getLJAGetByPaydate---");
        if(!lockpol(tLJAGetSet))
			return false;
        
        //校验银行授权
//        tLJAGetSet = verifyBankAuth(tLJAGetSet);
//        if (tLJAGetSet == null) throw new NullPointerException("校验银行授权处理失败！无数据！");
//        logger.debug("---End verifyBankAuth---");

        //生成送银行表数据
        LYSendToBankSet tLYSendToBankSet = getSendToBank(tLJAGetSet);
        if (tLYSendToBankSet == null) throw new Exception("生成送银行表数据失败！");
        //logger.debug("tLYSendToBankSet:" + tLYSendToBankSet.encode());
        logger.debug("---End getSendToBank---");

        //修改实付表银行在途标志
        tLJAGetSet = modifyBankFlag(tLJAGetSet);
        logger.debug("---End modifyBankFlag---");

        //生成银行日志表数据
        LYBankLogSchema tLYBankLogSchema = getBankLog();
        //logger.debug("tLYBankLogSchema:" + tLYBankLogSchema.encode());
        logger.debug("---End getBankLog---");

        outLJAGetSet.set(tLJAGetSet);
        outLYSendToBankSet.set(tLYSendToBankSet);
        outLYBankLogSet.add(tLYBankLogSchema);
      }

      //银行代付
      else if (mOperate.equals("")) {
    	  CError.buildErr(this, "mOperate error!");
    	  return false;
      }
    }
    catch(Exception e) {
      // @@错误处理
      CError tError = new CError();
      tError.moduleName = "PaySendToBankBL";
      tError.functionName = "dealData";
      tError.errorMessage = "数据处理错误! " + e.getMessage();
      this.mErrors .addOneError(tError);
      return false;
    }

    return true;
  }

	private boolean lockpol(LJAGetSet tLJAGetSet) {
		String[] ljspols = new String[tLJAGetSet.size() + tLJAGetSet.size()];
		for (int i = 0; i < tLJAGetSet.size(); i++) {
			ljspols[i * 2] = tLJAGetSet.get(i + 1).getActuGetNo();
			ljspols[i * 2 + 1] = tLJAGetSet.get(i + 1).getOtherNo();
		}

		if (!mLock.lock(ljspols, "LB0002", mGlobalInput.Operator)) {
			this.mErrors.copyAllErrors(mLock.mErrors);
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
      mInputData.add(outLJAGetSet);
      mInputData.add(outLYSendToBankSet);
      mInputData.add(outLYBankLogSet);
    }
    catch(Exception ex) {
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="PaySendToBankBL";
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

  public static void main(String[] args) {
    PaySendToBankBL paySendToBankBL1 = new PaySendToBankBL();
  }
}
