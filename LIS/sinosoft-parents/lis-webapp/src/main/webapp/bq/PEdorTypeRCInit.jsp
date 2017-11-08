<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Tjj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->


<script language="JavaScript">
var str_sql = "",sql_id = "", my_sql = "";
function initForm()
{
  try
  {
    initInpBox();
    initDivLayer();
    initCustomerGrid();
    queryCustomerInfo();
    initMainPolGrid();     
    initRCQuery(); //查询已录入补发信息

  }
  catch(re)
  {
    alert("PEdorTypeRCInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{

  try
  {
    Edorquery();
    try { document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value; } catch (ex) {}
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
  }
  catch(ex)
  {
    alert("在PEdorTypeRCInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!" + ex);
  }
}

function initCustomerGrid()
{
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="客户号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="40px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="角色";					//列名1
      iArray[2][1]="30px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="姓名";         			//列名2
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="性别";         		//列名8
      iArray[4][1]="20px";            		//列宽
      iArray[4][2]=30;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="出生日期";     //列名6
      iArray[5][1]="30px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="证件类型";         		//列名8
      iArray[6][1]="20px";            		//列宽
      iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="证件号码";         		//列名5
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

     

      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 1;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;
      CustomerGrid.hiddenPlus = 1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.selBoxEventFuncName ="";
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.detailInfo="单击显示详细信息";
      CustomerGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function queryCustomerInfo()
{
  var tContNo=document.all('ContNo').value;
	var strSQL="";
	//alert("------"+tContNo+"---------");
	if(tContNo!=null || tContNo !='')
	  {
// 	  strSQL =  " Select a.appntno,'投保人',a.appntname,a.appntsex||'-'||sex.codename,a.appntbirthday,a.idtype||'-'||x.codename,a.idno From lcappnt a "
// 										+" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
// 										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
// 										+" Union"
// 										+" Select i.insuredno,'被保人',i.name,i.Sex||'-'||sex.codename,i.Birthday,i.IDType||'-'||xm.codename,i.IDNo From lcinsured i "
// 										+" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
// 										+" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
	//alert("-----------"+strSQL+"------------");
	  sql_id = "PEdorTypeRCInputSql4";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeRCInputSql"); //指定使用的properties文件名
	  my_sql.setSqlId(sql_id);//指定使用的Sql的id
	  my_sql.addSubPara(tContNo);//指定传入的参数
	  my_sql.addSubPara(tContNo);//指定传入的参数
	  str_sql = my_sql.getString();
	}
	else
	{
		alert('没有相应的投保人或被保人信息！');
	}
	var arrSelected = new Array();
	turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);

	//判断是否查询成功
	if (!turnPage.strQueryResult) {
  //alert("没有相应的投保人或被保人信息！");
		return false;
	}

	//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//查询成功则拆分字符串，返回二维数组personQuery
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = CustomerGrid;
	//保存SQL语句
	turnPage.strQuerySql = strSQL;
	//设置查询起始位置
	turnPage.pageIndex = 0;
	//在查询结果数组中取出符合页面显示大小设置的数组
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//调用MULTILINE对象显示查询结果
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

function initMainPolGrid() 
{                               
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="40px";         			//列宽
      //iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";					//列名1
      iArray[2][1]="160px";            		//列宽
      //iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人客户号";
      iArray[3][1]="50px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="被保人姓名";
      iArray[4][1]="50px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="保费标准";         			//列名2
      iArray[5][1]="40px";            		//列宽
      //iArray[5][2]=60;            			//列最大值
      iArray[5][3]=7;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][23]="0";  

      iArray[6]=new Array();
      iArray[6][0]="份数";         		//列名8
      iArray[6][1]="40px";            		//列宽
      //iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="基本保额";         		//列名5
      iArray[7][1]="40px";            		//列宽
      //iArray[7][2]=100;            			//列最大值
      iArray[7][3]=7;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      iArray[7][23]="0"; 
      
      iArray[8]=new Array();
      iArray[8][0]="生效日期";     //列名6
      iArray[8][1]="50px";            		//列宽
      //iArray[8][2]=50;            			//列最大值
      iArray[8][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="交费对应日";         		//列名8
      iArray[9][1]="50px";            		//列宽
      //iArray[9][2]=30;            			//列最大值
      iArray[9][3]=8;
      
      iArray[10]=new Array();
      iArray[10][0]="MainPolNo";             //隐藏列
      iArray[10][1]="0px";            		//列宽
      //iArray[10][2]=50;            			//列最大值
      iArray[10][3]=3;              			
      
      iArray[11]=new Array();
      iArray[11][0]="PolNo";             //隐藏列
      iArray[11][1]="0px";            		//列宽
      //iArray[11][2]=50;            			//列最大值
      iArray[11][3]=3;
      
      iArray[12]=new Array();
			iArray[12][0]="币种";
			iArray[12][1]="60px";
			iArray[12][2]=100;
			iArray[12][3]=2;
			iArray[12][4]="currency";

      MainPolGrid = new MulLineEnter( "fm" , "MainPolGrid" ); 
      //这些属性必须在loadMulLine前
      MainPolGrid.displayTitle = 1;
      MainPolGrid.canSel=0;
      MainPolGrid.hiddenPlus = 1;
      MainPolGrid.hiddenSubtraction = 1;
      MainPolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
//       tSql = "SELECT distinct RiskCode,"
// 		             + " (select RiskName from LMRisk where RiskCode=a.RiskCode),"
// 		             + " InsuredNo,"
// 		             + " InsuredName,"
//        		       + " Prem,"
// 		 		         + " Mult,"
// 		 		         + " Amnt,"
// 		 		         + " CValiDate,"
// 		 		         + " PayToDate,"
// 		 		         + " MainPolNo,"
// 		 		         + " PolNo,"
// 		 		         + "currency"
// 		      + " FROM LCPol a"
// 		      + " WHERE ContNo='" + document.all('ContNo').value + "' order by polno";
      sql_id = "PEdorTypeRCInputSql5";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeRCInputSql"); //指定使用的properties文件名
	  my_sql.setSqlId(sql_id);//指定使用的Sql的id
	  my_sql.addSubPara(document.all('ContNo').value);//指定传入的参数
	  str_sql = my_sql.getString();
		   easyQueryVer3Modal(str_sql,MainPolGrid);
    }
    catch(ex) 
    {
       alert(ex);
    }
}
</script>
