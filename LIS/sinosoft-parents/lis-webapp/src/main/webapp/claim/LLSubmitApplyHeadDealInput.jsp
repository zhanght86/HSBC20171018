<%
//�������ƣ�LLSubmitApplyDealInput.jsp
//�����ܣ��ʱ���Ϣ����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
	<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		  
	      String tClmNo=request.getParameter("ClmNo"); //�ⰸ��
	      String tSubNo=request.getParameter("SubNo"); //�ʱ����      
	      String tSubCount=request.getParameter("SubCount"); //�ʱ�����
		  String tMissionID =request.getParameter("MissionID");  //����������ID
		  String tSubMissionID =request.getParameter("SubMissionID");  //������������ID	 	
		  String tActivityID =request.getParameter("ActivityID");  //�ID	 		
	%>
	<title>�ʱ���Ϣ</title>  
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
    <SCRIPT src="LLSubmitApplyHeadDeal.js"></SCRIPT>
    <%@include file="LLSubmitApplyHeadDealInit.jsp"%>
</head>
<body  onload="initForm();">
	<Form action="./LLSubmitApplyHeadDealSave.jsp"  method=post name=fm id=fm target="fraSubmit">
	    <table>
	        <TR>
	        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLSubmit);"></TD>
	         	<TD class= titleImg>ѡ�гʱ��ľ�����Ϣ</TD>
	       	</TR>
	     </table> 
        <Div  id= "DivLLSubmit" style= "display: ''" class="maxbox">
     		<Table class= common>     				
		      	<TR  class= common>
			      	<TD  class= title> �ⰸ��</TD>
			      	<TD  class= input><Input class= "readonly wid" readonly id="ClmNo" name="ClmNo"></TD>
			        <TD  class= title>�ʱ����</TD>
			        <TD  class= input><Input class= "readonly wid" readonly id="SubNO" name="SubNO"></TD>
			        <TD  class= title>�ʱ�����</TD>
			        <TD  class= input><Input class= "readonly wid" readonly id="SubCount"  name="SubCount"></TD>
		        </TR>
        		<TR  class= common>   	
	      			<TD  class= title> �����˿ͻ���</TD>
			      	<TD  class= input><Input class= "readonly wid" readonly id="CustomerNo" name="CustomerNo"></TD>
			        <TD  class= title> ����������</TD>
			        <TD  class= input><Input class= "readonly wid" readonly id="CustomerName" name="CustomerName"></TD>
			      	<TD  class= title> VIP�ͻ�</TD>
	        		<TD  class= input><Input class= "readonly wid" readonly id="VIPFlag"  name="VIPFlag"></TD>
        		</TR>
		        <TR  class= common>   	
		        	<TD  class= title> ����׶�</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="InitPhase" id="InitPhase" ondblclick="return showCodeList('llinitphase',[this,InitPhaseName],[0,1]);" onclick="return showCodeList('llinitphase',[this,InitPhaseName],[0,1]);" onkeyup="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);"><input class=codename name="InitPhaseName" id="InitPhaseName" readonly=true></TD>
		            <TD  class= title> �ʱ�����</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="SubType" id="SubType" ondblclick="return showCodeList('llsubtype',[this,SubTypeName],[0,1]);" onclick="return showCodeList('llsubtype',[this,SubTypeName],[0,1]);" onkeyup="return showCodeListKey('llinitphase',[this,SubTypeName],[0,1]);"><input class=codename name="SubTypeName" id="SubTypeName" readonly=true></TD>
			        <TD  class= title> �ʱ�ԭ��</TD>
			        <TD  class= input><Input class= "readonly wid" readonly id="SubRCode"  name="SubRCode"></TD>
		        </TR>
		        <TR  class= common>   	
			        <TD  class= title> �ʱ���</TD>
			        <TD  class= input><Input class= "readonly wid" readonly id="SubPer" name="SubPer"></TD>
			        <TD  class= title> �ʱ�����</TD>
			      	<!--<TD  class= input><Input class= "readonly" readonly id="SubDept" name="SubDept"></TD>-->
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="SubDept" id="SubDept" ondblclick="return showCodeList('station',[this,SubDeptName],[0,1]);" onclick="return showCodeList('station',[this,SubDeptName],[0,1]);" onkeyup="return showCodeListKey('station',[this,SubDeptName],[0,1]);"><input class=codename name="SubDeptName" id="SubDeptName" readonly=true></TD>
			        <TD  class= title> �ʱ�����</TD>
			        <TD  class= input><Input class= "readonly wid" readonly id="SubDate"  name="SubDate"></TD>
		        </TR>
		        <TR  class= common>   	
			        <TD  class= title> �ʱ�״̬</TD>
			        <!--<TD  class= input><Input class= "readonly" readonly id="SubState" name="SubState"></TD>-->
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name="SubState" id="SubState" ondblclick="return showCodeList('llsubstate',[this,SubStateName],[0,1]);" onclick="return showCodeList('llsubstate',[this,SubStateName],[0,1]);" onkeyup="return showCodeListKey('llsubstate',[this,SubStateName],[0,1]);"><input class=codename name="SubStateName" id="SubStateName" readonly=true></TD>		        
			        <TD  class= title> �н���Ա�û���</TD>
			      	<TD  class= input><Input class= "readonly wid" readonly id="DispPer" name="DispPer"></TD>
			        <!--<TD  class= title> �нӻ�������</TD>
			        <TD  class= input><Input class= "readonly" readonly id="DispDept"  name="DispDept" ></TD>-->
		        </TR>
		    </Table>
		 	<Table class= common>
		        <TR >
	            	<TD class= title> �ʱ����� </TD>
	            </TR>     
	            <TR >  
	            	<TD style="padding-left:16px" class= input> <textarea name="SubDesc" id=SubDesc readonly cols="199" rows="4" witdh=25% class="common"></textarea></TD>
	            </TR>
      		</Table>      			
	    </Div>	
        <hr class="line">
	    <Div id= "DivDispType" style= "display: none">  
	    	<TABLE class= common>   
	        	<TR >
	        		<TD class= title> ѡ��ʱ���������</TD> 
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="DispType" id="DispType" ondblclick="return showCodeList('lldisptype',[this,DispTypeName],[0,1]);"  onclick="return showCodeList('lldisptype',[this,DispTypeName],[0,1]);" onkeyup="return showCodeListKey('lldisptype',[this,DispTypeName],[0,1]);"><input class=codename name="DispTypeName" id="DispTypeName" readonly=true></TD>
                    <TD class= title></TD>
                    <TD  class= input></TD>
                    <TD class= title></TD>
                    <TD  class= input></TD>
	            </TR>  
	        </TABLE> 
	    </Div>     
	    <Div id= "DivDispType" style= "display: ''">  
	        <!-- �Ա����Ļظ����   -->        
	    	<TABLE class= common> 
	        	<TR class= common>
	            	<TD class= title> �ʱ�������� </TD>
	            </TR>     
	            <TR class= common>  
	            	<TD style="padding-left:16px" class= input> <textarea name="DispIdea" id=DispIdea cols="199" rows="4" witdh=25% class="common"></textarea></TD>
	            </TR>
  	    	</TABLE> 
	    </Div> 
	    <!--<Div>          
	    	<TABLE class= common> 	        	
	            <TR>
	                <TD class=common>
	                	<Input class=cssButton  name="Replyconfirm" value="�ʱ�ȷ��" type=button onclick="Replyport()"> 
   	                    <Input class=cssButton  value=" �� �� " type=button onclick="TurnBack()">
   	                </TD> 
	            </TR>
  	    	</TABLE> 
	    </Div>-->
        <br><a  name="Replyconfirm" href="javascript:void(0);" class="button" onClick="Replyport();">�ʱ�ȷ��</a>
                        <a href="javascript:void(0);" class="button" onClick="TurnBack();">��    ��</a> 
	    <input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
	    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->	
	    <input type=hidden id="MissionID" name="MissionID"><!--����ID-->	
	    <input type=hidden id="SubMissionID" name="SubMissionID"><!--������ID-->	
	     <input type=hidden id="ActivityID" name="ActivityID"><!--������ID-->	
	</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
