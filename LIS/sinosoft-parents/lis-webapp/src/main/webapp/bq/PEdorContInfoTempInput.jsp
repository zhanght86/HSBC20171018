<html>
<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-12-3
 * @direction: �ͻ���Ҫ���ϱ��
 ******************************************************************************/
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>

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
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="PEdorContInfoTemp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PEdorContInfoTempInit.jsp"%>

  <title>�ͻ���Ҫ���ϱ������</title>

</head>

<body  onload="initForm();" >
  <form  method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
        <tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNotice);">
			</td>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''" class="wid maxbox1">
        <table  class= common >
            <TR  class= common>
                 <TD class=title>������</TD>
                <td class=input><Input class="common wid" name=ContNo id=ContNo></td>            	
                <td class=title>Ͷ���˺���</td>
                <td class=input><Input class="common wid" name=AppntNo id=AppntNo></td>
                <TD class=title>�����˺���</TD>
                <td class=input><Input class="common wid" name=InsuredNo id=InsuredNo></td>
            </TR>
        </table>
    </div>
        <!--<INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClickCont()">-->
        <a href="javascript:void(0);" class="button" onClick="easyQueryClickCont();">��    ѯ</a>
    <br>
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divContGrid)"></td>
                <td class="titleImg">������ı���</td>
           </tr>
        </table>
        <div id="divContGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanContGrid"></span></td>
                </tr>
            </table>
           <!-- <div id="divTurnPageContGrid" align="center" style= "display:''">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageContGrid.firstPage();HighlightAllRow();">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageContGrid.previousPage();HighlightAllRow();">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageContGrid.nextPage();HighlightAllRow();">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageContGrid.lastPage();HighlightAllRow();">
            </div>-->
        </div>
    <br>
    <input type="button" class="cssButton" value="��   ��" onclick="applyMission();">
 
 
	<div id="PEdorContInfoInputPool"></div>
   <!--<table class= common border=0 width=100%>
        <tr>
            <td class= titleImg align= center>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearchSelf" style= "display: ''">
        <table  class= common >
            <TR  class= common>
                 <TD class=title>��ȫ�����</TD>
                <td class=input><Input class="common" name=EdorAcceptNoSelf></td>               	
                 <TD class=title>������</TD>
                <td class=input><Input class="common" name=ContNoSelf></td>            	
                <TD class=title></TD>
                <td class=input></td>
            </TR>
        </table>
    </div>
        <INPUT VALUE="��   ѯ" class = cssButton TYPE=button onclick="easyQueryClickSelf()">
     <br>
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSelfGrid)"></td>
                <td class="titleImg">���ڴ���ı���</td>
           </tr>
        </table>
        <div id="divSelfGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanSelfGrid"></span></td>
                </tr>
            </table>
            <div id="divTurnPageSelfGrid" align="center" style= "display:''">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageSelfGrid.firstPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.previousPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageSelfGrid.nextPage();HighlightSelfRow();">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageSelfGrid.lastPage();HighlightSelfRow();">
            </div>
        </div>  --> 
    <br>
        <input type="button" class="cssButton" value=" ��ȫ���� " onclick="goToBusiDeal()">       
            <input type="hidden" name="EdorAcceptNo" id=EdorAcceptNo>
            <input type="hidden" name="MissionID" id=MissionID>
            <input type="hidden" name="SubMissionID" id=SubMissionID>
            <input type="hidden" name="ActivityID" id=ActivityID>
             <input type="hidden" name="OtherNo" id=OtherNo>
            <input type="hidden" name="OtherNoType" id=OtherNoType>
            <input type="hidden" name="fmtransact" id=fmtransact>
            <input type="hidden" name="LoadFlag" id=LoadFlag>
            <input type="hidden" name="EdorAppDate" id=EdorAppDate>
            <input type="hidden" name="AppType" id=AppType>
            <input type="hidden" name="ICFlag" id=ICFlag>
            <input type="hidden" name="EdorAppName" id=EdorAppName> 
  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
