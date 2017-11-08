<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ImpartToICD.jsp
//程序功能：告知整理录入
//创建日期：2002-06-19 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./ImpartToICD.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ImpartToICDInit.jsp"%>
  <title> 新契约体检资料录入 </title>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./ImpartToICDSave.jsp">
    <!-- 非列表 -->
    <div class="maxbox1">
    <table class="common">
    	<TR  class= common>
          <TD  class= title5>  合同号码  </TD>
          <TD  class= input5> <Input class="readonly wid" id="ContNo" name=ContNo > </TD>
           <INPUT  type= "hidden" class= Common id="PrtNo" name= PrtNo value= "">
          <TD  class= title5>  被保人  </TD>
          <TD  class= input5> <Input class="code wid" id="InsureNo" name=InsureNo style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeListEx('InsureNo',[this,''],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('InsureNo',[this,''],[0,1],null,null,null,1);" onFocus= "easyQueryClickSingle();"> <!-- onFocus= "easyQueryClickSingle();easyQueryClick();"--> </TD>
        </TR>
    </table>
    </div>
<DIV id=DivLCImpart STYLE="display:''">
<!-- 告知信息部分（列表） -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart2);">
</td>
<td class= titleImg>
被保险人告知明细信息
</td>
</tr>
</table>

<div  id= "divLCImpart2" style= "display: ''">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanImpartDetailGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>   疾病结果</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWDis" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanDisDesbGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
      </div>
      <INPUT type= "button" id="sure" name= "sure" value="保  存" class=cssButton onclick="submitForm()">			
		
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
