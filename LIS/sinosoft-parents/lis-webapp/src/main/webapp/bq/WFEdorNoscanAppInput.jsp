<html>
<%
//�������ƣ�WFEdorNoscanAppInput.jsp
//�����ܣ���ȫ������-��ȫ��ɨ������
//�������ڣ�2005-04-27 15:13:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>


<%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //��¼����Ա
    var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����
    var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="WFEdorNoscanApp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="WFEdorNoscanAppInit.jsp"%>

  <title>��ȫ��ɨ������</title>
  

</head>
		<%
		    GlobalInput tGlobalInput = new GlobalInput(); 
		    tGlobalInput = (GlobalInput)session.getValue("GI");
	    %>
	 <script>	
		var operator = "<%=tGlobalInput.Operator%>";   //��¼����Ա
		var manageCom = "<%=tGlobalInput.ManageCom%>"; //��¼��½����
		var comcode = "<%=tGlobalInput.ComCode%>"; //��¼��½����
	</script>
<body  onload="initForm();" >
  <form action="./WFEdorNoscanAppSave.jsp" method=post name="fm" id="fm" target="fraSubmit">
<!-- 
    <table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    
   
    <Div  id= "divSearch" style= "display: ''">
        <table  class= common >
            <TR  class= common>
                <td class=title>��ȫ�����</td>
                <td class=input><Input class="common" name=EdorAcceptNo_ser></td>
                <TD class=title>��������</TD>
                <td class=input><Input class="codeno" name=OtherNoType ondblclick="showCodeList('edornotype',[this,OtherNoName],[0,1])" onkeyup="showCodeListKey('edornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName readonly></td>
                <TD class=title>�ͻ�/������</TD>
                <td class=input><Input class="common" name=OtherNo></td>
            </TR>
            <TR  class= common>
                <td class=title>������</td>
                <td class=input><Input type="input" class="common" name=EdorAppName></td>
                <td class=title>���뷽ʽ</td>
                <td class=input><Input class="codeno" name=AppType ondblclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" onkeyup="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName readonly></td>
                <TD class=title>¼������</TD>
                <TD class=input><Input class="multiDatePicker" name=MakeDate ></TD>
            </TR>
            <TR  class= common>
                <TD class=title>�������</TD>
                <TD class=input><Input class="codeno" name=ManageCom_ser ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly></TD>
                <TD class=title>  </TD>
                <TD class=input>  </TD>
                <TD class=title>  </TD>
                <TD class=input>  </TD>
            </TR>
        </table>
    </div>
        <INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClickSelf();">
        <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="applyMission();">
    <br>
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSelfGrid)">
                </td>
                <td class="titleImg">�ҵ�����</td>
           </tr>
        </table>
        
        <div id="divSelfGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanSelfGrid"></span></td>
                </tr>
            </table>
            <div id="divTurnPageSelfGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageSelfGrid.firstPage();HighlightByRow()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.previousPage();HighlightByRow()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.nextPage();HighlightByRow()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageSelfGrid.lastPage();HighlightByRow()">
            </div>
        </div>
         -->
    <div id="NoScanAppInputPool"></div>
    <br>
        <!--<input type="button" class="cssButton" value=" ��ȫ���� " onclick="goToBusiDeal()">
        <input type="button" class="cssButton" value="���±��鿴" onclick="showNotePad()">-->
        <a href="javascript:void(0);" class="button" onClick="goToBusiDeal();">��ȫ����</a>
        <a href="javascript:void(0);" class="button" onClick="showNotePad();">���±��鿴</a>

        <!-- �����򲿷� -->
            <input type="hidden" name="EdorAcceptNo" id="EdorAcceptNo">
            <input type="hidden" name="MissionID" id="MissionID">
            <input type="hidden" name="SubMissionID" id="SubMissionID">
            <input type="hidden" name="ActivityID" id="ActivityID">
            <input type="hidden" name="ICFlag" id="ICFlag">
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br><br><br><br>
</body>
</html>
