<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 

  <SCRIPT src="./GrpInterest.js"></SCRIPT>
  <%@include file="./GrpInterestInit.jsp"%>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�ŵ���Ϣ </title> 
</head>
<body  onload="initForm();" >
  <form name=fm method=post target="fraSubmit" action="./GrpInterestSave.jsp">
		<!-- Ͷ������Ϣ���� -->
		<table class= common border=0 width=100%>
			<tr>
            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,sanya);"></td>
				<td class= titleImg>�������ѯ������</td>
			</tr>
		</table>
        <Div  id= "sanya" style= "display: ''" class="maxbox1">
    <table  class= common>
			<TR  class= common>
				<TD  class= title5>������</TD>
				<TD  class= input5><Input class="wid" class= common name=FiscalYear id=FiscalYear verify="������|NOTNULL"> </TD>
				<TD  class= title5>�ŵ���</TD>
				<TD  class= input5><Input class="wid" class= common name=GrpContNo id=GrpContNo></TD></TR>
                <TR  class= common>
				<TD  class= title5>���ִ���</TD>
				<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name=RiskCode id=RiskCode CodeData="0|^212401|MS�����������^212402|MS�������������" ondblClick="showCodeListEx('RiskCode',[this],[0,1]);" onclick="showCodeListEx('RiskCode',[this],[0,1]);" onkeyup="showCodeListKeyEx('RiskCode',[this],[0,1]);"></TD> 
                <TD  class= title5>��Ч����</TD>
        <TD  class= input5>
        
        <Input class="coolDatePicker" onClick="laydate({elem: '#BDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=BDate id="BDate"><span class="icon"><a onClick="laydate({elem: '#BDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD> 
			</TR> 
			<TR  class= common>
         
	  		<TD  class= title5>��Чֹ��</TD>
      	<TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
			</TR> 
		    
    </table></div>
    
<!--		<INPUT VALUE="��    ѯ" class= cssButton TYPE=button onclick="easyQueryClick();"> 
		<INPUT VALUE="�鿴��Ϣ������־" class= cssButton TYPE=button onclick="viewErrLog();" name=ErrorLog> --><br>
        <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
        <a href="javascript:void(0);" class="button" onclick="viewErrLog();" name=ErrorLog>�鿴��Ϣ������־</a><br>
    <br>
    <table>
			<tr>
				<td class=common><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);"></td>
				<td class= titleImg>�ŵ���Ϣ</td>
			</tr>
    </table>
  	<Div  id= "divPol" style= "display: ''" align=center>
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
							<span id="spanPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
			<INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onclick="getPreviousPage();">
			<INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onclick="getNextPage();"> 
			<INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<p>
    <table class="common">
    	<tr class="common">
    		<td class="input">
			    <!--<INPUT VALUE="��Ϣȷ��" class= cssButton TYPE=button name="calculate" onclick="appConfirm();">-->
                <a href="javascript:void(0);" class="button" name="calculate" onclick="appConfirm();">��Ϣȷ��</a>
                 �������ѯ��ǰ��Ƚ�Ϣ������ٽ��б���Ȳ�������</td>
    	</tr>
    </table>
 		</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html> 
