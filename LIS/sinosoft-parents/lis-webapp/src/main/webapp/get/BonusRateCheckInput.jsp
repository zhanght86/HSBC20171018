<html> 
<%
//�������ƣ�
//�����ܣ��ֺ���ϵ��У�����
//�������ڣ�2008-11-09 17:55:57
//������  ������ͥ
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="BonusRateCheckInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BonusRateCheckInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./BonusRateCheckSave.jsp" method=post name=fm target="fraSubmit">
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInsuBonusRate);">
      </td>
      <td class= titleImg>
        �ֺ�ϵ��¼��
      </td>
    	</tr>
    </table>
    <Div  id= "divInsuBonusRate" style= "display: ''" class="maxbox1">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            �ɷ���ӯ��
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=DistributeValue id=DistributeValue verify="�ɷ���ӯ��|notnull" >
          </TD>
          <TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
          	<Input class="wid" class= common  name=DistributeRate id=DistributeRate  verify="�������|notnull">
          </TD>                   
        </TR>   
          <TR  class= common>
          <TD  class= title5>
            ����ϵ���� 
          </TD>
          <TD  class= input5>
          	<Input class="wid" class= common  name=BonusCoefSum id=BonusCoefSum  verify="����ϵ����|notnull">
          </TD>   
           <TD  class= title5>
            ������
          </TD>
          <TD  class= input5>
          	<Input class="wid" class= common  readonly  name=FiscalYear id=FiscalYear  verify="������ |notnull">
          </TD>                   
        </TR>              
   </table>       
    </Div>   
                 
  <Div id= "divOperate" style= "display: ''">
			<!--<INPUT class=cssButton name="addbutton" VALUE="����ȷ��"  TYPE=button onclick="return updateClick();">-->
            <a href="javascript:void(0);" name="addbutton" class="button" onClick="return updateClick();">����ȷ��</a>
			<!--<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
			<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();"> -->
  </Div>  
           
      <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLMBonusMainRate);">
            </td>
            <td class= titleImg>
                     ϵ����ϸ
       </td>
    	</tr>
     </table>
    <Div  id= "divLMBonusMainRate" style= "display: ''">
    <table  class= common>
            <tr  class= common>
                    <td text-align: left colSpan=1>
                                    <span id="spanLOBonusMainGrid" >
                                    </span>
                            </td>
                    </tr>
            </table>
    </div>

    <input type="hidden" name="transact">      
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
