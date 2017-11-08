<%
//程序名称：LLReportInput.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
     %>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
    document.all('PolNo').value = '';
    document.all('RptorName').value = '';
    document.all('RptorAddress').value = '';
    document.all('RptorPhone').value = '';
    document.all('RptorMobile').value = '';
    document.all('Relation').value = '';
    document.all('RptDate').value = '';
    document.all('RptMode').value = '';
    document.all('AccidentSite').value = '';
    document.all('AccidentReason').value = '';
    document.all('AccidentCourse').value = '';
    document.all('AccidentDate').value = '';
    document.all('MngCom').value = '';
    document.all('NoRgtReason').value = '';
    document.all('AgencyGroup').value = '';
    document.all('AgencyCode').value = '';
    document.all('Remark').value = '';
  }
  catch(ex)
  {
    alert("在PEdorInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在PEdorInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("PEdorInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>