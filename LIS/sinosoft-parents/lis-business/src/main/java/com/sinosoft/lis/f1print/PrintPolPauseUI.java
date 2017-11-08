package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:承保暂交费功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author HST
 * @version 1.0
 */
public class PrintPolPauseUI
{
private static Logger logger = Logger.getLogger(PrintPolPauseUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public PrintPolPauseUI() {}


  public static void main(String[] args)
  {
  }
  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;

   PrintPolPauseBL tPrintPolPauseBL = new PrintPolPauseBL();

    if (tPrintPolPauseBL.submitData(cInputData,mOperate) == false)
    {
  		// @@错误处理
      this.mErrors.copyAllErrors(tPrintPolPauseBL.mErrors);
      mResult.clear();
      return false;
    }
    else
			mResult = tPrintPolPauseBL.getResult();
    return true;
  }

  public VData getResult()
  {
  	return mResult;
  }
}
