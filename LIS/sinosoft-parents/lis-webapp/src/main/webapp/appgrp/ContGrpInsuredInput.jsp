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
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="ContGrpInsuredInput.js"></SCRIPT>
  <%@include file="ContGrpInsuredInit.jsp"%>
  <script>
	var turnPage = new turnPageClass(); 
</script>
  <title>�����˲�ѯ��Ϣ </title>
</head>

<body  onload="initForm();" >
  <form action="" method=post name=fm id="fm" target="fraTitle">
    <%@include file="../common/jsp/InputButton.jsp"%>
        <A id=fileUrl href=""></A>
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>�������ѯ����������</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divGroupPol1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>Ͷ��������</TD>
          <TD  class= input>
            <Input class="common wid" name=ProposalGrpContNo id="ProposalGrpContNo" readonly>
          </TD>
          <TD  class= title>�������</TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom id="ManageCom">
          </TD>
          <TD class= title>�������˿ͻ���</TD>
          <TD class= input>
            <Input class="common wid" name=InsuredNo id="InsuredNo">
          </TD>
        </TR>
        <TR class= common>      
          <TD class= title>����</TD>
          <TD class= input>
            <Input class="common wid" name=Name id="Name" onblur=" trimname();">
          </TD>
          <TD class= title>֤������</TD>
          <TD class= input>
            <Input class="common wid" name=IDNo id="IDNo">
          </TD>
          <TD class= title>���ϼ���</TD>
          <TD class= input>
            <Input class="common wid" name=ContPlanCode id="ContPlanCode">
          </TD>
        </TR>
      </table>
    </Div>
    </div>
    <a href="javascript:void(0)" class=button onclick="queryperinsure();">��  ѯ</a>
    <!-- <INPUT class=cssButton  value="��  ѯ" onclick="queryperinsure();" type=button> -->
    <INPUT VALUE="" TYPE=hidden name=querySql id="querySql">
    <INPUT VALUE="<%=request.getParameter("prtNo")%>" TYPE=hidden name=GrpContNo >
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPersonInsured);">
    		</td>
    		<td class="titleImg">��������Ϣ</td>
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
    </Div>
    <Div  align=center style= "display: none ">
      <INPUT CLASS=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
    </Div>
    <Div  id= "divSaveButton" style= "display: none" >
      <!-- <hr class="line"/> -->
      <br>  
      <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>          
      <a href="javascript:void(0)" id="pisdbutton2" class=button onclick="saveinsuinfo();">���汻�����嵥</a>          
      <a href="javascript:void(0)" id="pisdbutton2" class=button onclick="filedownload();">���ر������嵥</a>          
      <!-- <INPUT type =button class=cssButton value="��һ��" onclick="returnparent();">   
      <INPUT class=cssButton id="pisdbutton2" VALUE="���汻�����嵥" TYPE=button onclick="saveinsuinfo();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="���ر������嵥" TYPE=button onclick="filedownload();"> -->
        <br>
    </Div>
    <Div  id= "divSaveInsuredButton" style= "display: 'none'" >
      <hr class="line"/>
      <br>
      <div>
      <a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
      <a href="javascript:void(0)" class=button id="pisdbutton3" onclick="getintopersoninsured();">��ӱ�����</a>
      <a href="javascript:void(0)" class=button id="pisdbutton1" onclick="getin();">���뱻�����嵥</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="getout();">ɾ�����б�����</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="getcontplan();">���ƻ�ɾ��</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="saveinsuinfo();">���汻�����嵥</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="filedownload();">���ر������嵥</a>
      </div>
      <!-- <INPUT type =button class=cssButton value="��һ��" onclick="returnparent();">      
      <INPUT class=cssButton id="pisdbutton3"  VALUE="��ӱ�����"  TYPE=button onclick="getintopersoninsured();">    
      <INPUT class=cssButton id="pisdbutton1" VALUE="���뱻�����嵥" TYPE=button onclick="getin();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="ɾ�����б�����" TYPE=button onclick="getout();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="���ƻ�ɾ��" TYPE=button onclick="getcontplan();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="���汻�����嵥" TYPE=button onclick="saveinsuinfo();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="���ر������嵥" TYPE=button onclick="filedownload();"> --> 
      <br>  
      <div>
        <a href="./download1.xls">���̵���ģ��</a> &nbsp;<!--a href="./download2.xls">���յ���ģ��</a> &nbsp;<a href="./download3.xls">���ܵ���ģ��</a> &nbsp;<a href="./download4.xls">����606����ģ��</a-->
      <hr class="line"/>  
      <a href="./explain.doc">���ش��̵���˵����doc�ļ���</a>
      </div>
                
    </Div>   	
  <INPUT VALUE="" TYPE=hidden name=FileName id="FileName">
  <INPUT VALUE="" TYPE=hidden name=Url id="Url">		
  <input type=hidden id="fmtransact" name="fmtransact">
  <INPUT VALUE="0" TYPE=hidden name=myconfirm id="myconfirm">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <script>changecolor(); </script>
</body>
</html>
