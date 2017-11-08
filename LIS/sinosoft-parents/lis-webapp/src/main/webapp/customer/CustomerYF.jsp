<%
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
	String ReadType = "readonly";
	String PayRead = "readonly=true";
	try 
	{		
		String Type = request.getParameter("type");
		if (Type!=null)
		{
		 if (Type.equals("1"))
		 ReadType = "coolDatePicker";
		 PayRead = "";
		}
		loggerDebug("CustomerYF","ReadType:     "+ReadType);
	}
	catch( Exception e )
	{ 
		ReadType = null;
	}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="CustomerYF.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CustomerYFInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
<Form action="" method=post name=fm id="fm" target="fraSubmit">
<script>
	   var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
</script> 
<Div  id= "Frame1" style= "display: none">        
    <Table>
   	  <tr>
        <td class="common wid">
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame1);">    	 
          <td class= titleImg>������Ϣ</td>
   	    </td>
   	  </tr>
    </Table>
    <div class="maxbox1">
        <Table class= common border=0 >
          <TR  class= common>
            <TD  class= title>�շѻ���</TD>          
            <TD  class= input><Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true ></TD>
            <TD  class= title>����Ա����</TD>
            <TD  class= input><Input class="readonly wid" name=Operator id="Operator" readonly=true></TD>
 		        <TD class= title>��������</TD>
            <TD class= input ><Input class="coolDatePicker" onClick="laydate({elem: '#PayDate'});" verify="��������|DATE" dateFormat="short" name=PayDate id="PayDate"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD> 
          </TR>
        </Table>
      
    </div>
</Div>
 <!-- <input type =button class=cssButton value="��&nbsp;&nbsp;ѯ" onclick="queryClick();"> -->
  <Table>
    <tr>
      <td class="common wid"><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFinFeeInput);"></td>
    	<td class= titleImg>¼������շ���Ϣ</td>
    </tr>
  </Table>
 <Div id= "divFinFeeInput" style= "display: ''">
  <div class="maxbox1">
 		 <Div id= "divTempFeeClassInput" style="display:none">
		  <table  class= common border=0>
			  <tr class= common>
				  <TD  class= title5>���ѷ�ʽ</TD>
 	     	  <TD  class= input5 width="25%"><Input class=codeno name=PayMode id="PayMode" value=5><input class=codename name=PayModeName id="PayModeName" value = Ӧ��ת�� readonly=true></TD>
 	     	  <TD  class= title5></TD>
 	     	  <TD  class= input5></TD>
			  </tr>
 	      <tr class= common>
 	     	  <TD  class= title5>����ѡ��</TD>
 	     	  <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=Currency id="Currency" onclick="return showCodeList('currency',[this,CurrencyName],[0,1]);" ondblclick="return showCodeList('currency',[this,CurrencyName],[0,1]);" onkeyup="return showCodeListKey('currency',[this,CurrencyName],[0,1]);" ><input class=codename name=CurrencyName id="CurrencyName" readonly=true></TD>
 	     	  <!--TD  class= title>�����վݺ���</TD>
 	     	  <TD  class= input><Input class="common wid" name=TempFeeNo1 onblur="confirmSecondInput(this,'onblur');" ></TD>
 	     	  <TD  class= title>����������</TD>
 	     	  <TD  class= input><input class="common wid" name=PayName ></TD-->
 	      </tr>
 	 	  </Table> 
 		 </Div>
 		
 		<!------------�ڲ�ת��------------>
 		 <div id="divPayMode5" style="display:''" >
 			<Table  class= common>
 				<tr class= common>
 					<td class=title5>ʵ��֪ͨ���</td>
 					<td class=input5><Input class="common wid" name=ActuGetNo5 id="ActuGetNo5"></td>
 					<td class=title5>��ȫ�����/�ⰸ��/Ͷ����ӡˢ��</td>
 					<td class=input5><Input class="common wid" name=OtherNo5 id="OtherNo5"></td>
 					<!-- <td class=title><input class=cssButton type="button" value="��  ѯ" style="width:60" onclick="queryLJAGet();"></td>
 					<td class=input></td> -->
 				</tr>
 				<tr class= common>
 					<td class=title5>�������</td>
 					<td class=input5><Input class="readonly wid" name=PayFee5 id="PayFee5" readonly=true></td>
 					<td class=title5>��ȡ��</td>
 					<td class=input5><Input class="readonly wid" name=Drawer5 id="Drawer5" readonly=true></td>
 					<!-- <td class=title><Input class="common wid" type=hidden name=EnterAccDate></td>
 					<td class=input><Input class="common wid" type=hidden name=DrawerID></td> -->
 				</tr>
 		  </table>
 		 </div>
  </div>
  <div>
  </div>
  <br>
  <input class=cssButton type="button" value="��  ѯ" style="width:60" onclick="queryLJAGet();"> 

  <Div  id= "divFinFee1" style= "display: ''" >
    <Table  class= common>
      <TR  class= common>
        <TD text-align: left colSpan=1><span id="spanQueryLJAGetGrid" ></span> </TD>
      </TR>
    </Table>
    <div align=center style= "display: none">
    <INPUT CLASS="cssButton90" VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();">
    <INPUT CLASS="cssButton91" VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
    <INPUT CLASS="cssButton92" VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
    <INPUT CLASS="cssButton93" VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
    </div>
  </Div>
</Div>
<div>
</div>
<br>
 	  <input type =button class=cssButton value="ȷ  ��" 	onclick="addMul();"> 
 		<!--<input type =button class=cssButton	value="��  ��" 	onclick="ModMul();">-->
 		 <!--¼����ݽ��ѱ��� -->
 		<Table  class= common>
 		  <tr>
 		   	<td text-align: left colSpan=1><span id="spanFinFeeGrid" ></span></td>
 		  </tr>
 		</Table>

 <table>
   <tr>
      <td class="common wid"><IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame2);"></td>
    	<td class= titleImg>¼��ҵ�������Ϣ</td>
   </tr>
 </table>

 <Div  id= "Frame2" style= "display: ''">
 <div class="maxbox1">
    <table class= common> 
    	<TR  class= common>
    		<TD  class= title5>��������</TD>
   		  <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class=codeno name=TempFeeType id="TempFeeType" onclick="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" ondblclick="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" onkeyup="return showCodeListKey('TempFeeType',[this,TempFeeTypeName],[0,1]);"><input class=codename name=TempFeeTypeName id="TempFeeTypeName" readonly=true></TD>
   		  <TD  class= title5>�������</TD>
    	  <TD  class= input5><Input class="readonly wid" name=PolicyCom id="PolicyCom" readonly=true></TD>
      </TR>
      <TR  class= common>
   		  <TD  class= title5>ҵ�����</TD>
	  	  <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class=codeno name=OpeCurrency id="OpeCurrency" onclick="return showCodeList('currency',[this,OpeCurrencyName],[0,1]);" ondblclick="return showCodeList('currency',[this,OpeCurrencyName],[0,1]);" onkeyup="return showCodeListKey('currency',[this,OpeCurrencyName],[0,1]);"><input class=codename name=OpeCurrencyName id="OpeCurrencyName" readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font></TD>
    	</TR>
    </table>
    
    <Div id="AgentCode" style="display:">
   	<table class= common> 
   		<TR  class= common>
   			<TD  class= title5>�����˺���</TD>
   			<td class="input5" width="25%">
   				<Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class=code name=AgentCode id="AgentCode" onblur="GetManageCom();" onclick="queryAgent();"  ondblclick="queryAgent();" elementtype=nacessary ><font size=1 color='#ff0000'><b>*</b></font><!--ondblclick="queryAgent();" -->
   			</TD>
   			<TD class=title5>����������</TD>
   			<TD  class= input5><Input class="readonly wid"  tabindex=-1 name=AgentName id="AgentName" readonly=true ></TD>
      </TR>
      <TR  class= common>
   			<TD  class= title5>���������</TD>
	 			<TD  class= input5><Input class="readonly wid" readonly tabindex=-1 name=AgentGroup id="AgentGroup"></TD>
   		</TR>
   	</table>
  	</Div>
   	<Div id="AgentCode1" style="display:none">
   		<table class= common> 
   		  <TR  class= common>
   				<TD  class= title5>�����˺���</TD>
   				<td class="input5" width="25%">
   					<Input class="readonly wid" name=AgentCode1 id="AgentCode1">
   				</TD>
   				<TD class=title5>����������</TD>
   				<TD  class= input5><Input class="readonly wid"  tabindex=-1 name=AgentName1 id="AgentName1" readonly=true></TD>
        </TR>
      <TR  class= common>
   				<TD  class= title5>���������</TD>
	 		    <TD  class= input5><Input class="readonly wid" readonly tabindex=-1 name=AgentGroup1 id="AgentGroup1"></TD>
   			</TR>
   		</table>
  	</Div>
   <!-- �µ����� -->  
   <Div id="TempFeeType1" style="display:none">
   	<table class= common>
   		<TR  class= common>
	      <TD  class= title5>
	        Ͷ������
	      </TD>          
	      <TD  class= input5>
	        <Input class="common wid" name=InputNo1 id="InputNo1" onblur="confirmSecondInput(this,'onblur');" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
	      </TD>
	      <TD  class= title5>
	        Ͷ����
	      </TD>
	      <TD  class= input5>
	        <Input class="common wid" name=InputNob id="InputNob">
	      </TD>
      </TR>
      <TR  class= common>
	      <TD  class= title5>���ý��</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee1 id="InputNob" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
        </TD>
	     </TR>
	   </table> 
   </Div> 
   
   <!-- ���ڴ��ս��� --> 
   <Div id="TempFeeType2" style="display:none">
	   <table class= common> 
	     <TR  class= common>
	     	<TD  class= title5>���ý��</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee2 id="OpeFee2">
        </TD>
	      <TD  class= title5 >
	        ��������
	      </TD>        
	       <td class="input5" width="25%">
	        <Input class="common wid" name=InputNo3 id="InputNo3" onblur="getAgentCode();" >
	      </TD>  
      </TR>
      <TR  class= common>
	      <TD class=title5>
	      	<!--<Input class=cssButton type=button value="��  ѯ" onclick="queryLJSPay();">-->
	      </TD>
	      <TD  class= input5>
	      	<Input type=hidden name=GetNoticeNo id="GetNoticeNo">
	      </TD> 
	     </TR> 
	     <!--
	     	<TR>
	     	 <td class=title>
	     	    �ͻ�����
	     	 </td>
	     	 <td class=input>
	     	   <Input class="common wid" name=AppntNo readonly=true>
	     	 </td>
	     	 <td class=title>
	     	    ��������
	     	 </td>          
	     	 <td class=input>
	     	 	<Input class="common wid" name=PayCount readonly=true>
	     	 </td> 
	     		<TD class=title></TD>
	     		<TD  class= input></TD>
	     	</TR>
	   		-->
	   </table>  
   </Div>
   
   <!-- ����Ԥ�ս��� --> 
   <Div id="TempFeeType3" style="display:none">
	   <table class= common> 
	     <TR  class= common>
	     	<TD  class= title5>���ý��</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee3 id="OpeFee3">
        </TD>
	      <TD  class= title5>
	        ��������
	      </TD>          
	      <TD  class= input5>
	        <Input class="common wid" name=InputNo5 id="InputNo5" onblur="getPolicyCom();" >
	      </TD>        
	     </TR>
	   </table>  
   </Div>
	
   <!-- ��ȫ���� --> 
   <Div id="TempFeeType4" style="display:none">
   <table class= common> 
     <TR  class= common>
     	<TD  class= title5>���ý��</TD>
      <TD  class= input5>
	    	<Input class="common wid" name=OpeFee4 id="OpeFee4">
      </TD>
      <TD  class= title5>
        ��ȫ�����
      </TD>          
      <TD  class= input5>
        <Input class="common wid" name=InputNo7 id="InputNo7" onblur="getEdorCode();" >
      </TD>  
    </TR>
      <TR  class= common>      
      <TD  class= title5>
        �����վݺ���
      </TD>
      <TD  class= input5>
        <Input class="common wid" name=InputNo8 id="InputNo8" onblur="getEdorCode();">
        <!--<Input class=cssButton type=button value="��  ѯ" onclick="queryLJSPayEdor();">-->
      </TD>
     </TR>
   </table>  
   </Div>
   
   <!-- ������� -->  
   <Div id="TempFeeType5" style="display:none">
   	<table class= common> 
   	  <TR  class= common>
   	  	<TD  class= title5>���ý��</TD>
        <TD  class= input5>
	     		<Input class="common wid" name=OpeFee5 id="OpeFee5"> 
        </TD>
   	    <TD  class= title5>�����վݺ�</TD> 
   	    <TD  class= input5><Input class="common wid" name=InputNo11 id="InputNo11" onblur="getLJSPayPolicyCom();" ></TD>
      </TR>
      <TR  class= common> 
   	    <TD  class= title5>�ⰸ��</TD> 
   	    <TD  class= input5><Input class="common wid" name=InputNo12 id="InputNo12" onblur="getLJSPayPolicyCom();" ></TD>
   	  </TR>
   	</table>  
   </Div>
   
   <!-- �����ڽ��� -->
   <Div id="TempFeeType6" style="display:none">
   <table class= common> 
     <TR  class= common>
     	<TD  class= title5>���ý��</TD>
      <TD  class= input5>
	    	<Input class="common wid" name=OpeFee6> 
      </TD>
      <TD class= title5>��������</TD>
      <TD class= input5><Input class="common wid" name=InputNo22 id="InputNo22" onblur="getUrgePolicyCom();" ></TD>
     </TR>
   </table>  
   </Div>   
   
   <!-- �������д��۽��� -->  
   <Div id="TempFeeType8" style="display:none">
   	<table class= common> 
   	  <TR  class= common>
   	      <TD  class= title5>
   	        �ͻ�����
   	      </TD>          
   	      <TD  class= input5>
   	        <Input class="common wid" name=InputNo9 id="InputNo9">
   	      </TD>        
   	      <td class=title5>
   	       <!-- ��ע	-->
   	      </td>
   	      <td class=input5>
   	        <!--Input class="common wid" name=ReMark9 -->	
   	      </td>
   	  </TR>
   	</table>  
   </Div>
   
   <!-- ��ȫ������ --> 
   <Div id="TempFeeType7" style="display:none">
   	 <table class= common> 
   	   <TR  class= common>
   	     <TD class= title5>��ȫ�����</TD>
   	     <TD class= input5><Input class="common wid" name=InputNo13 id="InputNo13"></TD>
   	     <TD class= title5>�����վݺ���</TD>
   	     <TD class= input5><Input class="common wid" name=InputNo14 id="InputNo14"></TD> 
   	   </TR>
   	 </table>  
   </Div>    
   
   <!--��������ɷ�-->
   <Div id="TempFeeType9" style="display:none">
   <table class= common> 
     <TR  class= common>
       <TD  class= title5>�����</TD>          
       <TD  class= input5><Input class="common wid" name=InputNo99 id="InputNo99"></TD>        
       <TD  class= title5>��֤����</TD>
       <TD  class= input5><Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class= codeno name="CertifyFlag9" id="CertifyFlag9" CodeData="0|^1|�����վ�^2|���㵥" onclick="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])" ondblclick="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])" onkeyup="return showCodeListKeyEx('CertifyFlag', [this��ContTypeName],[0,1])"><input class=codename name=ContTypeName id="ContTypeName" readonly=true></TD>
      </TR>
      <TR  class= common>
       <TD  class= title5>��֤����</TD>
       <TD  class= input5><Input class="common wid" name=InputNo19 id="InputNo19" onblur="confirmSecondInput(this,'onblur');"></TD>
     </TR>
   </table>  
   </Div> 
   <div>
   </div>
  <br>
  <Input name="addButton" type=button class= cssButton value="��  ��" onclick="confirm1();"> 
 <!--�����ݽ��Ѻ��� type=hidden--> 
  <input  class= common  name="TempFeeNo" id="TempFeeNo" type=hidden readonly >
  
  <Div  id= "divTempFeeInput" style= "display: ''">
  <!--¼����ݽ��ѱ��� -->
  
    <Table  class= common>
      	<tr>
  				  	 <td text-align: left colSpan=1>
					 <span id="spanTempToGrid" >
					 </span> 
					</td>
       </tr>
    </Table>
 	</Div>
 </div>	
</Div>
 <table class= common> 
   <TR  class= common>
     <TD class= title5>ҵ����ϼ�</TD>
     <TD class= input5><Input class="common wid" name=OperateSum id="OperateSum" readonly=true ></TD>
     <td class=title5>�����</td>
     <td class=input5><Input class="common wid" name=OperateSub id="OperateSub" readonly=true ></td>
   </TR>
 </table>  
 </Div>
 
 <br></br>
 <input type=hidden name='OperateType' id="OperateType">
  	 <input type =button class=cssButton value="ȫ���ύ"  name="signbutton" onclick="submitForm();">
  	<input type =button class=cssButton value="����ȫ������" onclick="clearFormData();"> 
 	 <!-- <input type =button class=cssButton value="��ӡƱ��" onclick="printInvoice();">-->
	<br></br>
</form>
<br>
<br>
<br>
<br>
<!--=========================================================================-->

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>

<!--����-��Ų�ѯ���� -->
<Form action="./TempFeeTypeQuery.jsp" method=post name=fmTypeQuery id="fmTypeQuery" target="fraSubmit">
  <input type=hidden name=QueryNo id="QueryNo">
  <input type=hidden name=QueryType id="QueryType">
</Form>

</body>
</html>
