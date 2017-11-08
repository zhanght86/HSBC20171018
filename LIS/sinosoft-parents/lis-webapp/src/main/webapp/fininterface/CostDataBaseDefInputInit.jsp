<%
//Creator :范昕	
//Date :2008-08-18
%>

<!--用户校验类-->


<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
%>
<script language="JavaScript">

function initInpBox()
{
  try
  {

    fm.reset();
    document.all('VersionNo').value = VersionNo;
    document.all('AcquisitionID').value = AcquisitionID;
    document.all('VersionState').value = VersionState;
  	document.all('DataBaseID').value = '';      
  	document.all('DataBaseID1').value = '';      
    document.all('DataBaseName').value = '';
    document.all('DataBaseOrder').value = '';    
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
    alert("CostDataBaseDefInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{

  try
  {
    initInpBox();
    initSelBox();
    initCostDataBaseDefInputGrid();
  }
  catch(re)
  {
    alert("CostDataBaseDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCostDataBaseDefInputGrid()
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
      iArray[3][0]="数据源编号";         			//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="数据源名称";         			//列名
      iArray[4][1]="250px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="提取序号";         			//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="描述";         			//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      CostDataBaseDefInputGrid = new MulLineEnter( "document" , "CostDataBaseDefInputGrid" ); 
      CostDataBaseDefInputGrid.mulLineCount = 5;   
      CostDataBaseDefInputGrid.displayTitle = 1;
      CostDataBaseDefInputGrid.canSel=1;
      CostDataBaseDefInputGrid.selBoxEventFuncName = "ReturnData";
      CostDataBaseDefInputGrid.locked = 1;	
	 	 	CostDataBaseDefInputGrid.hiddenPlus = 1;
	  	CostDataBaseDefInputGrid.hiddenSubtraction = 1;
      CostDataBaseDefInputGrid.loadMulLine(iArray);  
      CostDataBaseDefInputGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
