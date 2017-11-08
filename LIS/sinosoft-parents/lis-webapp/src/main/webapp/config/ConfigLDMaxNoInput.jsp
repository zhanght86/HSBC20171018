<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2005-01-26 13:18:16
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
 <SCRIPT src="../common/javascript/jquery-1.7.2.js"></SCRIPT> 

	<SCRIPT src="../common/javascript/jquery.easyui.min.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <%@include file="ConfigLDMaxNoInit.jsp"%>
  <SCRIPT src="ConfigLDMaxNoInput.js"></SCRIPT>
</head>
<body  onload="initForm();initElementtype();" >
 
  <form action="./ConfigLDMaxNoSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id="NativeConfig" height=500>
  <div class="maxbox1">
   <table  class=common border=0>
		<tr class="common">
			
				<td CLASS="title5">号段编码</td>
			<td CLASS="input5">
				<input name="NoCodeQuery" id="NoCodeQuery"  class="common wid" >
			</td>
			<td class="title5">号段名称</td>
			<td class="input5">
				<input class="common wid" name="NoNameQuery" id="NoNameQuery">
			</td>
			
		</tr>
	</table></div>
	<INPUT class=cssButton name="addbutton" VALUE="查    询"  TYPE=button onclick="query();">
	<INPUT class=cssButton name="addbutton" VALUE="刷    新"  TYPE=button onclick="initForm();">
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    		</td>
    		 <td class= titleImg>
        		 号段格式配置
       		 </td>   		 
    	</tr>
    </table>
		 <div id="divMulCalendar" style="display:''">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanMaxNoGrid">
                    </span>
                </td>
            </tr>
        </table>
    </div>	

     
	
		 <table>
    	<tr>
    		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    		 <td class= titleImg>
        		 详细信息配置
       		 </td>   		 
    	</tr>
    </table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
	<table class="common" id="table2">
		<tr CLASS="common">
			<td CLASS="title5">号段编码</td>
			<td CLASS="input5" COLSPAN="1">
				<input name="NoCode" id="NoCode"  class="common wid" elementtype=nacessary verify="号段编码|notnull">
			</td>
			<td class="title5">号段名称</td>
			<td class="input5">
				<input class="common wid" elementtype=nacessary name="NoName" id="NoName" verify="号段名称|notnull"></td></tr>
             <tr CLASS="common">   
			<td CLASS="title5">规则预览</td>
			<td CLASS="input5">
				<input NAME="ShowRule" id="ShowRule" class="common wid" readonly >
			</td>	
            <td CLASS="title5">起期</td>
			<td CLASS="input5">
				
                <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});"  elementtype=nacessary verify="起期|notnull&Date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</td>
		</tr>
		<tr class="common"> 
			
			<td class="title5">止期</td>
			<td class="input5">
				
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			<td CLASS="title5"></td>
			<td CLASS="input5">
				<inputclass="common" readonly >
			</td>	
		</tr>
	</table>
</div>	
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
    
 <div id="w" class="easyui-window" title="号段生成规则配置" iconCls="icon-save" style="width:850px;height:450px;padding:0px;">
				 <!--iframe id= "IbrmsDetail" src="./ConfigLDMaxNoRule.jsp" width=100% height=500 SCROLLING='No' >
				 </iframe--> 
</div>
	
</Div>
    <INPUT class=cssButton name="addbutton" VALUE="保    存"  TYPE=button onclick="return submitForm();">
	<INPUT class=cssButton name="addbutton" disabled VALUE="删    除"  TYPE=button onclick="return submitForm();">
	<INPUT class=cssButton name="addbutton" VALUE="号段规则配置"  TYPE=button onclick="return configRule();">
	<INPUT class=cssButton name="addbutton" VALUE="号段约束配置"  TYPE=button onclick="return configRuleLimit();">
  </form>
  <br /><br /><br /><br />
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
