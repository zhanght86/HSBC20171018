<%
//程序名称：AgentQueryInput.jsp
//程序功能：
//创建日期：2003-04-8
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
 	String tManageCom = "";
 	String tQueryFlag = "";    //用于区分新契约多业务员录入时调用的查询
 	//当新契约多业务员进行录入时，tQueryFlag="1"
	try
	{
		tManageCom = request.getParameter( "ManageCom" );
		loggerDebug("AgentCommonQueryInput","---tManageCom:"+tManageCom);
		tQueryFlag = request.getParameter("queryflag");
		loggerDebug("AgentCommonQueryInput","---tQueryFlag:"+tQueryFlag);
	}
	catch( Exception e1 )
	{
			loggerDebug("AgentCommonQueryInput","---Exception:"+e1);
	}
%>

<script>
	var ManageCom = "<%= tManageCom%>";
	//2010-4-30 如果传入的branchtype为50则代表综拓
	var branchtype="<%=request.getParameter("branchtype")%>";
	var queryFlag = "<%=tQueryFlag%>";
	var row = "<%= request.getParameter("row")%>";
	var col = "<%= request.getParameter("col")%>";
</script>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./AgentCommonQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./AgentCommonQueryInit.jsp"%>
  <title>业务员查询 </title>
</head>
<body  onload="initForm();" >
  <form action="./AgentCommonQuerySubmit.jsp" method=post name=fm id="fm" target="fraSubmit">
  <!--业务员查询条件 -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            </td>
            <td class= titleImg>
                业务员查询条件
            </td>            
    	</tr>
    </table>
  <Div  id= "divLAAgent" style= "display: ''">
    <div class="maxbox1">
  <table  class= common>
      <TR  class= common> 
        <TD class= title>   业务员编码  </TD>
        <TD  class= input>  <Input class="common wid"  name=AgentCode  id="AgentCode"> </TD>
        <TD class= title>   销售机构  </TD>
        <TD class= input> <Input class="common wid" name=AgentGroup id="AgentGroup" >  </TD>
        <TD class= title>  管理机构 </TD>
        <TD class= input> <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name=ManageCom class='codeno' id="ManageCom" onMouseDown="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input name=ManageComName class=codename id="codename" readonly=true>   </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title> 姓名 </TD>
        <TD  class= input>   <Input name=Name id="Name" class="common wid" >  </TD>
        <TD  class= title> 性别  </TD>
        <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" name=Sex id="Sex"  class="codeno" MAXLENGTH=1 onMouseDown="return showCodeList('Sex',[this,SexName],[0,1]);"   ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"     ><input name=SexName  class=codename id="codename" readonly=true>      	
        	</TD>
        <TD  class= title>  身份证号码  </TD>
        <TD  class= input>   <Input name=IDNo id="IDNo" class="common wid" >  </TD>
      </TR>
   
    </table>
    </div>     
    </Div>
	<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> 
          				
    <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 业务员结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none">
			<INPUT class="cssButton90" VALUE="首  页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT class="cssButton91" VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT class="cssButton92" VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 		
      <INPUT class="cssButton93" VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"> 			</div>	
  	</div>
    <div>
    </div>
    <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();">
    <br>
    <br>
    <br>
    <br>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
