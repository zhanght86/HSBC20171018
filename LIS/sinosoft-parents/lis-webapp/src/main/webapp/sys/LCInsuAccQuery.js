//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var sqlresourcename = "sys.LCInsuAccQueryInputSql"; 

//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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

//显示frmSubmit框架，用来调试
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
    	
			mySql.setResourceName(sqlresourcename); //指定使用的properties文件名
			mySql.setSqlId(sqlid1);//指定使用的Sql的id
			mySql.addSubPara(tPolNo);

				
    }
    /*else if(tIsCancelPolFlag=="1"){//销户保单查询
         mySql.setSqlId("LCInsuAccQueryInputSql_1");
         mySql.addPara("tPolNo",tPolNo);
    }*/
    else {
      show("保单类型传输错误!");
      return;
    }
    
    turnPage.queryModal(mySql.getString(), PolGrid);
}


/*********************************************************************
 *  显示保险账户分类信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function showInsureAccClass()
{

    var arrReturn = new Array();
    var tSel = PolGrid.getSelNo();
    var cPolNo = "";
    if( tSel == 0 || tSel == null )
        show("请选择一条账户记录，再查看保险账户详细信息");
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
		mySql.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql.setSqlId(sqlid1);//指定使用的Sql的id
		mySql.addSubPara(tPolNo);
		mySql.addSubPara(cInsuAccNo);
    		 

         turnPage2.queryModal(mySql.getString(), PolGridClass);
    var sqlid1="LCInsuAccQueryInputSql3";
    var mySql=new SqlClass();
		mySql.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql.setSqlId(sqlid1);//指定使用的Sql的id
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
        show("请选择一条账户记录，再查看保险账户详细信息");
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
	mySql.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql.setSqlId(sqlid1);//指定使用的Sql的id
	mySql.addSubPara(tPolNo);
	

    turnPage2.queryModal(mySql.getString(),PolGridClass);
}

function getAccTrace()
{
		alert(2);
    initPolGridTrace();
	  var sqlid1="LCInsuAccQueryInputSql5";
	  var mySql=new SqlClass();
	  mySql.setResourceName(sqlresourcename); //指定使用的properties文件名
 	  mySql.setSqlId(sqlid1);//指定使用的Sql的id
	  mySql.addSubPara(tPolNo);	
	  mySql.addSubPara(tPolNo);	
	 
    //mySql.addSubPara(tPolNo);	
 

	turnPage3.queryModal(mySql.getString(), PolGridTrace);
}

 function findriskcode()
{
	 if(document.all('RiskCode').value == '')
 	   {
 	   	show("请先选择险种编码！");
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
	mySql.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql.setSqlId(sqlid1);//指定使用的Sql的id
	mySql.addSubPara(tPolNo);
    
    var LOArrearageSQL=mySql.getString();
    

	turnPage4.queryModal(LOArrearageSQL, LOArrearageGrid);
}*/