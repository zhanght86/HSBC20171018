<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%

//程序名称：
//程序功能：
//创建日期：2003-03-26 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
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
    		 <td class= titleImg>生成清单</td>
    	</tr>
    </table>
    <div class="maxbox1">
    	<Div  id= "divFCDay" style= "display: ''">
      	  <table  class= common>
        	<TR  class= common>
          		<TD  class= title5>起始时间</TD>
          		<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" 	verify="起始时间|NOTNULL" dateFormat="short" name=StartDate 	id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span></TD>
          		<TD  class= title5>结束时间</TD>
          		<TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" 	verify="结束时间|NOTNULL" dateFormat="short" name=EndDate 	id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span></TD>
          	</TR>
          	<tr class=common>
          		<TD CLASS=title5>管理机构 </TD>
          		<TD CLASS=input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  NAME=ManageCom id="ManageCom" VALUE="" MAXLENGTH=10 CLASS=code  onMouseDown="return showCodeList('comcode',[this],null,null,null,null,1);" onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" verify="管理机构|code:comcode&notnull"  readonly></TD>
        	</tr>
          </table>
    	</Div>
    </div>
    <p>
    	<Div  id= "divOperator" style= "display: ''">
      		<table  class= common>
        		<TR class= common style="text-align:center;">
					<TD  class= input width="26%">
						<!-- <input class= cssButton type=Button name="TempPrint" id="TempPrint" value="  暂收清单  " onclick="PrintTemp()"> -->
						<a href="javascript:void(0)" name="TempPrint" id="TempPrint" class=button onClick="PrintTemp();">&nbsp;&nbsp;暂收清单&nbsp;&nbsp;</a>
					</TD>
					<TD  class= input width="26%">
					  <!-- <input class= cssButton type=Button name="AdvancePrint"  id="AdvancePrint" value="  预收清单  " onclick="PrintAdvance()"> -->
					  <a href="javascript:void(0)" class=button name="AdvancePrint"  id="AdvancePrint" onClick="PrintAdvance();">&nbsp;&nbsp;预收清单&nbsp;&nbsp;</a>
					</TD>
					<TD  class= input width="26%">
						<!-- <input class= cssButton type=Button name="PremPrint" id="PremPrint" value=" 保费收入清单 " onclick="PrintPrem()"> -->
						<a href="javascript:void(0)" class=button name="PremPrint" id="PremPrint" onClick="PrintPrem();">&nbsp;保费收入清单&nbsp;</a>
					</TD>
					
					<TD  class= input width="26%">
						<!-- <input class= cssButton type=Button name="ClaimPrint" value=" 赔款支出清单 " onclick="PrintClaim()"> -->
						<a href="javascript:void(0)" class=button  name="ClaimPrint" id="ClaimPrint" onClick="PrintClaim();">&nbsp;赔款支出清单&nbsp;</a>
					</TD>
				</TR>
				<tr><td style="height:5px;"></td></tr>
			  	<TR class= common style="text-align:center;">
			  		<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="EdorDuePrint"  id="EdorDuePrint" value="保全应付清单" onclick="PrintEdorDue()"> -->
							<a href="javascript:void(0)" class=button name="EdorDuePrint"  id="EdorDuePrint" onClick="PrintEdorDue();">保全应付清单</a>
						</TD>
					<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="LiveGetPrint" id="LiveGetPrint" value="领取给付清单" onclick="PrintLiveGet()" > -->
							<a href="javascript:void(0)" class=button name="LiveGetPrint" id="LiveGetPrint" onClick="PrintLiveGet();">领取给付清单</a>
						</TD>
							<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="OtherDuePrint" id="OtherDuePrint" value=" 其他应付清单 " onclick="PrintOtherDue()"> -->
							<a href="javascript:void(0)" class=button name="OtherDuePrint" id="OtherDuePrint" onClick="PrintOtherDue();">&nbsp;其他应付清单&nbsp;</a>
						</TD>
						<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="ActuPayPrint" id="ActuPayPrint" value=" 业务实付清单 " onclick="PrintActuPay()"> -->
							<a href="javascript:void(0)" class=button name="ActuPayPrint" id="ActuPayPrint" onClick="PrintActuPay();">&nbsp;业务实付清单&nbsp;</a>
						</TD>
				</TR>
				<tr><td style="height:5px;"></td></tr>		
				<TR class= common style="text-align:center;">
						<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="DueYEPrint" id="DueYEPrint" value="应付余额清单" onclick="PrintDueYE()" > -->
							<a href="javascript:void(0)" class=button name="DueYEPrint" id="DueYEPrint" onClick="PrintDueYE();">应付余额清单</a>
						</TD>
					
						<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="AdvanceGetYEPrint" id="AdvanceGetYEPrint" value="预收余额清单" onclick="PrintAdvanceGetYE()" > -->
							<a href="javascript:void(0)" class=button name="AdvanceGetYEPrint" id="AdvanceGetYEPrint" onClick="PrintAdvanceGetYE();">预收余额清单</a>
						</TD>
							<TD  class= input width="26%">
							<!-- <input class= cssButton type=Button name="AirPolPrint" id="AirPolPrint" value="航意险应收清单" onclick="PrintAirPol()" > -->
							<a href="javascript:void(0)" class=button name="AirPolPrint" id="AirPolPrint" onClick="PrintAirPol();">航意险应收清单</a>
						</TD>
							<TD class= input width = "26%">
							<!-- <input class= cssButton type=Button name="GLFYPrint" id="GLFYPrint" value="管理费收入清单" onclick="PrintGLFY()" > -->
							<a href="javascript:void(0)" class=button name="GLFYPrint" id="GLFYPrint" onClick="PrintGLFY();">管理费收入清单</a>
						</TD>
				</TR>
			</table>
    	</Div>
    <br>		
       <Div  id= "divDownLoad" style= "display: ''">
         <table  class= common>
				<TR class= common>
					 <!-- <Input class=code name=ListType CodeData="0|^0|暂收清单^1|预收清单^2|保费收入清单^3|赔款支出清单^4|保全应付清单^5|领取给付清单^6|其他应付清单^7|业务实付清单^8|保全收费清单^9|应付余额清单^10|预收余额清单^11|冲消航意险清单" -->
                     <td class="title5 input5"><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class=code name=ListType id="ListType" CodeData="0|^0|暂收清单^1|预收清单^2|保费收入清单^3|赔款支出清单^4|保全应付清单^5|领取给付清单^6|其他应付清单^7|业务实付清单^8|应付余额清单^9|预收余额清单^10|冲消航意险清单^11|管理费收入清单" 
					 onMouseDown="showCodeListEx('ListType',[this],[0,1]);"
					  				ondblClick="showCodeListEx('ListType',[this],[0,1]);"onkeyup="showCodeListKeyEx('ListType',[this],[0,1]);"><INPUT class=cssButton ID="Down" VALUE=" 下    载 " TYPE=button onclick="fileDownload()"></td>
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
