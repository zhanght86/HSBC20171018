<%
//PEdorTypeENInit.jsp
//程序功能：
//创建日期：2005-06-20 11:10:36
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
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    //alert (document.all('EdorNo').value);

  }
  catch(ex)
  {
    alert("在PEdorTypeENInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

/*
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeENInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}
*/

function initForm()
{
  try
  {

    initInpBox();
    //initSelBox();
    //initLCInsuredGrid();
    initCustomerGrid();
    queryCustomerInfo();
    initMainPolGrid();
    //initPolGrid();
    //showRiskInfo();
    initPolInfoGrid();
    QueryEdorInfo();
  }
  catch(re)
  {
    alert("PEdorTypeENInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//本程序不需要此函数
//function initQuery()
//{
//	var tContNo=document.all("ContNo").value;
//	var strSQL ="select PolNo from LCPol where 1=1 and MainPolNo <> PolNo and PolNo = '" + tContNo + "'";
//
//
//}


//客户基本信息列表
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
  var tContNo=top.opener.document.all('ContNo').value;
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
	  sql_id = "PEdorTypeENInputSql2";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //指定使用的properties文件名
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
      iArray[1][1]="20px";         			//列宽
      //iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";					//列名1
      iArray[2][1]="50px";            		//列宽
      //iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人客户号";
      iArray[3][1]="20px";
      iArray[3][2]=100;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="被保人姓名";
      iArray[4][1]="20px";
      iArray[4][2]=100;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="保费标准";         			//列名2
      iArray[5][1]="30px";            		//列宽
      //iArray[5][2]=60;            			//列最大值
      iArray[5][3]=7;              			//是否允许输入,1表示允许，0表示不允许
			iArray[5][23]="0";

      iArray[6]=new Array();
      iArray[6][0]="份数";         		//列名8
      iArray[6][1]="20px";            		//列宽
      //iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="基本保额";         		//列名5
      iArray[7][1]="30px";            		//列宽
      //iArray[7][2]=100;            			//列最大值
      iArray[7][3]=7;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      iArray[7][23]="0";
      
      iArray[8]=new Array();
      iArray[8][0]="生效日期";     //列名6
      iArray[8][1]="30px";            		//列宽
      //iArray[8][2]=50;            			//列最大值
      iArray[8][3]=8;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="交费对应日";         		//列名8
      iArray[9][1]="30px";            		//列宽
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
				iArray[12][1]="20px";
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
// 		      + " WHERE ContNo='" + document.all('ContNo').value + "'";
      var tSql = "";
      sql_id = "PEdorTypeENInputSql3";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //指定使用的properties文件名
	  my_sql.setSqlId(sql_id);//指定使用的Sql的id
	  my_sql.addSubPara(document.all('ContNo').value);//指定传入的参数
	  tSql = my_sql.getString();
      
		   easyQueryVer3Modal(tSql,MainPolGrid);
    }
    catch(ex) 
    {
       alert(ex);
    }
}


function initPolGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="险种代码";
        iArray[1][1]="20px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="险种号码";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="被保人客户号";
        iArray[3][1]="30px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="被保人姓名";
        iArray[4][1]="30px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="基本保额/份数";
        iArray[5][1]="30px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="保费标准";
        iArray[6][1]="30px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="生效日期"
        iArray[7][1]="30px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="交费对应日";
        iArray[8][1]="30px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="保单号码";
        iArray[9][1]="30px";
        iArray[9][2]=100;
        iArray[9][3]=3;

        iArray[10]=new Array();
        iArray[10][0]="集体保单号码";
        iArray[10][1]="30px";
        iArray[10][2]=100;
        iArray[10][3]=3;

        PolGrid = new MulLineEnter( "fm" , "PolGrid" );
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 0;  //显示的行数，0为根据具体内容动态显示
        PolGrid.displayTitle = 1;
        PolGrid.canChk=0;       //1-显示checkbox选项，0-不显示checkbox选项
        PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolGrid.hiddenSubtraction=1;
        PolGrid.loadMulLine(iArray);

        //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}


/*********************************************************************
 *  查询险种信息，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showRiskInfo()
{

    var tContNo=document.all("ContNo").value;
    var tEdorAcceptNo=document.all('EdorAcceptNo').value;
    var tContNo=document.all('ContNo').value;
    var tEdorType=document.all('EdorType').value;
    var tInsuredNo=document.all('InsuredNo').value;
    var tPolNo=document.all('PolNo').value;

    /*
    var strSQL = " select p.RiskCode, p.PolNo, p.InsuredNo, p.InsuredName,"
                + " p.amnt, p.prem, p.CValiDate, p.PayToDate, p.ContNo, p.GrpContNo "
                + " from LCPol p,LPEdorItem e where p.ContNo='" + tContNo + "' "
                + " and p.ContNo = e.ContNo and e.EdorAcceptNo = '" + tEdorAcceptNo + "'"
                + " and e.EdorType = '" + tEdorType + "'"
                + " and e.InsuredNo = '" + tInsuredNo + "'"
                + " order by p.makedate, p.maketime";
    */
//     var strSQL = " select RiskCode, PolNo, InsuredNo, InsuredName,"
//                 + " amnt, prem, CValiDate, PayToDate, ContNo, GrpContNo "
//                 + " from LCPol where PolNo='" + tPolNo + "' "
//                 + " order by makedate, maketime";

      var strSQL = "";
      sql_id = "PEdorTypeENInputSql4";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //指定使用的properties文件名
	  my_sql.setSqlId(sql_id);//指定使用的Sql的id
	  my_sql.addSubPara(tPolNo);//指定传入的参数
	  strSQL = my_sql.getString();
    //alert (strSQL);
    turnPage.queryModal(strSQL, PolGrid);

}


function initPolInfoGrid() 
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
      iArray[1][1]="30px";         			//列宽
      //iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";					//列名1
      iArray[2][1]="180px";            		//列宽
      //iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

			iArray[3]=new Array();
      iArray[3][0]="主险编码";         			//列名2
      iArray[3][1]="30px";            		//列宽
      //iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[4]=new Array();
      iArray[4][0]="PolNo";             //隐藏列
      iArray[4][1]="0px";            		//列宽
      //iArray[4][2]=50;            			//列最大值
      iArray[4][3]=3;
      
      iArray[5]=new Array();
      iArray[5][0]="RnewFlag";             
      iArray[5][1]="0px";            		//列宽
      //iArray[4][2]=50;            			//列最大值
      iArray[5][3]=3;
      
      iArray[6]=new Array();
      iArray[6][0]="原险种续保状态";            
      iArray[6][1]="30px";            		//列宽
      //iArray[4][2]=50;            			//列最大值
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="新险种续保状态变更";           
      iArray[7][1]="30px";            		//列宽
      //iArray[4][2]=50;            			//列最大值
      iArray[7][3]=2;
      iArray[7][4]="RnewFlag";
      iArray[7][5]="7|8";
      iArray[7][6]="0|1";;
      
      iArray[8]=new Array();
      iArray[8][0]="新险种续保状态变更";           
      iArray[8][1]="30px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=0;
      
	    iArray[9]=new Array();
      iArray[9][0]="用户以前是否选过此项的标志";
      iArray[9][1]="0px";
      iArray[9][2]=1;
      iArray[9][3]=3;     			

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      //PolGrid.mulLineCount = 1;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel=0;
      PolGrid.canChk=1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      //PolGrid.tableWidth   ="500px";
      PolGrid.loadMulLine(iArray); 
      PolGrid.chkBoxEventFuncName="";
      //PolGrid.chkBoxEventFuncName="checkMainPol"; 
      
      //这些操作必须在loadMulLine后面 RnewFlag
      tSql ="" 
           //+"select c1.riskcode,"
       		 //+" (select riskname from lmrisk where riskcode = c1.riskcode),"
       		 //+" (select riskcode from lcpol where polno = c1.mainpolno),"
      	   //+" c1.polno,"
       		 //+" c1.rnewflag,"
      	   //+" decode(c1.rnewflag, '-2', '不续保', '-1', '自动续保', '未知')"
   				 //+" from lcpol c1 "
  				 //+" where c1.riskcode in (select r1.riskcode "
           //+" from lmriskedoritem r1, lmrisk a1 "
           //+" where r1.edorcode = 'EN' "
           //+" and a1.rnewflag = 'Y' "
           //+" and r1.riskcode = a1.riskcode) "
    			 //+" and c1.polno = c1.mainpolno "
   				 //+" and c1.riskcode in "
    		   //+" (select b1.riskcode from lmriskapp b1 where b1.riskperiod <> 'L') "
   				 //+" and c1.contno = '"+document.all('ContNo').value+"'"
 					 //+" union "
 					 
// 					 +" select c.riskcode,"
//            +" (select riskname from lmrisk where riskcode= c.riskcode),"
//            +" (select riskcode from lcpol where polno = c.mainpolno),"
//            +" c.polno,"
//            +" c.rnewflag,"
//            +" CASE c.rnewflag WHEN -1 THEN '自动续保' ELSE '不续保' END,"
//            +" CASE WHEN ( SELECT p1.rnewflag FROM lppol p1 WHERE p1.polno = c.polno AND p1.edorno = '"+document.all("EdorNo").value+"') IS NOT NULL THEN ( SELECT p1.rnewflag FROM lppol p1 WHERE p1.polno = c.polno AND p1.edorno = '"+document.all("EdorNo").value+"' ) ELSE -2 END, "
//            +" CASE ( SELECT p2.rnewflag FROM lppol p2 WHERE p2.polno = c.polno AND p2.edorno =  '"+document.all("EdorNo").value+"') WHEN -1 THEN '自动续保' ELSE '不续保' END, "
//            +" (select 1 from lppol p3 where p3.polno = c.polno and p3.edorno = '"+document.all("EdorNo").value+"') "
//            +" from lcpol c where c.riskcode in ("
// 					 +" select r.riskcode"
//            +" from lmriskedoritem r, lmrisk a"
// 				   +" where r.edorcode = 'EN'"
// 				   +" and a.rnewflag = 'Y'"
// 			     +" and r.riskcode = a.riskcode) and "
// 			     +" c.riskcode in "
// 			     +" (select b.riskcode from lmriskapp b where b.riskperiod <> 'L') and "
// 			     +" c.appflag='1' and c.contno='"+document.all('ContNo').value+"' order by c.polno";   
			 //alert("Begin");
	  var tSql = "";
      sql_id = "PEdorTypeENInputSql5";
	  my_sql = new SqlClass();
	  my_sql.setResourceName("bq.PEdorTypeENInputSql"); //指定使用的properties文件名
	  my_sql.setSqlId(sql_id);//指定使用的Sql的id
	  my_sql.addSubPara(document.all("EdorNo").value);//指定传入的参数
	  my_sql.addSubPara(document.all("EdorNo").value);//指定传入的参数
	  my_sql.addSubPara(document.all("EdorNo").value);//指定传入的参数
	  my_sql.addSubPara(document.all("EdorNo").value);//指定传入的参数
	  my_sql.addSubPara(document.all('ContNo').value);//指定传入的参数
	  tSql = my_sql.getString();
	  easyQueryVer3Modal(tSql,PolGrid);
		   //alert("OK!");
		   var rowNum=PolGrid.mulLineCount; //行数
		   if (rowNum <= 0)
		   {
		   		alert("当前保单无可操作的险种及相关信息！");
		   		return;
		   }
		   for (i=0;i<rowNum;i++)
		   {
					 if (PolGrid.getRowColData(i,9)=="1")
					 {
						 PolGrid.checkBoxSel (i+1);
					}
		   }
    }
    catch(ex) 
    {
       alert(ex);
    }
}

</script>
