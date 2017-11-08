package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.tb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;
/**
 * <p>Title: Web业务系统承保个人投保单交费状态查询部分</p>
 * <p>Description:接口功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author SXY
 * @version 1.0
 */
public class PolFeeStatusChkUI implements BusinessService
{
private static Logger logger = Logger.getLogger(PolFeeStatusChkUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public PolFeeStatusChkUI() {}

// @Main
  public static void main(String[] args)
  {
  GlobalInput tG = new GlobalInput();
  tG.Operator = "001";
  LCPolSchema p = new LCPolSchema();
  p.setProposalNo( "86110020030110003936" );
  p.setPolNo("86110020030110003971");

  LCPolSet tLCPolSet = new LCPolSet();
  tLCPolSet.add(p);

  //p = new LCPolSchema();
  //p.setProposalNo( "86220020020110000001" );
  //p.setPolNo("86220020020110000001");
  //tLCPolSet.add(p);

  VData tVData = new VData();
  tVData.add( tLCPolSet );
  tVData.add( tG );
  PolFeeStatusChkUI ui = new PolFeeStatusChkUI();
  if( ui.submitData( tVData, "" ) == true )
      logger.debug("---ok---");
  else
      logger.debug("---NO---");


  CErrors tError = ui.mErrors;
  int n = tError.getErrorCount();

  }

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;

    PolFeeStatusChkBL tPolFeeStatusChkBL = new PolFeeStatusChkBL();

    logger.debug("---PolFeeStatusChkUI BEGIN---");
    if (tPolFeeStatusChkBL.submitData(cInputData,mOperate) == false)
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tPolFeeStatusChkBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "PolFeeStatusChkUI";
      tError.functionName = "submitData";
      //tError.errorMessage = "数据查询失败!";
      this.mErrors .addOneError(tError) ;
      mResult.clear();
      return false;
    }
    else
    {
      mResult = tPolFeeStatusChkBL.getResult();
      this.mErrors.copyAllErrors(tPolFeeStatusChkBL.mErrors);
    }

    return true;
  }

  public VData getResult()
  {
    return mResult;
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return mErrors;
}
}
