<%
//�������ƣ�ProposalApproveModify.jsp
//�����ܣ����˲�ͨ���޸�
//�������ڣ�2002-11-23 17:06:57
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var prtNo = "<%=request.getParameter("prtNo")%>";      //���˵��Ĳ�ѯ����.
	var type = "<%=request.getParameter("type")%>";        //���ͣ��ɣУ�������ѣ������ģ�����촫��
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="ChangePlanSub.js"></SCRIPT>
  <%@include file="ChangePlanSubInit.jsp"%>
  
  <title>���˲�ͨ��������ѯ </title>
</head>

<body onload="initForm();" >
  <form method=post name=fm target="fraTitle">
    <!-- ������Ϣ���� -->
    <table>
    	<tr>
    		<td class=titleImg>
    			<INPUT VALUE="�޸�Ͷ����" class=cssButton TYPE=button onclick="modifyClick();">
    			<INPUT VALUE="�������ѯ" class=cssButton TYPE=button onclick="QuestQuery();">
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display:  ">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	
      				
  	</div>
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
