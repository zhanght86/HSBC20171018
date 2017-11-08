/*
 * @(#)PEdorNoscanAppAfterInitService.java	2005-08-16
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.workflow.proddef;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.workflowengine.AfterEndService;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体保全-无扫描申请服务类 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author zhangtao
 * @version 1.0
 */
public class PDTestAfterEndService implements AfterEndService
{
private static Logger logger = Logger.getLogger(PDTestAfterEndService.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  private TransferData mTransferData = new TransferData();

  public PDTestAfterEndService() {}

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {

//      GEdorAppBL tGEdorNoscanAppBL = new GEdorAppBL();
//
//      if (!tGEdorNoscanAppBL.submitData(cInputData, cOperate))
//      {
//          // @@错误处理
//          this.mErrors.copyAllErrors(tGEdorNoscanAppBL.mErrors);
//          return false;
//      }
//
//      mResult = tGEdorNoscanAppBL.getResult();
//      mTransferData = tGEdorNoscanAppBL.getReturnTransferData();

      return true;
  }

  public VData getResult()
  {
      return mResult;
  }

  public TransferData getReturnTransferData()
  {
      return mTransferData;
  }

  public CErrors getErrors()
  {
      return mErrors;
  }
}
