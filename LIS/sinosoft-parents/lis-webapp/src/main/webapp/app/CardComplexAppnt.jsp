<Div id=DivLCAppntIndButton STYLE="display: ">
	<!-- Ͷ������Ϣ���� -->
	<Table>
		<TR>
			<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);"></TD>
			<TD class= titleImg>
				Ͷ������Ϣ���ͻ��ţ�<Input class="common"  name=AppntNo id=AppntNo>
				<INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppntNo();">
				�״�Ͷ���ͻ�������д�ͻ��ţ�
			</TD>
		</TR>
	</Table>
</Div>

<Div id=DivLCAppntInd class=maxbox STYLE="display: ">
	<Table  class= common>
		<TR  class= common>        
			<TD  class= title>����</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntName id="AppntName"  onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="Ͷ��������|NOTNUlL&LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntIDType" id="AppntIDType"  verify="Ͷ����֤������|CODE:IDType" verifyorder="1" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);">
				<input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntIDNo" id="AppntIDNo" elementtype=nacessary verify="Ͷ����֤������|LEN<=20"  verifyorder="1" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getAge();if(LoadFlag=='1')confirmSecondInput(this,'onblur');">
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>�Ա�</TD>
			<TD nowrap class= input>
				<Input class="codeno" name=AppntSex id="AppntSex" verify="Ͷ�����Ա�|NOTNUlL&CODE:Sex"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);">
				<input class=codename name=AppntSexName id="AppntSexName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" elementtype=nacessary onblur="checkappntbirthday();getAge();" elementtype=nacessary name="AppntBirthday" id="AppntBirthday" verify="Ͷ���˳�������|NOTNUlL&DATE"  verifyorder="1" >
			</TD>
			<TD  class= title>Ͷ��������</TD>
			<TD  class= input>
				<input class="common wid" name="AppntAge" verify="Ͷ��������|value>0" verifyorder="1" value="" readonly="readonly">
			</TD>
		</TR>
        
		<TR  class= common>
			<!--
			<TD  class= title>����״��</TD>
			<TD nowrap class= input>
				<Input class="codeno" name="AppntMarriage" verify="Ͷ���˻���״��|NOTNUlL&CODE:Marriage"  verifyorder="1" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName readonly=true elementtype=nacessary>
			</TD>
			-->          
			<TD  class= title>ְҵ����</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntOccupationCode" id="AppntOccupationCode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="Ͷ����ְҵ����|NOTNUlL&CODE:OccupationCode"  verifyorder="1"  ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'240');" onblur="getdetailwork();">
				<input class=codename name=AppntOccupationCodeName id="AppntOccupationCodeName" readonly=true elementtype=nacessary>
				<!--<Input type="hidden" name="AppntOccupationType">-->
			</TD>  
			<TD class=title>ְҵ���</TD>
			<TD class= input>
				<Input class="codeno" readonly name="AppntOccupationType" id="AppntOccupationType"  onkeyup="showCodeListKey('OccupationType',[this,AppntOccupationTypeName],[0,1]);" verify="Ͷ����ְҵ���|NOTNUlL&CODE:OccupationType"  verifyorder="1">
				<input class=codename name=AppntOccupationTypeName id="AppntOccupationTypeName" readonly=true elementtype=nacessary>
			</TD> 
			<TD  class= title>������λ</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntGrpName" id="AppntGrpName" verify="Ͷ���˹�����λ|NOTNUlL&LEN<=60"  verifyorder="1" elementtype=nacessary >
			</TD>         
		</TR>     
		<TR  class= common>
			<TD  class= title>��ַ����</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntAddressNo" id="AppntAddressNo" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1], ,  ,  , true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1], ,  ,  , true);">
				<input class=codename name=AddressNoName id="AddressNoName" readonly=true>
			</TD> 			
		</TR>
		<TR class='common' style="display:none">
			<TD class="title">
                ͨѶ��ַ��
                <input type="hidden" name="AppntProvince" id="AppntProvince">
                <input type="hidden" name="AppntCity" id="AppntCity">
                <input type="hidden" name="AppntDistrict" id="AppntDistrict">
                <input type="hidden" name="AppntProvinceName" id="AppntProvinceName">
                <input type="hidden" name="AppntCityName" id="AppntCityName">
                <input type="hidden" name="AppntDistrictName" id="AppntDistrictName">
            </TD>

        </TR>
		<!--TR  class= common>
			<TD  class= title>ʡ</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ����ʡ��|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntProvince"  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1]);"><input class=codename name=AppntProvinceName onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ���˳���|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntCity"  ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename'],1);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename']);"><input class=codename name=AppntCityName onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��/��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ������/��|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntDistrict"  ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],1,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],null,'240');"><input class=codename name=AppntDistrictName onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
			</TD>  
		</TR-->
		<TR  class= common>
			<TD  class= title nowrap>ͨѶ��ַ</TD>
			<TD  class= input colspan=3 nowrap >
				<Input class='common3' elementtype=nacessary name="AppntPostalAddress" id="AppntPostalAddress" verify="Ͷ������ϵ��ַ|NOTNUlL&LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntZipCode" id="AppntZipCode" verify="Ͷ������������|NOTNUlL&zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>  			
		<TR  class= common>
			<TD  class= title>��ϵ�绰</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntPhone" id="AppntPhone" verify="Ͷ������ϵ�绰|LEN<=15&PHONE" verifyorder='2'>
			</TD>  
			<TD  class= title></TD>
			<TD  class= input></td>
			<TD  class= title></TD>
			<TD  class= input></td>
		</TR>  
                
	</Table>	

    <hr class=line>
    <Div id="DivLCAccount" style="display:none">
      <Table>
      	<TR  class= common>
      		<TD  class= title> ���� </TD>
          <TD  class="input">
          <input class="codeno" name="AppntNativePlace" id="AppntNativePlace" verify="Ͷ���˹���|NOTNUlL&CODE:NativePlace"  verifyorder="8" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);">
		  <input class=codename name=AppntNativePlaceName id="AppntNativePlaceName" readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title> סլ�绰</TD>
          <TD  class= input>
          <input class="common wid" name="AppntHomePhone" id="AppntHomePhone" verify="Ͷ����סլ�绰|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
          </TD>
          <TD class=title>��������</TD>
          <TD  class= input>
            <input class="codeno" name="AppntLicenseType" id="AppntLicenseType" verify="����|CODE:LicenseType"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);">
			<input class=codename name=AppntLicenseTypeName id="AppntLicenseTypeName" readonly=true>
          </TD>
          <TD  class= title>��������</TD>
          <TD  class= input> 
            <Input class="common wid" name="AppntEMail" id="AppntEMail" verify="Ͷ���˵�������|EMAIL&LEN<=40"  verifyorder="1" >
          </TD>  
        </TR>  
      	<TR class=common>
          <TD  class= title>�ƶ��绰</TD>
          <TD  class= input>
            <Input class="common wid" name="AppntMobile" id="AppntMobile" verify="Ͷ�����ƶ��绰|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
          </TD>         
          <TD  class= title>�칫�绰</TD>
          <TD  class= input>
            <Input class="common wid" name="AppntGrpPhone" id="AppntGrpPhone" verify="Ͷ���˰칫�绰|LEN<=15&PHONE"  verifyorder="1" >
          </TD>       
          <TD  class= title>����绰</TD>
          <TD  class= input>
            <Input class="common wid" name="AppntFax" id="AppntFax" verify="Ͷ���˴���绰|LEN<=15&PHONE"  verifyorder="1" >
          </TD>           
        </TR>
          <TR>
              <TD class=common>
                <img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
              </TD>
              <TD class= titleImg> �ɷ���Ϣ</TD>
          </TR>
      </Table>

		<Div id="divLCAccount1" style="display:none">
			<Table class="common">    
				<TR class="common">
					<TD class="title">���ڽ�����ʽ</TD>
					<TD>
						<input name="PayMode" id="PayMode" value="1">
						<input class="codename" name="FirstPayModeName" id=FirstPayModeName readonly="readonly" elementtype=nacessary>
					</TD>
				</TR>
				<TR class='common'>
					<TD class='title'> ����ת�ʿ����� </TD>
					<TD class='input'>
						<input  NAME=AppntBankCode id="AppntBankCode" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" ondblclick="return showCodeList('bank',[this,FirstBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,FirstBankCodeName],[0,1]);" >
						<input class=codename name=FirstBankCodeName id="FirstBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ�����</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntAccName id="AppntAccName" VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" >�����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntBankAccNo id="AppntBankAccNo"  VALUE="" class="common wid" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=21 verify="�����˺�|NUM&LEN<=21" onfocus="getdetail();">
						<input type="hidden" class=codename name=AppntBankAccNoName id=AppntBankAccNoName readonly=true>
					</TD>
				</TR>
				<TR class="common">
					<TD class="title">���ڽ�����ʽ</TD>
					<TD>
						<input name="SecPayMode" id=SecPayMode value="1">
						<input class="codename" name="PayModeName" id="PayModeName" readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class="title" >�ס������˺�һ��</TD>
					<TD class='input'>
						<input type="checkbox" name="theSameAccount" id="theSameAccount" value="true" onclick="theSameToFirstAccount();">
					</TD>
				</TR>
			
				<TR CLASS=common>
					<TD CLASS=title  >����ת�ʿ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=SecAppntBankCode id="SecAppntBankCode" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"  verifyorder="1" ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1]);" ><input class=codename name=AppntBankCodeName readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ�����</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=SecAppntAccName id="SecAppntAccName" VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" >�����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=SecAppntBankAccNo id="SecAppntBankAccNo"  VALUE="" class="common wid" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=21 verify="�����˺�|NUM&LEN<=21" onfocus="getdetail();">
						<input type="hidden" class=codename name=aa id=aa readonly=true>
					</TD>
				</TR>
			</Table>  
		</Div> 
	</Div>
</Div>


<!--��Ų���ʾ������-->
<Div  id= "divLCAppnt1" style= "display: none">
	<Table  class= common align="center" >
		<TR class="common">
			<TD class="common">VIP�ͻ�</TD>
			<TD>
				<input type="text" name="AppntVIPValue" id="AppntVIPValue" value="0">
				<input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname" class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>���˼���</TD>
			<TD  class= input><Input class="common wid" name=AppntGrade id="AppntGrade" ></TD>
			<TD  class= title>��������</TD>
			<TD  class= input><Input class="common wid" name=AppntType id="AppntType" ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title>�������</TD>
			<TD  class= input><Input class="common wid" name=AppntMakeDate id="AppntMakeDate" ></TD>
			<TD  class= title>���ʱ��</TD>
			<TD  class= input><Input class="common wid" name=AppntMakeTime  id="AppntMakeTime"></TD>
		</TR>
		<TR  class= common>
			<TD  class= title>���һ���޸�����</TD>
			<TD  class= input><Input class="common wid" name=AppntModifyDate id="AppntModifyDate"></TD>
			<TD  class= title>���һ���޸�ʱ��</TD>
			<TD  class= input><Input class="common wid" name=AppntModifyTime id="AppntModifyTime" ></TD>
		</TR>
	</Table>
</Div>
