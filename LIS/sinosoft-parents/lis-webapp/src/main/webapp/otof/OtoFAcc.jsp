<%
//�������ƣ�OtoFAcc.jsp
//�����ܣ��������˻���Ϣ
//�������ڣ�2008-01-02 
//������  ��zy
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
    loggerDebug("OtoFAcc","Branch:"+Branch);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="OtoFAcc.js"></SCRIPT>
  <%@include file="OtoFAccInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./OtoFAccSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table class=common >
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
        <td class= titleImg>�������ѯ������</td></tr>
		</table>
        <Div  id= "divPayPlan1" style= "display: ''" class="maxbox1">
    <table  class= common>
      	<TR  class= common>
          <TD  class= title5>��ʼ����</TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#bDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=bDate id="bDate"><span class="icon"><a onClick="laydate({elem: '#bDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>��ֹ����</TD>
          <TD  class= input5>
          <Input class="coolDatePicker" onClick="laydate({elem: '#eDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=eDate id="eDate"><span class="icon"><a onClick="laydate({elem: '#eDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
       <TR  class= common>
	        <TD  class= title5>���ֱ���</TD>
	        <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=RiskCode id=RiskCode ondblclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onclick="return showCodeList('RiskCode',[this,RiskCodeName],[0,1]);" onkeyup="return showCodeListKey('RiskCode',[this,RiskCodeName],[0,1]);"></TD>
          <TD  class= title5>��������</TD>
          <TD  class= input5><input class="wid" class=common name=RiskCodeName id=RiskCodeName readonly=true></TD>
       </TR>  
       <TR  class= common>
          <TD  class= title5>�������</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code"  name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this]);" onclick="return showCodeList('station',[this]);"onkeyup="return showCodeListKey('station',[this]);"></TD>
       </TR>        
       <!--TR class= common>
        <TD  class= title>ƾ֤���</TD>
        <TD  class= input><Input class= common name=VouchNo value="14" readonly></TD>
        <TD  class= title>��������</TD>
        <TD  class= input><Input class="coolDatePicker" dateFormat="short" name=AccountDate></TD>
	    </TR-->     
    </table>
		</Div>

    	<!--TD class= common>  <INPUT VALUE="ƾ֤��ȡ"  name="distill" class= common TYPE=button onclick="SubmitForm();"></TD-->
    	    	    	    	
     <!--<INPUT VALUE="��    ӡ" class= cssButton TYPE=button onclick="easyprint();">-->
<a href="javascript:void(0);" class="button" onClick="easyprint();">��    ӡ</a>
  	</div>
   <INPUT type= "hidden" name= "fmAction" value= ""> 
   <INPUT type= "hidden" name= "ComCode" value= ""> 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
