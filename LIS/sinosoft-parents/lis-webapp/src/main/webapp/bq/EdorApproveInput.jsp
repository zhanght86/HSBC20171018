<html>
<%
//�������ƣ�EdorApproveInput.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 15:20:22
//������  ��sinosoft
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<head >
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====BGN===================================
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //��ȫ�����
    String tMissionID = request.getParameter("MissionID");          //����ID
    String tSubMissionID = request.getParameter("SubMissionID");    //������ID
    String tEdorPopedom = request.getParameter("EdorPopedom");       //���Ȩ��
    String tActivityID      = request.getParameter("ActivityID"); 
    tActivityID=tActivityID.replace(" ", ""); 
//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====END===================================

%>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="./EdorApprove.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

<%@include file="EdorApproveInit.jsp"%>

</head>
<body  onload="initForm();" >
  <form action="./EdorApproveSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class="common">
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
            </td>
            <td class= titleImg>��ȫ������Ϣ</td>
        </tr>
    </table>
    <Div  id= "divEdorAppInfo" style= "display: ''" class=" maxbox">
        <TABLE class=common>
        <!-- ��ʾ��ȫ����Ļ�����Ϣ,���������� �Թ��ο� -->
            <tr class=common>
                <td class=title> ��ȫ����� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                <td class=title> ������� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=OtherNo id=OtherNo></td>
                <td class=title> �������� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=OtherNoTypeName id=OtherNoTypeName></td>
            </tr>
            <tr class=common>
                <td class=title> ������ </td>
                <td class= input><input type="input" class="readonly wid" readonly name=EdorAppName id=EdorAppName></td>
                <td class=title> ���뷽ʽ </td>
                <td class= input><input type="input" class="readonly wid" readonly name=ApptypeName id=ApptypeName></td>
                <td class=title> ������� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=ManageComName id=ManageComName></td>
            </tr>
            <tr class=common>
                <td class=title> ��/�˷ѽ��ϼ� </td>
                <td class= input><input type="input" class="readonly wid" readonly name=GetMoney id=GetMoney></td>
                <td class=title> ��/�˷���Ϣ </td>
                <td class= input><input type="input" class="readonly wid" readonly name=GetInterest id=GetInterest></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </TABLE>
   </Div>

    <Div  id= "divEdorMainInfo" style= "display: none">
        <table>
            <tr>
                <td class="common">
                    <IMG src="../common/images/butExpand.gif" style="cursor:hand;" onClick="showPage(this,divEdorMainGrid);">
                </td>
                <td class= titleImg> ��ȫ����������Ϣ </td>
            </tr>
        </table>
        <div id="divEdorMainGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorMainGrid"></span></td>
                </tr>
            </table>
           <!-- <div id="divTurnPageEdorMainGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPage1.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage1.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage1.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPage1.lastPage()">
            </div>-->
        </div>
    </DIV>

    <Div  id= "divEdorItemInfo" style= "display: none">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                </td>
                <td class= titleImg> ��ȫ������Ŀ��Ϣ </td>
            </tr>
        </table>
        <Div  id= "divEdorItemGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td><span id="spanEdorItemGrid"></span></td>
                </tr>
            </table>
            <!--<div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPage2.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage2.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPage2.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPage2.lastPage()">
            </div>-->
        </div>
    </DIV>

    <br>
    <INPUT VALUE=" ɨ�����ϸ " class=cssButton TYPE=button onclick="ScanDetail();">
        <INPUT VALUE="  ������ϸ  " class=cssButton TYPE=button onclick="EndorseDetail();">
        <INPUT VALUE="  ���������ѯ  " class=cssButton TYPE=hidden onclick="UWQuery();">
        <INPUT VALUE="��ȫ��ϸ��ѯ" class=cssButton TYPE=button onclick="EdorDetailQuery();">
        <INPUT VALUE="��ȫ�����켣" class=cssButton TYPE=button onclick="MissionQuery();">
  <!--      <Div  id= "divGetNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" ����֪ͨ�� " class=cssButton TYPE=button onclick="GetNotice();">
        </Div>
   -->     
        <Div  id= "divPayNotice" style= "display: none">
            <br>
            <INPUT VALUE=" ��ӡ�ܾ�֪ͨ�� " class=cssButton TYPE=button onclick="InvaliNotice();">
        </Div>
   <!--         
        <Div  id= "divPayPassNotice" style= "display: 'none'">
            <br>
            <INPUT VALUE=" ��ӡ����֪ͨ�� " class=cssButton TYPE=button onclick="InvaliPassNotice();">
        </Div>  
   -->   
    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg>��ȫ����</td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>���˽���</td>
                    <td class=input><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ApproveFlag id=ApproveFlag verify="���˽���|NotNull&Code:EdorApproveIdea" ondblclick="return showCodeList('edorapproveidea',[this,edorapproveideaName],[0,1]);" onclick="return showCodeList('edorapproveidea',[this,edorapproveideaName],[0,1]);" onkeyup="return showCodeListKey('edorapproveidea',[this,edorapproveideaName],[0,1]);"><input class=codename name=edorapproveideaName id=edorapproveideaName readonly></td>
                    <!-- XinYQ added on 2005-11-28 : �����޸�ԭ�� : BGN -->
                    <td class="title"><Div id="divApproveMofiyReasonTitle" style="display:none"> �޸�ԭ�� </Div></td>
                    <td class="input"><Div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" name="ModifyReason" verify="�޸�ԭ��|Code:EdorApproveReason" ondblclick="return showCodeList('EdorApproveReason',[this,ModifyReasonName],[0,1])" onkeyup="return showCodeListKey('EdorApproveReason',[this,ModifyReasonName],[0,1])"><input class="CodeName" name="ModifyReasonName" readonly></Div></td>
                    <td class="title"><Div id="divApproveErrorChk" style="display:none"><input name="ErrorChk" type="checkbox" onclick="setErrorFlag()" >�Ƿ�Ϊ����</Div></td>
                    <td class="input">&nbsp;</td>
                    <!-- XinYQ added on 2005-11-28 : �����޸�ԭ�� : END -->
                    <td class=title></td><td class=input></td> <td class=title></td><td class=input></td> 
                </tr></table><table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > ������� </TD>
                </tr>
                <tr class=common>
                    <TD  colspan="6" style="padding-left:16px" ><textarea name="ApproveContent" id="ApproveContent" cols="224%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>
        </DIV>
    </DIV>

            <input type="button" class="cssButton" value=" ȷ �� " onClick="ApproveSubmit()">
            <input type="button" class="cssButton" value=" �� �� " onClick="ApproveCancel()">
            <input type="button" class="cssButton" value=" �·������ " onclick="showPage(this,divAskModelInfo)">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
     <Div id= "divAskModelInfo" style= "display: none" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>���������</td>
                    <td class=input><Input class="codeno" name=AskFlag readonly value="3"><input class=codename name=AskFlagName readonly value="��ȫ���������"></td>
                    <td class="title">���Ͷ���ϵͳ������</td>
                    <td class="input"><input class=codeno name=SysOperator readonly><input class=codename name=SysOperatorName readonly></td>
 <td class=title></td><td class=input></td>
                </tr>
                <tr class=common>
                    <TD class=title colspan=6 > ��������� </TD>
                </tr>
                <tr class=common>
                    <TD colspan="6" style="padding-left:16px" ><textarea name="AskContent" id="AskContent" cols="226%" rows="4" witdh=100% class="common"></textarea></TD>
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
    <Div  id= "divAgentGrid" style= "display: none" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAgentGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
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
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="AskInfo" cols="226" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	      </table>
	      <table>
	        <TR>	        	
	         	<TD class= title>�ظ�����</TD>
	       	</TR>
	       	<TR>	        	
	         <TD colspan="6" style="padding-left:16px"> <textarea readonly=true name="ReplyInfo" cols="226" rows="4" witdh=25% class="common"></textarea></TD>
	       	</TR>
	     </table>
	    </div>
    </DIV>
    <!-- ������-->
    <input type="hidden" name="MissionID" id=MissionID>
    <input type="hidden" name="SubMissionID" id=SubMissionID>
    <input type="hidden" name="EdorState" id=>
    <input type="hidden" name="EdorMainState" id=EdorState>
    <input type="hidden" name="EdorItemState" id=EdorItemState>
    <input type="hidden" name="CancelType" id=CancelType>
    <input type="hidden" name="EdorNo" id=EdorNo>
    <input type="hidden" name="EdorType" id=EdorType>
    <input type="hidden" name="ContNo" id=ContNo>
    <input type="hidden" name="InsuredNo" id=InsuredNo>
    <input type="hidden" name="PolNo" id=PolNo>
    <input type="hidden" name="ActivityID" id=ActivityID>
    <input type="hidden" name="MakeDate" id=MakeDate>
    <input type="hidden" name="MakeTime" id=MakeTime>
    <input type="hidden" name="ActionFlag" id=ActionFlag>
    <input type="hidden" name="OtherNoType" id=OtherNoType>
    <input type="hidden" name="Apptype" id=Apptype>
    <input type="hidden" name="ManageCom" id=ManageCom>
    <input type="hidden" name="AppntName" id=AppntName>
    <input type="hidden" name="PaytoDate" id=PaytoDate>
    <input type="hidden" name="PrtSeq" id=PrtSeq>
    <input type="hidden" name="EdorItemAppDate" id=EdorItemAppDate>
    <input type="hidden" name="EdorAppDate" id=EdorAppDate>
    <input type="hidden" name="EdorValiDate" id=EdorValiDate>
    <input type="hidden" name="AppObj" value="I" id=AppObj>
    <input type="hidden" name="ErrorChkFlag" value="N" id=ErrorChkFlag>
    <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
	<input type="hidden" name="operator" value="" id=operator>
	<input type="hidden" name="EdorPopedom" id=EdorPopedom>
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>
</html>
