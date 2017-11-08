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
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./GEdorTypeMultiDetail.js"></SCRIPT>
  <%@include file="GEdorTypeMultiDetailInit.jsp"%>  
  <title>团体保全明细总页面</title> 
</head>
<body  onload="initForm();" >
  <form action="./GEdorTypeMultiDetailSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  <input type=hidden readonly name=EdorAcceptNo id=EdorAcceptNo >
  <div class=maxbox1>
    <table class=common>
      <TR  class= common> 
        <TD  class= title > 批单号</TD>
        <TD  class= input > 
          <input class="readonly wid" readonly name=EdorNo id=EdorNo >
        </TD>
        <TD class = title > 批改类型 </TD>
        <TD class = input >
        	<Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
        </TD>       
        <TD class = title > 团体保单号 </TD>
        <TD class = input >
        	<input class = "readonly wid" readonly name=GrpContNo id=GrpContNo>
        </TD>   
      </TR>
    </TABLE>     
    <br>
	</div>
	<hr class=line>    
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
    
<Div id= "divLPInsured" style= "display: ''" align="center">   
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
      		<input class = "common wid"  name=CustomerNo id=CustomerNo>
          </TD>
        <td class = title>
      		客户姓名
      		</td>
      	<td class = input>
      		<input class = "common wid"  name=Name id=Name>
          </TD>  
      </tr>
    </table>    
    <Input type=Button value="查   询" class=cssButton onclick="queryClick()">     
    <Input type=Button value="依姓名模糊查询" class=cssButton onclick="queryClick1()">     
    <br><br>    
    
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanLCInsuredGrid" >
  					</span> 
  			  	</td>
  			</tr>
    		</table>    	

        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage0.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage0.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage0.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage0.lastPage();"> 			
	
</div>
<BR>
<BR>
    	<DIV  id= "DivMark7" style= "display: ''" align="center">
<Input type=Button value=" 减  人 " class=cssButton onclick="ZTEdor()"> 
<INPUT class=cssButton type=Button value="批量导入" onclick="showPage(this,divDiskApp);">
<INPUT class=cssButton type=Button value="返  回" onclick="returnParent();">
</DIV>
    	       
      <hr class=line>
  <div id="LPContDiv" style="display: ''">  
	  <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured2);">
        </td>
        <td class= titleImg>
          变动的被保人列表
        </td>
      </tr>
    </table>
	  <Div  id= "divLPInsured2" style= "display: ''" align="center">
      <table  class= common>
       	<tr  class= common>
      	 	<td text-align: left colSpan=1>
  					<span id="spanLCInsured2Grid" >
  					</span> 
  			 	</td>
  			</tr>
    	</table>    	

        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">					
	  <br><hr>
	  <Div  id= "divLPFee" style= "display: ''">
      <table  class= common>
      	<TR class = common >
          <TD  class= title>退费金额合计</TD>
          <TD  class= input><Input class= "readonly wid" readonly name=GetMoney id=GetMoney>元</TD>
		  <TD  ></TD>
		  <TD  ></TD>
		  <TD  ></TD>
		  <TD  ></TD>
        </TR>        
      </table>
    </Div>
  	</div>
  

     </div>	    	
    <br>
    <div id= "divSaveButton" style="display: ''" align="center">

	  <!--Input type=Button value="导入清单" class=cssButton onclick="diskInput()"-->
	  <Input type=Button value=" 保  存 " class=cssButton onclick="ZTSave()">
	  <!--Input type=Button value=" 确定保存 " class=cssButton onclick="ZTAllSave()"-->
	  <Input type=Button value="撤销减人" class=cssButton onclick="cancelPEdor()">	
	  <Input type=Button value="费用明细" class=cssButton onclick="MoneyDetail()">  
	  <Input type=Button value=" 返  回 " class=cssButton onclick="returnParent()">
	 <BR>
	 <BR>

	  <input type=hidden id="ContNo"        name="ContNo">	  
	  <input type=hidden id="ContType"      name="ContType"> 
	  <input type=hidden id="Transact"      name="Transact">
	  <!--<input type=hidden id="EdorAcceptNo"  name="EdorAcceptNo">-->	  
	  <!--input type=hidden id="CustomerNo" name="CustomerNo"-->
	  <input type=hidden id="ContNoBak"     name="ContNoBak">
	  <input type=hidden id="CustomerNoBak" name="CustomerNoBak">		  
	  <input type=hidden id="EdorTypeCal"   name="EdorTypeCal">
	  <input type=hidden id="IfToPubInsu"   name="IfToPubInsu">

	</div>
	<div id="LCGrpContDiv" style= "display: 'none'">   
	  <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPGrpCont);">
        </td>
        <td class= titleImg>
          变动后的合同信息
        </td>
      </tr>
    </table>
	  <Div  id= "divLPGrpCont" style= "display: ''" align="center">
      <table  class= common>
       	<tr  class= common>
      	 	<td text-align: left colSpan=1>
  					<span id="spanLPGrpContGrid" >
  					</span> 
  			 	</td>
  			</tr>
    	</table>    	
				

  	</div>
  </div>
  <br>
  <div id="LCGrpPolDiv" style= "display: 'none'">
  	<table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPGrpPol);">
        </td>
        <td class= titleImg>
          变动的团体险种信息
        </td>
      </tr>
    </table>
	  <Div  id= "divLPGrpPol" style= "display: ''" align="center">
      <table  class= common>
       	<tr  class= common>
      	 	<td text-align: left colSpan=1>
  					<span id="spanLPGrpPolGrid" >
  					</span> 
  			 	</td>
  			</tr>
    	</table>    					

  	</div>
  </div>	  
  </form>
  <form action="./GEdorTypeMultiDetailDisk.jsp" method=post name=fm2 id=fm2 target="fraSubmit" enctype="multipart/form-data"> 
  	 <div id="divDiskApp" style="display: 'none'" align="center">
       <table>
         <TR class= common>
     	 	 <TD class=common>
						<Input type="file" name=FileName id=FileName>
       		 	<Input class= cssButton type=Button value=" 导  入 " onclick="diskInput();">
     	 	 </TD>
     	  </TR>
     </table>
       <center>
      		<a href="./Edor_ZT.xls">下载减人导入模板</a><br> 
      </center>
  </div>
  </form>


  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>  
  <br/><br/><br/><br/>
</body>
</html>
<script>
  window.focus();
</script>
