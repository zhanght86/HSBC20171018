<%
//程序名称：NewSelfProposalInputInit.jsp
//程序功能：新自助卡单-客户信息录入
//创建日期：2010-01-25 
//创建人  ：yanglh
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
//  	document.all( 'RelationToLCInsured' ).value      = "";
  	
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
     alert("在NewSelfProposalInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  } 
  
}

                                       

//初始化函数
function initForm() 
{
	initInpBox();
	initAppntCodeGrid();
	initLCInsuredCodeGrid();
	initLCInsuredGrid();
	initPeoples();
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


//被保险人表
function initLCInsuredGrid()
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
    iArray[1][0]="姓名";    	//列名
    iArray[1][1]="80px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		iArray[1][9]="被保人姓名|notnull&len<=20";     

    iArray[2]=new Array();
    iArray[2][0]="性别";         			//列名
    iArray[2][1]="150px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    iArray[2][9]="被保人性别|notnull&code:Sex";     
    
    iArray[3]=new Array();
    iArray[3][0]="出生日期";         			//列名
    iArray[3][1]="150px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 		iArray[3][9]="被保人出生日期|notnull&date";
    
    iArray[4]=new Array();
    iArray[4][0]="证件类型";         			//列名
    iArray[4][1]="100px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		iArray[4][9]="被保人证件类型|code:IDType";
		
		iArray[5]=new Array();
    iArray[5][0]="证件号码";         			//列名
    iArray[5][1]="100px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		iArray[5][9]="被保人证件号码|len<=20";

		iArray[6]=new Array();
    iArray[6][0]="职业类别";         			//列名
    iArray[6][1]="100px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		iArray[7]=new Array();
    iArray[7][0]="职业代码";         			//列名
    iArray[7][1]="100px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许


		iArray[8]=new Array();
    iArray[8][0]="联系地址";         			//列名
    iArray[8][1]="100px";            		//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		iArray[8][9]="被保人联系地址|len<=80";
		
		iArray[9]=new Array();
    iArray[9][0]="邮政编码";         			//列名
    iArray[9][1]="100px";            		//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		iArray[9][9]="被保人邮政编码|zipcode";
		
		iArray[10]=new Array();
    iArray[10][0]="联系电话";         			//列名
    iArray[10][1]="100px";            		//列宽
    iArray[10][2]=100;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    iArray[10][9]="被保人联系电话|len<=15";

    
    iArray[11]=new Array();
    iArray[11][0]="电子邮箱";         			//列名
    iArray[11][1]="100px";            		//列宽
    iArray[11][2]=100;            			//列最大值
    iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许	
		iArray[11][9]="被保人电子邮箱|len<=20";
		
		iArray[12]=new Array();
    iArray[12][0]="与投保人关系";         			//列名
    iArray[12][1]="100px";            		//列宽
    iArray[12][2]=100;            			//列最大值
    iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许	
		iArray[12][9]="与投保人关系|CODE:Relation";
    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" );
    //LCInsuredCodeGrid.mulLineCount = 10;
    LCInsuredGrid.displayTitle = 1;
    LCInsuredGrid.canSel=1;
    LCInsuredGrid.locked = 1;
    LCInsuredGrid.selBoxEventFuncName = "afterInsuredSelect";
    LCInsuredGrid.loadMulLine(iArray);
    LCInsuredGrid.hiddenPlus = 1;
    LCInsuredGrid.hiddenSubtraction = 1;
//    LCInsuredGrid.detailInfo="单击显示详细信息";
  }
  catch(ex)
  {
    alert(ex);
  }
}

//查询被保人数量
function initPeoples(){

	  var tCertifyCode = fm.CertifyNo.value.substring(2,8);
	  var tSql="select d.peoples3,plankind1 from ldplan d where d.contplancode in (select riskcode from lmcardrisk c where  portfolioflag=1 and c.certifycode='"+tCertifyCode+"') and d.portfolioflag=1";

    arrResult = easyExecSql(tSql, 1, 0);
    
    if (arrResult == null) 
    {
      alert("产品配置没有相关信息，请核实");
  //    displayAppnt(new Array());
    } 
    else 
    {
      fm.Peoples.value=arrResult[0][0];
      fm.AmntRate.value=arrResult[0][1];
    }
}

</script>
