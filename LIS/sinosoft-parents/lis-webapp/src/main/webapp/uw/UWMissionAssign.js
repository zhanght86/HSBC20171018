//�������ƣ�UWMissionAssign.js
//�����ܣ��˱��������
//�������ڣ�2005-5-14 16:25
//������  ��HWM
//���¼�¼��  ������    ��������     ����ԭ��/����
//            ������    2006-10-20    ����˱������������

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
//turnPage.pageLineNum = 10;
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tActivityStatus="";
var k = 0;

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQuery
 //===add====liuxiaosong===2006-10-20====start
    �޸ĺ󣬰�ѡ������Լ����ȫ������ �� �˹��˱����ͣ�
    ִ����Ӧ��SQL
 //===add====liuxiaosong===2006-10-20====end
 *  ����:��ѯ��ʾ�����Ǻ�ͬ����.��ʾ����:��ͬδ�����˹��˱�����״̬Ϊ���˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function easyQueryClickAll()
{
	// ��дSQL���
//	alert('1');
	var strSQL ="";
  if(fm.UWKind.value=="")
  {
  	alert("����ѡ��˱�����");
  	fm.UWKind.focus();
  	return false;
  }
  
   if(fm.UWKind.value!="1"&&fm.UWKind.value!="2"&&fm.UWKind.value!="3")
   {
       alert("����˱�����,��˫��ѡ����ȷ�ĺ˱�����");
       fm.UWKind.value="";
       fm.UWKind.focus();
       return false		
   }
   if(fm.ContType.value!="")
   {
   if(fm.ContType.value!="1"&&fm.ContType.value!="2")
   {
       alert("���󱣵�����,��˫��ѡ����ȷ�ı�������");
       fm.ContType.value="";
       fm.ContType.focus();
       return false		
   }
  }
//  alert(fm.UWName.value);
 
 // if(fm.UWName.value=="xqy")
 if(fm.UWKind.value=='1')
   {
   	initAllPolGrid();
   	
   	var wherePartStr = getWherePart('t.MissionProp1','PrtNo')
			//+ getWherePart('t.MissionProp1','PrtNo')
			+ getWherePart('t.MissionProp7','AppntName')
		//	+ getWherePart('t.MissionProp10','ManageCom')			
		//	+ getWherePart('t.MissionProp4','AgentCode')
			+ getWherePart('t.DefaultOperator','UserCode')
			+ getWherePart('t.ActivityID','ActivityID');
   	var sqlid915162539="DSHomeContSql915162539";
var mySql915162539=new SqlClass();
mySql915162539.setResourceName("uw.UWMissionAssignSql");//ָ��ʹ�õ�properties�ļ���
mySql915162539.setSqlId(sqlid915162539);//ָ��ʹ�õ�Sql��id
mySql915162539.addSubPara(wherePartStr);//ָ������Ĳ���
mySql915162539.addSubPara(manageCom+"%%");//ָ������Ĳ���
mySql915162539.addSubPara(manageCom+"%%");//ָ������Ĳ���
var subPart1 = " and 1=1 ";
var subPart2 = " and 1=1 ";
var subPart3 = " and 1=1 ";

   	
//     strSQL = "select t.missionprop1,t.missionprop2,t.missionprop7, "
//            + " (select codename from ldcode where codetype='uwstatus' and trim(code)=missionprop18),"
//            + " case t.ActivityID when '0000001100' "
//            + " then '����' when '0000002004' then '����' end,t.missionid,t.submissionid,t.ActivityID,"
//            +" t.defaultoperator,t.missionprop12,nvl(t.missionprop10,t.missionprop4) from lwmission t where 1=1 "
//			+ " and t.ActivityID in ('0000001100')"
//			//+ " and t.defaultoperator is not null "
//			//+ getWherePart('t.MissionProp2','ContNo')
//			+ getWherePart('t.MissionProp1','PrtNo')
//			//+ getWherePart('t.MissionProp1','PrtNo')
//			+ getWherePart('t.MissionProp7','AppntName')
//		//	+ getWherePart('t.MissionProp10','ManageCom')			
//		//	+ getWherePart('t.MissionProp4','AgentCode')
//			+ getWherePart('t.DefaultOperator','UserCode')
//			+ getWherePart('t.ActivityID','ActivityID')
//			//+ getWherePart('t.ActivityStatus','ActivityStatus')
//			+ " and (t.MissionProp10 like '"+manageCom+"%' or t.MissionProp4 like '"+manageCom+"%')" ;
		 if(fm.ManageCom.value!=null&&fm.ManageCom.value!="")
			{
				subPart1 = " and (t.MissionProp10 like '"+fm.ManageCom.value+"%' or t.MissionProp4 like '"+fm.ManageCom.value+"%')" ;
			}
		
			if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
			{
			  subPart2 = " and (t.MissionProp4 like '"+fm.AgentCode.value+"%' or t.MissionProp5 like '"+fm.AgentCode.value+"%')" ;
		  }
		  if(document.all('ActivityStatus').value!=null&&document.all('ActivityStatus').value!="")
					{
//				        subPart3 =  " and t.missionprop18='"+document.all('ActivityStatus').value+"' ";
						subPart3 =  " and exists (select 1 from lccuwmaster where contno= t.missionprop1 and uwstate ='"+document.all('ActivityStatus').value+"') ";
					}
			var subPart4 = " order by t.defaultoperator asc, t.modifydate asc,t.modifytime asc";
			mySql915162539.addSubPara(subPart1);//ָ������Ĳ���
			mySql915162539.addSubPara(subPart2);//ָ������Ĳ���
			mySql915162539.addSubPara(subPart3);//ָ������Ĳ���
			mySql915162539.addSubPara(subPart4);//ָ������Ĳ���
			strSQL=mySql915162539.getString();
      //prompt('',strSQL);
      turnPage.queryModal(strSQL, AllPolGrid);  
   }
   if(fm.UWName.value=="bq")
   {
   	initBqPolGrid();
   	
   	var wherePartStr = getWherePart('a.ActivityID','ActivityID')
          // +  getWherePart('a.MissionProp11','AppntName')
           +  getWherePart('a.MissionProp4','EdorAppName')
           +  getWherePart('a.MissionProp2','OtherNo')
           +  getWherePart('a.MissionProp1','EdorAcceptNo')
           +  getWherePart('a.ActivityStatus','ActivityStatus')
           +  getWherePart('a.MissionProp7','ManageCom','like')		
           +  getWherePart('a.DefaultOperator','UserCode');
   	var sqlid916100729="DSHomeContSql916100729";
var mySql916100729=new SqlClass();
mySql916100729.setResourceName("uw.UWMissionAssignSql");//ָ��ʹ�õ�properties�ļ���
mySql916100729.setSqlId(sqlid916100729);//ָ��ʹ�õ�Sql��id
mySql916100729.addSubPara(wherePartStr);//ָ������Ĳ���
mySql916100729.addSubPara(manageCom+"%%");//ָ������Ĳ���
strSQL=mySql916100729.getString();
   	
//   	strSQL = "select a.MissionID, a.SubMissionID, a.MissionProp1, a.MissionProp2,"
//   	       +" (select CodeName from LDCode where  1 = 1 and "
//   	       +" CodeType like '%edornotype%'  and trim(Code) = a.MissionProp3),a.MissionProp4,"
//   	       +" case  a.ActivityStatus when '1' then 'δ�˹��˱�'  when '3' then '�˱��ѻظ�' when '2' then '�˱�δ�ظ�' when '4' then '����' when '5' then '�˱�����' end,"
//           +" case a.ActivityID when '0000000005' then '����' when '0000008005' then '����' end,"
//           +" a.defaultoperator,a.MissionProp7,a.ActivityID from LWMission a where 1 = 1 and a.ProcessID = '0000000000'  "
//           + " and a.ActivityID in ('0000000005','0000008005')"
//           +  getWherePart('a.ActivityID','ActivityID')
//          // +  getWherePart('a.MissionProp11','AppntName')
//           +  getWherePart('a.MissionProp4','EdorAppName')
//           +  getWherePart('a.MissionProp2','OtherNo')
//           +  getWherePart('a.MissionProp1','EdorAcceptNo')
//           +  getWherePart('a.ActivityStatus','ActivityStatus')
//           +  getWherePart('a.MissionProp7','ManageCom','like')		
//           +  getWherePart('a.DefaultOperator','UserCode')
//           + " and a.MissionProp7 like '"+manageCom+"%'"	
//           +" order by a.defaultoperator asc,a.MissionProp1 asc, a.MakeDate asc " ;
          turnPage1.queryModal(strSQL, BqPolGrid);  
   }
   if(fm.UWName.value=="lp")
   {
   	initLpPolGrid();
   	
   	var wherePartStr = getWherePart('missionprop20' ,'ManageCom','like')  //<��ǰ����>
             + getWherePart('ActivityID','ActivityID')
             + getWherePart('MissionProp1','QCaseNo')
             + getWherePart('MissionProp4','QInsuredName')
             + getWherePart('MissionProp2','QBatNo')
             + getWherePart('ActivityStatus','ActivityStatus')
             + getWherePart('DefaultOperator','UserCode')	;
   	var sqlid916101029="DSHomeContSql916101029";
var mySql916101029=new SqlClass();
mySql916101029.setResourceName("uw.UWMissionAssignSql");//ָ��ʹ�õ�properties�ļ���
mySql916101029.setSqlId(sqlid916101029);//ָ��ʹ�õ�Sql��id
mySql916101029.addSubPara(wherePartStr);//ָ������Ĳ���
mySql916101029.addSubPara(manageCom+"%%");//ָ������Ĳ���
strSQL=mySql916101029.getString();
   	
//   	 strSQL = " select missionprop1,missionprop2, "
//	           +" case activitystatus when '1' then 'δ�˹��˱�' when '3' then '�˱��ѻظ�' "
//	           +" when '2' then '�˱�δ�ظ�'  when '4' then '�ٱ�δ�ظ�' when '5' then '�ٱ��ѻظ�' end,"
//	           +" missionprop4,"
//		         +" case missionprop5 when '0' then '���' when '1' then '�����' end,DefaultOperator,"
//		         +" missionprop20,missionid,submissionid,ActivityID, missionprop5 from lwmission where 1=1 "
//             +" and ActivityID = '0000005505' "
//             +" and processid = '0000000005'"
//            // +" and DefaultOperator ='"+fm.Operator.value+"'"
//             + getWherePart('missionprop20' ,'ManageCom','like')  //<��ǰ����>
//             + getWherePart('ActivityID','ActivityID')
//             + getWherePart('MissionProp1','QCaseNo')
//             + getWherePart('MissionProp4','QInsuredName')
//             + getWherePart('MissionProp2','QBatNo')
//             + getWherePart('ActivityStatus','ActivityStatus')
//             + getWherePart('DefaultOperator','UserCode')	
//             + " and MissionProp20 like '"+manageCom+"%'"
//             + " order by defaultoperator asc,makedate asc,maketime asc";	
             turnPage2.queryModal(strSQL, LpPolGrid);    	
   }

}

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQueryAddClick
 *  ����:����˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryAddClick()
{
	var tSel = PolGrid.getSelNo();
	var ActivityID = PolGrid.getRowColData(tSel - 1,8);
	var ContNo = PolGrid.getRowColData(tSel - 1,2);
	var PrtNo = PolGrid.getRowColData(tSel - 1,1);
	var MissionID = PolGrid.getRowColData(tSel - 1,6);
	var SubMissionID = PolGrid.getRowColData(tSel - 1,7);

	if(ActivityID == "0000001100")
	{
		window.location="./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo;
	}
	if(ActivityID == "0000002004")
	{
		window.location="./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;
	}
	if(ActivityID == "0000006004")
	{
		window.location="../askapp/AskGroupUW.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID; 
	}
}

/*********************************************************************
 *  ����˱�
 *  ����:����˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ApplyUW()
{
  if(beforUWApply()==false) return false;
	if(fm.DefaultOperator.value.length == 0)
	{
		  alert("û����������˱�ʦ���,�����Ѹ������˻ص������!");
			fm.DefaultOperator.focus();
			return;
			
//		if(!confirm("û����������˱�ʦ���,ȷ��Ҫ�Ѹ������˻ص��������?")){
//			fm.DefaultOperator.focus();
//			return;
//		  }
//	  if(tActivityStatus!="δ�˹��˱�")
//	  {
//	  	alert("�ñ���Ϊ��δ�˹��˱�״̬�������˻ص���������");
//	  	return false;
//	  }
	}
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    lockScreen('lkscreen');  
	fm.action = "./UWMissionAssignSave.jsp";
	document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  �ύ�����,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  if (FlagStr == "Fail" )
  {                 
    alert(content);
  }
  else
  { 
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //ִ����һ������
  }
  easyQueryClickAll();

}


function queryAgent()
{
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
    	fm.AgentCode.value = arrResult[0][0];
  	  //fm.AgentCode1.value = arrResult[0][0];
  }
}

function setManageCom(){
  document.all('MngCom').value =	AllPolGrid.getRowColData(AllPolGrid.getSelNo()-1, 10);
  //alert(fm.MngCom.value);
}
function afterCodeSelect(cCodeName, Field)
{
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
function getActivityID(flag)
{
	if(fm.ContType.value!="1"&&fm.ContType.value!="2")
  {
   	fm.ActivityID.value="";
  }
  if(flag=="uwtype")
  {
     if(fm.UWKind.value=="1")
     {
  	   	divBqPolGrid.style.display="none";
  	   	BQInfo.style.display="none";
  	   	divLpPolGrid.style.display="none";
  	   	LPInfo.style.display="none";
  	   	XQYInfo.style.display="";
  		  divAllPolGrid.style.display="";
  		  fm.UWName.value="xqy";
  		  if(fm.ContType.value=="1")
  		  {
  		  	fm.ActivityID.value="0000001100";
  		  }
  		  if(fm.ContType.value=="2")
  		  {
  		  	fm.ActivityID.value="0000002004";
  		  }
  	  }
     else 
     	if(fm.UWKind.value=="2")
       {  
       	 divAllPolGrid.style.display="none";
       	 XQYInfo.style.display="none";
       	 divLpPolGrid.style.display="none";
       	 LPInfo.style.display="none";
       	 BQInfo.style.display="";
       	 divBqPolGrid.style.display="";
       	 fm.UWName.value="bq";
       	 if(fm.ContType.value=="1")
  		   {
  		  	 fm.ActivityID.value="0000000005";
  		   }
  		   if(fm.ContType.value=="2")
  		   {
  		  	 fm.ActivityID.value="0000008005";
  		   }
       }
      else 
       if(fm.UWKind.value=="3")
        {
       	 	divBqPolGrid.style.display="none";
       	 	BQInfo.style.display="none";
  		    divAllPolGrid.style.display="none";
  		    XQYInfo.style.display="none";
  		    LPInfo.style.display="";
  		    divLpPolGrid.style.display="";
       	  fm.UWName.value="lp";
       	  fm.ActivityID.value="0000005505";
         }
     }
   if(flag=="contype")
   { 
     if(fm.ContType.value=="1")
     {
      	if(fm.UWKind.value=="1")
     	  {
     		  fm.ActivityID.value="0000001100";
      	}
      	if(fm.UWKind.value=="2")
       	{
     		  fm.ActivityID.value="0000000005";
     	  }
        if(fm.UWKind.value=="3")
     	  {
     			fm.ActivityID.value="0000005505";
     	  }
     }
     if(fm.ContType.value=="2")
      {
     	  if(fm.UWKind.value=="1")
     	  {
          fm.ActivityID.value="0000002004";
     	  }
     	  if(fm.UWKind.value=="2")
       	{
     	   fm.ActivityID.value="0000008005";
     	  }
        if(fm.UWKind.value=="3")
     	  {
     			fm.ActivityID.value="0000005505";
     	  }
      }
   }
}

function showmngcom()
{
	alert(fm.MngCom.value);
	}

//modified by liuning 08.01.17

function beforUWApply()
{
  if(fm.UWKind.value=="")
  {
	 alert("����ѡ��˱�����");
	 return false;
  } 
  var i=0 ;
    
  if(fm.UWKind.value=="1"&&fm.UWName.value=="xqy")
  {
  	/************************************************************************************/
  	/*******************************����Լ�˱��������***********************************/
  	/************************************************************************************/
  	
   for(i=0 ;i<AllPolGrid.mulLineCount ;i++)
  	if(AllPolGrid.getChkNo(i))
  	{
  		return;
    }
      //alert(1);
  		alert( "��ѡ��Ҫ�����Ͷ������" );
      return false;
	}
    
  if(fm.UWKind.value=="2"&&fm.UWName.value=="bq")
  { 
  	/************************************************************************************/
  	/*******************************��ȫ�˱��������***********************************/
  	/************************************************************************************/	

	 for(i=0 ;i<BqPolGrid.mulLineCount ;i++)
  	if(BqPolGrid.getChkNo(i))
  	{
  		return;
    }	 
     // alert(2);
  		alert( "��ѡ��Ҫ�����Ͷ������" );
      return false;
	 }

	 if(fm.UWKind.value=="3"&&fm.UWName.value=="lp")
   {
   	 /************************************************************************************/
  	/*******************************��������������***********************************/
  	/************************************************************************************/	
      
    for(i=0 ;i<LpPolGrid.mulLineCount ;i++)
	   if(LpPolGrid.getChkNo(i))
  	 {
  		return;
     }	  
      //alert(3);
  		alert( "��ѡ��Ҫ�����Ͷ������" );
      return false;
	 }

}		
  
//  var selno;
//  if(fm.UWKind.value=="1"&&fm.UWName.value=="xqy")
//  {
//  	/************************************************************************************/
//  	/*******************************����Լ�˱��������***********************************/
//  	/************************************************************************************/
//  	AllPolGrid.delBlankLine();
//  	if(AllPolGrid.mulLineCount==0)
//  	{
//  		alert("��������ѯ");
//  		return false;
//  	}
//	  selno = AllPolGrid.getSelNo()-1;
//	  if (selno <0)
//	  {
//	      alert("��ѡ��Ҫ�����Ͷ������");
//	      return false;
//	  }
//	  fm.MissionID.value = AllPolGrid.getRowColData(selno, 6);
//	  fm.SubMissionID.value = AllPolGrid.getRowColData(selno, 7);
//	  fm.ActivityID.value = AllPolGrid.getRowColData(selno, 8);
//	  document.all('MngCom').value =	AllPolGrid.getRowColData(selno, 10);
//	  tActivityStatus=AllPolGrid.getRowColData(selno, 4)
//	}
//	if(fm.UWKind.value=="2"&&fm.UWName.value=="bq")
//  { 
//  	/************************************************************************************/
//  	/*******************************��ȫ�˱��������***********************************/
//  	/************************************************************************************/	
//  	BqPolGrid.delBlankLine();
//  	if(BqPolGrid.mulLineCount==0)
//  	{
//  		alert("��������ѯ");
//  		return false;
//  	}
//	  selno = BqPolGrid.getSelNo()-1;
//	  if (selno <0)
//	  {
//	      alert("��ѡ��Ҫ�����Ͷ������");
//	      return false;
//  	}
//	  fm.MissionID.value = BqPolGrid.getRowColData(selno, 1);
//	  fm.SubMissionID.value = BqPolGrid.getRowColData(selno, 2);
//	  fm.ActivityID.value = BqPolGrid.getRowColData(selno, 11);
//	  document.all('MngCom').value =	BqPolGrid.getRowColData(selno, 10);
//	   tActivityStatus=BqPolGrid.getRowColData(selno, 7)
//	 }
//	 if(fm.UWKind.value=="3"&&fm.UWName.value=="lp")
//   {
//   	 /************************************************************************************/
//  	/*******************************��������������***********************************/
//  	/************************************************************************************/	
//  	 LpPolGrid.delBlankLine();
//  	 if(LpPolGrid.mulLineCount==0)
//  	 {
//  		 alert("��������ѯ");
//  		 return false;
//  	 }
//	   selno = LpPolGrid.getSelNo()-1;
//	   if (selno <0)
//	   { 
//	      alert("��ѡ��Ҫ�����Ͷ������");
//	      return;
//	   }
//	   fm.MissionID.value = LpPolGrid.getRowColData(selno, 8);
//	   fm.SubMissionID.value = LpPolGrid.getRowColData(selno, 9);
//     fm.ActivityID.value = LpPolGrid.getRowColData(selno, 10);
//     document.all('MngCom').value =	LpPolGrid.getRowColData(selno, 11);
//     tActivityStatus=LpPolGrid.getRowColData(selno,3);
//	 }
	
