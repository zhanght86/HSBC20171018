<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuHealth.jsp
//�����ܣ��б��˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./GrpUWManuHealthQ.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpUWManuHealthQInit.jsp"%>
  <title> �б��������¼�� </title>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./GrpUWManuHealthChk.jsp">
    <!-- ���б� -->
    <table>
    	<TR  class= common>
          <TD  class= title>  ��ͬ����  </TD>
          <TD  class= input> <Input class="readonly" name=ContNo > </TD>
           <INPUT  type= "hidden" class= Common name= MissionID value= ""><!-- ������������� -->
           <INPUT  type= "hidden" class= Common name= SubMissionID value= "">
           <INPUT  type= "hidden" class= Common name= PrtNo value= "">
           <INPUT  type= "hidden" class= Common name= PrtSeq value= "">
          <TD  class= title>  �����  </TD>
          <TD  class= input> <Input class=code name=InsureNo ondblClick="showCodeListEx('InsureNo',[this,''],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('InsureNo',[this,''],[0,1],null,null,null,1);" onFocus= "easyQueryClickSingle();"> <!-- onFocus= "easyQueryClickSingle();easyQueryClick();"--> </TD>
        </TR>
        
    </table>
    
        <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainUWSpec1);"></td>
    		<td class= titleImg>	 �������</td>                            
    	</tr>	
    </table>
    <Div  id= "divMainUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanMainHealthGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
      </div>
      
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>	 �����Ŀ</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanHealthGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
    <table>
    	<tr>
        	<td class=common>    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);"></td>
    		<td class= titleImg>   �������</td>                            
    	</tr>	
    </table>
    <Div  id= "divUWDis" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  				<span id="spanDisDesbGrid">
  				</span> 
  		  	</td>
  		</tr>
    	</table>
      </div>
     
    	<table class=common>
         <TR  class= common> 
           <TD  class= common> ���������Ϣ </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Note" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
	<input value="���������" class=cssButton type=button onclick="saveDisDesb();" > 
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
