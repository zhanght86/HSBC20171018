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
 * <p>Description: 单证打印：医疗险给付清单 -- PCT002,MedicalListC00060.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTMedBillPerBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTMedBillPerBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private MMap mMMap = new MMap();

    private TransferData mTransferData = new TransferData();

    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //出险人客户号
    private String mPrtSeq = "" ;       //流水号
    public LLPRTMedBillPerBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------医疗险给付清单-----LLPRTMedBillPerBL测试-----开始----------");

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

        logger.debug("----------医疗险给付清单-----LLPRTMedBillPerBL测试-----结束----------");
        return true;
    }

    public static void main(String[] args)
    {
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("PrtSeq","0000013666");
//        tTransferData.setNameAndValue("ClmNo","90000002042");
//        tTransferData.setNameAndValue("CustNo","0000550940");
        VData tVData = new VData();
        tVData.add(tTransferData);
        LLPRTMedBillPerBL tLLPRTMedBillPerBL=new LLPRTMedBillPerBL();
        if (!tLLPRTMedBillPerBL.submitData(tVData, ""))
         {
              String str= "提交失败，原因是: " +
                        tLLPRTMedBillPerBL.mErrors.getError(0).errorMessage;
              logger.debug(str);

         }


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
            String mClmNoSQL = "select OtherNo from LOPRTManager where PrtSeq='"+"?PrtSeq?"+"'" ;
            SQLwithBindVariables sqlbv = new SQLwithBindVariables();
            sqlbv.sql(mClmNoSQL);
            sqlbv.put("PrtSeq", mPrtSeq);
            ExeSQL clmExeSQL = new ExeSQL() ;
            String mClmNo = clmExeSQL.getOneValue(sqlbv);
            String mCusNoSQL = "select distinct CustomerNo from LLSubReport where SubRptNo='"+"?clmno?"+"'";
            SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
            sqlbv1.sql(mCusNoSQL);
            sqlbv1.put("clmno", mClmNo);
            ExeSQL cusExeSQL = new ExeSQL() ;
            SSRS cusNoSSRS = new SSRS();
            cusNoSSRS =cusExeSQL.execSQL(sqlbv1);
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
        String mCusNo = cusNoSSRS.GetText(1,1) ;


        XmlExportNew tXmlExport = new XmlExportNew(); //新建一个XmlExport的实例
//        tXmlExport.createDocument("MedicalListC00060.vts", "");
        tXmlExport.createDocument("医疗理赔给付清单[个人]");
        LLPRTPubFunBL tLLPRTPubFunBL= new LLPRTPubFunBL();

        //出险人姓名
        String nameSql = "select a.name from ldperson a where "
                    + "a.customerno = '" + "?cusno?" + "'";
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(nameSql);
        sqlbv2.put("cusno", mCusNo);
        ExeSQL tExeSQL = new ExeSQL();
        String tName = tExeSQL.getOneValue(sqlbv2);

        //系统时间
        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";

        //批单审核信息
        LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
        tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(mClmNo);
        if(tLLClaimUWMainSchema==null)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTProtestClaimBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询批单审核信息失败!";
            mErrors.addOneError(tError);
            return false;
        }

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
        //----------------------------------------------------------------------

        String endSQL = "select EndCaseDate from llclaim where clmno='"+"?clmno?"+"'" ;
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        sqlbv5.sql(endSQL);
        sqlbv5.put("clmno", mClmNo);
        ExeSQL endExeSQL = new ExeSQL() ;
        String endCaseDate = endExeSQL.getOneValue(sqlbv5);
        if(endCaseDate.equals("")||endCaseDate==null)
        {
            endCaseDate="";
        }
        else
        {
             endCaseDate=endCaseDate.substring(0,10);
        }


        //-----------------查询帐单原始金额-------------------------
        String tmedsumSql="select clmno,sum((case when fee is null then 0 else fee end)),sum((case when adjsum is null then 0 else adjsum end)) from llcasereceipt where clmno='"+"?clmno?"+"' group by clmno";
        SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
        sqlbv6.sql(tmedsumSql);
        sqlbv6.put("clmno", mClmNo);
        ExeSQL tmedsumexs = new ExeSQL();
        SSRS tmedsumSSRS = tmedsumexs.execSQL(sqlbv6);

        //----责任内金额算法需要修改为: 除去 费用类型为[ 其他 ]的调整金额的和------
        String tadjsumSql="select clmno,sum((case when adjsum is null then 0 else adjsum end)) from llcasereceipt where 1=1 "
                +" and clmno='"+"?clmno?"+"' and  feeitemcode not like 'CO%'"
                +" group by clmno";
        SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
        sqlbv7.sql(tadjsumSql);
        sqlbv7.put("clmno", mClmNo);
        ExeSQL tadjsumexs = new ExeSQL();
        SSRS tadjsumSSRS = tadjsumexs.execSQL(sqlbv7);

        if(tmedsumSSRS.MaxRow==0)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTProtestClaimBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询帐单原始金额 失败!";
            mErrors.addOneError(tError);
            return false;
        }
        String tmedSum=tmedsumSSRS.GetText(1,2);//帐单原始金额
        String tadjSum=tadjsumSSRS.GetText(1,2);//责任内总金额
        double tlimitSum=0;//责任内总金额
        tlimitSum=Double.parseDouble(tadjSum);
        double tnolimitSum=0;//责任外总金额
        tnolimitSum=Double.parseDouble(tmedSum)-tlimitSum;

//        //----------------查询 帐单金额、责任内总金额、责任外总金额 -----------------
//        String sumSQL = "select sum(a.OriSum),sum(a.AdjSum-a.OutDutyAmnt),sum(a.OriSum-(a.AdjSum-a.OutDutyAmnt)),sum(a.CalSum)"
//                        +" from LLClaimDutyFee a,llclaimdetail b where 1=1  "
//                        +" and a.dutyfeetype in('A','B ') "
//                        +" and a.clmno=b.clmno and a.polno=b.polno  and a.getdutytype=b.getdutykind"
//                        +" and a.dutycode=b.dutycode  and a.getdutycode=b.getdutycode "
//                        +" and b.givetype='0' and b.clmno='"+mClmNo +"' ";
//        ExeSQL exs = new ExeSQL();
//        SSRS tSSRS = exs.execSQL(sumSQL);
//        if(tSSRS.MaxRow==0)
//        {
//            CError tError = new CError();
//            tError.moduleName = "LLPRTProtestClaimBL";
//            tError.functionName = "dealdata";
//            tError.errorMessage = "查询帐单金额、责任内总金额、责任外总金额 失败!";
//            mErrors.addOneError(tError);
//            return false;
//        }
//        String oriSum = tSSRS.GetText(1, 1);//帐单金额
//        String AdjSum = tSSRS.GetText(1, 2);//责任内总金额
//        String exfee = tSSRS.GetText(1, 3);//责任外总金额
//        String calsum=tSSRS.GetText(1,4);//

        //------------------------社保,第三方,给付总金额------sum(nvl(SocPay,0))-----------------------
        String sSQL = "select sum((case when SocPay is null then 0 else SocPay end)),sum((case when OthPay is null then 0 else OthPay end)),sum((case when RealPay is null then 0 else RealPay end))from llclaimdutykind"
                      +" where clmno='" + "?clmno?" + "' and GetDutyKind in('100','200')";
        SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
        sqlbv8.sql(sSQL);
        sqlbv8.put("clmno", mClmNo);
        ExeSQL es = new ExeSQL();
        SSRS ssrs = es.execSQL(sqlbv8);
        if(ssrs.MaxRow==0)
        {
            CError tError = new CError();
            tError.moduleName = "LLPRTProtestClaimBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "查询第三方支付总金额,社保医疗支付总金额,给付总金额 失败!";
            mErrors.addOneError(tError);
            return false;
        }
        String socPay = ssrs.GetText(1, 1);
        String othPay = ssrs.GetText(1, 2);
        String realPay = ssrs.GetText(1, 3);


        //首先查出LLClaimUWDutyFee表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环
        String contSQL = "" ;
        contSQL = "select * from lccont where contno in "
                  +"(select distinct contno from LLClaimDutyFee where clmno='"+"?clmno?"+"')";
        SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
        sqlbv9.sql(contSQL);
        sqlbv9.put("clmno", mClmNo);
        //logger.debug(contSQL);
        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv9);

        ListTable tListTable = new ListTable();
        tListTable.setName("INSHRISK1");

        String[] Title = new String[8];
        Title[0] = "";
        Title[1] = "";
        Title[2] = "";
        Title[3] = "";
        Title[4] = "";
        Title[5] = "";
        Title[6] = "";
        Title[7] = "";
//        Title[8] = "";


//        double tlimitSum=0;//责任内总金额
//        double tnolimitSum=0;//责任外总金额

        for (int i=1;i<=tLCContSet.size();i++)
        {
            String contno = tLCContSet.get(i).getContNo() ;
//            String dutySQL = "select * from LLClaimDutyFee where 1=1"
//                             +" and dutyfeetype in('A','B ')"
//                             +" and clmno='"+mClmNo+"'"
//                             +" and contno='"+contno+"'";

            String dutySQL = "select a.* from llclaimdutyfee a,llclaimdetail b where 1=1 "
                             +" and a.dutyfeetype in('A','B ')"
                             +" and a.clmno=b.clmno and a.polno=b.polno  and a.getdutytype=b.getdutykind"
                             +" and a.dutycode=b.dutycode and a.getdutycode=b.getdutycode"
                             +" and b.givetype='0'"  //赔付结论 0----给付
                             +" and b.clmno='"+"?clmno?"+"' and b.contno='"+"?contno?"+"'"
                             +"  order by a.contno ";
            logger.debug("--dutySQL--"+dutySQL);
            SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
            sqlbv10.sql(dutySQL);
            sqlbv10.put("clmno", mClmNo);
            sqlbv10.put("contno", contno);
            LLClaimDutyFeeDB tLLClaimDutyFeeDB = new LLClaimDutyFeeDB() ;
            LLClaimDutyFeeSet tLLClaimDutyFeeSet = tLLClaimDutyFeeDB.executeQuery(sqlbv10) ;

            for(int j=1 ;j<=tLLClaimDutyFeeSet.size() ;j++)
            {
                logger.debug("------第"+i*j+"次循环开始----------------------------");
                String[] stra = new String[8] ;
                stra[0] = contno ;//保单号
                stra[1] = tLLClaimDutyFeeSet.get(j).getDutyFeeName();//费用类别
                stra[2] = tLLClaimDutyFeeSet.get(j).getStartDate();//开始日期
                stra[3] = tLLClaimDutyFeeSet.get(j).getEndDate();//结束日期

                //账单金额------原始金额
                double tdebt=tLLClaimDutyFeeSet.get(j).getOriSum();
                //责任内金额===调整金额--免赔额
//                double tlimit=tLLClaimDutyFeeSet.get(j).getAdjSum()-tLLClaimDutyFeeSet.get(j).getOutDutyAmnt();
                 double tlimit=tLLClaimDutyFeeSet.get(j).getAdjSum();
                //责任外金额===账单金额--责任内金额
                double tnolimit=tdebt-tlimit;

                stra[4] = String.valueOf(tdebt) ;//账单金额
                stra[5] = String.valueOf(tlimit);//责任内金额
                stra[6] = String.valueOf(tnolimit);//责任外金额

                stra[7] = tLLClaimDutyFeeSet.get(j).getAdjRemark();
//                stra[] = tLLClaimDutyFeeSet.get(j).getHosName();//单位

//               tlimitSum=tlimitSum+tlimit;//责任内总金额---累加
//               tnolimitSum=tnolimitSum+tnolimit;//责任外总金额------累加
               tListTable.add(stra);
              logger.debug("------第"+i*j+"次循环结束----------------------------");
            }
        }

        tXmlExport.addListTable(tListTable,Title);
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 加入单个字段的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        TextTag tTextTag = new TextTag(); //新建一个TextTag的实例
        tTextTag.add("ClNo",mClmNo);//赔案号
        tTextTag.add("RiskerName", tName); //出险人
        tTextTag.add("Checker", checker); //审核人
        tTextTag.add("Signer", signer); //审批人
        tTextTag.add("Date",endCaseDate);//结案日期

        tTextTag.add("Account1",tmedSum);//帐单原始金额
        tTextTag.add("Account4",String.valueOf(tlimitSum));//责任内总金额---累加
        tTextTag.add("Account5",String.valueOf(tnolimitSum));//责任外总金额------累加

        tTextTag.add("Account2",othPay);//第三方
        tTextTag.add("Account3", socPay); //社保
        tTextTag.add("Account6", realPay); //给付

        if (tTextTag.size() > 0)
        {
            tXmlExport.addTextTag(tTextTag);
        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 加入多行数据的打印变量
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        xmlexport.addDisplayControl("Risk"); //模版上的主险附加险部分的控制标记

      //  tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
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

