<%
//Name: LLLClaimQueryInit.jsp
//Function�������ⰸ��ѯ
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
		  String tAppntNo = request.getParameter("AppntNo");	//�����˱���
		  String tClmNo = request.getParameter("ClmNo");	//����Ŀͻ���
		  String tFlag = request.getParameter("flag");	//����Ŀͻ���
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLLClaimQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLClaimQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> �����ⰸ��ѯ</td>
        </tr>
    </table>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>      
        </table>        
		<!--INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.previousPage();">                     
        <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage.lastPage();"-->
		<table style="display:none">
			<TR>
   	          <td><INPUT class=cssButton name="Casedetail" VALUE="�ⰸ�����ѯ"  TYPE=button onclick="LLLClaimQueryGridClick()"></td>
              <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
			</TR>
		</table>
    </div>
    <br>
<a href="javascript:void(0);" name="Casedetail" class="button" onClick="LLLClaimQueryGridClick();">�ⰸ�����ѯ</a>
<a href="javascript:void(0);" name="goBack" class="button" onClick="goToBack();">��    ��</a>
    <Div id= "divLLCaseMerge" style= "display:'none'">
 		<table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> �����ϲ���Ϣ</td>
        </tr>
       </table>

        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLClaimMergeGrid" ></span></td>
            </tr>      
        </table>      
		<table>
			<TR>
			  <td>
	   	          <span id="operateButton1" style= "display: ''"><INPUT class=cssButton  name="CaseMerge" VALUE="�����ϲ�"  TYPE=button onclick="LLClaimMerge()"></span>
				  <span id="operateButton2" style= "display: none"><INPUT class=cssButton name="CancleCaseMerge" VALUE="ȡ�������ϲ�"  TYPE=button onclick="LLClaimCancelMerge()"></span>
			  <td>
              <td><INPUT class=cssButton name="goBack" VALUE="��  ��"  TYPE=button onclick="goToBack()"></td>
			  <!--<td><INPUT class=cssButton name="refresh" VALUE="ˢ ��"  TYPE=button onclick="Refresh()"></td>   -->
			</TR>
		</table>  
    </Div>


    <!--������,������Ϣ��-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--�����˱���,��ǰһҳ�洫��-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--�ж��Ƿ������¼�-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--����-->
    <Input type=hidden id="State" name="State"><!--ѡ���״̬-->
    <Input type=hidden id="ContNo" name="ContNo"><!--Ͷ���˺�ͬ����-->
    <Input type=hidden id="ClmNo" name="ClmNo"> <!--����ı����ⰸ��-->
	<Input type=hidden id="ViewFlag" name="ViewFlag"> <!--��ʶ�Ƿ�����˽׶�-->
	<Input type=hidden id="AccNo" name="AccNo"> <!--����ı��ΰ������¼���-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
