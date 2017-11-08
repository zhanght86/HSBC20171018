<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();
  }
  catch(re)
  {
    alert("mulLineInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
  function initCodeGrid()    //函数名为init+MulLine的对象名ObjGrid
   {
	var iArray = new Array(); //数组放置各个列
	try
	{
	      iArray[0]=new Array();
	      iArray[0][0]="序号";      //列名（序号列，第1列）
	      iArray[0][1]="30px";  	//列宽
	      iArray[0][2]=10;      	//列最大值
	      iArray[0][3]=0;   	//1表示允许该列输入，0表示只读且不响应Tab键
	                     		// 2 表示为容许输入且颜色加深.
	                           
	      iArray[1]=new Array();
	      iArray[1][0]="公司名称";  //列名（第2列）
	      iArray[1][1]="30px";  	//列宽
	      iArray[1][2]=10;        	//列最大值
	      iArray[1][3]=1;       	//是否允许输入,1表示允许，0表示不允许
	         			//后续可以添加N列，如上设置
	      iArray[2]=new Array();
	      iArray[2][0]="服务器名称";  //列名（第2列）
	      iArray[2][1]="30px";  	//列宽
	      iArray[2][2]=10;        	//列最大值
	      iArray[2][3]=1;       	//是否允许输入,1表示允许，0表示不允许
	         			//后续可以添加N列，如上设置
	
	      //生成对象区，规则：对象名=new MulLineEnter(“表单名”,”对象名”); 
	      CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	      //设置属性区 (需要其它特性，在此设置其它属性)
	      CodeGrid.mulLineCount = 5 ; 	//行属性：设置行数=5    
	      CodeGrid.displayTitle = 1;   	//标题属性：1显示标题 (缺省值) ,0隐藏标题        
	      //对象初始化区：调用对象初始化方法，属性必须在此前设置
	      CodeGrid.hiddenSubtraction=1;
	      CodeGrid.hiddenPlus=1;
	      CodeGrid.canChk=1;
	      CodeGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{ alert(ex); }
    }
function initInpBox()
{ 
  try
  {
	document.all('ManageCom').value = ''	;
	document.all('Host_Name').value = '';
  }
  catch(ex)
  {
    alert("在mulLineInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}    
    
</script>
