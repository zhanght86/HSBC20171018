package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import com.sinosoft.lis.f1print.*;
import com.sinosoft.lis.db.LAAgentDB;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：理赔单证通知书--PCT002,LpdztzsC000110.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTCertificatePutOutBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTCertificatePutOutBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private MMap mMMap = new MMap();
    private TransferData mTransferData = new TransferData();
    private String mPrtSeq = "" ;       //流水号
    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //客户号

    public LLPRTCertificatePutOutBL(){}


    public static void main(String[] args)
    {
        GlobalInput tG = new GlobalInput();
        tG.ComCode = "86";
        tG.ManageCom = "86";
        tG.Operator = "001";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("PrtSeq", "0000002835");

        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tG);
        tVData.add(tTransferData);
        LLPRTCertificatePutOutBL tLLPRTCertificatePutOutBL = new
                LLPRTCertificatePutOutBL();
        if (tLLPRTCertificatePutOutBL.submitData(tVData, "") == false)
        {
            logger.debug("#######----单证通知书打印出错----##############");
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

        logger.debug("----------理赔单证通知书-----LLPRTCertificatePutOutBL测试-----开始----------");

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

        logger.debug("----------理赔单证通知书-----LLPRTCertificatePutOutBL测试-----结束----------");
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

        String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"+mPrtSeq+"'" ;
        ExeSQL clmExeSQL = new ExeSQL();
        mClmNo = clmExeSQL.getOneValue(mClmNoSQL);
        String mCusNoSQL ="select distinct CustomerNo from LLSubReport where SubRptNo='" +mClmNo + "'";
        ExeSQL cusExeSQL = new ExeSQL();
        SSRS cusNoSSRS = new SSRS();
        cusNoSSRS = cusExeSQL.execSQL(mCusNoSQL);
    //        for(int i=1;i<=cusNoSSRS.getMaxRow();i++){}
        mCusNo = cusNoSSRS.GetText(1,1) ;

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 定义打印
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        TextTag tTextTag = new TextTag(); //新建一个TextTag的实例
        XmlExport tXmlExport = new XmlExport(); //新建一个XmlExport的实例
        tXmlExport.createDocument("LpdztzsC000110.vts", "");
        LLPRTPubFunBL tLLPRTPubFunBL= new LLPRTPubFunBL();
        LLReportAffixSet tLLReportAffixSet = tLLPRTPubFunBL.getLLReportAffix(mClmNo,mCusNo);
        //理赔类型---------------------------------------------------------------
        String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo,mCusNo);
        String sex="";
        if(tLLPRTPubFunBL.getCustSex(mCusNo).equals("男"))
        {
            sex ="先生";
        }
        else
        {
            sex ="女士";
        }

        //出险原因---------------------------------------------------------------
        String reason = tLLPRTPubFunBL.getLLReport(mClmNo).getAccidentReason();
        String tSQL = "select codename from ldcode where code='"+reason+"' and codetype='lloccurreason'";
        ExeSQL tExeSQL1 = new ExeSQL();
        String claimRsn = tExeSQL1.getOneValue(tSQL);

        //判断是否死亡案件 true---是，false--否
//        if(tLLPRTPubFunBL.isDeadClaim(mClmNo)==true)
//        {
//            //
//        }
//        else
//        {
//            //
//        }

        //出险人姓名--------------------------------------------------------------
        String tSql = "select a.name from ldperson a where "
                    + "a.customerno = '" + mCusNo + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String tName = tExeSQL.getOneValue(tSql);

        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
        tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(mClmNo);
        //出险时间---------------------------------------------------------------
        String claimTime = "";
        claimTime = tLLAccidentSchema.getAccDate();
        //保单服务人员和电话-()------------------------------------------------------
//        tLAAgentSchema = tLLPRTPubFunBL.getLAAgent("01",mCusNo);

        String tAgentCodeSql="select AgentCode from lccont where 1=1 "
                          +" and insuredno='"+mCusNo+"' order by CValiDate desc";
        ExeSQL tAgentCodeExeSQL = new ExeSQL();
        String tAgentCode = tAgentCodeExeSQL.getOneValue(tAgentCodeSql);
        String SeveiceName="";
        String transNo = "";
        String tel = "";
        if(tAgentCode.equals("") ||tAgentCode==null)
        {
             SeveiceName="";
             transNo = "";
             tel = "";
        }
        else
        {
            LAAgentSchema tLAAgentSchema = new LAAgentSchema();
            LAAgentDB tLAAgentDB = new LAAgentDB();
            tLAAgentDB.setAgentCode(tAgentCode);
            tLAAgentDB.getInfo();
            tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
             SeveiceName = tLAAgentSchema.getName();
             transNo = tLAAgentSchema.getAgentCode();
             tel = tLAAgentSchema.getPhone();
        }

        //经办人-----------------------------------------------------------------
        String operator="";
        LLSubReportSchema ttLLSubReportSchema = new LLSubReportSchema();
        ttLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(mClmNo,mCusNo);
        operator=ttLLSubReportSchema.getOperator();
        String checkerSQL = "select UserName from llclaimuser "
                         +"where usercode='"+operator+"'";
        ExeSQL cExeSQL = new ExeSQL() ;
        String jingbanren = cExeSQL.getOneValue(checkerSQL);


        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";
        tTextTag.add("ClaimNo",mClmNo);//陪案号
        tTextTag.add("ClaimType",ClaimType);//理赔类型
        tTextTag.add("ClaimRsn",claimRsn);//出险原因
        tTextTag.add("AccName",tName);//出险人
        tTextTag.add("ClaimTime",claimTime);//出险时间
        tTextTag.add("ClaimName",tName);//问候人
        tTextTag.add("Sex",sex);//问候人称谓
        tTextTag.add("ServiceName",SeveiceName);//保单服务人员
        tTextTag.add("TransNo",transNo);//业务号
        tTextTag.add("Tel",tel);//联系电话
        tTextTag.add("LPAppnt.Operator",jingbanren);//经办人
        tTextTag.add("SysDate",SysDate);//通知日期
        LLReportSchema tLLReportSchema = new LLReportSchema();
        tLLReportSchema = tLLPRTPubFunBL.getLLReport(mClmNo) ;
        String mngCom = tLLReportSchema.getMngCom() ;
        String comSQL = "select a.name,a.Phone from LDCom a where ComCode='"+mngCom+"'";
        ExeSQL comExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS = comExeSQL.execSQL(comSQL) ;
        String dep = tSSRS.GetText(1,1);//根据管理机构代码查出名称
//        String tel2 = tSSRS.GetText(1,2) ;
//        tTextTag.add("Company",dep);
        LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
        tLLClaimUserSchema = tLLPRTPubFunBL.getLLClaimUser(operator);
        String tel2 = tLLClaimUserSchema.getRelPhone(); //经办人电话
        tTextTag.add("Tel2",tel2);

        ListTable tListTable = new ListTable();
        tListTable.setName("CL");
        String[] Title = new String[1];
        Title[0] = "" ;

        for(int i = 1; i <= tLLReportAffixSet.size(); i++)
        {
            String[] stra= new String[1];
            stra[0] = i+"."+tLLReportAffixSet.get(i).getAffixName();
            tListTable.add(stra);
            logger.debug("i"+i);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 加入单个字段的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (tTextTag.size() > 0)
        {
            tXmlExport.addTextTag(tTextTag);
        }

        tXmlExport.addListTable(tListTable, Title);
        logger.debug("cccc");

        tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
        mResult.clear();
        mResult.addElement(tXmlExport);
        mResult.add(mTransferData);

        logger.debug("xmlexport=" + tXmlExport);

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

