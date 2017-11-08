/************************************************
 *
 *	��������: MulLine.js
 *	������: ���¼���룬����jquery���ɱ�
 *
 *************************************************/
/************************************************
 *	�ࣺ����������
 *************************************************/
/**	��ʼ��mulLine��,����initMulLineEnter
 *	@param  : string ���ؼ�����
 *	@param	: string ʵ������
 *	@return : none
 *	
 *	@logic	: 
 *	@global	: Ӱ�쵽��mulLine���Ժ�html�������г�ʼ��
 *				ʹ�õ�mulLine���Ժ�html����
 **/
var $j15 = jQuery.noConflict();
function MulLineEnter(iFormName, iInstanceName)
{
    //����������Ҫ�û���ʼ��
    this.formName = iFormName || "fm";	//���ؼ����ƣ�����
    this.instanceName = iInstanceName || "";	//ʵ�����ƣ�����
    this.mulLineCount = 0;	//��������������
    this.canAdd = 1;	//�Ƿ������������,ɾ��1��ʾ����,0��ʾ������
    this.canSel = 0;	//�Ƿ����ѡ��,1��ʾ����,0��ʾ������
    this.showTitle = 1; //�Ƿ���ʾ�ؼ�title 1��ʾ��ʾ,0��ʾ����ʾ
    this.displayTitle = 1;	//�Ƿ���ʾ��ͷ����,1��ʾ��ʾ,0��ʾ����ʾ
    this.locked = 0;	//�Ƿ�����,1��ʾ����,0��ʾ�ɱ༭
    this.canChk = 0;	//�Ƿ���Ҫ����ѡ��,1��ʾ���Զ���ѡ��,0��ʾ������
    this.displayCanChkAll = 0;    //�Ƿ���Ҫȫѡ��    //tongmeng 2009-05-08 modify
    this.hiddenPlus = 0;	//����,�Ƿ��������һ�еı�־��0Ϊ��ʾ,1Ϊ����
    this.hiddenSubtraction = 0;	//����,�Ƿ�����ɾ��һ�еı�־��0Ϊ��ʾ,1Ϊ����
    this.mulLineNum = 1;	//����,����ͬһ�е�MulLine�ĸ���,Ĭ����1
    this.flexConfig = {dataType: 'json',height: 'auto',cellEffect:'on'};//���ڿ���flexgrid����ʽ
    this.choiceColor = true;//��ѡ��radio����checkboxʱ���ñ�����ɫ

    
    //�����ⲿ�������ƺͲ���
	this.chkBoxEventFuncName = "";	//����,�����ⲿ����CheckBox��ʱ��Ӧ���ⲿ������
	this.chkBoxEventFuncParm = " ";	//����,�����ⲿ����CheckBox��ʱ��Ӧ���ⲿ��������Ĳ���
	this.chkBoxAllEventFuncName = "";	//����,�����ⲿ����������ȫѡCheckBox��ʱ��Ӧ���ⲿ������
	this.selBoxEventFuncName = "";	//����,�����ⲿ����RadioBox��ʱ��Ӧ���ⲿ������
	this.selBoxEventFuncParm = " ";	//����,�����ⲿ����RadioBox��ʱ��Ӧ���ⲿ��������Ĳ���
	this.addEventFuncName = "";	//����,�����ⲿ����+��ťʱ��Ӧ���ⲿ������
	this.addEventFuncParm = " ";	//����,�����ⲿ����+��ť��ʱ��Ӧ���ⲿ��������Ĳ���
	this.delEventFuncName = "";	//����,�����ⲿ����-��ťʱ��Ӧ���ⲿ������
	this.delEventFuncParm = " ";	//����,�����ⲿ����-��ť��ʱ��Ӧ���ⲿ��������Ĳ���

    //�������Բ���Ҫ�û���ʼ��
    this.colCount = 0;	//����,�е���Ŀ
    this.recordNo = 0;	//����,�����ҳ��ʾ������¼,��ô��ʾǰ����ֵ��Ϊ����,��ô��2ҳ��ʾ����Ż������ҳ�����,��turnPage�޸�
    this.checkFlag = 0;	//����,��checkAll���������,0 δȫѡ,1 ȫѡ
    this.state = 0;	//����,�˲������ⲿ���κ�ʵ������,��_ResumeState����һ��ʹ�ã���δʹ�ã�
    this.arraySave = new Array();	//����,���洫���������
    this.arraySave2 = new Array();	//����,�������������--�����Ƿ���ʾ���ģ���δʹ�ã�
    this.arraySaveOra = new Array();	//����,˳��û�иı������
    this.arraySaveOrder = new Array(); //��¼����˳���ж�Ӧ��ԭʼ�е�λ��

	this.editArrayStore = new Array();//����,�༭��ť��Ϣ

    this.arraySaveWith = new Array();//�����仯������Ϣ��array����δʹ�ã�
    this.arraySave3 = new Array();//�����仯������Ϣ��array����δʹ�ã�
    this.arrayChoose = new Array();//����ѡ������Ϣ��array����δʹ�ã�
    this.newColOrder = new Array();//�е�˳�� add by wanglei
    this.editArray = new Array();//�����洢������Ŧ��Ϣ add by jinsh����δʹ�ã�
    
    
    this.totalPre = 0;//table��ǰ׺����,����sel,chk ��.����δʹ�ã�
    this.AllowSort = "1";	//����
    this.SortPage = "";	//������Grid��Ӧ��turnpage
    this.ShowPageMark = true;//�Ƿ���ʾ�Դ��ķ�ҳ��ť
    this.allowsort = "AllowSortFun";	//Grid��������ͨ��������turnpage�еĺ�������δʹ�ã�
    this.windowWidth = window.document.body.clientWidth;
    this.windowHeight = window.document.body.clientHeight;

    this.detailInfo = "";	//���֧�ֵ���,���ڴ˴�������ʾ��Ϣ����δʹ�ã�
    this.tableWidth = "";	//����table�Ŀ��

	//�������������ɱ�����Ҫ����
	this.initTable;//����flexigrid֮ǰ�ı�����(�ڲ�ʹ�ã�
    this.mulLineText = "";	//����������һ��ģ�������(�ڲ�ʹ�ã�
    this.mulLineTextTitle = "";	//���������ı��⣨�ڲ�ʹ�ã�

    this.lastFocusRowNo = -1; //���һ�εõ��������(��0��ʼ)
    this.lastFocusColNo = -1; //���һ�εõ��������(��1��ʼ)


    this.maxSpanID = -1;	//�������������SpanID��ֵ    //��ʼ�����һ��������,spanID��-1 �ĳ�-2����δʹ�ã�
    this.errorString = "";	//�ñ���Ϊ��ִ�з�������ʱ,��ʾ�Ĵ�����Ϣ����δʹ�ã�

//����jquery���ã�ʵ���϶�����ʽ����ʾ�ȹ���
    try
    {
    	if(typeof(jQuery)!="function") throw "need jQuery!";
    	if(typeof($j15)!="function") throw "need jQuery!";
	   	var jquery_mulline = $j15(iFormName).initMulLineEnter(this);//alert(jquery_mulline.length);
    }
    catch(ex)
    {
    	alert("initMuLineEnter Failed!"+ex);
    }
    //����
    //���ɱ����Ҫ����
    this.loadMulLine = jquery_mulline.LoadMulLine;//���ɲ������ݵı�
    this.loadMulLineArr = jquery_mulline.LoadMulLineArr;//���ɴ����ݵı�
    this.loadPage = jquery_mulline.LoadPage;//����ҳ��
    this.setFieldValue = jquery_mulline.SetFieldValue;//����ģ��
    
    this.clearData = jquery_mulline.ClearData;//�������
    this.addOne = jquery_mulline.AddOneWithoutData;//����ģ�����һ�У����У��հ���
	this.deleteOne = jquery_mulline.DeleteOne;//ɾ��ָ����
	this.delBlankLine = jquery_mulline.DelBlankLine;//ɾ���հ���
	this.delCheckTrueLine = jquery_mulline.DelCheckTrueLine;	//ɾ��ѡ�е�CheckBox��
	this.delRadioTrueLine = jquery_mulline.DelRadioTrueLine;	//ɾ��ѡ�е�RadioBox��
	
	this.updateField = jquery_mulline.UpdateField;//��̬Ϊ����޸�ָ����(�����������)
	this.setPageMark = jquery_mulline.SetPageMark;//����ҳ����Ϣ
	this.reload = jquery_mulline.Reload;//�������������¼��ص�ǰ���
	
    //�����������,�����¼�,��ֵ,��ý���,���ý���
	this.radioClick = jquery_mulline.RadioClick;//��ѡ�����¼�
	this.checkBoxClick = jquery_mulline.CheckBoxClick;//��ѡ�����¼�
	
	this.orderByName = jquery_mulline.OrderByName;//����
	
	this.setTitle = jquery_mulline.SetTitle;//input�е�title�������ó�title
	
  	this.getSelNo = jquery_mulline.GetSelNo;//��ñ�ѡ�еĵ�ѡ����
	this.getChkNo = jquery_mulline.GetChkNo;//�жϸö�ѡ���Ƿ�ѡ��
    this.checkAll = jquery_mulline.CheckAll;//ȫѡ���߲�ѡ
    this.checkBoxAll = jquery_mulline.CheckBoxAll;//ȫѡ
    this.checkBoxAllNot = jquery_mulline.CheckBoxAllNot;//ȫ��ѡ
    this.checkBoxSel = jquery_mulline.CheckBoxSel;//ѡ��ָ����
    this.checkBoxNotSel = jquery_mulline.CheckBoxNotSel;//ȡ��ָ����ѡ��״̬
    
    this.setRowColData = jquery_mulline.SetRowColData;//����ĳ��ĳ��ֵ
    this.getRowColData = jquery_mulline.GetRowColData;//���ĳ��ĳ��ֵ
    this.getRowColElemt = jquery_mulline.GetRowColElemt;
    this.getRowColClass = jquery_mulline.GetRowColClass;//��ȡĳһ���е�classname
    this.setRowColDataByName = jquery_mulline.SetRowColDataByName;//����ĳ��ĳ��ֵ
    this.getRowColDataByName = jquery_mulline.GetRowColDataByName;//���ĳ��ĳ��ֵ
    this.getRowData = jquery_mulline.GetRowData;//���ĳ��ֵ
    this.setFocus = jquery_mulline.SetFocus;//���ý���
    this.getFocus = jquery_mulline.GetFocus;//�ж��Ƿ��ý���
    this.moveFocus = jquery_mulline.MoveFocus;//�����л�ý���
    
    this.findSpanID = jquery_mulline.FindSpanID;//��ö�Ӧ�е�spanid
    
    this.lock = jquery_mulline.Lock;//����ɾ���������еĹ���
    this.unLock = jquery_mulline.Unlock;//�������ɾ���������еĹ���
    
    //����Ϊ��δʹ��jqueryʵ�ֵĹ���
    // 2009-03 ��ĳ����Ԫ��ֵ��ͬʱ,�޸���ʽ
    this.setRowColDataCustomize = jquery_mulline.SetRowColDataCustomize;
    this.setRowColDataCustomize1 = jquery_mulline.SetRowColDataCustomize1;

    this.setRowColDataShowCodeList= jquery_mulline.SetRowColDataShowCodeList    //���������б�����
    this.setRowColClass=jquery_mulline.SetRowColClass;//������Ӧ���е�class
    this.setRowClass=_setRowClass;	//������Ӧ�е�class
    this.setRowColTitle=_SetRowColTitle;//������ʾ����ʾ��
	this.setRowColCurrency = _setRowColCurrency;//����ָ���е�moneyType����
	
	this.getChkCount = jquery_mulline.GetChkCount;//���ض�ѡ��ѡ������
	this.selOneRow = jquery_mulline.SelOneRow;//ѡ��ĳ��
	this.chkOneRow = jquery_mulline.ChkOneRow;//ѡ��ĳ��
	
    this.pagebuttondown = jquery_mulline.pageButtonDown;
    this.pagebuttonup = jquery_mulline.pageButtonUp;
	
	this.checkValue = _CheckValue;//У��
	this.checkValue2 = _CheckValue2;//У��
   
/*  this.keyUp = _KeyUp;//ԭjs��δʵ��
    this.getErrStr = _GetErrStr;//�õ�������Ϣ���ݲ�ʵ��
    this.detailClick = _detailClick;//ԭjs��δʵ��
    this.resumeState = _ResumeState;//����ʵ��
*/    
}

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
 * �����������ӿؼ�����һ���õ�����ʱ������ӿؼ����к���
 * ���룺MulLine ����
 * ���룺�ӿؼ�����
 * �������
 * ��ע��XinYQ added on 2006-10-10
 */
function myDate(oInstance, oEventCtrl)
{       
       // var timeId = document.getElementById(oEventCtrl.id);
	    laydate({elem:'#' + oEventCtrl.id });
	    _CalcFocusRowColNo();
	}

function _CalcFocusRowColNo(oInstance, oEventCtrl)
{
    try
    {
        if (oInstance == null || oEventCtrl == null || typeof(oInstance) == "undefined" || typeof(oEventCtrl) == "undefined")
        {
            //alert("�� MulLine.js  _CalcFocusRowColNo �����з�������ʵ�����Ϳؼ���������Ϊ�գ� ");
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
                //alert("�� MulLine.js  _CalcFocusRowColNo �����з������󣺼��㽹�����쳣�� ");
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
                //alert("�� MulLine.js  _CalcFocusRowColNo �����з������󣺼��㽹�����쳣�� ");
                return;
            }
        }
    }
    catch (ex)
    {
        _DisplayError("�� MulLine.js  _CalcFocusRowColNo �����з����쳣��" +  ex, oInstance);
    }
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

    tObj.delBlankLine(t_StrPageName, tObj);	//�������


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

            tRule = $j15("#"+t_StrPageName+i+"r0").attr("verify");

            if (tRule == null || tRule == "")

            {

                continue;

            }

            else

            {

                try

                {

                    strInfo = "��һ�е�" + tRule;

                    var dd = "document.getElementById('" + t_StrPageName + i+"r0')";

                    if (!verifyElementWrap2(strInfo, tObj.getRowColData(0, i, tObj), dd))

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
            	
            	try{
            		tRule = eval(tObj.formName + ".getElementsByName('" + t_StrPageName + "verify" + i + "')[0].value");
            	}catch(ex) {
            		try{
                tRule = eval(tObj.formName + ".all('" + t_StrPageName + "verify" + i + "').value");
            		}catch(ex) {
                	tRule = null;
                }
                }
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

                            var dd = tObj.formName + ".getElementById('" + t_StrPageName + i + "r" + n+"')";

                            if (!verifyElementWrap2(strInfo, tObj.getRowColData(n, i, tObj), dd))

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

    tObj.delBlankLine(t_StrPageName, tObj);	//�������


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

            tRule = $j15("#"+t_StrPageName+i+"r0").attr("verify");

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

                        if (!verifyElement(strInfo, tObj.getRowColData(n, i, tObj)))

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
	if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
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
/**
 *	���ܴ�MulLine�Ĳ���,����,ת��Ϊflexigrid����jquery����ʶ��ĸ�ʽ,����flexiGrid
 *	��Ҫ��MulLine�ķ���ָ��jquery
 *	2011-09-28
 *	huangliang
 **/
(function($){
/**	��ʼ��,����jquery����
 *	@param  :	object	mulline����
 *	@return :	object	jquery����
 *	
 *	@logic	: 
 *	@global	:	Ӱ�쵽��mulLine���Ժ�html����initTable
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.initMulLineEnter = function(cObjInstance){
		cObjInstance.initTable = document.createElement('table');	//���DOM����,���ڱ������ɱ�֮ǰ�ı���Ϣ
		$(cObjInstance.initTable).append("<thead></thead><tbody></tbody>");
		return this;
	};
/**	������õĳ�ʼ������,���ڳ�ʼ�����������ݣ�,������SetFieldValue��LoadPage��
 *	@param  :	array	��ά����,���ÿ�е�����
 *	@param  :	array	��ά����,�ɱ༭��ť���ԣ�ʵ���ϲ��ã�
 *	@return :	none
 *	
 *	@logic	:	����SetFieldValue����tbody��theadģ��,����LoadPage���ɱ�
 *	@global	:	Ӱ�쵽��mulLine���Ժ�html����initTable,span,arraySave,editArrayStore,arraySaveOra
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/
	$.fn.LoadMulLine = function(iarray,eArray){
//	alert("first step!")
		if(iarray.isMulArray==true){
			iarray=iarray.mullineSubData2D;
			if(!this.saveColID(iarray))return;
		}
    	var strPageName = this.instanceName;
		(eArray ==null)?this.editArrayStore =null:this.editArrayStore = eArray;
	    this.arraySave = iarray;
    	this.arraySaveOra = iarray;
    	var $this = $(this);
		$this.SetFieldValue(strPageName,iarray,this,this.editArrayStore);
		$this.LoadPage(strPageName,this);
	};
/**	�������ɴ����ݵı�,����SetFieldValue��ChangeArrCol��SaveArrInfo��LoadPageArr
 *	@param  :	array	ÿ�е�������Ϣ
 *	@param	:	array	��ά����,����
 *	@return :	none
 *	
 *	@logic	:	SetFieldValue����ģ�壬����SaveArrInfo���ɸ��е�����λ�úͿ����Ϣ������ChangArrCol����λ�úͿ�ȵ��¸������ã�����LoadPageArr���ɴ����ݵı�
 *	@global	:	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/
	$.fn.LoadMulLineArr = function(arrCols, cData){
		var strPageName = this.instanceName;
		var $this = $(this);
		$this.SetFieldValue(strPageName,arrCols,this,this.editArrayStore);
		$this.SaveArrInfo(strPageName,this);//��¼���еĿ�ȣ�λ��
		$this.ChangeArrCol(cData,this);//���ɸ�����λ��,���
		$this.LoadPageArr(strPageName, this, cData);
	}
/**	���ڰ����úõı�ŵ���ҳ�У��������ݣ�,����AddOneWithoutData��NewTableFlexiGrid
 *	@param  :	string 	mulLine������
 *	@param  :	objcect	mulLine����
 *	@return :	none
 *	
 *	@logic	:	���ɱ�ͷthead,����AddOneWithoutData����tbody,flexigrid���ɱ��
 *	@global	:	Ӱ�쵽��mulLine���Ժ�html����initTable��arraySaveOrder
 *				ʹ�õ�mulLine���Ժ�html����instanceName,mulLineTextTitle
 **/
	$.fn.LoadPage = function(strPageName, cObjInstance){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		$("thead",tObjInstance.initTable).append(tObjInstance.mulLineTextTitle);//��ʼ����ͷ
		var th_num = $("thead tr:eq(0) th",tObjInstance.initTable).length;
		for(i=0;i<th_num;i++){tObjInstance.arraySaveOrder[i]=i}////����ԭʼ��λ��
		var $this = $(this);
		$this.AddOneWithoutData(strPageName,tObjInstance.mulLineCount,tObjInstance);	//��ʼ��tbody
		$this.NewTableFlexiGrid(tObjInstance);//���ɱ�
	};
/**	���ڰ����úõı�ŵ���ҳ�У������ݣ�,����AddOneArr��flexigrid��
 *	@param  :	string	ʵ���� mulline������ ��***Grid
 *	@param	:	object	cObjInstance:mulline����
 *	@param	:	array	��ά����,����
 *	@return :	none
 *	
 *	@logic	:	����AddOneArr����tbody,flexigrid���ɱ��
 *	@global	:	Ӱ�쵽��mulLine���Ժ�html����iinitTable,span
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/	
	$.fn.LoadPageArr = function(strPageName, cObjInstance, cData){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		$("thead tr",tObjInstance.initTable).replaceWith(tObjInstance.mulLineTextTitle);//���³�ʼ����ͷ
		var $this = $(this);
		$this.AddOneArr(strPageName, tObjInstance.mulLineCount, tObjInstance, cData);		//����tbody���������
		$this.NewTableFlexiGrid(tObjInstance);//���ɱ�
	};
/**	���ڽ���ɵı�ת��Ϊflexigrid������flexigrid,���ɼ�һ�а�ť
 *	@param	: cObjInstance:mulline����
 *	@return : none
 *
 *	@logic	: ��λspan�����ɱ�,�ڱ�����ɼ�һ�а�ť
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����instanceName,hiddenPlus,initTable
 **/
	$.fn.NewTableFlexiGrid = function(cObjInstance){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		var strPageName = tObjInstance.instanceName;
		var new_table = $(tObjInstance.initTable).clone();		//��������µĸ��ƶ���,title������ͬʱ����grid�Ѵ��ڣ�flexigridҲ�����
		var $span = $("#span"+ strPageName);
		var grid = $span.find("[grid]");
		var config = tObjInstance.flexConfig;//cellEffect���Դ������
		if (grid.length > 0){
			$.extend(config,grid.attr("p"));
//			$.each(grid.attr("p"), function(i, val) {
//				$("#testPreHoHo_new").append(" " + i + ":" + val + "; ");
//			});
		}
		$span.empty();
		$span.append(new_table);
		//$span.html(new_table.html());
//		$("#testPreHoHo").html(HTMLDecode($span.html()));
		$span.find("table").flexigrid(config);
		if(tObjInstance.hiddenPlus == 0){
			tStatus = "" ;
			if(tObjInstance.locked == 1)tStatus = "disabled" ;
			addplus = "<div style='float:left'><input type=button class=button name='" + strPageName + "addOne' value='  +  ' " 
				+ tStatus + " id='" + strPageName + "addOne' " + " onclick=\" " + strPageName + ".addOne('" + strPageName + "');" 
				+ strPageName + ".moveFocus();\"></div>";
			$span.append(addplus);
		}
	};
	
/**	���ڼ�¼���µı�ĸ�����Ϣ����ı��Ŀ�ȣ�λ�õ�
 *	@param  :	string	ʵ���� mulline������ ��***Grid
 *	@param	:	object	mulLine����
 *	@return :	none
 *
 *	@logic	:	������Ϣ��newColOrder��,n���������кţ�����0�������ݣ�1�����ȣ�2�����ϴθĶ�����кţ�3����ԭʼ�к�,4�����Ƿ�����
 *	@global	:	Ӱ�쵽��mulLine���Ժ�html����newColOrder��arraySaveOrder
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/
	$.fn.SaveArrInfo = function(strPageName,cObjInstance){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		var spanId = "#span" + strPageName;
		var tmp_arr = new Array();var divText = "";var divStyle = ""; var divColNo = "";
		try
		{
			tObjInstance.newColOrder = new Array();
			$(spanId).find("thead tr:eq(0) th").each(function(n){
				var $this = $(this);
				tObjInstance.newColOrder[n] = new Array();
				divText = trim($this.find("div").html());
				divStyle = $this.find("div").attr("style");
                if(divStyle){
                    divStyle = divStyle.substr(divStyle.toLowerCase().indexOf("width:")+6,divStyle.toLowerCase().indexOf("px"));
                    divStyle = trim(divStyle);
                }else{
                    divStyle = "";
                }
				if(divStyle == "")divStyle = "0px";
				divColNo = $this.attr("axis");
				divColNo = parseInt(divColNo.substr(3));
				tObjInstance.newColOrder[n][0] = divText;
				tObjInstance.newColOrder[n][1] = divStyle;
				tObjInstance.newColOrder[n][2] = divColNo;
				tObjInstance.newColOrder[n][3] = tObjInstance.arraySaveOrder[divColNo];
				tObjInstance.newColOrder[n][4] = $this.attr("style");
				tmp_arr[n] = tObjInstance.arraySaveOrder[divColNo];
			});
			tObjInstance.arraySaveOrder = tmp_arr;
		}catch(ex)
		{
			alert("��MulLine.js-->saveArrInfo�����з����쳣:" + ex);
		}
	};
/**	���ڸ����µ�����Ϣ�������µ����ã����Ķ�����˳����Ϊ����Ͷ�Ӧ�а󶨣�
 *	@param  :	array	cData,��������
 *	@param	:	object	mulLine����
 *	@return :	none
 *
 *	@logic	:	����newColOrder�е�˳�򣬵���ģ���е�λ��
 *	@global	:	Ӱ�쵽��mulLine���Ժ�html����mulLineText��mulLineTextTitle
 *				ʹ�õ�mulLine���Ժ�html����newColOrder
 **/
	$.fn.ChangeArrCol = function(cData,cObjInstance){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		var tmp_tbody_ele = document.createElement("tbody");
		var tmp_thead_ele = document.createElement("thead");
		var tmp_text = "";		var tmp_title = "";
		$tmp_tbody_ele = $(tmp_tbody_ele);
		$tmp_thead_ele = $(tmp_thead_ele);
	 	$tmp_tbody_ele.append(tObjInstance.mulLineText);
	 	$tmp_thead_ele.append(tObjInstance.mulLineTextTitle);
	 	$tmp_tbody_ele_td = $tmp_tbody_ele.find("td");
	 	$tmp_thead_ele_th = $tmp_thead_ele.find("th");
	 	for(i=0;i<tObjInstance.newColOrder.length;i++){
			colNo = tObjInstance.newColOrder[i][3];//����������㿪ʼ
			colStyle = tObjInstance.newColOrder[i][4];//��ʽ�̳У���Ҫ����������
			tmp_width = "width:" + tObjInstance.newColOrder[i][1];
			$tmp_tbody_ele_td.eq(colNo).find("div").attr("style",tmp_width);
			if(tObjInstance.newColOrder[i][1] != "0px"){
				$tmp_thead_ele_th.eq(colNo).attr("width",function(){
					tmp_title_width = tObjInstance.newColOrder[i][1];
					tmp_title_width = parseInt(tmp_title_width);
					return tmp_title_width;
				});
			}
			if(colStyle){
				$tmp_tbody_ele_td.eq(colNo).attr("style",colStyle);
				$tmp_thead_ele_th.eq(colNo).attr("style",colStyle);
			}
			tmp_text += $tmp_tbody_ele_td.get(colNo).outerHTML;
			tmp_title += $tmp_thead_ele_th.get(colNo).outerHTML;
		}
		$tmp_tbody_ele.find("tr").html(tmp_text);
		$tmp_thead_ele.find("tr").html(tmp_title);
		tObjInstance.mulLineText = $tmp_tbody_ele.html();
		tObjInstance.mulLineTextTitle = $tmp_thead_ele.html();
	};
/**	����һ�����У����ڲ������ݵĿձ���ߴ���������һ��
 *	@param  : ʵ���� mulline������ ��***Grid
 *	@param	: ������Ĭ��Ϊ1��
 *	@param	: mulline����
 *	@return : none
 *
 *	@logic	: for�����������ݣ�array�����ɲ������(�����Բ�������)������ǳ�ʼ�����򲻵���NewTableFlexiGrid
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����mulLineCount,iinitTable
 *				ʹ�õ�mulLine���Ժ�html����mulLineText��mulLineTextTitle,instanceName
 **/
	$.fn.AddOneWithoutData = function(strPageName, intNumber, cObjInstance){
		var intCount;
		var hasInit;
		(intNumber == null) ? intCount = 1 : intCount = intNumber;	//�����еĸ���,Ĭ��Ϊ1��
		if(cObjInstance == null){tObjInstance = this;hasInit=false;}else{tObjInstance = cObjInstance;hasInit=true;}
		var t_StrPageName = strPageName || tObjInstance.instanceName;	//ʵ������
		var tr_num = $("tbody tr",tObjInstance.initTable).length;
		var str_text = "";
		//�����㹻������,ʹ��tObjInstance.mulLineText�е�ģ��
		var tmp = "";var total_num = tr_num+intCount;
		var str_text = "";
		for(i=tr_num;i<total_num;i++){
			str_text = tObjInstance.mulLineText;
			str_text = str_text.replace(/\$row\$/g, i);
			str_text = str_text.replace(/\$PageName\$/g, t_StrPageName);
			str_text = str_text.replace(/\$SpanId\$/g, i);
			relace_text = t_StrPageName + "No" + i;
			recordNo = parseInt(tObjInstance.recordNo + i + 1);
			
			//str_text = str_text.replace(relace_text,relace_text+" value='"+ recordNo +"'");
			var a = "id=" + relace_text;
			var b = "id=\"" + relace_text + "\"" ;
			if(str_text.indexOf(a)!= -1){
				str_text = str_text.replace(a,a+' value="'+ recordNo +'"');
			}else{
				str_text = str_text.replace(b,b+' value="'+ recordNo +'"');
			}
			
			tmp += str_text;
		}
		$tbody = $("tbody",tObjInstance.initTable);
		var rowNum = tObjInstance.mulLineCount;
		for(var i=0;i<rowNum;i++){//ͬ������MulLine�е�����
			var rowData = tObjInstance.getRowData(i);
			for(var j=0,size=rowData.length;j<size;j++){
				var col = j+1;
			    $tbody.find("#"+t_StrPageName+col+"r"+i).val(rowData[j]);
			}
		} 
		//tmp = "<tbody>" + $tbody.html() + tmp + "</tbody>";
		$tbody.append(tmp);
		tObjInstance.mulLineCount = total_num;		//�޸�muline��mulLineCount
		if(!hasInit) {$(this).NewTableFlexiGrid(tObjInstance);}//����ǵ������һ�����ɱ�
	};	
/**	�������ɴ����ݵı�
 *	@param  : ʵ���� mulline������ ��***Grid
 *	@param	: �������ݲ�ʹ��,ʹ��cData�ĳ���
 *	@param	: cObjInstance:mulline����
 *	@param	: array	��ά����,����
 *	@return : none
 *	
 *	@logic	: for�����������ݣ�array�����ɲ������,each�������������ÿ��input��ֵ
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����mulLineCount,iinitTable
 *				ʹ�õ�mulLine���Ժ�html����mulLineText��mulLineTextTitle
 **/	
	$.fn.AddOneArr = function(strPageName, intNumber, cObjInstance, cData){
//�����������ɱ�
		var tr_num = $("tbody tr",cObjInstance.initTable).length;
		var str_text = "";var tmp_tr = "";var tmp_td = "";
		var tmp = "";var replace_text = "";var recordNo = 0;
		for(i=0;i<cData.length;i++){
			str_text = cObjInstance.mulLineText;
			str_text = str_text.replace(/\$row\$/g, i);
			str_text = str_text.replace(/\$PageName\$/g, strPageName);
			str_text = str_text.replace(/\$SpanId\$/g, i);
			relace_text = strPageName + "No" + i;
			recordNo = parseInt(cObjInstance.recordNo + i + 1);
			
			var a = "id=" + relace_text;
			var b = "id=\"" + relace_text + "\"" ;
			if(str_text.indexOf(a)!= -1){
				str_text = str_text.replace(a,a+' value="'+ recordNo +'"');
				}else{
					str_text = str_text.replace(b,b+' value="'+ recordNo +'"');
					}
			//str_text = str_text.replace(relace_text,relace_text+'" value="'+ recordNo +'"');
			for(j=0;j<cData[i].length;j++){
				k = j + 1;
				replace_text = strPageName + k + "r" + i;
				//str_text = str_text.replace(replace_text,replace_text+'" value="'+cData[i][j]+'"');
				a = "id=" + replace_text;
			    b = "id=\"" + replace_text + "\"" ;
			if(str_text.indexOf(a)!= -1){
				str_text = str_text.replace(a,a+' value="'+ cData[i][j] +'"');
				}else{
					str_text = str_text.replace(b,b+' value= "'+  cData[i][j] +'"');
					}
			}
			tmp += str_text;
		}
		cObjInstance.mulLineCount = cData.length;		//�޸�muline��mulLineCount
		$(cObjInstance.initTable).find("tbody").html(tmp);
	};
/**	����ɾ��ָ��id��
 *	@param  : ʵ���� mulline������ ��***Grid
 *	@param	: ��id:span***Grid1,�����������id
 *	@param	: cObjInstance:mulline����
 *	@return : none
 *
 *	@logic	: for�����������ݣ�array�����ɲ������(�����Բ�������)
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����mulLineCount,iinitTable
 *				ʹ�õ�mulLine���Ժ�html����mulLineText��mulLineTextTitle,instanceName
 **/
	$.fn.DeleteOne = function(strPageName, spanID, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(spanID) == "string"){//�����ַ���
			spanID = "#" + spanID;
			span = "#span" + tObjInstance.instanceName;
			$(spanID,tObjInstance.initTable).remove();
			$(spanID,span).remove();
			tObjInstance.mulLineCount--;
		}else if(typeof(spanID) == "object"){//��������
			for(i=0;i<spanID.length;i++){
				spanID[i] = "#" + spanID[i];
				$(spanID[i],tObjInstance.initTable).remove();
				tObjInstance.mulLineCount--;
			}
			$(this).NewTableFlexiGrid(tObjInstance);//���ɱ�
		}
	};
/**	����ɾ��ָ��id��
 *	@param  : ʵ���� mulline������ ��***Grid
 *	@param	: cObjInstance:mulline����
 *	@return : none
 *
 *	@logic	: for�����������ݣ�array�����ɲ������(�����Բ�������)
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����mulLineCount,iinitTable
 *				ʹ�õ�mulLine���Ժ�html����mulLineText��mulLineTextTitle,instanceName
 **/
	$.fn.DelBlankLine = function(strPageName, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var t_StrPageName = strPageName||tObjInstance.instanceName;
		var rowCount = tObjInstance.mulLineCount;	//����
		var colCount = tObjInstance.colCount;	//����
		var i,j;var blankFlag = true;	//���б�־
		var lineSpanID;	//�е�spanID
		var data = "";var $this = $(this);
		try{
			//ѭ����ѯÿһ���Ƿ�Ϊ����,�����е�ÿһ�ж�Ϊ�գ�����0�У�����У�
			for (i = 0; i < rowCount; i++){//���п�ʼѭ��,0�п�ʼ
				for (j = 1; j < colCount; j++){
					//���п�ʼѭ����1�п�ʼ
					data = $this.GetRowColData(i, j, tObjInstance);
					if (data != null && data != ""){
						//�����Ϊ�գ����б�־��Ϊfalse
						blankFlag = false;break;
					}
				}
				if (blankFlag){
					lineSpanID = $this.FindSpanID(i, tObjInstance);  //�õ����е�spanID
					$this.DeleteOne(t_StrPageName, lineSpanID, tObjInstance); //ɾ����һ��
					rowCount = rowCount - 1;//ɾ��һ�У�ѭ����һ
					i = i - 1;//����һ�м��
				}
				blankFlag = true;
			}
		}catch(ex){
			alert("��MulLine.js-->delBlankLine�����з����쳣:" + ex);
		}
	};	
/**	����ɾ��checkboxѡ�е���
 *	@param  : ʵ���� mulline������ ��***Grid
 *	@param	: cObjInstance:mulline����
 *	@return : none
 *
 *	@logic	: for��������checkbox����deleteone��ɾ����Ӧ�������±�
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/
	$.fn.DelCheckTrueLine = function(strPageName, cObjInstance){
		var tObjInstance;var tArray = new Array;var j = 0;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canChk != 1) {alert("no checkBox!");return false;}
			var rowCount = tObjInstance.mulLineCount;
			for(i=0;i<rowCount;i++){
				checkTrueFlag = tObjInstance.getChkNo(i, tObjInstance);
				if(checkTrueFlag == true){
					tArray[j] = tObjInstance.findSpanID(i, tObjInstance);	//�õ����е�spanID
					j++;
				}
			}
			if(tArray.length > 0){
				tObjInstance.deleteOne(tObjInstance.instanceName, tArray, tObjInstance);
			}
		}catch(ex){
			alert("��MulLine.js-->delCheckTrueLine�����з����쳣:" + ex);
		}
	};
/**	����ɾ��radioѡ�е���
 *	@param  : ʵ���� mulline������ ��***Grid
 *	@param	: cObjInstance:mulline����
 *	@return : none
 *
 *	@logic	: for��������radio����deleteone��ɾ����Ӧ��
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����mulLineText��mulLineTextTitle,instanceName
 **/
	$.fn.DelRadioTrueLine = function(strPageName, cObjInstance){
		var tObjInstance;var j = 0;
		var lineSpanID = 0;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canSel != 1) {throw "no checkBox!";}
			var rowCount = tObjInstance.mulLineCount;
			selNo = tObjInstance.getSelNo(tObjInstance) -1;
			lineSpanID = tObjInstance.findSpanID(selNo, tObjInstance);	//�õ����е�spanID
			tObjInstance.deleteOne(tObjInstance.instanceName, lineSpanID, tObjInstance);
		}catch(ex){
			alert("��MulLine.js-->delRadioTrueLine�����з����쳣:" + ex);
		}
	};
/**
 *	����ָ���е�id(�ⲿ/�ڲ�����)
 *	@param	:	cIndex:  �к�,��0�п�ʼ
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	string:	��id
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
 	$.fn.FindSpanID = function(cIndex, cObjInstance){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(cIndex < 0 || cIndex >= tObjInstance.mulLineCount){
				throw"��MulLine.js-->FindSpanID������ָ���˴������:" + cIndex;}
			if(tObjInstance.mulLineCount > 0 && cIndex < tObjInstance.mulLineCount){
				tReturn = $(tObjInstance.initTable).find("tbody tr:eq(" + cIndex + ")").attr("id");
			}
		}catch(ex){
			alert("��MulLine.js-->findSpanID�����з����쳣:" + ex);
		}
		return tReturn;
	};
/**
 *	��̬�޸�(������)ָ���е�����(�ⲿ����)
 *	@param	:	cIndex:  �к�,��0�п�ʼ
 *	@param	:	arrCol:  ָ���е�����������
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	none
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����arraySave
 *				ʹ�õ�mulLine���Ժ�html����
 **/
 	$.fn.UpdateField = function(cIndex, arrCol, cObjInstance){
		var tObjInstance;var i = cIndex;var $this = $(this);
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			tObjInstance.arraySave[i] = arrCol;
			$this.SetFieldValue(tObjInstance.instanceName, tObjInstance.arraySave, tObjInstance);
			$this.LoadPage(tObjInstance.instanceName, tObjInstance);
		}catch(ex){
			alert("��MulLine.js-->UpdateField�����з����쳣:" + ex);
		}
	};
/**	
 *	�������úͲ���,�������ɱ������ģ��,thead��tbody����ʽ,����,�¼�,������,��������֮ǰʹ�õĲ����϶�,�˴���������Щ����,����ʹ��ȫ������
 *	@param  : string ҳ����
 *	@param  : array	��ά����,���ÿ�е�����
 *	@param  : cObjInstance: mulLine����
 *	@param  : array	����,���ÿ�е��Ƿ�ɱ༭
 *	@return : none
 *	
 *	@logic	: 	��һ�����������ɱ�ͷ��ģ�����,����mulLineTextTitle	
 *				�ڶ��������ɱ����ݵ�ģ�����,����mulLineText	
 *				�����ɻ�������td��input��,����iarray������Ӧ������,������ɺõ�ģ��ת�����ַ�����ֵ����������,�ṩ�����ɱ��ʹ��
 *	@global	: Ӱ�쵽��mulLine���Ժ�html����mulLineText,mulLineTextTitle,tableWidth,colCount
 *				ʹ�õ�mulLine���Ժ�html����canSel,canChk,windowWidth,mulLineNum
 							selBoxEventFuncName,selBoxEventFuncParm,chkBoxEventFuncName
 **/
    $.fn.SetFieldValue = function(strPageName,iarray,cObjInstance,eArray){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		tObjInstance.mulLineTextTitle	= document.createElement('tr');		
		tObjInstance.mulLineText	= document.createElement('tr');
		
		var $mulLineText = $(tObjInstance.mulLineText);
		var $mulLineTextTitle = $(tObjInstance.mulLineTextTitle);//ʹ�ñ�������jquery�������Ч��
		
		$mulLineText.attr("id","span$PageName$$SpanId$");
		for(k=0;k<iarray.length;k++){
			$mulLineTextTitle.append("<th></th>");
			$mulLineText.append("<td><div><input /></div></td>");
		}
		//�޸�mulLine���Ժ�html����,����
		fieldCount = iarray.length;
		tObjInstance.colCount = fieldCount;
		
		//��ʼ������
		var input_type = "";
		var td_width = 0;
		var input_name = "";
		var input_id = "";
		var max_length = "";
		var td_class = "";
		var input_class = "";
		var read_only ="";
		var input_style = "";
		
		var code_data = "";
		var amt_col = "";
		var money_type = "";
		var show_style = "";
		var show_class = "";
		var input_verify = "";
		
		var event_on_click = "";
		var event_on_dblclick = "";
		var event_on_change = "";
		var event_on_focus = "";
		var event_on_keyup = "";
		var event_on_mouseover = "";
		
//������ȸ��Ƹ�tObjInstance.tableWidth,����rate,ʹ���������,�ܺ�ҳ��ͬ��
		var rate = 1 / tObjInstance.mulLineNum;
		if(tObjInstance.tableWidth =="" ||tObjInstance.tableWidth ==null)tObjInstance.tableWidth= 0;
		if(tObjInstance.tableWidth == 0){
			$.each(iarray,function(i,td_config){
				tempText1 = td_config[1];
				if(td_config[3]!=3)tObjInstance.tableWidth = tObjInstance.tableWidth + parseInt(tempText1);//���ϸ�����ʾ���п�
			});
			if(tObjInstance.canSel == 1 || tObjInstance.canChk == 1)tObjInstance.tableWidth +=30;//����box��
			if(tObjInstance.hiddenSubtraction == 0){tObjInstance.tableWidth +=22;}
			tObjInstance.tableWidth +=30;//���ܿ��΢��
		}
		if (tObjInstance.tableWidth < tObjInstance.windowWidth){
			rate = (tObjInstance.windowWidth / tObjInstance.tableWidth) / tObjInstance.mulLineNum;
		}
//����
		$.each(iarray,function(i,td_config){
//td_config��Ӧ���ϵĸ�������,������tempText�Ǵ�ԭ����mulLine���ƹ���
			tempText0 = td_config[0];	//�����е�����
			tempText1 = td_config[1];	//�����п�
            tempText2 = td_config[2];	//�������������ֵ
            tempText4 = td_config[3];	//�������Ƿ���������,����,����ѡ��
            tempText5 = td_config[4];	//��������(���ݴӺ�̨���ݿ�ȡ)--������
            tempText6 = td_config[5];	//�������ö�Ӧ�Ķ��� (���ݴӺ�̨���ݿ�ȡ)
            tempText7 = td_config[6];	//�������ö�Ӧ�Ķ��е��ڲ�ֵ(���ݴӺ�̨���ݿ�ȡ)
            tempText8 = td_config[7];	//��Ӧ���ⲿ��js�����������ǵ�ǰ�е�spanID,�㴫������飩
            tempText9 = td_config[8];	//��Ӧ���ⲿ��js�����ĵ�2������
            tempText10 = td_config[9];	//��ʽУ��
            tempText11 = td_config[10];	//��������(���ݴ�ǰ̨����)--������
            tempText12 = td_config[11];	//��������(���ݴ�ǰ̨����)
            tempText13 = td_config[12];	//��������(���ݴ�ǰ̨����)--���ж���
            tempText14 = td_config[13];	//��������(���ݴ�ǰ̨����)
            tempText15 = td_config[14];	//�û����ø��г���
            tempText16 = td_config[15];	//���õ�ǰ�е�˫��������ʾ�����������ؼ����е�����
            tempText17 = td_config[16];	//���õ�ǰ�е�˫��������ʾ�����������ؼ���ֵ
            tempText18 = td_config[17];	//���õ�ǰ�е�˫��������ʾ�����������е�ֵ
            tempText19 = td_config[18];	//���õ�ǰ�е�˫���µ�������������Ŀ�ȣ�רΪcodeSelect�������:��8��������
            tempText20 = td_config[19];	//���õ�ǰ�е�˫����ǿ��ˢ��codeSelect����Դ��רΪcodeSelect�������:��7��������
			tempText21 = td_config[20];	//�˴�����,����setRowColData�������жϸò���,�Ƿ񽫱���תΪ���ģ���
			tempText22 = td_config[21]; //���뷽ʽ,������
			tempText23 = td_config[22]; //����ָ������ֻ��ҵ�����
			tempText24 = td_config[23]; //����ָ�����ҿ��Ƿ���ֻ��
			tempText25 = td_config[24]; //���õ�ǰ��ʧȥ���㴥�����ⲿ����
			tempText26 = td_config[25]; //���õ�ǰ��ʧȥ���㴥�����ⲿ�����Ĳ�����
			
			if (tempText19 == undefined){tempText19 = null;}
			if (tempText20 == undefined){tempText20 = null;}
			if (tempText21 == undefined){tempText21 = null;}
			if (tempText15 == null){tempText15 = "";}
			
			$mulLineTextTitleThI = $mulLineTextTitle.find("th").eq(i);
			$mulLineTextTdI = $mulLineText.find("td").eq(i);
//��һ����
			var orderStr = "<span></span>";
			if(tempText10 != null && tempText10 != ""){
				var verifyStr = "<input type=hidden name=" + strPageName + "verify" + i + " value='" + tempText10 + "'>";
				$mulLineTextTitleThI.html(tempText0+verifyStr+orderStr);//���Ɏ�����
			}else{
				$mulLineTextTitleThI.html(tempText0+orderStr);//��������
			}
			if(tempText4==3){
				$mulLineTextTitleThI.hide();
				$mulLineTextTdI.hide();//����
			} else if (tempText4 == '6') {
				$mulLineTextTdI.attr("onclick", calendar);
			}
//			$mulLineTextTitleThI.attr("class", "mulinetitlenew");
			$mulLineTextTitleThI.attr("width",function(index,OldValue){
			//�����п�,����0�ᵼ����һ���������,����""���ַ�����
				td_width = parseInt(tempText1.substr(0, tempText1.toLowerCase().indexOf("px")));//flexigrid�������Ҫ����px
				td_width = Math.round(td_width * rate);
				if(td_width == 0)
				{$mulLineTextTitleThI.hide();$mulLineTextTdI.hide();td_width="";}
				return td_width;
			});
			// ���ɵ��������¼�
		//	$mulLineTitleOrderSpan = $mulLineTextTitleThI.find("span").eq(0);
		//	$mulLineTitleOrderSpan.attr("width","20px");
		//	$mulLineTitleOrderSpan.attr("height","20px");
		//	$mulLineTitleOrderSpan.addClass("mullineorder");
		//	$mulLineTitleOrderSpan.attr("onclick",function(index,OldValue){
		$mulLineTextTitleThI.attr("onclick",function(index,OldValue){

				event_on_click = "";
				if(tObjInstance.AllowSort == true)	event_on_click = strPageName + ".orderByName('" + tempText0 + "'," + strPageName + ");"
				return event_on_click;
			});
			
			
//�ڶ�����,���������Ժ������¼�
			
			switch(tempText4) 
			{//����input_type,jquery������ֱ���޸�type,����html�ķ����޸�
				case 4:
					input_type= $mulLineTextTdI.html();
					input_type = input_type.replace(/INPUT/,"input type=\"password\" ");//input��д
				break;
				default:
					input_type= $mulLineTextTdI.html();
					input_type = input_type.replace(/INPUT/,"input type=\"text\" ");//input��д
				break;
			}
			$mulLineTextTdI.html(input_type);

			switch(i) 
			{//����name,ֱ���޸�name�ڲ�������»�ʧ��,����html�ķ����޸�
				case 0:
					tmp_input_name = "input name='" + strPageName + "No" + "'";
					input_name= $(tObjInstance.mulLineText).find("td").eq(i).html();
					input_name = input_name.toLowerCase().replace(/input/,tmp_input_name);//inputСд
				break;
				default:
	//				var eleHTML = $mulLineTextTdI.find("input").get(0);
	//				eleHTML.setAttribute("name",strPageName+i);
					tmp_input_name = "input name='" + strPageName + i + "'";
					input_name= $(tObjInstance.mulLineText).find("td").eq(i).html();
					input_name = input_name.toLowerCase().replace(/input/,tmp_input_name);//inputСд
				break;
			}
			$(tObjInstance.mulLineText).find("td").eq(i).html(input_name);//alert(td_width);
			//����div��ȣ������flexigrid��Ч�ʣ��轫��cellEffect����Ϊ"on"����������td��ȣ��ᵼ���޷��ı���
			$mulLineTextTdI.find("div").attr("style","width:"+td_width+"px");	
			$mulLineTextTdI.find("input").attr("id",function(index,oldValue){
			//����id
				if(i>0){input_id = strPageName + i + "r$row$";}else {input_id = strPageName + "No$row$";}
				return input_id;
			}).attr("maxlength",function(index,oldValue){
			//����maxlength
				max_length = tempText2;
				return	max_length;
			});
			if(tempText15 != ""){
			$mulLineTextTdI.find("input").attr("value",function(index,oldValue){
			//����Ĭ��value
				return tempText15;
			});
			}
			
			$mulLineTextTdI.attr("class",function(index,oldValue){
			//����td_class
				return	"muline";
			});
			$mulLineTextTdI.find("input").attr("class",function(index,oldValue){
			//����input_class
				switch (tempText4){
					case 0:
						input_class = "mulreadonly";
						break;
					case 2:
						input_class = "code8";
						break;
					case 6:
						input_class = "mulDatePicker";
						break;
					case 7:
						input_class = "multiCurrency";
						break;
					case 8:
						input_class = "multiDatePicker";
						break;
					default:
						input_class = "mulcommon";
						break;
				}
				return	input_class;		
			}).attr("style",function(index,oldValue){
			//����input_style
				if(tempText22 == 2 || tempText22 == 'center'){ 
					input_style = "text-align:center";
				}else if(tempText22 == 3 || tempText22 == 'right'){
					input_style = "text-align:right; padding-right:2px;";
				}else{
					input_style = "text-align:left; padding-left:2px;";
				}
				if(tempText4 == "6"){
					input_style += "width:" + (tempText1)+";";
				}else if( tempText4 == "7"){
					//input_style += "width:0px;height:0px;";
					input_style += "width:" + (td_width) + "px;";//��Ϊ�ݲ�֧��ԭ�ȶ���ַ���
				}else if( tempText4 == "8"){
				}else{
					input_style += "width:" + (td_width) + "px;";
				}
				input_style += " border:none; "
				return	input_style;	
			});
			
			var _read_only = "";
			if(tempText4 == 0){
				_read_only = "readonly";
			}else if(tempText4 == 7){
				if(tempText24 != "" && tempText24 != null && tempText24!= undefined ){
					if(tempText24 == 0 || tempText24 == false)_read_only = "readonly";
				}
			}
			if(_read_only != "") {
				$mulLineTextTdI.find("input").attr("readonly",function(index,oldValue){
				//����readonly
					return	"readonly";
				});
			}
			
		//�Զ��������
			$mulLineTextTdI.find("input").attr("CodeData",function(index,oldValue){
			//����codedata
				if (tempText5 == null && tempText6 == null && tempText7 == null && tempText8 == null && tempText9 == null){
                	if (tempText12 != null && tempText12 != "" && tempText11 != null && tempText11 != ""){
                		code_data = tempText12;
                	}//end 12 11
                }//end 56789
				return code_data;
			}).attr("amtcol",function(index,oldValue){
			//����amtcol
				amt_col = "";
				if(tempText4 == 7){
					if(tempText23 != "" && tempText23 != null && tempText23!= undefined){
						if(tempText23.substring(0,3).toUpperCase() == "COL"){
							amt_col = strPageName + tempText23.substring(3) + "r$row$" ;	
						}
					}
				}
				return amt_col;
			}).attr("moneytype",function(index,oldValue){
			//����moneytype
				money_type = "";
				if(tempText4 == 7){
					if(tempText23 != "" && tempText23 != null && tempText23!= undefined){
						if(tempText23.substring(0,3).toUpperCase() != "COL"){
							money_type = tempText23;	
						}
					}
				}
				return money_type;
			}).attr("ShowStyle",function(index,oldValue){
			//����show_style
				var show_style = "";
				if( tempText4 == "7")
				{show_style += "Style='width:" + (tempText1) + "'";}
				else if( tempText4 == "8")
				{show_style +="Style='width:" + (td_width-20) + "px" + "'";}
				return show_style;
			}).attr("ShowClass",function(index,oldValue){
			//����showclass
				show_class = "";
				if( tempText4 == "7" || tempText4 == "8"){show_class += "mulcommon";}
				return show_class;
			}).attr("verify",function(index,oldValue){
			//����input_verify
				if (tempText10 != null && tempText10 != ""){
					input_verify = tempText10;
				}else{
					input_verify = "";
				}
				return input_verify;
			});		
			/*
			 *	������������,���������¼�
			 */
			 
			if(typeof(tObjInstance.showTitle)=="function"){
			//����onmouseover����¼�
								
			}else if(tObjInstance.showTitle == 1){
				$mulLineTextTdI.find("input").attr("onmouseover",function(index,oldValue){
					event_on_mouseover="";
					//temp4��Ϊ4��7��8ʱ,��ʾtitleΪ����
					event_on_mouseover = tObjInstance.instanceName + ".setTitle(this)";
					return event_on_mouseover;
				});
			}
			   $mulLineTextTdI.find("input").attr("onclick",function(index,oldValue){
			//���ɵ�������¼�
				event_on_click = "";
				//_CalcFocusRowColNo,�����������mulline.lastFocusRowNo ��lastFocusColNo
				event_on_click = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this)";
				
				if(tempText4 == 8){
					$mulLineTextTdI.find("input").filter(".multiDatePicker").css("width",td_width-20);
					$mulLineTextTdI.find("input").filter(".multiDatePicker").after("<img src='../common/laydate/skins/default/icon.png'></img></a>");
					event_on_click ="myDate(" + tObjInstance.instanceName + ", this)";
					}
				
				return event_on_click;
			}).attr("ondblclick",function(index,oldValue){
			//����˫������¼�
				event_on_dblclick="";
				if(tempText5 == null){
					if (tempText8 != null && tempText8 != ""){
						event_on_dblclick = tempText8 + "('span$PageName$$SpanId$'," + tempText9 + ")" + ";";
					}
				}else{//tempt5!=null
                	if (tempText6 == null || tempText7 == null){//�����������ֻӦ����1����
                		if (tempText16 == null){//��������������ؼ��������ж�
                            if (tempText20 == null && tempText19 == null){
                            	event_on_dblclick = "return showCodeList('" + tempText5 + "',[this]);";
                            }else{
								if (tempText19 == null){
									event_on_dblclick = "return showCodeList('" + tempText5 + "',[this],null,null,null,null," + tempText20 + ");";
								}else{
									event_on_dblclick = "return showCodeList('" + tempText5 
										+ "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");";
								}
                            }
						}else{//temp16!=null
							if (tempText17 != null && tempText17 != ""){//������������ռ��ֵ���ж�,ȷ������
								if (tempText20 == null && tempText19 == null){
									event_on_dblclick ="return showCodeList('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" 
										+ tempText16 + "');";
								}else{
									if (tempText19 == null){
										event_on_dblclick = "return showCodeList('" + tempText5 + "',[this],null,null,'" + tempText17 
											+ "','" + tempText16 + "'," + tempText20 + ");";
									}else{
										event_on_dblclick = "return showCodeList('" + tempText5 + "',[this],null,null,'" + tempText17 
											+ "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
									}
								}
							}else{//temp17==null||""
								if (tempText18 != null && tempText18 != ""){//������������е�ֵ���ж�,ȡָ���е�ֵ
									var tempValue = "[";
									
									tempValue = tempValue + "]";
									if (tempText20 == null && tempText19 == null){
										event_on_dblclick = "return showCodeList('" + tempText5 + "',[this],null,null," + tempValue + ",'" 
											+ tempText16 + "');";
									}else{
										if (tempText19 == null){
											event_on_dblclick = "return showCodeList('" + tempText5 + "',[this],null,null," + tempValue 
												+ "','" + tempText16 + "'," + tempText20 + ");";
										}else{
											event_on_dblclick = "return showCodeList('" + tempText5 + "',[this],null,null," + tempValue 
												+ "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
										}
									}
								}//end 18
							}//end 17
						}//end 16
					}else{//else 6 7
						//�����������Ӧ���ڶ�����,�η���Ŀǰ�����в�ͨ
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
                            //��ʽ���ж������� ��0|1 ת��[�ж����ж���]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') ����ӦspanID���ж���
                                //arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                                arrColName = arrColName + "document.getElementById('$PageName$"+arrayField[n]+"r$SpanId$')";
                                if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
						arrCodeName = arrCodeName + "]";
						arrColName = arrColName + "]";
						
						if (tempText16 == null || tempText16 == ""){//��������������ؼ��������ж�
							 event_on_dblclick = "return showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,null,null," 
							 	+ tempText20 + "," + tempText19 + ");";
						}else{
							if (tempText17 != null && tempText17 != ""){
								event_on_dblclick = "return showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,'" 
									+ tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
							}else{
								if (tempText18 != null && tempText18 != ""){
									var tempValue = "[";
									arrText18 = tempText18.split(FIELDDELIMITER);
                                    for (var m = 0; m < arrText18.length; m++)
                                    {
                                        //tempValue = "document.all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrText18[m] + "').value";
                                        //tempValue = tempValue + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrText18[m] + "').value";
                                    	tempValue = tempValue + "document.getElementById('$PageName$"+arrText18[m]+"r$SpanId$').value";
                                        if (m != arrText18.length - 1)
                                        {
                                            tempValue = tempValue + ",";
                                        }
                                    }
									tempValue = tempValue + "]";
									event_on_dblclick = "return showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null," 
										+ tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
								}
							}
						}//end 16
					}//end 6 7
                }//end 5
                
                if (tempText5 == null && tempText6 == null && tempText7 == null && tempText8 == null && tempText9 == null){
                	if (tempText12 != null && tempText12 != "" && tempText11 != null && tempText11 != ""){
                		if (tempText13 == null || tempText14 == null){//ֻ��Ӧ��ǰ���д���ѡ��
                			if (tempText20 == null && tempText19 == null){
                				event_on_dblclick = "showCodeListEx('" + tempText11 + "',[this]);";
                			}else{//20!=null||19!=null
                				if (tempText19 == null){
                					event_on_dblclick = "showCodeListEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + ");";
                				}else{
                					event_on_dblclick = "showCodeListEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + ","
                						 + tempText19 + ");";
                				}
                			}
                		}else{//else 13 14 !=null
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
                            //��ʽ���ж������� ��0|1 ת��[�ж����ж���]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') ����ӦspanID���ж���
                                //arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                                arrColName = arrColName + "document.getElementById('$PageName$"+arrayField[n]+"r$SpanId$')";
                                
                                if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
							arrCodeName = arrCodeName + "]";
							arrColName = arrColName + "]";
							event_on_dblclick = "return showCodeListEx('" + tempText11 + "'," + arrColName + "," + arrCodeName + ",null,null,null,"
								 + tempText20 + "," + tempText19 + ");";
                		}//end 13 14
                	}//end 12 11
                }//end 56789
				return event_on_dblclick;
			}).attr("onchange",function(index,oldValue){
			//����change����¼�
				event_on_change = "";
				if(tempText5 == null){
					if (tempText22 != null && tempText22 != ""){
						event_on_change = tempText22 + "('span$PageName$$SpanId$'," + tempText9 + ")" + ";";
					}
				}
				return event_on_change;
			}).attr("onfocus",function(index,oldValue){
			//����onFocus����¼�
				event_on_focus = "";
				//�������mulline.lastFocusRowNo ��lastFocusColNo
				//�޸�input����
				event_on_focus = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this);this.style.width=(this.parentNode.style.width-20);";
				return event_on_focus;
			}).attr("onkeyup",function(index,oldValue){
			//����onkeyup����¼�
				event_on_keyup = "";
				if(tempText5 == null){
					if (tempText8 != null && tempText8 != ""){
						event_on_keyup += "if(event.keyCode=='13'){" + tempText8 + "('span$PageName$$SpanId$'," + tempText9 + ")" + "};";
					}
					if (tempText22 != null && tempText22 != ""){
						event_on_keyup += "if(event.keyCode=='13'){" + tempText22 + "('span$PageName$$SpanId$'," + tempText9 + ")" + "};";
					}
				}else{//tempt5!=null
                	if (tempText6 == null || tempText7 == null){//�����������ֻӦ����1����
                		if (tempText16 == null){//��������������ؼ��������ж�
                            if (tempText20 == null && tempText19 == null){
                            	event_on_keyup = "return showCodeListKey('" + tempText5 + "',[this]);";
                            }else{
								if (tempText19 == null){
									event_on_keyup = "return showCodeListKey('" + tempText5 + "',[this],null,null,null,null," + tempText20 + ");";
								}else{
									event_on_keyup = "return showCodeListKey('" + tempText5 
										+ "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");";
								}
                            }
						}else{//temp16!=null
							if (tempText17 != null && tempText17 != ""){//������������ռ��ֵ���ж�,ȷ������
								if (tempText20 == null && tempText19 == null){
									event_on_keyup ="return showCodeListKey('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" 
										+ tempText16 + "');";
								}else{
									if (tempText19 == null){
										event_on_keyup = "return showCodeListKey('" + tempText5 + "',[this],null,null,'" + tempText17 
											+ "','" + tempText16 + "'," + tempText20 + ");";
									}else{
										event_on_keyup = "return showCodeListKey('" + tempText5 + "',[this],null,null,'" + tempText17 
											+ "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
									}
								}
							}else{//temp17==null||""=
								if (tempText18 != null && tempText18 != ""){//������������е�ֵ���ж�,ȡָ���е�ֵ
									var tempValue = "[";
									
									tempValue = tempValue + "]";
									if (tempText20 == null && tempText19 == null){
										event_on_keyup = "return showCodeListKey('" + tempText5 + "',[this],null,null," + tempValue + ",'" 
											+ tempText16 + "');";
									}else{
										if (tempText19 == null){
											event_on_keyup = "return showCodeListKey('" + tempText5 + "',[this],null,null," + tempValue 
												+ "','" + tempText16 + "'," + tempText20 + ");";
										}else{
											event_on_keyup = "return showCodeListKey('" + tempText5 + "',[this],null,null," + tempValue 
												+ "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
										}
									}
								}//end 18
							}//end 17
						}//end 16
					}else{//else 6 7
						//�����������Ӧ���ڶ�����,�η���Ŀǰ�����в�ͨ
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
                            //��ʽ���ж������� ��0|1 ת��[�ж����ж���]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') ����ӦspanID���ж���
                                //arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                            	arrColName = arrColName + "document.getElementById('$PageName$"+arrayField[n]+"r$SpanId$')";
                                if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
						arrCodeName = arrCodeName + "]";
						arrColName = arrColName + "]";
						
						if (tempText16 == null || tempText16 == ""){//��������������ؼ��������ж�
							 event_on_keyup = "return showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,null,null," 
							 	+ tempText20 + "," + tempText19 + ");";
						}else{
							if (tempText17 != null && tempText17 != ""){
								event_on_keyup = "return showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,'" 
									+ tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
							}else{
								if (tempText18 != null && tempText18 != ""){
									var tempValue = "[";
									arrText18 = tempText18.split(FIELDDELIMITER);
                                    for (var m = 0; m < arrText18.length; m++)
                                    {
                                        //tempValue = tempValue + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrText18[m] + "').value";
                                        tempValue = tempValue + "document.getElementById('$PageName$"+arrText18[m]+"r$SpanId$')";
                                        if (m != arrText18.length - 1)
                                        {
                                            tempValue = tempValue + ",";
                                        }
                                    }
									tempValue = tempValue + "]";
									event_on_keyup = "return showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null," 
										+ tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");";
								}
							}
						}//end 16
					}//end 6 7
                }//end 5
                
                //���ǰ��������5,6,7,8,9���ǿ�,��ô�жϵ�10����������Ƿ����
                if (tempText5 == null && tempText6 == null && tempText7 == null && tempText8 == null && tempText9 == null){
                	if (tempText12 != null && tempText12 != "" && tempText11 != null && tempText11 != ""){
                		if (tempText13 == null || tempText14 == null){//ֻ��Ӧ��ǰ���д���ѡ��
                			if (tempText20 == null && tempText19 == null){
                				event_on_keyup = "showCodeListKeyEx('" + tempText11 + "',[this]);";
                			}else{//20!=null||19!=null
                				if (tempText19 == null){
                					event_on_keyup = "showCodeListKeyEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + ");";
                				}else{
                					event_on_keyup = "showCodeListKeyEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + ","
                						 + tempText19 + ");";
                				}
                			}
                		}else{//else 13 14 !=null
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
                            //��ʽ���ж������� ��0|1 ת��[�ж����ж���]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') ����ӦspanID���ж���
                                //arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                            	arrColName = arrColName + "document.getElementById('$PageName$"+arrayField[n]+"r$SpanId$')";
                            	if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
							arrCodeName = arrCodeName + "]";
							arrColName = arrColName + "]";
							event_on_keyup = "return showCodeListKeyEx('" + tempText11 + "'," + arrColName + "," + arrCodeName + ",null,null,null,"
								 + tempText20 + "," + tempText19 + ");";
                		}//end 13 14
                	}//end 12 11
                }//end 56789
				return event_on_keyup;
			}).attr("onblur",function(index,oldValue){
			//����onblur����¼�
				event_on_blur = "";
				if (tempText25 != null && tempText25 != ""){ 	
				    if (tempText25.indexOf("()") != -1){
				        tempText25 = tempText25.replace("()", "");
				    }
				    if(tempText26 != null && tempText26 != ""){
					    arrText26=tempText26.split(FIELDDELIMITER);
					    var tempValue = '';
						for(var m=0;m<arrText26.length;m++){
							//tempValue=tempValue+cObjInstance.formName+".all('span$PageName$$SpanId$')"+".all('"+"$PageName$"+arrText26[m]+"')";
							tempValue = tempValue + "document.getElementById('$PageName$"+arrText26[m]+"r$SpanId$')";
							if(m!=arrText26.length-1){
								tempValue=tempValue+",";
							}
						}
					    event_on_blur = tempText25+"('span$PageName$$SpanId$',"+ tempValue+");";
				    }
				    else{
				    	event_on_blur = tempText25+"('span$PageName$$SpanId$',"+ tempValue+");";
				    }
				}
				return event_on_blur;
			});
			
			if(tempText4==3){
				$mulLineTextTitleThI.hide();
				$mulLineTextTdI.hide();//����
			}
		});

//��ѡ����ѡ�����ж�,��ģ���ڲ���ǰ�˼���
		if(tObjInstance.canSel == 1){
			$mulLineTextTitle.prepend("<th class='mulinetitle' width='30'>&nbsp;</th>");

			$mulLineText.prepend("<td class='muline'><div style='width:30px'><input type=hidden name='Inp" + strPageName + "Sel' id='Inp" + strPageName
				 + "Sel$SpanId$' value=0 /><input type='radio' class='mulcommon' name='"+strPageName+"Sel' id='"+strPageName+"Sel$SpanId$' /></div></td>");
			$mulLineText.find("td").eq(0).find("input").eq(1).attr("onclick",function(index,oldValue){
			//��ѡ��ť�����¼�
				event_on_click = "";
			//_CalcFocusRowColNo,�����������mulline.lastFocusRowNo ��lastFocusColNo
				event_on_click = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this);";
				event_on_click += strPageName + ".radioClick(this," + fieldCount + ");";
				if(tObjInstance.selBoxEventFuncName != null && tObjInstance.selBoxEventFuncName != "")
					event_on_click += tObjInstance.selBoxEventFuncName + "('span$PageName$$SpanId$','"
						 + tObjInstance.selBoxEventFuncParm + "');";
				return event_on_click;
			});
		}else if(tObjInstance.canChk == 1){
			$mulLineTextTitle.prepend("<th class='mulinetitle' width='30'><input type='checkbox' name='checkAll" + strPageName
				 + "' id='checkAll"+strPageName+"' value='' class='title' /></th>");
			$mulLineTextTitle.find("th").eq(0).find("input").eq(0).attr("onclick",function(index,oldValue){
			//��ѡȫѡ��ť�����¼�
				event_on_click = "";
				//_CalcFocusRowColNo,�����������mulline.lastFocusRowNo ��lastFocusColNo
				event_on_click = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this);";
				event_on_click += strPageName + ".checkAll(this," + fieldCount + ");";
				if(tObjInstance.chkBoxAllEventFuncName != null && tObjInstance.chkBoxAllEventFuncName != "")
					event_on_click += tObjInstance.chkBoxAllEventFuncName + "(this.checked,this);";
				return event_on_click;
			});
				 
			$mulLineText.prepend("<td class='muline'><div style='width:30px'><input type='hidden'name='Inp" + strPageName + "Chk' id='Inp" + strPageName
				 + "Chk$SpanId$' value='0' /><input type='checkbox' name='"+strPageName+"Chk' id='"+strPageName+"Chk$SpanId$' /></div></td>");
			$mulLineText.find("td").eq(0).find("input").eq(1).attr("onclick",function(index,oldValue){
			//��ѡ��ť�����¼�
				event_on_click = "";
				//_CalcFocusRowColNo,�����������mulline.lastFocusRowNo ��lastFocusColNo
				event_on_click = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this);";
				event_on_click += strPageName + ".checkBoxClick(this," + fieldCount + ");";
				if(tObjInstance.chkBoxEventFuncName != null && tObjInstance.chkBoxEventFuncName != "")
					event_on_click += tObjInstance.chkBoxEventFuncName + "('span$PageName$$SpanId$','"
						 + tObjInstance.chkBoxEventFuncParm + "');";
				return event_on_click;
			});
		}else{
			$mulLineTextTitle.prepend("<th style='display:none'></th>");
			$mulLineText.prepend("<td style='display:none'><input type='' name='dd' /></td>");
		}
//�Ƿ����ɼ�һ�а�ť
		if(tObjInstance.hiddenSubtraction == 0){//������
			tStatus = "" ;
			if(tObjInstance.locked == 1)tStatus = "disabled" ;
			$mulLineTextTitle.append("<th class='mulinetitle' disabled width='23'>&nbsp; - &nbsp;</th>");
			
			$mulLineText.append("<td class='muline'><input class=button type=button " + tStatus + " value='  -  ' name='$PageName$Del' "
				+ "id='$PageName$Del$row$' />" + "<input type='hidden' name='$PageName$SpanID' value='span$PageName$$SpanId$' /></td>");
			$mulLineText.find("td").last().find("input").eq(0).attr("onclick",function(index,oldValue){
			//��ѡ��ť�����¼�
				event_on_click = "";
				event_on_click = "return ";
				//ɾ��֮ǰ�����¼�
				if(tObjInstance.delEventFuncName != null && tObjInstance.delEventFuncName != "")
					event_on_click += tObjInstance.delEventFuncName + "('span$PageName$$SpanId$','"
						 + tObjInstance.delEventFuncParm + "');";
				event_on_click += strPageName + ".deleteOne(\"$PageName$\",'span$PageName$$SpanId$');";
				return event_on_click;
			});
		}else{
		}	
//Ϊ���������Ч��,���������ɵ�mullineText��mulLineTextTitleת�����ַ���
		tObjInstance.mulLineTextTitle = tObjInstance.mulLineTextTitle.outerHTML;
		tObjInstance.mulLineText = tObjInstance.mulLineText.outerHTML;
	};
/**********************************************************************************************************************************/
/****************************************����Ϊ����table����Ҫ����������Ϊʵ������ϸ�ڹ��ܵĺ���*********************************************/	
/**********************************************************************************************************************************/
/**
 *	����ָ�����е�����(�ⲿ/�ڲ�����)
 *	@param	:	cRow:  ��(��0��ʼ)
 *	@param	:	cCol:  ��
 *	@param	:	cData: ����
 *	@param	:	cObjInstance Muline����,�ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *					��������һ�����ⲿ����,��cObjInstanceΪ��,�ڲ�tObjInstance=this;
 *					������Ӻ���ʱ,����ǰ����this��ΪcObjInstance������������Ӻ���
 *	@return ��	û��
 *	
 *	@logic	: 	��������к��н��ж�λ����ֵ
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����initTable
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/
	$.fn.SetRowColData = function(cRow, cCol, cData, cObjInstance){
		var tReturn = false;
		var tObjInstance,tStr;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cData == null){cData = "";}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->setRowColData() ʱָ���˴������:" + cRow}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "��MulLine.js-->setRowColData() ʱָ���˴������:" + cCol;}
		try{
			var newData = cData;//Ӧ�ü�һ���ַ����ж�
			newData = cData.replace("\r\n", "");
			newData = cData.replace("'", "\\'");
			document.getElementsByName(tObjInstance.instanceName+cCol)[cRow].value = newData;
			$("#" + tObjInstance.instanceName + cCol + "r" + cRow,tObjInstance.initTable).val(newData);
			tReturn = true;
		}catch(ex){
			alert("�� MulLine.js --> SetRowColData �����з����쳣��" +  ex + "\n" +tStr);
		}
		return tReturn;
	};
/**
 *	���ָ�����е�����(�ⲿ/�ڲ�����)
 *	@param��		cRow:  ��
 *				cCol:  ��
 *				cObjInstance Muline����,�ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *				��������һ�����ⲿ����,��cObjInstanceΪ��,�ڲ�tObjInstance=this;
 *				������Ӻ���ʱ,����ǰ����this��ΪcObjInstance������������Ӻ���
 *	@return��		string:ָ������ֵ
 *	
 *	@logic	:	 ��������к��н��ж�λ��ȡֵ
 *	@global	:	 Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����instanceName,mulLineCount
 **/
	$.fn.GetRowColData = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cCol;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).val();
		}catch(ex){
			alert("�� MulLine.js --> GetRowColData �����з����쳣��" +  ex + "\n");
		}
		return tReturn;
	};
	
//==============================================================================

/**
 * �������õ�ָ�����е�Element����
 * ���룺cRow:  ��  ��0��ʼ
 *       cCol:  ��  ��1��ʼ
 *       cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *       ��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;
 *       ������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���
 * �����ָ���У��е�Element����
 */
	$.fn.GetRowColElemt = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cCol;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow);
		}catch(ex){
			alert("�� MulLine.js --> GetRowColData �����з����쳣��" +  ex + "\n");
		}
		return tReturn;
	}

/**
 * ������ӦĳЩ�������Ҫ��ͬʱΪ�˸�������������棬���ӷ�����ȡĳһ���е�classname
 * ���룺cRow:  ��  ��0��ʼ
 *       cCol:  ��  ��1��ʼ
 *       cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *       ��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;
 *       ������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���
 * �����ָ���У��е�Element����
 */
	$.fn.GetRowColClass = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cCol;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).parent().parent().className;
		}catch(ex){
			alert("�� MulLine.js --> GetRowColData �����з����쳣��" +  ex + "\n");
		}
		return tReturn;
	}

/**
 * ������ӦĳЩ�������Ҫ��ͬʱΪ�˸�������������棬���ӷ�����ȡĳһ���е�classname
 * ���룺cRow:  ��  ��0��ʼ
 *       cCol:  ��  ��1��ʼ
 *       cObjInstance Muline�����ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *       ��������һ�����ⲿ���ã���cObjInstanceΪ�գ��ڲ�tObjInstance=this;
 *       ������Ӻ���ʱ������ǰ����this��ΪcObjInstance������������Ӻ���
 * �����ָ���У��е�Element����
 */
	$.fn.SetRowColClass = function(cRow, cCol, cData, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "��MulLine.js-->getRowColData() ʱָ���˴������:" + cCol;}
		try{
			var newData = replace(cData, "\r\n", "");
        // 2006-02-16 Kevin
        // calling replace(cData, "\\", "\\\\") results in IE hangs
        // so we have to replace "\\" with "\\\\" by following method
	        if(chkzh(newData)==false){
	            cData = newData;
	            newData = "";
	            var vIndex = 0;
	            var vSubStr = "";
	            for(vIndex = 0; vIndex < cData.length; vIndex++){
	                vSubStr = cData.substring(vIndex, vIndex + 1);
	                if (vSubStr == "\\"){newData += "\\\\";}else{newData += vSubStr;}
	            }
	        }
//	        if (cRow == 3) {
//	        	alert($("#" + tObjInstance.instanceName + cCol + "r" + cRow).parent().parent().html());
//	        }
	        
	        tReturn = $("#" + tObjInstance.instanceName + "Sel" + cRow).parent().parent()
        					.removeClass().addClass(newData);
	        tReturn = $("#" + tObjInstance.instanceName + "No" + cRow).parent().parent()
        					.removeClass().addClass(newData);
        	tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).parent().parent()
        					.removeClass().addClass(newData);
//        	tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).parent().parent().css({
//        		'background':'#ffb900'
//        	});
		}catch(ex){
			alert("�� MulLine.js --> GetRowColData �����з����쳣��" +  ex + "\n");
		}
		return tReturn;
	}

//==============================================================================

	
/**
 *	���ָ���е�����(�ⲿ/�ڲ�����)
 *	@param	��	cRow:  ��
 *	@param	��	cObjInstance Muline����,�ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *				��������һ�����ⲿ����,��cObjInstanceΪ��,�ڲ�tObjInstance=this;
 *				������Ӻ���ʱ,����ǰ����this��ΪcObjInstance������������Ӻ���
 *	@return	��	array:ָ����ֵ������
 *	
 *	@logic	: 	��������н��ж�λtr��ѭ��ȡ����id������inputֵ
 *	@global	:	 Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����instanceName,�Լ�tr��id="'span'+instanceName+cRow"��input��id="instanceName+cCol+'r'+cRow"
 **/
	$.fn.GetRowData = function(cRow, cObjInstance){
		var tReturn = new Array();
		var tStrInput,n=0,tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->GetRowData() ʱָ���˴������:" + cRow;}
		try{
			var id = "#" + tObjInstance.instanceName;id2 = "r" + cRow;
			for(var cCol = 1, size = tObjInstance.colCount;cCol < size;cCol++){
				tStrInput = id + cCol + id2;
				tReturn[n++] = $(tStrInput).val();
			}
		}catch(ex)
		{
			alert("�� MulLine.js --> GetRowData �����з����쳣��" +  ex + "\n" +tStr);
		}
		return tReturn;
	};
/**
 *	�������(�ⲿ/�ڲ�����)
 *	@param	��	strPageName:  ��������
 *	@param	��	cObjInstance Muline����,�ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *				��������һ�����ⲿ����,��cObjInstanceΪ��,�ڲ�tObjInstance=this;
 *				������Ӻ���ʱ,����ǰ����this��ΪcObjInstance������������Ӻ���
 *	@return	��	
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����initTable
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/
	$.fn.ClearData = function(strPageName, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var t_StrPageName = strPageName || tObjInstance.instanceName;
		try{
			tObjInstance.mulLineCount = 0;
			$("tbody tr",tObjInstance.initTable).remove();
			if(tObjInstance.checkFlag == 1 && tObjInstance.canChk == 1){
				tObjInstance.checkFlag = 0;
				var chkAllName ="checkAll" + this.instanceName;
				var chkAll = document.getElementsByName(chkAllName)[1];
				chkAll.value = 0;
				chkAll.checked = false;
			}
			$(this).NewTableFlexiGrid(tObjInstance);
		}catch(ex){
			alert("�� MulLine.js --> clearData �����з����쳣��" +  ex);
		}
	};
/**
 *	ָ�����еĻ�ý���(�ⲿ/�ڲ�����)
 *	@param	:	cRow:  ��
 *	@param	:	cCol:  ��,Ĭ��Ϊ1
 *	@param	:	cObjInstance Muline����,�ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *				��������һ�����ⲿ����,��cObjInstanceΪ��,�ڲ�tObjInstance=this;
 *				������Ӻ���ʱ,����ǰ����this��ΪcObjInstance������������Ӻ���
 *	@return ��	boolean
 *	
 *	@logic	: 	��������к��н��ж�λ
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.SetFocus = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance,tStr;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){alert("��MulLine.js-->setRowColData() ʱָ���˴������:" + cRow);return tReturn;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){
			throw "��MulLine.js-->setRowColData() ʱָ���˴������:" + cCol;
		}else if(cCol == "" || cCol ==  null){
			cCol = 1;//Ĭ��Ϊ1
		}
		try{
			tStr = "document.getElementsByName('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].focus();";
			eval(tStr);
			tReturn = true;
		}catch(ex){
			alert("�� MulLine.js --> GetRowData �����з����쳣��" +  ex + "\n" +tStr);
		}
		return tReturn;
	};
/**
 *	�ж�ָ�����е��Ƿ��ý���(�ⲿ/�ڲ�����)
 *	@param	:	cRow:  ��,��0��ʼ
 *	@param	:	cCol:  ��,Ĭ��Ϊ1
 *	@param	:	cObjInstance Muline����,�ⲿ����ʱΪ�գ��ڲ�����ʱ����Ϊ��
 *				��������һ�����ⲿ����,��cObjInstanceΪ��,�ڲ�tObjInstance=this;
 *				������Ӻ���ʱ,����ǰ����this��ΪcObjInstance������������Ӻ���
 *	@return ��	boolean
 *	
 *	@logic	: 	��������к����ж�lastFocusRowNo��lastFocusColNo�Ƿ��Ǹ�����
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.GetFocus = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance,tStr;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){alert("��MulLine.js-->setRowColData() ʱָ���˴������:" + cRow);return tReturn;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){
			throw "��MulLine.js-->setRowColData() ʱָ���˴������:" + cCol;
		}else if(cCol == "" || cCol ==  null){
			cCol = 1;//Ĭ��Ϊ1
		}
		try{
			if(tObjInstance.lastFocusRowNo == cRow && tObjInstance.lastFocusColNo == cCol){
				tReturn = true;
			}
		}catch(ex){
			alert("�� MulLine.js --> _GetFocus �����з����쳣��" + ex);
		}
		return tReturn;
	};
/**
 *	��ѡ��ť�����¼�(�ⲿ/�ڲ�����)
 *	@param	:	cObjSel:  radio����
 *	@param	:	colcount:  ����
 *	@return ��	none
 *	
 *	@logic	: 	�޸ı�ѡ�еĵ�ѡ�к�
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.RadioClick = function(cObjSel, colcount){
		var tStrPageName = this.instanceName;
		var ele = document.getElementById("span" + tStrPageName);
		var oldPageNo = ele.getAttribute("PageNoRadio");
		var pageNo;
		var selName = "#Inp" + tStrPageName + "Sel";
		var iMax = this.mulLineCount;
		var filedCount = colcount;
		try{
			for(i = 0; i < iMax; i++){
				$(selName+i).val(0);//����
				if ($("#"+tStrPageName+"Sel"+i).attr("checked")){
					pageNo = i;
					$(selName+i).val(1);//���ñ�ѡ�нڵ�ֵ
				}
        	}
	        if (pageNo != null && pageNo != undefined){
				ele.setAttribute("PageNoRadio",pageNo);//���ô˴α�ѡ����
			}
			//�޸���ʽ
			if((oldPageNo!=undefined&&oldPageNo!=null&&oldPageNo!="")||oldPageNo=="0"){
				//ԭ��ѡ��������ʽ
				$("#"+tStrPageName +"Sel" + oldPageNo).removeClass().addClass("mulreadonlyt");
				$("#"+tStrPageName +"No" + oldPageNo).removeClass().addClass("mulreadonlyt");
				for(j=1;j<fieldCount;j++){
					$(this).ChangeSelStyle(this.formName,tStrPageName,j,oldPageNo,this,"cancel");
				}
				$("#span"+ tStrPageName + oldPageNo).removeClass("trSelected");
			}
			//�ֱ�ѡ��������ʽ
			$("#"+tStrPageName +"Sel" + pageNo).removeClass().addClass("mulnotreadonlyt");
			$("#"+tStrPageName +"No" + pageNo).removeClass().addClass("mulnotreadonlyt");
			$("#span"+ tStrPageName + pageNo).addClass("trSelected");
			for(j=1;j<fieldCount;j++){
				$(this).ChangeSelStyle(this.formName,tStrPageName,j,pageNo,this,"select");
			}
		}catch(ex){
			alert("�� MulLine.js --> radioClick �����з����쳣��" +  ex);
		}
	};
/**
 *	��ѡ��ť�����¼�(�ⲿ/�ڲ�����)
 *	@param	:	cObjSel:  CheckBox����
 *	@param	:	colcount:  ����
 *	@return ��	none
 *	
 *	@logic	: 	�޸ı�ѡ�еĶ�ѡ�к�
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.CheckBoxClick = function(cObjChk, colcount, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var pageNo;
		var selName = "Inp" + this.instanceName + "Chk";
		var iMax = tObjInstance.mulLineCount;
		var fieldCount = colcount;
		try{
			var tChkId = $(cObjChk).attr("id");
			var tGridChkId = tObjInstance.instanceName + "Chk";
			tChkId = tChkId.substring(tGridChkId.length);//ȡ��ѡ�������
			if(cObjChk.checked==false){
				$("#Inp"+tGridChkId+tChkId).val(0);//����checkbox���ֵܽڵ�ֵ
				$("#"+tObjInstance.instanceName + "Chk"+tChkId).removeClass().addClass("mulreadonlyt");
				$("#"+tObjInstance.instanceName + "No"+tChkId).removeClass().addClass("mulreadonlyt");
				for(j=1;j<fieldCount;j++){
					$().ChangeSelStyle(tObjInstance.formName,tObjInstance.instanceName,j,tChkId,tObjInstance,"cancel");
				}
				$("#span"+ tObjInstance.instanceName +tChkId).removeClass("trSelected");
			}else if(cObjChk.checked==true){
				$("#Inp"+tGridChkId+tChkId).val(1);//����checkbox���ֵܽڵ�ֵ
				$("#"+tObjInstance.instanceName + "Chk"+tChkId).removeClass().addClass("mulnotreadonlyt");
				$("#"+tObjInstance.instanceName + "No"+tChkId).removeClass().addClass("mulnotreadonlyt");
				for(j=1;j<fieldCount;j++){
					$().ChangeSelStyle(tObjInstance.formName,tObjInstance.instanceName,j,tChkId,tObjInstance,"select");
				}
				$("#span"+ tObjInstance.instanceName +tChkId).addClass("trSelected");
			}
		}catch(ex){
			alert("�� MulLine.js --> checkBoxClick �����з����쳣��" +  ex);
		}
	};
/**
 *	��ñ�ѡ����к�(�ⲿ/�ڲ�����)
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	int:	�кţ���1��ʼ��0Ϊû�б�ѡ����
 *	
 *	@logic	: 	����
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.GetSelNo = function(cObjInstance){
		var tObjInstance;
		var tReturn = 0;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var selName = tObjInstance.instanceName + "Sel";
		var iMax = tObjInstance.mulLineCount;
		try{
			if(tObjInstance.mulLineCount > 0 && tObjInstance.canSel == 1){
				for(i=0;i<iMax;i++){
					if(document.getElementsByName(selName)[i].checked == true){
						tReturn = i + 1;break;
					}
				}
			}
			return tReturn;
		}catch(ex){
			alert("�� MulLine.js --> getSelNo �����з����쳣��" +  ex);
		}
	};
/**
 *	�ж�ָ�����Ƿ�ѡ��(�ⲿ/�ڲ�����)
 *	@param	:	cIndex:  �кŴ�0��ʼ
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	boolean
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.GetChkNo = function(cIndex, cObjInstance){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canChk != 1) {throw "no checkBox!";}
			if(cIndex < 0 || cIndex >= tObjInstance.mulLineCount){throw "��MulLine.js-->getChkNo������ָ���˴������:" + cIndex;}
			if(tObjInstance.mulLineCount > 0 && cIndex < tObjInstance.mulLineCount){
				if(document.getElementsByName(tObjInstance.instanceName + "Chk")[cIndex].checked == true){
					tReturn = true;
				}
			}
		}catch(ex){
			alert("��MulLine.js-->getChkNo�����з����쳣:" + ex);
		}
		return tReturn;
	};
/**
 *	��ñ�ѡ����к�(�ⲿ/�ڲ�����)
 *	@param	:	cObjCheckBox:  checkbox����
 *	@return ��	int:	����
 *	
 *	@logic	: 	����
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.CheckAll = function(cObjCheckBox, fieldCount){
		var tReturn = false;

		var chkAllName ="checkAll" + this.instanceName;
		var spanId = "span" + this.instanceName;
		var iMax = this.mulLineCount;
		try{
			if(this.canChk != 1) {throw "no checkBox!";}
			if(document.getElementsByName(chkAllName)[1].checked == true){
				this.checkBoxAll(this, fieldCount);
				this.checkFlag = 1;
			}else{
				this.checkBoxAllNot(this, fieldCount);
				this.checkFlag = 0;
			}
		}catch(ex){
			alert("��MulLine.js-->checkAll�����з����쳣:" + ex);
		}
	};
/**
 *	��ñ�ѡ����к�(�ⲿ/�ڲ�����)
 *	@param	:	cObjInstance:  mulline����
 *	@return ��	int:	����
 *	
 *	@logic	: 	����
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.CheckBoxAll = function(cObjInstance, fieldCount){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var chkAllName ="checkAll" + tObjInstance.instanceName;
		var chkName = tObjInstance.instanceName + "Chk";
		var chkInputName = "Inp" + chkName;
		var iMax = tObjInstance.mulLineCount;
		try{
			if(tObjInstance.canChk != 1) {alert("no checkBox!");return tReturn;}
			document.getElementsByName(chkAllName)[1].value = 1;
			document.getElementsByName(chkAllName)[1].checked = true;
			for(i=0;i<iMax;i++){
				document.getElementsByName(chkInputName)[i].value = 1;
				var cObjChk = document.getElementsByName(chkName)[i];
				cObjChk.checked = true;
				$(this).CheckBoxClick(cObjChk,fieldCount,tObjInstance);
			}
		}catch(ex){
			alert("��MulLine.js-->checkBoxAll�����з����쳣:"+ ex);
		}
	};
/**
 *	��ñ�ѡ����к�(�ⲿ/�ڲ�����)
 *	@param	:	cObjInstance:  mulline����
 *	@return ��	int:	����
 *	
 *	@logic	: 	����
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.CheckBoxAllNot = function(cObjInstance, fieldCount){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var chkAllName ="checkAll" + tObjInstance.instanceName;
		var chkName = tObjInstance.instanceName + "Chk";
		var chkInputName = "Inp" + chkName;
		var iMax = tObjInstance.mulLineCount;
		try{
			if(tObjInstance.canChk != 1) {alert("no checkBox!");return tReturn;}
			document.getElementsByName(chkAllName)[1].value = 0;
			document.getElementsByName(chkAllName)[1].checked = false;
			for(i=0;i<iMax;i++){
				document.getElementsByName(chkInputName)[i].value = 0;
				var cObjChk = document.getElementsByName(chkName)[i];
				cObjChk.checked = false;
				$(this).CheckBoxClick(cObjChk,fieldCount,tObjInstance);
			}
		}catch(ex){
			alert("��MulLine.js-->checkBoxAllNot�����з����쳣:" + ex);
		}
	};
/**
 *	ѡ��ָ����(�ⲿ/�ڲ�����)
 *	@param	:	cIndex:  �к�,��1�п�ʼ�������������ͬ��
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	boolean
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.CheckBoxSel = function(cIndex, cObjInstance){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canChk != 1) {alert("no checkBox!");return tReturn;}
			if(cIndex <= 0 || cIndex > tObjInstance.mulLineCount){alert("��MulLine.js-->getChkNo������ָ���˴������:" + cIndex);return tReturn;}
			if(tObjInstance.mulLineCount > 0 && cIndex <= tObjInstance.mulLineCount){
				document.getElementsByName(tObjInstance.instanceName + "Chk")[cIndex-1].checked = true;
				document.getElementsByName("Inp" + tObjInstance.instanceName + "Chk")[cIndex-1].value = 1;
				tReturn = true;
			}
		}catch(ex){
			alert("�� MulLine.js --> checkBoxSel �����з����쳣��" +  ex);
		}
		return tReturn;
	};
/**
 *	ȡ��ѡ��ָ����(�ⲿ/�ڲ�����)
 *	@param	:	cIndex:  �к�,��1�п�ʼ�������������ͬ��
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	boolean
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.CheckBoxNotSel = function(cIndex, cObjInstance){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canChk != 1) throw "boxErr";
			if(cIndex <= 0 || cIndex > tObjInstance.mulLineCount)throw "rowErr";
			if(tObjInstance.mulLineCount > 0 && cIndex <= tObjInstance.mulLineCount){
				document.getElementsByName(tObjInstance.instanceName + "Chk")[cIndex-1].checked = false;
				document.getElementsByName("Inp" + tObjInstance.instanceName + "Chk")[cIndex-1].value = 0;
				tReturn = true;
			}
		}catch(ex){
			switch (ex){
				case "no checkBox":alert("no checkBox!");break;
				case "rowErr":alert("��MulLine.js-->checkBoxNotSel������ָ���˴������:");break;
				default:alert("�� MulLine.js --> checkBoxNotSel �����з����쳣��" +  ex);break;
			}
		}
		return tReturn;
	};
/**
 *	ѡ��������(�ⲿ/�ڲ�����)
 *	@param	:	string:	����
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	boolean
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.OrderByName = function(colName, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var sortTurnPage = tObjInstance.SortPage;
		try{
			if(sortTurnPage == null || sortTurnPage == "") throw "init";
			for(i=1;i<tObjInstance.arraySaveOra.length;i++){
				if(colName == tObjInstance.arraySaveOra[i][0]){
					sortTurnPage.allowsort(i);
					tObjInstance.setPageMark(sortTurnPage);
					break;}
			}
		}catch(ex){
			switch (ex){
				case "init":alert("���Ȳ�ѯ��");break;
				default:alert("�� MulLine.js --> OrderByName �����з����쳣��" +  ex);break;
			}
		}
		
		//afterOrderByName(colName, cObjInstance);
	};
/**
 *	�����л�ý���(�ⲿ/�ڲ�����)
 *	@param	:	int:	�к�
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	boolean
 *	
 *	@logic	: 	���������кţ�����setfocus
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����mulLineCount
 **/
	$.fn.MoveFocus = function(col, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			cRow = tObjInstance.mulLineCount - 1;
			if(col == null || col ==""){cCol = 1}else{cCol = col}
			tObjInstance.setFocus(cRow,cCol,tObjInstance);
		}catch(ex){
			switch (ex){
				default:alert("�� MulLine.js --> MoveFocus �����з����쳣��" +  ex);break;
			}
		}
	};
/**
 *	�޸ı�ѡ�е����е���ʽ(�ڲ�����)
 *	@param	:	formName:	����
 *	@param	:	instanceName:	ʵ����
 *	@param	:	col:	�к�
 *	@param	:	pageNo:	�к�
 *	@param	:	cObjInstance:  mulLine����
 *	@param	:	action:  ������ʽ��select��cancel
 *	@return ��	none
 *	
 *	@logic	: 	����action�޸�ָ�������ʽ
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����instanceName
 **/
	$.fn.ChangeSelStyle = function(formName,instanceName,col,pageNo,cObjInstance,action){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var tInstanceName = instanceName||tObjInstance.instanceName;
		try{
			var tColId = "#" + tInstanceName + col + "r" + pageNo;
			if(action ==  "select"){
					var tNewClass;
					var tOldeClass = $(tColId).attr("class");
					if(tOldeClass=="code8"||tOldeClass=="codeselect"){
						tNewClass = "codeselect";
					}else if(tOldeClass=="multiDatePicker"||tOldeClass=="multiCurrency"){
						tColId = "#Show" + tInstanceName + col + "r" + pageNo;
						tNewClass = "mulnotreadonlyt";
					}else{
						tNewClass = "mulnotreadonlyt";
					}
					$(tColId).removeClass().addClass(tNewClass);
			}else if(action == "cancel"){
					var tNewClass;
					var tOldeClass = $(tColId).attr("class");
					if(tOldeClass=="code8"||tOldeClass=="codeselect"){
						tNewClass = "code8";
					}else if(tOldeClass=="multiDatePicker"||tOldeClass=="multiCurrency"){
						
						tColId = "#Show" + tInstanceName + col + "r" + pageNo;
	                    //event_on_click = laydate({istime: true});
						tNewClass = "mulreadonlyt";
					}else{
						tNewClass = "mulreadonlyt";
					}
					$(tColId).removeClass().addClass(tNewClass);
			}else throw "Error action~";
		}catch(ex){
			switch (ex){
				default:alert("�� MulLine.js --> ChangeSelStyle �����з����쳣��"+ex);break;
			}
		}
	};
/**
 *	����ɾ���������еĹ���(�ⲿ����)
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	none
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.Lock = function(cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var tInstanceName = tObjInstance.instanceName;
		if(tObjInstance.locked ==0){
			try{
				tObjInstance.locked = 1;
				$("#"+tInstanceName+"addOne").attr("disabled","true");
				tObjInstance.mulLineText = replace(tObjInstance.mulLineText, "type=button value='  -  '", "type=button disabled value='  -  '");
				for(i=0;i<tObjInstance.mulLineCount;i++){
					$("#"+tInstanceName+"Del"+i).attr("disabled","true");
				}
			}catch(ex){
				switch (ex){
					default:alert("�� MulLine.js --> Lock �����з����쳣��" +  ex);break;
				}
			}
		}
	};
/**
 *	�������ɾ���������еĹ���(�ⲿ����)
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	none
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineText
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.Unlock = function(cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var tInstanceName = tObjInstance.instanceName;
		if(tObjInstance.locked ==1){
			try{
				tObjInstance.locked = 0;
				$("#"+tInstanceName+"addOne").attr("disabled","false");
				tObjInstance.mulLineText = replace(tObjInstance.mulLineText, "type=button disabled value='  -  '", "type=button disabled value='  -  '");
				for(i=0;i<tObjInstance.mulLineCount;i++){
					$("#"+tInstanceName+"Del"+i).attr("disabled","false");
				}
			}catch(ex){
				switch (ex){
					default:alert("�� MulLine.js --> Unlock �����з����쳣��"+ex);break;
				}
			}
		}
	};
/**
 *	���ر�ѡ�и�ѡ����Ŀ(�ⲿ����)
 *	@param	:	cObjInstance:  mulLine����
 *	@return ��	nSelectedCount�� ��ѡ����Ŀ
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.GetChkCount = function(cObjInstance){
	   var tObjInstance;
	   if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var nSelectedCount = 0;
		try{
			for (var i = 0; i < tObjInstance.mulLineCount; i++){
				if (tObjInstance.getChkNo(i))nSelectedCount += 1;
			}
		}
		catch (ex){
		    switch (ex){
				default:alert("�� MulLine.js --> GetChkCount �����з����쳣��"+ex);break;
			}
		}
		return nSelectedCount;
	}
/**
 *	ʹָ���� radioBox ���ѡ�е�״̬(�ⲿ����)
 *	@param	��	nRowNumber��	ѡ���кţ���1��ʼ��
 *	@param	��	cObjInstance:  mulLine����
 *	@return	��	boolean�� ѡ�н��
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.SelOneRow = function(nRowNumber, cObjInstance){
		try{
			var tObjInstance;
			if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
			if (tObjInstance.canSel != 1){
				throw "�� MulLine.js --> SelOneRow �����з�������" + tObjInstance.instanceName + " ������ѡ�� ";
			}
        	if (nRowNumber == null || nRowNumber <= 0 || tObjInstance.mulLineCount <= 0 || nRowNumber > tObjInstance.mulLineCount){
            	throw "�� MulLine.js --> SelOneRow ������ָ���˴�����У�" + nRowNumber + " ";
        	}else{
            	document.getElementsByName(tObjInstance.instanceName + "Sel")[nRowNumber - 1].click();
	        }
        	return true;
    	}
		catch (ex){
			switch (ex){
				default:alert("�� MulLine.js --> SelOneRow �����з����쳣��"+ex);break;
			}
			return false;
		}
	}
/**
 *	ʹָ���� checkBox ���ѡ�е�״̬���߲�ѡ��״̬(�ⲿ����)
 *	@param	��	nRowNumber��	ѡ���кţ���1��ʼ��
 *	@param	��	cObjInstance:  mulLine����
 *	@return	��	boolean�� ѡ�н��
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.ChkOneRow = function(nRowNumber, cObjInstance){
		try{
			var tObjInstance;
			if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
	        if (tObjInstance.canChk != 1){
             throw "�� MulLine.js --> ChkOneRow �����з�������" + tObjInstance.instanceName + " ������ѡ�� ";
        	}
	        if (nRowNumber == null || nRowNumber <= 0 || tObjInstance.mulLineCount <= 0 || nRowNumber > tObjInstance.mulLineCount){
            	throw "�� MulLine.js --> ChkOneRow ������ָ���˴�����У�" + nRowNumber + " ";
	        }else{
				document.getElementsByName(tObjInstance.instanceName + "Chk")[nRowNumber - 1].click();
			}
			return true;
		}
		catch (ex){
			switch (ex){
				default:alert("�� MulLine.js --> ChkOneRow �����з����쳣��"+ex);break;
			}
			return false;
		}
	}
/**
 *	����������ݸ�ʽ���е���(�ڲ�����)
 *	@param	��	cData��	��������
 *	@return	��	cNewData��	�������
 *	
 *	@logic	: 	���ֱ���
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.ChangeDataToNew = function (cData){
		// 2006-02-16 Kevin
		// calling replace(cData, "\\", "\\\\") results in IE hangs
		// so we have to replace "\\" with "\\\\" by following method
		var vIndex=0;
		var vSubStr="";
		var cNewData = "";
		var tArrLength=cData.length;
		for(vIndex=0; vIndex < tArrLength; vIndex++) {
			vSubStr=cData.substring(vIndex, vIndex + 1);
			if( vSubStr == "\\" )
				cNewData+="\\\\";
			else
				cNewData+=vSubStr;
		}
		return cNewData;
	}
/**
 *	��ָ����ֵ��ͬʱ�ı���ʽ(�ⲿ����)
 *	@param	��	cRow��	�кţ���1��ʼ��
 *	@param	��	cCol��	�кţ���1��ʼ��
 *	@param	��	cData��	���ݣ��������
 *	@param	��	cObjInstance:  mulLine����
 *	@param	��	cCode��	��Ԫ����ʽ
 *	@param	��	cWidth��	��Ԫ���
 *	@return	��	boolean�� �ı���
 *	
 *	@logic	: 	��SetRowColDataCustomize1��ͬ����readonly���˷�������readonly
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.SetRowColDataCustomize = function(cRow, cCol, cData, cObjInstance, cCode, cWidth){
		var tReturn=false;	var tObjInstance;         //����ָ��
		if (cObjInstance==null)	tObjInstance=this;else	tObjInstance=cObjInstance;
		if(cData==null) cData="";
		if(cRow<0||cRow>=tObjInstance.mulLineCount)	{
			alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cRow);
			return tReturn;
		}
		if(cCol<0||cCol>=tObjInstance.colCount)	{
			alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cCol);
			return tReturn;
		}
		try	{
			cData+="";
			var newData=cData.replace("\r\n","");

			if( chkzh(newData) == false ){
				cData=newData;
				newData= $().ChangeDataToNew(cData);
			}
			var tColId = "#" + tObjInstance.instanceName + cCol + "r" + cRow;
			var $ele = $(tColId);
			$ele.attr("value",cData).attr("class",function(index, oldValue){
				if(cCode != null) oldValue="common";
				return oldValue;
			}).attr("readonly",function(index, oldValue){
				if(cCode != null && cCode!="") oldValue = "true";
				return oldValue;
			});
			
			var ele = $ele.get(0);  
			if(cCode != null && cCode != ""){
				if(cCode == "readonly"){
					ele.ondblclick=null;
					ele.onkeyup=null;
				}else{
					ele.ondblclick = function(){
			            showCodeList(cCode,[this],null,null,null,null,null,null);
			        }
					ele.onkeyup=function(){
						showCodeListKey(cCode,[this],null,null,null,null,null,null);
					} 
				}
			}
			tReturn=true;
			return tReturn;
		}
		catch(ex)
		{
			switch (ex){
				default:alert("�� MulLine.js-->_SetRowColDataCustomize �����з����쳣��"+ex);break;
			}
			return false;
		}
	}
/**
 *	��ָ����ֵ��ͬʱ�ı���ʽ(�ⲿ����)
 *	@param	��	cRow��	�кţ���1��ʼ��
 *	@param	��	cCol��	�кţ���1��ʼ��
 *	@param	��	cData��	���ݣ��������
 *	@param	��	cObjInstance:  mulLine����
 *	@param	��	cCode��	��Ԫ����ʽ
 *	@param	��	cWidth��	��Ԫ���
 *	@return	��	boolean�� �ı���
 *	
 *	@logic	: 	��SetRowColDataCustomize��ͬ����readonly���˷���������readonly
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.SetRowColDataCustomize1 = function(cRow, cCol, cData, cObjInstance, cCode, cWidth){
		var tReturn=false;	var tObjInstance;         //����ָ��
		if (cObjInstance==null)	tObjInstance=this;else	tObjInstance=cObjInstance;
		if(cData==null) cData="";
		if(cRow<0||cRow>=tObjInstance.mulLineCount)	{
			alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cRow);
			return tReturn;
		}
		if(cCol<0||cCol>=tObjInstance.colCount)	{
			alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cCol);
			return tReturn;
		}
		try	{
			cData+="";
			var newData=cData.replace("\r\n","");

			if( chkzh(newData) == false ){
				cData=newData;
				newData= $().ChangeDataToNew(cData);
			}
			var tColId = "#" + tObjInstance.instanceName + cCol + "r" + cRow;
			var $ele = $(tColId);
			$ele.attr("value",cData).attr("class",function(index, oldValue){
				if(cCode != null) oldValue="common";
				return oldValue;
			});
			
			var ele = $ele.get(0);  
			if(cCode != null && cCode != ""){
				if(cCode == "readonly"){
					ele.ondblclick=null;
					ele.onkeyup=null;
				}else{
					ele.ondblclick = function(){
			            showCodeList(cCode,[this],null,null,null,null,null,null);
			        }
					ele.onkeyup=function(){
						showCodeListKey(cCode,[this],null,null,null,null,null,null);
					} 
				}
			}
			tReturn=true;
			return tReturn;
		}
		catch(ex)
		{
			switch (ex){
				default:alert("�� MulLine.js-->_SetRowColDataCustomize �����з����쳣��"+ex);break;
			}
			return false;
		}
	}
/**
 *	��ָ����ֵ��ͬʱ�ı���ʽ(�ⲿ����)
 *	@param	��	cRow��	�кţ���1��ʼ��
 *	@param	��	cCol��	�кţ���1��ʼ��
 *	@param	��	cData��	���ݣ��������
 *	@param	��	cObjInstance:  mulLine����
 *	@param	��	cCode��	��Ԫ����ʽ
 *	@param	��	cCondition��	
 *	@return	��	boolean�� �ı���
 *	
 *	@logic	: 	��SetRowColDataCustomize��ͬ����readonly���˷���������readonly
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.SetRowColDataShowCodeList = function(cRow, cCol, cData, cObjInstance, cCode, cCondition){
		var tReturn=false;	var tObjInstance;         //����ָ��
		if (cObjInstance==null)	tObjInstance=this;else	tObjInstance=cObjInstance;
		if(cData==null) cData="";
		if(cRow<0||cRow>=tObjInstance.mulLineCount)	{
			alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cRow);
			return tReturn;
		}
		if(cCol<0||cCol>=tObjInstance.colCount)	{
			alert("�� MulLine.js-->SetRowColDataCustomize() ʱָ���˴������:"+cCol);
			return tReturn;
		}
		try	{
			cData+="";
			var newData=cData.replace("\r\n","");

			if( chkzh(newData) == false ){
				cData=newData;
				newData= $().ChangeDataToNew(cData);
			}
			var tColId = "#" + tObjInstance.instanceName + cCol + "r" + cRow;
			var $ele = $(tColId);
			$ele.attr("value",cData).attr("class",function(index, oldValue){
				if(cCode != null) oldValue="common";
				return oldValue;
			});
			
			var ele = $ele.get(0);  
			if(cCode != null && cCode != ""){
				ele.readOnly = true;
				if(cCode == "readonly"){
					ele.ondblclick=null;
					ele.onkeyup=null;
				}else{
					ele.ondblclick = function(){
			            showCodeList(cCode,[this],null,null,null,null,cCondition,1);
			        }
					ele.onkeyup=function(){
						showCodeListKey(cCode,[this],null,null,null,null,cCondition,1);
					} 
				}
			}
			tReturn=true;
			return tReturn;
		}
		catch(ex)
		{
			switch (ex){
				default:alert("�� MulLine.js-->_SetRowColDataCustomize �����з����쳣��"+ex);break;
			}
			return false;
		}
	}
/**
 *	���ҳ���ͻ�ҳ����
 *	@param  ��	turnPageClass��ҳ�������
 *	@return	��	boolean�� �ı���
 *	
 *	@logic	: 	
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����mulLineCount
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.SetPageMark = function(cTurnPage){
		var tReturn=false;	var tObjInstance;         //����ָ��
		tObjInstance=this;
        if (tObjInstance.SortPage == null || tObjInstance.SortPage == ""){
           return tReturn;
        }
        if(tObjInstance.ShowPageMark == false){
        	return tReturn;
        }
 		var tStrPageName = this.instanceName;
		var tTotalPageNum = Math.ceil(cTurnPage.queryAllRecordCount / cTurnPage.pageLineNum);
		var tPageIndex = cTurnPage.pageIndex + 1;
		var tHTML = "";
		try	{
			tHTML += "<div class=MulLinePageDiv valign=middle>";
			tHTML += "<table class = MulLinePageTable><tr hight=20px valign=middle>";
			if (tPageIndex != '1') {
			     tHTML += "<td valign=center >&nbsp<img src='../common/images/firstPage_defualt.jpg' style='cursor: hand;' onclick='" + tStrPageName 
						+ ".SortPage.gotoPage(1);' onmousedown=\"" + tStrPageName + ".pagebuttondown('f', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('f', this)\" name=btnchangepage>&nbsp</td>";
			     tHTML += "<td valign=center ><img src='../common/images/PreviousPage_defualt.jpg' style='cursor: hand;' onclick='" + tStrPageName 
					+ ".SortPage.gotoPage(" + tPageIndex + "-1);'  onmousedown=\"" + tStrPageName + ".pagebuttondown('p', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('p', this)\" name=btnchangepage>&nbsp</td>";
			} else {
			     tHTML += "<td valign=center >&nbsp<img src='../common/images/firstPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('f', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('f', this)\" style='cursor: hand;' onclick='alert(\"�Ѿ�������ҳ��\""
			     	+ ");' name=btnchangepage>&nbsp</td>";
			     tHTML += "<td valign=center ><img src='../common/images/PreviousPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('p', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('p', this)\" style='cursor: hand;' onclick='alert(\"�Ѿ�������ҳ��\");' "
			     	+ "name=btnchangepage>&nbsp</td>";
			}
			
			tHTML += "<td valign=center style='font-size:13px;padding-bottom:4px' >��&nbsp;" + tPageIndex + "/" + tTotalPageNum + "&nbsp;ҳ&nbsp;</td>";/**/
			if (tPageIndex != tTotalPageNum) {
			     tHTML += "<td valign=center ><img src='../common/images/NextPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('n', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('n', this)\" style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(" + tPageIndex + "+1);' name=btnchangepage>&nbsp</td>";
			     tHTML += "<td valign=center ><img src='../common/images/lastPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('l', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('l', this)\" style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(" + tTotalPageNum + ");' name=btnchangepage>&nbsp</td>";
			} else {
			     tHTML += "<td valign=center ><img src='../common/images/NextPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('n', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('n', this)\" style='cursor: hand;' onclick='alert(\"�Ѿ�����βҳ��\");' name=btnchangepage>&nbsp</td>";
			     tHTML += "<td valign=center ><img src='../common/images/lastPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('l', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('l', this)\" style='cursor: hand;' onclick='alert(\"�Ѿ�����βҳ��\");' name=btnchangepage>&nbsp</td>";
			}
			
			tHTML += "<td valign=center style='font-size:12px;' >ת��&nbsp;<input type='common' style='border: 1px #9999CC solid;height: 18px;position:relative;bottom:2px;' name='GotoPage" + tStrPageName + "' size='3'>&nbsp;ҳ</td>";
			tHTML += "<td valign=center>&nbsp<img src='../common/images/gotoPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('g', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('g', this)\" style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(document.all.GotoPage" + tStrPageName + ".value);' name=btnchangepage></td>";
			tHTML += "</tr></table></div>";
			$("#span"+tStrPageName).append(tHTML);
			tReturn=true;
			return tReturn;
		}
		catch(ex)
		{
			switch (ex){
				default:alert("�� MulLine.js-->SetPageMark �����з����쳣��"+ex);break;
			}
			return false;
		}
	}
	$.fn.pageButtonDown = function(namePix, obj){
		switch(namePix) {
			case 'f':
			  obj.src = "../common/images/firstPage_pressed.jpg";
			  break;
			case 'l':
			  obj.src = "../common/images/lastPage_pressed.jpg";
			  break;
			case 'p':
			  obj.src = "../common/images/PreviousPage_pressed.jpg";
			  break;
			case 'n':
			  obj.src = "../common/images/NextPage_pressed.jpg";
			  break;
			case 'g':
			  obj.src = "../common/images/gotoPage_defualt.jpg";
			  break;
			default:
		}
	}
	$.fn.pageButtonUp = function(namePix, obj){
		switch(namePix) {
			case 'f':
			  obj.src = "../common/images/firstPage_defualt.jpg";
			  break;
			case 'l':
			  obj.src = "../common/images/lastPage_defualt.jpg";
			  break;
			case 'p':
			  obj.src = "../common/images/PreviousPage_defualt.jpg";
			  break;
			case 'n':
			  obj.src = "../common/images/NextPage_defualt.jpg";
			  break;
			case 'g':
			  obj.src = "../common/images/gotoPage_defualt.jpg";
			  break;
			default:
		}
	}
	
/**
 *	���¼���flexigrid
 *	@param	��	cConfig��flexigrid������
 *	@param	��	cObjInstance:  mulLine����
 *	@return	��	boolean�� �ı���
 *	
 *	@logic	: 	��ȡ��ԭ�е�mulline���������еı�����������flexigrid
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.Reload = function(cConfig, cObjInstance){
		var tReturn=false;	var $tObjInstance;         //����ָ��
		if (cObjInstance==null)	$tObjInstance=$("#span"+this.instanceName);
		else	$tObjInstance=$("#span"+cObjInstance.instanceName);
		if (cConfig == null) return tReturn;
		try	{
			var t = $tObjInstance.find("[grid]");			
			var clone = t.flexOptions(cConfig).attr("clone");
			var p = t.attr("p");			
			$tObjInstance.empty();
			$tObjInstance.append(clone);
			$(clone).flexigrid(p);
			tReturn=true;
			return tReturn;
		}
		catch(ex)
		{
			switch (ex){
				default:alert("�� MulLine.js-->Reload �����з����쳣��"+ex);break;
			}
			return false;
		}
	}
/**
 *	Ϊinput������title����
 *	@param	��	cElement��input����
 *	@return	��	boolean�� �ı���
 *	
 *	@logic	: 	ֱ������title����
 *	@global	: 	Ӱ�쵽��mulLine���Ժ�html����
 *				ʹ�õ�mulLine���Ժ�html����
 **/
	$.fn.SetTitle = function(cElement){
	    if(cElement == null ) return ;
	    $(cElement).attr("title",$(cElement).val());
	}
	function getColNoByName(array,colName){
		for(var i=0,size=array.length;i<size;i++){
			if(array[i][0].trim()==colName){
				return i;
			}
		}
		return -1;
	}
	$.fn.SetRowColDataByName = function(cRow, colName, cData, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->GetRowColDataByName() ʱָ���˴������:" + cRow;}
		var cCol = getColNoByName(tObjInstance.arraySaveOra,colName);
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "��MulLine.js-->setRowColData() ʱָ���˴������:" + cCol;}
		try{
			var newData = cData;//Ӧ�ü�һ���ַ����ж�
			newData = cData.replace("\r\n", "");
			newData = cData.replace("'", "\\'");
			document.getElementsByName(tObjInstance.instanceName+cCol)[cRow].value = newData;
			$("#" + tObjInstance.instanceName + cCol + "r" + cRow,tObjInstance.initTable).val(newData);
			tReturn = true;
		}catch(ex){
			alert("�� MulLine.js --> SetRowColData �����з����쳣��" +  ex + "\n");
		}
		return tReturn;
	}
	$.fn.GetRowColDataByName = function(cRow, colName, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "��MulLine.js-->GetRowColDataByName() ʱָ���˴������:" + cRow;}
		var cCol = getColNoByName(tObjInstance.arraySaveOra,colName);
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "��MulLine.js-->GetRowColDataByName() ʱָ���˴������:" + GetRowColDataByName;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).val();
		}catch(ex){
			alert("�� MulLine.js --> GetRowColDataByName �����з����쳣��" +  ex + "\n");
		}
		return tReturn;
	};
})(jQuery);

/** ���÷� */
// ʾ����
/*
iArray.mulAdd(new function(){
    this.id = 'serailno';// id
	this.name = '���';// ����
	this.width = '30px';// �п�
	this.maxWidth = 30;// ����п�
	this.type = 0;// ����: 0-ֻ��; 1-�ı�; 2-����ѡ��; 3-����; 4-����; 5-����������ȷ��
});
iArray.mulAdd(new function(){
    this.id = 'contno';
	this.name = '������';
	this.width = '100px';
	this.maxWidth = 150;
	this.type = 0;
});
*/

// ����
function MulArray() {
	this.isMulArray = true;
	this.mullineSubData2D = new Array();
	this.add = _MulAdd;
}

// ����ת��&����˵��
function _MulAdd(colElement){
	var i = this.mullineSubData2D.length;
	
	this.mullineSubData2D[i] = new Array();
    this.mullineSubData2D[i]['id']=colElement.id;// ��ID
    this.mullineSubData2D[i][0] = colElement.name;// ����
    this.mullineSubData2D[i][1] = colElement.width;// �п�
    this.mullineSubData2D[i][2] = colElement.maxlength;// ��󳤶ȣ�Ĭ��100
    this.mullineSubData2D[i][3] = colElement.type;// ����: 'text'-�ı���'code'-����ѡ��'hidden'-���ء�'password'-���롢'doubleinput'-����������ȷ��
    this.mullineSubData2D[i]['ro']=colElement.readonly;// ֻ����Ĭ��false
    this.mullineSubData2D[i][21]= colElement.align;// ���ֶζ��뷽ʽ���磺'left'�� 'right'�� 'center'��Ĭ��'left'
    this.mullineSubData2D[i][14]= colElement.value;// ��ֵ(����)
    this.mullineSubData2D[i][22]= colElement.onblur;// ʧȥ���㴥�����ⲿ����
    this.mullineSubData2D[i][9] = colElement.verify;// ��ʽУ��
    this.mullineSubData2D[i][7] = colElement.ondblclickFunc;// ��Ӧ˫���¼��ķ�������������������ǰ�е�spanID, ondblclickParam2��
    this.mullineSubData2D[i][8] = colElement.ondblclickParam2;// ��Ӧ˫���¼�ondblclickFunc�ĵ�2������
    // �����ݿ�������á�
    this.mullineSubData2D[i][4] = colElement.showCodeListCodeName;// ���������磺'comcode'
    this.mullineSubData2D[i][5] = colElement.showCodeListShowCodeObj;// ��ʾ������У��磺'7|8'
    this.mullineSubData2D[i][6] = colElement.showCodeListShowCodeOrder;// ��Ӧ����ʾ˳���磺'0|1'
    this.mullineSubData2D[i][15]= colElement.showCodeListConditionFieldName;// �����������ؼ������������磺'ManageCom'
    this.mullineSubData2D[i][16]= colElement.showCodeListConditionFieldValue;// �����������ؼ�ֵ����ֵ���磺'86'
    this.mullineSubData2D[i][17]= colElement.showCodeListConditionFieldMulValue;// ����������(���)�ؼ�ֵ����ֵ���磺'86|8632'
    this.mullineSubData2D[i][18]= colElement.showCodeListWidth;// ����������Ŀ�ȣ��磺'60px'
    this.mullineSubData2D[i][19]= colElement.showCodeListRefresh;// ǿ��ˢ��codeSelect����Դ���磺'1'��Ĭ��null
    // ��ǰ̨CodeData���á�Ex
    this.mullineSubData2D[i][10]= colElement.showCodeListExCodeName;// ���������磺'sex'
    this.mullineSubData2D[i][11]= colElement.showCodeListExCodeData;// ��������Դ���磺'0|^0|��|^1|Ů'
    this.mullineSubData2D[i][12]= colElement.showCodeListExShowCodeObj;// ��ʾ������У��磺'5|6'
    this.mullineSubData2D[i][13]= colElement.showCodeListExShowCodeOrder;// ��Ӧ����ʾ˳���磺'0|1'
    
//  this.mullineSubData2D[i][20] = colElement.DISABLE_PARAM1;// [ͣ��]����setRowColData�������жϸò������Ƿ񽫱���תΪ���ģ�
    
    // ����ת��
    this.mullineSubData2D[i][3] = mullineTypeStringtoNumberFormat(this.mullineSubData2D[i][3]);
    
    // Ĭ��maxlength
    if (typeof(this.mullineSubData2D[i][2]) == 'undefined') {
    	this.mullineSubData2D[i][2] = 100;
    }
};

/**
	id ����ID , iArray[i]['id'];
	name ������ , iArray[i][0];
	width : �п� , iArray[i][1];
	maxlength : ��󳤶�, iArray[i][2];
	type : ����: 'readonly'-ֻ����'text'-�ı���'code'-����ѡ��'hidden'-���ء�'password'-���롢'doubleinput'-����������ȷ�� , iArray[i][3];
    showCodeListCodeName : �����ݿ�������á������� , iArray[i][4];
    showCodeListShowCodeObj : �����ݿ�������á���ʾ������� , iArray[i][5];
    showCodeListShowCodeOrder : �����ݿ�������á���Ӧ����ʾ˳�� , iArray[i][6];
    ondblclickFunc : ��Ӧ˫���¼��ķ�������������������ǰ�е�spanID, ondblclickParam2�� , iArray[i][7];
    ondblclickParam2 : ��Ӧ˫���¼�ondblclickFunc�ĵ�2������ , iArray[i][8];
    formatVerify : ��ʽУ�� , iArray[i][9];
    showCodeListExCodeName : ��ǰ̨CodeData���á������� , iArray[i][10];
    showCodeListExCodeData : ��ǰ̨CodeData���á���������Դ , iArray[i][11];
    showCodeListExShowCodeObj : ��ǰ̨CodeData���á���ʾ������� , iArray[i][12];
    showCodeListExShowCodeOrder : ��ǰ̨CodeData���á���Ӧ����ʾ˳�� , iArray[i][13];
    value : ��ֵ(����) , iArray[i][14];
    showCodeListConditionFieldName : �����ݿ�������á������������ؼ���������, iArray[i][15];
    showCodeListConditionFieldValue : �����ݿ�������á������������ؼ�ֵ����ֵ, iArray[i][16];
    showCodeListConditionFieldMulValue : �����ݿ�������á�����������(���)�ؼ�ֵ����ֵ, iArray[i][17];
    showCodeListWidth : �����ݿ�������á�����������Ŀ�� , iArray[i][18];
    showCodeListRefresh : �����ݿ�������á�ǿ��ˢ��codeSelect����Դ , iArray[i][19];
    DISABLE_PARAM1 : [ͣ��]����setRowColData�������жϸò������Ƿ񽫱���תΪ���ģ� , iArray[i][20];
	align : ���ֶζ��뷽ʽ , iArray[i][21];
	onblur : ʧȥ���㴥�����ⲿ���� , iArray[i][22];
	
}*/

// ����: 'readonly'-ֻ��; 'text'-�ı�; 'code'-����ѡ��; 'hidden'-����; 'password'-����; 'doubleinput'-����������ȷ��
function mullineTypeStringtoNumberFormat(ttype) {
	var formatNumber = '1';
	switch(ttype)
	{
	case 'readonly':
	  formatNumber = '0';
	  break;
	case 'text':
	  formatNumber = '1';
	  break;
	case 'code':
	  formatNumber = '2';
	  break;
	case 'hidden':
	  formatNumber = '3';
	  break;
	case 'password':
	  formatNumber = '4';
	  break;
	case 'doubleinput':
	  formatNumber = '5';
	  break;
	default:
	  formatNumber = '1';
	}
	return formatNumber;
}

MulLineEnter.prototype.IDtoIndexArray = new Array();
MulLineEnter.prototype.saveColID = _SaveColID;// ����ID
MulLineEnter.prototype.colIDtoIndex = _ColIDtoIndex;// ID���±��ת��
// ����ID
function _SaveColID(arrCols) {
	for (var i=0 ; i<arrCols.length ; i++) {
		var one_id = arrCols[i]['id'];
		if (typeof(one_id) != 'string') {
			alert(this.instanceName + "�У���" + arrCols[i][0] + "��id���Ͳ�����\nע��id�������ַ�����");
			return false;
		}
		this.IDtoIndexArray[one_id] = i;
	}
	return true;
}
// ID���±��ת��
function _ColIDtoIndex(cCol) {
	if (typeof(this.IDtoIndexArray[cCol]) == 'undefined') {
		alert("δ�ҵ�idΪ" + cCol + "���С�");
		return -1;
	}
	return this.IDtoIndexArray[cCol];
}