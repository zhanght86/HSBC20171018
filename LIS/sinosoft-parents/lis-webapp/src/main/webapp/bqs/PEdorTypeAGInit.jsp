<%
//程序名称：PEdorTypeAGInit.jsp
//程序功能：
//创建日期：2005-6-16
//创建人  ：Nicholas
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->


<script language="JavaScript">
function initInpBox()
{

  try
  {
    //Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');

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
//    setOption("t_sex","0=男&1=女&2=不详");
//    setOption("sex","0=男&1=女&2=不详");
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");
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
    //initSelBox();
    //easyQueryClick();
    //initLPPolGrid();
    QueryCustomerInfo();
    //formatGetModeAbout();
    //initQuery();
    //ctrlGetEndorse();
    initPolGrid();
    //QueryEdorInfo();
    queryBankInfo();
  }
  catch(re)
  {
    alert("PEdorTypeWTInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化

// 查询按钮
function easyQueryClick()
{


}

function initQuery()
{

}
 function QueryCustomerInfo()
{
	var tContNo=top.opener.document.all('ContNo').value;
	var strSQL=""
	//alert("------"+tContNo+"---------");
	if(tContNo!=null || tContNo !='')
	  {
	 /*  strSQL = "SELECT APPNTNAME,APPNTIDTYPE,APPNTIDNO,INSUREDNAME,INSUREDIDTYPE,INSUREDIDNO FROM LCCONT WHERE 1=1 AND "
							+"CONTNO='"+tContNo+"'"; */
	  
	  
	var sqlid1 = "PEdorTypeAGInitSql1";
  	var mySql1 = new SqlClass();
  	mySql1.setResourceName("bqs.PEdorTypeAGInitSql"); // 指定使用的properties文件名
  	mySql1.setSqlId(sqlid1);// 指定使用的Sql的id
  	mySql1.addSubPara(tContNo);// 指定传入的参数
  	strSQL = mySql1.getString();
	//alert("-----------"+strSQL+"------------");
	}
	else
	{
		alert('没有客户信息！');
	}
	var arrSelected = new Array();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
	//arrResult = easyExecSql("select * from LDGrp where GrpNo = '" + arrQueryResult[0][0] + "'", 1, 0);
  if(!turnPage.strQueryResult){
		alert("查询失败");
	}
	else
	{
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //投保人姓名
  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //投保人证件类型
  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //投保人证件号码
  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //被保人名称
  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //被保人证件类型
  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //被保人证件号码

  showOneCodeName('idtype','AppntIDType','AppntIDTypeName');
  showOneCodeName('idtype','InsuredIDType','InsuredIDTypeName');
  }
}

//险种信息列表
function initPolGrid()
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
      iArray[1][0]="领取项目";
      iArray[1][1]="170px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="给付期数";
      iArray[2][1]="100px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="给付金额";
      iArray[3][1]="100px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="给付通知书号码";
      iArray[4][1]="0px";
      iArray[4][2]=20;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="责任编码";
      iArray[5][1]="0px";
      iArray[5][2]=10;
      iArray[5][3]=3;

      iArray[6]=new Array();
      iArray[6][0]="给付责任编码";
      iArray[6][1]="0px";
      iArray[6][2]=6;
      iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="给付责任类型";
      iArray[7][1]="0px";
      iArray[7][2]=6;
      iArray[7][3]=3;

      iArray[8]=new Array();
      iArray[8][0]="险种编码";
      iArray[8][1]="0px";
      iArray[8][2]=10;
      iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="给付分类";
      iArray[9][1]="100px";
      iArray[9][2]=100;
      iArray[9][3]=0;

      iArray[10]=new Array();
      iArray[10][0]="应付日期";
      iArray[10][1]="100px";
      iArray[10][2]=100;
      iArray[10][3]=0;

	    PolGrid = new MulLineEnter( "fm" , "PolGrid" );
	    //这些属性必须在loadMulLine前
	    //PolGrid.mulLineCount = 3;
	    PolGrid.displayTitle = 1;
	    PolGrid.canChk=0;
	    PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
	    PolGrid.hiddenSubtraction=1;
	    PolGrid.loadMulLine(iArray);
	    PolGrid.selBoxEventFuncName ="" ;
	    //这些操作必须在loadMulLine后面
	    //显示符合标准的领取项信息
/* 	    var strSql="SELECT distinct (select distinct GetDutyName from LMDutyGetAlive where GetDutyCode=b.GetDutyCode and GetDutyKind=b.GetDutyKind),"
	    				+ " (select count(*) + 1 from LJAGetDraw where PolNo=b.PolNo and DutyCode=b.DutyCode and GetDutyKind=b.GetDutyKind and GetDutyCode=b.GetDutyCode and GetDate<b.GetDate),"
	    				+ " b.GetMoney,b.GetNoticeNo,b.DutyCode,b.GetDutyCode,b.GetDutyKind,b.RiskCode,"
	    				+ " nvl((select decode(GetType1,'0','满期金','1','年金','未知') from LMDutyGet where GetDutyCode=b.GetDutyCode),'无记录'),"
	    				+ " to_char(b.GetDate,'YYYY-MM-DD')"
	            + " FROM LJAGetDraw b"
	            + " WHERE exists(select 'y' from LPGet where PolNo=b.PolNo and DutyCode=b.DutyCode and GetDutyKind=b.GetDutyKind and GetDutyCode=b.GetDutyCode and GetToDate=b.LastGettoDate and EdorNo='"+document.all('EdorNo').value+"' and EdorType='"+document.all('EdorType').value+"')"
	            + " and (b.RReportFlag='1' or b.ComeFlag='1')"
	            + " and b.GetDate<=to_date('" + document.all('EdorValiDate').value + "','YYYY-MM-DD')"
	            //+ " and not exists(select 'X' from LCPol where PolNo=b.PolNo and GetForm='1')"
							+ " and not exists(select 'A' from LCContState where ContNo=b.ContNo and StateType='Loan' and State='1' and EndDate is null and StartDate<=b.GetDate)"
							+ " and not exists(select 'B' from LCContState where PolNo='" + document.all('PolNo').value + "' and StateType='PayPrem' and State='1' and EndDate is null and StartDate<=b.GetDate)";
														 //alert(strSql);
//			easyQueryVer3Modal(strSql,PolGrid);

 */

	    var sqlid2 = "PEdorTypeAGInitSql2";
	  	var mySql2 = new SqlClass();
	  	mySql2.setResourceName("bqs.PEdorTypeAGInitSql"); // 指定使用的properties文件名
	  	mySql2.setSqlId(sqlid2);// 指定使用的Sql的id
	  	mySql2.addSubPara(document.all('EdorNo').value);// 指定传入的参数
	  	mySql2.addSubPara(document.all('EdorType').value);// 指定传入的参数
	  	mySql2.addSubPara(document.all('EdorValiDate').value);// 指定传入的参数
	  	mySql2.addSubPara(document.all('PolNo').value);// 指定传入的参数
	  	var strSql = mySql2.getString();
        turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = PolGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

			//计算领取金额
		  var i;
			var sumGetMoney = 0; //领取金额的和
			for (i=0;i<PolGrid.mulLineCount;i++)
			{
					sumGetMoney = sumGetMoney + eval(PolGrid.getRowColData(i,3));
			}
			document.all('MoneyAdd').value = sumGetMoney;
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
