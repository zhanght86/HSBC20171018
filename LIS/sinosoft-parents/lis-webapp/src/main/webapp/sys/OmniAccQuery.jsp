<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ� 
//������  ��Howie
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tContNo = "";
	String tPolNo = "";
	String tIsCancelPolFlag = "";
	String tRiskCode = "";
	try
	{
	    tContNo = request.getParameter("ContNo");
		tPolNo = request.getParameter("PolNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
		tRiskCode = request.getParameter("RiskCode");
	}
	catch( Exception e )
	{
		tContNo = "";
		tPolNo = "";
		tIsCancelPolFlag = "";
	}
	String tInsuredName = "";
	try
	{
		tInsuredName = request.getParameter("InsuredName");
		tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"),"GBK");
		loggerDebug("OmniAccQuery","sxy--sxy : "+tInsuredName );
	}
	catch( Exception e )
	{
	
		tInsuredName = "";
	}
	String tAppntName = "";
	try
	{
		tAppntName = request.getParameter("AppntName");
		tAppntName = new String(tAppntName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tAppntName = "";
	}
%>
<head>
<script> 
	var tContNo = "<%=tContNo%>";
	var tPolNo = "<%=tPolNo%>";
	var tRiskCode = "<%=tRiskCode%>";
	var tInsuredName = "<%=tInsuredName%>";
	var tAppntName = "<%=tAppntName%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <!--���װ����������ļ���Ӱ�쵽��ҳ���ܵ�ʹ��-->
  <!--<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>-->
  <SCRIPT src="OmniAccQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="OmniAccQueryInit.jsp"%>
	<title>�����ձ��������ѯ </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id="fm" action="./OmniAccQuerySave.jsp" >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				������Ϣ
			</td>
		</tr>
	</table> 
	<Div  id= "divLCPol1" class="maxbox1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ContNo id="ContNo" >
          </TD>
           <TD  class= title>
            ���ֺ���
          </TD>
          <TD  class= input>
          	<Input class= "readonly wid" readonly name=PolNo id="PolNo" >
          </TD>
         <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=RiskCode id="RiskCode" >
          </TD>
		</TR>
		 <TR  class= common>
          <TD  class= title>
            ����������
          </TD>
          <TD  class= input>
          	<Input class= "readonly wid" readonly name=InsuredName id="InsuredName" >
          </TD>
          <TD  class= title>
            Ͷ��������
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=AppntName id="AppntName" >
          </TD>
           <TD  class= title>
          </TD>
          <TD  class= input>
          </TD>
        </TR>
		
     </table>
  </Div>
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAcc);">
    		</td>
    		<td class= titleImg>
    			�����˻���Ϣ
    		</td>
    	</tr>
    </table>
    
  	<Div  id= "divLCInsureAcc" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>					
  	</div>
  	
<INPUT class=cssButton VALUE=" ��ѯ��ϸ��Ϣ " TYPE=hidden onClick="showAccDetail();">

      
   	<Div  id= "divLCInsureAccClass" style= "display: none">	
  	    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid2);">
    		</td>
    		<td class= titleImg>
    			 �����˻�����
    		</td>
    	</tr>
    </table>
    <Div  id= "divPolGrid2" style= "display: ''"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid2" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage2.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage2.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage2.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage2.lastPage();"> 					
	
   </div>
 </div>

	<Div  id= "divLCInsureAccTrace" style= "display: none" >
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid3);">
    		</td>
    		<td class= titleImg>
    			 �����˻�����������Ϣ
    		</td>
    	</tr>  	
    </table>
  	<Div  id= "divPolGrid3" style= "display: ''" align=left>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid3" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <center>
		<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage3.firstPage();"> 
		<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage3.previousPage();"> 					
		<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage3.nextPage();"> 
		<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage3.lastPage();"> 
     </center>					
  	</div>
</div> 	
 <Div  id= "divDate" style= "display: ''">
    <table  class= common align=center>
		 <TR  class= common>
          <TD  class= title>
            ��ʼ����
          </TD>
          <TD  class= input>
          	<Input class= "coolDatePicker" dateFormat="short" name=StartDate id="StartDate" onClick="laydate({elem:'#StartDate'});"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
           <Input class= "coolDatePicker" dateFormat="short" name=EndDate id="EndDate" onClick="laydate({elem:'#EndDate'});"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
           <TD  class= title>
            </TD>
          <TD  class= input>
          </TD>
      </TR>		
     </table>
  </Div>
   <input type=hidden id="fmtransact" name="fmtransact">

  <INPUT VALUE="��ӡ�����ձ���" class= cssButton TYPE=button onClick="printNotice();"> 
	<INPUT class=cssButton VALUE=" ���� " TYPE=button onClick="returnParent();">
  </form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


