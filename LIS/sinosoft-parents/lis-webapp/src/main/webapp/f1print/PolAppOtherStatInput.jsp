<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：PolAppStatInput.jsp
//程序功能：承保报表统计
//创建日期：2007-04-05 15:01
//创建人  ：Fuqx
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
<SCRIPT src="PolAppOtherStatInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PolAppOtherStatInit.jsp"%>
</head>

<body onload="initForm();" >
<form action="PolAppOtherStatSave.jsp" method=post name=fm id=fm target="fraSubmit">


<table class=common>
  <tr class= common>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></td>
    <TD class= titleImg>
      月报补充统计
    </TD>
  </tr>
</table>
<Div  id= "divLLReport1" style= "display: ''" class="maxbox">
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
      <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AppReportType id=AppReportType  ondblClick="showCodeList('OthReportType',[this,ReportName],[0,1]);" onclick="showCodeList('OthReportType',[this,ReportName],[0,1]);" onkeyup="showCodeListKey('OthReportType',[this,ReportName],[0,1]);">
      </TD>
      <TD class= title5>
	机构类型
      </TD>
      <TD  class= input5>
      <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AppManageType id=AppManageType  ondblClick="showCodeListEx('AppManageType',[this,ManageName],[0,1],null,null,null,1);" onclick="showCodeListEx('AppManageType',[this,ManageName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('AppManageType',[this,ManageName],[0,1],null,null,null,1);">
      </TD>   
    </TR>
    <TR  class= common>
      <TD  class= title5>
    	销售渠道
      </TD>
      <TD  class= input5>
    	<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AppSalechnl id=AppSalechnl readonly  ondblClick="showCodeList('AppSalechnl',[this,SalechnlName],[0,1]);" onclick="showCodeList('AppSalechnl',[this,SalechnlName],[0,1]);" onkeyup="showCodeListKey('AppSalechnl',[this,SalechnlName],[0,1]);">
     	<!-- Input class=code name=AppSalechnl readonly value='3' -->
      </TD>
      <TD  style="display:none" class= title5>
    	险种编码
      </TD>
      <TD style="display:none"  class= input5>
    	<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center" class=code name=RiskCode id=RiskCode ondblClick="showCodeList('riskcode',[this],[0]);" onclick= "showCodeList('riskcode',[this],[0]);" onkeyup="showCodeListKey('riskcode',[this],[0]);">
     	<!-- Input class=code name=AppSalechnl readonly value='3' -->
      </TD>
    </TR>
  
  </table>
  </Div>
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
