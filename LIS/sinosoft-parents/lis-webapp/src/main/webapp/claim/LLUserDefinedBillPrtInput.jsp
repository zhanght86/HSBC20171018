<html>
<%
//Name:LLUserDefinedBillPrtInput.jsp
//Function：用户自定义打印
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
		GlobalInput tG = new GlobalInput();
		tG = (GlobalInput)session.getValue("GI"); 
		
		String tClmNo = request.getParameter("ClmNo");	//赔案号
		String tCustNo = request.getParameter("CustNo"); //出险人编码
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLUserDefinedBillPrt.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLUserDefinedBillPrtInit.jsp"%>
</head>
<body onload="initForm();">
<form method=post name=fm id=fm >
   
    <Table>
        <TR>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivAppAffixList);"></td>
            <td class= titleImg>用户自定义索赔文件清单列表</td>
        </TR>
    </Table>
    <Div id= "DivAppAffixList" style= "display: ''">
        <Table class= common>
            <TR class= common>
                <td style="text-align: left" colSpan=1><span id="spanAffixGrid" ></span></td>
            </TR>      
        </Table>
        <Table>
    	    <TR>    
                <TD><Input class=cssButton type=button id="" name="" value="增 加" onclick="showMyPage(DivAddAffix,true)"></td>   	    	
                <TD><Input class=cssButton type=button id="" name="" value="打 印" onclick="showAffixPrint()"></td>
                <TD><Input class=cssButton type=button id="" name="" value="返 回" onclick="top.close()"></td>
            </TR>
        </Table>
    </Div>

    <Div id= "DivAddAffix" style= "display: 'none'">
    	<hr>
        <Table class= common>
            <TR class= common>
                <TD  class= title>单证名称</TD>                      
                <TD  class= input><Input class=codeno name=AffixCode id=AffixCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1]);">
				<Input class=codename name=AffixName id=AffixName readonly=true></TD>
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                     
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                                                                     
            </TR>     
            <TR class= common>
                <TD  class= title><Input type="checkbox" value="1" name="OtherAffix" id=OtherAffix OnClick="CheckboxClick();">选择其他</Input></TD>                       
                <TD  class= input> <Input class=common name="OtherName" id=OtherName disabled=true></TD>
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                     
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                                                                         
            </TR>                 
        </Table>
        <Table>
    	    <TR>    
                <TD><Input class=cssButton type=button value=" 确 认 " onclick="addAffix()"></td>
                <TD><Input class=cssButton type=button value=" 取 消 " onclick="clearData();showMyPage(DivAddAffix,false)"></td>
            </TR>
        </Table>
    </Div>   
   
        
    
    <!--隐藏区域，用于隐藏登陆信息及上一页面传入的数据--->
    <Input type=hidden id="MngCom" name="MngCom"> <!--登陆信息-->
    <Input type=hidden id="ComCode" name="ComCode">
    <Input type=hidden id="Operator" name="Operator">
    
    <Input type=hidden id="ClmNo" name="ClmNo">   <!--上页传入数据-->
    <Input type=hidden id="CustNo" name="CustNo">

</form>
 	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
