//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//�������ƣ�
//�����ܣ����������������
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
	var iHeight=350;     //�������ڵĸ߶�; 
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
	var iHeight=350;     //�������ڵĸ߶�; 
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
		var selno = LOBonusMainGrid.getSelNo()-1;
	  if (selno<0)
	  {
	      alert("��ѡ����Ӧ�ļ�¼��");
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
     fm.action="./BonusRateSave.jsp";
     document.getElementById("fm").submit(); //�ύ	
	
}           

//queryClick�¼�
function queryClick()
{
    var sqlid830155634="DSHomeContSql830155634";
var mySql830155634=new SqlClass();
mySql830155634.setResourceName("get.BonusRateInputSql");//ָ��ʹ�õ�properties�ļ���
mySql830155634.setSqlId(sqlid830155634);//ָ��ʹ�õ�Sql��id
mySql830155634.addSubPara();//ָ������Ĳ���
var tSql_0=mySql830155634.getString();
    
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
	
