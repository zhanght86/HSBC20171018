<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
//�������ƣ�ForceUWReason.jsp
//�����ܣ�ǿ���˹��˱�ԭ���ѯ
//�������ڣ�2005-10-11 11:10:36
//������  ��guanwei
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tReason = "";
	try
	{
	  tReason = request.getParameter("Reason");
	}
	catch( Exception e )
	{
		tReason = "";
	}

//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
// 1 -- ����Ͷ����ֱ��¼��
// 2 -- �����¸���Ͷ����¼��
// 3 -- ����Ͷ������ϸ��ѯ
// 4 -- �����¸���Ͷ������ϸ��ѯ
// 5 -- ����
// 6 -- ��ѯ
// 7 -- ��ȫ�±�����
// 8 -- ��ȫ����������
// 9 -- ������������
// 10-- ��������
// 99-- �涯����
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
  loggerDebug("ForceUWReason","############tGI.ManageCom===="+tGI.ManageCom);
%>
<head>
<script language="javascript" type="">
	var ContNo = "<%=request.getParameter("ContNo")%>"
	var ManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var ContType = "<%=request.getParameter("ContType")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	if (ContType == "null")
	  ContType=1;
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/Common.js" type=""></script>
  <script src="../common/cvar/CCodeOperate.js" type=""></script>
 <script src="../common/javascript/EasyQuery.js" type=""></script>
  <script src="../common/javascript/MulLine.js" type=""></script>
  <script src="../common/laydate/laydate.js" type=""></script>
  <script src="../common/javascript/EasyQuery.js" type=""></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js" type=""></script>
  <script src="../common/javascript/VerifyInput.js" type=""></script>
<!--
	#########################################################################
	                   ���뱾ҳForceUWReason.js�ļ�
	#########################################################################
-->


  <%@include file = "ForceUWReasonInit.jsp"%>
  <script src="ForceUWReason.js" type=""></script>


<!--
	#########################################################################
	#########################################################################
	#########################################################################
-->

<!--
	#########################################################################
	                   ���뱾ҳForceUWInit.js�ļ�
	#########################################################################
-->

<!--
	#########################################################################
	#########################################################################
	#########################################################################
-->
</head>
<body onLoad="initForm();">
  <form method=post name=fm id="fm" target="fraSubmit" action="">
    <table>
      <tr>
        <td>
          <table class="common">
            <tr class="common">
              <td class="titleImg">
		<IMG  src="../common/images/butExpand.gif" style= "cursor:hand;" alt="">
 		 ǿ�ƽ����˹��˱�ԭ��
	      </td>
            </tr>
	  </table>
	  <div class="maxbox1">
          <table class="common">
            <tr class="common">
              <td class="input" colspan='4'>
                <textarea  cols="75" rows="6" class="common" name="ForceUWReason" id="ForceUWReason"  readonly = <%=tReason%> readonly>
                </textarea>
              </td>
            </tr>
          </table>
          </div>
          <table>
            <tr>
              <td>
                <input type="button" class="cssButton" id="quitButton" name="quitButton" value="��  ��" onClick="returnParent();">
              </td>
            </tr>
          </table>

      </tr>
    </table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>





