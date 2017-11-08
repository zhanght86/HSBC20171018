<script lanaguage="javascript">
function getOpName()
{
	fm.AppntOccupationCodeName.value = "";
	showOneCodeName('OccupationCode','AppntOccupationCode','AppntOccupationCodeName');
	getdetailwork();
}	

function onClickOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName)
{
	showCodeList('occupationcode',[AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240');
	showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	//getdetailwork();
}

function onClickUpOccupation(AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName)
{
	showCodeListKey('occupationcode',[AppntOccupationCode,AppntOccupationCodeName,AppntOccupationType,AppntOccupationTypeName],[0,1,2,3],null,null,null,null,'240')
	showOneCodeName('occupationcode','AppntOccupationCode','AppntOccupationCodeName');
	//getdetailwork();
}

</script>

<DIV id="DivLCAppntIndButton" STYLE="display:''">
<!-- 投保人信息部分 -->
	<Table>
		<TR>
			<TD class= common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
			</TD>
			<TD class= titleImg>
				投保人信息（客户号：<Input class="common"   name=AppntNo id="AppntNo" readOnly>）
				<!-- <INPUT id="butBack" VALUE="查  询" TYPE=button class= cssButton onclick="queryAppntNo();">
				首次投保客户无需填写客户号 -->
			</TD>
		</TR>
	</Table>
</DIV>

<DIV id="DivLCAppntInd" STYLE="display:''">
	<div class="maxbox">
	<Table  class= common>
		<TR class="common" STYLE="display:none">
			<TD class=title>VIP客户</TD>
			<TD class=input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" name="AppntVIPValue" id="AppntVIPValue" class="codeno" ><input type="text" name="AppntVIPFlagname" id="AppntVIPFlagname" class="codename" readonly="readonly">
			</TD>
		</TR>
	    <TR class=common>        
			<TD class=title>姓名</TD>
			<TD class=input>
				<Input class="common wid"  name=AppntName id="AppntName" verifyorder="1" >
			</TD>
			<TD class=title> 性别 </TD>
            <TD class=input>
                 <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="AppntSex" id="AppntSex" CodeData="0|^0|男^1|女" onClick="showCodeListEx('ForSex',[this],[0,1]);" ondblClick="showCodeListEx('ForSex',[this],[0,1]);" onkeyup="showCodeListKeyEx('ForSex',[this],[0,1]);" >
            </td>
			<TD class=title>出生日期</TD>
			<TD class=input>
				<input class="common wid" name="AppntBirthday" id="AppntBirthday" verifyorder="1">
			</TD>
		</TR>
		<TR class=common> 
			<TD class=title>系被保人的</TD>
			<TD class=input>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="RelationToInsured" id="RelationToInsured" verifyorder="1" onclick="return showCodeList('Relation', [this],[0,1]);" ondblclick="return showCodeList('Relation', [this],[0,1]);" onkeyup="return showCodeListKey('Relation', [this],[0,1]);">
			</TD> 
	    </TR>
	</Table>
	<table class=common>
	    <tr class=common>
			<TD class=title>证件类型</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="AppntIDType" id="AppntIDType" CodeData="0|^0|身份证^1|护照^2|军官证^3|驾照^4|户口本^5|学生证^6|工作证^7|出生证^8|其他^9|无证件" onClick="showCodeListEx('ForIDType',[this],[0,1]);" ondblClick="showCodeListEx('ForIDType',[this],[0,1]);"  onkeyup="showCodeListKeyEx('ForIDType',[this],[0,1]);"></TD>
			<TD class=title>证件号码</TD>
			<TD class=input>
				<Input verifyorder="1" class="common wid" colspan=3 name="AppntIDNo" id="AppntIDNo" >
			</TD>
			<td class=title>有效期至</td>
			<TD class=input>
				<input class="common wid"  name="AppntIDExpDate" id="AppntIDExpDate">
			</TD>
		</TR>
	</table>
	<DIV id="Bank1" STYLE="display:none">
		<Table  class= common>
		<TR  class= common>
			<TD  class= title STYLE="display:none">投保人年龄</TD>
			<TD  class= input STYLE="display:none">
				<input class="common wid" name="AppntAge" id="AppntAge" STYLE="display:'none'">
			</TD>
			<TD class= title name="AppntNativePlace1">国籍</TD>
			<TD class=input>
				<input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="AppntNativePlace" id="AppntNativePlace" ondblclick="return showCodeList('NativePlace',[this,null],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,null],[0,1]);">
			</TD>
			<TD class= title>户口所在地</TD>
			<TD class=input>
				<Input verifyorder="1" class="common wid"  name=AppntRgtAddress id="AppntRgtAddress" >
			</TD>
			<TD  class= title>婚姻状况</TD>
			<td class=input>
			<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="AppntMarriage" id="AppntMarriage" CodeData="0|^0|未婚^1|已婚^2|离异^3|丧偶" onClick="showCodeListEx('ForMarriage',[this],[0,1]);" ondblClick="showCodeListEx('ForMarriage',[this],[0,1]);" onkeyup="showCodeListKeyEx('ForMarriage',[this],[0,1]);">
            </td>
		</TR>
		</Table>
	</DIV>
	<table class=common>
		<TR  class= common>
			<TD  class= title>通信地址</TD>
			<TD  class= input colspan=3 >
				<Input verifyorder="1" class='common3' name="AppntPostalAddress" id="AppntPostalAddress">
			</TD>
		</TR>
		<TR  class= common>
			<TD  class= title>联系电话</TD>
			<TD  class= input>
				<Input verifyorder="1" class='common wid' name="AppntMobile" id="AppntMobile">
			</TD>         
			<TD  class= title>邮政编码</TD>
			<TD  class= input>
				<Input verifyorder="1" class='common wid' name="AppntZipCode" id="AppntZipCode">
			</TD>
			<td class= title></td>
			<td class= input></td>
		</TR>
		<TR  class= common>
			<TD  class= title>
		        职业
		    </TD>
		    <TD  class= input colspan=3>
		        <Input verifyorder="1" class='common3' name="AppntWorkType" id="AppntWorkType" value="" >
		    </TD>
		    <TD  class= title>职业代码</TD>
			<TD class= input>
				<Input verifyorder="1" class='common wid' name="AppntOccupationCode" id="AppntOccupationCode">
			</TD>
		</TR>
		<!-- <Input verifyorder="1" class="code" name="AppntOccupationCode" ondblclick= "return onClickOccupation(AppntOccupationCode);" onkeyup="return onClickUpOccupation(AppntOccupationCode);" > -->
	</table>
	<DIV id="Bank2" STYLE="display:none">
		<table class=common>
		<TR  class= common>
			<TD  class= title>住址</TD>
			<TD  class= input colspan=3 >
				<Input verifyorder="1" class='common3' name="AppntHomeAddress" id="AppntHomeAddress">
			</TD>
			<TD  class= title>住址邮政编码</TD>
			<TD  class= input>
				<Input verifyorder="1" class='common wid' name="AppntHomeZipCode" id="AppntHomeZipCode" >
			</TD>
		</TR>	
		<TR  class= common>        
			<TD  class= title>其他联系电话</TD>
			<TD  class= input>
				<Input verifyorder="1" class='common wid' name="AppntCompanyPhone" id="AppntCompanyPhone">
			</TD>       
			<TD  class= title>电子邮箱</TD>
			<TD  class= input>
				<Input verifyorder="1" class='common wid' name="AppntEMail" id="AppntEMail">
			</TD>  
		</TR>
    	<TR  class= common>
    		<TD  class= title>工作单位</TD>
			<TD  class= input colspan=3 >
				<Input verifyorder="1" class='common3' name="AppntCompanyAddress" id="AppntCompanyAddress" >
			</TD>
		</tr>
		<TR  class= common>
			<TD  class= title>
		        兼职
		    </TD>
		    <TD  class= input>
		       <Input verifyorder="1" class='common wid' name="AppntPluralityType" id="AppntPluralityType" value="" >
		    </TD>
		
		</TR>
    	<TR  class= common style="display:none">   
			<TD class=title>职业类别</TD>
			<TD class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntOccupationType" id="AppntOccupationType"><input class=codename name=AppntOccupationTypeName id="AppntOccupationTypeName" readonly=true>
			</TD> 	
		</TR> 
		<TR  class= common style="display:none">
			<TD  class= title>地址代码</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="AppntAddressNo" id="AppntAddressNo"><input class=codename name=AddressNoName id="AddressNoName" readonly=true>
			</TD>  
		</TR>
		<TR class='common' style="display:none">
			<TD class="title">通讯地址：</TD>
		</TR>
    	<TR  class= common style="display:'none'">
			<TD  class= title>省</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" verify="投保人省份|num&LEN=6"   name="AppntProvince" id="AppntProvince" onclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1],null,null,null,1);"><input class=codename name=AppntProvinceName id="AppntProvinceName" onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>市</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" verify="投保人城市|num&LEN=6"   name="AppntCity" id="AppntCity" onclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename'],1);"><input class=codename name=AppntCityName  id="AppntCityName" onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>区/县</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" verify="投保人区/县|num&LEN=6"   name="AppntDistrict" id="AppntDistrict" onclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');"><input class=codename name=AppntDistrictName id="AppntDistrictName" onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
			</TD>  
		</TR>        
		<TR class=common style="display:'none'">
			<TD  class= title>传真电话</TD>
			<TD  class= input>
				<Input class='common wid' name="AppntFax" id="AppntFax" verify="投保人传真电话|LEN<=15&PHONE"   >
			</TD>           
		</TR>
		<TR  class= common style="display:'none'">
			<TD  class= title>住宅电话</TD>
			<TD  class= input>
				<input class='common wid' name="AppntHomePhone" id="AppntHomePhone" verify="投保人住宅电话|LEN<=18&PHONE"   aelementtype=nacessary >
			</TD>
		</TR>        
		</table>
	</DIV>
	</div>
  	<!-- <hr> --> 
	<div id="DivLCAccount" style="display:none">
		<Table>
			<TR>
				<TD class=common>
					<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
				</TD>
				<TD class= titleImg>缴费信息</TD>
			</TR>
		</Table>   
		<div id="divLCAccount1" style="display:''">
			<div class="maxbox">
        	<Table class="common">    
				<TR class='common'>
					<TD CLASS=title >首期帐户姓名 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntAccName id="AppntAccName" VALUE="" CLASS="common wid"  MAXLENGTH=20  >
					</TD>
					<TD CLASS=title width="109" > 首期账号</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntBankAccNo  id="AppntBankAccNo" CLASS="common wid" VALUE="" CLASS=common ><input type="hidden" class=codename name=AppntBankAccNoName id="AppntBankAccNoName" readonly=true>
					</TD>
				</TR>
				<TR class="common">
					<TD class="title">续期交费形式</TD>
					<TD CLASS=input>
						<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="SecPayMode" id="SecPayMode"><input class="codename" name="SecPayModeName" id="SecPayModeName" readonly="readonly" >
					</TD>
					<TD class="title" >首、续期账号一致</TD>
					<TD class='title'>
						<input type="checkbox" name='theSameAccount' id="theSameAccount" value="true">
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >续期转帐开户行 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" NAME=SecAppntBankCode id="SecAppntBankCode" VALUE="" CLASS="codeno" MAXLENGTH=20 verify="开户行|CODE:bank"   onclick="return showCodeList('bank',[this,SecAppntBankCodeName],[0,1]);" ondblclick="return showCodeList('bank',[this,SecAppntBankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,SecAppntBankCodeName],[0,1]);" ><input class=codename name=SecAppntBankCodeName id="SecAppntBankCodeName" readonly=true>
					</TD>
					<TD CLASS=title >续期帐户姓名 </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=SecAppntAccName  id="SecAppntAccName" VALUE="" CLASS="common wid" MAXLENGTH=20 verify="户名|LEN<=20"  >
					</TD>
					<TD CLASS=title width="109" >续期账号</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=SecAppntBankAccNo id="SecAppntBankAccNo" CLASS="common wid" VALUE="" CLASS=common ><input type="hidden" class=codename name=aa id="aa" readonly=true>
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title >自动续保标志 </TD>
					
					<TD CLASS=input COLSPAN=1  >
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="RnewFlag" id="RnewFlag"
            				   CodeData="0|^-2|非续保^-1|自动续保^0|人工续保"
            				   onClick="showCodeListEx('ModeSelect_3',[this],[0,1]);"
            				   ondblClick="showCodeListEx('ModeSelect_3',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_3',[this],[0,1]);">
					</TD>
					<TD CLASS=title width="109" >保单递送方式</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="GetPolMode" id="GetPolMode"
            				   CodeData="0|^0|返回银行领取^1|邮寄或专递"
            				   onClick="showCodeListEx('ModeSelect_2',[this],[0,1]);"
            				   ondblClick="showCodeListEx('ModeSelect_2',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_2',[this],[0,1]);">
					</TD>
				</TR>
	        </Table>
	        </div>  
      	</div> 
    </div>
</DIV>

<Div  id= "divLCAppnt1" style= "display: none">
	<Table  class= common >
  		<TR  class= common>
  		</TR>
  		<TR  class= common>
    		<TD  class= title>
      			投保人级别
    		</TD>
   		 	<TD  class= input>
      			<Input class= 'common wid' name=AppntGrade id="AppntGrade">
    		</TD>
    		<td class= title></td>
    		<td class= input></td>
    		<td class= title></td>
    		<td class= input></td>
  		<TR  class= common>
    		<TD  class= title>
      			投保人类型
    		</TD>
    		<TD  class= input>
      			<Input class= 'common wid' name=AppntType id="AppntType">
    		</TD>
  		</TR>
  		<TR  class= common>
    		<TD  class= title>
      			入机日期
    		</TD>
    		<TD  class= input>
      			<Input class= 'common wid' name=AppntMakeDate id="AppntMakeDate">
    		</TD>
    		<TD  class= title>
      			入机时间
    		</TD>
    		<TD  class= input>
      			<Input class= 'common wid' name=AppntMakeTime id="AppntMakeTime">
    		</TD>
  		</TR>
  		<TR  class= common>
    		<TD  class= title>
      			最后一次修改日期
    		</TD>
    		<TD  class= input>
      			<Input class= 'common wid' name=AppntModifyDate id="AppntModifyDate">
    		</TD>
    		<TD  class= title>
      			最后一次修改时间
    		</TD>
    		<TD  class= input>
      			<Input class= 'common wid' name=AppntModifyTime id="AppntModifyTime">
    		</TD>
  		</TR>
  		<input type=hidden name=AppntFillNo id="AppntFillNo">
	</Table>
</Div>
