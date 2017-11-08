<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Supl
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
    
%>                            

<script language="JavaScript">  
function initInpBox()
{ 
  try
  {   
    //Edorquery();
    //alert(top.opener.document.all('EdorNo').value);      
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('EdorTypeCal').value = top.opener.document.all('EdorTypeCal').value;
    //alert(document.all('EdorTypeCal').value);
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeCTInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {  
  }
  catch(ex)
  {
    alert("在PEdorTypeCTInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initGrpPolGrid(); 
    initCTFeePolGrid();
    initCTFeeDetailGrid();
    getPolGrid(document.all('GrpContNo').value);
    getZTMoney(); //退费合计金额 
    getContZTInfo(); //退保信息     
    getCTFeePolGrid(); //险种退费合计     
    //getCTFeeDetailGrid(); //退保费用明细 
    
  }
  catch(re)
  {
    alert("PEdorTypeCTInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInusredGrid()

{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        
        iArray[2]=new Array();
        iArray[2][0]="姓名";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="性别";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="出生日期";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;        
        
        
        iArray[5]=new Array();
        iArray[5][0]="保单号码";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=3;
        
        iArray[6]=new Array();
        iArray[6][0]="集体保单号码";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
        //这些属性必须在loadMulLine前
        InsuredGrid.mulLineCount = 0;   
        InsuredGrid.displayTitle = 1;
        //alert(fm.DisplayType.value);        
        InsuredGrid.canChk=1;                                                                  
        InsuredGrid.canSel =1;                                                                 
        InsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数

        InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        InsuredGrid.hiddenSubtraction=1;
        InsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initGrpPolGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="险种代码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="300px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="团体险种保单号";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="保额";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="保费";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;        
        
        iArray[6]=new Array();
        iArray[6][0]="总保费";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="投保人数";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="集体保单号码";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;

		iArray[9]=new Array();
		iArray[9][0]="币种";         		
		iArray[9][1]="60px";            		 
		iArray[9][2]=60;            			
		iArray[9][3]=2;              		
		iArray[9][4]="Currency";              	  
		iArray[9][9]="币种|code:Currency";        
        
      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //这些属性必须在loadMulLine前
      GrpPolGrid.mulLineCount = 0;   
      GrpPolGrid.displayTitle = 1;
      //PolGrid.canChk=1;
      GrpPolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      GrpPolGrid.hiddenSubtraction=1;
      GrpPolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initCustomerGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="70px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="30px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;        

      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" ); 
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 10;   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;       
      //PolGrid.canChk=1;
      CustomerGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.selBoxEventFuncName ="" ; 
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initCTFeeDetailGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="险种号";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="40px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="120px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="费用名称";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="费用金额";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=0;        
        
        iArray[6]=new Array();
        iArray[6][0]="费用类型";
        iArray[6][1]="40px";
        iArray[6][2]=100;
        iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="币种";         		
		iArray[7][1]="60px";            		 
		iArray[7][2]=60;            			
		iArray[7][3]=2;              		
		iArray[7][4]="Currency";              	  
		iArray[7][9]="币种|code:Currency";
        
      CTFeeDetailGrid = new MulLineEnter( "fm" , "CTFeeDetailGrid" ); 
      //这些属性必须在loadMulLine前
      CTFeeDetailGrid.mulLineCount = 0;   
      CTFeeDetailGrid.displayTitle = 1;
      CTFeeDetailGrid.canChk=0;
      CTFeeDetailGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CTFeeDetailGrid.hiddenSubtraction=1;
      CTFeeDetailGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initCTFeePolGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="团体险种保单号";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
         iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="退费合计";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="币种";         		
		iArray[5][1]="60px";            		 
		iArray[5][2]=60;            			
		iArray[5][3]=2;              		
		iArray[5][4]="Currency";              	  
		iArray[5][9]="币种|code:Currency";
        
      CTFeePolGrid = new MulLineEnter( "fm" , "CTFeePolGrid" ); 
      //这些属性必须在loadMulLine前
      CTFeePolGrid.mulLineCount = 0;   
      CTFeePolGrid.displayTitle = 1;
      CTFeePolGrid.canChk=0;
      CTFeePolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      CTFeePolGrid.hiddenSubtraction=1;
      CTFeePolGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLCPolGrid()
{
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          		  	//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单险种号";    	  //列名1
      iArray[1][1]="80px";             //列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";        	//列名2
      iArray[2][1]="50px";              //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种名称";         	//列名3
      iArray[3][1]="120px";            	//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="客户号";         		//列名4
      iArray[4][1]="60px";            	//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              	

      iArray[5]=new Array();
      iArray[5][0]="客户姓名";         	//列名5
      iArray[5][1]="60px";            	//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="生效日";        //列名6
      iArray[6][1]="60px";            	//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[7]=new Array();
      iArray[7][0]="保费";        //列名6
      iArray[7][1]="60px";            	//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[8]=new Array();
      iArray[8][0]="保额";        //列名6
      iArray[8][1]="60px";            	//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[9]=new Array();
      iArray[9][0]="份数";        //列名6
      iArray[9][1]="60px";            	//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[10]=new Array();
      iArray[10][0]="退费金额";        //列名6
      iArray[10][1]="70px";            	//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不?

		iArray[11]=new Array();
		iArray[11][0]="币种";         		
		iArray[11][1]="60px";            		 
		iArray[11][2]=60;            			
		iArray[11][3]=2;              		
		iArray[11][4]="Currency";              	  
		iArray[11][9]="币种|code:Currency";     
                  
      
      LCPolGrid = new MulLineEnter( "fm" , "LCPolGrid" ); 
      //LCPolGrid.mulLineCount = 10;   
      LCPolGrid.displayTitle = 1;
      LCPolGrid.hiddenPlus = 1;
      LCPolGrid.hiddenSubtraction = 1;
      //LCPolGrid.canSel = 1;
      //LCPolGrid.selBoxEventFuncName = "reportDetailClick";
      //LCInsureAccClassGrid.chkBoxEventFuncName = "reportDetailClick1";
      LCPolGrid.loadMulLine(iArray);      
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
