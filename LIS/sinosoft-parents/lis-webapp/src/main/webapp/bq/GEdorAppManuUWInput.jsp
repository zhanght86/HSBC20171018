<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * @direction: �ŵ���ȫ�˹��˱������������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>���屣ȫ�˱�</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű�  -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT> 
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="GEdorAppManuUW.js"></script>
    <SCRIPT language="JavaScript" src="./PEdor.js"></SCRIPT>
    <%@ include file="GEdorAppManuUWInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id="fm" method="post" target="fraSubmit">
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divEdorAppInfo)"></td>
                <td class="titleImg">��ȫ������Ϣ</td>
            </tr>
        </table>
        <!-- ������Ϣ���չ�� -->
        <div id="divEdorAppInfo" style="display:''" class="maxbox">
            <table class="common">
                <tr class="common">
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id="EdorAcceptNo" readonly></td>
                    <td class="title">�������</td>
                    <td class="input"><input type="text" class="readonly wid" name="OtherNo" id="OtherNo" readonly></td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="codeno" name="OtherNoType" id="OtherNoType" readonly><input type="text" class="codename" name="OtherNoTypeName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="readonly wid" name="EdorAppName" id="EdorAppName" readonly></td>
                    <td class="title">���뷽ʽ</td>
                    <td class="input"><input type="text" class="codeno" name="AppType" id="AppType" readonly><input type="text" class="codename" name="AppTypeName" readonly></td>
                    <td class="title">�������</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom" id="ManageCom" readonly><input class="codename" name="ManageComName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">���˷ѽ��</td>
                    <td class="input"><input type="text" class="readonly wid" name="GetMoney" id="GetMoney" readonly></td>
                    <td class="title">���˷���Ϣ</td>
                    <td class="input"><input type="text" class="readonly wid" name="GetInterest" id="GetInterest" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </div>
        <!-- �ŵ���Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divGrpContInfo)"></td>
                <td class="titleImg">���屣����Ϣ</td>
           </tr>
        </table>
        <!-- �ŵ���Ϣ���չ�� -->
        <div id="divGrpContInfo" style="display:''" class="maxbox">
            <table class="common">
                <tr class="common">
                    <td class="title">���屣����</td>
                    <td class="input"><input type="text" class="readonly wid" name="GrpContNo" id="GrpContNo" readonly></td>
                    <td class="title">����ͻ���</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntNo" id="AppntNo" readonly></td>
                    <td class="title">Ͷ����λ</td>
                    <td class="input"><input type="text" class="readonly wid" name="AppntName" id="AppntName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">Ͷ������</td>
                    <td class="input"><input type="text" class="readonly wid" name="Peoples2" id="Peoples2" readonly></td>
                    <td class="title">�������</td>
                    <td class="input"><input type="text" class="codeno" name="ManageCom" id="ManageCom" readonly><input type="text" class="codename" name="ManageComName" readonly></td>
                    <td class="title">��������</td>
                    <td class="input"><input type="text" class="codeno" name="SaleChnl" id="SaleChnl" readonly><input type="text" class="codename" name="SaleChnlName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">�����˱���</td>
                    <td class="input"><input type="text" class="readonly wid" name="AgentCode" id="AgentCode" readonly></td>
                    <td class="title">����������</td>
                    <td class="input"><input type="text" class="readonly wid" name="AgentName" id="AgentName" readonly></td>
                    <td class="title">���������</td>
                    <td class="input"><input type="text" class="codeno" name="AgentGroup" id="AgentGroup" readonly><input type="text" class="codename" name="AgentGroupName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">VIP ���</td>
                    <td class="input"><input type="text" class="codeno" name="VIPValue" id="VIPValue" readonly><input type="text" class="codename" name="VIPValueName" readonly></td>
                    <td class="title">���������</td>
                    <td class="input"><input type="text" class="codeno" name="BlacklistFlag" id="BlacklistFlag" readonly><input type="text" class="codename" name="BlacklistFlagName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </div>
        
    <Div  id= "divEdorItemInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class=titleImg>��ȫ������Ŀ��Ϣ</td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
        </div>
    </DIV>
        <!-- ������Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divContInfo)"></td>
                <td class="titleImg">������Ϣ</td>
           </tr>
        </table> 
        <div id="divContInfo" style="display:''" class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="input">                       
                        <input type="button" class="cssButton" value="������ϸ��Ϣ" onclick="queryContDetail()">
                        <input type="button" class="cssButton" value="Ͷ����Ӱ���ѯ" onclick="scanQuery()">
                        <input type="button" class="cssButton" value="Ͷ����λ�ѳб�����" onclick="queryProposal()">
                        <input type="button" class="cssButton" value="Ͷ����λδ�б�����" onclick="queryNotProposal()">
                        <input type="button" class="cssButton" value="Ͷ����λ������ȫ" onclick="queryAgoEdor()">
                        <input type="button" class="cssButton" value="Ͷ����λ��������" onclick="queryAgoClaim()">
                        <br><br>
                        
                        <!--<input type="button" class="cssButton" value="   Ͷ����λ�����ۼ�   " onclick=""> -->
                    </td>
                </tr>
            </table>
        </div>               
        <!-- ������Ϣ�۵�չ�� -->
        <!--table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divGrpPolList)"></td>
                <td class="titleImg">���屣��������Ϣ</td>
           </tr>
        </table-->
        <!-- ������Ϣ���չ�� -->
        <!--div id="divGrpPolList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanGrpPolGrid"></span></td>
                </tr>
            </table>
            <!-- ������Ϣ�����ҳ -->
            <!--div id="divGrpPolTP" align="center" style= "display:''">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageGrpPolGrid.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageGrpPolGrid.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageGrpPolGrid.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageGrpPolGrid.lastPage()">
            </div>
        </div-->
        <!-- �˱������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divEdorOperations)"></td>
                <td class="titleImg">��ȫ��Ϣ</td>
           </tr>
        </table>
        <!-- �˱�������ť���� -->
        <div id="divEdorOperations" style="display:''" class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="input">  
                        <input type="button" class="cssButton" value="��ȫӰ���ѯ" onclick="scanDetail()">
                        <!-- <input type="button" class="cssButton" value="   ��ȫ�˱�Ӱ���ѯ   " onclick="UWScanQuery()"> -->
                        <input type="button" class="cssButton" value="����˱�ԭ��" onclick="queryGrpAutoUWTrack()">
                        <!--input type="button" class="cssButton" value="��ȫ��ϸ��ѯ" onclick="edorDetailQuery()"-->
                        <!--input type="button" class="cssButton" value="     ���˺˱���Ϣ     " onclick="gotoContUW()"-->
                        <input value="������ѯ" class="cssButton" type="button" onclick="EndorseDetail()">
                        <input value="�����嵥��ѯ" class="cssButton" type="button" onclick="NamesBill()">
                        <input type="button" class="cssButton" value="��ȫ�����켣" onclick="missionQuery()">
                        <input value="�˱���ѯ  " class="cssButton" type="button" onclick="UWQuery()">
                        <br><br>
                        <!--input type="button" class="cssButton" value="   ���˱�����֪ͨ��   " onclick="noticeEdorUWResult()">
                        <input type="button" class="cssButton" value="   ���˱�Ҫ��֪ͨ��   " onclick="sendUWRequest()"-->
                        <!-- 
                        <input type="button" class="cssButton" value="  ���������֪ͨ��  " onclick="noticeHealthInspect()">
                        <input type="button" class="cssButton" value="  ����������֪ͨ��  " onclick="noticeLiveInquiry()">
												<br><br> -->
												
                        <!--input type="button" class="cssButton" value="     �������ʵ���     " onclick=""-->                      
                        <!--input type="button" class="cssButton" value=" �ѷ��ͺ˱�֪ͨ���ѯ " onclick="queryAgoNotice()">
                        <input type="button" class="cssButton" value="   Ͷ����λ�������   " onclick="grpRiskElementView()"-->
                    </td>
                </tr>
            </table>
        </div>
        <!-- �ŵ��˱��۵�չ�� -->
        <!--table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divGrpContUWInput)"></td>
                <td class="titleImg">�������ֺ˱�����</td>
            </tr>
        </table-->
        <!-- �ŵ��˱�����¼�� -->
        <!--div id="divGrpContUWInput" style="display:''">
            <table class="common">
                <tr class="common">
                    <td class="title">�˱�����</td>
                    <td class="input"><input type="text" class="codeno" name="GrpUWState" verify="���屣���˱�����|Code:gedorgrppoluw" ondblClick="showCodeList('gedorgrppoluw',[this,GrpUWStateName],[0,1],null,null,null,0,207)" onkeyup="showCodeListKey('gedorgrppoluw',[this,GrpUWStateName],[0,1],null,null,null,0,207)"><input type="text" class="codename" name="GrpUWStateName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="title">�˱����</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                <tr class="common">
                    <td class="input" colspan="6"><textarea class="common" name="GrpUWIdea" cols="108" rows="3" verify="���屣���˱����|Len<255"></textarea></td>
                </tr>
            </table>
            <!-- �ύ���ݲ�����ť -->
            <!--input type="button" class="cssButton" value=" ȷ �� " onclick="saveGrpContUW()">
            <input type="button" class="cssButton" value=" �� �� " onclick="resetGrpContUW()">
            <br><br>
        </div-->
        <!-- ����˱��۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAppUWInput)"></td>
                <td class="titleImg">��ȫ����˱�����</td>
            </tr>
        </table>
        <!-- ����˱�����¼�� -->
        <div id="divEdorAppUWInput" style="display:''" class="maxbox1">
            <table class="common">
                <tr class="common">
                    <td class="title">�˱�����</td>
                    <td class="input">
                    	<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="AppUWState" id="AppUWState" verify="��ȫ����˱�����|Code:edorgrpuwstate" 
                    	ondblclick="initEdorType(this,AppUWStateName);" onclick="initEdorType(this,AppUWStateName);" onkeyup="actionKeyUp(this,AppUWStateName);"
                    	>
                    	<input type="text" class="codename" name="AppUWStateName" id="AppUWStateName" readonly></td>
                    <td class="title"></td>
                    <td class="input"></td>
                    <td class="title"></td>
                    <td class="input"></td>
                </tr>
                <tr class="common">
                    <td class="title">�˱����</td>
                    
                </tr>
                <tr class="common">
                    <td colspan="6" style="padding-left:16px"><textarea class="common" name="AppUWIdea" cols="224" rows="4" verify="��ȫ����˱����|Len<255"></textarea></td>
                </tr>
            </table>
            <!-- �ύ���ݲ�����ť -->
            <input type="button" class="cssButton" value=" ȷ �� " onclick="saveEdorAppUW()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <input type="button" class="cssButton" value=" �·������ " onclick="showPage(this,divAppCancelInfo)">
            <input type="button" class="cssButton" value=" ���±��鿴 " onclick="showNotePad()">
            <br><br><br>
        </div>
        
         <Div  id= "divAppCancelInfo" style= "display: none" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>���������</td>
                    <td class=input><Input class="codeno" name=AskFlag id=AskFlag readonly value="1"><input class=codename name=AskFlagName id=AskFlagName readonly value="�˹��˱������"></td>
                   <td class="title"></td>
                    <td class="input"></td>
                    <td class="title"></td>
                    <td class="input"></td>
 
                </tr>
                <tr class=common>
                    <TD class=title > ��������� </TD>
                </tr>
                <tr class=common>
                    <TD colspan="6" style="padding-left:16px" ><textarea name="AskContent" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>
            	 <input type="button" class="cssButton" value=" �·� " onclick="sendAskMsg()">
             <input type="button" class="cssButton" value=" �������ѯ " onclick="queryAskMsg()"> 
             
   <table>
    	<tr>
        <td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAgentGrid);">
    		</td>
    		<td class= titleImg>
    			 ������б�
    		</td>
    	</tr>
    </table>
    <Div  id= "divAgentGrid" style= "display: 'none'">
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table align =center>
    		<tr>
    			<td>
			      <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  		 <table>
	        <TR>	        	
	         	<TD class= title>��������</TD>
	       	</TR>
	       	<TR>	        	
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="AskInfo" cols="224" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	      </table>
	      <table>
	        <TR>	        	
	         	<TD class= title>�ظ�����</TD>
	       	</TR>
	       	<TR>	        	
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="ReplyInfo" cols="224" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
	    </div>
    </DIV>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="MissionID" id=MissionID>
        <input type="hidden" name="SubMissionID" id=SubMissionID>
        <input type="hidden" name="ActivityID" id=ActivityID>
        <input type="hidden" name="ActivityStatus" id=ActivityStatus>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="EdorState" id=EdorState>
        <input type="hidden" name="PrtNo" id=PrtNo>
        <input type="hidden" name="GrpPolNo" id=GrpPolNo>
        <input type="hidden" name="ProposalContNo" id=ProposalContNo>
        <input type="hidden" name="PayToDate" id=PayToDate>
        <input type="hidden" name="UWType" id=UWType>
        <input type="hidden" name="ActionFlag" id=ActionFlag>
        <input type="hidden" name="LoginOperator" id=LoginOperator>
        <input type="hidden" name="LoginManageCom" id=LoginManageCom>
        <input type="hidden" name="ContType" value="2" id=ContType>
        <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
        <input type="hidden" name="EdorItemAppDate" id=EdorItemAppDate>
        <input type="hidden" name="EdorValiDate" id=EdorValiDate>
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
