 //�������ƣ�SelAssignDuty.js
//�����ܣ�
//�������ڣ�20048-09-26
//������  :  liuqh
//���¼�¼��  ������    ��������     ����ԭ��/����

//var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var k = 0;
 var turnPage = new turnPageClass();  
 var turnPage1 = new turnPageClass();  
/*********************************************************************
 *  �ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function AssignSubmit()
{
    if(checkData()==false)
     return false;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "INSERT";
    document.all('Action').value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}


function AssignUpdate()
{
    if(!selsome())
     return false;
    if(checkData()==false)
     return false;
   	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "UPDATE";
    document.all('Action').value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}
 
function StartSel()
{
    if(!selsome())
     return false;
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "STARTSEL";
    document.all('Action').value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}
function StartAll()
{

   if(!allstart())
       return false;
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "STARTALL";
    document.all('Action').value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}
function StopSel()
{
   if(!selsome())
     return false;
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "TERMINATESEL";
    document.all('Action').value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}
function StopAll()
{
    if(!allstop())
      return false
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "TERMINATEALL";
	document.all('Action').value = mAction;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	document.getElementById("fm").submit(); //�ύ
}

function checkData()
{


  if(verifyInput()==false)
    return false;
  //������п���
  AssignGrid.delBlankLine();
  var m =AssignGrid.mulLineCount;
  for(i=1;i<m;i++)
  {
     for (j=0;j<i;j++)
     {
         if(AssignGrid.getRowColData(j,1)==AssignGrid.getRowColData(i,1))
         {
           alert("����Ա"+AssignGrid.getRowColData(j,1)+"���ڶ�����¼���뽫��ͬ�Ĳ���Ա�Ĺ�����¼��һ���У�");
           return false;
         }
     }
  }
 //�ж����ڸ�ʽ
    var StartTime = document.all("StartTime").value;
    var EndTime = document.all("EndTime").value;
    var t = new Date(StartTime.replace(/\-/g,"/"));
    var w = new Date(EndTime.replace(/\-/g,"/"));
    if(!(StartTime == ""||StartTime == null))
    {
      if(isNaN(t))
      {
         alert("��ʼ���ڸ�ʽ����");
         return false;
      }
      else
      {
         if(!(EndTime == ""||EndTime == null))
          {
            if(isNaN(w))
             {
                alert("��ֹ���ڸ�ʽ����");
                return false;
             }
           }
      } 
    }
    return true;
}

function afterSubmit(FlagStr,content)
{
   	            
	  showInfo.close();  
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

      InitAssign();

}

function InitAssign()
{
   //�쳣��
   var sqlid831163543="DSHomeContSql831163543";
var mySql831163543=new SqlClass();
mySql831163543.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163543.setSqlId(sqlid831163543);//ָ��ʹ�õ�Sql��id
mySql831163543.addSubPara();//ָ������Ĳ���
var sqlq=mySql831163543.getString();
   
//  var sqlq = "select count(*) from lwmission where activityid='0000001090' and defaultoperator is null ";
  document.all('QuestModify').value = easyExecSql(sqlq);
  //�����
  var sqlid831163621="DSHomeContSql831163621";
var mySql831163621=new SqlClass();
mySql831163621.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163621.setSqlId(sqlid831163621);//ָ��ʹ�õ�Sql��id
mySql831163621.addSubPara();//ָ������Ĳ���
var sqla=mySql831163621.getString();
  
//  var sqla = "select count(*) from lwmission where activityid='0000001002' and defaultoperator is null ";
  document.all('ApproveModify').value = easyExecSql(sqla);
  //�˹��ϲ�
  var sqlid831163650="DSHomeContSql831163650";
var mySql831163650=new SqlClass();
mySql831163650.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163650.setSqlId(sqlid831163650);//ָ��ʹ�õ�Sql��id
mySql831163650.addSubPara();//ָ������Ĳ���
var sqlc=mySql831163650.getString();
  
//  var sqlc = "select count(*) from lwmission where activityid='0000001404' and defaultoperator is null ";
  document.all('CustomerMerge').value = easyExecSql(sqlc);
  
  //ʱ���ʼ��
  var sqlid831163723="DSHomeContSql831163723";
var mySql831163723=new SqlClass();
mySql831163723.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163723.setSqlId(sqlid831163723);//ָ��ʹ�õ�Sql��id
mySql831163723.addSubPara();//ָ������Ĳ���
var sqltime=mySql831163723.getString();
  
//  var sqltime = "select taskstarttime,taskendtime from ldassignplan where rownum='1'and activityid in ('0000001090','0000001002','0000001404')";
  var arrResult = easyExecSql(sqltime);
  if(arrResult !=null)
  {
     document.all('StartTime').value = arrResult[0][0];
     document.all('EndTime').value = arrResult[0][1];
  }
  //�ж��ܹ�����
  var sqlid831163752="DSHomeContSql831163752";
var mySql831163752=new SqlClass();
mySql831163752.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163752.setSqlId(sqlid831163752);//ָ��ʹ�õ�Sql��id
mySql831163752.addSubPara();//ָ������Ĳ���
var sqlPlan=mySql831163752.getString();
  
//  var sqlPlan = "select distinct 1 from ldassignplan where activityid in ('0000001090','0000001002','0000001404')";
  var Exists = easyExecSql(sqlPlan);
  if(Exists=="1"){
    //�й����쳣����������޸Ļ��˹��ϲ��ķ���ƻ� ����ֻ��ͨ���޸����ύ����ƻ�
    fm.addNew.disabled = true;
  }else{
    fm.addNew.disabled = false;
  }
  
  
  //mulLine��ʼ��
//  var sql = "  select distinct(a.assignno) ,'����Ա����' ,"
//           +"  nvl((select b.planamount from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),0),"
//           +"  nvl((select c.planamount from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002' ),0),"
//           +"  nvl((select d.planamount from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' ),0),"
//           +"  a.state "
//           +"  from ldassignplan a order by a.assignno ";

	var sqlid831163834="DSHomeContSql831163834";
var mySql831163834=new SqlClass();
mySql831163834.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163834.setSqlId(sqlid831163834);//ָ��ʹ�õ�Sql��id
mySql831163834.addSubPara();//ָ������Ĳ���
var sql=mySql831163834.getString();

//  var sql = "  select distinct(a.assignno) ,(select codename from ldcode where codetype ='appalluser' and code=a.assignno) ,"
//           +"  (select decode(b.planamount,null,'',b.planamount) from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),"
//           +"  (select b.state from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),"
//           +"  (select decode(c.planamount,null,'',c.planamount) from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002'),"
//           +"  (select c.state from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002' ),"
//           +"  (select decode(d.planamount,null,'',d.planamount) from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' ),"
//           +"  (select d.state from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' )"
//           +"  from ldassignplan a "
//           +"  where activityid in ('0000001090','0000001404','0000001002') order by a.assignno ";
  turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
  if(turnPage.strQueryResult){
  turnPage.queryModal(sql, AssignGrid);
  }
  
}

function  allstart()
{
   if(!checkplan())
    return false;
    
    var sqlid831163933="DSHomeContSql831163933";
var mySql831163933=new SqlClass();
mySql831163933.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163933.setSqlId(sqlid831163933);//ָ��ʹ�õ�Sql��id
mySql831163933.addSubPara();//ָ������Ĳ���
var sql=mySql831163933.getString();

    
//   var sql = "select count(*) from ldassignplan where state ='1'";
   var tCount = easyExecSql(sql);
   if(tCount<=0)
    {
       alert("���з���ƻ����Ѿ�������û����ֹ�ļƻ���");
       return false;
    }
    return true;
}

function selsome()
{
  var checkedRowNum = 0 ;
  var rowNum = AssignGrid.mulLineCount ; 
  var count = -1;
  for ( var i = 0 ; i< rowNum ; i++ )
  {
  	if(AssignGrid.getChkNo(i)) {
  	   checkedRowNum = checkedRowNum + 1;
      }
  }
  if(checkedRowNum<1)
    {
       alert("������Ҫ�޸ĵļ�¼ǰ���[��]");
       return false;
    }
    return true;
}

function allstop()
{
   if(!checkplan())
    return false;
    
    var sqlid831164006="DSHomeContSql831164006";
var mySql831164006=new SqlClass();
mySql831164006.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831164006.setSqlId(sqlid831164006);//ָ��ʹ�õ�Sql��id
mySql831164006.addSubPara();//ָ������Ĳ���
var sql=mySql831164006.getString();
    
//   var sql = "select count(*) from ldassignplan where state = '0' ";
   var tCount = easyExecSql(sql);
   if(tCount <=0)
   {
      alert("���з���ƻ����Ѿ���ֹ��Ŀû�������ļƻ���");
      return false;
   }
   return true;
}

function checkplan()
{
  var sqlid831164035="DSHomeContSql831164035";
var mySql831164035=new SqlClass();
mySql831164035.setResourceName("app.SelAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831164035.setSqlId(sqlid831164035);//ָ��ʹ�õ�Sql��id
mySql831164035.addSubPara();//ָ������Ĳ���
var sql=mySql831164035.getString();
  
//  var sql = "select count(*) from ldassignplan ";
  var tCount = easyExecSql(sql);
  if(tCount<=0)
   {
      alert("Ŀǰû���κμƻ���");
      return false;
   }
   return true;
}

function displayData(tStrArr)
{ 
	//showInfo.close();
	
  if(tStrArr == "0|0")
  {
  	alert("��ѯ�޼�¼��");
    return true;
  }
  alert(123);
	 //ʹ��ģ������Դ
  turnPage.useSimulation   = 1;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.strQueryResult = tStrArr;
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);  
  
  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);

  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = arrDataSet;
  //alert(turnPage.arrDataCacheSet);

  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = AssignGrid;

  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 //���뽫������������Ϊһ�����ݿ�
 //alert(turnPage.queryAllRecordCount);
// alert(turnPage.pageLineNum);
 //alert(turnPage.queryAllRecordCount / turnPage.pageLineNum);
 //alert(turnPage.queryAllRecordCount % turnPage.pageLineNum)
 turnPage.dataBlockNum = turnPage.queryAllRecordCount;
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
/*  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }*/
  //���뽫������������Ϊһ�����ݿ�  
} 
