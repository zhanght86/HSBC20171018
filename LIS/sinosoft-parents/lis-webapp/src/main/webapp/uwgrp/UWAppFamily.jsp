<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html> 
<%
//�������ƣ�UWApp.jsp
//�����ܣ�����Ͷ����Ϣ��ѯ
//�������ڣ�2002-06-19 11:10:36
//������  �� WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWAppFamily.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWAppFamilyInit.jsp"%>
  <title>����Ͷ����Ϣ </title>
</head>
<body  onload="initForm('<%=tContNo%>');" >
  <form method=post name=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- ����Ͷ����Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 ��ͥ��Ա
    		</td>
    	</tr>
    </table>
  	<Div  id= "divFamily" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanFamilyGrid" >
					</span> 
				</td>
			</tr>
			</table>
		</div>  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 ����Ͷ����Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCPol1" style= "display: ''" align=center>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanPolGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="��ҳ" class= cssButton TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="βҳ" class= cssButton TYPE=button onclick="getLastPage();">
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" name= "ContNoHide" value= "">
      <INPUT type= "hidden" name= "InsureNoHide" value= "">
      <INPUT type= "hidden" name= "AppntNoHide" value= "">
      <hr>
      </hr>					
      <INPUT VALUE="������ϸ��Ϣ" class= cssButton TYPE=button onclick="showPolDetail();"> 
      <INPUT VALUE="�����˱���¼" class= cssButton TYPE=button onclick="showOldUWSub();">
      <INPUT VALUE="���պ˱���Ϣ" class= cssButton TYPE=button onclick="showNewUWSub();">
      <INPUT VALUE="���������Ϣ" class= cssButton TYPE=button onclick="showHealthQ();">
      <INPUT VALUE="�˱���������" class= cssButton TYPE=button onclick="showReport();">
      <hr>
      </hr>					
	</div>
	<p>
        <INPUT VALUE="����" class= cssButton TYPE=button onclick="parent.close();"> 					
    </P>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
