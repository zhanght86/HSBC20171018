var showInfo;

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var mLostTimes;
var sqlresourcename = "bq.PEdorTypeWTInputSql";

function getPolGrid(tContNo)
{
    var EdorAppDate = fm.EdorItemAppDate.value;
    var tContno=document.all('ContNo').value;
/*    var strSQL = " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno " +
                 " from LCPol c where ContNo='" + tContNo + "' and appflag = '1' ";
*/
	var strSQL = "";
	var sqlid1="PEdorTypeWTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(EdorAppDate);//ָ������Ĳ���
	mySql1.addSubPara(EdorAppDate);
	mySql1.addSubPara(EdorAppDate);
	mySql1.addSubPara(tContNo);
	strSQL=mySql1.getString();

    brrResult = easyExecSql(strSQL);
    if (brrResult)
    {
        displayMultiline(brrResult,PolGrid);
    }
}

function getCustomerGrid()
{

    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno, 'Ͷ����', a.appntname, "
                    +" (select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),"
                    +" a.appntbirthday, "
                    +" (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)), "
                    +" a.idno "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '������', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo "
                    +" From lcinsured i Where contno='" + tContNo+"'";
*/        
    var strSQL = "";
	var sqlid2="PEdorTypeWTInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(tContNo);
	strSQL=mySql2.getString();
        
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}

function chkPol()
{
    var tContNo   = document.all('ContNo').value;
    var tEdorNo   = document.all('EdorNo').value;
    var tEdorType = document.all('EdorType').value;
/*    var strSQL = " select polno from lppol " +
                 " where edorno='" + tEdorNo +
                 "' and edortype='" + tEdorType +
                 "' and contno='"+tContno+"'";
*/    
    var strSQL = "";
	var sqlid3="PEdorTypeWTInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql3.addSubPara(tEdorType);
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
    
    var arrResult2=easyExecSql(strSQL);
    var m=0;
    var n=0;

    if(arrResult2!=null)
    {
        var q = arrResult2.length;
        for(m = 0; m < PolGrid.mulLineCount; m++)
        {
            for(n = 0; n < q; n++)
           {   
                if(PolGrid.getRowColData(m, 8) == arrResult2[n][0])
                {
                    PolGrid.checkBoxSel(m+1);
                }
            }
        }
    }
}


function getContZTInfo()
{
    var MainPolNo;
    var MainPayIntv;
    var MainPayToDate;
    var MainPayEndDate;
    var MainRiskCode;
/*    strSQL = " select polno, paytodate, payintv, payenddate, riskcode,salechnl from lcPol " +
             " where contno = '" + fm.ContNo.value +
             "' and polno = mainpolno";
*/    
	var sqlid4="PEdorTypeWTInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql4.getString();
    
    var hrr = easyExecSql(strSQL);
    if ( hrr )
    {
        hrr[0][0]==null||hrr[0][0]=='null'?'0':MainPolNo = hrr[0][0];
        hrr[0][1]==null||hrr[0][1]=='null'?'0':MainPayToDate = hrr[0][1];
        hrr[0][2]==null||hrr[0][2]=='null'?'0':MainPayIntv = hrr[0][2];
        hrr[0][3]==null||hrr[0][3]=='null'?'0':MainPayEndDate = hrr[0][3];
        hrr[0][4]==null||hrr[0][4]=='null'?'0':MainRiskCode = hrr[0][4];
        hrr[0][5]==null||hrr[0][5]=='null'?'0':fm.SaleChnl.value = hrr[0][5];
    }
    else
    {
        alert("��������ʧ��");
        return;
    }
    if(fm.SaleChnl.value!='03')
    {
    	fm.FeeGetFlag.checked=true;
    }
    
    //alert(4.1)
/*    strSQL = " select customgetpoldate, ReceiveDate, cvalidate from lccont " +
             " where contno = '" + fm.ContNo.value + "' ";
*/    
    var sqlid5="PEdorTypeWTInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql5.getString();
    
    var drr = easyExecSql(strSQL);  
    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.CustomGetPolDate.value = drr[0][0];
        //drr[0][1]==null||drr[0][1]=='null'?'0':fm.ReceiveDate.value = drr[0][1];
        drr[0][2]==null||drr[0][2]=='null'?'0':fm.CvaliDate.value = drr[0][2];

/*        strSQL = " select count(*) from lccontstate  " +
                 " where statetype in('Available') and state = '1' " +
                 " and  ( (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate >= '" + fm.EdorItemAppDate.value + "' ) " +
                 " or (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate is null )) " +
                 " and polno = '" + MainPolNo + "' ";
*/        
    var sqlid6="PEdorTypeWTInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
	mySql6.addSubPara(fm.EdorItemAppDate.value);
	mySql6.addSubPara(fm.EdorItemAppDate.value);
	mySql6.addSubPara(MainPolNo);
	strSQL=mySql6.getString();
        
        var frr = easyExecSql(strSQL);
        var CalDate;
        if ( frr )
        {
            frr[0][0]==null||frr[0][0]=='null'?'0':disAvailable = frr[0][0];
            //alert(disAvailable);
            //�ж������Ƿ�ʧЧ
            if (disAvailable == 0)
            {
                //û��ʧЧ

                //�жϽ�������

                var intv = dateDiffCT(fm.EdorItemAppDate.value, MainPayToDate, "D");
                //alert(intv);
                if (intv > 0)
                {
                    CalDate = fm.EdorItemAppDate.value;
                }
                else
                {
                    if (MainPayIntv == 0 || MainPayToDate == MainPayEndDate)
                    {
                        CalDate = fm.EdorItemAppDate.value;
                    }
                    else
                    {
                        CalDate = MainPayToDate;
                    }
                }

            }
            else
            {
                CalDate = MainPayToDate;
            }
        }

           // alert("�ڽ�1");
            //�ж�����
           // alert(MainPayEndDate);
//alert(MainPayToDate);

/*        strSQL = " select riskcode from lmrisksort " +
                 " where riskcode = '" + MainRiskCode +
                 "' and risksorttype = '30' ";
*/        
    var sqlid7="PEdorTypeWTInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(MainRiskCode);//ָ������Ĳ���
	strSQL=mySql7.getString();
        
        var srr = easyExecSql(strSQL);
        if ( srr )  //����ǲ����ڽ��ѣ����������ʾʵ���ѹ����
        {
            CalDate = fm.EdorItemAppDate.value;
        }

        var intval = dateDiffCT(fm.CvaliDate.value, CalDate, "M");

        var year = (intval- intval%12)/12;
        var month = intval%12;
        fm.Inteval.value = year + " �� �� " + month + " ��";

        if (MainPayIntv == 0)
        {
            //alert("����");
            //�ж����޸�����
/*            strSQL = " select paytodate from lcPol " +
                     " where contno = '" + fm.ContNo.value +
                     "' and polno <> mainpolno and rownum = 1 and appflag = '1' ";
*/            
    var sqlid8="PEdorTypeWTInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql8.getString();
            
            var mrr = easyExecSql(strSQL);
            if ( mrr )
            {
                mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
            }
            else
            {
                fm.PayToDate.value = "";
            }
        }
        else
        {
            //alert("�ڽ�");
            //�ж�����
            //alert(MainPayEndDate);
            //alert(MainPayToDate);
            if (MainPayEndDate == MainPayToDate)
            {
                //�ж����޸�����
/*                strSQL = " select paytodate from lcPol " +
                         " where contno = '" + fm.ContNo.value +
                         "' and polno <> mainpolno and rownum = 1  and appflag = '1' ";
*/                
    var sqlid9="PEdorTypeWTInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql9.getString();
                
                var mrr = easyExecSql(strSQL);
                if ( mrr )
                {
                    mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
                }
                else
                {
                    fm.PayToDate.value = "";
                }
            }
            else
            {
                fm.PayToDate.value = MainPayToDate;
            }
        }
    }
}

function edorTypeCTSave()
{

    var row = PolGrid.mulLineCount;
    //�����ж���ѡ�������˱�ʱ�����ѡ�����գ���Ҫ�󽫸�����һ��ѡ��
    var tFlag=false,tPolFLag=false; //���գ������ձ��
    var tNoFlag=false,tPolNoFLag=false; //���գ������ձ��
    var i = 0;
      for(var m = 0; m < row; m++ )
      {
        if(PolGrid.getChkNo(m))
        {
         if(isMainPolno(PolGrid.getRowColData(m,8)))
         {
        	tFlag=true;
         }else
         	{        		
         	tPolFLag=true;	
         		}
          i = i+1;
        }
        if(!PolGrid.getChkNo(m))
        {
        if(!isMainPolno(PolGrid.getRowColData(m,8)))
         {
        	tNoFlag=true;
         }
         else
         	{        		
         	tPolNoFLag=true;	
         		}
        	}
      }
      if(i == 0)
      {
        alert("��ѡ����Ҫ�˱������֣�");
        return false;
      }

      if(tNoFlag&&tFlag)
      {
        alert("�Ѿ�ѡ�����գ���ѡ�񸽼���һ���˱�");
        return false;
      }
     //�ж��������˻��ǲ���������
     if(tFlag)
     {
     	fm.WTContFLag.value="1";
     }else
     	{
     	fm.WTContFLag.value="0";
     		}

    if (fm.SurrReason.value == null || fm.SurrReason.value == "")
    {
        alert("��¼���˱�ԭ��!");
        return;
    }
	    if(fm.SurrReason.value=='06')
      {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    	}
    	if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("�ñ����в�����¼����¼�벹����¼��");
    			fm.LostTimes.focus();
    			return;
    		}
    		
    		if(fm.LostTimes.value != fm.TrueLostTimes.value)
    		{
    			alert("����Ĳ�����������ȷ���ʵ��");
    			return;
    		}
    		//return;
    	}
    	//alert(fm.TrueLostTimes.value);
    	//return;
    //<!-- XinYQ modified on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : BGN -->
    //<!-- SunSX modified on 2008-08-15 ; ���в�����Ϣ��ҳ���ʼ��ʱ��ʾ��У��¼�� END -->
    //if (!checkIsReissue()) return; 
    //<!-- XinYQ modified on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : END -->
    //<!-- PST modified on 2007-12-01 : �����ԥ���˱������ѿ۳���� : BGN -->
  if(fm.FeeGetFlag.checked==true)  //
  {
  	  fm.FeeGetFlag.value='1';
  	}else{
  		fm.FeeGetFlag.value='0';
  		}
   if(tFlag)
   {
	   tPolFLag=false;
	 }
 if(tPolFLag&&fm.FeeGetFlag.value=='1')
 {
 	    alert("ֻ��ѡ�����ս�����ԥ���˱�����Ҫ�۳�������");
    	return;
 	} 	
 	//��������ԥ���˱�Ĭ�ϲ��۳�������
 	if(fm.SaleChnl.value=='03' && fm.FeeGetFlag.value=='1')
 	{
 		if(!confirm("����������ԥ���˱�Ĭ�ϲ��۳������ѣ���ѡ��۳��Ƿ����"))
 		{
 			return ;
 			}
 	}else if(fm.SaleChnl.value!='03' && fm.FeeGetFlag.value=='0')
 		{
 		 if(!confirm("������ԥ���˱�û��ѡ��۳������ѣ��Ƿ����"))
 		{
 			return ;
 		}		
 	  }	
 	  
 	    //������˸����գ���ÿ���������Ƿ�����ԥ�ڽ����жϣ����û�У��򲻿��Խ����˱�
 	    if(fm.WTContFLag.value=="0")
 	    {
      for(var k = 0; k < row; k++ )
      {
        if(PolGrid.getChkNo(k))
        {
         if(!isExistUW(PolGrid.getRowColData(k,1)))
         {
             alert("����"+PolGrid.getRowColData(k,1)+"û����ԥ�ڣ������Խ�����ԥ���˱�");
             return false;
         }         
        }
      }	  	    	
 	    }
 	     //��������ˣ��������Ƿ�����ԥ�ڽ����жϣ����û�У��򲻿��Խ����˱�
 	    if(fm.WTContFLag.value=="1")
 	    {
      for(var k = 0; k < row; k++ )
      {
        if(PolGrid.getChkNo(k))
        {
         if(!isExistUW(PolGrid.getRowColData(k,1))&&isMainPolno(PolGrid.getRowColData(k,8)))
         {
             alert("����"+PolGrid.getRowColData(k,1)+"û����ԥ�ڣ������Խ�����ԥ���˱�");
             return false;
         }         
        }
      }	  	    	
 	    }
 
    //<!-- XinYQ modified on 2006-03-01 : �����ԥ���˱������ѿ۳���� : END -->
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.submit();
}


//<!-- XinYQ added on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : BGN -->
/*============================================================================*/

/**
 * ����Ƿ��ǲ�����ӡ����
 */
function checkIsReissue()
{
    var QuerySQL, arrResult;
    //��ѯ�Ƿ�������������
/*    QuerySQL = "select 'X' "
             +   "from LPEdorItem "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +    "and EdorType = 'LR' "
             +    "and EdorState = '0'";
    //alert(QuerySQL);
*/    
    var sqlid10="PEdorTypeWTInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql10.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ƿ������������������쳣�� ");
        return true;
    }
    if (arrResult != null)
    {
        var sConfirmMsg = "�ñ�������������";
        //��ѯ����������ӡ����
/*        QuerySQL = "select distinct max(MakeDate) "
                 +   "from LDContInvoiceMap "
                 +  "where 1 = 1 "
                 +  getWherePart("ContNo", "ContNo")
                 +    "and OperType = '4'";
        //alert(QuerySQL);
*/        
    var sqlid11="PEdorTypeWTInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql11.getString();
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ���������³����Ĵ�ӡ���ڳ����쳣�� ");
            return true;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            sConfirmMsg += "���һ�δ�ӡ����Ϊ " + trim(arrResult[0][0]) + "��";
        }
        sConfirmMsg += "�Ƿ�����˱��� ";
        //ȷ����ʾ�˱�
        if (!confirm(sConfirmMsg))
        {
            return false;
        }
    }
    return true;
}

/*============================================================================*/
//<!-- XinYQ added on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : END -->


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if (FlagStr == "Succ")
    {
        try
        {
            //chkPol();
            getZTMoney();
            queryBackFee();
            top.opener.getEdorItemGrid();
            divBackFeeTotal.style.display="block";
        } catch (ex) {}
    }
}
function afterCodeSelect(cCodeName, Field)
{
	if( cCodeName == "SurrORDeReason" )
    {
    	if(Field.value=="06")
    	{
    		divRemarkInfo.style.display="block";
    		fm.Remark.value="";
    	}
    	else
    	{
    		divRemarkInfo.style.display="none";
    		fm.Remark.value="";
    	}
    }
}
function returnParent()
{
    top.close();
    top.opener.focus();
}

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}


/**
* �����������ڵĲ�,���ز������(M)������(D) (����������2.29��һ��)
* <p><b>Example: </b><p>
* <p>dateDiff("2002-10-1", "2002-10-3", "D") returns "2"<p>
* <p>dateDiff("2002-1-1", "2002-10-3", "M") returns "9"<p>
* @param dateStart ������
* @param dateEnd ��������
* @param MD ��ǣ���M��ΪҪ�󷵻ز����������D��ΪҪ�󷵻ز������
* @return �����������ڲ������(M)������(D)
*/
function dateDiffCT(dateStart,dateEnd,MD)
{
  if(dateStart==""||dateEnd=="")
  {
    return false;
  }
  if (typeof(dateStart) == "string") {
    dateStart = getDate(dateStart);
  }

  if (typeof(dateEnd) == "string") {
    dateEnd = getDate(dateEnd);
  }

  var i;
  if(MD=="D") //��������
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();
    var startT=new Date(startY,startM-1,startD);
    var endT=new Date(endY,endM-1,endD);
    var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
    return diffDay;
  }
  else //���¼����
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();

    if(endD<startD)
    {
      return (endY-startY)*12 + (endM-startM) -1;  //ֻ��������
    }
    else
    {
      return (endY-startY)*12 + (endM-startM);
    }
  }
}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}

/**
* ��ȡ���ڶ���
* @param strDate �����ַ���
* @param splitOp �ָ��
* @return �������ڶ���
*/
function getDate(strDate, splitOp) {
  if (splitOp == null) splitOp = "-";

  var arrDate = strDate.split(splitOp);
  if (arrDate[2] == "31")
  {
    arrDate[2] = "30";
  }
  if (arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
  if (arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];

  return new Date(arrDate[0], arrDate[1], arrDate[2]);

}

function getZTMoney()
{
/*    strSQL = " select getmoney,EdorReasonCode,EdorReason, standbyflag2,standbyflag1 " +
    		 " from lpedoritem " +
    		 " where edoracceptno = '" + fm.EdorAcceptNo.value +
    		 "' and edorno = '" + fm.EdorNo.value +
    		 "' and edortype = 'WT' ";
*/  
    var sqlid12="PEdorTypeWTInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
	mySql12.addSubPara(fm.EdorNo.value);
	strSQL=mySql12.getString();
     
    var brr = easyExecSql(strSQL);
    if ( brr )
    {
        //brr[0][0]==null||brr[0][0]=='null'?'0':fm.GetMoney.value     	= brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.SurrReason.value     	= brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.Remark.value          = brr[0][2];
        //brr[0][3]==null||brr[0][3]=='null'?'0':fm.RelationToAppnt.value  = brr[0][3];
       // brr[0][4]==null||brr[0][4]=='null'?'0':fm.CTType.value  = brr[0][4];
       brr[0][4]==0||brr[0][4]=='0'?fm.FeeGetFlag.checked = false:fm.FeeGetFlag.checked  = true;
       divBackFeeTotal.style.display="block";
       showOneCodeName('surrordereason','SurrReason','SurrReasonName');
       showInfo1();
    }
}

function showInfo1()
{
	    //alert(12);
	if(fm.SurrReason.value=='06')
    {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    }
    else
    {
    	divRemarkInfo.style.display="none";
    	fm.Remark.value="";
    }
}

function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";
	var sqlid13="PEdorTypeWTInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	mySql13.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tSQL=mySql13.getString();
		
		var ret = easyExecSql(tSQL);
		if(ret)
		{
			var tLostTimes = ret[0][0];
			if(tLostTimes > 0)
			{
				fm.TrueLostTimes.value = tLostTimes;
				divLRInfo.style.display="";
			}
			else
			{
				fm.TrueLostTimes.value = 0;
			}
		}
}

function checkPolInfo(spanId)
{
	var rowLine = spanId.substr(11,1);//��ǰ��ѡ�е��кš�
	if(document.all(spanId).all('InpPolGridChk').value=='1')
	{
		var riskcode = PolGrid.getRowColData(rowLine,1);
		var tPolNo = PolGrid.getRowColData(rowLine,8);
		//alert("���ֺţ�"+tPolNo);
/*		var tSQL =   "select distinct 1 "
  						 + " from lcpol c "
 							 + " where exists (select 1 from LLClaimPolicy l where c.polno = l.polno) "
      				 + " and exists (select 1 "
          		 + " from ljspay j "
               + " where j.othernotype in ('2', '3', '8')"
               + " and j.otherno = c.polno) and c.polno = '"+tPolNo+"'"
*/		
	var tSQL = "";
	var sqlid14="PEdorTypeWTInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	mySql14.addSubPara(tPolNo);//ָ������Ĳ���
	tSQL=mySql14.getString();
		
		var ret = easyExecSql(tSQL);
		if(ret && !confirm("�ñ���������������ڼ�¼������������"))
		{
			return;
		}
//		tSQL = "select 1 from lmedorcal where riskcode = '"+riskcode+"' and caltype = 'NoteMoney'";
		
	var sqlid15="PEdorTypeWTInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
	mySql15.addSubPara(riskcode);//ָ������Ĳ���
	tSQL=mySql15.getString();
		
		ret = easyExecSql(tSQL);
		if(ret != null && ret == 1)
		{
			if(fm.FeeGetFlag.checked==false && confirm("�����ִ��ڹ����Ѽ�¼����,�Ƿ�ѡ��۳������ѣ�"))
			{
				fm.FeeGetFlag.checked=true;
			}
		}
	}
}
/**�жϴ������Ƿ������գ�*/
function isMainPolno(tPolNo)
{
//	var tSQL="select 1 from lcpol  where polno='"+tPolNo+"' and mainpolno=polno and contno='"+document.all('ContNo').value+"'";
	
	var tSQL="";
	var sqlid16="PEdorTypeWTInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
	mySql16.addSubPara(tPolNo);//ָ������Ĳ���
	mySql16.addSubPara(document.all('ContNo').value);
	tSQL=mySql16.getString();
	
	var tFlag=easyExecSql(tSQL);
	//alert(tPolNo);
	if(tFlag==null)
	{
		return false;
	}else
		{
			return true;
			}
	
}

/**�жϴ������Ƿ������գ�*/
function isExistUW(tRiskCode)
{
//	var tSQL="select 1 from LMEdorWT where riskcode='"+tRiskCode+"'";
	
	var tSQL="";
	var sqlid17="PEdorTypeWTInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
	mySql17.addSubPara(tRiskCode);//ָ������Ĳ���
	tSQL=mySql17.getString();
	
	var tFlag=easyExecSql(tSQL);
	//alert(tPolNo);
	if(tFlag==null)
	{
		return false;
	}else
		{
			return true;
	}
	
}