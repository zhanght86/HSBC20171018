//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var confirmFlag=false;

function CheckTempfeeno()
{
	
	//����ѯ�����Ϊljtempfee zy 2008-10-16 10:32
	//var strSQL = "select count(*) from ljtempfee where otherno='"+trim(document.all('OtherNo').value)+"'";
    var strSQL = wrapSql1("LJTempFee1",[document.all('OtherNo').value]);
    var arrResult = easyExecSql(strSQL, 1, 0);
    if (arrResult != null)
    {
        try
        {
        	if (parseInt(arrResult[0][0]) > 1)
        	{
        		return false;	
        	}
            
        }
        catch (ex)
        {
            alert("��ѯ����������Ϣʧ�ܣ� ");
        }
    } //arrResult != null
	return true;

}


//�ύ�����水ť��Ӧ����
function submitForm()
{
	
	if(document.all('TempFeeNo').value==null||document.all('TempFeeNo').value=="")
	
	{
    if(!CheckTempfeeno())
    {
    	alert("������Ͷ�����ж�����վݺţ�������һ�����վݺţ�");
      return false;	
    }
    
  }
	
  if((document.all('TempFeeNo').value==null||document.all('TempFeeNo').value=="")&&(document.all('OtherNo').value==null||document.all('OtherNo').value==""))
  {
    alert("���������վݺŻ�Ͷ�����ţ�");
    return false;	
  }
  
  if(beforeSubmit())
  {
  	document.all('Query').disabled=true;
   	var i = 0;
   	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
   	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name="��ʾ";
   	var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();   
   	TempGrid.clearData("TempGrid");
   	TempClassToGrid.clearData("TempClassToGrid");
   	//fm.target="_blank";
   	fm.Opt.value="QUERY";
   	document.getElementById("fm").submit(); //�ύ
	showInfo.close();
   }
}
function submitForm1()
{

	document.all('Update').disabled=true;
 	var i = 0;
   	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
   	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name="��ʾ";
   	var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    showInfo.focus();   
    for(var c=0;c<TempClassToGrid.mulLineCount;c++){
    	if(TempClassToGrid.getRowColData(c,2)=="2" || TempClassToGrid.getRowColData(c,2)=="3" || TempClassToGrid.getRowColData(c,2)=="4"){
    		if(TempClassToGrid.getRowColData(c,7).length>0 && TempClassToGrid.getRowColData(c,8).length>0){
    			if(!checkBankAccNo(TempClassToGrid.getRowColData(c,7),TempClassToGrid.getRowColData(c,8))){
    				return;
    			}
    		}else{
    			if(TempClassToGrid.getRowColData(c,2)=="4"){//�շѷ�ʽΪ����ת��ʱ���б����������˺�Ϊ��¼��
    				alert("��¼�����б���������˺�");
    				return;		
    			}
    		}
    	}
    }
	 // document.getElementById("fm").submit(); //�ύ
   fm.Opt.value="SAVE";
   document.getElementById("fm").submit(); //�ύ
  
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	
  }
  else
  { 

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���
    //if(!verifyInput()) return false;
    return true;
}           

function returnParent()
{
    top.close();

}

//��ʾ���ݵĺ�������easyQuery��MulLine һ��ʹ��
function showRecord(strRecord)
{

  //�����ѯ����ַ���
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //ʹ��ģ������Դ������д�ڲ��֮ǰ
  turnPage.useSimulation   = 1;  
    
  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  var filterArray = new Array(0,2,6,7,4,3,38);

  
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = TempGrid;             
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}
function showRecord1(strRecord)
{

  //�����ѯ����ַ���
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //ʹ��ģ������Դ������д�ڲ��֮ǰ
  turnPage.useSimulation   = 1;  
    
  //��ѯ�ɹ������ַ��������ض�ά����
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  var filterArray = new Array(0,1,3,5,2,8,13,14,15,28,29,32,33);

  
  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = TempClassToGrid;             
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}

function clearFormData()
{  
  initTempClassGrid();
  initTempGrid();
  initInpBox();
  Frame1.style.display="none";
  Frame2.style.display="none";               
  TempGrid.lock();
}

function clearshowInfo()
{  
  
  showInfo.close();
}


/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
**/
function wrapSql1(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("finfee.TempFeeUpdateInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}
