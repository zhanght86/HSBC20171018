	 <script lanaguage="javascript">
function getInsuredOpName()
{
	fm.OccupationCodeName.value = "";
	showOneCodeName('OccupationCode',"OccupationCode","OccupationCodeName");
	getdetailwork2();
}	
function onClickInsuredOccupation(OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName)
{
	//fm.OccupationCodeName.value = "";	
	showCodeList('occupationcode',[OccupationCode,OccupationCodeName,OccupationType,OccupationTypeName],[0,1,2,3],null,null,null,null,'240');
	showOneCodeName('OccupationCode','OccupationCode','OccupationCodeName');
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
	<!-- 被保人信息部分 -->
	<Table>
		<TR>
			<TD class="common">
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);">
			</TD>
			<TD class="titleImg">
				<input class="mulinetitle" value="被保险人资料"  name="InsuredSequencename" id="InsuredSequencename" readonly >（客户号：<input class="common" name="InsuredNo" id="InsuredNo" value="" readOnly>）
				<Div  id= "divSamePerson" style="display:'none'">
					<font color="red">如投保人为被保险人本人，可免填本栏，请选择<input type="checkbox" name="SamePersonFlag" id="SamePersonFlag" onclick="isSamePerson();"></font>
				</Div>
			</TD>
		</TR>
	</Table>
</Div>

<div class="maxbox">
<Div id="DivRelation" style="display:none">
	<Table  class="common">  
		<TR class="common">
			<TD class="title">VIP客户</TD>
			<TD class=input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" name="VIPValue1" id="VIPValue1" class="codeno"><input type="text" name="AppntVIPFlagname1" id="AppntVIPFlagname1" class="codename" readonly="readonly">
			</TD>
			<td class="title"></td>
			<td class="input"></td>
			<td class="title"></td>
			<td class="input"></td>
		</TR>
		<TR class="common">  
			<TD  class="title">与第一被保险人关系</TD>             
			<TD  class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToMainInsured" id="RelationToMainInsured"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName"  readonly="readonly"  >
			</TD>  
		</TR> 
	</Table> 
</Div>  


<Div id="DivLCInsured" style="display:''">
	<Table  class= common>
		<TR class= common>        
			<TD class= title>姓名</TD>
			<TD class= input>
				<Input verifyorder="1" class="common wid" name=Name id="Name">
			</TD>
			<TD class=title> 性别 </TD>
            <TD class=input>
            	<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="Sex" id="Sex" CodeData="0|^0|男^1|女"
            		   						   onClick="showCodeListEx('ForSex',[this],[0,1]);"
            		   						   ondblClick="showCodeListEx('ForSex',[this],[0,1]);"
            								   onkeyup="showCodeListKeyEx('ForSex',[this],[0,1]);">
            </td>
			<TD  class= title>出生日期</TD>
			<TD  class= input>
				<input verifyorder="1" class="common wid" dateFormat="short" name="Birthday" id="Birthday">                 
			</TD>
		</TR>
	</Table>
	<Table  class= common>
		<TR  class= common>	
			<TD  class= title>证件类型</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="IDType" id="IDType" CodeData="0|^0|身份证^1|护照^2|军官证^3|驾照^4|户口本^5|学生证^6|工作证^7|出生证^8|其他^9|无证件"
				onClick="showCodeListEx('ForIDType',[this],[0,1]);"
				ondblClick="showCodeListEx('ForIDType',[this],[0,1]);"
				onkeyup="showCodeListKeyEx('ForIDType',[this],[0,1]);">
			</TD>
			<td class= title></td>
			<td class= input></td>
			<td class= title></td>
			<td class= input></td>
		</TR>
		<TR>
			<TD class=title>证件号码</TD>
			<TD class= input  colspan="3">
				<Input verifyorder="1" class= common3 colspan=3 name="IDNo" id="IDNo" >
			</TD>
			<td class=title>有效期至</td>
			<TD class=input>
				<input verifyorder="1" class="common wid"  name="IDExpDate" id="IDExpDate">
			</TD>
		</TR>
	</Table>
	<Table  class= common>
		<TR class= common style="display:none">
			<TD CLASS=title>被保人年龄</TD>
			<TD CLASS=input COLSPAN=1>
				<Input NAME=InsuredAppAge id="InsuredAppAge">
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>国籍</TD>
			<TD  class= input>
				<input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="NativePlace" id="NativePlace" onclick="return showCodeList('NativePlace',[this,null],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,null],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,null],[0,1]);">
			</TD>
			<TD  class= title>户口所在地</TD>
			<TD  class=input >
				<Input verifyorder="1" class="common wid"  name=RgtAddress id="RgtAddress">
			</TD>
			<TD>婚姻状况</TD>
			<td>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="Marriage" id="Marriage" CodeData="0|^0|未婚^1|已婚^2|离异^3|丧偶" onClick="showCodeListEx('ForMarriage',[this],[0,1]);" ondblClick="showCodeListEx('ForMarriage',[this],[0,1]);" onkeyup="showCodeListKeyEx('ForMarriage',[this],[0,1]);">
            </td>
		 </TR>
		 <!-- 
		<TR  class= common>
			<TD  class= title>通信地址</TD>
			-->
		<TR class=common>
			<TD  class= input colspan=3 >
				<Input type=hidden verifyorder="1" class='common3' name="PostalAddress" id="PostalAddress">
			</TD>
		<!--
			<TD  class= title>通讯地址邮政编码</TD>
			-->
			<TD  class= input>
				<Input type=hidden verifyorder="1" class='common' name="ZipCode" id="ZipCode">
			</TD>
		</TR>
		<TR class=common>
			<TD class= title>住址</TD>
			<TD  class= input  colspan="3"  >
				<Input verifyorder="1" class= common3 name="HomeAddress" id="HomeAddress">
			</TD>
			<TD  class= title>邮政编码</TD>
			<TD  class= input> 
				<Input verifyorder="1" class="common wid"  name="HomeZipCode" id="HomeZipCode">
			</TD>
		</TR>	 
		<TR class= common>
			<TD  class= title>联系电话(1)</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="Mobile" id="Mobile">
			</TD>
			<TD  class= title>联系电话(2)</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="CompanyPhone" id="CompanyPhone">
			</TD>                    
			<TD  class= title>电子邮箱</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="EMail" id="EMail">
			</TD>  
		</TR>    
		<TR class= common>    
			<TD  class= title>工作单位</TD>
			<TD  class= input colspan=3 ><Input verifyorder="1" class='common3' name="CompanyAddress" id="CompanyAddress" verifyorder="2"> </TD>         
			<TD>是否拥有公费医疗、社会医疗保险</TD>
			<TD CLASS=input COLSPAN=1  >
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="SocialInsuFlag" id="SocialInsuFlag" CodeData="0|^0|否^1|是" onClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);" onClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);" ondblClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);" onkeyup="showCodeListKeyEx('SocialInsuFlag',[this],[0,1]);">
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>
		        职业
		    </TD>
		    <TD  class= input>
		       <Input verifyorder="1" class="common wid"  name="WorkType" id="WorkType" value="" >
		    </TD>
		  	<TD  class= title>
		        兼职
		    </TD>
		    <TD  class= input>
		       <Input verifyorder="1" class="common wid" name="PluralityType" id="PluralityType" value="" >
		    </TD>
			<TD  class= title>职业代码</TD>
			<TD  class=input>
				<Input verifyorder="1" class="common wid" name="OccupationCode" id="OccupationCode"/>
				<!-- <Input verifyorder="1" class="common" name="OccupationCode" ondblclick= "return showCodeList('occupationcode',[this,null],[0,1]);" onkeyup="return showCodeList('occupationcode',[this,null],[0,1]);"/> -->
			</TD>
		</TR>
		<TR class=common style="display:none">
			<TD  class= title>职业类别</TD>
			<TD  class= input>
			  <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="OccupationType" id="OccupationType"><input class=codename name=OccupationTypeName id="OccupationTypeName" readonly=true>
			</TD>
		</TR>
		<TR class= common style="display:none">
			
			<TD class=title>驾照类型	</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=LicenseType id="LicenseType"><input class=codename name=LicenseTypeName id="LicenseTypeName" readonly=true>
			</TD>
		</TR>               
	</Table> 
     <!--#####以下将被保人的地址必录标志修改非必录标志,2005-11-02修改####-->
  	<Div id="DivAddress" STYLE="display:none">   
      	<Table  class= common>   	
			<TR class= common>
				<TD  class= title>地址代码</TD>                  
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredAddressNo" id="InsuredAddressNo"><input class=codename name=InsuredAddressNoName id="InsuredAddressNoName" readonly=true>
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
   					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredProvince" id="InsuredProvince"><input class=codename name=InsuredProvinceName id="InsuredProvinceName">
				</TD>  
				<TD  class= title>市 </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="被保人城市|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredCity"  ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredCity" id="InsuredCity"><input class=codename name=InsuredCityName  id="InsuredCityName" readonly=true>
				</TD>  
				<TD  class= title>区/县 </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="被保人区/县|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredDistrict"  ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredDistrict" id="InsuredDistrict"><input class=codename  >
				</TD>  
			</TR>        
			<TR class= common>
				
				<!--TD  class= title>联系电话</TD>
				<TD  class= input><input class= common name="Phone"  verify="被保险人家庭电话|LEN<=18" ></TD-->            
			</TR>
			     
			<TR class= common>  
				<TD class=title>住宅电话</TD>
				<TD class=input>
					<Input name="HomePhone" id="HomePhone" class="common wid"  >
				</TD>			
				
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
                              保险计划
                      </TD>
                      <TD  class= input>
                          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="ContPlanCode" id="ContPlanCode">
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
                              处理机构
                          </TD>
                          <TD  class= input>
                              <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="ExecuteCom" id="ExecuteCom">
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
                <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="AccountNo" id="AccountNo" CodeData="0|^1|缴费帐户^2|其它" >
            </TD>
            <td class= title></td>
            <td class= input></td>
            <td class= title></td>
            <td class= input></td>
        </TR> 
        <TR CLASS=common>
  	    	<TD CLASS=title  >
  	      		开户银行 
  	    	</TD>
  	    	<TD CLASS=input COLSPAN=1  >
  	      		<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=InsuredBankCode id="InsuredBankCode" VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|CODE:bank" >
  	    	</TD>
  	    	<td class= title></td>
            <td class= input></td>
            <td class= title></td>
            <td class= input></td>
  	  	</TR>   
    </Table>          
  </Div>
  <Div id="divSalary" style="display:''">
  </Div>
  	<Div id="divLCInsuredPerson" style="display:none">
  	  	<Div id="DivGrpNoname" STYLE="display:none">        
      		<Table  class= common>        
          		<TR class= common>
            		<TD CLASS=title>
              			保单类型标记
            		</TD>
            		<TD CLASS=input COLSPAN=1>
              			<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=PolTypeFlag id="PolTypeFlag" VALUE="0" CLASS=code >
            		</TD>        
              		<TD CLASS=title>
                  		被保人人数
              		</TD>
              		<TD CLASS=input COLSPAN=1>
                  		<Input NAME=InsuredPeoples id="InsuredPeoples" VALUE="" readonly=true CLASS="common wid"  verify="被保人人数|NUM" >
              		</TD>
              		<td class= title></td>
            		<td class= input></td>
  	    		</TR>  
  	    		<input type=hidden name=InsuredFillNo id="InsuredFillNo">
        	</Table>
  		</Div>

	</Div>
</div>