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

<DIV id=DivLCAppntIndButton STYLE="display:''">
<!-- Ͷ������Ϣ���� -->
	<Table>
		<TR>
			<TD class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCAppntInd);">
			</TD>
			<TD class= titleImg>
				Ͷ������Ϣ���ͻ��ţ�<Input class= common  name=AppntNo readOnly>��
				<!-- <INPUT id="butBack" VALUE="��  ѯ" TYPE=button class= cssButton onclick="queryAppntNo();">
				�״�Ͷ���ͻ�������д�ͻ��� -->
			</TD>
		</TR>
	</Table>
</DIV>

<DIV id=DivLCAppntInd STYLE="display:''">
	<Table  class= common>
		<!-- <TR class="common" STYLE="display:none">
			<TD class="common">VIP�ͻ�</TD>
			<TD>
				<input type="text" name="AppntVIPValue" class="codeno" ><input type="text" name="AppntVIPFlagname" class="codename" readonly="readonly">
			</TD>
		</TR> -->
	    <TR  class= common>        
			<TD class=title>Ͷ��������</TD>
			<TD  class=input >
				<Input class="common wid" name=AppntName id=AppntName verifyorder="1" >
			</TD>
			<TD class=title> �Ա� </TD>
            <TD>
                 <Input verifyorder="1" class="code" name="AppntSex" id=AppntSex CodeData="0|^0|��^1|Ů"
				 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				 ondblClick="showCodeListEx('ForSex',[this],[0,1]);"
            	 onkeyup="showCodeListKeyEx('ForSex',[this],[0,1]);" >
            </td>
			<TD class=title>��������</TD>
			<TD  class= input>
				<input class="common wid" name="AppntBirthday" id=AppntBirthday verifyorder="1">
			</TD>
			<TD >ϵ�����˵�</TD>
			<TD  class= input>
				<Input class="code" name="RelationToInsured" id=RelationToInsured verifyorder="1"
				style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="return showCodeList('Relation', [this],[0,1]);" 
				onkeyup="return showCodeListKey('Relation', [this],[0,1]);">
			</TD> 
	    </tr>
	    <tr class=common>
			<TD class=title>֤������</TD>
			<TD  class= input>
				<Input verifyorder="1" class="code" name="AppntIDType" id=AppntIDType CodeData="0|^0|���֤^1|����^2|����֤^3|����^4|���ڱ�^5|ѧ��֤^6|����֤^7|����֤^8|����^9|��֤��"
            	style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblClick="showCodeListEx('ForIDType',[this],[0,1]);"
            	onkeyup="showCodeListKeyEx('ForIDType',[this],[0,1]);"></TD>
			<TD class=title>֤������</TD>
			<TD class= input colspan=3>
				<Input verifyorder="1" class=common3  colspan=3 name="AppntIDNo" id=AppntIDNo  >
			</TD>
		</TR>
		<tr class=common>
			<TD  class= title>ͨ�ŵ�ַ</TD>
			<TD  class= input colspan=3 >
				<Input verifyorder="1" class='common3' name="AppntPostalAddress" id=AppntPostalAddress >
			</TD>
			<TD  class= title>��ϵ�绰</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="AppntMobile" id=AppntMobile >
			</TD>         
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input verifyorder="1" class='common wid' name="AppntZipCode" id=AppntZipCode >
			</TD>
		</tr>
		</table>
		<DIV id=Bank1 STYLE="display:none">
		<Table  class= common>
		<TR  class= common>
			<TD  class= title STYLE="display:none">Ͷ��������</TD>
			<TD  class= input STYLE="display:none">
				<input class="common wid" name="AppntAge" id=AppntAge STYLE="display:none">
			</TD>
			<TD class=title name="AppntNativePlace1"id=AppntNativePlace1>����</TD>
			<TD>
				<input verifyorder="1" class="code" name="AppntNativePlace" id=AppntNativePlace
				style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="return showCodeList('NativePlace',[this,null],[0,1]);" 
				onkeyup="return showCodeListKey('NativePlace',[this,null],[0,1]);">
			</TD>
			<TD class=title>�������ڵ�</TD>
			<TD>
				<Input verifyorder="1" class="common wid" name=AppntRgtAddress id=AppntRgtAddress >
			</TD>
			<TD  class= title>����״��</TD>
			<td>
			<Input verifyorder="1" class="code" name="AppntMarriage" id=AppntMarriage CodeData="0|^0|δ��^1|�ѻ�^2|����^3|ɥż"
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
			ondblClick="showCodeListEx('ForMarriage',[this],[0,1]);"
            onkeyup="showCodeListKeyEx('ForMarriage',[this],[0,1]);">
            </td>
		</TR>
		</table>
		
		<table class=common>
		<TR  class= common>
			<TD  class= title>
		        ְҵ
		    </TD>
		    <TD  class= input>
		        <Input verifyorder="1" class="common wid" name="AppntWorkType" id=AppntWorkType  value="" >
		    </TD>
		    <TD  class= title>ְҵ����</TD>
			<TD class= input>
				<Input verifyorder="1" class="common wid" name="AppntOccupationCode" id=AppntOccupationCode >
				<!-- <Input verifyorder="1" class="code" name="AppntOccupationCode" ondblclick= "return onClickOccupation(AppntOccupationCode);" onkeyup="return onClickUpOccupation(AppntOccupationCode);" > -->
			</TD>
		</TR>
		</table>
		</div>
		<DIV id=Bank2 STYLE="display:none">
		<table class=common>
		<TR  class= common>
			<TD  class= title>סַ</TD>
			<TD  class= input colspan=3 >
				<Input verifyorder="1" class='common3' name="AppntHomeAddress" id=AppntHomeAddress >
			</TD>
			<TD  class= title>סַ��������</TD>
			<TD  class= input>
				<Input verifyorder="1" class='common' name="AppntHomeZipCode" id=AppntHomeZipCode  >
			</TD>
		</TR>	
		<TR  class= common>        
			
			<TD  class= title>������ϵ�绰</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="AppntCompanyPhone" id=AppntCompanyPhone >
			</TD>       
			<TD  class= title>��������</TD>
			<TD  class= input>
				<Input verifyorder="1" class="common wid" name="AppntEMail" id=AppntEMail MAXLENGTH=40>
			</TD>  
		</TR>
    <TR  class= common>
    		<TD  class= title>������λ</TD>
			<TD  class= input colspan=3 >
				<Input verifyorder="1" class='common3' name="AppntCompanyAddress" id=AppntCompanyAddress >
			</TD>
	</tr>
	<TR  class= common>
			
		  	  <TD  class= title>
		        ��ְ
		      </TD>
		      <TD  class= input>
		        <Input verifyorder="1" class="common wid" name="AppntPluralityType" id=AppntPluralityType  value="" >
		      </TD>
		
	</TR>
    <TR  class= common style="display:none">   
			<TD class=title>ְҵ���</TD>
			<TD class= input>
				<Input class="codeno" name="AppntOccupationType" id=AppntOccupationType>
				<input class=codename name=AppntOccupationTypeName id=AppntOccupationTypeName readonly=true>
			</TD> 	
		</TR> 
		<TR  class= common style="display:none">
			<TD  class= title>��ַ����</TD>
			<TD  class= input>
				<Input class="codeno" name="AppntAddressNo" id=AppntAddressNo >
				<input class=codename name=AddressNoName id=AddressNoName readonly=true>
			</TD>  
		</TR>
		<TR class='common' style="display:none">
			<TD class="title">ͨѶ��ַ��</TD>
		</TR>
    
		<TR  class= common style="display:none">
			<TD  class= title>ʡ</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ����ʡ��|num&LEN=6" name="AppntProvince"  id=AppntProvince
				style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="return showCodeList('province',[this,AppntProvinceName],[0,1],null,null,null,1);" 
				onkeyup="return showCodeListKey('province',[this,AppntProvinceName],[0,1],null,null,null,1);">
				<input class=codename name=AppntProvinceName id=AppntProvinceName onblur="getAddressName('province','AppntProvince','AppntProvinceName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ���˳���|num&LEN=6"   name="AppntCity" id=AppntCity
				style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="showCodeList('city',[this,AppntCityName],[0,1],null,[fm.AppntProvince.value],['upplacename']);" 
				onkeyup="return showCodeListKey('city',[this,AppntCityName],[0,1],null,fm.AppntProvince.value,['upplacename'],1);">
				<input class=codename name=AppntCityName id=AppntCityName  onblur="getAddressName('city','AppntCity','AppntCityName');" readonly=true elementtype=nacessary>
			</TD>  
			<TD  class= title>��/��</TD>
			<TD  class= input>
				<Input class="codeno" verify="Ͷ������/��|num&LEN=6"  name="AppntDistrict" id=AppntDistrict
				style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
				ondblclick="showCodeList('district',[this,AppntDistrictName],[0,1],null,[fm.AppntCity.value],['upplacename'],null,'240');" 
				onkeyup="return showCodeListKey('district',[this,AppntDistrictName],[0,1],null,fm.AppntCity.value,['upplacename'],1,'240');">
				<input class=codename name=AppntDistrictName id=AppntDistrictName onblur="getAddressName('district','AppntDistrict','AppntDistrictName');" readonly=true elementtype=nacessary>
			</TD>  
		</TR>        
		<TR class=common style="display:none">
			<TD  class= title>����绰</TD>
			<TD  class= input>
				<Input class="common wid" name="AppntFax" id=AppntFax verify="Ͷ���˴���绰|LEN<=15&PHONE"   >
			</TD> 
			<TD  class= title></TD>
			<TD  class= input></td>	
			<TD  class= title></TD>
			<TD  class= input></td>				
		</TR>
		<TR  class=common style="display:none">
			<TD  class= title>סլ�绰</TD>
			<TD  class= input>
				<input class="common wid" name="AppntHomePhone" id=AppntHomePhone verify="Ͷ����סլ�绰|LEN<=18&PHONE"   aelementtype=nacessary >
			</TD>
			<TD  class= title></TD>
			<TD  class= input></td>	
			<TD  class= title></TD>
			<TD  class= input></td>	
		</TR>        
	</Table>
	</div>
  	<!-- <hr> --> 
	<div id="DivLCAccount" style="display:none">
		<Table>
			<TR>
				<TD class=common>
					<img id="accountImg" src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divLCAccount1);">
				</TD>
				<TD class= titleImg>�ɷ���Ϣ</TD>
			</TR>
		</Table>   
		<div id="divLCAccount1" style="display:''">
        	<Table class="common">    
	          	<TR class="common">
	          	</TR>
				<TR class='common'>
					<TD CLASS=title >�����ʻ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntAccName ID=AppntAccNameVALUE="" CLASS="common wid" MAXLENGTH=20  >
					</TD>
					<TD CLASS=title width="109" > �����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=AppntBankAccNo class="common wid" VALUE="" >
						<input type="hidden" class=codename name=AppntBankAccNoName id=AppntBankAccNoName readonly=true>
					</TD>
				</TR>
				<TR class="common">
					<TD class="title">���ڽ�����ʽ</TD>
					<TD>
						<input class="codeno" name="SecPayMode" id=SecPayMode>
						<input class="codename" name="SecPayModeName" id=SecPayModeName readonly="readonly" >
					</TD>
					<TD class="title" >�ס������˺�һ��</TD>
					<TD class='title'>
						<input type="checkbox" name='theSameAccount' id=theSameAccount value="true">
					</TD>
				</TR>
				<TR CLASS=common>
					<TD CLASS=title  >����ת�ʿ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=SecAppntBankCode id=SecAppntBankCode VALUE="" CLASS="codeno" MAXLENGTH=20 verify="������|CODE:bank"   
						style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
						ondblclick="return showCodeList('bank',[this,SecAppntBankCodeName],[0,1]);" 
						onkeyup="return showCodeListKey('bank',[this,SecAppntBankCodeName],[0,1]);" >
						<input class=codename name=SecAppntBankCodeName id=SecAppntBankCodeName readonly=true>
					</TD>
					<TD CLASS=title >�����ʻ����� </TD>
					<TD CLASS=input COLSPAN=1  >
						<Input NAME=SecAppntAccName id=SecAppntAccName VALUE="" CLASS="common wid" MAXLENGTH=20 verify="����|LEN<=20"  >
					</TD>
					<TD CLASS=title width="109" >�����˺�</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input  NAME=SecAppntBankAccNo id=SecAppntBankAccNo class="common wid" VALUE="" CLASS=common ><input type="hidden" class=codename name=aa readonly=true>
					</TD>
				</TR>
				<TR CLASS=common>
					<!--<TD CLASS=title >�Զ�������־ </TD>
					
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="RnewFlag" 
            				   CodeData="0|^-2|������^-1|�Զ�����^0|�˹�����"
            				   ondblClick="showCodeListEx('ModeSelect_3',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_3',[this],[0,1]);">
					</TD>-->
					<TD CLASS=title width="109" >�������ͷ�ʽ</TD>
					<TD CLASS=input COLSPAN=1  >
						<Input class="code" name="GetPolMode"  id=GetPolMode
						style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            				   CodeData="0|^0|����������ȡ^1|�ʼĻ�ר��"
            				   ondblClick="showCodeListEx('ModeSelect_2',[this],[0,1]);"
            				   onkeyup="showCodeListKeyEx('ModeSelect_2',[this],[0,1]);">
					</TD>
				</TR>
	        </Table>  
      	</div> 
    </div>
</DIV>

<Div  id= "divLCAppnt1" style= "display: none">
<Table  class= common align='center' >
  <TR  class= common>
  </TR>
  <TR  class= common>
    <TD  class= title>
      Ͷ���˼���
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntGrade id=AppntGrade>
    </TD>
  <TR  class= common>
    <TD  class= title>
      Ͷ��������
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntType id=AppntType>
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      �������
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntMakeDate id=AppntMakeDate>
    </TD>
    <TD  class= title>
      ���ʱ��
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntMakeTime id=AppntMakeTime>
    </TD>
  </TR>
  <TR  class= common>
    <TD  class= title>
      ���һ���޸�����
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyDate id=AppntModifyDate >
    </TD>
    <TD  class= title>
      ���һ���޸�ʱ��
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AppntModifyTime id=AppntModifyTime>
    </TD>
  </TR>
  <input type=hidden name=AppntFillNo id=AppntFillNo>
</Table>
    </Div>
