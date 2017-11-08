<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<% 
//程序名称：GEdorTypeMultiDetailInput.jsp
//程序功能：团体保全明细总页面
//创建日期：2003-12-03 16:49:22
//创建人  ：Minim
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>   
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>   
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 

  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./GEdorTypeMultiRisk.js"></SCRIPT>
  <%@include file="GEdorTypeMultiRiskInit.jsp"%>
  
  <title>团体保全明细总页面</title> 
</head>

<body  onload="initForm();" >
  <form action="./GEdorTypeMultiRiskSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <input type=hidden readonly name=EdorAcceptNo  id=EdorAcceptNo>
	<div class=maxbox1 >
    <table class=common>
      <TR  class= common> 
        <TD  class= title > 批单号</TD>
        <TD  class= input > 
          <input class="readonly wid" readonly name=EdorNo id=EdorNo >
        </TD>
        <TD class = title > 批改类型 </TD>
        <TD class = input >
        	<input class = "readonly wid" readonly name=EdorType id=EdorType>
        </TD>
       
        <TD class = title > 集体保单号 </TD>
        <TD class = input >
        	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
        </TD>   
      </TR>
    </TABLE> 
	</div>
    
    <br><hr class=line>
    
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
        </td>
        <td class= titleImg>
          险种信息
        </td>
      </tr>
    </table>
    <div class=maxbox1> 
    <table class = common>
      <tr class = common>
      	<td class = title>
      		险种保单号
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=GrpPolNo id=GrpPolNo>   
          </TD>
        <td class = title>
      		险种编号
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=RiskCode id=RiskCode>
          </TD>
        <td class = title>
      		
      		</td>
      	<td class = input>
          </TD>  
      </tr>
    </table>
	</div>
    <INPUT VALUE="查询" class=cssButton TYPE=button onclick="queryClick();"> 
    
    <br><br>
    
    <Div  id= "divLPInsured" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanRiskGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage" align=center style= "display: 'none' ">
        <INPUT class=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"> 			
      </Div>		
  	</div>

	  <br><hr class=line> 

	  <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
        </td>
        <td class= titleImg>
          修改过的险种信息
        </td>
      </tr>
    </table>
	  <Div  id= "divLPInsured2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanRisk2Grid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage2" align=center style= "display: 'none' ">
        <INPUT class=cssButton90 VALUE="首页" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage2.lastPage();">					
      </Div>
  	</div>
	  
	  <br><hr>
	  
	  <Input type=Button value="险种退保" class=cssButton onclick="pEdorMultiDetail()">
	  <Input type=Button value="撤销险种退保" class=cssButton onclick="cancelPEdor()">  
	  <Input type=Button value="返回" class=cssButton onclick="returnParent()">
	  
	  <input type=hidden id="ContNo" name="ContNo">
	  <!-- add ContType for PEdor GT -->
	  <input type=hidden id="ContType" name="ContType"> 
	  <input type=hidden id="Transact" name="Transact">
	  
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
  
</body>
</html>

<script>
  window.focus();
</script>
