<html> 
<%
//程序名称：
//程序功能：
//创建日期：2009-10-13 15:15:43
//创建人  ：zhanpeng程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LDExRateInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="LDExRateInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./LDExRateSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    
    <table>
      <tr class=common>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOLDCode1);">
          </IMG>
        </td>
        <td class=titleImg>
        	即期外汇牌价维护
        </td>
    </tr>
    </table>
    <Div  id= "divOLDCode1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>    
        	<TD  class= title5>                                                                                                                                                                                                                                               
            外币币种
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=CurrCode id=CurrCode ondblclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onclick="return showCodeList('currcode',[this,CurrCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,CurrCodeName],[0,1]);"  verify="外币币种代码|notnull&currcode"><input name=CurrCodeName id=CurrCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            外币数额单位
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Per id=Per verify="外币数额单位|notnull&num&len<=10" elementtype=nacessary  >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            本币币种
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class='codeno' name=DestCode id=DestCode ondblclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
          onclick="return showCodeList('currcode',[this,DestCodeName],[0,1]);" 
            onkeyup="return showCodeListKey('currcode',[this,DestCodeName],[0,1]);"  verify="本币币种代码|notnull&currcode"><input name=DestCodeName id=DestCodeName  class=codename elementtype=nacessary  readonly=true>
          </TD>
          <TD  class= title5>
            现汇买入价
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ExchBid id=ExchBid verify="现汇买入价|num" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            现汇卖出价
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ExchOffer id=ExchOffer verify="现汇卖出价|num" >
          </TD>
          <TD  class= title5>
            现钞买入价
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CashBid id=CashBid verify="现钞买入价|num" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            现钞卖出价
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CashOffer id=CashOffer verify="现钞卖出价|num" >
          </TD>
          <TD  class= title5>
            中间价
          </TD>
          <TD  class= input5>
           <Input class="wid" class= common name=Middle id=Middle verify="中间价|num" >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            创建日期
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=MakeDate verify="创建日期|notnull&date" elementtype=nacessary>-->
             <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            创建时间
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=MakeTime id=MakeTime verify="创建时间|notnull&len=8" elementtype=nacessary>（例如：19:39:20 ）
          </TD>
        </TR>
      </table></Div>

						<!--<INPUT class=cssButton name="deletebutton" VALUE="转  存"  TYPE=button onclick="return deleteClick();">-->
                        <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">转    存</a><br><br>
					
            <div class="maxbox1">
			<table  class=common>
				<TR  class= common>
          <TD  class= title5>
            停用日期
          </TD>
          <TD  class= input5>
            <!--<Input class="coolDatePicker" dateFormat="short" name=EndDate verify="创建日期|date">-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            停用时间
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=EndTime id=EndTime verify="停用时间|len=8">（例如：19:39:20 ）
          </TD>
        </TR>
			</table></div>

						<!--<INPUT class=cssButton name="querybutton" VALUE="外汇历史查询"  TYPE=button onclick="return queryClick2();">-->
                        <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick2();">外汇历史查询</a><br><br>
				
    <input type=hidden name=hideOperate value=''>
    <hr class="line">
    <br>

				<!--<INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return addClick();">
		
				<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
		
				<INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">-->
                
                <a href="javascript:void(0);" name="addbutton" class="button" onClick="return addClick();">增    加</a>
                <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">修    改</a>
                <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">查    询</a>
		
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
