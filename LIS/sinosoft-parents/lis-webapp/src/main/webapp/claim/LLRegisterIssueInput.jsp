<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput(); 
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String tRgtNo = request.getParameter("claimNo");	//�ⰸ��
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLRegisterIssue.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLRegisterIssueInit.jsp"%>
    <title>�����Ǽ� </title>
</head>
<body  onload="initForm();" >
    <form  method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <!--<Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
           
        </TR>
    </Table> --> 
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>    
                <TD class= title5> ������ </TD>       
                <TD class= input5> <Input class="wid" class= common name=RgtNo id=RgtNo ></TD>
                <TD class= title5></TD>
                <TD class= input5></TD>
            </TR>

         
        </table> 
    </DIV>
    <INPUT style="display:none" class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();"> 
<a href="javascript:void(0);" class="button" onClick="queryGrid();">��    ѯ</a>
    <DIV id=DivLCContInfo STYLE="display:''"> 
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLIssueGrid);"></td>
                <td class= titleImg> �������Ϣ </td>
            </tr>      
        </table>
    </Div>
    <Div  id= "divLLIssueGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanLLIssueGrid" ></span></td>
            </tr>
        </table>
        <center>
        <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">                     
        <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">   </center>  
    </div>
    <br>
    <Div  id= "divSearch" style= "display: ''" class="maxbox">
        <table class= common>
            <TR class= common>    
                <TD class= title5> ������ </TD>       
                <TD class= input5> <Input class="wid" class= common name=RgtNo1 id=RgtNo1 ></TD>
                <TD class= title5> �ͻ��� </TD>       
                <TD class= input5> <Input class="wid" class= common name=CustomerNo id=CustomerNo ></TD>
            </TR>
            <TR class= common>    
                <TD class= title5> ������� </TD>       
                <TD class= input5> <Input class="wid" class= common name=IssueConclusion id=IssueConclusion ></TD>
                <TD class= title5> ������˻�ԭ�� </TD>       
                <TD class= input5> <Input class="wid" class= common name=IssueReason id=IssueReason></TD>
            </TR>
            <TR class= common>    
                <TD class= title5> ������˻����� </TD>       
                <TD class= input5> <Input class="wid" class= multiDatePicker name=IssueBackDate id=IssueBackDate ></TD>
                <TD class= title5> ������˻��� </TD>       
                <TD class= input5> <Input class="wid" class= common name=IssueBacker id=IssueBacker ></TD>
            </TR>
           <TR class= common>    
                <TD class= title5> ������������� </TD>       
                <TD class= input5> <Input class="wid" class= multiDatePicker name=IssueReplyDate id=IssueReplyDate ></TD>
                <TD class= title5> ����������� </TD>       
                <TD class= input5> <Input class="wid" class= common name=IssueReplyer id=IssueReplyer ></TD>
            </TR>
         
        </table> 
    </DIV>
    <!--������˻�ԭ��,������˻�ȷ������,������˻���,�������������,�������������,�����������-->
    <table style="display:none"  class= common >
        <tr  class=common>                
            <td><INPUT class=cssButton VALUE="��    ��" TYPE=button onclick="top.close();"></td> 
        </tr>
    </table> 
    <a href="javascript:void(0);" class="button" onClick="top.close();">��    ��</a>           
    <%
    //�������������ر���
    %>  
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
 	<Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
