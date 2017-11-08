<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：NSProposalSave.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//only use for bq
%>
<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.service.*" %>
<%


  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String tAction = "";
  String tOldAction = "";
  String tOperate = "";
  boolean ChangePlanFlag = false;
  String approveFlag = "";
  String UWFlag = "";
  String approveCode = "";
  String approveDate = "";
  String tLoadFlag = "";
  String mLoadFlag = "";
  String remark="";
  tAction = request.getParameter( "fmAction" );
  if( tAction.equals( "INSERTPERSON" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "INSERTGROUP" )) tOperate = "INSERT||PROPOSAL";
  if( tAction.equals( "UPDATEPERSON" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "UPDATEGROUP" )) tOperate = "UPDATE||PROPOSAL";
  if( tAction.equals( "DELETE" )) tOperate = "DELETE||PROPOSAL";
  //loggerDebug("NSProposalSave","asdasdsd");
  //增加判断，如果没有录入主险不能录入附加险 write by yaory
    String triskcode=request.getParameter("RiskCode");
    loggerDebug("NSProposalSave","保单号-合同号-Session====="+request.getParameter("ContNo")+"-"+request.getParameter("RiskCode")+"-"+(String)session.getValue("MainRiskNo"));
    tLoadFlag = request.getParameter( "LoadFlag" );
    loggerDebug("NSProposalSave","===============LoadFlag:");
    if(!tLoadFlag.equals("8")){
       
       if(tOperate.equals("INSERT||PROPOSAL") && !triskcode.equals((String)session.getValue("MainRiskNo")))
       {
          loggerDebug("NSProposalSave","进入判断");
          ExeSQL xeSql = new ExeSQL();
          SSRS gSSRS = new SSRS();
          gSSRS = xeSql.execSQL("select * from lcpol where riskcode='"+(String)session.getValue("MainRiskNo")+"' and contno='"+request.getParameter("ContNo")+"'");
          if(gSSRS.MaxRow<1){
          remark="1";
          %>
             <script language="javascript">
             alert("请您先添加主险！");
             </script>
          <%
          }
       }
    }
    //同一个险种不能录入多次 verify by yaory
    if(tOperate.equals("INSERT||PROPOSAL"))
    {
    loggerDebug("NSProposalSave","进入判断");
      ExeSQL xeSql = new ExeSQL();
      SSRS gSSRS = new SSRS();
      gSSRS = xeSql.execSQL("select * from lcpol where riskcode='"+request.getParameter("RiskCode")+"' and contno='"+request.getParameter("ContNo")+"' and mainpolno='"+(String)session.getValue("MainRiskPolNo")+"'");
      if(gSSRS.MaxRow>0){
        remark="1";
        %>
        <script language="javascript">
        alert("您已经添加过这个险种！");
        </script>
        <%
        }
    }
    if(!remark.equals("1")){
      //end 判断
      LCPolSchema mLCPolSchema = new LCPolSchema();
      LCDutyBLSet mLCDutyBLSet=new LCDutyBLSet();
      try  {
        //ProposalUI tProposalUI  = new ProposalUI();
        String busiName2="tbProposalUI";
        BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
        GlobalInput tG = new GlobalInput();
        tG = ( GlobalInput )session.getValue( "GI" );

        VData tVData = new VData();

        tOldAction = tAction;
        loggerDebug("NSProposalSave","用户选择的操作为tAction:"+tAction);

        if( tAction.equals( "DELETE" ))  //选择删除保单操作的处理
        {
          LCPolSchema tLCPolSchema  = new LCPolSchema();
          tLCPolSchema.setProposalNo( request.getParameter( "ProposalNo" ));
          // 准备传输数据 VData
          tVData.addElement( tLCPolSchema );
          tVData.addElement( tG );
        }
        else
        {
          if (tAction.indexOf("ChangePlan") != -1) //在承保计划变更处保存保单时，获取投保单状态
          {
            ChangePlanFlag = true;
            loggerDebug("NSProposalSave","111 tAction:"+tAction);
            loggerDebug("NSProposalSave","111 tAction:这里可能出错，截取错误");
            if(tOldAction.indexOf("Modify") != 1)
            {
              if(tAction.equals("ChangePlanUPDATEGROUP")) //--添加判断
              {
                tAction = tAction.substring(10);
              }
              else
              { tAction = tAction.substring(16);}
            }
            else
            tAction = tAction.substring(10);
            loggerDebug("NSProposalSave","222 tAction:"+tAction);

            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
            VData VData2 = new VData();
            VData2.add(tLCPolSchema);
            //ChangePlanUI tChangePlanUI = new ChangePlanUI();
            String busiName="cbcheckChangePlanUI";
            BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
            //向后台传输数据
            if (!tBusinessDelegate.submitData(VData2, "QUERY||CHANGEPLAN",busiName))
            {  //操作失败时
            Content = "操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
            FlagStr = "Fail";
            throw new Exception();
          }
          else
          { //操作成功时
          VData vResult = tBusinessDelegate.getResult();
          approveFlag = (String)vResult.get(0);
          UWFlag = (String)vResult.get(1);
          approveCode = (String)vResult.get(2);
          approveDate = (String)vResult.get(3);

          loggerDebug("NSProposalSave","approveFlag:" + approveFlag + " UWFlag:" + UWFlag);
        }
      }

      //锁印刷号
      loggerDebug("NSProposalSave","tOldAction.indexOf_Modify:"+tOldAction.indexOf("Modify"));
      loggerDebug("NSProposalSave","GrpContNo:"+request.getParameter("GrpContNo"));
      if (request.getParameter("GrpContNo").equals("00000000000000000000") && tOldAction.indexOf("Modify") == -1)
      {
        LDSysTraceSchema tLDSysTraceSchema = new LDSysTraceSchema();
        tLDSysTraceSchema.setPolNo(request.getParameter("PrtNo"));
        tLDSysTraceSchema.setCreatePos("承保录单");
        tLDSysTraceSchema.setPolState("1002");
        LDSysTraceSet inLDSysTraceSet = new LDSysTraceSet();
        inLDSysTraceSet.add(tLDSysTraceSchema);
        VData VData3 = new VData();
        VData3.add(tG);
        VData3.add(inLDSysTraceSet);

        //LockTableUI LockTableUI1 = new LockTableUI();
        String busiName="cbcheckChangePlanUI";
        BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
        if (!tBusinessDelegate.submitData(VData3, "INSERT",busiName))
        {
          VData rVData = tBusinessDelegate.getResult();
          loggerDebug("NSProposalSave","LockTable Failed! " + (String)rVData.get(0));
        }
        else
        {
          loggerDebug("NSProposalSave","LockTable Succed!");
        }
      }

      if (tAction.indexOf("Modify") != -1)
      tAction = tAction.substring(6);
      loggerDebug("NSProposalSave","tAction" + tAction);
      LCContSchema tLCContSchema = new LCContSchema();
      tLCContSchema.setContNo(request.getParameter("ContNo"));
      loggerDebug("NSProposalSave","ContNo"+request.getParameter("ContNo"));
      // 保单信息部分
      LCPolSchema tLCPolSchema = new LCPolSchema();
      LCDutySchema tLCDutySchema = new LCDutySchema();

      tLCPolSchema.setProposalNo(request.getParameter("ProposalNo"));
      tLCPolSchema.setPrtNo(request.getParameter("PrtNo"));
      tLCPolSchema.setManageCom(request.getParameter("ManageCom"));
      //tLCPolSchema.setManageCom(tG.ManageCom);
      tLCPolSchema.setSaleChnl(request.getParameter("SaleChnl"));
      tLCPolSchema.setAgentCom(request.getParameter("AgentCom"));
      tLCPolSchema.setAgentType(request.getParameter("AgentType"));
      tLCPolSchema.setAgentCode(request.getParameter("AgentCode"));
      tLCPolSchema.setAgentGroup(request.getParameter("AgentGroup"));
      tLCPolSchema.setHandler(request.getParameter("Handler"));
      tLCPolSchema.setAgentCode1(request.getParameter("AgentCode1"));
      tLCPolSchema.setInsuredAppAge(request.getParameter("InsuredAppAge")); //被保人投保年龄
      tLCPolSchema.setInsuredPeoples(request.getParameter("InsuredPeoples")); //被保人人数
      tLCPolSchema.setPolTypeFlag(request.getParameter("PolTypeFlag")); //保单类型标记
      tLCPolSchema.setContNo(request.getParameter("ContNo"));
      //tLCPolSchema.setGrpPolNo(request.getParameter("GrpPolNo"));
      tLCPolSchema.setGrpContNo(request.getParameter("GrpContNo"));
      //借tLCPolSchema把判断的标志传到后台-在添加附加险之前是否添加了主险
      //tLCPolSchema.setKeepValueOpt(remark); //保全不用这个界面
      //tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      //add verify 如果主险没有添加，不能直接增加附加险 write by yaory
      //loggerDebug("NSProposalSave","标志====="+request.getParameter("tLoadFlag"));
      //edit by yaory-2005-7-18//如果是附加险则给SESSION
      ExeSQL texeSql = new ExeSQL();
      SSRS FlagSSRS = new SSRS();
      //loggerDebug("NSProposalSave","主保单号====="+request.getParameter("RiskCode"));
      FlagSSRS = texeSql.execSQL("select subriskflag from lmriskapp where riskcode='"+request.getParameter("RiskCode")+"'");
      if(FlagSSRS.GetText(1,1).equals("S"))
      {
        tLCPolSchema.setMainPolNo((String)session.getValue("MainRiskPolNo"));
      }else{
        tLCPolSchema.setMainPolNo(request.getParameter("MainPolNo"));
      }
      //loggerDebug("NSProposalSave","宝宝测试===="+(String)session.getValue("MainRiskPolNo"));
      tLCPolSchema.setFirstPayDate(request.getParameter("FirstPayDate"));
      //tLCPolSchema.setLang(request.getParameter("Lang"));
      //tLCPolSchema.setCurrency(request.getParameter("Currency"));
      //tLCPolSchema.setDisputedFlag(request.getParameter("DisputedFlag"));
      tLCPolSchema.setAgentPayFlag(request.getParameter("AgentPayFlag"));
      tLCPolSchema.setAgentGetFlag(request.getParameter("AgentGetFlag"));
      tLCPolSchema.setRemark(request.getParameter("Remark"));
      //add by LiuYansong添加客户内部号码
      //if(!(request.getParameter("SequenceNo")==null||request.getParameter("SequenceNo").equals("")))
      //{
        //  tLCPolSchema.setSequenceNo(request.getParameter("SequenceNo"));
        loggerDebug("NSProposalSave","add by LiuYansong 2004-11-02");
        //}


        // add for GrpAddName
        tLCPolSchema.setMasterPolNo(request.getParameter("MasterPolNo"));
        loggerDebug("NSProposalSave","MasterPolNo:" + request.getParameter("MasterPolNo"));

        loggerDebug("NSProposalSave","设置保单基本信息...");

        // 险种信息部分

        if(request.getParameter("PolApplyDate")==null||request.getParameter("PolApplyDate").equals(""))
        {
          tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));
        }
        else
        {
          tLCPolSchema.setPolApplyDate(request.getParameter("PolApplyDate"));
        }
        tLCPolSchema.setRiskCode(request.getParameter("RiskCode"));
        tLCPolSchema.setRiskVersion(request.getParameter("RiskVersion"));
        tLCPolSchema.setCValiDate(request.getParameter("CValiDate"));             //保单生效日期
        //tLCPolSchema.setPolApplyDate(request.getParameter("CValiDate"));          //投保单申请日期
        tLCPolSchema.setHealthCheckFlag(request.getParameter("HealthCheckFlag")); //是否体检件
        //tLCPolSchema.setOutPayFlag(request.getParameter("OutPayFlag"));           //溢交处理方式
        tLCPolSchema.setPayLocation(request.getParameter("PayLocation"));         //收费方式
        //tLCPolSchema.setBankCode(request.getParameter("BankCode"));               //开户行
        //tLCPolSchema.setBankAccNo(request.getParameter("BankAccNo"));             //银行帐号
        //tLCPolSchema.setAccName(request.getParameter("AccName"));                 //户名
        tLCPolSchema.setLiveGetMode(request.getParameter("LiveGetMode"));         //生存保险金领取方式
        loggerDebug("NSProposalSave","init lcol1 ") ;                                                                         //领取期限，通过TRANSFERDATA传递
        //领取方式，通过TRANSFERDATA传递

        //如果计算方向为份数算保额，则保存计算方向，用于团单打印时打印份数
        //String mPremToAmnt=request.getParameter("PremToAmnt");
        //if(mPremToAmnt!=null&&mPremToAmnt.equals("O")){
          //  tLCPolSchema.setPremToAmnt(mPremToAmnt);
          //}
          tLCPolSchema.setMult(request.getParameter("Mult"));                       //份数
          tLCPolSchema.setPrem(request.getParameter("Prem"));                       //保费
          tLCPolSchema.setAmnt(request.getParameter("Amnt"));                       //保额
          if(request.getParameter("RnewFlag")==null||request.getParameter("RnewFlag").equals(""))
          {
            //如果界面没有续保选项，则默认是自动续保-后台判断：如果该险种可以续保，则不变；如不能续保，则改为-2不续保
            //因为该字段类型为int，所以不传，后台默认是0-人工续保，有冲突，故作修改
            tLCPolSchema.setRnewFlag("-1");

          }
          else
          {
            tLCPolSchema.setRnewFlag(request.getParameter("RnewFlag"));               //
          }
          tLCPolSchema.setSpecifyValiDate(request.getParameter("SpecifyValiDate")); //是否指定生效日期
          tLCPolSchema.setFloatRate(request.getParameter("FloatRate"));             //
          //tLCPolSchema.setGetPolMode(request.getParameter("GetPolMode"));           //保单送达方式
          tLCPolSchema.setBonusMan(request.getParameter("BonusMan"));               //红利领取人
          tLCPolSchema.setBonusGetMode(request.getParameter("BonusGetMode"));       //红利领取方式
          tLCPolSchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));       //内部分类
          tLCPolSchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCPolSchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));       //打印标志printflag
          //tLCPolSchema.setStandbyFlag4(request.getParameter("StandbyFlag4"));       //银行保单号foreighpol


          //add by guoxiang at 2004-9-7 17:14 for  基业 的管理费比例存储
          tLCPolSchema.setManageFeeRate(request.getParameter("ManageFeeRate"));       //管理费比例
          //add end

          //add by HL @20050521 增加不丧失价值选择
          tLCPolSchema.setKeepValueOpt(request.getParameter("KeepValueOpt"));
          //end add




          loggerDebug("NSProposalSave","init LCDuty ");

          //以下页面信息未整理
          tLCDutySchema.setPayIntv(request.getParameter("PayIntv"));                //交费方式
          loggerDebug("NSProposalSave","payIntv:"+request.getParameter("PayIntv"));
          loggerDebug("NSProposalSave","1");
          tLCDutySchema.setInsuYear(request.getParameter("InsuYear"));              //保险期间
          loggerDebug("NSProposalSave","2");
          tLCDutySchema.setInsuYearFlag(request.getParameter("InsuYearFlag"));
          loggerDebug("NSProposalSave","3");
          tLCDutySchema.setPayEndYear(request.getParameter("PayEndYear"));          //交费年期
          loggerDebug("NSProposalSave","4");
          tLCDutySchema.setPayEndYearFlag(request.getParameter("PayEndYearFlag"));
          loggerDebug("NSProposalSave","5");
          tLCDutySchema.setGetYear(request.getParameter("GetYear"));                //年金开始领取年龄
          loggerDebug("NSProposalSave","6");
          tLCDutySchema.setGetYearFlag(request.getParameter("GetYearFlag"));
          loggerDebug("NSProposalSave","7");
          loggerDebug("NSProposalSave","getyear:"+request.getParameter("GetYear")+"GetYearFlag:"+request.getParameter("GetYearFlag"));
          tLCDutySchema.setGetStartType(request.getParameter("GetStartType"));
          tLCDutySchema.setCalRule(request.getParameter("CalRule"));
          tLCDutySchema.setFloatRate(request.getParameter("FloatRate"));
          loggerDebug("NSProposalSave","FloatRate:"+request.getParameter("FloatRate"));
          tLCDutySchema.setPremToAmnt(request.getParameter("PremToAmnt"));
          tLCDutySchema.setStandbyFlag1(request.getParameter("StandbyFlag1"));
          tLCDutySchema.setStandbyFlag2(request.getParameter("StandbyFlag2"));
          tLCDutySchema.setStandbyFlag3(request.getParameter("StandbyFlag3"));
          tLCDutySchema.setPrem(request.getParameter("Prem"));
          tLCDutySchema.setAmnt(request.getParameter("Amnt"));
          tLCDutySchema.setMult(request.getParameter("Mult"));
          tLCDutySchema.setGetLimit(request.getParameter("GetLimit"));
          loggerDebug("NSProposalSave","GetLimit:"+request.getParameter("GetLimit"));
          tLCDutySchema.setGetRate(request.getParameter("GetRate"));
          tLCDutySchema.setSSFlag(request.getParameter("SSFlag"));
          tLCDutySchema.setPeakLine(request.getParameter("PeakLine"));

          loggerDebug("NSProposalSave","设置保单中险种信息...");
          loggerDebug("NSProposalSave","init LCAppntInd ");

          // 投保人信息部分
          // 个人投保人
          LCAppntSchema tLCAppntSchema = new LCAppntSchema();
          tLCAppntSchema.setContNo(tLCContSchema.getContNo());
          tLCAppntSchema.setAppntNo(request.getParameter("AppntCustomerNo"));  //客户号
          tLCAppntSchema.setAppntName(request.getParameter("AppntName"));              //姓名
          tLCAppntSchema.setAppntSex(request.getParameter("AppntSex"));                //性别
          tLCAppntSchema.setAppntBirthday(request.getParameter("AppntBirthday"));    //出生日期

          // 主被保人
          LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
          //tLCInsuredSchema.setInsuredGrade("M");
          tLCInsuredSchema.setContNo(tLCContSchema.getContNo());
          tLCInsuredSchema.setInsuredNo(request.getParameter("CustomerNo"));  //客户号

          loggerDebug("NSProposalSave","end set schema 被保人信息...");

          // 受益人信息
          LCBnfSet tLCBnfSet = new LCBnfSet();

          String tBnfNum[]   = request.getParameterValues("BnfGridNo");
          String tBnfType[]  = request.getParameterValues("BnfGrid1");
          String tName[]     = request.getParameterValues("BnfGrid2");
          String tIDType[]   = request.getParameterValues("BnfGrid3");
          String tIDNo[]     = request.getParameterValues("BnfGrid4");
          String tBnfRelationToInsured[] = request.getParameterValues("BnfGrid5");

          String tBnfGrade[] = request.getParameterValues("BnfGrid6");
          String tBnfLot[]   = request.getParameterValues("BnfGrid7");
          String tAddress[]  = request.getParameterValues("BnfGrid8");

          int BnfCount = 0;
          if (tBnfNum != null) BnfCount = tBnfNum.length;
          for (int i = 0; i < BnfCount; i++)
          {
            if( tName[i] == null || tName[i].equals( "" )) break;

            LCBnfSchema tLCBnfSchema = new LCBnfSchema();

            tLCBnfSchema.setBnfType(tBnfType[i]);
            tLCBnfSchema.setName(tName[i]);
            //tLCBnfSchema.setSex(tSex[i]);
            tLCBnfSchema.setIDType(tIDType[i]);
            tLCBnfSchema.setIDNo(tIDNo[i]);
            //tLCBnfSchema.setBirthday(tBirthday[i]);
            tLCBnfSchema.setRelationToInsured(tBnfRelationToInsured[i]);
            tLCBnfSchema.setBnfLot(tBnfLot[i]);
            tLCBnfSchema.setBnfGrade(tBnfGrade[i]);
            //tLCBnfSchema.setAddress(tAddress[i]);
            //tLCBnfSchema.setZipCode(tZipCode[i]);
            //tLCBnfSchema.setPhone(tPhone[i]);
            tLCBnfSet.add(tLCBnfSchema);
          }

          loggerDebug("NSProposalSave","end set schema 受益人信息...");

          // 告知信息
          LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();

          String tImpartNum[] = request.getParameterValues("ImpartGridNo");
          String tImpartVer[] = request.getParameterValues("ImpartGrid1");            //告知版别
          String tImpartCode[] = request.getParameterValues("ImpartGrid2");           //告知编码
          String tImpartContent[] = request.getParameterValues("ImpartGrid4");        //填写内容
          String tImpartCustomerNoType[] = request.getParameterValues("ImpartGrid5"); //告知客户类型

          int ImpartCount = 0;
          if (tImpartNum != null) 
             ImpartCount = tImpartNum.length;

          for (int i = 0; i < ImpartCount; i++)  {
              if( !(tImpartCustomerNoType[i].trim().equals( "A" ) || tImpartCustomerNoType[i].trim().equals( "I" )))
                break;

              LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();

              if( tImpartCustomerNoType[i].trim().equals( "A" ))
                 tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "AppntCustomerNo" ));
              if( tImpartCustomerNoType[i].trim().equals( "I" ))
                 tLCCustomerImpartSchema.setCustomerNo( request.getParameter( "CustomerNo" ));
                 tLCCustomerImpartSchema.setCustomerNoType( tImpartCustomerNoType[i] );
                 tLCCustomerImpartSchema.setImpartCode(tImpartCode[i]);
                 tLCCustomerImpartSchema.setImpartContent(tImpartContent[i]);
                 tLCCustomerImpartSchema.setImpartVer(tImpartVer[i]) ;
                 tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
          }

          loggerDebug("NSProposalSave","end set schema 告知信息...");

          // 特别约定
          LCSpecSet tLCSpecSet = new LCSpecSet();

          String tSpecNum[] = request.getParameterValues("SpecGridNo");
          String tSpecType[] = request.getParameterValues("SpecGrid1");
          String tSpecCode[] = request.getParameterValues("SpecGrid2");
          String tSpecContent[] = request.getParameterValues("SpecGrid3");

          int SpecCount = 0;
          if (tSpecNum != null) SpecCount = tSpecNum.length;
          for (int i = 0; i < SpecCount; i++)  {
             if(tSpecCode[i]==null || tSpecCode[i].trim().equals("")) 
                 break;
             if(tSpecContent[i]==null || tSpecContent[i].trim().equals("")) 
                 break;

             LCSpecSchema tLCSpecSchema = new LCSpecSchema();
             tLCSpecSchema.setSpecCode(tSpecCode[i]);
             tLCSpecSchema.setSpecType(tSpecType[i]);
             tLCSpecSchema.setSpecContent(tSpecContent[i]);
             tLCSpecSet.add(tLCSpecSchema);
          }

          loggerDebug("NSProposalSave","end set schema 特约信息...");

          //是否有责任项
          String tNeedDutyGrid=request.getParameter("inpNeedDutyGrid");
          LCDutySet tLCDutySet=new LCDutySet();
          LCDutySchema tLCDutySchema1=new LCDutySchema();

          if (tNeedDutyGrid.equals("1")) {      //有责任项
          tLCDutySchema1 = new LCDutySchema();
          tLCDutySet=new LCDutySet();
          String tDutyChk[] = request.getParameterValues("InpDutyGridChk");

          String tDutyCode[] =request.getParameterValues("DutyGrid1");
          String tInsuYear[] =request.getParameterValues("DutyGrid3");
          String tInsuYearFlag[] =request.getParameterValues("DutyGrid4");
          String tPayEndYear[] =request.getParameterValues("DutyGrid5");
          String tPayEndYearFlag[] =request.getParameterValues("DutyGrid6");
          String tGetYearFlag[] =request.getParameterValues("DutyGrid7");
          String tGetYear[] =request.getParameterValues("DutyGrid8");
          String tStandbyFlag1[] =request.getParameterValues("DutyGrid9");
          String tStandbyFlag2[] =request.getParameterValues("DutyGrid10");
          String tStandbyFlag3[] =request.getParameterValues("DutyGrid11");
          String tPremToAmnt[] =request.getParameterValues("DutyGrid12");
          String tPrem[] =request.getParameterValues("DutyGrid13");
          String tAmnt[] =request.getParameterValues("DutyGrid14");
          String tMult[] =request.getParameterValues("DutyGrid15");
          String tCalRule[] =request.getParameterValues("DutyGrid16");
          String tFloatRate[] =request.getParameterValues("DutyGrid17");
          String tPayIntv[] =request.getParameterValues("DutyGrid18");
          String tGetLimit[] =request.getParameterValues("DutyGrid19");
          String tGetRate[] =request.getParameterValues("DutyGrid20");
          String tSSFlag[] =request.getParameterValues("DutyGrid21");
          String tPeakLine[] =request.getParameterValues("DutyGrid22");


          int DutyCount = 0;
          if (tDutyCode != null) DutyCount = tDutyCode.length;
          for (int i = 0; i < DutyCount; i++)  
          {
            if(tDutyCode[i]==null || tDutyCode[i].equals("")) break;

            tLCDutySchema1= new LCDutySchema();

            if(!tDutyCode[i].equals("") && tDutyChk[i].equals("1"))  
            {
              tLCDutySchema1.setDutyCode(tDutyCode[i]);
              tLCDutySchema1.setInsuYear(tInsuYear[i]);
              tLCDutySchema1.setInsuYearFlag(tInsuYearFlag[i]);
              tLCDutySchema1.setPayEndYear(tPayEndYear[i]);
              tLCDutySchema1.setPayEndYearFlag(tPayEndYearFlag[i]);
              tLCDutySchema1.setGetYearFlag(tGetYearFlag[i]);
              tLCDutySchema1.setGetYear(tGetYear[i]);
              tLCDutySchema1.setStandbyFlag1(tStandbyFlag1[i]);
              tLCDutySchema1.setStandbyFlag2(tStandbyFlag2[i]);
              tLCDutySchema1.setStandbyFlag3(tStandbyFlag3[i]);
              tLCDutySchema1.setPremToAmnt(tPremToAmnt[i]);
              tLCDutySchema1.setPrem(tPrem[i]);
              tLCDutySchema1.setAmnt(tAmnt[i]);
              tLCDutySchema1.setMult(tMult[i]);
              tLCDutySchema1.setCalRule(tCalRule[i]);
              tLCDutySchema1.setFloatRate(tFloatRate[i]);
              tLCDutySchema1.setPayIntv(tPayIntv[i]);
              tLCDutySchema1.setGetLimit(tGetLimit[i]);
              tLCDutySet.add(tLCDutySchema1);
            } 
          } 
        } 



        loggerDebug("NSProposalSave","end set schema 责任信息..."+tNeedDutyGrid);


        //是否有多个保费项
        String tNeedPremGrid="0";
        LCPremSet tLCPremSet=new LCPremSet();
        LCPremSchema tLCPremSchema1=new LCPremSchema();
        LCPremToAccSet tLCPremToAccSet = new LCPremToAccSet();
        LCPremToAccSchema tLCPremToAccSchema = new LCPremToAccSchema();

        try
        {  //捕获保费项错误
           tNeedPremGrid=request.getParameter("inpNeedPremGrid");

           loggerDebug("NSProposalSave"," tNeedPremGrid:"+tNeedPremGrid);
           if (tNeedPremGrid.equals("1")) 
           {       //有保费项
               tLCPremSchema1 = new LCPremSchema();
               tLCPremSet=new LCPremSet();
               //责任编码
               String tPremDutyCode[] =request.getParameterValues("PremGrid1");
               //缴费项编码
               String tPremPayCode[] =request.getParameterValues("PremGrid2");
               //交费项名称
               String tPremPayCodeName[] =request.getParameterValues("PremGrid3");
               //保费
               String tPremStandPrem[] =request.getParameterValues("PremGrid4");
               //分配比例
               String tPremRate[] =request.getParameterValues("PremGrid5");
               //关联帐户号
               String tPremAccNo[] =request.getParameterValues("PremGrid6");
               //帐户分配比率
               String tPremAccRate[] =request.getParameterValues("PremGrid7");

               //管理费比例 add by guoxiang 2004-9-7 10:34 暂时没用
               //String tManageFeeRate[]=request.getParameterValues("PremGrid8");
               String tPremChk[] = request.getParameterValues("InpPremGridChk");
 
               //交费占员工工资比例
               String tSalaryRate[] = request.getParameterValues("PremGrid9");

               double actuPrem=0;
               int PremCount = 0;
               if (tPremDutyCode != null) PremCount = tPremDutyCode.length;
               for (int i = 0; i < PremCount; i++)
               {
                  if(tPremDutyCode[i]==null || tPremDutyCode[i].equals("")) break;

                  tLCPremSchema1= new LCPremSchema();
                  tLCPremToAccSchema = new LCPremToAccSchema();

                  if(!tPremDutyCode[i].equals("") && tPremChk[i].equals("1"))
                  {
                     loggerDebug("NSProposalSave"," tPremDutyCode:"+tPremDutyCode[i]);
                     tLCPremSchema1.setDutyCode(tPremDutyCode[i]);
                     tLCPremSchema1.setPayPlanCode(tPremPayCode[i]);

                     tLCPremSchema1.setStandPrem(tPremStandPrem[i]);   //每个保费项上的保费在js页里算出
                     tLCPremSchema1.setPrem(tPremStandPrem[i]);
                     loggerDebug("NSProposalSave","--------------prem"+i+":"+tPremStandPrem[i]);
                     //tLCPremSchema1.setStandPrem(actuPrem);
                     //tLCPremSchema1.setPrem(actuPrem);
                     tLCPremSchema1.setRate(tPremRate[i]);

                     //add by guoxiang 2004-9-7 10:33 for 管理费比例 暂时没用
                     //tLCPremSchema1.setManageFeeRate(tManageFeeRate[i]);

                     tLCPremToAccSchema.setDutyCode( tPremDutyCode[i] );
                     tLCPremToAccSchema.setPayPlanCode( tPremPayCode[i] );
                     tLCPremToAccSchema.setInsuAccNo( tPremAccNo[i] );
                     tLCPremToAccSchema.setRate( tPremAccRate[i] );

                     tLCPremSet.add(tLCPremSchema1);
                     tLCPremToAccSet.add(tLCPremToAccSchema);

                  } // end of if
               } // end of for
            } // end of if

        } 
        catch(Exception ex)
        {
           loggerDebug("NSProposalSave","set schema 保费项信息 Error!");
        }

        loggerDebug("NSProposalSave","end set schema 保费项信息...");

        //传递非SCHEMA信息
        TransferData tTransferData = new TransferData();

        tTransferData.setNameAndValue("getIntv", request.getParameter("getIntv"));                //领取间隔（方式）
        tTransferData.setNameAndValue("GetDutyKind", request.getParameter("GetDutyKind"));        //年金开始领取年龄


        if (request.getParameter("SamePersonFlag") == null)
           tTransferData.setNameAndValue("samePersonFlag", "0"); //投保人同被保人标志
        else
           tTransferData.setNameAndValue("samePersonFlag", "1"); //投保人同被保人标志

        if (!ChangePlanFlag)
        {
           tTransferData.setNameAndValue("deleteAccNo", "1");
           tTransferData.setNameAndValue("ChangePlanFlag", "1");
        }
        else
        {
           tTransferData.setNameAndValue("deleteAccNo", "0");
           tTransferData.setNameAndValue("ChangePlanFlag", "0");
        }


        tTransferData.setNameAndValue("SavePolType", request.getParameter("BQFlag")); //保全保存标记
        tTransferData.setNameAndValue("EdorType", request.getParameter("EdorType"));
        loggerDebug("NSProposalSave","SavePolType : " + request.getParameter("BQFlag"));


        // 准备传输数据 VData
        tVData.addElement(tLCContSchema);
        tVData.addElement(tLCPolSchema);
        tVData.addElement(tLCAppntSchema);
       // tVData.addElement(tLCGrpAppntSchema);
        tVData.addElement(tLCInsuredSchema);
        LCInsuredRelatedSet mLCInsuredRelatedSet=new LCInsuredRelatedSet();
        //add by yaory write on 2005-7-6-17:43
        try{
           String tRadio[] = request.getParameterValues("InpInsuredGridSel");

           //loggerDebug("NSProposalSave","tRadio.length===="+tRadio.length);
           String tGrid1 [] = request.getParameterValues("InsuredGrid1"); //得到第1列的所有值
           String tGrid2 [] = request.getParameterValues("InsuredGrid2");
           String tGrid3 [] = request.getParameterValues("InsuredGrid3");
           String tGrid4 [] = request.getParameterValues("InsuredGrid4");
           String tGrid5 [] = request.getParameterValues("InsuredGrid5");
           LCInsuredRelatedSchema tLCInsuredRelatedSchema=new LCInsuredRelatedSchema();
           loggerDebug("NSProposalSave","总共的行数===="+tRadio.length);
           for(int i=0; i<tRadio.length;i++)
           {
              if(tRadio[i].equals("1"))
              {
                 loggerDebug("NSProposalSave","进入操作");
                 //说明-主被保险人客户号未做处理 
                 tLCInsuredRelatedSchema.setCustomerNo(tGrid1 [i]);          
                 tLCInsuredRelatedSchema.setName(tGrid2 [i]);          
                 tLCInsuredRelatedSchema.setSex(tGrid3 [i]);          
                 tLCInsuredRelatedSchema.setBirthday(tGrid4 [i]);          
                 tLCInsuredRelatedSchema.setRelationToInsured(tGrid5 [i]);
              tLCPolSchema.setStandbyFlag1(tGrid1 [i]);
              tLCDutySchema.setStandbyFlag1(tGrid1 [i]);
              tLCInsuredRelatedSchema.setOperator(request.getParameter("AgentCode"));
              tLCInsuredRelatedSchema.setMakeDate(request.getParameter("AgentCode"));
              tLCInsuredRelatedSchema.setMakeTime(request.getParameter("AgentCode"));
              //tLCInsuredRelatedSchema.setSequenceNo(request.getParameter("SequenceNo"));
              tLCInsuredRelatedSchema.setSequenceNo("01");
              if(tGrid5 [i].equals("00"))
              {            
                 tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
              }
              tLCInsuredRelatedSchema.setPolNo(request.getParameter("ProposalNo"));
           }
           if(tRadio[i].equals("0"))
        {
          //loggerDebug("NSProposalSave","该行未被选中");
          if(tGrid5 [i].equals("00"))
          {
            //loggerDebug("NSProposalSave","要的就是你"); //主被保人客户号
            tLCInsuredRelatedSchema.setMainCustomerNo(tGrid5 [i]);
          }
        }
      }
      for(int i=0; i<tRadio.length;i++)
      {
        if(tRadio[i].equals("1"))
        {
          mLCInsuredRelatedSet.add(tLCInsuredRelatedSchema);
        }
      }
    }catch(Exception ex){}
    ///end add by yaory 2005-7-6

    tVData.addElement(mLCInsuredRelatedSet);
    tVData.addElement(tLCBnfSet);
    tVData.addElement(tLCCustomerImpartSet);
    tVData.addElement(tLCSpecSet);
    tVData.addElement(tG);
    tVData.addElement(tTransferData);

    //如果是可选责任项
    if (("1").equals(tNeedDutyGrid))
    tVData.addElement(tLCDutySet);
    else
    tVData.addElement(tLCDutySchema);

    //如果是可选保费项
    if (tNeedPremGrid!=null&&tNeedPremGrid.equals("1"))
    {
      tVData.addElement(tLCPremSet);
      tVData.addElement(tLCPremToAccSet);
    }


  } // end of if




  // 数据传输

  if( !tBusinessDelegate2.submitData( tVData, tOperate,busiName2 ) ) {
    Content = " 保存失败，原因是: " + tBusinessDelegate2.getCErrors().getError(0).errorMessage;
    FlagStr = "Fail";
  }
  else {
    Content = "*************Proposal 保存成功!*************";
    FlagStr = "Succ";

    tVData.clear();
    tVData = tBusinessDelegate2.getResult();

    // 显示
    // 保单信息
    mLCPolSchema.setSchema(( LCPolSchema )tVData.getObjectByObjectName( "LCPolSchema", 0 ));
    mLCDutyBLSet.set((LCDutyBLSet)tVData.getObjectByObjectName( "LCDutyBLSet", 0 ));
    ///////////add by yaory -2005-7-13 如果是主险则---
    loggerDebug("NSProposalSave","操作是===="+tOperate);
    if(tOperate.equals("INSERT||PROPOSAL"))
    {
      ExeSQL rexeSql = new ExeSQL();
      SSRS riskFlagSSRS = new SSRS();
      riskFlagSSRS = rexeSql.execSQL("select subriskflag from lmriskapp where riskcode in (select riskcode from lcpol where polno='"+mLCPolSchema.getPolNo()+"')");
      if(riskFlagSSRS.GetText(1,1).equals("M"))
      {
        loggerDebug("NSProposalSave","主险==="+(String)session.getValue("MainRiskPolNo"));
        session.putValue("MainRiskPolNo",mLCPolSchema.getPolNo());
      }
    }else{
      loggerDebug("NSProposalSave","不是附加险就是其他操作===="+(String)session.getValue("MainRiskPolNo"));
      session.putValue("MainRiskPolNo",null);
    }
    /////////end add by yaory
  }
  //无名单补名单，直接签单
  try  {
    if (FlagStr.equals("Succ") && request.getParameter("BQFlag")!=null && request.getParameter("BQFlag").equals("4")) {
      loggerDebug("NSProposalSave","\nNewProposalNo:" + mLCPolSchema.getProposalNo());

      GrpAddNameSignBL tGrpAddNameSignBL = new GrpAddNameSignBL();
      if (!tGrpAddNameSignBL.dealSign(mLCPolSchema, tG)) {
        Content = "无名单补名单签单失败，原因是: " + tGrpAddNameSignBL.mErrors.getError(0).errorMessage;
        FlagStr = "Fail";
      }
      else {
        Content = "无名单补名单保存、核保、签单成功！";
      }
    } // end of if
  }
  catch ( Exception e2 ) {
    Content = "无名单补名单签单失败，原因是: " + e2.toString();
    FlagStr = "Fail";
  }

  //承保计划变更，恢复投保单状态
  if (ChangePlanFlag) {
    LCPolSchema tLCPolSchema = new LCPolSchema();
    tLCPolSchema.setPolNo(request.getParameter("ProposalNo"));
    tLCPolSchema.setApproveFlag(approveFlag);
    tLCPolSchema.setUWFlag(UWFlag);
    tLCPolSchema.setApproveCode(approveCode);
    tLCPolSchema.setApproveDate(approveDate);

    VData VData3 = new VData();
    VData3.add(tLCPolSchema);

    ChangePlanUI tChangePlanUI = new ChangePlanUI();
    if (!tChangePlanUI.submitData(VData3, "INSERT||CHANGEPLAN")) {
      Content = "操作失败，原因是: " + tChangePlanUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
      throw new Exception();
    }

  }

} // end of try
catch( Exception e1 )  {
  Content = " 保存失败，原因是: " + e1.toString().trim();
  FlagStr = "Fail";
}

if(FlagStr.equals("Succ")&&!tAction.equals( "DELETE" ))  {
  %>
  <script language="javascript">
    var dutyArray = new Array();
    </script>
    <%
    loggerDebug("NSProposalSave",mLCDutyBLSet.encode());
    for (int j=1;j<=mLCDutyBLSet.size();j++)
    {
      %>
      <script language="javascript">
        dutyArray[<%=j-1%>]=new Array();
        dutyArray[<%=j-1%>][0]='<%=mLCDutyBLSet.get(j).getDutyCode()%>';
        dutyArray[<%=j-1%>][1]='<%=mLCDutyBLSet.get(j).getPrem()%>';
        dutyArray[<%=j-1%>][2]='<%=mLCDutyBLSet.get(j).getAmnt()%>';
        </script>
        <%
        }
        %>
        <script language="javascript">
          try
          {

            if (parent.fraInterface.document.all('inpNeedDutyGrid').value==1)
            {
              var mulLineCount=parent.fraInterface.DutyGrid.mulLineCount;
              for (i=0;i<mulLineCount;i++)
              {
                var dutycode=parent.fraInterface.DutyGrid.getRowColData(i,1);
                for (j=0;j<dutyArray.length;j++)
                {
                  if (dutyArray[j][0]==dutycode)
                  {
                    parent.fraInterface.DutyGrid.setRowColData(i,13,dutyArray[j][1]);
                    parent.fraInterface.DutyGrid.setRowColData(i,14,dutyArray[j][2]);
                  }
                }

              }
            }
            else
            {

              parent.fraInterface.document.all("Prem").value = "<%=mLCPolSchema.getStandPrem()%>";

              parent.fraInterface.document.all("Amnt").value = "<%=mLCPolSchema.getAmnt()%>";

            }
            parent.fraInterface.document.all("ContNo").value = "<%=mLCPolSchema.getContNo()%>";
            //parent.fraInterface.document.all("GrpPolNo").value = "<%=mLCPolSchema.getGrpPolNo()%>";
            parent.fraInterface.document.all("ProposalNo").value = "<%=mLCPolSchema.getProposalNo()%>";


            parent.fraInterface.document.all("ContType").value = "<%=mLCPolSchema.getContType()%>";

            if(parent.VD.gVSwitch.addVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>")==false){
              parent.VD.gVSwitch.updateVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>");
            }

            //录入修改
            if (parent.fraInterface.LoadFlag == 2) {

              parent.fraInterface.inputButton.style.display = "none";
              parent.fraInterface.divButton.style.display = "";
              parent.fraInterface.modifyButton.style.display = "";
              //parent.fraInterface.riskbutton31.disabled='';
              //parent.fraInterface.riskbutton32.disabled='';


            }
          } catch(ex) {
            alert("after Save but happen err:" + ex);
          }
          </script>

          <%
          } // end of if

          loggerDebug("NSProposalSave",Content);

          //替换掉错误信息的特殊字符
          Content = Content.replace('\n', ' ');

          while (Content.indexOf("\"") != -1) {
            Content = Content.replace('\"', ' ');
            //loggerDebug("NSProposalSave",Content);
          }


          loggerDebug("NSProposalSave",Content);
          loggerDebug("NSProposalSave",FlagStr);
          %>

          <html>
            <script language="javascript">
              try
              {
                parent.fraInterface.afterSubmit('<%=FlagStr%>','<%=Content%>');
              }  catch(ex) {
                //alert("after Save but happen err:" + ex);
              }
              </script>
              <%
              if(( tAction.equals( "INSERTPERSON" ) || tAction.equals( "UPDATEPERSON" )) && FlagStr.equals( "Succ" ))
              {
                %>
                <script language="javascript">
                  try {
                    var prtNo = "<%=mLCPolSchema.getPrtNo()%>";
                    if (prtNo.substring(2, 4) == "11") {
                      parent.fraPic.goToPic(3); top.fraPic.scrollTo(0, 0);
                    }
                    else if (prtNo.substring(2, 4) == "15") {
                      parent.fraPic.goToPic(0); top.fraPic.scrollTo(300, 2000);
                    }
                    //parent.fraInterface.showFee();
                  }
                  catch(ex) {}
                  </script>
                  <%
                  } // end of if
                } //add by yaory for //增加判断，如果没有录入主险不能录入附加险 write by yaory
%>
</html>

