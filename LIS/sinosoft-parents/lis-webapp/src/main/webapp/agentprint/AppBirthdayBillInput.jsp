<%@include  file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<script src="../common/easyQueryVer3/EasyQueryCache.js"></script> 
<script src="../common/easyQueryVer3/EasyQueryVer3.js"> </script>   
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>           
<script src="AppBirthdayBill.js"></script>
<link href="../common/css/Project.css"   rel="stylesheet"   type="text/css">
<link href="../common/css/Project3.css"   rel="stylesheet"   type="text/css">
<link href="../common/css/mulLine.css"   rel="stylesheet"   type="text/css">
<%@include file="../agent/SetBranchType.jsp"%>
<%@include file="AppBirthdayBillInit.jsp"%>
<title>过生日客户清单</title>
</head>
<body onLoad="initForm();">
<form action="./AppBirthdayBillPrt.jsp" method="post" name="fm" id="fm" target="fraSubmit" >
   <table border="0"   class="common" width="100%">
   <tr >
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
   <td class="titleImg" align="center" >请输入查询条件：</td>
   </tr>
   </table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
<table border="0" class="common" align="center" >
<tr class="common">
 <td class="title5">管理机构
 </td>
 <td   class="input5" >
 <Input class="common wid"  name=ManageCom  id=ManageCom 
            verify="管理机构|code:comcode&NOTNULL" 
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this],null,null,null,null,1);" 
            ondblclick="return showCodeList('comcode',[this],null,null,null,null,1);" 
            onkeyup="return showCodeListKey('comcode',[this],null,null,null,null,1);">  
 </td>
 <td  class="title5">销售渠道</td>
 <td class="input5">
 <input class="common wid" name="SaleChnl" id="SaleChnl"
  verify="机构类型|NOTNULL" 
  CodeData="0|^1|个险^2|银代^3|中介^4|联办^5|收展"
   style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('SaleChnl',[this],[0,1]);"
     ondblClick="showCodeListEx('SaleChnl',[this],[0,1]);"
   onkeyup="showCodeListKeyEx('SaleChnl',[this],[0,1]);"
  >
 </td>
 </tr>
 <tr class="common">
 <td  class="title5">交费类别</td>
 <td class="input5">
 <input class="common wid" name="PayIntv" id="PayIntv"
  verify="交费类别|NOTNULL" 
  CodeData="0|^1|趸交^2|期交^3|不区分"
  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('PayIntv',[this],[0,1]);"
     ondblClick="showCodeListEx('PayIntv',[this],[0,1]);"
   onkeyup="showCodeListKeyEx('PayIntv',[this],[0,1]);"
  >
 </td>
 <td  class="title5">保单属性</td>
 <td class="input5">
 <input class="common wid" name="PolType" id="PolType"
  verify="保单属性|NOTNULL" 
  CodeData="0|^1|在职单^2|孤儿单^3|不区分"
  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('PolType',[this],[0,1]);"
     ondblClick="showCodeListEx('PolType',[this],[0,1]);"
   onkeyup="showCodeListKeyEx('PolType',[this],[0,1]);"
  >
 </td>
 </tr>
 <tr class="common" >
  <td class="title5" width="34%" > 
  	生日统计起期
  </td>
  <td class="input5">
   <input class="common wid"  name="StartDate" id="StartDate" 
    verify="生日统计起期|NOTNULL&len=5" > 
   <font color="red" >*(例如:06-07)</font>
  </td >
  
  <td class="title5" width="34%" > 
  	生日统计止期
  </td>
  <td class="input5">
   <input class="common wid"   name="EndDate"  id="EndDate" 
    verify="生日统计止期|NOTNULL&len=5" > 
   <font color="red" >*(例如:06-20)</font>
  </td >
</tr>
</table>
</div></div>
<!--<table  class= common>
      <TR class= common> 
		<TD  class= input width="34%">
			<input class= common type=Button value="生成报表" onClick="Polmake();">
		</TD>			
		
		<TD  class= input width="34%">
			<input class= common type=Button value="下载报表" onClick="Poldown();" >
		</TD>	

	   </TR>    
   </table>-->
    <a href="javascript:void(0);" class="button"onClick="Polmake();">生成报表</a>
    <a href="javascript:void(0);" class="button"onClick="Poldown();">下载报表</a>

    <Div id=DivFileDownload style="display:'none'">
      <A id=fileUrl href=""></A>
    </Div>
   <input type=hidden id="Operate" name="Operate">
      <input type=hidden name=GetType value=''>
      	<input type=hidden id="fmtransact" name="fmtransact">
      	<INPUT VALUE="" TYPE=hidden name=FileName>
    	<INPUT VALUE="" TYPE=hidden name=Url>
      
       <input value="" type=hidden name=BranchType >
      
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>
</body>
</html>

