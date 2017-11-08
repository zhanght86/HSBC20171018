<%
//程序名称:保全报表打印(初始值）
//程序功能:各模块的清单类型的报表打印  
//创建日期：2003-11-17
//创建人  ：郭翔
//更新记录：  更新人    更新日期     更新原因/内容
//其中：
// * <p>bq1：保全日结(费用类-个险)</p>
// * <p>bq2: 保全月结(综合类-个险)</p>
// * <p>bq3: 保全日结(费用类-法人明细)</p>
// * <p>bq4: 保全月结(综合类-法人明细)</p>
// * <p>bq5: 保全日结(费用类-银代)</p>
// * <p>bq6: 保全月结(综合类-银代)</p>
//add 
// * <p>bq7: 保全日结清单（费用类--团险合计）</p>
// * <p>bq8: 保全月结清单（综合类--团险合计）</p>
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
      
%>
<script>
    var operator = "<%=tG1.Operator%>";   //记录操作员
    var manageCom = "<%=tG1.ManageCom%>"; //记录登陆机构
    var comcode = "<%=tG1.ComCode%>"; //记录登陆机构
</script>
<html> 
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="./BQXReportList.js"></SCRIPT>
<%@include file="BQXReportListInit.jsp"%>
</head>
<body onload="initForm();initElementtype();">
<form method=post name=fm id=fm>
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>
            报表名称
          </TD>  
          <TD class= input5>	
          	<select name='ReportName' id='ReportName_Select' onChange='ReportNameSelect()'>
          		<OPTION VALUE="GrpInfo">团单信息统计</OPTION>
							<OPTION VALUE="GrpSingleInfo">团单下个单信息统计</OPTION>
							<OPTION VALUE="GrpSingleAcc">团单下个单账户轨迹清单</OPTION>
							<OPTION VALUE="GrpEdorInfo">团体保全时效统计</OPTION>
							<OPTION VALUE="GrpLoanInfo">团体保全借还款统计</OPTION>
							<OPTION VALUE="GrpEdorUser">团体保全权限信息统计</OPTION>
          	</select>
          </TD>
          <TD  class= title5></TD>
          <TD class= input5></TD>
       </TR>
    </Table>  

<Div  id= "divGrpInfo" style= "display: ''">   
<div class="maxbox">
	   <Table class= common>
     <TR class= common><TD  class= title colSpan=4><center><strong>报表名称:团单信息统计</Strong></center></TD></TR> 
		 <TR class= common>
		 	<TD class= title5> 团体合同号</TD>
  	   <TD class= input5> <Input class="wid" class=common name=GIGrpContNo id=GIGrpContNo></TD>
		 	<TD  class= title5> 保单状态 </TD>
  	   <TD  class= input5> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GIAppFlag id=GIAppFlag CodeData="0|^1|有效^4|失效" ondblclick="showCodeListEx('AppFlag', [this,GIAppFlagName],[0,1]);" onMouseDown="showCodeListEx('AppFlag', [this,GIAppFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('AppFlag', [this,GIAppFlagName],[0,1]);" ><input class="codename" name=GIAppFlagName id=GIAppFlagName readonly=true></TD>
  	 </TR>
  	  <TR class= common>
  	  	<td class=title5>险种编码</td>
  	  	<td class=input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=GIRiskCode id=GIRiskCode ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,GIRiskCodeName],[0,1],null,null,null,true,'400');" onMouseDown="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,GIRiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,GIRiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=GIRiskCodeName id=GIRiskCodeName readonly=true></td>
  	  	<TD class=title5>管理机构</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=GIManageCom id=GIManageCom readonly ondblclick="return showCodeList('station',[this,GIManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GIManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GIManageComName],[0,1]);"><input class=codename name=GIManageComName id=GIManageComName readonly></TD>
  	  </TR>
  	 <TR class= common>
  	   <TD class= title5>生效日期区间(起)</TD>          
  	   <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GIStartCValidate elementtype=nacessary>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#GIStartCValidate'});" verify="有效开始日期|DATE" dateFormat="short" name=GIStartCValidate id="GIStartCValidate"><span class="icon"><a onClick="laydate({elem: '#GIStartCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
  	   <TD class =title5>生效日期区间(止)</TD>
  	   <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GIEndCValidate elementtype=nacessary>-->
       <Input class="coolDatePicker" onClick="laydate({elem: '#GIEndCValidate'});" verify="有效开始日期|DATE" dateFormat="short" name=GIEndCValidate id="GIEndCValidate"><span class="icon"><a onClick="laydate({elem: '#GIEndCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
       </TD>
		 </TR>
		 
    </Table> 




	<Div  id= "divGrpPol" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage1.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage1.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage1.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage1.lastPage();"> 			
	</DIV>
    <!-- <INPUT VALUE="预  览" class = cssButton TYPE=button onclick="QueryGrpInfo();">
 <INPUT VALUE="打印报表" class = cssButton TYPE=button onclick="PrintGrpInfo();">  --><br>
 <a href="javascript:void(0);" class="button" onClick="QueryGrpInfo();">预    览</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpInfo();">打印报表</a><br>
</DIV>
</Div>
<Div  id= "divGrpSingleInfo" style= "display: none"> 
 	<div class="maxbox"> 
 <Table class= common>
  <TR class= common><TD  class= title colSpan=4><center><strong>报表名称:团单下个单信息统计</Strong></center></TD></TR>  
	<TR class= common>
		<TD class= title5> 团体合同号</TD>
    <TD class= input5> <Input class="wid" class=common name=GSGrpContNo elementtype=nacessary></TD>
		<TD  class= title5> 保单状态 </TD>
    <TD  class= input5> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GSAppFlag id=GSAppFlag CodeData="0|^1|有效^4|失效" ondblclick="return showCodeListEx('AppFlag', [this,GSAppFlagName],[0,1]);" onMouseDown="return showCodeListEx('AppFlag', [this,GSAppFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('AppFlag', [this,GSAppFlagName],[0,1]);" ><input class="codename" name=GSAppFlagName id=GSAppFlagName readonly=true></TD>
  </TR>
  <TR class= common>
    <TD class= title5>生效日期区间(起)</TD>          
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSStartCValidate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GSStartCValidate'});" verify="有效开始日期|DATE" dateFormat="short" name=GSStartCValidate id="GSStartCValidate"><span class="icon"><a onClick="laydate({elem: '#GSStartCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class =title5>生效日期区间(止)</TD>
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSEndCValidate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GSEndCValidate'});" verify="有效开始日期|DATE" dateFormat="short" name=GSEndCValidate id="GSEndCValidate"><span class="icon"><a onClick="laydate({elem: '#GSEndCValidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
 </Table>

 

	<Div  id= "divGrpSinglePol" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpSinglePolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage0.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage0.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage0.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage0.lastPage();"> 			
	</DIV>   
  <!--  <INPUT VALUE="预  览" class = cssButton TYPE=button onclick="QueryGrpSingleInfo();">
 <INPUT VALUE="打印报表" class = cssButton TYPE=button onclick="PrintGrpSingleInfo();">-->
 <a href="javascript:void(0);" class="button" onClick="QueryGrpSingleInfo();">预    览</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpSingleInfo();">打印报表</a><br>
   
</DIV>   
</Div>
<Div  id= "divGrpSingleAcc" style= "display: none">
	<div class="maxbox">
	<Table class= common>
  	<TR class= common><TD  class= title colSpan=4><center><strong>报表名称:团单下个单账户轨迹清单</Strong></center></TD></TR>
  	<TR class= common>
			<TD class= title5> 团体合同号</TD>
    	<TD class= input5> <Input class="wid" class=common name=GSAGrpContNo id=GSAGrpContNo elementtype=nacessary></TD>
    	<TD class= title5> 个人保单号</TD>
    	<TD class= input5> <Input class="wid" class=common name=GSAContNo id=GSAContNo elementtype=nacessary></TD>
    </TR>
    <TR class= common>
    	<TD class= title5>交费日期(起)</TD>          
    	<TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSAStartPayDate>-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#GSAStartPayDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GSAStartPayDate id="GSAStartPayDate"><span class="icon"><a onClick="laydate({elem: '#GSAStartPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
    	<TD class =title5>交费日期(止)</TD>
    	<TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GSAEndPayDate>-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#GSAEndPayDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GSAEndPayDate id="GSAEndPayDate"><span class="icon"><a onClick="laydate({elem: '#GSAEndPayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
  	</TR> 
  </Table>

 

	<Div  id= "divGrpSingleTrace" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpSingleAccGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();"> 			
	</DIV> 
   <!-- <INPUT VALUE="预  览" class = cssButton TYPE=button onclick="QueryGrpSingleAcc();">
 <INPUT VALUE="打印报表" class = cssButton TYPE=button onclick="PrintGrpSingleAcc();">-->
 <a href="javascript:void(0);" class="button" onClick="QueryGrpSingleAcc();">预    览</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpSingleAcc();">打印报表</a><br>

</Div>
</Div>
<Div  id= "divGrpLoanInfo" style= "display: none">
	<div class="maxbox">
	<Table class= common>
  	<TR class= common><TD  class= title colSpan=4><center><strong>报表名称:团体保全借还款统计</Strong></center></TD></TR>
  	<TR class= common>
  		<TD class=title5>管理机构</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GLManageCom id=GLManageCom ondblclick="return showCodeList('station',[this,GLManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GLManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GLManageComName],[0,1]);"><input class=codename name=GLManageComName id=GLManageComName readonly></TD>
  		<TD  class= title5>借款状态</TD><TD  class= input5> <input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GLPayOffFlag id=GLPayOffFlag CodeData="0|^0|未还清^1|还清" ondblclick="return showCodeListEx('PayOffFlag', [this,GLPayOffFlagName],[0,1]);" onMouseDown="return showCodeListEx('PayOffFlag', [this,GLPayOffFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('PayOffFlag', [this,GLPayOffFlagName],[0,1]);" ><input class="codename" name=GLPayOffFlagName id=GLPayOffFlagName readonly=true></TD>
  	</TR>
  <TR class= common>
    <TD class= title5>借款日期区间(起)</TD>          
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GLStartLoanDate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GLStartLoanDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GLStartLoanDate id="GLStartLoanDate"><span class="icon"><a onClick="laydate({elem: '#GLStartLoanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class =title5>借款日期区间(止)</TD>
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GLEndLoanDate>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GLEndLoanDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GLEndLoanDate id="GLEndLoanDate"><span class="icon"><a onClick="laydate({elem: '#GLEndLoanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
  </Table>

 		
	
		<Div  id= "divGrpLoan" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpLoanGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage3.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage3.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage3.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage3.lastPage();"> 			
	</DIV>
   <!-- <INPUT VALUE="预  览" class = cssButton TYPE=button onclick="QueryGrpLoanInfo();">
 		<INPUT VALUE="打印报表" class = cssButton TYPE=button onclick="PrintGrpLoanInfo();">-->
        <a href="javascript:void(0);" class="button" onClick="QueryGrpLoanInfo();">预    览</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpLoanInfo();">打印报表</a><br>

</Div>
</Div>
<Div  id= "divGrpEdorInfo" style= "display: none">
	<div class="maxbox">
	<Table class= common>
  	<TR class= common><TD  class= title colSpan=4><center><strong>报表名称:团体保全时效统计</Strong></center></TD></TR>
  	<TR class= common>
  		<TD class= title5>团体合同号</TD><TD class= input5> <Input class="wid" class=common name=GEGrpContNo id=GEGrpContNo></TD>
  		<TD class=title5>管理机构</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GEManageCom id=GEManageCom ondblclick="return showCodeList('station',[this,GEManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GEManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GEManageComName],[0,1]);"><input class=codename name=GEManageComName id=GEManageComName readonly></TD>
  	</TR>
  	<TR class=common>
  		<TD class= title5>保全项目</TD><TD  class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="GEEdorType"  id="GEEdorType"  readonly=true ondblclick="getEdorInfo();return showCodeListEx('EdorType',[this,GEEdorTypeName],[0,1],'', '', '', true);" onMouseDown="getEdorInfo();return showCodeListEx('EdorType',[this,GEEdorTypeName],[0,1],'', '', '', true);" onkeyup="getEdorInfo();return showCodeListKeyEx('GEEdorType',[this,GEEdorTypeName],[0,1],'', '', '', true);"><input class=codename name="GEEdorTypeName" id="GEEdorTypeName" readonly=true></TD> 
  		<TD class= title5>操作员</TD> <TD class= input5><Input class="wid" class=common name=GEdorOperator id=GEdorOperator></TD>
  	</TR>
  	<TR class= common>
    <TD class= title5>保全确认日期区间(起)</TD>          
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorConfStartDate elementtype=nacessary>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorConfStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GEdorConfStartDate id="GEdorConfStartDate"><span class="icon"><a onClick="laydate({elem: '#GEdorConfStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class =title5>保全确认日期区间(止)</TD>
    <TD class=input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorConfEndDate elementtype=nacessary>-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorConfEndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GEdorConfEndDate id="GEdorConfEndDate"><span class="icon"><a onClick="laydate({elem: '#GEdorConfEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
	</TR>
  </Table>

	 

	
	<Div  id= "divGrpEdor" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpEdorGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage5.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage5.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage5.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage5.lastPage();"> 			
	</DIV>
   <!-- <INPUT VALUE="预  览" class = cssButton TYPE=button onclick="QueryGrpEdorInfo();">
	 <INPUT VALUE="打印报表" class = cssButton TYPE=button onclick="PrintGrpEdorInfo();">-->
      <a href="javascript:void(0);" class="button" onClick="QueryGrpEdorInfo();">预    览</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpEdorInfo();">打印报表</a><br>
</Div>
</Div>
<Div  id= "divGrpEdorUser" style= "display: none">
	<div class="maxbox">
		<Table class= common>
  		<TR class= common><TD  class= title colSpan=4><center><strong>报表名称:团体保全权限信息统计</Strong></center></TD></TR>
			<TR class= common>
  			<TD class= title5>权限级别</TD><TD class= input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" readonly name="GEdorPopedom" id="GEdorPopedom" verify="团单权限级别|Code:GEdorPopedom" ondblclick="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onMouseDown="showCodeList('GEdorPopedom',[this,GEdorPopedomName],[0,1])" onkeyup="showCodeListKey('GEdorPopedom',[this,GEdorPopedomName],[0,1])"><input type="text" class="codename" name="GEdorPopedomName" id="GEdorPopedomName" readonly></TD>
  			<TD class= title5>所属机构</TD><TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GEUManageCom id=GEUManageCom ondblclick="return showCodeList('station',[this,GEUManageComName],[0,1]);" onMouseDown="return showCodeList('station',[this,GEUManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,GEUManageComName],[0,1]);"><input class=codename name=GEUManageComName id=GEUManageComName readonly></TD>
  		</TR>
			<TR class= common>
    		<TD class= title5>用户建立时间区间(起)</TD>          
    		<TD class= input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorUserStartDate>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorUserStartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GEdorUserStartDate id="GEdorUserStartDate"><span class="icon"><a onClick="laydate({elem: '#GEdorUserStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
    		<TD class= title5>用户建立时间区间(止)</TD>
    		<TD class= input5><!--<Input class="coolDatePicker" dateFormat="short" name=GEdorUserEndDate>-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#GEdorUserEndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=GEdorUserEndDate id="GEdorUserEndDate"><span class="icon"><a onClick="laydate({elem: '#GEdorUserEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
			</TR>
			<TR class= common>
  			<TD class= title5>代码</TD><TD class= input5><input class="wid" type="text" class="common" name="GEdorUserCode" id="GEdorUserCode"></TD>
  			<TD class= title5>姓名</TD><TD class= input5><input class="wid" type="text" class="common" name="GEdorUserName" id="GEdorUserName"></TD>
  		</TR>
  		<TR class= common>
  			<TD class= title5>人员状态</TD><TD class= input5><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" readonly name=GEUserState id=GEUserState CodeData="0|^0|有效^1|失效" ondblclick="return showCodeListEx('UserState', [this,GEUserStateName],[0,1]);" onMouseDown="return showCodeListEx('UserState', [this,GEUserStateName],[0,1]);" onkeyup="return showCodeListKeyEx('UserState', [this,GEUserStateName],[0,1]);" ><input class="codename" name=GEUserStateName id=GEUserStateName readonly=true></TD>
  			<TD class= title5></TD><TD class= input5></TD>
  		</TR>	
		</Table>
		

	 	
 
	
		<Div  id= "divGrpEdorUserInfo" style= "display: none" align="center">
      	<table  class= common>
       		<tr  class= common>
      	  	<td style=" text-align: left" colSpan=1>
  					<span id="spanGrpEdorUserGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage6.firstPage();"> 
        <INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage6.previousPage();"> 					
        <INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage6.nextPage();"> 
        <INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage6.lastPage();"> 			
	</DIV>
   <!-- <INPUT VALUE="预  览" class = cssButton TYPE=button onclick="QueryGrpEdorUserInfo();">
	 	<INPUT VALUE="打印报表" class = cssButton TYPE=button onclick="PrintGrpEdorUserInfo();">-->
        <a href="javascript:void(0);" class="button" onClick="QueryGrpEdorUserInfo();">预    览</a>
 <a href="javascript:void(0);" class="button" onClick="PrintGrpEdorUserInfo();">打印报表</a>
</Div>
</Div>
 </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"> </span> 
<br><br><br><br>
</body>
</html>
