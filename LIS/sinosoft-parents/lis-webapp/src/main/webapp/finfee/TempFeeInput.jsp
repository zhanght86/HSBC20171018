<%
//�������ƣ�TempFeeInput.jsp
//�����ܣ������շѵ�����
//�������ڣ�2002-07-12 
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   
<SCRIPT src="TempFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<%@include file="TempFeeInit.jsp"%>
</head>
<body  onload="initForm();" >
<Form action="./TempFeeQuery.jsp" method=post name=fm id="fm" target="fraSubmit">
<!--���һ��������Ϣ --> 
     <script>
	   var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
     </script> 
     <div align="right" style="margin-top:5px">
     <input type =button class=cssButton value="��ѯ�ݽ���" onClick="queryTempfee();">
     </div> 
     <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame1);">    	 
          <td class= titleImg>������Ϣ
           
          </td>
          <td class=common></td>
            	  
    	</tr>
    </Table>
  <Div  id= "Frame1" class="maxbox1" style= "display:  ">          
     <Table class= common border=0>
       <TR  class= common> 
          <TD  class= title>
            ҵ��Ա
          </TD>
         <td class="input" width="25%">
         <Input class=code name=AgentCode id="AgentCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="queryAgent()" onDblClick="queryAgent()" onBlur="GetManageCom();">
         </TD> 
            
          <TD class=title>
          	ҵ��Ա����
          </TD>          
          <TD  class= input>
            <Input class="readonly wid"  tabindex=-1 name=AgentName id="AgentName" readonly=true >
          </TD>          
         <TD class= title> 
            ��������
          </TD>
          <TD class= input >
            <Input class="readonly wid" verify="��������|DATE&NOTNULL" name=PayDate id="PayDate" onClick="laydate({elem:'#PayDate'});"><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>                                        
       </TR>         
       <TR  class= common>
          <TD  class= title>
             �շѻ���
          </TD>          
          <TD  class= input>
							<Input class="readonly wid" name=ManageCom id="ManageCom" readonly=true >				 
				  </TD>             
          <TD  class= title>
             �������
          </TD>          
          <TD  class= input>
							<Input class="readonly wid" name=PolicyCom id="PolicyCom" readonly=true >				 
				  </TD>         
          <TD  class= title>
            ���������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly tabindex=-1 name=AgentGroup id="AgentGroup" >
          </TD>
          
      </TR>           	                 	 
     </TABLE>
 </Div>
 
<!--���һ  -->     

<!--��ܶ���ѡ���ݽ������Ͳ����� --> 
     <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Frame2);">    	 
          <td class= titleImg>
           ¼���ݽ���
          </td>    	 
    	</tr>
    </Table>
 
    <Div  id= "Frame2" class="maxbox1" style= "display:  ">
     <table class= common> 
       <TR  class= common>
          <TD  class= title>
            �ݽ�������
          </TD>          
          <TD  class= input>
            <Input class=codeno name=TempFeeType id="TempFeeType" style="background: url(../common/images/select--bg_03.png) no-repeat center right;" onMouseDown="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" onDblClick="return showCodeList('TempFeeType',[this,TempFeeTypeName],[0,1]);" onKeyUp="return showCodeListKey('TempFeeType',[this,TempFeeTypeName],[0,1]);"><input class=codename name=TempFeeTypeName readonly=true>
          </TD>        
          <td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
      </TR> 
     </table>
  </div>  <!-- maxbox  -->  
    <!-- �µ����� -->  
    <Div id="TempFeeType1" style="display:none">
    <table class= common> 
      <TR  class= common>        
          <TD  class= title>
            ��֤����
          </TD>
          <TD  class= input>
            <Input class= codeno name=CertifyFlag id="CertifyFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('certifyflag',[this,CertifyFlagName],[0,1]);" onDblClick="return showCodeList('certifyflag',[this,CertifyFlagName],[0,1]);" onKeyUp="return showCodeListKey('certifyflag',[this,CertifyFlagName],[0,1]);"><input class=codename name=CertifyFlagName readonly=true>
          </TD> 
          <TD class= title></TD>
          <TD class= input></TD>
          <TD class= title></TD>
          <TD class= input></TD>                            
      </TR>                   
    </table>  
          <Div id="divCertifyFlag1" style="display:none">
      	<table class= common>
		      <TR  class= common> 
				      <TD  class= title>
		            Ͷ����ӡˢ��
		          </TD>          
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNo1 id="InputNo1" >
		          </TD>
		      	  <TD  class= title>
		            ���շ��վݺ�
		          </TD>
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNo2 id="InputNo2" >
		          </TD> 
                  <TD class= title></TD>
                  <TD class= input></TD>
			  	</TR>
	      </table>
	    </Div>
		  <Div id="divCertifyFlag2" style="display:none">
		  	<table class= common>
			  	<TR  class= common> 
			  		  <TD  class= title>
		            Ͷ����ӡˢ��
		          </TD>          
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNoB1 id="InputNoB1" >
		          </TD>
		      	  <TD  class= title>
		            ���л���Э�����
		          </TD>
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNoB id="InputNoB" >
		          </TD> 
                   <TD class= title></TD>
                   <TD class= input></TD>
			  	</TR>
			  </table>
		  </Div>
		  <Div id="divCertifyFlag3" style="display:none">
		  	<table class= common>
			  	<TR  class= common> 
		          <TD  class= title>
		            ��֤���� 
		          </TD>          
		          <TD  class= input>
		            <Input class=codeno name=CardCode id="CardCode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="showCodeList('CardCode',[this,CardCodeName],[0,1]);" onDblClick="showCodeList('CardCode',[this,CardCodeName],[0,1]);" onKeyUp="return showCodeListKey('CardCode',[this,CardCodeName],[0,1]);" ><Input class="codename" name=CardCodeName readonly=true>
		          </TD>        
		          <TD  class= title>
		            ��֤����
		          </TD>
		          <TD  class= input>
		            <Input class="coolConfirmBox wid" name=InputNoC id="InputNoC" >
		          </TD> 
                  <TD class= title></TD>
                  <TD class= input></TD>
		  	</TR>
		  </table>
		  </Div>
    </Div>

    <!-- ���ڴ��ս��� --> 
    <Div id="TempFeeType2" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title >
            ������ͬ����
          </TD>        
           <td class="input">
          <Input class="common wid" name=InputNo3 id=""InputNo3 onBlur="getAgentCode();">
          </td>
           <td class=title>
             ����֪ͨ�����
          </td>
          <td class=input>
            <Input class="common wid" name=InputNo4 >
            <Input class=cssButton type=button value="��  ѯ" onClick="queryLJSPay();">
          </TD>  
          <TD class= title></TD>
          <TD class= input></TD>
      </TR>          
      
      <TR>
          <td class=title>
             �������շ��վݺ�
          </td>          
          <td class=input>
          	<Input class="common wid"  name=XQCardNo id="XQCardNo" >
          </td> 
          <TD class= title></TD>
          <TD class= input></TD>
          <TD class= title></TD>
          <TD class= input></TD>
      
      </TR>
    
    </table>  
    </Div>
    
        <!-- Ԥ�ձ��� --> 
    <Div id="TempFeeType3" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            ������ͬ����
          </TD>          
          <TD  class= input>
            <Input class="coolConfirmBox wid" name=InputNo13 id="InputNo13" >
          </TD>        
          <TD  class= title>
            Ԥ�����վݺ�
          </TD>
          <TD  class= input>
            <Input class="coolConfirmBox wid" name=InputNo14 id="InputNo14" >
          </TD> 
          <td class="title"></td>
          <td class="input"></td>         
      </TR>
    </table>  
    </Div>       
    <!-- ��ȫ���� --> 
    <Div id="TempFeeType4" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            ��ȫ�����
          </TD>          
          <TD  class= input>
            <Input class="common wid" name=InputNo7 id="InputNo7" >
          </TD>        
          <TD  class= title>
            �����վݺ���
          </TD>
          <TD  class= input>
            <Input class="common wid" name=InputNo8 id="InputNo8" >
          </TD>  
          <td class="title"></td>
          <td class="input">
          <Input class=cssButton type=button value="��  ѯ" onClick="queryLJSPayEdor();"></td>         
      </TR>
    </table>  
    </Div>
    </Div>
    <Div id="TempFeeType5" style="display:none">
     </Div>
    
    <!-- �����շѣ���Ϊ����ӷѺ�����ع� -->  
    <Div id="TempFeeType6" style="display:none">
    <table class= common> 
      <TR  class= common>        
          <TD  class= title>
            ��֤����
          </TD>
          <TD  class= input>
            <Input class= codeno name=ClaimFeeType id="ClaimFeeType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|����ӷ�|^2|����ع�"  onMouseDown="return showCodeListEx('ClaimFeeType',[this,ClaimFeeTypeName],[0,1]);" onDblClick="return showCodeListEx('ClaimFeeType',[this,ClaimFeeTypeName],[0,1]);" onKeyUp="return showCodeListKeyEx('ClaimFeeType',[this,ClaimFeeTypeName],[0,1]);"><input class=codename name=ClaimFeeTypeName readonly=true>
          </TD> 
          <TD class= title></TD>
          <TD class= input></TD>
          <TD class= title></TD>
          <TD class= input></TD>                            
      </TR>                   
    </table>  
      <Div id="divClaimFeeType1" style="display:none">
      	<table class= common>
		      <TR  class= common> 
	          <TD  class= title>
	            �����վݺ�
	          </TD>          
	          <TD  class= input>
	            <Input class="common wid" name=InputNo11 id="InputNo11" onBlur="getLJSPayPolicyCom();" >
	          </TD>        
	          <TD  class= title>
	            �ⰸ�� 
	          </TD>
	          <TD  class= input>
	            <Input class="common wid" name=InputNo12 id="InputNo12" onBlur="getLJSPayPolicyCom();">
                <Input class=cssButton type=button value="��  ѯ" onClick="queryLJSPayClaim();">
	          </TD>
              <TD class= title></TD>
          <TD class= input></TD>  
			  	</TR>
	      </table>
	    </Div>
		  <Div id="divClaimFeeType2" style="display:none">
		  	<table class= common>
			  	<TR  class= common> 
	          <TD  class= title>
	            �����վݺ�
	          </TD>          
	          <TD  class= input>
	            <Input class="common wid" name=InputNoH11 id="InputNoH11" onBlur="getLJSPayPolicyCom();" >
	          </TD>        
	          <TD  class= title>
	            �ⰸ�� 
	          </TD>
	          <TD  class= input>
	            <Input class="common wid" name=InputNoH12 onBlur="getLJSPayPolicyCom();">
                <Input class=cssButton type=button value="��  ѯ" onClick="queryLJSPayClaim();">
	          </TD> 
              <TD class= title></TD>
          <TD class= input></TD> 
			  	</TR>
			  </table>
		  </Div>
    </Div>
    <Div id="TempFeeType7" style="display:none">
    </Div>
        <!-- ���ڷǴ��ս��� --> 
    <Div id="TempFeeType8" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            ������ͬ����
          </TD>          
          <TD  class= input>
            <Input class="common wid" name=InputNo5 id="InputNo5" onBlur="getPolicyCom();">
          </TD>        
          <td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>         
      </TR>
    </table>  
    </Div>   
    <!--��������ɷ�-->
    <Div id="TempFeeType9" style="display:none">
    <table class= common> 
      <TR  class= common>
          <TD  class= title>
            �����
          </TD>          
          <TD  class= input>
            <Input class="common wid" name=InputNo99 id="InputNo99" >
          </TD>        
          <TD  class= title>
            ��֤����
          </TD>
          <TD  class= input>
            <Input class= codeno name=CertifyFlag9 id="CertifyFlag9" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " CodeData="0|^1|�����վ�^2|���㵥" onMouseDown="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])" onDblClick="return showCodeListEx('CertifyFlag', [this,ContTypeName],[0,1])"onkeyup="return showCodeListKeyEx('CertifyFlag', [this��ContTypeName],[0,1])"><input class=codename name=ContTypeName readonly=true>
          </TD>          
          <TD  class= title>
            ��֤����
          </TD>
          <TD  class= input>
            <Input class="coolConfirmBox wid" name=InputNo19 id="InputNo19" >
          </TD>        
      </TR>
    </table>  
    </Div> 
       <Input type=button class= cssButton value="ȷ  ��" onClick="confirm1();" name=butConfirm>
      <!--�����ݽ��Ѻ��� type=hidden--> 
       <input  class= "common wid"  name="TempFeeNo" type=hidden readonly >
    <br><br>           
  <Div  id= "divTempFeeInput" style= "display:  ">
  <!--¼����ݽ��ѱ��� -->
    <Table  class= common>
      	<tr>
    	 		<td style="text-align: left" colSpan=1>
	 					<span id="spanTempGrid" >
	 					</span> 
					</td>
       </tr>
    </Table>
  <!--¼����ݽ��ѷ������ -->
    <Table  class= common>
      	<tr>
  				  	 <td style="text-align: left" colSpan=1>
					 <span id="spanTempClassGrid" >
					 </span> 
					</td>
       </tr>
    </table>
  <div id= "divTempFeeClassInput" style="display:none">
	<table  class= common>
      <tr class= common>
       		<td class= title>
       			���ѷ�ʽ
       			</td>
       		<td class= input>
					<Input class=codeno name=PayMode id="PayMode" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('chargepaymode',[this,PayModeName],[0,1]);" onDblClick="return showCodeList('chargepaymode',[this,PayModeName],[0,1]);" onKeyUp="return showCodeListKey('chargepaymode',[this,PayModeName],[0,1]);"><input class=codename name=PayModeName readonly=true>			    			
       		</td>
       		<td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
       </tr>
    </Table> 
   </div>  
  <div id= "divTempFeeClassInput3" style="display:'none;'">
		<table  class= common align=left>
       <tr class= common>
       		<td>
                <input type =button class=cssButton value="��  ��" onClick="addMul();">  
       			<input type =button class=cssButton value="��  ��" onClick="ModMul();"></td>
       		<td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
       </tr>
    </Table> 
  </div>

  <br></br>
  
    <div id=divPayMode1 style="display:none" >
    	<Table  class= common>
    		<tr class= common>
	    		<td class = title>
	    		�ֽ𽻷ѷ�ʽ
	    	</td>
	    		<td class="input">	
	    			<Input class= codeno name=CashType id="CashType" style="background: url(../common/images/select--bg_03.png) no-repeat center right; "  CodeData="0|^1|�ͻ��Խ��ֽ�|^2|�˹���ȡ�ֽ�" onMouseDown="return showCodeListEx('CashType',[this,CashTypeName],[0,1]);" onDblClick="return showCodeListEx('CashType',[this,CashTypeName],[0,1]);" onKeyUp="return showCodeListKeyEx('CashType',[this,CashTypeName],[0,1]);"><input class=codename name=CashTypeName readonly=true>
	    		</td>
                <td class=title></td>
          <td class=input></td>
          <td class=title></td>
          <td class=input></td>
    		</tr>
    		<tr class= common>
    			<td class=title>����</td>
    			<td class=input><Input class=codeno name=Currency1 id="Currency1" style="background: url(../common/images/select--bg_03.png) no-repeat center right; " onMouseDown="return showCodeList('currency',[this,CurrencyName1],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName1],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName1],[0,1]);"><input class=codename name=CurrencyName1 readonly=true></td>
    			<td class=title>
    					���ѽ��
    			</td>
    			<td class=input>
    				<Input class="multiCurrency wid" name=PayFee1 id="PayFee1">
    			</td> 
                <td class=title></td>
          <td class=input></td>   			
    		</tr>
    	</table>
    </div>
    <div id=divPayMode2 style="display:none" >
    	<Table  class= common>    			
    			<tr class= common>
    					<td class=title>����</td>
    					<td class=input><Input class=codeno name=Currency2 id="Currency2" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName2],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName2],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName2],[0,1]);"><input class=codename name=CurrencyName2 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							���ѽ��
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee2 id="PayFee2">
    					</td>
    					<td class=title>
    							Ʊ�ݺ���
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=ChequeNo2 id="ChequeNo2" >
    					</td>
    					<td class=title>
    							֧Ʊ���� 
    					</td>
    					<td class=input>
    						<Input class="multiDatePicker" name=ChequeDate2 id="ChequeDate2" onClick="laydate({elem:'#ChequeDate2'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    					</td>
    			</tr>
    			
    			<tr class= common>
    					<td class=title>
    							��������
    					</td>
    					<td class=input>
    						<Input class=codeno name=BankCode2 id="BankCode2" style="background: url(../common/images/select--bg_03.png) no-repeat center; " verify="��������|code:FINAbank" onMouseDown="return showCodeList('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');" onKeyUp="return showCodeListKey('FINAbank',[this,BankCode2Name],[0,1],null,mSql,'1');"><input class=codename name=BankCode2Name readonly=true>
    					</td>
    					<td class=title>
    							�����˺�
    					</td>
    					<td class=input>
    						<Input class="common wid" name=BankAccNo2 id="BankAccNo2" onBlur="checkBank(BankCode2,BankAccNo2);">
    					</td>
    					<td class=title>
    							����
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName2 id="AccName2">
    					</td>
    			</tr>    		   			  			
    	</table>
    </div>
    <div id=divPayMode3 style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title>����</td>
    					<td class=input><Input class=codeno name=Currency3 id="Currency3" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName3],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName3],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName3],[0,1]);"><input class=codename name=CurrencyName3 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							���ѽ��
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee3 id="PayFee3">
    					</td>
    					<td class=title>
    							Ʊ�ݺ���
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=ChequeNo3 id="ChequeNo3" >
    					</td>
    					<td class=title>
    							֧Ʊ����
    					</td>
    					<td class=input>
    						<Input class="multiDatePicker" name=ChequeDate3 id="ChequeDate3" onClick="laydate({elem:'#ChequeDate3'});"><span class="icon"><a onClick="laydate({elem: '#ChequeDate3'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    					</td>
    			</tr>
    			
    			<tr class= common>
    					<td class=title>
    							��������
    					</td>
    					<td class=input>
								<Input name=BankCode3 id="BankCode3" class='codeno' style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankCode3Name],[0,1],null,mSql,'1');" onBlur="checkBank(BankCode3,BankAccNo3);"><input name=BankCode3Name class='codename' readonly=true elementtype=nacessary>
    					</td>
    				 <td class=title>
    							�����˺�
    					</td>
    					<td class=input>
    						<Input class="common wid" name=BankAccNo3 id="BankAccNo3" onBlur="checkBank(BankCode3,BankAccNo3);">
    					</td>
    					<td class=title>
    							��  ��
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName3 id="AccName3">
    					</td>
    			</tr>    		

    			  			
    	</table>
    </div>

    <div id=divPayMode4 style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title>����</td>
    					<td class=input><Input class=codeno name=Currency4 id="Currency4" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName4],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName4],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName4],[0,1]);"><input class=codename name=CurrencyName4 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							���ѽ��
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee4 id="PayFee4">
    					</td>
					    <td class=title>
    							��������
    					</td>
    					<td class=input>
								<Input class=codeno name=BankCode4 id="BankCode4" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('FINAbank',[this,BankCode4Name],[0,1],null,mSql,'1');" onDblClick="return showCodeList('FINAbank',[this,BankCode4Name],[0,1],null,mSql,'1');" onKeyUp="return showCodeListKey('FINAbank',[this,BankCode4Name],[0,1],null,mSql,'1');" ><input class=codename name=BankCode4Name readonly=true>
    					</td>
    			</tr>
    			<tr class= common>
    					<td class=title>
    							�����˺�
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=BankAccNo4 id="BankAccNo4" onBlur="checkBank(BankCode4,BankAccNo4);">
    					</td>
    					<td class=title>
    							��  ��
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName4 id="AccName4" >
    					</td>
    			</tr>
    			<tr class= common>
			      <td class=title>
			       	֤������
			     	</td>
			     	<td class=input>
							<Input class=codeno name=IDType id="IDType" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onDblClick="return showCodeList('idtype',[this,IDTypeName],[0,1]);" onKeyUp="return showCodeListKey('idtype',[this,IDTypeName],[0,1]);"><input class=codename name=IDTypeName readonly=true>							        			
			     	</td>
			  		<td class=title>
			     		֤������
			   		</td>
			  		<td class=input>
							<Input class="common wid" name=IDNo id="IDNo" >							        			
			   		</td>
                    <td class=title></td>
    			<td class=input></td>
        	</tr> 			
    	</table>
    <div id=divPayMode5 style="display:none" >
    	<Table  class= common>
    			<tr class= common>
    					<td class=title>
    						 ʵ��֪ͨ���
    					</td>
    					<td class=input>
    						<Input class="coolConfirmBox wid" name=ChequeNo5 id="ChequeNo5" >   						 
    					</td>
    					<td class=title>
    					   ��ȫ�����/�ⰸ��/Ͷ����ӡˢ��	
    					</td>
    					<td class=input>
    					  <Input class="coolConfirmBox wid" name=OtherNo5 id="OtherNo5" > 
    					</td>
    					<td class=title>
    					<a href="javascript:void(0)" style="width:60" class=button onClick="queryLJAGet();">��    ѯ</a>
                        <!--<input class=cssButton type="button" value="��  ѯ" style="width:60" onClick="queryLJAGet()"></td>-->
    			</tr>
    			<tr class= common>
    					<td class=title>����</td>
    					<td class=input><Input class=codeno name=Currency5 id="Currency5" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName5],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName5],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName5],[0,1]);"><input class=codename name=CurrencyName5 readonly=true></td>
    					<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    			</tr>
    			
    			<tr class= common>
    					<td class=title>
    							�������
    					</td>
    					<td class=input>
    						<Input class="multiCurrency wid" name=PayFee5 id="PayFee5" readonly=true>
    					</td>
    					<td class=title>
    							��ȡ��
    					</td>
    					<td class=input>
    						<Input class="common wid" name=Drawer5 id="Drawer5" readonly=true>
    					</td>
    			</tr>    		
          <Div  id= "divFinFee1" style= "display:  " align=center>
             <Table  class= common>
               <TR  class= common>
                <TD text-align: left colSpan=1>
                  <span id="spanQueryLJAGetGrid" ></span> 
  	            </TD>
              </TR>
             </Table>					
            <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
            <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
            <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
            <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
          </Div>	    			  			
    	</table>
    <div id=divPayMode6 style="display:none" >
     	<Table  class= common>
    		<tr class= common>
    			<td class=title>����</td>
    					<td class=input><Input class=codeno name=Currency6 id="Currency6" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName6],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName6],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName6],[0,1]);"><input class=codename name=CurrencyName6 readonly=true></td>
    			<td class=title>
    					���ѽ��
    			</td>
    			<td class=input>
    				<Input class="multiCurrency wid" name=PayFee6 id="PayFee6">
    			</td>
    			<td class=title></td>
    			<td class=input></td>
    			<td class=title></td>
    			<td class=input></td>
    		</tr>
    			<tr class= common>
    					<td class=title>
    							��������
    					</td>
    					<td class=input>
    						<Input name=BankCode6 id="BankCode6" class='codeno' style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('FINAbank',[this,BankCode6Name],[0,1],null,mSql,'1');"  ondblclick="return showCodeList('FINAbank',[this,BankCode6Name],[0,1],null,mSql,'1');"  onkeyup="return showCodeListKey('FINAbank',[this,BankCode6Name],[0,1],null,mSql,'1');" onBlur="checkBank(BankCode6,BankAccNo6);"><input name=BankCode6Name class='codename' readonly=true elementtype=nacessary>
    					</td>
    					<td class=title>
    							�����˺�
    					</td>
    					<td class=input>
    						<Input class="common wid" name=BankAccNo6 id="BankAccNo6" onBlur="checkBank(BankCode6,BankAccNo6);">
    					</td>
    					<td class=title>
    							��  ��
    					</td>
    					<td class=input>
    						<Input class="common wid" name=AccName6 id="AccName6" >
    					</td>
    			</tr> 
    	</table>
    </div>
    <div id=divPayMode0 style="display:none" >
    	<Table  class= common>
    		<tr class= common>
    			<td class=title>����</td>
    					<td class=input><Input class=codeno name=Currency0 id="Currency0" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onMouseDown="return showCodeList('currency',[this,CurrencyName0],[0,1]);" onDblClick="return showCodeList('currency',[this,CurrencyName0],[0,1]);" onKeyUp="return showCodeListKey('currency',[this,CurrencyName0],[0,1]);"><input class=codename name=CurrencyName0 readonly=true></td>
    			<td class=title>
    					���ѽ��
    			</td>
    			<td class=input>
    				<Input class="multiCurrency wid" name=PayFee0 id="PayFee0">
    			</td>
    			<td class=title></td>
    			<td class=input></td>
    		</tr>
    	</table>
    </div>
   </div>
   </div>
    <table class=common>
    <TR class=common>    
     <TD class=common align=right>
     <!-- <a href="javascript:void(0)" name=butAdd  class=button onClick="addRecord();">���һ��</a> -->
      <input id="butAdd" type =button class=cssButton value="���һ��" onClick="addRecord();" name=butAdd>      
     </TD>
    </TR> 
    </table>
    <br>
    <hr class="line">
    <Table class=common>
      <TR>
        <TD  class= title>
          �ݽ��ѹ�����
        <input style="width:40px" class=common  name=TempFeeCount id="TempFeeCount" readonly tabindex=-1 >
          ��
       </TD>
       <TD  class= title>
          �ݽ����ܼ�
        <!-- <input width='10px' class=common  name=SumTempFee readonly tabindex=-1 > -->
       </TD>
       </TR>    
   </Table>
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanSumTempGrid" >
	 </span> 
	</td>
       </tr>
    </Table> 
  </Div>   
</Form>
<!--��ܶ� -->

<!--������������ύ������ -->
<Form action="./TempFeeSave.jsp" method=post name=fmSave id="fmSave" target="fraSubmit">
     <Table>
    	<tr>
         <td class=common>
         <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divTempFeeSave);">    	 
          <td class= titleImg>
           �洢����
          </td>    	 
    	</tr>
    </Table>          
  <Div  id= "divTempFeeSave" style= "display: ">
  <!--�ݽ��ѱ��� -->  
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanTempToGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    
    <Table>
    <tr>
    	<input class = common name=CertifyFlagHidden  type=hidden>  
    	<input class = common name=ClaimFeeTypeHidden  type=hidden>
    </tr>
    </Table>
    
  <!--�ݽ��ѷ������ -->
    <Table  class= common>
      	<tr>
    	 <td text-align: left colSpan=1>
	 <span id="spanTempClassToGrid" >
	 </span> 
	</td>
       </tr>
    </Table>
    <br><br>
    <table align=left>
    <TR class=input>     
     <TD class=common>
     <a href="javascript:void(0)"  class=button onClick="submitForm();">ȫ���ύ</a>
     <a href="javascript:void(0)"  class=button onClick="clearFormData();">����ȫ������</a>
      <!--<input type =button class=cssButton value="ȫ���ύ" onClick="submitForm();">
      <input type =button class=cssButton value="����ȫ������" onClick="clearFormData();">-->
      <!--input type =button class=cssButton value="��ӡƱ��" onclick="printInvoice();"-->
     </TD>
    </TR>
    </table>
  </Div>
  <input type=hidden name=NewQueryType>
  <input type=hidden name=CERTIFY_XQTempFee>
  
</Form> 
<!--����� -->   
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

<!--����-��Ų�ѯ���� -->
<Form action="./TempFeeTypeQuery.jsp" method=post name=fmTypeQuery id="fmTypeQuery" target="fraSubmit">
  <input type=hidden name=QueryNo>
  <input type=hidden name=QueryType>
 
</Form>
<br><br><br><br><br>
</body>
</html>
<script>
var bCom=manageCom.trim();
if(bCom.length >4)
{
	bCom=bCom.substring(0,4);
}

var mSql="1 and comcode like #"+bCom+"%#" ;

</script>
