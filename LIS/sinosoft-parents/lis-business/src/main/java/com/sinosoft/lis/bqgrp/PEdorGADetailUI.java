package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:业务系统给付申请功能部分
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @version 1.0
 */
public class PEdorGADetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(PEdorGADetailUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();

  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();

  /** 往界面传输数据的容器 */
  private VData mResult = new VData();

  /** 数据操作字符串 */
  private String mOperate;

  /**
   * Constructor
   **/
  public PEdorGADetailUI() {}

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    logger.debug("=====This is PEdorGADetialUI->submitData=====\n");

    //将操作数据拷贝到本类中
    this.mOperate = cOperate;
    PEdorGADetailBL tPEdorGADetailBL = new PEdorGADetailBL();
    if (tPEdorGADetailBL.submitData(cInputData, mOperate) == false)
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tPEdorGADetailBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "PEdorGADetailBL";
      tError.functionName = "submitData";
      tError.errorMessage = "失败!";
      this.mErrors .addOneError(tError) ;
      mResult.clear();

      return false;
    }
    else
      mResult = tPEdorGADetailBL.getResult();
    return true;
  }

  /**
   *
   **/
  public VData getResult()
  {
    return mResult;
  }

  /**
   *
   **/
  public static void main(String[] args)
  {
//    LPGetSet tLPGetSet = new LPGetSet();
//    LPInsureAccClassSchema tLPInsureAccClassSchema=new LPInsureAccClassSchema();
//    GlobalInput tGlobalInput = new GlobalInput();
//    PEdorGADetailUI aPEdorGADetailUI = new PEdorGADetailUI();
//    LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//
//    String tChangePercent = "";
//    tGlobalInput.ManageCom="86";
//    tGlobalInput.Operator="001";
//
//    tLPEdorItemSchema.setEdorAcceptNo("86110000001088");
//    tLPEdorItemSchema.setContNo("230110000003159");
//    tLPEdorItemSchema.setGrpContNo("240110000000126");
//    tLPEdorItemSchema.setInsuredNo("0000006400");
//    tLPEdorItemSchema.setEdorNo("430110000001066");
//    tLPEdorItemSchema.setEdorType("GA");
//    tLPEdorItemSchema.setPolNo("210110000008961");
//
//    tLPInsureAccClassSchema.setInsuAccNo("000001");
//    tLPInsureAccClassSchema.setContNo("230110000003159");
//    tLPInsureAccClassSchema.setGrpContNo("240110000000126");
//    tLPInsureAccClassSchema.setInsuredNo("0000006400");
//    tLPInsureAccClassSchema.setEdorNo("430110000001066");
//    tLPInsureAccClassSchema.setEdorType("GA");
//    tLPInsureAccClassSchema.setPolNo("210110000008961");
//    tLPInsureAccClassSchema.setPayPlanCode("692102");
//    tLPInsureAccClassSchema.setOtherNo("210110000008961");
//    tLPInsureAccClassSchema.setAccAscription("0");
//    tChangePercent = "0.5";
//
//    LPGetSchema tLPGetSchema   = new LPGetSchema();
//    tLPGetSchema.setPolNo("210110000008961");
//    tLPGetSchema.setGetDutyKind("1");
//    tLPGetSchema.setGetIntv("12");
//    tLPGetSchema.setDutyCode("692001");
//    tLPGetSchema.setGetDutyCode("692201");
//    tLPGetSet.add(tLPGetSchema);
//
//    VData tVData = new VData();
//    tVData.addElement(tGlobalInput);
//    tVData.addElement(tLPEdorItemSchema);
//    tVData.addElement(tLPInsureAccClassSchema);
//    tVData.addElement(tLPGetSet);
//    tVData.addElement(tChangePercent);
//    aPEdorGADetailUI.submitData(tVData,"INSERT||MONEY");
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}
}
