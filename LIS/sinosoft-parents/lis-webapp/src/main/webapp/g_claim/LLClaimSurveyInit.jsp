<%
/***************************************************************
 * <p>ProName：LLClaimSurveyAppinput.jsp</p>
 * <p>Title：调查录入</p>
 * <p>Description：调查录入</p>
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
		
		initSurveyAttachmentGrid();
		initLLSurveyFeeGrid();
		initLLSurveyCourseGrid();
		QueryInqAppInfo();
		InitQuerySurveyCourse();
		querySurveyAttachment();
		initInqFeeInfo();
		initSurveyFeeQuery();
		queryUwConclusionInfo();
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		 mCurrentDate = nullToEmpty("<%= tCurrentDate %>"); 
		 
		 fm.ClmNo.value=tRgtNo;
		 fm.InqNo.value=tInqNo;
		 fm2.ClmNo.value=tRgtNo;
		 fm2.InqNo.value=tInqNo;
		 fm3.ClmNo.value=tRgtNo;
		 fm3.InqNo.value=tInqNo;
		 
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件

 */
function initInpBox() {
	
	try {
		
		tState = nullToEmpty("<%=tState%>"); 
		if(tState=="1"||tState=="2") {
			divSurveyReviewEntry.style.display="";
		}
		if(tState=="1"||tState=="2") {
			Save1.style.display="none";
			divFJ.style.display="none";
			Save2.style.display="none";
			Save3.style.display="none";
			showYC();
			fm.UpdateCourseButton.disabled=true;
			fm.DeleteCourseButton.disabled=true;
			fm5.AssFeeButton.disabled=true;
			fm5.UpdateFeeButton.disabled=true;
			fm5.DeleteFeeButton.disabled=true;
			fm2.saveAttache.disabled=true;
			fm2.updateAttache.disabled=true;
			fm2.deleteAttache.disabled=true;
			fm2.uploadfile.disabled=true;
			fm2.repalecefile.disabled=true;
			fm2.deletefile.disabled=true;			
			fm3.InqConfirm.disabled=true;
			fm3.ReturnSurveyProcess.disabled=true;	
		}
		if(tState=="2") {

			divSurveyShowReturnView.style.display="none";
		//	divSurveyViewInfo.style.display = "";
			document.getElementById("luruper").style.display="";
			document.getElementById("luruper1").style.display="";	
			document.getElementById("luruper2").style.display="";	
			document.getElementById("luruper3").style.display="";
			document.getElementById("per").style.display="";
			document.getElementById("per1").style.display="";	
			document.getElementById("per2").style.display="";	
			document.getElementById("per3").style.display="";
			document.getElementById("Save4").style.display="";

		}
		if(tState=="1"){
			document.getElementById("luruper").style.display="";
			document.getElementById("luruper1").style.display="";	
			document.getElementById("luruper2").style.display="";	
			document.getElementById("luruper3").style.display="";	
		}
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
// 调查调查过程列表的初始化
function initLLSurveyCourseGrid()
{                             
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i]=new Array();
        iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[i][1]="30px";                //列宽
        iArray[i][2]=10;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[i]=new Array();
        iArray[i][0]="过程序号";             //列名
        iArray[i][1]="100px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[i]=new Array();
        iArray[i][0]="调查方式";             //列名
        iArray[i][1]="60px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0; 
        
				iArray[i]=new Array();
        iArray[i][0]="调查地点";             //列名
        iArray[i][1]="100px";                //列宽
        iArray[i][2]=200;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[i]=new Array();
        iArray[i][0]="调查日期";             //列名
        iArray[i][1]="80px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

       	iArray[i]=new Array();
        iArray[i][0]="第一调查人";             //列名
        iArray[i][1]="80px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

       	iArray[i]=new Array();
        iArray[i][0]="第二调查人";             //列名
        iArray[i][1]="80px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[i]=new Array();
        iArray[i][0]="被调查人";             //列名
        iArray[i][1]="100px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[i]=new Array();
        iArray[i][0]="调查方式编码";             //列名
        iArray[i][1]="0px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=3;
        
        iArray[i]=new Array();
        iArray[i][0]="被调查人与出险人关系";             //列名
        iArray[i][1]="0px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=3;
        
        iArray[i]=new Array();
        iArray[i][0]="被调查人与出险人关系名称";             //列名
        iArray[i][1]="0px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i][3]=3;                
                             
        LLSurveyCourseGrid = new MulLineEnter( "fm" , "LLSurveyCourseGrid" ); 
        //这些属性必须在loadMulLine前
        LLSurveyCourseGrid.mulLineCount = 0;   
        LLSurveyCourseGrid.displayTitle = 1;
        LLSurveyCourseGrid.locked = 1;
        LLSurveyCourseGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLSurveyCourseGrid.selBoxEventFuncName = "showLLSurveyCourseGridClick";//点击RadioBox时响应的函数名
        LLSurveyCourseGrid.hiddenPlus=1;
        LLSurveyCourseGrid.hiddenSubtraction=1;
        LLSurveyCourseGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        alert(ex);
    }
}

// 调查结论表的初始化
function initLLSurveyFeeGrid()
{                           
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i]=new Array();
        iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[i][1]="30px";                //列宽
        iArray[i][2]=10;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[i]=new Array();
        iArray[i][0]="费用类型";             //列名
        iArray[i][1]="80px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        iArray[i]=new Array();
        iArray[i][0]="FeeType";             //费用类型编码
        iArray[i][1]="0px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=3;       
        
        iArray[i]=new Array();
        iArray[i][0]="费用金额";             //列名
        iArray[i][1]="80px";                //列宽
        iArray[i][2]=200;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[i]=new Array();
        iArray[i][0]="发生时间";             //列名
        iArray[i][1]="80px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0; 

        iArray[i]=new Array();
        iArray[i][0]="领款人";             //列名
        iArray[i][1]="100px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许

        iArray[i]=new Array();
        iArray[i][0]="领款方式";             //列名
        iArray[i][1]="60px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=0;                    //是否允许输入,1表示允许，0表示不允许
        
        
        iArray[i]=new Array();
        iArray[i][0]="PayeeType";             //领款方式编码
        iArray[i][1]="0px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i++][3]=3;   

        iArray[i]=new Array();
        iArray[i][0]="备注";             //列名
        iArray[i][1]="0px";                //列宽
        iArray[i][2]=100;                  //列最大值
        iArray[i][3]=3;                    //是否允许输入,1表示允许，0表示不允许     
                 
        LLSurveyFeeGrid = new MulLineEnter( "fm5" , "LLSurveyFeeGrid" ); 
        //这些属性必须在loadMulLine前
        LLSurveyFeeGrid.mulLineCount = 0;   
        LLSurveyFeeGrid.displayTitle = 1;
        LLSurveyFeeGrid.locked = 1;
        LLSurveyFeeGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        LLSurveyFeeGrid.selBoxEventFuncName = "showLLSurveyFeeClick";//点击RadioBox时响应的函数名
        LLSurveyFeeGrid.hiddenPlus=1;
        LLSurveyFeeGrid.hiddenSubtraction=1;
        LLSurveyFeeGrid.loadMulLine(iArray); 
     
    }
    catch(ex)
    {
        alert(ex);
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