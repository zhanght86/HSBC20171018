/*
 * @(#)PEdorTIDetailBLF.java	2007-04-16
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.vschema.LPEdorItemSet;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全- 投连随时领取变更明细保存提交类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author：tp
 * @version：1.0
 * @CreateDate：2007-04-16
 */
public class PEdorTIDetailBLF implements EdorDetail
{
private static Logger logger = Logger.getLogger(PEdorTIDetailBLF.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData ;
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  /** 往后面传输的数据库操作 */
  private MMap map = new MMap();
  private TransferData mTransferData;

  public PEdorTIDetailBLF()
  {
  }

  /**
   * 传输数据的公共方法
   * @param: cInputData 输入的数据
   * @param: cOperate 数据操作符
   * @return: boolean
   */
  public boolean submitData(VData cInputData, String cOperate)
  {
    //将操作数据拷贝到本类中
    mInputData = (VData) cInputData.clone();
    mOperate = cOperate;

    //得到外部传入的数据
    if (!getInputData())
    {
        return false;
    }
    logger.debug("PEdorTIDetailBLF after getInputData...");

    //数据操作业务处理
    if (!dealData())
    {
        return false;
    }
    logger.debug("after dealData...");

    //准备提交后台的数据
    if (!prepareOutputData())
    {
       return false;
    }
    logger.debug("after prepareOutputData...");

    //数据提交
    PubSubmit tPubSubmit = new PubSubmit();
    if (!tPubSubmit.submitData(mInputData, ""))
    {
        mErrors.copyAllErrors(tPubSubmit.mErrors);
        CError.buildErr(this,"数据提交失败!");
        return false;
    }
    mInputData = null;

    return true;
  }

  /**
   * 数据操作业务处理
   * @return: boolean
   */
  private boolean dealData()
  {

      PEdorTIDetailBL tPEdorTIDetailBL = new PEdorTIDetailBL();

      if (!tPEdorTIDetailBL.submitData(mInputData, mOperate))
      {
          mErrors.copyAllErrors(tPEdorTIDetailBL.mErrors);
          return false;
      }
      mResult = tPEdorTIDetailBL.getResult();
      map.add((MMap) mResult.getObjectByObjectName("MMap", 0));

      return true;
  }

  /**
   * 得到外部传入的数据
   * @return: boolean
   */
  private boolean getInputData()
  {

      return true;
  }

  /**
   * 准备提交后台的数据
   * @return: boolean
   */
  private boolean prepareOutputData()
  {
      try
      {
          mInputData.clear();
          mInputData.add(map);
      }
      catch (Exception ex)
      {
          // @@错误处理
          CError tError = new CError();
          tError.moduleName = "PEdorTIDetailBLF";
          tError.functionName = "prepareOutputData";
          tError.errorMessage = "在准备往后层处理所需要的数据时出错:" +
                                ex.toString();
          mErrors.addOneError(tError);
          return false;
      }

      return true;
  }

  /**
   * 返回处理结果
   * @return: VData
   */
  public VData getResult()
  {
      return mResult;
  }

  /**
   * 返回处理结果
   * @return: VData
   */
  public CErrors getErrors()
  {
      return mErrors;
  }

  public static void main(String[] args)
  {

  }
}
