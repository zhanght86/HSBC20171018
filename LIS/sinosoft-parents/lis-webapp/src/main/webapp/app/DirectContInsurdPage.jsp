<%
//�������ƣ�DirectContInsurdPage.jsp
//�����ܣ�ֱ������¼�뱻������Ϣҳ��
//�������ڣ� 2006-1-20 10:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script lanaguage="javascript">

</script>	
<!-- ��������Ϣ���� -->
<Table>
	<TR>
		<TD class="common">
			<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsuredInfo);">
		</TD>
		<TD class="titleImg">������������
			���ͻ��ţ�<input class="common" name="InsuredNo" value="">
			<input id="InsuredButBack" VALUE="��  ѯ" class=cssButton type="button" onclick="queryInsured();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
		    <Div  id= "divSamePerson" style="display:none">
				<font color="red">��Ͷ����Ϊ�������˱��ˣ������������ѡ��<input type="checkbox" name="SamePersonFlag" onclick="IsSamePerson();"></font>
		    </Div>
		</TD>
	</TR>
</Table>
<div class=maxbox>
<Div id=DivRelation style="display: ">
	<Table  class="common">  
		<TR class="common">
			<TD class="title">VIP�ͻ�</TD>
			<TD>
				<input  name="InsuredVIPFlag" id=InsuredVIPFlag class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('vipvalue', [this,InsuredVIPFlagName], [0,1]);" onkeyup="return showCodeListKey('vipvalue', [this,InsuredVIPFlagName], [0,1]);"><input type="text" name="InsuredVIPFlagName" class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">  
			<TD  class="title">�ͻ��ڲ�����</TD>             
			<TD class="input">
				<input class="codeno" name="SequenceNo" id=SequenceNo verify="�ͻ��ڲ�����|NOTNULL&NUM&CODE:SequenceNo" verifyorder='2' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKey('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" elementtype=nacessary readonly="true">
			</TD>              
			<TD  class="title">���һ�������˹�ϵ</TD>             
			<TD  class="input">
				<input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured verify="���������˹�ϵ|NOTNULL&CODE:Relation" verifyorder='2' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName"  elementtype=nacessary readonly="readonly"  >
			</TD>  
			<TD  class= title>��Ͷ���˹�ϵ</TD>
			<TD  class= input>
			<Input class="codeno" name="RelationToAppnt" id=RelationToAppnt verify="��Ͷ���˹�ϵ|NOTNULL&CODE:Relation" verifyorder='2' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName" readonly="readonly" elementtype=nacessary>
			</TD> 
		</TR> 
	</Table> 
</Div>  


<Div id=DivLCInsuredInfo style="display: ">
	<Table  class= common>
		<TR class= common>        
			<TD  class= title>����</TD>
			<TD  class= input>
				<Input class="common wid" name=InsuredName id=InsuredName onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="������������|NOTNULL&LEN<=20" verifyorder='2' onblur="getallinfo();">
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredIDType" id=InsuredIDType verify="��������֤������|NOTNULL&CODE:IDType" verifyorder='2' ondblclick="return showCodeList('IDType',[this,InsuredIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,InsuredIDTypeName],[0,1]);" aonblur="getallinfo();" ><input class=codename name=InsuredIDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredIDNo" id=InsuredIDNo onblur="checkInsuredIDNo();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="��������֤������|NOTNULL&LEN<=20" verifyorder='2'>
			</TD>                                                                                                                                                                                                        
		</TR>
		<TR class= common>
			<TD  class= title>�Ա�</TD>
			<TD  class= input>
				<Input class="codeno" name=InsuredSex id=InsuredSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="���������Ա�|NOTNULL&CODE:Sex" verifyorder='2' ondblclick="return showCodeList('Sex',[this,InsuredSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,InsuredSexName],[0,1]);">
				<input class=codename name=InsuredSexName id=InsuredSexName readonly="readonly" elementtype=nacessary>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" name="InsuredBirthday" id=InsuredBirthday elementtype=nacessary onblur="checkInsuredBirthday()" verify="�������˳�������|NOTNULL&DATE" verifyorder='2' >
			</TD>
			<TD CLASS=title>����������</TD>
			<TD CLASS=input >
				<Input NAME=InsuredAppAge id=InsuredAppAge readonly verify="����������|value>=0" verifyorder="2" VALUE=""  CLASS="common wid"  >
			</TD>
		</TR>
		<TR class= common style="display:none">
			<TD  class= title>����״��</TD>
			<TD  class= input>
				<Input class="codeno" name=InsuredMarriage id=InsuredMarriage ondblclick="return showCodeList('Marriage',[this,InsuredMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,InsuredMarriageName],[0,1]);"><input class=codename name=InsuredMarriageName readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>����</TD>
			<TD  class= input>
				<input class="codeno" name="InsuredNativePlace" id=InsuredNativePlace  ondblclick="return showCodeList('NativePlace',[this,InsuredNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,InsuredNativePlaceName],[0,1]);"><input class=codename name=InsuredNativePlaceName readonly=true>
			</TD>              
			<TD class=title>��������	</TD>
			<TD  class= input>
				<input class="codeno" name=InsuredLicenseType id=InsuredLicenseType verify="�������˼�������|CODE:LicenseType" verifyorder='2' ondblclick="return showCodeList('LicenseType',[this,InsuredLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,InsuredLicenseTypeName],[0,1]);"><input class=codename name=InsuredLicenseTypeName readonly=true>
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>ְҵ����</TD>
			<TD  class=input>
				<Input class="codeno" name="InsuredOccupationCode" id=InsuredOccupationCode verify="��������ְҵ����|CODE:OccupationCode&NOTNULL" verifyorder='2' onblur="getInsuredOpName();" ondblclick="return showCodeList('OccupationCode',[this,InsuredOccupationCodeName],[0,1],null,null,null,1,'240');"  on keyup="return showCodeListKey('OccupationCode',[this,InsuredOccupationCodeName],[0,1],null,null,null,1,'240');">
				<input class=codename name=InsuredOccupationCodeName id=InsuredOccupationCodeName elementtype=nacessary readonly=true>
			</TD>
			<TD  class= title>ְҵ���</TD>
			<TD  class= input>
			  <Input class="codeno" name="InsuredOccupationType" id=InsuredOccupationType readonly onkeyup="showCodeListKey('OccupationType',[this,InsuredOccupationTypeName],[0,1]);"><input class=codename name=InsuredOccupationTypeName readonly=true>
			</TD>
			
		</TR>               
	</Table> 
  	<Table  class= common>   	
		<TR class= common>
			<TD  class= title>��ַ����</TD>                  
			<TD  class= input>
				<Input class="codeno" name="InsuredAddressNo" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredAddressNo ondblclick="getInsuredAddressNoData();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , 1);" onkeyup="getInsuredAddressNoData(); return showCodeListKeyEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , 1);" >
				<input class=codename name=InsuredAddressNoName id=InsuredAddressNoName readonly=true>
			</TD>
		</TR>  
		<TR class='common'>
			<TD class="title">ͨѶ��ַ��</TD>
		</TR>
		<TR class= common>
			<TD  class= title>ʡ</TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredProvince" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredProvince  onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);">
				<input class=codename name=InsuredProvinceName id=InsuredProvinceName onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" readonly=true>
			</TD>  
			<TD  class= title>�� </TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredCity" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredCity  onblur="getAddressName('city','InsuredCity','InsuredCityName');" ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename'],1);">
				<input class=codename name=InsuredCityName id=InsuredCityName onblur="getAddressName('city','InsuredCity','InsuredCityName');" readonly=true>
			</TD>  
			<TD  class= title>��/�� </TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredDistrict" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredDistrict onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],1,'240');">
				<input class=codename onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" id=InsuredDistrictName name=InsuredDistrictName readonly=true >
			</TD>  
		</TR>        
		<TR class= common>
			<TD class= title>�ֵ�</TD>
			<TD  class= input  colspan="3"  >
				<Input class="common3" name="InsuredPostalAddress" id=InsuredPostalAddress>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredZipCode" id=InsuredZipCode MAXLENGTH="6" >
			</TD>
		</TR>
		<TR class= common style="display:none">
			<TD  class= title>�칫�绰</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredGrpPhone" id=InsuredGrpPhone verify="�������˰칫�绰|LEN<=15&PHONE" verifyorder='2' >
			</TD>   
			<TD class=title>��ͥ�绰</TD>
			<TD class=input>
				<Input name="InsuredHomePhone" class="common wid" verify="�������˼�ͥ�绰|LEN<=15&PHONE" verifyorder='2'>
			</TD>		       
			<TD  class= title>�ƶ��绰</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredMobile" id=InsuredMobile verify="���������ƶ��绰|LEN<=15&PHONE" verifyorder='2' >
			</TD>
		</TR>   
		<TR  class= common >
			<TD class=title> ��ϵ�绰</TD> 
			<TD class=input>
				<Input name="InsuredPhone" id=InsuredPhone class="common wid" verify="��������ϵ�绰|LEN<=15&PHONE" >
			</TD>  
			<TD class=title></TD>
			<TD class=input></TD>
			<TD class=title></TD>
			<TD class=input></TD>
	    </TR>      
		<TR class= common style="display:none">
			<TD  class= title>����绰 </TD>
			<TD  class= input><Input class="common wid" name="InsuredFax" id=InsuredFax ></TD>	
			<TD  class= title>������λ </TD>
			<TD  class= input ><Input class="common wid" name="InsuredGrpName" id=InsuredGrpName></TD>
			<TD  class= title>�������� </TD>
			<TD  class= input><Input class="common wid" name="InsuredEMail" id=InsuredEMail ></TD>
		</TR>  
	</Table>    
</Div>
</div>
<Table id="">
	<TR>
		<TD class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivImpartInsuredInfo);"></TD>
		<TD class="titleImg">�����˸�֪��Ϣ</TD>
		<TD><Input type="button" class="cssButton" name="HiddenInsuredImpart" value="�����˸�֪��Ϣ"></TD>
	</TR>
</Table>
<Div id=DivImpartInsuredInfo style="display: ">
	<table class="common">
		<tr class="common">
			<td style="text-align:left" colSpan="1">
				<span id="spanImpartInsuredGrid"></span>
			</td>
		</tr>
	</table>
</Div>	

