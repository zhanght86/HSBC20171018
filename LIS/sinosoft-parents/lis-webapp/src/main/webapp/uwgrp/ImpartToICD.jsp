<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ImpartToICD.jsp
//�����ܣ���֪����¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./ImpartToICD.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ImpartToICDInit.jsp"%>
  <title> ����Լ�������¼�� </title>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./ImpartToICDSave.jsp">
    <!-- ���б� -->
    <table>
    	<TR  class= common>
          <TD  class= title>  ��ͬ����  </TD>
          <TD  class= input> <Input class="readonly" name=ContNo > </TD>
           <INPUT  type= "hidden" class= Common name= PrtNo value= "">
          <TD  class= title>  ������  </TD>
          <TD  class= input> <Input class=code name=InsureNo ondblClick="showCodeListEx('InsureNo',[this,''],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('InsureNo',[this,''],[0,1],null,null,null,1);" onFocus= "easyQueryClickSingle();"> <!-- onFocus= "easyQueryClickSingle();easyQueryClick();"--> </TD>
        </TR>
    </table>
<DIV id=DivLCImpart STYLE="display:''">
<!-- ��֪��Ϣ���֣��б� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart2);">
</td>
<td class= titleImg>
�������˸�֪��ϸ��Ϣ
</td>
</tr>
</table>

<div  id= "divLCImpart2" style= "display: ''">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanImpartDetailGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>   �������</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWDis" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanDisDesbGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
      </div>
      <INPUT type= "button" name= "sure" value="��  ��" class=cssButton onclick="submitForm()">			
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
