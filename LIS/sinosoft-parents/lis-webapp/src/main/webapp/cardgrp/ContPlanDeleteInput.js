var showInfo;
var approvefalg;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = 0;
var mAction = "";

var turnPage = new turnPageClass();
this.window.onfocus=myonfocus;


//��ʼ�����ֱ���
function getaddresscodedata()
{
	//alert(grpcontno);
	//��ʼ������
	var sql="select contplancode,contplanname From lccontplan where grpcontno='"+grpcontno+"'";
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    
    fm.all("ContPlan").CodeData=tCodeData;
	
}



function deleteall()
{
	if(fm.ContPlan.value=="")
	{
		alert("��ѡ��Ҫɾ���ı��ϼƻ���");
		return;
	}
	
	if (confirm("����ɾ��Ͷ���ñ��ϼƻ��µ����б�������Ϣ,ȷ����?"))
	      {
	      	lockScreen('lkscreen');  
  fm.GrpContNo.value=grpcontno;
  
	fm.submit();
}
	
}

function afterSubmit( FlagStr, content )
{
   
	unlockScreen('lkscreen');  
		document.all("info").innerText=content;
	
	    
}