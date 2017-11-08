<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="GorupScanDetil.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<title>团险扫描清单</title>
</head>
<body onLoad="initForm();">
<form action="./GorupScanDetilSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table class=common border=0 width=100%>
	<tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivFileDownload);">
            </TD>
		<td class=titleImg align=center>团险扫描清单</td>
	</tr>
</table>
<Div id=DivFileDownload style="display: 'none'"><A id=fileUrl
	href=""></A></Div>
    <div class="maxbox1" >
 <table  class= common align=center>
      	<TR  class= common> 
      	  <TD  class= title5>
            管理机构
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=ManageCom  id=ManageCom 
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this],null,null,null,null,1);"
            onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);"
             onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);"> 
          </TD>    
           <TD  class= title5>
            印刷号
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PrtNO >         
 
          </TD>    
        </TR>     
        
           
        <tr>
          <TD  class= title5 >
           投保单扫描开始日期
          </TD>
          <TD  class= input5 >
            
            <input class="coolDatePicker" dateFormat="short" id="StartDay"  name="StartDay" onClick="laydate
({elem:'#StartDay'});" > <span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>      
          <TD  class= title5 >
           投保单扫描结束日期
          </TD>
          <TD  class= input5>
           
             <input class="coolDatePicker" dateFormat="short" id="EndDay"  name="EndDay" onClick="laydate({elem:'#EndDay'});"> <span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

          </TD>        
        </TR>
        
        	<TR  class= common>      
          
	   	   <TD  class= title5>
            签批状态
           </TD>
           <TD  class= input5>         
	           <Input class="common wid" name=tjtype  id=tjtype 
                CodeData="0|^0|签发保单未成功^1|签发保单成功" 
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('tjtype',[this],[0]);" 
                onDblClick="showCodeListEx('tjtype',[this],[0]);" 
                 onkeyup="showCodeListKeyEx('tjtype',[this],[0]);">
           </TD>   
           
         
	   	
	   	    
        </TR>   
 
   </table>
   </div></div>
   
<!-- 确认对话框 初始化为0 点确认框的确定修改为1 计算完毕后重新初始话为0 --> 
<INPUT VALUE="0" TYPE=hidden name=myconfirm> 
<INPUT VALUE="" TYPE=hidden name=FileName> 
<INPUT VALUE="" TYPE=hidden name=Url> 
<input type=hidden id="fmtransact" name="fmtransact"> 
<br>
<!--<center>
<INPUT VALUE="生成Excel" class=common name=compute TYPE=button onClick="return fmsubmit()"> &nbsp;&nbsp;&nbsp;
<INPUT VALUE="下载Excel" class=common name=download TYPE=button onClick="fileDownload()">
</center>-->
<a href="javascript:void(0);" class="button"onClick="return fmsubmit()">生成Excel</a>
<a href="javascript:void(0);" class="button"onClick="fileDownload()">下载Excel</a>

</form>
<span id="spanCode" style="display: none; position: absolute;"></span>
<script>
function initForm()
{
 document.all('ManageCom').value = "";
 document.all('PrtNO').value = "";
 document.all('StartDay').value = "";
 document.all('EndDay').value = "";
 document.all('tjtype').value="";
 

}

</script>
</body>
</html>
