<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="FFChargeDay.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="FFChargeDayInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr> 
    		<td class=common>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
    		</td>
    		 <td class= titleImg>
        		����ӿ��ս�
       		 </td>   		 
    	</tr>
    </table>
    <div class="maxbox1">
     <Div  id= "divFCDay" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            ��ʼʱ��
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="��ʼʱ��|NOTNULL" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          	<!--Input class= "coolDatePicker" dateFormat="short" name=StartDate verify="��ʼʱ��|NOTNULL"-->
          </TD>
          <TD  class= title5>
            ����ʱ��
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="����ʱ��|NOTNULL" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
        </TR>
        </table>
     </Div>
    </div>   
      <Div  id= "divFinance" style= "display: ''">
        <a href="javascript:void(0)" class=button name="FRiskPrint" id="FRiskPrint" onclick="FRPrint();">�����ִ�ӡ</a>
        <a href="javascript:void(0)" class=button name="FModePrint" id="FModePrint" onclick="FMPrint();">����ʽ��ӡ</a>
			  <!-- <input class= cssButton type=Button name="FRiskPrint" id="FRiskPrint" value="�����ִ�ӡ" onclick="FRPrint()">
        <input class= cssButton type=Button name="FModePrint" id="FModePrint" value="����ʽ��ӡ" onclick="FMPrint()"> -->
      </Div>
        <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
