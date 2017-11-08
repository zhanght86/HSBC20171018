<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：GetReturnFromBankInput.jsp
//程序功能：
//创建日期：2002-11-18 11:10:36
//创建人  ：胡 博
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <SCRIPT src="GetReturnFromBankInput.js"></SCRIPT>
  <%@include file="GetReturnFromBankInit.jsp"%>
  
  <title>银行代收 </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./GetReturnFromBankSave.jsp" method=post name=fm id=fm target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 银行代收 fraSubmit-->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请选择批次号：</td>
  		</tr>
  	</table>   
    <div id="divInvAssBuildInfo">
      
     </div>    
    <!-- 批次号信息（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBank1);">
    		</td>
    		<td class= titleImg>
    			 批次号信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divBank1" style= "display: ''">
      	<table  class= common>
          	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table>
  	</div>
  	
   <!-- <Div id= "divPage" align=center style= "display: 'none' ">
    <INPUT CLASS=cssButton VALUE="首页" TYPE=button onClick="turnPage.firstPage();"> 
    <INPUT CLASS=cssButton VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
    <INPUT CLASS=cssButton VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
    <INPUT CLASS=cssButton VALUE="尾页" TYPE=button onClick="turnPage.lastPage();">
    </Div>
-->
    
    <br>
    <div class="maxbox1" >
    <table  class= common>
    <TR  class= common>
      <TD  class= title5>
        请输入银行返回的总金额：
      </TD>
      <TD  class= input5>
        <Input class="common wid" name=TotalMoney verify="总金额|notnull&num" > 
      </TD>    
       <TD  class= title5>
        
      </TD>
      <TD  class= input5>
        
      </TD>                  
    </TR>
    </table>
    <br><br>
    <!--<INPUT VALUE="代收返回处理" class= cssButton TYPE=button onClick="submitForm()">-->
     <a href="javascript:void(0);" class="button"onClick="submitForm()">代收返回处理</a>

    
    <INPUT VALUE="" TYPE=hidden name=serialNo>
    
     </div>      										
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
