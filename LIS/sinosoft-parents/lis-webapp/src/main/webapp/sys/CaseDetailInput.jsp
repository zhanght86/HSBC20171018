<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>             
<html>
<%
//�������ƣ�CaseDetailInput.jsp
//�����ܣ��ۺϲ�ѯ֮������ϸ��Ϣ��ѯ
//�������ڣ�2004-2-19
//������  ��������
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="CaseDetailInput.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="CaseDetailInit.jsp"%>

	<title>������ϸ��Ϣ </title>
</head>

<body  onload="initForm();" >
  <form action="./ShowCaseDetail.jsp" method=post name=fm id=fm target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				������ϸ��Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            ������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RptNo id=RptNo >
          </TD>
          <TD  class= title5>
            ������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RgtNo id=RgtNo >
          </TD>
		</TR>
				
		<TR  class= common>
          <TD  class= title5>
            ������� 
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid"  readonly name=CheckType id=CheckType >
          </TD>
          <TD  class= title5>
            ����״̬
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CaseState id=CaseState >
          </TD>
		</TR>
				
				<TR  class= common>
          <TD  class= title5>
            �¹������� 
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CustomerType id=CustomerType >
          </TD>
          <TD  class= title5>
            �¹�������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CustomerName id=CustomerName >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AccidentDate id=AccidentDate >
          </TD>
          <TD  class= title5>
            ���յص�
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AccidentSite id=AccidentSite >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            �¹�����
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AccidentType id=AccidentType >
          </TD>
          <TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=ClmDecision id=ClmDecision >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RptDate id=RptDate >
          </TD>
          <TD  class= title5>
            ����������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RptOperatorName id=RptOperatorName >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RgtDate id=RgtDate >
          </TD>
          <TD  class= title5>
            ����Ȩ��������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RgtantName id=RgtantName >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            �ϼƸ������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalPayMoney id=TotalPayMoney >
          </TD>
          <TD  class= title5>
            �ϼƻ�����
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalReleaseMoney id=TotalReleaseMoney >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            �ѷ���δ��ȡ���汣�ս�
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=LiveMoney id=LiveMoney>
          </TD>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=BonusMoney id=BonusMoney >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            �ϼ��˻����շ�
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalPermMoney id=TotalPermMoney >
          </TD>
          <TD  class= title5>
            �ϼƿۿ�
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=TotalSubMoney id=TotalSubMoney >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=PDNo id=PDNo>
          </TD>
		  <TD  class= title5>
            
          </TD>
          <TD  class= input5>
            
          </TD>
        </TR>
        
        <TR  class= common>
          <TD  class= title5>
            �᰸����
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=EndCaseDate id=EndCaseDate >
          </TD>
          <TD  class= title5>
            ֪ͨ����
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=InformDate id=InformDate >
          </TD>
				</TR>
				
				<TR  class= common>
          <TD  class= title5>
            ���������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=Clmer id=Clmer >
          </TD>
          <TD  class= title5>
            ǩ����
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=ClmUWer id=ClmUWer >
          </TD>
				</TR>
				
				<tr >
        	<td class=common>
        	  <input class=common type= hidden name= RgtNo_1 id=RgtNo_1 >
        	  <input class=common type= hidden name= ClmUWNo id=ClmUWNo >
        	  <input class=common type= hidden name= PolNo id=PolNo >
        	  <input class=common type= hidden name= InsuredName id=InsuredName >
        		<input class=common type= hidden name= CaseNo id=CaseNo >
        		<input class=common type= hidden name= InsuredNo id=InsuredNo >
        		<input class=common type= hidden name= LPJC id=LPJC >
        		
        	</td>
				</tr>
				<TR  class= common>
          <TD  class= title5>
            ����֪ͨ��
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=InformName id=InformName >
          </TD>
          <TD  class= title5>
            ���ս���ȡ��ʽ
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=CaseGetMode id=CaseGetMode >
          </TD>
				</TR>
			</table>
			
			<table class=common>
				<TR class= common>
			    <TD class= title>
 				    �¹ʾ�������
  			  </TD>
		    </TR>

		    <TR  class= common>
  			  <TD  class= input>
    				<textarea class= "readonly wid" readonly name="AccidentReason" id=AccidentReason cols="110%" rows="3" witdh=25% class="common wid">
            </textarea>
    			</TD>
  		  </TR>
  		  
  		  <TR class= common>
          <TD  class= title>
            ��������ԭ��
          </TD>
        </TR>
        
        <TR>
          <TD  class= input>
            <textarea class= "readonly wid" readonly name="RgtReason" id=RgtReason cols="110%" rows="3" witdh=25% class="common">
            </textarea>
           </TD>
        </TR>
        
        <TR class= common>
          <TD  class= title>
            ��������
          </TD>
        </TR>
        
        <TR>
          <TD  class= input>
            <textarea class= "readonly wid" readonly name="PDContent" id=PDContent cols="110%" rows="3" witdh=25% class="common">
            </textarea>
           </TD>
        </TR>
        
        <tr>
        	<td>
        		<input class="cssButton" type=button value="���κ˱�" onclick="SecondUW()">
  					<input class="cssButton" type=button value="��Լ��ͣ" onclick="EndAgreement()">
  				</td>
  			</tr>
     </table>
     
  </Div>
</form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
  </body>
<script language="javascript">
try
{
  fm.ClmUWNo.value = '<%= request.getParameter("ClmUWNo") %>';
  fm.RgtNo_1.value = '<%= request.getParameter("RgtNo") %>';
  fm.InsuredName.value = '<%= StrTool.unicodeToGBK(request.getParameter("InsuredName")) %>';
	fm.PolNo.value = '<%= request.getParameter("PolNo")%>';
	fm.ClmUWer.value = '<%= StrTool.unicodeToGBK(request.getParameter("ClmUWer")) %>';
	fm.CheckType.value = '<%= StrTool.unicodeToGBK(request.getParameter("CheckType")) %>';	
	fm.Clmer.value = '<%= StrTool.unicodeToGBK(request.getParameter("Clmer")) %>';
	fm.LPJC.value = '<%= StrTool.unicodeToGBK(request.getParameter("LPJC")) %>'				
}
catch(ex)
{
  alert(ex);
}
</script>
</html>
