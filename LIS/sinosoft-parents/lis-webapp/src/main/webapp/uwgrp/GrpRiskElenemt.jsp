<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ScanContInput.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  
	String tContNo = request.getParameter("GrpContNo");
	String tRiskcode=request.getParameter("riskcode");
%>
<script>	
	var contNo = "<%=tContNo%>";   
	var riskcode= "<%=tRiskcode%>";  	
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="GrpRiskElenemtMain.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  


  <%@include file="GrpRiskElenemtMainInit.jsp"%>
  <title></title>
</head>
<body  onload="initForm();" >
  <form action="./GrpRiskElenemtSave.jsp" method=post name=fm target="fraSubmit">
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 投保人员职业类别分布
    		</td>
    	</tr>
    </table>
    <table class="common">

		<tr CLASS="common">
			<td CLASS="title">调整比例 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="coorate1" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20">
		  </td>
		  <td></td><td></td>
		</tr>
	</table>
  	<Div  id= "divGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      					
  	</div>
  	
  <br>
   

   
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid2);">
    		</td>
    		<td class= titleImg>
    			 投保人员年龄分布
    		</td>
    	</tr>  	
    </table>
    <table class="common">

		<tr CLASS="common">
			<td CLASS="title">调整比例 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="coorate2" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20">
		  </td>
		  <td></td><td></td>
		</tr>
	</table>
  	<Div  id= "divGrpGrid2" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>					
  	</div>
 	
   <br>
   
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid3);">
    		</td>
    		<td class= titleImg>
    			 投保人员性别分布
    		</td>
    	</tr>  	
    </table>
    <table class="common">

		<tr CLASS="common">
			<td CLASS="title">调整比例 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input NAME="coorate3" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20">
		  </td>
		  <td></td><td></td>
		</tr>
	</table>
  	<Div  id= "divGrpGrid3" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSexGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>	
  	
		</div>
 <br>
 <table class="common">

		<tr CLASS="common">
			<td CLASS="title">投保单位性质 
    	</td>
			<td CLASS="input" COLSPAN="1">
				
				<input class=codeno name=BusinessType ondblclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);" onkeyup="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);"><input class=codename name=BusinessTypeName readonly=true elementtype=nacessary>
		  </td>
		  <td CLASS="title">调整比例 
    	</td>
    	<td CLASS="input" COLSPAN="1">
    	<input NAME="coorate4" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20">
    </td>
    
		</tr>
		<tr>
			<td CLASS="title">既往理赔率 
    	</td>
    	<td CLASS="input" COLSPAN="1">
    	<input NAME="orirate" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20">
    </td>
    <td CLASS="title">调整比例 
    	</td>
    	<td CLASS="input" COLSPAN="1">
    	<input NAME="coorate5" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20">
    </td>
			</tr>
		<tr CLASS="common">
			<td CLASS="title">上年/平均赔付率 
    	</td>
    	<td CLASS="input" COLSPAN="1">
    	<input NAME="coorate6" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20">
    </td>
    <td CLASS="title">综合调整比例 
    	</td>
    	<td CLASS="input" COLSPAN="1">
    	<input NAME="coorate7" VALUE CLASS="common" TABINDEX="-1" MAXLENGTH="20" readonly>
    </td>
  </tr>
  <tr>
    <td align="right" colspan="4">
    	<input value="  保存  " class=cssButton type=button onclick="save();">
    </td>
		</tr>
	</table> 	
	<input type=hidden id="Risk" name="Risk" value="<%=request.getParameter("riskcode")%>">
  <input type=hidden id="GrpCont" name="GrpCont" value="<%=request.getParameter("GrpContNo")%>">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
