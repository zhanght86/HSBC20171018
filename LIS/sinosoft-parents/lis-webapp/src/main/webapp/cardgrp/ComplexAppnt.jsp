<DIV id=DivLCAppntIndButton STYLE="display:''">
<!-- 投保人信息部分 -->
<table>
<tr>
<td>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
</td>
<td class= titleImg>
投保人信息（客户号：<Input class= common  name=AppntNo >
<INPUT id="butBack" VALUE="查  询" TYPE=button class= cssButton onclick="queryAppntNo();">
首次投保客户无需填写客户号）
</td>
</tr>
</table>

</DIV>

<DIV id=DivLCAppntInd STYLE="display:''">
 <table  class= common>
        <tr class="common">
          <td class="common">VIP客户</td>
          <td>
            <input type="text" name="AppntVIPValue" class="codeno" readonly="readonly" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagname],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagname],[0,1]);"><input type="text" name="AppntVIPFlagname" class="codename" readonly="readonly">
          </td>
        </tr>
        <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class=input >
            <Input class=common name=AppntName  onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="投保人姓名|NOTNUlL&LEN<=20"  verifyorder="1">
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="codeno" name="AppntIDType"  verify="投保人证件类型|CODE:IDType" verifyorder="1" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);"><input class=codename name=AppntIDTypeName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class= common name="AppntIDNo" elementtype=nacessary verify="投保人证件号码|LEN<=20"  verifyorder="1" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getAge();if(LoadFlag=='1')confirmSecondInput(this,'onblur');">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            性别
          </TD>
          <TD nowrap class= input>
            <Input class="codeno" name=AppntSex verify="投保人性别|NOTNUlL&CODE:Sex"  verifyorder="1" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
            <input class="common" dateFormat="short" elementtype=nacessary onblur="checkappntbirthday();getAge();" elementtype=nacessary name="AppntBirthday" verify="投保人出生日期|NOTNUlL&DATE"  verifyorder="1" >
          </TD>
          <TD  class= title>
            投保人年龄
          </TD>
          <TD  class= input>
            <input class="common" name="AppntAge" verify="投保人年龄|value>0" verifyorder="1" value="" readonly="readonly">
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD nowrap class= input>
            <Input class="codeno" name="AppntMarriage" verify="投保人婚姻状况|NOTNUlL&CODE:Marriage"  verifyorder="1" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName readonly=true elementtype=nacessary>
          </TD>          
          <TD  class= title>
            国籍
          </TD>
          <TD  class="input">
          <input class="codeno" name="AppntNativePlace" verify="投保人国籍|NOTNUlL&CODE:NativePlace"  verifyorder="1" ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);"><input class=codename name=AppntNativePlaceName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            职业编码
          </TD>
          <TD nowrap  class= input>
            <Input class="codeno" name="AppntOccupationCode" verify="投保人职业代码|NOTNUlL&CODE:OccupationCode"  verifyorder="1"  ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'240');" onfocus="getdetailwork();"><input class=codename name=AppntOccupationCodeName readonly=true elementtype=nacessary>
            <Input type="hidden" name="AppntOccupationType">
          </TD>           
        </TR>
        
        <TR  class= common>
          <td class=title>
            驾照类型
          </td>
          <td  class= input>
            <input class="codeno" name="AppntLicenseType" verify="驾照|CODE:LicenseType"  verifyorder="1" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);"><input class=codename name=AppntLicenseTypeName readonly=true>
          </td>
        </TR>
        
        <TR  class= common>
         
          <TD  class= title>
            地址代码
          </TD>
          <TD  class= input>
            <Input class="codeno" name="AppntAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename name=AddressNoName readonly=true>
          </TD>  
        </TR>
        <tr class='common'>
          <td class="title">
            通讯地址：
          </td>
        </tr>
        
        <TR  class= common>
          <TD  class= title>
            省
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人省份|NOTNUlL&num&LEN=2"  verifyorder="1" name="AppntProvince"  ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,AppntProvinceName],[0,1]);"><input class=codename name=AppntProvinceName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            市
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人城市|NOTNUlL&num&LEN=4"  verifyorder="1" name="AppntCity"  ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('City',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);"><input class=codename name=AppntCityName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            区/县
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="投保人区/县|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntDistrict"  ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('District',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');"><input class=codename name=AppntDistrictName readonly=true elementtype=nacessary>
          </TD>  
        </TR>        
          <!--TD  class= title>
            户口所在地
          </TD>
          <TD  class= input>
            <Input class= common name="AppntRgtAddress" verify="投保人户口所在地|LEN<=80" >
          </TD>
          <TD  class= title>
            民族
          </TD>
          <TD  class= input>
          <input class="codeno" name="AppntNationality" verify="投保人民族|CODE:Nationality" ondblclick="return showCodeList('Nationality',[this,AppntNationalityName],[0,1]);" onkeyup="return showCodeListKey('Nationality',[this,AppntNationalityName],[0,1]);">
          <input class=codename name=AppntNationalityName readonly=true>
          </TD-->
        <TR  class= common>
          <TD  class= title>
            街道
          </TD>
          <TD  class= input colspan=3 >
            <Input class='common3' elementtype=nacessary name="AppntPostalAddress" verify="投保人联系地址|NOTNUlL&LEN<=80"  verifyorder="1" >
          </TD>
          <TD  class= title>
            邮政编码
          </TD>
          <TD  class= input>
            <Input class='common' name="AppntZipCode" verify="投保人邮政编码|NOTNUlL&zipcode" elementtype=nacessary verifyorder="1" >
          </TD>
          <!--TD  class= title>
            单位编码
          </TD>
          <TD  class= input  >
            <Input class= codeno name="AppntGrpNo"  ondblclick="getCompanyCode();return showCodeListEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);" onkeyup="getCompanyCode();return showCodeListKeyEx('getGrpNo',[this,AppntGrpName,AppntGrpNoName],[0,1,2],'', '', '', true);">
            <input class=codename name=AppntGrpNoName readonly=true>
          </TD-->        
          <!--TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="codeno" name="AppntSmokeFlag" verify="投保人是否吸烟|CODE:YesNo" ondblclick="return showCodeList('YesNo',[this,AppntSmokeFlagName],[0,1]);" onkeyup="return showCodeListKey('YesNo',[this,AppntSmokeFlagName],[0,1]);">
            <input class=codename name=AppntSmokeFlagName readonly=true>
      </TD-->
    
        </TR>        
        <tr class=common>
          <TD  class= title>
            移动电话
          </TD>
          <TD  class= input>
            <Input class= common name="AppntMobile" verify="投保人移动电话|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
          </TD>         
          <TD  class= title>
            办公电话
          </TD>
          <TD  class= input>
            <Input class= common name="AppntGrpPhone" verify="投保人办公电话|LEN<=15&PHONE"  verifyorder="1" >
          </TD>       
          <TD  class= title>
            传真电话
          </TD>
          <TD  class= input>
            <Input class= common name="AppntFax" verify="投保人传真电话|LEN<=15&PHONE"  verifyorder="1" >
          </TD>           

        </tr>
        <TR  class= common>
          <TD  class= title>
            住宅电话
          </TD>
          <TD  class= input>
          <input class= common name="AppntHomePhone" verify="投保人住宅电话|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
          </TD>
          <TD  class= title>
            工作单位
          </TD>
          <TD  class= input>
            <Input class=common name="AppntGrpName" verify="投保人工作单位|NOTNUlL&LEN<=60"  verifyorder="1" elementtype=nacessary >
          </TD>
          <TD  class= title>
            电子邮箱
          </TD>
          <TD  class= input>
            <Input class= common name="AppntEMail" verify="投保人电子邮箱|EMAIL&LEN<=40"  verifyorder="1" >
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
  
  </table>
  <hr>
    <div id="DivLCAccount" style="display:''">
      <table>
          <tr>
              <td class=common>
                <img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
              </td>
              <td class= titleImg>
                缴费信息
              </td>
          </tr>
      </table>

      <div id="divLCAccount1" style="display:''">
        <table class="common">    
          <tr class="common">
          	 <td class="title">
              首期交费形式
            </td>
            <td>
            <input class="codeno" name="PayMode" verify="首期交费方式|NOTNUlL&CODE:paymode"  verifyorder="1" ondblclick="return showCodeList('paymode',[this,FirstPayModeName],[0,1]);" onkeyup="return showCodeListKey('paymode',[this,FirstPayModeName],[0,1]);"><input class="codename" name="FirstPayModeName" readonly="readonly" elementtype=nacessary>
            </td>
          </tr>
          <tr class='common'>
	          <TD class='title'>
	            首期转帐开户行 
	          </TD>
	          <TD class='input'>
	            <input  NAME=AppntBankCode VALUE="" CLASS="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"  verifyorder="1" ondblclick="return showCodeList('bank',[this,FirstBankCodeName],[0,1] ,null,fm.all('ManageCom').value,'a.comcode');" onkeyup="return showCodeListKey('bank',[this,FirstBankCodeName],[0,1]);" ><input class=codename name=FirstBankCodeName readonly=true>

	          </TD>
	          <TD CLASS=title >
	            首期帐户姓名 
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input  NAME=AppntAccName VALUE="" CLASS=common MAXLENGTH=20 verify="户名|LEN<=20"  verifyorder="1" >
	          </TD>
            <TD CLASS=title width="109" >
	            首期账号
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input  NAME=AppntBankAccNo class="common" VALUE="" CLASS=common onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|LEN<=40" onfocus="getdetail();"><input type="hidden" class=codename name=AppntBankAccNoName readonly=true>
	          </TD>
        
	        </TR>
          <tr class="common">
          	<td class="title">
              续期交费形式
            </td>
            <td>
            <input class="codeno" name="SecPayMode" verify="续期交费方式|NOTNUlL&CODE:paylocation"  verifyorder="1" ondblclick="return showCodeList('paylocation',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKey('paylocation',[this,PayModeName],[0,1]);"><input class="codename" name="PayModeName" readonly="readonly" elementtype=nacessary>
            </td>
            <td class="title" >
              首、续期账号一致
            </td>
            <td class='title'>
              <input type="checkbox" name='theSameAccount' value="true" onclick="theSameToFirstAccount();">
            </td>
          </tr>
        
          <TR CLASS=common>
	          <TD CLASS=title  >
	            续期转帐开户行 
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input NAME=SecAppntBankCode VALUE="" CLASS="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"  verifyorder="1" ondblclick="return showCodeList('bank',[this,AppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,AppntBankCodeName],[0,1]);" ><input class=codename name=AppntBankCodeName readonly=true>
	          </TD>
	          <TD CLASS=title >
	            续期帐户姓名 
	          </TD>
	          <TD CLASS=input COLSPAN=1  >
	            <Input NAME=SecAppntAccName VALUE="" CLASS=common MAXLENGTH=20 verify="户名|LEN<=20"  verifyorder="1" >
	          </TD>
            <TD CLASS=title width="109" >
	            续期账号
	          </td>
	          <td CLASS=input COLSPAN=1  >
	          	<!--edit by yaory-->
	            <!--<input name='SecAppntBankAccNo' class="common" onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="银行帐号|LEN<=40" verifyorder='2' onblur="getdetail();"><input type="hidden" class=codename name=aa readonly=true>-->
	            <!--<Input class= common name=SecAppntBankAccNo onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="银行帐号|LEN<=40" verifyorder='2' onblur="getdetail();">-->
	            <Input  NAME=SecAppntBankAccNo class="common" VALUE="" CLASS=common onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|LEN<=20" onfocus="getdetail();"><input type="hidden" class=codename name=aa readonly=true>
	            <!--<input name='SecAppntBankAccNo' class="common" aondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" verify="银行帐号|LEN<=40" aonfocus="getdetail();"><input type="hidden" class=codename name=aa readonly=true>-->
	        
	            <!--<Input class=common name=SecAppntBankAccNo  onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="银行帐号|LEN<=20"  verifyorder="1"><input type="hidden" class=codename name=aa readonly=true>-->
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
      <Input class= 'common' name=AppntGrade >
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
      <Input class= 'common' name=AppntType >
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
      <Input class= 'common' name=AppntModifyDate >
    </TD>
    <TD  class= title>
      最后一次修改时间
    </TD>
    <TD  class= input>
      <Input class= 'common' name=AppntModifyTime >
    </TD>
  </TR>
</table>
    </Div>
