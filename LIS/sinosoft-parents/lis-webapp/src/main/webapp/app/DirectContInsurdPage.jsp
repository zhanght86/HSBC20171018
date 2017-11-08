<%
//程序名称：DirectContInsurdPage.jsp
//程序功能：直销险种录入被保人信息页面
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script lanaguage="javascript">

</script>	
<!-- 被保人信息部分 -->
<Table>
	<TR>
		<TD class="common">
			<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsuredInfo);">
		</TD>
		<TD class="titleImg">被保险人资料
			（客户号：<input class="common" name="InsuredNo" value="">
			<input id="InsuredButBack" VALUE="查  询" class=cssButton type="button" onclick="queryInsured();"> 首次投保客户无需填写客户号）
		    <Div  id= "divSamePerson" style="display:none">
				<font color="red">如投保人为被保险人本人，可免填本栏，请选择<input type="checkbox" name="SamePersonFlag" onclick="IsSamePerson();"></font>
		    </Div>
		</TD>
	</TR>
</Table>
<div class=maxbox>
<Div id=DivRelation style="display: ">
	<Table  class="common">  
		<TR class="common">
			<TD class="title">VIP客户</TD>
			<TD>
				<input  name="InsuredVIPFlag" id=InsuredVIPFlag class="codeno" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('vipvalue', [this,InsuredVIPFlagName], [0,1]);" onkeyup="return showCodeListKey('vipvalue', [this,InsuredVIPFlagName], [0,1]);"><input type="text" name="InsuredVIPFlagName" class="codename" readonly="readonly">
			</TD>
		</TR>
		<TR class="common">  
			<TD  class="title">客户内部号码</TD>             
			<TD class="input">
				<input class="codeno" name="SequenceNo" id=SequenceNo verify="客户内部号码|NOTNULL&NUM&CODE:SequenceNo" verifyorder='2' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKey('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" elementtype=nacessary readonly="true">
			</TD>              
			<TD  class="title">与第一被保险人关系</TD>             
			<TD  class="input">
				<input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured verify="主被保险人关系|NOTNULL&CODE:Relation" verifyorder='2' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName"  elementtype=nacessary readonly="readonly"  >
			</TD>  
			<TD  class= title>与投保人关系</TD>
			<TD  class= input>
			<Input class="codeno" name="RelationToAppnt" id=RelationToAppnt verify="与投保人关系|NOTNULL&CODE:Relation" verifyorder='2' style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName" readonly="readonly" elementtype=nacessary>
			</TD> 
		</TR> 
	</Table> 
</Div>  


<Div id=DivLCInsuredInfo style="display: ">
	<Table  class= common>
		<TR class= common>        
			<TD  class= title>姓名</TD>
			<TD  class= input>
				<Input class="common wid" name=InsuredName id=InsuredName onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="被保险人姓名|NOTNULL&LEN<=20" verifyorder='2' onblur="getallinfo();">
			</TD>
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredIDType" id=InsuredIDType verify="被保险人证件类型|NOTNULL&CODE:IDType" verifyorder='2' ondblclick="return showCodeList('IDType',[this,InsuredIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,InsuredIDTypeName],[0,1]);" aonblur="getallinfo();" ><input class=codename name=InsuredIDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>证件号码</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredIDNo" id=InsuredIDNo onblur="checkInsuredIDNo();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="被保险人证件号码|NOTNULL&LEN<=20" verifyorder='2'>
			</TD>                                                                                                                                                                                                        
		</TR>
		<TR class= common>
			<TD  class= title>性别</TD>
			<TD  class= input>
				<Input class="codeno" name=InsuredSex id=InsuredSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="被保险人性别|NOTNULL&CODE:Sex" verifyorder='2' ondblclick="return showCodeList('Sex',[this,InsuredSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,InsuredSexName],[0,1]);">
				<input class=codename name=InsuredSexName id=InsuredSexName readonly="readonly" elementtype=nacessary>
			</TD>
			<TD  class= title>出生日期</TD>
			<TD  class= input>
				<input class="common wid" dateFormat="short" name="InsuredBirthday" id=InsuredBirthday elementtype=nacessary onblur="checkInsuredBirthday()" verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' >
			</TD>
			<TD CLASS=title>被保人年龄</TD>
			<TD CLASS=input >
				<Input NAME=InsuredAppAge id=InsuredAppAge readonly verify="被保人年龄|value>=0" verifyorder="2" VALUE=""  CLASS="common wid"  >
			</TD>
		</TR>
		<TR class= common style="display:none">
			<TD  class= title>婚姻状况</TD>
			<TD  class= input>
				<Input class="codeno" name=InsuredMarriage id=InsuredMarriage ondblclick="return showCodeList('Marriage',[this,InsuredMarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,InsuredMarriageName],[0,1]);"><input class=codename name=InsuredMarriageName readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>国籍</TD>
			<TD  class= input>
				<input class="codeno" name="InsuredNativePlace" id=InsuredNativePlace  ondblclick="return showCodeList('NativePlace',[this,InsuredNativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,InsuredNativePlaceName],[0,1]);"><input class=codename name=InsuredNativePlaceName readonly=true>
			</TD>              
			<TD class=title>驾照类型	</TD>
			<TD  class= input>
				<input class="codeno" name=InsuredLicenseType id=InsuredLicenseType verify="被保险人驾照类型|CODE:LicenseType" verifyorder='2' ondblclick="return showCodeList('LicenseType',[this,InsuredLicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,InsuredLicenseTypeName],[0,1]);"><input class=codename name=InsuredLicenseTypeName readonly=true>
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>职业编码</TD>
			<TD  class=input>
				<Input class="codeno" name="InsuredOccupationCode" id=InsuredOccupationCode verify="被保险人职业代码|CODE:OccupationCode&NOTNULL" verifyorder='2' onblur="getInsuredOpName();" ondblclick="return showCodeList('OccupationCode',[this,InsuredOccupationCodeName],[0,1],null,null,null,1,'240');"  on keyup="return showCodeListKey('OccupationCode',[this,InsuredOccupationCodeName],[0,1],null,null,null,1,'240');">
				<input class=codename name=InsuredOccupationCodeName id=InsuredOccupationCodeName elementtype=nacessary readonly=true>
			</TD>
			<TD  class= title>职业类别</TD>
			<TD  class= input>
			  <Input class="codeno" name="InsuredOccupationType" id=InsuredOccupationType readonly onkeyup="showCodeListKey('OccupationType',[this,InsuredOccupationTypeName],[0,1]);"><input class=codename name=InsuredOccupationTypeName readonly=true>
			</TD>
			
		</TR>               
	</Table> 
  	<Table  class= common>   	
		<TR class= common>
			<TD  class= title>地址代码</TD>                  
			<TD  class= input>
				<Input class="codeno" name="InsuredAddressNo" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredAddressNo ondblclick="getInsuredAddressNoData();return showCodeListEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , 1);" onkeyup="getInsuredAddressNoData(); return showCodeListKeyEx('GetInsuredAddressNo',[this,InsuredAddressNoName],[0,1], ,  ,  , 1);" >
				<input class=codename name=InsuredAddressNoName id=InsuredAddressNoName readonly=true>
			</TD>
		</TR>  
		<TR class='common'>
			<TD class="title">通讯地址：</TD>
		</TR>
		<TR class= common>
			<TD  class= title>省</TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredProvince" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredProvince  onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);">
				<input class=codename name=InsuredProvinceName id=InsuredProvinceName onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" readonly=true>
			</TD>  
			<TD  class= title>市 </TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredCity" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredCity  onblur="getAddressName('city','InsuredCity','InsuredCityName');" ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename'],1);">
				<input class=codename name=InsuredCityName id=InsuredCityName onblur="getAddressName('city','InsuredCity','InsuredCityName');" readonly=true>
			</TD>  
			<TD  class= title>区/县 </TD>
			<TD  class= input>
				<Input class="codeno" name="InsuredDistrict" style="background:url(../common/images/select--bg_03.png) no-repeat right center" id=InsuredDistrict onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],1,'240');">
				<input class=codename onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" id=InsuredDistrictName name=InsuredDistrictName readonly=true >
			</TD>  
		</TR>        
		<TR class= common>
			<TD class= title>街道</TD>
			<TD  class= input  colspan="3"  >
				<Input class="common3" name="InsuredPostalAddress" id=InsuredPostalAddress>
			</TD>
			<TD  class= title>邮政编码</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredZipCode" id=InsuredZipCode MAXLENGTH="6" >
			</TD>
		</TR>
		<TR class= common style="display:none">
			<TD  class= title>办公电话</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredGrpPhone" id=InsuredGrpPhone verify="被保险人办公电话|LEN<=15&PHONE" verifyorder='2' >
			</TD>   
			<TD class=title>家庭电话</TD>
			<TD class=input>
				<Input name="InsuredHomePhone" class="common wid" verify="被保险人家庭电话|LEN<=15&PHONE" verifyorder='2'>
			</TD>		       
			<TD  class= title>移动电话</TD>
			<TD  class= input>
				<Input class="common wid" name="InsuredMobile" id=InsuredMobile verify="被保险人移动电话|LEN<=15&PHONE" verifyorder='2' >
			</TD>
		</TR>   
		<TR  class= common >
			<TD class=title> 联系电话</TD> 
			<TD class=input>
				<Input name="InsuredPhone" id=InsuredPhone class="common wid" verify="被保人联系电话|LEN<=15&PHONE" >
			</TD>  
			<TD class=title></TD>
			<TD class=input></TD>
			<TD class=title></TD>
			<TD class=input></TD>
	    </TR>      
		<TR class= common style="display:none">
			<TD  class= title>传真电话 </TD>
			<TD  class= input><Input class="common wid" name="InsuredFax" id=InsuredFax ></TD>	
			<TD  class= title>工作单位 </TD>
			<TD  class= input ><Input class="common wid" name="InsuredGrpName" id=InsuredGrpName></TD>
			<TD  class= title>电子邮箱 </TD>
			<TD  class= input><Input class="common wid" name="InsuredEMail" id=InsuredEMail ></TD>
		</TR>  
	</Table>    
</Div>
</div>
<Table id="">
	<TR>
		<TD class=common><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivImpartInsuredInfo);"></TD>
		<TD class="titleImg">被保人告知信息</TD>
		<TD><Input type="button" class="cssButton" name="HiddenInsuredImpart" value="被保人告知信息"></TD>
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

