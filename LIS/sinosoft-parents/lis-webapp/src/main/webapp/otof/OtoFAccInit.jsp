<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                          
<script language="JavaScript">

function initInpBox()
{ 
  try
  {
  	fm.RiskCode.value = '';
    fm.bDate.value = '';  
    fm.eDate.value = ''; 
    fm.ManageCom.value='';
    fm.RiskCodeName.value='';

  }
  catch(ex)
  {
    alert("在OtoFAccInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();  
		//initCodeGrid();
  }
  catch(re)
  {
    alert("在OtoFAccInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
		      iArray[1][0]="管理机构";         		//列名
		      iArray[1][1]="60px";            	//列宽
		      iArray[1][2]=100;            			//列最大值
		      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[2]=new Array();
		      iArray[2][0]="险种名称";         		//列名
		      iArray[2][1]="60px";          		//列宽
		      iArray[2][2]=100;            			//列最大值
		      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[3]=new Array();
		      iArray[3][0]="提取日期";     		//列名
		      iArray[3][1]="60px";          		//列宽
		      iArray[3][2]=100;            			//列最大值
		      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[4]=new Array();
		      iArray[4][0]="保费收入";         	//列名
		      iArray[4][1]="70px";            	//列宽
		      iArray[4][2]=100;            			//列最大值
		      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      		      		      		
		      iArray[5]=new Array();
		      iArray[5][0]="账户初始费用";         	//列名
		      iArray[5][1]="70px";            	//列宽
		      iArray[5][2]=100;            			//列最大值
		      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[6]=new Array();
		      iArray[6][0]="保单管理费";         	//列名
		      iArray[6][1]="70px";            	//列宽
		      iArray[6][2]=100;            			//列最大值
		      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[7]=new Array();
		      iArray[7][0]="风险管理费";         	//列名
		      iArray[7][1]="70px";            	//列宽
		      iArray[7][2]=100;            			//列最大值
		      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[8]=new Array();
		      iArray[8][0]="保单利息";         	//列名
		      iArray[8][1]="70px";            	//列宽
		      iArray[8][2]=100;            			//列最大值
		      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      		      		      
		      iArray[9]=new Array();
		      iArray[9][0]="退保金";         	//列名
		      iArray[9][1]="50px";            	//列宽
		      iArray[9][2]=100;            			//列最大值
		      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[10]=new Array();
		      iArray[10][0]="死亡给付";         	//列名
		      iArray[10][1]="50px";            	//列宽
		      iArray[10][2]=100;            			//列最大值
		      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[11]=new Array();
		      iArray[11][0]="满期给付";         	//列名
		      iArray[11][1]="50px";            	//列宽
		      iArray[11][2]=100;            			//列最大值
		      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      
		      iArray[12]=new Array();
		      iArray[12][0]="退保费用及部分领取费用";         	//列名
		      iArray[12][1]="70px";            	//列宽
		      iArray[12][2]=100;            			//列最大值
		      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		      



		      CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 
		      CodeGrid.mulLineCount = 5;   
		      CodeGrid.displayTitle = 1;
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
