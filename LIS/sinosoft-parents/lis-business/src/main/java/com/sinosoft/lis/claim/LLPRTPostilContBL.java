package com.sinosoft.lis.claim;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.f1print.AccessVtsFile;
import com.sinosoft.lis.f1print.CombineVts;
import com.sinosoft.lis.f1print.FileQueue;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.f1print.VtsFileCombine;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：批单--合同处理批注--PCT002,ContrCorrectC00030.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTPostilContBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTPostilContBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private MMap mMMap = new MMap();

    private TransferData mTransferData = new TransferData();

    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //客户号
    private String tWebPath="";         //模板路径
    private String mPrtSeq = "" ;       //流水号

    private LLPRTPubFunBL mLLPRTPubFunBL = new LLPRTPubFunBL();
    private XmlExportNew xmlExportAll = new XmlExportNew();
    private GlobalInput tGI;

    public LLPRTPostilContBL(){}

    public static void main(String[] args)
    {
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("PrtSeq", "0000013998");
        tTransferData.setNameAndValue("Path", "E:lis/ui/f1print/NCLtemplate/");

        double d = 0;
        String s = "0";
//        String sd = new DecimalFormat("0.00").format(s);

        VData tVData = new VData();
         tVData.add(tTransferData);
        LLPRTPostilContBL tLLPRTPostilContBL = new LLPRTPostilContBL();
        if(tLLPRTPostilContBL.submitData(tVData,"")==false)
        {
            logger.debug("--------合同打印失败------------");
        }

    }


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------批单--合同处理批注-----LLPRTPostilCont测试-----开始----------");

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }

        if (!dealData())
        {
            return false;
        }

        if (!prepareOutputData())
        {
            return false;
        }

        if (!pubSubmit())
        {
            return false;
        }

        logger.debug("----------批单--合同处理批注-----LLPRTPostilCont测试-----结束----------");
        return true;
    }


    /**
     * 取传入参数信息
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {
        this.mInputData = cInputData;
        tGI = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput",0);
        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
        this.tWebPath = (String) mTransferData.getValueByName("Path");
        if(tGI == null)//批量打印使用
        {
            tGI = new GlobalInput();
            tGI.ComCode = "86";
            tGI.ManageCom = "86";
            tGI.Operator = "001";
        }
        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        //模板路径
        String strTemplatePath = tWebPath;

        //取得赔案号
        String tSQL = "select OtherNo from LOPRTManager where "
                           + " PrtSeq='" + "?PrtSeq?" + "'" ;
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(tSQL);
        sqlbv.put("PrtSeq", mPrtSeq);
        ExeSQL tExeSQL = new ExeSQL() ;
        mClmNo = tExeSQL.getOneValue(sqlbv);
        if (mClmNo == null || mClmNo.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTPostilContBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询赔案号失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //取得客户号
        tSQL = "select b.customerno from llcase b where "
               + " caseno='" + "?clmno?" + "'" ;
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(tSQL);
        sqlbv1.put("clmno", mClmNo);
        mCusNo = tExeSQL.getOneValue(sqlbv1);
        if (mCusNo == null || mCusNo.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTPostilContBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询出险人号码失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //取得合同号并作循环处理
        String pageSQL = "select distinct contno from llcontstate where "
                         + " clmno='" + "?clmno?" + "'" ;
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(pageSQL);
        sqlbv2.put("clmno", mClmNo);
        ExeSQL es = new ExeSQL();
        SSRS ssrs = es.execSQL(sqlbv2);

        int page = 0;
        page = ssrs.getMaxRow() ;
        if(page==0)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTPostilContBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "没有查询到该赔案处理的合同号!";
            mErrors.addOneError(tError);
            return false;
        }

        logger.debug("-------共涉及合同数为:" + page + "---------------");
        String[] strVFPathName = new String[page];
//        xmlExportAll.createDocuments("printer",this.tGI);
//        xmlExportAll.setTemplateName("ContrCorrectC00030.vts");
        xmlExportAll.createDocument("批单-合同处理批注");
        
        for(int p = 0;p < page ;p ++)
        {
            String mContNo =  ssrs.GetText(p + 1, 1);
            logger.debug("-------合同号---" + mContNo + "处理如下:-------");

            TextTag tTextTag = new TextTag(); //新建一个TextTag的实例
            XmlExportNew tXmlExport = new XmlExportNew(); //新建一个XmlExport的实例
//            tXmlExport.createDocument("ContrCorrectC00030.vts", "");
            tXmlExport.createDocument("批单-合同处理批注");

            //理赔类型---------------------------------------------------------------
            String ClaimType = mLLPRTPubFunBL.getSLLAppClaimReason(mClmNo, mCusNo);

            //出险人姓名-------------------------------------------------------------
            String tSql = "select a.name from ldperson a where "
                          + "a.customerno = '" + "?cusno?" + "'";
            SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
            sqlbv3.sql(tSql);
            sqlbv3.put("cusno", mCusNo);
            String tName = tExeSQL.getOneValue(sqlbv3);

            //投保人和被保险人姓名-----------------------------------------------------
            String nameSQL = "select * from lccont where "
                             + "contno = '" + "?contno?" + "'";
            SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
            sqlbv4.sql(nameSQL);
            sqlbv4.put("contno", mContNo);
            LCContDB tLCContDB = new LCContDB();
            LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv4);
            String appntName = tLCContSet.get(1).getAppntName();
            String insuredName = tLCContSet.get(1).getInsuredName();

            //系统时间---------------------------------------------------------------
            String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                             StrTool.getDay() + "日";

            //合同处理结论------------------------------------------------------------
            tSql = "select (select c.codename from ldcode c where "
                   + "c.codetype = 'llcontdealtype' and "
                   + "trim(c.code)=trim(b.dealstate)) from llcontstate b "
                   + "where b.clmno = '" + "?clmno?" + "' "
                   + "and b.contno = '" + "?contno?" + "'";
            SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
            sqlbv5.sql(tSql);
            sqlbv5.put("clmno", mClmNo);
            sqlbv5.put("contno", mContNo);
            String displayConclusion = tExeSQL.getOneValue(sqlbv5);
            if (displayConclusion == null || displayConclusion.equals(""))
            {
                displayConclusion = "险种终止"; //为空时置为此值
            }

            //***添加单条字段
            tTextTag.add("CLNo", mClmNo); //陪案号
            tTextTag.add("LCPol.MainPolNo", mContNo); //保单号
            tTextTag.add("ClType", ClaimType); //理赔类型
            tTextTag.add("RiskerName", tName); //出险人
            tTextTag.add("LCCont.AppntName", appntName); //投保人
            tTextTag.add("LCCont.InsuredName", insuredName); //被保险人
            tTextTag.add("Date", SysDate); //批注日期
            tTextTag.add("displayConclusion",displayConclusion);//合同处理结论

            //判断是否显示退费项目#####################################################
            String tfSQL = "select count(1) from llbalance where "
                           + " contno='" + "?contno?" + "'"
                           + " and clmno='" + "?clmno?" + "'"
                           + " and FeeFinaType in('TF','TB','EF','CB')";
            SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
            sqlbv6.sql(tfSQL);
            sqlbv6.put("clmno", mClmNo);
            sqlbv6.put("contno", mContNo);
            ExeSQL tfExeSQL = new ExeSQL();
            int tfcount = 0;
            tfcount = Integer.parseInt(tfExeSQL.getOneValue(sqlbv6));
            if (tfcount > 0)
            {
                tXmlExport.addDisplayControl("displayResult6");
            }

            //退还保费金额(出险前)--------------------------------------------------
            String fee1SQL = "select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                             + " and clmno='" + "?clmno?" + "'"
                             + " and FeeOperationType='D01'" //业务类型
                             + " and SubFeeOperationType='D0101'" //子业务类型
                             + " and FeeFinaType='TF'" //财务类型
                             + " and polno = (select distinct(b.mainpolno) from lcpol b where b.contno = '" +"?contno?" + "')" //只汇总主险
                             ;
            SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
            sqlbv7.sql(fee1SQL);
            sqlbv7.put("contno", mContNo);
            sqlbv7.put("clmno", mClmNo);
            ExeSQL fee1ExeSQL = new ExeSQL();
            String fee1 = fee1ExeSQL.getOneValue(sqlbv7);
            if (fee1 != null)
            {
                fee1 = new DecimalFormat("0.00").format(Double.parseDouble(fee1));
                if (!fee1.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult7");
                    tTextTag.add("Fee1", fee1);
                }
            }

            //基本保额应退金额(现金价值)--------------------------------------------
            String fee2SQL = "select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where "
                             + " contno = '" + "?contno?" + "'"
                             + " and clmno = '" + "?clmno?" + "'"
                             + " and FeeOperationType = 'D02'" //业务类型
                             + " and SubFeeOperationType = 'D0201'" //子业务类型
                             + " and FeeFinaType = 'TB'"; //财务类型
            SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
            sqlbv8.sql(fee2SQL);
            sqlbv8.put("contno", mContNo);
            sqlbv8.put("clmno", mClmNo);
            ExeSQL fee2ExeSQL = new ExeSQL();
            String fee2 = fee2ExeSQL.getOneValue(sqlbv8);
            if (fee2 != null)
            {
                fee2 = new DecimalFormat("0.00").format(Double.parseDouble(fee2));
                if (!fee2.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult8");
                    tTextTag.add("Fee2", fee2);
                }
            }

            //累计红利保额应退金额--------------------------------------------------
            String fee3SQL = "select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                             + " and clmno='" + "?clmno?" + "'"
                             + " and FeeOperationType='C05'" //业务类型
                             + " and SubFeeOperationType='C0501'" //子业务类型
                             + " and FeeFinaType='EF' "; //财务类型
            SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
            sqlbv9.sql(fee3SQL);
            sqlbv9.put("contno", mContNo);
            sqlbv9.put("clmno", mClmNo);
            ExeSQL fee3ExeSQL = new ExeSQL();
            String fee3 = fee3ExeSQL.getOneValue(sqlbv9);
            if (fee3 != null)
            {
                fee3 = new DecimalFormat("0.00").format(Double.parseDouble(fee3));
                if (!fee3.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult9");
                    tTextTag.add("Fee3", fee3);
                }
            }

            //判断终了红利---------------------------------------------------------
            String fee4SQL = "select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                             + " and clmno='" + "?clmno?" + "'"
                             + " and FeeOperationType = 'C05'" //业务类型
                             + " and SubFeeOperationType = 'C0502'" //子业务类型
                             + " and FeeFinaType = 'EF' "; //财务类型
            SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
            sqlbv10.sql(fee4SQL);
            sqlbv10.put("contno", mContNo);
            sqlbv10.put("clmno", mClmNo);
            ExeSQL fee4ExeSQL = new ExeSQL();
            String fee4 = fee4ExeSQL.getOneValue(sqlbv10);
            if (fee4 != null)
            {
                fee4 = new DecimalFormat("0.00").format(Double.parseDouble(fee4));
                if (!fee4.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult10");
                    tTextTag.add("Fee4", fee4);
                }
            }

            //附加险费用-----------------------------------------------------------2005-9-21 16:02 by zl
            String tSSQL = "select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end),polno,substr(riskcode,3,3) from llbalance where "
                             + " contno='" + "?contno?" + "'"
                             + " and clmno='" + "?clmno?" + "'"
                             + " and FeeFinaType in('TF','TB')" //财务类型
                             + " group by polno,riskcode";
            SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
            sqlbv11.sql(tSSQL);
            sqlbv11.put("contno", mContNo);
            sqlbv11.put("clmno", mClmNo);
            SSRS tSSRS = tExeSQL.execSQL(sqlbv11);
            String tSn = "select distinct mainpolno from lcpol where "
                         + " contno = '" + "?contno?" + "'";
            SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
            sqlbv12.sql(tSn);
            sqlbv12.put("contno", mContNo);
            String tMpolno = tExeSQL.getOneValue(sqlbv12);

            int n = 0;
            int i = 4;
            int j = 10;
            for (int k = 1; k <= tSSRS.getMaxRow(); k++)
            {
                String tFEE = tSSRS.GetText(k,1);
                if (tFEE != null)
                {
                    tFEE = new DecimalFormat("0.00").format(Double.parseDouble(
                            tFEE));
                }
                String tPOLNO = tSSRS.GetText(k,2);
                String tRiskCode = "("+tSSRS.GetText(k,3)+")";
                if (!tFEE.equals("0") && !tPOLNO.equals(tMpolno))
                {
                    n++;
                    i++;
                    j++;
                    tXmlExport.addDisplayControl("displayResult"+String.valueOf(j));
                    tTextTag.add("RiskName"+String.valueOf(n), tRiskCode);
                    tTextTag.add("Fee"+String.valueOf(i), tFEE);
                }
                if (n > 2)
                {
                    break;
                }
            }

            //判断多收收保费退还----------------------------------------------------
            String fee8SQL = "select (case when sum(case when pay is null then 0 ELSE pay END) is null then 0 else sum(case when pay is null then 0 ELSE pay END) end) from llbalance where "
                             + " contno = '" + "?contno?" + "'"
                             + " and clmno = '" + "?clmno?" + "'"
                             + " and ((FeeOperationType = 'C01'" //业务类型
                             + " and SubFeeOperationType = 'C0101'" //子业务类型
                             + " and FeeFinaType = 'TF')" //财务类型
                             + " or (FeeOperationType = 'D01'" //业务类型
                             + " and SubFeeOperationType = 'D0102'" //子业务类型
                             + " and FeeFinaType = 'TF'))"
                             + " and polno = (select distinct(b.mainpolno) from lcpol b where b.contno = '" +"?contno?" + "')" //只汇总主险
                             ;
            SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
            sqlbv13.sql(fee8SQL);
            sqlbv13.put("contno", mContNo);
            sqlbv13.put("clmno", mClmNo);
            ExeSQL fee8ExeSQL = new ExeSQL();
            String fee8 = fee8ExeSQL.getOneValue(sqlbv13);
            if (fee8 != null)
            {
                fee8 = new DecimalFormat("0.00").format(Double.parseDouble(fee8));
                if (!fee8.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult14");
                    tTextTag.add("Fee8", fee8);
                }
            }

            //判断利差返还---------------------------------------------------------
            String fee18SQL = "select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                              + " and clmno='" + "?clmno?" + "'"
                              + " and FeeOperationType = 'C06'" //业务类型
                              + " and SubFeeOperationType = 'C0601'" //子业务类型
                              + " and FeeFinaType = 'CB' "; //财务类型
            SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
            sqlbv14.sql(fee18SQL);
            sqlbv14.put("contno", mContNo);
            sqlbv14.put("clmno", mClmNo);
            ExeSQL fee18ExeSQL = new ExeSQL();
            String fee18 = fee18ExeSQL.getOneValue(sqlbv14);
            if (fee18 != null)
            {
                fee18 = new DecimalFormat("0.00").format(Double.parseDouble(
                        fee18));
                if (!fee18.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult25");
                    tTextTag.add("Fee18", fee18);
                }
            }
            //判断是否显示扣费项目#####################################################
            String kfSQL = "select count(1) from llbalance "
                           + "where contno='" + "?contno?" + "'"
                           + " and clmno='" + "?clmno?" + "'"
                           + " and FeeFinaType in('BF','LX','HK')";
            SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
            sqlbv15.sql(kfSQL);
            sqlbv15.put("contno", mContNo);
            sqlbv15.put("clmno", mClmNo);
            ExeSQL kfExeSQL = new ExeSQL();
            int kfcount = 0;
            kfcount = Integer.parseInt(kfExeSQL.getOneValue(sqlbv15));
            if (kfcount > 0)
            {
                tXmlExport.addDisplayControl("displayResult15");
            }

            //欠缴保费及利息--------------------------------------------------------2005-10-10 11:25
            String fee19SQL = "select abs(case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                             + " and clmno='" + "?clmno?" + "'"
                             + " and FeeOperationType='C02'" //业务类型
//                             + " and SubFeeOperationType='C0201'" //子业务类型
//                             + " and FeeFinaType='BF' " //财务类型
                             ;
            SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
            sqlbv16.sql(fee19SQL);
            sqlbv16.put("contno", mContNo);
            sqlbv16.put("clmno", mClmNo);
            ExeSQL fee19ExeSQL = new ExeSQL();
            String fee19 = fee19ExeSQL.getOneValue(sqlbv16);
            if (fee19 != null)
            {
                fee19 = new DecimalFormat("0.00").format(Double.parseDouble(
                        fee19));
                if (!fee19.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult26");
                    tTextTag.add("Fee19", fee19);
                }
            }

            //判断自垫本金----------------------------------------------------------
            String fee9SQL = "select abs(case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                             + " and clmno='" + "?clmno?" + "'"
                             + " and FeeOperationType='C07'" //业务类型
                             + " and SubFeeOperationType='C0701'" //子业务类型
                             + " and FeeFinaType='BF' "; //财务类型
            SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
            sqlbv17.sql(fee9SQL);
            sqlbv17.put("contno", mContNo);
            sqlbv17.put("clmno", mClmNo);
            ExeSQL fee9ExeSQL = new ExeSQL();
            String fee9 = fee9ExeSQL.getOneValue(sqlbv17);
            if (fee9 != null)
            {
                fee9 = new DecimalFormat("0.00").format(Double.parseDouble(fee9));
                if (!fee9.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult16");
                    tTextTag.add("Fee9", fee9);
                }
            }

            //判断自垫利息----------------------------------------------------------
            String fee10SQL = "select abs(case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                              + " and clmno='" + "?clmno?" + "'"
                              + " and FeeOperationType='C07'" //业务类型
                              + " and SubFeeOperationType='C0702'" //子业务类型
                              + " and FeeFinaType='LX' "; //财务类型
            SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
            sqlbv18.sql(fee10SQL);
            sqlbv18.put("contno", mContNo);
            sqlbv18.put("clmno", mClmNo);
            ExeSQL fee10ExeSQL = new ExeSQL();
            String fee10 = fee10ExeSQL.getOneValue(sqlbv18);
            if (fee10 != null)
            {
                fee10 = new DecimalFormat("0.00").format(Double.parseDouble(
                        fee10));
                if (!fee10.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult17");
                    tTextTag.add("Fee10", fee10);
                }
            }

            //判断保单质押贷款本金---------------------------------------------------
            String fee11SQL = "select abs(case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                              + " and clmno='" + "?clmno?" + "'"
                              + " and FeeOperationType='C03'" //业务类型
                              + " and SubFeeOperationType='C0301'" //子业务类型
                              + " and FeeFinaType='HK' "; //财务类型
            SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
            sqlbv19.sql(fee11SQL);
            sqlbv19.put("contno", mContNo);
            sqlbv19.put("clmno", mClmNo);
            ExeSQL fee11ExeSQL = new ExeSQL();
            String fee11 = fee11ExeSQL.getOneValue(sqlbv19);
            if (fee11 != null)
            {
                fee11 = new DecimalFormat("0.00").format(Double.parseDouble(
                        fee11));
                if (!fee11.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult18");
                    tTextTag.add("Fee11", fee11);
                }
            }

            //判断保单质押贷款利息---------------------------------------------------
            String fee12SQL = "select abs(case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
            				  + " and clmno='" + "?clmno?" + "'"
                              + " and FeeOperationType='C03'" //业务类型
                              + " and SubFeeOperationType='C0302'" //子业务类型
                       	      + " and FeeFinaType='LX' "; //财务类型
            SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
            sqlbv20.sql(fee12SQL);
            sqlbv20.put("contno", mContNo);
            sqlbv20.put("clmno", mClmNo);
            ExeSQL fee12ExeSQL = new ExeSQL();
            String fee12 = fee12ExeSQL.getOneValue(sqlbv20);
            if (fee12 != null)
            {
                fee12 = new DecimalFormat("0.00").format(Double.parseDouble(
                        fee12));
                if (!fee12.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult19");
                    tTextTag.add("Fee12", fee12);
                }
            }

            //理赔事故发生后超额给付年金(生存金,养老金)--------------------------------
            String fee13SQL = "select abs(case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
  				  			  + " and clmno='" + "?clmno?" + "'"
                              + " and FeeOperationType='C04'" //业务类型
                              + " and SubFeeOperationType='C0401'" //子业务类型
                              + " and FeeFinaType='EF' "; //财务类型
            SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
            sqlbv21.sql(fee13SQL);
            sqlbv21.put("contno", mContNo);
            sqlbv21.put("clmno", mClmNo);
            ExeSQL fee13ExeSQL = new ExeSQL();
            String fee13 = fee13ExeSQL.getOneValue(sqlbv21);
            if (fee13 != null)
            {
                fee13 = new DecimalFormat("0.00").format(Double.parseDouble(
                        fee13));
                if (!fee13.equals("0.00"))
                {
                    tXmlExport.addDisplayControl("displayResult20");
                    tTextTag.add("Fee13", fee13);
                }
            }

            //帐户管理费用?????????????????????

            //实退金额合计------------------------------------------------------------
            String fee17SQL = "select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where contno='" +"?contno?" + "'"
                              + " and clmno='" + "?clmno?" + "'"
                              + " and FeeOperationType not in ('A','B')";
            SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
            sqlbv22.sql(fee17SQL);
            sqlbv22.put("contno", mContNo);
            sqlbv22.put("clmno", mClmNo);
            ExeSQL fee17ExeSQL = new ExeSQL();
            String fee17 = fee17ExeSQL.getOneValue(sqlbv22);
            if (fee17 != null)
            {
                fee17 = new DecimalFormat("0.00").format(Double.parseDouble(
                        fee17));
            }
            tXmlExport.addDisplayControl("displayResult24");
            tTextTag.add("Fee17", fee17);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No1.0 加入单个字段的打印变量
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (tTextTag.size() > 0) {
                tXmlExport.addTextTag(tTextTag);
            }
            xmlExportAll.addDataSet(tXmlExport.getDocument().getRootElement());
            CombineVts tcombineVts = null;

           tcombineVts = new CombineVts(tXmlExport.getInputStream(),strTemplatePath);

            ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            tcombineVts.output(dataStream);

            strVFPathName[p] = strTemplatePath+ p + "ContrCorrectC00030.vts";
            //把dataStream存储到磁盘文件
            //logger.debug("存储文件到"+strVFPathName);
            AccessVtsFile.saveToFile(dataStream, strVFPathName[p]);
            logger.debug("####################################");
            logger.debug("###########分模板保存成功###########");
            logger.debug("####################################");
        }
        VtsFileCombine vtsfilecombine = new VtsFileCombine();
        BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
        FileQueue tFileQueue = new FileQueue();
        //把原来写死的new.vts文件名,改成动态随机生成,niuzj2005-09-23
        String t= tFileQueue.getFileName();
        String allname = strTemplatePath + tGI.Operator + t + ".vts";
        try
        {
            //vtsfilecombine.write(tb, strTemplatePath+ "new.vts");
            vtsfilecombine.write(tb, allname);
        }
        catch (IOException ex)
        {
        }
        catch (F1Exception ex)
        {
        }

        String remark = "new.vts";
        mResult.clear();
        //mResult.addElement(strTemplatePath + "new.vts");
        mResult.addElement(allname);
        mResult.addElement(xmlExportAll);
        mResult.add(mTransferData);
        //把后台动态生成的文件名返回前台
        String b=(String) mResult.getObjectByObjectName("String",0);
        logger.debug("********************"+b);
        return true;

    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        mInputData.clear();
        mInputData.add(mMMap);
        return true;
    }

    /**
     * 提交数据
     * @return
     */
    private boolean pubSubmit()
    {
        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTPostilContBL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }
    public CErrors getErrors()
    {
        return mErrors;
    }

    public VData getResult()
    {
        return mResult;
    }

}

