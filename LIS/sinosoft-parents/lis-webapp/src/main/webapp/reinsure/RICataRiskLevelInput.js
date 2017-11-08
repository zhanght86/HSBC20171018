var showInfo;

var turnPage = new turnPageClass(); 
window.onfocus=myonfocus;

function myonfocus()
{
	if(showInfo!=null)
	{
		try
		{
			showInfo.focus();  
		}
		catch(ex)
		{
			showInfo=null;
		}
	}
}

function verifyInput1()
{
	if(fm.CalYear.value==""||fm.CalYear.value==null){
		myAlert(""+"年度不能为空"+"");
		return false;
	}
	if(!isNumeric(fm.CalYear.value)){
		myAlert(""+"年度格式错误"+"");
		return false;
	}
	return true;
}


function riCataRiskImp()
{

}

//风险级别分类统计
function catastropheQuery()
{

	if(!verifyInput1())
	{
		return false;
	}
	
	var tCalYear = fm.CalYear.value ;
	
	var strSQL="select Codename,lives,riskamnt,riskamnt-cessionamnt from("
		+" select d.Codename,count(f.Insuredno) lives, "
		+" sum(nvl((select sum(decode(c.Eventtype,'01',c.Riskamnt,'02',c.Riskamnt,'03',c.Riskamnt-c.Preriskamnt,0)) from Ripolrecordbake c where c.Accumulatedefno = f.Accumulatedefno and c.Insuredno = f.Insuredno ),0)) riskamnt,"
		+" sum(nvl((select sum(decode(c.Eventtype,'01',c.Cessionamount,'03',c.Amountchang,0)) from Rirecordtrace c where c.Accumulatedefno = f.Accumulatedefno and c.otherno = f.Insuredno and c.Eventtype <> '04' ),0)) cessionamnt "
		+" from ldcode d left join( select a.Insuredno,a.Accumulatedefno,a.Accamnt from Ripolrecordbake a where a.Accumulatedefno in('L000000001','L000000002')"
		+" and getdate in(select max(getdate) from Ripolrecordbake b where b.Accumulatedefno = a.Accumulatedefno and b.Insuredno = a.Insuredno )" 
		+" order by Accumulatedefno,a.Insuredno,getdate,contno) f on  f.Accamnt between d.Codealias and d.Othersign"
		+" where d.Codetype = 'ricatastrophe' group by d.code ,d.Codename order by d.code)"
		+" union all (select '"+"汇总"+"' Codename ,0 lives,0 riskamnt,0 cessionamnt from dual)";
		
	turnPage.queryModal(strSQL,CatastropheGrid,'','',20);
	var lives = 0 ;
	var riskamnt=0.00 ;
	var cessionamnt = 0.00 ;
	var lineNum=CatastropheGrid.mulLineCount;
	for(var k=0 ;k< lineNum ; k++)
	{
		lives = lives + parseInt(CatastropheGrid.getRowColData(k,2)) ;
		riskamnt = riskamnt + parseFloat(CatastropheGrid.getRowColData(k,3)) ;
		cessionamnt = cessionamnt + parseFloat(CatastropheGrid.getRowColData(k,4)) ;	
		
	}

	CatastropheGrid.setRowColData(13,1,''+"汇总"+'');
	CatastropheGrid.setRowColData(13,2,''+lives);
	CatastropheGrid.setRowColData(13,3,getValue(''+riskamnt));
	CatastropheGrid.setRowColData(13,4,getValue(''+cessionamnt));	
}

function getValue( n )
{
	var value = n.split('.');
	if(value.length==1)
		return value[0];
	if(value[1].length<3)
		return value.join('.');
	value[1]=value[1].replace(/(\d{3}).*/,"$1");
	value=Math.round(parseInt(value.join(''),10)/10)/100;
	return ''+value;
}

function isNumeric(strValue)
{
  var NUM="0123456789";
  var i;
  if(strValue==null ||strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false
  }
  if(strValue.indexOf(".")!=strValue.lastIndexOf(".")) return false;
  return true;
}

function afterCodeSelect(cCodeName, Field )
{

}

