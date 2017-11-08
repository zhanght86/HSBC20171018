/************************************************
 *
 *	程序名称: MulLine.js
 *	程序功能: 多记录输入，调用jquery生成表
 *
 *************************************************/
/************************************************
 *	类：多行输入类
 *************************************************/
/**	初始化mulLine类,调用initMulLineEnter
 *	@param  : string 表单控件名称
 *	@param	: string 实例名称
 *	@return : none
 *	
 *	@logic	: 
 *	@global	: 影响到的mulLine属性和html对象：所有初始化
 *				使用的mulLine属性和html对象：
 **/
var $j15 = jQuery.noConflict();
function MulLineEnter(iFormName, iInstanceName)
{
    //以下属性需要用户初始化
    this.formName = iFormName || "fm";	//表单控件名称，必须
    this.instanceName = iInstanceName || "";	//实例名称，必须
    this.mulLineCount = 0;	//行输入对象的行数
    this.canAdd = 1;	//是否可以允许增加,删除1表示可以,0表示不可以
    this.canSel = 0;	//是否可以选择,1表示可以,0表示不可以
    this.showTitle = 1; //是否显示控件title 1表示显示,0表示不显示
    this.displayTitle = 1;	//是否显示表头标题,1表示显示,0表示不显示
    this.locked = 0;	//是否锁定,1表示锁定,0表示可编辑
    this.canChk = 0;	//是否需要多行选择,1表示可以多行选择,0表示不可以
    this.displayCanChkAll = 0;    //是否需要全选键    //tongmeng 2009-05-08 modify
    this.hiddenPlus = 0;	//新增,是否隐藏添加一行的标志：0为显示,1为隐藏
    this.hiddenSubtraction = 0;	//新增,是否隐藏删除一行的标志：0为显示,1为隐藏
    this.mulLineNum = 1;	//新增,设置同一行的MulLine的个数,默认是1
    this.flexConfig = {dataType: 'json',height: 'auto',cellEffect:'on'};//用于控制flexgrid的样式
    this.choiceColor = true;//当选中radio或者checkbox时设置背景颜色

    
    //调用外部函数名称和参数
	this.chkBoxEventFuncName = "";	//新增,保存外部单击CheckBox框时响应的外部函数名
	this.chkBoxEventFuncParm = " ";	//新增,保存外部单击CheckBox框时响应的外部函数传入的参数
	this.chkBoxAllEventFuncName = "";	//新增,保存外部单击标题栏全选CheckBox框时响应的外部函数名
	this.selBoxEventFuncName = "";	//新增,保存外部单击RadioBox框时响应的外部函数名
	this.selBoxEventFuncParm = " ";	//新增,保存外部单击RadioBox框时响应的外部函数传入的参数
	this.addEventFuncName = "";	//新增,保存外部单击+按钮时响应的外部函数名
	this.addEventFuncParm = " ";	//新增,保存外部单击+按钮框时响应的外部函数传入的参数
	this.delEventFuncName = "";	//新增,保存外部单击-按钮时响应的外部函数名
	this.delEventFuncParm = " ";	//新增,保存外部单击-按钮框时响应的外部函数传入的参数

    //以下属性不需要用户初始化
    this.colCount = 0;	//新增,列的数目
    this.recordNo = 0;	//新增,如果分页显示多条纪录,那么显示前将该值赋为基数,那么第2页显示的序号会接着上页的序号,由turnPage修改
    this.checkFlag = 0;	//新增,和checkAll函数配合用,0 未全选,1 全选
    this.state = 0;	//新增,此参数对外部无任何实际意义,和_ResumeState函数一起使用，（未使用）
    this.arraySave = new Array();	//新增,保存传入的列数组
    this.arraySave2 = new Array();	//新增,保存参数的数组--用于是否显示中文，（未使用）
    this.arraySaveOra = new Array();	//新增,顺序没有改变的数组
    this.arraySaveOrder = new Array(); //记录现在顺序列对应的原始列的位置

	this.editArrayStore = new Array();//新增,编辑按钮信息

    this.arraySaveWith = new Array();//包含变化后宽度信息的array，（未使用）
    this.arraySave3 = new Array();//包含变化后宽度信息的array，（未使用）
    this.arrayChoose = new Array();//包含选择列信息的array，（未使用）
    this.newColOrder = new Array();//列的顺序 add by wanglei
    this.editArray = new Array();//用来存储其它按纽信息 add by jinsh，（未使用）
    
    
    this.totalPre = 0;//table的前缀数量,例如sel,chk 等.，（未使用）
    this.AllowSort = "1";	//排序
    this.SortPage = "";	//排序中Grid对应的turnpage
    this.ShowPageMark = true;//是否显示自带的翻页按钮
    this.allowsort = "AllowSortFun";	//Grid的排序函数通过它调用turnpage中的函数，（未使用）
    this.windowWidth = window.document.body.clientWidth;
    this.windowHeight = window.document.body.clientHeight;

    this.detailInfo = "";	//如果支持单击,则在此处设置提示信息，（未使用）
    this.tableWidth = "";	//设置table的宽度

	//以下三个是生成表格的重要对象
	this.initTable;//生成flexigrid之前的表格对象(内部使用）
    this.mulLineText = "";	//行输入对象的一行模版的内容(内部使用）
    this.mulLineTextTitle = "";	//行输入对象的标题（内部使用）

    this.lastFocusRowNo = -1; //最近一次得到焦点的行(从0开始)
    this.lastFocusColNo = -1; //最近一次得到焦点的列(从1开始)


    this.maxSpanID = -1;	//行输入对象的最大SpanID的值    //初始化添加一行隐藏行,spanID由-1 改成-2，（未使用）
    this.errorString = "";	//该变量为当执行发生错误时,提示的错误信息，（未使用）

//引入jquery调用，实现拖动，样式，显示等功能
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
    //方法
    //生成表格主要方法
    this.loadMulLine = jquery_mulline.LoadMulLine;//生成不带数据的表
    this.loadMulLineArr = jquery_mulline.LoadMulLineArr;//生成带数据的表
    this.loadPage = jquery_mulline.LoadPage;//加载页面
    this.setFieldValue = jquery_mulline.SetFieldValue;//生成模板
    
    this.clearData = jquery_mulline.ClearData;//清空数据
    this.addOne = jquery_mulline.AddOneWithoutData;//根据模板添加一行（或几行）空白行
	this.deleteOne = jquery_mulline.DeleteOne;//删除指定行
	this.delBlankLine = jquery_mulline.DelBlankLine;//删除空白行
	this.delCheckTrueLine = jquery_mulline.DelCheckTrueLine;	//删除选中的CheckBox行
	this.delRadioTrueLine = jquery_mulline.DelRadioTrueLine;	//删除选中的RadioBox行
	
	this.updateField = jquery_mulline.UpdateField;//动态为表格修改指定列(或在最后增加)
	this.setPageMark = jquery_mulline.SetPageMark;//生成页码信息
	this.reload = jquery_mulline.Reload;//根据新设置重新加载当前表格
	
    //表格其它函数,如点击事件,求值,获得焦点,设置焦点
	this.radioClick = jquery_mulline.RadioClick;//单选单击事件
	this.checkBoxClick = jquery_mulline.CheckBoxClick;//多选单击事件
	
	this.orderByName = jquery_mulline.OrderByName;//排序
	
	this.setTitle = jquery_mulline.SetTitle;//input中的title属性设置成title
	
  	this.getSelNo = jquery_mulline.GetSelNo;//获得被选中的单选行数
	this.getChkNo = jquery_mulline.GetChkNo;//判断该多选行是否被选中
    this.checkAll = jquery_mulline.CheckAll;//全选或者不选
    this.checkBoxAll = jquery_mulline.CheckBoxAll;//全选
    this.checkBoxAllNot = jquery_mulline.CheckBoxAllNot;//全不选
    this.checkBoxSel = jquery_mulline.CheckBoxSel;//选中指定行
    this.checkBoxNotSel = jquery_mulline.CheckBoxNotSel;//取消指定行选中状态
    
    this.setRowColData = jquery_mulline.SetRowColData;//设置某行某列值
    this.getRowColData = jquery_mulline.GetRowColData;//获得某行某列值
    this.getRowColElemt = jquery_mulline.GetRowColElemt;
    this.getRowColClass = jquery_mulline.GetRowColClass;//获取某一行列的classname
    this.setRowColDataByName = jquery_mulline.SetRowColDataByName;//设置某行某列值
    this.getRowColDataByName = jquery_mulline.GetRowColDataByName;//获得某行某列值
    this.getRowData = jquery_mulline.GetRowData;//获得某行值
    this.setFocus = jquery_mulline.SetFocus;//设置焦点
    this.getFocus = jquery_mulline.GetFocus;//判断是否获得焦点
    this.moveFocus = jquery_mulline.MoveFocus;//最新行获得焦点
    
    this.findSpanID = jquery_mulline.FindSpanID;//获得对应行的spanid
    
    this.lock = jquery_mulline.Lock;//锁定删除和增加行的功能
    this.unLock = jquery_mulline.Unlock;//解除锁定删除和增加行的功能
    
    //以下为还未使用jquery实现的功能
    // 2009-03 给某个单元格赋值的同时,修改样式
    this.setRowColDataCustomize = jquery_mulline.SetRowColDataCustomize;
    this.setRowColDataCustomize1 = jquery_mulline.SetRowColDataCustomize1;

    this.setRowColDataShowCodeList= jquery_mulline.SetRowColDataShowCodeList    //设置下拉列表数据
    this.setRowColClass=jquery_mulline.SetRowColClass;//设置相应行列的class
    this.setRowClass=_setRowClass;	//设置相应行的class
    this.setRowColTitle=_SetRowColTitle;//设置显示的提示框
	this.setRowColCurrency = _setRowColCurrency;//设置指定列的moneyType属性
	
	this.getChkCount = jquery_mulline.GetChkCount;//返回多选的选中行数
	this.selOneRow = jquery_mulline.SelOneRow;//选中某行
	this.chkOneRow = jquery_mulline.ChkOneRow;//选中某行
	
    this.pagebuttondown = jquery_mulline.pageButtonDown;
    this.pagebuttonup = jquery_mulline.pageButtonUp;
	
	this.checkValue = _CheckValue;//校检
	this.checkValue2 = _CheckValue2;//校检
   
/*  this.keyUp = _KeyUp;//原js中未实现
    this.getErrStr = _GetErrStr;//得到错误消息，暂不实现
    this.detailClick = _detailClick;//原js中未实现
    this.resumeState = _ResumeState;//无需实现
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
 * 方法：当其子控件任意一个得到焦点时计算该子控件的行和列
 * 输入：MulLine 对象
 * 输入：子控件对象
 * 输出：无
 * 备注：XinYQ added on 2006-10-10
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
            //alert("在 MulLine.js  _CalcFocusRowColNo 函数中发生错误：实例名和控件名不允许为空！ ");
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
            //计算焦点行
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
                //alert("在 MulLine.js  _CalcFocusRowColNo 函数中发生错误：计算焦点行异常！ ");
                return;
            }
            //计算焦点列
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
                //alert("在 MulLine.js  _CalcFocusRowColNo 函数中发生错误：计算焦点列异常！ ");
                return;
            }
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js  _CalcFocusRowColNo 函数中发生异常：" +  ex, oInstance);
    }
}

function _setRowColCurrency(cRow, cCol, cData, cObjInstance)

{

    var tStr;

    var tObj;

    var tReturn = false;

    var tObjInstance;	//对象指针

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

        alert("在MulLine.js-->setRowColData() 时指定了错误的行:" + cRow);

        return tReturn;

    }

    if (cCol < 0 || cCol >= tObjInstance.colCount)

    {

        alert("在MulLine.js-->setRowColData() 时指定了错误的列:" + cCol);

        return tReturn;

    }

    try

    {

        //		var newData=replace(cData,"\r\n","");

        //好像是初始化行数用

        var newData = cData.replace("\r\n", "");

        //注意：必须添加下面的判断条件，否则在初始化为0行时，会出现异常

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

        _DisplayError("在MulLine.js-->_SetRowColData函数中发生异常:" + ex, tObjInstance);

    }

    return tReturn;

}
/************************************************

 *检验表格中输入的值是否符合规范

 *输入：	可以为空，或对象名

 *输出：	没有

 interpreting right by yuanaq

 ************************************************/

function _CheckValue2(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//实例名称

    var tObj = cObjInstance || this;

    tObj.delBlankLine(t_StrPageName, tObj);	//清除空行


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

            //从第1列开始，第0列是序列，不检验

            tRule = $j15("#"+t_StrPageName+i+"r0").attr("verify");

            if (tRule == null || tRule == "")

            {

                continue;

            }

            else

            {

                try

                {

                    strInfo = "第一行的" + tRule;

                    var dd = "document.getElementById('" + t_StrPageName + i+"r0')";

                    if (!verifyElementWrap2(strInfo, tObj.getRowColData(0, i, tObj), dd))

                        return false;//如果错误，返回

                }

                catch(ex)

                {

                    alert("请确认verifyInput.js 文件被包含或数据库连接正常");

                    return false;

                }

            }

        }

    }

    else

    {

        for (var i = 1; i < tObj.colCount; i++)

        {

            //从第1列开始，第0列是序列，不检验

            try

            {

                //注意:初始化时，如果不检验该列，在textTitle中设置verify=''
            	
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

                        // 外部函数，请察看verifyInput.js parm1:位置|检验规则 parm2: 要检验的值(即第N行i列的值)

                        try

                        {

                            rowNo = n + 1;

                            strInfo = "第" + rowNo + "行的" + tRule;	//提示信息中确定第几行

                            var dd = tObj.formName + ".getElementById('" + t_StrPageName + i + "r" + n+"')";

                            if (!verifyElementWrap2(strInfo, tObj.getRowColData(n, i, tObj), dd))

                            {

                                return false;	//如果错误，返回

                            }

                        }

                        catch(ex)

                        {

                            alert("请确认verifyInput.js 文件被包含或数据库连接正常");

                            return false;

                        }

                    }

                }

            }

            catch(ex)

            {

                alert("_CheckValue函数出错");

                return false;

            }

        }

    }

    return true;

}

/************************************************

 *检验表格中输入的值是否符合规范

 *输入：	可以为空，或对象名

 *输出：	没有

 ************************************************/

function _CheckValue(strPageName, cObjInstance)

{

    var t_StrPageName = strPageName || this.instanceName;	//实例名称

    var tObj = cObjInstance || this;

    tObj.delBlankLine(t_StrPageName, tObj);	//清除空行


    var tRule = "";

    var strInfo = "";

    var rowNo = 0;

    var tReturn;

    if (tObj.mulLineCount == 0)

    {

        return true;

    }

    for (var i = 1; i < tObj.colCount; i++) //从第1列开始，第0列是序列，不检验

    {

        try

        {

            //注意:初始化时，如果不检验该列，在textTitle中设置verify=''

            tRule = $j15("#"+t_StrPageName+i+"r0").attr("verify");

            if (tRule == null || tRule == "")

            {

                continue; //即不校验

            }

            else

            {

                for (var n = 0; n < tObj.mulLineCount; n++)

                {

                    // 外部函数，请察看verifyInput.js parm1:位置|检验规则 parm2: 要检验的值(即第N行i列的值)

                    try

                    {

                        rowNo = n + 1;

                        strInfo = "第" + rowNo + "行的" + tRule;   //提示信息中确定第几行

                        if (!verifyElement(strInfo, tObj.getRowColData(n, i, tObj)))

                        {

                            return false;//如果错误，返回

                        }

                    }

                    catch(ex)

                    {

                        alert("请确认verifyInput.js 文件被包含或数据库连接正常");

                        return false;

                    }

                }

            }

        }

        catch(ex)

        {

            alert("_CheckValue函数出错");

            return false;

        }

    }

    return true;

}
//设置相应行的class
function _setRowClass(cRow, cData, cObjInstance)
{  
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }

    for(var j=0; j<tObjInstance.colCount; j++){			//循环此行的列
		tObjInstance.setRowColClass(cRow,j,cData);			//修改背景色
    }
}

//tongmeng 2008-10-27 add
//设置相应行列的class
function _SetRowColClass(cRow, cCol, cData, cObjInstance)
{
    var tStr;
    var tObj;
    var tReturn = false;
    var tObjInstance; //对象指针
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
        alert("在 MulLine.js --> _SetRowColClass() 时指定了错误的行:" + cRow);
        return tReturn;
    }
    if (cCol < 0 || cCol >= tObjInstance.colCount)
    {
        alert("在 MulLine.js --> _SetRowColClass() 时指定了错误的列:" + cCol);
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
        //注意：必须添加下面的判断条件，否则在初始化为0行时，会出现异常
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
        //_DisplayError("在 MulLine.js --> _SetRowColData 函数中发生异常：" + ex, tObjInstance);//edit by yaory


    }

    return tReturn;
}

//tongmeng 2008-10-27 add
//设置相应行列的Title
function _SetRowColTitle(cRow, cCol, cData, cObjInstance)
{
    var tStr;
    var tObj;
    var tReturn = false;
    var tObjInstance; //对象指针
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
        alert("在 MulLine.js --> _SetRowColTitle() 时指定了错误的行:" + cRow);
        return tReturn;
    }
    if (cCol < 0 || cCol >= tObjInstance.colCount)
    {
        alert("在 MulLine.js --> _SetRowColTitle() 时指定了错误的列:" + cCol);
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
        //注意：必须添加下面的判断条件，否则在初始化为0行时，会出现异常
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
        //_DisplayError("在 MulLine.js --> _SetRowColData 函数中发生异常：" + ex, tObjInstance);//edit by yaory
    }
    return tReturn;
}
/************************************************

 *方法：显示多行输入的错误信息

 *输入：	strError 需要显示的错误信息

 *			cObj	实例指针

 *输出：	没有

 ************************************************

 */

function _DisplayError(strError, cObj)

{

    cObj.errorString = strError;

    alert(strError);

}
/**
 *	接受从MulLine的参数,请求,转化为flexigrid——jquery可以识别的格式,传给flexiGrid
 *	需要把MulLine的方法指向jquery
 *	2011-09-28
 *	huangliang
 **/
(function($){
/**	初始化,返回jquery对象
 *	@param  :	object	mulline对象
 *	@return :	object	jquery对象
 *	
 *	@logic	: 
 *	@global	:	影响到的mulLine属性和html对象：initTable
 *				使用的mulLine属性和html对象：
 **/
	$.fn.initMulLineEnter = function(cObjInstance){
		cObjInstance.initTable = document.createElement('table');	//表的DOM对象,用于保存生成表之前的表信息
		$(cObjInstance.initTable).append("<thead></thead><tbody></tbody>");
		return this;
	};
/**	程序调用的初始化方法,用于初始化表（不带数据）,并调用SetFieldValue和LoadPage。
 *	@param  :	array	二维数组,表格每列的属性
 *	@param  :	array	二维数组,可编辑按钮属性（实际上不用）
 *	@return :	none
 *	
 *	@logic	:	调用SetFieldValue生成tbody和thead模板,调用LoadPage生成表
 *	@global	:	影响到的mulLine属性和html对象：initTable,span,arraySave,editArrayStore,arraySaveOra
 *				使用的mulLine属性和html对象：instanceName
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
/**	用于生成带数据的表,调用SetFieldValue，ChangeArrCol，SaveArrInfo，LoadPageArr
 *	@param  :	array	每列的属性信息
 *	@param	:	array	二维数组,数据
 *	@return :	none
 *	
 *	@logic	:	SetFieldValue生成模板，调用SaveArrInfo生成各列的现有位置和宽度信息，调用ChangArrCol生成位置和宽度的新各列配置，调用LoadPageArr生成带数据的表
 *	@global	:	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：instanceName
 **/
	$.fn.LoadMulLineArr = function(arrCols, cData){
		var strPageName = this.instanceName;
		var $this = $(this);
		$this.SetFieldValue(strPageName,arrCols,this,this.editArrayStore);
		$this.SaveArrInfo(strPageName,this);//记录各列的宽度，位置
		$this.ChangeArrCol(cData,this);//生成各列新位置,宽度
		$this.LoadPageArr(strPageName, this, cData);
	}
/**	用于把配置好的表放到网页中（不带数据）,调用AddOneWithoutData和NewTableFlexiGrid
 *	@param  :	string 	mulLine对象名
 *	@param  :	objcect	mulLine对象
 *	@return :	none
 *	
 *	@logic	:	生成表头thead,调用AddOneWithoutData生成tbody,flexigrid生成表格
 *	@global	:	影响到的mulLine属性和html对象：initTable，arraySaveOrder
 *				使用的mulLine属性和html对象：instanceName,mulLineTextTitle
 **/
	$.fn.LoadPage = function(strPageName, cObjInstance){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		$("thead",tObjInstance.initTable).append(tObjInstance.mulLineTextTitle);//初始化表头
		var th_num = $("thead tr:eq(0) th",tObjInstance.initTable).length;
		for(i=0;i<th_num;i++){tObjInstance.arraySaveOrder[i]=i}////生成原始列位置
		var $this = $(this);
		$this.AddOneWithoutData(strPageName,tObjInstance.mulLineCount,tObjInstance);	//初始化tbody
		$this.NewTableFlexiGrid(tObjInstance);//生成表
	};
/**	用于把配置好的表放到网页中（带数据）,调用AddOneArr和flexigrid。
 *	@param  :	string	实例名 mulline对象名 ：***Grid
 *	@param	:	object	cObjInstance:mulline对象
 *	@param	:	array	二维数组,数据
 *	@return :	none
 *	
 *	@logic	:	调用AddOneArr生成tbody,flexigrid生成表格
 *	@global	:	影响到的mulLine属性和html对象：iinitTable,span
 *				使用的mulLine属性和html对象：instanceName
 **/	
	$.fn.LoadPageArr = function(strPageName, cObjInstance, cData){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		$("thead tr",tObjInstance.initTable).replaceWith(tObjInstance.mulLineTextTitle);//重新初始化表头
		var $this = $(this);
		$this.AddOneArr(strPageName, tObjInstance.mulLineCount, tObjInstance, cData);		//生成tbody并添加数据
		$this.NewTableFlexiGrid(tObjInstance);//生成表
	};
/**	用于将完成的表转化为flexigrid，调用flexigrid,生成加一行按钮
 *	@param	: cObjInstance:mulline对象
 *	@return : none
 *
 *	@logic	: 定位span，生成表,在表后生成加一行按钮
 *	@global	: 影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：instanceName,hiddenPlus,initTable
 **/
	$.fn.NewTableFlexiGrid = function(cObjInstance){
		var tObjInstance;
		(cObjInstance==null)?tObjInstance=this:tObjInstance=cObjInstance;
		var strPageName = tObjInstance.instanceName;
		var new_table = $(tObjInstance.initTable).clone();		//如果不用新的复制对象,title将出错，同时由于grid已存在，flexigrid也会出错
		var $span = $("#span"+ strPageName);
		var grid = $span.find("[grid]");
		var config = tObjInstance.flexConfig;//cellEffect可以大幅提速
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
	
/**	用于记录最新的表的各列信息，如改变后的宽度，位置等
 *	@param  :	string	实例名 mulline对象名 ：***Grid
 *	@param	:	object	mulLine对象
 *	@return :	none
 *
 *	@logic	:	保存信息到newColOrder中,n代表现在列号，其中0代表内容，1代表宽度，2代表上次改动后的列号，3代表原始列号,4代表是否隐藏
 *	@global	:	影响到的mulLine属性和html对象：newColOrder，arraySaveOrder
 *				使用的mulLine属性和html对象：instanceName
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
			alert("在MulLine.js-->saveArrInfo函数中发生异常:" + ex);
		}
	};
/**	用于根据新的列信息，生成新的配置（不改动数组顺序，因为数组和对应列绑定）
 *	@param  :	array	cData,传入数据
 *	@param	:	object	mulLine对象
 *	@return :	none
 *
 *	@logic	:	根据newColOrder中的顺序，调整模板中的位置
 *	@global	:	影响到的mulLine属性和html对象：mulLineText，mulLineTextTitle
 *				使用的mulLine属性和html对象：newColOrder
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
			colNo = tObjInstance.newColOrder[i][3];//配置数组从零开始
			colStyle = tObjInstance.newColOrder[i][4];//样式继承，主要是隐藏属性
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
/**	增加一个空行，用于不带数据的空表或者带数据增加一行
 *	@param  : 实例名 mulline对象名 ：***Grid
 *	@param	: 行数：默认为1行
 *	@param	: mulline对象
 *	@return : none
 *
 *	@logic	: for——根据数据（array）生成不足的行(带属性不带数据)，如果是初始化表，则不调用NewTableFlexiGrid
 *	@global	: 影响到的mulLine属性和html对象：mulLineCount,iinitTable
 *				使用的mulLine属性和html对象：mulLineText、mulLineTextTitle,instanceName
 **/
	$.fn.AddOneWithoutData = function(strPageName, intNumber, cObjInstance){
		var intCount;
		var hasInit;
		(intNumber == null) ? intCount = 1 : intCount = intNumber;	//新增行的个数,默认为1行
		if(cObjInstance == null){tObjInstance = this;hasInit=false;}else{tObjInstance = cObjInstance;hasInit=true;}
		var t_StrPageName = strPageName || tObjInstance.instanceName;	//实例名称
		var tr_num = $("tbody tr",tObjInstance.initTable).length;
		var str_text = "";
		//插入足够的行数,使用tObjInstance.mulLineText中的模板
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
		for(var i=0;i<rowNum;i++){//同步现有MulLine中的数据
			var rowData = tObjInstance.getRowData(i);
			for(var j=0,size=rowData.length;j<size;j++){
				var col = j+1;
			    $tbody.find("#"+t_StrPageName+col+"r"+i).val(rowData[j]);
			}
		} 
		//tmp = "<tbody>" + $tbody.html() + tmp + "</tbody>";
		$tbody.append(tmp);
		tObjInstance.mulLineCount = total_num;		//修改muline的mulLineCount
		if(!hasInit) {$(this).NewTableFlexiGrid(tObjInstance);}//如果是调用添加一行生成表
	};	
/**	用于生成带数据的表
 *	@param  : 实例名 mulline对象名 ：***Grid
 *	@param	: 行数；暂不使用,使用cData的长度
 *	@param	: cObjInstance:mulline对象
 *	@param	: array	二维数组,数据
 *	@return : none
 *	
 *	@logic	: for——根据数据（array）生成不足的行,each——遍历数组给每个input赋值
 *	@global	: 影响到的mulLine属性和html对象：mulLineCount,iinitTable
 *				使用的mulLine属性和html对象：mulLineText、mulLineTextTitle
 **/	
	$.fn.AddOneArr = function(strPageName, intNumber, cObjInstance, cData){
//根据数组生成表
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
		cObjInstance.mulLineCount = cData.length;		//修改muline的mulLineCount
		$(cObjInstance.initTable).find("tbody").html(tmp);
	};
/**	用于删除指定id行
 *	@param  : 实例名 mulline对象名 ：***Grid
 *	@param	: 行id:span***Grid1,或者数组的行id
 *	@param	: cObjInstance:mulline对象
 *	@return : none
 *
 *	@logic	: for——根据数据（array）生成不足的行(带属性不带数据)
 *	@global	: 影响到的mulLine属性和html对象：mulLineCount,iinitTable
 *				使用的mulLine属性和html对象：mulLineText、mulLineTextTitle,instanceName
 **/
	$.fn.DeleteOne = function(strPageName, spanID, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(spanID) == "string"){//传入字符串
			spanID = "#" + spanID;
			span = "#span" + tObjInstance.instanceName;
			$(spanID,tObjInstance.initTable).remove();
			$(spanID,span).remove();
			tObjInstance.mulLineCount--;
		}else if(typeof(spanID) == "object"){//传入数组
			for(i=0;i<spanID.length;i++){
				spanID[i] = "#" + spanID[i];
				$(spanID[i],tObjInstance.initTable).remove();
				tObjInstance.mulLineCount--;
			}
			$(this).NewTableFlexiGrid(tObjInstance);//生成表
		}
	};
/**	用于删除指定id行
 *	@param  : 实例名 mulline对象名 ：***Grid
 *	@param	: cObjInstance:mulline对象
 *	@return : none
 *
 *	@logic	: for——根据数据（array）生成不足的行(带属性不带数据)
 *	@global	: 影响到的mulLine属性和html对象：mulLineCount,iinitTable
 *				使用的mulLine属性和html对象：mulLineText、mulLineTextTitle,instanceName
 **/
	$.fn.DelBlankLine = function(strPageName, cObjInstance){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		var t_StrPageName = strPageName||tObjInstance.instanceName;
		var rowCount = tObjInstance.mulLineCount;	//行数
		var colCount = tObjInstance.colCount;	//列数
		var i,j;var blankFlag = true;	//空行标志
		var lineSpanID;	//行的spanID
		var data = "";var $this = $(this);
		try{
			//循环查询每一行是否为空行,即该行的每一列都为空，除了0列（序号列）
			for (i = 0; i < rowCount; i++){//从行开始循环,0行开始
				for (j = 1; j < colCount; j++){
					//从列开始循环，1列开始
					data = $this.GetRowColData(i, j, tObjInstance);
					if (data != null && data != ""){
						//如果不为空，空行标志设为false
						blankFlag = false;break;
					}
				}
				if (blankFlag){
					lineSpanID = $this.FindSpanID(i, tObjInstance);  //得到该行的spanID
					$this.DeleteOne(t_StrPageName, lineSpanID, tObjInstance); //删除这一行
					rowCount = rowCount - 1;//删除一行，循环减一
					i = i - 1;//回退一行检查
				}
				blankFlag = true;
			}
		}catch(ex){
			alert("在MulLine.js-->delBlankLine函数中发生异常:" + ex);
		}
	};	
/**	用于删除checkbox选中的行
 *	@param  : 实例名 mulline对象名 ：***Grid
 *	@param	: cObjInstance:mulline对象
 *	@return : none
 *
 *	@logic	: for——根据checkbox调用deleteone，删除对应行生成新表
 *	@global	: 影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：instanceName
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
					tArray[j] = tObjInstance.findSpanID(i, tObjInstance);	//得到该行的spanID
					j++;
				}
			}
			if(tArray.length > 0){
				tObjInstance.deleteOne(tObjInstance.instanceName, tArray, tObjInstance);
			}
		}catch(ex){
			alert("在MulLine.js-->delCheckTrueLine函数中发生异常:" + ex);
		}
	};
/**	用于删除radio选中的行
 *	@param  : 实例名 mulline对象名 ：***Grid
 *	@param	: cObjInstance:mulline对象
 *	@return : none
 *
 *	@logic	: for——根据radio调用deleteone，删除对应行
 *	@global	: 影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：mulLineText、mulLineTextTitle,instanceName
 **/
	$.fn.DelRadioTrueLine = function(strPageName, cObjInstance){
		var tObjInstance;var j = 0;
		var lineSpanID = 0;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canSel != 1) {throw "no checkBox!";}
			var rowCount = tObjInstance.mulLineCount;
			selNo = tObjInstance.getSelNo(tObjInstance) -1;
			lineSpanID = tObjInstance.findSpanID(selNo, tObjInstance);	//得到该行的spanID
			tObjInstance.deleteOne(tObjInstance.instanceName, lineSpanID, tObjInstance);
		}catch(ex){
			alert("在MulLine.js-->delRadioTrueLine函数中发生异常:" + ex);
		}
	};
/**
 *	返回指定行的id(外部/内部调用)
 *	@param	:	cIndex:  行号,从0行开始
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	string:	行id
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
 **/
 	$.fn.FindSpanID = function(cIndex, cObjInstance){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(cIndex < 0 || cIndex >= tObjInstance.mulLineCount){
				throw"在MulLine.js-->FindSpanID函数中指定了错误的行:" + cIndex;}
			if(tObjInstance.mulLineCount > 0 && cIndex < tObjInstance.mulLineCount){
				tReturn = $(tObjInstance.initTable).find("tbody tr:eq(" + cIndex + ")").attr("id");
			}
		}catch(ex){
			alert("在MulLine.js-->findSpanID函数中发生异常:" + ex);
		}
		return tReturn;
	};
/**
 *	动态修改(或新增)指定列的属性(外部调用)
 *	@param	:	cIndex:  行号,从0行开始
 *	@param	:	arrCol:  指定列的新属性数组
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	none
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：arraySave
 *				使用的mulLine属性和html对象：
 **/
 	$.fn.UpdateField = function(cIndex, arrCol, cObjInstance){
		var tObjInstance;var i = cIndex;var $this = $(this);
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			tObjInstance.arraySave[i] = arrCol;
			$this.SetFieldValue(tObjInstance.instanceName, tObjInstance.arraySave, tObjInstance);
			$this.LoadPage(tObjInstance.instanceName, tObjInstance);
		}catch(ex){
			alert("在MulLine.js-->UpdateField函数中发生异常:" + ex);
		}
	};
/**	
 *	根据配置和参数,用于生成表的两个模板,thead和tbody的样式,属性,事件,方法等,——由于之前使用的参数较多,此处仍引入这些参数,但不使用全部参数
 *	@param  : string 页面名
 *	@param  : array	二维数组,表格每列的属性
 *	@param  : cObjInstance: mulLine对象
 *	@param  : array	数组,表格每列的是否可编辑
 *	@return : none
 *	
 *	@logic	: 	第一部分用于生成表头的模板对象,赋给mulLineTextTitle	
 *				第二部分生成表内容的模板对象,赋给mulLineText	
 *				先生成基本对象（td，input）,根据iarray生成相应的属性,最后将生成好的模板转换成字符串赋值给两个变量,提供给生成表格使用
 *	@global	: 影响到的mulLine属性和html对象：mulLineText,mulLineTextTitle,tableWidth,colCount
 *				使用的mulLine属性和html对象：canSel,canChk,windowWidth,mulLineNum
 							selBoxEventFuncName,selBoxEventFuncParm,chkBoxEventFuncName
 **/
    $.fn.SetFieldValue = function(strPageName,iarray,cObjInstance,eArray){
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		tObjInstance.mulLineTextTitle	= document.createElement('tr');		
		tObjInstance.mulLineText	= document.createElement('tr');
		
		var $mulLineText = $(tObjInstance.mulLineText);
		var $mulLineTextTitle = $(tObjInstance.mulLineTextTitle);//使用变量保存jquery对象，提高效率
		
		$mulLineText.attr("id","span$PageName$$SpanId$");
		for(k=0;k<iarray.length;k++){
			$mulLineTextTitle.append("<th></th>");
			$mulLineText.append("<td><div><input /></div></td>");
		}
		//修改mulLine属性和html对象,列数
		fieldCount = iarray.length;
		tObjInstance.colCount = fieldCount;
		
		//初始化数据
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
		
//计算表宽度复制给tObjInstance.tableWidth,计算rate,使表各列拉长,总和页面同宽
		var rate = 1 / tObjInstance.mulLineNum;
		if(tObjInstance.tableWidth =="" ||tObjInstance.tableWidth ==null)tObjInstance.tableWidth= 0;
		if(tObjInstance.tableWidth == 0){
			$.each(iarray,function(i,td_config){
				tempText1 = td_config[1];
				if(td_config[3]!=3)tObjInstance.tableWidth = tObjInstance.tableWidth + parseInt(tempText1);//加上各个显示的列宽
			});
			if(tObjInstance.canSel == 1 || tObjInstance.canChk == 1)tObjInstance.tableWidth +=30;//加上box宽
			if(tObjInstance.hiddenSubtraction == 0){tObjInstance.tableWidth +=22;}
			tObjInstance.tableWidth +=30;//对总宽度微调
		}
		if (tObjInstance.tableWidth < tObjInstance.windowWidth){
			rate = (tObjInstance.windowWidth / tObjInstance.tableWidth) / tObjInstance.mulLineNum;
		}
//遍历
		$.each(iarray,function(i,td_config){
//td_config对应以上的各列属性,变量名tempText是从原来的mulLine复制过来
			tempText0 = td_config[0];	//索引列的名称
			tempText1 = td_config[1];	//索引列宽
            tempText2 = td_config[2];	//索引列最大允许值
            tempText4 = td_config[3];	//索引列是否允许输入,隐藏,代码选择
            tempText5 = td_config[4];	//代码引用(数据从后台数据库取)--代码名
            tempText6 = td_config[5];	//代码引用对应的多列 (数据从后台数据库取)
            tempText7 = td_config[6];	//代码引用对应的多列的内部值(数据从后台数据库取)
            tempText8 = td_config[7];	//对应的外部的js函数（参数是当前行的spanID,你传入的数组）
            tempText9 = td_config[8];	//对应的外部的js函数的第2个参数
            tempText10 = td_config[9];	//格式校验
            tempText11 = td_config[10];	//代码引用(数据从前台传入)--代码名
            tempText12 = td_config[11];	//代码引用(数据从前台传入)
            tempText13 = td_config[12];	//代码引用(数据从前台传入)--排列多列
            tempText14 = td_config[13];	//代码引用(数据从前台传入)
            tempText15 = td_config[14];	//用户设置该列常量
            tempText16 = td_config[15];	//设置当前列的双击下拉显示依赖于其它控件或列的名字
            tempText17 = td_config[16];	//设置当前列的双击下拉显示依赖于其它控件的值
            tempText18 = td_config[17];	//设置当前列的双击下拉显示依赖于其它列的值
            tempText19 = td_config[18];	//设置当前列的双击下调整弹出下拉框的宽度（专为codeSelect度身打造:第8个参数）
            tempText20 = td_config[19];	//设置当前列的双击下强制刷新codeSelect数据源（专为codeSelect度身打造:第7个参数）
			tempText21 = td_config[20];	//此处不用,用于setRowColData函数（判断该参数,是否将编码转为中文）。
			tempText22 = td_config[21]; //对齐方式,左中右
			tempText23 = td_config[22]; //用来指定多币种货币的类型
			tempText24 = td_config[23]; //用来指定货币框是否是只读
			tempText25 = td_config[24]; //设置当前列失去焦点触发的外部函数
			tempText26 = td_config[25]; //设置当前列失去焦点触发的外部函数的参数列
			
			if (tempText19 == undefined){tempText19 = null;}
			if (tempText20 == undefined){tempText20 = null;}
			if (tempText21 == undefined){tempText21 = null;}
			if (tempText15 == null){tempText15 = "";}
			
			$mulLineTextTitleThI = $mulLineTextTitle.find("th").eq(i);
			$mulLineTextTdI = $mulLineText.find("td").eq(i);
//第一部分
			var orderStr = "<span></span>";
			if(tempText10 != null && tempText10 != ""){
				var verifyStr = "<input type=hidden name=" + strPageName + "verify" + i + " value='" + tempText10 + "'>";
				$mulLineTextTitleThI.html(tempText0+verifyStr+orderStr);//生成帶列名
			}else{
				$mulLineTextTitleThI.html(tempText0+orderStr);//生成列名
			}
			if(tempText4==3){
				$mulLineTextTitleThI.hide();
				$mulLineTextTdI.hide();//隐藏
			} else if (tempText4 == '6') {
				$mulLineTextTdI.attr("onclick", calendar);
			}
//			$mulLineTextTitleThI.attr("class", "mulinetitlenew");
			$mulLineTextTitleThI.attr("width",function(index,OldValue){
			//生成列宽,返回0会导致下一个数组出错,返回""空字符不会
				td_width = parseInt(tempText1.substr(0, tempText1.toLowerCase().indexOf("px")));//flexigrid插件不需要输入px
				td_width = Math.round(td_width * rate);
				if(td_width == 0)
				{$mulLineTextTitleThI.hide();$mulLineTextTdI.hide();td_width="";}
				return td_width;
			});
			// 生成单击排序事件
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
			
			
//第二部分,先生成属性后生成事件
			
			switch(tempText4) 
			{//生成input_type,jquery不允许直接修改type,采用html的方法修改
				case 4:
					input_type= $mulLineTextTdI.html();
					input_type = input_type.replace(/INPUT/,"input type=\"password\" ");//input大写
				break;
				default:
					input_type= $mulLineTextTdI.html();
					input_type = input_type.replace(/INPUT/,"input type=\"text\" ");//input大写
				break;
			}
			$mulLineTextTdI.html(input_type);

			switch(i) 
			{//生成name,直接修改name在部分情况下会失败,采用html的方法修改
				case 0:
					tmp_input_name = "input name='" + strPageName + "No" + "'";
					input_name= $(tObjInstance.mulLineText).find("td").eq(i).html();
					input_name = input_name.toLowerCase().replace(/input/,tmp_input_name);//input小写
				break;
				default:
	//				var eleHTML = $mulLineTextTdI.find("input").get(0);
	//				eleHTML.setAttribute("name",strPageName+i);
					tmp_input_name = "input name='" + strPageName + i + "'";
					input_name= $(tObjInstance.mulLineText).find("td").eq(i).html();
					input_name = input_name.toLowerCase().replace(/input/,tmp_input_name);//input小写
				break;
			}
			$(tObjInstance.mulLineText).find("td").eq(i).html(input_name);//alert(td_width);
			//生成div宽度，可提高flexigrid的效率，需将其cellEffect设置为"on"，不可生成td宽度，会导致无法改变宽度
			$mulLineTextTdI.find("div").attr("style","width:"+td_width+"px");	
			$mulLineTextTdI.find("input").attr("id",function(index,oldValue){
			//生成id
				if(i>0){input_id = strPageName + i + "r$row$";}else {input_id = strPageName + "No$row$";}
				return input_id;
			}).attr("maxlength",function(index,oldValue){
			//生成maxlength
				max_length = tempText2;
				return	max_length;
			});
			if(tempText15 != ""){
			$mulLineTextTdI.find("input").attr("value",function(index,oldValue){
			//生成默认value
				return tempText15;
			});
			}
			
			$mulLineTextTdI.attr("class",function(index,oldValue){
			//生成td_class
				return	"muline";
			});
			$mulLineTextTdI.find("input").attr("class",function(index,oldValue){
			//生成input_class
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
			//生成input_style
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
					input_style += "width:" + (td_width) + "px;";//因为暂不支持原先多币种方案
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
				//生成readonly
					return	"readonly";
				});
			}
			
		//自定义的属性
			$mulLineTextTdI.find("input").attr("CodeData",function(index,oldValue){
			//生成codedata
				if (tempText5 == null && tempText6 == null && tempText7 == null && tempText8 == null && tempText9 == null){
                	if (tempText12 != null && tempText12 != "" && tempText11 != null && tempText11 != ""){
                		code_data = tempText12;
                	}//end 12 11
                }//end 56789
				return code_data;
			}).attr("amtcol",function(index,oldValue){
			//生成amtcol
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
			//生成moneytype
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
			//生成show_style
				var show_style = "";
				if( tempText4 == "7")
				{show_style += "Style='width:" + (tempText1) + "'";}
				else if( tempText4 == "8")
				{show_style +="Style='width:" + (td_width-20) + "px" + "'";}
				return show_style;
			}).attr("ShowClass",function(index,oldValue){
			//生成showclass
				show_class = "";
				if( tempText4 == "7" || tempText4 == "8"){show_class += "mulcommon";}
				return show_class;
			}).attr("verify",function(index,oldValue){
			//生成input_verify
				if (tempText10 != null && tempText10 != ""){
					input_verify = tempText10;
				}else{
					input_verify = "";
				}
				return input_verify;
			});		
			/*
			 *	以上生成属性,以下生成事件
			 */
			 
			if(typeof(tObjInstance.showTitle)=="function"){
			//生成onmouseover相关事件
								
			}else if(tObjInstance.showTitle == 1){
				$mulLineTextTdI.find("input").attr("onmouseover",function(index,oldValue){
					event_on_mouseover="";
					//temp4不为4和7、8时,显示title为内容
					event_on_mouseover = tObjInstance.instanceName + ".setTitle(this)";
					return event_on_mouseover;
				});
			}
			   $mulLineTextTdI.find("input").attr("onclick",function(index,oldValue){
			//生成单击相关事件
				event_on_click = "";
				//_CalcFocusRowColNo,设置最近焦点mulline.lastFocusRowNo 和lastFocusColNo
				event_on_click = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this)";
				
				if(tempText4 == 8){
					$mulLineTextTdI.find("input").filter(".multiDatePicker").css("width",td_width-20);
					$mulLineTextTdI.find("input").filter(".multiDatePicker").after("<img src='../common/laydate/skins/default/icon.png'></img></a>");
					event_on_click ="myDate(" + tObjInstance.instanceName + ", this)";
					}
				
				return event_on_click;
			}).attr("ondblclick",function(index,oldValue){
			//生成双击相关事件
				event_on_dblclick="";
				if(tempText5 == null){
					if (tempText8 != null && tempText8 != ""){
						event_on_dblclick = tempText8 + "('span$PageName$$SpanId$'," + tempText9 + ")" + ";";
					}
				}else{//tempt5!=null
                	if (tempText6 == null || tempText7 == null){//如果代码引用只应用在1列上
                		if (tempText16 == null){//如果不根据其它控件或列做判断
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
							if (tempText17 != null && tempText17 != ""){//如果根据其它空间的值做判断,确定参数
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
								if (tempText18 != null && tempText18 != ""){//如果根据其它列的值做判断,取指定列的值
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
						//如果代码引用应用在多列上,次方法目前好像行不通
						var arrColName = "["; //对应列的集合的格式
						var arrCodeName = "["; //对应代码选择的项的名称
						 //分割数组，得到对应列数的数组
                            var arrayField = tempText6.split(FIELDDELIMITER);
                            var arrayCode = tempText7.split(FIELDDELIMITER);
                            //格式化代码选择数组 从 0|1 转到[0,1]
                            for (var m = 0; m < arrayCode.length; m++)
                            {
                                arrCodeName = arrCodeName + arrayCode[m];
                                if (m != arrayCode.length - 1)
                                {
                                    arrCodeName = arrCodeName + ",";
                                }
                            }
                            //格式化列对象数组 从0|1 转到[列对象，列对象]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') 即对应spanID的列对象
                                //arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                                arrColName = arrColName + "document.getElementById('$PageName$"+arrayField[n]+"r$SpanId$')";
                                if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
						arrCodeName = arrCodeName + "]";
						arrColName = arrColName + "]";
						
						if (tempText16 == null || tempText16 == ""){//如果不根据其它控件或列做判断
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
                		if (tempText13 == null || tempText14 == null){//只对应当前单列代码选择
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
                			//对应多列或者不是当前列代码选择
                			var arrColName = "["; //对应列的集合的格式
							var arrCodeName = "["; //对应代码选择的项的名称
							 //分割数组，得到对应列数的数组
                            var arrayField = tempText13.split(FIELDDELIMITER);
                            var arrayCode = tempText14.split(FIELDDELIMITER);
                            //格式化代码选择数组 从 0|1 转到[0,1]
                            for (var m = 0; m < arrayCode.length; m++)
                            {
                                arrCodeName = arrCodeName + arrayCode[m];
                                if (m != arrayCode.length - 1)
                                {
                                    arrCodeName = arrCodeName + ",";
                                }
                            }
                            //格式化列对象数组 从0|1 转到[列对象，列对象]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') 即对应spanID的列对象
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
			//生成change相关事件
				event_on_change = "";
				if(tempText5 == null){
					if (tempText22 != null && tempText22 != ""){
						event_on_change = tempText22 + "('span$PageName$$SpanId$'," + tempText9 + ")" + ";";
					}
				}
				return event_on_change;
			}).attr("onfocus",function(index,oldValue){
			//生成onFocus相关事件
				event_on_focus = "";
				//最近焦点mulline.lastFocusRowNo 和lastFocusColNo
				//修改input长度
				event_on_focus = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this);this.style.width=(this.parentNode.style.width-20);";
				return event_on_focus;
			}).attr("onkeyup",function(index,oldValue){
			//生成onkeyup相关事件
				event_on_keyup = "";
				if(tempText5 == null){
					if (tempText8 != null && tempText8 != ""){
						event_on_keyup += "if(event.keyCode=='13'){" + tempText8 + "('span$PageName$$SpanId$'," + tempText9 + ")" + "};";
					}
					if (tempText22 != null && tempText22 != ""){
						event_on_keyup += "if(event.keyCode=='13'){" + tempText22 + "('span$PageName$$SpanId$'," + tempText9 + ")" + "};";
					}
				}else{//tempt5!=null
                	if (tempText6 == null || tempText7 == null){//如果代码引用只应用在1列上
                		if (tempText16 == null){//如果不根据其它控件或列做判断
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
							if (tempText17 != null && tempText17 != ""){//如果根据其它空间的值做判断,确定参数
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
								if (tempText18 != null && tempText18 != ""){//如果根据其它列的值做判断,取指定列的值
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
						//如果代码引用应用在多列上,次方法目前好像行不通
						var arrColName = "["; //对应列的集合的格式
						var arrCodeName = "["; //对应代码选择的项的名称
						 //分割数组，得到对应列数的数组
                            var arrayField = tempText6.split(FIELDDELIMITER);
                            var arrayCode = tempText7.split(FIELDDELIMITER);
                            //格式化代码选择数组 从 0|1 转到[0,1]
                            for (var m = 0; m < arrayCode.length; m++)
                            {
                                arrCodeName = arrCodeName + arrayCode[m];
                                if (m != arrayCode.length - 1)
                                {
                                    arrCodeName = arrCodeName + ",";
                                }
                            }
                            //格式化列对象数组 从0|1 转到[列对象，列对象]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') 即对应spanID的列对象
                                //arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                            	arrColName = arrColName + "document.getElementById('$PageName$"+arrayField[n]+"r$SpanId$')";
                                if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
						arrCodeName = arrCodeName + "]";
						arrColName = arrColName + "]";
						
						if (tempText16 == null || tempText16 == ""){//如果不根据其它控件或列做判断
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
                
                //如果前面的数组第5,6,7,8,9项是空,那么判断第10项即代码引用是否可用
                if (tempText5 == null && tempText6 == null && tempText7 == null && tempText8 == null && tempText9 == null){
                	if (tempText12 != null && tempText12 != "" && tempText11 != null && tempText11 != ""){
                		if (tempText13 == null || tempText14 == null){//只对应当前单列代码选择
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
                			//对应多列或者不是当前列代码选择
                			var arrColName = "["; //对应列的集合的格式
							var arrCodeName = "["; //对应代码选择的项的名称
							 //分割数组，得到对应列数的数组
                            var arrayField = tempText13.split(FIELDDELIMITER);
                            var arrayCode = tempText14.split(FIELDDELIMITER);
                            //格式化代码选择数组 从 0|1 转到[0,1]
                            for (var m = 0; m < arrayCode.length; m++)
                            {
                                arrCodeName = arrCodeName + arrayCode[m];
                                if (m != arrayCode.length - 1)
                                {
                                    arrCodeName = arrCodeName + ",";
                                }
                            }
                            //格式化列对象数组 从0|1 转到[列对象，列对象]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') 即对应spanID的列对象
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
			//生成onblur相关事件
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
				$mulLineTextTdI.hide();//隐藏
			}
		});

//单选、多选符号判断,在模板内部的前端加入
		if(tObjInstance.canSel == 1){
			$mulLineTextTitle.prepend("<th class='mulinetitle' width='30'>&nbsp;</th>");

			$mulLineText.prepend("<td class='muline'><div style='width:30px'><input type=hidden name='Inp" + strPageName + "Sel' id='Inp" + strPageName
				 + "Sel$SpanId$' value=0 /><input type='radio' class='mulcommon' name='"+strPageName+"Sel' id='"+strPageName+"Sel$SpanId$' /></div></td>");
			$mulLineText.find("td").eq(0).find("input").eq(1).attr("onclick",function(index,oldValue){
			//单选按钮单击事件
				event_on_click = "";
			//_CalcFocusRowColNo,设置最近焦点mulline.lastFocusRowNo 和lastFocusColNo
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
			//多选全选按钮单击事件
				event_on_click = "";
				//_CalcFocusRowColNo,设置最近焦点mulline.lastFocusRowNo 和lastFocusColNo
				event_on_click = "_CalcFocusRowColNo(" + tObjInstance.instanceName + ", this);";
				event_on_click += strPageName + ".checkAll(this," + fieldCount + ");";
				if(tObjInstance.chkBoxAllEventFuncName != null && tObjInstance.chkBoxAllEventFuncName != "")
					event_on_click += tObjInstance.chkBoxAllEventFuncName + "(this.checked,this);";
				return event_on_click;
			});
				 
			$mulLineText.prepend("<td class='muline'><div style='width:30px'><input type='hidden'name='Inp" + strPageName + "Chk' id='Inp" + strPageName
				 + "Chk$SpanId$' value='0' /><input type='checkbox' name='"+strPageName+"Chk' id='"+strPageName+"Chk$SpanId$' /></div></td>");
			$mulLineText.find("td").eq(0).find("input").eq(1).attr("onclick",function(index,oldValue){
			//多选按钮单击事件
				event_on_click = "";
				//_CalcFocusRowColNo,设置最近焦点mulline.lastFocusRowNo 和lastFocusColNo
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
//是否生成减一行按钮
		if(tObjInstance.hiddenSubtraction == 0){//不隐藏
			tStatus = "" ;
			if(tObjInstance.locked == 1)tStatus = "disabled" ;
			$mulLineTextTitle.append("<th class='mulinetitle' disabled width='23'>&nbsp; - &nbsp;</th>");
			
			$mulLineText.append("<td class='muline'><input class=button type=button " + tStatus + " value='  -  ' name='$PageName$Del' "
				+ "id='$PageName$Del$row$' />" + "<input type='hidden' name='$PageName$SpanID' value='span$PageName$$SpanId$' /></td>");
			$mulLineText.find("td").last().find("input").eq(0).attr("onclick",function(index,oldValue){
			//单选按钮单击事件
				event_on_click = "";
				event_on_click = "return ";
				//删除之前触发事件
				if(tObjInstance.delEventFuncName != null && tObjInstance.delEventFuncName != "")
					event_on_click += tObjInstance.delEventFuncName + "('span$PageName$$SpanId$','"
						 + tObjInstance.delEventFuncParm + "');";
				event_on_click += strPageName + ".deleteOne(\"$PageName$\",'span$PageName$$SpanId$');";
				return event_on_click;
			});
		}else{
		}	
//为了提高运行效率,将最终生成的mullineText和mulLineTextTitle转换成字符串
		tObjInstance.mulLineTextTitle = tObjInstance.mulLineTextTitle.outerHTML;
		tObjInstance.mulLineText = tObjInstance.mulLineText.outerHTML;
	};
/**********************************************************************************************************************************/
/****************************************以上为生成table的主要函数，以下为实现其他细节功能的函数*********************************************/	
/**********************************************************************************************************************************/
/**
 *	设置指定行列的数据(外部/内部调用)
 *	@param	:	cRow:  行(从0开始)
 *	@param	:	cCol:  列
 *	@param	:	cData: 数据
 *	@param	:	cObjInstance Muline对象,外部调用时为空；内部调用时不能为空
 *					顶级函数一般是外部调用,则cObjInstance为空,内部tObjInstance=this;
 *					则调用子函数时,将当前对象this作为cObjInstance参数传入调用子函数
 *	@return ：	没有
 *	
 *	@logic	: 	用输入的行和列进行定位，赋值
 *	@global	: 	影响到的mulLine属性和html对象：initTable
 *				使用的mulLine属性和html对象：instanceName
 **/
	$.fn.SetRowColData = function(cRow, cCol, cData, cObjInstance){
		var tReturn = false;
		var tObjInstance,tStr;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cData == null){cData = "";}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->setRowColData() 时指定了错误的行:" + cRow}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "在MulLine.js-->setRowColData() 时指定了错误的列:" + cCol;}
		try{
			var newData = cData;//应该加一个字符串判断
			newData = cData.replace("\r\n", "");
			newData = cData.replace("'", "\\'");
			document.getElementsByName(tObjInstance.instanceName+cCol)[cRow].value = newData;
			$("#" + tObjInstance.instanceName + cCol + "r" + cRow,tObjInstance.initTable).val(newData);
			tReturn = true;
		}catch(ex){
			alert("在 MulLine.js --> SetRowColData 函数中发生异常：" +  ex + "\n" +tStr);
		}
		return tReturn;
	};
/**
 *	获得指定行列的数据(外部/内部调用)
 *	@param：		cRow:  行
 *				cCol:  列
 *				cObjInstance Muline对象,外部调用时为空；内部调用时不能为空
 *				顶级函数一般是外部调用,则cObjInstance为空,内部tObjInstance=this;
 *				则调用子函数时,将当前对象this作为cObjInstance参数传入调用子函数
 *	@return：		string:指定行列值
 *	
 *	@logic	:	 用输入的行和列进行定位，取值
 *	@global	:	 影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：instanceName,mulLineCount
 **/
	$.fn.GetRowColData = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->getRowColData() 时指定了错误的行:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "在MulLine.js-->getRowColData() 时指定了错误的列:" + cCol;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).val();
		}catch(ex){
			alert("在 MulLine.js --> GetRowColData 函数中发生异常：" +  ex + "\n");
		}
		return tReturn;
	};
	
//==============================================================================

/**
 * 方法：得到指定行列的Element对象
 * 输入：cRow:  行  从0开始
 *       cCol:  列  从1开始
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用子函数时，将当前对象this作为cObjInstance参数传入调用子函数
 * 输出：指定行，列的Element对象
 */
	$.fn.GetRowColElemt = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->getRowColData() 时指定了错误的行:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "在MulLine.js-->getRowColData() 时指定了错误的列:" + cCol;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow);
		}catch(ex){
			alert("在 MulLine.js --> GetRowColData 函数中发生异常：" +  ex + "\n");
		}
		return tReturn;
	}

/**
 * 方法：应某些需求的需要，同时为了更方便的美化界面，增加方法获取某一行列的classname
 * 输入：cRow:  行  从0开始
 *       cCol:  列  从1开始
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用子函数时，将当前对象this作为cObjInstance参数传入调用子函数
 * 输出：指定行，列的Element对象
 */
	$.fn.GetRowColClass = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->getRowColData() 时指定了错误的行:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "在MulLine.js-->getRowColData() 时指定了错误的列:" + cCol;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).parent().parent().className;
		}catch(ex){
			alert("在 MulLine.js --> GetRowColData 函数中发生异常：" +  ex + "\n");
		}
		return tReturn;
	}

/**
 * 方法：应某些需求的需要，同时为了更方便的美化界面，增加方法获取某一行列的classname
 * 输入：cRow:  行  从0开始
 *       cCol:  列  从1开始
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用子函数时，将当前对象this作为cObjInstance参数传入调用子函数
 * 输出：指定行，列的Element对象
 */
	$.fn.SetRowColClass = function(cRow, cCol, cData, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(typeof(cCol)=='string'){cCol=tObjInstance.colIDtoIndex(cCol);if(cCol==-1)return null;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->getRowColData() 时指定了错误的行:" + cRow;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "在MulLine.js-->getRowColData() 时指定了错误的列:" + cCol;}
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
			alert("在 MulLine.js --> GetRowColData 函数中发生异常：" +  ex + "\n");
		}
		return tReturn;
	}

//==============================================================================

	
/**
 *	获得指定行的数据(外部/内部调用)
 *	@param	：	cRow:  行
 *	@param	：	cObjInstance Muline对象,外部调用时为空；内部调用时不能为空
 *				顶级函数一般是外部调用,则cObjInstance为空,内部tObjInstance=this;
 *				则调用子函数时,将当前对象this作为cObjInstance参数传入调用子函数
 *	@return	：	array:指定行值的数组
 *	
 *	@logic	: 	用输入的行进行定位tr，循环取满足id条件的input值
 *	@global	:	 影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：instanceName,以及tr的id="'span'+instanceName+cRow"和input的id="instanceName+cCol+'r'+cRow"
 **/
	$.fn.GetRowData = function(cRow, cObjInstance){
		var tReturn = new Array();
		var tStrInput,n=0,tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->GetRowData() 时指定了错误的行:" + cRow;}
		try{
			var id = "#" + tObjInstance.instanceName;id2 = "r" + cRow;
			for(var cCol = 1, size = tObjInstance.colCount;cCol < size;cCol++){
				tStrInput = id + cCol + id2;
				tReturn[n++] = $(tStrInput).val();
			}
		}catch(ex)
		{
			alert("在 MulLine.js --> GetRowData 函数中发生异常：" +  ex + "\n" +tStr);
		}
		return tReturn;
	};
/**
 *	清空数据(外部/内部调用)
 *	@param	：	strPageName:  对象名称
 *	@param	：	cObjInstance Muline对象,外部调用时为空；内部调用时不能为空
 *				顶级函数一般是外部调用,则cObjInstance为空,内部tObjInstance=this;
 *				则调用子函数时,将当前对象this作为cObjInstance参数传入调用子函数
 *	@return	：	
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：initTable
 *				使用的mulLine属性和html对象：instanceName
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
			alert("在 MulLine.js --> clearData 函数中发生异常：" +  ex);
		}
	};
/**
 *	指定行列的获得焦点(外部/内部调用)
 *	@param	:	cRow:  行
 *	@param	:	cCol:  列,默认为1
 *	@param	:	cObjInstance Muline对象,外部调用时为空；内部调用时不能为空
 *				顶级函数一般是外部调用,则cObjInstance为空,内部tObjInstance=this;
 *				则调用子函数时,将当前对象this作为cObjInstance参数传入调用子函数
 *	@return ：	boolean
 *	
 *	@logic	: 	用输入的行和列进行定位
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
 **/
	$.fn.SetFocus = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance,tStr;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){alert("在MulLine.js-->setRowColData() 时指定了错误的行:" + cRow);return tReturn;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){
			throw "在MulLine.js-->setRowColData() 时指定了错误的列:" + cCol;
		}else if(cCol == "" || cCol ==  null){
			cCol = 1;//默认为1
		}
		try{
			tStr = "document.getElementsByName('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].focus();";
			eval(tStr);
			tReturn = true;
		}catch(ex){
			alert("在 MulLine.js --> GetRowData 函数中发生异常：" +  ex + "\n" +tStr);
		}
		return tReturn;
	};
/**
 *	判断指定行列的是否获得焦点(外部/内部调用)
 *	@param	:	cRow:  行,从0开始
 *	@param	:	cCol:  列,默认为1
 *	@param	:	cObjInstance Muline对象,外部调用时为空；内部调用时不能为空
 *				顶级函数一般是外部调用,则cObjInstance为空,内部tObjInstance=this;
 *				则调用子函数时,将当前对象this作为cObjInstance参数传入调用子函数
 *	@return ：	boolean
 *	
 *	@logic	: 	用输入的行和列判断lastFocusRowNo和lastFocusColNo是否是该行列
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
 **/
	$.fn.GetFocus = function(cRow, cCol, cObjInstance){
		var tReturn = false;
		var tObjInstance,tStr;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){alert("在MulLine.js-->setRowColData() 时指定了错误的行:" + cRow);return tReturn;}
		if(cCol < 0 || cCol >= tObjInstance.colCount){
			throw "在MulLine.js-->setRowColData() 时指定了错误的列:" + cCol;
		}else if(cCol == "" || cCol ==  null){
			cCol = 1;//默认为1
		}
		try{
			if(tObjInstance.lastFocusRowNo == cRow && tObjInstance.lastFocusColNo == cCol){
				tReturn = true;
			}
		}catch(ex){
			alert("在 MulLine.js --> _GetFocus 函数中发生异常：" + ex);
		}
		return tReturn;
	};
/**
 *	单选按钮单击事件(外部/内部调用)
 *	@param	:	cObjSel:  radio对象
 *	@param	:	colcount:  列数
 *	@return ：	none
 *	
 *	@logic	: 	修改被选中的单选行号
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
				$(selName+i).val(0);//归零
				if ($("#"+tStrPageName+"Sel"+i).attr("checked")){
					pageNo = i;
					$(selName+i).val(1);//设置被选中节点值
				}
        	}
	        if (pageNo != null && pageNo != undefined){
				ele.setAttribute("PageNoRadio",pageNo);//设置此次被选中行
			}
			//修改样式
			if((oldPageNo!=undefined&&oldPageNo!=null&&oldPageNo!="")||oldPageNo=="0"){
				//原被选择中行样式
				$("#"+tStrPageName +"Sel" + oldPageNo).removeClass().addClass("mulreadonlyt");
				$("#"+tStrPageName +"No" + oldPageNo).removeClass().addClass("mulreadonlyt");
				for(j=1;j<fieldCount;j++){
					$(this).ChangeSelStyle(this.formName,tStrPageName,j,oldPageNo,this,"cancel");
				}
				$("#span"+ tStrPageName + oldPageNo).removeClass("trSelected");
			}
			//现被选择中行样式
			$("#"+tStrPageName +"Sel" + pageNo).removeClass().addClass("mulnotreadonlyt");
			$("#"+tStrPageName +"No" + pageNo).removeClass().addClass("mulnotreadonlyt");
			$("#span"+ tStrPageName + pageNo).addClass("trSelected");
			for(j=1;j<fieldCount;j++){
				$(this).ChangeSelStyle(this.formName,tStrPageName,j,pageNo,this,"select");
			}
		}catch(ex){
			alert("在 MulLine.js --> radioClick 函数中发生异常：" +  ex);
		}
	};
/**
 *	多选按钮单击事件(外部/内部调用)
 *	@param	:	cObjSel:  CheckBox对象
 *	@param	:	colcount:  列数
 *	@return ：	none
 *	
 *	@logic	: 	修改被选中的多选行号
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
			tChkId = tChkId.substring(tGridChkId.length);//取出选中行序号
			if(cObjChk.checked==false){
				$("#Inp"+tGridChkId+tChkId).val(0);//设置checkbox的兄弟节点值
				$("#"+tObjInstance.instanceName + "Chk"+tChkId).removeClass().addClass("mulreadonlyt");
				$("#"+tObjInstance.instanceName + "No"+tChkId).removeClass().addClass("mulreadonlyt");
				for(j=1;j<fieldCount;j++){
					$().ChangeSelStyle(tObjInstance.formName,tObjInstance.instanceName,j,tChkId,tObjInstance,"cancel");
				}
				$("#span"+ tObjInstance.instanceName +tChkId).removeClass("trSelected");
			}else if(cObjChk.checked==true){
				$("#Inp"+tGridChkId+tChkId).val(1);//设置checkbox的兄弟节点值
				$("#"+tObjInstance.instanceName + "Chk"+tChkId).removeClass().addClass("mulnotreadonlyt");
				$("#"+tObjInstance.instanceName + "No"+tChkId).removeClass().addClass("mulnotreadonlyt");
				for(j=1;j<fieldCount;j++){
					$().ChangeSelStyle(tObjInstance.formName,tObjInstance.instanceName,j,tChkId,tObjInstance,"select");
				}
				$("#span"+ tObjInstance.instanceName +tChkId).addClass("trSelected");
			}
		}catch(ex){
			alert("在 MulLine.js --> checkBoxClick 函数中发生异常：" +  ex);
		}
	};
/**
 *	获得被选择的行号(外部/内部调用)
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	int:	行号：从1开始，0为没有被选中行
 *	
 *	@logic	: 	遍历
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
			alert("在 MulLine.js --> getSelNo 函数中发生异常：" +  ex);
		}
	};
/**
 *	判断指定行是否被选中(外部/内部调用)
 *	@param	:	cIndex:  行号从0开始
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	boolean
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
 **/
	$.fn.GetChkNo = function(cIndex, cObjInstance){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canChk != 1) {throw "no checkBox!";}
			if(cIndex < 0 || cIndex >= tObjInstance.mulLineCount){throw "在MulLine.js-->getChkNo函数中指定了错误的行:" + cIndex;}
			if(tObjInstance.mulLineCount > 0 && cIndex < tObjInstance.mulLineCount){
				if(document.getElementsByName(tObjInstance.instanceName + "Chk")[cIndex].checked == true){
					tReturn = true;
				}
			}
		}catch(ex){
			alert("在MulLine.js-->getChkNo函数中发生异常:" + ex);
		}
		return tReturn;
	};
/**
 *	获得被选择的行号(外部/内部调用)
 *	@param	:	cObjCheckBox:  checkbox对象
 *	@return ：	int:	行数
 *	
 *	@logic	: 	遍历
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
			alert("在MulLine.js-->checkAll函数中发生异常:" + ex);
		}
	};
/**
 *	获得被选择的行号(外部/内部调用)
 *	@param	:	cObjInstance:  mulline对象
 *	@return ：	int:	行数
 *	
 *	@logic	: 	遍历
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
			alert("在MulLine.js-->checkBoxAll函数中发生异常:"+ ex);
		}
	};
/**
 *	获得被选择的行号(外部/内部调用)
 *	@param	:	cObjInstance:  mulline对象
 *	@return ：	int:	行数
 *	
 *	@logic	: 	遍历
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
			alert("在MulLine.js-->checkBoxAllNot函数中发生异常:" + ex);
		}
	};
/**
 *	选中指定行(外部/内部调用)
 *	@param	:	cIndex:  行号,从1行开始（和其他情况不同）
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	boolean
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
 **/
	$.fn.CheckBoxSel = function(cIndex, cObjInstance){
		var tObjInstance;
		var tReturn = false;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		try{
			if(tObjInstance.canChk != 1) {alert("no checkBox!");return tReturn;}
			if(cIndex <= 0 || cIndex > tObjInstance.mulLineCount){alert("在MulLine.js-->getChkNo函数中指定了错误的行:" + cIndex);return tReturn;}
			if(tObjInstance.mulLineCount > 0 && cIndex <= tObjInstance.mulLineCount){
				document.getElementsByName(tObjInstance.instanceName + "Chk")[cIndex-1].checked = true;
				document.getElementsByName("Inp" + tObjInstance.instanceName + "Chk")[cIndex-1].value = 1;
				tReturn = true;
			}
		}catch(ex){
			alert("在 MulLine.js --> checkBoxSel 函数中发生异常：" +  ex);
		}
		return tReturn;
	};
/**
 *	取消选中指定行(外部/内部调用)
 *	@param	:	cIndex:  行号,从1行开始（和其他情况不同）
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	boolean
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
				case "rowErr":alert("在MulLine.js-->checkBoxNotSel函数中指定了错误的行:");break;
				default:alert("在 MulLine.js --> checkBoxNotSel 函数中发生异常：" +  ex);break;
			}
		}
		return tReturn;
	};
/**
 *	选中行排序(外部/内部调用)
 *	@param	:	string:	列名
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	boolean
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
				case "init":alert("请先查询！");break;
				default:alert("在 MulLine.js --> OrderByName 函数中发生异常：" +  ex);break;
			}
		}
		
		//afterOrderByName(colName, cObjInstance);
	};
/**
 *	新增行获得焦点(外部/内部调用)
 *	@param	:	int:	列号
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	boolean
 *	
 *	@logic	: 	生成最新行号，调用setfocus
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：mulLineCount
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
				default:alert("在 MulLine.js --> MoveFocus 函数中发生异常：" +  ex);break;
			}
		}
	};
/**
 *	修改被选中的行列的样式(内部调用)
 *	@param	:	formName:	表名
 *	@param	:	instanceName:	实例名
 *	@param	:	col:	列号
 *	@param	:	pageNo:	行号
 *	@param	:	cObjInstance:  mulLine对象
 *	@param	:	action:  操作方式，select，cancel
 *	@return ：	none
 *	
 *	@logic	: 	根据action修改指定格的样式
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：instanceName
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
				default:alert("在 MulLine.js --> ChangeSelStyle 函数中发生异常："+ex);break;
			}
		}
	};
/**
 *	锁定删除和增加行的功能(外部调用)
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	none
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
					default:alert("在 MulLine.js --> Lock 函数中发生异常：" +  ex);break;
				}
			}
		}
	};
/**
 *	解除锁定删除和增加行的功能(外部调用)
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	none
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：mulLineText
 *				使用的mulLine属性和html对象：
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
					default:alert("在 MulLine.js --> Unlock 函数中发生异常："+ex);break;
				}
			}
		}
	};
/**
 *	返回被选中复选项数目(外部调用)
 *	@param	:	cObjInstance:  mulLine对象
 *	@return ：	nSelectedCount： 被选中数目
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：
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
				default:alert("在 MulLine.js --> GetChkCount 函数中发生异常："+ex);break;
			}
		}
		return nSelectedCount;
	}
/**
 *	使指定的 radioBox 变成选中的状态(外部调用)
 *	@param	：	nRowNumber：	选中行号（从1开始）
 *	@param	：	cObjInstance:  mulLine对象
 *	@return	：	boolean： 选中结果
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：
 **/
	$.fn.SelOneRow = function(nRowNumber, cObjInstance){
		try{
			var tObjInstance;
			if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
			if (tObjInstance.canSel != 1){
				throw "在 MulLine.js --> SelOneRow 函数中发生错误：" + tObjInstance.instanceName + " 不允许单选！ ";
			}
        	if (nRowNumber == null || nRowNumber <= 0 || tObjInstance.mulLineCount <= 0 || nRowNumber > tObjInstance.mulLineCount){
            	throw "在 MulLine.js --> SelOneRow 函数中指定了错误的行：" + nRowNumber + " ";
        	}else{
            	document.getElementsByName(tObjInstance.instanceName + "Sel")[nRowNumber - 1].click();
	        }
        	return true;
    	}
		catch (ex){
			switch (ex){
				default:alert("在 MulLine.js --> SelOneRow 函数中发生异常："+ex);break;
			}
			return false;
		}
	}
/**
 *	使指定的 checkBox 变成选中的状态或者不选中状态(外部调用)
 *	@param	：	nRowNumber：	选中行号（从1开始）
 *	@param	：	cObjInstance:  mulLine对象
 *	@return	：	boolean： 选中结果
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：
 **/
	$.fn.ChkOneRow = function(nRowNumber, cObjInstance){
		try{
			var tObjInstance;
			if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
	        if (tObjInstance.canChk != 1){
             throw "在 MulLine.js --> ChkOneRow 函数中发生错误：" + tObjInstance.instanceName + " 不允许复选！ ";
        	}
	        if (nRowNumber == null || nRowNumber <= 0 || tObjInstance.mulLineCount <= 0 || nRowNumber > tObjInstance.mulLineCount){
            	throw "在 MulLine.js --> ChkOneRow 函数中指定了错误的行：" + nRowNumber + " ";
	        }else{
				document.getElementsByName(tObjInstance.instanceName + "Chk")[nRowNumber - 1].click();
			}
			return true;
		}
		catch (ex){
			switch (ex){
				default:alert("在 MulLine.js --> ChkOneRow 函数中发生异常："+ex);break;
			}
			return false;
		}
	}
/**
 *	将输入的数据格式进行调整(内部调用)
 *	@param	：	cData：	输入数据
 *	@return	：	cNewData：	输出数据
 *	
 *	@logic	: 	逐字遍历
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
 *	给指定格赋值，同时改变样式(外部调用)
 *	@param	：	cRow：	行号（从1开始）
 *	@param	：	cCol：	列号（从1开始）
 *	@param	：	cData：	数据（需调整）
 *	@param	：	cObjInstance:  mulLine对象
 *	@param	：	cCode：	单元格样式
 *	@param	：	cWidth：	单元格宽
 *	@return	：	boolean： 改变结果
 *	
 *	@logic	: 	与SetRowColDataCustomize1不同在于readonly，此方法设置readonly
 *	@global	: 	影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：
 **/
	$.fn.SetRowColDataCustomize = function(cRow, cCol, cData, cObjInstance, cCode, cWidth){
		var tReturn=false;	var tObjInstance;         //对象指针
		if (cObjInstance==null)	tObjInstance=this;else	tObjInstance=cObjInstance;
		if(cData==null) cData="";
		if(cRow<0||cRow>=tObjInstance.mulLineCount)	{
			alert("在 MulLine.js-->SetRowColDataCustomize() 时指定了错误的行:"+cRow);
			return tReturn;
		}
		if(cCol<0||cCol>=tObjInstance.colCount)	{
			alert("在 MulLine.js-->SetRowColDataCustomize() 时指定了错误的列:"+cCol);
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
				default:alert("在 MulLine.js-->_SetRowColDataCustomize 函数中发生异常："+ex);break;
			}
			return false;
		}
	}
/**
 *	给指定格赋值，同时改变样式(外部调用)
 *	@param	：	cRow：	行号（从1开始）
 *	@param	：	cCol：	列号（从1开始）
 *	@param	：	cData：	数据（需调整）
 *	@param	：	cObjInstance:  mulLine对象
 *	@param	：	cCode：	单元格样式
 *	@param	：	cWidth：	单元格宽
 *	@return	：	boolean： 改变结果
 *	
 *	@logic	: 	与SetRowColDataCustomize不同在于readonly，此方法不设置readonly
 *	@global	: 	影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：
 **/
	$.fn.SetRowColDataCustomize1 = function(cRow, cCol, cData, cObjInstance, cCode, cWidth){
		var tReturn=false;	var tObjInstance;         //对象指针
		if (cObjInstance==null)	tObjInstance=this;else	tObjInstance=cObjInstance;
		if(cData==null) cData="";
		if(cRow<0||cRow>=tObjInstance.mulLineCount)	{
			alert("在 MulLine.js-->SetRowColDataCustomize() 时指定了错误的行:"+cRow);
			return tReturn;
		}
		if(cCol<0||cCol>=tObjInstance.colCount)	{
			alert("在 MulLine.js-->SetRowColDataCustomize() 时指定了错误的列:"+cCol);
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
				default:alert("在 MulLine.js-->_SetRowColDataCustomize 函数中发生异常："+ex);break;
			}
			return false;
		}
	}
/**
 *	给指定格赋值，同时改变样式(外部调用)
 *	@param	：	cRow：	行号（从1开始）
 *	@param	：	cCol：	列号（从1开始）
 *	@param	：	cData：	数据（需调整）
 *	@param	：	cObjInstance:  mulLine对象
 *	@param	：	cCode：	单元格样式
 *	@param	：	cCondition：	
 *	@return	：	boolean： 改变结果
 *	
 *	@logic	: 	与SetRowColDataCustomize不同在于readonly，此方法不设置readonly
 *	@global	: 	影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：
 **/
	$.fn.SetRowColDataShowCodeList = function(cRow, cCol, cData, cObjInstance, cCode, cCondition){
		var tReturn=false;	var tObjInstance;         //对象指针
		if (cObjInstance==null)	tObjInstance=this;else	tObjInstance=cObjInstance;
		if(cData==null) cData="";
		if(cRow<0||cRow>=tObjInstance.mulLineCount)	{
			alert("在 MulLine.js-->SetRowColDataCustomize() 时指定了错误的行:"+cRow);
			return tReturn;
		}
		if(cCol<0||cCol>=tObjInstance.colCount)	{
			alert("在 MulLine.js-->SetRowColDataCustomize() 时指定了错误的列:"+cCol);
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
				default:alert("在 MulLine.js-->_SetRowColDataCustomize 函数中发生异常："+ex);break;
			}
			return false;
		}
	}
/**
 *	添加页数和换页功能
 *	@param  ：	turnPageClass：页面控制类
 *	@return	：	boolean： 改变结果
 *	
 *	@logic	: 	
 *	@global	: 	影响到的mulLine属性和html对象：mulLineCount
 *				使用的mulLine属性和html对象：
 **/
	$.fn.SetPageMark = function(cTurnPage){
		var tReturn=false;	var tObjInstance;         //对象指针
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
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('f', this)\" style='cursor: hand;' onclick='alert(\"已经到达首页！\""
			     	+ ");' name=btnchangepage>&nbsp</td>";
			     tHTML += "<td valign=center ><img src='../common/images/PreviousPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('p', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('p', this)\" style='cursor: hand;' onclick='alert(\"已经到达首页！\");' "
			     	+ "name=btnchangepage>&nbsp</td>";
			}
			
			tHTML += "<td valign=center style='font-size:13px;padding-bottom:4px' >第&nbsp;" + tPageIndex + "/" + tTotalPageNum + "&nbsp;页&nbsp;</td>";/**/
			if (tPageIndex != tTotalPageNum) {
			     tHTML += "<td valign=center ><img src='../common/images/NextPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('n', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('n', this)\" style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(" + tPageIndex + "+1);' name=btnchangepage>&nbsp</td>";
			     tHTML += "<td valign=center ><img src='../common/images/lastPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('l', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('l', this)\" style='cursor: hand;' onclick='" + tStrPageName + ".SortPage.gotoPage(" + tTotalPageNum + ");' name=btnchangepage>&nbsp</td>";
			} else {
			     tHTML += "<td valign=center ><img src='../common/images/NextPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('n', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('n', this)\" style='cursor: hand;' onclick='alert(\"已经到达尾页！\");' name=btnchangepage>&nbsp</td>";
			     tHTML += "<td valign=center ><img src='../common/images/lastPage_defualt.jpg' onmousedown=\"" + tStrPageName + ".pagebuttondown('l', this)\" " 
						+ " onmouseup=\"" + tStrPageName + ".pagebuttonup('l', this)\" style='cursor: hand;' onclick='alert(\"已经到达尾页！\");' name=btnchangepage>&nbsp</td>";
			}
			
			tHTML += "<td valign=center style='font-size:12px;' >转到&nbsp;<input type='common' style='border: 1px #9999CC solid;height: 18px;position:relative;bottom:2px;' name='GotoPage" + tStrPageName + "' size='3'>&nbsp;页</td>";
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
				default:alert("在 MulLine.js-->SetPageMark 函数中发生异常："+ex);break;
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
 *	重新加载flexigrid
 *	@param	：	cConfig：flexigrid的设置
 *	@param	：	cObjInstance:  mulLine对象
 *	@return	：	boolean： 改变结果
 *	
 *	@logic	: 	先取出原有的mulline表格，清空现有的表格后，重新生成flexigrid
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
 **/
	$.fn.Reload = function(cConfig, cObjInstance){
		var tReturn=false;	var $tObjInstance;         //对象指针
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
				default:alert("在 MulLine.js-->Reload 函数中发生异常："+ex);break;
			}
			return false;
		}
	}
/**
 *	为input框设置title属性
 *	@param	：	cElement：input对象
 *	@return	：	boolean： 改变结果
 *	
 *	@logic	: 	直接设置title属性
 *	@global	: 	影响到的mulLine属性和html对象：
 *				使用的mulLine属性和html对象：
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
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->GetRowColDataByName() 时指定了错误的行:" + cRow;}
		var cCol = getColNoByName(tObjInstance.arraySaveOra,colName);
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "在MulLine.js-->setRowColData() 时指定了错误的列:" + cCol;}
		try{
			var newData = cData;//应该加一个字符串判断
			newData = cData.replace("\r\n", "");
			newData = cData.replace("'", "\\'");
			document.getElementsByName(tObjInstance.instanceName+cCol)[cRow].value = newData;
			$("#" + tObjInstance.instanceName + cCol + "r" + cRow,tObjInstance.initTable).val(newData);
			tReturn = true;
		}catch(ex){
			alert("在 MulLine.js --> SetRowColData 函数中发生异常：" +  ex + "\n");
		}
		return tReturn;
	}
	$.fn.GetRowColDataByName = function(cRow, colName, cObjInstance){
		var tReturn = false;
		var tObjInstance;
		if(cObjInstance==null||typeof(cObjInstance)!="object"){tObjInstance = this;}else{tObjInstance = cObjInstance;}
		if(cRow < 0 || cRow >= tObjInstance.mulLineCount){throw "在MulLine.js-->GetRowColDataByName() 时指定了错误的行:" + cRow;}
		var cCol = getColNoByName(tObjInstance.arraySaveOra,colName);
		if(cCol < 0 || cCol >= tObjInstance.colCount){throw "在MulLine.js-->GetRowColDataByName() 时指定了错误的列:" + GetRowColDataByName;}
		try{
			tReturn = $("#" + tObjInstance.instanceName + cCol + "r" + cRow).val();
		}catch(ex){
			alert("在 MulLine.js --> GetRowColDataByName 函数中发生异常：" +  ex + "\n");
		}
		return tReturn;
	};
})(jQuery);

/** 新用法 */
// 示例：
/*
iArray.mulAdd(new function(){
    this.id = 'serailno';// id
	this.name = '序号';// 名称
	this.width = '30px';// 列宽
	this.maxWidth = 30;// 最大列宽
	this.type = 0;// 类型: 0-只读; 1-文本; 2-代码选择; 3-隐藏; 4-密码; 5-需两次输入确认
});
iArray.mulAdd(new function(){
    this.id = 'contno';
	this.name = '保单号';
	this.width = '100px';
	this.maxWidth = 150;
	this.type = 0;
});
*/

// 新类
function MulArray() {
	this.isMulArray = true;
	this.mullineSubData2D = new Array();
	this.add = _MulAdd;
}

// 数据转换&含义说明
function _MulAdd(colElement){
	var i = this.mullineSubData2D.length;
	
	this.mullineSubData2D[i] = new Array();
    this.mullineSubData2D[i]['id']=colElement.id;// 列ID
    this.mullineSubData2D[i][0] = colElement.name;// 列名
    this.mullineSubData2D[i][1] = colElement.width;// 列宽
    this.mullineSubData2D[i][2] = colElement.maxlength;// 最大长度，默认100
    this.mullineSubData2D[i][3] = colElement.type;// 类型: 'text'-文本、'code'-代码选择、'hidden'-隐藏、'password'-密码、'doubleinput'-需两次输入确认
    this.mullineSubData2D[i]['ro']=colElement.readonly;// 只读，默认false
    this.mullineSubData2D[i][21]= colElement.align;// 列字段对齐方式，如：'left'、 'right'、 'center'，默认'left'
    this.mullineSubData2D[i][14]= colElement.value;// 列值(常量)
    this.mullineSubData2D[i][22]= colElement.onblur;// 失去焦点触发的外部函数
    this.mullineSubData2D[i][9] = colElement.verify;// 格式校验
    this.mullineSubData2D[i][7] = colElement.ondblclickFunc;// 响应双击事件的方法，两个参数：（当前行的spanID, ondblclickParam2）
    this.mullineSubData2D[i][8] = colElement.ondblclickParam2;// 响应双击事件ondblclickFunc的第2个参数
    // 【数据库代码引用】
    this.mullineSubData2D[i][4] = colElement.showCodeListCodeName;// 代码名，如：'comcode'
    this.mullineSubData2D[i][5] = colElement.showCodeListShowCodeObj;// 显示结果的列，如：'7|8'
    this.mullineSubData2D[i][6] = colElement.showCodeListShowCodeOrder;// 对应的显示顺序，如：'0|1'
    this.mullineSubData2D[i][15]= colElement.showCodeListConditionFieldName;// 依赖的其它控件名或列名，如：'ManageCom'
    this.mullineSubData2D[i][16]= colElement.showCodeListConditionFieldValue;// 依赖的其它控件值或列值，如：'86'
    this.mullineSubData2D[i][17]= colElement.showCodeListConditionFieldMulValue;// 依赖的其它(多个)控件值或列值，如：'86|8632'
    this.mullineSubData2D[i][18]= colElement.showCodeListWidth;// 调整下拉框的宽度，如：'60px'
    this.mullineSubData2D[i][19]= colElement.showCodeListRefresh;// 强制刷新codeSelect数据源，如：'1'，默认null
    // 【前台CodeData引用】Ex
    this.mullineSubData2D[i][10]= colElement.showCodeListExCodeName;// 代码名，如：'sex'
    this.mullineSubData2D[i][11]= colElement.showCodeListExCodeData;// 代码数据源，如：'0|^0|男|^1|女'
    this.mullineSubData2D[i][12]= colElement.showCodeListExShowCodeObj;// 显示结果的列，如：'5|6'
    this.mullineSubData2D[i][13]= colElement.showCodeListExShowCodeOrder;// 对应的显示顺序，如：'0|1'
    
//  this.mullineSubData2D[i][20] = colElement.DISABLE_PARAM1;// [停用]用于setRowColData函数（判断该参数，是否将编码转为中文）
    
    // 编码转换
    this.mullineSubData2D[i][3] = mullineTypeStringtoNumberFormat(this.mullineSubData2D[i][3]);
    
    // 默认maxlength
    if (typeof(this.mullineSubData2D[i][2]) == 'undefined') {
    	this.mullineSubData2D[i][2] = 100;
    }
};

/**
	id ：列ID , iArray[i]['id'];
	name ：列名 , iArray[i][0];
	width : 列宽 , iArray[i][1];
	maxlength : 最大长度, iArray[i][2];
	type : 类型: 'readonly'-只读、'text'-文本、'code'-代码选择、'hidden'-隐藏、'password'-密码、'doubleinput'-需两次输入确认 , iArray[i][3];
    showCodeListCodeName : 【数据库代码引用】代码名 , iArray[i][4];
    showCodeListShowCodeObj : 【数据库代码引用】显示结果的列 , iArray[i][5];
    showCodeListShowCodeOrder : 【数据库代码引用】对应的显示顺序 , iArray[i][6];
    ondblclickFunc : 响应双击事件的方法，两个参数：（当前行的spanID, ondblclickParam2） , iArray[i][7];
    ondblclickParam2 : 响应双击事件ondblclickFunc的第2个参数 , iArray[i][8];
    formatVerify : 格式校验 , iArray[i][9];
    showCodeListExCodeName : 【前台CodeData引用】代码名 , iArray[i][10];
    showCodeListExCodeData : 【前台CodeData引用】代码数据源 , iArray[i][11];
    showCodeListExShowCodeObj : 【前台CodeData引用】显示结果的列 , iArray[i][12];
    showCodeListExShowCodeOrder : 【前台CodeData引用】对应的显示顺序 , iArray[i][13];
    value : 列值(常量) , iArray[i][14];
    showCodeListConditionFieldName : 【数据库代码引用】依赖的其它控件名或列名, iArray[i][15];
    showCodeListConditionFieldValue : 【数据库代码引用】依赖的其它控件值或列值, iArray[i][16];
    showCodeListConditionFieldMulValue : 【数据库代码引用】依赖的其它(多个)控件值或列值, iArray[i][17];
    showCodeListWidth : 【数据库代码引用】调整下拉框的宽度 , iArray[i][18];
    showCodeListRefresh : 【数据库代码引用】强制刷新codeSelect数据源 , iArray[i][19];
    DISABLE_PARAM1 : [停用]用于setRowColData函数（判断该参数，是否将编码转为中文） , iArray[i][20];
	align : 列字段对齐方式 , iArray[i][21];
	onblur : 失去焦点触发的外部函数 , iArray[i][22];
	
}*/

// 类型: 'readonly'-只读; 'text'-文本; 'code'-代码选择; 'hidden'-隐藏; 'password'-密码; 'doubleinput'-需两次输入确认
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
MulLineEnter.prototype.saveColID = _SaveColID;// 保存ID
MulLineEnter.prototype.colIDtoIndex = _ColIDtoIndex;// ID到下标的转换
// 保存ID
function _SaveColID(arrCols) {
	for (var i=0 ; i<arrCols.length ; i++) {
		var one_id = arrCols[i]['id'];
		if (typeof(one_id) != 'string') {
			alert(this.instanceName + "中，【" + arrCols[i][0] + "】id类型不符。\n注：id必须是字符串。");
			return false;
		}
		this.IDtoIndexArray[one_id] = i;
	}
	return true;
}
// ID到下标的转换
function _ColIDtoIndex(cCol) {
	if (typeof(this.IDtoIndexArray[cCol]) == 'undefined') {
		alert("未找到id为" + cCol + "的列。");
		return -1;
	}
	return this.IDtoIndexArray[cCol];
}