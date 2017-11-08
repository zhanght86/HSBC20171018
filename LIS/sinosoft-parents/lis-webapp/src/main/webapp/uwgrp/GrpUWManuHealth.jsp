<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PRnewUWManuHealth.jsp
//程序功能：承保人工核保体检资料录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"> 
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="./GrpUWManuHealth.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="GrpUWManuHealthInit.jsp"%>
  <title> 承保体检资料录入 </title>
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
  <form method=post name=fm target="fraSubmit" action= "./GrpUWManuHealthChk.jsp">
    <!-- 非列表 -->
     <table class= common border=0 width=100%>
    	<tr>
		<td class= titleImg align= center>体检基本信息</td>
	</tr>
    </table>
        <table  class= common align=center>
        <TR  class= common >
        	<TD  class= title>  集体合同号码  </TD>
          <TD  class= input> <Input class="readonly" name=GrpContNo > </TD>
        </TR>
    	<TR  class= common >
          <TD  class= title>  合同号码  </TD>
          <TD  class= input> <Input class="readonly" name=ContNo > </TD>
  
           <INPUT  type= "hidden" class= Common name= PrtNo value= "">
          <TD  class= title>  打印状态 </TD>
          <TD  class= input>  <Input class="readonly" name=PrintFlag > </TD>
           <TD  class= title>  体检人  </TD>

          <TD  class= input> <Input class=code name=InsureNo ondblClick="showCodeListEx('InsureNo',[this,''],[0,1],null,null,null,1);" onkeyup="showCodeListKeyEx('InsureNo',[this,''],[0,1],null,null,null,1);" onFocus= "easyQueryClickSingle();"> <!-- onFocus= "easyQueryClickSingle();easyQueryClick();"--> </TD>
        </TR>
        <TR  class= common>

          <TD  class= title> 体检日期   </TD>
          <TD  class= input>  <Input class="coolDatePicker" dateFormat="short" name=EDate verify="体检日期|date" >  </TD>
          <TD  class= title>    体检医院  </TD>
          <TD  class= input>  <Input class=code name=Hospital ondblclick="showCodeListEx('Hospital',[this,''],[0,1]);" onkeyup="showCodeListKeyEx('Hospital',[this,''],[0,1]);">  </TD>
          <TD  class= title>  是否空腹 </TD>
          <TD  class= input>   <Input class=code name=IfEmpty ondblclick="return showCodeList('YesNo',[this]);" onkeyup="return showCodeListKey('YesNo',[this]);">  </TD>
        </TR>
        <TR  class= common>

        </TR>

      <table class= common border=0 width=100%>
        <tr>
		<td class= titleImg align= center>体检类型选择</td>
	</tr>
        <tr class= common>
          <td class= common><input type=radio name="bodyCheck" value='type1'  OnClick= "showBodyCheck(bodyCheckType1);">临床物理检查、心电图、尿常规 </td>
        </tr>
        <tr class= common>
          <td class= common><input type=radio name="bodyCheck" value='type2' OnClick= "showBodyCheck(bodyCheckType2);">临床物理检查、心电图、尿常规、空腹血糖、血常规、肝功能、乙肝表面抗原、血脂、肾功能 </td>
        </tr>
        <tr class= common>
          <td class= common><input type=radio name="bodyCheck" value='type3' OnClick= "showBodyCheck(bodyCheckType3);">临床物理检查、心电图、尿常规、空腹血糖、血常规、肝功能、乙肝表面抗原、血脂、肾功能、眼底、B超、胸部透视 </td>
        </tr>
        <tr class= common>
          <td class= common><input type=radio name="bodyCheck" value='type4' OnClick= "showBodyCheck(bodyCheckType4);">临床物理检查、心电图、尿常规、空腹血糖、血常规、肝功能、乙肝表面抗原、血脂、肾功能、眼底、B超、胸部透视、妇科检查 </td>
        </tr>
      </table>



       <table class= common border=0 width=100%>
        <tr>
		<td class= titleImg align= center>体检项目选择</td>
	</tr>
       </table>

       <div id = "bodyCheckType1" style= "display: 'none'">
          <table  class= common>
              <tr class=common>
                <td class= common> <input type=checkbox id='lw1' checked="checked" OnClick="checkall_lw1();">临床物理检查(身高、体重、血压、内科检查、外科检查) </td>
              <div id='lwdiv'style="display: 'none'">
                <input type=checkbox name='type1' id='sublw11' value='011身高' checked="checked">身高
                <input type=checkbox name='type1' id='sublw12' value='012体重' checked="checked">体重
                <input type=checkbox name='type1' id='sublw13' value='013血压' checked="checked">血压
                <input type=checkbox name='type1' id='sublw14' value='014内科检查' checked="checked">内科检查
                <input type=checkbox name='type1' id='sublw15' value='015外科检查' checked="checked">外科检查
              </div>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type1' value='070心电图' checked="checked">心电图 </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type1' value='020尿常规' checked="checked">尿常规 </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox name='type1' value='140衰弱测试'>衰弱测试 </td>
              </tr>
          </table>
       </div>

       <div id = "bodyCheckType2" style= "display: 'none'">
          <table  class= common>
              <tr class=common>
                <td class= common> <input type=checkbox id='lw2' checked="checked" onclick="checkall_lw2();">临床物理检查(身高、体重、血压、内科检查、外科检查) </td>
              <div id='lwdiv'style="display: 'none'">
                <input type=checkbox name='type2' id='sublw21' value='011身高' checked="checked">身高
                <input type=checkbox name='type2' id='sublw22' value='012体重' checked="checked">体重
                <input type=checkbox name='type2' id='sublw23' value='013血压' checked="checked">血压
                <input type=checkbox name='type2' id='sublw24' value='014内科检查' checked="checked">内科检查
                <input type=checkbox name='type2' id='sublw25' value='015外科检查' checked="checked">外科检查
              </div>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type2' value='070心电图' checked="checked">心电图 </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type2' value='020尿常规' checked="checked">尿常规 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id='kfxt2' checked="checked" onclick="checkall_kfxt2();">空腹血糖(空腹血糖、糖耐量) </td>
                 <div style= "display: 'none'">
                     <input type=checkbox name='type2' id='kfxt21' value='031空腹血糖' checked="checked">空腹血糖
                    <input type=checkbox name='type2' id='kfxt22' value='032糖耐量' checked="checked">糖耐量
                 </div>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type2' value='040血常规' checked="checked">血常规 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type2' value='050肝功能' checked="checked">肝功能 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type2' value='060乙肝表面抗原' checked="checked">乙肝表面抗原 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type2' value='080血脂' checked="checked">血脂 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type2' value='090肾功能' checked="checked">肾功能 </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox name='type2' value='140衰弱测试'>衰弱测试 </td>
              </tr>
          </table>
       </div>

       <div id = "bodyCheckType3" style= "display: 'none'">
          <table  class= common>
              <tr class=common>
              <td class= common> <input type=checkbox id='lw3' checked="checked" onclick="checkall_lw3();">临床物理检查(身高、体重、血压、内科检查、外科检查) </td>
              <div id='lwdiv'style="display: 'none'">
                <input type=checkbox name='type3' id='sublw31' value='011身高' checked="checked">身高
                <input type=checkbox name='type3' id='sublw32' value='012体重' checked="checked">体重
                <input type=checkbox name='type3' id='sublw33' value='013血压' checked="checked">血压
                <input type=checkbox name='type3' id='sublw34' value='014内科检查' checked="checked">内科检查
                <input type=checkbox name='type3' id='sublw35' value='015外科检查' checked="checked">外科检查
              </div>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='070心电图' checked="checked">心电图 </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='020尿常规' checked="checked">尿常规 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id='kfxt3' checked="checked" onclick="checkall_kfxt3();">空腹血糖(空腹血糖、糖耐量) </td>
                 <div style= "display: 'none'">
                     <input type=checkbox name='type3' id='kfxt31' value='031空腹血糖' checked="checked">空腹血糖
                    <input type=checkbox name='type3' id='kfxt32' value='032糖耐量' checked="checked">糖耐量
                 </div>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='040血常规' checked="checked">血常规 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='050肝功能' checked="checked">肝功能 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='060乙肝表面抗原' checked="checked">乙肝表面抗原 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='080血脂' checked="checked">血脂 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='090肾功能' checked="checked">肾功能 </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='100眼底' checked="checked">眼底 </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='110B超' checked="checked">B超 </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type3' value='120胸部透视' checked="checked">胸部透视 </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox name='type3' value='140衰弱测试'>衰弱测试 </td>
              </tr>
          </table>
       </div>

       <div id = "bodyCheckType4" style= "display: 'none'">
          <table  class= common>
              <tr class=common>
              <td class= common> <input type=checkbox id='lw4' checked="checked" onclick="checkall_lw4();">临床物理检查(身高、体重、血压、内科检查、外科检查) </td>
              <div id='lwdiv'style="display: 'none'">
                <input type=checkbox name='type4' id='sublw41' value='011身高' checked="checked">身高
                <input type=checkbox name='type4' id='sublw42' value='012体重' checked="checked">体重
                <input type=checkbox name='type4' id='sublw43' value='013血压' checked="checked">血压
                <input type=checkbox name='type4' id='sublw44' value='014内科检查' checked="checked">内科检查
                <input type=checkbox name='type4' id='sublw45' value='015外科检查' checked="checked">外科检查
              </div>
              </tr>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='070心电图' checked="checked">心电图 </td>
              </tr>
              <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='020尿常规' checked="checked">尿常规 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox id='kfxt4' checked="checked" onclick="checkall_kfxt4();">空腹血糖(空腹血糖、糖耐量) </td>
                <div style= "display: 'none'">
                    <input type=checkbox name='type4' id='kfxt41' value='031空腹血糖' checked="checked">空腹血糖
                    <input type=checkbox name='type4' id='kfxt42' value='032糖耐量' checked="checked">糖耐量
                 </div>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='040血常规' checked="checked">血常规 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='050肝功能' checked="checked">肝功能 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='060乙肝表面抗原' checked="checked">乙肝表面抗原 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='080血脂' checked="checked">血脂 </td>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='090肾功能' checked="checked">肾功能 </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='100眼底' checked="checked">眼底 </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='110B超' checked="checked">B超 </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='120胸部透视' checked="checked">胸部透视 </td>
              </tr>
              </tr>
               <tr class=common>
                <td class= common> <input type=checkbox name='type4' value='130妇科检查' checked="checked">妇科检查 </td>
              </tr>
              <tr class=common>
                <td class= common><input type=checkbox name='type4' value='140衰弱测试'>衰弱测试 </td>
              </tr>
          </table>
       </div>

       <p>

       <table class=title>
         <TR  class= common>
           <TD  class= common> 其他体检信息 </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             <textarea name="Note" cols="120" rows="3" class="common" >
             </textarea>
           </TD>
         </TR>
      </table>

      <INPUT type= "button" name= "sure" value="确  认" class=cssButton onclick="submitForm()">

    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
