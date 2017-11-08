
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
    loggerDebug("RuleQuery","FLAG::"+flag);
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
  <SCRIPT type="text/JavaScript" src="RuleQuery.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT type="text/JavaScript">
  
    var flag=<%=flag%>;
  </SCRIPT>
  <%@include file="RuleQueryInit.jsp"%>
<%
 //flag对应的页面： 1：规则定制 2 ：规则复制 3 规则测试 4：规则修改 5：规则审批 6：规则发布 7：规则作废 8：规则查询 9：规则打印
%>

</head>
<body  onload="initForm(<%=flag%>);initElementtype();" >

<form action="./RuleQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>规则查询</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>规则名</TD>
		<TD class=input5><Input class="wid" class=common name=RuleName id=RuleName ></TD>
			
		<TD class=title5>描述</TD>
		<TD class=input5><Input class="wid" class=common name=description id=description></TD>	
		</TR>
        <TR class=common>
       <TD class=title5>业务模块</TD>
        <TD  class= input5>
	          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class="codeno" 
	          ondblclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
              onclick="return showCodeList('ibrmsbusiness',[this,ibrmsbusinessName],[0,1]);" 
	          onkeyup="return showCodeListKey('LTibrmsbusiness',[this,ibrmsbusinessName],[0,1]); "  ><input name=ibrmsbusinessName id=ibrmsbusinessName  class='codename' onchange="return Clean();" readonly=true>
	    </TD> 
        <TD class=title5>
                          生效日期：
        </TD>	  
      
         <TD  class= input5>
          
          <Input class="coolDatePicker" onClick="laydate({elem: '#RuleStartDate'});" verify="开始日期|Date" dateFormat="short" name=RuleStartDate id="RuleStartDate"><span class="icon"><a onClick="laydate({elem: '#RuleStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
	</TR>
	<TR class=common>
	
		<TD class=title5>
                         失效日期：
        </TD>	  
         <TD  class= input5>
          
           <Input class="coolDatePicker" onClick="laydate({elem: '#RuleEndDate'});" verify="开始日期|Date" dateFormat="short" name=RuleEndDate id="RuleEndDate"><span class="icon"><a onClick="laydate({elem: '#RuleEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
        
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
<TR>
</Table>
</Div>
<p>
  <Div id= DivSelect style="display: ''">
    <INPUT VALUE="查  询" TYPE=button class="cssButton" onclick="easyQuery(<%=flag %>)">
     <input class=cssButton type=button  value='查看明细' onclick="RuleDetails()"> 
     
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
     
        <br>
	    <div id="CopyDiv" style="display:none; ">
         <input class=cssButton type="submit" name='Copy' value='复  制' onclick="copy()">
          <!--<a href="javascript:void(0);" name='Copy' class="button" onClick="copy();">复    制</a> -->
        </div> <br>

        <div id="TestDiv" style="display:none">
        <input class=cssButton type="button" name='test' value='测  试' onclick="testRule();">
         <input class=cssButton type="button" name='test1' value='测试通过' onclick="testApprove(true);">
         <input class=cssButton type="button" name='test1' value='测试不通过' onclick="testApprove(false);">
         <!--<a href="javascript:void(0);" name='test' class="button" onClick="testRule();">测    试</a>
         <a href="javascript:void(0);" name='test1' class="button" onClick="testApprove(true);">测试通过</a>
         <a href="javascript:void(0);" name='test1' class="button" onClick="testApprove(false);">测试不通过</a>    -->    
         <input id="TemplateId" type="hidden" name='TemplateId'> 
        </div> 
        
        <div id="ModifyDiv" style="display:none">
          <input class=cssButton type="button" name='Modify' value='修  改' onclick="modify()">
          <!--<a href="javascript:void(0);" name='Modify' class="button" onClick="modify();">修    改</a>-->
		</div> 
         
        <div id="ApproveDiv" style="display:none">
         <input class=cssButton type="button" name='Approve' value='审批通过' onclick="approve()">
          <input class=cssButton type="button" name='UnApprove' value='审批不通过' onclick="unapprove()">
          <!--<a href="javascript:void(0);" name='Approve' class="button" onClick="approve();">审批通过</a>
          <a href="javascript:void(0);" name='UnApprove' class="button" onClick="unapprove();">审批不通过</a>-->
        </div> 
         
        <div id="DeployDiv" style="display:none">
         <input class=cssButton type="button" name='Deploy' value='发   布' onclick="deploy()">
          <!--<a href="javascript:void(0);" name='Deploy' class="button" onClick="deploy();">发    布</a>-->
         
        </div>
         
        <div id="DropDiv" style="display:none">
          <input class=cssButton type="button" name='Drop' value='作   废' onclick="drop()">
          <!--<a href="javascript:void(0);" name='Drop' class="button" onClick="drop();">作    废</a>-->
        </div> 
        
        <div id="PrintDiv" style="display:none">
         <input class=cssButton type="submit" name='Print' value='打  印' onclick="RuleDetails()">
          <!--<a href="javascript:void(0);" name='Print' class="button" onClick="RuleDetails();">打    印</a>-->
        </div> 
       <input type=hidden id="BtnFlag" name= "BtnFlag"> 
       <!-- BtnFlag的赋值:规则状态流转的下一个-->
</div>
</form>
    
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
