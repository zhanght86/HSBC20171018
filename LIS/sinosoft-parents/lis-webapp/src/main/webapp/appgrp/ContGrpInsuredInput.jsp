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
  <title>被保人查询信息 </title>
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
    		<td class= titleImg>请输入查询被保人条件</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <Div  id= "divGroupPol1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title>投保单号码</TD>
          <TD  class= input>
            <Input class="common wid" name=ProposalGrpContNo id="ProposalGrpContNo" readonly>
          </TD>
          <TD  class= title>管理机构</TD>
          <TD  class= input>
            <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class="code" name=ManageCom id="ManageCom">
          </TD>
          <TD class= title>被保险人客户号</TD>
          <TD class= input>
            <Input class="common wid" name=InsuredNo id="InsuredNo">
          </TD>
        </TR>
        <TR class= common>      
          <TD class= title>姓名</TD>
          <TD class= input>
            <Input class="common wid" name=Name id="Name" onblur=" trimname();">
          </TD>
          <TD class= title>证件号码</TD>
          <TD class= input>
            <Input class="common wid" name=IDNo id="IDNo">
          </TD>
          <TD class= title>保障级别</TD>
          <TD class= input>
            <Input class="common wid" name=ContPlanCode id="ContPlanCode">
          </TD>
        </TR>
      </table>
    </Div>
    </div>
    <a href="javascript:void(0)" class=button onclick="queryperinsure();">查  询</a>
    <!-- <INPUT class=cssButton  value="查  询" onclick="queryperinsure();" type=button> -->
    <INPUT VALUE="" TYPE=hidden name=querySql id="querySql">
    <INPUT VALUE="<%=request.getParameter("prtNo")%>" TYPE=hidden name=GrpContNo >
    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPersonInsured);">
    		</td>
    		<td class="titleImg">被保人信息</td>
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
      <INPUT CLASS=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
    </Div>
    <Div  id= "divSaveButton" style= "display: none" >
      <!-- <hr class="line"/> -->
      <br>  
      <a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a>          
      <a href="javascript:void(0)" id="pisdbutton2" class=button onclick="saveinsuinfo();">保存被保人清单</a>          
      <a href="javascript:void(0)" id="pisdbutton2" class=button onclick="filedownload();">下载被保人清单</a>          
      <!-- <INPUT type =button class=cssButton value="上一步" onclick="returnparent();">   
      <INPUT class=cssButton id="pisdbutton2" VALUE="保存被保人清单" TYPE=button onclick="saveinsuinfo();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="下载被保人清单" TYPE=button onclick="filedownload();"> -->
        <br>
    </Div>
    <Div  id= "divSaveInsuredButton" style= "display: 'none'" >
      <hr class="line"/>
      <br>
      <div>
      <a href="javascript:void(0)" class=button onclick="returnparent();">上一步</a>
      <a href="javascript:void(0)" class=button id="pisdbutton3" onclick="getintopersoninsured();">添加被保人</a>
      <a href="javascript:void(0)" class=button id="pisdbutton1" onclick="getin();">导入被保人清单</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="getout();">删除所有被保人</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="getcontplan();">按计划删除</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="saveinsuinfo();">保存被保人清单</a>
      <a href="javascript:void(0)" class=button id="pisdbutton2" onclick="filedownload();">下载被保人清单</a>
      </div>
      <!-- <INPUT type =button class=cssButton value="上一步" onclick="returnparent();">      
      <INPUT class=cssButton id="pisdbutton3"  VALUE="添加被保人"  TYPE=button onclick="getintopersoninsured();">    
      <INPUT class=cssButton id="pisdbutton1" VALUE="导入被保人清单" TYPE=button onclick="getin();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="删除所有被保人" TYPE=button onclick="getout();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="按计划删除" TYPE=button onclick="getcontplan();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="保存被保人清单" TYPE=button onclick="saveinsuinfo();"> 
      <INPUT class=cssButton id="pisdbutton2" VALUE="下载被保人清单" TYPE=button onclick="filedownload();"> --> 
      <br>  
      <div>
        <a href="./download1.xls">磁盘导入模板</a> &nbsp;<!--a href="./download2.xls">长险导入模板</a> &nbsp;<a href="./download3.xls">万能导入模板</a> &nbsp;<a href="./download4.xls">定额606导入模板</a-->
      <hr class="line"/>  
      <a href="./explain.doc">下载磁盘导入说明（doc文件）</a>
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
