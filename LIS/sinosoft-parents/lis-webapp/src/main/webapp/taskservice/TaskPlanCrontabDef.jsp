<table class=common>
    		<TR class=common >
					<TD class=title>����ģʽѡ��</TD>
							<td class=input>
									<input type = "radio" value = "0"  name = "PlanModeFlag" checked="checked" onClick="showPlanMode('0');"/>����ģʽ
									<input type = "radio" value = "1"  name = "PlanModeFlag" onClick="showPlanMode('1');"/>ר��ģʽ
							</td>
					</TD>
					<TD class=title>ִ�мƻ�����</TD>
							<td class=input>
									 	<input type='button' class=cssButton value='��  ��' onclick="testPlan();">
							</td>
					</TD>
</table>	
<div id='divProMode'  style= "display: none" >
      <!-- ר��ģʽ -->
    	<table  class= common>
      	<TR>
    	  		<TD text-align: left colSpan=1>
							<span id="spanCrontabGrid" > </span> 
		  		</TD>
       	</TR>
			</table>
		 </div>
		
		 <div id='divSimpleMode'  style= "display: ''" >
      <!-- ����ģʽ -->
    	<table  class= common style= "display: none">
    		<TR  class= common>
      		<TD  class= title  style='width: 50px'>���е�λ</TD>
        	<TD  class= common><Input class= code id=RecycleType  name=RecycleType CodeData = "0|^11|����^21|Сʱ^31|��^41|��^51|��" ondblclick="return showCodeListEx('RecycleType',[this, ''],[0,1],null,null,null,1,150);" onkeyup="return showCodeListKeyEx('RecycleType',[this, ''],[0,1],null,null,null,1,150);"></TD>
        	<TD  class= title>���м��</TD>
        	<TD  class= input><Input class=common  name=Interval ></TD>
        	<TD  class= title>Crontab</TD>
        	<TD  class= input><Input class=common  id=CrontabStr ></TD>
        </TR>
			</table>
			<table  class= muline border=1 CELLSPACING=0 CELLPADDING=0  style='font-size:9pt;'>
			<TR>   
    		<TD  class= mulinetitle ><input class='mulinetitle'  readonly  style='width: 20px'></TD>
      		<TD  class= mulinetitle ><input class='mulinetitle'  readonly  style='width: 60px' value='���е�λ' ></TD>
        	<TD  class= mulinetitle ><input class='mulinetitle'  readonly  style='width: 150px'  value='���м��' ></TD>
      		<TD  class= mulinetitle ><input class='mulinetitle'  style='width: 180px' readonly  value='����' ></TD>
        	<TD  class= mulinetitle ><input class='mulinetitle' style='width: 180px' readonly  value='ֹ��' ></TD>
			</TR>
        <TR>
        	<TD   class= muline><input class='muline'  type = "radio" checked="checked"   name = "SimpleModeCho" value='11'></TD>
          <TD  class= muline >��</TD>
          <TD  class= muline style='text-align:left'><NOBR>ÿ<Input class="easyui-numberspinner" min="1" max="60" maxlength="2"  style='cursor:e-resize;width:50;border:no;' id=intervalMin >��(1-60)</NOBR></TD>
           <TD  class= muline ></TD>
          <TD  class= muline ></TD>
        </TR>
        <TR >
        	<TD   class= muline><input class='muline'  type = "radio" name = "SimpleModeCho" value='21'></TD>
          <TD  class= muline >Сʱ</TD>
          <TD  class= muline style='text-align:left'><NOBR>ÿ<Input  class="easyui-numberspinner" min="1" max="24" maxlength="2"  style='cursor:e-resize;width:50;border:1;' id=intervalHour >Сʱ(1-24)</NOBR></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left;  padding-left:2px;' id=hourStart  ondblclick="showCodeList('hour',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('hour',[this,''],[0,1],null,null,null);">(ʱ)</NOBR></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left; padding-left:2px;' id=hourEnd  ondblclick="showCodeList('hour',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('hour',[this,''],[0,1],null,null,null);">(ʱ)</NOBR></TD>
        </TR>
        
        <TR style='font-size:9pt'>
        	<TD   class= muline><input class='muline'  type = "radio" name = "SimpleModeCho" value='31'></TD>
          <TD  class= muline >��</TD>
          <TD  class= muline style='text-align:left'><NOBR>ÿ<Input class="easyui-numberspinner" min="1" max="31" maxlength="2"  style='cursor:e-resize;width:50;border:1;' id=intervalDay >��(1-31)</NOBR></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left; padding-left:2px;' id=dayStart  ondblclick="showCodeList('day',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('day',[this,''],[0,1],null,null,null);">(��)</NOBR></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left; padding-left:2px;' id=dayEnd  ondblclick="showCodeList('day',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('day',[this,''],[0,1],null,null,null);">(��)</NOBR></TD>
        </TR>
        
        <TR>
        	<TD   class= muline><input class='muline'  type = "radio" name = "SimpleModeCho" value='41'></TD>
          <TD  class= muline>��</TD>
          <TD  class= muline ></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left; padding-left:2px;width:140px' id=weekStart  ondblclick="showCodeList('week',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('week',[this,''],[0,1],null,null,null);">(����)</NOBR></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left; padding-left:2px;width:140px' id=weekEnd  ondblclick="showCodeList('week',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('week',[this,''],[0,1],null,null,null);">(����)</NOBR></TD>
        </TR>
        
        <TR >
        	<TD   class= muline><input class='muline'  type = "radio" name = "SimpleModeCho" value='51'></TD>
          <TD   class= muline >��</TD>
          <TD  class= muline style='text-align:left'><NOBR>ÿ<Input  class="easyui-numberspinner" min="1" max="12" maxlength="2"  style='cursor:e-resize;width:50;border:1;' id=intervalMonth >��(1-12)</NOBR></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left; padding-left:2px;' id=monthStart  ondblclick="showCodeList('taskmonth',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('taskmonth',[this,''],[0,1],null,null,null);">(��)</NOBR></TD>
          <TD  class= muline ><NOBR><Input readonly style='text-align:left; padding-left:2px;' id=monthEnd  ondblclick="showCodeList('taskmonth',[this, ''],[0,1],null,null,null);" onkeyup="showCodeListKey('taskmonth',[this,''],[0,1],null,null,null);">(��)</NOBR></TD>
        </TR>
			</table>
			
		 </div>
