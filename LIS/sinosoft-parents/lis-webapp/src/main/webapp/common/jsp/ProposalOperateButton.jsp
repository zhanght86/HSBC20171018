<%
//*******************************************************
//* 程序名称：inputButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="inputButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				&nbsp;<!--<INPUT class=cssButton VALUE="重  置"  TYPE=button onclick="return resetForm();">-->
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="保  存"  TYPE=button onclick="return submitForm();">
			</td>
		</tr>
	</table>
</div>
<Div  id= "divInvestPlanRate" style= "display: none" >
      <table  class= common>
       <tr>
    	 	<td class=common>
    			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);">
            </td>
            <td class= titleImg>
    			 保单投资计划
    		</td>
    	  </tr>
          </table>
          <div class="maxbox" id="maxbox">
          <table>
    	    <TR  class= common>
       	<td class="title5">保单帐户生效日类型</td> 
       <td class="input5"><Input class=codeno name=UintLinkValiFlag id="UintLinkValiFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center; "  CodeData='0|^2|签单日生效^4|过犹豫期后生效' value=2 onclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" ondblclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);"><input class=codename name=UintLinkValiFlagName readonly=true elementtype=nacessary></font></td>
       <td class="title5"></td>
       <td class="input5"></td>
        </TR> 
        </table>
    		  <table  class= common>
    			<tr>
    				<INPUT class=cssButton VALUE="投资计划影像定位" name='btnInvestPlan' TYPE=button onclick="gotoInvestPlan();">
    			</tr>
        	 <tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					  <span id="spanInvestPlanRate" >
					  </span>
				    </td>
			     </tr>
			      <tr  class= common>
			      	<td style="text-align: left" colSpan=1>
					  <span id="spanQueryInvestPlanRateGrip" >
					  </span>
			      	</td>
			     </tr>
		    </table>
	   <Div  id= "divRiskPlanSave" style= "display: none" >
			<INPUT VALUE="添  加" class =cssButton name=submitFormButton  TYPE=button onclick="submitFormPlan();">
			<INPUT VALUE="删  除" class =cssButton name=deleteClickButton TYPE=button onclick="DeleteClickPlan();">
			<INPUT VALUE="修  改" class =cssButton name=updateClickButton TYPE=button onclick="updateClickPlan();">
	  <table  class= common>
	       <tr><td></td></tr>
			   <tr><td></td> </tr>
			    <tr><td></td></tr>
			   <tr><td></td> </tr>
	   </table>
	</Div>
</Div>
</div>  
<div id="inputQuest" style="display:none">
  <input type="button" class=cssButton name="Input" value="上一步" onClick="returnparent()" class=cssButton>
  <div id="inputQuestIn" style="display: none">
	  <input type="button" class=cssButton name="Input" value="问题件录入" onClick="QuestInput()" class=cssButton>
  </div>
	<INPUT class=cssButton VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
	<INPUT class=cssButton id="inputconfirm" VALUE=录入完毕 TYPE=button onclick="inputConfirm(1);">
	<!--input type="button" class=cssButton name="Input" value="强制解除锁定" onClick="unLockTable();" class=cssButton-->
</div>


<div id="modifyButton" style="display:none">
	  	<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();" style="float: right">
			<!--INPUT class=cssButton VALUE="删  除"  TYPE=button onclick="return deleteClick();" style="float: right"-->
		<!--input type="button" class=cssButton name="Input" value="强制解除锁定" onClick="unLockTable();" class=cssButton-->
</div>


<div  id= "divInputContButton" style = "display:none;float:left">
  <INPUT class=cssButton VALUE="受益人影像定位" TYPE=hidden onclick="gotoBnf();">
  <INPUT class=cssButton id="riskbutton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
  <INPUT class=cssButton id="riskbutton1" VALUE="上一步" TYPE=button onclick="returnparent();">
  <INPUT class=cssButton id="riskbutton2" VALUE="录入完毕"  TYPE=button onclick="inputConfirm(1);">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>
<!--add by pst 2009-02-11-新增附加险无需录入问题件，返回需要单独显示 -->
<div  id= "divInputContButtonBQ" style = "display:none;float:left">
  <INPUT class=cssButton id="riskbutton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>
<div  id= "divInputContButtonBQBack" style = "display:none;float:left">
  <INPUT class=cssButton id="riskbutton1" VALUE=" 返 回 " TYPE=button onclick="returnparent();">
</div>
<!--end add by pst->
<!--add by yaory 2005-7-11-14:14 -->

<!--end add by yaory-->
<div  id= "divGrpContButton" style = "display:none;float:left">
  <INPUT class=cssButton id="GrpContButton2" VALUE="上一步" TYPE=button onclick="returnparent();">
  <INPUT class=cssButton id="GrpContButton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
	<INPUT class=cssButton id="GrpContButton1" VALUE=问题件查询 TYPE=button onclick="QuestQuery();">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>

<div  id= "divUWContButton" style = "display:none;float:left">
  <INPUT class=cssButton id="backbutton" VALUE="上一步" TYPE=button onclick="returnparent();">
</div>
<div id = "divApproveContButton" style = "display:none;float:left">
      <INPUT class=cssButton id="riskbutton4" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton id="riskbutton5" VALUE="上一步" TYPE=button onclick="returnparent();">
    	<INPUT class=cssButton id="riskbutton6" VALUE="审批完毕" TYPE=button onclick="inputConfirm(2);">
    	<!--INPUT class=cssButton id="riskbutton7" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>
<div id = "divApproveModifyContButton" style = "display:none;float:left">
	<INPUT class=cssButton id="riskbutton21" VALUE="上一步" TYPE=button onclick="returnparent();">
	<!--
	<INPUT class=cssButton id="riskbutton22" VALUE="问题件录入" TYPE=button onClick="QuestInput();">
	-->
	<!--INPUT class=cssButton id="riskbutton23" VALUE="审批修改完毕" TYPE=hidden onclick="inputConfirm(3);"-->
	<!--INPUT class=cssButton id="riskbutton7" VALUE="强制解除锁定" TYPE=button onclick="unLockTable();"-->
</div>


<div  id= "divBqNSButton" style = "display:none;float:left">
  <!--<INPUT class=cssButton id="riskbutton0" VALUE="问题件录入" TYPE=button onClick="QuestInput();">-->
  <INPUT class=cssButton id="riskbutton1" VALUE="上一步" TYPE=button onclick="returnparent();">
</div>

<%
//*******************************************************
//* 程序名称：queryButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="queryButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="查  询"  TYPE=button onclick="return queryClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* 程序名称：updateButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="updateButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="修  改"  TYPE=button onclick="return updateClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* 程序名称：deleteButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="deleteButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="删  除"  TYPE=button onclick="return deleteClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* 程序名称：autoMoveButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" value="随动定制确定" onclick="submitAutoMove(''+param+'');" class=cssButton>
	<input type="button" name="autoMoveInput" value="上一步" onclick="returnparent();" class=cssButton>
        <input type=hidden  id=""  name="autoMoveFlag">
        <input type=hidden  id=""  name="autoMoveValue">
        <input type=hidden id="" name="pagename" value="">
</div>

<%
//*******************************************************
//* 程序名称：autoMoveButton.jsp
//* 程序功能：页面通用按钮
//* 创建日期：2002-05-20
//* 更新记录：  更新人    更新日期     更新原因/内容
//*             Minim   2002-05-20    新建
//******************************************************
%>
<div id="inputQuestButton" style="display: none">
	<input type="button" name="Input" value="问题件录入" onClick="QuestInput()" class=cssButton>
</div>
</div>