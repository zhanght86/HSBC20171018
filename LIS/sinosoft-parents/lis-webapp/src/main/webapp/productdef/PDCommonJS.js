//����sql�����ļ�
var tResourceNamePDC = "productdef.PDCommonJSSql";
function checkRules(type)
{
	var mySql=new SqlClass();
  var sqlid = "PDCommonJSSql1";
  mySql.setResourceName(tResourceNamePDC); //ָ��ʹ�õ�properties�ļ���
  mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
  mySql.addSubPara(type);//ָ������Ĳ���
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
			alStr += ""+"\r\n\r\n��ʾ����ʹ��Ctrl+C������ʾ��Ϣ��Ctrl+Vճ�������±�"+"";
		}
		
		return alStr;
	}
	catch(ex)
	{
		return(""+"У��ʧ��"+"" + ex); 
	}
}
