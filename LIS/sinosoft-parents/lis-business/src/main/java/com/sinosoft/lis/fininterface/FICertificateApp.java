package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIDataFeeBackAppSchema;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.db.FIAboriginalDataTempDB;
import com.sinosoft.lis.vschema.FIAboriginalDataTempSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.lis.pubfun.PubFun1;


/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author jw
 * @version 1.0
 */

public class FICertificateApp  implements BusinessService
{
private static Logger logger = Logger.getLogger(FICertificateApp.class);


    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;
    private MMap mmap = new MMap();
    // 保存操作员和管理机构的类
    private GlobalInput mGI = new GlobalInput();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String tAskPrtNo = "";

    private FIDataFeeBackAppSchema mFIDataFeeBackAppSchema = new FIDataFeeBackAppSchema();

    public LogInfoDeal tLogInfoDeal ;
    public static void main(String[] args)
    {

    }

    private boolean InitInfo()
    {
        try
        {
            tAskPrtNo = "APP" + PubFun1.CreateMaxNo("FinAppNo",15);
            tLogInfoDeal = new LogInfoDeal(mGI.Operator,"04");
            tLogInfoDeal.AddLogParameter("AppNo","红冲申请号码",tAskPrtNo);
            tLogInfoDeal.AddLogParameter("CertificateID","凭证类型",mFIDataFeeBackAppSchema.getCertificateID());
            tLogInfoDeal.AddLogParameter("BusinessNo","业务号码",mFIDataFeeBackAppSchema.getBusinessNo());
            if(!tLogInfoDeal.SaveLogParameter())
            {
                buildError("FICertificateApp","InitInfo",tLogInfoDeal.mErrors.getFirstError());
                return false;
            }

        }
        catch (Exception ex)
        {
            buildError("FICertificateApp","InitInfo","FICertificateApp生成日志信息出现异常，信息为：" + ex.getMessage());
            return false;
        }
        return true;
    }


    // 传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!getInputData(cInputData)){
            return false;
        }
        if (!checkData()) {
            return false;
        }
        if (!InitInfo()) {
            return false;
        }
        if (!dealData()) {
            return false;
        }
        tLogInfoDeal.Complete(true);
        return true;
    }

    // 根据前面的输入数据，进行逻辑处理
    // 如果在处理过程中出错，则返回false,否则返回true
    private boolean dealData()
    {
        try
        {
            mFIDataFeeBackAppSchema.setAppNo(tAskPrtNo);
            mFIDataFeeBackAppSchema.setMakeDate(CurrentDate);
            mFIDataFeeBackAppSchema.setMakeTime(CurrentTime);
            mmap.put(mFIDataFeeBackAppSchema, "INSERT");
            mInputData = new VData();
            mInputData.add(mmap);
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, ""))
            {
               this.mErrors.copyAllErrors(tPubSubmit.mErrors);
               return false;
            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FICertificateApp","dealData","FICertificateApp保存后台数据出现异常，信息为：" + ex.getMessage());
            return false;
        }

    }


    /**
     * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData mInputData)
    {
        mGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        mFIDataFeeBackAppSchema.setSchema((FIDataFeeBackAppSchema) mInputData.getObjectByObjectName("FIDataFeeBackAppSchema", 0));
        if (mGI == null)
        {
            buildError("FICertificateApp", "getInputData", "传入登陆信息参数为空");
            return false;
        }
        if (mFIDataFeeBackAppSchema == null)
        {
            buildError("FICertificateApp", "getInputData", "传入红冲信息参数为空");
            return false;
        }
        return true;
    }

    private boolean checkData()
    {
        FIAboriginalDataTempDB tFIAboriginalDataTempDB = new FIAboriginalDataTempDB();
        FIAboriginalDataTempSet tFIAboriginalDataTempSet = new FIAboriginalDataTempSet();
        String tSql = "select * from FIAboriginalDataTemp a where a.CertificateID = '?CertificateID?' and a.BusinessNo = '?BusinessNo?' and exists (select 1 from FIDataTransGather f where f.batchno = a.batchno) and not exists (select * from FIDataTransGather b where b.batchno = a.batchno and b.VoucherNo is null)";
        logger.debug(tSql);
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(tSql);
        sqlbv.put("CertificateID", mFIDataFeeBackAppSchema.getCertificateID());
        sqlbv.put("BusinessNo", mFIDataFeeBackAppSchema.getBusinessNo());
        tFIAboriginalDataTempSet = tFIAboriginalDataTempDB.executeQuery(sqlbv);
        if(tFIAboriginalDataTempDB.mErrors.needDealError())
        {
            buildError("FICertificateApp", "checkData", "执行数据红冲数据校验语句异常：" + tFIAboriginalDataTempDB.mErrors.getFirstError());
            return false;
        }
        if(tFIAboriginalDataTempSet.size()<1)
        {
            buildError("FICertificateApp", "checkData", "该红冲登记业务信息无对应已生成凭证数据");
            return false;
        }

        return true;
    }




    private void buildError(String szModuleName, String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = szModuleName;
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }

	public CErrors getErrors() {
		return this.mErrors;
	}

	public VData getResult() {
		return null;
	}

}
