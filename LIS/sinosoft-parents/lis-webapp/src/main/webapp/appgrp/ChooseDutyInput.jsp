<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-07-21 17:44:24
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ChooseDutyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./ChooseDutyInit.jsp"%>
<script>
<!--
var travel=true
var hotkey=27   /* hotkey��Ϊ�ȼ��ļ�ֵ,��ASII��,����98����b�� */
var destination="../js.htm" /* �ڴ˶����ȼ���Ӧ��ҳ�� */

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
    top.opener.fm.all("inpNeedDutyGrid").value="1";
  }
  else
  {
    top.opener.spanDutyGrid.innerHTML="";
    top.opener.fm.all("inpNeedDutyGrid").value="0";
  }
  top.close()
}

-->
</script>
</head>
<body  onload="initForm();" >
  <form action="./ChooseDutysave.jsp" method=post name=fm target="fraSubmit">

    <!-- ������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCKind1);">
    		</td>
    		<td class= titleImg>
    			 ����������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLCKind1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);">
          </TD>
          <TD  class= title>
            ���ְ汾
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskVersion ondblclick="return showCodeList('RiskVersion',[this]);">
          </TD>
        </TR>
      </table>
        <!-- ������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDutyGrid);">
    		</td>
    		<td class= titleImg>
    			 ����ѡ�������
    		</td>
    	</tr>
    </table>
	<Div  id= "divDutyGrid" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>

        <!-- ������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPremGrid);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divPremGrid" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanPremGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>

        <!-- ������Ϣ���� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGetGrid);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divGetGrid" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanGetGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>

         <INPUT VALUE="����" TYPE=button onclick="returnParent();"> 					
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
