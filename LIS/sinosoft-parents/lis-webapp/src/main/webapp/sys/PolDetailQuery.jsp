<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-1-22
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
//�޸ģ�������
//�޸����ڣ�2004-02-17
//�޸ģ�����ǿ
//�޸����ڣ�2005-11-03

//�޸ģ�liuxiaosong
//�޸����ڣ�2006-10-19
//�޸����ݣ����뱣�������ѯ���
%>
<%
  String tPrtNo = "";
	String tContNo = "";
	String tIsCancelPolFlag = "";
	try
	{
		tContNo = request.getParameter("ContNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
		loggerDebug("PolDetailQuery","tContNo="+tContNo);
	}
	catch( Exception e )
	{
		tContNo = "";
		tIsCancelPolFlag = "0";
	}
	tPrtNo = request.getParameter("PrtNo");
%>
<head >
<script>
	var tContNo = "<%=tContNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
	var PrtNo = "<%=tPrtNo%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<SCRIPT src="PolDetailQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolDetailQueryInit.jsp"%>

	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ѯ </title>
</head>

<body  onload="initForm();queryPolType();querySignDate();queryMakeDate();" >
  <form  name=fm id=fm >
    <table class="common">
    	<tr class="common">
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    		<td class= titleImg>
    			��ͬ��Ϣ����
    		</td>
    	</tr>
    </table>

   <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
	   <table class="common" id="table2">
	   	 <tr CLASS="common">
	   	 	 <td CLASS="title">��������</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input NAME="GrpContNo" type=hidden CLASS="common">
     	 	   <input NAME="ContNo" id="ContNo" readonly  CLASS="readonly wid">
         </td>
	   	 	 <td CLASS="title">Ͷ��������</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input NAME="PrtNo" id="PrtNo" CLASS="readonly wid" readonly>
         </td>
	   	 	 <td CLASS="title">�������</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input NAME="ManageCom" id="ManageCom" readonly CLASS="codeno"><input class=codename name=ManageComName id=ManageComName readonly>
         </td>
	   	 </tr>

	   	 <tr CLASS="common">
	   	 	 <td CLASS="title">��������</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	 	   <input class=codeno readonly name="SaleChnl" id="SaleChnl"><input class=codename name=SaleChnlName id=SaleChnlName readonly>
         </td>
	   	   <td CLASS="title">����ͨ��ʶ</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="PayMode" id="PayMode" CLASS="readonly wid" readonly >
         </td>
	   	   <td CLASS="title">�ͻ�ǩ������</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="CustomGetPolDate" id="CustomGetPolDate" readonly CLASS="multiDatePicker wid">
         </td>
	   	 </tr>

	   	 <tr CLASS="common">
	   	 	<td CLASS="title">ҵ��Ա����</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="AgentCode" id="AgentCode" CLASS="readonly wid" readonly>
        	</td>
         <td CLASS="title">ҵ��Ա���</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="AgentGroup" id="AgentGroup" CLASS="readonly wid" readonly>
        	</td>
	   	 	<td CLASS="title">�������</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="AgentCom" id="AgentCom" CLASS="codeno" readonly><input class=codename name=AgentComName id=AgentComName readonly>
	   	 	</td>
       </tr>

       <tr>
         <td CLASS="title">��������״̬</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="PolType" id="PolType" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">ǩ������</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	 	  <input NAME="SignCom" id="SignCom" readonly CLASS="codeno"><input class=codename name=SignComName id=SignComName readonly>
         </td>
         <td CLASS="title">ǩ������</td>
	   	 	<td CLASS="input" COLSPAN="1">
	   	     <input NAME="SignDate" id="SignDate" CLASS="multiDatePicker wid" readonly >
         </td>
       </tr>

       <tr>
         <td CLASS="title">���ۻ���</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="Name" id="Name" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">������ӡ����</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="MakeDate" id="MakeDate" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">�ص�����</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="GetPolDate" id="GetPolDate" CLASS="multiDatePicker wid" readonly >
         </td>
       </tr>
       <tr CLASS="common">
       </tr>

       <tr>
         <td CLASS="title">���ڽɷ����д���</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="RNBankCode" id="RNBankCode" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">���ڽɷ������˺�</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="RNAccNo" id="RNAccNo" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title">���ڽɷѻ���</td>
	   	 	 <td CLASS="input" COLSPAN="1">
	   	     <input NAME="RNAccName" id="RNAccName" CLASS="readonly wid" readonly >
         </td>
       </tr>
       <tr>
         <td CLASS="title">������������</td>
	   	   <td CLASS="input" COLSPAN="1">
	   	     <input NAME="PrintTimes" id="PrintTimes" CLASS="readonly wid" readonly >
         </td>
         <td CLASS="title"></td>
	   	 	 <td CLASS="input"></td>
         <td CLASS="title"></td>
	   	 	 <td CLASS="input" ></td>
       </tr>
     </table>
   </div>

    <table>
    	<tr>
       <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,asd);"></td>
    		<td class= titleImg>Ͷ������Ϣ����</td>
    	</tr>
    </table>

<Div  id= "asd" style= "display: ''" class="maxbox1">
 <table  class= common>
   <TR  class= common>
     <TD  class= title>
       ����
     </TD>
     <TD  class= input>
       <Input name=AppntName id=AppntName CLASS="readonly wid" readonly>
     </TD>
     <TD  class= title>
       �Ա�
     </TD>
     <TD class= input>
       <Input readonly class="codeno" name=AppntSex id=AppntSex><input class=codename name=AppntSexName id=AppntSexName readonly>
     </TD>
     <TD  class= title>
       ��������
     </TD>
     <TD  class= input>
       <input readonly class="multiDatePicker wid" name="AppntBirthday" id="AppntBirthday">
     </TD>
   </TR>

   <TR  class= common>
   	 <TD  class= title>
       �ͻ���
     </TD>
     <TD  class= input>
     	 <Input class="wid" readonly class=readonly name="AppntNo" id="AppntNo">
     </TD>
     <TD  class= title>
       ֤������
     </TD>
     <TD  class= input>
       <Input readonly class="codeno" name="AppntIDType" id="AppntIDType"><input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly>
     </TD>
     <TD  class= title>
       ֤������
     </TD>
     <TD  class= input>
       <Input readonly class="readonly wid" name="AppntIDNo" id="AppntIDNo">
     </TD>
   </TR>

   <TR  class= common>
     <TD  class= title>
       ְҵ�ȼ�
     </TD>
     <TD  class= input>
       <input readonly class="codeno" name="WorkType" id="WorkType"><input class=codename name=WorkTypeName id=WorkTypeName readonly>
     </TD>
     <TD  class= title>
       ����
     </TD>
     <TD  class= input>
       <Input readonly class="codeno" name="NativePlace" id="NativePlace"><input class=codename name=NativePlaceName id=NativePlaceName readonly>
     </TD>
     <TD  class= title>
     <!--�ͻ�����  (��ʱ����)-->
     </TD>
     <TD  class= input >
       <Input readonly class="codeno" name="VIPType"type=hidden><input type=hidden class=codename name=VIPTypeName readonly>
     </TD>
   </TR>
 </table>
</div>


  <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divInsured);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ����
    		</td>
    	</tr>
    </table>
  <Div  id= "divInsured" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanInsuredGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
  	</Div>

    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divPol1);">
    		</td>
    		<td class= titleImg>
    			 ����������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divPol1" style= "display: ''">
      	<table align= center class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>

    	</table>
  	<!--<Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" class= cssButton TYPE=button onclick="turnPage1.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage1.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="��һҳ" class= cssButton TYPE=button onclick="turnPage1.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" class= cssButton TYPE=button onclick="turnPage1.lastPage();">
      </Div>-->
  	</Div>



   <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton1);">
    		</td>
    		<td class= titleImg>
    			����Լ��
    		</td>
    	</tr>
    </table>
  <Div id= "divButton1" class="maxbox1" style= "display: ''">

  <table class= common align=center>
    <tr class= common>
       <td class= input>
           <INPUT class=cssButton VALUE="������ϸ��ѯ" TYPE=button onClick="getQueryDetail();">
           <INPUT class=cssButton VALUE="Ӱ�����ϲ�ѯ" TYPE=button onClick="ImageQuery();">
           <INPUT class=cssButton VALUE="����������ѯ" TYPE=button onClick="OperationQuery();">
           <INPUT class=cssButton VALUE="����״̬��ѯ" TYPE=button onClick="StateQuery();">
           <INPUT class=cssButton VALUE="�����ݽ��Ѳ�ѯ " TYPE=button onClick="NewTempFeeQuery();">
           <INPUT class=cssButton VALUE=" �����˲�ѯ " TYPE=button onClick="InsuredQuery();">
           <INPUT class=cssButton VALUE="������ѯ" TYPE=hidden onClick="GetQueryClick();">
	       <!--INPUT class=common VALUE="���Ĳ��˷Ѳ�ѯ" TYPE=button onclick="EdorQueryClick();"-->
	       <INPUT class=cssButton VALUE="��ȫ��ѯ" TYPE=hidden onClick="BqEdorQueryClick();">
	       <!--INPUT class=common VALUE="���ղ�ѯ" TYPE=button onclick="MainRiskQuery();"-->
	       <!--INPUT class=common VALUE="�����ղ�ѯ" TYPE=button onclick="OddRiskQuery();"-->
	       <!--<INPUT class=cssButton VALUE="����ѯ" TYPE=button onclick="LoanQuery();"-->
	   </td>
    </tr>
  </table>
  </Div>


	 <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton2);">
    		</td>
    		<td class= titleImg>
    			�����շ���
    		</td>
    	</tr>
    </table>
  <Div id= "divButton2" class="maxbox1" style= "display: ''">

	 <table class= common align=center>
    <tr class= common>
     <td class= input align=lift>
	     <INPUT class=cssButton VALUE="  ���Ѳ�ѯ  "TYPE=button onClick="FeeQueryClick();">
	     <INPUT class=cssButton VALUE=" �ݽ��Ѳ�ѯ " TYPE=button onClick="TempFeeQuery();">
	     <INPUT class=cssButton VALUE=" �������ѯ " TYPE=button onClick="PremQuery();">
	     <INPUT class=cssButton VALUE="Ƿ�����Ѳ�ѯ" TYPE=hidden onClick="PayPremQuery();">
	     <INPUT class=cssButton VALUE="  �˻���ѯ  " TYPE=button onClick="InsuredAccQuery();">
	     <INPUT class=cssButton VALUE="�������ݲ�ѯ" TYPE=button onClick="BaoPanShuju();">
	     <br><br>
	     <INPUT class=cssButton VALUE=" ��������켣��ѯ " TYPE=hidden TYPE=button onclick="ShowTraceQuery();">
	     <INPUT class=cssButton VALUE="�շ�Ա������Ϣ��ѯ" TYPE=button onClick="ShowCollectorQuery();">
	     <INPUT class=cssButton VALUE=" �������ѯ " TYPE=hidden onClick="GetItemQuery();">
	   </td>
    </tr>
  </table>
	</Div>


	<table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton3);">
    		</td>
    		<td class= titleImg>
    			��ȫ��
    		</td>
    	</tr>
    </table>
  <Div id= "divButton3" class="maxbox1" style= "display: ''">

	 <table class= common align=center>
    <tr class= common>
     <td class= input align=left>
   <INPUT class=cssButton VALUE="  ������ѯ  " TYPE=button onClick="BonusQuery();">
   <INPUT class=cssButton VALUE="������ȡ��ѯ" TYPE=button onClick="LiveGetQuery();">

   <!-- XinYQ added on 2005-11-03 -->
   <INPUT class=cssButton VALUE="   �˻��켣��ѯ   " TYPE=button onClick="LCInsuAccQuery();">
   <input class="cssButton" value="���մ�����ѯ" type="button" onClick="ProxyIncomePayQuery()">

   <INPUT class=cssButton VALUE="��ȫ���Ĳ�ѯ" TYPE=button onClick="BqEdorQueryClick();">

   <!-- XinYQ added on 2006-03-01 -->
   <input class="cssButton" value="������ӡ��ѯ" type="button" onClick="ReissuePrintQuery()">

   <INPUT class=cssButton VALUE="�������ʲ�ѯ" TYPE=hidden onClick="ShowBankRateQuery();" >
   <br><br>
   <INPUT class=cssButton VALUE="������Ϣ��ѯ" TYPE=button onClick="ShowRiskInfoQuery();" >
   <INPUT class=cssButton VALUE="�潻/����ѯ" TYPE=button onClick="LoLoanQuery();">
   <INPUT class=cssButton VALUE="�����ձ��������ѯ" TYPE=button onClick="OmniQuery();">
   <INPUT class=cssButton VALUE="�����޸Ĺ켣��ѯ" TYPE=hidden onClick="PwdChangeTrackQuery();">
    </td>
   </tr>
   <tr class= common>
     <td class=input>

   <INPUT class=cssButton VALUE="Ͷ���Ƽ۲�ѯ" TYPE=hidden onClick="ShowCountQuery();"  >
   <INPUT class=cssButton VALUE="��ȫ�����ѯ" TYPE=hidden onClick="ShowEdorTrialQuery();" >
   <INPUT class=cssButton VALUE="  �˻���ѯ  " TYPE=hidden onClick="InsuredAccQuery();" >
   <INPUT class=cssButton VALUE="��ע��Ϣ" TYPE=hidden onClick="ShowRemark();" >
	 <INPUT class=cssButton VALUE="�������ѯ" TYPE=hidden onClick="ShowQuest();">
   <INPUT class=cssButton VALUE="�˱���ѯ" TYPE=hidden onClick="UWQuery();">
	 <INPUT class=cssButton VALUE="����״̬��ѯ" TYPE=hidden onClick="StateQuery();">
	  </td>
   </tr>
     </td>
   </tr>
  </table>
  </Div>

<!--Modify by zhaorx 2006-03-06
  <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton4);">
    		</td>
    		<td class= titleImg>
    			������
    		</td>
    	</tr>
    </table>
  <Div id= "divButton4" style= "display: ''">

     <table class= common align=center>
        <tr class= common>
            <td class=input>
                <INPUT class=cssButton VALUE="  �����ѯ  " TYPE=button onclick="ClaimGetQuery();">
                <INPUT class=cssButton VALUE="�������ѯ" TYPE=hidden onclick="HealthQuery();">
                <INPUT class=cssButton VALUE="���������ѯ" TYPE=hidden onclick="MeetQuery();">
                <INPUT class=cssButton VALUE="�ٱ��ظ���ѯ" TYPE=hidden onclick="UpReportQuery();">
                <INPUT class=cssButton VALUE="�˱�֪ͨ���ѯ" TYPE=hidden onclick="UWNoticQuery();">
                <INPUT class=cssButton VALUE="�ͻ��ϲ�֪ͨ���ѯ" TYPE=hidden onclick="KHHBNoticQuery();">
                <INPUT class=cssButton VALUE="�Ժ���ʾ��ѯ" TYPE=hidden onclick="UWErrQuery();">
                <!--INPUT class=cssButton VALUE="������֪��ѯ" TYPE=button onclick="ImpartQuery();"-->
                <!--INPUT class=cssButton VALUE="������������ϲ�ѯ" TYPE=button onclick="InsuredHealthQuery();"-->
                <!--INPUT class=cssButton VALUE="�����˽�����֪��ѯ" TYPE=button onclick="InsuredImpartQuery();"-->
                <!--INPUT class=cssButton VALUE="Ͷ���˱����ۼƲ�ѯ" TYPE=button onclick="AmntAccumulateQuery();"-->
                <!--INPUT class=cssButton VALUE="�����˱����ۼƲ�ѯ" TYPE=button onclick="InsuredAmntAccumulateQuery();"-->
        <!--        </td>
            </tr>
        </table>
    </Div>-->


  <table style= "display: none">
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton5);">
    		</td>
    		<td class= titleImg>
    			������
    		</td>
    	</tr>
    </table>
  <Div id= "divButton5" class="maxbox1" style= "display: none">
  	<table class= common align=center>
        <tr class= common>
        <td class= input align=lift>
  	        <INPUT class=cssButton VALUE="������ʷ��ѯ" TYPE=hidden onClick="ShowWageHistoQuery();">
            <INPUT class=cssButton VALUE="��֯��ϵ��ѯ" TYPE=button onClick="ShowOrganizationQuery();">
            <INPUT class=cssButton VALUE="ѪԵ��ϵ��ѯ" TYPE=button onClick="ShowConsanguinityQuery();">
            <INPUT class=cssButton VALUE="������ʷ��ѯ" TYPE=hidden onClick="ShowWelfareHistoQuery();">
            <INPUT class=cssButton VALUE="������ʷ��ѯ" TYPE=button onClick="ShowCheckHistoQuery();">
            <input class="cssButton" value="�����˻�����Ϣ��ѯ" type="button" onClick="AgentQuery();">
            <!-- INPUT class=cssButton VALUE="ҵ��Ա������Ϣ��ѯ" TYPE=button onclick="ShowOperInfoQuery();" -->
            <INPUT class=cssButton VALUE="��������ʷ��Ϣ��ѯ" TYPE=button onClick="ShowOperHistoQuery();">
      </tr>
     </td>
   </table>
  </Div>

     <table>
    	 <tr>
         <td class=common>
			     <IMG  src= "../common/images/butExpand.gif" style="cursor:hand;" OnClick= "showPage(this,divButton6);">
    		 </td>
    		 <td class= titleImg>
    			 �ͻ�����
    		 </td>
    	 </tr>
     </table>

  <Div id= "divButton6" class="maxbox1" style= "display: ''">
    <table class= common align=center>
      <tr class= common>
        <td class= input align=lift>
          <INPUT class=cssButton VALUE="Ͷ�����ѳб�������ѯ" TYPE=button onClick="AppntqueryProposal();">
          <INPUT class=cssButton VALUE="Ͷ����δ�б�������ѯ" TYPE=button onClick="AppntqueryNotProposal();">
          <INPUT class=cssButton VALUE="�������ѳб�������ѯ" TYPE=button onClick="InsuredqueryProposal();">
          <INPUT class=cssButton VALUE="������δ�б�������ѯ" TYPE=button onClick="InsuredqueryNotProposal();">
	     </td>
  	  </tr>
  	</table>
  </Div>

  <table class= common>
    <tr class= common>
      <td>
        <INPUT class=cssButton VALUE="  ��  ��  " TYPE=button onClick="GoBack();">
      </td>
   </tr>
  </table>
 </form>
 <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
