<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期： 
//创建人  ：ck
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tContNo = "";
	String tPolNo = "";
	String tIsCancelPolFlag = "";
	String tRiskCode = "";
	try
	{
	    tContNo = request.getParameter("ContNo");
		tPolNo = request.getParameter("PolNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
		tRiskCode = request.getParameter("RiskCode");
	}
	catch( Exception e )
	{
		tContNo = "";
		tPolNo = "";
		tIsCancelPolFlag = "";
	}
	String tInsuredName = "";
	try
	{
		tInsuredName = request.getParameter("InsuredName");
		tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"),"GBK");
		loggerDebug("LCInsuAccQuery","sxy--sxy : "+tInsuredName );
	}
	catch( Exception e )
	{
		tInsuredName = "";
	}
	String tAppntName = "";
	try
	{
		tAppntName = request.getParameter("AppntName");
		tAppntName = new String(tAppntName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tAppntName = "";
	}
%>
<head>
<script> 
	var tContNo = "<%=tContNo%>";
	var tPolNo = "<%=tPolNo%>";
	var tRiskCode = "<%=tRiskCode%>";
	var tInsuredName = "<%=tInsuredName%>";
	var tAppntName = "<%=tAppntName%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="LCInsuAccQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LCInsuAccQueryInit.jsp"%>
	<title>账户轨迹查询 </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id="fm" >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				保单信息
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" class="maxbox1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            保单号码
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ContNo id="ContNo" >
          </TD>
           <TD  class= title>
            险种号码
          </TD>
          <TD  class= input>
          	<Input class= "readonly wid" readonly name=PolNo id="PolNo" >
          </TD>
         <TD  class= title>
            险种编码
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=RiskCode id="RiskCode" >
          </TD>
		</TR>
		 <TR  class= common>
          <TD  class= title>
            被保人姓名
          </TD>
          <TD  class= input>
          	<Input class= "readonly wid" readonly name=InsuredName id="InsuredName" >
          </TD>
          <TD  class= title>
            投保人姓名
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=AppntName id="AppntName" >
          </TD>
           <TD  class= title>
          </TD>
          <TD  class= input>
          </TD>
     </TR>
      <TR  class= common>
      	
         <td class="title">投资帐户号码</td>
         <!-- <td class="input"><input class=codeno name=InsuAccNo ondblclick="return showCodeList('insuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');" onkeyup="return showCodeListKey('insuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');"><input class=codename name=InsuAccNoName readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></td>-->
         <td class="input"><input class=codeno name=InsuAccNo id="InsuAccNo" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('findinsuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');"  ondblclick="return showCodeList('findinsuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');" onKeyUp="return showCodeListKey('findinsuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');"><input class=codename name=InsuAccNoName id="InsuAccNoName" readonly=true elementtype=nacessary></td>
        <td class="title">缴费帐户号码</td>
         <!-- <td class="input"><input class=codeno name=InsuAccNo ondblclick="return showCodeList('insuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');" onkeyup="return showCodeListKey('insuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');"><input class=codename name=InsuAccNoName readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></td>-->
         <td class="input"><input class=codeno name=Payplancode id="Payplancode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onClick="return showCodeList('payplancodewithrisk',[this,PayplancodeName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');"  ondblclick="return showCodeList('payplancodewithrisk',[this,PayplancodeName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');" onKeyUp="return showCodeListKey('payplancodewithrisk',[this,PayplancodeName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');"><input class=codename name=PayplancodeName id="PayplancodeName" readonly=true elementtype=nacessary></td>

      </TR>	
        <!--
	<TR  class= common>	
	 <TD  class= title>
          投资帐户号码
          </TD>
          <TD  class= input>
           <input class=codeno name=InsuAccNo ondblclick="return showCodeList('findinsuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');" onkeyup="return showCodeListKey('findinsuacc',[this,InsuAccNoName],[0,1],null,[fm.RiskCode.value],['riskcode'],1,'500');"><input class=codename name=InsuAccNoName readonly=true elementtype=nacessary>
          </TD>
	</TR>
	-->
     </table>
  </Div>
  
 
  	<INPUT class=cssButton VALUE="查 询" TYPE=button onClick="QueryClassAndTrace();">
  <br></br>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAcc);">
    		</td>
    		<td class= titleImg>
    			保险账户信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCInsureAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>					
  	</div>
  
<INPUT class=cssButton VALUE=" 查询详细信息 " TYPE=hidden onClick="showAccDetail();">
      
   	<Div  id= "divLCInsureAccClass" style= "display: ''">	
  	    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGridClass);">
    		</td>
    		<td class= titleImg>
    			 保险账户分类
    		</td>
    	</tr>
    </table>
    <Div  id= "divPolGridClass" style= "display: ''"  align = left>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGridClass" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage2.lastPage();"> 					
	   </center>
   </div>
 </div>
 
	<Div  id= "divLCInsureAccTrace" style= "display: ''" >
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGridTrace);">
    		</td>
    		<td class= titleImg>
    			 保险账户轨迹信息
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divPolGridTrace" style= "display: ''" align=left>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGridTrace" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
  	<!--<div id="divturnPage3" align="center" style="display:'none'">-->
    <center>
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage3.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage3.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage3.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage3.lastPage();"> 
	</center>	
		
	</div>					
  	</div>
  	
  <br>
  <!--
  <Div  id= "divLOArrearage" style= "display: ''" >
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLOArrearageGrid);">
    		</td>
    		<td class= titleImg>
    			 保单欠费信息
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divLOArrearageGrid" style= "display: ''" align=left>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanLOArrearageGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
  	<!--<div id="divturnPage4" align="center" style="display:'none'">-->
	<!--
		<INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage4.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage4.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage4.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage4.lastPage();"> 
		
		-->
	</div>					
  	</div>

  	<BR> <BR>
	<INPUT class=cssButton VALUE=" 返回 " TYPE=button onClick="returnParent();">
  </form>
  <Br> <Br> <Br> <Br> <Br>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


