<%
//PEdorTypeIAInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//            lizhuo      2005.5.25
%>


<script language="JavaScript">
//单击时查询
var str_sql = "",sql_id = "", my_sql = "";
function initInpBox()
{
  try
  {
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    //document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('ReFlag').value = "NO";
    showOneCodeName('PEdorType','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeREInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
  	//alert('1');
    Edorquery();
    //alert('2');
    initInpBox();
    //alert('3');
    //initQueryPolInfo();
    initImpartGrid();
   // alert('4');
    initAppntImpartGrid();
  //  alert('5');
    initLCInsuredGrid();
   // alert('6');
    initCustomerGrid();
  //  alert('7');
    queryCustomerInfo();
  //  alert('8');
    //initPolInsuredGrid();
 //   alert('9');
    getInfo();
 //  alert('10'); 
    showCustomerImpart();
 //   alert('11');
    showRelaInsured();
 //   alert('12');
    showimpart();
  // alert('13'); 
    showotherimpart();
    queryCalInterest();
  }
  catch(re)
  {
    alert("PEdorTypeTRInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initQuery()
{
    var i = 0;
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "QUERY||MAIN";
    //alert("----begin---");
    //showSubmitFrame(mDebug);
    fm.submit();

}

function initQueryPolInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    var arrSelected = new Array();
    //alert("------"+tPolNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
//       strSQL = "SELECT RISKCODE,POLNO,APPNTNO,APPNTNAME,INSUREDNO,INSUREDNAME,MULT,PREM,AMNT,'','' FROM LCPOL WHERE MAINPOLNO=POLNO AND "
//                             +"CONTNO='"+tContNo+"'";
      sql_id = "PEdorTypeREInputSql10";
      my_sql = new SqlClass();
      my_sql.setResourceName("bq.PEdorTypeREInputSql"); //指定使用的properties文件名
      my_sql.setSqlId(sql_id);//指定使用的Sql的id
      my_sql.addSubPara(tContNo);//指定传入的参数
      str_sql = my_sql.getString();
    alert("-----------"+strSQL+"------------");
    }
    else
    {
        alert('没有相应的险种代码！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);
  if(!turnPage.strQueryResult){
        alert("查询失败");
    }
    else
    {
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

  try {document.all('RiskCode').value = arrSelected[0][0];} catch(ex) { }; //险种代码
  try {document.all('RiskName').value = arrSelected[0][1];} catch(ex) { }; //险种名称
  try {document.all('AppntNo').value = arrSelected[0][2];} catch(ex) { }; //投保人号码
  try {document.all('AppntName').value = arrSelected[0][3];} catch(ex) { }; //投保人名称
  try {document.all('InsuredNo').value = arrSelected[0][4];} catch(ex) { }; //被保人号码
  try {document.all('InsuredName').value = arrSelected[0][5];} catch(ex) { }; //被保人名称
  try {document.all('Mult').value = arrSelected[0][6];} catch(ex) { }; //份数
  try {document.all('Prem').value = arrSelected[0][7];} catch(ex) { }; //保费
  try {document.all('Amnt').value = arrSelected[0][8];} catch(ex) { }; //保额
  //try {document.all('Prem1').value = arrSelected[0][9];} catch(ex) { };
  }
}
function initImpartGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=30;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="impartver";
      //add by jiaqiangli 2009-03-12 保全只取健康告知与财务告知
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A05#,#A06#)";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";              //列名
      iArray[2][1]="70px";                  //列宽
      iArray[2][2]=100;                     //列最大值
      iArray[2][3]=2;                       //是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";              //列名
      iArray[3][1]="235px";                 //列宽
      iArray[3][2]=300;                     //列最大值
      iArray[3][3]=1;                       //是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";              //列名
      iArray[4][1]="175px";                 //列宽
      iArray[4][2]=300;                     //列最大值
      iArray[4][3]=1;                       //是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";                //列名
//      iArray[5][1]="90px";                    //列宽
//      iArray[5][2]=90;                        //列最大值
//      iArray[5][3]=2;                         //是否允许输入,1表示允许，0表示不允许
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";                //列名
//      iArray[6][1]="90px";                    //列宽
//      iArray[6][2]=90;                        //列最大值
//      iArray[6][3]=2;                         //是否允许输入,1表示允许，0表示不允许
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
//险种信息列表
function initLCInsuredGrid()
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
      iArray[1][0]="险种代码";
      iArray[1][1]="80px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="险种名称";
      iArray[2][1]="130px";
      iArray[2][2]=200;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="被保人姓名";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="基本保额";
      iArray[4][1]="70px";
      iArray[4][2]=120;
      iArray[4][3]=7;
      iArray[4][21]=3;
      iArray[4][23]="0";

      iArray[5]=new Array();
      iArray[5][0]="份数";
      iArray[5][1]="70px";
      iArray[5][2]=100;
      iArray[5][3]=0;

      iArray[6]=new Array();
      iArray[6][0]="保费标准";
      iArray[6][1]="70px";
      iArray[6][2]=100;
      iArray[6][3]=7;
      iArray[6][21]=3;
      iArray[6][23]="0";

      iArray[7]=new Array();
      iArray[7][0]="健康加费";
      iArray[7][1]="70px";
      iArray[7][2]=100;
      iArray[7][3]=7;
      iArray[7][21]=3;
      iArray[7][23]="0";

      iArray[8]=new Array();
      iArray[8][0]="职业加费";
      iArray[8][1]="70px";
      iArray[8][2]=100;
      iArray[8][3]=7;
      iArray[8][21]=3;
      iArray[8][23]="0";

      iArray[9]=new Array();
      iArray[9][0]="投保人号";
      iArray[9][1]="0px";
      iArray[9][2]=0;
      iArray[9][3]=3;

      iArray[10]=new Array();
      iArray[10][0]="被保人号";
      iArray[10][1]="0px";
      iArray[10][2]=0;
      iArray[10][3]=3;
      
      iArray[11]=new Array();
			iArray[11][0]="币种";
			iArray[11][1]="60px";
			iArray[11][2]=100;
			iArray[11][3]=2;
			iArray[11][4]="currency";

      LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" );
      //这些属性必须在loadMulLine前
      LCInsuredGrid.mulLineCount = 4;
      LCInsuredGrid.displayTitle = 1;
      LCInsuredGrid.canChk=0;
      LCInsuredGrid.hiddenPlus = 1;
      LCInsuredGrid.hiddenSubtraction = 1;
      LCInsuredGrid.selBoxEventFuncName ="";
      LCInsuredGrid.loadMulLine(iArray);
      LCInsuredGrid.detailInfo="单击显示详细信息";
      }
      catch(ex)
      {
        alert(ex);
      }
}
//客户基本信息列表
function initCustomerGrid()
{
    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=30;                      //列最大值
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="客户号码";
      iArray[1][1]="80px";
      iArray[1][2]=100;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="角色";
      iArray[2][1]="80px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="姓名";
      iArray[3][1]="90px";
      iArray[3][2]=120;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="性别";
      iArray[4][1]="70px";
      iArray[4][2]=100;
      iArray[4][3]=0;
      iArray[4][21]=2;

      iArray[5]=new Array();
      iArray[5][0]="出生日期";
      iArray[5][1]="90px";
      iArray[5][2]=100;
      iArray[5][3]=8;
      iArray[5][21]=3;

      iArray[6]=new Array();
      iArray[6][0]="证件类型";
      iArray[6][1]="110px";
      iArray[6][2]=120;
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="证件号码";
      iArray[7][1]="120px";
      iArray[7][2]=150;
      iArray[7][3]=0;

      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );
      //这些属性必须在loadMulLine前
      CustomerGrid.mulLineCount = 4;
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;
      CustomerGrid.hiddenPlus = 1;
      CustomerGrid.hiddenSubtraction = 1;
      CustomerGrid.selBoxEventFuncName ="queryCustomerInfo";
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.detailInfo="单击显示详细信息";
      }
      catch(ex)
      {
        alert(1);
        alert(ex);
      }
}
///////查询客户信息
function queryCustomerInfo()
{

  var tContNo=top.opener.document.all('ContNo').value;
    var strSQL="";
    if(tContNo!=null || tContNo !='')
    {
//       strSQL =" Select a.appntno,'投保人',a.appntname,concat(concat(a.appntsex,'-'),sex.codename),a.appntbirthday,concat(concat(a.idtype,'-'),x.codename),a.idno From lcappnt a "
//                                         +" Left Join (Select code,codename From ldcode Where codetype='idtype') x On x.code = a.idtype "
//                                         +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = a.appntsex  Where contno='"+tContNo+"'"
//                                         +" Union"
//                                         +" Select i.insuredno,'被保人',i.name,concat(concat(i.Sex,'-'),sex.codename),i.Birthday,concat(concat(i.IDType,'-'),xm.codename),i.IDNo From lcinsured i "
//                                         +" Left Join (Select code,codename From ldcode Where codetype='idtype') xm On xm.code = i.idtype "
//                                         +" Left Join (Select code,codename From ldcode Where codetype='sex') sex On sex.code = i.sex Where contno='"+tContNo+"'";
	  var strSQL = "";
      sql_id = "PEdorTypeREInputSql11";
      my_sql = new SqlClass();
      my_sql.setResourceName("bq.PEdorTypeREInputSql"); //指定使用的properties文件名
      my_sql.setSqlId(sql_id);//指定使用的Sql的id
      my_sql.addSubPara(tContNo);//指定传入的参数
      my_sql.addSubPara(tContNo);//指定传入的参数
      strSQL = my_sql.getString();
    }
    else
    {
        alert('没有相应的投保人或被保人信息！');
    }
    var arrSelected = new Array();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
  //alert("没有相应的投保人或被保人信息！");
        return false;
    }

    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //查询成功则拆分字符串，返回二维数组
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

function getInfo(){

    var tContNo=top.opener.document.all('ContNo').value;

	//add by jiaqiangli 2009-03-11  length(dutycode)=6 and
//     var strSQL = "select c.riskcode,d.riskname,c.insuredname,c.amnt,c.mult,(case when (select sum(e.prem) from lcprem e where char_length(dutycode)=6 and e.polno = c.polno and e.PayPlanType = '0') is not null then (select sum(e.prem) from lcprem e where char_length(dutycode)=6 and e.polno = c.polno and e.PayPlanType = '0') else 0 end),(case when (select sum(p.prem) from lcprem p where p.polno = c.polno and p.PayPlanType in ('03', '01')) is not null then (select sum(p.prem) from lcprem p where p.polno = c.polno and p.PayPlanType in ('03', '01')) else 0 end),(case when (select sum(m.prem) from lcprem m where m.polno = c.polno and m.PayPlanType in ('04', '02')) is not null then (select sum(m.prem) from lcprem m where m.polno = c.polno and m.PayPlanType in ('04', '02')) else 0 end),c.appntno,c.insuredno,c.currency from lcpol c, lmrisk d where c.riskcode = d.riskcode and c.mainpolno = c.polno and c.contno = '"+tContNo+"' union select a.riskcode,b.riskname,a.insuredname,a.amnt,a.mult,a.standprem,(case when (select sum(q.prem) from lcprem q where q.polno = a.polno and q.PayPlanType in ('03', '01')) is not null then (select sum(q.prem) from lcprem q where q.polno = a.polno and q.PayPlanType in ('03', '01')) else 0 end),(case when (select sum(n.prem) from lcprem n where n.polno = a.polno and n.PayPlanType in ('04', '02')) is not null then (select sum(n.prem) from lcprem n where n.polno = a.polno and n.PayPlanType in ('04', '02')) else 0 end),a.appntno,a.insuredno,a.currency from lcpol a, lmrisk b where a.riskcode = b.riskcode and a.contno = '"+tContNo+"' and a.mainpolno <> a.polno and a.appflag = '1' and a.polno not in (select distinct polno from lccontstate where statetype = 'Terminate' and state = '1' and contno = '"+tContNo+"')";
    var strSQL = "";
    sql_id = "PEdorTypeREInputSql12";
    my_sql = new SqlClass();
    my_sql.setResourceName("bq.PEdorTypeREInputSql"); //指定使用的properties文件名
    my_sql.setSqlId(sql_id);//指定使用的Sql的id
    my_sql.addSubPara(tContNo);//指定传入的参数
    my_sql.addSubPara(tContNo);//指定传入的参数
    my_sql.addSubPara(tContNo);//指定传入的参数
    strSQL = my_sql.getString();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    //判断是否查询成功
    if (!turnPage.strQueryResult) {
  //alert("没有相应的投保人或被保人信息！");
        return false;
    }
    //alert(turnPage.strQueryResult);
    //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
    turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
    //查询成功则拆分字符串，返回二维数组
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
    turnPage.pageDisplayGrid = LCInsuredGrid;
    //保存SQL语句
    turnPage.strQuerySql = strSQL;
    //设置查询起始位置
    turnPage.pageIndex = 0;
    //在查询结果数组中取出符合页面显示大小设置的数组
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
    //调用MULTILINE对象显示查询结果
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    //alert(arrDataSet[0][8]);
    document.all('CustomerNo').value = arrDataSet[0][8];
    //alert(arrDataSet[0][9]);
    document.all('InsuredNo').value = arrDataSet[0][9];
}


function initPolInsuredGrid()
{
    var iArray = new Array();

      try
      {

      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种代码";
      iArray[1][1]="80px";
      iArray[1][2]=40;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="险种名称";
      iArray[2][1]="120px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="欠交期数";
      iArray[3][1]="50px";
      iArray[3][2]=50;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="基本保费欠交额";
      iArray[4][1]="70px";
      iArray[4][2]=70;
      iArray[4][3]=7;
      iArray[4][23]="0";

      iArray[5]=new Array();
      iArray[5][0]="健康加费欠交额";
      iArray[5][1]="70px";
      iArray[5][2]=70;
      iArray[5][3]=7;
      iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="职业加费欠交额";
      iArray[6][1]="70px";
      iArray[6][2]=70;
      iArray[6][3]=7;
      iArray[6][23]="0";

      iArray[7]=new Array();
      iArray[7][0]="欠交保费利息";
      iArray[7][1]="60px";
      iArray[7][2]=60;
      iArray[7][3]=7;
      iArray[7][23]="0";

      iArray[8]=new Array();
      iArray[8][0]="贷款未清偿欠费";
      iArray[8][1]="70px";
      iArray[8][2]=70;
      iArray[8][3]=7;
      iArray[8][23]="0";

      iArray[9]=new Array();
      iArray[9][0]="自垫未清偿欠费";
      iArray[9][1]="70px";
      iArray[9][2]=70;
      iArray[9][3]=7;
      iArray[9][23]="0";

      iArray[10]=new Array();
      iArray[10][0]="年金给付应退";
      iArray[10][1]="70px";
      iArray[10][2]=60;
      iArray[10][3]=7;
      iArray[10][23]="0";

      iArray[11]=new Array();
      iArray[11][0]="费用合计";
      iArray[11][1]="80px";
      iArray[11][2]=40;
      iArray[11][3]=7;
      iArray[11][23]="0";
      
      iArray[12]=new Array();
			iArray[12][0]="币种";
			iArray[12][1]="60px";
			iArray[12][2]=100;
			iArray[12][3]=2;
			iArray[12][4]="currency";
alert('##');
      PolInsuredGrid = new MulLineEnter( "fm" , "PolInsuredGrid" );
      //这些属性必须在loadMulLine前
      PolInsuredGrid.mulLineCount = 0;
      PolInsuredGrid.displayTitle = 1;
      PolInsuredGrid.canChk=0;
      PolInsuredGrid.hiddenPlus = 1;
      PolInsuredGrid.hiddenSubtraction = 1;
      PolInsuredGrid.selBoxEventFuncName ="";
      alert('##1');
      PolInsuredGrid.loadMulLine(iArray);
      alert('##2');
      PolInsuredGrid.detailInfo="单击显示详细信息";
      alert('##3');
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initAppntImpartGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="impartver";
      //add by jiaqiangli 2009-03-12 保全只取健康告知与财务告知
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A01#,#A02#)";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=2;
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";
      iArray[3][1]="250px";
      iArray[3][2]=200;
      iArray[3][3]=1;

      iArray[4]=new Array();
      iArray[4][0]="填写内容";
      iArray[4][1]="150px";
      iArray[4][2]=150;
      iArray[4][3]=1;
      iArray[4][9]="填写内容|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";
//      iArray[5][1]="90px";
//      iArray[5][2]=90;
//      iArray[5][3]=2;
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";
//      iArray[6][1]="90px";
//      iArray[6][2]=90;
//      iArray[6][3]=2;
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      AppntImpartGrid = new MulLineEnter( "fm" , "AppntImpartGrid" );
      //这些属性必须在loadMulLine前
      AppntImpartGrid.mulLineCount = 1;
      AppntImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      AppntImpartGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
