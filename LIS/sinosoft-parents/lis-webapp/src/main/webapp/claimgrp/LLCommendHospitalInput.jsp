<html>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <!--�û�У����-->
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�LLCommendHospitalInput.jsp
//�����ܣ�ҽԺ��Ϣά��
//�������ڣ�2005-7-13
//������  ��yuejw
//���¼�¼��  ������ yuejw    ��������     ����ԭ��/����
%> 
<head >
	<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLCommendHospital.js"></SCRIPT>
    <%@include file="LLCommendHospitalInit.jsp"%>
</head>

<body  onload="initForm();">	
<form action="" method=post name=fm target="fraSubmit">   	
    <!-- ҽԺ��Ϣά��-->    
    <hr>                                
	<Table>
	     <TR>
	     	<TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,QueryHospital);"></TD>
	        <TD class= titleImg>�Ƽ�ҽԺ��Ϣ��ѯ</TD>
	        <TD> <input value="��   ѯ" class= cssButton type=button onclick="InitQueryClick();"></TD>
	     </TR>
	</Table>  
	<Div  id= "QueryHospital" style= "display:''">
	    <Table  class= common>
            <TR class= common>  
					<TD  class= title>ҽԺ����</TD>
					<TD  class= input> <input class=common name=HospitalCodeQ></TD>
	                <TD  class= title>ҽԺ����</TD>      
	                <TD  class= input> <input class=common name=HospitalNameQ></TD>
	                <TD  class= title></TD>
					<TD  class= input></TD>
	       </TR>   
	    </Table>  
	</Div>         	 
    <hr>

	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCommendHospitalGrid);"></TD>
	          <TD class= titleImg>�Ƽ�ҽԺ�б�</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLCommendHospitalGrid" style= "display:''">    
		<Table  class= common>
		    <TR>
		    	<TD text-align: left colSpan=1><span id="spanLLCommendHospitalGrid"></span></TD>
		    </TR>
		</Table>
		<input class= button value=" ��  ҳ " type=button onclick="getFirstPage();"> 
	    <input class= button value=" ��һҳ " type=button onclick="getPreviousPage();">                   
	    <input class= button value=" ��һҳ " type=button onclick="getNextPage();"> 
	    <input class= button value=" β  ҳ " type=button onclick="getLastPage();">  
	</Div>
    <hr>
	<Table>
	     <TR><TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLCommendHospitalInfo);"></TD>
	          <TD class= titleImg>�Ƽ�ҽԺ��Ϣ</TD>
	     </TR>
	</Table>  
    <Div id= "DivLLCommendHospitalInfo" style= "display:''">       
        <Table>
            <TR>
                <TD><input class=cssButton name="saveHospitalButton"   value="��   ��"  type=button onclick="HospitalAddClick();"></TD>
				<TD><input class=cssButton name="editHospitalButton"   value="��   ��"  disabled type=button onclick="HospitalEditClick();"></TD>
				<TD><input class=cssButton name="deleteHospitalButton" value="ɾ   ��"  disabled type=button onclick="HospitalDeleteClick();"></TD>                      
                <TD><input class=cssButton name="resetHospitalButton"  value="��   ��" type=button onclick="HospitalResetClick();"></TD>                
            </TR>
        </Table>    
        <Table  class= common>
            <TR class= common>   
				<TD  class= title>ҽԺ����</TD>
				<TD  class= input> <input class=common name=HospitalCode><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title>ҽԺ����</TD>      
				<TD  class= input> <input class=common name=HospitalName><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title>ҽԺ�ȼ�</TD>      
				<!--<TD  class= input> <input class=common name=HosAtti></TD>-->         
               <TD class= input><Input class=codeno readonly name="HosAtti" ondblclick="return showCodeList('llhosgrade',[this,HosAttiName],[0,1]);" onkeyup="return showCodeListKey('llhosgrade',[this,HosAttiName],[0,1]);"><input class=codename name="HosAttiName" readonly=true></TD> 	              
           </TR>
            <TR class= common>   
				<TD  class= title>�����־</TD>
				<!--<TD  class= input> <input class=common name=ConFlag></TD>-->
                <TD  class= input><input class=codeno readonly name=ConFlag CodeData="0|3^0|����^1|�Ƕ���" ondblclick="return showCodeListEx('ConFlag', [this,ConFlagName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('ConFlag', [this,],ConFlagName[0,1],'','','','',100);"><input class=codename name=ConFlagName readonly ></TD>
				<TD  class= title>�м��������ʱ�־</TD>      
				<!--<TD  class= input> <input class=common name=AppFlag></TD>-->
                <TD  class= input><input class=codeno readonly name=AppFlag CodeData="0|3^0|������^1|������" ondblclick="return showCodeListEx('AppFlag', [this,AppFlagName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('AppFlag', [this,AppFlagName],[0,1],'','','','',100);"><input class=codename name=AppFlagName readonly ></TD>
				<TD  class= title>ҽԺ״̬</TD>      
				<!--<TD  class= input> <input class=common name=HosState></TD>-->         
                <TD  class= input><input class=codeno readonly name=HosState CodeData="0|3^0|��Ч^1|��ͣ^2|��ֹ" ondblclick="return showCodeListEx('HosState', [this,HosStateName],[0,1],'','','','',100)"onkeyup="return showCodeListKeyEx('HosState', [this,HosStateName],[0,1],'','','','',100);"><input class=codename name=HosStateName readonly ><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>            
		</Table>                             
    </Div>   
    <hr>
    
    <!--���ر�����-->	
 	<input type=hidden id="ConsultNo" name=ConsultNo > <!--��ѯ֪ͨ����-->
    
 	<input type=hidden id="tOperator" name=tOperator > 
	<input type=hidden id="tComCode" name=tComCode > 	
	<input type=hidden id="fmtransact" name="fmtransact">        
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
