  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
	<DIV id=DivLCInsuredIndButton STYLE="display:''">
	<!--��������Ϣ���� -->
	<table class=common border=0 width=100%>
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
<DIV id=DivLCInsuredInd class="maxbox" STYLE="display:''">
    <table  class= common>  
        <TR  class= common>  
	       <td CLASS="title">�����˺���
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="InsuredNo" id="InsuredNo" MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
	        <TD  class= title>
                �ͻ��ڲ�����
          </TD>             
            <TD  class= input>
                <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
                &nbsp;&nbsp;<input class="codeno" name="SequenceNoCode" id="SequenceNoCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;"  elementtype=nacessary  CodeData="0|^1|��һ�������� ^2|�ڶ��������� ^3|������������" onclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" id="SequenceNoName" name="SequenceNoName" readonly="true">
            </TD>              
	        <TD  class= title>
                ���һ�������˹�ϵ</TD>             
            <TD  class= input>
                &nbsp;&nbsp;<Input class="codeno" name="RelationToMainInsured" id="RelationToMainInsured" style="background: url(../common/images/select--bg_03.png) no-repeat center; "  elementtype=nacessary verify="���������˹�ϵ|code:Relation" onclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" readonly="readonly"  >
            </TD>                  
        </TR> 
    </table> 
</DIV>  

<DIV id=DivLCInsured STYLE="display:''">
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
            	<Input class="readonly wid" readonly name=InsuredStature id="InsuredStature" >
          	</TD>
	        	<td  class= title>
           	 ����
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=InsuredWeight id="InsuredWeight" >
          	</TD>
          	<td  class= title>
           	 BMIֵ
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=InsuredBMI id="InsuredBMI" >
          	</TD>
	        </TR>
	        <tr>
	         	<td  class= title>
           	 ������
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=Insuredincome id="Insuredincome" >
          	</TD>
          	<td  class="title" nowrap>
            	���������
          	</TD>
         	 <td  class= input>
            	<Input class="readonly wid" readonly name=InsuredBlacklistFlag id="InsuredBlacklistFlag" type="hidden">
            	<Input class="readonly wid" readonly name=InsuredBlacklistFlagName id="InsuredBlacklistFlagName" >
          	</TD>
	        	<td  class= title>
	            �ۼ����ձ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredSumLifeAmnt" id="InsuredSumLifeAmnt" >
	          </TD>
	        </tr>
            <tr>
	        	<td  class= title>
           	 �ۼ��ش󼲲�����
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=InsuredSumHealthAmnt id="InsuredSumHealthAmnt" >
          	</TD>          
	         <td  class="title" nowrap>
            	�ۼ�ҽ���ձ���
           </TD>
         	 <td  class= input>
            	<Input class="readonly wid" readonly name=InsuredMedicalAmnt id="InsuredMedicalAmnt" >
           </TD>	
	      	<td  class= title>
	            �ۼ������ձ���
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredAccidentAmnt" id="InsuredAccidentAmnt" >
	          </TD>
	       </tr>
           <tr>
	        	<td  class= title>
           	 �ۼƷ��ձ���
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=InsuredSumAmnt id="InsuredSumAmnt" >
          	</TD>
	      	<td  class= title>
           	 �ۼƱ���
          	</TD>
          	<td  class= input>
            	<Input class="readonly wid" readonly name=InsuredSumPrem id="InsuredSumPrem" >
          	</TD>
	        </tr>
	      </table>
</Div>
<table>
  <tr>
    <td nowrap>
   	<INPUT VALUE="  �����˽�����֪��ѯ  " class=cssButton TYPE=button id="indButton1" name="indButton1" onclick="queryInsuredHealthImpart()">   
   	<INPUT VALUE="  �����˼���Ͷ�����ϲ�ѯ  " class=cssButton TYPE=button id="indButton2" name="indButton2" onclick="showApp(2);"> 
   	<INPUT VALUE="  ��ͯ��Լ¼��  " class=cssButton TYPE=button id="Button2" name="Button2" onclick="showSpec(1);"> 
   	<INPUT VALUE="  �б����۱��¼��  " class=cssButton TYPE=button id="Button3" name="Button3" onclick="NewChangeRiskPlan();"> 
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
  					<span id="spanPolRiskGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>
</Div>
