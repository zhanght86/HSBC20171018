<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�EsDocManage.jsp
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
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
  <SCRIPT src="EsDocManage.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EsDocManageInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./EsDocManageSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    <%//@include file="../common/jsp/InputButton.jsp"%>
    
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
    		</td>
    		 <td class= titleImg>
        		 ɨ�赥֤��Ϣ
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "divCode1" style= "display: ''" class="maxbox">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>
            ��֤�ڲ���
          </TD>
          <TD  class=input>
            <Input class="wid" class= common name=ScanNo id=ScanNo readonly value="" type="hidden">
            <Input class="wid" class= common name=DOC_ID id=DOC_ID >
          </TD>
          <TD  class= title>
            ӡˢ����
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=DOC_CODE id=DOC_CODE >
          </TD>
          <TD  class= title>
            ҳ��
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=NUM_PAGES id=NUM_PAGES readonly>
          </TD></TR>
          <TR  class= common>
          
          <TD  class= title>
            ɨ������
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=INPUT_DATE id=INPUT_DATE readonly>
          </TD>
          <TD  class= title>
            ɨ��Ա
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=ScanOperator id=ScanOperator readonly>
          </TD>
          <TD  class= title>
            ɨ�����
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=ManageCom id=ManageCom readonly>
          </TD>
        </TR>
        <!-- 
        <TR  class= common>
          <TD  class= title>
            ��֤����
          </TD>
          <TD  class= input>
            <Input class= common name=BussType >
          </TD>          
        </TR>
         -->
        
        <TR  class= common>          
          <TD  class= title>
            ��֤״̬
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=DOC_FLAGE id=DOC_FLAGE readonly>
          </TD>
          <TD  class= title>
            ��֤��ע
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=DOC_REMARK id=DOC_REMARK readonly>
          </TD>
          <TD  class= title>
            ¼��Ա
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=Operator id=Operator readonly>
          </TD>
          <!--
          <TD  class= title>
            ״̬_EX
          </TD>
          <TD  class= input>
            <Input class= common name=Doc_Ex_Flag >
          </TD>
          -->
        </TR>
        <TR  class= common>
          
          <TD  class= title>
            ¼����ʼ����
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=InputStartDate id=InputStartDate readonly>
          </TD>
          <TD  class= title>
            ¼����ʼʱ��
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=InputStartTime id=InputStartTime readonly>
          </TD>
          <TD  class= title>
            ¼��״̬
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=InputState id=InputState readonly>
          </TD>
          </TR>
         
         
        <TR  class= common>
          
          <TD  class= title>
            ¼����������
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=InputEndDate id=InputEndDate readonly>
          </TD>
          <TD  class= title>
            ¼������ʱ��
          </TD>
          <TD  class= input>
            <Input class="wid" class= common name=InputEndTime id=InputEndTime readonly>
          </TD> <TD class= title> ��֤ɾ��ԭ�� </TD>	 
				      <TD class= input> <Input class="code" name=DelReasonCode ondblclick="return showCodeList('esdocdelreason',[this,DelReason],[0,1]);" onkeyup="return showCodeListKey('esdocdelreason',[this,DelReason],[0,1]);"></TD>
        </TR>
        <!-- 
        <TR  class= common>
          <td class=title>
           ��֤���� 
         </td>
	    	<td class=input>
         <Input class="codeno" name=SubType ondblclick="return showCodeList('imagetype',[this,SubTypeName],[0,1]);" onkeyup="return showCodeListKey('Imagetype',[this,SubTypeName],[0,1]);" ><Input class="codename" name=SubTypeName readonly elementtype=nacessary>
         </td>
        </TR>
         -->
      <!--</table>-->
      <!--Input class=code name=ComCode ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);" >
      <!--Input class= common name=ComCode -->
	      <!--<Div  id= "divReason" style= "display: ''">
		      <table class = common border=0>-->
			    
			  <!--</table>
			  <table class = common border=0>-->
				    <TR  class= common>				      
				      <TD style="padding-left:16px" colspan="6"><textarea name="DelReason" cols="196" rows="4" class="common"></textarea></TD>
				    </TR>
		    </table>  
	    </Div>
    </Div>
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden name="BussType" value="">
    <!-- ES_DOC_PAGES������ MultiLine -->
    <%@include file="../common/jsp/OperateButton.jsp"%>
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
      	<INPUT VALUE="��  ҳ" TYPE=Button class=cssButton90 onclick="getFirstPage();"> 
      	<INPUT VALUE="��һҳ" TYPE=Button class=cssButton91 onclick="getPreviousPage();"> 					
      	<INPUT VALUE="��һҳ" TYPE=Button class=cssButton92 onclick="getNextPage();"> 
      	<INPUT VALUE="β  ҳ" TYPE=Button class=cssButton93 onclick="getLastPage();"> 	</center>				
  	</div>
    <INPUT VALUE="�޸�ҳ��" TYPE=Button class=cssButton onclick="saveUpdate()"> 	
    <INPUT VALUE="ɾ��ѡ��ҳ" TYPE=Button class=cssButton onclick="deleteChecked()">
    <INPUT VALUE="�޸ĵ�֤����" TYPE=Button class=cssButton onclick="updateClick1()">
    <INPUT VALUE="ɾ����֤" TYPE=Button class=cssButton onclick="deleteClick1()">
    <!--<a href="javascript:void(0);" class="button" onClick="saveUpdate();">�޸�ҳ��</a>
    <a href="javascript:void(0);" class="button" onClick="deleteChecked();">ɾ��ѡ��ҳ</a>
    <a href="javascript:void(0);" class="button" onClick="updateClick1();">�޸ĵ�֤����</a>
    <a href="javascript:void(0);" class="button" onClick="deleteClick1();">ɾ����֤</a><br><br><br>-->
    
    <div class="maxbox1">
	<table class=common>
		<tr class="common">
			<td class=title5>
			��֤���룺
			</td>
            <TD  class= input5>
            <Input class="wid" class= common name=serialno id=serialno readonly>
          </TD>
          <td class=title5></td>
          <TD  class= input5></TD>
		</tr>
		<tr>
			<td style="padding-left:16px" colspan="4" class="titleImg">
			<font color="red">
			��ʾ�������֤����Ϊ������ظ��������¼�뵥֤����
			</font>
			</td>
		</tr>
	</table></div>
    <Input class= common type=hidden name=PrtNo >
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
