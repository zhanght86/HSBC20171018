//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm(){
 var mySql100=new SqlClass();
	 mySql100.setResourceName("reinsure.LRFeeRateQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql100.setSqlId("LRFeeRateQueryInputSql100");//ָ��ʹ�õ�Sql��id
	 /**
	 mySql100.addSubPara(getWherePart("x.X1","FeeTableNo"));//ָ������Ĳ���
	 mySql100.addSubPara(getWherePart("x.X2","FeeTableName","like"));//ָ������Ĳ���
	 */
	 mySql100.addSubPara(fm.FeeTableNo.value);//ָ������Ĳ���
	 mySql100.addSubPara(fm.FeeTableName.value);//ָ������Ĳ���
var strSQL=mySql100.getString();
 
 /**
  var strSQL = "select x.X1,x.X2,x.X3,x.X4,decode(x.X5,'1','��δ����ķ��ʱ�','��ȫ��������ʱ�'),decode(x.X6,'01','��Ч','02','δ��Ч') from (select a.FeeTableNo X1,a.FeeTableName X2,a.FeeTableType X3,(case a.FeeTableType when '01' then 'һ��' when '02' then '����' end) X4 ,(select distinct 1 from RIFeeRateTableTrace c where not exists (select * from RIFeeRateTable b where c.feetableno=b.feetableno and c.batchno=b.batchno) and c.feetableno=a.feetableno) X5,a.state X6 from RIFeeRateTableDef a ) x where 1=1"
  + getWherePart("x.X1","FeeTableNo")
  + getWherePart("x.X2","FeeTableName","like")
  ;
  strSQL = strSQL +" order by x.X1";
*/
	turnPage.queryModal(strSQL, FeeRateGrid);
  
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
		var tRow=FeeRateGrid.getSelNo();
  	if (tRow==0){
   		myAlert(""+"�����Ƚ���ѡ��!"+"");
  		return;
  	}
  	var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.LRFeeRateQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql101.setSqlId("LRFeeRateQueryInputSql101");//ָ��ʹ�õ�Sql��id
	 	mySql101.addSubPara(FeeRateGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
	var strSQL=mySql101.getString();
  /**	
  	var strSQL="select a.FeeTableNo,"
  	          +" a.FeeTableName,"
  	          +" a.FeeTableType,"
  	          +"(case a.FeeTableType when '01' then 'һ��' when '02' then '����' end),"
  	          +" a.Remark,"
  	          +" a.state,"
  	          +"(case a.state when '01' then '��Ч' when '02' then 'δ��Ч' end) "
  	          +" from RIFeeRateTableDef a where "
  	+ " a.FeeTableNo='"+FeeRateGrid.getRowColData(tRow-1,1)+"' "
  	;
  	*/
  	strArray = easyExecSql(strSQL);
  	if (strArray==null){
  		myAlert(""+"�޷�����"+","+"�����ݿ��ܸձ�ɾ��!"+"");
  		return false;
  	}
  	var deTailFlag=strArray[0][2];
    top.opener.fm.all('FeeTableNo').value 			=strArray[0][0];
    top.opener.fm.all('FeeTableName').value 		=strArray[0][1];
    top.opener.fm.all('FeeTableType').value 		=strArray[0][2];
    top.opener.fm.all('FeeTableTypeName').value =strArray[0][3];
    top.opener.fm.all('ReMark').value 					=strArray[0][4];
    top.opener.fm.all('State').value 					  =strArray[0][5];
    top.opener.fm.all('stateName').value 			  =strArray[0][6];
		
	var mySql102=new SqlClass();
		mySql102.setResourceName("reinsure.LRFeeRateQueryInputSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql102.setSqlId("LRFeeRateQueryInputSql102");//ָ��ʹ�õ�Sql��id
	 	mySql102.addSubPara(FeeRateGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
	    strSQL=mySql102.getString();
	/**	
		strSQL = " select a.FeeClumnName,a.FeeClumnDataCode,(case a.FeeClumnType when 'A01' then '�ַ��͹̶�ֵ' when 'A02' then '�ַ�������ֵ' when 'N01' then '��ֵ�͹̶�ֵ' when 'N02' then '��ֵ������ֵ' end),a.FeeClumnType,a.TagetClumn,a.TagetDLClumn,a.TagetULClumn "
		+ " from RIFeeRateTableClumnDef a where a.FeeTableNo='"+FeeRateGrid.getRowColData(tRow-1,1)+"' order by a.feeclumnno ";
		*/
		strArray = easyExecSql(strSQL);
		top.opener.TableClumnDefGrid.clearData();
		if (strArray!=null){
			for (var k=0;k<strArray.length;k++){
				top.opener.TableClumnDefGrid.addOne("TableClumnDefGrid");
				top.opener.TableClumnDefGrid.setRowColData(k,1,strArray[k][0]);
				top.opener.TableClumnDefGrid.setRowColData(k,2,strArray[k][1]);
				top.opener.TableClumnDefGrid.setRowColData(k,3,strArray[k][2]);
				top.opener.TableClumnDefGrid.setRowColData(k,4,strArray[k][3]);
				top.opener.TableClumnDefGrid.setRowColData(k,5,strArray[k][4]);
				top.opener.TableClumnDefGrid.setRowColData(k,7,strArray[k][5]);
				top.opener.TableClumnDefGrid.setRowColData(k,9,strArray[k][6]);
			}
		}
    top.close();
}

function ClosePage()
{
	top.close();
}
