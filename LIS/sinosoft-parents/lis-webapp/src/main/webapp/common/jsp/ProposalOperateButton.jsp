<%
//*******************************************************
//* �������ƣ�inputButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="inputButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				&nbsp;<!--<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return resetForm();">-->
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return submitForm();">
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
    			 ����Ͷ�ʼƻ�
    		</td>
    	  </tr>
          </table>
          <div class="maxbox" id="maxbox">
          <table>
    	    <TR  class= common>
       	<td class="title5">�����ʻ���Ч������</td> 
       <td class="input5"><Input class=codeno name=UintLinkValiFlag id="UintLinkValiFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center; "  CodeData='0|^2|ǩ������Ч^4|����ԥ�ں���Ч' value=2 onclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" ondblclick="return showCodeListEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);" onkeyup="return showCodeListKeyEx('UintLinkValiFlag',[this,UintLinkValiFlagName],[0,1]);"><input class=codename name=UintLinkValiFlagName readonly=true elementtype=nacessary></font></td>
       <td class="title5"></td>
       <td class="input5"></td>
        </TR> 
        </table>
    		  <table  class= common>
    			<tr>
    				<INPUT class=cssButton VALUE="Ͷ�ʼƻ�Ӱ��λ" name='btnInvestPlan' TYPE=button onclick="gotoInvestPlan();">
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
			<INPUT VALUE="��  ��" class =cssButton name=submitFormButton  TYPE=button onclick="submitFormPlan();">
			<INPUT VALUE="ɾ  ��" class =cssButton name=deleteClickButton TYPE=button onclick="DeleteClickPlan();">
			<INPUT VALUE="��  ��" class =cssButton name=updateClickButton TYPE=button onclick="updateClickPlan();">
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
  <input type="button" class=cssButton name="Input" value="��һ��" onClick="returnparent()" class=cssButton>
  <div id="inputQuestIn" style="display: none">
	  <input type="button" class=cssButton name="Input" value="�����¼��" onClick="QuestInput()" class=cssButton>
  </div>
	<INPUT class=cssButton VALUE=�������ѯ TYPE=button onclick="QuestQuery();">
	<INPUT class=cssButton id="inputconfirm" VALUE=¼����� TYPE=button onclick="inputConfirm(1);">
	<!--input type="button" class=cssButton name="Input" value="ǿ�ƽ������" onClick="unLockTable();" class=cssButton-->
</div>


<div id="modifyButton" style="display:none">
	  	<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();" style="float: right">
			<!--INPUT class=cssButton VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();" style="float: right"-->
		<!--input type="button" class=cssButton name="Input" value="ǿ�ƽ������" onClick="unLockTable();" class=cssButton-->
</div>


<div  id= "divInputContButton" style = "display:none;float:left">
  <INPUT class=cssButton VALUE="������Ӱ��λ" TYPE=hidden onclick="gotoBnf();">
  <INPUT class=cssButton id="riskbutton0" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
  <INPUT class=cssButton id="riskbutton1" VALUE="��һ��" TYPE=button onclick="returnparent();">
  <INPUT class=cssButton id="riskbutton2" VALUE="¼�����"  TYPE=button onclick="inputConfirm(1);">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="ǿ�ƽ������" TYPE=button onclick="unLockTable();"-->
</div>
<!--add by pst 2009-02-11-��������������¼���������������Ҫ������ʾ -->
<div  id= "divInputContButtonBQ" style = "display:none;float:left">
  <INPUT class=cssButton id="riskbutton0" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="ǿ�ƽ������" TYPE=button onclick="unLockTable();"-->
</div>
<div  id= "divInputContButtonBQBack" style = "display:none;float:left">
  <INPUT class=cssButton id="riskbutton1" VALUE=" �� �� " TYPE=button onclick="returnparent();">
</div>
<!--end add by pst->
<!--add by yaory 2005-7-11-14:14 -->

<!--end add by yaory-->
<div  id= "divGrpContButton" style = "display:none;float:left">
  <INPUT class=cssButton id="GrpContButton2" VALUE="��һ��" TYPE=button onclick="returnparent();">
  <INPUT class=cssButton id="GrpContButton0" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
	<INPUT class=cssButton id="GrpContButton1" VALUE=�������ѯ TYPE=button onclick="QuestQuery();">
  <!--INPUT class=cssButton id="riskbutton3" VALUE="ǿ�ƽ������" TYPE=button onclick="unLockTable();"-->
</div>

<div  id= "divUWContButton" style = "display:none;float:left">
  <INPUT class=cssButton id="backbutton" VALUE="��һ��" TYPE=button onclick="returnparent();">
</div>
<div id = "divApproveContButton" style = "display:none;float:left">
      <INPUT class=cssButton id="riskbutton4" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton id="riskbutton5" VALUE="��һ��" TYPE=button onclick="returnparent();">
    	<INPUT class=cssButton id="riskbutton6" VALUE="�������" TYPE=button onclick="inputConfirm(2);">
    	<!--INPUT class=cssButton id="riskbutton7" VALUE="ǿ�ƽ������" TYPE=button onclick="unLockTable();"-->
</div>
<div id = "divApproveModifyContButton" style = "display:none;float:left">
	<INPUT class=cssButton id="riskbutton21" VALUE="��һ��" TYPE=button onclick="returnparent();">
	<!--
	<INPUT class=cssButton id="riskbutton22" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
	-->
	<!--INPUT class=cssButton id="riskbutton23" VALUE="�����޸����" TYPE=hidden onclick="inputConfirm(3);"-->
	<!--INPUT class=cssButton id="riskbutton7" VALUE="ǿ�ƽ������" TYPE=button onclick="unLockTable();"-->
</div>


<div  id= "divBqNSButton" style = "display:none;float:left">
  <!--<INPUT class=cssButton id="riskbutton0" VALUE="�����¼��" TYPE=button onClick="QuestInput();">-->
  <INPUT class=cssButton id="riskbutton1" VALUE="��һ��" TYPE=button onclick="returnparent();">
</div>

<%
//*******************************************************
//* �������ƣ�queryButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="queryButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* �������ƣ�updateButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="updateButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="��  ��"  TYPE=button onclick="return updateClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* �������ƣ�deleteButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="deleteButton" style="display: none">
	<table class="common" align=center>
		<tr>
			<td class=button >
				&nbsp;&nbsp;
			</td>
			<td class=button width="10%">
				<INPUT class=cssButton VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
			</td>
		</tr>
	</table>
</div>

<%
//*******************************************************
//* �������ƣ�autoMoveButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove(''+param+'');" class=cssButton>
	<input type="button" name="autoMoveInput" value="��һ��" onclick="returnparent();" class=cssButton>
        <input type=hidden  id=""  name="autoMoveFlag">
        <input type=hidden  id=""  name="autoMoveValue">
        <input type=hidden id="" name="pagename" value="">
</div>

<%
//*******************************************************
//* �������ƣ�autoMoveButton.jsp
//* �����ܣ�ҳ��ͨ�ð�ť
//* �������ڣ�2002-05-20
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//*             Minim   2002-05-20    �½�
//******************************************************
%>
<div id="inputQuestButton" style="display: none">
	<input type="button" name="Input" value="�����¼��" onClick="QuestInput()" class=cssButton>
</div>
</div>