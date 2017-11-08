package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.get.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description:保单查询功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author TJJ
 * @version 1.0
 */
public class LFGetPayQueryUI
{
private static Logger logger = Logger.getLogger(LFGetPayQueryUI.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public  CErrors mErrors = new CErrors();
  /** 往后面传输数据的容器 */
  private VData mInputData = new VData();
  /** 数据操作字符串 */
  private String mOperate;

  public LFGetPayQueryUI() {}

  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData,String cOperate)
  {
    //将操作数据拷贝到本类中
    this.mOperate = cOperate;

    LFGetPayQueryBL tLFGetPayQueryBL=new LFGetPayQueryBL();

    if (tLFGetPayQueryBL.submitData(cInputData,mOperate) == false)
	{
  		// @@错误处理
            this.mErrors.copyAllErrors(tLFGetPayQueryBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LFGetPayQueryUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据查询失败!";
            this.mErrors .addOneError(tError) ;
            mInputData.clear();
            return false;
	}
	else
		mInputData = tLFGetPayQueryBL.getResult();

    return true;
  }

  public VData getResult()
  {
  	return mInputData;
  }
  public static void main(String[] args) {
    String tDay="2001-01-01";
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput.Operator ="Admin";
    tGlobalInput.ComCode  ="asd";
    tGlobalInput.ManageCom="sdd";
    VData tVData = new VData();
    LJSGetSchema tLJSGetSchema=new LJSGetSchema();
    tLJSGetSchema.setOtherNo("00000120020110000050");
    tVData.addElement(tLJSGetSchema);
    LFGetPayQueryUI tLFGetPayQueryUI = new LFGetPayQueryUI();
    tLFGetPayQueryUI.submitData(tVData,"QUERY||MAIN");
    LJSGetSet tLJSGetSet=new LJSGetSet();
    tLJSGetSet =(LJSGetSet)tLFGetPayQueryUI.getResult().getObjectByObjectName("LJSGetSet",0);
    LCPolSet tLCPolSet=new LCPolSet();
    tLCPolSet=(LCPolSet)tLFGetPayQueryUI.getResult().getObjectByObjectName("LCPolSet",0);

  }
}
