<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>

<%
	String tContNo = "";
	try
	{
		tContNo = request.getParameter( "ContNo" );
		
		//默认情况下为集体保单
		if( tContNo == null || tContNo.equals( "" ))
			tContNo = "00000000000000000000";
	}
	
	
	catch( Exception e1 )
	{
		tContNo = "00000000000000000000";
			loggerDebug("GrpPolQueryInput","---contno:"+tContNo);

	}
	loggerDebug("GrpPolQueryInput","---contno:"+tContNo);
        String tDisplay = "";
	try
	{
		tDisplay = request.getParameter("display");
		if(tDisplay == null || tDisplay.equals( "" ))
		 { tDisplay = "0";}
	}
	catch( Exception e )
	{
		tDisplay = "";
	}
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
   loggerDebug("GrpPolQueryInput","管理机构-----"+tG.ComCode);
%>   
<script>
	var contNo = "<%=tContNo%>";  //个人单的查询条件.
	var comCode = "<%=tG.ComCode%>";
	var tDisplay = "<%=tDisplay%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="GrpPolQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="GrpPolQueryInit.jsp"%>
  <title>团体
  保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm  id= fm target="fraSubmit">
    <!-- 保单信息部分 -->
    <table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入查询条件：</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title> 团体保单号码 </TD>
          <TD  class= input> <Input class="common wid" name=GrpContNo id=GrpContNo >  </TD>
          <TD  class= title>  投保单号码 </TD>
          <TD  class= input>  <Input class="common wid" name=PrtNo id=> ></TD>
         
          <TD  class= title> 管理机构 </TD>
          <TD  class= input> <Input class="code" name=ManageCom id=ManageCom
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('station',[this]);"
           onDblClick="return showCodeList('station',[this]);"
            onKeyUp="return showCodeListKey('station',[this]);"> </TD>
         </TR>
        <TR  class= common>
          <TD  class= title> 代理人编码 </TD>
        <TD  class= input> <input NAME="AgentCode" id=AgentCode VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onBlur="return queryAgent();" onDblClick="return queryAgent();" ></TD> 
          <!--<TD  class= input> <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);" onkeyup="return showCodeListKey('AgentCode',[this]);"> </TD>          -->
         
          <TD  class= title> 代理人组别 </TD>
          <TD  class= input> <Input class="code" name=AgentGroup  id=AgentGroup
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('AgentGroup',[this]);"  
          onDblClick="return showCodeList('AgentGroup',[this]);" 
          onKeyUp="return showCodeListKey('AgentGroup',[this]);"> </TD>
          <TD  class= title> 单位客户号码 </TD>
          <TD  class= input> <Input class="common wid" name=CustomerNo id=CustomerNo > </TD>
        </TR>
        <TR  class= common>
          <TD  class= title> 单位名称 </TD>
          <TD  class= input> <Input class="common wid" name=GrpName id=GrpName > </TD>
          <TD  class= title> 单位地址 </TD>
          <TD  class= input> <Input class="common wid" name=GrpAddress id=GrpAddress> </TD>
         
          <TD  class= title> 联系人 </TD>
          <TD  class= input> <Input class="common wid"name=LinkMan1 id=LinkMan1> </TD>
        </TR>
        <TR  class= common>
		  <td class=title>险种编码</td>
						<td class=input>
							<input class=codeno name=RiskCode  id=RiskCode
                            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                            onclick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');"
                            onDblClick="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,true,'400');"
                             onKeyUp="getcodedata2();return showCodeListEx('RiskGrpWithOutEC',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class=codename name=RiskCodeName readonly=true elementtype=nacessary>							
                </td>
		</TR>
        
    </table>
    </div>
    </div>
     <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
         <!-- <INPUT VALUE="查  询" class = cssButton TYPE=button onClick="easyQueryClick();"> -->
         
          <INPUT VALUE="返  回" name=Return class = cssButton TYPE=button STYLE="display:none" onClick="returnParentBQ();">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="首  页" class = cssButton90 TYPE=button onClick="turnPage1.firstPage();HighlightByRow()"> 
      <INPUT VALUE="上一页" class = cssButton91 TYPE=button onClick="turnPage1.previousPage();HighlightByRow()"> 					
      <INPUT VALUE="下一页" class = cssButton92 TYPE=button onClick="turnPage1.nextPage();HighlightByRow()"> 
      <INPUT VALUE="尾  页" class = cssButton93 TYPE=button onClick="turnPage1.lastPage();HighlightByRow()"> 					
  	</div>
  	<P>
    <a href="javascript:void(0);" class="button"onClick="returnParent();">集体保单明细</a>

  	 <!--<INPUT VALUE="集体保单明细" class = cssButton TYPE=button onClick="returnParent();"> 	-->				
  	</P>
  	<%
          String today=PubFun.getCurrentDate();
          
          %>
			<input type=hidden id="sysdate" name="sysdate" value="<%=today%>">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
