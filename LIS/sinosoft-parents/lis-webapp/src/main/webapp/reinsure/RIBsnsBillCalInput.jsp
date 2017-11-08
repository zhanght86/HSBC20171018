<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %> 

<head>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
	<SCRIPT src="RIBsnsBillCalInput.js"></SCRIPT> 
	<%@include file="RIBsnsBillCalInit.jsp"%>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css> 
</head>

<body onload="initForm();initElementtype();">    
  <form action="" method=post name=fm target="fraSubmit" >
    <div style="width:200">
      <table class="common">
        <tr class="common">
          <td class="common"><img  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCessGetData);"></td>
          <td class="titleImg">账单数据生成</td>
        </tr>
      </table>
  	</div>
    <br>
    <Div  id= "divCessGetData"  style= "display: ''" >
		<table class= common border=0 width=100%>
			<TR  class= common>
		    	<TD  class= title5 >再保公司</TD>
		        <TD  class= input5 > 
		        	<Input class="codeno" name="RIComCode"  verify="再保公司|NOTNULL"
				      ondblClick="showCodeList('riincompany',[this,RIComName],[0,1],null,null,null,1,250);"
				      onkeyup="showCodeListKey('',[this,RIComName],[0,1],null,null,null,1,250);" readonly="readonly" verify="再保公司|NOTNULL" ><Input 
				      class= codename name= 'RIComName' readonly="readonly" elementtype=nacessary>
		        </TD>
		        <td class="title5">计算起期</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"
					dateFormat="short" name="StartDate"  id="StartDate">		<span class="icon"><a onClick="laydate({elem: '#StartDate'});">		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
			</TR>
			<TR  class= common>
				<td class="title5">计算止期</td>
				<td class="input5"><input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});"					dateFormat="short" name="EndDate"  id="EndDate">		<span class="icon"><a onClick="laydate({elem: '#EndDate'});">
		<img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
		      	<TD  class= title5 >账单类型</TD>
		        <TD  class= input5 > 
		        	<Input class="codeno" name="BillType"  verify="账单类型|NOTNULL"
				      ondblClick="showCodeList('ribilltype',[this,BillTypeName],[0,1],null,null,null,1,250);"
				      onkeyup="showCodeListKe('ribilltype',[this,BillTypeName],[0,1],null,null,null,1,250);" readonly="readonly"><Input 
				      class= codename name="BillTypeName" readonly="readonly" elementtype=nacessary>
		        </TD>
			</TR>
	    </table>
	    <br>
	    <hr>
	    <br>	    	   
		<INPUT class=cssButton  VALUE="账单数据生成" TYPE=button onClick="StatisticData();">
		<INPUT class=cssButton  VALUE="重  置" TYPE=button onClick="ResetForm();">				
	</Div>
	<br>	 
    <input type="hidden" name=OperateType value="">
    <input type="hidden" name=BillTimes value="">
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
