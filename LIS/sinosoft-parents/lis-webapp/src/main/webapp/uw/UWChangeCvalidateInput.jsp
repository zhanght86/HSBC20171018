<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ProposalQueryInput.jsp
//�����ܣ��б���ѯ
//�������ڣ�2005-06-01 11:10:36
//������  ��zhangxing
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //�ͻ���
	String tContNo = request.getParameter("ContNo");
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("UWChangeCvalidateInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var tContNo = "<%=tContNo%>"; //�ͻ���
    var tQueryFlag = "<%=request.getParameter("QueryFlag")%>"; //�ͻ���
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�޸ı�����Ч����</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="UWChangeCvalidate.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  
  <%@include file="UWChangeCvalidateInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit" action="./UWChangeCvalidateChk.jsp">
<br>
    <table class="common">
      <tr class="common">
        <td class="title">
          ӡˢ��
        </td>
        <td class="input">
          <input class="common wid" name="ContNo" id="ContNo" readonly >
        </td>
        <td class="title">
          ��Ч����
        </td>
        <td class="input">
        <!--class="multiDatePicker" -->
          <input class="common" dateFormat="short" name="Cvalidate" id="Cvalidate" onClick="laydate({elem:'#Cvalidate'});"><span class="icon"><a onClick="laydate({elem: '#Cvalidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
        <TD  class= title>�Ƿ�ָ����Ч����</TD>
		<TD class= input><Input class= code name=CvalidateConfirm id="CvalidateConfirm" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" CodeData= "0|^Y|ָ��^N|��ָ��" onClick="showCodeListEx('CvalidateConfirm',[this],[0]);" ondblClick="showCodeListEx('CvalidateConfirm',[this],[0]);" onKeyUp="showCodeListKeyEx('EnteraccState',[this],[0]);">
      </tr>
    </table>
<hr class="line">
<!--���ౣ���ۼ�-->
   <input class="cssButton" value="��Ч�ڻ�����Լ" type="button" id="Button1" name="Button1" onClick="specInput();">
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
	    	</td>
	    	<td class= titleImg>
	    	 ������Ϣ���� 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divCont" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanContGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: none ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	
<hr class="line">

<!--������Ϣ-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>
	    	 ����������Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divPol" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanPolGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: none ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	
    <hr class="line">
    <div id = "divChangeResult" style = "display: 'none'">
      	  <table  class= common align=center>
          	<td height="24"  class= title>
            		��Ч�ջ�����Լ:
          	</TD>
		<tr></tr>
      		<td  class= input><textarea name="CvalidateIdea" id=CvalidateIdea cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    	 </table>
     </div>


      <input class="cssButton" value="ȷ  ��" id="sure" name=sure type="button" onClick="submitForm();">
      <!-- modified by liuyuxiao 2011-05-24 ��ȥ���أ���tab������ -->
      <input class="cssButton" value="��  ��" type="button" onClick="returnParent();" style= "display: none">
      
    </table>
    </div>
		<!--������-->
    <div id = "divHidden" style = "display:'none'" >   
    <INPUT  type= "hidden" class= Common id="InsuredNo" name= InsuredNo value= "">
    <INPUT  type= "hidden" class= Common id="ProposalContNo" name= ProposalContNo value= "">
    <INPUT  type= "hidden" class= Common id="CvalidateHide" name= CvalidateHide value= "">
    <INPUT  type= "hidden" class= Common id="Specifyvalidate" name= Specifyvalidate value= "">
    <INPUT  type= "hidden" class= Common id="Flag" name= Flag value= "1">
    <INPUT  type= "hidden" class= Common id="PolApplyDate" name= PolApplyDate value= "">
    <INPUT  type= "hidden" class= Common id="SpecCode" name= SpecCode value= "">
    <INPUT  type= "hidden" class= Common id="SpecType" name= SpecType value= "">
    <INPUT  type= "hidden" class= Common id="SpecReason" name= SpecReason value= "">
    <INPUT  type= "hidden" class= Common id="Operate" name= Operate value= "INSERT">
    <INPUT  type= "hidden" class= Common id="SerialNo" name= SerialNo value= "">
    <INPUT  type= "hidden" class= Common id="DivFlag" name= DivFlag value= "1">
  	</div>

  </form>
<br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
