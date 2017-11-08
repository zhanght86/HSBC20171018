<%
//程序名称：EdorAppManuUWInit.jsp
//程序功能：保全人工核保
//创建日期：2005-04-26 16:49:22
//创建人  ：zhangtao
//更新记录：  更新人liurongxiao    更新日期   2005-06-17  更新原因/内容 与新契约界面取得一致
%>

<script language="JavaScript">

//页面初始化
function initForm()
{
  try
  {
      initParam();
      initInpBox();
      initEdorMainGrid();
      initPolAddGrid();
      initPolRiskGrid();
      initQuery();
      initUWErrGrid();
      //easyQueryClickUW();
  }
  catch (ex)
  {
      alert("在 EdorAppManuUWInit --> InitForm 函数中发生异常:初始化界面错误！ ");
  }
}

//接收从工作流保全申请页面传递过来的参数
function initParam()
{
   document.all('ContNo').value   		= NullToEmpty("<%= tContNo %>");
   document.all('PrtNo').value   		= NullToEmpty("<%= tPrtNo %>");
   //alert(document.all('PrtNo').value);
   //makeWorkFlow();alert(36);
}

//初始化页面元素
function initInpBox()
{

  try
  { 
    document.all('AppntName').value = '';
    document.all('AppntSex').value = '';
    document.all('AppntBirthday').value = '';
    document.all('OccupationCode').value = '';
    document.all('OccupationType').value = '';
    document.all('NativePlace').value = '';
    
    document.all('EdorUWState').value = "";
    document.all('EdorUWStateName').value = "";
    document.all('UWDelay').value = "";
    document.all('UWPopedomCode').value = "";
    document.all('UWIdea').value = "";
  }
  catch (ex)
  {
    alert("在 EdorAppManuUWInit.jsp --> InitInpBox 函数中发生异常:初始化界面错误！ ");
  }
}

// 申请任务列表的初始化
function initEdorMainGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                  //列宽
        iArray[0][2]=30;                      //列最大值
        iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保单号";
        iArray[1][1]="150px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户号";
        iArray[2][1]="150px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="100px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="投保人";
        iArray[4][1]="150px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="被保险人";
        iArray[5][1]="140px";
        iArray[5][2]=200;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="保单生效日期";
        iArray[6][1]="140px";
        iArray[6][2]=200;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="保单状态";
        iArray[7][1]="60px";
        iArray[7][2]=0;
        iArray[7][3]=0;

        iArray[8] = new Array();
        iArray[8][0] = "工作流任务号";
	      iArray[8][1] = "0px";
	      iArray[8][2] = 0;
	      iArray[8][3] = 3;
	
	      iArray[9] = new Array();
	      iArray[9][0] = "工作流子任务号";
	      iArray[9][1] = "0px";
	      iArray[9][2] = 100;
	      iArray[9][3] = 3;
	
	      iArray[10] = new Array();
	      iArray[10][0] = "工作流活动ID";
	      iArray[10][1] = "0px";
	      iArray[10][2] = 0;
	      iArray[10][3] = 3;
	
	      iArray[11] = new Array();
	      iArray[11][0] = "工作流当前活动状态";
	      iArray[11][1] = "0px";
	      iArray[11][2] = 0;
	      iArray[11][3] = 3;
	      
	      iArray[12] = new Array();
	      iArray[12][0] = "被保险人编码";
	      iArray[12][1] = "0px";
	      iArray[12][2] = 0;
	      iArray[12][3] = 3;
	      
	      iArray[13] = new Array();
	      iArray[13][0] = "投保单号";
	      iArray[13][1] = "0px";
	      iArray[13][2] = 0;
	      iArray[13][3] = 3;
	      
	      iArray[14] = new Array();
	      iArray[14][0] = "险种";
	      iArray[14][1] = "0px";
	      iArray[14][2] = 0;
	      iArray[14][3] = 3;

        EdorMainGrid = new MulLineEnter("fm", "EdorMainGrid");
        //这些属性必须在loadMulLine前
        EdorMainGrid.mulLineCount = 3;
        EdorMainGrid.displayTitle = 1;
        EdorMainGrid.locked = 1;
        EdorMainGrid.canSel = 1;
        EdorMainGrid.hiddenPlus = 1;
        EdorMainGrid.hiddenSubtraction=1;
        EdorMainGrid.loadMulLine(iArray);
        EdorMainGrid. selBoxEventFuncName = "showDetailInfo";
    }
    catch (ex)
    {
        alert(ex);
    }
}
function initPolAddGrid()
  {
      var iArray = new Array();

      try
      {
        
		      iArray[0]=new Array();
		      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		      iArray[0][1]="30px";            		//列宽
		      iArray[0][2]=30;            			//列最大值
		      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[1]=new Array();
		      iArray[1][0]="客户号码";         		//列名
		      iArray[1][1]="60px";            		//列宽
		      iArray[1][2]=100;            			//列最大值
		      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		
		      iArray[2]=new Array();
		      iArray[2][0]="姓名";         		//列名
		      iArray[2][1]="60px";            		//列宽
		      iArray[2][2]=100;            			//列最大值
		      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		   
		     	iArray[3]=new Array();
		      iArray[3][0]="性别";         		//列名
		      iArray[3][1]="40px";            		//列宽
		      iArray[3][2]=100;            			//列最大值
		      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
			  	iArray[3][4]="sex";              	        //是否引用代码:null||""为不引用
		    	iArray[3][5]="3";              	                //引用代码对应第几列，'|'为分割符
		
		  		iArray[4]=new Array();
		      iArray[4][0]="年龄";         		//列名
		      iArray[4][1]="40px";            		//列宽
		      iArray[4][2]=100;            			//列最大值
		      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		      iArray[5]=new Array();
		      iArray[5][0]="与投保人关系";         		//列名
		      iArray[5][1]="60px";            		//列宽
		      iArray[5][2]=200;            			//列最大值
		      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
			  	iArray[5][4]="relation";              	        //是否引用代码:null||""为不引用
		    	iArray[5][5]="3";              	                //引用代码对应第几列，'|'为分割符
		
		     	iArray[6]=new Array();
		      iArray[6][0]="与主被保人关系";         		//列名
		      iArray[6][1]="60px";            		//列宽
		      iArray[6][2]=200;            			//列最大值
		      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
			  	iArray[6][4]="relation";              	        //是否引用代码:null||""为不引用
		    	iArray[6][5]="3";              	                //引用代码对应第几列，'|'为分割符
		
		    	iArray[7]=new Array();
		      iArray[7][0]="国籍";         		//列名
		      iArray[7][1]="40px";            		//列宽
		      iArray[7][2]=200;            			//列最大值
		      iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
			  	iArray[7][4]="nativeplace";              	        //是否引用代码:null||""为不引用
		    	iArray[7][5]="3";              	                //引用代码对应第几列，'|'为分割符

          PolAddGrid = new MulLineEnter("fm", "PolAddGrid");
          //这些属性必须在loadMulLine前
          PolAddGrid.mulLineCount = 3;
          PolAddGrid.displayTitle = 1;
          PolAddGrid.locked = 1;
          PolAddGrid.canSel = 1;
          PolAddGrid.hiddenPlus = 1;
          PolAddGrid.hiddenSubtraction = 1;
          PolAddGrid.loadMulLine(iArray);
          //PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
          PolAddGrid.selBoxEventFuncName = "getInsuredDetail";
      }
      catch (ex)
      {
        alert(ex);
      }
}
function initPolRiskGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
   
     	iArray[3]=new Array();
      iArray[3][0]="保额";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

  		iArray[4]=new Array();
      iArray[4][0]="份数";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="保险期间";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
	
     	iArray[6]=new Array();
      iArray[6][0]="交费期间";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

    	iArray[7]=new Array();
      iArray[7][0]="交费频率";         		//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
	


      iArray[8]=new Array();
      iArray[8][0]="标准保费";         		//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="职业加费";         		//列名
      iArray[9][1]="40px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="健康加费";         		//列名
      iArray[10][1]="40px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="险种号";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="居住地加费";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="爱好加费";         		//列名
      iArray[13][1]="40px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="保单状态";         		//列名
      iArray[14][1]="40px";            		//列宽
      iArray[14][2]=200;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[15]=new Array();
      iArray[15][0]="上期核保结论";         		//列名
      iArray[15][1]="60px";            		//列宽
      iArray[15][2]=200;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      PolRiskGrid = new MulLineEnter( "fm" , "PolRiskGrid" ); 
      //这些属性必须在loadMulLine前
      PolRiskGrid.mulLineCount = 3;   
      PolRiskGrid.displayTitle = 1;
      PolRiskGrid.locked = 1;
      PolRiskGrid.canSel = 0;
      PolRiskGrid.hiddenPlus = 1;
      PolRiskGrid.hiddenSubtraction = 1;
      PolRiskGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
   //   PolRiskGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 自动核保信息提示
function initUWErrGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="投保单号";    	//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="被保险人";    	//列名
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种编码";    	//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
     
      iArray[4]=new Array();
      iArray[4][0]="险种名称";    	//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="核保意见";    	//列名
      iArray[5][1]="300px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="核保日期";    	//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="审阅状态";    	//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="投保单号";    	//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="流水号";    	//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
//合同号,被保人,险种编码,险种名称,核保信息,修改时间,是否审阅,
//投保单号,流水号,核保序列号,合同险种标记,投保单号

      iArray[10]=new Array();
      iArray[10][0]="核保顺序号";         			//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="合同险种标记";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="投保单号";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      UWErrGrid = new MulLineEnter( "fm" , "UWErrGrid" ); 
      //这些属性必须在loadMulLine前
      UWErrGrid.mulLineCount = 0;   
      UWErrGrid.displayTitle = 1;
      UWErrGrid.canChk = 1;
      UWErrGrid.locked = 1;
      UWErrGrid.hiddenPlus = 1;
      UWErrGrid.hiddenSubtraction = 1;
      UWErrGrid.loadMulLine(iArray);  
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initPolGrid()
{
}
</script>
