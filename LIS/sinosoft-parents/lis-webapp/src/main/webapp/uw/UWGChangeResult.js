var showInfo;

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;         
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;     
  }
  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  //alert(parent.opener.name);
  //ctrlButtonDisabled(fm.GrpContNo.value);
  parent.close();
}

//��ʾ���ռƻ��������
function saveChangeResult()
{
	var cContNo = fm.GrpContNo.value; //�ŵ���
	var cChangeResult = fm.ChangeIdea.value; //�˱�Ҫ��
	var cEdorNo=fm.EdorNo.value; //������
	var cMissionID=fm.MissionID.value; //����ID
	var cPrtNo=fm.PrtNo.value;//ӡˢ��
	
	if (trim(cChangeResult) == "")
	{
		alert("û��¼�����!");
		fm.ChangeIdea.focus();
		return;
	}
	
	var sqlid824101103="DSHomeContSql824101103";
var mySql824101103=new SqlClass();
mySql824101103.setResourceName("uw.UWGChangeResultInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824101103.setSqlId(sqlid824101103);//ָ��ʹ�õ�Sql��id
mySql824101103.addSubPara(cContNo);//ָ������Ĳ���
mySql824101103.addSubPara(cMissionID);//ָ������Ĳ���
var sSQL=mySql824101103.getString();

//	var sSQL = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + cContNo +"'"	
//				 + " and LWMission.ActivityID = '0000002301'"
//				 + " and LWMission.ProcessID = '0000000004'"
//				 + " and LWMission.MissionID = '" + cMissionID +"'";
//==============add========liuxiaosong==========2006-11-15==============start=============

   //�������Ų�Ϊ�գ���Ϊ��ȫҵ��
   //2301��missionprop1ΪprtNo����������Լ��prtno��grpcontnoһ������
  
   if(!(cEdorNo=="" || cEdorNo==null || cEdorNo=="null")){
   	
   	var sqlid824101217="DSHomeContSql824101217";
var mySql824101217=new SqlClass();
mySql824101217.setResourceName("uw.UWGChangeResultInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824101217.setSqlId(sqlid824101217);//ָ��ʹ�õ�Sql��id
mySql824101217.addSubPara(cPrtNo);//ָ������Ĳ���
mySql824101217.addSubPara(cMissionID);//ָ������Ĳ���
sSQL=mySql824101217.getString();
   	
//   		sSQL = "select LWMission.SubMissionID from LWMission where 1=1"
//				 + " and LWMission.MissionProp1 = '" + cPrtNo +"'"	
//				 + " and LWMission.ActivityID = '0000002301'"
//				 + " and LWMission.ProcessID = '0000000000'"
//				 + " and LWMission.MissionID = '" + cMissionID +"'";
   }
//==============add========liuxiaosong==========2006-11-15==============start=============
   //alert(sSQL);

   var arr=easyExecSql(sSQL);
   if(arr==null)
   {
      alert("��ѯ�ŵ�����Լ���ͺ˱�Ҫ��֪ͨ��ڵ�����ʧ��!");	
      return;
   }
   fm.SubMissionID.value=arr[0][0];

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 
  	fm.action = "./GrpUWChangPlanChk.jsp";
  	fm.submit(); //�ύ
}