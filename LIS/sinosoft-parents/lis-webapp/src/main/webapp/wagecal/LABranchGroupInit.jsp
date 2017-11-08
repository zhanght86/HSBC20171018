<%
//程序名称：LABranchGroupInit.jsp
//程序功能：
//创建日期：
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('AgentGroup').value = '';
    document.all('Name').value = '';
    document.all('ManageCom').value = '';
    document.all('UpBranch').value = '';
    document.all('BranchAttr').value = '';
    //document.all('BranchType').value = '';
    document.all('BranchLevel').value = '';
    document.all('BranchManager').value = '';
    document.all('BranchManagerName').value = '';
    //document.all('BranchAddressCode').value = '';
    document.all('BranchAddress').value = '';
    document.all('BranchPhone').value = '';
    document.all('BranchFax').value = '';
    document.all('BranchZipcode').value = '';
    document.all('FoundDate').value = '';
    document.all('EndDate').value = '';
    document.all('EndFlag').value = '';
    //document.all('CertifyFlag').value = '';
    document.all('FieldFlag').value = '';
    document.all('UpBranchAttr').value = '';
    document.all('Operator').value = '';
    
    if (top.opener == null)
    {

      document.all('BranchType').value = getBranchType();
    }else
    {
      document.all('BranchType').value = top.opener.fm.BranchType.value;
    }

  }
  catch(ex)
  {
    alert("在LABranchGroupInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在LABranchGroupInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
  }
  catch(re)
  {
    alert("LABranchGroupInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>
