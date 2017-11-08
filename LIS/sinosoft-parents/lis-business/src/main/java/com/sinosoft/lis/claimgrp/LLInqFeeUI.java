/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.LLInqFeeBL;
import com.sinosoft.lis.schema.LLInqFeeSchema;
import com.sinosoft.lis.pubfun.GlobalInput;


/**
 * <p>Title: 调查费用录入信息</p>
 * <p>Description:调查费用录入信息类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLInqFeeUI
{
private static Logger logger = Logger.getLogger(LLInqFeeUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    public LLInqFeeUI()
    {
    }

    public static void main(String[] args)
    {

//        LLInqFeeSchema tLLInqFeeSchema = new LLInqFeeSchema(); //案件核赔表
//
//        GlobalInput tGI = new GlobalInput();
//        LLInqFeeUI tLLInqFeeUI = new LLInqFeeUI();
//
//        String transact = "INSERT";//获取操作insert||update
//        String isReportExist = "false";//是否为新增事件，是false，否true
//        tGI.ManageCom="86";
//        tGI.Operator="001";
//        //获取费用录入页面信息
//        tLLInqFeeSchema.setClmNo("90000000750");//赔案号
//        tLLInqFeeSchema.setInqNo("0000000066");//调查序号
//        tLLInqFeeSchema.setInqDept("86");//调查机构
//        tLLInqFeeSchema.setFeeType("FFFFF");//费用类型
//        tLLInqFeeSchema.setFeeDate("2005-06-06");//发生时间
//        tLLInqFeeSchema.setFeeSum("2000");//金额
//        tLLInqFeeSchema.setPayee("AAAAA");//领款人
//        tLLInqFeeSchema.setPayeeType("BBBBB");//领款方式
//        tLLInqFeeSchema.setRemark("CCCCcc");//领款方式

//        VData tVData = new VData();
//        tVData.add(tGI);
//        tVData.add(isReportExist);
//        tVData.add(tLLInqFeeSchema);

//        tLLInqFeeUI.submitData(tVData,transact);
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
        this.mOperate = cOperate;

        LLInqFeeBL tLLInqFeeBL = new LLInqFeeBL();

        logger.debug("----------UI BEGIN----------");
        if (tLLInqFeeBL.submitData(mInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLInqFeeBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLInqFeeUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLInqFeeBL.getResult();
        }
        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

}
