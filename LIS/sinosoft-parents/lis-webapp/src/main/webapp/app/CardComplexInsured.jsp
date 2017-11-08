<Div id=DivLCInsuredButton STYLE="display: ">
	<!-- 被保人信息部分 -->
	<Table>
		<TR>
			<TD class="common"><img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);"></TD>
			<TD class="titleImg">
				<input class="mulinetitle" value="被保险人资料"  name="InsuredSequencename" id=InsuredSequencename readonly >（客户号：<input class="common" name="InsuredNo" id=InsuredNo value="">
				<input id="InsuredButBack" VALUE="查  询" class=cssButton type="button" onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
				<Div  id= "divSamePerson" style="display:none">
					<font color="red">如投保人为被保险人本人，可免填本栏，请选择
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
			<TD  class= title>与投保人关系</TD>
			<TD  class= input>
				<Input class="codeno" name="RelationToAppnt" id="RelationToAppnt" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="与投保人关系|NOTNULL&CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');">
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
			<TD  class= title>姓名</TD>
			<TD  class= input>
				<Input class="wid common" name=Name id="Name" onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="被保险人姓名|NOTNULL&LEN<=20" verifyorder='2' onblur="getallinfo();">
			</TD>
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input class="codeno" name="IDType" id=IDType value="0" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保险人证件类型|CODE:IDType" verifyorder='2' ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" aonblur="getallinfo();" >
				<input class=codename name=IDTypeName id=IDTypeName readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>证件号码 </TD>
			<TD  class= input>
				<Input class="wid common" name="IDNo" id=IDNo onblur="checkidtype2();getBirthdaySexByIDNo2(this.value);getAge2();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="被保险人证件号码|LEN<=20" verifyorder='2'>
			</TD>                                                                                                                                                                                                        
		</TR>
		<TR  class= common>
			<TD  class= title>性别 </TD>
			<TD  class= input>
				<Input class="codeno" name=Sex id=Sex verify="被保险人性别|NOTNULL&CODE:Sex" style="background:url(../common/images/select--bg_03.png) no-repeat right center" verifyorder='2' ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);">
				<input class=codename name=SexName id=SexName readonly="readonly" elementtype=nacessary>
			</TD>
			<TD  class= title>出生日期 </TD>
			<TD  class= input>
				<input class="common" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge2();" name="Birthday"  verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' onClick="laydate({elem: '#Birthday'});" id="Birthday">
				<span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
			</TD>
			<TD CLASS=title>被保人年龄</TD>
			<TD CLASS=input COLSPAN=1>
				<Input NAME=InsuredAppAge id=InsuredAppAge verify="被保人年龄|value>=0" verifyorder="2" VALUE="" readonly=true CLASS="common wid" verify="被保人年龄|NUM" >
			</TD>
		</TR>
	
		<TR  class= common>
			<!--TD  class= title>婚姻状况</TD>
			<TD  class= input>
			<Input class="codeno" name=Marriage ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName readonly=true elementtype=nacessary verify="被保人婚姻状况|NOTNUlL&CODE:Marriage"  verifyorder="2">
			</TD-->  
			<TD  class= title>职业编码</TD>
			<TD  class=input>
				<Input class="codeno" name="OccupationCode" id=OccupationCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" verify="被保险人职业代码|NOTNULL&CODE:OccupationCode" verifyorder='2' ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onblur="getdetailwork2();">
				<input class=codename name=OccupationCodeName id=OccupationCodeName readonly=true elementtype=nacessary >
				<!--<Input type="hidden" name="OccupationType">--> 
			</TD>
			<TD  class= title>职业类别</TD>
			<TD  class= input>
			  <Input class="codeno" name="OccupationType" id=OccupationType readonly  onkeyup="showCodeListKey('OccupationType',[this,OccupationTypeName],[0,1]);">
			  <input class=codename name=OccupationTypeName id="OccupationTypeName" readonly=true elementtype=nacessary>
			</TD>
			<TD  class= title>工作单位</TD>
			<TD  class= input ><Input class="wid common" name="GrpName" id=GrpName></TD>	    
		</TR>               
	</Table>    
	  
	  <!--#######将被保险人地址信息必录标志调整为非必录项,修改于2005-11-02.######-->
	<Div id=DivAddress STYLE="display: ">   
		<Table  class= common>   	
			<TR  class= common>
				<TD  class= title>地址代码</TD>
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
                    通讯地址：
                    <Input type="hidden" name="InsuredProvince" id=InsuredProvince>
                    <Input type="hidden" name="InsuredCity" id=InsuredCity>
                    <Input type="hidden" name="InsuredDistrict" id=InsuredDistrict>
                    <Input type="hidden" name="InsuredProvinceName" id=InsuredProvinceName>
                    <Input type="hidden" name="InsuredCityName" id=InsuredCityName>
                    <Input type="hidden" name="InsuredDistrictName" id=InsuredDistrictName>
                </TD>
			</TR>
			<!--TR  class= common>
				<TD  class= title>省</TD>
				<TD  class= input>
 					<Input class="codeno" name="InsuredProvince"  ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName onblur="getAddressName('province','InsuredProvince','InsuredProvinceName');" readonly=true >
				</TD>  
				<TD  class= title>市</TD>
				<TD  class= input>
					<Input class="codeno" name="InsuredCity"  ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName onblur="getAddressName('city','InsuredCity','InsuredCityName');" readonly=true>
				</TD>  
				<TD  class= title>区/县 </TD>
				<TD  class= input>
					<Input class="codeno" name="InsuredDistrict"  ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName onblur="getAddressName('district','InsuredDistrict','InsuredDistrictName');" readonly=true>
				</TD>  
			</TR-->
			<TR  class= common>
				<TD class= title nowrap>通讯地址</TD>
				<TD  class= input  colspan="3" nowrap >
					<Input class= common3 name="PostalAddress" id=PostalAddress >
				</TD>
				<TD  class= title>邮政编码</TD>
				<TD  class= input>
					<!--
					<Input class= common name="ZIPCODE" elementtype=nacessary MAXLENGTH="6" verify="被保险人邮政编码|NOTNULL&ZIPCODE" verifyorder='2' >
	                -->	
					<Input class="wid common" name="ZIPCODE" id=ZIPCODE >
				</TD>           
			</TR>
			<TR  class= common>
				<TD  class= title>联系电话</TD>
    			<TD  class= input>
    				<Input class="common wid" name="InsuredPhone" id=InsuredPhone verify="被保险人联系电话|LEN<=15&PHONE" verifyorder='2'>
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
					<TD  class= title>保险计划</TD>
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
					<TD  class= title>国籍</TD>
					<TD  class= input>
						<input class="codeno" name="NativePlace" id=NativePlace style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);">
						<input class=codename name=NativePlaceName id=NativePlaceName readonly=true>
					</TD>    
					<TD class=title>住宅电话</TD>
					<TD class=input>
						<Input name="HomePhone" id=HomePhone class="common wid" verify="被保险人住宅电话|LEN<=15&PHONE" verifyorder='2'>
					</TD>			
					<TD class=title>驾照类型</TD>
					<TD  class= input>
					<input class="codeno" name=LicenseType id=LicenseType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="被保险人驾照类型|CODE:LicenseType" verifyorder='2' ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);">
					<input class=codename name=LicenseTypeName id=LicenseTypeName readonly=true>
					</TD> 
					<TD  class= title>电子邮箱</TD>
					<TD  class= input>
						<Input class="wid common" name="EMail" id=EMail verify="被保险人电子邮箱|LEN<=20&EMAIL" verifyorder='2' >
					</TD>
				</TR> 
				<TR class= common>
					<TD  class= title>处理机构</TD>
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
			<TD  class= title>理赔金帐户</TD>                  
			<TD  class= input>
				<Input class="code" name="AccountNo" id=AccountNo  CodeData="0|^1|缴费帐户^2|其它" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
			</TD>
		</TR> 
		<TR CLASS=common>
			<TD CLASS=title  >开户银行 </TD>
			<TD CLASS=input COLSPAN=1  >
				<Input NAME=BankCode id=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|CODE:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
			</TD>
			<TD CLASS=title >户名 </TD>
			<TD CLASS=input COLSPAN=1  >
				<Input NAME=AccName id=AccName VALUE="" CLASS="common wid" MAXLENGTH=20 verify="户名|LEN<=20" >
			</TD>
			<TD CLASS=title width="109" >账号</TD>
			<TD CLASS=input COLSPAN=1  >
				<Input NAME=BankAccNo id=BankAccNo class="code" VALUE="" CLASS="common wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|LEN<=40" onfocus="getdetail();">
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
				<TD  class= title>移动电话</TD>
				<TD  class= input>
					<Input class="wid common" name="Mobile" id=Mobile verify="被保险人移动电话|LEN<=15&PHONE" verifyorder='2' >
				</TD>
				<TD  class= title>办公电话</TD>
				<TD  class= input>
					<Input class="wid common" name="GrpPhone" id=GrpPhone verify="被保险人办公电话|LEN<=15&PHONE" verifyorder='2' >
				</TD>          
				<TD  class= title>传真电话</TD>
				<TD  class= input colspan=3 >
					<Input class="wid common" name="Fax" id=Fax verify="被保险人传真电话|LEN<=15&PHONE" verifyorder='2' >
				</TD>
			</TR>       
			<TR class="common">
				<TD  class="title">客户内部号码</TD>             
				<TD class="input">
					<!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
					<input name="SequenceNo" id=SequenceNo value="1">
					<Input class="codename" name="SequenceNoName" id=SequenceNoName elementtype=nacessary readonly="true">
				</TD>              
				<TD  class="title">与第一被保险人关系</TD>             
				<TD  class="input">
					<input name="RelationToMainInsured" id=RelationToMainInsured value="00"">
					<Input class="codename" name="RelationToMainInsuredName" id=RelationToMainInsuredName  elementtype=nacessary readonly="readonly"  >
				</TD>  
				<TD class="common">VIP客户</TD>
				<TD>
					<input type="text" name="VIPValue1" id=VIPValue1 value="0">
					<input type="text" name="AppntVIPFlagname1" id=AppntVIPFlagname1 class="codename" readonly="readonly">
				</TD>
			</TR>       
			<TR class= common>
				<TD CLASS=title>保单类型标记</TD>
				<TD CLASS=input COLSPAN=1>
					<Input NAME=PolTypeFlag id=PolTypeFlag VALUE="0" CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
 ondblclick="showCodeList('PolTypeFlag', [this]);" onkeyup="showCodeListKey('PolTypeFlag', [this]);" verify="保单类型标记" >
				</TD>        
				<TD CLASS=title>被保人人数</TD>
				<TD CLASS=input COLSPAN=1>
					<Input NAME=InsuredPeoples id=InsuredPeoples VALUE="" readonly=true CLASS="common wid" verify="被保人人数|NUM" >
				</TD>
				<td class=title></td>
				<td class=input></td>
			</TR>  
		</Table>
	</Div>
</Div>
</div>