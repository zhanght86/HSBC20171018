<script lanaguage="javascript">
function getOpName()
{
	fm.AppntOccupationCodeName.value = "";
	showOneCodeName('OccupationCode','AppntOccupationCode','AppntOccupationCodeName');
	getdetailwork();
}	

function onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName)
{
	showCodeList('occupationcode',[AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240');
	showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	//getdetailwork(); 
}

function onClickUpOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName)
{
	showCodeListKey('occupationcode',[AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240')
	showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	//getdetailwork();
}

</script>

<DIV id="DivLCAppntIndButton" STYLE="display:''">
<!-- Ͷ������Ϣ���� -->
	<Table>
		<TR>
			<TD class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
			</TD>
			<TD class= titleImg>
				Ͷ������Ϣ���ͻ��ţ�<Input class="common"   name=AppntNo id="AppntNo">
				<a href="javascript:void(0)" id="butBack" style="font-weight:normal;" class=button onclick="queryAppntNo();">��  ѯ</a>
				�״�Ͷ���ͻ�������д�ͻ��ţ�
			</TD>
		</TR>
	</Table>
</DIV>

<DIV id="DivLCAppntInd" STYLE="display:''">
	<div class="maxbox">
	<Table  class= common>
		<TR class="common" STYLE="display:none">
			<TD class=title>VIP�ͻ�</TD>
			<TD>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" name="AppntVIPValue" id="AppntVIPValue" class="codeno" readonly="readonly" onclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname"class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR  class= common>        
			<TD  class= title>��</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntLastName id="AppntLastName" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ������|NOTNUlL&LEN<=20"  verifyorder="1">
				<font color="red">*</font>
			</TD>
			<TD  class= title>��</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=AppntFirstName id="AppntFirstName" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ������|NOTNUlL&LEN<=20"  verifyorder="1">
				<font color="red">*</font>
			</TD>
			<TD  class= title>����</TD>
			<TD  class=input >
				<Input class="common wid"  name=AppntName id="AppntName" elementtype=nacessary verify="Ͷ��������|LEN<=20"  verifyorder="1" readonly>
				<font color="red">*</font>
			</TD>
	    </TR>
	    <TR  class= common>        
			<TD  class= title>Ӣ����</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntLastNameEn  id="AppntLastNameEn" verify="Ӣ����|LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>Ӣ����</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=AppntFirstNameEn id="AppntFirstNameEn" verify="Ӣ����|LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>ƴ����</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=AppntLastNamePY id="AppntLastNamePY" verify="ƴ����|LEN<=20"  verifyorder="1">
			</TD>
	    </TR>
	    <TR  class= common>        
			<TD  class= title>ƴ����</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntFirstNamePY id="AppntFirstNamePY" verify="ƴ����|LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>�Ա�</TD>
			<TD nowrap class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=AppntSex id="AppntSex" verify="Ͷ�����Ա�|NOTNUlL&CODE:Sex"  verifyorder="1" onclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName id="AppntSexName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class="coolDatePicker" onblur="checkappntbirthday();getAge();" elementtype=nacessary onClick="laydate({elem: '#AppntBirthday'});" verify="Ͷ���˳�������|NOTNUlL&DATE" verifyorder="1" dateFormat="short" name=AppntBirthday id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				<font color="red">*</font>
			</TD>
	    </TR>
    
		<TR  class= common>			
			<TD  class= title>ϵ�����˵�</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToInsured" id="RelationToInsured" verify="�뱻���˹�ϵ|NOTNULL&CODE:Relation" verifyorder='1' onclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToInsuredName],[0,1]);"><Input class=codename name=RelationToInsuredName id="RelationToInsuredName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD> 
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntIDType" id="AppntIDType" verify="Ͷ����֤������|CODE:IDType" verifyorder="1" onclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName id="AppntIDTypeName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="common wid"  name="AppntIDNo" id="AppntIDNo" elementtype=nacessary verify="Ͷ����֤������|LEN<=20"  verifyorder="1" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getAge();if(LoadFlag=='1')confirmSecondInput(this,'onblur');">
				<font color="red">*</font>
			</TD>

		</TR>
		<TR>
			<TD class=title>֤��������Ч����</TD>
			<TD>
				<input class="common wid" dateFormat="short" name="AppIDPeriodOfValidity" id="AppIDPeriodOfValidity" verify="֤��������Ч��|DATE" verifyorder="1">
			</TD>
			<TD class=title>�Ƿ�ӵ�й���ҽ�ơ����ҽ�Ʊ���</TD>
			<TD CLASS=input>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="AppSocialInsuFlag" id="AppSocialInsuFlag"
            				   CodeData="0|^0|��^1|��"
            				   onClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);"
            				   ondblClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('SocialInsuFlag',[this],[0,1]);">
			</TD>
			<TD class=title>����</TD>
			<TD CLASS=input>
			<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntLanguage" id="AppntLanguage" verify="����|CODE:language" verifyorder="1" onclick="return showCodeList('language',[this,AppntLanguageName],[0,1]);" ondblclick="return showCodeList('language',[this,AppntLanguageName],[0,1]);" onkeyup="return showCodeListKey('language',[this,AppntLanguageName],[0,1]);"><input class=codename name=AppntLanguageName id="AppntLanguageName" readonly=true elementtype=nacessary>
			<font color="red">*</font>
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>Ͷ��������</TD>
			<TD  class= input>
				<input class="common wid" name="AppntAge" id="AppntAge" verify="Ͷ��������|value>0" verifyorder="1" value="" readonly="readonly">
			</TD>
			<TD  class= title>����</TD>
			<TD  class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntNativePlace" id="AppntNativePlace" verify="Ͷ���˹���|CODE:NativePlace"  verifyorder="1" onclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename name=AppntNativePlaceName id="AppntNativePlaceName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>
			<TD  class= title>�������ڵ�</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntPlace  id="AppntPlace">
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>����״��</TD>
			<TD nowrap class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntMarriage" id="AppntMarriage"  verifyorder="1" onclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName id="AppntMarriageName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>  
			<TD class=title style="display:none">��������</TD>
			<TD  class= input style="display:none">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntLicenseType" id="AppntLicenseType" verify="����|CODE:LicenseType"  verifyorder="1" onclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);"><input class=codename name=AppntLicenseTypeName id="AppntLicenseTypeName" readonly=true>
			</TD>
			</TR>
			<TR  class= common>
				<TD  class= title>ͨѶ��ַ</TD>
				<TD  class= input colspan=3 >
					<Input class='common3' elementtype=nacessary name="AppntPostalAddress" id="AppntPostalAddress" verify="Ͷ������ϵ��ַ|NOTNUlL&LEN<=80"  verifyorder="1" >
					<font color="red">*</font>
				</TD>
				<TD  class= title>ͨѶ��ַ��������</TD>
				<TD  class= input>
					<Input class="common wid" name="AppntZipCode" id="AppntZipCode" verify="Ͷ������������|NOTNUlL&zipcode" elementtype=nacessary verifyorder="1" >
					<font color="red">*</font>
				</TD>
			</TR>
			<TR  class= common>
				<TD  class= title>סַ</TD>
				<TD  class= input colspan=3 >
					<Input class='common3' name="AppntHomeAddress" id="AppntHomeAddress" verify="סַ|LEN<=80"  verifyorder="1" >
				</TD>
				<TD  class= title>סַ��������</TD>
				<TD  class= input>
					<Input class="common wid" name="AppntHomeZipCode" id="AppntHomeZipCode" verify="Ͷ����סַ��������|zipcode"  verifyorder="1" >
				</TD>
			</TR>	
			<TR  class= common>        
				<TD  class= title>��ѡ�طõ绰</TD>
				<TD  class= input>
					<Input class="common wid" name="AppntMobile" id="AppntMobile" verify="Ͷ������ѡ�طõ绰|LEN<=15"  verifyorder="1" >
				</TD>         
				<TD  class= title>������ϵ�绰</TD>
				<TD  class= input>
					<Input class="common wid" name="AppntPhone" id="AppntPhone" verify="Ͷ����������ϵ�绰|LEN<=15"  verifyorder="1" >
				</TD>       
				<TD  class= title>��������</TD>
				<TD  class= input>
					<Input class="common wid" name="AppntEMail" id="AppntEMail" MAXLENGTH=40 verify="Ͷ���˵�������|EMAIL&LEN<=40"  verifyorder="1" >
				</TD>  
			</TR>
    		<TR  class= common>
	    		<TD  class= title>������λ</TD>
				<TD  class= input>
					<Input class="common wid" name="AppntGrpName" id="AppntGrpName" verify="Ͷ���˹�����λ|LEN<=60"  verifyorder="1" >
				</TD>
				<TD  class= title>
			        ְҵ
			    </TD>
			    <TD  class= input>
			       <Input class="common wid" name="AppntWorkType" id="AppntWorkType" value="" >
			    </TD>
			  	<TD  class= title>
			        ��ְ
			    </TD>
			    <TD  class= input>
			       <Input class="common wid"  name="AppntPluralityType" id="AppntPluralityType" value="" >
			    </TD>
    		</TR>
    		<TR  class= common>
				<TD  class= title>ְҵ����</TD>
				<TD class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntOccupationCode" id="AppntOccupationCode"  verifyorder="1" onclick= "return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" ondblclick= "return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" onkeyup="return onClickUpOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);"><input class=codename name=AppntOccupationCodeName id="AppntOccupationCodeName" onblur="getOpName();" readonly=true elementtype=nacessary>
					<font color="red">*</font>
				</TD>   
				<TD class=title>ְҵ���</TD>
				<TD class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" readonly name="AppntOccupationType" id="AppntOccupationType" onkeyup="showCodeListKey('OccupationType',[this,AppntOccupationTypeName],[0,1]);"><input class=codename name=AppntOccupationTypeName id="AppntOccupationTypeName" readonly=true>
				</TD> 	
			</TR> 
			<TR  class= common style="display:none">
				<TD  class= title>��ַ����</TD>
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntAddressNo" id="AppntAddressNo" onclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);" ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);"><input class=codename name=AddressNoName id="AddressNoName" readonly=true>
				</TD>  
			</TR>
			<TR class='common' style="display:none">
				<TD class="title">ͨѶ��ַ��</TD>
			</TR>
    		<TR  class= common style="display:none">
				<TD  class= title>ʡ</TD>
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" verify="Ͷ����ʡ��|num&LEN=6"  verifyorder="1" name="AppntProvince" id="AppntProvince" onclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1],null,null,null,1);"><input class=codename name=AppntProvinceName id="AppntProvinceName" onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
				</TD>  
				<TD  class= title>��</TD>
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" verify="Ͷ���˳���|num&LEN=6"  verifyorder="1" name="AppntCity" id="AppntCity" onclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename'],1);"><input class=codename name=AppntCityName id="AppntCityName" onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
				</TD>  
				<TD  class= title>��/��</TD>
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" verify="Ͷ������/��|num&LEN=6"  verifyorder="1" name="AppntDistrict" id="AppntDistrict" onclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');"><input class=codename name=AppntDistrictName id="AppntDistrictName" onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
				</TD>  
			</TR>        
		  <!--
	      <TD  class= title>�������ڵ�</TD>
	      <TD  class= input>
	        <Input class= common name="AppntRgtAddress" verify="Ͷ���˻������ڵ�|LEN<=80" >
	      </TD>
	      <TD  class= title>����</TD>
	      <TD  class= input>
		      <input class="codeno" name="AppntNationality" verify="Ͷ��������|CODE:Nationality" ondblclick="return showCodeList('Nationality',[this,AppntNationalityName],[0,1]);" onkeyup="return showCodeListKey('Nationality',[this,AppntNationalityName],[0,1]);">
		      <input class=codename name=AppntNationalityName readonly=true>
	      </TD>
	      -->
		
		<!--  
		<TD  class= title>��λ����</TD>
		<TD  class= input  >
			<Input class= codeno name="AppntGrpNo"  ondblclick="getCompanyCode();return showCodeListEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);" onkeyup="getCompanyCode();return showCodeListKeyEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);">
			<input class=codename name=AppntGrpNoName readonly=true>
		</TD>        
		<TD  class= title>�Ƿ�����</TD>
		<TD  class= input>
			<Input class="codeno" name="AppntSmokeFlag" verify="Ͷ�����Ƿ�����|CODE:YesNo" ondblclick="return showCodeList('YesNo',[this,AppntSmokeFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,AppntSmokeFlagName],[0,1]);">
			<input class=codename name=AppntSmokeFlagName readonly=true>
		</TD>
		-->
			<TR class=common style="display:none">
				<TD  class= title>����绰</TD>
				<TD  class= input>
					<Input class="common wid" name="AppntFax" id="AppntFax" verify="Ͷ���˴���绰|LEN<=15&PHONE"  verifyorder="1" >
				</TD>           
			</TR>
			<TR  class= common style="display:none">
				<TD  class= title>סլ�绰</TD>
				<TD  class= input>
					<input class="common wid"  name="AppntHomePhone" id="AppntHomePhone" verify="Ͷ����סլ�绰|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
				</TD>
			</TR>        
    <!--TR  class= common>
      <TD  class= title>
        ѧ��
      </TD>
      <TD  class= input>
        <Input class="codeno" name="AppntDegree" verify="Ͷ����ѧ��|CODE:Degree" ondblclick="return showCodeList('Degree',[this,AppntDegreeName],[0,1]);" onkeyup="return showCodeListKey('Degree',[this,AppntDegreeName],[0,1]);">
        <input class=codename name=AppntDegreeName readonly=true>
      </TD>        
      <TD  class= title>
        ��ְ�����֣�
      </TD>
      <TD  class= input>
        <Input class= common name="AppntPluralityType" verify="Ͷ���˼�ְ�����֣�|LEN<=10" >
      </TD>       
    </TR-->       
    <!--TR  class= common>
      <TD  class= title>
        ��ͥ��ַ
      </TD>
      <TD  class= input colspan=3 >
        <Input class= common3 name="AppntHomeAddress" verify="Ͷ���˼�ͥ��ַ|LEN<=80" >
      </TD>
      <TD  class= title>
        ��ͥ��������
      </TD>
      <TD  class= input>
        <Input class= common name="AppntHomeZipCode" verify="Ͷ���˼�ͥ��������|zipcode" >
      </TD>
    </TR-->
    
    <!--TR  class= common>
      <TD  class= title>
        ��ͥ����
      </TD>
      <TD  class= input>
        <Input class= common name="AppntHomeFax" verify="Ͷ���˼�ͥ����|LEN<=15" >
      </TD>
    </TR-->        
    <!--TR  class= common>
      <!--TD  class= title>
        ��λ��ַ
      </TD>
      <TD  class= input colspan=3 >
        <Input class= common3 name="CompanyAddress" verify="Ͷ���˵�λ��ַ|LEN<=80" >
      </TD> 
      <TD  class= title>
        ��λ��������
      </TD>
      <TD  class= input>
        <Input class= common name="AppntGrpZipCode" verify="Ͷ���˵�λ��������|zipcode" >
      </TD>                   
    </TR-->        
    <!--TR  class= common>
      <TD  class= title>
        ��λ����
      </TD>
      <TD  class= input>
        <Input class= common name="AppntGrpFax" verify="��λ����|LEN<=18" >
      </TD>                    
    </TR-->
    <!--TR  class= common>        
      <TD  class= title>
        ��ϵ��ַѡ��
      </TD>
      <TD  class= input>
        <Input class="codeno" name="CheckPostalAddress"  value='3' CodeData="0|^1|��λ��ַ^2|��ͥ��ַ^3|����"  ondblclick="return showCodeListEx('CheckPostalAddress',[this,CheckPostalAddressName],[0,1]);" onkeyup="return showCodeListKeyEx('CheckPostalAddress',[this,CheckPostalAddressName],[0,1]);FillPostalAddress()" onfocus="FillPostalAddress();">  
        <input class=codename name=CheckPostalAddressName readonly=true>        
      </TD>   
      <TD  class= title colspan=2>
        <font color=red>(����д��λ��ַ���ͥ��ַ��ʵ������)</font>
      </TD> 
    </TR-->                   
    
    <!--TR  class= common>
      <TD  class= title>
        ��ϵ�绰
      </TD>
      <TD  class= input>
      <input class= common name="AppntPhone" verify="Ͷ������ϵ�绰|LEN<=18" >
      </TD>
    </TR-->        
	</Table>
	</div>
	<div id="DivLCAccount" style="display:''">
		<Table>
			<TR>
				<TD class=common>
					<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
				</TD>
				<TD class= titleImg>�ɷ���Ϣ</TD>
			</TR>
		</Table>   
		<div id="divLCAccount1" style="display:''">
			<div class="maxbox">
        	<Table class="common">    
	          	<TR class="common">
	          		<TD class="title">���ڽ�����ʽ</TD>
		            <TD>
		            	<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="PayMode" id="PayMode" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:paylocation2"  verifyorder="1" onclick="return showCodeList('paylocation2',[this,PayModeName],[0,1]);" ondblclick="return showCodeList('paylocation2',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKey('paylocation2',[this,PayModeName],[0,1]);"><input class="codename" name="PayModeName" id="PayModeName" readonly="readonly" elementtype=nacessary>
						<font color="red">*</font>
		            </TD>
	          	</TR>
				<TR class='common'>
					<TD class='title'>����ת�ʿ����� </TD>
					<TD class='input'>
						<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" NAME=AppntBankCode id="AppntBankCode" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" onclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1]);" ><input class=codename name=AppntBankCodeName id="AppntBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntAccName id="AppntAccName" VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" > �����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntBankAccNo id="AppntBankAccNo" class="common wid" VALUE="" CLASS=common verifyorder="1" onblur="checkAccNo(fm.AppntBankCode,fm.AppntBankAccNo);" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');"  MAXLENGTH=25 verify="�����˺�|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=AppntBankAccNoName id="AppntBankAccNoName" readonly=true>
					</TD>
				</TR>
				<TR class="common">
					<TD class="title">���ڽ�����ʽ</TD>
					<TD>
						<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SecPayMode" id="SecPayMode" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:paylocation"  verifyorder="1" onclick="return showCodeList('paylocation',[this,SecPayModeName],[0,1]);" ondblclick="return showCodeList('paylocation',[this,SecPayModeName],[0,1]);" onkeyup="return showCodeListKey('paylocation',[this,SecPayModeName],[0,1]);"><input class="codename" name="SecPayModeName" id="SecPayModeName" readonly="readonly" elementtype=nacessary>
						<font color="red">*</font>
					</TD>
					<TD class="title" >�ס������˺�һ��</TD>
					<TD class='title'>
						<input type="checkbox" name='theSameAccount' id="theSameAccount" value="true" onclick="theSameToFirstAccount();">
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >����ת�ʿ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" NAME=SecAppntBankCode id="SecAppntBankCode" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" onclick="return showCodeList('bank',[this,SecAppntBankCodeName],[0,1]);" ondblclick="return showCodeList('bank',[this,SecAppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,SecAppntBankCodeName],[0,1]);" ><input class=codename name=SecAppntBankCodeName id="SecAppntBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=SecAppntAccName id="SecAppntAccName" VALUE="" CLASS="common wid"  MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" >�����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=SecAppntBankAccNo id="SecAppntBankAccNo" class="common wid" VALUE="" CLASS=common verifyorder="1" onblur="checkAccNo(fm.SecAppntBankCode,fm.SecAppntBankAccNo);" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=25 verify="�����˺�|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=aa id="aa" readonly=true>
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >�Զ��潻��־ </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="AutoPayFlag" id="AutoPayFlag"
            				   CodeData="0|^0|����^1|�潻"
            				   onClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
            				   ondblClick="showCodeListEx('ModeSelect_0',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_0',[this],[0,1]);">
					</TD>
					<TD CLASS=title >�Զ�������־ </TD>
					
					<TD CLASS=input COLSPAN=1  >
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="RnewFlag" id="RnewFlag"
            				   CodeData="0|^-2|������^-1|�Զ�����^0|�˹�����"
            				   onClick="showCodeListEx('ModeSelect_3',[this],[0,1]);"
            				   ondblClick="showCodeListEx('ModeSelect_3',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_3',[this],[0,1]);">
					</TD>
					<TD CLASS=title width="109" >�������ͷ�ʽ</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="GetPolMode" id="GetPolMode"
            				   CodeData="0|^0|����������ȡ^1|�ʼĻ�ר��"
            				   onClick="showCodeListEx('ModeSelect_2',[this],[0,1]);"
            				   ondblClick="showCodeListEx('ModeSelect_2',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_2',[this],[0,1]);">
					</TD>
				</TR>
				<tr class=common>
					<TD  class= title>�罻���Ѵ���ʽ</TD>
				    <TD  class= input>
				    	<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class= 'code' name=OutPayFlag id="OutPayFlag"
											   CodeData="0|^1|�˷�^2|�ֽ����ڱ���"
		            				   		   onClick="showCodeListEx('ModeSelect_4',[this],[0,1]);"
		            				   		   ondblClick="showCodeListEx('ModeSelect_4',[this],[0,1]);"
		            				   		   onkeyup="showCodeListKeyEx('ModeSelect_4',[this],[0,1]);">
				    </TD>
				</tr>
	        </Table> 
	        </div> 
      	</div> 
    </div>
</DIV>

<Div  id= "divLCAppnt1" style= "display: none">
<Table  class= common  >
  <TR  class= common>
<!--    <TD  class= title>
      �����ͬ����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=GrpContNo >
    </TD>
    <TD  class= title>
      ��ͬ����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=ContNo >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ӡˢ����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=PrtNo >
    </TD>
    <TD  class= title>
      Ͷ���˿ͻ�����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntNo >
    </TD>-->
  </TR>
  <TR  class= common>
    <TD  class= title>
      Ͷ���˼���
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntGrade id="AppntGrade">
    </TD>
    <td class= title></td>
    <td class= input></td>
    <td class= title></td>
    <td class= input></td>
<!--     <TD  class= title>
      Ͷ��������
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntName >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      Ͷ�����Ա�
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntSex >
    </TD>
    <TD  class= title>
      Ͷ���˳�������
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntBirthday >
    </TD>
  </TR>-->
  <TR  class= common>
    <TD  class= title>
      Ͷ��������
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntType id="AppntType">
    </TD>
<!--     <TD  class= title>
      �ͻ���ַ����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AddressNo >
    </TD>
  </TR>
 <TR  class= common>
    <TD  class= title>
      ֤������
    </TD>
    <TD  class= input>
      <Input class= 'common' name=IDType >
    </TD>
    <TD  class= title>
      ֤������
    </TD>
    <TD  class= input>
      <Input class= 'common' name=IDNo >
    </TD>
  </TR>-->
<!--
  <TR  class= common>
    <TD  class= title>
      �������ڵ�
    </TD>
    <TD  class= input>
      <Input class= 'common' name=RgtAddress >
    </TD>
    <TD  class= title>
      ����״��
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Marriage >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      �������
    </TD>
    <TD  class= input>
      <Input class= 'common' name=MarriageDate >
    </TD>
    <TD  class= title>
      ����״��
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Health >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ���
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Stature >
    </TD>
    <TD  class= title>
      ����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Avoirdupois >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ѧ��
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Degree >
    </TD>
    <TD  class= title>
      ���õȼ�
    </TD>
    <TD  class= input>
      <Input class= 'common' name=CreditGrade >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ���б���
    </TD>
    <TD  class= input>
      <Input class= 'common' name=BankCode >
    </TD>
    <TD  class= title>
      �����ʺ�
    </TD>
    <TD  class= input>
      <Input class= 'common' name=BankAccNo >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      �����ʻ���
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AccName >
    </TD>
    <TD  class= title>
      ��˾����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=JoinCompanyDate >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      �μӹ�������
    </TD>
    <TD  class= input>
      <Input class= 'common' name=StartWorkDate >
    </TD>
    <TD  class= title>
      ְλ
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Position >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Salary >
    </TD>
    <TD  class= title>
      ְҵ���
    </TD>
    <TD  class= input>
      <Input class= 'common' name=OccupationType >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ְҵ����
    </TD>
    <TD  class= input>
      <Input class= 'common' name=OccupationCode >
    </TD>
    <TD  class= title>
      ְҵ�����֣�
    </TD>
    <TD  class= input>
      <Input class= 'common' name=WorkType >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ��ְ�����֣�
    </TD>
    <TD  class= input>
      <Input class= 'common' name=PluralityType >
    </TD>
    <TD  class= title>
      �Ƿ����̱�־
    </TD>
    <TD  class= input>
      <Input class= 'common' name=SmokeFlag >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ����Ա
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Operator >
    </TD>
    -->
<!--    <TD  class= title>
      �������
    </TD>
    <TD  class= input>
      <Input class= 'common' name=ManageCom >
    </TD>-->
  </TR>
  <TR  class= common>
    <TD  class= title>
      �������
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntMakeDate id="AppntMakeDate">
    </TD>
    <TD  class= title>
      ���ʱ��
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntMakeTime id="AppntMakeTime">
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ���һ���޸�����
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyDate id="AppntModifyDate">
    </TD>
    <TD  class= title>
      ���һ���޸�ʱ��
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyTime id="AppntModifyTime">
    </TD>
  </TR>
</Table>
    </Div>
