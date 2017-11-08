<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String AppntNo = request.getParameter("AppntNo");
String LoadFlag = request.getParameter("LoadFlag");
loggerDebug("GrpBonusInput",AppntNo);
%>
<script>
LoadFlag="<%=LoadFlag%>"; //暂用来传递AppntNo 
GrpContNo="<%=GrpContNo%>";

</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="GrpBonusInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpBonusInit.jsp"%>
  <title>利率配置</title>
</head>
<body  onload="initForm();">
  <form action="./GrpBonusSave.jsp" method=post name=fm target="fraSubmit" >
    <!-- 保单信息部分 -->
  <table class= common border=0 width=100%>
    <tr>
			<td class= titleImg align= center>
				请输入查询条件：
			</td>
		</tr>
	</table>
	
    <table  class= common align=center>
      	<TR  class= common>
 
          <TD  class= title>险种编码</TD>
          <TD class=input>
					<Input class=codeno name=QueryRiskCode ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName readonly=true>
				</TD>   
          <td><INPUT VALUE="查  询" class = button TYPE=button onclick="easyQueryClick();"> </td>
    </table>
  	<Div  id= "divMain1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBonusGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
 <center>   
    	<input type=hidden name=Transact >
      <INPUT VALUE="首页" class= button TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class= button TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class= button TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class= button TYPE=button onclick="turnPage.lastPage();">		
</center>  		
  	</div>
   	<table class= common align=center>
    		<tr class= common>
    			 <TD  class= title>团单合同号</TD>
          <TD  class= input> 
          <Input class="readonly" readonly name=GrpContNo value="<%=GrpContNo%>">
							
          </TD>
    		  <TD  class= title>客户代码</TD>
    			<td  class= input>
    				<Input class="common"  name=AppntNo value="<%=LoadFlag%>">
    			</td>
    			<TD  class= title>给付金额</TD>
    			<td  class= input>
    				<Input class= "common" name=PayMoney >     			
    			</td>

<!--    			 <TD  class= title>客户标识</TD>
    			<td  class= input>
    			<Input class="code" name=Degree CodeData="0|^0|优质客户^1|普通客户^2|黑名单客户" ondblclick="showCodeListEx('Degree',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('Degree',[this,''],[0,1]);">  
    			</td>  --> 			
    		</tr>   	
    		<tr class= common>
    			<TD  class= title>险种代码</TD>
    			<TD class=input>
					<Input class=codeno name=RiskCode ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName readonly=true>
				<font size=1 color='#ff0000'><b>*</b></font>
				</TD>
   			<TD  class= title>团单险种号</TD>
    			<TD class=input>
					<input class="readonly" readonly name="GrpPolNo">
				</tD> 		
    		        
    			<TD  class= title>保险期间</TD>
    			<td  class= input>
    				<Input class= common name=InsuYear ><font size=1 color='#ff0000'><b>*</b></font>    			
    			</td>
    			
    		</tr>      
    		<tr class= common>
           <TD  class= title>期间单位</TD>
    			<td  class= input>
    				 <Input class="code" name=InsuYearFlag CodeData="0|^0|年^1|月^2|天" ondblclick="showCodeListEx('GrpRateType',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('GrpRateType',[this,''],[0,1]);">  
    			
    			</td>
    		        <TD  class= title>回报方式</TD>
    			<td  class= input>
    			 <Input class="code" name=RewardType CodeData="0|^0|固定利率" ondblclick="showCodeListEx('RewardType',[this,''],[0]);" onkeyup="showCodeListKeyEx('RewardType',[this,''],[0]);">  
    			   			
    			</td>


 <!--  
       <div  id= "divExplain" style= "display: 'none'">
       	<TD  class= title>承诺回报利率</TD> 
       </div>
       <div  id= "divExplain2" style= "display: 'none'">
       	<TD  class= title>承诺回报比例</TD> 
       </div>  -->
       <TD  class= title>回报利率/比例</TD> 
    		<td  class= input>
    				  <Input class="common" name=RewardValue><font color=red>该利率只录分红利率，不包含保底2.5%</font>   			      
    			</td>
    		</tr>		        
    		<tr class= common>

    		        <TD  class= title>合同起始日</TD>
    			<td  class= input>
    			<Input class= "coolDatePicker" dateFormat="short" name=StartDate > 
    			 	  			
    			</td>
    			<TD  class= title>合同终止日</TD>
    			<td  class= input>
    				<Input class= "coolDatePicker" dateFormat="short" name=EndDate >     			
    			</td>

    		</tr>    			
	  	</table>     
 	<table>	  
    		<tr class= common>
    			<TD  class= title>
            说明
          </TD>
          <tr></tr>
          <TD  class= input> <textarea name="Note" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
   			<td>
    				 <INPUT class= code name=GrpPolNo1 type=hidden> 
    		</td>
    		</tr>
    		
    		 <tr>
    		 	<td>
  				 <INPUT class= button VALUE="分红添加" TYPE=button onclick="save();">
    				<INPUT class= button VALUE="分红修改" TYPE=button onclick="Mod();">  
    				<input type=hidden name=frmAction>  			
    			</td>
    		 	</tr>
  	
    	</table> 	
    	
    	<!--个别分红退保-->
  <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson2);">
    		</TD>
    		<TD class= titleImg>
    			 个别分红退保处理
    		</TD>
    	</TR>
    </Table>  
    <table class= common border=0 width=100%>
    <tr>
			<td class= titleImg align= center>
				请输入查询条件：
			</td>
		</tr>
	 </table>
	
    <table  class= common align=center>
      	<TR  class= common>
 
          <TD  class= title>险种编码</TD>
          <TD class=input>
					<Input class=codeno name=QueryRiskCode2 ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName readonly=true>
				</TD>   
          <td><INPUT VALUE="查  询" class = button TYPE=button onclick="easyQueryClick2();"> </td>
    </table>
    	  	
 <Div  id= "divLDPerson2" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanCanclePolGrid" ></span> 
  	</TD>
      </TR>
    </Table>
<center>    
   <table> 
    <tr>		
    <td>			
      <INPUT class=cssButton VALUE="首  页" TYPE=button onclick="getFirstPage();"> 
    </td>
    <td>
      <INPUT class=cssButton VALUE="上一页" TYPE=button onclick="getPreviousPage();"> 					
    </td>
    <td>  
      <INPUT class=cssButton VALUE="下一页" TYPE=button onclick="getNextPage();"> 
    </td>
    <td>  
      <INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="getLastPage();"> 
    </td>
    </tr> 
    
   </table> 
</center>    
 </Div>		

  	<table class= common align=center>
    		<tr class= common>
    			 <TD  class= title>团单合同号</TD>
          <TD  class= input> 
          <Input class="readonly" readonly name=GrpContNo2 value="<%=GrpContNo%>">
							
          </TD>
    		  <TD  class= title>客户代码</TD>
    			<td  class= input>
    				<Input class="common"  name=AppntNo2 value="<%=LoadFlag%>">
    			</td>
    			<TD  class= title>险种代码</TD>
    			<TD class=input>
					<Input class=codeno name=RiskCode2 ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName,GrpPolNo2],[0,1,2],null,fm.GrpContNo2.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName,GrpPolNo2],[0,1,2],null,fm.GrpContNo2.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName2 readonly=true>
				  <font size=1 color='#ff0000'><b>*</b></font>
				  </TD>
    		</tr>	    			
    		<tr class= common> 			
   			<TD  class= title>团单险种号</TD>
    			<TD class=input>
					<input class="readonly" readonly name="GrpPolNo2">
				</tD> 
				<TD  class= title>帐户经过年数</TD>
    			<td  class= input>
    			 <Input class="code" name=AccountPassYear CodeData="0|^0|年数T小于1年^1|年数T大等于1年小于2年^2|年数T大等于2年小于3年^3|年数T大等于3年" ondblclick="showCodeListEx('AccountPassYear',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('AccountPassYear',[this,''],[0,1]);">    			   			
    			<font size=1 color='#ff0000'><b>*</b></font>
    			</td>		
    		        
    			<TD  class= title>占账户金额比例</TD>
    			<td  class= input>
    				  <Input class="common" name=AccountValueRate>   			      
    			</td>  	
    		</tr>      					
	  	</table>  
	  	<br>   
 	<table>	    		
    		 <tr>
    		 	<td >
    			<INPUT class= button VALUE="添加" TYPE=button onclick="canclePol();">
    			</td>
    			<td > 
   				<INPUT class= button VALUE="修改" TYPE=button onclick="canclePolMod();">
   				</td> 
   				<td >
   				<INPUT class= button VALUE="删除" TYPE=button onclick="canclePolDel();">       						
    			</td>
    		 	</tr>
    		 		      
    	</table> 	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
