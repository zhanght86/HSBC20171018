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

// ���ݷ��ظ�����
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		
		if (cPolNo == "")
		    return;
		    
		var GrpPolNo = PolGrid.getRowColData(tSel-1,1);
        
        //alert("dfdf");
        if( tIsCancelPolFlag == "0"){
	    	if (GrpPolNo =="00000000000000000000") {
	    	 	window.open("./AllProQueryMain.jsp?LoadFlag=3","window1");	
		    } else {
			window.open("./AllProQueryMain.jsp?LoadFlag=4");	
		    }
		} else {
		if( tIsCancelPolFlag == "1"){//����������ѯ
			if (GrpPolNo =="00000000000000000000")   {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} else {
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	    } else {
	    	myAlert(""+"�������ʹ������!"+"");
	    	return ;
	    }
	 }
 }
}

//���������Ĳ�ѯ����
function getQueryDetail_B()
{
	
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼��"+"" );
	else
	{
	  var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		if (cPolNo == "")
			return;
		var GrpPolNo = PolGrid.getRowColData(tSel-1,6);
	    if (GrpPolNo =="00000000000000000000") 
	    {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} 
			else 
			{
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	}
}



// ������ϸ��ѯ
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼���ٵ�����ذ�ť��"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
		if (cPolNo == "")
		    return;		
		
	    window.open("RePolDetailQueryMain.jsp?PolNo=" + cPolNo );	
	}
}

// ��ѯ��ť
function easyQueryClick()
{
    
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var pram=getWherePart( 'PolNo' )+ getWherePart( 'GrpPolNo' )+ getWherePart( 'RiskCode' )+ getWherePart( 'CessStart' )+ getWherePart( 'ReinsurItem' )+ getWherePart( 'ProtItem' )+ getWherePart( 'InsuredName' )+ getWherePart( 'InsuredNo' )+ getWherePart( 'InsuredBirthday' )+ getWherePart( 'InsuredYear' );
	var strSQL = "";
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.AllReinsurPolQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("AllReinsurPolQuerySql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(pram);//ָ������Ĳ���
	    strSQL=mySql100.getString();
	/**
	strSQL = "select PolNo,ReinsurItem,GrpPolNo,RiskCode,SignDate,CValiDate,InsuredName,StandPrem,RiskAmnt,CessionRate,CessionAmount,CessPrem,CessComm,ExPrem,ExCessPrem,ExCessComm,InsuredAppAge,InsuredSex from LRPol where 1=1  "
				 + getWherePart( 'PolNo' )
				 + getWherePart( 'GrpPolNo' )
				 + getWherePart( 'RiskCode' )
				 + getWherePart( 'CessStart' )
				 + getWherePart( 'ReinsurItem' )
				 + getWherePart( 'ProtItem' )
				 + getWherePart( 'InsuredName' )
				 + getWherePart( 'InsuredNo' )
				 + getWherePart( 'InsuredBirthday' )
				 + getWherePart( 'InsuredYear' )
//				 + " and ManageCom like '" + comCode + "%%'"
				 + " order by InsuredNo,signdate";
		 
//	alert(strSQL);
*/
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    myAlert(""+"��ѯʧ�ܣ�"+"");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
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




// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		myAlert( ""+"����ѡ��һ����¼���ٵ�����ذ�ť��"+"" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			//alert(arrReturn);
			//alert("����"+arrReturn);
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			myAlert( ""+"����ѡ��һ���ǿռ�¼���ٵ�����ذ�ť��"+"");
			//alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	var tRow = PolGrid.getSelNo();
	//alert(arrGrid);
	//alert("safsf");
	if( tRow == 0 || tRow == null || arrDataSet == null )
		      return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = PolGrid.getRowData(tRow-1);
	//alert(arrSelected[0][1]);
	//tRow = 10 * turnPage.pageIndex + tRow; //10����multiline������
	//������Ҫ���ص�����
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//������Ҫ���ص�����
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
}
