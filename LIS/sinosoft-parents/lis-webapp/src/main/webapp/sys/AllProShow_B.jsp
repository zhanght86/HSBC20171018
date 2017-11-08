<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
	String tPNo = "";
	try
	{
		//tPNo = request.getParameter("PNo");
	}
	catch( Exception e )
	{
		tPNo = "";
	}

//得到界面的调用位置,默认为1,表示个人保单直接录入.
// 1 -- 个人投保单直接录入
// 2 -- 集体下个人投保单录入
// 3 -- 个人投保单明细查询
// 4 -- 集体下个人投保单明细查询

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//默认情况下为个人保单直接录入
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "6";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "6";
	}
   loggerDebug("AllProShow_B","sxy-LoadFlag:" + tLoadFlag);
%>
<head >
<script>
	var loadFlag = "<%=tLoadFlag%>";  //判断从何处进入保单录入界面,该变量需要在界面出始化前设置.

</script>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="AllProShow_B.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllProShowInit_B.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
</head>

<body  onload="initForm(); " >
  <form  name=fm id=fm >

    <Div  id= "divRiskCode0" style="display:none" >
 
    <!-- 连带被保人信息部分（列表） -->

	<Div  id= "divLCInsured0" style= "display: 'none'">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
    		</td>
    		<td class= titleImg>
    			 连带被保人信息
    		</td>
    	</tr>
      </table>
	  <Div  id= "divLCInsured2" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSubInsuredGrid" >
					</span> 
				</td>
			</tr>
		</table>
	  </div>
	</div>
	
	<Div  id= "divLCSubInsured0" style= "display: ''">
    <!-- 受益人信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
    		</td>
    		<td class= titleImg>
    			 受益人信息
    		</td>
    	</tr>
    </table>    
	<Div  id= "divLCBnf1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanBnfGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    </div>
    <!-- 告知信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 告知信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCImpart1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- 特约信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
    		</td>
    		<td class= titleImg>
    			 特约信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCSpec1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSpecGrid">
					</span> 
				</td>
			</tr>
		</table>
	</div>

    <!--可以选择的责任部分，该部分始终隐藏-->
	<Div  id= "divDutyGrid1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<!--确定是否需要责任信息-->
	</div>
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
  </Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


