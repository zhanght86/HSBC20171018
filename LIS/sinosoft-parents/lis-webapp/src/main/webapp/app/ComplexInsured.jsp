<DIV id=DivLCInsuredButton STYLE="display:''">
<!-- ��������Ϣ���� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
</td>
<td class=titleImg>
<Input class= mulinetitle value="��һ������������"  name=InsuredSequencename id=InsuredSequencename readonly >���ͻ��ţ�<Input class= common name=InsuredNo >
<INPUT id="butBack" VALUE="��  ѯ" class=cssButton TYPE=button onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
<Div  id= "divSamePerson" style="display:none">
<font color=red>
��Ͷ����Ϊ�������˱��ˣ������������ѡ��
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
                �ͻ��ڲ�����
          </TD>             
            <TD  class= input>
                <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
                <input class="codeno" name="SequenceNoCode" id=SequenceNoCode  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" 
				style="background:url(../common/images/select--bg_03.png) no-repeat right center"
				ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);">
				<Input class="codename" name="SequenceNoName" id=SequenceNoName readonly="true">
            </TD>              
	        <TD  class= title>
                ���һ�������˹�ϵ</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToMainInsured" id=RelationToMainInsured  elementtype=nacessary verify="���������˹�ϵ|code:Relation" 
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
            ����
          </TD>
          <TD  class="input">
            <Input class="common" name=Name id=Name elementtype=nacessary verify="������������|notnull&len<=20" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();">
          </TD>
          <TD  class="title">
            �Ա�
          </TD>
          <TD  class="input">
            <Input class="codeno" name=Sex id=Sex verify="���������Ա�|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);">
			<input class=codename name=SexName id=SexName readonly="readonly" elementtype=nacessary>
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
          	 <input class="common" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge();" name="Birthday" id=Birthday verify="�������˳�������|NOTNULL&DATE" verifyorder='2' >
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class="codeno" name="IDType" id=IDType value="0" verify="��������֤������|NOTNULL&code:IDType" ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName readonly=true elementtype=nacessary>
          </TD>
          <TD  class= title>
            ֤������
          </TD>
          <TD  class= input>
            <Input class=common name="IDNo" id=IDNo  aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();getAge();" verify="��������֤������|NOTNULL&len<=20"  elementtype=nacessary >
          </TD>
           <td class="title">ְҵ</td>
					<td class="input">
            <input class="common" name="Occupation" id=Occupation>
           
        </TR>
        <tr class = common>
        	 <TD  class= title>
                ְҵ���
            </TD>
            
             <TD  class= input>
            <Input class="codeno" name="OccupationType" id=OccupationType value="0" verify="��������ְҵ����|code:occupation1&notnull" 
			style="background:url(../common/images/select--bg_03.png) no-repeat right center"
			ondblclick="return showCodeList('occupation1',[this,OccupationName],[0,1]);" onkeyup="return showCodeListKey('occupation1',[this,OccupationName],[0,1]);" onblur="getallinfo();" >
			<input class=codename name=OccupationName id=OccupationName readonly=true elementtype=nacessary>
             <Input type="hidden" name="OccupationCode" id=OccupationCode>
          </TD>
        </tr>
<!--
        <TR  class= common>
          <TD  class= title>
            ����״��
          </TD>
          <TD  class= input>
            <Input class="codeno" name="Marriage"  ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName readonly=true elementtype=nacessary>
          </TD>          
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="codeno" name="NativePlaceCode"  ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);"><input class=codename name=NativePlaceName readonly=true elementtype="nacessary">
          </TD>
          <TD  class= title>
            ְҵ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="WorkType"  >
          </TD>
          <td class=title>
          ����
          </td>
          <td  class= input>
            <input class="codeno" name="License" verify="����|code:License" CodeData="0|^1|�� ^2|��" ondblclick="return showCodeListEx('License',[this,LicenseName],[0,1]);" onkeyup="return showCodeListKeyEx('License',[this,LicenseName],[0,1]);"><input class=codename name=LicenseName readonly=true>
          </td>
          <TD  class= title>
            �������ڵ�
          </TD>
          <TD  class= input>
            <Input class= common name="RgtAddress" >
          </TD>
          <TD  class= title>
            ����
          </TD>
          <TD  class= input>
          <input class="code" name="Nationality"  ondblclick="return showCodeList('Nationality',[this]);" onkeyup="return showCodeListKey('Nationality',[this]);">
          </TD>          
        
        <TR class= common>
          <TD  class= title>
            ѧ��
          </TD>
          <TD  class= input>
            <Input class="code" name="Degree"  ondblclick="return showCodeList('Degree',[this]);" onkeyup="return showCodeListKey('Degree',[this]);">
          </TD>
          <TD  class= title>
            ��ְ�����֣�
          </TD>
          <TD  class= input>
            <Input class= common name="PluralityType"  >
          </TD>           
        </TR>   
          <td class=title>
          ��������	
          </td>
          <td  class= input>
            <input class="codeno" name="LicenseType" verify="����|code:LicenseType" ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName readonly=true>
          </td>
            <TD  class= title>
                ְҵ����
            </TD>
            <TD  class= input>
              <Input class="codeno" name="OccupationCode" verify="��������ְҵ����|code:OccupationCode&notnull" ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName],[0,1]);" onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName],[0,1]);" onfocus="getdetailwork();"><input class=codename name=OccupationCodeName readonly=true elementtype="nacessary">
              <Input type="hidden" name="OccupationType">
            </TD>
          <!--TD  class= title>
            ��Ͷ���˹�ϵ
            </TD>
          <TD  class= input>
            <Input class="codeno" name="RelationToAppntCode" elementtype=nacessary ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1]);"><Input class="codename" name="RelationToAppntName" readonly="readonly">
           </TD--> 
            <!--TD  class= title>
                ְҵ���
            </TD>
            <TD  class= input>
                <Input class="code" name="OccupationType"  verify="��������ְҵ���|code:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
            </TD>
          <TD  class= title>
            �Ƿ�����
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
            �������ͱ��
          </TD>
          <TD CLASS=input COLSPAN=1>
            <Input NAME=PolTypeFlag id=PolTypeFlag VALUE="0" CLASS=codeno style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
			ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="�������ͱ��" >
			<input name="PolTypeFlagName" id=PolTypeFlagName class="codename" readonly="readonly">
          </TD>        
            <TD CLASS=title>
                ����������
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input NAME=InsuredAppAge id=InsuredAppAge VALUE="" readonly=true CLASS=common verify="����������|num" >
            </TD>
             <TD CLASS=title>
               <!-- ����������   -->
            </TD>
            <TD CLASS=input COLSPAN=1>
                <Input type=hidden NAME=InsuredPeoples id=InsuredPeoples VALUE="" readonly=true CLASS=common verify="����������|num" >
            </TD> 
	    </TR>  
      </table>
</Div>
<DIV id=DivAddress STYLE="display:''">   
    <table  class= common>   	
        <TR  class= common>
            <TD  class= title>
                �ƶ��绰
            </TD>
            <TD  class= input>
                <Input class= common name="Mobile" id=Mobile verify="���������ƶ��绰|len<=15" >
            </TD>
          <TD  class= title>
            �칫�绰
          </TD>
          <TD  class= input>
            <Input class= common name="GrpPhone" id=GrpPhone  >
          </TD>          
          <TD  class= title>
            ����绰
          </TD>
          <TD  class= input colspan=3 >
            <Input class= common name="Fax" id=Fax verify="����|len<=15" >
          </TD>
        </TR>         
      <TR  class= common>  
			<TD class=title>
			  סլ�绰
			</TD>
			<TD class=input>
			  <Input name="HomePhone" id=HomePhone class=common>
			</TD>			
           <TD  class= title>
            ������λ
          </TD>
          <TD  class= input >
            <Input class= common name="GrpName" id=HomePhone  >
            
          </TD>
            <TD  class= title>
                ��������
            </TD>
            <TD  class= input>
                <Input class= common name="EMail" id=EMail verify="�������˵�������|len<=20&Email" >
            </TD>
	    </TR>  
        <!--TR  class= common>
          <TD  class= title>
            ��λ��ַ
          </TD>
          <TD  class= input  >
            <Input class= common name="GrpAddress"  >
          </TD>
          <TD  class= title>
            ��λ��������
          </TD>
          <TD  class= input>
            <Input class= common name="GrpZipCode"  >
          </TD>
        </TR>	 
	<TR class=common>
		<TD class=title>
		  ��ͥ��ַ
		</TD>
		<TD class=input>
		  <Input name="HomeAddress" class= common >
		</TD>
		<TD class=title>
		  ��ͥ�ʱ�
		</TD>
		<TD class=input>
		  <Input name="HomeZipCode" class=common>
		</TD>
       </TR-->			             
        <!--TR  class= common>        
          <TD  class= title>
            ��ϵ��ַѡ��
          </TD>
          <TD  class= input>
            <Input class="code" name="CheckPostalAddress"  value='3' CodeData="0|^1|��λ��ַ^2|��ͥ��ַ^3|����"  ondblclick="return showCodeListEx('CheckPostalAddress',[this]);" onkeyup="return showCodeListKeyEx('CheckPostalAddress',[this]);">                     
          </TD>   
          <TD  class= title colspan=2>
            <font color=red>(����д��λ��ַ���ͥ��ַ��ʵ������)</font>
          </TD> 
        </TR--> 		             	       
        <TR  class= common>
            <TD  class= title>
                ��ַ����
            </TD>                  
            <TD  class= input>
                <Input class="codeno" name="InsuredAddressNo" id=InsuredAddressNo  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="getaddresscodedata2();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress2();"><input class=codename name=InsuredAddressNoName readonly=true>
            </TD>
	      </TR>  
        <TR  class= common>

            <TD  class= title>
                ��ϵ��ַ
            </TD>
            <TD  class= input  colspan="3"  >
              <Input class= common3 name="PostalAddress" id=PostalAddress  verify="����������ϵ��ַ|len<=80" >
            </TD>
            <TD  class= title>
                ��������
            </TD>
            <TD  class= input>
                <Input class= common name="ZipCode" id=ZipCode  MAXLENGTH="6" verify="����������������|zipcode" >
            </TD>
            <!--TD  class= title>
                ��ϵ�绰
            </TD>
            <TD  class= input>
                <input class= common name="Phone"  verify="�������˼�ͥ�绰|len<=18" >
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
                            ���ռƻ�
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
                            �������
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
                ������ʻ�
            </TD>                  
            <TD  class= input>
                <Input class="code" name="AccountNo" id=AccountNo  CodeData="0|^1|�ɷ��ʻ�^2|����" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
            </TD>
    </TR> 
          <TR CLASS=common>
	    <TD CLASS=title  >
	      �������� 
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=BankCode id=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="������|code:bank" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
	    </TD>
	    <TD CLASS=title >
	      ���� 
	    </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=AccName id=AccName VALUE="" CLASS=common MAXLENGTH=20 verify="����|len<=20" >
	    </TD>
            <TD CLASS=title width="109" >
	      �˺�</TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input NAME=BankAccNo id=BankAccNo class="code" VALUE="" CLASS=common style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accnum',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="�����ʺ�|len<=40" onfocus="getdetail();">
	    </TD>
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
<DIV id=divLCInsuredPerson style="display:none">
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
