<DIV id=DivLCInsuredIndButton STYLE="display:''">
	<!--��������Ϣ���� -->
	<table>
	<tr>
	<td class="common">
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
	</td>
	<td class= titleImg>
		��������Ϣ��ϸ
	</td>
	</tr>
	</table>
</DIV>
<DIV id=DivLCInsured class="maxbox1" STYLE="display:''">
    <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            ����
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredName" id="InsuredName" value="">
	          </TD>
	          <td  class= title>
	            ְҵ����
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCode" id="InsuredOccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCodeName" id="InsuredOccupationCodeName">
	          </TD>
	          <td  class= title>
	            ְҵ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationType" id="InsuredOccupationType" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationTypeName" id="InsuredOccupationTypeName">
	          </TD>
	        </tr>
	        <tr>
	        	<td  class= title>
           	 ���
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredStature id="InsuredStature" >
          	</TD>
	        	<td  class= title>
           	 ����
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredWeight id="InsuredWeight" >
          	</TD>
          	<td  class= title>
           	 BMIֵ
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredBMI id="InsuredBMI" >
          	</TD>
	        </TR>
	        <tr>
	         	<td  class= title>
           	 ������
          	</TD>
          	<td  class= input>
            	<Input class="multiCurrency" readonly name=Insuredincome id="Insuredincome" >
          	</TD>          	
	        	<td  class= title>
	            �ۼ����ձ���
	          </TD>
	          <td  class= input>
	          <input CLASS="multiCurrency" readonly name="InsuredSumLifeAmnt" id="InsuredSumLifeAmnt" >
	          </TD>
	          <td  class= title>
	           	 �ۼ��ش󼲲�����
	          	</TD>
	          	<td  class= input>
	            	<Input class="multiCurrency wid" readonly name=InsuredSumHealthAmnt id="InsuredSumHealthAmnt" >
	          	</TD>
	        </tr>
            <tr>	        	          
	         <td  class="title" nowrap>
            	�ۼ�ҽ���ձ���
           </TD>
         	 <td  class= input>
            	<Input class="multiCurrency wid" readonly name=InsuredMedicalAmnt id="InsuredMedicalAmnt" >
           </TD>	
	      	<td  class= title>
	            �ۼ������ձ���
	          </TD>
	          <td  class= input>
	          <input CLASS="multiCurrency wid" readonly name="InsuredAccidentAmnt" id="InsuredAccidentAmnt" >
	          </TD>
	        	<td  class= title>
           	 �ۼƷ��ձ���
          	</TD>
          	<td  class= input>
            	<Input class="multiCurrency wid" readonly name=InsuredSumAmnt id="InsuredSumAmnt" >
          	</TD>
           </tr>
           <tr>
	      	<td  class= title>
           	 �ۼƱ���
          	</TD>
          	<td  class= input>
            	<Input class="multiCurrency wid" readonly name=InsuredSumPrem id="InsuredSumPrem" >
          	</TD>
	       </tr>
	</table>
</Div>
<table>
  <tr>
    <td nowrap>
   	<INPUT VALUE="  �����˽�����֪��ѯ  " class=cssButton TYPE=button name="indButton1" onclick="queryHealthImpartInd()">
   	<INPUT VALUE="  ��������������Ϣ��ѯ  " class=cssButton TYPE=button name="indButton3" onclick="queryClaim()">  
   	<INPUT VALUE="  �����˼���Ͷ�����ϲ�ѯ  " class=cssButton TYPE=button name="indButton2" onclick="showApp(2);"> 
   	<INPUT VALUE="  ��ͯ��Լ¼��  " class=cssButton TYPE=hidden name="Button2" onclick="showSpec(1);"> 
   	<INPUT VALUE="  �б����۱��¼��  " class=cssButton TYPE=button name="Button3" onclick="NewChangeRiskPlan();"> 
    </td>
  </tr>
</table>
<DIV id=DivRiskButton STYLE="display:''">
	<!--������������Ϣ���� -->
	<table>
	<tr>
	<td class="common">
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivRiskInd);">
	</td>
	<td class= titleImg>
		������Ϣ
	</td>
	</tr>
	</table>
</DIV>
<DIV id=DivRiskInd STYLE="display:''">
	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanRiskGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>
</Div>
