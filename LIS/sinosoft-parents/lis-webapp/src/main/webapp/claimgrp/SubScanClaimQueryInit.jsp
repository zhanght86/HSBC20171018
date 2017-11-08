<%
//程序名称：ClaimGetQueryInit.jsp
//程序功能：
//创建日期：2003-4-2
//创建人  ：lh
//修改人：刘岩松
//修改日期：2004-2-17
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
var tClaimNo = "<%=tClaimNo%>";
var tCustomerNo = "<%=tCustomerNo%>";
var tCustomerName = "<%=tCustomerName%>";

// 下拉框的初始化
function initSelBox()
{
  try
  {       	   
          
  }
  catch(ex)
  {
    alert("在ClaimGetQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initSelBox();	 		
     
     if (tClaimNo==null && tCustomerNo==null)
     {
        fm.RgtNo.value="";        
        fm.InsuredNo.value="";                  
     }else
     {        
        fm.InsuredNo.value=tCustomerNo; 
        fm.InsuredName.value=tCustomerName;
        fm.RgtNo.value=tClaimNo;                        
     }  		
		
  }
  catch(re)
  {
    alert("ClaimGetQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

</script>