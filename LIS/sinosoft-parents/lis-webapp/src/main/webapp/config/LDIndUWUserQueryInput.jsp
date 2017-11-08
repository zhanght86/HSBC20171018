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
  
  <SCRIPT src="LDIndUWUserQueryInput.js"></SCRIPT> 
  <%@include file="LDIndUWUserQueryInit.jsp"%>
  
  <title></title>
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
  <table class=common border=0 width=100%>
    <tr>
    		<td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDUWUserGrid1);"></td>
			<td class=titleImg align=center> 请输入查询条件： </td>
		</tr>
	</table>
	
  <Div  id= "divLDUWUserGrid1" style= "display: ''" class="maxbox">    
<table  class= common >
  <TR  class= common>
     <TD  class= title>
      核保师编码
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=UserCode id=UserCode  verify="核保师编码|notnull&len<=10" >
    </TD>
    <TD  class= title>
      核保师类别
    </TD>
    <TD  class= input>
      <Input  class= 'codeno' readonly  name=UWType verify="核保师类别|notnull&len<=1" 
      value='1' ><input class=codename readonly=true value='个险' name=UWTypeName id=UWTypeName elementtype=nacessary>
      <!--Input class= 'common' name=UWType elementtype=nacessary verify="核保师类别|notnull&len<=1"-->
    </TD>
    <TD  class= title>
      核保权限
    </TD>
    <TD  class= input>
      <!--Input class= 'code' name=UWPopedom elementtype=nacessary verify="核保权限|notnull&len<=2" ondblclick=" showCodeList('UWPopedom',[this]);"  onkeyup="return showCodeListKey('UWPopedom',[this]);"-->   
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=UWPopedom id=UWPopedom verify="核保权限|notnull&len<=2" 
        ondblclick="return showCodeList('uwpopedom',[this,UWPopedomName],[0,1]);"  onclick="return showCodeList('uwpopedom',[this,UWPopedomName],[0,1]);" onkeyup="return showCodeListKey('uwpopedom', [this,UWPopedomName],[0,1]);"><input class=codename readonly=true name=UWPopedomName id=UWPopedomName >
  
    </TD>
     
  </TR>  
  <TR  class= common>
    <TD  class= title>
      加费评点
    </TD>
    <TD  class= input>
      <Input class= 'common wid' name=AddPoint id=AddPoint verify="加费评点|len<=2">
    </TD>
     <TD  class= title>
      上级核保级别
    </TD>
    <TD  class= input>
       <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= 'codeno' name=UpUWPopedom id=UpUWPopedom verify="上级核保师|notnull&len<=10"  ondblclick="return showCodeList('uwpopedom',[this,UpUserCodeName],[0,1]);" onclick="return showCodeList('uwpopedom',[this,UpUserCodeName],[0,1]);" onkeyup="return showCodeListKey('uwpopedom', [this,UpUserCodeName],[0,1]);"><input class=codename readonly=true name=UpUserCodeName id=UpUserCodeName >
    </TD>
    <TD style="display:none"  class= title>
	      核保师描述
	    </TD>
	    <TD style="display:none"  class= input>
	      <Input class= 'common wid' name=UserDescription id=UserDescription >
	    </TD>
  </TR>
    
   
    
    <!--TD  class= title>
      首席核保标志
    </TD>
    <TD  class= input>
      <Input class= 'code' name=PopUWFlag verify="核保师组别|len<=1" CodeData= "0|^1|是首席核保师^0|不是首席核保师" ondblClick="showCodeListEx('PopUWFlag',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('PopUWFlag',[this,''],[0,1]);">
    </TD>
    <TD  class= title>
      有效开始日期
    </TD>
    <TD  class= input>
      <Input class= 'cooldatePicker' dateFormat="Short" name=ValidStartDate >
    </TD>
  </TR>  
  <TR  class= common>
    <TD  class= title>
      有效结束日期
    </TD>
    <TD  class= input>
      <Input class= 'cooldatePicker' dateFormat="Short" name=ValidEndDate >
    </TD>
    <TD  class= title>
      是否暂停
    </TD>
    <TD  class= input>
      
       <Input class= 'code' name=IsPendFlag verify="是否暂停|len<=1" CodeData= "0|^1|是^0|否" ondblClick="showCodeListEx('IsPendFlag',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('IsPendFlag',[this,''],[0,1]);">
   
    </TD>
    <TD  class= title>
      暂停原因
    </TD>
    <TD  class= input>
      <Input class= 'common' name=PendReason verify="核保师组别|len<=120">
    </TD>
  </TR>
  <TR  class= common-->
    

 
  
</table>
  <Div style="display:none">
      <TD  class= title>
      保全权限
    </TD>
    <TD  class= input>
       <Input class= 'codeno' name=edpopedom  ondblclick="return showCodeList('edpopedom',[this,edpopedomName],[0,1]);"  onkeyup="return showCodeListKey('edpopedom', [this,edpopedomName],[0,1]);"><input class=codename readonly=true name=edpopedomName >
    </TD>
    <TD  class= title>
      理赔权限
    </TD>
    <TD  class= input>
      <Input class= 'codeno' name=ClaimPopedom verify="理赔权限|len<=1" CodeData= "0|^1|有^0|无" ondblclick=" showCodeList('clPopedom',[this,claimpopedomName],[0,1]);"  onkeyup="return showCodeListKey('clPopedom',[this,claimpopedomName],[0,1]);"><input class=codename readonly=true name=claimpopedomName >
    </TD>
 </div>	
  <div id="div1" style="display: none">
  	<table class="common">

	    
  	 	<TR  class= common>
        
	  	 <TD  class= title>
	      是否暂停
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=IsPendFlag id=IsPendFlag >
	    </TD>	
	    <TD  class= title>
	      暂停原因
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=PendReason id=PendReason >
	    </TD>
	    <TD  class= title>
	      备注
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=Remark id=Remark  >
	    </TD>
	  </TR>
  		<TR  class= common>
	    <TD  class= title>
	      操作员代码
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=Operator id=Operator >
	    </TD>
	    <TD  class= title>
	      管理机构
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=ManageCom id=ManageCom >
	    </TD>
         <TD  class= title>
	      入机日期
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=MakeDate id=MakeDate >
	    </TD>
	  </TR>
	  <TR  class= common>
	   
	    <TD  class= title>
	      入机时间
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=MakeTime id=MakeTime >
	    </TD>
         <TD  class= title>
	      最后一次修改日期
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=ModifyDate id=ModifyDate >
	    </TD>
	    <TD  class= title>
	      最后一次修改时间
	    </TD>
	    <TD  class= input>
	      <Input class= 'common wid' name=ModifyTime id=ModifyTime >
	    </TD>
	  </TR> 
	 </table>
	</div></Div>
    
  <!--<table class=common border=0 width=100%>
    <TR class=common>  
      <TD  class=title>
        
          		
      </TD>
    </TR>
  </table>  --><br>
  <INPUT VALUE="查询"   TYPE=button   class=cssButton onclick="easyQueryClick();">
  
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
  				<span id="spanLDUWUserGrid">
  				</span> 
		    </td>
			</tr>
		</table>
	</div>
  	
 <!-- <Div id= "divPage" align=center style= "display: '' ">
    <INPUT CLASS=cssButton VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
    <INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
    <INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
    <INPUT CLASS=cssButton VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
  </Div>--><br>
   <INPUT VALUE="返回" TYPE=button   class=cssButton onclick="returnParent();"> 			
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
 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  
</body>
</html>
 
