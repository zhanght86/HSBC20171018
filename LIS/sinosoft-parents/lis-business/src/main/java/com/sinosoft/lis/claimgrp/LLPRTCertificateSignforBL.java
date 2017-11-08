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
 * <p>Description: 单证打印：索赔文件签收清单--PCT002,SpwjqsqdGrC000100.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTCertificateSignforBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTCertificateSignforBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private MMap mMMap = new MMap();
    private TransferData mTransferData = new TransferData();
    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //客户号
//    private String claimTag = "" ;      //案件状态

    public LLPRTCertificateSignforBL(){}


    /*
     **************加入数据，用于调试********
     */
    public static void main(String[] args)
    {
        GlobalInput tG = new GlobalInput();
        tG.ComCode = "86";
        tG.ManageCom = "86";
        tG.Operator = "001";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("ClmNo", "90000001455");
        tTransferData.setNameAndValue("CustNo", "0000531640");

        // 准备传输数据 VData
        VData tVData = new VData();
        tVData.add(tG);
        tVData.add(tTransferData);
        LLPRTCertificateSignforBL tLLPRTCertificateSignforBL = new
                LLPRTCertificateSignforBL();
        if (tLLPRTCertificateSignforBL.submitData(tVData, "") == false)
        {
            logger.debug("#######----索赔文件签收清单打印出错----##############");
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

        logger.debug("----------索赔文件签收清单-----LLPRTCertificateSignforBL测试-----开始----------");

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

        logger.debug("----------索赔文件签收清单-----LLPRTCertificateSignforBL测试-----结束----------");
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
        this.mClmNo = (String) mTransferData.getValueByName("ClmNo");
        this.mCusNo = (String) mTransferData.getValueByName("CustNo");
//        this.claimgrpTag = (String) mTransferData.getValueByName("ClaimTag");  //案件状态
        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 定义打印
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        TextTag tTextTag = new TextTag(); //新建一个TextTag的实例
        XmlExport tXmlExport = new XmlExport(); //新建一个XmlExport的实例
        tXmlExport.createDocument("SpwjqsqdGrC000100.vts", "");
        LLPRTPubFunBL tLLPRTPubFunBL= new LLPRTPubFunBL();
        //理赔类型
        String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo,mCusNo);
        //出险人姓名
        String tSql = "select a.name from ldperson a where "
                      + "a.customerno = '" + mCusNo + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String tName = tExeSQL.getOneValue(tSql);
        //收信人(申请人)姓名Name、性别、管理机构 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
//        String rgtname = "" ;
        String rgtname = tLLRegisterSchema.getRgtantName();
        if(rgtname==null){rgtname="";}
        logger.debug("-------rgtname-------"+rgtname);

        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";
        logger.debug("------SysDate-------"+SysDate);

        tTextTag.add("BarCode1", "CA041");
        tTextTag.add("BarCodeParam1", "BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
        tTextTag.add("BarCode2",mClmNo);
        tTextTag.add("BarCodeParam2","BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
        logger.debug("------赔案号条码打印成功-------");

        tTextTag.add("ClaimNo",mClmNo);//陪案号
        tTextTag.add("ClaimType",ClaimType);//理赔类型
        tTextTag.add("AccName",tName);//出险人
        tTextTag.add("AppName",rgtname);//申请人
        tTextTag.add("SysDate",SysDate);//通知日期
        tTextTag.add("ClaimTag","留存");//立案为留存

        ListTable tListTable = new ListTable();
        tListTable.setName("GRID");
        String[] Title = new String[5];
        Title[0] = "" ;
        Title[1] = "" ;
        Title[2] = "" ;
        Title[3] = "" ;
        Title[4] = "" ;

        //索赔文件签收清单
        LLAffixDB tLLAffixDB = new LLAffixDB();
        String affixSQL = "select * from Llaffix where rgtno='"+mClmNo+"' and customerno='"+mCusNo+"'";
        LLAffixSet tLLAffixSet = tLLAffixDB.executeQuery(affixSQL);
//        if(tLLAffixSet.size()==0)
//        {
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalCheckBL";
//            tError.functionName = "dealdata";
//            tError.errorMessage = "没有查询到任何单证记录!";
//            mErrors.addOneError(tError);
//            return false;
//        }
        String operator = "";
        if(tLLAffixSet.size()>0)
        {
            String operatorSQL = "select UserName from llclaimuser "
                 +"where usercode='"+tLLAffixSet.get(1).getOperator()+"'";
            ExeSQL cExeSQL = new ExeSQL();
            operator = cExeSQL.getOneValue(operatorSQL);
        }
        else
        {
             operator = "";
        }


        tTextTag.add("Operator",operator);//签收人
        for(int i = 1; i <= tLLAffixSet.size(); i++)
        {
            String[] stra= new String[5];
            stra[0] = tLLAffixSet.get(i).getAffixName();

            if(tLLAffixSet.get(i).getSubFlag()==null)
            {
                stra[1] = "否";
            }
            else
            {

                if (tLLAffixSet.get(i).getSubFlag().equals("0")) {
                    stra[1] = "是";
                } else if (tLLAffixSet.get(i).getSubFlag().equals("1")) {
                    stra[1] = "否";
                }
            }

            int tReadyCount=tLLAffixSet.get(i).getReadyCount();
            stra[2] = Integer.toString(tReadyCount);

            if(tLLAffixSet.get(i).getProperty()==null)
            {
                stra[3] = "原件";
            }
            else
            {

                if (tLLAffixSet.get(i).getProperty().equals("0")) {
                    stra[3] = "原件";
                } else if (tLLAffixSet.get(i).getProperty().equals("1")) {
                    stra[3] = "复印件";
                }
            }

            if(tLLAffixSet.get(i).getReturnFlag()==null)
            {
                stra[4] = "否";
            }
            else
            {

                if (tLLAffixSet.get(i).getReturnFlag().equals("0")) {
                    stra[4] = "是";
                } else if (tLLAffixSet.get(i).getReturnFlag().equals("1")) {
                    stra[4] = "否";

                }
            }
            tListTable.add(stra);
        }
        logger.debug("##################");
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
    public VData getResult()
    {
        return mResult;
    }
    public CErrors getErrors()
    {
        return mErrors;
    }


}

