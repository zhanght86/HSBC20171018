
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {   
    	
  }
  catch(ex)
  {
    alert("在InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在LMYieldInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initBonusGrid();
		initCanclePolGrid();
		
  }
  catch(re)
  {
    alert("InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initBonusGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

       iArray[1]=new Array();
      iArray[1][0]="团单合同号";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户代码";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="客户标识";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="团单险种号";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	    iArray[5]=new Array();
      iArray[5][0]="险种";         		//列名
      iArray[5][1]="30px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
	    iArray[6]=new Array();
      iArray[6][0]="保险期间";         		//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0; 									//是否允许输入,1表示允许，0表示不允许      
 
 	    iArray[7]=new Array();
      iArray[7][0]="期间单位";         		//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0; 			
      
      iArray[8]=new Array();
      iArray[8][0]="回报方式";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0; 			
      
      iArray[9]=new Array();
      iArray[9][0]="回报利率/比例";         		//列名
      iArray[9][1]="90px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0; 			
      
      iArray[10]=new Array();
      iArray[10][0]="合同起始日";         		//列名
      iArray[10][1]="90px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0; 			
       
      iArray[11]=new Array();
      iArray[11][0]="合同终止日";         		//列名
      iArray[11][1]="90px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0; 	
      
      iArray[12]=new Array();
      iArray[12][0]="合同给付金额";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0; 
      
      iArray[13]=new Array();
      iArray[13][0]="说明";         		//列名
      iArray[13][1]="60px";            		//列宽
      iArray[13][2]=1000;            			//列最大值
      iArray[13][3]=0; 
      BonusGrid = new MulLineEnter( "fm" , "BonusGrid" ); 
      //这些属性必须在loadMulLine前
      BonusGrid.mulLineCount = 0;   
      BonusGrid.displayTitle = 1;
      BonusGrid.locked = 1;
      BonusGrid.canSel = 1;
      BonusGrid.hiddenPlus=1;
      BonusGrid.loadMulLine(iArray);  
      BonusGrid.selBoxEventFuncName="showMul";

      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
//个别分红退保
function initCanclePolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

       iArray[1]=new Array();
      iArray[1][0]="团单合同号";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户代码";         		//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="团单险种号";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="险种编码";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	    iArray[5]=new Array();
      iArray[5][0]="帐户经过年数";         		//列名
      iArray[5][1]="30px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
	    iArray[6]=new Array();
      iArray[6][0]="占账户金额比例";         		//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0; 									//是否允许输入,1表示允许，0表示不允许      

      CanclePolGrid = new MulLineEnter( "fm" , "CanclePolGrid" ); 
      //这些属性必须在loadMulLine前
      CanclePolGrid.mulLineCount = 0;   
      CanclePolGrid.displayTitle = 1;
      CanclePolGrid.locked = 1;
      CanclePolGrid.canSel = 1;
      CanclePolGrid.hiddenPlus=1;
      CanclePolGrid.loadMulLine(iArray);  
      CanclePolGrid.selBoxEventFuncName="showMul2";
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
