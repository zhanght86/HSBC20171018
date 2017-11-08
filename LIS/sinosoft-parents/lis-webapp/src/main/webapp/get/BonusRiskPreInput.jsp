<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<%
   
    String CurrentDate = PubFun.getCurrentDate();
    loggerDebug("BonusRiskPreInput",CurrentDate);      
%>


<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="BonusRiskPre.js"></SCRIPT> 
  <%@include file="BonusRiskPreInit.jsp"%> 
  <%@include file="../common/jsp/ManageComLimit.jsp"%>

</head>
<body  onload="initForm();" >

<form action="./BonusRiskPreSave.jsp" method=post name=fm id=fm target="fraSubmit">

  <Div class="maxbox1">      
    <table  class= common>
      <TR class= common>
         <TD  class= title5>
            红利分配会计年度
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalYear id=BonusCalYear verify="红利分配会计年度|notnull&INT&len==4" onchange="clearAllShow()"><font color=red>*</font>
             </TD> 
          <TD  class= title5>
            红利分配险种
          </TD>          
          <TD class= input5>    
              <Input class="wid" class=common  name=BonusCalRisk id=BonusCalRisk >
          </TD>   
      </TR>     
    </Table>  
</Div>
<INPUT VALUE="查  询" CLASS=cssButton TYPE=button onclick="queryData('0');">  
<INPUT VALUE="新  增" CLASS=cssButton TYPE=button onclick="addData();">
<!--<a href="javascript:void(0);" class="button" onClick="queryData('0');">查    询</a>
<a href="javascript:void(0);" class="button" onClick="addData();">新    增</a>-->

<table>
   <tr>
      <td class="common">
        <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick= "showPage(this,divNoAgentGrid);">
      </td>
      <td class= titleImg>
        分红险种信息
      </td>
   </tr>
</table>
<Div  id= "divNoAgentGrid" style= "display: ''" >
      	<table width="100%">
       		<tr  >
      	  		<td text-align: center colSpan=1>
  					<span id="spanBonusRiskGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT  VALUE="首  页" TYPE=button CLASS=cssButton90 onclick="turnPage1.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="上一页" TYPE=button CLASS=cssButton91 onclick="turnPage1.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="下一页" TYPE=button CLASS=cssButton92 onclick="turnPage1.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="尾  页" TYPE=button CLASS=cssButton93 onclick="turnPage1.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>							
</div>

<table>
   <tr>
      <td class="common">
        <IMG src="../common/images/butExpand.gif" style="cursor:hand" onClick= "showPage(this,divAgentGrid);">
      </td>
      <td class= titleImg>
        不参与分红险种信息
      </td>
   </tr>
</table>   


       
<Div  id= "divAgentGrid" style= "display: ''" >
      	<table  width="100%" >
       		<tr  >
      	  		<td text-align: center colSpan=1>
  					<span id="spanNoBonusRiskGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align="center">
    		<tr>
    			<td>
			      <INPUT  VALUE="首  页" TYPE=button CLASS=cssButton90 onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT  VALUE="上一页" TYPE=button CLASS=cssButton91 onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 		 
			      <INPUT  VALUE="下一页" TYPE=button CLASS=cssButton92 onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 		 
			      <INPUT  VALUE="尾  页" TYPE=button CLASS=cssButton93 onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>							
</div>
<INPUT VALUE="查  询" name = "query" CLASS=cssButton TYPE=button onclick="queryData('1');">
<!--<a href="javascript:void(0);" name = "query" class="button" onClick="queryData('1');">查    询</a>
<a href="javascript:void(0);" name = "compute" class="button" onClick="delData();">删    除</a><br><br>-->
<INPUT VALUE="删  除" name = "compute" CLASS=cssButton TYPE=button onclick="delData();"> 

<table class= common style= "display: none">
<TR class= common >
         <TD class= title>
             <input Type="Checkbox"  name= checkbox1 value="1"  onClick="setDefaultClass()">扩充算法
         </TD> 
</TR>
  <!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 注:如果扩充算法需要用红利分配会计年度请用 ?BonusCalYear? 标注--></table>
<Div  id= "divOtherGrid" style= "display: none">
<table  class= common>
      <TR class= common>
         <TD  class= title5>
            默认取数算法
          </TD></TR>
         <TR class= common> 
          <TD class=input colspan="4" style="padding-left:16px">
           <textarea class=common readonly cols=164 rows=4 name="WorkDetail" value="">   
           </textarea>  
          </TD>                  
      </TR> 
      <TR class= common>
         <TD  class= title5>
            扩充取数算法
          </TD>    </TR>
             <TR class= common>            
          <TD class=input colspan="4" style="padding-left:16px">
           <textarea class=common cols=164 rows=4 name="OtherCondition" >   
           </textarea>  
          </TD>   
      </TR>
 </table>
 <Div id= divCmdButton style="display: ''">
      
  </Div>     
  <INPUT VALUE="扩充算法数据保存" name = "query" CLASS=cssButton  TYPE=button onclick="saveOtherData();"> 
 <!-- <a href="javascript:void(0);" name = "query" class="button" onClick="saveOtherData();">扩充算法数据保存</a> -->
</Div>  
  <!-- 确认对话框 初始化为0 点确认框的确定修改为1 计算完毕后重新初始话为0 -->
  <INPUT VALUE="0" TYPE=hidden name=myconfirm >
  
    <!-- 当前日期 -->
  <INPUT VALUE=<%=CurrentDate%> TYPE=hidden name=CurrentDay>
  <!-- 当前月份 -->
  <input type=hidden id="hideaction" name="hideaction">  
  <input type=hidden id="hideDelManageCom" name="hideDelManageCom"> 
  <input type=hidden id="hideDelIndexCalNo" name="hideDelIndexCalNo">   
   
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
