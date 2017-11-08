<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<% 
//程序名称：
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
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
  <SCRIPT src="FeeInvoiceInput.js"></SCRIPT>    
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="FeeInvoiceInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>   
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
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
          <TD  class= title5>交费收据号码  </TD>
          <TD  class= input5>  <Input class="common wid" name=GetNoticeNo > </TD> 
          <TD  class= title5>   交费日期 </TD>
          <TD  class= input5>	
           <input class="coolDatePicker" dateFormat="short" id="PayDate"  name="PayDate" onClick="laydate({elem:'#PayDate'});"> <span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </TD> 
          </TR>
        <TR  class= common>
          <TD  class= title5>  管理机构   </TD>
          <TD  class= input5>
          <Input class="common wid" name=MngCom verify="管理机构|code:comcode"
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this],null,null,null,null,1);" 

           onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" 
           onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" readonly> </TD>
         
          <TD  class= title5> 实收编号  </TD>
          <TD  class= input5> <Input class="common wid" name=PayNo > </TD>
            </TR>
        <TR  class= common>
           <TD  class= title5> 代理人编码  </TD> 
          <TD  class= input5> <Input class="common wid" name=AgentCode  verify="代理人编码|code:AgentCode"
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return queryAgent(comcode);"
           onDblClick="return queryAgent(comcode);"
            onKeyUp="return queryAgent(comcode);" >  </TD>
          <TD  class= title5>  合同号码类型  </TD>
          <!--TD  class= input>	<Input class=code name=IncomeType verify="应收/实收编号类型|NOTNULL" CodeData="0|^1|集体保单号^2|个人保单号" ondblClick="showCodeListEx('FeeIncomeType1',[this],[0,1]);" onkeyup="showCodeListKeyEx('FeeIncomeType1',[this],[0,1]);">           </TD--> 
          <TD  class= input5>	<Input class="common wid" name=IncomeType  CodeData="0|^1|集体合同号^2|个人合同号"
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('FeeIncomeType1',[this]);"
           ondblClick="showCodeListEx('FeeIncomeType1',[this]);"
            onKeyUp="showCodeListKeyEx('FeeIncomeType1',[this]);">           </TD> 

        </TR>
       <TR  class= common>

          <TD  class= title5> 合同号码  </TD>
          <TD  class= input5> <Input class="common wid" name=IncomeNo > </TD>
        </TR>
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
    			 费用信息
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
        
        <br/>
		<!--<INPUT VALUE="发票打印" class="cssButton" TYPE=button onClick="PPrint();"> -->
		<a href="javascript:void(0);" class="button"onClick="PPrint();">发票打印</a>
        <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
