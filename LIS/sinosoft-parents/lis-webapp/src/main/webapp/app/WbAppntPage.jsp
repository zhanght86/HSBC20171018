<script lanaguage="javascript">
function getOpName()
{
	fm.AppntOccupationCodeName.value = "";
	showOneCodeNameRefresh('OccupationCode','AppntOccupationCode','AppntOccupationCodeName');
	getdetailwork();
}	

function onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName)
{
	showCodeList('occupationcode',[AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240');
	//showOneCodeNameRefresh('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	//getdetailwork();
}

function onClickUpOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName)
{
	showCodeListKey('occupationcode',[AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240')
	//showOneCodeNameRefresh('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	//getdetailwork();
}

</script>

<DIV id=DivLCAppntIndButton STYLE="display:">
<!-- Ͷ������Ϣ���� -->
	<Table>
		<TR>
			<TD class="common">
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
			</TD>
			<TD class= titleImg>
				Ͷ������Ϣ���ͻ��ţ�<Input class="common"  name=AppntNo id="AppntNo" >
				<INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppntNo();">
				�״�Ͷ���ͻ�������д�ͻ��ţ�
			</TD>
		</TR>
	</Table>
    
</DIV>

<DIV id=DivLCAppntInd class="maxbox" STYLE="display:">
    
	<Table  class= common>
		<TR class="common" STYLE="display:none">
			<TD class="title">VIP�ͻ�</TD>
			<TD class="input">
				<input type="text" name="AppntVIPValue" id="AppntVIPValue" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " class="codeno" readonly="readonly" onclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname" class="codename" readonly="readonly">
			</TD>
		</TR>
	    <TR  class= common>        
			<TD  class= title>����</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntName id="AppntName"  onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ��������|NOTNUlL&LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>�Ա�</TD>
			<TD nowrap class= input>
				<Input class="codeno" name=AppntSex id="AppntSex" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="Ͷ�����Ա�|NOTNUlL&CODE:Sex"  verifyorder="1" onclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName id="AppntSexName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" elementtype=nacessary onblur="checkappntbirthday();getAge();" elementtype=nacessary name="AppntBirthday" id="AppntBirthday" verify="Ͷ���˳�������|NOTNUlL&DATE"  verifyorder="1" >
			</TD>
	    </TR>
    
		<TR  class= common>			
			<TD  class= title>ϵ�����˵�</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntRelationToInsured" id="AppntRelationToInsured" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="�뱻���˹�ϵ|CODE:Relation" verifyorder='1' onclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToInsuredName],[0,1]);"><Input class=codename name=RelationToInsuredName id="RelationToInsuredName" readonly=true elementtype=nacessary>
			</TD> 
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntIDType" id="AppntIDType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  verify="Ͷ����֤������|CODE:IDType" verifyorder="1" onclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName id="AppntIDTypeName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntIDNo" id="AppntIDNo" elementtype=nacessary verify="Ͷ����֤������|LEN<=20"  verifyorder="1" onblur="checkidtype();if(LoadFlag=='1')confirmSecondInput(this,'onblur');">
			</TD>

		</TR>
		<TR  class= common>
			<TD  class= title>��Ч����</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntIDEndDate" id="AppntIDEndDate" >
			</TD>
			<TD  class= title>Ͷ��������</TD>
			<TD  class= input>
				<input class="common wid" name="AppntAge" id="AppntAge" verify="Ͷ��������|value>0" verifyorder="1" value="" readonly="readonly">
			</TD>
			<TD  class= title>����</TD>
			<TD  class="input">
				<input class="codeno" name="AppntNativePlace" id="AppntNativePlace" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="Ͷ���˹���|CODE:NativePlace"  verifyorder="1" onclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename name=AppntNativePlaceName id="AppntNativePlaceName" readonly=true elementtype=nacessary>
			</TD>			
		</TR>
		<TR  class= common>
			<TD  class= title>�������ڵ�</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntRgtAddress id="AppntRgtAddress"  >
			</TD>
			<TD  class= title>����״��</TD>
			<TD nowrap class= input>
				<Input class="codeno" name="AppntMarriage" id="AppntMarriage" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="Ͷ���˻���״��|CODE:Marriage"  verifyorder="1" onclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName id="AppntMarriageName" readonly=true elementtype=nacessary>
			</TD>  
			<TD class=title style="display:none">��������</TD>
			<TD  class= input style="display:none">
				<input class="codeno" name="AppntLicenseType" id="AppntLicenseType" verify="����|CODE:LicenseType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  verifyorder="1" onclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);"><input class=codename name=AppntLicenseTypeName id="AppntLicenseTypeName" readonly=true>
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>ͨѶ��ַ</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntPostalAddress" id="AppntPostalAddress" verify="Ͷ������ϵ��ַ|LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>ͨѶ��ַ��������</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntZipCode" id="AppntZipCode" verify="Ͷ������������|zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>סַ</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntHomeAddress" id="AppntHomeAddress" verify="סַ|LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>סַ��������</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntHomeZipCode" id="AppntHomeZipCode" verify="Ͷ����סַ��������|zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>	
		<TR  class= common>        
			<TD  class= title>�ֻ�</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntPhone" id="AppntPhone" verify="Ͷ������ѡ�طõ绰|LEN<=15"  verifyorder="1" aelementtype=nacessary >
			</TD>         
			<TD  class= title>�̶��绰</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntPhone2" id="AppntPhone2" verify="Ͷ����������ϵ�绰|LEN<=15"  verifyorder="1" >
			</TD>       
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntEMail" id="AppntEMail" MAXLENGTH=40 verify="Ͷ���˵�������|LEN<=40"  verifyorder="1" >
			</TD>  
		</TR>
    <TR  class= common>
    		<TD  class= title>������λ</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntGrpName" id="AppntGrpName" verify="Ͷ���˹�����λ|LEN<=60"  verifyorder="1" elementtype=nacessary >
			</TD>
			<TD  class= title>�Ƿ�ӵ�й���ҽ�ơ����ҽ�Ʊ���</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntSocialInsuFlag" id="AppntSocialInsuFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="�Ƿ�ӵ�й���ҽ�ơ����ҽ�Ʊ���|CODE:ibrmsflag" verifyorder='1' onclick="return showCodeList('ibrmsflag', [this,AppntSocialInsuFlagName],[0,1]);" ondblclick="return showCodeList('ibrmsflag', [this,AppntSocialInsuFlagName],[0,1]);" onkeyup="return showCodeListKey('ibrmsflag', [this,AppntSocialInsuFlagName],[0,1]);"><Input class=codename name=AppntSocialInsuFlagName id="AppntSocialInsuFlagName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>
		        ְҵ
		      </TD>
		      <TD  class= input>
		        <Input class= "common wid" name="AppntWorkType" id="AppntWorkType"  value="" >
		      </TD>		  	
    </TR>
    <TR  class= common>
    	<TD  class= title>
		        ��ְ
		      </TD>
		      <TD  class= input>
		        <Input class= "common wid" name="AppntPluralityType" id="AppntPluralityType"  value="" >
		      </TD>
		<TD  class= title>ְҵ����</TD>
			<TD class= input>
				<Input class="codeno" name="AppntOccupationCode" id="AppntOccupationCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" ondblclick= "return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" onkeyup="return onClickUpOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);"><input class=codename name=AppntOccupationCodeName id="AppntOccupationCodeName" readonly=true elementtype=nacessary>
			</TD>   
			<TD class=title>ְҵ���</TD>
			<TD class= input>
				<Input class="codeno" readonly name="AppntOccupationType" id="AppntOccupationType" onkeyup="showCodeListKey('OccupationType',[this,AppntOccupationTypeName],[0,1]);"><input class=codename name=AppntOccupationTypeName id="AppntOccupationTypeName" readonly=true>
			</TD> 	
		</TR> 
		<TR  class= common style="display:none">
			<TD  class= title>��ַ����</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntAddressNo" id="AppntAddressNo" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);"><input class=codename name=AddressNoName id="AddressNoName" readonly=true>
			</TD>  
		</TR>
		<TR class='common' style="display:none">
			<TD class="title">ͨѶ��ַ��</TD>
		</TR>
    
		<TR  class= common style="display:none">
			<TD  class= title>ʡ</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ����ʡ��|num&LEN=6"  verifyorder="1" name="AppntProvince" id="AppntProvince" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1],null,null,null,1);"><input class=codename name=AppntProvinceName id="AppntProvinceName" onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ���˳���|num&LEN=6"  verifyorder="1" name="AppntCity" id="AppntCity" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename'],1);"><input class=codename name=AppntCityName id="AppntCityName"  onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��/��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ������/��|num&LEN=6"  verifyorder="1" name="AppntDistrict" id="AppntDistrict" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');"><input class=codename name=AppntDistrictName id="AppntDistrictName" onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
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
				<Input class= "common wid" name="AppntFax" id="AppntFax" verify="Ͷ���˴���绰|LEN<=15&PHONE"  verifyorder="1" >
			</TD>           
		</TR>
		<TR  class= common style="display:none">
			<TD  class= title>סլ�绰</TD>
			<TD  class= input>
				<input class= "common wid" name="AppntHomePhone" id="AppntHomePhone" verify="Ͷ����סլ�绰|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
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
	<div id="DivLCAccount" style="display:">
		<Table>
			<TR>
				<TD class=common>
					<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
				</TD>
				<TD class= titleImg>�ɷ���Ϣ</TD>
			</TR>
		</Table>   
		<div id="divLCAccount1" class="maxbox" style="display:''">
        	<Table class="common">    
	          	<TR class="common">
	          		<TD class="title">���ڽ�����ʽ</TD>
		            <TD class="input">
		            	<input class="codeno" name="NewPayMode" id="NewPayMode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="���ڽ��ѷ�ʽ|CODE:paylocation2"  verifyorder="1" onclick="return showCodeList('paylocation2',[this,NewPayModeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('paylocation2',[this,NewPayModeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('paylocation2',[this,NewPayModeName],[0,1],null,null,null,1);"><input class="codename" name="NewPayModeName" id="NewPayModeName" readonly="readonly" elementtype=nacessary>
		            </TD>
	          	</TR>
				<TR class='common'>
					<TD class='title'>����ת�ʿ����� </TD>
					<TD class='input'>
						<input  NAME=NewBankCode id="NewBankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" onclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1],null,null,null,1);" ><input class=codename name=AppntBankCodeName id="AppntBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=NewAccName id="NewAccName" VALUE="" CLASS="common wid" MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" > �����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=NewBankAccNo id="NewBankAccNo" class="common wid" VALUE="" verifyorder="1" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');"  MAXLENGTH=25 verify="�����˺�|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=AppntBankAccNoName id="AppntBankAccNoName" readonly=true>
					</TD>
				</TR>
				<TR class="common">
					<TD class="title">���ڽ�����ʽ</TD>
					<TD>
						<input class="codeno" name="PayLocation" id="PayLocation" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="���ڽ��ѷ�ʽ|CODE:paylocation"  verifyorder="1" onclick="return showCodeList('paylocation',[this,PayLocationName],[0,1],null,null,null,1);" ondblclick="return showCodeList('paylocation',[this,PayLocationName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('paylocation',[this,PayLocationName],[0,1],null,null,null,1);"><input class="codename" name="PayLocationName" id="PayLocationName" readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class="title" >�ס������˺�һ��</TD>
					<TD class='input'>
						<input type="checkbox" name='theSameAccount' id="theSameAccount" value="true" onclick="theSameToFirstAccount();">
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >����ת�ʿ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=BankCode id="BankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" onclick="return showCodeList('bank',[this,ReNBankCodeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('bank',[this,ReNBankCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('bank',[this,ReNBankCodeName],[0,1],null,null,null,1);" ><input class=codename name=ReNBankCodeName id="ReNBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=AccName id="AccName" VALUE="" CLASS="common wid" MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" >�����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=BankAccNo id="BankAccNo" class="common wid" VALUE="" CLASS=common verifyorder="1" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,ReNBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,ReNBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=25 verify="�����˺�|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=ReNBankAccNoName readonly=true>
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >�Զ��潻��־ </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="AutoPayFlag" id="AutoPayFlag" verify="�Զ��潻��־|CODE:autopayflag"  verifyorder="1" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onclick="return showCodeList('autopayflag',[this],[0,1],null,null,null,1);"
            				   ondblClick="return showCodeList('autopayflag',[this],[0,1],null,null,null,1);"
            				   onkeyup="return showCodeListKey('autopayflag',[this],[0,1],null,null,null,1);">	   
					</TD>
					<TD CLASS=title >�Զ�������־ </TD>
					
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="RnewFlag" id="RnewFlag" verify="�Զ�������־|CODE:rnewflag"  verifyorder="1" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"
                        onclick="return showCodeList('rnewflag',[this],[0,1],null,null,null,1);"
            				   ondblClick="return showCodeList('rnewflag',[this],[0,1],null,null,null,1);"
            				   onkeyup="return showCodeListKey('rnewflag',[this],[0,1],null,null,null,1);">
					</TD>
					<TD CLASS=title width="109" >�������ͷ�ʽ</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="GetPolMode" id="GetPolMode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onclick="showCodeListEx('ModeSelect_2',[this],[0,1],null,null,null,1);" 
            				   CodeData="0|^0|����������ȡ^1|�ʼĻ�ר��"
            				   ondblClick="showCodeListEx('ModeSelect_2',[this],[0,1],null,null,null,1);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_2',[this],[0,1],null,null,null,1);">
					</TD>
				</TR>
				<tr class=common>
					<TD  class= title>�罻���Ѵ���ʽ</TD>
				    <TD  class= input>
				    	<Input class= 'code' name=OutPayFlag id="OutPayFlag"  verify="�罻���Ѵ���ʽ|CODE:outpayflag" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"  verifyorder="1" onclick="return showCodeList('outpayflag',[this],[0,1],null,null,null,1);"
		            				   		   ondblClick="return showCodeList('outpayflag',[this],[0,1],null,null,null,1);"
		            				   		   onkeyup="return showCodeListKey('outpayflag',[this],[0,1],null,null,null,1);">
				    </TD>
				     <TD CLASS=title>
				      ���ѷ�ʽ
				    </TD>
				    <TD CLASS=input COLSPAN=1>
				      <Input NAME=PayIntv id="PayIntv" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" VALUE="" CLASS=code verify="���ѷ�ʽ|CODE:dspayintv"  verifyorder="1" onclick="return showCodeList('dspayintv',[this],[0],null,null,null,1);" ondblClick="return showCodeList('dspayintv',[this],[0],null,null,null,1);" onkeyup="return showCodeListKey('dspayintv',[this],[0],null,null,null,1);" verify="���ѷ�ʽ|notnull" >
				    </TD>
				</tr>
	        </Table>  
      	</div> 
    </div>
</DIV>

<Div  id= "divLCAppnt1" style= "display: none">
<Table  class= common align='center' >
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
      <Input class= 'common wid' name=AppntGrade id="AppntGrade" >
    </TD>
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
      <Input class= 'common wid' name=AppntType id="AppntType" >
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
      <Input class= 'common' name=AppntMakeDate >
    </TD>
    <TD  class= title>
      ���ʱ��
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntMakeTime >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ���һ���޸�����
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyDate >
    </TD>
    <TD  class= title>
      ���һ���޸�ʱ��
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyTime >
    </TD>
  </TR>
</Table>
    </Div>
