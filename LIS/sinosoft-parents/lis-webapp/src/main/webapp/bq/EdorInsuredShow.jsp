<DIV id=DivLCInsuredIndButton STYLE="display:''">
	<!--被保人信息部分 -->
	<table>
	<tr>
	<td class="common">
	<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLCInsured);">
	</td>
	<td class= titleImg>
		被保人信息明细
	</td>
	</tr>
	</table>
</DIV>
<DIV id=DivLCInsured class="maxbox1" STYLE="display:''">
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
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCode" id="InsuredOccupationCode" type="hidden">
	          <input CLASS="readonly wid" readonly name="InsuredOccupationCodeName" id="InsuredOccupationCodeName">
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
            	<Input class="multiCurrency" readonly name=Insuredincome id="Insuredincome" >
          	</TD>          	
	        	<td  class= title>
	            累计寿险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="multiCurrency" readonly name="InsuredSumLifeAmnt" id="InsuredSumLifeAmnt" >
	          </TD>
	          <td  class= title>
	           	 累计重大疾病保额
	          	</TD>
	          	<td  class= input>
	            	<Input class="multiCurrency wid" readonly name=InsuredSumHealthAmnt id="InsuredSumHealthAmnt" >
	          	</TD>
	        </tr>
            <tr>	        	          
	         <td  class="title" nowrap>
            	累计医疗险保额
           </TD>
         	 <td  class= input>
            	<Input class="multiCurrency wid" readonly name=InsuredMedicalAmnt id="InsuredMedicalAmnt" >
           </TD>	
	      	<td  class= title>
	            累计意外险保额
	          </TD>
	          <td  class= input>
	          <input CLASS="multiCurrency wid" readonly name="InsuredAccidentAmnt" id="InsuredAccidentAmnt" >
	          </TD>
	        	<td  class= title>
           	 累计风险保额
          	</TD>
          	<td  class= input>
            	<Input class="multiCurrency wid" readonly name=InsuredSumAmnt id="InsuredSumAmnt" >
          	</TD>
           </tr>
           <tr>
	      	<td  class= title>
           	 累计保费
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
   	<INPUT VALUE="  被保人健康告知查询  " class=cssButton TYPE=button name="indButton1" onclick="queryHealthImpartInd()">
   	<INPUT VALUE="  被保险人理赔信息查询  " class=cssButton TYPE=button name="indButton3" onclick="queryClaim()">  
   	<INPUT VALUE="  被保人既往投保资料查询  " class=cssButton TYPE=button name="indButton2" onclick="showApp(2);"> 
   	<INPUT VALUE="  儿童特约录入  " class=cssButton TYPE=hidden name="Button2" onclick="showSpec(1);"> 
   	<INPUT VALUE="  承保结论变更录入  " class=cssButton TYPE=button name="Button3" onclick="NewChangeRiskPlan();"> 
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
	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanRiskGrid">
  					</span>
  			  	</td>
  			</tr>
    	</table>
</Div>
