<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�GroupUW.jsp
//�����ܣ����屣���˹��˱�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tContNo = "";
	String tPrtNo = "";
	String tGrpContNo = "";
	String tMissionID = "";
	String tSubMissionID = "";  
	String tActivityID = "";
	String tNoType = "";
		
	tPrtNo = request.getParameter("PrtNo");
	tGrpContNo = request.getParameter("GrpContNo");
	tMissionID = request.getParameter("MissionID");
	tSubMissionID  = request.getParameter("SubMissionID");	
	tActivityID = request.getParameter("ActivityID");	
	tNoType = request.getParameter("NoType");
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
	loggerDebug("GroupUW",tGrpContNo+"asdf"+tPrtNo);
%>
<script>
	var PrtNo = "<%=tPrtNo%>";
	var GrpContNo = "<%=request.getParameter("GrpContNo")%>";
	var MissionID = "<%=tMissionID%>";
	var SubMissionID = "<%=tSubMissionID%>";
	var ActivityID = "<%=tActivityID%>";
	var NoType = "<%=tNoType%>";
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GroupUW.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GroupUWInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./GroupUWCho.jsp" method=post name=fmQuery id="fmQuery" target="fraSubmit">
  	<div id = "yaory" style = "display: none">
      <table>
        <tr>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=AgentCom id="AgentCom" readonly=true><input class=codename name=AgentComName id="AgentComName" readonly=true>
          </TD>
          <TD  class= title5>
            VIP���
          </TD>
          <TD  class= input5>
          	<input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=VIPValue id="VIPValue" readonly=true><input class=codename name=VIPValueName id="VIPValueName" readonly=true>
            <INPUT type= "hidden" name= "Operator" id="Operator" value= "">
            <INPUT type= "hidden" name= "temriskcode" id="temriskcode" value= "">
          </TD>
        </tr>
      </table>
    </div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpCont);">
    		</td>
    		<td class= titleImg>
    			����Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divGrpCont" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            Ͷ��������
          </TD>
 		      <TD  class= input>
            <Input class="readonly wid" readonly name=GrpContNo id="GrpContNo">
      <!--    <TD  class= title8>
            ӡˢ����
          </TD>-->
            <Input type="hidden"  class="readonly" readonly name=PrtNo id="PrtNo">
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=ManageCom id="ManageCom" readonly=true><input class=codename name=ManageComName id="ManageComName" readonly=true>
          </TD>
          <TD  class= title>
            ��������
          </TD> 
          <TD  class= input>
          	<input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=SaleChnl id="SaleChnl" readonly=true><input class=codename name=SaleChnlName id="SaleChnlName" readonly=true>
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ҵ��Ա
          </TD>
          <TD  class= input>
      				<Input class="readonly wid" readonly name=AgentCode id="AgentCode">
         </TD>
         <TD  class= title>
            ���������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AgentGroup id="AgentGroup">
          </TD>  
          <TD  class= title>
            Ͷ����λ�ͻ�����
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AppntNo id="AppntNo">
          </TD>        
        </TR>
        <TR  class= common>
          <TD  class= title>
            Ͷ����λ����
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AppntName id="AppntName">
          </TD>  
        </TR>
      </table>
        <div id="DivBlack" STYLE="display:none">
        <table class=common>
        <TR  class= common>          
          <TD  CLASS="title5">
            ���������
          </TD>
          <TD CLASS="input5">
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=BlacklistFlag id="BlacklistFlag" readonly=true><input class=codename name=BlacklistFlagName id="BlacklistFlagName" readonly=true>
          </TD>
          <td CLASS="title5">�˱����� </td>
				  <td CLASS="input5" COLSPAN="1">
					  <input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=SendUWFlag id="SendUWFlag" readonly=true><input class=codename name=SendUWFlagName id="SendUWFlagName" readonly=true>
	    		</td>
        </TR>
        </table>
        </div>
        <div id="DivLCSendTrance" STYLE="display:none">
			  <table class=common>
				  <tr CLASS="common">
			      <td CLASS="title5">�ϱ���־ 
	    		  </td>
				    <td CLASS="input5" COLSPAN="1">
				      <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=SendFlag id="SendFlag" readonly=true><input class=codename name=SendFlagName  id="SendFlagName" readonly=true>
	    	    </td>
    				<td CLASS="title5">�˱����</td>
    				<td CLASS="input5" COLSPAN="1">
				      <input NAME="SendUWIdea" id="SendUWIdea" CLASS="readonly wid" readonly>
	    	    </td>
			    </tr>
		    </table>		
	      </div>
    </Div>
    </div>
    <!-- �����ѯ���� -->
    <!--TR  class= common>
      <TD  class= titles>
        ����Ͷ��������
      </TD>
      <TD  class= input>
        <Input class= common name=GrpProposalNo >
        <Input type= "hidden" class= common name=GrpMainProposalNo >
        <INPUT type= "hidden" name= "Operator" value= "">
      </TD>
    </TR-->       
       
    <!-- ��ѯδ�����屣�����б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			����Ͷ������ѯ���
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </Div>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolFee);">
    		</td>
    		<td class= titleImg>
    			���ֱ�����Ϣ
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCPolFee" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanGrpPolFeeGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>    	
    </Div>    
   
	<hr class="line">
	<div>
    <INPUT VALUE ="ɨ�����ѯ" Class="cssButton" name="scan" id="scan" TYPE=button onclick="ScanQuery2()"> 
    <INPUT VALUE ="����˱���Ϣ" Class="cssButton" name="autoCheck" id="autoCheck" TYPE=button onclick= "showGNewUWSub();"> 
    <a href="javascript:void(0)" class=button name="grpDetails" id="grpDetails" onclick="showGrpCont();">���屣����ϸ</a> 
    <input value="���ŵ������" class=cssButton name="grpManageFee" id="grpManageFee" type=button onclick="showRealFee();">
    <INPUT VALUE ="�����������ѯ" Class="cssButton" name="grpPolReason" id="grpPolReason" TYPE=button onclick="GrpQuestQuery();"> 
    <a href="javascript:void(0)" class=button name="perAutoCheck" id="perAutoCheck" onclick="GrpContQuery('<%=tGrpContNo%>');">�����Զ��˱���Ϣ</a> 
  </div>
	<!-- <INPUT VALUE ="ɨ�����ѯ" Class="cssButton" name="scan" TYPE=button onclick="ScanQuery2()"> 
	<INPUT VALUE ="����˱���Ϣ" Class="cssButton" name="autoCheck" TYPE=button onclick= "showGNewUWSub();">
	<INPUT VALUE ="���屣����ϸ" Class="cssButton" name="grpDetails" TYPE=button onclick= "showGrpCont();"> -->
	<!--INPUT VALUE =" �������ѯ����Ϣ " Class="cssButton" TYPE=button onclick= "showAskApp();"-->
	<!--INPUT VALUE =" �������������Ϣ " Class="cssButton" TYPE=button onclick= "showHistoryCont();"-->
	<!-- <input value="���ŵ������" class=cssButton name="grpManageFee" type=button onclick="showRealFee();">
	<INPUT VALUE ="�����������ѯ" Class="cssButton" name="grpPolReason" TYPE=button onclick="GrpQuestQuery();">
	<INPUT VALUE =" �����Զ��˱���Ϣ " Class="cssButton" name="perAutoCheck" TYPE=button onclick="GrpContQuery('<%=tGrpContNo%>');"> -->
	<br>
	<!--INPUT VALUE ="�����ѳб�������ѯ" class="cssButton"TYPE=button onclick="queryProposal();"-->
  <!--INPUT VALUE ="����δ�б�������ѯ" class="cssButton"TYPE=button onclick="queryNotProposal();"-->
	<!--INPUT VALUE="���屣���б���Լ¼��" Class=Common TYPE=button onclick="showGSpec();"-->
	
	
	<!--INPUT VALUE = "���屣�������¼��" Class="cssButton" TYPE=button onclick="GrpQuestInput();"-->
  <div>
    <a href="javascript:void(0)" class=button name="grpQuestionInput" id="grpQuestionInput" onclick="GrpQuestInput();">���������¼��</a>
    <a href="javascript:void(0)" class=button name="questInputConfirm" id="questInputConfirm" onclick="GrpQuestInputConfirm();">�����¼�����</a>
    <a href="javascript:void(0)" class=button style="display:none" name="PE" id="PE" onclick="showPE();">���������۲�ѯ</a>
    <a href="javascript:void(0)" class=button name="RealFee" id="RealFee" onclick="showContPlan();">���ϼƻ���ѯ</a>
  </div>
	<!-- <INPUT VALUE="���������¼��"    class=cssButton name="grpQuestionInput"  TYPE=button onclick="GrpQuestInput();">
	<INPUT VALUE="�����¼�����"    class=cssButton name="questInputConfirm"  TYPE=button onclick="GrpQuestInputConfirm();"> -->
	<!--INPUT VALUE = "���������֪ͨ��" Class="cssButton" name="perAutoInfo" TYPE=button onclick="checkBody();"-->
	<!-- <input value="���������۲�ѯ" style="display:'none'" class=cssButton name="PE" disabled=true type=button onclick="showPE();">
	<input value="���ϼƻ���ѯ" class=cssButton name="RealFee" type=button onclick="showContPlan();"> -->
	
	<!--INPUT VALUE = "����������֪ͨ��" Class="cssButton" TYPE=button onclick="RReport();"-->
	<!--INPUT VALUE = "���˱�����֪ͨ��" Class="cssButton" TYPE=button onclick="GrpSendNotice();"-->
	
	<!--input value="���˱�֪ͨ��" class=cssButton type=button onclick="SendNotice();"--> 
	
		<!--input value="  �б��ƻ����  " class=cssButton type=button onclick="showChangePlan();"-->
	<br>
	<!--INPUT VALUE="�ֱ�����¼��"    class=cssButton name="distriContract"  TYPE=button onclick="tt('<%=tGrpContNo%>');"-->
	<!--INPUT VALUE="������¼��"    class=cssButton name="FeeContract" TYPE=button onclick="pp('<%=tGrpContNo%>');"-->
	<!--input value="����ֺ�¼��" class=cssButton name="perFH"  type=button onclick="showBonus();"-->
	<!--input value="��Լ¼��" class=cssButton name="SpecInfo" type=button onclick="showSpecInfo();"-->
  <div>
    <input value="����Ѷ���" class=cssButton name="manageFeeQuery" id="manageFeeQuery" type=button onclick="showManaFee();">
    <a href="javascript:void(0)" class=button name="perAutoRea" id="perAutoRea" onclick="GrpContReasonQuery('<%=tGrpContNo%>');">�Ժ�δͨ���嵥</a>
    <a href="javascript:void(0)" class=button name="Tbandpay" id="Tbandpay" onclick="UWPastInfo('<%=tGrpContNo%>');">����Ͷ�����⸶�����ѯ</a>
  </div>
  <br>
	<!-- <input value="����Ѷ���" class=cssButton name="manageFeeQuery"  type=button onclick="showManaFee();">
	<INPUT VALUE ="�Ժ�δͨ���嵥" Class="cssButton" name="perAutoRea" TYPE=button onclick="GrpContReasonQuery('<%=tGrpContNo%>');"> -->
	<!--INPUT VALUE ="ǿ���ٱ���" Class="cssButton" name="perAutoRea1" TYPE=button onclick="ReLife('<%=tGrpContNo%>');"-->
	<!-- <INPUT VALUE ="����Ͷ�����⸶�����ѯ" Class="cssButton" name="Tbandpay" TYPE=button onclick="UWPastInfo('<%=tGrpContNo%>');"> -->
	<!--input class=cssButton VALUE="�����ͨ�ò�ѯ" TYPE=button onclick="ManageFeeCal();"-->
		<!--input value="�޸�������Ҫ����¼��" class=cssButton type=button onclick="showChangeResultView();"-->
		<!--input value="  �����������۲�ѯ  " class=cssButton type=button onclick="showReport();"-->
		
		<!--input value = "�ŵ��ӷѳб�¼��" class=cssButton type=button onclick="grpAddFee()"-->
		<!--input value = "����Ҫ�ط��ʵ���" class=cssButton type=button onclick="grpRiskElement()">
		<input value = "�������ʵ���" class = cssButton type = button onclick = "grpFloatRate()"-->
		<!--INPUT VALUE="�����¼��" class=cssButton TYPE=button onclick="QuestInput();"-->
	<!--INPUT VALUE = "���������" Class="cssButton" TYPE=button onclick="SendQuest();"-->
	<!--INPUT VALUE = "���ͼӷѺ˱�֪ͨ��" Class="cssButton" TYPE=button onclick="SendNotice();"-->
  <hr class="line">    
    <!-- ���嵥�˱����� -->
    <div id="div1" style="display:none">
      <table class= common border=0 width=100%>
    	  <tr>
		      <td class= titleImg align= center>�������ֵ��˱����ۣ�</td>
	      </tr>
      </table>
      <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
            �������ֵ��˱�����
          </TD>
          <TD class=input5>
            <!--Input class=codeno name=GUWState ondblclick="return showCodeList('tdhbconlusion',[this,GUWStateName],[0,1]);" onkeyup="return showCodeListKey('tdhbconlusion',[this,GUWStateName],[0,1]);"><Input class=codename name=GUWStateName readonly elementtype=nacessary--> 
            <!--Input class="code" name=GUWState CodeData="0|^1|�ܱ�^4|ͨ�ڳб�^9|��׼�б�" ondblclick="showCodeListEx('Grpcondition',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition',[this,''],[0,1]);"-->                   
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name=GUWState class='codeno' id="GUWState" ondblclick="getcodedata();return showCodeListEx('GUWState',[this,GUWStateName],[0,1],'', '', '', true);" onkeyup="getcodedata();return showCodeListEx('GUWState',[this,GUWStateName],[0,1]);"><input name=GUWStateName id="GUWStateName" class=codename readonly=true>
          </TD>
          <td class=title5></td>
          <td class=input5></td>
        </TR>
        <tr>
          <TD  class= title5>
            �������ֵ��˱����
          </TD>
          <TD  class= input5> <textarea name="GUWIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></TD>
          <td class=title5></td>
          <td class=input5></td>
        </tr>
      </table>
      <div id="divUWSave" style="display:''">		
        <a href="javascript:void(0)" class=button name="ok" id="ok" onclick="grpPolCommit();">ȷ  ��</a>
    	  <!-- <input value = "ȷ  ��" Class="cssButton" name="ok" TYPE=button onclick="grpPolCommit();"> -->
      </div>
      <br>
    	<div id = "divUWAgree" style = "display: 'none'">
        <a href="javascript:void(0)" class=button name="isOk" id="isOk" onclick="gmanuchk(1);">ͬ  ��</a>
        <a href="javascript:void(0)" class=button name="isNotOk" id="isNotOk" onclick="gmanuchk(2);">��ͬ��</a>
      	<!-- <INPUT VALUE="ͬ  ��" class=cssButton name="isOk" TYPE=button onclick="gmanuchk(1);">
      	<INPUT VALUE="��ͬ��" class=cssButton name="isNotOk" TYPE=button onclick="gmanuchk(2);"> -->
      </div>
  
          <input type="hidden" name= "WorkFlowFlag" id="WorkFlowFlag" value= "">
          <input type="hidden" name= "MissionID" id="MissionID" value= "">
          <input type="hidden" name= "SubMissionID" id="SubMissionID" value= "">
          <input type="hidden" name= "PrtNoHide" id="PrtNoHide" value= "">
          <input type="hidden" name= "GrpProposalContNo" id="GrpProposalContNo" value= "">
          <input type="hidden" name= "GrpPolNo" id="GrpPolNo" value="">
          <INPUT  type= "hidden" class= Common name= "YesOrNo" id="YesOrNo" value= ""> 
      <br>   
      <div id = "divChangeResult" style = "display: 'none'">
        <table  class= common>
          <tr class=common>
          	<TD  class= title5>
            		�б��ƻ��������¼��:
          	</TD>
            <TD  class= input5><textarea name="ChangeIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></TD>
            <td class= title5></td>
            <td class= input5></td>
          </tr>
      	</table>
        <div>
          <a href="javascript:void(0)" class=button onclick="showChangeResult();">ȷ  ��</a>
          <a href="javascript:void(0)" class=button onclick="HideChangeResult();">ȡ  ��</a>
        </div>
      	<!-- <INPUT VALUE="ȷ  ��" class=cssButton TYPE=button onclick="showChangeResult();">
      	<INPUT VALUE="ȡ  ��" class=cssButton TYPE=button onclick="HideChangeResult();"> -->
      </div>
    </div>
    <div id = "divUWResult" style = "display: ''">
        <!-- �˱����� -->
        <table class=common >
            <tr>
              <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
              </td>
              <td class= titleImg align= center>�ŵ��˱�����</td>
            </tr>
        </table>
        <div class="maxbox1">
        <div  id= "divFCDay" style= "display: ''">
        <table class=common>
            <tr class = common>
            <!--td class=title8
                    �� �� ��-->
                <!--Input class="code" name=cooperate CodeData="0|^001|�����Ѱ�^002|�п���" ondblclick="showCodeListEx('condition2',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition2',[this,''],[0,1]);"--> 
                <!--Input name=cooperate class='codeno' id="cooperate" ondblclick="getcodedata1();return showCodeListEx('cooperate',[this,cooperateName],[0,1],'', '', '', true);" onkeyup="getcodedata1();return showCodeListEx('cooperate',[this,cooperateName],[0,1]);"><input name=cooperateName class=codename readonly=true-->                 
                
                <td class=title5>
                    ���ռ���
                </td>
                <!--Input class="code" name=riskgrade CodeData="0|^01|һ��^02|��^03|��" ondblclick="showCodeListEx('condition3',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition3',[this,''],[0,1]);"--> 
                <td class=input5>
                <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name=riskgrade class='codeno' id="riskgrade" onclick="getcodedata2();return showCodeListEx('riskgrade',[this,riskgradeName],[0,1],'', '', '', true);" ondblclick="getcodedata2();return showCodeListEx('riskgrade',[this,riskgradeName],[0,1],'', '', '', true);" onkeyup="getcodedata2();return showCodeListEx('riskgrade',[this,cooperateName],[0,1]);"><input name=riskgradeName id="riskgradeName" class=codename readonly=true><font color=red>*</font>                                  
                </td>
                <td class=title5></td>
                <td class=input5></td>
                
                <!--
                <td class=title8>
                    ����Э��
                <Input class="code" name=appcontract CodeData="0|^0|��^1|��" ondblclick="showCodeListEx('condition4',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition4',[this,''],[0,1]);"> 
                 <Input name=appcontract class='codeno' id="appcontract" ondblclick="getcodedata3();return showCodeListEx('appcontract',[this,appcontractName],[0,1],'', '', '', true);" onkeyup="getcodedata3();return showCodeListEx('appcontract',[this,cooperateName],[0,1]);"><input name=appcontractName class=codename readonly=true><font color=red>*</font>                                                   
                </td>
                -->
            </tr>
            <tr class = common>
                <td class=title5>
                    �˱�����
                </td>
                    <!--Input class="codeno" name=uwUpReport ondblclick="return showCodeList('uwUpReport',[this,uwUpReportname],[0,1]);" onkeyup="return showCodeListKey('uwUpReport',[this,uwUpReportname],[0,1]);" onFocus="showUpDIV();"><Input class="codename" name=uwUpReportname readonly elementtype=nacessary-->
                    <!--Input class="code" name=uwUpReport CodeData="0|^0|�������^2|��Ȩ���ϱ�" ondblclick="showCodeListEx('condition1',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('condition1',[this,''],[0,1]);"-->     
                <td class=input5>              
                   <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" name=uwUpReport class='codeno' id="uwUpReport" onclick="getcodedata4();return showCodeListEx('uwUpReport',[this,uwUpReportName],[0,1],'', '', '', true);" ondblclick="getcodedata4();return showCodeListEx('uwUpReport',[this,uwUpReportName],[0,1],'', '', '', true);" onkeyup="getcodedata4();return showCodeListEx('uwUpReport',[this,uwUpReportName],[0,1]);"><input name=uwUpReportName id="uwUpReportName" class=codename readonly=true><font color=red>*</font>
                </td>
                <td class=title5></td>
                <td class=input5></td>
            </tr>
        </table>
        <table class=common>
  		    <tr class=common>
  		      	<td class= title5 style="width:14.5%;">
              		�˱����
            	</td>
              <td  class= input5 > <textarea name="UWIdea" cols="100%" rows="4" witdh=100% class="common"></textarea></td>
              <td class=title5></td>
              <td class=input5></td>
          </tr>
  		    <tr>
        		<td>
        			<input name=uwPopedom id="uwPopedom" type=hidden>
            </td>
        	</tr>
	      </table>
      </div>
      </div>
      <a href="javascript:void(0)" class=button name="grpPolUtility" id="grpPolUtility" onclick="gmanuchk(0);">����Ͷ��������ȷ��</a>
      <a href="javascript:void(0)" class=button onclick="GoBack();">��  ��</a>
	    <!-- <INPUT VALUE="����Ͷ��������ȷ��" Class="cssButton" name="grpPolUtility" TYPE=button onclick="gmanuchk(0);"> -->
	     <!--INPUT class=cssButton VALUE="���±��鿴" TYPE=button onclick="showNotePad();"-->  
	    <!-- <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="GoBack();">  	 -->
</div>
    
    
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
