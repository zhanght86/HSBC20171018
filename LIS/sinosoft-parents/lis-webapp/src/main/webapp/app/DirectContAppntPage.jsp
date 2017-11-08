<%
//程序名称：DirectContAppntPage.jsp
//程序功能：直销险种录入投保人信息页面
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script lanaguage="javascript">

</script>

<!-- 投保人信息部分 -->
<Table>
	<TR>
		<TD class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInfo);">
		</TD>
		<TD class= titleImg>投保人信息
			（投保人客户号：<Input class="common"  name=AppntNo id=AppntNo >
			<input id="" VALUE="查  询" TYPE=button class= cssButton onclick="queryAppnt();">
			首次投保客户无需填写客户号）
		</TD>
	</TR>
</Table>
<Div id=DivLCAppntInfo class="maxbox" STYLE="display: ">
	<Table  class= common>
		<TR class="common">
			<TD class="title">VIP客户</TD>
			<TD>
				<input name="AppntVIPFlag" id=AppntVIPFlag class="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('vipvalue',[this,AppntVIPFlagName],[0,1]);" onkeyup="return showCodeListKey('vipvalue',[this,AppntVIPFlagName],[0,1]);">
				<input name="AppntVIPFlagName" id=AppntVIPFlagName class="codename" readonly="readonly">
			</TD>
		</TR>
	    <TR  class= common>        
			<TD  class= title>姓名</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntName id=AppntName onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="投保人姓名|NOTNUlL&LEN<=20"  verifyorder="1">
			</TD>
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntIDType" id=AppntIDType  verify="投保人证件类型|CODE:IDType&NOTNUlL" verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);">
				<input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>证件号码</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntIDNo" id=AppntIDNo onblur="checkAppntIDNo();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="投保人证件号码|NOTNUlL&LEN<=20"  verifyorder="1" onblur="">
			</TD>
	    </TR>
	    <TR  class= common>
			<TD  class= title>性别</TD>
			<TD  class= input>
				<Input class="codeno" name=AppntSex id=AppntSex verify="投保人性别|NOTNUlL&CODE:Sex"  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);">
				<input class=codename name=AppntSexName id=AppntSexName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>出生日期</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" name="AppntBirthday" id=AppntBirthday elementtype=nacessary onblur="checkAppBirthDay();" elementtype=nacessary  verify="投保人出生日期|NOTNUlL&DATE"  verifyorder="1" >
			</TD>
			<TD  class= title>投保人年龄</TD>
			<TD  class= input>
				<input class="common wid" name="AppntAge" verify="投保人年龄|value>0" readonly verifyorder="1" value="" >
			</TD>
		</TR>
		<TR  class= common style="display:none">
			<TD  class= title>婚姻状况</TD>
			<TD nowrap class= input>
				<Input class="codeno" name="AppntMarriage" id=AppntMarriage style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Marriage',[this,AppntMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,AppntMarriageName],[0,1]);"><input class=codename name=AppntMarriageName id=AppntMarriageName readonly=true elementtype=nacessary>
			</TD>    
			<TD  class= title>国籍</TD>
			<TD  class="input">
				<input class="codeno" name="AppntNativePlace" id=AppntNativePlace  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('NativePlace',[this,AppntNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,AppntNativePlaceName],[0,1]);">
				<input class=codename name=AppntNativePlaceName id=AppntNativePlaceName readonly=true elementtype=nacessary>
			</TD>
			<TD class=title>驾照类型</TD>
			<TD  class= input>
				<input class="codeno" name="AppntLicenseType" id=AppntLicenseType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('LicenseType',[this,AppntLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,AppntLicenseTypeName],[0,1]);">
				<input class=codename name=AppntLicenseTypeName id=AppntLicenseTypeName readonly=true>
			</TD>        
		</TR>
		<TR  class= common>
			<TD class= title>职业编码</TD>
			<TD class= input>
				<Input class="codeno" name="AppntOccupationCode" id=AppntOccupationCode verify="投保人职业代码|NOTNUlL" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  verifyorder="1" onblur="getAppOpName();" ondblclick= "showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,1,'240');" onkeyup="showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,1,'240');" >
				<input class=codename name=AppntOccupationCodeName id=AppntOccupationCodeName readonly=true elementtype=nacessary>
			</TD>   
			<TD class=title>职业类别</TD>
			<TD class= input>
				<Input class="codeno" readonly name="AppntOccupationType" id=AppntOccupationType onkeyup="showCodeListKey('OccupationType',[this,AppntOccupationTypeName],[0,1]);">
				<input class=codename name=AppntOccupationTypeName id=AppntOccupationTypeName readonly=true>
			</TD> 
			
		</TR>
	</Table> 
  	<Table  class= common>  		
		<TR  class= common>
			<TD  class= title>地址代码</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntAddressNo" id=AppntAddressNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="getAppntAddressNoData();return showCodeListEx('GetAppntAddressNo',[this,AppntAddressNoName],[0,1], ,  ,  , 1);" onkeyup="getAppntAddressNoData();return showCodeListKeyEx('GetAppntAddressNo',[this,AppntAddressNoName],[0,1], ,  ,  , 1);">
				<input class=codename name=AppntAddressNoName id=AppntAddressNoName readonly=true>
			</TD>  
		</TR>	
    	<TR class='common'>
			<TD class="title">通讯地址：</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>省</TD>
			<TD  class= input>
				<Input class="codeno" verify="投保人省份|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntProvince" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="getAddressName('province','AppntProvince','AppntProvinceName');" ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1],null,null,null,1);">
				<input class=codename name=AppntProvinceName onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>市</TD>
			<TD  class= input>
				<Input class="codeno" verify="投保人城市|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntCity" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="getAddressName('city','AppntCity','AppntCityName');" ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename'],1);">
				<input class=codename name=AppntCityName  onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>区/县</TD>
			<TD  class= input>
				<Input class="codeno" verify="投保人区/县|NOTNUlL&num&LEN=6"  verifyorder="1" name="AppntDistrict" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');">
				<input class=codename name=AppntDistrictName onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
			</TD>  
		</TR>
		<TR  class= common>
			<TD  class= title>街道</TD>
			<TD  class= input colspan=3 >
				<Input class='common3' elementtype=nacessary name="AppntPostalAddress" verify="投保人联系地址|NOTNUlL&LEN<=80"  verifyorder="1" >
			</TD>
			<TD  class= title>邮政编码</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntZipCode" verify="投保人邮政编码|NOTNUlL&zipcode" elementtype=nacessary verifyorder="1" >
			</TD>
		</TR>
		<TR class=common>         
			<TD  class= title>办公电话</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntGrpPhone" verify="投保人办公电话|LEN<=15&PHONE"  verifyorder="1" >
			</TD>
			<TD  class= title>家庭电话</TD>
			<TD  class= input>
				<input class="common wid" name="AppntHomePhone" verify="投保人住宅电话|LEN<=18&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>   
			<TD  class= title>移动电话</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntMobile" verify="投保人移动电话|LEN<=15&PHONE"  verifyorder="1" aelementtype=nacessary >
			</TD>           
		</TR> 
		<TR  class= common style="display:none">
			<TD class=title> 联系电话</TD> 
			<TD class=input>
				<Input name="AppntPhone" class="common wid" verify="投保人联系电话|LEN<=15&PHONE" >
			</TD>  
			<TD class=title></TD>
			<TD class=input></TD>
			<TD class=title></TD>
			<TD class=input></TD>
	    </TR>	
		<TR  class= common style="display:none">
			<TD  class= title>传真电话</TD>
			<TD  class= input><Input class="common wid" name="AppntFax"></TD>  
			<TD  class= title>工作单位</TD>
			<TD  class= input><Input class="common wid" name="AppntGrpName"></TD>
			<TD  class= title>电子邮箱</TD>
			<TD  class= input><Input class="common wid" name="AppntEMail"></TD>  
	    </TR>           
	</Table>
</Div>



<Table>
	<TR>
		<TD class=common>
			<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount);">
		</TD>
		<TD class= titleImg>缴费信息</TD>
	</TR>
</Table>

<div id="divLCAccount" class=maxbox style="display: ">
	<Table class="common">    
		<TR class="common">
			<TD class="title">首期交费形式</TD>
			<TD>
				<input class="codeno" name="NewPayMode" verifyorder="1" verify="首期交费方式|NOTNUlL&CODE:paymode" style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('paymode',[this,NewPayModeName],[0,1]);" onkeyup="return showCodeListKey('paymode',[this,NewPayModeName],[0,1]);"><input class="codename" name="NewPayModeName" readonly="readonly" elementtype=nacessary>
			</TD>
		</TR>
		<TR class='common'>
			<TD class='title'>投保人开户行</TD>
			<TD class='input'>
				<input  name=NewBankCode VALUE="" class="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"  verifyorder="1" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('bank',[this,NewBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,NewBankCodeName],[0,1]);" ><input class=codename name=NewBankCodeName readonly=true>
			</TD>
			<TD class=title >帐户姓名</TD>
			<TD class=input >
				<Input  name=NewAccName VALUE="" class="common wid" MAXLENGTH=20 verify="户名|LEN<=20"  verifyorder="1" >
			</TD>
			<TD class=title >首期账号</TD>
			<TD class=input >
				<Input  name=NewBankAccNo class="common wid" VALUE="" class="common wid" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" ondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" MAXLENGTH=25 verify="首期账号|LEN<=25" >
				<input type="hidden" class=codename name=AppntBankAccNoName readonly=true>
			</TD>
		</TR>
		<TR class="common" style="display:">
			<TD class="title">续期交费形式</TD>
			<TD>
				<input class="codeno" name="SecPayMode" verifyorder="1" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="续期交费方式|NOTNUlL&CODE:continuepaymode"   ondblclick="return showCodeList('continuepaymode',[this,SecPayModeName],[0,1]);" onkeyup="return showCodeListKey('continuepaymode',[this,SecPayModeName],[0,1]);"><input class="codename" name="SecPayModeName" readonly="readonly" elementtype=nacessary>
			</TD>
			<TD class="title" >首、续期账号一致</TD>
			<TD class='title'>
				<input type="checkbox" name='theSameAccount'  onclick="SameToFirstAccount();">
			</TD>
		</TR>
		<TR class=common style="display:">
			<TD class=title>续期转帐开户行</TD>
			<TD class=input>
				<Input NAME=SecBankCode VALUE="" class="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" MAXLENGTH=20 verify="开户行|CODE:bank"   ondblclick="return showCodeList('bank',[this,SecBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,SecBankCodeName],[0,1]);" ><input class=codename name=SecBankCodeName readonly=true>
			</TD>
			<TD class=title>续期帐户姓名 </TD>
			<TD class=input>
				<Input NAME=SecAccName VALUE="" class="common wid" MAXLENGTH=20 verify="户名|LEN<=20"   >
			</TD>
			<TD class=title>续期账号</TD>
			<TD class=input>
				<input name='SecBankAccNo' style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="common wid" ondblclick="return showCodeList('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" aonkeyup="return showCodeListKey('accnum',[this,AppntBankAccNoName],[0,1],null,null,fm.AppntNo.value,'CustomerNo');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" MAXLENGTH=25 verify="续期账号|LEN<=25" aonfocus="getdetail();"><input type="hidden" class=codename name=aa readonly=true>
			</TD>
		</TR>
	</Table>   
</div> 


<Div  id= "divLCAppnt" style= "display: none">
<Table  class= common align='center' >
  <TR  class= common>
    <TD  class= title> 投保人级别</TD>
    <TD  class= input> <Input class="common wid" name=AppntGrade ></TD>
    <TD  class= title>投保人类型</TD>
    <TD  class= input><Input class="common wid" name=AppntType ></TD>
  </TR>
  <TR  class= common>
    <TD  class= title>入机日期</TD>
    <TD  class= input><Input class="common wid" name=AppntMakeDate ></TD>
    <TD  class= title>入机时间</TD>
    <TD  class= input><Input class="common wid" name=AppntMakeTime ></TD>
  </TR>
  <TR  class= common>
    <TD  class= title>最后一次修改日期</TD>
    <TD  class= input><Input class="common wid" name=AppntModifyDate ></TD>
    <TD  class= title>最后一次修改时间</TD>
    <TD  class= input><Input class="common wid" name=AppntModifyTime ></TD>
  </TR>
</Table>
</Div> 
