<html>
<%
//程序名称 :FIRuleEngineService.jsp
//程序功能 :数据差异核对
//创建人 :范昕
//创建日期 :2008-09-28
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIRuleEngineService.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIRuleEngineServiceInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./FIRuleEngineServiceSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divFIRuleEngineService" style= "display: ''">
 		<table>
    	<tr>
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,hub1a);"></td>
    		 <td class= titleImg>
        		数据差异核对
       		 </td>   		 
    	</tr>
    </table>
    <Div id= "hub1a" style= "display: ''"><div class="maxbox">
   	<Table class= common>
   	
   	<TR  class= common>
  				<TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true>
				</TD>
				<TD class= title5>
					  版本状态
				 </TD>
				 <TD class=input5>
				 	<input class="wid" class=readonly name=VersionState2 id=VersionState2 readonly=true>
				</TD>
  		</TR>
  		<TR  class= common>
  				<TD class= title5>
					  版本起始日期
				 </TD>
				 <TD class=input5>
				 	<!--<input class=readonly name=StartDay readonly=true>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
				<TD class= title5>
					  版本结束日期
				 </TD>
				 <TD class=input5>
				 	<!--<input class=readonly name=EndDay readonly=true>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</TD>
  		</TR>
  		
		 <TR  class= common>   
          		<TD  class= title5 width="25%">开始日期</TD>
          		<TD  class= input5 width="25%">
                <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="开始日期|notnull" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                 </TD>      
          		<TD  class= title5 width="25%">结束日期</TD>
          		<TD  class= input5 width="25%">
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|notnull" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>  
     	 </TR> 
  		<TR  class= common>
  				<TD class= title5>
					  事件点汇总
				 </TD>
				 <TD class=input5>
				 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=callpoint id=callpoint verify="事件点汇总|NOTNULL" ondblClick="showCodeList('callpointB',[this,callpointName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpointB',[this,callpointName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpointB',[this,callpointName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=callpointName id=callpointName readonly=true elementtype=nacessary>					
				</TD>
               <TD class= title5>请逐一选择您所要添加的事件点并进行追加</TD> 
               <TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=callpointB id=callpointB verify="事件点|NOTNULL" readonly=true ondblClick="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=callpointBName id=callpointBName readonly=true elementtype=nacessary>			</TD>
			</TR>
		<!--	<TR  class= common>
				<TD class= common align=left>
		   	   
		    	</TD>
		    	 <TD class= common align=left>
		     	 
		    	</TD>
		    	<TD class= title5>
					  请逐一选择您所要添加的事件点并进行追加
				 </TD>
		    	<TD class=input5>
		     	 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=callpointB id=callpointB verify="事件点|NOTNULL" readonly=true ondblClick="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onMouseDown="showCodeList('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" onkeyup="showCodeListKey('callpoint',[this,callpointBName],[0,1],null,'CallPointID','codetype',[1]);" ><input class=codename name=callpointBName id=callpointBName readonly=true elementtype=nacessary>					
		    	</TD>
  		</TR>-->
   </Table>
   </Div>
   </Div>
  
 <!-- <INPUT VALUE="版本查询" TYPE=button class= cssButton name= addbutton onclick="VersionQuery()" >-->
  <!--<INPUT VALUE="追  加" TYPE=button class= cssButton name="superaddbutton" onclick="return superaddClick();">-->
  <!--<INPUT VALUE="清  空" TYPE=button class= cssButton name="clearbutton" onclick="return clearClick();">-->
   <input type=hidden id="OperateType" name="OperateType">
   <INPUT type=hidden name=VersionState value=''>
	 <!--<INPUT VALUE="数据差异核对" TYPE=button class= cssButton name="datajudge" onclick="return DataJudge();"> -->
     
     <a href="javascript:void(0);" name= addbutton class="button" onClick="VersionQuery();">版本查询</a>
     <a href="javascript:void(0);" name="superaddbutton" class="button" onClick="return superaddClick();">追    加</a>
     <a href="javascript:void(0);" name="clearbutton" class="button" onClick="return clearClick();">清    空</a>
     <a href="javascript:void(0);" name="datajudge" class="button" onClick="return DataJudge();">数据差异核对</a>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
