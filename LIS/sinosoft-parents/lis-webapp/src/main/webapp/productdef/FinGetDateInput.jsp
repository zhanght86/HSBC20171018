<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<%
//程序名称：
//程序功能：
//创建日期：2007-11-15 15:39:06
//创建人  ：sunyu程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="FinGetDay.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <%@include file="FinGetDayInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();" >    
  <form  method=post name=fm target="fraSubmit">
    <table>
    	<tr> 
    		<td>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
    		</td>
    		 <td class= titleImg>
输入查询的时间范围
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divFCDay" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
起始时间
          </TD>
          <TD  class= input>
            <Input class= "multiDatePicker" dateFormat="short" name=StartDay verify="起始时间|NOTNULL">
          </TD>
          <TD  class= title>
结束时间
          </TD>
          <TD  class= input>
            <Input class= "multiDatePicker" dateFormat="short" name=EndDay  verify="结束时间|NOTNULL">
          </TD>  
        </TR>
        </table>
    </Div>
    <table>
    	<tr> 
    		<td>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOperator);">
    		</td>
    		 <td class= titleImg>
保单明细
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divOperator" style= "display: ''">    
    <table align=right>
        <TR > 
		<TD >
			<input class= cssButton type=Button name=button1 value="渠道业务保费打印" onclick="printPay('finget')">
			<input class= cssButton type=Button name=button2 value="犹豫期退费打印" onclick="printPay('finpay')">
		</TD>
		<TD>
		</TD>
		</TR>    	 
      </table>
      </Div>
    
       <input type=hidden id="fmtransact" name="fmtransact">
    <Div id=DivFileDownload style="display:none;">
      <A id=fileUrl href=""></A>
    </Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>