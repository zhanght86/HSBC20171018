<%
//�������ƣ�LLSubmitApplyInput.jsp
//�����ܣ�����ʱ���Ϣ¼��,�������ʱ��ڵ�
//�������ڣ�2005-05-10
//������  ��yuejw
//���¼�¼�� 
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<%
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI"); 	  
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	  String tCustomerNo = request.getParameter("custNo"); //�����˱���	  
	  String tCustomerName = request.getParameter("custName"); //����������
	  tCustomerName =  new String(tCustomerName.getBytes("ISO-8859-1"),"GB2312");
	  String tVIPFlag = request.getParameter("custVip"); //vip��־
	  
	  String tMissionID = request.getParameter("MissionID");  //����������ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID	
%>	
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="LLSubmitApply.js"></SCRIPT> 
    <%@include file="LLSubmitApplyInit.jsp"%>
        
</head>
<body onload="initForm()">
<form name=fm id=fm target=fraSubmit method=post>
   
	  <Table>
         <TR>
              <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubmitApplyForm);"></TD>
              <TD class= titleImg> ����ʱ���Ϣ </TD>
         </TR>
    </Table>
    <Div  id= "divLLSubmitApplyForm" style= "display: ''" class="maxbox1">    
        <TABLE class=common>
        	<tr class=common>
                <td class= title> �ͻ����� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=CustomerNo id=CustomerNo ></td>      
                <td class= title> �ͻ����� </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=CustomerName id=CustomerName ></td>   
                <td class= title></td>
                 <td class= input></td>  
                <!-- <td class= title> VIP��־ </td>
                <td class= input><Input type="input" class="readonly" readonly name=VIPFlag ></td>  -->
            </tr>              
        </TABLE> 
        <Table class= common>     
            <tr class= common>           	               
                <td class= title> �ʱ����� </td>
            </tr> 
            <tr class= common>                  
                <td colspan="6" style="padding-left:16px"><textarea name="SubDesc" id="SubDesc" cols="224" rows="4" witdh=25% class="common" ></textarea></td>
            </tr>
        </TABLE>  </Div>
        <Input class=cssButton value=" �� �� " type=button onclick="saveClick()">     
        <Input class=cssButton value=" �� �� " type=button onclick="top.close()">  
   
    <%
    //******************
    //�������ݵ����ر�
    //******************
    %>    
    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--��½��Ϣ�еĹ������-->
    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <Input type=hidden id="SubType" name="SubType"><!-- �ʱ�����-->
    <Input type=hidden id="SubPer" name="SubPer"><!--�ʱ���-->
    <Input type=hidden id="SubDate" name="SubDate"><!--�ʱ�����-->
    <Input type=hidden id="SubDept" name="SubDept"><!--�ʱ�����-->    
    <Input type=hidden id="SubRCode" name="SubRCode"><!--�ʱ�ԭ��-->    
    <Input type=hidden id="SubCount" name="SubCount"><!--�ʱ�����-->    
    <Input type=hidden id="InitPhase" name="InitPhase"><!--����׶�-->
    <Input type=hidden id="SubState" name="SubState"><!--�ʱ�״̬-->  
     <!--��½��Ϣ�еĹ��������ǰ��λ���룬�������ʱ��ڵ��л���-->
    <Input type=hidden id="MngCom" name="MngCom">
    <Input type=hidden id="FilialeDirector" name="FilialeDirector"><!--�ֹ�˾���κ���Ա-->
    
    <!--//����������-->
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
</Form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
