<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//程序名称：UWManuHealth.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
//tongmeng 2008-10-10 add
//初始化界面
UWAutoHealthQueryBL tUWAutoHealthQueryBL = new UWAutoHealthQueryBL();
tUWAutoHealthQueryBL.submitData(new VData(),"INIT");
VData mInitData = new VData();
mInitData = tUWAutoHealthQueryBL.getResult();
SSRS tSSRS_Main = (SSRS)mInitData.get(0);
SSRS tSSRS_Sub = (SSRS)mInitData.get(1);

loggerDebug("BQManuHealth","@@tSSRS_Main:"+tSSRS_Main.getMaxRow()+":"+tSSRS_Sub.getMaxRow());
%>
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./BQManuHealth.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="BQManuHealthInit.jsp"%>
  <title> 新契约体检资料录入 </title>

  <script language="JavaScript">

  </script>

</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tPrtNo%>','<%=tFlag%>','<%=tEdorNo %>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./BQManuHealthChk.jsp">
    <!-- 非列表 -->
    <DIV id=DivLCContButton STYLE="display:''">
    <table >
        <tr>
        <td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,BasicInfo);">
			</td>
        <td class= titleImg>体检基本信息</td>
    </tr>
    </table>
  </div>
  <Div  id= "BasicInfo" style= "display: ''" class="maxbox1">
    <table  class= common align=center>
        <TR  class= common >
          <TD  class= title>  投保单号  </TD>
          <TD  class= input> <Input class="readonly wid" name=ContNo id=ContNo > </TD>
           <INPUT  type= "hidden" class= Common id="MissionID" name= MissionID value= ""><!-- 工作流任务编码 -->
           <INPUT  type= "hidden" class= Common id="SubMissionID" name= SubMissionID value= "">
           <INPUT  type= "hidden" class= Common id="PrtNo" name= PrtNo value= "">
          <TD  class= title>  打印状态 </TD>
          <TD  class= input>  <Input class="readonly wid" name=PrintFlag id=PrintFlag > </TD>
           <TD  class= title>  体检人  </TD>

          <TD  class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center right" class=code name=InsureNo id=InsureNo ondblClick="showCodeListEx('InsureNo',[this,this,'',CustomerType],[0,1,2,3],null,null,null,1);" onClick="showCodeListEx('InsureNo',[this,this,'',CustomerType],[0,1,2,3],null,null,null,1);" 
          onkeyup="showCodeListKeyEx('InsureNo',[this,this,'',CustomerType],[0,1,2,3],null,null,null,1);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title> 体检日期   </TD>
          <TD  class= input>  <!--<Input class="coolDatePicker" dateFormat="short" name=EDate verify="体检日期|date" > --> 
          <Input class="coolDatePicker" onClick="laydate({elem: '#EDate'});" verify="体检日期|date" dateFormat="short" name=EDate id="EDate"><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>

           <input type=hidden id="Hospital" name="Hospital">
          <TD  class= title>  是否空腹 </TD>
          <TD  class= input>   <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat center right" class=code name=IfEmpty id=IfEmpty onDblClick="return showCodeList('YesNo',[this]);" onClick="return showCodeList('YesNo',[this]);" onKeyUp="return showCodeListKey('YesNo',[this]);">  </TD>
          <TD  class= title>
              体检原因
          </TD>
          <TD  class= input>
              <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="codeno" name=PEReason id=PEReason onDblClick="showCodeList('pereason',[this,PEReasonName],[0,1]);" onClick="showCodeList('pereason',[this,PEReasonName],[0,1]);" onKeyUp="showCodeListKeyEx('pereason',[this,PEReasonName],[0,1]);"><input class="codename" name="PEReasonName" id="PEReasonName" readonly>
          </TD>
        </TR>
      </table>
  </div>
<DIV id=DivLCContButton STYLE="display:''">
	<table id="table1">
			<tr>
				<td class="common">
				<img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,DivLCPE);">
				</td>
				<td class="titleImg">已保存体检信息
				</td>
			</tr>
	</table>
	</DIV>
<Div id="DivLCPE" style="display: ''">
        <table class=common>
            <tr class=common>
                <td text-align: left colSpan=1>
  					<span id="spanPEGrid">
  					</span>
                </td>
            </tr>
        </table>
        <center>
        <INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="getFirstPage();">
        <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="getPreviousPage();">
        <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="getNextPage();">
        <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="getLastPage();"></center>
</div>
<DIV id=DivLCContButton STYLE="display:''">
      <table  >
        <tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
        <td class= titleImg>体检类型选择</td>
    	</tr>
    	</table>
 </DIV>
<Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
 	<table class= common align=center>
    	<%
    	//直接写到界面上
    	  for(int i=1;i<=tSSRS_Main.getMaxRow();i++)
    	  {
    		  String tMainHealthCode = tSSRS_Main.GetText(i,1);
    		  String tMainHealthName = tSSRS_Main.GetText(i,2);
    		  String tSubHealthCode = tSSRS_Main.GetText(i,3);
    		 // loggerDebug("BQManuHealth","tSubHealthCode:"+tSubHealthCode);
    		  %>
    		 <tr class= common>
          		<td class= common>
          		   <input type=radio name=HealthCode id="<%=tMainHealthCode%>" value="<%=tMainHealthCode%>" OnClick= "showBodyCheck('<%=tSubHealthCode%>');"><%=tMainHealthName%></td>
    		 </tr>
    		  <%
    	  }
    	%>
  </table>
</Div>
       <table class= common border=0 width=100%>
        <tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,aew);"></td>
        <td class= titleImg align= center>体检项目选择</td>
    </tr>
       </table>

       <Div  id= "aew" style= "display: ''" class="maxbox1">
          <table  class= common>
             <% 
                //初始化体检子项目界面
                for(int i=1;i<=tSSRS_Sub.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Sub.GetText(i,1);
                	String tSubName = tSSRS_Sub.GetText(i,2);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	%>
                		<td class= common width=25%> <input type=checkbox name='<%=tSubCode%>' id='<%=tSubCode%>' value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	//每4个做换行处理.
                	if(i%3==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Sub.getMaxRow())
                	{
                		%>
                		</tr>
                		<%

                	}
                }
             %>
         </table>   
       </div>

       <div id = "otheritem" style= "display: ''">
       <table class=title>
         <TR  class= common>
           <TD  class= common> 其他体检项目 </TD>
         </TR>
         <TR  class= common>
           <TD  colspan="6" style="padding-left:16px">
             <textarea name="otheritem2" id="otheritem2" cols="200" rows="4" class="common" value=""></textarea>
           </TD>
         </TR>
      </table>
      </div>

       <p>
       <table class=title>
         <TR  class= common>
           <TD  class= common> 其他体检信息 </TD>
         </TR>
         <TR  class= common>
           <TD  colspan="6" style="padding-left:16px">
             <textarea name="Note" id="Note" cols="200" rows="4" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>

      <input type="button" class="cssButton" value=" 确 认 " onClick="submitForm()">
      <input type="button" class="cssButton" value=" 修 改 " onClick="updateData()">
      <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">

        <!-- XinYQ added on 2006-07-19 : 保全复用新契约体检 : BGN -->
        <input type="hidden" id="EdorNo" name="EdorNo">
        <!-- XinYQ added on 2006-07-19 : 保全复用新契约体检 : END -->

      <input type=hidden id="Flag" name="Flag">
      <input type=hidden id="Action" name="Action">
      <input type=hidden id="PrtSeq" name="PrtSeq">
      <input class=input type=hidden id="CheckedItem" name="CheckedItem" >
      <input type=hidden id="CustomerType" name="CustomerType" >
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
