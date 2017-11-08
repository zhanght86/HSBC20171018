<DIV id=DivLCInsuredButton STYLE="display:''">
<!-- 被保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
</td>
<td class=titleImg>
<Input class= mulinetitle value="第一被保险人资料"  name=InsuredSequencename id=InsuredSequencename readonly >（客户号：<Input class= common name=InsuredNo >
<INPUT id="butBack" VALUE="查  询" class=cssButton TYPE=button onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
<Div  id= "divSamePerson" style="display:none">
<font color=red>
如投保人为被保险人本人，可免填本栏，请选择
<INPUT TYPE="checkbox" NAME="SamePersonFlag" id=SamePersonFlag onclick="isSamePerson();">
</font>
</div>
</td>
</tr>
</table>

</DIV>
<DIV id=DivRelation STYLE="display:''">
    <table  class= common>  
        <TR  class= common>  
	        <TD  class= title>
                客户内部号码
          </TD>             
            <TD  class= input>
                <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
                <input class="codeno" name="SequenceNoCode" id=SequenceNoCode  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" 
				style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);">
				<Input class="codename" name="SequenceNoName" id=SequenceNoName readonly="true">
            </TD>              
	        <TD  class= title>
                与第一被保险人关系</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured  elementtype=nacessary verify="主被保险人关系|code:Relation" 
				style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);">
				<Input class="codename" name="RelationToMainInsuredName" id=RelationToMainInsuredName readonly="readonly"  >
            </TD>  
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>                     
        </TR> 
    </table> 
</DIV>  
<DIV id=DivLCInsured STYLE="display:''">
<DIV id=DivLCBasicInfo STYLE="display:''">   
    <table  class= common>
        <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class="input">
            <Input class="common" name=Name id=Name elementtype=nacessary verify="被保险人姓名|notnull&len<=20" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();">
          </TD>
          <TD  class="title">
            性别
          </TD>
          <TD  class="input">
            <Input class="codeno" name=Sex id=Sex verify="被保险人性别|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);">
			<input class=codename name=SexName id=SexName readonly="readonly" elementtype=nacessary>
          </TD>
          <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          	 <input class="common" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge();" name="Birthday" id=Birthday verify="被保险人出生日期|NOTNULL&DATE" verifyorder='2' >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="codeno" name="IDType" id=IDType value="0" verify="被保险人证件类型|NOTNULL&code:IDType" ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class=common name="IDNo" id=IDNo  aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();getAge();" verify="被保险人证件号码|NOTNULL&len<=20"  elementtype=nacessary >
          </TD>
           <td class="title">职业</td>
					<td class="input">
            <input class="common" name="Occupation" id=Occupation>
           
        </TR>
        <tr class = common>
        	 <TD  class= title>
                职业类别
            </TD>
            
             <TD  class= input>
            <Input class="codeno" name="OccupationType" id=OccupationType value="0" verify="被保险人职业代码|code:occupation1&notnull" 
			style="background:url(../common/images/select--bg_03.png) no-repeat right center"
			ondblclick="return showCodeList('occupation1',[this,OccupationName],[0,1]);" onkeyup="return showCodeListKey('occupation1',[this,OccupationName],[0,1]);" onblur="getallinfo();" >
			<input class=codename name=OccupationName id=OccupationName readonly=true elementtype=nacessary>
             <Input type="hidden" name="OccupationCode" id=OccupationCode>
          </TD>
        </tr>
<!--
        <TR  class= common>
          <TD  class= title>
            婚姻状况
          </TD>
          <TD  class= input>
            <Input class="codeno" name="Marriage"  ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName readonly=true elementtype=nacessary>
          </TD>          
          <TD  class= title>
            国籍
          </TD>
          <TD  class= input>
          <input class="codeno" name="NativePlaceCode"  ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);"><input class=codename name=NativePlaceName readonly=true elementtype="nacessary">
          </TD>
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
            <input class="codeno" name="License" verify="驾照|code:License" CodeData="0|^1|有 ^2|无" ondblclick="return showCodeListEx('License',[this,LicenseName],[0,1]);" onkeyup="return showCodeListKeyEx('License',[this,LicenseName],[0,1]);"><input class=codename name=LicenseName readonly=true>
          </td>
          <TD  class= title>
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
          </TD>          
        
        <TR class= common>
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
        </TR>   
          <td class=title>
          驾照类型	
          </td>
          <td  class= input>
            <input class="codeno" name="LicenseType" verify="驾照|code:LicenseType" ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName readonly=true>
          </td>
            <TD  class= title>
                职业编码
            </TD>
            <TD  class= input>
              <Input class="codeno" name="OccupationCode" verify="被保险人职业代码|code:OccupationCode&notnull" ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName],[0,1]);" onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName],[0,1]);" onfocus="getdetailwork();"><input class=codename name=OccupationCodeName readonly=true elementtype="nacessary">
              <Input type="hidden" name="OccupationType">
            </TD>
          <!--TD  class= title>
            与投保人关系
            </TD>
          <TD  class= input>
            <Input class="codeno" name="RelationToAppntCode" elementtype=nacessary ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1]);"><Input class="codename" name="RelationToAppntName" readonly="readonly">
           </TD--> 
            <!--TD  class= title>
                职业类别
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationType"  verify="被保险人职业类别|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
            </TD>
          <TD  class= title>
            是否吸烟
          </TD>
          <TD  class= input>
            <Input class="code" name="SmokeFlag"  ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
          </TD-->              
	    </TR>               
              
    </Table>
</Div>	
    
<DIV id=DivGrpNoname STYLE="display:''">        
    <table  class="common">        
        <TR class="common">
          <TD CLASS=title>
            保单类型标记
          </TD>
          <TD CLASS=input COLSPAN=1>
            <Input NAME=PolTypeFlag id=PolTypeFlag VALUE="0" CLASS=codeno style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
			ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="保单类型标记" >
			<input name="PolTypeFlagName" id=PolTypeFlagName class="codename" readonly="readonly">
          </TD>        
            <TD CLASS=title>
                被保人年龄
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input NAME=InsuredAppAge id=InsuredAppAge VALUE="" readonly=true CLASS=common verify="被保人年龄|num" >
            </TD>
             <TD CLASS=title>
               <!-- 被保人人数   -->
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input type=hidden NAME=InsuredPeoples id=InsuredPeoples VALUE="" readonly=true CLASS=common verify="被保人人数|num" >
            </TD> 
	    </TR>  
      </table>
</Div>
<DIV id=DivAddress STYLE="display:''">   
    <table  class= common>   	
        <TR  class= common>
            <TD  class= title>
                移动电话
            </TD>
            <TD  class= input>
                <Input class= common name="Mobile" id=Mobile verify="被保险人移动电话|len<=15" >
            </TD>
          <TD  class= title>
            办公电话
          </TD>
          <TD  class= input>
            <Input class= common name="GrpPhone" id=GrpPhone  >
          </TD>          
          <TD  class= title>
            传真电话
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common name="Fax" id=Fax verify="传真|len<=15" >
          </TD>
        </TR>         
      <TR  class= common>  
			<TD class=title>
			  住宅电话
			</TD>
			<TD class=input>
			  <Input name="HomePhone" id=HomePhone class=common>
			</TD>			
           <TD  class= title>
            工作单位
          </TD>
          <TD  class= input >
            <Input class= common name="GrpName" id=HomePhone  >
            
          </TD>
            <TD  class= title>
                电子邮箱
            </TD>
            <TD  class= input>
                <Input class= common name="EMail" id=EMail verify="被保险人电子邮箱|len<=20&Email" >
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
        <TR  class= common>
            <TD  class= title>
                地址代码
            </TD>                  
            <TD  class= input>
                <Input class="codeno" name="InsuredAddressNo" id=InsuredAddressNo  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="getaddresscodedata2();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress2();"><input class=codename name=InsuredAddressNoName readonly=true>
            </TD>
	      </TR>  
        <TR  class= common>

            <TD  class= title>
                联系地址
            </TD>
            <TD  class= input  colspan="3"  >
              <Input class= common3 name="PostalAddress" id=PostalAddress  verify="被保险人联系地址|len<=80" >
            </TD>
            <TD  class= title>
                邮政编码
            </TD>
            <TD  class= input>
                <Input class= common name="ZipCode" id=ZipCode  MAXLENGTH="6" verify="被保险人邮政编码|zipcode" >
            </TD>
            <!--TD  class= title>
                联系电话
            </TD>
            <TD  class= input>
                <input class= common name="Phone"  verify="被保险人家庭电话|len<=18" >
            </TD-->            
        </TR>
        <!--TR  class= common>
        </TR-->
   
    </TABLE>
</Div>    
</DIV>
<TABLE class= common>
    <TR class= common>
        <TD  class= title>
            <DIV id="divContPlan" style="display:none" >
	            <TABLE class= common>
		            <TR class= common>
			            <TD  class= title>
                            保险计划
                    </TD>
                    <TD  class= input>
                        <Input class="code" name="ContPlanCode" id=ContPlanCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);">
                    </TD>
		
                    </TR>
	            </TABLE>
            </DIV>
        </TD>
        <TD  class= title>
            <DIV id="divExecuteCom" style="display:none" >
	            <TABLE class= common>
		            <TR class= common>
			            <TD  class= title>
                            处理机构
                        </TD>
                        <TD  class= input>
                            <Input class="code" name="ExecuteCom" id=ExecuteCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeListEx('ExecuteCom',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ExecuteCom',[this],[0],'', '', '', true);">
                        </TD>
		            </TR>
	            </TABLE>
            </DIV>
        </TD>
        <TD  class= title>
        </TD>		
    </TR>
</TABLE>
<DIV id=DivClaim STYLE="display:none"> 
    <table  class= common>         
    <TR  class= common>  
            <TD  class= title>
                理赔金帐户
            </TD>                  
            <TD  class= input>
                <Input class="code" name="AccountNo" id=AccountNo  CodeData="0|^1|缴费帐户^2|其它" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
            </TD>
    </TR> 
          <TR CLASS=common>
	    <TD CLASS=title  >
	      开户银行 
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=BankCode id=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|code:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
	    </TD>
	    <TD CLASS=title >
	      户名 
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=AccName id=AccName VALUE="" CLASS=common MAXLENGTH=20 verify="户名|len<=20" >
	    </TD>
            <TD CLASS=title width="109" >
	      账号</TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=BankAccNo id=BankAccNo class="code" VALUE="" CLASS=common style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="银行帐号|len<=40" onfocus="getdetail();">
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
<DIV id=divLCInsuredPerson style="display:none">
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
</DIV>
