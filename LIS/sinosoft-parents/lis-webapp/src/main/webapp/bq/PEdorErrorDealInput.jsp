<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2009-01-11, 2009-01-14
 * @direction: ��ȫ������������������
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>��ȫ��촦������</title>
    <!-- ����������ʽ -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
     <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- �������ýű� -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- ˽�����ýű� -->
    <script language="JavaScript" src="PEdorErrorDeal.js"></script>
    <%@ include file="PEdorErrorDealInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()">
    <form name="fm" id="fm" action="./PEdorErrorDealSubmit.jsp" method="post" target="fraSubmit">
        <!-- ������в�ѯ�۵� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllSearch)"></td>
                <td class="titleImg">�������ѯ����</td>
            </tr>
        </table>
        <!-- ������в�ѯ¼�� -->
        <div id="divEdorAllSearch" style="display:''" class="maxbox1">
            <table class="common" >
                <tr class="common">
                    <td class="title">��ȫ�����</td>
                    <td class="input"><input type="text" class="common wid" name="EdorAcceptNo1" id="EdorAcceptNo1"></td>
                    <td class="title">������</td>
                    <td class="input"><input type="text" class="common wid" name="OtherNo1" id="OtherNo1"></td>
                    <td class="title">�������</td>
                    <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="ManageCom1" id="ManageCom1" ondblclick="showCodeList('station',[this,ManageComName1],[0,1],null,null,null,0,256)" onclick="showCodeList('station',[this,ManageComName1],[0,1],null,null,null,0,256)" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1],null,null,null,0,256)"><input class="codename" name="ManageComName1" id="ManageComName1" readonly></td>
              </tr>
                <tr class="common">
                    <td class="title">��������</td>
                    <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="LetterType" id="LetterType" ondblclick="showCodeList('lettertype',[this,LetterTypeName],[0,1],null,codeSql,1,0,256)" onclick="showCodeList('lettertype',[this,LetterTypeName],[0,1],null,codeSql,1,0,256)" onkeyup="showCodeListKey('LetterType',[this,LetterTypeName],[0,1],null,codeSql,1,0,256)"><input type="text" class="codename" name="LetterTypeName" id="LetterTypeName" readonly></td>
                    <td class="title">����Ա</td>
                    <td class="input"><input type="text" class="common wid" name="AcceptOperator1" id="AcceptOperator1"></td>
                    <td class="title">��������</td>
                    <td class="input"><!--<input type="text" class= "multiDatePicker" dateFormat="short" name="MakeDate1">-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate1'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=MakeDate1 id="MakeDate1"><span class="icon"><a onClick="laydate({elem: '#MakeDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </td>
                </tr>
            </table></div>
            <!-- ��ѯ���ݲ�����ť -->
            <!--<input type="button" class="cssButton" value=" �� ѯ " onclick="queryAllGrid(1)">-->
        <a href="javascript:void(0);" class="button" onClick="queryAllGrid(1)">��    ѯ</a>
        <!-- ��������۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divAllList)"></td>
                <td class="titleImg">�����б�</td>
           </tr>
        </table>
        <!-- ������н��չ�� -->
        <div id="divAllList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanAllGrid"></span></td>
                </tr>
            </table>
            <!-- ������н����ҳ -->
            <!--<div id="divTurnPageAllGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton" value="��  ҳ" onclick="turnPageAllGrid.firstPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageAllGrid.previousPage()">
                <input type="button" class="cssButton" value="��һҳ" onclick="turnPageAllGrid.nextPage()">
                <input type="button" class="cssButton" value="β  ҳ" onclick="turnPageAllGrid.lastPage()">
            </div>-->
        </div>
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="LoginOperator">
        <input type="hidden" name="LoginManageCom">
        <input type="hidden" name="PrtSerNo">
      <Div  id= "divForceBack" style= "display: none">
       <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divAppCancelInfo)"></td>
                <td class="titleImg">����ԭ��</td>
           </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>����ԭ��</td>
                    <td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=MCanclReason id=MCanclReason verify="����ԭ��|NotNull&Code:edorcancelmreason" ondblclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onkeyup="return showCodeListKey('edorcancelmreason',[this,SCanclReasonName],[0,1]);"><input class=codename name=SCanclReasonName id=SCanclReasonName readonly></td>
                    <td class="title"><Div id="divCancelMainReason" style="display:none"> ����ԭ�� </Div></td>
                    <td class="input"><Div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" CodeData=""  name="SCanclReason" verify="�޸�ԭ��|Code:edorcancelsreason" ondblclick="return showCodeListEx('edorcancelsreason',[this,MCanclReasonName],[0,1])" onkeyup="return showCodeListKeyEx('edorcancelsreason',[this,MCanclReasonName],[0,1])"><input class="CodeName" name="MCanclReasonName" readonly></Div></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </DIV>
         <Div  id= "divAppCancelA" style= "display: none">            
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
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="A01">�ͻ�����
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="A02" >��ͥì��
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="A03" >�ͻ�����
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelB" style= "display: none">            
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
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="B01">ԽȨ����
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="B02" >����������
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="B03" >���ڴ�ǩ������
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelC" style= "display: none">            
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
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="C01">ԽȨ����
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="C02" >������
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="C03" >���ڴ�ǩ������
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelD" style= "display: none">            
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
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="D01">ϵͳ����
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="D02" >������ݴ���
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelE" style= "display: none">            
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
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E01">��������
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E02" >��ͨ�������
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E03">��������ת��
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E04" >�����Ϲ�˾��ذ���涨
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E05">����δȷ��
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E06" >����ͬ��Ŀδ�ᱣȫ����
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E07" >������δ�ᱣȫ����
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelF" style= "display: none">            
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
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="F01">˾�����
              </TD>
             </tr>
           </table>
        </DIV>
        <Div  id= "divAppCancelG" style= "display: none">     
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
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="G01">����
              </TD>
             </tr>
           </table>
           <table  class= common>
           <tr class=common>
                    <TD class=title colspan=6 > ��ϸ��� </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px" colspan=6 ><textarea name="CancelReasonContent" id=CancelReasonContent cols="180%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>       
        </DIV>   
       </DIV>
       <Div  id= "divBack" style= "display: none">      
        <!-- �ύ���ݲ�����ť -->
        <!--<input type="button" class="cssButton" value="  ���ڻ���  " onclick="dealSpotCheck()">
        <input type="button" class="cssButton" value=" ��ȫ��ϸ��ѯ " onclick="viewEdorDetail()">
        <input type="button" class="cssButton" value="  �������� " onclick="dealCheck()">--><br>
        <a href="javascript:void(0);" class="button" onClick="dealSpotCheck();">���ڻ���</a>
        <a href="javascript:void(0);" class="button" onClick="viewEdorDetail();">��ȫ��ϸ��ѯ</a>
        <a href="javascript:void(0);" class="button" onClick="dealCheck();">��������</a>
        </DIV><br>
       
       <Div  id= "divForceBankBack" style= "display: none">       
        <!--<input type="button" class="cssButton" value=" ��ȫ��ϸ��ѯ " onclick="viewEdorDetail()">
        <input type="button" class="cssButton" value="  �������� " onclick="dealCheck()">-->
        <a href="javascript:void(0);" class="button" onClick="viewEdorDetail();">��ȫ��ϸ��ѯ</a>
        <a href="javascript:void(0);" class="button" onClick="dealCheck();">��������</a>
       </DIV>  
        <input type="hidden" name="ActionFlag" id=ActionFlag>
        <input type="hidden" name="CancelReasonCode" id=CancelReasonCode> 
    </form>
    <!-- ͨ��������Ϣ�б� -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and othersign =#1#";
</script>
