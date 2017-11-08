<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorUWManuSpecInput.jsp
//程序功能：人工核保特约信息录入
//创建日期：2005-06-24 15:10:36
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<head >

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="EdorUWManuSpec.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 特约录入 </title>
  <%@include file="EdorUWManuSpecInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tMissionID%>','<%=tSubMissionID%>','<%=tEdorNo%>','<%=tEdorType%>','<%=tPrtNo%>','<%=tPolNo%>');" >

  <form method=post name=fm id=fm target="fraSubmit" action= "./EdorUWManuSpecSave.jsp">

    <Div  id= "divLPPol" style= "display: ''">
      <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
            </td>
            <td class= titleImg>
                个人险种信息
            </td>
            </tr>
        </table>
     <div id="divPolGrid" style="display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanPolGrid"></span></td>
            </tr>
        </table>
     </div>
    </Div>

    <!-- 以往核保记录部分（列表） -->
     <Div  id= "divLCPol" style= "display: ''">
      <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
            </td>
            <td class= titleImg>
                原特约信息
            </td>
            </tr>
        </table>
        <Div  id= "divLCPol2" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanUWSpecGrid"></span></td>
            </tr>
        </table>
    </Div>
   </div>

    <!-- 现在需要往LPSpec存的核保信息 -->
     <Div  id= "divLPSpec" style= "display: ''">
      <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPSpec2);">
            </td>
            <td class= titleImg>
                总特约信息
            </td>
            </tr>
        </table>
        <Div  id= "divLPSpec2" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanUWLPSpecGrid"></span></td>
            </tr>
        </table>
    </Div>
   </div>


        <Div id= "divModel" style= "display: ''">
        <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divModel2);">
            </td>
            <td class= titleImg>
                特约信息模板
            </td>
            </tr>
        </table>
       <Div id= "divModel2" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanUWModelGrid"></span>
                </td>
            </tr>
        </table>
      </Div>
   </div>

    </table>
        <table class = common>
        <TR  class= common>
          <TD  class= title>
            特约原因
          </TD>
          <tr></tr>
      <TD  class= input> <textarea name="SpecReason" id=SpecReason cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
        </TR>
    </table>



        <table class = common>
        <TR  class= common>
          <TD  class= title>
            特别约定
          </TD>
          <tr></tr>

      <TD  class= input> <textarea name="Remark" id=Remark cols="100%" rows="5" witdh=100% class="common wid"></textarea></TD>
        </TR>

     <table>
      <br>
      <INPUT type="hidden" name="ContNo" id=ContNo>
      <INPUT type="hidden" name="PolNo" id=PolNo>
      <INPUT type="hidden" name="Flag" id=Flag>
      <input type="hidden" name="UWIdea" id=UWIdea>
      <input type="hidden" name="PrtNo" id=PrtNo>
      <INPUT type="hidden" name="MissionID" id=MissionID>
      <INPUT type="hidden" name="SubMissionID" id=SubMissionID>
      <INPUT type="hidden" name="operate" id=operate>
      <INPUT type="hidden" name="proposalno" id=proposalno>
      <INPUT type="hidden" name="serialno" id=serialno>
      <input type="hidden" name="EdorNo" id=EdorNo>
      <input type="hidden" name="EdorType" id=EdorType>
      <input type="hidden" name="QueryFlag" id=QueryFlag>

      <INPUT type="button" value=" 添 加 " class=cssButton  onclick="submitForm(1)">
      <INPUT type="button" value=" 修 改 " class=cssButton  onclick="submitForm(2)">
      <INPUT type="button" value=" 删 除 " class=cssButton  onclick="submitForm(3)">
      <INPUT type="button" value=" 返 回 " class=cssButton  onclick="returnParent()">
      <Br>

    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
