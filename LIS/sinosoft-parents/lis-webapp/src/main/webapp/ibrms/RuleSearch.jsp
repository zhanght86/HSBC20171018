
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：CommandMain.jsp
//程序功能：规则管理
//创建日期：2008-9-17
//创建人  ：
//更新记录：  
%>
<%
//==================================================================BEGIN
	String flag= request.getParameter("flag");
    loggerDebug("RuleSearch","FLAG::"+flag);
//===================================================================END
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT type="text/JavaScript" src="RuleSearch.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <%@include file="RuleSearchInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >

<form action="./RuleQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>规则搜索</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>规则名</TD>
		<TD class=input5><Input class="wid" class=common name=RuleName id=RuleName ></TD>
		<TD class=title5>描述</TD>
		<TD class=input5><Input class="wid" class=common name=description id=description></TD>	</TR>
        <TR class=common>
       <TD class=title5>业务模块</TD>
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);"
              onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]); "  ><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename' onchange="return Clean();" readonly=true>
	    </TD> 
        <TD class=title5>BOM</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=BOM id=BOM CodeData=""
			slementtype=nacessary
			ondblclick="showCodeListEx('BOM',[this,BOMName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('BOM',[this,BOMName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('BOM',[this,BOMName],[0,1],null,null,null,true);"><input
			class=codename name="BOMName" id="BOMName" readonly></TD>
	</TR>
	<TR class=common>
	
		<TD class=title5>
                         词条
        </TD>	  
         <TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=BOMItem id=BOMItem CodeData=""
			slementtype=nacessary
			ondblclick="showCodeListEx('BOMItem',[this,BOMItemName],[0,1],null,null,null,true);"
            onclick="showCodeListEx('BOMItem',[this,BOMItemName],[0,1],null,null,null,true);"
			onkeyup="showCodeListKeyEx('BOMItem',[this,BOMItemName],[0,1],null,null,null,true);"><input
			class=codename name="BOMItemName" id="BOMItemName" readonly></TD>
        
         <TD class=title5>
                         状态：
        </TD>	  
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IbrmsState id=IbrmsState class="codeno" 
	          ondblclick="return showCodeList('ibrmsstate',[this,ibrmsstateName],[0,1]);"
              onclick="return showCodeList('ibrmsstate',[this,ibrmsstateName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsstate',[this,ibrmsstateName],[0,1]); " ><input name=ibrmsstateName id=ibrmsstateName  class='codename' onchange="return Clean();" >
	        </TD> 
	</TR>
</Table>
</Div>
  <p>
  <Div id= DivSelect style="display: ''">
    <INPUT VALUE="查  询" TYPE=button class="cssButton" onclick="search()">
     <input class=cssButton type="submit"  value='查看明细' onclick="details()"> <br>
     <!--<a href="javascript:void(0);" class="button" onClick="search();">查    询</a>
     <a href="javascript:void(0);" class="button" onClick="details();">查看明细</a>-->   
  </Div>

<Div  id= "divQueryGrp" style= "display: ''">
    <table>
      <td class= titleImg>
    	规则列表
      </td>
    </table>

     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	          <span id="spanQueryGrpGrid" ></span>
		    </td>
		    <TD class=input style="display: none">						
		    </TD>
	   </tr>
    </table>
       
</div>
</form>
    
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
