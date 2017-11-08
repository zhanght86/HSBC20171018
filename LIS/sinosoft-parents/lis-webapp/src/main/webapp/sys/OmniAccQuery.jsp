<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期： 
//创建人  ：Howie
//更新记录：  更新人    更新日期     更新原因/内容
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
  <!--如果装载下面这个文件会影响到翻页功能的使用-->
  <!--<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>-->
  <SCRIPT src="OmniAccQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="OmniAccQueryInit.jsp"%>
	<title>万能险保单结算查询 </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id="fm" action="./OmniAccQuerySave.jsp" >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				保单信息
			</td>
		</tr>
	</table> 
	<Div  id= "divLCPol1" class="maxbox1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            保单号码
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ContNo id="ContNo" >
          </TD>
           <TD  class= title>
            险种号码
          </TD>
          <TD  class= input>
          	<Input class= "readonly wid" readonly name=PolNo id="PolNo" >
          </TD>
         <TD  class= title>
            险种编码
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=RiskCode id="RiskCode" >
          </TD>
		</TR>
		 <TR  class= common>
          <TD  class= title>
            被保人姓名
          </TD>
          <TD  class= input>
          	<Input class= "readonly wid" readonly name=InsuredName id="InsuredName" >
          </TD>
          <TD  class= title>
            投保人姓名
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
    			保险账户信息
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
  	
<INPUT class=cssButton VALUE=" 查询详细信息 " TYPE=hidden onClick="showAccDetail();">

      
   	<Div  id= "divLCInsureAccClass" style= "display: none">	
  	    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid2);">
    		</td>
    		<td class= titleImg>
    			 保险账户分类
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
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage2.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage2.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage2.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage2.lastPage();"> 					
	
   </div>
 </div>

	<Div  id= "divLCInsureAccTrace" style= "display: none" >
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid3);">
    		</td>
    		<td class= titleImg>
    			 保险账户结算履历信息
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
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage3.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage3.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage3.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage3.lastPage();"> 
     </center>					
  	</div>
</div> 	
 <Div  id= "divDate" style= "display: ''">
    <table  class= common align=center>
		 <TR  class= common>
          <TD  class= title>
            起始日期
          </TD>
          <TD  class= input>
          	<Input class= "coolDatePicker" dateFormat="short" name=StartDate id="StartDate" onClick="laydate({elem:'#StartDate'});"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title>
            结束日期
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

  <INPUT VALUE="打印万能险报告" class= cssButton TYPE=button onClick="printNotice();"> 
	<INPUT class=cssButton VALUE=" 返回 " TYPE=button onClick="returnParent();">
  </form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


