package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.PrintService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：鉴定提示通知--PCT002,CjjdtssC000160.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTAppraisalBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTAppraisalBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private MMap mMMap = new MMap();
    private TransferData mTransferData = new TransferData();
    private String mPrtSeq = "" ;
    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //客户号




    public LLPRTAppraisalBL()
    {

    }

    /*
    **************加入数据，用于调试********
    */
    public static void main(String[] args)
    {
            GlobalInput tG = new GlobalInput();
            tG.ComCode="86";
            tG.ManageCom="86";
            tG.Operator="001";
        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("ClmNo", "ClmNo");
//        tTransferData.setNameAndValue("PrtCode", "PCT008");
        tTransferData.setNameAndValue("PrtSeq", "0000003313");

    // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add( tG );
        tVData.add( tTransferData );
        LLPRTAppraisalBL  tLLPRTAppraisalBL = new LLPRTAppraisalBL();
        if (tLLPRTAppraisalBL.submitData(tVData,"") == false)
        {
            int n = tLLPRTAppraisalBL.mErrors.getErrorCount();
            logger.debug("###############################");
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

        logger.debug("----------鉴定提示通知-----LLPRTAppraisal测试-----开始----------");

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

        logger.debug("----------鉴定提示通知-----LLPRTAppraisal测试-----结束----------");
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
        //根据打印管理表的流水号查出赔案号和出险人客户号(暂时假定为单一出险人)
        String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"+mPrtSeq+"'" ;
        ExeSQL clmExeSQL = new ExeSQL() ;
        String mClmNo = clmExeSQL.getOneValue(mClmNoSQL);
        String mCusNoSQL = "select distinct CustomerNo from LLSubReport where SubRptNo='"+mClmNo+"'";
        ExeSQL cusExeSQL = new ExeSQL() ;
        SSRS cusNoSSRS = new SSRS();
        cusNoSSRS =cusExeSQL.execSQL(mCusNoSQL);
        if(cusNoSSRS.MaxRow==0)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTAppraisalBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询出险人客户号失败!";
            mErrors.addOneError(tError);
            return false;
        }

//        for(int i=1;i<=cusNoSSRS.getMaxRow();i++){}
        String mCusNo = cusNoSSRS.GetText(1,1) ;

        TextTag tTextTag = new TextTag(); //新建一个TextTag的实例
        XmlExport tXmlExport = new XmlExport(); //新建一个XmlExport的实例
        tXmlExport.createDocument("CjjdtssC000160.vts", "");
        LLPRTPubFunBL tLLPRTPubFunBL= new LLPRTPubFunBL();
        //理赔类型---------------------------------------------------------------
        String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo,mCusNo);

        //出险人信息--------------------------------------------------------------
        String tSql = "select * from ldperson a where "
                    + "a.customerno = '" + mCusNo + "'";
        LDPersonDB tLDPersonDB = new LDPersonDB();
        LDPersonSet tLDPersonSet = tLDPersonDB.executeQuery(tSql);
        if(tLDPersonSet.size()==0)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTAppraisalBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询出险人信息失败!";
            mErrors.addOneError(tError);
            return false;
        }

        String tName = tLDPersonSet.get(1).getName();
        String sex ="";
        if(tLDPersonSet.get(1).getSex().equals("0"))
        {
            sex = "先生" ;
        }
        else if(tLDPersonSet.get(1).getSex().equals("1"))
        {
            sex = "女士" ;
        }
        String ttSql = "select * from lcaddress where CustomerNo='"+mCusNo+"'" ;
        LCAddressDB tLCAddressDB = new LCAddressDB() ;
        LCAddressSet tLCAddressSet = tLCAddressDB.executeQuery(ttSql) ;
        if(tLCAddressSet.get(1)!=null)
        {
            String zipCode = tLCAddressSet.get(1).getZipCode();
            String postalAddress = tLCAddressSet.get(1).getPostalAddress() ;
        }
        //出险日期---------------------------------------------------------------
        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
        tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(mClmNo);
        String accDate = "";
        accDate = tLLAccidentSchema.getAccDate();
        //保单服务人员姓名、业务号和电话---------------------------------------------
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
//        String checkerSQL = "select UserName from llclaimuser "
//                 +"where usercode='"+operator+"'";
//       ExeSQL cExeSQL = new ExeSQL();
//       String jingbanren = cExeSQL.getOneValue(checkerSQL);
       LLClaimUserSchema tLLClaimUserSchema = new LLClaimUserSchema();
       tLLClaimUserSchema=tLLPRTPubFunBL.getLLClaimUser(operator);
       if(tLLClaimUserSchema==null)
       {
           CError tError = new CError();
           tError.moduleName = "LLPRTAppraisalBL";
           tError.functionName = "dealdata";
           tError.errorMessage = "查询经办人信息失败!";
           mErrors.addOneError(tError);
           return false;
       }
       String jingbanren =tLLClaimUserSchema.getUserName();
       String tel2 =tLLClaimUserSchema.getRelPhone() ;//经办人电话
        //公司------------------------------------------------------------------
        String mngCom="";
        mngCom = ttLLSubReportSchema.getMngCom() ;
        String comSQL = "select a.name,a.phone from LDCom a where ComCode='"+mngCom+"'";
        ExeSQL comExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS =comExeSQL.execSQL(comSQL);
        String company = tSSRS.GetText(1,1);//根据管理机构代码查出名称

        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";
        tTextTag.add("ClaimNo",mClmNo);//陪案号
        tTextTag.add("ClaimType",ClaimType);//理赔类型
        tTextTag.add("AccName",tName);//出险人
        tTextTag.add("AccDate",accDate);//出险日期
        tTextTag.add("ClaimName",tName);//问候人
        tTextTag.add("Sex",sex);//问候人性别
        tTextTag.add("ServiceName",SeveiceName);//保单服务人员
        tTextTag.add("Tel",tel);//联系电话
        tTextTag.add("LPAppnt.Operator",jingbanren);//经办人
        tTextTag.add("SysDate",SysDate);//通知日期
        //tTextTag.add("ManageComName",company);//
        //tTextTag.add("ZipCode","邮编："+zipCode);
        //tTextTag.add("Address",postalAddress);
        //tTextTag.add("Name",tName);
        
        tTextTag.add("Tel2",tel2);//经办人联系电话
        //tTextTag.add("Dep",company);//部门名称
        ListTable tListTable = new ListTable();
        tListTable.setName("CL");
        String[] Title = new String[1];
        Title[0] = "" ;

        //鉴定机构列表------------------------------------------------------------
        LLCommendHospitalDB tLLCommendHospitalDB = new LLCommendHospitalDB();
        String affixSQL = "select * from LLCommendHospital where 1=1"
                          +" and AppFlag ='0' and HosState='0' and MngCom='"+mngCom+"'";
        LLCommendHospitalSet tLLCommendHospitalSet = tLLCommendHospitalDB.executeQuery(affixSQL);

        for(int i = 1; i <= tLLCommendHospitalSet.size(); i++)
        {
            String[] stra= new String[1];
            stra[0] = i+"."+tLLCommendHospitalSet.get(i).getHospitalName();
            tListTable.add(stra);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 加入单个字段的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (tTextTag.size() > 0)
        {
            tXmlExport.addTextTag(tTextTag);
        }

        tXmlExport.addListTable(tListTable, Title);//保存主险附加险信息

        mResult.clear();
        mResult.addElement(tXmlExport);
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

