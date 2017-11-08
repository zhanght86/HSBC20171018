<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>

<%
//程序名称：RulesVersionTraceQuery.jsp
//程序功能：账务规则版本维护轨迹查询页面
//创建日期：2008-08-21
//创建人  ：FanXin
//更新记录：  更新人    更新日期     更新原因/内容
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
  var VersionNo = <%=request.getParameter("VersionNo")%>;
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./RulesVersionTraceQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./RulesVersionTraceQueryInit.jsp"%>

<title>版本信息查询</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm id=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion1);">
    </IMG>
    <td class=titleImg>
      查询条件
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divFIRulesVersion1" style= "display: ''" class="maxbox1">
  <table  class= common>
		<tr class= common>
         <TD class= title5>
					  版本编号
				 </TD>
				 <TD class=input5>
				 	 <input class="wid" class=readonly name=VersionNo id=VersionNo readonly=true >
				 </TD>
         <TD  class= title5>
           维护编号
         </TD>
         <TD class=input5>
				 	 <Input class="wid" class=common name=Maintenanceno id=Maintenanceno >
				 </TD>          
		</tr>
		<tr class= common>
         <TD class= title5>
					  维护状态
				 </TD>
				 <TD class=input5>
				 	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name= MaintenanceState id= MaintenanceState verify="维护状态|notnull" CodeData="0|^01|完成^02|维护^03|撤销"  ondblclick="return showCodeListEx('MaintenanceState',[this,MaintenanceStateName],[0,1]);"  onclick="return showCodeListEx('MaintenanceState',[this,MaintenanceStateName],[0,1]);" onkeyup="return showCodeListKeyEx('MaintenanceState',[this,MaintenanceStateName],[0,1]);"  ><input class=codename name=MaintenanceStateName id=MaintenanceStateName readonly=true></TD>
				 </TD>
         <TD  class= title5>
           维护描述
         </TD>
         <TD class=input5>
				 	 <Input class="wid" class=common name=MaintenanceReMark id=MaintenanceReMark >
				 </TD>          
		</tr>

      </table></div>	
          <!--<INPUT VALUE="查  询" TYPE=button onclick="easyQueryClick();" class="cssButton">-->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
     
          
          
   <table>    	
    <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIRulesVersion2);">
    		</td>
    		<td class= titleImg>
    			 版本维护轨迹查询结果
    		</td>
    	</tr>
   </table>

    
  	<Div  id= "divFIRulesVersion2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanRulesVersionTraceGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
     <!-- <INPUT VALUE="首  页" TYPE=button onclick="turnPage.firstPage();" class="cssButton">
      <INPUT VALUE="上一页" TYPE=button onclick="turnPage.previousPage();" class="cssButton">
      <INPUT VALUE="下一页" TYPE=button onclick="turnPage.nextPage();" class="cssButton">
      <INPUT VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();" class="cssButton">-->
  	</div>  
<!--<INPUT VALUE="返  回" TYPE=button onclick="returnParent();" class="cssButton">-->
<a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

