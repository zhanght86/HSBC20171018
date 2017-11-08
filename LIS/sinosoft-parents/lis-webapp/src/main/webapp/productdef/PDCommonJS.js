//定义sql配置文件
var tResourceNamePDC = "productdef.PDCommonJSSql";
function checkRules(type)
{
	var mySql=new SqlClass();
  var sqlid = "PDCommonJSSql1";
  mySql.setResourceName(tResourceNamePDC); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(type);//指定传入的参数
	var sql = mySql.getString();

	try
	{
		var algos = easyExecSql(sql,1,1,1);
		
		if(algos == null)
			return "";
		
		var alStr = "";
		
		for(var i = 0; i < algos.length; i++)
		{
			var algoSql = algos[i][0];
			algoSql = algoSql.replace("@RiskCode@", fm.all("RiskCode").value);
			var result = easyExecSql(algoSql);
			
			if(result != null && result[0][0] == "1")
			{
				alStr += algos[i][1] + "\r\n";
			}
		}
		if(alStr != "")
		{
			alStr += ""+"\r\n\r\n提示：可使用Ctrl+C复制提示信息，Ctrl+V粘贴到记事本"+"";
		}
		
		return alStr;
	}
	catch(ex)
	{
		return(""+"校验失败"+"" + ex); 
	}
}
