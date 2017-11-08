<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWMissionAssign.jsp.
//程序功能：核保任务分配
//创建日期：2005-5-14 16:25
//创建人  ：HWM
//更新记录：  更新人    更新日期     更新原因/内容
//            刘晓松    2006-10-20    加入核保任务分配类型
%> 
<%
	String tFlag = "";
	tFlag = request.getParameter("type");
	tFlag = "1";
%>
<html>
<%	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operFlag = "<%=tFlag%>";		//区分团险和个险的标志
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>";     //记录登陆机构
</script>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="UWMissionAssign.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWMissionAssignInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post id="fm" name=fm target="fraSubmit" action="./UWMissionAssignSave.jsp"> 
    <table class= common border=0 width=100%>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
			<td class= titleImg>请输入查询条件：</td>
		</tr>
	</table>
	<Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common  id="XQYInfo" style="display:''">
      	  <!-- 
          <TD  class= title8>
            投保单号码
          </TD>
          <TD  class= input8>
            <Input class= common name=ContNo >
          </TD> 
           --> 
          <TD  class= title>
            印刷号
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=PrtNo id=PrtNo >
          </TD> 
          <TD  class= title>
            投保人姓名
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=AppntName id=AppntName >
          </TD> <TD  class= title>
            业务员编码
          </TD>
          <TD  class= input>
            <Input class="code wid" name=AgentCode id=AgentCode ondblclick="return queryAgent();" onclick="return queryAgent();">
          </TD>  </TR>
           <TR  class= common>      
         
          <TD  class= title>
            保全受理号
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=EdorAcceptNo id=EdorAcceptNo >
          </TD>  
           <TD  class= title>
            客户/保单号
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=OtherNo id=OtherNo >
          </TD>          
         <TD  class= title>
            申请人
          </TD>
          <TD  class= input>
            <Input class="common wid" name=EdorAppName id=EdorAppName >
          </TD>         
        </TR>
        
  	<!--<TR  class= common  id="BQInfo" style="display:'none'">          
        </TR>-->
        
      	<TR  class= common  id="LPInfo" style="display:none">
          <TD  class= title>
            赔案号
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=QCaseNo id=QCaseNo >
          </TD>  
          <TD  class= title>
            批次号
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=QBatNo id=QBatNo >
          </TD>  
          <TD  class= title>
            被保险人
          </TD>
          <TD  class= input>
            <Input class="common wid" name=QInsuredName id=QInsuredName >
          </TD>  </TR>
          <TR  class= common  id="LPInfo" style="display:none">      
         
          <TD  class= title>
            管理机构
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="showCodeList('station',[this,ManageComName],[0,1]);" onclick="showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true>
          </TD> 
          <TD  class= title>
            核保师编码
          </TD>
          <TD  class= input>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=UserCode id=UserCode ondblclick="showCodeList('UWCode',[this, UserName,ComCode], [0, 1,2]);" onclick="showCodeList('UWCode',[this, UserName,ComCode], [0, 1,2]);" onkeyup="showCodeListKey('UWCode', [this, UserName,ComCode], [0, 1,2]);"><input class=codename name=UserName id=UserName readonly=true>
          </TD> 
          <TD  class=title>核保类型</TD>
		<TD  class=input><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=text class="codeno" name="UWKind" id="UWKind" 
			CodeData="0|^1|新契约人工核保"
			ondblclick="showCodeListEx('uwtype',[this,UWKindName], [0, 1]);" 
            onclick="showCodeListEx('uwtype',[this,UWKindName], [0, 1]);"
			onkeyup="showCodeListKeyEx('uwtype',[this,UWKindName], [0, 1]);" value='1'><input type=text class="codename" name="UWKindName" id="UWKindName" value="新契约人工核保"><font   color="#FF0000">*</font>
			</TD>        
        </TR>
        
<!--        <TR  class= common>         
          <TD  class= title>
            业务员编码
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode ondblclick="return queryAgent();">
          </TD>
		      <TD  class= title>
            核保师编码
          </TD>
          <TD  class= input>
            <Input class="codeno" name=UserCode ondblclick="showCodeList('UWCode',[this, UserName,ComCode], [0, 1,2]);" onkeyup="showCodeListKey('UWCode', [this, UserName,ComCode], [0, 1,2]);"><input class=codename name=UserName readonly=true>
          </TD>
		      <TD  class= title>
            核保师所属机构
          </TD>
          <TD  class= input>
         		<input class="common" readonly name=ComCode readonly=true>       
          </TD>
        </TR> -->
      <TR class=common>
			  
           
          <!-- 
          <TD  class= title>
            核保师所属机构
          </TD>
          <TD  class= input>
         		<input class="common" readonly name=ComCode readonly=true>       
          </TD> 
           -->   
		 </TR>
		<TR  class= common>  
		
	  <!--
      <TD  class= title8 id= "XQYN" style= "display:'none'" >
            保单类型
          </TD>
		    <TD  class= input id= "XQYT" style= "display:'none'" >
          	<Input class="code" name=ActivityId 
            CodeData="0|^0000002004|新契约团险 ^0000001100|新契约个险  ^0000008005|保全团险 ^0000000005|保全个险 ^0000005505|理赔二核 "
            ondblClick="showCodeListEx('ActivityId',[this]);"
            onkeyup="showCodeListKeyEx('ActivityId',[this]);">
           </TD> -->
      
      <TD  class= title >
            保单类型
          </TD>
		    <TD  class= input >
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" id="ContType" name=ContType id=ContType value="1"  CodeData="0|^1|个单 "  onchange="getActivityID('contype')" ondblclick="showCodeListEx('contype',[this,ContTypeName], [0, 1]);" onclick="showCodeListEx('contype',[this,ContTypeName], [0, 1]);" onkeyup="showCodeListKeyEx('contype',[this,ContTypeName], [0, 1]);"><input type=text class="codename" name="ContTypeName" id="ContTypeName" value="个单">
           </TD>
           
      <TD  class= title>
            核保状态
      </TD> 
      <TD class=input>
      	<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ActivityStatus id=ActivityStatus 
      		ondblClick="showCodeList('uwStatus',[this,ActivityStatusName],[0,1]);"     
            onclick="showCodeList('uwStatus',[this,ActivityStatusName],[0,1]);" 
      		onkeyup="showCodeListKey('uwStatus',[this,ActivityStatusName],[0,1]);"><input type=text class="codename" name="ActivityStatusName" id="ActivityStatusName">
      </TD>
      <TD  class= title></TD>
      <TD class=input></TD>
		</TR>
    </table>
    </DIV>
		<!--添加层-->		
		<span id="spanCode"  style="display: none; position:absolute; slategray; left: 700px; top: 264px; width: 150px; height: 44px;"> 
        </span> 

          <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClickAll();"> --> 
          <a href="javascript:void(0);" class="button" onClick="easyQueryClickAll();">查    询</a>   
      
   <DIV id=DivLCContInfo STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllPolGrid);">
    		</td>
    		<td class= titleImg>
    			 共享工作池
    		</td>
    	</tr>  	
    </table>
   </Div>
<!--新契约核保工作对列-->			
	<Div  id= "divAllPolGrid" style= "display:''" align = center>
	<table  class= common >
		<tr  class=common>
			<td text-align: left colSpan=1 >
			<span id="spanAllPolGrid" >
			</span> 
			</td>
		</tr>
	</table>
		  
	</div>
<!--保全核保工作对列-->		
	<Div  id= "divBqPolGrid" style= "display:none" align = center>
	<table  class= common >
		<tr  class=common>
			<td text-align: left colSpan=1 >
			<span id="spanBqPolGrid" >
			</span> 
			</td>
		</tr>
	</table>
    <center>
		<INPUT VALUE="首页"   class=cssButton90 TYPE=button onclick="turnPage1.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage1.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage1.nextPage();"> 
		<INPUT VALUE="尾页"   class=cssButton93 TYPE=button onclick="turnPage1.lastPage();">	    </center>
	</div>
<!--理赔核保工作对列-->	
		<Div  id= "divLpPolGrid" style= "display:none" align = center>
	<table  class= common >
		<tr  class=common>
			<td text-align: left colSpan=1 >
			<span id="spanLpPolGrid" >
			</span> 
			</td>
		</tr>
	</table>
    <center>
		<INPUT VALUE="首页"   class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾页"   class=cssButton93 TYPE=button onclick="turnPage2.lastPage();">	    </center>
	</div>
	
  <br>
    <table class="common">
        <tr class="common">
        	<td class="title5">调配到核保师</td>
            <td class="input5"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" id="DefaultOperator" name=DefaultOperator id=DefaultOperator ondblclick="showCodeList('UWCode',[this, UWType], [0, 1]);" onclick="showCodeList('UWCode',[this, UWType], [0, 1]);" onkeyup="showCodeListKey('UWCode', [this, UWType], [0, 1]);"><input class=codename name=UWType id=UWType readonly=true>
    	 <Input type=hidden id="ApplyType" name="ApplyType" >
    	 <Input type=hidden id="MissionID" name="MissionID" >
    	 <Input type=hidden id="SubMissionID" name="SubMissionID" >
    	 <Input type=hidden id="MngCom" name="MngCom" >
    	 <Input type=hidden id="ActivityID" name="ActivityID">
    	 <Input type=hidden id="UWName" name="UWName" value=""></td> 
            <td class="title5"></td>
            <td class="input5"></td>    
        </tr>
       
    </table>
    <!--<INPUT class=cssButton id="riskbutton" VALUE="调  配" TYPE=button onClick="ApplyUW();">--><br>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="ApplyUW();">调    配</a>
</form><br><br><br><br>
</body>
</html>
