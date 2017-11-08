<%
//Creator :ZhongYan
//Date :2008-08-12
%>
<!--用户校验类-->

<script language="JavaScript">


// 输入框的初始化
function initInpBox()
{
  try
  {
    document.all('VersionNo').value=VersionNo; 
    
  }
  catch(ex)
  {
    alert("在FinItemDefQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在FinItemDefQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initFinItemDefGrid();
  }
  catch(re)
  {
    alert("在FinItemDefQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initFinItemDefGrid()
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
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[3]=new Array();
      iArray[3][0]="科目名称";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[4]=new Array();
      iArray[4][0]="科目类型";         			//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="科目类型";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[6]=new Array();
      iArray[6][0]="科目代码（一级）";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
 
  	  iArray[7]=new Array();
      iArray[7][0]="科目处理方式";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
 
      iArray[8]=new Array();
      iArray[8][0]="描述";         		//列名
      iArray[8][1]="120px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用  
      

      FinItemDefGrid = new MulLineEnter( "document" , "FinItemDefGrid" ); 
      FinItemDefGrid.mulLineCount = 5;   
      FinItemDefGrid.displayTitle = 1;
      FinItemDefGrid.canSel=1;
      FinItemDefGrid.locked = 1;	
			FinItemDefGrid.hiddenPlus = 1;
			FinItemDefGrid.hiddenSubtraction = 1;
      FinItemDefGrid.loadMulLine(iArray);  
      FinItemDefGrid.detailInfo="单击显示详细信息";
      //FinItemDefGrid.detailClick=reportDetailClick;
     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}


</script>
