//定义sql配置文件
var tResourceNameDic = "productdef.DictionaryUtilitiesSql";
function GetDicOfTableByKeys(tableName, arr)
{
	var len = arr.length; 
	var wherePart = ""; 
	for(var i = 0; i < len; i++)
	{
		wherePart += arr[i][0] + " = '" + arr[i][1] + "',";
	}
	
	wherePart = wherePart.substr(0,wherePart.length - 1);

	var mySql=new SqlClass();
  var sqlid = "DictionaryUtilitiesSql1";
  mySql.setResourceName(tResourceNameDic); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(tableName);//指定传入的参数
  mySql.addSubPara(wherePart);//指定传入的参数
	var selectvalues = mySql.getString();

	var mySql2=new SqlClass();
  var sqlid2 = "DictionaryUtilitiesSql2";
  mySql2.setResourceName(tResourceNameDic); //指定使用的properties文件名
  mySql2.setSqlId(sqlid2);//指定使用的Sql的id
  mySql2.addSubPara(tableName);//指定传入的参数
	var selectnames = mySql2.getString();

	var arrValues = easyExecSql(selectvalues);
	var arrNames = easyExecSql(selectnames);
	 
	var dic = new ActiveXObject("Scripting.Dictionary"); 
	
	var len = arrNames.length;

	for(var i = 0; i < len; i++)
	{
		dic.add(arrNames[i][0],arrValues[0][i]);
	}
	
	return dic;
}
