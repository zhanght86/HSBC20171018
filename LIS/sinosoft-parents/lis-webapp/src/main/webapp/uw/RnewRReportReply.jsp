<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�RReportReply.jsp
//�����ܣ�������鱨��ظ�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  �����ˣ�ln    �������ڣ�2008-10-23   ����ԭ��/���ݣ������º˱�Ҫ������޸�
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head >
<title>�������ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="RnewRReportReply.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewRReportReplyInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit" action="">
    <!-- ������ѯ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
		<td class= titleImg>�������ѯ������</td>
	</tr>
    </table>
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common>
    	<tr class= common>
      	<TD  class= title5>  ������ </TD>
        <TD  class= input5> <Input class="wid" class= common name=QPrtNo id=QPrtNo > </TD>
        <TD  class= title5>  ������ </TD>
        <TD  class= input5> <Input class="wid" class= common name=QInsuredName id=QInsuredName > </TD></tr>
        <tr class= common>
        <TD  class= title5> ������� </TD>
        <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code wid" name=QManagecom id=QManagecom ondblclick="return showCodeList('station',[this]);" onclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"> </TD>
        <TD  class= title5></TD>
        <TD  class= input5></TD>
        </tr>
    </table></Div>
    <!--<input type= "button" class= cssButton name= "Reply" value="��  ѯ" onClick= "easyQueryClick()">-->
    <a href="javascript:void(0);" name= "Reply" class="button" onClick="easyQueryClick();">��    ѯ</a>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 ������鱨�����ݣ�
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
        <center>
      <INPUT VALUE="��  ҳ" class = cssButton90  TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91  TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92  TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class = cssButton93  TYPE=button onclick="turnPage.lastPage();">	</center>
    </div>
    <br> </br>
    
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
