<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��HST
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ProposalDisplay.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalDisplayInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./ProposalDetail.jsp" method=post name=fm target="fraSubmit">
      <INPUT VALUE="������Ϣ" TYPE=button onclick="showDuty();"> 
      <INPUT VALUE="�ݽ�����Ϣ" TYPE=button onclick="showFee();"> 					
      <INPUT VALUE="����" TYPE=button onclick="parent.close();"> 
    <!-- ������Ϣ���� -->
    <table>
      <tr>
      <td>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
      </td>
      <td class= titleImg>
        ������Ϣ
      </td>
    	</tr>
    </table>
    <Div  id= "divLCPol1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            Ͷ��������
          </TD>
          <TD  class= input>
            <Input class= common name=ProposalNo >
          </TD>
          <TD  class= title>
            ӡˢ����
          </TD>
          <TD  class= input>
            <Input class= common name=PrtNo >
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl ondblclick="return showCodeList('SaleChnl',[this]);">
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCom ondblclick="return showCodeList('AgentCom',[this]);">
          </TD>
          <TD  class= title>
            ������
          </TD>
          <TD  class= input>
            <Input class= common name=Handler >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode ondblclick="return showCodeList('AgentCode',[this]);">
          </TD>
          <TD  class= title>
            ���������
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentGroup ondblclick="return showCodeList('AgentGroup',[this]);">
          </TD>
          <TD  class= title>
            ���ϴ����˱���
          </TD>
          <TD  class= input>
            <Input class= common name=AgentCode1 >
          </TD>
        </TR>
      </table>
    </Div>
    <!-- Ͷ������Ϣ���� -->
    <table>
    	<tr>
        	<td>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntInd1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" style= "display: ''">
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            Ͷ���˿ͻ���
          </TD>
          <TD  class= input>
            <Input class= common name=AppntCustomerNo >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=AppntName >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <input class="code" name=AppntSex ondblclick="return showCodeList('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <input class="coolDatePicker" dateFormat="short" name="AppntBirthday" >
            <!--<Input class= common name=AppntBirthday >-->
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntIDType ondblclick="return showCodeList('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= common name=AppntIDNo >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �뱻���˹�ϵ
          </TD>
          <TD  class= input>
            <Input class="code" name=RelationToInsured ondblclick="return showCodeList('Relation',[this]);">
          </TD>
          <TD  class= title>
            �绰
          </TD>
          <TD  class= input>
            <Input class= common name=AppntPhone >
          </TD>
          <TD  class= title>
            �ֻ�
          </TD>
          <TD  class= input>
            <Input class= common name=AppntMobile >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ͨѶ��ַ
          </TD>
          <TD  class= input>
            <Input class= common name=AppntPostalAddress >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name=AppntZipCode >
          </TD>
          <TD  class= title>
            e_mail
          </TD>
          <TD  class= input>
            <Input class= common name=AppntEMail >
          </TD>
        </TR>
      </table>
    </Div>
    <!-- ��������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured1);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCInsured1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            �����˿ͻ���
          </TD>
          <TD  class= input>
            <Input class= common name=CustomerNo >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=Name >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex ondblclick="return showCodeList('Sex',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="Birthday" >
            <!--<Input class= common name=Birthday >-->
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name=IDType ondblclick="return showCodeList('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= common name=IDNo >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name=Health ondblclick="return showCodeList('Health',[this]);">
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="code" name=OccupationType ondblclick="return showCodeList('OccupationType',[this]);">
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name=Marriage ondblclick="return showCodeList('Marriage',[this]);">
          </TD>
        </TR>
      </table>
    </Div>
    <!-- ������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCKind1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCKind1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);">
          </TD>
          <TD  class= title>
            ���ְ汾
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion ondblclick="return showCodeList('RiskVersion',[this]);">
          </TD>
          <TD  class= title>
            ������Ч����
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="CValiDate" >
            <!--<Input class= common name=CValiDate >-->
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            �ս��ڼ�
          </TD>
          <TD  class= input>
            <Input class= common name=PayEndYear >
          </TD>
          <TD  class= title>
            �ս��ڼ䵥λ
          </TD>
          <TD  class= input>
            <Input class= common name=PayEndYearFlag >
          </TD>
          <TD  class= title>
            �ս�����
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="PayEndDate" >
            <!--<Input class= common name=PayEndDate >-->
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �����ڼ�
          </TD>
          <TD  class= input>
            <Input class= common name=GetYear >
          </TD>
          <TD  class= title>
            �����ڼ䵥λ
          </TD>
          <TD  class= input>
            <Input class= common name=GetYearFlag >
          </TD>
          <TD  class= title>
            �������ڼ�������
          </TD>
          <TD  class= input>
            <Input class= common name=GetStartType >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �����ڼ�
          </TD>
          <TD  class= input>
            <Input class= common name=InsuYear >
          </TD>
          <TD  class= title>
            �����ڼ䵥λ
          </TD>
          <TD  class= input>
            <Input class= common name=InsuYearFlag >
          </TD>
          <TD  class= title>
            ������ֹ����
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="EndDate" >
            <!--<Input class= common name=EndDate >-->
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=Mult >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=StandPrem >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=Amnt >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �Զ��潻��־
          </TD>
          <TD  class= input>
            <Input class="code" name=AutoPayFlag ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
          <TD  class= title>
            �������䷽ʽ
          </TD>
          <TD  class= input>
            <Input class="code" name=BonusMode ondblclick="return showCodeList('BonusMode',[this]);">
          </TD>
          <TD  class= title>
            �������ʽ
          </TD>
          <TD  class= input>
            <Input class="code" name=InterestDifFlag ondblclick="return showCodeList('InterestDifFlag',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ������־
          </TD>
          <TD  class= input>
            <Input class="code" name=SubFlag ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
          <TD  class= title>
            �ɷ�λ��
          </TD>
          <TD  class= input>
            <Input class="code" name=PayLocation ondblclick="return showCodeList('PayLocation',[this]);">
          </TD>
          <TD  class= title>
            ����־
          </TD>
          <TD  class= input>
            <Input class="code" name=HealthCheckFlag ondblclick="return showCodeList('YesNo',[this]);">
          </TD>
        </TR>
      </table>
    </Div>
    <!-- ������������Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
    		</td>
    		<td class= titleImg>
    			 ������������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCInsured2" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSubInsuredGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��������Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCBnf1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanBnfGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��֪��Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 ��֪��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCImpart1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��Լ��Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
    		</td>
    		<td class= titleImg>
    			 ��Լ��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCSpec1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanSpecGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
