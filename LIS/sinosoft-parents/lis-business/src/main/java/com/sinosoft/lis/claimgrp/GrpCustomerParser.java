/*
 * @(#)LCPolParser.java	2005-01-15
 *
 * Copyright 2005 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */

package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.util.*;
import org.w3c.dom.*;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.objects.XObject;



import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;


/**
 * <p>Title: Web业务系统 </p>
 * <p>Description: 个单磁盘投保的解析类，从一个XML数据流中解析出被保险人信息，
 * 险种保单，保费项信息，责任项信息，受益人信息 </p>
 * <p>Copyright: Copyright (c) 2005 </p>
 * <p>Company: Sinosoft </p>
 * @author zhangtao
 * @version 1.0
 * @date 2005-01-15
 */
public class GrpCustomerParser
{
private static Logger logger = Logger.getLogger(GrpCustomerParser.class);

    //private GrpCustomerGuideReportIn GCGRI = new GrpCustomerGuideReportIn();
    //投保人 XML Path
    private static String PATH_APPNT = "ROW";
    private static String PATH_APPNT_AppntID = "AppntID";
    private static String PATH_APPNT_ContID = "ContID";
    private static String PATH_APPNT_CustomerNo = "CustomerNo";
    private static String PATH_APPNT_Name = "Name";
    private static String PATH_APPNT_Sex = "Sex";
    private static String PATH_APPNT_Birthday = "Birthday";
    private static String PATH_APPNT_IDType = "IDType";
    private static String PATH_APPNT_IDNo = "IDNo";
    private static String PATH_APPNT_NativePlace = "NativePlace";
    private static String PATH_APPNT_RgtAddress = "RgtAddress";
    private static String PATH_APPNT_Marriage = "Marriage";
    private static String PATH_APPNT_Nationality = "Nationality";
    private static String PATH_APPNT_OccupationCode = "OccupationCode";
    private static String PATH_APPNT_OccupationType = "OccupationType";
    private static String PATH_APPNT_WorkType = "WorkType";
    private static String PATH_APPNT_PluralityType = "PluralityType";
    private static String PATH_APPNT_BankCode = "BankCode";
    private static String PATH_APPNT_BankAccNo = "BankAccNo";
    private static String PATH_APPNT_AccName = "AccName";
    private static String PATH_APPNT_SmokeFlag = "SmokeFlag";
    private static String PATH_APPNT_PostalAddress = "PostalAddress";
    private static String PATH_APPNT_ZipCode = "ZipCode";
    private static String PATH_APPNT_Phone = "Phone";
    private static String PATH_APPNT_Fax = "Fax";
    private static String PATH_APPNT_Mobile = "Mobile";
    private static String PATH_APPNT_EMail = "EMail";
    private static String PATH_APPNT_HomeAddress = "HomeAddress";
    private static String PATH_APPNT_HomeZipCode = "HomeZipCode";
    private static String PATH_APPNT_HomePhone = "HomePhone";
    private static String PATH_APPNT_HomeFax = "HomeFax";
    private static String PATH_APPNT_CompanyAddress = "CompanyAddress";
    private static String PATH_APPNT_CompanyZipCode = "CompanyZipCode";
    private static String PATH_APPNT_CompanyPhone = "CompanyPhone";
    private static String PATH_APPNT_CompanyFax = "CompanyFax";
    private static String PATH_APPNT_GrpNo = "GrpNo";
    private static String PATH_APPNT_JoinCompanyDate = "JoinCompanyDate";
    private static String PATH_APPNT_Degree = "Degree";
    private static String PATH_APPNT_Position = "Position";
    private static String PATH_APPNT_Salary = "Salary";

    //被保险人 XML Path
    private static String PATH_INSURED = "ROW";
    private static String PATH_INSURED_InsuredID = "InsuredID";
    private static String PATH_INSURED_ContID = "ContID";
    private static String PATH_INSURED_CustomerNo = "CustomerNo";
    private static String PATH_INSURED_Name = "Name";
    private static String PATH_INSURED_Sex = "Sex";
    private static String PATH_INSURED_Birthday = "Birthday";
    private static String PATH_INSURED_IDType = "IDType";
    private static String PATH_INSURED_IDNo = "IDNo";
    private static String PATH_INSURED_NativePlace = "NativePlace";
    private static String PATH_INSURED_RgtAddress = "RgtAddress";
    private static String PATH_INSURED_Marriage = "Marriage";
    private static String PATH_INSURED_Nationality = "Nationality";
    private static String PATH_INSURED_OccupationCode = "OccupationCode";
    private static String PATH_INSURED_OccupationType = "OccupationType";
    private static String PATH_INSURED_WorkType = "WorkType";
    private static String PATH_INSURED_PluralityType = "PluralityType";
    private static String PATH_INSURED_BankCode = "BankCode";
    private static String PATH_INSURED_BankAccNo = "BankAccNo";
    private static String PATH_INSURED_AccName = "AccName";
    private static String PATH_INSURED_SmokeFlag = "SmokeFlag";
    private static String PATH_INSURED_PostalAddress = "PostalAddress";
    private static String PATH_INSURED_ZipCode = "ZipCode";
    private static String PATH_INSURED_Phone = "Phone";
    private static String PATH_INSURED_Fax = "Fax";
    private static String PATH_INSURED_Mobile = "Mobile";
    private static String PATH_INSURED_EMail = "EMail";
    private static String PATH_INSURED_HomeAddress = "HomeAddress";
    private static String PATH_INSURED_HomeZipCode = "HomeZipCode";
    private static String PATH_INSURED_HomePhone = "HomePhone";
    private static String PATH_INSURED_HomeFax = "HomeFax";
    private static String PATH_INSURED_CompanyAddress = "CompanyAddress";
    private static String PATH_INSURED_CompanyZipCode = "CompanyZipCode";
    private static String PATH_INSURED_CompanyPhone = "CompanyPhone";
    private static String PATH_INSURED_CompanyFax = "CompanyFax";
    private static String PATH_INSURED_GrpNo = "GrpNo";
    private static String PATH_INSURED_JoinCompanyDate = "JoinCompanyDate";
    private static String PATH_INSURED_Degree = "Degree";
    private static String PATH_INSURED_Position = "Position";
    private static String PATH_INSURED_Salary = "Salary";

    //险种保单 XML Path
    private static String PATH_LCPOL = "ROW";
    private static String PATH_LCPOL_PolID = "PolID";
    private static String PATH_LCPOL_ContID = "ContID";
    private static String PATH_LCPOL_PrtNo = "PrtNo";
    private static String PATH_LCPOL_RiskCode = "RiskCode";
    private static String PATH_LCPOL_FamilyType = "FamilyType";
    private static String PATH_LCPOL_MainPolID = "MainPolID";
    private static String PATH_LCPOL_InsuredID = "InsuredID";
    private static String PATH_LCPOL_RelationToAppnt = "RelationToAppnt";
    private static String PATH_LCPOL_RelationToMainInsured = "RelationToMainInsured";
    private static String PATH_LCPOL_AppntID = "AppntID";
    private static String PATH_LCPOL_RelaId = "RelaId";
    private static String PATH_LCPOL_PolApplyDate = "PolApplyDate";
    private static String PATH_LCPOL_CValiDate = "CValiDate";
    private static String PATH_LCPOL_FirstTrialOperator = "FirstTrialOperator";
    private static String PATH_LCPOL_FirstTrialDate = "FirstTrialDate";
    private static String PATH_LCPOL_FirstTrialTime = "FirstTrialTime";
    private static String PATH_LCPOL_ReceiveOperator = "ReceiveOperator";
    private static String PATH_LCPOL_ReceiveDate = "ReceiveDate";
    private static String PATH_LCPOL_ReceiveTime = "ReceiveTime";
    private static String PATH_LCPOL_TempFeeNo = "TempFeeNo";
    private static String PATH_LCPOL_PayMode = "PayMode";
    private static String PATH_LCPOL_LiveGetMode = "LiveGetMode";
    private static String PATH_LCPOL_DeadGetMode = "DeadGetMode";
    private static String PATH_LCPOL_PayIntv = "PayIntv";
    private static String PATH_LCPOL_InsuYear = "InsuYear";
    private static String PATH_LCPOL_InsuYearFlag = "InsuYearFlag";
    private static String PATH_LCPOL_PayEndYear = "PayEndYear";
    private static String PATH_LCPOL_PayEndYearFlag = "PayEndYearFlag";
    private static String PATH_LCPOL_GetYear = "GetYear";
    private static String PATH_LCPOL_GetYearFlag = "GetYearFlag";
    private static String PATH_LCPOL_GetStartType = "GetStartType";
    private static String PATH_LCPOL_GetDutyKind = "GetDutyKind";
    private static String PATH_LCPOL_BonusGetMode = "BonusGetMode";
    private static String PATH_LCPOL_PremToAmnt = "PremToAmnt";
    private static String PATH_LCPOL_Mult = "Mult";
    private static String PATH_LCPOL_Prem = "Prem";
    private static String PATH_LCPOL_Amnt = "Amnt";
    private static String PATH_LCPOL_CalRule = "CalRule";
    private static String PATH_LCPOL_FloatRate = "FloatRate";
    private static String PATH_LCPOL_GetLimit = "GetLimit";
    private static String PATH_LCPOL_GetRate = "GetRate";
    private static String PATH_LCPOL_HealthCheckFlag = "HealthCheckFlag";
    private static String PATH_LCPOL_OutPayFlag = "OutPayFlag";
    private static String PATH_LCPOL_ManageCom = "ManageCom";
    private static String PATH_LCPOL_SaleChnl = "SaleChnl";
    private static String PATH_LCPOL_AgentCode= "AgentCode";
    private static String PATH_LCPOL_AgentCom = "AgentCom";
    private static String PATH_LCPOL_BankWorkSite = "BankWorkSite";
    private static String PATH_LCPOL_StandbyFlag1 = "StandbyFlag1";
    private static String PATH_LCPOL_StandbyFlag2 = "StandbyFlag2";
    private static String PATH_LCPOL_StandbyFlag3 = "StandbyFlag3";

    //责任项 XML Path
    private static String PATH_SUBDUTY = "SUBDUTYTABLE/ROW";
    private static String PATH_SUBDUTY_PolID = "PolID";
    private static String PATH_SUBDUTY_InsuredID = "InsuredID";
    private static String PATH_SUBDUTY_RiskCode = "RiskCode";
    private static String PATH_SUBDUTY_DutyCode = "DutyCode";
    private static String PATH_SUBDUTY_PayIntv = "PayIntv";
    private static String PATH_SUBDUTY_InsuYear = "InsuYear";
    private static String PATH_SUBDUTY_InsuYearFlag = "InsuYearFlag";
    private static String PATH_SUBDUTY_PayEndYear = "PayEndYear";
    private static String PATH_SUBDUTY_PayEndYearFlag = "PayEndYearFlag";
    private static String PATH_SUBDUTY_GetYear = "GetYear";
    private static String PATH_SUBDUTY_GetYearFlag = "GetYearFlag";
    private static String PATH_SUBDUTY_GetStartType = "GetStartType";
    private static String PATH_SUBDUTY_GetDutyKind = "GetDutyKind";
    private static String PATH_SUBDUTY_BonusGetMode = "BonusGetMode";
    private static String PATH_SUBDUTY_PremToAmnt = "PremToAmnt";
    private static String PATH_SUBDUTY_Mult = "Mult";
    private static String PATH_SUBDUTY_Prem = "Prem";
    private static String PATH_SUBDUTY_Amnt = "Amnt";
    private static String PATH_SUBDUTY_CalRule = "CalRule";
    private static String PATH_SUBDUTY_FloatRate = "FloatRate";
    private static String PATH_SUBDUTY_GetLimit = "GetLimit";
    private static String PATH_SUBDUTY_GetRate = "GetRate";
    private static String PATH_SUBDUTY_StandbyFlag1 = "StandbyFlag1";
    private static String PATH_SUBDUTY_StandbyFlag2 = "StandbyFlag2";
    private static String PATH_SUBDUTY_StandbyFlag3 = "StandbyFlag3";

    //受益人 XML Path
    private static String PATH_BNF = "ROW";
    private static String PATH_BNF_ContId = "ContId";
    private static String PATH_BNF_PolID = "PolID";
    private static String PATH_BNF_InsuredId = "InsuredId";
    private static String PATH_BNF_RiskCode = "RiskCode";
    private static String PATH_BNF_BnfType = "BnfType";
    private static String PATH_BNF_Name = "Name";
    private static String PATH_BNF_Sex = "Sex";
    private static String PATH_BNF_IDType = "IDType";
    private static String PATH_BNF_IDNo = "IDNo";
    private static String PATH_BNF_Birthday = "Birthday";
    private static String PATH_BNF_RelationToInsured = "RelationToInsured";
    private static String PATH_BNF_BnfGrade = "BnfGrade";
    private static String PATH_BNF_BnfLot = "BnfLot";

    //客户告知 XML Path
    private static String PATH_IMPART = "ROW";
    private static String PATH_IMPART_ContId = "ContId";
    private static String PATH_IMPART_CustomerNoType = "CustomerNoType";
    private static String PATH_IMPART_CustomerNo = "CustomerNo";
    private static String PATH_IMPART_ImpartVer = "ImpartVer";
    private static String PATH_IMPART_ImpartCode = "ImpartCode";
    private static String PATH_IMPART_ImpartParamModle = "ImpartParamModle";
    private static String PATH_IMPART_DiseaseContent = "DiseaseContent";
    private static String PATH_IMPART_StartDate = "StartDate";
    private static String PATH_IMPART_EndDate = "EndDate";
    private static String PATH_IMPART_Prover = "Prover";
    private static String PATH_IMPART_CurrCondition = "CurrCondition";
    private static String PATH_IMPART_IsProved = "IsProved";

    //问题件 XML Path
    private static String PATH_ISSUE = "ROW";
    private static String PATH_ISSUE_ContId = "ContId";
    private static String PATH_ISSUE_BackObjType = "BackObjType";
    private static String PATH_ISSUE_IssueCode = "IssueCode";
    private static String PATH_ISSUE_IssueCont = "IssueCont";

    public CErrors mErrors = new CErrors();
    public CError tError = new CError();
    /**
     * 构造函数
     */
    public GrpCustomerParser()
    {

    }


    /**
     * 解析一个DOM树的节点，在这个节点中，包含个人险种保单的所有信息。
     * @param node 要解析的节点
     * @param vReturn 存放返回数据的VData
     * @return
     */
    public boolean parseOneNode(Node node,
                                VData vReturn)
    {
        try
        {
            if (vReturn == null)
            {
                buildError("parseOneNode", "存放返回数据的VData是非法的");
                return false;
            }

            // 一些特殊的信息
            TransferData tTransferData = getTransferData(node);


            String id = (String) tTransferData.getValueByName("ID");


            // 返回数据
            vReturn.add(tTransferData);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * 利用XPathAPI取得某个节点的节点值
     * @param node
     * @param strPath
     * @return
     */
    private static String parseNode(Node node,
                                    String strPath)
    {
        String strValue = "";

        try
        {
            XObject xobj = XPathAPI.eval(node, strPath);
            strValue = xobj == null ? "" : xobj.toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return strValue;
    }

    /**
     * 得到一些不好用Schema表示的信息
     * @param node Node
     * @return TransferData
     */
    private TransferData getTransferData(Node node)
    {
        TransferData tTransferData = new TransferData();

        tTransferData.setNameAndValue("GetDutyKind", parseNode(node,PATH_LCPOL_GetDutyKind));
        tTransferData.setNameAndValue("ID", parseNode(node, PATH_LCPOL_PolID));
        return tTransferData;
    }

    /**
     * 从XML中解析立案信息
     * @param node
     * @return
     */
    public VData getLLCaseData(Node node)
    {
        VData tLLCaseVData = new VData();
        LLCaseSchema tLLCaseSchema = new LLCaseSchema();
        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema(); //立案表
        LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //理赔类型表
        LLAppClaimReasonSet tLLAppClaimReasonSet = new LLAppClaimReasonSet(); //理赔类型集合
        ExeSQL tExeSQL = new ExeSQL();

        String ID             = parseNode(node, "CaseID");
        String tGrpCustomerNo = parseNode(node, "GrpCustomerNo"); //团体客户号
        String tGrpContNo     = parseNode(node, "GrpContNo");     //团体保单号
        String tRiskCode     = parseNode(node, "RiskCode");     //保单险种号
        String tCustomerNo    = parseNode(node, "CustomerNo");    //出险人客户号
        String tCustomerName  = parseNode(node, "CustomerName");  //出险人名称
        String tCustomerSex   = parseNode(node, "CustomerSex");   //出险人性别
        String tIDType        = parseNode(node, "IDType");        //出险人证件类型
        String tIDNo          = parseNode(node, "IDNo");          //出险人证件号码
        String tAppReasonCode = parseNode(node, "AppReasonCode");          //出险类型
        String tOccurReason   = parseNode(node, "OccurReason");          //出险原因
        String tAccidentDate  = parseNode(node, "AccidentDate");  //出险日期
        String tRgtNo         = parseNode(node, "RgtNo");    //赔案号

        String tGrpName       = "";
        String tPeoples2      = "";
        String tCustomerNo2   = "";                               //处理一个出险人有两个客户号的问题
        logger.debug("ID             === "+ID);
       logger.debug("tGrpCustomerNo === "+tGrpCustomerNo);
       logger.debug("tGrpContNo     === "+tGrpContNo);
       logger.debug("tCustomerNo    === "+tCustomerNo);
       logger.debug("tRgtNo  ========== "+tRgtNo);

            //通过团体客户号或团体保单号取到团体立案相关信息
            String strSql2 = "  select a.customerno,a.name,g.grpcontno,g.Peoples2 from lcgrpcont g, LCGrpAppnt a"
                           + " where a.grpcontno = g.grpcontno and g.appflag in ('1','4')";
            if (tGrpContNo != null && !tGrpContNo.equals("")) {
                strSql2 += " and g.GrpContNo = '" + tGrpContNo + "'";
            }
            if (tGrpCustomerNo != null && !tGrpCustomerNo.equals("")) {
                strSql2 += " and g.AppntNo = '" + tGrpCustomerNo + "'";
            }
            SSRS tSSRS2 = tExeSQL.execSQL(strSql2);
            if (tSSRS2.getMaxRow() > 0) {
                //for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
                tGrpCustomerNo = tSSRS2.GetText(1, 1); //保单险种号
                tGrpName = tSSRS2.GetText(1, 2); //单位名称
                tGrpContNo = tSSRS2.GetText(1, 3); //团体保单号
                tPeoples2 = tSSRS2.GetText(1, 4); //投保总人数
                //}
            } else {
                if (tGrpContNo != null && !tGrpContNo.equals("")) {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseData";
                    tError.errorMessage =  "未查询到以保单：" + tGrpContNo + "相关的团体信息！";
                    this.mErrors.addOneError(tError);
                } else {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseData";
                    tError.errorMessage = "未查询到团体客户号：" + tGrpCustomerNo + "相关的团体信息！";
                    this.mErrors.addOneError(tError);
                }
            }

            //通过客户号或客户姓名证件类型以及证件号码得到客户信息
            /*
            String strSql3 =
                    "select CustomerNo,Name,Sex,IDType,IDNo from ldperson where";
            if (tCustomerNo != null && !tCustomerNo.equals("")) {
                strSql3 += " CustomerNo = '" + tCustomerNo + "'";
            } else if (tIDType.equals("0")) { //身份证
                strSql3 += " Name = '" + tCustomerName + "' and IDNo = '" +
                        tIDNo + "' and IDType = '" + tIDType + "'";
            } else if(tIDType==null && tIDType.equals("")){
                strSql3 += " Name = '" + tCustomerName + "' and Birthday = '" +
                        tBirthday + "' and Sex = '" + tCustomerSex + "'";
            } else {
                strSql3 += " Name = '" + tCustomerName + "' and IDNo = '" +
                        tIDNo + "' and IDType = '" + tIDType + "' and Sex = '" +
                        tCustomerSex + "'";
            }
            SSRS tSSRS3 = tExeSQL.execSQL(strSql3);
            if(tSSRS3.getMaxRow() <= 0 ){
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getLLCaseData";
                tError.errorMessage = "未查询到该客户的信息！";
                this.mErrors.addOneError(tError);

                tLLCaseVData.add(ID);
                tLLCaseVData.add(tCustomerNo);
                tLLCaseVData.add(tCustomerName);
            }

            if (tGrpContNo==null ||tGrpContNo.equals(""))
            {
                CError tError = new CError();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getLLCaseData";
                tError.errorMessage = "未查询到该客户的信息！";
                this.mErrors.addOneError(tError);
                return tLLCaseVData;

            }
            */
            String strSql3 =
                    "select InsuredNo,Name,Sex,IDType,IDNo from lcinsured where" ;
                   strSql3=strSql3 + " GrpContNo='"+tGrpContNo +"'";

            if (tCustomerNo != null && !tCustomerNo.equals(""))
            {
                strSql3 += " and InsuredNo = '" + tCustomerNo + "'";
            }
            else
            {
                strSql3 += " and Name = '" + tCustomerName + "'";
                if (tIDNo!=null && !tIDNo.equals(""))
                {
                    strSql3 += " and IDNo = '" + tIDNo + "'";
                }
                if (tCustomerSex!=null && !tCustomerSex.equals(""))
                {
                    strSql3 += " and Sex = '" + tCustomerSex + "'";
                }
//                if (tBirthday!=null && !tBirthday.equals(""))
//                {
//                    strSql3 += " and birthday = '" + tBirthday + "'";
//                }

            }
            SSRS tSSRS3 = tExeSQL.execSQL(strSql3);
            if(tSSRS3.getMaxRow() <= 0 ){
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getLLCaseData";
                tError.errorMessage = "未查询到该客户的信息！";
                this.mErrors.addOneError(tError);

                tLLCaseVData.add(ID);
                tLLCaseVData.add(tCustomerNo);
                tLLCaseVData.add(tCustomerName);
                tLLCaseVData.add(tRgtNo);
            }
            if(tSSRS3.getMaxRow() >1 ){
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseData";
                    tError.errorMessage = "查询到该客户在该保单下有重名的信息！";
                    this.mErrors.addOneError(tError);


                }


            for(int j = 1 ; j <= tSSRS3.getMaxRow() ; j++) {
                tCustomerNo = tSSRS3.GetText(1, 1); //出险人编码
                tCustomerNo2 = tSSRS3.GetText(j, 1); //出险人编码 处理一个出险人有两个客户号的问题
                tCustomerName = tSSRS3.GetText(1, 2); //出险人名称
                tCustomerSex = tSSRS3.GetText(1, 3); //出险人性别
                tIDType = tSSRS3.GetText(1, 4); //出险人证件类型
                tIDNo = tSSRS3.GetText(1, 5); //出险人证件号码
            }


            if(tSSRS3.getMaxRow() > 0 && tCustomerNo!=null && !tCustomerNo.equals("")){//客户信息不全时不能查询
              //通过团体客户号或团体保单号判断是否在保险期间内
                String strSql4 =
                        " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='" +
                        tAccidentDate + "' and a.enddate>='" +
                        tAccidentDate +
                        "' and a.GrpContNo=b.GrpContNo and a.appflag in ('1','4')"
                        + "  and insuredno='" + tCustomerNo + "'";
                if (tGrpCustomerNo != null && !tGrpCustomerNo.equals("")) {
                    strSql4 = strSql4 + " and b.AppntNo='" + tGrpCustomerNo + "'";
                }
                if (tGrpContNo != null && !tGrpContNo.equals("")) {
                    strSql4 = strSql4 + " and b.GrpContNo='" + tGrpContNo + "'";
                }

                SSRS tSSRS4 = tExeSQL.execSQL(strSql4);
                if(tSSRS4.getMaxRow() <= 0){
                    // @@错误处理
                    CError tError = new CError();
                   // this.mErrors.clearErrors();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseData";
                    tError.errorMessage = "该客户号的出险日期不在保单保险期间内";
                    this.mErrors.addOneError(tError);

                    tLLCaseVData.add(ID);
                    tLLCaseVData.add(tCustomerNo);
                    tLLCaseVData.add(tCustomerName);
                    tLLCaseVData.add(tRgtNo);
                }

                //保全判断,限制保全未确认和退保的
                String strSql5 = "select count(*) from LPEdorItem where grpcontno = '" +tGrpContNo+ "' and "
                               + " insuredno = '"+tCustomerNo+"' and edorstate != '0' and edortype = 'CT'";
                String tCont5 = tExeSQL.getOneValue(strSql5);
                int ICont5 = 0;
                if( tCont5 != null && !tCont5.equals("")){
                    ICont5 = Integer.parseInt(tCont5);
                }
                //长险判断
                String strSql6 = "select count(*) from lmriskapp where "
                               + "riskcode in (select riskcode From lcpol where "
                               + "grpcontno = '" +tGrpContNo+ "' and insuredno = '" +tCustomerNo+ "') "
                               + "and RiskPeriod = 'L'";
                String tCont6 = tExeSQL.getOneValue(strSql6);
                int ICont6 = 0;
                if (tCont6 != null && !tCont6.equals("")) {
                    ICont6 = Integer.parseInt(tCont6);
                }

                if( ICont5 > 0 && ICont6 > 0 ){
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseData";
                    tError.errorMessage = "该出险人有未确认的保全或已经退保!";
                    this.mErrors.addOneError(tError);

                    tLLCaseVData.add(ID);
                    tLLCaseVData.add(tCustomerNo);
                    tLLCaseVData.add(tCustomerName);
                    tLLCaseVData.add(tRgtNo);
                }

                if (tSSRS4.getMaxRow() > 0 && ICont5 <= 0 && ICont6 <= 0) {
//                        tLLAccidentSchema.setAccNo(""); //事件号
//                        tLLRegisterSchema.setRgtNo(""); //报案号=赔案号
//                        tLLCaseSchema.setCaseNo(""); //分案号=报案号=赔案号
                        tLLAccidentSchema.setAccNo(tRgtNo); //事件号
                        tLLRegisterSchema.setRgtNo(tRgtNo); //报案号=赔案号
                        tLLCaseSchema.setCaseNo(tRgtNo); //分案号=报案号=赔案号

                        tLLCaseSchema.setRgtType("01"); //1-立案
                        tLLCaseSchema.setRgtState("20"); //案件状态
                        tLLCaseSchema.setCustomerNo(tCustomerNo); //出险人编码
                        tLLCaseSchema.setCustomerName(tCustomerName); //出险人名称
                        //tLLCaseSchema.setCustomerAge(parseNode(node, "customerAge")); //出险人年龄
                        tLLCaseSchema.setCustomerSex(tCustomerSex); //出险人性别
                        tLLCaseSchema.setAccDate(parseNode(node, "AccidentDate")); //出险日期
                        tLLCaseSchema.setIDType(tIDType); //出险人证件类型
                        tLLCaseSchema.setIDNo(tIDNo); //出险人证件号码
                        tLLCaseSchema.setSecondUWFlag(ID);//为借用字段 以便跟帐单ID匹配
                        tLLRegisterSchema.setAccidentDate(parseNode(node,
                                "AccidentDate")); //意外事故发生日期
                        tLLRegisterSchema.setAccidentReason(parseNode(node,
                                "OccurReason")); //出险原因
                        tLLRegisterSchema.setGrpContNo(tGrpContNo); //团体保单号
                        tLLRegisterSchema.setRiskCode(parseNode(node, "RiskCode")); //保单险种号
                        tLLRegisterSchema.setAppntNo(tGrpCustomerNo); //团体客户号
                        tLLRegisterSchema.setGrpName(tGrpName); //单位名称
                        tLLRegisterSchema.setPeoples2(tPeoples2); //投保总人数
                        tLLRegisterSchema.setRgtClass("2"); // 1个单 2团单
                        tLLRegisterSchema.setRgtState("03");//03批量案件标志

                        //用事故描述来存申请原因代码
                        String[] AppReasonCode = getCode(parseNode(node,"AppReasonCode"));
                        for (int i = 0; i < AppReasonCode.length; i++)
                        {
                            AppReasonCode[i] = parseNode(node, "OccurReason") + AppReasonCode[i];
                            tLLAppClaimReasonSchema = new LLAppClaimReasonSchema();
//                            tLLAppClaimReasonSchema.setCaseNo(""); //报案号=赔案号
//                            tLLAppClaimReasonSchema.setRgtNo(""); //报案号=赔案号
                            tLLAppClaimReasonSchema.setCaseNo(tRgtNo); //报案号=赔案号
                            tLLAppClaimReasonSchema.setRgtNo(tRgtNo); //报案号=赔案号

                            tLLAppClaimReasonSchema.setCustomerNo(tCustomerNo); //出险人编码
                            tLLAppClaimReasonSchema.setReasonCode(AppReasonCode[i]); //理赔类型
                            tLLAppClaimReasonSet.add(tLLAppClaimReasonSchema);
                        }

                        tLLCaseVData.add(ID);
                        tLLCaseVData.add(tCustomerNo);
                        tLLCaseVData.add(tCustomerName);
                        tLLCaseVData.add(tRgtNo);
                        tLLCaseVData.add(tLLCaseSchema);
                        tLLCaseVData.add(tLLRegisterSchema);
                        tLLCaseVData.add(tLLAccidentSchema);
                        tLLCaseVData.add(tLLAppClaimReasonSet);
                }
            }
        return tLLCaseVData;
    }

    /**
     * 从XML中解析立案信息
     * @param node
     * @return
     */
    public VData getLLCaseDataReport(Node node,String cGrpContNo,String cGrpCustomerNo,String cRiskCode,String cRptorName,String cRptorPhone,String cRptorAddress,String crgtstate,String cAccFlag,int I)
    {
        VData tLLCaseVData = new VData();
        LLSubReportSchema tLLSubReportSchema = new LLSubReportSchema();
        LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema(); //事件表
        LLReportSchema tLLReportSchema = new LLReportSchema(); //立案表
        LLReportReasonSchema tLLReportReasonSchema = new LLReportReasonSchema(); //理赔类型表
        LLReportReasonSet tLLReportReasonSet = new LLReportReasonSet(); //理赔类型集合
        ExeSQL tExeSQL = new ExeSQL();

        ///////////////////////////////////////////////////////////////////////////////////////
        String tGrpCustomerNo = cGrpCustomerNo; //团体客户号*
        String tGrpContNo     = cGrpContNo;     //团体保单号*
        String tRiskCode      = cRiskCode;     //保单险种号
        String tRptorName     = cRptorName;      //报案人姓名llreport中RptorName*
        String tRptorPhone    = cRptorPhone;      //报案人电话llreport中RptorPhone
        String tRptorAddress  = cRptorAddress;     //报案人通讯地址llreport中RptorAddress
        String trgtstate      = crgtstate;         //报案类型llreport中avalireason*
        String tAccFlag       = cAccFlag;          //是否使用团体金额llreport中rgtobj**
        ///////////////////////////////////////////////////////////////////////////////////////

        String ID             = parseNode(node, "CaseID");        //案件ID
        String tCustomerNo    = parseNode(node, "CustomerNo");    //出险人客户号
        String tCustomerName  = parseNode(node, "CustomerName");  //出险人名称
        String tCustomerSex   = parseNode(node, "CustomerSex");   //出险人性别
        String tIDType        = parseNode(node, "IDType");        //出险人证件类型
        String tIDNo          = parseNode(node, "IDNo");          //出险人证件号码
        String tAppReasonCode = parseNode(node, "AppReasonCode"); //出险类型*
        String tOccurReason   = parseNode(node, "OccurReason");   //出险原因*
        String tAccidentDate  = parseNode(node, "AccidentDate");  //出险日期*
        String tRgtNo         = parseNode(node, "RgtNo");          //赔案号
        String tGrpName       = "";
        String tPeoples2      = "";
        String tCustomerNo2   = "";                               //处理一个出险人有两个客户号的问题
        String tAppReasonCodeFlag="0";
        String tOccurReasonFlag="0";
        logger.debug("ID             === "+ID);
        logger.debug("tGrpCustomerNo === "+tGrpCustomerNo);
        logger.debug("tGrpContNo     === "+tGrpContNo);
        logger.debug("tCustomerNo    === "+tCustomerNo);
        logger.debug("tRgtNo         === "+tRgtNo);
        //-----jinsh--2007-06-14--页面必录项的校验--------------------------//

        if(tAppReasonCode==null||tAppReasonCode.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "GrpCustomerParser";
            tError.functionName = "getLLCaseDataReport";
            tError.errorMessage = "出险类型没有录入,请重新检查录入！";
            this.mErrors.addOneError(tError);
            tLLCaseVData=null;
            return tLLCaseVData;
        }

        if(tAppReasonCode.equals("00")||tAppReasonCode.equals("01")||tAppReasonCode.equals("02")||tAppReasonCode.equals("04")||tAppReasonCode.equals("06"))
        {
           tAppReasonCodeFlag="1"; //控制输入其他编码，用!=或!equals的方式居然查不出来！？暂加个标志来判断。
        }
        if(tAppReasonCodeFlag=="0"||tAppReasonCodeFlag.equals("0"))
        {
            CError tError = new CError();
            tError.moduleName = "GrpCustomerParser";
            tError.functionName = "getLLCaseDataReport";
            tError.errorMessage = "出险类型录入有误，不是标准的类型！";
            this.mErrors.addOneError(tError);
        }

        if(tOccurReason==null||tOccurReason.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "GrpCustomerParser";
            tError.functionName = "getLLCaseDataReport";
            tError.errorMessage = "出险原因没有录入,请重新检查录入！";
            this.mErrors.addOneError(tError);
            tLLCaseVData=null;
        }

        if(tOccurReason.toString().equals("1")||tOccurReason.toString().equals("2"))
        {
            tOccurReasonFlag="1";
        }
        if (tOccurReasonFlag=="0"||tOccurReasonFlag.equals("0"))
        {
            CError tError = new CError();
            tError.moduleName = "GrpCustomerParser";
            tError.functionName = "getLLCaseDataReport";
            tError.errorMessage = "出险原因录入有误，不是标准的原因！";
            this.mErrors.addOneError(tError);
        }

        if(tAccidentDate==null||tAccidentDate.equals(""))
        {
            CError tError = new CError();
            tError.moduleName = "GrpCustomerParser";
            tError.functionName = "getLLCaseDataReport";
            tError.errorMessage = "出险日期没有录入,请重新检查录入！";
            this.mErrors.addOneError(tError);
            tLLCaseVData=null;
        }
        //-----jinsh--2007-06-14--页面必录项的校验--------------------------//
            //通过团体客户号或团体保单号取到团体立案相关信息
            String strSql2 = "  select a.customerno,a.name,g.grpcontno,g.Peoples2 from lcgrpcont g, LCGrpAppnt a"
                           + " where a.grpcontno = g.grpcontno and g.appflag in ('1','4')";
            if (tGrpContNo != null && !tGrpContNo.equals("")) {
                strSql2 += " and g.GrpContNo = '" + tGrpContNo.trim() + "'";
            }
            if (tGrpCustomerNo != null && !tGrpCustomerNo.equals("")) {
                strSql2 += " and g.AppntNo = '" + tGrpCustomerNo.trim() + "'";
            }
            SSRS tSSRS2 = tExeSQL.execSQL(strSql2);
            if (tSSRS2.getMaxRow() > 0) {
                //for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
                tGrpCustomerNo = tSSRS2.GetText(1, 1); //保单险种号
                tGrpName = tSSRS2.GetText(1, 2); //单位名称
                tGrpContNo = tSSRS2.GetText(1, 3); //团体保单号
                tPeoples2 = tSSRS2.GetText(1, 4); //投保总人数
                //}
            } else {
                if (tGrpContNo != null && !tGrpContNo.equals("")) {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseDataReport";
                    tError.errorMessage =  "未查询到以保单：" + tGrpContNo + "相关的团体信息！";
                    this.mErrors.addOneError(tError);
                } else {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseDataReport";
                    tError.errorMessage = "未查询到团体客户号：" + tGrpCustomerNo + "相关的团体信息！";
                    this.mErrors.addOneError(tError);
                }
            }

            String strSql3 =
                    "select InsuredNo,Name,Sex,IDType,IDNo from lcinsured where" ;
                   strSql3=strSql3 + " GrpContNo='"+tGrpContNo.trim() +"'";

            if (tCustomerNo != null && !tCustomerNo.equals(""))
            {
                strSql3 += " and InsuredNo = '" + tCustomerNo.trim() + "'";
            }
            else
            {
                strSql3 += " and Name = '" + tCustomerName + "'";
                if (tIDNo!=null && !tIDNo.equals(""))
                {
                    strSql3 += " and IDNo = '" + tIDNo + "'";
                }
                if (tCustomerSex!=null && !tCustomerSex.equals(""))
                {
                    strSql3 += " and Sex = '" + tCustomerSex + "'";
                }
//                if (tBirthday!=null && !tBirthday.equals(""))
//                {
//                    strSql3 += " and birthday = '" + tBirthday + "'";
//                }

            }
            SSRS tSSRS3 = tExeSQL.execSQL(strSql3);
            if(tSSRS3.getMaxRow() <= 0 ){
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getLLCaseDataReport";
                tError.errorMessage = "未查询到该客户的信息！";
                this.mErrors.addOneError(tError);

                tLLCaseVData.add(ID);
                tLLCaseVData.add(tCustomerNo);
                tLLCaseVData.add(tCustomerName);
                tLLCaseVData.add(tRgtNo);
            }
            if(tSSRS3.getMaxRow() >1 ){
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseDataReport";
                    tError.errorMessage = "查询到该客户在该保单下有重名的信息！";
                    this.mErrors.addOneError(tError);

                }


            for(int j = 1 ; j <= tSSRS3.getMaxRow() ; j++) {
                tCustomerNo = tSSRS3.GetText(1, 1); //出险人编码
                tCustomerNo2 = tSSRS3.GetText(j, 1); //出险人编码 处理一个出险人有两个客户号的问题
                tCustomerName = tSSRS3.GetText(1, 2); //出险人名称
                tCustomerSex = tSSRS3.GetText(1, 3); //出险人性别
                tIDType = tSSRS3.GetText(1, 4); //出险人证件类型
                tIDNo = tSSRS3.GetText(1, 5); //出险人证件号码
            }


            if(tSSRS3.getMaxRow() > 0 && tCustomerNo!=null && !tCustomerNo.equals("")){//客户信息不全时不能查询
              //通过团体客户号或团体保单号判断是否在保险期间内
                String strSql4 =
                        " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='" +
                        tAccidentDate + "' and a.enddate>='" +
                        tAccidentDate +
                        "' and a.GrpContNo=b.GrpContNo and a.appflag in ('1','4')"
                        + "  and insuredno='" + tCustomerNo + "'";
                if (tGrpCustomerNo != null && !tGrpCustomerNo.equals("")) {
                    strSql4 = strSql4 + " and b.AppntNo='" + tGrpCustomerNo + "'";
                }
                if (tGrpContNo != null && !tGrpContNo.equals("")) {
                    strSql4 = strSql4 + " and b.GrpContNo='" + tGrpContNo + "'";
                }

                SSRS tSSRS4 = tExeSQL.execSQL(strSql4);
                if(tSSRS4.getMaxRow() <= 0){
                    // @@错误处理
                    CError tError = new CError();
                   // this.mErrors.clearErrors();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseDataReport";
                    tError.errorMessage = "该客户号的出险日期不在保单保险期间内";
                    this.mErrors.addOneError(tError);

                    tLLCaseVData.add(ID);
                    tLLCaseVData.add(tCustomerNo);
                    tLLCaseVData.add(tCustomerName);
                    tLLCaseVData.add(tRgtNo);
                }

                //保全判断,限制保全未确认和退保的
                String strSql5 = "select count(*) from LPEdorItem where"
                               + " grpcontno = '" +tGrpContNo+ "' and insuredno = '"+tCustomerNo+"' and edorstate != '0' and edortype = 'CT'";
                String tCont5 = tExeSQL.getOneValue(strSql5);
                int ICont5 = 0;
                if( tCont5 != null && !tCont5.equals("")){
                    ICont5 = Integer.parseInt(tCont5);
                }
                //长险判断
                String strSql6 = "select count(*) from lmriskapp where "
                               + "riskcode in (select riskcode From lcpol where "
                               + "grpcontno = '" +tGrpContNo+ "' and insuredno = '" +tCustomerNo+ "') "
                               + "and RiskPeriod = 'L'";
                String tCont6 = tExeSQL.getOneValue(strSql6);
                int ICont6 = 0;
                if (tCont6 != null && !tCont6.equals("")) {
                    ICont6 = Integer.parseInt(tCont6);
                }

                if( ICont5 > 0 && ICont6 > 0 ){
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getLLCaseDataReport";
                    tError.errorMessage = "该出险人有未确认的保全或已经退保!";
                    this.mErrors.addOneError(tError);

                    tLLCaseVData.add(ID);
                    tLLCaseVData.add(tCustomerNo);
                    tLLCaseVData.add(tCustomerName);
                    tLLCaseVData.add(tRgtNo);
                }

                if (tSSRS4.getMaxRow() > 0 && ICont5 <= 0 && ICont6 <= 0) {

                        tLLAccidentSchema.setAccNo(tRgtNo);//事件号
                        tLLAccidentSchema.setAccDate(parseNode(node,"AccidentDate")); //意外事故发生日期
                        tLLReportSchema.setRptNo(tRgtNo); //报案号=赔案号
                        tLLSubReportSchema.setSubRptNo(tRgtNo); //分案号=报案号=赔案号
                        tLLSubReportSchema.setCustomerNo(tCustomerNo); //出险人编码
                        tLLSubReportSchema.setCustomerName(tCustomerName); //出险人名称
                        tLLSubReportSchema.setAccDate(parseNode(node, "AccidentDate")); //出险日期
                        if (tRgtNo==null || tRgtNo.trim().equals(""))
                        {
                            tLLSubReportSchema.setCondoleFlag(ID); //借用字段，用来保存导入操作的ID号
                        }else
                        {
                           String RSQL="select max(to_number(CondoleFlag)) from llsubreport where subrptno='"+tRgtNo.trim()+"' ";
                           String tCondoleFlag = tExeSQL.getOneValue(RSQL);
                           if (tCondoleFlag!=null&&!tCondoleFlag.equals(""))
                           {
                               int tCondoleFlag2 =Integer.parseInt(tCondoleFlag)+I;
                               tLLSubReportSchema.setCondoleFlag(Integer.toString(tCondoleFlag2)); //借用字段，用来保存导入操作的ID号
                           }else
                           {
                               CError tError = new CError();
                               tError.moduleName = "GrpCustomerParser";
                               tError.functionName = "getLLCaseDataReport";
                               tError.errorMessage = "输入的赔案号不存在或已被删除！";
                               this.mErrors.addOneError(tError);
                           }
                        }

                        tLLReportSchema.setAccidentDate(parseNode(node,"AccidentDate")); //意外事故发生日期
                        tLLReportSchema.setAccidentReason(parseNode(node,"OccurReason")); //出险原因
                        tLLReportSchema.setGrpContNo(tGrpContNo); //团体保单号
                        tLLReportSchema.setRiskCode(tRiskCode); //保单险种号
                        tLLReportSchema.setAppntNo(tGrpCustomerNo); //团体客户号
                        tLLReportSchema.setGrpName(tGrpName); //单位名称
                        tLLReportSchema.setPeoples2(tPeoples2); //投保总人数
                        tLLReportSchema.setRgtClass("2"); // 1个单 2团单
                        //-----------------------jinsh--2007-06-14-add-------------------//
                        tLLReportSchema.setRptorName(tRptorName);
                        tLLReportSchema.setRgtObj(tAccFlag);
                        tLLReportSchema.setRptorAddress(tRptorAddress);
                        tLLReportSchema.setRptorPhone(tRptorPhone);
                        tLLReportSchema.setAvaliReason(trgtstate); //01简易案件 02帐户案件 03非理算理赔

                        //用事故描述来存申请原因代码
                        String[] AppReasonCode = getCode(parseNode(node,"AppReasonCode"));
                        for (int i = 0; i < AppReasonCode.length; i++)
                        {
                            AppReasonCode[i] = parseNode(node, "OccurReason") + AppReasonCode[i];
                            tLLReportReasonSchema = new LLReportReasonSchema();
                            tLLReportReasonSchema.setRpNo(tRgtNo); //报案号=赔案号
                            tLLReportReasonSchema.setCustomerNo(tCustomerNo); //出险人编码
                            tLLReportReasonSchema.setReasonCode(AppReasonCode[i]); //理赔类型
                            tLLReportReasonSet.add(tLLReportReasonSchema);
                        }

                        tLLCaseVData.add(ID);
                        tLLCaseVData.add(tCustomerNo);
                        tLLCaseVData.add(tCustomerName);
                        tLLCaseVData.add(tRgtNo);
                        tLLCaseVData.add(tLLSubReportSchema);
                        tLLCaseVData.add(tLLReportSchema);
                        tLLCaseVData.add(tLLAccidentSchema);
                        tLLCaseVData.add(tLLReportReasonSet);
                }
            }
        return tLLCaseVData;
    }

//取得账单和账单明细
    public VData getFeeVData(Node node,String mRgtNo)
    {
                LLFeeMainSet tLLFeeMainSet = new LLFeeMainSet();
                LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();

                LLFeeMainSchema tLLFeeMainSchema;
                Node nodeLLFeeMain = node;
                //帐单ID匹配客户号
                String tCustomerNo = parseNode(nodeLLFeeMain, "tCustomerNo");
                //通过案件ID号到llcase表中查询客户号
//                String strSql = "Select CustomerNo from llcase where caseno = '" +
//                                mRgtNo + "'";
                                //+" and SecondUWFlag = '" + tFeeID + "'";
                ExeSQL exesql = new ExeSQL();
//                String tCustomerNo = exesql.getOneValue(strSql);

                    if(tCustomerNo!=null && !tCustomerNo.equals("")){
                        tLLFeeMainSchema = new LLFeeMainSchema();
                        tLLFeeMainSchema.setClmNo(mRgtNo); //赔案号
                        tLLFeeMainSchema.setCaseNo(mRgtNo); //分案号
                        tLLFeeMainSchema.setMainFeeNo(parseNode(nodeLLFeeMain,
                                "MainFeeNo")); //帐单号码
                        tLLFeeMainSchema.setCustomerNo(tCustomerNo); //客户号
                        tLLFeeMainSchema.setHospitalCode(parseNode(
                                nodeLLFeeMain, "HospitalCode")); //医院代码
                        tLLFeeMainSchema.setFeeType(parseNode(nodeLLFeeMain,
                                "FeeType")); //A 门诊 B住院
                        tLLFeeMainSet.clear();
                        tLLFeeMainSet.add(tLLFeeMainSchema);

                        LLCaseReceiptSchema tLLCaseReceiptSchema = new
                                LLCaseReceiptSchema();
                        tLLCaseReceiptSchema.setClmNo(mRgtNo);
                        tLLCaseReceiptSchema.setCaseNo(mRgtNo);
                        tLLCaseReceiptSchema.setMainFeeNo(parseNode(
                                nodeLLFeeMain, "MainFeeNo"));
                        tLLCaseReceiptSchema.setRgtNo(mRgtNo);
                        tLLCaseReceiptSchema.setFeeItemType(parseNode(
                                nodeLLFeeMain, "FeeType")); //A 门诊 B住院
//                        tLLCaseReceiptSchema.setFeeItemName(parseNode(
//                                nodeLLFeeMain, "FeeType")); //A 门诊 B住院
                        tLLCaseReceiptSchema.setDiseaseCode(parseNode(
                                nodeLLFeeMain, "DiseaseCode")); //疾病代码
                        tLLCaseReceiptSchema.setFeeItemCode(parseNode(
                                nodeLLFeeMain, "FeeItemCode")); //费用类型

                        String tFeeItemCode = parseNode(nodeLLFeeMain,"FeeItemCode");
//                        String FeeSql="select codename from ldcode where codetype='llfeeitemtype' and code='"+tFeeItemCode+"' ";
                        String FeeSql="select codename from ldcode where 1 = 1 and codetype = 'llhospitalfeetype'"
                        			+" or codetype='llmedfeetype' and code='"+tFeeItemCode+"' ";
                        String tFeeItemName=exesql.getOneValue(FeeSql);
                        tLLCaseReceiptSchema.setFeeItemName(tFeeItemName); //费用名称，可以从code表里取

                        tLLCaseReceiptSchema.setFee(parseNode(nodeLLFeeMain,
                                "Fee")); //原始费用
                        tLLCaseReceiptSchema.setSelfAmnt(parseNode(
                                nodeLLFeeMain, "RefuseAmnt")); //扣除费用
                        tLLCaseReceiptSchema.setAdjReason(parseNode(
                                nodeLLFeeMain, "AdjReason")); //扣除代码
                        tLLCaseReceiptSchema.setAdjRemark(parseNode(
                                nodeLLFeeMain, "AdjRemark")); //扣除备注
                        tLLCaseReceiptSchema.setSecurityAmnt(parseNode(nodeLLFeeMain,
                                "SecurityAmnt")); //社保赔付费用
                        tLLCaseReceiptSchema.setStartDate(parseNode(
                                nodeLLFeeMain, "StartDate")); //开始日期
                        tLLCaseReceiptSchema.setEndDate(parseNode(nodeLLFeeMain,
                                "EndDate")); //结束日期
                        tLLCaseReceiptSchema.setDealFlag("1"); //开始日期是否早于出险日期,0是1不是
                        tLLCaseReceiptSchema.setCustomerNo(tCustomerNo); //客户号
                        tLLCaseReceiptSchema.setHospLineAmnt(parseNode(
                                nodeLLFeeMain, "HospLineAmnt")); //住院起付线
                        tLLCaseReceiptSet.clear();//每次提交时候清空set集合
                        tLLCaseReceiptSet.add(tLLCaseReceiptSchema);
                    }
                VData tLLFeeVData = new VData();
                tLLFeeVData.add(tLLFeeMainSet);
                tLLFeeVData.add(tLLCaseReceiptSet);
                return tLLFeeVData;

    }

    //取得账单和账单明细 2006-06-28 换个方式传值 原来的太慢了~~哇咔咔`~~
        public VData getFeeVDataNew(Node node,String mRgtNo)
        {
                    LLFeeMainSchema tLLFeeMainSchema = new LLFeeMainSchema();
                    LLCaseReceiptSchema tLLCaseReceiptSchema = new LLCaseReceiptSchema();
                    Node nodeLLFeeMain = node;
                    //帐单ID匹配客户号
                    String tFeeID = parseNode(nodeLLFeeMain, "FeeID");
                    //通过案件ID号到llcase表中查询客户号
                    String strSql = "Select CustomerNo from llcase where caseno = '" +
                                    mRgtNo + "' and SecondUWFlag = '" + tFeeID + "'";
                    ExeSQL exesql = new ExeSQL();
                    String tCustomerNo = exesql.getOneValue(strSql);

                        if(tCustomerNo!=null && !tCustomerNo.equals("")){
                            tLLFeeMainSchema.setClmNo(mRgtNo); //赔案号
                            tLLFeeMainSchema.setCaseNo(mRgtNo); //分案号
                            tLLFeeMainSchema.setMainFeeNo(parseNode(nodeLLFeeMain,
                                    "MainFeeNo")); //帐单号码
                            tLLFeeMainSchema.setCustomerNo(tCustomerNo); //客户号
                            tLLFeeMainSchema.setHospitalCode(parseNode(
                                    nodeLLFeeMain, "HospitalCode")); //医院代码
                            tLLFeeMainSchema.setFeeType(parseNode(nodeLLFeeMain,
                                    "FeeType")); //A 门诊 B住院

                            tLLCaseReceiptSchema.setClmNo(mRgtNo);
                            tLLCaseReceiptSchema.setCaseNo(mRgtNo);
                            tLLCaseReceiptSchema.setMainFeeNo(parseNode(
                                    nodeLLFeeMain, "MainFeeNo"));
                            tLLCaseReceiptSchema.setRgtNo(mRgtNo);
                            tLLCaseReceiptSchema.setFeeItemType(parseNode(
                                    nodeLLFeeMain, "FeeType")); //A 门诊 B住院
//                            tLLCaseReceiptSchema.setFeeItemName(parseNode(
//                                    nodeLLFeeMain, "FeeType")); //A 门诊 B住院
                            tLLCaseReceiptSchema.setDiseaseCode(parseNode(
                                    nodeLLFeeMain, "DiseaseCode")); //疾病代码
                            tLLCaseReceiptSchema.setFeeItemCode(parseNode(
                                    nodeLLFeeMain, "FeeItemCode")); //费用类型

                            String tFeeItemCode = parseNode(nodeLLFeeMain,"FeeItemCode");
                            String FeeSql="select codename from ldcode where codetype='llfeeitemtype' and code='"+tFeeItemCode+"' ";
                            String tFeeItemName=exesql.getOneValue(FeeSql);
                            tLLCaseReceiptSchema.setFeeItemName(tFeeItemName); //费用名称，可以从code表里取

                            tLLCaseReceiptSchema.setFee(parseNode(nodeLLFeeMain,
                                    "Fee")); //原始费用
                            tLLCaseReceiptSchema.setRefuseAmnt(parseNode(
                                    nodeLLFeeMain, "RefuseAmnt")); //扣除费用
                            tLLCaseReceiptSchema.setAdjReason(parseNode(
                                    nodeLLFeeMain, "AdjReason")); //扣除代码
                            tLLCaseReceiptSchema.setAdjRemark(parseNode(
                                    nodeLLFeeMain, "AdjRemark")); //扣除备注
                            tLLCaseReceiptSchema.setSecurityAmnt(parseNode(nodeLLFeeMain,
                                    "SecurityAmnt")); //社保赔付费用
                            tLLCaseReceiptSchema.setStartDate(parseNode(
                                    nodeLLFeeMain, "StartDate")); //开始日期
                            tLLCaseReceiptSchema.setEndDate(parseNode(nodeLLFeeMain,
                                    "EndDate")); //结束日期
                            tLLCaseReceiptSchema.setDealFlag("1"); //开始日期是否早于出险日期,0是1不是
                            tLLCaseReceiptSchema.setCustomerNo(tCustomerNo); //客户号
                            tLLCaseReceiptSchema.setHospLineAmnt(parseNode(
                                    nodeLLFeeMain, "HospLineAmnt")); //住院起付线
                        }
                    VData tLLFeeVData = new VData();
                    tLLFeeVData.add(tLLFeeMainSchema);
                    tLLFeeVData.add(tLLCaseReceiptSchema);
                    return tLLFeeVData;

    }

        /**
         * 由XML中提取特种费用、社保第三方给付费用 返回VData
         * */
        public VData getOtherFactor(Node node,String mRgtNo,String tFactorType){
        	Node nodeOtherFactor = node;
        	LLOtherFactorSchema tLLOtherFactorSchema = new LLOtherFactorSchema();
            //帐单ID匹配客户号
            String tCustomerNo = parseNode(nodeOtherFactor, "tCustomerNo");

            //准备后台数据
            tLLOtherFactorSchema.setClmNo(mRgtNo);
            tLLOtherFactorSchema.setCaseNo(mRgtNo);
//            tLLOtherFactorSchema.setSerialNo();
            tLLOtherFactorSchema.setCustomerNo(tCustomerNo);
            
            tLLOtherFactorSchema.setFactorType(parseNode(nodeOtherFactor,"FactorType"));
            String tFactorCode=parseNode(nodeOtherFactor,"FactorCode");
            tLLOtherFactorSchema.setFactorCode(tFactorCode);
            String tFactorNameSql = "select codename from ldcode where codetype='llfactypemed'"
            			+" or codetype = 'llfactypesuccor' and code='"+tFactorCode+"'";
            String tFactorName = "";
            ExeSQL tExeSql = new ExeSQL();
            tFactorName = tExeSql.getOneValue(tFactorNameSql);
            tLLOtherFactorSchema.setFactorName(tFactorName);
            tLLOtherFactorSchema.setFactorValue(parseNode(nodeOtherFactor,"FactorValue"));//要素值
            tLLOtherFactorSchema.setAdjSum(parseNode(nodeOtherFactor,"FactorValue"));//调整金额设为初始金额
            tLLOtherFactorSchema.setUnitNo("0");     //机构代码置0
            tLLOtherFactorSchema.setUnitName(parseNode(nodeOtherFactor,"UnitName"));  //机构名称
            tLLOtherFactorSchema.setStartDate(parseNode(nodeOtherFactor,"tStartDate")); 
            tLLOtherFactorSchema.setEndDate(parseNode(nodeOtherFactor,"tEndDate"));
            tLLOtherFactorSchema.setSelfAmnt(parseNode(nodeOtherFactor, "RefuseAmnt")); //扣除费用
            tLLOtherFactorSchema.setAdjReason(parseNode(nodeOtherFactor, "AdjReason")); //扣除代码
            tLLOtherFactorSchema.setAdjRemark(parseNode(nodeOtherFactor,"AdjRemark3")); 
            tLLOtherFactorSchema.setFeeItemType(tFactorType);//费用项目类型
            VData tLLOtherFactorVData = new VData();
            tLLOtherFactorVData.add(tLLOtherFactorSchema);
            return tLLOtherFactorVData;
        }
        
        /**
         * 由XML中提比例给付 返回VData
         * */
        public VData getLLCaseInfo(Node node,String mRgtNo,String tFactorType){
            LLCaseInfoSchema tLLCaseInfoSchema = new LLCaseInfoSchema();

            Node nodeLLCaseInfo = node;
            //准备后台数据
            tLLCaseInfoSchema.setClmNo(mRgtNo);
            tLLCaseInfoSchema.setCaseNo(mRgtNo);
//            tLLCaseInfoSchema.setSerialNo();
          //帐单ID匹配客户号
            String tCustomerNo = parseNode(nodeLLCaseInfo, "tCustomerNo");
            tLLCaseInfoSchema.setCustomerNo(tCustomerNo);
            String tDefoType = parseNode(nodeLLCaseInfo, "DefoType");//伤残类型
            String tDefoGrade = parseNode(nodeLLCaseInfo, "DefoGrade");//伤残级别代码
           
            String tDefoNameSql = "select defogradename from LLParaDeformity where 1=1  and DefoType = '"
            				+tDefoType+"' and defograde = '"+tDefoGrade+"' ";
            ExeSQL tExeSQL = new ExeSQL();
            String tDefoName = tExeSQL.getOneValue(tDefoNameSql);//伤残级别名称
            String tDefoCode = parseNode(nodeLLCaseInfo, "DefoCode");//伤残代码
            String tDefoCodeNameSql = "select defoname from LLParaDeformity where 1=1  and DefoType = '"
            				+tDefoType+"' and defograde = '"+tDefoGrade+"' "
            				+"and defocode='"+tDefoCode+"'  order by defocode ";
            String tDefoCodeName = tExeSQL.getOneValue(tDefoCodeNameSql);//伤残代码名称
            tLLCaseInfoSchema.setDefoType(tDefoType);//伤残类型
            tLLCaseInfoSchema.setDefoGrade(tDefoGrade);//伤残级别代码
            tLLCaseInfoSchema.setDefoCode(tDefoCode);//伤残代码
            tLLCaseInfoSchema.setDefoName(tDefoName);//伤残级别名称
            
            
            tLLCaseInfoSchema.setDefoCodeName(tDefoCodeName);//伤残代码名称
            
//            tLLCaseInfoSchema.setDeformityReason();
            tLLCaseInfoSchema.setDeformityRate(parseNode(nodeLLCaseInfo, "DeformityRate"));
            String tAppDeformityRateSql = "select t.deforate from LLParaDeformity t where  t.defocode='"+tDefoCode+"'";
            String tAppDeformityRate = tExeSQL.getOneValue(tAppDeformityRateSql);//申请给付比例
            tLLCaseInfoSchema.setAppDeformityRate(tAppDeformityRate);//申请给付比例
            tLLCaseInfoSchema.setDeformityRate(tAppDeformityRate);//申请给付比例
            tLLCaseInfoSchema.setRealRate(tAppDeformityRate);//实际给付比例
            tLLCaseInfoSchema.setJudgeOrganName(parseNode(nodeLLCaseInfo, "JudgeOrganName"));
            tLLCaseInfoSchema.setJudgeDate(parseNode(nodeLLCaseInfo, "JudgeDate"));
            tLLCaseInfoSchema.setAdjRemark(parseNode(nodeLLCaseInfo, "AdjRemark2"));
            
        	VData tLLCaseInfoVData = new VData();
        	tLLCaseInfoVData.add(tLLCaseInfoSchema);
        	return tLLCaseInfoVData;
        }
        
    //取得社保账单明细 2006-08-30
    public VData getReceiptClass(Node node, String mRgtNo) {
        LLCaseReceiptClassSchema tLLCaseReceiptClassSchema = new LLCaseReceiptClassSchema();
        Node nodeLLFeeMain = node;
        String tCustomerNo ="";
        String tCustomerName ="";
        //帐单ID匹配客户号
        String tFeeID = parseNode(nodeLLFeeMain, "FeeID");
        //通过案件ID号到llcase表中查询客户号
        String strSql = "Select CustomerNo,CustomerName from llcase where caseno = '" +
                        mRgtNo + "' and SecondUWFlag = '" + tFeeID + "'";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(strSql);
        if (tSSRS.getMaxRow()>0)
        {
             tCustomerNo = tSSRS.GetText(1, 1);
             tCustomerName = tSSRS.GetText(1, 2);
        }else
        {
                // @@错误处理
                CError tError = new CError();
                //this.mErrors.clearErrors();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getReceiptClass";
                tError.errorMessage = "该出险人"+tCustomerNo+"对应的ID号:"+tFeeID+"在立案表中无信息！";
                this.mErrors.addOneError(tError);
        }

        String StrSql2 = "select grpcontno from llregister where rgtno = '"+mRgtNo+"'";
        String tGrpContNo = tExeSQL.getOneValue(StrSql2);

        //判断该出险人是否有重复的收据号
        String StrSql3 = "select clmno From LLCaseReceiptClass where insuredno = '"+tCustomerNo+"' and billno = '"+parseNode(nodeLLFeeMain,"MainFeeNo")+"'";
        String tClmno = tExeSQL.getOneValue(StrSql3);
        if(tClmno!=null && !tClmno.equals("")){
                // @@错误处理
                CError tError = new CError();
                //this.mErrors.clearErrors();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getReceiptClass";
                tError.errorMessage = "该出险人在"+tClmno+"这个赔案中有相同的收据号:"+parseNode(nodeLLFeeMain,"MainFeeNo")+"!";
                this.mErrors.addOneError(tError);
        }
        if (tCustomerNo != null && !tCustomerNo.equals("")) {
            //固定字段
            tLLCaseReceiptClassSchema.setClmNo(mRgtNo);//赔案号
            tLLCaseReceiptClassSchema.setGrpContNo(tGrpContNo);//团体保单号
            tLLCaseReceiptClassSchema.setInsuredNo(tCustomerNo);//客户号
            tLLCaseReceiptClassSchema.setName(tCustomerName);//姓名
            tLLCaseReceiptClassSchema.setBillNo(parseNode(nodeLLFeeMain,"MainFeeNo"));//收据号
            tLLCaseReceiptClassSchema.setReceiptDate(parseNode(nodeLLFeeMain,"ReceiptDate"));//收据日期
            tLLCaseReceiptClassSchema.setFeeItemType(parseNode(nodeLLFeeMain,"FeeType"));//收据类型(门诊；住院)
            tLLCaseReceiptClassSchema.setTotalFee(parseNode(nodeLLFeeMain,"Fee"));//收据总费用
            tLLCaseReceiptClassSchema.setStartDate(parseNode(nodeLLFeeMain,"StartDate"));//开始日期
            tLLCaseReceiptClassSchema.setEndDate(parseNode(nodeLLFeeMain,"EndDate"));//结束日期
            tLLCaseReceiptClassSchema.setReason(parseNode(nodeLLFeeMain,"AdjRemark"));//收据总体的扣除原因
            tLLCaseReceiptClassSchema.setHospitalCode(parseNode(nodeLLFeeMain,"HospitalCode"));//医院代码
            tLLCaseReceiptClassSchema.setUWFlag("");//是否审核
            tLLCaseReceiptClassSchema.setUWDate("");//审核日期
            tLLCaseReceiptClassSchema.setUWOperator("");//审核操作员
            tLLCaseReceiptClassSchema.setTotalAdjFee("");//收据总体合理费用
            tLLCaseReceiptClassSchema.setFeeItemCode(parseNode(nodeLLFeeMain,"FeeItemCode"));//费用类别
            tLLCaseReceiptClassSchema.setFeeItemName("");//费用类别名称
            tLLCaseReceiptClassSchema.setLastOperator("");

            //不定数字段
            tLLCaseReceiptClassSchema.setCol1(parseNode(nodeLLFeeMain,"CoL1"));
            tLLCaseReceiptClassSchema.setCol2(parseNode(nodeLLFeeMain,"CoL2"));
            tLLCaseReceiptClassSchema.setCol3(parseNode(nodeLLFeeMain,"CoL3"));
            tLLCaseReceiptClassSchema.setCol4(parseNode(nodeLLFeeMain,"CoL4"));
            tLLCaseReceiptClassSchema.setCol5(parseNode(nodeLLFeeMain,"CoL5"));
            tLLCaseReceiptClassSchema.setCol6(parseNode(nodeLLFeeMain,"CoL6"));
            tLLCaseReceiptClassSchema.setCol7(parseNode(nodeLLFeeMain,"CoL7"));
            tLLCaseReceiptClassSchema.setCol8(parseNode(nodeLLFeeMain,"CoL8"));
            tLLCaseReceiptClassSchema.setCol9(parseNode(nodeLLFeeMain,"CoL9"));
            tLLCaseReceiptClassSchema.setCol10(parseNode(nodeLLFeeMain,"CoL10"));

            //判断收据日期是否在保单保险期间内;
            String StrSql4 = "select count(*) From lcpol where grpcontno = '"+tGrpContNo+"'"
                           + " and insuredno = '"+tCustomerNo+"'"
                           + " and cvalidate <= '"+parseNode(nodeLLFeeMain,"ReceiptDate")+"'"
                           + " and enddate >= '"+parseNode(nodeLLFeeMain,"ReceiptDate")+"'";
            String tCont = tExeSQL.getOneValue(StrSql4);
            if( !tCont.equals("0") ){
                tLLCaseReceiptClassSchema.setValidFlag("1");//状态(0--无效，1--有效)
            }else{
                tLLCaseReceiptClassSchema.setValidFlag("0");//状态(0--无效，1--有效)
            }

        }
        VData tLLFeeVData = new VData();
        tLLFeeVData.add(tLLCaseReceiptClassSchema);
        return tLLFeeVData;

    }

   /**===============================================================================================
    * 从XML中解析立案信息,
    * @param node
    * @return vdata
    * desc: 用于账户案件理赔
    * auther: 万泽辉
    * date:2006-01-13
    ==================================================================================================
    */
   public VData getLLCaseDataForAcc(Node node)
   {
       VData tLLCaseVData = new VData();
       LLCaseSchema tLLCaseSchema                     = new LLCaseSchema();           //赔案表
       LLAccidentSchema tLLAccidentSchema             = new LLAccidentSchema();       //事件表
       LLRegisterSchema tLLRegisterSchema             = new LLRegisterSchema();       //立案表
       LLAppClaimReasonSchema tLLAppClaimReasonSchema = new LLAppClaimReasonSchema(); //理赔类型表
       LLAppClaimReasonSet tLLAppClaimReasonSet       = new LLAppClaimReasonSet();    //理赔类型集合
       LLClaimAccountSchema tLLClaimAccountSchema     = new LLClaimAccountSchema();   //保险账户分类表
       LLClaimAccountSchema tLLClaimAccountSchema1    = new LLClaimAccountSchema();   //保险账户分类表
       LLClaimAccountSet tLLClaimAccountSet           = new LLClaimAccountSet();      //账户相关
       ExeSQL tExeSQL = new ExeSQL();

       String ID             = parseNode(node, "CaseID");        //
       String tGrpCustomerNo = parseNode(node, "GrpCustomerNo"); //团体客户号
       String tGrpContNo     = parseNode(node, "GrpContNo");     //团体保单号
       String tRiskCode      = parseNode(node, "RiskCode");      //保单险种号
       String tCustomerNo    = parseNode(node, "CustomerNo");    //出险人编码
       String tCustomerName  = parseNode(node, "CustomerName");  //出险人名称
       String tCustomerSex   = parseNode(node, "CustomerSex");   //出险人性别
       String tIDType        = parseNode(node, "IDType");        //出险人证件类型
       String tIDNo          = parseNode(node, "IDNo");          //出险人证件号码
       String tAccidentDate  = parseNode(node, "AccidentDate");  //出险日期
       String tAccFlag       = parseNode(node, "GrpFlag");       //是否使用团体帐户金额
       String tPesAcc        = parseNode(node, "OwnCancel");     //个报销额
       String tAccAmt        = parseNode(node, "CompanyCancel"); //团体报销
       String tAppReasonCode = parseNode(node, "AppReasonCode");
       String tOccurReason   = parseNode(node, "OccurReason");
       String tRgtNo   = parseNode(node, "RgtNo");

       String tCustomerNo2   = "";                               //处理一个出险人有两个客户号的问题
       String tGrpName       = "";
       String tPeoples2      = "";
       logger.debug("ID=================="+ID);
       logger.debug("tGrpCustomerNo======="+tGrpCustomerNo);
       logger.debug("tGrpContNo============="+tGrpContNo);
       logger.debug("tCustomerNo============="+tCustomerNo);
       logger.debug("tRgtNo============="+tRgtNo);

           //通过团体客户号或团体保单号取到团体立案相关信息
           String strSql2 = "  select a.customerno,a.name,g.grpcontno,g.Peoples2 from lcgrpcont g, LCGrpAppnt a"
                          + " where a.grpcontno = g.grpcontno and g.appflag in ('1','4')";
           if (tGrpContNo != null && !tGrpContNo.equals(""))
           {
               strSql2 += " and g.GrpContNo = '" + tGrpContNo + "'";
           }
           if (tGrpCustomerNo != null && !tGrpCustomerNo.equals(""))
           {
               strSql2 += " and g.AppntNo = '" + tGrpCustomerNo + "'";
           }
           SSRS tSSRS2 = tExeSQL.execSQL(strSql2);
           if (tSSRS2.getMaxRow() > 0)
           {
               tGrpCustomerNo = tSSRS2.GetText(1, 1);        //保单险种号
               tGrpName       = tSSRS2.GetText(1, 2);        //单位名称
               tGrpContNo     = tSSRS2.GetText(1, 3);        //团体保单号
               tPeoples2      = tSSRS2.GetText(1, 4);        //投保总人数

           }
           else
           {
               if (tGrpContNo != null && !tGrpContNo.equals(""))
               {
                   // @@错误处理
                   CError tError       = new CError();
                   tError.moduleName   = "GrpCustomerParser";
                   tError.functionName = "getLLCaseData";
                   tError.errorMessage =  "未查询到以保单：" + tGrpContNo + "相关的团体信息！";
                   this.mErrors.addOneError(tError);
               }
               else
               {
                   // @@错误处理
                   CError tError       = new CError();
                   tError.moduleName   = "GrpCustomerParser";
                   tError.functionName = "getLLCaseData";
                   tError.errorMessage = "未查询到团体客户号：" + tGrpCustomerNo + "相关的团体信息！";
                   this.mErrors.addOneError(tError);
               }
           }
           //
           String tGrpPolNo = "";
           String tSql = "select grppolno from  lcgrppol where grpcontno = '"+tGrpContNo+"' "
                       + " and customerno = '"+tGrpCustomerNo+"' and riskcode = '"+tRiskCode+"'";
           SSRS tSSRS5 = tExeSQL.execSQL(tSql);
           if(tSSRS5.getMaxRow()>0)
           {
               tGrpPolNo = tSSRS5.GetText(1,1);
           }

           //通过客户号或客户姓名证件类型以及证件号码得到客户信息
           String strSql3 =
                   "select CustomerNo,Name,Sex,IDType,IDNo from ldperson where";
           if (tCustomerNo != null && !tCustomerNo.equals(""))
           {
               strSql3 += " CustomerNo = '" + tCustomerNo + "'";
           }
           else if (tIDType.equals("0"))
           { //身份证
               strSql3 += " Name = '" + tCustomerName + "' and IDNo = '" +
                       tIDNo + "' and IDType = '" + tIDType + "'";
           }
           else if(tIDType==null && tIDType.equals("")){
//                strSql3 += " Name = '" + tCustomerName + "' and Birthday = '" +
//                        tBirthday + "' and Sex = '" + tCustomerSex + "'";
                 strSql3 += " Name = '" + tCustomerName + "' and Sex = '" + tCustomerSex + "'";
            }
           else
           {
               strSql3 += " Name = '" + tCustomerName + "' and IDNo = '" +
                       tIDNo + "' and IDType = '" + tIDType + "' and Sex = '" +
                       tCustomerSex + "'";
           }
           SSRS tSSRS3 = tExeSQL.execSQL(strSql3);
           if (tSSRS3.getMaxRow() <= 0)
           {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "GrpCustomerParser";
               tError.functionName = "getLLCaseData";
               tError.errorMessage = "未查询到该客户的信息！";
               this.mErrors.addOneError(tError);

               tLLCaseVData.add(ID);
               tLLCaseVData.add(tCustomerNo);
               tLLCaseVData.add(tCustomerName);
               tLLCaseVData.add(tRgtNo);
           }
           for(int j = 1; j<=tSSRS3.getMaxRow();j++){
               tCustomerNo = tSSRS3.GetText(1, 1); //出险人编码
               tCustomerNo2 = tSSRS3.GetText(j, 1); //出险人编码
               tCustomerName = tSSRS3.GetText(1, 2); //出险人名称
               tCustomerSex = tSSRS3.GetText(1, 3); //出险人性别
               tIDType = tSSRS3.GetText(1, 4); //出险人证件类型
               tIDNo = tSSRS3.GetText(1, 5); //出险人证件号码
           }

           if(tSSRS3.getMaxRow() > 0 && tCustomerNo!=null && !tCustomerNo.equals(""))
           {//客户信息不全时不能查询
             //通过团体客户号或团体保单号判断是否在保险期间内
               String strSql4 =
                       " select a.polno ,a.contno from LCPol a, LCGrpCont b where  a.CValidate<='" +
                       tAccidentDate + "' and a.GrpContNo=b.GrpContNo and a.appflag in ('1','4')"
                       + "  and insuredno='" + tCustomerNo + "'";
               if (tGrpCustomerNo != null && !tGrpCustomerNo.equals(""))
               {
                   strSql4 = strSql4 + " and b.AppntNo='" + tGrpCustomerNo + "'";
               }
               if (tGrpContNo != null && !tGrpContNo.equals(""))
               {
                   strSql4 = strSql4 + " and b.GrpContNo='" + tGrpContNo + "'";
               }

               SSRS tSSRS4    = tExeSQL.execSQL(strSql4);

               if(tSSRS4.getMaxRow() <= 0)
               {
                   // @@错误处理
                   CError tError = new CError();
                   tError.moduleName = "GrpCustomerParser";
                   tError.functionName = "getLLCaseData";
                   tError.errorMessage = "该客户号的出险日期不在保单保险期间内";
                   this.mErrors.addOneError(tError);

                   tLLCaseVData.add(ID);
                   tLLCaseVData.add(tCustomerNo);
                   tLLCaseVData.add(tCustomerName);
                   tLLCaseVData.add(tRgtNo);
               }
               if (tSSRS4.getMaxRow() > 0)
               {

                   String tPolNo  = tSSRS4.GetText(1,1);//个人险种号
                   String tContNo = tSSRS4.GetText(1,2);//个人合同号
                   //判断导入的客户是否是该团体客户号下的客户
                   String strSql =" select b.insuredno from LcCont a, LCInsured b,LCGrpCont c"
                           + " where a.contno = b.contno and a.grpcontno = c.grpcontno and  c.AppntNo = '"
                           +tGrpCustomerNo + "'"
                           + " and  b.insuredno in ('" + tCustomerNo + "','"+tCustomerNo2+"') union"
                           + " select b.AppntNo from LcCont a, lcappnt b ,LCGrpCont c where a.contno = b.contno and a.grpcontno = c.grpcontno"
                           + " and c.AppntNo = '" + tGrpCustomerNo +
                           "' and b.AppntNo in ('" + tCustomerNo + "','"+tCustomerNo2+"')";
                   logger.debug("-团体客户号下的客户：-"+strSql);
                   ExeSQL exesql = new ExeSQL();
                   String tResult = exesql.getOneValue(strSql);

                   if (tResult != null && !tResult.equals(""))
                   {
//                       tLLAccidentSchema.setAccNo(""); //事件号
//                       tLLRegisterSchema.setRgtNo(""); //报案号=赔案号
//                       tLLCaseSchema.setCaseNo(""); //分案号=报案号=赔案号
                       tLLAccidentSchema.setAccNo(tRgtNo); //事件号
                       tLLRegisterSchema.setRgtNo(tRgtNo); //报案号=赔案号
                       tLLCaseSchema.setCaseNo(tRgtNo); //分案号=报案号=赔案号

                       tLLCaseSchema.setRgtType("02"); //1-立案
                       tLLCaseSchema.setRgtState("20"); //案件状态
                       tLLCaseSchema.setCustomerNo(tCustomerNo); //出险人编码
                       tLLCaseSchema.setCustomerName(tCustomerName); //出险人名称
                       tLLCaseSchema.setCustomerSex(tCustomerSex); //出险人性别
                       tLLCaseSchema.setAccDate(tAccidentDate); //出险日期
                       tLLCaseSchema.setIDType(tIDType); //出险人证件类型
                       tLLCaseSchema.setIDNo(tIDNo); //出险人证件号码
                       tLLCaseSchema.setSecondUWFlag(ID); //为借用字段 以便跟帐单ID匹配

                       tLLRegisterSchema.setAccidentDate(tAccidentDate); //意外事故发生日期
                       tLLRegisterSchema.setAccidentReason(tOccurReason); //出险原因
                       tLLRegisterSchema.setGrpContNo(tGrpContNo); //团体保单号
                       tLLRegisterSchema.setRiskCode(tRiskCode); //保单险种号
                       tLLRegisterSchema.setAppntNo(tGrpCustomerNo); //团体客户号
                       tLLRegisterSchema.setGrpName(tGrpName); //单位名称
                       tLLRegisterSchema.setPeoples2(tPeoples2); //投保总人数
                       tLLRegisterSchema.setRgtClass("2"); // 1个单 2团单
                       tLLRegisterSchema.setRgtObj(tAccFlag);
                       tLLRegisterSchema.setRgtState("02"); //01简易案件 02帐户案件 03非理算理赔

                       //用事故描述来存申请原因代码
                       String[] AppReasonCode = getCode(tAppReasonCode);
                       for (int i = 0; i < AppReasonCode.length; i++)
                       {
                           AppReasonCode[i] = tOccurReason +
                                              AppReasonCode[i];
                           tLLAppClaimReasonSchema = new
                                   LLAppClaimReasonSchema();
//                           tLLAppClaimReasonSchema.setCaseNo(""); //报案号=赔案号
//                           tLLAppClaimReasonSchema.setRgtNo(""); //报案号=赔案号
                           tLLAppClaimReasonSchema.setCaseNo(tRgtNo); //报案号=赔案号
                           tLLAppClaimReasonSchema.setRgtNo(tRgtNo); //报案号=赔案号
                           tLLAppClaimReasonSchema.setCustomerNo(
                                   tCustomerNo); //出险人编码
                           tLLAppClaimReasonSchema.setReasonCode(
                                   AppReasonCode[i]); //理赔类型
                           tLLAppClaimReasonSet.add(
                                   tLLAppClaimReasonSchema);
                       }
                       //准备保险账户分类表信息
                       if (tAccFlag.equals("20"))
                       {
//                           tLLClaimAccountSchema.setClmNo("");
                           tLLClaimAccountSchema.setClmNo(tRgtNo);
                           tLLClaimAccountSchema.setContNo(tContNo);
                           tLLClaimAccountSchema.setGrpContNo(
                                   tGrpContNo);
                           tLLClaimAccountSchema.setGrpPolNo(tGrpPolNo);
                           tLLClaimAccountSchema.setOtherNo(tRiskCode);
                           tLLClaimAccountSchema.setRiskCode(tRiskCode);
                           tLLClaimAccountSchema.setAccPayMoney(tPesAcc);
                           tLLClaimAccountSchema.setOtherType("S");
                           tLLClaimAccountSchema.setDeclineNo(
                                   tCustomerNo);
                           tLLClaimAccountSchema.setPayDate(
                                   tAccidentDate);

//                           tLLClaimAccountSchema1.setClmNo("");
                            tLLClaimAccountSchema1.setClmNo(tRgtNo);
                           tLLClaimAccountSchema1.setContNo(tContNo);
                           tLLClaimAccountSchema1.setGrpContNo(
                                   tGrpContNo);
                           tLLClaimAccountSchema1.setGrpPolNo(tGrpPolNo);
                           tLLClaimAccountSchema1.setOtherNo(tRiskCode);
                           tLLClaimAccountSchema1.setRiskCode(tRiskCode);
                           tLLClaimAccountSchema1.setAccPayMoney("0");
                           tLLClaimAccountSchema1.setOtherType("P");
                           tLLClaimAccountSchema1.setDeclineNo(
                                   tCustomerNo);
                           tLLClaimAccountSchema1.setPayDate(
                                   tAccidentDate);
                       }
                       else if (tAccFlag.equals("10"))
                       {
//                           tLLClaimAccountSchema.setClmNo("");
                            tLLClaimAccountSchema.setClmNo(tRgtNo);
                           tLLClaimAccountSchema.setContNo(tContNo);
                           tLLClaimAccountSchema.setGrpContNo(
                                   tGrpContNo);
                           tLLClaimAccountSchema.setGrpPolNo(tGrpPolNo);
                           tLLClaimAccountSchema.setOtherNo(tRiskCode);
                           tLLClaimAccountSchema.setRiskCode(tRiskCode);
                           tLLClaimAccountSchema.setAccPayMoney(tPesAcc);
                           tLLClaimAccountSchema.setOtherType("S");
                           tLLClaimAccountSchema.setDeclineNo(
                                   tCustomerNo);
                           tLLClaimAccountSchema.setPayDate(
                                   tAccidentDate);

//                           tLLClaimAccountSchema1.setClmNo("");
                           tLLClaimAccountSchema1.setClmNo(tRgtNo);
                           tLLClaimAccountSchema1.setContNo(tContNo);
                           tLLClaimAccountSchema1.setGrpContNo(
                                   tGrpContNo);
                           tLLClaimAccountSchema1.setGrpPolNo(tGrpPolNo);
                           tLLClaimAccountSchema1.setOtherNo(tRiskCode);
                           tLLClaimAccountSchema1.setRiskCode(tRiskCode);
                           tLLClaimAccountSchema1.setAccPayMoney(
                                   tAccAmt);
                           tLLClaimAccountSchema1.setOtherType("P");
                           tLLClaimAccountSchema1.setDeclineNo(
                                   tCustomerNo);
                           tLLClaimAccountSchema1.setPayDate(
                                   tAccidentDate);
                       }
                       tLLClaimAccountSet.add(tLLClaimAccountSchema);
                       tLLClaimAccountSet.add(tLLClaimAccountSchema1);

                       tLLCaseVData.add(ID);
                       tLLCaseVData.add(tCustomerNo);
                       tLLCaseVData.add(tCustomerName);
                       tLLCaseVData.add(tRgtNo);
                       tLLCaseVData.add(tLLCaseSchema);
                       tLLCaseVData.add(tLLRegisterSchema);
                       tLLCaseVData.add(tLLAccidentSchema);
                       tLLCaseVData.add(tLLAppClaimReasonSet);
                       tLLCaseVData.add(tLLClaimAccountSet);
                   }
                   else
                   {
                       // @@错误处理
                       CError tError = new CError();
                       tError.moduleName = "GrpCustomerParser";
                       tError.functionName = "getLLCaseData";
                       tError.errorMessage = "客户号：" + tCustomerNo +
                                             " 姓名：" + tCustomerName +
                                             " 不是团体客户号：" + tGrpCustomerNo +
                                             "下的客户";
                       this.mErrors.addOneError(tError);
                       tLLCaseVData.add(ID);
                       tLLCaseVData.add(tCustomerNo);
                       tLLCaseVData.add(tCustomerName);
                       tLLCaseVData.add(tRgtNo);
                   }
               }
           }
       return tLLCaseVData;
   }
   /**===============================================================================================
     * 从XML中解析立案信息,取得账单和账单明细
     * @param node
     * @return vdata
     * desc: 用于账户案件理赔
     * auther: 万泽辉
     * date:2006-01-17
     ==================================================================================================
    */

   public VData getFeeVDataForAcc(Node node,String mRgtNo,LLCaseSet mLLCaseSet)
   {
         LLFeeMainSet tLLFeeMainSet         = new LLFeeMainSet();
         LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();
         String tCustomerNo = "";
         VData tLLFeeVData = new VData();
         LLFeeMainSchema tLLFeeMainSchema;
             Node nodeLLFeeMain = node;
             //帐单ID匹配客户号
             String tFeeID = parseNode(nodeLLFeeMain, "FeeID");
             //通过案件ID号到llcase表中查询客户号
             String strSql = "Select CustomerNo from llcase where caseno = '" +
                             mRgtNo + "' and SecondUWFlag = '" + tFeeID + "'";
             ExeSQL exesql = new ExeSQL();
             tCustomerNo = exesql.getOneValue(strSql);

             if (tCustomerNo != null && !tCustomerNo.equals(""))
             {
                 tLLFeeMainSchema = new LLFeeMainSchema();
                 tLLFeeMainSchema.setClmNo(mRgtNo); //赔案号
                 tLLFeeMainSchema.setCaseNo(mRgtNo); //分案号
                 tLLFeeMainSchema.setMainFeeNo(parseNode(nodeLLFeeMain,
                         "MainFeeNo")); //帐单号码
                 tLLFeeMainSchema.setCustomerNo(tCustomerNo); //客户号
                 tLLFeeMainSchema.setHospitalCode(parseNode(nodeLLFeeMain,
                         "HospitalCode")); //医院代码
                 tLLFeeMainSchema.setFeeType(parseNode(nodeLLFeeMain, "FeeType")); //A 门诊 B住院
                 tLLFeeMainSet.clear();
                 tLLFeeMainSet.add(tLLFeeMainSchema);
                 LLCaseReceiptSchema tLLCaseReceiptSchema = new
                         LLCaseReceiptSchema();
                 tLLCaseReceiptSchema.setClmNo(mRgtNo);
                 tLLCaseReceiptSchema.setCaseNo(mRgtNo);
                 tLLCaseReceiptSchema.setRgtNo(mRgtNo);
                 tLLCaseReceiptSchema.setMainFeeNo(parseNode(nodeLLFeeMain,
                         "MainFeeNo"));
                 tLLCaseReceiptSchema.setFeeItemType(parseNode(nodeLLFeeMain,
                         "FeeType")); //A 门诊 B住院
//                 tLLCaseReceiptSchema.setFeeItemName(parseNode(nodeLLFeeMain,
//                         "FeeType")); //A 门诊 B住院
                 tLLCaseReceiptSchema.setDiseaseCode(parseNode(nodeLLFeeMain,
                         "DiseaseCode")); //疾病代码
                 tLLCaseReceiptSchema.setFeeItemCode(parseNode(nodeLLFeeMain,
                         "FeeItemCode")); //费用类型

                 String tFeeItemCode = parseNode(nodeLLFeeMain,"FeeItemCode");
                 String FeeSql="select codename from ldcode where codetype='llfeeitemtype' and code='"+tFeeItemCode+"' ";
                 String tFeeItemName=exesql.getOneValue(FeeSql);
                 tLLCaseReceiptSchema.setFeeItemName(tFeeItemName); //费用名称，可以从code表里取

                 tLLCaseReceiptSchema.setFee(parseNode(nodeLLFeeMain, "OriFee")); //原始费用
                 tLLCaseReceiptSchema.setRefuseAmnt(parseNode(nodeLLFeeMain,
                         "RefuseAmnt")); //扣除费用
                 tLLCaseReceiptSchema.setAdjReason(parseNode(nodeLLFeeMain,
                         "AdjReason")); //扣除代码
                 tLLCaseReceiptSchema.setAdjRemark(parseNode(nodeLLFeeMain,
                         "AdjRemark")); //扣除备注
                 tLLCaseReceiptSchema.setStartDate(parseNode(nodeLLFeeMain,
                         "StartDate")); //开始日期
                 tLLCaseReceiptSchema.setEndDate(parseNode(nodeLLFeeMain,
                         "EndDate")); //结束日期
                 tLLCaseReceiptSchema.setDealFlag("1"); //开始日期是否早于出险日期,0是1不是
                 tLLCaseReceiptSchema.setCustomerNo(tCustomerNo); //客户号
                 tLLCaseReceiptSet.clear(); //每次提交时候清空set集合
                 tLLCaseReceiptSet.add(tLLCaseReceiptSchema);

                 tLLFeeVData.add(tLLFeeMainSet);
                 tLLFeeVData.add(tLLCaseReceiptSet);
             }
         return tLLFeeVData;

   }
   /**===============================================================================================
      * 检查导入的帐单中[团体赔付金额]是否小于团体人帐户余额
      * @param String dGrpContNo 团体保单号
      * @param String dAccAmt 团体赔付金额
      * @return
      * desc: 用于账户案件理赔
      * auther: 万泽辉
      * date:2006-02-22
      ==================================================================================================
     */

   private boolean checkPAccount(String dGrpContNo,String dAccAmt)
       {
           String cSql = "";
           ExeSQL tExeSQL = new ExeSQL();
           if (dAccAmt != null && !dAccAmt.equals("") && dAccAmt.equals("0"))
           {
               cSql =
                       "select lastaccbala from lcinsureaccclass where grpcontno = '" +
                       dGrpContNo + "'"
                       + " and acctype = '001'";
               SSRS cSSRS = tExeSQL.execSQL(cSql);
               if (cSSRS.getMaxRow() < 1)
               {
                   CError tError = new CError();
                   tError.moduleName = "GrpCustomerGuideForAccIn";
                   tError.functionName = "ParseXML";
                   tError.errorMessage = "没有找到团体保单号[" + dGrpContNo +
                                         "]下的帐户信息!";
                   this.mErrors.addOneError(tError);
                   return false;
               }
               if (Double.parseDouble(cSSRS.GetText(1, 1)) <
                   Double.parseDouble(dAccAmt))
               {
                   return false;
               }
           }
           return true;
       }
    /**===============================================================================================
      * 检查导入的帐单中[个人赔付金额]是否小于个人帐户余额
      * @param String eGrpContNo 团体保单号
      * @param String eCustomerNo 投保人客户号
      * @param String ePesAcc 个人赔付金额
      * @return
      * desc: 用于账户案件理赔
      * auther: 万泽辉
      * date:2006-02-22
      ==================================================================================================
     */

       private boolean checkSAccount(String eGrpContNo,String eCustomerNo,String ePesAcc)
       {

         String eSql = "";
         ExeSQL tExeSQL = new ExeSQL();

         if (ePesAcc != null && !ePesAcc.equals("") && !ePesAcc.equals("0"))
         {
             eSql =
                     "select lastaccbala from lcinsureaccclass where grpcontno = '" +
                     eGrpContNo + "'"
                     + " and acctype = '002' and insuredno = '" +
                     eCustomerNo + "'";
             SSRS cSSRS1 = tExeSQL.execSQL(eSql);
             if (cSSRS1.getMaxRow() < 1)
             {
                 CError tError = new CError();
                 tError.moduleName = "GrpCustomerGuideForAccIn";
                 tError.functionName = "ParseXML";
                 tError.errorMessage = "没有找到团体保单号[" + eGrpContNo +
                                       "]下的出险人[" + eCustomerNo +
                                       "]的个人帐户信息!";
                 this.mErrors.addOneError(tError);
                 return false;
             }
             else
             {
                 if (Double.parseDouble(cSSRS1.GetText(1, 1)) <
                     Double.parseDouble(ePesAcc))
                 {
                     return false;
                 }
             }
         }
         return true;
  }

//取得申请材料
public VData getAffixVData(Node node,VData tVData)
{
      //处理材料子表
            NodeList nodeList = null;
            int nLength = 0;
            String caseNo =(String) tVData.getObjectByObjectName("String",0);
            String rgtNo = (String) tVData.getObjectByObjectName("String",1);
            LLAffixSet tLLAffixSet = new LLAffixSet();
            LLFeeMainSet tLLFeeMainSet = new LLFeeMainSet();
            LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();
            try
            {
                nodeList = XPathAPI.selectNodeList(node, "LLAFFIXTABLE/ROW");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                nodeList = null;
                return null;
            }
             nLength = nodeList == null ? 0 : nodeList.getLength();
            for (int nIndex = 0; nIndex < nLength; nIndex++)
            {
                Node nodeLLAffix = nodeList.item(nIndex);
                LLAffixSchema tLLAffixSchema = new LLAffixSchema();
                tLLAffixSchema.setCaseNo(caseNo);
                tLLAffixSchema.setRgtNo(rgtNo);
                tLLAffixSchema.setAffixType(parseNode(nodeLLAffix,
                                                    "AffixType"));
                tLLAffixSchema.setAffixCode(parseNode(nodeLLAffix,
                                                     "AffixCode"));
                tLLAffixSchema.setSupplyDate(parseNode(nodeLLAffix,
                                                        "SupplyDate"));
                tLLAffixSchema.setReadyCount(parseNode(nodeLLAffix,
                                                   "count"));
                tLLAffixSchema.setShortCount(parseNode(nodeLLAffix,
                                                  "ShortCount"));
                tLLAffixSchema.setReasonCode("13");
                tLLAffixSet.add(tLLAffixSchema);
            }
            VData tLLAffixVData = new VData();
            tLLAffixVData.add(tLLAffixSet);
            return tLLAffixVData;
}
    /**
     * 从字符串中根据分隔符提取编码
     */
    public  String[] getCode(String str) {
      String strcode = str.trim();
      Vector vect = new Vector();
      String code = "";
      while (strcode.indexOf(",") != -1) {
        code = "";
        code = strcode.substring(0, strcode.indexOf(","));
        //剩下的串
        strcode = strcode.substring(strcode.indexOf(",") + 1,
                                    strcode.length());
        if (!(code.length()==0 || ",".equals(code))) {
          vect.add(code);
        }
      }
      //对最后的串处理
      if (strcode.indexOf(",") == -1&&strcode.length()>0)
        vect.add(strcode);
      int length = vect.size();
      String[] returnstr = new String[length];
      for (int i = 0; i < length; i++) {
        returnstr[i] = (String) vect.get(i);
      }
      return returnstr;
    }

    /**
     * 创建错误信息
     * @param szFunc String 函数名称
     * @param szErrMsg String 错误信息
     */
    private void buildError(String szFunc,
                            String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "LCPolParser";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

//======ADD===2006-1-18 9:36======zhoulei=======================================BEG
    /**
     * 取得理赔理算信息,相应的模板增加sheet3
     * @param tNode NodeList 结点
     * @param tRgtNo String 团体赔案号
     * @param tLLCaseSet LLCaseSet 基础分案信息
     * @return VDate
     */
    public VData getClaimCalVData(Node tNode, String tRgtNo)
    {
        //得到结点数
//        NodeList nodeList = null;
//        int nLength = 0;
//        nLength = nodeList == null ? 0 : nodeList.getLength();
//
        LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
//        LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet();

        //遍历每个结点
//        for (int n = 0; n < nLength; n++)
//        {
            /**-----------------------------------------------------------------
             * No1 得到结点信息,及非空校验
             *------------------------------------------------------------------*/
//            Node tNode = nodeList.item(n);

            String tCusID = parseNode(tNode, "ClaimID"); //帐单ID匹配客户号
            String tRiskCode = parseNode(tNode, "RiskCode"); //险种
            String tGetDutyCode = parseNode(tNode, "GetDutyCode"); //给付责任代码
            String tStandPay = parseNode(tNode, "StandPay"); //险种责任理算金额
            String tRealPay = parseNode(tNode, "RealPay"); //险种责任实际给付金额
            /**-----------------------------------------------------------------
             * No2 根据结点信息查询所需信息
             * 条件：团单号、客户号、险种
             * 结果：grpcontno,lcpol,dutycode,dutycode
             *------------------------------------------------------------------*/

            //匹配客户号
            String tCustomerNo = "";
//            for (int i = 1; i <= tLLCaseSet.size(); i++)
//            {
//                //getEditFlag 为借用字段 以便跟帐单ID匹配 不存入数据库
//                if (tLLCaseSet.get(i).getSecondUWFlag().equals(tCusID))
//                {
//                    tCustomerNo = tLLCaseSet.get(i).getCustomerNo();
//                    break;
//                }
//            }
//            if (tCustomerNo == null || tCustomerNo.equals(""))
//            {
//                //error
//                continue;
//            }
            //通过案件ID号到llcase表中查询客户号
            String strSql = "Select CustomerNo from llcase where caseno = '" +
                            tRgtNo + "' and SecondUWFlag = '" + tCusID + "'";
            ExeSQL exesql = new ExeSQL();
            tCustomerNo = exesql.getOneValue(strSql);
            if(tCustomerNo!=null&&!tCustomerNo.equals("")){
                //团体保单号
                String tSqlStr =
                        "select a.grpcontno from llregister a where 1=1 "
                        + " and a.rgtno = '" + tRgtNo + "'";
                String tGrpContNo = exesql.getOneValue(tSqlStr);
                if (tGrpContNo == null || tGrpContNo.equals("")) {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "GrpCustomerParser";
                    tError.functionName = "getClaimCalVData";
                    tError.errorMessage = "查询团体保单号失败！";
                    this.mErrors.addOneError(tError);
                }
            //查询保单信息，包括polno
            LCPolDB tLCPolDB = new LCPolDB();
            LCPolSchema tLCPolSchema = new LCPolSchema();
            String tSql = "select * from lcpol where 1=1 "
                        + " and grpcontno = '" + tGrpContNo +"'"
                        + " and insuredno = '" + tCustomerNo +"'"
                        + " and riskcode = '" + tRiskCode +"'";
            LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSql);
            if (tLCPolSet == null || tLCPolSet.size() <= 0)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getClaimCalVData";
                tError.errorMessage = "查询保单信息失败！";
                this.mErrors.addOneError(tError);
//                break;
            }
            else
            {
                tLCPolSchema = tLCPolSet.get(1);
            }
            //给付责任类型（理赔类型）
            String tSQLR = "select e.reasoncode from llappclaimreason e where 1=1"
                         + " and e.caseno = '" + tRgtNo + "'"
                         + " and e.customerno = '" + tCustomerNo + "'";
            String tGetdutyKind = exesql.getOneValue(tSQLR);
            if (tGetdutyKind == null || tGetdutyKind.equals(""))
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getClaimCalVData";
                tError.errorMessage = "查询理赔类型失败！";
                this.mErrors.addOneError(tError);
//                break;
            }
            //查询责任编码dutycode,以及进行有效保额控制
            String tStrSql = "select dutycode,StandMoney,SumMoney from lcget where 1=1 "
               + " and polno = '" + tLCPolSchema.getPolNo() + "'"
               + " and getdutycode = '" + tGetDutyCode + "'";
            SSRS tSSRS = exesql.execSQL(tStrSql);
            if (exesql.mErrors.needDealError())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "GrpCustomerParser";
                tError.functionName = "getClaimCalVData";
                tError.errorMessage = "责任编码，保额查询失败！";
                this.mErrors.addOneError(tError);
//                break;
            }
            String tDutyCode    = "";
            String tStandMoney  = "";
            String tSumMoney    = "";
            for (int i = 1; i <= tSSRS.getMaxRow(); i++)
            {
                 tDutyCode = tSSRS.GetText(1, 1); //责任编码
                 tStandMoney = tSSRS.GetText(1, 2); //标准给付金额
                 tSumMoney = tSSRS.GetText(1, 3); //已领金额
            }
            double dStandMoney = Double.parseDouble(tStandMoney);
            double dSumMoney   = Double.parseDouble(tSumMoney);
            double dRealPay    = Double.parseDouble(tRealPay);
            double dMoney      = 0.00;
            dStandMoney = PubFun.setPrecision( dStandMoney, "0.00"); //标准给付金额
            dSumMoney   = PubFun.setPrecision( dSumMoney, "0.00");   //实际给付金额
            dRealPay    = PubFun.setPrecision( dRealPay, "0.00");    //险种责任实际给付金额
            dMoney      = PubFun.setPrecision( dMoney, "0.00");      //有效保额
            if(dStandMoney - dSumMoney <=0){
                dMoney = 0.00;
            }else{
                dMoney = dStandMoney - dSumMoney;
            }
            /*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * 修改原因：对有津贴的责任最大的保额是责任保额20，取消这个限制，应该已它的实际责任保额为准
             * 修 改 人：万泽辉
             * 修改日期：20060208
             －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tSQL = "select * from lmdutygetclm where deadtopvalueflag = 'N' and "
                          +" getdutycode = '"+tGetDutyCode+"'";
            SSRS tSSRS1 = exesql.execSQL(tSQL);
            if( tSSRS1.getMaxRow() < 1 )
            {
                if (dRealPay > dMoney)
                {
                    dRealPay = dMoney;
                }
                else if (dRealPay <= 0)
                {
                    dRealPay = 0.00;
                }
            }
            tRealPay = Double.toString(dRealPay);
            /**-----------------------------------------------------------------
             * No3 拼装存储信息
             * 涉及表：LLClaimDetail
             *------------------------------------------------------------------*/
            LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
            tLLClaimDetailSchema.setClmNo(tRgtNo);
            tLLClaimDetailSchema.setCaseNo(tRgtNo);
            tLLClaimDetailSchema.setPolNo(tLCPolSchema.getPolNo());
            tLLClaimDetailSchema.setPolSort("");         //保单类型,C表保单，B表保单,A承保出单前出险
            tLLClaimDetailSchema.setPolType("2");         /*保单性质0 --个人单,1 --无名单,2 --（团单）公共帐户*/

            tLLClaimDetailSchema.setDutyCode(tDutyCode);
            tLLClaimDetailSchema.setGetDutyKind(tGetdutyKind);
            tLLClaimDetailSchema.setGetDutyCode(tGetDutyCode);
            tLLClaimDetailSchema.setCaseRelaNo(tRgtNo);

            tLLClaimDetailSchema.setDefoType("");           /*伤残类型*/
            tLLClaimDetailSchema.setRgtNo(tRgtNo);     /*立案号*/
            tLLClaimDetailSchema.setDeclineNo("");          /*拒赔号*/
            tLLClaimDetailSchema.setStatType("");           /*统计类别*/

            tLLClaimDetailSchema.setContNo(tLCPolSchema.getContNo());
            tLLClaimDetailSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
            tLLClaimDetailSchema.setGrpContNo(tLCPolSchema.getGrpContNo());

            tLLClaimDetailSchema.setKindCode(tLCPolSchema.getKindCode());
            tLLClaimDetailSchema.setRiskCode(tLCPolSchema.getRiskCode());
            tLLClaimDetailSchema.setRiskVer(tLCPolSchema.getRiskVersion());
            tLLClaimDetailSchema.setPolMngCom(tLCPolSchema.getManageCom());
            tLLClaimDetailSchema.setSaleChnl(tLCPolSchema.getSaleChnl());
            tLLClaimDetailSchema.setAgentCode(tLCPolSchema.getAgentCode());

            tLLClaimDetailSchema.setAmnt(tLCPolSchema.getAmnt());               //有效保额
            tLLClaimDetailSchema.setGracePeriod(""); //缴费宽限期
            tLLClaimDetailSchema.setCasePolType(""); //给付类型
            tLLClaimDetailSchema.setYearBonus("");     //年度红利
            tLLClaimDetailSchema.setEndBonus("");       //终了红利
            tLLClaimDetailSchema.setGiveType("0");       //给付标志
            tLLClaimDetailSchema.setCustomerNo(tCustomerNo);   //用于存放出险人编号
            tLLClaimDetailSchema.setPrepayFlag("0");   //预付标志,0没有预付,1已经预付
            tLLClaimDetailSchema.setPrepaySum(0);     //预付金额
            tLLClaimDetailSchema.setAcctFlag("0");     //帐户标志.0非帐户,1个险帐户
            tLLClaimDetailSchema.setPosFlag("");         //0未做保全,1已做保全
            tLLClaimDetailSchema.setPosEdorNo("");     //保全批单号
            tLLClaimDetailSchema.setCValiDate(tLCPolSchema.getCValiDate());     //保单生效日期
            tLLClaimDetailSchema.setEffectOnMainRisk("");   //附加险影响主险标志
            tLLClaimDetailSchema.setRiskCalCode("");       //用于保存主险的公式
            tLLClaimDetailSchema.setAddAmnt("0");        //加保保额
            tLLClaimDetailSchema.setNBPolNo("");        //做过续期抽档之后,把原号保存起来,用于后续业务
            tLLClaimDetailSchema.setPosEdorType("");   //保全业务类型
            tLLClaimDetailSchema.setRiskType("");    // 主附险标志 M主险 S附险

            tLLClaimDetailSchema.setStandPay(tStandPay); //理算金额
            tLLClaimDetailSchema.setRealPay(tRealPay); //实付金额

            tLLClaimDetailSchema.setMakeDate(PubFun.getCurrentDate());
            tLLClaimDetailSchema.setMakeTime(PubFun.getCurrentTime());
            tLLClaimDetailSchema.setModifyDate(PubFun.getCurrentDate());
            tLLClaimDetailSchema.setModifyTime(PubFun.getCurrentTime());
//            tLLClaimDetailSchema.setOperator(this.mGlobalInput.Operator); //在提交之前补充
//            tLLClaimDetailSchema.setMngCom(this.mGlobalInput.ManageCom);
            tLLClaimDetailSet.clear();
            tLLClaimDetailSet.add(tLLClaimDetailSchema);
//        }
            }
        VData tLLFeeVData = new VData();
        tLLFeeVData.add(tLLClaimDetailSet);
        return tLLFeeVData;
    }
//======ADD===2006-1-18 9:36======zhoulei=======================================END


}
