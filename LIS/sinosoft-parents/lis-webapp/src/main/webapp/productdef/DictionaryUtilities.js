//����sql�����ļ�
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
  mySql.setResourceName(tResourceNameDic); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(tableName);//ָ������Ĳ���
  mySql.addSubPara(wherePart);//ָ������Ĳ���
	var selectvalues = mySql.getString();

	var mySql2=new SqlClass();
  var sqlid2 = "DictionaryUtilitiesSql2";
  mySql2.setResourceName(tResourceNameDic); //ָ��ʹ�õ�properties�ļ���
  mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
  mySql2.addSubPara(tableName);//ָ������Ĳ���
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
