<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="AgentTrussInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="AgentTrussInit.jsp"%>
  <title>��ѯչҵ����</title>   
</head>

<body onLoad="initForm();" >
  <form action="./AgentTrussPQuery.jsp" method=post name=fm id=fm target="fraSubmit">

    <table class="common" border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
				<td class="titleImg" align= center>������չҵ������ѯ������</td></tr>
		</table>
		 <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
	  <table  class="common" align=center>
			<tr class="common">
            
				<td class="title5">չҵ��������</td>
				<td class="input5"><input class="common" name=BranchAttr></td>

        <td class="title5">�������</td>
				<td class="input5"><input class="code" name=ManageCom  id=ManageCom
                style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('comcode',[this]);"
                 onDblClick="return showCodeList('comcode',[this]);"
                  onKeyUp="return showCodeListKey('comcode',[this]);"></td></tr>
    </table>
    </div></div>
<a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a>
<a href="javascript:void(0);" class="button"onClick="returnParent();">��   ��</a>
   <!-- <input VALUE="��  ѯ" class="cssButton" type=button onClick="easyQueryClick();"> 
    <input VALUE="��  ��" class="cssButton" type=button onClick="returnParent();"> -->
  </form>

  <form action="" method=post name=fmSave target="fraSubmit">
    <table>
    	<tr>
				<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    		</td>
    		<td class="titleImg">
    			 չҵ������Ϣ
    		</td>
    	</tr>
    </table>

  	<div id="divLCPol1" style= "display: ''">
			<table class="common">
				<tr class="common">
					<td style="text-align: left" colSpan=1><span id="spanPolGrid" ></span></td>
  			</tr>
    	</table>
			<input value="��  ҳ" class="cssButton90" type=button onClick="getFirstPage();">
      <input value="��һҳ" class="cssButton91" type=button onClick="getPreviousPage();">
      <input value="��һҳ" class="cssButton92" type=button onClick="getNextPage();">
      <input value="β  ҳ" class="cssButton93" type=button onClick="getLastPage();">
  	</div>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
