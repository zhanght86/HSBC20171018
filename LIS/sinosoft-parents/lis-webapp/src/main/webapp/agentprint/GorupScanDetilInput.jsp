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
<title>����ɨ���嵥</title>
</head>
<body onLoad="initForm();">
<form action="./GorupScanDetilSave.jsp" method=post name=fm id=fm
	target="fraSubmit">
<table class=common border=0 width=100%>
	<tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivFileDownload);">
            </TD>
		<td class=titleImg align=center>����ɨ���嵥</td>
	</tr>
</table>
<Div id=DivFileDownload style="display: 'none'"><A id=fileUrl
	href=""></A></Div>
    <div class="maxbox1" >
 <table  class= common align=center>
      	<TR  class= common> 
      	  <TD  class= title5>
            �������
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=ManageCom  id=ManageCom 
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this],null,null,null,null,1);"
            onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);"
             onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);"> 
          </TD>    
           <TD  class= title5>
            ӡˢ��
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PrtNO >         
 
          </TD>    
        </TR>     
        
           
        <tr>
          <TD  class= title5 >
           Ͷ����ɨ�迪ʼ����
          </TD>
          <TD  class= input5 >
            
            <input class="coolDatePicker" dateFormat="short" id="StartDay"  name="StartDay" onClick="laydate
({elem:'#StartDay'});" > <span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>      
          <TD  class= title5 >
           Ͷ����ɨ���������
          </TD>
          <TD  class= input5>
           
             <input class="coolDatePicker" dateFormat="short" id="EndDay"  name="EndDay" onClick="laydate({elem:'#EndDay'});"> <span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

          </TD>        
        </TR>
        
        	<TR  class= common>      
          
	   	   <TD  class= title5>
            ǩ��״̬
           </TD>
           <TD  class= input5>         
	           <Input class="common wid" name=tjtype  id=tjtype 
                CodeData="0|^0|ǩ������δ�ɹ�^1|ǩ�������ɹ�" 
                 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('tjtype',[this],[0]);" 
                onDblClick="showCodeListEx('tjtype',[this],[0]);" 
                 onkeyup="showCodeListKeyEx('tjtype',[this],[0]);">
           </TD>   
           
         
	   	
	   	    
        </TR>   
 
   </table>
   </div></div>
   
<!-- ȷ�϶Ի��� ��ʼ��Ϊ0 ��ȷ�Ͽ��ȷ���޸�Ϊ1 ������Ϻ����³�ʼ��Ϊ0 --> 
<INPUT VALUE="0" TYPE=hidden name=myconfirm> 
<INPUT VALUE="" TYPE=hidden name=FileName> 
<INPUT VALUE="" TYPE=hidden name=Url> 
<input type=hidden id="fmtransact" name="fmtransact"> 
<br>
<!--<center>
<INPUT VALUE="����Excel" class=common name=compute TYPE=button onClick="return fmsubmit()"> &nbsp;&nbsp;&nbsp;
<INPUT VALUE="����Excel" class=common name=download TYPE=button onClick="fileDownload()">
</center>-->
<a href="javascript:void(0);" class="button"onClick="return fmsubmit()">����Excel</a>
<a href="javascript:void(0);" class="button"onClick="fileDownload()">����Excel</a>

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
