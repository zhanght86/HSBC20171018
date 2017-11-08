 <%
//Creator :Fanxin
//Date :2008-09-16
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
    alert("在AssociatedDirectItemDefQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在AssociatedDirectItemDefQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initAssociatedDirectItemDefGrid();
  }
  catch(re)
  {
    alert("在AssociatedDirectItemDefQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initAssociatedDirectItemDefGrid()
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
      iArray[2][0]="专项表字段标识";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="上游数据来源表名";         			//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[4]=new Array();
      iArray[4][0]="上游数据来源字段";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许      
            

      

      AssociatedDirectItemDefGrid = new MulLineEnter( "document" , "AssociatedDirectItemDefGrid" ); 
      AssociatedDirectItemDefGrid.mulLineCount = 5;   
      AssociatedDirectItemDefGrid.displayTitle = 1;
      AssociatedDirectItemDefGrid.canSel=1;
      AssociatedDirectItemDefGrid.locked = 1;	
	    AssociatedDirectItemDefGrid.hiddenPlus = 1;
	    AssociatedDirectItemDefGrid.hiddenSubtraction = 1;
      AssociatedDirectItemDefGrid.loadMulLine(iArray);  
      AssociatedDirectItemDefGrid.detailInfo="单击显示详细信息";
      //AssociatedItemDefGrid.detailClick=reportDetailClick;
     
      }
      
      catch(ex)
      {
        alert("初始化AssociatedDirectItemDefGrid时出错："+ ex);
      }

}


</script>
