<%
//�������ƣ�FineProposalQuestModify.jsp
//�����ܣ����˲�ͨ���޸�
//�������ڣ�2008-06-26 
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/laydate/laydate.js"></script>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="FineWbProposalQuestModify.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <%@include file="FineWbProposalQuestModifyInit.jsp"%>
  
  <title>�쳣������ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraTitle">
    <!-- ������Ϣ���� -->     
     <table >
      <TR >
        <td class="common" >
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRegisterInfo);">
        </td>
        <td class= titleImg>
          �쳣����ѯ
        </TD>
    	</TR>
    </table>
  <div class="maxbox1" id="divRegisterInfo">  
    <table class=common>
    <tr class=common>
    	<TD  class= title5 >
          ��֤ӡˢ��
        </TD>
        <TD  class= input5>
          <Input class= "common wid"  name=TempFeeNo >
        </TD>
        <TD  class= title5 >
          �����˾
        </TD>
        <TD  class= input5>
          <Input class= code  name=BPOID id="BPOID" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');"  ondblclick="return showCodeList('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');"  onkeyup="return showCodeListKey('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');">
        </TD>
    </tr>
    <tr>         
        <TD  class= title5 >
          �������
        </TD>
        <TD  class= input5>
          <Input class= code  name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('comcode',[this]);" onDblClick="return showCodeList('comcode',[this]);" onKeyUp="return showCodeListKey('comcode',[this]);">
        </TD>      
      <TD class=title5>ɨ������</TD>
	    <TD  class= input5>
	      <Input class= "coolDatePicker" dateFormat="short" name=ScanDate id="ScanDate" onClick="laydate({elem:'#ScanDate'});"><span class="icon"><a onClick="laydate({elem: '#ScanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>    
    </tr>
    </table>
    <Br>
    <table class=common>
      <tr>
	<td class=common>
    	 <input type=button class = cssButton value="��  ѯ" onClick="easyQueryClick()">
    	 <INPUT VALUE="�쳣������" class = cssButton TYPE="hidden" onClick="modifyClick();">
  	</td>
      </tr>
    </table>
    <Br>
    </div>
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
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
      </Div> 	
      				
  	</div>
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
