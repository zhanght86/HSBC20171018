	 <script lanaguage="javascript">
function getInsuredOpName()
{
	fm.OccupationCodeName.value = "";
	showOneCodeName('OccupationCode',"OccupationCode","OccupationCodeName");
	getdetailwork2();
}	
function onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName)
{
	fm.OccupationCodeName.value = "";	
	showCodeList('occupationcode',[OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,null,null,null,'240');
	showOneCodeName('occupationcode','OccupationCode','OccupationCodeName');
	//getdetailwork2(); 
}

function onClickUpInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName)
{
	fm.OccupationCodeName.value = "";
	showCodeListKey('occupationcode',[OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,null,null,null,'240')
	showOneCodeName('occupationcode','OccupationCode','OccupationCodeName');
	//getdetailwork2();
}	
</script>

<Div id="DivLCInsuredButton" STYLE="display: ">
	<!-- ��������Ϣ���� -->
	<Table>
		<tr>
			<TD class="common">
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);">
			</TD>
			<TD class="titleImg">
				<input class="mulinetitle" value="������������"  name="InsuredSequencename" id="InsuredSequencename" readonly >���ͻ��ţ�<input class="common" name="InsuredNo" id="InsuredNo" value="">
				<a href="javascript:void(0)" id="InsuredButBack" style="font-weight:normal;" class=button onclick="queryInsuredNo();">��  ѯ</a> �״�Ͷ���ͻ�������д�ͻ��ţ�
				<Div  id= "divSamePerson" style="display:none">
					<font color="red">��Ͷ����Ϊ�������˱��ˣ������������ѡ��<input type="checkbox" name="SamePersonFlag" id="SamePersonFlag" onclick="isSamePerson();"></font>
				</Div>
			</TD>
		</tr>
	</Table>
</Div>

<div class="maxbox">
<Div id="DivRelation1" style="display:none">
	<Table  class="common">  
		<TR class="common">
			<TD class="title">VIP�ͻ�</TD>
			<TD class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" name="VIPValue1" id="VIPValue1" class="codeno" readonly="readonly" onclick="return showCodeList('vipvalue', [this,AppntVIPFlagname1], [0,1]);" ondblclick="return showCodeList('vipvalue', [this,AppntVIPFlagname1], [0,1]);" onkeyup="return showCodeListKey('vipvalue', [this,AppntVIPFlagname1], [0,1]);"><input type="text" name="AppntVIPFlagname1" id="AppntVIPFlagname1" class="codename" readonly="readonly">
			</TD>
			<td class="title"></td>
			<td class="input"></td>
			<td class="title"></td>
			<td class="input"></td>
		</TR>
	</table>
</Div>
<Div id="DivRelation" style="display: ">
	<Table  class="common">  
		<TR class="common">  
			<TD  class="title">�ͻ��ڲ�����</TD>             
			<TD class="input">
				<!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SequenceNo" id="SequenceNo" verify="�ͻ��ڲ�����|CODE:SequenceNo&NOTNULL&NUM" verifyorder='2' onclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" ondblclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKey('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" id="SequenceNoName" elementtype=nacessary readonly="true">
				<font color="red">*</font>
			</TD>              
			<TD  class="title">���һ�������˹�ϵ</TD>             
			<TD  class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToMainInsured" id="RelationToMainInsured" verify="���һ�������˹�ϵ|NOTNULL&CODE:Relation" verifyorder='2' onclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" elementtype=nacessary readonly="readonly"  >
				<font color="red">*</font>
			</TD>  
			<TD  class= title>��Ͷ���˹�ϵ</TD>
			<TD  class= input>
			<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToAppnt" id="RelationToAppnt" verify="��Ͷ���˹�ϵ|CODE:Relation" verifyorder='2' onclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName" id="RelationToAppntName" readonly="readonly" >
			</TD> 
		</TR> 
	</Table> 
  


<Div id="DivLCInsured" style="display: ">
	<Table  class= common>
		<TR  class= common>        
			<TD  class= title>��</TD>
			<TD  class=input >
				<Input class="common wid"  name=InsuredLastName id="InsuredLastName" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="����������|NOTNUlL&LEN<=20"  verifyorder="2">
				<font color="red">*</font>
			</TD>
			<TD  class= title>��</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=InsuredFirstName id="InsuredFirstName" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="����������|NOTNUlL&LEN<=20"  verifyorder="2" onblur="getallinfo();">
				<font color="red">*</font>
			</TD>
			<TD  class= title>����</TD>
			<TD  class= input>
				<Input class="common wid" name=Name id="Name" elementtype=nacessary verify="������������|LEN<=20" verifyorder='2' readonly>
				<font color="red">*</font>
			</TD>
	    </TR>
	    <TR  class= common>        
			<TD  class= title>Ӣ����</TD>
			<TD  class=input >
				<Input class="common wid" name=InsuredLastNameEn id="InsuredLastNameEn"  verify="��������Ӣ����|LEN<=20"  verifyorder="2">
			</TD>
			<TD  class= title>Ӣ����</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=InsuredFirstNameEn id="InsuredFirstNameEn" verify="��������Ӣ����|LEN<=20"  verifyorder="2">
			</TD>
			<TD  class= title>ƴ����</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=InsuredLastNamePY id="InsuredLastNamePY" verify="��������ƴ����|LEN<=20"  verifyorder="2">
			</TD>
	    </TR>
	    <TR  class= common>        
			<TD  class= title>ƴ����</TD>
			<TD  class=input >
				<Input class="common wid" name=InsuredFirstNamePY id="InsuredFirstNamePY" verify="��������ƴ����|LEN<=20"  verifyorder="2">
			</TD>
			<TD  class= title>�Ա�</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=Sex id="Sex" verify="���������Ա�|NOTNULL&CODE:Sex" verifyorder='2' onclick="return showCodeList('Sex',[this,SexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName id="SexName" readonly="readonly" elementtype=nacessary>
				<font color="red">*</font>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge2();" verify="�������˳�������|NOTNULL&DATE" verifyorder='2' dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>                                                                                                               
					</TD>
		</TR>
		<TR class= common>
			<TD CLASS=title>����������</TD>
			<TD CLASS=input COLSPAN=1>
				<Input NAME=InsuredAppAge id="InsuredAppAge" verify="����������|value>=0" verifyorder="2" VALUE="" readonly=true class="common wid" verify="����������|NUM" >
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="IDType" id="IDType" verify="��������֤������|CODE:IDType" verifyorder='2' onclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" aonblur="getallinfo();" ><input class=codename name=IDTypeName id="IDTypeName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="common wid" name="IDNo" id="IDNo" onblur="checkidtype2();getBirthdaySexByIDNo2(this.value);getAge2();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="��������֤������|LEN<=20" verifyorder='2'>
				<font color="red">*</font>
			</TD>             
			
		</TR>
		<TR>
			<TD class= title>֤��������Ч����</TD>
			<TD class= input>
				<input class="common wid" dateFormat="short" name="IDPeriodOfValidity" id="IDPeriodOfValidity" verify="֤��������Ч��|DATE" verifyorder="1">
			</TD>
			<TD class= title>�Ƿ�ӵ�й���ҽ�ơ����ҽ�Ʊ���</TD>
			<TD class= input>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="SocialInsuFlag" id="SocialInsuFlag"
            				   CodeData="0|^0|��^1|��"
            				   onClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);"
            				   ondblClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('SocialInsuFlag',[this],[0,1]);">
			</TD>
			<TD class= title>����</TD>
			<TD class= input>
			<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredLanguage" id="InsuredLanguage" verify="����|CODE:language" verifyorder="1" onclick="return showCodeList('language',[this,InsuredLanguageName],[0,1]);" ondblclick="return showCodeList('language',[this,InsuredLanguageName],[0,1]);" onkeyup="return showCodeListKey('language',[this,InsuredLanguageName],[0,1]);"><input class=codename name=InsuredLanguageName id="InsuredLanguageName" readonly=true elementtype=nacessary>
			<font color="red">*</font>
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>����</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="NativePlace" id="NativePlace"  onclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);"><input class=codename name=NativePlaceName id="NativePlaceName" readonly=true>
			</TD>
			<TD  class= title>�������ڵ�</TD>
			<TD  class=input >
				<Input class="common wid" name=InsuredPlace  id="InsuredPlace">
			</TD>
			<TD  class= title>����״��</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=Marriage id="Marriage"  verifyorder="2" onclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName id="MarriageName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>  
			 </TR>
		<TR class=common>
			<TD class= title>סַ</TD>
				<TD  class= input  colspan="3"  >
					<!--
					<Input class= common3 name="PostalAddress" elementtype=nacessary verify="����������ϵ��ַ|NOTNULL&LEN<=80" verifyorder='2' >
				    --> 
					<Input class= common3 name="PostalAddress" id="PostalAddress">
				</TD>
				<TD  class= title>��������</TD>
				<TD  class= input>
					<!--
					<Input class= common name="ZIPCODE" elementtype=nacessary MAXLENGTH="6" verify="����������������|NOTNULL&ZIPCODE" verifyorder='2' >
				    --> 
					<Input class="common wid" name="ZIPCODE" id="ZIPCODE" MAXLENGTH="6" >
				</TD>
		</TR>	 
		<TR class= common>
				<TD  class= title>��ϵ�绰(1)</TD>
				<TD  class= input>
					<Input class="common wid" name="Mobile" id="Mobile" verifyorder='2' >
				</TD>
				<TD  class= title>��ϵ�绰(2)</TD>
				<TD  class= input>
					<Input class="common wid" name="Phone" id="Phone" verifyorder='2' >
				</TD>          
				<TD  class= title>��������</TD>
				<TD  class= input>
				  <Input class="common wid" name="EMail" id="EMail" MAXLENGTH=40 verify="�������˵�������|LEN<=40&EMAIL" verifyorder='2' >
				</TD>
				<TD  class= title style="display:none">����绰</TD>
				<TD  class= input  style="display:none" colspan=3 >
					<Input class="common wid" name="Fax" id="Fax" verify="�������˴���绰|LEN<=15&PHONE" verifyorder='2' >
				</TD>
			</TR>    
		<TR class= common>    
			<TD  class= title>������λ</TD>
			<TD  class= input ><Input class="common wid" name="GrpName" id="GrpName" verify="�����˹�����λ|LEN<=60"  verifyorder="2"> </TD>         
			<TD  class= title>
		        ְҵ
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="WorkType" id="WorkType" value="" >
		      </TD>
		  	<TD  class= title>
		        ��ְ
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="PluralityType" id="PluralityType" value="" >
		      </TD>
		</TR>
		<TR class=common>
			<TD  class= title>ְҵ����</TD>
			<TD  class=input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="OccupationCode" id="OccupationCode" verify="��������ְҵ����|CODE:OccupationCode&NOTNULL" verifyorder='2' onclick="return onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);" ondblclick="return onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);" onkeyup="return onClickUpInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);"><input class=codename name=OccupationCodeName id="OccupationCodeName" onblur="getInsuredOpName()" elementtype=nacessary readonly=true>
				<font color="red">*</font>
				<!--<Input type="hidden" name="OccupationType"> -->
			</TD>
			<TD  class= title>ְҵ���</TD>
			<TD  class= input>
			  <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="OccupationType" id="OccupationType" readonly onkeyup="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);"><input class=codename name=OccupationTypeName id="OccupationTypeName" readonly=true>
			</TD>
		</TR>
		<TR class= common style="display:none">
			
			<TD class=title>��������	</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=LicenseType id="LicenseType" verify="�������˼�������|CODE:LicenseType" verifyorder='2' onclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName id="LicenseTypeName" readonly=true>
			</TD> 
		</TR>               
	</Table> 
     <!--#####���½������˵ĵ�ַ��¼��־�޸ķǱ�¼��־,2005-11-02�޸�####-->
  	<Div id="DivAddress" STYLE="display:none">   
      	<Table  class= common>   	
			<TR class= common>
				<TD  class= title>��ַ����</TD>                  
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredAddressNo" id="InsuredAddressNo" ondblclick="getaddresscodedata2();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1],null,null,null, 1);" ondblclick="getaddresscodedata2();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], null,null,null, 1);" onkeyup="getaddresscodedata2(); return showCodeListKeyEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], null,null,null, 1);" onfocus="getdetailaddress2();"><input class=codename name=InsuredAddressNoName id="InsuredAddressNoName" readonly=true>
				</TD>
			</TR>  
			<TR class='common' >
				<TD class="title">ͨѶ��ַ��</TD>
			</TR>
			<TR class= common>
				<TD  class= title>ʡ</TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="������ʡ��|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredProvince"  ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName readonly=true elementtype=nacessary>
				    --> 
   					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredProvince" id="InsuredProvince" onclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName id="InsuredProvinceName" onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" readonly=true>
				</TD>  
				<TD  class= title>�� </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="�����˳���|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredCity"  ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredCity" id="InsuredCity" onclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename'],1);"><input class=codename name=InsuredCityName id="InsuredCityName" onblur="getAddressName('city','InsuredCity','InsuredCityName');" readonly=true>
				</TD>  
				<TD  class= title>��/�� </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="��������/��|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredDistrict"  ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredDistrict" id="InsuredDistrict" onclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],1,'240');"><input class=codename onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" name=InsuredDistrictName id="InsuredDistrictName" readonly=true >
				</TD>  
			</TR>        
			<TR class= common>
				
				<!--TD  class= title>��ϵ�绰</TD>
				<TD  class= input><input class= common name="Phone"  verify="�������˼�ͥ�绰|LEN<=18" ></TD-->            
			</TR>
			     
			<TR class= common>  
				<TD class=title>סլ�绰</TD>
				<TD class=input>
					<Input name="HomePhone" id="HomePhone" class="common wid" verify="��������סլ�绰|LEN<=15&PHONE" verifyorder='2'>
				</TD>			
				
				
			</TR>  
          <!--TR  class= common>
            <TD  class= title>
              ��λ��ַ
            </TD>
            <TD  class= input  >
              <Input class= common name="GrpAddress"  >
            </TD>
            <TD  class= title>
              ��λ��������
            </TD>
            <TD  class= input>
              <Input class= common name="GrpZipCode"  >
            </TD>
          </TR>	 
  	<TRclass=common>
  		<TD class=title>
  		  ��ͥ��ַ
  		</TD>
  		<TD class=input>
  		  <Input name="HomeAddress" class= common >
  		</TD>
  		<TD class=title>
  		  ��ͥ�ʱ�
  		</TD>
  		<TD class=input>
  		  <Input name="HomeZipCode" class=common>
  		</TD>
         </TR-->			             
          <!--TR  class= common>        
            <TD  class= title>
              ��ϵ��ַѡ��
            </TD>
            <TD  class= input>
              <Input class="code" name="CheckPostalAddress"  value='3' CodeData="0|^1|��λ��ַ^2|��ͥ��ַ^3|����"  ondblclick="return showCodeListEx('CheckPostalAddress',[this]);" onkeyup="return showCodeListKeyEx('CheckPostalAddress',[this]);">                     
            </TD>   
            <TD  class= title colspan=2>
              <font color=red>(����д��λ��ַ���ͥ��ַ��ʵ������)</font>
            </TD> 
          </TR--> 		             	       
          <!--TR  class= common>
          </TR-->
    	</Table>
	</Div>    
</Div>

  <Table class= common>
      <TRclass= common>
          <TD  class= title>
              <Div id="divContPlan" style="display:none" >
  	            <Table class= common>
  		            <TRclass= common>
  			            <TD  class= title>
                              ���ռƻ�
                      </TD>
                      <TD  class= input>
                          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="ContPlanCode" id="ContPlanCode" onclick="showCodeListEx('ContPlanCode',[this],[0],null,null,null, true);" ondblclick="showCodeListEx('ContPlanCode',[this],[0],null,null,null, true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],null,null,null, true);">
                      </TD>
  		
                   </TR>
  	            </Table>
              </Div>
          </TD>
          <TD  class= title>
              <Div id="divExecuteCom" style="display:none" >
  	            <Table class= common>
  		            <TRclass= common>
  			            <TD  class= title>
                              �������
                          </TD>
                          <TD  class= input>
                              <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="ExecuteCom" id="ExecuteCom" onclick="showCodeListEx('ExecuteCom',[this],[0],null,null,null, true);" ondblclick="showCodeListEx('ExecuteCom',[this],[0],null,null,null, true);" onkeyup="showCodeListKeyEx('ExecuteCom',[this],[0],null,null,null, true);">
                          </TD>
  		            </TR>
  	            </Table>
              </Div>
          </TD>
          <TD  class= title>
          </TD>		
      </TR>
  </Table>
  <Div id="DivClaim" STYLE="display:none"> 
      <Table  class= common>         
      <TR class= common>  
              <TD  class= title>
                  ������ʻ�
              </TD>                  
              <TD  class= input>
                  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="AccountNo" id="AccountNo" CodeData="0|^1|�ɷ��ʻ�^2|����" onClick="showCodeListEx('AccountNo',[this]);" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
              </TD>
      </TR> 
            <TRCLASS=common>
  	    <TD CLASS=title  >
  	      �������� 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=BankCode id="BankCode" VALUE="" CLASS="code" MAXLENGTH=20 verify="������|CODE:bank" onclick="return showCodeList('bank',[this]);" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
  	    </TD>
  	    <TD CLASS=title >
  	      ���� 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=AccName id="AccName" VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20" >
  	    </TD>
              <TD CLASS=title width="109" >
  	      �˺�</TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=BankAccNo id="BankAccNo" class="code" VALUE="" CLASS=common onclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" ondblclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="�����ʺ�|LEN<=40" onfocus="getdetail();">
  	    </TD>
  	  </TR>   
      </Table>          
  </Div>
  <Div id="divSalary" style="display: ">
  	<!--table class=common>
  		<TRclass=common>
  			<TD class=title>
  			  ��˾ʱ��
  			</TD>
  			<TD class=input>
  			  <Input name="JoinCompanyDate" class="coolDatePicker" dateFormat="short">
  			</TD>
  			<TD class=title>
  			  ���ʣ�Ԫ��
  			</TD>
  			<TD class=input>
  			  <Input name="Salary" class=common>
  			</TD>
  			<TD class=title>
  			  ְλ
  			</TD>
  			<TD class=input>
  			  <Input name="Position" class=common>
  			</TD>		
  		</TR>
  	</table-->
  </Div>
  <Div id="divLCInsuredPerson" style="display:none">
  	<!--table class=common>				
  		</TR>
  		<TRclass=common>
  			<TD class=title>
  			  ��ͥ����
  			</TD>
  			<TD class=input>
  			  <Input name="HomeFax" >
  			</TD>
  			<TD class=title>
  			  ��λ����
  			</TD>
  			<TD class=input>
  			  <Input name="GrpFax" class=common>
  			</TD>
  			<TD class=title>
  			  ��ϵ����
  			</TD>
  			<TD class=input>
  			  <Input name="Fax" class=common>
  			</TD>		
  		</TR>	
          <TR class= common>
         </TR> 			
  	</table-->
  	  <Div id="DivGrpNoname" STYLE="display:none">        
      <Table  class= common>        
          <TRclass= common>
            <TD CLASS=title>
              �������ͱ��
            </TD>
            <TD CLASS=input COLSPAN=1>
              <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=PolTypeFlag id="PolTypeFlag" VALUE="0" CLASS=code onclick="showCodeList('PolTypeFlag', [this]);" ondblclick="showCodeList('PolTypeFlag', [this]);" onkeyup="showCodeListKey('PolTypeFlag', [this]);" verify="�������ͱ��" >
            </TD>        
              <TD CLASS=title>
                  ����������
              </TD>
              <TD CLASS=input COLSPAN=1>
                  <Input NAME=InsuredPeoples id="InsuredPeoples" VALUE="" readonly=true class="common wid" verify="����������|NUM" >
              </TD>
              <td CLASS=title></td>
              <td CLASS=input></td>
  	    </TR>  
        </Table>
  </Div>

</Div>
</div>