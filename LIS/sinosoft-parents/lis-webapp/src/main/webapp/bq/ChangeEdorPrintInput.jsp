<html>
<%@page contentType="text/html;charset=GB2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK"> 
<%
     GlobalInput tG = new GlobalInput();
     tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
	//String strEdorNo = request.getParameter("EdorNo"); 
	//if (strEdorNo == null)	    strEdorNo = "";
	//String strContNo = request.getParameter("ContNo"); 
	//if (strContNo == null)	    strContNo = "";
	//loggerDebug("ChangeEdorPrintInput","strContNo"+strContNo);
  	%>  
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  
  <SCRIPT src="ChangeEdorPrint.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ChangeEdorPrintInit.jsp"%>
  <title>��ӡ����/���뵥�޸� </title>
</head>
<body onload="initForm();">
<form action="./ChangeEdorPrintSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
    <table>
      <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor1);">
      </td>
      <td class= titleImg>
        ���������ѯ������
      </td>
    	</tr>
    </table>
    <Div  id= "divLPEdor1" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR class=common>  
          <TD  class= title5>
            ��ͬ��
          </TD>
          <TD  class= input5>
            <Input class="wid common" id=ContNo name=ContNo >
          </TD>
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=EdorType id=EdorType >
          </TD> 
        </TR>
        <TR class=common>  
          <TD  class= title5>
            ��ȫ���뵥��
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=EdorAppNo id=EdorAppNo >
          </TD>          
          <TD  class= title5>
            ��ȫ������
          </TD>
          <TD  class= input5>
            <Input class="wid common" id=EdorNo name=EdorNo >
          </TD>
        </TR>
      </table>
    <INPUT VALUE="��ѯ" TYPE=button class=cssButton onclick="easyQueryClick();">
    </Div>
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor2);">
    		</td>
    		<td class= titleImg>
    			 ����������Ϣ
    		</td>
    	</tr>
    </table>
    <Div  id= "divLPEdor2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	   <td text-align: left colSpan=1>
  			<span id="spanEdorPrintGrid" >
  			</span> 
  		   </td>
  		</tr>
    </table>
    <input type=hidden name=Transact >
    <INPUT CLASS=cssButton90 VALUE="��ҳ" TYPE=button onclick="getFirstPage();"> 
    <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="getPreviousPage();"> 					
    <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="getNextPage();"> 
    <INPUT CLASS=cssButton93 VALUE="βҳ" TYPE=button onclick="getLastPage();"> 
    </div>
  	<table class=common>
  	 <TR class= common>
         	<TD  class= title5>
         	   ���뵼��/������ļ���
         	</TD>
         	<TD  class= input5>
         	   <Input class="wid common" id=FileName name= FileName>
         	</TD>
         	<TD  class= input > 
  			<INPUT class =cssButton VALUE="��������" TYPE=button onclick="getbqPrintToXML();"> 
        	</TD>
         </TR>
         <TR class= common>	
         	<TD  class= title5>
         	   ���뵼�������/���뵥����
         	</TD>
         	<TD  class= input5>
         	   <Input class="wid common" id=EdorNo1 name= EdorNo1>
         	</TD>
         	<TD  class= input5 > 
  			<INPUT class =cssButton VALUE="��������" TYPE=button onclick="setXMLTobqPrint();"> 
        	</TD>        
        </TR>
<!--        <TR>	
        	<TD class= input>
            		 <INPUT class = common VALUE="����" TYPE=button onclick="returnParent();"> 
        	</TD>
        </TR>		-->
     </table>
     
     <table class=common>
  	   <TR class= common>
         	<TD  class= title>
         	   XML
         	</TD>
         	<TD  class= input>
         	   <textarea rows="20" name="EdorXml" id=EdorXml cols="100%"></textarea>
         	</TD>
       </TR>
       <TR class= common>	
          <TD  class= title>
             <INPUT class=cssButton VALUE="����ҳ������" TYPE=button onclick="setXMLTobqPrint2();"!----> 
          </TD>       
       </TR>
     </table>
     <br /><br /><br /><br />
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
