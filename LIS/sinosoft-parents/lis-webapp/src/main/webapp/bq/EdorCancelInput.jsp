<html>
<%
//�������ƣ�EdorCancelInput.jsp
//�����ܣ���ȫ����
//�������ڣ�2005-05-08 09:20:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<head >
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");

//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====BGN===================================
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");    //��ȫ�����
    String tMissionID = request.getParameter("MissionID");          //����ID
    String tSubMissionID = request.getParameter("SubMissionID");    //������ID
    String tActivityID      = request.getParameter("ActivityID"); 
    tActivityID=tActivityID.replace(" ", ""); 
//=====�ӹ�������ȫ����ҳ�洫�ݹ����Ĳ���=====END===================================

%>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./EdorCancel.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorCancelInit.jsp"%>
</head>
<body  onload="initForm();" >
    <form action="./PEdorAppCancelSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorAppInfo);">
                </td>
                <td class= titleImg> ��ȫ������Ϣ </td>
            </tr>
        </table>
        <Div  id= "divEdorAppInfo" style= "display: ''" class="maxbox">
            <TABLE class=common>
            <!- ��ʾ��ȫ����Ļ�����Ϣ,���������� �Թ��ο� ->
                <tr class=common>
                    <td class=title> ��ȫ����� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
                    <td class=title> ������� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=OtherNo id=OtherNo></td>
                    <td class=title> �������� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=OtherNoTypeName id=OtherNoTypeName></td>
                </tr>
                <tr class=common>
                    <td class=title> ������ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=EdorAppName id=EdorAppName></td>
                    <td class=title> ���뷽ʽ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=Apptype id=Apptype></td>
                    <td class=title> ������� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=ManageCom id=ManageCom></td>
                </tr>
                <tr class=common>
                    <td class=title> ��/�˷ѽ�� </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=GetMoney id=GetMoney></td>
                    <td class=title> ��/�˷���Ϣ </td>
                    <td class= input><Input type="input" class="readonly wid" readonly name=GetInterest id=GetInterest></td>
                    <td class= title> </td>
                    <td class= input> </td>
                </tr>
            </TABLE>
        </Div>
        <!-- XinYQ commented on 2006-02-09 : ��ʼ����ʾ������������Ŀ��Ϣ : BGN -->
        <!--
        <Div  id= "divEdorMainInfo" style= "display: 'none'">
            <table>
                <tr>
                    <td>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorMainGrid);">
                    </td>
                    <td class= titleImg> ��ȫ����������Ϣ </td>
                </tr>
            </table>
            <Div  id= "divEdorMainGrid" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td text-align: left colSpan=1>
                            <span id="spanEdorMainGrid" >
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </DIV>
        <Div  id= "divEdorItemInfo" style= "display: 'none'">
            <table>
                <tr>
                    <td>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItemGrid);">
                    </td>
                    <td class= titleImg> ��ȫ������Ŀ��Ϣ </td>
                </tr>
            </table>
            <Div  id= "divEdorItemGrid" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td text-align: left colSpan=1>
                            <span id="spanEdorItemGrid" >
                            </span>
                        </td>
                    </tr>
                </table>
            </div>
        </DIV>
        -->
        <!-- XinYQ commented on 2006-02-09 : ��ʼ����ʾ������������Ŀ��Ϣ : BGN -->

        <!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : BGN -->
        <!-- ������Ŀ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style= "cursor:hand" onclick="showPage(this,divAppItemList)"></td>
                <td class="titleImg">������Ŀ��Ϣ</td>
           </tr>
        </table>
        <!-- ������Ŀ���չ�� -->
        <div id="divAppItemList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanAppItemGrid"></span></td>
                </tr>
            </table>
            <!-- ������Ŀ�����ҳ -->
            <div align="center" style= "display:'none'">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPage.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPage.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPage.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPage.lastPage()">
            </div>
        </div>
        
        <INPUT VALUE="��ȫ��ϸ��ѯ" class=cssButton TYPE=button onclick="EdorDetailQuery();">
        <!-- XinYQ added on 2006-02-09 : �������޸�Ϊ������Ŀ��Ϣһ����ʾ : END -->

    <Div  id= "divEdorAppCancelInfo" style= "display: ''">
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAppCancelInfo);">
                </td>
                <td class= titleImg> ��ȫ���볷�� </td>
            </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title><font color=red><b>����ԭ��:</b></font></td> 
                    <td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=MCanclReason id=MCanclReason verify="����ԭ��|NotNull&Code:edorcancelmreason" ondblclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onkeyup="return showCodeListKey('edorcancelmreason',[this,SCanclReasonName],[0,1]);"><input class=codename name=SCanclReasonName id=SCanclReasonName readonly></td>
                    <td class="title"><Div id="divCancelMainReason" style="display:none"> ����ԭ�� </Div></td>
                    <td class="input"><Div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" CodeData=""  name="SCanclReason" verify="�޸�ԭ��|Code:edorcancelsreason" ondblclick="return showCodeListEx('edorcancelsreason',[this,MCanclReasonName],[0,1])" onkeyup="return showCodeListKeyEx('edorcancelsreason',[this,MCanclReasonName],[0,1])"><input class="CodeName" name="MCanclReasonName" readonly></Div></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
     </DIV>
         <Div  id= "divAppCancelA" style= "display: 'none'">            
            <table  class= common>
                <tr class=common>
                    <td class=title>A��	�ͻ�ԭ��</td>
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="A01">�ͻ�����
               <input class=title name=SCanclReason TYPE=radio  value="A02" >��ͥì��
               <input class=title name=SCanclReason TYPE=radio  value="A03" >�ͻ�����
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelB" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>B��	��������</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="B01">ԽȨ����
               <input class=title name=SCanclReason TYPE=radio  value="B02" >����������
               <input class=title name=SCanclReason TYPE=radio  value="B03" >���ڴ�ǩ������
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelC" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>C��	ҵ��Աԭ��</td>
                 
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="C01">ԽȨ����
               <input class=title name=SCanclReason TYPE=radio  value="C02" >������
               <input class=title name=SCanclReason TYPE=radio  value="C03" >���ڴ�ǩ������
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelD" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>D��	ϵͳ����</td>
                  
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="D01">ϵͳ����
               <input class=title name=SCanclReason TYPE=radio  value="D02" >������ݴ���
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelE" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>E��	��˾ԭ��</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="E01">��������
               <input class=title name=SCanclReason TYPE=radio  value="E02" >��ͨ�������
               <input class=title name=SCanclReason TYPE=radio  value="E03">��������ת��
               <input class=title name=SCanclReason TYPE=radio  value="E04" >�����Ϲ�˾��ذ���涨
               <input class=title name=SCanclReason TYPE=radio  value="E05">����δȷ��
               <input class=title name=SCanclReason TYPE=radio  value="E06" >����ͬ��Ŀδ�ᱣȫ����
               <input class=title name=SCanclReason TYPE=radio  value="E07" >������δ�ᱣȫ����
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelF" style= "display: 'none'">            
           <table  class= common>
                <tr class=common>
                    <td class=title>F��	˾�����</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="F01">˾�����
              </TD>
             </tr>
           </table>
        </DIV>
        <Div  id= "divAppCancelG" style= "display: 'none'">     
        <table  class= common>
                <tr class=common>
                    <td class=title>G��	����</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason TYPE=radio  value="G01">����
              </TD>
             </tr>
           </table>
           <table  class= common>
           <tr class=common>
                    <TD class=title colspan=6 > ��ϸ��� </TD>
                </tr>
                <tr class=common>
                    <TD colspan="6" style="padding-left:16px" ><textarea name="CancelReasonContent" id="CancelReasonContent" cols="226%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>       
        </DIV>                                               

    </DIV>

       
        <INPUT VALUE=" �� �� " class=cssButton TYPE=button onclick="cSubmit();">
        <INPUT VALUE=" �� �� " class=cssButton TYPE=button onclick="cCancel();" style= "display: 'none'">

       
        <INPUT VALUE=" �� �� " class=cssButton TYPE=button onclick="returnParent();">

    <!-- ������-->
    <input type="hidden" name="MissionID">
    <input type="hidden" name="SubMissionID">
    <input type="hidden" name="EdorState">
    <input type="hidden" name="EdorMainState">
    <input type="hidden" name="EdorItemState">
    <input type="hidden" name="CancelType">
    <input type="hidden" name="DelFlag">
    <input type="hidden" name="EdorNo">
    <input type="hidden" name="EdorType">
    <input type="hidden" name="ContNo">
    <input type="hidden" name="InsuredNo">
    <input type="hidden" name="PolNo">
    <input type="hidden" name="MakeDate">
    <input type="hidden" name="MakeTime">
    <input type="hidden" name="ActionFlag">
    <input type="hidden" name="OtherNoType">
    <input type="hidden" name="EdorAppDate">
    <input type="hidden" name="EdorValiDate">
    <input type="hidden" name="ActivityID">
    <input type="hidden" name="AppObj" value="I">
    

    <input type="hidden" name="OtherNoType">
    <input type="hidden" name="Apptype">
    <input type="hidden" name="ManageCom">
    <input type="hidden" name="AppntName">
    <input type="hidden" name="PaytoDate">
    <input type="hidden" name="PrtSeq">
    <input type="hidden" name="EdorItemAppDate">
    <input type="hidden" name="GrpContNo">
    <input type="hidden" name="ButtonFlag" value="1">
    <input type="hidden" name="CancelReasonCode">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
