/*****************************************************************
 *  Program NAME: ͨ�ò�ѯ������EasyQueryVer3.js
 *  programmer: ����
 *  Create DATE: 2002-10-19
 *  Modify programmer:
 *  Modify DATE:
 *****************************************************************
 *  ͨ�ò�ѯ����SQL���Ĳ�ѯ,Ȼ�󽫲�ѯ����Զ�ά�������ʽ����
 *  ˵����������ʹ�ò�����Common.js�ж����ȫ������
 *****************************************************************
 */
var sqlCacheWindow; //ָ�����ڻ���Ĵ���
var mLargeFlag; //����������־
var mLimitFlag; //ͻ��Ĭ��200������, XinYQ added on 2006-09-30

/**
 * ���ò�ѯ�ӿں���
 * @param strSql - SQL����ַ���
 * @param synchronization - ͬ�첽���ã���1��ͬ������0���첽��Ĭ��ͬ��
 * @param useCache - ʹ�û������ã���1��ʹ�ã���0����ʹ�ã�Ĭ�ϲ�ʹ��
 * @param strStart - ָ������������Ĭ�ϴӵ�һ����¼��ʼ��ѯ
 * @param strLargeFlag - ����������ѯ������10000����¼ʹ�ã���1��ʹ�ã���0����ʹ�ã�Ĭ�ϲ�ʹ��
 * @param strLimitFlag - ͻ��Ĭ��200������(С��1000��)����1��ʹ�ã���0����ʹ�ã�Ĭ�ϲ�ʹ�ã���strLargeFlag��Чʱ�˲�����Ч
 * @return ��ѯ����ַ�������ʽΪ���׼�¼��0|��¼���������ԡ�^���ָ���¼���ԡ�|���ָ��ֶΡ�ʧ�ܷ���FALSE
 */
function easyQueryVer3(strSql, synchronization, useCache, strStart, strLargeFlag, strLimitFlag)
{  
    //strSql �ӿڲ�������
    if (typeof(strSql) == "undefined" || strSql == "")
    {
        alert("easyQueryVer3 ��Ҫ����һ��SQL��䣡");
        return false;
    }
    else
    {
        //���JSPҳ��Ļ������⣬ʹSQL���ÿʱ���ǲ�ͬ��
        var strDate = Date.parse(new Date());
        var tStrSql = strSql.toLowerCase();
        if (tStrSql.indexOf("where") != -1)
        {
            //��������仰�ĺ��壿ֱ���滻����ok�ˣ�
            tStrSql = strSql.substring(tStrSql.indexOf("where"), tStrSql.indexOf("where")+5);
            strSql = strSql.replace("where", "where '" + strDate + "'='" + strDate + "' and ");
        }
        //���=��%��?����JS��JSP֮��Ĵ�������
        //
        // Kevin, 2006-08-04
        // Conversion is not needed while using XMLHttp
        //
        // while(strSql.indexOf("=") != -1)
        //{
        //  strSql = strSql.replace("=", "%3D");
        //}
        while(strSql.indexOf("%25") != -1) {
            strSql = strSql.replace("%25", "%");
        }

        while(strSql.indexOf("%%") != -1) {
            strSql = strSql.replace("%%", "%");
        }
        //while(strSql.indexOf("?") != -1)
        //{
        //  strSql = strSql.replace("?", "%3F");
        //}
        //while(strSql.indexOf("+") != -1)
        //{
        //  strSql = strSql.replace("+", "%2B");
        //}
    }
    //synchronization �ӿڲ�������
    if (typeof(synchronization) == "undefined")
    {
        synchronization = true;
    }
    else if (synchronization == "0")
        {
            synchronization = false;
        }
        else
        {
            synchronization = true;
        }
    //useCache �ӿڲ�������
    if (useCache == "1")
    {
        if (sqlCacheWindow == null)
        {
            //����TOP���ں͸��崰�ڣ�ͨ��ȫ�ֱ���sqlCacheIndex�ҵ���������ҳ��
            var i=0;
            var tLength = top.frames.length;
            while (i < tLength)
            {
            	try{
            		if (top.frames[i].sqlCacheIndex != null)
                    {
                        sqlCacheWindow = top.frames[i];
                    }
            	}catch(e){
            		console.log(e);
            	}
                i++;
            }
        }
        //�ж��Ƿ������ڸ�����
        if (sqlCacheWindow == null && top.sqlCacheIndex != null)
        {
            sqlCacheWindow = top;
        }
        if (sqlCacheWindow == null)
        {
            sqlCacheWindow = false;
        }
        useCache = sqlCacheWindow;
    }
    else
    {
        useCache = false;
    }
    //strStart �ӿڲ�������
    if (typeof(strStart) == "undefined" || (typeof(useCache) == "string" && strStart == ""))
    {
        strStart = "1";
    }
    //strLargeFlag �ӿڲ�������
    if (typeof(strLargeFlag) == "undefined" || (typeof(strLargeFlag) == "string" && strLargeFlag == ""))
    {
        strLargeFlag = "0";
        mLargeFlag = strLargeFlag;
    }
    else
    {
        mLargeFlag = strLargeFlag;
    }
    //strLimitFlag �ӿڲ�������
    if (typeof(strLimitFlag) == "undefined" || (typeof(strLimitFlag) == "string" && strLimitFlag == ""))
    {
        strLimitFlag = "0";
        mLimitFlag = strLimitFlag;
    }
    else
    {
        mLimitFlag = strLimitFlag;
    }
    //urlStr����ѯ����URL�Ͳ�ѯ����
    var urlStr = "../common/easyQueryVer3/EasyQueryVer3Window.jsp?strSql=" + strSql + "&strStart=" + strStart + "&LargeFlag=" + strLargeFlag + "&LimitFlag=" + strLimitFlag;
    //sFeatures����ѯ������ʽ
    var sFeatures = "status:no;help:0;close:0;dialogWidth:0px;dialogHeight:0px;resizable=1";
    var strQueryResult = "";    //��ѯ����ַ���
    var arrQueryResult = new Array();   //��ѯ�������
    var sqlCacheFlag = false;
    //��黺�棬������ڣ��������ݣ������ڷ���FALSE����SQL������ظ�����Ҫ���в�ѯ
    if (typeof(useCache) == "object")
    {
        sqlCacheFlag = useCache.SqlCache(strSql);
    }
    if (!sqlCacheFlag && synchronization)
    {
        //ͬ����ѯ
        //add by wangyc ��ΪXMLHTTP��ʽ
        var strURL = "../common/easyQueryVer3/EasyQueryXML.jsp";
        //
        // Kevin, 2006-08-04
        // Make sure you have the correct order of params
        // 1. strSql; 2. strStart; 3. strLargeFlag; 4. strLimitFlag
        //
        var strPara = strSql + "&" + strStart + "&" + strLargeFlag + "&" + strLimitFlag;
        //Request = new ActiveXObject("Microsoft.XMLHTTP");
        //tongmeng 2007-10-15 modify
        //�����ݴ���
        //���IE��������������������������Ĳ�ͬ��ʽд��ͬ�Ĵ���
        var xmlhttp
        if(window.XMLHttpRequest){
        //���firefox,mozillaz,opera,IE7,IE8
            xmlhttp = new XMLHttpRequest();
            //�����޸�ĳЩMozillaz�����bug
            if(xmlhttp.overrideMimeType)
            {
                xmlhttp.overrideMimeType("text/xml");
            }
        }
        else if(window.ActiveXObject){
            //���IE6��IE5.5��IE5
            //���������Դ���XMLHttpRequest���󣬱�����js������
            var activeName=["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];

    //var activeName= ['MSXML2.XMLHTTP.6.0','MSXML2.XMLHTTP.5.0', //'MSXML2.XMLHTTP.4.0','MSXML2.XMLHTTP.3.0',//'MSXML2.XMLHTTP','Microsoft.XMLHTTP']; 
            for(var i =0;i<activeName.length;i++)
            {//ȡ��һ���ؼ���������XMLHttpRequest���󣬴����ɹ�����ֹѭ�����������ʧ�ܿ��Լ�������
            //�����׳��쳣����������
                try{
                    xmlhttp= new ActiveXObject(activeName[i]);
                    break;
                }catch(e){
                }
            }
            
        }
        //ȷ��XMLHttpRequest�����Ѿ������ɹ�
        if(!xmlhttp)
        {
            alert("XmlHttpRequest����ʧ��");
            return;
        }    

        xmlhttp.open("POST",strURL, false);
//        xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        xmlhttp.send(strPara);
//      xmlhttp.open("GET", strURL, false);
//      xmlhttp.send(null);
        try
        {
            strQueryResult = xmlhttp.responseText;
            if (strQueryResult != null && typeof(strQueryResult) == "string")
            {
            	strQueryResult = strQueryResult.replace(/\r\n/g,"");
                strQueryResult = trim(strQueryResult);
            }
        }
        catch (ex)
        {
            alert("���ݷ��س���" + ex.message);
        }
    }
    else if (!sqlCacheFlag && !synchronization)
        {
            //alert("�첽��ѯ��");
            //�첽��ѯ
            window.open(urlStr, "", sFeatures);
            return false;
        }
    if (!sqlCacheFlag && typeof(useCache) == "object")
    {
        useCache.setQueryResult(strQueryResult);    //�����ѯ���
    }
    if (sqlCacheFlag)
    {
        strQueryResult = sqlCacheFlag;  //��������������ݣ������в�ѯ��ֱ�ӻ�ȡ����
    }
    //�����ַ�ת�����ú�����LH����
    if (strQueryResult.indexOf("@@Enter")!=-1 || strQueryResult.indexOf("@@DouQuot")!=-1 || strQueryResult.indexOf("@@SinQuot")!=-1)
    {
        try
        {
            strQueryResult = Conversion(strQueryResult);
        }
        catch(e)
        {}
    }
    //����Լ�����ص��ַ�����ʽ�����޸�
    if (typeof(strQueryResult) == "string" && strQueryResult.substring(0, strQueryResult.indexOf("|")) != "0")
    {
        return false;
    }
    else
    {
        return strQueryResult;
    }
}

/**
 * ����ѯ�����������������ҳ��ʱ����ת��ѯ���
 * @param strQueryResult - ��Ҫ������ת������
 */
function transferQueryResult(strQueryResult)
{
    if (typeof(sqlCacheWindow) == "object")
    {
        sqlCacheWindow.setQueryResult(strQueryResult);
    }
}

/**
 * �첽��ѯ��ȡ����ӿڲ���
 * @param strQueryResult - �첽��ѯ����ַ���
 */
function afterEasyQueryVer3(strQueryResult)
{
    alert("�첽��ѯ��δ���:"+strQueryResult);
}

/**
 * ���ò�ѯ�ӿں���
 * @param strSql - SQL����ַ���
 * @param synchronization - ͬ�첽���ã���1��ͬ������0���첽��Ĭ��ͬ��
 * @param useCache - ʹ�û������ã���1��ʹ�ã���0����ʹ�ã�Ĭ��ʹ��
 * @param intStart - ָ������������Ĭ�ϴӵ�һ����¼��ʼ��ѯ
 * @param notUseEasyQuery - ��EASYQUERY�����ַ�����ǣ��Լ���д��ѯ��̨ʱʹ�ã���1���ǣ���0���ǣ�Ĭ�Ϸ�
 * @param notUseTurnPage - ��ʹ�÷�ҳ���ܱ�ǣ���1���ǣ���0���ǣ�Ĭ�Ϸ�
 * @return ��ѯ������飬ʧ�ܷ���NULL
 */
function easyExecSql(strSql, synchronization, useCache, strStart, notUseEasyQuery, notUseTurnPage)
{
    var strEasyQueryVer3Result;
    strEasyQueryVer3Result = easyQueryVer3(strSql, synchronization, useCache, strStart);
    if (!strEasyQueryVer3Result)
    {
        return null;
    }
    else if (strEasyQueryVer3Result.substring(0, strEasyQueryVer3Result.indexOf("|")) != "0")
        {
            return null;
        }
        else
        {
            return decodeEasyQueryResult(strEasyQueryVer3Result, notUseEasyQuery, notUseTurnPage);
        }
}

/**
 * ���Լ����ʽ�ַ�������ά���麯��
 * ����ѯ����ַ�����ֵ���ѯ��������У��ַ���ͷ�����ܼ�¼����Ϣ
 * ����뷭ҳ�������ʹ�ã�������turnPageȫ�ֶ��󣩣������������С��turnPage.pageLineNum * turnPage.blockPageNum
 * ������ʾ�С�ҳ��turnPage.pageLineNum * turnPage.blockPageNum���ô������ݿ��ѯ���������д����
 * @param strResult - Լ����ʽ�ַ������ԡ�^���ָ���¼���ԡ�|���ָ��ֶ�
 * @param notUseEasyQuery - ��EASYQUERY�����ַ�����ǣ��Լ���д��ѯ��̨ʱʹ�ã���1���ǣ���0���ǣ�Ĭ�Ϸ�
 * @param notUseTurnPage - ��ʹ�÷�ҳ���ܱ�ǣ���1���ǣ���0���ǣ�Ĭ�Ϸ�
 * @param otherTurnPage - ʹ�������ķ�ҳ���󣬶�����Ĭ�ϵ�turnPage���󣬱�����һ��turnPageClass�Ķ���
 * @return ��ά���飬ʧ�ܷ���NULL
 */
function decodeEasyQueryResult(strResult, notUseEasyQuery, notUseTurnPage, otherTurnPage)
{
    var arrEasyQuery = new Array();
    var arrRecord = new Array();
    var arrField = new Array();
    var recordNum, fieldNum, i, j;
    var cTurnPage;
    if (typeof(otherTurnPage)=="object")
    {
        cTurnPage = otherTurnPage;
    }
    else
    {
        try
        {
            cTurnPage = turnPage;
        }
        catch(e)
        {}
    }
    if (typeof(strResult) == "undefined" || strResult == "" || strResult == false)
    {
        return null;
    }

    //���ó���������ǿ�ݴ���
    if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
    if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";
    try
    {
        arrRecord = strResult.split(RECORDDELIMITER);   //��ֲ�ѯ������õ���¼����
        //���⴦����ѯ����ַ�����λ������������������¼������Ϣ
        if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object")
        {
            cTurnPage.queryAllRecordCount = arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1);
        }
        if(typeof(notUseEasyQuery)=="undefined" || notUseEasyQuery=="" || notUseEasyQuery==0)
        {
            if (arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1) != "" && arrRecord[0].substring(arrRecord[0].indexOf(FIELDDELIMITER) + 1) == 0)
            {
                return null;
            }
            arrRecord.shift();
        }
        recordNum = arrRecord.length;
//      for(i=0; i<recordNum; i++)
        var i=0;
        while(i<recordNum)
        {
            arrField = arrRecord[i].split(FIELDDELIMITER);  //��ּ�¼���õ��ֶ�����
            fieldNum = arrField.length;
            arrEasyQuery[i] = new Array();
//          for(j=0; j<fieldNum; j++)
            var j=0;
            while(j<fieldNum)
            {
            	// Update by YaoYi in 2011-10-8 for bug.
            	if (arrField[j].indexOf("</script>") > 0) {
            		arrEasyQuery[i][j] = arrField[j].split("\';")[0];
            	} else {
                    arrEasyQuery[i][j] = arrField[j];   //�γ�����Ϊ��¼����Ϊ�ֶεĶ�ά����	
            	}
                j++;
            }
            i++;
        }

    }
    catch(ex)
    {
        alert("�������ʧ�ܣ�����ԭ���ǣ�" + ex);
        return null;
    }
    //��Ϸ�ҳ���ƽ�����ʾ���ݴ���
    if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object" && cTurnPage.useSimulation==0)
    {
        var arrQueryResultNum = arrEasyQuery.length;
//      for (i=cTurnPage.dataBlockNum; i<arrQueryResultNum; i++)
        var i=cTurnPage.dataBlockNum;
        while(i<arrQueryResultNum)
        {
            arrEasyQuery.pop();
            i++;
        }
    }
    else if ((typeof(notUseTurnPage)=="undefined" || notUseTurnPage==0) && typeof(cTurnPage)=="object" && cTurnPage.useSimulation==1)
        {
            cTurnPage.blockPageNum = cTurnPage.queryAllRecordCount / cTurnPage.pageLineNum;
        }
    return arrEasyQuery;
}

/**
 * ���Լ����ʽ�ַ�������ά���麯��
 * ����ѯ����ַ�����ֵ���ѯ��������У��ַ���ͷ�����ܼ�¼����Ϣ
 * ����뷭ҳ�������ʹ�ã�������turnPageȫ�ֶ��󣩣������������С��turnPage.pageLineNum * turnPage.blockPageNum
 * ������ʾ�С�ҳ��turnPage.pageLineNum * turnPage.blockPageNum���ô������ݿ��ѯ���������д����
 * @param strResult - Լ����ʽ�ַ������ԡ�^���ָ���¼���ԡ�|���ָ��ֶ�
 * @param notUseEasyQuery - ��EASYQUERY�����ַ�����ǣ��Լ���д��ѯ��̨ʱʹ�ã���1���ǣ���0���ǣ�Ĭ�Ϸ�
 * @param notUseTurnPage - ��ʹ�÷�ҳ���ܱ�ǣ���1���ǣ���0���ǣ�Ĭ�Ϸ�
 * @param otherTurnPage - ʹ�������ķ�ҳ���󣬶�����Ĭ�ϵ�turnPage���󣬱�����һ��turnPageClass�Ķ���
 * @return ��ά���飬ʧ�ܷ���NULL
 */
function decodeEasyQueryResultV2(statment,strResult,notUseEasyQuery)
{
    var arrEasyQuery = new Array();
    var arrRecord = new Array();
    var arrField = new Array();
    var recordNum, fieldNum, i, j;

    //���ó���������ǿ�ݴ���
    if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
    if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";

    try
    {
        strResult=strResult.substring(strResult.indexOf(RECORDDELIMITER)+1);    //ȥ����һ�������ֶ�
        arrRecord = strResult.split(RECORDDELIMITER);   //��ֲ�ѯ������õ���¼����
        recordNum = arrRecord.length;
        for(i=0; i<recordNum; i++)
        {
            arrField = arrRecord[i].split(FIELDDELIMITER);  //��ּ�¼���õ��ֶ�����
            fieldNum = arrField.length;
            if ( fieldNum!=statment.length )
            {
                alert("���Ȳ�ƥ��,����ʹ�ø÷���");
                return null;
            }
            arrEasyQuery[i] = new Array();
            for(j=0; j<fieldNum; j++)
            {
                sb = trim(statment[j]);
                arrEasyQuery[i][sb] = arrField[j];  //�γ�����Ϊ��¼����Ϊ�ֶεĶ�ά����
            }
            for(j=0;j<fieldNum; j++)
            {
                sb = trim(statment[j]);
            }
        }
    }
    catch(ex)
    {
        alert("�������ʧ�ܣ�����ԭ���ǣ�" + ex.message);
        return null;
    }

    return arrEasyQuery;
}

/**
 * ��MULTILINE��ʾ��ά���麯��
 * @param arrDisplayData - ��ά����
 * @param multilineGrid - multiline����
 * @return ʧ�ܷ���FALSE
 */
function displayMultiline(arrDisplayData, multilineGrid, otherTurnPage)
{
    var i, j;
    var recordNum, fieldNum;
    var cTurnPage;
    if (typeof(otherTurnPage)=="object")
    {
        cTurnPage = otherTurnPage;
    }
    else
    {
        cTurnPage = turnPage;
    }
    //arrDisplayData�ӿڲ�������
    if (typeof(arrDisplayData) == "undefined")
    {
        alert( "displayEasyResultû���յ�Ҫ��ʾ������!" );
        return false;
    }
    //multilineGrid�ӿڲ�������
    if (typeof(multilineGrid) == "undefined")
    {
        alert( "displayEasyResultû���ҵ���ʾ��������MULTILINE!" );
        return false;
    }
    // ��ʼ��MULTILINE���
    try
    {
        multilineGrid.mulLineCount = arrDisplayData.length;
        // ʹ��¼�������ͳһ
        if (typeof(cTurnPage) == "object")
        {
            multilineGrid.recordNo = cTurnPage.pageIndex * cTurnPage.pageLineNum;
        }
        else
        {
            multilineGrid.recordNo = 0;

        }
        //2006-3-13 15:19 ������޸�����װ���߼�
//      multilineGrid.loadMulLine(multilineGrid.arraySave);
        multilineGrid.loadMulLineArr(multilineGrid.arraySave,arrDisplayData);
    }
    catch(ex)
    {
        alert("displayMultiline ��ʼ��Multiline�������!");
        return false;
    }
//  for(i=0; i<arrDisplayData.length; i++)
//  {
//      fieldNum = arrDisplayData[i].length;
//      for(j=0; j<fieldNum; j++)
//      {
//          multilineGrid.setRowColData(i, j+1, arrDisplayData[i][j]);
//      }
//  }
}

/**
 * ����ҳ������ҳ���¼�������������ݿ���ȡ����ǰҳҪ��ʾ�ļ�¼��
 * @param arrDataSet - ��ά����
 * @param arrDataIndex - ҳ�������������������ʼ��ȡ��λ��
 * @param pageRecordNum - ҳ���¼����������ȷ��ȡ������
 * @return ��ά���飬ʧ�ܷ���NULL
 */
function getPageDisplayData(arrDataSet, arrDataIndex, pageRecordNum, otherTurnPage)
{
    var arrDisplayData = new Array();
    var fieldNum;
    var cTurnPage;

    if (typeof(otherTurnPage)=="object")
    {
        cTurnPage = otherTurnPage;
    }
    else
    {
        cTurnPage = turnPage;
    }

    if (typeof(pageRecordNum) == "undefined") pageRecordNum = cTurnPage.pageLineNum;

    try {
        for( i = arrDataIndex; i < (arrDataIndex + pageRecordNum < arrDataSet.length ? arrDataIndex + pageRecordNum : arrDataSet.length); i++ )
        {
            fieldNum = arrDataSet[i].length;
            arrDisplayData[i - arrDataIndex] = new Array();
            for( j = 0; j < fieldNum; j++ )
            {
                arrDisplayData[i - arrDataIndex][j] = arrDataSet[i][j];
            }
        }
    }
    catch(ex)
    {
        alert("getPageDisplayData�������");
        return null;
    }
    return arrDisplayData;
}

/**
 * ������һҳ����������Ҫ��Բ�ѯ��ʾ��һҳ��ť���¼�
 * @return ʧ�ܷ���FALSE
 */
function getNextPage()
{
    //Ϊ����������ĳ����ݴ������
    var cTurnPage = this;
    //alert('cTurnPage.pageLineNum:'+cTurnPage.pageLineNum+':cTurnPage.pageIndex:'+cTurnPage.pageIndex);
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }
    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("���Ȳ�ѯ��");
        return false;
    }
    //ҳβ�жϣ�cTurnPage.pageIndex��0��ʼ������cTurnPage.queryAllRecordCountȫ����¼��/cTurnPage.pageLineNumÿҳ�������õ�ȫ��ҳ��
    if (cTurnPage.pageIndex == Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum) - 1)
    {
        alert("�Ѿ�����βҳ!");
        return false;
    }

    //��ҳ��ѯ,�ж��Ƿ��Ծ���µĺ�̨���ݿ⻺��飺cTurnPage.pageIndex���ۼӵģ�cTurnPage.dataBlockNum �Ǻ�̨���ݿ⻺���Ĵ�С���ж���һҳ�ĵ�һ����¼���Ƿ���λ���µĺ�̨���ݿ⻺���ĵ�һ��,�������������µĺ�̨���ݿ⻺���,�����Ծ��ھɵĺ�̨���ݿ⻺���ȡ����ǰ����ҳ�����ݼ�¼
    var intStart = Math.ceil(((cTurnPage.pageIndex + 1)*cTurnPage.pageLineNum + 1)/cTurnPage.dataBlockNum -1) * cTurnPage.dataBlockNum + 1 ;
    if(cTurnPage.useSimulation == 1)
    {
    	  //alert('2');
        //����������Դ������ǰ�Ĵ���2004-04-22 add by sxy
        //һ������µĿ��ƣ���Ϊҳ����һ����ʾ����
        cTurnPage.pageIndex = cTurnPage.pageIndex + 1;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex % cTurnPage.blockPageNum * cTurnPage.pageLineNum, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    cTurnPage.pageIndex = cTurnPage.pageIndex + 1;
     //alert("(cTurnPage.pageIndex + cTurnPage.preQueryLimit) :"+(cTurnPage.pageIndex + cTurnPage.preQueryLimit));
    //����Ԥ��ҳ�Ĺ���
     if ((((cTurnPage.pageIndex + cTurnPage.preQueryLimit )* cTurnPage.pageLineNum )% cTurnPage.dataBlockNum) == 0 && cTurnPage.useSimulation == 0)
     {
     	  //alert("begin preQuery ");
     	    //���жϻ������Ƿ���
        var tSearchFlag = false;
        var tCatchArr = new Array();
        var tPageIndex =  cTurnPage.pageIndex;
				var tPreCache = cTurnPage.arrDataCacheSet;
				var tIntStart = Math.ceil(((cTurnPage.pageIndex + cTurnPage.preQueryLimit)*cTurnPage.pageLineNum + 1)/cTurnPage.dataBlockNum -1) * cTurnPage.dataBlockNum + 1 ;
        for(i=0;i<cTurnPage.allArrDataCacheSet.length;i++)
        {
        	var tCurrent = cTurnPage.allArrDataCacheSet[i];
        	//alert("tCurrent.id:"+tCurrent.id+":intStart:"+intStart);
        	if(tCurrent.id == tIntStart)
        	{
        		tSearchFlag = true;
        		tCatchArr = tCurrent.value;
        		break;
        	}
        }
        //alert("tSearchFlag:"+tSearchFlag);
        if(!tSearchFlag)
        {
        	//���֮ǰ���첽��ѯ��û�����,���ټ����ύ.
        	if(!cTurnPage.preQueryFlag)
        	{
        		//������û��������ѯ
        		var tSQL = cTurnPage.strQuerySql;
        		//tSQL = 
        		cTurnPage.preQueryFlag = true;
        		var url= "../common/easyQueryVer3/mulLineQuery.jsp?SQL="+tSQL+"&intStart="+tIntStart+"&LargeFlag="+mLargeFlag ;
        				url=encodeURI(url); 
   							url=encodeURI(url);
        	    	jQuery.post(url,
  										//params,
  										function(data) {
															ResultData = data;
															if(ResultData != null && typeof(ResultData) == "string")
															{
																ResultData = ResultData.trim();
																//alert('ResultData:'+ResultData);
																//������صĽ������ȷ�Ͳ�������.
																//alert('ResultData:'+ResultData);
																//cTurnPage.arrDataCacheSet = decodeEasyQueryResult(ResultData, 0, 0, cTurnPage);
																var tKey = tIntStart;
        												cTurnPage.allCacheSize ++;
                              	cTurnPage.allArrDataCacheSet[cTurnPage.allCacheSize%cTurnPage.allArrCacheSize] = {id:tKey,value:decodeEasyQueryResult(ResultData, 0, 0, cTurnPage)};
																cTurnPage.preQueryFlag = false;
															}
												},"text" 
								);
						}       
      	}
     }
    
    if (((cTurnPage.pageIndex * cTurnPage.pageLineNum )% cTurnPage.dataBlockNum) == 0 && cTurnPage.useSimulation == 0)
    {
        //alert("[cTurnPage.allArrDataCacheSet.length]:"+cTurnPage.allArrDataCacheSet.length);
        //���жϻ������Ƿ���
        var tSearchFlag = false;
        var tCatchArr = new Array();
        var tPageIndex =  cTurnPage.pageIndex;
				var tPreCache = cTurnPage.arrDataCacheSet;
        for(i=0;i<cTurnPage.allArrDataCacheSet.length;i++)
        {
        	var tCurrent = cTurnPage.allArrDataCacheSet[i];
        	//alert("tCurrent.id:"+tCurrent.id+":intStart:"+intStart);
        	if(tCurrent.id == intStart)
        	{
        		tSearchFlag = true;
        		tCatchArr = tCurrent.value;
        		break;
        	}
        }
        if(!tSearchFlag)
        {
        	if(!cTurnPage.preQueryFlag)
        	{
        		cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);
	        	cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
	        	var tKey = intStart;
        		cTurnPage.allCacheSize ++;
          	cTurnPage.allArrDataCacheSet[cTurnPage.allCacheSize%cTurnPage.allArrCacheSize] = {id:tKey,value:cTurnPage.arrDataCacheSet};
          }
          else
          {
          	alert('���ڲ�ѯ��ҳ����,���Ժ��ٵ��');
          	cTurnPage.pageIndex = cTurnPage.pageIndex - 1;
          	return false;
          }
      	}
      	else
      	{
      		//alert('search');
      		cTurnPage.arrDataCacheSet = tCatchArr;
      	}
      	

        var nextDateBlockLineIndex = cTurnPage.pageIndex *cTurnPage.pageLineNum +1 - intStart ;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, nextDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //�����cTurnPage.pageLineNumΪ�Ե�λ��С���ֵ��������ҳ��ʼ��¼��cTurnPage.dataBlockNumΪ��λ��С���ֵ�������ݿ���е������
    var nextDateBlockLineIndex = cTurnPage.pageIndex *cTurnPage.pageLineNum +1 - intStart ;
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, nextDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
    return false;
}

/**
 * ������һҳ����������Ҫ��Բ�ѯ��ʾ��һҳ��ť���¼�
 * @return ʧ�ܷ���FALSE
 */
function getPreviousPage()
{
    //Ϊ����������ĳ����ݴ������
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("���Ȳ�ѯ��");
        return false;
    }

    //cTurnPage.pageIndex��0��ʼ�������������ۻ��ģ����Ϊ��ʱΪȫ����¼�ĵ�һ��
    if (cTurnPage.pageIndex == 0)
    {
        alert("�Ѿ�������ҳ!");
        return false;
    }

    //��ҳ��ѯ,�ж��Ƿ��Ծ���µĺ�̨���ݿ⻺��飺cTurnPage.pageIndex���ۼӵģ�cTurnPage.dataBlockNum �Ǻ�̨���ݿ⻺���Ĵ�С���ж���һҳ�ĵ�һ����¼���Ƿ���λ���µĺ�̨���ݿ⻺���ĵ�һ��,�������������µĺ�̨���ݿ⻺���,�����Ծ��ھɵĺ�̨���ݿ⻺���ȡ����ǰ����ҳ�����ݼ�¼
    var intStart = Math.ceil(((cTurnPage.pageIndex - 1)*cTurnPage.pageLineNum + 1)/cTurnPage.dataBlockNum -1) * cTurnPage.dataBlockNum + 1 ;
    cTurnPage.pageIndex = cTurnPage.pageIndex - 1;
    if (((cTurnPage.pageIndex+1)*cTurnPage.pageLineNum)%cTurnPage.dataBlockNum == 0 && cTurnPage.useSimulation == 0)
    {
  			//���ӻ���
  			var tSearchFlag = false;
        var tCatchArr = new Array();
        for(i=0;i<cTurnPage.allArrDataCacheSet.length;i++)
        {
        	var tCurrent = cTurnPage.allArrDataCacheSet[i];
        	//alert("tCurrent.id:"+tCurrent.id+":intStart:"+intStart);
        	if(tCurrent.id == intStart)
        	{
        		tSearchFlag = true;
        		tCatchArr = tCurrent.value;
        		break;
        	}
        }
        if(!tSearchFlag)
        {
       	 	cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);
        	cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        	var tKey = intStart;
          cTurnPage.allCacheSize ++;
          cTurnPage.allArrDataCacheSet[cTurnPage.allCacheSize%cTurnPage.allArrCacheSize] = {id:tKey,value:cTurnPage.arrDataCacheSet};
      	}
      	else
      	{
      		//alert('search');
      		cTurnPage.arrDataCacheSet = tCatchArr;
      	}
        //�����cTurnPage.pageLineNumΪ�Ե�λ��С���ֵ��������ҳ��ʼ��¼��cTurnPage.dataBlockNumΪ��λ��С���ֵ�������ݿ���е������
        var previousDateBlockLineIndex = (cTurnPage.pageIndex) *cTurnPage.pageLineNum +1 - intStart ;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet,previousDateBlockLineIndex , cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //�����cTurnPage.pageLineNumΪ�Ե�λ��С���ֵ��������ҳ��ʼ��¼��cTurnPage.dataBlockNumΪ��λ��С���ֵ�������ݿ���е������
    var previousDateBlockLineIndex = (cTurnPage.pageIndex) *cTurnPage.pageLineNum +1 - intStart ;
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet,previousDateBlockLineIndex , cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
}

/**
 * ����βҳ����������Ҫ��Բ�ѯ��ʾβҳ��ť���¼�
 * @return ʧ�ܷ���FALSE
 */
function getLastPage()
{
    //Ϊ����������ĳ����ݴ������
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("���Ȳ�ѯ��");
        return false;
    }

    //ҳβ�жϣ�cTurnPage.pageIndex��0��ʼ������cTurnPage.queryAllRecordCountȫ����¼��/cTurnPage.pageLineNumÿҳ�������õ�ȫ��ҳ��
    if (cTurnPage.pageIndex == Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum) - 1)
    {
        alert("�Ѿ�����βҳ!");
        return false;
    }

		var tPageIndex =  cTurnPage.pageIndex;
		var tPreCache = cTurnPage.arrDataCacheSet;
    //��ҳ�����õ����һ������һҳ��cTurnPage.queryAllRecordCount - cTurnPage.queryAllRecordCount % (cTurnPage.blockPageNum * cTurnPage.pageLineNum) + 1 Ϊ�������һ��
    cTurnPage.pageIndex = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum - 1);
    //���ҳ������
    cTurnPage.blockPageNum = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum );

    //�����ѯ��ʼλ��:���������ݼ�¼���ں�̨���ݿ⻺������ʼλ��.
    var intStart = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.dataBlockNum -1)*cTurnPage.dataBlockNum + 1 ;

    if (cTurnPage.useSimulation == 0)
    {
        //��ü����������ݼ�¼���ں�̨���ݿ⻺������ʼλ��.
        //���ӻ���
  			var tSearchFlag = false;
        var tCatchArr = new Array();
        for(i=0;i<cTurnPage.allArrDataCacheSet.length;i++)
        {
        	var tCurrent = cTurnPage.allArrDataCacheSet[i];
        	//alert("tCurrent.id:"+tCurrent.id+":####intStart:"+intStart);
        	if(tCurrent.id == intStart)
        	{
        		tSearchFlag = true;
        		tCatchArr = tCurrent.value;
        		break;
        	}
        }
        if(!tSearchFlag)
        {
        	cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);        	
       	  cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
       	  var tKey = intStart;
          cTurnPage.allCacheSize ++;
          cTurnPage.allArrDataCacheSet[cTurnPage.allCacheSize%cTurnPage.allArrCacheSize] = {id:tKey,value:cTurnPage.arrDataCacheSet};

       	}
       	else
       	{
       		cTurnPage.arrDataCacheSet = tCatchArr;
       	}
        //�����cTurnPage.pageLineNumΪ�Ե�λ��С���ֵ��������ҳ��ʼ��¼��cTurnPage.dataBlockNumΪ��λ��С���ֵ�������ݿ���е������
        var lastDateBlockLineIndex = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum -1)*cTurnPage.pageLineNum - Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.dataBlockNum -1)*cTurnPage.dataBlockNum ;
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, lastDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //�����cTurnPage.pageLineNumΪ�Ե�λ��С���ֵ��������ҳ��ʼ��¼��cTurnPage.dataBlockNumΪ��λ��С���ֵ�������ݿ���е������
    var lastDateBlockLineIndex = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum -1)*cTurnPage.pageLineNum - Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.dataBlockNum -1)*cTurnPage.dataBlockNum ;
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, lastDateBlockLineIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
}

/**
 * ������ҳ����������Ҫ��Բ�ѯ��ʾ��ҳ��ť���¼�
 * @return ʧ�ܷ���FALSE
 */
function getFirstPage()
{
    //Ϊ����������ĳ����ݴ������
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false)
    {
        alert("���Ȳ�ѯ��");
        return false;
    }

    //cTurnPage.pageIndex��0��ʼ�������������ۻ��ģ����Ϊ��ʱΪȫ����¼�ĵ�һ��
    if (cTurnPage.pageIndex == 0)
    {
        alert("�Ѿ�������ҳ!");
        return false;
    }

    //��ҳ�����õ���һ��ĵ�һҳ
    var tPageIndex = cTurnPage.pageIndex;
    var tPreCache = cTurnPage.arrDataCacheSet;
    cTurnPage.pageIndex = 0;
    if (cTurnPage.useSimulation == 0)
    {
    	 //���ӻ���
  			var tSearchFlag = false;
        var tCatchArr = new Array();
        for(i=0;i<cTurnPage.allArrDataCacheSet.length;i++)
        {
        	var tCurrent = cTurnPage.allArrDataCacheSet[i];
        	//alert("tCurrent.id:"+tCurrent.id);
        	if(tCurrent.id == 1)
        	{
        		tSearchFlag = true;
        		tCatchArr = tCurrent.value;
        		break;
        	}
        }
        if(!tSearchFlag)
        {
       	 	cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, 1, mLargeFlag);      	
        	cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        	var tKey = 1;
        	cTurnPage.allCacheSize ++;
          cTurnPage.allArrDataCacheSet[cTurnPage.allCacheSize%cTurnPage.allArrCacheSize] = {id:tKey,value:cTurnPage.arrDataCacheSet};

        }
        else
        {
        	cTurnPage.arrDataCacheSet = tCatchArr;
        }
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
    }
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, 0, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
    cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
}

/**
 * ��ҳ��
 * ����ʾ��ҳ�������������˳�ʼ��
 */
function turnPageClass()
{
    this.arrDataCacheSet = new Array();
    this.pageDisplayGrid = "";  //��ʾ���ؼ���MULTILINE��
    this.strQuerySql = "";  //��ѯ��SQL���
    this.strQueryResult = "";   //��ѯ����ַ���
    this.queryAllRecordCount = 0;   //��ѯ�����¼����
    this.pageIndex = 0; //��������
    this.pageRecordNum = 0; //ҳ����ʾ����
    this.synchronization = 1;   //ͬ���첽��ѯ���
    this.useCache = 0;  //ʹ�û�����
    this.startQueryRecord = 1;  //��ѯ��ʼ��λ�ñ�ǣ��ȴӵڼ�����¼��ʼ���в�ѯ
    this.useSimulation = 0; //ʹ��ģ������Դ,Ϊ1ʱ��ʾʹ��
    this.blockPageNum = 20; //���ݿ��ҳ����(��ʱδ��)
    this.dataBlockNum = 200;    //���ݿ��̨���ݿ��ҳ��������ֵΪ���ݿ��̨���漯�ϴ�С,���Ե����ݿ��̨���漯�����С�仯ʱ��Ҫͬʱ�ı��ֵsxy-add 2004-02-02��
    this.pageLineNum = 10;  //ÿҳ����������(��ֵ����ΪdataBlockNum��Լ��sun-add-2004-02-02)
    this.pageDivName = "divPage";   //ÿҳ����������
    this.getData = getPageDisplayData;
    this.nextPage = getNextPage;
    this.previousPage = getPreviousPage;
    this.lastPage = getLastPage;
    this.decodeEasyQueryResult = decodeEasyQueryResult;
    this.firstPage = getFirstPage;
    this.queryModal = easyQueryVer3Modal;
    this.allowsortcol =0;   //Ҫ�������
    this.sortdesc = new Array();
    this.allowsort =allowsort;
    this.allowsortparam =allowsortparam;
    this.gotoPage = showGotoPage;
    if (typeof(MAXMEMORYPAGES) != "undefined") this.blockPageNum = MAXMEMORYPAGES;
    if (typeof(MAXSCREENLINES) != "undefined") this.pageLineNum = MAXSCREENLINES;
    
    //���ӻ���
    this.allArrDataCacheSet = new Array();
    this.allArrCacheSize = 5; //���ƻ��������
    this.allCacheSize = -1;//���ڼ�¼ʵ�ʻ����е���
    this.preQueryLimit = 3;//������ǰ��ѯ��һ�μ�¼��ҳ��
    this.preQueryFlag = false;//���ڿ����Ƿ����첽��ѯ
    
    //�����Ƿ���ʾ��ҳ��ʾ
    this.showTurnPageDiv = 1;
        
}

/**
 * ���ɲ�ѯ���𴮡���ʾ����ҳ���ƵĲ�ѯģ��
 * @param strSql - SQL���
 * @param multilineGrid - multiline����
 * @param LargeFlag - ����������ѯ����
 * @param IndexFlag - �Ƿ�չ��ҳ����ת��
 * @return ʧ�ܷ���FALSE
 */
function easyQueryVer3Modal(strSql, multilineGrid, LargeFlag, IndexFlag, PageLineNum)
{
    //Ϊ����������ĳ����ݴ������
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined")
    {
        cTurnPage = turnPage;
    }

    //LargeFlag �ӿڲ�������
    if (typeof(LargeFlag) == "undefined" || LargeFlag == "0" || (typeof(LargeFlag) == "string" && LargeFlag == ""))
    {
        //��ѯSQL�����ؽ���ַ���
        cTurnPage.strQueryResult = easyQueryVer3(strSql, 1, 0, 1);
    }
    else
    {
        //��ѯSQL�����ؽ���ַ���
        cTurnPage.strQueryResult = easyQueryVer3(strSql, 1, 0, 1, 1, 1);
    }
    //PageLineNum �ӿڲ�������
    if (typeof(PageLineNum) == "undefined" || (typeof(PageLineNum) == "string" && PageLineNum == ""))
    {}
    else
    {
        cTurnPage.pageLineNum = PageLineNum;
    }

    //�ж��Ƿ��ѯ�ɹ�
    if (!cTurnPage.strQueryResult)
    {
        //���MULTILINE��ʹ�÷�����MULTILINEʹ��˵��
        multilineGrid.clearData();
        try
        {
            window.document.all(cTurnPage.pageDivName).style.display = "none";
        }
        catch (ex) {}
        return false;
    }

    //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
    cTurnPage.arrDataCacheSet = clearArrayElements(cTurnPage.arrDataCacheSet);
    //��ջ���
    cTurnPage.allArrDataCacheSet = clearArrayElements(cTurnPage.allArrDataCacheSet);
    cTurnPage.allCacheSize = 0;
    //��ѯ�ɹ������ַ��������ض�ά����
    cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
    var tKey = 1;
    //cTurnPage.allCacheSize ++;
    cTurnPage.allArrDataCacheSet[cTurnPage.allCacheSize%cTurnPage.allArrCacheSize] = {id:tKey,value:cTurnPage.arrDataCacheSet};

    //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    cTurnPage.pageDisplayGrid = multilineGrid;
    if(typeof(IndexFlag) == "undefined" || IndexFlag == "0" || (typeof(IndexFlag) == "string" && IndexFlag == ""))
    {
    }
    //else if(IndexFlag == "1" )
    //{
        multilineGrid.SortPage = cTurnPage;
    //}
    //����SQL���
    cTurnPage.strQuerySql = strSql;
    //���ò�ѯ��ʼλ��
    cTurnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    var arrDataSet = cTurnPage.getData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex, cTurnPage.pageLineNum);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, cTurnPage.pageDisplayGrid, cTurnPage);
    //�����Ƿ���ʾ��ҳ��ť
    //if (cTurnPage.queryAllRecordCount > cTurnPage.pageLineNum)
    if(cTurnPage.showTurnPageDiv==1)
    {
        try
        {
            window.document.all(cTurnPage.pageDivName).style.display = "";
        }
        catch (ex) {}
    }
    else
    {
    	try
        {
            window.document.all(cTurnPage.pageDivName).style.display = "none";
        }
        catch (ex) {}
    }
 /*   else
    {
        try
        {
            window.document.all(cTurnPage.pageDivName).style.display = "none";
        }
        catch (ex) {}
    }*/
    if(cTurnPage.showTurnPageDiv==1){
    		try
    		{
    			multilineGrid.setPageMark(cTurnPage);
   			 }
   			 catch(ex){}
  	}
}

/**
 * ��ѯƴ������
 * @param fieldName - �ֶ�����
 * @param controlName - �ؼ�����
 * @param strOperate - ������
 * @param type - �ֶ�����( 0:�ַ��͡�1:������ )
 * @return ƴ�õĴ�
 */

 function getWherePart(fieldName, controlName, strOperate, fieldType) {

    var strWherePart = "";
    var value = "";
    if(controlName == "" || controlName == null) controlName = fieldName;
    //XinYQ modified on 2006-05-09 : �����������ͬ���ؼ����޷���ȡ�κ�ֵ,�޸�Ϊȡ�׸�ͬ���ؼ���ֵ
    //OLD : value = eval( "fm." + trim( controlName ) + ".value" );
    value = window.document.getElementsByName(trim(controlName))[0].value;
    if(value == "" || value == null) return strWherePart;
    if(fieldType == null || fieldType == "") fieldType = "0";
    if(strOperate == "" || strOperate == null) strOperate = "=";
    if(fieldType == "0") { // 0:�ַ���


        if(strOperate == "like")
        {
            strWherePart = " and " + trim( fieldName ) + " like '" + trim( value ) + "%25' ";
        }
        else
        {
            strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + "'" + trim( value ) + "' ";
        }
    }
    if(fieldType == "1") { // 1:������


        strWherePart = " and " + trim(fieldName) + trim(strOperate) + trim(value) + " ";
    }
    return strWherePart;
}

/**
 * ������麯��
 * @param arrData - ����
 * @return ������
 */
function clearArrayElements(arrData)
{
    try
    {
        while (arrData.length != 0) arrData.pop();
    }
    catch(ex)
    {
       // alert("clearArrayElements�����������ʧ��:"+ex);
        return new Array();
    }
    return arrData;
}

/**
* ��ȡsql��statement���֣��ֽ�����
*/
function getStatment(strsql)
{
    try

    {
        strsql= trim(strsql);
        var  statpart = strsql.substring(6, strsql.indexOf("from"));
        var statment= statpart.split(",");
        for ( i=0;i<statment.length;i++)
        {
            statment[i] = trim(statment[i]);
        }
        return statment ;
    }
    catch(ex)
    {
        alert("getStatement����"+ex.message);
        return null;
    }
}

/**
* ���ݴ����sql�����ݿ��в�ѯ��¼����ʾ��ҳ����
* ע��:sql�г��ֵ��ֶ�����Ҫ��ҳ��Ԫ�ص�����һ�����ܴ�������ı���������
* author:wujs 2005-1-14 11:42
*/
function EasyShowForm( strSQL,alterflag,paraForm)
{
    if ( paraForm == null || paraForm=='undefined')
    {
        //Ĭ��ȡfm
        paraForm = fm;
    }
    if ( alterflag ==null || alterflag=='undefined' || alterflag==1)
    {
         alterflag=true;
    }
    else
    {
        alterflag=false;
    }
    var strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
    if (!strQueryResult)
    {

        return false;
    }
    var statment = getStatment(strSQL );
    if (statment==null)
    {
        alert("����sql������Ҫ��");
        return false;
    }
    var deResult= decodeEasyQueryResultV2(statment,strQueryResult);

    for( i=0;i<statment.length ;i++)
    {
        try
        {
            paraForm.all(statment[i]).value =Conversion( deResult[0][statment[i]] ) ;
        }
        catch(ex)
        {
            if ( alterflag)
            {
                alert("û�ҵ�ҳ��Ԫ��:"+ statment[i]);
                return false;
            }
        }
    }
    return true;
}

function allowsort(i)
{
    var sortturnPage =this;
    allowsortcol=i-1;//Ϊʲô������pageȡ�ã���������turnpageclass����...?
    if(sortturnPage.sortdesc[allowsortcol]!="asc")
    {
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.sort(allowsortparam);
        sortturnPage.sortdesc[allowsortcol]="asc";
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++)
        {
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }
    else
    {
        sortturnPage.arrDataCacheSet = sortturnPage.arrDataCacheSet.reverse(allowsortparam);
        for(var n=0;n<sortturnPage.pageDisplayGrid.colCount;n++)
        {
            if(n!=allowsortcol) sortturnPage.sortdesc[n]="desc";
        }
    }
    //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
    //���ò�ѯ��ʼλ��
    sortturnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    var arrDataSet = sortturnPage.getData(sortturnPage.arrDataCacheSet, sortturnPage.pageIndex, sortturnPage.pageLineNum);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, sortturnPage.pageDisplayGrid, sortturnPage);

}

function allowsortparam(x,y)
{
    if (x[allowsortcol] > y[allowsortcol])
    {
        return 1;
    }
    else if (x[allowsortcol] < y[allowsortcol])
        {
            return -1;
        }
        else
        {
            return 0;
        }
    //���ݶ�ά����ĵ����еĵ�һ����ĸ��ASCII������������
}

//��ҳ����ҳ
function showGotoPage(tIndex){
    var cTurnPage = this;
    if (typeof(this.strQueryResult)=="undefined") {
        cTurnPage = turnPage;
    }
    if (cTurnPage.strQueryResult == "" || cTurnPage.strQueryResult == false) {
        alert("���Ȳ�ѯ��");
        return false;
    }
    if(tIndex==null||tIndex==""||!isInteger(tIndex))
    {
        alert("��������ȷҳ��!");
        return;
    }
    if(tIndex<1 || tIndex>Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum)){
        alert("��תҳ�ų�����Χ!");
        return;
    }
    var tIndexPage = tIndex-1;
    var currentBlock = Math.floor(cTurnPage.pageIndex/cTurnPage.blockPageNum);
    var gotoBlock = Math.floor(tIndexPage/cTurnPage.blockPageNum);
    cTurnPage.pageIndex = tIndexPage;
    cTurnPage.blockPageNum = Math.ceil(cTurnPage.queryAllRecordCount/cTurnPage.pageLineNum );

    //�����ѯ��ʼλ��:���������ݼ�¼���ں�̨���ݿ⻺������ʼλ��.
    var intStart =((Math.ceil((tIndex*cTurnPage.pageLineNum)/cTurnPage.dataBlockNum))-1)*cTurnPage.dataBlockNum+1;
    if (cTurnPage.useSimulation == 0)
    {
        //��ü����������ݼ�¼���ں�̨���ݿ⻺������ʼλ��.
         //���ӻ���
       // alert(intStart);
         
  			var tSearchFlag = false;
        var tCatchArr = new Array();
        for(i=0;i<cTurnPage.allArrDataCacheSet.length;i++)
        {
        	var tCurrent = cTurnPage.allArrDataCacheSet[i];
        	//alert("tCurrent.id:"+tCurrent.id);
        	if(tCurrent.id == intStart)
        	{
        		tSearchFlag = true;
        		tCatchArr = tCurrent.value;
        		break;
        	}
        }
        if(!tSearchFlag)
        {
       	 	cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);      	
        	cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        	var tKey = intStart;
        	cTurnPage.allCacheSize ++;
          cTurnPage.allArrDataCacheSet[cTurnPage.allCacheSize%cTurnPage.allArrCacheSize] = {id:tKey,value:cTurnPage.arrDataCacheSet};

        }
        else
        {
        	cTurnPage.arrDataCacheSet = tCatchArr;
        }
        
        
        
        
       // cTurnPage.strQueryResult = easyQueryVer3(cTurnPage.strQuerySql, cTurnPage.synchronization, cTurnPage.useCache, intStart, mLargeFlag);
        //cTurnPage.arrDataCacheSet = decodeEasyQueryResult(cTurnPage.strQueryResult, 0, 0, cTurnPage);
        //�����cTurnPage.pageLineNumΪ�Ե�λ��С���ֵ��������ҳ��ʼ��¼��cTurnPage.dataBlockNumΪ��λ��С���ֵ�������ݿ���е������
        var mIndex = ((Math.ceil(((tIndex*cTurnPage.pageLineNum)%cTurnPage.dataBlockNum))/cTurnPage.pageLineNum)-1)*cTurnPage.pageLineNum ;
        if(mIndex==-10)
        {
            mIndex=190;
        }
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet,mIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    if(cTurnPage.useSimulation == 1)
    {
        //����������Դ������ǰ�Ĵ���2004-04-22 add by sxy
        //һ������µĿ��ƣ���Ϊҳ����һ����ʾ����
        displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex % cTurnPage.blockPageNum * cTurnPage.pageLineNum, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
        cTurnPage.pageDisplayGrid.setPageMark(cTurnPage);
        return false;
    }
    //�����cTurnPage.pageLineNumΪ�Ե�λ��С���ֵ��������ҳ��ʼ��¼��cTurnPage.dataBlockNumΪ��λ��С���ֵ�������ݿ���е������
    displayMultiline(getPageDisplayData(cTurnPage.arrDataCacheSet, cTurnPage.pageIndex, cTurnPage.pageLineNum), cTurnPage.pageDisplayGrid, cTurnPage);
}

/**
 * ��String���������trim����
 * common.js ����, ����Ϊ�˱�֤��ҳ֮������β�ѯ�����ȷ, �ٴ����, ��ֹû������ common.js ���󱨲�ѯ����
 */
 
 
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g,"");
}


/**
//EASYQUERY������
//��Ҫ���ڱ���JSP���ƺ�SQL�����Ƽ�����
//�����ɷ��������Խ����Ĵ�
//mojiao 2006-04-21
//
function SqlClass()
{
    this.sqlParaName = new Array();//������������
    this.sqlParaValue = new Array();//����ֵ����
    this.paraCount = 0;//������count
    this.jspName = "";//��Ӧ��jsp�ļ�����
    this.sqlId = "";    //��ѯ��sql��Ӧ������
    this.setJspName = _setJspName;//����jsp���ƺ���
    this.setSqlId             = _setSQLID;//����sql��Ӧ�����ƺ���
  this.addPara            = _addPara;//��Ӳ����ĺ���
  this.getString        = _getString;//��ô��ĺ���
  this.getWherePart =_getWherePart;//�ӽ����ö����ֵ�ĺ���
  this.setValue =_setValue;
  this.setInPart=_setInpart;

}

//����JSP����
function _setJspName(jspName)
{
    var cMySqlClass=this;
    cMySqlClass.jspName=jspName;
}

//����SQL��ID
function _setSQLID(sqlId)
{
    var cMySqlClass=this;
    cMySqlClass.sqlId=sqlId;
}

//��Ӳ���
function _addPara(paraName,paraValue)
{
    var cMySqlClass=this;
    cMySqlClass.sqlParaName[cMySqlClass.paraCount]=paraName;
    cMySqlClass.sqlParaValue[cMySqlClass.paraCount]=paraValue;
    cMySqlClass.paraCount++;
}

//����һ���������������Ĵ�
function _getString()
{
    var cMySqlClass=this;
    var strRetString="";
    strRetString+=cMySqlClass.jspName+";";
    strRetString+=cMySqlClass.sqlId+";";
    for( j = 0; j < cMySqlClass.paraCount; j++ )
    {
        strRetString+=cMySqlClass.sqlParaName[j]+"="+cMySqlClass.sqlParaValue[j]+";";
    }

//  alert(strRetString);
    return strRetString
}

//�ӽ�����ָ�����ƵĶ����ֵ�������Ϊ������뵽����������
function _getWherePart(paraName, controlName)
{
    var cMySqlClass=this;
    var value = "";
    if(controlName == "" || controlName == null) controlName = paraName;
    value = eval("fm." + trim(controlName) + ".value");
    if(value == "" || value == null)
    {
        return;
    }else
    {
        cMySqlClass.addPara(trim(paraName),trim(value)) ;
        return;
    }
}

function _setValue(paraName,controlvalue)
 {
      var cMySqlClass=this;
      var value=controlvalue;
    cMySqlClass.addPara(trim(paraName),trim(value));
 }

function _setInpart(paraName,controlvalue)
{
      var i;
    var cMySqlClass=this;
    var value=controlvalue;
    for(i=0;i<cMySqlClass.paraCount;i++)
    {
        if(trim(cMySqlClass.sqlParaName[i])==trim(paraName))
        {
            cMySqlClass.sqlParaValue[i]=cMySqlClass.sqlParaValue[i].substring(0,cMySqlClass.sqlParaValue[i].length-1)+",'"+controlvalue+"')";
            break ;
        }
    }
    if(i==cMySqlClass.paraCount)
      {
       controlvalue="('"+controlvalue+"')";
         cMySqlClass.addPara(paraName,controlvalue);
      }
 }
 ***/
 
 //EASYQUERY������
//��Ҫ���ڱ���SQLInfo���ƺ�SQL�����Ƽ�����
//�����ɷ��������Խ����Ĵ�
//mojiao 2006-04-21
//
function SqlClass()
{
	this.sqlParaValue = new Array();//����ֵ����
	this.paraCount = 0;//������count
	this.resourceName = "";//��Ӧ��jsp�ļ�����
	this.sqlId = "";	//��ѯ��sql��Ӧ������
	this.setResourceName = _setResourceName;//����jsp���ƺ���
	this.setSqlId             = _setSQLID;//����sql��Ӧ�����ƺ���
  this.addPara            = _addPara;//��Ӳ����ĺ���
  this.getString        = _getString;//��ô��ĺ���
  this.addSubPara				= _addSubPara;//�����sql
}

//����JSP����
function _setResourceName(resourceName)
{
	if(_checkStr(resourceName))
	{
		alert("������������Ƿ��ַ�");
		retrun;
	}
	var cMySqlClass=this;	
	cMySqlClass.resourceName=resourceName;
}

//����SQL��ID
function _setSQLID(sqlId)
{
	if(_checkStr(sqlId))
	{
		alert("������������Ƿ��ַ�");
		retrun;
	}
	var cMySqlClass=this;
	cMySqlClass.sqlId=sqlId;
}

//��Ӳ���
function _addPara(paraValue)
{
	if(_checkStr(paraValue))
	{
		alert("������������Ƿ��ַ�");
		retrun;
	}
	var cMySqlClass=this;	
	cMySqlClass.sqlParaValue[cMySqlClass.paraCount]=paraValue;
	cMySqlClass.paraCount++;
}

//����Ӳ���
function _addSubPara(paraValue)
{
	if(_checkStr(paraValue))
	{
		alert("������������Ƿ��ַ�");
		retrun;
	}
	var cMySqlClass=this;	
	cMySqlClass.sqlParaValue[cMySqlClass.paraCount]="*"+paraValue;
	cMySqlClass.paraCount++;
}

//����һ���������������Ĵ�
function _getString()
{	
	var cMySqlClass=this;
	var strRetString="";
	strRetString+=cMySqlClass.resourceName+";";
	strRetString+=cMySqlClass.sqlId+";";
	if(cMySqlClass.paraCount>0)
	{
		strRetString+=cMySqlClass.sqlParaValue[0];
	} 
	for( j = 1; j < cMySqlClass.paraCount; j++ )	
	{
		strRetString+=":"+cMySqlClass.sqlParaValue[j];
	}
	return strRetString;
}

function _checkStr(strValue)
{
		var chkExp=/^.*[;*].*$/;
		return (chkExp.test(strValue));
}	
 
 
