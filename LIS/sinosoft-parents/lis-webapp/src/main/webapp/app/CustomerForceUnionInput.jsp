<html>
<%
	String CustomerNo = request.getParameter("customerno");
	String ptrno = request.getParameter("prtno");
	loggerDebug("CustomerForceUnionInput","ptrno: "+ptrno);
	loggerDebug("CustomerForceUnionInput","CustomerNo: "+CustomerNo);
//程序名称：客户号强制关联
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <script type="text/javascript">
  var PtrNo = "<%=ptrno %>"
  var Hole_CustomerNo = "<%=CustomerNo %>"
  </script>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="CustomerForceUnionInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CustomerForceUnionInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./CustomerMergeSave.jsp" method=post name=fm id="fm" target="fraSubmit">

	

    <table style= "display: none">
      <tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></td>
        <td class= titleImg>客户合并查询条件</td></tr>
    </table>

      <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOPolGrid);">
	    	</td>
	    	<td class= titleImg>
	    	 新客户信息 
	    	</td>
	    </tr>
	    </table>    
	    <div id= "divOPolGrid" style= "display: ''" align=center >
        <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			  </tr>
    	  </table>
      </div>      
      <table class="common">
    		<tr>
    			<td class="title">客户号</td>
    			<td class="input"><input class="common wid" name=OldCustomerno id="OldCustomerno"></td>
    			<td>
    				<a href="javascript:void(0)" class=button onclick="queryClick();">查  询</a>
    			</td>
    		</tr>
    	</table>
       <!-- <input class=cssButton type="button" align=lift value=" 查  询 " onclick="queryClick()"> -->

    
    </div>
    
    <!-- 客户列表 -->
    <table>
   	  <tr>
        <td class="common"><IMG  src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divClientList);"></td>
    	<td class= titleImg>相同原客户信息列表</td></tr>
    </table>
    
	  <div id="divClientList" style="display: ''">
      <table  class= common>
        <tr  class= common>
          <td text-align: left colSpan=1><span id="spanClientList" ></span></td>
        </tr>
	    </table>
  	  <div id= "divCustomerUnionInfo" style= "display: ''" >
  	    <table class= common>
      	  <TR  class= common>
            <TD class= title> 新客户号码 </td>
            <TD class= input><input class="readonly wid"   name=CustomerNo_OLD id="CustomerNo_OLD" readonly ></td>
            <TD class = title>  </td>
            <TD class= input><input class="readonly wid"  name= readonly id="readonly"></td>
            <TD class= title> 合并后客户号码 </td>
            <TD class= input><input class="readonly wid"  name=CustomerNo_NEW id="CustomerNo_NEW" readonly ></td>
          </TR>
       </table>
      </div>
      <a href="javascript:void(0)" class=button onclick="ClientMerge();">强制客户合并</a>
	  <!-- <input class=cssButton type="button" value="强制客户合并" onclick="ClientMerge()"> -->
	  </div>
    <br>
    <div id="divClientList1" style="display: none">
      <div class="maxbox1">
      <table>
        <tr>
          <td class="title">合并方向</td>
          <td class="input">
			      <input type="radio" name="exchangeRadio" id="exchangeRadio" value="1"  OnClick="exchangeCustomerNo();" >反向 
			      <input type="radio" name="exchangeRadio" id="exchangeRadio" value="-1" OnClick="exchangeCustomerNo();">正向 
			      <input type=hidden id="MissionID" name=MissionID>
			      <input type=hidden id="SubMissionID" name=SubMissionID>         
			      <input type=hidden id="CustomerType" name=CustomerType>         
			      <input type=hidden id="PrtNo" name=PrtNo>         
				    <input type=hidden id="ForceFlag" name=ForceFlag value="1">
		      </td>
        </tr>
      </table>
      </div>	
    </div>
   
   <hr class="line">
    <div id="divCustomerContInfo" style="display: none">
   	  <input class=cssButton type="button" value="投保单/保单 明细查询" onclick="getContDetailInfo()">
		  <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCustomerContGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <div align=center>
  	  <INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage3.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage3.previousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage3.nextPage();"> 
      <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage3.lastPage();"> 			
      </div>
    </div>
   	
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
