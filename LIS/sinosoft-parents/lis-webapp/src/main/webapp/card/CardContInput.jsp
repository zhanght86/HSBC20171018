<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-08-15 11:48:42
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
	
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
	tGI = (GlobalInput)session.getValue("GI");
	String tCurrentDate =PubFun.getCurrentDate();
	//CardRisk
	String mCardRisk =request.getParameter( "CardRisk" )==null?"":request.getParameter( "CardRisk" );
	String mCardFace = "";
	mCardFace = "../riskinput/cardinput/Card"+mCardRisk+".jsp";
  loggerDebug("CardContInput","mCardRisk:" + mCardRisk+"mCardFace:"+mCardFace);
%>

<%
 if(mCardRisk.equals(""))
 {
%>
<script language="javascript">
	
	alert("����ѡ�����");
	parent.fraInterface.location='./CardTypeSelect.jsp';
	
</script>
<%
}

%>
<script language="javascript">
   var CardFace ="<%=mCardFace%>";
   var CardRisk ="<%=mCardRisk%>";
   var CurrentDate ="<%=tCurrentDate%>";
   //alert(CardFace);
   //fm.RiskCode.value =tRiskCode;
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                 �ڱ�ҳ����������JSҳ��              
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="../common/javascript/Common.js"></script>
  <script src="../common/cvar/CCodeOperate.js"></script> 
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
  <script src="../common/javascript/VerifyInput.js"></script>
  
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
             ���뱾ҳContInit.jspҳ��                               
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<%@include file="CardContInit.jsp"%>
<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->

<!--
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
                    ���뱾ҳContInput.js           
    VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
-->
  <script src="CardContInput.js"></script>
 
</head>
<body  onload="initForm3();" >
<form action="./CardContSave.jsp" method=post name=fm id=fm target="fraSubmit">
 <jsp:include page="<%=mCardFace%>" />
 <Div id="HiddenButton" style="display:''" style= "float: left" >
   <INPUT class=cssButton id="riskbutton9" VALUE="��һ��" TYPE=button onclick="returnparent();">
 </Div>
 <Div  id= "HiddenValue" style= "float: right" style="float: right" style="display:''">
    	
    	
    	<!--INPUT class=cssButton id="Donextbutton8" VALUE="��  �� "  TYPE=button onclick="submitForm();"-->
        <input class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">      			
    	<INPUT class=cssButton id="Donextbutton8" VALUE="��  ��"  TYPE=button onclick="submitForm1();" >
      <!-- INPUT class=cssButton id="Donextbutton9" VALUE="ǩ  ��"  TYPE=button onclick="submitForm2();"-->  
      
      <INPUT class=cssButton id="Donextbutton10" VALUE="ɾ  ��"  TYPE=button onclick="deleteClick2();">  	 	
    
    </div> 
    <Input type=hidden name=RiskCode value="<%=mCardRisk%>">
    </form>
 	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 	</body>
</html>
