<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：AssociatedDirectItemDefQuery.jsp
//程序功能：科目专项定义查询界面
//创建日期：2008-09-16
//创建人  ：Fanxin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./AssociatedDirectItemDefQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./AssociatedDirectItemDefQueryInit.jsp"%>

<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
</script>

<title>分录固定数据定义</title>
</head>
<body onload="initForm();initElementtype();">
  <form  method=post name=fm id=fm target="fraSubmit">
  
  <table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssociatedItemDef1);">
    </IMG>
    <td class=titleImg>
      查询条件
      </td>
    </td>
    </tr>
  </table>
    <Div  id= "divAssociatedItemDef1" style= "display: ''" class="maxbox1">
      <table  class= common>
			   
			<tr class= common>
				<TD class= title5>
		   	   专项表字段标识
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ColumnID id=ColumnID verify="专项表字段标识|NOTNULL" ondblClick="showCodeList('columnid',[this,columnidName],[0,1]);" onMouseDown="showCodeList('columnid',[this,columnidName],[0,1]);" onkeyup="showCodeListKey('columnid',[this,columnidName],[0,1]);"><input class=codename name=columnidName id=columnidName readonly=true >
		    </TD>
				<TD class= title5>
		   	   上游数据来源字段
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SourceColumnID id=SourceColumnID verify="上游数据来源字段|NOTNULL" ondblClick="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1]);" onMouseDown="showCodeList('sourcecolumnid',[this,sourcecolumnName],[0,1]);" onkeyup="showCodeListKey('sourcecolumnid',[this,sourcecolumnName],[0,1]);"><input class=codename name=sourcecolumnName id=sourcecolumnName readonly=true >
		    </TD>                	    		    
			</tr>
				
							

      </table>
      </div>
          <!--<INPUT VALUE="查  询" TYPE=button onclick="easyQueryClick();" class="cssButton">-->
          <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a> 
     	
          
          
    <table>    	
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssociatedDirectItemDef2);">
    		</td>
    		<td class= titleImg>
    			 分录固定数据定义查询结果
    		</td>
    	</tr>
    </table>

    
  	<Div  id= "divAssociatedDirectItemDef2" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					 <span id="spanAssociatedDirectItemDefGrid" >
  					 </span>
  			  	</td>
  			</tr>
    	</table>
        
      <!--<INPUT VALUE="首  页" TYPE=button onclick="turnPage.firstPage();" class="cssButton">
      <INPUT VALUE="上一页" TYPE=button onclick="turnPage.previousPage();" class="cssButton">
      <INPUT VALUE="下一页" TYPE=button onclick="turnPage.nextPage();" class="cssButton">
      <INPUT VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();" class="cssButton">-->
  	</div>  

    	<INPUT type=hidden name=VersionNo value=''> 
    	<INPUT type=hidden name=SourceTableID value=''> 

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
</body>
</html>

