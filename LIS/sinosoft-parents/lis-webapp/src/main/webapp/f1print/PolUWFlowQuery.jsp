<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�PolAppStatInput.jsp
//�����ܣ��б�����ͳ��
//�������ڣ�2007-04-05 15:01
//������  ��Fuqx
%>
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
<SCRIPT src="PolUWFlowQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="PolUWFlowQueryInit.jsp"%>
</head>

<body onload="initForm();" >
<form action="PolUWFlowQuerySave.jsp" method=post name=fm target="fraSubmit">


<table class=common>
  <tr class= common>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></td>
    <TD class= titleImg>
      �˱�����ͳ�Ʊ���
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
    <TR class= common>  
    	<TD class= title5>
	��������
      </TD>
      <TD  class=input5>
      <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=UWReportType id=UWReportType readonly  ondblClick="showCodeList('UWReportType',[this,ReportName],[0,1]);" onclick="showCodeList('UWReportType',[this,ReportName],[0,1]);" onkeyup="showCodeListKey('UWReportType',[this,ReportName],[0,1]);">
      </TD>
      <TD class= title5>
	��������
      </TD>
      <TD  class=input5>
      <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=UWManageType id=UWManageType readonly  CodeData ="" ondblClick="showCodeListEx('UWManageType',[this,ManageName],[0,1],null,null,null,1);" onclick="showCodeListEx('UWManageType',[this,ManageName],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('UWManageType',[this,ManageName],[0,1],null,null,null,1);">
      </TD>   
    </TR>
    <TR  class= common>
      <TD  class= title5>
    	��������
      </TD>
      <TD  class= input5>
    	<!--Input class=code name=AppSalechnl readonly  ondblClick="showCodeList('AppSalechnl',[this,SalechnlName],[0,1]);" onkeyup="showCodeListKey('AppSalechnl',[this,SalechnlName],[0,1]);"-->
     	<!--Input class=code name=UWSalechnl readonly value=''-->
     	<Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=UWSalechnl id=UWSalechnl readonly  CodeData ="" ondblClick="return showCodeList('AppSalechnl',[this,SalechnlName],[0,1],null,null,null,1);" onclick="return showCodeList('AppSalechnl',[this,SalechnlName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('AppSalechnl',[this,SalechnlName],[0,1],null,null,null,1);">

      </TD>
      <TD  class= title5></TD>
      <TD  class= input5></TD>
    </TR>

  </table></Div>
  <!--<input class=cssButton type=button value="��   ӡ" onclick="PrintData();">-->
  <a href="javascript:void(0);" class="button" onClick="PrintData();">��    ӡ</a>
<input type="hidden" name=ReportName >
<input type="hidden" name=ManageName >
<input type="hidden" name=SalechnlName value='��������'>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
