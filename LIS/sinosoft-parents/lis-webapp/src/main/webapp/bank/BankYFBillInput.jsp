<html>
<%
//�������ƣ����д��ն����嵥
//�����ܣ�
//�������ڣ�2003-3-25
//������  �������ɳ��򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.bank.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>

	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  	<SCRIPT src="BankYFBillInput.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="BankYFBillInit.jsp"%>
	</head>

	<body  onload="initForm();" >
  	<form action="./PrintBill.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- ��ʾ������LLReport1����Ϣ -->
    		
    	<Div  id= "divLLReport1" style= "display: ''">
    	<table class=common>
          <tr class= titleImg>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class= titleImg > ����ת�˴���ʧ���嵥 </TD>
          </tr>
        </table>
         <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
      	<table  class= common>
        	<TR  class= common>
          	<TD  class= title5>��ʼ����</TD>
          	<TD  class= input5>
                <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short"  name="StartDate"  id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          	</TD>
          	<TD  class= title5>��������</TD>
          	<TD  class= input5>
                <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EndDate'});"dateFormat="short"  name="EndDate"  id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          	</TD>
		      </TR>

 					<TR  class= common>	
         		<TD  class= title5>���д���</TD>
          	<TD  class= input5>
				 <Input class=codeno name=BankCode id=BankCode 
                 style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                 onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
                 onDblClick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
                  onkeyup="return showCodeListKey('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');"
                   verify="���д���|notnull&code:bank"><input class=codename name=BankName readonly=true>
          	</TD>
					<TD  class= title5> ������� </TD>
          <TD  class= input5>
	<Input class= code name=Station  id=Station 
    style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
                 onclick="return showCodeList('bank',[this,BankName],[0,1],null,'1 and comcode like #'+comCode+'%#','1');" 
    onDblClick="return showCodeList('Station',[this]);" onKeyUp="return showCodeListKey('Station',[this]);">
						</TD>
	              </tr>
				  </table>
                   </div>
                </div>	 
    <!--  <input class=cssButton type=button value="�г���ӡ����" onClick="showSerialNo()">
       <input class=cssButton type=button value="��  ӡ  ��  ��" onClick="PrintBill()">-->
      
      <a href="javascript:void(0);" class="button"onClick="showSerialNo()">�г���ӡ����</a>
      <a href="javascript:void(0);" class="button"onClick="PrintBill()">��ӡ����</a>
         
            	<input type=hidden  name="BillNo" >
            	<input type=hidden  name="selBankCode" >
     
               

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport2);">
    		</td>
    		<td class= titleImg>
    			 �嵥�б�
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLLReport2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanBillGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <Div  id= "divPage" align=center style= "display: 'none' ">
      <INPUT CLASS="cssButton90" VALUE="��ҳ" TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT CLASS="cssButton91" VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT CLASS="cssButton92" VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT CLASS="cssButton93"VALUE="βҳ" TYPE=button onClick="turnPage.lastPage();">
      </Div>
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
