<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");
	}
	catch( Exception e )
	{
		tPNo = "";
	}

//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
// 1 -- ����Ͷ����ֱ��¼��
// 2 -- �����¸���Ͷ����¼��
// 3 -- ����Ͷ������ϸ��ѯ
// 4 -- �����¸���Ͷ������ϸ��ѯ

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//Ĭ�������Ϊ���˱���ֱ��¼��
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{ 
		tLoadFlag = "1";
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
loggerDebug("ProposalOnlyInput","LoadFlag:" + tLoadFlag);
%>
<head >
<script>
	var loadFlag = "<%=tLoadFlag%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����.
	var type = "<%=request.getParameter("type")%>";
	var Operator = "<%=tGI.Operator%>";   //��¼����Ա
	var ManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	
	<%@include file="ProposalInit.jsp"%>
	<SCRIPT src="ProposalInput.js"></SCRIPT>
	
</head>

<body  onload="initForm(); " >
  <form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">
    <Div  id= "divButton" style= "display: 'none'">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    </DIV>
    <Div  id= "divRiskCode0">
    <table class=common>
       <tr class=common>
          <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode ondblclick="if(loadFlag=='1') showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);">
          </TD>
       </tr>
    </table>
    </Div>
    <!-- ������Ϣ -->
    <Div  id= "divALL0" style= "display: 'none'">
    <Div  id= "divLCPol0" style= "display: 'none'">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ���ְ汾
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion ondblclick="return showCodeList('RiskVersion',[this]);" onkeyup="return showCodeListKey('RiskVersion',[this]);">
          </TD>
          <TD  class= title>
            ��ͬ��
          </TD>
          <TD  class= input>
            <Input class= common name=ContNo >
          </TD>
          <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input>
            <Input class= common name=GrpPolNo >
          </TD>
          <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input>
            <Input class= common name=MainPolNo >
          </TD>
        <TR  class= common>
          <TD  class= title>
            ���ڽ�������
          </TD>
          <TD  class= input>
            <Input class= common name=FirstPayDate >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=Lang >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name=Currency >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��ͬ���鴦��ʽ
          </TD>
          <TD  class= input>
            <Input class= common name=DisputedFlag >
          </TD>
          <TD  class= title>
            ���д��ձ��
          </TD>
          <TD  class= input>
            <Input class= common name=AgentPayFlag >
          </TD>
          <TD  class= title>
            ���д������
          </TD>
          <TD  class= input>
            <Input class= common name=AgentGetFlag >
          </TD>
          <TD  class= title>
             ��ע
          </TD>
          <TD  class= input>
            <Input class= common name=Remark >
          </TD>
        </TR>
      </table>
    </Div>
    <!-- ������Ϣ���� -->
    <table>
      <tr>
        <td>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
        </td>
        <td class= titleImg>
          ������Ϣ
          <INPUT VALUE="��ѯ������Ϣ" TYPE=button onclick="showDuty();"> 
          <INPUT VALUE="�����ݽ�����Ϣ" TYPE=button onclick="showFee();">
          <INPUT id="butChooseDuty" VALUE="ѡ������" TYPE=button onclick="chooseDuty();">
          <INPUT id="butBack" VALUE="����" TYPE=button >
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
            <Input class="readonly" readonly name=ProposalNo >
          </TD>
          <TD  class= title>
            ӡˢ����
          </TD>
          <TD  class= input>
            <Input class= common name=PrtNo verify="ӡˢ����|len<=20" >
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom verify="�������|code:station" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl verify="��������|code:SaleChnl" ondblclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);">
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCom verify="�������|code:AgentCom" ondblclick="return showCodeList('AgentCom',[this]);" onkeyup="return showCodeListKey('AgentCom',[this]);">
          </TD>
          <TD  class= title>
            ������
          </TD>
          <TD  class= input>
            <Input class= common name=Handler verify="������|len<=10" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode verify="�����˱���|notnull&code:AgentCode" ondblclick="return showCodeList('AgentCode',[this, AgentGroup], [1, 0]);" onkeyup="return showCodeListKey('AgentCode', [this, AgentGroup], [1, 0]);">
          </TD>
          <TD  class= title>
            ���������
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=AgentGroup verify="���������|notnull&len<=12" >
          </TD>
          <TD  class= title>
            ���ϴ����˱���
          </TD>
          <TD  class= input>
            <Input class= common name=AgentCode1 verify="���ϴ����˱���|len<=10" >
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
    			 ��������Ϣ���ͻ��ţ�<Input class= common name=CustomerNo > <INPUT id="butBack" VALUE="��ѯ" TYPE=button onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCInsured1" style= "display: ''">
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=Name verify="����������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex verify="�������Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="Birthday" verify="�����˳�������|notnull&date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="readonly" readonly name="Age" >
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="IDType" verify="������֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= common name="IDNo" verify="������֤������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="NativePlace" verify="�����˹���|code:NativePlace" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class= common name="RgtAddress" verify="�����˻������ڵ�|len<=80" >
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name="Marriage" verify="�����˻���״��|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="Nationality" verify="����������|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code" name="Degree" verify="������ѧ��|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="code" name="SmokeFlag" verify="�������Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="PostalAddress" verify="��������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name="ZipCode" verify="��������������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ͥ�绰
          </TD>
          <TD  class= input>
          <input class= common name="Phone" verify="�����˼�ͥ�绰|len<=18" >
          </TD>
          <TD  class= title>
            �ƶ��绰
          </TD>
          <TD  class= input>
            <Input class= common name="Mobile" verify="�������ƶ��绰|len<=15" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name="EMail" verify="�����˵�������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpName" verify="�����˹�����λ|len<=60" >
          </TD>
          <TD  class= title>
            ��λ�绰
          </TD>
          <TD  class= input>
            <Input class= common name="GrpPhone" verify="�����˵�λ�绰|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpAddress" verify="�����˵�λ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class= common name="GrpZipCode" verify="�����˵�λ��������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="WorkType" verify="������ְҵ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="PluralityType" verify="�����˼�ְ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="code" name="OccupationType" verify="������ְҵ���|notnull&code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class="code" name="OccupationCode" verify="������ְҵ����|code:OccupationCode" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>

        </TR>
      </table>
    </Div>    
    <!-- ������Ϣ -->
    <Div  id= "divLCPol01" style= "display: 'none'">
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name=Health ondblclick="return showCodeList('Health',[this]);" onkeyup="return showCodeListKey('Health',[this]);">
          </TD>
    </Div> 

    <!-- ����Ͷ������Ϣ���� -->
    <table>
    	<tr>
        	<td>
    	  		<!--<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntInd1);">-->
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ���ͻ��ţ�<Input class= common  name=AppntCustomerNo > 
	    		 <INPUT id="butBack" VALUE="��ѯ" TYPE=button onclick="queryAppntNo();"> 
	    		 �״�Ͷ���ͻ�������д�ͻ��š���
    			 <Div  id= "divSamePerson" style= "display: ''">
	    			 <font color=red>
	    			 	��Ͷ����Ϊ�������˱��ˣ������������ѡ��
	    			 	<INPUT TYPE="checkbox" NAME="SamePersonFlag" onclick="isSamePerson();">
	    			 </font>
    			 </div>
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" style= "display: ''">
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class= common name=AppntName verify="Ͷ��������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex verify="Ͷ�����Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" name="AppntBirthday" verify="Ͷ���˳�������|notnull&date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="readonly" readonly name="AppntAge" >
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntIDType" verify="Ͷ����֤������|code:IDType" ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= common name="AppntIDNo" verify="Ͷ����֤������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="AppntNativePlace" verify="Ͷ���˹���|code:NativePlace" ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class= common name="AppntRgtAddress" verify="Ͷ���˻������ڵ�|len<=80" >
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntMarriage" verify="Ͷ���˻���״��|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="AppntNationality" verify="Ͷ��������|code:Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntDegree" verify="Ͷ����ѧ��|code:Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            �뱻�����˹�ϵ
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntRelationToInsured" verify="Ͷ�����뱻�����˹�ϵ|code:Relation" ondblclick="return showCodeList('Relation',[this]);" onkeyup="return showCodeListKey('Relation',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntPostalAddress" verify="Ͷ������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name="AppntZipCode" verify="Ͷ������������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ͥ�绰
          </TD>
          <TD  class= input>
          <input class= common name="AppntPhone" verify="Ͷ���˼�ͥ�绰|len<=18" >
          </TD>
          <TD  class= title>
            �ƶ��绰
          </TD>
          <TD  class= input>
            <Input class= common name="AppntMobile" verify="Ͷ�����ƶ��绰|len<=15" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name="AppntEMail" verify="Ͷ���˵�������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntGrpName" verify="Ͷ���˹�����λ|len<=60" >
          </TD>
          <TD  class= title>
            ��λ�绰
          </TD>
          <TD  class= input>
            <Input class= common name="AppntGrpPhone" verify="Ͷ���˵�λ�绰|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntGrpAddress" verify="Ͷ���˵�λ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class= common name="AppntGrpZipCode" verify="Ͷ���˵�λ��������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="AppntWorkType" verify="Ͷ����ְҵ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="AppntPluralityType" verify="Ͷ���˼�ְ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntOccupationType" verify="Ͷ����ְҵ���|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntOccupationCode" verify="Ͷ����ְҵ����|code:OccupationCode" ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>
          <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntSmokeFlag" verify="Ͷ�����Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>

        </TR>
      </table>   
    </Div>

    <!-- ����Ͷ������Ϣ���� �޸������ֶ�����-->
    <Div  id= "divLCAppntGrp0" style= "display: 'none'">
    <table>
    	<tr>
        	<td>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntGrp1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntGrp1" style= "display: ''">
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            Ͷ���˿ͻ���
          </TD>
          <TD  class= input>
            <Input class="code" readonly name=ColGrpNo ondblclick="showAppnt();" value="00010220020990000017">
          </TD>
          <TD  class= title>
            ��λ����
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=AppGrpName >
          </TD>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=AppGrpAddress >
          </TD>          
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=AppGrpZipCode >
          </TD>       
          <TD  class= title>
            ��λ����
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=GrpNature >
          </TD>
          <TD  class= title>
            ��ҵ���
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=BusinessType >
          </TD>
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            ��λ������
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=Peoples >
          </TD>       
          <TD  class= title>
            ע���ʱ���
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=RgtMoney >
          </TD>
          <TD  class= title>
            �ʲ��ܶ�
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=Asset >
          </TD>
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            ���ʲ�������
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=NetProfitRate >
          </TD>       
          <TD  class= title>
            ��Ӫҵ��
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=MainBussiness >
          </TD>
          <TD  class= title>
            ��λ���˴���
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=Corporation >
          </TD>
        </TR>        
        <TR  class= common>
          <TD  class= title>
            �����ֲ�����
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=ComAera >
          </TD>
        </TR>        
        <TR  class= common>                 
          <TD  class= title>
            ������ϵ��һ
          </TD>
        </TR>        
        <TR  class= common>                 
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=LinkMan1 >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=Department1 >
          </TD>
          <TD  class= title>
            ְ��
          </TD>
          <TD  class= input>
            <Input name=HeadShip1 class="readonly" readonly>
          </TD>
        </TR>        
        <TR  class= common>                 
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=Phone1 >
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input name=E_Mail1 class="readonly" readonly>
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input  name=Fax1 class="readonly" readonly>
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            ������ϵ�˶�
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=LinkMan2 >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input name=Department2 class="readonly" readonly>
          </TD>
          <TD  class= title>
            ְ��
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=HeadShip2 >
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input name=Phone2 class="readonly" readonly>
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input name=E_Mail2 class="readonly" readonly>
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input name=Fax2 class="readonly" readonly>
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            ���ʽ
          </TD>
          <TD  class= input>
            <Input name=GetFlag class="readonly" readonly>
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input name=GrpBankCode class="readonly" readonly>
          </TD>
          <TD  class= title>
            �ʺ�
          </TD>
          <TD  class= input>
            <Input class="readonly" readonly name=GrpBankAccNo >
          </TD>       
        </TR>        
      </table>
    </Div>
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
              ������Ч����
            </TD>
            <TD  class= input>
	          <input class="coolDatePicker" dateFormat="short" name="CValiDate" verify="������Ч����|notnull&date" >
            </TD>         
            <TD  class= title>
              �Ƿ�ָ����Ч��
            </TD>
            <TD  class= input>
              <Input class="code" name=SpecifyValiDate verify="�Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            <TD  class= title>
              �����ڼ�
            </TD>
            <TD  class= input>
              <Input class= common name=InsuYear verify="�����ڼ�|value<=65535&value>=0&num" >
            </TD>
          </TR>
          
          <TR  class= common>            
            <TD  class= title>
              ��������
            </TD>
            <TD  class= input>
              <Input class= common name=PayEndYear verify="��������|value<=65535&value>=0&num" >
            </TD>
            <TD  class= title>
              ���ѷ�ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=PayIntv verify="���ѷ�ʽ|code:PayIntv" ondblclick="return showCodeList('PayIntv',[this]);" onkeyup="return showCodeListKey('PayIntv',[this]);">
            </TD>          
            <TD  class= title>
              �罻����ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=OutPayFlag verify="�罻����ʽ|code:OutPayFlag" ondblclick="return showCodeList('OutPayFlag',[this]);" onkeyup="return showCodeListKey('OutPayFlag',[this]);">
            </TD>                      
          </TR>
          
          <TR  class= common>
            <TD  class= title>
              �շѷ�ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=PayLocation verify="�շѷ�ʽ|code:PayLocation" ondblclick="return showCodeList('PayLocation',[this]);" onkeyup="return showCodeListKey('PayLocation',[this]);">
            </TD>
            <TD  class= title>
              ������
            </TD>
            <TD  class= input>
              <Input class="code" name=BankCode verify="������|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);">
            </TD>
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class= common name=countName verify="����|len<=20" >
            </TD>
          </TR>

          <TR  class= common>
            <TD  class= title>
              �����ʺ�
            </TD>
            <TD  class= input>
              <Input class= common name=BankAccNo verify="�����ʺ�|len<=40" >
            </TD>
            <TD  class= title>
              ���汣�ս���ȡ��ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=LiveGetMode verify="���汣�ս���ȡ��ʽ|code:LiveGetMode" ondblclick="return showCodeList('LiveGetMode',[this]);" onkeyup="return showCodeListKey('LiveGetMode',[this]);">
            </TD>
          </TR>
          
          <TR  class= common>
            <TD  class= title>
              ��ȡ����
            </TD>
            <TD  class= input>
              <Input class= common name=GetYear verify="��ȡ����|len<=10" >
            </TD>
            <TD  class= title>
              ��ȡ��ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=GetIntv verify="��ȡ��ʽ|code:getIntv" ondblclick="return showCodeList('getIntv',[this]);" onkeyup="return showCodeListKey('getIntv',[this]);">
            </TD>
            <TD  class= title>
              ������ȡ��ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=BonusGetMode verify="������ȡ��ʽ|code:BonusMode" ondblclick="return showCodeList('BonusMode',[this]);" onkeyup="return showCodeListKey('BonusMode',[this]);">
            </TD>       
          </TR>
          
          <TR  class= common>                    
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class= common name=Mult verify="����|notnull&len<=13&num" >
            </TD>
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class="readonly" readonly name=Prem >
            </TD>
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class= common name=Amnt verify="����|notnull&len<=10&num" >
            </TD> 
          </TR>

          <TR  class= common>                         
            <TD  class= title>
              �Ƿ�����
            </TD>
            <TD  class= input>
              <Input class="code" name=HealthCheckFlag verify="�Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            
          </TR>
        </table>
      </Div>
      <!-- ���� -->
    <Div  id= "divLCKind0" style= "display: 'none'">
      <table  class= common>
	      <TR  class= common>
            
            <TD  class= title>
              �ս��ڼ䵥λ
            </TD>
            <TD  class= input>
              <Input class="code" name=PayEndYearFlag ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);">
            </TD>

          </TR>
          <TR  class= common>
            <TD  class= title>
              �����ڼ䵥λ
            </TD>
            <TD  class= input>
              <Input class="code" name=GetYearFlag ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);">
            </TD>
            <TD  class= title>
              �������ڼ�������
            </TD>
            <TD  class= input>
              <Input class="code" name=GetStartType ondblclick="return showCodeList('CalRefType',[this]);" onkeyup="return showCodeListKey('CalRefType',[this]);">
            </TD>
          </TR>
          <TR  class= common>
            <TD  class= title>
              �����ڼ䵥λ
            </TD>
            <TD  class= input>
              <Input class="code" name=InsuYearFlag ondblclick="return showCodeList('PeriodUnit1',[this]);" onkeyup="return showCodeListKey('PeriodUnit1',[this]);">
            </TD>
            <TD  class= title>
              �Զ��潻��־
            </TD>
            <TD  class= input>
              <Input class="code" name=AutoPayFlag ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
          </TR>
          <TR  class= common>
            <TD  class= title>
              �������ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=InterestDifFlag ondblclick="return showCodeList('InterestDifFlag',[this]);" onkeyup="return showCodeListKey('InterestDifFlag',[this]);">
            </TD>
            <TD  class= title>
              ������־
            </TD>
            <TD  class= input>
              <Input class="code" name=SubFlag ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
          </TR>
      </table>
    </Div>

    <!-- ������������Ϣ���֣��б� -->
	<Div  id= "divLCInsured0" style= "display: 'none'">
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
					<span id="spanSpecGrid">
					</span> 
				</td>
			</tr>
		</table>
	</div>

    <!--����ѡ������β��֣��ò���ʼ������-->
	<Div  id= "divDutyGrid" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<!--ȷ���Ƿ���Ҫ������Ϣ-->
	</div>
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
  </Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>


