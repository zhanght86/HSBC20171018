<html> 
<% 
//�������ƣ�WFEdorConfirmInput.jsp
//�����ܣ���ȫ������-��ȫȷ��
//�������ڣ�2005-04-30 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>

<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
 
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput) session.getValue("GI");  
%>   
<script>	
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
	var curDay = "<%=PubFun.getCurrentDate()%>"; 
</script> 
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/jquery.workpool.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="WFEdorConfirm.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <%@include file="WFEdorConfirmInit.jsp"%>
  
  <title>�����޸�</title>

</head>

<body  onload="initForm();" >
  <form  method=post name=fm id=fm target="fraSubmit">
  <div id="ConfirmInputPool"></div>
  <!--<table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
		        <td class=title> ��ȫ����� </td>
		        <td class= input><Input class="common" name=EdorAcceptNoSearch></td>
	          	<TD class= title> �������� </TD>
	          	<td class= input><Input class="codeno" name=OtherNoType ondblclick="showCodeList('edornotype',[this, OtherNoName], [0, 1]);"onkeyup="showCodeListKey('edornotype', [this, OtherNoName], [0, 1]);" onkeydown="QueryOnKeyDown();" ><input class=codename name=OtherNoName readonly=true ></td>            	                          
	          	<TD class= title> �ͻ�/������ </TD>
	          	<td class= input><Input class="common" name=OtherNo></td>
	        </TR>
	      	<TR  class= common>
                <td class=title> ������ </td>
                <td class=input><Input type="input" class="common" name=EdorAppName></td>             
                <td class=title> ���뷽ʽ </td>
                <td class= input ><Input class="codeno" name=AppType ondblclick="showCodeList('edorapptype',[this, AppTypeName], [0, 1]);"onkeyup="showCodeListKey('edorapptype', [this, AppTypeName], [0, 1]);"><input class=codename name=AppTypeName readonly=true></td>                          
	            <TD class= title> ¼������ </TD>
	            <TD class= input><Input class= "multiDatePicker" dateFormat="short" name=MakeDate ></TD> 
	        </TR> 
	      	<TR  class= common>
				<TD class=title> ������� </TD>
	          	<TD class=input><Input class="codeno" name=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true></TD>      	               
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	            <TD class= title>  </TD>
	        </TR> 	        
	    </table>
    </div> 
    
	<INPUT VALUE="��  ѯ" class = cssButton TYPE=button onclick="easyQueryClickAll();"> 
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllGrid);">
    		</td>
    		<td class= titleImg>
    			 ��������
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAllGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();HighlightAllRow();"> 
      	<INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();HighlightAllRow();"> 					
      	<INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();HighlightAllRow();"> 
      	<INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();HighlightAllRow();">						
  	</div>
  	
	<br>
		<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="applyMission();">
	<br>
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfGrid);">
    		</td>
    		<td class= titleImg>
    			 �ҵ�����
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divSelfGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSelfGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();HighlightSelfRow();"> 
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();HighlightSelfRow();"> 					
		<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();HighlightSelfRow();"> 
		<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();HighlightSelfRow();"> 					
  	</div>  -->  
  	<Div  id= "divAppUWLable" style= "display: ''">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppUWInfo);">
                    </td>
                    <td class= titleImg> ��ȫ�˱����� </td>
                </tr>
               </table>
     </DIV>
    <Div  id= "divAppUWInfo" style= "display: ''" class="maxbox1">
             <table  class= common>
                <tr class=common>
                    <td class=title> �˱����� </td>
                    <td class=input><Input class="codeno" name=AppUWState id=AppUWState readonly  ><input class=codename name=AppUWStateName id=AppUWStateName readonly=true></td>                    <td class=title></td>
                    <td class=input></td>
                    <td class=title></td>
                    <td class=input></td>
                </tr></table>
                <table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > �˱���� </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px" colspan=6 ><textarea name="AppUWIdea" cols="166%" rows="4" witdh=100% class="common" readonly ></textarea></TD>
                </tr>
              </table>
    </DIV>
    
    <!--  add by jiaqiangli 2009-04-03 ����ǿ���˹��˱�ԭ��¼�� -->
   <Div  id= "divMandatoryUWLable" style= "display: ''">
               <table>
                <tr>
                    <td class="common">
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMandatoryUWInfo);">
                    </td>
                    <td class= titleImg> ǿ���˹��˱�ԭ�� </td>
                </tr>
               </table>
     </DIV>
    <Div  id= "divMandatoryUWInfo" style= "display: ''" class="maxbox1">
             <table  class= common>
                <tr class=common>
                    <TD class=title colspan=6 > ǿ���˹��˱�ԭ��¼�� </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px" colspan=6 ><textarea name="MMUWReason" cols="166%" rows="4" witdh=100% class="common" ></textarea></TD>
                </tr>
              </table>
    </DIV> 
    <!--  add by jiaqiangli 2009-04-03 ����ǿ���˹��˱�ԭ��¼�� -->
    
  	<br>
	<!--<INPUT class= cssButton TYPE=button VALUE=" ��ȫȷ�� " onclick="GoToBusiDeal();"> 
	<INPUT class= cssButton TYPE=button VALUE=" ǿ����ֹ " onclick="doOverDue();">
	<INPUT class= cssButton TYPE=button VALUE=" �� �� " onclick="doJB();">
	<INPUT class= cssButton TYPE=button VALUE=" ǿ���ύ�˱� " onclick="submitUW();">
	<INPUT class= cssButton TYPE=button VALUE="���±��鿴" onclick="showNotePad();">-->
    <a href="javascript:void(0);" class="button" onClick="GoToBusiDeal();">��ȫȷ��</a><!--
    <a href="javascript:void(0);" class="button" onClick="doOverDue();">ǿ����ֹ</a>
    <a href="javascript:void(0);" class="button" onClick="doJB();">��    ��</a>
    --><a href="javascript:void(0);" class="button" onClick="submitUW();">ǿ���ύ�˱�</a>
    <a href="javascript:void(0);" class="button" onClick="showNotePad();">���±��鿴</a><br>
    
	 <Div  id= "divGetNotice" style= "display: none">
	 	  	<br>
     <!--<INPUT class= cssButton TYPE=button VALUE="����֪ͨ��" onclick="GetNotice();">-->
     <a href="javascript:void(0);" class="button" onClick="GetNotice();">����֪ͨ��</a><br>
  </Div>

	<Div  id= "divPayNotice" style= "display: none">
		  	<br>
      <!--<INPUT VALUE=" ����֪ͨ�� " class=cssButton TYPE=button onclick="PayNotice();">-->
      <a href="javascript:void(0);" class="button" onClick="PayNotice();">����֪ͨ��</a><br>
  </Div>

<Div  id= "divCashValue" style= "display: none">

		  	<br>
      <!--<INPUT VALUE=" ��ӡ���ּ۱� " class=cssButton TYPE=button onclick="PrintCashValueTable();">-->
      <a href="javascript:void(0);" class="button" onClick="PrintCashValueTable();">��ӡ���ּ۱�</a>
  </Div>
		<!-- �����򲿷� -->
			<INPUT  type= "hidden" class= Common name= MissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          	<INPUT  type= "hidden" class= Common name= ActivityID  value= "">
          	<INPUT  type= "hidden" class= Common name= EdorAcceptNo  value= "">
          	<INPUT  type= "hidden" class= Common name= fmtransact  value= "">
          	<INPUT  type= "hidden" class= Common name= PrtSeq  value= "">
			
			<INPUT  type= "hidden" class= Common name= OtherNo1  value= "">
			<INPUT  type= "hidden" class= Common name= OtherNoType1  value= ""> 
			<INPUT  type= "hidden" class= Common name= EdorAppName1  value= "">
			<INPUT  type= "hidden" class= Common name= Apptype1  value= "">
			<INPUT  type= "hidden" class= Common name= ManageCom1  value= "">
			<INPUT  type= "hidden" class= Common name= AppntName1  value= "">
			<INPUT  type= "hidden" class= Common name= PaytoDate1  value= "">
		<!--	<INPUT  type= "hidden" class= Common name= CustomerNo1  value= "">
			<INPUT  type= "hidden" class= Common name= InsuredName1  value= "">
			<INPUT  type= "hidden" class= Common name= InsuredName2  value= "">
			<INPUT  type= "hidden" class= Common name= InsuredName3  value= "">
			<INPUT  type= "hidden" class= Common name= EdorType1  value= "">
			<INPUT  type= "hidden" class= Common name= theCurrentDate1  value= "">
			<INPUT  type= "hidden" class= Common name= BackDate1  value= "">
			<INPUT  type= "hidden" class= Common name= VIP1  value= "">
			<INPUT  type= "hidden" class= Common name= StarAgent1  value= "">-->
			<INPUT type="hidden" class= Common name=uWstate1 value="">
			<INPUT type="hidden" class= Common name=CustomerIdea value="">
			
			<INPUT  type= "hidden" class= Common name= ContNo  value= "">
			<!--��ȫȷ�ϲ����� --->
			<INPUT  type= "hidden" class= Common name= Operator  value= "">
			<!--��ȫ��������� --->
			<INPUT  type= "hidden" class= Common name= ApplyOperator  value= "">
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
