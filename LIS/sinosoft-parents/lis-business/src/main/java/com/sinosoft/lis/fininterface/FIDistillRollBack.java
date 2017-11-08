package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.lis.db.FIDataBaseLinkDB;
import com.sinosoft.lis.vschema.FIDataBaseLinkSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.lis.fininterface.utility.PubSubmitForInterface;
import com.sinosoft.utility.SSRS;
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
public class FIDistillRollBack {
private static Logger logger = Logger.getLogger(FIDistillRollBack.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    private GlobalInput mGlobalInput = new GlobalInput();
    private String BatchNo = "";

    StringBuffer logString = new StringBuffer();
    public String Content = "";
    private final String enter = "\r\n"; // 换行符
    public LogInfoDeal tLogInfoDeal ;

    public FIDistillRollBack()
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

        if (!DealWithData()) {
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
            buildError("FIDistillRollBack", "getInputData", "传入登陆信息参数为空");
            return false;
        }
        if (BatchNo == null || BatchNo.equals(""))
        {
            buildError("FIDistillRollBack", "getInputData", "传入批次号码参数为空");
            return false;
        }
        return true;
    }

    private boolean InitInfo()
    {
        try
        {


            String tSql = "select count(1) from fidatatransgather where batchno = '?BatchNo' and (select count(1) from fidatatransgather where batchno = '?BatchNo?')>0 and not exists (select 1 from fidatatransgather where batchno = '?BatchNo?' and VoucherNo is null)";
           SQLwithBindVariables sqlbv=new SQLwithBindVariables();
           sqlbv.sql(tSql);
           sqlbv.put("BatchNo", BatchNo);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(sqlbv);
            if(tExeSQL.mErrors.needDealError())
            {
                buildError("FIDistillRollBack","InitInfo","校验该批次数据对是否已生成凭证执行出错");
                return false;
            }

            int Check = Integer.parseInt(tSSRS.GetText(1, 1));
            if(Check >0)
            {
                buildError("FIDistillRollBack","InitInfo","该批次数据已生成凭证,不允许回滚");
                return false;
            }

            tLogInfoDeal = new LogInfoDeal(mGlobalInput.Operator,"03");
            tLogInfoDeal.AddLogParameter("BatchNo","提数批次号",BatchNo);
            if(!tLogInfoDeal.SaveLogParameter())
            {
                buildError("FIDistillRollBack","InitInfo",tLogInfoDeal.mErrors.getFirstError());
                return false;
            }
            Content = "成功获得采集批处理相关的参数，参数列表如下：" + enter + "操作员 = " + mGlobalInput.Operator + "；批次号码 = " + BatchNo;
            tLogInfoDeal.WriteLogTxt(Content);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    private boolean DealWithData()
    {
        try
        {
            String tSql = "select * from FIDataBaseLink where InterfaceCode in (select distinct DataBaseID from FIAboriginalData where batchno = '?BatchNo?')" ;
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(tSql);
            sqlbv1.put("BatchNo", BatchNo);
            FIDataBaseLinkDB tFIDataBaseLinkDB = new FIDataBaseLinkDB();
            FIDataBaseLinkSet tFIDataBaseLinkSet = new FIDataBaseLinkSet();
            tFIDataBaseLinkSet = tFIDataBaseLinkDB.executeQuery(sqlbv1);
            if(tFIDataBaseLinkDB.mErrors.needDealError())
            {
                buildError("FIDistillRollBack","DealWithData", "数据源定义表查询出错" + tFIDataBaseLinkDB.mErrors.getFirstError());
                tLogInfoDeal.WriteLogTxt("数据源定义表查询出错" + tFIDataBaseLinkDB.mErrors.getFirstError()+ enter);
                return false;
            }


            String tSql01 = "delete from FIDataDistilledInfo where batchno = '?BatchNo?'";
            String tSql02 = "delete from FIAboriginalData where batchno = '?BatchNo?'";
            String tSql03 = "delete from FIAboriginaldatatemp where batchno = '?BatchNo?'";
            String tSql04 = "delete from FIDataTransResult where batchno = '?BatchNo?'";
            String tSql05 = "delete from FIDataTransGather where batchno = '?BatchNo?'";
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(tSql01);
            sqlbv2.put("BatchNo", BatchNo);
            SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
            sqlbv3.sql(tSql02);
            sqlbv3.put("BatchNo", BatchNo);
            SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
            sqlbv4.sql(tSql03);
            sqlbv4.put("BatchNo", BatchNo);
            SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            sqlbv5.sql(tSql04);
            sqlbv5.put("BatchNo", BatchNo);
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql(tSql05);
            sqlbv6.put("BatchNo", BatchNo);


            MMap smap = new MMap();
            VData sInputData = new VData();
            smap.put(sqlbv2, "DELETE");
            smap.put(sqlbv3, "DELETE");
            sInputData.add(smap);

            for (int i =1;i<=tFIDataBaseLinkSet.size();i++)
            {
                FIDataBaseLinkSchema tFIDataBaseLinkSchema = tFIDataBaseLinkSet.get(i);
                PubSubmitForInterface tPubSubmitForInterface = new PubSubmitForInterface();
                if (!tPubSubmitForInterface.submitData(sInputData, tFIDataBaseLinkSchema))
                {
                    this.mErrors.copyAllErrors(tPubSubmitForInterface.mErrors);
                    buildError("FIDistillRollBack","DealWithData", "批次为" + BatchNo + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "删除相关数据失败，错误信息为：" +tPubSubmitForInterface.mErrors.getFirstError());
                    tLogInfoDeal.WriteLogTxt("批次为" + BatchNo + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "删除相关数据失败，错误信息为：" +tPubSubmitForInterface.mErrors.getFirstError() + enter);
                    return false;
                }
            }

            MMap tmap = new MMap();
            VData tInputData = new VData();
            tmap.put(sqlbv2, "DELETE");
            tmap.put(sqlbv3, "DELETE");
            tmap.put(sqlbv4, "DELETE");
            tmap.put(sqlbv5, "DELETE");
            tmap.put(sqlbv6, "DELETE");
            tInputData.add(tmap);
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(tInputData, ""))
            {
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                buildError("FIDistillRollBack","DealWithData", "批次为" + BatchNo + "从本地数据删除相关数据失败，错误信息为：" + tPubSubmit.mErrors.getFirstError());
                tLogInfoDeal.WriteLogTxt("批次为" + BatchNo + "从本地数据删除相关数据失败，异常信息为：" + tPubSubmit.mErrors.getFirstError() + enter);
                return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            buildError("FIDistillRollBack","DealWithData", "批次为" + BatchNo + "从数据源删除相关数据失败，错误信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("批次为" + BatchNo + "从数据源删除相关数据失败，错误信息为：" + ex.getMessage() + enter);
            ex.printStackTrace();
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
        VData vData = new VData();
        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86";
        String Batchno = "00000000000000000104";
        vData.addElement(tG);
        vData.addElement(Batchno);
        FIDistillRollBack tFIDistillRollBack = new FIDistillRollBack();
        tFIDistillRollBack.dealProcess(vData);
        tFIDistillRollBack.mErrors.getFirstError();
    }

}
