<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI"); 
	String tClmNo = request.getParameter("ClmNo");	//赔案号
	String tCustNo = request.getParameter("CustNo"); //出险人编码	
%>      
    <title>赔案结案单证打印</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLClaimEndCaseAffixPrt.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimEndCaseAffixPrtInit.jsp"%>
</head>

<body  onload="initForm();">
<!--登录画面表格-->
<br>
<form name=fm id=fm target=fraSubmit method=post>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLEndCasePrtInfo);"></TD>
            <TD class= titleImg> 打印信息 </TD>
        </TR>
    </Table>      
    <Div  id= "divLLEndCasePrtInfo" class=maxbox1 style= "display: " >
		<Table  class= common>
			<TR  class= common>
				<TD  class= title> 赔案号 </TD>
				<TD  class= input> <Input class="readonly wid" readonly name=ClaimNo id=ClaimNo ></TD>
				<TD  class= title> 客户号码 </TD>       
				<TD  class= input> <Input class="readonly wid" readonly name=customerNo id=customerNo ></TD>			    	     					          	
				<TD  class= title> </TD>
				<TD  class= input> </TD>
			</TR>
			<TR  class= common>
				<TD  class= title> 单证代码 </TD>       
				<TD  class= Input><Input class=codeno readonly=true name=PrtCode id=PrtCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llallprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');" onkeyup="return showCodeListKey('llallprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');" onFocus="showDIVInqGrid();"><input class=codename name=PrtCodeName id=PrtCodeName readonly=true></TD>   				    	     					          	
				<TD  class= title> </TD>
				<TD  class= input> </TD>
				<TD  class= title> </TD>
				<TD  class= input> </TD>
		</Table>
    </Div>   		
    <Div id= "divLLInqApplyGrid" style= "display: none" >
    	<hr class=line>
    	<table  class= common>
	        	<tr  class= common>
	        		<td style="text-align: left" colSpan=1><span id="spanLLInqApplyGrid" ></span></td>
		    		</tr>
	    </table>
	    <hr class=line>
    </Div>
    
    <Div id= "divLLInqFeeGrid" style= "display: none" >
    	<hr>
    	<table  class= common>
	        	<tr  class= common>
	        		<td style="text-align: left" colSpan=1><span id="spanLLInqFeeGrid" ></span></td>
		    		</tr>
	    </table>
	    <hr class=line>
    </Div>   
    
    <Table>
		<Input class=cssButton VALUE="打  印" TYPE=button onclick="showPrtAffix();"> 
		<Input class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();">  
    </Table>     
    <!---***隐藏表单区域*****-----> 
    <Input type=hidden id="ClmNo" name="ClmNo">   <!----赔案号---------->
    <Input type=hidden id="RptNo" name="RptNo">
    
    <Input type=hidden id="CustNo" name="CustNo" ><!-----客户号--------->
    
    <Input type=hidden id="PrtSeq" name="PrtSeq" ><!-----打印流水号-----> 
    
    <Input type=hidden id="ClmNo3" name="ClmNo3"><!---赔案号，用于调查打印--->
    <Input type=hidden id="Payee"  name="Payee"> <!---查勘人--->
    <Input type=hidden id="InqNo" name="InqNo" ><!-----调查序号-----> 
    
    <Input type=hidden id="fmtransact" name="fmtransact" ><!---动作--->
       
</Form>
	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>     
</body>
</html>
