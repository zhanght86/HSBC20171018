<%
//Name: LLLcContQueryInit.jsp
//Function��������ѯҳ��
//Date��2005.06.21
//Author ��quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN�����ղ���
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");	  

	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLLcContQueryClmScan.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLcContQueryClmScanInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
        <tr>
			<td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
            <td class= titleImg align= center>������ѯ �� �������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" class=maxbox1 style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title5> �ͻ��� </TD>
                <TD class= input5> <Input class="common wid" name=Customerno id=Customerno elementtype=nacessary></TD>
				<TD class= title5>  </TD>
                <TD class= input5>  </TD>
            </TR>
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">
    <p>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td style="text-align: left" colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>      
        </table>        
        <Div  id= "divLcContState" style= "display: 'none'">
            <table>
                <tr>
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divState);"></td>
                    <td class= titleImg> ����״̬��Ϣ </td>
                </tr>
            </table>
            <div id= "divState" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td style="text-align: left" colSpan=1><span id="spanLcContStateGrid" ></span></td>
                    </tr>      
                </table>    
            </div>
        </div>
    </div>
    <hr class=line>
    <table>
        <tr>
            <td><input class=cssButton  type=button value="������ϸ" onclick="queryClick()"></td>
            <td><input class=cssButton  type=button value="��  ��" onclick="top.close()"></td>
        </tr>      
    </table>   
    
    <!--������,������Ϣ��-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--Ͷ���˿ͻ�����,��ǰһҳ�洫��-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--�ж��Ƿ������¼�-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--����-->
    <Input type=hidden id="State" name="State"><!--ѡ���״̬-->
    <Input type=hidden id="ContNo" name="ContNo"><!--Ͷ���˺�ͬ����-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
