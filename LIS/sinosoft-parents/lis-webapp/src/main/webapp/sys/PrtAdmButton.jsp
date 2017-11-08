<!--
*******************************************************
* 程序名称：ProposalButton.jsp
* 程序功能：页面通用按钮
* 创建日期：2002-05-20
* 更新记录：  更新人    更新日期     更新原因/内容
*             欧阳晟   2002-05-20    新建
*******************************************************
-->               
<table class="common" align=center>
  <tr>
    
    
    <td class=button width="50%" align=center>
      <img class=button alt="重置" src='../common/images/butReset.gif' 
				onmouseover="return changeImage(this,'../common/images/butResetOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butReset.gif');"
				onclick="return resetForm();"></img>
    </td>
    
    <td class=button width="50%" align=center>
      <img id="saveImg" class=button alt="保存" src='../common/images/butSave.gif' 
				onmouseover="return changeImage(this,'../common/images/butSaveOver.gif');"
				onmouseout="return changeImage(this,'../common/images/butSave.gif');"
				onclick="return saveForm();"></img>
    </td>
  </tr>
</table>
