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
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="PayPlanCancelQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PayPlanCancelQueryInit.jsp"%>
  <title>�߸�������־��ѯ </title>
</head>

<body  onload="initForm();">
  <form method=post name=fm id="fm" target="fraSubmit" >    
   
  <!-- ������Ϣ���� -->
  <table class= common border=0 width=100%>
    <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLC);"></td>
      <td class= titleImg align= center> ������߸�������־��ѯ������</td>
    </tr>
  </table>
  <Div  id= "divLC" style= "display: ''" class="maxbox1" >
  <table  class= common>
    <TR  class= common>
      <TD  class= title5>
        �ŵ�����
      </TD>
      <TD  class= input5>
        <Input class= wid name=GrpContNo id=GrpContNo >
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
        ��ȡ����
      </TD>
      <TD  class= input5>
        <!--<Input class= "coolDatePicker" dateFormat="short" name=GetDate >-->
        <Input  class= "coolDatePicker" onClick="laydate({elem: '#GetDate'});" 	verify="��Ч��ʼ����|DATE" dateFormat="short" name=GetDate 	id="GetDate"><span class="icon"><a onClick="laydate({elem: 	'#GetDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
      </TD>
       <TD  class= title5></TD>
       <TD  class= input5></TD>  
          
      
    </TR>
  </table>     
  </div> 
  <!--<INPUT class="cssButton" VALUE=" ��  ѯ " TYPE=button onclick="easyQueryClick();">-->
  <!--<a?href="javascript:void(0);"?class="button" onclick="easyQueryClick();">��   ѯ</a>-->
  <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
  
  
  <Div  id= "divCancelLog" style= "display: none">
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCancelLog);">
        </td>
        <td class= titleImg>
          �߸���Ϣ
        </td>
      </tr>
    </table>
  
    <table  class= common>
      <tr  class= common>
        <td text-align: left colSpan=1>
          <span id="spanLFGetCancelLogGrid" > </span>
        </td>
      </tr>	
    </table>
   
	<div id="divLFCanButton" align="center">
        <INPUT class = cssButton90 VALUE=" �� ҳ " TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT class = cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
        <INPUT class = cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT class = cssButton93 VALUE=" β ҳ " TYPE=button onclick="turnPage.lastPage();">	  			
</div>
  </div>
  
  <Div  id= "divCancelContent" style= "display: none">
    <table>
      <TR  class= common>
        <td>
          �����߸���¼ԭ��
        </td>
      </TR>
      <TR  class= common>
        <TD  class= title>
          <textarea name="CancelReason" cols="120" rows="3" class="common" >
        </textarea></TD>
      </TR>
    </table> 
  </div> 
  
  </form>
                                                                                                                                                                    
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>    
</body>
</html>
