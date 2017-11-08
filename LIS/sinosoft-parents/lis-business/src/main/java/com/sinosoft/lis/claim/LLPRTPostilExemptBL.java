package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import com.sinosoft.lis.f1print.PrintService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：批单-豁免保费批注 -- PCT002,HmFeeCorreC00040.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTPostilExemptBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTPostilExemptBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private MMap mMMap = new MMap();
    private TransferData mTransferData = new TransferData();
    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //客户号
    private String mPrtSeq = "" ;       //流水号
    public LLPRTPostilExemptBL(){}


    public static void main(String[] args)
    {
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("PrtSeq", "0000001312");
        tTransferData.setNameAndValue("Path", "D:/ui/f1print/NCLtemplate/");

        VData tVData = new VData();
        tVData.add(tTransferData);
        LLPRTPostilExemptBL tLLPRTPostilExemptBL = new LLPRTPostilExemptBL();
        if (tLLPRTPostilExemptBL.submitData(tVData, "") == false) {
            logger.debug("--------保费豁免打印失败------------");
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

        logger.debug("----------批单-豁免保费批注-----LLPRTPostilExemptBL测试-----开始----------");

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

        logger.debug("----------批单-豁免保费批注-----LLPRTPostilExemptBL测试-----结束----------");
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

        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
//        this.mClmNo = (String) mTransferData.getValueByName("ClmNo");    //赔案号
//        this.mCusNo = (String) mTransferData.getValueByName("CustNo");   //出险人客户号
        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"+"?PrtSeq?"+"'" ;
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(mClmNoSQL);
        sqlbv.put("PrtSeq", mPrtSeq);
        ExeSQL clmExeSQL = new ExeSQL() ;
        mClmNo = clmExeSQL.getOneValue(sqlbv);
        String mCusNoSQL = "select CustomerNo from LLcase where CaseNo='"+"?clmno?"+"'";
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        sqlbv1.sql(mCusNoSQL);
        sqlbv1.put("clmno", mClmNo);
        ExeSQL cusExeSQL = new ExeSQL() ;
        SSRS cusNoSSRS = new SSRS();
        cusNoSSRS =cusExeSQL.execSQL(sqlbv1);
//        for(int i=1;i<=cusNoSSRS.getMaxRow();i++){}
        mCusNo = cusNoSSRS.GetText(1,1) ;
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 定义打印
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        TextTag tTextTag = new TextTag(); //新建一个TextTag的实例
        XmlExportNew tXmlExport = new XmlExportNew(); //新建一个XmlExport的实例
//        tXmlExport.createDocument("HmFeeCorreC00040.vts", "");
        tXmlExport.createDocument("批单-豁免保费批注");
        LLPRTPubFunBL tLLPRTPubFunBL= new LLPRTPubFunBL();
        //理赔类型
        String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo,mCusNo);

        //出险人姓名
        String nameSql = "select a.name from ldperson a where "
                    + "a.customerno = '" + "?cusno?" + "'";
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(nameSql);
        sqlbv2.put("cusno", mCusNo);
        ExeSQL tExeSQL = new ExeSQL();
        String tName = tExeSQL.getOneValue(sqlbv2);

        //申请人 姓名Name 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
        String rgtname = "" ;
        rgtname = tLLRegisterSchema.getRgtantName();

        //系统时间
        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";

        //批单审核信息
        LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
        tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(mClmNo);

        //----------------------------------------------------------------------
        tTextTag.add("ClNo",mClmNo);//赔案号
        tTextTag.add("ClType",ClaimType);//理赔类型
        tTextTag.add("RiskerName",tName);//出险人
        tTextTag.add("AppName",rgtname);//申请人
        //审核人编码 -- tLLClaimUWMainSchema.getAuditPer()
        String checkerSQL = "select UserName from llclaimuser "
                         +"where usercode='"+"?usercode?"+"'";
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql(checkerSQL);
        sqlbv3.put("usercode", tLLClaimUWMainSchema.getAuditPer());
        ExeSQL cExeSQL = new ExeSQL() ;
        String checker = cExeSQL.getOneValue(sqlbv3);
        //审批人编码 -- tLLClaimUWMainSchema.getExamPer()
        String signerSQL = "select UserName from llclaimuser "
                         +"where usercode='"+"?usercode?"+"'";
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
        sqlbv4.sql(signerSQL);
        sqlbv4.put("usercode", tLLClaimUWMainSchema.getExamPer());
        ExeSQL sExeSQL = new ExeSQL() ;
        String signer = sExeSQL.getOneValue(sqlbv4);

        tTextTag.add("Checker",checker);//审核人
        tTextTag.add("Signer",signer);//审批人
        tTextTag.add("Date",tLLClaimUWMainSchema.getExamDate());//审批日期

        //合同号号、理赔金额、剩余有效保额信息 循环输出
        //首先查出LLClaimDetail表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环
        String contSQL = "" ;
        contSQL = "select * from lccont where contno in (select distinct contno from llclaimdetail where clmno='"+"?clmno?"+"' and givetype='0')";
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        sqlbv5.sql(contSQL);
        sqlbv5.put("clmno", mClmNo);
        //logger.debug(contSQL);
        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv5);

        ListTable tListTable = new ListTable();
        tListTable.setName("INSHRISK1");

        String[] Title = new String[3];
        Title[0] = "";
        Title[1] = "";
        Title[2] = "";

        for (int i=1;i<=tLCContSet.size();i++)
        {
            //取得保单号(合同号)
            String contNo = tLCContSet.get(i).getContNo();

            String[] contStr = new String[3];
            contStr[0] = "保单号: "+contNo;//页面上显示为保单号
            contStr[1] = "";
            contStr[2] = "";
            tListTable.add(contStr);

            String[] tSStr = new String[3];
            tSStr[0] = "保险责任";
            tSStr[1] = "豁免期间";
            tSStr[2] = "豁免金额";
            tListTable.add(tSStr);

            //保项
            String bxSQL = "";
            bxSQL = "select * from LLExempt where clmno='"+"?clmno?"+"' and contno='"+"?contno?"+"' "
                    +" and FreeFlag='1' and ExemptPeriod is not null";
            logger.debug(bxSQL);
            SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
            sqlbv6.sql(bxSQL);
            sqlbv6.put("clmno", mClmNo);
            sqlbv6.put("contno", contNo);
            LLExemptDB bxLLExemptDB = new LLExemptDB();
            LLExemptSet bxLLExemptSet = bxLLExemptDB.executeQuery(sqlbv6);
            for(int j=1;j<=bxLLExemptSet.size();j++)//内层循环保项的数目
            {
                //根据LLExempt中的 DutyCode ClmNo PolNo 查llclaimdetail 中的相应的GetDutyCode集合
                //再根据GetDutyCode(i) 和 GetDutyKind(i) 从 LMDutyGetClm中查保项名称
                //LLClaimDetail中理赔类型为109--意外豁免，209--疾病豁免的赔案
                String lmSQL = "";
                lmSQL = "select * from llclaimdetail where clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"'"
                        +" and dutycode='"+"?dutycode?"+"'"
                        +" and getdutykind in('109','209')";
                SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
                sqlbv7.sql(lmSQL);
                sqlbv7.put("clmno", mClmNo);
                sqlbv7.put("polno", bxLLExemptSet.get(j).getPolNo());
                sqlbv7.put("dutycode", bxLLExemptSet.get(j).getDutyCode());
                LLClaimDetailDB lmLLClaimDetailDB = new LLClaimDetailDB();
                LLClaimDetailSet lmLLClaimDetailSet = lmLLClaimDetailDB.executeQuery(sqlbv7);

                //===修复豁免作为独立责任的查询(如607)===2006-6-19 11:52===周磊============beg
                String bxName = ""; //保项名称
                String bxNameSQL = "";
                ExeSQL bxnameExeSQL = new ExeSQL();
                if (lmLLClaimDetailSet.size() != 0)
                {
                    bxNameSQL =
                            "select GetDutyName from lmdutygetclm where getdutycode='" +
                            "?getdutycode?" + "'"
                            + " and getdutykind='" +
                            "?getdutykind?" +
                            "'";
                    SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
                    sqlbv8.sql(bxNameSQL);
                    sqlbv8.put("getdutycode", lmLLClaimDetailSet.get(1).getGetDutyCode());
                    sqlbv8.put("getdutykind", lmLLClaimDetailSet.get(1).getGetDutyKind());
                    bxName = bxnameExeSQL.getOneValue(sqlbv8);
                }
                else
                {
                    bxNameSQL =
                            "select DutyName from LMDuty where "
                            + "dutycode='" + "?dutycode?"
                            + "'";
                    SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
                    sqlbv9.sql(bxNameSQL);
                    sqlbv9.put("dutycode", bxLLExemptSet.get(j).getDutyCode());
                    bxName = bxnameExeSQL.getOneValue(sqlbv9);
                }
                //===修复豁免作为独立责任的查询(如607)===2006-6-19 11:52===周磊============end

                String[] stra = new String[3];
//                stra[0] = "保项"+j+": "+bxName;//保项名称
                stra[0] = bxName;//保项名称
                //豁免开始日期-豁免结束日期(免交起期-免交止期)
                stra[1] = bxLLExemptSet.get(j).getFreeStartDate()+" - "+bxLLExemptSet.get(j).getFreeEndDate();
                //豁免期数
                double exemptPeriod= Double.parseDouble(bxLLExemptSet.get(j).getExemptPeriod()) ;
                stra[2] = String.valueOf(bxLLExemptSet.get(j).getStandPrem()* exemptPeriod) ;//豁免期数*每期保费
                tListTable.add(stra);//将保项信息加入ListTable
            }
        }
        tXmlExport.addListTable(tListTable,Title);
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 加入单个字段的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (tTextTag.size() > 0)
        {
            tXmlExport.addTextTag(tTextTag);
        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 加入多行数据的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        xmlexport.addDisplayControl("Risk"); //模版上的主险附加险部分的控制标记

    //    tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
        mResult.clear();
        mResult.addElement(tXmlExport);
        mResult.add(mTransferData);
        logger.debug("xmlexport=" + tXmlExport);
        
	    String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT012'";
	    logger.debug("更新打印管理表的sql:"+updateStateSql);
	    SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
        sqlbv10.sql(updateStateSql);
        sqlbv10.put("clmno", mClmNo);
	    mMMap.put(sqlbv10, "UPDATE");

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
            tError.moduleName = "LLClaimCalCheckBL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            //
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

