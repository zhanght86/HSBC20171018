<html>
<%@page contentType="text/html;charset=GB2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK"> 
<%
     GlobalInput tG = new GlobalInput();
     tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
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
  <title>打印批单/申请单修改 </title>
</head>
<body onload="initForm();">
<form action="./ChangeEdorPrintSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
    <table>
      <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor1);">
      </td>
      <td class= titleImg>
        请您输入查询条件：
      </td>
    	</tr>
    </table>
    <Div  id= "divLPEdor1" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR class=common>  
          <TD  class= title5>
            合同号
          </TD>
          <TD  class= input5>
            <Input class="wid common" id=ContNo name=ContNo >
          </TD>
          <TD  class= title5>
            批改类型
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=EdorType id=EdorType >
          </TD> 
        </TR>
        <TR class=common>  
          <TD  class= title5>
            保全申请单号
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=EdorAppNo id=EdorAppNo >
          </TD>          
          <TD  class= title5>
            保全批单号
          </TD>
          <TD  class= input5>
            <Input class="wid common" id=EdorNo name=EdorNo >
          </TD>
        </TR>
      </table>
    <INPUT VALUE="查询" TYPE=button class=cssButton onclick="easyQueryClick();">
    </Div>
    
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdor2);">
    		</td>
    		<td class= titleImg>
    			 集体批改信息
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
    <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="getFirstPage();"> 
    <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="getPreviousPage();"> 					
    <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="getNextPage();"> 
    <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="getLastPage();"> 
    </div>
  	<table class=common>
  	 <TR class= common>
         	<TD  class= title5>
         	   输入导出/导入的文件名
         	</TD>
         	<TD  class= input5>
         	   <Input class="wid common" id=FileName name= FileName>
         	</TD>
         	<TD  class= input > 
  			<INPUT class =cssButton VALUE="导出数据" TYPE=button onclick="getbqPrintToXML();"> 
        	</TD>
         </TR>
         <TR class= common>	
         	<TD  class= title5>
         	   输入导入的批单/申请单号码
         	</TD>
         	<TD  class= input5>
         	   <Input class="wid common" id=EdorNo1 name= EdorNo1>
         	</TD>
         	<TD  class= input5 > 
  			<INPUT class =cssButton VALUE="导入数据" TYPE=button onclick="setXMLTobqPrint();"> 
        	</TD>        
        </TR>
<!--        <TR>	
        	<TD class= input>
            		 <INPUT class = common VALUE="返回" TYPE=button onclick="returnParent();"> 
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
             <INPUT class=cssButton VALUE="导入页面数据" TYPE=button onclick="setXMLTobqPrint2();"!----> 
          </TD>       
       </TR>
     </table>
     <br /><br /><br /><br />
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
