<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//�������ƣ�SelfRiskInformationQuery.jsp
//�����ܣ���������������Ϣ��ѯ
//�������ڣ�2008-03-14
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tCertifyNo = "";
	try
	{
		tCertifyNo = request.getParameter("CertifyNo");
		loggerDebug("SelfRiskInformationQuery","������Ϣ��ѯ��ÿ���="+tCertifyNo);
	}
	catch( Exception e )
	{
		tCertifyNo = "";
	}
	
	String tCertifyCode=""; //��֤����
	
	if(!tCertifyNo.equals(""))
	{
		//û�н�λ
		if(tCertifyNo.substring(4,5).equals("0")&&tCertifyNo.substring(5,6).equals("0"))
		{
			tCertifyCode=tCertifyNo.substring(2,4)+tCertifyNo.substring(6,8);
		}
		  
	    //��λ������,����λ�汾�ŵĵ�1λ������
		else if(tCertifyNo.substring(5,6).equals("0"))
		{
			tCertifyCode=tCertifyNo.substring(2,8);
		}
		//��λ�汾�ŵĵ�2λ������
		else
		{
			tCertifyCode=tCertifyNo.substring(2,4)+tCertifyNo.substring(5,8);
		}

		loggerDebug("SelfRiskInformationQuery","����"+tCertifyNo+"��Ӧ�ĵ�֤����:"+tCertifyCode);
		
	}
	
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

%>
<script>
    var CertifyCode="<%=tCertifyCode%>"
</script>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="SelfRiskInformationQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SelfRiskInformationQueryInit.jsp"%>
  <title>���ֻ�����Ϣ��ѯ</title>
</head>
<body  onload="initForm();" >
  <form action="" method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCertifyInfo);"></td>
    	<td class= titleImg>���ֻ�����Ϣ</td></tr>
    </table>
    
  	<div id="divRiskInfo" style="display: ''">
      <table class="common">
        <tr class="common">
      	  <td text-align: left colSpan=1><span id="spanRiskInfo"></span></td></tr>
      </table>					
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
