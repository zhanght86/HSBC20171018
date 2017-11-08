package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import java.util.Date;

import com.sinosoft.lis.db.FICertificateTypeDefDB;
import com.sinosoft.lis.db.FIPeriodManagementDB;
import com.sinosoft.lis.db.FIRulesVersionDB;
import com.sinosoft.lis.fininterface.checkdata.FIRuleEngineService;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.vschema.FICertificateTypeDefSet;
import com.sinosoft.lis.vschema.FIPeriodManagementSet;
import com.sinosoft.lis.vschema.FIRulesVersionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
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
public class FIDistillMain {
private static Logger logger = Logger.getLogger(FIDistillMain.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 存储处理信息 */
    private VData mInputInfo = new VData();
    /** 存储业务数据类型 */

    private FICertificateTypeDefSet mFICertificateTypeDefSet;
    private FIDistillDealCertificate mFIDistillDealCertificate[];

    /** 业务数据 */
    GlobalInput mGlobalInput = new GlobalInput();
    String StartDate = "";
    String EndDate = "";
    public String BatchNo = "";
    String VersionNo = "";
    String CertificateID = "";

    String CurrentDate = PubFun.getCurrentDate();
    String CurrentTime = PubFun.getCurrentTime();

    private final String enter = "\r\n"; // 换行符
    public LogInfoDeal tLogInfoDeal = null;


    /*****
     * 把相关的日志信息也初始化
     */
    public FIDistillMain()
    {

    }

    public boolean  dealProcess(VData cInputData)
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
        
        if(!ChQUData()){
        	return false;
        }      
        
        tLogInfoDeal.Complete(true);
        return true;

    }
    
    /*****
     * 质量校验数据处理 
     * @return
     */
    private boolean ChQUData(){
        
    	try{
			VData tVData = new VData();
			tVData.addElement(mGlobalInput);
			tVData.addElement(BatchNo);
			tVData.addElement(VersionNo);
			tVData.addElement("01");
			FIRuleEngineService tFIRuleEngineService = new FIRuleEngineService();
			tFIRuleEngineService.dealData(tVData, "quantity");
    	}catch(Exception e){
    		tLogInfoDeal.WriteLogTxt("执行质量校验失败："+  e.getMessage() + enter);
    	}
		
		return true;
    }


    private boolean getInputData(VData cInputData)
    {
        StringBuffer oStringBuffer = new StringBuffer(1024);
        try
        {
            oStringBuffer.append("进入FIDistillMain的getInputData方法" + enter);
            mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
            StartDate = (String) cInputData.get(1);
            EndDate = (String) cInputData.get(2);
            CertificateID = (String) cInputData.get(3);
            if (mGlobalInput == null)
            {
                buildError("FIDistillMain", "getInputData", "传入登陆信息参数为空");
                return false;
            }
            if (StartDate == null || StartDate.equals(""))
            {
                buildError("FIDistillMain", "getInputData", "传入起始日期参数为空");
                return false;
            }
            if (EndDate == null || EndDate.equals(""))
            {
                buildError("FIDistillMain", "getInputData", "传入终止日期参数为空");
                return false;
            }

            return true;
        }
        catch (Exception e)
        {
            buildError("FIDistillMain","getInputData", "获取参数时异常，异常信息为：" + e.getMessage());
            return false;
        }
    }

    private boolean getPeriodManagement(String sDate, String eDate)
    {
            String sql = "  select * from FIPeriodManagement where StartDate<=to_date('?eDate?','yyyy-mm-dd') " +
                         " and EndDate>=to_date('?sDate?','yyyy-mm-dd') and state='1' order by StartDate asc";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(sql);
            sqlbv.put("eDate", eDate);
            sqlbv.put("sDate", sDate);
            FIPeriodManagementSet tFIPeriodManagementSet = new FIPeriodManagementSet();
            FIPeriodManagementDB tFIPeriodManagementDB = new FIPeriodManagementDB();
            tFIPeriodManagementSet = tFIPeriodManagementDB.executeQuery(sqlbv);
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
                    if(i==0){
                        tInfo += "[" + tFIPeriodManagementSet.get(i + 1).getstartdate() + "][" +  tFIPeriodManagementSet.get(i + 1).getenddate() + "]";
                    }else{
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
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(sql);
            sqlbv1.put("eDate", eDate);
            sqlbv1.put("sDate", sDate);

            System.out.println(sql);
            FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
            FIRulesVersionSet tFIRulesVersionSet = tFIRulesVersionDB.executeQuery(sqlbv1);
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
            buildError("FIDistillMain","getRuleVersion", "提数规则版本核对出现异常，异常信息:" + e.getMessage());
            return false;
        }
    }

    private boolean InitInfo()
    {
    	try
        {
            StringBuffer oStringBuffer = new StringBuffer(1024);
            //校验传入时间范围内有是否有开启的会计区间
            if(!getPeriodManagement(StartDate, EndDate))
            {
                return false;
            }

            //校验查询传入时间范围内有多少个版本
            if (!getRuleVersion(StartDate, EndDate)) {
                return false;
            }

            BatchNo = PubFun1.CreateMaxNo("FinBatch", 20);
            tLogInfoDeal = new LogInfoDeal(mGlobalInput.Operator,"01");
            tLogInfoDeal.AddLogParameter("StartDate","开始日期",StartDate);
            tLogInfoDeal.AddLogParameter("EndDate","结束日期",EndDate);
            tLogInfoDeal.AddLogParameter("BatchNo","提数批次号",BatchNo);
            tLogInfoDeal.AddLogParameter("VersionNo","规则版本号",VersionNo);
            if(!tLogInfoDeal.SaveLogParameter())
            {
                buildError("FIDistillMain","InitInfo",tLogInfoDeal.mErrors.getFirstError());
                return false;
            }
            oStringBuffer.append("成功获得采集批处理相关的参数，参数列表如下：" + enter);
            oStringBuffer.append("操作员 = " + mGlobalInput.Operator + ",开始日期 = "
                                 + StartDate + ",结束日期 = " + EndDate + "。并已经通过会计区间与规则版本校验" +
                                 enter);
            tLogInfoDeal.WriteLogTxt(oStringBuffer.toString());

            FICertificateTypeDefDB tFICertificateTypeDefDB = new FICertificateTypeDefDB();
            //判断两种情况，1有凭证编码2所有提数
            if(CertificateID==null||"".equals(CertificateID))
            {
            	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            	sqlbv2.sql("select * from FICertificateTypeDef where AcquisitionType='01' and versionno='?VersionNo?'");
            	sqlbv2.put("VersionNo", VersionNo);
                mFICertificateTypeDefSet = tFICertificateTypeDefDB.executeQuery(sqlbv2);
            }
            else
            {
            	SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            	sqlbv3.sql("select * from FICertificateTypeDef where AcquisitionType='01' and versionno='?VersionNo?' and CertificateID= '?CertificateID?'");
            	sqlbv3.put("VersionNo", VersionNo);
            	sqlbv3.put("CertificateID", CertificateID);
                mFICertificateTypeDefSet = tFICertificateTypeDefDB.executeQuery(sqlbv3);
            }
            if (mFICertificateTypeDefSet.size() > 0)
            {
            	tLogInfoDeal.WriteLogTxt("这次提取得到" + mFICertificateTypeDefSet.size() + "条个凭证类型，开始初试化凭证数据采集类" + enter);
                mFIDistillDealCertificate = new FIDistillDealCertificate[mFICertificateTypeDefSet.size()];
                for (int i = 1; i <= mFICertificateTypeDefSet.size(); i++)
                {
                    mFIDistillDealCertificate[i -1] = new FIDistillDealCertificate(tLogInfoDeal);

                    if (!mFIDistillDealCertificate[i -1].InitInfo(mFICertificateTypeDefSet.get(i)))
                    {
                        this.mErrors.copyAllErrors(mFIDistillDealCertificate[i -1].mErrors);
                        tLogInfoDeal.WriteLogTxt("初始化的凭证失败，错误原因为："+  mFIDistillDealCertificate[i -1].mErrors.getFirstError() + enter);
                    	return false;
                    }
                }
                tLogInfoDeal.WriteLogTxt("初试化凭证数据采集类成功" + enter);
            }
            else
            {
                buildError("FIDistillMain","InitInfo", "未查询到任何凭证采集定义");
                tLogInfoDeal.WriteLogTxt("未查询到任何凭证采集定义" + enter);
                return false;
            }
            return true;

        }
        catch (Exception ex)
        {
            buildError("FIDistillMain","InitInfo","FIDistillMain初始化时出现异常，异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("FIDistillMain初始化时出现异常，异常信息为：" + ex.getMessage() + enter);
            return false;
        }
    }


    private boolean DealWithData()
    {
        try {


            FDate chgdate = new FDate();
            Date dbdate = chgdate.getDate(StartDate);
            Date dedate = chgdate.getDate(EndDate);

            while (dbdate.compareTo(dedate) <= 0)
            {
                mInputInfo.clear();
                mInputInfo.add(mGlobalInput);
                mInputInfo.add(chgdate.getString(dbdate));
                mInputInfo.add(chgdate.getString(dedate));
                mInputInfo.add(BatchNo);
                mInputInfo.add(VersionNo);
                for (int i = 0; i < mFIDistillDealCertificate.length; i++)
                {
                    tLogInfoDeal.WriteLogTxt("开始提取日期为" + chgdate.getString(dbdate) + "凭证类型为" +  mFICertificateTypeDefSet.get(i+1).getCertificateID()  + "的数据" + enter);
                    if (!mFIDistillDealCertificate[i].dealProcess(mInputInfo))
                    {
                        this.mErrors.copyAllErrors(mFIDistillDealCertificate[i].mErrors);
                        tLogInfoDeal.WriteLogTxt("提取凭证数据失败，错误原因为："+  mFIDistillDealCertificate[i].mErrors.getFirstError() + enter);
                    	return false;
                    }
                }
                dbdate = PubFun.calDate(dbdate, 1, "D", null);
            }
        }
        catch (Exception ex)
        {
            buildError("FIDistillMain","DealWithData","FIDistillMain循环提取凭证数据时出现异常，异常信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("FIDistillMain循环提取凭证数据时出现异常，异常信息为：" + ex.getMessage() + enter);
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

    public String getBatchNo()
    {
        return BatchNo;
    }

    public String getVersionNo()
    {
        return VersionNo;
    }

    public static void main(String[] args)
    {
        String CertificateIDdd = "O-N-01";
        VData vData = new VData();
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86";
        String bdate = "2006-11-23";
        String edate = "2006-11-23";
        vData.addElement(tG);
        vData.addElement(bdate);
        vData.addElement(edate);
        vData.addElement(CertificateIDdd);
        FIDistillMain tFInDealEngine = new FIDistillMain();
        tFInDealEngine.dealProcess(vData);

    }

}
