
<DIV id=DivLCInsuredButton STYLE="display:''">
<!-- ��������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
</td>
<td class= titleImg>
<Input class= mulinetitle value="��һ������������"  name=InsuredSequencename readonly >���ͻ��ţ�<Input class= common name=InsuredNo >
<INPUT id="butBack" VALUE="��  ѯ" class=cssButton TYPE=button onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
<Div  id= "divSamePerson" style="display:'none'">
<font color=red>
��Ͷ����Ϊ�������˱��ˣ������������ѡ��
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
                �ͻ��ڲ�����
          </TD>             
            <TD  class= input>
                <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
                <input class="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" readonly="true">
            </TD>              
	        <TD  class= title>
                ���һ�������˹�ϵ</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToMainInsured"  elementtype=nacessary verify="���������˹�ϵ|code:Relation" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" readonly="readonly"  >
            </TD>  
            <td class="title">&nbsp;</td>
            <td class="input">&nbsp;</td>                     
        </TR> 
    </table> 
</DIV>  
<table  class= common>
<TR  class= common>   
    <TD CLASS=title>
            �������ͱ��
          </td>
          <TD  class="input">
            <Input NAME=PolTypeFlag VALUE="0" CLASS=codeno ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="�������ͱ��" ><input name="PolTypeFlagName" class="codename" readonly="readonly">
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
            ����
          </TD>
          <TD  class="input">
            <Input class="common" name=Name elementtype=nacessary verify="������������|notnull&len<=30" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();">
          </TD>
          <TD  class="title">
            �Ա�
          </TD>
          <TD  class="input">
            <Input class="codeno" name=Sex ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly="readonly" elementtype=nacessary>
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="codeno" name="IDType" value="0"  ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName readonly=true>
          </TD>
        </TR>
        
        <TR  class= common>
          
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class=common name="IDNo"   aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();">
          </TD>
           
           <TD  class= title>
        ְҵ����
      </TD>
      <TD  class= input>
      
                <Input class="codeno" name="OccupationCode" verify="Ͷ����ְҵ����|NOTNUlL&CODE:OccupationCode"  verifyorder="1" onblur="throughwork();" ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" ><input class=codename name=AppntOccupationCodeName  elementtype=nacessary onblur="throughwork();">
            </TD>
            <TD  class= title>
                �ƶ��绰
            </TD>
            <TD  class= input>
                <Input class= common name="Mobile" verify="���������ƶ��绰|len<=15" >
            </TD> 
          
        </TR>
        

	    </TR>               
              
    </Table>
</Div>	
    
<DIV id=DivGrpNoname STYLE="display:''">        
    <table  class="common">        
        <TR class="common">
        <TD  class= title>
                ְҵ���
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationType"  verify="��������ְҵ���|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);" elementtype=nacessary>
            </TD> 
            
            
            <TD CLASS=title >
                ����������
            </TD>
            <TD CLASS=input>
                <Input NAME=InsuredPeoples VALUE="" readonly=true CLASS=common verify="����������|num" >
            </TD>
            <TD CLASS=title>
                ����֤��
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
            ��������
          </TD>
          <TD  class= input>
          	 <input class="common" dateFormat="short" onblur="getAge();" verify="�����˳�������|NOTNUlL&DATE" name="Birthday" elementtype=nacessary>
          </TD>
           <TD CLASS=title>
                ����������
            </TD>
            <TD CLASS=input >
                <Input NAME=InsuredAppAge VALUE="" readonly=true CLASS=common verify="����������|num" >
            </TD>
            
            <TD  class= title>
                ��������
            </TD>
            <TD  class= input>
                <Input class= common name="EMail" verify="�������˵�������|len<=20&Email" >
            </TD>
            
            
	    </TR>  
	    
	    <TR CLASS=common>
	    <TD  class= title>
           ���б���
      </TD>                  
      <TD  class= input>
           <Input NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="������|code:bank" ondblclick="return queryBank();" onkeyup=" queryBank();" >           
      </TD>
      <TD CLASS=title width="109" >
	        �˺�
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=BankAccNo VALUE="" CLASS=common MAXLENGTH=20 verify="�˺�|len<=20" >
	    </TD>
	    <TD CLASS=title type="hidden">
	       
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=AccName  type="hidden" VALUE="" CLASS=common MAXLENGTH=20 verify="����|len<=20" >
	    </TD>

	  </TR>   
            
        <TR  class= common>
            
	      </TR>  
        
        <TR class=common>
			<TD class=title>
			  ��˾ʱ��
			</TD>
			<TD class=input>
			  <Input name="JoinCompanyDate" class="coolDatePicker" dateFormat="short">
			</TD>
			<TD class=title>
			  ����
			</TD>
			<TD class=input>
			  <Input name="Salary" class=common>
			</TD>
			<TD  class= title>
                            �������
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
                            ���ռƻ�
                    </TD>
                    <TD  class= input>
                        <Input class="code" name="ContPlanCode" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);"  >
                    </TD>
                    <TD  class= title>
                            ��������
                    </TD>
      			    <TD  class= input>
			            <Input name="CertifyCode" class=common>
         			   <!-- Input class=common name="CardNo"   aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();"-->
       			   </TD>
       			   <TD  class= title>
                           �������
                    </TD>
      			    <TD  class= input>
			            <Input name="StartCode" class=common>
			        </TD>
                    </TR>
			          <TD  class= title>
                           ����ֹ��
                    </TD>
      			    <TD  class= input>
			            <Input name="EndCode" class=common>
			        </TD>
			       <td class=title>������Ч����</td>
						<td class=input>
							<input class="coolDatePicker" dateFormat="short"  name=ContCValiDate verify="������Ч����|DATE verifyorder="1"">
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
                ��ϵ��ַ
            </TD>
            <TD  class= input  colspan="3"  >
              <Input class= common3 name="PostalAddress"  verify="����������ϵ��ַ|len<=80" >
            </TD>
            <TD  class= title>
                ��������
            </TD>
            <TD  class= input>
                <Input class= common name="ZipCode"  MAXLENGTH="6" verify="����������������|zipcode" >
            </TD>
            <!--TD  class= title>
                ��ϵ�绰
            </TD>
            <TD  class= input>
                <input class= common name="Phone"  verify="�������˼�ͥ�绰|len<=18" >
            </TD-->            
        </TR>  
    <TR  class= common>  
    <TD  class= title>
                ��ַ����
            </TD>                  
            <TD  class= input>
                  <Input class="codeno" name="InsuredAddressNo"  ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress();"><input class=codename name=InsuredAddressNoName readonly=true>
              </TD>
            
            <TD CLASS=title  >
	      �������� 
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=AccountNo VALUE="" CLASS="code" MAXLENGTH=20 verify="������|code:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
	    </TD>
          <TD  class= title>
            �칫�绰
          </TD>
          <TD  class= input>
            <Input class= common name="GrpPhone"  >
          </TD>          
          <!--TD  class= title>
            ����绰
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common name="Fax" verify="����|len<=15" >
          </TD-->
          
        </TR>         
      <TR  class= common>  
			     <TD class=title>
			  סլ�绰
			</TD>
			<TD class=input>
			  <Input name="HomePhone" class=common>
			</TD>			
           <!--TD  class= title>
            ������λ
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
			  ��˾ʱ��
			</TD>
			<TD class=input>
			  <Input name="JoinCompanyDate" class="coolDatePicker" dateFormat="short">
			</TD>
			<TD class=title>
			  ���ʣ�Ԫ��
			</TD>
			<TD class=input>
			  <Input name="Salary" class=common>
			</TD>
			<TD class=title>
			  ְλ
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
			  ��ͥ����
			</TD>
			<TD class=input>
			  <Input name="HomeFax" >
			</TD>
			<TD class=title>
			  ��λ����
			</TD>
			<TD class=input>
			  <Input name="GrpFax" class=common>
			</TD>
			<TD class=title>
			  ��ϵ����
			</TD>
			<TD class=input>
			  <Input name="Fax" class=common>
			</TD>		
		</TR>	
        <TR  class= common>
       </TR> 			
	</table-->
</DIV>
