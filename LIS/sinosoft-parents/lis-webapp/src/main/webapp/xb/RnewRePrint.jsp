<html> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="RnewRePrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="RnewRePrintInit.jsp"%>
  <title>���� </title>
</head>
<body  onload="initForm();" >
  <form action="./RnewRePrintSave1.jsp" method=post name=fm  id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
		<td class= titleImg align= center>�����뱣����ѯ������</td>
	</tr>
    </table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
    <tr  class= common>
      	  <TD  class= title5> ֪ͨ������ </TD>
      	  <TD  class= input5><input class= codeno name=CodeS 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeList('rernuwnotice',[this, CodeName], [0, 1]);"
          onDblClick="showCodeList('rernuwnotice',[this, CodeName], [0, 1]);"
          onkeyup="showCodeListKey('rernuwnotice', [this, CodeName], [0, 1]);"><input class=codename name=CodeName readonly=true></TD>
           </tr>
           <tr  class= common>
          <TD  class= title5> ������   </TD>
          <TD  class= input5><Input class="common wid" name=ContNoS ></TD>  
          <TD  class= title5> ֪ͨ��� </TD>
          <TD  class= input5><Input class="common wid" name=PrtSeqS ></TD>
          
    </tr>
    <tr  class= common>
         <TD  class= title5>   ��ʼ����   </TD>
          <TD  class= input5>  <input class="coolDatePicker" dateFormat="short" id="StartDay"  name="StartDay" onClick="laydate({elem:'#StartDay'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span> </TD>
          <TD  class= title5>  �������� </TD>
          <TD  class= input5> <input class="coolDatePicker" dateFormat="short" id="EndDay"  name="EndDay" onClick="laydate({elem:'#EndDay'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> </TD>        	  
     </tr>
    </table>
    </div></div>
          <!--<INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="easyQueryClick();"> -->
          <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
    <table>
    	<tr>
          <td class=common>
	    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	  </td>
    	  <td class= titleImg>
    		����֪ͨ����Ϣ
    	  </td>
    	</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="getLastPage();"> 	
  	</div>
  <!--	<p>
      <INPUT VALUE="�ύ����֪ͨ������" class= cssButton TYPE=button onClick="printPol();">
  	</p>-->
    <a href="javascript:void(0);" class="button"onClick="printPol();">�ύ����֪ͨ������</a>
    <br><br><br><br>
  	 
      <input type=hidden id="fmtransact" name="fmtransact">
      <input type=hidden id="Code" name="Code">
      <input type=hidden id="ContNo" name="ContNo">
      <input type=hidden id="EdorNo" name="EdorNo">
      <input type=hidden id="EdorType" name="EdorType">
      <input type=hidden id="PrtSeq" name="PrtSeq">
      <input type=hidden id="MissionID" name="MissionID">
      <input type=hidden id="SubMissionID" name="SubMissionID">
      <input type=hidden id="ActivityID" name="ActivityID">

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
