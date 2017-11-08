<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-08-21
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 	GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
	String CurrentDate= PubFun.getCurrentDate();
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);
    String tCodeType=request.getParameter("CodeType");
    String tSerialNo=request.getParameter("SerialNo");
 %>
<script type="text/javascript">
	var x=0;
	var y=0;
	var com=new Array(y);
	var line=new Array(x);
	
function initInpBox()
{
  try
  {  
    fm.all('OperateNo').value  = <%=request.getParameter("OperateNo")%>;   
    fm.all('CodeType').value	 = <%=tCodeType%>;
    fm.all('AccumulateDefNO').value = '';
    fm.all('RIPreceptNo').value = '';
    fm.all('SerialNo').value	 = <%=tSerialNo%>;
    if(fm.all('CodeType').value=='05'){
    	divTempCessConclusion1.style.display='';
    }else if(fm.all('CodeType').value=='01'){
    	divTempCessConclusion1.style.display='';
    }else{
    	fm.all('RIContNo').value = <%=request.getParameter("ReContCode")%>;   //再保合同编号ReContCode是合同编号的id
    	divTempCessConclusion1.style.display='none';
    }
    divHidden.style.display='none';
  }         
  catch(ex) 
  {
    myAlert("初始化界面错误!");
  }
}
;

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("在LRContInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    intPreceptSearchGrid();
    initContCessGrid();
    initScaleLineGrid();
    initCessScaleGrid();
    initFeeRateGrid();
    initFactorGrid();
    
    queryClick();
    
  }
  catch(re)
  {
    myAlert("ReContManageInit.jsp-->"+"初始化界面错误!");
  }
}

//再保方案查询 mulline初始值设定
function intPreceptSearchGrid(){
	var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1]="30px";            		//列宽
			iArray[0][2]=40;            			  //列最大值
			iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
			
			iArray[1]=new Array();
			iArray[1][0]="再保合同号码";
			iArray[1][1]="90px";  
			iArray[1][2]=100;     
			iArray[1][3]=0;
			
			iArray[2]=new Array();
			iArray[2][0]="再保方案号码";
			iArray[2][1]="90px";  
			iArray[2][2]=100;     
			iArray[2][3]=0;     
			
			iArray[3]=new Array();
			iArray[3][0]="再保方案名称";
			iArray[3][1]="250px"; 
			iArray[3][2]=200;
			iArray[3][3]=0;
			
			iArray[4]=new Array();
			iArray[4][0]="累计方案编码";
			iArray[4][1]="80px";
			iArray[4][2]=100;
			iArray[4][3]=0;
			
			iArray[5]=new Array();
			iArray[5][0]="累计方案名称";
			iArray[5][1]="80px";
			iArray[5][2]=100;
			iArray[5][3]=3;
	    
			iArray[6]=new Array();
			iArray[6][0]="分保公司数";
			iArray[6][1]="80px";
			iArray[6][2]=100; 
			iArray[6][3]=0; 
			
			iArray[7]=new Array();
			iArray[7][0]="溢额层数";
			iArray[7][1]="80px";  
			iArray[7][2]=100;     
			iArray[7][3]=0;
			
			iArray[8]=new Array();
			iArray[8][0]="分保算法ID";
			iArray[8][1]="50px";
			iArray[8][2]=100;
			iArray[8][3]=3;
			
			iArray[9]=new Array();
			iArray[9][0]="分保算法名称";
			iArray[9][1]="30px";  
			iArray[9][2]=100;     
			iArray[9][3]=3;
			
			iArray[10]=new Array();
			iArray[10][0]="分保方案类型代码"; //01：合同分保；02：临时分保
			iArray[10][1]="0px";  
			iArray[10][2]=100;     
			iArray[10][3]=3;
			
			iArray[11]=new Array();
			iArray[11][0]="分保方案类型";
			iArray[11][1]="100px";  
			iArray[11][2]=100;     
			iArray[11][3]=0;
			
			iArray[12]=new Array();
			iArray[12][0]="计算周期";
			iArray[12][1]="50px";  
			iArray[12][2]=100;     
			iArray[12][3]=3;
			
			iArray[13]=new Array();
			iArray[13][0]="计算周期名称";
			iArray[13][1]="80px";  
			iArray[13][2]=100;     
			iArray[13][3]=3;
			
			iArray[14]=new Array();
			iArray[14][0]="计算方式";
			iArray[14][1]="80px";  
			iArray[14][2]=100;     
			iArray[14][3]=3;
			
			iArray[15]=new Array();
			iArray[15][0]="计算方式名称";
			iArray[15][1]="80px";  
			iArray[15][2]=100;     
			iArray[15][3]=3;
			
			iArray[16]=new Array();
			iArray[16][0]="方案状态";
			iArray[16][1]="80px";  
			iArray[16][2]=100;     
			iArray[16][3]=0;
			
			iArray[17]=new Array();
			iArray[17][0]="方案状态码"; //01 生效  02 未生效  
			iArray[17][1]="80px";  
			iArray[17][2]=100;     
			iArray[17][3]=3;

			iArray[18]=new Array();
			iArray[18][0]="分保类型"; //01 生效  02 未生效  
			iArray[18][1]="80px";  
			iArray[18][2]=100;     
			iArray[18][3]=3;

			iArray[19]=new Array();
			iArray[19][0]="分保方式编码"; //01 生效  02 未生效  
			iArray[19][1]="80px";  
			iArray[19][2]=100;     
			iArray[19][3]=3;

			iArray[20]=new Array();
			iArray[20][0]="分保方式名称"; //01 生效  02 未生效  
			iArray[20][1]="80px";  
			iArray[20][2]=100;     
			iArray[20][3]=3;

			iArray[21]=new Array();
			iArray[21][0]="数值类型编码"; //01 生效  02 未生效  
			iArray[21][1]="80px";  
			iArray[21][2]=100;     
			iArray[21][3]=3;

			iArray[22]=new Array();
			iArray[22][0]="数值类型名称"; //01 生效  02 未生效  
			iArray[22][1]="80px";  
			iArray[22][2]=100;     
			iArray[22][3]=3;
															
	  	PreceptSearchGrid = new MulLineEnter( "fm" , "PreceptSearchGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	PreceptSearchGrid.mulLineCount = 10;   
	  	PreceptSearchGrid.displayTitle = 1;
	  	PreceptSearchGrid.locked = 0;
	  	PreceptSearchGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	  	PreceptSearchGrid.selBoxEventFuncName ="showPrecept"; //响应的函数名，不加扩号   
	  	PreceptSearchGrid.hiddenPlus=1; 
	  	PreceptSearchGrid.hiddenSubtraction=1;
	  	PreceptSearchGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

// 溢额线设置列表的初始化
function initContCessGrid()
{                               
    var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1]="30px";            		//列宽
			iArray[0][2]=40;            			  //列最大值
			iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
			
			iArray[1]=new Array();
			iArray[1][0]="层级";
			iArray[1][1]="10px";  
			iArray[1][2]=100;     
			iArray[1][3]=0;    

			iArray[2]=new Array();
			iArray[2][0]="数值类型";
			iArray[2][1]="10px";  
			iArray[2][2]=100;     
			iArray[2][3]=1;
			
			iArray[3]=new Array();
			iArray[3][0]="数值";
			iArray[3][1]="10px";  
			iArray[3][2]=100;     
			iArray[3][3]=1;     
			iArray[3][9]="数值|notnull";  
					
			iArray[4]=new Array();
			iArray[4][0]="溢额线类型";
			iArray[4][1]="15px";  
			iArray[4][2]=100;     
			iArray[4][3]=2;  
			iArray[4][9]="溢额线类型|notnull";    
			iArray[4][10]="IncomeCompanyType"; 		//引用代码："AccType"为传入数据的名称,该控件的名称  
			iArray[4][11]="0|^01|自留额|^02|层次线^03|临分限额^04|最低分出额^05|最大限额";	  
			iArray[4][12]="4|5";		  						//引用代码对应第几列，'|'为分割符
			iArray[4][13]="1|0";		  						//上面的列中放置引用代码中第几位值,即:结果的值的位数
			iArray[4][18]="150";  
			iArray[4][19]=1; 
	    
			iArray[5]=new Array();
			iArray[5][0]="溢额线类型代码";
			iArray[5][1]="0px";  
			iArray[5][2]=100;     
			iArray[5][3]=3;  
			iArray[5][9]="溢额线类型|notnull";
			
			iArray[6]=new Array();
			iArray[6][0]="动态限额系数";
			iArray[6][1]="10px"; 
			iArray[6][2]=100;     
			iArray[6][3]=2;  
			iArray[6][10]="param1"; 		//引用代码："AccType"为传入数据的名称,该控件的名称  
			iArray[6][11]="0|^|^InsuredYear|保单年度";	  
			iArray[6][12]="6|7";		  						//引用代码对应第几列，'|'为分割符
			iArray[6][13]="0|1";		  						//上面的列中放置引用代码中第几位值,即:结果的值的位数
			iArray[6][18]="150";  
			iArray[6][19]=3; 
	    
			iArray[7]=new Array();
			iArray[7][0]="动态系数名称";
			iArray[7][1]="20px";  
			iArray[7][2]=100;     
			iArray[7][3]=3;  
			
	  	ContCessGrid = new MulLineEnter( "fm" , "ContCessGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	ContCessGrid.mulLineCount = 0;   
	  	ContCessGrid.displayTitle = 1;
	  	ContCessGrid.locked = 0;
	  	ContCessGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
	  	//ContCessGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号   
	  	ContCessGrid.hiddenPlus=1; 
	  	ContCessGrid.hiddenSubtraction=1;
	  	ContCessGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

// 分保比例线设置列表的初始化
function initScaleLineGrid()
{                               
    var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1]="30px";            		//列宽
			iArray[0][2]=40;            			  //列最大值
			iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
			
			iArray[1]=new Array();
			iArray[1][0]="公司代码";
			iArray[1][1]="15px";  
			iArray[1][2]=100;  
			iArray[1][3]=2;    
			iArray[1][4]="lrcompanycode"; 
		  iArray[1][5]="1|2"; 	 						//将引用代码分别放在第1、2
		  iArray[1][6]="0|1";
		  iArray[1][19]=1;  
			
//			iArray[1][4]="lrcompany"; 
//			iArray[1][5]="1|2"; 	 							//将引用代码分别放在第1、2
//			iArray[1][6]="0|1";									
//			iArray[1][9]="公司代码|notnull";    
//			iArray[1][19]=1;   									//强制刷新
			
			iArray[2]=new Array();
			iArray[2][0]="公司名称";
			iArray[2][1]="35px";  
			iArray[2][2]=100;     
			iArray[2][3]=1;           
			iArray[2][9]="公司名称|notnull";    
			
			iArray[3]=new Array();
			iArray[3][0]="分保公司类型";
			iArray[3][1]="15px";  
			iArray[3][2]=100;     
			iArray[3][3]=2;  
			iArray[3][9]="分保公司类型|notnull";    
			iArray[3][10]="IncomeCompanyType"; 		  //引用代码："AccType"为传入数据的名称,该控件的名称
	    iArray[3][11]="0|^02|不计算分出^01|计算分出|" 	  //是传入要下拉显示的代码
	    //"0|^1|疾病|^2|意外"
	    iArray[3][12]="3|4"		  //引用代码对应第几列，'|'为分割符
	    iArray[3][13]="1|0"		  //上面的列中放置引用代码中第几位值,即:结果的值的位数
	    iArray[3][18]="150"   
	    iArray[3][19]=1;   		
			
			iArray[4]=new Array();
			iArray[4][0]="分保公司类型代码";
			iArray[4][1]="15px";  
			iArray[4][2]=100;     
			iArray[4][3]=3;  
			iArray[4][9]="分保公司类型代码|notnull";    
			
			iArray[5]=new Array();
			iArray[5][0]="防止空行消失";
			iArray[5][1]="15px";  
			iArray[5][2]=100;     
			iArray[5][3]=3;  
			iArray[5][14]="防止空行消失";  
			
	  	ScaleLineGrid = new MulLineEnter( "fm" , "ScaleLineGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	ScaleLineGrid.mulLineCount = 0;   
	  	ScaleLineGrid.displayTitle = 1;
	  	ScaleLineGrid.locked = 0;
	  	ScaleLineGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
	  	//ScaleLineGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号   
	  	ScaleLineGrid.hiddenPlus=1; 
	  	ScaleLineGrid.hiddenSubtraction=1;
	  	ScaleLineGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
       myAlert(ex);
    }
}

//分保比例设置初始化
function initCessScaleGrid()
{                               
    var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="序号";        				//列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1]="30px";            		//列宽
			iArray[0][2]=40;            			  //列最大值
			iArray[0][3]=3;              			  //是否允许输入,1表示允许，0表示不允许   			     
			
			iArray[1]=new Array();
			iArray[1][0]="层级";        				
			iArray[1][1]="45px";            		
			iArray[1][2]=40;            			  
			iArray[1][3]=0;              			    			     		     
			
			//alert("init:  line"+line.length+"  com:"+com.length);
			for (var i=1;i<=com.length;i++){
				iArray[i+1]=new Array();
				iArray[i+1][0]=com[i-1]; 					//公司名称
				iArray[i+1][1]="55px";  
				iArray[i+1][2]=100;     
				iArray[i+1][3]=1; 
				iArray[i+1][9]="分保比例设置,每行的值 |notnull&num";
			}
	  	CessScaleGrid = new MulLineEnter("fm","CessScaleGrid");
	  	//这些属性必须在loadMulLine前
	  	CessScaleGrid.mulLineCount = 0;   
	  	CessScaleGrid.displayTitle = 1;
	  	CessScaleGrid.locked = 0;
	  	CessScaleGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
	  	//CessScaleGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号   
	  	CessScaleGrid.hiddenPlus=1; 
	  	CessScaleGrid.hiddenSubtraction=1;
	  	CessScaleGrid.loadMulLine(iArray); 
    }
    catch(ex)
    {
       myAlert(ex);
    }
}

function initFeeRateGrid()
{
  var iArray = new Array();   
  try
  {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="再保公司代码";
		iArray[1][1]="40px";  
		iArray[1][2]=100;  
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="再保公司名称";
		iArray[2][1]="50px";  
		iArray[2][2]=100;     
		iArray[2][3]=1;
		
		iArray[3]=new Array();
		iArray[3][0]="区域上限";
		iArray[3][1]="35px";  
		iArray[3][2]=100;     
		iArray[3][3]=1;
		
		iArray[4]=new Array();
		iArray[4][0]="区域下限";
		iArray[4][1]="35px";  
		iArray[4][2]=100;     
		iArray[4][3]=1;
		
		iArray[5]=new Array();
		iArray[5][0]="区域编号";
		iArray[5][1]="35px";  
		iArray[5][2]=100;     
		iArray[5][3]=1;
		
		iArray[6]=new Array();
		iArray[6][0]="分保费率表编号";
		iArray[6][1]="40px";  
		iArray[6][2]=100;  
		iArray[6][3]=2;
		iArray[6][4]="FeeRate"; 	
		iArray[6][5]="6|7"; 
		iArray[6][6]="0|1";								          
		iArray[6][9]="分保费率表|notnull"; 
	  iArray[6][19]=1;
  	
		iArray[7]=new Array();
		iArray[7][0]="分保费率表名称";
		iArray[7][1]="50px";  
		iArray[7][2]=100;     
		iArray[7][3]=0; 
		
		iArray[8]=new Array();
		iArray[8][0]="分保佣金率表编号";
		iArray[8][1]="50px";  
		iArray[8][2]=100;  
		iArray[8][3]=2;
		iArray[8][4]="FeeRate";
		iArray[8][5]="8|9"; 
		iArray[8][6]="0|1";	
	  iArray[8][19]=1;
		
		iArray[9]=new Array();
		iArray[9][0]="分保佣金率表名称";
		iArray[9][1]="50";
		iArray[9][2]=100;     
		iArray[9][3]=0;   
		
		FeeRateGrid = new MulLineEnter( "fm" , "FeeRateGrid" ); 
		//这些属性必须在loadMulLine前
		FeeRateGrid.mulLineCount = 0;   
		FeeRateGrid.displayTitle = 1;
		FeeRateGrid.locked = 0;
		FeeRateGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
		FeeRateGrid.hiddenPlus=1; 
		FeeRateGrid.hiddenSubtraction=1;
		FeeRateGrid.selBoxEventFuncName ="nextStep"; //响应的函数名，不加扩号   
		FeeRateGrid.loadMulLine(iArray); 
				
		
  }
  catch(ex)
  {
     myAlert(ex);
  }
}


// 单证信息列表的初始化
function initFactorGrid()
{   
	  var contNo = fm.RIContNo.value;                           
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="要素名称";
		iArray[1][1]="25px";  
		iArray[1][2]=100;     
		iArray[1][3]=2;    
		iArray[1][4]="lrfactor"; 
		iArray[1][5]="1|2"; 	 							//将引用代码分别放在第1、2
		iArray[1][6]="0|1";
		iArray[1][15]=""+contNo;  
		iArray[1][17]="2";  
		iArray[1][19]=1;
		
		iArray[2]=new Array();
		iArray[2][0]="要素代码";
		iArray[2][1]="25px";  
		iArray[2][2]=100;     
		iArray[2][3]=3;           
		
		iArray[3]=new Array();
		iArray[3][0]="要素值";
		iArray[3][1]="15px";  
		iArray[3][2]=100;     
		iArray[3][3]=1;           
		           
	  FactorGrid = new MulLineEnter( "fm" , "FactorGrid" ); 
	  //这些属性必须在loadMulLine前
	  FactorGrid.mulLineCount = 0;   
	  FactorGrid.displayTitle = 1;
	  FactorGrid.locked = 0;
	  FactorGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
	  //FactorGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号   
	  FactorGrid.hiddenPlus=0; 
	  FactorGrid.hiddenSubtraction=0;
	  FactorGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

</script>


