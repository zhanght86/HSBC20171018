<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-07-21 17:44:24
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ChooseDutyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ChooseDutyInit.jsp"%>
<script>
<!--
var travel=true
var hotkey=27   /* hotkey即为热键的键值,是ASII码,这里98代表b键 */
var destination="../js.htm" /* 在此定义热键对应的页面 */

if (document.layers)
document.captureEvents(Event.KEYPRESS)
function gogo(e)
{
 if (document.layers) { 
  if (e.which==hotkey&&travel) 
  top.close();
 }
 else if (document.all){ 
    if (event.keyCode==hotkey) top.close(); 
 }
}
document.onkeypress=gogo 

function returnParent()
{
  iMax=DutyGrid.mulLineCount;
  tNeedDuty=false;

  for (i=0;i<iMax;i++)
  {
      if(DutyGrid.getChkNo(i)) tNeedDuty=true;

  }

  if(tNeedDuty)
  {
    DutyGrid.delBlankLine();
    top.opener.spanDutyGrid.innerHTML=spanDutyGrid.innerHTML;
    top.opener.document.all("inpNeedDutyGrid").value="1";
  }
  else
  {
    top.opener.spanDutyGrid.innerHTML="";
    top.opener.document.all("inpNeedDutyGrid").value="0";
  }
  top.close()
}

-->
</script>
</head>
<body  onload="initForm();" >
  <form action="./ChooseDutysave.jsp" method=post name=fm id=fm target="fraSubmit">

    <!-- 险种信息部分 -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCKind1);">
    		</td>
    		<td class= titleImg>
    			 基本保险信息
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCKind1" style= "display:  ">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            险种编码
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode id=RiskCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('RiskCode',[this]);">
          </TD>
          <TD  class= title>
            险种版本
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion id=RiskVersion style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('RiskVersion',[this]);">
          </TD>
        </TR>
      </table>
        <!-- 责任信息部分 -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid);">
    		</td>
    		<td class= titleImg>
    			 可以选择的责任
    		</td>
    	</tr>
    </table>
	<Div  id= "divDutyGrid" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>

        <!-- 保费信息部分 -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPremGrid);">
    		</td>
    		<td class= titleImg>
    			 保费信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divPremGrid" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanPremGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>

        <!-- 给付信息部分 -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGetGrid);">
    		</td>
    		<td class= titleImg>
    			 给付信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divGetGrid" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
				<td style="text-align: left" colSpan=1>
					<span id="spanGetGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>

         <INPUT VALUE="返回" TYPE=button class=cssButton onclick="returnParent();"> 					
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
