<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PRnewUWManuHealth.jsp
//�����ܣ��б��˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������  zhangxing  ��������     ����ԭ��/����
%>

<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"> 
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <!-- <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> -->
  <script src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./GrpUWManuHealth.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpUWManuHealthInit.jsp"%>
  <title> �б��������¼�� </title>
  <script language="JavaScript">

  function showBodyCheck(ID)
  {

    bodyCheckType1.style.display="none";
    bodyCheckType2.style.display="none";
    bodyCheckType3.style.display="none";
    bodyCheckType4.style.display="none";
    ID.style.display="";
  }

function checkall_lw1()
{
  if(document.fm.lw1.checked == true)
  {
     document.fm.sublw11.checked = true
     document.fm.sublw12.checked = true
     document.fm.sublw13.checked = true
     document.fm.sublw14.checked = true
     document.fm.sublw15.checked = true
  }
  else
  {
     document.fm.sublw11.checked = false
     document.fm.sublw12.checked = false
     document.fm.sublw13.checked = false
     document.fm.sublw14.checked = false
     document.fm.sublw15.checked = false
  }
}

function checkall_lw2()
{
  if(document.fm.lw2.checked == true)
  {
     document.fm.sublw21.checked = true
     document.fm.sublw22.checked = true
     document.fm.sublw23.checked = true
     document.fm.sublw24.checked = true
     document.fm.sublw25.checked = true
  }
  else
  {
     document.fm.sublw21.checked = false
     document.fm.sublw22.checked = false
     document.fm.sublw23.checked = false
     document.fm.sublw24.checked = false
     document.fm.sublw25.checked = false
  }
}

function checkall_lw3()
{
  if(document.fm.lw3.checked == true)
  {
     document.fm.sublw31.checked = true
     document.fm.sublw32.checked = true
     document.fm.sublw33.checked = true
     document.fm.sublw34.checked = true
     document.fm.sublw35.checked = true
  }
  else
  {
     document.fm.sublw31.checked = false
     document.fm.sublw32.checked = false
     document.fm.sublw33.checked = false
     document.fm.sublw34.checked = false
     document.fm.sublw35.checked = false
  }
}

function checkall_lw4()
{
  if(document.fm.lw4.checked == true)
  {
     document.fm.sublw41.checked = true
     document.fm.sublw42.checked = true
     document.fm.sublw43.checked = true
     document.fm.sublw44.checked = true
     document.fm.sublw45.checked = true
  }
  else
  {
     document.fm.sublw41.checked = false
     document.fm.sublw42.checked = false
     document.fm.sublw43.checked = false
     document.fm.sublw44.checked = false
     document.fm.sublw45.checked = false
  }
}

function checkall_kfxt2()
{
   if(document.fm.kfxt2.checked == true)
  {
     document.fm.kfxt21.checked = true
     document.fm.kfxt22.checked = true
  }
  else
  {
     document.fm.kfxt21.checked = false
     document.fm.kfxt22.checked = false
  }
}

function checkall_kfxt3()
{
   if(document.fm.kfxt3.checked == true)
  {
     document.fm.kfxt31.checked = true
     document.fm.kfxt32.checked = true
  }
  else
  {
     document.fm.kfxt31.checked = false
     document.fm.kfxt32.checked = false
  }
}

function checkall_kfxt4()
{
   if(document.fm.kfxt4.checked == true)
  {
     document.fm.kfxt41.checked = true
     document.fm.kfxt42.checked = true
  }
  else
  {
     document.fm.kfxt41.checked = false
     document.fm.kfxt42.checked = false
  }
}


  </script>
  
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>','<%=tGrpContNo%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./GrpUWManuHealthChk.jsp">
    <!-- ���б� -->
    <div class="maxbox">
     <table class= common border=0 width=100%>
    	<tr>
		  <td class= titleImg align= center>��������Ϣ</td>
	    </tr>
    </table>
        <table  class= common align=center>
          <TR  class= common >
        	<TD  class= title>  �����ͬ����  </TD>
          <TD  class= input> <Input class="readonly wid" id="GrpContNo" name=GrpContNo readonly> </TD>
          <TD  class= title>  ��ͬ����  </TD>
          <TD  class= input> <Input class="readonly wid" id="ContNo" name=ContNo readonly> </TD>
          <TD  class= title>  ��ӡ״̬ </TD>
          <TD  class= input>  <Input class="readonly wid" id="PrintFlag" name=PrintFlag > </TD>
          </TR>
    	    <TR  class= common >
          
  
          <INPUT  type= "hidden" class= Common id="PrtNo" name= PrtNo value= "">
          
          <TD  class= title>  �����  </TD>

          <TD  class= input> <Input class="readonly wid" id="InsureNo" name=InsureNo readonly> <!-- onFocus= "easyQueryClickSingle();easyQueryClick();"--> </TD>
          <td class=title>���ԭ��</td><!--CodeData="0|^1|����Լ^2|����/�ܱ�ʷ^3|����^4|�߱���^5|��Ч^6|�ӱ�/���Ӹ�����^7|�����֪" ondblclick="return showCodeListEx('Reason', [this],null,null,null,null,1)"onkeyup="return showCodeListKeyEx('Reason', [this],null,null,null,null,1)"-->
          <td class=input><input class=codeno id="Reason" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" name="Reason" ondblclick="return showCodeList('lcpereason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lcpereason',[this,ReasonName],[0,1]);" ><Input class=codename id="ReasonName" name=ReasonName ></td>
          <TD  class= title>  �Ƿ�ո� </TD>
          <TD  class= input>   <Input class="code wid" id="IfEmpty" name=IfEmpty style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">  </TD>
          </TR>
          <TR  class= common>

          <TD  class= title> �������   </TD>
          <TD  class= input> 
           <!-- <Input class="coolDatePicker wid" dateFormat="short" id="EDate" name=EDate verify="�������|date" > -->
           <Input class="coolDatePicker wid" dateFormat="short" id="EDate" name=EDate  onClick="laydate({elem: '#EDate'});" verify="�������|date"  ><span class="icon"><a onClick="laydate({elem: '#EDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
             </TD>
          <!--<TD  class= title>    ���ҽԺ  </TD>
          <TD  class= input>  <Input class=code name=Hospital ondblclick="showCodeListEx('Hospital',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('Hospital',[this,''],[0,1]);">  </TD>-->
          
          </TR>
         </table>
         </div>
<div class="maxbox">
      <table class= common border=0 width=100%>
        <tr>
		    <td class= titleImg align= center>�������ѡ��</td>
	      </tr>
        <tr class= common>
        <td class= common><input type=radio id="bodyCheck" name="bodyCheck" value='type1'  OnClick= "showBodyCheck(bodyCheckType1);">�ٴ������顢�ĵ�ͼ���򳣹� </td>
        </tr>
        <tr class= common>
        <td class= common><input type=radio id="bodyCheck" name="bodyCheck" value='type2' OnClick= "showBodyCheck(bodyCheckType2);">�ٴ������顢�ĵ�ͼ���򳣹桢�ո�Ѫ�ǡ�Ѫ���桢�ι��ܡ��Ҹα��濹ԭ��Ѫ֬�������� </td>
        </tr>
        <tr class= common>
        <td class= common><input type=radio id="bodyCheck" name="bodyCheck" value='type3' OnClick= "showBodyCheck(bodyCheckType3);">�ٴ������顢�ĵ�ͼ���򳣹桢�ո�Ѫ�ǡ�Ѫ���桢�ι��ܡ��Ҹα��濹ԭ��Ѫ֬�������ܡ��۵ס�B�����ز�͸�� </td>
        </tr>
        <tr class= common>
        <td class= common><input type=radio id="bodyCheck" name="bodyCheck" value='type4' OnClick= "showBodyCheck(bodyCheckType4);">�ٴ������顢�ĵ�ͼ���򳣹桢�ո�Ѫ�ǡ�Ѫ���桢�ι��ܡ��Ҹα��濹ԭ��Ѫ֬�������ܡ��۵ס�B�����ز�͸�ӡ����Ƽ�� </td>
        </tr>
      </table>
</div>

       <table class= common border=0 width=100%>
        <tr>
		    <td class= titleImg align= center>�����Ŀѡ��</td>
	      </tr>
       </table>

       <div id = "bodyCheckType1" style= "display: none">
          <table  class= common>
              <tr class=common>
                <td class= common> <input type=checkbox id='lw1' checked="checked" OnClick="checkall_lw1();">�ٴ�������(��ߡ����ء�Ѫѹ���ڿƼ�顢��Ƽ��) </td>
              <div id='lwdiv'style="display: none">
                <input type=checkbox name='type1' id='sublw11' value='011���' checked="checked">���
                <input type=checkbox name='type1' id='sublw12' value='012����' checked="checked">����
                <input type=checkbox name='type1' id='sublw13' value='013Ѫѹ' checked="checked">Ѫѹ
                <input type=checkbox name='type1' id='sublw14' value='014�ڿƼ��' checked="checked">�ڿƼ��
                <input type=checkbox name='type1' id='sublw15' value='015��Ƽ��' checked="checked">��Ƽ��
              </div>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type1" name='type1' value='070�ĵ�ͼ' checked="checked">�ĵ�ͼ </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type1" name='type1' value='020�򳣹�' checked="checked">�򳣹� </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox id="type1" name='type1' value='140˥������'>˥������ </td>
              </tr>
          </table>
       </div>

       <div id = "bodyCheckType2" style= "display: none">
          <table  class= common>
              <tr class=common>
                <td class= common> <input type=checkbox id='lw2' checked="checked" onclick="checkall_lw2();">�ٴ�������(��ߡ����ء�Ѫѹ���ڿƼ�顢��Ƽ��) </td>
              <div id='lwdiv'style="display: none">
                <input type=checkbox name='type2' id='sublw21' value='011���' checked="checked">���
                <input type=checkbox name='type2' id='sublw22' value='012����' checked="checked">����
                <input type=checkbox name='type2' id='sublw23' value='013Ѫѹ' checked="checked">Ѫѹ
                <input type=checkbox name='type2' id='sublw24' value='014�ڿƼ��' checked="checked">�ڿƼ��
                <input type=checkbox name='type2' id='sublw25' value='015��Ƽ��' checked="checked">��Ƽ��
              </div>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type2" name='type2' value='070�ĵ�ͼ' checked="checked">�ĵ�ͼ </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type2" name='type2' value='020�򳣹�' checked="checked">�򳣹� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id='kfxt2' checked="checked" onclick="checkall_kfxt2();">�ո�Ѫ��(�ո�Ѫ�ǡ�������) </td>
                 <div style= "display: none">
                     <input type=checkbox name='type2' id='kfxt21' value='031�ո�Ѫ��' checked="checked">�ո�Ѫ��
                     <input type=checkbox name='type2' id='kfxt22' value='032������' checked="checked">������
                 </div>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type2" name='type2' value='040Ѫ����' checked="checked">Ѫ���� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type2" name='type2' value='050�ι���' checked="checked">�ι��� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type2" name='type2' value='060�Ҹα��濹ԭ' checked="checked">�Ҹα��濹ԭ </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type2"��name='type2' value='080Ѫ֬' checked="checked">Ѫ֬ </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type2" name='type2' value='090������' checked="checked">������ </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox id="type2" name='type2' value='140˥������'>˥������ </td>
              </tr>
          </table>
       </div>

       <div id = "bodyCheckType3" style= "display: none">
          <table  class= common>
              <tr class=common>
              <td class= common> <input type=checkbox id='lw3' checked="checked" onclick="checkall_lw3();">�ٴ�������(��ߡ����ء�Ѫѹ���ڿƼ�顢��Ƽ��) </td>
              <div id='lwdiv'style="display: none">
                <input type=checkbox name='type3' id='sublw31' value='011���' checked="checked">���
                <input type=checkbox name='type3' id='sublw32' value='012����' checked="checked">����
                <input type=checkbox name='type3' id='sublw33' value='013Ѫѹ' checked="checked">Ѫѹ
                <input type=checkbox name='type3' id='sublw34' value='014�ڿƼ��' checked="checked">�ڿƼ��
                <input type=checkbox name='type3' id='sublw35' value='015��Ƽ��' checked="checked">��Ƽ��
              </div>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='070�ĵ�ͼ' checked="checked">�ĵ�ͼ </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='020�򳣹�' checked="checked">�򳣹� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id='kfxt3' checked="checked" onclick="checkall_kfxt3();">�ո�Ѫ��(�ո�Ѫ�ǡ�������) </td>
                 <div style= "display: none">
                     <input type=checkbox name='type3' id='kfxt31' value='031�ո�Ѫ��' checked="checked">�ո�Ѫ��
                     <input type=checkbox name='type3' id='kfxt32' value='032������' checked="checked">������
                 </div>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='040Ѫ����' checked="checked">Ѫ���� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='050�ι���' checked="checked">�ι��� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='060�Ҹα��濹ԭ' checked="checked">�Ҹα��濹ԭ </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='080Ѫ֬' checked="checked">Ѫ֬ </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='090������' checked="checked">������ </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='100�۵�' checked="checked">�۵� </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='110B��' checked="checked">B�� </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type3" name='type3' value='120�ز�͸��' checked="checked">�ز�͸�� </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox id="type3" name='type3' value='140˥������'>˥������ </td>
              </tr>
          </table>
       </div>

       <div id = "bodyCheckType4" style= "display: none">
          <table  class= common>
              <tr class=common>
              <td class= common> <input type=checkbox id='lw4' checked="checked" onclick="checkall_lw4();">�ٴ�������(��ߡ����ء�Ѫѹ���ڿƼ�顢��Ƽ��) </td>
              <div id='lwdiv'style="display: none">
                <input type=checkbox name='type4' id='sublw41' value='011���' checked="checked">���
                <input type=checkbox name='type4' id='sublw42' value='012����' checked="checked">����
                <input type=checkbox name='type4' id='sublw43' value='013Ѫѹ' checked="checked">Ѫѹ
                <input type=checkbox name='type4' id='sublw44' value='014�ڿƼ��' checked="checked">�ڿƼ��
                <input type=checkbox name='type4' id='sublw45' value='015��Ƽ��' checked="checked">��Ƽ��
              </div>
              </tr>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='070�ĵ�ͼ' checked="checked">�ĵ�ͼ </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='020�򳣹�' checked="checked">�򳣹� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id='kfxt4' checked="checked" onclick="checkall_kfxt4();">�ո�Ѫ��(�ո�Ѫ�ǡ�������) </td>
                <div style= "display: none">
                    <input type=checkbox name='type4' id='kfxt41' value='031�ո�Ѫ��' checked="checked">�ո�Ѫ��
                    <input type=checkbox name='type4' id='kfxt42' value='032������' checked="checked">������
                 </div>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='040Ѫ����' checked="checked">Ѫ���� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='050�ι���' checked="checked">�ι��� </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='060�Ҹα��濹ԭ' checked="checked">�Ҹα��濹ԭ </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='080Ѫ֬' checked="checked">Ѫ֬ </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='090������' checked="checked">������ </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='100�۵�' checked="checked">�۵� </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='110B��' checked="checked">B�� </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='120�ز�͸��' checked="checked">�ز�͸�� </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id="type4" name='type4' value='130���Ƽ��' checked="checked">���Ƽ�� </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox id="type4" name='type4' value='140˥������'>˥������ </td>
              </tr>
          </table>
         
       </div>

       <p>
<div class="maxbox">
       <table class=title>
         <TR  class= common>
           <TD  class= common>&nbsp; ���������Ϣ<br/> </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             &nbsp;<textarea name="Note" id=Note cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>
 </div>
      <INPUT type= "button" id="sure" name= "sure" value="ȷ  ��" class=cssButton onclick="submitForm()">
      
        <input type="hidden" id="EdorNo" name="EdorNo">
        <input type="hidden" id="EdorType" name="EdorType">

    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
