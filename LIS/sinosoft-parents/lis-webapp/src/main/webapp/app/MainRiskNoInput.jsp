<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<script>
var isOk=false;
function unloadMe()
{

  if(!isOk)
  {returnValue="";}
}
</script>

<script>
<!--
var conEscKey=27   /* conEscKey��Ϊ�ȼ��ļ�ֵ,��ASII��,����98����b��,27��ʾEsc */
var conEnterKey=13   /* Enter */

if (document.layers)
document.captureEvents(Event.KEYPRESS)

function onKeyPressEx(e)
{
 if (document.layers) { 
  if (e.which==conEscKey) 
  top.close();
 }
 else if (document.all){
    if (event.keyCode==conEscKey)  { Cancel_Click(); }
    if (event.keyCode==conEnterKey){ Ok_Click(); }
 }
}
document.onkeypress=onKeyPressEx ;
window.onunload=unloadMe;

function Ok_Click()
{
  isOk=true;
  returnValue=PolNo.value;
  window.close();
}
function Cancel_Click()
{
  if (confirm("��ȷʵ���˳����ձ�����¼����?"))
  { }
  else
  {
    return false;
  }
  returnValue='';
  window.close();
}
-->
</script>
</head>
<BODY style="background-color:lightblue;margin:10;"   onload="PolNo.value=dialogArguments;" >
<DIV id=d1></DIV>
    <table class=common>
      <tr>
          <TD  class= title>
            ���ձ�������:
          </TD>
          <TD  class= input>
            <Input class="common wid" name=PolNo id=PolNo maxlength=20>
          </TD>
      </tr>
      <tr align="right">
          <TD  class= title>
            &nbsp;
          </TD>
          <TD  class= input>
            <input type='button' onclick="Ok_Click();" value="ȷ��"> &nbsp
            <input type='button' onclick="Cancel_Click();" value="ȡ��">
          </TD>
      </tr>
    </table>
</BODY>
</HTML>