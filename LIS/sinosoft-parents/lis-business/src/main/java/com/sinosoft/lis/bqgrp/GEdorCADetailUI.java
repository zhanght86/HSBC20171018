package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GEdorCADetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(GEdorCADetailUI.class);

  private VData mInputData = new VData();
  private VData mResult = new VData();
  private String mOperate;
  public CErrors mErrors = new CErrors();

  public GEdorCADetailUI() {
  }

  public boolean submitData(VData cInputData, String cOperate)	{
    // 数据操作字符串拷贝到本类中
    this.mInputData = (VData)cInputData.clone();
    this.mOperate = cOperate;

    logger.debug("---GEdorGADetail BL BEGIN---");
    GEdorCADetailBL tGEdorCADetailBL = new GEdorCADetailBL();

    if(tGEdorCADetailBL.submitData(cInputData, cOperate) == false)	{
      // @@错误处理
      this.mErrors.copyAllErrors(tGEdorCADetailBL.mErrors);
      mResult.clear();
      mResult.add(mErrors.getFirstError());
      return false;
    }
    else
    {
      mResult = tGEdorCADetailBL.getResult();
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

  /**
   * 主函数，测试用
   */
  public static void main(String[] args) {
//   logger.debug("====     Test");
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}
}
