<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.* " %>
<%@page import="com.sinosoft.utility.*"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2003-4-2
//创建人  ：lh
//修改人：刘岩松
//修改时间:2004-2-17
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%	
	String tClaimNo = "";
	String tCustomerNo = "";
	String tCustomerName = "";
	try
	{		
		tClaimNo = request.getParameter("ClaimNo");
		tCustomerNo = request.getParameter("CustomerNo");
		tCustomerName = StrTool.unicodeToGBK(request.getParameter("CustomerName"));
	}
	catch( Exception e )
	{		
		tClaimNo = "";
		tCustomerNo = "";				
    tCustomerName = "";		
	}


	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");


%>
<head>


  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  	
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

	<SCRIPT src="SubScanClaimQuery.js"></SCRIPT>
	<%@include file="SubScanClaimQueryInit.jsp"%>

	<title>理赔查询 </title>
</head>

<body  onload="initForm();" >
  <form action="./ScanClaimQuerySave.jsp" method=post name=fm target="fraSubmit">
 <br>
 <br> 
 <table class= common>
       <TR>
          <TD  class= title >赔案号</TD>
          <TD  class= input >  <Input class="readonly" name=RgtNo readonly > </TD>
          <TD  class= title >被保人客户号</TD>
          <TD  class= input >  <Input class="readonly" name=InsuredNo readonly > </TD>
          <TD  class= title > 被保人姓名 </TD>
          <TD  class= input > <Input class="readonly" name=InsuredName readonly > </TD>
       </TR>      
 </table> 
 <br>
 <hr>  
   
 <table class= common>
  	  <!--tr  class= common>
      <TD  class= title >扫描类型</TD>
      <TD  class= input ><Input class="code" name="ScanType" elementtype=nacessary  ondblclick="return showCodeList('LPScanType',[this],null,null,null,null,1,200);" onkeyup="return showCodeListKey('LPScanType',[this],null,null,null,null,1,200);" elementtype=nacessary ></td>
	    </tr-->
          <TD  class= title>
            扫描类型&nbsp;&nbsp;
          </TD>
          <TD  class= input>
            <select class=common  name=ScanType verify="扫描类型|NOTNULL" elementtype=nacessary >
               <option value='0000'>                        </option>
               <!--option value='4002'>4002-理赔证明           </option-->
               <option value='4003'>4003-理赔资料           </option>
               <option value='4004'>4004-个人医疗费用清单   </option>
               <option value='4005'>4005-理赔补充资料       </option>
            </select>
          </TD>	        
 </table> 
 <br>
 <table class= common>     	    
  	  <tr  class= common>	    
	    <INPUT class= cssButton VALUE="扫描件查询" TYPE=button onclick="ScanPrtHTM();"> 
	    <INPUT class= cssButton VALUE=" 返    回 " TYPE=button onclick="top.close();"> 
	    </tr>	 
 </table>	       
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


