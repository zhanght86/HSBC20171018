<%
/***************************************************************
 * <p>ProName：LLClaimCustomerInit.jsp</p>
 * <p>Title：系统团体投保人查询</p>
 * <p>Description：系统团体投保人查询</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : 李肖峰
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面

 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();

		initGrpAppntListGrid();
		initGrpAppntDetailGrid();
		queryGrpAppnt();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件

 */
function initInpBox() {
	
	try {
		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}

/**
 * 把null的字符串转为空

 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}
// 下辖投保单位列表的初始化
function initGrpAppntDetailGrid(){
	
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
			iArray[i]=new Array();
      iArray[i][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）             
      iArray[i][1]="30px";         			//列宽                                                     
      iArray[i][2]=10;          			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                                                                                                                   
      iArray[i]=new Array();
      iArray[i][0]="投保人编码";
      iArray[i][1]="120px";
      iArray[i][2]=120;
      iArray[i++][3]=0;            
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="投保人名称";                                                
      iArray[i][1]="180px";                                             
      iArray[i][2]=180;
      iArray[i++][3]=0;           
                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="管理机构";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0; 
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="承保起期";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="承保止期";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;      
      
			GrpAppntDetailGrid = new MulLineEnter("fm", "GrpAppntDetailGrid");
			GrpAppntDetailGrid.mulLineCount = 0;
			GrpAppntDetailGrid.displayTitle = 1;
			GrpAppntDetailGrid.locked = 1;
			GrpAppntDetailGrid.canSel = 0;
			GrpAppntDetailGrid.canChk = 0;
			GrpAppntDetailGrid.hiddenSubtraction = 1;
			GrpAppntDetailGrid.hiddenPlus = 1;
			GrpAppntDetailGrid.loadMulLine(iArray);

			
		}catch(ex){}
	}
	
// 报案信息列表的初始化
function initGrpAppntListGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try {
		
			iArray[i]=new Array();
      iArray[i][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）             
      iArray[i][1]="30px";         			//列宽                                                     
      iArray[i][2]=10;          			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      

      iArray[i]=new Array();
      iArray[i][0]="AppntType";
      iArray[i][1]="60px";
      iArray[i][2]=100;
      iArray[i++][3]=3;         
                                                                                                                   
      iArray[i]=new Array();
      iArray[i][0]="投保人编码";
      iArray[i][1]="120px";
      iArray[i][2]=120;
      iArray[i++][3]=0;            
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="投保人名称";                                                
      iArray[i][1]="180px";                                             
      iArray[i][2]=180;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();
      iArray[i][0]="投保人性质";
      iArray[i][1]="120px";
      iArray[i][2]=120;
      iArray[i++][3]=0;      
                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="管理机构";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="承保起期";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="承保止期";                                                
      iArray[i][1]="100px";                                             
      iArray[i][2]=100;
      iArray[i++][3]=0;      
      
			GrpAppntListGrid = new MulLineEnter("fm", "GrpAppntListGrid");
			GrpAppntListGrid.mulLineCount = 0;
			GrpAppntListGrid.displayTitle = 1;
			GrpAppntListGrid.locked = 1;
			GrpAppntListGrid.canSel = 1;
			GrpAppntListGrid.canChk = 0;
			GrpAppntListGrid.hiddenSubtraction = 1;
			GrpAppntListGrid.hiddenPlus = 1;
			GrpAppntListGrid.selBoxEventFuncName = "showDetail";
			GrpAppntListGrid.loadMulLine(iArray);

			
		}catch(ex){}
	}	
	
</script>
