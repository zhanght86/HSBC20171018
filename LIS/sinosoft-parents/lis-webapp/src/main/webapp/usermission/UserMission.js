// sunsheng 20110527 ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//jiyongtian  ȡ�û���֡

var win = searchMainWindow(this);
if (win == false) { win = this; }
 mCs=win.parent.VD.gVSwitch;
if(typeof(mCs)!="object")setTimeout("mCs=win.parent.VD.gVSwitch",100);


var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();


var NodeCode = 0;
var count=0;
var arrObject = new Array();  //���ڴ洢�˵���ڵ�
var page=1;
//var Opened = false;
/**
function easyQueryClickSelf()
{   
	// ��ʼ�����
	//initSelfGrpGrid();
	//var sqlid="QueryMenuPageSql1"; //ָ��properties�ļ���sql
	//var mySql=new SqlClass();
	//mySql.setResourceName("app.QueryMenuPageSql"); //ָ��ʹ�õ�properties�ļ���
	//mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	//mySql.addSubPara(operator);//ָ������Ĳ���
	//strSQL=mySql.getString(); 
	  var arr=new Array();
	  var strSQL = "select distinct m.defaultoperator,(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='1' and  m.defaultoperator = '"+operator+"'),(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='2' and  m.defaultoperator = '"+operator+"'),(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='3' and  m.defaultoperator = '"+operator+"'),(select count(1) from lwmission m where (case when  m.priorityid > m.SQLPriorityID then m.priorityid else m.SQLPriorityID end)='4' and  m.defaultoperator = '"+operator+"') from lwmission m where m.defaultoperator = '"+operator+"'";
	  initSelfGrpGrid();
	  turnPage.queryModal(strSQL, SelfGrpGrid);
	  return true;
}
*/
//jiyongtian   ���˹����ز�ѯ
function easyQueryClickOneself()
{   
	// ��ʼ�����
	initOneselfGrpGrid();
	var sqlid="QueryMenuPageSql3"; //ָ��properties�ļ���sql
	var mySql=new SqlClass();
	mySql.setResourceName("app.QueryMenuPageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(operator);//ָ������Ĳ���
	mySql.addSubPara(manageCom);//ָ������Ĳ���
	strSQL=mySql.getString();
	turnPage.queryModal(strSQL, OneselfGrpGrid);
	return true;
}
//jiyongtian  ���������ز�ѯ
function easyQueryClickPublic()
{   
	// ��ʼ�����
	initPublicGrpGrid();
	var sqlid="QueryMenuPageSql4"; //ָ��properties�ļ���sql
	var mySql=new SqlClass();
	mySql.setResourceName("app.QueryMenuPageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(operator);//ָ������Ĳ���
	mySql.addSubPara(manageCom);//ָ������Ĳ���
	strSQL=mySql.getString();
	turnPage2.queryModal(strSQL, PublicGrpGrid);
	return true;
}
//���˹����� ѡ��һ����¼���õĺ��� jiyongtian
function QueryMenuInitOneSelf()
{  
     var tFunctionID = OneselfGrpGrid.getRowColData(OneselfGrpGrid.getSelNo() - 1, 3);	 
	 if (tFunctionID == null || tFunctionID == '')
	 {
		 alert("����ѡ��һ��������Ϣ��");
		 return false;
	 }
	 var missionid=OneselfGrpGrid.getRowColData(OneselfGrpGrid.getSelNo() - 1, 9);
	 var submissionid=OneselfGrpGrid.getRowColData(OneselfGrpGrid.getSelNo() - 1, 10);
	 mCs.addVar("MissionID","",missionid);
	 mCs.addVar("SubMissionID","",submissionid);
	 mCs.addVar("ActivityID","",tFunctionID);
	 var sqlid="QueryMenuPageSql5"; //ָ��properties�ļ���sql
	 var mySql=new SqlClass();
	 mySql.setResourceName("app.QueryMenuPageSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(operator);//ָ������Ĳ���
	 mySql.addSubPara(tFunctionID);//ָ������Ĳ���
	 strSQL=mySql.getString();
	 var strArray = easyExecSql(strSQL);
	 if(strArray == null)
	 {
		 alert("���ݹ������Ʋ�ѯ�˵��ڵ�ʧ�ܣ�");
		 return false;
	 }
	 var runscript= strArray[0][0];
	 var nodecode = strArray[0][1];
	 var parentnode = strArray[0][2];
	 
	  NodeCode = nodecode;
	  arrObject[0] = nodecode;//��ǰ�ڵ�
	  arrObject[1] = parentnode; //���ڵ�
	  count = 1;
	  openMenuLayer(parentnode);
	  window.location=runscript; 		  		 
}
//���������� ѡ��һ����¼���õĺ��� jiyongtian
function QueryMenuInitPublic()
{
	 if (PublicGrpGrid.getSelNo() == 0)
	 {
		    alert("���ݹ������Ʋ�ѯ�˵��ڵ�ʧ�ܣ�");
		    return false;
      } 	
	 var tFunctionIDPublic = OneselfGrpGrid.getRowColData(PublicGrpGrid.getSelNo() - 1, 3);
	 var sqlid="QueryMenuPageSql5"; //ָ��properties�ļ���sql
	 var mySql=new SqlClass();
	 mySql.setResourceName("app.QueryMenuPageSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(operator);//ָ������Ĳ���
	 mySql.addSubPara(tFunctionIDPublic);//ָ������Ĳ���
	 strSQL=mySql.getString();
	 var strArray = easyExecSql(strSQL);
	 if(strArray == null)
	 {
		 alert("��ѯ�˵��ڵ�ʧ�ܣ�");
		 return false;
	 }
	 var runscript= strArray[0][0];
	 var nodecode = strArray[0][1];
	 var parentnode = strArray[0][2];
	  NodeCode = nodecode;
	  arrObject[0] = nodecode;//��ǰ�ڵ�
	  arrObject[1] = parentnode; //���ڵ�
	  count = 1;
	  openMenuLayer(parentnode);
	  window.location=runscript;	  		 
}
/* jiyongtian 2012-6-11 ��ʱû���õ�
function initMenu() { //���mulLineĳ�д����ķ���
  if (SelfGrpGrid.getSelNo() === 0) {
    alert("����ѡ��һ��������Ϣ��");
    return false;
  } 
  var polNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 1);
  var prtNo = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 2);
  var cMissionID=SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 3);
  var runscript=SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 4);
  var nodecode = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 5);
  var parentnode = SelfGrpGrid.getRowColData(SelfGrpGrid.getSelNo() - 1, 6);
  NodeCode = nodecode;
  arrObject[0] = nodecode;//��ǰ�ڵ�
  arrObject[1] = parentnode; //���ڵ�
  count = 1;
  // alert("runscript==="+parentnode);
  openMenuLayer(parentnode);
 // Opened = true;
  window.location=runscript; 
  //fm + ".all('" + instanceName + 1 + "')[" + 1 + "].style.backgroundColor ='#EE82EE'"; 		
}
*/
//��ѯ�������˵��Ľڵ㣬�Ӹ��ڵ㵽�ӽڵ����չ��
function openMenuLayer(sParentNode){
	//alert("sParentNode=="+sParentNode);
	if(sParentNode=="0"){
		for(var i=arrObject.length-1;i>=0;i--){	//�����˵��ڵ㣬�Ӹ��ڵ㵽�ӽڵ����չ��
			var nodesrc = parent.fraMenu.window.document.getElementsByName(arrObject[i]); //�˵�link��ַ����
			//var nodeicon = parent.fraMenu.window.document.getElementsByName("s"+arrObject[i]);//�˵��ڵ�ͼƬ����
			
			//var iconsrc = nodeicon[0].src;

		//	alert(nodesrc.isExpand);
			//var icontype = iconsrc.substring(iconsrc.indexOf("images/",0)+7,iconsrc.length);//��Ӧ�˵�ͼƬ����
			try{
			var usrc = nodesrc[0];	
			//if(icontype=="folderopen_blue.png"){//ͨ���˵�ͼƬ�жϸ�ͼƬ�Ƿ��Ѵ�
			if(nodesrc.isExpand){//ͨ���˵�ͼƬ�жϸ�ͼƬ�Ƿ��Ѵ�
				continue;
			}else{
  				usrc.click();
  			}
  		}
  		catch(e)
  		{
  			}
		}
  	}else{
  		var sqlid="QueryMenuPageSql2";
		var mySql=new SqlClass();
		mySql.setResourceName("app.QueryMenuPageSql"); //ָ��ʹ�õ�properties�ļ���
		mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
		mySql.addSubPara(sParentNode);//ָ������Ĳ���
		strSQL=mySql.getString();
		var strArray = easyExecSql(strSQL );
		sParentNode = strArray[0];
		if(strArray[0]!=0){		
		count = count+1;
		arrObject[count] = strArray[0];//arrObject�������ڴ洢�ڵ�
		}
  		 openMenuLayer(sParentNode);
  	}
}
function initPersonalWorkPool()
{  
	var sqlid="QueryMenuPageSql0"; //ָ��properties�ļ���sql
	var mySql=new SqlClass();
	mySql.setResourceName("app.QueryMenuPageSql"); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(operator);//ָ������Ĳ���
	mySql.addSubPara(operator);//ָ������Ĳ���
//	mySql.addSubPara(manageCom);//ָ������Ĳ���
	strSQL=mySql.getString();
	 var strArray = easyExecSql(strSQL);
	 if(strArray == null)
	 {
		 alert("��ѯ�û�����������Ϣ����");
		 return false;
	 }
	 fm.UserName.value = strArray[0][0];

	 for ( var i = 0 ;i< strArray.length;i++ )
	 {   
		 var count = strArray[i][2];
		 if(count == '1')
		 {
			 fm.ExtendA.value = strArray[i][1] +"       ��";
	     }
		 if(count == '2')
		 {
			 fm.EmergencyA.value = strArray[i][1] +"       ��";
	     }
		 if(count == '3')
		 {
			 fm.SpeedUpA.value = strArray[i][1] +"       ��";
		 }
		 if(count == '4')
		 {
			 fm.StandardA.value = strArray[i][1] +"       ��";
		 }
	 }
	 return true;
}
