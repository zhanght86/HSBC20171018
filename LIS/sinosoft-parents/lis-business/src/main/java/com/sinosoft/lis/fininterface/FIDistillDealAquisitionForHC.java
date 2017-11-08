package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.FICostDataAcquisitionDefSchema;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.vschema.FIAboriginalDataSet;
import com.sinosoft.lis.schema.FIAboriginalDataSchema;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.db.FICostDataKeyDefDB;
import com.sinosoft.lis.vschema.FICostDataKeyDefSet;
import com.sinosoft.lis.schema.FIDataDistilledInfoSchema;
import com.sinosoft.lis.vschema.FIDataDistilledInfoSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.db.FIDataBaseLinkDB;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.fininterface.utility.ExeSQLForInterFace;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;
import com.sinosoft.lis.schema.FIDataForDealDistilledInfoSchema;
import com.sinosoft.lis.vschema.FIDataBaseLinkSet;
import com.sinosoft.lis.schema.FIDataDealTypeDefSchema;
import com.sinosoft.lis.vschema.FIDataForDealDistilledInfoSet;
import com.sinosoft.lis.fininterface.utility.PubSubmitForInterface;
import com.sinosoft.lis.vschema.FIAboriginalDataForDealSet;
import com.sinosoft.lis.schema.FIAboriginalDataForDealSchema;

import java.util.Calendar;

import com.sinosoft.lis.schema.FICostTypeDefSchema;
import com.sinosoft.lis.schema.FICostDataKeyDefSchema;


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
public class FIDistillDealAquisitionForHC {
private static Logger logger = Logger.getLogger(FIDistillDealAquisitionForHC.class);

        /** 错误处理类，每个需要错误处理的类中都放置该类 */
        public CErrors mErrors = new CErrors();

        private FICostDataAcquisitionDefSchema mFICostDataAcquisitionDefSchema = new FICostDataAcquisitionDefSchema();
        private FICostDataKeyDefSet mFICostDataKeyDefSet;
        private FIDataBaseLinkSet mFIDataBaseLinkSet;
        private DistillType mDistillTypeClass;
        private String mSQL = "";
        private long tTime1 = 0;
        private long tTime2 = 0;

        private FIDataDealTypeDefSchema mFIDataDealTypeDefSchema = new FIDataDealTypeDefSchema();
        private FICostTypeDefSchema mFICostTypeDefSchema = new FICostTypeDefSchema();


        /**执行参数 */
        GlobalInput mGlobalInput = new GlobalInput();
        String BusinessNo = "";
        String BatchNo = "";
        String VersionNo = "";

        private final String enter = "\r\n"; // 换行符
        private LogInfoDeal tLogInfoDeal = null;


        public FIDistillDealAquisitionForHC(LogInfoDeal oDeal)
        {
            tLogInfoDeal = oDeal;
        }


        public boolean InitFIDistillDealAquisition(FICostDataAcquisitionDefSchema tFICostDataAcquisitionDefSchema,FIDataDealTypeDefSchema tFIDataDealTypeDefSchema)
        {
            mFIDataDealTypeDefSchema.setSchema(tFIDataDealTypeDefSchema);
            if(!InitInfo(tFICostDataAcquisitionDefSchema))
            {
                return false;
            }
            return true;
        }

        public boolean InitFIDistillDealAquisition(FICostDataAcquisitionDefSchema tFICostDataAcquisitionDefSchema,FICostTypeDefSchema tFICostTypeDefSchema)
        {
            mFICostTypeDefSchema.setSchema(tFICostTypeDefSchema);
            VersionNo = mFICostTypeDefSchema.getVersionNo();
            if(!InitInfo(tFICostDataAcquisitionDefSchema))
            {
                return false;
            }
            return true;
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


        private boolean InitInfo(FICostDataAcquisitionDefSchema tFICostDataAcquisitionDefSchema)
        {
            try
            {
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + tFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类初始化" + enter);
                mFICostDataAcquisitionDefSchema = tFICostDataAcquisitionDefSchema;

                FIDataBaseLinkDB tFIDataBaseLinkDB = new FIDataBaseLinkDB();
                SQLwithBindVariables sbv=new SQLwithBindVariables();
                String strKeyDefSQL = "select * from FIDataBaseLink where InterfaceCode in (select DataBaseID from FICostDataBaseDef where VersionNo = '?VersionNo?' and AcquisitionID = '?AcquisitionID?')";
                sbv.sql(strKeyDefSQL);
                sbv.put("VersionNo", mFICostDataAcquisitionDefSchema .getVersionNo());
                sbv.put("AcquisitionID", mFICostDataAcquisitionDefSchema.getAcquisitionID());
                mFIDataBaseLinkSet = tFIDataBaseLinkDB.executeQuery(sbv);
                if(mFIDataBaseLinkSet == null || mFIDataBaseLinkSet.size()==0)
                {
                    buildError("FIDistillDealAquisitioForHC","InitInfo", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义未查询到任何数据源链接定义");
                    tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义未查询到任何数据源链接定义" + enter);
                    return false;
                }

                //查询主键信息表当前采集的相关的主键
                FICostDataKeyDefDB tFICostDataKeyDefDB = new FICostDataKeyDefDB();
                strKeyDefSQL = "select * from FICostDataKeyDef where versionno ='?VersionNo?' and AcquisitionID = '?AcquisitionID?' order by keyorder ";
                SQLwithBindVariables sbv1=new SQLwithBindVariables();
                sbv1.sql(strKeyDefSQL);
                sbv1.put("VersionNo", VersionNo);
                sbv1.put("AcquisitionID", mFICostDataAcquisitionDefSchema.getAcquisitionID());
                mFICostDataKeyDefSet = tFICostDataKeyDefDB.executeQuery(sbv1);
                if(mFICostDataKeyDefSet == null || mFICostDataKeyDefSet.size()==0)
                {
                    buildError("FIDistillDealAquisitioForHC","InitInfo", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义未查询到任何数据主键定义");
                    tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义未查询到任何数据主键定义" + enter);
                    return false;
                }

                if(mFICostDataAcquisitionDefSchema.getDistillMode().equals("1"))
                {
                    mSQL = mFICostDataAcquisitionDefSchema.getDistillSQLForOne();
                    if(mSQL==null || mSQL.equals("") || mSQL.equals("null"))
                    {
                        buildError("FIDistillDealAquisitioForHC","InitInfo", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义批量采集SQL为空");
                        tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义批量采集SQL为空" + enter);
                        return false;
                    }
                }
                else if (mFICostDataAcquisitionDefSchema.getDistillMode().equals("2"))
                {
                    Class tClass = Class.forName(mFICostDataAcquisitionDefSchema.getDistillClassForOne());
                    mDistillTypeClass = (DistillType) tClass.newInstance();
                    VData tInputData = new VData();
                    tInputData.add(mGlobalInput);
                    if(!mDistillTypeClass.initInfo(tInputData,tLogInfoDeal))
                    {
                        buildError("FIDistillDealAquisitioForHC","InitInfo", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集处理类初始化失败，错误信息为：" + mDistillTypeClass.mErrors.getFirstError());
                        tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集处理类初始化失败，错误信息为：" + mDistillTypeClass.mErrors.getFirstError() + enter);
                        return false;
                    }
                }
                else
                {
                    buildError("FIDistillDealAquisitioForHC","InitInfo", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义采集方式参数非法");
                    tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集定义采集方式参数非法" + enter);
                    return false;
                }
                return true;
            }
            catch (Exception ex)
            {
                buildError("FIDistillDealAquisitioForHC","InitInfo", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类初始化出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类初始化出现异常，异常信息为：" + ex.getMessage() + enter);
                return false;
            }
        }


        private boolean getInputData(VData cInputData)
        {
            try
            {
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类开始获取执行参数" + enter);
                mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
                BusinessNo = (String) cInputData.get(1);
                BatchNo = (String) cInputData.get(2);


                if (mGlobalInput == null)
                {
                    buildError("FIDistillDealAquisitioForHC", "getInputData","类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类检查前台传入的登陆信息为空!");
                    tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类检查前台传入的登陆信息为空!" + enter);
                    return false;
                }
                if (BusinessNo == null || BusinessNo.equals(""))
                {
                    buildError("FIDistillDealAquisitioForHC", "getInputData","类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类检查前台传入的业务关联号码为空!");
                    tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类检查前台传入的业务关联号码为空!" + enter);
                    return false;
                }

                if (BatchNo == null || BatchNo.equals(""))
                {
                    buildError("FIDistillDealAquisitioForHC", "getInputData","类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类检查前台传入的批次号码为空!");
                    tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类检查前台传入的批次号码为空!" + enter);
                    return false;
                }

                return true;
            }
            catch (Exception ex)
            {
                buildError("FIDistillDealAquisitioForHC","getInputData", "类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "提数处理类获得参数出现异常" + "异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("类型编号为" + mFICostTypeDefSchema.getCostID() + "数据采集编号为" +  mFICostDataAcquisitionDefSchema.getAcquisitionID() + "提数处理类获得参数出现异常" + "异常信息为：" + ex.getMessage() + enter);
                return false;
            }
        }

        public boolean DealWithData()
        {
            try
            {
                for (int i = 1; i <= mFIDataBaseLinkSet.size(); i++)
                {
                    FIDataBaseLinkSchema tFIDataBaseLinkSchema = mFIDataBaseLinkSet.get(i);
                    if (mFICostDataAcquisitionDefSchema.getDistillMode().equals("1"))
                    {
                        if(mFICostDataAcquisitionDefSchema.getAcquisitionType().equals("01"))
                        {
                            if (!getAboriginalDataBySql(tFIDataBaseLinkSchema))
                            {
                                return false;
                            }
                        }
                        else if(mFICostDataAcquisitionDefSchema.getAcquisitionType().equals("02"))
                        {
                            if (!getDataForDealBySql(tFIDataBaseLinkSchema))
                            {
                                return false;
                            }
                        }
                        else
                        {
                            buildError("FIDistillDealAquisitioForHC","DealWithData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类采集类型定义非法" );
                            tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类采集类型定义非法" + enter);
                            return false;
                        }
                    }
                    if (mFICostDataAcquisitionDefSchema.getDistillMode().equals("2"))
                    {
                        if(mFICostDataAcquisitionDefSchema.getAcquisitionType().equals("01"))
                        {
                            if (!getAboriginalDataByClass(tFIDataBaseLinkSchema))
                            {
                                return false;
                            }
                        }
                        else if(mFICostDataAcquisitionDefSchema.getAcquisitionType().equals("02"))
                        {
                            if (!getDataForDealByClass(tFIDataBaseLinkSchema))
                            {
                                return false;
                            }
                        }
                        else
                        {
                            buildError("FIDistillDealAquisitioForHC","DealWithData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类采集类型定义非法" );
                            tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类采集类型定义非法" + enter);
                            return false;
                        }
                     }
                }
                return true;
            }
            catch (Exception ex)
            {
                buildError("FIDistillDealAquisitioForHC","DealWithData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类执行采集时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "的数据采集类执行采集时出现异常，异常信息为：" + ex.getMessage() + enter);
                return false;
            }
        }

        private FIDataDistilledInfoSchema CreatLIDistillInfo( FIAboriginalDataSchema tFIAboriginalDataSchema ) throws Exception
        {
            try
            {
                FIDataDistilledInfoSchema tFIDataDistilledInfoSchema = new FIDataDistilledInfoSchema();
                String tKeyUnionValue =  CreatKeyUnionValue(tFIAboriginalDataSchema);
                tFIDataDistilledInfoSchema.setBatchNo(tFIAboriginalDataSchema.getBatchNo());
                tFIDataDistilledInfoSchema.setAcquisitionID(tFIAboriginalDataSchema.getAcquisitionID());
                tFIDataDistilledInfoSchema.setKeyUnionValue(tKeyUnionValue);
                tFIDataDistilledInfoSchema.setASerialNo(tFIAboriginalDataSchema.getASerialNo());
                tFIDataDistilledInfoSchema.setCertificateID(tFIAboriginalDataSchema.getCertificateID());
                tFIDataDistilledInfoSchema.setBusinessNo(tFIAboriginalDataSchema.getBusinessNo());
                tFIDataDistilledInfoSchema.setBussDate(tFIAboriginalDataSchema.getAccountDate());
                tFIDataDistilledInfoSchema.setMakeDate(PubFun.getCurrentDate());
                tFIDataDistilledInfoSchema.setMakeTime(PubFun.getCurrentTime());
                return tFIDataDistilledInfoSchema;
            }
            catch (Exception ex)
            {
                throw ex;
            }

        }

        private  FIDataForDealDistilledInfoSchema CreatLIDistillInfo( FIAboriginalDataForDealSchema tFIAboriginalDataForDealSchema ) throws Exception
        {
            try
            {
               FIDataForDealDistilledInfoSchema tFIDataForDealDistilledInfoSchema = new FIDataForDealDistilledInfoSchema();
               String tKeyUnionValue = tKeyUnionValue = CreatKeyUnionValue(tFIAboriginalDataForDealSchema);
               tFIDataForDealDistilledInfoSchema.setBatchNo(tFIAboriginalDataForDealSchema.getBatchNo());
               tFIDataForDealDistilledInfoSchema.setAcquisitionID(tFIAboriginalDataForDealSchema.getAcquisitionID());
               tFIDataForDealDistilledInfoSchema.setKeyUnionValue(tKeyUnionValue);
               tFIDataForDealDistilledInfoSchema.setSerialNo(tFIAboriginalDataForDealSchema.getSerialNo());
               tFIDataForDealDistilledInfoSchema.setDataType(tFIAboriginalDataForDealSchema.getDataType());
               tFIDataForDealDistilledInfoSchema.setBusinessNo(tFIAboriginalDataForDealSchema.getBusinessNo());
               tFIDataForDealDistilledInfoSchema.setBussDate(tFIAboriginalDataForDealSchema.getBusinessDate());
               tFIDataForDealDistilledInfoSchema.setMakeDate(PubFun.getCurrentDate());
               tFIDataForDealDistilledInfoSchema.setMakeTime(PubFun.getCurrentTime());
               return tFIDataForDealDistilledInfoSchema;

            }
            catch (Exception ex)
            {
                throw ex;
            }

        }

        private String CreatKeyUnionValue( FIAboriginalDataSchema tFIAboriginalDataSchema) throws Exception
        {
            String tKeyUnionValue = "";
            for(int i=1;i<= mFICostDataKeyDefSet.size();i++)
            {
                FICostDataKeyDefSchema tFICostDataKeyDefSchema = mFICostDataKeyDefSet.get(i);
                String temp = tFICostDataKeyDefSchema.getKeyID();
                String tempvalue = tFIAboriginalDataSchema.getV(temp);
                if(tempvalue.equals("null"))
                {
                    Exception e = new Exception("费用类型为" + tFIAboriginalDataSchema.getCostID() + "采集定义编号为" + tFIAboriginalDataSchema.getAcquisitionID() + "的业务数据数据在生成提数日志时出错，因为必要主键信息字段" + temp +"值为空");
                    throw e;
                }
                else
                {
                   if(i==1)
                   {
                      tKeyUnionValue = tKeyUnionValue +  tempvalue;
                   }
                   else
                   {
                      tKeyUnionValue = tKeyUnionValue + "," + tempvalue;
                   }
                }
            }
            return tKeyUnionValue;
        }

        private String CreatKeyUnionValue( FIAboriginalDataForDealSchema tFIAboriginalDataForDealSchema ) throws Exception
        {
            String tKeyUnionValue = "";
            for(int i=1;i<= mFICostDataKeyDefSet.size();i++)
            {
                FICostDataKeyDefSchema tFICostDataKeyDefSchema = mFICostDataKeyDefSet.get(i);
                String temp = tFICostDataKeyDefSchema.getKeyID();
                String tempvalue = tFIAboriginalDataForDealSchema.getV(temp);
                if(tempvalue.equals("null"))
                {
                    Exception e = new Exception("数据类型为" + tFIAboriginalDataForDealSchema.getDataType() + "采集定义编号为" + tFIAboriginalDataForDealSchema.getAcquisitionID() + "的业务数据数据在生成提数日志时出错，因为必要主键信息字段" + temp +"值为空");
                    throw e;
                }
                else
                {
                   if(i==1)
                   {
                      tKeyUnionValue = tKeyUnionValue +  tempvalue;
                   }
                   else
                   {
                      tKeyUnionValue = tKeyUnionValue + "," + tempvalue;
                   }
                }
            }
            return tKeyUnionValue;
        }

        private boolean getAboriginalDataBySql(FIDataBaseLinkSchema tFIDataBaseLinkSchema)
        {
            try
            {
                tLogInfoDeal.WriteLogTxt("开始采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "数据源编号为" + tFIDataBaseLinkSchema.getInterfaceCode() + "的数据采集任务" + enter);

                PubCalculator tPubCalculator = new PubCalculator();
                tPubCalculator.addBasicFactor("BusinessNo", BusinessNo);

                String sql = mSQL;
                tPubCalculator.setCalSql(sql);
                sql = tPubCalculator.calculateEx();

                String AddSql =  sql ;
                ExeSQLForInterFace tExeSQLForInterFace = new ExeSQLForInterFace("Normal");


                    FIAboriginalDataSet tFIAboriginalDataSet = new FIAboriginalDataSet();
                    tLogInfoDeal.WriteLogTxt("执行SQL为：" + AddSql + enter);
                    tTime1 = Calendar.getInstance().getTimeInMillis();
                    tFIAboriginalDataSet = tExeSQLForInterFace.executeQueryA(AddSql,tFIDataBaseLinkSchema);
                    tTime2 = Calendar.getInstance().getTimeInMillis();
                    tLogInfoDeal.WriteLogTxt("该SQL执行消耗的时间为：" + String.valueOf((tTime2 - tTime1) / 1000) + "秒" + enter);

                    if(tExeSQLForInterFace.mErrors.needDealError())
                    {
                        buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据SQL执行时出现错误，错误信息为：" + tExeSQLForInterFace.mErrors.getFirstError());
                        tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据SQL执行时出现错误，错误信息为：" + tExeSQLForInterFace.mErrors.getFirstError() + enter);
                        return false;
                    }

                    if(tFIAboriginalDataSet==null || tFIAboriginalDataSet.size()==0)
                    {
                        return true;
                    }
                    else
                    {
                        ekeInfo(tFIAboriginalDataSet,tFIDataBaseLinkSchema.getInterfaceCode());
                        if(!groupSaveData(tFIAboriginalDataSet,tFIDataBaseLinkSchema))
                        {
                            return false;
                        }
                    }

                return true;
            }
            catch (Exception ex)
            {
                buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行时出现异常，异常信息为：" + ex.getMessage() + enter);
                return false;
            }
        }


        private boolean getDataForDealBySql(FIDataBaseLinkSchema tFIDataBaseLinkSchema)
        {
            try
            {
                tLogInfoDeal.WriteLogTxt("开始采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "数据源编号为" + tFIDataBaseLinkSchema.getInterfaceCode() + "的数据采集任务" + enter);

                PubCalculator tPubCalculator = new PubCalculator();
                tPubCalculator.addBasicFactor("BusinessNo", BusinessNo);

                String sql = mSQL;
                tPubCalculator.setCalSql(sql);
                sql = tPubCalculator.calculateEx();

                String AddSql =  sql ;
                ExeSQLForInterFace tExeSQLForInterFace = new ExeSQLForInterFace("ForDeal");
                tLogInfoDeal.WriteLogTxt("执行SQL为：" + AddSql + enter);


                    FIAboriginalDataForDealSet tFIAboriginalDataForDealSet = new FIAboriginalDataForDealSet();
                    tTime1 = Calendar.getInstance().getTimeInMillis();
                    tFIAboriginalDataForDealSet = tExeSQLForInterFace.executeQueryB(AddSql,tFIDataBaseLinkSchema);
                    tTime2 = Calendar.getInstance().getTimeInMillis();
                    tLogInfoDeal.WriteLogTxt("该SQL执行消耗的时间为：" + String.valueOf((tTime2 - tTime1) / 1000) + "秒" + enter);

                    if(tExeSQLForInterFace.mErrors.needDealError())
                    {
                        buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据SQL执行时出现错误，错误信息为：" + tExeSQLForInterFace.mErrors.getFirstError());
                        tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据SQL执行时出现错误，错误信息为：" + tExeSQLForInterFace.mErrors.getFirstError() + enter);
                        return false;

                    }

                    if(tFIAboriginalDataForDealSet==null || tFIAboriginalDataForDealSet.size()==0)
                    {
                        return true;
                    }
                    else
                    {
                        ekeInfo(tFIAboriginalDataForDealSet,tFIDataBaseLinkSchema.getInterfaceCode());
                        if(!groupSaveData(tFIAboriginalDataForDealSet,tFIDataBaseLinkSchema))
                        {
                            return false;
                        }
                    }

                return true;
            }
            catch (Exception ex)
            {
                buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行时出现异常，异常信息为：" + ex.getMessage() + enter);
                return false;
            }
        }

        private boolean getAboriginalDataByClass(FIDataBaseLinkSchema tFIDataBaseLinkSchema)
        {
            try
            {

                tLogInfoDeal.WriteLogTxt("开始采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "数据源编号为" + tFIDataBaseLinkSchema.getInterfaceCode() + "的数据采集任务" + enter);

                boolean tFlag = true;
                while(tFlag)
                {
                    FIAboriginalDataSet tFIAboriginalDataSet = new FIAboriginalDataSet();
                    tTime1 = Calendar.getInstance().getTimeInMillis();
                    tFIAboriginalDataSet = mDistillTypeClass.DitillInfoNormalForOne(tFIDataBaseLinkSchema);
                    tTime2 = Calendar.getInstance().getTimeInMillis();
                    tLogInfoDeal.WriteLogTxt("该处理类执行消耗的时间为：" + String.valueOf((tTime2 - tTime1) / 1000) + "秒" + enter);

                    if(tFIAboriginalDataSet==null || tFIAboriginalDataSet.size()==0)
                    {
                        tFlag = false;
                    }
                    else
                    {
                        ekeInfo(tFIAboriginalDataSet,tFIDataBaseLinkSchema.getInterfaceCode());
                        if(!groupSaveData(tFIAboriginalDataSet,tFIDataBaseLinkSchema))
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
                buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据类执行时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据类执行时出现异常，异常信息为：" + ex.getMessage() + enter);
                return false;
            }
        }

        private boolean getDataForDealByClass(FIDataBaseLinkSchema tFIDataBaseLinkSchema)
        {
            try
            {

                tLogInfoDeal.WriteLogTxt("开始采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "数据源编号为" + tFIDataBaseLinkSchema.getInterfaceCode() + "的数据采集任务" + enter);

                boolean tFlag = true;
                while(tFlag)
                {
                    FIAboriginalDataForDealSet tFIAboriginalDataForDealSet = new FIAboriginalDataForDealSet();
                    tTime1 = Calendar.getInstance().getTimeInMillis();
                    tFIAboriginalDataForDealSet = mDistillTypeClass.DitillInfoForDealForOne(tFIDataBaseLinkSchema);
                    tTime2 = Calendar.getInstance().getTimeInMillis();
                    tLogInfoDeal.WriteLogTxt("该处理类执行消耗的时间为：" + String.valueOf((tTime2 - tTime1) / 1000) + "秒" + enter);

                    if(tFIAboriginalDataForDealSet==null || tFIAboriginalDataForDealSet.size()==0)
                    {
                        tFlag = false;
                    }
                    else
                    {
                        ekeInfo(tFIAboriginalDataForDealSet,tFIDataBaseLinkSchema.getInterfaceCode());
                        if(!groupSaveData(tFIAboriginalDataForDealSet,tFIDataBaseLinkSchema))
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
                buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据类执行时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据类执行时出现异常，异常信息为：" + ex.getMessage() + enter);
                return false;
            }
        }


        private void ekeInfo(FIAboriginalDataSet tFIAboriginalDataSet,String tDataBaseID) throws Exception
        {
            try
            {
               String tDate = PubFun.getCurrentDate();
               String tTime = PubFun.getCurrentTime();
               FinCreateSerialNo tFinCreateSerialNo = new FinCreateSerialNo();
               String[] SerialNo = tFinCreateSerialNo.getSerialNo("ABORIGINALNO", tFIAboriginalDataSet.size(), 20);
               for (int i = 1; i <= tFIAboriginalDataSet.size(); i++)
               {
                   tFIAboriginalDataSet.get(i).setASerialNo(SerialNo[i - 1]);
                   tFIAboriginalDataSet.get(i).setAcquisitionID(mFICostDataAcquisitionDefSchema.getAcquisitionID());
                   tFIAboriginalDataSet.get(i).setDataBaseID(tDataBaseID);
                   tFIAboriginalDataSet.get(i).setBatchNo(BatchNo);
                   tFIAboriginalDataSet.get(i).setCertificateID(mFICostTypeDefSchema.getCertificateID());
                   tFIAboriginalDataSet.get(i).setCostID(mFICostTypeDefSchema.getCostID());
                   tFIAboriginalDataSet.get(i).setDataState("01");
                   tFIAboriginalDataSet.get(i).setAccountDate(PubFun.getCurrentDate());
                   tFIAboriginalDataSet.get(i).setMakeDate(tDate);
                   tFIAboriginalDataSet.get(i).setMakeTime(tTime);
               }
            }
            catch (Exception ex)
            {
                Exception  e = new Exception("FIAboriginalDataSet追加数据出现异常：" + ex.getMessage());
                throw e;
            }
        }

        private void ekeInfo(FIAboriginalDataForDealSet tFIAboriginalDataForDealSet,String tDataBaseID) throws Exception
        {
            try
            {
               FinCreateSerialNo tFinCreateSerialNo = new FinCreateSerialNo();
               String[] SerialNo = tFinCreateSerialNo.getSerialNo("DataDealNO", tFIAboriginalDataForDealSet.size(), 20);
               for (int i = 1; i <= tFIAboriginalDataForDealSet.size(); i++)
               {
                   tFIAboriginalDataForDealSet.get(i).setSerialNo(SerialNo[i - 1]);
                   tFIAboriginalDataForDealSet.get(i).setAcquisitionID(mFICostDataAcquisitionDefSchema.getAcquisitionID());
                   tFIAboriginalDataForDealSet.get(i).setDataBaseID(tDataBaseID);
                   tFIAboriginalDataForDealSet.get(i).setBatchNo(BatchNo);
                   tFIAboriginalDataForDealSet.get(i).setDataType(mFIDataDealTypeDefSchema.getDataType());
                   tFIAboriginalDataForDealSet.get(i).setBusinessDate(PubFun.getCurrentDate());
                   tFIAboriginalDataForDealSet.get(i).setDataState("01");
               }
            }
            catch (Exception ex)
            {
                Exception  e = new Exception("FIAboriginalDataSet追加数据出现异常：" + ex.getMessage());
                throw e;
            }
        }

        private FIDataDistilledInfoSet getDistillPKInfo(FIAboriginalDataSet tFIAboriginalDataSet) throws Exception
        {
            try
            {
                FIDataDistilledInfoSet tFIDataDistilledInfoSet = new FIDataDistilledInfoSet();
                for (int j = 0; j < tFIAboriginalDataSet.size(); j++)
                {
                    FIAboriginalDataSchema tFIAboriginalDataSchema = tFIAboriginalDataSet.get(j + 1);
                    tFIDataDistilledInfoSet.add(CreatLIDistillInfo(tFIAboriginalDataSchema));
                }
                return tFIDataDistilledInfoSet;
            }
            catch (Exception ex)
            {
                Exception  e = new Exception("生成提数主键数据出现异常：" + ex.getMessage());
                throw e;
            }
        }

        public  FIDataForDealDistilledInfoSet getDistillPKInfo(FIAboriginalDataForDealSet tFIAboriginalDataForDealSet) throws Exception
        {
            try
            {
                FIDataForDealDistilledInfoSet tFIDataForDealDistilledInfoSet = new FIDataForDealDistilledInfoSet();
                for (int j = 0; j < tFIAboriginalDataForDealSet.size(); j++)
                {
                    FIAboriginalDataForDealSchema tFIAboriginalDataForDealSchema = tFIAboriginalDataForDealSet.get(j+1);
                    tFIDataForDealDistilledInfoSet.add(CreatLIDistillInfo(tFIAboriginalDataForDealSchema));
                }
                return tFIDataForDealDistilledInfoSet;
            }
            catch (Exception ex)
            {
                Exception  e = new Exception("生成提数主键数据出现异常：" + ex.getMessage());
                throw e;
            }
        }

        public boolean groupSaveData(FIAboriginalDataSet tFIAboriginalDataSet,FIDataBaseLinkSchema tFIDataBaseLinkSchema)
        {
            try
            {
                MMap tmap = new MMap();
                VData tInputData = new VData();
                tmap.put(tFIAboriginalDataSet, "INSERT");
                tInputData.add(tmap);
                PubSubmit tPubSubmit = new PubSubmit();
                if (!tPubSubmit.submitData(tInputData, ""))
                {
                    this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                    buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在本地数据库时出错，提示信息为：" +tPubSubmit.mErrors.getFirstError());
                    tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在本地数据库时出错，提示信息为：" +tPubSubmit.mErrors.getFirstError() + enter);
                    return false;
                }
                if(mFICostDataAcquisitionDefSchema.getDataSourceType().equals("02"))
                {
                    PubSubmitForInterface tPubSubmitForInterface = new PubSubmitForInterface();
                    if (!tPubSubmitForInterface.submitData(tInputData, tFIDataBaseLinkSchema))
                    {
                        this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                        buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在外地数据库时出错，提示信息为：" +tPubSubmitForInterface.mErrors.getFirstError());
                        tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在外地数据库时出错，提示信息为：" +tPubSubmitForInterface.mErrors.getFirstError() + enter);
                        return false;
                    }
                }

                return true;
            }
            catch (Exception ex)
            {
                buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行保存功能时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行保存功能时出现异常，异常信息为：" + ex.getMessage() + enter);
                return false;
            }

        }

        private boolean groupSaveData(FIAboriginalDataForDealSet tFIAboriginalDataForDealSet,FIDataBaseLinkSchema tFIDataBaseLinkSchema)
        {
            try
            {
                MMap tmap = new MMap();
                VData tInputData = new VData();
                tmap.put(tFIAboriginalDataForDealSet, "INSERT");
                tInputData.add(tmap);
                PubSubmit tPubSubmit = new PubSubmit();
                if (!tPubSubmit.submitData(tInputData, ""))
                {
                    this.mErrors.copyAllErrors(tPubSubmit.mErrors);
                    buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在本地数据库时出错，提示信息为：" +tPubSubmit.mErrors.getFirstError());
                    tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在本地数据库时出错，提示信息为：" +tPubSubmit.mErrors.getFirstError() + enter);
                    return false;
                }
                if(mFICostDataAcquisitionDefSchema.getDataSourceType().equals("02"))
                {
                    PubSubmitForInterface tPubSubmitForInterface = new PubSubmitForInterface();
                    if (!tPubSubmitForInterface.submitData(tInputData, tFIDataBaseLinkSchema))
                    {
                        this.mErrors.copyAllErrors(tPubSubmitForInterface.mErrors);
                        buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在外地数据库时出错，提示信息为：" +tPubSubmitForInterface.mErrors.getFirstError());
                        tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据保存在外地数据库时出错，提示信息为：" +tPubSubmitForInterface.mErrors.getFirstError() + enter);
                        return false;
                    }
                }

                return true;
            }
            catch (Exception ex)
            {
                buildError("FIDistillDealAquisitioForHC","stepSaveData", "数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行保存功能时出现异常，异常信息为：" + ex.getMessage());
                tLogInfoDeal.WriteLogTxt("数据采集编码为" + mFICostDataAcquisitionDefSchema.getAcquisitionID() + "从数据源" + tFIDataBaseLinkSchema.getInterfaceCode() + "的采集数据执行保存功能时出现异常，异常信息为：" + ex.getMessage() + enter);
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
