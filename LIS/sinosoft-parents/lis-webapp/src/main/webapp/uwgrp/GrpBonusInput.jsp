<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String AppntNo = request.getParameter("AppntNo");
String LoadFlag = request.getParameter("LoadFlag");
loggerDebug("GrpBonusInput",AppntNo);
%>
<script>
LoadFlag="<%=LoadFlag%>"; //����������AppntNo 
GrpContNo="<%=GrpContNo%>";

</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="GrpBonusInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpBonusInit.jsp"%>
  <title>��������</title>
</head>
<body  onload="initForm();">
  <form action="./GrpBonusSave.jsp" method=post name=fm target="fraSubmit" >
    <!-- ������Ϣ���� -->
  <table class= common border=0 width=100%>
    <tr>
			<td class= titleImg align= center>
				�������ѯ������
			</td>
		</tr>
	</table>
	
    <table  class= common align=center>
      	<TR  class= common>
 
          <TD  class= title>���ֱ���</TD>
          <TD class=input>
					<Input class=codeno name=QueryRiskCode ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName readonly=true>
				</TD>   
          <td><INPUT VALUE="��  ѯ" class = button TYPE=button onclick="easyQueryClick();"> </td>
    </table>
  	<Div  id= "divMain1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanBonusGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
 <center>   
    	<input type=hidden name=Transact >
      <INPUT VALUE="��ҳ" class= button TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class= button TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class= button TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class= button TYPE=button onclick="turnPage.lastPage();">		
</center>  		
  	</div>
   	<table class= common align=center>
    		<tr class= common>
    			 <TD  class= title>�ŵ���ͬ��</TD>
          <TD  class= input> 
          <Input class="readonly" readonly name=GrpContNo value="<%=GrpContNo%>">
							
          </TD>
    		  <TD  class= title>�ͻ�����</TD>
    			<td  class= input>
    				<Input class="common"  name=AppntNo value="<%=LoadFlag%>">
    			</td>
    			<TD  class= title>�������</TD>
    			<td  class= input>
    				<Input class= "common" name=PayMoney >     			
    			</td>

<!--    			 <TD  class= title>�ͻ���ʶ</TD>
    			<td  class= input>
    			<Input class="code" name=Degree CodeData="0|^0|���ʿͻ�^1|��ͨ�ͻ�^2|�������ͻ�" ondblclick="showCodeListEx('Degree',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('Degree',[this,''],[0,1]);">  
    			</td>  --> 			
    		</tr>   	
    		<tr class= common>
    			<TD  class= title>���ִ���</TD>
    			<TD class=input>
					<Input class=codeno name=RiskCode ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName,GrpPolNo],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName readonly=true>
				<font size=1 color='#ff0000'><b>*</b></font>
				</TD>
   			<TD  class= title>�ŵ����ֺ�</TD>
    			<TD class=input>
					<input class="readonly" readonly name="GrpPolNo">
				</tD> 		
    		        
    			<TD  class= title>�����ڼ�</TD>
    			<td  class= input>
    				<Input class= common name=InsuYear ><font size=1 color='#ff0000'><b>*</b></font>    			
    			</td>
    			
    		</tr>      
    		<tr class= common>
           <TD  class= title>�ڼ䵥λ</TD>
    			<td  class= input>
    				 <Input class="code" name=InsuYearFlag CodeData="0|^0|��^1|��^2|��" ondblclick="showCodeListEx('GrpRateType',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('GrpRateType',[this,''],[0,1]);">  
    			
    			</td>
    		        <TD  class= title>�ر���ʽ</TD>
    			<td  class= input>
    			 <Input class="code" name=RewardType CodeData="0|^0|�̶�����" ondblclick="showCodeListEx('RewardType',[this,''],[0]);" onkeyup="showCodeListKeyEx('RewardType',[this,''],[0]);">  
    			   			
    			</td>


 <!--  
       <div  id= "divExplain" style= "display: 'none'">
       	<TD  class= title>��ŵ�ر�����</TD> 
       </div>
       <div  id= "divExplain2" style= "display: 'none'">
       	<TD  class= title>��ŵ�ر�����</TD> 
       </div>  -->
       <TD  class= title>�ر�����/����</TD> 
    		<td  class= input>
    				  <Input class="common" name=RewardValue><font color=red>������ֻ¼�ֺ����ʣ�����������2.5%</font>   			      
    			</td>
    		</tr>		        
    		<tr class= common>

    		        <TD  class= title>��ͬ��ʼ��</TD>
    			<td  class= input>
    			<Input class= "coolDatePicker" dateFormat="short" name=StartDate > 
    			 	  			
    			</td>
    			<TD  class= title>��ͬ��ֹ��</TD>
    			<td  class= input>
    				<Input class= "coolDatePicker" dateFormat="short" name=EndDate >     			
    			</td>

    		</tr>    			
	  	</table>     
 	<table>	  
    		<tr class= common>
    			<TD  class= title>
            ˵��
          </TD>
          <tr></tr>
          <TD  class= input> <textarea name="Note" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
   			<td>
    				 <INPUT class= code name=GrpPolNo1 type=hidden> 
    		</td>
    		</tr>
    		
    		 <tr>
    		 	<td>
  				 <INPUT class= button VALUE="�ֺ����" TYPE=button onclick="save();">
    				<INPUT class= button VALUE="�ֺ��޸�" TYPE=button onclick="Mod();">  
    				<input type=hidden name=frmAction>  			
    			</td>
    		 	</tr>
  	
    	</table> 	
    	
    	<!--����ֺ��˱�-->
  <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson2);">
    		</TD>
    		<TD class= titleImg>
    			 ����ֺ��˱�����
    		</TD>
    	</TR>
    </Table>  
    <table class= common border=0 width=100%>
    <tr>
			<td class= titleImg align= center>
				�������ѯ������
			</td>
		</tr>
	 </table>
	
    <table  class= common align=center>
      	<TR  class= common>
 
          <TD  class= title>���ֱ���</TD>
          <TD class=input>
					<Input class=codeno name=QueryRiskCode2 ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName],[0,1,2],null,fm.GrpContNo.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName readonly=true>
				</TD>   
          <td><INPUT VALUE="��  ѯ" class = button TYPE=button onclick="easyQueryClick2();"> </td>
    </table>
    	  	
 <Div  id= "divLDPerson2" style= "display: ''">
   <Table  class= common>
       <TR  class= common>
        <TD text-align: left colSpan=1>
            <span id="spanCanclePolGrid" ></span> 
  	</TD>
      </TR>
    </Table>
<center>    
   <table> 
    <tr>		
    <td>			
      <INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="getFirstPage();"> 
    </td>
    <td>
      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="getPreviousPage();"> 					
    </td>
    <td>  
      <INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="getNextPage();"> 
    </td>
    <td>  
      <INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="getLastPage();"> 
    </td>
    </tr> 
    
   </table> 
</center>    
 </Div>		

  	<table class= common align=center>
    		<tr class= common>
    			 <TD  class= title>�ŵ���ͬ��</TD>
          <TD  class= input> 
          <Input class="readonly" readonly name=GrpContNo2 value="<%=GrpContNo%>">
							
          </TD>
    		  <TD  class= title>�ͻ�����</TD>
    			<td  class= input>
    				<Input class="common"  name=AppntNo2 value="<%=LoadFlag%>">
    			</td>
    			<TD  class= title>���ִ���</TD>
    			<TD class=input>
					<Input class=codeno name=RiskCode2 ondblclick="return showCodeList('GrpBonusRisk',[this,RiskCodeName,GrpPolNo2],[0,1,2],null,fm.GrpContNo2.value,'b.GrpContNo','1');" onkeyup="return showCodeListKey('GrpBonusRisk',[this,RiskCodeName,GrpPolNo2],[0,1,2],null,fm.GrpContNo2.value,'b.GrpContNo','1');"><input class=codename name=RiskCodeName2 readonly=true>
				  <font size=1 color='#ff0000'><b>*</b></font>
				  </TD>
    		</tr>	    			
    		<tr class= common> 			
   			<TD  class= title>�ŵ����ֺ�</TD>
    			<TD class=input>
					<input class="readonly" readonly name="GrpPolNo2">
				</tD> 
				<TD  class= title>�ʻ���������</TD>
    			<td  class= input>
    			 <Input class="code" name=AccountPassYear CodeData="0|^0|����TС��1��^1|����T�����1��С��2��^2|����T�����2��С��3��^3|����T�����3��" ondblclick="showCodeListEx('AccountPassYear',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('AccountPassYear',[this,''],[0,1]);">    			   			
    			<font size=1 color='#ff0000'><b>*</b></font>
    			</td>		
    		        
    			<TD  class= title>ռ�˻�������</TD>
    			<td  class= input>
    				  <Input class="common" name=AccountValueRate>   			      
    			</td>  	
    		</tr>      					
	  	</table>  
	  	<br>   
 	<table>	    		
    		 <tr>
    		 	<td >
    			<INPUT class= button VALUE="���" TYPE=button onclick="canclePol();">
    			</td>
    			<td > 
   				<INPUT class= button VALUE="�޸�" TYPE=button onclick="canclePolMod();">
   				</td> 
   				<td >
   				<INPUT class= button VALUE="ɾ��" TYPE=button onclick="canclePolDel();">       						
    			</td>
    		 	</tr>
    		 		      
    	</table> 	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
