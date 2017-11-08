<%
//Creator :范昕	
//Date :2008-08-18
%>

<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
%>
<script language="JavaScript">
<%
  GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	
	if(globalInput == null) {
		out.println("网页超时，请重新登录");
		return;
	}
	String strOperator = globalInput.Operator;
%>   
function initInpBox()
{
  try
  {

    fm.reset();
    document.all('VersionNo').value = VersionNo;
    document.all('VersionState').value = VersionState;
    document.all('AcquisitionID').value = AcquisitionID;
  	document.all('KeyID').value = '';     
  	//document.all('KeyID1').value = '';     
  	document.all('KeyIDName').value = '';    
    document.all('KeyName').value = '';
    document.all('KeyOrder').value = '';    
    document.all('Remark').value = '';   
	if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			
			document.all('deletebutton').disabled = true;	
		}  
  }
  catch(ex)
  {
    alert("进行初始化是出现错误！！！！");
  }
}


// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("CostDataKeyDefInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initCostDataKeyDefInputGrid();
  }
  catch(re)
  {
    alert("CostDataKeyDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCostDataKeyDefInputGrid()
{
	var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="版本编号";    	//列名
      iArray[1][1]="300px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="数据采集编号";         			//列名
      iArray[2][1]="300px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="主键标识";         			//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="主键名称";         			//列名
      iArray[4][1]="250px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="主键序号";         			//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="描述";         			//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      CostDataKeyDefInputGrid = new MulLineEnter( "document" , "CostDataKeyDefInputGrid" ); 
      CostDataKeyDefInputGrid.mulLineCount = 0;   
      CostDataKeyDefInputGrid.displayTitle = 1;
      CostDataKeyDefInputGrid.canSel=1;
      CostDataKeyDefInputGrid.selBoxEventFuncName = "ReturnData";
      CostDataKeyDefInputGrid.locked = 1;	
	 	 	CostDataKeyDefInputGrid.hiddenPlus = 1;
	  	CostDataKeyDefInputGrid.hiddenSubtraction = 1;
      CostDataKeyDefInputGrid.loadMulLine(iArray);  
      CostDataKeyDefInputGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
