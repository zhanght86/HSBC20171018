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
import com.sinosoft.lis.claimgrp.LLPRTPubFunBL;
import com.sinosoft.lis.f1print.PrintService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：理赔决定通知书--PCT005,LpjdtzsJfPacmC000140.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author zhaoy，2005.08.01
 * @version 1.0
 */
public class LLPRTProtestClaimBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTProtestClaimBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private MMap mMMap = new MMap();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();

    private String mPrtCode  = "PCT005"	;	//打印编码
    private String mClmNo    = "";      	//赔案号
    private String mCusNo    = "";      	//客户号
    private String mPrtSeq = "" ;       //流水号



    public LLPRTProtestClaimBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }

        if (!dealData())
        {
            return false;
        }
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

        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);

        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
        //this.mClmNo = (String) mTransferData.getValueByName("ClmNo");    //赔案号
        //this.mCusNo = (String) mTransferData.getValueByName("CustNo");   //出险人编号
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
        if(cusNoSSRS.MaxRow==0)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTProtestClaimBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询出险人客户号失败!";
            mErrors.addOneError(tError);
            return false;
        }
//        for(int i=1;i<=cusNoSSRS.getMaxRow();i++){}
        mCusNo = cusNoSSRS.GetText(1,1) ;
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 定义打印
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
          TextTag texttag = new TextTag(); //新建一个TextTag的实例
          XmlExport tXmlExport = new XmlExport(); //新建一个XmlExport的实例
          tXmlExport.createDocument("LpjdtzsJfPacmC000140.vts", "");

          String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                   StrTool.getDay() + "日";
                          //赔案号
          texttag.add("IndemnityNo", mClmNo);

         //出险人
         String tSql = "select a.name from ldperson a where "
                       + "a.customerno = '" + mCusNo + "'";
         ExeSQL tExeSQL = new ExeSQL();
         String strCusName = tExeSQL.getOneValue(tSql);
         texttag.add("InsuredName", strCusName);

         //理赔类型
         LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
         String strAppClaimReason = tLLPRTPubFunBL.getSLLAppClaimReason(mClmNo,
                 mCusNo);
         texttag.add("IndemnityType", strAppClaimReason);

         //涉及保单号
         LCContSet tLCContSet = new LCContSet();
         String strContno = "";
         tLCContSet = tLLPRTPubFunBL.getClmLCCont(mClmNo);
         for (int i = 1; i <= tLCContSet.size(); i++)
         {
             if (i != 1)
             {
                 strContno = strContno + "、";
             }
             strContno = strContno + tLCContSet.get(i).getContNo();
         }
         texttag.add("ConNoList", strContno);

         //申请人、收信人信息
         LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
         tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
         if(tLLRegisterSchema==null)
         {
             CError tError = new CError();
             tError.moduleName = "LLPRTProtestClaimBL";
             tError.functionName = "dealdata";
             tError.errorMessage = "查询申请人信息失败!";
             mErrors.addOneError(tError);
             return false;
         }
         String rgtname = tLLRegisterSchema.getRgtantName();//申请人姓名
         String RgtantSex=tLLRegisterSchema.getRgtantSex();//申请人性别
         String zipcode = "邮编: " + tLLRegisterSchema.getPostCode();
         String address = tLLRegisterSchema.getRgtantAddress();
         String mngCom = tLLRegisterSchema.getMngCom();

         String AppntName="";
         if(RgtantSex==null)
         {
             AppntName = rgtname;
         }
         else
         {
             if(RgtantSex.equals("男")||RgtantSex.equals("0"))
             {
                 AppntName = rgtname + "先生";
             }
             else if (RgtantSex.equals("女") || RgtantSex.equals("1"))
             {
                 AppntName = rgtname + "女士";
             }
             else
             {
                 AppntName = rgtname;
             }
         }

         texttag.add("LCCont.AppntName", AppntName); //收信人


         //理赔决定
         String[] DSTitle = new String[3];
         DSTitle[0] = "";
         DSTitle[1] = "";
         DSTitle[2] = "";
//         DSTitle[3] = "";
         ListTable tDSListTable = new ListTable();
         tDSListTable.setName("LPJD");
         LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();

         tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(mClmNo);
         String[] strDS = new String[3];
         strDS[0] = "整案拒付";
//         strDS[1] = tLLClaimUWMainSchema.getAuditDate(); //决定日期

         String strcode = tLLClaimUWMainSchema.getAuditNoPassReason(); //原因
         String tSql1 = "select a.codename from ldcode a where "
                        + "a.codetype = 'llprotestreason' and a.code='"
                        + strcode + "'";
         ExeSQL tExeSQL1 = new ExeSQL();
         strDS[1] = tExeSQL1.getOneValue(tSql1);

         strDS[2] = tLLClaimUWMainSchema.getAuditNoPassDesc(); //依据
         tDSListTable.add(strDS);

     //通知日期
         texttag.add("SysDate", SysDate);
     //邮编
     //texttag.add("ZipCode",zipcode);//收信人邮编
     //地址
     //texttag.add("Address",address);//收信人地址
     //姓名
     //texttag.add("Name", rgtname);
     //公司名称
     //tSql = "select a.name from ldcom a where "
     //			+ "a.comcode = '" + mngCom + "'";
     //String comname = tExeSQL.getOneValue(tSql);
     //texttag.add("company",comname);
     //部门
     //电话

        /**
         * No1.0 加入单个字段的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (texttag.size() > 0) {
            tXmlExport.addTextTag(texttag);
        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 加入多行数据的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tXmlExport.addListTable(tDSListTable, DSTitle); //保存理赔决定信息

        //tXmlExport.outputDocumentToFile("d:\\", "testprt"); //输出xml文档到文件
        mResult.clear();
        mResult.addElement(tXmlExport);
        mResult.add(mTransferData);
        logger.debug("xmlexport=" + tXmlExport);

        return true;
    }

    public CErrors getErrors()
    {
        return mErrors;
    }
		//得到结果集
    public VData getResult()
    {
        return mResult;
    }

		//主函数
    public static void main(String[] args)
    {

        GlobalInput tG = new GlobalInput();
        tG.Operator="001";
        tG.ManageCom ="86";
        tG.ComCode ="86";
        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("ClmNo",tClmNo);
//        tTransferData.setNameAndValue("CustNo",tCusno);
        tTransferData.setNameAndValue("PrtSeq","0000002461");

        VData tVData = new VData();
        tVData.add(tG);
        tVData.add(tTransferData);
        LLPRTProtestClaimBL tLLPRTProtestClaimBL = new LLPRTProtestClaimBL();
        if (tLLPRTProtestClaimBL.submitData(tVData,"") == false)
        {
            logger.debug("-------false-------");
        }
        int n = tLLPRTProtestClaimBL.mErrors.getErrorCount();
        for (int i = 0; i < n; i++)
        {
            String Content = "";
            Content = Content + "原因是: " + tLLPRTProtestClaimBL.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        }


    }


}
