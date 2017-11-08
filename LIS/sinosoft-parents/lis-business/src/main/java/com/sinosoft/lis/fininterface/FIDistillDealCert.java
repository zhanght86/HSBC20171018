package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.FICostTypeDefSet;
import com.sinosoft.lis.db.FICostTypeDefDB;
import com.sinosoft.lis.schema.FICertificateTypeDefSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;


public class FIDistillDealCert {
private static Logger logger = Logger.getLogger(FIDistillDealCert.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    public LogInfoDeal tLogInfoDeal;

    private FICertificateTypeDefSchema mFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
    private FICostTypeDefSet mFICostTypeDefSet = new FICostTypeDefSet();
    private FICertDealCost mFICertDealCost[];
    private final String enter = "\r\n"; // 换行符

    public FIDistillDealCert() {

    }

    public static void main(String[] args) {

    }

    public boolean dealProcess(VData cInputData)
    {
        if(!DealWithData(cInputData))
        {
            return false;
        }
        return true;
    }


    public boolean InitFIDistillDealCert(FICertificateTypeDefSchema tFICertificateTypeDefSchema,LogInfoDeal ttLogInfoDeal)
    {
        try
        {
          mFICertificateTypeDefSchema = tFICertificateTypeDefSchema;
          tLogInfoDeal = ttLogInfoDeal;

          tLogInfoDeal.WriteLogTxt("开始初试化凭证类型为" +  mFICertificateTypeDefSchema.getCertificateID() + "的账务规则处理类" + enter);

          FICostTypeDefDB tFICostTypeDefDB = new FICostTypeDefDB();
          tFICostTypeDefDB.setCertificateID(mFICertificateTypeDefSchema.getCertificateID());
          tFICostTypeDefDB.setVersionNo(mFICertificateTypeDefSchema.getVersionNo());
          mFICostTypeDefSet = tFICostTypeDefDB.query();

          if (tFICostTypeDefDB.mErrors.needDealError())
          {
              buildError("FIDistillDealCert","InitInfo", "凭证" + mFICertificateTypeDefSchema.getCertificateID() + "关联费用定义查询失败" + tFICostTypeDefDB.mErrors.getFirstError() );
              tLogInfoDeal.WriteLogTxt("凭证" + mFICertificateTypeDefSchema.getCertificateID() + "关联费用定义查询失败"  + tFICostTypeDefDB.mErrors.getFirstError() + enter);
              return false;
          }
          if (mFICostTypeDefSet.size() < 1)
          {
              buildError("FIDistillDealCert","InitInfo", "未查询到任何专项定义");
              tLogInfoDeal.WriteLogTxt("未查询到任何专项定义" + enter);
              return false;
          }

          mFICertDealCost = new FICertDealCost[mFICostTypeDefSet.size()];
          for (int i = 1; i <= mFICostTypeDefSet.size(); i++)
          {
              mFICertDealCost[i - 1] = new FICertDealCost();
              if (!mFICertDealCost[i -1].InitFICertDealCost(mFICostTypeDefSet.get(i), tLogInfoDeal))
              {
                  this.mErrors.copyAllErrors(mFICertDealCost[i -1].mErrors);
                  return false;
              }
          }
          return true;

      }
      catch(Exception ex)
      {
          buildError("FIDistillDealCert","InitInfo", "凭证" + mFICertificateTypeDefSchema.getCertificateID() + "的账务规则处理类初始化异常，错误为：" + ex.getMessage());
          tLogInfoDeal.WriteLogTxt("凭证" + mFICertificateTypeDefSchema.getCertificateID() + "的账务规则处理类初始化异常，错误为："  + ex.getMessage() + enter);
          return false;
      }
    }

    private boolean DealWithData(VData cInputData)
    {
        try
        {
            for(int i=0;i<mFICertDealCost.length;i++)
            {
                if (!mFICertDealCost[i].dealProcess(cInputData))
                {
                    this.mErrors.copyAllErrors(mFICertDealCost[i].mErrors);
                    return false;
                }
            }
           return true;
        }
        catch (Exception ex)
        {
            buildError("FIDistillDealCert","DealWithData", "凭证" + mFICertificateTypeDefSchema.getCertificateID() + "的账务规则处理类执行出现异常，信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("凭证" + mFICertificateTypeDefSchema.getCertificateID() + "的账务规则处理类执行出现异常，信息为：" + ex.getMessage() + enter);
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
