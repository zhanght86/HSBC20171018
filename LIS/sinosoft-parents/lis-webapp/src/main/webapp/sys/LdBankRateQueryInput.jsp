<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�LdBankRateQueryInput.jsp
//�����ܣ�
//�������ڣ�2005-10-31
//�����ˣ������
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="LmRiskAppQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="LmRiskAppQueryInit.jsp"%>

	<title>����������Ϣ��ѯ </title>
</head>

<body  onload="initLdBankRateForm();" >
  <form  method=post name=fm id=fm target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				����������Ϣ��ѯ 
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      <TR class=common>
				<TD  class= title>����  </TD>
          <TD  class= input><Input class= "coolDatePicker" dateFormat="short" name=DeclareDate onClick="laydate({elem: '#DeclareDate'});" id="DeclareDate"><span class="icon"><a onClick="laydate({elem: '#DeclareDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
        	<TD  class= title>ֹ��  </TD>
          <TD  class= input><Input class= "coolDatePicker" dateFormat="short" name=EndDate onClick="laydate({elem: '#EndDate'});" id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </TD>
				 <TD class= common>
          <input class=cssButton type=button value="��  ѯ" onclick="LdBankRateQuery()">
          </TD>		
			</TR>
			
     </table>
  </Div>


    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCClaim1);">
    		</td>
    		<td class= titleImg>
    			����������Ϣ
    		</td>
    	</tr>
    	
    </table>
  	<Div  id= "divLCClaim1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanLdBankRateGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
    	
    	
    	<table align="center">
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage1.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage1.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage1.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage1.lastPage();"></td>
            </tr>
        </table>
 <table>
            <tr>
                <td><INPUT class=cssButton VALUE="��   ��" TYPE=button onclick="GoBack();"></td>
                
            </tr>
        </table>      
  	</div>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


