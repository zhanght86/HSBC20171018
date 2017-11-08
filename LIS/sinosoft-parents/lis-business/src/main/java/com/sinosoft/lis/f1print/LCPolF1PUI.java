/*
 * <p>ClassName: LCPolF1PUI </p>
 * <p>Description: LCPolF1PUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-11-04
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.*;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.BusinessService;
public class LCPolF1PUI implements BusinessService{
private static Logger logger = Logger.getLogger(LCPolF1PUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors=new CErrors();

	private VData mResult = new VData();

	//业务处理相关变量
 	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput() ;
	private LCPolSet mLCPolSet = new LCPolSet();

	public LCPolF1PUI ()
	{
	}

 	/**
		传输数据的公共方法
	*/
	public boolean submitData(VData cInputData, String cOperate)
	{
    try {
      if( !cOperate.equals("PRINT")  // 正常打印
         && !cOperate.equals("REPRINT")  // 保单遗失补发
         && !cOperate.equals("PRINTEX") ) {  // 前台保单打印
        buildError("submitData", "不支持的操作字符串");
        return false;
      }

      // 得到外部传入的数据，将数据备份到本类中
      if( !getInputData(cInputData) ) {
        return false;
      }

      // 进行业务处理
      if( !dealData() ) {
        return false;
      }

      // 准备传往后台的数据
      VData vData = new VData();

      if( !prepareOutputData(vData) ) {
        return false;
      }

      LCPolF1PBL f1pLCPolBL = new LCPolF1PBL();
      logger.debug("Start LCPolF1P UI Submit ...");

      if( !f1pLCPolBL.submitData(vData, cOperate) ) {
        if( f1pLCPolBL.mErrors.needDealError() ) {
          mErrors.copyAllErrors(f1pLCPolBL.mErrors);
          return false;
        } else {
          buildError("sbumitData", "f1pLCPolBL发生错误，但是没有提供详细的出错信息");
          return false;
        }
      } else {
        mResult = f1pLCPolBL.getResult();
        return true;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      buildError("submit", "发生异常");
      return false;
    }
	}

	public static void main(String[] args)
	{
    LCPolF1PUI tLCPolF1PUI = new LCPolF1PUI();

    LCPolSet tLCPolSet = new LCPolSet();
    LCPolSchema tLCPolSchema = new LCPolSchema();

    tLCPolSchema.setPolNo("86110020030210006981");
    tLCPolSet.add(tLCPolSchema);

//    tLCPolSchema = new LCPolSchema();
//
//    tLCPolSchema.setPolNo("86110020030210001161");
//    tLCPolSet.add(tLCPolSchema);

    GlobalInput tGlobalInput = new GlobalInput();

    tGlobalInput.Operator = "Kevin";

    VData vData = new VData();

    vData.addElement(tGlobalInput);
    vData.addElement(tLCPolSet);

    if( !tLCPolF1PUI.submitData(vData, "PRINTEX") ) {
      logger.debug(tLCPolF1PUI.mErrors.getFirstError());
    } else {
      vData = tLCPolF1PUI.getResult();
      try {
        InputStream ins = (InputStream)vData.get(0);
        FileOutputStream fos = new FileOutputStream("LCPolData.xml");
        int n = 0;

        while( ( n = ins.read() ) != -1 ) {
          fos.write(n);
        }

        fos.close();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
	}

	/**
	* 准备往后层输出所需要的数据
	* 输出：如果准备数据时发生错误则返回false,否则返回true
	*/
	private boolean prepareOutputData(VData vData)
	{
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLCPolSet);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理
	 * 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		//全局变量
		mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		mLCPolSet.set((LCPolSet)cInputData.getObjectByObjectName("LCPolSet",0));

		if( mGlobalInput==null ) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}

  private void buildError(String szFunc, String szErrMsg)
  {
    CError cError = new CError( );

    cError.moduleName = "LCPolF1PUI";
    cError.functionName = szFunc;
    cError.errorMessage = szErrMsg;
    this.mErrors.addOneError(cError);
  }

public CErrors getErrors() {
	// TODO Auto-generated method stub
	return mErrors;
}
}
