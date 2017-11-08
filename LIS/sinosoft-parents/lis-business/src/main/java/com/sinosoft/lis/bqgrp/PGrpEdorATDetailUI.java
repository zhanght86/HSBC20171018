package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;


import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:被保险人资料变更功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Tjj
 * @version 1.0
 */
public class PGrpEdorATDetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(PGrpEdorATDetailUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public PGrpEdorATDetailUI() {}

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;
    PGrpEdorATDetailBL tPGrpEdorATDetailBL = new PGrpEdorATDetailBL();
    logger.debug("---UI BEGIN---"+mOperate);
    if (tPGrpEdorATDetailBL.submitData(cInputData,mOperate) == false)
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tPGrpEdorATDetailBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "PGrpEdorCTDetailUI";
      tError.functionName = "submitData";
      tError.errorMessage = "数据查询失败!";
      this.mErrors .addOneError(tError) ;
      mResult.clear();
      return false;
    }
    else
      mResult = tPGrpEdorATDetailBL.getResult();
    
    mResult.clear();
    return true;
  }

  public VData getResult()
  {
    return mResult;
  }

  public static void main(String[] args)
  {

  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return this.mErrors;
}
}
