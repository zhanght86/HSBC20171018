var PassFlag = '0';
var ComLength= 8;
var ScreenWidth=640;
var ScreenHeight=480;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage11 = new turnPageClass();   
var mySql=new SqlClass();
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
					var newWindow = window.open("./PEdorTypeWT.jsp","PEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				else
				{
					var newWindow = window.open("./GEdorTypeWT.jsp","GEdorTypeWT",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  					newWindow.focus();
				}
				break;
			case "NS":
				if (document.all('ContType').value =='1')
				{
             window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp?ContNo="+document.getElementsByName("ContNo")[0].value, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
				}
				break;
			default:

				if (document.all('ContType').value =='1') //��Ĭ�ϸ��屣ȫ��Ŀ����ϸ����
				{
			 		window.open("./PEdorType" + trim(document.all('EdorType').value) + ".jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
			 	}
				else  //�����ձ�ȫ��ϸ
				{
if (
              //document.all('EdorType').value=="PR" ||
              document.all('EdorType').value=="IC" || 
              document.all('EdorType').value=="GT" || 
              document.all('EdorType').value=="IO" || 
              document.all('EdorType').value=="GC" || 
              document.all('EdorType').value=="YC" || 
              document.all('EdorType').value=="PT" ||
              document.all('EdorType').value=="AA" ||
              document.all('EdorType').value=="GA" ||
              document.all('EdorType').value=="GB" ||
              document.all('EdorType').value=="GM" ||              
              document.all('EdorType').value=="RT" ||
              //document.all('EdorType').value=="AG" ||              
              document.all('EdorType').value=="BD") 
          {
          var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              newWindow.focus();
          }
          else if (document.all('EdorType').value=="BC"                 
                   //document.all('EdorType').value=="RC" ||
                   )
          {
              window.open("./GEdorTypeRiskDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }          
          else if (document.all('EdorType').value=="ZT") 
          {
              window.open("./GEdorTypeMultiDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          //�����ո�ѡ��ȫ��ϸ�ۺ�ҳ��
          //else if ( document.all('EdorType').value=="LT" ) 
          //{
          //    window.open("./GEdorTypeCT.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              //window.open("./GEdorTypeMultiRisk.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          //}
          else if ( document.all('EdorType').value=="BB" ) 
          {
          	
              //alert(document.all('EdorTypeCal').value);
              if(document.all('EdorTypeCal').value=="004"){
                 window.open("./GrpEdorTypeBB.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
              }else{
                var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
                newWindow.focus();
              }
              //window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              return;  
          }
          else if ( document.all('EdorType').value=="PR" ) 
          {
              //alert(document.all('EdorTypeCal').value);
              if(document.all('EdorTypeCal').value=="004"){
                 window.open("./GrpEdorTypePR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
              }else{
                var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
                newWindow.focus();
              }
              //window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              return;  
          } 
          else if ( document.all('EdorType').value=="RC" ) 
          {
              //alert(document.all('EdorTypeCal').value);
              if(document.all('EdorTypeCal').value=="004"){
                 window.open("./GrpEdorTypePR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
              }else{
                var newWindow = window.open("./GEdorTypeDetail.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
                newWindow.focus();
              }
              //window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
              return;  
          }                   
          //��������㷨
          else if ( document.all('EdorType').value=="AG" ) 
          {
          	  var tGrpContNo           = fm.OtherNo.value;
              var tEdorItemAppDate     = fm.EdorItemAppDate.value;//������������
              var tEdorValiDate        = fm.EdorValiDate.value;//��Ч����
              window.open("./GrpEdorTypeAGMain.jsp?GrpContNo="+tGrpContNo+"&EdorItemAppDate="+tEdorItemAppDate+"&EdorValiDate="+tEdorValiDate, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');          
          }
          else if ( document.all('EdorType').value=="NR" ) 
          {
              window.open("./GrpEdorTypeNR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if ( document.all('EdorType').value=="BS" ) //add by wanzh 2006-04-18 ���롮�����ڼ��жϡ�����ҳ��
          {
              
              var tGrpContNo    = fm.OtherNo.value;
              var tEdorType     = fm.EdorType.value;
              var tEdorAcceptNo = fm.EdorAcceptNo.value;
              window.open("./GrpEdorTypeBSMain.jsp?GrpContNo="+tGrpContNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if ( document.all('EdorType').value=="BR" ) //add by wanzh 2006-04-18 ���롮�����ڼ�ָ�������ҳ��
          {
              var tGrpContNo    = fm.OtherNo.value;
              var tEdorType     = fm.EdorType.value;
              var tEdorAcceptNo = fm.EdorAcceptNo.value;
              window.open("./GrpEdorTypeBRMain.jsp?GrpContNo="+tGrpContNo+"&EdorType="+tEdorType+"&EdorAcceptNo="+tEdorAcceptNo, "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if ( document.all('EdorType').value=="VR" ) //add by wanzh 2006-04-18 ���롮�����ڼ�ָ�������ҳ��
          {
              window.open("./PEdorTypeVR.jsp", "PEdorType" + trim(document.all('EdorType').value), 'width='+screen.availWidth+',height='+screen.availHeight+',top=20,left=20,toolbar=20,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if(document.all('EdorType').value=='VC')
          {
            window.open("./GEdorTypeVC.jsp","PEdorType" + trim(document.all('EdorType').value),'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
          }
          else if(document.all('EdorType').value=='CR')
          {
            window.open("./GEdorTypeCR.jsp","PEdorType" + trim(document.all('EdorType').value),'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
	window.open("./LJSGetEndorse.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}
function LPPolQuery()
{
	window.open("./LPPolQuery.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

// ��ѯ��ť
function detailQueryClick()
{
	var tEdorAcceptNo = document.all('EdorAcceptNo').value;
	var tEdorType = document.all('EdorType').value;
	var tContType = document.all('ContType').value;

    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdor");
    
	if (tContType=='1')
		//strSQL = "select count(*) from lpedorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
	  mySql.setSqlId("PEdorSql2");
	else
		//strSQL = "select count(*) from LPGrpEdorItem where edoracceptno='"+tEdorAcceptNo+"' and edortype='"+tEdorType+"'";
   mySql.setSqlId("PEdorSql3");
   mySql.addSubPara(tEdorAcceptNo); 
   mySql.addSubPara(tEdorType);
   
	  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);

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

		var sqlid818145353="DSHomeContSql818145353";
var mySql818145353=new SqlClass();
mySql818145353.setResourceName("bq.GetLJSGetEndorseInputSql");//ָ��ʹ�õ�properties�ļ���
mySql818145353.setSqlId(sqlid818145353);//ָ��ʹ�õ�Sql��id
mySql818145353.addSubPara(tEdorNo);//ָ������Ĳ���
mySql818145353.addSubPara(tEdorType);//ָ������Ĳ���
strSQL=mySql818145353.getString();
		
//		strSQL = " select count(*) from LJSGetEndorse " +
//				 " where EndorsementNo = '" + tEdorNo + "' and FeeOperationType = '" + tEdorType + "'";

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
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdor");
    mySql.setSqlId("PEdorSql1");
    mySql.addSubPara(document.all('EdorType').value); 
        	
   // var strSQL = " select NeedDetail from LMEdorItem " +
   // 			 " where edorcode = '" + document.all('EdorType').value + "' ";
    if (fm.OtherNoType.value == '1')
    {
        if (document.all('ContType').value =='1')
           // strSQL = strSQL + " and APPOBJ = 'B'"
            mySql.addSubPara('B');
        else
            //strSQL = strSQL + " and APPOBJ = 'A'"
            mySql.addSubPara('A');
    }
    else
    {
        if (document.all('ContType').value =='1')
           // strSQL=strSQL+" and APPOBJ='I'"
            mySql.addSubPara('I');
        else
            //strSQL=strSQL+" and APPOBJ='G'"
            mySql.addSubPara('G');
    }
    var arrResult = easyExecSql(mySql.getString());
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


//<!-- XinYQ added on 2005-12-12 : ��ȡҵ��Ա��Ͷ���˵Ĺ�ϵ : BGN -->
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
    
    var sqlid818145513="DSHomeContSql818145513";
var mySql818145513=new SqlClass();
mySql818145513.setResourceName("bq.GetLJSGetEndorseInputSql");//ָ��ʹ�õ�properties�ļ���
mySql818145513.setSqlId(sqlid818145513);//ָ��ʹ�õ�Sql��id
mySql818145513.addSubPara(trim(sEdorAcceptNo));//ָ������Ĳ���
mySql818145513.addSubPara(trim(sEdorType));//ָ������Ĳ���
QuerySQL=mySql818145513.getString();
    
//    QuerySQL = "select a.StandByFlag2, "
//             +        "(select CodeName "
//             +           "from LDCode "
//             +          "where CodeType = 'relationtoappnt' "
//             +            "and Code = a.StandByFlag2) "
//             +   "from LPEdorItem a "
//             +  "where 1 = 1 "
//             +    "and a.EdorAcceptNo = '" + trim(sEdorAcceptNo) + "' "
//             +    "and a.EdorType = '" + trim(sEdorType) + "'";
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
        
        var sqlid818145650="DSHomeContSql818145650";
var mySql818145650=new SqlClass();
mySql818145650.setResourceName("bq.GetLJSGetEndorseInputSql");//ָ��ʹ�õ�properties�ļ���
mySql818145650.setSqlId(sqlid818145650);//ָ��ʹ�õ�Sql��id
mySql818145650.addSubPara(trim(sContNo));//ָ������Ĳ���
mySql818145650.addSubPara(trim(sContNo));//ָ������Ĳ���
QuerySQL=mySql818145650.getString();
        
//        QuerySQL = "select a.RelationShip, "
//                 +        "(select CodeName "
//                 +           "from LDCode "
//                 +          "where CodeType = 'relationtoappnt' "
//                 +            "and Code = a.RelationShip) "
//                 +   "from LACommisionDetail a "
//                 +  "where 1 = 1 "
//                 +    "and a.GrpContNo = '" + trim(sContNo) + "' "
//                 +    "and a.AgentCode = "
//                 +        "(select trim(AgentCode) "
//                 +           "from LCCont "
//                 +          "where ContNo = '" + trim(sContNo) + "')";
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

/**
*���ò�ѯ add by sunsx 2008-10-07
*/
function MoneyDetail()
{
	var tEdorType = document.all('EdorType').value;
	//alert(tEdorType);
	if(tEdorType == null || tEdorType == "")
	{
		alert("��ѡ��ȫ��ϸ����ӱ�ȫ��Ŀ!");
		return;
	}
  window.open("./GetLJSGetEndorse.jsp","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");  
}

function queryMoneyDetail()
{
  //alert("ok");
  var tEdorNo = document.all('EdorNo').value;
  var tEdorType = document.all('EdorType').value;
  var strSQL = "";
  if(tEdorNo != null || tEdorNo != '')
    {
      //strSQL = "Select a.GetNoticeNo, a.FeeFinaType, a.ContNo,b.insuredname, a.GetDate, a.RiskCode, a.GetMoney From LJSGetEndorse a,lccont b where a.otherno='"+tEdorNo+"' and a.contno = b.contno and a.FeeOperationType='"+tEdorType+"' order by a.ContNo";
    
    var sqlid818145908="DSHomeContSql818145908";
var mySql818145908=new SqlClass();
mySql818145908.setResourceName("bq.GetLJSGetEndorseInputSql");//ָ��ʹ�õ�properties�ļ���
mySql818145908.setSqlId(sqlid818145908);//ָ��ʹ�õ�Sql��id
mySql818145908.addSubPara(tEdorNo);//ָ������Ĳ���
mySql818145908.addSubPara(tEdorType);//ָ������Ĳ���
strSQL=mySql818145908.getString();
    
//    strSQL = "Select GetNoticeNo, FeeFinaType, ContNo,(select insuredname from lccont where contno=LJSGetEndorse.contno), GetDate, RiskCode, GetMoney From LJSGetEndorse where otherno='"+tEdorNo+"' and FeeOperationType='"+tEdorType+"' order by ContNo";
    }

  turnPage11.queryModal(strSQL, MoneyDetailGrid);
  
}
/*============================================================================*/
//<!-- XinYQ added on 2005-12-12 : ��ȡҵ��Ա��Ͷ���˵Ĺ�ϵ : END -->
