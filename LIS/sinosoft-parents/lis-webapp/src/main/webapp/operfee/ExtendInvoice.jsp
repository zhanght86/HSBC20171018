<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 

<head > 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="ExtendInvoice.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ExtendInvoiceInit.jsp"%>

  <title>��ӡ���ڷ�Ʊ</title>
</head>

<%
  GlobalInput tGlobalInput = new GlobalInput(); 
  tGlobalInput = (GlobalInput)session.getValue("GI");
%>

<script>
  comCode = "<%=tGlobalInput.ComCode%>";
</script>

<body  onload="initForm();" >
  <form method=post action="./ExtendInvoiceF1PSave.jsp" name=fm id=fm target="fraTitle" >
    <%@include file="../common/jsp/InputButton.jsp"%>

    <!-- ��ѯ��Ϣ���� -->
  <table class= common border=0 width=100%>
        <tr>
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
    		<td class= titleImg align= center>&nbsp;�������ѯ������</td>
 		</tr>
	</table>
    <div class=maxbox>
	<table  class= common align=center>
		<TR  class= common>
				<TD class=title>
        		��������
        </TD>
				<TD class=input>
					<input class=codeno name=ContType id=ContType CodeData="0|^01|��������^02|��������^03|��������^04|�绰ֱ������" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('SysCertCode', [this,ContTypeName],[0,1])"onkeyup="return showCodeListKeyEx('SysCertCode', [this,ContTypeName],[0,1])">
					<input class=codename name=ContTypeName id=ContTypeName readonly=true>
				</TD>
        <TD  class= title>  ��ͬ��   </TD>
        <TD  class= input>
        		<Input NAME=ContNo class="common wid" id=ContNo> 
		        <Input NAME=ContNo1 id=ContNo1 type=hidden class="common wid"> 
        </TD>
        <TD  class= title>����֪ͨ���</TD>
        <TD  class= input>
	        	<Input NAME=GetNoticeNo id=GetNoticeNo class="common wid"> 
        </TD>
    </tr>
    <tr class= common>
    		<td class=title>
              ������ʽ
        </td>
        <td>
            <input class="codeno" name="SecPayMode" id=SecPayMode  verifyorder="1" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('continuepaymode',[this,PayModeName],[0,1]);" onkeyup="return showCodeListKey('continuepaymode',[this,PayModeName],[0,1]);">
			<input class="codename" name="PayModeName"  id=PayModeName readonly="readonly" elementtype=nacessary>
        </td>
        <TD  class= title>
        			��ʼ����
        </TD>
        <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=StartDate onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            <Input NAME=senddate id=senddate type=hidden class="common wid"> 
            <Input NAME=paymoney id=paymoney type=hidden class="common wid"> 
        </TD>
        <TD  class= title>
        			��ֹ����
        </TD>
        <TD  class= input>
            <Input class="coolDatePicker" dateFormat="short" name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
		</tr>

    <TR class=common>
    		<TD class= title>
    				�������
				</TD>
				<TD class=input>
						<Input class=codeno name=ManageCom id=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('comcode',[this,ManageComName],[0,1]);">
						<input class=codename name=ManageComName id=ManageComName readonly=true elementtype=nacessary>
				</TD>
       <TD  class= title>
       		Ԥ��� 
       </TD>
       <TD  class= input>
        	<Input NAME=TempFeeNo id=TempFeeNo class="common wid">
       </TD>
				<TD class=title>
  	      	��ӡ����
        </TD>
				<TD class=input>
						<input class=codeno name=PrintType id=PrintType CodeData="0|^01|������Ʊ��ӡ^02|Ԥ�վݻ���Ʊ" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('SysCode', [this,PrintTypeName],[0,1])"onkeyup="return showCodeListKeyEx('SysCode', [this,PrintTypeName],[0,1])">
						<input class=codename name=PrintTypeName id=PrintTypeName readonly=true>
				</TD>
		</tr>

    <!--TR  class= common>
        <TD  class= title>  �ͻ��ʺ�   </TD>
        <TD  class= input> <Input NAME=AccNo class=common>
        <Input NAME=AccNo1 type=hidden class=common>
        </TD>
        <TD  class= title>  �ͻ�����  </TD>
        <TD  class= input>  <Input NAME=AccName class=common>
        <Input NAME=AccName1 type=hidden class=common>
        </TD>
      </TR-->
    <TR class = common>
    		<TD  class= title>
    				<INPUT VALUE="��  ѯ" TYPE=button class=cssButton onclick="EasyQueryClick();"><!--INPUT VALUE="��Լ��ѯ" TYPE=button class=common onclick="specEasyQueryClick();"-->
    		</TD>
    </TR>
  </table>
   </div>
 	<Div  id= "divLCCont" style= "display: ''">
    	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
		  					<span id="spanContGrid" >
  							</span>
  				  	</td>
	  			</tr>
    	</table>
			<center>
      		<INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();">
      		<INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
      		<INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
      		<INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
			</center>
  </div>
  <p>

  <table class= common border=0 width=100%>
    <tr>
		<TD  class= title5>*��Ʊ����</TD>
        <TD  class= input5><Input NAME=ChequNo id=ChequNo class="common wid"> </TD>
		<TD  class= title5></TD>
		<TD  class= input5></TD>
	</tr>
  </table> 
	    <INPUT VALUE="�� ӡ" class= cssButton TYPE=button onclick="NoPrint()"> 
	    <input name=fmtransact id=fmtransact type=hidden>
	    <input name=OtherNo type=hidden>
	    <input name=GetNoticeNoHidden type=hidden>
	    <input name=PayNo type=hidden>  
	    <input name=Managecom1 type=hidden>
	    <input name=PreSeq type=hidden>
	    <input name=type type=hidden>
	    <Input type=hidden id="CertifyCode" name="CertifyCode">
	    <div> <INPUT VALUE="" TYPE=hidden name=serialNo>
      	<INPUT VALUE="" TYPE=hidden name=GetNoticeNo1>
  		</div>
  	</form>
   <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
 
