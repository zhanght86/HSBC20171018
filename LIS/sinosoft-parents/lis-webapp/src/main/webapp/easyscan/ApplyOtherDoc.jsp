<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�ApplyIssueDoc.jsp
//�����ܣ�
//�������ڣ�2006-04-07
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
		String mOperator = tG1.Operator;
%>
<script>
	var moperator = "<%=mOperator%>"; //��¼�������
</script>
<head >
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./ApplyOtherDoc.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ApplyOtherDocInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./ApplyOtherDocSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
        </td>
				<td class= titleImg>ɨ�赥֤��Ϣ</td>
      </tr>
    </table>
    <div class="maxbox1">		 
    <Div  id= "divCode1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>ӡˢ��</TD>
          <TD  class= input5><Input class= "common wid" name=BussNo id="BussNo" ></TD>
          <TD  class= title5>�������</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=ManageCom id="ManageCom" value="<%=Branch%>"
              onclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
          		ondblclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
            	onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>¼��״̬</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=InputState id="InputState" CodeData="0|^0|¼��δ���^1|¼�����" 
          		ondblClick="showCodeListEx('InputState',[this],[0,1]);"
              onclick="showCodeListEx('InputState',[this],[0,1]);"
          		onkeyup="showCodeListKeyEx('InputState',[this],[0,1]);" verify="¼��״̬|code:InputState"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>��ʼ���ڣ�ɨ�裩</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ���ڣ�ɨ�裩|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>����ʱ�䣨ɨ�裩</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="����ʱ�䣨ɨ�裩|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TR>
      </table>
    </Div>
  </div>
    <div>
    </div>
   <INPUT VALUE="��  ѯ" TYPE=button class=cssButton onclick="easyQueryClick()"> 
    <br>
  	<Div  id= "divCodeGrid" style= "display: ''" align=center>
    	<table  class= common>
     		<tr  class= common>
  	  		<td text-align: left colSpan=1><span id="spanCodeGrid" ></span></td>
				</tr>
  		</table>
    	<INPUT VALUE="��  ҳ" TYPE=button class="cssButton90" onClick="getFirstPage();"> 
    	<INPUT VALUE="��һҳ" TYPE=button class="cssButton91" onClick="getPreviousPage();">
    	<INPUT VALUE="��һҳ" TYPE=button class="cssButton92" onClick="getNextPage();"> 
    	<INPUT VALUE="β  ҳ" TYPE=button class="cssButton93" onClick="getLastPage();">
  	</div>
  	<br>
  	<Div  id= "divReason" style= "display: none">
	    <Table class= common>
				<TR class= common>
					<TD class= title5>�������ͣ�</TD>
                    <td class="input5"><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class='code' name=Reason id="Reason" readonly
					CodeData="0|^UR200|������Ͷ����ʾ^UR202|Ͷ��ҪԼ����������(��ӡ)^UR203|Ͷ��ҪԼ����������(����ӡ)^UR204|��Ч�ջ���^UR205|�������^UR206|δ��������ʱ��ս��ر�Լ��(��ӡ)^UR207|�ʾ�^UR208|���֤��^UR209|��������^UR210|��������^UR211|�ֺ��Ʒ˵����^UR212|�����֪�ʾ�"
          onclick="showCodeListEx('Reason',[this,Content],[0,1]);"
          		ondblClick="showCodeListEx('Reason',[this,Content],[0,1]);"
          		onkeyup="showCodeListKeyEx('Reason',[this,Content],[0,1]);" verify="��������|NOTNULL"></TD>
                <TD class= title5></TD>
                <td class="input5"></td>
                </TR>
                <tr>
					<TD  class= input><textarea name="Content" cols="170%" rows="4" witdh=25% class="common" verify="����ԭ��|NOTNULL"></textarea></TD></TR>
	 		</table>
	    <input type=hidden id="fmtransact" name="fmtransact">
	    <INPUT VALUE="��  ��" TYPE=button class="cssButton" name="APPLY" id="APPLY" onclick="saveApply()">
<!--
	    <INPUT VALUE="���볷��" TYPE=button class=common onclick="undoApply()">
-->
  	</div>
    <br>
    <br>
    <br>
    <br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
