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
var conEscKey=27   /* conEscKey即为热键的键值,是ASII码,这里98代表b键,27表示Esc */
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
  if (confirm("您确实想退出主险保单号录入吗?"))
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
            主险保单号码:
          </TD>
          <TD  class= input>
            <Input class= common name=PolNo maxlength=20>
          </TD>
      </tr>
      <tr align="right">
          <TD  class= title>
            &nbsp;
          </TD>
          <TD  class= input>
            <input type='button' onclick="Ok_Click();" value="确定"> &nbsp
            <input type='button' onclick="Cancel_Click();" value="取消">
          </TD>
      </tr>
    </table>
</BODY>
</HTML>
