package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:承保暂交费功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HST
 * @version 1.0
 */
public class CertifyMaxSaveUI
{
private static Logger logger = Logger.getLogger(CertifyMaxSaveUI.class);

  public  CErrors mErrors = new CErrors();
  private VData mInputData = new VData();
  private VData mResult = new VData();
  private String mOperate;
  public CertifyMaxSaveUI() {}
  public boolean submitData(VData cInputData,String cOperate)
  {
    this.mOperate = cOperate;
   CertifyMaxSaveBL tCertifyMaxSaveBL = new CertifyMaxSaveBL();
   logger.debug("开始执行到CertifyMaxSaveUI");
   if (tCertifyMaxSaveBL.submitData(cInputData,mOperate) == false)
    {
      this.mErrors.copyAllErrors(tCertifyMaxSaveBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "tCertifyMaxSaveBL";
      tError.functionName = "submitData";
      tError.errorMessage = "数据操作失败！！！";
      this.mErrors .addOneError(tError) ;
      mResult.clear();
      return false;
    }
    else
			mResult = tCertifyMaxSaveBL.getResult();
    return true;
  }

  public VData getResult()
  {
  	return mResult;
  }
}
