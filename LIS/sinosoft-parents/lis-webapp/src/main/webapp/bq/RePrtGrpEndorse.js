//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
}        


function queryEndorse()
{
	var EdorState;	
	if(fm.GrpContNo.value == "" && fm.EdorNo.value == "" && fm.EdorAcceptNo.value == "")
	{
	    alert("���ߴ�ӡǰ�������뱣���ţ���ȫ����Ż������ţ�\n���ߵ���ȫ������ӡҳ����д�ӡ��");
	    return;
	}

	// ��дSQL���
	var strSQL = "";
	strSQL = " select distinct EdorConfNo,     "           
								+" otherno,       "            
								+" a.edorappname, "            
								+" a.EdorAppDate, "            
								+" a.modifytime,  "            
								+" a.edoracceptno,"            
								+" b.edorno       "            
                +" from LPEdorApp a,lpgrpedoritem b  "          
                +" where a.edoracceptno=b.edoracceptno "   
                +" and exists (select 'Y' "                 
								+"	from lpedorprint c "      
								+" where c.prttimes > 0 "     
								+"	 and c.edorno = b.edorno) "
								 +" and a.ManageCom LIKE '" + comcode.substring(0,6) + "%%'" //��½����Ȩ�޿���
	              +" and a.EdorState in ('0', '6') "   
					      + getWherePart( 'a.otherno','GrpContNo' )
				        + getWherePart( 'a.EdorConfNo','EdorNo' )
				        + getWherePart('a.EdorAcceptNo','EdorAcceptNo')
				        + getWherePart('a.ManageCom', 'ManageCom', 'like')
	              +" order by a.EdorAppDate,a.modifytime";

				 
	//��ѯSQL�����ؽ���ַ���
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	
	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) 
	{
		alert("�������ѯ������������������Ѵ�ӡ!");
	  	return;
	}
	else
	{
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	
		//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		turnPage.pageDisplayGrid = PEdorMainGrid;    	        
		//����SQL���
		turnPage.strQuerySql     = strSQL; 	
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex       = 0;	  
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		arrGrid = arrDataSet;
	  	//����MULTILINE������ʾ��ѯ���
	  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
}

function print()
{
	var selno = PEdorMainGrid.getSelNo()-1;
	if (selno <0)
	{
		alert("��ѡ��Ҫ���������");
	    return;
	}
	var tType = "Endorsement";	
	var EdorNo = PEdorMainGrid.getRowColData(selno, 7);
	fm.action = "../f1print/ReEndorsementF1PJ1.jsp?EdorNo=" + EdorNo+ "&type="+tType;
	fm.target="f1print";
	document.getElementById("fm").submit();
}


//��ʼ��������������ȡ��λ
function initManageCom()
{
	if(comcode.substring(0,6) !=null && comcode.substring(0,6) != "")
	{
    	var i,j,m,n;
	    var returnstr;
	    var tTurnPage = new turnPageClass();
	    var strSQL = "select comcode,name from ldcom where comcode like '"+comcode.substring(0,6)+"%%'";
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);  
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
        	for( i = 0;i < n; i++)
        	{
  	        	m = tTurnPage.arrDataCacheSet[i].length;
        		if (m > 0)
  		        {
  		        	for( j = 0; j< m; j++)
  			        {
  		 		        if (i == 0 && j == 0)
  			        	{
  				        	returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
  			        	}
  			        	if (i == 0 && j > 0)
  			        	{
  					        returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
  				        }
  			         	if (i > 0 && j == 0)
  				        {
  					        returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
  				        }
  			        	if (i > 0 && j > 0)
  				        {
  					        returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
  				        }
     		        }
  		        }
  	            else
  	            {
  		          	alert("��ѯʧ��!!");
  			        return "";
  		        }
             }
         }
         else
         {
	         alert("��ѯʧ��!");
	         return "";
         }
         fm.ManageCom.CodeData = returnstr;
         return returnstr;
	}	
}