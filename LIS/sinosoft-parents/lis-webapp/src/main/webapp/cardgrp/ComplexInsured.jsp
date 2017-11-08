
<DIV id=DivLCInsuredButton STYLE="display:''">
<!-- 被保人信息部分 -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
</td>
<td class= titleImg>
<Input class= mulinetitle value="第一被保险人资料"  name=InsuredSequencename readonly >（客户号：<Input class= common name=InsuredNo >
<INPUT id="butBack" VALUE="查  询" class=cssButton TYPE=button onclick="queryInsuredNo();"> 首次投保客户无需填写客户号）
<Div  id= "divSamePerson" style="display:'none'">
<font color=red>
如投保人为被保险人本人，可免填本栏，请选择
<INPUT TYPE="checkbox" NAME="SamePersonFlag" onclick="isSamePerson();">
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
                <input class="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" readonly="true">
            </TD>              
	        <TD  class= title>
                与第一被保险人关系</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToMainInsured"  elementtype=nacessary verify="主被保险人关系|code:Relation" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" readonly="readonly"  >
            </TD>  
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>                     
        </TR> 
    </table> 
</DIV>  
<table  class= common>
<TR  class= common>   
    <TD CLASS=title>
            保单类型标记
          </td>
          <TD  class="input">
            <Input NAME=PolTypeFlag VALUE="0" CLASS=codeno ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="保单类型标记" ><input name="PolTypeFlagName" class="codename" readonly="readonly">
          </TD>
          <TD CLASS=input>
            
          </TD>        
         </TR> 
 </table>         
<DIV id=DivLCInsured STYLE="display:''">
<DIV id=DivLCBasicInfo STYLE="display:''">   
    <table  class= common>
    
         
        <TR  class= common>        
          <TD  class= title>
            姓名
          </TD>
          <TD  class="input">
            <Input class="common" name=Name elementtype=nacessary verify="被保险人姓名|notnull&len<=30" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();">
          </TD>
          <TD  class="title">
            性别
          </TD>
          <TD  class="input">
            <Input class="codeno" name=Sex ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly="readonly" elementtype=nacessary>
          </TD>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class="codeno" name="IDType" value="0"  ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName readonly=true>
          </TD>
        </TR>
        
        <TR  class= common>
          
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class=common name="IDNo"   aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();">
          </TD>
           
           <TD  class= title>
        职业代码
      </TD>
      <TD  class= input>
      
                <Input class="codeno" name="OccupationCode" verify="投保人职业代码|NOTNUlL&CODE:OccupationCode"  verifyorder="1" onblur="throughwork();" ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" ><input class=codename name=AppntOccupationCodeName  elementtype=nacessary onblur="throughwork();">
            </TD>
            <TD  class= title>
                移动电话
            </TD>
            <TD  class= input>
                <Input class= common name="Mobile" verify="被保险人移动电话|len<=15" >
            </TD> 
          
        </TR>
        

	    </TR>               
              
    </Table>
</Div>	
    
<DIV id=DivGrpNoname STYLE="display:''">        
    <table  class="common">        
        <TR class="common">
        <TD  class= title>
                职业类别
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationType"  verify="被保险人职业类别|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);" elementtype=nacessary>
            </TD> 
            
            
            <TD CLASS=title >
                被保人人数
            </TD>
            <TD CLASS=input>
                <Input NAME=InsuredPeoples VALUE="" readonly=true CLASS=common verify="被保人人数|num" >
            </TD>
            <TD CLASS=title>
                工作证号
            </TD>
            <TD CLASS=input>
                <Input NAME=WorkNo VALUE="" CLASS=common >
            </TD> 
            
	    </TR>  
	    
      </table>
</Div>
<DIV id=DivAddress STYLE="display:''">   
    <table  class= common>   	
        <TR  class= common>
           <TD  class= title>
            出生日期
          </TD>
          <TD  class= input>
          	 <input class="common" dateFormat="short" onblur="getAge();" verify="被保人出生日期|NOTNUlL&DATE" name="Birthday" elementtype=nacessary>
          </TD>
           <TD CLASS=title>
                被保人年龄
            </TD>
            <TD CLASS=input >
                <Input NAME=InsuredAppAge VALUE="" readonly=true CLASS=common verify="被保人年龄|num" >
            </TD>
            
            <TD  class= title>
                电子邮箱
            </TD>
            <TD  class= input>
                <Input class= common name="EMail" verify="被保险人电子邮箱|len<=20&Email" >
            </TD>
            
            
	    </TR>  
	    
	    <TR CLASS=common>
	    <TD  class= title>
           银行编码
      </TD>                  
      <TD  class= input>
           <Input NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|code:bank" ondblclick="return queryBank();" onkeyup=" queryBank();" >           
      </TD>
      <TD CLASS=title width="109" >
	        账号
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=BankAccNo VALUE="" CLASS=common MAXLENGTH=20 verify="账号|len<=20" >
	    </TD>
	    <TD CLASS=title type="hidden">
	       
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=AccName  type="hidden" VALUE="" CLASS=common MAXLENGTH=20 verify="户名|len<=20" >
	    </TD>

	  </TR>   
            
        <TR  class= common>
            
	      </TR>  
        
        <TR class=common>
			<TD class=title>
			  入司时间
			</TD>
			<TD class=input>
			  <Input name="JoinCompanyDate" class="coolDatePicker" dateFormat="short">
			</TD>
			<TD class=title>
			  工资
			</TD>
			<TD class=input>
			  <Input name="Salary" class=common>
			</TD>
			<TD  class= title>
                            处理机构
                        </TD>
                        <TD  class= input>
                            <!--Input class="code" name="ExecuteCom" ondblclick="showCodeListEx('comcode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('comcode',[this],[0],'', '', '', true);"-->
                            <Input class=codeno name=ExecuteCom  ondblclick="return showCodeList('comcode2',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode2',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
                        </TD>
		</TR>
        <!--TR  class= common>
        </TR-->
   
    </TABLE>
</Div>    
</DIV>
<TABLE class= common>
    <TR class= common>
        <TD  class= title>
            <DIV id="divContPlan" style="display:''" >
		            <TR class= common>
			            <TD  class= title>
                            保险计划
                    </TD>
                    <TD  class= input>
                        <Input class="code" name="ContPlanCode" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);"  >
                    </TD>
                    <TD  class= title>
                            卡单编码
                    </TD>
      			    <TD  class= input>
			            <Input name="CertifyCode" class=common>
         			   <!-- Input class=common name="CardNo"   aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();"-->
       			   </TD>
       			   <TD  class= title>
                           卡单起号
                    </TD>
      			    <TD  class= input>
			            <Input name="StartCode" class=common>
			        </TD>
                    </TR>
			          <TD  class= title>
                           卡单止号
                    </TD>
      			    <TD  class= input>
			            <Input name="EndCode" class=common>
			        </TD>
			       <td class=title>保单生效日期</td>
						<td class=input>
							<input class="coolDatePicker" dateFormat="short"  name=ContCValiDate verify="保单生效日期|DATE verifyorder="1"">
						</td>

            </DIV>
        </TD>
        <TD  class= title>
            <DIV id="divExecuteCom" style="display:'none'" >
	            <TABLE class= common>
		            <TR class= common>
			            
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
                联系地址
            </TD>
            <TD  class= input  colspan="3"  >
              <Input class= common3 name="PostalAddress"  verify="被保险人联系地址|len<=80" >
            </TD>
            <TD  class= title>
                邮政编码
            </TD>
            <TD  class= input>
                <Input class= common name="ZipCode"  MAXLENGTH="6" verify="被保险人邮政编码|zipcode" >
            </TD>
            <!--TD  class= title>
                联系电话
            </TD>
            <TD  class= input>
                <input class= common name="Phone"  verify="被保险人家庭电话|len<=18" >
            </TD-->            
        </TR>  
    <TR  class= common>  
    <TD  class= title>
                地址代码
            </TD>                  
            <TD  class= input>
                  <Input class="codeno" name="InsuredAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress();"><input class=codename name=InsuredAddressNoName readonly=true>
              </TD>
            
            <TD CLASS=title  >
	      开户银行 
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=AccountNo VALUE="" CLASS="code" MAXLENGTH=20 verify="开户行|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
	    </TD>
          <TD  class= title>
            办公电话
          </TD>
          <TD  class= input>
            <Input class= common name="GrpPhone"  >
          </TD>          
          <!--TD  class= title>
            传真电话
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common name="Fax" verify="传真|len<=15" >
          </TD-->
          
        </TR>         
      <TR  class= common>  
			     <TD class=title>
			  住宅电话
			</TD>
			<TD class=input>
			  <Input name="HomePhone" class=common>
			</TD>			
           <!--TD  class= title>
            工作单位
          </TD>
          <TD  class= input >
            <Input class= common name="GrpName"  >
            
          </TD-->
            
            </tr>
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
</DIV>
