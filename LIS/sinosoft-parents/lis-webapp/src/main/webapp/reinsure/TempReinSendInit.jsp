<%@include file="/i18n/language.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>                
<%
//程序名称：TempReinSendInit.jsp
//程序功能：临分审核发送
//创建日期：2007-02-22 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
  String tPolNo = ""; 
  String tOperate 			= request.getParameter("OperateNo");
  String tCodeType 			= request.getParameter("CodeType");
  String tAuditCode 		= request.getParameter("AuditCode");
  String tAuditAffixCode= request.getParameter("AuditAffixCode");
%>                            

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">

function initInpBox()
{ 
	try                 
  {
  	fm.Remark.value						= "在此录入申报意见"; 
  	fmImport.DeTailFlag.value = "1"; //1-到险种 2-到责任 
  	fmImport.OpeData.value		= "<%=tOperate%>"; 
  	fmImport.OpeFlag.value		= "<%=tCodeType%>"; 
  	fm.GrpContNo.value 				= "<%=tOperate%>"; 
  	fm.Remark.value						= "在此录入申报意见"; 
  	//fmImport.AutoAnswer.value='1'; //0:系统自动回复 1:正常流程
  }
  catch(ex)
  {
    myAlert("在ReInsureInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

//下拉列表的初始化
function initSelBox()
{
}                                        

function initForm(tContNo, tPolNo)
{
  try
  {
		initInpBox();
		initGrpUWResultGrid();
		initSearchResultGrid();
		initIndUWResultGrid();
		initReInsureGrid();
		
		initRiskInfoGrid();
		initClaimInfoGrid();
		initReInsureAuditGrid();
		
  }
  catch(re)
  {
    myAlert("ReInsureInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
  }
}

//责任指令清单的初始化
function initReInsureGrid()
{                               
    var iArray = new Array();
    
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	    iArray[0][1]="30px";         			//列宽
	    iArray[0][2]=10;          				//列最大值
	    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	    iArray[1]=new Array(); 
	    iArray[1][0]="被保人客户号"; 			//列名
	    iArray[1][1]="30px";            	//列宽
	    iArray[1][2]=100;            			//列最大值
	    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
				
	    iArray[2]=new Array(); 
	    iArray[2][0]="被保人姓名";         	//列名
	    iArray[2][1]="30px";            	//列宽
	    iArray[2][2]=100;            			//列最大值
	    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[3]=new Array(); 
	    iArray[3][0]="险种编码";         	//列名
	    iArray[3][1]="30px";            	//列宽
	    iArray[3][2]=100;            			//列最大值
	    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	    
			iArray[4]=new Array();
	    iArray[4][0]="险种名称";     			//列名
	    iArray[4][1]="50px";            	//列宽
	    iArray[4][2]=100;            			//列最大值
	    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[5]=new Array();
	    iArray[5][0]="责任代码";     			//列名
	    iArray[5][1]="0px";            	//列宽
	    iArray[5][2]=100;            			//列最大值
	    iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[6]=new Array();
	    iArray[6][0]="自核结果";     			//列名
	    iArray[6][1]="80px";            	//列宽
	    iArray[6][2]=200;            			//列最大值
	    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	    ReInsureGrid = new MulLineEnter( "fm" , "ReInsureGrid" ); 
	    //这些属性必须在loadMulLine前
	    ReInsureGrid.mulLineCount = 0;   
	    ReInsureGrid.displayTitle = 1;
	    ReInsureGrid.locked = 1;
	    ReInsureGrid.hiddenPlus = 1;
	    ReInsureGrid.hiddenSubtraction = 1;
	    ReInsureGrid.loadMulLine(iArray);  
  	}  
    catch(ex){
      myAlert(ex);
    }
}       

////团单核保结果
function initGrpUWResultGrid(){
	var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
	    iArray[0][1]="30px";         		//列宽
	    iArray[0][2]=10;          			//列最大值
	    iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[1]=new Array();
	    iArray[1][0]="印刷号码";    		//列名
	    iArray[1][1]="80px";            //列宽
	    iArray[1][2]=100;            		//列最大值
	    iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[2]=new Array();
	    iArray[2][0]="集体投保单号码";  //列名
	    iArray[2][1]="80px";            //列宽
	    iArray[2][2]=100;            		//列最大值
	    iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[3]=new Array();
	    iArray[3][0]="保障计划";        //列名
	    iArray[3][1]="60px";            //列宽
	    iArray[3][2]=100;            		//列最大值
	    iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[4]=new Array();
	    iArray[4][0]="再保合同";        //列名
	    iArray[4][1]="60px";            //列宽
	    iArray[4][2]=100;            		//列最大值
	    iArray[4][3]=3;              		//是否允许输入,1表示允许，0表示不允许
			
	    iArray[5]=new Array();
	    iArray[5][0]="再保方案";        //列名
	    iArray[5][1]="60px";            //列宽
	    iArray[5][2]=100;            		//列最大值
	    iArray[5][3]=3;              		//是否允许输入,1表示允许，0表示不允许
			
	    iArray[6]=new Array();
	    iArray[6][0]="临分自核规则";    //列名
	    iArray[6][1]="150px";            //列宽
	    iArray[6][2]=100;            		//列最大值
	    iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[7]=new Array();
	    iArray[7][0]=" 自核结果";   		//列名
	    iArray[7][1]="200px";           //列宽
	    iArray[7][2]=100;            		//列最大值
	    iArray[7][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	    GrpUWResultGrid = new MulLineEnter( "fm" , "GrpUWResultGrid" ); 
	    //这些属性必须在loadMulLine前
	    GrpUWResultGrid.mulLineCount = 0;   
	    GrpUWResultGrid.displayTitle = 1;
	    GrpUWResultGrid.locked = 1;
	    GrpUWResultGrid.hiddenPlus = 1;
	    GrpUWResultGrid.canSel = 0;
	    GrpUWResultGrid.hiddenSubtraction = 1;
	    GrpUWResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}
//个人核保结果
function initIndUWResultGrid(){
		var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="序号";
	    iArray[0][1]="30px";
	    iArray[0][2]=10;
	    iArray[0][3]=0;
	
	    iArray[1]=new Array();
	    iArray[1][0]="印刷号码";
	    iArray[1][1]="100px";           //列宽
	    iArray[1][2]=100;            		//列最大值
	    iArray[1][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[2]=new Array();
	    iArray[2][0]="集体投保单号码";  //列名
	    iArray[2][1]="100px";           //列宽
	    iArray[2][2]=100;            		//列最大值
	    iArray[2][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[3]=new Array();
	    iArray[3][0]="保障计划";        //列名
	    iArray[3][1]="60px";            //列宽
	    iArray[3][2]=100;            		//列最大值
	    iArray[3][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[4]=new Array();
	    iArray[4][0]="个人合同号码";    //列名
	    iArray[4][1]="100px";           //列宽
	    iArray[4][2]=100;            		//列最大值
	    iArray[4][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	
	    iArray[5]=new Array();
	    iArray[5][0]="投保单险种号码";  //列名
	    iArray[5][1]="100px";           //列宽
	    iArray[5][2]=100;            		//列最大值
	    iArray[5][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[6]=new Array();
	    iArray[6][0]="被保人姓名";      //列名
	    iArray[6][1]="80px";            //列宽
	    iArray[6][2]=100;            		//列最大值
	    iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[7]=new Array();
	    iArray[7][0]="被保人号码";    	//列名
	    iArray[7][1]="100px";           //列宽
	    iArray[7][2]=100;            		//列最大值
	    iArray[7][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[8]=new Array();
	    iArray[8][0]="险种代码";        //列名
	    iArray[8][1]="80px";            //列宽
	    iArray[8][2]=100;            		//列最大值
	    iArray[8][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[9]=new Array();
	    iArray[9][0]="责任代码";        //列名
	    iArray[9][1]="80px";            //列宽
	    iArray[9][2]=100;            		//列最大值
	    iArray[9][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[10]=new Array();
	    iArray[10][0]="再保合同";      	//列名
	    iArray[10][1]="100px";          //列宽
	    iArray[10][2]=100;            	//列最大值
	    iArray[10][3]=3;              	//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[11]=new Array();
	    iArray[11][0]="再保方案";    		//列名
	    iArray[11][1]="100px";          //列宽
	    iArray[11][2]=100;            	//列最大值
	    iArray[11][3]=3;              	//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[12]=new Array();
	    iArray[12][0]="临分自核规则";   //列名
	    iArray[12][1]="150px";          //列宽
	    iArray[12][2]=100;            	//列最大值
	    iArray[12][3]=0;              	//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[13]=new Array();
	    iArray[13][0]="自核结果";    		//列名
	    iArray[13][1]="200px";          //列宽
	    iArray[13][2]=200;            	//列最大值
	    iArray[13][3]=0;              	//是否允许输入,1表示允许，0表示不允许
	
	    IndUWResultGrid = new MulLineEnter( "fm" , "IndUWResultGrid" ); 
	    //这些属性必须在loadMulLine前
	    IndUWResultGrid.mulLineCount = 0;   
	    IndUWResultGrid.displayTitle = 1;
	    IndUWResultGrid.locked = 1;
	    IndUWResultGrid.hiddenPlus = 1;
	    IndUWResultGrid.canSel = 0;
	    IndUWResultGrid.hiddenSubtraction = 1;
	    IndUWResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

function initSearchResultGrid(){
		var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="序号";
	    iArray[0][1]="30px";
	    iArray[0][2]=10;
	    iArray[0][3]=0;
	
	    iArray[1]=new Array();
	    iArray[1][0]="印刷号码";
	    iArray[1][1]="100px";
	    iArray[1][2]=100;
	    iArray[1][3]=0;
			
	    iArray[2]=new Array();
	    iArray[2][0]="集体投保单号码";
	    iArray[2][1]="100px";
	    iArray[2][2]=100;
	    iArray[2][3]=0;
	    
	    iArray[3]=new Array();
	    iArray[3][0]="保障计划";
	    iArray[3][1]="60px";
	    iArray[3][2]=100;
	    iArray[3][3]=0;
	    
	    iArray[4]=new Array();
	    iArray[4][0]="个人合同号码";
	    iArray[4][1]="100px";
	    iArray[4][2]=100;
	    iArray[4][3]=0;
			
	    iArray[5]=new Array();
	    iArray[5][0]="投保单险种号码";
	    iArray[5][1]="100px";
	    iArray[5][2]=100;
	    iArray[5][3]=0;
			
	    iArray[6]=new Array();
	    iArray[6][0]="被保人姓名";
	    iArray[6][1]="60px";
	    iArray[6][2]=100;
	    iArray[6][3]=0;
	    
	    iArray[7]=new Array();
	    iArray[7][0]="被保人号码";
	    iArray[7][1]="100px";
	    iArray[7][2]=100;
	    iArray[7][3]=0;
	    
	    iArray[8]=new Array();
	    iArray[8][0]="险种代码";
	    iArray[8][1]="100px";
	    iArray[8][2]=100;
	    iArray[8][3]=0;
	    
	    iArray[9]=new Array();
	    iArray[9][0]="责任代码";
	    iArray[9][1]="100px";
	    iArray[9][2]=100;
	    iArray[9][3]=0;
	    
	    iArray[10]=new Array();
	    iArray[10][0]="临分核保结论";
	    iArray[10][1]="100px";
	    iArray[10][2]=100;
	    iArray[10][3]=0;
			
			iArray[11]=new Array();
	    iArray[11][0]="临分核保结论代码";
	    iArray[11][1]="100px";
	    iArray[11][2]=100;
	    iArray[11][3]=3;
			
	    SearchResultGrid = new MulLineEnter( "fm" , "SearchResultGrid" ); 
	    //这些属性必须在loadMulLine前
	    SearchResultGrid.mulLineCount = 0;   
	    SearchResultGrid.displayTitle = 1;
	    SearchResultGrid.locked = 1;
	    SearchResultGrid.hiddenPlus = 1;
	    SearchResultGrid.canSel = 0;
    	SearchResultGrid.canChk = 1; 
	    SearchResultGrid.hiddenSubtraction = 1;
	    SearchResultGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

//// 责任指令清单的初始化
function initRiskInfoGrid(){
    var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
	    iArray[0][1]="30px";         		//列宽
	    iArray[0][2]=10;          			//列最大值
	    iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[1]=new Array();
	    iArray[1][0]="被保险人";    		//列名
	    iArray[1][1]="80px";            //列宽
	    iArray[1][2]=100;            		//列最大值
	    iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[2]=new Array();
	    iArray[2][0]="险种代码";        //列名
	    iArray[2][1]="80px";            //列宽
	    iArray[2][2]=100;            		//列最大值
	    iArray[2][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[3]=new Array();
	    iArray[3][0]="责任编码";        //列名
	    iArray[3][1]="0px";            	//列宽
	    iArray[3][2]=100;            		//列最大值
	    iArray[3][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[4]=new Array();
	    iArray[4][0]="档次";         		//列名
	    iArray[4][1]="0px";            	//列宽
	    iArray[4][2]=100;            		//列最大值
	    iArray[4][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[5]=new Array();
	    iArray[5][0]="保費";         		//列名
	    iArray[5][1]="60px";            //列宽
	    iArray[5][2]=100;            		//列最大值
	    iArray[5][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[6]=new Array();
	    iArray[6][0]="保額";         		//列名
	    iArray[6][1]="60px";            //列宽
	    iArray[6][2]=100;            		//列最大值
	    iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[7]=new Array();
	    iArray[7][0]="风险保额";        //列名
	    iArray[7][1]="60px";            //列宽
	    iArray[7][2]=100;            		//列最大值
	    iArray[7][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[8]=new Array();
	    iArray[8][0]="累计风险保额";    //列名
	    iArray[8][1]="0px";           	//列宽
	    iArray[8][2]=100;            		//列最大值
	    iArray[8][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[9]=new Array();
	    iArray[9][0]="投保单号";        //列名
	    iArray[9][1]="0px";           	//列宽
	    iArray[9][2]=100;            		//列最大值
	    iArray[9][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[10]=new Array();
	    iArray[10][0]="核保结论";        //列名
	    iArray[10][1]="50px";          	 //列宽
	    iArray[10][2]=100;           		 //列最大值
	    iArray[10][3]=0;            		 //是否允许输入,1表示允许，0表示不允许
	    
	    iArray[11]=new Array();
	    iArray[11][0]="核保结论编码";    //列名
	    iArray[11][1]="0px";            //列宽
	    iArray[11][2]=100;           		 //列最大值
	    iArray[11][3]=3;            		 //是否允许输入,1表示允许，0表示不允许
	    
	    iArray[12]=new Array();
	    iArray[12][0]="核保结论编码备份";//列名
	    iArray[12][1]="0px";           //列宽
	    iArray[12][2]=100;           		 //列最大值
	    iArray[12][3]=3;             		 //是否允许输入,1表示允许，0表示不允许
	    
	    if(<%=tCodeType%>=='03'){
	    	iArray[13]=new Array();
	    	iArray[13][0]="保全批次号";   //列名
	    	iArray[13][1]="100px";        //列宽
	    	iArray[13][2]=100;            //列最大值
	    	iArray[13][3]=0;              //是否允许输入,1表示允许，0表示不允许
	    	
	    	iArray[14]=new Array();
	    	iArray[14][0]="更改类型";     //列名
	    	iArray[14][1]="60px";         //列宽
	    	iArray[14][2]=100;            //列最大值
	    	iArray[14][3]=0;              //是否允许输入,1表示允许，0表示不允许
	  	}
	  	
	    RiskInfoGrid = new MulLineEnter( "fm" , "RiskInfoGrid" ); 
	    //这些属性必须在loadMulLine前
	    RiskInfoGrid.mulLineCount = 0;   
	    RiskInfoGrid.displayTitle = 1;
	    RiskInfoGrid.locked = 1;
	    RiskInfoGrid.hiddenPlus = 1;
	    RiskInfoGrid.canSel = 0;
	    RiskInfoGrid.canChk = 1;
	    RiskInfoGrid.hiddenSubtraction = 1;
	    RiskInfoGrid.selBoxEventFuncName = "showRule"; 
	    RiskInfoGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}

//// 理赔信息清单的初始化
function initClaimInfoGrid(){
    var iArray = new Array();
    try
    {
	    iArray[0]=new Array();
	    iArray[0][0]="序号";         		//列名（此列为顺序号，列名无意义，而且不显示）
	    iArray[0][1]="30px";         		//列宽
	    iArray[0][2]=10;          			//列最大值
	    iArray[0][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[1]=new Array();
	    iArray[1][0]="被保险人姓名"; 		//列名
	    iArray[1][1]="80px";            //列宽
	    iArray[1][2]=100;            		//列最大值
	    iArray[1][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[2]=new Array();
	    iArray[2][0]="被保人客户号";   //列名
	    iArray[2][1]="80px";           //列宽
	    iArray[2][2]=100;           		//列最大值
	    iArray[2][3]=0;             		//是否允许输入,1表示允许，0表示不允许

	    iArray[3]=new Array();
	    iArray[3][0]="保单号";    			//列名
	    iArray[3][1]="80px";            //列宽
	    iArray[3][2]=100;            		//列最大值
	    iArray[3][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	
	    iArray[4]=new Array();
	    iArray[4][0]="险种代码";        //列名
	    iArray[4][1]="80px";            //列宽
	    iArray[4][2]=100;            		//列最大值
	    iArray[4][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[5]=new Array();
	    iArray[5][0]="责任编码";        //列名
	    iArray[5][1]="60px";            //列宽
	    iArray[5][2]=100;            		//列最大值
	    if(fmImport.DeTailFlag.value=='1'){
	       iArray[5][3]=3;              		//是否允许输入,1表示允许，0表示不允许
	    }else{
	       iArray[5][3]=0;
	    }
	    iArray[6]=new Array();
	    iArray[6][0]="赔付金额";        //列名
	    iArray[6][1]="60px";            //列宽
	    iArray[6][2]=100;            		//列最大值
	    iArray[6][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[7]=new Array();
	    iArray[7][0]="理赔号";         	//列名
	    iArray[7][1]="80px";            //列宽
	    iArray[7][2]=100;            		//列最大值
	    iArray[7][3]=0;              		//是否允许输入,1表示允许，0表示不允许
	    
	    iArray[8]=new Array();
	    iArray[8][0]="再保保单状态";    //列名
	    iArray[8][1]="0px";            //列宽
	    iArray[8][2]=100;           		 //列最大值
	    iArray[8][3]=3;
	    
	    iArray[9]=new Array();
	    iArray[9][0]="核赔结论";        //列名
	    iArray[9][1]="50px";          	 //列宽
	    iArray[9][2]=100;           		 //列最大值
	    iArray[9][3]=0;            		 //是否允许输入,1表示允许，0表示不允许
	    
	    iArray[10]=new Array();
	    iArray[10][0]="核赔结论编码";    //列名
	    iArray[10][1]="0px";            //列宽
	    iArray[10][2]=100;           		 //列最大值
	    iArray[10][3]=3;
	        	    
	    ClaimInfoGrid = new MulLineEnter( "fm" , "ClaimInfoGrid" ); 
	    //这些属性必须在loadMulLine前
	    ClaimInfoGrid.mulLineCount = 0;   
	    ClaimInfoGrid.displayTitle = 1;
	    ClaimInfoGrid.locked = 1;
	    ClaimInfoGrid.hiddenPlus = 1;
	    ClaimInfoGrid.canSel = 0;
	    ClaimInfoGrid.canChk = 1;
	    ClaimInfoGrid.hiddenSubtraction = 1;
	    ClaimInfoGrid.selBoxEventFuncName = "showRule"; 
	    ClaimInfoGrid.loadMulLine(iArray);  
    }
    catch(ex){
      myAlert(ex);
    }
}
//申请发送信息
function initReInsureAuditGrid()
{                               
    var iArray = new Array();  
    try
    {
    	iArray[0]=new Array();
    	iArray[0][0]="序号";
    	iArray[0][1]="30px";
    	iArray[0][2]=10;
    	iArray[0][3]=0;
    	
    	iArray[1]=new Array();
    	iArray[1][0]="发送序号";
    	iArray[1][1]="60px";
    	iArray[1][2]=100;
    	iArray[1][3]=0;
    	
    	iArray[2]=new Array();
    	iArray[2][0]="发送人";
    	iArray[2][1]="60px";
    	iArray[2][2]=100;
    	iArray[2][3]=0;
    	
    	iArray[3]=new Array();
    	iArray[3][0]="发送回复信息";
    	iArray[3][1]="60px";
    	iArray[3][2]=100;
    	iArray[3][3]=3;
			
    	iArray[4]=new Array();
    	iArray[4][0]="发送时间";
    	iArray[4][1]="80px";
    	iArray[4][2]=100;
    	iArray[4][3]=0;
    	
    	iArray[5]=new Array();
    	iArray[5][0]="上载路径";         	
    	iArray[5][1]="300px";            	
    	iArray[5][2]=300;            			
    	iArray[5][3]=0;              			
    	       
    	iArray[6]=new Array();
    	iArray[6][0]="发送/回复标志";
    	iArray[6][1]="80px";
    	iArray[6][2]=100;
    	iArray[6][3]=0;
    	
    	iArray[7]=new Array();
    	iArray[7][0]="任务状态";
    	iArray[7][1]="80px";
    	iArray[7][2]=100;
    	iArray[7][3]=0;
    	
    	ReInsureAuditGrid = new MulLineEnter( "fmInfo" , "ReInsureAuditGrid" ); 
    	ReInsureAuditGrid.muineCount = 0;   
    	ReInsureAuditGrid.displayTitle = 1;
    	ReInsureAuditGrid.locked = 1;
    	ReInsureAuditGrid.canSel = 1;
    	ReInsureAuditGrid.hiddenPlus = 1;
    	ReInsureAuditGrid.hiddenSubtraction = 1;
    	ReInsureAuditGrid.selBoxEventFuncName = "showInfomation";
    	ReInsureAuditGrid.loadMulLine(iArray);  
    	
    }
    catch(ex)
    {
      myAlert(ex);
    }
}

function initHide(tContNo, tPolNo)
{
	fm.all('ContNo').value=tContNo;
}
</script>


