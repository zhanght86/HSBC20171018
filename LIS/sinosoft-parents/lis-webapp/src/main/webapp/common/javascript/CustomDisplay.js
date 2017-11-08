//定义sql配置文件
var tResourceNameCus = "common.CustomDisplaySql";

// pageNo:页面的编号，来唯一确定该页面
// eleType:要校验的元素的类型，可为空
// paras:sql语句中参数名称和值的二维数组
function customDisplay(pageNo, eleType, paras)
{
	var mySql=new SqlClass();
  var sqlid = "CustomDisplaySql1";
  mySql.setResourceName(tResourceNameCus); //指定使用的properties文件名
  mySql.setSqlId(sqlid);//指定使用的Sql的id
  mySql.addSubPara(pageNo);//指定传入的参数
	var sql = mySql.getString();
	var checkRules = easyExecSql(sql,1,1,1);
	
	if(checkRules != null)
	{
		var forms = document.forms;
		
		for(var formIndex = 0; formIndex < forms.length; formIndex++)
		{
			checkOneForm(forms[formIndex], eleType, paras, checkRules);
		}
	}
}

function checkOneForm(form, eleType, paras, checkRules)
{
	for (var eleIndex = 0; eleIndex < form.length; eleIndex++)
  	{
		var eleId = form.elements[eleIndex].id;
  		
  		if(eleType != "")
  		{
  			if(form.elements[eleIndex].type == eleType)
	  		{
		  		checkOneElement(eleId, checkRules, paras);
	  		}	
  		}
  		else
  		{
	  		checkOneElement(eleId, checkRules, paras);
  		}
  	}
}

function checkOneElement(eleId, checkRules, paras)
{
	for(var j = 0; j < checkRules.length; j++)
	{
		if(eleId == checkRules[j][0])
		{
			var abledRule = checkRules[j][1];
			if(abledRule != "")
			{
				var result = getResult(abledRule, paras);
				checkRule(result, eleId, "abled");
			}
			
			var displayRule = checkRules[j][2];
			if(displayRule != "")
			{
				var result = getResult(displayRule, paras);
				checkRule(result, eleId, "display");
			}
			
			break;
		}
	}
}

function getResult(sql, paras)
{
	for(var i = 0; i < paras.length; i++)
	{
		sql = sql.replace("@" + paras[i][0] + "@", paras[i][1]);
	}
	
	var result = easyExecSql(sql,1,1,1);
	
	return result;
}

function checkRule(result, eleId, type)
{
	if(result == null || result[0][0] != "1")
	{
		if(type == "abled")
		{
			document.getElementById(eleId).disabled = true;
		}
		else if(type == "display")
		{
			document.getElementById(eleId).style.display = "none";
		}
	}
	else
	{
		if(type == "abled")
		{
			document.getElementById(eleId).disabled = false;
		}
		else if(type == "display")
		{
			document.getElementById(eleId).style.display = "";
		}
	}  					
}