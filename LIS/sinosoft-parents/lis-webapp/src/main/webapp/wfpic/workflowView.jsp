<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<!--
@author:lk
@date:20080904 
-->

<HTML xmlns:v = "urn:schemas-microsoft-com:vml">
<HEAD>

   <TITLE>WorkFlow 1.0</TITLE>
   
   <META http-equiv=Content-Type content="text/html; charset=gb2312">
   <LINK href="workflow.css" type=text/css rel=stylesheet>  
   <LINK href="../common/css/Project.css" type=text/css rel=stylesheet>    
   <STYLE>v\:* {BEHAVIOR: url(#default#VML)}</STYLE>
<style type="text/css">
#divTip {
	display: block;
	position: absolute;
	height: 100%;
	width: 100%;
	padding-top: 10%;
	z-index: 1001;
}

</style>   
   <script>
   </script>
   <SCRIPT language=VBScript>
    '判断是否取整的数是否小余 0 ，为0则 加1
   
   		Function FormatNumberDemo(MyNumber) 
   			if (isNaN(parseInt(FormatNumber(MyNumber,0)))) then
   				FormatNumberDemo = FormatNumber(MyNumber+1,0) ' 把 MySecant 格式化为不带小数点的数。
   				exit Function
   			end if
   			FormatNumberDemo = FormatNumber(MyNumber,0) ' 把 MySecant 格式化为不带小数点的数。
   		End Function    
    
   </SCRIPT>
   
   <SCRIPT language=javascript src="workflow.js"></SCRIPT>
   <SCRIPT language=javascript src="other.js"></SCRIPT>  

   <script language=jscript src="active.js"></script>
   <script language=jscript src="data.js"></script>
   <script language=jscript src="comp/function.js"></script>
   
   <META content="MSHTML 6.00.2900.3243" name=GENERATOR>

</HEAD>

<BODY onselectstart="javascript:return false;"  onload="">
 <INPUT TYPE="hidden" name="FlowXML" value="">
<span style="color:red">点击节点查看开始,结束时间,执行人</span>
     	          <TABLE height="100%" width="100%" border=0 cellSpacing=0 cellPadding=0 >
	                <TR>            
	                	  <TD height="100%" width="100%" >   
	             
                         <v:group class=WorkFlowGroup id=chart style="WIDTH: 800px; POSITION: relative; HEIGHT: 500px; v-text-anchor: middle-center"  coordsize = "800,500"  onmouseup="ShowNodeHis()"  onselectstart="javascript:return false;" ondragstart="javascript:return false;">
                        
                           
                         </v:group>
                  
                       </TD>
                   </TR>
                </TABLE>
                 

<SCRIPT LANGUAGE="JavaScript">
   function showTip()
   {
      divTip.style.display="block";
   }
   function hideTip()
   {
      divTip.style.display="none";
   }
</SCRIPT> 

<div id="divTip"  style="left: 0px;top: 0px; display:none">
   <table width="50%" border="0" cellpadding="3" cellspacing="1"  
         style="background: #ff7300; position:static;filter:progid:DXImageTransform.Microsoft.DropShadow(color=#666666,offX=4,offY=4,positives=true)" 
         align="center">
     <tr style="cursor: move;">
       <td><font color="#FFFFFF">温馨提示：</font></td>
       <td align="right"><span class="navPoint" onClick="document.getElementById('divTip').style.display='none'"; title="关闭">r</span></td>
     </tr>
     <tr>
       <td colspan="2" width="100%" bgcolor="#FFFFFF" height="150" align="middle"><span id="Tip"></span></td>
     </tr>
   </table>
</div>
    
<SCRIPT LANGUAGE="JavaScript">
   function showHis(x1,y1)
   {  
      divHis.style.left=x1;
      divHis.style.top=y1;
      divHis.style.display="block";
   }
   function hideHis()
   {
      divHis.style.display="none";
   }
</SCRIPT> 

<span id="divHis"  style="display: none; position:absolute; slategray">
   <table border="0" cellpadding="3" cellspacing="1"  
         style="background: #ff7300; position:static;filter:progid:DXImageTransform.Microsoft.DropShadow(color=#666666,offX=4,offY=4,positives=true)" 
         >
     <tr style="cursor: move;">
       <td><font color="#FFFFFF">操作轨迹：</font></td>
       <td align="right"><span class="navPoint" onClick="document.getElementById('divHis').style.display='none'"; title="关闭">r</span></td>
     </tr>
     <tr>
       <td colspan="2" width="100%" bgcolor="#FFFFFF" align="middle"><span id="His"></span></td>
     </tr>
   </table>
</span>    
    
</BODY>
</HTML>
