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
	<!-- ��������Ϣ���� -->
	<Table>
		<TR>
			<TD class="common">
				<img src= "../common/images/butExpand.gif" style= "cursor:hand;" onclick="showPage(this,DivLCInsured);">
			</TD>
			<TD class="titleImg">
				<input class="mulinetitle" value="������������"  name="InsuredSequencename" id="InsuredSequencename" readonly >���ͻ��ţ�<input class="common" name="InsuredNo" id="InsuredNo" value="" readOnly>��
				<Div  id= "divSamePerson" style="display:'none'">
					<font color="red">��Ͷ����Ϊ�������˱��ˣ������������ѡ��<input type="checkbox" name="SamePersonFlag" id="SamePersonFlag" onclick="isSamePerson();"></font>
				</Div>
			</TD>
		</TR>
	</Table>
</Div>

<div class="maxbox">
<Div id="DivRelation" style="display:none">
	<Table  class="common">  
		<TR class="common">
			<TD class="title">VIP�ͻ�</TD>
			<TD class=input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" type="text" name="VIPValue1" id="VIPValue1" class="codeno"><input type="text" name="AppntVIPFlagname1" id="AppntVIPFlagname1" class="codename" readonly="readonly">
			</TD>
			<td class="title"></td>
			<td class="input"></td>
			<td class="title"></td>
			<td class="input"></td>
		</TR>
		<TR class="common">  
			<TD  class="title">���һ�������˹�ϵ</TD>             
			<TD  class="input">
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="RelationToMainInsured" id="RelationToMainInsured"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName"  readonly="readonly"  >
			</TD>  
		</TR> 
	</Table> 
</Div>  


<Div id="DivLCInsured" style="display:''">
	<Table  class= common>
		<TR class= common>        
			<TD class= title>����</TD>
			<TD class= input>
				<Input verifyorder="1" class="common wid" name=Name id="Name">
			</TD>
			<TD class=title> �Ա� </TD>
            <TD class=input>
            	<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="Sex" id="Sex" CodeData="0|^0|��^1|Ů"
            		   						   onClick="showCodeListEx('ForSex',[this],[0,1]);"
            		   						   ondblClick="showCodeListEx('ForSex',[this],[0,1]);"
            								   onkeyup="showCodeListKeyEx('ForSex',[this],[0,1]);">
            </td>
			<TD  class= title>��������</TD>
			<TD  class= input>
				<input verifyorder="1" class="common wid" dateFormat="short" name="Birthday" id="Birthday">                 
			</TD>
		</TR>
	</Table>
	<Table  class= common>
		<TR  class= common>	
			<TD  class= title>֤������</TD>
			<TD  class= input>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="IDType" id="IDType" CodeData="0|^0|���֤^1|����^2|����֤^3|����^4|���ڱ�^5|ѧ��֤^6|����֤^7|����֤^8|����^9|��֤��"
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
			<TD class=title>֤������</TD>
			<TD class= input  colspan="3">
				<Input verifyorder="1" class= common3 colspan=3 name="IDNo" id="IDNo" >
			</TD>
			<td class=title>��Ч����</td>
			<TD class=input>
				<input verifyorder="1" class="common wid"  name="IDExpDate" id="IDExpDate">
			</TD>
		</TR>
	</Table>
	<Table  class= common>
		<TR class= common style="display:none">
			<TD CLASS=title>����������</TD>
			<TD CLASS=input COLSPAN=1>
				<Input NAME=InsuredAppAge id="InsuredAppAge">
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>����</TD>
			<TD  class= input>
				<input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="NativePlace" id="NativePlace" onclick="return showCodeList('NativePlace',[this,null],[0,1]);" ondblclick="return showCodeList('NativePlace',[this,null],[0,1]);" onkeyup="return showCodeListKey('NativePlace',[this,null],[0,1]);">
			</TD>
			<TD  class= title>�������ڵ�</TD>
			<TD  class=input >
				<Input verifyorder="1" class="common wid"  name=RgtAddress id="RgtAddress">
			</TD>
			<TD>����״��</TD>
			<td>
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="Marriage" id="Marriage" CodeData="0|^0|δ��^1|�ѻ�^2|����^3|ɥż" onClick="showCodeListEx('ForMarriage',[this],[0,1]);" ondblClick="showCodeListEx('ForMarriage',[this],[0,1]);" onkeyup="showCodeListKeyEx('ForMarriage',[this],[0,1]);">
            </td>
		 </TR>
		 <!-- 
		<TR  class= common>
			<TD  class= title>ͨ�ŵ�ַ</TD>
			-->
		<TR class=common>
			<TD  class= input colspan=3 >
				<Input type=hidden verifyorder="1" class='common3' name="PostalAddress" id="PostalAddress">
			</TD>
		<!--
			<TD  class= title>ͨѶ��ַ��������</TD>
			-->
			<TD  class= input>
				<Input type=hidden verifyorder="1" class='common' name="ZipCode" id="ZipCode">
			</TD>
		</TR>
		<TR class=common>
			<TD class= title>סַ</TD>
			<TD  class= input  colspan="3"  >
				<Input verifyorder="1" class= common3 name="HomeAddress" id="HomeAddress">
			</TD>
			<TD  class= title>��������</TD>
			<TD  class= input> 
				<Input verifyorder="1" class="common wid"  name="HomeZipCode" id="HomeZipCode">
			</TD>
		</TR>	 
		<TR class= common>
			<TD  class= title>��ϵ�绰(1)</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="Mobile" id="Mobile">
			</TD>
			<TD  class= title>��ϵ�绰(2)</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="CompanyPhone" id="CompanyPhone">
			</TD>                    
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="EMail" id="EMail">
			</TD>  
		</TR>    
		<TR class= common>    
			<TD  class= title>������λ</TD>
			<TD  class= input colspan=3 ><Input verifyorder="1" class='common3' name="CompanyAddress" id="CompanyAddress" verifyorder="2"> </TD>         
			<TD>�Ƿ�ӵ�й���ҽ�ơ����ҽ�Ʊ���</TD>
			<TD CLASS=input COLSPAN=1  >
				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" verifyorder="1" class="code" name="SocialInsuFlag" id="SocialInsuFlag" CodeData="0|^0|��^1|��" onClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);" onClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);" ondblClick="showCodeListEx('SocialInsuFlag',[this],[0,1]);" onkeyup="showCodeListKeyEx('SocialInsuFlag',[this],[0,1]);">
			</TD>
		</TR>
		<TR class= common>
			<TD  class= title>
		        ְҵ
		    </TD>
		    <TD  class= input>
		       <Input verifyorder="1" class="common wid"  name="WorkType" id="WorkType" value="" >
		    </TD>
		  	<TD  class= title>
		        ��ְ
		    </TD>
		    <TD  class= input>
		       <Input verifyorder="1" class="common wid" name="PluralityType" id="PluralityType" value="" >
		    </TD>
			<TD  class= title>ְҵ����</TD>
			<TD  class=input>
				<Input verifyorder="1" class="common wid" name="OccupationCode" id="OccupationCode"/>
				<!-- <Input verifyorder="1" class="common" name="OccupationCode" ondblclick= "return showCodeList('occupationcode',[this,null],[0,1]);" onkeyup="return showCodeList('occupationcode',[this,null],[0,1]);"/> -->
			</TD>
		</TR>
		<TR class=common style="display:none">
			<TD  class= title>ְҵ���</TD>
			<TD  class= input>
			  <Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="OccupationType" id="OccupationType"><input class=codename name=OccupationTypeName id="OccupationTypeName" readonly=true>
			</TD>
		</TR>
		<TR class= common style="display:none">
			
			<TD class=title>��������	</TD>
			<TD  class= input>
				<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name=LicenseType id="LicenseType"><input class=codename name=LicenseTypeName id="LicenseTypeName" readonly=true>
			</TD>
		</TR>               
	</Table> 
     <!--#####���½������˵ĵ�ַ��¼��־�޸ķǱ�¼��־,2005-11-02�޸�####-->
  	<Div id="DivAddress" STYLE="display:none">   
      	<Table  class= common>   	
			<TR class= common>
				<TD  class= title>��ַ����</TD>                  
				<TD  class= input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredAddressNo" id="InsuredAddressNo"><input class=codename name=InsuredAddressNoName id="InsuredAddressNoName" readonly=true>
				</TD>
			</TR>  
			<TR class='common' >
				<TD class="title">ͨѶ��ַ��</TD>
			</TR>
			<TR class= common>
				<TD  class= title>ʡ</TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="������ʡ��|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredProvince"  ondblclick="return showCodeList('province',[this,InsuredProvinceName],[0,1]);" onkeyup="return showCodeListKey('province',[this,InsuredProvinceName],[0,1]);"><input class=codename name=InsuredProvinceName readonly=true elementtype=nacessary>
				    --> 
   					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredProvince" id="InsuredProvince"><input class=codename name=InsuredProvinceName id="InsuredProvinceName">
				</TD>  
				<TD  class= title>�� </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="�����˳���|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredCity"  ondblclick="showCodeList('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);" onkeyup="return showCodeListKey('city',[this,InsuredCityName],[0,1],null,fm.InsuredProvince.value,['upplacename']);"><input class=codename name=InsuredCityName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredCity" id="InsuredCity"><input class=codename name=InsuredCityName  id="InsuredCityName" readonly=true>
				</TD>  
				<TD  class= title>��/�� </TD>
				<TD  class= input>
					<!--
					<Input class="codeno" verify="��������/��|NOTNULL&NUM&LEN=6"  verifyorder="2" name="InsuredDistrict"  ondblclick="showCodeList('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');" onkeyup="return showCodeListKey('district',[this,InsuredDistrictName],[0,1],null,fm.InsuredCity.value,['upplacename'],null,'240');"><input class=codename name=InsuredDistrictName readonly=true elementtype=nacessary>
				    --> 
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" name="InsuredDistrict" id="InsuredDistrict"><input class=codename  >
				</TD>  
			</TR>        
			<TR class= common>
				
				<!--TD  class= title>��ϵ�绰</TD>
				<TD  class= input><input class= common name="Phone"  verify="�������˼�ͥ�绰|LEN<=18" ></TD-->            
			</TR>
			     
			<TR class= common>  
				<TD class=title>סլ�绰</TD>
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
                              ���ռƻ�
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
                              �������
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
                ������ʻ�
            </TD>                  
            <TD  class= input>
                <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name="AccountNo" id="AccountNo" CodeData="0|^1|�ɷ��ʻ�^2|����" >
            </TD>
            <td class= title></td>
            <td class= input></td>
            <td class= title></td>
            <td class= input></td>
        </TR> 
        <TR CLASS=common>
  	    	<TD CLASS=title  >
  	      		�������� 
  	    	</TD>
  	    	<TD CLASS=input COLSPAN=1  >
  	      		<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=InsuredBankCode id="InsuredBankCode" VALUE="" CLASS="code" MAXLENGTH=20 verify="������|CODE:bank" >
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
              			�������ͱ��
            		</TD>
            		<TD CLASS=input COLSPAN=1>
              			<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=PolTypeFlag id="PolTypeFlag" VALUE="0" CLASS=code >
            		</TD>        
              		<TD CLASS=title>
                  		����������
              		</TD>
              		<TD CLASS=input COLSPAN=1>
                  		<Input NAME=InsuredPeoples id="InsuredPeoples" VALUE="" readonly=true CLASS="common wid"  verify="����������|NUM" >
              		</TD>
              		<td class= title></td>
            		<td class= input></td>
  	    		</TR>  
  	    		<input type=hidden name=InsuredFillNo id="InsuredFillNo">
        	</Table>
  		</Div>

	</Div>
</div>