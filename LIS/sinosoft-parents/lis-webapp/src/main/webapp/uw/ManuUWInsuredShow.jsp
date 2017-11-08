<link href="../common/css/Project.css" rel="stylesheet" type="text/css">
<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<DIV id="DivLCInsuredIndButton" STYLE="display:''">
	<!--被保人信息部分 -->
	<table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);" />
			</td>
			<td class= titleImg>被保人信息明细</td>
		</tr>
	</table>
</DIV>
<div id="DivLCInsured" name="DivLCInsured" class="maxbox">
<DIV id="DivLCInsuredInd" STYLE="display:">
    <table  class= common>  
        <TR  class= common>  
	       	<td CLASS="title">被保人号码</td>
			<td CLASS="input" COLSPAN="1">
				<input id="InsuredNo" NAME="InsuredNo" MAXLENGTH="10" CLASS="readonly wid" readonly>
	    	</td>
	        <TD  class= title>客户内部号码</TD>             
            <TD  class= input>
                <!--Input clss="codeno" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"-->
                &nbsp;&nbsp;<input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" id="SequenceNoCode" name="SequenceNoCode"  elementtype=nacessary  CodeData="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人" onMouseDown="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" ondblclick="return showCodeListEx('SequenceNo', [this,SequenceNoName],[0,1]);" onkeyup="return showCodeListKeyEx('SequenceNo', [this,SequenceNoName],[0,1]);"><Input class="codename" id="SequenceNoName" name="SequenceNoName" readonly="true">
            </TD> 
	        <TD  class= title>与第一被保险人关系</TD>             
            <TD  class= input>
                &nbsp;<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class="codeno" id="RelationToMainInsured" name="RelationToMainInsured"  elementtype=nacessary verify="主被保险人关系|code:Relation" onMouseDown="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" ondblclick="return showCodeList('Relation', [this,RelationToMainInsuredName], [0,1]);" onkeyup="return showCodeListKey('Relation', [this,RelationToMainInsuredName], [0,1]);"><Input class="codename" id="RelationToMainInsuredName" name="RelationToMainInsuredName" readonly="readonly"  >
            </TD>                  
        </TR> 
    </table> 
</DIV>  

<DIV id="DivLCInsured" STYLE="display:''">
    <table  class= common>
	    <tr  class= common>
	        <td  class= title>姓名</TD>
	        <td  class= input><Input CLASS="readonly wid" readonly id="InsuredName" name="InsuredName" value=""></TD>
	        <td  class= title>职业代码</TD>
	        <td  class= input><input CLASS="readonly wid" readonly id="InsuredOccupationCode" name="InsuredOccupationCode"><input CLASS="readonly" readonly id="InsuredOccupationCodeName" name="InsuredOccupationCodeName"  type="hidden"></TD>
	        <td  class= title>职业类别</TD>
	        <td  class= input><input CLASS="readonly wid" readonly id="InsuredOccupationType" name="InsuredOccupationType" type="hidden"><input CLASS="readonly wid" readonly id="InsuredOccupationTypeName" name="InsuredOccupationTypeName"></TD>
        </tr>
	    <tr class= common>
            <td  class= title>身高</TD>
          	<td  class= input><Input class="readonly wid" readonly id="InsuredStature" name=InsuredStature ></TD>
	        <td  class= title>体重</TD>
          	<td  class= input><Input class="readonly wid" readonly id="InsuredWeight" name=InsuredWeight ></TD>
          	<td  class= title>BMI值</TD>
          	<td  class= input><Input class="readonly wid" readonly id="InsuredBMI" name=InsuredBMI ></TD>
	    </tr>
	    <tr class= common>
	        <td  class= title>年收入</TD>
          	<td  class= input><Input class="readonly wid" readonly id="Insuredincome" name=Insuredincome ></TD>
          	<td  class="title" nowrap>黑名单标记</TD>
         	<td  class= input><Input class="readonly wid" readonly id="InsuredBlacklistFlag" name=InsuredBlacklistFlag type="hidden"><Input class="readonly wid" readonly id="InsuredBlacklistFlagName" name=InsuredBlacklistFlagName ></TD>
	        <td  class= title>累计寿险保额</TD>
	        <td  class= input><input CLASS="readonly wid" readonly id="InsuredSumLifeAmnt" name="InsuredSumLifeAmnt" ></TD>
        </tr>
	    <tr class= common>
            <td  class= title>累计重大疾病保额</TD>
          	<td  class= input><Input class="readonly wid" readonly id="InsuredSumHealthAmnt" name=InsuredSumHealthAmnt ></TD>
          	<td  class="title" nowrap>累计医疗险保额</TD>
         	<td  class= input><Input class="readonly wid" readonly id="InsuredMedicalAmnt" name=InsuredMedicalAmnt ></TD>	
	      	<td  class= title>累计意外险保额</TD>
	        <td  class= input><input CLASS="readonly wid" readonly id="InsuredAccidentAmnt" name="InsuredAccidentAmnt" ></TD>
	    </tr>
	    <tr class= common>
	        <td  class= title>累计风险保额</TD>
          	<td  class= input><Input class="readonly wid" readonly id="InsuredSumAmnt" name=InsuredSumAmnt ></TD>
	      	<td  class= title>累计保费</TD>
          	<td  class= input><Input class="readonly wid" readonly id="InsuredSumPrem" name=InsuredSumPrem ></TD>
            <td  class= title></TD>
          	<td  class= input></td>
	    </tr>
	</table>
</DIV>
</div>
<br>
<INPUT VALUE="  被保人健康告知查询  " class=cssButton TYPE=button name="indButton1" onclick="queryInsuredHealthImpart()">  
<INPUT VALUE="  被保人既往投保资料查询  " class=cssButton TYPE=button name="indButton2" onclick="showApp(2);"> 
<INPUT VALUE="  儿童特约录入  " class=cssButton TYPE=hidden name="Button2" onclick="showSpec(1);"> 
<INPUT VALUE="  客户承保结论变更录入  " class=cssButton TYPE=button name="Button3" onclick="NewChangeRiskPlan();"> 
<input class="CssButton" name="button16" value="  被保人既往理赔资料查询  " type="button" onclick="queryClaimCusIns();"> 
<input class="CssButton" name="button17" value="  被保人既往保全资料查询  " type="button" onclick="queryEdorCusIns();"> 
<DIV id="DivRiskButton" STYLE="display:''">
	<!--被保人险种信息部分 -->
	<table>
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivRiskInd);">
			</td>
			<td class= titleImg>险种信息</td>
		</tr>
	</table>
</DIV>
<DIV id="DivRiskInd" STYLE="display:''">
	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolRiskGrid">
  					</span>
  			  	</td>
  			</tr>
    </table>
</Div>