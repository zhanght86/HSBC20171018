<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWNotePad.jsp
//�����ܣ����±���ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
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
//	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>���±���ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="UWNotePad.js"></SCRIPT>
  <%@include file="UWNotePadInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit" action="./UWNotePadSave.jsp">
    <table>
    	<tr>
      	<td class=common>
		      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 ���±���ѯ
    		</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <table class= common>
    	<TR  class= common>
    	  <TD  class= title5>
          ����Ա
        </TD>
        <TD  class= input5>
          <Input class="common wid" name=Operator id=Operator>
        </TD>
        <TD  class= title5>
          ¼������
        </TD>
        <TD  class= input5>
          <Input class="coolDatePicker" dateFormat="short" name=MakeDate onClick="laydate({elem: '#MakeDate'});" id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
      </TR>
      <tr class="common">
      	<TD  class= input colspan="2">
          <input type= "button" name= "Query" value="��  ѯ" class=cssButton onClick= "queryClick()">
        </TD>
      </tr>
    </table>
    </div>
    <hr class="line">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 ���±����ݣ�
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
    	
    	<Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	
      
    </div>

  <hr class="line">
  
  <table width="80%" height="20%" class= common>
    <TR  class= common>  
      <TD width="100%" height="15%"  class= title> ���±����ݣ�400������,�س���ռһ���ַ��� </TD>
    </TR>
    <TR  class= common>
      <TD height="85%"  class= title><textarea name="Content" verify="���±�����|len<800" verifyorder="1" cols="135" rows="5" class="common" ></textarea></TD>
    </TR>
  </table>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">
  <input type="hidden" name= "OperatePos" value= "">
  <INPUT CLASS=cssButton VALUE="��  ��" TYPE=button onclick="insertClick()">
  <INPUT CLASS=cssButton VALUE="��ռ��±�����" TYPE=button onclick="fm.Content.value=''">
  
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
