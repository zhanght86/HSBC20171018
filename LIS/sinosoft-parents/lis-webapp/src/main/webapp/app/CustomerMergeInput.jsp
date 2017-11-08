<html>
<%
	String AppntName = request.getParameter("AppntName");
	String AppntNo = request.getParameter("AppntNo");
	String InsuredName = request.getParameter("InsuredName");
	String InsuredNo = request.getParameter("InsuredNo");
	String ptrno = request.getParameter("prtNo");
	String tMissionId = request.getParameter("MissionID");
	String tSubMissionId = request.getParameter("SubMissionID");
	String tCustomerType = request.getParameter("CustomerType");
	loggerDebug("CustomerMergeInput","ptrno: "+ptrno);
	loggerDebug("CustomerMergeInput","AppntName: "+AppntName);
	loggerDebug("CustomerMergeInput","AppntNo: "+AppntNo);
	loggerDebug("CustomerMergeInput","InsuredNo: "+InsuredNo);
	loggerDebug("CustomerMergeInput","InsuredName: "+InsuredName);
	loggerDebug("CustomerMergeInput","tMissionId: "+tMissionId);
	loggerDebug("CustomerMergeInput","tSubMissionId: "+tSubMissionId);
	loggerDebug("CustomerMergeInput","tCustomerType: "+tCustomerType);
//程序名称：ClientMergeInput.jsp
//程序功能：
//创建日期：2002-08-19
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <script type="text/javascript">
  var AppntName = "<%=AppntName%>";
  var AppntNo = "<%=AppntNo%>";
  var InsuredName = "<%=InsuredName%>";
  var InsuredNo ="<%=InsuredNo%>";
  var PtrNo = "<%=ptrno %>"
  var tMissionId = "<%=tMissionId %>"
  var tSubMissionId = "<%=tSubMissionId %>"
  var tCustomerType = "<%=tCustomerType %>"
  
  </script>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="CustomerMerge.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CustomerMergeInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
</head>
<body  onload="initForm();" >
  <form action="./CustomerMergeSave.jsp" method=post name=fm id="fm" target="fraSubmit">

	

    <table style= "display: none">
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></td>
        <td class= titleImg>客户合并查询条件</td></tr>
    </table>

    <div  id= "divLDPerson" style= "display:  ">
      <table class="common" style= "display: none">
        <tr class="common">
          <td class="title">客户姓名</td>
          <td class="input"><Input class="common wid" name=Name id="Name" ></td>
          
          <td class="title">客户性别</td>
          <td class="input"><Input class="codeno" name=AppntSex id="AppntSex"  verify="投保人性别|notnull&code:Sex" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Sex',[this,AppntSexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,AppntSexName],[0,1]);"><input class=codename name=AppntSexName id=AppntSexName readonly=true ></td>
          
          <td class="title">客户出生日期</td>
          <td class="input"><input class="coolDatePicker" dateFormat="short" name="Birthday" onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>></td></tr>
          	
          <tr class="common">
        	<td class="title">证件类型</td>
            <td class="input">
			    <Input class="codeno" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name="AppntIDType" id="AppntIDType" verify="投保人证件类型|code:IDType" ondblclick="return showCodeList('IDType',[this,AppntIDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,AppntIDTypeName],[0,1]);">
		        <input class=codename name=AppntIDTypeName id="AppntIDTypeName" readonly=true></td>	
          
          <td class="title">证件号码</td>
          <td class="input"><Input class="common wid" name=IDNo id="IDNo" ></td>
     
          <td class="title"></td>
          <td class="input"><Input class="readonly wid" readonly name= ></td></tr>
         </table>
<input class=cssButton type="hidden" align=lift value=" 查  询 " onclick="queryClick()">
 
          
      <table>
	    	<tr>
	        <td class=common>
	          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOPolGrid);">
	    	</td>
	    	<td class=titleImg>
	    	 新客户信息 
	    	</td>
	    </tr>
	    </table>    
	<div id= "divOPolGrid" style= "display:  " align=center >
        <table  class=common>
       		<tr  class=common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         
    </div>      
      
       
          
       

    
    </div>
    
    <!-- 客户列表 -->
    <table>
   	  <tr>
        <td class="common"><IMG  src="../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divClientList);"></td>
    	<td class= titleImg>相同原客户信息列表</td></tr>
    </table>
    
	<div id="divClientList"  style="display:  ">
      <table  class=common>
        <tr  class=common>
          <td style="text-align: left" colSpan=1><span id="spanClientList" ></span></td></tr>
	  </table>
	  <div id= "divCustomerUnionInfo" style= "display:  " >
	 <table class= common>
    	<TR  class= common>
            	
          <TD class= title5> 新客户号码 </td>
          <TD class= input5><input class="readonly wid"  name=CustomerNo_OLD id="CustomerNo_OLD" readonly ></td>
          <TD class= title5> 合并后客户号码 </td>
          <TD class= input5><input class= "readonly wid"  name=CustomerNo_NEW id="CustomerNo_NEW"  readonly ></td>
        </tr>
     </table>
     
	</div>
	  <input class=cssButton type="button" value="客户合并" onclick="ClientMerge()">
	</div>
    <div id="divClientList1" style="display: none">
      <table>
        <tr>
          <td class="title">合并方向</td>
          <td class="input">
			      <input type="radio" name="exchangeRadio"  value="1"  OnClick="exchangeCustomerNo();" >反向 
			      <input type="radio" name="exchangeRadio"  value="-1" OnClick="exchangeCustomerNo();">正向 
			      <input type=hidden name=MissionID id="MissionID">
			      <input type=hidden name=SubMissionID id="SubMissionID">         
			      <input type=hidden name=CustomerType id="CustomerType">         
			      <input type=hidden name=PrtNo id="PrtNo">         
		  </td>
        </tr>
      </table>	
   </div>
   
   <hr class=line>
   <div id="divCustomerContInfo" style="display: none">
   	  <input class=cssButton type="button" value="投保单/保单 明细查询" onclick="getContDetailInfo()">
		
    <table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanCustomerContGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
	<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage3.firstPage();"> 
    <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage3.previousPage();"> 					
    <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage3.nextPage();"> 
    <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage3.lastPage();"> 			
   </div>
   	
  </form>
  <Br /><Br /><Br /><Br /><Br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
