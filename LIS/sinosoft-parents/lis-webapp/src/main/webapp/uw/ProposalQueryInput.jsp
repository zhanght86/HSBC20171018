<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ProposalQueryInput.jsp
//�����ܣ��б���ѯ
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
	//�ͻ���
	String tCustomerNo = request.getParameter("CustomerNo");
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("ProposalQueryInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var customerNo = "<%=tCustomerNo%>"; //�ͻ���
</script>
<head>
	<title>�б���ѯ</title>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
    <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="../common/javascript/Common.js" ></SCRIPT>
	<script src="../common/javascript/MulLine.js"></SCRIPT>
	<script src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<script src="ProposalQuery.js"></SCRIPT> 
	<%@include file="ProposalQueryInit.jsp"%>
</head>
<body  onload="initForm();" > 
	<form method="post" name="fm" id="fm" target="fraSubmit">
		<br>
		<div class="maxbox1">
        <table class="common">
			<tr class="common">
				<td class="title5">�ͻ���</td>
				<td class="input5"><input class="common wid" name="CustomerNo" id="CustomerNo" readonly></td>
				<td class="title5">�ͻ�����</td>
				<td class="input5"><input class="common wid" name="CustomerName" id="CustomerName" readonly></td>
			</tr>
		</table>
		</div>
		<table>
		
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
				</td>
				<td class= titleImg>�б�����</td>
			</tr>
		</table>    
		<div id= "divCont" style= "display: ''" >
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanContGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display: '' ">
				<input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
				<input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
				<input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
				<input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
			</div>
		</div>	

		<!--������Ϣ-->
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
				</td>
				<td class= titleImg>�б�����������Ϣ</td>
			</tr>
		</table>    
		<div id= "divPol" style= "display: ''" >
				<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
						<span id="spanPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<div  id= "divTurnPage" align=center style= "display: '' ">
				<input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
				<input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
				<input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
				<input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
			</div>
		</div>	
		<hr class="line">
		<div id="Button" style="display:">
			<input class="cssButton" id="button1" name="button1" value="������ϸ��Ϣ��ѯ" type="button" onClick="getContDetailInfo();">
			<input class="cssButton" id="button2" name="button2" value="  Ӱ�����ϲ�ѯ  " type="button" onClick="showImage();">        
			<input class="cssButton" id="button3" name="button3" value="  �������Ѳ�ѯ  " type="button" onClick="showTempFee();">      
			<input class="cssButton" id="" name="" value="  �˱����۲�ѯ  " type="button" onClick="UWQuery();">          
      <input class="cssButton" id="button5" name="button5" value="  ����������ѯ  " type="button"  onclick="RecordQuery();">           
			<input class="cssButton" id="button6" name="button6" value="    ��Լ��ѯ    " type="button"  onclick="SpecQuery();">           
			<hr class="line"> 
			<input class="cssButton" value="     ��  ��     " type="button" onClick="returnParent();">
		</div>

		<!--������-->
		<div id = "divHidden" style = "display:none" >   
		</div>
	</form>
    <br><Br><br><Br><br><Br>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
