package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:团体保全集体下个人功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */
public class PGrpEdorXTDetailUI implements BusinessService{
private static Logger logger = Logger.getLogger(PGrpEdorXTDetailUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public PGrpEdorXTDetailUI() {
  }

  /**
   * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
   * @param cInputData 传入的数据,VData对象
   * @param cOperate 数据操作字符串，主要包括"INSERT"
   * @return 布尔值（true--提交成功, false--提交失败）
   */
  public boolean submitData(VData cInputData, String cOperate)	{
    // 数据操作字符串拷贝到本类中
    this.mInputData = (VData)cInputData.clone();
    this.mOperate = cOperate;

    logger.debug("---PGrpEdorXTDetail BL BEGIN---");
    PGrpEdorXTDetailBL tPGrpEdorXTDetailBL = new PGrpEdorXTDetailBL();

    if(tPGrpEdorXTDetailBL.submitData(cInputData, cOperate) == false)	{
      // @@错误处理
      this.mErrors.copyAllErrors(tPGrpEdorXTDetailBL.mErrors);
      mResult.clear();
      mResult.add(mErrors.getFirstError());
      return false;
    }	else {
      mResult = tPGrpEdorXTDetailBL.getResult();
    }
    logger.debug("---PGrpEdorXTDetail BL END---");

    return true;
  }

  /**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    return mResult;
  }

  /**
   * 主函数，测试用
   */
  public static void main(String[] args) {
    logger.debug("-------test...");

     LPEdorMainSet tLPEdorMainSet   = new LPEdorMainSet();
     LPGrpEdorItemSchema tLPGrpEdorItemSchema   = new LPGrpEdorItemSchema();

     tLPGrpEdorItemSchema.setEdorAcceptNo("86110000000409");
     tLPGrpEdorItemSchema.setGrpContNo("240110000000199");
     tLPGrpEdorItemSchema.setEdorNo("430110000000206");
     tLPGrpEdorItemSchema.setEdorType("XT");
     tLPGrpEdorItemSchema.setGetMoney("100");

     LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
            //个人保单批改主表信息
      tLPEdorMainSchema.setEdorAcceptNo("86110000000409");
      tLPEdorMainSchema.setEdorNo("430110000000206");
      tLPEdorMainSchema.setContNo("130110000013429");
      tLPEdorMainSchema.setGetMoney("100");
      tLPEdorMainSet.add(tLPEdorMainSchema);

     LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
     LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
     tLPEdorItemSchema.setEdorAcceptNo("86110000000409");
     tLPEdorItemSchema.setEdorNo("430110000000206");
     tLPEdorItemSchema.setContNo("130110000013429");
     tLPEdorItemSchema.setInsuredNo("000000");
     tLPEdorItemSchema.setGetMoney("100");
     tLPEdorItemSet.add(tLPEdorItemSchema);



    PGrpEdorXTDetailUI tPGrpEdorXTDetailUI = new PGrpEdorXTDetailUI();

    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput.Operator = "001";
    tGlobalInput.ComCode = "86";
    tGlobalInput.ManageCom = "86";

    // 准备传输数据 VData

    VData tVData = new VData();

    //保存集体保单信息(保全)
    tVData.addElement(tLPEdorMainSet);
    tVData.addElement(tLPGrpEdorItemSchema);
    tVData.addElement(tLPEdorItemSet);
    tVData.addElement(tGlobalInput);
    tPGrpEdorXTDetailUI.submitData(tVData, "INSERT||EDOR");
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}
}
