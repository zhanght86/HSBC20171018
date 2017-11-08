package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.tb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
/**
 * <p>Title: 保单修改</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class DelPrtUI
{
private static Logger logger = Logger.getLogger(DelPrtUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 往界面传输数据的容器 */
  private VData mResult = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public DelPrtUI() {}

// @Main
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

	DelPrtBL tDelPrtBL = new DelPrtBL();

	logger.debug("---DelPrtUI BEGIN---");
	if (tDelPrtBL.submitData(cInputData,mOperate) == false)
	{
	  mResult.clear();          
          this.mErrors.copyAllErrors(tDelPrtBL.mErrors);
	  return false;
	}
	else
	{
	  mResult = tDelPrtBL.getResult();
	  this.mErrors.copyAllErrors(tDelPrtBL.mErrors);
	}

	return true;
  }

  public VData getResult()
  {
	return mResult;
  }
}
