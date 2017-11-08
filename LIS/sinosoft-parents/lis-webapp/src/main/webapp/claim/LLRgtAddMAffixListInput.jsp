<html>
<%
//Name:LLRgtAddMAffixListInput.jsp
//Function：单证补充主界面
//Date：2005-07-1 17:44:28
//Author ：yuejw
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		  
	      String tClmNo=request.getParameter("ClmNo"); //赔案号
	      String tCustomerNo=request.getParameter("CustomerNo"); //客户号   
	      String tProc=request.getParameter("Proc"); //标志	          		  
	%> 	
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLRgtAddMAffixList.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLRgtAddMAffixListInit.jsp"%>
</head>
<body onLoad="initForm();">
<form  method=post name=fm id=fm target="fraSubmit">
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,AppAffixList);"></td>
            <td class= titleImg>申请单证清单</td>
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
    	    	<td><input class=cssButton  type=button id="build" name="build" value="生成单证" onClick="AffixBuild()"></td> 
    	    	<td><input class=cssButton type=button id="add" name="add"    value="增加单证" onClick="showMyPage(AppAffix,true)"></td> 
                <td><input class=cssButton type=button id="save" name="save"   value="保存单证" onClick="AffixSave()"></td>
                <td><input class=cssButton type=button id="close" name="close" value="返    回" onClick="returnParent()"></td>
            </tr>
        </table>
    

    <Div id= "AppAffix" style= "display: 'none'">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,AppAffixDetail);"></td>
            <td class= titleImg>增加单证信息</td>
        </tr>
    </table>
    <Div id= "AppAffixDetail" style= "display: ''" class="maxbox1">
        <table class= common>
            <tr class= common>
                <TD  class= title>单证名称</TD>                       
                <TD  class= input><Input style="background: url(../common/images/select--bg_03.png) no-repeat center right; " class=codeno name=AffixCode id=AffixCode onDblClick="showCodeList('llmaffix',[this,AffixName],[0,1]);"  onclick="showCodeList('llmaffix',[this,AffixName],[0,1]);"onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1]);"><input class=codename name=AffixName id=AffixName readonly=true></TD>
                 <TD  class= title><input style="vertical-align:middle" type="checkbox" value="1" name="OtherAffix" OnClick="fm.AffixCode.disabled=fm.AffixName.disabled=this.checked;fm.OtherName.disabled=!this.checked;fm.OtherName.value=''"><span style="vertical-align:middle">选择其他</span></input></TD>                       
                <TD  class= input> 
                	<Input class="wid" class=common name="OtherName" id="OtherName" disabled=true>
                </TD>                                    
                <TD  class= title></TD>                                       
                <TD  class= input></TD>                                                                                     
            </tr>      
                           
        </table></div>
    </div> 
        <table>
    	    <tr>    
                <td><input class=cssButton type=button value=" 确 认 " onClick="addAffix()"></td>
                <td><input class=cssButton type=button value=" 取 消 " onClick="clearData();showMyPage(AppAffix,false)"></td>
            </tr>
        </table>
           
    <input type=hidden id="Operate" name="Operate">
    <input type=hidden id="ClmNo" name="ClmNo">
    <input type=hidden id="CustomerNo" name="CustomerNo">
    <input type=hidden id="ShortCountFlag" name="ShortCountFlag">
    <input type=hidden id="SupplyDateResult" name="SupplyDateResult">
    <input type=hidden id="AffixNo" name="AffixNo">
    <input type="hidden" id="claimTypes" name="claimTypes">
    <input type="hidden" id="cols" name="cols">
    <!--Proc为页面进入标志，Rpt:报案  Rgt:立案 RgtAdd:立案补充 -->
    <input type="hidden" id="Proc" name="Proc">    
</form>
</body>
</html>
