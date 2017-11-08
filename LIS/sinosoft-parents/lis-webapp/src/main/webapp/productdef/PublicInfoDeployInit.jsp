<%@include file="../i18n/language.jsp"%>
<%
  //程序名称：PDTestDeployInit.jsp
  //程序功能：产品测试与发布
  //创建日期：2009-3-18
  //创建人  ：CM
  //更新记录：  更新人    更新日期     更新原因/内容
%>
<script type="text/javascript">

function initForm()
{
	try{
	
		initMulline9Grid();
		
		initMulline10Grid();
		initMulline8Grid();
		//initMulline11Grid();
		queryMulline10Grid();
		
		query();
	}
	catch(re){
		myAlert("PDTestDeployInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
	}
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="发布数据类型";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="发布数据名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		
		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		//Mulline9Grid.selBoxEventFuncName ="showTestPlanList";
		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=200;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="发布平台代码";
		iArray[1][1]="50px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="发布平台名称";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="系统类别";
		iArray[3][1]="50px";
		iArray[3][2]=100;
		iArray[3][3]=3;
		iArray[3][4]="pd_systype";
		
		iArray[4]=new Array();
		iArray[4][0]="环境类别";
		iArray[4][1]="50px";
		iArray[4][2]=100;
		iArray[4][3]=3;
		iArray[4][4]="pd_envtype";
		

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		//Mulline10Grid.selBoxEventFuncName ="showDeployReason";//你写的JS函数名，不加扩号
		
		Mulline10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initMulline8Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="规则编码";
		iArray[1][1]="25px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种编码";
		iArray[2][1]="20px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="核保类型";
		iArray[3][1]="20px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="关联保单类型";
		iArray[4][1]="20px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="规则描述";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="业务模块";
		iArray[6][1]="20px";
		iArray[6][2]=100;
		iArray[6][3]=0;		
		
		iArray[7]=new Array();
		iArray[7][0]="适用系统";
		iArray[7][1]="20px";
		iArray[7][2]=100;
		iArray[7][3]=0;
						
		Mulline8Grid = new MulLineEnter( "fm" , "Mulline8Grid" ); 
		Mulline8Grid.mulLineCount=0;
		Mulline8Grid.displayTitle=1;
		Mulline8Grid.canSel=0;
		Mulline8Grid.canChk=1;
		Mulline8Grid.hiddenPlus=1;
		Mulline8Grid.hiddenSubtraction=1;
		//Mulline10Grid.selBoxEventFuncName ="showDeployReason";//你写的JS函数名，不加扩号
		
		Mulline8Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

</script>
