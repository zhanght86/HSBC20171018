
//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var arrDataSet;
var k = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

var tDate ;
//�ύ�����水ť��Ӧ����
function signPol() {
	if (PublicWorkPoolGrid.mulLineCount == 0) {
		alert("����ǩ��ǰѡ��һ�ݱ���");
		return;
	}

	var i = 0;
	var showStr = "���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,
			//"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

 
	//document.all("signbutton").disabled = true;
	//showSubmitFrame(mDebug);
	lockScreen('lkscreen');
	document.getElementById("fm").submit(); //�ύ 
}


//�ɷѴ߰��ѯ__��ʱע��
function UrgPayQuery()
{ 
  var checkedRowNum = 0 ;
  var rowNum = PolGrid. mulLineCount ; 
  var count = -1;
  for ( var i = 0 ; i< rowNum ; i++ )
  { 
  	if( checkedRowNum > 1)
      break;
  	if(PolGrid.getChkNo(i)) 
  	{
  	   checkedRowNum = checkedRowNum + 1;
       count = i;
    }
  }  
  if(checkedRowNum == 1)
  {
  	var cProposalNo = PolGrid.getRowColData(count,1,PolGrid);//Ͷ������
  	//alert(cProposalNo);
  	window.open("../uw/OutTimeQueryMain.jsp?ProposalNo1="+cProposalNo,"",sFeatures);
  }
  if(checkedRowNum == 2 || checkedRowNum == 0)
  {			
	alert("��ֻѡ��һ��Ͷ�������нɷѴ߰��ѯ!");  	
  }
  
}

//���ɷѴ߰�֪ͨ��_��ʱע��,ǰ̨����̨�������ʵ��.ֻ�Ǳ���δ����.��MS��������������
function SendUrgPay()
{
  var checkedRowNum = 0 ;
  var rowNum = PolGrid. mulLineCount ; 
  var count = -1;
  for ( var i = 0 ; i< rowNum ; i++ )
  { 
  	if( checkedRowNum > 1)
      break;
  	if(PolGrid.getChkNo(i))
  	 {
  	   checkedRowNum = checkedRowNum + 1;
       count = i;
      }
  } 
  if(checkedRowNum == 1)
  {
  	var cProposalNo = PolGrid.getRowColData(count,1,PolGrid);//Ͷ������
        cOtherNoType="00"; //����Ͷ�������պ�������
        cCode="15";        //�ɷѴ߰�֪ͨ�鵥������  
   if (cProposalNo != "")
   {
	  	//showModalDialog("../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");	  
		var urlStr1 = "../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=20+"cm";      //�������ڵĿ��; 
	var iHeight=10+"cm";     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   }
  }
  if(checkedRowNum == 2 || checkedRowNum == 0)
  {			
	alert("��ֻѡ��һ��Ͷ�������з��ɷѴ߰�֪ͨ��!");  	
  }
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
 unlockScreen('lkscreen');  
  //content.
  document.all("signbutton").disabled=false;

  if (FlagStr == "Fail" )
  {   
	//if(content.length>1480)
	//{
	  //content="��̨��������ʾ��Ϣ���࣬�ѳ��������ʾ��Χ��������ύ��¼����(����18����������)";	
	//} 
	 alert(content);
//	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ; 
//    showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:800px;dialogHeight:450px");   
  }
  else
  { 
  	
	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ; 
    //showModelessDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:550px;dialogHeight:350px");   
	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;resizable:1;dialogWidth:800px;dialogHeight:450px");   
 	//initForm();
	easyQueryClick();
    //ִ����һ������
  }
}

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

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
// ��ѯ��ť
function easyQueryClick()
{
	initPolGrid();
	k++;
	
	var year = new Date().getFullYear();
	var month = new Date().getMonth()+ 1;
	var inputDay = fm.AppntCompDay.value;
	var tDay = new Date().getDate();
	var day = tDay;
	var strOperate="like";
	tDate = year+"-"+month+"-"+day;
	
	var tScanDate = "";
	var tSCanTime = "";
	//
	if(document.all('ScanDate').value!=null&&document.all('ScanDate').value!="")
	{
		tScanDate = document.all('ScanDate').value;
		tSCanTime = "00:00:00";
		if(document.all('SCanTime').value!=null&&document.all('SCanTime').value!="")
		{
			 tSCanTime = document.all('SCanTime').value;
		}
		tScanDate = tScanDate + " " + tSCanTime;
	}
	
	    var addStr1 = "  ";
	    var addStr2 = "  ";
	    var addStr3 = " ";
	    var addStr4 = "  ";
	    var sqlid1="ProposalSignSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ProposalSignSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
//		mySql1.addSubPara(k);//ָ������Ĳ���
//		mySql1.addSubPara(k);//ָ������Ĳ���
		mySql1.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.AppntCompDay.value);//ָ������Ĳ���
		mySql1.addSubPara(comcode);//ָ������Ĳ���
//	    var strSql =mySql1.getString();	
	
//	var strSql = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp5,lwmission.MissionProp6,lwmission.MissionProp7,LWMission.MissionID ,LWMission.SubMissionID,LWMission.missionprop3 from lwmission where "+k+"="+k
//	         + " and LWMission.ProcessID = '0000000003' " 
//             + " and LWMission.ActivityID = '0000001150' " 
//             + getWherePart('lwmission.MissionProp1','ContNo',strOperate)
//			 + getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//			 + getWherePart('lwmission.MissionProp3','AgentCode',strOperate)
//			 + getWherePart('lwmission.MissionProp7','ManageCom',strOperate)
//			 + getWherePart('lwmission.MissionProp6','AppntCompDay',' = ')
//			 + " and LWMission.MissionProp7 like '" + comcode + "%%'";  //����Ȩ�޹�������	
			 if(document.all("SaleChnl").value!=""){
			 	addStr1 = " and exists(select 'x' from lccont where lccont.contno = lwmission.missionprop1 and lccont.salechnl='"+document.all("SaleChnl").value+"')";
			 }
			 
			 if(document.all('RiskCode').value!="")
			 {
			 	  addStr2 = " and exists (select '1' from lcpol where contno=lwmission.missionprop1 and riskcode= '"+document.all('RiskCode').value+"')";
			 	}
			 
			 if(document.all('EnterAccFlag').value=="Y")
			 {
			 	 	 addStr3 = " and exists (select '1' from ljtempfee where otherno=lwmission.missionprop1 and enteraccdate is not null and confflag='0' )";
			 }
			 else if(document.all('EnterAccFlag').value=="N")
			 {
			 	addStr3 = " and not exists (select '1' from ljtempfee where otherno=lwmission.missionprop1 and enteraccdate is not null and confflag='0' )";
			 }	
			 if(tScanDate!=null&&tScanDate!="")
			 {
			 	 addStr4 = " and '"+tScanDate+"'>=(select (case when min(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)) is not null then min(concat(concat(to_char(makedate,'yyyy-mm-dd'),' '),maketime)) else 'NOSCAN' end) from es_doc_main where doccode=lwmission.missionprop1) ";
			 }
			 	
//			 strSql=strSql+ " order by lwmission.MissionProp6 asc ";
			
		//	prompt('1',strSql);
	//��ѯSQL�����ؽ���ַ���
  mySql1.addSubPara(addStr1);//ָ������Ĳ���
  mySql1.addSubPara(addStr2);//ָ������Ĳ���
  mySql1.addSubPara(addStr3);//ָ������Ĳ���
  mySql1.addSubPara(addStr4);//ָ������Ĳ���
  var strSql =mySql1.getString();
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	
    alert("δ��ѯ���������������ݣ�");
     return false;
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 20 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  //showCodeName();
}

function easyQueryClick3()
{
  initPolGrid();
  	k++;
 
		 var year = new Date().getFullYear();
		 var month = new Date().getMonth()+ 1;
		 var inputDay = fm.AppntCompDay.value;
		 var tDay = new Date().getDate();
		 var day = tDay;
		 var strOperate="like";
		 tDate = year+"-"+month+"-"+day;
		 
		var sqlid2="ProposalSignSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.ProposalSignSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(k);//ָ������Ĳ���
		mySql2.addSubPara(k);//ָ������Ĳ���
		mySql2.addSubPara(fm.ContNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql2.addSubPara(fm.AppntCompDay.value);//ָ������Ĳ���
		mySql2.addSubPara(comcode);//ָ������Ĳ���
	    var strSql =mySql2.getString();	
		 
//	var strSql = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp5,lwmission.MissionProp6,lwmission.MissionProp7,LWMission.MissionID ,LWMission.SubMissionID from lwmission where "+k+"="+k
//	         + " and LWMission.ProcessID = '0000000003' " 
//             + " and LWMission.ActivityID = '0000001150' " 
//             + getWherePart('lwmission.MissionProp1','ContNo',strOperate)
//			 + getWherePart('lwmission.MissionProp2','PrtNo',strOperate)
//			 + getWherePart('lwmission.MissionProp3','AgentCode',strOperate)
//			 + getWherePart('lwmission.MissionProp7','ManageCom',strOperate)
//			 + getWherePart('lwmission.MissionProp6','AppntCompDay',' < ','4')
//			 + " and LWMission.MissionProp7 like '" + comcode + "%%'";  //����Ȩ�޹�������	
//			 + " order by lwmission.MissionProp6 asc "
//					 ;
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {  	
   
     return "";
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 20 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql  
  
 
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);


}

function easyQueryClick2()
{
  initPolGrid();
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
  showInfo.focus();

		var sqlid3="ProposalSignSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.ProposalSignSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(comcode);//ָ������Ĳ���
		mySql3.addSubPara(fm.ProposalNo.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.RiskCode.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.PrtNo.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.ManageCom.value);//ָ������Ĳ���
		mySql3.addSubPara(fm.AgentCode.value);//ָ������Ĳ���
		mySql3.addSubPara(comcode);//ָ������Ĳ���
	    var strSql =mySql3.getString();	


	// ��дSQL���
//  var strSql = "select ProposalNo,PrtNo,RiskCode,AppntName,InsuredName,UWDate from LCPol where 1=1 "
//				 + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+comcode+"','Ph')=1 "
//				 + "and AppFlag='0' "						// Ͷ��״̬				
//				 + "and ApproveFlag='9' "					// �Ѿ�����
//				 + "and UWFlag in ('9','4') "			    // �˱�ͨ��
//				 + "and GrpPolNo='00000000000000000000' "	// ����Ͷ����
//				 + "and ContNo='00000000000000000000' "		// ����Ͷ����
//				 + getWherePart( 'ProposalNo' )
//				 + getWherePart( 'ManageCom','ManageCom', 'like' )
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'RiskCode' )
//				 + getWherePart( 'PrtNo' )
//				 + "and PrtNo in (select PrtNo from lcpol where  1=1 " 			
//				 + "and ApproveFlag='9' "					// �Ѿ�����
//				 + "and UWFlag in ('9','4') "			    // �˱�ͨ��
//				 + "and GrpPolNo='00000000000000000000' "	// ����Ͷ����
//				 + "and ContNo='00000000000000000000' "		// ����Ͷ����
//				 + getWherePart( 'ManageCom','ManageCom', 'like' )
//				 + getWherePart( 'AgentCode' )
//				 + " and PolNo=MainPolNo )"	 
//				 + " and PolNo=MainPolNo "  //����
//				 + " and ManageCom like '" + comcode + "%%'"
//				 + " Order by UWDate";

	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	showInfo.close();
    alert("δ��ѯ���������������ݣ�");
     return false;
  }
  
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 20 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSql 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  showInfo.close();
  //showCodeName();
}

//���׽�֪ͨ��
function SendFirstNotice()
{

  cOtherNoType="00"; //������������
  cCode="07";        //��������
  var checkedRowNum = 0 ;
  var rowNum = PolGrid. mulLineCount ; 
  var count = -1;
  
  for ( var i = 0 ; i< rowNum ; i++ )
  { 
  	if( checkedRowNum > 1)
      break;
  	if(PolGrid.getChkNo(i)) 
  	{
  	   checkedRowNum = checkedRowNum + 1;
       count = i;
    }
  }  
  if(checkedRowNum == 1)
  {
  	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
  	var cProposalNo = PolGrid.getRowColData(count,1,PolGrid);//Ͷ������
  	//showModalDialog("../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode,window,"status:no;help:0;close:0;dialogWidth=20cm;dialogHeight=10cm");
	
	var urlStr1 = "../uw/UWSendPrintMain.jsp?ProposalNo1="+cProposalNo+"&OtherNoType="+cOtherNoType+"&Code="+cCode
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=20+"cm";      //�������ڵĿ��; 
	var iHeight=10+"cm";     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr1,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
  	showInfo.close();
  }
  else
  {
  	alert("����ѡ��һ�����������ڽɷ�֪ͨ��!");
  }
}

/*************************************************************
 *  ��ѯƴ������
 *  ����  ��  fieldName -- �ֶ�����
 *			 controlName -- �ؼ�����
 *			 strOperate -- ������
 *			 type -- �ֶ�����( 0:�ַ��͡�1:������  3:date )
 *  ����ֵ��  ƴ�õĴ�
 *************************************************************
 */
function getWherePart( fieldName, controlName, strOperate, fieldType )
{
	var strWherePart = "";
	var value = "";
	if( controlName == "" || controlName == null ) controlName = fieldName;
	value = eval( "fm." + trim( controlName ) + ".value" );
	if( value == "" || value == null ) return strWherePart;
	if( fieldType == null || fieldType == "" ) fieldType = "0";
	if( strOperate == "" || strOperate == null ) strOperate = "=";
	if( fieldType == "0" )	// 0:�ַ���
	{
		if(strOperate == "like")
		{
			strWherePart = " and " + trim( fieldName ) + " like '" + trim( value ) + "%25'";
		}
		else
		{
			strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + "'" + trim( value ) + "'";
		}
	}	
	if( fieldType == "1" )	// 1:������
	{
		strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + trim( value ) + " ";
	}
	if(fieldType == "3") //3:date
	{
		strWherePart = " and "+ trim( fieldName )+ trim(strOperate ) + "to_date('"+trim( value )+"','YYYY-MM-DD')"  + " ";	
	}
	if(fieldType == "4")//4:������
	{
			strWherePart = " and "+ trim(fieldName) + trim(strOperate)+ "trim(datediff(now() , "+trim(value)+")) ";	
			//strWherePart = " and "+fieldName +" <'"+tDate+"'";	
			
	}
	return strWherePart;
}	

function queryAgent()
{
	//alert("document.all('AgentCode').value==="+document.all('AgentCode').value);
	//if(document.all('ManageCom').value==""){
	//	 alert("����¼����������Ϣ��");
	//	 return;
	//}
  if(document.all('AgentCode').value == "")	{
  	//tongmeng 2007-09-10 modify
  	//��ѯ��������Ҫ��ѯ����������
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	  //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
	}
	if(document.all('AgentCode').value != ""){
	  var cAgentCode = fm.AgentCode.value;  //��������
    //alert("cAgentCode=="+cAgentCode);
    //���ҵ��Ա���볤��Ϊ8���Զ���ѯ����Ӧ�Ĵ������ֵ���Ϣ
    //alert("cAgentCode=="+cAgentCode);
    if(cAgentCode.length!=8){
    	return;
    }
      	//tongmeng 2007-09-10 modify
  	//��ѯ��������Ҫ��ѯ����������
	  //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
   	
		var sqlid4="ProposalSignSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.ProposalSignSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(cAgentCode);//ָ������Ĳ���
	    var strSQL =mySql4.getString();	
	
//	var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//	         + "and a.AgentCode = b.AgentCode " 
//	         + " and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='"+cAgentCode+"'";
   //alert(strSQL);
    var arrResult = easyExecSql(strSQL);
    //alert(arrResult);
    if (arrResult != null) 
    {
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
//  	  fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];
     
      //if(fm.AgentManageCom.value != fm.ManageCom.value)
      //{
      //   	
    	//    fm.ManageCom.value = fm.AgentManageCom.value;
    	//    fm.ManageComName.value = fm.AgentManageComName.value;
    	//    //showCodeName('comcode','ManageCom','AgentManageComName');  //���뺺��
    	//   
      //}
      //showContCodeName();
      //alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
     fm.AgentGroup.value="";
     alert("����Ϊ:["+document.all('AgentCode').value+"]��ҵ��Ա�����ڣ���ȷ��!");
    }
   
	}
}

function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
//  	fm.AgentCode.value = arrResult[0][0];
//  	fm.BranchAttr.value = arrResult[0][93];
//  	fm.AgentGroup.value = arrResult[0][1];
//  	fm.AgentName.value = arrResult[0][5];
//  	fm.AgentManageCom.value = arrResult[0][2];
    	fm.AgentCode.value = arrResult[0][0];
    	//fm.BranchAttr.value = arrResult[0][10];
    	//fm.AgentGroup.value = arrResult[0][1];
  	  //fm.AgentName.value = arrResult[0][3];
      //fm.AgentManageCom.value = arrResult[0][2];

  	//showContCodeName();
  	//showOneCodeName('comcode','AgentManageCom','ManageComName');

  }
}
