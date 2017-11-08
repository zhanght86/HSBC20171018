<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
	String tContNo = "";
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
			loggerDebug("GrpApproveModify","---contno:"+tContNo);

	}
	String tGrpPolNo = "";
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("GrpApproveModify","---contno:"+tContNo);
%>
<script>
	
	var grpPolNo = "<%=tGrpPolNo%>";      //个人单的查询条件.
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="GrpApproveModify.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpApproveModifyInit.jsp"%>
  <title>集体投保单查询 </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 保单信息部分 -->
    
    <table  class= common align=center>
    </table>
          <!--INPUT VALUE="查询" TYPE=button onclick="easyQueryClick();"-->           
          <!--INPUT VALUE="问题件查询" class= common TYPE=button onclick="QuestQuery();"-->
    	  <!--INPUT VALUE="修改完成确认" class= common TYPE=button onclick="ModifyMakeSure();"-->					
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>请输入查询团体主险投保单条件：</td>
		  </tr>
	</table>
  <div class="maxbox1">
  <Div  id= "divFCDay" style= "display: ''"> 
    <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
            投保单号
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpProposalNo >
          </TD>
          <TD  class= title5>
            &nbsp
          </TD>
          <TD  class= input5>
            &nbsp
          </TD>
            <Input type = "hidden" class= common name=PrtNo >  
        </TR>    
    </table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="easyQueryClick();">查  询</a>
  <a href="javascript:void(0)" id="riskbutton" class=button onclick="ApplyUW();">申  请</a>
  <!-- <INPUT VALUE="查  询" class=cssButton  TYPE=button onclick="easyQueryClick();">  
  <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyUW();"> -->

    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGrpGrid);">
    		</td>
    		<td class= titleImg>共享工作池</td>
    	</tr>
    </table>
  	<Div  id= "divGrpGrid" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none" align=center>
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 		
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">					
      </div>
  	</div> 
    <div class="maxbox1">  	
      <table  class= common >
      	<TR  class= common>
          <TD  class= title5>
            投保单号
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpProposalNoSelf >
          </TD>
          <TD  class= title5>
            &nbsp;
          </TD>
          <TD  class= input5>
            &nbsp;
          </TD>
        </TR>
    </table> 
    </div> 
    <a href="javascript:void(0)" class=button onclick="easyQueryClickSelf();">查  询</a>
    <!-- <INPUT VALUE="查  询" class=cssButton  TYPE=button onclick="easyQueryClickSelf();">   -->
 	
    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrpGrid);">
    		</td>
    		<td class= titleImg>
    			 投保单信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divSelfGrpGrid" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrpGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div style= "display: none" align=center>
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage2.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage2.previousPage();"> 		
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage2.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage2.lastPage();">				
      </div>
  	</div>
  	  	
  	<P>
  	<a href="javascript:void(0)" class=button style="display: none" onclick="showNotePad();">记事本查看</a>
  	<!-- <INPUT class=cssButton VALUE="记事本查看" TYPE=button style="display: none" onclick="showNotePad();">    -->
  	</P>
  	
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID  value= "">     	
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
