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
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="UWNotePad.js"></SCRIPT>
  <%@include file="UWNotePadInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="./UWNotePadSave.jsp">
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
    <table class= common>
    	<TR  class= common>
    	  <TD  class= title>
          ����Ա
        </TD>
        <TD  class= input>
          <Input class=common name=Operator>
        </TD>
        <TD  class= title>
          ¼������
        </TD>
        <TD  class= input>
          <Input class="coolDatePicker" dateFormat="short" name=MakeDate >
        </TD>
        <TD  class= input>
          <input type= "button" name= "Query" value="��  ѯ" class=cssButton onClick= "queryClick()">
        </TD>
      </TR>
    </table>
    

    <hr>
    
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
    	
    	<Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=common VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=common VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=common VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	
      
    </div>

  <hr>
  
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
