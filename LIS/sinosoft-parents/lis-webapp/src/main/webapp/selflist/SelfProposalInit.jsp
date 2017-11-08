<%
//程序名称：SelfProposalInputInit.jsp
//程序功能：自助卡单-客户信息录入
//创建日期：2008-03-05 
//创建人  ：zz
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() 
{ 
  try 
  {  
                                   
	// 保单信息部分
	document.all('CertifyNo').value=tCertifyNo;
	document.all("CValiDate").readOnly = true;


	// 投保人信息部分  
  	document.all( 'AppntName' ).value                = "";
  	document.all( 'AppntSex' ).value                 = "";
  	document.all( 'AppntBirthday' ).value            = "";
  	document.all( 'AppntIDType' ).value              = "";
  	document.all( 'AppntIDNo' ).value                = "";
  	document.all( 'AppntPostalAddress' ).value       = "";
  	document.all( 'AppntZipCode' ).value             = "";
  	document.all( 'AppntPhone' ).value              = "";
  	document.all( 'AppntEMail' ).value               = "";
  	document.all( 'AppntOccupationType' ).value      = "";
  	document.all( 'AppntOccupationCode' ).value      = "";
  	document.all( 'RelationToLCInsured' ).value      = "";
  	
  	//alert("初始化投保人完毕");
  	
  	// 被保人信息部分  
  	document.all( 'LCInsuredName' ).value                = "";
  	document.all( 'LCInsuredSex' ).value                 = "";
  	document.all( 'LCInsuredBirthday' ).value            = "";
  	document.all( 'LCInsuredIDType' ).value              = "";
  	document.all( 'LCInsuredIDNo' ).value                = "";
  	document.all( 'LCInsuredPostalAddress' ).value       = "";
  	document.all( 'LCInsuredZipCode' ).value             = "";
  	document.all( 'LCInsuredPhone' ).value              = "";
  	document.all( 'LCInsuredEMail' ).value               = "";
  	document.all( 'LCInsuredOccupationType' ).value      = "";
  	document.all( 'LCInsuredOccupationCode' ).value      = "";

    //alert("初始化被保人完毕");
    
  } 
  catch(ex)
  {
     alert("在SelfProposalInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  } 
  
}

                                       

//初始化函数
function initForm() 
{
	initInpBox();
	initAppntCodeGrid();
	initLCInsuredCodeGrid();
}


//投保人职业类别表
function initAppntCodeGrid()
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
    iArray[1][0]="职业类别";    	//列名
    iArray[1][1]="30px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="职业名称";         			//列名
    iArray[2][1]="150px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="行业名称";         			//列名
    iArray[3][1]="150px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="职业代码";         			//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    AppntCodeGrid = new MulLineEnter( "fm" , "AppntCodeGrid" );
    //LCInsuredCodeGrid.mulLineCount = 10;
    AppntCodeGrid.displayTitle = 1;
    AppntCodeGrid.canSel=1;
    AppntCodeGrid.locked = 1;
    AppntCodeGrid.selBoxEventFuncName = "afterAppntLOccupationSelect";
    AppntCodeGrid.loadMulLine(iArray);
    AppntCodeGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert(ex);
  }
}


//被保险人职业类别表
function initLCInsuredCodeGrid()
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
    iArray[1][0]="职业类别";    	//列名
    iArray[1][1]="30px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="职业名称";         			//列名
    iArray[2][1]="150px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="行业名称";         			//列名
    iArray[3][1]="150px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="职业代码";         			//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    LCInsuredCodeGrid = new MulLineEnter( "fm" , "LCInsuredCodeGrid" );
    //LCInsuredCodeGrid.mulLineCount = 10;
    LCInsuredCodeGrid.displayTitle = 1;
    LCInsuredCodeGrid.canSel=1;
    LCInsuredCodeGrid.locked = 1;
    LCInsuredCodeGrid.selBoxEventFuncName = "afterLOccupationSelect";
    LCInsuredCodeGrid.loadMulLine(iArray);
    LCInsuredCodeGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert(ex);
  }
}


</script>
