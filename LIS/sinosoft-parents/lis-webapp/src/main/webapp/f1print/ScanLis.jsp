<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<html>
<%

%>
<%
    GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
     <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  	<SCRIPT src="ScanLis.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="ScanLisInit.jsp"%>
	</head>

<body  onload="initForm();" >
  	<form action="./ScanLisSave.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
        <!-- 显示或隐藏LLReport1的信息 -->
<table class= common border=0 width=100%>
    	<tr>   
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>  		
    		 <td class= titleImg>
        		输入查询条件
       		 </td>   		      
    	</tr>
    </table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
      	<table  class= common>
          <TR  class= common>
        	  <TD  class= title5>管理机构	</TD>
            <TD  class= input5>
          	<Input class=codeno name=ManageCom  id=ManageCom 
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('ComCode',[this,ManageComName],[0,1]);
            onDblClick="return showCodeList('ComCode',[this,ManageComName],[0,1]);" 
            onKeyUp="return showCodeListKey('ComCode',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" readonly>
          	</TD>
          	<TD  class= title5>业务类型</TD>
                <TD  class= Input5> <Input class="codeno" 
                CodeData="0|^个险|8611^银代|8615-8612-8635^中介|8621^简易|8616"
                 name=ScanType verify="" onDblClick="showCodeListEx('ScanType',[this,ScanTypeName],[0,1]);"
                 style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick=showCodeListKeyEx('ScanType',[this,ScanTypeName],[0,1]);"
                  onKeyUp="showCodeListKeyEx('ScanType',[this,ScanTypeName],[0,1]);"><input class=codename name=ScanTypeName readonly=true >
          </TD> 
          </TR>
         <TR  class= common>  
           <TD  class= title5>单证类型</TD>      
                <TD  class= Input5> <Input class="codeno"  name=SubType verify=""
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                 onclick="showCodeList('station',[this,ComName],[0,1]);" 
                 onDblClick="getcodedata2();return showCodeListEx('SubType',[this,SubTypeName],[0,1]);" 
                 onKeyUp="return showCodeListKeyEx('SubType',[this,SubTypeName],[0,1]);"><input class=codename name=SubTypeName readonly=true >
          </TD> 
            
           <TD  class= title5>操作员工号</TD>
           <TD class="input5">
             <Input class="common wid" name=OperaterNo >       
           </TD>
           </TR>
         <TR  class= common>  
           <TD  class= title5>
              起始日期	
            </TD>
            <TD  class= input5>
              
              <input class="coolDatePicker" dateFormat="short" id="IssueStartDate"  name="IssueStartDate" onClick="laydate
({elem:'#IssueStartDate'});" > <span class="icon"><a onClick="laydate({elem: '#IssueStartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>
            <TD  class= title5>
              结束日期	
            </TD>
            <TD  class= input5>
              <input class="coolDatePicker" dateFormat="short" id="IssueEndDate"  name="IssueEndDate" onClick="laydate
({elem:'#IssueEndDate'});" > <span class="icon"><a onClick="laydate({elem: '#IssueEndDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>	
            </TD>  
         </tr>    
         </table>  
     </div></div>
</form>
<form action="" method=post name=fmSave target="f1print">
  
  
  <!--	<p>
      <INPUT VALUE="查  询" class=cssButton TYPE=button onClick="easyQuery();">
      <INPUT VALUE="打  印" class=cssButton TYPE=button onClick="PrintNoticeBill();">
  	</p>-->
     <a href="javascript:void(0);" class="button" onClick="easyQuery();">查   询</a>
      <a href="javascript:void(0);" class="button"onClick="PrintNoticeBill();">打  印</a>
  	
  	 <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 扫描清单数据
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanPolGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
      	<INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="getLastPage();"> 					
  	</div>
  	
  	<Div id="OldInfoDiv" style ="display:'none'">
  	<!--添加该清单或报表的说明-->
    <Table>
        <TR>
            <TD  class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivShow);"></TD>
            <TD class= titleImg>用途说明:</TD>
        </TR>
    </Table>
    
    <Div id= "DivShow" style= "display: ''">
        <Table class=common>
          <TR class= common>
          	该清单用于统计扫描的所有单证的明细清单，可根据清单进行单证的归档处理
          </TR>	
        </Table>
    </Div>
    
    <Table>
    <TR>
        <TD  class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivRemark);"></TD>
        <TD class= titleImg>备注信息:</TD>
    </TR>
    </Table>
    <Div id= "DivRemark" style= "display: ''">
        <Table class=common>
          <TR class= common >
          	<font color=red>可以批次号进行查询并打印</font>
          </TR>	
        </Table>
    </Div>
    </Div>
    <br><br><br><br>
              <Input Type=hidden name=StrSQL> 
              <Input Type=hidden name=Sum>
    
  </form>
  
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
