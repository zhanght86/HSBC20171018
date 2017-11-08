<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
//程序名称：Status.jsp
//程序功能：状态查询
//创建日期：2008-10-27 11:10:36
//创建人  ：lk
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>

<script>

</script>
<head>
  <title>状态查询 </title>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js" ></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="StatusQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="StatusQueryInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();" >
<Form action="" method=post name=fm  id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			   <td class= titleImg align= center>请输入查询条件：</td>
		  </tr>
	  </table>
      <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
      <TR  class= common>
          <TD  class= title5>
            业务类型
          </TD>
          <TD  class= input5>
            <Input class=codeno  name="BusiType" id="BusiType" verify="业务类型|NotNull"
            style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
            onclick="showCodeList('busitype',[this,BusiTypeName],[0,1]);" 
             onDblClick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);" 
             onKeyUp="return showCodeList('busitype',[this,BusiTypeName],[0,1]);" ><input name=BusiTypeName class=codename readonly=true elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
          </TD>  
          <TD  class= title5>
            号码
          </TD>
          <TD  class= input5>
            <Input class="common wid " name=NO verify="号码|NotNull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
          </TD>      
       </TR>
      <TR  class= common>
          <TD  class= title5>
            合同号
          </TD>
          <TD  class= input5>
            <Input class="common wid "   name=ContNo>
          </TD>  
          <TD class= title5>
            管理机构
          </TD>
          <TD class= input5>
           <Input  class=codeno name=ComCode id=ComCode
           style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
            onclick="showCodeList('station',[this,ComName],[0,1]);" 
            onDblClick="showCodeList('station',[this,ComName],[0,1]);" 	
            onkeyup="showCodeList('station',[this,ComName],[0,1]);"><Input class=codename name=ComName readOnly>
          </TD>      
       </TR>         
      <TR  class= common>
          <TD  class= title5>
            开始日期
          </TD>
          <TD  class= input5>
            
            <Input class="multiDatePicker laydate-icon"  onClick="laydate({elem: '#ValidStartDate'});"dateFormat="short" name=ValidStartDate id=ValidStartDate><span class="icon"><a onClick="laydate({elem: '#ValidStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
          <TD  class= title5>
            结束日期
          </TD>
          <TD  class= input5>
            <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#ValidEndDate'});" dateFormat="short" name=ValidEndDate id=ValidEndDate><span class="icon"><a onClick="laydate({elem: '#ValidEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>      
       </TR>       
      
    </table>
    </div>
     </div>
          <!--<INPUT VALUE="查  询" class= cssButton TYPE=button onClick="easyQueryClick();"> -->
          <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>

    <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNo);">
    		</td>
    		<td class= titleImg>
    			 业务信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divNo" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	    <td style=" text-align: left" colSpan=1>
  					    <span id="spanNoGrid" ></span> 
  			  	</td>
  			  </tr>
    	  </table>
  
       <!-- <INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">-->	
		
  	</div>

    <!--table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divStatus);">
    		</td>
    		<td class= titleImg>
    			 状态信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divStatus" style= "display: '';" align=center>
    <div class="maxbox1" >
  	
      	<table align=top>
       		<tr>
      	     <td>
                 <iframe id=iframeView src="../wfpic/workflowView.jsp" frameborder=0 width="900" height="550"></iframe>
  			     </td>
  		    </tr>
    	 </table>
    </div>	
    </div-->	 
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form> 
</body>
</html>
