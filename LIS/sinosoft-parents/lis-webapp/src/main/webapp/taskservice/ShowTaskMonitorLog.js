//�������ƣ�TaskService.js
//�����ܣ�
//�������ڣ�2004-12-15 
//������  ��ZhangRong
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var k = 0;
 var turnPage = new turnPageClass();  
 var turnPage1 = new turnPageClass(); 
 var turnPage2 = new turnPageClass(); 
   var turnPageItem = new turnPageClass();  
  
  var mySql = new SqlClass();
/*********************************************************************
 *  �ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	if( mAction == "")
		return;

	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
}

 
function ItemQuery()
{
	// ��ʼ�����
	initLogItemGrid();

	// ��дSQL���
/*  var tSQL = "select subjectid,itemid,(select itemdes from logitem where itemid=logdomaintoitem.itemid),keytype,Switch "
           + " from logdomaintoitem where 1=1 "
           + " and ( subjectid='TASK"+jMonitorCode+"' or subjectid in (select 'TASK'||taskcode from ldtaskgroupdetail where taskgroupcode='"+jMonitorCode+"')) "
           + " and switch='Y'";
	turnPageItem.queryModal(tSQL, LogItemGrid);
*/
	mySql=new SqlClass();
    mySql.setResourceName("taskservice.TaskServiceSql");
    mySql.setSqlId("TaskServiceSql13");

    mySql.addSubPara(jMonitorCode);
    mySql.addSubPara(jMonitorCode);
	turnPageItem.queryModal(mySql.getString(), LogItemGrid);
	if(LogItemGrid.mulLineCount <= 0){
		alert("δ��ѯ���������Ӧ���Ƶ���Ϣ��");
		return false;
	}
	//divLogItemMain.style.display = "";

}

function ShowItem()
{
	var tSel = LogItemGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}			
	
	var tControlID = LogItemGrid.getRowColData(tSel-1,1);
	var tItemID =  LogItemGrid.getRowColData(tSel-1,2);
	
	document.all('hiddenItemID').value=tItemID;
	document.all('hiddenControlID').value=tControlID;
	initTaskRunLogTodayGrid(tItemID);
	queryTaskRunLogTodayGrid(tItemID,tControlID);
	initTaskRunLogBeforeGrid(tItemID);
	//queryTaskRunLogBefore(tItemID);
	
	divTaskRunLogToday.style.display="";
	divTaskRunLogBefore.style.display="";
}


/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}


/*********************************************************************
 *  ��ʾ����ƻ�
 *  ����  ��  
 *  ����ֵ��  ��
 *********************************************************************
 */


function queryTaskRunLogTodayGrid(tItemID,tControlID)
{
	//resetForm();
	initTaskRunLogTodayGrid(tItemID);
	//k = k + 1;
	//var strSQL = "";
	var strSQL = "";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	if(tItemID=='01'||tItemID=='04')
	{
/*		strSQL = "select logno,serialno,keyno,state,itemdes,remark,makedate,maketime,modifydate,modifytime from  LogBusinessState "
	         + " where (subjectid='"+tControlID+"' ) "
	         + " and itemid='"+tItemID+"' "
	         + " and makedate = to_char(sysdate,'yyyy-mm-dd') "
	         + " order by logno ";
*/
		mySql.setSqlId("TaskServiceSql14");
		mySql.addSubPara(tControlID);
		mySql.addSubPara(tItemID);     
	}
	else
	{
/*		strSQL = " select logno,serialno,keyno,itemdes,remark,makedate,maketime  from LogTrackResult "
 					 + " where (subjectid='"+tControlID+"' ) "
 					 + " and itemid='"+tItemID+"' "
 					 + " and makedate = to_char(sysdate,'yyyy-mm-dd') "
	    		     + " order by logno ";
*/
		mySql.setSqlId("TaskServiceSql15");
		mySql.addSubPara(tControlID);
		mySql.addSubPara(tItemID); 
	}

	//mySql.setSqlId("TaskServiceSql4");  
	//mySql.addSubPara("");  
//	turnPage.queryModal(strSQL,TaskRunLogTodayGrid);
	turnPage.queryModal(mySql.getString(),TaskRunLogTodayGrid);
}

function queryTaskRunLogBefore()
{
	//resetForm();
	var tStartDate = document.all('RunLogDateStart').value;
	var tEndDate = document.all('RunLogDateEnd').value;
	
	if(tStartDate==null||tStartDate==''
	   ||tEndDate==null||tEndDate=='')
	  {
	  	alert('������\'��־����\'��\'��־ֹ��\'���ٲ�ѯ');
	  	return false;
	  }
	  var tItemID = document.all('hiddenItemID').value;
	initTaskRunLogBeforeGrid(tItemID);
	
	var tControlID = document.all('hiddenControlID').value;
	//k = k + 1;
	var strSQL = "";
	mySql = new SqlClass();
	mySql.setResourceName("taskservice.TaskServiceSql");
	if(tItemID=='01'||tItemID=='04')
	{
/*		strSQL = "select logno,serialno,keyno,state,itemdes,remark,makedate,maketime,modifydate,modifytime from  LogBusinessState "
	         + " where (subjectid='"+tControlID+"'  ) "
	         + " and itemid='"+tItemID+"' "
	         + " and makedate>='"+tStartDate+"' "
           + " and maketime<='"+tEndDate+"' "
	         + " order by logno ";
*/
	    mySql.setSqlId("TaskServiceSql16"); 
	    mySql.addSubPara(tControlID); 
	    mySql.addSubPara(tItemID); 
	    mySql.addSubPara(tStartDate); 
	    mySql.addSubPara(tEndDate);  
  }
  else
  {
/*  	strSQL = " select logno,serialno,keyno,itemdes,remark,makedate,maketime  from LogTrackResult "
 					 + " where (subjectid='"+tControlID+"' ) "
 					 + " and itemid='"+tItemID+"' "
 					 + " and makedate>='"+tStartDate+"' "
           + " and maketime<='"+tEndDate+"' "
	         + " order by logno ";
*/
	    mySql.setSqlId("TaskServiceSql17"); 
	    mySql.addSubPara(tControlID); 
	    mySql.addSubPara(tItemID); 
	    mySql.addSubPara(tStartDate); 
	    mySql.addSubPara(tEndDate); 
  }
//	turnPage1.queryModal(strSQL,TaskRunLogBeforeGrid);
	turnPage1.queryModal(mySql.getString(),TaskRunLogBeforeGrid);
}

