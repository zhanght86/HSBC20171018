<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String bussType = request.getParameter("busstype");
	loggerDebug("EsDocManageNew","url�Ĳ���Ϊ:" + bussType);
	String bussStr = "";
	if("".equals(bussType) || bussType == null) bussType = "LP";
	if("TB".equals(bussType)) bussStr = "�б�";
	if("BQ".equals(bussType)) bussStr = "��ȫ";
	if("OF".equals(bussType)) bussStr = "����";
	if("LP".equals(bussType)) bussStr = "����";
	
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String ComCode = tG1.ComCode;
	String Operator = tG1.Operator; //����Ա
%>
<html> 
<%
//�������ƣ�EsDocManage.jsp
//�����ܣ�
//�������ڣ�2009-09-09
//������  yanglh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="EsDocManageNew.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EsDocManageNewInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./EsDocManageNewSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
    		</td>
    		 <td class= titleImg>
        		 <%=bussStr%>ɨ�赥֤��Ϣ
       		 </td>   		 
    	</tr>
    	<tr>
    		  	
    	</tr>
    </table>
    <Div  id= "divCode1" style= "display: ''" class="maxbox1">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            ��֤����
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=DOC_CODE id=DOC_CODE >
             <input class= common readonly type=hidden name=busstype value=<%=bussType%>>
             <Input class= common  type=hidden name=PrtNo id="PrtNo" >
             <Input class= common  type=hidden name=DOC_ID id=DOC_ID >
             <Input class= common  type=hidden name=NUM_PAGES id="NUM_PAGES" >
             <Input class= common  type=hidden name=MngCom id="MngCom" value=<%=ComCode%> ></TD>
             <TD  class= title5></TD>
             <TD  class= input5></TD>
             </TR>
       <!--</table>-->
      <!--Input class=code name=ComCode ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);" >
      <!--Input class= common name=ComCode -->
	      <!--<Div  id= "divReason" style= "display: 'none'">
		      <table class = common border=0>-->
			    <TR style="display:none" class=common >
			       
				      <TD class= title5> ��֤ɾ��ԭ�� </TD>
				    </TR>
				    <TR style="display:none"  class= common>
				      <TD style="padding-left:16px" colspan="4"><textarea name="DelReason" cols="146" rows="4" class="common"></textarea></TD>
				    </TR>
		    </table>  
	    </Div>
    </Div>
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden name="BussType" value="">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <!-- ES_DOC_PAGES������ MultiLine -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ɨ�����֤ҳ��Ϣ
    		</td>
    	</tr>
    </table>
    
   
    
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
      	<INPUT VALUE="��  ҳ" TYPE=Button class=cssButton90 onclick="turnPage1.firstPage();"> 
      	<INPUT VALUE="��һҳ" TYPE=Button class=cssButton91 onclick="turnPage1.previousPage();"> 					
      	<INPUT VALUE="��һҳ" TYPE=Button class=cssButton92 onclick="turnPage1.nextPage();"> 
      	<INPUT VALUE="β  ҳ" TYPE=Button class=cssButton93 onclick="turnPage1.lastPage();"> 	</center>				
  	</div>
<!-- <INPUT VALUE="�޸�ҳ��" TYPE=Button class=cssButton onclick="saveUpdate()"> 	
    <INPUT VALUE="ɾ��ѡ��ҳ" TYPE=Button class=cssButton onclick="deleteChecked()">
    <INPUT VALUE="�޸ĵ�֤����" TYPE=Button class=cssButton onclick="updateClick1()">
    <INPUT VALUE="ɾ����֤" TYPE=Button class=cssButton onclick="deleteClick1()">--><br>
    <a href="javascript:void(0);" class="button" onClick="saveUpdate();">�޸�ҳ��</a>
    <a href="javascript:void(0);" class="button" onClick="deleteChecked();">ɾ��ѡ��ҳ</a>
    <a href="javascript:void(0);" class="button" onClick="updateClick1();">�޸ĵ�֤����</a>
    <a href="javascript:void(0);" class="button" onClick="deleteClick1();">ɾ����֤</a><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
