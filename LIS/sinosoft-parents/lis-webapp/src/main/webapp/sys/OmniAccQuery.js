//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql = new SqlClass();
//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
            parent.fraMain.rows = "0,0,0,0,*";
  }
    else {
        parent.fraMain.rows = "0,0,0,82,*";
    }

    parent.fraMain.rows = "0,0,0,0,*";
}

function returnParent()
{
    //top.opener.initSelfGrid();
    //top.opener.easyQueryClick();
    top.close();
}


function easyQueryClick()
{
    // 书写SQL语句
    var strSQL = "";
    if( tIsCancelPolFlag == "0"){

       /*  strSQL = "select a.InsuAccNo,a.GrpContNo,a.GrpPolNo,a.PrtNo,a.RiskCode,"
                +" a.AccType,a.InvestType,a.InsuredNo,a.AppntNo,a.Owner,"
                +" (select sum(sumactupaymoney) from ljapayperson p where p.polno = a.polno),"
                +" a.sumpaym,a.LastAccBala,"
                +" a.insuaccbala ,a.InsuAccGetMoney,a.ManageCom,a.Operator,(select nvl(q.fee,'') from lcinsureaccfeetrace q where  q.polno = a.polno and q.feecode like '%01')"
                +" from LCInsureAcc a where "
                +" a.PolNo='" + tPolNo + "'";*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql1");
	mySql.addSubPara(tPolNo); 
    }
    else
    if(tIsCancelPolFlag=="1"){//销户保单查询
      //strSQL = "select LBPrem.PolNo,LBPrem.PayPlanCode,LBPrem.PayPlanType,LBPrem.PayTimes,LBPrem.Mult,LBPrem.Prem,LBPrem.SumPrem,LBPrem.Rate,LBPol.AppntName,LBPol.riskcode,LBPrem.PaytoDate,LBPrem.PayIntv ,,LCPrem.managefeerate from LBPol,LBPrem where LBPrem.PolNo = '" + tPolNo + "' and LBPol.PolNo = LBPrem.PolNo";
    mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql2");
	mySql.addSubPara(tPolNo); 
    }
    else {
      alert("保单类型传输错误!");
      return;
      }

    //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    PolGrid.clearData();
    alert("数据库中没有满足条件的数据！");
    //alert("查询失败！");
    return false;
  }

  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;

  //保存SQL语句
  turnPage.strQuerySql     = strSQL;

  //设置查询起始位置
  turnPage.pageIndex = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}


/*********************************************************************
 *  显示保险账户分类信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsureAccClass()
{

    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    var cPolNo = "";
    if( tSel == 0 || tSel == null )
        alert("请选择一条账户记录，再查看保险账户详细信息");
    else
    {
        cInsuAccNo = PolGrid.getRowColData( tSel - 1, 1 );
        if (cInsuAccNo== null||cInsuAccNo == "")
            return;

        divLCInsureAccClass.style.display="";
        getAccClass(cInsuAccNo);
        divLCInsureAccTrace.style.display="";
        getAccTrace(cInsuAccNo);
         queryRate();
    }
}

function showAccDetail()
{
    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
    	alert("请选择一条账户记录，再查看保险账户详细信息");
    	}     
    else
    {
        var cInsuAccNo = PolGrid.getRowColData( tSel - 1, 1 );
        if (cInsuAccNo== null||cInsuAccNo == "")
            return;
        //divLCInsureAccClass.style.display="";
        
        getAccClass(cInsuAccNo);
        
        divLCInsureAccTrace.style.display="";
        getAccTrace(cInsuAccNo);
                        

        queryRate();
    }
}

function getAccClass(cInsuAccNo)
{
    // 书写SQL语句
    
   // alert('cInsuAccNo'+cInsuAccNo);

   /* strSQL = " select a.GrpContNo,a.GrpPolNo,a.ManageCom,a.PayPlanCode,a.OtherNo,"
                +" a.AccAscription,a.AccType,a.AccComputeFlag,a.BalaDate,a.BalaTime,"
                +" a.sumpay,a.sumpaym,a.LastAccBala,a.LastUnitCount,a.LastUnitPrice,"
                +" a.InsuAccBala,a.UnitCount,a.InsuAccGetMoney,nvl(a.FrozenMoney,0),a.State,"
                +" a.Operator "
                +" from LCInsureAccClass a where "
                +" a.PolNo='" + tPolNo + "' and a.InsuAccNo='"+ cInsuAccNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql3");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
    var brr = easyExecSql(mySql.getString());
    if(!brr)
    {
        PolGrid2.clearData();
        alert("数据库中没有满足条件的数据！");
        return false;
    }
   
    turnPage2.queryModal(mySql.getString(),PolGrid2);
}

function getAccTrace(cInsuAccNo)
{
    // 书写SQL语句

 /*
   var strSQL_0 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
        + " Operator,MODIFYDATE,MODIFYTIME,"
        +		 " decode(moneytype,'BF',0,( select nvl(( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno),( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = substr(a.paydate,0,8)||'01' "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) ) from dual )) ,"
        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"   
        + " From LCInsureAccTrace a where a.moneytype in ('LX', 'BF','GL','JJ') and "
        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"' "; 
//   var strSQL_1 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
//        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
//        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
//        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
//        + " Operator,MODIFYDATE,MODIFYTIME," 
//        + " ,"
//        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"   
//        + " From LCInsureAccTrace a where a.moneytype in ('GL') and "
//        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"' ";
   var strSQL_2 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
        + " Operator,MODIFYDATE,MODIFYTIME,"
        +		 " ( select nvl(( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno),( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = substr(a.paydate,0,8)||'01' "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) ) from dual ) ,"
        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"   
        + " From LCInsureAccTrace a where a.moneytype in ('TB') and "
        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"'  ";
   var strSQL_3 = "Select GRPCONTNO,GRPPOLNO,CONTNO, POLNO,SERIALNO,"
        + " INSUACCNO,RISKCODE,PAYPLANCODE, OTHERNO, OTHERTYPE,"
        + " ACCASCRIPTION, (select trim(code)||'-'||trim(codename) from ldcode where codetype = 'finfeetype' and trim(code)=trim(MONEYTYPE)),MONEY,UNITCOUNT,PAYDATE,"
        + " STATE,MANAGECOM,FEECODE, ACCALTERNO, ACCALTERTYPE,"
        + " Operator,MODIFYDATE,MODIFYTIME,"
        +		 " ( select nvl(( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = a.paydate "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno),( select r.rate "
				+ "		from lminsuaccrate r "
				+ "	 where r.baladate = substr(a.paydate,0,8)||'01' "
				+ "		 and r.riskcode = a.riskcode "
				+ "		 and r.insuaccno = a.insuaccno) ) from dual ) ,"
        + " ( case when (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) < 0 then 0 else (select sum(MONEY) from LCInsureAccTrace r where r.POLNO=a.POLNO and r.PAYDATE>='1900-01-01' and r.PAYDATE <=a.paydate ) end)"        
        + " From LCInsureAccTrace a where a.moneytype in ('TBFY') and "
        + " a.PolNo='" + tPolNo + "'and a.InsuAccNo='"+ cInsuAccNo+"'  and money<0 ";
   var strSQL=strSQL_0 + " union "+ strSQL_2 + " union " +strSQL_3 +" order by MODIFYDATE,MODIFYTIME";
   //alert(strSQL);
   */
   mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql4");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(cInsuAccNo); 
    var brr = easyExecSql(mySql.getString());
    if(!brr)
    {
        PolGrid3.clearData();
        alert("数据库中没有满足条件的数据！");
        return false;
    }
    turnPage3.queryModal(mySql.getString(),PolGrid3);

}

function queryRate()
{
  var i = 0;
  for(i;i<PolGrid3.mulLineCount;i++)
  {
    var tPolNo = PolGrid3.getRowColData(i,4);
    var tInsurAccNo = PolGrid3.getRowColData(i,6); 
    var tRiskCode = PolGrid3.getRowColData(i,7); 
    var tPayDate = PolGrid3.getRowColData(i,15);  
    var tMoneyType = PolGrid3.getRowColData(i,12).substring(0,2); 

    if(tPayDate.substring(8,tPayDate.length)=='01') 
    {
	   // strSQL = " select nvl((select rate from lminsuaccrate where insuaccno = '"+tInsurAccNo+"' and baladate = '"+tPayDate+"' and rateintv='Y' and riskcode= '"+tRiskCode+"'),'0.00') from dual "
	    mySql = new SqlClass();
	mySql.setResourceName("sys.OmniAccQuerySql");
	mySql.setSqlId("OmniAccQuerySql5");
	mySql.addSubPara(tInsurAccNo); 
	mySql.addSubPara(tPayDate); 
	mySql.addSubPara(tRiskCode);  
	    var brr = easyExecSql(mySql.getString(), 1, 0,"","",1);//注意此处第6个参数应设为1，不使用翻页功能，否则可能会把全局变量turnPage覆盖
	    if(brr)
	    {
	    	  
	         PolGrid3.setRowColData(i, 24, brr[0][0]);
	    }
	    else
	    {
	    		PolGrid3.setRowColData(i, 24, '0.025');
	    }
	 }
	//保费收入利率统一置为空，但是翻页后此特殊处理失效
	if(tMoneyType=='BF')
	{
	   PolGrid3.setRowColData(i, 24, '');
	}
  }
}
//提交，保存按钮对应操作
function printNotice()
{
  //var i = 0;
  var showStr="正在打印，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

    
	  fm.fmtransact.value = "PRINT";
		fm.target = "f1print";
		document.getElementById("fm").submit();
		showInfo.close();
}
