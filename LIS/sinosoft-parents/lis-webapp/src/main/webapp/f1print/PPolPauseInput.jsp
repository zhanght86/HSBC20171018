
<%
  GlobalInput GI = new GlobalInput();
	GI = (GlobalInput)session.getValue("GI");
%>
<script>
	var manageCom = "<%=GI.ManageCom%>"; //记录管理机构
	var ComCode = "<%=GI.ComCode%>"; //记录登陆机构
</script>

<html>
<%
//程序名称：
//程序功能：续期保费银行转账成功清单
//创建日期：2004-5-24
//创建人  ：刘岩松程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
	<%@page contentType="text/html;charset=GBK" %>
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.bank.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="PPolPauseInput.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="PPolPauseInit.jsp"%>
	</head>

	<body  onload="initForm();" >
  	<form action="./PPolPausePrint.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- 显示或隐藏LLReport1的信息 -->
    		
    	<Div  id= "divLLReport1" style= "display: ''">
    	<table class=common>
          <tr class= common>
           <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD  class= titleImg>
              保单效力中止
            </TD>
          </tr>
        </table>
        <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
      	<table  class= common>
        	<TR  class= common>
          	<TD  class= title5>
            	开始日期
          	</TD>
          	<TD  class= input5>
            	<input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          	</TD>
          	<TD  class= title5>
            	结束日期
          	</TD>
          	<TD  class= input5>
            	 <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          	</TD>
		      </TR>
<!--
 					<TR  class= common>
         			
         		<TD  class= title>
            	银行代码
          	</TD>
          	<TD  class= input>
-->
            	<Input  name=AccName type= hidden>
            	<Input  name=AppntName type= hidden>
            	<Input  name=Date type= hidden>
            	<Input  name=BankAccNo type= hidden>
<!--
          	</TD>
    				<TD class= title>
    				银行名称
    				</TD>
    				<TD class=input >
          		<Input class="readonly" readonly name=BankName verify="银行名称|notnull&len<=12" >
						</TD>
						
        	</TR>
        	-->
					<TR class= common>
					<TD  class= title5>
            管理机构
          </TD>
          <TD  class= input5>
	          <Input class= "common wid" name=Station  id=Station 
               style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('Station',[this]);"
              onDblClick="return showCodeList('Station',[this]);"
               onKeyUp="return showCodeListKey('Station',[this]);">
						</TD>
						<!--
						<TD class= title>
					    清单类型
					  </td>
					  <TD  class= code>
  					  <Input class=code name=AgentState verify="清单类型|NOTNULL"CodeData="0|^0|孤儿单^1|在职单" ondblClick="showCodeListEx('AgentState1',[this],[0,1]);"onkeyup="showCodeListKeyEx('AgentState1',[this],[0,1]);">
  				  </TD>
  				  -->
          <TD>
					<Input Type= hidden name=Flag >
					</TD>

					</tr></table>
                    </div></div>
                    
					
          <!--  <TD>
          	  <input class=cssButton type=button value="查询保单效力中止清单" onClick="showSerialNo()">
           </TD>
           <TD class=input width="23%">
            	<input class=cssButton type=button value="打印保单效力中止清单" onClick="QueryBill()">
          </TD>-->
          <a href="javascript:void(0);" class="button"onClick="showSerialNo()">查询保单效力中止清单</a>
          <a href="javascript:void(0);" class="button"onClick="QueryBill()">打印保单效力中止清单</a>
           <table>
           <TR  class= common>
          <TD  class= input>
            	<input type=hidden  name="PremType" >
              <input type=hidden  name="Flag" >
              <Input type=hidden name=Date>
              <Input type=hidden name=PrintType>
          	</TD>
        	</TR>
        	
 				

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
      	  	<td text-align: left colSpan=1>
  					<span id="spanBillGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
    	
      <Div  id= "divPage"  style= "display:''" align="center">     
       <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
       <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
       <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
       <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
  	 </Div>
   </Div>
   <br/>
   <!--<TR  class= common> 
         <TD class=input width="23%">
          	<input class=cssButton type=button value="打印保险合同效力中止通知书" onClick="PrintBill()">
         </TD>      
     </TR>-->
     <a href="javascript:void(0);" class="button"onClick="PrintBill()">打印保险合同效力中止通知书</a>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
