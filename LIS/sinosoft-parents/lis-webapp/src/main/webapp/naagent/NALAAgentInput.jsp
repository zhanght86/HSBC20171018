<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
<%
String gToday = PubFun.getCurrentDate();
  GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
    
 %>
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="NALAAgentInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NALAAgentInit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./NALAAgentSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent1);">
          <td class=titleImg>
            ��������Ϣ
          </td>
        </td>
      </tr> 
    </table>
    <div id= "divLAAgent1" class="maxbox" style= "display:  ">      
    <table  class= common>
      <TR  class= common> 
        <TD class= title> 
          �����˱��� 
        </TD>
        <TD  class= input> 
          <Input class= 'readonly wid' readonly name=AgentCode >
        </TD>        
        <TD  class= title>
          ����
        </TD>
        <TD  class= input>
          <Input name=Name id="Name" class="common wid" verify="����|NotNull&len<=20"
                 onkeyup="value=value.replace(/[^A-Za-z\u4E00-\u9FA5]/g, )"
                 onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^A-Za-z\u4E00-\u9FA5]/g, ))" 
                 maxlength=20> 
                 <font color=red>*</font>
        </TD>
        <TD  class= title>
          �Ա� 
        </TD>
        <TD  class= input>
          <Input name=Sex id="Sex" class="code" verify="�Ա�||NotNull&code:Sex" 
          ondblclick="return showCodeList('Sex',[this]);" 
          onkeyup="return showCodeListKey('Sex',[this]);" > <font color=red>*</font>
        </TD> 
        <!--TD  class= title> 
          ���� 
        </TD> 
        <TD  class= input>
          <Input name=Password class=common > 
        </TD--> 
      </TR> 
       <TR  class= common> 
        <!--TD  class= title> 
          �Ƽ�������� 
        </TD>
        <TD  class= input> 
          <Input name=EntryNo class= common > 
        </TD-->
        <TD  class= title >
          �������� 
        </TD>
        <TD  class= input >
          <Input name=Birthday class="common wid"  onClick="laydate({elem: '#Birthday'});" verify="��������|NotNull&Date" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> <font color=red>*</font>
        </TD>
      
        <TD  class= title >
          ����
        </TD>
        <TD  class= input>
          <Input name=Nationality id="Nationality" class="code" id="Nationality" ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);" > 
        </TD>
      </TR>
      <tr class=common>
      <td  class=title>
       ֤������
      </td>
      <td class=common>
      <input name=IdType class=code readonly="readonly" VALUE=""
			MAXLENGTH=1 CLASS=code style="background:url(../common/images/select--bg_03.png) no-repeat right center"
			ondblclick="return showCodeList('IDType', [this]);"
			onkeyup="return showCodeListKey('IDType', [this]);"
			verify="֤������|code:IDType">
      </td>
      <TD  class= title>
         ֤������ 
        </TD>
        <TD  class= input> 
          <Input name=IDNo id="IDNo" class= "common wid" verify="֤������|notnull&len<=18"
           onchange="return changeIDNo();"> <font color=red>*</font>
        </TD> 
      
      
      
      </tr>
      <TR  class= common> 
        <TD  class= title> 
          ����
        </TD>
        <TD  class= input>
          <Input name=NativePlace id="NativePlace"  class="code" verify="����|code:NativePlaceBak" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id="NativePlaceBak" ondblclick="return showCodeList('NativePlaceBak',[this]);" onkeyup="return showCodeListKey('NativePlaceBak',[this]);">
        </TD>   
        <TD  class= title>
          ������ò 
        </TD>
        <TD  class= input> 
          <Input name=PolityVisage id="PolityVisage" class="code" verify="������ò|code:polityvisage" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id="polityvisage" ondblclick="return showCodeList('polityvisage',[this]);" onkeyup="return showCodeListKey('polityvisage',[this]);" > 
        </TD> 
        <TD  class= title>
          �������ڵ�
        </TD>
        <TD  class= input> 
          <Input name=RgtAddress id="RgtAddress" class="code" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('NativePlaceBak',[this]);" onkeyup="return showCodeListKey('NativePlaceBak',[this]);"> 
        </TD>
      </TR>
      <!--TR  class= common> 
        <TD  class= title>
          ��Դ�� 
        </TD>
        <TD  class= input> 
          <Input name=Source class= common > 
        </TD>        
        <TD  class= title> 
          Ѫ��
        </TD>
        <TD  class= input> 
          <Input name=BloodType class="code" verify="Ѫ��|code:BloodType" id="BloodType" ondblclick="return showCodeList('BloodType',[this]);" onkeyup="return showCodeListKey('BloodType',[this]);"> 
        </TD>        
        <TD  class= title>
          ����״�� 
        </TD>
        <TD  class= input>
          <Input name=Marriage class="code" verify="����״��|code:Marriage" ondblclick="return showCodeList('Marriage',[this]);" onkeyup="return showCodeListKey('Marriage',[this]);" >
        </TD>   
      </TR>
      <tr class=common>           
        <TD  class= title> 
          �������
        </TD>
        <TD  class= input> 
          <Input name=MarriageDate class="coolDatePicker" dateFormat="short" > 
        </TD>     
      </tr-->
      <TR  class= common> 
        <TD  class= title>
          ѧ�� 
        </TD>
        <TD  class= input> 
          <Input name=Degree class="code" verify="ѧ��|code:Degree" id="Degree" ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);"> 
        </TD>
       <TD  class= title> 
          ��ҵԺУ
        </TD>
        <TD  class= input> 
          <Input name=GraduateSchool id="GraduateSchool" class="common wid" > 
        </TD>
        <TD  class= title>
          רҵ 
        </TD>
        <TD  class= input> 
          <Input name=Speciality id="Speciality" class="common wid" > 
        </TD>
      </TR>
      <TR  class= common>
        <!--TD  class= title> 
          ����ˮƽ 
        </TD>
        <TD  class= input> 
          <Input name=ForeignLevel class="code" verify="����ˮƽ|code:EngLevel" id="EngLevel" ondblclick="return showCodeList('EngLevel',[this]);" onkeyup="return showCodeListKey('EngLevel',[this]);"> 
        </TD-->
        <TD  class= title>
          ְ�� 
        </TD>
        <TD  class= input> 
          <Input name=PostTitle id="PostTitle" class='code' style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="ְ��|code:posttitle" ondblclick="return showCodeList('posttitle',[this]);" onkeyup="return showCodeListKey('posttitle',[this]);" > 
        </TD>
        <!--TD  class= title>
          ��ͥ��ַ����
        </TD>
        <TD  class= input>
          <Input name=HomeAddressCode class= common >
        </TD-->
        <TD  class= title>
          ��ͥ��ַ 
        </TD>
        <TD  class= input> 
          <Input name=HomeAddress  id="HomeAddress" class="common wid" > <font color=red>*</font>
        </TD>
        <!--TD  class= title> 
          ͨѶ��ַ 
        </TD>
        <TD  class= input> 
          <Input name=PostalAddress class= common > 
        </TD-->
        <TD  class= title>
          �������� 
        </TD>
        <TD  class= input> 
          <Input name=ZipCode id="ZipCode" class="common wid" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          �绰 
        </TD>
        <TD  class= input> 
          <Input name=Phone id="Phone" class="common wid">
        </TD>
        <TD  class= title> 
          ����
        </TD>
        <TD  class= input>
          <Input name=BP id="BP" class="common wid">
        </TD>
        <TD  class= title>
          �ֻ� 
        </TD>
        <TD  class= input> 
          <Input name=Mobile id="Mobile" class="common wid">
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          E_mail 
        </TD>
        <TD  class= input> 
          <Input name=EMail id="EMail" class="common wid" > 
        </TD>
        <!--TD  class= title>
          �Ƿ����̱�־ 
        </TD>
        <TD  class= input> 
          <Input name=SmokeFlag class='code' verify="�Ƿ����̱�־|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);"> 
        </TD-->
        <TD  class= title>
          ���б���
        </TD>
        <TD  class= input> 
          <Input name=BankCode id="BankCode" class='code' verify="���б���|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);"> 
        </TD>
        <TD  class= title>
          ���ʴ����ʺ� 
        </TD>
        <TD  class= input> 
          <Input name=BankAccNo id="BankAccNo" class="common wid" > 
        </TD>
      </TR>
      <TR  class= common>
        <!--TD  class= title>
          ��ҵ���� 
        </TD>
        <TD  class= input> 
          <Input name=WorkAge id="WorkAge" class="common wid"  > 
        </TD-->
        <TD  class= title>
          ԭ������λ 
        </TD>
        <TD  class= input> 
          <Input name=OldCom id="OldCom" class="common wid" > 
        </TD>
        <TD  class= title> 
          ԭְҵ 
        </TD>
        <TD  class= input> 
          <Input name=OldOccupation id="OldOccupation" class='code' style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="ԭְҵ|code:occupation" ondblclick="return showCodeList('occupation',[this]);" onkeyup="return showCodeListKey('occupation',[this]);"> 
        </TD>
        <TD  class= title>
          ����ְ�� 
        </TD>
        <TD  class= input> 
          <Input name=HeadShip id="HeadShip" class="common wid"  > 
        </TD>
      </TR>
      <!--TR  class= common> 
        <TD  class= title>
          �Ƽ������� 
        </TD>
        <TD  class= input> 
          <Input name=RecommendAgent class="common wid"  > 
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
          <Input name=SaleQuaf class='code' verify="�����ʸ�|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD-->
        <TD  class= title>
          �������ʸ�֤���� 
        </TD>
        <TD  class= input> 
          <Input name=QuafNo id="QuafNo" class="common wid" 
          verify="�������ʸ�֤����|NOTNULL"
          onkeyup="value=value.replace(/[^a-zA-Z0-9]/g, )"
          onmouseover="value=value.replace(/[^a-zA-Z0-9]/g, )"   
          onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^a-zA-Z0-9]/g, ))"
          maxlength=20
          >
        </TD>
        <TD  class= title>
          ֤��������� 
        </TD>
        <TD  class= input> 
          <Input name=QuafEndDate id="QuafEndDate"  class="coolDatePicker"  verify="֤���������|NOTNULL" dateFormat="short" onClick="laydate({elem: '#QuafEndDate'});" >
          <span class="icon"><a onClick="laydate({elem: '#QuafEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		  <input name=currDate type=hidden value='<%=gToday%>' >
        </TD>
        <TD  class= title>
          չҵ֤����1 
        </TD>
        <TD  class= input> 
          <Input name=DevNo1 id="DevNo1" class="common wid" maxlength=26> 
        </TD>
      </TR>
      <!--TR  class= common>         
        <TD  class= title> 
          ֤�鿪ʼ���� 
        </TD>
        <TD  class= input> 
          <Input name=QuafStartDate class="coolDatePicker" dateFormat="short" > 
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
          <Input name=AgentKind class='code' verify="���������|code:AgentKind" ondblclick="return showCodeList('AgentKind',[this]);" onkeyup="return showCodeListKey('AgentKind',[this]);"  > 
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
          <Input name=InsideFlag class='code' verify="���ڱ�־|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
        <TD  class= title>
          �Ƿ�רְ��־ 
        </TD>
        <TD  class= input> 
          <Input name=FullTimeFlag class='code' verify="�Ƿ�רְ��־|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD>
      </TR-->
      <TR  class= common> 
        <!--TD  class= title>
          �Ƿ��д�ҵ֤��־ 
        </TD>
        <TD  class= input> 
          <Input name=NoWorkFlag class='code' verify="�Ƿ��д�ҵ֤��־|code:yesno" ondblclick="return showCodeList('yesno',[this]);" onkeyup="return showCodeListKey('yesno',[this]);" > 
        </TD-->
        <TD  class= title>
          ��ѵ���� 
        </TD>
        <TD  class= input> 
          <Input name=TrainPeriods id="TrainPeriods" class="common wid" verify="��ѵ����|INT" > 
        </TD>
        <TD  class= title>
          ��˾ʱ�� 
        </TD>
        <TD  class= input> 
          <Input name=EmployDate id="EmployDate" class="readonly wid"readonly> 
        </TD>
        <TD  class= title>
          ��֤��֤�� 
        </TD>
        <TD  class= input> 
          <Input name=AssuMoney id="AssuMoney" class="readonly wid" readonly="readonly" verify="��֤��|notnull&value>"> <font color=red>*</font>
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          ��ע
        </TD>
        <TD  class=input colSpan= 3> 
          <Input name=Remark id="Remark" class= common3 > 
        </TD>
        <!--TD  class= title>
          ת������ 
        </TD>
        <TD  class= input> 
          <Input name=InDueFormDate class='coolDatePicker' dateFormat='short' > 
        </TD-->
        <!--TD  class= title>
          ������״̬ 
        </TD>
        <TD  class= input> 
          <Input name=AgentState class='code' verify="������״̬|code:agentstate" ondblclick="return showCodeList('agentstate',[this]);" onkeyup="return showCodeListKey('agentstate',[this]);" > 
        </TD-->
        <TD  class= title>
          ����Ա���� 
        </TD>
        <TD  class= input> 
          <Input name=Operator id="Operator" class= "readonly wid" readonly > 
        </TD>
      </TR>
      <!--TR  class= common> 
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
      <TR class= common> 
        <TD  class= title>
          ��־λ
        </TD>
        <TD  class= input> 
          <Input name=QualiPassFlag class= common MAXLENGTH=1> 
        </TD>
      </TR-->
    </table>
       <!--������״̬--> <Input name=AgentState id="AgentState" type=hidden value ='01' > 
       <!--������״̬--> <Input name=initAgentState id="initAgentState" type=hidden value ='01' > 
    </div>	
    <!--������Ϣ-->    
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent3);">
            <td class= titleImg>
                ������Ϣ
            </td>
            </td>
    	</tr>
     </table>
     <Div id= "divLAAgent3" class="maxbox" style= "display:  ">
       <table class=common>
        <tr class=common>     
        <TD class= title>
          ������ְ��
        </TD>
        <TD class= input>
          <Input name=AgentGrade1 id="AgentGrade1" class="code" readonly verify="������ְ��|notnull&code:AgentGrade1" 
		  style="background:url(../common/images/select--bg_03.png) no-repeat right center"
          ondblclick="return showCodeList('AgentGrade1',[this],[0,1],null,'<%=tSqlAgentGrade+" and code <> #M00# "%>','1');" 
          onkeyup="return showCodeListKey('AgentGrade1',[this],[0,1],null,'<%=tSqlAgentGrade+" and code <> #M00# "%>','1');" 
          > 
          <font color=red>*</font>
        </TD>
        <TD class= title>
          �������
        </TD>
        <TD class= input>
          <!--Input name=ManageCom class='code' verify="�������|notnull" ondblclick="return showCodeList('station',[this]);" --> 
          <Input name=ManageCom id="ManageCom" class="readonly wid"readonly >
        </TD>
        <TD class= title>
          �Ƽ��� 
        </TD>
        <TD class= input>
          <Input class="common wid" id="IntroAgency" name=IntroAgency onchange="return changeIntroAgency();"> 
        </TD>
        
        </tr>
        <tr class=common>
        <TD class= title>
          ���ۻ��� 
        </TD>
        <TD class= input>
          <Input class="common wid" name=BranchCode id="BranchCode" verify="���ۻ���|notnull" 
          onchange="return changeGroup();"> <font color=red>*</font>
        </TD>
        <TD class= title>
          ҵ������
        </TD>
        <TD class= input>
          <Input name=GroupManagerName id="GroupManagerName" class="readonly wid" readonly > 
        </TD>
        <TD class= title>
          ������
        </TD>
        <TD class= input>
          <Input name=DepManagerName id="DepManagerName" class="readonly wid"readonly > 
        </TD>
        </tr>
        <tr class=common>
        <TD class= title>
          ������
        </TD>
        <TD class= input>
          <Input name=DirManagerName id="DirManagerName" class="readonly wid"readonly > 
        </TD>
        <TD class= title>
          �ܼ�
        </TD>
        <TD class= input>
          <Input name=MajordomoName id="MajordomoName" class="readonly wid"readonly > 
        </TD>
        <TD class= title>
          ��������
        </TD>
        <TD class= input>
          <Input name=RearAgent id="RearAgent" class="common wid" verify="��������|len<=10" onchange= "return getInputAgentName(2);"> 
        </TD>
        </tr>
        <tr class=common>
        <TD class= title>
          ��������
        </TD>
        <TD class= input>
          <Input name=RearDepartAgent id="RearDepartAgent" class="common wid" verify="��������|len<=10" onchange= "return getInputAgentName(3);"> 
        </TD>
        <TD class= title>
          �������� 
        </TD>
        <TD class= input>
          <Input class="common wid" name=RearSuperintAgent id="RearSuperintAgent" verify="��������|len<=10" onchange= "return getInputAgentName(4);"> 
        </TD>
        <TD class= title>
          �ܼ������� 
        </TD>
        <TD class= input>
          <Input class="common wid" name=RearAreaSuperintAgent id="RearAreaSuperintAgent" verify="�ܼ�������|len<=10" onchange= "return getInputAgentName(5);"> 
        </TD>
        </tr>
       </table>   
         <!--������ϵ��--> <Input name=AgentSeries id="AgentSeries" type=hidden> 
    </Div>
    <!--��������Ϣ���б� -->
    <table>
    	<tr>
            <td class="common">
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAgent2);">
            <td class= titleImg>
                ��������Ϣ
            </td>
            </td>
    	</tr>
     </table>
    <Div  id= "divLAAgent2"  style= "display:  ">
    <table  class= common>
            <tr  class= common>
                    
        <td style="text-align: left" colSpan=1> 
		  <span id="spanWarrantorGrid" >
		  </span> 
        </td>
                    </tr>
            </table>
    </div>
<input type=hidden name=WFlag value='zy'>
    <input type=hidden id="hideOperate"  name=hideOperate value= >
    <input type=hidden id="AgentGrade"  name=AgentGrade value=  >
    <input type=hidden id="initOperate"  name=initOperate value='INSERT'>
    <input type=hidden id="hideIsManager"  name=hideIsManager value='false'>
    <input type=hidden id="hideManageCom"  name=hideManageCom value='<%=tGI.ManageCom%>'>
    <input type=hidden id="BranchType"  name=BranchType value= >
    <input type=hidden id="hideBranchCode"  name=hideBranchCode value= > <!--���������ʽ����-->
    <input type=hidden id="UpAgent"  name=UpAgent value= >
    <input type=hidden id="ManagerCode"  name=ManagerCode value= > <!--����������Ա����-->
    <input type=hidden id="upBranchAttr"  name=upBranchAttr value= >
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	<%@include file="../common/jsp/OperateAgentButton.jsp"%>
    <%@include file="../common/jsp/InputButton.jsp"%>
  </form>
</body>
</html>

        
