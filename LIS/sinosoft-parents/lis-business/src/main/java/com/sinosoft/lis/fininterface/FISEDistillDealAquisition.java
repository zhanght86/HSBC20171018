package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.FICostDataAcquisitionDefSchema;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.vschema.FIAboriginalDataForDealSet;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.lis.schema.FIAboriginalDataForDealSchema;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.db.FICostDataKeyDefDB;
import com.sinosoft.lis.vschema.FICostDataKeyDefSet;
import com.sinosoft.lis.schema.FIDataForDealDistilledInfoSchema;
import com.sinosoft.lis.vschema.FIDataForDealDistilledInfoSet;
import com.sinosoft.lis.pubfun.PubFun;

import java.sql.Connection;

import com.sinosoft.lis.db.FICostDataBaseDefDB;
import com.sinosoft.lis.vschema.FICostDataBaseDefSet;
import com.sinosoft.lis.schema.FICostDataBaseDefSchema;
import com.sinosoft.lis.db.FIDataBaseLinkDB;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.lis.vdb.FIDataForDealDistilledInfoDBSet;
import com.sinosoft.lis.fininterface.utility.DBConnPool;

import java.sql.*;

import com.sinosoft.utility.CError;
import com.sinosoft.lis.vschema.FIDataDealErrSet;
import com.sinosoft.lis.schema.FIDataDealErrSchema;
import com.sinosoft.lis.schema.FIDataDealTypeDefSchema;
import com.sinosoft.lis.fininterface.utility.FinCreateSerialNo;

/**
 * <p>
 * ClassName: FISEDistillDealAquisition
 * </p>
 * <p>
 * Description: 财务接口-二次数据提取
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @Database: 财务接口
 * @author：ZhongYan
 * @version：1.0
 * @CreateDate：2008-08-11
 */

public class FISEDistillDealAquisition {
private static Logger logger = Logger.getLogger(FISEDistillDealAquisition.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 存储处理信息 */
    private VData mInputInfo = new VData();

    public VData mRESULT = new VData();

    public FIAboriginalDataForDealSet mFIAboriginalDataForDealSet = new
            FIAboriginalDataForDealSet();

    public FICostDataAcquisitionDefSchema mFICostDataAcquisitionDefSchema = new
            FICostDataAcquisitionDefSchema();

    private FIDataDealErrSet mFIDataDealErrSet = new FIDataDealErrSet();

    private FIDataDealTypeDefSchema mFIDataDealTypeDefSchema = new
            FIDataDealTypeDefSchema();

    // MMap mmap = new MMap();

    /** 业务数据 */
    GlobalInput mGlobalInput = new GlobalInput();

    String StartDate = "";

    String EndDate = "";

    String BatchNo = "";

    String VersionNo = "";

    /** 数据批次处理限制数 */
    private int mMaxDealNUm = 200;

    public FISEDistillDealAquisition() {}

    public boolean dealProcess(VData cInputData) {
        if (!getInputData(cInputData)) {
            return false;
        }
        if (!InitInfo()) {
            return false;
        }
        if (!DealWithData()) {
            return false;
        }
        if (!WriteLog("SUCC")) {
            return false;
        }
        return true;
    }

    private boolean getInputData(VData cInputData) {
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        StartDate = (String) cInputData.get(1);
        EndDate = (String) cInputData.get(2);
        BatchNo = (String) cInputData.get(3);
        VersionNo = (String) cInputData.get(4);
        mFIDataDealTypeDefSchema = (FIDataDealTypeDefSchema) cInputData.
                                   getObjectByObjectName(
                "FIDataDealTypeDefSchema", 0);

        if (mGlobalInput == null) {
            buildError("getInputData", "前台传入的登陆信息为空!");
            return false;
        }
        if (StartDate == null || StartDate.equals("")) {
            buildError("getInputData", "没有起始日期!");
            return false;
        }
        if (EndDate == null || EndDate.equals("")) {
            buildError("getInputData", "没有终止日期!");
            return false;
        }
        if (BatchNo == null || BatchNo.equals("")) {
            buildError("getInputData", "没有批次号码!");
            return false;
        }
        if (VersionNo == null || VersionNo.equals("")) {
            buildError("getInputData", "没有版本号码!");
            return false;
        }

        return true;
    }

    public boolean InitFISEDistillDealAquisition(FICostDataAcquisitionDefSchema
            tFICostDataAcquisitionDefSchema) {
        mFICostDataAcquisitionDefSchema = tFICostDataAcquisitionDefSchema;
        return true;
    }

    private boolean InitInfo() {
        try {
            mInputInfo.clear();
            mInputInfo.add(mGlobalInput);
            mInputInfo.add(StartDate);
            mInputInfo.add(EndDate);
            mInputInfo.add(BatchNo);
            mInputInfo.add(VersionNo);
            // mFICostDataAcquisitionDefSchema
            return true;
        } catch (Exception ex) {
            buildError("InitInfo", ex.getMessage());
            return false;
        }
    }

    public Connection getConnection(FIDataBaseLinkSchema tFIDataBaseLinkSchema) {
    try {
        Connection con = DBConnPool.getConnection(tFIDataBaseLinkSchema);
        if (con == null) {
            logger.debug("getConnection的连接没有获得");
        }
        return con;
    } catch (Exception ex) {
        return null;
    }

    }

    public boolean getLinkDealDataBySql(String sql,
                                        FIDataBaseLinkSchema tFIDataBaseLinkSchema) {
        boolean flag = true;
        try {
            FIAboriginalDataForDealSet tFIAboriginalDataForDealSet = new
                    FIAboriginalDataForDealSet();
            MMap tmap = new MMap();
            // mFIAboriginalDataForDealSet.clear();
            VData tInputData = new VData();
            SSRS tSSRS = new SSRS();
            Connection con = getConnection(tFIDataBaseLinkSchema);
            ExeSQL tExeSQL = new ExeSQL(con);
//			sql = "select * from (" + sql + ") where rownum <=" + mMaxDealNUm;
//			logger.debug("提取数据" + sql);
//			tSSRS = tExeSQL.execSQL(sql);
            String AddSql = "";
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            	AddSql = "select * from (" + sql + ") where rownum <=?mMaxDealNUm?";
            }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
             	AddSql = "select * from (" + sql + ") t limit 0,?mMaxDealNUm?";
            }
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(AddSql);
            sqlbv.put("mMaxDealNUm", mMaxDealNUm);
            logger.debug("提取数据" + AddSql);
            tSSRS = tExeSQL.execSQL(sqlbv);

            // 判断是否有记录如果没有记录则返回空
            if (tSSRS == null || tSSRS.getMaxRow() == 0) {
                flag = true;
            } else {
                FinCreateSerialNo tFinCreateSerialNo = new FinCreateSerialNo();
                String[] SerialNo = tFinCreateSerialNo.getSerialNo("FISEDISTILLNO",tSSRS.getMaxRow(),20);
                for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
                    FIAboriginalDataForDealSchema
                            tFIAboriginalDataForDealSchema = new
                            FIAboriginalDataForDealSchema();
                    tFIAboriginalDataForDealSchema.setSerialNo(SerialNo[i-1]);
                    tFIAboriginalDataForDealSchema.setBatchNo(BatchNo);
                    tFIAboriginalDataForDealSchema.setAcquisitionID(
                            mFICostDataAcquisitionDefSchema.getAcquisitionID());
                    tFIAboriginalDataForDealSchema.setDataBaseID(
                            tFIDataBaseLinkSchema.getInterfaceCode());
                    tFIAboriginalDataForDealSchema.setDataType(
                            mFICostDataAcquisitionDefSchema.getCostOrDataID());
                    tFIAboriginalDataForDealSchema.setDataState("00");
                    tFIAboriginalDataForDealSchema.setStringInfo01(tSSRS.
                            GetText(i, 1));
                    tFIAboriginalDataForDealSchema.setStringInfo02(tSSRS.
                            GetText(i, 2));
                    tFIAboriginalDataForDealSchema.setStringInfo03(tSSRS.
                            GetText(i, 3));
                    tFIAboriginalDataForDealSchema.setStringInfo04(tSSRS.
                            GetText(i, 4));
                    tFIAboriginalDataForDealSchema.setStringInfo05(tSSRS.
                            GetText(i, 5));
                    tFIAboriginalDataForDealSchema.setStringInfo06(tSSRS.
                            GetText(i, 6));
                    tFIAboriginalDataForDealSchema.setStringInfo07(tSSRS.
                            GetText(i, 7));
                    tFIAboriginalDataForDealSchema.setStringInfo08(tSSRS.
                            GetText(i, 8));
                    tFIAboriginalDataForDealSchema.setStringInfo09(tSSRS.
                            GetText(i, 9));
                    tFIAboriginalDataForDealSchema.setStringInfo10(tSSRS.
                            GetText(i, 10));
                    tFIAboriginalDataForDealSchema.setStringInfo11(tSSRS.
                            GetText(i, 11));
                    tFIAboriginalDataForDealSchema.setStringInfo12(tSSRS.
                            GetText(i, 12));
                    tFIAboriginalDataForDealSchema.setStringInfo13(tSSRS.
                            GetText(i, 13));
                    tFIAboriginalDataForDealSchema.setStringInfo14(tSSRS.
                            GetText(i, 14));
                    tFIAboriginalDataForDealSchema.setStringInfo15(tSSRS.
                            GetText(i, 15));
                    tFIAboriginalDataForDealSchema.setDateInfo01(tSSRS.GetText(
                            i, 16));
                    tFIAboriginalDataForDealSchema.setDateInfo02(tSSRS.GetText(
                            i, 17));
                    tFIAboriginalDataForDealSchema.setDateInfo03(tSSRS.GetText(
                            i, 18));
                    tFIAboriginalDataForDealSchema.setDateInfo04(tSSRS.GetText(
                            i, 19));
                    tFIAboriginalDataForDealSchema.setDateInfo05(tSSRS.GetText(
                            i, 20));
                    tFIAboriginalDataForDealSchema.setDateInfo06(tSSRS.GetText(
                            i, 21));
                    tFIAboriginalDataForDealSchema.setDateInfo07(tSSRS.GetText(
                            i, 22));
                    tFIAboriginalDataForDealSchema.setDateInfo08(tSSRS.GetText(
                            i, 23));
                    tFIAboriginalDataForDealSchema.setDateInfo09(tSSRS.GetText(
                            i, 24));
                    tFIAboriginalDataForDealSchema.setDateInfo10(tSSRS.GetText(
                            i, 25));
                    tFIAboriginalDataForDealSchema.setNumInfo01(tSSRS.GetText(i,
                            26));
                    tFIAboriginalDataForDealSchema.setNumInfo02(tSSRS.GetText(i,
                            27));
                    tFIAboriginalDataForDealSchema.setNumInfo03(tSSRS.GetText(i,
                            28));
                    tFIAboriginalDataForDealSchema.setNumInfo04(tSSRS.GetText(i,
                            29));
                    tFIAboriginalDataForDealSchema.setNumInfo05(tSSRS.GetText(i,
                            30));
                    tFIAboriginalDataForDealSchema.setBusinessNo01(tSSRS.
                            GetText(i, 31));
                    tFIAboriginalDataForDealSchema.setBusinessNo02(tSSRS.
                            GetText(i, 32));
                    tFIAboriginalDataForDealSchema.setBusinessNo03(tSSRS.
                            GetText(i, 33));
                    tFIAboriginalDataForDealSchema.setBusinessNo04(tSSRS.
                            GetText(i, 34));
                    tFIAboriginalDataForDealSchema.setBusinessNo05(tSSRS.
                            GetText(i, 35));
                    tFIAboriginalDataForDealSchema.setBusinessNo06(tSSRS.
                            GetText(i, 36));
                    tFIAboriginalDataForDealSchema.setBusinessNo07(tSSRS.
                            GetText(i, 37));
                    tFIAboriginalDataForDealSchema.setBusinessNo08(tSSRS.
                            GetText(i, 38));
                    tFIAboriginalDataForDealSchema.setBusinessNo09(tSSRS.
                            GetText(i, 39));
                    tFIAboriginalDataForDealSchema.setBusinessNo10(tSSRS.
                            GetText(i, 40));
                    tFIAboriginalDataForDealSchema.setTypeFlag01(tSSRS.GetText(
                            i, 41));
                    tFIAboriginalDataForDealSchema.setTypeFlag02(tSSRS.GetText(
                            i, 42));
                    tFIAboriginalDataForDealSchema.setTypeFlag03(tSSRS.GetText(
                            i, 43));
                    tFIAboriginalDataForDealSchema.setTypeFlag04(tSSRS.GetText(
                            i, 44));
                    tFIAboriginalDataForDealSchema.setTypeFlag05(tSSRS.GetText(
                            i, 45));
                    tFIAboriginalDataForDealSchema.setTypeFlag06(tSSRS.GetText(
                            i, 46));
                    tFIAboriginalDataForDealSchema.setTypeFlag07(tSSRS.GetText(
                            i, 47));
                    tFIAboriginalDataForDealSchema.setTypeFlag08(tSSRS.GetText(
                            i, 48));
                    tFIAboriginalDataForDealSchema.setTypeFlag09(tSSRS.GetText(
                            i, 49));
                    tFIAboriginalDataForDealSchema.setTypeFlag10(tSSRS.GetText(
                            i, 50));

                    // mFIAboriginalDataForDealSet.add(tFIAboriginalDataForDealSchema);
                    tFIAboriginalDataForDealSet.add(
                            tFIAboriginalDataForDealSchema);
                }

                // mmap.put(mFIAboriginalDataForDealSet, "INSERT");
                mFIAboriginalDataForDealSet.add(tFIAboriginalDataForDealSet);
                tmap.put(tFIAboriginalDataForDealSet, "INSERT");

                // 一个采集的所有主键信息，保存到FIDataForDealDistilledInfo
                FIDataForDealDistilledInfoSet tFIDataForDealDistilledInfoSet = new
                        FIDataForDealDistilledInfoSet();
                // tFIDataForDealDistilledInfoSet =
                // putPKInfo(mFIAboriginalDataForDealSet,
                // tFIDataBaseLinkSchema);
                // mmap.put(tFIDataForDealDistilledInfoSet, "INSERT");
                // tInputData.add(mmap);
                // tFIDataForDealDistilledInfoSet =
                // putPKInfo(tFIAboriginalDataForDealSet, tFIDataBaseLinkSchema,
                // tmap);
                tFIDataForDealDistilledInfoSet = putPKInfo(
                        tFIAboriginalDataForDealSet, tFIDataBaseLinkSchema);
                if (tFIDataForDealDistilledInfoSet != null) {
                    tmap.put(tFIDataForDealDistilledInfoSet, "INSERT");
                    tInputData.add(tmap);
                    PubSubmit tPubSubmit = new PubSubmit();
                    if (tPubSubmit.submitData(tInputData, "")) {} else {
                        logger.debug("外地采集");
                    }
                    if (con != null) {
                        con.close();
                    }

                    getLinkDealDataBySql(sql, tFIDataBaseLinkSchema);

                } else {
                    this.mErrors = new CErrors();
                    buildError("getLinkDealDataBySql", "外连接数据保存失败！");
                    flag = false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean saveErrors(String ErrStage, String ErrType, String SerialNo,
                              String BatchNo, String BussDate) {
        String EeeSerialNo = PubFun1.CreateMaxNo("EeeSerialNo", 20);
        FIDataDealErrSchema tFIDataDealErrSchema = new FIDataDealErrSchema();
        tFIDataDealErrSchema.setAFSerialNo(SerialNo);
        tFIDataDealErrSchema.setBatchNo(BatchNo);
        tFIDataDealErrSchema.setBussDate(BussDate);
        tFIDataDealErrSchema.setEeeSerialNo(EeeSerialNo);
        tFIDataDealErrSchema.setMakeDate(PubFun.getCurrentDate());
        tFIDataDealErrSchema.setErrDealState("01");
        tFIDataDealErrSchema.setMakeTime(PubFun.getCurrentTime());
        return true;
    }

    public boolean getDealDataBySql(String sql) {
        boolean flag = true;
        VData tInputData = new VData();
        FIAboriginalDataForDealSet tFIAboriginalDataForDealSet = new
                FIAboriginalDataForDealSet();
        MMap tmap = new MMap();
        try {
            // mFIAboriginalDataForDealSet.clear();
            SSRS tSSRS = new SSRS();
            ExeSQL tExeSQL = new ExeSQL();
//			sql = "select * from (" + sql + ") where rownum <=" + mMaxDealNUm;
//			logger.debug("提取数据" + sql);
//			tSSRS = tExeSQL.execSQL(sql);
            
            String AddSql = "";
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            	AddSql = "select * from (" + sql + ") where rownum <=?mMaxDealNUm?";
            }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
             	AddSql = "select * from (" + sql + ") g limit 0,?mMaxDealNUm?";
            }
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(AddSql);
            sqlbv1.put("mMaxDealNUm", mMaxDealNUm);
            logger.debug("提取数据" + AddSql);
            tSSRS = tExeSQL.execSQL(sqlbv1);
            // 判断是否有记录如果没有记录则返回空
            if (tSSRS == null || tSSRS.getMaxRow() == 0) {
                flag = true;
            } else {
                for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
                    FIAboriginalDataForDealSchema
                            tFIAboriginalDataForDealSchema = new
                            FIAboriginalDataForDealSchema();
                    String SerialNo = PubFun1.CreateMaxNo("FISEDISTILLNO", 20);
                    tFIAboriginalDataForDealSchema.setSerialNo(SerialNo);
                    tFIAboriginalDataForDealSchema.setBatchNo(BatchNo);
                    tFIAboriginalDataForDealSchema.setAcquisitionID(
                            mFICostDataAcquisitionDefSchema.getAcquisitionID());
                    tFIAboriginalDataForDealSchema.setDataBaseID("A");
                    tFIAboriginalDataForDealSchema.setDataType(
                            mFICostDataAcquisitionDefSchema.getCostOrDataID());
                    tFIAboriginalDataForDealSchema.setDataState("00");
                    tFIAboriginalDataForDealSchema.setStringInfo01(tSSRS.
                            GetText(i, 1));
                    tFIAboriginalDataForDealSchema.setStringInfo02(tSSRS.
                            GetText(i, 2));
                    tFIAboriginalDataForDealSchema.setStringInfo03(tSSRS.
                            GetText(i, 3));
                    tFIAboriginalDataForDealSchema.setStringInfo04(tSSRS.
                            GetText(i, 4));
                    tFIAboriginalDataForDealSchema.setStringInfo05(tSSRS.
                            GetText(i, 5));
                    tFIAboriginalDataForDealSchema.setStringInfo06(tSSRS.
                            GetText(i, 6));
                    tFIAboriginalDataForDealSchema.setStringInfo07(tSSRS.
                            GetText(i, 7));
                    tFIAboriginalDataForDealSchema.setStringInfo08(tSSRS.
                            GetText(i, 8));
                    tFIAboriginalDataForDealSchema.setStringInfo09(tSSRS.
                            GetText(i, 9));
                    tFIAboriginalDataForDealSchema.setStringInfo10(tSSRS.
                            GetText(i, 10));
                    tFIAboriginalDataForDealSchema.setStringInfo11(tSSRS.
                            GetText(i, 11));
                    tFIAboriginalDataForDealSchema.setStringInfo12(tSSRS.
                            GetText(i, 12));
                    tFIAboriginalDataForDealSchema.setStringInfo13(tSSRS.
                            GetText(i, 13));
                    tFIAboriginalDataForDealSchema.setStringInfo14(tSSRS.
                            GetText(i, 14));
                    tFIAboriginalDataForDealSchema.setStringInfo15(tSSRS.
                            GetText(i, 15));
                    tFIAboriginalDataForDealSchema.setDateInfo01(tSSRS.GetText(
                            i, 16));
                    tFIAboriginalDataForDealSchema.setDateInfo02(tSSRS.GetText(
                            i, 17));
                    tFIAboriginalDataForDealSchema.setDateInfo03(tSSRS.GetText(
                            i, 18));
                    tFIAboriginalDataForDealSchema.setDateInfo04(tSSRS.GetText(
                            i, 19));
                    tFIAboriginalDataForDealSchema.setDateInfo05(tSSRS.GetText(
                            i, 20));
                    tFIAboriginalDataForDealSchema.setDateInfo06(tSSRS.GetText(
                            i, 21));
                    tFIAboriginalDataForDealSchema.setDateInfo07(tSSRS.GetText(
                            i, 22));
                    tFIAboriginalDataForDealSchema.setDateInfo08(tSSRS.GetText(
                            i, 23));
                    tFIAboriginalDataForDealSchema.setDateInfo09(tSSRS.GetText(
                            i, 24));
                    tFIAboriginalDataForDealSchema.setDateInfo10(tSSRS.GetText(
                            i, 25));
                    tFIAboriginalDataForDealSchema.setNumInfo01(tSSRS.GetText(i,
                            26));
                    tFIAboriginalDataForDealSchema.setNumInfo02(tSSRS.GetText(i,
                            27));
                    tFIAboriginalDataForDealSchema.setNumInfo03(tSSRS.GetText(i,
                            28));
                    tFIAboriginalDataForDealSchema.setNumInfo04(tSSRS.GetText(i,
                            29));
                    tFIAboriginalDataForDealSchema.setNumInfo05(tSSRS.GetText(i,
                            30));
                    tFIAboriginalDataForDealSchema.setBusinessNo01(tSSRS.
                            GetText(i, 31));
                    tFIAboriginalDataForDealSchema.setBusinessNo02(tSSRS.
                            GetText(i, 32));
                    tFIAboriginalDataForDealSchema.setBusinessNo03(tSSRS.
                            GetText(i, 33));
                    tFIAboriginalDataForDealSchema.setBusinessNo04(tSSRS.
                            GetText(i, 34));
                    tFIAboriginalDataForDealSchema.setBusinessNo05(tSSRS.
                            GetText(i, 35));
                    tFIAboriginalDataForDealSchema.setBusinessNo06(tSSRS.
                            GetText(i, 36));
                    tFIAboriginalDataForDealSchema.setBusinessNo07(tSSRS.
                            GetText(i, 37));
                    tFIAboriginalDataForDealSchema.setBusinessNo08(tSSRS.
                            GetText(i, 38));
                    tFIAboriginalDataForDealSchema.setBusinessNo09(tSSRS.
                            GetText(i, 39));
                    tFIAboriginalDataForDealSchema.setBusinessNo10(tSSRS.
                            GetText(i, 40));
                    tFIAboriginalDataForDealSchema.setTypeFlag01(tSSRS.GetText(
                            i, 41));
                    tFIAboriginalDataForDealSchema.setTypeFlag02(tSSRS.GetText(
                            i, 42));
                    tFIAboriginalDataForDealSchema.setTypeFlag03(tSSRS.GetText(
                            i, 43));
                    tFIAboriginalDataForDealSchema.setTypeFlag04(tSSRS.GetText(
                            i, 44));
                    tFIAboriginalDataForDealSchema.setTypeFlag05(tSSRS.GetText(
                            i, 45));
                    tFIAboriginalDataForDealSchema.setTypeFlag06(tSSRS.GetText(
                            i, 46));
                    tFIAboriginalDataForDealSchema.setTypeFlag07(tSSRS.GetText(
                            i, 47));
                    tFIAboriginalDataForDealSchema.setTypeFlag08(tSSRS.GetText(
                            i, 48));
                    tFIAboriginalDataForDealSchema.setTypeFlag09(tSSRS.GetText(
                            i, 49));
                    tFIAboriginalDataForDealSchema.setTypeFlag10(tSSRS.GetText(
                            i, 50));

                    // mFIAboriginalDataForDealSet.add(tFIAboriginalDataForDealSchema);
                    tFIAboriginalDataForDealSet.add(
                            tFIAboriginalDataForDealSchema);
                }

                // mmap.put(mFIAboriginalDataForDealSet, "INSERT");
                mFIAboriginalDataForDealSet.add(tFIAboriginalDataForDealSet);
                tmap.put(tFIAboriginalDataForDealSet, "INSERT");

                // 一个采集的所有主键信息，保存到FIDataForDealDistilledInfo
                FIDataForDealDistilledInfoSet tFIDataForDealDistilledInfoSet = new
                        FIDataForDealDistilledInfoSet();

                // tFIDataForDealDistilledInfoSet =
                // putPKInfo(mFIAboriginalDataForDealSet);
                // mmap.put(tFIDataForDealDistilledInfoSet, "INSERT");
                // tInputData.add(mmap);
                tFIDataForDealDistilledInfoSet = putPKInfo(
                        tFIAboriginalDataForDealSet);
                tmap.put(tFIDataForDealDistilledInfoSet, "INSERT");
                tInputData.add(tmap);
                PubSubmit tPubSubmit = new PubSubmit();
                if (tPubSubmit.submitData(tInputData, "")) {} else {
                    logger.debug("本地采集");
                }

                getDealDataBySql(sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }


    // 存业务主键表
    private FIDataForDealDistilledInfoSet putPKInfo(FIAboriginalDataForDealSet
            tFIAboriginalDataForDealSet) {
        FIDataForDealDistilledInfoSet tFIDataForDealDistilledInfoSet = new
                FIDataForDealDistilledInfoSet();
        FICostDataKeyDefSet tFICostDataKeyDefSet = new FICostDataKeyDefSet();
        FICostDataKeyDefDB tFICostDataKeyDefDB = new FICostDataKeyDefDB();
        String KeyUnionValue = "";
        // 查询主键信息表当前采集的相关的主键
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql("select * from FICostDataKeyDef where versionno ='?VersionNo?' and AcquisitionID='?AcquisitionID?'");
        sqlbv2.put("VersionNo", VersionNo);
        sqlbv2.put("AcquisitionID", mFICostDataAcquisitionDefSchema.getAcquisitionID());
        tFICostDataKeyDefSet = tFICostDataKeyDefDB.executeQuery(sqlbv2);
        for (int j = 0; j < tFIAboriginalDataForDealSet.size(); j++) {
            FIDataForDealDistilledInfoSchema tFIDataForDealDistilledInfoSchema = new
                    FIDataForDealDistilledInfoSchema();
            FIAboriginalDataForDealSchema tFIAboriginalDataForDealSchema =
                    tFIAboriginalDataForDealSet.get(j + 1);
            for (int i = 0; i < tFICostDataKeyDefSet.size(); i++) {
                if (i == 0) {
                    KeyUnionValue = tFIAboriginalDataForDealSchema.getV(
                            tFICostDataKeyDefSet.get(i + 1).getKeyID());
                } else {
                    KeyUnionValue = KeyUnionValue + ","
                                    +
                                    tFIAboriginalDataForDealSchema.getV(tFICostDataKeyDefSet.
                            get(i + 1).getKeyID());
                }
            }
            tFIDataForDealDistilledInfoSchema.setKeyUnionValue(KeyUnionValue);
            tFIDataForDealDistilledInfoSchema.setBatchNo(
                    tFIAboriginalDataForDealSchema.getBatchNo());
            tFIDataForDealDistilledInfoSchema.setAcquisitionID(
                    tFIAboriginalDataForDealSchema.getAcquisitionID());
            tFIDataForDealDistilledInfoSchema.setSerialNo(
                    tFIAboriginalDataForDealSchema.getSerialNo());
            tFIDataForDealDistilledInfoSchema.setBussDate("");
            tFIDataForDealDistilledInfoSchema.setMakeDate(PubFun.getCurrentDate());
            tFIDataForDealDistilledInfoSchema.setMakeTime(PubFun.getCurrentTime());
            tFIDataForDealDistilledInfoSchema.setBusinessNo(
                    tFIAboriginalDataForDealSchema.getBusinessNo01());
            tFIDataForDealDistilledInfoSchema.setBussDate("2008-08-05");
            tFIDataForDealDistilledInfoSet.add(
                    tFIDataForDealDistilledInfoSchema);
        }
        return tFIDataForDealDistilledInfoSet;
    }

    // 存业务主键表（外部连接用）
    private FIDataForDealDistilledInfoSet putPKInfo(FIAboriginalDataForDealSet
            tFIAboriginalDataForDealSet,
            FIDataBaseLinkSchema tFIDataBaseLinkSchema) {
        Connection con = getConnection(tFIDataBaseLinkSchema);
        FIDataForDealDistilledInfoSet tFIDataForDealDistilledInfoSet = new
                FIDataForDealDistilledInfoSet();
        // FIDataForDealDistilledInfoSchema tFIDataForDealDistilledInfoSchema =
        // new FIDataForDealDistilledInfoSchema();
        FIDataForDealDistilledInfoDBSet tFIDataForDealDistilledInfoDBSet = new
                FIDataForDealDistilledInfoDBSet(con);
        FICostDataKeyDefSet tFICostDataKeyDefSet = new FICostDataKeyDefSet();
        FICostDataKeyDefDB tFICostDataKeyDefDB = new FICostDataKeyDefDB();
        String KeyUnionValue = "";
        // 查询主键信息表当前采集的相关的主键
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql("select * from FICostDataKeyDef where versionno ='?VersionNo?' and AcquisitionID='?AcquisitionID?'");
        sqlbv3.put("VersionNo", VersionNo);
        sqlbv3.put("AcquisitionID", mFICostDataAcquisitionDefSchema.getAcquisitionID());
        tFICostDataKeyDefSet = tFICostDataKeyDefDB.executeQuery(sqlbv3);
        // 根据主键定义表循环获取业务接口表中的相关信息存入主键记录表
        for (int j = 0; j < tFIAboriginalDataForDealSet.size(); j++) {
            // FIDataForDealDistilledInfoSchema
            // tFIDataForDealDistilledInfoSchema = new
            // FIDataForDealDistilledInfoSchema();
            FIAboriginalDataForDealSchema tFIAboriginalDataForDealSchema =
                    tFIAboriginalDataForDealSet.get(j + 1);
            for (int i = 0; i < tFICostDataKeyDefSet.size(); i++) {
                if (i == 0) {
                    KeyUnionValue = tFIAboriginalDataForDealSchema.getV(
                            tFICostDataKeyDefSet.get(i + 1).getKeyID());
                } else {
                    KeyUnionValue = KeyUnionValue + ","
                                    +
                                    tFIAboriginalDataForDealSchema.getV(tFICostDataKeyDefSet.
                            get(i + 1).getKeyID());
                }
            }
            FIDataForDealDistilledInfoSchema tFIDataForDealDistilledInfoSchema = new
                    FIDataForDealDistilledInfoSchema();
            tFIDataForDealDistilledInfoSchema.setKeyUnionValue(KeyUnionValue);
            tFIDataForDealDistilledInfoSchema.setBatchNo(
                    tFIAboriginalDataForDealSchema.getBatchNo());
            tFIDataForDealDistilledInfoSchema.setAcquisitionID(
                    tFIAboriginalDataForDealSchema.getAcquisitionID());
            tFIDataForDealDistilledInfoSchema.setSerialNo(
                    tFIAboriginalDataForDealSchema.getSerialNo());
            tFIDataForDealDistilledInfoSchema.setMakeDate(PubFun.getCurrentDate());
            tFIDataForDealDistilledInfoSchema.setMakeTime(PubFun.getCurrentTime());
            tFIDataForDealDistilledInfoSchema.setBusinessNo(
                    tFIAboriginalDataForDealSchema.getStringInfo03());
            tFIDataForDealDistilledInfoSchema.setBussDate(tFIAboriginalDataForDealSchema.getDateInfo01());
            // 为了插入不同库
            tFIDataForDealDistilledInfoDBSet.add(
                    tFIDataForDealDistilledInfoSchema);
            // tMap.put(tFIDataForDealDistilledInfoSet, "INSERT");
            tFIDataForDealDistilledInfoSet.add(
                    tFIDataForDealDistilledInfoSchema);
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        // tMap.put(tFIDataForDealDistilledInfoDBSet, "INSERT");
        if (!tFIDataForDealDistilledInfoDBSet.insert()) {
            logger.debug("主键保存到外连接表出现问题！");
            tFIDataForDealDistilledInfoSet = null;
        }
        return tFIDataForDealDistilledInfoSet;
    }

    // 获得本地链接，并提数
    private boolean putLocalData() {
        // mFIAboriginalDataForDealSet.clear();
        String tDistillMode = mFICostDataAcquisitionDefSchema.getDistillMode();

        // VData tInputData = new VData();
        if (tDistillMode.equals("1")) {
            PubCalculator tPubCalculator = new PubCalculator();
            tPubCalculator.addBasicFactor("ManageCom", mGlobalInput.ManageCom);
            tPubCalculator.addBasicFactor("StartDate", StartDate);
            tPubCalculator.addBasicFactor("EndDate", EndDate);
            String sql = mFICostDataAcquisitionDefSchema.getDistillSQL();
            if (sql == null || sql.equals("")) {
                logger.debug("DistillSQL没有定义");
                return false;
            }
            tPubCalculator.setCalSql(sql);
            sql = tPubCalculator.calculateEx();
            // 得到提数的结果，对应接口表
            getDealDataBySql(sql);
            // mmap.put(mFIAboriginalDataForDealSet, "INSERT");
            // // 一个采集的所有主键信息，保存到FIDataForDealDistilledInfo
            // FIDataForDealDistilledInfoSet tFIDataForDealDistilledInfoSet =
            // new FIDataForDealDistilledInfoSet();
            //
            // tFIDataForDealDistilledInfoSet =
            // putPKInfo(mFIAboriginalDataForDealSet);
            // mmap.put(tFIDataForDealDistilledInfoSet, "INSERT");
            // tInputData.add(mmap);
            // PubSubmit tPubSubmit = new PubSubmit();
            // if (tPubSubmit.submitData(tInputData, ""))
            // {}
            // else
            // {
            // logger.debug("本地采集");
            // }
        } else if (tDistillMode.equals("2")) {
            try {
                MMap tMap = new MMap();
                FIAboriginalDataForDealSet tFIAboriginalDataForDealSet = new
                        FIAboriginalDataForDealSet();
                Class tClass = Class.forName(mFICostDataAcquisitionDefSchema.
                                             getDistillClass());
                DistillTypeSE mDistillTypeSEClass = (DistillTypeSE) tClass.
                        newInstance();
                VData sVData = new VData();
                sVData.add(mGlobalInput);
                sVData.add(StartDate);
                sVData.add(EndDate);
                sVData.add(BatchNo);
                sVData.add(VersionNo);
                // FIAboriginalDataForDealSet tFIAboriginalDataForDealSet = new
                // FIAboriginalDataForDealSet();
                tFIAboriginalDataForDealSet = mDistillTypeSEClass.DitillInfo(
                        sVData);
                // mmap.put(tFIAboriginalDataForDealSet, "INSERT");
                tMap.put(tFIAboriginalDataForDealSet, "INSERT");
                VData tInputData = new VData();
                // tInputData.add(mmap);
                tInputData.add(tMap);
                PubSubmit tPubSubmit = new PubSubmit();
                if (tPubSubmit.submitData(tInputData, "")) {} else {
                    logger.debug("本地采集");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    // 获得外部链接，并提数
    private boolean putLinkData() {
        // mFIAboriginalDataForDealSet.clear();
        String tDistillMode = mFICostDataAcquisitionDefSchema.getDistillMode();
        VData tInputData = new VData();
        // 一个采集号查询有几个数据源
        FICostDataBaseDefDB tFICostDataBaseDefDB = new FICostDataBaseDefDB();
        FICostDataBaseDefSet tFICostDataBaseDefSet = new FICostDataBaseDefSet();
        tFICostDataBaseDefDB.setVersionNo(VersionNo);
        tFICostDataBaseDefDB.setAcquisitionID(mFICostDataAcquisitionDefSchema.
                                              getAcquisitionID());
        tFICostDataBaseDefSet = tFICostDataBaseDefDB.query();
        // 循环数据源
        for (int i = 0; i < tFICostDataBaseDefSet.size(); i++) {
            FICostDataBaseDefSchema tFICostDataBaseDefSchema =
                    tFICostDataBaseDefSet.get(i + 1);
            FIDataBaseLinkDB tFIDataBaseLinkDB = new FIDataBaseLinkDB();
            tFIDataBaseLinkDB.setInterfaceCode(tFICostDataBaseDefSchema.
                                               getDataBaseID());
            // 查询每个数据源对应的参数表信息
            if (!tFIDataBaseLinkDB.getInfo()) {
                logger.debug("数据源链接表没有定义");
                return false;
            }
            String cycDate = this.StartDate;
            while (cycDate.compareTo(this.EndDate) <= 0) {
                if (tDistillMode.equals("1")) {
                    PubCalculator tPubCalculator = new PubCalculator();
                    tPubCalculator.addBasicFactor("ManageCom",
                                                  mGlobalInput.ManageCom);
                    tPubCalculator.addBasicFactor("StartDate", StartDate);
                    tPubCalculator.addBasicFactor("EndDate", EndDate);
                    String sql = mFICostDataAcquisitionDefSchema.getDistillSQL();
                    if (sql == null || sql.equals("")) {
                        logger.debug("DistillSQL没有定义");
                        return false;
                    }
                    tPubCalculator.setCalSql(sql);
                    sql = tPubCalculator.calculateEx();
                    // 得到提数的结果，对应接口表
                    // getLinkDealDataBySql(sql, tFIDataBaseLinkDB.getSchema());
                    if (!getLinkDealDataBySql(sql, tFIDataBaseLinkDB.getSchema())) {
                        return false;
                    }

                    // mmap.put(mFIAboriginalDataForDealSet, "INSERT");
                    // // 一个采集的所有主键信息，保存到FIDataForDealDistilledInfo
                    // FIDataForDealDistilledInfoSet tFIDataForDealDistilledInfoSet
                    // = new FIDataForDealDistilledInfoSet();
                    // tFIDataForDealDistilledInfoSet =
                    // putPKInfo(mFIAboriginalDataForDealSet,
                    // tFIDataBaseLinkDB.getSchema());
                    // mmap.put(tFIDataForDealDistilledInfoSet, "INSERT");
                    //
                    // tInputData.add(mmap);
                    // PubSubmit tPubSubmit = new PubSubmit();
                    // if (tPubSubmit.submitData(tInputData, ""))
                    // {}
                    // else
                    // {
                    // logger.debug("外地采集");
                    // }
                } else if (tDistillMode.equals("2")) {
                    try {
                        MMap tMap = new MMap();
                        FIAboriginalDataForDealSet tFIAboriginalDataForDealSet = new
                                FIAboriginalDataForDealSet();
                        Class tClass = Class.forName(
                                mFICostDataAcquisitionDefSchema.getDistillClass());
                        DistillTypeSE mDistillTypeSEClass = (DistillTypeSE)
                                tClass.newInstance();
                        VData sVData = new VData();
                        sVData.add(mGlobalInput);
                        sVData.add(StartDate);
                        sVData.add(EndDate);
                        sVData.add(BatchNo);
                        sVData.add(VersionNo);
                        // mFIAboriginalDataForDealSet =
                        // mDistillTypeSEClass.DitillInfo(sVData);
                        // mmap.put(mFIAboriginalDataForDealSet, "INSERT");
                        // tInputData.add(mmap);
                        tFIAboriginalDataForDealSet = mDistillTypeSEClass.
                                DitillInfo(sVData);
                        tMap.put(tFIAboriginalDataForDealSet, "INSERT");
                        tInputData.add(tMap);
                        PubSubmit tPubSubmit = new PubSubmit();
                        if (tPubSubmit.submitData(tInputData, "")) {} else {
                            logger.debug("外地采集");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                cycDate = PubFun.calDate(cycDate, 1, "D", null);
            }
        }
        return true;
    }

    private boolean DealWithData() {
        try {
//			if (!putLocalData())
//			{
//				logger.debug("采集本地数据出现问题！");
//				return false;
//			}
            if (!putLinkData()) {
                logger.debug("采集外部数据出现问题！");
                return false;
            }
            return true;
        } catch (Exception ex) {
            buildError("DealWithData", ex.getMessage());
            return false;
        }
    }

    private boolean WriteLog(String tFlag) {
        return true;
        // try
        // {
        // DataToSubmit tDataToSubmit = new DataToSubmit();
        // VData lInputData = new VData();
        // LIDistillLogSet tLIDistillLogSet = new LIDistillLogSet();
        // LIDistillLogSchema tLIDistillLogSchema = new LIDistillLogSchema();
        // tLIDistillLogSchema.setBatchNo(BatchNo);
        // tLIDistillLogSchema.setStartDate(StartDate);
        // tLIDistillLogSchema.setEndDate(EndDate);
        // tLIDistillLogSchema.setFlag(tFlag);
        // tLIDistillLogSchema.setMakeDate(PubFun.getCurrentDate());
        // tLIDistillLogSchema.setMakeTime(PubFun.getCurrentTime());
        // tLIDistillLogSchema.setManageCom(mGlobalInput.ManageCom);
        // tLIDistillLogSchema.setOperator(mGlobalInput.Operator);
        // tLIDistillLogSet.add(tLIDistillLogSchema);
        // lInputData.add(tLIDistillLogSet);
        // if(!tDataToSubmit.submitData(lInputData,""))
        // {
        // buildError("WriteLog","记录提数日志出错！");
        // return false;
        // }
        //
        // return true;
        // }
        // catch (Exception ex)
        // {
        // buildError("WriteLog",ex.getMessage());
        // return false;
        // }
    }

    public boolean WriteErrLog() {
        return true;
        // try
        // {
        // if(this.mErrors.needDealError())
        // {
        // DataToSubmit tDataToSubmit = new DataToSubmit();
        // VData lInputData = new VData();
        // LIDistillLogSet tLIDistillLogSet = new LIDistillLogSet();
        // LIDistillLogSchema tLIDistillLogSchema = new LIDistillLogSchema();
        // tLIDistillLogSchema.setBatchNo(BatchNo);
        // tLIDistillLogSchema.setStartDate(StartDate);
        // tLIDistillLogSchema.setEndDate(EndDate);
        // tLIDistillLogSchema.setFlag("Lose");
        // tLIDistillLogSchema.setFilePath(this.mErrors.getFirstError());
        // tLIDistillLogSchema.setMakeDate(PubFun.getCurrentDate());
        // tLIDistillLogSchema.setMakeTime(PubFun.getCurrentTime());
        // tLIDistillLogSchema.setManageCom(mGlobalInput.ManageCom);
        // tLIDistillLgSchema.setOperator(mGlobalInput.Operator);
        // tLIDistillLogSet.add(tLIDistillLogSchema);
        // lInputData.add(tLIDistillLogSet);
        // if (!tDataToSubmit.submitData(lInputData, "")) {
        // this.mErrors = new CErrors();
        // buildError("WriteErrLog",
        // "记录提数日志出错！"+tDataToSubmit.mErrors.getFirstError());
        // return false;
        // }
        // return true;
        // }
        // else
        // {
        // return true;
        // }
        //
        // }
        // catch (Exception ex)
        // {
        // this.mErrors = new CErrors();
        // buildError("WriteLog",ex.getMessage());
        // return false;
        // }
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "FISEDistillDealAquisition";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        logger.debug(szErrMsg);
    }

}
