package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.PrintService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 单证打印：批单-理赔预付保险金给付批注 -- PCT014,LpyubxjjfpzC00050.vts</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author 续涛，2005.07.28--2005.07.28
 * @version 1.0
 */
public class LLPRTPostilPrepayBL implements PrintService
{
private static Logger logger = Logger.getLogger(LLPRTPostilPrepayBL.class);


    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */

    private MMap mMMap = new MMap();

    private TransferData mTransferData = new TransferData();

    private String mClmNo    = "";      //赔案号
    private String mCusNo    = "";      //客户号
    private String mPrtSeq = "" ;       //流水号
    private String mAccNo = "" ;       //流水号
    private String mGiveType = "" ;       //流水号
    private String mPrtType = "" ;       //流水号
    
    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    public LLPRTPostilPrepayBL(){}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *          cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {

        logger.debug("----------批单-理赔预付保险金给付批注-----LLPRTPostilPrepayBL测试-----开始----------");

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

        logger.debug("----------批单-理赔预付保险金给付批注-----LLPRTPostilPrepayBL测试-----结束----------");
        return true;
    }


    public static void main(String[] args)
    {
       TransferData tTransferData=new  TransferData();
       tTransferData.setNameAndValue("PrtSeq","0000003181");

       VData tVData=new VData();
       tVData.add(tTransferData);

       LLPRTPostilPrepayBL tLLPRTPostilPrepayBL=new LLPRTPostilPrepayBL();
       if(tLLPRTPostilPrepayBL.submitData(tVData,"")==false)
       {
           logger.debug("打印出错");
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
//        this.mClmNo = (String) mTransferData.getValueByName("ClmNo");    //赔案号
//        this.mCusNo = (String) mTransferData.getValueByName("CustNo");   //出险人客户号
        this.mPrtSeq = (String) mTransferData.getValueByName("PrtSeq");
        this.mPrtType = (String) mTransferData.getValueByName("PrtType");
        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData1()
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
        XmlExport tXmlExport = new XmlExport(); //新建一个XmlExport的实例
        tXmlExport.createDocument("LpyubxjjfpzC00050.vts", "");
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

        //----------------------------------------------------------------------
        tTextTag.add("IndemnityNo",mClmNo);//赔案号
        tTextTag.add("IndemnityType",ClaimType);//理赔类型
        tTextTag.add("InsuredName",tName);//出险人
        tTextTag.add("AppName",rgtname);//申请人

//        //批单审核信息----------------------------------------------------------改为预付确认人
//        LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
//        tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(mClmNo);
//
//        //审核人编码 -- tLLClaimUWMainSchema.getAuditPer()
//        String checkerSQL = "select UserName from llclaimuser "
//                         +"where usercode='"+tLLClaimUWMainSchema.getAuditPer()+"'";
//        ExeSQL cExeSQL = new ExeSQL() ;
//        String checker = cExeSQL.getOneValue(checkerSQL);
//        //审批人编码 -- tLLClaimUWMainSchema.getExamPer()
//        String signerSQL = "select UserName from llclaimuser "
//                         +"where usercode='"+tLLClaimUWMainSchema.getExamPer()+"'";
//        ExeSQL sExeSQL = new ExeSQL() ;
//        String signer = sExeSQL.getOneValue(signerSQL);

        LLPrepayClaimDB tLLPrepayClaimDB = new LLPrepayClaimDB();
        tLLPrepayClaimDB.setClmNo(this.mClmNo);
        if (tLLPrepayClaimDB.getInfo())
        {
            String tSql = "select username from llclaimuser where "
                          + "usercode = '" + "?usercode?" + "'";
            SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
            sqlbv3.sql(tSql);
            sqlbv3.put("usercode", tLLPrepayClaimDB.getOperator());
            ExeSQL tEXE = new ExeSQL();
            String tOperator = tEXE.getOneValue(sqlbv3);
            tTextTag.add("LCContl.UWOperator", tOperator); //审核人
            tTextTag.add("ApproveName", tOperator); //审批人
        }

        //系统时间-----------------------------------------------------------------
        String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                         StrTool.getDay() + "日";
        tTextTag.add("SysDate", SysDate); //批注日期


        //总预付金额
        String prepaySumSQL = "";
        prepaySumSQL = "select PrepaySum from LLPrepayClaim where clmNo='"+"?clmNo?"+"'";
        SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
        sqlbv4.sql(prepaySumSQL);
        sqlbv4.put("clmNo", mClmNo);
        ExeSQL prepaySumExeSQL = new ExeSQL();
        String prepaySum=prepaySumExeSQL.getOneValue(sqlbv4);
        tTextTag.add("TotalMoney",prepaySum);


        //合同号号、理赔金额、剩余有效保额信息 循环输出
        //首先查出LLClaimDetail表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环
        String contSQL = "" ;
        contSQL = "select * from lccont where contno in "
                  +"(select distinct contno from LLPrepayDetail "
                  +"where clmno='"+"?clmNo?"+"')";
        //logger.debug(contSQL);
        SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
        sqlbv5.sql(contSQL);
        sqlbv5.put("clmNo", mClmNo);
        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv5);

        ListTable tListTable = new ListTable();
        tListTable.setName("PDLPYF");

        String[] Title = new String[3];
        Title[0] = "";
        Title[1] = "";
        Title[2] = "";

        for (int i=1;i<=tLCContSet.size();i++)
        {
            //取得保单号(合同号)
            String contNo = tLCContSet.get(i).getContNo();

            String[] contStr = new String[3] ;
            contStr[0] = "保单号: "+contNo ;//页面上显示为保单号
            contStr[1] = "" ;
            contStr[2] = "" ;
            tListTable.add(contStr);

            //预付保项
            String yfbxSQL = "";
            yfbxSQL = "select * from LLPrepayDetail where clmno='"+"?clmNo?"+"' and contno='"+"?contno?"+"'";
            SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
            sqlbv6.sql(yfbxSQL);
            sqlbv6.put("clmNo", mClmNo);
            sqlbv6.put("contno", contNo);
            LLPrepayDetailDB LLPrepayDetailDB = new LLPrepayDetailDB();
            LLPrepayDetailSet yfbxLLPrepayDetailSet = LLPrepayDetailDB.executeQuery(sqlbv6);
            for(int j=1;j<=yfbxLLPrepayDetailSet.size();j++)
            {
                //根据LLPrepayDetail中的 给付责任类型和给付责任编码 从LMDutyGetClm中  查询给付责任名称 即 保项名称
                String yfbxNameSQL = "select GetDutyName from lmdutygetclm where getdutycode='"+"?getdutycode?"+"'"
                                     +" and getdutykind='"+"?getdutykind?"+"'";
                SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
                sqlbv7.sql(yfbxNameSQL);
                sqlbv7.put("getdutycode", yfbxLLPrepayDetailSet.get(j).getGetDutyCode());
                sqlbv7.put("getdutykind", yfbxLLPrepayDetailSet.get(j).getGetDutyKind());
                ExeSQL yfbxnameExeSQL = new ExeSQL();
                String yfbxName = yfbxnameExeSQL.getOneValue(sqlbv7);
                logger.debug("******************************************");


                //剩余有效保额
                String feeSQL = "";
                feeSQL = "select * from lcget where polno='"+"?polno?"+"'"
                         +" and getdutycode='"+"?getdutycode?"+"'"
                         +" and dutycode='"+"?dutycode?"+"'";
                //logger.debug(feeSQL);
                SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
                sqlbv8.sql(feeSQL);
                sqlbv8.put("polno", yfbxLLPrepayDetailSet.get(j).getPolNo());
                sqlbv8.put("getdutycode", yfbxLLPrepayDetailSet.get(j).getGetDutyCode());
                sqlbv8.put("dutycode", yfbxLLPrepayDetailSet.get(j).getDutyCode());
                LCGetDB tLCGetDB = new LCGetDB();
                LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv8);

                //保项剩余有效保额
//                double tStandMoney =tLCGetSet.get(1).getStandMoney();
//                double tPrepaySum=yfbxLLPrepayDetailSet.get(j).getPrepaySum();
                double fee = 0;
                fee = tLCGetSet.get(1).getStandMoney() - yfbxLLPrepayDetailSet.get(j).getPrepaySum();

                String[] preStr = new String[3];
//                preStr[0] = "保项"+j+": " +yfbxName;//预付保项的责任名称
                preStr[0] = "保项：" +yfbxName;//预付保项的责任名称
                preStr[1] = "预付金额："+String.valueOf(yfbxLLPrepayDetailSet.get(j).getPrepaySum());//保项预付金额
                preStr[2] = "剩余有效保额："+String.valueOf(fee);//
                tListTable.add(preStr);
            }

            //增加一行信息（计算金额、结算金额、给付金额《预付金额》：）
            String tsumrealpaySql="select (case when sum(case when realpay is null then 0 else realpay end) is null then 0 else sum(case when realpay is null then 0 else realpay end) end) from llclaimdetail where 1=1 "
                      +" and clmno='"+"?clmno?"+"'"
                      + " and contno='" + "?contno?" + "' ";
            logger.debug("查询计算金额" + tsumrealpaySql);
            SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
            sqlbv9.sql(tsumrealpaySql);
            sqlbv9.put("clmno", mClmNo);
            sqlbv9.put("contno", contNo);
            ExeSQL tsumrealpayExeSQL = new ExeSQL();
            String tsumrealpay = tsumrealpayExeSQL.getOneValue(sqlbv9);

            String tsumpaySql=" select (case when sum(case when pay is null then 0 else pay end) is null then 0 else sum(case when pay is null then 0 else pay end) end) from llbalance where 1=1"
                              +" and clmno='"+"?clmno?"+"'"
                              +" and contno='"+"?contno?"+"' "
                              +" and (feeoperationtype like 'C%' or feeoperationtype like 'D%')";
           logger.debug("查询保单结算金额"+tsumpaySql);
           SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
           sqlbv10.sql(tsumpaySql);
           sqlbv10.put("clmno", mClmNo);
           sqlbv10.put("contno", contNo);
           ExeSQL tsumpayExeSQL = new ExeSQL();
           String tsumpay = tsumpayExeSQL.getOneValue(sqlbv10);

            String tPrepaySumSql="select (case when sum(case when prepaysum is null then 0 else prepaysum end) is null then 0 else sum(case when prepaysum is null then 0 else prepaysum end) end) from llclaimdetail where 1=1 "
                      +" and clmno='"+"?clmno?"+"'"
                      + " and contno='" + "?contno?" + "' ";
            SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
            sqlbv11.sql(tPrepaySumSql);
            sqlbv11.put("clmno", mClmNo);
            sqlbv11.put("contno", contNo);
            ExeSQL tPrepaySumExeSQL = new ExeSQL();
            String tPrepaySum = tPrepaySumExeSQL.getOneValue(sqlbv11);

            String[] llbeforebnfStr = new String[3];
            llbeforebnfStr[0]="计算金额："+tsumrealpay;
            llbeforebnfStr[1]="结算金额："+tsumpay;
            llbeforebnfStr[2]="给付金额："+tPrepaySum;
            tListTable.add(llbeforebnfStr);

            //受益人、领取人信息--LLBNF,查询LLBnf表，条件：受益性质（bnfkind=‘B--预付金额受益’）
            String pSQL = "";
            pSQL = "select * from LLbnf where clmno='"+"?clmno?"+"'"
                   +" and contNo= '"+"?contno?"+"' and bnfkind='B' " ;
            logger.debug(pSQL);
            SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
            sqlbv12.sql(pSQL);
            sqlbv12.put("clmno", mClmNo);
            sqlbv12.put("contno", contNo);
            LLBnfDB tLLBnfDB = new LLBnfDB();
            LLBnfSet tLLBnfSet = tLLBnfDB.executeQuery(sqlbv12);

            for(int m=1;m<=tLLBnfSet.size();m++)
            {
                String[] str1 = new String[3];
//                str1[0] = "受益人"+m+": "+tLLBnfSet.get(m).getName();
                str1[0] = "受益人: "+tLLBnfSet.get(m).getName();
                //模板只有三列把百分比和金额拼成一列
                str1[1] = "给付金额: "+tLLBnfSet.get(m).getGetMoney();
                str1[2] = "百分比: "+tLLBnfSet.get(m).getBnfLot();

                String[] str2 = new String[3];
//                str2[0] = "领款人"+m+": "+tLLBnfSet.get(m).getPayeeName();
                str2[0] = "领款人: "+tLLBnfSet.get(m).getPayeeName();
                //模板只有三列把百分比和金额拼成一列
                str2[1] = "受领金额: "+tLLBnfSet.get(m).getGetMoney();
                str2[2] = "身份证号: "+tLLBnfSet.get(m).getPayeeIDNo();

                tListTable.add(str1);//受益人所在行信息
                tListTable.add(str2);//领款人所在行信息
            }

//            String[] spaceStr = new String[1];
//            spaceStr[0] = "" ;
//            tListTable.add(spaceStr);//加空行
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

     //   tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
        mResult.clear();
        mResult.addElement(tXmlExport);
        mResult.add(mTransferData);
        logger.debug("xmlexport=" + tXmlExport);
        
        String updateStateSql="update loprtmanager set stateflag='1' where otherno='"+"?clmno?"+"' and code='PCT014'";
        logger.debug("更新打印管理表的sql:"+updateStateSql);
        SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
        sqlbv13.sql(updateStateSql);
        sqlbv13.put("clmno", mClmNo);
        mMMap.put(sqlbv13, "UPDATE");

        return true;
    }

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		String mSQL = "";
		String amontFee = "";// 总给付金额
		String endcasedate = "";// 结案日期

		// 赔案号
		mSQL = "select OtherNo from LOPRTManager where PrtSeq='" + "?PrtSeq?"
				+ "'";
		logger.debug("根据打印管理表的流水号" + mPrtSeq + "查询赔案号的sql=" + mSQL);
		SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
		sqlbv14.sql(mSQL);
		sqlbv14.put("PrtSeq", mPrtSeq);
		mClmNo = tExeSQL.getOneValue(sqlbv14);

		// 客户号
		mSQL = "select CustomerNo from LLcase where CaseNo='" + "?clmno?" + "'";
		logger.debug("根据赔案号" + mPrtSeq + "查询出险人客户号的sql=" + mSQL);
		SQLwithBindVariables sqlbv15 = new SQLwithBindVariables();
		sqlbv15.sql(mSQL);
		sqlbv15.put("clmno", mClmNo);
		tSSRS = tExeSQL.execSQL(sqlbv15);
		mCusNo = tSSRS.GetText(1, 1);

		// 事件号
		mSQL = "select distinct caserelano from LLCaseRela where caseno='"
				+ "?clmno?" + "'";
		logger.debug("根据赔案号" + mClmNo + "查询事件号的sql=" + mSQL);
		SQLwithBindVariables sqlbv16 = new SQLwithBindVariables();
		sqlbv16.sql(mSQL);
		sqlbv16.put("clmno", mClmNo);
		mAccNo = tExeSQL.getOneValue(sqlbv16);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 定义打印
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew tXmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		tXmlExport.createDocument("LpyubxjjfpzC00050.vts", "");
		tXmlExport.createDocument("批单-理赔预付保险金批注", "", "", "0");
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 理赔类型---------------------------------------------------------------
		String ClaimType = tLLPRTPubFunBL.getSLLReportReason(mClmNo, mCusNo);

		// 出险人姓名--------------------------------------------------------------
		String nameSql = "select a.name from ldperson a where "
				+ "a.customerno = '" + "?cusno?" + "'";
		SQLwithBindVariables sqlbv17 = new SQLwithBindVariables();
		sqlbv17.sql(nameSql);
		sqlbv17.put("cusno", mCusNo);
		String tName = tExeSQL.getOneValue(sqlbv17);

		// 申请人 姓名Name 根据赔案号（mClmNo）从立案申请登记(LLRegister)中查------------
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterSchema = tLLPRTPubFunBL.getLLRegister(mClmNo);
		String rgtname = "";
		rgtname = tLLRegisterSchema.getRgtantName();

		// 系统时间---------------------------------------------------------------
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";

		// 批单审核信息
		LLClaimUWMainSchema tLLClaimUWMainSchema = new LLClaimUWMainSchema();
		tLLClaimUWMainSchema = tLLPRTPubFunBL.getLLClaimUWMain(mClmNo);

		//----------------------------------------------------------------------
		tTextTag.add("IndemnityNo", mClmNo);// 赔案号
		tTextTag.add("AccNo", mAccNo);// 事件号
		tTextTag.add("IndemnityType", ClaimType);// 理赔类型
		tTextTag.add("InsuredName", tName);// 出险人
		tTextTag.add("LCCont.AppntName", rgtname);// 申请人
		tTextTag.add("SysDate", PubFun.getCurrentDate());// 系统时间
		// 判断是否发生过预付以打印批注-----------------------------------------------
		String pzSQL = "";
		pzSQL = "select count(*) from llclaimdetail where clmno='" + "?clmno?"
				+ "' and PrepayFlag='1'";
		SQLwithBindVariables sqlbv18 = new SQLwithBindVariables();
		sqlbv18.sql(pzSQL);
		sqlbv18.put("clmno", mClmNo);
		ExeSQL pzExeSQL = new ExeSQL();
		String pzName = pzExeSQL.getOneValue(sqlbv18);
		String prePay = "";
		if (Integer.parseInt(pzName) > 0) {
			prePay = "此次实付保险金为已扣除预付保险金的金额。";
			tXmlExport.addDisplayControl("display");// 控制批注是否显示
		}
		// 判断是否将身故保险金转换为即期年金进行给付
		String pzchaSQL = "select GetMode from llregister where RgtNo='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv19 = new SQLwithBindVariables();
		sqlbv19.sql(pzchaSQL);
		sqlbv19.put("clmno", mClmNo);
		ExeSQL pzChaExeSQL = new ExeSQL();
		String tGetMode = pzChaExeSQL.getOneValue(sqlbv19);
		String yearPay = "";
		if (tGetMode.equals("2")) {
			yearPay = "本次保险事故确定可以给付的身故保险金将按照保险合同之规定转换为即期年金进行给付。";
			tXmlExport.addDisplayControl("display");
			//tTextTag.add("PZ1",prePay+"本次保险事故确定可以给付的身故保险金将按照保险合同之规定转换为即期年金进行给付"
			// );
		}
		tTextTag.add("PZ1", prePay + yearPay);

		//总给付金额和结案日期------------------------------------------------------------
		// --
		String realPaySQL = "select RealPay,EndCaseDate from llclaim where clmNo='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv20 = new SQLwithBindVariables();
		sqlbv20.sql(realPaySQL);
		sqlbv20.put("clmno", mClmNo);
		ExeSQL realPayExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv20);
		amontFee = tSSRS.GetText(1, 1);
		endcasedate = PubFun.getCurrentDate();
		logger.debug("赔案" + mClmNo + "的总给付金额是" + amontFee + ",结案日期是"
				+ endcasedate);
		double tZJFJE = Double.parseDouble(amontFee); // 为格式化小数点
		String DXJE = PubFun.getChnMoney(tZJFJE);
		amontFee = new DecimalFormat("0.00").format(tZJFJE);

		// 合同号号、理赔金额、剩余有效保额信息 循环输出---------------------------------
		String tSQL = "select givetype from llclaim where clmNo='" + "?clmno?"
				+ "'";
		SQLwithBindVariables sqlbv21 = new SQLwithBindVariables();
		sqlbv21.sql(tSQL);
		sqlbv21.put("clmno", mClmNo);
		mGiveType = tExeSQL.getOneValue(sqlbv21);

		// 首先查出llbalance表中指定赔案号下的不同的保单号(合同号)的数量作为最外层循环

		String contSQL = "";
		contSQL = "select * from lccont where contno in "
				+ "(select distinct contno from llbalance " + " where clmno='"
				+ "?clmno?" + "') order by contno";
		logger.debug(contSQL);
		SQLwithBindVariables sqlbv22 = new SQLwithBindVariables();
		sqlbv22.sql(contSQL);
		sqlbv22.put("clmno", mClmNo);
		LCContDB tLCContDB = new LCContDB();
		LCContSet tLCContSet = tLCContDB.executeQuery(sqlbv22);

		ListTable tListTable = new ListTable();
		tListTable.setName("LPJF");

		String[] Title = new String[3];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";

		double sumPay = 0.00;

		String tBnfSql = "select distinct bnfno from llbnfgather where clmno='"
				+ "?clmno?" + "'";
		SQLwithBindVariables sqlbv23 = new SQLwithBindVariables();
		sqlbv23.sql(tBnfSql);
		sqlbv23.put("clmno", mClmNo);
		SSRS tBnfSSRS = new SSRS();
		ExeSQL tExeSql = new ExeSQL();
		tBnfSSRS = tExeSql.execSQL(sqlbv23);
		// 循环受益人信息
		for (int i = 1; i <= tBnfSSRS.MaxRow; i++) {
			String tBnfNo = tBnfSSRS.GetText(i, 1);
			LLBnfSchema tLLBnfSchema = new LLBnfSchema();
			LLBnfDB tLLBnfDB = new LLBnfDB();
			LLBnfSet tLLBnfSet = new LLBnfSet();
			tLLBnfDB.setClmNo(mClmNo);
			tLLBnfDB.setBnfNo(tBnfNo);
			tLLBnfDB.setBnfKind("B");

			tLLBnfSet = tLLBnfDB.query();
			if (tLLBnfSet.size() > 0) {
				tLLBnfSchema = tLLBnfSet.get(1);
			}
			String[] bnfStr = new String[3];
			bnfStr[0] = "受益人: " + tLLBnfSchema.getName();
			String tSex = "";
			if("0".equals(tLLBnfSchema.getSex())){
				tSex = "男";
			}
			if("1".equals(tLLBnfSchema.getSex())){
				tSex = "女";
			}
			
			bnfStr[1] = "性别：" + tSex;
			String tRelation = "";
			if(tLLBnfSchema.getRelationToInsured()!=null){
				String tRelationSql = "select codename from ldcode where codetype='relation' and code='"+"?code?"
								+"'";
				SQLwithBindVariables sqlbv24 = new SQLwithBindVariables();
				sqlbv24.sql(tRelationSql);
				sqlbv24.put("code", tLLBnfSchema.getRelationToInsured());
				tRelation = tExeSql.getOneValue(sqlbv24);
			}
			bnfStr[2] = "与被保险人关系：" + tRelation;
			tListTable.add(bnfStr);
			String[] bnfStr2 = new String[3];
			String tIDType = "";
			if(tLLBnfSchema.getIDType()!=null){
				String tIDTypeSql = "select * from ldcode where codetype='idtype' and code='"+"?code?"
								+"'";
				SQLwithBindVariables sqlbv25 = new SQLwithBindVariables();
				sqlbv25.sql(tIDTypeSql);
				sqlbv25.put("code", tLLBnfSchema.getIDType());
				tIDType = tExeSql.getOneValue(sqlbv25);
			}
			bnfStr2[0] = "证件类型： " + tIDType;
			bnfStr2[1] = "证件号码：" + tLLBnfSchema.getIDNo();
			bnfStr2[2] = "";
			//bnfkind='A' 过滤掉预付
			String tPolSql = "select distinct polno from llbnf where clmno='"
					+ "?clmno?" + "' and bnfno='" + "?bnfno?" +"' and bnfkind='B'";
			SQLwithBindVariables sqlbv26 = new SQLwithBindVariables();
			sqlbv26.sql(tPolSql);
			sqlbv26.put("clmno", mClmNo);
			sqlbv26.put("bnfno", tBnfNo);
			SSRS tPolSSRS = new SSRS();
			tPolSSRS = tExeSql.execSQL(sqlbv26);
			for (int k = 1; k <= tPolSSRS.MaxRow; k++) {
				String tPolNo = tPolSSRS.GetText(k, 1);
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tPolNo);
				if (!tLCPolDB.getInfo()) {
					CError.buildErr(this, "查找保险信息失败!");
					return false;
				}

				String tRiskNameSql = "select riskname from lmrisk where riskcode ='"
						+ "?riskcode?" + "'";
				SQLwithBindVariables sqlbv27 = new SQLwithBindVariables();
				sqlbv27.sql(tRiskNameSql);
				sqlbv27.put("riskcode", tLCPolDB.getRiskCode());
				String tRiskName = tExeSql.getOneValue(sqlbv27);
				String[] polStr = new String[3];
				polStr[0] = "险种：" + tRiskName;
				polStr[1] = "保单号：" + tLCPolDB.getPolNo();
				polStr[2] = "保险金额：" + tLCPolDB.getAmnt();
				tListTable.add(polStr);
				String bxSQL = "";
				bxSQL = "select * from LLClaimDetail where clmno='" + "?clmno?"
						+ "'" + " and polno='" + "?polno?" + "' and givetype='0'";
				logger.debug("查出合同下的保单险种号为" + tPolNo + "保项数目的sql="
						+ bxSQL);
				SQLwithBindVariables sqlbv28 = new SQLwithBindVariables();
				sqlbv28.sql(bxSQL);
				sqlbv28.put("clmno", mClmNo);
				sqlbv28.put("polno", tPolNo);
				LLClaimDetailDB bxLLClaimDetailDB = new LLClaimDetailDB();
				LLClaimDetailSet bxLLClaimDetailSet = bxLLClaimDetailDB
						.executeQuery(sqlbv28);

				// 内层循环保项的数目
				for (int j = 1; j <= bxLLClaimDetailSet.size(); j++) {
					//剩余有效保额----------------------------------------------------
					String feeSQL = "";
					feeSQL = "select * from lcget where polno='"
							+ "?polno?" + "'"
							+ " and getdutycode='"
							+ "?getdutycode?" + "'"
							+ " and dutycode='"
							+ "?dutycode?" + "'";
					logger.debug("查询剩余保额的sql=" + feeSQL);
					SQLwithBindVariables sqlbv29 = new SQLwithBindVariables();
					sqlbv29.sql(feeSQL);
					sqlbv29.put("polno", bxLLClaimDetailSet.get(j).getPolNo());
					sqlbv29.put("getdutycode", bxLLClaimDetailSet.get(j).getGetDutyCode());
					sqlbv29.put("dutycode", bxLLClaimDetailSet.get(j).getDutyCode());
					LCGetDB tLCGetDB = new LCGetDB();
					LCGetSet tLCGetSet = tLCGetDB.executeQuery(sqlbv29);
					double fee = 0;// 剩余有效保额

					// 判断保项是否终止，如果终止 则 剩余有效保额==0
					LLContStateSet tLLContStateSet = new LLContStateSet();
					LLContStateDB tLLContStateDB = new LLContStateDB();
					String strStateSql = "select * from llcontstate where 1=1 "
							+ " and clmno = '" + "?clmno?" + "'"
							+ " and polno = '"
							+ "?polno?" + "'";
					SQLwithBindVariables sqlbv30 = new SQLwithBindVariables();
					sqlbv30.sql(strStateSql);
					sqlbv30.put("clmno", mClmNo);
					sqlbv30.put("polno", bxLLClaimDetailSet.get(j).getPolNo());
					tLLContStateSet.set(tLLContStateDB
							.executeQuery(sqlbv30));
					if (tLLContStateSet.size() > 0) {
						fee = 0;// 保项终止---剩余有效保额==0
					} else {
						fee = tLCGetSet.get(1).getStandMoney()
								- tLCGetSet.get(1).getSumMoney();
						if (mLLClaimPubFunBL.getLMRiskSort(bxLLClaimDetailSet
								.get(j).getRiskCode(), "26")) {
							logger.debug("********---------mLLClaimPubFunBL.getLMRiskSort--------********");
							fee = mLLClaimPubFunBL.getSumMoney26(mClmNo,
									bxLLClaimDetailSet.get(j).getPolNo(),
									bxLLClaimDetailSet.get(j).getDutyCode(),
									bxLLClaimDetailSet.get(j).getGetDutyCode(),
									bxLLClaimDetailSet.get(j).getGetDutyKind());
						}
						fee = (fee > 0) ? fee : 0;
					}

					// 根据llclaimdetail中的 给付责任类型和给付责任编码 从LMDutyGetClm中 查询给付责任名称 即
					// 保项名称
					String bxNameSQL = "select GetDutyName from lmdutygetclm where getdutycode='"
							+ "?getdutycode?"
							+ "'"
							+ " and getdutykind='"
							+ "?getdutykind?" + "'";
					SQLwithBindVariables sqlbv31 = new SQLwithBindVariables();
					sqlbv31.sql(bxNameSQL);
					sqlbv31.put("getdutycode", bxLLClaimDetailSet.get(j).getGetDutyCode());
					sqlbv31.put("getdutykind", bxLLClaimDetailSet.get(j).getGetDutyKind());
					ExeSQL bxnameExeSQL = new ExeSQL();
					String bxName = bxnameExeSQL.getOneValue(sqlbv31);

					double tRealPay = bxLLClaimDetailSet.get(j).getRealPay();

					String[] stra = new String[3];
					stra[0] = "给付项目：" + bxName;// 保项

					if (tGetMode.equals("2")) {
						tRealPay = 0.00;
						fee = 0.00;
					}
					stra[1] = "理赔金额："
							+ new DecimalFormat("0.00").format(tRealPay); // 赔付金额
					stra[2] = "剩余有效保额：" + new DecimalFormat("0.00").format(fee); // lcget表中standmoney
																					// -
																					// summoney
					tListTable.add(stra);// 将保项信息加入ListTable
				}
				
				//红利结算 FeeOperationType = 'C05' 红利结算
				String tHongliSql = "select (case when sum(GetMoney) is null then 0 else sum(GetMoney) end) from llbnf where clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"'"
									+" and bnfno='"+"?bnfno?"+"' and FeeOperationType = 'C05' and bnfkind='B'";
				SQLwithBindVariables sqlbv32 = new SQLwithBindVariables();
				sqlbv32.sql(tHongliSql);
				sqlbv32.put("clmno", mClmNo);
				sqlbv32.put("polno", tPolNo);
				sqlbv32.put("bnfno", tBnfNo);
				ExeSQL tHongLiExeSQL = new ExeSQL();
				String tHongli = tHongLiExeSQL.getOneValue(sqlbv32);
				
				//保单欠款结算
				String tBdqkjsSql = "select (case when sum(pay) is null then 0 else sum(pay) end) from llbalance a where a.feeoperationtype ='C02'"
								+" and clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"'";
				SQLwithBindVariables sqlbv33 = new SQLwithBindVariables();
				sqlbv33.sql(tBdqkjsSql);
				sqlbv33.put("clmno", mClmNo);
				sqlbv33.put("polno", tPolNo);
				ExeSQL tBdqkjsExeSQL = new ExeSQL();
				String tBdqkjs = tBdqkjsExeSQL.getOneValue(sqlbv33);
				
				//保险费结算
				String tBxfjsSql = "select (case when sum(pay) is null then 0 else sum(pay) end) from llbalance a where a.feeoperationtype in('C01','C02','C09','D01','D05','D06')"
									+" and clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"'";
				SQLwithBindVariables sqlbv34 = new SQLwithBindVariables();
				sqlbv34.sql(tBxfjsSql);
				sqlbv34.put("clmno", mClmNo);
				sqlbv34.put("polno", tPolNo);
				ExeSQL tBxfjsExeSQL = new ExeSQL();
				String tBxfjs = tBxfjsExeSQL.getOneValue(sqlbv34);
				
				String tJS[] = new String[3];
				tJS[0] = "红利结算："+tHongli;
				tJS[1] = "保单欠款结算："+tBdqkjs;
				tJS[2] = "保险费结算："+tBxfjs;
				tListTable.add(tJS);
				
				//生存金结算
				String tScjjsSql = "select (case when sum(GetMoney) is null then 0 else sum(GetMoney) end) from llbnf a where FeeOperationType='C04'"
									+" and clmno='"+"?clmno?"+"' and polno='"+"?polno?"+"' and bnfno='"+"?bnfno?"+"' and bnfkind='B'";
				SQLwithBindVariables sqlbv35 = new SQLwithBindVariables();
				sqlbv35.sql(tScjjsSql);
				sqlbv35.put("clmno", mClmNo);
				sqlbv35.put("polno", tPolNo);
				sqlbv35.put("bnfno", tBnfNo);
				ExeSQL tScjjsExeSQL = new ExeSQL();
				String tScjjs = tScjjsExeSQL.getOneValue(sqlbv35);
				
				//合同处理结论
				String tHtcljlSql = "select case appflag when '1' then '承保' when '4' then '终止' when '0' then '投保' end"
									+" from lcpol where polno='"+"?polno?"+"'";
				SQLwithBindVariables sqlbv36 = new SQLwithBindVariables();
				sqlbv36.sql(tHtcljlSql);
				sqlbv36.put("polno", tPolNo);
				ExeSQL tHtcljlExeSQL = new ExeSQL();
				String tHtcljl = tHtcljlExeSQL.getOneValue(sqlbv36);
				
				//险种给付金额
				String tXzjfjeSql = "select sum(realpay) from llclaimdetail"
									+" where polno='"+"?polno?"+"' and clmno='"+"?clmno?"+"'";
				SQLwithBindVariables sqlbv37 = new SQLwithBindVariables();
				sqlbv37.sql(tXzjfjeSql);
				sqlbv37.put("clmno", mClmNo);
				sqlbv37.put("polno", tPolNo);
				ExeSQL tXzjfjeExeSQL = new ExeSQL();
				String tXzjfje = tXzjfjeExeSQL.getOneValue(sqlbv37);
				
				String tJS2[] = new String[3];
				tJS2[0] = "生存金结算："+tScjjs;
				tJS2[1] = "合同处理结论："+tHtcljl;
				tJS2[2] = "险种给付金额："+tXzjfje;
				tListTable.add(tJS2);
			}
			
			//支付方式
//			String tZffsSql = "select CasePayMode from ";
//			ExeSQL tZffsExeSQL = new ExeSQL();
			String tZffs = tLLBnfSchema.getCasePayMode();
			if("1".equals(tZffs)){
				tZffs = "现金";
			}
			if("2".equals(tZffs)){
				tZffs = "现金支票";
			}
			if("3".equals(tZffs)){
				tZffs = "转账支票";
			}
			if("4".equals(tZffs)){
				tZffs = "银行转账";
			}
			if("5".equals(tZffs)){
				tZffs = "内部转账";
			}
			//合计受益金额
			String tHjsyjeSql = "select otherno,GetMoney from LLBnfGather where bnfno = '"+"?bnfno?"
								+"' and clmno='"+"?clmno?"+"' and bnfkind='B'";

			SQLwithBindVariables sqlbv38 = new SQLwithBindVariables();
			sqlbv38.sql(tHjsyjeSql);
			sqlbv38.put("clmno", mClmNo);
			sqlbv38.put("bnfno", tBnfNo);
			ExeSQL tHjsyjeExeSQL = new ExeSQL();
			tSSRS=tHjsyjeExeSQL.execSQL(sqlbv38);
			//String tHjsyje = tHjsyjeExeSQL.getOneValue(tHjsyjeSql);
			
			String tBnfStr3[] = new String[3];
			tBnfStr3[0] = "支付方式："+tZffs;
			tBnfStr3[1] = "实付号："+tSSRS.GetText(1, 1);
			tBnfStr3[2] = "合计受益金额："+tSSRS.GetText(1, 2);
			tListTable.add(tBnfStr3);
			
			//支付银行
			String tBankCode = tLLBnfSchema.getBankCode();
			String tBankSql = "select bankname from ldbank where bankcode='"+"?bankcode?"+"'";
			SQLwithBindVariables sqlbv39 = new SQLwithBindVariables();
			sqlbv39.sql(tBankSql);
			sqlbv39.put("bankcode", tBankCode);
			ExeSQL tBankExeSQL = new ExeSQL();
			String tBankName = tBankExeSQL.getOneValue(sqlbv39);

			if(tBankName==null)
			{
				tBankName="";
			}
			
			//银行账户名
			String tAccName = tLLBnfSchema.getAccName();
			
			if(tAccName==null)
			{
				tAccName="";
			}
			
			//银行账号
			String tBankAccNo = tLLBnfSchema.getBankAccNo();
			
			if(tBankAccNo==null)
			{
				tBankAccNo="";
			}
			
			String tBankStr[] = new String[3];
			tBankStr[0] = "支付银行："+tBankName;
			tBankStr[1] = "银行账户名："+tAccName;
			tBankStr[2] = "银行账号："+tBankAccNo;
			
			tListTable.add(tBankStr);
		}

		tTextTag.add("ZJFJE", amontFee);
		tTextTag.add("DXJE", DXJE);
		tTextTag.add("ZEndCaseDate", endcasedate);
		tXmlExport.addListTable(tListTable, Title);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入单个字段的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 加入多行数据的打印变量
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// xmlexport.addDisplayControl("Risk"); //模版上的主险附加险部分的控制标记
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		String updateStateSql = "update loprtmanager set stateflag='1' where otherno='"
				+ "?clmno?" + "' and code='PCT010'";
		logger.debug("更新打印管理表的sql:" + updateStateSql);
		SQLwithBindVariables sqlbv40 = new SQLwithBindVariables();
		sqlbv40.sql(updateStateSql);
		sqlbv40.put("clmno", mClmNo);
		mMMap.put(sqlbv40, "UPDATE");

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

