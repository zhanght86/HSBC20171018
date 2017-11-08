//该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass(); //使用翻页功能，必须建立为全局变量
var tResourceName="certify.CardPrinteryFeeStatSql";
//单证状态查询
function certifyQuery()
{
  CardPrintInfoGrid.clearData();//清除表格中所有数据

  /*var strSQL = "select a.prtno, a.printery, a.certifycode, "
  		+" (select certifyname from lmcertifydes where certifycode = a.certifycode),"
		+"  to_char(a.certifyprice, 'FM99990.0099'), to_char(a.sumcount), a.certifyprice * a.sumcount,  to_char(a.printdate,'YYYY-MM-DD') printdate"
		+" from lzcardprint a where 1 = 1 and a.state in ('1', '2')";
		
  if(fm.Printery.value!=null && fm.Printery.value!=""){		
    strSQL+=" and a.Printery like '%" + fm.Printery.value + "%'";
  }
		//+ getWherePart('a.Printery', 'Printery')
		
  strSQL+= getWherePart('a.certifycode', 'CertifyCode')		
		+ getWherePart('a.PlanType', 'PlanType')		
 		+ getWherePart('a.PrintDate', 'PrintDateB','>=')
 		+ getWherePart('a.PrintDate', 'PrintDateE','<=') 		
 		+" union all "
 		+"select '金额合计', '' printery, '' certifycode,  '', '', '', sum(b.certifyprice * b.sumcount), '' printdate"
		+" from lzcardprint b where 1 = 1 and b.state in ('1', '2')";
		
  if(fm.Printery.value!=null && fm.Printery.value!=""){			
    strSQL+=" and b.Printery like '%" + fm.Printery.value + "%'";
  }
  		
		//+ getWherePart('b.Printery', 'Printery')
  strSQL+= getWherePart('b.certifycode', 'CertifyCode')		
		+ getWherePart('b.PlanType', 'PlanType')		
 		+ getWherePart('b.PrintDate', 'PrintDateB','>=')
 		+ getWherePart('b.PrintDate', 'PrintDateE','<=')
 		+" order by printdate,printery,certifycode";*/
    var strSQL = wrapSql(tResourceName,"querysqldes1",[fm.Printery.value,fm.CertifyCode.value,fm.PlanType.value,fm.PrintDateB.value,fm.PrintDateE.value
                                       ,fm.Printery.value,fm.CertifyCode.value,fm.PlanType.value,fm.PrintDateB.value,fm.PrintDateE.value]);
	
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
	//alert("查询到的记录行数："+CardSendOutInfoGrid.mulLineCount);
   	if(CardPrintInfoGrid.mulLineCount==0)
   	{
   		alert("没有可以打印的数据！");
   		return false;
   	}
	easyQueryPrint(2,'CardPrintInfoGrid','turnPage');	
}

