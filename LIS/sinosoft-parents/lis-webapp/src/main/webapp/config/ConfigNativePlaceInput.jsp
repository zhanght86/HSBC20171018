<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2005-01-26 13:18:16
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ConfigNativePlaceInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <%@include file="ConfigNativePlaceInit.jsp"%>
</head>
<body  onload="initForm();" >
 
  <form action="./ConfigNativePlaceSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDCode1);">
    		</td>
    		 <td class= titleImg>
        		 ϵͳ�������
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLDCode1" style= "display: ''" class="maxbox1">
			<table  class= common>
  			<TR  class= common>
    			
    			<TD  class= input5>
      			<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=CurCountry id=CurCountry class='codeno'  verify="ϵͳ�������|NOTNUlL&code:nativeplace" ondblclick="return showCodeList('nativeplace',[this,CurCountryName],[0,1]);" onclick="return showCodeList('nativeplace',[this,CurCountryName],[0,1]);" onkeyup="return showCodeListKey('nativeplace',[this,CurCountryName],[0,1]);" ><Input class= 'codename' name=CurCountryName id=CurCountryName >
    			</TD><TD  class= title5>
    			</TD>
                <TD  class= title5></TD>
                <TD  class= input5></TD>
  			</TR>
			</table>  </Div>
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">

  <!--<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">-->
  <a href="javascript:void(0);" name="addbutton" class="button" onClick="return submitForm();">��    ��</a>
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
