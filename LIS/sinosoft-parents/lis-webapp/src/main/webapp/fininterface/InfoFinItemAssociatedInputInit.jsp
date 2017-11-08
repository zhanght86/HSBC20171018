<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//创建日期：2008-08-11
//创建人  ：ZhongYan
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
             
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {          	
  
  	document.all('VersionNo').value=VersionNo;
  	//alert(document.all('VersionNo').value);
  	document.all('FinItemID').value=FinItemID;
  	document.all('AssociatedID1').value='';  	  	
  	//alert(document.all('FinItemID').value);  	
  	
  	//当版本状态不为02-维护的时候，增删改按钮为灰色		
  	//alert(VersionState);
		if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			document.all('deletebutton').disabled = true;	
		}                    
  	//document.all('VersionNo').readOnly=false;       
  	//document.all('AssociatedID').readOnly=false;       
  }
  catch(ex)
  {
    alert("在InfoFinItemAssociatedInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误0!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在InfoFinItemAssociatedInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误1!");
  }
}                                   


function initForm()
{
  try
  {
    initInpBox(); 
    initSelBox();
		initItemAssociatedGrid();    
  	initAssociatedQuery();    
  }
  catch(re)
  {
    alert("在InfoFinItemAssociatedInputInit.jsp-->InitForm函数中发生异常:初始化界面错误2!");
  }
}


 function initItemAssociatedGrid()
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
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="科目编号";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[3]=new Array();
      iArray[3][0]="专项编号";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[4]=new Array();
      iArray[4][0]="专项名称";         			//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="描述";         		//列名
      iArray[5][1]="200px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      

      ItemAssociatedGrid = new MulLineEnter( "document" , "ItemAssociatedGrid" ); 
      ItemAssociatedGrid.mulLineCount = 5;   
      ItemAssociatedGrid.displayTitle = 1;
      ItemAssociatedGrid.canSel=1;
      //选中一行自动给输入框赋值
      ItemAssociatedGrid.selBoxEventFuncName = "ShowAssociated";			      
      ItemAssociatedGrid.locked = 1;	
			ItemAssociatedGrid.hiddenPlus = 1;
			ItemAssociatedGrid.hiddenSubtraction = 1;

      ItemAssociatedGrid.loadMulLine(iArray);  
      ItemAssociatedGrid.detailInfo="单击显示详细信息";
      //ItemAssociatedGrid.detailClick=reportDetailClick;     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

</script>
