<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：FinFeeSure.jsp
//程序功能：到帐确认
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="FinFeeSure.js"></SCRIPT>
  <%@include file="FinFeeSureInit.jsp"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <title>到帐确认</title>
</head>

<script>
  var currentDate = "<%=PubFun.getCurrentDate()%>";
</script>

<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
</td>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
    <div class="maxbox" id="maxbox">
    <table  class= common align=center>
        <tr class=common>
          <TD  class= title5>
            投保人名称
          </TD>
          <TD  class= input5>
           <Input class="common wid" name=AppntName id="AppntName">
          </TD>

          <TD  class= title5>
            票据号
          </TD>
          <TD  class= input5>
           <Input class="common wid" name=ChequeNo id="ChequeNo">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            管理机构
          </TD>
          <TD  class= input5>
            <Input class=codeno  name=ManageCom id="ManageCom" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);"  onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1],null,checkComCode,'1');"><input class=codename id="ManageComName" name=ManageComName readonly=true>
          </TD>

          <TD  class= title5>
            交费收据号
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=TempFeeNo id="TempFeeNo">
          </TD>
       </TR>
        <TR  class= common>
          <TD  class= title5>
            交费方式
          </TD>
          <TD  class= input5>
            <Input class=codeno name=PayMode id="PayMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onMouseDown="return showCodeList('paymode',[this,payname],[0,1]);" onDblClick="return showCodeList('paymode',[this,payname],[0,1]);" onKeyUp="return showCodeListKey('paymode',[this,payname],[0,1]);"><input class=codename id="payname" name=payname readonly>
          </TD>
		    	<TD  class= title5>
		            登陆机构
		      </TD> 
		      <TD  class= input5>
		          <Input class="common wid" name=comcode id="comcode"  value="<%=tGI.ManageCom%>" readonly >
		       </TD> 
        </TR>
        <TR  class= common>
          <TD  class= title5>
            入机起期
          </TD>
          <TD  class= input5>
            <Input class= "coolDatePicker" dateFormat="short" name=StartDay id="StartDay" onClick="laydate({elem:'#StartDay'});"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            入机止期
          </TD>
          <TD  class= input5>
            <Input class= "coolDatePicker" dateFormat="short" name=EndDay id="EndDay" onClick="laydate({elem:'#EndDay'});"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>          
        </TR>
    </table> 
    </div>
    <Br>
    <table align=left>
      <tr>
        <td> 
          <INPUT VALUE="查  询" class=cssButton TYPE=button onClick="easyQueryClick();">
        </td>
        <td>
          <INPUT VALUE="填写当天日期" class=cssButton TYPE=button Title="填写当天日期作为选择暂交费的到帐日期" onClick="insertCurrentDate();"> 
        </td>
      </tr>
    </table>
    <br>
    <br>
    <table class= common>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 未到帐数据信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	
    	<Div  id= "divPage" align=center style= "display: none ">
      <INPUT VALUE="首页" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="尾页" class="cssButton93" TYPE=button onClick="getLastPage();"> 					
      </Div>
      				
  	</div>
  	  <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            交费金额合计
          </TD>
          <TD  class= input5>
            <Input class=common name=sumMoney id="sumMoney" readonly>
          </TD>
          <td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
        </TR>
      </table>
      <Br>
      <table align=left>
        <tr>
          <td>
     <INPUT VALUE="到帐确认" class=cssButton TYPE=button onclick = "autochk();"> 
      <INPUT VALUE="支票退票" class=cssButton TYPE=hidden onclick = "Notchk();">
          </td>
        </tr>
      </table>
      <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
<script>
	var checkComCode ="1 and comcode like #"+<%=tGI.ComCode%>+"%#";
</script>
