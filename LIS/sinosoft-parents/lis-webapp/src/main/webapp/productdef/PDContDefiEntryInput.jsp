<%@include file="../i18n/language.jsp"%>


<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>

<%
 //程序名称：PDContDefiEntryInput.jsp
 //程序功能：契约信息定义入口
 //创建日期：2009-3-13
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
%>

<head>
<%
	//=============================================================BGN
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String tPdFlag = request.getParameter("pdflag"); //显示标记0只读1可修改

	//=============================================================END
%>

  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
  <SCRIPT src="SpeedProgressConvert.js"></SCRIPT>
  <script src="PDCommonJS.js"></script>
 <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
 <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="buttonshow.jsp"%> 
 <SCRIPT src="PDContDefiEntry.js"></SCRIPT>

 <%@include file="PDContDefiEntryInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();initElementtype();">
<form action="./PDContDefiEntrySave.jsp" method=post name=fm target="fraSubmit">

<table>
  <tr>
    <td class="titleImg" >产品基础信息：</td>
  </tr>
</table>
<table class=common>
	<tr class=common>
		<td class=title5>产品险种代码</td>
		<td class=input5>
			<input class=common name="RiskCode" readonly="readonly" >
		</td>
		<td class=title5>申请日期</td>
		<td class=input5>
			<input class="multiDatePicker" dateFormat="short" name="RequDate" readonly="readonly" >
		</td>
	</tr>
</table>
<input value="险种基础信息查看" type=button  onclick="button117( )" class="cssButton" type="button" >
<table>
  <tr>
    <td class="titleImg" >产品责任信息：</td>
  </tr>
</table>
<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanMulline9Grid" >
     </span> 
      </td>
   </tr>
</table>
<INPUT CLASS=cssbutton VALUE="首  页" TYPE=button onclick="Mulline9GridTurnPage.firstPage();"> 
<INPUT CLASS=cssbutton VALUE="上一页" TYPE=button onclick="Mulline9GridTurnPage.previousPage();">      
<INPUT CLASS=cssbutton VALUE="下一页" TYPE=button onclick="Mulline9GridTurnPage.nextPage();"> 
<INPUT CLASS=cssbutton VALUE="尾  页" TYPE=button onclick="Mulline9GridTurnPage.lastPage();">
</BR></BR>
<div id = 'riskPropId2' style=''> 
<table>
  <tr>
    <td class="titleImg" >保障计划要素</td>
  </tr>
</table>

<table  class= common>
   <tr  class= common>
      <td style="text-align:left;" colSpan=1>
     <span id="spanLMDutyParamGrid" >
     </span> 
      </td>
   </tr>
</table>
</div>
<!--  <div id = 'riskProp2' style=''>  -->
<div id = 'riskPropId'> 
<table>
  <tr>
    <td class="titleImg" >责任层级定义</td>
  </tr>
</table>
<!--
<input value="费率表定制" type=button  onclick="button100( )" class="cssButton" type="button" >

<input value="定义责任缴费算法" type=button onclick="button101( )" class="cssButton" type="button" >
<input value="缴费字段控制定义" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" >
<input value="给付字段控制定义" type=button id="btnGetFieldCtrl" onclick="button103( )" class="cssButton" type="button" >
<input value="险种责任加费定义" type=button  onclick="button104( )" class="cssButton" type="button" >
  -->

 <input value="保障计划约定要素" type=button  onclick="button105( )" class="cssButton" type="button" >  

</div>
<table>
  <tr>
    <td class="titleImg" >险种层级定义</td>
  </tr>
</table>

<input value="录入信息控制定义" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" >
<input value="险种各角色定义" type=button  onclick="button106( )" class="cssButton" type="button" >

<input value="险种界面定义" type=button id="btnRiskFace" onclick="buttonRiskFace( )" class="cssButton" type="button" ><!--  -->
<!-- <input value="险种缴费属性定义" type=button  onclick="button108( )" class="cssButton" type="button" >   -->
<!--  <input value="险种缴费间隔定义" type=button  onclick="button109( )" class="cssButton" type="button" >    -->
<!-- input value="账户型险种定义" type=button id="btnRiskAcc" onclick="button116( )" class="cssButton" type="hidden"> -->

<!--<input value="账户型险种定义" type=button id="btnRiskAcc" onclick="button116( )" class="cssButton">-->
<input value="险种分类定义" type=button  onclick="button82( )" class="cssButton" type="button" >
<input value="险种销售机构控制" type=button  onclick="button114( )" class="cssButton" type="button" >
<input value="险种责任加费定义" type=button  onclick="button104( )" class="cssButton" type="button" >
<!--<input value="险种投保规则" type=button  onclick="button110( )" class="cssButton" type="button" >-->
<input value="险种核保规则" type=button  onclick="button111( )" class="cssButton" type="button" >
<input value="险种公共核保规则" type=button  onclick="pubUWRule( )" class="cssButton" type="button" >
<input value="佣金比例定义" type=button  onclick="commission()" class="cssButton" type="button" >

<input value="员工折扣比例定义" type=button  onclick="staffrateDefi()" class="cssButton" type="button" >

<input value="字段参数定义" type=button  onclick="button127( )" class="cssButton" type="button"  id=definedparams >
<!-- modify by pangyingjie 2015/07/03 -->
<!--  <input value="险种名称配置" type=button  onclick="button128()" class="cssButton" type="button"  id=risknameconfiguration >-->
<!-- modify by baos 2015/07/03 -->
<input type = "hidden" value="" type=button  onclick="button130( )" class="cssButton" type="button">

<div id='sugDefButtons'>
<table>
  <tr>
    <td class="titleImg" >建议书相关数据定义</td>
  </tr>
</table>

<input value="险种界面定义" type=button  onclick="buttonSugFace( )" class="cssButton" type="button" >
<input value="收益数据定义" type=button  onclick="buttonIncome( )" class="cssButton" type="button" >
<!-- input value="险种静态文件定义" type=button  onclick="buttonStatic( )" class="cssButton" type="button" -->
<input value="险种利率参数定义" type=button  onclick="buttonRate( )" class="cssButton" type="button" >
<div>
<!--<input value="主附险组合定义" type=button  onclick="button115( )" class="cssButton" type="button" >--> 
<!--input value="险种条款打印定义" type=button  onclick="button107( )" class="cssButton" type="button" -->
<!--input value="险种页面录入配置" type=button id="btnPayFieldCtrl" onclick="button102( )" class="cssButton" type="button" -->

<hr>
<input value="问题件回复/查询" type=button  onclick="IssueQuery( )" class="cssButton" type="button" >
<input id=savabutton3 value="问题件录入" type=button  onclick="IssueInput( )" class="cssButton" type="button" >
<input value="记事本信息" onclick="IssueQuery( )" class="cssButton" type="hidden" >
<hr>
<!--  
<input value="下一步" type=button  onclick="button122( )" class="cssButton" type="button" >-->
<input id=savabutton1 value="契约信息录入完毕" type=button  onclick="button119( )" class="cssButton" type="button" >
<input id=savabutton2 value="简易产品录入完毕" type=button  onclick="button121( )" class="cssButton" type="button" >
<input value="返  回" type=button  onclick="returnParent( )" class="cssButton" type="button" >
<br><br>

<input type=hidden name="operator">
<input type=hidden name="IsDealWorkflow">
<input type=hidden name="tableName" value="LMRisk">
<input type=hidden name="SimpleContPara">
<input type=hidden name=MissionID>
<input type=hidden name=SubMissionID>
<input type=hidden name=ActivityID>
<input type=hidden name=IsReadOnly>
<input type=hidden name=ContOpt>
<input type=hidden name=PdFlag>
<input type=hidden name=PageNo value=101>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
