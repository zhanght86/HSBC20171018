var turnPage = new turnPageClass();
var tArray = new Array();
var tInstanceName="";
var tfunctionid="";
var wholeArry = new Array();
wholeArry=[["���","30px",100,0]];
//��ѯ�������
var strSQLResultPart=" select ";
//��ѯ����
var strSQLTablePart=" from LWMission��lwmission left join lwactivity on lwactivity.activityid = lwmission.activityid ";
// ��ѯwhere��������
var strSQLWherePart="  where 1=1 ";
// �����Ⱥ���ɫID�ǳ���ʱ�������ó� 
var strSQLDegreePart1 =" (case when (select priorityid from lwpriority where range = (select min(range) from lwpriority where range > (case  when lwmission.ActivityStatus <> '3' and  lwmission.StandEndDate is not null then round((1 - calworktime(substr(to_char(SysDate,'YYYY-MM-DD HH24:MI:SS'),1,10),substr(to_char(SysDate,'YYYY-MM-DD HH24:MI:SS'),12,8), lwmission.StandEndDate,lwmission.StandEndTime) / calworktime(lwmission.InDate, lwmission.InTime,lwmission.StandEndDate, lwmission.StandEndTime)) * 100,2) else 0 end))) >= lwmission.sqlpriorityid then lwmission.sqlpriorityid "
                      +" else (select priorityid from lwpriority where range = (select min(range) from lwpriority where range > (case when lwmission.ActivityStatus <> '3' and lwmission.StandEndDate is not null then round((1 - calworktime(substr(to_char(SysDate,  'YYYY-MM-DD HH24:MI:SS'), 1, 10), substr(to_char(SysDate, 'YYYY-MM-DD HH24:MI:SS'), 12, 8), lwmission.StandEndDate, lwmission.StandEndTime) / calworktime(lwmission.InDate, lwmission.InTime, lwmission.StandEndDate, lwmission.StandEndTime)) * 100, 2) else 0 end))) end) ";
var strSQLDegreePart2 = " as degreeofemergency ";
                   
//      left join lwactivity on LWMission.ActivityID=lwactivity.ActivityID

/**
 * InstanceName  MulLine��������  
 * functionid  ����ID��Ӧ(select * from ldcode where codetype='functionid'�ļ�¼)
 * whereArray  where������ ��λ�����һ�� ������.�ֶ���    �ڶ��� ��ֵ   ����������[lwmission.ActivityID ,"000001098"]
 * radioOrChk  ��/��ѡ��  ����Ϊ�� ��Ĭ��Ϊ ��ѡ��
 * boxEventFuncName  ѡ��һ����¼��ĺ���
 * classversion ����Ϊ�գ�1�����˹����أ�2�����������أ� Ĭ��Ϊ���˹����� (���Զ��� �磺3 �ڶ������˹����أ���ѯSQL��1��ͬ) 
 * degreeofemergency  ����Ϊ�� ������ ��Ϊ1ʱ��ѯ�������ȣ�Ϊ0ʱ����ѯ�� Ĭ����Ϊ����ѯ
 */
function  initGridQuery(InstanceName,functionid,whereArray ,radioOrChk,boxEventFuncName,classversion ,degreeofemergency)
{   
	tInstanceName=InstanceName;
	tfunctionid=functionid;
    try
    {	
    	//��ʼ��ҳ���йصĶ���
	    initPage(InstanceName);
	   //����FunctionIDƴд��ѯ���where��������Ĳ��֣�����muline���������
	    queryActivityID(functionid , classversion ,degreeofemergency);
	   //ƴд��ѯSQL��where��������
	    makeSQLCondition(whereArray��, functionid ,degreeofemergency);
    	//��ʼ��muline���� 
	   initInstanceName(InstanceName,radioOrChk,boxEventFuncName,degreeofemergency);
	    //ִ��sql��queryModal��MulLine����
	   easyQueryClickOneself(InstanceName);
    }
    catch(ex)
    {
    	alert("MuLineInit.js�ļ����� ��"+ex);
    }
}
function initPage(InstanceName)
{
	try{		
		eval('turnPage_'+tInstanceName +'= new turnPageClass()');
		document.getElementById("div"+InstanceName).innerHTML="<table class=common><tr class=common><td><span id='span"+InstanceName+"'> </span><br/></td></tr></table>" +
				"<INPUT VALUE='��  ҳ' class=cssButton TYPE=button onclick='turnPage_"+InstanceName+".firstPage();'>" +
				"<INPUT VALUE='��һҳ' class=cssButton TYPE=button onclick='turnPage_"+InstanceName+".previousPage();'>" +
				"<INPUT VALUE='��һҳ' class=cssButton TYPE=button onclick='turnPage_"+InstanceName+".nextPage();'>" +
				"<INPUT VALUE='β  ҳ' class=cssButton TYPE=button  onclick='turnPage_"+InstanceName+".lastPage();'>";
	}
	catch(ex)
	{
		alert("������Ϣ    "+ex.message);
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
	mySql0.setResourceName("common.javascript.MulLineInitSql"); //ָ��ʹ�õ�properties�ļ���
	mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
	mySql0.addSubPara(classversion);//ָ������Ĳ���
	mySql0.addSubPara(functionid);//ָ������Ĳ���
	var tableSQL=mySql0.getString();
     
     var tableSQLArry=easyExecSql(tableSQL);
     if( tableSQLArry != null && tableSQLArry !="" )
     {
    	 for (var i=0;i<tableSQLArry.length;i++)
         {  
        	var tablename=tableSQLArry[i][0];
        	var linkcondition=tableSQLArry[i][1];
        	// ���������lwmission�����ҵ���ֶ�  ���������
        	if(tablename.toLowerCase()=="lwmission")
        	{
        		
        	}
        	// ����ҵ���
        	else
        	{
        		strSQLTablePart=strSQLTablePart+" left join "+tablename+" on "+linkcondition;
        	}       	
//        	var fieldSQL=" select ld.fieldcode, (select lw.fieldcodename from ldwfbusifield lw where lw.businesstable = ld.businesstable and lw.fieldcode = ld.fieldcode), colwidth,isshow from ldwfmulline ld where 1=1 and ld.functionid = '"+functionid+"' and ld.businesstable='"+tablename+"' order by ld.colserialno,ld.fieldcode ";
            
        	var sqlid1="MulLineInitSql1";
        	var mySql1=new SqlClass();
        	mySql1.setResourceName("common.javascript.MulLineInitSql"); //ָ��ʹ�õ�properties�ļ���
        	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        	mySql1.addSubPara(functionid);//ָ������Ĳ���
        	mySql1.addSubPara(tablename);//ָ������Ĳ���
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
    	 alert("���ݻ�ڵ�ID��ѯҵ������");
    	 return false;
     }
     
/**     //�����ѯ�������decode�Ӿ���������־������
     if(FunctionID=="")
     {
    	 wholeArry.push(["������ʾ���е����� "," ����ʾ���п�",   , �Ƿ���ʾ]);
    	 strSQLResultPart = strSQLResultPart+"  ������ӵĲ�ѯ���sql �������˸�ʽ   ����.�ֶ��� ";
     }
*/     
     
     wholeArry.push(["MissionID","100px",100,3],["SubMissionID","50px",100,3],["ProcessID","50px",100,3],["ActivityID","50px",100,3]);
     // ��ѯ�������  ����� missionid��submissionid �� processid�� activityid
     strSQLResultPart = strSQLResultPart+" lwmission.missionid,lwmission.submissionid,lwmission.processid,lwmission.activityid ";
     
     if(degreeofemergency == "1")
     {
         // ��ѯ������� ����Ͻ�����
         strSQLResultPart = strSQLResultPart +" ,"+ strSQLDegreePart1 + strSQLDegreePart2;
         // ��ѯ�������� �����ȵ���ɫ��ʶID
         strSQLResultPart = strSQLResultPart + ",(select colorid from lwpriority  where priorityid = "+ strSQLDegreePart1+" )";
         wholeArry.push(["������","80px",100,0],["colorid","80px",100,3]);
     }
}
function makeSQLCondition(whereArray ,functionid ,degreeofemergency)
{   
	strSQLWherePart =strSQLWherePart +" and lwactivity.functionid ='"+functionid+"' ";
	var newwhereArray=whereArray;
	if (newwhereArray == null)
	{
		alert("����Ĳ�ѯ����Ϊ�գ�");
		return false;
	}
	for(var i=0;i<newwhereArray.length;i++)
	{
		strSQLWherePart=strSQLWherePart +" and "+newwhereArray[i][0]+"= '"+newwhereArray[i][1]+"' ";
	}
/** where ���������޷�ͨ����ά���鴫�ݹ���ʱ 
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
    	  eval(InstanceName+'.canSel = 1');//��ѡ��   	     
      }
      else
      {   
    	  eval(InstanceName+'.canChk = 1');//��ѡ��    
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
      alert("�쳣 "+ex);
    }  
}

function easyQueryClickOneself()
{ 
    var strSQL=strSQLResultPart+strSQLTablePart+strSQLWherePart;	   
//	prompt('',strSQL);
	eval(' turnPage_'+tInstanceName+'.queryModal(strSQL, eval(tInstanceName))');	
}
