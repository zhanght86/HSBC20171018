<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-07-24 08:38:43
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="PayPlanInput.js"></SCRIPT>
  <!--<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>-->
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PayPlanInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form action="./PayPlanSave.jsp" method=post name=fm id=fm target="fraSubmit">
          <!-- ��ʾ������PayPlan1����Ϣ -->
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);">
      </td>
      <td class= titleImg>
        �߸��ƻ�������������
      </td>
    	</tr>
    </table>
	
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    
      <table  class= common>
        <TR class=common>
        <TD class=title5> ҵ�����</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="wid"  class= code name=ManageCom value="" ondblclick="showCodeList('station',[this]);" 
            onkeyup="return showCodeListKey('station',[this]);" onMouseDown="showCodeList('station',[this]);">
 
        </TD>
        <!--<TD class=title> ʱ�䷶Χ</TD>
        <td class="input" width="25%"><input class="coolDatePicker" dateFormat="short" id="timeStart" name="timeStart" verify="��ʼ����|NOTNULL&DATE" ></td>
        -->
        <TD class=title5> �߸�������</TD>
        		<td class="input5" width="25%"><!--<input class="coolDatePicker"
			dateFormat="short" id="timeEnd" name="timeEnd"
			verify="�߸�������|NOTNULL&DATE">-->
            <Input class="coolDatePicker" class="laydate-icon" onClick="laydate({elem: '#timeEnd'});" 	verify="�߸�������|NOTNULL&DATE" dateFormat="short" name=timeEnd 	id="timeEnd"><span class="icon"><a onClick="laydate({elem: 	'#timeEnd'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
            </td>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ���˺�ͬ����
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ContNo id=ContNo >
          </TD>
          <TD  class= title5>
            �����˿ͻ�����
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=InsuredNo id=InsuredNo >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ���屣������
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=GrpContNo id=GrpContNo >
          </TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
          </TR>
          
      </table> 
      </div>
      <!--<Input class= cssButton type=Button value="���ɴ߸�" onclick="submitForm()">-->
      <a href="javascript:void(0);" class="button" onClick="submitForm();">���ɴ߸�</a>
   <Div id = "divBTquery" style = "display :none">
             <INPUT class= cssButton VALUE="��ѯ" TYPE=button width=10% OnClick="easyQueryClick();">
             </Div>
      <!--������ȡ���б� -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
            </td>
            <td class= titleImg>
                     Ӧ�����һ��
            </td>
    	</tr>
     </table>
    <Div  id= "divLCInsured2" style= "display:none" >
    <table  class= common>
            <tr  class= common>
                    <td text-align: left colSpan=1>
                                    <span id="spanGetGrid" >
                                    </span>
                            </td>
                    </tr>
            </table>
      <center>
      				<INPUT VALUE="��  ҳ" class="cssButton90" type="button" onclick="turnPage.firstPage();"> 
				    	<INPUT VALUE="��һҳ" class="cssButton91" type="button" onclick="turnPage.previousPage();"> 					
				    	<INPUT VALUE="��һҳ" class="cssButton92" type="button" onclick="turnPage.nextPage();"> 
				    	<INPUT VALUE="β  ҳ" class="cssButton93" type="button" onclick="turnPage.lastPage();"></center>
    </div>
   
    <table class = common>
    <tr class = common>
    
    	<td class = input width="2%">
    		������&nbsp<Input class= readonly readonly name=getCount >&nbsp����¼��
    	</td>
     	<input type=hidden id="SerialNo" name="SerialNo" id="SerialNo">
     </table>
      
    <!-- ��ʾ������LJSGet1����Ϣ -->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
