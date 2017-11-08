<html>
<%
//name :RateCardQuery.jsp
//Creator :zz
//date :2008-06-20
//卡单费率表查询功能
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.certify.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");
    String Branch =tGlobalInput.ComCode;
%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="RateCardQuery.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="RateCardQueryInit.jsp"%>
	</head>

	<body  onload="initForm();" >
  	<form action="./RateCardQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- 显示或隐藏LLReport1的信息 -->
    		
   <Div id= "divLLReport1" style= "display: ''">
   <Table class= common>
	<tr>
    <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
		<td class=titleImg>卡单费率查询(一个险种的一种保费对应的一种产品形态)</td>
	</tr>
	</Table>
  <div class="maxbox1">
  <Div  id= "divFCDay" style= "display: ''"> 
   <Table class= common>
   		<TR class= common>
   			<TD class= title5>险种编码</TD>
   			<td class= input5>
   				 <Input class="code" name=Riskcode id="Riskcode" readonly CodeData="0|^141814|MS交通工具意外伤害保险|^141815|MS短期综合意外伤害保险|" onMouseDown="return showCodeListEx('Riskcode',[this]);" ondblclick="return showCodeListEx('Riskcode',[this]);" onkeyup="return showCodeListKeyEx('Riskcode',[this]);">
				</td>
				

        	     <td class= title5>产品计划</td>
         <td class= input5>
        	  <Input class="code" name=ProductPlan id="ProductPlan" readonly CodeData="0|^1|1期|^2|2期|" onMouseDown="return showCodeListEx('ProductPlan',[this]);"  ondblclick="return showCodeListEx('ProductPlan',[this]);" onkeyup="return showCodeListKeyEx('ProductPlan',[this]);">
         </td>
   		</TR>

       <TR class= common>
						<TD class= title5>保险年期</TD>
        <TD class= input5><Input class= "common wid" name=InsuYear id="InsuYear" ></TD>
        
        	
	    	<td class= title5>保险年期单位</td>
        <td class= input5>
        	  <Input class="code" name=InsuYearFlag id="InsuYearFlag" readonly CodeData="0|^Y|年|^M|月|^D|日|^A岁|"  onMouseDown="return showCodeListEx('RelationToLCInsured',[this]);"  ondblclick="return showCodeListEx('RelationToLCInsured',[this]);" onkeyup="return showCodeListKeyEx('RelationToLCInsured',[this]);">
        </td>
 
      </TR>
      
       <TR class= common>      	
		   <TD class= title5>保费</TD>
           <TD class= input5><Input  class= "common wid" name= Prem id="Prem" ></TD>    
  
      </TR>
      
 </Table>
</Div>
</div>
<!--<a href="javascript:void(0)" class=button onclick="EasyQueryClick();">查　询</a>-->

  	 <input class=cssButton type=button value="查  询" onclick="EasyQueryClick()">
		
    <Input class= common type= hidden name= OperateType id="OperateType">
    <BR>
    <BR>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 清单列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLLReport2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanRateCardGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
      </Div>
  	</div>
    <!--<a href="javascript:void(0)" class=button onclick="ReturnData();">返  回</a>-->
    <input class=cssButton type=button value="返  回" onclick="ReturnData()">  
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
