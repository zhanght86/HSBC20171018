<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%

//�������ƣ�
//�����ܣ�
//�������ڣ�2003-03-26 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="./FinDayList.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="FinDayListInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./FinDayListSave.jsp">
    <table>
    	<tr>
    		<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);"></td>
    		 <td class= titleImg>�����嵥</td>
    	</tr>
    </table>
    <div class="maxbox1">
    	<Div  id= "divFCDay" style= "display: ''">
      	  <table  class= common>
        	<TR  class= common>
          		<TD  class= title5>��ʼʱ��</TD>
          		<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" 	verify="��ʼʱ��|NOTNULL" dateFormat="short" name=StartDate 	id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span></TD>
          		<TD  class= title5>����ʱ��</TD>
          		<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" 	verify="����ʱ��|NOTNULL" dateFormat="short" name=EndDate 	id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span></TD>
          	</TR>
          	<tr class=common>
          		<TD CLASS=title5>������� </TD>
          		<TD CLASS=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  NAME=ManageCom id="ManageCom" VALUE="" MAXLENGTH=10 CLASS=code  onMouseDown="return showCodeList('comcode',[this],null,null,null,null,1);" onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" verify="�������|code:comcode&notnull"  readonly></TD>
        	</tr>
          </table>
    	</Div>
    </div>
    <p>
    	<Div  id= "divOperator" style= "display: ''">
      		<table  class= common>
        		<TR class= common style="text-align:center;">
					<TD  class= input width="26%">
						<!-- <input class= cssButton type=Button name="TempPrint" id="TempPrint" value="  �����嵥  " onclick="PrintTemp()"> -->
						<a href="javascript:void(0)" name="TempPrint" id="TempPrint" class=button onClick="PrintTemp();">&nbsp;&nbsp;�����嵥&nbsp;&nbsp;</a>
					</TD>
					<TD  class= input width="26%">
					  <!-- <input class= cssButton type=Button name="AdvancePrint"  id="AdvancePrint" value="  Ԥ���嵥  " onclick="PrintAdvance()"> -->
					  <a href="javascript:void(0)" class=button name="AdvancePrint"  id="AdvancePrint" onClick="PrintAdvance();">&nbsp;&nbsp;Ԥ���嵥&nbsp;&nbsp;</a>
					</TD>
					<TD  class= input width="26%">
						<!-- <input class= cssButton type=Button name="PremPrint" id="PremPrint" value=" ���������嵥 " onclick="PrintPrem()"> -->
						<a href="javascript:void(0)" class=button name="PremPrint" id="PremPrint" onClick="PrintPrem();">&nbsp;���������嵥&nbsp;</a>
					</TD>
					
					<TD  class= input width="26%">
						<!-- <input class= cssButton type=Button name="ClaimPrint" value=" ���֧���嵥 " onclick="PrintClaim()"> -->
						<a href="javascript:void(0)" class=button  name="ClaimPrint" id="ClaimPrint" onClick="PrintClaim();">&nbsp;���֧���嵥&nbsp;</a>
					</TD>
				</TR>
				<tr><td style="height:5px;"></td></tr>
			  	<TR class= common style="text-align:center;">
			  		<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="EdorDuePrint"  id="EdorDuePrint" value="��ȫӦ���嵥" onclick="PrintEdorDue()"> -->
							<a href="javascript:void(0)" class=button name="EdorDuePrint"  id="EdorDuePrint" onClick="PrintEdorDue();">��ȫӦ���嵥</a>
						</TD>
					<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="LiveGetPrint" id="LiveGetPrint" value="��ȡ�����嵥" onclick="PrintLiveGet()" > -->
							<a href="javascript:void(0)" class=button name="LiveGetPrint" id="LiveGetPrint" onClick="PrintLiveGet();">��ȡ�����嵥</a>
						</TD>
							<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="OtherDuePrint" id="OtherDuePrint" value=" ����Ӧ���嵥 " onclick="PrintOtherDue()"> -->
							<a href="javascript:void(0)" class=button name="OtherDuePrint" id="OtherDuePrint" onClick="PrintOtherDue();">&nbsp;����Ӧ���嵥&nbsp;</a>
						</TD>
						<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="ActuPayPrint" id="ActuPayPrint" value=" ҵ��ʵ���嵥 " onclick="PrintActuPay()"> -->
							<a href="javascript:void(0)" class=button name="ActuPayPrint" id="ActuPayPrint" onClick="PrintActuPay();">&nbsp;ҵ��ʵ���嵥&nbsp;</a>
						</TD>
				</TR>
				<tr><td style="height:5px;"></td></tr>		
				<TR class= common style="text-align:center;">
						<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="DueYEPrint" id="DueYEPrint" value="Ӧ������嵥" onclick="PrintDueYE()" > -->
							<a href="javascript:void(0)" class=button name="DueYEPrint" id="DueYEPrint" onClick="PrintDueYE();">Ӧ������嵥</a>
						</TD>
					
						<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="AdvanceGetYEPrint" id="AdvanceGetYEPrint" value="Ԥ������嵥" onclick="PrintAdvanceGetYE()" > -->
							<a href="javascript:void(0)" class=button name="AdvanceGetYEPrint" id="AdvanceGetYEPrint" onClick="PrintAdvanceGetYE();">Ԥ������嵥</a>
						</TD>
							<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="AirPolPrint" id="AirPolPrint" value="������Ӧ���嵥" onclick="PrintAirPol()" > -->
							<a href="javascript:void(0)" class=button name="AirPolPrint" id="AirPolPrint" onClick="PrintAirPol();">������Ӧ���嵥</a>
						</TD>
							<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="GLFYPrint" id="GLFYPrint" value="����������嵥" onclick="PrintGLFY()" > -->
							<a href="javascript:void(0)" class=button name="GLFYPrint" id="GLFYPrint" onClick="PrintGLFY();">����������嵥</a>
						</TD>
				</TR>
			</table>
    	</Div>
    <br>		
       <Div  id= "divDownLoad" style= "display: ''">
         <table  class= common>
				<TR class= common>
					 <!-- <Input class=code name=ListType CodeData="0|^0|�����嵥^1|Ԥ���嵥^2|���������嵥^3|���֧���嵥^4|��ȫӦ���嵥^5|��ȡ�����嵥^6|����Ӧ���嵥^7|ҵ��ʵ���嵥^8|��ȫ�շ��嵥^9|Ӧ������嵥^10|Ԥ������嵥^11|�����������嵥" -->
                     <td class="title5 input5"><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class=code name=ListType id="ListType" CodeData="0|^0|�����嵥^1|Ԥ���嵥^2|���������嵥^3|���֧���嵥^4|��ȫӦ���嵥^5|��ȡ�����嵥^6|����Ӧ���嵥^7|ҵ��ʵ���嵥^8|Ӧ������嵥^9|Ԥ������嵥^10|�����������嵥^11|����������嵥" 
					 onMouseDown="showCodeListEx('ListType',[this],[0,1]);"
					  				ondblClick="showCodeListEx('ListType',[this],[0,1]);"onkeyup="showCodeListKeyEx('ListType',[this],[0,1]);"><INPUT class=cssButton ID="Down" VALUE=" ��    �� " TYPE=button onclick="fileDownload()"></td>
                          <td class="title5"></td>          
                         <td class="title5"></td>
                         <td class="input5"></td>
				</TR>
	    </table>
      </Div>
		<Div id="DivFileDownload" style="display:'none'">
				<A id="fileUrl" href=""></A>
		</Div>
		<input type=hidden id="fmtransact" name="fmtransact" id="fmtransact">
		<input type=hidden name="PrintType" id="PrintType">
		<input type=hidden name="Operator" id="Operator">
		<input type=hidden name="Url" id="Url">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
