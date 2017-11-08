package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.vschema.FICertificateTypeDefSet;
import com.sinosoft.lis.db.FICertificateTypeDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.vschema.FIRulesVersionSet;
import com.sinosoft.lis.db.FIRulesVersionDB;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.lis.db.FIPeriodManagementDB;
import com.sinosoft.lis.vschema.FIPeriodManagementSet;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
//import com.sinosoft.lis.db.FIAssociatedItemDefDB;
//import com.sinosoft.lis.vschema.FIAssociatedItemDefSet;
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
public class FiDistillForCertMain
{
private static Logger logger = Logger.getLogger(FiDistillForCertMain.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 存储处理信息 */
    private VData mInputInfo = new VData();
    /** 存储业务数据类型 */

    private FICertificateTypeDefSet mFICertificateTypeDefSet;
    private FIDistillDealCert mFIDistillDealCert[];


    /** 业务数据 */
    GlobalInput mGlobalInput = new GlobalInput();
    String StartDate = "";
    String EndDate = "";
    String BatchNo = "";
    String VersionNo = "";

    private final String enter = "\r\n"; // 换行符
    public LogInfoDeal tLogInfoDeal ;


    public FiDistillForCertMain()
    {
    }

    public boolean dealProcess(VData cInputData)
    {
        if (!getInputData(cInputData))
        {
            return false;
        }
        if (!InitInfo()) {
            return false;
        }
        if(!DealWithData())
        {
            return false;
        }
        tLogInfoDeal.Complete(true);
        return true;

    }

    private boolean getInputData(VData cInputData)
    {
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        BatchNo = (String) cInputData.get(1);

        if (mGlobalInput == null)
        {
            buildError("FICertificateMain", "getInputData", "传入登陆信息参数为空");
            return false;
        }
        if (BatchNo == null || "".equals(BatchNo))
        {
            buildError("FICertificateMain", "getInputData", "传入处理批次参数为空");
            return false;
        }
        return true;
    }

    private boolean InitInfo()
    {
        try
        {
            StringBuffer oStringBuffer = new StringBuffer(1024);

            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql("select min(AccountDate),max(AccountDate) from FIAboriginalData where batchno='?BatchNo?'");
            sqlbv.put("BatchNo", BatchNo);
            tSSRS = tExeSQL.execSQL(sqlbv);
            
            if(tExeSQL.mErrors.needDealError())
            {
                buildError("FiDistillForCertMain","InitInfo","查询该批次数据对应时间区间出错");
                return false;
            }

            StartDate = tSSRS.GetText(1, 1);
            EndDate = tSSRS.GetText(1, 2);

            if(StartDate == null || EndDate == null || StartDate.equals("") || EndDate.equals(""))
            {
                buildError("FiDistillForCertMain","InitInfo","未查询到该批次数据对应时间区间，该批次数据可能为空");
                return false;
            }

            //校验该批数据账务日期是否对应开启的会计区间
            if(!getPeriodManagement(StartDate, EndDate))
            {
                return false;
            }

            //校验该批数据是否对应多个账务规则版本，并获得规则版本号码
            if (!getRuleVersion(StartDate, EndDate)) {
                return false;
            }

            tLogInfoDeal = new LogInfoDeal(mGlobalInput.Operator,"02");
            tLogInfoDeal.AddLogParameter("BatchNo","提数批次号",BatchNo);

            if(!tLogInfoDeal.SaveLogParameter())
            {
                buildError("FIDistillMain","InitInfo",tLogInfoDeal.mErrors.getFirstError());
                return false;
            }
            oStringBuffer.append("成功获得生成凭证处理相关的参数，参数列表如下：" + enter);
            oStringBuffer.append("操作员 = " + mGlobalInput.Operator + ",数据批次号 = " + BatchNo + enter);
            tLogInfoDeal.WriteLogTxt(oStringBuffer.toString());


            FICertificateTypeDefDB tFICertificateTypeDefDB = new FICertificateTypeDefDB();
            String tSQL = "select * from FICertificateTypeDef where  versionno= '?VersionNo?' and CertificateID in (select distinct CertificateID from FIAboriginalData where BatchNo = '?BatchNo?' )";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(tSQL);
            sqlbv1.put("VersionNo", VersionNo);
            sqlbv1.put("BatchNo", BatchNo);

            mFICertificateTypeDefSet = tFICertificateTypeDefDB.executeQuery(sqlbv1);
            if(tFICertificateTypeDefDB.mErrors.needDealError())
            {
                buildError("FiDistillForCertMain","InitInfo", "该批次数据包含凭证类型查询失败");
                tLogInfoDeal.WriteLogTxt("专项数据包含凭证类型查询失败" + enter);
                return false;
            }
            if(mFICertificateTypeDefSet.size() <= 0)
            {
                buildError("FiDistillForCertMain","InitInfo", "该批次数据包含凭证类型");
                tLogInfoDeal.WriteLogTxt("专项数据包含凭证类型查询失败" + enter);
                return false;
            }

            mFIDistillDealCert = new FIDistillDealCert[mFICertificateTypeDefSet.size()];
            for (int i = 1; i <= mFICertificateTypeDefSet.size(); i++)
            {
                mFIDistillDealCert[i -1] = new FIDistillDealCert();
                if (!mFIDistillDealCert[i -1].InitFIDistillDealCert(mFICertificateTypeDefSet.get(i),tLogInfoDeal))
                {
                    this.mErrors.copyAllErrors(mFIDistillDealCert[i -1].mErrors);
                    return false;
                }
            }
            return true;

        }
        catch (Exception ex)
        {
            buildError("FiDistillForCertMain","InitInfo","FiDistillForCertMain初始化时出现异常，异常信息为：" + ex.getMessage());
            return false;
        }
    }


    private boolean getPeriodManagement(String sDate, String eDate)
    {
            String sql = "  select * from FIPeriodManagement where StartDate<=to_date('?eDate?','yyyy-mm-dd') " +
                         " and EndDate>=to_date('?sDate?','yyyy-mm-dd') and state='1' order by StartDate asc";
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(sql);
            sqlbv2.put("eDate", eDate);
            sqlbv2.put("sDate", sDate);
            FIPeriodManagementSet tFIPeriodManagementSet = new FIPeriodManagementSet();
            FIPeriodManagementDB tFIPeriodManagementDB = new FIPeriodManagementDB();
            tFIPeriodManagementSet = tFIPeriodManagementDB.executeQuery(sqlbv2);
            if (tFIPeriodManagementSet.size() ==0)
            {
                buildError("FIDistillMain","getPeriodManagement", "您输入的时间区间所对应的会计区间尚未开启，请重新输入起始日期！");
                return false;
            }
            else if(tFIPeriodManagementSet.size() !=1)
            {
                String tInfo = "您输入的时间区间内查询到"+tFIPeriodManagementSet.size()+"个有效会计区间！分别是";
                for(int i =0;i<tFIPeriodManagementSet.size();i++)
                {
                    if(i==0)
                    {
                        tInfo += "[" + tFIPeriodManagementSet.get(i + 1).getstartdate() + "][" +  tFIPeriodManagementSet.get(i + 1).getenddate() + "]";
                    }
                    else
                    {
                        tInfo += "," + "[" + tFIPeriodManagementSet.get(i + 1).getstartdate() + "][" +  tFIPeriodManagementSet.get(i + 1).getenddate() + "]";
                    }
                }
                buildError("FIDistillMain","getPeriodManagement", tInfo);
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
            SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            sqlbv3.sql(sql);
            sqlbv3.put("eDate", eDate);
            sqlbv3.put("sDate", sDate);

            FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
            FIRulesVersionSet tFIRulesVersionSet = tFIRulesVersionDB.executeQuery(sqlbv3);
            if (tFIRulesVersionSet.size() == 0)
            {
                buildError("FiDistillForCertMain","getRuleVersion", "您输入的时间区间内提数规则无版本，请重新输入起始日期！");
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
                buildError("FiDistillForCertMain","getRuleVersion", tInfo);
                return false;
            }
            VersionNo = tFIRulesVersionSet.get(1).getVersionNo();
            return true;
        }
        catch (Exception e)
        {
            buildError("FiDistillForCertMain","getRuleVersion", "提数规则版本核对出现异常，异常信息:" + e.getMessage());
            return false;
        }
    }


    private boolean DealWithData()
    {
        try
        {
            tLogInfoDeal.WriteLogTxt("开始执行生成凭证数据功能" + enter);
            mInputInfo.clear();
            mInputInfo.add(mGlobalInput);
            mInputInfo.add(BatchNo);

            for (int i = 0; i < mFIDistillDealCert.length; i++)
            {
                if (!mFIDistillDealCert[i].dealProcess(mInputInfo))
                {
                    this.mErrors.copyAllErrors(mFIDistillDealCert[i].mErrors);
                    return false;
                }
            }

        }
        catch (Exception ex)
        {
            buildError("FiDistillForCertMain","DealWithData", "执行生成凭证功能出现异常，信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("执行生成凭证功能出现异常，信息为：" + ex.getMessage() + enter);
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

    public static void main(String[] args)
    {
         VData tVData  = new VData();
         String BatchNo = "00000000000000000132";
         GlobalInput tG = new GlobalInput();
         tG.Operator = "001";
         tG.ManageCom = "86";
         tVData.add(tG);
         tVData.add(BatchNo);
         FiDistillForCertMain tFiDistillForCertMain = new FiDistillForCertMain();
         tFiDistillForCertMain.dealProcess(tVData);
    }

}
