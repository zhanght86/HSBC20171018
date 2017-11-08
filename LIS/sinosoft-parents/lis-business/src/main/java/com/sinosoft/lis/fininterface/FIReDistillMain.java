package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.vschema.FIRulesVersionSet;
import com.sinosoft.lis.db.FIRulesVersionDB;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.db.FIAboriginalDataDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.lis.db.FIDataBaseLinkDB;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.SSRS;
import com.sinosoft.lis.db.FIOperationLogDB;
import com.sinosoft.lis.vschema.FIOperationLogSet;
import com.sinosoft.lis.db.FICostDataAcquisitionDefDB;
import com.sinosoft.lis.fininterface.utility.PubSubmitForInterface;
import com.sinosoft.lis.schema.FICertificateTypeDefSchema;
import com.sinosoft.lis.db.FICertificateTypeDefDB;
import com.sinosoft.lis.vschema.FICertificateTypeDefSet;



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
public class FIReDistillMain
{
private static Logger logger = Logger.getLogger(FIReDistillMain.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 存储处理信息 */
    private VData mInputInfo = new VData();

    /** 业务数据 */
    private FIAboriginalDataSet mFIAboriginalDataSet = new FIAboriginalDataSet();
    private FIDataBaseLinkSchema mFIDataBaseLinkSchema = new FIDataBaseLinkSchema();
    private FICertificateTypeDefSchema mFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
    private FIDistillDealCertificateForHC tFIDistillDealCertificateForHC;
    private String DataSourceType = "";
    private String StartDate = "";
    private String CertificateID = "";
    private String BusinessNo = "";
    private String AppNo = "";
    private String BatchNo = "";
    private GlobalInput mGlobalInput = new GlobalInput();
    private String VersionNo = "";

    private final String enter = "\r\n"; // 换行符
    public LogInfoDeal tLogInfoDeal;

    public FIReDistillMain() {}

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
        CertificateID = (String) cInputData.get(1);
        BusinessNo = (String) cInputData.get(2);
        AppNo = (String) cInputData.get(3);

        if (mGlobalInput == null)
        {
            buildError("FIReDistillMain", "getInputData", "传入登陆信息参数为空");
            return false;
        }
        if (CertificateID == null && CertificateID.equals(""))
        {
            buildError("FIReDistillMain", "getInputData", "传入登陆凭证类型参数为空");
            return false;
        }
        if (BusinessNo == null && BusinessNo.equals("")) {
            buildError("FIReDistillMain", "getInputData", "传入业务关联号码参数为空");
            return false;
        }
        if (AppNo == null && AppNo.equals(""))
        {
            buildError("FIReDistillMain", "getInputData", "传入红冲申请号码参数为空");
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
                buildError("FIReDistillMain", "InitInfo", "执行控制语句执行出错");
                return false;
            }
            if(tFIOperationLogSet.size()>0)
            {
                buildError("FIReDistillMain", "InitInfo", "已执行红冲凭证确认操作");
                return false;
            }

            tFIOperationLogDB = new  FIOperationLogDB();
            tFIOperationLogSet = new FIOperationLogSet();
            tSql = "select * from fioperationlog where fioperationlog.eventtype = '05' and fioperationlog.performstate = '0' and exists (select * from fioperationparameter where fioperationparameter.eventno = fioperationlog.eventno and fioperationparameter.parametertype = 'AppNo' and fioperationparameter.parametervalue = '?AppNo?' )";
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
                buildError("FICertificateRBProduceMain", "InitInfo", "已执行红冲数据采集操作");
                return false;
            }

            //获得该业务最近的正项业务采集数据（可能红冲多次）
            FIAboriginalDataDB tFIAboriginalDataDB = new FIAboriginalDataDB();
            tSql = "select * from  fiaboriginaldata c where c.batchno in (select a.batchno from fiaboriginaldata a where a.aserialno = (select  max(b.aserialno)  from fiaboriginaldata b where b.certificateid = '?CertificateID?' and b.businessno = '?BusinessNo?' and b.sumactumoney >0)) and  c.certificateid = '?CertificateID?' and c.businessno = '?BusinessNo?'";
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(tSql);
            sqlbv2.put("CertificateID", CertificateID);
            sqlbv2.put("BusinessNo", BusinessNo);
            mFIAboriginalDataSet = tFIAboriginalDataDB.executeQuery(sqlbv2);
            if(tFIAboriginalDataDB.mErrors.needDealError())
            {
                buildError("FIReDistillMain", "InitInfo", "获得该业务最近的正项业务采集数据查询执行出错");
                return false;
            }
            if(mFIAboriginalDataSet.size()<1)
            {
                buildError("FIReDistillMain", "InitInfo", "该红冲业务不存在任何正项业务采集数据");
                return false;
            }

           String batchno = mFIAboriginalDataSet.get(1).getBatchNo();
           //校验获得该业务最近的正项业务采集数据是否生成凭证
           ExeSQL tExeSQL = new ExeSQL();
           SSRS tSSRS = new SSRS();
           SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
           sqlbv3.sql("select count(1) from fidatatransgather a where a.batchno = '?batchno?' and  (select count(1) from fidatatransgather b where b.batchno = '?batchno?') >0  and not exists (select * from fidatatransgather c where c.batchno = '?batchno?' and c.voucherno is null)");
           sqlbv3.put("batchno", batchno);
           tSSRS = tExeSQL.execSQL(sqlbv3);
           int checkno = Integer.parseInt(tSSRS.GetText(1, 1));
           if(checkno<1)
           {
              buildError("FIReDistillMain", "InitInfo", "获得该业务最近的正项业务采集数据还未生成凭证");
              return false;
           }

           //获得该业务最早的正项业务采集数据（间接相应对应数据的版本规则）
            tExeSQL = new ExeSQL();
            tSSRS = new SSRS();
            tSql = "select a.accountdate from fiaboriginaldata a where a.aserialno = (select  min(b.aserialno)  from fiaboriginaldata b where b.certificateid = '?CertificateID?' and b.businessno = '?BusinessNo?' )";
            SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
            sqlbv4.sql(tSql);
            sqlbv4.put("CertificateID", CertificateID);
            sqlbv4.put("BusinessNo", BusinessNo);
            tSSRS = tExeSQL.execSQL(sqlbv4);
            if(tSSRS.getMaxRow()<1)
            {
                buildError("FIReDistillMain", "InitInfo", "未获得该最早生成凭证的数据采集数据，无法获取相应版本信息");
                return false;
            }
            StartDate = tSSRS.GetText(1,1);

            if (!getRuleVersion(StartDate,StartDate)) {
                return false;
            }

            BatchNo = AppNo;

            FIDataBaseLinkDB tFIDataBaseLinkDB = new FIDataBaseLinkDB();
            tFIDataBaseLinkDB.setInterfaceCode(mFIAboriginalDataSet.get(1).getDataBaseID());
            if(!tFIDataBaseLinkDB.getInfo())
            {
                buildError("FIReDistillMain","InitInfo", "无红冲数据来源定义信息");
                return false;
            }
            if(tFIDataBaseLinkDB.mErrors.needDealError())
            {
                buildError("FIReDistillMain","InitInfo", "查询红冲数据来源出错");
                return false;
            }
            mFIDataBaseLinkSchema.setSchema(tFIDataBaseLinkDB.getSchema());

            FICertificateTypeDefDB tFICertificateTypeDefDB = new FICertificateTypeDefDB();
            FICertificateTypeDefSet tFICertificateTypeDefSet = new FICertificateTypeDefSet();
            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            sqlbv5.sql("select * from FICertificateTypeDef where CertificateID = '?CertificateID?' and VersionNo = '?VersionNo?'");
            sqlbv5.put("CertificateID", CertificateID);
            sqlbv5.put("VersionNo", VersionNo);
            tFICertificateTypeDefSet = tFICertificateTypeDefDB.executeQuery(sqlbv5);
            if(tFICertificateTypeDefDB.mErrors.needDealError())
            {
                buildError("FIReDistillMain","InitInfo", "查询凭证定义出错");
                return false;
            }
            if(tFICertificateTypeDefSet.size()<1)
            {
                buildError("FIReDistillMain","InitInfo", "无该反冲凭证定义信息");
                return false;
            }
            mFICertificateTypeDefSchema.setSchema(tFICertificateTypeDefSet.get(1));

            FICostDataAcquisitionDefDB tFICostDataAcquisitionDefDB = new FICostDataAcquisitionDefDB();
            tFICostDataAcquisitionDefDB.setAcquisitionID(mFIAboriginalDataSet.get(1).getAcquisitionID());
            tFICostDataAcquisitionDefDB.setVersionNo(VersionNo);
            if(!tFICostDataAcquisitionDefDB.getInfo())
            {
                buildError("FIReDistillMain","InitInfo", "未查询红冲数据采集定义");
                return false;
            }

            if(tFICostDataAcquisitionDefDB.mErrors.needDealError())
            {
                buildError("FIReDistillMain","InitInfo", "查询红冲数据采集定义出错");
                return false;
            }
            DataSourceType =  tFICostDataAcquisitionDefDB.getDataSourceType();

            tLogInfoDeal = new LogInfoDeal(mGlobalInput.Operator,"05");
            tLogInfoDeal.AddLogParameter("AppNo","红冲申请号码",AppNo);
            tLogInfoDeal.AddLogParameter("VersionNo","规则版本号",VersionNo);
            if(!tLogInfoDeal.SaveLogParameter())
            {
                buildError("FIReDistillMain","InitInfo",tLogInfoDeal.mErrors.getFirstError());
                return false;
            }

            tFIDistillDealCertificateForHC = new FIDistillDealCertificateForHC(tLogInfoDeal);
            if(!tFIDistillDealCertificateForHC.InitInfo(mFICertificateTypeDefSchema))
            {
                this.mErrors.copyAllErrors(tFIDistillDealCertificateForHC.mErrors);
                return false;
            }


        }
        catch (Exception ex)
        {
            buildError("FIReDistillMain","InitInfo","FIReDistillMain初始化时出现异常，异常信息为：" + ex.getMessage());
            return false;
        }
        return true;
    }


    private boolean getRuleVersion(String sDate, String eDate)
    {
        try
        {
            String sql = " select * from FIRulesVersion where  versionstate ='01' and StartDate<=to_date('?eDate?','yyyy-mm-dd') "
                         + " and EndDate>to_date('?sDate?','yyyy-mm-dd') " + " order by startdate asc ";
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql(sql);
            sqlbv6.put("eDate", eDate);
            sqlbv6.put("sDate", sDate);

            FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
            FIRulesVersionSet tFIRulesVersionSet = tFIRulesVersionDB.executeQuery(sqlbv6);
            if (tFIRulesVersionSet.size() == 0)
            {
                buildError("FIDistillMain","getRuleVersion", "您输入的时间区间内提数规则无版本，请重新输入起始日期！");
                return false;
            }
            else if (tFIRulesVersionSet.size() != 1)
            {
                String tInfo = "您输入的时间区间内查询到" + tFIRulesVersionSet.size() + "个提数有效版本！版本分别是：";
                for (int i = 0; i < tFIRulesVersionSet.size(); i++) {
                    if (i == 0) {
                        tInfo += "[" + tFIRulesVersionSet.get(i + 1).getStartDate() + "][" +  tFIRulesVersionSet.get(i + 1).getEndDate()+ "]";
                    } else {
                        tInfo += "," +  "[" + tFIRulesVersionSet.get(i + 1).getStartDate() + "][" +  tFIRulesVersionSet.get(i + 1).getEndDate()+ "]";
                    }
                }
                buildError("FIDistillMain","getRuleVersion", tInfo);
                return false;
            }
            VersionNo = tFIRulesVersionSet.get(1).getVersionNo();
            return true;
        }
        catch (Exception e)
        {
            buildError("FIReDistillMain","getRuleVersion", "提数规则版本核对出现异常，异常信息:" + e.getMessage());
            return false;
        }
    }



    //对采集数据进行处理
    private boolean DealOldData()
    {
        try
        {
            FIAboriginalDataSet tFIAboriginalDataSet = new FIAboriginalDataSet();
            for (int i = 1; i <= mFIAboriginalDataSet.size(); i++)
            {
                FIAboriginalDataSchema tFIAboriginalDataSchema = new FIAboriginalDataSchema();
                tFIAboriginalDataSchema.setSchema(mFIAboriginalDataSet.get(i));

                //业务接口表插入负记录
                String ASerialNo = PubFun1.CreateMaxNo("ABORIGINALNO", 20);
                tFIAboriginalDataSchema.setASerialNo(ASerialNo);
                tFIAboriginalDataSchema.setBatchNo(BatchNo);
                tFIAboriginalDataSchema.setAccountDate(PubFun.getCurrentDate());
                tFIAboriginalDataSchema.setDataState("01");
                tFIAboriginalDataSchema.setSumActuMoney(-tFIAboriginalDataSchema.getSumActuMoney());
                tFIAboriginalDataSet.add(tFIAboriginalDataSchema);
            }

            MMap tmap = new MMap();
            VData tInputData = new VData();
            tmap.put(tFIAboriginalDataSet, "INSERT");
            tInputData.add(tmap);

            PubSubmit tPubSubmit = new PubSubmit();
            if(DataSourceType.equals("01")){
            	
            	if (!tPubSubmit.submitData(tInputData, ""))
            	{
            		this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            		buildError("FIReDistillMain","DealOldData", "存在本地数据库时出错，提示信息为：" +tPubSubmit.mErrors.getFirstError());
            		tLogInfoDeal.WriteLogTxt("存在本地数据库时出错，提示信息为：" +tPubSubmit.mErrors.getFirstError() + enter);
            		return false;
            	}
            }
            if(DataSourceType.equals("02"))
            {
                PubSubmitForInterface tPubSubmitForInterface = new PubSubmitForInterface();
                if (!tPubSubmitForInterface.submitData(tInputData, mFIDataBaseLinkSchema))
                {
                    this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                    buildError("FIReDistillMain","DealOldData",  "在数据源" + mFIDataBaseLinkSchema.getInterfaceCode() + "保存在外地数据库时出错，提示信息为：" +tPubSubmitForInterface.mErrors.getFirstError());
                    tLogInfoDeal.WriteLogTxt("数据采集编码为" + "在数据源" + mFIDataBaseLinkSchema.getInterfaceCode() + "保存在外地数据库时出错，提示信息为：" +tPubSubmitForInterface.mErrors.getFirstError() + enter);
                    return false;
                }

            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FIReDistillMain","DealOldData","FIReDistillMain处理负项数据时出现异常，异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("FIReDistillMain处理负项数据时出现异常，异常信息为：" + ex.getMessage() + enter);
            return false;
        }
    }



    private boolean DealWithData()
    {
        try
        {

            tLogInfoDeal.WriteLogTxt("开始重提数据"+ enter);

            mInputInfo.clear();
            mInputInfo.add(mGlobalInput);
            mInputInfo.add(BusinessNo);
            mInputInfo.add(BatchNo);
            mInputInfo.add(VersionNo);

            if(!tFIDistillDealCertificateForHC.dealProcess(mInputInfo))
            {
                this.mErrors.copyAllErrors(tFIDistillDealCertificateForHC.mErrors);
                return false;
            }

            tLogInfoDeal.WriteLogTxt("开始处理旧数据"+ enter);
            if (!DealOldData())
            {
                return false;
            }

            ExeSQL tExeSQL = new ExeSQL();
            String tSql = "update FIDataFeeBackApp set AppState = '02' where AppNo = '?AppNo?'";
            SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
            sqlbv7.sql(tSql);
            sqlbv7.put("AppNo", AppNo);
            tExeSQL.execUpdateSQL(sqlbv7);
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
            buildError("FIReDistillMain","DealWithData","FIReDistillMain重新提取数据时出现异常，异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("FIReDistillMain重新提取数据时出现异常，异常信息为：" + ex.getMessage() + enter);
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
        tVData.add("N0000000000000000033");
        tVData.add("APP000000000000007");

        FIReDistillMain tFIReDistillMain = new FIReDistillMain();
        if(!tFIReDistillMain.dealProcess(tVData))
        {
            logger.debug(tFIReDistillMain.mErrors.getFirstError());
        }

    }

}
