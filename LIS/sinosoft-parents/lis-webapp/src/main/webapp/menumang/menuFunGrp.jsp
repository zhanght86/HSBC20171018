<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�menuGrp.jsp
//�����ܣ��˵��������
//�������ڣ�2002-10-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
    GlobalInput tG1 = new GlobalInput();
	tG1=(GlobalInput)session.getValue("GI");
	String Operator = tG1.Operator;;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="menuFunGrp.js"></SCRIPT>
  <script src="treeMenu.js"></script>
  <%@include file="menuFunInit.jsp"%>

</head>
<body  onload="initForm();" >

<form action="./menuFunMan.jsp" method=post name=fm id=fm target="fraSubmit">
<br>
<div class="maxbox1">
   <table  >
    	<tr>
		<TD  class= input style= "display: none">
         <Input class="code" name=Action>
         class= input style= "display: none">
         <Input class="code" name=isChild>
         class= input style= "display: none">
         <Input class="code" name=isChild2>//2005
       </TD>
	   </tr>
      <TR  class= common>
          <TD  class= title5> �˵��ڵ�����</TD>
          <TD  class= input5><Input class="wid" class=common name=NodeName id=NodeName > </TD>
          <TD class= title5> ӳ���ļ�</TD>
          <TD  class= input5> <Input class="wid" class=common name=RunScript id=RunScript  ></TD>
      </TR> 
    </Table></div>

 &nbsp;&nbsp;&nbsp;&nbsp;<input style="vertical-align:middle;" type="checkbox" name="checkbox1" value="1"><span style="vertical-align:middle;  font-family:"����";">��Ϊ�Ӳ˵�����(��ѡ����ͬ���˵�����)</span>
  &nbsp;&nbsp;
  <input style="vertical-align:middle" type="checkbox" name="checkbox2" value="1"><span style="vertical-align:middle; font-family:"����";">��Ϊҳ��Ȩ�޲˵�����</span>
<br>
    <!-- <INPUT VALUE="��ѯ�˵�" TYPE=button class="cssButton" onclick="queryClick()">
     <INPUT VALUE="���Ӳ˵�" TYPE=button class="cssButton" onclick="insertClick()">
     <INPUT VALUE="ɾ���˵�" TYPE=button class="cssButton" onclick="deleteClick()">--><br>
     <a href="javascript:void(0);" class="button" onClick="queryClick();">��ѯ�˵�</a>
     <a href="javascript:void(0);" class="button" onClick="insertClick();">���Ӳ˵�</a>
     <a href="javascript:void(0);" class="button" onClick="deleteClick();">ɾ���˵�</a>


  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	 �˵��б�
      </td>

    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td><span id="spanQueryGrpGrid"></span></td>
	    </tr>
     </table>
</div>
</Div>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
