package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.get.PayPlanBL;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:生存领取确认（界面输入）
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author GYJ
 * @version 1.0
 */
public class PayPlanUI  implements BusinessService{
private static Logger logger = Logger.getLogger(PayPlanUI.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors=new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData =new VData();
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;
  //业务处理相关变量
   /** 全局数据 */
  private LCPolSchema mLCPolSchema=new LCPolSchema();
  private GlobalInput mGlobalInput =new GlobalInput() ;
  public PayPlanUI () {
  }
   /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate =cOperate;
    mInputData = (VData)cInputData.clone();

    PayPlanBL tPayPlanBL=new PayPlanBL();
    logger.debug("Start PayPlan UI Submit...");
    tPayPlanBL.submitData(mInputData,mOperate);
    logger.debug("End PayPlan UI Submit...");
    //如果有需要处理的错误，则返回
    if (tPayPlanBL.mErrors .needDealError() )
    {
  		// @@错误处理
      this.mErrors.copyAllErrors(tPayPlanBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "PayPlanUI";
      tError.functionName = "submitData";
      tError.errorMessage = "数据提交失败!";
      this.mErrors .addOneError(tError) ;
      return false;
    }
    else
      mResult = tPayPlanBL.getResult();

    return true;
  }
  public VData getResult()
 {
   return mResult;
  }
  public CErrors getErrors()
  {
      return mErrors;
  }

   public static void main(String[] args) {
    String tDay="2001-01-01";
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput.Operator ="Admin";
    tGlobalInput.ComCode  ="001";
    tGlobalInput.ManageCom="001";
    VData tVData = new VData();
    tVData.addElement(tGlobalInput) ;
    LCPolSchema tLCPolSchema=new LCPolSchema();
    TransferData aTransferData = new TransferData();
    aTransferData.setNameAndValue("timeStart","2002-10-01");
    aTransferData.setNameAndValue("timeEnd","2002-12-01");

    //tLCPolSchema.setPolNo("00000120020110000050");
    tVData.addElement(tLCPolSchema);
    tVData.addElement(aTransferData);
    PayPlanUI tPayPlanUI = new PayPlanUI();
    tPayPlanUI.submitData(tVData,"INSERT||PERSON");

  }

}
