//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CardPrintInfoInputSql";
//单证状态查询
function certifyQuery()
{
  CardPrintInfoGrid.clearData();//清除表格中所有数据
  if (fm.ManageCom.value==null || fm.ManageCom.value=='')
  {
    alert("请输入印刷机构！");
    return false;
  }
  var strSQL="";//查寻SQL语句字串
  /*strSQL="select a.prtno,a.plantype,a.certifycode,"
	+" (select certifyname from lmcertifydes where certifycode=a.certifycode),"
	+" (select unit from lmcertifydes where certifycode=a.certifycode),"
	+" a.certifyprice, a.startno, a.endno, a.sumcount, a.printery, a.maxdate, a.printdate,"
	+" decode(a.State, '1', '已印刷', '2', '已入库') State"
	+" from lzcardprint a where 1=1 and a.state in ('1','2') " 
	+ getWherePart('a.ManageCom', 'ManageCom', 'like')
	+ getWherePart('a.PlanType', 'PlanType')
	+ getWherePart('a.State', 'State')
	+ getWherePart('a.certifycode', 'CertifyCode') 		
 	+ getWherePart('a.printdate', 'MakeDateB','>=')
 	+ getWherePart('a.printdate', 'MakeDateE','<=')
 	+ getWherePart('a.PrintMan', 'Operator') 
 	+" and exists (select 1 from lmcertifydes where certifycode = a.certifycode "
 	+ getWherePart('certifyclass', 'CertifyClass') 
 	+ getWherePart('CertifyClass2', 'CertifyClass2') + ")"  
 	+" order by a.printdate, a.plantype, a.certifycode";*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('ManageCom').value,document.all('PlanType').value,document.all('State').value
                                   ,document.all('CertifyCode').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                   ,document.all('Operator').value,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardPrintInfoGrid);
   	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(CardPrintInfoGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}

//[打印]按钮函数
function certifyPrint()
{
	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(CardInfoGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardInfoGrid','turnPage');	
}
