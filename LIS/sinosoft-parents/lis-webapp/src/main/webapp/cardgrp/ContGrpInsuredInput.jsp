<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html> 

<head >
<script>
	var prtNo ="<%=request.getParameter("prtNo")%>";
  var polNo ="<%=request.getParameter("polNo")%>";
  var scantype ="<%=request.getParameter("scantype")%>";
  var MissionID ="<%=request.getParameter("MissionID")%>";
  var ManageCom ="<%=request.getParameter("ManageCom")%>";
  var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
  var ActivityID = "<%=request.getParameter("ActivityID")%>";
  var NoType = "<%=request.getParameter("NoType")%>";
  var GrpContNo ="<%=request.getParameter("GrpContNo")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var BQFlag = "<%=request.getParameter("BQFlag")%>";
	if (BQFlag == null||BQFlag == "null") BQFlag = "0";
	var LoadFlag = "<%=request.getParameter("LoadFlag")%>";
	if (LoadFlag == null||LoadFlag == "null") BQFlag = "0";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="ContGrpInsuredInput.js"></SCRIPT>
  <%@include file="ContGrpInsuredInit.jsp"%>
  <script>
	var turnPage = new turnPageClass(); 
</script>
  <title>�����˲�ѯ��Ϣ </title>
</head>

<body  onload="initForm();" >
  <form action="" method=post name=fm target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
        <A id=fileUrl href=""></A>
      <table>
    	<tr>
        <td class=common>
			  <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>
    			�������ѯ����������
    		</td>
    	</tr>
    </table>
      <Div  id= "divGroupPol1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
	 
          <TD  class= title8>
            �������κ�
          </TD>
          <TD  class= input8>
            <Input class=common8 name=ProposalGrpContNo readonly>
          </TD>
          <TD  class= title8>
            �������
          </TD>
          <TD  class= input8>
            <Input class="code8" name=ManageCom >
          </TD>
          <TD class= title>
          �������˿ͻ���
        </TD>
        <TD class= input8>
          <Input class= common8 name=InsuredNo>
        </TD>
      </TR>
      
    <table class= common>
      <TR class= common>      
        
        <TD class= title8>
          ����
        </TD>
        <TD class= input8>
          <Input class= common8 name=Name onblur=" trimname();">
        </TD>
        <TD class= title8>
          ֤������
        </TD>
        <TD class= input8>
          <Input class= common8 name=IDNo>
        </TD>
        <TD class= title8>
          ���ϼ���
        </TD>
        <TD class= input8>
          <Input class= common8 name=ContPlanCode>
        </TD>
      </TR>
      
      <TR>
        <TD>
          <INPUT class=cssButton  value="��  ѯ" onclick="queryperinsure();" type=button>
        </TD>
       <TD>
          <!--INPUT VALUE="�����ŵ��±�������ϢExcel"  class = cssButton TYPE=button onclick="contGrpInsuredCreateExcel();"-->
         </TD>
        <TD>
        <INPUT VALUE="" TYPE=hidden name=querySql >
        </TD>
        <TD>
        <INPUT VALUE="<%=request.getParameter("prtNo")%>" TYPE=hidden name=GrpContNo >
         <TD>
      </TR>
    </table>
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPersonInsured);">
    		</td>
    		<td class="titleImg">��������Ϣ
  			</td>
    	</tr>
    </table>
	<Div  id= "divPersonInsured" style= "display: ''">
    	<table >
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanPersonInsuredGrid" >
					</span> 
				</td>
			</tr>
       </table>
	</div>
	
  <Div id= "divPage" align=center style= "display: '' ">
  
  <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
  <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
  <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
  <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
  </Div>
  <center>
  <Div  id= "divSaveButton" style= "display: 'none'" >
   <hr/>            
   <INPUT type =button class=cssButton value="�� һ ��" onclick="returnparent();">   
   <INPUT class=cssButton id="pisdbutton2" VALUE="���汻�����嵥" TYPE=button onclick="saveinsuinfo();"> 
   <INPUT class=cssButton id="pisdbutton2" VALUE="���ر������嵥" TYPE=button onclick="filedownload();"> 
   
  <hr/>
  </DIV>
   <Div  id= "divSaveInsuredButton" style= "display: ''" >
   <hr/>
   <INPUT type =button class=cssButton value="�� һ ��" onclick="returnparent();">      
   <INPUT class=cssButton id="pisdbutton3"  VALUE="��ӱ�����"  TYPE=button onclick="getintopersoninsured();">    
   <INPUT class=cssButton id="pisdbutton1" VALUE="���뱻�����嵥" TYPE=button onclick="getin();"> 
   <INPUT class=cssButton id="pisdbutton2" VALUE="ɾ�����б�����" TYPE=button onclick="getout();"> 
   <INPUT class=cssButton id="pisdbutton2" VALUE="���ƻ�ɾ��" TYPE=button onclick="getcontplan();"> 
   <INPUT class=cssButton id="pisdbutton2" VALUE="���汻�����嵥" TYPE=button onclick="saveinsuinfo();"> 
   <INPUT class=cssButton id="pisdbutton2" VALUE="���ر������嵥" TYPE=button onclick="filedownload();"> 
   <hr/>  
   <a href="./download1.xls">���̵���ģ��</a> &nbsp;<!--a href="./download2.xls">���յ���ģ��</a> &nbsp;<a href="./download3.xls">���ܵ���ģ��</a> &nbsp;<a href="./download4.xls">����606����ģ��</a-->
   <hr/>  
   <a href="./explain.doc">���ش��̵���˵����doc�ļ���</a>          
   </DIV>

  </center>    	
   <INPUT VALUE="" TYPE=hidden name=FileName>
   <INPUT VALUE="" TYPE=hidden name=Url>		
   <input type=hidden id="fmtransact" name="fmtransact">
   <INPUT VALUE="0" TYPE=hidden name=myconfirm >
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
     
</body>
</html>
