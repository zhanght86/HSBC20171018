<%@include file="../i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-15 11:48:42
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%
	String tPNo = "";
	try
	{
		tPNo = request.getParameter("PNo");

	}
	catch( Exception e )
	{
		tPNo = "";
	}

//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
    /**********************************************
     *LoadFlag=1 -- ����Ͷ����ֱ��¼��
     *LoadFlag=2 -- �����¸���Ͷ����¼��
     *LoadFlag=3 -- ����Ͷ������ϸ��ѯ
     *LoadFlag=4 -- �����¸���Ͷ������ϸ��ѯ
     *LoadFlag=5 -- ����
     *LoadFlag=6 -- ��ѯ
     *LoadFlag=7 -- ��ȫ�±�����
     *LoadFlag=8 -- ��ȫ����������
     *LoadFlag=9 -- ������������
     *LoadFlag=10-- ��������
     *LoadFlag=13-- ������Ͷ���������޸�
     *LoadFlag=16-- ������Ͷ������ѯ
     *LoadFlag=25-- �˹��˱��޸�Ͷ����
     *LoadFlag=99-- �涯����
     *
     ************************************************/

	String tLoadFlag = "";
	try
	{
		tLoadFlag = request.getParameter( "LoadFlag" );
		//Ĭ�������Ϊ���˱���ֱ��¼��
		if( tLoadFlag == null || tLoadFlag.equals( "" ))
			tLoadFlag = "1";
	}
	catch( Exception e1 )
	{
		tLoadFlag = "1";
	}

	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getAttribute("GI");
	
	
  System.out.println("LoadFlag:" + tLoadFlag);
%>
<script type="text/javascript">
  var ManageCom ="<%=tGI.ManageCom%>";
</script>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  
  

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 �ڱ�ҳ����������JSҳ��              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script> 
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/Calendar/Calendar.js"></script>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    ���뱾ҳContInput.js           
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="RiskTypeSelect.js"></script>
 
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>


<body  onload="initForm();" >
  <form action="" method=post name=fm target="fraSubmit">
<table class=common>
			<TR class=common>
				<TD class=title5>���ֱ���</TD>
				<TD class=input5>
	<Input class="codeno" name=RiskCode  verify="���ֱ���|NOTNUlL&CODE:RiskCode" ondblclick="showCodeList('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');" onkeyup="showCodeListKey('RiskCode',[this,RiskCodeName],[0,1],null,null,null,1,'400');"><input class="common" name="RiskCodeName" readonly="readonly">
					<!--input class=codeno name=CardType ondblclick="getcodedata2();return showCodeListEx('CardType',[this,CardTypeName],[0,1],null,null,null,true,'400');" onkeyup="getcodedata2();return showCodeListEx('CardType',[this,CardTypeName],[0,1],null,null,null,1,'400');"><input class=codename name=CardTypeName readonly="readonly" elementtype=nacessary-->							
				</TD>
		</TR>
</table>
<%
          String today=PubFun.getCurrentDate();
          
          %>
			<input type=hidden id="" name="sysdate" value="<%=today%>">
			<input type=hidden id="" name="ManageCom" >
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>


</body>

</html>
