<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%> 
<%
GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");
 //程序名称：ConfigVATRiskGrpInput.jsp
 //程序功能：险种组界面
 //创建日期：
 //创建人  ：
 //更新记录：  更新人    更新日期     更新原因/内容
  
%>
<script>

</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
 <SCRIPT src="../common/javascript/jquery-1.7.2.js"></SCRIPT> 
 
  <SCRIPT src="./ConfigVATRiskGrp.js"></SCRIPT>
 

	<SCRIPT src="../common/javascript/jquery.easyui.min.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">

<%@include file="./ConfigVATRiskGrpInit.jsp"%>

<title>增值税率配置</title>
<script language="javascript">
	
	/* var intPageWidth=screen.availWidth;
	var intPageHeight=screen.availHeight;
	window.moveTo(-1, -1);
	window.resizeTo(intPageWidth,intPageHeight); 
	//window.focus();
	window.onunload = afterInput;
	

function afterInput() {
	//alert('close');
	try {
	
}
	catch(e) {}
	window.focus();
} 
 */

</script>

</head>

 <body onload="initForm();initElementtype();">
 <form action="./ConfigVATRiskGrpSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id="NativeConfig" height=500>  
    <table>
    <tr>
      <td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMul);">
    	</td>
      <td class= titleImg>
    	        险种组信息
      </td>
    </tr>
    </table>
    <Div  id= "divMul" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanRiskGrpGrid" ></span>
	       	</td>
	      </tr>
     </table>  
  </div>
<br>

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
			<td CLASS="title5">险种编码</td>
			<td CLASS="input5">
				 <Input class="common" name="RiskCode"  id="RiskCode" 
               style="background:url(../common/images/select--bg_03.png)	no-repeat right center" 
               onclick="return showCodeList('RiskCodeAPP',[this],[0,1],null,null,null,true);"
               onDblClick="return showCodeList('RiskCodeAPP',[this],[0,1],null,null,null,true);" 
               onKeyUp="return showCodeListKey('RiskCodeAPP',[this],[0,1],null,null,null,true);">
               <font id="StartDaten" color="red">&nbsp;*</font>
               
			</td>
			
			<td CLASS="title5">险种组</td>
			<td CLASS="input5">
				<input NAME="RiskGrpCode" id="RiskGrpCode" class="common" elementtype=nacessary verify="险种组编码|notnull" >
				<font id="StartDaten" color="red">&nbsp;*</font>
			</td>	
			</tr>
			<tr CLASS="common"> 
			  <td CLASS="title5"></td>
			 <td CLASS="input5"><input type=hidden  id="RecordID" name="RecordID"  >
			 </td>
              </tr>
			
		
	</table>
</div>
	<input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
</Div>
       
        
<a href="javascript:void(0);" class="button"onClick="newClick()">新  建</a>
<a href="javascript:void(0);" class="button"onClick="updateClick()">修  改</a>
<!-- <a href="javascript:void(0);" class="button"onClick="updateClick()">复  制</a> -->
<a href="javascript:void(0);" class="button"onClick="deleteClick()">删  除</a>


  <br><br><br><br>      

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 </body>
</html>

