<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html> 
<%
//�������ƣ�UWAppG.jsp
//�����ܣ�����Ͷ����Ϣ��ѯ
//�������ڣ�2004-11-27 11:10:36
//������  �� ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWAppG.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWAppGInit.jsp"%>
  <title>����Ͷ����Ϣ </title>
</head>
<body  onload="initForm('<%=tProposalNo%>','<%=tInsureNo%>','<%=tContNo%>');" >
  <form method=post id="fm" name=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- ����Ͷ����Ϣ���֣��б� -->
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
	<Div  id= "divLCPol1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanPolGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onclick="getLastPage();">
      <INPUT type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
      <INPUT type= "hidden" id="InsureNoHide" name= "InsureNoHide" value= "">
      <hr class="line"/>
      <!--INPUT VALUE="������ϸ��Ϣ" class= common TYPE=button onclick="showPolDetail();"> 
      <INPUT VALUE="�����˱���¼" class= common TYPE=button onclick="showOldUWSub();">
      <INPUT VALUE="���պ˱���Ϣ" class= common TYPE=button onclick="showNewUWSub();">
      <INPUT VALUE="���������Ϣ" class= common TYPE=button onclick="showHealthQ();">
      <INPUT VALUE="�˱���������" class= common TYPE=button onclick="showReport();"-->
      <!--hr>
      </hr-->					
	</div>
	<p>
        <INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="parent.close();"> 					
    </P>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
