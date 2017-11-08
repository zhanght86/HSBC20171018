<DIV id=DivLCInsuredIndButton STYLE="display:''">
	<!--被保人信息部分 -->
	<table>
	<tr>
	<td class="common">
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsuredInd);">
	</td>
	<td class= titleImg>
		被保人信息明细
	</td>
	</tr>
	</table>
	</DIV>
<DIV id=DivLCInsuredInd class="maxbox1" STYLE="display:none">
    <table  class= common>  
        <TR  class= common>  
	       <td CLASS="title">被保人号码
	    		</td>
				<td CLASS="input" COLSPAN="1">
				<input NAME="InsuredNo" id="InsuredNo" MAXLENGTH="10" CLASS="readonly wid" readonly>
	    		</td>
	        <TD  class= title>
                客户内部号码
          </TD>             
            <TD  class= input>
                <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
                <input class="codeno" name="SequenceNoCode" id="SequenceNoCode" style="background: url(../common/images/select--bg_03.png) no-repeat center; "  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" onclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" name="SequenceNoName" id="SequenceNoName" readonly="true">
            </TD>              
	        <TD  class= title>
                与第一被保险人关系</TD>             
            <TD  class= input>
                <Input class="codeno" name="RelationToMainInsured" id="RelationToMainInsured" style="background: url(../common/images/select--bg_03.png) no-repeat center; "  elementtype=nacessary verify="主被保险人关系|code:Relation" onclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" name="RelationToMainInsuredName" id="RelationToMainInsuredName" readonly="readonly"  >
            </TD>                  
        </TR> 
    </table> 
</DIV>  

<DIV id=DivLCInsured class="maxbox" STYLE="display:''">
    <table  class= common>
	        <tr  class= common>
	          <td  class= title>
	            姓名
	          </TD>
	          <td  class= input>
	            <Input CLASS="readonly wid" readonly name="InsuredName" id="InsuredName" value="">
	          </TD>
	          <td  class= title>
	            职业代码
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCode" id="InsuredOccupationCode" >
	          <input CLASS="readonly wid" readonly type="hidden" name="InsuredOccupationCodeName" id="InsuredOccupationCodeName">
	          </TD>
	          <td  class= title>
	            职业类别
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredOccupationType" id="InsuredOccupationType" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationTypeName" id="InsuredOccupationTypeName">
	          </TD>
	        </tr>
	        <tr>
	        	<td  class= title>
           	 身高
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredStature id="InsuredStature" >
          	</TD>
	        	<td  class= title>
           	 体重
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredWeight id="InsuredWeight" >
          	</TD>
          	<td  class= title>
           	 BMI值
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredBMI id="InsuredBMI" >
          	</TD>
	        </TR>
	        <tr>
	         	<td  class= title>
           	 年收入
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=Insuredincome id="Insuredincome" >
          	</TD>
          	<!--
          	<td  class="title" nowrap>
            	黑名单标记
          	</TD>
          	-->    	 
            	<Input CLASS="readonly wid" readonly name=InsuredBlacklistFlag type="hidden">
            	<Input CLASS="readonly wid" readonly name=InsuredBlacklistFlagName type="hidden">
	        	<td  class= title>
	            累计寿险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredSumLifeAmnt" id="InsuredSumLifeAmnt" >
	          </TD>
	          <td  class= title>
           	 累计重大疾病保额
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredSumHealthAmnt id="InsuredSumHealthAmnt" >
          	</TD> 
	        </tr>
           <tr>       	         
	         <td  class="title" nowrap>
            	累计医疗险保额
           </TD>
         	 <td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredMedicalAmnt id="InsuredMedicalAmnt" >
           </TD>	
	      	<td  class= title>
	            累计意外险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="readonly wid" readonly name="InsuredAccidentAmnt" id="InsuredAccidentAmnt" >
	          </TD>
	          <td  class= title>
           	 累计风险保额
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredSumAmnt id="InsuredSumAmnt" >
          	</TD>
	       </tr>
           <tr>
	      	<td  class= title>
           	 累计保费
          	</TD>
          	<td  class= input>
            	<Input CLASS="readonly wid" readonly name=InsuredSumPrem id="InsuredSumPrem" >
          	</TD>
	        </tr>
	      </table>
</Div>
<table>
  <tr>
    <td nowrap>
   	<INPUT VALUE="  被保人健康告知查询  " class=cssButton TYPE=button name="Button1" onclick="queryInsuredHealthImpart()">  
   	<INPUT VALUE="  被保险人理赔信息查询  " class=cssButton TYPE=button name="Button4" onclick="queryClaim()">
   	<INPUT VALUE="  被保人既往投保资料查询  " class=cssButton TYPE=button  onclick="showApp(2);"> 
   	<INPUT VALUE="  儿童特约录入  " class=cssButton TYPE=button name="Button2" onclick="showSpec(1);"> 
   	<INPUT VALUE="  客户承保结论变更录入  " class=cssButton TYPE=button name="Button3" onclick="NewChangeRiskPlan();"> 
    </td>
  </tr>
</table>
<DIV id=DivRiskButton STYLE="display:''">
	<!--被保人险种信息部分 -->
	<table>
	<tr>
	<td class="common">
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivRiskInd);">
	</td>
	<td class= titleImg>
		险种信息
	</td>
	</tr>
	</table>
</DIV>
<DIV id=DivRiskInd STYLE="display:''">
  	<table  class=common>
        <tr  class=common>
            <td>
            	<span id="spanPolRiskGrid"></span>
            </td>
        </tr>
   </table>
</Div>
