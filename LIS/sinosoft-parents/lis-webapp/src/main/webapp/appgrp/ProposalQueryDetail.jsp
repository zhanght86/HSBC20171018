<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String statusFlag = "";
  String customerNo = "";  //记录被保人客户号，用来与投保人客户号进行判断
  loggerDebug("ProposalQueryDetail","\n\n------ProposalQueryDetail start------");
  // 保单信息部分
  LCPolSchema tLCPolSchema   = new LCPolSchema();
  // 准备传输数据 VData
  tLCPolSchema.setPolNo(request.getParameter("PolNo"));
  tLCPolSchema.setContNo(request.getParameter("ContNo"));
  String tLoadFlag=(String)request.getParameter("LoadFlag");
  loggerDebug("ProposalQueryDetail","tLoadFlag"+tLoadFlag);
  VData tVData = new VData();
  tVData.addElement( tLCPolSchema );
  // 数据传输
  ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
  if (!tProposalQueryUI.submitData( tVData, "QUERY||DETAIL" )) {
      Content = "查询失败，原因是: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
    tVData.clear();
    tVData = tProposalQueryUI.getResult();
    // 保单信息
    LCPolSchema mLCPolSchema = new LCPolSchema();
    mLCPolSchema.setSchema((LCPolSchema)tVData.getObjectByObjectName("LCPolSchema",0));

//    String tContType = mLCPolSchema.getContType();
//    String tLoadFlag = "3";
//    if( tContType.equals( "2" ))   // 集体下的个人投保单
//       tLoadFlag = "4";
//add by yaory 表lcpol中字段mainpolno
loggerDebug("ProposalQueryDetail","mainpolno======"+mLCPolSchema.getMainPolNo());
 session.putValue("MainRiskPolNo",mLCPolSchema.getMainPolNo());
%>
<html>
<head>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="ProposalInput.js"></SCRIPT>
</head>
<body>
<script language="javascript">
try{parent.VD.gVSwitch.addVar("mainRiskPolNo","","<%=mLCPolSchema.getMainPolNo()%>");}catch(ex){};
//add by yaory
//parent.fraInterface.fm.hidepolno.value="<%=mLCPolSchema.getMainPolNo()%>";
//alert(parent.fraInterface.fm.MainPolNo.value);
//alert("mainriskpolno:"+parent.VD.gVSwitch.getVar("mainRiskPolNo"));

    //alert("mainriskpolno:"+parent.VD.gVSwitch.getVar("mainRiskPolNo"));
    try {
    //alert("parent.fraInterface.LoadFlag"+parent.fraInterface.LoadFlag);
      if (parent.fraInterface.LoadFlag == 5||parent.fraInterface.LoadFlag ==25) {
        parent.fraInterface.getRiskInput("<%=mLCPolSchema.getRiskCode()%>", "5");
      } else {
    //  alert("mLCPolSchema.getRiskCode()"+"<%=mLCPolSchema.getRiskCode()%>");
        parent.fraInterface.getRiskInput("<%=mLCPolSchema.getRiskCode()%>", parent.fraInterface.LoadFlag);

      }
    } catch(ex) {}
<%
    //集体下的个人投保单
    int elementsNum;
    Class c = mLCPolSchema.getClass();
    Field f[] = c.getDeclaredFields();

    for(elementsNum=0; elementsNum<f.length; elementsNum++) {

      if (!mLCPolSchema.getV(f[elementsNum].getName()).equals("null")) {

        out.println("try{");
        out.println("parent.fraInterface.fm.all('" + f[elementsNum].getName() + "').value='" + mLCPolSchema.getV(f[elementsNum].getName()) + "';");
        out.println("} catch(ex) {};");
      }
    }
    //设置投保单状态标记
    try {
      if (mLCPolSchema.getApproveFlag().equals("0")) statusFlag = "未进行复核";
      else if (mLCPolSchema.getApproveFlag().equals("1")) statusFlag = "复核未通过";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("0")) statusFlag = "复核通过，待核保";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("5")) statusFlag = "复核通过，自动核保未通过，待人工核保";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("9")) statusFlag = "复核通过，核保通过";
    }
    catch(Exception e) {
      loggerDebug("ProposalQueryDetail","statusFlag throw Error");
    }
    loggerDebug("ProposalQueryDetail","statusFlag:" + statusFlag);
    loggerDebug("ProposalQueryDetail","End Display 保单信息...");


   // 责任信息（未整理）
   LCDutySet mLCDutySet = new LCDutySet();
   mLCDutySet.set((LCDutySet)tVData.getObjectByObjectName("LCDutySet",0));
   LMDutySet mLMDutySet = new LMDutySet();
   mLMDutySet.set((LMDutySet)tVData.getObjectByObjectName("LMDutySet",0));
   int dutyCount = mLCDutySet.size();
   loggerDebug("ProposalQueryDetail","dutyCount:"+dutyCount);
   loggerDebug("ProposalQueryDetail","mainPolNo:"+mLCPolSchema.getMainPolNo());
   loggerDebug("ProposalQueryDetail","mLMDutySet:"+mLMDutySet.encode());
   if(dutyCount==1)
    {
         c = mLCDutySet.get(1).getClass();
         f = c.getDeclaredFields();
         for(elementsNum=0; elementsNum<f.length; elementsNum++)
         {

            if (!mLCDutySet.get(1).getV(f[elementsNum].getName()).equals("null"))
            {
              out.println("try{");
              out.println("parent.fraInterface.fm.all('" + f[elementsNum].getName() + "').value='" + mLCDutySet.get(1).getV(f[elementsNum].getName()) + "';");
              out.println("} catch(ex) {};");
            }
         }
    }

%>
</script>


<script language="javascript">
//parent.fraInterface.fm.all('ProposalNo').value='<%=mLCPolSchema.getProposalNo()%>';
//alert("parent.fraInterface.fm.all('ProposalNo')"+parent.fraInterface.fm.all('ProposalNo').value);
//alert("ProposalNo"+'<%=mLCPolSchema.getProposalNo()%>');
parent.fraInterface.initDutyGrid();
parent.fraInterface.DutyGrid.clearData();
</script>
<%
   for (int i = 1; i <= dutyCount; i++) {
         LCDutySchema mLCDutySchema = mLCDutySet.get(i);
         LMDutySchema mLMDutySchema = mLMDutySet.get(i);
%>
<script language="javascript">
					//如果需要责任

					//alert(parent.fraInterface.fm.all('inpNeedDutyGrid').value);
          if (parent.fraInterface.fm.all('inpNeedDutyGrid').value==1 )
          {
          	parent.fraInterface.DutyGrid.addOne();
          	parent.fraInterface.DutyGrid.checkBoxSel(<%=i%>);
          	parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,1,'<%=mLCDutySchema.getDutyCode()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,2,'<%=mLMDutySchema.getDutyName()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,3,'<%=mLCDutySchema.getInsuYear()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,4,'<%=StrTool.cTrim(mLCDutySchema.getInsuYearFlag())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,5,'<%=mLCDutySchema.getPayEndYear()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,6,'<%=StrTool.cTrim(mLCDutySchema.getPayEndYearFlag())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,7,'<%=StrTool.cTrim(mLCDutySchema.getGetYearFlag())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,8,'<%=mLCDutySchema.getGetYear()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,9,'<%=StrTool.cTrim(mLCDutySchema.getStandbyFlag1())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,10,'<%=StrTool.cTrim(mLCDutySchema.getStandbyFlag2())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,11,'<%=StrTool.cTrim(mLCDutySchema.getStandbyFlag3())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,12,'<%=StrTool.cTrim(mLCDutySchema.getPremToAmnt())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,13,'<%=mLCDutySchema.getPrem()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,14,'<%=mLCDutySchema.getAmnt()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,15,'<%=mLCDutySchema.getMult()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,16,'<%=StrTool.cTrim(mLCDutySchema.getCalRule())%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,17,'<%=mLCDutySchema.getFloatRate()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,18,'<%=mLCDutySchema.getPayIntv()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,19,'<%=mLCDutySchema.getGetLimit()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,20,'<%=mLCDutySchema.getGetRate()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,21,'<%=mLCDutySchema.getSSFlag()%>');
						parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,22,'<%=mLCDutySchema.getPeakLine()%>');
            parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,26,'<%=mLCDutySchema.getGetIntv()%>');
					  parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,27,'<%=mLCDutySchema.getAscriptionRuleCode()%>');
					  parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,28,'<%=mLCDutySchema.getPayRuleCode()%>');
					  parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,29,'<%=mLCDutySchema.getBonusGetMode()%>');
					  parent.fraInterface.DutyGrid.setRowColData(<%=i-1%>,31,'<%=mLCDutySchema.getGetStartType()%>');

				  }
</script>
<%
   }
   loggerDebug("ProposalQueryDetail","End Display 责任信息...");
%>

<%
      // 保费项信息
      LCPremSet mLCPremSet = new LCPremSet();
      mLCPremSet.set((LCPremSet)tVData.getObjectByObjectName("LCPremSet",0));

      LMDutyPaySet mLMDutyPaySet = new LMDutyPaySet();
      mLMDutyPaySet.set((LMDutyPaySet)tVData.getObjectByObjectName("LMDutyPaySet",0));
      int PayPlanCount = mLCPremSet.size();
      loggerDebug("ProposalQueryDetail","PayPlanCount:"+PayPlanCount);

%>
<script language="javascript">
      try
      {
         if(parent.fraInterface.initPremGrid()==true)
         {
           parent.fraInterface.PremGrid.clearData();
<%
    for (int i = 1; i <=PayPlanCount; i++)
    {
%>
               parent.fraInterface.PremGrid.addOne();
               parent.fraInterface.PremGrid.checkBoxSel(<%=i%>);
               parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 1, "<%=mLCPremSet.get(i).getDutyCode()%>");
               parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 2, "<%=mLCPremSet.get(i).getPayPlanCode()%>");
               parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 3, "<%=mLMDutyPaySet.get(i).getPayPlanName()%>");

               if(<%=mLCPremSet.get(i).getPrem()%>>0)
               {

                 //parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 4, "<%=mLCPremSet.get(i).getPrem()/mLCPremSet.get(i).getRate()%>");
                 //parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 5, "<%=mLCPremSet.get(i).getRate()%>");
                 parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 4, "<%=mLCPremSet.get(i).getPrem()%>");
                 parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 5, "<%=mLCPremSet.get(i).getRate()%>");
              }
              else
              {
                parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 4, "0");
                parent.fraInterface.PremGrid.setRowColData(<%=i-1%>, 5, "0");

              }
<%
      }
%>
           }
      }
      catch(ex){}

</script>
<%

   loggerDebug("ProposalQueryDetail","End Display 保费项信息...");
%>

<%
      // 个人投保人信息
      LCAppntSchema mLCAppntSchema = new LCAppntSchema();
      mLCAppntSchema.setSchema((LCAppntSchema)tVData.getObjectByObjectName("LCAppntSchema",0));

      // 集体投保人信息
      LCGrpAppntSchema mLCGrpAppntSchema = new LCGrpAppntSchema();
      mLCGrpAppntSchema.setSchema((LCGrpAppntSchema)tVData.getObjectByObjectName("LCGrpAppntSchema",0));

%>
<script language="javascript">


</script>

<%
    loggerDebug("ProposalQueryDetail","End Display 集体投保人信息...");
    // 被保人信息
    LCInsuredSet mLCInsuredSet = new LCInsuredSet();
    mLCInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
    int insuredCount = mLCInsuredSet.size();

%>
<script language="javascript">
</script>
<%
    loggerDebug("ProposalQueryDetail","End Display 被保人信息...");
%>

<%
     // 险种信息的给付方法
      LCGetSet mLCGetSet = new LCGetSet();
      mLCGetSet.set((LCGetSet)tVData.getObjectByObjectName("LCGetSet",0));
      int lcgetCount = mLCGetSet.size();

      for (int i = 1; i <= lcgetCount; i++)
      {
         LCGetSchema mLCGetSchema = mLCGetSet.get(i);
         loggerDebug("ProposalQueryDetail","mLCGetSchema:" + mLCGetSchema.encode() + "\n" + mLCGetSchema.getGetDutyKind());
         if (mLCGetSchema.getGetDutyKind()!=null && !mLCGetSchema.getGetDutyKind().equals(""))
         {
%>
<script language="javascript">
  try {
  	parent.fraInterface.fm.all("GetDutyKind").value="<%=mLCGetSchema.getGetDutyKind()%>";


  }
  catch(ex) {
  }
</script>
<%
            if(mLCGetSchema.getGetDutyKind().trim().equals("0")){
             continue;
            }
            else{
             break;
            }
         }
      }
      loggerDebug("ProposalQueryDetail","End Display 给付方法...2");
%>

<%

      // 连带被保人信息（未整理）
      LCInsuredSet mLCSubInsuredSet = new LCInsuredSet();
      mLCSubInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
      int insuredSubCount = mLCSubInsuredSet.size();
      loggerDebug("ProposalQueryDetail","End Display 给付方法...2" +insuredSubCount);
      int j=0;
      for (int i = 1; i <= insuredSubCount; i++) {
        LCInsuredSchema mLCSubInsuredSchema = mLCInsuredSet.get(i);
        /**这里if条件判断出错，跳过判断  20041124 wzw*/
        //if (mLCSubInsuredSchema.getInsuredGrade()!=null && mLCSubInsuredSchema.getInsuredGrade().equals("S")) {
        if(1==2){
%>
<script language="javascript">
            parent.fraInterface.SubInsuredGrid.addOne("SubInsuredGrid");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 1, "<%=mLCSubInsuredSchema.getInsuredNo()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 2, "<%=mLCSubInsuredSchema.getName()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 3, "<%=mLCSubInsuredSchema.getSex()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 4, "<%=mLCSubInsuredSchema.getBirthday()%>");
            parent.fraInterface.SubInsuredGrid.setRowColData(<%=j%>, 5, "<%=mLCSubInsuredSchema.getRelationToMainInsured()%>");
</script>
<%
         j++;
        }
      }
      loggerDebug("ProposalQueryDetail","End Display 连带被保人信息...");
%>

<%
      // 受益人信息
      LCBnfSet mLCBnfSet = new LCBnfSet();
      mLCBnfSet.set((LCBnfSet)tVData.getObjectByObjectName("LCBnfSet",0));
      int bnfCount = mLCBnfSet.size();
%>
<script language="javascript">
      parent.fraInterface.BnfGrid.clearData("BnfGrid");
</script>
<%
      for (int i = 1; i <= bnfCount; i++)   {
        LCBnfSchema mLCBnfSchema = mLCBnfSet.get(i);
%>
<script language="javascript">
  parent.fraInterface.BnfGrid.addOne("BnfGrid");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 1, "<%=mLCBnfSchema.getBnfType()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 2, "<%=mLCBnfSchema.getName()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 3, "<%=mLCBnfSchema.getIDType()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 4, "<%=mLCBnfSchema.getIDNo()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 5, "<%=mLCBnfSchema.getRelationToInsured()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 6, "<%=mLCBnfSchema.getBnfGrade()%>");
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 7, "<%=mLCBnfSchema.getBnfLot()%>");
  //hl
  parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 10, "<%=mLCBnfSchema.getInsuredNo()%>");
  //parent.fraInterface.BnfGrid.setRowColData(<%=i-1%>, 8, "no method wzw");
</script>
<%
      }
      loggerDebug("ProposalQueryDetail","End Display 受益人信息...");

      // 特约信息
      LCSpecSet mLCSpecSet = new LCSpecSet();
      mLCSpecSet.set((LCSpecSet)tVData.getObjectByObjectName("LCSpecSet",0));
      int specCount = mLCSpecSet.size();
%>
<script language="javascript">
  parent.fraInterface.SpecGrid.clearData("SpecGrid");
</script>
<%
      if (specCount==0) {
%>
<script language="javascript">
  parent.fraInterface.SpecGrid.addOne("SpecGrid");
  parent.fraInterface.SpecGrid.setRowColData(0,1,"1");
  parent.fraInterface.SpecGrid.setRowColData(0,2,"1");
</script>
<%
      }
      String strPath = application.getRealPath("config//Conversion.config");

      for (int i = 1; i <= specCount; i++) {
        LCSpecSchema mLCSpecSchema = mLCSpecSet.get(i);
%>
<script language="javascript">
  parent.fraInterface.SpecGrid.addOne("SpecGrid");
  parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 1, "<%=mLCSpecSchema.getSpecCode()%>");
  parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 2, "<%=mLCSpecSchema.getSpecType()%>");
  tStr = "<%=StrTool.Conversion(mLCSpecSchema.getSpecContent(), strPath)%>";
  tStr = Conversion(tStr);
  parent.fraInterface.SpecGrid.setRowColData(<%=i-1%>, 3, tStr);
</script>
<%
      }
      loggerDebug("ProposalQueryDetail","End Display 特约信息...");
    } // end of if

    //如果在Catch中发现异常，则不从错误类中提取错误信息
    if (FlagStr == "Fail") {
      tError = tProposalQueryUI.mErrors;
      if (!tError.needDealError()) {
         Content = " 查询成功! ";
         FlagStr = "Succ";
      } else {
         Content = " 查询失败2，原因是:" + tError.getFirstError();
         FlagStr = "Fail";
      }
    }


     loggerDebug("ProposalQueryDetail",FlagStr);
     loggerDebug("ProposalQueryDetail",Content);
     loggerDebug("ProposalQueryDetail","------ProposalQueryDetail end------\n\n");

%>


<script language="javascript">

    //得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/



  parent.fraInterface.emptyUndefined();
  //alert(parent.fraInterface.LoadFlag);

  //个人投保单直接录入
  if (parent.fraInterface.LoadFlag == 1) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";
    //////add by yaory for disabled Addappbutton when modify
//    parent.fraInterface.fm.riskbutton31.style.display='';
//    parent.fraInterface.fm.riskbutton32.style.display='';
//    parent.fraInterface.fm.riskbutton31.disabled='';
//    parent.fraInterface.fm.riskbutton32.disabled='';
    //parent.fraInterface.fm.riskbutton31.onclick="addAppRisk()";
    //parent.fraInterface.fm.riskbutton32.onclick="addAppRisk()";
    //////add by yaory for modify appaddrisk
                        //parent.fraInterface.fm.SelPolNo.value=="";
                        //alert(parent.fraInterface.fm.SelPolNo.value);
                        //parent.fraInterface.mSwitch.deleteVar("PolNo")='';
                        /////end add
  }

  //集体下个人投保单录入
  if (parent.fraInterface.LoadFlag == 2) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "none";
  }
  
  if (parent.fraInterface.LoadFlag == 7) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "none";
  }

  //个人投保单明细查询
  if (parent.fraInterface.LoadFlag == 3) {
    parent.fraInterface.divButton.style.display = "none";

    //增加随动处理
    try { parent.fraInterface.setFocus(parent.fraInterface.fm.all("PrtNo").value); } catch(e) {}

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }

  //集体下个人投保单明细查询
  if (parent.fraInterface.LoadFlag == 4) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.updateButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "";
    parent.fraInterface.spanApprove.style.display = "";
    //显示代码选择中文
    parent.fraInterface.showCodeName();
    //新单复核界面不显示录入完毕
    parent.fraInterface.fm.all("inputconfirm").style.display="none";
  }


  //复核
  if (parent.fraInterface.LoadFlag == 5) {
    parent.fraInterface.divButton.style.display = "none";

    //增加随动处理
    try { parent.fraInterface.setFocus(parent.fraInterface.fm.all("PrtNo").value); } catch(e) {}

    var innerHTML = "<center>"

    <!--innerHTML = innerHTML + " <INPUT class=cssButton VALUE=复核通过 TYPE=button onclick='top.opener.passApprovePol(); top.close();'>"-->
    <!--innerHTML = innerHTML + " <INPUT class=cssButton VALUE=问题件录入 TYPE=button onclick='top.opener.QuestInput();'>"-->
    <!--innerHTML = innerHTML + " <INPUT class=cssButton VALUE=问题件查询 TYPE=button onclick='top.opener.QuestQuery();'>"-->
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE=上一步 TYPE=button     onclick='returnparent();'>"
    innerHTML = innerHTML + "</center><br><br>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //显示代码选择中文
    parent.fraInterface.showCodeName();
  }


  //查询
  if (parent.fraInterface.LoadFlag == 6) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.inputQuest.style.display = "none";
    //“上一步”按钮
    parent.fraInterface.divUWContButton.style.display = "";
    innerHTML = parent.fraInterface.DivRiskCode.innerHTML.substring(0, parent.fraInterface.DivRiskCode.innerHTML.indexOf("</TR>"));
//    innerHTML = innerHTML + " <TD  class= title>投保单状态</TD>"
//    innerHTML = innerHTML + " <TD  class= input><Input class=readonly readonly name=statusFlag value='<%=statusFlag%>'></TD></tr></TBODY></table>";
    parent.fraInterface.DivRiskCode.innerHTML = innerHTML;

    //显示年龄
    parent.fraInterface.fm.all("AppntBirthday").onmousemove = parent.fraInterface.showAppntAge;
    parent.fraInterface.fm.all("Birthday").onmousemove = parent.fraInterface.showAge;

    //显示代码选择中文
    parent.fraInterface.showCodeName();
  }
  
  //新增附加险
  if (parent.fraInterface.LoadFlag == 8) {
     parent.fraInterface.inputButton.style.display = "none";
     parent.fraInterface.divButton.style.display = "";
     parent.fraInterface.divBqNSButton.style.display = "none";
  }

  //浮动费率
  if(parent.fraInterface.LoadFlag==10){

    try{
      parent.fraInterface.inputButton.style.display = "none";
      parent.fraInterface.updateButton.style.display = "";
      parent.fraInterface.divButton.style.display = "";
      parent.fraInterface.fm.all("FloatRate").readOnly = false;
      parent.fraInterface.fm.all("FloatRate").className = "common";
    }
    catch(ex)
    {

    }

    var innerHTML = "<center><input class=common name=SubModify VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"../appgrp/changeSubFloatRate.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "\"'>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";

  }



  //集体下投保单复核修改
  if (parent.fraInterface.LoadFlag == 13) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
    //问题件修改岗界面不显示录入完毕
    parent.fraInterface.fm.all("riskbutton2").style.display="none";
  }



  //Q:??
  if (parent.fraInterface.LoadFlag == 14) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";

    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }



  //集体下投保单查询
  if (parent.fraInterface.LoadFlag == 16) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.updateButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "";
    parent.fraInterface.inputQuestIn.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //显示代码选择中文
    parent.fraInterface.showCodeName();
    parent.fraInterface.fm.all("inputconfirm").style.display="none";
  }




  //复核修改、问题修改
  if (parent.fraInterface.LoadFlag ==23) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";

    parent.fraInterface.modifyButton.style.display = "";
    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=修改附加险 TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }





  //人工核保修改投保单
  if (parent.fraInterface.LoadFlag == 25) {
    parent.fraInterface.divButton.style.display = "none";

    //增加随动处理
    try { parent.fraInterface.setFocus(parent.fraInterface.fm.all("PrtNo").value); } catch(e) {}

    var innerHTML = "<center>"

    innerHTML = innerHTML + " <INPUT class=cssButton VALUE=上一步 TYPE=button     onclick='returnparent();'>"
    innerHTML = innerHTML + "</center><br><br>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.divButton.style.display = "";
    //显示“删除”、“修改”按钮
    parent.fraInterface.modifyButton.style.display = "";
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divUWContButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //显示代码选择中文
   // parent.fraInterface.showCodeName();
  }
  if (parent.fraInterface.LoadFlag == 99) {
    parent.fraInterface.divButton.style.display = "none";

     //显示“删除”、“修改”按钮
    parent.fraInterface.modifyButton.style.display = "none";
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divUWContButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.autoMoveButton.style.display = "";

    //显示代码选择中文
   // parent.fraInterface.showCodeName();
  }
  

</script>
</body>
</html>
