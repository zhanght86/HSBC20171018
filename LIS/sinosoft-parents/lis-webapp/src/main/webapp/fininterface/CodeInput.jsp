<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-08-16 17:44:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./CodeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="CodeInput.js"></SCRIPT>
  <%@include file="CodeInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./CodeSave.jsp" method=post name=fm id=fm target="fraSubmit">
   
    <%@include file="../common/jsp/InputButton.jsp"%>
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
    		</td>
    		 <td class= titleImg>
        		 代码基本信息
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divCode1" style= "display: ''" class="maxbox1">
    
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            编码类型
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CodeType id=CodeType >
          </TD>
          <TD  class= title5>
            编码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=Code id=Code >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            编码名称
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CodeName id=CodeName >
          </TD>
          <TD  class= title5>
            编码别名
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=CodeAlias id=CodeAlias >
          </TD> 
        </TR>
        <TR  class= common>
          <TD  class= title5>
            其它标志
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=OtherSign id=OtherSign >
          </TD>
        </TR>
      </table>
      </div>
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);">
    		</td>
    		 <td class= titleImg>
        		 代码基本信息
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "peer" style= "display: ''">
      <table  class= common>
	   	<TR>
	   	</TR>
	   	<TR>
    	  <TD text-align: left colSpan=1>
			<span id="spanCodeGrid" > </span> 
		  </TD>
        </TR>
       </table>
       
    </Div>
    <input type=hidden id="fmtransact" name="fmtransact" id="fmtransact"><br>
     <%@include file="../common/jsp/OperateButton.jsp"%>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
