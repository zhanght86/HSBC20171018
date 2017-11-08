package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.db.FICertificateTypeDefDB;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.schema.FICertificateTypeDefSchema;
import com.sinosoft.lis.db.FIOperationLogDB;
import com.sinosoft.lis.vschema.FICertificateTypeDefSet;
import com.sinosoft.lis.vschema.FIOperationLogSet;
import com.sinosoft.lis.db.FIOperationParameterDB;
import com.sinosoft.lis.vschema.FIOperationParameterSet;
import com.sinosoft.utility.ExeSQL;

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
public class FICertificateRBProduceMain {
private static Logger logger = Logger.getLogger(FICertificateRBProduceMain.class);

        /** 错误处理类，每个需要错误处理的类中都放置该类 */
        public CErrors mErrors = new CErrors();
        /** 存储处理信息 */
        private VData mInputInfo = new VData();

        /** 业务数据 */

        private FICertificateTypeDefSchema mFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
        private FIDistillDealCert tFIDistillDealCert;
        private String CertificateID = "";
        private String AppNo = "";
        private String BatchNo = "";
        private GlobalInput mGlobalInput = new GlobalInput();
        private String VersionNo = "";

        private final String enter = "\r\n"; // 换行符
        public LogInfoDeal tLogInfoDeal;

        public FICertificateRBProduceMain() {}

        public boolean dealProcess(VData cInputData)
        {
            if (!getInputData(cInputData))
            {
                return false;
            }
            if (!InitInfo())
            {
                return false;
            }

            if (!DealWithData())
            {
                return false;
            }
            tLogInfoDeal.Complete(true);
            return true;
        }

        private boolean getInputData(VData cInputData)
        {
            mGlobalInput = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
            CertificateID =  (String) cInputData.get(1);
            AppNo = (String) cInputData.get(2);

            if (mGlobalInput == null)
            {
                buildError("FICertificateRBProduceMain", "getInputData", "传入登陆信息参数为空");
                return false;
            }
            if (CertificateID == null && CertificateID.equals(""))
            {
                buildError("FICertificateRBProduceMain", "getInputData", "传入登陆凭证类型参数为空");
                return false;
            }

            if (AppNo == null && AppNo.equals(""))
            {
                buildError("FICertificateRBProduceMain", "getInputData", "传入红冲申请号码参数为空");
                return false;
            }
            return true;
        }


        private boolean InitInfo()
        {
            try
            {
                FIOperationLogDB tFIOperationLogDB = new  FIOperationLogDB();
                FIOperationLogSet tFIOperationLogSet = new FIOperationLogSet();
                String tSql = "select * from fioperationlog where fioperationlog.eventtype = '07' and fioperationlog.performstate = '0' and exists (select * from fioperationparameter where fioperationparameter.eventno = fioperationlog.eventno and fioperationparameter.parametertype = 'AppNo' and fioperationparameter.parametervalue = '?AppNo?' )";
                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                sqlbv.sql(tSql);
                sqlbv.put("AppNo", AppNo);
                tFIOperationLogSet = tFIOperationLogDB.executeQuery(sqlbv);
                if(tFIOperationLogDB.mErrors.needDealError())
                {
                    buildError("FICertificateRBProduceMain", "InitInfo", "执行控制语句执行出错");
                    return false;
                }
                if(tFIOperationLogSet.size()>0)
                {
                    buildError("FICertificateRBProduceMain", "InitInfo", "已执行红冲凭证确认操作");
                    return false;
                }

                tFIOperationLogDB = new  FIOperationLogDB();
                tFIOperationLogSet = new FIOperationLogSet();
                tSql = "select * from fioperationlog where fioperationlog.eventtype = '06' and fioperationlog.performstate = '0' and exists (select * from fioperationparameter where fioperationparameter.eventno = fioperationlog.eventno and fioperationparameter.parametertype = 'AppNo' and fioperationparameter.parametervalue = '?AppNo?' )";
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
                sqlbv1.sql(tSql);
                sqlbv1.put("AppNo", AppNo);
                tFIOperationLogSet = tFIOperationLogDB.executeQuery(sqlbv1);
                if(tFIOperationLogDB.mErrors.needDealError())
                {
                    buildError("FICertificateRBProduceMain", "InitInfo", "执行控制语句执行出错");
                    return false;
                }
                if(tFIOperationLogSet.size()>0)
                {
                    buildError("FICertificateRBProduceMain", "InitInfo", "已执行红冲凭证提取操作");
                    return false;
                }



                FIOperationParameterDB tFIOperationParameterDB = new  FIOperationParameterDB();
                FIOperationParameterSet tFIOperationParameterSet = new FIOperationParameterSet();
                tSql = "select * from FIOperationParameter a where a.Eventtype = '05' and a.Parametertype = 'VersionNo'  and exists (select 1 from fioperationlog b where b.eventno = a.eventno and  b.performstate = '0') and exists (select * from FIOperationParameter c where c.Eventno = a.Eventno  and c.Parametertype = 'AppNo' and c.parametervalue = '?AppNo?' )";
                SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
                sqlbv2.sql(tSql);
                sqlbv2.put("AppNo", AppNo);
                tFIOperationParameterSet = tFIOperationParameterDB.executeQuery(sqlbv2);
                if(tFIOperationParameterDB.mErrors.needDealError())
                {
                    buildError("FICertificateRBProduceMain", "InitInfo", "执行红冲数据采集日志查询语句执行出错");
                    return false;
                }
                if(tFIOperationParameterSet.size()<1)
                {
                    buildError("FICertificateRBProduceMain", "InitInfo", "请先执行红冲数据采集");
                    return false;
                }

                VersionNo = tFIOperationParameterSet.get(1).getParameterValue();

                BatchNo = AppNo;

                FICertificateTypeDefDB tFICertificateTypeDefDB = new FICertificateTypeDefDB();
                FICertificateTypeDefSet tFICertificateTypeDefSet = new FICertificateTypeDefSet();
                SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
                sqlbv3.sql("select * from FICertificateTypeDef where CertificateID = '?CertificateID?' and VersionNo = '?VersionNo?'");
                sqlbv3.put("CertificateID", CertificateID);
                sqlbv3.put("VersionNo", VersionNo);
                tFICertificateTypeDefSet = tFICertificateTypeDefDB.executeQuery(sqlbv3);
                if(tFICertificateTypeDefDB.mErrors.needDealError())
                {
                    buildError("FICertificateRBProduceMain","InitInfo", "查询凭证定义出错");
                    return false;
                }
                if(tFICertificateTypeDefSet.size()<1)
                {
                    buildError("FICertificateRBProduceMain","InitInfo", "无该反冲凭证定义信息");
                    return false;
                }
                mFICertificateTypeDefSchema.setSchema(tFICertificateTypeDefSet.get(1));


                tLogInfoDeal = new LogInfoDeal(mGlobalInput.Operator,"06");
                tLogInfoDeal.AddLogParameter("AppNo","红冲申请号码",AppNo);
                tLogInfoDeal.AddLogParameter("VersionNo","规则版本号",VersionNo);
                if(!tLogInfoDeal.SaveLogParameter())
                {
                    buildError("FIReDistillMain","InitInfo",tLogInfoDeal.mErrors.getFirstError());
                    return false;
                }

                tFIDistillDealCert = new FIDistillDealCert();
                if(!tFIDistillDealCert.InitFIDistillDealCert(mFICertificateTypeDefSchema,tLogInfoDeal))
                {
                    this.mErrors.copyAllErrors(tFIDistillDealCert.mErrors);
                    return false;
                }

            }
            catch (Exception ex)
            {
                buildError("FICertificateRBProduceMain","InitInfo","FICertificateRBProduceMain初始化时出现异常，异常信息为：" + ex.getMessage());
                return false;
            }
            return true;
        }

        private boolean DealWithData()
        {
            try
            {
                mInputInfo.clear();
                mInputInfo.add(mGlobalInput);
                mInputInfo.add(BatchNo);

                tLogInfoDeal.WriteLogTxt("开始生成凭证数据"+ enter);
                if (!tFIDistillDealCert.dealProcess(mInputInfo))
                {
                    this.mErrors.copyAllErrors(tFIDistillDealCert.mErrors);
                    return false;
                }

                ExeSQL tExeSQL = new ExeSQL();
                String tSql = "update FIDataFeeBackApp set AppState = '03' where AppNo = '?AppNo?'";
                SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
                sqlbv4.sql(tSql);
                sqlbv4.put("AppNo", AppNo);
                tExeSQL.execUpdateSQL(sqlbv4);
                if(tExeSQL.mErrors.needDealError())
                {
                    buildError("FICertificateRBProduceMain","DealWithData","FICertificateRBProduceMain更新申请状态失败" );
                    tLogInfoDeal.WriteLogTxt("FICertificateRBProduceMain更新申请状态失败"+ enter);
                    return false;
                }

                return true;
            }
            catch (Exception ex)
            {
                buildError("FICertificateRBProduceMain","DealWithData","FICertificateRBProduceMain重新提取数据时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("FICertificateRBProduceMain重新提取数据时出现异常，异常信息为：" + ex.getMessage() + enter);
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
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86";
        VData tVData = new VData();
        tVData.add(tG);
        tVData.add("S-N-01");
        tVData.add("APP000000000000005");

        FICertificateRBProduceMain tFIReDistillMain = new FICertificateRBProduceMain();
        if(!tFIReDistillMain.dealProcess(tVData))
        {
            logger.debug(tFIReDistillMain.mErrors.getFirstError());
        }
    }

}
