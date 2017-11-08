<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2005-01-24 18:15:01
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
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
        		 体检医院基本信息
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divLDUWUser1" style= "display: ''" class="maxbox">
<table  class= common  >
  <TR  class= common>
    <TD  class= title5>
      体检医院编码
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=HospitalCode id=HospitalCode elementtype=nacessary  verify="体检医院编码|notnull&len<=20">
    </TD>
    <TD  class= title5>
      体检医院名称
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=HospitalName id=HospitalName elementtype=nacessary  verify="体检医院名称|notnull">
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title5>
      体检医院级别
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=HospitalGrade id=HospitalGrade  verify="体检医院级别|code:HospitalGrade"
        ondblclick="return showCodeList('HospitalGrade',[this,HospitalGradeName],[0,1],null,null,null,1);"  onclick="return showCodeList('HospitalGrade',[this,HospitalGradeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('HospitalGrade', [this,HospitalGradeName],[0,1],null,null,null,1);"><input class=codename readonly=true name=HospitalGradeName id=HospitalGradeName >
    </TD>
    <TD  class= title5>
      所属管理机构
    </TD>
    <TD  class= input5>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=ManageCom id=ManageCom verify="所属管理机构|notnull&len<=8"  ondblclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);"onclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('CodeCode', [this,ManageComName],[0,1]);"><input class=codename readonly=true name=ManageComName id=ManageComName elementtype=nacessary>
    </TD>
  </TR>  
  <TR  class= common >
    <TD  class= title5>
      协议书签署日期
    </TD>
		<td class="input5">
			<!--<input class="coolDatePicker" dateFormat="short" elementtype=nacessary name="ValidityDate" verify="签署日期|notnull&DATE" >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#ValidityDate'});" verify="签署日期|notnull&DATE" dateFormat="short" name=ValidityDate id="ValidityDate"><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>
    <TD  class= title5>
      状态
    </TD>
    <TD  class= input5>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=HosState id=HosState verify="状态|notnull&code:HosState"  ondblclick="return showCodeList('HosState',[this,HosStateName],[0,1]);" onclick="return showCodeList('HosState',[this,HosStateName],[0,1]);" onkeyup="return showCodeListKey('HosState', [this,HosStateName],[0,1]);"><input class=codename readonly=true name=HosStateName id=HosStateName elementtype=nacessary>
    </TD>

  </TR>
</table>
<table class= common>
	 <TR  class= common>
	 	<TD  class= title5>
      详细地址
    </TD>
	 </TR>
	 	<TR colspan="4">
     <td class="title">
          <textarea class="common" rows="4" cols="169" name="Address" id="Address" value=""></textarea>
     </td> 
  </TR>
  <TR  class= common>
	 	<TD  class= title>
      备注
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
