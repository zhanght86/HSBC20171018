<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<% 
//程序名称：GEdorTypeDetailInput.jsp
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

  <SCRIPT src="./GEdorTypeDetail.js"></SCRIPT>
  <%@include file="GEdorTypeDetailInit.jsp"%>
  
  <title>团体保全明细总页面</title> 
</head>

<body  onload="initForm();" >
  <form action="./GEdorTypeDetailSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
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
        </TD>
       
        <TD class = title > 团体保单号 </TD>
        <TD class = input >
        	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
        </TD>   
      </TR>
      <TR class=common>
    	<TD class =title>申请日期</TD>
    	<TD class = input>    		
    		<Input class= "coolDatePicker" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
    	</TD>
    	<TD class =title>生效日期</TD>
    	<TD class = input>
    		<Input class= "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
    	</TD>
    	<TD class = title></TD>
    	<TD class = input></TD>
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
    <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="queryClick();"> 
    
    <br><br>
    
    <Div  id= "divLPInsured" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsuredGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<Div  id= "divPage" align=center style= "display: 'none' ">
        <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage1.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage1.lastPage();"> 			
      </Div>		
  	</div>

	  <br>
	  
	  <hr> 
	  
	  <br>
	  <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
        </td>
        <td class= titleImg>
          修改过的被保人信息
        </td>
      </tr>
    </table>
    
    <table class = common>
      <tr class = common>
      	<td class = title>
      		个人保单号
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=ContNo3 id=ContNo3>
          </TD>
        <td class = title>
      		个人客户号
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=CustomerNo3 id=CustomerNo3>
          </TD>
        <td class = title>
      		客户姓名
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=Name3 id=Name3>
          </TD>  
      </tr>
    </table>
    <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="queryClick2();"> 
    
    <br><br>
    
	  <Div  id= "divLPInsured2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsured2Grid" >
  					</span> 
  			</td>
  			</tr>
    	</table>
    	<Div  id= "divPage2" align=center style= "display: 'none' ">
        <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">					
      </Div>
  	</div>
	  
	  <br>
	 <DIV id="divPEdorButton" align="center" style= "display: ''"> 
	  <Input type=Button value="进入个人保全" class = cssButton onclick="GEdorDetail()">
	  <Input type=Button value="撤销个人保全" class = cssButton onclick="cancelGEdor()">  
	  <Input type=Button value="返  回" class = cssButton onclick="returnParent()">
	  	<br>
	  	<br>
	  	  <DIV id= "divGABatchImport"  style= "display: 'none' ">

	  	<INPUT VALUE="年金转换批量导入" class = cssButton TYPE=button onclick="showPage(this,divSubmit1);"> 	  	
	  </DIV>
	  	  	  <DIV id= "divAmntBatchImport"  style= "display: 'none' ">

	  	<INPUT VALUE="批量导入" class = cssButton TYPE=button onclick="showPage(this,divAmntLoad);"> 	  	
	  </DIV>
	</DIV>
	  	

    <input type=hidden id="EdorAcceptNo" 	name="EdorAcceptNo">
	  <input type=hidden id="ContNo" 				name="ContNo">
	  <input type=hidden id="CustomerNo" 		name="CustomerNo">
	  <input type=hidden id="ContNoBak" 		name="ContNoBak">
	  <input type=hidden id="CustomerNoBak" name="CustomerNoBak">
	  <input type=hidden id="ContType" 			name="ContType">
	  <input type=hidden id="Transact" 			name="Transact">
	  <input type=hidden id="EdorTypeCal" 	name="EdorTypeCal">
	  <input type=hidden id="fmtransact" name="fmtransact">
	  <input type=hidden id="Money" 				name="Money">
  </form>
  <form action="./GrpEdorTypeGALoadSubmit.jsp" method=post name=fm1 id=fm1 target="fraSubmit" ENCTYPE="multipart/form-data">   
  <Div  id= "divSubmit1" style= "display:'none'" align="center" > 
  	

        <Input type="file" name=FileName id=FileName size=20>
        <input name=ImportPath id=ImportPath type= hidden>
        <input class="common wid" name=BatchNo id=BatchNo type=hidden>
        <INPUT class=cssButton VALUE="年金转换导入" TYPE=button onclick="submitLoad();">

    <input type=hidden name=ImportFile id=ImportFile>

       <center>
       	<br>
      		 <a href="./GAEdor.xls">下载年金转换导入模板</a><br> 
      </center>
</Div> 
</form>

<form action="./GEdorTypeAmntLoadSubmit.jsp" method=post name=fm2 id=fm2 target="fraSubmit" ENCTYPE="multipart/form-data">   
  <Div  id= "divAmntLoad" style= "display:'none'" align="center"> 
  	

        <Input type="file" name=FileName id=FileName size=20>
        <input name=ImportPath id=ImportPath type= hidden>
        <input class="common wid" name=BatchNo id=BatchNo type=hidden>
        <INPUT class=cssButton VALUE="导入" TYPE=button onclick="submitAmntLoad();">

    <input type=hidden name=ImportFile id=ImportFile>

       <center>
       	<br>
      		 <a href="./AmntEdor.xls">下载导入模板</a><br> 
      </center>
</Div> 
</form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
  <br/><br/><br/><br/>
</body>
</html>

<script>
  window.focus();
</script>
