
<DIV id="DivLCInsuredButton" STYLE="display:''">
<!-- ��������Ϣ���� -->
  <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
      </td>
      <td class= titleImg>
        <Input class= mulinetitle value="��һ������������"  name=InsuredSequencename id="InsuredSequencename" readonly >���ͻ��ţ�<Input class= "common" name=InsuredNo id="InsuredNo">
        <!-- <INPUT id="butBack" name="butBack" id="butBack" VALUE="��  ѯ" class=cssButton TYPE=button onclick="queryInsuredNo();"> --><a href="javascript:void(0)" class=button id="butBack" name="butBack" style="font-weight:normal;" onclick="queryInsuredNo();">��  ѯ</a> �״�Ͷ���ͻ�������д�ͻ��ţ�
        <Div  id= "divSamePerson" style="display:'none'">
          <font color=red>��Ͷ����Ϊ�������˱��ˣ������������ѡ��
          <INPUT TYPE="checkbox" NAME="SamePersonFlag" id="SamePersonFlag" onclick="isSamePerson();">
          </font>
        </div>
      </td>
    </tr>
  </table>
</DIV>
<DIV id="DivRelation" STYLE="display:''">
    <table  class= common>  
      <TR  class= common>  
	      <TD  class= title>�ͻ��ڲ�����</TD>             
        <TD  class= input>
          <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
          <input style="background:url(../common/images/select--bg_03.png)    no-repeat right center"  class="codeno" name="SequenceNoCode" id="SequenceNoCode" elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" onclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" id="SequenceNoName" readonly="true">
        </TD>              
	      <TD  class= title>���һ�������˹�ϵ</TD>             
        <TD  class= input>
          <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center"  class="codeno" name="RelationToMainInsured" id="RelationToMainInsured"  elementtype=nacessary verify="���������˹�ϵ|code:Relation" onclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" readonly="readonly"  >
        </TD> 
        <td class= title></td>                     
        <td class= input></td>                     
      </TR> 
    </table> 
</DIV>  
<div class="maxbox"> 
<table  class= common>
  <TR  class= common>   
    <TD CLASS=title style="width:9%">�������ͱ��</td>
    <TD  class="input">
      <Input  style="background:url(../common/images/select--bg_03.png)     no-repeat right center" NAME=PolTypeFlag id="PolTypeFlag" VALUE="0" CLASS=codeno ondblclick="showCodeList('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" onkeyup="showCodeListKey('PolTypeFlag', [this,PolTypeFlagName],[0,1]);" verify="�������ͱ��" ><input name="PolTypeFlagName" id="PolTypeFlagName" class="codename" readonly="readonly">
    </TD>
    <TD CLASS=input></TD> 
    <td CLASS=input></td>       
    <td CLASS=title></td>       
    <td CLASS=input></td>             
  </TR> 
</table> 
     
<DIV id="DivLCInsured" STYLE="display:''">
  <DIV id="DivLCBasicInfo" STYLE="display:''">   
    <table  class= common>
      <TR  class= common>        
        <TD  class= title>����</TD>
        <TD  class="input">
          <Input  class="common wid" name=Name id="Name" elementtype=nacessary verify="������������|notnull&len<=30" aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="confirmSecondInput(this,'onblur');" onblur="getallinfo();">
        </TD>
        <TD  class="title">�Ա�</TD>
        <TD  class="input">
          <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name=Sex id="Sex" onclick="return showCodeList('Sex',[this,SexName],[0,1]);" ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName id="SexName" readonly="readonly" elementtype=nacessary>
        </TD>
        <TD  class= title>֤������</TD>
        <TD  class= input>
          <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="IDType" id="IDType" value="0" onclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" onblur="getallinfo();" ><input class=codename name=IDTypeName id="IDTypeName" readonly=true elementtype=nacessary>
        </TD>
      </TR>
      <TR  class= common>
        <TD  class= title>֤������</TD>
        <TD  class= input>
          <Input class="common wid" name="IDNo" id="IDNo"   aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();" elementtype=nacessary>
        </TD>
        <TD  class= title>ְҵ����</TD>
        <TD  class= input>
          <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="OccupationCode" id="OccupationCode" verify="Ͷ����ְҵ����|NOTNUlL&CODE:OccupationCode"  verifyorder="1" onblur="throughwork();" onclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" ondblclick= "return showCodeList('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" onkeyup="return showCodeListKey('OccupationCode',[this,AppntOccupationCodeName],[0,1],null,null,null,null,'200');" ><input class=codename name=AppntOccupationCodeName id="AppntOccupationCodeName"  elementtype=nacessary onblur="throughwork();">
        </TD>
        <TD  class= title>�ƶ��绰</TD>
        <TD  class= input>
          <Input class="common wid" name="Mobile" id="Mobile" verify="���������ƶ��绰|len<=15" >
        </TD> 
      </TR>             
    </Table>
  </Div>	
  <DIV id="DivGrpNoname" STYLE="display:''">        
    <table  class="common">        
      <TR class="common">
        <TD  class= title>ְҵ���</TD>
        <TD  class= input>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="OccupationType" id="OccupationType"  verify="��������ְҵ���|code:OccupationType" onclick="return showCodeList('OccupationType',[this]);" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);" elementtype=nacessary>
        </TD> 
        <TD CLASS=title >����������</TD>
        <TD CLASS=input>
          <Input NAME=InsuredPeoples id="InsuredPeoples" VALUE="" readonly=true CLASS="common wid" verify="����������|num" >
        </TD>
        <TD CLASS=title>����֤��
        </TD>
        <TD CLASS=input>
          <Input NAME=WorkNo id="WorkNo" VALUE="" CLASS="common wid" >
        </TD> 
      </TR>  
	  </table>
  </Div>
  <DIV id="DivAddress" STYLE="display:''">   
    <table  class= common>   	
      <TR  class= common>
        <TD  class= title>��������</TD>
        <TD  class= input>
          <input class="common wid" dateFormat="short" onblur="getAge();" verify="�����˳�������|NOTNUlL&DATE" name="Birthday" id="Birthday" elementtype=nacessary>
        </TD>
        <TD CLASS=title>����������</TD>
        <TD CLASS=input >
          <Input NAME=InsuredAppAge id="InsuredAppAge" VALUE="" readonly=true class="common wid" verify="����������|num" >
        </TD>
        <TD  class= title>��������</TD>
        <TD  class= input>
          <Input class="common wid" name="EMail" id="EMail" verify="�������˵�������|len<=20&Email" >
        </TD>
      </TR>  	    
	    <TR CLASS=common>
	      <TD  class= title>���б���</TD>                  
        <TD  class= input>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=BankCode id="BankCode" VALUE="" CLASS="code" MAXLENGTH=20 verify="������|code:bank" onclick="return queryBank();" ondblclick="return queryBank();" onkeyup=" queryBank();" >           
        </TD>
        <TD CLASS=title width="109" >�˺�</TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=BankAccNo id="BankAccNo" VALUE="" class="common wid" MAXLENGTH=20 verify="�˺�|len<=20" onblur="checkAccNo(fm.BankCode,fm.BankAccNo);">
  	    </TD>
  	    <TD CLASS=title type="hidden"></TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=AccName id="AccName"  type="hidden" VALUE="" class="common wid" MAXLENGTH=20 verify="����|len<=20" >
  	    </TD>
      </TR>   
      <TR  class= common></TR>  
      <TR class=common>
				<TD  class= title>�ӹ�˾����</TD>
        <TD  class= input>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="AccountCode" id="AccountCode" onclick="showCodeListEx('AccountCode',[this],[0],'', '', '', true);" ondblclick="showCodeListEx('AccountCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('AccountCode',[this],[0],'', '', '', true);" >
        </TD>
  			<TD class=title>��˾ʱ��</TD>
  			<TD class=input>
          <Input class="coolDatePicker" onClick="laydate({elem: '#JoinCompanyDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=JoinCompanyDate id="JoinCompanyDate"><span class="icon"><a onClick="laydate({elem: '#JoinCompanyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
  			</TD>
  			<TD class=title>����</TD>
  			<TD class=input>
  			  <Input name="Salary" id="Salary" class="common wid">
  			</TD>
  		</TR>
		  <TR  class= common>
       	<TD  class= title>�������</TD>
        <TD  class= input>
            <!--Input class="code" name="ExecuteCom" ondblclick="showCodeListEx('comcode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('comcode',[this],[0],'', '', '', true);"-->
            <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=ExecuteCom id="ExecuteCom" onclick="return showCodeList('comcode2',[this,ManageComName],[0,1]);"  ondblclick="return showCodeList('comcode2',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode2',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id="ManageComName" readonly=true>
        </TD>
       	<TD  class= title></TD>
        <TD  class= input></TD>
        <TD  class= title></TD>
        <TD  class= input></TD>
      </TR>
    </TABLE>
  </DIV>    
</DIV>
  
<TABLE class= common>
  <TR class= common>
    <TD  class= title>
      <DIV id="divContPlan" style="display:''" >
		    <TR class= common>
			    <TD  class= title>���ռƻ�</TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="ContPlanCode" id="ContPlanCode" onclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" ondblclick="showCodeListEx('ContPlanCode',[this],[0],'', '', '', true);" onkeyup="showCodeListKeyEx('ContPlanCode',[this],[0],'', '', '', true);"  >
          </TD>
          <TD  class= title>��������</TD>
    			<TD  class= input>
		        <Input name="CertifyCode" id="CertifyCode" class="common wid">
       			   <!-- Input class=common name="CardNo"   aonkeyup="return confirmSecondInput(this,'onkeyup');" aonblur="return confirmSecondInput(this,'onblur');" onblur="checkidtype();getBirthdaySexByIDNo(this.value);getallinfo();"-->
     			</TD>
     			<TD  class= title>������� </TD>
    			<TD  class= input>
		        <Input name="StartCode" id="StartCode" class="common wid">
		      </TD>
        </TR>
        <TR class= common>
		      <TD  class= title>����ֹ��</TD>
    			<TD  class= input>
		        <Input name="EndCode" id="EndCode" class="common wid">
		      </TD>
		      <td class=title>������Ч����</td>
					<td class=input>
            <Input class="coolDatePicker" onClick="laydate({elem: '#ContCValiDate'});" verify="������Ч����|DATE verifyorder="1"" dateFormat="short" name=ContCValiDate id="ContCValiDate"><span class="icon"><a onClick="laydate({elem: '#ContCValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
					</td>
        </TR>
      </DIV>
    </TD>
    <TD  class= title>
      <DIV id="divExecuteCom" style="display:'none'" >
        <TABLE class= common>
          <TR class= common></TR>
        </TABLE>
      </DIV>
    </TD>
    <TD  class= title></TD>		
  </TR>
</TABLE>
</div>
<DIV id="DivClaim" STYLE="display:'none'"> 
  <table  class= common> 
    <TR  class= common>
      <TD  class= title>��ϵ��ַ</TD>
      <TD  class= input  colspan="3"  >
        <Input class= common3 name="PostalAddress" id="PostalAddress"  verify="����������ϵ��ַ|len<=80" >
      </TD>
      <TD  class= title>��������</TD>
      <TD  class= input>
        <Input class="common wid" name="ZipCode" id="ZipCode"  MAXLENGTH="6" verify="����������������|zipcode" >
      </TD>
            <!--TD  class= title>
                ��ϵ�绰
            </TD>
            <TD  class= input>
                <input class= common name="Phone"  verify="�������˼�ͥ�绰|len<=18" >
            </TD-->            
    </TR>  
    <TR  class= common>  
      <TD  class= title>��ַ����</TD>                  
      <TD  class= input>
        <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="InsuredAddressNo" id="InsuredAddressNo"  onclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress();"><input class=codename name=InsuredAddressNoName id="InsuredAddressNoName" readonly=true>
      </TD>
      <TD CLASS=title  >�������� </TD>
	    <TD CLASS=input COLSPAN=1  >
	      <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=AccountNo id="AccountNo" VALUE="" CLASS="code" MAXLENGTH=20 verify="������|code:bank"   onclick="return showCodeList('bank',[this]);" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
	    </TD>
      <TD  class= title>�칫�绰</TD>
      <TD  class= input>
        <Input class="common wid" name="GrpPhone" id="GrpPhone" >
      </TD>          
      <!--TD  class= title>
        ����绰
      </TD>
      <TD  class= input colspan=3 >
        <Input class= common name="Fax" verify="����|len<=15" >
      </TD-->
    </TR>         
    <TR  class= common>  
			<TD class=title>סլ�绰</TD>
			<TD class=input>
			  <Input name="HomePhone" id="HomePhone" class="common wid">
			</TD>			
           <!--TD  class= title>
            ������λ
          </TD>
          <TD  class= input >
            <Input class= common name="GrpName"  >
            
          </TD-->
    </TR> 
  </Table>          
</DIV>
<DIV id="divSalary" style="display:''">
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
<DIV id="divLCInsuredPerson" style="display:'none'">
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
