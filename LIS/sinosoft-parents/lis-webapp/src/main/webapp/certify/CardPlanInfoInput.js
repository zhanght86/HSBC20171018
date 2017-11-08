//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var tResourceName="certify.CardPlanInfoInputSql";
//单证状态查询
function certifyQuery()
{
  if (fm.ManageCom.value==null || fm.ManageCom.value=='')
  {
    alert("请输入申请机构！");
    return false;
  }

  initCardPlanQueryGrid();
  initCardPlanDetailGrid();
  /*var strSQL = "select a.appcom, a.plantype,max(a.appoperator),max(a.makedate) from lzcardplan a"
 		+" where 1=1 "
 		+ getWherePart('a.appcom', 'ManageCom', 'like')
 		+ getWherePart('length(a.appcom)', 'Grade')
 		//+ getWherePart('a.PlanID', 'PlanID')
 		+ getWherePart('a.PlanType', 'PlanType')
 		+ getWherePart('a.PlanState', 'PlanState')
 		+ getWherePart('a.MakeDate', 'MakeDateB','>=')
 		+ getWherePart('a.MakeDate', 'MakeDateE','<=')
 		+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 		+ getWherePart('certifyclass', 'CertifyClass') 
 		+ getWherePart('CertifyClass2', 'CertifyClass2') + ")"  
 		+" group by a.appcom, a.plantype order by a.plantype, max(a.makedate), a.appcom";*/
    var strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ManageCom').value,document.all('Grade').value,document.all('PlanType').value
                                       ,document.all('PlanState').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                       ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardPlanQueryGrid);
   	//alert("查询到的记录行数："+CardPlanQueryGrid.mulLineCount);
   	if(CardPlanQueryGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}

function showDetail(parm1, parm2)
{
  var tManageCom = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 1);
  var tPlanType = CardPlanQueryGrid.getRowColData(CardPlanQueryGrid.getSelNo()-1, 2);

  /*var strSql = "select a.planid, a.appcom, a.plantype,a.certifycode,"
      	+" (select certifyname from lmcertifydes where certifycode=a.certifycode), (select certifyprice from lmcertifydes where certifycode=a.certifycode) price,"
      	+" a.appcount, a.concount, a.retcount, assigncount, a.retcount - assigncount,(select certifyprice*decode( (a.retcount - assigncount),0,decode( (a.retcount),0,decode( (a.concount),0,a.appcount,a.concount),a.retcount),(a.retcount - assigncount))  from lmcertifydes  where certifycode = a.certifycode), "
      	+" (case a.planstate when 'A' then '申请状态'when 'C' then '提交状态'when 'S' then '汇总提交状态'when 'R' then '批复状态' end), "
      	+" a.MakeDate, a.UpdateDate "
    	+" from lzcardplan a where 1=1 and a.appcom = '" + tManageCom
    	+"' and a.plantype ='" + tPlanType + "'"   
    	+ getWherePart('a.appcom', 'ManageCom', 'like')
    	+ getWherePart('length(a.appcom)', 'Grade')
 		//+ getWherePart('a.PlanID', 'PlanID')
 		+ getWherePart('a.PlanType', 'PlanType')
 		+ getWherePart('a.PlanState', 'PlanState') 
 		+ getWherePart('a.MakeDate', 'MakeDateB','>=')
 		+ getWherePart('a.MakeDate', 'MakeDateE','<=') 	
 		+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 		+ getWherePart('certifyclass', 'CertifyClass') 
 		+ getWherePart('CertifyClass2', 'CertifyClass2') + ")"  
    	+" order by a.certifycode,a.planstate";*/
  var strSql = wrapSql(tResourceName,"querysqldes2",[tManageCom,tPlanType,document.all('ManageCom').value,document.all('Grade').value,document.all('PlanType').value
                                       ,document.all('PlanState').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                       ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
  //prompt("strSql",strSql);
  turnPage2.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage2.strQueryResult) {
 	 alert("未查询到单证计划明细信息！");
     return false;
    }else{
  	  turnPage2.pageDivName = "CardPlanDetailGrid";
  	  turnPage2.queryModal(strSql, CardPlanDetailGrid);
	} 
}

//[打印]按钮函数
function certifyPrint()
{
	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(CardPlanDetailGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
   	
   	//alert(turnPage.queryAllRecordCount);
   	
   	if(turnPage2.queryAllRecordCount>100000)
   	{
   		alert("一次打印的数据超过100000条,请精确查询条件！");
   		return false;
   	}
   	
	easyQueryPrint(2,'CardPlanDetailGrid','turnPage2');	
}

