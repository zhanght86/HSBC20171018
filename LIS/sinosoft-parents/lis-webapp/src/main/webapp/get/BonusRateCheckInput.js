// ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//�������ƣ�
//�����ܣ��ֺ���ϵ��У�����
//�������ڣ�2008-11-09 17:55:57
//������  ������ͥ
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
    showInfo.close();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo1 = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo1.focus();
  	showInfo.close();
  }
  queryClick();
}
       

//updateClick�¼�
function updateClick()
{  	
   	var tFiscalYear=fm.FiscalYear.value; 
   	
   	var sqlid830155948="DSHomeContSql830155948";
var mySql830155948=new SqlClass();
mySql830155948.setResourceName("get.BonusRateCheckInputSql");//ָ��ʹ�õ�properties�ļ���
mySql830155948.setSqlId(sqlid830155948);//ָ��ʹ�õ�Sql��id
mySql830155948.addSubPara(fm.FiscalYear.value);//ָ������Ĳ���
var tSQL_0=mySql830155948.getString();
   	 	
//   	var tSQL_0="select FiscalYear from LOBonusMain  where FiscalYear='"+fm.FiscalYear.value+"'";
   	var tResult_0=easyExecSql(tSQL_0,1,0,1);
   	if(tFiscalYear!=tResult_0)
   	{
   		 alert("�����������ݲ����ڣ�����������");
   		 return;   		
   		} 		
     var showStr="�����޸����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
     var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     fm.transact.value="update"; 
     fm.action="./BonusRateCheckSave.jsp";
     document.getElementById("fm").submit(); //�ύ	
	
}           

//queryClick�¼�
function queryClick()
{
    var sqlid830160055="DSHomeContSql830160055";
var mySql830160055=new SqlClass();
mySql830160055.setResourceName("get.BonusRateCheckInputSql");//ָ��ʹ�õ�properties�ļ���
mySql830160055.setSqlId(sqlid830160055);//ָ��ʹ�õ�Sql��id
mySql830160055.addSubPara();//ָ������Ĳ���
var tSql_0=mySql830160055.getString();
    
//    var tSql_0="select FiscalYear,groupid, nvl(DistributeValue,0),nvl(DistributeRate,0),nvl(BonusCoefSum,0),decode(state,'0','δ����','1','�Ѹ���')  from  LOBonusMain order by FiscalYear";

    	 turnPage.queryModal(tSql_0, LOBonusMainGrid);             
}           

         

function displayInfo()
{

	 var tLength=LOBonusMainGrid.mulLineCount;
	 var tCow=0;

	  tCow=LOBonusMainGrid.getSelNo()	
	  tCow=tCow-1; 
    fm.FiscalYear.value =LOBonusMainGrid.getRowColData(tCow,1);
    fm.DistributeValue.value=LOBonusMainGrid.getRowColData(tCow,3);
    fm.DistributeRate.value =LOBonusMainGrid.getRowColData(tCow,4);
    fm.BonusCoefSum.value = LOBonusMainGrid.getRowColData(tCow,5);		
}
	

