<html>
<%
//Name:LLRptMAffixListInput.jsp
//Function����֤����֪ͨ��
//Date��2005-05-21 17:44:28
//Author ����־��
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLRptMAffixList.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLRptMAffixListInit.jsp"%>
</head>
<body onload="initForm();">
<form action="./LLRptMAffixListSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,AppAffixList);"></td>
            <td class= titleImg>���뵥֤�嵥</td>
        </tr>
    </table>
    <Div id= "AppAffixList" style= "display: ''">
        <table class= common>
            <tr class= common>
                <td text-align: left colSpan=1><span id="spanAffixGrid" ></span></td>
            </tr>      
        </table></div>
        <table>
    	    <tr>    
                <td><input class=cssButton type=button id="add" name="add" value=" �� �� " onclick="showMyPage(AppAffix,true)"></td>   	    	
                <td><input class=cssButton type=button id="save" name="save" value=" �� �� " onclick="AffixSave()"></td>
                <td><input class=cssButton type=button id="close" name="close" value=" �� �� " onclick="top.close()"></td>
            </tr>
        </table>
    

    <Div id= "AppAffix" style= "display: 'none'" class="maxbox1">
        <table class= common>
            <tr class= common>
                <TD  class= title>��֤����</TD>                      
                <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AffixCode id=AffixCode ondblclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1]);"><input class=codename name=AffixName id=AffixName readonly=true></TD>
                <TD  class= title><input style="vertical-align:middle" type="checkbox" value="1" name="OtherAffix" OnClick="fm.AffixCode.disabled=fm.AffixName.disabled=this.checked;fm.OtherName.disabled=!this.checked;fm.OtherName.value=''"><span style="vertical-align:middle">ѡ������</span></input></TD>                       
                <TD  class= input> <Input class="wid" class=common name="OtherName" id="OtherName" disabled=true></TD>                                    
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                                                                     
            </tr>     
                          
        </table></div>
        <table>
    	    <tr>    
                <td><input class=cssButton type=button value=" ȷ �� " onclick="addAffix()"></td>
                <td><input class=cssButton type=button value=" ȡ �� " onclick="clearData();showMyPage(AppAffix,false)"></td>
            </tr>
        </table>
            
    <input type=hidden id="Operate" name="Operate">
    <input type=hidden id="RptNo" name="RptNo">
    <input type=hidden id="customerNo" name="customerNo">
    <input type=hidden id="ShortCountFlag" name="ShortCountFlag">
    <input type=hidden id="SupplyDateResult" name="SupplyDateResult">
    <input type=hidden id="AffixNo" name="AffixNo">
    <input type="hidden" id="claimTypes" name="claimTypes">
    <input type="hidden" id="cols" name="cols">
    <!--ProcΪҳ������־��Rpt:����  Rgt:���� RgtAdd:�������� -->
    <input type="hidden" id="Proc" name="Proc">    
</form><br><br><br><br>
</body>
</html>
