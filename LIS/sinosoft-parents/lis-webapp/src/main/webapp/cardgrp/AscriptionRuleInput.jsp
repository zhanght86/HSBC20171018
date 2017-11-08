<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<head>
<script>
var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
var GrpContNo="<%=request.getParameter("GrpContNo")%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT> 
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="AscriptionRuleInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AscriptionRuleInit.jsp"%>
<title>团体险种归属规则定制 </title>
</head>
<body onload="initForm();">
	<form method=post name=fm target="fraSubmit" action="AscriptionRuleSave.jsp">		
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>合同信息</td>
			</tr>
		</table>
		<table  class= common align=center>  
			<TR  class= common>
				<TD  class= title>团体合同号</TD>
				<TD  class= input>
					<Input class=common name=GrpContNo onblur="GrpPerPolDefine();">					
					<input type=hidden name=mOperate>
				</TD>
				<TD  class= title>&nbsp;</TD>
				<TD  class= input>
					<INPUT VALUE="查  询" class =cssButton  TYPE=button onclick="GrpPerPolDefine();">
				</TD>
				<TD  class= title>&nbsp;</TD>
				<TD  class= input>
					
				</TD>
			</TR>
			
			</table>
			
		<Div  id= "divAscriptionRuleOld" style= "display: ''">
	 	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 已添加的归属规则
    		</td>
    	</tr>
    	    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanAscriptionRuleOldGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<div id="divAscriptionRuleGrid" style="display:'none'">
				<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>归属规则详细信息</td>
			</tr>
		</table>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanAscriptionRuleGrid" ></span>
				</td>
			</tr>
		</table>
		</div>
       </table>
       
       </Div>
       		
		
		<Div  id= "divAscriptionRule" style= "display: ''">
	 	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
    		</td>
    		<td class= titleImg>
    			 定制归属规则
    		</td>
    	</tr>
       </table>
       			<TR  class= common>
				<TD  class= title>归属规则</TD>
				<TD  class= input>
					<Input class=common name=AscriptionRuleCode maxlength=2><font color=red>*</font>
				</TD>
				<TD  class= title>说明</TD>
				<TD  class= input>
					<Input class=common name=AscriptionRuleName><font color=red>*</font>
				</TD>
			</TR>
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanAscriptionRuleNewGrid" >				
					</span> 
				    </td>
			    </tr>			
		  </table>	
					<table align=center>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
          </table>  		  		
	</div>
  <hr>
   	
		<!--INPUT VALUE="上一步" class =cssButton  TYPE=button onclick="returnparent();"-->
	<Div  id= "divRiskPlanSave" style= "display: ''" align= right> 
		<INPUT VALUE="归属规则保存" class =cssButton  TYPE=button onclick="submitForm();">
		<INPUT VALUE="归属规则修改" class =cssButton  TYPE=button onclick="updateClick();">
		<INPUT VALUE="归属规则删除" class =cssButton  TYPE=button onclick="DelContClick();">
	</Div>
	</form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
