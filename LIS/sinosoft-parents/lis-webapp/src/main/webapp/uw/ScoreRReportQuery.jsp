<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//�������ƣ�ScoreRReportQuery.jsp
//�����ܣ��������ֻ���
//�������ڣ�ln
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="ScoreRReportQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�µ��˱��ȴ�</title>
  <%@include file="ScoreRReportQueryInit.jsp"%>
<script language="javascript">
  	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>  
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <div id="divSearch">
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
		<td class= titleImg align= center>�������ѯ������</td>
	</tr>
    </table>
   <div id="divInvAssBuildInfo">
       <div class="maxbox1" > 
    <table  class= common align=center>
      	<tr  class= common>
          <td  class= title5>ӡˢ��</TD>
          <td  class= input5> <Input class="common wid" id="PrtNo" name=PrtNo> </TD>
          </TR>
        <tr  class= common>  
          <td  class= title5> �������  </TD>
          <td  class= input5>  <Input class="codeno" name=ManageCom id=ManageCom
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('station',[this,ManageComName],[0,1]);" 
           onDblClick="showCodeList('station',[this,ManageComName],[0,1]);" 
           onKeyUp="showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName id=ManageComName readonly=true>  </TD>
          <td  class= title5>  �˱�ʦ����  </TD>
          <td  class= input5>   <Input class="common wid" id="AssessOperator" name=AssessOperator >   </TD>
            </TR>
        <tr  class= common>                      
          <td  class= title5>  ����Ա���� </TD>
          <td  class= input5> <Input class="common wid" id="Name" name=Name > </TD>
          <td  class= title5> ����Ա����  </TD>
          <td  class= input5>  <Input class="common wid" id="CustomerNo" name=CustomerNo >  </TD>
        </TR>
        <tr  class= common>
          <TD  class= title5 >��ʼ���� </TD>
       		<TD  class= input5 >  
            <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short" name=StartDate id=StartDate><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
             src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
       		<TD  class= title5 >��ֹ����  	</TD>
       		<TD  class= input5 >
            <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EndDate'});"dateFormat="short" name=EndDate id=EndDate><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 
             src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>         
    </table>
    </div>
    </div>
          <!--<INPUT VALUE="��  ѯ" class=cssButton TYPE=button onClick="easyQueryClick();">-->
          <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
    </div>
  <table>
      <tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 ����������Ϣ
    		</td>
      </tr>
  </table>
  <Div  id= "divLCPol2" style= "display: ''" >
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanScoreRReportGrid">
  					</span>
  			  	</td>
  			</tr>
      </table>  
    	<div align=center>
    	<input CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
      <input CLASS=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
      <input CLASS=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
      <input CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
    </div>
    <p></p> 
	
    <!--<a href="javascript:void(0);" class="button"onClick="ScoreQuery();">�������ֱ�</a>
    <a href="javascript:void(0);" class="button"onClick="showPolDetail();">Ͷ������ϸ��ѯ</a>
    <a href="javascript:void(0);" class="button"onClick="ScanQuery();">Ӱ�����ϲ�ѯ </a>-->
    <INPUT VALUE=" �������ֱ� " class=cssButton TYPE=button id="Button1" name="Button1" onClick="ScoreQuery();">
    <INPUT VALUE=" Ͷ������ϸ��ѯ " class=cssButton TYPE=button id="Button2" name="Button2" onClick="showPolDetail();">
    <INPUT VALUE="  Ӱ�����ϲ�ѯ  " class=cssButton TYPE=button id="Button3" name="Button3" onClick="ScanQuery();">
 
  </Div>	  
  <p> 
    <!--��ȡ��Ϣ-->
    <input type= "hidden" id="ContNoH" name= "ContNoH" value= "">
    <input type= "hidden" id="PrtNoH" name= "PrtNoH" value= "">
  </p>
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
