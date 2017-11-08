package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.FICostTypeDefSet;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.db.FICostTypeDefDB;
import com.sinosoft.lis.schema.FICertificateTypeDefSchema;
import com.sinosoft.utility.CError;


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
public class FIDistillDealCertificateForHC {
private static Logger logger = Logger.getLogger(FIDistillDealCertificateForHC.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 存储处理信息 */
    private VData mInputInfo = new VData();

    private FICertificateTypeDefSchema mFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
    private FIDistillDealCostForHC mFIDistillDealCostForHC[];

    private final String enter = "\r\n"; // 换行符

    private GlobalInput mGlobalInput = new GlobalInput();

    private String BusinessNo = "";
    private String BatchNo = "";
    private String VersionNo = "";

    public LogInfoDeal tLogInfoDeal = null;
    public FiDistillErrInfo fidistillerrinfo = new FiDistillErrInfo();

    public FIDistillDealCertificateForHC(LogInfoDeal sLogInfoDeal)
    {
        this.tLogInfoDeal = sLogInfoDeal;
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
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "红冲提数处理类开始校验传入参数"  + enter);

            mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
            BusinessNo = (String) cInputData.get(1);
            BatchNo = (String) cInputData.get(2);
            VersionNo = (String) cInputData.get(3);

            if (mGlobalInput == null)
            {
                buildError("FIDistillDealCertificateForHC","getInputData",  "FIReDistillMain前台传入的登陆信息为空!");
                tLogInfoDeal.WriteLogTxt("FIReDistillMain前台传入的登陆信息为空!" + enter);
                return false;
            }

            if (BusinessNo == null || BusinessNo.equals(""))
            {
                buildError("FIDistillDealCertificateForHC","getInputData", "FIReDistillMain前台传入的业务关联号码为空!");
                tLogInfoDeal.WriteLogTxt("FIReDistillMain前台传入的终止日期为空!" + enter);
                return false;

            }
            if (BatchNo == null || BatchNo.equals(""))
            {
                buildError("FIDistillDealCertificateForHC","getInputData", "FIReDistillMain前台传入的批次号码为空!");
                tLogInfoDeal.WriteLogTxt("FIReDistillMain前台传入的批次号码为空!" + enter);
                return false;
            }
            if (VersionNo == null || VersionNo.equals(""))
            {
                buildError("FIDistillDealCertificateForHC","getInputData", "FIReDistillMain前台传入的版本号码为空!");
                tLogInfoDeal.WriteLogTxt("FIReDistillMain前台传入的版本号码为空!" + enter);
                return false;
            }


            mInputInfo.clear();
            mInputInfo.add(mGlobalInput);
            mInputInfo.add(BusinessNo);
            mInputInfo.add(BatchNo);
            mInputInfo.add(VersionNo);

            return true;
        }
        catch (Exception e)
        {
            buildError("FIDistillDealCertificateForHC","getInputData", "FIDistillDealCertificateForHC获取参数时出现异常，异常信息为：" + e.getMessage());
            tLogInfoDeal.WriteLogTxt("FIDistillDealCertificateForHC类获取参数时出现异常，异常信息为：" + e.getMessage());
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
                mFIDistillDealCostForHC = new FIDistillDealCostForHC[tFICostTypeDefSet.size()];
                for (int i = 1; i <= tFICostTypeDefSet.size(); i++)
                {
                    mFIDistillDealCostForHC[i -1] = new FIDistillDealCostForHC(tLogInfoDeal);
                    if (!mFIDistillDealCostForHC[i -1].InitInfo(tFICostTypeDefSet.get(i)))
                    {
                        this.mErrors.copyAllErrors(mFIDistillDealCostForHC[i -1].mErrors);
                        tLogInfoDeal.WriteLogTxt("费用类型" + tFICostTypeDefSet.get(i).getCostName() + "[" + tFICostTypeDefSet.get(i).getCostID() +  "]" + "提数类初始化失败"  + enter);
                        return false;
                    }
                }
                return true;
            }
            else
            {
                buildError("FIDistillDealCertificateForHC","InitInfo", "类型编号为" + CertificateID + "凭证没有费用类型定义记录");
                tLogInfoDeal.WriteLogTxt("类型编号为" + CertificateID + "凭证没有费用类型定义记录" + enter);
                return false;
            }
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCertificateForHC","InitInfo", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类初试化异常" + "异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类初试化异常" + "异常信息为：" + ex.getMessage() + enter);
            return false;
        }
    }

    private boolean DealWithData()
    {
        try
        {
            tLogInfoDeal.WriteLogTxt("类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类开始执行"  + enter);
            for (int i = 0; i < mFIDistillDealCostForHC.length; i++)
            {
                if (!mFIDistillDealCostForHC[i].dealProcess(mInputInfo))
                {
                    this.mErrors.copyAllErrors(mFIDistillDealCostForHC[i].mErrors);
                    return false;
                }
            }

            return true;
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCertificateForHC","DealWithData", "类型编号为" + mFICertificateTypeDefSchema.getCertificateID() + "凭证提数处理类执行异常" + "异常信息为：" + ex.getMessage());
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
