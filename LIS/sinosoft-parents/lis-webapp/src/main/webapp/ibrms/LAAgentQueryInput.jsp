<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LAAgentQueryInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:31:08
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
 

<html>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./LAAgentQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./LAAgentQueryInit.jsp"%>
  <%@include file="../common/jsp/ManageComLimit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
  <title>�����˲�ѯ </title>
</head>
<body  onload="initForm();" >
  <form action="./LAAgentQuerySubmit.jsp" method=post id=fm   name=fm target="fraSubmit">
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
  <Div  id= "divLAAgent" class=maxbox style= "display: ''">
  <table  class= common>
      <TR  class= common> 
        <TD class= title> 
          �����˱��� 
        </TD>
        <TD  class= input> 
          <Input class="common wid" id=AgentCode   name=AgentCode >
        </TD>        
        <TD  class= title> 
          ����
        </TD>
        <TD  class= input>
          <Input id=Name   name=Name class="common wid" verify="����|NotNull&len<=20" elementtype=nacessary>
        </TD>
        <TD  class= title>
          �Ա� 
        </TD>
        <TD  class= input>
          <Input id=Sex  name=Sex class="codeno" elementtype=nacessary verify="�Ա�|code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" 
          onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input id=SexName   name=SexName class='codename' readonly=true>
        </TD> 
      </TR> 
      <TR  class= common> 
        <TD  class= title>
          �������� 
        </TD>
        <TD  class= input>
          <Input id=Birthday   name=Birthday class="common wid"  verify="��������|NotNull&Date" elementtype=nacessary> 
        </TD>
        <TD  class= title>
          ֤����������
        </TD>
        <TD  class= input> 
          <Input id=IDNoType   name=IDNoType class="codeno" verify="֤����������|code:idtype" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          id="idtype" ondblclick="return showCodeList('idtype',[this,IDNoTypeName],[0,1]);" 
          onkeyup="return showCodeListKey('idtype',[this,IDNoTypeName],[0,1]);"><input id=IDNoTypeName   name=IDNoTypeName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          ֤������ 
        </TD>
        <TD  class= input> 
          <Input id=IDNo   name=IDNo class="common wid" > 
        </TD>
        </TR> 
        <TR  class= common> 
        <TD  class= title>
          ����
        </TD>
        <TD  class= input>
          <Input name=Nationality class="codeno" id="Nationality" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('Nationality',[this,NationalityName],[0,1]);"
           onkeyup="return showCodeListKey('Nationality',[this,NationalityName],[0,1]);" ><input id=NationalityName   name=NationalityName class='codename' readonly=true> 
        </TD>
        <TD  class= title> 
          ����
        </TD>
        <TD  class= input>
          <Input id=NativePlace   name=NativePlace class="codeno" verify="����|code:nativeplacebak" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          id="nativeplacebak" ondblclick="return showCodeList('nativeplacebak',[this,NativePlaceName],[0,1]);" 
          onkeyup="return showCodeListKey('nativeplacebak',[this,NativePlaceName],[0,1]);"><input id=   name =NativePlaceName class='codename' readonly=true>
        </TD>   
        <TD  class= title>
          ������ò 
        </TD>
        <TD  class= input> 
          <Input id=PolityVisage  name=PolityVisage class="codeno" verify="������ò|code:polityvisage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          id="polityvisage" ondblclick="return showCodeList('polityvisage',[this,PolityVisageName],[0,1]);"
           onkeyup="return showCodeListKey('polityvisage',[this,PolityVisageName],[0,1]);" ><input id=PolityVisageName   name=PolityVisageName class='codename' readonly=true> 
        </TD> 
      </TR>
      <TR  class= common> 
        <TD  class= title>
          �������ڵ�
        </TD>
        <TD  class= input> 
          <Input id=RgtAddress   name=RgtAddress class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('NativePlaceBak',[this,RgtAddressName],[0,1]);" 
          onkeyup="return showCodeListKey('NativePlaceBak',[this,RgtAddressName],[0,1]);"><input id=RgtAddressName   name=RgtAddressName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          ѧ�� 
        </TD>
        <TD  class= input> 
          <Input  name=Degree class="codeno" 
          verify="ѧ��|code:degree" id="degree" 
          ondblclick="return showCodeList('degree',[this,DegreeName],[0,1]);" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          onkeyup="return showCodeListKey('degree',[this,DegreeName],[0,1]);"><input id=DegreeName   name=DegreeName class='codename' readonly=true> 
        </TD>
       <TD  class= title> 
          ��ҵԺУ
        </TD>
        <TD  class= input> 
          <Input id=GraduateSchool   name=GraduateSchool class="common wid" > 
        </TD>
      </TR>
      <TR  class= common> 
        <TD  class= title>
          רҵ 
        </TD>
        <TD  class= input> 
          <Input id=Speciality   name=Speciality class="common wid" > 
        </TD>
        <TD  class= title>
          ְ�� 
        </TD>
        <TD  class= input> 
          <Input id=PostTitle name=PostTitle class='codeno' verify="ְ��|code:posttitle" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
           ondblclick="return showCodeList('posttitle',[this,PostTitleName],[0,1]);" 
           onkeyup="return showCodeListKey('posttitle',[this,PostTitleName],[0,1]);" ><input id=   name=PostTitleName class='codename' readonly=true>
        </TD>
        <TD  class= title>
          ��ͥ��ַ 
        </TD>
        <TD  class= input> 
          <Input id=HomeAddress   name=HomeAddress class="common wid" > 
        </TD>
      </TR>
       <TR  class= common> 
       	<TD  class= title>
          ͨѶ��ַ 
        </TD>
        <TD  class= input> 
          <Input id=PostalAddress   name=PostalAddress class="common wid" > 
        </TD>
        <TD  class= title>
          �������� 
        </TD>
        <TD  class= input> 
          <Input id=ZipCode   name=ZipCode class="common wid" > 
        </TD>
        <TD  class= title>
          �绰 
        </TD>
        <TD  class= input> 
          <Input id=Phone   name=Phone class="common wid" > 
        </TD>
      </TR>
      <TR  class= common>
        <!--TD  class= title> 
          ����
        </TD>
        <TD  class= input>
          <Input id=BP   name=BP class="common wid" > 
        </TD-->  
        <TD  class= title>
          �ֻ� 
        </TD>
        <TD  class= input> 
          <Input id=Mobile   name=Mobile class="common wid" > 
        </TD>
        <TD  class= title>
          E_mail 
        </TD>
        <TD  class= input> 
          <Input id=EMail   name=EMail class="common wid" > 
        </TD>
        <TD  class= title>
          ��ҵ���� 
        </TD>
        <TD  class= input> 
          <Input id=Workage   name=Workage class="common wid"  > 
        </TD>
      </TR>
       <TR  class= common>
        <TD  class= title>
          ���б���
        </TD>
        <TD  class= input> 
          <Input id=BankCode   name=BankCode class='codeno' verify="���б���|code:HeadOffice" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('HeadOffice',[this,BankCodeName],[0,1]);" 
          onkeyup="return showCodeListKey('HeadOffice',[this,BankCodeName],[0,1]);"><input id=BankCodeName   name=BankCodeName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          �����˻� 
        </TD>
        <TD  class= input> 
          <Input id=BankAccNo   name=BankAccNo class="common wid" > 
        </TD>
        <TD  class= title>
         ��֤���վݺ�
        </TD>
        <TD  class= input> 
          <Input id=ReceiptNo   name=ReceiptNo class="common wid" verify="��֤���վݺ�|notnull&value>0"> 
        </TD>        
      </TR>
        <TR  class= common>
        <TD  class= title>
          ԭ������λ 
        </TD>
        <TD  class= input> 
          <Input id=OldCom   name=OldCom class="common wid" > 
        </TD>
        <TD  class= title> 
          ԭְҵ 
        </TD>
        <TD  class= input> 
          <Input id=OldOccupation   name=OldOccupation class='codeno' verify="ԭְҵ|code:occupation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('occupation',[this,OldOccupationName],[0,1]);"
           onkeyup="return showCodeListKey('occupation',[this,OldOccupationName],[0,1]);"><input id=OldOccupationName   name=OldOccupationName class='codename' readonly=true> 
        </TD>
        <TD  class= title>
          ����ְ�� 
        </TD>
        <TD  class= input> 
          <Input id=HeadShip   name=HeadShip class="common wid"  > 
        </TD>
      </TR>
      <TR  class= common>
        <TD  class= title>
          �ʸ�֤���� 
        </TD>
        <TD  class= input> 
          <Input id=QuafNo   name=QuafNo class="common wid" > 
        </TD>
        <TD  class= title>
          �ʸ�֤��������
        </TD>
        <TD  class= input> 
          <Input id=GrantDate   name=GrantDate class='coolDatePicker' dateFormat='short'onClick="laydate({elem: '#GrantDate'});" ><span class="icon"><a onClick="laydate({elem: '#GrantDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD  class= title>
          չҵ֤���� 
        </TD>
        <TD  class= input> 
          <Input id=DevNo1   name=DevNo1 class="common wid" > 
        </TD>   
      </TR>
        <TR  class= common>  
        <!--TD  class= title>
          ������VIP���� 
        </TD>
        <TD  class= input> 
          <Input id=   name=VIPProperty class="common wid"> 
        </TD-->
       
       <TD  class= title>
          ��˾ʱ�� 
        </TD>
        <TD  class= input> 
           <Input id=EmployDate   name=EmployDate class='coolDatePicker' dateFormat='short' verify="��˾ʱ��|notnull&Date" elementtype=nacessaryonClick="laydate({elem: '#EmployDate'});" ><span class="icon"><a onClick="laydate({elem: '#EmployDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        
        </TR>
    </table>
    
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
     <Div id= "divLAAgent3" class=maxbox style= "display: ''">
       <table class=common>
       <tr class=common>
        <TD width="11%" class= title>
          ְ��
        </TD>
        <TD width="27%" class= input>
          <Input id=AgentGrade   name=AgentGrade class="codeno" 
          verify="������ְ��|notnull&code:AgentGrade" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
          ondblclick="return showCodeList('AgentGrade',[this,AgentGradeName],[0,1],null,'<%=tSqlAgentGrade%>','1');" 
          onkeyup="return showCodeListKey('AgentGrade',[this,AgentGradeName],[0,1],null,'<%=tSqlAgentGrade%>','1');" >\
		  <input id=AgentGradeName   name=AgentGradeName class="codename" readonly=true elementtype=nacessary >
          
        </TD>
        
         <TD class= title>
          �������
       </TD>
        <TD class= input>
          <input id=ManageCom   name=ManageCom class="common" elementtype=nacessary verify="�������|notnull">
          <input type=button class="cssButton" value="ѡ��" onClick="SelectCom();">
        </TD>

        <TD class= title>
          ���ۻ���
        </TD>
        <TD class= input>
          <Input type=text class="common" id=BranchCode   name=BranchCode elementtype=nacessary verify="���ۻ���|notnull" onBlur="return checkManageCom();">
		  <input type=button id=selectBtn   name=selectBtn class="cssButton" value="ѡ��" onClick="SelectBranch();">
        </TD>
        
   <!--�������2007-12-03������Ĵ�������޸�-->
   <TR>
       <TD width="11%" class= title  >�Ƽ��˴���
         </TD>
        <TD width="23%" class= input>
        <input class="common wid" id=IntroAgency   name=IntroAgency  onChange="return changeIntroAgency();">       
         </TD>
        <TD  class= title  >�Ƽ�������
         </TD>
        <TD  class= input><input class="readonly wid"   id=IntroAgencyName   name=IntroAgencyName >
         </TD>
     </TR>
    
      <!--
      <TR class=comm> 

        <TD class= title>
          �������
       </TD>
        <TD class= input>
          <input id=ManageCom   name=ManageCom class="common wid" elementtype=nacessary verify="�������|notnull">
          <input type=button class="cssButton" value="ѡ��" onClick="SelectCom();">
        </TD>

        <TD class= title>
          ���ۻ���
        </TD>
        <TD class= input>
          <Input type=text class="common wid" id=BranchCode   name=BranchCode elementtype=nacessary verify="���ۻ���|notnull" onBlur="return checkManageCom();">
		 		 <input type=button id=selectBtn   name=selectBtn class="cssButton" value="ѡ��" onClick="SelectBranch();">
        </TD>
        <TD class= title  >
          ������
        </TD>
        <TD class= input>
          <Input id=RearAgent   name=RearAgent class="common wid"   verify="��������|len<=10" onchange="return disPlayRearAgentName()">
        </TD>
      </TR>
  
        <tr class=common>
        	
        <TD class= title  >
          ������
        </TD>
        <TD class= input>
          <Input id=RearDepartAgent   name=RearDepartAgent     class="common wid" verify="������|len<=10" onchange="return disPlayRearDepartAgentName()">
        </TD>
        <TD class= title >
          ����������
        </TD>
        <TD class= input>
          <Input id=RearAgentName   name=RearAgentName class="readonly wid" >
        </TD>
        <TD class= title  > 
          ����������
        </TD>
        <TD class= input>
          <Input id=RearDepartAgentName   name=RearDepartAgentName class="readonly wid"  >
        </TD>-->
        </tr> 
        </table>
        <!--ly 20070611 Ϊ�˲��������ļ���ֱ�������ｫһЩ�ֶ��ó����صģ���ʵ��Щ����û�õ�-->
        <input class="readonly wid"   type=hidden  id=IntroAgency   name=IntroAgency >
        <input class="readonly wid"   type=hidden  id=IntroAgencyName   name=IntroAgencyName >
        <input class="readonly wid"   type=hidden  id=RearAgent   name=RearAgent >
        <input class="readonly wid"   type=hidden  id=RearDepartAgent   name=RearDepartAgent >
        <input class="readonly wid"   type=hidden  id=RearAgentName   name=RearAgentName >
        <input class="readonly wid"   type=hidden  id=RearDepartAgentName   name=RearDepartAgentName >
        
         <!--������ϵ��--> <Input id=AgentSeries   name=AgentSeries type=hidden>
    </Div>
    
          <input type=hidden id=BranchType   name=BranchType value=''>
          <INPUT VALUE="��  ѯ" class="cssButton" TYPE=button onclick="easyQueryClick();"> 
          <INPUT VALUE="��  ��" class="cssButton" TYPE=button onclick="returnParent();"> 	
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
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanAgentGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class="cssButton90" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class="cssButton93" TYPE=button onclick="turnPage.lastPage();"> 						
  	</div>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	  <br /><br /><br /><br />
  </form>
</body>
</html>
