<%
//程序名称：NoBackPolQueryInit.jsp
//程序功能：外包录入未回传数据查询
//创建日期：2007-09-28 15:10
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容
%>                         
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
    alert("InputErrQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCodeGrid()    							//函数名为init+MulLine的对象名ObjGrid
{                           						
	var iArray = new Array(); 						//数组放置各个列
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="序号";         			//列名
    	iArray[0][1]="30px";         			//列宽
    	iArray[0][2]=100;         				//列最大值
    	iArray[0][3]=0;         					//1表示允许该列输入，0表示只读且不响应Tab键
    																		//2表示为容许输入且颜色加深

	    iArray[1]=new Array();
	    iArray[1][0]="印刷号";    			//列名（序号列，第1列）
	    iArray[1][1]="100px";  						//列宽
	    iArray[1][2]=30;      						//列最大值
	    iArray[1][3]=0;   								//1表示允许该列输入，0表示只读且不响应Tab键
	                   										

	    iArray[2]=new Array();          	
	    iArray[2][0]="管理机构";  				//列名（第2列）
	    iArray[2][1]="145px";  						//列宽
	    iArray[2][2]=10;        					//列最大值
	    iArray[2][3]=0;       						//是否允许输入,1表示允许，0表示不允许

	    iArray[3]=new Array();
	    iArray[3][0]="扫描日期";    			//列名（序号列，第1列）
	    iArray[3][1]="100px";  						//列宽
	    iArray[3][2]=30;      						//列最大值
	    iArray[3][3]=0;   								//1表示允许该列输入，0表示只读且不响应Tab键


	    iArray[4]=new Array();
	    iArray[4][0]="扫描时间";    			//列名（序号列，第1列）
	    iArray[4][1]="100px";  						//列宽
	    iArray[4][2]=30;      						//列最大值
	    iArray[4][3]=0;   								//1表示允许该列输入，0表示只读且不响应Tab键	    
	    
	    //生成对象区，规则：对象名=new MulLineEnter(“表单名”,”对象名”); 
	    CodeGrid= new MulLineEnter( "document" , "CodeGrid" ); 
	    //设置属性区 (需要其它特性，在此设置其它属性)
	    CodeGrid.mulLineCount = 5 ; 		//行属性：设置行数=10    
	    CodeGrid.displayTitle = 1;   		//标题属性：1显示标题(缺省值), 0隐藏标题        
	    //对象初始化区：调用对象初始化方法，属性必须在此前设置
	    CodeGrid.hiddenSubtraction=1;
	    CodeGrid.hiddenPlus=1;
	    //CodeGrid.canChk=1;
	    CodeGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{ alert(ex); }
}

function initInpBox()
{ 
  try
  {
    document.all('BPOID').value = '';
		document.all('ManageCom').value = '';
    document.all('StartDate').value = '';
		document.all('EndDate').value = '';

  }
  catch(ex)
  {
    alert("InputErrQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}    
    
</script>
