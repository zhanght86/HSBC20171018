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
public class CertifyFeeUI
{
private static Logger logger = Logger.getLogger(CertifyFeeUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  private VData mInputData = new VData();
  private VData mResult = new VData();
  private String mOperate;
  public CertifyFeeUI() 
  {
  	
  }

  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;
    CertifyFeeBL tCertifyFeeBL = new CertifyFeeBL();
    logger.debug("---开始执行CertifyFeeUI---");
    if (tCertifyFeeBL.submitData(cInputData,mOperate) == false)
    {
  		// @@错误处理
      this.mErrors.copyAllErrors(tCertifyFeeBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "ReportUI";
      tError.functionName = "submitData";
      tError.errorMessage = "数据保存失败!";
      this.mErrors .addOneError(tError) ;
      mResult.clear();
      return false;
    }
    else
    {
			mResult = tCertifyFeeBL.getResult();
		}
    return true;
  }

  public VData getResult()
  {
  	return mResult;
  }

  public static void main(String[] args)
  {
  }
}
