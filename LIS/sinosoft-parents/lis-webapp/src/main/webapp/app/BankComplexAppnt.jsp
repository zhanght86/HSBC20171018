<script lanaguage="javascript">
function getOpName()
{
	fm.AppntOccupationCodeName.value = "";
	showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
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

<Div id="DivLCAppntIndButton" STYLE="display:''">
	<!-- Ͷ������Ϣ���� -->
	<Table>
		<TR>
			<TD class= common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
			</TD>
			<TD class= titleImg>
				Ͷ������Ϣ���ͻ��ţ�<Input class= common  name=AppntNo id="AppntNo">
				<a href="javascript:void(0)" id="butBack" class=button onclick="queryAppntNo();">��  ѯ</a>
				<!-- <INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppntNo();"> -->
				�״�Ͷ���ͻ�������д�ͻ��ţ�
			</TD>
		</TR>
	</Table>
</Div>
<div class="maxbox">
<Div id="DivLCAppntInd" STYLE="display:''">
	<Table  class= common>
		<TR class="common" style="display:none"> 
			<TD class="title">VIP�ͻ�</TD>
			<TD class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" name="AppntVIPValue" id="AppntVIPValue" class="codeno" readonly="readonly" onclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname" class="codename" readonly="readonly">
			</TD>
		</TR>  
		<TR  class= common>        
			<TD  class= title>����</TD>
			<TD  class=input >
				<Input class="common wid"  name=AppntName id="AppntName" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ��������|NOTNUlL&LEN<=20"  verifyorder="1" oncopy="return false;" oncut="return false">
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntIDType" id="AppntIDType" verify="Ͷ����֤������|CODE:IDType" verifyorder="1"  onclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName id="AppntIDTypeName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntIDNo" id="AppntIDNo" elementtype=nacessary verify="Ͷ����֤������|LEN<=20"  verifyorder="1" oncopy="return false" oncut="return false" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getAge();if(LoadFlag=='1')confirmSecondInput(this,'onblur');">
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title> �Ա�</TD>
			<TD nowrap class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=AppntSex id="AppntSex" verify="Ͷ�����Ա�|NOTNUlL&CODE:Sex"  verifyorder="1" onclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName id="AppntSexName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class="coolDatePicker" elementtype=nacessary onblur="checkappntbirthday();getAge();" onClick="laydate({elem: '#AppntBirthday'});" verify="Ͷ���˳�������|NOTNUlL&DATE"  verifyorder="1" dateFormat="short" name=AppntBirthday id="AppntBirthday"><span class="icon"><a onClick="laydate({elem: '#AppntBirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
			<TD  class= title>Ͷ��������</TD>
			<TD  class= input>
				<input class="common wid" name="AppntAge" id="AppntAge" verify="Ͷ��������|value>0" verifyorder="1" value="" readonly="readonly">
			</TD>   
		</TR>
		<TR  class= common>
			<TD  class= title>ְҵ����</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" verify="Ͷ����ְҵ����|code:OccupationCode" name="AppntOccupationCode" id="AppntOccupationCode" onclick= "return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" ondblclick= "return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" onkeyup="return onClickUpOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);"><input class=codename name=AppntOccupationCodeName id="AppntOccupationCodeName" readonly=true >
			</TD> 
			<TD class=title>ְҵ���</TD>
			<TD class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" readonly name="AppntOccupationType" id="AppntOccupationType" onkeyup="showCodeListKey('OccupationType',[this,AppntOccupationTypeName],[0,1]);"><input class=codename name=AppntOccupationTypeName id="AppntOccupationTypeName" readonly=true >
			</TD> 
			<TD  class= title>
		        ְҵ
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="AppntWorkType" id="AppntWorkType" value="" >
		      </TD>
				</TR>	
		<TR  class= common  style="display:none">
			<TD  class= title>����״�� </TD>
			<TD nowrap class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntMarriage" id="AppntMarriage" verify="Ͷ���˻���״��|CODE:Marriage" onclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName id="AppntMarriageName" readonly=true elementtype=nacessary>
			</TD>          
			<TD  class= title>����</TD>
			<TD  class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntNativePlace" id="AppntNativePlace" verify="Ͷ���˹���|CODE:NativePlace"   onclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename name=AppntNativePlaceName id="AppntNativePlaceName" readonly=true elementtype=nacessary>
			</TD>
     
		</TR>
		<TR  class= common style="display:none"> 
			<TD class=title>��������</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntLicenseType" id="AppntLicenseType" verify="����|CODE:LicenseType"  verifyorder="1" onclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);"><input class=codename name=AppntLicenseTypeName id="AppntLicenseTypeName" readonly=true>
			</TD>
		</TR>
		
		<TR  class= common >
			<TD  class= title>�뱻���˹�ϵ</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToInsured" id="RelationToInsured" verify="�뱻���˹�ϵ|NOTNULL&CODE:Relation" verifyorder='1' onclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToInsuredName],[0,1]);"><Input class=codename name=RelationToInsuredName id="RelationToInsuredName" readonly=true elementtype=nacessary>
			</TD>  
			<TD>֤��������Ч����</TD>
			<TD>
				<input class="common wid" dateFormat="short" name="AppIDPeriodOfValidity" id="AppIDPeriodOfValidity" verify="֤��������Ч��|DATE" verifyorder="1">
			</TD>
        
		</TR>

		<TR  class= common style=display:none> 
			<TD  class= title>��ַ����</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntAddressNo" id="AppntAddressNo" type="hidden"  onclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename name=AddressNoName id="AddressNoName" readonly=true  >
			</TD>  
		</TR>
	
		<TR  class= common style=display:none>
			<TD  class= title>ʡ</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno"  style="display:none;" verify="Ͷ����ʡ��|num&LEN=6"  verifyorder="1" name="AppntProvince" id="AppntProvince" onclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1]);"><input class=codename name=AppntProvinceName id="AppntProvinceName" onblur="getAddressName('province','AppntProvince','AppntProvinceName');"  readonly=true style=display:none >
			</TD>  
			<TD  class= title>��</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno"  style="display:none;" verify="Ͷ���˳���|num&LEN=6"  verifyorder="1" name="AppntCity" id="AppntCity" onclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename']);"><input class=codename name=AppntCityName id="AppntCityName" onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true style=display:none >
			</TD>  
			<TD  class= title> ��/��</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno"  style="display:none;" verify="Ͷ������/��|num&LEN=6"  verifyorder="1" name="AppntDistrict" id="AppntDistrict" onclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');"><input class=codename name=AppntDistrictName id="AppntDistrictName" onblur="getAddressName('district','AppntDistrict','AppntDistrictName');"  readonly=true  style=display:none>
			</TD>  
		</TR>        
	
          <!--TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class= common name="AppntRgtAddress" verify="Ͷ���˻������ڵ�|LEN<=80" >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="codeno" name="AppntNationality" verify="Ͷ��������|CODE:Nationality" ondblclick="return showCodeList('Nationality',[this,AppntNationalityName],[0,1]);" onkeyup="return showCodeListKey('Nationality',[this,AppntNationalityName],[0,1]);">
          <input class=codename name=AppntNationalityName readonly=true>
          </TD-->
		<TR  class= common>
			<TD  class= title>�ʼĵ�ַ</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntPostalAddress" id="AppntPostalAddress" verify="Ͷ������ϵ��ַ|NOTNUlL&LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntZipCode" id="AppntZipCode" verify="Ͷ������������|NOTNUlL&zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>        
          <!--TD  class= title>
            ��λ����
          </TD>
          <TD  class= input  >
            <Input class= codeno name="AppntGrpNo"  ondblclick="getCompanyCode();return showCodeListEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);" onkeyup="getCompanyCode();return showCodeListKeyEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);">
            <input class=codename name=AppntGrpNoName readonly=true>
          </TD-->        
          <!--TD  class= title>
            �Ƿ�����
          </TD>
          <TD  class= input>
            <Input class="codeno" name="AppntSmokeFlag" verify="Ͷ�����Ƿ�����|CODE:YesNo" ondblclick="return showCodeList('YesNo',[this,AppntSmokeFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,AppntSmokeFlagName],[0,1]);">
            <input class=codename name=AppntSmokeFlagName readonly=true>
      </TD-->
    
        
		<TR class=common>
			<TD  class= title  style="display:none">�ƶ��绰</TD>
			<TD  class= input  style="display:none">
				<Input class="common wid" name="AppntMobile" id="AppntMobile" verify="Ͷ�����ƶ��绰|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>         
			<TD  class= title>��ϵ�绰 </TD>
			<TD  class= input>
				<Input class="common wid" name="AppntPhone" id="AppntPhone" verify="Ͷ������ϵ�绰|LEN<=15&PHONE"  verifyorder="1" >
			</TD> 
		</TR>   
               
		<TR  class= common style="display:none">        
			<TD  class= title>����绰</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntFax" id="AppntFax" verify="Ͷ���˴���绰|LEN<=15&PHONE"  verifyorder="1" >
			</TD>           
		</TR>
         
		<TR  class= common  style="display:none">
			<TD  class= title>סլ�绰 </TD>
			<TD  class= input>
				<input class="common wid" name="AppntHomePhone" id="AppntHomePhone" verify="Ͷ����סլ�绰|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>
			<TD  class= title>������λ</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntGrpName" id="AppntGrpName" verify="Ͷ���˹�����λ|LEN<=60"  elementtype=nacessary >
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntEMail" id="AppntEMail" verify="Ͷ���˵�������|EMAIL&LEN<=40"   >
			</TD>  
		</TR>
	</Table>		
    
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
  

  <hr class="line">
	<Div id="DivLCAccount" style="display:''"> 
		<Table>
		<TR>
			<TD class=common>
				<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
			</TD>
			<TD class= titleImg>�ɷ���Ϣ</TD>
		</TR>
		</Table>
		<Div id="divLCAccount1" style="display:''">
			<div class="maxbox1">
			<Table class="common">    
				<TR class="common" style="display:none">
					<TD class="title">���ڽ�����ʽ</TD>
					<TD class="input">
						<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="PayMode" id="PayMode" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:paymode"  onclick="return showCodeList('paymode',[this,FirstPayModeName],[0,1]);" ondblclick="return showCodeList('paymode',[this,FirstPayModeName],[0,1]);" onkeyup="return showCodeListKey('paymode',[this,FirstPayModeName],[0,1]);"><input class="codename" name="FirstPayModeName" id="FirstPayModeName" readonly="readonly" elementtype=nacessary>
					</TD>
				</TR>
				<TR class='common'>
					<TD class='title'>Ͷ���˿�����</TD>
					<TD class='input'>
						<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" name=AppntBankCode id="AppntBankCode" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" onclick="return showCodeList('bank',[this,FirstBankCodeName],[0,1]);" ondblclick="return showCodeList('bank',[this,FirstBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,FirstBankCodeName],[0,1]);" ><input class=codename name=FirstBankCodeName id="FirstBankCodeName"readonly=true>
					</TD>
					<TD CLASS=title >�ʻ�����</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  name=AppntAccName id="AppntAccName" VALUE="" CLASS="common wid"  MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" >�˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  name=AppntBankAccNo id="AppntBankAccNo" CLASS="common wid" VALUE="" CLASS=common onblur="checkAccNo(fm.AppntBankCode,fm.AppntBankAccNo);"  aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=25 verify="�˺�|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=AppntBankAccNoName id="AppntBankAccNoName" readonly=true oncopy="return false;" oncut="return false">
					</TD>
				</TR>
				<TR class="common" style="display:none">
					<TD class="title">���ڽ�����ʽ</TD>
					<TD>
						<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SecPayMode" id="SecPayMode" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:continuepaymode"   onclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);" ondblclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKey('continuepaymode',[this,PayModeName],[0,1]);"><input class="codename" name="PayModeName" id="PayModeName" readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class="title" >�ס������˺�һ��</TD>
					<TD class='title'>
						<input type="checkbox" name='theSameAccount' id="theSameAccount" value="true" onclick="theSameToFirstAccount();">
					</TD>
				</TR>
				<TR CLASS=common style="display:none">
					<TD CLASS=title  >����ת�ʿ�����</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" name=SecAppntBankCode id="SecAppntBankCode" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"   onclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1]);" ><input class=codename name=AppntBankCodeName id="AppntBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input name=SecAppntAccName id="SecAppntAccName" VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20"   >
					</TD>
					<TD CLASS=title width="109" >�����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<input name='SecAppntBankAccNo' id="SecAppntBankAccNo" class="common wid" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" MAXLENGTH=22 verify="�����˺�|LEN<=22" aonfocus="getdetail();"><input type="hidden" class=codename name=aa id="aa" readonly=true>
					</TD>
				</TR>
			</Table>
			</div>  
		</Div> 
    </Div>
</Div>

<Div  id= "divLCAppnt1" style= "display: none">
<Table  class= common >
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
</TR>
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
      <Input class= 'common wid' name=AppntModifyDate id="AppntModifyDate">
    </TD>
  </TR>
</Table>
</Div> 
</div>