<html> 
<%
//�������ƣ�NormPayGrpChooseInput.jsp
//�����ܣ�����ѡ��������

//�������ڣ�2002-10-08    
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
    GlobalInput tG = (GlobalInput)session.getValue("GI");
    String Branch =tG.ComCode;
    String strCurTime = PubFun.getCurrentDate();   
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  

  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./NormPayGrpChooseInput.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="NormPayGrpChooseInit.jsp"%>
</head>
<body  onload="initForm();" >					                                                                                  
<form action="./NormPayGrpChooseSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    
    <div class="maxbox">
    <Div  id= "divNormPayGrpChoose" style= "display: ''">
      <table  class= common >                   
        <TR class= common>                      
          <TD  class= title5>
            ��Ӧ�����
          </TD>                   
          <TD  class= input5>
            <Input class="common wid" name=SumDuePayMoney id="SumDuePayMoney" readonly tabindex=-1 >
          </TD>           
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PaytoDate id="PaytoDate" readonly tabindex=-1 >
          </TD>                                                                
        </TR>
        <TR class= common>                            
          <TD class=title5>
	     ���屣������
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=GrpPolNo id="GrpPolNo" verify="���屣������|NOTNULL">
	  </TD>   
          <TD  class= title5>
            ��������
          </TD>
         <TD  class= input5>
            <input class="common wid" name="PayDate" id="PayDate" verify="��������|DATE&NOTNULL">                       
          </TD>                                                                             
        </TR>   
        <TR class= common>                            
          <TD class=title5>
	     ����ѱ���
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=ManageFeeRate id="ManageFeeRate" verify="����ѱ���|NUM">
	  </TD>                                                                               
      <TD class=title5>
	     Ͷ����λ
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=GrpName id="GrpName">
	  </TD> 
        </TR>
       <TR class= common> 
       	<TD class=title5>
	     ��������
	  </TD>
	  <TD class= input5>
	      <input class="common wid" name=PolNo1 id="PolNo1" verify="��������">
	  </TD>                                    
       </TR>
      </table>
    </Div>
    </div>  
    <a href="javascript:void(0)" class=button onclick="queryRecord();">��  ѯ</a>
    <a href="javascript:void(0)" class=button onclick=onclick="window.open('./GrpChooseFeeList.html');">��ѯ�ѽ������岻���ڱ���</a>
        <!-- <INPUT class= cssButton VALUE="��    ѯ"  TYPE=button onclick="queryRecord();">        
          
        <INPUT VALUE="��ѯ�ѽ������岻���ڱ���" TYPE=button class=cssButton onclick="window.open('./GrpChooseFeeList.html');">    -->      
         
      <!--<INPUT VALUE="����" TYPE=button onclick="returnParent();">--> 					
            
    <table>
      <tr class="common">
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNormGrid1);">
        </td>
    	  <td class= titleImg>
    	    �����еĸ�����Ϣ
          </td>
    	</tr>
    </table>
  <Div  id= "divNormGrid1" style= "display: ''" align=center>
     <table  class= common >
        <tr>
    	  	<td text-align: left colSpan=1 >
	         <span id="spanNormPayGrpChooseGrid" ></span> 
		      </td>
	      </tr>
      </table>
     <INPUT VALUE="��  ҳ"   class= cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
     <INPUT VALUE="��һҳ"   class= cssButton91 TYPE=button onclick="turnPage.previousPage();"> 			
     <INPUT VALUE="��һҳ"   class= cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
     <INPUT VALUE="β  ҳ"   class= cssButton93 TYPE=button onclick="turnPage.lastPage();">	
  </Div>
  <div>
    <a href="javascript:void(0)" class=button onclick="submitCurData();">����ҳ����ѡ�и�������</a>
    <a href="javascript:void(0)" class=button onclick="verifyChooseRecord();">ѡ��������</a>
  </div>
  <!-- <INPUT class= cssButton VALUE="����ҳ����ѡ�и�������" TYPE=button onclick="submitCurData();">  
  <INPUT class= cssButton VALUE="ѡ��������" TYPE=button onclick="verifyChooseRecord();">   --> 
         <!--���صĺ��룬���ڱ��漯�屣���������ύ-->      
         <Input type=hidden name=PolNo id="PolNo"> 
         <input type=hidden name=GrpContNo id="GrpContNo">
  </form>
  
    <Form name=fmFileImport id="fmFileImport" action="./FileImportNormPayCollSaveAll.jsp" method=post target="fraSubmit" ENCTYPE="multipart/form-data">         
      <table>
      <tr>
        <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay1);">
        </td>
    	  <td class= titleImg>
    	   �����ļ�����
          </td>
    	</tr>
    </table> 
    <div class="maxbox1">
    <div  id= "divFCDay1" style= "display: ''">
     <table class= common>
	  <TR  class= common>
	    	<TD class=title5>
	     ���屣������
	  	</TD>
	  	<TD class= input5>
	     		 <input class="common wid" name=GrpPolNo1 id="GrpPolNo1">
	 	 </TD>
     <td class=title5></td>       
     <td class= input5></td>    	 
	   </TR>     
	</table>
</div>
</div> 
<div>
  <a href="javascript:void(0)" class=button onclick="submitCurDataAll();">�������������и�������</a>
  <a href="javascript:void(0)" class=button onclick="ToExcel();">����ģ��</a>
</div>
<br>
	<!-- <INPUT class= cssButton VALUE="�������������и�������" type=button onclick ="submitCurDataAll();">
	<Input type=button class=cssButton value="����ģ��" onclick = "ToExcel();"> -->
<!--	<a href="./download.jsp">�������</a>-->

	<div class="maxbox1">
	<table class=common>
	<tr class="common">
		<TD class = title width=20%>
	        	�ļ���ַ
	     	 </TD>
	     	 <TD class = common width=80%>
	        	<Input type="file" name=FileName id="FileName">
	     	 </TD>
	</tr>
	</table>
  </div>
  <a href="javascript:void(0)" class=button onclick="fileImport();">�����ļ�����</a>
	<!-- <INPUT VALUE="�����ļ�����" class= cssButton TYPE=button onclick = "fileImport();">  -->
	<input type=hidden name=ImportFile id="ImportFile">
	<input type=hidden name=ImportConfigFile id="ImportConfigFile">
</Form>	
  <Form name=fmToExcel id="fmToExcel" action="./NormPayToExcel.jsp" method=post target="fraSubmit">   
 <!--        <Input type=button class=common value="����ģ��" onclick = "ToExcel();">      -->
  </Form> 

  <Form name=fmSaveAll id="fmSaveAll" action="./NormPayCollSaveAll.jsp" method=post target="fraSubmit">   
         <Input type=hidden name=SubmitGrpPolNo id="SubmitGrpPolNo">      
       <!--<Input type=button class=common value="�������������и�������" onclick = "submitCurDataAll();"> -->    
  </Form> 
  <Form name=fmSubmitAll id="fmSubmitAll" action="./NormPayGrpChooseSubmitAll.jsp" method=post target="fraSubmit">   
         <Input type=hidden name=SubmitGrpPolNo id="SubmitGrpPolNo"> 
         <Input type=hidden name=SubmitPayDate id="SubmitPayDate"> 
         <Input type=hidden name=SubmitManageFeeRate id="SubmitManageFeeRate"> 
         <Input type=hidden name=SubmitGrpContNo id="SubmitGrpContNo"> 
  </Form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanNormPayGrp"  style="display:''; position:absolute; slategray"></span>  
</body>
</html>
