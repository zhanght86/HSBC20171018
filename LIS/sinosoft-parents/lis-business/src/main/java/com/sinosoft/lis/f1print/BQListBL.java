/**
 * <p>Title:保全清单统计</p>
 * <p>Description: 5张报表</p>
 * <p>Oper：保全操作日结清单</p>
 * <p>State：保全状态查询清单</p>
 * <p>Loan：保全借款清单</p>
 * <p>Get：生存金领取清单</p>
 * <p>GTMargin：协议退保差值明细清单</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author guoxiang
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class BQListBL
{
private static Logger logger = Logger.getLogger(BQListBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mStartDate = "";
    private String mEndDate = "";
    private String mManageCom = "";
    private String mSaleChnl = "";
    private String mEdorType = "";
    private String mStatType = "";

    public BQListBL(){ }

    /**传输数据的公共方法*/
    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean retFlag = true;
        if (!getInputData(cInputData))
        {
            return false;
        }

        mResult.clear();
        logger.debug("Operate : " + cOperate);
        if(cOperate.equals("Oper"))
            retFlag = getOperData();
        else if(cOperate.equals("State"))
            retFlag = getStateData();
        else if(cOperate.equals("Loan"))
            retFlag = getLoanData();
        else if(cOperate.equals("Get"))
            retFlag = getGetData();
        else if(cOperate.equals("GTMargin"))
            retFlag = getGTMarginData();
        else
        {
            retFlag = false;
            buildError("submitData","无法识别的清单统计类型" + cOperate);
        }

        return retFlag;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",0));
        TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
        if (tTransferData == null || mGlobalInput == null)
        {
            buildError("getInputData", "传入后台的参数缺少！");
            return false;
        }

        mStartDate = (String)tTransferData.getValueByName("StartDate");
        mEndDate = (String)tTransferData.getValueByName("EndDate");
        mManageCom = (String)tTransferData.getValueByName("ManageCom");
        mSaleChnl = (String)tTransferData.getValueByName("SaleChnl");
        mEdorType = (String)tTransferData.getValueByName("EdorType");
        mStatType = (String)tTransferData.getValueByName("StatType");
        if(mStartDate.equals("") || mEndDate.equals("") || mManageCom.equals(""))
        {
            buildError("getInputData", "没有得到足够的查询信息！");
            return false;
        }

        return true;
    }

    /**
     * 获取保全操作日结的清单数据
     * @return
     */
    private boolean getOperData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("BQOperList.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("BQList");
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String sql ="select (select trim(edorname) from lmedoritem where edorcode = a.edortype and appobj='I'),a.edoracceptno,"
                   + "contno,edorno,(select trim(min(appntname)) from lcpol where contno = a.contno),"
                   + "getmoney,(select trim(riskname) from lmriskapp where riskcode =(select distinct  riskcode from lcpol where contno = a.contno and polno=mainpolno)),"
                   + "substr(managecom,1,6),managecom,operator,makedate "
                   + "from lpedoritem a "
                   + "where edorstate = '0' and grpcontno = '00000000000000000000' "
                   + ReportPubFun.getWherePartLike("managecom",ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
                   + " and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
				   + ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)+")"
                   + ReportPubFun.getWherePart("edortype",ReportPubFun.getParameterStr(mEdorType, "?mEdorType?"))
                   + " and exists(select 1 from lcpol where polno = a.polno "
                   + ReportPubFun.getWherePart("salechnl",ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) +") "
                   + "order by edortype,edorno,managecom ";
        sqlbv.sql(sql);
        sqlbv.put("mManageCom", mManageCom);
        sqlbv.put("mStartDate", mStartDate);
        sqlbv.put("mEndDate",mEndDate);
        sqlbv.put("mEdorType", mEdorType);
        sqlbv.put("mSaleChnl", mSaleChnl);
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv);
        if(tssrs == null || tssrs.getMaxRow() <= 0)
        {
            buildError("getOperData","没有取得统计数据" + tssrs.mErrors.getFirstError());
            return false;
        }

        String[] strArr = new String[11];
        for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
        {
            strArr = new String[11];
            strArr[0] = tssrs.GetText(i,1);                                     //保全项目
            strArr[1] = tssrs.GetText(i,2);  //保全受理号                                   //保单号
            strArr[2] = tssrs.GetText(i,3);                                     //批单号
            strArr[3] = tssrs.GetText(i,4);                                     //投保人
            strArr[4] = tssrs.GetText(i,5);                                     //合计金额
            strArr[5] = tssrs.GetText(i,6);                                     //险种名称
            strArr[6] = tssrs.GetText(i,7);                                     //分公司
            strArr[7] = tssrs.GetText(i,8);                                     //管理机构
            strArr[8] = tssrs.GetText(i,9);                                     //操作员代码
            strArr[9] = tssrs.GetText(i,10);   
            strArr[10] = tssrs.GetText(i,11);  //操作时间
            tlistTable.add(strArr);
        }

        String[] tArr = new String[11];
        for (int i = 0 ; i < tArr.length ; i ++)
            tArr[i] = "";
        tlistTable.add(tArr);
        xmlexport.addListTable(tlistTable, tArr);

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
        texttag.add("StartDate", mStartDate);
        texttag.add("EndDate", mEndDate);
        texttag.add("date", PubFun.getCurrentDate());
        logger.debug("大小" + texttag.size());
        if (texttag.size() > 0)
            xmlexport.addTextTag(texttag);

//        xmlexport.outputDocumentToFile("e:\\","BQList.xml");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    /**
     * 获取保全状态查询的清单数据
     * @return
     */
    private boolean getStateData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("BQStateList.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("BQList");
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String sql = "select (select trim(edorname) from lmedoritem where edorcode = a.edortype and appobj='I'),"
                   + "edorstate,contno,edorappno,substr(managecom,1,4),managecom,operator,makedate "
                   + "from lpedoritem a "
                   + "where grpcontno = '00000000000000000000' "
                   + ReportPubFun.getWherePartLike("managecom",ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
                  + " and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
				   + ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)+")"
                   + ReportPubFun.getWherePart("edortype",ReportPubFun.getParameterStr(mEdorType, "?mEdorType?"))
                   + ReportPubFun.getWherePart("edorstate",ReportPubFun.getParameterStr(mStatType, "?mStatType?"))
                   + " and exists(select 1 from lcpol where polno = a.polno "
                   + ReportPubFun.getWherePart("salechnl",ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) +") "
                   + "order by edortype,edorno,managecom ";
        sqlbv.sql(sql);
        sqlbv.put("mManageCom", mManageCom);
        sqlbv.put("mEdorType", mEdorType);
        sqlbv.put("mStartDate", mStartDate);
        sqlbv.put("mEndDate", mEndDate);
        sqlbv.put("mStatType", mStatType);
        sqlbv.put("mSaleChnl", mSaleChnl);
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv);
        if(tssrs == null || tssrs.getMaxRow() <= 0)
        {
            buildError("getStateData","没有取得统计数据" + tssrs.mErrors.getFirstError());
            return false;
        }

        String[] strArr = new String[8];
        for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
        {
            strArr = new String[8];
            strArr[0] = tssrs.GetText(i,1);         //保全项目
            strArr[1] = tssrs.GetText(i,2);         //操作状态                                
            strArr[2] = tssrs.GetText(i,3);         //保单号
            strArr[3] = tssrs.GetText(i,4);         //申请号
            strArr[4] = tssrs.GetText(i,5);         //分公司
            strArr[5] = tssrs.GetText(i,6);                                     //管理机构
            strArr[6] = tssrs.GetText(i,7);                                 //操作员代码
            strArr[7] = tssrs.GetText(i,8); 
                                             //操作日期
            tlistTable.add(strArr);
        }

        String[] tArr = new String[8];
        for (int i = 0 ; i < tArr.length ; i ++)
            tArr[i] = "";
        tlistTable.add(tArr);
        xmlexport.addListTable(tlistTable, tArr);

       

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
        texttag.add("StartDate", mStartDate);
        texttag.add("EndDate", mEndDate);
 
        texttag.add("date", PubFun.getCurrentDate());
        logger.debug("大小" + texttag.size());
        if (texttag.size() > 0)
            xmlexport.addTextTag(texttag);

//        xmlexport.outputDocumentToFile("e:\\","BQList.xml");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    /**
     * 获取保全借款的清单数据
     * @return
     */
    private boolean getLoanData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("BQLoanList.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("BQList");
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String sql = "select contno,edorno,loandate,summoney,"
                   + "(select trim(riskname) from lmriskapp where riskcode in (select riskcode from lcpol where contno = a.contno and polno=mainpolno)),"
                   + "(select managecom from lcpol where contno = a.contno),"
                   + "operator,makedate "
                   + ",SpecifyRate,InterestRate,InterestMode,InterestType,RateCalType,RateCalCode "
                   + "from loloan a "
                   + "where loantype = '0' "
                   + "and exists(select 1 from lcpol where contno = a.contno and grpcontno = '00000000000000000000' "
                   + ReportPubFun.getWherePartLike("managecom",ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
                   + ReportPubFun.getWherePart("salechnl",ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) +") "
                   + "and exists(select 1 from lpedoritem where contno = a.contno and edorno = a.edorno and edortype = 'LN' and edorstate = '0') "
                   + " and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
				   + ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)+")"
                   + ReportPubFun.getWherePart("payoffflag",ReportPubFun.getParameterStr(mStatType, "?mStatType?"))
                   + " order by contno,edorno ";
        sqlbv.sql(sql);
        sqlbv.put("mManageCom", mManageCom);
        sqlbv.put("mSaleChnl", mSaleChnl);
        sqlbv.put("mStartDate",mStartDate);
        sqlbv.put("mEndDate", mEndDate);
        sqlbv.put("mStatType", mStatType);
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv);
        if(tssrs == null || tssrs.getMaxRow() <= 0)
        {
            buildError("getLoanData","没有取得统计数据" + tssrs.mErrors.getFirstError());
            return false;
        }

        double interestMoney = 0,cashValue = 0;                                 //利息，现价
        AccountManage tAccountManage = new AccountManage();                     //计算利息
        VData tVData = new VData();
        String[] strArr = new String[11];
        for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
        {
            LOLoanSchema tLOLoanSchema = new LOLoanSchema();
            tLOLoanSchema.setSpecifyRate(tssrs.GetText(i,9));
            tLOLoanSchema.setInterestRate(tssrs.GetText(i,10));
            tLOLoanSchema.setInterestMode(tssrs.GetText(i,11));
            tLOLoanSchema.setInterestType(tssrs.GetText(i,12));
            tLOLoanSchema.setRateCalType(tssrs.GetText(i,13));
            tLOLoanSchema.setRateCalCode(tssrs.GetText(i,14));

            tVData.clear();
            tVData.add(tLOLoanSchema);
            interestMoney = tAccountManage.getMultiInterest("1",tVData,Double.parseDouble(tssrs.GetText(i,4)),tssrs.GetText(i,3), mEndDate);

//          有待讨论确认
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//            tLPEdorItemSchema.setEdorNo(tssrs.GetText(i,2));
//            tLPEdorItemSchema.setPolNo(tssrs.GetText(i,1));
//            tLPEdorItemSchema.setEdorType("LN");
//
//            LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
//            tLPEdorMainDB.setSchema(tLPEdorMainSchema);
//            LPEdorMainSet tLPEdorMainSet = (LPEdorMainSet)tLPEdorMainDB.query();
//            if(tLPEdorMainSet.size() > 0)
//            {
//                tLPEdorMainSchema = tLPEdorMainSet.get(1);
//                tVData.clear();
//                tVData.add(tLPEdorMainSchema);
//                tVData.add(mGlobalInput);
//
//                PEdorForeZTBL tPEdorForeZTBL = new PEdorForeZTBL();
//                if(tPEdorForeZTBL.submitData(tVData,""))
//                {
//                    tVData = tPEdorForeZTBL.getResult();
//                    cashValue = Double.parseDouble((String)tVData.getObject(1));
//                }
//            }

            strArr = new String[11];
            strArr[0] = tssrs.GetText(i,1);                                     //保单号
            strArr[1] = tssrs.GetText(i,2);                                     //批单号
            strArr[2] = tssrs.GetText(i,3);                                     //借款日期
            strArr[3] = tssrs.GetText(i,4);                                     //借款金额
            strArr[4] = ReportPubFun.functionJD(interestMoney,"0.00");          //借款利息
            strArr[5] = (cashValue < 0) ? "Y" : "N";                            //现价净额是否小于0
            strArr[6] = tssrs.GetText(i,5);                                     //险种名称
            strArr[7] = tssrs.GetText(i,6).substring(0,4);                      //分公司代码
            strArr[8] = tssrs.GetText(i,6);                                     //管理机构
            strArr[9] = tssrs.GetText(i,7);                                     //操作员代码
            strArr[10] = tssrs.GetText(i,8);                                    //操作日期
            tlistTable.add(strArr);
        }

        String[] tArr = new String[11];
        for (int i = 0 ; i < tArr.length ; i ++)
            tArr[i] = "";
        tlistTable.add(tArr);
        xmlexport.addListTable(tlistTable, tArr);

        String tStatType = "有借款";
        if(mStatType.equals("0"))
            tStatType = "有借款已还款";
        else if(mStatType.equals("1"))
            tStatType = "有借款未还款";

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
        texttag.add("StartDate", mStartDate);
        texttag.add("EndDate", mEndDate);
        texttag.add("StatType", tStatType);
        texttag.add("date", PubFun.getCurrentDate());
        logger.debug("大小" + texttag.size());
        if (texttag.size() > 0)
            xmlexport.addTextTag(texttag);

//        xmlexport.outputDocumentToFile("e:\\","BQLoanList");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    /**
     * 获取生存金领取的清单数据
     * @return
     */
    private boolean getGetData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("BQGetList.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("BQList");

        String sql = "",asql = "",bsql = "";
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        //生存金已领取清单
        asql = "select polno,getnoticeno,(select appntname from lccont where contno = a.contno),"
             + "(select trim(riskname) from lmriskapp where riskcode = a.riskcode),"
             + "(select min(trim(name)) from lcbnf where polno = a.polno and bnftype = '0'),"
             + "(select trim(getdutyname) from lmdutygetalive where getdutycode = a.getdutycode and getdutykind = a.getdutykind),"
             + "(select count(polno) from ljagetdraw where polno = a.polno),getmoney,lastgettodate,curgettodate,"
             + "(case getchannelflag when 0 then '银行转账' when 1 then '柜台领取' else '' end),substr(managecom,1,4),managecom,operator,makedate "
             + "from ljagetdraw a "
             + "where grppolno = '00000000000000000000' "
             + "and exists(select 1 from lcpol where polno = a.polno "
             + ReportPubFun.getWherePart("salechnl",ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) +") "
             + ReportPubFun.getWherePartLike("managecom",ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
             + ReportPubFun.getWherePart("confdate",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
               ;
        //生存金应领未领清单
        bsql = "select polno,getnoticeno,(select appntname from lccont where contno = a.contno),"
             + "(select trim(riskname) from lmriskapp where riskcode = a.riskcode),"
             + "(select min(trim(name)) from lcbnf where polno = a.polno and bnftype = '0'),"
             + "(select trim(getdutyname) from lmdutygetalive where getdutycode = a.getdutycode and getdutykind = a.getdutykind),"
             + "(select count(polno) from ljagetdraw where polno = a.polno),getmoney,lastgettodate,curgettodate,"
             + "(case getchannelflag when 0 then '银行转账' when 1 then '柜台领取' else '' end),substr(managecom,1,4),managecom,operator,makedate "
             + "from ljagetdraw a "
             + "where grppolno = '00000000000000000000' "
             + "and exists(select 1 from lcpol where polno = a.polno "
             + ReportPubFun.getWherePart("salechnl",ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) +") "
             + ReportPubFun.getWherePartLike("managecom",ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
             + ReportPubFun.getWherePart("makedate",ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1);
        if(mStatType.equals(""))
            sql = asql + " union all " + bsql;
        else if(mStatType.equals("0"))
            sql = asql;
        else if(mStatType.equals("1"))
            sql = bsql;
        sqlbv.sql(sql);
        sqlbv.put("mSaleChnl", mSaleChnl);
        sqlbv.put("mManageCom", mManageCom);
        sqlbv.put("mStartDate", mStartDate);
        sqlbv.put("mEndDate", mEndDate);
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv);
        if(tssrs == null || tssrs.getMaxRow() <= 0)
        {
            buildError("getGetData","没有取得统计数据" + tssrs.mErrors.getFirstError());
            return false;
        }

        String[] strArr = new String[15];
        for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
        {
            strArr = new String[15];
            strArr[0] = tssrs.GetText(i,1);                                     //保单号
            strArr[1] = tssrs.GetText(i,2);                                     //领取通知书号
            strArr[2] = tssrs.GetText(i,3);                                     //投保人
            strArr[3] = tssrs.GetText(i,4);                                     //险种名称
            strArr[4] = tssrs.GetText(i,5);                                     //生存金受益人
            strArr[5] = tssrs.GetText(i,6);                                     //领取项目
            strArr[6] = tssrs.GetText(i,7);                                     //领取次数
            strArr[7] = tssrs.GetText(i,8);                                     //领取标准
            strArr[8] = tssrs.GetText(i,9);                                     //应领日期
            strArr[9] = tssrs.GetText(i,10);                                    //领取日期
            strArr[10] = tssrs.GetText(i,11);                                   //支付方式
            strArr[11] = tssrs.GetText(i,12);                                   //分公司机构代码
            strArr[12] = tssrs.GetText(i,13);                                   //管理机构
            strArr[13] = tssrs.GetText(i,14);                                   //操作员代码
            strArr[14] = tssrs.GetText(i,15);                                   //操作日期
            tlistTable.add(strArr);
        }

        String[] tArr = new String[15];
        for (int i = 0 ; i < tArr.length ; i ++)
            tArr[i] = "";
        tlistTable.add(tArr);
        xmlexport.addListTable(tlistTable, tArr);

        String tStatType = "生存金领取清单";
        if(mStatType.equals("0"))
            tStatType = "已领取清单";
        else if(mStatType.equals("1"))
            tStatType = "应领未领取清单";

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
        texttag.add("StartDate", mStartDate);
        texttag.add("EndDate", mEndDate);
        texttag.add("StatType", tStatType);
        texttag.add("date", PubFun.getCurrentDate());
        logger.debug("大小" + texttag.size());
        if (texttag.size() > 0)
            xmlexport.addTextTag(texttag);

//        xmlexport.outputDocumentToFile("e:\\","BQGetList");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    /**
     * 获取协议退保差值明细清单数据
     * @return
     */
    private boolean getGTMarginData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("BQGTMarginList.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("BQList");
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String sql = "select contno,edorno,(select appntname from lccont where contno = a.contno),"
                   + "(select trim(riskname) from lmriskapp where riskcode in (select distinct  riskcode from lcpol where  contno = a.contno and polno=mainpolno)),"
                   + "(select min(cvalidate) from lcpol where contno = a.contno and polno=mainpolno),"
                   + "abs(getmoney),abs(getmoney)-(case when StandbyFlag3 is not null then StandbyFlag3  else 0 end),(case when StandbyFlag3 is not null then StandbyFlag3  else 0 end),(select max(agentcode) from lcpol where contno = a.contno and polno=mainpolno),"
                   + "substr(managecom,1,4),managecom,operator,makedate "
                   + "from lpedoritem a "
                   + "where edortype = 'XT' and edorstate = '0' and grpcontno = '00000000000000000000' "
                   + "and exists(select 1 from lcpol where contno = a.contno "
                   + ReportPubFun.getWherePart("salechnl",ReportPubFun.getParameterStr(mSaleChnl, "?mSaleChnl?")) +") "
                   + ReportPubFun.getWherePartLike("managecom",ReportPubFun.getParameterStr(mManageCom, "?mManageCom?"))
                 + " and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
				   + ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)+")"
                   + " order by managecom,edorno,contno ";
        sqlbv.sql(sql);
        sqlbv.put("mSaleChnl", mSaleChnl);
        sqlbv.put("mManageCom", mManageCom);
        sqlbv.put("mStartDate", mStartDate);
        sqlbv.put("mEndDate", mEndDate);
        logger.debug(sql);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv);
        if(tssrs == null || tssrs.getMaxRow() <= 0)
        {
            buildError("getGTMarginData","统计数据为空" + tssrs.mErrors.getFirstError());
            return false;
        }

        double cashValue = 0;
        String[] strArr = new String[14];
        for (int i = 1 ; i <= tssrs.getMaxRow(); i++)
        {
            

            strArr = new String[14];
            strArr[0] = tssrs.GetText(i,1);                                     //保单号
            strArr[1] = tssrs.GetText(i,2);                                     //批单号
            strArr[2] = tssrs.GetText(i,3);                                     //投保人
            strArr[3] = tssrs.GetText(i,4);                                     //险种名称
            strArr[4] = tssrs.GetText(i,5);                                     //保单生效日期
            strArr[5] = tssrs.GetText(i,6);                                     //退费金额
            strArr[6] = ReportPubFun.functionJD(Double.parseDouble(tssrs.GetText(i,7)),"0.00");              //实际现价
            strArr[7] = ReportPubFun.functionJD(Double.parseDouble(tssrs.GetText(i,8)),"0.00"); //差值
            strArr[8] = ReportPubFun.getAgentName(tssrs.GetText(i,9));          //代理人
            strArr[9] = tssrs.GetText(i,9);                                     //代理人编码
            strArr[10] = tssrs.GetText(i,10);                                    //分公司
            strArr[11] = tssrs.GetText(i,11);                                    //管理机构
            strArr[12] = tssrs.GetText(i,12);                                   //操作员代码
            strArr[13] = tssrs.GetText(i,13);                                   //操作日期
            tlistTable.add(strArr);
        }

        String[] tArr = new String[7];
        for (int i = 0 ; i < tArr.length ; i ++)
            tArr[i] = "";
        tlistTable.add(tArr);
        xmlexport.addListTable(tlistTable, tArr);

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
        texttag.add("StartDate", mStartDate);
        texttag.add("EndDate", mEndDate);
        texttag.add("date", PubFun.getCurrentDate());
        logger.debug("大小" + texttag.size());
        if (texttag.size() > 0)
            xmlexport.addTextTag(texttag);

//        xmlexport.outputDocumentToFile("e:\\","BQMarginList");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "BQListBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    public static void main (String []args)
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ComCode = "86";
        tGlobalInput.ManageCom = "8611";
        tGlobalInput.Operator = "DEBUG";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("StartDate","2007-01-01");
        tTransferData.setNameAndValue("EndDate","2007-01-31");
        tTransferData.setNameAndValue("ManageCom","8611");
        tTransferData.setNameAndValue("SaleChnl","02");
        tTransferData.setNameAndValue("EdorType","");
        tTransferData.setNameAndValue("StatType","1");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        BQListBL tBQListBL = new BQListBL();
        tBQListBL.submitData(tVData,"Oper");
    }
}
