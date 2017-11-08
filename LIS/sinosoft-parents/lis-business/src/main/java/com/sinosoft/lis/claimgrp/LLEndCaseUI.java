/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.LLEndCaseBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.service.BusinessService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔结案信息提交类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLEndCaseUI implements BusinessService
{
private static Logger logger = Logger.getLogger(LLEndCaseUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    public LLEndCaseUI()
    {
    }

    public static void main(String[] args)
    {

//        LLClaimSchema tLLClaimSchema = new LLClaimSchema(); //案件核赔表
//
//        GlobalInput tGI = new GlobalInput();
//        LLEndCaseUI tLLEndCaseUI = new LLEndCaseUI();
//
//        String transact = "UPDATE";//获取操作insert||update
//        String isReportExist = "false";//是否为新增事件，是false，否true
//        tGI.ManageCom="86";
//        tGI.Operator="001";
//        //获取报案页面信息
//        tLLClaimSchema.setClmNo("90000000130");//赔案号
//        tLLClaimSchema.setCaseNo("90000000130");//分案号=报案号=赔案号
//        tLLClaimSchema.setRgtNo("90000000130");//报案号=赔案号
//        tLLClaimSchema.setExamConclusion("1");//审核结论
//        tLLClaimSchema.setExamIdea("1");//审核意见
//
//        VData tVData = new VData();
//        tVData.add(tGI);
//        tVData.add(isReportExist);
//        tVData.add(tLLClaimSchema);
//
//        tLLEndCaseUI.submitData(tVData,transact);
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

        LLEndCaseBL tLLEndCaseBL = new LLEndCaseBL();

        logger.debug("----------UI BEGIN----------");
        if (tLLEndCaseBL.submitData(mInputData,mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLEndCaseBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLEndCaseUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors .addOneError(tError) ;
            mResult.clear();
            return false;
        }
        else
        {
            mResult = tLLEndCaseBL.getResult();
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
