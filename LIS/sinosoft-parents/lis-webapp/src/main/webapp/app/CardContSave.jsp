<%@ page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@ page import="com.sinosoft.utility.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>
<%@ page import="com.sinosoft.lis.tb.*" %>
<%@ page import="com.sinosoft.lis.db.*" %>
<%@ page import="com.sinosoft.lis.pubfun.*" %>
<%@ page import="java.text.*" %>
<%@page import="com.sinosoft.service.*" %>
<%
    //输出参数
    CErrors tError = null;
    String tRela = "";
    String FlagStr = "";
    String Content = "";
    String tOperate = "";
    String flag = "";

    //输入参数
    VData tVData = new VData();
    LCContSchema tLCContSchema = new LCContSchema();
    LCAppntSchema tLCAppntSchema = new LCAppntSchema();
    LCAddressSchema tLCAddressSchema = new LCAddressSchema();
    LCAccountSchema tLCAccountSchema = new LCAccountSchema();
    LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
    LACommisionDetailSet tLACommisionDetailSet = new LACommisionDetailSet();
    String tRiskCode = request.getParameter("RiskCode");
    String tContType = "1";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");
    
    ExeSQL tExeSQL = new ExeSQL();
    tContType = tExeSQL.getOneValue("select (case count(*) when 0 then 1 else 2 end) from lmriskapp"
              + " where riskcode='" + tRiskCode 
              + "' and RiskProp in ('G','A','B','D')");
    loggerDebug("CardContSave","ContType=" + tContType);

    ////////////////////以上合同信息与投保人信息/////////
    //合同信息已经具有而且数值已经准备好
    LDPersonSchema tLDPersonSchema = new LDPersonSchema();
    LCInsuredDB tOLDLCInsuredDB = new LCInsuredDB();
    LCAddressSchema mLCAddressSchema = new LCAddressSchema(); //注意是不同的值
    LCInsuredSchema tmLCInsuredSchema = new LCInsuredSchema();
    //LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet(); //已经具有
    LCCustomerImpartDetailSet tLCCustomerImpartDetailSet = new LCCustomerImpartDetailSet();
    ////////////////////以上被保人信息////////////////////
    LCPolSchema tLCPolSchema = new LCPolSchema();
    
    LCPolOtherSet tLCPolOtherSet = new LCPolOtherSet();
    LCPolOtherSchema tLCPolOtherSchema = new LCPolOtherSchema();
    
    LCDutySchema tLCDutySchema = new LCDutySchema();
    LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema();
    LCInsuredRelatedSet mLCInsuredRelatedSet = new LCInsuredRelatedSet();
    LCBnfSet tLCBnfSet = new LCBnfSet();
    LCSpecSet tLCSpecSet = new LCSpecSet();
    ///////////////////以上险种信息////////////////////////

    flag = request.getParameter("fmAction"); //操作方式
    loggerDebug("CardContSave","操作方式in save " + flag);
    //loggerDebug("CardContSave","------------------agetngroup=="+request.getParameter("AgentGroup"));
    if (flag.equals("DELETE")) {
        //合同信息
        tLCContSchema.setContNo(request.getParameter("ProposalContNo"));
        //投保人信息
        tLCAppntSchema.setContNo(request.getParameter("ProposalContNo"));
        //代理人提拥表
        //LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
        //tLACommisionDetailSchema.setGrpContNo(request.getParameter("ProposalContNo"));
        //tLACommisionDetailSchema.setAgentCode(request.getParameter("AgentCode"));
        //tLACommisionDetailSet.add(tLACommisionDetailSchema);
        //被保人表
        //tmLCInsuredSchema.setContNo(request.getParameter("ProposalContNo"));
        //tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
        //险种信息表
        //tLCPolSchema.setContNo(request.getParameter("ProposalContNo"));
        //tLCDutySchema.setContNo(request.getParameter("ProposalContNo"));
        //LCBnfSchema tLCBnfSchema = new LCBnfSchema();
        //tLCBnfSchema.setContNo(request.getParameter("ProposalContNo"));      
        //tLCBnfSet.add(tLCBnfSchema);
    } else {
        tLCContSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCContSchema.setContNo(request.getParameter("ProposalContNo"));
        tLCContSchema.setProposalContNo(request.getParameter("ProposalContNo"));
        loggerDebug("CardContSave","合同号====" + request.getParameter("ProposalContNo"));
        tLCContSchema.setPrtNo(request.getParameter("ProposalContNo"));
        tLCContSchema.setManageCom(request.getParameter("ManageCom"));
        //销售渠道为个险 02
        tLCContSchema.setSaleChnl(tContType);
        //销售方式：
        tLCContSchema.setSellType(request.getParameter("SellType"));
        tLCContSchema.setAgentCode(request.getParameter("AgentCode"));
        tLCContSchema.setAgentCode1(request.getParameter("AgentCode1"));
        tLCContSchema.setAgentGroup(request.getParameter("AgentGroup"));
        tLCContSchema.setRemark(request.getParameter("Remark"));
        tLCContSchema.setAgentCom(request.getParameter("AgentCom"));
        tLCContSchema.setAgentType(request.getParameter("AgentType"));
        tLCContSchema.setPolType("0");
        tLCContSchema.setContType(tContType);
        tLCContSchema.setBankCode(request.getParameter("AppntBankCode"));
        tLCContSchema.setAccName(request.getParameter("AppntAccName"));
        tLCContSchema.setBankAccNo(request.getParameter("AppntBankAccNo"));
        tLCContSchema.setPolApplyDate(request.getParameter("PolAppntDate"));
        tLCContSchema.setForceUWFlag("0");
        /**
         * 卡单不需要进入人工核保，则用强制进入人工核保原因暂存卡号，用于签单批处理
         * hl 2006-01-19
         *
         */
        tLCContSchema.setForceUWReason(request.getParameter("ContCardNo"));

        //投保人信息
        tLCAppntSchema.setAppntNo(request.getParameter("AppntNo"));
        tLCAppntSchema.setContNo(request.getParameter("ProposalContNo"));
        tLCAppntSchema.setAppntName(request.getParameter("AppntName"));
        tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));
        tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));
        tLCAppntSchema.setIDType(request.getParameter("AppntIDType"));
        tLCAppntSchema.setIDNo(request.getParameter("AppntIDNo"));
        tLCAppntSchema.setRgtAddress(request.getParameter("AppntRgtAddress"));
        tLCAppntSchema.setMarriage(request.getParameter("AppntMarriage"));
        tLCAppntSchema.setDegree(request.getParameter("AppntDegree"));
        tLCAppntSchema.setOccupationType(request.getParameter("AppntOccupationType"));
        tLCAppntSchema.setOccupationCode(request.getParameter("AppntOccupationCode"));
        tLCAppntSchema.setWorkType(request.getParameter("AppntWorkType"));
        tLCAppntSchema.setPluralityType(request.getParameter("AppntPluralityType"));
        tLCAppntSchema.setSmokeFlag(request.getParameter("AppntSmokeFlag"));
        tLCAppntSchema.setNativePlace(request.getParameter("AppntNativePlace"));
        tLCAppntSchema.setNationality(request.getParameter("AppntNationality"));
        tLCAppntSchema.setBankCode(request.getParameter("AppntBankCode"));
        tLCAppntSchema.setAccName(request.getParameter("AppntAccName"));
        tLCAppntSchema.setBankAccNo(request.getParameter("AppntBankAccNo"));
        tLCAppntSchema.setMakeDate(request.getParameter("AppntMakeDate"));
        tLCAppntSchema.setMakeTime(request.getParameter("AppntMakeTime"));
        tLCAppntSchema.setLicenseType(request.getParameter("AppntLicenseType"));

        tLCAddressSchema.setCustomerNo(request.getParameter("AppntNo"));
        tLCAddressSchema.setAddressNo(request.getParameter("AppntAddressNo"));
        tLCAddressSchema.setPostalAddress(request.getParameter("AppntPostalAddress"));
        tLCAddressSchema.setZipCode(request.getParameter("AppntZipCode"));
        tLCAddressSchema.setPhone(request.getParameter("AppntPhone"));
        tLCAddressSchema.setFax(request.getParameter("AppntFax"));
        tLCAddressSchema.setMobile(request.getParameter("AppntMobile"));
        tLCAddressSchema.setEMail(request.getParameter("AppntEMail"));
        tLCAddressSchema.setHomeAddress(request.getParameter("AppntHomeAddress"));
        tLCAddressSchema.setHomePhone(request.getParameter("AppntHomePhone"));
        tLCAddressSchema.setHomeFax(request.getParameter("AppntHomeFax"));
        tLCAddressSchema.setHomeZipCode(request.getParameter("AppntHomeZipCode"));
        tLCAddressSchema.setCompanyPhone(request.getParameter("AppntGrpPhone"));
        tLCAddressSchema.setCompanyAddress(request.getParameter("CompanyAddress"));
        tLCAddressSchema.setCompanyZipCode(request.getParameter("AppntGrpZipCode"));
        tLCAddressSchema.setCompanyFax(request.getParameter("AppntGrpFax"));
        tLCAddressSchema.setGrpName(request.getParameter("AppntGrpName"));
        tLCAddressSchema.setProvince(request.getParameter("AppntProvince"));
        tLCAddressSchema.setCity(request.getParameter("AppntCity"));
        tLCAddressSchema.setCounty(request.getParameter("AppntDistrict"));

        //被保人信息
        tmLCInsuredSchema.setInsuredNo(request.getParameter("InsuredNo"));
        tmLCInsuredSchema.setRelationToMainInsured(request.getParameter("RelationToMainInsured"));
        tmLCInsuredSchema.setRelationToAppnt(request.getParameter("RelationToAppnt"));
        tmLCInsuredSchema.setContNo(request.getParameter("ProposalContNo"));
        tmLCInsuredSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tmLCInsuredSchema.setContPlanCode(request.getParameter("ContPlanCode"));
        tmLCInsuredSchema.setExecuteCom(request.getParameter("ExecuteCom"));
        tmLCInsuredSchema.setJoinCompanyDate(request.getParameter("JoinCompanyDate"));
        tmLCInsuredSchema.setInsuredPeoples(request.getParameter("InsuredPeoples"));
        tmLCInsuredSchema.setSalary(request.getParameter("Salary"));
        tmLCInsuredSchema.setBankCode(request.getParameter("BankCode"));
        tmLCInsuredSchema.setBankAccNo(request.getParameter("BankAccNo"));
        tmLCInsuredSchema.setAccName(request.getParameter("AccName"));
        tmLCInsuredSchema.setLicenseType(request.getParameter("LicenseType"));
        tmLCInsuredSchema.setSex(request.getParameter("Sex"));
        tmLCInsuredSchema.setBirthday(request.getParameter("Birthday"));
        //个人信息
        tLDPersonSchema.setCustomerNo(request.getParameter("InsuredNo"));
        loggerDebug("CardContSave","%%%%%%%%%%%%%%%%%客户号码：" + request.getParameter("InsuredNo"));

        tLDPersonSchema.setName(request.getParameter("Name"));
        tLDPersonSchema.setSex(request.getParameter("Sex"));
        tLDPersonSchema.setBirthday(request.getParameter("Birthday"));
        tLDPersonSchema.setIDType(request.getParameter("IDType"));
        tLDPersonSchema.setIDNo(request.getParameter("IDNo"));
        tLDPersonSchema.setPassword(request.getParameter("Password"));
        tLDPersonSchema.setNativePlace(request.getParameter("NativePlace"));
        tLDPersonSchema.setNationality(request.getParameter("Nationality"));
        tLDPersonSchema.setRgtAddress(request.getParameter("RgtAddress"));
        tLDPersonSchema.setMarriage(request.getParameter("Marriage"));
        tLDPersonSchema.setMarriageDate(request.getParameter("MarriageDate"));
        tLDPersonSchema.setHealth(request.getParameter("Health"));
        tLDPersonSchema.setStature(request.getParameter("Stature"));
        tLDPersonSchema.setAvoirdupois(request.getParameter("Avoirdupois"));
        tLDPersonSchema.setDegree(request.getParameter("Degree"));
        tLDPersonSchema.setCreditGrade(request.getParameter("CreditGrade"));
        tLDPersonSchema.setOthIDType(request.getParameter("OthIDType"));
        tLDPersonSchema.setOthIDNo(request.getParameter("OthIDNo"));
        tLDPersonSchema.setICNo(request.getParameter("ICNo"));
        tLDPersonSchema.setGrpNo(request.getParameter("GrpNo"));
        tLDPersonSchema.setJoinCompanyDate(request.getParameter("JoinCompanyDate"));
        tLDPersonSchema.setStartWorkDate(request.getParameter("StartWorkDate"));
        tLDPersonSchema.setPosition(request.getParameter("Position"));
        tLDPersonSchema.setSalary(request.getParameter("Salary"));
        tLDPersonSchema.setOccupationType(request.getParameter("OccupationType"));
        tLDPersonSchema.setOccupationCode(request.getParameter("OccupationCode"));
        tLDPersonSchema.setWorkType(request.getParameter("WorkType"));
        tLDPersonSchema.setPluralityType(request.getParameter("PluralityType"));
        tLDPersonSchema.setDeathDate(request.getParameter("DeathDate"));
        tLDPersonSchema.setSmokeFlag(request.getParameter("SmokeFlag"));
        tLDPersonSchema.setBlacklistFlag(request.getParameter("BlacklistFlag"));
        tLDPersonSchema.setProterty(request.getParameter("Proterty"));
        tLDPersonSchema.setRemark(request.getParameter("Remark"));
        tLDPersonSchema.setState(request.getParameter("State"));
        tLDPersonSchema.setLicenseType(request.getParameter("LicenseType"));

        // 受益人信息
        //   LCBnfSet tLCBnfSet = new LCBnfSet();

        String tBnfNum[] = request.getParameterValues("BnfGridNo");
        String tBnfType[] = request.getParameterValues("BnfGrid9");
        String tName[] = request.getParameterValues("BnfGrid2");
        String tIDType[] = request.getParameterValues("BnfGrid4");
        String tIDNo[] = request.getParameterValues("BnfGrid5");
        String tBnfRelationToInsured[] = request.getParameterValues("BnfGrid3");

        String tBnfGrade[] = request.getParameterValues("BnfGrid6");
        String tBnfLot[] = request.getParameterValues("BnfGrid7");
        String tAddress[] = request.getParameterValues("BnfGrid8");
        String tInsurno[] = request.getParameterValues("BnfGrid10"); //add by yaory
        int BnfCount = 0;
        if (tBnfNum != null) BnfCount = tBnfNum.length;
        for (int i = 0; i < BnfCount; i++) {
            loggerDebug("CardContSave","BnfCount:" + BnfCount);
            loggerDebug("CardContSave","tName[i]:" + tName[i]);
            if (tName[i] == null || tName[i].equals("")) break;

            LCBnfSchema tLCBnfSchema = new LCBnfSchema();
            ////////////////////////////////yaory
            loggerDebug("CardContSave","被保人号码=====" + tInsurno[i]);
            //如果险种是00150000并且所属被保人是第二被保人，则受益人必须是第一被保人
            //write by yaory
            ExeSQL yeSql = new ExeSQL();
            SSRS zSSRS = new SSRS();
            String nsql = "select sequenceno from lcinsured where contno='" + request.getParameter("ContNo") + "' and insuredno='" + tInsurno[i] + "' and sequenceno='2'";
            zSSRS = yeSql.execSQL(nsql);
            loggerDebug("CardContSave","查询被保人信息=====" + nsql);
            if (zSSRS.MaxRow > 0) {
                nsql = "select * from lcinsured where contno='" + request.getParameter("ContNo") + "' and sequenceno='1'";
                zSSRS = yeSql.execSQL(nsql);
                loggerDebug("CardContSave","查询被保人信息2=====" + nsql);
                tLCBnfSchema.setBnfType("1");
                tLCBnfSchema.setName(zSSRS.GetText(1, 13));

                tLCBnfSchema.setIDType(zSSRS.GetText(1, 16));
                tLCBnfSchema.setIDNo(zSSRS.GetText(1, 17));

                tLCBnfSchema.setRelationToInsured("00");
                tLCBnfSchema.setBnfLot(tBnfLot[i]);
                tLCBnfSchema.setBnfGrade(tBnfGrade[i]);
                tLCBnfSchema.setInsuredNo(tInsurno[i]); //add by yaory
            } else {

                /////////////////////////////////////end write
                tLCBnfSchema.setBnfType("1");
                tLCBnfSchema.setName(tName[i]);
                //tLCBnfSchema.setSex(tSex[i]);
                tLCBnfSchema.setIDType(tIDType[i]);
                tLCBnfSchema.setIDNo(tIDNo[i]);
                //tLCBnfSchema.setBirthday(tBirthday[i]);
                tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
                tLCBnfSchema.setBnfLot(tBnfLot[i]);
                tLCBnfSchema.setBnfGrade(tBnfGrade[i]);
                tLCBnfSchema.setInsuredNo(tInsurno[i]); //add by yaory

                //tLCBnfSchema.setAddress(tAddress[i]);
                //tLCBnfSchema.setZipCode(tZipCode[i]);
                //tLCBnfSchema.setPhone(tPhone[i]);
                tLCBnfSet.add(tLCBnfSchema);
            }
        }

        loggerDebug("CardContSave","end set schema 受益人信息...");

        //地址信息被保人
        mLCAddressSchema.setCustomerNo(request.getParameter("InsuredNo"));
        if (request.getParameter("InsuredAddressNo") != null && !request.getParameter("InsuredAddressNo").equals("")) {
            loggerDebug("CardContSave","abc");
            mLCAddressSchema.setAddressNo(request.getParameter("InsuredAddressNo"));
        }
        mLCAddressSchema.setPostalAddress(request.getParameter("PostalAddress"));
        mLCAddressSchema.setZipCode(request.getParameter("ZIPCODE"));
        mLCAddressSchema.setPhone(request.getParameter("InsuredPhone"));
        mLCAddressSchema.setFax(request.getParameter("Fax"));
        mLCAddressSchema.setMobile(request.getParameter("Mobile"));
        mLCAddressSchema.setEMail(request.getParameter("EMail"));

        mLCAddressSchema.setHomeAddress(request.getParameter("HomeAddress"));
        mLCAddressSchema.setHomeZipCode(request.getParameter("HomeZipCode"));
        mLCAddressSchema.setHomePhone(request.getParameter("HomePhone"));
        mLCAddressSchema.setHomeFax(request.getParameter("HomeFax"));
        mLCAddressSchema.setCompanyPhone(request.getParameter("GrpPhone"));
        mLCAddressSchema.setCompanyAddress(request.getParameter("GrpAddress"));
        mLCAddressSchema.setCompanyZipCode(request.getParameter("GrpZipCode"));
        mLCAddressSchema.setCompanyFax(request.getParameter("GrpFax"));
        mLCAddressSchema.setGrpName(request.getParameter("GrpName"));
        mLCAddressSchema.setProvince(request.getParameter("InsuredProvince"));
        mLCAddressSchema.setCity(request.getParameter("InsuredCity"));
        mLCAddressSchema.setCounty(request.getParameter("InsuredDistrict"));

        ////////////////代理人提拥表////////////            //由于卡单不支持多个业务员故分配比例为1  HYQ

        LACommisionDetailSchema tLACommisionDetailSchema = new LACommisionDetailSchema();
        tLACommisionDetailSchema.setGrpContNo(request.getParameter("ProposalContNo"));
        tLACommisionDetailSchema.setAgentCode(request.getParameter("AgentCode"));
        tLACommisionDetailSchema.setBusiRate(1);
        tLACommisionDetailSchema.setAgentGroup(request.getParameter("AgentGroup"));
        tLACommisionDetailSchema.setPolType("0");

        tLACommisionDetailSet.add(tLACommisionDetailSchema);

        ////////////////险种信息////////////////////
        tLCPolSchema.setProposalNo(request.getParameter("ProposalContNo"));
        tLCPolSchema.setPrtNo(request.getParameter("ProposalContNo"));
        tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
        tLCPolSchema.setSaleChnl(tContType);
        tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
        tLCPolSchema.setAgentType(request.getParameter("AgentType"));
        tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
        tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
        tLCPolSchema.setHandler(request.getParameter("Handler"));
        tLCPolSchema.setAgentCode1(request.getParameter("AgentCode1"));
        tLCPolSchema.setInsuredAppAge(request.getParameter("InsuredAppAge")); //被保人投保年龄
        tLCPolSchema.setInsuredPeoples("1"); //被保人人数
        tLCPolSchema.setPolTypeFlag(request.getParameter("PolTypeFlag")); //保单类型标记
        tLCPolSchema.setContNo(request.getParameter("ProposalContNo"));
        tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
        tLCPolSchema.setFirstPayDate(request.getParameter("FirstPayDate"));
        tLCPolSchema.setAgentPayFlag(request.getParameter("AgentPayFlag"));
        tLCPolSchema.setAgentGetFlag(request.getParameter("AgentGetFlag"));
        tLCPolSchema.setRemark(request.getParameter("Remark"));
        tLCPolSchema.setMasterPolNo(request.getParameter("MasterPolNo"));
        tLCPolSchema.setPolApplyDate(request.getParameter("PolAppntDate"));     //投保日期
        loggerDebug("CardContSave","PolApplyDate" + request.getParameter("PolAppntDate"));
        if (request.getParameter("PolAppntDate") == null || request.getParameter("PolAppntDate").equals("")) {
            tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));
        } else {
            tLCPolSchema.setPolApplyDate(request.getParameter("PolAppntDate"));
        }
        tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
        tLCPolSchema.setRiskVersion(request.getParameter("RiskVersion"));
        tLCPolSchema.setCValiDate(request.getParameter("PolAppntDate"));             //保单生效日期
        tLCPolSchema.setHealthCheckFlag(request.getParameter("HealthCheckFlag")); //是否体检件
        tLCPolSchema.setPayLocation(request.getParameter("PayLocation"));         //收费方式
        tLCPolSchema.setLiveGetMode(request.getParameter("LiveGetMode"));         //生存保险金领取方式
        tLCPolSchema.setMult(request.getParameter("Mult"));                       //份数
        tLCPolSchema.setPrem(request.getParameter("Prem"));                       //保费
        tLCPolSchema.setAmnt(request.getParameter("Amnt"));                       //保额
        if (request.getParameter("RnewFlag") == null || request.getParameter("RnewFlag").equals("")) {
            //如果界面没有续保选项，则默认是自动续保-后台判断：如果该险种可以续保，则不变；如不能续保，则改为-2不续保
            //因为该字段类型为int，所以不传，后台默认是0-人工续保，有冲突，故作修改
            tLCPolSchema.setRnewFlag("-1");

        } else {
            tLCPolSchema.setRnewFlag(request.getParameter("RnewFlag"));               //
        }
        tLCPolSchema.setSpecifyValiDate("N");
        tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));             //
        tLCPolSchema.setBonusMan(request.getParameter("BonusMan"));               //红利领取人
        tLCPolSchema.setBonusGetMode(request.getParameter("BonusGetMode"));       //红利领取方式
        tLCPolSchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));       //内部分类
        tLCPolSchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
        tLCPolSchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));       //打印标志printflag
        tLCPolSchema.setManageFeeRate(request.getParameter("ManageFeeRate"));       //管理费比例
        tLCPolSchema.setKeepValueOpt(request.getParameter("KeepValueOpt"));

        /////////////责任信息//////////////////////
        //以下页面信息未整理
        tLCDutySchema.setPayIntv(request.getParameter("PayIntv"));                //交费方式
        tLCDutySchema.setInsuYear(request.getParameter("InsuYear"));              //保险期间
        tLCDutySchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
        tLCDutySchema.setPayEndYear(request.getParameter("PayEndYear"));          //交费年期
        tLCDutySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
        tLCDutySchema.setGetYear(request.getParameter("GetYear"));                //年金开始领取年龄
        tLCDutySchema.setGetYearFlag(request.getParameter("GetYearFlag"));
        tLCDutySchema.setGetStartType(request.getParameter("GetStartType"));
        tLCDutySchema.setCalRule(request.getParameter("CalRule"));
        tLCDutySchema.setFloatRate(request.getParameter("FloatRate"));
        tLCDutySchema.setPremToAmnt(request.getParameter("PremToAmnt"));
        tLCDutySchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));
        tLCDutySchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
        tLCDutySchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));
        tLCDutySchema.setPrem(request.getParameter("Prem"));
        tLCDutySchema.setAmnt(request.getParameter("Amnt"));
        tLCDutySchema.setMult(request.getParameter("Mult"));
        tLCDutySchema.setGetLimit(request.getParameter("GetLimit"));
        tLCDutySchema.setGetRate(request.getParameter("GetRate"));
        tLCDutySchema.setSSFlag(request.getParameter("SSFlag"));
        tLCDutySchema.setPeakLine(request.getParameter("PeakLine"));
        
        //此处用于存险种其他信息       
        String tLCPolOtherNum[] = request.getParameterValues("PolOtherGridNo");
        String tLCPolOtherDPN[] = request.getParameterValues("PolOtherGrid2"); //责任代码<>
        String tLCPolOtherSPN[] = request.getParameterValues("PolOtherGrid4"); //源参数代码<>
        String tLCPolOtherPN[] = request.getParameterValues("PolOtherGrid5"); //参数代码<存储>
        String tLCPolOtherPV[] = request.getParameterValues("PolOtherGrid6"); //参数值
        int tLCPolOtherCount=0;
        if (tLCPolOtherNum != null) tLCPolOtherCount = tLCPolOtherNum.length;
        loggerDebug("CardContSave","tLCPolOtherCount==="+tLCPolOtherCount);
        for (int i = 0; i < tLCPolOtherCount; i++) 
        {
        	loggerDebug("CardContSave",tLCPolOtherPN[i]+"==="+tLCPolOtherPV[i]);
			    tLCPolOtherSchema.setV(tLCPolOtherPN[i],tLCPolOtherPV[i]);
			    tLCPolOtherSchema.setDutyCode("000000");
			    tLCPolOtherSchema.setDutyCode(tLCPolOtherDPN[i]);
					tLCPolOtherSchema.setContNo(request.getParameter("ProposalContNo"));
					tLCPolOtherSchema.setGrpContNo(request.getParameter("GrpContNo"));
					tLCPolOtherSet.add(tLCPolOtherSchema);
        }
        
    }  // end of else
    // 准备传输数据 VData
    loggerDebug("CardContSave","kaishitijiao");
    tVData.add(tLCContSchema);
    tVData.add(tLCAddressSchema);
    tVData.add(tLCAppntSchema);
    tVData.add(tLCAccountSchema);
    tVData.add(tLCCustomerImpartSet);
    tVData.add(tLACommisionDetailSet);
    tVData.addElement(tLCPolSchema);
    tVData.addElement(tLCPolOtherSet);
    tVData.addElement(tLCGrpAppntSchema);
    tVData.addElement(mLCInsuredRelatedSet);
    tVData.addElement(tLCBnfSet);
    tVData.addElement(tLCDutySchema);
    tVData.addElement(tLCSpecSet);
    tVData.add(tG);
    //被保人
    tVData.add(tLDPersonSchema);
    tVData.add(tLCCustomerImpartDetailSet);
    tVData.add(tmLCInsuredSchema);
    tVData.add(mLCAddressSchema);
    tVData.add(tOLDLCInsuredDB);

    //传递非SCHEMA信息
    TransferData tTransferData = new TransferData();
    String SavePolType = "";
    SavePolType = "0";

    tTransferData.setNameAndValue("SavePolType", SavePolType); //保全保存标记，默认为0，标识非保全
    tTransferData.setNameAndValue("GrpNo", request.getParameter("AppntGrpNo"));
    tTransferData.setNameAndValue("GrpName", request.getParameter("AppntGrpName"));
    tTransferData.setNameAndValue("mark", "card");

    tTransferData.setNameAndValue("ContNo", request.getParameter("ProposalContNo"));
    tTransferData.setNameAndValue("FamilyType", "0"); //家庭单标记
    tTransferData.setNameAndValue("ContType", tContType); //团单，个人单标记
    tTransferData.setNameAndValue("PolTypeFlag", request.getParameter("PolTypeFlag")); //无名单标记
    tTransferData.setNameAndValue("InsuredPeoples", "1"); //被保险人人数
    tTransferData.setNameAndValue("InsuredAppAge", request.getParameter("InsuredAppAge")); //被保险人年龄
    tTransferData.setNameAndValue("SequenceNo", request.getParameter("SequenceNo")); //内部客户号

    tTransferData.setNameAndValue("getIntv", request.getParameter("getIntv"));                //领取间隔（方式）
    tTransferData.setNameAndValue("GetDutyKind", request.getParameter("GetDutyKind"));
    if (request.getParameter("SamePersonFlag") == null)
        tTransferData.setNameAndValue("samePersonFlag", "0"); //投保人同被保人标志
    else
        tTransferData.setNameAndValue("samePersonFlag", "1"); //投保人同被保人标志

    tTransferData.setNameAndValue("deleteAccNo", "1");
    tTransferData.setNameAndValue("ChangePlanFlag", "1");
    tTransferData.setNameAndValue("ISPlan", request.getParameter("ISPlan")); //套餐险种

    tVData.addElement(tTransferData);

    if (flag.equals("DELETE")) {
        flag = "DELETE||CONT";
    } else {
        flag = "INSERT||CONT";
    }
    String busiName="tbCardProposalBL";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    //CardProposalBL tCardProposalBL = new CardProposalBL();
    loggerDebug("CardContSave","before submit");
    if (tBusinessDelegate.submitData(tVData, flag,busiName) == false) {
        Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
        FlagStr = "Fail";

    } else {
        Content = " 操作成功! ";
        FlagStr = "Succ";
        loggerDebug("CardContSave","操作成功。。。。。。。。。。。");
    }
%>
<html>
<script language="javascript">
    parent.fraInterface.afterSubmit4("<%=FlagStr%>", "<%=Content%>");
</script>
</html>
 



