<%
/***************************************************************
 * <p>ProName：LLClaimSurveyAppinput.jsp</p>
 * <p>Title：调查录入申请</p>
 * <p>Description：调查录入申请</p>
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
		
		initDefoGrid();
		
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
		
		fm.DModify.disabled=true;
		fm.DDelete.disabled=true;
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
function initDefoGrid(){
	
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
      iArray[i][0]="伤残类型编码";    			//列名                                                     
      iArray[i][1]="40px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许                      
         
			iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残类型";    			//列名                                                     
      iArray[i][1]="80px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残分类";         		//列名                                                     
      iArray[i][1]="40px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                          
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残分类名称";         		//列名                                                     
      iArray[i][1]="80px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;             			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残级别";         		//列名                                                     
      iArray[i][1]="40px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                          
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残级别名称";         		//列名                                                     
      iArray[i][1]="50px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;             			//是否允许输入,1表示允许，0表示不允许                      
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残代码";         		//列名                                                     
      iArray[i][1]="120px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残代码名称";         		//列名                                                     
      iArray[i][1]="160px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用        
                                                                                                                   
      iArray[i]=new Array();                                                                                       
      iArray[i][0]="伤残给付比例";         			//列名                                                     
      iArray[i][1]="50px";            			//列宽                                                     
      iArray[i][2]=100;            			//列最大值                                                 
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用    
      
     
      
			DefoGrid = new MulLineEnter("fm", "DefoGrid");
			DefoGrid.mulLineCount = 0;
			DefoGrid.displayTitle = 1;
			DefoGrid.locked = 1;
			DefoGrid.canSel = 1;
			DefoGrid.canChk = 0;
			DefoGrid.hiddenSubtraction = 1;
			DefoGrid.hiddenPlus = 1;
			DefoGrid.selBoxEventFuncName = "showDefoInfo";
			DefoGrid.loadMulLine(iArray);

			
		}catch(ex){
			
			}
	}
</script>
