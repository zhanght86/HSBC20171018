<%@page contentType="text/html;charset=GBK" %>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<%
//==========BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��
//==========END
%>
<title>�ʱ���Ϣ��ѯ</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLSubmitQuery.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLSubmitQueryInit.jsp"%>
</head>
<body onload="initForm();">
  
<form name=fm id=fm target=fraSubmit method=post>
    <!--�ʱ���Ϣ�鿴-->        
    <Div id= "DivLLSubmitApplyGrid" style= "display: ''">
		<Table>
			<TR>
			    <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"></TD>
			    <TD class= titleImg> ���ⰸ�µĳʱ���Ϣ�б� </TD>
			</TR>
		</Table>   
         <Table class= common>
             <TR class= common>
                 <TD text-align: left colSpan=1><span id="spanLLSubmitApplyGrid" ></span> </TD>
             </TR>
         </Table>
         <!--<table> 
             <TR>  
                 <TD><INPUT class=cssButton VALUE=" ��ҳ " TYPE=button onclick="turnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton VALUE=" βҳ " TYPE=button onclick="turnPage.lastPage();"></TD>
             </TR> 
         </table>-->    
    </Div>
	    
    <!------�ʱ��������ϢӦ���У�����������ơ������ˡ�����ʱ�䡢�ʱ�������---------->
    <Div id= "DivLLSubmitApplyInfo" style= "display: ''">
    	<Table>
	        <TR>
	            <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,vvb);"></td>
	            <TD class= titleImg> �ʱ�������ϸ��Ϣ </TD>
	        </TR>
	    </Table><Div  id= "vvb" style= "display: ''" class="maxbox1">
    	<Table class=common>
           <TR class=common>
               <TD class=title> ������ </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="InitSubPer" id="InitSubPer"></TD>
               <TD class=title> ������� </TD>
               <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="InitSubDept" id="InitSubDept" ondblclick="return showCodeList('stati',[this,InitSubDeptName],[0,1]);" onclick="return showCodeList('stati',[this,InitSubDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitSubDeptName],[0,1]);"><input class=codename name="InitSubDeptName" id="InitSubDeptName" readonly=true></TD> 	                        
               <TD class=title> �������� </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="InitSubDate" id="InitSubDate"></TD>              
           </TR>
        <Table>   
        <Table class=common>
			<TR class= common>
               <TD class= title> �������� </TD>
           	</TR> 
           	<TR class= common>       
               <TD colspan="6" style="padding-left:16px"> <textarea name="InitSubDesc" cols="230" rows="4" witdh=25% class="common" readonly ></textarea></TD>
           	</TR>
        <Table>   		
   	</Div> 	</div>
    
    
    <!------�ֹ�˾���������ϢӦ���У�����������ơ������ˡ�����ʱ�䡢�������͡��������---------->
    <Div id= "DivFilialeDispInfo" style= "display: ''">
    	<Table>
			<TR>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,rtr);"></td>
				<TD class= titleImg> �ֹ�˾�ʱ����������ϸ��Ϣ </TD>
			</TR>
		</Table><Div  id= "rtr" style= "display: ''" class="maxbox1">
    	<Table class=common>
           <TR class=common>
               <TD class=title> ������ </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="FilialeDispPer" id="FilialeDispPer"></TD>
               <TD class=title> ������� </TD>
               <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="FilialeDispDept" id="FilialeDispDept" ondblclick="return showCodeList('stati',[this,FilialeDispDeptName],[0,1]);" onclick="return showCodeList('stati',[this,FilialeDispDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,FilialeDispDeptName],[0,1]);"><input class=codename name="FilialeDispDeptName" id="FilialeDispDeptName" readonly=true></TD> 	                        
               <TD class=title> �������� </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="FilialeDispDate" id="FilialeDispDate"></TD>              
           </TR>
           <TR class=common>
               <TD class=title> �������� </TD>
			   <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="FilialeDispType" id="FilialeDispType" ondblclick="return showCodeList('lldisptype',[this,FilialeDispTypeName],[0,1]);" onclick="return showCodeList('lldisptype',[this,FilialeDispTypeName],[0,1]);" onkeyup="return showCodeListKey('lldisptype',[this,FilialeDispTypeName],[0,1]);"><input class=codename name="FilialeDispTypeName" id="FilialeDispTypeName" readonly=true></TD>               
                <TD class=title></TD>
               <TD class= input></TD> 	                        
               <TD class=title> </TD>
               <TD class= input></TD> 
          </TR>
        <Table>   	
        <Table class=common>
			<TR class= common>
               <TD class= title> �ֹ�˾������� </TD>
           	</TR> 
           	<TR class= common>       
               <TD colspan="6" style="padding-left:16px"> <textarea name="FilialeDispIdea" cols="230" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>
        <Table>   		
   	</Div> 	</div>
   	
		
    <!------�ܹ�˾���������ϢӦ���У�����������ơ������ˡ�����ʱ�䡢�������---------->
    <Div id= "DivHeadDispInfo" style= "display: ''">
    	<Table>
			<TR>
				<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,zxc);"></td>
				<TD class= titleImg> �ܹ�˾�ʱ����������ϸ��Ϣ </TD>
			</TR>
		</Table><Div  id= "zxc" style= "display: ''" class="maxbox1">
    	<Table class=common>
           <TR class=common>
               <TD class=title> ������ </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="HeadDispPer" id="HeadDispPer"></TD>
               <TD class=title> ������� </TD>
               <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="HeadDispDept" id="HeadDispDept" ondblclick="return showCodeList('stati',[this,HeadDispDeptName],[0,1]);"onclick="return showCodeList('stati',[this,HeadDispDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,HeadDispDeptName],[0,1]);"><input class=codename name="HeadDispDeptName" id="HeadDispDeptName" readonly=true></TD> 	                        
               <TD class=title> �������� </TD>
               <TD class= input><Input type="input" class="readonly wid" readonly name="HeadDispDate" id="HeadDispDate"></TD>              
           </TR>
        <Table>   
        <Table class=common>
			<TR class= common>
               <TD class= title> �ܹ�˾������� </TD>
           	</TR> 
           	<TR class= common>       
               <TD colspan="6" style="padding-left:16px"> <textarea name="HeadDispIdea" cols="230" rows="4" wiTDh=25% class="common" readonly ></textarea></TD>
           	</TR>
        <Table>   		
   	</Div> </div>	
    
    <Input class=cssButton value=" �� �� " type=button onclick=top.close() align= center>
     
    <!-----�������ݵ����ر�----->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--�ⰸ��-->
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
