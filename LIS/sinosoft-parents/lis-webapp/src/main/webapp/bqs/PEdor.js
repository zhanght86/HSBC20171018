var PassFlag = '0';
var ComLength= 8;
var ScreenWidth=640;
var ScreenHeight=480;
//var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

//���ݲ�ͬ�ı�ȫ��Ŀ���벻ͬ�ı�ȫ��ϸ
function detailEdorType()
{
	//alert(start);	
	if (!needDetail())
	{
	    return;
	}
	
	detailQueryClick();
	
	if (PassFlag == '1')
	{
		switch(document.all('EdorType').value) 
		{
			case "WT":
				if (document.all('ContType').value =='1')
				{
					var newWindow = window.open("./PEdorTypeWT.html","PEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				else
				{
					var newWindow = window.open("./GEdorTypeWT.html","GEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				break;
				
			default:

				if (document.all('ContType').value =='1') //��Ĭ�ϸ��屣ȫ��Ŀ����ϸ����
				{
			 		//alert("EdorType");
			 		window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			 	}
				else  //�����ձ�ȫ��ϸ
				{
				  //�����յ�ѡ��ȫ��ϸ�ۺ�ҳ��
				  if (document.all('EdorType').value=="BB" || 
				  	  document.all('EdorType').value=="IC" || 
				  	  document.all('EdorType').value=="GT" || 
				  	  document.all('EdorType').value=="IO" || 
				  	  document.all('EdorType').value=="GC" || 
				  	  document.all('EdorType').value=="YC" || 
				  	  document.all('EdorType').value=="PT" ||
				  	  document.all('EdorType').value=="GA") 
				  {
					  var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				      newWindow.focus();
				  }
				  else if (document.all('EdorType').value=="BC" ||
				  		   document.all('EdorType').value=="RC" ||
				  		   document.all('EdorType').value=="GC")
				  {
				  	  window.open("./GEdorTypeRiskDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  //�����ո�ѡ��ȫ��ϸ�ۺ�ҳ��
				  else if (document.all('EdorType').value=="ZT" ) 
				  {
				      window.open("./GEdorTypeMultiDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  //�����ո�ѡ��ȫ��ϸ�ۺ�ҳ��
				  else if (document.all('EdorType').value=="LT" ) 
				  {
				      window.open("./GEdorTypeMultiRisk.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
				  else //��Ĭ�����ձ�ȫ��Ŀ����ϸ����
				  {				  
					  window.open("./GEdorType" + trim(document.all('EdorType').value) + ".jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				  }
			    }
				break;
		}
	}		
}
function GetEndorseQuery()
{
	window.open("./LJSGetEndorse.html");
}
function LPPolQuery()
{
	window.open("./LPPolQuery.jsp");
}

// ��ѯ��ť
function detailQueryClick()
{
	var tEdorAcceptNo = document.all('EdorAcceptNo').value;
	var tEdorType = document.all('EdorType').value;
	var tContType = document.all('ContType').value;
	
	// ��дSQL���
	var strSQL = "";
	//alert("conttype:"+tContType);
	if (tContType=='1')
		
	//strSQL = "select count(*) from lpedorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
	var sqlid1 = "PEdorSql1";
	var mySql1 = new SqlClass();
	mySql1.setResourceName("bqs.PEdorSql"); // ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);// ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tEdorAcceptNo);// ָ������Ĳ���
	mySql1.addSubPara(tEdorType);// ָ������Ĳ���
	strSQL = mySql1.getString();
		
//		strSQL = "select count(*) from lpedorItem a left join lpedorapp b on a.EdorAcceptNo = b.EdorAcceptNo where (edorno='"+tEdorNo+"' and edortype='"+tEdorType+"' and b.OtherNoType = 3) or (edortype='"+tEdorType+"' and b.OtherNoType = 1)";
	else
	//strSQL = "select count(*) from LPGrpEdorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
	var sqlid2 = "PEdorSql2";
	var mySql2 = new SqlClass();
	mySql2.setResourceName("bqs.PEdorSql"); // ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);// ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tEdorAcceptNo);// ָ������Ĳ���
	mySql2.addSubPara(tEdorType);// ָ������Ĳ���
	strSQL = mySql2.getString();

	  //alert(strSQL);			 
	  //��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	      alert("��ѯʧ�ܣ�");
	   	  PassFlag='0';
	      return;
	  }
	  else
	  {
		    //��ѯ�ɹ������ַ��������ض�ά����
		    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  	  
		    if (turnPage.arrDataCacheSet[0][0]<=0)
		    {
				 alert("�뱣������������Ŀ��");
				 PassFlag='0';
				 return;
		    }
		    PassFlag ='1';
		}
}

//���Ʒ�����ϸ��ť
function ctrlGetEndorse()
{
	try
	{
		var tEdorNo = 	document.all('EdorNo').value;
		var tEdorType = document.all('EdorType').value;
		
		/*strSQL = " select count(*) from LJSGetEndorse " +
				 " where EndorsementNo = '" + tEdorNo + "' and FeeOperationType = '" + tEdorType + "'";*/
		
		var sqlid3 = "PEdorSql3";
		var mySql3 = new SqlClass();
		mySql3.setResourceName("bqs.PEdorSql"); // ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);// ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(tEdorNo);// ָ������Ĳ���
		mySql3.addSubPara(tEdorType);// ָ������Ĳ���
		strSQL = mySql3.getString();
		//��ѯSQL�����ؽ���ַ���
	    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
	  
	    //�ж��Ƿ��ѯ�ɹ�
	    if (!turnPage.strQueryResult) 
	    {
	        alert("��ѯʧ�ܣ�");
	    	return;
	    }
	    else
	    {
		    //��ѯ�ɹ������ַ��������ض�ά����
		    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  	  
			if (turnPage.arrDataCacheSet[0][0]<=0)
			{
				 divGetEndorse.style.display='none';
				 return;
			}
		}
	}
	catch(ex)
	{
	}
}

/*
function getScreenSize()
{
	ScreenWidth = screen.availWidth;
	ScreenHeight = screen.availHeight;
}
*/

//У�����Ŀ�Ƿ���Ҫ��ϸ
function needDetail()
{
   /* var strSQL = " select NeedDetail from LMEdorItem " +
    			 " where edorcode = '" + document.all('EdorType').value + "' ";
    */
    
    var sqlid4 = "PEdorSql4";
	var mySql4 = new SqlClass();
	mySql4.setResourceName("bqs.PEdorSql"); // ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);// ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(document.all('EdorType').value);// ָ������Ĳ���
	var strSQL = mySql4.getString();
	
    if (fm.OtherNoType.value == '1')
    {
        if (document.all('ContType').value =='1')
        	
            strSQL = strSQL + " and APPOBJ = 'B'"
           // mySql4.addSubPara(" and APPOBJ = 'B'");// ָ������Ĳ���
        else
            strSQL = strSQL + " and APPOBJ = 'A'"
        	// mySql4.addSubPara(" and APPOBJ = 'A'");// ָ������Ĳ���
    }
    else
    {
        if (document.all('ContType').value =='1')
            strSQL=strSQL+" and APPOBJ='I'"
           // mySql4.addSubPara(" and APPOBJ='I'");// ָ������Ĳ���
        else
           strSQL=strSQL+" and APPOBJ='G'"
           // mySql4.addSubPara(" and APPOBJ='G'");// ָ������Ĳ���
    }
    
    var arrResult = easyExecSql(strSQL);
	if (arrResult != null) 
	{
		if (arrResult[0][0]==0)
		{
			alert("����Ŀ����Ҫ¼����ϸ��Ϣ��")
			return false;
		}
	    else
			return true;
	}
	else
	{
		alert("����Ŀ���岻������")
		return false;
	}
}


//<!-- XinYQ added on 2006-07-25 : ��ȡҵ��Ա��Ͷ���˵Ĺ�ϵ : BGN -->
/*============================================================================*/

function getAgentToAppntRelation(sEdorType)
{
    //������
    if (sEdorType == null || trim(sEdorType) == "")
    {
        alert("�޷���ȡ��ȫ��Ŀ���͡���ѯҵ��Ա��Ͷ���˵Ĺ�ϵʧ�ܣ� ");
        return;
    }
    //��ѯ����
    var sEdorAcceptNo;
    try
    {
        sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
    }
    catch (ex) {}
    if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "")
    {
        //alert("�޷���ȡ��ȫ����š���ѯҵ��Ա��Ͷ���˵Ĺ�ϵʧ�ܣ� ");
        return;
    }
    //�Ȳ�ѯ LPEdorItem
    var QuerySQL, arrResult;
   /* QuerySQL = "select a.StandByFlag2, "
             +        "(select CodeName "
             +           "from LDCode "
             +          "where CodeType = 'relationtoappnt' "
             +            "and Code = a.StandByFlag2) "
             +   "from LPEdorItem a "
             +  "where 1 = 1 "
             +    "and a.EdorAcceptNo = '" + trim(sEdorAcceptNo) + "' "
             +    "and a.EdorType = '" + trim(sEdorType) + "'";
    */
    var sqlid5 = "PEdorSql5";
	var mySql5 = new SqlClass();
	mySql5.setResourceName("bqs.PEdorSql"); // ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);// ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(trim(sEdorAcceptNo));// ָ������Ĳ���
	mySql5.addSubPara(trim(sEdorType));// ָ������Ĳ���
	QuerySQL = mySql5.getString();
    
    
    //alert(QuerySQL);
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯҵ��Ա��Ͷ���˵Ĺ�ϵ�����쳣�� ");
        return;
    }
    if (arrResult != null && trim(arrResult[0][0]) != "")
    {
        try
        {
            document.getElementsByName("RelationToAppnt")[0].value = arrResult[0][0];
            document.getElementsByName("RelationToAppntName")[0].value = arrResult[0][1];
        }
        catch (ex) {}
    }
    //��ѯ LACommisionDetail
    else
    {
        var sContNo;
        try
        {
            sContNo = document.getElementsByName("ContNo")[0].value;
        }
        catch (ex) {}
        if (sContNo == null || trim(sContNo) == "")
        {
            //alert("�޷���ȡ��ͬ�š���ѯҵ��Ա��Ͷ���˵Ĺ�ϵʧ�ܣ� ");
            return;
        }
       /* QuerySQL = "select a.RelationShip, "
                 +        "(select CodeName "
                 +           "from LDCode "
                 +          "where CodeType = 'relationtoappnt' "
                 +            "and Code = a.RelationShip) "
                 +   "from LACommisionDetail a "
                 +  "where 1 = 1 "
                 +    "and a.GrpContNo = '" + trim(sContNo) + "' "
                 +    "and a.AgentCode = "
                 +        "(select trim(AgentCode) "
                 +           "from LCCont "
                 +          "where ContNo = '" + trim(sContNo) + "')";*/
        
        var sqlid6 = "PEdorSql6";
    	var mySql6 = new SqlClass();
    	mySql6.setResourceName("bqs.PEdorSql"); // ָ��ʹ�õ�properties�ļ���
    	mySql6.setSqlId(sqlid6);// ָ��ʹ�õ�Sql��id
    	mySql6.addSubPara(trim(sContNo));// ָ������Ĳ���
    	mySql6.addSubPara(trim(sContNo));// ָ������Ĳ���
    	QuerySQL = mySql6.getString();
        
        
        //alert(QuerySQL);
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯҵ��Ա��Ͷ���˵Ĺ�ϵ�����쳣�� ");
            return;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            try
            {
                document.getElementsByName("RelationToAppnt")[0].value = arrResult[0][0];
                document.getElementsByName("RelationToAppntName")[0].value = arrResult[0][1];
            }
            catch (ex) {}
        }
    }
}

/*============================================================================*/
//<!-- XinYQ added on 2006-07-25 : ��ȡҵ��Ա��Ͷ���˵Ĺ�ϵ : END -->
