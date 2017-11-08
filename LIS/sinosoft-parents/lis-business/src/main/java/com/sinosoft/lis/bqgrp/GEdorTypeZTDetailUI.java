package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

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
public class GEdorTypeZTDetailUI implements BusinessService{
private static Logger logger = Logger.getLogger(GEdorTypeZTDetailUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public GEdorTypeZTDetailUI() {
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

    logger.debug("---GEdorTypeZTDetailBL BEGIN---");
    GEdorTypeZTDetailBL tGEdorTypeZTDetailBL = new GEdorTypeZTDetailBL();

    if(tGEdorTypeZTDetailBL.submitData(cInputData, cOperate) == false)	{
      // @@错误处理
      this.mErrors.copyAllErrors(tGEdorTypeZTDetailBL.mErrors);
      mResult.clear();
      mResult.add(mErrors.getFirstError());
      return false;
    }	else {
      mResult = tGEdorTypeZTDetailBL.getResult();
    }
    logger.debug("---GEdorTypeZTDetailBL END---");
    mResult.clear();
    return true;
  }

  /**
   * 数据输出方法，供外界获取数据处理结果
   * @return 包含有数据查询结果字符串的VData对象
   */
  public VData getResult() {
    return mResult;
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}


}
