<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<%
//�������ƣ�NBCList.jsp
//�����ܣ�
//�������ڣ�2003-05-13 15:39:06
//������  ��zy
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="NBCList.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="NBCListInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr> 
    		<td class=common>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
    		</td>
    		<td class=titleImg>
        	�����ѯ��ʱ�䷶Χ
       	</td>   		 
    	</tr>
    </table>
    <Div  id= "divFCDay" class=maxbox style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class="title5"> ��ʼʱ�� </TD>
          <TD  class="input5"> <Input class= "coolDatePicker" dateFormat="short" name=StartDay verify="��ʼʱ��|NOTNULL" onClick="laydate({elem: '#StartDay'});" id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class="title5"> ����ʱ�� </TD>
          <TD  class="input5"> <Input class= "coolDatePicker" dateFormat="short" name=EndDay  verify="����ʱ��|NOTNULL" onClick="laydate({elem: '#EndDay'});" id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>  
        </TR>
        <tr>
           <TD  class="title5"> �������� </TD>
           <TD  class="input5"><Input class="code" name=SaleChnl id=SaleChnl style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SaleChnl',[this]);"  onkeyup="return showCodeListKey('SaleChnl',[this]);"></TD>
           <TD  class="title5"> ������� </TD>
           <TD  class="input5"> <Input class="code" name=ManageCom id=ManageCom style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="�������|code:comcode" ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);"> </TD>
        </tr>        
        <tr>
           <TR  class= common>      
           <td  class="title5">
           	 ��Ʒ����	
           </td>
           <td  class="input5"> 
             	<input name=RiskCode id=RiskCode class="code" verify="��Ʒ����|code:Riskcode"  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
		         ondblclick="return showCodeList('riskcode',[this]);" 
		         onkeyup="return showCodeListKey('riskcode',[this]);">
		       </td>          
        </tr>    
        </table>
    </Div>
    <table>
    	<tr> 
    		<td class=common>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOperator1);">
    		</td>
    		<td class=titleImg>
          ����Լ����ͳ��
       	</td>   		 
    	</tr>
    </table>
    <Div  id= "divOperator1" class=maxbox1 style= "display: ''">    
    <table  class= common>
		<TR class= common>
		  	<TD class="title5">����Լ������</TD>
			<TD  class=input5> 
				<Input class= 'readonly wid' readonly name=polamount id=polamount >
				<input class="cssButton" type=Button  value="����Լ����ͳ��" onclick="polcount()">
			</TD>
			 <TD  class= title5></TD>	
			 <TD  class= input5></TD>	
		</TR>
    </table>
    </Div>
      
    <table>
    	<tr> 
    		<td class=common>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOperator2);">
    		</td>
    		<td class=titleImg>
          ����Լ�ݷÿͻ���ϸ��
       	</td>   		 
    	</tr>
    </table>
    <Div  id= "divOperator2" class=maxbox1 style= "display: ''">    
    <table  class= common>
    <TR class= common> 
		  <TD  class="input5" width="15%">
		   	<input class=cssButton type=Button  value="�����ӡ" onclick="printList()">
		  </TD>
		</TR>    	 
      </table>
      </Div>
    
       <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
