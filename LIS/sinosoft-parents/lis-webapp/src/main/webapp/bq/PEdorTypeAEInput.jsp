<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>Ͷ���˱��</title>
    <!-- �����ʵ�ַ -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeAE.jsp";
        }
    </script>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <LINK href="../common/css/Occupation.css" rel=stylesheet type=text/css>
    <!-- �������ýű� -->
    <SCRIPT src="../common/javascript/Occupation.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorTypeAE.js"></script>
    <%@ include file="PEdorTypeAEInit.jsp" %>
</head>
<BODY  onload="initForm();initElementtype();">
 <FORM id=fm name=fm method=post target="fraSubmit">
 <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title >��ȫ�����</TD>
      <TD  class= input >
        <input class="readonly wid" readonly id="EdorAcceptNo" name="EdorAcceptNo">
      </TD>
      <TD class = title > �������� </TD>
      <TD class = input >
        <Input class=codeno  readonly id=EdorType name=EdorType><input class=codename id=EdorTypeName name=EdorTypeName readonly=true>
      </TD>
      <TD class = title > ������ </TD>
      <TD class = input >
        <input class="readonly wid" readonly id=ContNo name=ContNo>
      </TD>
    </TR>
    <TR class=common>
        <TD class = title>������������</TD>
            <TD class = input>
                <input class = "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
        <TD class = title>��Ч����</TD>
          <TD class = input>
            <input class = "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD class= title>

          </TD>
          <TD class= input>
            
          </TD>
    </TR>

  </TABLE>
 </div>
   <TABLE>
    <TR>
      <TD class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDetail);">
      </td>
      <TD class= titleImg>
        ��Ͷ������Ϣ
      </TD>
   </TR>
   </TABLE>
<Div  id= "divDetail" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            �ͻ���
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PCustomerNo name=PCustomerNo >
          </TD>
          <TD  class= title>
            �ͻ�����
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PName name=PName >
          </TD>
          <TD  class= title>
              �Ա�
          </TD>
          <TD  class= input>
                 <Input class="readonly wid" readonly id=PSex name=PSex >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
           ��������
          </TD>
          <TD  class= input>
          <Input class= "coolDatePicker" readonly id=PBirthday name=PBirthday onClick="laydate({elem: '#PBirthday'});" id="PBirthday"><span class="icon"><a onClick="laydate({elem: '#PBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PIDType name=PIDType>
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly id=PIDNo name=PIDNo >
          </TD>
        </TR>
        </table>
</Div>
<TABLE style= "display: none">
    <TR>
      <TD class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLC);">
      </td>
      <TD class= titleImg>
        ���ԭ��
      </TD>
   </TR>
</TABLE>
<Div  id= "divLC" class=maxbox1 style= "display: none">
        <table class="common">
            <tr class="common">
                <td class="title">���ԭ��</td>
                <td class="input"><input type="text" class="codeno" id=EdorReason name="EdorReason" verify="���ԭ��|Code:EdorReason" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('EdorReason',[this,EdorReasonName],[0,1])" onkeyup="showCodeListKey('EdorReason',[this,EdorReasonName],[0,1])"><input type="text" class="codename" id=EdorReasonName name="EdorReasonName" readonly></td>
                <td class="title">&nbsp;</td>
				<td class="input"><input type="text" class="readonly wid" id=CustomerNo name="CustomerNo" readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
</Div>
<Div id = "divdeath" style = "display: none">
    <table class = common>
        <tr class = common>
            <td class = title>
          Ͷ��������ʱ��
      </td>
      <td class = input>
          <input class="coolDatePicker" elementtype=nacessary name="deathdate" onchange="compareTwoDate2() "; onClick="laydate({elem: '#deathdate'});" id="deathdate"><span class="icon"><a onClick="laydate({elem: '#deathdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </td>
      <td class="title">&nbsp;</td>
	  <td class="input"></td>
      <td class="title">&nbsp;</td>
      <td class="input">&nbsp;</td>
    </tr>
  </table>
</Div>
<DIV id=DivLCAppntIndButton STYLE="display:''">
<!-- Ͷ������Ϣ���� -->
<table>
<tr>
<td class="common">
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
Ͷ������Ϣ(�ͻ���)��<Input class= common  id=AppntNo name=AppntNo >
<INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppntNo();">
���״�Ͷ���ͻ�������д�ͻ��ţ�
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd class=maxbox STYLE="display:''">
 <table  class= common>
 	<TR  class= common>
 	 	<TD  class= title>��</TD>
        <TD  class= input>
            <Input class="common wid" id=LastName name=LastName elementtype=nacessary verify="��|notnull&len<=120">
        </TD>
        <TD  class= title>��</TD>
        <TD  class= input>
            <Input class="common wid" id=FirstName name=FirstName elementtype=nacessary verify="��|notnull&len<=120">
        </TD>
        <TD  class= title>����</TD>
        <TD  class= input>
            <Input class=readonly id=AppntName name=AppntName readonly=true>
        </TD>
 	</TR>
    <TR  class= common>
 	 	<TD  class= title>Ӣ����</TD>
        <TD  class= input>
            <Input class="common wid" id=LastNameEn name=LastNameEn>
        </TD>
        <TD  class= title> Ӣ����</TD>
        <TD  class= input>
            <Input class="common wid" id=FirstNameEn name=FirstNameEn>
        </TD>
        <TD  class= title> ƴ����</TD>
        <TD  class= input>
            <Input class="common wid" id=LastNamePY name=LastNamePY>
        </TD>
 	</TR>
    <TR  class= common>
        <TD  class= title>ƴ����</TD>
        <TD  class= input>
            <Input class="common wid" id=FirstNamePY name=FirstNamePY>
        </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntIDType name="AppntIDType"  verify="Ͷ����֤������|notnull&code:IDType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename id=AppntIDTypeName name=AppntIDTypeName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <input type="text" class="coolConfirmBox wid" id=AppntIDNo name="AppntIDNo" elementtype="nacessary" verify="Ͷ����֤������|notnull&LEN<=20" onblur="checkidtype(); getBirthdaySexByIDNo(this.value); showOneCodeName('Sex','AppntSex','AppntSexName');">
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            �Ա�
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntSex name=AppntSex verify="Ͷ�����Ա�|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename id=AppntSexName name=AppntSexName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          <input class="coolDatePicker" dateFormat="short" elementtype=nacessary onblur="checkappntbirthday();getAge();" elementtype=nacessary name="AppntBirthday" verify="Ͷ���˳�������|NOTNUlL&DATE" onClick="laydate({elem: '#AppntBirthday'});" id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntMarriage name="AppntMarriage" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename id=AppntMarriageName name=AppntMarriageName readonly=true >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntLanguage name="AppntLanguage"  verify="����|CODE:language" verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('language',[this,AppntLanguageName],[0,1]);" onkeyup="return showCodeListKey('language',[this,AppntLanguageName],[0,1]);"><input class=codename id=AppntLanguageName name=AppntLanguageName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            
          </TD>
          <TD  class= input>
          
          </TD>
          <TD  class= title>
            
          </TD>
          <TD  class= input>
           
          </TD>
        </TR>

        <TR  class= common>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="codeno" id=AppntNativePlace name="AppntNativePlace" verify="Ͷ���˹���|code:NativePlace" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename id=AppntNativePlaceName name=AppntNativePlaceName readonly=true>
          </TD>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class="common wid" id=AppntRgtAddress name="AppntRgtAddress" verify="Ͷ���˻������ڵ�|len<=80" >
          </TD>
         <TD  class= title>
		        ְҵ
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" id=AppntWorkType name="AppntWorkType"  value="" >
		      </TD>
        <!--  <TD  class= title>
          	����
          </TD>
          <TD  class= input>
          <input class="codeno" id= name="AppntNationality" verify="Ͷ��������|code:Nationality" ondblclick="return showCodeList('Nationality',[this,AppntNationalityName],[0,1]);" onkeyup="return showCodeListKey('Nationality',[this,AppntNationalityName],[0,1]);"><input class=codename id= name=AppntNationalityName readonly=true>
          </TD>
          -->
        </TR>

        <TR  class= common>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD  class= input>
            <Input class=codeno id=AppntOccupationCode name=AppntOccupationCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
            ondblclick="return showCodeList('OccupationCode',[this,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240');"
            ><input class=codename id=AppntOccupationCodeName name=AppntOccupationCodeName readonly=true>
          </TD>
          <TD  class= title>
            ְҵ���
          </TD>
          <TD  class= input>
            <Input class=codeno id=AppntOccupationType name=AppntOccupationType ><input class=codename id=AppntOccupationTypeName name=AppntOccupationTypeName readonly=true>
          </TD>
         <TD  class= title>
            ��ְ
          </TD>
          <TD  class= input>
            <Input class="common wid" id=PluralityType name=PluralityType >
          </TD>
         <!-- <TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AppntSmokeFlag name="AppntSmokeFlag" verify="Ͷ�����Ƿ�����|code:YesNo" ondblclick="return showCodeList('YesNo',[this,AppntSmokeFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,AppntSmokeFlagName],[0,1]);"><input class=codename id=AppntSmokeFlagName name=AppntSmokeFlagName readonly=true>
          </TD>
         -->
        </TR>
          <!--      <TR>
          <TD  class= title>
            ��ַ����
          </TD>
          <TD  class= input>
            <Input class="codeno" id=AddressNo name="AddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename id=AddressNoName name=AddressNoName readonly=true>
          </TD>
            <TD class = title>

            </TD>
            <TD class = input>

            </TD>
             <TD  class= title>

          </TD>
          <TD  class= input>

          </TD>
                </TR>
                -->
<!--
        <tr class='common'>
          <td class="title">
            ͨѶ��ַ��
          </td>
         <TD  class= input>
            <Input class="codeno" verify="Ͷ����ʡ��|len=6"   id= name="AppntProvince"  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,AppntProvinceName],[0,1]);"><input class=codename id= name=AppntProvinceName readonly=true elementtype=nacessary>
          </TD>
        </tr>

        <TR  class= common>
          <TD  class= title>
            ʡ
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="Ͷ����ʡ��|len=6"   id= name="AppntProvince"  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,AppntProvinceName],[0,1]);"><input class=codename id= name=AppntProvinceName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            ��
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="Ͷ���˳���|len=6"   id= name="AppntCity"  ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('City',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);"><input class=codename id= name=AppntCityName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            ��/��
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="Ͷ������/��|len=6"   id= name="AppntDistrict"  ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename']);" onkeyup="return showCodeListKey('District',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename']);"><input class=codename id= name=AppntDistrictName readonly=true elementtype=nacessary>
          </TD>
        </TR>
-->
        <TR  class= common>
          <TD  class= title>
            ͨѶ��ַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "common3" id=PostalAddress name="PostalAddress" verify="Ͷ����ͨѶ��ַ|notnull&len<=80"   elementtype=nacessary>
          </TD>
          <TD  class= title>
            ͨѶ��ַ��������
          </TD>
          <TD  class= input>
            <Input class= "common wid"  id=ZipCode name="ZipCode" verify="Ͷ����ͨѶ��ַ��������|zipcode&notnull&len=6" elementtype=nacessary  >
          </TD>

        </TR>
        <TR  class= common>
          <TD  class= title>
            סַ
          </TD>
          <TD  class= input colspan=3 >
            <Input class= "common3" id=HomeAddress name="HomeAddress" verify="Ͷ����סַ|len<=80"   >
          </TD>
          <TD  class= title>
            סַ��������
          </TD>
          <TD  class= input>
            <Input class= "common wid"  id=HomeZipCode name="HomeZipCode" verify="Ͷ����סַ��������|zipcode&len=6"  >
          </TD>

        </TR>
        <tr class=common>
          <TD  class= title>
            ��ѡ�طõ绰(��ϵ�绰һ)           
          </TD>
          <TD  class= input>
            <Input class="common wid"  id=Mobile name="Mobile" verify="��ѡ�طõ绰(��ϵ�绰һ)|TEL&len<=15">
          </TD>
          <TD  class= title>
            ������ϵ�绰(��ϵ�绰��)
          </TD>
          <TD  class= input>
            <Input class="common wid"  id=GrpPhone name="GrpPhone" verify="������ϵ�绰(��ϵ�绰��)|TEL&len<=18"   >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= common>
            <Input class="common wid"  id=EMail name="EMail" verify="Ͷ���˵�������|EMAIL&LEN<=40"  >
          </TD>
     <!--     <TD  class= title>
            ����绰
          </TD>
          <TD  class= input>
            <Input class="common wid"  id= name="Fax" verify="����|len<=15"   >
          </TD>
      -->
        </tr>
         <TR  class= common>
          <TD  class= title  >
            ������λ
          </TD>
          <TD  class=input colspan=3 >
            <Input class=common3  id=GrpName name="GrpName" verify="Ͷ���˹�����λ|notnull&len<=80"   elementtype=nacessary >
          </TD>
       	<TD  class= title>ϵ�����˵�</TD>
			  <TD  class= input>
			 	<Input class="codeno" id=RelationToInsured name="RelationToInsured" verify="�뱻���˹�ϵ|NOTNULL&CODE:Relation" verifyorder='1' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToInsuredName],[0,1]);"><Input class=codename id=RelationToInsuredName name=RelationToInsuredName readonly=true elementtype=nacessary>
		   	</TD>           
        </TR>
    <!--    <TR  class= common>
          <TD  class= title>
            סլ�绰
          </TD>
          <TD  class= common>
          <input class="common wid"  id= name="HomePhone" verify="Ͷ���˼�ͥ�绰|len<=18" >
          </TD>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= common>
            <Input class=common  id= name="GrpName" verify="Ͷ���˹�����λ|notnull&len<=60"   elementtype=nacessary >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= common>
            <Input class="common wid"  id= name="EMail" verify="Ͷ���˵�������|EMAIL&LEN<=40"  >
          </TD>
        </TR>
       --> 
          <TR class = common >
          <TD  class= title >��������</TD>
          <TD  class= input >
                <Input class="codeno" id=GetBankCode name="GetBankCode" verify="������|CODE:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('bank',[this, BankName], [0, 1]);"onkeyup="showCodeListKey('bank', [this, BankName], [0, 1]);"><input class=codename id=BankName name=BankName readonly=true>
          </TD>
          <TD  class= title >
          �����ʻ�
          </TD>
          <TD  class= input >
              <input type="text" class="coolConfirmBox wid" id=GetBankAccNo name="GetBankAccNo">
            </TD>
           <TD  class= title >
          ��   ��
          </TD>
          <TD  class= input >
                 <Input class="common wid"  id=GetAccName name="GetAccName">
          </TD>
        </TR>
      </table>
    </Div>
<TABLE>
    <TR>
        <TD class=common>
            <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsured);">
        </TD>
        <TD class = titleImg>
            ��ر�������Ϣ
        </TD>
    </TR>
</TABLE>
<Div id = "divInsured" style= "display: ''">
    <TABLE class= common>
        <TR class= common>
            <TD text-align:left colSpan = 1>
                <span id = "spanInsuredGrid"></span>
            </TD>
        </TR>
    </TABLE>
</Div>
<Div id= "divCustomer" style= "display: ">
<TABLE>
    <TR>
      <TD class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <TD class= titleImg>
        ��Ͷ���˽�����֪
      </TD>
   </TR>
   </TABLE>

<Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanImpartGrid" >
                    </span>
                </td>
            </tr>
        </table>
</Div>
</Div>

<br>

<Div  id= "divLRInfo" style= "display: none">
      <table  class= common>
        <TR  class = common>
            <TD  class= title> ������������ </TD>
            <TD  class= input ><input class="common wid" type="text" id=LostTimes name="LostTimes"></TD>
			<TD  class= title></TD>
            <TD  class= input></TD>
			<TD  class= title></TD>
            <TD  class= input></TD>
            <input type=hidden id="TrueLostTimes" name="TrueLostTimes">
        </TR>
     </table>
</Div>
 
<Div id="divEdorquery" style="display: ''">
     <Input class= cssButton type=Button value=" �� �� " onclick="edorTypeAESave()">
   <Input class= cssButton type=Button value=" �� �� " onclick="resetForm()">
   <Input type=Button class= cssButton value=" �� �� " onclick="returnParent()">
   <Input class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();">
</Div>
     <input type=hidden id="ContType" name="ContType">
     <input type=hidden id="EdorNo" name="EdorNo">
     <input type=hidden id="addrFlag" name="addrFlag">
     <input type=hidden id="AddressNo" name="AddressNo">
     <input type=hidden id="fmtransact" name="fmtransact">

     </FORM>
	 <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</BODY>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";

</script>
</HTML>
