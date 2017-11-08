package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.FICostDataAcquisitionDefSet;
import com.sinosoft.lis.db.FICostDataAcquisitionDefDB;
import com.sinosoft.lis.schema.FICostTypeDefSchema;



/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author jw
 * @version 1.0
 */
public class FIDistillDealCost
{
private static Logger logger = Logger.getLogger(FIDistillDealCost.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 存储处理信息 */
    private VData mInputInfo = new VData();
    private FICostTypeDefSchema mFICostTypeDefSchema = new FICostTypeDefSchema();
    private FIDistillDealAquisition mFIDistillDealAquisition[];


    /** 业务数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private String StartDate = "";
    private String EndDate = "";
    private String BatchNo = "";
    private String VersionNo = "";


    private final String enter = "\r\n"; // 换行符
    StringBuffer logString = new StringBuffer();

    /**保存全局日志信息***/
    public LogInfoDeal tLogInfoDeal = null;

    public FIDistillDealCost(LogInfoDeal oDeal)
    {
    	tLogInfoDeal = oDeal;
    }

    public boolean dealProcess(VData cInputData)
    {
        if (!getInputData(cInputData))
        {
            return false;
        }
        if (!DealWithData())
        {
            return false;
        }
        return true;
    }


    private boolean getInputData(VData cInputData)
    {
        try
        {
            mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
            StartDate = (String) cInputData.get(1);
            EndDate = (String) cInputData.get(2);
            BatchNo = (String) cInputData.get(3);
            VersionNo = (String) cInputData.get(4);

            if (mGlobalInput == null) {
                buildError("FIDistillDealCost", "getInputData",
                           "类型编号为" +
                           mFICostTypeDefSchema.getCostID() +
                           "FIDistillDealCertificate前台传入的登陆信息为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillDealCertificate前台传入的登陆信息为空!" + enter);
                return false;
            }

            if (StartDate == null || StartDate.equals("")) {
                buildError("FIDistillDealCost", "getInputData",
                           "类型编号为" +
                           mFICostTypeDefSchema.getCostID() +
                           "FIDistillDealCertificate前台传入的起始日期为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillDealCertificate前台传入的起始日期为空!" + enter);
                return false;
            }
            if (EndDate == null || EndDate.equals("")) {
                buildError("FIDistillDealCost", "getInputData",
                           "类型编号为" +
                           mFICostTypeDefSchema.getCostID() +
                           "FIDistillDealCertificate前台传入的终止日期为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillDealCertificate前台传入的终止日期为空!" + enter);
                return false;

            }
            if (BatchNo == null || BatchNo.equals("")) {
                buildError("FIDistillDealCost", "getInputData",
                           "类型编号为" +
                           mFICostTypeDefSchema.getCostID() +
                           "FIDistillDealCertificate前台传入的批次号码为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillDealCertificate前台传入的批次号码为空!" + enter);
                return false;
            }
            if (VersionNo == null || VersionNo.equals("")) {
                buildError("FIDistillDealCost", "getInputData",
                           "类型编号为" +
                           mFICostTypeDefSchema.getCostID() +
                           "FIDistillDealCertificate前台传入的版本号码为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillDealCertificate前台传入的版本号码为空!" + enter);
                return false;
            }

            mInputInfo.clear();
            mInputInfo.add(mGlobalInput);
            mInputInfo.add(StartDate);
            mInputInfo.add(EndDate);
            mInputInfo.add(BatchNo);
            mInputInfo.add(VersionNo);

            return true;
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCost","getInputData", "类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类获得参数出现异常" + "异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类获得参数出现异常" + "异常信息为：" + ex.getMessage() + enter);
            return false;
        }
    }


    public boolean InitInfo(FICostTypeDefSchema tFICostTypeDefSchema)
    {
    	try
        {
            mFICostTypeDefSchema = tFICostTypeDefSchema;
            String CostID = mFICostTypeDefSchema.getCostID();

            tLogInfoDeal.WriteLogTxt("开始初试化费用类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类" + enter);

            FICostDataAcquisitionDefSet tFICostDataAcquisitionDefSet = new FICostDataAcquisitionDefSet();
            FICostDataAcquisitionDefDB tFICostDataAcquisitionDefDB = new FICostDataAcquisitionDefDB();
            tFICostDataAcquisitionDefDB.setCostOrDataID(CostID);
            tFICostDataAcquisitionDefDB.setVersionNo(VersionNo);
            tFICostDataAcquisitionDefDB.setAcquisitionType("01");
            tFICostDataAcquisitionDefSet = tFICostDataAcquisitionDefDB.query();

            if (tFICostDataAcquisitionDefSet.size() > 0)
            {
                tLogInfoDeal.WriteLogTxt("获得当前费用对应的采集定义包含" + tFICostDataAcquisitionDefSet.size() + "条记录。，开始初试化数据采集处理类" + enter);
                mFIDistillDealAquisition = new FIDistillDealAquisition[tFICostDataAcquisitionDefSet.size()];
                for (int i = 1; i <= tFICostDataAcquisitionDefSet.size(); i++)
                {
                    mFIDistillDealAquisition[i -1] = new FIDistillDealAquisition(tLogInfoDeal);
                    if (!mFIDistillDealAquisition[i -1].InitFIDistillDealAquisition(tFICostDataAcquisitionDefSet.get(i),mFICostTypeDefSchema))
                    {
                        this.mErrors.copyAllErrors(mFIDistillDealAquisition[i -1].mErrors);
                        tLogInfoDeal.WriteLogTxt("数据采集类型"  + "[" + tFICostDataAcquisitionDefSet.get(i).getAcquisitionID() +  "]" + "提数类初始化失败"  + enter);
                    	return false;
                    }
                }
                return true;
            }
            else
            {
                buildError("FIDistillDealCost","InitInfo", "类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数无任何数据采集定义信息");
                tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数无任何数据采集定义信息" + enter);
                return false;
            }
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCost","InitInfo", "类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类初始化异常" + "异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类初始化异常" + "异常信息为：" + ex.getMessage() + enter);
            return false;
        }
    }


    private boolean DealWithData()
    {
        try
        {
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类开始执行提数" + enter);
            for (int i = 0; i < mFIDistillDealAquisition.length; i++)
            {
                if (!mFIDistillDealAquisition[i].dealProcess(mInputInfo))
                {
                    this.mErrors.copyAllErrors(mFIDistillDealAquisition[i -1].mErrors);
                    return false;
                }
            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCost","InitInfo", "类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类提数异常" + "异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "凭证费用提数处理类提数异常" + "异常信息为：" + ex.getMessage() + enter);
            return false;
        }
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

    public static void main(String[] args)
    {

    }

}
