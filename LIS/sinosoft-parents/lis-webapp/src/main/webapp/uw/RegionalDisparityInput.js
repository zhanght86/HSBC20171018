//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;


// ��ѯ��ť
function easyQueryClick() {   
	//���Ȳ�ѯ��ʷ�޸���Ϣ
	// ��ʼ�����
	initPolGrid();

	if((fm.ManageCom.value==""||fm.ManageCom.value==null)){
		alert("�������������");
		return false;
	}
	var tManageCom = fm.ManageCom.value;
	// ��дSQL���
	var strSQL = "";
	
//	strSQL = "select managecom,manauwtype,uwlevel, "
//			 			+ " concat(concat(to_char(makedate2,'YYYY-MM-DD') , ' ') , maketime2)"
//			 			+ " from LDUWDifferenceB "
//			 			+ " where 1=1 and managecom='"+tManageCom+"'"
//			 			+ " and difftype='0' "
//			 			+ " order by makedate2,maketime2";
	
	    var  sqlid1="RegionalDisparityInputSql0";
	 	var  mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.RegionalDisparityInputSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	 	mySql1.addSubPara(tManageCom);//ָ������Ĳ���
	     strSQL=mySql1.getString();
//	prompt("",strSQL);
	
	//��ѯSQL�����ؽ���ַ���
	turnPage.queryModal(strSQL,PolGrid);
	
	//��ѯ������ǰ��Ϣ
	//strSQL = "select othersign,comcode from ldcode where codetype='station' and code='"+tManageCom+"'";

    var  sqlid2="RegionalDisparityInputSql1";
 	var  mySql2=new SqlClass();
 	mySql2.setResourceName("uw.RegionalDisparityInputSql"); //ָ��ʹ�õ�properties�ļ���
 	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
 	mySql2.addSubPara(tManageCom);//ָ������Ĳ���
     strSQL=mySql2.getString();
	var arrResult = easyExecSql(strSQL); 
	if (arrResult != null) {
		fm.UWLevel.value = arrResult[0][0];
    	fm.UWClass.value = arrResult[0][1];
    	showOneCodeName('uwlevel','UWLevel','UWLevelName');
    	showOneCodeName('areauwclass','UWClass','UWClassName');
    } else {
    	fm.UWLevel.value = "";
    	fm.UWClass.value = "";
    }
}



//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ) {
  showInfo.close();
  if (FlagStr == "Fail" ) {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();
  } else {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();
    initForm();
  }
  unlockScreen('lkscreen');
}


function queryAgent() {
	if(document.all('AgentCode').value == "")	{
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comCode+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(document.all('AgentCode').value != ""&& document.all('AgentCode').value.length==10){
		var cAgentCode = fm.AgentCode.value;  //��������
//	   	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//		         + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentState < '03'  and a.AgentCode='"+cAgentCode+"' ";//and a.managecom='"+document.all("ManageCom").value+"'";
	    var  sqlid3="RegionalDisparityInputSql2";
	 	var  mySql3=new SqlClass();
	 	mySql3.setResourceName("uw.RegionalDisparityInputSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	 	mySql3.addSubPara(cAgentCode);//ָ������Ĳ���
	    strSQL=mySql3.getString();
	    var arrResult = easyExecSql(strSQL); 
	    //alert(arrResult);
	    if (arrResult != null) {
	    	fm.AgentGroup.value = arrResult[0][1];
	    	fm.AgentName.value = arrResult[0][3];
	    } else {
	    	fm.AgentCode.value ="";
	    	fm.AgentGroup.value ="";
	  	  	fm.AgentName.value ="";
	  	  	alert("����Ϊ:["+cAgentCode+"]��ҵ��Ա�����ڣ����߲��ڸù�������£������Ѿ���ְ����ȷ��!");
	    }
	}	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
//Ϊҵ��Ա���롢����
function afterQuery2(arrResult) {
	if(arrResult!=null) {
		fm.AgentCode.value = arrResult[0][0];
		fm.AgentGroup.value = arrResult[0][1];
		fm.AgentName.value = arrResult[0][3];
	}
}

/**
 * "�޸�"��ť
 * */
function UpdateAgent(){
	lockScreen('lkscreen');
	if(CheckData()==false){
		unlockScreen('lkscreen');
		return false;
	}
	var showStr="���ڱ�������,�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

   showInfo.focus();	document.getElementById("fm").submit();
}

function CheckData(){
	if(fm.ManageCom.value==null||fm.ManageCom.value==""){
		alert("�������벻��Ϊ�գ�");
		return false;
	}
	if(fm.UWLevel.value==null||fm.UWLevel.value==""){
		if(!confirm("���컯����Ϊ�գ��Ƿ������")){
			return false;
		}
	}
	if(fm.ManageCom.value.length==2){
		alert("���ܰ��ܹ�˾�޸ģ����ٰ����������޸ģ�");
		return false;
	}
	if(fm.ManageCom.value.length==4){
		if(!confirm("ע�⣬�����޸Ķ�������"+fm.ManageCom.value+"�µ����л����Ĳ��컯�����Ƿ������")){
			return false;
		}
	}
}