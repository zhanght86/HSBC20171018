//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm(){
 var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.LRAccRDQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("LRAccRDQueryInputSql100");//ָ��ʹ�õ�Sql��id
		/**
		mySql100.addSubPara(getWherePart("AccumulateDefNO","AccumulateDefNO"));//ָ������Ĳ���
		mySql100.addSubPara(getWherePart("AccumulateDefName","AccumulateDefName","like"));//ָ������Ĳ���
*/
		mySql100.addSubPara(fm.AccumulateDefNO.value);//ָ������Ĳ���
		mySql100.addSubPara(fm.AccumulateDefName.value);//ָ������Ĳ���
var    strSQL=mySql100.getString();
 
 /**
  var strSQL = "select AccumulateDefNO,AccumulateDefName,DeTailFlag,AccumulateMode,State,decode(state,'01','��Ч','δ��Ч') from RIAccumulateDef where 1=1 "
  + getWherePart("AccumulateDefNO","AccumulateDefNO")
  + getWherePart("AccumulateDefName","AccumulateDefName","like")
  ;
  */
  //strSQL = strSQL +" order by AccumulateDefNO";
  var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	turnPage.queryModal(strSQL, ReComGrid)
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content ){
  //showInfo.close();
  if (FlagStr == "Fail" ){
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+encodeURI(encodeURI(content));
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  }
  else{
  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm(){
  try{
	  initForm();
  }
  catch(re){
  	myAlert(""+"��Proposal.js"+"-->"+"resetForm�����з����쳣:��ʼ���������!"+"");
  }
}

//ȡ����ť��Ӧ����
function cancelForm(){
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit(){
  //��Ӳ���
}

//Click�¼������������ͼƬʱ�����ú���
function addClick(){
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick(){
  //����������Ӧ�Ĵ���
  myAlert("update click");
}

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick(){
  //����������Ӧ�Ĵ���
	myAlert("query click");
	  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick(){
  //����������Ӧ�Ĵ���
  myAlert("delete click");
}

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true"){
    cDiv.style.display="";
  }
  else{
    cDiv.style.display="none";
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug){
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData(){
		var tRow=ReComGrid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"�����Ƚ���ѡ��!"+"");
  		return;
  	}
  	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRAccRDQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("LRAccRDQueryInputSql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(ReComGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
	var strSQL=mySql101.getString();
  	
  	/**
  	var strSQL = "select AccumulateDefNO,AccumulateDefName,DeTailFlag,case DeTailFlag when '01' then '�������ּ���' when '02' then '�������μ���' end,"
		+" AccumulateMode,case AccumulateMode when '01' then '���˵���ͬ�ۼ�' when '02' then '���˶��ͬ�ۼ�' when '03' then '���˶��ͬ�ۼ�' end,"
		+" RiskAmntFlag,case RiskAmntFlag when '01' then '����Ҫת��' when '02' then '��Ҫת��' end ,"
		+" State,decode(state,'01','��Ч','δ��Ч'),standbyflag,decode(standbyflag,'01','�ۼ�','02','���ۼ�','') "
  	+" from RIAccumulateDef where 1=1 and AccumulateDefNO='"+ReComGrid.getRowColData(tRow-1,1)+"'"
  	;
  	*/
  	strArray = easyExecSql(strSQL);
  	if (strArray==null){
  		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
  		return false;
  	}
  	var deTailFlag=strArray[0][2];
    top.opener.fm.all('AccumulateDefNO').value 		=strArray[0][0];
    top.opener.fm.all('AccumulateDefName').value 	=strArray[0][1];
    top.opener.fm.all('DeTailFlag').value 				=strArray[0][2];
    top.opener.fm.all('DeTailFlagName').value 		=strArray[0][3];
    top.opener.fm.all('AccumulateMode').value 		=strArray[0][4];
    top.opener.fm.all('AccumulateModeName').value =strArray[0][5];
    top.opener.fm.all('RiskAmntFlag').value				=strArray[0][6];
    top.opener.fm.all('RiskAmntFlagName').value		=strArray[0][7];
    top.opener.fm.all('State').value							=strArray[0][8];
    top.opener.fm.all('StateName').value					=strArray[0][9];
    top.opener.fm.all('StandbyFlag').value					=strArray[0][10];
    top.opener.fm.all('StandbyFlagName').value					=strArray[0][11];
    
    var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRAccRDQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql102.setSqlId("LRAccRDQueryInputSql102");//ָ��ʹ�õ�Sql��id
		mySql102.addSubPara(ReComGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
	    strSQL=mySql102.getString();
    
   /**
    strSQL = "select a.AssociatedCode,a.AssociatedName,(decode(a.StandbyFlag,'01','�ۼ�','02','���ۼ�')),a.StandbyFlag from RIAccumulateRDCode a"
			+ " where 1=1 and a.AccumulateDefNO='"+ReComGrid.getRowColData(tRow-1,1)+"'";
	*/
		strArray = easyExecSql(strSQL);
		top.opener.RelateGrid.clearData();
		top.opener.DutyGrid.clearData();
		if(deTailFlag=="01"){
				top.opener.fm.DeTailType.value='RISK';
				top.opener.divCertifyType1.style.display='';
				top.opener.divCertifyType2.style.display='none';
		}else{
				top.opener.fm.DeTailType.value='DUTY';
				top.opener.divCertifyType1.style.display='none';
				top.opener.divCertifyType2.style.display='';
		}
		
		if (strArray!=null){			
			if(deTailFlag=="01"){
				for (var k=0;k<strArray.length;k++){
					top.opener.RelateGrid.addOne("RelateGrid");
					top.opener.RelateGrid.setRowColData(k,1,strArray[k][0]);
					top.opener.RelateGrid.setRowColData(k,2,strArray[k][1]);
					top.opener.RelateGrid.setRowColData(k,3,strArray[k][2]);
					top.opener.RelateGrid.setRowColData(k,4,strArray[k][3]);
				}
			}else{
				for (var k=0;k<strArray.length;k++){
					top.opener.DutyGrid.addOne("DutyGrid");
					top.opener.DutyGrid.setRowColData(k,1,strArray[k][0]);
					top.opener.DutyGrid.setRowColData(k,2,strArray[k][1]);
					top.opener.DutyGrid.setRowColData(k,3,strArray[k][2]);
					top.opener.DutyGrid.setRowColData(k,4,strArray[k][3]);
				}
			}
		}
    top.close();
}

function ClosePage()
{
	top.close();
}
