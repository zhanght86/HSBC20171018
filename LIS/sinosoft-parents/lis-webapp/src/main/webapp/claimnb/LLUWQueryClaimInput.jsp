<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�LLUWQueryClaimInput.jsp
//�����ܣ�����Ͷ���˺ͱ������ⰸ��ѯ
//�������ڣ�2005-12-03 
//������  �������
%>
<html>
<%
	String tClmNo      = "";  //�ⰸ��
	String tTransferNo = "";  //Ͷ���˻��߱����˺���
	String tFlag       = "";  //��ʶ

	tClmNo      = request.getParameter("ClmNo");
	tTransferNo = request.getParameter("transferNo");
	tFlag       = request.getParameter("Flag");
    
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var operator   = "<%=tGI.Operator%>";     //��¼����Ա
	var manageCom  = "<%=tGI.ManageCom%>";    //��¼��½����
    var ClmNo      = "<%=tClmNo%>";           //�ⰸ��
    var transferNo = "<%=tTransferNo%>";      //Ͷ���˻��߱����˺���
    var Flag       = "<%=tFlag%>";            //��ʶ
</script>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="LLUWQueryClaim.js"></SCRIPT>
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="LLUWQueryClaimInit.jsp"%>
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
<br>
    <table class="common">
      <tr class="common">
        <td class="title5"> �ͻ��� </td>
        <td class="input5">
          <input class="common wid" name="CustomerNo" id="CustomerNo" readonly>
        </td>
        <td class="title5"> �ͻ�����</td>
        <td class="input5">
          <input class="common wid" name="CustomerName" id="CustomerName" readonly>
        </td>
      </tr>
    </table>

<!--���ౣ���ۼ�-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaim);">
	    	</td>
	    	<td class= titleImg> ������Ϣ </td>
	    </tr>
	  </table>    
	  <div id= "divClaim" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanClaimGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
    <div  id= "divTurnPage" align=center style= "display: '' ">
      <table class=common>
    	<tr class=common>
    	  <td class=common>
            <input class=cssButton90 VALUE="��  ҳ" TYPE=button id="buttona" onClick="turnPage.firstPage();"> 
            <input class=cssButton91 VALUE="��һҳ" TYPE=button id="buttonb" onClick="turnPage.previousPage();"> 					
            <input class=cssButton92 VALUE="��һҳ" TYPE=button id="buttonc" onClick="turnPage.nextPage();"> 
            <input class=cssButton93 VALUE="β  ҳ" TYPE=button id="buttond" onClick="turnPage.lastPage();">
          </td>
        </tr>
     </table>
    </div>
   </div>	


<!--������Ϣ-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>�����ⰸ������Ϣ </td>
	    </tr>
	  </table>    
	  <div id= "divPol" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  			<span id="spanPolGrid" ></span> 
          </td>
  		</tr>
      </table>
   </div>	
   <div id="Button" style="display:">
   <hr class="line">
   <table>
   <tr>
   <td>
      <INPUT VALUE="����������ϸ��ѯ" class=cssButton TYPE=button onClick="showDetail();">
      <INPUT VALUE="  Ӱ�����ϲ�ѯ  " class=cssButton TYPE=button onClick="showImage();"> 
      <INPUT VALUE="  ����˱���ѯ  " class=cssButton TYPE=button id="button1"  onclick="showClaimSecond();">
	  <Input Value ="��    ��" class=cssButton Type=button onClick="top.close();"> 
   </td>
   </tr>
   </table>
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>

 
