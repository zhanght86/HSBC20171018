<%
//**************************************************************************************************
//Name��LPStateQueryInput.jsp
//Function���ⰸ״̬��ѯҳ��
//Author�� wangjm
//Date: 2006-4-7
//Desc: 
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
  GlobalInput tGI = new GlobalInput();
  tGI = (GlobalInput)session.getValue("GI");
%>
<head>
<script>
    //���˵��Ĳ�ѯ����
var operator = "<%=tGI.Operator%>";   //��¼����Ա
var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>
<title>����״̬��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
   <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="LLStateQuery.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLStateQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<!--��¼������-->
 <form  name=fm id=fm target=fraSubmit method=post action="LPStateQueryInput.jsp" >

    <table class= common border=0 width=100%>
    <tr>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
    <td class= titleImg>�������ѯ������</td>
    </tr>
    </table>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    <table  class= common>
       <TR  class= common>
       <TD  class= title5>  ������ </TD>
       <TD  class= input5>
       <Input class="common wid"  name=ClmNo  id=ClmNo ></font>
       </TD>    
       <TD  class= title5>  ���屣���� </TD>
       <TD  class= input5>  <Input class="wid" class= common name=GrpContNo id=GrpContNo > </TD></Tr>
       <TR  class= common>
       <TD  class= title5>  �ͻ��� </TD>
       <TD  class= input5>  <Input class="wid" class= common name=CustomerNo id=CustomerNo > </TD>
       <TD  class= title5>  �ͻ����� </TD>
       <TD  class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName >  </TD>
       </TR>           
       <TR  class= common>
       
      <TD class=title5>�������</TD>
      <TD  class= input5 ><Input class="wid" class="code" name=ManageCom id=ManageCom  readonly >
  
 <!--      <TD class=title>�������</TD>
      <TD  class= input ><Input class="code" name=ManageCom ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
  -->   
      <TD  class= title5>�������� </TD>
      <TD  class= input5><!--<Input class="coolDatePicker"  dateFormat="short" name=RiskDate >-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#RiskDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RiskDate id="RiskDate"><span class="icon"><a onClick="laydate({elem: '#RiskDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
     </TR> 
     <TR  class= common>
      <TD  class= title5>�ⰸ״̬ </TD>
      <TD  class= input5>
      	<select name="clmstate">
			      <OPTION SELECTED  value="0">==��ѡ����==</OPTION>	      
			      <OPTION  value="10">����</OPTION>
			      <OPTION  value="20">����</OPTION>
			      <OPTION  value="30">���</OPTION>
			      <OPTION  value="40">����</OPTION>
			      <OPTION  value="11">�᰸</OPTION>
			    </select></TD>
     </TR>      

   </Table> </div>
   <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="easyQueryClick();">--> 
   <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>
    <Table>
    <TR>
    <TD class=common>
     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
     </TD>
      <TD class= titleImg>
        ������Ϣ
      </TD>
     </TR>
    </Table>    
 <Div  id= "divLDPerson1" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanClaimGrid" ></span> 
  </TD>
      </TR>
    </Table>
<center>    
   <table> 
    <tr>
    <td>
      <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="getFirstPage();"> 
    </td>
    <td>
      <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="getPreviousPage();"> 
    </td>
    <td>  
      <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="getNextPage();"> 
    </td>
    <td>  
      <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="getLastPage();"> 
    </td>
    </tr> 
    
   </table> 
</center>     

 </Div>
  
  <table>
      <tr>
       <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson2);">
        </td>
        <td class= titleImg >
        ����״̬��Ϣ
    </td>
    </tr>
    </table>

 <Div  id= "divLDPerson2" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanClaimStateGrid" ></span> 
     </TD>
      </TR>
  </Table>
 
 </Div>	
 <br>
<!--<INPUT class=cssButton VALUE="������Ϣ��ѯ" TYPE=button onclick="ReportQueryClick();">
<INPUT class=cssButton VALUE="������Ϣ��ѯ" TYPE=button onclick="RegisterQueryClick();">-->
<a href="javascript:void(0);" class="button" onClick="ReportQueryClick();">������Ϣ��ѯ</a>
<a href="javascript:void(0);" class="button" onClick="RegisterQueryClick();">������Ϣ��ѯ</a>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</Form>
</body>
</html>
