<html>
<%
//�������� :FIRuleDealErrLogInput.jsp
//������ :�������ݴ���
//������ :���
//�������� :2008-09-09
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ���� 
 	%>
<script>

  var comcode = "<%=tGI1.ComCode%>";
  var RuleDealBatchNo = <%=request.getParameter("RuleDealBatchNo")%>;
  var DataSourceBatchNo = <%=request.getParameter("DataSourceBatchNo")%>;
  var RulePlanID = <%=request.getParameter("RulePlanID")%>;
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src = "FIRuleDealErrLogInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleDealErrLogInputInit.jsp"%>
</head>
<body  onload="initForm();queryFIRuleDealErrLogGrid();initElementtype();">
  <form action="./FIRuleDealErrLogSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divLLReport1" style= "display: ''">
  	<table class= common border=0 width=100%>
  		<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIRuleDealErrLogGrid);">
    		</td>
    		<td class= titleImg>
    			 �嵥�б�
    		</td>
    	</tr>
    </table>
    <Div  id= "divFIRuleDealErrLogGrid" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanFIRuleDealErrLogGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <center>
      <INPUT CLASS="cssButton90" VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS="cssButton91" VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS="cssButton92" VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS="cssButton93" VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();"></center>
   </Div>
   
  <table class= common>
	<INPUT VALUE="���������嵥" class = cssButton TYPE=button onclick="ToExcel();" >  
	<INPUT VALUE="�����������" class = cssButton TYPE=button onclick="DealErrdata();" >  
	<INPUT VALUE="���Դ���" class = cssButton TYPE=button onclick="DealErrdata1();" >  
	</table>   
  <div class="maxbox1">
  <table class= common>
	<tr class= common>
				 <TD class= title5>
					  У�����κ���
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RuleDealBatchNo id=RuleDealBatchNo readonly=true >
				</TD>
				<TD class= title5>
					  ����Դ���κ���
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=DataSourceBatchNo id=DataSourceBatchNo readonly=true >
				</TD>
	</tr>
	</table></div>
 <div class="maxbox1">
<table class= common>
	<tr class= common>
				 <TD class= title5>
					  ƾ֤����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=CertificateID id=CertificateID readonly=true >
				</TD>
				<TD class= title5>
					  ������Ϣ
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=ErrInfo id=ErrInfo readonly=true >
				</TD>
	</tr>
	<tr class= common>
				 <TD class= title5>
					  ҵ������ʶ
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=businessno id=businessno readonly=true >
				</TD>
				<TD class= title5>
					  У��ƻ�
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RulePlanID id=RulePlanID readonly=true >
				</TD>
	</tr>
	<tr class= common>
				 <TD class= title5>
					  У�����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=RuleID id=RuleID readonly=true >
				</TD>
				<TD class= title5>
					  ����״̬
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=DealState id=DealState readonly=true >
				</TD>
	</tr>
	</table></div>
  <input type=hidden NAME="OperateType" VALUE=''>
  <input type=hidden NAME="DelErrSerialNo" VALUE=''>
  <input type=hidden NAME="ExpSQL" VALUE=''>
</table>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
