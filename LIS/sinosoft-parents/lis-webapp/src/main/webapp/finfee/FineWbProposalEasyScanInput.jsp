<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2008-06-26 
//������  ��ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
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

//LoadFlag   3-- �쳣��
//           4-- ����
 	
 		String tLoadFlag = ""; 
		tLoadFlag = request.getParameter( "LoadFlag" );
		loggerDebug("FineWbProposalEasyScanInput","tLoadFlag"+tLoadFlag);
	 

%>
<head >
<script>
     var loadFlag = "<%=tLoadFlag%>";  //�жϴӺδ�������񵥴������,�ñ�����Ҫ�ڽ����ʼ��ǰ����.
     var TempFeeNo = "<%=request.getParameter("TempFeeNo")%>";��
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	
	<%@include file="./FineWbProposalInit.jsp"%>
	<SCRIPT src="FineWbProposalInput.js"></SCRIPT>
	<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
		

  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
  	
</head>

<body  onload="initForm(); " >
  <form action="./FineWbProposalSave.jsp" method=post name=fm id=fm target="fraSubmit">
 
    <Div  id= "divALL0" style= "display: ''">
    ��
    <!-- ������Ϣ���� -->
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
        </td>
        <td class= titleImg>
          �����շ��޸�          
        </td>
      </tr>
    </table>
    <Div  id= "divLCPol" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR  class= common>
         <TD  class= title>
            �������
          </TD>
          <TD  class= input>
            <Input class="code" name=ManageCom id=ManageCom verify="�������|code:station" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);">
          </TD>    
           <TD CLASS=title>
		      ��������
		  </TD>
		  <TD CLASS=input COLSPAN=1>
		      <Input NAME=PayDate id=PayDate class= "common wid" verify="��������|notnull&date"  onchange="changeEnterDate();">
          </TD>
          <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
                  <Input NAME=AgentCode id=AgentCode VALUE="" MAXLENGTH=10 CLASS=code style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return queryAgent();"onkeyup="return queryAgent2();" >
          </TD>
        </TR>
        <TR  class= common> 
          <TD  class= title>
            �ݽ�������
          </TD>          
          <TD  class= input>
            <Input class="code" name=TempFeeType id=TempFeeType verify="�ݽ�������|code:TempFeeType" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('TempFeeType',[this]);" onkeyup="return showCodeListKey('TempFeeType',[this]);" readonly=readonly>
          </TD>          
          <TD  class= title>
            Ͷ����ӡˢ��
          </TD>
          <TD  class= input>
            <Input  class="common wid" name=OtherNo id=OtherNo verify="ӡˢ��|len<=20" onchange="changeOtherNo();">
          </TD>
          <TD  class= title>
            ��֤ӡˢ��
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=TempFeeNo id=TempFeeNo >
          </TD>
        </TR>                    
      </table>
    </Div>           
    
     <Div  id= "divTempFeeInput" style= "display: ''">
  <!--¼����ݽ��ѱ��� -->
    <Table  >
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
  <!--¼����ݽ��ѷ������ -->
    <Table  class= common>
      	<tr>
    	 <td style="text-align: left" colSpan=1>
	 <span id="spanTempClassGrid" >
	 </span> 
	</td>
       </tr>
    </Table>         
    </Div>
        <input type=hidden id="fmAction" name="fmAction">
		<input  type=hidden id="fmLoadFlag" name="fmLoadFlag">
		<input type=hidden name="DealType" id=DealType value="<%=request.getParameter("DealType")%>" >
		<input type=hidden name="aftersave" id=aftersave value="0"   >��
		<input type=hidden name="TempFeeNoHide" id=TempFeeNoHide value=""   >��
  
  <Div  id= "divButton" style= "display: ''">
  <br>
  <%@include file="../app/ProposalOperateButtonSpec.jsp"%>
  </Div> 
  </Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>


