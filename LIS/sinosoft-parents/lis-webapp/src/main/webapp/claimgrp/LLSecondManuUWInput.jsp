<%
//�������ƣ���LLSecondManuUWInput.jsp.
//�����ܣ���������˹��˱�-----��ͬ�򱣵��˱�
//�������ڣ�2005-12-29 11:10:36
//������  ��zhangxing
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<head >
	<%
		GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
    	String tCaseNo = request.getParameter("CaseNo"); //�ⰸ��		
		String tBatNo = request.getParameter("BatNo");   //���κ�			
		String tInsuredNo = request.getParameter("InsuredNo"); //�����˿ͻ��� 
		String tClaimRelFlag = request.getParameter("ClaimRelFlag"); //�ⰸ��ر�־ 	
		
		String tMissionid = request.getParameter("Missionid");   //����ID 
		String tSubmissionid = request.getParameter("Submissionid"); //������ID 
		String tActivityid = request.getParameter("Activityid");  //�ڵ�� 	
		
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="./LLSecondManuUW.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="./LLSecondManuUWInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm target="fraSubmit" >
	<table>
		<tr>
			<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCUWBatchGrid);"></td>
			<td class= titleImg>��ͬ�б���Ϣ</td>
		</tr>
	</table>	
	<Div  id= "DivLLCUWBatchGrid" align= center style= "display: ''">
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1 ><span id="spanLLCUWBatchGrid"></span> </td>
			</tr>
		</table>
	</Div>   
	<!--��ͬ��Ϣ-->
	<table >
		<tr>
			<td><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCCont);"></td>
			<td class="titleImg">��ͬ��ϸ��Ϣ</td>
		</tr>
	</table>
	<div id="DivLCCont" STYLE="display:''">
		<table class=common >
			<tr class=common>
				<td class="title">ӡˢ���� </td>
				<td><input name="PrtNo" VALUE class="readonly" readonly TABINDEX="-1" MAXLENGTH="14"></td>
				<td class="title">�ܵ�Ͷ��������</td>
				<td ><input name="ProposalContNo" class="readonly" readonly ></td>	
				<td class="title">������� </td>
				<td><input name="ManageCom"  MAXLENGTH="10" class="readonly" readonly></td>

			</tr>
			<tr class=common>
				<td class="title" >�������� </td>
				<td ><input name="SaleChnl" class="readonly" readonly ></td>
				<td class="title">�����˱��� </td>
				<td><input name="AgentCode" MAXLENGTH="10" class="readonly" readonly></td>
				<td class="title">�������</td>
				<td ><input name="AgentCom" class="readonly" readonly ></td>					    		
			</tr>
		</table>
		<table class=common>
			<tr>
				<TD class= title>��ע</TD>
			</tr>
			<tr>
				<TD  class= input> <textarea name="Remark" readonly cols="100%" rows="3" witdh=100% class=common></textarea></TD>
			</tr>
		</table>
	</div>	
	
	<!----Ͷ���齡����֪��ѯ�ʺ�,��콡����֪��ѯ�ʺ�,��Ӧδ��֪���,�����˽���״����֪����----->
	<hr>
	<div>
		<table class=common>
			<tr><TD class= title>Ͷ���齡����֪��ѯ�ʺţ�</TD></tr>
			<tr>
				<TD  class= input><textarea name="HealthImpartNo1" readonly cols="100%" rows="1" witdh=100% class=common></textarea></TD>
			</tr>
			<tr><TD class= title>��콡����֪��ѯ�ʺţ�</TD></tr>
			<tr>
				<TD  class= input> <textarea name="HealthImpartNo2" readonly cols="100%" rows="1" witdh=100% class=common></textarea></TD>
			</tr>
				
			<tr><TD class= title>��Ӧδ��֪�����</TD></tr>
			<tr>
				<TD  class= input> <textarea name="NoImpartDesc" readonly cols="100%" rows="3" witdh=100% class=common></textarea></TD>
			</tr>
			<tr><TD class= title>�����˽���״����֪���ݣ�</TD></tr>
			<tr>
				<TD  class= input> <textarea name="Remark1" readonly cols="100%" rows="3" witdh=100% class=common></textarea></TD>
			</tr>					
		</table>
	</div>
	
	<!-- Ͷ������Ϣ���� -->
	<table>
		<tr>
			<td><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);"></td>
			<td class= titleImg>Ͷ������Ϣ</td>
		</tr>
	</table>
	<DIV id=DivLCAppntInd STYLE="display:''">
		<table  class= common>
			<TR  class= common>        
				<TD  class= title>�ͻ���</TD>
				<TD  class= input><Input class="readonly" readonly name=AppntNo></TD>				
				<TD  class= title>����</TD>
				<TD  class= input><Input class="readonly" readonly name=AppntName></TD>
				<TD  class= title> �Ա�</TD>
				<TD  class= input><Input class="readonly" readonly name=AppntSex ></TD>

			</TR>
			<TR  class= common>
				<TD  class= title> ����</TD>
				<TD  class= input><input class="readonly" readonly name="AppntBirthday"></TD>				
				<TD  class= title>ְҵ</TD>
		  		<TD  class= input><input class="readonly" readonly name="OccupationCode"></TD>	         
		  		<TD  class= title>����</TD>
		  		<TD  class= input><input class="readonly" readonly name="NativePlace"></TD>

			</TR>
			<TR>
				<TD  class= title>VIP���</TD>
				<TD  class= input><Input class="readonly" readonly name=VIPValue ></TD>
				<TD  class=title>���������</TD>
		 		<TD  class= input><Input class="readonly" readonly name=BlacklistFlag ></TD>
				<TD  class=title></TD>
		 		<TD  class= input></TD> 				 		
			</TR>
		</table>   
	</DIV>
	<hr>
	<INPUT VALUE="Ͷ������ϸ" disabled class=cssButton TYPE=hidden onclick="showPolDetail();"> 
	<INPUT VALUE="ɨ�����ѯ"  class=cssButton  TYPE=button onclick="ScanQuery();">  
	<INPUT VALUE="Ͷ���˼���Ͷ����Ϣ"  disabled class=cssButton TYPE=hidden onclick="showApp(1);"> 
	<input value="���¼��"  class=cssButton type=button onclick="showHealth();" >
	<input value="�������¼��"  class=cssButton type=button onclick="showRReport();">   
	<input value="���±�" disabled class=cssButton TYPE=hidden onclick="showNotePad();" >   
	<input value ="����������Ϣ" class = cssButton type = button onclick="enterRiskInfo();">
	<hr>   
	<div id = "divUWResult" style = "display: ''">
		<!-- �˱����� -->   	
		<table  class= common>
			<TR class= common>
				<TD class= title>�˱�����</td>
				<td class=input><Input class=codeno readonly name=uwState ondblclick="return showCodeList('contuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKey('contuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename readonly ></td>
				<TD class=title></TD>
				<TD class=input></TD>	
				<TD class=title></TD>
				<TD class=input></TD>	
			</TR>               
		</table>            
		<table class=common>
			<tr>
				<TD class= title>�˱����</TD>
			</tr>
			<tr>
				<TD  class= input> <textarea name="UWIdea" cols="100%" rows="5" witdh=100% class=common></textarea></TD>
			</tr>
		</table>
		<table class=common>		
			<tr>
				<INPUT VALUE="ȷ   ��" class=cssButton TYPE=button onclick="uwSaveClick();">
	    		<INPUT VALUE="��   ��" class=cssButton TYPE=button onclick="uwCancelClick();">
        	</tr>		
		</table>
	</div>
	<hr>
	<Input value ="�˱����" class = cssButton Type=button onclick="llSecondUWFinish();">
	<INPUT value ="��   ��" class=cssButton TYPE=button onclick="turnBack();"> 
	<!--��������-->
	<input type=hidden id="Operator" name="Operator" ><!--//-->
	<input type=hidden id="ComCode" name="ComCode" ><!--//-->
	<input type=hidden id="ManageCom" name="ManageCom" ><!--//-->			
	
	<input type=hidden id="tCaseNo" name="tCaseNo" ><!--//�ⰸ��<��ҳ����>-->
	<input type=hidden id="tBatNo" name="tBatNo" ><!--//���κ�<��ҳ����>-->
	<input type=hidden id="tInsuredNo" name="tInsuredNo" ><!--//�����˿ͻ���<��ҳ����> -->
	<input type=hidden id="tClaimRelFlag" name="tClaimRelFlag" ><!--//�ⰸ��ر�־<��ҳ����> -->	
	<input type=hidden id="tMissionid" name="tMissionid"><!--  //����ID -->
	<input type=hidden id="tSubmissionid" name="tSubmissionid"><!--//������ID -->
	<input type=hidden id="tActivityid" name="tActivityid"><!--//�ڵ�� 	-->	
		  
	<input type=hidden id="tContNo" name="tContNo" ><!--//��ͬ���� -->	
	
    <input type=hidden id="fmtransact" name="fmtransact" ><!--�����Ĳ�������-->
</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
</body>
</html>
