<%@include file="../i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="LDRiskToRateTableInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LDRiskToRateTableInit.jsp"%>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<body  onload="initForm();"  >
<form action="LDRiskToRateTableSave.jsp" enctype="multipart/form-data" method="post" name=fm target="fraSubmit">	
<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQuery);">
    		</TD>
    		<TD class= titleImg>
�������ѯ����
    		</TD>
    	</TR>
  </Table>
   <Div  id= "divQuery" style= "display: ''" >
    <table  class= common >
    <TR  class= common>
    	 <TD  class= title>
��Ʒ����
         </TD>
         <TD  class= input>
	        <Input class="codeno" name=RiskCode 
	        ondblclick="return showCodeList('RiskCodeShow',[this,RiskCodeName],[0,1]);" 
	        onkeyup="return showCodeListKey('RiskCodeShow',[this,RiskCodeName],[0,1]);"><input 
	        class="codename" name="RiskCodeName" readonly="readonly" > </TD>
         <TD  class= title>  
��������
         </TD>
         <TD  class= input>
           <Input class="codeno" name=RateType 
	        ondblclick="return showCodeList('RateTypeShow',[this,RateTypeName],[0,1]);" 
	        onkeyup="return showCodeListKey('RateTypeShow',[this,RateTypeName],[0,1]);"><input 
	        class="codename" name="RateTypeName" readonly="readonly" >
         </TD> 
		</TR>
 </Table>
  <Table>
  	<TR>   
       <td width="10%">&nbsp;&nbsp;</td>        
       	<TD> 
       		<INPUT VALUE="��  ѯ" class= cssbutton TYPE=button onclick="easyQueryClick();">
       	 </TD> 
       	<td width="10%">&nbsp;&nbsp;</td>        
       	<TD> <INPUT VALUE="�� ��" class= cssbutton TYPE=button onclick="resetForm();"> </TD>      
       	<td width="10%">&nbsp;&nbsp;</td>           
       	  
       </TR>
    </Table>
    </div>
  	<Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCollectivityGrid);">
    		</TD>
    		<TD class= titleImg>
��Ʒ������Ϣ��
    		</TD>
    	</TR>
  </Table> 
 <Div  id= "divCollectivityGrid" style= "display: ''" alight="center">
   <Table  class= common>
       <TR  class= common>
        <td style="text-align:left;" colSpan=1>
            <span id="spanCollectivityGrid" ></span> 
  		</TD>
       </TR>
    </Table>
    <Table  class= common>	
    	<TR  class= common>
    	<TD>
			<INPUT VALUE="������������" class=cssButton TYPE=button onclick="download();"> 
        </TD>
      </TR>
	 </Table>	
<Table class= common>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,biangengyuanyinluru);">
    		</TD>
    		<TD class= titleImg>
����¼��
    		</TD>
    	</TR>
</Table>
        <input type=hidden name=Transact >
        <input type=hidden name=State >
<table  class= common >
		 <tr class="common">
				<td class="title">���ԭ��¼��:</td>
			</tr>
			<tr class="common">
				<td class=input5 colSpan=4><textarea name="Remark" cols="135"
					rows="4" witdh=25% class="common"></textarea></td>
			</tr>
</Table>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>
<form action="LDRiskToRateTableInputSave.jsp" enctype="multipart/form-data" method="post" name=fmm target="fraSubmit">	
<table  class= common >
		    <TR  class= common>
		         <TD  class= title>  
���ݵ���
		      		 <input class=common type="file" name=FileName size=14 style="display :''" >
		           <INPUT VALUE="ȫ������" name="ImportExcel1"  class= cssbutton TYPE=button onclick="fullInputClick();">
		           <INPUT VALUE="��������" name="ImportExcel2" class= cssbutton TYPE=button onclick="incrementalClick();">
		    	</TD>
			</TR>
 </Table>
</form>
 </Div>		
<div align=right>
 </div> 

</body>

</html>
