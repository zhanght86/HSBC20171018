<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�QuestQuery.jsp
//�����ܣ��������ѯ
//�������ڣ�2002-09-24 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>

<script>
	var grpPolNo = "<%=tGrpPolNo%>";      //���˵��Ĳ�ѯ����.
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script>

<head >
<title>�������ѯ </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="QuestQuery.js"></SCRIPT>
  <%@include file="QuestQueryInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm target="fraSubmit" action="./QuestQueryLisSave.jsp">
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,picw);">
    		</td>
    		<td class= titleImg>
    			 �������ѯ
    		</td>
    	</tr>
    </table>
    <Div  id= "picw" style= "display: ''" class="maxbox">
    <table class= common>
     <tr class = common>
   

     	
        	
 <td class=title5>��ʼʱ��</td>
  <td class=input5>
  
  <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼʱ��|notnull&Date" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
  </td>
    <td class=title5>����ʱ��</td>
    <td class=input5>
    
    <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="����ʱ��|notnull&Date" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </td>
        </tr>
     <tr class = common>
   

     	
        	
 <td class=title5>Ԥ�տ�ʼʱ��</td>
  <td class=input5>
  
  <Input class="coolDatePicker" onClick="laydate({elem: '#PolStartDate'});" verify="��ʼʱ��|Date" dateFormat="short" name=PolStartDate id="PolStartDate"><span class="icon"><a onClick="laydate({elem: '#PolStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
  </td>
    <td class=title5>Ԥ�ս���ʱ��</td>
    <td class=input5>
    
    <Input class="coolDatePicker" onClick="laydate({elem: '#PolEndDate'});" verify="����ʱ��|Date" dateFormat="short" name=PolEndDate id="PolEndDate"><span class="icon"><a onClick="laydate({elem: '#PolEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </td>
        </tr>
    	<TR  class= common>
    	  <TD  class= title5>
          ӡˢ����
        </TD>
        <TD  class= input5>
          <Input class="wid" class=common name=PrtNo id=PrtNo>
        </TD>
        <TD  class= title5>
            ����λ��
          </TD>
          <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=OperatePos id=OperatePos  ondblclick= "showCodeList('OperatePos',[this,''],[0,1]);" onclick="showCodeList('OperatePos',[this,''],[0,1]);" onkeyup="showCodeListKey('OperatePos',[this,''],[0,1]);" >
          </TD>
      </TR>

    	<TR  class= common>
        <TD  class= title5>
          ���ض���
        </TD>
        <TD  class= input5>
        	
       <!--  <Input class=code name=BackObj CodeData="0|^1|����Ա^2|ҵ��Ա^3|����^4|����" ondblclick= "showCodeListEx('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('BackObj',[this,''],[0,1]);" >   -->  
         <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=BackObj id=BackObj ondblclick= "showCodeList('BackObj',[this,''],[0,1]);" onclick="showCodeList('BackObj',[this,''],[0,1]);" onkeyup="showCodeListKey('BackObj',[this,''],[0,1]);" >  
        </TD>
        <TD  class= title5>
          ���ض������
        </TD>
        <TD CLASS=input5>
          <Input class="wid" NAME=Operator id=Operator CLASS=common >
        </TD>
        </TR>
        <TR  class= common>
        <TD  class= title5>
          �Ƿ�ظ�
        </TD>
        <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=IfReply id=IfReply ondblclick="return showCodeList('YesNo',[this]);"     onclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">
        </TD>
        <TD CLASS=title5>
          ��������� 
        </TD>
        <TD CLASS=input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" NAME=IssueType id=IssueType CLASS=code ondblclick= "showCodeListEx('IssueType',[this],[0],null,null,null,null,1500);" onclick="showCodeListEx('IssueType',[this],[0],null,null,null,null,1500);" onkeyup="showCodeListKeyEx('IssueType',[this],[0],null,null,null,null,1500);"  >
        </TD>
      </TR>
      
      <TR>
        
        <TD CLASS=title5>
          ������� 
        </TD>
        <TD CLASS=input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" NAME=IsueManageCom id=IsueManageCom VALUE="" MAXLENGTH=10 CLASS=code ondblclick="return showCodeList('comcode',[this],null,null,'#1# and Length(trim(comcode))<=8 and comcode not like #8699%%# ','1',1);" onclick="return showCodeList('comcode',[this],null,null,'#1# and Length(trim(comcode))<=8 and comcode not like #8699%%# ','1',1);" onkeyup="return showCodeListKey('comcode',[this],null,null,'#1# and Length(trim(comcode))<=8 and comcode not like #8699%%# ','1',1);" >
        </TD>
        <TD CLASS=title5>
          �Ƿ��¼���
        </TD>
        <TD CLASS=input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" NAME=ErrorFlag id=ErrorFlag CLASS=code  CodeData="0|^Y|��^N|��"  ondblclick="return showCodeListEx('ErrorFlag',[this]);" onclick="return showCodeListEx('ErrorFlag',[this]);" onkeyup="return showCodeListKeyEx('ErrorFlag',[this]);">
        </TD>
      </TR>  
    </table>
</Div>
    <!--<input type= "button" name= "Query" value="��    ѯ" class=cssButton onClick= "query()">-->
    <a href="javascript:void(0);" name= "Query" class="button" onClick="query()">��    ѯ</a>
    <br><br>
    <div class="maxbox1">
    <table class=common>
      <TR>
        <TD CLASS=title5>
          �˱�֪ͨ����ˮ�� 
        </TD>
        <TD CLASS=input5>
          <Input class="wid" NAME=SerialNo id=SerialNo VALUE="" CLASS=common >
        </TD>
		<TD CLASS=title5></TD>
        <TD CLASS=input5></TD>
      </TR>    
    </table></div>
<!--    <input type= "button" name= "Query" value="����֪ͨ���ѯ" class=cssButton onClick= "query2()">
    <INPUT VALUE="�� �� �� ӡ" class=cssButton TYPE=button onclick="print();">-->
    <a href="javascript:void(0);" name= "Query" class="button" onClick="query2();">����֪ͨ���ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="print();">���ش�ӡ</a>
    <br><br>
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 ��������ݣ�
    		</td>
    	</tr>
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanQuestGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    	
    	<Div  id= "divPage" align=center style= "display: 'none' "align = center>
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
      </Div> 	 
         
    </div>
  
  <br> 
  <table class= common>
    <TR  class= common> 
      <TD class= title5> ��������� </TD>
      <TD colspan="3"><textarea name="Content" id="Content" cols="148" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  
  <table class= common>
    <TR  class= common> 
      <TD class= title5> ������ظ� </TD>
      <TD colspan="3"><textarea name="ReplyResult" id="ReplyResult" cols="148" rows="4" class="common" readonly></textarea></TD>
    </TR>
  </table>
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" name= "Flag" id="Flag" value="">
    <input type= "hidden" name= "ProposalNoHide" id="ProposalNoHide" value= "">
    <input type= "hidden" name= "Type" id="Type" value="">
    <input type= "hidden" name= "HideOperatePos" id="HideOperatePos" value="">
    <input type= "hidden" name= "HideQuest" id="HideQuest" value="">
    <input type= "hidden" name= "PrintType" id="PrintType" value="">
  </p>
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 
<br><br><br><br>
</body>
</html>
