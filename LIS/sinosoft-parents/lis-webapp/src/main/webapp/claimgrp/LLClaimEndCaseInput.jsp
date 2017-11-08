
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//赔案号

	  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID
//=======================END========================
%> 
<head >
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLClaimEndCase.js"></SCRIPT>
   <%@include file="LLClaimEndCaseInit.jsp"%>
</head>

<body  onload="initForm()" >
<form action="./LLClaimEndCaseSave.jsp" method=post name=fm id=fm target="fraSubmit"> 
	<!--table>            
    	<tr>
    		<td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimGrid);"></td>
    		<td class=titleImg> 赔案信息 </td>
    	</tr>
    </table>  
    <div id= "divLLClaimGrid" style= "display: ''">	
        <INPUT class=cssButton name="" VALUE="  共享池  "  TYPE=button onclick="LLClaimQuery()" >     
	      <table  class= common>
	        	<tr  class= common>
	        		<td text-align: left colSpan=1><span id="spanLLClaimGrid" ></span></td>
		    		</tr>
	      </table>
	      <table> 
             <tr>  
                 <td><INPUT class=cssButton VALUE=" 首页 " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr> 
         </table>  
    </div-->         
    <table>            
    	<tr>
    		<td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
    		<td class=titleImg> 赔案详细信息 </td>
    	</tr>
    </table>
		<Div id= "divLLSubReport" style= "display: ''" class="maxbox1">
 			  <table  class= common>
				   <TR  class= common>
				    	<TD  class= title> 立案号 </TD>
				    	<TD  class= input> <Input class="readonly wid" readonly name=ClmNo id=ClmNo ></TD>
				    	<TD  class= title> 赔案状态 </TD>       
				    	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=ClmState id=ClmState ondblclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);"onclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);" onkeyup="return showCodeListKey('llclaimstate',[this,ClmStateName],[0,1]);"><input class=codename name=ClmStateName id=ClmStateName readonly=true></TD>				    	
 				  		<!--TD  class= title> 核算赔付金额</TD>
				    	<TD  class= input> <Input class="readonly" readonly name=StandPay></TD>
				        <TD  class= title> 先给付金额</TD>
				    	<TD  class= input> <Input class="readonly" readonly name=BeforePay ></TD>        
					    <TD  class= title> 核赔赔付金额</TD>
					    <TD  class= input> <Input class="readonly" readonly name=RealPay ></TD-->          					          	
				  		<TD  class= title> 赔付结论</TD>
				    	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=GiveType id=GiveType ondblclick="return showCodeList('llclaimconclusion',[this,GiveTypeName],[0,1]);" onclick="return showCodeList('llclaimconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,GiveTypeName],[0,1]);"><input class=codename name=GiveTypeName id=GiveTypeName readonly=true></TD>
				   </TR>
				   <TR  class= common>
				        <TD  class= title> 理赔员</TD>
				    	<TD  class= input> <Input class="readonly wid" readonly name=ClmUWer id=ClmUWer ></TD>        
				  		<TD  class= title> 结案日期</TD>
				    	<TD  class= input> <Input class="readonly wid" readonly name=EndCaseDate id=EndCaseDate ></TD>
				    	<TD  class= title> 实付标志</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="confGetMoney" id="confGetMoney" ></TD>       
				        <!--TD  class= title> 案件给付类型</TD>
				    	<TD  class= input> <Input class="readonly" readonly name=CasePayType ></TD>        
					    <TD  class= title> 审核类型</TD>
					    <TD  class= input> <Input class="readonly" readonly name=CheckType ></TD-->          					          	
				  </TR>	
        </table>
        <!--table class= common>
            <TR  class= common>
				<TD  class= title> 赔付结论描述 </TD>
		    </tr>	      
			<TR  class= common>				        		
			    <TD  class= input> <textarea name="GiveTypeDesc" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
			</tr>	      
		</table-->
		<INPUT class=cssButton name="addSave" VALUE="结案保存"  TYPE=button onclick="saveClick()" >
		<INPUT class=cssButton name="queryClm" VALUE="赔案查询"  TYPE=button onclick="queryClick()" >
        <INPUT class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();">
        <INPUT class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();">
		<!--INPUT class=cssButton name="" VALUE="结案通知"  TYPE=button onclick="zhoulei()" -->
		<!--INPUT class=cssButton name="" VALUE="客户签收标记"  TYPE=button onclick="zhoulei()" -->				
		<hr class="line">
		<!--按钮区-->
        <INPUT class=cssButton name="EndSave" VALUE="结案确认"  TYPE=button onclick="EndSaveClick()">  
        <INPUT class=cssButton name="goBack" VALUE=" 返  回 "  TYPE=button onclick="goToBack()">
	  </div>
	  <Div id= "divLLParaPrint" style= "display: none">
	  <table>            
    	<tr>
    		<td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,are);"></td>
    		<td class=titleImg> 结案单证打印 </td>
    	</tr>
    </table><Div id= "are" style= "display: ''">
	  	<table  class= common>
	        	<tr  class= common>
	        		<td text-align: left colSpan=1><span id="spanLLReCustomerGrid" ></span></td>
		    		</tr>
	    </table>
		<table  class= common>
			<TR  class= common>
				<TD  class= title> 单证代码 </TD>       
	            <TD  class= Input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name=PrtCode id=PrtCode ondblclick="return showCodeList('llparaprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');"onclick="return showCodeList('llparaprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');" onkeyup="return showCodeListKey('llparaprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');"><input class=codename name=PrtCodeName id=PrtCodeName readonly=true></TD>    
				<TD  class= title> </TD><TD  class= Input></TD><TD  class= title> </TD><TD  class= Input></TD>
			</TR>
		</table>
       <INPUT class=cssButton type=hidden name="" VALUE="批单打印 "  TYPE=button onclick="BatchPrtPrintB();">
	   <INPUT class=cssButton type=hidden name="" VALUE="清单打印"  TYPE=button onclick="BatchPrtPrintC();">
	  </div>
	  </Div>	<!--<Input class=cssButton name="" VALUE="打印单证"  TYPE=button onclick="SinglePrtPrint();">-->
    <%
    //隐藏区,保存信息用
    %> 
    <input type=hidden id="fmtransact" name="fmtransact">    
    <Input type=hidden id="ClaimNo" name="ClaimNo"><!--赔案号-->
    
  	<Input type=hidden id="CustomerNo" name="CustomerNo"><!--客户号《用于打印》，8月6日添加-->
  	
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	
	<!--##########打印流水号，在打印检测时根据  赔案号、单证类型（代码）查询得到######-->
    <input type=hidden id="PrtSeq" name="PrtSeq">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
