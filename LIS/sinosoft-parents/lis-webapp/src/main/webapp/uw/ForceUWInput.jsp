<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>   
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2005-05-19 11:10:36
//������  ��HL
//���¼�¼��  ������    ��������     ����ԭ��/����
%>     
<%  
	String tContNo = "";
	try
	{
		tContNo = request.getParameter("ContNo");
	}
	catch( Exception e )
	{ 
		tContNo = "";
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
  loggerDebug("ForceUWInput","############tGI.ManageCom===="+tGI.ManageCom);
%>
<head>
<script language="javascript">
	var ContNo = "<%=request.getParameter("ContNo")%>"
	var ManageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var ContType = "<%=request.getParameter("ContType")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	if (ContType == "null")
	  ContType=1;
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script>
  <script src="../common/javascript/MulLine.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="ProposalAutoMove.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
<!--	
	#########################################################################
	                   ���뱾ҳForceUW.js�ļ�
	#########################################################################
-->
  <script src="ForceUW.js"></script>	
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
	<%@include file="ForceUWInit.jsp"%>
<!--	
	#########################################################################
	#########################################################################
	#########################################################################
-->
</head>
<body onload="initForm();initElementtype();">
  <form action="./ForceUWSave.jsp" method=post id="fm" name=fm target="fraSubmit">
    <table>
      <tr>
        <td>
          <table class="common">
            <tr class="common">
			        <td class="titleImg">
				        <IMG  src="../common/images/butExpand.gif" style= "cursor:hand;">
 			          ǿ�ƽ����˹��˱�
			        </td>
            </tr>
			    </table>
			    <div class="maxbox1">
			    <table class="common">
			      <tr class="common">
			        <td class="title5">
			          ǿ�ƽ����˹��˱�ѡ��			        
			        </td>
			        <td class="input5">
			          <input class="codeno" id="ForceUWOpt" name="ForceUWOpt" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="ǿ�ƽ����˹��˱�ѡ��|notnull&len<2" ondblclick="return showCodeList('forceuwopt',[this,ForceUWOptName],[0,1]);" onkeyup="return showCodeListKey('forceuwopt',[this,ForceUWOptName],[0,1]);"><input class="codename" id="ForceUWOptName" name="ForceUWOptName" elementtype=nacessary readonly="readonly">
			        </td>
			        <td class="title5">&nbsp;</td>
			        <td class="title5">&nbsp;</td>
			      </tr>
			      <tr>
			        <td colspan="4" class="title">ǿ�ƽ����˹��˱�ԭ��</td>
			      </tr>
			      <tr class="common">
			        <td class="input" colspan='4'>
			          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea class="common" cols="110" rows="4" name="ForceUWRemark" id=ForceUWRemark verify="">			          
			          </textarea>
			        </td>			      
			      </tr>			    
			    </table>
			    </div>
			    <table>
			      <tr>
			        <td>
			          <input type="button" class="cssButton" id="saveButton" name="saveButton" value="��  ��" onclick="submitForm();">
			        </td>
			        <td>
			          <input type="button" class="cssButton" id="quitButton" name="quitButton" value="��  ��" onclick="returnParent();">
			        </td>
			      </tr>
			    </table>
			  </td>
      </tr>
      <tr>
        <td>
          <input type= "hidden" id="ContNo" name= "ContNo" value= "">
        </td>
      </tr>
    </table>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>
