<%
//�������ƣ�LLInqApplyInput.jsp
//�����ܣ����������Ϣ¼��
//�������ڣ�2005-05-10
//������  ��zhoulei
//���¼�¼��yuejw
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%

	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI"); 
	  
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
	  String tCustomerNo = request.getParameter("custNo"); //�����˱���
	  String tCustomerName ="";
//	  String tCustomerName = request.getParameter("custName"); //����������
//	  tCustomerName =  new String(tCustomerName.getBytes("ISO-8859-1"),"GB2312");
	  String tVIPFlag = request.getParameter("custVip"); //vip��־
	  String tInitPhase = request.getParameter("initPhase"); //����׶�
	  
	  String tMissionID = request.getParameter("MissionID");  //����������ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID	

%>
    <title>������Ϣ¼��</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLInqApply.js"></script> 
    <%@include file="LLInqApplyInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
    <!--������Ϣ-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApply1);"></TD>
            <TD class= titleImg>�������</TD>
        </TR>
    </table>
    <Div id= "divLLInqApply1" style= "display: ''" class="maxbox">
        
        <TABLE class=common>
            <tr class=common>
                <td class=title>�����˴���</td>
                <td class= input><Input class="readonly wid" readonly  name=CustomerNo id=CustomerNo></td>
		        <td class=title>����������</td>
                <td class= input><Input class="readonly wid" readonly  name=CustomerName id=CustomerName></td>
                <td class=title> �������� </td>
                <td class= input><Input class="readonly wid" readonly  name=BatNo id=BatNo></td>
		        <!--<td class=title>VIP��־</td>
                <td class= input><Input class="readonly" readonly  name=VIPFlag></td>  -->
            </tr>        	
            <tr class=common>
                <!--<td class=title> ����׶� </td>-->
                <!--<td class= input><Input class=codeno readonly  name="ApplyPhase" ondblclick="return showCodeList('llInitPhase',[this,ApplyPhaseName],[0,1]);" onkeyup="return showCodeListKey('llInitPhase',[this,ApplyPhaseName],[0,1]);"><input class=codename name="ApplyPhaseName" readonly=true></TD>-->
                
                <td class=title> ����ԭ�� </td>
                <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqReason" id="InqReason" ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1]);"><input class=codename name="InqReasonName" id="InqReasonName" readonly=true></TD>                                 
                <td class=title> ������� </td>
		        <td class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="InqOrg" id="InqOrg" ondblclick="return showCodeList('stati',[this,InqOrgName],[0,1],null,null,null,1);" onclick="return showCodeList('stati',[this,InqOrgName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('stati',[this,InqOrgName],[0,1],null,null,null,1);"><input class=codename name="InqOrgName" id="InqOrgName" readonly=true></TD> 	
		        <td ><input style="vertical-align:middle" type="checkbox" value="1" name="MoreInq" onclick="MoreInqClick()"><span style="vertical-align:middle">��ص���</span></input></td> 	
            </tr> 
            
            <!--
        	  <TR  class= common>
                <td class=title> ������ </td>
                <td class= input><Input class="readonly" readonly  name=Proposer ></td>      
                <td class=title> ����ʱ�� </td>
                <td class= input><Input class="readonly" readonly  name=ApplyTime ></td>   
                <td class=title> ������� </td>
                <td class= input><Input class="readonly" readonly  name=ApplyOrg ></td>                         
		      </TR>  
		      -->
        </table>        
        <Table class= common>		
                <tr class= common>
	    	        <td class= title> ������Ŀ </td>
    		    </tr> 
		        <tr class= common>       
	    	        <td colspan="6" style="padding-left:16px"> <textarea name="InqItem" cols="224"  rows="4" class="common"></textarea></td>
		        </tr>
    	    
	    	    <tr class= common>
	    	        <td class= title> �������� </td>
    		    </tr> 
		        <tr class= common>       
	    	        <td colspan="6" style="padding-left:16px"> <textarea name="InqDesc" cols="224" rows="4" class="common"></textarea></td>
		        </tr>
        </TABLE>
    </Div>
    <table style="display:none">
            <tr>
                <td><input class=cssButton name="AddBatNo" value="�������κ�" type=button onclick="AddBatNoClick()"></td>
                <td><Input class=cssButton name="saveAdd"  value="��      ��" type=button onclick="saveClick()"></td> 
                <td><Input class=cssButton name="doClose"  value="��     ��" type=button onclick="top.close()"></td> 
            </tr>
        </table>
    <a href="javascript:void(0);" name="AddBatNo" class="button" onClick="AddBatNoClick();">�������κ�</a>
    <a href="javascript:void(0);" name="saveAdd" class="button" onClick="saveClick();">��    ��</a>
    
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyGrid);"></TD>
            <TD class= titleImg> ���ⰸ�Ѿ�����ĵ�����Ϣ�б� </TD>
        </TR>
    </Table>       
    <Div  id= "divLLInqApplyGrid" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
             </TR>
         </Table>
         <!--<table> 
             <tr>  
                 <td><INPUT class=cssButton VALUE=" ��ҳ " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" βҳ " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr> 
         </table> --> 
    </Div><a href="javascript:void(0);" name="doClose" class="button" onClick="top.close();">��    ��</a>
    <!--�������ݵ����ر�-->
    <Input type=hidden id="InqOrg2" name="InqOrg2"><!--���صĵ������������disabledʱ������Ϣ��ע��input����Ϊdisabledʱ��request.getParameter()ȡ����ֵ��-->
    
    <Input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->
    <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
    
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    <!--<Input type=hidden id="BatNo" name="BatNo">   ���κ�-->    
    <Input type=hidden id="InqNo" name="InqNo"><!--�������-->
    <!--<Input type=hidden id="CustomerNo" name="CustomerNo"> �����˴���-->
    <!--<Input type=hidden id="CustomerName" name="CustomerName"> ����������-->
    <!-- <Input type=hidden id="VIPFlag" name="VIPFlag">VIP��־-->
    <Input type=hidden id="InitPhase" name="InitPhase"><!--����׶�-->    
    <Input type=hidden id="LocFlag" name="LocFlag"><!--���ر�־-->
        <!--//����������-->
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>			
</body>
</html>
