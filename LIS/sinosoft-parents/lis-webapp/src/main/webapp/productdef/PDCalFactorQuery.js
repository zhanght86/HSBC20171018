//�������ƣ�PDCalFactorQuery.js
//�����ܣ�����Ҫ����ʾ��ѯ����
//�������ڣ�2009-3-18
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	
//����sql�����ļ�
var tResourceName = "productdef.PDCalFactorQueryInputSql";
function submitForm()
{
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
function query()
{
	 var mySql=new SqlClass();
	 var sqlid = "PDCalFactorQueryInputSql1";
	 mySql.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(fm.all("FactorType").value);//ָ������Ĳ���
	 mySql.addSubPara(fm.all("Kind").value);//ָ������Ĳ���
	 mySql.addSubPara(fm.all("Module").value);//ָ������Ĳ���
	 mySql.addSubPara(fm.all("FactorCode").value);//ָ������Ĳ���
	 mySql.addSubPara(fm.all("FactorValType").value);//ָ������Ĳ���
	 mySql.addSubPara(fm.all("FactorDesc").value);//ָ������Ĳ���
   	
   	if(fm.selectTable.value == null || fm.selectTable.value != "PD_CalFactor_Lib")
   	{
   		sqlid = "PDCalFactorQueryInputSql2";
   		mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
   		mySql.addSubPara(fm.all("FactorCode").value);//ָ������Ĳ���
	 		mySql.addSubPara(fm.all("FactorDesc").value);//ָ������Ĳ���
   	  if(fm.all("FactorType").value == "1")
   		{
   			mySql.addSubPara("0");//ָ������Ĳ���
   		}else{
   			mySql.addSubPara("1");//ָ������Ĳ���
   		}
   	}   	
   	var strSQL = mySql.getString();
   	   	
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
var dicSelectedFactors = new ActiveXObject("Scripting.Dictionary");

function checkedEvent()
{	
	var columnIndexs = new Array();
	columnIndexs[0] = 1;
	columnIndexs[1] = 2;
	columnIndexs[2] = 3;
	columnIndexs[3] = 4;
	columnIndexs[4] = 5;
	columnIndexs[5] = 6;
	columnIndexs[6] = 7;
	columnIndexs[7] = 8;
	columnIndexs[8] = 9;	
		
	addCheckedResult(Mulline9Grid, Mulline9GridTurnPage, columnIndexs);
}

// obj:MulLineName,objTurnPage:MulLineName+TurnPage,columnIndexs:��Ҫ�е��������
function addCheckedResult(obj, objTurnPage, columnIndexs)
{
	var arrColValues = new Array();
	
	var pageIndex = objTurnPage.pageIndex;
	
	for(var i = 0; i < obj.mulLineCount; i++)
	{
		if(obj.getChkNo(i))	
		{
			if(!dicSelectedFactors.Exists(pageIndex + "_" + i))
			{
				for(var j = 0; j < columnIndexs.length; j++)
				{
					arrColValues[j] = obj.getRowColData(i, columnIndexs[j]);
				}
				
				dicSelectedFactors.Add(pageIndex + "_" + i, arrColValues);
			}
		}
		else
		{
			if(dicSelectedFactors.Exists(pageIndex + "_" + i))
			{
				dicSelectedFactors.Remove(pageIndex + "_" + i);
			}
		}
	}
}

function returnParent( )
{
	try
	  {
	  	//modify by nicole ���dicSelectedFactors��û��add�κ�Ԫ�أ���ᱨ��
	  	var factorArr = dicSelectedFactors.Keys().toArray();
	  	if( factorArr.length > 0 )
	  	{
		  	top.opener.afterQueryFactor(dicSelectedFactors);	  		
	  	}
	  	top.close();
		top.opener.focus();
	  }
	  catch(ex)
	  {
	  	myAlert(""+"�ر�¼��ҳ�����˳�ϵͳ�������µ�¼"+""+ex);
	  	window.open("PDCalFactorInput.jsp");
	  	top.close();
	  }
}
