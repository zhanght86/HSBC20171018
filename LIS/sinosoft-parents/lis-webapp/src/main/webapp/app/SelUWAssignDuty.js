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
function UWAssignSubmit()
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


function UWAssignUpdate()
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
 
function UWStartSel()
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
function UWStartAll()
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
function UWStopSel()
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
function UWStopAll()
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

function allstop()
{
   if(!checkplan())
    return false;
   
   var sqlid831162730="DSHomeContSql831162730";
var mySql831162730=new SqlClass();
mySql831162730.setResourceName("app.SelUWAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831162730.setSqlId(sqlid831162730);//ָ��ʹ�õ�Sql��id
mySql831162730.addSubPara();//ָ������Ĳ���
var sql=mySql831162730.getString();
    
//   var sql = "select count(*) from ldassignplan where state = '0' ";
   var tCount = easyExecSql(sql);
   if(tCount <=0)
   {
      alert("���з���ƻ����Ѿ���ֹ��Ŀû�������ļƻ���");
      return false;
   }
   return true;
}




function InitAssign()
{
   //��ʼ����ص�����
   var sqlid831162834="DSHomeContSql831162834";
var mySql831162834=new SqlClass();
mySql831162834.setResourceName("app.SelUWAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831162834.setSqlId(sqlid831162834);//ָ��ʹ�õ�Sql��id
mySql831162834.addSubPara();//ָ������Ĳ���
var sqluwamount=mySql831162834.getString();
   
//   var sqluwamount = "select count(*) from lwmission where activityid = '0000001100' and defaultoperator is null ";
   document.all('UWPolSum').value = easyExecSql(sqluwamount);
  //ʱ���ʼ��
  var sqlid831162920="DSHomeContSql831162920";
var mySql831162920=new SqlClass();
mySql831162920.setResourceName("app.SelUWAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831162920.setSqlId(sqlid831162920);//ָ��ʹ�õ�Sql��id
mySql831162920.addSubPara();//ָ������Ĳ���
var sqltime=mySql831162920.getString();
  
//  var sqltime = "select taskstarttime,taskendtime from ldassignplan where rownum='1' and activityid in ('0000001100') ";
  var arrResult = easyExecSql(sqltime);
  if(arrResult !=null)
  {
     document.all('StartTime').value = arrResult[0][0];
     document.all('EndTime').value = arrResult[0][1];
  }
  
  //mulLine��ʼ��
//  var sql = "  select distinct(a.assignno) ,'����Ա����' ,"
//           +"  nvl((select b.planamount from ldassignplan b where a.taskno=b.taskno  and a.assignno=b.assignno and b.activityid='0000001090' ),0),"
//           +"  nvl((select c.planamount from ldassignplan c where a.taskno=c.taskno  and a.assignno=c.assignno and c.activityid='0000001002' ),0),"
//           +"  nvl((select d.planamount from ldassignplan d where a.taskno=d.taskno  and a.assignno=d.assignno and d.activityid='0000001404' ),0),"
//           +"  a.state "
//           +"  from ldassignplan a order by a.assignno ";
  
  var sqlid831163016="DSHomeContSql831163016";
var mySql831163016=new SqlClass();
mySql831163016.setResourceName("app.SelUWAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163016.setSqlId(sqlid831163016);//ָ��ʹ�õ�Sql��id
mySql831163016.addSubPara();//ָ������Ĳ���
var sqlAssign=mySql831163016.getString();
  
//  var sqlAssign = " select a.assignno,b.username,b.uwpopedom,'1',a.planamount,nvl((select actuallyamount from ldautoassign where taskno = a.taskno and activityid = a.activityid and assignno = a.assignno),0),a.state from ldassignplan a,lduser b where  a.activityid in ('0000001100') and b.usercode=a.assignno and b.usercode in (select usercode from lduwuser) order by a.assignno ";
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage1.queryModal(sqlAssign, AssignGrid);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult) {
    AssignGrid.clearData("AssignGrid");  	
    alert("û�в�ѯ�����ݣ�");
    return false;
  }
  
  //�ж��ܹ�����
  var sqlid831163105="DSHomeContSql831163105";
var mySql831163105=new SqlClass();
mySql831163105.setResourceName("app.SelUWAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163105.setSqlId(sqlid831163105);//ָ��ʹ�õ�Sql��id
mySql831163105.addSubPara();//ָ������Ĳ���
var sqlPlan=mySql831163105.getString();

  
//  var sqlPlan = "select distinct 1 from ldassignplan where activityid in ('0000001100')";
  var Exists = easyExecSql(sqlPlan);
  if(Exists=="1"){
    //�й����쳣����������޸Ļ��˹��ϲ��ķ���ƻ� ����ֻ��ͨ���޸����ύ����ƻ�
    fm.addNew.disabled = true;
  }else{
    fm.addNew.disabled = false;
  } 
}

function afterCodeSelect(cCodeName, Field)
{
   if(cCodeName =="uwuser")
   //alert(12312);
	if(cCodeName=="uwtype"||cCodeName=="contype")
	{
		fm.ActivityID.value="";
	}
  if (cCodeName == "uwtype") 
  {
       getActivityID("uwtype");
  }
   if(cCodeName == "contype")
   {
     getActivityID("contype");
   }
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

function  allstart()
{
   if(!checkplan())
    return false;
    
    var sqlid831163141="DSHomeContSql831163141";
var mySql831163141=new SqlClass();
mySql831163141.setResourceName("app.SelUWAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163141.setSqlId(sqlid831163141);//ָ��ʹ�õ�Sql��id
mySql831163141.addSubPara();//ָ������Ĳ���
var sql=mySql831163141.getString();
    
//   var sql = "select count(*) from ldassignplan where state ='1'";
   var tCount = easyExecSql(sql);
   if(tCount<=0)
    {
       alert("���з���ƻ����Ѿ�������û����ֹ�ļƻ���");
       return false;
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

function checkplan()
{
  var sqlid831163238="DSHomeContSql831163238";
var mySql831163238=new SqlClass();
mySql831163238.setResourceName("app.SelUWAssignDutySql");//ָ��ʹ�õ�properties�ļ���
mySql831163238.setSqlId(sqlid831163238);//ָ��ʹ�õ�Sql��id
mySql831163238.addSubPara();//ָ������Ĳ���
var sql=mySql831163238.getString();
  
//  var sql = "select count(*) from ldassignplan ";
  var tCount = easyExecSql(sql);
  if(tCount<=0)
   {
      alert("Ŀǰû���κμƻ���");
      return false;
   }
   return true;
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
