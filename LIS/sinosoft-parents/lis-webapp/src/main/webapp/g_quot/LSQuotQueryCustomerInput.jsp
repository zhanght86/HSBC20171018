<%
/***************************************************************
 * <p>ProName��LSQuotQueryCustomerInput.jsp</p>
 * <p>Title����ѯ׼�ͻ�����</p>
 * <p>Description����ѯ׼�ͻ�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-04-24
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getAttribute("GI");
    String tCurrentDate= PubFun.getCurrentDate();
    String tOperator = tGI.Operator;
    String tManageCom = tGI.ManageCom;
    String tMulLineNo = request.getParameter("SelNo");
    String tMark = request.getParameter("Mark");
    String tPreCustomerNo = request.getParameter("PreCustomerNo");
    String tPreCustomerName = null;
    try{
        tPreCustomerName = new String(request.getParameter("PreCustomerName").getBytes("ISO8859_1"),"GBK");        
    }catch(Exception e){}
    String tQuotNo = request.getParameter("QuotNo");
%>
<script>
    var tOperator = "<%=tOperator%>";//��ǰ��¼��
    var tManageCom = "<%=tManageCom%>";//��ǰ��¼����
    var tMulLineNo = "<%=tMulLineNo%>";//MulLine���
    var tMark = "<%=tMark%>";//���뷽ʽ���
    var tPreCustomerNo1 = "<%=tPreCustomerNo%>";//ѯ��һ����Ϣ¼����洫��ͻ���
    var tPreCustomerName = "<%=tPreCustomerName%>";
    var tQuotNo = "<%=tQuotNo%>";//ѯ�ۺ�
    
</script>
<html>
<head>
    <title>׼�ͻ����Ʋ�ѯ</title>
    <script src="../common/javascript/Common.js"></script>
    <script src="../common/cvar/CCodeOperate.js"></script>
    <script src="../common/javascript/MulLine.js"></script>
    <script src="../common/javascript/EasyQuery.js"></script>
    <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script src="../common/javascript/VerifyInput.js"></script>
    <script src="../common/laydate/laydate.js"></script>
    <link href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="./LSQuotQueryCustomerInput.js"></script>
    <%@include file="./LSQuotQueryCustomerInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<!-- ��ѯ���� -->
<form name=fm id=fm method=post action="" target=fraSubmit>
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
            </td>
            <td class=titleImg>׼�ͻ����Ʋ�ѯ����</td>
        </tr>
    </table>
    <div id="divInfo1" class=maxbox1 style="display: ''">
        <table class=common>
            <tr class=common>
                <td class=title>׼�ͻ�����</td>
                <td class=input><input class="wid common" name=PreCustomerNo id=PreCustomerNo></td>
                <td class=title>׼�ͻ�����</td>
                <td class=input><input class="wid common" name=PreCustomerName id=PreCustomerName></td>
                <td class=title></td>
                <td class=input></td>
            </tr>
        </table>
        <input class=cssButton type=button value="��  ѯ" onclick="queryCustomer();">
        <input class=cssButton type=button value="ѡ  ��" onclick="returnCustomer();">
        <input class=cssButton type=button value="��  ��" onclick="top.close();">
    </div>
        
    <table>
        <tr>
            <td class=common>
                <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divResult1);">
            </td>
            <td class=titleImg>׼�ͻ�������Ϣ�б�</td>
        </tr>
    </table>
    <div id="divResult1"  style="display: ''">
        <table class=common>
            <tr class=common>
                <td text-align: left colSpan=1>
                    <span id="spanRelaCustGrid"></span>
                </td>
            </tr>
        </table>
        <center>        
            <input class=cssButton90 type=button value="��  ҳ" onclick="turnPage1.firstPage();">
            <input class=cssButton91 type=button value="��һҳ" onclick="turnPage1.previousPage();">
            <input class=cssButton92 type=button value="��һҳ" onclick="turnPage1.nextPage();">
            <input class=cssButton93 type=button value="β  ҳ" onclick="turnPage1.lastPage();">
        </center>
    </div>
    <input type=hidden name=Operate id=Operate>
</form>
<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
    <div style="display: none">
        <input type=hidden name=Operate id=Operate>
    </div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
