<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�SpePolBankTagInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 16:25:40
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="SpePolBankTagInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SpePolBankTagInit.jsp"%>
  <%@include file="../common/jsp/ManageComLimit.jsp"%>
  <%@include file="../agent/SetBranchType.jsp"%>
<title></title>
</head>
<body  onload="initForm();" >
  <form action="./SpePolBankTagSave.jsp" method=post name=fm id="fm" target="fraSubmit">  
     <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAscription);">
    <td class=titleImg>
      Ӧ�ձ�����ѯ¼����Ϣ
    </td>
    </td>
    </tr>
    </table>
    <div class="maxbox1">
  <Div  id= "divLAAscription" style= "display: ''">      
    <table  class= common>
       <tr  class= common> 
          <td  class= title5>
            �������ֺ�
          </td>          
          <td  class= input5>
            <Input class="common wid" name=MainPolNo id="MainPolNo" onchange="return clearMulLine();">
          </td>  
          <td  class= title5>
		        ������
		      </td>
          <td  class= input5> 
            <input class="common wid" name=ContNo  id="ContNo">
		      </td>                                 
       </tr> 
       <tr  class= common> 
          <td  class= title5>
            �������
          </td>          
          <td  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code"  name=ManageCom id="ManageCom" verify="�������|code:comcode" 
            onclick="return showCodeList('comcode',[this],null,null,null,null,1);"  ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" 
            onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);">  
          </td>
          <td  class= title5>
		        �����˹���
		      </td>
          <td  class= input5>
		        <Input class="common wid" name=AgentCode  id="AgentCode">
		      </td>                                           
       </tr>
       <tr class=common>
       	 <td  class= title5>
           ����Ӧ������
          </td>
          <td  class= input5 width="25%">
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="����Ӧ������|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>  
          
          <td  class= title5>
           ����Ӧ��ֹ��
          </td>
          <td  class= input5 width="25%">
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="����Ӧ��ֹ��|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td> 
      </tr>
    </table>  
  </Div>
  </div> 
  <a href="javascript:void(0)" class=button onclick="return PolConfirm();">��  ѯ</a> 
   <!-- <input type=button class=common  value='��ѯ' onclick="return PolConfirm()">    -->
    <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLAAscription2);">
    <td class=titleImg>
      Ӧ�ձ�����Ϣ
    </td>
    </td>
    </tr>
    </table>
  <Div  id= "divLAAscription2" style= "display: ''">
    <table  class= common>
            <tr  class= common>
                    
        <td text-align: left colSpan=1> 
		  <span id="spanAscriptionGrid" >
		  </span> 
        </td>
  			</tr>
    	</table>
      <div align=center>
      <INPUT class="cssButton90" VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class="cssButton91" VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class="cssButton92" VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class="cssButton93" VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
      </div>	
    </Div>
    <a href="javascript:void(0)" class=button onclick="submitSave();">��  ��</a>
    <!-- <input type=button class=common name=saveb value='���' onclick="submitSave();">           -->
    <input type=hidden name=hideOperate id="hideOperate" value=''>
    <input type=hidden name=confirmflag id="confirmflag" value=''>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
