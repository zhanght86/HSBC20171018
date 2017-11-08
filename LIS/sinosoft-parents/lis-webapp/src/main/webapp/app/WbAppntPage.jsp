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
<!-- 投保人信息部分 -->
	<Table>
		<TR>
			<TD class="common">
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
			</TD>
			<TD class= titleImg>
				投保人信息（客户号：<Input class="common"  name=AppntNo id="AppntNo" >
				<INPUT id="butBack" VALUE="查  询" TYPE=button class= cssButton onclick="queryAppntNo();">
				首次投保客户无需填写客户号）
			</TD>
		</TR>
	</Table>
    
</DIV>

<DIV id=DivLCAppntInd class="maxbox" STYLE="display:">
    
	<Table  class= common>
		<TR class="common" STYLE="display:none">
			<TD class="title">VIP客户</TD>
			<TD class="input">
				<input type="text" name="AppntVIPValue" id="AppntVIPValue" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " class="codeno" readonly="readonly" onclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname" class="codename" readonly="readonly">
			</TD>
		</TR>
	    <TR  class= common>        
			<TD  class= title>姓名</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntName id="AppntName"  onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="投保人姓名|NOTNUlL&LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>性别</TD>
			<TD nowrap class= input>
				<Input class="codeno" name=AppntSex id="AppntSex" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="投保人性别|NOTNUlL&CODE:Sex"  verifyorder="1" onclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName id="AppntSexName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>出生日期</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" elementtype=nacessary onblur="checkappntbirthday();getAge();" elementtype=nacessary name="AppntBirthday" id="AppntBirthday" verify="投保人出生日期|NOTNUlL&DATE"  verifyorder="1" >
			</TD>
	    </TR>
    
		<TR  class= common>			
			<TD  class= title>系被保人的</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntRelationToInsured" id="AppntRelationToInsured" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="与被保人关系|CODE:Relation" verifyorder='1' onclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToInsuredName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToInsuredName],[0,1]);"><Input class=codename name=RelationToInsuredName id="RelationToInsuredName" readonly=true elementtype=nacessary>
			</TD> 
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntIDType" id="AppntIDType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  verify="投保人证件类型|CODE:IDType" verifyorder="1" onclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName id="AppntIDTypeName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>证件号码</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntIDNo" id="AppntIDNo" elementtype=nacessary verify="投保人证件号码|LEN<=20"  verifyorder="1" onblur="checkidtype();if(LoadFlag=='1')confirmSecondInput(this,'onblur');">
			</TD>

		</TR>
		<TR  class= common>
			<TD  class= title>有效期至</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntIDEndDate" id="AppntIDEndDate" >
			</TD>
			<TD  class= title>投保人年龄</TD>
			<TD  class= input>
				<input class="common wid" name="AppntAge" id="AppntAge" verify="投保人年龄|value>0" verifyorder="1" value="" readonly="readonly">
			</TD>
			<TD  class= title>国籍</TD>
			<TD  class="input">
				<input class="codeno" name="AppntNativePlace" id="AppntNativePlace" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="投保人国籍|CODE:NativePlace"  verifyorder="1" onclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename name=AppntNativePlaceName id="AppntNativePlaceName" readonly=true elementtype=nacessary>
			</TD>			
		</TR>
		<TR  class= common>
			<TD  class= title>户籍所在地</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntRgtAddress id="AppntRgtAddress"  >
			</TD>
			<TD  class= title>婚姻状况</TD>
			<TD nowrap class= input>
				<Input class="codeno" name="AppntMarriage" id="AppntMarriage" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="投保人婚姻状况|CODE:Marriage"  verifyorder="1" onclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName id="AppntMarriageName" readonly=true elementtype=nacessary>
			</TD>  
			<TD class=title style="display:none">驾照类型</TD>
			<TD  class= input style="display:none">
				<input class="codeno" name="AppntLicenseType" id="AppntLicenseType" verify="驾照|CODE:LicenseType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  verifyorder="1" onclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);"><input class=codename name=AppntLicenseTypeName id="AppntLicenseTypeName" readonly=true>
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>通讯地址</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntPostalAddress" id="AppntPostalAddress" verify="投保人联系地址|LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>通讯地址邮政编码</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntZipCode" id="AppntZipCode" verify="投保人邮政编码|zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>住址</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntHomeAddress" id="AppntHomeAddress" verify="住址|LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>住址邮政编码</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntHomeZipCode" id="AppntHomeZipCode" verify="投保人住址邮政编码|zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>	
		<TR  class= common>        
			<TD  class= title>手机</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntPhone" id="AppntPhone" verify="投保人首选回访电话|LEN<=15"  verifyorder="1" aelementtype=nacessary >
			</TD>         
			<TD  class= title>固定电话</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntPhone2" id="AppntPhone2" verify="投保人其他联系电话|LEN<=15"  verifyorder="1" >
			</TD>       
			<TD  class= title>电子邮箱</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntEMail" id="AppntEMail" MAXLENGTH=40 verify="投保人电子邮箱|LEN<=40"  verifyorder="1" >
			</TD>  
		</TR>
    <TR  class= common>
    		<TD  class= title>工作单位</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntGrpName" id="AppntGrpName" verify="投保人工作单位|LEN<=60"  verifyorder="1" elementtype=nacessary >
			</TD>
			<TD  class= title>是否拥有公费医疗、社会医疗保险</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntSocialInsuFlag" id="AppntSocialInsuFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="是否拥有公费医疗、社会医疗保险|CODE:ibrmsflag" verifyorder='1' onclick="return showCodeList('ibrmsflag', [this,AppntSocialInsuFlagName],[0,1]);" ondblclick="return showCodeList('ibrmsflag', [this,AppntSocialInsuFlagName],[0,1]);" onkeyup="return showCodeListKey('ibrmsflag', [this,AppntSocialInsuFlagName],[0,1]);"><Input class=codename name=AppntSocialInsuFlagName id="AppntSocialInsuFlagName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>
		        职业
		      </TD>
		      <TD  class= input>
		        <Input class= "common wid" name="AppntWorkType" id="AppntWorkType"  value="" >
		      </TD>		  	
    </TR>
    <TR  class= common>
    	<TD  class= title>
		        兼职
		      </TD>
		      <TD  class= input>
		        <Input class= "common wid" name="AppntPluralityType" id="AppntPluralityType"  value="" >
		      </TD>
		<TD  class= title>职业编码</TD>
			<TD class= input>
				<Input class="codeno" name="AppntOccupationCode" id="AppntOccupationCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" ondblclick= "return onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);" onkeyup="return onClickUpOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName);"><input class=codename name=AppntOccupationCodeName id="AppntOccupationCodeName" readonly=true elementtype=nacessary>
			</TD>   
			<TD class=title>职业类别</TD>
			<TD class= input>
				<Input class="codeno" readonly name="AppntOccupationType" id="AppntOccupationType" onkeyup="showCodeListKey('OccupationType',[this,AppntOccupationTypeName],[0,1]);"><input class=codename name=AppntOccupationTypeName id="AppntOccupationTypeName" readonly=true>
			</TD> 	
		</TR> 
		<TR  class= common style="display:none">
			<TD  class= title>地址代码</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntAddressNo" id="AppntAddressNo" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', 1);"><input class=codename name=AddressNoName id="AddressNoName" readonly=true>
			</TD>  
		</TR>
		<TR class='common' style="display:none">
			<TD class="title">通讯地址：</TD>
		</TR>
    
		<TR  class= common style="display:none">
			<TD  class= title>省</TD>
			<TD  class= input>
				<Input class="codeno" verify="投保人省份|num&LEN=6"  verifyorder="1" name="AppntProvince" id="AppntProvince" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1],null,null,null,1);"><input class=codename name=AppntProvinceName id="AppntProvinceName" onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>市</TD>
			<TD  class= input>
				<Input class="codeno" verify="投保人城市|num&LEN=6"  verifyorder="1" name="AppntCity" id="AppntCity" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename'],1);"><input class=codename name=AppntCityName id="AppntCityName"  onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>区/县</TD>
			<TD  class= input>
				<Input class="codeno" verify="投保人区/县|num&LEN=6"  verifyorder="1" name="AppntDistrict" id="AppntDistrict" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');"><input class=codename name=AppntDistrictName id="AppntDistrictName" onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
			</TD>  
		</TR>        
		  <!--
	      <TD  class= title>户口所在地</TD>
	      <TD  class= input>
	        <Input class= common name="AppntRgtAddress" verify="投保人户口所在地|LEN<=80" >
	      </TD>
	      <TD  class= title>民族</TD>
	      <TD  class= input>
		      <input class="codeno" name="AppntNationality" verify="投保人民族|CODE:Nationality" ondblclick="return showCodeList('Nationality',[this,AppntNationalityName],[0,1]);" onkeyup="return showCodeListKey('Nationality',[this,AppntNationalityName],[0,1]);">
		      <input class=codename name=AppntNationalityName readonly=true>
	      </TD>
	      -->
		
		<!--  
		<TD  class= title>单位编码</TD>
		<TD  class= input  >
			<Input class= codeno name="AppntGrpNo"  ondblclick="getCompanyCode();return showCodeListEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);" onkeyup="getCompanyCode();return showCodeListKeyEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);">
			<input class=codename name=AppntGrpNoName readonly=true>
		</TD>        
		<TD  class= title>是否吸烟</TD>
		<TD  class= input>
			<Input class="codeno" name="AppntSmokeFlag" verify="投保人是否吸烟|CODE:YesNo" ondblclick="return showCodeList('YesNo',[this,AppntSmokeFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,AppntSmokeFlagName],[0,1]);">
			<input class=codename name=AppntSmokeFlagName readonly=true>
		</TD>
		-->
		<TR class=common style="display:none">
						<TD  class= title>传真电话</TD>
			<TD  class= input>
				<Input class= "common wid" name="AppntFax" id="AppntFax" verify="投保人传真电话|LEN<=15&PHONE"  verifyorder="1" >
			</TD>           
		</TR>
		<TR  class= common style="display:none">
			<TD  class= title>住宅电话</TD>
			<TD  class= input>
				<input class= "common wid" name="AppntHomePhone" id="AppntHomePhone" verify="投保人住宅电话|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>
		</TR>        
    <!--TR  class= common>
      <TD  class= title>
        学历
      </TD>
      <TD  class= input>
        <Input class="codeno" name="AppntDegree" verify="投保人学历|CODE:Degree" ondblclick="return showCodeList('Degree',[this,AppntDegreeName],[0,1]);" onkeyup="return showCodeListKey('Degree',[this,AppntDegreeName],[0,1]);">
        <input class=codename name=AppntDegreeName readonly=true>
      </TD>        
      <TD  class= title>
        兼职（工种）
      </TD>
      <TD  class= input>
        <Input class= common name="AppntPluralityType" verify="投保人兼职（工种）|LEN<=10" >
      </TD>       
    </TR-->       
    <!--TR  class= common>
      <TD  class= title>
        家庭地址
      </TD>
      <TD  class= input colspan=3 >
        <Input class= common3 name="AppntHomeAddress" verify="投保人家庭地址|LEN<=80" >
      </TD>
      <TD  class= title>
        家庭邮政编码
      </TD>
      <TD  class= input>
        <Input class= common name="AppntHomeZipCode" verify="投保人家庭邮政编码|zipcode" >
      </TD>
    </TR-->
    
    <!--TR  class= common>
      <TD  class= title>
        家庭传真
      </TD>
      <TD  class= input>
        <Input class= common name="AppntHomeFax" verify="投保人家庭传真|LEN<=15" >
      </TD>
    </TR-->        
    <!--TR  class= common>
      <!--TD  class= title>
        单位地址
      </TD>
      <TD  class= input colspan=3 >
        <Input class= common3 name="CompanyAddress" verify="投保人单位地址|LEN<=80" >
      </TD> 
      <TD  class= title>
        单位邮政编码
      </TD>
      <TD  class= input>
        <Input class= common name="AppntGrpZipCode" verify="投保人单位邮政编码|zipcode" >
      </TD>                   
    </TR-->        
    <!--TR  class= common>
      <TD  class= title>
        单位传真
      </TD>
      <TD  class= input>
        <Input class= common name="AppntGrpFax" verify="单位传真|LEN<=18" >
      </TD>                    
    </TR-->
    <!--TR  class= common>        
      <TD  class= title>
        联系地址选择
      </TD>
      <TD  class= input>
        <Input class="codeno" name="CheckPostalAddress"  value='3' CodeData="0|^1|单位地址^2|家庭地址^3|其它"  ondblclick="return showCodeListEx('CheckPostalAddress',[this,CheckPostalAddressName],[0,1]);" onkeyup="return showCodeListKeyEx('CheckPostalAddress',[this,CheckPostalAddressName],[0,1]);FillPostalAddress()" onfocus="FillPostalAddress();">  
        <input class=codename name=CheckPostalAddressName readonly=true>        
      </TD>   
      <TD  class= title colspan=2>
        <font color=red>(在填写单位地址或家庭地址后实现速填)</font>
      </TD> 
    </TR-->                   
    
    <!--TR  class= common>
      <TD  class= title>
        联系电话
      </TD>
      <TD  class= input>
      <input class= common name="AppntPhone" verify="投保人联系电话|LEN<=18" >
      </TD>
    </TR-->        
	</Table>
	<div id="DivLCAccount" style="display:">
		<Table>
			<TR>
				<TD class=common>
					<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
				</TD>
				<TD class= titleImg>缴费信息</TD>
			</TR>
		</Table>   
		<div id="divLCAccount1" class="maxbox" style="display:''">
        	<Table class="common">    
	          	<TR class="common">
	          		<TD class="title">首期交费形式</TD>
		            <TD class="input">
		            	<input class="codeno" name="NewPayMode" id="NewPayMode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="首期交费方式|CODE:paylocation2"  verifyorder="1" onclick="return showCodeList('paylocation2',[this,NewPayModeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('paylocation2',[this,NewPayModeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('paylocation2',[this,NewPayModeName],[0,1],null,null,null,1);"><input class="codename" name="NewPayModeName" id="NewPayModeName" readonly="readonly" elementtype=nacessary>
		            </TD>
	          	</TR>
				<TR class='common'>
					<TD class='title'>首期转帐开户行 </TD>
					<TD class='input'>
						<input  NAME=NewBankCode id="NewBankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " VALUE="" CLASS="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"  verifyorder="1" onclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1],null,null,null,1);" ><input class=codename name=AppntBankCodeName id="AppntBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >首期帐户姓名 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=NewAccName id="NewAccName" VALUE="" CLASS="common wid" MAXLENGTH=20 verify="户名|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" > 首期账号</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=NewBankAccNo id="NewBankAccNo" class="common wid" VALUE="" verifyorder="1" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');"  MAXLENGTH=25 verify="首期账号|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=AppntBankAccNoName id="AppntBankAccNoName" readonly=true>
					</TD>
				</TR>
				<TR class="common">
					<TD class="title">续期交费形式</TD>
					<TD>
						<input class="codeno" name="PayLocation" id="PayLocation" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " verify="续期交费方式|CODE:paylocation"  verifyorder="1" onclick="return showCodeList('paylocation',[this,PayLocationName],[0,1],null,null,null,1);" ondblclick="return showCodeList('paylocation',[this,PayLocationName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('paylocation',[this,PayLocationName],[0,1],null,null,null,1);"><input class="codename" name="PayLocationName" id="PayLocationName" readonly="readonly" elementtype=nacessary>
					</TD>
					<TD class="title" >首、续期账号一致</TD>
					<TD class='input'>
						<input type="checkbox" name='theSameAccount' id="theSameAccount" value="true" onclick="theSameToFirstAccount();">
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >续期转帐开户行 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=BankCode id="BankCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " VALUE="" CLASS="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"  verifyorder="1" onclick="return showCodeList('bank',[this,ReNBankCodeName],[0,1],null,null,null,1);" ondblclick="return showCodeList('bank',[this,ReNBankCodeName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('bank',[this,ReNBankCodeName],[0,1],null,null,null,1);" ><input class=codename name=ReNBankCodeName id="ReNBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >续期帐户姓名 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=AccName id="AccName" VALUE="" CLASS="common wid" MAXLENGTH=20 verify="户名|LEN<=20"  verifyorder="1" >
					</TD>
					<TD CLASS=title width="109" >续期账号</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=BankAccNo id="BankAccNo" class="common wid" VALUE="" CLASS=common verifyorder="1" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,ReNBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,ReNBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=25 verify="续期账号|LEN<=25" onfocus="getdetail();"><input type="hidden" class=codename name=ReNBankAccNoName readonly=true>
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >自动垫交标志 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="AutoPayFlag" id="AutoPayFlag" verify="自动垫交标志|CODE:autopayflag"  verifyorder="1" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onclick="return showCodeList('autopayflag',[this],[0,1],null,null,null,1);"
            				   ondblClick="return showCodeList('autopayflag',[this],[0,1],null,null,null,1);"
            				   onkeyup="return showCodeListKey('autopayflag',[this],[0,1],null,null,null,1);">	   
					</TD>
					<TD CLASS=title >自动续保标志 </TD>
					
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="RnewFlag" id="RnewFlag" verify="自动续保标志|CODE:rnewflag"  verifyorder="1" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"
                        onclick="return showCodeList('rnewflag',[this],[0,1],null,null,null,1);"
            				   ondblClick="return showCodeList('rnewflag',[this],[0,1],null,null,null,1);"
            				   onkeyup="return showCodeListKey('rnewflag',[this],[0,1],null,null,null,1);">
					</TD>
					<TD CLASS=title width="109" >保单递送方式</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="GetPolMode" id="GetPolMode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onclick="showCodeListEx('ModeSelect_2',[this],[0,1],null,null,null,1);" 
            				   CodeData="0|^0|返回银行领取^1|邮寄或专递"
            				   ondblClick="showCodeListEx('ModeSelect_2',[this],[0,1],null,null,null,1);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_2',[this],[0,1],null,null,null,1);">
					</TD>
				</TR>
				<tr class=common>
					<TD  class= title>溢交保费处理方式</TD>
				    <TD  class= input>
				    	<Input class= 'code' name=OutPayFlag id="OutPayFlag"  verify="溢交保费处理方式|CODE:outpayflag" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"  verifyorder="1" onclick="return showCodeList('outpayflag',[this],[0,1],null,null,null,1);"
		            				   		   ondblClick="return showCodeList('outpayflag',[this],[0,1],null,null,null,1);"
		            				   		   onkeyup="return showCodeListKey('outpayflag',[this],[0,1],null,null,null,1);">
				    </TD>
				     <TD CLASS=title>
				      交费方式
				    </TD>
				    <TD CLASS=input COLSPAN=1>
				      <Input NAME=PayIntv id="PayIntv" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" VALUE="" CLASS=code verify="交费方式|CODE:dspayintv"  verifyorder="1" onclick="return showCodeList('dspayintv',[this],[0],null,null,null,1);" ondblClick="return showCodeList('dspayintv',[this],[0],null,null,null,1);" onkeyup="return showCodeListKey('dspayintv',[this],[0],null,null,null,1);" verify="交费方式|notnull" >
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
      集体合同号码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=GrpContNo >
    </TD>
    <TD  class= title>
      合同号码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=ContNo >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      印刷号码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=PrtNo >
    </TD>
    <TD  class= title>
      投保人客户号码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntNo >
    </TD>-->
  </TR>
  <TR  class= common>
    <TD  class= title>
      投保人级别
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntGrade id="AppntGrade" >
    </TD>
<!--     <TD  class= title>
      投保人名称
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntName >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      投保人性别
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntSex >
    </TD>
    <TD  class= title>
      投保人出生日期
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntBirthday >
    </TD>
  </TR>-->
  <TR  class= common>
    <TD  class= title>
      投保人类型
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntType id="AppntType" >
    </TD>
<!--     <TD  class= title>
      客户地址号码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AddressNo >
    </TD>
  </TR>
 <TR  class= common>
    <TD  class= title>
      证件类型
    </TD>
    <TD  class= input>
      <Input class= 'common' name=IDType >
    </TD>
    <TD  class= title>
      证件号码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=IDNo >
    </TD>
  </TR>-->
<!--
  <TR  class= common>
    <TD  class= title>
      户口所在地
    </TD>
    <TD  class= input>
      <Input class= 'common' name=RgtAddress >
    </TD>
    <TD  class= title>
      婚姻状况
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Marriage >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      结婚日期
    </TD>
    <TD  class= input>
      <Input class= 'common' name=MarriageDate >
    </TD>
    <TD  class= title>
      健康状况
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Health >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      身高
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Stature >
    </TD>
    <TD  class= title>
      体重
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Avoirdupois >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      学历
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Degree >
    </TD>
    <TD  class= title>
      信用等级
    </TD>
    <TD  class= input>
      <Input class= 'common' name=CreditGrade >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      银行编码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=BankCode >
    </TD>
    <TD  class= title>
      银行帐号
    </TD>
    <TD  class= input>
      <Input class= 'common' name=BankAccNo >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      银行帐户名
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AccName >
    </TD>
    <TD  class= title>
      入司日期
    </TD>
    <TD  class= input>
      <Input class= 'common' name=JoinCompanyDate >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      参加工作日期
    </TD>
    <TD  class= input>
      <Input class= 'common' name=StartWorkDate >
    </TD>
    <TD  class= title>
      职位
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Position >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      工资
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Salary >
    </TD>
    <TD  class= title>
      职业类别
    </TD>
    <TD  class= input>
      <Input class= 'common' name=OccupationType >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      职业代码
    </TD>
    <TD  class= input>
      <Input class= 'common' name=OccupationCode >
    </TD>
    <TD  class= title>
      职业（工种）
    </TD>
    <TD  class= input>
      <Input class= 'common' name=WorkType >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      兼职（工种）
    </TD>
    <TD  class= input>
      <Input class= 'common' name=PluralityType >
    </TD>
    <TD  class= title>
      是否吸烟标志
    </TD>
    <TD  class= input>
      <Input class= 'common' name=SmokeFlag >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      操作员
    </TD>
    <TD  class= input>
      <Input class= 'common' name=Operator >
    </TD>
    -->
<!--    <TD  class= title>
      管理机构
    </TD>
    <TD  class= input>
      <Input class= 'common' name=ManageCom >
    </TD>-->
  </TR>
  <TR  class= common>
    <TD  class= title>
      入机日期
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntMakeDate >
    </TD>
    <TD  class= title>
      入机时间
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntMakeTime >
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      最后一次修改日期
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyDate >
    </TD>
    <TD  class= title>
      最后一次修改时间
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyTime >
    </TD>
  </TR>
</Table>
    </Div>
