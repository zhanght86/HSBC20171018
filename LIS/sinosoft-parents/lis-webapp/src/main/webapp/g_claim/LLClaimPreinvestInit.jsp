<%
/***************************************************************
 * <p>ProName：LLClaimPreinvestInit.jsp</p>
 * <p>Title：发起调查</p>
 * <p>Description：发起调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
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
		
		initSurveyAttachmentGrid();
		initInvestListGrid();
		initOnInvestGrid();
		initValue();
			
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
		fm.saveButton.disabled=false;
		fm.recoverButton.disabled=true;
		fm.deleteButton.disabled=true;
		fm.doCloseButton.disabled=true;
		fm.doButton.disabled=true;
		
		if(tMode=="0"){
			fm.inqTuenBack.style.display="";
			fm.saveButton.style.display="";
			fm.recoverButton.style.display="";
			fm.deleteButton.style.display="";
			fm.doCloseButton.style.display="";
			fm.doButton.style.display="";
			fm.goBackButton.style.display="";
		}else if(tMode="1"){
			fm.inqTuenBack.style.display="none";
			fm.saveButton.style.display="none";
			fm.recoverButton.style.display="none";
			fm.deleteButton.style.display="none";
			fm.doCloseButton.style.display="none";
			fm.doButton.style.display="none";
			fm.goBackButton.style.display="none";
		}
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
// 已发起调查信息列表
function initInvestListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="调查流水号";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
 
 		iArray[i]=new Array();
    iArray[i][0]="报案号/案件号";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="调查批次"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="出险人姓名"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="调查发起日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
	
		iArray[i]=new Array();
    iArray[i][0]="调查类型编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3

    iArray[i]=new Array();
    iArray[i][0]="调查类型"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="调查原因编码"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="调查原因"; 
    iArray[i][1]="120px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="调查发起机构"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
		
		iArray[i]=new Array();
    iArray[i][0]="是否异地调查"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
		
		iArray[i]=new Array();
    iArray[i][0]="调查发起方式编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="调查发起方式"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
		
		iArray[i]=new Array();
    iArray[i][0]="调查状态编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
		
    iArray[i]=new Array();
    iArray[i][0]="调查状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="调查关闭原因编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="调查关闭原因"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="调查目的"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="调查计划"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="备注信息"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    InvestListGrid = new MulLineEnter( "fm" , "InvestListGrid" ); 
    //这些属性必须在loadMulLine前
    InvestListGrid.mulLineCount = 0;   
    InvestListGrid.displayTitle = 1;
    InvestListGrid.locked = 0;
    InvestListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    InvestListGrid.hiddenPlus=1;
    InvestListGrid.selBoxEventFuncName ="getInvestListInfo"; //响应的函数名，不加扩号
    InvestListGrid.hiddenSubtraction=1;
    InvestListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}

// 报案信息列表的初始化个人
function initOnInvestGrid() {
	       
    var iArray = new Array();
    var i=0;
    try{
    
   iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="调查流水号";             
    iArray[i][1]="60px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
 
 		iArray[i]=new Array();
    iArray[i][0]="报案号/案件号";             
    iArray[i][1]="140px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=0;      


    iArray[i]=new Array();
    iArray[i][0]="调查批次"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="出险人姓名"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="调查发起日期"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
	
		iArray[i]=new Array();
    iArray[i][0]="调查类型编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3

    iArray[i]=new Array();
    iArray[i][0]="调查类型"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="调查原因编码"; 
    iArray[i][1]="60px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="调查原因"; 
    iArray[i][1]="120px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="调查发起机构"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

		iArray[i]=new Array();
    iArray[i][0]="调查发起方式编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="调查发起方式"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;

		iArray[i]=new Array();
    iArray[i][0]="调查状态编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
		
    iArray[i]=new Array();
    iArray[i][0]="调查状态"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="调查关闭原因编码"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="调查关闭原因"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="调查目的"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="调查计划"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
		iArray[i]=new Array();
    iArray[i][0]="备注信息"; 
    iArray[i][1]="80px";
    iArray[i][2]=80; 
    iArray[i++][3]=3;
    
    OnInvestGrid = new MulLineEnter( "fm" , "OnInvestGrid" ); 
    //这些属性必须在loadMulLine前
    OnInvestGrid.mulLineCount = 0;   
    OnInvestGrid.displayTitle = 1;
    OnInvestGrid.locked = 0;
    OnInvestGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    OnInvestGrid.selBoxEventFuncName ="getOnInvestInfo"; //响应的函数名，不加扩号
    OnInvestGrid.hiddenPlus=1;
    OnInvestGrid.hiddenSubtraction=1;
    OnInvestGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("初始化界面信息出错->OnInvestGrid");
    }
}
//初始化附件mulline
function initSurveyAttachmentGrid()	{
	var iArray = new Array();  
	var i=0;    
	try
	{
      iArray[i]=new Array();
      iArray[i][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[i][1]="30px";            		//列宽
      iArray[i][2]=10;            			//列最大值
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[i]=new Array();
      iArray[i][0]="调查序号";         		//列名
      iArray[i][1]="0px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[i]=new Array();
      iArray[i][0]="附件序号";         		//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[i]=new Array();
      iArray[i][0]="附件名称";         		//列名
      iArray[i][1]="150px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=0;  
      
      iArray[i]=new Array();
      iArray[i][0]="文件名称";         		//列名
      iArray[i][1]="120px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[i]=new Array();
      iArray[i][0]="原件标识";         		//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=0;    
      
      iArray[i]=new Array();
      iArray[i][0]="上传张数";         		//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=0;    
      
	    iArray[i]=new Array();
      iArray[i][0]="上传日期";         		//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[i]=new Array();
      iArray[i][0]="修改日期";         		//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i++][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
			iArray[i]=new Array();
      iArray[i][0]="filepath";         		//列名
      iArray[i][1]="0px";            		//列宽
      iArray[i][2]=200;            			//列最大值
      iArray[i++][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
			iArray[i]=new Array();
      iArray[i][0]="originallogo";         		//列名
      iArray[i][1]="0px";            		//列宽
      iArray[i][2]=200;            			//列最大值
      iArray[i][3]=3; 

      SurveyAttachmentGrid = new MulLineEnter("fm2","SurveyAttachmentGrid"); 
      //这些属性必须在loadMulLine前
      SurveyAttachmentGrid.mulLineCount = 0;   
      SurveyAttachmentGrid.displayTitle = 1;
      SurveyAttachmentGrid.locked = 1;
      SurveyAttachmentGrid.canSel = 1;
      SurveyAttachmentGrid.canChk = 0;
      SurveyAttachmentGrid.hiddenPlus = 1;
      SurveyAttachmentGrid.hiddenSubtraction = 1; 
      SurveyAttachmentGrid.selBoxEventFuncName = "showSurveyAttachment";     
      SurveyAttachmentGrid.loadMulLine(iArray); 
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
