<Div id=DivLCInsuredButton STYLE="display: ">
	<!-- ��������Ϣ���� -->
	<Table>
		<TR>
			<TD class="common"><img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);"></TD>
			<TD class="titleImg">
				<input class="mulinetitle" value="������������"  name="InsuredSequencename" id=InsuredSequencename readonly >���ͻ��ţ�<input class="common" name="InsuredNo" id=InsuredNo value="">
				<input id="InsuredButBack" VALUE="��  ѯ" class=cssButton type="button" onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
				<Div  id= "divSamePerson" style="display:none">
					<font color="red">��Ͷ����Ϊ�������˱��ˣ������������ѡ��
						<input type="checkbox" name="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
					</font>
				</Div>
			</TD>
		</TR>
	</Table>
</Div>

<div class=maxbox>
<Div id=DivRelation  style="display: ">
	<Table  class="common">  
		<TR  class="common">  
			<TD  class= title>��Ͷ���˹�ϵ</TD>
			<TD  class= input>
				<Input class="codeno" name="RelationToAppnt" id="RelationToAppnt" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="��Ͷ���˹�ϵ|NOTNULL&CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');">
				<Input class="codename" name="RelationToAppntName" id=RelationToAppntName readonly="readonly" elementtype=nacessary>
			</TD> 
			<TD></TD>
			<TD></TD>
			<TD></TD>
		</TR> 
	</Table> 
</Div>  


<Div id=DivLCInsured style="display: ">
	<Table  class= common>
		<TR  class= common>        
			<TD  class= title>����</TD>
			<TD  class= input>
				<Input class="wid common" name=Name id="Name" onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="������������|NOTNULL&LEN<=20" verifyorder='2' onblur="getallinfo();">
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="codeno" name="IDType" id=IDType value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="��������֤������|CODE:IDType" verifyorder='2' ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" aonblur="getallinfo();" >
				<input class=codename name=IDTypeName id=IDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>֤������ </TD>
			<TD  class= input>
				<Input class="wid common" name="IDNo" id=IDNo onblur="checkidtype2();getBirthdaySexByIDNo2(this.value);getAge2();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="��������֤������|LEN<=20" verifyorder='2'>
			</TD>                                                                                                                                                                                                        
		</TR>
		<TR  class= common>
			<TD  class= title>�Ա� </TD>
			<TD  class= input>
				<Input class="codeno" name=Sex id=Sex verify="���������Ա�|NOTNULL&CODE:Sex" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verifyorder='2' ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);">
				<input class=codename name=SexName id=SexName readonly="readonly" elementtype=nacessary>
			</TD>
			<TD  class= title>�������� </TD>
			<TD  class= input>
				<input class="common" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge2();" name="Birthday"  verify="�������˳�������|NOTNULL&DATE" verifyorder='2' onClick="laydate({elem: '#Birthday'});" id="Birthday">
				<span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
			<TD CLASS=title>����������</TD>
			<TD CLASS=input COLSPAN=1>
				<Input NAME=InsuredAppAge id=InsuredAppAge verify="����������|value>=0" verifyorder="2" VALUE="" readonly=true CLASS="common wid" verify="����������|NUM" >
			</TD>
		</TR>
	
		<TR  class= common>
			<!--TD  class= title>����״��</TD>
			<TD  class= input>
			<Input class="codeno" name=Marriage ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName readonly=true elementtype=nacessary verify="�����˻���״��|NOTNUlL&CODE:Marriage"  verifyorder="2">
			</TD-->  
			<TD  class= title>ְҵ����</TD>
			<TD  class=input>
				<Input class="codeno" name="OccupationCode" id=OccupationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="��������ְҵ����|NOTNULL&CODE:OccupationCode" verifyorder='2' ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onblur="getdetailwork2();">
				<input class=codename name=OccupationCodeName id=OccupationCodeName readonly=true elementtype=nacessary >
				<!--<Input type="hidden" name="OccupationType">--> 
			</TD>
			<TD  class= title>ְҵ���</TD>
			<TD  class= input>
			  <Input class="codeno" name="OccupationType" id=OccupationType readonly  onkeyup="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);">
			  <input class=codename name=OccupationTypeName id="OccupationTypeName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>������λ</TD>
			<TD  class= input ><Input class="wid common" name="GrpName" id=GrpName></TD>	    
		</TR>               
	</Table>    
	  
	  <!--#######���������˵�ַ��Ϣ��¼��־����Ϊ�Ǳ�¼��,�޸���2005-11-02.######-->
	<Div id=DivAddress STYLE="display: ">   
		<Table  class= common>   	
			<TR  class= common>
				<TD  class= title>��ַ����</TD>
				<TD  class= input>
					<Input class="codeno" name="InsuredAddressNo" id=InsuredAddressNo style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="getaddresscodedata2();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , true);" onkeyup="getaddresscodedata2(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , true);" onfocus="getdetailaddress2();">
					<input class=codename name=InsuredAddressNoName id=InsuredAddressNoName readonly=true>
				</TD>
				<TD  class= title></TD>
				<TD  class= input></td>
				<TD  class= title></TD>
				<TD  class= input></td>
			</TR>  
			<TR class=common>
				<TD class="title" style="display:none">
                    ͨѶ��ַ��
                    <Input type="hidden" name="InsuredProvince" id=InsuredProvince>
                    <Input type="hidden" name="InsuredCity" id=InsuredCity>
                    <Input type="hidden" name="InsuredDistrict" id=InsuredDistrict>
                    <Input type="hidden" name="InsuredProvinceName" id=InsuredProvinceName>
                    <Input type="hidden" name="InsuredCityName" id=InsuredCityName>
                    <Input type="hidden" name="InsuredDistrictName" id=InsuredDistrictName>
                </TD>
			</TR>
			<!--TR  class= common>
				<TD  class= title>ʡ</TD>
				<TD  class= input>
 					<Input class="codeno" name="InsuredProvince"  ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" readonly=true >
				</TD>  
				<TD  class= title>��</TD>
				<TD  class= input>
					<Input class="codeno" name="InsuredCity"  ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName onblur="getAddressName('city','InsuredCity','InsuredCityName');" readonly=true>
				</TD>  
				<TD  class= title>��/�� </TD>
				<TD  class= input>
					<Input class="codeno" name="InsuredDistrict"  ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" readonly=true>
				</TD>  
			</TR-->
			<TR  class= common>
				<TD class= title nowrap>ͨѶ��ַ</TD>
				<TD  class= input  colspan="3" nowrap >
					<Input class= common3 name="PostalAddress" id=PostalAddress >
				</TD>
				<TD  class= title>��������</TD>
				<TD  class= input>
					<!--
					<Input class= common name="ZIPCODE" elementtype=nacessary MAXLENGTH="6" verify="����������������|NOTNULL&ZIPCODE" verifyorder='2' >
	                -->	
					<Input class="wid common" name="ZIPCODE" id=ZIPCODE >
				</TD>           
			</TR>
			<TR  class= common>
				<TD  class= title>��ϵ�绰</TD>
    			<TD  class= input>
    				<Input class="common wid" name="InsuredPhone" id=InsuredPhone verify="����������ϵ�绰|LEN<=15&PHONE" verifyorder='2'>
    			</TD> 
				<TD  class= title></TD>
				<TD  class= input></td>
				<TD  class= title></TD>
				<TD  class= input></td>
			</TR>
		</Table>
	</Div>    
</Div>

<Table class= common>
	<TR class= common>
	<TD  class= title>
		<Div id="divContPlan" style="display:none" >
			<Table class= common>
				<TR class= common>
					<TD  class= title>���ռƻ�</TD>
					<TD  class= input>
						<Input class="code" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('ContPlanCode',[this],[0], ,  ,  , true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0], ,  ,  , true);">
					</TD>
				</TR>
			</Table>
		</Div>
	</TD>
	<TD  class= title>
		<Div id="divExecuteCom" style="display:none" >
			<Table class= common>
				<TR  class= common>  
					<TD  class= title>����</TD>
					<TD  class= input>
						<input class="codeno" name="NativePlace" id=NativePlace style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);">
						<input class=codename name=NativePlaceName id=NativePlaceName readonly=true>
					</TD>    
					<TD class=title>סլ�绰</TD>
					<TD class=input>
						<Input name="HomePhone" id=HomePhone class="common wid" verify="��������סլ�绰|LEN<=15&PHONE" verifyorder='2'>
					</TD>			
					<TD class=title>��������</TD>
					<TD  class= input>
					<input class="codeno" name=LicenseType id=LicenseType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="�������˼�������|CODE:LicenseType" verifyorder='2' ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);">
					<input class=codename name=LicenseTypeName id=LicenseTypeName readonly=true>
					</TD> 
					<TD  class= title>��������</TD>
					<TD  class= input>
						<Input class="wid common" name="EMail" id=EMail verify="�������˵�������|LEN<=20&EMAIL" verifyorder='2' >
					</TD>
				</TR> 
				<TR class= common>
					<TD  class= title>�������</TD>
					<TD  class= input>
						<Input class="code" name="ExecuteCom" id=ExecuteCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="showCodeListEx('ExecuteCom',[this],[0], ,  ,  , true);" onkeyup="showCodeListKeyEx('ExecuteCom',[this],[0], ,  ,  , true);">
					</TD>
				</TR>
			</Table>
		</Div>
	
	</TD>
	<TD  class= title></TD>		
	</TR>
</Table>
  
<Div id=DivClaim STYLE="display:none"> 
	<Table  class= common>         
		<TR  class= common>  
			<TD  class= title>������ʻ�</TD>                  
			<TD  class= input>
				<Input class="code" name="AccountNo" id=AccountNo  CodeData="0|^1|�ɷ��ʻ�^2|����" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
			</TD>
		</TR> 
		<TR CLASS=common>
			<TD CLASS=title  >�������� </TD>
			<TD CLASS=input COLSPAN=1  >
				<Input NAME=BankCode id=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="������|CODE:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
			</TD>
			<TD CLASS=title >���� </TD>
			<TD CLASS=input COLSPAN=1  >
				<Input NAME=AccName id=AccName VALUE="" CLASS="common wid" MAXLENGTH=20 verify="����|LEN<=20" >
			</TD>
			<TD CLASS=title width="109" >�˺�</TD>
			<TD CLASS=input COLSPAN=1  >
				<Input NAME=BankAccNo id=BankAccNo class="code" VALUE="" CLASS="common wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="�����ʺ�|LEN<=40" onfocus="getdetail();">
			</TD>
		</TR>   
	</Table>          
</Div>


<Div id=divSalary style="display: ">
</Div>

<Div id=divLCInsuredPerson style="display:none">
	<Div id=DivGrpNoname STYLE="display:none">        
		<Table  class= common> 
			<TR  class= common>
				<TD  class= title>�ƶ��绰</TD>
				<TD  class= input>
					<Input class="wid common" name="Mobile" id=Mobile verify="���������ƶ��绰|LEN<=15&PHONE" verifyorder='2' >
				</TD>
				<TD  class= title>�칫�绰</TD>
				<TD  class= input>
					<Input class="wid common" name="GrpPhone" id=GrpPhone verify="�������˰칫�绰|LEN<=15&PHONE" verifyorder='2' >
				</TD>          
				<TD  class= title>����绰</TD>
				<TD  class= input colspan=3 >
					<Input class="wid common" name="Fax" id=Fax verify="�������˴���绰|LEN<=15&PHONE" verifyorder='2' >
				</TD>
			</TR>       
			<TR class="common">
				<TD  class="title">�ͻ��ڲ�����</TD>             
				<TD class="input">
					<!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
					<input name="SequenceNo" id=SequenceNo value="1">
					<Input class="codename" name="SequenceNoName" id=SequenceNoName elementtype=nacessary readonly="true">
				</TD>              
				<TD  class="title">���һ�������˹�ϵ</TD>             
				<TD  class="input">
					<input name="RelationToMainInsured" id=RelationToMainInsured value="00"">
					<Input class="codename" name="RelationToMainInsuredName" id=RelationToMainInsuredName  elementtype=nacessary readonly="readonly"  >
				</TD>  
				<TD class="common">VIP�ͻ�</TD>
				<TD>
					<input type="text" name="VIPValue1" id=VIPValue1 value="0">
					<input type="text" name="AppntVIPFlagname1" id=AppntVIPFlagname1 class="codename" readonly="readonly">
				</TD>
			</TR>       
			<TR class= common>
				<TD CLASS=title>�������ͱ��</TD>
				<TD CLASS=input COLSPAN=1>
					<Input NAME=PolTypeFlag id=PolTypeFlag VALUE="0" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 ondblclick="showCodeList('PolTypeFlag', [this]);" onkeyup="showCodeListKey('PolTypeFlag', [this]);" verify="�������ͱ��" >
				</TD>        
				<TD CLASS=title>����������</TD>
				<TD CLASS=input COLSPAN=1>
					<Input NAME=InsuredPeoples id=InsuredPeoples VALUE="" readonly=true CLASS="common wid" verify="����������|NUM" >
				</TD>
				<td class=title></td>
				<td class=input></td>
			</TR>  
		</Table>
	</Div>
</Div>
</div>