/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-10-10, 2006-10-16, 2006-11-01, 2006-11-17
 * @direction: ��ȫ�����˱�����ű�
 ******************************************************************************/

//<!-- JavaScript Document BGN -->


/*============================================================================*/

var showInfo;                                      //ȫ�ֱ���, ������ʾ����, ������
var turnPage = new turnPageClass();                //ȫ�ֱ���, ��ѯ�����ҳ, ������
var turnPageCustomerGrid = new turnPageClass();    //ȫ�ֱ���, �����ͻ���ѯ�����ҳ
var turnPageOldBnfGrid = new turnPageClass();      //ȫ�ֱ���, ԭ�����˲�ѯ�����ҳ
var turnPageNewBnfGrid = new turnPageClass();      //ȫ�ֱ���, �������˲�ѯ�����ҳ

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
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try
        {
            top.opener.getEdorItemGrid();
            queryNewBnfGrid();
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ���ݲ�������(¼����ѯ)����������ť�Ƿ���ʾ
 */
function EdorQuery()
{
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ��ѯ����������Ϣ
 */
function getCustomerType()
{
    var sCustomerType;
    var sAppObj;    //���ָ����������帴��
    try
    {
        sAppObj = document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
    if (sAppObj != null && sAppObj == "G")
    {
        sCustomerType = "0|^I|��������";
    }
    else
    {
        sCustomerType = "0|^A|Ͷ����^I|��������";
    }
    return sCustomerType;
}

/*============================================================================*/

/**
 * ��ѯ����������Ϣ
 */
function queryPolInfo()
{
    var QuerySQL, arrResult;
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql1");
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);
    try
    {
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ����������Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        try
        {
            document.getElementsByName("RiskCode")[0].value = arrResult[0][0];
            document.getElementsByName("RiskName")[0].value = arrResult[0][1];
            document.getElementsByName("CValiDate")[0].value = arrResult[0][2];
            document.getElementsByName("PayToDate")[0].value = arrResult[0][3];
            document.getElementsByName("Prem")[0].value = arrResult[0][4];
        }
        catch (ex) {}
    } //arrResult != null
}

/*============================================================================*/

/**
 * ��ѯ�����ͻ����
 */
function queryCustomerGrid()
{
    var sAppObj;    //���ָ����������帴��
    try
    {
        sAppObj = document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
    
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");  
    mySql.setSqlId("PEdorTypeBCSql5");
 
    if (sAppObj != null && sAppObj != "G")
    {
    	  mySql.setSqlId("PEdorTypeBCSql2"); 	  
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);    	  
 
          
    }
    
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);      
 
    //�ڶ�������
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.PolNo.value);
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);
 
    try
    {
        turnPageCustomerGrid.pageDivName = "divTurnPageCustomerGrid";
        turnPageCustomerGrid.queryModal(mySql.getString(), CustomerGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ�����ͻ���Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ��ѯ�����������б���Ϣ
 */
function getInsuredCodeList()
{
    var sInsuredCodeList = "";
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql3");
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);
    mySql.addSubPara(fm.ContNo.value);	
    mySql.addSubPara(fm.PolNo.value);    
    
    try
    {
        sInsuredCodeList = easyQueryVer3(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ������������Ϣ�����쳣�� ");
        return;
    }
    return sInsuredCodeList;
}

/*============================================================================*/

/**
 * ��ѯԭ��������Ϣ
 */
function queryOldBnfGrid()
{
 
    try
    {
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeBC");
        mySql.setSqlId("PEdorTypeBCSql6");    	
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);
        turnPageOldBnfGrid.pageDivName = "divTurnPageOldBnfGrid";
        turnPageOldBnfGrid.queryModal(mySql.getString(), OldBnfGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯԭ��������Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ��ѯ����������Ϣ
 */
function queryNewBnfGrid()
{
 
    try
    {
    	  mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeBC");
        mySql.setSqlId("PEdorTypeBCSql7");    	
        mySql.addSubPara(fm.EdorNo.value);
        mySql.addSubPara(fm.EdorType.value);
        mySql.addSubPara(fm.ContNo.value);
        mySql.addSubPara(fm.PolNo.value);
        turnPageNewBnfGrid.pageDivName = "divTurnPageNewBnfGrid";
        turnPageNewBnfGrid.queryModal(mySql.getString(), NewBnfGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ����������Ϣ�����쳣�� ");
        return;
    }
}

/*============================================================================*/

/**
 * ����ԭ��������Ϣ������������Ϣ
 */
function copyTwoBnfGrid()
{
    try
    {
        NewBnfGrid.clearData();
    }
    catch (ex) {}
    var nOldBnfRowCount, nOldBnfColCount;
    try
    {
        nOldBnfRowCount = OldBnfGrid.mulLineCount;
        nOldBnfColCount = OldBnfGrid.colCount;
    }
    catch (ex) {}
    if (nOldBnfRowCount != null || nOldBnfRowCount > 0)
    {
        try
        {
            for (var i = 0 ; i < nOldBnfRowCount; i++)
            {
                //Row �� 0 ��ʼ; Col �� 1 ��ʼ
                NewBnfGrid.addOne();
                for (var k = 1; k < nOldBnfColCount; k++)
                {
                    NewBnfGrid.setRowColData(i, k, OldBnfGrid.getRowColData(i, k))
                }
            }
        }
        catch (ex) {}
    }
}

/*============================================================================*/

/**
 * ���ݵ�ǰ״̬���������
 */
function checkEdorState()
{
    var QuerySQL, arrResult;
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql4");
    mySql.addSubPara(fm.EdorAcceptNo.value);	
    mySql.addSubPara(fm.EdorNo.value);
    mySql.addSubPara(fm.EdorType.value);
    mySql.addSubPara(fm.ContNo.value);
    mySql.addSubPara(fm.PolNo.value);
    
    try
    {
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ����״̬��Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
        var sEdorState = arrResult[0][0];
        if (sEdorState != null && trim(sEdorState) == "3")    //�ȴ�¼��
        {
            copyTwoBnfGrid();
        }
        else
        {
            queryNewBnfGrid();
        }
    } //arrResult != null
}

/*============================================================================*/

/**
 * showCodeList �Ļص�����, ������������Ϣ
 */
function afterCodeSelect(sCodeListType, oCodeListField)
{
    if (sCodeListType == "CustomerType")
    {
        //�������������˶���Ϊ����
        if (oCodeListField.name.indexOf("NewBnfGrid") == -1)
        {
            return;
        }
        else
        {
            var nLastFocusRowNo = NewBnfGrid.lastFocusRowNo;
            if (nLastFocusRowNo != null && nLastFocusRowNo >= 0)
            {
                //�����˲���Ϊ��
                var sInsuredNo = NewBnfGrid.getRowColData(nLastFocusRowNo, 1);
                if (sInsuredNo == null || trim(sInsuredNo) == "")
                {
                    alert("����ѡ���һ�б����˺�������� ");
                    try
                    {
                        NewBnfGrid.setRowColData(nLastFocusRowNo, 11, "");
                    }
                    catch (ex) {}
                    return;
                }
                //���ָ����������帴��
                var sAppObj;
                try
                {
                    sAppObj = document.getElementsByName("AppObj")[0].value;
                }
                catch (ex) {}
                //Ͷ����
                if (oCodeListField.value == "A")
                {
                    if (sAppObj != null && sAppObj.trim() != "G")
                    {
                        try
                        {
                            //Ͷ����ʼ����ʾ�ڵ�һ��
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 4, CustomerGrid.getRowColData(0, 3));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 12, CustomerGrid.getRowColData(0, 4));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 13, CustomerGrid.getRowColData(0, 5));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 6, CustomerGrid.getRowColData(0, 6));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 7, CustomerGrid.getRowColData(0, 7));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 8, "");
                        }
                        catch (ex) {}
                    }
                    else
                    {
                        try
                        {
                            //���屣��û��Ͷ��������
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 4, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 5, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 6, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 7, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 8, "");
                        }
                        catch (ex) {}
                    }
                }
                //������
                else if (oCodeListField.value == "I")
                {
                    //���Ϳͻ��б��ĸ������˺�����ͬ
                    var nInsuredRowNo = -1;
                    var i = 0;
                    if (sAppObj != null && sAppObj.trim() != "G")
                    {
                        i = 1;    //�ŵ��µķֵ�û�� LCAppnt ����, �� 0 ��ʼ, ������ 1 ��ʼ
                    }
                    for (i; i < CustomerGrid.mulLineCount; i++)
                    {
                        if (CustomerGrid.getRowColData(i, 2) == sInsuredNo)
                        {
                            nInsuredRowNo = i;
                        }
                    }
                    //����ı����˺���Ϸ�
                    if (nInsuredRowNo >= 0)
                    {
                        try
                        {
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 4, CustomerGrid.getRowColData(nInsuredRowNo, 3));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 12, CustomerGrid.getRowColData(nInsuredRowNo, 4));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 13, CustomerGrid.getRowColData(nInsuredRowNo, 5));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 6, CustomerGrid.getRowColData(nInsuredRowNo, 6));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 7, CustomerGrid.getRowColData(nInsuredRowNo, 7));
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 8, "00");
                        }
                        catch (ex) {}
                    }
                    //����ı����˺���Ƿ�
                    else
                    {
                        try
                        {
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 1, "");
                            NewBnfGrid.setRowColData(nLastFocusRowNo, 11, "");
                        }
                        catch (ex) {}
                    }
                }
            } //NewBnfGrid.lastFocusRowNo != null
        }
    } //CodeListType == CustomerType
}

/*============================================================================*/

/**
 * ����Ǳ���ĸ�����, �������ʾ
 */
function sureAddtionalPol()
{
    var QuerySQL, arrResult;
 
    try
    {
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeBC");
        mySql.setSqlId("PEdorTypeBCSql11");
        mySql.addSubPara(fm.ContNo.value);	
        mySql.addSubPara(fm.PolNo.value);    	
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("���棺����Ƿ��Ǳ�����ջ򸽼�����Ϣ�����쳣�� ");
        return false;
    }
    if (arrResult != null)
    {
        if (!confirm("���α�ȫ������Ǹ����������ˡ��Ƿ������ "))
        {
            return false;
        }
    } //arrResult != null
    return true;
}

/*============================================================================*/

/**
 * �ύ��ȫ��Ŀ���
 */
function saveEdorTypeBC()
{
    var nNewBnfCount;
    try
    {
        NewBnfGrid.delBlankLine();
        nNewBnfCount = NewBnfGrid.mulLineCount;
    }
    catch (ex) {}
    

    if (nNewBnfCount != null && nNewBnfCount == 0)
    {
        if (!confirm("����������ϢΪ�գ���ɾ��������������Ϣ���Ƿ������ "))
        {
            return;
        }
    }
    else
    {
        if (!NewBnfGrid.checkValue2())
        {
            return;
        }
        //var tSumCount=0;
        //У��������� yaory-rewrite the rule of bnf-lot.
        var sumLiveBnf = new Array();
        var sumDeadBnf = new Array();
			  var LiveBnfFlag=false;
			  var DeadBnfFlag=false;
        for(var i=0;i<nNewBnfCount;i++)
        {
        	  //tSumCount+=parseFloat(NewBnfGrid.getRowColData(i,10));
        	  //yaory-update:origal author do not know how to distribute .
        	  if(NewBnfGrid.getRowColData(i, 3) == "1")
				    {	
				    	if(NewBnfGrid.getRowColData(i, 8)=="00")
				    	{
				    		alert('��������˲����Ǳ���!');
				    		return false;
				    	}
				    }
				    
				     if (NewBnfGrid.getRowColData(i, 3) == "0")
             {
             	 LiveBnfFlag = true;
               if (typeof(sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))]) == "undefined")
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = 0;
               sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = sumLiveBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] + parseFloat(NewBnfGrid.getRowColData(i, 10))*100;
             }
             else if (NewBnfGrid.getRowColData(i, 3) == "1")
             {
               if (typeof(sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))]) == "undefined")
               sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = 0;
               sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] = sumDeadBnf[parseInt(NewBnfGrid.getRowColData(i, 9))] + parseFloat(NewBnfGrid.getRowColData(i, 10))*100;
             	 DeadBnfFlag = true;
             }
        }
        //------
        for (i=0; i<sumLiveBnf.length; i++)
      {
        if (typeof(sumLiveBnf[i])!="undefined"){sumLiveBnf[i]=parseFloat(sumLiveBnf[i])/100;}
        if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]>1)
        {
            alert("��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i]+ " ������100%�������ύ��");
            return false;
        }
        else if (typeof(sumLiveBnf[i])!="undefined" && sumLiveBnf[i]<1)
        {
            alert("ע�⣺��������������˳�� " + i + " �����������Ϊ��" + sumLiveBnf[i] + " ��С��100%");
            return false;
        }
      }
      
      for (i=0; i<sumDeadBnf.length; i++)
      {
        if (typeof(sumDeadBnf[i])!="undefined"){sumDeadBnf[i]=parseFloat(sumDeadBnf[i])/100;}
        if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]>1)
        {

          alert("��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ������100%�������ύ��");
          return false;
        }
        else if (typeof(sumDeadBnf[i])!="undefined" && sumDeadBnf[i]<1)
        {
            alert("��������������˳�� " + i + " �����������Ϊ��" + sumDeadBnf[i] + " ��С��100%");
            return false;
        }
      }
        //if(tSumCount!=1)
        //{
        //		alert("�����˷���֮��Ӧ��Ϊ1");
        //		return;
        //}
    }
    //start-----------�������֤��д�Ա𼰳�������
    var tIDNo ;
    var tIDType ;
    var str ;
    var tBirthday ;
    var tSex ;
    var tRiskCode=fm.RiskCode.value ;
    var tBnfType ;
 
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorTypeBC");
    mySql.setSqlId("PEdorTypeBCSql8");
    mySql.addSubPara(tRiskCode);	              
              
  	var AliveResult = easyExecSql(mySql.getString(),1,0);
  	var trelationtoinsured;
  	var trelationtoinsuredSQL;
  	var trelationtoinsuredResult;
    for (i=0;i<NewBnfGrid.mulLineCount;i++){
		tIDType=NewBnfGrid.getRowColData(i,6);
		tIDNo=NewBnfGrid.getRowColData(i,7);
		if(tIDType=="0")
		{
			 tBirthday=getBirthdatByIdNo(tIDNo);
		   tSex=getSexByIDNo(tIDNo);
		   if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		   {
		   	  str="��"+(i+1)+"�����֤��λ���������!!!" ;
		   	  alert(str);
		   	  return;
		   }
		   else
		   {  
		   	  NewBnfGrid.setRowColData(i,13,tBirthday) ;
		   	  NewBnfGrid.setRowColData(i,12,tSex) ;
		   }
	  }
	 //---------end
	//start --�Ƿ��������������
	     //alert(AliveResult.length);
	    tBnfType=NewBnfGrid.getRowColData(i,3);
	    var tBnfIA=NewBnfGrid.getRowColData(i,11);
	    if(tBnfType=="0")
	    {
//	    	 if((AliveResult==null || AliveResult.length<=0)&&tRiskCode!='111502') //111502 ���ж�
//	    	 {
//		   	   alert("��"+(i+1)+"��¼��������������,�����������������Σ������������ˡ����Ѿ����������ˣ������������ʷ�������ݣ�����ϵ�����Ա���д���" );
//            return false;
         	    	   //���ԭ������������������Ҫ�����ڵĽ��бȽϣ����ͬһ������˵��ֻ�Ǳ������
//	    	 	 if(OldBnfGrid.mulLineCount>0)
//	    	 	 {
//	    	 	 	var tFlag=false;
//	    	 	 	for(var t=0;t<OldBnfGrid.mulLineCount;t++)
//	    	 	 	{	 			       	 	  
//	    	 	 		var rName=OldBnfGrid.getRowColData(t,4);
//	    	 	 		var nName=NewBnfGrid.getRowColData(i,4);
//	    	 	 		var rBnfType=NewBnfGrid.getRowColData(i,3);  	 	 		    	 	 				   	     
//	    	 	 		if(rName!=nName&&rBnfType==tBnfType) //������������˲���ͬһ���ˣ�����Ҫ��ʾ������ֻ�����������ж���
//	    	 	 		{
//									tFlag=true;
//									break;
//	    	 	 		}
//	    	 	 	}
//	    	 	 		if(tFlag)
//	    	 	 		{
//    	    	 str="��"+(i+1)+"��¼��������������,��������������Լ�������ɽ������������˵�����ָ��!!!" ;
//		   	     alert(str);
//		   	      return;
//	    	 	 			}
//	    	 	 	}	    	 	 		    	 	
//	    	 }else
//	    	 	{
 
         mySql=new SqlClass();
         mySql.setResourceName("bq.PEdorTypeBC");
         mySql.setSqlId("PEdorTypeBCSql9");
         mySql.addSubPara(fm.RiskCode.value);	   						 
						 
         var brr = easyExecSql(mySql.getString(),1,0);
 
         var tBnfFLag;
	       if(brr)
         {
             brr[0][0]==null||brr[0][0]=='null'?'0':tBnfFLag  = brr[0][0];
         }
         if(tBnfFLag=='I'&& (tBnfIA ==null || tBnfIA==""))
         {
         	alert("������������ֵ�������ֻ���Ǳ����ˣ��Ƿ����?")
         	return false;
         	}
	         if(tBnfFLag=='A'&& (tBnfIA ==null || tBnfIA==""))
         {
         	alert("������������ֵ�������ֻ���Ǳ����ˣ��Ƿ����?")
					return false;
         	}
	    	 		    	 		
	    	 		
//	    	 		}
	    }
	//end ----
	
	//add by jiaqiangli 2009-09-11 ������ϵ�µ������˱����У�����
	trelationtoinsured = NewBnfGrid.getRowColData(i,8);
 
  mySql=new SqlClass();
  mySql.setResourceName("bq.PEdorTypeBC");
  mySql.setSqlId("PEdorTypeBCSql10");
  mySql.addSubPara(fm.EdorItemAppDate.value);
  mySql.addSubPara(fm.ContNo.value);
  mySql.addSubPara(trelationtoinsured);
         
	trelationtoinsuredResult = easyExecSql(mySql.getString(),1,0);
 
	if (trelationtoinsuredResult != null && trelationtoinsuredResult == '1') {
		if (!confirm("��"+(i+1)+"����������Ϣ�У�"+"Ͷ����Ϊ�����˵Ĺ���������ָ���������˼���������������Ϊ�����ˡ��Ƿ������ ")) {
            return false;
        }
	}
	//add by jiaqiangli 2009-09-11 ������ϵ�µ������˱����У�����
	}
	  if (!sureAddtionalPol())
    {
        return;
    }
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
    document.forms[0].action = "PEdorTypeBCSubmit.jsp";
    document.forms[0].submit();
}

/*============================================================================*/

/**
 * ���ñ�������˱��
 */
function resetNewBnfGrid()
{
    if (confirm("�˲��������ñ����������ϢΪԭʼֵ���Ƿ������ "))
    {
        copyTwoBnfGrid();
    }
}

/*============================================================================*/

/**
 * ���±��鿴
 */
function showNotePad()
{
    var sMissionID, sSubMissionID, sOtherNo;
    try
    {
        sMissionID = top.opener.document.getElementsByName("MissionID")[0].value;
        sSubMissionID = top.opener.document.getElementsByName("SubMissionID")[0].value;
        sOtherNo = top.opener.document.getElementsByName("OtherNo")[0].value;
    }
    catch (ex) {}
    if (sMissionID == null || trim(sMissionID) == "" || sSubMissionID == null || trim(sSubMissionID) == "" || sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("���棺�޷���ȡ����������ڵ�����š��鿴���±�ʧ�ܣ� ");
        return;
    }
    var sOpenWinURL = "../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp";
    var sParameters = "MissionID="+ sMissionID + "&SubMissionID="+ sSubMissionID + "&ActivityID=0000000003&PrtNo="+ sOtherNo + "&NoType=1";
    OpenWindowNew(sOpenWinURL + "&" + sParameters, "���������±��鿴", "left");
}

/*============================================================================*/

/**
 * ����������
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
}


/*============================================================================*/


//<!-- JavaScript Document END -->
