//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
	/**
	var strSQL = "select a.SerialNo,a.RIContNo,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),"
		+ " a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode) x,"
		+ " a.CalYear,a.ProLosAmnt from RIProLossResult a where 1=1 "
		+ getWherePart("a.SerialNo","SerialNo")
		+ getWherePart("a.ReComCode","ReComCode")
		+ getWherePart("a.CalYear","CalYear")
		;
	 strSQL = strSQL +" order by a.SerialNo desc ";
	 */
	 var mySql100=new SqlClass();
		 mySql100.setResourceName("reinsure.LRProfitLossQuerySql"); //ָ��ʹ�õ�properties�ļ���
		 mySql100.setSqlId("LRProfitLossQuerySql100");//ָ��ʹ�õ�Sql��id
		/**
		 mySql100.addSubPara(getWherePart("a.SerialNo","SerialNo"));//ָ������Ĳ���
		 mySql100.addSubPara(getWherePart("a.ReComCode","ReComCode"));//ָ������Ĳ���
		 mySql100.addSubPara(getWherePart("a.CalYear","CalYear"));//ָ������Ĳ���
*/

		 mySql100.addSubPara(fm.SerialNo.value);//ָ������Ĳ���
		 mySql100.addSubPara(fm.ReComCode.value);//ָ������Ĳ���
		 mySql100.addSubPara(fm.CalYear.value);//ָ������Ĳ���
		
	 var strSQL=mySql100.getString();
	 
	 var arrResult = new Array();
	//arrResult = easyExecSql(strSQL);
	turnPage.queryModal(strSQL,ProfitLossGrid)
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
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
		var tRow=ProfitLossGrid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"�����Ƚ���ѡ��!"+"");
  		return;
  	}
  	
  	/**
  	var strSQL = "select a.SerialNo,a.ReComCode,(select CompanyName from RIComInfo where companyno=a.Recomcode),RIContno,(select RIContName from RIBarGainInfo where RIContNo=a.RIContNo),a.CalYear,ProLosAmnt "
			+" from RIProLossResult a where a.ReComCode='"+ProfitLossGrid.getRowColData(tRow-1,4)+"' and a.CalYear='"+ProfitLossGrid.getRowColData(tRow-1,6)+"'"
  		;
  	*/
  	var mySql101=new SqlClass();
		 mySql101.setResourceName("reinsure.LRProfitLossQuerySql"); //ָ��ʹ�õ�properties�ļ���
		 mySql101.setSqlId("LRProfitLossQuerySql101");//ָ��ʹ�õ�Sql��id
		 mySql101.addSubPara(ProfitLossGrid.getRowColData(tRow-1,4));//ָ������Ĳ���
		 mySql101.addSubPara(ProfitLossGrid.getRowColData(tRow-1,6));//ָ������Ĳ���
	 var strSQL=mySql101.getString();
  	
  	
  	
  	strArray = easyExecSql(strSQL);
  	if (strArray==null){
  		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
  		return false;
  	}
  	var SerialNo=strArray[0][0];
    top.opener.fm.all('SerialNo').value 	=strArray[0][0];
    top.opener.fm.all('RIcomCode').value 	=strArray[0][1];
    top.opener.fm.all('RIcomName').value 	=strArray[0][2];
    top.opener.fm.all('ContNo').value 		=strArray[0][3];
    top.opener.fm.all('ContName').value 	=strArray[0][4];
    top.opener.fm.all('CalYear').value 		=strArray[0][5];
    top.opener.fm.all('CalResult').value	=strArray[0][6];
    
    /**
    strSQL = "select a.FactorCode,a.FactorName,(select InputType from RIProLossRela " 
    + " where ReComCode=a.ReComCode and FactorCode=a.FactorCode ), a.FactorValue "
    + " from riprolossvalue a where serialNo='"+SerialNo+"'"; 
		*/
	var  mySql102=new SqlClass();
		 mySql102.setResourceName("reinsure.LRProfitLossQuerySql"); //ָ��ʹ�õ�properties�ļ���
		 mySql102.setSqlId("LRProfitLossQuerySql102");//ָ��ʹ�õ�Sql��id
		 mySql102.addSubPara(SerialNo);//ָ������Ĳ���
	     strSQL=mySql102.getString();
	 	
		strArray = easyExecSql(strSQL);
		top.opener.IncomeGrid.clearData();
		top.opener.PayoutGrid.clearData();
		for (var k=0;k<strArray.length;k++){
			top.opener.IncomeGrid.addOne("IncomeGrid");
			top.opener.IncomeGrid.setRowColData(k,1,strArray[k][0]);
			top.opener.IncomeGrid.setRowColData(k,2,strArray[k][1]);
			top.opener.IncomeGrid.setRowColData(k,3,strArray[k][2]);
			top.opener.IncomeGrid.setRowColData(k,4,strArray[k][3]);
			top.opener.IncomeGrid.setRowColData(k,5,strArray[k][3]);
		}
		
		//strSQL = "select a.FactorCode,a.FactorName,(select InputType from RIProLossRela where ReComCode=a.ReComCode and FactorCode=a.FactorCode ), "
		//	+" a.FactorValue from riprolossvalue a where inouttype='02' and serialNo='"+SerialNo+"'";
		//strArray = easyExecSql(strSQL);
		//for (var k=0;k<strArray.length;k++){
		//	top.opener.PayoutGrid.addOne("PayoutGrid");
		//	top.opener.PayoutGrid.setRowColData(k,1,strArray[k][0]);
		//	top.opener.PayoutGrid.setRowColData(k,2,strArray[k][1]);
		//	top.opener.PayoutGrid.setRowColData(k,3,strArray[k][2]);
		//	top.opener.PayoutGrid.setRowColData(k,4,strArray[k][3]);
		//	if(strArray[k][2]=="01"){ //ϵͳ�����ֵ
		//		top.opener.PayoutGrid.setRowColData(k,5,strArray[k][3]);
		//	}
		//}
    top.close();
}

function ClosePage()
{
	top.close();
}
