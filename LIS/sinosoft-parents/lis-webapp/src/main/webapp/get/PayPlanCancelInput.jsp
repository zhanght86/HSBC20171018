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
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <!--<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>-->
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="PayPlanCancel.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PayPlanCancelInit.jsp"%>
  <title>�߸���ѯ </title>
</head>

<body  onload="initForm();">
  <form action="./PayPlanCancelSubmit.jsp" method=post name=fm id="fm" target="fraSubmit" >    
   
  <!-- ������Ϣ���� -->
  <table class= common border=0 width=100%>
    <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cuifu);"></td>
      <td class= titleImg align= center> ������߸���ѯ������</td>
    </tr>
  </table>   
  <Div  id= "cuifu" style= "display: ''" class="maxbox1" >
  <table  class= common align=center>
    <TR  class= common>
     <TD  class= title5>
        ���屣����
      </TD>
      <TD  class= input5>
        <Input class=wid name=GrpContNo id=GrpContNo >
      </TD>
      <TD  class= title5>
        ��ȡ����
      </TD>
      <TD  class= input5>
        <!--<Input class= "coolDatePicker" dateFormat="short" name=GetDate >-->
        <Input  class="coolDatePicker" class="laydate-icon" onClick="laydate({elem: '#GetDate'});" 	verify="��Ч��ʼ����|DATE" dateFormat="short" name=GetDate 	id="GetDate"><span class="icon"><a onClick="laydate({elem: 	'#GetDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
      </TD>

    </TR>
    
        
  </table>     
  </div>

<!--<INPUT class="cssButton" VALUE=" ��  ѯ " TYPE=button onclick="easyQueryClick();">-->
<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>

  
  <Div  id= "divLCGet" style= "display: none">
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
  
    <table  class= common>
      <tr  class= common>
        <td text-align: left colSpan=1>
          <span id="spanLJSGetGrid" > </span> 
        </td>
      </tr>	
    </table>
    <br>
  <Div id= "divLCGetButton" style= "display: ''" align="center">
    	
        <INPUT VALUE="��  ҳ" class="cssButton90" type="button" onclick="turnPage.firstPage();"> 
				<INPUT VALUE="��һҳ" class="cssButton91" type="button" onclick="turnPage.previousPage();"> 					
				<INPUT VALUE="��һҳ" class="cssButton92" type="button" onclick="turnPage.nextPage();"> 
				<INPUT VALUE="β  ҳ" class="cssButton93" type="button" onclick="turnPage.lastPage();">  			

  </div> 
</div>  
  <Div  id= "divCancelCommit" style= "display: none">
    <table>
    <tr>
      <td>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSpeccontent);">
      </td>
      <td class= titleImg>
          �����볷��ԭ��
      </td>
    </tr>
  </table>
      <TR  class= common>
        <TD  class= title>
          <textarea name="CancelReason" cols="120" rows="3" class="common" >
        </textarea></TD>
        <td  class= title>
          <INPUT class="cssButton"  VALUE="���볷��" TYPE=button onclick="CancelCommit();">     			
			</td>
      </TR>
    </table> 



  </div> 
                                                                                  
  <input type=hidden id="fmtransact" name="fmtransact">
  <input type=hidden id="OutGetNoticeNo" name="OutGetNoticeNo">

  </form>
                                                                                                                                                                    
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>    
</body>
</html>
