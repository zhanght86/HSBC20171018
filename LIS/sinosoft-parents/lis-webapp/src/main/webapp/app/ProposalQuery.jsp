<%
//�������ƣ�ProposalQuery.jsp
//�����ܣ����˲�ͨ���޸�
//�������ڣ�2002-11-23 17:06:57
//������  ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="ProposalQuery.js"></SCRIPT>
  <%@include file="ProposalQueryInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <title>������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraTitle">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
			  <td class= titleImg align= center>�������ѯ��������ע������Ͷ����������������վݺ�/����Э����ſɶ�����ѯ�����޷��ṩ������������������������</td>
		  </tr>
	  </table>
    <div class="maxbox">
    <Div  id= "divFCDay" style= "display:  "> 
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5> ӡˢ��  </TD>
          <TD  class= input5> <Input class= "common wid" name=ContNo id="ContNo"> </TD>
          <TD  class= title5> ������� </TD>
          <TD  class= input5> <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" verify="�������|code:station" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" id="ManageComName"> </TD> 
        </TR>
        <TR  class= common>         
          <TD  class= title5> Ͷ������ </TD>
          <TD  class= input5> 
            <Input class="coolDatePicker" onClick="laydate({elem: '#PolApplyDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=PolApplyDate id="PolApplyDate"><span class="icon"><a onClick="laydate({elem: '#PolApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>          
          <TD  class= title5>  Ͷ���˿ͻ���  </TD>
          <TD  class= input5> <Input class= "common wid"  name="AppntNo" id="AppntNo"> </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>  Ͷ��������  </TD>
          <TD  class= input5> <Input class= "common wid"  name="AppntName" id="AppntName"> </TD>
          <TD  class= title5>  Ͷ����֤������  </TD>
          <TD  class= input5> <Input class= "common wid"  name="AppntIDNo" id="AppntIDNo"> </TD>       
        </TR>
        <tr class="common">
          <TD  class= title5>  �����˿ͻ���  </TD>
          <TD  class= input5> <Input class= "common wid"  name="InsuredNo" id="InsuredNo"> </TD>
          <TD  class= title5>  ����������</TD>
          <TD  class= input5> <Input class= "common wid"  name=InsuredName id="InsuredName"> <input type="hidden" name="InsuredSex" id="InsuredSex"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>  ������֤������  </TD>
          <TD  class= input5> <Input class= "common wid"  name="InsuredIDNo" id="InsuredIDNo"> </TD>
          <TD  class= title5>  �˱�����</TD>
          <td class=input5>
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno CodeData="0|^1|�ܱ�^2|����^4|�α�׼��^9|��׼��^a|����^z|�˱�����" name=uwState id="uwState" onclick="return showCodeListEx('acontuwstate',[this,uwStatename],[0,1]);" ondblclick="return showCodeListEx('acontuwstate',[this,uwStatename],[0,1]);" onkeyup="return showCodeListKeyEx('acontuwstate',[this,uwStatename],[0,1]);"><Input class=codename name=uwStatename id="uwStatename" readonly ></td>
        </tr>
        <TR  class= common>
          <TD  class= title5> ҵ��Ա����  </TD>
          <TD  class= input5>  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=AgentCode id="AgentCode" onclick="return showCodeList('AgentCode',[this, 'AgentGroup'], [0, 2]);" ondblclick="return showCodeList('AgentCode',[this,'AgentGroup'], [0, 2]);" onkeyup="return showCodeListKey('AgentCode', [this, 'AgentGroup'], [0, 2]);">  </TD>
          <Input class= "common wid"  type= "hidden" name=AgentGroup id="AgentGroup">
          <Input type= "hidden" class= "common wid"  name=PrtNo id="PrtNo">
        </TR>

        <TR  class= common style= "display: none">
          <TD  class= title5> �˱��ȼ� </TD>
          <TD  class= input5> <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=uwgrade id="uwgrade" onclick="return showCodeList('lduwuser',[this],[0]);" ondblclick="return showCodeList('lduwuser',[this],[0]);" onkeyup="return showCodeListKey('lduwuser',[this],[0]);"> </TD>          
        </Tr>
      <TR  class= common >
          <TD  class= title5>�����վݺ�/����Э�����</TD>
          <TD  class= input5> <Input class= "common wid"  name=tempfeeno id="tempfeeno"> 
        </TR>      
    </table>
  </Div>
  </div>
  <div>
    <INPUT class=cssButton VALUE="     ��ѯͶ����     " TYPE=button onclick="easyQueryClick();">
  </div>
  <br>
      
  <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display:  ">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<center>
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 					
  	</div>
    <br>
    <div>
        <INPUT VALUE="Ͷ�����ѳб�������ѯ" class=cssButton TYPE=button name="Button1" onclick="queryProposal();">
        <INPUT VALUE="Ͷ����δ�б�������ѯ" class=cssButton TYPE=button name="Button2" onclick="queryNotProposal();">
		<INPUT VALUE=" Ͷ���˼�����ȫ��ѯ " class=cssButton TYPE=button name="Button3" onclick="queryEdor()">
		<INPUT VALUE=" Ͷ���˼��������ѯ " class=cssButton TYPE=button name="Button4" onclick="queryClaim();">
	</div>
	<br>
	<div>
		<INPUT class=cssButton VALUE="     Ͷ������ϸ     " TYPE=button name="Button5" onclick="queryDetailClick();"> 	
		<INPUT class=cssButton VALUE="   Ͷ����Ӱ���ѯ   " TYPE=button name="Button6" onclick="ImageQuery();">
		<INPUT class=cssButton VALUE="     �����˲�ѯ     " TYPE=button name="Button7" onclick="InsuredQuery();">
		<INPUT class=cssButton VALUE="     �������ѯ     " TYPE=button name="Button8" onclick="PremQuery();">
	</div>
	<br>
	<div>
		<INPUT class=cssButton VALUE="    ����������ѯ    " TYPE=button name="Button9" onclick="OperationQuery();">
		<INPUT class=cssButton VALUE="     �ݽ��Ѳ�ѯ     " TYPE=button name="Button10" onclick="showTempFee();">        
		<INPUT class=cssButton VALUE="      ״̬��ѯ      " TYPE=button name="Button11" onclick="statequery();">
	</div>
	<br>
		<input type=hidden id="GrpPolNo" name="GrpPolNo">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
