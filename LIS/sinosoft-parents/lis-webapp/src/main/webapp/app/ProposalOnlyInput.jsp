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
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	
	<%@include file="ProposalInit.jsp"%>
	<SCRIPT src="ProposalInput.js"></SCRIPT>
	
</head>

<body  onload="initForm(); " >
  <form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">
    <Div  id= "divButton" style= "display: none">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
    </DIV>
    <Div  id= "divRiskCode0" class=maxbox1>
    <table class=common>
       <tr class=common>
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="if(loadFlag=='1') showCodeList('RiskInd',[this]);" onkeyup="return showCodeListKey('RiskInd',[this]);">
          </TD>
		  <TD  class= title5></TD>
          <TD  class= input5></td>
       </tr>
    </table>
    </Div>
    <!-- ������Ϣ -->
    <Div  id= "divALL0"   style= "display: none">
    <Div  id= "divLCPol0" class=maxbox style= "display: none">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ���ְ汾
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion id=RiskVersion  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('RiskVersion',[this]);" onkeyup="return showCodeListKey('RiskVersion',[this]);">
          </TD>
          <TD  class= title>
            ��ͬ��
          </TD>
          <TD  class= input>
            <Input class="wid common" name=ContNo id=ContNo >
          </TD>
          <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=GrpPolNo id=GrpPolNo >
          </TD>
		</tr>
		<TR  class= common>
          <TD  class= title>
            ����Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=MainPolNo id=MainPolNo >
          </TD>
        
          <TD  class= title>
            ���ڽ�������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=FirstPayDate id=FirstPayDate >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Lang id=Lang >
          </TD>
		</tr>
		<tr class=common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Currency id=Currency >
          </TD>
          <TD  class= title>
            ��ͬ���鴦��ʽ
          </TD>
          <TD  class= input>
            <Input class="wid common" name=DisputedFlag id=DisputedFlag >
          </TD>
          <TD  class= title>
            ���д��ձ��
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AgentPayFlag id=AgentPayFlag >
          </TD>
		</TR>
        <TR  class= common>
          <TD  class= title>
            ���д������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AgentGetFlag id=AgentGetFlag >
          </TD>
          <TD  class= title>
             ��ע
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Remark id=Remark >
          </TD>
		  <td class=title></td>
		  <td class=input></td>
        </TR>
      </table>
    </Div>
    <!-- ������Ϣ���� -->
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
        </td>
        <td class= titleImg>
          ������Ϣ
          <INPUT VALUE="��ѯ������Ϣ" class=cssButton TYPE=button onclick="showDuty();"> 
          <INPUT VALUE="�����ݽ�����Ϣ" class=cssButton TYPE=button onclick="showFee();">
          <INPUT id="butChooseDuty" VALUE="ѡ������" TYPE=button class=cssButton onclick="chooseDuty();">
          <INPUT id="butBack" VALUE="����" TYPE=button class=cssButton >
        </td>
      </tr>
    </table>
    <Div  id= "divLCPol1" class=maxbox style= "display: ">
      <table  class= common>
        <TR  class="wid common">
          <TD  class= title>
            Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=ProposalNo id=ProposalNo >
          </TD>
          <TD  class= title>
            ӡˢ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=PrtNo id=PrtNo verify="ӡˢ����|len<=20" >
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom verify="�������|code:station" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="code" name=SaleChnl id=SaleChnl verify="��������|code:SaleChnl" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('SaleChnl',[this]);" onkeyup="return showCodeListKey('SaleChnl',[this]);">
          </TD>
          <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCom id=AgentCom verify="�������|code:AgentCom" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('AgentCom',[this]);" onkeyup="return showCodeListKey('AgentCom',[this]);">
          </TD>
          <TD  class= title>
            ������
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Handler id=Handler verify="������|len<=10" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
            <Input class="code" name=AgentCode id=AgentCode verify="�����˱���|notnull&code:AgentCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('AgentCode',[this, AgentGroup], [1, 0]);" onkeyup="return showCodeListKey('AgentCode', [this, AgentGroup], [1, 0]);">
          </TD>
          <TD  class= title>
            ���������
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AgentGroup id=AgentGroup verify="���������|notnull&len<=12" >
          </TD>
          <TD  class= title>
            ���ϴ����˱���
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AgentCode1 id=AgentCode1 verify="���ϴ����˱���|len<=10" >
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
    			 ��������Ϣ���ͻ��ţ�<Input class= common name=CustomerNo > <INPUT id="butBack" VALUE="��ѯ" class=cssButton TYPE=button onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCInsured1" class=maxbox style= "display: ">
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=Name id=Name verify="����������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=Sex id=Sex verify="�������Ա�|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker wid" dateFormat="short" name="Birthday" id=Birthday verify="�����˳�������|notnull&date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="wid common" readonly name="Age" id=Age >
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="IDType" id=IDType verify="������֤������|code:IDType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="IDNo" id=IDNo verify="������֤������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="NativePlace" id=NativePlace verify="�����˹���|code:NativePlace" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class="wid common" name="RgtAddress" id=RgtAddress verify="�����˻������ڵ�|len<=80" >
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name="Marriage" id=Marriage verify="�����˻���״��|code:Marriage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="Nationality" id=Nationality verify="����������|code:Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code" name="Degree" id=Degree verify="������ѧ��|code:Degree" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="code" name="SmokeFlag" id=SmokeFlag verify="�������Ƿ�����|code:YesNo" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="PostalAddress" id=PostalAddress verify="��������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="ZipCode" id=ZipCode  verify="��������������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ͥ�绰
          </TD>
          <TD  class= input>
          <input class="wid common" name="Phone" id=Phone verify="�����˼�ͥ�绰|len<=18" >
          </TD>
          <TD  class= title>
            �ƶ��绰
          </TD>
          <TD  class= input>
            <Input class="wid common" name="Mobile" id=Mobile verify="�������ƶ��绰|len<=15" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="EMail" id=EMail verify="�����˵�������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpName" id=GrpName verify="�����˹�����λ|len<=60" >
          </TD>
          <TD  class= title>
            ��λ�绰
          </TD>
          <TD  class= input>
            <Input class="wid common" name="GrpPhone" id=GrpPhone verify="�����˵�λ�绰|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="GrpAddress" id=GrpAddress verify="�����˵�λ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="GrpZipCode" id=GrpZipCode verify="�����˵�λ��������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class="wid common" name="WorkType" id=WorkType verify="������ְҵ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input>
            <Input class="wid common" name="PluralityType" id=PluralityType verify="�����˼�ְ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="code" name="OccupationType" id=OccupationType verify="������ְҵ���|notnull&code:OccupationType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class="code" name="OccupationCode" id=OccupationCode verify="������ְҵ����|code:OccupationCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>

        </TR>
      </table>
    </Div>    
    <!-- ������Ϣ -->
    <Div  id= "divLCPol01" style= "display: none">
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name=Health id=Health style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Health',[this]);" onkeyup="return showCodeListKey('Health',[this]);">
          </TD>
    </Div> 

    <!-- ����Ͷ������Ϣ���� -->
    <table>
    	<tr>
        	<td>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ���ͻ��ţ�<Input class= common  name=AppntCustomerNo > 
	    		 <INPUT id="butBack" VALUE="��ѯ" TYPE=button class=cssButton onclick="queryAppntNo();"> 
	    		 �״�Ͷ���ͻ�������д�ͻ��š���
    			 <Div  id= "divSamePerson" style= "display: ">
	    			 <font color=red>
	    			 	��Ͷ����Ϊ�������˱��ˣ������������ѡ��
	    			 	<INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
	    			 </font>
    			 </div>
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntInd1" class=maxbox style= "display: ">
      <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="wid common" name=AppntName id=AppntName verify="Ͷ��������|notnull&len<=20" >
          </TD>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="code" name=AppntSex id=AppntSex verify="Ͷ�����Ա�|notnull&code:Sex" ondblclick="return showCodeList('Sex',[this]);" onkeyup="return showCodeListKey('Sex',[this]);">
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker wid" dateFormat="short" name="AppntBirthday" id=AppntBirthday verify="Ͷ���˳�������|notnull&date" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="wid common" readonly name="AppntAge" id=AppntAge >
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntIDType" id=AppntIDType verify="Ͷ����֤������|code:IDType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('IDType',[this]);" onkeyup="return showCodeListKey('IDType',[this]);">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntIDNo" id=AppntIDNo verify="Ͷ����֤������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="AppntNativePlace" id=AppntNativePlace verify="Ͷ���˹���|code:NativePlace" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('NativePlace',[this]);" onkeyup="return showCodeListKey('NativePlace',[this]);">
          </TD>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntRgtAddress" id=AppntRgtAddress verify="Ͷ���˻������ڵ�|len<=80" >
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntMarriage" id=AppntMarriage verify="Ͷ���˻���״��|code:Marriage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="AppntNationality" id=AppntNationality verify="Ͷ��������|code:Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntDegree" id=AppntDegree verify="Ͷ����ѧ��|code:Degree" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            �뱻�����˹�ϵ
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntRelationToInsured" id=AppntRelationToInsured verify="Ͷ�����뱻�����˹�ϵ|code:Relation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('Relation',[this]);" onkeyup="return showCodeListKey('Relation',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ϵ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntPostalAddress" id=AppntPostalAddress verify="Ͷ������ϵ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntZipCode" id=AppntZipCode verify="Ͷ������������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��ͥ�绰
          </TD>
          <TD  class= input>
          <input class="wid common" name="AppntPhone" id=AppntPhone verify="Ͷ���˼�ͥ�绰|len<=18" >
          </TD>
          <TD  class= title>
            �ƶ��绰
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntMobile" id=AppntMobile verify="Ͷ�����ƶ��绰|len<=15" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntEMail" id=AppntEMail verify="Ͷ���˵�������|len<=20" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntGrpName" id=AppntGrpName verify="Ͷ���˹�����λ|len<=60" >
          </TD>
          <TD  class= title>
            ��λ�绰
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntGrpPhone" id=AppntGrpPhone verify="Ͷ���˵�λ�绰|len<=18" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common3 name="AppntGrpAddress" id=AppntGrpAddress verify="Ͷ���˵�λ��ַ|len<=80" >
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntGrpZipCode" id=AppntGrpZipCode verify="Ͷ���˵�λ��������|zipcode" >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class="wid common" name="AppntWorkType" id=AppntWorkType verify="Ͷ����ְҵ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input> 
            <Input class="wid common" name="AppntPluralityType" id=AppntPluralityType verify="Ͷ���˼�ְ�����֣�|len<=10" >
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntOccupationType" id=AppntOccupationType verify="Ͷ����ְҵ���|code:OccupationType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntOccupationCode" id=AppntOccupationCode verify="Ͷ����ְҵ����|code:OccupationCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('OccupationCode',[this]);" onkeyup="return showCodeListKey('OccupationCode',[this]);">
          </TD>
          <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="code" name="AppntSmokeFlag" id=AppntSmokeFlag verify="Ͷ�����Ƿ�����|code:YesNo" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD>

        </TR>
      </table>   
    </Div>

    <!-- ����Ͷ������Ϣ���� �޸������ֶ�����-->
    <Div  id= "divLCAppntGrp0" style= "display: none">
    <table>
    	<tr>
        	<td class=common>
    	  		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCAppntGrp1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCAppntGrp1" class=maxbox style= "display: ">
      <table  class= common>
        <TR class= common>
          <TD  class= title>
            Ͷ���˿ͻ���
          </TD>
          <TD  class= input>
            <Input class="code" readonly name=ColGrpNo id=ColGrpNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showAppnt();" value="00010220020990000017">
          </TD>
          <TD  class= title>
            ��λ����
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AppGrpName id=AppGrpName >
          </TD>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AppGrpAddress id=AppGrpAddress >
          </TD>          
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=AppGrpZipCode id=AppGrpZipCode >
          </TD>       
          <TD  class= title>
            ��λ����
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=GrpNature id=GrpNature >
          </TD>
          <TD  class= title>
            ��ҵ���
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=BusinessType id=BusinessType >
          </TD>
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            ��λ������
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Peoples id=Peoples >
          </TD>       
          <TD  class= title>
            ע���ʱ���
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=RgtMoney id=RgtMoney >
          </TD>
          <TD  class= title>
            �ʲ��ܶ�
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Asset id=Asset >
          </TD>
        </TR>        
        <TR  class= common>          
          <TD  class= title>
            ���ʲ�������
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=NetProfitRate id=NetProfitRate >
          </TD>       
          <TD  class= title>
            ��Ӫҵ��
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=MainBussiness id=MainBussiness >
          </TD>
          <TD  class= title>
            ��λ���˴���
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Corporation id=Corporation >
          </TD>
        </TR>        
        <TR  class= common>
          <TD  class= title>
            �����ֲ�����
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=ComAera id=ComAera >
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
            <Input class="wid common" readonly name=LinkMan1 id=LinkMan1 >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Department1 id=Department1 >
          </TD>
          <TD  class= title>
            ְ��
          </TD>
          <TD  class= input>
            <Input name=HeadShip1 id=HeadShip1 class="wid common" readonly>
          </TD>
        </TR>        
        <TR  class= common>                 
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=Phone1 id=Phone1 >
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input name=E_Mail1 id=E_Mail1 class="wid common" readonly>
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input  name=Fax1 id=Fax1 class="wid common" readonly>
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
            <Input class="wid common" readonly name=LinkMan2  id=LinkMan2>
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input name=Department2 id=Department2 class="wid common" readonly>
          </TD>
          <TD  class= title>
            ְ��
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=HeadShip2 id=HeadShip2 >
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            ��ϵ�绰
          </TD>
          <TD  class= input>
            <Input name=Phone2 id=Phone2 class="wid common" readonly>
          </TD>
          <TD  class= title>
            E-MAIL
          </TD>
          <TD  class= input>
            <Input name=E_Mail2 id=E_Mail2 class="wid common" readonly>
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input name=Fax2 id=Fax2 class="wid common" readonly>
          </TD>       
        </TR>        
        <TR  class= common>
          <TD  class= title>
            ���ʽ
          </TD>
          <TD  class= input>
            <Input name=GetFlag id=GetFlag class="wid common" readonly>
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input name=GrpBankCode id=GrpBankCode class="wid common" readonly>
          </TD>
          <TD  class= title>
            �ʺ�
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=GrpBankAccNo id=GrpBankAccNo >
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
      <Div  id= "divLCKind1" class=maxbox style= "display: ">
        <table  class= common>
          <TR  class= common>
            <TD  class= title>
              ������Ч����
            </TD>
            <TD  class= input>
	          <input class="coolDatePicker wid" dateFormat="short" name="CValiDate" id=CValiDate verify="������Ч����|notnull&date" >
            </TD>         
            <TD  class= title>
              �Ƿ�ָ����Ч��
            </TD>
            <TD  class= input>
              <Input class="code" name=SpecifyValiDate id=SpecifyValiDate verify="�Ƿ�����|code:YesNo"  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            <TD  class= title>
              �����ڼ�
            </TD>
            <TD  class= input>
              <Input class="wid common" name=InsuYear id=InsuYear verify="�����ڼ�|value<=65535&value>=0&num" >
            </TD>
          </TR>
          
          <TR  class= common>            
            <TD  class= title>
              ��������
            </TD>
            <TD  class= input>
              <Input class="wid common" name=PayEndYear id=PayEndYear verify="��������|value<=65535&value>=0&num" >
            </TD>
            <TD  class= title>
              ���ѷ�ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=PayIntv id=PayIntv verify="���ѷ�ʽ|code:PayIntv" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PayIntv',[this]);" onkeyup="return showCodeListKey('PayIntv',[this]);">
            </TD>          
            <TD  class= title>
              �罻����ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=OutPayFlag id=OutPayFlag verify="�罻����ʽ|code:OutPayFlag" 
			  ondblclick="return showCodeList('OutPayFlag',[this]);" onkeyup="return showCodeListKey('OutPayFlag',[this]);">
            </TD>                      
          </TR>
          
          <TR  class= common>
            <TD  class= title>
              �շѷ�ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=PayLocation id=PayLocation verify="�շѷ�ʽ|code:PayLocation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PayLocation',[this]);" onkeyup="return showCodeListKey('PayLocation',[this]);">
            </TD>
            <TD  class= title>
              ������
            </TD>
            <TD  class= input>
              <Input class="code" name=BankCode id=BankCode verify="������|code:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);">
            </TD>
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class="wid common" name=countName id=countName verify="����|len<=20" >
            </TD>
          </TR>

          <TR  class= common>
            <TD  class= title>
              �����ʺ�
            </TD>
            <TD  class= input>
              <Input class="wid common" name=BankAccNo id=BankAccNo verify="�����ʺ�|len<=40" >
            </TD>
            <TD  class= title>
              ���汣�ս���ȡ��ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=LiveGetMode id=LiveGetMode verify="���汣�ս���ȡ��ʽ|code:LiveGetMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('LiveGetMode',[this]);" onkeyup="return showCodeListKey('LiveGetMode',[this]);">
            </TD>
          </TR>
          
          <TR  class= common>
            <TD  class= title>
              ��ȡ����
            </TD>
            <TD  class= input>
              <Input class="wid common" name=GetYear id=GetYear verify="��ȡ����|len<=10" >
            </TD>
            <TD  class= title>
              ��ȡ��ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=GetIntv id=GetIntv verify="��ȡ��ʽ|code:getIntv" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('getIntv',[this]);" onkeyup="return showCodeListKey('getIntv',[this]);">
            </TD>
            <TD  class= title>
              ������ȡ��ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=BonusGetMode id=BonusGetMode verify="������ȡ��ʽ|code:BonusMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
			  ondblclick="return showCodeList('BonusMode',[this]);" onkeyup="return showCodeListKey('BonusMode',[this]);">
            </TD>       
          </TR>
          
          <TR  class= common>                    
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class="wid common" name=Mult id=Mult verify="����|notnull&len<=13&num" >
            </TD>
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class="wid common" readonly name=Prem id=Prem >
            </TD>
            <TD  class= title>
              ����
            </TD>
            <TD  class= input>
              <Input class="wid common" name=Amnt id=Amnt verify="����|notnull&len<=10&num" >
            </TD> 
          </TR>

          <TR  class= common>                         
            <TD  class= title>
              �Ƿ�����
            </TD>
            <TD  class= input>
              <Input class="code" name=HealthCheckFlag id=HealthCheckFlag verify="�Ƿ�����|code:YesNo" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            
          </TR>
        </table>
      </Div>
      <!-- ���� -->
    <Div  id= "divLCKind0" style= "display: none">
      <table  class= common>
	      <TR  class= common>
            
            <TD  class= title>
              �ս��ڼ䵥λ
            </TD>
            <TD  class= input>
              <Input class="code" name=PayEndYearFlag id=PayEndYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);">
            </TD>
            <TD  class= title>
              �����ڼ䵥λ
            </TD>
            <TD  class= input>
              <Input class="code" name=GetYearFlag id=GetYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			  ondblclick="return showCodeList('PeriodUnit',[this]);" onkeyup="return showCodeListKey('PeriodUnit',[this]);">
            </TD>
            <TD  class= title>
              �������ڼ�������
            </TD>
            <TD  class= input>
              <Input class="code" name=GetStartType id=GetStartType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('CalRefType',[this]);" onkeyup="return showCodeListKey('CalRefType',[this]);">
            </TD>
          </TR>
          <TR  class= common>
            <TD  class= title>
              �����ڼ䵥λ
            </TD>
            <TD  class= input>
              <Input class="code" name=InsuYearFlag id=InsuYearFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('PeriodUnit1',[this]);" onkeyup="return showCodeListKey('PeriodUnit1',[this]);">
            </TD>
            <TD  class= title>
              �Զ��潻��־
            </TD>
            <TD  class= input>
              <Input class="code" name=AutoPayFlag id=AutoPayFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
            <TD  class= title>
              �������ʽ
            </TD>
            <TD  class= input>
              <Input class="code" name=InterestDifFlag id=InterestDifFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('InterestDifFlag',[this]);" onkeyup="return showCodeListKey('InterestDifFlag',[this]);">
            </TD>
		
          </TR>
          <TR  class= common>
            <TD  class= title>
              ������־
            </TD>
            <TD  class= input>
              <Input class="code" name=SubFlag id=SubFlag style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
            </TD>
			<TD  class= title></TD>
            <TD  class= input></td>
			<TD  class= title></TD>
            <TD  class= input></td>
          </TR>
      </table>
    </Div>

    <!-- ������������Ϣ���֣��б� -->
	<Div  id= "divLCInsured0" style= "display: none">
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
	  <Div  id= "divLCInsured2" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
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
	<Div  id= "divLCBnf1" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
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
	<Div  id= "divLCImpart1" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
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
	<Div  id= "divLCSpec1" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanSpecGrid">
					</span> 
				</td>
			</tr>
		</table>
	</div>

    <!--����ѡ������β��֣��ò���ʼ������-->
	<Div  id= "divDutyGrid" style= "display: ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
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


