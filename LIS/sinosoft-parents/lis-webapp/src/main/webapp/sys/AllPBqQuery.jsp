<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-1-22
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="AllPBqQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../sys/AllPBqQueryInit.jsp"%>
  <title>��ȫ��ѯ</title>
</head>
<body  onload="initForm();">
  <form action="./AllPBqQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit" >
    <!-- ������Ϣ���� -->
  <table class= common border=0 width=100%>
    <tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
            <td class= titleImg align= center>
                ��������˱�ȫ��ѯ������
            </td>
        </tr>
    </table>
 <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common>
        <TR  class= common>
         <td class=title5>
            ��ȫ�����
        </td>
        <td class= input5>
            <Input type="input" class="common wid" name=EdorAcceptNo>
        </td>
          <td class=title5>
                    ��������
          </td>
          <td class= input5><Input class="codeno" name=OtherNoType id=OtherNoType
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('edornotype',[this,OtherNoName],[0,1])"
           onDblClick="showCodeList('edornotype',[this,OtherNoName],[0,1])"
            onKeyUp="showCodeListKey('edornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName readonly=true></td>
         </TR>
         <TR  class= common>
         <td class=title5>
                    �ͻ�/������
         </td>
         <td class= input5>
         <Input class="common wid"   name=OtherNo>
         </td>
      
          <!--TD  class= title> ���ֱ��� </TD>
          <TD  class= input> <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);"> </TD-->
          
          <td class=title5>
                    ����������
                </td>
                <td class= input5>
                    <Input type="input" class="common wid"  name=EdorAppName>
                </td>
                </TR>
         <TR  class= common>
           <td class=title5>
                    ���뷽ʽ
                </td>
                <td class= input5><Input class="codeno" name=AppType 
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" 
                onDblClick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" 
                onKeyUp="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName readonly=true></td>
                <TD  class= title5>
                    ��ȫ��������
                </TD>
                <TD  class= input5>
                    <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EdorAppDate'});"dateFormat="short" 

name=EdorAppDate id=EdorAppDate><span class="icon"><a onClick="laydate({elem: '#EdorAppDate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
        </TR>
         <TR  class= common>
         <td class=title5>
            ������
        </td>
        <td class= input5>
            <Input type="input" class="common wid"  name=EdorNo>
        </td>
          <TD  class= title5>   �������  </TD>
        <TD  class= input5><Input class= codeno name=ManageCom   id=ManageCom 
        style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('station',[this,ManageComName],[0,1])" 
        onDblClick="showCodeList('station',[this,ManageComName],[0,1])" 
        onKeyUp="showCodeListKey('station',[this,ManageComName],[0,1])"><input class="codename" name=ManageComName readonly=true></TD>
          
         
         </TR>


    </table>
    </div>
    </div>
<!-- <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">��   ѯ</a> -->

          <INPUT VALUE=" �� ѯ " class=cssButton  TYPE=button  onClick="easyQueryClick();">

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain1);">
            </td>
            <td class= titleImg>
                 ��ȫ��Ϣ
            </td>
        </tr>
    </table>
    <Div  id= "divLPEdorMain1" style= "display: ''" align = center>
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolGrid" >
                    </span>
                </td>
            </tr>
   </table>
      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage1.firstPage();HighlightByRow()">
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage1.previousPage();HighlightByRow()">
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage1.nextPage();HighlightByRow()">
      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage1.lastPage();HighlightByRow()">

        <input type=hidden name=Transact >
    </div>
    <br>
     <a href="javascript:void(0);" class="button" onClick="gotoDetail();">��ȫ��ϸ</a>
     <a href="javascript:void(0);" class="button" onClick="queryNotice();">�������ѯ</a>
     
 <a href="javascript:void(0);" class="button"onClick="MissionQuery();">��ȫ�����켣</a>
 
 <a href="javascript:void(0);" class="button" onClick="showNotePad();">���±��鿴</a>
    <!--<INPUT class=cssButton VALUE=" ��ȫ��ϸ"  TYPE=button onClick="gotoDetail();">
    <INPUT class=cssButton VALUE=" �������ѯ"  TYPE=button onClick="queryNotice();">
    <INPUT VALUE="��ȫ�����켣" class=cssButton TYPE=button onClick="MissionQuery();">
    <INPUT class= cssButton TYPE=button VALUE="���±��鿴" onClick="showNotePad();">-->

        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="LoginManageCom">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
