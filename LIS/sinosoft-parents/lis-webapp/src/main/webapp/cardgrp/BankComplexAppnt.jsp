<DIV id=DivLCAppntIndButton STYLE="display:''">
<!-- Ͷ������Ϣ���� -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
Ͷ������Ϣ���ͻ��ţ�<Input class= common  name=AppntNo >
<INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppntNo();">
�״�Ͷ���ͻ�������д�ͻ��ţ�
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display:''">
 <table  class= common>
         <tr class="common" style="display:none"> 
          <td class="common">VIP�ͻ�</td>
          <td>
            <input type="text" name="AppntVIPValue" class="codeno" readonly="readonly" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" class="codename" readonly="readonly">
          </td>
        </tr>  
        <TR  class= common>        
          <TD  class= title>
            ����
          </TD>
          <TD  class=input >
            <Input class=common name=AppntName  onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ��������|NOTNUlL&LEN<=20"  verifyorder="1">
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="codeno" name="AppntIDType"  verify="Ͷ����֤������|CODE:IDType" verifyorder="1" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class= common name="AppntIDNo" elementtype=nacessary verify="Ͷ����֤������|LEN<=20"  verifyorder="1" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getAge();if(LoadFlag=='1')confirmSecondInput(this,'onblur');">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            �Ա�
          </TD>
          <TD nowrap class= input>
            <Input class="codeno" name=AppntSex verify="Ͷ�����Ա�|NOTNUlL&CODE:Sex"  verifyorder="1" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <input class="common" dateFormat="short" elementtype=nacessary onblur="checkappntbirthday();getAge();" elementtype=nacessary name="AppntBirthday" verify="Ͷ���˳�������|NOTNUlL&DATE"  verifyorder="1" >
          </TD>
          <TD  class= title>
            ְҵ����
          </TD>
          <TD nowrap  class= input>
            <Input class="codeno" name="AppntOccupationCode" verify="Ͷ����ְҵ����|NOTNUlL&CODE:OccupationCode"  verifyorder="1"  ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'240');" onfocus="getdetailwork();"><input class=codename name=AppntOccupationCodeName readonly=true elementtype=nacessary>
            <Input type="hidden" name="AppntOccupationType">
          </TD>    
          
        </TR>
        
        <TR  class= common  style="display:none">
          <TD  class= title>
            ����״�� 
          </TD>
          <TD nowrap class= input>
            <Input class="codeno" name="AppntMarriage" verify="Ͷ���˻���״��|NOTNUlL&CODE:Marriage" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName readonly=true elementtype=nacessary>
          </TD>          
          <TD  class= title>
            ����
          </TD>
          <TD  class="input">
          <input class="codeno" name="AppntNativePlace" verify="Ͷ���˹���|NOTNUlL&CODE:NativePlace"  verifyorder="1" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename name=AppntNativePlaceName readonly=true elementtype=nacessary>
          </TD>
           <TD  class= title>
            Ͷ��������  
          </TD>
          <TD  class= input>
            <input class="common" name="AppntAge" verify="Ͷ��������|value>0" verifyorder="1" value="" readonly="readonly">
          </TD>      
        </TR>
        
        <TR  class= common style="display:none"> 
          <td class=title>
            ��������
          </td>
          <td  class= input>
            <input class="codeno" name="AppntLicenseType" verify="����|CODE:LicenseType"  verifyorder="1" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);"><input class=codename name=AppntLicenseTypeName readonly=true>
          </td>
        </TR>
        
        <TR  class= common >
         
          <TD  class= title>
            ��ַ����
          </TD>
          <TD  class= input>
            <Input class="codeno" name="AppntAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename name=AddressNoName readonly=true>
          </TD>  
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ʡ
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="Ͷ����ʡ��|NOTNUlL&num&LEN=2"  verifyorder="1" name="AppntProvince"  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,AppntProvinceName],[0,1]);"><input class=codename name=AppntProvinceName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            ��
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="Ͷ���˳���|NOTNUlL&num&LEN=4"  verifyorder="1" name="AppntCity"  ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('City',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);"><input class=codename name=AppntCityName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            ��/��
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="Ͷ������/��|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntDistrict"  ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('District',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');"><input class=codename name=AppntDistrictName readonly=true elementtype=nacessary>
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
          <TD  class= title>
            �ֵ�
          </TD>
          <TD  class= input colspan=3 >
            <Input class='common3' elementtype=nacessary name="AppntPostalAddress" verify="Ͷ������ϵ��ַ|NOTNUlL&LEN<=80"  verifyorder="1" >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class='common' name="AppntZipCode" verify="Ͷ������������|NOTNUlL&zipcode" elementtype=nacessary verifyorder="1" >
          </TD>
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
    
        </TR>        
        <tr class=common>
          <TD  class= title>
            �ƶ��绰
          </TD>
          <TD  class= input>
            <Input class= common name="AppntMobile" verify="Ͷ�����ƶ��绰|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
          </TD>         
          <TD  class= title>
            ��ϵ�绰 
          </TD>
          <TD  class= input>
            <Input class= common name="AppntGrpPhone" verify="Ͷ���˰칫�绰|LEN<=15&PHONE"  verifyorder="1" >
          </TD> 
            </tr>   
               
         <TR  class= common style="display:none">        
          <TD  class= title>
            ����绰
          </TD>
          <TD  class= input>
            <Input class= common name="AppntFax" verify="Ͷ���˴���绰|LEN<=15&PHONE"  verifyorder="1" >
          </TD>           
        </tr>
         
        <TR  class= common  style="display:none">
          <TD  class= title>
            סլ�绰 
          </TD>
          <TD  class= input>
          <input class= common name="AppntHomePhone" verify="Ͷ����סլ�绰|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
          </TD>
          <TD  class= title>
            ������λ
          </TD>
          <TD  class= input>
            <Input class=common name="AppntGrpName" verify="Ͷ���˹�����λ|NOTNUlL&LEN<=60"  elementtype=nacessary >
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= common name="AppntEMail" verify="Ͷ���˵�������|EMAIL&LEN<=40"   >
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
  
  </table>
  <hr>
    <div id="DivLCAccount" style="display:''"> 
      <table>
          <tr>
              <td class=common>
                <img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
              </td>
              <td class= titleImg>
                �ɷ���Ϣ
              </td>
          </tr>
      </table>

      <div id="divLCAccount1" style="display:''">
        <table class="common">    
          <tr class="common" style="display:none">
          	 <td class="title">
              ���ڽ�����ʽ
            </td>
            <td>
            <input class="codeno" name="PayMode" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:paymode"  ondblclick="return showCodeList('paymode',[this,FirstPayModeName],[0,1]);" onkeyup="return showCodeListKey('paymode',[this,FirstPayModeName],[0,1]);"><input class="codename" name="FirstPayModeName" readonly="readonly" elementtype=nacessary>
            </td>
          </tr>
          <tr class='common'>
	          <TD class='title'>
	            Ͷ���˿����� 
	          </TD>
	          <TD class='input'>
	            <input  NAME=AppntBankCode VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" ondblclick="return showCodeList('bank',[this,FirstBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,FirstBankCodeName],[0,1]);" ><input class=codename name=FirstBankCodeName readonly=true>
	          </TD>
	          <TD CLASS=title >
	            �ʻ����� 
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input  NAME=AppntAccName VALUE="" CLASS=common MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
	          </TD>
            <TD CLASS=title width="109" >
	            �˺�
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input  NAME=AppntBankAccNo class="common" VALUE="" CLASS=common onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" verify="�����ʺ�|LEN<=40" onfocus="getdetail();"><input type="hidden" class=codename name=AppntBankAccNoName readonly=true>
	          </TD>
        
	        </TR>
          <tr class="common" style="display:none">
          	<td class="title">
              ���ڽ�����ʽ
            </td>
            <td>
            <input class="codeno" name="SecPayMode" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:continuepaymode"   ondblclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKey('continuepaymode',[this,PayModeName],[0,1]);"><input class="codename" name="PayModeName" readonly="readonly" elementtype=nacessary>
            </td>
            <td class="title" >
              �ס������˺�һ��
            </td>
            <td class='title'>
              <input type="checkbox" name='theSameAccount' value="true" onclick="theSameToFirstAccount();">
            </td>
          </tr>
        
          <TR CLASS=common style="display:none">
	          <TD CLASS=title  >
	            ����ת�ʿ����� 
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input NAME=SecAppntBankCode VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"   ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1]);" ><input class=codename name=AppntBankCodeName readonly=true>
	          </TD>
	          <TD CLASS=title >
	            �����ʻ����� 
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input NAME=SecAppntAccName VALUE="" CLASS=common MAXLENGTH=20 verify="����|LEN<=20"   >
	          </TD>
            <TD CLASS=title width="109" >
	            �����˺�
	          </td>
	          <td CLASS=input COLSPAN=1  >
	            <input name='SecAppntBankAccNo' class="common" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" verify="�����ʺ�|LEN<=40" aonfocus="getdetail();"><input type="hidden" class=codename name=aa readonly=true>
	          </td>
         
	        </tr>
        </table>  
      </div> 
    </div>
</DIV>

<Div  id= "divLCAppnt1" style= "display: 'none'">
<table  class= common align='center' >
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
      <Input class= 'common' name=AppntGrade >
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
      <Input class= 'common' name=AppntType >
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
      <Input class= 'common' name=AppntModifyDate >
    </TD>
    <TD  class= title>
      ���һ���޸�ʱ��
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntModifyTime >
    </TD>
  </TR>
</table>
    </Div> 
