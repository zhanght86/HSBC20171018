package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICostTypeDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FICertificateTypeDefSchema;
import com.sinosoft.lis.vschema.FICostTypeDefSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


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

public class FIDistillDealCertificate
{
private static Logger logger = Logger.getLogger(FIDistillDealCertificate.class);

    /** 存储处理信息 */
    private VData mInputInfo = new VData();
    public CErrors mErrors = new CErrors();

    private FICertificateTypeDefSchema mFICertificateTypeDefSchema = new FICertificateTypeDefSchema();

    private FIDistillDealCost mFIDistillDealCost[];
    public LogInfoDeal tLogInfoDeal;

    /** 业务数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private String StartDate = "";
    private String EndDate = "";
    private String BatchNo = "";
    private String VersionNo = "";
    private final String enter = "\r\n"; // 换行符


    public FIDistillDealCertificate(LogInfoDeal oDeal)
    {
        tLogInfoDeal = oDeal;
    }

    public static void main(String[] args)
    {
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
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类开始校验传入参数"  + enter);

            mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
            StartDate = (String) cInputData.get(1);
            EndDate = (String) cInputData.get(2);
            BatchNo = (String) cInputData.get(3);
            VersionNo = (String) cInputData.get(4);

            if (mGlobalInput == null)
            {
                buildError("FIDistillDealCertificate","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "FIDistillMain前台传入的登陆信息为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillMain前台传入的登陆信息为空!" + enter);
                return false;
            }

            if (StartDate == null || StartDate.equals(""))
            {
                buildError("FIDistillDealCertificate","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "FIDistillMain前台传入的起始日期为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillMain前台传入的起始日期为空!" + enter);
                return false;
            }
            if (EndDate == null || EndDate.equals(""))
            {
                buildError("FIDistillDealCertificate","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "FIDistillMain前台传入的终止日期为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillMain前台传入的终止日期为空!" + enter);
                return false;

            }
            if (BatchNo == null || BatchNo.equals(""))
            {
                buildError("FIDistillDealCertificate","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "FIDistillMain前台传入的批次号码为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillMain前台传入的批次号码为空!" + enter);
                return false;
            }
            if (VersionNo == null || VersionNo.equals(""))
            {
                buildError("FIDistillDealCertificate","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "FIDistillMain前台传入的版本号码为空!");
                tLogInfoDeal.WriteLogTxt("FIDistillMain前台传入的版本号码为空!" + enter);
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
        catch (Exception e)
        {
            buildError("FIDistillDealCertificate","getInputData", "FIDistillDealCertificate获取参数时出现异常，异常信息为：" + e.getMessage());
            tLogInfoDeal.WriteLogTxt("FIDistillDealCertificate类获取参数时出现异常，异常信息为：" + e.getMessage());
            return false;
        }
    }


    public boolean InitInfo(FICertificateTypeDefSchema tFICertificateTypeDefSchema)
    {

        mFICertificateTypeDefSchema = tFICertificateTypeDefSchema;
        try
        {
            String CertificateID = mFICertificateTypeDefSchema.getCertificateID();
            tLogInfoDeal.WriteLogTxt("开始初始化凭证"+ tFICertificateTypeDefSchema.getCertificateName() + "[" + CertificateID + "]"+ "数据采集类" + enter);

            FICostTypeDefSet tFICostTypeDefSet = new FICostTypeDefSet();
            FICostTypeDefDB tFICostTypeDefDB = new FICostTypeDefDB();
            tFICostTypeDefDB.setCertificateID(CertificateID);
            tFICostTypeDefDB.setVersionNo(VersionNo);
            tFICostTypeDefSet = tFICostTypeDefDB.query();

            if (tFICostTypeDefSet.size() > 0)
            {
                tLogInfoDeal.WriteLogTxt("开始初始化凭证"+ tFICertificateTypeDefSchema.getCertificateName() + "[" + CertificateID + "]"+ "数据采集类" + enter);
                mFIDistillDealCost = new FIDistillDealCost[tFICostTypeDefSet.size()];
                for (int i = 1; i <= tFICostTypeDefSet.size(); i++)
                {
                    mFIDistillDealCost[i -1] = new FIDistillDealCost(tLogInfoDeal);
                    if (!mFIDistillDealCost[i -1].InitInfo(tFICostTypeDefSet.get(i)))
                    {
                        this.mErrors.copyAllErrors(mFIDistillDealCost[i -1].mErrors);
                        tLogInfoDeal.WriteLogTxt("费用类型" + tFICostTypeDefSet.get(i).getCostName() + "[" + tFICostTypeDefSet.get(i).getCostID() +  "]" + "提数类初始化失败"  + enter);
                    	return false;
                    }
                }
                return true;
            }
            else
            {
                buildError("FIDistillMain","InitInfo", "类型编号为" + CertificateID + "凭证没有费用类型定义记录");
                tLogInfoDeal.WriteLogTxt("类型编号为" + CertificateID + "凭证没有费用类型定义记录" + enter);
                return false;
            }
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCertificate","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类初试化异常" + "异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类初试化异常" + "异常信息为：" + ex.getMessage() + enter);
            return false;
        }
    }

    private boolean DealWithData()
    {
        try
        {
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类开始执行"  + enter);
            for (int i = 0; i < mFIDistillDealCost.length; i++)
            {
                if (!mFIDistillDealCost[i].dealProcess(mInputInfo))
                {
                    this.mErrors.copyAllErrors(mFIDistillDealCost[i].mErrors);
                    return false;
                }
            }

            return true;
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCertificate","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类执行异常" + "异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类执行异常" + "异常信息为：" + ex.getMessage() + enter);
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
}
