<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.cardgrp.*"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
  //�������
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  String statusFlag = "";
  String customerNo = "";  //��¼�����˿ͻ��ţ�������Ͷ���˿ͻ��Ž����ж�
  loggerDebug("ProposalQueryDetail","\n\n------ProposalQueryDetail start------");
  // ������Ϣ����
  LCPolSchema tLCPolSchema   = new LCPolSchema();
  // ׼���������� VData
  tLCPolSchema.setPolNo(request.getParameter("PolNo"));
  tLCPolSchema.setContNo(request.getParameter("ContNo"));
  String tLoadFlag=(String)request.getParameter("LoadFlag");
  loggerDebug("ProposalQueryDetail","tLoadFlag"+tLoadFlag);
  VData tVData = new VData();
  tVData.addElement( tLCPolSchema );
  // ���ݴ���
  ProposalQueryUI tProposalQueryUI   = new ProposalQueryUI();
  if (!tProposalQueryUI.submitData( tVData, "QUERY||DETAIL" )) {
      Content = "��ѯʧ�ܣ�ԭ����: " + tProposalQueryUI.mErrors.getError(0).errorMessage;
      FlagStr = "Fail";
  }else {
    tVData.clear();
    tVData = tProposalQueryUI.getResult();
    // ������Ϣ
    LCPolSchema mLCPolSchema = new LCPolSchema();
    mLCPolSchema.setSchema((LCPolSchema)tVData.getObjectByObjectName("LCPolSchema",0));

//    String tContType = mLCPolSchema.getContType();
//    String tLoadFlag = "3";
//    if( tContType.equals( "2" ))   // �����µĸ���Ͷ����
//       tLoadFlag = "4";
//add by yaory ��lcpol���ֶ�mainpolno
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
    //�����µĸ���Ͷ����
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
    //����Ͷ����״̬���
    try {
      if (mLCPolSchema.getApproveFlag().equals("0")) statusFlag = "δ���и���";
      else if (mLCPolSchema.getApproveFlag().equals("1")) statusFlag = "����δͨ��";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("0")) statusFlag = "����ͨ�������˱�";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("5")) statusFlag = "����ͨ�����Զ��˱�δͨ�������˹��˱�";
      else if (mLCPolSchema.getApproveFlag().equals("9") && mLCPolSchema.getUWFlag().equals("9")) statusFlag = "����ͨ�����˱�ͨ��";
    }
    catch(Exception e) {
      loggerDebug("ProposalQueryDetail","statusFlag throw Error");
    }
    loggerDebug("ProposalQueryDetail","statusFlag:" + statusFlag);
    loggerDebug("ProposalQueryDetail","End Display ������Ϣ...");


   // ������Ϣ��δ����
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
					//�����Ҫ����

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
   loggerDebug("ProposalQueryDetail","End Display ������Ϣ...");
%>

<%
      // ��������Ϣ
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

   loggerDebug("ProposalQueryDetail","End Display ��������Ϣ...");
%>

<%
      // ����Ͷ������Ϣ
      LCAppntSchema mLCAppntSchema = new LCAppntSchema();
      mLCAppntSchema.setSchema((LCAppntSchema)tVData.getObjectByObjectName("LCAppntSchema",0));

      // ����Ͷ������Ϣ
      LCGrpAppntSchema mLCGrpAppntSchema = new LCGrpAppntSchema();
      mLCGrpAppntSchema.setSchema((LCGrpAppntSchema)tVData.getObjectByObjectName("LCGrpAppntSchema",0));

%>
<script language="javascript">


</script>

<%
    loggerDebug("ProposalQueryDetail","End Display ����Ͷ������Ϣ...");
    // ��������Ϣ
    LCInsuredSet mLCInsuredSet = new LCInsuredSet();
    mLCInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
    int insuredCount = mLCInsuredSet.size();

%>
<script language="javascript">
</script>
<%
    loggerDebug("ProposalQueryDetail","End Display ��������Ϣ...");
%>

<%
     // ������Ϣ�ĸ�������
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
      loggerDebug("ProposalQueryDetail","End Display ��������...2");
%>

<%

      // ������������Ϣ��δ����
      LCInsuredSet mLCSubInsuredSet = new LCInsuredSet();
      mLCSubInsuredSet.set((LCInsuredSet)tVData.getObjectByObjectName("LCInsuredSet",0));
      int insuredSubCount = mLCSubInsuredSet.size();
      loggerDebug("ProposalQueryDetail","End Display ��������...2" +insuredSubCount);
      int j=0;
      for (int i = 1; i <= insuredSubCount; i++) {
        LCInsuredSchema mLCSubInsuredSchema = mLCInsuredSet.get(i);
        /**����if�����жϳ��������ж�  20041124 wzw*/
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
      loggerDebug("ProposalQueryDetail","End Display ������������Ϣ...");
%>

<%
      // ��������Ϣ
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
      loggerDebug("ProposalQueryDetail","End Display ��������Ϣ...");

      // ��Լ��Ϣ
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
      loggerDebug("ProposalQueryDetail","End Display ��Լ��Ϣ...");
    } // end of if

    //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
    if (FlagStr == "Fail") {
      tError = tProposalQueryUI.mErrors;
      if (!tError.needDealError()) {
         Content = " ��ѯ�ɹ�! ";
         FlagStr = "Succ";
      } else {
         Content = " ��ѯʧ��2��ԭ����:" + tError.getFirstError();
         FlagStr = "Fail";
      }
    }


     loggerDebug("ProposalQueryDetail",FlagStr);
     loggerDebug("ProposalQueryDetail",Content);
     loggerDebug("ProposalQueryDetail","------ProposalQueryDetail end------\n\n");

%>


<script language="javascript">

    //�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/



  parent.fraInterface.emptyUndefined();
  //alert(parent.fraInterface.LoadFlag);

  //����Ͷ����ֱ��¼��
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

  //�����¸���Ͷ����¼��
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

  //����Ͷ������ϸ��ѯ
  if (parent.fraInterface.LoadFlag == 3) {
    parent.fraInterface.divButton.style.display = "none";

    //�����涯����
    try { parent.fraInterface.setFocus(parent.fraInterface.fm.all("PrtNo").value); } catch(e) {}

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }

  //�����¸���Ͷ������ϸ��ѯ
  if (parent.fraInterface.LoadFlag == 4) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.updateButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "";
    parent.fraInterface.spanApprove.style.display = "";
    //��ʾ����ѡ������
    parent.fraInterface.showCodeName();
    //�µ����˽��治��ʾ¼�����
    parent.fraInterface.fm.all("inputconfirm").style.display="none";
  }


  //����
  if (parent.fraInterface.LoadFlag == 5) {
    parent.fraInterface.divButton.style.display = "none";

    //�����涯����
    try { parent.fraInterface.setFocus(parent.fraInterface.fm.all("PrtNo").value); } catch(e) {}

    var innerHTML = "<center>"

    <!--innerHTML = innerHTML + " <INPUT class=cssButton VALUE=����ͨ�� TYPE=button onclick='top.opener.passApprovePol(); top.close();'>"-->
    <!--innerHTML = innerHTML + " <INPUT class=cssButton VALUE=�����¼�� TYPE=button onclick='top.opener.QuestInput();'>"-->
    <!--innerHTML = innerHTML + " <INPUT class=cssButton VALUE=�������ѯ TYPE=button onclick='top.opener.QuestQuery();'>"-->
    innerHTML = innerHTML + " <INPUT class=cssButton VALUE=��һ�� TYPE=button     onclick='returnparent();'>"
    innerHTML = innerHTML + "</center><br><br>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //��ʾ����ѡ������
    parent.fraInterface.showCodeName();
  }


  //��ѯ
  if (parent.fraInterface.LoadFlag == 6) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.inputQuest.style.display = "none";
    //����һ������ť
    parent.fraInterface.divUWContButton.style.display = "";
    innerHTML = parent.fraInterface.DivRiskCode.innerHTML.substring(0, parent.fraInterface.DivRiskCode.innerHTML.indexOf("</TR>"));
//    innerHTML = innerHTML + " <TD  class= title>Ͷ����״̬</TD>"
//    innerHTML = innerHTML + " <TD  class= input><Input class=readonly readonly name=statusFlag value='<%=statusFlag%>'></TD></tr></TBODY></table>";
    parent.fraInterface.DivRiskCode.innerHTML = innerHTML;

    //��ʾ����
    parent.fraInterface.fm.all("AppntBirthday").onmousemove = parent.fraInterface.showAppntAge;
    parent.fraInterface.fm.all("Birthday").onmousemove = parent.fraInterface.showAge;

    //��ʾ����ѡ������
    parent.fraInterface.showCodeName();
  }
  
  //����������
  if (parent.fraInterface.LoadFlag == 8) {
     parent.fraInterface.inputButton.style.display = "none";
     parent.fraInterface.divButton.style.display = "";
     parent.fraInterface.divBqNSButton.style.display = "none";
  }

  //��������
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

    var innerHTML = "<center><input class=common name=SubModify VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"../cardgrp/changeSubFloatRate.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "\"'>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";

  }



  //������Ͷ���������޸�
  if (parent.fraInterface.LoadFlag == 13) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
    //������޸ĸڽ��治��ʾ¼�����
    parent.fraInterface.fm.all("riskbutton2").style.display="none";
  }



  //Q:??
  if (parent.fraInterface.LoadFlag == 14) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";

    parent.fraInterface.modifyButton.style.display = "";

    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }



  //������Ͷ������ѯ
  if (parent.fraInterface.LoadFlag == 16) {

    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.updateButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";
    parent.fraInterface.inputQuest.style.display = "";
    parent.fraInterface.inputQuestIn.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //��ʾ����ѡ������
    parent.fraInterface.showCodeName();
    parent.fraInterface.fm.all("inputconfirm").style.display="none";
  }




  //�����޸ġ������޸�
  if (parent.fraInterface.LoadFlag ==23) {
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divButton.style.display = "";

    parent.fraInterface.modifyButton.style.display = "";
    if (top.type!=null && top.type=="ChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";
    }
    if (top.type!=null && top.type=="SubChangePlan") {
      innerHTML = "<center> <INPUT class=common name=ChangePlanSub VALUE=�޸ĸ����� TYPE=button onclick='parent.fraInterface.window.location=\"./ChangePlanSub.jsp?prtNo=" + parent.fraInterface.fm.all("PrtNo").value + "&type=SubChangePlan\"'>";

      parent.fraInterface.deleteButton.style.display = "";
    }
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.inputQuestButton.style.display = "none";
    parent.fraInterface.inputQuestButton.disabled = true ;
  }





  //�˹��˱��޸�Ͷ����
  if (parent.fraInterface.LoadFlag == 25) {
    parent.fraInterface.divButton.style.display = "none";

    //�����涯����
    try { parent.fraInterface.setFocus(parent.fraInterface.fm.all("PrtNo").value); } catch(e) {}

    var innerHTML = "<center>"

    innerHTML = innerHTML + " <INPUT class=cssButton VALUE=��һ�� TYPE=button     onclick='returnparent();'>"
    innerHTML = innerHTML + "</center><br><br>";
    parent.fraInterface.spanApprove.innerHTML = innerHTML;
    parent.fraInterface.divButton.style.display = "";
    //��ʾ��ɾ���������޸ġ���ť
    parent.fraInterface.modifyButton.style.display = "";
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divUWContButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";

    //��ʾ����ѡ������
   // parent.fraInterface.showCodeName();
  }
  if (parent.fraInterface.LoadFlag == 99) {
    parent.fraInterface.divButton.style.display = "none";

     //��ʾ��ɾ���������޸ġ���ť
    parent.fraInterface.modifyButton.style.display = "none";
    parent.fraInterface.inputButton.style.display = "none";
    parent.fraInterface.divUWContButton.style.display = "none";
    parent.fraInterface.spanApprove.style.display = "";
    parent.fraInterface.autoMoveButton.style.display = "";

    //��ʾ����ѡ������
   // parent.fraInterface.showCodeName();
  }
  

</script>
</body>
</html>
