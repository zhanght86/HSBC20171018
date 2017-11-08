package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;


import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.service.BusinessService;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:投连账户赎回
 * </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class PEdorARDetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(PEdorARDetailUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public PEdorARDetailUI() {}

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;
    PEdorARDetailBL tPEdorARDetailBL = new PEdorARDetailBL();
    logger.debug("---UI BEGIN---"+mOperate);
    if (tPEdorARDetailBL.submitData(cInputData,mOperate) == false)
    {
      // @@错误处理
      this.mErrors.copyAllErrors(tPEdorARDetailBL.mErrors);
      CError tError = new CError();
      tError.moduleName = "PEdorRDDetailUI";
      tError.functionName = "submitData";
      tError.errorMessage = "数据查询失败!";
      this.mErrors .addOneError(tError) ;
      mResult.clear();
      return false;
    }
    else
      mResult = tPEdorARDetailBL.getResult();
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
		return mErrors;
  }
}
