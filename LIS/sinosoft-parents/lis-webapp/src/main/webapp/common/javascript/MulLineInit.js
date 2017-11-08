var turnPage = new turnPageClass();
var tArray = new Array();
var tInstanceName="";
var tfunctionid="";
var wholeArry = new Array();
wholeArry=[["序号","30px",100,0]];
//查询结果部分
var strSQLResultPart=" select ";
//查询表部分
var strSQLTablePart=" from LWMission　lwmission left join lwactivity on lwactivity.activityid = lwmission.activityid ";
// 查询where条件部分
var strSQLWherePart="  where 1=1 ";
// 紧急度和颜色ID非常耗时，单独拿出 
var strSQLDegreePart1 =" (case when (select priorityid from lwpriority where range = (select min(range) from lwpriority where range > (case  when lwmission.ActivityStatus <> '3' and  lwmission.StandEndDate is not null then round((1 - calworktime(substr(to_char(SysDate,'YYYY-MM-DD HH24:MI:SS'),1,10),substr(to_char(SysDate,'YYYY-MM-DD HH24:MI:SS'),12,8), lwmission.StandEndDate,lwmission.StandEndTime) / calworktime(lwmission.InDate, lwmission.InTime,lwmission.StandEndDate, lwmission.StandEndTime)) * 100,2) else 0 end))) >= lwmission.sqlpriorityid then lwmission.sqlpriorityid "
                      +" else (select priorityid from lwpriority where range = (select min(range) from lwpriority where range > (case when lwmission.ActivityStatus <> '3' and lwmission.StandEndDate is not null then round((1 - calworktime(substr(to_char(SysDate,  'YYYY-MM-DD HH24:MI:SS'), 1, 10), substr(to_char(SysDate, 'YYYY-MM-DD HH24:MI:SS'), 12, 8), lwmission.StandEndDate, lwmission.StandEndTime) / calworktime(lwmission.InDate, lwmission.InTime, lwmission.StandEndDate, lwmission.StandEndTime)) * 100, 2) else 0 end))) end) ";
var strSQLDegreePart2 = " as degreeofemergency ";
                   
//      left join lwactivity on LWMission.ActivityID=lwactivity.ActivityID

/**
 * InstanceName  MulLine对象名称  
 * functionid  功能ID对应(select * from ldcode where codetype='functionid'的记录)
 * whereArray  where条件， 二位数组第一列 ：表名.字段名    第二列 ：值   类似这样：[lwmission.ActivityID ,"000001098"]
 * radioOrChk  单/多选框  可以为空 ，默认为 单选狂
 * boxEventFuncName  选择一条记录后的函数
 * classversion 可以为空，1、个人工作池，2、公共工作池， 默认为个人工作池 (可自定义 如：3 第二个个人工作池，查询SQL与1不同) 
 * degreeofemergency  可以为空 紧急度 ，为1时查询出紧急度，为0时不查询， 默认问为不查询
 */
function  initGridQuery(InstanceName,functionid,whereArray ,radioOrChk,boxEventFuncName,classversion ,degreeofemergency)
{   
	tInstanceName=InstanceName;
	tfunctionid=functionid;
    try
    {	
    	//初始化页面有关的对象
	    initPage(InstanceName);
	   //根据FunctionID拼写查询语句where条件以外的部分，构成muline对象的数组
	    queryActivityID(functionid , classversion ,degreeofemergency);
	   //拼写查询SQL的where条件部分
	    makeSQLCondition(whereArray　, functionid ,degreeofemergency);
    	//初始化muline对象 
	   initInstanceName(InstanceName,radioOrChk,boxEventFuncName,degreeofemergency);
	    //执行sql，queryModal此MulLine对象
	   easyQueryClickOneself(InstanceName);
    }
    catch(ex)
    {
    	alert("MuLineInit.js文件出错 ，"+ex);
    }
}
function initPage(InstanceName)
{
	try{		
		eval('turnPage_'+tInstanceName +'= new turnPageClass()');
		document.getElementById("div"+InstanceName).innerHTML="<table class=common><tr class=common><td><span id='span"+InstanceName+"'> </span><br/></td></tr></table>" +
				"<INPUT VALUE='首  页' class=cssButton TYPE=button onclick='turnPage_"+InstanceName+".firstPage();'>" +
				"<INPUT VALUE='上一页' class=cssButton TYPE=button onclick='turnPage_"+InstanceName+".previousPage();'>" +
				"<INPUT VALUE='下一页' class=cssButton TYPE=button onclick='turnPage_"+InstanceName+".nextPage();'>" +
				"<INPUT VALUE='尾  页' class=cssButton TYPE=button  onclick='turnPage_"+InstanceName+".lastPage();'>";
	}
	catch(ex)
	{
		alert("错误信息    "+ex.message);
	}
}
function queryActivityID(functionid ,classversion, degreeofemergency)
{   
	if (classversion == null || classversion == "")
	{
		classversion = "1";
	}
//    var tSQL=" select businesstable,linkcondition from ldwftobusiness where classversion='' and  functionid=''";
//     var tableSQL=" select ldwf.businesstable,ldwf.linkcondition from ldwftobusiness ldwf  where 1=1 and  ldwf.businesstable in (select ld.businesstable from ldwfmulline ld where ld.functionid = ldwf.functionid) and ldwf.classversion='"+classversion+"' and ldwf.functionid='"+functionid+"' order by ldwf.businesstable"; 
     
	var sqlid0="MulLineInitSql0";
	var mySql0=new SqlClass();
	mySql0.setResourceName("common.javascript.MulLineInitSql"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(classversion);//指定传入的参数
	mySql0.addSubPara(functionid);//指定传入的参数
	var tableSQL=mySql0.getString();
     
     var tableSQLArry=easyExecSql(tableSQL);
     if( tableSQLArry != null && tableSQLArry !="" )
     {
    	 for (var i=0;i<tableSQLArry.length;i++)
         {  
        	var tablename=tableSQLArry[i][0];
        	var linkcondition=tableSQLArry[i][1];
        	// 如果仅仅查lwmission表里的业务字段  不需关联表
        	if(tablename.toLowerCase()=="lwmission")
        	{
        		
        	}
        	// 关联业务表
        	else
        	{
        		strSQLTablePart=strSQLTablePart+" left join "+tablename+" on "+linkcondition;
        	}       	
//        	var fieldSQL=" select ld.fieldcode, (select lw.fieldcodename from ldwfbusifield lw where lw.businesstable = ld.businesstable and lw.fieldcode = ld.fieldcode), colwidth,isshow from ldwfmulline ld where 1=1 and ld.functionid = '"+functionid+"' and ld.businesstable='"+tablename+"' order by ld.colserialno,ld.fieldcode ";
            
        	var sqlid1="MulLineInitSql1";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("common.javascript.MulLineInitSql"); //指定使用的properties文件名
        	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        	mySql1.addSubPara(functionid);//指定传入的参数
        	mySql1.addSubPara(tablename);//指定传入的参数
        	var fieldSQL=mySql1.getString();
        	
        	var fieldSQLArry=easyExecSql(fieldSQL);         
            if(fieldSQLArry != null && fieldSQLArry != "")
            {          
            	 for (var j=0;j<fieldSQLArry.length;j++)
                 {   
            		 strSQLResultPart=strSQLResultPart+tablename+"."+fieldSQLArry[j][0]+",";
                     if(fieldSQLArry[j][2] == "" || fieldSQLArry[j][2] == null)
                     {
                    	 fieldSQLArry[j][2] ="100";
                     }
            		 if(fieldSQLArry[j][3] == 1||fieldSQLArry[j][3]=="1")
            		 {   
            			 var newArry=new Array();
            			 newArry[0]=fieldSQLArry[j][1];         			
            			 newArry[1]=fieldSQLArry[j][2]+"px";            		
            			 newArry[2]=100;            		
            			 newArry[3]=0;              			
//            			 wholeArry.push(fieldSQLArry[j][1],fieldSQLArry[j][2],100,0);
            			 wholeArry.push(newArry);
            		 }
            		 else
            		 {
//            			 wholeArry.push[fieldSQLArry[j][0],fieldSQLArry[j][2],100,3];
            			 var tNewArry=new Array();
            			 tNewArry[0]=fieldSQLArry[j][0];         			
            			 tNewArry[1]=fieldSQLArry[j][2]+"px";            		
            			 tNewArry[2]=100;            		
            			 tNewArry[3]=3; 
            			 wholeArry.push(tNewArry);
            		 }
                 }
            }
            else
            {
            	
            }
         }
     }
     else
     {
    	 alert("根据活动节点ID查询业务表出错！");
    	 return false;
     }
     
/**     //如果查询结果含有decode子句或者其他字句请添加
     if(FunctionID=="")
     {
    	 wholeArry.push(["你想显示的列的列名 "," 想显示的列宽",   , 是否显示]);
    	 strSQLResultPart = strSQLResultPart+"  你想添加的查询结果sql ，别忘了格式   表名.字段名 ";
     }
*/     
     
     wholeArry.push(["MissionID","100px",100,3],["SubMissionID","50px",100,3],["ProcessID","50px",100,3],["ActivityID","50px",100,3]);
     // 查询结果部分  添加上 missionid、submissionid 、 processid、 activityid
     strSQLResultPart = strSQLResultPart+" lwmission.missionid,lwmission.submissionid,lwmission.processid,lwmission.activityid ";
     
     if(degreeofemergency == "1")
     {
         // 查询结果部分 添加上紧急度
         strSQLResultPart = strSQLResultPart +" ,"+ strSQLDegreePart1 + strSQLDegreePart2;
         // 查询结果添加上 紧急度的颜色标识ID
         strSQLResultPart = strSQLResultPart + ",(select colorid from lwpriority  where priorityid = "+ strSQLDegreePart1+" )";
         wholeArry.push(["紧急度","80px",100,0],["colorid","80px",100,3]);
     }
}
function makeSQLCondition(whereArray ,functionid ,degreeofemergency)
{   
	strSQLWherePart =strSQLWherePart +" and lwactivity.functionid ='"+functionid+"' ";
	var newwhereArray=whereArray;
	if (newwhereArray == null)
	{
		alert("传入的查询条件为空！");
		return false;
	}
	for(var i=0;i<newwhereArray.length;i++)
	{
		strSQLWherePart=strSQLWherePart +" and "+newwhereArray[i][0]+"= '"+newwhereArray[i][1]+"' ";
	}
/** where 条件部分无法通过二维数组传递过来时 
	if(tfunctionid=="xxxxxx")
	{
//		strSQLWherePart+
       
	}
*/
	if(degreeofemergency !=null && typeof(degreeofemergency) !="undefined" && degreeofemergency != "" )
	{
		strSQLWherePart=strSQLWherePart+" order by degreeofemergency";	
	}
}
function  initInstanceName(InstanceName,radioOrChk,boxEventFuncName,degreeofemergency)
{
	
 var iArray = new Array();
    
    try
    {  
      iArray = wholeArry;    
      eval(InstanceName +' = new MulLineEnter( "fm" , InstanceName)'); 
      eval(InstanceName+'.mulLineCount = 0');
      eval(InstanceName+'.displayTitle = 1');
      eval(InstanceName+'.locked = 1');
      if(radioOrChk == null ||radioOrChk =="")
      {
    	  radioOrChk = "1";
      }
      if(radioOrChk=="1")
      {   
    	  eval(InstanceName+'.canSel = 1');//单选框   	     
      }
      else
      {   
    	  eval(InstanceName+'.canChk = 1');//多选框    
      }
      eval(InstanceName+'.hiddenPlus = 1');
      eval(InstanceName+'.hiddenSubtraction = 1');
      if(boxEventFuncName != null && boxEventFuncName !="")
      {
    	  eval(InstanceName+'.selBoxEventFuncName = "'+boxEventFuncName+'"');
      }
      eval(InstanceName+'.loadMulLine(iArray)');
     
      if(degreeofemergency == "1")
      {   
    	  var maxcol="";
    	  for(var ii=0;ii<wholeArry.length;ii++)
    	  {   
    		  if (wholeArry[ii][0]=="colorid")
    		  {
    			  maxcol=ii;
    		  }
    	  }
    	  eval(InstanceName+'.colorNum =maxcol');
      }
    }
    catch(ex)
    {
      alert("异常 "+ex);
    }  
}

function easyQueryClickOneself()
{ 
    var strSQL=strSQLResultPart+strSQLTablePart+strSQLWherePart;	   
//	prompt('',strSQL);
	eval(' turnPage_'+tInstanceName+'.queryModal(strSQL, eval(tInstanceName))');	
}
