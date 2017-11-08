<%
/***************************************************************
 * <p>ProName：LLDrugFeeMaintainInit.jsp</p>
 * <p>Title：药品信息维护</p>
 * <p>Description：药品信息维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
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
		//restartPermission();
		initDrugFeeMaintainGrid();
		//queryPermissionInfo();
		
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
function initDrugFeeMaintainGrid(){
	
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
		iArray[i][0]="药品流水号";    			//列名                                                     
		iArray[i][1]="40px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许                      
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="地区编码";    			//列名                                                     
		iArray[i][1]="40px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;     
			
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="地区";    			//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
			                                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="更新日期";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
		                                                                              
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="药品名称";         		//列名                                                     
		iArray[i][1]="100px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;             			//是否允许输入,1表示允许，0表示不允许                      
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="商品名称";         		//列名                                                     
		iArray[i][1]="100px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="剂型";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		                                                                                                             
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="规格";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		 
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="自付比例";         		//列名                                                     
		iArray[i][1]="80px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="医保类型";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="价格";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用   
		
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="限制内容";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用  
      
		iArray[i]=new Array();                                                                                       
		iArray[i][0]="医保类型编码";         			//列名                                                     
		iArray[i][1]="60px";            			//列宽                                                     
		iArray[i][2]=100;            			//列最大值                                                 
		iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用  
      
		DrugFeeMaintainGrid = new MulLineEnter("fm", "DrugFeeMaintainGrid");
		DrugFeeMaintainGrid.mulLineCount = 0;
		DrugFeeMaintainGrid.displayTitle = 1;
		DrugFeeMaintainGrid.locked = 1;
		DrugFeeMaintainGrid.canSel = 1;
		DrugFeeMaintainGrid.canChk = 0;
		DrugFeeMaintainGrid.hiddenSubtraction = 1;
		DrugFeeMaintainGrid.hiddenPlus = 1;
		DrugFeeMaintainGrid.selBoxEventFuncName = "showPermissionInfo";
		DrugFeeMaintainGrid.loadMulLine(iArray);

		}catch(ex){
			alert("初始化信息错误");
			return false;
		}
	}
	
</script>
