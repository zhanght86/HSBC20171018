<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<% 
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-12-20
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //��¼�������
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="GetCredenceInput.js"></SCRIPT> 
  <%@include file="GetCredenceInit.jsp"%>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
	<title>����ƾ֤��ӡ</title>
</head>

<body  onload="initForm();" >    
  <form  method=post name=fm  id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr> 
    		<td class=common  width=2% >
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGetCre);">
    		</td>
    		<td class= titleImg align=left>
        		�����ѯ����
       	</td>   		      
    	</tr>
    </table>
    <Div  id= "divGetCre" style= "display: ''">
      <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>  ʵ������  </TD>
          <TD  class= input5>  <Input class="common wid" name=ActuGetNo >  </TD>
          <TD  class= title5>  �������� </TD>
          <TD  class= input5> <Input class="common wid"name=OtherNo >  </TD>
            </TR>
        <TR  class= common>
          <TD  class= title5>  ������������ </TD>
          <!--TD  class= input>	<Input class=code name=OtherNoType verify="������������" CodeData="0|^0|������ȡ��ͬ��^1|������ȡ���屣����^2|������ȡ���˱�����^3|���ĺ�^4|�ݽ����˷Ѹ���֪ͨ���^5|�⸶Ӧ�ո���֪ͨ���^6|(����)�����˷Ѹ���֪ͨ���^7|�������˱�����^8|(�ŵ�)�����˷Ѹ���֪ͨ���" ondblClick="showCodeListEx('OtherNoType',[this]);" onkeyup="showCodeListKeyEx('OtherNoType',[this]);">            </TD-->          
          <TD  class= input5>	<Input class=code name=OtherNoType verify="������������"
           CodeData="0|^1|�ͻ���^2|������ȡ��Ӧ�ĺ�ͬ��^4|�ݽ����˷Ѹ���֪ͨ���^5|�⸶Ӧ�ո���֪ͨ���^6|�罻�˷Ѷ�Ӧ�ĺ�ͬ��^7|����������Ӧ�ĺ�ͬ��^9|���ڻ��˶�Ӧ�ĺ�ͬ��^10|��ȫ��Ӧ�����ĺ�" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeListEx('OtherNoType',[this]);" 
           ondblClick="showCodeListEx('OtherNoType',[this]);" 
           onKeyUp="showCodeListKeyEx('OtherNoType',[this]);" readonly>            </TD>          
        
          <TD  class= title5> Ӧ������  </TD>
          <TD  class= input5>
          <input class="coolDatePicker" dateFormat="short" id="ShouldDate"  name="ShouldDate" onClick="laydate
({elem:'#ShouldDate'});" > <span class="icon"><a onClick="laydate({elem: '#ShouldDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
            </TR>
        <TR  class= common>
          <TD  class= title5> ������� </TD>
          <TD  class= input5><Input class="common wid"name=MngCom
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this]);" 
           onDblClick="return showCodeList('comcode',[this]);"
            onKeyUp="return showCodeListKey('comcode',[this],null,null,null,null,1);" verify="�������|code:comcode" readonly> </TD>
          <TD  class= title5> �����˱���  </TD> 
          <TD  class= input5> <Input class="common wid" name=AgentCode verify="�����˱���|code:AgentCode"
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return queryAgent(comcode);"
           onDblClick="return queryAgent(comcode);"
            onKeyUp="return queryAgent(comcode);" readonly></TD> 
        </TR>
    </table>
    </div></Div>
   
    	<!--<INPUT VALUE="��  ѯ" class= cssButton TYPE=button onClick="easyQueryClick();"> -->
        <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGet1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAGet1" style= "display: ''">
      <table class= common>
        <TR  class= common>
					<td text-align: left colSpan=1>
		  			<span id="spanPolGrid" >
		  			</span> 
		  		</td>	
				</TR>    	 
      </table>
      <div align=center >
		      <INPUT VALUE="��  ҳ" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
		      <INPUT VALUE="��һҳ" class= cssButton91  TYPE=button onClick="turnPage.previousPage();"> 					
		      <INPUT VALUE="��һҳ" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
		      <INPUT VALUE="β  ҳ" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">				
		
	  </div>  
	 
   <!-- <table align=right>
      <tr>
        <td>	 
	  <input type=Button class= cssButton name="GetPrint" value="����ƾ֤��ӡ" onClick="GPrint()"> 
	</td>
      </tr>
    </table>		-->
     <a href="javascript:void(0);" class="button"onClick="GPrint()">����ƾ֤��ӡ</a>
    </Div>
    <br><br><br><br>
        <input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
