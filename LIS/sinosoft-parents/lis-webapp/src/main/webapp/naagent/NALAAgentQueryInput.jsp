<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    GlobalInput tG2 = new GlobalInput();
    tG2 = (GlobalInput)session.getValue("GI");
%>
<script>
	var commonManageCom = "<%=tG2.ManageCom%>";
</script>
<%
//�������ƣ�LAAgentQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:31:08
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./NALAAgentQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./NALAAgentQueryInit.jsp"%>
  <%@include file="../common/jsp/ManageComLimit.jsp"%>

  <title>�����˲�ѯ </title>
</head>
<body  onload="initForm();" >
  <form action="./NALAAgentQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--�����˲�ѯ���� -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent);">
            <td class= titleImg>
                �����˲�ѯ����
            </td>
            </td>
    	</tr>
     </table>
  <Div  id= "divLAAgent" class=maxbox1 style= "display: ''">
  <table  class= common>
      <TR  class= common> 
        <TD class= title> 
          �����˱��� 
        </TD>
        <TD  class= input> 
          <Input class="wid common" id=AgentCode  name=AgentCode >
        </TD>
        <TD class= title>
          ���ۻ��� 
        </TD>
        <TD class= input>
          <Input class="wid common" name=AgentGroup id=AgentGroup > 
        </TD>
        <TD class= title>
          �������
        </TD>
        <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);"> 
        </TD> 
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ����
        </TD>
        <TD  class= input>
          <Input name=Name id=Name class="wid common" >
        </TD>        
        <TD  class= title>
          �Ա� 
        </TD>
        <TD  class= input>
          <Input name=Sex id=Sex class="code" MAXLENGTH=1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);" >
        </TD>
        <TD  class= title>
          �������� 
        </TD>
        <TD  class= input>
          <Input name=Birthday id=Birthday class="wid common" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ֤������ 
        </TD>
        <TD  class= input> 
          <Input name=IDNo id=IDNo class="wid common" > 
        </TD>
        <TD  class= title>
          ����
        </TD>
        <TD  class= input>
          <Input name=Nationality id=Nationality class="code" id="Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);" > 
        </TD>
        <TD  class= title> 
          ����
        </TD>
        <TD  class= input>
          <Input name=NativePlace id=NativePlace class="code" MAXLENGTH=2 id="NativePlaceBak" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlaceBak',[this]);" onkeyup="return showCodeListKey('NativePlaceBak',[this]);">
        </TD>
      </TR>      
      <TR  class= common>        
        <TD  class= title>
          �������� 
        </TD>
        <TD  class= input> 
          <Input name=ZipCode id=ZipCode class="wid common" > 
        </TD>
        <TD  class= title>
          �绰 
        </TD>
        <TD  class= input> 
          <Input name=Phone id=Phone class="wid common" > 
        </TD>
        <TD  class= title> 
          ����
        </TD>
        <TD  class= input>
          <Input name=BP id=BP class="wid common" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          �ֻ� 
        </TD>
        <TD  class= input> 
          <Input name=Mobile id=Mobile class="wid common" > 
        </TD>
        <TD  class= title>
          e_mail 
        </TD>
        <TD  class= input> 
          <Input name=EMail id=EMail class="wid common" > 
        </TD>
        <TD  class= title>
          ����ְ�� 
        </TD>
        <TD  class= input> 
          <Input name=HeadShip id=HeadShip class="wid common"  > 
        </TD>
      </TR>
      <!--TR  class= common>
        <TD  class= title>
          �Ƿ����̱�־ 
        </TD>
        <TD  class= input> 
          <Input name=SmokeFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);"> 
        </TD>
        <TD  class= title>
          ���б���
        </TD>
        <TD  class= input> 
          <Input name=BankCode class=common > 
        </TD>
        <TD  class= title>
          �����˺� 
        </TD>
        <TD  class= input> 
          <Input name=BankAccNo class= common > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ��ҵ���� 
        </TD>
        <TD  class= input> 
          <Input name=WorkAge class= common  > 
        </TD>
        <TD  class= title>
          ԭ������λ 
        </TD>
        <TD  class= input> 
          <Input name=OldCom class= common > 
        </TD>
        <TD  class= title> 
          ԭְҵ 
        </TD>
        <TD  class= input> 
          <Input name=OldOccupation class= common > 
        </TD>
      </TR-->
      <!--TR  class= common> 
        <TD  class= title>
          �Ƽ������� 
        </TD>
        <TD  class= input> 
          <Input name=RecommendAgent class= common  > 
        </TD>
        <TD  class= title> 
          ����/��ҵ
        </TD>
        <TD  class= input> 
          <Input name=Business class=common > 
        </TD>
      </TR-->
      <TR  class= common> 
        <!--TD  class= title>
          ���õȼ� 
        </TD>
        <TD  class= input> 
          <Input name=CreditGrade class=common > 
        </TD>
        <TD  class= title>
          �����ʸ� 
        </TD>
        <TD  class= input> 
          <Input name=SaleQuaf class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD-->
        <TD  class= title>
          �������ʸ�֤���� 
        </TD>
        <TD  class= input> 
          <Input name=QuafNo id=QuafNo class="wid common" > 
        </TD>
        <TD  class= title>
          չҵ֤����1 
        </TD>
        <TD  class= input> 
          <Input name=DevNo1 id=DevNo1 class="wid common" > 
        </TD>
        <TD  class= title>
          ��˾ʱ�� 
        </TD>
        <TD  class= input> 
          <Input name=EmployDate id=EmployDate class='coolDatePicker' dateFormat='short' > 
        </TD>
      </TR>
      <!--TR  class= common> 
        <TD  class= title> 
          ֤�鿪ʼ���� 
        </TD>
        <TD  class= input> 
          <Input name=QuafStartDate class="coolDatePicker" dateFormat="short" > 
        </TD>
        <TD  class= title>
          ֤��������� 
        </TD>
        <TD  class= input> 
          <Input name=QuafEndDate class="coolDatePicker" dateFormat="short" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title> 
          չҵ֤����2
        </TD>
        <TD  class= input> 
          <Input name=DevNo2 class= common > 
        </TD>
        <TD  class= title>
          Ƹ�ú�ͬ���� 
        </TD>
        <TD  class= input> 
          <Input name=RetainContNo class= common > 
        </TD>
        <TD  class= title>
          ��������� 
        </TD>
        <TD  class= input> 
          <Input name=AgentKind class='code' ondblclick="return showCodeList('AgentKind',[this]);" onkeyup="return showCodeListKey('AgentKind',[this]);"  > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ҵ����չ����
        </TD>
        <TD  class= input> 
          <Input name=DevGrade class=common > 
        </TD>
        <TD  class= title>
          ���ڱ�־ 
        </TD>
        <TD  class= input> 
          <Input name=InsideFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
        <TD  class= title>
          �Ƿ�רְ��־ 
        </TD>
        <TD  class= input> 
          <Input name=FullTimeFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          �Ƿ��д�ҵ֤��־ 
        </TD>
        <TD  class= input> 
          <Input name=NoWorkFlag class='code' MAXLENGTH=1 ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
        <TD  class= title>
          ��ѵ���� 
        </TD>
        <TD  class= input> 
          <Input name=TrainDate class='coolDatePicker' dateFormat='short' > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ת������ 
        </TD>
        <TD  class= input> 
          <Input name=InDueFormDate class='coolDatePicker' dateFormat='short' > 
        </TD>
        <TD  class= title>
          ������״̬ 
        </TD>
        <TD  class= input> 
          <Input name=AgentState class='code'  maxlength=2 ondblclick="return showCodeList('AgentState',[this]);" onkeyup="return showCodeListKey('AgentState',[this]);" > 
        </TD>
        <TD  class= title>
          ��˾���� 
        </TD>
        <TD  class= input> 
          <Input name=OutWorkDate class='coolDatePicker' dateFormat='short' > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ��֤�� 
        </TD>
        <TD  class= input> 
          <Input name=AssuMoney class= common > 
        </TD>
        <TD  class= title>
          ����Ա
        </TD>
        <TD  class= input> 
          <Input name=Approver class= common > 
        </TD>
        <TD  class= title>
          ��������
        </TD>
        <TD  class= input> 
          <Input name=ApproveDate class= common > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ��־λ
        </TD>
        <TD  class= input> 
          <Input name=QualiPassFlag class= common MAXLENGTH=1 > 
        </TD>
        <TD  class= title>
          ��ע
        </TD>
        <TD class=input> 
          <Input name=Remark class= common > 
        </TD>
        <TD  class= title>
          ����Ա���� 
        </TD>
        <TD  class= input> 
          <Input name=Operator class= common > 
        </TD>
      </TR-->
    </table>
          <input type=hidden name=BranchType value=''>
          <INPUT VALUE="��ѯ" class=cssButton TYPE=button onclick="easyQueryClick();"> 
          <INPUT VALUE="����" class=cssButton TYPE=button onclick="returnParent();"> 	
    </Div>      
          				
    <table>
    	<tr>
        	<td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 �����˽��
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAgentGrid" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��ҳ" TYPE=button class=cssButton90 onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton91 onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton92 onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" TYPE=button class=cssButton93 onclick="turnPage.lastPage();"> 						
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	  <br /> <br /> <br /> <br /> <br />
  </form>
</body>
</html>
