<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
String tLoadFlag = "6";
%>
<head >
<script>
	var loadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.
	var prtNo = "<%=request.getParameter("prtNo")%>";
	window.onfocus = f; 
	var showInfo;
	function f() {
	  try { showInfo.focus(); } catch(e) {}
	}
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	
	<SCRIPT src="GrpEdorTypeNRInput.js"></SCRIPT>
	<%@include file="GrpEdorTypeNRInit.jsp"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>

</head>

<body  onload="initForm(); " >
  <form action="./GrpEdorTypeNRSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--  -->
  	<div class=maxbox1>
	<table class=common>
	    <TR  class= common> 
	      <TD  class= title > 批单号</TD>
	      <TD  class= input > 
	        <input class="readonly wid" readonly name=EdorNo id=EdorNo >
	      </TD>
	      <TD class = title > 批改类型 </TD>
	      <TD class = input >
	      	<Input class=codeno  readonly name=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
	      	<input class = "readonly wid" readonly name=EdorTypeCal id=EdorTypeCal type=hidden>
	      </TD>
	     
	      <TD class = title > 团体保单号 </TD>
	      <TD class = input >
	      	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
	      </TD>   
	    </TR>
	  </TABLE> 
	 </div>
	  
   <table>
     <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
      </td>
      <td class= titleImg>
        被保人信息
      </td>
    </tr>
   </table>
   <div class=maxbox1>
   <table class = common>
      <tr class = common>
      	<td class = title>
      		个人保单号
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=ContNo2 id=ContNo2>
          </TD>
        <td class = title>
      		个人客户号
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=CustomerNo2 id=CustomerNo2>
          </TD>
        <td class = title>
      		客户姓名
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=Name2 id=Name2>
          </TD>  
      </tr>
    </table>
	</div>
    <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="queryClick();"> 
	 
    <Div  id= "divLPInsured" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLPInsuredGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
      <Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage1.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage1.lastPage();">
      </Div>	
  	</div>	 
  	
  	<br><br> 
  	
    <Div  id= "divGetMoney" style= "display:none">
<table class=common>
	    <TR  class= common> 
	      <TD  class= title > 缴费金额合计</TD>
	      <TD  class= input > 
	        <input class="readonly wid" readonly name=GetMoney id=GetMoney>元   </TD>	     
	      <TD class = input ></TD>
	      <TD class = input ></TD> 	     
	      <TD class = input ></TD>   
	    </TR>
	  </TABLE> 	  
	  </Div> 
	    	
    <Div  id= "divSubmit" style= "display:''">
    <left>
      <table>
		<TR>
       		 <td><Input class= cssButton type=Button value="增加险种" onclick="edorNewInsured()"></td>       		 
       		 <td><INPUT class=cssButton id="pisdbutton1" VALUE="导入名单" TYPE=button onclick="getin()"></td> 
       		 <td><Input class= cssButton type=Button value="导入日志查看" onclick="getinblog()"></td>

    </TR>
    <br>
    <TR>
    	<td><Input class= cssButton type=Button value="保存申请" onclick="edorSave()"></td>
       		 <!--<td><Input class= cssButton type=Button value="使用挂帐" onclick="doEY()"></td>-->
       		 <!--td><Input class= cssButton type=Button value=" 使用公共帐户 " onclick="doInsurAcc()"></td-->
       		 <td><Input type=Button value="费用明细" class= cssButton onclick="MoneyDetail()"></td>
       		 <td><Input class= cssButton type=Button value="返  回" onclick="returnParent()"></td>
    	</TR>
     	</table> 
     	</left>
    </Div>  
    <hr class=line>
    
    <table>
     <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPPol);">
      </td>
      <td class= titleImg>
        新增险种列表
      </td>
    </tr>
   </table>
   <Div  id= "divLPPol" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLPPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage3.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage3.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage3.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage3.lastPage();">
      </Div>	
 	</div>	 
<hr class=line>   
    <Div  id= "divEY" style= "display: 'none'">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanEYGrid" >
  					</span> 
  			  	</td>
  			</tr>  			  			
    	</table>
    	<Input class= cssButton type=Button value="使  用" onclick="doEYSave()">
    	<Input class= cssButton type=Button value="返  回" onclick="doreturn()">
    	</Div>
    	
   <Div  id= "divInsurAcc" style= "display: 'none'">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanInsurAccGrid" >
  					</span> 
  			  	</td>
  			</tr>  			  			
    	</table>
    	<Input class= cssButton type=Button value="使  用" onclick="doInsurAccSave()">
    	<Input class= cssButton type=Button value="返  回" onclick="doreturn1()">
   </Div>
   
      <Div  id= "divBlog" style= "display: 'none'">
      	<table  class= common>
      	<tr>
				<td class=titleImg align=center>磁盘导入错误信息查询－请输入查询条件:</td>
			  </tr>
			  </table>
			  <table  class= common>
			  <TR class=common>
				<TD class=title>导入文件名</TD>
				<TD class=input>
					<Input class="common wid" name=BatchNo id=BatchNo>
					<TD></TD>
					<TD></TD>
					<TD><INPUT VALUE="查  询" class=cssButton TYPE=button onclick="BlogQuery()">
					<Input class= cssButton type=Button value="返  回" onclick="doreturn2()"></TD>
			  </table>
			  <table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanBlogGrid" >
  					</span> 
  			  	</td>
  			</tr>  			  			
    	</table>
    	<Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">
      </Div>	
    	
   </Div>
     
	<input type=hidden id="fmtransact" name="fmtransact">
	<input type=hidden id="ContType" name="ContType">
	<input type=hidden id="ContNo" name="ContNo">
	<input type=hidden id="PolNo" name="PolNo">
	<input type=hidden id="EdorValiDate" name="EdorValiDate">
	<input type=hidden id="EdorAcceptNo" name="EdorAcceptNo">
  <input type=hidden id="InsuredNo" 		name="InsuredNo"> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


