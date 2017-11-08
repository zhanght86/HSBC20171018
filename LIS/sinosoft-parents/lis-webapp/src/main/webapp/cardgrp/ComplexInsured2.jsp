<div id=DivLCInsuredButton STYLE="display:''">

  <!-- 被保人信息部分 -->
  <table>
    <tr>
      <td class="common">
        <img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);">
      </td>
      <td class="titleImg">
        <input class="mulinetitle" value="被保险人资料"  name="InsuredSequencename" readonly >（客户号：<input class="common" name="InsuredNo" value="">
        <input id="InsuredButBack" VALUE="查  询" class=cssButton type="button" onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
        <div  id= "divSamePerson" style="display:'none'">
          <font color="red">
            如投保人为被保险人本人，可免填本栏，请选择
            <input type="checkbox" name="SamePersonFlag" onclick="isSamePerson();">
          </font>
        </div>
      </td>
    </tr>
  </table>

</div>


<div id=DivRelation style="display:''">
  <table  class="common">  
        <tr class="common">
          <td class="common">VIP客户</td>
          <td>
            <input type="text" name="VIPValue1" class="codeno" readonly="readonly" ondblclick="return showCodeList('vipvalue', [this,AppntVIPFlagname1], [0,1]);" onkeyup="return showCodeListKey('vipvalue', [this,AppntVIPFlagname1], [0,1]);"><input type="text" name="AppntVIPFlagname1" class="codename" readonly="readonly">
          </td>
        </tr>
    <tr  class="common">  
      <td  class="title">
        客户内部号码
      </td>             
      <td class="input">
        <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
        <input class="codeno" name="SequenceNo" verify="客户内部号码|NOTNULL&NUM" verifyorder='2' CodeData="0|^1|被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" elementtype=nacessary readonly="true">
      </td>              
      <td  class="title">
        与第一被保险人关系</TD>             
      <td  class="input">
        <input class="codeno" name="RelationToMainInsured" verify="主被保险人关系|NOTNULL&CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName"  elementtype=nacessary readonly="readonly"  >
      </td>  
        <TD  class= title>
          与投保人关系
          </TD>
        <TD  class= input>
          <Input class="codeno" name="RelationToAppnt" verify="与投保人关系|NOTNULL&CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName" readonly="readonly" elementtype=nacessary>
         </TD> 
    </tr> 
  </table> 
</div>  
<div id=DivLCInsured style="display:''">
  <table  class= common>
    <TR  class= common>        
      <TD  class= title>
        姓名
      </TD>
      <TD  class= input>
        <Input class= common name=Name onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="被保险人姓名|NOTNULL&LEN<=20" verifyorder='2' onblur="getallinfo();">
      </TD>
      <TD  class= title>
        证件类型
      </TD>
      <TD  class= input>
        <Input class="codeno" name="IDType" value="0" verify="被保险人证件类型|NOTNULL&CODE:IDType" verifyorder='2' ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" aonblur="getallinfo();" ><input class=codename name=IDTypeName readonly=true elementtype=nacessary>
      </TD>
      <TD  class= title>
        证件号码
      </TD>
      <TD  class= input>
        <Input class= common name="IDNo" onblur="checkidtype2();getBirthdaySexByIDNo2(this.value);getAge2();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="被保险人证件号码|NOTNULL&LEN<=20" verifyorder='2'>
      </TD>                                                                                                                                                                                                        
    </TR>
    
    <TR  class= common>
      <TD  class= title>
        性别
      </TD>
      <TD  class= input>
        <Input class="codeno" name=Sex verify="被保险人性别|NOTNULL&CODE:Sex" verifyorder='2' ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly="readonly" elementtype=nacessary>
      </TD>
      <TD  class= title>
        出生日期
      </TD>
      <TD  class= input>
        <input class="common" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge2();" name="Birthday" verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' >
          </TD>
      </TD>
      <TD CLASS=title>
        被保人年龄
      </TD>
      <TD CLASS=input COLSPAN=1>
        <Input NAME=InsuredAppAge verify="被保人年龄|value>=0" verifyorder="2" VALUE="" readonly=true CLASS=common verify="被保人年龄|NUM" >
      </TD>
    </TR>
    
    <TR  class= common>
      <TD  class= title>
        婚姻状况
      </TD>
      <TD  class= input>
        <Input class="codeno" name=Marriage ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName readonly=true elementtype=nacessary>
      </TD>  
      <TD  class= title>
        国籍
      </TD>
      <TD  class= input>
      <input class="codeno" name="NativePlace"  ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);"><input class=codename name=NativePlaceName readonly=true>
      </TD>              
      <TD  class= title>
        职业编码
      </TD>
      <TD  class=input>
        <Input class="codeno" name="OccupationCode" verify="被保险人职业代码|CODE:OccupationCode&NOTNULL" verifyorder='2' ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onfocus="getdetailwork2();"><input class=codename name=OccupationCodeName elementtype=nacessary readonly=true>
        <Input type="hidden" name="OccupationType"> 
      </td>

    </TR>
<!--    
    <TR  class= common>
      <TD  class= title>
        职业（工种）
      </TD>
      <TD  class= input>
        <Input class= common name="WorkType"  >
      </TD>
      <td class=title>
      驾照
      </td>
      <td  class= input>
        <input class="codeno" name="License" verify="驾照|CODE:License" CodeData="0|^1|有 ^2|无" ondblclick="return showCodeListEx('License',[this,LicenseName],[0,1]);" onkeyup="return showCodeListKeyEx('License',[this,LicenseName],[0,1]);"><input class=codename name=LicenseName readonly=true>
      </td-->
      <!--TD  class= title>
        户口所在地
      </TD>
      <TD  class= input>
        <Input class= common name="RgtAddress" >
      </TD>
      <TD  class= title>
        民族
      </TD>
      <TD  class= input>
      <input class="code" name="Nationality"  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
      </Td>          
    </TR>
    -->
    <!--TR class= common>
      <TD  class= title>
        学历
      </TD>
      <TD  class= input>
        <Input class="code" name="Degree"  ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
      </TD>
      <TD  class= title>
        兼职（工种）
      </TD>
      <TD  class= input>
        <Input class= common name="PluralityType"  >
      </TD>           
    </TR-->   
    <tr class= common>
        <td class=title>
        驾照类型	
        </td>
        <td  class= input>
          <input class="codeno" name=LicenseType verify="被保险人驾照类型|CODE:LicenseType" verifyorder='2' ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName readonly=true>
        </td>
          <!--TD  class= title>
              职业类别
          </TD>
          <TD  class= input>
              <Input class="code" name="OccupationType"  verify="被保险人职业类别|CODE:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        <TD  class= title>
          是否吸烟
        </TD>
        <TD  class= input>
          <Input class="code" name="SmokeFlag"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
        </TD-->              
    </TR>               
  </Table>      
  <DIV id=DivAddress STYLE="display:''">   
      <table  class= common>   	
          <TR  class= common>
              <TD  class= title>
                  地址代码
              </TD>                  
              <TD  class= input>
                  <Input class="codeno" name="InsuredAddressNo"  ondblclick="getaddresscodedata2();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata2(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress2();"><input class=codename name=InsuredAddressNoName readonly=true>
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
            <Input class="codeno" verify="被保人省份|NOTNULL&NUM&LEN=2"  verifyorder="2" name="InsuredProvince"  ondblclick="return showCodeList('Province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            市
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="被保人城市|NOTNULL&NUM&LEN=4"  verifyorder="2" name="InsuredCity"  ondblclick="showCodeList('City',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);" onkeyup="return showCodeListKey('City',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);"><input class=codename name=InsuredCityName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            区/县
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="被保人区/县|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredDistrict"  ondblclick="showCodeList('District',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('District',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName readonly=true elementtype=nacessary>
          </TD>  
        </TR>        
          <TR  class= common>
  
              <TD class= title>
                  街道
              </TD>
              <TD  class= input  colspan="3"  >
                <Input class= common3 name="PostalAddress" elementtype=nacessary verify="被保险人联系地址|NOTNULL&LEN<=80" verifyorder='2' >
              </TD>
              <TD  class= title>
                  邮政编码
              </TD>
              <TD  class= input>
                <Input class= common name="ZIPCODE" elementtype=nacessary MAXLENGTH="6" verify="被保险人邮政编码|NOTNULL&ZIPCODE" verifyorder='2' >
              </TD>
              <!--TD  class= title>
                  联系电话
              </TD>
              <TD  class= input>
                  <input class= common name="Phone"  verify="被保险人家庭电话|LEN<=18" >
              </TD-->            
          </TR>
          <TR  class= common>
              <TD  class= title>
                  移动电话
              </TD>
              <TD  class= input>
                  <Input class= common name="Mobile" verify="被保险人移动电话|LEN<=15&PHONE" verifyorder='2' >
              </TD>
            <TD  class= title>
              办公电话
            </TD>
            <TD  class= input>
              <Input class= common name="GrpPhone" verify="被保险人办公电话|LEN<=15&PHONE" verifyorder='2' >
            </TD>          
            <TD  class= title>
              传真电话
            </TD>
            <TD  class= input colspan=3 >
              <Input class= common name="Fax" verify="被保险人传真电话|LEN<=15&PHONE" verifyorder='2' >
            </TD>
          </TR>         
        <TR  class= common>  
  			<TD class=title>
  			  住宅电话
  			</TD>
  			<TD class=input>
  			  <Input name="HomePhone" class=common verify="被保险人住宅电话|LEN<=15&PHONE" verifyorder='2'>
  			</TD>			
             <TD  class= title>
              工作单位
            </TD>
            <TD  class= input >
              <Input class= common name="GrpName">
              
            </TD>
              <TD  class= title>
                  电子邮箱
              </TD>
              <TD  class= input>
                  <Input class= common name="EMail" verify="被保险人电子邮箱|LEN<=20&EMAIL" verifyorder='2' >
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
              <Input class= common name="GrpZIPCODE"  >
            </TD>
          </TR>	 
  	<TR class=common>
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
  		  <Input name="HomeZIPCODE" class=common>
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
     
      </TABLE>
  </Div>    
</DIV>
  <TABLE class= common>
      <TR class= common>
          <TD  class= title>
              <DIV id="divContPlan" style="display:'none'" >
  	            <TABLE class= common>
  		            <TR class= common>
  			            <TD  class= title>
                              保险计划
                      </TD>
                      <TD  class= input>
                          <Input class="code" name="ContPlanCode" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);">
                      </TD>
  		
                      </TR>
  	            </TABLE>
              </DIV>
          </TD>
          <TD  class= title>
              <DIV id="divExecuteCom" style="display:'none'" >
  	            <TABLE class= common>
  		            <TR class= common>
  			            <TD  class= title>
                              处理机构
                          </TD>
                          <TD  class= input>
                              <Input class="code" name="ExecuteCom" ondblclick="showCodeListEx('ExecuteCom',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ExecuteCom',[this],[0],'', '', '', true);">
                          </TD>
  		            </TR>
  	            </TABLE>
              </DIV>
          </TD>
          <TD  class= title>
          </TD>		
      </TR>
  </TABLE>
  <DIV id=DivClaim STYLE="display:'none'"> 
      <table  class= common>         
      <TR  class= common>  
              <TD  class= title>
                  理赔金帐户
              </TD>                  
              <TD  class= input>
                  <Input class="code" name="AccountNo"  CodeData="0|^1|缴费帐户^2|其它" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
              </TD>
      </TR> 
            <TR CLASS=common>
  	    <TD CLASS=title  >
  	      开户银行 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|CODE:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
  	    </TD>
  	    <TD CLASS=title >
  	      户名 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=AccName VALUE="" CLASS=common MAXLENGTH=20 verify="户名|LEN<=20" >
  	    </TD>
              <TD CLASS=title width="109" >
  	      账号</TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=BankAccNo class="code" VALUE="" CLASS=common ondblclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|LEN<=40" onfocus="getdetail();">
  	    </TD>
  	  </TR>   
      </Table>          
  </DIV>
  <DIV id=divSalary style="display:''">
  	<!--table class=common>
  		<TR class=common>
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
  </DIV>
  <DIV id=divLCInsuredPerson style="display:'none'">
  	<!--table class=common>				
  		</TR>
  		<TR class=common>
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
          <TR  class= common>
         </TR> 			
  	</table-->
  	  <DIV id=DivGrpNoname STYLE="display:'none'">        
      <table  class= common>        
          <TR class= common>
            <TD CLASS=title>
              保单类型标记
            </TD>
            <TD CLASS=input COLSPAN=1>
              <Input NAME=PolTypeFlag VALUE="0" CLASS=code ondblclick="showCodeList('PolTypeFlag', [this]);" onkeyup="showCodeListKey('PolTypeFlag', [this]);" verify="保单类型标记" >
            </TD>        
              <TD CLASS=title>
                  被保人人数
              </TD>
              <TD CLASS=input COLSPAN=1>
                  <Input NAME=InsuredPeoples VALUE="" readonly=true CLASS=common verify="被保人人数|NUM" >
              </TD>
  	    </TR>  
        </table>
  </Div>

</DIV>
