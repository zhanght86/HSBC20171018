<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");  
%>
<script>
  var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head >
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="HospitalQualityQuery2.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="HospitalQualityQueryInit2.jsp"%>
</head>

<body onload="initForm();" >
<form action="HospitalQualityQuerySave2.jsp" method=post name=fm id=fm target="fraSubmit">


<table class=common>
  <tr class= common>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></td>
    <TD class= titleImg>
      �б�ͳ�Ʊ���
    </TD>
  </tr>
</table>
<Div  id= "divLLReport1" style= "display: ''" class="maxbox1">
<table  class=common>
   <TR  class= common>
      <TD  class= title5>
    	��ʼ����
      </TD>
      <TD  class= input5>
    	<Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>     
      <TD  class= title5>
    	��������
      </TD>
      <TD  class= input5>
    	<Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>     
    </TR>
    <TR  class= common>
      <TD  class= title5>
    	ͳ������
      </TD>
      <TD  class= input5>
    	<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=AppManageType id=AppManageType readonly  CodeData ="0|^2|��������ͳ��^3|��������ͳ��^S|����^N|����" 
    	ondblClick="showCodeListEx('AppManageType',[this,ManageName],[0,1]);" onclick="showCodeListEx('AppManageType',[this,ManageName],[0,1]);" onkeyup="showCodeListKeyEx('AppManageType',[this,ManageName],[0,1]);">    	
      </TD>
    </TR>
  </table></Div>

  <p>
       <input class=cssButton type=button value="��   ӡ" onclick="PrintData();">
  
<input type="hidden" name=HQReportType >
<input type="hidden" name=ManageName >
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
