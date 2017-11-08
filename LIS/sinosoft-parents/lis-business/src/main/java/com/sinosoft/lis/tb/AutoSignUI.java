package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.finfee.*;
import com.sinosoft.lis.db.*;
/**
 * <p> Title: Web业务系统 </p>
 * <p> Description: UI功能类 </p>
 * <p>Copyright: Copyright (c) 2002 </p>
 * <p>Company: Sinosoft </p>
 * @author zy
 * @version 1.0
 * @date 2004-10-26
 */

public class AutoSignUI
{
private static Logger logger = Logger.getLogger(AutoSignUI.class);

  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 错误处理类 */
  public CErrors mErrors = new CErrors();

  // @Constructor
  public AutoSignUI() {}
  /** 个人投保单表 */
  private LCPolSet mLCPolSet = new LCPolSet();
  private LCPolSchema mLCPolSchema = new LCPolSchema();

  /** 全局数据 */
  private GlobalInput tGI = new GlobalInput();
  private String mEnterAccDate = null;//到帐日期
  private VData mResult = new VData();

  // @Method
  public static void main(String[] args)
  {
    LCPolSchema mLCPolSchema = new LCPolSchema();
    mLCPolSchema.setPolNo("86510020043100000190");
    mLCPolSchema.setPrtNo("86310000000214");
    VData aVData = new VData();
    aVData.add(mLCPolSchema);
    AutoSignUI tAutoSignUI = new AutoSignUI();
    tAutoSignUI.submitData(aVData,"INSERT");
  }

  /**
   * 数据提交方法
   * @param: 传入数据、数据操作字符串
   * @return: boolean
   **/
  public boolean submitData( VData cInputData, String cOperate )
  {
    // 数据操作字符串拷贝到本类中
    this.mOperate = cOperate;

    //得到外部传入的数据,将数据备份到本类中 
    if (this.getInputData(cInputData) == false)
    {
      return false;
    }
    
    if (!dealData())
	{
		CError.buildErr(this, "业务逻辑处理失败！");
		return false;
	}
    
    logger.debug("**********success**********");
	return true;
  }

  private boolean dealData() 
  {
	  	tGI.Operator="0001" ;
	    tGI.ComCode = "86" ;
	    tGI.ManageCom="86";
	    LCPolDB tLCPolDB = new LCPolDB();
	    tLCPolDB.setPolNo(mLCPolSchema.getPolNo());
	    tLCPolDB.getInfo();
	    Reflections tReflections = new Reflections();
	    tReflections.transFields(this.mLCPolSchema, tLCPolDB.getSchema());
	    MMap map = new MMap();
	    VData mDVData = new VData();
	    
	    SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
	    sqlbv1.sql("update lcpol  set uwcode='"+"?uwcode?"+"',uwdate='"+"?uwdate?"+"',uwtime='"+"?uwtime?"+"',approveflag='9',approvecode ='"+"?approvecode?"+"',approvedate ='"+"?approvedate?"+"',approvetime='"+"?approvetime?"+"',uwflag='9' where contno = '" + "?contno?" + "'");

	    sqlbv2.sql("update lccont set UWOperator='"+"?UWOperator?"+"',uwdate='"+"?uwdate?"+"',uwtime='"+"?uwtime?"+"',approveflag='9',approvecode ='"+"?approvecode?"+"',approvedate='"+"?approvedate?"+"',approvetime='"+"?approvetime?"+"' ,uwflag='9',signcom='"+"?signcom?"+"' where contno='"+"?contno?"+"'");
	 	
			sqlbv1.put("uwcode",tGI.Operator);
			sqlbv1.put("uwdate",PubFun.getCurrentDate());
			sqlbv1.put("uwtime",PubFun.getCurrentTime());
			sqlbv1.put("approvecode",tGI.Operator);
			sqlbv1.put("approvedate",PubFun.getCurrentDate());
			sqlbv1.put("approvetime",PubFun.getCurrentTime());
			sqlbv1.put("contno",mLCPolSchema.getContNo());
			
			sqlbv2.put("UWOperator",tGI.Operator);
			sqlbv2.put("uwdate",PubFun.getCurrentDate());
			sqlbv2.put("uwtime",PubFun.getCurrentTime());
			sqlbv2.put("approvecode",tGI.Operator);
			sqlbv2.put("approvedate",PubFun.getCurrentDate());
			sqlbv2.put("approvetime",PubFun.getCurrentTime());
			sqlbv2.put("signcom",tGI.ComCode);
			sqlbv2.put("contno",mLCPolSchema.getContNo());
			
	    map.put(sqlbv1, "UPDATE");
	 	//由于合同的签单机构是公共程序来源有保单的管理机构，所以该处调整与保单一致的签单机构即86
		map.put(sqlbv2, "UPDATE");
		
		mDVData.add(map);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mDVData))
		{
			// 插入错误记录
			CError.buildErr(this, "合同号为"+mLCPolSchema.getContNo()+"复核处理过程中出错！");
			return false;
			
		}


	    //处理暂交费信息
	    LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
	    LJTempFeeSet tLJTempFeeSet   = new LJTempFeeSet();
	    tLJTempFeeSchema.setTempFeeNo(tLCPolDB.getPrtNo());
	    tLJTempFeeSchema.setTempFeeType("1");//新单交费
	    tLJTempFeeSchema.setTempFeeNoType("2");//银行划款协议书
	    tLJTempFeeSchema.setRiskCode(tLCPolDB.getRiskCode());
	    tLJTempFeeSchema.setAgentGroup(tLCPolDB.getAgentGroup());
	    tLJTempFeeSchema.setAgentCode(tLCPolDB.getAgentCode());
	    tLJTempFeeSchema.setPayDate(PubFun.getCurrentDate());
	    tLJTempFeeSchema.setEnterAccDate(mEnterAccDate);//到帐日期＝＝＝＝＝＝＝＝
	    tLJTempFeeSchema.setPayMoney(tLCPolDB.getPrem());
	    tLJTempFeeSchema.setManageCom("86");
	    tLJTempFeeSchema.setOtherNo(tLCPolDB.getPrtNo());
	    tLJTempFeeSchema.setOtherNoType("4");
	    tLJTempFeeSchema.setOperator("0001");
	    tLJTempFeeSet.add(tLJTempFeeSchema);

	    // 暂收分类表记录集
	    LJTempFeeClassSchema tLJTempFeeClassSchema;
	    LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
	    tLJTempFeeClassSchema   = new LJTempFeeClassSchema();
	    tLJTempFeeClassSchema.setTempFeeNo(tLCPolDB.getPrtNo());
	    tLJTempFeeClassSchema.setPayMode("8"); //电子商务
	    tLJTempFeeClassSchema.setPayDate(PubFun.getCurrentDate());
	    tLJTempFeeClassSchema.setPayMoney(tLCPolDB.getPrem());
	    tLJTempFeeClassSchema.setChequeNo("000000");//默认为000000
	    tLJTempFeeClassSchema.setEnterAccDate(mEnterAccDate);
	    tLJTempFeeClassSchema.setManageCom("86");
	    tLJTempFeeClassSchema.setBankCode("");
	    tLJTempFeeClassSchema.setBankAccNo("");
	    tLJTempFeeClassSchema.setAccName("");

	    tLJTempFeeClassSchema.setOperator("0001");
	    tLJTempFeeClassSet.add(tLJTempFeeClassSchema);


	    // 准备传输数据 VData
	    VData tVData = new VData();
	    tVData.addElement(tLJTempFeeSet);
	    tVData.addElement(tLJTempFeeClassSet);
	    tVData.addElement(tGI);

	    // 数据传输
	    TempFeeUI tTempFeeUI   = new TempFeeUI();
	    tTempFeeUI.submitData(tVData,"INSERT");
	    //如果有需要处理的错误，则返回
	    if (tTempFeeUI .mErrors .needDealError() )
	      this.mErrors .copyAllErrors(tTempFeeUI.mErrors ) ;
	    
	  LCContDB mLCContDB = new LCContDB();
	  mLCContDB.setContNo(mLCPolSchema.getContNo());
	  if(!mLCContDB.getInfo())
	  {
		  CError.buildErr(this, "合同号为"+mLCPolSchema.getContNo()+"的合同信息不存在，请核实！");
		  return false;
	  }
	  LCContSet mLCContSet = new LCContSet();
	  mLCContSet= mLCContDB.query();
	  
	  LCPolDB mLCPolDB = new LCPolDB();
	  mLCPolDB.setPolNo(mLCPolSchema.getPolNo());
	  if(!mLCContDB.getInfo())
	  {
		  CError.buildErr(this, "险种号为"+mLCPolSchema.getContNo()+"的合同信息不存在，请核实！");
		  return false;
	  }
	  LCPolSet mLCPolSet = new LCPolSet();
	  mLCPolSet= mLCPolDB.query();
	  
	  TransferData mTransferData = new TransferData();
	  mTransferData.setNameAndValue("ECheckFlag", "1");
	  
	  tVData= new VData();
	  mLCPolSet.add(mLCPolSchema);
	  tVData.add(mTransferData);
	  tVData.add(tGI);
	  tVData.add(mLCContSet);

	  // 调用核心的签单逻辑
	  LCContSignBL mLCContSignBL = new LCContSignBL();
	  if (!mLCContSignBL.submitData(tVData, ""))
	  {
		  this.mErrors.copyAllErrors(mLCContSignBL.mErrors);
	  	  return false;
	  }

	  map = new MMap();
	  map = new MMap();
	  map.add((MMap) mLCContSignBL.getResult().getObjectByObjectName("MMap", 0));
	  VData tInputData = new VData();
	  tInputData.add(map);

	  PubSubmit tSubmit = new PubSubmit();
	  if (!tSubmit.submitData(tInputData))
	  {
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			return false;
	  }
//	  else
//	  {
//		  mResult.clear();
//          mResult.add(mLCPolSchema.encode());
//		  logger.debug("mLCContSet.encode()"+mLCContSet.encode());
//	  }
	  return true;
	
  }

  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    //全局变量
//    mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
//        "GlobalInput", 0));

    //保单
    this.mLCPolSchema.setSchema((LCPolSchema) cInputData.getObjectByObjectName("LCPolSchema", 0));
    mEnterAccDate = (String)cInputData.getObjectByObjectName("String", 0);
    return true;
  }
  public VData getResult()
  {
      return this.mResult;
  }

}
