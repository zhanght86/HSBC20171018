<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ScanContInput.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人        更新日期     更新原因/内容
//            liuxiaosong   2006-10-30   保全“人工核保-投保单位基本情况”
%>
<html>
<%
  
	String tContNo = request.getParameter("GrpContNo");
	String tRiskcode=request.getParameter("riskcode");
	String tBusinessType=request.getParameter("businesstype"); //add by liu xiaosong 2006-10-30,便于保全人工核保时复用！
%>
<script>	
	var contNo = "<%=tContNo%>";   
	var riskcode= "<%=tRiskcode%>";  
//=======add=======liuxiaosong========2006-10-30==start======	
	var businesstype= "<%=tBusinessType%>";	
//=======add=======liuxiaosong========2006-10-30==end======
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="GrpRiskElenemtMain.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  


  <%@include file="GrpRiskElenemtMainInit.jsp"%>
  <title>风险要素调整</title>
</head>
<body  onload="initForm();" >
  <form action="./GrpRiskElenemtSave.jsp" id="fm" method=post name=fm target="fraSubmit">

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
   
     <div class="maxbox1">
    <table class="common">

		<tr CLASS="common">
			<td CLASS="title">调整比例 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input id="coorate1" NAME="coorate1" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20" onblur="showTotalCoorate()">
		  </td>
		  <td></td><td></td>
		</tr>
	</table>
	 </div>
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
    <div class="maxbox1">
    <table class="common">

		<tr CLASS="common">
			<td CLASS="title">调整比例 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input id="coorate2" NAME="coorate2" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20" onblur="showTotalCoorate()">
		  </td>
		  <td></td><td></td>
		</tr>
	</table>
	</div>
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
    <div class="maxbox1">
    <table class="common">

		<tr CLASS="common">
			<td CLASS="title">调整比例 
    	</td>
			<td CLASS="input" COLSPAN="1">
			  <input id="coorate3" NAME="coorate3" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20" onblur="showTotalCoorate()">
		  </td>
		  <td></td><td></td>
		</tr>
	</table>
	</div>
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
 <div class="maxbox">
 <table class="common">

		<tr CLASS="common">
			<td CLASS="title5">投保单位性质</td>
			<td CLASS="input5" COLSPAN="1">
				<input class=codeno id="BusinessType" name=BusinessType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);" onkeyup="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null,300);"><input class=codename id="BusinessTypeName" name=BusinessTypeName readonly=true elementtype=nacessary>
		    </td>
		    <td CLASS="title5">调整比例</td>
        	<td CLASS="input5" COLSPAN="1">
        	    <input id="coorate4" NAME="coorate4" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20" onblur="showTotalCoorate()">
            </td>
    
		</tr>
		<tr>
			<td CLASS="title5">既往理赔率 
        	</td>
        	<td CLASS="input5" COLSPAN="1">
        	    <input id="orirate" NAME="orirate" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20">
            </td>
            <td CLASS="title5">调整比例 
            	</td>
            	<td CLASS="input5" COLSPAN="1">
            	<input id="coorate5" NAME="coorate5" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20" onblur="showTotalCoorate()">
            </td>
	    </tr>
        <tr CLASS="common">
        	<td CLASS="title5">上年/平均赔付率</td>
        	<td CLASS="input5" COLSPAN="1">
        	<input id="coorate6" NAME="coorate6" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20" onblur="showTotalCoorate()">
            </td>
            <td CLASS="title5" COLSPAN="1" align="right">综合调整比例</td>
            <td CLASS="input5" COLSPAN="1">
            	<input id="coorate7" NAME="coorate7" VALUE CLASS="common wid" TABINDEX="-1" MAXLENGTH="20" readonly>
            </td>
            <!-- <td COLSPAN="2"></td> -->
        </tr>
        <tr>
            <td COLSPAN="2"></td>
        </tr>
	</table> 
  </div>
	<div id="divSaveRate" style="display:''" align=left>
        <input value="    保存    " class="cssButton"  TYPE=button onclick="cooratesave();">
    </div>

	<input type=hidden id="Risk" name="Risk" value="<%=request.getParameter("riskcode")%>">
  <input type=hidden id="GrpCont" name="GrpCont" value="<%=request.getParameter("GrpContNo")%>">
  <input type=hidden id="GrpPolNo" name="GrpPolNo" value="<%=request.getParameter("GrpPolNo")%>">
  <input type=hidden id="PrtNo" name="PrtNo" value="<%=request.getParameter("PrtNo")%>">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
