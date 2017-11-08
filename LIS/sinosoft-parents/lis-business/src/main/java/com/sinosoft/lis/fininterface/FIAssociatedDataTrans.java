package com.sinosoft.lis.fininterface;
import java.sql.SQLWarning;

import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.FIAssociatedItemDefSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.SSRS;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FIAssociatedDataTrans
{
private static Logger logger = Logger.getLogger(FIAssociatedDataTrans.class);


    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private FIAssociatedItemDefSchema mFIAssociatedItemDefSchema = new FIAssociatedItemDefSchema();
    private String mUpdateSQL;
    private  SQLwithBindVariables mUpdateSQLBV=new SQLwithBindVariables();
    private TransTypeClass mTransTypeClass ;
    public LogInfoDeal mLogInfoDeal;
    private final String enter = "\r\n"; // 换行符

    public FIAssociatedDataTrans(LogInfoDeal tLogInfoDeal)
    {
        mLogInfoDeal = tLogInfoDeal;
    }


    public boolean initInfo(FIAssociatedItemDefSchema tFIAssociatedItemDefSchema)
    {
        try
        {
            mFIAssociatedItemDefSchema.setSchema(tFIAssociatedItemDefSchema);
            String transmode = tFIAssociatedItemDefSchema.getTransFlag();

            if (transmode.equals("N"))
            {
                 return true;
            }
            else if (transmode.equals("S"))
            {
                String sql = tFIAssociatedItemDefSchema.getTransSQL();
                if (sql == null || sql.equals("") || sql.equals("null"))
                {
                    buildError("FIAssociatedDataTrans", "initInfo","专项:" + mFIAssociatedItemDefSchema.getAssociatedName() + "对应的处理SQL方式SQL值为空.");
                    mLogInfoDeal.WriteLogTxt("专项:" + mFIAssociatedItemDefSchema.getAssociatedName() + "对应的处理SQL方式SQL值为空." + enter);
                    return false;
                }

                 mUpdateSQL = "update fidatatransresult set " +
                                      tFIAssociatedItemDefSchema.
                                      getColumnID().
                                      trim()
                                      + " = (" + sql + ")" +
                                      " where " +
                                      " exists(select 'X' from ficosttypedef, FIInfoFinItemAssociated" +
                                      " where ficosttypedef.costid = fidatatransresult.costid" +
                                      " and fiinfofinitemassociated.finitemid = ficosttypedef.finitemid" +
                                      " and fiinfofinitemassociated.AssociatedID = '?AssociatedID?')" +
                                      " and BatchNo = '" ;
                 mUpdateSQLBV.put("AssociatedID",mFIAssociatedItemDefSchema.getAssociatedID());
            }
            else if (transmode.equals("C"))
            {
                try
                {
                    Class tClass = null;
                    tClass = Class.forName(tFIAssociatedItemDefSchema.getTransClass());
                    mTransTypeClass = (TransTypeClass)tClass.newInstance();

                }
                catch (Exception ex)
                {
                    buildError("FIAssociatedDataTrans","initInfo", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化实现类初试化异常，信息为：" + ex.getMessage());
                    mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化实现类初试化异常，信息为：" + ex.getMessage() + enter);
                    return false;
                }
            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FIAssociatedDataTrans","initInfo", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化处理类初试化异常，信息为" + ex.getMessage());
            mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化处理类初试化异常，信息为" + ex.getMessage() + enter);
            return false;
        }
    }


    public boolean upDateClumn(String tBatchNo)
    {
        try
        {
            String transmode = mFIAssociatedItemDefSchema.getTransFlag();
            if (transmode.equals("N"))
            {
                return true;
            }
            else if (transmode.equals("S"))
            {
                String sql = mUpdateSQL + tBatchNo + "'";
                mUpdateSQLBV.sql(sql);
                ExeSQL tExeSQL = new ExeSQL();
                if (!tExeSQL.execUpdateSQL(mUpdateSQLBV))
                {
                    buildError("FIAssociatedDataTrans", "upDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "SQL方式更新专项字段失败，信息为：" + tExeSQL.mErrors.getFirstError());
                    mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "SQL方式更新专项字段失败，信息为：" + tExeSQL.mErrors.getFirstError() + enter);
                    return false;
                }
            }
            else if (transmode.equals("C"))
            {

                    VData sVData = new VData();
                    sVData.add(tBatchNo);
                    if(!mTransTypeClass.DealInfo(sVData))
                    {
                        buildError("FIAssociatedDataTrans", "upDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "类方式更新专项字段失败，信息为：" + mTransTypeClass.mErrors.getFirstError());
                        mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "类方式更新专项字段失败，信息为：" + mTransTypeClass.mErrors.getFirstError() + enter);
                        return false;
                    }

            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FIAssociatedDataTrans","upDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化处理类更新专项字段异常，信息为" + ex.getMessage());
            mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化处理类更新专项字段异常，信息为" + ex.getMessage() + enter);
            return false;
        }
    }


    public boolean checkDataClumn(String tBatchNo)
    {
        try
        {
            String transmode = mFIAssociatedItemDefSchema.getTransFlag();
            if (transmode.equals("N"))
            {
                return true;
            }
            else if (transmode.equals("S"))
            {
                String sql = "select distinct " + mFIAssociatedItemDefSchema.getColumnID().trim() + " from FIDataTransResult" +
                                      " where " + " (" + mFIAssociatedItemDefSchema.getTransSQL() + ") is null and" +
                                      " exists(select 'X' from ficosttypedef, FIInfoFinItemAssociated" +
                                      " where ficosttypedef.costid = fidatatransresult.costid" +
                                      " and fiinfofinitemassociated.finitemid = ficosttypedef.finitemid" +
                                      " and fiinfofinitemassociated.AssociatedID = '?AssociatedID?')" +
                                      " and BatchNo = '?tBatchNo?'";
                SQLwithBindVariables sqlbv=new SQLwithBindVariables();
                sqlbv.sql(sql);
                sqlbv.put("AssociatedID", mFIAssociatedItemDefSchema.getAssociatedID());
                sqlbv.put("tBatchNo", tBatchNo);


                ExeSQL tExeSQL = new ExeSQL();
                SSRS aSSRS = new SSRS();
                aSSRS = tExeSQL.execSQL(sqlbv);
                if (tExeSQL.mErrors.needDealError())
                {
                    buildError("FIAssociatedDataTrans", "checkDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "SQL方式校验专项字段映射完整性失败，信息为：" + tExeSQL.mErrors.getFirstError());
                    mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "SQL方式校验专项字段映射完整性失败，信息为：" + tExeSQL.mErrors.getFirstError() + enter);
                    return false;
                }
                if(aSSRS.getMaxRow()>0)
                {
                    String info = "";
                    for (int i = 1; i <= aSSRS.getMaxRow(); i++)
                    {
                        info += "[" + aSSRS.GetText(i, 1) + "]";
                    }
                    buildError("FIAssociatedDataTrans", "checkDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "存在未定义的待映射编码，信息为：" + info);
                    mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "存在未定义的待映射编码，信息为：" + info + enter);
                    return false;
                }

            }
            else if (transmode.equals("C"))
            {

                    VData sVData = new VData();
                    sVData.add(tBatchNo);
                    if(!mTransTypeClass.CheckInfo(sVData))
                    {
                        buildError("FIAssociatedDataTrans", "upDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "类方式校验专项字段映射完整性失败，信息为：" + mTransTypeClass.mErrors.getFirstError());
                        mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "类方式校验专项字段映射完整性失败，信息为：" + mTransTypeClass.mErrors.getFirstError() + enter);
                        return false;
                    }
                    if(!mTransTypeClass.getClumnValue().equals(""))
                    {
                        buildError("FIAssociatedDataTrans", "checkDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "存在未定义的待映射编码，信息为：" + mTransTypeClass.getClumnValue());
                        mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "存在未定义的待映射编码，信息为：" + mTransTypeClass.getClumnValue() + enter);
                        return false;
                    }


            }
            return true;
        }
        catch (Exception ex)
        {
            buildError("FIAssociatedDataTrans","upDateClumn", "专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化处理类更新专项字段异常，信息为" + ex.getMessage());
            mLogInfoDeal.WriteLogTxt("专项" + mFIAssociatedItemDefSchema.getAssociatedID() + "转化处理类更新专项字段异常，信息为" + ex.getMessage() + enter);
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
