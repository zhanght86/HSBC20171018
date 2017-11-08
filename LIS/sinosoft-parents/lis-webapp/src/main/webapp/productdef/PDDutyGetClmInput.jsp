<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDDutyGetClmInput.jsp
 //程序功能：责任给付生存
 //创建日期：2009-3-16
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
    GlobalInput tGI = new GlobalInput();
 tGI = (GlobalInput)session.getAttribute("GI");
 String tPdFlag=request.getParameter("pdflag");
%>

<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="DictionaryUtilities.js"></SCRIPT>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
 <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
 <script src="../common/javascript/jquery.js"></script>
 <script src="../common/javascript/jquery.easyui.min.js"></script> 
  <%@include file="buttonshow.jsp"%>
 <SCRIPT src="PDDutyGetClm.js"></SCRIPT>
 <%@include file="PDDutyGetClmInit.jsp"%>
   <script>
	
	var mOperator = "<%=tGI.Operator%>";   //记录操作员

</script>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDDutyGetClmSave.jsp" method=post name=fm target="fraSubmit">
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class="common" name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>给付代码</td>
		<td class=input5>
			<input class="common" name="GetDutyCode" readonly="readonly" >
		</td>
	</tr>
</table>
<table>
  <tr>
    <td class="titleImg" >已有责任给付赔付项</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline10Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline10GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline10GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline10GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline10GridTurnPage.lastPage();">
</BR></BR>
<div id = 'relaIssueId' style = 'display:none'>
	<input value="关联账单定义" type=button  onclick="button246( )" class="cssButton" type="button" id="relaIssueId"></div>

<table>
  <tr>
    <td class="titleImg" >责任给付赔付属性定义</td>
  </tr>
</table>
<table  class= common>
	<tr class= common>
        <!--TD  class= title>给付代码</TD>
        <TD  class= input>
						<Input class=common name=GETDUTYCODE verify="给付代码|NOTNUlL&LEN<=6" >
        </TD> 
        <TD  class= title>给付名称</TD>
        <TD  class= input>
						<Input class=common name=GETDUTYNAME verify="给付名称|NOTNUlL" >
        </TD--> 
        <TD  class= title>理赔类型</TD>
        <TD  class= input>
						<Input class=common name=GETDUTYKIND verify="理赔类型|NOTNUlL" ><font size=1 color='#ff0000'><b>*</b></font>
        </TD> 
        <Input type=hidden name=INPFLAG readonly="readonly"  ondblclick="return showCodeList('pd_inpflag',[this,INPFLAGName],[0,1]);" onkeyup="return showCodeListKey('pd_inpflag',[this,INPFLAGName],[0,1]);"><input type=hidden name=INPFLAGName readonly="readonly">
         <TD  class= title>
赔付类型
        </TD>
        <TD  class= input>
           <Input class="codeno" name=STATTYPE readonly="readonly" verify="赔付类型|NOTNUlL&CODE:pd_stattype" ondblclick="return showCodeList('pd_stattype',[this,STATTYPEName],[0,1]);" onkeyup="return showCodeListKey('pd_stattype',[this,STATTYPEName],[0,1]);"><input class=codename name=STATTYPEName readonly="readonly"><font size=1 color='#ff0000'><b>*</b></font>
        </TD>
    	
        <TD  class= title>
算法编码
        </TD>
        <TD  class= input>
          <input class=common  class=input5  name="CALCODE" verify="算法编码|LEN>=6">
        </TD>
    	</tr>
    	<tr class= common>
      
        <TD  class= title>
观察期(天)
        </TD>
        <TD  class= input>
          <input class=common   name=ObsPeriod>
        </TD>
        <Input type=hidden name=DeadToPValueFlag value ='N'><input type=hidden name=DeadToPValueFlagName readonly="readonly">
    	 <TD  class= title>
每日住院给付标记
        </TD>
        <TD  class= input>
          <Input class="codeno" name=GetByHosDay  verify="每日住院给付标记|CODE:pd_getbyhosday" readonly="readonly" ondblclick="return showCodeList('pd_getbyhosday',[this,GetByHosDayName],[0,1]);" onkeyup="return showCodeListKey('pd_getbyhosday',[this,GetByHosDayName],[0,1]);"><input class=codename name=GetByHosDayName readonly="readonly"><!-- <font size=1 color='#ff0000'><b>*</b></font>--> 
        </TD>
    	<TD  class= title>
给付后动作
        </TD>
        <TD  class= input>
          <Input class="codeno" name=AfterGet readonly="readonly" verify="给付后动作|NOTNUlL&CODE:pd_afterget" ondblclick="return showCodeList('pd_afterget',[this,AfterGetName],[0,1]);" onkeyup="return showCodeListKey('pd_afterget',[this,AfterGetName],[0,1]);"><input class=codename name=AfterGetName readonly="readonly"><font size=1 color='#ff0000'><b>*</b></font>
        </TD>
    	
    	</tr>
    	<!--  <tr class=common>
    		 <TD  class= title>
          账单录入标记
        </TD>
        <TD  class= input>
           <Input class="codeno" name=INPFLAG readonly="readonly"  ondblclick="return showCodeList('pd_inpflag',[this,INPFLAGName],[0,1]);" onkeyup="return showCodeListKey('pd_inpflag',[this,INPFLAGName],[0,1]);"><input class=codename name=INPFLAGName readonly="readonly">
        </TD>
        <TD  class= title>
          被保人死亡后有效标记
        </TD>
        <TD  class= input>
           <Input class="codeno" name=DeadValiFlag readonly="readonly" ondblclick="return showCodeList('pd_deadvaliflag',[this,DeadValiFlagName],[0,1]);" onkeyup="return showCodeListKey('pd_deadvaliflag',[this,DeadValiFlagName],[0,1]);"><input class=codename name=DeadValiFlagName readonly="readonly">
        </TD>
         <Input type="hidden" name=DeadValiFlag readonly="readonly" ondblclick="return showCodeList('pd_deadvaliflag',[this,DeadValiFlagName],[0,1]);" onkeyup="return showCodeListKey('pd_deadvaliflag',[this,DeadValiFlagName],[0,1]);"><input type="hidden"  name=DeadValiFlagName readonly="readonly">
        <TD  class= title>
          死亡给付与现值关系
        </TD>
        <TD  class= input>
           <Input class="codeno" name=DeadToPValueFlag readonly="readonly" ondblclick="return showCodeList('pd_yesno',[this,DeadToPValueFlagName],[0,1]);" onkeyup="return showCodeListKey('pd_yesno',[this,DeadToPValueFlagName],[0,1]);"><input class=codename name=DeadToPValueFlagName readonly="readonly">
        </TD>
    	</tr>-->
</table>
<!--算法定义引用页-->
<jsp:include page="CalCodeDefMain.jsp"/>
<hr>
<div align=left id=savabuttonid><input value="查询给付赔付库" type=hidden  onclick="queryDutyGetClmLib()" class="cssButton" type="button" >
<input value="重  置" type=button  onclick="initGetClaimDetail()" class="cssButton" type="button" >
<input value="保  存" type=button  onclick="save()" class="cssButton" type="button" >
<input value="修  改" type=button  onclick="update()" class="cssButton" type="button" >
<input value="删  除" type=button  onclick="del()" class="cssButton" type="button" ></div>
<div align=left id=checkFunc>
<input value="查看算法内容" type=button  onclick="InputCalCodeDefFace2()" class="cssButton" type="button" >
</div>
</BR></BR>
<!--input value="进入算法定义" type=button  onclick="button245( )" class="cssButton" type="button" -->

<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="tableName" value="PD_LMDutyGetClm">
<input type=hidden name="tableName2" value="PD_LMDutyGetClm_Lib">
<input type=hidden name=IsReadOnly>
<input type=hidden name=getDutyCode2>
<input type=hidden name=dutyType2 value=4>
<input type=hidden name=PdFlag>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
