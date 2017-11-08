/************************************************
 *
 *	��������: MulLine.js
 *	������: ���¼����
 *
 *************************************************/
/************************************************
 *	�ࣺ����������
 *************************************************/

function MulLineEnter(iFormName, iInstanceName)
{
    //����������Ҫ�û���ʼ��
    this.formName = iFormName || "fm";	//���ؼ�����
    this.instanceName = iInstanceName || "";	//ʵ������
    this.mulLineCount = 0;	//��������������
    this.canAdd = 1;	//�Ƿ�����������ӣ�ɾ��1��ʾ���ԣ�0��ʾ������
    this.canSel = 0;	//�Ƿ����ѡ��1��ʾ���ԣ�0��ʾ������
    this.showTitle = 1; //�Ƿ���ʵtitle 1��ʾ��ʾ��0��ʾ����ʾ
    this.displayTitle = 1;	//�Ƿ���ʾ���⣬1��ʾ��ʾ��0��ʾ����ʾ
    this.locked = 0;	//�Ƿ�������1��ʾ������0��ʾ�ɱ༭
    this.canChk = 0;	//�Ƿ���Ҫ����ѡ��,1��ʾ���Զ���ѡ��0��ʾ������
    
    //tongmeng 2009-05-08 modify
    //�Ƿ���Ҫȫѡ��
    this.daiplayCanChkAll = 0;
    
    this.colCount = 0;	//�������е���Ŀ
    this.hiddenPlus = 0;	//����,�Ƿ��������һ�еı�־��0Ϊ��ʾ��1Ϊ����
    this.hiddenSubtraction = 0;	//����,�Ƿ�����ɾ��һ�еı�־��0Ϊ��ʾ��1Ϊ����
    this.recordNo = 0;	//����,�����ҳ��ʾ������¼����ô��ʾǰ����ֵ��Ϊ����,��ô��2ҳ��ʾ����Ż������ҳ�����
    this.checkFlag = 0;	//����,��checkAll���������
    this.state = 0;	//����,�˲������ⲿ���κ�ʵ������,��_ResumeState����һ��ʹ��
    this.arraySave = new Array();	//���������洫���������
    this.arraySave2 = new Array();	//�������������������--�����Ƿ���ʾ����
    this.arraySaveOra = new Array();	//������˳��û�иı������

		this.editArrayStore = new Array();//�������༭��ť��Ϣ

    this.arraySaveWith = new Array();//�����仯������Ϣ��array
    this.arraySave3 = new Array();//�����仯������Ϣ��array
    this.arrayChoose = new Array();//����ѡ������Ϣ��array

    this.totalPre = 0;//table��ǰ׺����,����sel,chk ��.
    this.chkBoxEventFuncName = "";	//�����������ⲿ����CheckBox��ʱ��Ӧ���ⲿ������
    this.chkBoxEventFuncParm = " ";	//�����������ⲿ����CheckBox��ʱ��Ӧ���ⲿ��������Ĳ���
    this.chkBoxAllEventFuncName = "";	//�����������ⲿ����������ȫѡCheckBox��ʱ��Ӧ���ⲿ������
    this.selBoxEventFuncName = "";	//�����������ⲿ����RadioBox��ʱ��Ӧ���ⲿ������
    this.selBoxEventFuncParm = " ";	//�����������ⲿ����RadioBox��ʱ��Ӧ���ⲿ��������Ĳ���
    this.addEventFuncName = "";	//�����������ⲿ����+��ťʱ��Ӧ���ⲿ������
    this.addEventFuncParm = " ";	//�����������ⲿ����+��ť��ʱ��Ӧ���ⲿ��������Ĳ���
    this.delEventFuncName = "";	//�����������ⲿ����-��ťʱ��Ӧ���ⲿ������
    this.delEventFuncParm = " ";	//�����������ⲿ����-��ť��ʱ��Ӧ���ⲿ��������Ĳ���
    this.AllowSort = "1";	//����
    this.SortPage = "";	//������Grid��Ӧ��turnpage
    this.allowsort = "AllowSortFun";	//Grid��������ͨ��������turnpage�еĺ���
    this.windowWidth = window.document.body.clientWidth;
    this.windowHeight = window.document.body.clientHeight;
    this.mulLineNum = 1;	//����,����ͬһ�е�MulLine�ĸ�����Ĭ����1
    this.detailInfo = "";	//���֧�ֵ��������ڴ˴�������ʾ��Ϣ
    this.tableWidth = "";	//����table�Ŀ��
    this.newColOrder = new Array();//�е�˳�� add by wanglei
    this.editArray = new Array();//�����洢������Ŧ��Ϣ add by jinsh
    //�������Բ���Ҫ�û���ʼ��
    this.mulLineText = "";	//����������һ��ģ�������(�ڲ�ʹ�ã�
    this.mulLineTextTitle = "";	//���������ı��⣨�ڲ�ʹ�ã�
    //2006-04 ����������
    this.lastFocusRowNo = -1; //���һ�εõ��������(��0��ʼ)
    this.lastFocusColNo = -1; //���һ�εõ��������(��1��ʼ)

    //��ʼ�����һ�������У�spanID��-1 �ĳ�-2
    this.maxSpanID = -1;	//�������������SpanID��ֵ
    this.errorString = "";	//�ñ���Ϊ��ִ�з�������ʱ����ʾ�Ĵ�����Ϣ
    //jinsh20080927
    //this.mulLineEdit = "";
    //jinsh20080927
    //����
    this.loadPage = _LoadPage;
    this.setFieldValue = _SetFieldValue;
    this.clearData = _ClearData;
    this.findSpanID = _FindSpanID;
    this.delBlankLine = _DelBlankLine;
    this.delCheckTrueLine = _DelCheckTrueLine;	//ɾ��ѡ�е�CheckBox��
    this.delRadioTrueLine = _DelRadioTrueLine;	//ɾ��ѡ�е�RadioBox��
    this.lock = _Lock;
    this.unLock = _UnLock;
    this.getSelNo = _GetSelNo;
    this.getChkNo = _GetChkNo;
    this.checkAll = _CheckAll;
    this.checkBoxAll = _CheckBoxAll;
    this.checkBoxAllNot = _CheckBoxAllNot;
    this.checkBoxSel = _CheckBoxSel;
    this.checkBoxNotSel = _CheckBoxNotSel;
    this.loadMulLine = _LoadMulLine;
    this.loadMulLineArr = _LoadMulLineArr;
    this.updateField = _UpdateField;
    this.addOne = _AddOne;
    this.deleteOne = _DeleteOne;
    this.keyUp = _KeyUp;
    this.getErrStr = _GetErrStr;
    //tongmeng 2008-10-27 add
    this.setRowColClass=_SetRowColClass;
    this.setRowClass=_setRowClass;	//������Ӧ�е�class
    this.setRowColTitle=_SetRowColTitle;
    this.setRowColData = _SetRowColData;
    this.setRowColCurrency = _setRowColCurrency;
    this.getRowColData = _GetRowColData;
    this.getRowData = _GetRowData;
    this.detailClick = _detailClick;
    this.checkBoxClick = _CheckBoxClick;
    this.radioBoxClick = _RadioBoxClick;
    this.radioClick = _radioClick;
    this.resumeState = _ResumeState;
    this.checkValue = _CheckValue;
    this.checkValue2 = _CheckValue2;
    this.setFocus = _SetFocus;
    this.getFocus = _GetFocus;
    this.moveFocus = _MoveFocus;
    this.setPageMark = _SetPageMark;
    //2006-04 ����������
    this.getChkCount = _GetChkCount;
    this.selOneRow = _SelOneRow;
    this.chkOneRow = _ChkOneRow;

    this.moveSpan = _MoveSpan;
    this.mouseDownToResize = _MouseDownToResize;
    this.mouseMoveToResize = _MouseMoveToResize;
    this.mouseUpToResize = _MouseUpToResize;
    this.showColChoose = _ShowColChoose;
    this.submitChoose = _SubmitChoose;
    
    // 2009-03 ��ĳ����Ԫ��ֵ��ͬʱ���޸���ʽ
    this.setRowColDataCustomize = _SetRowColDataCustomize;
    this.setRowColDataCustomize1 = _SetRowColDataCustomize1;
    
    this.setRowColDataShowCodeList=_SetRowColDataShowCodeList
}



function _detailClick(cObj)
{
}

function _Lock(iTObj)
{
    var tObj = iTObj || this;
    if (tObj.locked != 1)
    {
        //���û��������ִ������
        try
        {
            tObj.locked = 1;
            //ע�⣺�����_SetFieldValue������"��ע��"��˵�����ֽ������
            //��Ϊ�����ǽ�_SetFieldValue������ģ�岿�ֵ��ı��滻�ַ�����
            //���_SetFieldValue()�У��ⲿ�ֵ��ı���ʽ���ˣ�����ҲҪ��Ӧ�仯
            ( tObj.formName + ".all('" + tObj.instanceName + "addOne').disabled=true");
            tObj.mulLineText = replace(tObj.mulLineText, "type=button value='-'", "type=button disabled value='-'");
            if (tObj.mulLineCount > 0)
            {
                if (tObj.mulLineCount == 1)
                {
                    _ResumeState();
                    try
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del').disabled=true");
                    }
                    catch(ex)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[0].disabled=true");
                    }
                }
                else
                {
                    for (i = 0; i < tObj.mulLineCount; i++)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[" + i + "].disabled=true");
                    }
                }
            }
        }
        catch(ex)
        {
            _DisplayError("��MulLine.js-->_Lock�����з����쳣:" + ex, tObj);
        }
    }
}


function _UnLock(iTObj)
{
    var tObj = iTObj || this;
    if (tObj.locked != 0)
    {
        //���������ִ�н���
        try
        {
            tObj.locked = 0;
            //ע�⣺�����_SetFieldValue������"��ע��"��˵�����ֽ������
            //��Ϊ�����ǽ�_SetFieldValue������ģ�岿�ֵ��ı��滻�ַ�����
            //���_SetFieldValue()�У��ⲿ�ֵ��ı���ʽ���ˣ�����ҲҪ��Ӧ�仯
            eval(tObj.formName + ".all('" + tObj.instanceName + "addOne').disabled=false");
            tObj.mulLineText = replace(tObj.mulLineText, "type=button disabled value='-'", "type=button value='-'");
            if (tObj.mulLineCount > 0)
            {
                if (tObj.mulLineCount == 1)
                {
                    _ResumeState();
                    try
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del').disabled=false");
                    }
                    catch(ex)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[0].disabled=false");
                    }
                }
                else
                {
                    for (i = 0; i < tObj.mulLineCount; i++)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[" + i + "].disabled=false");
                    }
                }
            }
        }
        catch(ex)
        {
            _DisplayError("��MulLine.js-->_UnLock�����з����쳣:" + ex, tObj);
        }
    }
}


/************************************************
 *�������ṩѡ�е�������������canSel����Ϊ1
 *���룺	��
 *�����	ѡ�������
 ************************************************
 */

function _GetSelNo(ObjInstance)
{
    var tObjInstance = ObjInstance || this;
    var i = 0;
    try
    {
        if (tObjInstance.mulLineCount > 0)
        {
            if (tObjInstance.mulLineCount == 1)
            {
                _ResumeState();
                try
                {
                    if (eval(tObjInstance.formName + ".all('" + tObjInstance.instanceName + "Sel').checked"))
                    {
                        return 1;
                    }
                }
                catch(ex)
                {
                    if (eval(tObjInstance.formName + ".all('" + tObjInstance.instanceName + "Sel')[0].checked"))
                    {
                        return 1;
                    }
                }
            }
            else
            {
                for (i = 0; i < tObjInstance.mulLineCount; i++)
                {
                    if (eval(tObjInstance.formName + ".all('" + tObjInstance.instanceName + "Sel')[" + i + "].checked"))
                    {
                        return i + 1;
                    }
                }
            }
        }
        else
        {
            return 0;
        }
    }
    catch(ex)
    {
        _DisplayError("��MulLine.js-->_GetSelNo�����з����쳣:" + ex, tObjInstance);
    }
    return 0;
}

function setupSelStyle(formName,instanceName,col,pageNo)
{
	var tObj = "";
	try{
		if( pageNo == null )
		{
			tObj = formName + ".all('" + instanceName + col + "').className"; 
		}
		else
		{
			tObj = formName + ".all('" + instanceName + col + "')[" + pageNo + "].className";
			
		}
		
		if( eval(tObj+"=='code8'")||eval(tObj+"=='codeselect'"))          
  		{     
  			eval(tObj+"='codeselect'");          
  		} 
  		else if( eval(tObj+"=='multiDatePicker'") || eval(tObj+"=='multiCurrency'") )
		{
			if( pageNo == null )
			{
				tObj = formName + ".all('Show" + instanceName + col + "').className";
			}
			else
			{
				tObj = formName + ".all('Show" + instanceName + col + "')[" + pageNo + "].className";
			}		
			eval(tObj+"='mulnotreadonlyt'");
		}         
  		else          
  		{          
  			eval(tObj+"='mulnotreadonlyt'");          
  		} 
  }catch(ex){}	
}

function cancelSelStyle(formName,instanceName,col,pageNo)
{
	var tObj = "";
	
	if( pageNo == null )
	{
		tObj = formName + ".all('" + instanceName + col + "').className"; 
	}
	else
	{
		tObj = formName + ".all('" + instanceName + col + "')[" + pageNo + "].className";
	}

	if( eval(tObj+"=='code8'") || eval(tObj+"=='codeselect'"))
	{
		eval(tObj+"='code8'");
	}
	else if( eval(tObj+"=='multiDatePicker'") || eval(tObj+"=='multiCurrency'") )
	{
		if( pageNo == null )
		{
			tObj = formName + ".all('Show" + instanceName + col + "').className";
		}
		else
		{
			tObj = formName + ".all('Show" + instanceName + col + "')[" + pageNo + "].className";
		}		
		eval(tObj+"='mulreadonlyt'");
	}
	else
	{
		eval(tObj+"='mulreadonlyt'");
	}
}


/************************************************
 *�������ж�ָ�����Ƿ�ѡ�У�������canSel����Ϊ1
 *���룺	��
 *�����	���ѡ�У�����true�����򷵻�false
 ************************************************
 */

function _radioClick(cObj, colcount)
{
    var ele = document.getElementById("span" + this.instanceName);
    var oldPageNo = ele.getAttribute("PageNoRadio");
	var oldPageNoCheckBox = ele.getAttribute("PageNoCheckBox");

    var pageNo;
    var iMax = 0;
    iMax = this.mulLineCount;
    try
    {
        for (i = 0; i < iMax; i++)
        {
            if (iMax == 1)
            {
                pageNo = "0";
            }
            else
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].checked"))
                {
                    pageNo = i;
                    break;
                }
            }
        }
        if (pageNo != null && pageNo != undefined)
        {
            ele.setAttribute("PageNoRadio",pageNo);
        }
    }
    catch(ex)
    {
        alert("_radioClick " + ex.message);
    }
    var fieldCount = colcount;
    var i = 0;
    try
    {
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel').checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value='1'")
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                    	//����һ�б�Ϊ��ɫ
                    	setupSelStyle(this.formName,this.instanceName,j);
                    }
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value=0")
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                    	//����һ�б��ԭɫ
                    	cancelSelStyle(this.formName,this.instanceName,j);
                    }
                }
            }
            catch(ex)
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel')[0].checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[0].value='1'")
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[0].value=0")
                }
            }
        }
        else
        {
			//oldPageNoΪ0��Ϊ�յ�ʱ�򣬽ű��ж��Ľ����һ�µ�
			if((oldPageNo!=undefined&&oldPageNo!=null&&oldPageNo!="")||oldPageNo=="0")
			{				
				eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + oldPageNo + "].value=0");				
				eval(this.formName + ".all('" + this.instanceName +"Sel')[" + oldPageNo + "].className='mulreadonlyt'");
				if(oldPageNoCheckBox==undefined||oldPageNoCheckBox==null||oldPageNoCheckBox.toString().indexOf(","+oldPageNo+",")==-1)
				{
					eval(this.formName + ".all('" + this.instanceName +"No')[" + oldPageNo + "].className='mulreadonlyt'");					
					for(j=1;j<fieldCount;j++)
				  	{        
				  		//��ԭ�ȵ��б��ԭɫ  
				  		cancelSelStyle(this.formName,this.instanceName,j,oldPageNo);         
				  	}
				}
			}
			eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + pageNo + "].value='1'")
			eval(this.formName + ".all('" + this.instanceName +"Sel')[" + pageNo + "].className='mulnotreadonlyt'");
			eval(this.formName + ".all('" + this.instanceName +"No')[" + pageNo + "].className='mulnotreadonlyt'");
			for(j=1;j<fieldCount;j++)
			{
				//��ѡ�е��б�Ϊ��ɫ
				setupSelStyle(this.formName,this.instanceName,j,pageNo);
			}
		}
        if (pageNo != null && pageNo != undefined)
        {
            ele.setAttribute("PageNo", pageNo);
        }
	}
	catch(ex)
	{
		_DisplayError("��MulLine.js-->_radioClick�����з����쳣:" + ex.message,this);
	}
}


function _RadioBoxClick(cObj, colcount)
{
    var fieldCount = colcount || this.colCount;
    //XinYQ modified on 2006-05-12 : ��� _RadioBoxClick ֻ������ѡ��ͺ��������
    //OLD : var fieldCount=colcount;
    var i = 0, iMax = 0;
    iMax = this.mulLineCount;
    try
    {
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel').checked"))

                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulnotreadonlyt'");
                    }
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value='0'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulreadonlyt'");
                    }
                 }
            }
            catch (ex)
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel')[0].checked"))

                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[0].value='1'")
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[0].value='0'")
                }
            }
        }
        else
        {
            for (i = 0; i < iMax; i++)
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + i + "].value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulnotreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))

                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='codeselect'");
                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulnotreadonlyt'");
                        }
                    }
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + i + "].value='0'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='code8'");

                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulreadonlyt'");
                        }
                    }
                }
            }
        }
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _RadioBoxClick �����з����쳣��" + ex, this);
    }
}

/************************************************

 *�������ж�ָ�����Ƿ�ѡ�У�������canChk����Ϊ1

 *���룺	��

 *�����	���ѡ�У�����true�����򷵻�false

 ************************************************

 */

function _GetChkNo(cIndex, cObj)

{

    var tObj = cObj || this;

    var i = 0;

    i = cIndex;

    var tReturn;

    var tStr;

    if (tObj.canChk == 0)

    {

        alert("no checkBox!");

        return false;

    }

    if (cIndex < 0 || cIndex >= tObj.mulLineCount)

    {

        alert("��MulLine.js-->getChkNo������ָ���˴������:" + cIndex);

        return false;

    }

    try

    {

        if (tObj.mulLineCount > 0 && i < tObj.mulLineCount)

        {

            if (tObj.mulLineCount == 1)

            {

                _ResumeState();

                tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";

                tReturn = eval(tStr);

                //����õ���ֵΪnull��˵��������Ϊ1ʱ��������Ŀ��ܣ�

                //����ǴӶ���ɾ����һ�У���ô���ܻ������Ϊ����������������һ��Ԫ�أ����Ի�Ҫ���±�

                //�������⣬������javaScript�л����������أ���ˣ�������Կ����Ը��������������

                try

                {

                    //��undefined���������������Ϊ�е�JS��֧��undefined������Ҫ��������

                    if (tReturn == undefined)

                    {

                        try

                        {

                            tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk')[0].checked";

                            tReturn = eval(tStr);

                        }

                        catch(ex)

                        {

                            tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";

                            tReturn = eval(tStr);

                        }

                    }

                }

                catch(ex)

                {
                }
                ;

                if (tReturn == null)

                {

                    try

                    {

                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk')[0].checked";

                        tReturn = eval(tStr);

                    }

                    catch(ex)

                    {

                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";

                        tReturn = eval(tStr);

                    }

                }

                return tReturn;

            }

            else

            {

                if (eval(tObj.formName + ".all('" + tObj.instanceName + "Chk')[" + i + "].checked"))

                {

                    return true;

                }

            }

        }

        else

        {

            return false;

        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_GetChkNo�����з����쳣:" + ex, tObj);

    }

    return false;

}


/************************************************

 *�������ж�ָ�����Ƿ�ѡ�У�������canChk����Ϊ1

 *���룺	��

 *�����	���ѡ�У�����true�����򷵻�false

 ************************************************

 */

function _GetChkNo(cIndex, cObj)

{

    var tObj = cObj || this;

    var i = 0;

    i = cIndex;

    var tReturn;

    var tStr;

    if (tObj.canChk == 0)

    {

        alert("no checkBox!");

        return false;

    }

    if (cIndex < 0 || cIndex >= tObj.mulLineCount)

    {

        alert("��MulLine.js-->getChkNo������ָ���˴������:" + cIndex);

        return false;

    }

    try

    {

        if (tObj.mulLineCount > 0 && i < tObj.mulLineCount)

        {

            if (tObj.mulLineCount == 1)

            {

                _ResumeState();

                tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";

                tReturn = eval(tStr);

                //����õ���ֵΪnull��˵��������Ϊ1ʱ��������Ŀ��ܣ�

                //����ǴӶ���ɾ����һ�У���ô���ܻ������Ϊ����������������һ��Ԫ�أ����Ի�Ҫ���±�

                //�������⣬������javaScript�л����������أ���ˣ�������Կ����Ը��������������

                try

                {

                    //��undefined���������������Ϊ�е�JS��֧��undefined������Ҫ��������

                    if (tReturn == undefined)

                    {

                        try

                        {

                            tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk')[0].checked";

                            tReturn = eval(tStr);

                        }

                        catch(ex)

                        {

                            tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";

                            tReturn = eval(tStr);

                        }

                    }

                }

                catch(ex)

                {
                }
                ;

                if (tReturn == null)

                {

                    try

                    {

                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk')[0].checked";

                        tReturn = eval(tStr);

                    }

                    catch(ex)

                    {

                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";

                        tReturn = eval(tStr);

                    }

                }

                return tReturn;

            }

            else

            {

                if (eval(tObj.formName + ".all('" + tObj.instanceName + "Chk')[" + i + "].checked"))

                {

                    return true;

                }

            }

        }

        else

        {

            return false;

        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_GetChkNo�����з����쳣:" + ex, tObj);

    }

    return false;

}


/************************************************

 *�������ж�ָ�����Ƿ�ѡ�У�������canChk����Ϊ1

 *���룺	��

 *�����	���ѡ�У�����true�����򷵻�false

 ************************************************

 */

function _CheckBoxClick(cObj, colcount)
{

    var ele = document.getElementById("span" + this.instanceName);

    var oldPageNo = ele.getAttribute("PageNoCheckBox");
	
	var oldPageNoRadio = ele.getAttribute("PageNoRadio");

    var pageNo = "";

    var realNo;

    var iMax = 0;

    iMax = this.mulLineCount;

    try
    {
        //		alert("��"+oldPageNo+"��"+pageNo);

        for (i = 0; i < iMax; i++)
        {

            if (iMax == 1)

            {

                pageNo = ",0,";

            }

            else

            {

                if (eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].checked"))

                {

                    pageNo += "," + i + ",";

                    if (oldPageNo != null && oldPageNo != undefined)

                    {

                        if(oldPageNo.toString().indexOf(","+i+",")==-1)

                        {

                            realNo = i;

                        }

                    }

                    else

                    {

                        realNo = i;

                        break;

                    }

                }

                else

                {

                    if (oldPageNo != null && oldPageNo != undefined)

                    {

                        if(oldPageNo.toString().indexOf(","+i+",")>=0)

                        {

                            realNo = i;

                        }

                    }

                }

            }

        }

        if (pageNo != null && pageNo != undefined)

        {

          	ele.setAttribute("PageNoCheckBox",pageNo);

        }

    }

    catch(ex)

    {

        alert("_CheckBoxClick" + ex.message);

    }

    //	alert(realNo);

    var i = 0;
    var fieldCount = colcount;
    try
    {
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Chk').checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value='1'")
                    eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                    	setupSelStyle(this.formName,this.instanceName,j);
                    }
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value=0")
                    eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                    	cancelSelStyle(this.formName,this.instanceName,j);
                    }
                }
            }
            catch(ex)
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value='1'")
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value=0")
                }
            }
        }
        else
        {
            if ( eval( this.formName + ".all('" + this.instanceName + "Chk')[" + realNo + "].checked") 
					& eval( this.formName + ".all('Inp" + this.instanceName + "Chk')[" + realNo + "].value!='1'") )
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + realNo + "].value='1'")
                eval(this.formName + ".all('" + this.instanceName + "Chk')[" + realNo + "].className='mulnotreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No')[" + realNo + "].className='mulnotreadonlyt'");
                for (j = 1; j < fieldCount; j++)
                {
                	setupSelStyle(this.formName,this.instanceName,j,realNo);
                }
            }
            else if (eval(this.formName + ".all('" + this.instanceName + "Chk')[" + realNo + "].checked==false")
                    	& eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + realNo + "].value!='0'") )
            {
             	eval( this.formName + ".all('Inp" + this.instanceName + "Chk')[" + realNo + "].value=0")
				eval( this.formName + ".all('" + this.instanceName +"Chk')[" + realNo + "].className='mulreadonlyt'");
				if(oldPageNoRadio==null||oldPageNoRadio=='')
				{
					oldPageNoRadio="x";	
				}
             	if(realNo!=oldPageNoRadio)
             	{        				
					eval( this.formName + ".all('" + this.instanceName +"No')[" + realNo + "].className='mulreadonlyt'");
					for(j=1;j<fieldCount;j++)
					{
						cancelSelStyle(this.formName,this.instanceName,j,realNo);
					}
             	}			
			}
		}
	}
	catch(ex)
	{
		_DisplayError("��MulLine.js-->_checkBoxClick�����з����쳣:" + ex,this);
	}
}


/************************************************

 *������ ʹ�����е�checkBox���ѡ�е�״̬

 *���룺	��

 *�����	���ѡ�У�����true�����򷵻�false

 ************************************************

 */

function _CheckBoxAll(cObj, colcount)
{
    var ele = document.getElementById("span" + this.instanceName);
	var oldPageNo = ele.getAttribute("PageNoCheckBox");
    var pageNo = "";
    var realNo;
    var iMax = 0;

    iMax = this.mulLineCount;
    try
    {
        for (i = 0; i < iMax; i++)
        {
            pageNo += "," + i + ",";
        }

        if (pageNo != null && pageNo != undefined)
        {
            ele.setAttribute("PageNoCheckBox",pageNo);
        }
    }
    catch(ex)
    {
        alert("_CheckBoxAll" + ex.message);
    }

    var fieldCount = colcount;

    if (this.canChk == 0)
    {
        alert("no checkBox!");
        return;
    }

    var i = 0;

    try
    {
        try
        {
            eval(this.formName + ".all('checkAll" + this.instanceName + "').value=1");
            eval(this.formName + ".all('checkAll" + this.instanceName + "').checked=true");
        }
        catch(ex)
        {
            alert("error->_CheckBoxAll");
        }

        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value=1");
                eval(this.formName + ".all('" + this.instanceName + "Chk').checked=true");
                eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulnotreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                for (j = 1; j < fieldCount; j++)
                {
					setupSelStyle(this.formName,this.instanceName,j);
                }
            }
            catch(ex)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value=1");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked=true");
            }
        }
        else
        {
            for (i = 0; i < iMax; i++)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + i + "].value=1");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].checked=true");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].className='mulnotreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulnotreadonlyt'");
                for (j = 1; j < fieldCount; j++)
                {
                	setupSelStyle(this.formName,this.instanceName,j,i);
                }
            }
        }
    }
    catch(ex)
    {
        _DisplayError("��MulLine.js-->_checkBoxAll�����з����쳣:" + ex, this);
    }

}


/************************************************

 *������ ʹѡ����е�checkBox���ѡ�е�״̬

 *���룺	�кţ���1��ʼ

 *�����	���ѡ�У�����true�����򷵻�false

 ************************************************

 */

function _CheckBoxSel(row, cObj)

{
    if (this.canChk == 0)

    {

        alert("no checkBox!");

        return;

    }

    var i = 0,iMax = 0;

    var rowNo = row;
    var ele = document.getElementById("span" + this.instanceName);
    var pageNo = ele.getAttribute("PageNoCheckBox");
    iMax = this.mulLineCount;

    if (rowNo > iMax || rowNo <= 0)

    {

        alert("�����кų�����Χ");

        return;

    }

    try

    {

        if (this.mulLineCount == 1)

        {

            _ResumeState();

            try

            {

                eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value=1");

                eval(this.formName + ".all('" + this.instanceName + "Chk').checked=true");

            }

            catch(ex)

            {

                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value=1");

                eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked=true");

            }

        }

        else

        {

            eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + (rowNo - 1) + "].value=1");

            eval(this.formName + ".all('" + this.instanceName + "Chk')[" + (rowNo - 1) + "].checked=true");

        }

        pageNo += ","+ (rowNo-1) + ",";
       	ele.setAttribute("PageNoCheckBox",pageNo);
    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_CheckBoxSel�����з����쳣:" + ex, this);

    }

}


function _CheckBoxNotSel(row, cObj)
{
    if (this.canChk == 0)
    {
        alert("no checkBox!");
        return;
    }
    var i = 0, iMax = 0;
    var rowNo = row;
    iMax = this.mulLineCount;
    if (rowNo > iMax || rowNo <= 0)
    {
        alert("�����кų�����Χ");
        return;
    }
    try
    {
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value='0'");
                eval(this.formName + ".all('" + this.instanceName + "Chk').checked=false");
            }
            catch (ex)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value='0'");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked=false");
            }
        }
        else
        {
            eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + (rowNo - 1) + "].value='0'");
            eval(this.formName + ".all('" + this.instanceName + "Chk')[" + (rowNo - 1) + "].checked=false");
        }
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _CheckBoxSel �����з����쳣��" + ex, this);
    }
}

/************************************************

 *������	ѡ������checkBox�� ��canChk��������á�

 *�ڲ���:	����checkFlag�����ж���ѡ�������л��ǳ�������ѡ�С�

 ************************************************

 */

function _CheckAll(cObjInstance, fieldCount)

{

    var tObjInstance;

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    if (tObjInstance.canChk == 0)

    {

        alert("no checkBox!");

        return;

    }

    if (eval(this.formName + ".all('checkAll" + this.instanceName + "').checked==true"))

    {

        this.checkBoxAll(this, fieldCount);

        this.checkFlag = 1;

    }

    else

    {

        this.checkBoxAllNot(this, fieldCount);

        this.checkFlag = 0;

    }

}


/************************************************

 *������ ʹ�����е�checkBox���û��ѡ�е�״̬

 *���룺	��

 *�����	���ѡ�У�����true�����򷵻�false

 ************************************************

 */

function _CheckBoxAllNot(cObj, colcount)
{
    var fieldCount = colcount;
	var ele = document.getElementById("span"+this.instanceName);
	var oldPageNoRadio = ele.getAttribute("PageNoRadio");
    
    if (this.canChk == 0)
    {
        alert("no checkBox!");
        return;
    }

    var i = 0,iMax = 0;
    iMax = this.mulLineCount;
    var ele = document.getElementById("span" + this.instanceName);

    ele.setAttribute("PageNoCheckBox","");

    try
    {
        try
        {
            eval(this.formName + ".all('checkAll" + this.instanceName + "').value=0");
            eval(this.formName + ".all('checkAll" + this.instanceName + "').checked=false");
        }
        catch(ex)
        {
            alert("error->_CheckBoxAllNot");
        }

        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value=0");
                eval(this.formName + ".all('" + this.instanceName + "Chk').checked=false");
                eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                for (j = 1; j < fieldCount; j++)
                {
					cancelSelStyle(this.formName,this.instanceName,j);
                }
            }
            catch(ex)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value=0");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked=false");
            }
        }
        else
        {
			for(i=0;i<iMax;i++)
			{
				eval( this.formName + ".all('Inp" + this.instanceName + "Chk')[" + i + "].value=0");
				eval( this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].checked=false");
				eval( this.formName + ".all('" + this.instanceName +"Chk')[" + i + "].className='mulreadonlyt'");
				if(oldPageNoRadio!=0&&(oldPageNoRadio==null||oldPageNoRadio==''))
				{
					oldPageNoRadio="x";	
				}

        		if(i!=oldPageNoRadio)
        		{
					eval( this.formName + ".all('" + this.instanceName +"No')[" + i + "].className='mulreadonlyt'");
					for(j=1;j<fieldCount;j++)
					{
        				cancelSelStyle(this.formName,this.instanceName,j,i);
					}
		        }
		        else
		        {
		        		//alert("ze");
		        }
			}
		}
	}
	catch(ex)
	{
		_DisplayError("��MulLine.js-->_checkBoxAllNot�����з����쳣:" + ex,this);
	}

}



/************************************************

 *������ ��ʾ�����������

 *���룺	����������

 *�����	û��

 *************************************************/

function _LoadMulLine(arrCols)
{
    this.editArrayStore = null;
    cStrPageName = this.instanceName;
    this.arraySaveWith = arrCols;
    this.arraySaveOra = arrCols;
    this.arraySave3 = Clone(arrCols);//����ԭʼ��Ϣ
    this.arraySave = arrCols;	//������������Ϣ
    _InitArrCol(arrCols, this);//��ʼ��Array�б��˳��
    _SetFieldValue(this.instanceName, arrCols, this, null);
    _LoadPage(this.instanceName, this);

}
/************************************************
 *  add by jinsh
 *  ���ɱ༭��Ŧ��Ԫ���mulline
 * *********************************************/
function _LoadMulLine(arrCols, arrEdits)
{
    //alert(arrEdits);
    this.editArrayStore = arrEdits;
    cStrPageName = this.instanceName;
    this.arraySaveWith = arrCols;
    this.arraySaveOra = arrCols;
    this.arraySave3 = Clone(arrCols);//����ԭʼ��Ϣ
    this.arraySave = arrCols;	//������������Ϣ
    _InitArrCol(arrCols, this);//��ʼ��Array�б��˳��
    _SetFieldValue(this.instanceName, arrCols, this, arrEdits);
    _LoadPage(this.instanceName, this);

}

function _UpdateField(cIndex, arrCol)
{
    var i = 0;
    i = cIndex;
    this.arraySave[i] = arrCol;
    _SetFieldValue(this.instanceName, this.arraySave, this);
    _LoadPage(this.instanceName, this);
}

/************************************************

 *������ ��ʾ�����������

 *���룺	����������

 *�����	û��

 *************************************************/

function _LoadMulLineArr(arrCols, cData)
{
    this.arraySaveOra = this.arraySaveWith;
    var arrCols = _ChangeArrCol(this.arraySaveOra, this);//����mulline��title��column
    this.arraySave = arrCols;	//������������Ϣ
    for (var n = 0; n < cData.length; n++)
    {
        cData[n] = _ChangeDataCol(cData[n], this);//��ҳʱ���������������ݵ�˳��
    }
    if(this.editArrayStore!=null)
    {
         _SetFieldValue(this.instanceName, arrCols, this, this.editArrayStore);
    }
    else
    {
        _SetFieldValue(this.instanceName, arrCols, this, null);
    }

    _LoadPageArr(this.instanceName, this, cData);
}
/*******************************
 *��ʼ��Array�б��˳��
 *��ʼ��Ĭ��Ϊ1,2,3,4,5.....���ε���
 *add by wanglei 2008-07-24
 *******************************/
function _InitArrCol(oldCol, cObjInstance)
{
    var oArray = new Array();
    var cArray = new Array();
    for (var z = 0; z < oldCol.length; z++)
    {
        oArray[z] = z + 1;
        cArray[z] = "checked";
    }
    cObjInstance.arrayChoose = cArray;
    //this.newColOrder=oArray;
    cObjInstance.newColOrder = oArray;
    if (cObjInstance.canSel == 1)
    {
        cObjInstance.totalPre = cObjInstance.totalPre + 1;
    }
    if (cObjInstance.canChk == 1)
    {
        cObjInstance.totalPre = cObjInstance.totalPre + 1;
    }
    //alert("this.newColOrder is"+this.newColOrder);
    //alert("cObjInstance.newColOrder is"+cObjInstance.newColOrder);
}
/*******************************
 *�����϶����˳������(newColOrder),��ҳʱ����mulline��title��column
 *add by wanglei 2008-07-24
 *******************************/
function _ChangeArrCol(oldCol, cObjInstance)
{
    var nArray = new Array();
    for (var k = 0; k < oldCol.length; k++)
    {
        for (var m = 0; m < cObjInstance.newColOrder.length; m++)
        {
            if (k + 1 == cObjInstance.newColOrder[m])
            {
                nArray[m] = new Array();
                nArray[m] = oldCol[k];
                break;
            }
        }
    }
    return nArray;

}
/*******************************
 *�����϶����˳������(newColOrder),��ҳʱ���������������ݵ�˳��
 *add by wanglei 2008-07-24
 *******************************/
function _ChangeDataCol(oldCol, cObjInstance)
{
    var nArray = new Array();
    for (var k = 0; k < oldCol.length; k++)
    {
        for (var m = 1; m < cObjInstance.newColOrder.length; m++)
        {
            if (k + 1 == cObjInstance.newColOrder[m] - 1)
            {
                nArray[m - 1] = new Array();
                nArray[m - 1] = oldCol[k];
                break;
            }
        }
    }
    return nArray;
}
/************************************************
 *���������ݴ�������飬�γ�ÿ���������ģ��
 *��ά�����ʽ���������п������ֵ�����Ƿ��ܹ�����
 *************************************************/

function _SetFieldValue(strPageName, iArray, cObjInstance, eArray)//����һ����eArray ������Ű�Ŧ��Ϣ.add by jinsh
{
    //alert(eArray);
    var text = "";
    var textTitle = "";
    cObjInstance.errorString = "";
    var boxWidth = 20; //radioBox ��checkBox ����Ŀ��
    var userWidth = 0; //�û�������п��ܺ�
    var rate = 1 / cObjInstance.mulLineNum;	//����body��Ⱥ��û������ȵı���
    var fieldCount = iArray.length;
    //�����е���Ŀ
    cObjInstance.colCount = fieldCount;
    var strPageName1 = strPageName + 1;
    var i = 0;
    var status = "";
    //�ж��Ƿ����ɾ��/���� button
    //����û���ʼ��ѡ�����,��ôģ����Ҳ��֮�仯
    if (cObjInstance.locked == 1)
    {
        status = "disabled";
    }
    try
    {
        if (fieldCount > 0)
        {
            //����������
            var tempText0 = iArray[0][0];	//�����е�����
            var tempText1 = iArray[0][1];	//�����п�
            var tempText2 = iArray[0][2];	//�������������ֵ
            var tempText4 = iArray[0][3];	//�������Ƿ���������
            var tempText5 = "";
            var tempText6 = "";
            var tempText7 = "";
            var tempText8 = "";
            var tempText9 = "";
            var tempText10 = "";
            var tempText11 = "";
            var tempText12 = "";
            var tempText13 = "";
            var tempText14 = "";
            var tempText15 = "";
            var tempText16 = "";
            var tempText17 = "";
            var tempText18 = "";
            var tempText19 = "";
            var tempText20 = "";
            var tempText21 = "";//���õ�ǰ���Ƿ���ʾʱ���ñ���ת���ֵĺ���������setRowColData����
            var tempText22 = "";
            var tempText23 = "";
            var tempText24 = ""; 
            var tempText25 = "";
            var tempText26 = ""; 
            
            var showTitleText = "";
            var strFocusEvent = "_CalcFocusRowColNo(" + cObjInstance.instanceName + ", this)";
            if (cObjInstance.showTitle == 1)
            {
                showTitleText = "onmouseover=_showtitle(this);";
            }
            text = "";
            if (cObjInstance.tableWidth == "")
            {
                text = text + "<table id='" + strPageName + "ExChange$SpanId$' class=muline border=0 CELLSPACING=0 CELLPADDING=0>";
                textTitle = textTitle + "<table id='" + strPageName + "ExChange' class=muline border=0 CELLSPACING=0 CELLPADDING=0>";
            }
            else
            {
                text = text + "<table id='" + strPageName + "ExChange$SpanId$' class=muline ALIGN=center border=0 CELLSPACING=0 CELLPADDING=0 width='" + cObjInstance.tableWidth + "'>";
                textTitle = textTitle + "<table id='" + strPageName + "ExChange' class=muline ALIGN=center border=0 CELLSPACING=0 CELLPADDING=1 width='" + cObjInstance.tableWidth + "'>";
            }
            text = text + "<tr>";
            textTitle = textTitle + "<tr>";
            //------------------------------------------------------------------------------------------
            //if((cObjInstance.canSel == 1)&&(cObjInstance.canChk == 1))
            if(false)
            {
                userWidth = userWidth + parseInt(boxWidth);
                if (cObjInstance.selBoxEventFuncName != null && cObjInstance.selBoxEventFuncName != "")
                {
                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
                    text = text + "<td class=muline>";
                   // text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='display:none' type=radio name=" + strPageName + "Sel onclick=\" " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");" + cObjInstance.selBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.selBoxEventFuncParm + "');\"";
                      text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='display:none' type=radio name=" + strPageName + "Sel onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");" + cObjInstance.selBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.selBoxEventFuncParm + "');\"";
                    //alert(text);
                    text = text + "</td>";
                    //alert(text);
                    //					textTitle = textTitle + "<td class=mulinetitle style='width:"+boxWidth+"'>";
                    textTitle = textTitle + "<td class=mulinetitle>";
                    textTitle = textTitle + "<input type=hidden class=mulinetitle readonly style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
                else
                {
                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
                    text = text + "<td class=muline>";
                  //  text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='display:none' type=radio name=" + strPageName + "Sel onclick=\"return " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");\"";
                      text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='display:none;' type=radio name=" + strPageName + "Sel onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");\"";
                    text = text + "</td>";
                    //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
                    textTitle = textTitle + "<td class=mulinetitle>";
                    textTitle = textTitle + "<input type=hidden class=mulinetitle readonly style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
                userWidth = userWidth + parseInt(boxWidth);
                if (cObjInstance.chkBoxAllEventFuncName != null && cObjInstance.chkBoxAllEventFuncName != "")
                {
                    //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
                    textTitle = textTitle + "<td class=mulinetitle>";
                   // textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\" " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");" + cObjInstance.chkBoxAllEventFuncName + "(this.checked,this);\"" + " style='width:" + boxWidth + "'>";
                      textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\"" + strFocusEvent + "; " +cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");" + cObjInstance.chkBoxAllEventFuncName + "(this.checked,this);\"" + " style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
                else
                {
                    //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
                    textTitle = textTitle + "<td class=mulinetitle>";
                   // textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\" " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");\" style='width:" + boxWidth + "'>";
                      textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");\" style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
                if (cObjInstance.chkBoxEventFuncName != null && cObjInstance.chkBoxEventFuncName != "")
                {
                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
                    text = text + "<td class=muline>";
                  //  text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"" + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");" + cObjInstance.chkBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.chkBoxEventFuncParm + "');\"";
                      text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");" + cObjInstance.chkBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.chkBoxEventFuncParm + "');\"";
                    text = text + "</td>";
                }
                else
                {
                    //CheckBox����Ӧ�ⲿ�ĺ���
                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
                    text = text + "<td class=muline>";
                   // text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"return " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");\"";
                             text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");\"";
                    text = text + "</td>";
                }
            }
            else
            {
                if (cObjInstance.canSel == 1)
                {
                    //����radioBox��
                    userWidth = userWidth + parseInt(boxWidth);
                    if (cObjInstance.selBoxEventFuncName != null && cObjInstance.selBoxEventFuncName != "")
                    {
                        //					text = text + "<td class=muline width='"+boxWidth+"'>";
                        text = text + "<td class=muline>";
                       // text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='width:" + boxWidth + "' type=radio name=" + strPageName + "Sel onclick=\" " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");" + cObjInstance.selBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.selBoxEventFuncParm + "');\"";
                          text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='width:" + boxWidth + "' type=radio name=" + strPageName + "Sel onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");" + cObjInstance.selBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.selBoxEventFuncParm + "');\"";
                        //alert(text);
                        text = text + "</td>";
                        //alert(text);
                        //					textTitle = textTitle + "<td class=mulinetitle style='width:"+boxWidth+"'>";
                        textTitle = textTitle + "<td class=mulinetitle>";
                        textTitle = textTitle + "<input class=mulinetitle readonly style='width:" + boxWidth + "'>";
                        textTitle = textTitle + "</td>";
                    }
                    else
                    {
                        //					text = text + "<td class=muline width='"+boxWidth+"'>";
                        text = text + "<td class=muline>";
                       // text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='width:" + boxWidth + "' type=radio name=" + strPageName + "Sel onclick=\"return " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");\"";
                          text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='width:" + boxWidth + "' type=radio name=" + strPageName + "Sel onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");\"";
                        text = text + "</td>";
                        //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
                        textTitle = textTitle + "<td class=mulinetitle>";
                        textTitle = textTitle + "<input class=mulinetitle readonly style='width:" + boxWidth + "'>";
                        textTitle = textTitle + "</td>";
                    }
                }
                if (cObjInstance.canChk == 1)
                {
                    //����checkBox��
                    userWidth = userWidth + parseInt(boxWidth);
                    if (cObjInstance.chkBoxAllEventFuncName != null && cObjInstance.chkBoxAllEventFuncName != "")
                    {
                        //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
                        textTitle = textTitle + "<td class=mulinetitle>";
                    //    textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\" " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");" + cObjInstance.chkBoxAllEventFuncName + "(this.checked,this);\"" + " style='width:" + boxWidth + "'>";
                          textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");" + cObjInstance.chkBoxAllEventFuncName + "(this.checked,this);\"" + " style='width:" + boxWidth + "'>";
                        textTitle = textTitle + "</td>";
                    }
                    else
                    {
                        //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
                        textTitle = textTitle + "<td class=mulinetitle>";
                       // textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\" " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");\" style='width:" + boxWidth + "'>";
                          textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");\" style='width:" + boxWidth + "'>";
                        textTitle = textTitle + "</td>";
                    }
                    if (cObjInstance.chkBoxEventFuncName != null && cObjInstance.chkBoxEventFuncName != "")
                    {
                        //					text = text + "<td class=muline width='"+boxWidth+"'>";
                        text = text + "<td class=muline>";
                       // text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"" + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");" + cObjInstance.chkBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.chkBoxEventFuncParm + "');\"";
                          text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"" + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");" + cObjInstance.chkBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.chkBoxEventFuncParm + "');\"";
                        text = text + "</td>";
                    }
                    else
                    {
                        //CheckBox����Ӧ�ⲿ�ĺ���
                        //					text = text + "<td class=muline width='"+boxWidth+"'>";
                        text = text + "<td class=muline>";
                        //text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"return " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");\"";
                          text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"return " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");\"";
                        text = text + "</td>";
                    }
                }
            }
            //-----------------------------------------------------------------------------------------------------------------------------------------------------
//            if (cObjInstance.canSel == 1)
//            {
//                //����radioBox��
//                userWidth = userWidth + parseInt(boxWidth);
//                if (cObjInstance.selBoxEventFuncName != null && cObjInstance.selBoxEventFuncName != "")
//                {
//                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
//                    text = text + "<td class=muline>";
//                    text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='width:" + boxWidth + "' type=radio name=" + strPageName + "Sel onclick=\" " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");" + cObjInstance.selBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.selBoxEventFuncParm + "');\"";
//                    //alert(text);
//                    text = text + "</td>";
//                    //alert(text);
//                    //					textTitle = textTitle + "<td class=mulinetitle style='width:"+boxWidth+"'>";
//                    textTitle = textTitle + "<td class=mulinetitle>";
//                    textTitle = textTitle + "<input class=mulinetitle readonly style='width:" + boxWidth + "'>";
//                    textTitle = textTitle + "</td>";
//                }
//                else
//                {
//                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
//                    text = text + "<td class=muline>";
//                    text = text + "<input type=hidden name='Inp" + strPageName + "Sel' value=0><input class=mulcommon style='width:" + boxWidth + "' type=radio name=" + strPageName + "Sel onclick=\"return " + cObjInstance.instanceName + ".radioClick(this," + fieldCount + ");\"";
//                    text = text + "</td>";
//                    //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
//                    textTitle = textTitle + "<td class=mulinetitle>";
//                    textTitle = textTitle + "<input class=mulinetitle readonly style='width:" + boxWidth + "'>";
//                    textTitle = textTitle + "</td>";
//                }
//            }
//            if (cObjInstance.canChk == 1)
//            {
//                //����checkBox��
//                userWidth = userWidth + parseInt(boxWidth);
//                if (cObjInstance.chkBoxAllEventFuncName != null && cObjInstance.chkBoxAllEventFuncName != "")
//                {
//                    //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
//                    textTitle = textTitle + "<td class=mulinetitle>";
//                    textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\" " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");" + cObjInstance.chkBoxAllEventFuncName + "(this.checked,this);\"" + " style='width:" + boxWidth + "'>";
//                    textTitle = textTitle + "</td>";
//                }
//                else
//                {
//                    //					textTitle = textTitle + "<td class=mulinetitle width='"+boxWidth+"'>";
//                    textTitle = textTitle + "<td class=mulinetitle>";
//                    textTitle = textTitle + "<input class=title type=checkbox name='checkAll" + cObjInstance.instanceName + "' onclick=\" " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");\" style='width:" + boxWidth + "'>";
//                    textTitle = textTitle + "</td>";
//                }
//                if (cObjInstance.chkBoxEventFuncName != null && cObjInstance.chkBoxEventFuncName != "")
//                {
//                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
//                    text = text + "<td class=muline>";
//                    text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"" + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");" + cObjInstance.chkBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.chkBoxEventFuncParm + "');\"";
//                    text = text + "</td>";
//                }
//                else
//                {
//                    //CheckBox����Ӧ�ⲿ�ĺ���
//                    //					text = text + "<td class=muline width='"+boxWidth+"'>";
//                    text = text + "<td class=muline>";
//                    text = text + "<input type=hidden name='Inp" + strPageName + "Chk' value=0><input class=mulcommon style='width:" + boxWidth + "' type=checkbox name=" + strPageName + "Chk onclick=\"return " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");\"";
//                    text = text + "</td>";
//                }
//            }
            tempText1 = tempText1.substr(0, tempText1.toLowerCase().indexOf("px"));
            userWidth = userWidth + parseInt(tempText1);//���������п�
            text = text + "<td class=muline width='" + tempText1 + "'>";
   //         textTitle = textTitle + "<td onclick=" + cObjInstance.instanceName + ".showColChoose(this,'" + strPageName + "'," + strPageName + ") class=mulinetitle><input class=mulinetitle readonly value='" + tempText0 + "' style='width:" + tempText1 + "'>";
   //         text = text + "<input class=mulreadonly name=" + strPageName + "No " + isReadOnly(tempText4) + " maxlength=" + tempText2 + " style='width:" + tempText1 + "' onclick=\"return " + cObjInstance.instanceName + ".detailClick(this);\" title='" + cObjInstance.detailInfo + "'>";
              
           // var strFocusEvent = "_CalcFocusRowColNo(" + cObjInstance.instanceName + ", this)";
            textTitle = textTitle + "<td onclick=\"" + strFocusEvent + "; " +  cObjInstance.instanceName + ".showColChoose(this,'" + strPageName + "'," + strPageName + "); \" class=mulinetitle><input class=mulinetitle readonly value='" + tempText0 + "' style='width:" + tempText1 + "'>";
          
          
            text = text + "<input class=mulreadonly name=" + strPageName + "No " + isReadOnly(tempText4) + " maxlength=" + tempText2 + " style='width:" + tempText1 + "' onclick=\"" + strFocusEvent + "; " +  cObjInstance.instanceName + ".detailClick(this);\" title='" + cObjInstance.detailInfo + "'>";
 
            text = text + "</td>";
            textTitle = textTitle + "</td>";
            for (i = 1; i < fieldCount; i++)
            {
                tempText1 = iArray[i][1];	//�����п�
                //�о���ʹ��javascript�Դ��ĺ���Ч���ϻ��Щ
                tempText1 = tempText1.substr(0, tempText1.toLowerCase().indexOf("px"));
                userWidth = userWidth + parseInt(tempText1);//���ϸ����п�
            }

            userWidth = userWidth + 40;	//���ӿ�ȣ�û�ио�����40px�ķ���
            if (userWidth < cObjInstance.windowWidth)
            {
                if (trate != 0)
                {//add by wanglei 2008-08-12 ����Ǹı����ˣ��Ͳ���������rate�ˣ�������ԭ������
                    rate = trate;
                }
                else
                {
                    rate = (cObjInstance.windowWidth / userWidth) / cObjInstance.mulLineNum;
                }
            }

            for (i = 1; i < fieldCount; i++)
            {
                tempText0 = iArray[i][0];	//�����е�����
                tempText1 = iArray[i][1];	//�����п�
                tempText2 = iArray[i][2];	//�������������ֵ
                tempText4 = iArray[i][3];	//�������Ƿ���������,���أ�����ѡ��
                tempText5 = iArray[i][4];	//��������(���ݴӺ�̨���ݿ�ȡ)--������
                tempText6 = iArray[i][5];	//�������ö�Ӧ�Ķ��� (���ݴӺ�̨���ݿ�ȡ)
                tempText7 = iArray[i][6];	//�������ö�Ӧ�Ķ��е��ڲ�ֵ(���ݴӺ�̨���ݿ�ȡ)
                tempText8 = iArray[i][7];	//��Ӧ���ⲿ��js�����������ǵ�ǰ�е�spanID,�㴫������飩
                tempText9 = iArray[i][8];	//��Ӧ���ⲿ��js�����ĵ�2������
                tempText10 = iArray[i][9];	//��ʽУ��
                tempText11 = iArray[i][10];	//��������(���ݴ�ǰ̨����)--������
                tempText12 = iArray[i][11];	//��������(���ݴ�ǰ̨����)
                tempText13 = iArray[i][12];	//��������(���ݴ�ǰ̨����)--���ж���
                tempText14 = iArray[i][13];	//��������(���ݴ�ǰ̨����)
                tempText15 = iArray[i][14];	//�û����ø��г���
                tempText16 = iArray[i][15];	//���õ�ǰ�е�˫��������ʾ�����������ؼ����е�����
                tempText17 = iArray[i][16];	//���õ�ǰ�е�˫��������ʾ�����������ؼ���ֵ
                tempText18 = iArray[i][17];	//���õ�ǰ�е�˫��������ʾ�����������е�ֵ
                tempText19 = iArray[i][18];	//���õ�ǰ�е�˫���µ�������������Ŀ�ȣ�רΪcodeSelect�������:��8��������
                tempText20 = iArray[i][19];	//���õ�ǰ�е�˫����ǿ��ˢ��codeSelect����Դ��רΪcodeSelect�������:��7��������
                tempText21 = iArray[i][20];	//�˴����ã�����setRowColData�������жϸò������Ƿ񽫱���תΪ���ģ���
                tempText22 = iArray[i][21];
                tempText23 = iArray[i][22]; //����ָ������ֻ��ҵ�����
                tempText24 = iArray[i][23]; //����ָ�����ҿ��Ƿ���ֻ��
                tempText25 = iArray[i][24]; //���õ�ǰ��ʧȥ���㴥�����ⲿ����
                tempText26 = iArray[i][25]; //���õ�ǰ��ʧȥ���㴥�����ⲿ�����Ĳ�����
                 try
                {
                    if (tempText19 == undefined)
                    {
                        tempText19 = null;
                    }
                    if (tempText20 == undefined)
                    {
                        tempText20 = null;
                    }
                    if (tempText21 == undefined)
                    {
                        tempText21 = null;
                    }
                }
                catch(ex)
                {
                    tempText19 = null;
                    tempText20 = null;
                    tempText21 = null;
                }
                if (tempText15 == null)
                {
                    tempText15 = "";
                }

                tempText1 = tempText1.substr(0, tempText1.toLowerCase().indexOf("px"));
                tempText1 = parseInt(tempText1) * rate; //��ʵ�ʿ�������û����Ŀ��
                trate = rate;

                var tempTextTitle1 = tempText1 - 1;//add by wanglei 2008-07-25 for td e-resize
                if (tempText1 == 0)
                {
                    tempTextTitle1 = 0;
                }
                if (tempText4 == '3')
                {
                    textTitle = textTitle + "<td  class=mulinetitle style=\"display:'none'\">";
                    text = text + "<td class=mulinetitle style=\"display:'none'\"><input type=hidden name=" + strPageName + i + " value='" + tempText15 + "' maxlength=" + tempText2 + "";
                }
                else
                {
                    if (cObjInstance.AllowSort == true)
                    {
                        if (tempTextTitle1 == 0)
                        {
                            textTitle = textTitle + "<td  class=mulinetitle>";
                        }
                        else
                        {
//                            textTitle = textTitle + "<td  class=mulinetitle > <input onmousedown=" + cObjInstance.instanceName + ".moveSpan(this,'" + strPageName + "'," + strPageName + ") class=mulinetitle readonly value=" + tempText0 + " style='cursor:hand;width :" + tempTextTitle1 + "' >" + "<input onmousedown=" + cObjInstance.instanceName + ".mouseDownToResize(this); onmousemove=" + cObjInstance.instanceName + ".mouseMoveToResize(this,'" + strPageName + "'); " + " onmouseup=" + cObjInstance.instanceName + ".mouseUpToResize(this," + strPageName + "); style='cursor:e-resize;width:1'>";
                           		textTitle = textTitle + "<td  class=mulinetitle ><NOBR> <input onclick=\"OrderByName(" + cObjInstance.instanceName + ",'" + tempText0+"'); \" onmousedown=" + cObjInstance.instanceName + ".moveSpan(this,'" + strPageName + "'," + strPageName + ") class=mulinetitle readonly value='" + tempText0 + "' style='cursor:hand;width :" + tempTextTitle1 + "' >" + "<input onmousedown=" + cObjInstance.instanceName + ".mouseDownToResize(this); onmousemove=" + cObjInstance.instanceName + ".mouseMoveToResize(this,'" + strPageName + "'); " + " onmouseup=" + cObjInstance.instanceName + ".mouseUpToResize(this," + strPageName + "); style='cursor:e-resize;width:1;border:none;'></NOBR>";//2011-07-28 newupdate modify
                           		

                        }
                    }
                   // text = text + "<td class=muline width='" + tempText1 + "'><input name=" + strPageName + i + " value='" + tempText15 + "' id=" + strPageName + i + "r$row$ " + isReadOnly(tempText4) + " class=" + isReadOnlyClass(tempText4) + " maxlength=" + tempText2 + " ";
                      text = text + "<td class=muline width='" + tempText1 + "'><input name=" + strPageName + i + " value='" + tempText15 + "' id=" + strPageName + i + "r$row$ " + isReadOnly(tempText4) + " class=" + isReadOnlyClass(tempText4) + " maxlength=" + tempText2 + " onClick=\"" + strFocusEvent + ";\"" + " onFocus=\"" + strFocusEvent + ";\" ";
                    //<!-- XinYQ added on 2006-08-22 : ֧������������ֶζ��뷽ʽ : BGN -->
                    if (tempText4 == "4")
                    {
                        text = text + " type='password'";
                    }
                    else
                    {
                        //�����������Ͷ��������ڿ�,������ʾ
                        if( tempText4 != "8" && tempText4 != "7") text = text + " " + showTitleText;
                    }
                    if (tempText22 == "2")
                    {
                        text = text + " style='text-align:center;'";
                    }
                    else if (tempText22 == "3")
                    {
                        text = text + " style='text-align:right; padding-right:2px;'";
                    }
                    else
                    {
                        text = text + " style='text-align:left; padding-left:2px;'";
                    }
                    

 			if(tempText4 == "7"){
						if(tempText23 != "" && tempText23 != null && tempText23!= undefined){
	                    	if(tempText23.substring(0,3).toUpperCase() == "COL"){
	                    		text = text + " amtcol='" + strPageName + tempText23.substring(3) + "r$row$' " ;	
	                    	} else {
	                    		text = text + " moneytype='"+tempText23+"' " ;	
	                    	}							
						}
						if(tempText24 != "" && tempText24 != null && tempText24!= undefined ){
							text = text + " " + isReadOnly(tempText24) + " " ;	
						}
					}
                    //<!-- XinYQ added on 2006-08-22 : ֧������������ֶζ��뷽ʽ : END -->
                }
                if (tempText5 == null)
                {
                    if (tempText8 != null && tempText8 != "")
                    {
                        //������ã���ô��ʹ���Լ���д��javaScript������.js�ļ��� ,����������ǵ�ǰ�е�spanID��
                        text = text + " ondblclick=\"" + tempText8 + "('span$PageName$$SpanId$'," + tempText9 + ")" + ";\"";
                        //update-Liuliang-205-08-17
                        //��Ӧ���̻س��¼�����.js�ļ����Լ���д��javaScript����
                        text = text + " onkeyup=\"if(event.keyCode=='13'){" + tempText8 + "('span$PageName$$SpanId$'," + tempText9 + ")" + "};\"";
                    }
                    if (tempText22 != null && tempText22 != "")
                    {
                        //������ã���ô��ʹ���Լ���д��javaScript������.js�ļ��� ,����������ǵ�ǰ�е�spanID��
                        text = text + " onchange=\"" + tempText22 + "('span$PageName$$SpanId$'," + tempText9 + ")" + ";\"";
                        //update-Liuliang-205-08-17
                        //��Ӧ���̻س��¼�����.js�ļ����Լ���д��javaScript����
                        text = text + " onkeyup=\"if(event.keyCode=='13'){" + tempText22 + "('span$PageName$$SpanId$'," + tempText9 + ")" + "};\"";
                    }
                }
                else
                {
                    if (tempText6 == null || tempText7 == null)
                    {
                        //�����������ֻӦ����1����
                        if (tempText16 == null)
                        {
                            //��������������ؼ��������ж�
                            if (tempText20 == null && tempText19 == null)
                            {
                                text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this]);\"";
                                text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this]);\"";
                            }
                            else
                            {
                                if (tempText19 == null)
                                {
                                    text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null,null,null," + tempText20 + ");\"";
                                    text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null,null,null," + tempText20 + ");\"";
                                }
                                else
                                {
                                    text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\"";
                                    text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\"";
                                }
                            }
                        }
                        else
                        {
                            if (tempText17 != null && tempText17 != "")
                            {
                                //������������ռ��ֵ���ж�
                                if (tempText20 == null && tempText19 == null)
                                {
                                    text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "');\"";
                                    text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "');\"";
                                }
                                else
                                {
                                    if (tempText19 == null)
                                    {
                                        text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + ");\"";
                                        text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + ");\"";
                                    }
                                    else
                                    {
                                        text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                        text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                    }
                                }
                            }
                            else
                            {
                                if (tempText18 != null && tempText18 != "")
                                {
                                    //������������е�ֵ���ж�
                                    var tempValue = "[";
                                    arrText18 = tempText18.split(FIELDDELIMITER);
                                    for (var m = 0; m < arrText18.length; m++)
                                    {
                                        tempValue = tempValue + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrText18[m] + "').value";
                                        if (m != arrText18.length - 1)
                                        {
                                            tempValue = tempValue + ",";
                                        }
                                    }
                                    tempValue = tempValue + "]";
                                    if (tempText20 == null && tempText19 == null)
                                    {
                                        text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "');\"";
                                        text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "');\"";
                                    }
                                    else
                                    {
                                        if (tempText19 == null)
                                        {
                                            text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "'," + tempText20 + ");\"";
                                            text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "'," + tempText20 + ");\"";
                                        }
                                        else
                                        {
                                            text = text + " ondblclick=\"return showCodeList('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                            text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        //�����������Ӧ���ڶ����ϣ��η���Ŀǰ�����в�ͨ
                        var arrColName = "["; //��Ӧ�еļ��ϵĸ�ʽ
                        var arrCodeName = "["; //��Ӧ����ѡ����������
                        //�ָ����飬�õ���Ӧ����������
                        var arrayField = tempText6.split(FIELDDELIMITER);
                        var arrayCode = tempText7.split(FIELDDELIMITER);
                        //��ʽ������ѡ������ �� 0|1 ת��[0,1]
                        for (var m = 0; m < arrayCode.length; m++)
                        {
                            arrCodeName = arrCodeName + arrayCode[m];
                            if (m != arrayCode.length - 1)
                            {
                                arrCodeName = arrCodeName + ",";
                            }
                        }
                        arrCodeName = arrCodeName + "]";
                        //��ʽ���ж������� ��0|1 ת��[�ж����ж���]
                        for (var n = 0; n < arrayField.length; n++)
                        {
                            //arrColName=fm.all('spanXXXID').all('XXX+Col') ����ӦspanID���ж���
                            arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                            if (n != arrayField.length - 1)
                            {
                                arrColName = arrColName + ",";
                            }
                        }
                        arrColName = arrColName + "]";
                        if (tempText16 == null || tempText16 == "")
                        {
                            //��������������ؼ��������ж�
                            text = text + " ondblclick=\"return showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\"";
                            text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\"";
                        }
                        else
                        {
                            if (tempText17 != null && tempText17 != "")
                            {
                                //������������ռ��ֵ���ж�
                                text = text + " ondblclick=\"return showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                            }
                            else
                            {
                                if (tempText18 != null && tempText18 != "")
                                {
                                    //������������е�ֵ���ж�
                                    var tempValue = "[";
                                    arrText18 = tempText18.split(FIELDDELIMITER);
                                    for (var m = 0; m < arrText18.length; m++)
                                    {
                                        tempValue = tempValue + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrText18[m] + "').value";
                                        if (m != arrText18.length - 1)
                                        {
                                            tempValue = tempValue + ",";
                                        }
                                    }
                                    tempValue = tempValue + "]";
                                    text = text + " ondblclick=\"return showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                    text = text + " onkeyup=\"return showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                }
                            }
                        }
                    }
                }
                //���ǰ��������5,6,7,8,9���ǿգ���ô�жϵ�10����������Ƿ����
                if (tempText5 == null && tempText6 == null && tempText7 == null && tempText8 == null && tempText9 == null)
                {
                    if (tempText12 != null && tempText12 != "" && tempText11 != null && tempText11 != "")
                    {
                        if (tempText13 == null || tempText14 == null)
                        {
                            //ֻ��Ӧ��ǰ���д���ѡ��
                            if (tempText20 == null && tempText19 == null)
                            {
                                text = text + "CodeData='" + tempText12 + "' ondblClick=\"showCodeListEx('" + tempText11 + "',[this]);\" ";
                                text = text + "onkeyup=\"showCodeListKeyEx('" + tempText11 + "',[this]);\" ";
                            }
                            else
                            {
                                if (tempText19 == null)
                                {
                                    text = text + "CodeData='" + tempText12 + "' ondblClick=\"showCodeListEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + ");\" ";
                                    text = text + "onkeyup=\"showCodeListKeyEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + ");\" ";
                                }
                                else
                                {
                                    text = text + "CodeData='" + tempText12 + "' ondblClick=\"showCodeListEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\" ";
                                    text = text + "onkeyup=\"showCodeListKeyEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\" ";
                                }
                            }
                        }
                        else
                        {
                            //��Ӧ���л��߲��ǵ�ǰ�д���ѡ��
                            var arrColName = "["; //��Ӧ�еļ��ϵĸ�ʽ
                            var arrCodeName = "["; //��Ӧ����ѡ����������
                            //�ָ����飬�õ���Ӧ����������
                            var arrayField = tempText13.split(FIELDDELIMITER);
                            var arrayCode = tempText14.split(FIELDDELIMITER);
                            //��ʽ������ѡ������ �� 0|1 ת��[0,1]
                            for (var m = 0; m < arrayCode.length; m++)
                            {
                                arrCodeName = arrCodeName + arrayCode[m];
                                if (m != arrayCode.length - 1)
                                {
                                    arrCodeName = arrCodeName + ",";
                                }
                            }
                            arrCodeName = arrCodeName + "]";
                            //��ʽ���ж������� ��0|1 ת��[�ж����ж���]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') ����ӦspanID���ж���
                                arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                                if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
                            arrColName = arrColName + "]";
                            text = text + "CodeData='" + tempText12 + "' ondblclick=\"return showCodeListEx('" + tempText11 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\" ";
                            text = text + "onkeyup=\"return showCodeListKeyEx('" + tempText11 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\"";
                        }
                    }
                }
                if (tempText25 != null && tempText25 != "")
				{ 	
				    if (tempText25.indexOf("()") != -1)
				    { 
				        tempText25 = tempText25.replace("()", "");
				    }
				    arrText26=tempText26.split(FIELDDELIMITER);
				    var tempValue = '';
					for(var m=0;m<arrText26.length;m++)
					{
						tempValue=tempValue+cObjInstance.formName+".all('span$PageName$$SpanId$')"+".all('"+"$PageName$"+arrText26[m]+"')";
						if(m!=arrText26.length-1)
						{
							tempValue=tempValue+",";
						}
					}
				    text=text+"onblur=\""+tempText25+"('span$PageName$$SpanId$',"+ tempValue+")"+";\"";
				}
                
                if (tempText10 != null && tempText10 != "")
                {
                    //�����ҪУ��
                    textTitle = textTitle + "<input type=hidden name=" + strPageName + "verify" + i + " value='" + tempText10 + "'>";
                    text = text + " verify='" + tempText10 + "'";
                }
                else
                {
                    textTitle = textTitle + "<input type=hidden name=" + strPageName + "verify" + i + " value=''>";
                }
                //				text = text + " onkeyup='return " + cObjInstance.instanceName + ".keyUp(\"$PageName$\");'";
                if( tempText4 == "6" )
                {
                	text = text + " style='width:" + (tempText1-20) + "'>";
        		} 
        		else if( tempText4 == "7")
                {
				//	text = text + " style='width:0px;height:0px;' ShowClass='mulcommon' ShowStyle=\"Style='width:" + (tempText1-40) + "'\">";
										text = text + " style='width:0px;height:0px;' ShowClass='mulcommon' ShowStyle=\"Style='width:" + (tempText1) + "'\">";
        		} 
        		else if( tempText4 == "8")
                {
					//text = text + " style='width:0px;height:0px;' ShowClass='mulcommon' ShowStyle=\"Style='width:" + (tempText1-20) + "'\">";
										text = text + " ShowClass='mulcommon' ShowStyle=\"Style='width:" + (tempText1-20) + "'\">";
        		} 
        		else 
        		{
        			text = text + " style='width:" + tempText1 + "'>";
       			}
                text = text + "</td>";
                textTitle = textTitle + "</td>";
                //alert(text);
            }
            //jinsh20080927
            if ((eArray != null) && (eArray != '') && (eArray != ""))
            {
                var jindex = 0;
                var jnum = eArray.length;
                for(jindex=0;jindex<jnum;jindex++)   //@ todo
                {
                    var jtitle = eArray[jindex][0];
                    var jwidth = eArray[jindex][1];
                    var jfunc  = eArray[jindex][2];
                    var jbuts  = eArray[jindex][3];
                    if((jfunc!=null)&&(jfunc!='')&&(jfunc!=""))
                    {
                        text = text + "<td  class=muline><input readonly type=button class=cssButton style='width:"+jwidth+"'   value='"+jbuts+"' onmouseover=_showtitle(this) onclick=\"checkEdit(this);"+jfunc+"();\">";
                    }
                    else
                    {
                        text = text + "<td  class=muline><input readonly type=button class=cssButton style='width:"+jwidth+"'   value='"+jbuts+"' onmouseover=_showtitle(this) onclick=\"checkEdit(this);\">";
                    }

                    textTitle = textTitle + "<td class=mulinetitle><input class=mulinetitle   readonly style='width:"+jwidth+"' value='"+jtitle+"' onmouseover=_showtitle(this)>";
                    text = text + "</td>";
                    textTitle = textTitle + "</td>";
                }
                //text = text + "<td  class=muline><input readonly type=button class=cssButton style='width:40'   value='�༭' onmouseover=_showtitle(this) onclick=\"return jinsh(this);\">";
                //textTitle = textTitle + "<td class=mulinetitle><input class=mulinetitle   readonly style='width:40' value='�༭' onmouseover=_showtitle(this)>";
                //text = text + "</td>";//jinsh20080927
                //textTitle = textTitle + "</td>";//jinsh20080927
                //text = text + "<td  class=muline><input readonly type=button class=cssButton style='width:40'   value='ɾ��' onmouseover=_showtitle(this) onclick=\"return jinsh(this);\">";
                //textTitle = textTitle + "<td class=mulinetitle><input class=mulinetitle  readonly style='width:40' value='ɾ��' onmouseover=_showtitle(this)>";
                //text = text + "</td>";//jinsh20080927
                //textTitle = textTitle + "</td>";//jinsh20080927
            }
            //jinsh20080927
            if (cObjInstance.hiddenSubtraction == 0)
            {
                //������ؼ���"-"�ı�־=0����ô��ʾ����������
                text = text + "<td class=muline>";
                textTitle = textTitle + "<td class=mulinetitle width=15><input class=mulinetitle disabled readonly value='-' style='width :15'></td>";
                //��ע�⣬�������еĸ�ʽ�ǲ�������Ķ��ģ�����_Lock(),UnLock()�����������
                //����Ķ��������У�������޸�_Lock(),UnLock()��������ز���
                text = text + "<input class=button type=button " + status + " value='-' name='$PageName$Del' ";
                //�����Ҫ���-����Ӧ�ⲿ����
                if (cObjInstance.delEventFuncName != null && cObjInstance.delEventFuncName != "")
                {
                    text = text + " onclick=' return " + cObjInstance.delEventFuncName + "(\"span$PageName$$SpanId$\",\"" + cObjInstance.delEventFuncParm + "\");" + cObjInstance.instanceName + ".deleteOne(\"$PageName$\",span$PageName$$SpanId$);" + "'>";
                    // alert(text1)
                }
                else
                {
                    text = text + " onclick='return " + cObjInstance.instanceName + ".deleteOne(\"$PageName$\",span$PageName$$SpanId$);'>";
                }
                text = text + "<input type=hidden name=$PageName$SpanID value=span$PageName$$SpanId$>";
                text = text + "</td>";
            }
            else
            {
                text = text + "<td class=muline style='display:none'>";
                textTitle = textTitle + "<td class=mulinetitle width=15 style='display:none'><input class=mulinetitle type=hidden readonly value='-' style='width :15'></td>";
                //��ע�⣬�������еĸ�ʽ�ǲ�������Ķ��ģ�����_Lock(),UnLock()�����������
                //����Ķ��������У�������޸�_Lock(),UnLock()��������ز���0
                text = text + "<input class=button type=button " + status + " value='-' name='$PageName$Del' style='display:none'";
                text = text + " onclick='return " + cObjInstance.instanceName + ".deleteOne(\"$PageName$\",span$PageName$$SpanId$);'>";
                text = text + "<input type=hidden name=$PageName$SpanID value=span$PageName$$SpanId$>";
                text = text + "</td>";
            }
            text = text + "</tr>";
            textTitle = textTitle + "</tr>";
            text = text + "</table>";
            textTitle = textTitle + "</table>";
        }
        cObjInstance.mulLineText = text;	//������
        //alert(text);
        //alert(textTitle);
        //		fm.all('tt').value = text;
        cObjInstance.mulLineTextTitle = textTitle;	//�б���
    }
    catch(ex)
    {
        _DisplayError("��MulLine.js-->_SetFieldValue�����з����쳣:" + ex, cObjInstance);
    }
    //alert(textTitle);
    
    //alert(text)
}


/************************************************

 *�����������������ĳ�ʼ��

 *************************************************/

function _LoadPage(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    //	var innerHTML="";

    var tHTML = "";

    cObjInstance.errorString = "";


    //	var status="";

    var tStatus = "";

    //�ж��Ƿ����ɾ��/���� button

    //����û���ʼ��ѡ�����,��ôģ����Ҳ��֮�仯

    if (cObjInstance.locked == 1)

    {

        tStatus = "disabled";

    }


    if (cObjInstance.displayTitle == 1)

    {

        tHTML = tHTML + cObjInstance.mulLineTextTitle;

    }

    tHTML = tHTML + "<span id='span" + t_StrPageName + "Field'></span>";


    if (cObjInstance.hiddenPlus == 0)

    {

        //������һ�б�־"+"������

        tHTML = tHTML + "<div align=left>";

        if (cObjInstance.addEventFuncName != null && cObjInstance.addEventFuncName != "")

        {

            tHTML = tHTML + "<input class=button type=button name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "'); " + cObjInstance.instanceName + ".moveFocus(); "

                    + cObjInstance.addEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.addEventFuncParm + "');\">";

        }

        else

        {

            tHTML = tHTML + "<input type=button class=button name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "'); " + cObjInstance.instanceName + ".moveFocus();\">";

        }

        tHTML = tHTML + "</div>";

    }

    else

    {

        //�������һ�б�־"+"

        tHTML = tHTML + "<div align=left>";

        tHTML = tHTML + "<input type=button class=button style='display:none' name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "');" + cObjInstance.instanceName + ".moveFocus();\">"
        tHTML = tHTML + "</div>";

    }


    try

    {

        document.all("span" + t_StrPageName).innerHTML = tHTML;
        _AddOne(cObjInstance.instanceName, cObjInstance.mulLineCount, cObjInstance);

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_LoadPage�����з����쳣:" + ex, cObjInstance);

    }

}


/************************************************

 *�����������������ĳ�ʼ��

 *************************************************/

function _LoadPageArr(strPageName, cObjInstance, cData)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    //	var innerHTML="";

    var tHTML = "";

    cObjInstance.errorString = "";


    //	var status="";

    var tStatus = "";

    //�ж��Ƿ����ɾ��/���� button

    //����û���ʼ��ѡ�����,��ôģ����Ҳ��֮�仯

    if (cObjInstance.locked == 1)

    {

        tStatus = "disabled";

    }


    if (cObjInstance.displayTitle == 1)

    {

        tHTML = tHTML + cObjInstance.mulLineTextTitle;

    }

    tHTML = tHTML + "<span id='span" + t_StrPageName + "Field'></span>";


    if (cObjInstance.hiddenPlus == 0)

    {

        //������һ�б�־"+"������

        tHTML = tHTML + "<div align=left>";

        if (cObjInstance.addEventFuncName != null && cObjInstance.addEventFuncName != "")

        {

            tHTML = tHTML + "<input class=button type=button name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "'); " + cObjInstance.instanceName + ".moveFocus(); "

                    + cObjInstance.addEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.addEventFuncParm + "');\">";

        }

        else

        {

            tHTML = tHTML + "<input type=button class=button name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "'); " + cObjInstance.instanceName + ".moveFocus();\">";

        }

        tHTML = tHTML + "</div>";

    }

    else

    {

        //�������һ�б�־"+"

        tHTML = tHTML + "<div align=left>";

        tHTML = tHTML + "<input type=button class=button style='display:none' name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "');" + cObjInstance.instanceName + ".moveFocus();\">";

        tHTML = tHTML + "</div>";

    }


    try

    {

        document.all("span" + t_StrPageName).innerHTML = tHTML;

        //		fm.all('tt').value = tHTML;
        _AddOneArr(cObjInstance.instanceName, cObjInstance.mulLineCount, cObjInstance, cData);

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_LoadPage�����з����쳣:" + ex, cObjInstance);

    }

}


/************************************************

 *���������һ��(�ⲿ/�ڲ�����)

 ************************************************

 */

function _AddOne(strPageName, intNumber, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    var i,j;

    var strText;	//ÿ������

    var strFunctionName = "";	//��ִ����addone����õĺ�����

    var spanID = -1;	//spanID���

    var intCount;	//��ӵ��и���

    var tObjInstance;	//����ָ��

    var isInit;	//�ж��Ƿ����ڳ�ʼ��������


    if (cObjInstance == null)

    {

        tObjInstance = this;

        isInit = false;

    }

    else

    {

        tObjInstance = cObjInstance;

        isInit = true;

    }


    tObjInstance.errorString = "";


    (intNumber == null) ? intCount = 1 : intCount = intNumber;	//�õ��еĸ���



    //�Ա�����ֵ

    strText = tObjInstance.mulLineText;

    spanID = tObjInstance.maxSpanID;

    try

    {

        //�õ�ԭ��������

        var strOldText = document.all("span" + t_StrPageName + "Field").innerHTML;


        //���intCount��

        for (i = 1; i <= intCount; i++)

        {

            spanID++;

            tObjInstance.maxSpanID = spanID;

            strText = tObjInstance.mulLineText;
            
            strText = strText.replace(/\$row\$/g, spanID);

            //			strText = replace(strText,"$PageName$",t_StrPageName);

            //			strText = replace(strText,"$SpanId$",spanID);

            strText = strText.replace(/\$PageName\$/g, t_StrPageName);

            strText = strText.replace(/\$SpanId\$/g, spanID);

            //strText = replace(strText,"$i$",tObjInstance.mulLineCount+1);

            strText = "<span id='span" + t_StrPageName + spanID + "'>" + strText + "</span>";

            strOldText = strOldText + strText;

        }

        if (!isInit)

        {

            //����ǳ�ʼ���������Ѿ�ָ��

            tObjInstance.mulLineCount = tObjInstance.mulLineCount + intCount;

        }

        //���ر仯����ı�

        document.all("span" + t_StrPageName + "Field").innerHTML = strOldText;

        _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance);	//���ú�����ΪstrFunctionName�ĺ���

        //		fm.all('tt').value=strOldText;

        //ע��˳�򣬱������ڼ��ر仯����ı���ָ������

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_AddOne�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *���������һ��(�ⲿ/�ڲ�����)

 ************************************************

 */

function _AddOneArr(strPageName, intNumber, cObjInstance, cData)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    var i,j;

    var strText;	//ÿ������

    var strFunctionName = "";	//��ִ����addone����õĺ�����

    var spanID = -1;	//spanID���

    var intCount;	//��ӵ��и���

    var tObjInstance;	//����ָ��

    var isInit;	//�ж��Ƿ����ڳ�ʼ��������


    if (cObjInstance == null)

    {

        tObjInstance = this;

        isInit = false;

    }

    else

    {

        tObjInstance = cObjInstance;

        isInit = true;

    }


    tObjInstance.errorString = "";


    (intNumber == null) ? intCount = 1 : intCount = intNumber;	//�õ��еĸ���



    //�Ա�����ֵ

    strText = tObjInstance.mulLineText;

    spanID = tObjInstance.maxSpanID;

    try

    {

        //�õ�ԭ��������

        var strOldText = document.all("span" + t_StrPageName + "Field").innerHTML;


        //���intCount��

        for (i = 1; i <= intCount; i++)

        {

            spanID++;

            tObjInstance.maxSpanID = spanID;

            strText = tObjInstance.mulLineText;

			strText = strText.replace(/\$row\$/g, spanID);
			
            //			strText = replace(strText,"$PageName$",t_StrPageName);

            //			strText = replace(strText,"$SpanId$",spanID);

            strText = strText.replace(/\$PageName\$/g, t_StrPageName);

            strText = strText.replace(/\$SpanId\$/g, spanID);

            for (j = 0; j < cData[i - 1].length; j++)

            {

                //				if(cData[i-1][j]=="")

                //				{

                //					cData[i-1][j]=" ";

                //				}

                k = j + 1;

                strText = strText.replace(t_StrPageName + k + " value=''", t_StrPageName + k + " value='" + cData[i - 1][j] + "'");

            }

            //strText = replace(strText,"$i$",tObjInstance.mulLineCount+1);

            strText = "<span id='span" + t_StrPageName + spanID + "'>" + strText + "</span>";

            strOldText = strOldText + strText;

        }

        if (!isInit)

        {

            //����ǳ�ʼ���������Ѿ�ָ��

            tObjInstance.mulLineCount = tObjInstance.mulLineCount + intCount;

        }

        //���ر仯����ı�

        document.all("span" + t_StrPageName + "Field").innerHTML = strOldText;
        //alert(strOldText);

        _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance);	//���ú�����ΪstrFunctionName�ĺ���

        //		fm.all('tt').value=strOldText;

        //ע��˳�򣬱������ڼ��ر仯����ı���ָ������

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_AddOne�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *������ɾ��һ��(�ⲿ/�ڲ�����)

 ************************************************

 */

function _DeleteOne(strPageName, spanID, cObjInstance)

{

    var tStr;

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

        var spanName = spanID;

        spanID = eval(tObjInstance.formName + ".all('" + spanName + "')");


    }

    tObjInstance.errorString = "";

    var spanObj = eval(tObjInstance.formName + ".all('span" + t_StrPageName + "')");

    try

    {
        spanID.innerHTML = "";

						//����¼CheckBoxѡ�е�ele.getAttribute("PageNoCheckBox")Ҳ����Ӧ��ɾ��
				var ele = document.getElementById("span"+this.instanceName);
				var oldPageNo = "";
				if(ele!=null&&ele!=undefined)
				{
				oldPageNo = ele.getAttribute("PageNoCheckBox");			
				}
				var newPageNo = "";		
				var sid = spanID.id;		
				var thisLineNo = sid.substring(t_StrPageName.length+4,sid.length);	
				if(oldPageNo!=null&&oldPageNo!=''&&oldPageNo!=undefined)
				{
					var oldPageNoArrTemp = oldPageNo.split(',');
					for(var oi=0;oi<oldPageNoArrTemp.length;oi++)
					{
						if(oldPageNoArrTemp[oi]!=','&&oldPageNoArrTemp[oi]!=null&&oldPageNoArrTemp[oi]!='')
						{
							if(oldPageNoArrTemp[oi]<thisLineNo)
							{
								newPageNo+=","+oldPageNoArrTemp[oi]+",";
							}
							else if(oldPageNoArrTemp[oi]>thisLineNo)
							{
								newPageNo+=","+(oldPageNoArrTemp[oi]-1)+",";	
							}
						}
					}
				}
				
//over

        tObjInstance.errorString = "";

        tObjInstance.mulLineCount = tObjInstance.mulLineCount - 1;

        tStr = "<SPAN id=" + spanID.id + "></SPAN>";

        //		spanObj.innerHTML=replace(spanObj.innerHTML,tStr,"");

        spanObj.innerHTML = spanObj.innerHTML.replace(tStr, "");

        _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance);
        
				if(ele!=null&&ele!=undefined)
				{
				ele.setAttribute("PageNoCheckBox",newPageNo);		
				}
        		


    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_DeleteOne�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *��������Enter������һ��

 ************************************************

 */

function _KeyUp(strPageName)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    if (( window.event.keyCode == 40 ) || ( window.event.keyCode == 13 ))

    {
    }

}


/************************************************

 *�������ƶ����㵽������

 ************************************************

 */

function _MoveFocus(Col, cObjInstance)

{

    var cCol;

    var tObjInstance;	//����ָ��

    if (cObjInstance == null || cObjInstance == '')

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    cRow = tObjInstance.mulLineCount - 1;	//�кŴ�0��ʼ

    if (Col == "" || Col == null)

    {

        cCol = 1;

    }

    else

    {

        cCol = Col;

    }

    try

    {

        tObjInstance.setFocus(cRow, cCol, tObjInstance);

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_MoveFocus�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *�������õ�������Ϣ

 ************************************************

 */

function _GetErrStr()

{

    return this.errorString;

}


/************************************************

 *�������޸�������Ϣ(�ڲ�����)

 ************************************************

 */

function _ModifyCount(iFormName, iStrPageName, iCount, cObjInstance)

{

    var t_StrPageName = iStrPageName || this.instanceName;	//ʵ������

    //ÿ�γ�ʼ��������ϵ�ʱ����Ҫ��PageNo������г�ʼ�����Ǻǣ������ȽϺõ�˵

    //var ele = document.getElementById("span" + t_StrPageName);

    //ele.setAttribute("PageNo", "");

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    var i;

    var len = iCount;

    var No;

    try

    {

        //ע�⣬�����Ӧ_SetRowColData��������Ϊ

        if (iCount == 1)

        {

            No = tObjInstance.recordNo + 1;

            try

            {

                eval(iFormName + ".all('" + t_StrPageName + "No').value=" + No);

            }

            catch(ex)

            {

                eval(iFormName + ".all('" + t_StrPageName + "No')[0].value=" + No);

            }

            //���������������������쳣���μ�_SetRowColData��������Ϊ��

        }

        else

        {

            if (!isNaN(len))

            {

                for (i = 1; i <= len; i++)

                {

                    No = tObjInstance.recordNo + i;

                    eval(iFormName + ".all('" + t_StrPageName + "No')[i-1].value=" + No);

                    //����������±����ã�����������iCount>1�����Կ������飬=1��ʱ�򣬲������±꣬������ͨ����

                }

            }

        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_ModifyCount�����з����쳣:" + ex, cObjInstance);

    }

}


/************************************************

 *�������ж��Ƿ���ֻ������

 *���룺�������Ϊ0����������"readonly",���򷵻�""

 ************************************************

 */

function isReadOnly(strReadOnly)

{

    var tempText;

    if (strReadOnly == "0")

    {

        tempText = "readonly";

    }

    else

    {

        tempText = "";

    }

    return tempText;

}


/************************************************

 *�������ж���ʾ����

 *���룺�������Ϊ0����������"readonly",���򷵻�""

 ************************************************

 */

function isReadOnlyClass(strReadOnly)
{

    var tempText;

    if (strReadOnly == "0")
    {
        tempText = "mulreadonly";
    }
    else if (strReadOnly == "2")
    {
        tempText = "code8";
    }
	else if (strReadOnly=="6")
	{
	    tempText = "mulDatePicker";
	}
	else if (strReadOnly=="7")
	{
	    tempText = "multiCurrency";
	}
	else if (strReadOnly=="8")
	{
	    tempText = "multiDatePicker";
	}
    else
    {
        tempText = "mulcommon";
    }

    return tempText;

}


/************************************************

 *��������ʾ��������Ĵ�����Ϣ

 *���룺	strError ��Ҫ��ʾ�Ĵ�����Ϣ

 *			cObj	ʵ��ָ��

 *�����	û��

 ************************************************

 */

function _DisplayError(strError, cObj)

{

    cObj.errorString = strError;

    alert(strError);

}


/************************************************

 *�õ�ָ�����е�����(�ⲿ/�ڲ�����)

 *���룺	cRow:  ��

 *			cCol:  ��

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	ָ���У��е�ֵ

 ************************************************

 */

function _GetRowColData(cRow, cCol, cObjInstance)

{

    var tStr,tReturn;

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)

    {

        alert("��MulLine.js-->getRowColData() ��ָ���˴�����У�" + cRow);

        tReturn = "";

        return trim(tReturn);

    }


    if (cCol < 0 || cCol >= tObjInstance.colCount)

    {

        alert("��MulLine.js-->getRowColData() ��ָ���˴�����У�" + cCol);

        tReturn = "";

        return trim(tReturn);

    }


    try

    {

        //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣

        if (tObjInstance.mulLineCount == 1)

        {

            _ResumeState();

            try

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";

                tReturn = eval(tStr);

            }

            catch(ex)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";

                tReturn = eval(tStr);

            }

            //����õ���ֵΪnull����undefined��˵��������Ϊ1ʱ��������Ŀ��ܣ�

            //����ǴӶ���ɾ����һ�У���ô���ܻ������Ϊ�������һ��Ԫ�أ����Ի�Ҫ���±�

            //�������⣬������javaScript�л����������أ���ˣ����Կ����Ը��������������

            try

            {

                //��undefined���������������Ϊ�е�JS��֧��undefined������Ҫ��������

                if (tReturn == undefined)

                {

                    try

                    {

                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";

                        tReturn = eval(tStr);

                    }

                    catch(ex)

                    {

                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";

                        tReturn = eval(tStr);

                    }

                }

            }

            catch(ex)

            {
            }

            if (tReturn == null)

            {

                try

                {

                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";

                    tReturn = eval(tStr);

                }

                catch(ex)

                {

                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";

                    tReturn = eval(tStr);

                }

            }

            //��ʹͨ�������ת�������Ǵ���©��������ֵ��Ȼ������null����undefined

            //��˶Դ���ȥ��ֵӦ�����ж��Ƿ�null����undefined

        }

    }

    catch(ex)

    {
    }


    try

    {

        if (tObjInstance.mulLineCount > 1)

        {

            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].value";

            tReturn = eval(tStr);

        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_GetRowColData�����з����쳣:" + ex, tObjInstance);

    }


    //ͨ��ת������Ȼ��������ֵ����javascript������ص��в������Ϊ��ȫ�Ա��������֤

    try

    {

        //��undefined���������������Ϊ�е�JS��֧��undefined������Ҫ��������

        if (tReturn == undefined)

        {

            tReturn = "";

        }

    }

    catch(ex)

    {
    }


    try

    {

        if (tReturn == null)

        {

            tReturn = "";

        }

    }

    catch(ex)

    {

        tReturn = "";

    }


    return trim(tReturn);

}


/************************************************

 *�õ�ָ���е�����(�ⲿ/�ڲ�����)

 *���룺	cRow:  ��

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	���ظ��е�����

 ************************************************

 */

function _GetRowData(cRow, cObjInstance)

{

    var tStr,tReturn,n,cCol;

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)

    {

        alert("��MulLine.js-->getRowColData() ��ָ���˴�����У�" + cRow);

        tReturn = "";

        return trim(tReturn);

    }

    var iArray = new Array();//���ص�����

    for (n = 1; n < tObjInstance.colCount; n++)

    {

        cCol = n;

        try

        {

            //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣

            if (tObjInstance.mulLineCount == 1)

            {

                _ResumeState();

                try

                {

                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";

                    tReturn = eval(tStr);

                }

                catch(ex)

                {

                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";

                    tReturn = eval(tStr);

                }

                //����õ���ֵΪnull����undefined��˵��������Ϊ1ʱ��������Ŀ��ܣ�

                //����ǴӶ���ɾ����һ�У���ô���ܻ������Ϊ�������һ��Ԫ�أ����Ի�Ҫ���±�

                //�������⣬������javaScript�л����������أ���ˣ����Կ����Ը��������������

                try

                {

                    //��undefined���������������Ϊ�е�JS��֧��undefined������Ҫ��������

                    if (tReturn == undefined)

                    {

                        try

                        {

                            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";

                            tReturn = eval(tStr);

                        }

                        catch(ex)

                        {

                            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";

                            tReturn = eval(tStr);

                        }

                    }

                }

                catch(ex)

                {
                }

                if (tReturn == null)

                {

                    try

                    {

                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";

                        tReturn = eval(tStr);

                    }

                    catch(ex)

                    {

                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";

                        tReturn = eval(tStr);

                    }

                }

                //��ʹͨ�������ת�������Ǵ���©��������ֵ��Ȼ������null����undefined

                //��˶Դ���ȥ��ֵӦ�����ж��Ƿ�null����undefined

            }

        }

        catch(ex)

        {
        }

        try
        {

            if (tObjInstance.mulLineCount > 1)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].value";

                tReturn = eval(tStr);

            }

        }

        catch(ex)

        {

            _DisplayError("��MulLine.js-->_GetRowColData�����з����쳣:" + ex, tObjInstance);

            return;

        }

        //ͨ��ת������Ȼ��������ֵ����javascript������ص��в������Ϊ��ȫ�Ա��������֤

        try

        {

            //��undefined���������������Ϊ�е�JS��֧��undefined������Ҫ��������

            if (tReturn == undefined)

            {

                tReturn = "";

            }

        }

        catch(ex)

        {
        }


        try
        {

            if (tReturn == null)

            {

                tReturn = "";

            }

        }

        catch(ex)

        {

            tReturn = "";

        }

        iArray[n - 1] = trim(tReturn);

    }

    return iArray;

}


/************************************************

 *����ָ�����е�����(�ⲿ/�ڲ�����)

 *���룺	cRow:  ��

 *			cCol:  ��

 *			cData: ����

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	û��

 ************************************************

 */

function _SetRowColData(cRow, cCol, cData, cObjInstance)

{

    var tStr;

    var tObj;

    var tReturn = false;

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    if (cData == null)

    {

        cData = "";

    }

    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)

    {

        alert("��MulLine.js-->setRowColData() ʱָ���˴������:" + cRow);

        return tReturn;

    }

    if (cCol < 0 || cCol >= tObjInstance.colCount)

    {

        alert("��MulLine.js-->setRowColData() ʱָ���˴������:" + cCol);

        return tReturn;

    }

    try

    {

        //		var newData=replace(cData,"\r\n","");

        //�����ǳ�ʼ��������

        var newData = cData.replace("\r\n", "");
        
        //notes added by zhouwh@sinosoft.com.cn �����'�ŵ����⣻
				newData = cData.replace("'", "\\'");
        //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣

        if (tObjInstance.mulLineCount == 1)

        {

            _ResumeState();

            try

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value='" + newData + "'";

            }

            catch(ex)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value='" + newData + "'";

            }

        }

        else

        {

            if (tObjInstance.mulLineCount > 1)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].value='" + newData + "'";

            }

        }

        eval(tStr);

        tReturn = true;

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_SetRowColData�����з����쳣:" + ex, tObjInstance);

    }

    return tReturn;

}


function _setRowColCurrency(cRow, cCol, cData, cObjInstance)

{

    var tStr;

    var tObj;

    var tReturn = false;

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    if (cData == null)

    {

        cData = "";

    }

    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)

    {

        alert("��MulLine.js-->setRowColData() ʱָ���˴������:" + cRow);

        return tReturn;

    }

    if (cCol < 0 || cCol >= tObjInstance.colCount)

    {

        alert("��MulLine.js-->setRowColData() ʱָ���˴������:" + cCol);

        return tReturn;

    }

    try

    {

        //		var newData=replace(cData,"\r\n","");

        //�����ǳ�ʼ��������

        var newData = cData.replace("\r\n", "");

        //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣

        if (tObjInstance.mulLineCount == 1)

        {

            _ResumeState();

            try

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').moneytype='" + newData + "'";

            }

            catch(ex)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].moneytype='" + newData + "'";

            }

        }

        else

        {

            if (tObjInstance.mulLineCount > 1)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].moneytype='" + newData + "'";

            }

        }

        eval(tStr);

        tReturn = true;

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_SetRowColData�����з����쳣:" + ex, tObjInstance);

    }

    return tReturn;

}








//������Ӧ�е�class
function _setRowClass(cRow, cData, cObjInstance)
{  
    var tObjInstance; //����ָ��
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }

    for(var j=0; j<tObjInstance.colCount; j++){			//ѭ�����е���
	tObjInstance.setRowColClass(cRow,j,cData);			//�޸ı���ɫ
    }
}

//tongmeng 2008-10-27 add
//������Ӧ���е�class
function _SetRowColClass(cRow, cCol, cData, cObjInstance)
{
    var tStr;
    var tObj;
    var tReturn = false;
    var tObjInstance; //����ָ��
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    if (cData == null)
    {
        cData = "";
    }
    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)
    {
        alert("�� MulLine.js --> _SetRowColClass() ʱָ���˴������:" + cRow);
        return tReturn;
    }
    if (cCol < 0 || cCol >= tObjInstance.colCount)
    {
        alert("�� MulLine.js --> _SetRowColClass() ʱָ���˴������:" + cCol);
        return tReturn;
    }
    try
    {
        var newData = replace(cData, "\r\n", "");
        // 2006-02-16 Kevin
        // calling replace(cData, "\\", "\\\\") results in IE hangs
        // so we have to replace "\\" with "\\\\" by following method
        if (chkzh(newData) == false)
        {
            cData = newData;
            newData = "";
            var vIndex = 0;
            var vSubStr = "";
            for (vIndex = 0; vIndex < cData.length; vIndex++)
            {
                vSubStr = cData.substring(vIndex, vIndex + 1);
                if (vSubStr == "\\")
                {
                    newData += "\\\\";
                }
                else
                {
                    newData += vSubStr;
                }
            }
        }
        //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣
        if (tObjInstance.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').className=\"" + newData + "\"";
            }
            catch (ex)
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].className=\"" + newData + "\"";
            }
        }
        else
        if (tObjInstance.mulLineCount > 1)
        {
            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].className=\"" + newData + "\"";
        }
        eval(tStr);

        tReturn = true;

    }

    catch (ex)

    {
        //_DisplayError("�� MulLine.js --> _SetRowColData �����з����쳣��" + ex, tObjInstance);//edit by yaory


    }

    return tReturn;
}

//tongmeng 2008-10-27 add
//������Ӧ���е�Title
function _SetRowColTitle(cRow, cCol, cData, cObjInstance)
{
    var tStr;
    var tObj;
    var tReturn = false;
    var tObjInstance; //����ָ��
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    if (cData == null)
    {
        cData = "";
    }
    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)
    {
        alert("�� MulLine.js --> _SetRowColTitle() ʱָ���˴������:" + cRow);
        return tReturn;
    }
    if (cCol < 0 || cCol >= tObjInstance.colCount)
    {
        alert("�� MulLine.js --> _SetRowColTitle() ʱָ���˴������:" + cCol);
        return tReturn;
    }
    try
    {
        var newData = replace(cData, "\r\n", "");
        // 2006-02-16 Kevin
        // calling replace(cData, "\\", "\\\\") results in IE hangs
        // so we have to replace "\\" with "\\\\" by following method
        if (chkzh(newData) == false)
        {
            cData = newData;
            newData = "";
            var vIndex = 0;
            var vSubStr = "";
            for (vIndex = 0; vIndex < cData.length; vIndex++)
            {
                vSubStr = cData.substring(vIndex, vIndex + 1);
                if (vSubStr == "\\")
                {
                    newData += "\\\\";
                }
                else
                {
                    newData += vSubStr;
                }
            }
        }
        //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣
        if (tObjInstance.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').setAttribute('title',\"" + newData + "\")";
                tStr1 = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').setAttribute('onmouseover',\"\")";

            }
            catch (ex)
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].setAttribute('title',\"" + newData + "\")";
           			tStr1 = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].setAttribute('onmouseover',\"\")";
            }
        }
        else
        if (tObjInstance.mulLineCount > 1)
        {
           tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].setAttribute('title',\"" + newData + "\")";
           tStr1 = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].setAttribute('onmouseover',\"\")";

        }
        //prompt('',tStr);
        eval(tStr);
        eval(tStr1);
        tReturn = true;
    }
    catch (ex)
    {
        //_DisplayError("�� MulLine.js --> _SetRowColData �����з����쳣��" + ex, tObjInstance);//edit by yaory
    }
    return tReturn;
}

/************************************************

 *���Muline������(���ⲿ/�ڲ�����)

 *���룺	strPageName:  ҳ����span�����������ַ���

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������ֺ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	û��

 ************************************************

 */

function _ClearData(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    var strNewText = "";

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    try

    {

        document.all("span" + t_StrPageName + "Field").innerHTML = strNewText;

        tObjInstance.mulLineCount = 0;

        tObjInstance.maxSpanID = -1;

        try

        {

            if (tObjInstance.checkFlag == 1 && tObjInstance.canChk == 1)

            {

                tObjInstance.checkFlag = 0;

                eval(tObjInstance.formName + ".all('checkAll" + tObjInstance.instanceName + "').checked=false");

            }

        }

        catch(ex)

        {
        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_clearData�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *��Muline�Ŀհ����崦(�����ⲿ/�ڲ�����)

 *���룺	strPageName:ҳ����Muline�Ķ�����������Ϊ��

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������ֺ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	û��

 ************************************************

 */

function _DelBlankLine(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������


    var tObjInstance;

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }


    tObjInstance.errorString = "";


    var rowCount = tObjInstance.mulLineCount;	//����

    var colCount = tObjInstance.colCount;	//����


    var i,j;

    var blankFlag = true;	//���б�־

    var lineSpanID;	//�е�spanID

    var data = "";

    try

    {

        //ѭ����ѯÿһ���Ƿ�Ϊ����,�����е�ÿһ�ж�Ϊ�գ�����0�У�����У�

        for (i = 0; i < rowCount; i++)//���п�ʼѭ��,0�п�ʼ

        {

            for (j = 1; j < colCount; j++)

            {

                //���п�ʼѭ����1�п�ʼ

                data = _GetRowColData(i, j, tObjInstance);

                if (data != null && data != "")

                {

                    //�����Ϊ�գ����б�־��Ϊfalse

                    blankFlag = false;

                    break;

                }

            }

            if (blankFlag)

            {

                lineSpanID = _FindSpanID(i, tObjInstance);  //�õ����е�spanID

                _DeleteOne(t_StrPageName, lineSpanID, tObjInstance); //ɾ����һ��

                //ɾ��һ�У�ѭ����һ

                rowCount = rowCount - 1;

                //����һ�м��

                i = i - 1;

            }

            blankFlag = true;

        }

        try

        {

            if (tObjInstance.checkFlag == 1 && tObjInstance.canChk == 1)

            {

                tObjInstance.checkFlag = 0;

                eval(tObjInstance.formName + ".all('checkAll" + tObjInstance.instanceName + "').checked=false");

            }

        }

        catch(ex)

        {
        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_DelBlankLine�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *��Muline��ѡ�������(�����ⲿ/�ڲ�����)

 *���룺	strPageName:ҳ����Muline�Ķ�����������Ϊ��

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������ֺ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	û��

 ************************************************

 */

function _DelCheckTrueLine(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������
	var ele = document.getElementById("span"+this.instanceName);
	ele.setAttribute("PageNoCheckBox",null);

    var tObjInstance;

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    var rowCount = tObjInstance.mulLineCount;	//����

    var i;

    var checkTrueFlag = true;	//ѡ���б�־

    var lineSpanID;	//�е�spanID


    if (tObjInstance.canChk == 0)

    {

        alert("no checkBox!");

        return;

    }

    try

    {

        //ѭ����ѯÿһ���Ƿ�Ϊ����,�����е�ÿһ�ж�Ϊ�գ�����0�У�����У�

        for (i = 0; i < rowCount; i++)

        {

            //���п�ʼѭ��,0�п�ʼ

            checkTrueFlag = _GetChkNo(i, tObjInstance);

            if (checkTrueFlag)

            {

                lineSpanID = _FindSpanID(i, tObjInstance);	//�õ����е�spanID

                _DeleteOne(t_StrPageName, lineSpanID, tObjInstance);	//ɾ����һ��

                //ɾ��һ�У�ѭ����һ

                rowCount = rowCount - 1;

                //����һ�м��

                i = i - 1;

            }

        }

        try

        {

            if (tObjInstance.checkFlag == 1)

            {

                tObjInstance.checkFlag = 0;

                eval(tObjInstance.formName + ".all('checkAll" + tObjInstance.instanceName + "').checked=false");

            }

        }

        catch(ex)

        {
        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_DelBlankLine�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *��Muline��ѡ�е�radiobox�����(�����ⲿ/�ڲ�����)

 *���룺	strPageName:ҳ����Muline�Ķ�����������Ϊ��

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������ֺ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	û��

 ************************************************

 */

function _DelRadioTrueLine(strPageName, cObjInstance)
{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������
	var ele = document.getElementById("span"+this.instanceName);
	ele.setAttribute("PageNoRadio",null);
    var tObjInstance;

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }


    tObjInstance.errorString = "";

    var rowCount = tObjInstance.mulLineCount;	//����

    var selno = 0;	//ѡ�е�����

    var lineSpanID;	//�е�spanID


    if (tObjInstance.canSel == 0)

    {

        alert("no radioBox!");

        return;

    }


    try

    {

        selno = _GetSelNo(tObjInstance);

        if (selno == 0)

        {

            selno = 1;

        }

        lineSpanID = _FindSpanID(selno - 1, tObjInstance);	//�õ����е�spanID

        _DeleteOne(t_StrPageName, lineSpanID, tObjInstance);	//ɾ����һ��
 			
    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_DelRadioTrueLine�����з����쳣:" + ex, tObjInstance);

    }

}


/************************************************

 *����ָ���е�SpanID(���ⲿ/�ڲ�����)

 *���룺	cRow:  ָ��������

 *			cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��

 *			��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;

 *			������ֺ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���

 *�����	tReturn��ָ���е�spanֵ

 *��Ҫ˵��������ģ����ɾ��һ�еı�־"-"���棬������ص�INPUT����nameΪ

 *			this.instanceName+"SpanID'����valueΪ��Ӧ���е�spanֵ

 *			�õ����е�spanֵ�󣬴���_DeleteOne()��������������ɾ��

 *			Ŀ����Ϊ�˶�̬ɾ����������������У�ͨ��ѭ��ʵ��

 ************************************************

 */

function _FindSpanID(cRow, cObjInstance)

{

    var tStr;

    var tReturn = "";

    var tObjInstance;

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }


    tObjInstance.errorString = "";


    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)

    {

        alert("��MulLine.js-->findSpanID() ʱָ���˴������:" + cRow);

        return tReturn;

    }

    try

    {

        //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣

        if (tObjInstance.mulLineCount == 1)

        {

            _ResumeState();

            try

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID').value";

                tReturn = eval(tStr);

            }

            catch(ex)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[0].value";

                tReturn = eval(tStr);

            }

            //����õ���ֵΪnull��˵��������Ϊ1ʱ��������Ŀ��ܣ�

            //����ǴӶ���ɾ����һ�У���ô���ܻ������Ϊ����������������һ��Ԫ�أ����Ի�Ҫ���±�

            //�������⣬������javaScript�л����������أ���ˣ�������Կ����Ը��������������

            try

            {

                //��undefined���������������Ϊ�е�JS��֧��undefined������Ҫ��������

                if (tReturn == undefined)

                {

                    try

                    {

                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[0].value";

                        tReturn = eval(tStr);

                    }

                    catch(ex)

                    {

                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID').value";

                        tReturn = eval(tStr);

                    }

                }

            }

            catch(ex)

            {
            }


            if (tReturn == null)

            {

                try

                {

                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[0].value";

                    tReturn = eval(tStr);

                }

                catch(ex)

                {

                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID').value";

                    tReturn = eval(tStr);

                }

            }

        }

        else

        {

            if (tObjInstance.mulLineCount > 1)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[" + cRow + "].value";

                tReturn = eval(tStr);

            }

        }

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_FindSpanID�����з����쳣:" + ex, tObjInstance);

    }

    return tReturn;

}


/************************************************

 *�������������ֵ�Ƿ���Ϲ淶

 *���룺	����Ϊ�գ��������

 *�����	û��

 interpreting right by yuanaq

 ************************************************/

function _CheckValue2(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    var tObj = cObjInstance || this;

    _DelBlankLine(t_StrPageName, tObj);	//�������


    var tRule = "";

    var strInfo = "";

    var rowNo = 0;

    var tReturn;

    if (tObj.mulLineCount == 0)

    {

        return true;

    }

    else if (tObj.mulLineCount == 1)

    {

        for (var i = 1; i < tObj.colCount; i++)

        {

            //�ӵ�1�п�ʼ����0�������У�������

            tRule = eval(tObj.formName + ".all('" + t_StrPageName + "verify" + i + "').value");

            if (tRule == null || tRule == "")

            {

                continue;

            }

            else

            {

                try

                {

                    strInfo = "��һ�е�" + tRule;

                    var dd = tObj.formName + "." + t_StrPageName + i;

                    if (!verifyElementWrap2(strInfo, _GetRowColData(n, i, tObj), dd))

                        return false;//������󣬷���

                }

                catch(ex)

                {

                    alert("��ȷ��verifyInput.js �ļ������������ݿ���������");

                    return false;

                }

            }

        }

    }

    else

    {

        for (var i = 1; i < tObj.colCount; i++)

        {

            //�ӵ�1�п�ʼ����0�������У�������

            try

            {

                //ע��:��ʼ��ʱ�������������У���textTitle������verify=''

                tRule = eval(tObj.formName + ".all('" + t_StrPageName + "verify" + i + "').value");

                if (tRule == null || tRule == "")

                {

                    continue;

                }

                else

                {

                    for (var n = 0; n < tObj.mulLineCount; n++)

                    {

                        // �ⲿ��������쿴verifyInput.js parm1:λ��|������� parm2: Ҫ�����ֵ(����N��i�е�ֵ)

                        try

                        {

                            rowNo = n + 1;

                            strInfo = "��" + rowNo + "�е�" + tRule;	//��ʾ��Ϣ��ȷ���ڼ���

                            var dd = tObj.formName + "." + t_StrPageName + i + "[" + n + "]";

                            if (!verifyElementWrap2(strInfo, _GetRowColData(n, i, tObj), dd))

                            {

                                return false;	//������󣬷���

                            }

                        }

                        catch(ex)

                        {

                            alert("��ȷ��verifyInput.js �ļ������������ݿ���������");

                            return false;

                        }

                    }

                }

            }

            catch(ex)

            {

                alert("_CheckValue��������");

                return false;

            }

        }

    }

    return true;

}


/************************************************

 *�������������ֵ�Ƿ���Ϲ淶

 *���룺	����Ϊ�գ��������

 *�����	û��

 ************************************************/

function _CheckValue(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//ʵ������

    var tObj = cObjInstance || this;

    _DelBlankLine(t_StrPageName, tObj);	//�������


    var tRule = "";

    var strInfo = "";

    var rowNo = 0;

    var tReturn;

    if (tObj.mulLineCount == 0)

    {

        return true;

    }

    for (var i = 1; i < tObj.colCount; i++) //�ӵ�1�п�ʼ����0�������У�������

    {

        try

        {

            //ע��:��ʼ��ʱ�������������У���textTitle������verify=''

            tRule = eval(tObj.formName + ".all('" + t_StrPageName + "verify" + i + "').value");

            if (tRule == null || tRule == "")

            {

                continue; //����У��

            }

            else

            {

                for (var n = 0; n < tObj.mulLineCount; n++)

                {

                    // �ⲿ��������쿴verifyInput.js parm1:λ��|������� parm2: Ҫ�����ֵ(����N��i�е�ֵ)

                    try

                    {

                        rowNo = n + 1;

                        strInfo = "��" + rowNo + "�е�" + tRule;   //��ʾ��Ϣ��ȷ���ڼ���

                        if (!verifyElement(strInfo, _GetRowColData(n, i, tObj)))

                        {

                            return false;//������󣬷���

                        }

                    }

                    catch(ex)

                    {

                        alert("��ȷ��verifyInput.js �ļ������������ݿ���������");

                        return false;

                    }

                }

            }

        }

        catch(ex)

        {

            alert("_CheckValue��������");

            return false;

        }

    }

    return true;

}


/************************************************

 *��������(�ڲ�����):�ú�������ȥ����������չ���������ӵĹ���

 *��MulLine�Ӷ��м�����һ��ʱ���ٵ����ڲ�����

 *�ǻ����ġ����ܵ�ԭ���ǣ�������������ʽ��������һ��������ʽ

 *���ɶ��б�ɵ��к��ֽ��ŵ����ڲ���������ʣ�µĵ��л����������

 *��ʽ����[0]�����������ڲ��ж�ʱ��������=1����ֱ���ñ�����ʽ����

 *����ִ��󡣸ú�������xxx.mulLineCount==1�󡣼����øö�����ڲ�

 *�������Ա��ٵ�����غ���ʱ���õ�����ʽ��Ϊ������ʽ

 *���룺	����Ϊ�գ��������

 *�����	û��

 ************************************************/

function _ResumeState(cObjInstance)

{

    var tObjInstance;

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";

    tObjInstance.state = 0;

}


/**
 * ���������� MulLine ��ĳ��ĳ���Ƿ�õ�����
 * ���룺����(�� 0 ��ʼ)
 * ���룺����(�� 1 ��ʼ)
 * �����true or false
 * ��ע��XinYQ added on 2006-11-14
 */
function _GetFocus(nRowNumber, nColNumber, tObjInstance)
{
    try
    {
        var oInstance = this; //var oInstance = tObjInstance || this;
        if (oInstance.lastFocusRowNo != nRowNumber || oInstance.lastFocusColNo != nColNumber)
        {
            return false;
        }
        return true;
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _GetFocus �����з����쳣��" + ex, tObjInstance);
    }
}


/************************************************

 *����MulLine��ĳ��ĳ�еõ�����

 *���룺	row �� ����Ϊ��(������Ϊ���һ�е�����)

 col �� ����Ϊ�գ�ȱʡ��Ϊ1����������ź�ĵ�һ�У�

 cObjInstance (MulLine�������Ϊ�� )

 *�����	true or false

 ************************************************/

function _SetFocus(Row, Col, cObjInstance)

{

    var tStr;

    var tObj;

    var tReturn = false;

    var cRow;

    var cCol;

    var tObjInstance;	//����ָ��

    if (cObjInstance == null)

    {

        tObjInstance = this;

    }

    else

    {

        tObjInstance = cObjInstance;

    }

    tObjInstance.errorString = "";


    cRow = Row;

    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)

    {

        return tReturn;

    }

    if (Col == "" || Col == null)

    {

        cCol = 1;

    }

    else

    {

        cCol = Col;

    }


    if (cCol < 0 || cCol >= tObjInstance.colCount)

    {

        return tReturn;

    }


    try

    {

        //ע�⣺�������������ж������������ڳ�ʼ��Ϊ0��ʱ��������쳣

        if (tObjInstance.mulLineCount == 1)

        {

            _ResumeState();

            try

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').focus()";

            }

            catch(ex)

            {

                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].focus()";

            }

        }

        else if (tObjInstance.mulLineCount > 1)

        {

            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].focus()";

        }

        eval(tStr);

        tReturn = true;

    }

    catch(ex)

    {

        _DisplayError("��MulLine.js-->_SetFocus�����з����쳣:" + ex, tObjInstance);

    }

    return tReturn;

}


/************************************************

 *���MulLine��ĳ�����ֳ�����ʾ��Χ������ʾtitle

 *���룺	�ؼ�ʵ��

 *�����	title

 ************************************************/

function _showtitle(obj)
{

    obj.title = obj.innerHTML;

}


function AllowSortFun(obj, i)

{
    var sortturnpage = obj.SortPage;

    if (sortturnpage == null || sortturnpage == "")

    {

        alert("���Ȳ�ѯ��");

        return false;

    }

    sortturnpage.allowsort(i);

}


/************************************************************

 *	������	����ҳ����ʾ������ҳ����ת����Ӧҳ

 *************************************************************/

function _SetPageMark(cTurnPage)

{
    var tStrPageName = this.instanceName;
		var ele = document.getElementById("span" + this.instanceName);
    ele.setAttribute("PageNoRadio","");
		ele.setAttribute("PageNoCheckBox","");

    try

    {
				//alert(this.SortPage);
        if (this.SortPage == null || this.SortPage == "")

        {
            return false;

        }

    }

    catch(ex)

    {

        alert(ex.message);

        return false;

    }

    var tTotalPageNum = Math.ceil(cTurnPage.queryAllRecordCount / cTurnPage.pageLineNum);

    var tPageIndex = cTurnPage.pageIndex + 1;

    var tHTML = document.all("span" + tStrPageName).innerHTML;

    if (tHTML == "" || tHTML == null)

    {

        return;

    }

    tHTML += "<div class=MulLinePageDiv valign=middle>";

    tHTML += "<table class = MulLinePageTable><tr hight=18px valign=middle>";

    if (tPageIndex != '1')
    {
        tHTML += "<td valign=bottom >&nbsp<img src='../common/images/firstPage.gif' style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(1);' name=btnchangepage>&nbsp</td>";

        tHTML += "<td valign=bottom ><img src='../common/images/PreviousPage.gif' style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(" + tPageIndex + "-1);' name=btnchangepage>&nbsp</td>";
    }
    else
    {
        tHTML += "<td valign=bottom >&nbsp<img src='../common/images/firstPage.gif' style='cursor: hand;' onclick='alert(\"�Ѿ�������ҳ��\");' name=btnchangepage>&nbsp</td>";

        tHTML += "<td valign=bottom ><img src='../common/images/PreviousPage.gif' style='cursor: hand;' onclick='alert(\"�Ѿ�������ҳ��\");' name=btnchangepage>&nbsp</td>";
    }


    tHTML += "<td valign=bottom >��&nbsp;" + tPageIndex + "/" + tTotalPageNum + "&nbsp;ҳ&nbsp;</td>";

    if (tPageIndex != tTotalPageNum)
    {
        tHTML += "<td valign=bottom ><img src='../common/images/NextPage.gif' style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(" + tPageIndex + "+1);' name=btnchangepage>&nbsp</td>";

        tHTML += "<td valign=bottom ><img src='../common/images/lastPage.gif' style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(" + tTotalPageNum + ");' name=btnchangepage>&nbsp</td>";
    }
    else
    {
        tHTML += "<td valign=bottom ><img src='../common/images/NextPage.gif' style='cursor: hand;' onclick='alert(\"�Ѿ�����βҳ��\");' name=btnchangepage>&nbsp</td>";

        tHTML += "<td valign=bottom ><img src='../common/images/lastPage.gif' style='cursor: hand;' onclick='alert(\"�Ѿ�����βҳ��\");' name=btnchangepage>&nbsp</td>";
    }


    tHTML += "<td valign=bottom >ת��&nbsp;<input type='common' style='{border: 1px #9999CC solid;height: 18px}' name='GotoPage" + tStrPageName + "' size='3'>&nbsp;ҳ</td>";

    tHTML += "<td valign=center>&nbsp<img src='../common/images/goto.gif' style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(document.all.GotoPage" + tStrPageName + ".value);' name=btnchangepage></td>";

    tHTML += "</tr></table></div>";

    document.all("span" + tStrPageName).innerHTML = tHTML;

}

/**
 * ����������ѡ�е� CheckBox �ܸ���
 * ���룺��
 * ���������
 * ��ע��XinYQ added on 2006-05-11
 */
function _GetChkCount(tObjInstance)
{
    var oInstance = this; //var oInstance = tObjInstance || this;
    var nSelectedCount = 0;
    try
    {
        for (var i = 0; i < oInstance.mulLineCount; i++)
        {
            if (this.getChkNo(i))
            {
                nSelectedCount += 1;
            }
        }
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _GetChkCount �����з����쳣:  " + ex, tObjInstance);
    }
    return nSelectedCount;
}

//==============================================================================

/**
 * ������ʹָ���� radioBox ���ѡ�е�״̬
 * ���룺����(�� 1 ��ʼ)
 * ��������ѡ�У����� true�����򷵻� false
 * ��ע��XinYQ added on 2006-05-15
 */
function _SelOneRow(nRowNumber, tObjInstance)
{
    try
    {
        var oInstance = this; //var oInstance = tObjInstance || this;
        if (oInstance.canSel != 1)
        {
            alert("�� MulLine.js --> _SelOneRow �����з�������" + oInstance.instanceName + " ������ѡ�� ");
            return false;
        }
        if (nRowNumber == null || nRowNumber <= 0 || oInstance.mulLineCount <= 0 || nRowNumber > oInstance.mulLineCount)
        {
            alert("�� MulLine.js --> _SelOneRow ������ָ���˴�����У�" + nRowNumber + " ");
            return false;
        }
        else
        {
            document.getElementsByName(oInstance.instanceName + "Sel")[nRowNumber - 1].click();
        }
        return true;
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _SelOneRow �����з����쳣��" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * ������ʹָ���� checkBox ���ѡ�е�״̬
 * ���룺����(�� 1 ��ʼ)
 * ��������ѡ�У����� true�����򷵻� false
 * ��ע��XinYQ added on 2006-05-15
 */
function _ChkOneRow(nRowNumber, tObjInstance)
{
    try
    {
        var oInstance = this; //var oInstance = tObjInstance || this;
        if (oInstance.canChk != 1)
        {
            alert("�� MulLine.js --> _ChkOneRow �����з�������" + oInstance.instanceName + " ������ѡ�� ");
            return false;
        }
        if (nRowNumber == null || nRowNumber <= 0 || oInstance.mulLineCount <= 0 || nRowNumber > oInstance.mulLineCount)
        {
            alert("�� MulLine.js --> _ChkOneRow ������ָ���˴�����У�" + nRowNumber + " ");
            return false;
        }
        else
        {
            document.getElementsByName(oInstance.instanceName + "Chk")[nRowNumber - 1].click();
        }
        return true;
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _ChkOneRow �����з����쳣��" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * �����������ӿؼ�����һ���õ�����ʱ������ӿؼ����к���
 * ���룺MulLine ����
 * ���룺�ӿؼ�����
 * �������
 * ��ע��XinYQ added on 2006-10-10
 */
function _CalcFocusRowColNo(oInstance, oEventCtrl)
{
    try
    {
        if (oInstance == null || oEventCtrl == null || typeof(oInstance) == "undefined" || typeof(oEventCtrl) == "undefined")
        {
            //alert("�� MulLine.js --> _CalcFocusRowColNo �����з�������ʵ�����Ϳؼ���������Ϊ�գ� ");
            return;
        }
        if (oInstance.mulLineCount <= 0)
        {
            oInstance.lastFocusRowNo = -1;
            oInstance.lastFocusColNo = -1;
            return;
        }
        else
        {
            //���㽹����
            try
            {
                for (var i = 0; i < oInstance.mulLineCount; i++)
                {
                    if (document.getElementsByName(oEventCtrl.name)[i] == oEventCtrl)
                    {
                        oInstance.lastFocusRowNo = i;
                    }
                }
                //alert("lastFocusRowNo = " + oInstance.lastFocusRowNo);
            }
            catch (ex)
            {
                //alert("�� MulLine.js --> _CalcFocusRowColNo �����з������󣺼��㽹�����쳣�� ");
                return;
            }
            //���㽹����
            try
            {
                var sInstanceName = (oInstance.instanceName != null) ? oInstance.instanceName : oInstance.name;
                var sEventCtrlName = oEventCtrl.name;
                if (sInstanceName != null && typeof(sInstanceName) != "undefined")
                {
                    var sLastFocusColNo = sEventCtrlName.substring(sInstanceName.length);
                    if (sLastFocusColNo != null && isInteger(sLastFocusColNo))
                    {
                        oInstance.lastFocusColNo = parseInt(sLastFocusColNo);
                    }
                    else
                    {
                        oInstance.lastFocusColNo = 0;
                    }
                    //alert("lastFocusColNo = " + oInstance.lastFocusColNo);
                }
            }
            catch (ex)
            {
                //alert("�� MulLine.js --> _CalcFocusRowColNo �����з������󣺼��㽹�����쳣�� ");
                return;
            }
        }
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js --> _CalcFocusRowColNo �����з����쳣��", oInstance);
    }
}

//==============================================================================



var dragColStart = 0;//��ʼ����ק��ʼ�������������
var dragColEnd = 0;
/*******************************
 *�������е����У���Ҫ����ÿһ�У�Ȼ�󽻻�ÿһ��Ԫ�ء���Ϊû�б��ж���
 *add by wanglei 2008-08-01
 *******************************/
function ChangeColumn(table, sor, tag)
{
    try
    {
        for (var i = 0; i < table.rows.length; i++)
            table.rows[i].cells[sor].swapNode(table.rows[i].cells[tag]);
        return 1;
    }
    catch(ex)
    {
        return 0;
    }
}
/*******************************
 *�϶���������,��̬�ļ�¼newColOrder��ֵ
 *add by wanglei 2008-08-01
 *******************************/
function ChangeColOrder(sor, tag, obj)
{
    var tObjInstance = obj;
    try
    {
        var temp = tObjInstance.newColOrder[sor - tObjInstance.totalPre];
        tObjInstance.newColOrder[sor - tObjInstance.totalPre] = tObjInstance.newColOrder[tag - tObjInstance.totalPre];
        tObjInstance.newColOrder[tag - tObjInstance.totalPre] = temp;
        return 1;
    }
    catch(ex)
    {
        return 0;
    }
}
var dragdiv;

/*******************************
 *�϶���������,���������ֵ
 *add by wanglei 2008-08-01
 *******************************/
function _MoveSpan(obj, t_StrPageName, obj1)
{
    var tHavenMove=false;
    init();
    function init()
    {
        dragdiv = document.createElement("DIV");
        dragdiv.onselectstart = function()
        {
            return false
        };
        document.body.appendChild(dragdiv);
    }
    var nx = event.x,ny = event.y;
    var tab_data = document.getElementById(t_StrPageName);
    var tab_data1 = document.getElementById(t_StrPageName);
    var tab_alldata = document.getElementsByTagName("TABLE");
    setspan();
    function setspan()//��̬����ʾһ���϶��еı�td������
    {
       
       //alert("����λ�ƣ�=="+document.body.scrollTop );
      // alert("����λ�ƣ�=="+document.body.scrollLeft   );
        var cell;
        cell = obj;
        var t = cell.offsetTop;
        var l = cell.offsetLeft;
        while (cell = cell.offsetParent)
        {
            l += cell.offsetLeft;
            t += cell.offsetTop;
        }
        
        dragdiv.style.border = "1px solid black";
        
        dragdiv.style.left = l  ;
        dragdiv.style.top =t ;
        dragdiv.style.fontSize = obj.currentStyle.fontSize;
        dragdiv.style.textAlign = "center";
        dragdiv.style.display = "none";
        dragdiv.innerHTML = obj.innerHTML;
        dragdiv.style.width = obj.offsetWidth;
        dragdiv.style.height = obj.offsetHeight;
        dragdiv.style.cursor = "hand";
        dragdiv.style.position = "absolute";
        dragdiv.style.backgroundColor = obj.style.backgroundColor;
        dragdiv.style.filter = "alpha(opacity=50)";
       
    }
    document.onmousemove = function()
    {
        //alert("wwwww");
       
        dragdiv.style.left = event.x+document.body.scrollLeft - dragdiv.offsetWidth / 2;
        dragdiv.style.top = event.y +document.body.scrollTop- dragdiv.offsetHeight / 2;
      // add by chenjianguo,liuhuiming ͨ�����ȥ�ж��Ƿ�����ק���ǵ���������������ƶ� ��tHavenMove=true����ִ����ק�����û�ƶ���꣬��ִ��������  tHavenMove=true
       
        if(nx!=event.x||ny!=event.y){
        	 tHavenMove=true;
        	 dragdiv.style.display = "";
        }
    }

    function get_Element(the_ele, the_tag)//���ô˺��������Ա�����ִ��󡣵��϶�Ŀ���Ƶ������ǣ������Դ���
    {
        try
        {
            the_tag = the_tag.toLowerCase();
            if (the_ele.tagName.toLowerCase() == the_tag)
            {
                return   the_ele;
            }
            while (the_ele = the_ele.offsetParent)
            {
                if (the_ele.tagName.toLowerCase() == the_tag)
                    return   the_ele;
            }
        }
        catch(ex)
        {
            //���catch��ʵ���ܲ��񵽴��󣬵���Ӱ��ʹ�ã�ϣ�����˳��ְ�����ط�����һ�£�thank you�� 2008-08-12 add by wanglei

        }
        return(null);
    }
   
    document.onmouseup = function()
    {
        //alert("heere ="+tHavenMove);
        if(tHavenMove){
        	
        var the_start = get_Element(obj, "td");
        if (the_start != null)
        {
            dragColStart = getCellIndex(the_start);
        }
        else
        {
            return(null);
        }
        //dragdiv.style.display = "none";
        var the_end = get_Element(document.elementFromPoint(event.x, event.y), "td");
        var curleftX = parseInt(dragdiv.style.left);
        var curtopY = parseInt(dragdiv.style.top);
        var currightX = parseInt(dragdiv.style.left) + parseInt(dragdiv.style.width);
        var firstelm = document.elementFromPoint(curleftX, curtopY);
        var secondelm = document.elementFromPoint(currightX, curtopY);
        var testfristtable = get_Element(document.elementFromPoint(curleftX, curtopY), "td");
        var testsecondtable = get_Element(document.elementFromPoint(currightX, curtopY), "td");
        if (the_end != null)
        {
            dragColEnd = getCellIndex(the_end);

        }
        else
        {
           dragdiv.parentNode.removeChild(dragdiv); //ɾ�� 
            return(null);
        }
       // if (testfrist == testsecond)
      //  {
            var tCflag = 0;
            //alert("table num =="+tab_alldata.length);
            for (var k = 0; k < tab_alldata.length; k++)
            {
               
                if (tab_alldata[k].id.indexOf(t_StrPageName + "ExChange") == 0)
                {
                    tCflag = ChangeColumn(tab_alldata[k], dragColStart, dragColEnd);
                }
            }
            if (tCflag == 1)
            {
                ChangeColOrder(dragColStart, dragColEnd, obj1);
            }
						dragdiv.parentNode.removeChild(dragdiv); //ɾ�� 
       // }
        //else
       // {
       // }
        document.onmouseup = null;//���������֤document.onmouseup���ᱻ�����ط����� add by wanglei 2008-08-15
       }else{
  			//alert("no move");
  			dragdiv.parentNode.removeChild(dragdiv); //ɾ�� 
  	   // _ShowColChoose(obj, t_StrPageName, t_StrPageName);
  	    
  	}
    	
    }
    function changeSort(table, targettag, firsttag, secondtag)
    {
        if (secondtag == null || firsttag == null)
            return;
        var targetleft = targettag.offsetLeft + targettag.offsetWidth;
        var ftagleft = firsttag.offsetLeft + firsttag.offsetWidth;
        var stagleft = secondtag.offsetLeft + secondtag.offsetWidth;
        var targindex = targettag.cellIndex;
        var findex = firsttag.cellIndex;
        var sindex = secondtag.cellIndex;
        var arrtar = new Array();
        for (var i = 0; i < table.rows.length; i++)
            arrtar[i] = table.rows[i].cells[targindex];
        //���϶���td�����ʱ
        if (ftagleft > targetleft && stagleft > targetleft)
        {
            //alert("�����ѭ��...");
            for (var k = targindex; k < findex; k++)
            {
                for (var i = 0; i < table.rows.length; i++)
                {
                    table.rows[i].cells[k].swapNode(table.rows[i].cells[k + 1]);
                }
            }
        }
        if (ftagleft < targetleft && stagleft < targetleft)
        {  //���϶���td���ұ�ʱ
            //alert("�ӽ���ѭ��...");
            for (var k = targindex; k > sindex; k--)
            {
                for (var i = 0; i < table.rows.length; i++)
                {
                    table.rows[i].cells[k].swapNode(table.rows[i].cells[k - 1]);
                }
            }
        }
    }
 
}

/**************************
 *����mulline��title��td�Ŀ��
 **************************/
var tab_alldata = document.getElementsByTagName("TABLE");
function _MouseDownToResize(obj)
{
    obj.mouseDownX = event.clientX;
    obj.pareneTdW = obj.parentElement.offsetWidth;
    obj.setCapture();
}
var tCol = 0;
var newWidth = 0;
/*******************************
 *��������϶�ʱ����̬�ĸı��еĿ��
 *add by wanglei 2008-08-12
 *******************************/
function _MouseMoveToResize(obj, StrPageName)
{
    tCol = getCellIndex(obj.parentElement.parentElement);
    if (!obj.mouseDownX) return false;
    newWidth = obj.pareneTdW * 1 + event.clientX * 1 - obj.mouseDownX;
    if (newWidth > 0)
    {
        obj.parentElement.childNodes[0].style.width = newWidth;
        for (var k = 0; k < tab_alldata.length; k++)
        {
            if (tab_alldata[k].id.indexOf(StrPageName + "ExChange") == 0)
            {
                tab_alldata[k].rows[0].childNodes[tCol].style.width = newWidth;
                tab_alldata[k].rows[0].childNodes[tCol].childNodes[0].style.width = newWidth;
            }
        }
    }
}
/*******************************
 *���̧��󣬼�¼Ŀǰ�Ŀ��
 *add by wanglei 2008-08-12
 *******************************/
function _MouseUpToResize(obj, cObjInstance)
{
    for (var m = 0; m < cObjInstance.newColOrder.length; m++)
    {
        if (m == tCol - cObjInstance.totalPre)
        {
            var t = cObjInstance.newColOrder[m];
            this.arraySaveWith[t - 1][1] = (newWidth / trate) + "px";
        }
    }
    obj.releaseCapture();
    obj.mouseDownX = 0;
}
/*******************************
 *��ʾ��Ҫɸѡ����
 *add by wanglei 2008-08-13  
 *******************************/
function _ShowColChoose(obj, t_StrPageName, insobj)
{
   
    try
    {
        //alert(insobj.arrayChoose);
        var t = obj.offsetTop;
        var l = obj.offsetLeft;
        while (obj = obj.offsetParent)
        {
            l += obj.offsetLeft;
            t += obj.offsetTop;
        }
        var newDiv = document.createElement("div");
        newDiv.id = "newdiv";
        newDiv.style.position = "absolute";
        newDiv.style.width = "120px";
        newDiv.style.height = "10px";
        newDiv.style.top = t;
        newDiv.style.left = l;
        newDiv.style.background = "#FFFFFF";
        newDiv.style.border = "1px solid #860001";
        var ctext = "<table border=0 >";
        ctext = ctext + "<tr><td><input  class=title type=button value=ȷ�� onclick= _SubmitChoose('" + t_StrPageName + "'," + this.instanceName + ")></td></tr>";
        for (var i = 1; i < this.arraySave3.length; i++)
        {
            if (this.arraySave3[i][3] != 3 && this.arraySave3[i][1] != "0px")
            {
                var t = insobj.arrayChoose[i];
                ctext = ctext + "<tr border: 0px>";
                ctext = ctext + "<td border: 0px>";
                if (t == "checked")
                {
                    ctext = ctext + "<input  name=" + t_StrPageName + "choose" + i + " type=checkbox " + t + " onclick=ChooseState(" + i + "," + t + "," + this.instanceName + ")>";
                }
                else
                {
                    t = true;
                    ctext = ctext + "<input  name=" + t_StrPageName + "choose" + i + " type=checkbox  onclick=ChooseState(" + i + "," + t + "," + this.instanceName + ")>";
                }
                ctext = ctext + "<input style=WIDTH:80px readonly border: 0px value= " + this.arraySave3[i][0] + ">";
                ctext = ctext + "<input  type=button value=����  onclick=OrderBy(" + this.instanceName + "," + i + ")>";
                ctext = ctext + "</td>";
                ctext = ctext + "</tr>";
            }
        }
        ctext = ctext + "</table>"
        newDiv.innerHTML = ctext;
        document.body.appendChild(newDiv);
    }
    catch(ex)
    {
        alert(ex);
    }
}
/*******************************
 *��¼checkbox��ѡ��״̬
 *add by wanglei 2008-08-21
 *******************************/
function ChooseState(colindex, choosestate, insobj)
{
    //alert(insobj.newColOrder);
    if (choosestate == false)
    {
        insobj.arrayChoose[colindex] = false;
    }
    else
    {
        insobj.arrayChoose[colindex] = "checked";
    }

}
/*******************************
 *����,������Ǻܺ�,�ǰ�DataResult�����ݰ���ascii������
 *DataResult��������������
 *��Ŀǰ��sqlд������Ҳ�벻������δ����ݿ���������
 *add by wanglei 2008-08-14
 *******************************/
function OrderBy(cObjInstance, i)

{
        	   	 AllowSortFun(cObjInstance, i);
}
   
function OrderByName(cObjInstance, uu)

{
    
    for (var i = 1; i < cObjInstance.arraySave3.length; i++){
    	   if(uu==cObjInstance.arraySave3[i][0]){
    	   	 AllowSortFun(cObjInstance, i);
    	   	 break;
    	   	}
    	}
   
   // document.body.removeChild(document.getElementById("newdiv"));
}
/*******************************
 *ȷ����ɸѡ��
 *add by wanglei 2008-08-13
 *******************************/
function _SubmitChoose(t_StrPageName, obj)
{
    var tObjInstance = obj;
    try
    {
        for (var m = 1; m < tObjInstance.arraySave3.length; m++)
        {
            if (tObjInstance.arraySave3[m][3] == 3 || tObjInstance.arraySave3[m][1] == "0px")
            {
                continue;
            }
            var oraindex = "";
            var choosebox = document.getElementById(t_StrPageName + "choose" + m);
            var l = 0;
            for (var z = 1; z < tObjInstance.newColOrder.length; z++)
            {
                var t = tObjInstance.newColOrder[z];
                if (t - 1 == m)
                {
                    l = z;
                    if (choosebox.checked)
                    {
                        tObjInstance.arraySaveWith[m][3] = 0;
                        for (var k = 0; k < tab_alldata.length; k++)
                        {
                            if (tab_alldata[k].id.indexOf(t_StrPageName + "ExChange") != -1)
                            {
                                tab_alldata[k].rows[0].childNodes[l + tObjInstance.totalPre].style.display = "";
                            }
                        }
                        break;
                    }
                    else
                    {
                        tObjInstance.arraySaveWith[m][3] = 3;
                        for (var k = 0; k < tab_alldata.length; k++)
                        {
                            if (tab_alldata[k].id.indexOf(t_StrPageName + "ExChange") != -1)
                            {
                                tab_alldata[k].rows[0].childNodes[l + tObjInstance.totalPre].style.display = "none";
                            }
                        }
                        break;
                    }
                }
            }
        }
        document.body.removeChild(document.getElementById("newdiv"));
    }
    catch(ex)
    {
        document.body.removeChild(document.getElementById("newdiv"));
        alert("��_SubmitChoose�г����쳣" + ex);
    }
}
/*******************************
 *ΪObject����Clone����,��Ϊ��Щֵ����Ҫһֱ������(�ڶ�������֮ǰ)
 *add by wanglei 2008-08-13
 *******************************/
function Clone(objecta)
{
    var objClone;
    var that = objecta;
		try{
    		if (that.constructor == Object) objClone = new that.constructor();
    		else objClone = new that.constructor(that.valueOf());
    		for (var key in that)
    		{
    		    if (objClone[key] != that[key])
    		    {
    		        if (typeof(that[key]) == 'object')
    		        {
    		            objClone[key] = that[key].Clone();
    		        }
    		        else
    		        {
    		            objClone[key] = that[key];
    		        }
    		    }
    		}
    
    	objClone.toString = that.toString;
   	  objClone.valueOf = that.valueOf;
    }catch(ex){
    	
    }
    return objClone;
}
/*******************************
 *���ΪIE6-7��һ��bugд�ķ���,ff�������������
 *��,��㲻��΢��ΪʲôҪ��ô���.
 *add by wanglei 2008-08-13
 *******************************/
function getCellIndex(td)
{
    var cells = td.parentNode.cells;
    for (var i = 0,j = cells.length; i < j; i++)
    {
        if (cells[i] === td)
        {
            return i;
        }
    }
    return td.cellIndex;
}
var trate = 0;//������ʾͲ��ı���

function checkEdit(obj)
{
    obj.parentNode.parentNode.childNodes[0].childNodes[1].checked = true;
}

/**
 * ����������ָ�����е�����(�ⲿ/�ڲ�����)
 * ���룺cRow:  ��
 *       cCol:  ��
 *       cData: ����
 *       cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *		 cCode: 
 			ֵΪ"readonly"ʱ��������Ϊֻ��;
 			Ϊ�����ǿ�ֵ����Ϊcodeselect code��
 			Ϊnull��ֻ��ֵ
 			""����ʾ�����
 *		 cWidth:�趨�п�
 *       ��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;
 *       ������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���
 */
function _SetRowColDataCustomize(cRow, cCol, cData, cObjInstance, cCode, cWidth)
{
	var tStr;
	var tObj;
	var tReturn=false;
	var tObjInstance;         //����ָ��
	if (cObjInstance==null)
		tObjInstance=this;
	else
		tObjInstance=cObjInstance;
	tObjInstance.errorString="";
	if(cData==null) cData="";
	if(cRow<0||cRow>=tObjInstance.mulLineCount)
	{
		alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cRow);
		return tReturn;
	}
	if(cCol<0||cCol>=tObjInstance.colCount)
	{
		alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cCol);
		return tReturn;
	}
	try
	{
		cData+="";
		var newData=cData.replace("\r\n","");
		// 2006-02-16 Kevin
		// calling replace(cData, "\\", "\\\\") results in IE hangs
		// so we have to replace "\\" with "\\\\" by following method
		if( chkzh(newData) == false )
		{
			cData=newData;
			newData="";
			var vIndex=0;
			var vSubStr="";
			var tArrLength=cData.length;
			for(vIndex=0; vIndex < tArrLength; vIndex++)
			{
				vSubStr=cData.substring(vIndex, vIndex + 1);
				if( vSubStr == "\\" )
				{
					newData+="\\\\";
				}
				else
				{
					newData+=vSubStr;
				}
			}
		}
		
		var tColArr=document.getElementsByName(tObjInstance.instanceName+cCol);
		var ele = tColArr[cRow];  

		if(cCode == null)
		{
			ele.setAttribute("value",cData);
		}
		else if(cCode != null && cCode != "")
		{
			if(cCode == "readonly")
			{
				ele.setAttribute("readOnly","true");
				ele.setAttribute("value",cData);
				ele.setAttribute("type","text");
				ele.setAttribute("className","common");
				ele.ondblclick=null;
				ele.onkeyup=null;
			}
			else
			{
				ele.readOnly = true;
				ele.setAttribute("value",cData);
				ele.ondblclick = function()
		        {
		            showCodeList(cCode,[this],null,null,null,null,null,null);
		        }
				ele.onkeyup=function f()
				{
					showCodeListKey(cCode,[this],null,null,null,null,null,null);
				} 
			}
		}
		else if(cCode == "")
		{
			ele.setAttribute("value",cData);
			ele.setAttribute("type","text");
			ele.setAttribute("className","common");
		}

		tReturn=true;
	}
	catch(ex)
	{
		_DisplayError("�� MulLine.js-->_SetRowColDataCustomize �����з����쳣��" + ex.message + " ", tObjInstance);//edit by yaory
	}
	return tReturn;
}

//==============================================================================

/**
 * ����������ָ�����е�����(�ⲿ/�ڲ�����)
 * ���룺cRow:  ��
 *       cCol:  ��
 *       cData: ����
 *       cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *		 cCode: 
 			ֵΪ"readonly"ʱ��������Ϊֻ��;
 			Ϊ�����ǿ�ֵ����Ϊcodeselect code��
 			Ϊnull��ֻ��ֵ
 			""����ʾ�����
 *		 cWidth:�趨�п�
 *       ��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;
 *       ������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���
 */
function _SetRowColDataCustomize1(cRow, cCol, cData, cObjInstance, cCode, cWidth)
{
	var tStr;
	var tObj;
	var tReturn=false;
	var tObjInstance;         //����ָ��
	if (cObjInstance==null)
		tObjInstance=this;
	else
		tObjInstance=cObjInstance;
	tObjInstance.errorString="";
	if(cData==null) cData="";
	if(cRow<0||cRow>=tObjInstance.mulLineCount)
	{
		alert("�� MulLine.js-->SetRowColDataCustomize1() ʱָ���˴������:"+cRow);
		return tReturn;
	}
	if(cCol<0||cCol>=tObjInstance.colCount)
	{
		alert("�� MulLine.js-->SetRowColDataCustomize1() ʱָ���˴������:"+cCol);
		return tReturn;
	}
	try
	{
		cData+="";
		var newData=cData.replace("\r\n","");
		// 2006-02-16 Kevin
		// calling replace(cData, "\\", "\\\\") results in IE hangs
		// so we have to replace "\\" with "\\\\" by following method
		if( chkzh(newData) == false )
		{
			cData=newData;
			newData="";
			var vIndex=0;
			var vSubStr="";
			var tArrLength=cData.length;
			for(vIndex=0; vIndex < tArrLength; vIndex++)
			{
				vSubStr=cData.substring(vIndex, vIndex + 1);
				if( vSubStr == "\\" )
				{
					newData+="\\\\";
				}
				else
				{
					newData+=vSubStr;
				}
			}
		}
		
		var tColArr=document.getElementsByName(tObjInstance.instanceName+cCol);
		var ele = tColArr[cRow];  

		if(cCode == null)
		{
			ele.setAttribute("value",cData);
		}
		else if(cCode != null && cCode != "")
		{
			if(cCode == "readonly")
			{
				ele.setAttribute("value",cData);
				ele.setAttribute("type","text");
				ele.setAttribute("className","common");
				ele.ondblclick=null;
				ele.onkeyup=null;
			}
			else
			{
				ele.setAttribute("value",cData);
				ele.ondblclick = function()
		        {
		            showCodeList(cCode,[this],null,null,null,null,null,null);
		        }
				ele.onkeyup=function f()
				{
					showCodeListKey(cCode,[this],null,null,null,null,null,null);
				} 
			}
		}
		else if(cCode == "")
		{
			ele.setAttribute("value",cData);
			ele.setAttribute("type","text");
			ele.setAttribute("className","common");
		}

		tReturn=true;
	}
	catch(ex)
	{
		_DisplayError("�� MulLine.js-->_SetRowColDataCustomize1 �����з����쳣��" + ex.message + " ", tObjInstance);//edit by yaory
	}
	return tReturn;
}

///////////////////////////////////////////////
/**
 * ����:�趨ĳ��ĳ��Ϊ�����б�ѡ��
 */
function _SetRowColDataShowCodeList(cRow, cCol, cData, cObjInstance, cCode, cCondition)
{
	//alert(cCondition);
	var tStr;
	var tObj;
	var tReturn=false;
	var tObjInstance;         //����ָ��
	if (cObjInstance==null)
		tObjInstance=this;
	else
		tObjInstance=cObjInstance;
	tObjInstance.errorString="";
	if(cData==null) cData="";
	if(cRow<0||cRow>=tObjInstance.mulLineCount)
	{
		alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cRow);
		return tReturn;
	}
	if(cCol<0||cCol>=tObjInstance.colCount)
	{
		alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cCol);
		return tReturn;
	}
	try
	{
		cData+="";
		var newData=cData.replace("\r\n","");
		// 2006-02-16 Kevin
		// calling replace(cData, "\\", "\\\\") results in IE hangs
		// so we have to replace "\\" with "\\\\" by following method
		if( chkzh(newData) == false )
		{
			cData=newData;
			newData="";
			var vIndex=0;
			var vSubStr="";
			var tArrLength=cData.length;
			for(vIndex=0; vIndex < tArrLength; vIndex++)
			{
				vSubStr=cData.substring(vIndex, vIndex + 1);
				if( vSubStr == "\\" )
				{
					newData+="\\\\";
				}
				else
				{
					newData+=vSubStr;
				}
			}
		}
		
		var tColArr=document.getElementsByName(tObjInstance.instanceName+cCol);
		var ele = tColArr[cRow];  
//alert("cCode:"+cCode);
		if(cCode == null)
		{
			ele.setAttribute("value",cData);
		}
		else if(cCode != null && cCode != "")
		{
			{
				ele.readOnly = true;
				ele.setAttribute("value",cData);
				//alert("cCode:"+cCode+":cCondition:"+cCondition);
				ele.ondblclick = function()
		        {
		        	//showCodeList('pd_lc_checkfield',[this,CheckFieldName],[0,1],null,'<%=tType%>','<%=tEdorType%>',1)
		        	
		            showCodeList(cCode,[this],null,null,null,cCondition,1);
		        }
				ele.onkeyup=function ()
				{
					showCodeListKey(cCode,[this],null,null,null,cCondition,1);
				} 
			}
		}
		else if(cCode == "")
		{
			ele.setAttribute("value",cData);
			ele.setAttribute("type","text");
			ele.setAttribute("className","common");
		}

		tReturn=true;
	}
	catch(ex)
	{
		_DisplayError("�� MulLine.js-->_SetRowColDataCustomize �����з����쳣��" + ex.message + " ", tObjInstance);//edit by yaory
	}
	return tReturn;
}