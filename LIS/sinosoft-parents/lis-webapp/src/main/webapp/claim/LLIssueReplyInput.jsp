<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput(); 
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLIssueReply.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLIssueReplyInit.jsp"%>
    <title>������ϻ��� </title>
</head>
<body  onload="initForm();" >
    <form  method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
 	<Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> �������ѯ������ </TD>
        </TR>
    </Table>
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>    
                <TD class= title5> ������ </TD>       
                <TD class= input5> <Input class="wid" class= common name="RgtNo" id="RgtNo"></TD>
               <TD class= title5> �������</TD>       
                <TD class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="IssueBackCom" id="IssueBackCom" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name="ManageComName" id="ManageComName" readonly=true></TD> 	
            </TR>
            <TR class= common>    
                <TD class= title5> ��ʼ���� </TD>       
                <TD class= input5> <!--<input class="multiDatePicker" dateFormat="short" name="IssueBackStartDate">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#IssueBackStartDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=IssueBackStartDate id="IssueBackStartDate"><span class="icon"><a onClick="laydate({elem: '#IssueBackStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
               <TD class= title5> ��������</TD>       
                <TD class= input5> <!--<input class="multiDatePicker" dateFormat="short" name="IssueBackEndDate" >-->
                 <Input class="coolDatePicker" onClick="laydate({elem: '#IssueBackEndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=IssueBackEndDate id="IssueBackEndDate"><span class="icon"><a onClick="laydate({elem: '#IssueBackEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>

         
        </table> 
    </DIV>
    <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">--> <a href="javascript:void(0);" class="button" onClick="queryGrid();">��    ѯ</a>

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
        <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">     </center>
    </div>


        <table class= common>
            <TR class= common>    
                <TD class= title5> ������˻�ԭ�� </TD>   
                </TR>    
                 <TR class= common>
                 <TD style="padding-left:16px" colspan="4"> <textarea name="IssueReason" cols="166" rows="4" witdh=25% class="common"  readonly></textarea></TD>

            </TR>
         <TR class= common>    
                <TD class= title5> ������ظ����� </TD>
                </TR>  
                 <TR class= common>     
                 <TD style="padding-left:16px" colspan="4"> <textarea name="IssueReplyConclusion" cols="166" rows="4" witdh=25% class="common"></textarea><font size=1 color='#ff0000'><b>*</b></font></TD>

            </TR>

         
        </table> 
    <!--������˻�ԭ��,������˻�ȷ������,������˻���,�������������,�������������,�����������-->

   <!-- <table  class= common >
        <tr  class=common>                
            <td><input class=cssButton type=button id="save" name="save" value="��   �� " onclick="ReplySave()"></td> 
        </tr>
    </table> --><br>
    <a href="javascript:void(0);" id="save" name="save" class="button" onClick="ReplySave();">��    ��</a>           
    <%
    //�������������ر���
    %>  
    
    <Input type=hidden name="Operator" >
    <Input type=hidden name="Operate" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <input type=hidden id="ClmNo" name="ClmNo">
	<input type=hidden id="Autditno" name="Autditno">
    <Input type=hidden name=IssueBackDate  id="IssueBackDate">
 	<Input type=hidden name=IssueBacker  id="IssueBacker">
 	<Input type=hidden name=IssueStage  id="IssueStage">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
