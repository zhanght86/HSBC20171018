<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
 GlobalInput tGI1 = new GlobalInput();
 tGI1=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
 String ManageCom = tGI1.ManageCom;
%>
<script>
  var manageCom = "<%=tGI1.ManageCom%>"; //��¼�������
  var comcode = "<%=tGI1.ComCode%>"; //��¼��½����
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<!--<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>-->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="PersonPayPlanCancel.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="PersonPayPlanCancelInit.jsp"%>
  <title>�߸���ѯ</title>
</head>

<body  onload="initForm();">
  <form action="./PersonPayPlanCancelSubmit.jsp" method=post name=fm id=fm target="fraSubmit" >    
   
  <!-- ������Ϣ���� -->
  <table class= common border=0 width=100%>
    <tr>
    <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,queryy);">
            </td>
      <td class= titleImg align= center> ������߸���ѯ������</td>
    </tr>
  </table>   
  <Div  id= "queryy" style= "display: ''" class="maxbox1">
  <table  class= common align=center>
    <TR  class= common>
      <TD  class= title5>
        ֪ͨ�����
      </TD>
      <TD  class= input5>
        <Input class= wid name=GetNoticeNo id=GetNoticeNo >
      </TD>
      <TD  class= title5>
        ���˱�������
      </TD>
      <TD  class= input5>
        <Input class= wid name=ContNo id=ContNo >
      </TD>
    </TR>
    <TR  class= common>
      <TD  class= title5>
        ����������
      </TD>
      <TD  class= input5>
        <Input class=wid name=InsuredName id=InsuredName >
      </TD>
      <TD  class= title5>
        ��ȡ����
      </TD>
      <TD  class= input5>
        <Input  class="coolDatePicker" class="laydate-icon" onClick="laydate({elem: '#GetDate'});" 	verify="��Ч��ʼ����|DATE" dateFormat="short" name=GetDate 	id="GetDate"><span class="icon"><a onClick="laydate({elem: 	'#GetDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
      </TD>
    </TR>
  </table>      
  </div>
  <!--<INPUT class =cssButton VALUE="��   ѯ" type=Button onclick="easyQueryClick();">-->
  <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
 
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGet);">
        </td>
        <td class= titleImg>
          �߸���Ϣ
        </td>
      </tr>
    </table>
   <Div  id= "divLCGet" style= "display: none">
    <table  class= common>
      <tr  class= common>
        <td text-align: left colSpan=1>
          <span id="spanLJSGetGrid" > </span> 
        </td>
      </tr>	
    </table>
    
   <div align="center" style="display:''">
		<INPUT VALUE="��  ҳ" TYPE=button class="cssButton90" onclick="turnPage.firstPage();"> 
		<INPUT VALUE="��һҳ" TYPE=button class="cssButton91" onclick="turnPage.previousPage();"> 
		<INPUT VALUE="��һҳ" TYPE=button class="cssButton92" onclick="turnPage.nextPage();"> 
		<INPUT VALUE="β  ҳ" TYPE=button class="cssButton93" onclick="turnPage.lastPage();">
	</div> 

  </div> 
	<!--<INPUT class= cssButton VALUE="���볷��" TYPE=button onclick="CancelCommit();"> -->  
    <a href="javascript:void(0);" class="button" onClick="CancelCommit();">���볷��</a>  			
  <input type=hidden id="fmtransact" name="fmtransact" >
  <input type=hidden id="OutGetNoticeNo" name="OutGetNoticeNo">
  <input type=hidden id="manageCom" name="manageCom" value=<%=tGI1.ManageCom%>>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
  <br><br><br><br>   
</body>
</html>
