<%
//程序名称：ReportQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
//单击时查询
function reportDetailClick(cObj)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divLJSGetEndorse.style.left=ex;
  	divLJSGetEndorse.style.top =ey;
    	divLJSGetEndorse.style.display ='';
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	

  }
  catch(ex)
  {
    alert("在LJSGetEndorseInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在LCPolQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        
// 查询按钮
function easyQueryClick(Flag)
{
	var tEdorNo;
	var tPolNo;
	var tEdorType;
	var tGrpPolNo;
	// 书写SQL语句
	var strSQL = "";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.LJSGetEndorseInitSql"); //指定使用的properties文件名
	//alert("---"); 
	tEdorNo = top.opener.document.all('EdorNo').value;
      	//alert(tEdorNo);
  tEdorType = top.opener.document.all('EdorType').value;
      	//alert(tEdorType);
  var queryFlag = "0";   	
  if (top.opener.document.all('ContType').value=='1')
	{
	     	tPolNo  = top.opener.document.all('PolNo').value;
// 	     	strSQL = "select PolNo,PayPlanCode,FeeFinaType,GetMoney from LJSGetEndorse where EndorsementNo='"+ tEdorNo + "' and FeeOperationType='" + tEdorType+"'";
	     	var sqlid1="LJSGetEndorseInitSql1";
	     	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	    	mySql1.addSubPara(tEdorNo);//指定传入的参数
	    	mySql1.addSubPara(tEdorType);//指定传入的参数
	}
	else
	{
		tGrpPolNo = top.opener.document.all('GrpPolNo').value;
		try
		{
		   	tPolNo  = top.opener.document.all('QueryPolNo').value;
				queryFlag="1";
		}
		catch(ex)
		{
		}
		if (queryFlag=="1")
		{
// 			strSQL = "select PolNo,PayPlanCode,FeeFinaType,GetMoney from LJSGetEndorse where EndorsementNo='"+ tEdorNo + "' and FeeOperationType='" + tEdorType+"' and PolNo='"+tPolNo+"'";
			var sqlid2="LJSGetEndorseInitSql2";
	     	mySql1.setSqlId(sqlid2);//指定使用的Sql的id
	    	mySql1.addSubPara(tEdorNo);//指定传入的参数
	    	mySql1.addSubPara(tEdorType);//指定传入的参数
	    	mySql1.addSubPara(tPolNo);//指定传入的参数
		}
		else
// 			strSQL = "select PolNo,PayPlanCode,FeeFinaType,GetMoney from LJSGetEndorse where EndorsementNo='"+ tEdorNo + "' and FeeOperationType='" + tEdorType+"'";
			var sqlid3="LJSGetEndorseInitSql3";
	     	mySql1.setSqlId(sqlid3);//指定使用的Sql的id
	    	mySql1.addSubPara(tEdorNo);//指定传入的参数
	    	mySql1.addSubPara(tEdorType);//指定传入的参数
	}		
	//alert(strSQL);	  
	var strsql = "";
	switch(Flag) 
	{
		case "PolNo":
			strsql = " order by PolNo";
			break;
		case "PayPlanCode":
			strsql = "order by PayPlanCode";
			break;
		case "FeeFinaType":
			strsql = "order by FeeFinaType";
			break;
		case "GetMoney":
			strsql = "order by GetMoney";
			break;
		default:
			break;
		
	}		
	//alert(strSQL);	
		//alert("querybef");	
	mySql1.addSubPara(strsql);//指定传入的参数
	strSQL=mySql1.getString();
	execEasyQuery( strSQL );
	//alert("query");
}

function displayEasyResult(arrResult )
{
	var i, j, m, n;
//alert("init");
	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		//alert("init");
		initLJSGetEndorseGrid();
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		//alert(n);
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			//alert(i);
			for( j = 0; j < m; j++ )
			{
				//alert("j;"+j);
				LJSGetEndorseGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

function initForm(Flag)
{
  try
  {
  //alert('111');
    initInpBox();
    initSelBox();    
    //alert('222');
    initLJSGetEndorseGrid();
   // alert('333');
    
    //easyQueryClick(Flag);
  }
  catch(re)
  {
    alert("LJSGetEndorseInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 费用信息信息列表的初始化
function initLJSGetEndorseGrid()
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
      iArray[2][0]="交费项目编码";		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="费用类型编码";		//列名
      iArray[3][1]="200px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="费用金额";         		//列名
      iArray[4][1]="200px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用


      LJSGetEndorseGrid = new MulLineEnter( "fm" , "LJSGetEndorseGrid" ); 
      //这些属性必须在loadMulLine前
      LJSGetEndorseGrid.mulLineCount = 10;   
      LJSGetEndorseGrid.displayTitle = 1;
      LJSGetEndorseGrid.canSel=1;
      LJSGetEndorseGrid.loadMulLine(iArray);  
      LJSGetEndorseGrid.detailInfo="单击显示详细信息";
      LJSGetEndorseGrid.detailClick=reportDetailClick;
      //这些操作必须在loadMulLine后面
    
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>