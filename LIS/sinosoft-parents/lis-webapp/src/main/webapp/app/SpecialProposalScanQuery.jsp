<%
//�������ƣ�SpecialProposalScanQuery.jsp
//�����ܣ�����Ͷ����¼��
//�������ڣ�2007-07-23 16:05:57
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
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var	ManageCom = "";  //�����Ĺ������
  var MissionID = "";
  var SubMissionID = "";
  var ActivityID = "";
  var SubType = "";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="SpecialProposalScanQuery.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="SpecialProposalScanQueryInit.jsp"%>
  
  <title>Ͷ����ɨ�����ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <div class="maxbox1">
    <table class=common>
        <tr class= common>
            <TD class=title5>ӡˢ��</TD>
            <TD class=input5><Input class="common wid"  name=QueryPrtNo id="QueryPrtNo"></TD>
            <TD class=title5></TD>
            <TD class=input5></TD>
        </tr>
    </table>
    </div>
    <div>
    <a href="javascript:void(0)" class=button onclick="ClickQuery();">��  ѯ</a>
    <a href="javascript:void(0)" class=button onclick="scanApplyClick();">����Ͷ����</a>
    </div>
    <br>
    <!-- <Input class=cssButton  VALUE="��ѯ" TYPE=button onclick="ClickQuery()">
    <INPUT class= cssButton VALUE="����Ͷ����" TYPE=button onclick="scanApplyClick();"> -->
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					  <span id="spanPolGrid" >
  					  </span> 
  			  	</td>
  			  </tr>
    	  </table>
    	
      <Div  id= "divPage" align=center style= "display: 'none' ">
        <INPUT CLASS=cssButton90 VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	 				
  	</Div>
		<input type=hidden id="GrpPolNo" name="GrpPolNo" id="GrpPolNo">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
