<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：PolAppOtherInput.jsp
//程序功能：承保报表统计
//创建日期：2009-04-13 15:01
//创建人  ：ln
%>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");  
%>
<script>
  var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head >
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="PolAppTimeStatInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PolAppTimeStatInit.jsp"%>
</head>

<body onload="initForm();" >
<form action="PolAppTimeStatSave.jsp" method=post name=fm target="fraSubmit">


<table class=common>
  <tr class= common>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></td>
    <TD class= titleImg>
      时效统计
    </TD>
  </tr>
</table>
<Div  id= "divLLReport1" style= "display: ''"  class="maxbox1">
<table  class=common>
   <TR  class= common>
      <TD  class= title5>
    	开始日期
      </TD>
      <TD  class= input5>
    	<Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
      <TD  class= title5>
            开始时间
      </TD>          
      <TD class= input5>    
        <Input class="coolDatePicker" onClick="laydate({elem: '#StartTime'});" value="00:00:00" verify="扫描日期|len=8" dateFormat="short" name=StartTime id="StartTime"><span class="icon"><a onClick="laydate({elem: '#StartTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>(例:22:00:00)
      </TD>
    </TR>
    <TR class= common>
      <TD  class= title5>
    	结束日期
      </TD>
      <TD  class= input5>
    	<Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
      <TD  class= title5>
            结束时间
      </TD>          
      <TD class= input5>    
         <Input class="coolDatePicker" onClick="laydate({elem: '#EndTime'});" value="00:00:00" verify="扫描日期|len=8" dateFormat="short" name=EndTime id="EndTime"><span class="icon"><a onClick="laydate({elem: '#EndTime'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>(例:22:00:00)
      </TD>
    </TR>
    <TR class= common>  
    	<TD class= title5>
	报表类型
      </TD>
      <TD  class= input5>
      <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AppReportType id=AppReportType  ondblClick="showCodeList('TimReportType',[this,ReportName],[0,1]);" onclick="showCodeList('TimReportType',[this,ReportName],[0,1]);" onkeyup="showCodeListKey('TimReportType',[this,ReportName],[0,1]);">
      <TD  class= title5>
    	
      </TD>
      <TD  class= input5>
    	
      </TD>   
    </TR>
  </table></div>
 
  <p>
       <!--<input class=cssButton type=button value="打   印" onclick="PrintData();">-->
       <a href="javascript:void(0);" class="button" onClick="PrintData();">打    印</a>
  
<input type="hidden" name=ReportName >
<input type="hidden" name=ManageName >
<input type="hidden" name=SalechnlName value='银代渠道'>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
