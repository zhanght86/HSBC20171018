<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
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

//�õ�����ĵ���λ��,Ĭ��Ϊ1,��ʾ���˱���ֱ��¼��.
// 1 -- ����Ͷ����ֱ��¼��
// 2 -- �����¸���Ͷ����¼��
// 3 -- ����Ͷ������ϸ��ѯ
// 4 -- �����¸���Ͷ������ϸ��ѯ
// 5 --
// 6 --



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
loggerDebug("ProposalGrpInput","LoadFlag:" + tLoadFlag);
%>
<head >
<script>
	var loadFlag = "<%=tLoadFlag%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����.
	var prtNo = "<%=request.getParameter("prtNo")%>";
	var type = "<%=request.getParameter("type")%>";
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	
	<SCRIPT src="ProposalInput.js"></SCRIPT>
	<%@include file="ProposalInit.jsp"%>
	<SCRIPT src="ProposalAutoMove.js"></SCRIPT>
	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	
<% if (request.getParameter("type") == null) { %>	
	<SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
<% } %>
</head>




<body  onload="" >
 <!--
  <form action="./ProposalSave.jsp" method=post name=fm target="fraSubmit">
   -->
    <Div  id= "divButton" style= "display:  ">
    <%@include file="../common/jsp/ProposalOperateButton.jsp"%>
    </DIV>
    <Div  id= "divRiskCode0">
    <table class=common>
       <tr class=common>
          <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class="code" name=RiskCode id="RiskCode" ondblclick="showCodeList('RiskGrp',[this]);" onkeyup="return showCodeListKey('RiskGrp',[this]);">
          </TD>
       </tr>
    </table>
    </Div>


<!-- ������Ϣ -->
    <Div  id= "divALL0" style= "display: none">
    <!-- ������������Ϣ���֣��б� -->
	<Div  id= "divLCInsured0" style= "display: none">
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
    		</td>
    		<td class= titleImg>
    			 ������������Ϣ
    		</td>
    	</tr>
      </table>
	  <Div  id= "divLCInsured2" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left;" colSpan=1>
					<span id="spanSubInsuredGrid" >
					</span> 
				</td>
			</tr>
		</table>
	  </div>
	</div>
    <!-- ��������Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnf1);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCBnf1" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanBnfGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��֪��Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
    		</td>
    		<td class= titleImg>
    			 ��֪��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCImpart1" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanImpartGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
    <!-- ��Լ��Ϣ���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCSpec1);">
    		</td>
    		<td class= titleImg>
    			 ��Լ��Ϣ
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCSpec1" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanSpecGrid">
					</span> 
				</td>
			</tr>
		</table>
	</div>

    <!--����ѡ������β��֣��ò���ʼ������-->
	<Div  id= "divDutyGrid" style= "display:  ">
    	<table  class= common>
        	<tr  class= common>
    	  		<td style="text-align: left" colSpan=1>
					<span id="spanDutyGrid" >
					</span> 
				</td>
			</tr>
		</table>
		<!--ȷ���Ƿ���Ҫ������Ϣ-->
	</div>
		<input type=hidden id="inpNeedDutyGrid" name="inpNeedDutyGrid" value="0">
		<input type=hidden id="fmAction" name="fmAction">
  </Div>
  <!--</form> -->
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>


