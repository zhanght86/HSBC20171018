/*****************************************************************
 *               Program NAME: ѡȡ���벢��ʾ
 *                 programmer:
 *                Create DATE:
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *     ͨ�ô����ѯ����ҳ��,���������صĿ����,����
 *     ������Ҫ��ʾ�����嵥ʱ���ô�ҳ��
 *
 *****************************************************************
 */
/**
* ��Ѱ�����ڣ�����CodeSelect��������
* ����
* ����
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
//Ѱ��������
var win = searchMainWindow(this);
if (win == false) { win = this; }

//�õ���ű������ݵ��ڴ�����,Ҫ��CVarData.js����Ҫ��һ������ΪVD��֡��
//��ŵ��Ǵ����ݿ�ı���ȡ���ı�������飬���ڲ����ݵĴ��
mVs=win.parent.VD.gVCode;
if(typeof(mVs)!="object")setTimeout("mVs=win.parent.VD.gVCode;",100);
//�õ���ű������ݵ��ڴ�����,Ҫ��CVarData.js����Ҫ��һ������ΪVD��֡��
//��ŵ���ҳ���ϵı�����λ�ã������ݿ����ȡ������������һЩ�ⲿ��������
mCs=win.parent.VD.gVSwitch;
if(typeof(mCs)!="object")setTimeout("mCs=win.parent.VD.gVSwitch;",100);
var _Code_FIELDDELIMITER    = "|";   //��֮��ķָ���
var _Code_RECORDDELIMITER   = "^";   //��¼֮��ķָ���
var arrayBmCode;                     //����������Ĵ���
var arrEasyQuery = new Array();          // ���ʹ��easyQuery()�õ��Ĳ�ѯ�������

var showFirstIndex=0;
var oEventField;    //�����¼���ԭʼ�ؼ�, Added by XinYQ on 2006-10-23


/**
* ��ȡ�ַ����Ĳ����Ӵ����ú����õ�c_Str�еĵ�c_i����c_Split�ָ���ַ���
* <p><b>Example: </b><p>
* <p>getStr("Minim|Hzm|Yt|", "2", "|") returns "Hzm"<p>
* @param c_Str �зָ�������ַ���
* @param c_i ȡ�ڼ����ָ��Ӵ�
* @param c_Split �ָ���
* @return ���ص�c_i���ָ��Ӵ�
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
 *  ��ʾ���루�ú���Ϊ��ʾ�������ں�����
 *  ����  ��  Field �����¼��Ŀؼ�;
 *            strCodeName ��������;
 *            arrShowCodeObj ��������ʾ����Ӧ�Ŀؼ���
 *            arrShowCodeOrder ������ʾ��Ӧ�ؼ��ͱ���˳��Ķ�Ӧ��ϵ
 *            arrShowCodeFrame ������ʾ��Ӧ��Frameָ��
 *  ����ֵ��  ���û�з���false,���򷵻�true
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
    	searchFlag = '1' ;//����Ĭ������
    }
    
     
    var ex,ey;
    var tCode;
    var Field;
    //����ƥ��Sql��䣬������where�Ӿ��Ժ�Ĳ���
    var strCondition = "";                                   //����Ĳ�ѯ����ֵ
    var strConditionField = "";                              //����Ĳ�ѯ�������ֶ���
    //���Ӵ���ѡ��Ĳ�ѯ����
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

  //����������ݿ��ѯ���ڶ��β����������
    if (arrShowCodeObj != null){
    //Field  ��һ�����鼯�ϣ���ʾҪ��ʾ�������ҳ���ϵĶ���
    Field = arrShowCodeObj[0];
    setFieldPos(Field);

    oldField = Field;
    oldFieldKey = "";
    //alert(oldField + "\n2:" + oldFieldKey);

//    Field.onblur = closeCodeList;      //Ϊ�ؼ����ӹر�CODESELECT�ķ���

    if (arrShowCodeOrder == null) {
      arrShowCodeOrder = [0];
    }

    if (objShowCodeFrame == null) {
      objShowCodeFrame = parent.fraInterface;//window.self;
    }

      try {
      //��һЩ�����ŵ��ͻ��˴�����
      mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
      mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
      mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);    //Frame��ָ��
    }
    catch(e) {
      //���³�ʼ����������
      mVs=parent.VD.gVCode;
      mCs=parent.VD.gVSwitch;
      var _Code_FIELDDELIMITER    = "|";   //��֮��ķָ���
      var _Code_RECORDDELIMITER   = "^";   //��¼֮��ķָ���
      var arrayBmCode;                     //����������Ĵ���
      var arrEasyQuery = new Array();            // ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
      var showFirstIndex=0;
      showCodeList(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven,searchFlag);
    }
    }

  //alert("strCondition:" + strCondition + "\nstrConditionField:" + strConditionField);
    tCode=searchCode(strCodeName,strCondition,strConditionField);  //�Ӵ�������ȡ����
    //ǿ��ˢ�²�ѯ
    if (refresh) {
      tCode = false;
    try {
        //����newһ��codeselect�����ʱ�򣬻���������������������ǿ��ˢ�µ�ʱ��Ҳ��Ҫdelete��������
        mVs.deleteVar(strCodeName+strCondition+strConditionField);
        mVs.deleteVar(strCodeName+"Select");
        mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
    } catch(ex) {}
  }
    if (tCode == false && arrShowCodeObj != null) {
      //����������ˣ���ȡ����
            //alert(strCondition);
            //alert(strConditionField);
        requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven,searchFlag);
        //��js��ֹ��ת������������������ҳ��ִ�У���ҳ���ѯ���ݺ󱣴浽ҳ���ϵ��ڴ�����
    //���ٴε��ø�js,������initializeCode��Ȼ��showCodeList,��ע��initializeCode�����ٵ����ã�
    //����ִ��js��������δ������Ѿ����˴��룬����������������
        return false;
    }

    //���������õ�������ͱ������ƴ��룬��ʾ����
   
    showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
  	
    //���Ҫ��ʾ�����ҳ��������
  //��ʾ���������ҳ������е�ֵ��ʹ�����λ����ӦField�е�ֵ����һ��
    if(Field!=null) {
      goToSelect(strCodeName,Field,strCondition,strConditionField);
      document.all("codeselect").style.borderColor = "#d4d4d4"; //2015-08-25 
    }

//      alert(mVs.getVar(strCodeName+strCondition+strConditionField));
//      alert(mVs.getVar(strCodeName+strCondition+strConditionField+"Select"));

    //XinYQ added on 2006-05-16
    //�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
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
 *  ��ʾ���루�ú���Ϊ��ʾ�������ں���
 *  ����  ��  Field �����¼��Ŀؼ�;
 *            strCodeName ��������;
 *            arrShowCodeObj ��������ʾ����Ӧ�Ŀؼ���
 *            arrShowCodeOrder ������ʾ��Ӧ�ؼ��ͱ���˳��Ķ�Ӧ��ϵ
 *            arrShowCodeFrame ������ʾ��Ӧ��Frameָ��
 *  ����ֵ��  ���û�з���false,���򷵻�true
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

  //���Ӵ���ѡ��Ĳ�ѯ������MINIM
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

  //����������ݿ��ѯ���ڶ��β����������
    if (arrShowCodeObj != null) {
    Field=arrShowCodeObj[0];

    oldField = Field;                  //����������⣬MINIM����
//    Field.onblur = closeCodeList;      //Ϊ�ؼ����ӹر�CODESELECT�ķ�����MINIM����
    oldFieldKey = "";

    setFieldPos(Field);

    if (arrShowCodeOrder == null) {
      arrShowCodeOrder = [0];
    }

    if (objShowCodeFrame == null) {
      objShowCodeFrame = parent.fraInterface;//window.self;
    }

      try {
      //��һЩ�����ŵ��ͻ��˴�����
      mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
      mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
      mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame��ָ��
    }
    catch(e) {
      //���³�ʼ����������
      mVs=parent.VD.gVCode;
      mCs=parent.VD.gVSwitch;
      var _Code_FIELDDELIMITER    = "|";   //��֮��ķָ���
      var _Code_RECORDDELIMITER   = "^";   //��¼֮��ķָ���
      var arrayBmCode;                     //����������Ĵ���
      var arrEasyQuery = new Array();            // ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
      var showFirstIndex=0;

      showCodeListEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
    }
    }

    tCode=searchCode(strCodeName,strCondition,strConditionField);                         //�Ӵ�������ȡ����

  //ǿ��ˢ�²�ѯ
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
    //�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
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
 *  ���Ҵ���(���̰����¼�)
 *  ����  ��  Field ��Ҫ��ʾ����Ŀؼ�;
 *            strCodeName ��������(Lx_Code);
 *            intFunctionIndex ��Ҫ��ֵ�ؼ���˳�� 1-ǰһ���ͱ���
 *                                                2-����ͺ�һ��;
 *            stationCode ����������վ.
 *  ����ֵ��  string  ��code �� null
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

    //���Ӵ���ѡ��Ĳ�ѯ������MINIM
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
      //�ύ�������Ƽ���Ϣ
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
        mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame��ָ��
      }
      catch(e) {
        //���³�ʼ����������
        mVs=parent.VD.gVCode;
        mCs=parent.VD.gVSwitch;
        var _Code_FIELDDELIMITER    = "|";   //��֮��ķָ���
        var _Code_RECORDDELIMITER   = "^";   //��¼֮��ķָ���
        var arrayBmCode;                     //����������Ĵ���
        var arrEasyQuery = new Array();          // ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
        var showFirstIndex=0;

          showCodeListKey(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
        }

      //�ύ�������Ƽ���Ϣ
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
    //�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
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
 *  ���Ҵ���(���̰����¼�)
 *  ����  ��  Field ��Ҫ��ʾ����Ŀؼ�;
 *            strCodeName ��������(Lx_Code);
 *            intFunctionIndex ��Ҫ��ֵ�ؼ���˳�� 1-ǰһ���ͱ���
 *                                                2-����ͺ�һ��;
 *            stationCode ����������վ.
 *  ����ֵ��  string  ��code �� null
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

    //���Ӵ���ѡ��Ĳ�ѯ������MINIM
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
      //�ύ�������Ƽ���Ϣ
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
        mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame��ָ��
      }
      catch(e) {
        //���³�ʼ����������
        mVs=parent.VD.gVCode;
        mCs=parent.VD.gVSwitch;
        var _Code_FIELDDELIMITER    = "|";   //��֮��ķָ���
        var _Code_RECORDDELIMITER   = "^";   //��¼֮��ķָ���
        var arrayBmCode;                     //����������Ĵ���
        var arrEasyQuery = new Array();          // ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
        var showFirstIndex=0;

          showCodeListKeyEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
        }

      //�ύ�������Ƽ���Ϣ
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
    //�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
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
 *  ��ʼ������
 *  ����  ��  strCodeName����������
 *  ����ֵ��  boolean   true���ҵ�����Ĵ���   false��δ�ҵ�
 *************************************************************
 */
function initializeCode(strCodeName, codeCondition, conditionField,searchFlag)
{

  var i,i1,j,j1;
  var strValue;                         //��ŷ������˷��صĴ�������
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //��ų�ʼ������ʱ��
  var t_Str;

  var strCodeSelect = "";

  clearShowCodeError();

  strValue  = getCodeValue(strCodeName);              //�õ��������˶�ȡ������

  arrRecord = strValue.split(_Code_RECORDDELIMITER);  //����ַ������γɷ��ص�����

  t_Str     = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);

  if (t_Str!="0")                                     //�����Ϊ0��ʾ��������ִ�з�������
  {
    mCs.updateVar("ShowCodeError",getStr(arrRecord[0],2,_Code_FIELDDELIMITER));   //�����󱣴浽�ñ�����
    mCs.updateVar("ShowCodeErrorCode",t_Str);   //�����󱣴浽�ñ�����
    return false;
  }

  i1=arrRecord.length;
  for (i=1;i<i1;i++)
  {
    arrField  = arrRecord[i].split(_Code_FIELDDELIMITER); //����ַ���,��ÿ����¼���Ϊһ������
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

  mVs.addArrVar(strCodeName+codeCondition+conditionField,"",arrCode); //�����Ƿ������ݴӷ������˵õ�,�����øñ���
  //var temp
  if(searchFlag!=0)
  {
  	mVs.addArrVar(strCodeName+codeCondition+conditionField+"Chinese","",XqkChinese.createCTextArrayForLis(arrCode)); //�����Ƿ������ݴӷ������˵õ�,�����øñ���
  }

  //XqkChinese.createCTextArrayForLis(tempSearch);
  mVs.addVar(strCodeName+codeCondition+conditionField); //�����Ƿ������ݴӷ������˵õ�,�����øñ���
  mVs.addVar(strCodeName+codeCondition+conditionField+"Select","",strCodeSelect); //�����Ƿ������ݴӷ������˵õ�,�����øñ���

  return true;
}

/*************************************************************
 *                     ��ʼ������
 *  ����  ��  strCodeName����������
 *  ����ֵ��  boolean   true���ҵ�����Ĵ���   false��δ�ҵ�
 *************************************************************
 */
function initializeCodeEx(strCodeName,cField)
{
    var i,i1,j,j1;
  var strValue;                         //��ŷ������˷��صĴ�������
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //��ų�ʼ������ʱ��
  var t_Str;

  var strCodeSelect = "";

  clearShowCodeError();
  try
  {
  strValue  =  $(cField).attr("CodeData");              //�õ��������˶�ȡ������//
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
	 alert("û���ڿͻ���������������!");
	 return false;
	  }
  }

  arrRecord = strValue.split(_Code_RECORDDELIMITER);  //����ַ������γɷ��ص�����

  t_Str     = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);

  if (t_Str!="0")                                     //�����Ϊ0��ʾ��������ִ�з�������
  {
    mCs.updateVar("ShowCodeError",getStr(arrRecord[0],2,_Code_FIELDDELIMITER));   //�����󱣴浽�ñ�����
    mCs.updateVar("ShowCodeErrorCode",t_Str);   //�����󱣴浽�ñ�����
    return false;
  }

  i1=arrRecord.length;
  for (i=1;i<i1;i++)
  {
    arrField  = arrRecord[i].split(_Code_FIELDDELIMITER); //����ַ���,��ÿ����¼���Ϊһ������
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
  mVs.addArrVar(strCodeName,"",arrCode);                 //�����Ƿ������ݴӷ������˵õ�,�����øñ���
  mVs.addArrVar(strCodeName+"Chinese","",XqkChinese.createCTextArrayForLis(arrCode)); //�����Ƿ������ݴӷ������˵õ�,�����øñ���

  mVs.deleteVar(strCodeName+"Select");
  mVs.addVar(strCodeName+"Select","",strCodeSelect); //�����Ƿ������ݴӷ������˵õ�,�����øñ���

  return arrCode;
}



/*************************************************************
 *                     �õ�����ֵ��
 *  ����  ��  strCodeName����������
 *  ����ֵ��  string     ������ֵ��
 *************************************************************
 */
function getCodeValue(strCodeName)
{
  var reStr;
  //try
  //    {
      reStr= parent.EX.fm.txtVarData.value;     //�Ӹ�frame��ȡ�ôӷ�������ȡ�õ�ֵ
    //}
    //catch(ex)
    //{}
    return reStr;
}

/*************************************************************
 *                     ���������
 *  ����  ��  intElementIndex ��Ҫ��ʾ����Ŀؼ���������;
 *            strCodeName ��������(Lx_Code);
 *            intFunctionIndex ��Ҫ��ֵ�ؼ���˳�� 1-ǰһ���ͱ���
 *                                                2-����ͺ�һ��;
 *            stationCode ����������վ��
 *            ex,ey ��ʾ��λ��.
 *  ����ֵ��  ��
 *************************************************************
 */
function requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven,searchFlag)
{
//  alert(strCodeName+strCondition+strConditionField);
  var objFrame;
  objFrame=mCs.getVar("ShowCodeFrame");
    //���������
//      try
//      {
      //alert(strCodeName + "\n" + objFrame.name);
        parent.EX.fm.txtCodeName.value       = strCodeName;     //��������
        parent.EX.fm.txtFrameName.value      = objFrame.name;   //Frame������
        parent.EX.fm.txtVarData.value        = "";              //����ʱ��Ҫ�Ŀռ�
        parent.EX.fm.txtOther.value          = "";              //�ύʱ����������

        parent.EX.fm.txtCodeCondition.value  = strCondition;       //��ѯ����
        parent.EX.fm.txtConditionField.value = strConditionField; //��ѯ�����ֶ�

        parent.EX.fm.txtShowWidth.value      = showWidth; //��ѯ�����ֶ�
        parent.EX.fm.changeEven.value        = changeEven;
        
        parent.EX.fm.searchFlag.value        = searchFlag;
        parent.EX.fm.submit();
//  }
//  catch(exception){}
}

/*************************************************************
 *  ������ķ�ʽ��ʾ�����б�
 *  ����  ��  arrCode     ���������ͱ�������б�����Ϣ������;
 *            strCodeName ��Ҫ��ʾ�Ĵ�������
 *  ����ֵ��  ��
 *************************************************************
 */
function showCodeList1(arrCode,strCodeName,strCondition,strConditionField,showWidth,changeEven)
{
	 //tongmeng 2008-04-30 add
    //���Ӷ�IE7��֧��
    document.body.onclick=function (){closeCodeList();}
    var strValue;
    var flag=false;
    var strText;
    var arrCount;
    var fm;
    //add by yt ,���ӿ�ȵ�����Ӧ
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

    //�����ݿ�ȡ���ݵ����
   
   	var strCode = searchCode(strCodeName,strCondition,strConditionField,1);
//    alert(strCode);
   		 if (strCode) {
      		strText = strText + strCode;
     		  flag = true;
    	}

    	if (!flag) {
      	//��������Դ�����
      	strCode = searchCode(strCodeName,strCondition,strConditionField,"EX");

      	if (strCode) {
          strText = strText + strCode;
          flag = true;
        }
    	}
  	
//	prompt('',strText);
    //alert(strCode);
    //add by yt , ʹ��CodeSelect�Ŀ���ܹ��Զ���Ӧ
    arrCount=arrCode.length;
    if (arrCount>100 ) arrCount = 100;//����Ч�ʣ�ֻ����ǰ100�����
    for(i=0;i<arrCount;i++)
    {
      tStr = arrCode[i][1];
      try{
        tCurLen = tStr.length;
        }catch (ex){}
      if (tCurLen>tMaxLen) tMaxLen = tCurLen;
    }

    tCurLen = 10 * tMaxLen ;    //XinYQ modified on 2006-06-12 : ԭ���Ļ��� 25 ����,������� Station ����
  //  if (tCurLen <= 255) tCurLen = 255;  //ȡ��С���    //XinYQ modified on 2006-06-12 : Ĭ��һ�� title + һ�� input �߽���
// alert(tCurLen);
  if (tCurLen <= 200) tCurLen = 200; //tongmeng 20090409modify ��ʼ����
    if (tCurLen >= 2000) tCurLen = 2000;    //ȡ�����
    //��showWidthΪnullʱ���⴦��
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
        document.all("spanCode").style.left=mCs.getVar("ShowCodeX") + "px";    //��ȡ����������������X
        document.all("spanCode").style.top=mCs.getVar("ShowCodeY") + "px";     //��ȡ����������������Y
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
    //���Ӷ�IE7��֧��
    document.body.onclick=function (){closeCodeList();}
    var strValue;
    var flag=false;
    var strText;
    var arrCount;
    var fm;
    //add by yt ,���ӿ�ȵ�����Ӧ
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
    //add by yt , ʹ��CodeSelect�Ŀ���ܹ��Զ���Ӧ
    arrCount=arrCode.length;
    if (arrCount>100 ) arrCount = 100;//����Ч�ʣ�ֻ����ǰ100�����
    for(i=0;i<arrCount;i++)
    {
      tStr = arrCode[i][1];
      try{
        tCurLen = tStr.length;
        }catch (ex){}
      if (tCurLen>tMaxLen) tMaxLen = tCurLen;
    }

    tCurLen = 10 * tMaxLen ;    //XinYQ modified on 2006-06-12 : ԭ���Ļ��� 25 ����,������� Station ����
  //  if (tCurLen <= 255) tCurLen = 255;  //ȡ��С���    //XinYQ modified on 2006-06-12 : Ĭ��һ�� title + һ�� input �߽���
// alert(tCurLen);
  if (tCurLen <= 200) tCurLen = 200; //tongmeng 20090409modify ��ʼ����
    if (tCurLen >= 2000) tCurLen = 2000;    //ȡ�����
    //��showWidthΪnullʱ���⴦��
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
        document.all("spanCode").style.left=mCs.getVar("ShowCodeX");    //��ȡ����������������X
        document.all("spanCode").style.top=mCs.getVar("ShowCodeY");     //��ȡ����������������Y
        document.all("spanCode").style.display ='';
    }
    else
    {
        document.all("spanCode").style.display ='none';
    }
		

    //document.all("codeselect").focus();
   
}


/*************************************************************
 *  Ϊ�ؼ���ֵ
 *  ����  ��  Field ��Ҫ��ֵ�Ŀؼ�
 *  ����ֵ��  ��
 *************************************************************
 */
function setFieldValue(Field,strCodeName,strCondition,strConditionField,changeEven)
{
    var tFldCode;               //Ϊһ�������ļ�¼����001,�ܹ�˾,�ܹ�˾��Ϣ
    var tArrDisplayObj;         //��Ҫ��ʾ���Ķ���
    var tArrDisplayOrder;       //��ʾ��˳��
    var i,iMax;
    var strChange="";

    //�õ�һ�������¼
    tFldCode = getOneCode(strCodeName,Field.value,strCondition,strConditionField);
    tArrDisplayObj   = mCs.getVar("ShowCodeObj");     //�õ���Ҫ��ʾ�Ķ���
    tArrDisplayOrder = mCs.getVar("ShowCodeOrder");   //�õ���ʾʱ��Ӧ��˳��
    iMax = tArrDisplayObj.length;

    try
    {
        for (i=0; i<iMax; i++)
        {
        	if(typeof(tArrDisplayObj[i]) == 'object')
        		tArrDisplayObj[i].value = tFldCode[tArrDisplayOrder[i]];  //������ʾ˳��������ʾ����
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
    //ʹ�û�ѡ��ֵ�󣬿ؼ��Ա��ֽ���
   // alert(oldField.name + "\n2:" + oldFieldKey);
   try { if (oldField != "") oldField.focus(); } catch(ex) {}
    try { if (oldFieldKey != "") oldFieldKey.focus(); } catch(ex) {}
}

/*************************************************************
 *              ��Code�ڴ��ж�ȡ��Code����Ϣ
 *  ����  ��  strCodeName:Code������(����)
 *            strCode    :Code�ı���
 *            index �ؼ���������
 *  ����ֵ��  ��
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
 *                      ���ұ���
 *  ����  ��  strValue����������
 *  ����ֵ��  string  ��code �� false
 *************************************************************
 */
function searchCode(strValue,strCondition,strConditionField,isShortShow) {
	
  //alert(isShortShow);
  if (typeof(isShortShow) == "undefined") {
    //alert(1);
    //ȡ�ñ��룬���û���ҵ�������-1
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
    //ȡ�ñ��룬���û���ҵ�������-1
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
 *                     ��մ�����Ϣ
 *  ����  ��  û��
 *  ����ֵ��  û��
 *************************************************************
 */
function clearShowCodeError()
{
  mCs.updateVar("ShowCodeError","","");       //��մ�����Ϣ
  mCs.updateVar("ShowCodeErrorCode","","");   //��մ�����Ϣ
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
 *                     ��������λ��
 *  ����  ��  ��Ҫ���յĶ���
 *  ����ֵ��  ����ex,ey��ֵ
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
    tCode=searchCode(strCodeName,strCondition,strConditionField);  //�Ӵ�������ȡ����

    if (refresh) {
      tCode = false;
    try {
        mVs.deleteVar(strCodeName+strCondition+strConditionField);
        mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
    } catch(ex) {}
  }

    if (tCode == false && arrShowCodeObj != null){
      //����������ˣ���ȡ����
        requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven);
        return false;
    }

    showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
}


/*************************************************************
 *                     Ϊ�ؼ���ֵ
 *  ����  �� ��
 *  ����ֵ�� ��
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
   //���Ӷ�IE7��֧��
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
			// ���ö���Ľ��㶪ʧ�¼�Ϊ��
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
 * ȥ���ַ���ͷβ�ո�
 * Example: trim(" XinYQ ") returns "XinYQ"<p>
 * @param strValue �ַ������ʽ
 * @return ͷβ�޿ո���ַ������ʽ
 */
function trim(sSrcString)
{
	//ʹ��������ʽ����ȫ���滻
	if (sSrcString != null && typeof(sSrcString) == "string")
	{
	    sSrcString = sSrcString.replace(/(^\s*)|(\s*$)/g, "");
	}
	return sSrcString;
}

/*************************************************************
 *                     ��λ��ָ���Ĵ���λ��
 *  ����  �� ��
 *  ����ֵ�� ��
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
			arrayBmCode=null;//������Ǽ����������˫���������¼������ȡȫ������
		}else{
	        //�ҵ���Ӧ�������Ƶı�������
	        arrayBmCode=searchCodeChinese(strCodeName,strCondition,strConditionField);
        }
        //var tSearchValue = 
        var tempSearch = new Array();
        if (arrayBmCode!=null)
        {
        	tempSearch  =arrayBmCode;
        	//tongmeng 2012-04-12 Modify
        	//֧��ģ����ѯ��ƴ������
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
        						//"text:8611_�����ֹ�˾_beijingfengongsi;value:8611&�����ֹ�˾&�����ֹ�˾&�����г�����������20�����ϴ����Ĳ�&"
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

              		//����ؼ������е�ֵ���ڱ��������е�ĳһ��
              		if( arrayBmCode[i][0].substring(0,t_len) == trim(Field.value))                         //���Ǵ���ֵ�Ѵ�����������
              		{
	                		showFirstIndex = i;

                			//��ô��ҳ������ʾ�ñ�����ʱ�򣬶�λ����һ��
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
              //����ؼ������е�ֵ���ڱ��������е�ĳһ��
              if( arrayBmCode[i][0].substring(0,t_len) == vv)               //���Ǵ���ֵ�Ѵ�����������
              {
                showFirstIndex = i;

                //��ô��ҳ������ʾ�ñ�����ʱ�򣬶�λ����һ��
                document.all("codeselect").children[showFirstIndex].selected=true;
                return;

              }
            }
          	}
        }
}
//input��ѡ��ʱ���߿��ɫ
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
//Ƕ�����������ƴ������
//ע:Ϊ�˼��������ݿ�Ľ���,��������ֻ���������е�����.

