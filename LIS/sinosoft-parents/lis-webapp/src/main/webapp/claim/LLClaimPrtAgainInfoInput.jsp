<%
//**************************************************************************************************
//�������ƣ�LLClaimPrtAgainInfoInput.jsp
//�����ܣ�������Ҫ��֤������Ϣ��ѯ
//�����ˣ�niuzj
//����ʱ��: 2005-11-01 
//�޸ļ�¼:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
    String tClmNo = request.getParameter("ClmNo");	//�ⰸ��
    loggerDebug("LLClaimPrtAgainInfoInput","tClmNo="+tClmNo);  
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPrtAgainInfo.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimPrtAgainInfoInit.jsp"%>
    <title> ������Ҫ��֤������Ϣ��ѯ </title>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimPrtAgainInfo);"></TD>
            <TD class= titleImg> ����֤�б� </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimPrtAgainInfo" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD style="text-align: left" colSpan=1><span id="spanLLClaimPrtAgainInfoGrid" ></span> </TD>
            </TR>
        </Table>
        <!--<Table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </Table>-->
    </Div>
    <hr class=line>
    <Table>
	       <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPrtAgainInfo);"></TD>
            <TD class= titleImg> ��֤������Ϣ </TD>
        </TR>
    </Table>
    <Div  id= "divPrtAgainInfo" class=maxbox1 style= "display: ''" align = center>
       <Table class= common>	
	       <TR class= common>
            <TD class= input>
                <textarea readonly name="PrAgInfo" id=PrAgInfo cols="100" rows="5" witdh=25% class="common"></textarea>
            </TD>
         </TR>
	      </Table>
	    </Div>
	    <hr class=line>
	    <table>
        <tr>
        	<!--<td><input value="��  ѯ" name=return class= cssButton type=button onclick="QueryInfo();"></td>-->
	        <td><input value="��  ��" name=return class= cssButton type=button onclick="returnParent();"></td>
        </tr>
    </table>
    
    <%
    //�������������ر���
    %>
    <input class=common name=ClmNo type=hidden >
    <!--�������ش��ݵ�ֵ��û�õ�-->
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
	  <input type=hidden id="MissionID" 	 name= "MissionID">
	  <input type=hidden id="SubMissionID" name= "SubMissionID">
	  <input type=hidden id="ActivityID" name= "ActivityID">	
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
