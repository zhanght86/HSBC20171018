<%
//PEdorTypeHIInit.jsp
//程序功能： 
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">  
//单击时查询
var str_sql = "",sql_id = "", my_sql = "";
function initInpBox()
{ 

  try
  { 
    Edorquery(); 
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;   
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('CustomerNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;  
     
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
  	showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeHIInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeHIInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    QueryCustomerInfo();
    initContGrid();
    initImpartGrid();
    initNewImpartGrid();
    QueryImpartInfo();
    QueryNewImpartInfo();
    initInpRole();
  }
  catch(re)
  {
    alert("PEdorTypeHIInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initImpartGrid() 
{                               
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[3][4]="ImpartContent";

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
	    ImpartGrid.hiddenSubtraction=1;
      ImpartGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
      alert(ex);
    }
}

function initNewImpartGrid() 
{                               
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="impartver";
      iArray[1][9]="告知版别|len<=3";
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A05#,#A06#)";

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      //iArray[2][16]="#02#";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[3][4]="ImpartContent";

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      //iArray[4][4]="ImpartParamModle";
      iArray[4][9]="填写内容|len<=200";

      NewImpartGrid = new MulLineEnter( "fm" , "NewImpartGrid" ); 
      //这些属性必须在loadMulLine前
      NewImpartGrid.mulLineCount = 1;   
      NewImpartGrid.displayTitle = 1;
      NewImpartGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
      alert(ex);
    }
}


function QueryNewImpartInfo()
{
// 	var tSql = "SELECT distinct ImpartVer,ImpartCode,ImpartContent,ImpartParamModle"
// 					+ " FROM LPCustomerImpart a"
// 					+ " WHERE a.EdorType='HI' and exists(select 'X' from LPEdorItem where EdorNo=a.EdorNo and EdorAcceptNo='" + document.all('EdorAcceptNo').value + "')";
	sql_id = "PEdorTypeHIInputSql8";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorTypeHIInputSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(document.all('EdorAcceptNo').value);//指定传入的参数
	str_sql = my_sql.getString();
	easyQueryVer3Modal(str_sql,NewImpartGrid);
}


function QueryImpartInfo()
{
// 	var tSql = "SELECT distinct ImpartVer,ImpartCode,ImpartContent,ImpartParamModle"
// 					+ " FROM LCCustomerImpart a"
// 					+ " WHERE a.Customerno='" + document.all('CustomerNo').value + "' and contno='"+document.all('ContNo').value+"'";
	sql_id = "PEdorTypeHIInputSql9";
	my_sql = new SqlClass();
	my_sql.setResourceName("bq.PEdorTypeHIInputSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(document.all('CustomerNo').value);//指定传入的参数
	my_sql.addSubPara(document.all('ContNo').value);//指定传入的参数
	str_sql = my_sql.getString();
	
	easyQueryVer3Modal(str_sql,ImpartGrid);
}

function QueryCustomerInfo() 
{
// 		var tSql = "SELECT"
// 						     + " Name as r0,"
// 						     + " Sex as r1,"
// 						     + " Marriage as r2,"
// 						     + " Birthday as r3,"
// 						     + " IDType as r6,"
// 						     + " IDNo as r7 "
// 						  + " FROM LDPerson a"
// 						  + " WHERE a.CustomerNo='" + document.all('CustomerNo').value + "'";
		sql_id = "PEdorTypeHIInputSql10";
		my_sql = new SqlClass();
		my_sql.setResourceName("bq.PEdorTypeHIInputSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(document.all('CustomerNo').value);//指定传入的参数
		str_sql = my_sql.getString();
    var arrResult = easyExecSql(str_sql,1,0);
    if (arrResult==null)
    {
    	alert("查询客户信息失败，请返回！");
    	return;
    }
    //显示投保人信息
		displayCustomerBasicFC(arrResult);
}

//显示投保人的详细信息，从C表查出
function displayCustomerBasicFC(arrResult)
{
		try{document.all('Name').value= arrResult[0][0];}catch(ex){};
		try{document.all('Sex').value= arrResult[0][1];}catch(ex){};
		try{document.all('Marriage').value= arrResult[0][2];}catch(ex){};
		try{document.all('Birthday').value= arrResult[0][3];}catch(ex){};
		try{document.all('IDType').value= arrResult[0][4];}catch(ex){};
		try{document.all('IDNo').value= arrResult[0][5];}catch(ex){};

		//由编码取得名称
		showOneCodeName('sex','Sex','SexName');
		showOneCodeName('marriage','Marriage','MarriageName');
		showOneCodeName('idtype','IDType','IDTypeName');
		
		//alert("displayAppntBasicFC Finish!");
}

function initContGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=100;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="个人保单号";
      iArray[1][1]="100px";
      //iArray[1][2]=100;
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="客户角色";
      iArray[2][1]="70px";
      //iArray[2][2]=100;
      iArray[2][3]=0;      
      
      iArray[3]=new Array();
      iArray[3][0]="保单所属管理机构";
      iArray[3][1]="350px";
      //iArray[3][2]=20;
      iArray[3][3]=0;
      
	    ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
	    ContGrid.displayTitle = 1;
	    ContGrid.canChk=0;       
	    ContGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
	    ContGrid.hiddenSubtraction=1;
	    ContGrid.loadMulLine(iArray);
			selectContInfo();
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>