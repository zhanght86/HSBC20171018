//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;

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

//��ʾfrmSubmit��ܣ���������
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

//������־�Ĳ�ѯ����
function getQueryDetail_B()
{
}

// ��ѯ��ť
function easyQueryClick()
{    
	// ��ʼ�����
	divLog2.style.display="none";
	initLogGrid();	
	// ��дSQL���
	var strSQL = "";
	
//	strSQL = "select OtherNo,OtherNoType,PrtNo,MakeDate,ModifyDate from LCDelPolLog where 1=1 "
//				 
//				 + getWherePart( 'OtherNo' )
//				 + getWherePart( 'OtherNoType' )
//				 + getWherePart( 'IsPolFlag' )
//				 + getWherePart( 'PrtNo' )
//				 + getWherePart( 'ManageCom' )
//				 + getWherePart( 'Operator' )
//				 + getWherePart( 'MakeDate' )
//				 + getWherePart( 'MakeTime' )
//				 + getWherePart( 'ModifyDate' )
//				 + getWherePart( 'ModifyTime' )
//				 ;
	var mySql1=new SqlClass();
    mySql1.setResourceName("logmanage.LogQuerySql");
    mySql1.setSqlId("LogQuerySql1");
    mySql1.addSubPara(window.document.getElementsByName(trim('OtherNo'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('OtherNoType'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('IsPolFlag'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('PrtNo'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('Operator'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('MakeDate'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('MakeTime'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('ModifyDate'))[0].value);
    mySql1.addSubPara(window.document.getElementsByName(trim('ModifyTime'))[0].value);
    strSQL = mySql1.getString();
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = LogGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //showCodeName();
}

function getQueryResult()
{
	var arrSelected = null;
	var tRow = LogGrid.getSelNo();
	
	if( tRow == 0 || tRow == null || arrDataSet == null )
		      return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = LogGrid.getRowData(tRow-1);
	
	return arrSelected;
}

/*********************************************************************
 *  MulLine��RadioBox����¼��������־����ϸ��Ϣ
 *  ����ֵ��  ��
 *********************************************************************
 */
function getLogDetail(parm1,parm2)
{

    var OtherNo=fm.all(parm1).all('LogGrid1').value;
    var OtherNoType = fm.all(parm1).all('LogGrid2').value;
   
//    strSQL ="select * from LCDelPolLog where OtherNo = '"+OtherNo+"' and OtherNoType='"+OtherNoType+"'";
    var mySql2=new SqlClass();
    mySql2.setResourceName("logmanage.LogQuerySql");
    mySql2.setSqlId("LogQuerySql2");
    mySql2.addSubPara(OtherNo);
    mySql2.addSubPara(OtherNoType);
    arrResult=easyExecSql(mySql2.getString(),1,0);
    if(arrResult!=null)
    {
    	divLog2.style.display="";
      DisplayLog();
    }
}

/*********************************************************************
 *  ��ʾ��־��ϸ��Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function DisplayLog()
{
    try{fm.all('tOtherNo').value=arrResult[0][0];}catch(ex){};            
    try{fm.all('tOtherNoType').value=arrResult[0][1];}catch(ex){};               
    try{fm.all('tIsPolFlag').value=arrResult[0][2];}catch(ex){};            
    try{fm.all('tPrtNo').value=arrResult[0][3];}catch(ex){};                
    try{fm.all('tManageCom').value=arrResult[0][4];}catch(ex){};              
    try{fm.all('tOperator').value=arrResult[0][5];}catch(ex){};            
    try{fm.all('tMakeDate').value=arrResult[0][6];}catch(ex){};           
    try{fm.all('tMakeTime').value=arrResult[0][7];}catch(ex){};             
    try{fm.all('tModifyDate').value=arrResult[0][8];}catch(ex){};      
    try{fm.all('tModifyTime').value=arrResult[0][9];}catch(ex){};           
}	