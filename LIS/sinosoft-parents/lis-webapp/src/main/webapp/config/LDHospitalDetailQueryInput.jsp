<%
//程序名称：LDUWUserInput.jsp
//程序功能：功能描述
//创建日期：2005-01-24 18:15:01
//创建人  ：ctrHTML
//更新人  ：  
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>  
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>

  <SCRIPT src="LDHospitalDetailQueryInput.js"></SCRIPT> 
  <%@include file="LDHospitalDetailQueryInit.jsp"%>
  <title>体检医院信息查询</title>
</head>
<%
  GlobalInput tG = new GlobalInput(); 
  tG = (GlobalInput)session.getValue("GI");
%>
<script>
  var comCode = "<%=tG.ComCode%>";
  var manageCom = "<%=tG.ManageCom%>";
  var operator = "<%=tG.Operator%>";
</script>
<body onload="initForm();">
  <form method=post name=fm target=fraSubmit>
  
  <!-- 导入按钮界面 -->
  <%@include file="../common/jsp/InputButton.jsp"%>
    
  <!-- 查询条件部分 -->
  <!-- 查询条件Title -->
  <!--<table class=common border=0 width=100%>
    <tr>
    		
			<td class=titleImg> 请输入查询条件： </td>
		</tr>
	</table>-->
	
     
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);">
    		</td>
    		 <td class= titleImg>
        		 体检医院基本信息
       		 </td>   		 
    	</tr>
    </table>
    <Div  id= "peer" style= "display: ''" class="maxbox1">
<table  class= common  >
  <TR  class= common>
    <TD  class= title5>
      体检医院编码
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= 'code' name=HospitalCode id=HospitalCode " verify="体检医院编码|code:pehospital"
        ondblclick="return showCodeList('pehospital',[this,HospitalName],[0,1]);" onclick="return showCodeList('pehospital',[this,HospitalName],[0,1]);" onkeyup="return showCodeListKey('pehospital', [this,HospitalName],[0,1]);">
    </TD>
    <TD  class= title5>
      体检医院名称
    </TD>
    <TD  class= input5>
      <input class="wid" class=common name=HospitalName id=HospitalName elementtype=nacessary  verify="体检医院名称|notnull">
    </TD>

  </TR>
  <TR  class= common>
    <TD  class= title5>
      所属管理机构
    </TD>
    <TD  class= input5>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=ManageCom id=ManageCom verify="所属管理机构|notnull&len<=8"  ondblclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" onclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('CodeCode', [this,ManageComName],[0,1]);"><input class=codename readonly=true name=ManageComName id=ManageComName elementtype=nacessary>
    </TD>
    <TD  class= title5>
      协议书签署日期
    </TD>
		<td class="input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#ValidityDate'});" verify="签署日期|notnull&DATE" dateFormat="short" elementtype=nacessary name="ValidityDate" id="ValidityDate"><span class="icon"><a onClick="laydate({elem: '#ValidityDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </td>

  </TR>  
  <TR  class= common style='display:none'>
      <TD  type='hidden' class= title5>
      体检医院级别
    </TD>
    <TD  class= input5>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type='hidden'  class= 'codeno' name=HospitalGrade " 
        ondblclick="return showCodeList('hospitalgrade',[this,HospitalGradeName],[0,1]);" onclick="return showCodeList('hospitalgrade',[this,HospitalGradeName],[0,1]);" onkeyup="return showCodeListKey('uwpopedom', [this,HospitalGradeName],[0,1]);"><input class=codename readonly=true name=HospitalGradeName type='hidden' >
    </TD>
  </TR>
</table>
</Div>

        <!--<INPUT VALUE="查询"   TYPE=button   class=cssButton onclick="easyQueryClick();">-->
		<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
        

  
  <!-- 查询结果部分 -->      
  <table>
  	<tr>
      <td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUser1);">
  		</td>
  		<td class=titleImg>
  			 信息
  		</td>
  	</tr>
  </table>
  <!-- 信息（列表） -->
	<Div id="divLDUWUser1" style="display:''">
    <table class=common>
    	<tr class=common>
	  		<td text-align:left colSpan=1>
  				<span id="spanLDHospitalGrid">
  				</span> 
		    </td>
			</tr>
		</table>
	</div>
  	
 
    
<!-- 录入提交信息部分     
  <table class=common border=0 width=100%>
    <tr>
  		<td class=titleImg align= center>录入信息：</td>
  	</tr>
  </table> 
     		  								
  <table  class=common align=center>
    <TR CLASS=common>
      <TD  class=title>
        通知单号码
      </TD>
      <TD  class=input>
        <Input NAME=GetNoticeNo class=common verify="通知单号码|notnull">
      </TD>
      <TD  class=title>
        <INPUT VALUE="打 印" class=common TYPE=button onclick="PPrint()">
      </TD>
      
    </TR>
  </table>
  
  <br>           
-->    
 
  <INPUT VALUE="" TYPE=hidden name=serialNo>
  <input type=hidden id="fmtransact" name="fmtransact">
 <!--<INPUT VALUE="返回" TYPE=button   class=cssButton onclick="returnParent();">-->
 <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
 
