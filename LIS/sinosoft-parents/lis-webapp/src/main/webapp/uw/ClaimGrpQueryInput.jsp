<%
//**************************************************************************************************
//�ļ����ƣ�ClaimGrpQueryInput.jsp
//�����ܣ��б�����-���屣��-�˹��˱�-���������ѯ��Ӧ���棬������Ϣ����ʾ��������˽��浥ѡ��ť
//          ʱ�����ӵ���ui/uw/ClaimQueryMain.jsp�����ղ�����ⰸ��ѯ��
//�������ڣ�2006-11-08
//������  ��zhaorx
//���¼�¼��
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
<%
//============================================================BGN�����ղ���
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tGrpAppntNo = request.getParameter("CustomerNo");	//����Ͷ���˿ͻ���
//============================================================END
%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="ClaimGrpQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="ClaimGrpQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLGrpQueryGrid);"></TD>
            <TD class= titleImg> Ͷ���˱����漰���ⰸ��Ϣ</TD>
        </TR>
    </Table>
    <Div  id= "divLLGrpQueryGrid" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLClaimGrpQueryGrid" ></span> </TD>
             </TR>
         </Table>
         <center>
           <table>
             <tr>
                 <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton93 VALUE=" βҳ " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr>
          </table>
        </center>
    </Div>     	                 
    
    <!--������,������Ϣ��-->
    <Input type=hidden id="GrpAppntNo" name="GrpAppntNo"><!--Ͷ���˿ͻ�����,��ǰһҳ�洫��-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--�ж��Ƿ������¼�-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--����-->
    <Input type=hidden id="State" name="State"><!--ѡ���״̬-->
    <Input type=hidden id="ContNo" name="ContNo"><!--Ͷ���˺�ͬ����-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
