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
  	//alert(document.all('FinItemID').value);  	
  	
  	//当版本状态不为02-维护的时候，增删改按钮为灰色		
  	//alert(VersionState);
		if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}                    
  	//document.all('VersionNo').readOnly=false;       
  }
  catch(ex)
  {
    alert("在DetailFinItemDefInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在DetailFinItemDefInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                   


function initForm()
{
  try
  {    
    initInpBox();    
    initSelBox();
		initDetailDefGrid();    
  	initDetailDefQuery();    
  }
  
  catch(re)
  {
    alert("在DetailFinItemDefInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


 function initDetailDefGrid()
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
      iArray[3][0]="判断条件序号";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[4]=new Array();
      iArray[4][0]="层级条件组合";         			//列名
      iArray[4][1]="90px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="首次条件判断标志";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
  
      iArray[6]=new Array();
      iArray[6][0]="描述";         		//列名
      iArray[6][1]="120px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      

      DetailDefGrid = new MulLineEnter( "document" , "DetailDefGrid" ); 
      DetailDefGrid.mulLineCount = 5;   
      DetailDefGrid.displayTitle = 1;
      DetailDefGrid.canSel=1;
      //选中一行自动给输入框赋值
      DetailDefGrid.selBoxEventFuncName = "ShowDetailDef";			      
      DetailDefGrid.locked = 1;	
			DetailDefGrid.hiddenPlus = 1;
			DetailDefGrid.hiddenSubtraction = 1;

      DetailDefGrid.loadMulLine(iArray);  
      DetailDefGrid.detailInfo="单击显示详细信息";
      //DetailDefGrid.detailClick=reportDetailClick;     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

</script>
