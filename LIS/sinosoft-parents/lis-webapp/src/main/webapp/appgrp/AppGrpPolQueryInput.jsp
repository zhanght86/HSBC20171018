<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>

<%
	String tContNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	try
	{
		tContNo = request.getParameter( "ContNo" );
		
		//默认情况下为集体投保单
		if( tContNo == null || tContNo.equals( "" ))
			tContNo = "";
	}
	
	catch( Exception e1 )
	{
		tContNo = "";
			loggerDebug("AppGrpPolQueryInput","---contno:"+tContNo);

	}
	loggerDebug("AppGrpPolQueryInput","---contno:"+tContNo);
%>
<script>
	var contNo = "<%=tContNo%>";  //个人单的查询条件.
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/MultiCom.js"></script>
    
   <SCRIPT src="AppGrpPolQuery.js"></SCRIPT>
  <%@include file="AppGrpPolQueryInit.jsp"%>
  <title>集体投保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入查询条件：</td>
		  </tr>
	</table>
  <div class="maxbox1">
  <Div  id= "divFCDay" style= "display: ''"> 
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title> 投保单号</TD>
          <TD  class= input>
            <Input class= "common wid" name=GrpProposalNo id="GrpProposalNo">
          </TD>
            <Input type="hidden" class= "common wid" name=PrtNo id="PrtNo">
          <TD  class= title>销售渠道</TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=SaleChnl id="SaleChnl" onclick="return showCodeList('AgentType',[this]);" ondblclick="return showCodeList('AgentType',[this]);" onkeyup="return showCodeListKey('AgentType',[this]);">
          </TD>
          <TD class=title>管理机构</TD>
				  <TD class=input>
					  <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="管理机构|code:comcode&notnull" onclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" ondblclick="return showCodeList('comcode',[this,ManageComName],[0,1],null,'#1#','1');" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1],null,'#1#','1');"><input class=codename name=ManageComName id="ManageComName" readonly=true elementtype=nacessary>
				  </TD>
        </TR>
        <tr class=common>
          <td class=title>险种编码</td>
          <td class=input>
            <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=RiskCode id="RiskCode" onclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" ondblclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true elementtype=nacessary>                                                
          </td>
          <TD  class= title>客户经理编码</TD>
          <TD  class= input>
            <Input class= "common wid" name=CManagerCode id="CManagerCode">
          </TD>
        </tr>      
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
  <!-- <INPUT VALUE="查  询" Class=cssButton TYPE=button onclick="easyQueryClick();">  -->
          		
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none" align=center>
      <INPUT VALUE="首  页" Class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="上一页" Class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="下一页" Class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="尾  页" Class=cssButton93 TYPE=button onclick="getLastPage();"> 	
      </div>				
  	</div>
  	<P>
      <a href="javascript:void(0)" class=button onclick="returnParent();">投保单明细</a>
      <a href="javascript:void(0)" class=button onclick="ScanQuery();">团体扫描件查询</a>
  	  <!-- <INPUT VALUE="投保单明细" Class=cssButton TYPE=button onclick="returnParent();"> 
      <INPUT VALUE="团体扫描件查询"    Class=cssButton TYPE=button onclick="ScanQuery();"> --> 
          <%
          String today=PubFun.getCurrentDate();
          
          %>
          <input type=hidden id="sysdate" name="sysdate" value="<%=today%>">			
  	</P>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
