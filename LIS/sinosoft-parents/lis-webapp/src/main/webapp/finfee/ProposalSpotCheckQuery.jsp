<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/laydate/laydate.js"></script>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="ProposalSpotCheckQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalSpotCheckQueryInit.jsp"%>
  <title>复核抽查规则查询</title>
  <!-- 
  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" FOR="document" EVENT="onkeydown">
    document_onkeydown();
  </SCRIPT>
  -->
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  var Operator = "<%=tGlobalInput.Operator%>";
  var ComCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form action="" method=post name=fm id="fm" target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);"></td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
    <div class="maxbox1" id="maxbox">
    <table class=common>
    <tr class=common>
    	<TD  class= title5 >
          单证印刷号
        </TD>
        <TD  class= input5>
          <Input class= "common wid"  name=TempFeeNo id="TempFeeNo" >
        </TD>
        <TD  class= title5 >
          外包公司
        </TD>
        <TD  class= input5>
          <Input class= code  name=BPOID id="BPOID" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');"  ondblclick="return showCodeList('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');" onKeyUp="return showCodeListKey('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');">
        </TD>
    </tr>
    <tr>    	    
        <TD  class= title5 >
          管理机构
        </TD>
        <TD  class= input5>
          <Input class= code  name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('comcode',[this]);" onDblClick="return showCodeList('comcode',[this]);" onKeyUp="return showCodeListKey('comcode',[this]);">
        </TD>
        
      <TD class=title5>扫描日期</TD>
	    <TD  class= input5>
	      <Input class= "coolDatePicker" dateFormat="short" name=ScanDate id="ScanDate" onClick="laydate({elem:'#ScanDate'});"><span class="icon"><a onClick="laydate({elem: '#ScanDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>    	
    </tr>
    </table>
    <br>
          <INPUT CLASS=cssButton VALUE="查  询" TYPE=button onClick="easyQueryClick();">  
          <INPUT CLASS=cssButton VALUE="复核抽检" type="hidden" onClick="SpotCheck();">
 </div>   
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 抽检信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display:''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
      </Div>			
  	</div>
    <br><br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
