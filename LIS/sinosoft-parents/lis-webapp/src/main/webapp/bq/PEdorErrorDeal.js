
//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                 //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();           //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageAllGrid = new turnPageClass();    //ȫ�ֱ���, ���������н����ҳ
var turnPageSelfGrid = new turnPageClass();   //ȫ�ֱ���, �ҵĹ������н����ҳ
var sqlresourcename = "bq.PEdorErrorDealInputSql";
/*============================================================================*/

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        queryAllGrid();
    }
}

/*============================================================================*/

/**
 * ��ѯ�������
 */
function queryAllGrid()
{
	  //alert(11);
    var QuerySQL = "";
/*    var QuerySQL = "select StandbyFlag1, "
                 +        "OtherNo, "
                 +        "decode(code,'BQ33','��ȫ�ܾ�֪ͨ��','BQ37','��ȫ���֪ͨ��','BQ42','��ȫ����֪ͨ��','BQ99','��ȫת��ʧ��֪ͨ��'), "
                 +        "(select edortype||'-'||(select edorname from lmedoritem  c where c.edorcode=a.edortype and appobj='I' ) from lpedoritem a where  a.EdorAcceptNo=b.StandbyFlag1 and rownum=1), "
                 +        "StandbyFlag4, "
                 +        "StandbyFlag6, "
                 +        "decode(StateFlag,'2','����','δ����'),prtseq,(case when ((select floor(sysdate-b.makedate) from dual ) >=20 and b.StateFlag!='2') then '��' else '��' end ) ,code, "
                 +   " (select othersign from ldcode where codetype = 'lettertype' and code=b.code )"
                 +   "from LOPRTManager  b "
                 +  "where 1=1 and StateFlag!='2' and code in (select code from ldcode where 1 = 1 and codetype = 'lettertype') "
                 +  getWherePart("StandbyFlag1", "EdorAcceptNo1")
                 +  getWherePart("ManageCom", "ManageCom1", "like")
                 +  getWherePart("OtherNo", "OtherNo1")
                 +  getWherePart("Code", "LetterType")
                 +  getWherePart("StandbyFlag4", "AcceptOperator1")
                 +  getWherePart("StandbyFlag5", "MakeDate1","<=")
                 +  "order by StandbyFlag1 desc, StandbyFlag5 desc";
*/
    //alert(QuerySQL);
    try
    {
    	var sqlid1="PEdorErrorDealInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.EdorAcceptNo1.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.ManageCom1.value);
		mySql1.addSubPara(fm.OtherNo1.value);
		mySql1.addSubPara(fm.LetterType.value);
		mySql1.addSubPara(fm.AcceptOperator1.value);
		mySql1.addSubPara(fm.MakeDate1.value);
		QuerySQL=mySql1.getString();
		
        turnPageAllGrid.pageDivName = "divTurnPageAllGrid";
        turnPageAllGrid.queryModal(QuerySQL, AllGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��챣����Ϣ�����쳣�� ");
        return;
    }
    if (AllGrid.mulLineCount == 0)
    {
       alert("û�к�����¼!");
    }
    document.all("divForceBankBack").style.display = "none";  
    document.all("divForceBack").style.display = "none";  
    document.all("divBack").style.display = "none";     
}

/*============================================================================*/

/*============================================================================*/

/**
 * ����ѡ�еĳ�챣��
 */
function dealSpotCheck()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ����ĺ����� ");
        return;
    }else if(AllGrid.getRowColData(nSelNo,10)=='BQ37') //��ȫ���֪ͨ��ǿ�ƻ�����ԭ���ж�
    	{
    		if(AllGrid.getRowColData(nSelNo,9)=='��')
    		{
    			  alert("����δ���ڲ����������ڻ��������Ҫ���������Խ�����������");
            return ;
    		}
    		
      if(!verifyFormElements())return;
      if(fm.MCanclReason.value==null || fm.MCanclReason.value =="")
      {
      alert("��ѡ��Ļ���ԭ��");
      return ;
    	
    	}
    
    if(fm.MCanclReason.value=="G" && fm.CancelReasonContent.value=="")
    {
    	alert("ѡ��Ļ���ԭ��Ϊ��������¼�볷����ע�� ");
      return ;
    }		
   }    	

       //alert(AllGrid.getRowColData(nSelNo,10));

    	  if(!confirm("ȷ����Ҫǿ�ƻ�������"))
    	  {
    	  	return ;
        }else
       	{
          fm.PrtSerNo.value = AllGrid.getRowColData(nSelNo, 8);
          //alert(fm.PrtSerNo.value );
          fm.EdorAcceptNo1.value = AllGrid.getRowColData(nSelNo, 1);
          fm.LetterType.value = AllGrid.getRowColData(nSelNo, 10);

        if (fm.EdorAcceptNo1.value == null || trim(fm.EdorAcceptNo1.value) == "")
        {
            alert("���棺�޷���ȡ������Ϣ�� ");
            return;
        }
        else
        {
           var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
         fm.ActionFlag.value="force"	
         document.getElementById("fm").submit();
        }
       		
      }
}


/**
 * ����ѡ�еĳ�챣��
 */
function dealCheck()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ����ĺ����� ");
        return;
    }else if(AllGrid.getRowColData(nSelNo,10)=='BQ37') //��ȫ���֪ͨ��ǿ�ƻ�����ԭ���ж�
    	{
    		
    		if(AllGrid.getRowColData(nSelNo,9)=='��')
    		{
    			  alert("�����Ѿ����ڲ��������������������Ҫ���������Խ���ǿ�ƻ���");
            return ;
    		}	
   }  
    		  	
   	  if(!confirm("ȷ����Ҫ��������"))
    	  {
    	  	return ;
        }else
       	{
          fm.PrtSerNo.value = AllGrid.getRowColData(nSelNo, 8);
          //alert(fm.PrtSerNo.value );
          fm.EdorAcceptNo1.value = AllGrid.getRowColData(nSelNo, 1);
          fm.LetterType.value = AllGrid.getRowColData(nSelNo, 10);
          //alert(fm.LetterType.value );
        if (fm.EdorAcceptNo1.value == null || trim(fm.EdorAcceptNo1.value) == "")
        {
            alert("���棺�޷���ȡ������Ϣ�� ");
            return;
        }
        else
        {

          var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
          fm.ActionFlag.value="normal"
         document.getElementById("fm").submit();
        }       		
       		}
}


/*============================================================================*/

/**
 * �鿴ѡ�еĳ�챣���ı�ȫ��ϸ
 */
function viewEdorDetail()
{
    var nSelNo = AllGrid.getSelNo() - 1;
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ��ѯ������ ");
        return;
    }
    else
    {
        var sEdorAcceptNo = AllGrid.getRowColData(nSelNo, 1);
        var sOtherNoType = AllGrid.getRowColData(nSelNo, 3);
        if (sEdorAcceptNo == null || trim(sEdorAcceptNo) == "" || sOtherNoType == null || trim(sOtherNoType) == "")
        {
            alert("���棺�޷���ȡ��ȫ����š��������������Ϣ�� ");
            return;
        }
        else
        {
            var sOpenWinURL = "../sys/BqDetailQueryFrame.jsp?Interface=../sys/BqDetailQuery.jsp";
            var sParameters = "&EdorAcceptNo=" + sEdorAcceptNo + "&OtherNoType=" + sOtherNoType;
            OpenWindowNew(sOpenWinURL + sParameters, "��ȫ�����ϸ��ѯ", "left");
        }
    } //nSelNo > 0
}

/*============================================================================*/

/**
* showCodeList �Ļص�����
*/
function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "backreason")
    {
        if (oCodeListField.value =="d")
        {
            try
            {

                document.all("divConment").style.display = "";
            }
            catch (ex) {}
        }else
        	{
             try
            {

                document.all("divConment").style.display = "none";
            }
            catch (ex) {}       		
        		
         }
    } 
}

function afterCodeSelect(sCodeListType, oCodeListField)
{
    sCodeListType = sCodeListType.toLowerCase();
    if (sCodeListType == "edorcancelmreason")
    {
    	
    	
    	      try
            {
            	   var str = new Array('A','B','C','D','E','F','G');
                //str=('A','B','C','D','E','F','G');
                //alert(str);
                for(var t=0;t<str.length;t++)
                {
                	//alert("divAppCancel"+str[t]);
                	if(str[t]!=oCodeListField.value)
                	{
                		 document.all("divAppCancel"+str[t]).style.display = "none";
                	}
               }
                document.all("divAppCancel"+oCodeListField.value).style.display = "";
                
            }
            catch (ex) {}

    } //EdorApproveReason
}


//��ʾ�������
function  disPlayBackInf()
{
	
	    var nSelNo = AllGrid.getSelNo() - 1;
      var sCode = AllGrid.getRowColData(nSelNo, 10);
      var sYQ = AllGrid.getRowColData(nSelNo, 9);
      //alert(sYQ);
    var strsql = "";
//    var strsql = "select othersign,code  from ldcode where codetype='lettertype' and  code='"+sCode+"'";
    
    	var sqlid2="PEdorErrorDealInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(sCode);//ָ������Ĳ���
		strsql=mySql2.getString();
    
    var brr = easyExecSql(strsql);
    if("1"==brr[0][0]&&'BQ37'==sCode)
    {
     try
       {
       if('��'==sYQ)
       {
       	 document.all("divForceBack").style.display = "";
       	}else
       		{
       		 document.all("divForceBack").style.display = "none";	
       }

       document.all("divBack").style.display = "";
        }
       catch (ex) {}    	
    	
    	}
        
	    if("1"==brr[0][0]&&('BQ99'==sCode ||'LP01'==sCode))
    {
     try
       {

       document.all("divForceBankBack").style.display = "";  
        }
       catch (ex) {}    	
    	
    	}else
    		{
    	document.all("divForceBankBack").style.display = "none";  
			
    	}
	}
/*============================================================================*/

function verifyFormElements()
{
	
	  var selectedIndex = -1;
    var i = 0;
    
    for (i=0; i<fm.SCanclReason.length; i++)
    {
        if (fm.SCanclReason[i].checked)
        {
            selectedIndex = i;
            //alert("��ѡ����� value �ǣ�" + fm.SCanclReason[i].value);
            fm.CancelReasonCode.value=fm.SCanclReason[i].value;
            //alert("��ѡ����� value �ǣ�" + fm.CancelReasonCode.value);
            return true;
        }
    }    
    if (selectedIndex < 0)
    {
        alert("��û��ѡ���κγ���ԭ��");
        return false ;
    }

}      
 

//<!-- JavaScript Document END -->
