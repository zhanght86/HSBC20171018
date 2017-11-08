//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CertifyDesInfoInputSql";
//单证状态查询
function certifyQuery()
{
  CardSendOutInfoGrid.clearData();//清除表格中所有数据

  var strSQL="";//查寻SQL语句字串
  /*strSQL="select a.certifycode, a.certifyname, a.certifyclass, min(a.makedate), "
  		+"(select max(b.makedate) from lmcertifydestrace b where b.certifycode = a.certifycode and b.state = '1')"
		+"from lmcertifydestrace a where 1=1 "
		+ getWherePart('a.certifycode', 'CertifyCode')		
		+ getWherePart('a.CertifyClass', 'CertifyClass')
		+ getWherePart('a.CertifyClass2', 'CertifyClass2')
 		+ getWherePart('a.makedate', 'MakeDateB','>=')
 		+ getWherePart('a.makedate', 'MakeDateE','<=')
 		+" and exists (select 1	from lmcertifydes where certifycode = a.certifycode "
		+ getWherePart('a.State', 'State')
 		+") group by a.certifycode, a.certifyname, a.certifyclass order by a.certifyclass, a.certifycode";*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('CertifyCode').value,document.all('CertifyClass').value,document.all('CertifyClass2').value
                                   ,document.all('MakeDateB').value,document.all('MakeDateE').value,document.all('State').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid);
   	//alert("查询到的记录行数："+CardInfoGrid.mulLineCount);
   	if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("没有查询到任何单证信息！");
   		return false;
   	}
}

//[打印]按钮函数
function certifyPrint()
{
	//alert("查询到的记录行数："+CardSendOutInfoGrid.mulLineCount);
   	if(CardSendOutInfoGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
   	
   	//alert(turnPage.queryAllRecordCount);
   	
   	if(turnPage.queryAllRecordCount>100000)
   	{
   		alert("一次打印的数据超过100000条,请精确查询条件！");
   		return false;
   	}
   	
	easyQueryPrint(2,'CardSendOutInfoGrid','turnPage');	
}
