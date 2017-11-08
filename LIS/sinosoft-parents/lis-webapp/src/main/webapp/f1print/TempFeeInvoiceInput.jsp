<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<% 
//程序名称：
//程序功能：
//创建日期：2009-10-27 15:39:06
//创建人  yanglh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%
    GlobalInput tGI = new GlobalInput();
		tGI = (GlobalInput)session.getValue("GI");
		String tCurrentDate = PubFun.getCurrentDate();
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="TempFeeInvoiceInput.js"></SCRIPT>    
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="TempFeeInvoiceInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>  
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFeeInv);">
            </TD>
   		
    		 <td class= titleImg>
        		输入查询条件 
       		 </td>   		      
    	</tr>
    </table>
        <Div  id= "divFeeInv" style= "display: ''">
          <div class="maxbox1" >
      <table class= common border=0 width=100%>
         <TR  class= common>
         	 <TD  class= title5> 合同号码  </TD>
          <TD  class= input5> <Input class="common wid" name=ContNo > </TD>
           </TR>
          <TR  class= common>
           <TD  class= title5> 人员代码  </TD> 
          <TD  class= input5> <Input class="common wid" name=AgentCode  verify="代理人编码|code:AgentCode" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return queryAgent(comcode);" 
          onDblClick="return queryAgent(comcode);" 
          onBlur="return queryAgentAll(comcode);" >  </TD>
         <TD  class= title5> 人员姓名  </TD> 
          <TD  class= input5> <Input class="readonly wid "  tabindex=-1 name=AgentName readonly=true > </TD>
        </TR>
          <TR  class= common>

          <TD  class= title5>   应交日期（开始） </TD>
          <TD  class= input5>	
          <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          
            </TD> 
			    <TD  class= title5>   应交日期（结束） </TD>
          <TD  class= input5>	<input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          
           </TD> 

          </TR>
        </table> 
    	<table class= common border=0 width=100%>
    	<tr>     		
    		 <td class= titleImg>
        		所查记录须有催收信息, 保单状态须为正常缴费有效
       		 </td>   		      
    	</tr>
    </table>
    </div></Div>		  
		<!--<INPUT VALUE="查询" class="cssButton" TYPE=button onClick="easyQueryClick();"> 	-->
		 <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPay1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <div align="center">
    	<INPUT VALUE="首页" class="cssButton90" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onClick="turnPage.lastPage();">	
      </div>			
  	</div>
    </div>
        <input type=hidden id="fmtransact" name="fmtransact">
        <input type=hidden name="MngCom" value=<%=tGI.ManageCom%>>
        <br/>
		<!--<INPUT VALUE="发票打印" class="cssButton" TYPE=button onClick="Print();"> -->
        <a href="javascript:void(0);" class="button"onClick="Print();">发票打印</a>
        <br><br><br><br>
		
  </form>
</body>
</html> 