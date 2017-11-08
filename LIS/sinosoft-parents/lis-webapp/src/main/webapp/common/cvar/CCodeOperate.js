/*****************************************************************
 *               Program NAME: 选取代码并显示
 *                 programmer:
 *                Create DATE:
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *     通用代码查询处理页面,包含在隐藏的框架中,输入
 *     过程中要显示代码清单时调用此页面
 *
 *****************************************************************
 */
/**
* 搜寻主窗口，用于CodeSelect缓存数据
* 参数
* 返回
*/
function searchMainWindow(win) {
	if (typeof(win) != "object")
	{
		return false;
	}
	if (win.top.name == "Lis")
	{
		return win.top;
	}
	return searchMainWindow(win.top.opener);
}
//寻找主窗口
var win = searchMainWindow(this);
if (win == false) { win = this; }

//得到存放编码数据的内存引用,要求CVarData.js必须要在一个名称为VD的帧中
//存放的是从数据库的表中取出的编码的数组，即内部数据的存放
mVs=win.parent.VD.gVCode;
if(typeof(mVs)!="object")setTimeout("mVs=win.parent.VD.gVCode;",100);
//得到存放编码数据的内存引用,要求CVarData.js必须要在一个名称为VD的帧中
//存放的是页面上的表名，位置，从数据库表中取出代码的排序等一些外部操作数据
mCs=win.parent.VD.gVSwitch;
if(typeof(mCs)!="object")setTimeout("mCs=win.parent.VD.gVSwitch;",100);
var _Code_FIELDDELIMITER    = "|";   //域之间的分隔符
var _Code_RECORDDELIMITER   = "^";   //记录之间的分隔符
var arrayBmCode;                     //存放最近代码的代码
var arrEasyQuery = new Array();          // 存放使用easyQuery()得到的查询结果数组

var showFirstIndex=0;
var oEventField;    //发生事件的原始控件, Added by XinYQ on 2006-10-23


/**
* 获取字符串的部分子串，该函数得到c_Str中的第c_i个以c_Split分割的字符串
* <p><b>Example: </b><p>
* <p>getStr("Minim|Hzm|Yt|", "2", "|") returns "Hzm"<p>
* @param c_Str 有分隔规则的字符串
* @param c_i 取第几个分隔子串
* @param c_Split 分隔符
* @return 返回第c_i个分隔子串
*/
function getStr(c_Str , c_i ,c_Split)
{
  var t_Str1, t_Str2 , t_strOld;
  var i, i_Start, j_End;
  t_Str1 = c_Str;
  t_Str2 = c_Split;
  i = 0;
	try
	{
    while (i < c_i)
    {
      i_Start = t_Str1.indexOf(t_Str2,0);
      if (i_Start >= 0)
      {
        i = i + 1;
        t_strOld = t_Str1;
        t_Str1 = t_Str1.substring(i_Start+t_Str2.length,t_Str1.length);
      }
      else
      {
        if (i != c_i - 1)
        {
          t_Str1="";
        }
        break;
      }
    }

    if (i_Start >= 0)
      t_Str1=t_strOld.substring(0,i_Start);
  }
  catch(ex)
  {
    t_Str1="";
  }
  return t_Str1;
}


/*************************************************************
 *  显示编码（该函数为显示编码的入口函数）
 *  参数  ：  Field 触发事件的控件;
 *            strCodeName 编码名称;
 *            arrShowCodeObj 将编码显示到对应的控件上
 *            arrShowCodeOrder 编码显示对应控件和编码顺序的对应关系
 *            arrShowCodeFrame 编码显示对应的Frame指针
 *  返回值：  如果没有返回false,否则返回true
 *************************************************************
 */
function showCodeList(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven,searchFlag)
{
    if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
    {
        oEventField = arrShowCodeObj[0];
    }
    
    if(searchFlag == null || typeof(searchFlag)=="undefined")
    {
    	searchFlag = '1' ;//不传默认启用
    }
    
     
    var ex,ey;
    var tCode;
    var Field;
    //用来匹配Sql语句，必须是where子句以后的部分
    var strCondition = "";                                   //传入的查询条件值
    var strConditionField = "";                              //传入的查询条件的字段名
    //增加代码选择的查询条件
  if (objCondition != null) {
    if (typeof(objCondition) == "object") {
      for(var m=0;m<objCondition.length;m++){
        strCondition = strCondition+objCondition[m];
        if(m<objCondition.length-1)
        strCondition=strCondition+"|";
      }
    }
    else {
      strCondition = objCondition;
    }
  }
  if (conditionField != null) {
    strConditionField = conditionField;
  }

  //如果进行数据库查询，第二次不会进入这里
    if (arrShowCodeObj != null){
    //Field  是一个数组集合，表示要显示编码的在页面上的对象
    Field = arrShowCodeObj[0];
    setFieldPos(Field);

    oldField = Field;
    oldFieldKey = "";
    //alert(oldField + "\n2:" + oldFieldKey);

//    Field.onblur = closeCodeList;      //为控件增加关闭CODESELECT的方法

    if (arrShowCodeOrder == null) {
      arrShowCodeOrder = [0];
    }

    if (objShowCodeFrame == null) {
      objShowCodeFrame = parent.fraInterface;//window.self;
    }

      try {
      //将一些参数放到客户端代码区
      mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
      mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
      mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);    //Frame的指针
    }
    catch(e) {
      //重新初始化各个对象
      mVs=parent.VD.gVCode;
      mCs=parent.VD.gVSwitch;
      var _Code_FIELDDELIMITER    = "|";   //域之间的分隔符
      var _Code_RECORDDELIMITER   = "^";   //记录之间的分隔符
      var arrayBmCode;                     //存放最近代码的代码
      var arrEasyQuery = new Array();            // 存放使用easyQuery()得到的查询结果数组
      var showFirstIndex=0;
      showCodeList(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven,searchFlag);
    }
    }

  //alert("strCondition:" + strCondition + "\nstrConditionField:" + strConditionField);
    tCode=searchCode(strCodeName,strCondition,strConditionField);  //从代码区读取代码
    //强制刷新查询
    if (refresh) {
      tCode = false;
    try {
        //由于new一个codeselect对象的时候，会插入以下两个对象，因此再强制刷新的时候，也需要delete两个对象
        mVs.deleteVar(strCodeName+strCondition+strConditionField);
        mVs.deleteVar(strCodeName+"Select");
        mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
    } catch(ex) {}
  }
    if (tCode == false && arrShowCodeObj != null) {
      //请求服务器端，读取数据
            //alert(strCondition);
            //alert(strConditionField);
        requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven,searchFlag);
        //该js终止，转到向服务器发出请求的页面执行，该页面查询数据后保存到页面上的内存区，
    //并再次调用该js,（先是initializeCode，然后showCodeList,请注意initializeCode不可少的作用）
    //继续执行js，不过这次代码区已经有了代码，不会进入这个控制区
        return false;
    }

    //将代码区得到的数组和编码名称传入，显示代码
   
    showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
  	
    //如果要显示编码的页面对象存在
  //显示编码表，根据页面对象中的值，使编码表定位到对应Field中的值的那一项
    if(Field!=null) {
      goToSelect(strCodeName,Field,strCondition,strConditionField);
      document.all("codeselect").style.borderColor = "#d4d4d4"; //2015-08-25 
    }

//      alert(mVs.getVar(strCodeName+strCondition+strConditionField));
//      alert(mVs.getVar(strCodeName+strCondition+strConditionField+"Select"));

    //XinYQ added on 2006-05-16
    //如果清空了下拉列表代码, 则同时清除已显示的下拉列表代码对应的名称
    var oFieldCodeArea, oFieldCodeName;
    try
    {
        oFieldCodeArea = arrShowCodeObj[0];
        oFieldCodeName = arrShowCodeObj[1];
    }
    catch (ex) {}
    if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
    {
        try
        {
        	if (typeof(oFieldCodeName) == "object") 
        		oFieldCodeName.value = "";
        	if(typeof(oFieldCodeName) == "string")
        		document.getElementById("oFieldCodeName").value = "";
        }
        catch (ex) {}
    }

    return true;
}

/*************************************************************
 *  显示编码（该函数为显示编码的入口函数
 *  参数  ：  Field 触发事件的控件;
 *            strCodeName 编码名称;
 *            arrShowCodeObj 将编码显示到对应的控件上
 *            arrShowCodeOrder 编码显示对应控件和编码顺序的对应关系
 *            arrShowCodeFrame 编码显示对应的Frame指针
 *  返回值：  如果没有返回false,否则返回true
 *************************************************************
 */
function showCodeListEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
    if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
    {
        oEventField = arrShowCodeObj[0];
    }

  var ex,ey;
  var tCode;
  var Field;

  var strCondition = "";
  var strConditionField = "";

  //增加代码选择的查询条件，MINIM
  if (objCondition != null) {
    if (typeof(objCondition) == "object") {
      strCondition = objCondition.value;
    }
    else {
      strCondition = objCondition;
    }
  }
  if (conditionField != null) {
    strConditionField = conditionField;
  }

  //如果进行数据库查询，第二次不会进入这里
    if (arrShowCodeObj != null) {
    Field=arrShowCodeObj[0];

    oldField = Field;                  //解决焦点问题，MINIM增加
//    Field.onblur = closeCodeList;      //为控件增加关闭CODESELECT的方法，MINIM增加
    oldFieldKey = "";

    setFieldPos(Field);

    if (arrShowCodeOrder == null) {
      arrShowCodeOrder = [0];
    }

    if (objShowCodeFrame == null) {
      objShowCodeFrame = parent.fraInterface;//window.self;
    }

      try {
      //将一些参数放到客户端代码区
      mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
      mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
      mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame的指针
    }
    catch(e) {
      //重新初始化各个对象
      mVs=parent.VD.gVCode;
      mCs=parent.VD.gVSwitch;
      var _Code_FIELDDELIMITER    = "|";   //域之间的分隔符
      var _Code_RECORDDELIMITER   = "^";   //记录之间的分隔符
      var arrayBmCode;                     //存放最近代码的代码
      var arrEasyQuery = new Array();            // 存放使用easyQuery()得到的查询结果数组
      var showFirstIndex=0;

      showCodeListEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
    }
    }

    tCode=searchCode(strCodeName,strCondition,strConditionField);                         //从代码区读取代码

  //强制刷新查询
    if (refresh) {
      tCode = false;
    try {
        mVs.deleteVar(strCodeName+strCondition+strConditionField);
        mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
    } catch(ex) {}
  }

    if (tCode == false && arrShowCodeObj != null){
        tCode=initializeCodeEx(strCodeName,Field);
        try {
          if(tCode==false)
            return false;
        }
        catch(ex1) {
          return false;
        }
    }

    showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
    if(Field!=null) {
      goToSelect(strCodeName,Field,strCondition,strConditionField);
    }

    //XinYQ added on 2006-05-16
    //如果清空了下拉列表代码, 则同时清除已显示的下拉列表代码对应的名称
    var oFieldCodeArea, oFieldCodeName;
    try
    {
        oFieldCodeArea = arrShowCodeObj[0];
        oFieldCodeName = arrShowCodeObj[1];
    }
    catch (ex) {}
    if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
    {
        try
        {
        	if (typeof(oFieldCodeName) == "object") 
        			oFieldCodeName.value = "";
        	if(typeof(oFieldCodeName) == "string")
        			document.getElementById("oFieldCodeName").value = "";
        }
        catch (ex) {}
    }

    return true;
}

function initClientCode(cCodeName,cField)
{

}

/*************************************************************
 *  查找代码(键盘按键事件)
 *  参数  ：  Field 需要显示代码的控件;
 *            strCodeName 代码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 代码所属区站.
 *  返回值：  string  ：code 或 null
 *************************************************************
 */
function showCodeListKey(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
    if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
    {
        oEventField = arrShowCodeObj[0];
    }

    var ex,ey,i,intElementIndex;
    var Field;

    var strCondition = "";
    var strConditionField = "";

    Field=arrShowCodeObj[0];
    oldFieldKey = Field;
    oldField = "";

    //增加代码选择的查询条件，MINIM
  if (objCondition != null) {
    if (typeof(objCondition) == "object") {
        //strCondition = objCondition.value; /*deleted by liurx 2006-01-24*/
      for(var m=0;m<objCondition.length;m++){
        strCondition = strCondition+objCondition[m];
        if(m<objCondition.length-1)
        strCondition=strCondition+"|";
      }
    }
    else {
      strCondition = objCondition;
    }
  }
  if (conditionField != null) {
    strConditionField = conditionField;
  }
  	//eobj = window.event;
    //key  = eobj.keyCode;
  	var eobj = arguments.callee.caller.arguments[0] || window.event; 
    var key = eobj.which ? eobj.which : eobj.keyCode;

    if (  document.all("spanCode").style.display=="" && (key == 13 )) {
       document.all("codeselect").focus();
       document.all("codeselect").onclick();
       try { Field.focus(); } catch(ex) {}
    }

    if ( key == 40 && document.all("spanCode").style.display=="") {
      Field.onblur=null;
      document.all("codeselect").focus();

      try {
        document.all("codeselect").children[showFirstIndex].selected=true;
      }
      catch(e) {
          document.all("codeselect").children[0].selected=true;
        }
      //showFirstIndex += showFirstIndex;
    }
    else {
//      Field.onblur=closeCodeList;
    }

    if (document.all("spanCode").style.display=="none" && (key >= 48 || key==8 || key==46 || key==40 ))
    {
      setFieldPos(Field);
      //提交代码名称及信息
      if (arrShowCodeOrder == null) {
        arrShowCodeOrder = [0];
      }

      if (objShowCodeFrame == null) {
        objShowCodeFrame = parent.fraInterface;//window.self;
      }

      setFieldPos(Field);

      try {
          mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
          mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
        mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame的指针
      }
      catch(e) {
        //重新初始化各个对象
        mVs=parent.VD.gVCode;
        mCs=parent.VD.gVSwitch;
        var _Code_FIELDDELIMITER    = "|";   //域之间的分隔符
        var _Code_RECORDDELIMITER   = "^";   //记录之间的分隔符
        var arrayBmCode;                     //存放最近代码的代码
        var arrEasyQuery = new Array();          // 存放使用easyQuery()得到的查询结果数组
        var showFirstIndex=0;

          showCodeListKey(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
        }

      //提交代码名称及信息
      getCode(strCodeName,arrShowCodeObj,strCondition,strConditionField,refresh,showWidth,changeEven);
      //getCodeSearch(strCodeName,arrShowCodeObj,strCondition,strConditionField,refresh,showWidth,changeEven);
      goToSelect(strCodeName,Field,strCondition,strConditionField);
    }
    else if ( document.all("spanCode").style.display=="" && (key >= 48 || key==8 || key==46))
    {
      if ( Field.value != null)
      {
      	//getCode(strCodeName,arrShowCodeObj,strCondition,strConditionField,refresh,showWidth,changeEven);
        goToSelect(strCodeName,Field,strCondition,strConditionField);
      }
    }

    //XinYQ added on 2006-05-16
    //如果清空了下拉列表代码, 则同时清除已显示的下拉列表代码对应的名称
    var oFieldCodeArea, oFieldCodeName;
    try
    {
        oFieldCodeArea = arrShowCodeObj[0];
        oFieldCodeName = arrShowCodeObj[1];
    }
    catch (ex) {}
    if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
    {
        try
        {
        	if (typeof(oFieldCodeName) == "object") 
        		oFieldCodeName.value = "";
        	if(typeof(oFieldCodeName) == "string")
        		document.getElementById("oFieldCodeName").value = "";
        }
        catch (ex) {}
    }
}

/*************************************************************
 *  查找代码(键盘按键事件)
 *  参数  ：  Field 需要显示代码的控件;
 *            strCodeName 代码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 代码所属区站.
 *  返回值：  string  ：code 或 null
 *************************************************************
 */
function showCodeListKeyEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
    if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
    {
        oEventField = arrShowCodeObj[0];
    }

    var ex,ey,i,intElementIndex;
    var Field;

    var strCondition = "";
    var strConditionField = "";

    Field=arrShowCodeObj[0];
    oldFieldKey = Field;
    oldField = "";

    //增加代码选择的查询条件，MINIM
    if (objCondition != null) {
      if (typeof(objCondition) == "object") {
        strCondition = objCondition.value;
      }
      else {
        strCondition = objCondition;
      }
    }
    if (conditionField != null) {
      strConditionField = conditionField;
    }

//    eobj = window.event;
//    key  = eobj.keyCode;
  	var eobj = arguments.callee.caller.arguments[0] || window.event; 
    var key = eobj.which ? eobj.which : eobj.keyCode;
    
    if (  document.all("spanCode").style.display=="" && (key == 13|| key==16) )
    {
       document.all("codeselect").focus();
       document.all("codeselect").onclick();
       Field.focus();
    }

    if ( key == 40 && document.all("spanCode").style.display=="")
    {
      Field.onblur=null;
      document.all("codeselect").focus();

      try {
        document.all("codeselect").children[showFirstIndex].selected=true;
      }
      catch(e) {
          document.all("codeselect").children[0].selected=true;
        }
      //showFirstIndex += showFirstIndex;
    }
    else
    {
//      Field.onblur=closeCodeList;
    }

    if (  document.all("spanCode").style.display=="none" && (key >= 48 || key==8 || key==46 || key==40 ))
    {
      setFieldPos(Field);
      //提交代码名称及信息
      if (arrShowCodeOrder == null)
      {
        arrShowCodeOrder = [0];
      }

      if (objShowCodeFrame == null)
      {
        objShowCodeFrame = parent.fraInterface;//window.self;
      }

      setFieldPos(Field);

      try {
          mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
          mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
        mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame的指针
      }
      catch(e) {
        //重新初始化各个对象
        mVs=parent.VD.gVCode;
        mCs=parent.VD.gVSwitch;
        var _Code_FIELDDELIMITER    = "|";   //域之间的分隔符
        var _Code_RECORDDELIMITER   = "^";   //记录之间的分隔符
        var arrayBmCode;                     //存放最近代码的代码
        var arrEasyQuery = new Array();          // 存放使用easyQuery()得到的查询结果数组
        var showFirstIndex=0;

          showCodeListKeyEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
        }

      //提交代码名称及信息
      showCodeListEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,refresh,showWidth,changeEven);
      goToSelect(strCodeName,Field,strCondition,strConditionField);
    }
    else if ( document.all("spanCode").style.display=="" && (key >= 48 || key==8 || key==46))
    {
      if ( Field.value != null)
      {
        goToSelect(strCodeName,Field,strCondition,strConditionField);
      }
    }

    //XinYQ added on 2006-05-16
    //如果清空了下拉列表代码, 则同时清除已显示的下拉列表代码对应的名称
    var oFieldCodeArea, oFieldCodeName;
    try
    {
        oFieldCodeArea = arrShowCodeObj[0];
        oFieldCodeName = arrShowCodeObj[1];
    }
    catch (ex) {}
    if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
    {
        try
        {
        	if (typeof(oFieldCodeName) == "object") 
        		oFieldCodeName.value = "";
        	if(typeof(oFieldCodeName) == "string")
        		document.getElementById("oFieldCodeName").value = "";
        }
        catch (ex) {}
    }
}

/*************************************************************
 *  初始化编码
 *  参数  ：  strCodeName：编码名称
 *  返回值：  boolean   true：找到所需的代码   false：未找到
 *************************************************************
 */
function initializeCode(strCodeName, codeCondition, conditionField,searchFlag)
{

  var i,i1,j,j1;
  var strValue;                         //存放服务器端返回的代码数据
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //存放初始化变量时用
  var t_Str;

  var strCodeSelect = "";

  clearShowCodeError();

  strValue  = getCodeValue(strCodeName);              //得到服务器端读取的数据

  arrRecord = strValue.split(_Code_RECORDDELIMITER);  //拆分字符串，形成返回的数组

  t_Str     = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);

  if (t_Str!="0")                                     //如果不为0表示服务器端执行发生错误
  {
    mCs.updateVar("ShowCodeError",getStr(arrRecord[0],2,_Code_FIELDDELIMITER));   //将错误保存到该变量中
    mCs.updateVar("ShowCodeErrorCode",t_Str);   //将错误保存到该变量中
    return false;
  }

  i1=arrRecord.length;
  for (i=1;i<i1;i++)
  {
    arrField  = arrRecord[i].split(_Code_FIELDDELIMITER); //拆分字符串,将每个纪录拆分为一个数组
    j1=arrField.length;

    arrCode[i-1] = new Array();
    for (j=0;j<j1;j++)
    {
      arrCode[i-1][j] = arrField[j];
    }

    strCodeSelect = strCodeSelect + "<option value='" + arrCode[i-1][0] + "'>";
    strCodeSelect = strCodeSelect + arrCode[i-1][0] + "-" + arrCode[i-1][1];
    strCodeSelect = strCodeSelect + "</option>";
  }

  mVs.addArrVar(strCodeName+codeCondition+conditionField,"",arrCode); //无论是否有数据从服务器端得到,都设置该变量
  //var temp
  if(searchFlag!=0)
  {
  	mVs.addArrVar(strCodeName+codeCondition+conditionField+"Chinese","",XqkChinese.createCTextArrayForLis(arrCode)); //无论是否有数据从服务器端得到,都设置该变量
  }

  //XqkChinese.createCTextArrayForLis(tempSearch);
  mVs.addVar(strCodeName+codeCondition+conditionField); //无论是否有数据从服务器端得到,都设置该变量
  mVs.addVar(strCodeName+codeCondition+conditionField+"Select","",strCodeSelect); //无论是否有数据从服务器端得到,都设置该变量

  return true;
}

/*************************************************************
 *                     初始化编码
 *  参数  ：  strCodeName：编码名称
 *  返回值：  boolean   true：找到所需的代码   false：未找到
 *************************************************************
 */
function initializeCodeEx(strCodeName,cField)
{
    var i,i1,j,j1;
  var strValue;                         //存放服务器端返回的代码数据
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //存放初始化变量时用
  var t_Str;

  var strCodeSelect = "";

  clearShowCodeError();
  try
  {
  strValue  =  $(cField).attr("CodeData");              //得到服务器端读取的数据//
  if(!strValue){
	  strValue  =  cField.getAttribute("CodeData") || cField.CodeData;
  }
  }
  catch(ex)
  {
	  try {
		   strValue  =  cField.getAttribute("CodeData") || cField.CodeData;
	  }
	  catch(ex)
	  {
	 alert("没有在客户端描述代码数据!");
	 return false;
	  }
  }

  arrRecord = strValue.split(_Code_RECORDDELIMITER);  //拆分字符串，形成返回的数组

  t_Str     = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);

  if (t_Str!="0")                                     //如果不为0表示服务器端执行发生错误
  {
    mCs.updateVar("ShowCodeError",getStr(arrRecord[0],2,_Code_FIELDDELIMITER));   //将错误保存到该变量中
    mCs.updateVar("ShowCodeErrorCode",t_Str);   //将错误保存到该变量中
    return false;
  }

  i1=arrRecord.length;
  for (i=1;i<i1;i++)
  {
    arrField  = arrRecord[i].split(_Code_FIELDDELIMITER); //拆分字符串,将每个纪录拆分为一个数组
    j1=arrField.length;
    arrCode[i-1] = new Array();
    for (j=0;j<j1;j++)
    {
      arrCode[i-1][j] = arrField[j];
    }

    strCodeSelect = strCodeSelect + "<option value='" + arrCode[i-1][0] + "'>";
    strCodeSelect = strCodeSelect + arrCode[i-1][0] + "-" + arrCode[i-1][1];
    strCodeSelect = strCodeSelect + "</option>";
  }

  mVs.deleteVar(strCodeName);
  mVs.addArrVar(strCodeName,"",arrCode);                 //无论是否有数据从服务器端得到,都设置该变量
  mVs.addArrVar(strCodeName+"Chinese","",XqkChinese.createCTextArrayForLis(arrCode)); //无论是否有数据从服务器端得到,都设置该变量

  mVs.deleteVar(strCodeName+"Select");
  mVs.addVar(strCodeName+"Select","",strCodeSelect); //无论是否有数据从服务器端得到,都设置该变量

  return arrCode;
}



/*************************************************************
 *                     得到编码值串
 *  参数  ：  strCodeName：编码名称
 *  返回值：  string     ：编码值串
 *************************************************************
 */
function getCodeValue(strCodeName)
{
  var reStr;
  //try
  //    {
      reStr= parent.EX.fm.txtVarData.value;     //从父frame中取得从服务器端取得的值
    //}
    //catch(ex)
    //{}
    return reStr;
}

/*************************************************************
 *                     请求服务器
 *  参数  ：  intElementIndex 需要显示编码的控件的索引号;
 *            strCodeName 编码类型(Lx_Code);
 *            intFunctionIndex 需要赋值控件的顺序 1-前一个和本身
 *                                                2-本身和后一个;
 *            stationCode 编码所属区站；
 *            ex,ey 显示的位置.
 *  返回值：  无
 *************************************************************
 */
function requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven,searchFlag)
{
//  alert(strCodeName+strCondition+strConditionField);
  var objFrame;
  objFrame=mCs.getVar("ShowCodeFrame");
    //请求服务器
//      try
//      {
      //alert(strCodeName + "\n" + objFrame.name);
        parent.EX.fm.txtCodeName.value       = strCodeName;     //编码名称
        parent.EX.fm.txtFrameName.value      = objFrame.name;   //Frame的名字
        parent.EX.fm.txtVarData.value        = "";              //返回时需要的空间
        parent.EX.fm.txtOther.value          = "";              //提交时的其他数据

        parent.EX.fm.txtCodeCondition.value  = strCondition;       //查询条件
        parent.EX.fm.txtConditionField.value = strConditionField; //查询条件字段

        parent.EX.fm.txtShowWidth.value      = showWidth; //查询条件字段
        parent.EX.fm.changeEven.value        = changeEven;
        
        parent.EX.fm.searchFlag.value        = searchFlag;
        parent.EX.fm.submit();
//  }
//  catch(exception){}
}

/*************************************************************
 *  下拉框的方式显示编码列表
 *  参数  ：  arrCode     包含该类型编码的所有编码信息的数组;
 *            strCodeName 需要显示的代码名称
 *  返回值：  无
 *************************************************************
 */
function showCodeList1(arrCode,strCodeName,strCondition,strConditionField,showWidth,changeEven)
{
	 //tongmeng 2008-04-30 add
    //增加对IE7的支持
    document.body.onclick=function (){closeCodeList();}
    var strValue;
    var flag=false;
    var strText;
    var arrCount;
    var fm;
    //add by yt ,增加宽度的自适应
    var strText1;
    var tStr;
    var tMaxLen = 0;
    var tCurLen = 0;

    var strChange="";

    if(changeEven != null) strChange = ",1";

      fm=mCs.getVar("ShowCodeFrame");

      //alert("showWidth: " + showWidth);
//      strText="<select name=codeselect style='width:350px' size=8  onchange=setFieldValue(this,'"+strCodeName+"')>";
    strText = "" ;
    strText = strText
            + strCodeName+"','"+strCondition+"','"+strConditionField+"'"+strChange+");}\""
            + " onclick=\"setFieldValue(this,'" +strCodeName + "','" + strCondition + "','" + strConditionField + "'"+strChange+")\""
            + " onblur=\"closeCodeList();\" >";

//    arrCount=arrCode.length;
//      for(i=0;i<arrCount;i++)
//      {
//      flag=true;
//      strText=strText+"<option value="+arrCode[i][0]+">";
//      strText=strText+arrCode[i][0]+"-"+arrCode[i][1];
//      strText=strText+"</option>";
//      }

    //从数据库取数据的情况
   
   	var strCode = searchCode(strCodeName,strCondition,strConditionField,1);
//    alert(strCode);
   		 if (strCode) {
      		strText = strText + strCode;
     		  flag = true;
    	}

    	if (!flag) {
      	//虚拟数据源的情况
      	strCode = searchCode(strCodeName,strCondition,strConditionField,"EX");

      	if (strCode) {
          strText = strText + strCode;
          flag = true;
        }
    	}
  	
//	prompt('',strText);
    //alert(strCode);
    //add by yt , 使得CodeSelect的宽度能够自动适应
    arrCount=arrCode.length;
    if (arrCount>100 ) arrCount = 100;//由于效率，只考虑前100个情况
    for(i=0;i<arrCount;i++)
    {
      tStr = arrCode[i][1];
      try{
        tCurLen = tStr.length;
        }catch (ex){}
      if (tCurLen>tMaxLen) tMaxLen = tCurLen;
    }

    tCurLen = 10 * tMaxLen ;    //XinYQ modified on 2006-06-12 : 原来的基数 25 过大,造成类似 Station 过长
  //  if (tCurLen <= 255) tCurLen = 255;  //取最小宽度    //XinYQ modified on 2006-06-12 : 默认一个 title + 一个 input 边界宽度
// alert(tCurLen);
  if (tCurLen <= 200) tCurLen = 200; //tongmeng 20090409modify 初始过宽
    if (tCurLen >= 2000) tCurLen = 2000;    //取最大宽度
    //当showWidth为null时特殊处理
    try{
        if (showWidth == "null") showWidth = 255;
    }catch(ex1){}
    if (typeof(showWidth)!="undefined" && showWidth!="undefined") {
      //if (showWidth < tCurLen) showWidth = tCurLen;
      strText1 = "<select name=codeselect id='codeselect' style='width:" + showWidth + "px' size=8  onkeyup=" +
      		"\"var eobj = arguments.callee.caller.arguments[0] || window.event;var key = eobj.which ? eobj.which : eobj.keyCode;if (key==13||key==16){setFieldValue(this,'";
    }
    else {
      strText1 = "<select name=codeselect id='codeselect' style='width:" + tCurLen + "px' size=8  onkeyup=" +
      		"\"var eobj = arguments.callee.caller.arguments[0] || window.event;var key = eobj.which ? eobj.which : eobj.keyCode;if (key==13||key==16){setFieldValue(this,'";
    }
    strText=strText1 + strText+"</select>"

    if(flag)
    {
        document.all("spanCode").innerHTML =strText;
        document.all("spanCode").style.left=mCs.getVar("ShowCodeX") + "px";    //读取公共变量区的坐标X
        document.all("spanCode").style.top=mCs.getVar("ShowCodeY") + "px";     //读取公共变量区的坐标Y
        document.all("spanCode").style.display ='';
    }
    else
    {
        document.all("spanCode").style.display ='none';
    }
		

    //document.all("codeselect").focus();
   
}

function showCodeListSearch(arrCode,strCodeName,strCondition,strConditionField,showWidth,changeEven)
{
	 //tongmeng 2008-04-30 add
    //增加对IE7的支持
    document.body.onclick=function (){closeCodeList();}
    var strValue;
    var flag=false;
    var strText;
    var arrCount;
    var fm;
    //add by yt ,增加宽度的自适应
    var strText1;
    var tStr;
    var tMaxLen = 0;
    var tCurLen = 0;

    var strChange="";

    if(changeEven != null) strChange = ",1";

      fm=mCs.getVar("ShowCodeFrame");

      //alert("showWidth: " + showWidth);
//      strText="<select name=codeselect style='width:350px' size=8  onchange=setFieldValue(this,'"+strCodeName+"')>";
    strText = "" ;
    strText = strText
            + strCodeName+"','"+strCondition+"','"+strConditionField+"'"+strChange+");}\""
            + " onclick=\"setFieldValue(this,'" +strCodeName + "','" + strCondition + "','" + strConditionField + "'"+strChange+")\""
            + " onblur=\"closeCodeList();\" >";

     arrCount=arrCode.length;
      for(i=0;i<arrCount;i++)
      {
     	   flag=true;
    		 strText=strText+"<option value="+arrCode[i][0]+">";
     		 strText=strText+arrCode[i][0]+"-"+arrCode[i][1];
         strText=strText+"</option>";
      }

//	prompt('',strText);
    //alert(strCode);
    //add by yt , 使得CodeSelect的宽度能够自动适应
    arrCount=arrCode.length;
    if (arrCount>100 ) arrCount = 100;//由于效率，只考虑前100个情况
    for(i=0;i<arrCount;i++)
    {
      tStr = arrCode[i][1];
      try{
        tCurLen = tStr.length;
        }catch (ex){}
      if (tCurLen>tMaxLen) tMaxLen = tCurLen;
    }

    tCurLen = 10 * tMaxLen ;    //XinYQ modified on 2006-06-12 : 原来的基数 25 过大,造成类似 Station 过长
  //  if (tCurLen <= 255) tCurLen = 255;  //取最小宽度    //XinYQ modified on 2006-06-12 : 默认一个 title + 一个 input 边界宽度
// alert(tCurLen);
  if (tCurLen <= 200) tCurLen = 200; //tongmeng 20090409modify 初始过宽
    if (tCurLen >= 2000) tCurLen = 2000;    //取最大宽度
    //当showWidth为null时特殊处理
    try{
        if (showWidth == "null") showWidth = 255;
    }catch(ex1){}
    if (typeof(showWidth)!="undefined" && showWidth!="undefined") {
      //if (showWidth < tCurLen) showWidth = tCurLen;
      strText1 = "<select name=codeselect id='codeselect' style='width:" + showWidth + "px' size=8  onkeyup=" +
      		"\"var eobj = arguments.callee.caller.arguments[0] || window.event;var key = eobj.which ? eobj.which : eobj.keyCode;if (key==13||key==16){setFieldValue(this,'";
    }
    else {
      strText1 = "<select name=codeselect id='codeselect' style='width:" + tCurLen + "px' size=8  onkeyup=" +
      		"\"var eobj = arguments.callee.caller.arguments[0] || window.event;var key = eobj.which ? eobj.which : eobj.keyCode;if (key==13||key==16){setFieldValue(this,'";
    }
    strText=strText1 + strText+"</select>"

    if(flag)
    {
        document.all("spanCode").innerHTML =strText;
        document.all("spanCode").style.left=mCs.getVar("ShowCodeX");    //读取公共变量区的坐标X
        document.all("spanCode").style.top=mCs.getVar("ShowCodeY");     //读取公共变量区的坐标Y
        document.all("spanCode").style.display ='';
    }
    else
    {
        document.all("spanCode").style.display ='none';
    }
		

    //document.all("codeselect").focus();
   
}


/*************************************************************
 *  为控件赋值
 *  参数  ：  Field 需要赋值的控件
 *  返回值：  无
 *************************************************************
 */
function setFieldValue(Field,strCodeName,strCondition,strConditionField,changeEven)
{
    var tFldCode;               //为一个代码表的纪录，如001,总公司,总公司信息
    var tArrDisplayObj;         //需要显示到的对象
    var tArrDisplayOrder;       //显示的顺序
    var i,iMax;
    var strChange="";

    //得到一个代码纪录
    tFldCode = getOneCode(strCodeName,Field.value,strCondition,strConditionField);
    tArrDisplayObj   = mCs.getVar("ShowCodeObj");     //得到需要显示的对象
    tArrDisplayOrder = mCs.getVar("ShowCodeOrder");   //得到显示时对应的顺序
    iMax = tArrDisplayObj.length;

    try
    {
        for (i=0; i<iMax; i++)
        {
        	if(typeof(tArrDisplayObj[i]) == 'object')
        		tArrDisplayObj[i].value = tFldCode[tArrDisplayOrder[i]];  //根据显示顺序设置显示对象
        	else if(typeof(tArrDisplayObj[i]) == 'string')
        		document.getElementById(tArrDisplayObj[i]).value = tFldCode[tArrDisplayOrder[i]];
        }

        if (changeEven != null)
        {
          strChange = "change"+tArrDisplayObj[0].name+"();";
          eval(strChange);
        }
        //XinYQ modified on 2006-10-23
        //alert("oEventField = " + oEventField.name);
        //alert("Field = " + Field.name);
        if (oEventField != null && typeof(oEventField) != "undefined")
        {
            afterCodeSelect(strCodeName, oEventField);
        }
        else
        {
            afterCodeSelect(strCodeName, Field);
        }
    }
    catch (ex) {}
    document.all("spanCode").style.display = 'none';
    //使用户选择值后，控件仍保持焦点
   // alert(oldField.name + "\n2:" + oldFieldKey);
   try { if (oldField != "") oldField.focus(); } catch(ex) {}
    try { if (oldFieldKey != "") oldFieldKey.focus(); } catch(ex) {}
}

/*************************************************************
 *              从Code内存中读取该Code的信息
 *  参数  ：  strCodeName:Code的类型(名称)
 *            strCode    :Code的编码
 *            index 控件的索引号
 *  返回值：  无
 *************************************************************
 */
function getOneCode(strCodeName,strCode,strCondition,strConditionField)
{
  var tArrCode;
  var i,iMax;
  var tArrReturn;
  tArrCode = mVs.getVar(strCodeName+strCondition+strConditionField);
  //alert(tArrCode);
  iMax     = tArrCode.length;

  for (i=0 ; i<iMax;i++)
  {
    if (tArrCode[i][0]==strCode)
    {
      tArrReturn = tArrCode[i];
      break;
    }
  }
  return tArrReturn;
}


/*************************************************************
 *                      查找编码
 *  参数  ：  strValue：编码名称
 *  返回值：  string  ：code 或 false
 *************************************************************
 */
function searchCode(strValue,strCondition,strConditionField,isShortShow) {
	
  //alert(isShortShow);
  if (typeof(isShortShow) == "undefined") {
    //alert(1);
    //取得编码，如果没有找到，返回-1
    return mVs.getVar(strValue+strCondition+strConditionField);
  }
  else if (isShortShow == "EX"){
    //alert(2);
    return mVs.getVar(strValue+"Select");
  }
  else {
    //alert(3);
    return mVs.getVar(strValue+strCondition+strConditionField+"Select");
  }

}

function searchCodeChinese(strValue,strCondition,strConditionField,isShortShow) {
  //alert(isShortShow);
  if (typeof(isShortShow) == "undefined") {
    //alert(1);
    //取得编码，如果没有找到，返回-1
    return mVs.getVar(strValue+strCondition+strConditionField+"Chinese");
  }
  else if (isShortShow == "EX"){
    //alert(2);
    return mVs.getVar(strValue+"Chinese");
  }
  else {
    //alert(3);
    return mVs.getVar(strValue+strCondition+strConditionField+"Chinese");
  }

}

/*************************************************************
 *                     清空错误信息
 *  参数  ：  没有
 *  返回值：  没有
 *************************************************************
 */
function clearShowCodeError()
{
  mCs.updateVar("ShowCodeError","","");       //清空错误信息
  mCs.updateVar("ShowCodeErrorCode","","");   //清空错误信息
}

function GetAbsoluteLocationEx(element)
{
	if(arguments.length!=1||element==null)
	{
		return null;
	}
	var elmt=element;
	var offsetTop=elmt.offsetTop;
	var offsetLeft=elmt.offsetLeft;
	var offsetWidth=elmt.offsetWidth;
	var offsetHeight=elmt.offsetHeight;
	while(elmt=elmt.offsetParent)
	{
		// add this judge
		if(elmt.style.position=='absolute'||elmt.style.position=='relative'
			||(elmt.style.overflow!='visible'&&elmt.style.overflow!=''))
		{
			break;
		}
		offsetTop+=elmt.offsetTop;
		offsetLeft+=elmt.offsetLeft;
	}
	return{absoluteTop:offsetTop,absoluteLeft:offsetLeft,
		offsetWidth:offsetWidth,offsetHeight:offsetHeight};
}

/*************************************************************
 *                     设置坐标位置
 *  参数  ：  需要参照的对象
 *  返回值：  设置ex,ey的值
 *************************************************************
 */
function setFieldPos(Field)
{
	var ex,ey;
	try
	{	
		var $obj = GetAbsoluteLocationEx(Field);
		
		ex=$obj.absoluteLeft ;
		ey=$obj.absoluteTop+25;
		
//		alert(ex+","+ey);
		mCs.updateVar("ShowCodeX","0",ex);
		mCs.updateVar("ShowCodeY","0",ey);
	}
	catch(ex)
	{}
}

function getCode(strCodeName,arrShowCodeObj,strCondition,strConditionField,refresh,showWidth,changeEven)
{
  var tCode;
    tCode=searchCode(strCodeName,strCondition,strConditionField);  //从代码区读取代码

    if (refresh) {
      tCode = false;
    try {
        mVs.deleteVar(strCodeName+strCondition+strConditionField);
        mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
    } catch(ex) {}
  }

    if (tCode == false && arrShowCodeObj != null){
      //请求服务器端，读取数据
        requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven);
        return false;
    }

    showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
}


/*************************************************************
 *                     为控件赋值
 *  参数  ： 无
 *  返回值： 无
 *************************************************************
 */
function closeCodeList()
{
  try
  {
    showFirstIndex=0;
    arrayBmCode=null;
    var disFlag = 0;
    if (document.all("spanCode").style.display == '') disFlag = 1;
//    document.all("spanCode").style.display ='none';
   //tongmeng 2008-04-30 Modify
   //增加对IE7的支持
  		if(document.activeElement.name==undefined||(oldField.name != document.activeElement.name && document.activeElement.name != "codeselect"))
		{
//			alert(document.activeElement.name);
			if (document.all("spanCode").style.display == '')
			{ 
				disFlag = 1;
				document.all("spanCode").style.display ='none';
			}
		}
		try
		{
			if(oldFieldKey != "")
			{
//				if(disFlag) oldFieldKey.focus();
			}
			// 设置对象的焦点丢失事件为空
			oldFieldKey.onblur="";
			oldField.onblur="";
		}
		catch(ex)
		{}

   // try { if (disFlag) oldFieldKey.focus(); } catch(ex) {}

  }
  catch(ex)
  {}
}

/**
 * 去掉字符串头尾空格
 * Example: trim(" XinYQ ") returns "XinYQ"<p>
 * @param strValue 字符串表达式
 * @return 头尾无空格的字符串表达式
 */
function trim(sSrcString)
{
	//使用正则表达式进行全局替换
	if (sSrcString != null && typeof(sSrcString) == "string")
	{
	    sSrcString = sSrcString.replace(/(^\s*)|(\s*$)/g, "");
	}
	return sSrcString;
}

/*************************************************************
 *                     定位到指定的代码位置
 *  参数  ： 无
 *  返回值： 无
 *************************************************************
 */
function goToSelect(strCodeName,Field,strCondition,strConditionField)
{
	//alert('111');
        var i=0;
        var arrayBmCode;
//          window.status=Field.value;
//		eobj = window.event;
        var eobj = arguments.callee.caller.arguments[0] || window.event;
        var key = eobj.which ? eobj.which : eobj.keyCode;
		if(key=="0"||eobj.type!="keyup"){
			arrayBmCode=null;//如果不是键盘输入而是双击等其他事件，则读取全部数据
		}else{
	        //找到对应代码名称的编码数组
	        arrayBmCode=searchCodeChinese(strCodeName,strCondition,strConditionField);
        }
        //var tSearchValue = 
        var tempSearch = new Array();
        if (arrayBmCode!=null)
        {
        	tempSearch  =arrayBmCode;
        	//tongmeng 2012-04-12 Modify
        	//支持模糊查询和拼音搜索
        	var temp = null;
        	var reg = null;
        	try{
        			if(tempSearch)
        			{
        	 			//temp = XqkChinese.createCTextArrayForLis(tempSearch);
        			  temp = tempSearch;
        				//alert(temp);
        	
        				var regContent  = trim(Field.value);
        				var tSerch = eval("/text:[^;]*"+regContent+"[^@]*/g");
        				var tSeachValue = temp.join('@');
        				var reg = tSeachValue.match(tSerch);
        				var temparrayBmCode = new Array();
        				if(reg!=null)
        				{
        					for(var i=0;i<reg.length;i++)
        					{
        						//"text:8611_北京分公司_beijingfengongsi;value:8611&北京分公司&北京分公司&北京市朝阳区朝外大街20号联合大厦四层&"
        						var splitArr = ((reg[i].split(";"))[1].split(":"))[1].split("&");
        						temparrayBmCode.push(splitArr);
        					}
        					showCodeListSearch(temparrayBmCode,strCodeName,strCondition,strConditionField);
        					try{
        						document.all("codeselect").children[0].selected=true;
        						}
        					catch(e){}
        				}
        		
         			}
         			if(reg==null||reg.length==0)
         			{
         				arrayBmCode=searchCode(strCodeName,strCondition,strConditionField);
         				if (arrayBmCode!=null){
            		for(i=0;i<arrayBmCode.length;i++)
            		{
              		var t_len = trim(Field.value).length;

              		//如果控件对象中的值等于编码数组中的某一项
              		if( arrayBmCode[i][0].substring(0,t_len) == trim(Field.value))                         //若是代码值已存在于数组中
              		{
	                		showFirstIndex = i;

                			//那么在页面上显示该编码表的时候，定位到那一项
                			document.all("codeselect").children[showFirstIndex].selected=true; 
                			return;
              		}
            		}
          		}
          	}
          }
          catch(e)
          {
          		//alert(e);
          }
        }
        else
        {
        	arrayBmCode=searchCode(strCodeName,strCondition,strConditionField);
     
         		 if (arrayBmCode!=null){
         			var vv = trim(Field.value);
         			 var t_len = vv.length;
         			
         	
            for(i=0;i<arrayBmCode.length;i++)
            {
              //如果控件对象中的值等于编码数组中的某一项
              if( arrayBmCode[i][0].substring(0,t_len) == vv)               //若是代码值已存在于数组中
              {
                showFirstIndex = i;

                //那么在页面上显示该编码表的时候，定位到那一项
                document.all("codeselect").children[showFirstIndex].selected=true;
                return;

              }
            }
          	}
        }
}
//input表单选中时，边框变色
function changecolor(){
  var elements = document.getElementsByTagName("input");
    for (var i=0; i < elements.length; i++) {
		var onfocus = elements[i].onfocus, onblur = elements[i].onblur;
		if (onfocus == null) {
            elements[i].onfocus = function() { 
				if (this.className != "warn") {
					this.style.borderColor = "rgba(31, 173, 254, 1)"; 
				}
			};
		}
		if (onblur == null) {
            elements[i].onblur = function() { 
				if (this.className != "warn") {
					this.style.borderColor = "#d4d4d4";
				}
			 
			};
		}
    }  
    var inputs = document.getElementsByTagName("input"); 
    for (var i=0; i < inputs.length; i++) {
          if(inputs[i].className =="codeno" || inputs[i].className =="code"  ){
            inputs[i].onfocus = function() { this.style.borderColor = "#d4d4d4"; };
            inputs[i].onblur = function() { this.style.borderColor = "#d4d4d4"; };
          }
    } 
}
function bind(elem, evt, func) {
  if (window.attachEvent){
    elem.attachEvent("on"+evt,func);
  }else{
    elem.addEventListener(evt,func,false);
  }
}
bind(window, 'load', changecolor);
//$(function (){
//	changecolor();
//	})
//嵌入快速搜索和拼音搜索
//注:为了减少与数据库的交互,快速搜索只搜索缓存中的数据.

