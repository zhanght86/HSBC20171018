<%
//-------------------------------------------------
//程序名称：LDUnifyCodeTypeInit.jsp
//程序功能：系统统一编码维护
//创建日期：2012-01-01
//创建人  ：刘锦祥
//修改人  ：
//更新记录：  更新人    更新日期     更新原因/内容
//-------------------------------------------------
%>
<script language="JavaScript">
	
/**
 * 初始化界面
 */
function initForm() {
  try {
  	//数据类型
  	initCodeTypeInfo();
  	initParam();
    initInpBox();
		initCodeTypeGrid();
    
    //代码
    initCodeInfo();
    initCodeGrid();
  }catch(re) {
    alert("LDUnifyCodeTypeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

/**
 * 初始化界面传过来的参数
 */
function initParam() {
	try {
  }catch(re) {
    alert("LDUnifyCodeTypeInit.jsp-->initParam函数中发生异常:初始化参数错误!");
  }
}

/**
 * 初始化值	
 */
function initInpBox() 
{ 
  try { 
	
  }catch(ex) {
    alert("LDUnifyCodeTypeInit.jsp-->InitInpBox函数中发生异常:初始化值错误!");
  }      
}                                     

/**
 * 把null的字符串转为空
 */
function nullToEmpty(string)
{
	if((string == "null") || (string == "undefined")) {
		string = "";
	}
	return string;
}

/**
 * 按钮的初始化
 */
function initButton() {
	try { 
	
	}catch(ex) {
		alert("LDUnifyCodeTypeInit.jsp-->InitInpBox函数中发生异常:初始化按钮错误!");
	}  
}

/**
 * 初始化数据类型输入框
 */
function initCodeTypeInfo() {
	
	fm.SysCode.value="";
	fm.SysCodeName.value="";
	fm.CodeType.value="";
	fm.CodeTypeName.value="";
	fm.MaintainFlag.value="";
	fm.MaintainFlagName.value="";
	fm.State.value="";
	fm.StateName.value="";
	fm.CodeTypeDescription.value="";
	divSysCodeReadOnly.style.display = "none";
	divSysCode.style.display="";
	fm.CodeType.readOnly = false;
}

/**
 * 初始化编码输入框
 */
function initCodeInfo() {
	
	fm.SysCode1.value="";
	fm.SysCode1Name.value="";
	fm.CodeType1.value="";
	fm.CodeType1Name.value="";
	fm.State1.value="";
	fm.State1Name.value="";
	fm.Code.value="";
	fm.CodeName.value="";
	fm.CodeAlias.value="";	
	divSysCodeReadOnly1.style.display = "none";
	divCodeTypeReadOnly1.style.display = "none";
	divSysCode1.style.display = "";
	divCodeType1.style.display = "";
	fm.Code.readOnly = false;
}

/**
 * 数据类型的初始化
 */
function initCodeTypeGrid() {
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i=0;
	try {
		iArray[i]=new Array();
		iArray[i][0]="序号";         //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[i][1]="30px";         //列宽
		iArray[i][2]=30;            //列最大值
		iArray[i++][3]=0;              //是否允许输入,1表示允许，0表示不允许

		iArray[i]=new Array();
		iArray[i][0]="系统名称";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="编码类型";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;        
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="编码类型名称";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;        
		iArray[i++][3]=0;        

		iArray[i]=new Array();
		iArray[i][0]="维护标识";         
		iArray[i][1]="100px";        
		iArray[i][2]=100;        
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="状态";        
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="编码类型描述";         
		iArray[i][1]="200px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;      
		
		iArray[i]=new Array();
		iArray[i][0]="操作人";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="操作日期";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;     
	       	
		iArray[i]=new Array();
		iArray[i][0]="系统代码";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();
		iArray[i][0]="维护代码";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;  
		
		iArray[i]=new Array();
		iArray[i][0]="状态代码";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;  
		
		CodeTypeInfoGrid = new MulLineEnter( "fm" , "CodeTypeInfoGrid" );

		//这些属性必须在loadMulLine前
		CodeTypeInfoGrid.mulLineCount = 0;
		CodeTypeInfoGrid.displayTitle = 1;
		CodeTypeInfoGrid.canSel=1;
		CodeTypeInfoGrid.hiddenPlus = 1;
		CodeTypeInfoGrid.hiddenSubtraction = 1;
		CodeTypeInfoGrid.selBoxEventFuncName = "showCodeTypeInfo";//点击RadioBox时响应的函数名
		CodeTypeInfoGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert("初始化CodeTypeGrid时出错："+ ex);
	}
}

/**
 * 数据的初始化
 */
function initCodeGrid() {
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i=0;
	try {
		iArray[i]=new Array();
		iArray[i][0]="序号";         //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[i][1]="30px";         //列宽
		iArray[i][2]=100;            //列最大值
		iArray[i++][3]=0;              //是否允许输入,1表示允许，0表示不允许

		iArray[i]=new Array();
		iArray[i][0]="系统名称";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="编码类型名称";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;        
		iArray[i++][3]=0;                

		iArray[i]=new Array();
		iArray[i][0]="编码";        
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;         

		iArray[i]=new Array();
		iArray[i][0]="编码名称";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;      
		
		iArray[i]=new Array();
		iArray[i][0]="编码别名";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="状态";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="操作人";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;  
		
		iArray[i]=new Array();
		iArray[i][0]="操作日期";         
		iArray[i][1]="100px";         
		iArray[i][2]=100;         
		iArray[i++][3]=0;     
		
		iArray[i]=new Array();
		iArray[i][0]="系统代码";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;      
		
		iArray[i]=new Array();
		iArray[i][0]="类型代码";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;  
		
		iArray[i]=new Array();
		iArray[i][0]="状态代码";         
		iArray[i][1]="0px";         
		iArray[i][2]=100;         
		iArray[i++][3]=3;   
	       	
		CodeInfoGrid = new MulLineEnter( "fm" , "CodeInfoGrid" );

		//这些属性必须在loadMulLine前
		CodeInfoGrid.mulLineCount = 0;
		CodeInfoGrid.displayTitle = 1;
		CodeInfoGrid.canSel=1;
		CodeInfoGrid.hiddenPlus = 1;
		CodeInfoGrid.hiddenSubtraction = 1;
		CodeInfoGrid.selBoxEventFuncName = "showCodeInfo";//点击RadioBox时响应的函数名
		CodeInfoGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert("初始化CodeGrid时出错："+ ex);
	}
}
</script>
