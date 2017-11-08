<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                          
<script language="JavaScript">

function initInpBox()
{ 
  try
  {
 //   document.all('VoucherID').value = '';
//    document.all('ManageCom').value = '';
 //   document.all('BussDate').value = '';
  }
  catch(ex)
  {
    alert("在OtoFReverseInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();  
		initCodeGrid();
  }
  catch(re)
  {
    alert("在OtoFReverseInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCodeGrid()
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
		      iArray[1][0]="批次号";         		//列名
		      iArray[1][1]="100px";            	//列宽
		      iArray[1][2]=100;            			//列最大值
		      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[2]=new Array();
		      iArray[2][0]="借贷关联号";         		//列名
		      iArray[2][1]="70px";          		//列宽
		      iArray[2][2]=100;            			//列最大值
		      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[3]=new Array();
		      iArray[3][0]="科目代码";     		//列名
		      iArray[3][1]="60px";          		//列宽
		      iArray[3][2]=100;            			//列最大值
		      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[4]=new Array();
		      iArray[4][0]="科目明细代码";         	//列名
		      iArray[4][1]="90px";            	//列宽
		      iArray[4][2]=100;            			//列最大值
		      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      		      		      		
		      iArray[5]=new Array();
		      iArray[5][0]="凭证号";         	//列名
		      iArray[5][1]="60px";            	//列宽
		      iArray[5][2]=100;            			//列最大值
		      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[6]=new Array();
		      iArray[6][0]="险种";         	//列名
		      iArray[6][1]="70px";            	//列宽
		      iArray[6][2]=100;            			//列最大值
		      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[7]=new Array();
		      iArray[7][0]="事务借计金额";         	//列名
		      iArray[7][1]="80px";            	//列宽
		      iArray[7][2]=100;            			//列最大值
		      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[8]=new Array();
		      iArray[8][0]="事务贷计金额";         	//列名
		      iArray[8][1]="80px";            	//列宽
		      iArray[8][2]=100;            			//列最大值
		      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      		      		      
		      iArray[9]=new Array();
		      iArray[9][0]="管理机构";         	//列名
		      iArray[9][1]="50px";            	//列宽
		      iArray[9][2]=100;            			//列最大值
		      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[10]=new Array();
		      iArray[10][0]="记帐日期";         	//列名
		      iArray[10][1]="70px";            	//列宽
		      iArray[10][2]=100;            			//列最大值
		      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[11]=new Array();
		      iArray[11][0]="保单号";         	//列名
		      iArray[11][1]="120px";            	//列宽
		      iArray[11][2]=100;            			//列最大值
		      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[12]=new Array();
		      iArray[12][0]="事务号";         	//列名
		      iArray[12][1]="120px";            	//列宽
		      iArray[12][2]=100;            			//列最大值
		      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[13]=new Array();
		      iArray[13][0]="记账日期";         	//列名
		      iArray[13][1]="120px";            	//列宽
		      iArray[13][2]=100;            			//列最大值
		      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[14]=new Array();
		      iArray[14][0]="凭证类型";         	//列名
		      iArray[14][1]="120px";            	//列宽
		      iArray[14][2]=100;            			//列最大值
		      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许


		      CodeGrid = new MulLineEnter( "document" , "CodeGrid" );   
		      CodeGrid.displayTitle = 5;
		      CodeGrid.canSel = 0;
      		CodeGrid.canChk=1;
		      CodeGrid.locked = 1;
		      CodeGrid.hiddenPlus = 1;
		      CodeGrid.hiddenSubtraction = 1;
		      CodeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
