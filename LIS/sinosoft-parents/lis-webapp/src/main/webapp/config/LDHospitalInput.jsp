<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2005-01-24 18:15:01
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">
 var UWPopedom = "<%=request.getParameter("UWPopedom")%>";
 var UWType = "<%=request.getParameter("UWType")%>";
 var tt="hh";
</script> 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LDHospitalInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LDHospitalInputInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDHospitalSave.jsp" method=post name=fm id=fm target="fraSubmit">
   
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUser1);">
    		</td>
    		 <td class= titleImg>
        		 ���ҽԺ������Ϣ
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLDUWUser1" style= "display: ''" class="maxbox">
<table  class= common  >
  <TR  class= common>
    <TD  class= title5>
      ���ҽԺ����
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=HospitalCode id=HospitalCode elementtype=nacessary  verify="���ҽԺ����|notnull&len<=20">
    </TD>
    <TD  class= title5>
      ���ҽԺ����
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=HospitalName id=HospitalName elementtype=nacessary  verify="���ҽԺ����|notnull">
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      ���ҽԺ����
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="�˱�Ȩ��|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=HospitalGrade id=HospitalGrade  verify="���ҽԺ����|code:HospitalGrade"
        ondblclick="return showCodeList('HospitalGrade',[this,HospitalGradeName],[0,1],null,null,null,1);"  onclick="return showCodeList('HospitalGrade',[this,HospitalGradeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('HospitalGrade', [this,HospitalGradeName],[0,1],null,null,null,1);"><input class=codename readonly=true name=HospitalGradeName id=HospitalGradeName >
    </TD>
    <TD  class= title5>
      �����������
    </TD>
    <TD  class= input5>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=ManageCom id=ManageCom verify="�����������|notnull&len<=8"  ondblclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);"onclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('CodeCode', [this,ManageComName],[0,1]);"><input class=codename readonly=true name=ManageComName id=ManageComName elementtype=nacessary>
    </TD>
  </TR>  
  <TR  class= common >
    <TD  class= title5>
      Э����ǩ������
    </TD>
		<td class="input5">
			<!--<input class="coolDatePicker" dateFormat="short" elementtype=nacessary name="ValidityDate" verify="ǩ������|notnull&DATE" >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#ValidityDate'});" verify="ǩ������|notnull&DATE" dateFormat="short" name=ValidityDate id="ValidityDate"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
    <TD  class= title5>
      ״̬
    </TD>
    <TD  class= input5>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=HosState id=HosState verify="״̬|notnull&code:HosState"  ondblclick="return showCodeList('HosState',[this,HosStateName],[0,1]);" onclick="return showCodeList('HosState',[this,HosStateName],[0,1]);" onkeyup="return showCodeListKey('HosState', [this,HosStateName],[0,1]);"><input class=codename readonly=true name=HosStateName id=HosStateName elementtype=nacessary>
    </TD>

  </TR>
</table>
<table class= common>
	 <TR  class= common>
	 	<TD  class= title5>
      ��ϸ��ַ
    </TD>
	 </TR>
	 	<TR colspan="4">
     <td class="title">
          <textarea class="common" rows="4" cols="169" name="Address" id="Address" value=""></textarea>
     </td> 
  </TR>
  <TR  class= common>
	 	<TD  class= title>
      ��ע
    </TD>
	 </TR>
	 	<TR  class= common>
     <td class="title">
          <textarea class="common" rows="4" cols="169" name="Remark" value=""></textarea>
     </td> 
  </TR>
</table>
</div>
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="HospitalCode1" name="HospitalCode1"> <br>
     <%@include file="../common/jsp/OperateButton.jsp"%>
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
