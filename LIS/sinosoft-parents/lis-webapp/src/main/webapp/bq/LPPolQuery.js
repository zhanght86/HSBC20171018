//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
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
  initLPPolGrid();
  document.all('fmtransact').value = "QUERY||POL";
 // showSubmitFrame(mDebug);
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Result )
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
    // ��дSQL���
			 // alert(Result);                    
		 
	//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = Result;  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	    alert("��ѯʧ�ܣ�");
	  }
	  else
	  {
	  		//��ѯ�ɹ������ַ��������ض�ά����
	  	var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
	  	turnPage.arrDataCacheSet= chooseArray(tArr,[1,5,20,21,11,31]);
	  	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  	turnPage.pageDisplayGrid = LPPolGrid;    
	  
	  	//���ò�ѯ��ʼλ��
	 	 turnPage.pageIndex       = 0;  
	  
	 	 //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  	var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  
	  	//����MULTILINE������ʾ��ѯ���
	  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
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
  	alert("��LPPolQuery.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
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

// ��ѯ��ť
function easyQueryClick()
{
	var EdorState;
	
	// ��ʼ�����
	initLPPolGrid();
	var tReturn = parseManageComLimit();

	// ��дSQL���
	var strSQL = "";
	
	var sqlid818151019="DSHomeContSql818151019";
var mySql818151019=new SqlClass();
mySql818151019.setResourceName("bq.LPPolQueryInputSql");//ָ��ʹ�õ�properties�ļ���
mySql818151019.setSqlId(sqlid818151019);//ָ��ʹ�õ�Sql��id
mySql818151019.addSubPara(fm.PolNo.value);//ָ������Ĳ���
mySql818151019.addSubPara(tReturn);//ָ������Ĳ���
strSQL=mySql818151019.getString();
	
//	strSQL = "select PolNo,ContNo,GrpPolNo,ProposalNo,AppntName,CValiDate from LPPol where appFlag = '1'"+" and"+tReturn
//				 +" "+ getWherePart( 'PolNo' );
					 
	//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	    alert("��ѯʧ�ܣ�");
	  }
	  else
	  {
	  	//��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  turnPage.pageDisplayGrid = LPPolGrid;    
	          
	  //����SQL���
	  turnPage.strQuerySql     = strSQL; 
	  
	  //���ò�ѯ��ʼλ��
	  turnPage.pageIndex       = 0;  
	  
	  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  
	  //����MULTILINE������ʾ��ѯ���
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}

function returnParent()
{
    tSel=LPPolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
    {
//			alert("pp");
			top.close();
		}
    else
    {
    	var tPolNo=LPPolGrid.getRowColData(tSel-1,1);
    	try{
    	   		top.opener.document.all('PolNo').value = tPolNo;
				}catch(e){
					}
	
		top.opener.afterQuery();
		top.close();

    }
}

function getPolNo()
{
	 tSel=LPPolGrid.getSelNo();
    if( tSel == 0 || tSel == null )
			top.close();
    else
    {
    	tCol=1;
   		tPolNo = LPPolGrid.getRowColData(tSel-1,tCol);
   		top.opener.document.all('PolNo').value = tPolNo;
   	}
}