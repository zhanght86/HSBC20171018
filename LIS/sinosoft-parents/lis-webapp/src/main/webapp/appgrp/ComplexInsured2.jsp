<div id=DivLCInsuredButton STYLE="display:''">

  <!-- ��������Ϣ���� -->
  <table>
    <tr>
      <td class="common">
        <img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);">
      </td>
      <td class="titleImg">
        <input class="mulinetitle" value="������������"  name="InsuredSequencename" readonly >���ͻ��ţ�<input class="common" name="InsuredNo" value="">
        <input id="InsuredButBack" VALUE="��  ѯ" class=cssButton type="button" onclick="queryInsuredNo();"> �״�Ͷ���ͻ�������д�ͻ��ţ�
        <div  id= "divSamePerson" style="display:'none'">
          <font color="red">
            ��Ͷ����Ϊ�������˱��ˣ������������ѡ��
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
          <td class="common">VIP�ͻ�</td>
          <td>
            <input type="text" name="VIPValue1" class="codeno" readonly="readonly" ondblclick="return showCodeList('vipvalue', [this,AppntVIPFlagname1], [0,1]);" onkeyup="return showCodeListKey('vipvalue', [this,AppntVIPFlagname1], [0,1]);"><input type="text" name="AppntVIPFlagname1" class="codename" readonly="readonly">
          </td>
        </tr>
    <tr  class="common">  
      <td  class="title">
        �ͻ��ڲ�����
      </td>             
      <td class="input">
        <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
        <input class="codeno" name="SequenceNo" verify="�ͻ��ڲ�����|NOTNULL&NUM" verifyorder='2' CodeData="0|^1|�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" elementtype=nacessary readonly="true">
      </td>              
      <td  class="title">
        ���һ�������˹�ϵ</TD>             
      <td  class="input">
        <input class="codeno" name="RelationToMainInsured" verify="���������˹�ϵ|NOTNULL&CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName"  elementtype=nacessary readonly="readonly"  >
      </td>  
        <TD  class= title>
          ��Ͷ���˹�ϵ
          </TD>
        <TD  class= input>
          <Input class="codeno" name="RelationToAppnt" verify="��Ͷ���˹�ϵ|NOTNULL&CODE:Relation" verifyorder='2' ondblclick="return showCodeList('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('Relation', [this,RelationToAppntName],[0,1],null,null,null,null,'240');"><Input class="codename" name="RelationToAppntName" readonly="readonly" elementtype=nacessary>
         </TD> 
    </tr> 
  </table> 
</div>  
<div id=DivLCInsured style="display:''">
  <table  class= common>
    <TR  class= common>        
      <TD  class= title>
        ����
      </TD>
      <TD  class= input>
        <Input class= common name=Name onkeyup="if(LoadFlag=='1')confirmSecondInput(this,'onkeyup');" onblur="if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary verify="������������|NOTNULL&LEN<=20" verifyorder='2' onblur="getallinfo();">
      </TD>
      <TD  class= title>
        ֤������
      </TD>
      <TD  class= input>
        <Input class="codeno" name="IDType" value="0" verify="��������֤������|NOTNULL&CODE:IDType" verifyorder='2' ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);" aonblur="getallinfo();" ><input class=codename name=IDTypeName readonly=true elementtype=nacessary>
      </TD>
      <TD  class= title>
        ֤������
      </TD>
      <TD  class= input>
        <Input class= common name="IDNo" onblur="checkidtype2();getBirthdaySexByIDNo2(this.value);getAge2();if(LoadFlag=='1')confirmSecondInput(this,'onblur');" elementtype=nacessary  verify="��������֤������|NOTNULL&LEN<=20" verifyorder='2'>
      </TD>                                                                                                                                                                                                        
    </TR>
    
    <TR  class= common>
      <TD  class= title>
        �Ա�
      </TD>
      <TD  class= input>
        <Input class="codeno" name=Sex verify="���������Ա�|NOTNULL&CODE:Sex" verifyorder='2' ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly="readonly" elementtype=nacessary>
      </TD>
      <TD  class= title>
        ��������
      </TD>
      <TD  class= input>
        <input class="common" dateFormat="short" elementtype=nacessary onblur=" checkinsuredbirthday(); getAge2();" name="Birthday" verify="�������˳�������|NOTNULL&DATE" verifyorder='2' >
          </TD>
      </TD>
      <TD CLASS=title>
        ����������
      </TD>
      <TD CLASS=input COLSPAN=1>
        <Input NAME=InsuredAppAge verify="����������|value>=0" verifyorder="2" VALUE="" readonly=true CLASS=common verify="����������|NUM" >
      </TD>
    </TR>
    
    <TR  class= common>
      <TD  class= title>
        ����״��
      </TD>
      <TD  class= input>
        <Input class="codeno" name=Marriage ondblclick="return showCodeList('Marriage',[this,MarriageName],[0,1]);" onkeyup="return showCodeListKey('Marriage',[this,MarriageName],[0,1]);"><input class=codename name=MarriageName readonly=true elementtype=nacessary>
      </TD>  
      <TD  class= title>
        ����
      </TD>
      <TD  class= input>
      <input class="codeno" name="NativePlace"  ondblclick="return showCodeList('NativePlace',[this,NativePlaceName],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,NativePlaceName],[0,1]);"><input class=codename name=NativePlaceName readonly=true>
      </TD>              
      <TD  class= title>
        ְҵ����
      </TD>
      <TD  class=input>
        <Input class="codeno" name="OccupationCode" verify="��������ְҵ����|CODE:OccupationCode&NOTNULL" verifyorder='2' ondblclick="return showCodeList('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onkeyup="return showCodeListKey('OccupationCode',[this,OccupationCodeName],[0,1],null,null,null,null,'240');" onfocus="getdetailwork2();"><input class=codename name=OccupationCodeName elementtype=nacessary readonly=true>
        <Input type="hidden" name="OccupationType"> 
      </td>

    </TR>
<!--    
    <TR  class= common>
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
        <input class="codeno" name="License" verify="����|CODE:License" CodeData="0|^1|�� ^2|��" ondblclick="return showCodeListEx('License',[this,LicenseName],[0,1]);" onkeyup="return showCodeListKeyEx('License',[this,LicenseName],[0,1]);"><input class=codename name=LicenseName readonly=true>
      </td-->
      <!--TD  class= title>
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
      </Td>          
    </TR>
    -->
    <!--TR class= common>
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
    </TR-->   
    <tr class= common>
        <td class=title>
        ��������	
        </td>
        <td  class= input>
          <input class="codeno" name=LicenseType verify="�������˼�������|CODE:LicenseType" verifyorder='2' ondblclick="return showCodeList('LicenseType',[this,LicenseTypeName],[0,1]);" onkeyup="return showCodeListKey('LicenseType',[this,LicenseTypeName],[0,1]);"><input class=codename name=LicenseTypeName readonly=true>
        </td>
          <!--TD  class= title>
              ְҵ���
          </TD>
          <TD  class= input>
              <Input class="code" name="OccupationType"  verify="��������ְҵ���|CODE:OccupationType" ondblclick="return showCodeList('OccupationType',[this]);" onkeyup="return showCodeListKey('OccupationType',[this]);">
          </TD>
        <TD  class= title>
          �Ƿ�����
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
                  ��ַ����
              </TD>                  
              <TD  class= input>
                  <Input class="codeno" name="InsuredAddressNo"  ondblclick="getaddresscodedata2();return showCodeListEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata2(); return showCodeListKeyEx('GetAddressNo',[this,InsuredAddressNoName],[0,1],'', '', '', true);" onfocus="getdetailaddress2();"><input class=codename name=InsuredAddressNoName readonly=true>
              </TD>
  	      </TR>  
        <tr class='common'>
          <td class="title">
            ͨѶ��ַ��
          </td>
        </tr>
        <TR  class= common>
          <TD  class= title>
            ʡ
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="������ʡ��|NOTNULL&NUM&LEN=2"  verifyorder="2" name="InsuredProvince"  ondblclick="return showCodeList('Province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('Province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            ��
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="�����˳���|NOTNULL&NUM&LEN=4"  verifyorder="2" name="InsuredCity"  ondblclick="showCodeList('City',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);" onkeyup="return showCodeListKey('City',[this,InsuredCityName],[0,1],null,[fm.InsuredProvince.value],['upplacename']);"><input class=codename name=InsuredCityName readonly=true elementtype=nacessary>
          </TD>  
          <TD  class= title>
            ��/��
          </TD>
          <TD  class= input>
            <Input class="codeno" verify="��������/��|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredDistrict"  ondblclick="showCodeList('District',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');" onkeyup="return showCodeListKey('District',[this,InsuredDistrictName],[0,1],null,[fm.InsuredCity.value],['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName readonly=true elementtype=nacessary>
          </TD>  
        </TR>        
          <TR  class= common>
  
              <TD class= title>
                  �ֵ�
              </TD>
              <TD  class= input  colspan="3"  >
                <Input class= common3 name="PostalAddress" elementtype=nacessary verify="����������ϵ��ַ|NOTNULL&LEN<=80" verifyorder='2' >
              </TD>
              <TD  class= title>
                  ��������
              </TD>
              <TD  class= input>
                <Input class= common name="ZIPCODE" elementtype=nacessary MAXLENGTH="6" verify="����������������|NOTNULL&ZIPCODE" verifyorder='2' >
              </TD>
              <!--TD  class= title>
                  ��ϵ�绰
              </TD>
              <TD  class= input>
                  <input class= common name="Phone"  verify="�������˼�ͥ�绰|LEN<=18" >
              </TD-->            
          </TR>
          <TR  class= common>
              <TD  class= title>
                  �ƶ��绰
              </TD>
              <TD  class= input>
                  <Input class= common name="Mobile" verify="���������ƶ��绰|LEN<=15&PHONE" verifyorder='2' >
              </TD>
            <TD  class= title>
              �칫�绰
            </TD>
            <TD  class= input>
              <Input class= common name="GrpPhone" verify="�������˰칫�绰|LEN<=15&PHONE" verifyorder='2' >
            </TD>          
            <TD  class= title>
              ����绰
            </TD>
            <TD  class= input colspan=3 >
              <Input class= common name="Fax" verify="�������˴���绰|LEN<=15&PHONE" verifyorder='2' >
            </TD>
          </TR>         
        <TR  class= common>  
  			<TD class=title>
  			  סլ�绰
  			</TD>
  			<TD class=input>
  			  <Input name="HomePhone" class=common verify="��������סլ�绰|LEN<=15&PHONE" verifyorder='2'>
  			</TD>			
             <TD  class= title>
              ������λ
            </TD>
            <TD  class= input >
              <Input class= common name="GrpName">
              
            </TD>
              <TD  class= title>
                  ��������
              </TD>
              <TD  class= input>
                  <Input class= common name="EMail" verify="�������˵�������|LEN<=20&EMAIL" verifyorder='2' >
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
              <Input class= common name="GrpZIPCODE"  >
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
  		  <Input name="HomeZIPCODE" class=common>
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
                              ���ռƻ�
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
                              �������
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
                  ������ʻ�
              </TD>                  
              <TD  class= input>
                  <Input class="code" name="AccountNo"  CodeData="0|^1|�ɷ��ʻ�^2|����" ondblClick="showCodeListEx('AccountNo',[this]);" onkeyup="showCodeListKeyEx('AccountNo',[this]);" onfocus="getdetailaccount();">
              </TD>
      </TR> 
            <TR CLASS=common>
  	    <TD CLASS=title  >
  	      �������� 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=BankCode VALUE="" CLASS="code" MAXLENGTH=20 verify="������|CODE:bank" ondblclick="return showCodeList('bank',[this]);" onkeyup="return showCodeListKey('bank',[this]);" >
  	    </TD>
  	    <TD CLASS=title >
  	      ���� 
  	    </TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=AccName VALUE="" CLASS=common MAXLENGTH=20 verify="����|LEN<=20" >
  	    </TD>
              <TD CLASS=title width="109" >
  	      �˺�</TD>
  	    <TD CLASS=input COLSPAN=1  >
  	      <Input NAME=BankAccNo class="code" VALUE="" CLASS=common ondblclick="return showCodeList('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" onkeyup="return showCodeListKey('accNUM',[this],null,null,fm.AppntNo.value,'CustomerNo');" verify="�����ʺ�|LEN<=40" onfocus="getdetail();">
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
  	  <DIV id=DivGrpNoname STYLE="display:'none'">        
      <table  class= common>        
          <TR class= common>
            <TD CLASS=title>
              �������ͱ��
            </TD>
            <TD CLASS=input COLSPAN=1>
              <Input NAME=PolTypeFlag VALUE="0" CLASS=code ondblclick="showCodeList('PolTypeFlag', [this]);" onkeyup="showCodeListKey('PolTypeFlag', [this]);" verify="�������ͱ��" >
            </TD>        
              <TD CLASS=title>
                  ����������
              </TD>
              <TD CLASS=input COLSPAN=1>
                  <Input NAME=InsuredPeoples VALUE="" readonly=true CLASS=common verify="����������|NUM" >
              </TD>
  	    </TR>  
        </table>
  </Div>

</DIV>
