<%
//**************************************************************************************************
//Name��LLGrpReportMissInput.jsp
//Function�����屨������������Ϣ
//�������ڣ�2008-10-25 18:13
//������  ��zhangzheng
//Desc: 
//**************************************************************************************************
%>
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
    <SCRIPT src="LLGrpReportMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLGrpReportMissInit.jsp"%>
    <title>���ձ����Ǽ� </title>
</head>
<body  onload="initForm();" >
    <form action="" method=post name=fm id=fm target="fraSubmit">

    <DIV id=DivLCContInfo STYLE="display:''"> 
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
                <td class= titleImg> �������� </td>
            </tr>      
        </table>
    </Div>

     <Div  id= "peer" style= "display: ''" class="maxbox1">
       <table  class= common>
  <TR  class= common>
    <TD  class= title5>�ⰸ��</TD>
    <TD  class= input5><input class="wid" class= common name="CaseNo" id="CaseNo" ></TD>
    <TD  class= title5>����ͻ���</TD>
    <TD  class= input5><input class="wid" class= common name="CustomerNo" id="CustomerNo" ></TD></TR>
    <TR  class= common>
    <TD  class= title5>��λ����</TD>
    <TD  class= input5><input class="wid" class= common name="CustomerName" id="CustomerName" ></TD>
    <TD  class= title5>��������</TD>
    <TD  class= input5><!--<Input class="multiDatePicker"  dateFormat="short" name=RgtDateStart >-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDateStart'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RgtDateStart id="RgtDateStart"><span class="icon"><a onClick="laydate({elem: '#RgtDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD> 
  </TR>
  
  <TR  class= common>
                 
    <TD  class= title5>��������</TD>
    <TD  class= input5><!--<Input class="multiDatePicker"  dateFormat="short" name=RgtDateEnd >-->
     <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDateEnd'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RgtDateEnd id="RgtDateEnd"><span class="icon"><a onClick="laydate({elem: '#RgtDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>
    <TD class= title5> ��������</TD>
    <TD class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=rgtstate id=rgtstate ondblclick="return showCodeList('llreportstate',[this,rgtstateName],[0,1]);" onclick="return showCodeList('llreportstate',[this,rgtstateName],[0,1]);" onkeyup="return showCodeListKey('llreportstate',[this,rgtstateName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=rgtstateName id=rgtstateName readonly=true>
  
  </TR>
       </table>
     </DIV>
   
   <!--<input name="AskIn" style="display:''"  class=cssButton type=button value="��  ѯ" onclick="querySelfGrid()">-->
   <a href="javascript:void(0);" name="AskIn" class="button" onClick="querySelfGrid();">��    ѯ</a><br><br>
   <br>

    <Div  id= "divSelfLLReportGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLReportGrid" ></span></td>
            </tr>
        </table>
        
    </div>
    <!--<table  class= common >
        <tr  class=common>    
            <td><INPUT class=cssButton name='Report' VALUE="��������" TYPE=button onclick="newReport();"></td> 
        </tr>
    </table>  -->          
    <a href="javascript:void(0);" name='Report' class="button" onClick="newReport();">��������</a>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
