package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 新保加人保全项目</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * ReWrite ZhangRong
 * @version 1.0
 */

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;

public class GrpEdorNRDetailUI {
private static Logger logger = Logger.getLogger(GrpEdorNRDetailUI.class);
  /** 传入数据的容器 */
  private VData mInputData = new VData();
  /** 传出数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 错误处理类 */
  public CErrors mErrors = new CErrors();

  public GrpEdorNRDetailUI() {
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

    logger.debug("---GEdorNRDetail BL BEGIN---");
    GrpEdorNRDetailBL tGrpEdorNRDetailBL = new GrpEdorNRDetailBL();

    if(tGrpEdorNRDetailBL.submitData(cInputData, cOperate) == false)	{
      // @@错误处理
      this.mErrors.copyAllErrors(tGrpEdorNRDetailBL.mErrors);
      mResult.clear();
      mResult.add(mErrors.getFirstError());
      return false;
    }	else {
      mResult = tGrpEdorNRDetailBL.getResult();
    }
    logger.debug("---GEdorNRDetail BL END---");

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
  public static void main(String[] args)
  {
	  GlobalInput tGlobalInput = new GlobalInput();
	  tGlobalInput.Operator = "001";
	  tGlobalInput.ManageCom = "86";

	  LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
	  tLPEdorItemSchema.setEdorNo("410110000000070");
	  tLPEdorItemSchema.setGrpContNo("240110000000166");
	  tLPEdorItemSchema.setContNo("130110000009273");
	  tLPEdorItemSchema.setEdorType("NR");

	  LPEdorItemSet inLPEdorItemSet = new  LPEdorItemSet();
	  inLPEdorItemSet.add(tLPEdorItemSchema);

	  VData tVData = new VData();
	  tVData.add(tGlobalInput);
	  tVData.add(inLPEdorItemSet);

	  GrpEdorNRDetailUI tGrpEdorNRDetailUI = new GrpEdorNRDetailUI();
	  if (!tGrpEdorNRDetailUI.submitData(tVData, "INSERT"))
	  {
		  logger.debug(tGrpEdorNRDetailUI.mErrors.getErrContent());
	  }
  }
}
