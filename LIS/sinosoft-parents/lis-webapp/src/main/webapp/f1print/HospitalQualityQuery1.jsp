<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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
<SCRIPT src="HospitalQualityQuery1.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="HospitalQualityQueryInit1.jsp"%>
</head>

<body onload="initForm();" >
<form action="HospitalQualityQuerySave1.jsp" method=post name=fm id=fm target="fraSubmit">


<table class=common>
  <tr class= common>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,mave);"></td>
    <TD class= titleImg align= center >
      承保统计报表
    </TD>
  </tr>
</table>
<Div  id= "mave" style= "display: ''" class="maxbox1">
<table  class=common>
   <TR  class= common>
      <TD  class= title5>
    	开始日期
      </TD>
      <TD  class= input5>
    	
        <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="有效开始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>     
      <TD  class= title5>
    	结束日期
      </TD>
      <TD  class= input5>
    	
        <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>     
    </TR>
    <TR  class= common>
      <TD  class= title5>
    	销售渠道
      </TD>
      <TD  class= input5>
    	<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AppSalechnl id=AppSalechnl readonly  ondblClick="showCodeList('AppSalechnl',[this,SalechnlName],[0,1]);" onclick="showCodeList('AppSalechnl',[this,SalechnlName],[0,1]);" onkeyup="showCodeListKey('AppSalechnl',[this,SalechnlName],[0,1]);">
      </TD>
    </TR>
  </table></Div>

  <p>
       <!--<input class=cssButton type=button value="打   印" onclick="PrintData();">-->
       <a href="javascript:void(0);" class="button" onClick="PrintData();">打    印</a>
  
<input type="hidden" name=HQReportType >

<input type="hidden" name=SalechnlName value='银代渠道'>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
