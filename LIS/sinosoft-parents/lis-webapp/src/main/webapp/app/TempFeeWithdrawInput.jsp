<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 
<%
//�������ƣ�TempFeeWithdrawInput.jsp
//�����ܣ�
//�������ڣ�2002-11-18 11:10:36
//������  ���� ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	//��½��Ϣ
	GlobalInput tGI = new GlobalInput();
	if (session.getValue("GI")==null)
	{
		loggerDebug("TempFeeWithdrawInput","null");
	}
	else
	{
		tGI = (GlobalInput)session.getValue("GI");
	}
	
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" type="text/javascript" ></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <script src="../common/javascript/MultiCom.js"></script>
  <SCRIPT src="TempFeeWithdrawInput.js"></SCRIPT>
  <%@include file="TempFeeWithdrawInit.jsp"%>
  
  <title>�ݽ�����Ϣ </title>
</head>

<body  onload="initForm();" >
  <form action="./TempFeeWithdrawSave.jsp" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- �ݽ�����Ϣ���� fraSubmit-->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>�������ѯ������</td>
  		</tr>
  	</table>
    <div class="maxbox">
  	<Div  id= "divFCDay" style= "display: ''">
    <table  class= common>
    <TR  class= common>
      <TD  class= title5>�շѺ���</TD>
      <TD  class= input5><Input class= "common wid" name=TempFeeNo id="TempFeeNo"></TD>
      <TD  class= title5>ӡˢ��/��ͬ��</TD>
      <TD  class= input5><Input class= "common wid" name=PrtNo id="PrtNo"></TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>�������</TD>
      <TD  class= input5>
      	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ManageCom id="ManageCom" verify="�������|code:station" onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');" ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1');"><input class=codename name=ManageComName id="ManageComName" readonly=true>
      </TD>      	
      <TD  class= title5>��������</TD>
      <TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#PayDate'});" verify="��������|date" dateFormat="short" name=PayDate id="PayDate"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>��������</TD>
      <TD  class= input5>
        <Input class="coolDatePicker" onClick="laydate({elem: '#EnterAccDate'});" verify="��������|date" dateFormat="short" name=EnterAccDate id="EnterAccDate"><span class="icon"><a onClick="laydate({elem: '#EnterAccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
      <TD  class= title5>���ѽ��</TD>
      <TD  class= input5>
        <Input class= "common wid" name=PayMoney id="PayMoney">
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>����Ա</TD>
      <TD  class= input5>
        <Input class= "common wid" name=Operator id="Operator">
      </TD>
      <TD  class= title5>���ֱ���</TD>
      <TD  class= input5>
        <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=RiskCode id="RiskCode" onclick="return showCodeList('RiskCode',[this]);" ondblclick="return showCodeList('RiskCode',[this]);" >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>�����˱���</TD>
      <TD  class= input5>
        <Input class= "common wid" name=AgentCode id="AgentCode">
      </TD>
    	<TD  class= title5>��½����</TD> 
      <TD  class= input5>
        <Input class= "common wid" name=mComcode id="mComcode" value="<%=tGI.ManageCom%>" readonly >
       </TD> 
    </TR>
  </table>
  </Div>
  </div>
  <Input class= common type=hidden name=AgentGroup id="AgentGroup">
  <a href="javascript:void(0)" class=button onclick="tempFeeNoQuery();">��  ѯ</a>
  <a href="javascript:void(0)" class=button  name=PrintFee id = "PrintFee" onclick="PrintInform();">��ӡ�ݽ����˷�ʵ��ƾ֤</a>
  <!-- <INPUT VALUE="��  ѯ" class= cssButton TYPE=button onclick="tempFeeNoQuery();">
  <INPUT VALUE="��ӡ�ݽ����˷�ʵ��ƾ֤" class= cssButton TYPE=button onclick="PrintInform();" name=PrintFee> -->
    <!-- �ݽ�����Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJTempFee1);">
    		</td>
    		<td class= titleImg>
    			 �ݽ�����Ϣ 
    		</td>
    	</tr>
    </table>
	<Div  id= "divLJTempFee1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanFeeGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
	
  <Div id= "divPage" align=center style= "display: 'none' ">
  <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
  <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
  <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
  <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
  </Div>
  <div>
    <a href="javascript:void(0)" class=button name=TFConfirm id="TFConfirm" onclick="submitForm();">�˷�ȷ��</a>
  </div>
  <br>
  <!-- <INPUT VALUE="�˷�ȷ��" class= cssButton TYPE=button onclick="submitForm();" name=TFConfirm>    -->
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
  <form action="./TempFeeWithdrawPrintMain.jsp" method=post name=fm2 id="fm2" target="_blank">
    <Input type=hidden name=PrtData id="PrtData">
  </form>
  <form action="../f1print/TempFeeWithdrawPrintSave.jsp" method=post name=fm3 id="fm3" target="_blank">
    <Input type=hidden name=PrtData1 id="PrtData1">
  </form>
  <br>
  <br>
  <br>
  <br>
</body>
</html>
<script>
	var codeSql=" 1 and comcode like #"+<%=tGI.ComCode%>+"%#";
</script>
