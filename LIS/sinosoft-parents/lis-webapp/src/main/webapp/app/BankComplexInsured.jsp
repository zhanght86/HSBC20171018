<script lanaguage="javascript">
function getInsuredOpName()
{
	fm.OccupationCodeName.value = "";
	showOneCodeName('occupationcode',"OccupationCode","OccupationCodeName");
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

<Div id="DivLCInsuredButton" STYLE="display:''">
<!-- ��������Ϣ���� -->
	<Table>
		<TR>
			<TD class="common">
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);">
			</TD>
			<TD class="titleImg">
				<input class="mulinetitle" value="������������"  name="InsuredSequencename" id="InsuredSequencename" readonly >���ͻ��ţ�<input class="common" name="InsuredNo" id="InsuredNo" value="">
				<input id="InsuredButBack" VALUE="��  ѯ" class=cssButton type="button" onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
				<Div  id= "divSamePerson" style="display:none">
					<font color="red">
						��Ͷ����Ϊ�������˱��ˣ������������ѡ��
						<input type="checkbox" name="SamePersonFlag" id="SamePersonFlag" onclick="isSamePerson();">
					</font>
				</Div>
			</TD>
		</TR>
	</Table>
</Div>
<div class="maxbox">
<Div id="DivRelation" style="display:''">
	<Table  class= common >  
		<TR class="common" style="display:none">
			<TD class="common">VIP�ͻ�</TD>
			<TD>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" name="VIPValue1" id="VIPValue1" class="codeno" readonly="readonly" onclick="return showCodeList('vipvalue', [this,AppntVIPFlagname1], [0,1]);" ondblclick="return showCodeList('vipvalue', [this,AppntVIPFlagname1], [0,1]);" onkeyup="return showCodeListKey('vipvalue', [this,AppntVIPFlagname1], [0,1]);"><input type="text" name="AppntVIPFlagname1" id="AppntVIPFlagname1" class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR  class="common" style="display:none">   
			<TD  class="title">�ͻ��ڲ����� </TD>             
			<TD class="input">
				<!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SequenceNo" id="SequenceNo" value="1" verify="�ͻ��ڲ�����|CODE:SequenceNo&NOTNULL&NUM"  onclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" ondblclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKey('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" id="SequenceNoName" elementtype=nacessary readonly="true">
			</TD>              
			<TD  class="title">���һ�������˹�ϵ</TD>              
			<TD  class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToMainInsured" id="RelationToMainInsured" value="00" verify="���������˹�ϵ|NOTNULL&CODE:Relation"  onclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" elementtype=nacessary readonly="readonly"  >
			</TD>  
		</TR> 
	</Table> 
</Div>  


<Div id="DivLCInsured" style="display:''">
	<Table  class= common>
		<TR  class= common>        
			<TD  class= title> ����</TD>
			<TD  class= input>
				<Input class="common wid"  name=Name id="Name" onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="������������|NOTNULL&LEN<=20" verifyorder='2' oncopy="return false;" oncut="return false" onblur="getallinfo();">
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="IDType" id="IDType" value="0" verify="��������֤������|CODE:IDType" verifyorder='2' onclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName id="IDTypeName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input class="common wid" name="IDNo" id="IDNo" onblur="checkidtype2();getBirthdaySexByIDNo2(this.value);getAge2();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="��������֤������|LEN<=20" verifyorder='2' oncopy="return false;" oncut="return false">
			</TD>                                                                                                                                                                                                        
		</TR>
		<TR  class= common>
			<TD  class= title>�Ա�</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=Sex id="Sex" verify="���������Ա�|NOTNULL&CODE:Sex" verifyorder='2' onclick="return showCodeList('Sex',[this,SexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName id="SexName" readonly="readonly" elementtype=nacessary>
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<input class="coolDatePicker" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge2();" name="Birthday" id="Birthday" verify="�������˳�������|NOTNULL&DATE" verifyorder='2' >
			</TD>
			<TD CLASS=title>����������</TD>
			<TD CLASS=input >
				<Input name=InsuredAppAge id="InsuredAppAge" verify="����������|value>=0" verifyorder="2" VALUE="" readonly=true class="common wid" verify="����������|NUM" >
			</TD>
		</TR>
		<TR  class= common >     
		</TR>
		<TR  class= common style="display:none">
			<TD  class= title>����״��</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=Marriage id="Marriage" onclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName id="MarriageName" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>����</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="NativePlace" id="NativePlace" onclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);"><input class=codename name=NativePlaceName id="NativePlaceName" readonly=true>
			</TD> 
		</TR> 
		<TR  class= common style="display:none">  
			  
			<TD  class= title >��Ͷ���˹�ϵ</TD>
			<TD  class= input>
			  <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToAppnt" id="RelationToAppnt"  onclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName" id="RelationToAppntName" readonly="readonly" >
			 </TD>  
			   </TR> 
		<TR  class= common > 
			<TD  class= title>ְҵ����</TD>
			<TD  class=input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="OccupationCode" id="OccupationCode" onclick="return onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);" ondblclick="return onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);" onkeyup="return onClickUpInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);"><input class=codename  name=OccupationCodeName id="OccupationCodeName" readonly="readonly" >
			</TD>
			<TD class=title>ְҵ���</TD>
			<TD class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" readonly name="OccupationType" id="OccupationType" onkeyup="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);"><input class=codename name=OccupationTypeName id="OccupationTypeName" readonly="readonly" >
			</TD> 
			 <TD  class= title>
		        ְҵ
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="WorkType" id="WorkType" value="" >
		      </TD>
		</TR>
<!--    
    <TR  class= common>
      <TD  class= title>
        ְҵ�����֣�
      </TD>
      <TD  class= input>
        <Input class= common name="WorkType"  >
      </TD>
      <TD class=title>
      ����
      </TD>
      <TD  class= input>
        <input class="codeno" name="License" verify="����|CODE:License" CodeData="0|^1|�� ^2|��" ondblclick="return showCodeListEx('License',[this,LicenseName],[0,1]);" onkeyup="return showCodeListKeyEx('License',[this,LicenseName],[0,1]);"><input class=codename name=LicenseName readonly=true>
      </td-->
      <!--TD  class= title>
        �������ڵ�
      </TD>
      <TD  class= input>
        <Input class= common name="RgtAddress" >
      </TD>
      <TD  class= title>
        ����
      </TD>
      <TD  class= input>
      <input class="code" name="Nationality"  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
      </TD>          
    </TR>
    -->
    <!--TR class= common>
      <TD  class= title>
        ѧ��
      </TD>
      <TD  class= input>
        <Input class="code" name="Degree"  ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
      </TD>
      <TD  class= title>
        ��ְ�����֣�
      </TD>
      <TD  class= input>
        <Input class= common name="PluralityType"  >
      </TD>           
    </TR-->   
		<TR class= common style="display:none">
			<TD class=title>��������	</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=LicenseType id="LicenseType" verify="�������˼�������|CODE:LicenseType" verifyorder='2' onclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName id="LicenseTypeName" readonly=true>
			</TD>
			<!--TD  class= title>
			ְҵ���
			</TD>
			<TD  class= input>
			<Input class="code" name="OccupationType"  verify="��������ְҵ���|CODE:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
			</TD>
			<TD  class= title>
			�Ƿ�����
			</TD>
			<TD  class= input>
			<Input class="code" name="SmokeFlag"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
			</TD-->              
		</TR>               
	   	
          <TR  class= common >
              <TD  class= title STYLE = "display:none">��ַ����</TD>                  
              <TD  class= input STYLE = "display:none">
                  <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredAddressNo" id="InsuredAddressNo" onclick="getaddresscodedata2();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', 1);" ondblclick="getaddresscodedata2();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', 1);" onkeyup="getaddresscodedata2(); return showCodeListKeyEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', 1);" onfocus="getdetailaddress2();"><input class=codename name=InsuredAddressNoName id="InsuredAddressNoName" readonly=true>
              </TD>
  	      </TR>  
		<TR  class= common STYLE = "display:none">
			<TD  class= title STYLE = "display:none" >ʡ</TD>
			<TD  class= input STYLE = "display:none">
				<!--
				<Input class="codeno" verify="������ʡ��|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredProvince"  ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName readonly=true elementtype=nacessary>
			    -->
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredProvince" id="InsuredProvince" onclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName id="InsuredProvinceName" onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" readonly=true >
			</TD>  
			<TD  class= title STYLE = "display:none">��</TD>
			<TD  class= input> 
				<!--
				<Input class="codeno" verify="�����˳���|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredCity"  ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName readonly=true elementtype=nacessary>
			    -->
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredCity" id="InsuredCity" onclick="showCodeList('city',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);" ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName id="InsuredCityName" onblur="getAddressName('city','InsuredCity','InsuredCityName');" readonly=true >
			</TD>  
			<TD  class= title STYLE = "display:none"> ��/��</TD>
			<TD  class= input>
				<!--
				<Input class="codeno" verify="��������/��|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredDistrict"  ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName readonly=true elementtype=nacessary>
			    -->
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredDistrict" id="InsuredDistrict" onclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName id="InsuredDistrictName" onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" readonly=true >
			</TD>  
		</TR>        
		<TR  class= common>
			<TD class= title>ͨ�ŵ�ַ</TD>
			<TD  class= input  colspan= 3  >
				
				<Input class= "common3"  name="PostalAddress" id="PostalAddress"  elementtype=nacessary verify="����������ϵ��ַ|NOTNULL&LEN<=80" verifyorder='2' >
			   <!--
				<Input class= common3 name="PostalAddress" >
				 -->
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input>
				
				<Input class="common wid" name="ZIPCODE" id="ZIPCODE" elementtype=nacessary  verify="����������������|NOTNULL&ZIPCODE" verifyorder='2' >
			    <!--
				<Input class= common name="ZIPCODE" MAXLENGTH="6">
				-->
			</TD>       
		</TR>
        <TR  class= common style="display:none">
			<TD  class= title>�ƶ��绰</TD>
			<TD  class= input>
				<Inputclass="common wid" name="Mobile" id="Mobile" verify="���������ƶ��绰|LEN<=15&PHONE"  >
			</TD>
			<TD  class= title>סլ�绰 </TD>
			<TD  class= input>
				<Input class="common wid" name="HomePhone" id="HomePhone" verify="��������סլ�绰|LEN<=15&PHONE" >
			</TD>          
			<TD  class= title>����绰
			</TD>
			<TD  class= input colspan=3 >
				<Input class="common wid" name="Fax" id="Fax" verify="�������˴���绰|LEN<=15&PHONE"  >
			</TD>
        </TR>         
		<TR  class= common style="display:">  
			<TD class=title> ��ϵ�绰</TD> 
			<TD class=input>
				<Input name="Phone" id="Phone" class="common wid" verify="����������ϵ�绰|LEN<=15&PHONE" >
			</TD>	
			<TD>֤��������Ч����</TD>
			<TD>
				<input class="common wid" dateFormat="short" name="IDPeriodOfValidity" id="IDPeriodOfValidity" verify="֤��������Ч��|DATE" verifyorder="1">
			</TD>	
		</TR>    
		<TR  class= common style="display:none">  
			<TD  class= title>������λ</TD> 
			<TD  class= input ><Input class="common wid" name="GrpName" id="GrpName"></TD>
			<TD  class= title>��������</TD>
			<TD  class= input><Input class="common wid" name="EMail" id="EMail" verify="�������˵�������|LEN<=20&EMAIL"  ></TD>
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
  			            <TD  class= title>
                              ���ռƻ�
                      </TD>
                      <TD  class= input>
                          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="ContPlanCode" id="ContPlanCode" onclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);">
                      </TD>
  		
                      </TR>
  	            </Table>
              </Div>
          </TD>
          <TD  class= title>
              <Div id="divExecuteCom" style="display:none" >
  	            <Table class= common>
  		            <TR class= common>
  			            <TD  class= title>
                              �������
                          </TD>
                          <TD  class= input>
                              <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="ExecuteCom" id="ExecuteCom" onclick="showCodeListEx('ExecuteCom',[this],[0],'', '', '', true);" ondblclick="showCodeListEx('ExecuteCom',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ExecuteCom',[this],[0],'', '', '', true);">
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
      <TR  class= common>  
              <TD  class= title>
                  ������ʻ�
              </TD>                  
              <TD  class= input>
                  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="AccountNo" id="AccountNo" CodeData="0|^1|�ɷ��ʻ�^2|����" onClick="showCodeListEx('AccountNo',[this]);" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
              </TD>
      </TR> 
            <TR CLASS=common>
  	    <TD CLASS=title  >
  	      �������� 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" name=BankCode id="BankCode" VALUE="" CLASS="code" MAXLENGTH=20 verify="������|CODE:bank" onclick="return showCodeList('bank',[this]);" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
  	    </TD>
  	    <TD CLASS=title >
  	      ���� 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input name=AccName id="AccName" VALUE="" class="common wid" MAXLENGTH=20 verify="����|LEN<=20" >
  	    </TD>
              <TD CLASS=title width="109" >
  	      �˺�</TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" name=BankAccNo id="BankAccNo" class="code" VALUE=""  onclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" ondblclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="�����ʺ�|LEN<=40" onfocus="getdetail();">
  	    </TD>
  	  </TR>   
      </Table>          
  </Div>
  <Div id="divSalary" style="display:''">
  	<!--table class=common>
  		<TR class=common>
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
  		<TR class=common>
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
          <TR  class= common>
         </TR> 			
  	</table--> 
  	  <Div id="DivGrpNoname" STYLE="display:none">        
      <Table  class= common>        
          <TR class= common>
            <TD CLASS=title>
              �������ͱ��
            </TD>
            <TD CLASS=input COLSPAN=1>
              <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  name=PolTypeFlag id="PolTypeFlag" VALUE="0" CLASS=code onclick="showCodeList('PolTypeFlag', [this]);" ondblclick="showCodeList('PolTypeFlag', [this]);" onkeyup="showCodeListKey('PolTypeFlag', [this]);" verify="�������ͱ��" >
            </TD>        
              <TD CLASS=title>
                  ����������
              </TD>
              <TD CLASS=input COLSPAN=1>
                  <Input name=InsuredPeoples id="InsuredPeoples" VALUE="" readonly=true class="common wid" verify="����������|NUM" >
              </TD>
              <td CLASS=title></td>
              <td CLASS=input></td>
  	    </TR>  
        </Table>
  </Div>

</Div>  
</div>