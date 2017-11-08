<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="LogQuery.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>

  <%@include file="LogQueryInit.jsp"%>
  
<title>日志查询 </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- 日志信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>请输入日志查询条件：</td>
		</tr>
	</table>
    <table  class= common align=center>
    
       <TR  class= common>
          <TD  class= title>其它号码 </TD>
          <TD  class= input> <Input class= common name=OtherNo > </TD>
          <TD  class= title> 其它号码类型</TD>
          <TD  class= input><Input class= 'code' name=OtherNoType CodeData="0|^0|团单合同整单删除^1|团单下险种单整单删除^2|团单下个人合同整单删除^3|团单下个单主被保人删除^4|团单下个人险种单删除^12|个单合同整单删除^13|个单下主被保人删除^14|个单合同下险种单整单删除" ondblclick="showCodeListEx('OtherNoType',[this,''],[0,1]);" onkeyup="showCodeListEx('OtherNoType',[this,''],[0,1]);"></TD>
          <TD  class= title> 是否是保单</TD>
          <TD  class= input><Input class= 'code' name=IsPolFlag CodeData="0|^0|否^1|是" ondblclick="showCodeListEx('IsPolFlag',[this,''],[0,1]);" onkeyup="showCodeListEx('IsPolFlag',[this,''],[0,1]);"></TD>
        </TR>    

      	<TR  class= common>
          <TD  class= title>印刷号码 </TD>
          <TD  class= input> <Input class= common name=PrtNo > </TD>
          <TD  class= title> 管理机构</TD>
          <TD  class= input><Input class="code" name=ManageCom verify="管理机构|code:comcode&notnull" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></TD>
          <TD  class= title> 操作员</TD>
          <TD  class= input> <Input class= common name=Operator > </TD>
        </TR>
        
        <TR  class= common>          
          <TD  class= title> 入机日期</TD>
          <TD  class= input> <Input class="coolDatePicker" name=MakeDate verify="入机日期|date"> </TD>
          <TD  class= title> 入机时间 </TD>
          <TD  class= input> <Input class=common name=MakeTime verify="入机时间"> </TD>
          <TD  class= title> 最后一次修改日期 </TD>
          <TD  class= input> <Input class="coolDatePicker" name=ModifyDate verify="最后一次修改日期|date"> </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title> 最后一次修改时间 </TD>
          <TD  class= input> <Input class= common name=ModifyTime verify="最后一次修改时间"> </TD>
        </TR>
       
    </table>
          <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClick();"> 
          <!--INPUT VALUE="日志明细" TYPE=button onclick="getQueryDetail();"--> 					
          
          <INPUT VALUE="返  回" name=Return class = cssButton TYPE=button STYLE="display:none" onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLog1);">
    		</td>
    		<td class= titleImg>
    			 日志信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLog1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanLogGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<BR>
      <INPUT VALUE="首  页" class = cssButton TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class = cssButton TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class = cssButton TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class = cssButton TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  	<p>
  	
  <Div  id= "divLog2" style= "display: 'none'"> 
     <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLog2);">
    		</td>
    		<td class= titleImg>
    			 日志明细
    		</td>
    	</tr>
    </table>   	
    <table  class= common align=center>
    
       <TR  class= common>
          <TD  class= title>其它号码 </TD>
          <TD  class= input> <Input class= common name=tOtherNo > </TD>
          <TD  class= title> 其它号码类型</TD>
          <TD  class= input><Input class= 'code' name=tOtherNoType CodeData="0|^0|团单合同整单删除^1|团单下险种单整单删除^2|团单下个人合同整单删除^3|团单下个单主被保人删除^4|团单下个人险种单删除^12|个单合同整单删除^13|个单下主被保人删除^14|个单合同下险种单整单删除" ondblclick="showCodeListEx('OtherNoType',[this,''],[0,1]);" onkeyup="showCodeListEx('OtherNoType',[this,''],[0,1]);"></TD>
          <TD  class= title> 是否是保单</TD>
          <TD  class= input><Input class= 'code' name=tIsPolFlag CodeData="0|^0|否^1|是" ondblclick="showCodeListEx('IsPolFlag',[this,''],[0,1]);" onkeyup="showCodeListEx('IsPolFlag',[this,''],[0,1]);"></TD>
        </TR>    

      	<TR  class= common>
          <TD  class= title>印刷号码 </TD>
          <TD  class= input> <Input class= common name=tPrtNo > </TD>
          <TD  class= title> 管理机构</TD>
          <TD  class= input><Input class="code" name=tManageCom verify="管理机构|code:comcode&notnull" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);"></TD>
          <TD  class= title> 操作员</TD>
          <TD  class= input> <Input class= common name=tOperator > </TD>
        </TR>
        
        <TR  class= common>          
          <TD  class= title> 入机日期</TD>
          <TD  class= input> <Input class="coolDatePicker" name=tMakeDate verify="投保人出生日期|notnull&date"> </TD>
          <TD  class= title> 入机时间 </TD>
          <TD  class= input> <Input class=common name=tMakeTime verify="投保人出生日期|notnull&date"> </TD>
          <TD  class= title> 最后一次修改日期 </TD>
          <TD  class= input> <Input class="coolDatePicker" name=tModifyDate verify="投保人出生日期|notnull&date"> </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title> 最后一次修改时间 </TD>
          <TD  class= input> <Input class= common name=tModifyTime verify="投保人出生日期|notnull&date"> </TD>
        </TR>       
    </table> 	 
  </div>  	 
  	 </p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
