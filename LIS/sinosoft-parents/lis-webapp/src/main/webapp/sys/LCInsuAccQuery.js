//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var sqlresourcename = "sys.LCInsuAccQueryInputSql"; 

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
            parent.fraMain.rows = "0,0,0,0,*";
  }
    else {
        parent.fraMain.rows = "0,0,0,82,*";
    }

    parent.fraMain.rows = "0,0,0,0,*";
}

function returnParent()
{
    //top.opener.initSelfGrid();
    //top.opener.easyQueryClick();
    top.close();
}


function easyQueryClick()
{
    if( tIsCancelPolFlag == "0"){
    
    	var sqlid1="LCInsuAccQueryInputSql1";
    	var mySql=new SqlClass();
    	
			mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
			mySql.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql.addSubPara(tPolNo);

				
    }
    /*else if(tIsCancelPolFlag=="1"){//����������ѯ
         mySql.setSqlId("LCInsuAccQueryInputSql_1");
         mySql.addPara("tPolNo",tPolNo);
    }*/
    else {
      show("�������ʹ������!");
      return;
    }
    
    turnPage.queryModal(mySql.getString(), PolGrid);
}


/*********************************************************************
 *  ��ʾ�����˻�������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function showInsureAccClass()
{

    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    var cPolNo = "";
    if( tSel == 0 || tSel == null )
        show("��ѡ��һ���˻���¼���ٲ鿴�����˻���ϸ��Ϣ");
    else
    {
        cInsuAccNo = PolGrid.getRowColData( tSel - 1, 1 );
        if (cInsuAccNo== null||cInsuAccNo == "")
            return;

        divLCInsureAccClass.style.display="";

        divLCInsureAccTrace.style.display="";
        initPolGridClass();
        initPolGridTrace();
        
		var sqlid1="LCInsuAccQueryInputSql2";
		var mySql=new SqlClass();
		mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql.addSubPara(tPolNo);
		mySql.addSubPara(cInsuAccNo);
    		 

         turnPage2.queryModal(mySql.getString(), PolGridClass);
    var sqlid1="LCInsuAccQueryInputSql3";
    var mySql=new SqlClass();
		mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql.addSubPara(tPolNo);
		mySql.addSubPara(cInsuAccNo);
		
         turnPage3.queryModal(mySql.getString(), PolGridTrace);
    }
}

function showAccDetail()
{


    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
        show("��ѡ��һ���˻���¼���ٲ鿴�����˻���ϸ��Ϣ");
    else
    {
        var cInsuAccNo = PolGrid.getRowColData( tSel - 1, 1 );
        if (cInsuAccNo== null||cInsuAccNo == "")
            return;

        divLCInsureAccClass.style.display="";
        divLCInsureAccTrace.style.display="";
    }
}

function getAccClass()
{
	alert(1);
	initPolGridClass();
	var sqlid1="LCInsuAccQueryInputSql4";
	var mySql=new SqlClass();
	mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(tPolNo);
	

    turnPage2.queryModal(mySql.getString(),PolGridClass);
}

function getAccTrace()
{
		alert(2);
    initPolGridTrace();
	  var sqlid1="LCInsuAccQueryInputSql5";
	  var mySql=new SqlClass();
	  mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
 	  mySql.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	  mySql.addSubPara(tPolNo);	
	  mySql.addSubPara(tPolNo);	
	 
    //mySql.addSubPara(tPolNo);	
 

	turnPage3.queryModal(mySql.getString(), PolGridTrace);
}

 function findriskcode()
{
	 if(document.all('RiskCode').value == '')
 	   {
 	   	show("����ѡ�����ֱ��룡");
 	   	return false;
 	  }
}

function  QueryClassAndTrace()
{
	easyQueryClick();
	
	getAccClass();
	getAccTrace();
	//getLOArrearage();
}

/*
function getLOArrearage()
{
    initLOArrearageGrid();
  var sqlid1="LCInsuAccQueryInputSql6";
  var mySql=new SqlClass();
	mySql.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(tPolNo);
    
    var LOArrearageSQL=mySql.getString();
    

	turnPage4.queryModal(LOArrearageSQL, LOArrearageGrid);
}*/