<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//程序名称：DeleteSendToBankInput.jsp
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

  <SCRIPT src="DeleteSendToBankInput.js"></SCRIPT> 
  <%@include file="DeleteSendToBankInit.jsp"%>
  
  <title>生成送银行文件 </title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="./DeleteSendToBankSave.jsp" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <!-- 保单信息部分 -->
    
  <table class= common border=0 width=100%>
        <tr>
         <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
         </td>
		<td class= titleImg align= center>请输入查询条件：</td>
	   </tr>
	</table>
	<div class="maxbox1" id="maxbox">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            印刷号
          </TD>
          <TD  class= input>
            <Input NAME=PrtNo class="common wid">
          </TD>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td>  
        </TR>
        <tr  class= common>
          <td class= title></td>
          <td class= input></td>
          <td class= title></td>
          <td class= input></td> 
          <td class=title>
          </td>
        </TR>
    </table>
    </div> 
    <INPUT VALUE="查    询" TYPE=button class=cssButton onClick="easyQueryClick();" name=butQuery >  
    <br>
    <!-- 生成送银行文件 fraSubmit-->  
     
    <!-- 暂交费信息（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBank1);">
    		</td>
    		<td class= titleImg>
    			 送银行数据信息
    		</td>
    	</tr>
    </table>
  	<Div id= "divBank1" style= "display: ''">
      	<table  class= common>
          	<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBankGrid" >
  					</span> 
  				</td>
  			</tr>
  		</table> 

  	
    <Div id= "divPage" align=center style= "display: none ">
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
    </Div>
    <table class= common border=0 width=100% >
    	<tr>
			<td class= titleImg >银行在途数据不能取消</td>
  		</tr>
  	</table>
    </Div>
    <br><br><hr class="line"><br><br>
    
    <DIV id= "divBank2"  style= "display: none">      		  								
    <table  class= common align=center>     
    	<TR CLASS=common>
        <TD CLASS=title5>
          暂交费号 
        </TD>
        <TD CLASS=input5 COLSPAN=1>
          <Input class="common wid" NAME=TempFeeNo id="TempFeeNo" VALUE="" >
        </TD>
        <TD CLASS=title5>
          操作方式 
        </TD>
        <TD CLASS=input5 COLSPAN=1>
          <Input class="common wid" NAME=Action id="Action" VALUE="" >
        </TD>
      </TR>
    </table>
    <br>
    </DIV>
    <table align=left>
      <tr>
        <td> 
    <INPUT VALUE="取消发送数据" class= cssButton TYPE=button onClick="submitForm()" name=butSave >
        </td>
        <td>
    <INPUT VALUE="取消发送限制" class= cssButton TYPE=button onClick="submitForm2()" name=butSave2>
        </td>
      </tr>
    </table>
    <INPUT VALUE="" TYPE=hidden name=serialNo>
 
  </form>
   <br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>

