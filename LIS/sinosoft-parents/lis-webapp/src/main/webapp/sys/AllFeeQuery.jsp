<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
 <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="AllFeeQuery.js"></SCRIPT>
  <%@include file="AllFeeQueryInit.jsp"%>
  <title>���Ѳ�ѯ </title>
</head>
<body  onload="initForm();initElementtype();" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- ������Ϣ���� -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>�����뽻�Ѳ�ѯ������</td>
		</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5> �����վݺ��� </TD>
          <TD  class= input5> <Input class="common wid" name=PayNo > </TD>
          <TD  class= title5>  Ӧ��/ʵ�ձ��</TD>
          <TD  class= input5>  <Input class="common wid" name=IncomeNo > </TD>
           </TR>
           	<TR  class= common>
          <TD  class= title5> Ӧ��/ʵ�ձ������ </TD>
         <TD  class= input5><Input  class="common wid" name=IncomeType verify="Ӧ��/ʵ�ձ������" 
          CodeData="0|^1|�����ͬ��^2|���˺�ͬ��^3|��ͥ�����ͬ��^5|�����ⰸ��^10|��ȫ���ĺ�"
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="showCodeListEx('FeeIncomeType',[this],[0,1,2,3]);"  
          ondblClick="showCodeListEx('FeeIncomeType',[this],[0,1,2,3]);"
           onKeyUp="showCodeListKeyEx('FeeIncomeType',[this],[0,1,2,3]);">           </TD>          
          <TD  class= title5> �������� </TD>
          <TD  class= input5>	
          <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#PayDate'});"dateFormat="short" name=PayDate id=PayDate><span class="icon"><a onClick="laydate({elem: '#PayDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
           </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5> ������� </TD>
          <TD  class= input5><Input  class="common wid" name=MngCom verify="�������|code:comcode" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('comcode',[this],null,null,null,null,1);"
          onDblClick="return showCodeList('comcode',[this],null,null,null,null,1);"
           onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" readonly> </TD>
          <TD  class= title5> �����˱���  </TD> 
          <TD  class= input5> <Input  class="common wid" name=AgentCode verify="�����˱���|notnull" onDblClick="queryAgent()" > </TD>
        </TR>
    </table>
    </div>
    </div>
    <a href="javascript:void(0);" class="button" id="feequery" onClick="easyQueryClick();">��   ѯ</a>
    <a href="javascript:void(0);" class="button" onClick="getQueryDetail();">������ϸ</a>

         <!-- <INPUT VALUE="��  ѯ" class= cssButton TYPE=button name="feequery" onClick="easyQueryClick();"> 
          <INPUT VALUE="������ϸ" class= cssButton TYPE=button onClick="getQueryDetail();"> 				-->	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPay1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
     
      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class= cssButton91  TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
