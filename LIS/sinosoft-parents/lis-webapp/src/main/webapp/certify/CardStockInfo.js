//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量 
var tResourceName="certify.CardStockInfoInputSql";
//单证状态查询
function certifyQuery()
{
  if(fm.ManageCom.value==null ||　fm.ManageCom.value==''){
    alert("请录入【管理机构】！");
    return false;
  }
  initCardSendOutInfoGrid();
  
  if(fm.ManageCom.value!=null && fm.ManageCom.value!=""){
    fm.hidManageCom.value = "A"+fm.ManageCom.value;
  }
  if(fm.Grade.value!=null && fm.Grade.value!=""){
    fm.hidGrade.value = (parseInt(fm.Grade.value)+1)+"";
  }
  //alert(fm.hidManageCom.value);
  //alert(fm.hidGrade.value);

  var ManageCom = fm.ManageCom.value;
  var strSQL="";//查寻SQL语句字串
  /*strSQL="select a.receivecom, (select name from ldcom where comcode = substr(a.receivecom, 2)), "
		+" (select certifyclass from lmcertifydes where certifycode = a.certifycode) certifyclass, "
		+" a.certifycode, (select certifyname from lmcertifydes where certifycode = a.certifycode), "
		+" a.startno, a.endno, a.sumcount, '' "
		+" from lzcard a "
		+" where a.stateflag in ('2','3') "
		+ getWherePart('a.receivecom', 'hidManageCom', 'like')
		+ getWherePart('length(a.receivecom)', 'hidGrade', '=', '1')	
		+ getWherePart('a.certifycode', 'CertifyCode')
 		+ getWherePart('a.MakeDate', 'MakeDateB','>=')
 		+ getWherePart('a.MakeDate', 'MakeDateE','<=')
	 	+" and exists (select 1	from lmcertifydes b where b.certifycode = a.certifycode"
		+ getWherePart('b.certifyclass', 'CertifyClass')
		+ getWherePart('b.certifyclass2', 'CertifyClass2')
		+") order by certifyclass, a.certifycode, a.receivecom, a.startno" ;*/
    strSQL = wrapSql(tResourceName,"querysqldes1",[document.all('hidManageCom').value,document.all('hidGrade').value
                                   ,document.all('CertifyCode').value,document.all('MakeDateB').value,document.all('MakeDateE').value
                                   ,document.all('CertifyClass').value,document.all('CertifyClass2').value]);
	
   	turnPage.pageLineNum = 10;
   	turnPage.queryModal(strSQL, CardSendOutInfoGrid, 1);
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

