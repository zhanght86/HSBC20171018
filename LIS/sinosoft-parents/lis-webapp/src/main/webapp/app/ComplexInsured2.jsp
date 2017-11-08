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
	<!-- 被保人信息部分 -->
	<Table>
		<tr>
			<TD class="common">
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);">
			</TD>
			<TD class="titleImg">
				<input class="mulinetitle" value="被保险人资料"  name="InsuredSequencename" id="InsuredSequencename" readonly >（客户号：<input class="common" name="InsuredNo" id="InsuredNo" value="">
				<a href="javascript:void(0)" id="InsuredButBack" style="font-weight:normal;" class=button onclick="queryInsuredNo();">查  询</a> 首次投保客户无需填写客户号）
				<Div  id= "divSamePerson" style="display:none">
					<font color="red">如投保人为被保险人本人，可免填本栏，请选择<input type="checkbox" name="SamePersonFlag" id="SamePersonFlag" onclick="isSamePerson();"></font>
				</Div>
			</TD>
		</tr>
	</Table>
</Div>

<div class="maxbox">
<Div id="DivRelation1" style="display:none">
	<Table  class="common">  
		<TR class="common">
			<TD class="title">VIP客户</TD>
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
			<TD  class="title">客户内部号码</TD>             
			<TD class="input">
				<!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SequenceNo" id="SequenceNo" verify="客户内部号码|CODE:SequenceNo&NOTNULL&NUM" verifyorder='2' onclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" ondblclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKey('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" id="SequenceNoName" elementtype=nacessary readonly="true">
				<font color="red">*</font>
			</TD>              
			<TD  class="title">与第一被保险人关系</TD>             
			<TD  class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToMainInsured" id="RelationToMainInsured" verify="与第一被保险人关系|NOTNULL&CODE:Relation" verifyorder='2' onclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" elementtype=nacessary readonly="readonly"  >
				<font color="red">*</font>
			</TD>  
			<TD  class= title>与投保人关系</TD>
			<TD  class= input>
			<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToAppnt" id="RelationToAppnt" verify="与投保人关系|CODE:Relation" verifyorder='2' onclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName" id="RelationToAppntName" readonly="readonly" >
			</TD> 
		</TR> 
	</Table> 
  


<Div id="DivLCInsured" style="display: ">
	<Table  class= common>
		<TR  class= common>        
			<TD  class= title>姓</TD>
			<TD  class=input >
				<Input class="common wid"  name=InsuredLastName id="InsuredLastName" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="被保险人姓|NOTNUlL&LEN<=20"  verifyorder="2">
				<font color="red">*</font>
			</TD>
			<TD  class= title>名</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=InsuredFirstName id="InsuredFirstName" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="被保险人名|NOTNUlL&LEN<=20"  verifyorder="2" onblur="getallinfo();">
				<font color="red">*</font>
			</TD>
			<TD  class= title>姓名</TD>
			<TD  class= input>
				<Input class="common wid" name=Name id="Name" elementtype=nacessary verify="被保险人姓名|LEN<=20" verifyorder='2' readonly>
				<font color="red">*</font>
			</TD>
	    </TR>
	    <TR  class= common>        
			<TD  class= title>英文姓</TD>
			<TD  class=input >
				<Input class="common wid" name=InsuredLastNameEn id="InsuredLastNameEn"  verify="被保险人英文姓|LEN<=20"  verifyorder="2">
			</TD>
			<TD  class= title>英文名</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=InsuredFirstNameEn id="InsuredFirstNameEn" verify="被保险人英文名|LEN<=20"  verifyorder="2">
			</TD>
			<TD  class= title>拼音姓</TD>
			<TD nowrap class= input>
				<Input class="common wid" name=InsuredLastNamePY id="InsuredLastNamePY" verify="被保险人拼音姓|LEN<=20"  verifyorder="2">
			</TD>
	    </TR>
	    <TR  class= common>        
			<TD  class= title>拼音名</TD>
			<TD  class=input >
				<Input class="common wid" name=InsuredFirstNamePY id="InsuredFirstNamePY" verify="被保险人拼音名|LEN<=20"  verifyorder="2">
			</TD>
			<TD  class= title>性别</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=Sex id="Sex" verify="被保险人性别|NOTNULL&CODE:Sex" verifyorder='2' onclick="return showCodeList('Sex',[this,SexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName id="SexName" readonly="readonly" elementtype=nacessary>
				<font color="red">*</font>
			</TD>
			<TD  class= title>出生日期</TD>
			<TD  class= input>
				<Input class="coolDatePicker" onClick="laydate({elem: '#Birthday'});" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge2();" verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>                                                                                                               
					</TD>
		</TR>
		<TR class= common>
			<TD CLASS=title>被保人年龄</TD>
			<TD CLASS=input COLSPAN=1>
				<Input NAME=InsuredAppAge id="InsuredAppAge" verify="被保人年龄|value>=0" verifyorder="2" VALUE="" readonly=true class="common wid" verify="被保人年龄|NUM" >
			</TD>
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="IDType" id="IDType" verify="被保险人证件类型|CODE:IDType" verifyorder='2' onclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" aonblur="getallinfo();" ><input class=codename name=IDTypeName id="IDTypeName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>
			<TD  class= title>证件号码</TD>
			<TD  class= input>
				<Input class="common wid" name="IDNo" id="IDNo" onblur="checkidtype2();getBirthdaySexByIDNo2(this.value);getAge2();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="被保险人证件号码|LEN<=20" verifyorder='2'>
				<font color="red">*</font>
			</TD>             
			
		</TR>
		<TR>
			<TD class= title>证件号码有效期至</TD>
			<TD class= input>
				<input class="common wid" dateFormat="short" name="IDPeriodOfValidity" id="IDPeriodOfValidity" verify="证件号码有效期|DATE" verifyorder="1">
			</TD>
			<TD class= title>是否拥有公费医疗、社会医疗保险</TD>
			<TD class= input>
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="SocialInsuFlag" id="SocialInsuFlag"
            				   CodeData="0|^0|否^1|是"
            				   onClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);"
            				   ondblClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('SocialInsuFlag',[this],[0,1]);">
			</TD>
			<TD class= title>语言</TD>
			<TD class= input>
			<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredLanguage" id="InsuredLanguage" verify="语言|CODE:language" verifyorder="1" onclick="return showCodeList('language',[this,InsuredLanguageName],[0,1]);" ondblclick="return showCodeList('language',[this,InsuredLanguageName],[0,1]);" onkeyup="return showCodeListKey('language',[this,InsuredLanguageName],[0,1]);"><input class=codename name=InsuredLanguageName id="InsuredLanguageName" readonly=true elementtype=nacessary>
			<font color="red">*</font>
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>国籍</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="NativePlace" id="NativePlace"  onclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);"><input class=codename name=NativePlaceName id="NativePlaceName" readonly=true>
			</TD>
			<TD  class= title>户籍所在地</TD>
			<TD  class=input >
				<Input class="common wid" name=InsuredPlace  id="InsuredPlace">
			</TD>
			<TD  class= title>婚姻状况</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=Marriage id="Marriage"  verifyorder="2" onclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName id="MarriageName" readonly=true elementtype=nacessary>
				<font color="red">*</font>
			</TD>  
			 </TR>
		<TR class=common>
			<TD class= title>住址</TD>
				<TD  class= input  colspan="3"  >
					<!--
					<Input class= common3 name="PostalAddress" elementtype=nacessary verify="被保险人联系地址|NOTNULL&LEN<=80" verifyorder='2' >
				    --> 
					<Input class= common3 name="PostalAddress" id="PostalAddress">
				</TD>
				<TD  class= title>邮政编码</TD>
				<TD  class= input>
					<!--
					<Input class= common name="ZIPCODE" elementtype=nacessary MAXLENGTH="6" verify="被保险人邮政编码|NOTNULL&ZIPCODE" verifyorder='2' >
				    --> 
					<Input class="common wid" name="ZIPCODE" id="ZIPCODE" MAXLENGTH="6" >
				</TD>
		</TR>	 
		<TR class= common>
				<TD  class= title>联系电话(1)</TD>
				<TD  class= input>
					<Input class="common wid" name="Mobile" id="Mobile" verifyorder='2' >
				</TD>
				<TD  class= title>联系电话(2)</TD>
				<TD  class= input>
					<Input class="common wid" name="Phone" id="Phone" verifyorder='2' >
				</TD>          
				<TD  class= title>电子邮箱</TD>
				<TD  class= input>
				  <Input class="common wid" name="EMail" id="EMail" MAXLENGTH=40 verify="被保险人电子邮箱|LEN<=40&EMAIL" verifyorder='2' >
				</TD>
				<TD  class= title style="display:none">传真电话</TD>
				<TD  class= input  style="display:none" colspan=3 >
					<Input class="common wid" name="Fax" id="Fax" verify="被保险人传真电话|LEN<=15&PHONE" verifyorder='2' >
				</TD>
			</TR>    
		<TR class= common>    
			<TD  class= title>工作单位</TD>
			<TD  class= input ><Input class="common wid" name="GrpName" id="GrpName" verify="被保人工作单位|LEN<=60"  verifyorder="2"> </TD>         
			<TD  class= title>
		        职业
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="WorkType" id="WorkType" value="" >
		      </TD>
		  	<TD  class= title>
		        兼职
		      </TD>
		      <TD  class= input>
		        <Input class="common wid" name="PluralityType" id="PluralityType" value="" >
		      </TD>
		</TR>
		<TR class=common>
			<TD  class= title>职业编码</TD>
			<TD  class=input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="OccupationCode" id="OccupationCode" verify="被保险人职业代码|CODE:OccupationCode&NOTNULL" verifyorder='2' onclick="return onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);" ondblclick="return onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);" onkeyup="return onClickUpInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName);"><input class=codename name=OccupationCodeName id="OccupationCodeName" onblur="getInsuredOpName()" elementtype=nacessary readonly=true>
				<font color="red">*</font>
				<!--<Input type="hidden" name="OccupationType"> -->
			</TD>
			<TD  class= title>职业类别</TD>
			<TD  class= input>
			  <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="OccupationType" id="OccupationType" readonly onkeyup="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);"><input class=codename name=OccupationTypeName id="OccupationTypeName" readonly=true>
			</TD>
		</TR>
		<TR class= common style="display:none">
			
			<TD class=title>驾照类型	</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=LicenseType id="LicenseType" verify="被保险人驾照类型|CODE:LicenseType" verifyorder='2' onclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName id="LicenseTypeName" readonly=true>
			</TD> 
		</TR>               
	</Table> 
     <!--#####以下将被保人的地址必录标志修改非必录标志,2005-11-02修改####-->
  	<Div id="DivAddress" STYLE="display:none">   
      	<Table  class= common>   	
			<TR class= common>
				<TD  class= title>地址代码</TD>                  
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredAddressNo" id="InsuredAddressNo" ondblclick="getaddresscodedata2();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1],null,null,null, 1);" ondblclick="getaddresscodedata2();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], null,null,null, 1);" onkeyup="getaddresscodedata2(); return showCodeListKeyEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], null,null,null, 1);" onfocus="getdetailaddress2();"><input class=codename name=InsuredAddressNoName id="InsuredAddressNoName" readonly=true>
				</TD>
			</TR>  
			<TR class='common' >
				<TD class="title">通讯地址：</TD>
			</TR>
			<TR class= common>
				<TD  class= title>省</TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="被保人省份|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredProvince"  ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName readonly=true elementtype=nacessary>
				    --> 
   					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredProvince" id="InsuredProvince" onclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName id="InsuredProvinceName" onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" readonly=true>
				</TD>  
				<TD  class= title>市 </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="被保人城市|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredCity"  ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredCity" id="InsuredCity" onclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename'],1);"><input class=codename name=InsuredCityName id="InsuredCityName" onblur="getAddressName('city','InsuredCity','InsuredCityName');" readonly=true>
				</TD>  
				<TD  class= title>区/县 </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="被保人区/县|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredDistrict"  ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredDistrict" id="InsuredDistrict" onclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],1,'240');"><input class=codename onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" name=InsuredDistrictName id="InsuredDistrictName" readonly=true >
				</TD>  
			</TR>        
			<TR class= common>
				
				<!--TD  class= title>联系电话</TD>
				<TD  class= input><input class= common name="Phone"  verify="被保险人家庭电话|LEN<=18" ></TD-->            
			</TR>
			     
			<TR class= common>  
				<TD class=title>住宅电话</TD>
				<TD class=input>
					<Input name="HomePhone" id="HomePhone" class="common wid" verify="被保险人住宅电话|LEN<=15&PHONE" verifyorder='2'>
				</TD>			
				
				
			</TR>  
          <!--TR  class= common>
            <TD  class= title>
              单位地址
            </TD>
            <TD  class= input  >
              <Input class= common name="GrpAddress"  >
            </TD>
            <TD  class= title>
              单位邮政编码
            </TD>
            <TD  class= input>
              <Input class= common name="GrpZipCode"  >
            </TD>
          </TR>	 
  	<TRclass=common>
  		<TD class=title>
  		  家庭地址
  		</TD>
  		<TD class=input>
  		  <Input name="HomeAddress" class= common >
  		</TD>
  		<TD class=title>
  		  家庭邮编
  		</TD>
  		<TD class=input>
  		  <Input name="HomeZipCode" class=common>
  		</TD>
         </TR-->			             
          <!--TR  class= common>        
            <TD  class= title>
              联系地址选择
            </TD>
            <TD  class= input>
              <Input class="code" name="CheckPostalAddress"  value='3' CodeData="0|^1|单位地址^2|家庭地址^3|其它"  ondblclick="return showCodeListEx('CheckPostalAddress',[this]);" onkeyup="return showCodeListKeyEx('CheckPostalAddress',[this]);">                     
            </TD>   
            <TD  class= title colspan=2>
              <font color=red>(在填写单位地址或家庭地址后实现速填)</font>
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
                              保险计划
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
                              处理机构
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
                  理赔金帐户
              </TD>                  
              <TD  class= input>
                  <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="AccountNo" id="AccountNo" CodeData="0|^1|缴费帐户^2|其它" onClick="showCodeListEx('AccountNo',[this]);" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
              </TD>
      </TR> 
            <TRCLASS=common>
  	    <TD CLASS=title  >
  	      开户银行 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=BankCode id="BankCode" VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|CODE:bank" onclick="return showCodeList('bank',[this]);" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
  	    </TD>
  	    <TD CLASS=title >
  	      户名 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=AccName id="AccName" VALUE="" class="common wid" MAXLENGTH=20 verify="户名|LEN<=20" >
  	    </TD>
              <TD CLASS=title width="109" >
  	      账号</TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=BankAccNo id="BankAccNo" class="code" VALUE="" CLASS=common onclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" ondblclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|LEN<=40" onfocus="getdetail();">
  	    </TD>
  	  </TR>   
      </Table>          
  </Div>
  <Div id="divSalary" style="display: ">
  	<!--table class=common>
  		<TRclass=common>
  			<TD class=title>
  			  入司时间
  			</TD>
  			<TD class=input>
  			  <Input name="JoinCompanyDate" class="coolDatePicker" dateFormat="short">
  			</TD>
  			<TD class=title>
  			  工资（元）
  			</TD>
  			<TD class=input>
  			  <Input name="Salary" class=common>
  			</TD>
  			<TD class=title>
  			  职位
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
  			  家庭传真
  			</TD>
  			<TD class=input>
  			  <Input name="HomeFax" >
  			</TD>
  			<TD class=title>
  			  单位传真
  			</TD>
  			<TD class=input>
  			  <Input name="GrpFax" class=common>
  			</TD>
  			<TD class=title>
  			  联系传真
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
              保单类型标记
            </TD>
            <TD CLASS=input COLSPAN=1>
              <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=PolTypeFlag id="PolTypeFlag" VALUE="0" CLASS=code onclick="showCodeList('PolTypeFlag', [this]);" ondblclick="showCodeList('PolTypeFlag', [this]);" onkeyup="showCodeListKey('PolTypeFlag', [this]);" verify="保单类型标记" >
            </TD>        
              <TD CLASS=title>
                  被保人人数
              </TD>
              <TD CLASS=input COLSPAN=1>
                  <Input NAME=InsuredPeoples id="InsuredPeoples" VALUE="" readonly=true class="common wid" verify="被保人人数|NUM" >
              </TD>
              <td CLASS=title></td>
              <td CLASS=input></td>
  	    </TR>  
        </Table>
  </Div>

</Div>
</div>