<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
<%
  //个人下个人	
	String tContNo = "";
	String tFlag = request.getParameter("type");
	String tInputTime = request.getParameter("InputTime");
	loggerDebug("TSWorkPool","tInputTime: "+tInputTime);
  	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var type = "<%=tFlag%>";
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
	var InputTime = "<%=tInputTime%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="TSWorkPool.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="TSWorkPoolInit.jsp"%>
  <title>扫描录入</title>
</head>
<body  onload="initForm();" >
  <form action="./ScanContInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);">
        </td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divSearch" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
	          <TD  class= title5>扫描机构</TD>
	          <TD  class= input5>
	            <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" verify="管理机构|code:station"  onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id="ManageComName" readonly=true>
	          </TD>      	
	          <TD  class= title5>印刷号</TD>
	          <TD  class= input5><Input class= "common wid" name=PrtNo id="PrtNo"></TD>
	                           
	        </TR>  
	        <TR  class= common>
	          <TD  class= title5>扫描日期</TD>
	          <TD  class= input5>
              <Input class="coolDatePicker" onClick="laydate({elem: '#InputDate'});" verify="扫描日期|DATE" dateFormat="short" name=InputDate id="InputDate"><span class="icon"><a onClick="laydate({elem: '#InputDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> 
            </TD> 
	          <td  class= title5>交费日期</td>
	          <TD  class= input5>
              <Input class="coolDatePicker" onClick="laydate({elem: '#PayDate'});" verify="交费日期|DATE" dateFormat="short" name=PayDate id="PayDate"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
             </TD>
	        </tr>
	        <tr class= common>
	          <TD  class= title5>保单性质</TD>
	          <TD  class= input5>
	          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="state" id="state" 
            					  CodeData="0|^11|个险^35|银代（包括15、25、35）^16|简易^21|中介^11|个人^61|多主险"  onclick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
                                  ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
            					  onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">
	          </TD>              
	        </TR>      
	    </table>
    </div> 
    </div>
    <a href="javascript:void(0)" class=button onClick="easyQueryClick();">查  询</a>
    <a href="javascript:void(0)" class=button onClick="ApplyUW();">申  请</a>
    <!-- <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClick();"> 
    <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();"> -->

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>共享工作池</td>
    	</tr>
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID    id="MissionID" value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID id="SubMissionID" value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID   id="ActivityID" value= "">        
          <INPUT  type= "hidden" class= Common name= InputTime    id="InputTime" value= "">        
    	</tr>
    </table>
  	<Div  id= "divGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" ></span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">						
  	</div>
  	
  <br>

   <DIV id="DivLCContInfo" STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch1);">
    		</td>
    		<td class= titleImg>个人工作池</td>
    	</tr>  	
    </table>
    <div class="maxbox1">
      <Div  id= "divSearch1" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
	          <TD  class= title5>扫描机构</TD>
	          <TD  class= input5>
	            <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom1 id="ManageCom1" verify="管理机构|code:station" onClick="return showCodeList('station',[this,ManageComName1],[0,1]);"  ondblclick="return showCodeList('station',[this,ManageComName1],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 readonly=true>
	          </TD>      	
	          <TD  class= title5>印刷号</TD>
	          <TD  class= input5><Input class="common wid"  name=PrtNo1 id="PrtNo1"></TD>
	                           
	        </TR>  
	        <TR  class= common>
	          <TD  class= title5>扫描日期</TD>
	          <TD  class= input5>
              <Input class="coolDatePicker" onClick="laydate({elem: '#InputDate1'});" verify="扫描日期|DATE" dateFormat="short" name=InputDate1 id="InputDate1"><span class="icon"><a onClick="laydate({elem: '#InputDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> 
            </TD> 
	          <td  class= title5>交费日期</td>
	          <TD  class= input5>
              <Input class="coolDatePicker" onClick="laydate({elem: '#PayDate1'});" verify="交费日期|DATE" dateFormat="short" name=PayDate1 id="PayDate1"><span class="icon"><a onClick="laydate({elem: '#PayDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> 
            </TD>
	        </tr>
	        <tr class= common>
	          <TD  class= title5>保单性质</TD>
	          <TD  class= input5>
	          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"   class="code" name="state1"  id="state1"
            					  CodeData="0|^11|个险^35|银代（包括15、25、35）^16|简易^21|中介^11|个人^61|多主险"
                        onclick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
                                  ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
            					  onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">
	          </TD>              
	        </TR>      
	    </table>
    </div> 
    </div>
    <div>
    <a href="javascript:void(0)" class=button onClick="easyQueryClickSelf();">查  询</a>
    </div>
    <br>
    <!-- <INPUT VALUE="查  询" class = cssButton TYPE=button onclick="easyQueryClickSelf();">  -->
  	<Div  id= "divSelfGrpGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         <INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage2.firstPage();"> 
         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage2.previousPage();"> 					
         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage2.nextPage();"> 
         <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage2.lastPage();"> 					
  	</div>

  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
