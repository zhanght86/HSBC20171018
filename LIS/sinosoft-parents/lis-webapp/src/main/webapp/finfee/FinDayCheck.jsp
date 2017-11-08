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
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="FinDayCheck.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="FinDayCheckInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form  method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr>
    		<td class=common>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
    		</td>
    		 <td class= titleImg>
        		操作员日结
       		 </td>
    	</tr>
    </table>
    <div class="maxbox1" >
     <Div  id= "divFCDay" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            起始时间
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="起始时间|NOTNULL" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            结束时间
          </TD>
          <TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="结束时间|NOTNULL" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR>
        <tr class=common>
          <TD CLASS=title5>
            管理机构 
          </TD>
          <TD CLASS=input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" NAME=ManageCom id="ManageCom" VALUE="" MAXLENGTH=10 CLASS=code onMouseDown="return showCodeList('comcode',[this],null,null,null,null,1);" onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);" onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" verify="管理机构|code:comcode&notnull"  readonly>
          
          </tr>
        </table>
     </Div>
    </div>
      <Div  id= "divOperator" style= "display: ''">
        <table  class= common>
          <TR class= common style="text-align:center;">
		        <TD  class= input>
			         <!-- <input class= cssButton type=Button name="ORiskPrint" id="ORiskPrint" value="    暂收日结打印    " onclick="ORPrint()"> -->
               <a href="javascript:void(0)" class=button name="ORiskPrint" id="ORiskPrint" onClick="ORPrint();">&nbsp;&nbsp;&nbsp;暂收日结打印&nbsp;&nbsp;&nbsp;</a>
		        </TD>
            <TD  class= input >
                <!-- <input class= cssButton type=Button name="YuShouPrint" id="YuShouPrint" value="     预收日结打印     " onclick="YSPrint()"> -->
                <a href="javascript:void(0)" class=button name="YuShouPrint" id="YuShouPrint" onClick="YSPrint();">&nbsp;&nbsp;&nbsp;预收日结打印&nbsp;&nbsp;&nbsp;</a>
            </TD>
            <TD  class= input >
			           <!-- <input class= cssButton type=Button name="OModePrint" id="OModePrint" value="   保费收入日结打印  " onclick="HeBao_Print()"> -->
                 <a href="javascript:void(0)" class=button name="OModePrint" id="OModePrint" onClick="HeBao_Print();">&nbsp;&nbsp;保费收入日结打印&nbsp;&nbsp;</a>
		        </TD>
            <TD  class= input >
               <!-- <input class= cssButton type=Button name="OModePrint" id="OModePrint" value="    赔款支出日结打印  " onclick="PrintPKZC()"> -->
               <a href="javascript:void(0)" class=button name="OModePrint" id="OModePrint" onClick="PrintPKZC();">&nbsp;赔款支出日结打印&nbsp;</a>
            </TD>
	        </TR>
	       <tr><td style="height:5px;"></td></tr>
	        <TR class= common style="text-align:center;">
		        <TD  class= input >
			         <!-- <input class= cssButton type=Button name="ORiskPrint" id="ORiskPrint" value="  保全应付日结打印  " onclick="PrintBQYF()"> -->
               <a href="javascript:void(0)" class=button name="ORiskPrint" id="ORiskPrint" onClick="PrintBQYF();">&nbsp;保全应付日结打印&nbsp;</a>
		        </TD>
            <TD  class= input >
                <!-- <input class= cssButton type=Button name="YuShouPrint" id="YuShouPrint" value="   领取给付日结打印   " onclick="PrintLQJF()" > -->
                <a href="javascript:void(0)" class=button name="YuShouPrint" id="YuShouPrint" onClick="PrintLQJF();">&nbsp;领取给付日结打印&nbsp;</a>
            </TD>
            <TD  class= input >
               <!-- <input class= cssButton type=Button name="OModePrint" id="OModePrint" value="   其它应付日结打印  " onclick="PrintQTYF()"> -->
               <a href="javascript:void(0)" class=button name="OModePrint" id="OModePrint" onClick="PrintQTYF();">&nbsp;&nbsp;其它应付日结打印&nbsp;&nbsp;</a>
            </TD>
  
            <TD class= input >
                <!-- <input class= cssButton type=Button name="HeBaoPrint" id="HeBaoPrint" value="   业务实付日结打印   " onclick="PrintYWSF()"> -->
                <a href="javascript:void(0)" class=button name="HeBaoPrint" id="HeBaoPrint" onClick="PrintYWSF();">&nbsp;业务实付日结打印&nbsp;</a>
            </TD>
          </TR>
          <tr><td style="height:5px;"></td></tr>
          <TR class= common style="text-align:center;">
            <TD  class= input >
      	       <!-- <input class= cssButton type=Button name="ORiskPrint" id="ORiskPrint" value=" 航意险应收日结打印 " onclick="AirPrint()"> -->
               <a href="javascript:void(0)" class=button name="ORiskPrint" id="ORiskPrint" onClick="AirPrint();">航意险应收日结打印</a>
            </TD>
            <TD class= input >
              <!-- <input class= cssButton type=Button name="BtnPayModePrint" id="BtnPayModePrint" value="  暂收费方式日结打印  " onclick="PayModePrint()"> -->
              <a href="javascript:void(0)" class=button name="BtnPayModePrint" id="BtnPayModePrint" onClick="PayModePrint();">暂收费方式日结打印</a>
            </TD>
            <TD class= input >
              <!-- <input class= cssButton type=Button name="BtnGetModePrint" id="BtnGetModePrint" value=" 业务支出方式日结打印" onclick="GetModePrint()"> -->
              <a href="javascript:void(0)" class=button name="BtnGetModePrint" id="BtnGetModePrint" onClick="GetModePrint();">业务支出方式日结打印</a>
            </TD>
            <TD class= input >
              <!-- <input class= cssButton type=Button name="BtnGetModePrint" id="BtnGetModePrint" value="  管理费收入日结打印  " onclick="PrintGLFY()"> -->
              <a href="javascript:void(0)" class=button name="BtnGetModePrint" id="BtnGetModePrint" onClick="PrintGLFY();">管理费收入日结打印</a>
            </TD>
          </TR> 
        </table>
      </Div>
       <input type=hidden id="fmtransact" name="fmtransact">
       <input type=hidden name="Opt" id="Opt">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
