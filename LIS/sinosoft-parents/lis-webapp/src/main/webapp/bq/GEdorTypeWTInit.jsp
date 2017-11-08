<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Tjj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
                      

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        
  
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('OtherNo').value;    
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('ContType').value = top.opener.document.all('ContType').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('CValiDate').value = top.opener.document.all('CValiDate').value;

    //easyQueryClick();

  }
  catch(ex)
  {
    alert("在GEdorTypeWTInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("在PEdorTypeWTInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    easyQueryClick();    
    //initLPPolGrid();
    initWTFeeDetailGrid();
    //alert("ok");
    getWTFeeDetailGrid();

    //initQuery();
    //ctrlGetEndorse();
  }
  catch(re)
  {
    alert("PEdorTypeWTInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initLPPolGrid()
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
      iArray[1][0]="保单号";    	//列名
      iArray[1][1]="200px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户号";         			//列名
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="客户姓名";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="起保日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[5]=new Array();
      iArray[5][0]="保费";         		//列名
      iArray[5][1]="150px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		iArray[6]=new Array();
		iArray[6][0]="币种";         		
		iArray[6][1]="50px";            		 
		iArray[6][2]=60;            			
		iArray[6][3]=2;              		
		iArray[6][4]="Currency";              	  
		iArray[6][9]="币种|code:Currency";

      LPPolGrid = new MulLineEnter( "fm" , "LPPolGrid" ); 
      //这些属性必须在loadMulLine前
      LPPolGrid.mulLineCount = 10;   
      LPPolGrid.displayTitle = 1;
      LPPolGrid.canChk=1;
      LPPolGrid.detailInfo="单击显示详细信息";
      LPPolGrid.loadMulLine(iArray);  
      
      
      }
      catch(ex)
      {
        alert(ex);
      }
}
// 查询按钮
function easyQueryClick()
{
	var tEdorNo;
	var tGrpContNo;
	var tEdorType;
	
	tEdorNo = top.opener.document.all('EdorNo').value;
  tGrpContNo  = top.opener.document.all('ContNoApp').value;
  tEdorType = top.opener.document.all('EdorType').value;  
	
	document.all('GetMoney').value = '';
	var strSQL = "";	

// 	strSQL = "select sum(prem)-10 from lcpol where grpcontno='"+ tGrpContNo +"' and appflag = '1'";
	var sqlid1="GEdorTypeWTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorTypeWTInputSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tGrpContNo);//指定传入的参数
	strSQL=mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	if (!turnPage.strQueryResult) 
  {
  		    
  }else
	 {
	 	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		document.all('GetMoney').value = turnPage.arrDataCacheSet[0][0];
	 }

}

function initQuery()
{	
	
	 var i = 0;
	 var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	 var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	 showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		
		document.all('fmtransact').value = "QUERY||MAIN";
		//alert("----begin---");
		//showSubmitFrame(mDebug);
		fm.submit();	  	 	 
}

function initWTFeeDetailGrid()
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
        iArray[1][0]="险种保单号";
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
        iArray[4][0]="生效日期";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;   
        
        iArray[5]=new Array();
        iArray[5][0]="缴费金额";
        iArray[5][1]="50px";
        iArray[5][2]=100;
        iArray[5][3]=0;   
        
		iArray[6]=new Array();
		iArray[6][0]="币种";         		
		iArray[6][1]="50px";            		 
		iArray[6][2]=60;            			
		iArray[6][3]=2;              		
		iArray[6][4]="Currency";              	  
		iArray[6][9]="币种|code:Currency";
        
      WTFeeDetailGrid = new MulLineEnter( "fm" , "WTFeeDetailGrid" ); 
      //这些属性必须在loadMulLine前
      WTFeeDetailGrid.mulLineCount = 0;   
      WTFeeDetailGrid.displayTitle = 1;
      WTFeeDetailGrid.canChk=0;
      WTFeeDetailGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      WTFeeDetailGrid.hiddenSubtraction=1;
      WTFeeDetailGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
