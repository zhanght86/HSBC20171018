
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//�ⰸ��

	  String tMissionID = request.getParameter("MissionID");  //����������ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //������������ID
//=======================END========================
%> 
<head >
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLClaimEndCase.js"></SCRIPT>
   <%@include file="LLClaimEndCaseInit.jsp"%>
</head>

<body  onload="initForm()" >
<form action="./LLClaimEndCaseSave.jsp" method=post name=fm id=fm target="fraSubmit"> 
	<!--table>            
    	<tr>
    		<td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimGrid);"></td>
    		<td class=titleImg> �ⰸ��Ϣ </td>
    	</tr>
    </table>  
    <div id= "divLLClaimGrid" style= "display: ''">	
        <INPUT class=cssButton name="" VALUE="  �����  "  TYPE=button onclick="LLClaimQuery()" >     
	      <table  class= common>
	        	<tr  class= common>
	        		<td text-align: left colSpan=1><span id="spanLLClaimGrid" ></span></td>
		    		</tr>
	      </table>
	      <table> 
             <tr>  
                 <td><INPUT class=cssButton VALUE=" ��ҳ " TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton VALUE=" βҳ " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr> 
         </table>  
    </div-->         
    <table>            
    	<tr>
    		<td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
    		<td class=titleImg> �ⰸ��ϸ��Ϣ </td>
    	</tr>
    </table>
		<Div id= "divLLSubReport" style= "display: ''" class="maxbox1">
 			  <table  class= common>
				   <TR  class= common>
				    	<TD  class= title> ������ </TD>
				    	<TD  class= input> <Input class="readonly wid" readonly name=ClmNo id=ClmNo ></TD>
				    	<TD  class= title> �ⰸ״̬ </TD>       
				    	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=ClmState id=ClmState ondblclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);"onclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);" onkeyup="return showCodeListKey('llclaimstate',[this,ClmStateName],[0,1]);"><input class=codename name=ClmStateName id=ClmStateName readonly=true></TD>				    	
 				  		<!--TD  class= title> �����⸶���</TD>
				    	<TD  class= input> <Input class="readonly" readonly name=StandPay></TD>
				        <TD  class= title> �ȸ������</TD>
				    	<TD  class= input> <Input class="readonly" readonly name=BeforePay ></TD>        
					    <TD  class= title> �����⸶���</TD>
					    <TD  class= input> <Input class="readonly" readonly name=RealPay ></TD-->          					          	
				  		<TD  class= title> �⸶����</TD>
				    	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=GiveType id=GiveType ondblclick="return showCodeList('llclaimconclusion',[this,GiveTypeName],[0,1]);" onclick="return showCodeList('llclaimconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,GiveTypeName],[0,1]);"><input class=codename name=GiveTypeName id=GiveTypeName readonly=true></TD>
				   </TR>
				   <TR  class= common>
				        <TD  class= title> ����Ա</TD>
				    	<TD  class= input> <Input class="readonly wid" readonly name=ClmUWer id=ClmUWer ></TD>        
				  		<TD  class= title> �᰸����</TD>
				    	<TD  class= input> <Input class="readonly wid" readonly name=EndCaseDate id=EndCaseDate ></TD>
				    	<TD  class= title> ʵ����־</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="confGetMoney" id="confGetMoney" ></TD>       
				        <!--TD  class= title> ������������</TD>
				    	<TD  class= input> <Input class="readonly" readonly name=CasePayType ></TD>        
					    <TD  class= title> �������</TD>
					    <TD  class= input> <Input class="readonly" readonly name=CheckType ></TD-->          					          	
				  </TR>	
        </table>
        <!--table class= common>
            <TR  class= common>
				<TD  class= title> �⸶�������� </TD>
		    </tr>	      
			<TR  class= common>				        		
			    <TD  class= input> <textarea name="GiveTypeDesc" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
			</tr>	      
		</table-->
		<INPUT class=cssButton name="addSave" VALUE="�᰸����"  TYPE=button onclick="saveClick()" >
		<INPUT class=cssButton name="queryClm" VALUE="�ⰸ��ѯ"  TYPE=button onclick="queryClick()" >
        <INPUT class=cssButton name=BarCodePrint VALUE="��ӡ������"  TYPE=button onclick="colBarCodePrint();">
        <INPUT class=cssButton name=ColQueryImage VALUE="Ӱ���ѯ"  TYPE=button onclick="colQueryImage();">
		<!--INPUT class=cssButton name="" VALUE="�᰸֪ͨ"  TYPE=button onclick="zhoulei()" -->
		<!--INPUT class=cssButton name="" VALUE="�ͻ�ǩ�ձ��"  TYPE=button onclick="zhoulei()" -->				
		<hr class="line">
		<!--��ť��-->
        <INPUT class=cssButton name="EndSave" VALUE="�᰸ȷ��"  TYPE=button onclick="EndSaveClick()">  
        <INPUT class=cssButton name="goBack" VALUE=" ��  �� "  TYPE=button onclick="goToBack()">
	  </div>
	  <Div id= "divLLParaPrint" style= "display: none">
	  <table>            
    	<tr>
    		<td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,are);"></td>
    		<td class=titleImg> �᰸��֤��ӡ </td>
    	</tr>
    </table><Div id= "are" style= "display: ''">
	  	<table  class= common>
	        	<tr  class= common>
	        		<td text-align: left colSpan=1><span id="spanLLReCustomerGrid" ></span></td>
		    		</tr>
	    </table>
		<table  class= common>
			<TR  class= common>
				<TD  class= title> ��֤���� </TD>       
	            <TD  class= Input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name=PrtCode id=PrtCode ondblclick="return showCodeList('llparaprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');"onclick="return showCodeList('llparaprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');" onkeyup="return showCodeListKey('llparaprtcode',[this,PrtCodeName],[0,1],null,fm.all('ClaimNo').value,'otherno','1');"><input class=codename name=PrtCodeName id=PrtCodeName readonly=true></TD>    
				<TD  class= title> </TD><TD  class= Input></TD><TD  class= title> </TD><TD  class= Input></TD>
			</TR>
		</table>
       <INPUT class=cssButton type=hidden name="" VALUE="������ӡ "  TYPE=button onclick="BatchPrtPrintB();">
	   <INPUT class=cssButton type=hidden name="" VALUE="�嵥��ӡ"  TYPE=button onclick="BatchPrtPrintC();">
	  </div>
	  </Div>	<!--<Input class=cssButton name="" VALUE="��ӡ��֤"  TYPE=button onclick="SinglePrtPrint();">-->
    <%
    //������,������Ϣ��
    %> 
    <input type=hidden id="fmtransact" name="fmtransact">    
    <Input type=hidden id="ClaimNo" name="ClaimNo"><!--�ⰸ��-->
    
  	<Input type=hidden id="CustomerNo" name="CustomerNo"><!--�ͻ��š����ڴ�ӡ����8��6�����-->
  	
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	
	<!--##########��ӡ��ˮ�ţ��ڴ�ӡ���ʱ����  �ⰸ�š���֤���ͣ����룩��ѯ�õ�######-->
    <input type=hidden id="PrtSeq" name="PrtSeq">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
