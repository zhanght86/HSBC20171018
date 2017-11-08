<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  //System.out.println("werererererrrerwer");
%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	  
	  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="LDRiskToRateReleaseInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LDRiskToRateReleaseInputInit.jsp"%>

<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="init();" onclick="" >
  <form action="LDRiskToRateReleaseInputSave.jsp" method=post name=fm target="fraSubmit">	
<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    		</TD>
    		<TD class= titleImg>
请输入查询条件
    		</TD>
    	</TR>
  </Table>
   <Div  id= "divQuery" style= "display: ''" >
    <table  class= common >
    <TR  class= common>
    	 <TD  class= title>
产品编码
         </TD>
         <TD  class= input>
	        <Input class="codeno" name=RiskCode 
	        ondblclick="return showCodeList('RiskCodeShow',[this,RiskCodeName],[0,1]);" 
	        onkeyup="return showCodeListKey('RiskCodeShow',[this,RiskCodeName],[0,1]);"><input 
	        class="codename" name="RiskCodeName" readonly="readonly" > </TD>
         <TD  class= title>  
费率类型
         </TD>
         <TD  class= input>
           <Input class="codeno" name=RateType 
	        ondblclick="return showCodeList('RateTypeShow',[this,RateTypeName],[0,1]);" 
	        onkeyup="return showCodeListKey('RateTypeShow',[this,RateTypeName],[0,1]);"><input 
	        class="codename" name="RateTypeName" readonly="readonly" >
         </TD> 
         
         
		</TR>
         
	     
 </Table>

  <Table>
  	<TR>   
       <td width="10%">&nbsp;&nbsp;</td>        
       	<TD> 
       		<INPUT VALUE="查  询" class= cssbutton TYPE=button onclick="easyQueryClick();">
       <!-- <input type="button" class=cssbutton value="&nbsp;增加&nbsp;" onclick="submitFrom()">
	        <input type="button" class=cssbutton value="&nbsp;修改&nbsp;" onclick="updateClick()">
	        <input type="button" class=cssbutton value="&nbsp;删除&nbsp;" onclick="deleteClick()"> --> 
       	 </TD> 
       	<td width="10%">&nbsp;&nbsp;</td>        
       	<TD> <INPUT VALUE="重置" class= cssbutton TYPE=button onclick="resetForm();"> </TD>      
       	<td width="10%">&nbsp;&nbsp;</td>      
       	  
       </TR>
    </Table>
    </div>
   

    
  	<Table>
  
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCollectivityGrid);">
    		</TD>
    		<TD class= titleImg>
产品费率查询信息表
    		</TD>
    	</TR>
  </Table> 
 
   
 <Div  id= "divCollectivityGrid" style= "display: ''" alight="center">

   <Table  class= common>
       <TR  class= common>
        <td style="text-align:left;" colSpan=1>
            <span id="spanCollectivityGrid" ></span> 
  		</TD>
       </TR>
         
    </Table>	
    
	 <table class= common>
	 	<br/>  
	 			 <tr class="common">
				<td class="title">变更原因:</td>
			</tr>
			<tr class="common">
				<td class=input5 colSpan=4><textarea name="changeReason" cols="135"
					rows="4" readonly="readonly" witdh=25% class="common"></textarea></td>
			</tr>
	 </table>
	 
	<table  class= common id="biangengyuanyinluru">
			<br/>	
			<tr class="common">
				<td class="title">审核结论:</td>
			</tr>
			<tr class="common">
				<td class=input5 colSpan=4><textarea name="auditConclusion" cols="135"
					rows="4" readonly="readonly" witdh=25% class="common"></textarea></td>
			</tr>
         
	    
		    <TR  class= common>
		
		         <TD  class= title>  
		           <INPUT VALUE="费率数据下载" class=cssButton TYPE=button onclick="download();"> 
		           <INPUT VALUE="费率脚本下载" class= cssbutton TYPE=button onclick="rateDownloadClick();">
		         </td>
			</TR>
         
	     
 </Table>
	 	
 </Div>		
<div align=right>

 </div> 
        <input type=hidden name=Transact >
        <input type=hidden name=State >
        
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
