<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                         

<script language="JavaScript">

function initForm()
{
  try
  {
    
    initGrpGrid();
    initSelfGrpGrid();
    initSexGrpGrid();
    easyQueryClick();
  }
  catch(re)
  {
    alert("InitForm函数中发生异常:初始化界面错误!");
  }
}
function initSexGrpGrid()
{
var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="性别";         	//列名
      iArray[1][1]="140px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="占百分比";         	//列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

 
      SexGrpGrid = new MulLineEnter( "fm" , "SexGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SexGrpGrid.mulLineCount = 2;   
      SexGrpGrid.displayTitle = 1;
      SexGrpGrid.locked = 1;
      SexGrpGrid.canSel = 0;
      SexGrpGrid.canChk = 0;
      SexGrpGrid.hiddenPlus = 1;
      SexGrpGrid.hiddenSubtraction = 1;         
      SexGrpGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }

}
function initSelfGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="年龄段";         	//列名
      iArray[1][1]="140px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="占百分比";         	//列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

 
      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //这些属性必须在loadMulLine前
      SelfGrpGrid.mulLineCount = 7;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 0;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;         
      SelfGrpGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            	//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="职业类别";         	//列名
      iArray[1][1]="140px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="占百分比";         	//列名
      iArray[2][1]="120px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

 
      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount = 7;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 0;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;          
      GrpGrid.loadMulLine(iArray);  
      
      
      //这些操作必须在loadMulLine后面
      //GrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
