<%
//�������ƣ�DirectContAppntPage.jsp
//�����ܣ�ֱ������¼��Ͷ������Ϣҳ��
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<script lanaguage="javascript">

</script>

<!-- Ͷ������Ϣ���� -->
<Table>
	<TR>
		<TD class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInfo);">
		</TD>
		<TD class= titleImg>Ͷ������Ϣ
			��Ͷ���˿ͻ��ţ�<Input class="common"  name=AppntNo id=AppntNo >
			<input id="" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppnt();">
			�״�Ͷ���ͻ�������д�ͻ��ţ�
		</TD>
	</TR>
</Table>
<Div id=DivLCAppntInfo class="maxbox" STYLE="display: ">
	<Table  class= common>
		<TR class="common">
			<TD class="title">VIP�ͻ�</TD>
			<TD>
				<input name="AppntVIPFlag" id=AppntVIPFlag class="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagName],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagName],[0,1]);">
				<input name="AppntVIPFlagName" id=AppntVIPFlagName class="codename" readonly="readonly">
			</TD>
		</TR>
	    <TR  class= common>        
			<TD  class= title>����</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntName id=AppntName onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ��������|NOTNUlL&LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntIDType" id=AppntIDType  verify="Ͷ����֤������|CODE:IDType&NOTNUlL" verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);">
				<input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntIDNo" id=AppntIDNo onblur="checkAppntIDNo();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ����֤������|NOTNUlL&LEN<=20"  verifyorder="1" onblur="">
			</TD>
	    </TR>
	    <TR  class= common>
			<TD  class= title>�Ա�</TD>
			<TD  class= input>
				<Input class="codeno" name=AppntSex id=AppntSex verify="Ͷ�����Ա�|NOTNUlL&CODE:Sex"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);">
				<input class=codename name=AppntSexName id=AppntSexName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" name="AppntBirthday" id=AppntBirthday elementtype=nacessary onblur="checkAppBirthDay();" elementtype=nacessary  verify="Ͷ���˳�������|NOTNUlL&DATE"  verifyorder="1" >
			</TD>
			<TD  class= title>Ͷ��������</TD>
			<TD  class= input>
				<input class="common wid" name="AppntAge" verify="Ͷ��������|value>0" readonly verifyorder="1" value="" >
			</TD>
		</TR>
		<TR  class= common style="display:none">
			<TD  class= title>����״��</TD>
			<TD nowrap class= input>
				<Input class="codeno" name="AppntMarriage" id=AppntMarriage style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName id=AppntMarriageName readonly=true elementtype=nacessary>
			</TD>    
			<TD  class= title>����</TD>
			<TD  class="input">
				<input class="codeno" name="AppntNativePlace" id=AppntNativePlace  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);">
				<input class=codename name=AppntNativePlaceName id=AppntNativePlaceName readonly=true elementtype=nacessary>
			</TD>
			<TD class=title>��������</TD>
			<TD  class= input>
				<input class="codeno" name="AppntLicenseType" id=AppntLicenseType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);">
				<input class=codename name=AppntLicenseTypeName id=AppntLicenseTypeName readonly=true>
			</TD>        
		</TR>
		<TR  class= common>
			<TD class= title>ְҵ����</TD>
			<TD class= input>
				<Input class="codeno" name="AppntOccupationCode" id=AppntOccupationCode verify="Ͷ����ְҵ����|NOTNUlL" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  verifyorder="1" onblur="getAppOpName();" ondblclick= "showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,1,'240');" onkeyup="showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,1,'240');" >
				<input class=codename name=AppntOccupationCodeName id=AppntOccupationCodeName readonly=true elementtype=nacessary>
			</TD>   
			<TD class=title>ְҵ���</TD>
			<TD class= input>
				<Input class="codeno" readonly name="AppntOccupationType" id=AppntOccupationType onkeyup="showCodeListKey('OccupationType',[this,AppntOccupationTypeName],[0,1]);">
				<input class=codename name=AppntOccupationTypeName id=AppntOccupationTypeName readonly=true>
			</TD> 
			
		</TR>
	</Table> 
  	<Table  class= common>  		
		<TR  class= common>
			<TD  class= title>��ַ����</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntAddressNo" id=AppntAddressNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="getAppntAddressNoData();return showCodeListEx('GetAppntAddressNo',[this,AppntAddressNoName],[0,1], ,  ,  , 1);" onkeyup="getAppntAddressNoData();return showCodeListKeyEx('GetAppntAddressNo',[this,AppntAddressNoName],[0,1], ,  ,  , 1);">
				<input class=codename name=AppntAddressNoName id=AppntAddressNoName readonly=true>
			</TD>  
		</TR>	
    	<TR class='common'>
			<TD class="title">ͨѶ��ַ��</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>ʡ</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ����ʡ��|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntProvince" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="getAddressName('province','AppntProvince','AppntProvinceName');" ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1],null,null,null,1);">
				<input class=codename name=AppntProvinceName onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ���˳���|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntCity" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="getAddressName('city','AppntCity','AppntCityName');" ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename'],1);">
				<input class=codename name=AppntCityName  onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��/��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ������/��|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntDistrict" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');">
				<input class=codename name=AppntDistrictName onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
			</TD>  
		</TR>
		<TR  class= common>
			<TD  class= title>�ֵ�</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntPostalAddress" verify="Ͷ������ϵ��ַ|NOTNUlL&LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntZipCode" verify="Ͷ������������|NOTNUlL&zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>
		<TR class=common>         
			<TD  class= title>�칫�绰</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntGrpPhone" verify="Ͷ���˰칫�绰|LEN<=15&PHONE"  verifyorder="1" >
			</TD>
			<TD  class= title>��ͥ�绰</TD>
			<TD  class= input>
				<input class="common wid" name="AppntHomePhone" verify="Ͷ����סլ�绰|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>   
			<TD  class= title>�ƶ��绰</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntMobile" verify="Ͷ�����ƶ��绰|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>           
		</TR> 
		<TR  class= common style="display:none">
			<TD class=title> ��ϵ�绰</TD> 
			<TD class=input>
				<Input name="AppntPhone" class="common wid" verify="Ͷ������ϵ�绰|LEN<=15&PHONE" >
			</TD>  
			<TD class=title></TD>
			<TD class=input></TD>
			<TD class=title></TD>
			<TD class=input></TD>
	    </TR>	
		<TR  class= common style="display:none">
			<TD  class= title>����绰</TD>
			<TD  class= input><Input class="common wid" name="AppntFax"></TD>  
			<TD  class= title>������λ</TD>
			<TD  class= input><Input class="common wid" name="AppntGrpName"></TD>
			<TD  class= title>��������</TD>
			<TD  class= input><Input class="common wid" name="AppntEMail"></TD>  
	    </TR>           
	</Table>
</Div>



<Table>
	<TR>
		<TD class=common>
			<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount);">
		</TD>
		<TD class= titleImg>�ɷ���Ϣ</TD>
	</TR>
</Table>

<div id="divLCAccount" class=maxbox style="display: ">
	<Table class="common">    
		<TR class="common">
			<TD class="title">���ڽ�����ʽ</TD>
			<TD>
				<input class="codeno" name="NewPayMode" verifyorder="1" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:paymode" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('paymode',[this,NewPayModeName],[0,1]);" onkeyup="return showCodeListKey('paymode',[this,NewPayModeName],[0,1]);"><input class="codename" name="NewPayModeName" readonly="readonly" elementtype=nacessary>
			</TD>
		</TR>
		<TR class='common'>
			<TD class='title'>Ͷ���˿�����</TD>
			<TD class='input'>
				<input  name=NewBankCode VALUE="" class="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('bank',[this,NewBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,NewBankCodeName],[0,1]);" ><input class=codename name=NewBankCodeName readonly=true>
			</TD>
			<TD class=title >�ʻ�����</TD>
			<TD class=input >
				<Input  name=NewAccName VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
			</TD>
			<TD class=title >�����˺�</TD>
			<TD class=input >
				<Input  name=NewBankAccNo class="common wid" VALUE="" class="common wid" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" ondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=25 verify="�����˺�|LEN<=25" >
				<input type="hidden" class=codename name=AppntBankAccNoName readonly=true>
			</TD>
		</TR>
		<TR class="common" style="display:">
			<TD class="title">���ڽ�����ʽ</TD>
			<TD>
				<input class="codeno" name="SecPayMode" verifyorder="1" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="���ڽ��ѷ�ʽ|NOTNUlL&CODE:continuepaymode"   ondblclick="return showCodeList('continuepaymode',[this,SecPayModeName],[0,1]);" onkeyup="return showCodeListKey('continuepaymode',[this,SecPayModeName],[0,1]);"><input class="codename" name="SecPayModeName" readonly="readonly" elementtype=nacessary>
			</TD>
			<TD class="title" >�ס������˺�һ��</TD>
			<TD class='title'>
				<input type="checkbox" name='theSameAccount'  onclick="SameToFirstAccount();">
			</TD>
		</TR>
		<TR class=common style="display:">
			<TD class=title>����ת�ʿ�����</TD>
			<TD class=input>
				<Input NAME=SecBankCode VALUE="" class="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" MAXLENGTH=20 verify="������|CODE:bank"   ondblclick="return showCodeList('bank',[this,SecBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,SecBankCodeName],[0,1]);" ><input class=codename name=SecBankCodeName readonly=true>
			</TD>
			<TD class=title>�����ʻ����� </TD>
			<TD class=input>
				<Input NAME=SecAccName VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20"   >
			</TD>
			<TD class=title>�����˺�</TD>
			<TD class=input>
				<input name='SecBankAccNo' style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="common wid" ondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" MAXLENGTH=25 verify="�����˺�|LEN<=25" aonfocus="getdetail();"><input type="hidden" class=codename name=aa readonly=true>
			</TD>
		</TR>
	</Table>   
</div> 


<Div  id= "divLCAppnt" style= "display: none">
<Table  class= common align='center' >
  <TR  class= common>
    <TD  class= title> Ͷ���˼���</TD>
    <TD  class= input> <Input class="common wid" name=AppntGrade ></TD>
    <TD  class= title>Ͷ��������</TD>
    <TD  class= input><Input class="common wid" name=AppntType ></TD>
  </TR>
  <TR  class= common>
    <TD  class= title>�������</TD>
    <TD  class= input><Input class="common wid" name=AppntMakeDate ></TD>
    <TD  class= title>���ʱ��</TD>
    <TD  class= input><Input class="common wid" name=AppntMakeTime ></TD>
  </TR>
  <TR  class= common>
    <TD  class= title>���һ���޸�����</TD>
    <TD  class= input><Input class="common wid" name=AppntModifyDate ></TD>
    <TD  class= title>���һ���޸�ʱ��</TD>
    <TD  class= input><Input class="common wid" name=AppntModifyTime ></TD>
  </TR>
</Table>
</Div> 
