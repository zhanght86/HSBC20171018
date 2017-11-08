package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.*;
import java.util.*;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.claimgrp.SynLCLBPolBL;
import com.sinosoft.lis.claimgrp.SynLCLBDutyBL;
import com.sinosoft.lis.claimgrp.SynLCLBGetBL;
import com.sinosoft.lis.f1print.PrintService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：理赔决定通知书(不予立案)--PCT007,LpjdtzsBylaPacmC000150.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTProtestNoRegisterBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTProtestNoRegisterBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();
    private TransferData mTransferData = new TransferData();
    private String mPrtSeq = "" ;       //流水号
    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //客户号

    public LLPRTProtestNoRegisterBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------不予立案单证入口-----LLPRTProtestNoRegister测试-----开始----------");

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

        logger.debug("----------不予立案单证入口-----LLPRTProtestNoRegister测试-----结束----------");
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
//        this.mClmNo = (String) mTransferData.getValueByName("ClmNo");    //赔案号
//        this.mCusNo = (String) mTransferData.getValueByName("CustNo");   //意外事故发生日期
        this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"+mPrtSeq+"'" ;
        ExeSQL clmExeSQL = new ExeSQL() ;
        mClmNo = clmExeSQL.getOneValue(mClmNoSQL);
        String mCusNoSQL = "select CustomerNo from LLcase where CaseNo='"+mClmNo+"'";
        ExeSQL cusExeSQL = new ExeSQL() ;
        SSRS cusNoSSRS = new SSRS();
        cusNoSSRS =cusExeSQL.execSQL(mCusNoSQL);
//        for(int i=1;i<=cusNoSSRS.getMaxRow();i++){}
        mCusNo = cusNoSSRS.GetText(1,1) ;
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 定义打印
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        TextTag tTextTag = new TextTag(); //新建一个TextTag的实例
        XmlExport tXmlExport = new XmlExport(); //新建一个XmlExport的实例
        tXmlExport.createDocument("LpjdtzsBylaPacmC000150.vts", "");
        LLPRTPubFunBL tLLPRTPubFunBL= new LLPRTPubFunBL();
        //理赔类型
        String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo,mCusNo);

        //出险人姓名
        String tSql = "select a.name from ldperson a where "
                    + "a.customerno = '" + mCusNo + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String tName = tExeSQL.getOneValue(tSql);

        //理赔决定日期和不立案原因
        String endCaseDate="";//决定日期
        String noRgtReasonFlag = "";//不立案原因代码
        String noRgtReason = "" ;//不立案原因
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
        endCaseDate = tLLRegisterSchema.getEndCaseDate();
        noRgtReasonFlag = tLLRegisterSchema.getNoRgtReason();
        //根据noRgtReasonFlag从ldcode表中查出相对应的名字
        String reasonSQL = "select a.codename from ldcode a where codetype='llnorgtreason' and code='"+noRgtReasonFlag+"'";
        ExeSQL reasonExeSQL1 = new ExeSQL();
        noRgtReason = reasonExeSQL1.getOneValue(reasonSQL);
        //理赔结论
        ListTable tListTable = new ListTable();
        tListTable.setName("LPJD");
        String[] Title = new String[3];
        Title[0] = "" ;
        Title[1] = "" ;
        Title[2] = "" ;

        //收信人(申请人)邮编ZipCode、地址Address、姓名Name、性别、管理机构 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查
        String zipcode = "" ;
        String address = "" ;
        String rgtname = "" ;
        //String sexFlag = "" ;
        //String sex = "" ;
        String mngCom = "" ; //管理机构编码
        zipcode = "邮编: "+tLLRegisterSchema.getPostCode();
        address = tLLRegisterSchema.getRgtantAddress();
        rgtname = tLLRegisterSchema.getRgtantName();
//        sexFlag = tLLRegisterSchema.getRgtantSex();
//        if(sexFlag.equals("0"))
//        {
//            sex = "男";
//        }
//        else if(sexFlag.equals("1"))
//        {
//            sex = "女";
//        }
//        else
//        {
//            sex = "" ;
//        }
        mngCom = tLLRegisterSchema.getMngCom() ;
        String comSQL = "select a.name,a.phone from LDCom a where ComCode='"+mngCom+"'";
        ExeSQL comExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS =comExeSQL.execSQL(comSQL);
        String company = "人寿保险股份有限公司" ;
        String dep = tSSRS.GetText(1,1);//根据管理机构代码查出名称
        String tel = tSSRS.GetText(1,2) ;
        //系统时间
        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";

        tTextTag.add("IndemnityNo",mClmNo);//赔案号
        tTextTag.add("IndemnityType",ClaimType);//理赔类型
        tTextTag.add("InsuredName",tName);//出险人
        tTextTag.add("LCCont.AppntName",rgtname);//申请人
        //tTextTag.add("Sex",sex);//申请人称谓
        tTextTag.add("SysDate",SysDate);//通知日期
        //tTextTag.add("ZipCode",zipcode);//申请人邮编
        //tTextTag.add("Address",address);//申请人地址
        //tTextTag.add("Name",rgtname);//申请人姓名
        //tTextTag.add("CompanyName",company);//管理机构
        //目前表中没有dep字段 部门和电话写死
        //tTextTag.add("Dep",dep);
        //tTextTag.add("Tel",tel);

        String[] stra= new String[3];
        stra[0] = "不予立案";
        stra[1] = endCaseDate;
        stra[2] = noRgtReason;
        tListTable.add(stra);

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

        tXmlExport.addListTable(tListTable, Title);
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

