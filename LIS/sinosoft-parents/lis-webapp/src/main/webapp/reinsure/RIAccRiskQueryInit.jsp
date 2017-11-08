<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2007-10-17
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
 String pageFlag = request.getParameter("PageFlag"); 
 String accumulateNo = request.getParameter("AccumulateNo"); 
%>
<script type="text/javascript">
function initInpBox()
{
  try
  { 
  	fm.AccumulateNo.value = "<%=accumulateNo%>";
  }
  catch(ex)
  {
    myAlert("进行初始化是出现错误！！！！");
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
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initAccumulateGrid();
  }
  catch(re)
  {
    myAlert("3CertifyDescInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAccumulateGrid()
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
	      iArray[1][0]="分出责任编码";    	
	      iArray[1][1]="120px";         
	      iArray[1][2]=100;            	
	      iArray[1][3]=0;              	
	
	      iArray[2]=new Array();
	      iArray[2][0]="分出责任名称";      
	      iArray[2][1]="100px";         
	      iArray[2][2]=100;            	
	      iArray[2][3]=0;    
	      
	      iArray[3]=new Array();
	      iArray[3][0]="险种编码"; 
	      iArray[3][1]="100px"; 
	      iArray[3][2]=100;     
	      iArray[3][3]=0;  
	      
	      iArray[4]=new Array();
	      iArray[4][0]="险种名称"; 
	      iArray[4][1]="100px"; 
	      iArray[4][2]=100;     
	      iArray[4][3]=0; 

	      iArray[5]=new Array();
	      iArray[5][0]="责任编码"; 
	      iArray[5][1]="100px"; 
	      iArray[5][2]=100;     
	      iArray[5][3]=0;  
	      
	      iArray[6]=new Array();
	      iArray[6][0]="责任名称"; 
	      iArray[6][1]="100px"; 
	      iArray[6][2]=100;     
	      iArray[6][3]=0;
	      
	      iArray[7]=new Array();
	      iArray[7][0]="GEB标志编号"; 
	      iArray[7][1]="100px"; 
	      iArray[7][2]=100;     
	      iArray[7][3]=3; 
				
		  iArray[8]=new Array();
	      iArray[8][0]="GEB标志"; 
	      iArray[8][1]="100px"; 
	      iArray[8][2]=100;     
	      iArray[8][3]=3; 
				
	      AccumulateGrid = new MulLineEnter( "fm" , "AccumulateGrid" ); 
	      AccumulateGrid.mulLineCount = 0;   
	      AccumulateGrid.displayTitle = 1;
	      AccumulateGrid.canSel=0;
	      AccumulateGrid.locked = 1;	
		  AccumulateGrid.hiddenPlus = 1;
		  AccumulateGrid.hiddenSubtraction = 1;
	      AccumulateGrid.loadMulLine(iArray);  
	      AccumulateGrid.detailInfo="单击显示详细信息";
     
      }
      catch(ex)
      {
        myAlert(ex);
      }
}
</script>
