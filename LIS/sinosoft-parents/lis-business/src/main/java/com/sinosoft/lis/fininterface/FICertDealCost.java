package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.db.FIAboriginalDataDB;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.vschema.FIDataTransResultSet;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.FIDataTransResultSchema;
import com.sinosoft.lis.vschema.FIAboriginalDataTempSet;
import com.sinosoft.utility.Reflections;
import com.sinosoft.lis.schema.FIAboriginalDataTempSchema;
import com.sinosoft.lis.vschema.FIDataDealErrSet;
import com.sinosoft.lis.schema.FICostTypeDefSchema;
import com.sinosoft.lis.schema.FIDataDealErrSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;

import java.sql.SQLWarning;
import java.util.Calendar;


public class FICertDealCost
{
private static Logger logger = Logger.getLogger(FICertDealCost.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    public FICostTypeDefSchema mFICostTypeDefSchema = new FICostTypeDefSchema();
    public FIDataTransResultSet mFIDataTransResultSet = new FIDataTransResultSet();

    /** 存储数据关联科目处理类 */
    private FIAccountItemType mFIAccountItemType = new FIAccountItemType();

    private GlobalInput mGlobalInput = new GlobalInput();
    private String BatchNo = "";

    /** 数据批次处理限制数*/
    private int mMaxDealNUm = 500;
    private final String enter = "\r\n"; // 换行符
    public LogInfoDeal tLogInfoDeal = null;


    public FICertDealCost() {
    }

    public boolean InitFICertDealCost(FICostTypeDefSchema tFICostTypeDefSchema,LogInfoDeal ttLogInfoDeal)
    {
        try
        {
            mFICostTypeDefSchema = tFICostTypeDefSchema;
            tLogInfoDeal = ttLogInfoDeal;
            if(!mFIAccountItemType.InitItemDefInfo(tFICostTypeDefSchema,tLogInfoDeal))
            {
                this.mErrors.copyAllErrors(mFIAccountItemType.mErrors);
                return false;
            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FICertDealCost","InitFICertDealCost", "费用" + tFICostTypeDefSchema.getCostID() + "的账务规则处理类初始化异常，错误为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("费用" +tFICostTypeDefSchema.getCostID() + "的账务规则处理类初始化异常，错误为："  + ex.getMessage() + enter);
            return false;
        }

    }

    public boolean dealProcess(VData cInputData)
    {
        if (!getInputData(cInputData)) {
            return false;
        }

        if (!DealWithData()) {
            return false;
        }

        return true;
    }


    private boolean getInputData(VData cInputData)
    {
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        BatchNo = (String) cInputData.get(1);

        if (mGlobalInput == null)
        {
            buildError("FICertDealCost", "getInputData", "传入登陆信息参数为空");
            return false;
        }
        if (BatchNo == null || "".equals(BatchNo))
        {
            buildError("FICertDealCost", "getInputData", "传入处理批次参数为空");
            return false;
        }

        return true;
    }

    private boolean DealWithData()
    {
        try
        {
            String sql = "select * from FIAboriginalData where costid = '?costid?' and batchno = '" +
                        BatchNo + "' and DataState ='01'  and not exists (select 1 from FIAboriginalDataTemp where FIAboriginalDataTemp.ASerialNo = FIAboriginalData.ASerialNo)";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();

            String AddSql = "";
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            AddSql = "select * from (" + sql + ") g where rownum <=?mMaxDealNUm?";
            }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
            	 AddSql = "select * from (" + sql + ") g limit 0,?mMaxDealNUm? ";
            }

            sqlbv.sql(AddSql);
            sqlbv.put("costid", mFICostTypeDefSchema.getCostID());
            sqlbv.put("mMaxDealNUm", mMaxDealNUm);
            boolean tFlag = true;
            while(tFlag)
            {

                FIAboriginalDataSet tFIAboriginalDataSet = new FIAboriginalDataSet();
                FIAboriginalDataDB tFIAboriginalDataDB = new FIAboriginalDataDB();

                tLogInfoDeal.WriteLogTxt("执行SQL为：" + AddSql + enter);
                long tTime1 = Calendar.getInstance().getTimeInMillis();
                tFIAboriginalDataSet = tFIAboriginalDataDB.executeQuery(sqlbv);
                long tTime2 = Calendar.getInstance().getTimeInMillis();
                tLogInfoDeal.WriteLogTxt("该SQL执行消耗的时间为：" + String.valueOf((tTime2 - tTime1) / 1000) + "秒" + enter);

                if(tFIAboriginalDataDB.mErrors.needDealError())
                {
                    buildError("FICertDealCost","DealWithData", "费用" + mFICostTypeDefSchema.getCostID() + "查询该批次接口数据时SQL执行时出现错误，错误信息为：" + tFIAboriginalDataDB.mErrors.getFirstError());
                    tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() + "查询该批次接口数据时SQL执行时出现错误，错误信息为：" + tFIAboriginalDataDB.mErrors.getFirstError() + enter);
                    tFlag = false;
                    return false;
                }

                if(tFIAboriginalDataSet==null || tFIAboriginalDataSet.size()==0)
                {
                    tFlag = false;
                }
                else
                {
                    if(!ForDealData(tFIAboriginalDataSet))
                    {
                        tFlag = false;
                        return false;
                    }
                }
            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FICertDealCost","DealWithData", "费用" + mFICostTypeDefSchema.getCostID() + "处理类执行时出现异常，错误信息为：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() + "处理类执行时出现异常，错误信息为："  + ex.getMessage() + enter);
            return false;
        }
    }


    private boolean ForDealData(FIAboriginalDataSet tFIAboriginalDataSet)
    {
        try
        {
            FIAboriginalDataTempSet tFIAboriginalDataTempSet =  new FIAboriginalDataTempSet();
            FIDataTransResultSet tFIDataTransResultSet = new FIDataTransResultSet();
            Reflections tReflections = new Reflections();

            for (int i = 0; i < tFIAboriginalDataSet.size(); i++)
            {
                FIAboriginalDataSchema tFIAboriginalDataSchema = new FIAboriginalDataSchema();
                FIAboriginalDataTempSchema tFIAboriginalDataTempSchema = new FIAboriginalDataTempSchema();
                tFIAboriginalDataSchema = tFIAboriginalDataSet.get(i + 1);

                try
                {
                    FIDataTransResultSchema tFIDataTransResultSchema = mFIAccountItemType.DealInfo(tFIAboriginalDataSchema);

                    if (tFIDataTransResultSchema == null)
                    {
                        buildError("FICertDealCost","ForDealData", "费用" + mFICostTypeDefSchema.getCostID() +  ", 业务号码为" + tFIAboriginalDataSchema.getBusinessNo() + "数据生成会计分录数据错误");
                        tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() +  ", 业务号码为" + tFIAboriginalDataSchema.getBusinessNo() + "数据生成会计分录数据错误" + enter);
                        DealError(mFIAccountItemType.ErrInfo,mFIAccountItemType.ErrType,tFIAboriginalDataSchema.getASerialNo(), tFIAboriginalDataSchema.getAccountDate());
                        return false;
                    }
                    tReflections.transFields(tFIAboriginalDataTempSchema,tFIAboriginalDataSchema);
                    tFIAboriginalDataTempSet.add(tFIAboriginalDataTempSchema);
                    tFIDataTransResultSet.add(tFIDataTransResultSchema);
                }
                catch (Exception ex)
                {
                    buildError("FICertDealCost","ForDealData", "费用" + mFICostTypeDefSchema.getCostID() +  ", 业务号码为" + tFIAboriginalDataSchema.getBusinessNo() + "数据生成会计分录数据错误" + ex.getMessage() );
                    tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() +  ", 业务号码为" + tFIAboriginalDataSchema.getBusinessNo() + "数据生成会计分录数据错误"  + ex.getMessage() + enter);
                    DealError(mFIAccountItemType.ErrInfo,mFIAccountItemType.ErrType,tFIAboriginalDataSchema.getASerialNo(), tFIAboriginalDataSchema.getAccountDate());
                    return false;
                }

            }

            MMap map = new MMap();
            map.put(tFIAboriginalDataTempSet, "INSERT");
            map.put(tFIDataTransResultSet, "INSERT");
            VData tInputInfo = new VData();
            tInputInfo.add(map);
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(tInputInfo, ""))
            {
                buildError("FICertDealCost","ForDealData", "费用" + mFICostTypeDefSchema.getCostID() + "提交数据库凭证数据失败,错误是：" + tPubSubmit.mErrors.getFirstError() );
                tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() + "提交数据库凭证数据失败,错误是：" + tPubSubmit.mErrors.getFirstError() + enter);
                return false;
            }
        }
        catch (Exception ex)
        {
            buildError("FICertDealCost","ForDealData", "费用" + mFICostTypeDefSchema.getCostID() + "生成凭证数据出现异常,错误是：" + ex.getMessage());
            tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() + "生成凭证数据出现异常,错误是：" +  ex.getMessage() + enter);
            return false;
        }

        return true;
    }


    private boolean DealError(String eInfo, String eType, String FSerialNo,String BussDate)
    {
        try
        {
            FIDataDealErrSet tFIDataDealErrSet = new FIDataDealErrSet();
            FIDataDealErrSchema tFIDataDealErrSchema = new FIDataDealErrSchema();
            String EeeSerialNo = PubFun1.CreateMaxNo("EeeSerialNo", 20);
            tFIDataDealErrSchema.setEeeSerialNo(EeeSerialNo);
            tFIDataDealErrSchema.setErrStage("02");
            tFIDataDealErrSchema.setErrType(eType);
            tFIDataDealErrSchema.setCertificateid(mFICostTypeDefSchema.getCertificateID());
            tFIDataDealErrSchema.setBatchNo(BatchNo);
            tFIDataDealErrSchema.setAFSerialNo(FSerialNo);
            tFIDataDealErrSchema.setBussDate(BussDate);
            tFIDataDealErrSchema.setErrInfo(eInfo);
            tFIDataDealErrSchema.setMakeDate(PubFun.getCurrentDate());
            tFIDataDealErrSchema.setMakeTime(PubFun.getCurrentTime());
            tFIDataDealErrSchema.setErrDealState("01");
            tFIDataDealErrSchema.setDealDescription("");
            tFIDataDealErrSet.add(tFIDataDealErrSchema);

            MMap map = new MMap();
            map.put(tFIDataDealErrSet, "INSERT");
            VData tInputInfo = new VData();
            tInputInfo.add(map);
            PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(tInputInfo, ""))
            {
                buildError("FICertDealCost","DealError", "费用" + mFICostTypeDefSchema.getCostID() +  ", 编号为" + FSerialNo+ "的数据生成错误记录信息出错");
                tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() +  ", 编号为" + FSerialNo+ "的数据生成错误记录信息出错"+ enter);
                return false;
            }

            return true;
        }
        catch (Exception ex)
        {
            buildError("FICertDealCost","DealError", "费用" + mFICostTypeDefSchema.getCostID() +  ", 编号为" + FSerialNo+ "的数据生成错误记录信息出现异常，信息为" + ex.getMessage() );
            tLogInfoDeal.WriteLogTxt("费用" + mFICostTypeDefSchema.getCostID() +  ", 编号为" + FSerialNo+ "的数据生成错误记录信息异常，信息为" + ex.getMessage() + enter);
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
