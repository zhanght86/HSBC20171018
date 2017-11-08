/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.LLParaPrintBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.*;
import com.sinosoft.service.BusinessService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 结案时单证打印业务类 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: yuejw
 * @version 1.0
 */
public class LLParaPrintUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LLParaPrintUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLParaPrintUI()
    {
    }

    public static void main(String[] args)
    {
//        CErrors tError = null;
//        String FlagStr = "Fail";
//        String Content = "";
//
//        GlobalInput tGI = new GlobalInput();
//        tGI.Operator="001";
//        tGI.ComCode="86";
//        tGI.ManageCom="86";
//
//        String transact="SinglePrt||Print";
//        LLParaPrintSchema tLLParaPrintSchema = new LLParaPrintSchema();
//        tLLParaPrintSchema.setPrtCode("PCT010 ");
////        String tPrtCode=tLLParaPrintSchema.getPrtCode();
//        //String使用TransferData打包后提交-----用于传送 赔案号、客户号
//        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("ClmNo", "90000002449"); //赔案号
//        tTransferData.setNameAndValue("CustNo","0000523090"); //客户号
//        try
//        {
//                //准备传输数据 VData
//           VData tVData = new VData();
//           tVData.add(tGI);
//           tVData.add(tLLParaPrintSchema);
//           tVData.add(tTransferData);
//
//           LLParaPrintUI tLLParaPrintUI=new LLParaPrintUI();
//          if (!tLLParaPrintUI.submitData(tVData, transact))
//          {
//              Content = "提交失败，原因是: " +
//                        tLLParaPrintUI.mErrors.getError(0).errorMessage;
//              FlagStr = "Fail";
//          }
//          else
//          {
//              Content = "数据提交成功";
//              FlagStr = "Succ";
//          }
//
//        }
//        catch(Exception ex)
//        {
//            Content = "数据提交失败，原因是:" + ex.toString();
//            FlagStr = "Fail";
//        }

    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData,String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;
        LLParaPrintBL tLLParaPrintBL = new LLParaPrintBL();
        logger.debug("----------UI BEGIN----------");
        if (!tLLParaPrintBL.submitData(mInputData,mOperate))
        {
                // @@错误处理
            this.mErrors.copyAllErrors(tLLParaPrintBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLParaPrintUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLParaPrintBL.getResult();
        }
        mInputData = null;
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
