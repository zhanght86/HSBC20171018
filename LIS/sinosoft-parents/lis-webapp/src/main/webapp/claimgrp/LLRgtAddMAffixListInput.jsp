<html>
<%
//Name:LLRgtAddMAffixListInput.jsp
//Function����֤����������
//Date��2005-07-1 17:44:28
//Author ��yuejw
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		  
	      String tClmNo=request.getParameter("ClmNo"); //�ⰸ��
	      String tCustomerNo=request.getParameter("CustomerNo"); //�ͻ���   
	      String tProc=request.getParameter("Proc"); //��־	          		  
	%> 	
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLRgtAddMAffixList.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLRgtAddMAffixListInit.jsp"%>
</head>
<body onload="initForm();">
<form  method=post name=fm target="fraSubmit">
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
        </table>
        <table>
    	    <tr>   
    	    	<td><input class=cssButton  type=button id="build" name="build" value="���ɵ�֤" onclick="AffixBuild()"></td> 
    	    	<td><input class=cssButton type=button id="add" name="add"    value="���ӵ�֤" onclick="showMyPage(AppAffix,true)"></td> 
                <td><input class=cssButton type=button id="save" name="save"   value="���浥֤" onclick="AffixSave()"></td>
                <td><input class=cssButton type=button id="close" name="close" value="��    ��" onclick="returnParent()"></td>
            </tr>
        </table>
    </div>

    <Div id= "AppAffix" style= "display: 'none'">
    <hr>
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,AppAffixDetail);"></td>
            <td class= titleImg>���ӵ�֤��Ϣ</td>
        </tr>
    </table>
    <Div id= "AppAffixDetail" style= "display: ''">
        <table class= common>
            <tr class= common>
                <TD  class= title>��֤����</TD>                       
                <TD  class= input><Input class=codeno name=AffixCode ondblclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1]);"><input class=codename name=AffixName readonly=true></TD>
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                     
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                                                                     
            </tr>      
            <tr class= common>
                <TD  class= title><input type="checkbox" value="1" name="OtherAffix" OnClick="fm.AffixCode.disabled=fm.AffixName.disabled=this.checked;fm.OtherName.disabled=!this.checked;fm.OtherName.value=''">ѡ������</input></TD>                       
                <TD  class= input> 
                	<Input class=common name="OtherName" disabled=true>
                </TD>
                <TD  class= title></TD>                                       
                <TD  class= title></TD>                                       
                <TD  class= title></TD>                                       
                <TD  class= title></TD>                                                                                       
            </tr>                 
        </table>
        <table>
    	    <tr>    
                <td><input class=cssButton type=button value=" ȷ �� " onclick="addAffix()"></td>
                <td><input class=cssButton type=button value=" ȡ �� " onclick="clearData();showMyPage(AppAffix,false)"></td>
            </tr>
        </table>
    </div>
    </div>        
    <input type=hidden id="Operate" name="Operate">
    <input type=hidden id="ClmNo" name="ClmNo">
    <input type=hidden id="CustomerNo" name="CustomerNo">
    <input type=hidden id="ShortCountFlag" name="ShortCountFlag">
    <input type=hidden id="SupplyDateResult" name="SupplyDateResult">
    <input type=hidden id="AffixNo" name="AffixNo">
    <input type="hidden" id="claimTypes" name="claimTypes">
    <input type="hidden" id="cols" name="cols">
    <!--ProcΪҳ������־��Rpt:����  Rgt:���� RgtAdd:�������� -->
    <input type="hidden" id="Proc" name="Proc">    
</form>
</body>
</html>
