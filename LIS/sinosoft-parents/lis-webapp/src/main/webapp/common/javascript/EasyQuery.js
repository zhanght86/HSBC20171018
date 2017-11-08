/*****************************************************************
 *               Program NAME: ͨ����ͨ��ѯ
 *                 programmer: HST
 *                Create DATE: 2002-09-06
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *         ͨ����ͨ��ѯ����SQL���Ĳ�ѯ,Ȼ�󽫲�ѯ�����ʾ����Ӧ�Ľ���
 *     �ؼ��ϣ����н��֧����һҳ����һҳ�ȷ�ҳ�Ĺ���
 *
 *	   ˵����������ʹ�ò�����Common.js�ж����ȫ������
 *
 *****************************************************************
 */

var arrEasyQuery = new Array();		  // ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
var arrScreenResult = new Array();	  // ��ŵ�ǰҳ����Ҫ�Ĳ�ѯ�������
var totalBlock = 0;					  // ��ѯ������ܷ�����Ŀ���
var currBlockIndex = 0;				  // ��ǰʹ�ÿ������
var currPageIndex = 0;				  // ��ǰ��Ļ��ʾҳ�ڵ�ǰʹ�ÿ��е�����
var currPageCount = 0;				  // ��ǰ���ҳ��
var currRecordCount = 0;			  // ��ǰʹ�ÿ�����ļ�¼����
var currBlockRecord = 0;			  // ��ǰ���еļ�¼��
var startRecordIndex = 0;			  // ��̨ȡ���Ŀ�ʼ��¼λ��
var strEvent = "";					  // ��־EasyQuery���õ��¼�
var strSQL = "";					  // ��ѯʹ�õ�SQL���
var strError = "";					  // ������Ϣ


/*********************************************************************
 *  ִ�в�ѯ����������Ҫ��Բ�ѯ��ť���¼�
 *  ����  ��  strSQL����ȷ��SQL���
 *  ����ֵ��  ���ִ�гɹ������ʹ�ò�ѯ�ĵ�ǰ��ʾ���ֽ������arrScreenResult��
 *          ���ִ�в��ɹ����򷵻�false
 *********************************************************************
 */
function execEasyQuery( sqlString )
{
	strSQL = sqlString;
	startRecordIndex = 1;
	strEvent = "EASYQUERY";
	if ( easyQuery( strSQL, startRecordIndex ) == false )
		return false;
}

/*********************************************************************
 *  ������һҳ����������Ҫ��Բ�ѯ��ʾ��һҳ��ť���¼�
 *  ����  ��  ��
 *  ����ֵ��  ���ִ�гɹ������ʹ�ò�ѯ�ĵ�ǰ��ʾ���ֽ������arrScreenResult��
 *          ���ִ�в��ɹ����򷵻�false
 *********************************************************************
 */
function getNextPage()
{
	// У���Ƿ��ѯ
	if( totalBlock == 0 )
	{
		strError = "���Ȳ�ѯ!";
		alert( strError );
		return false;
	}
	// У���Ƿ񵽴�βҳ
	if( currBlockIndex == totalBlock && currPageIndex == currPageCount )
	{
		strError = "�Ѿ�����βҳ!";
		alert( strError );
		return false;
	}

	if( currPageIndex == currPageCount )		// �Ѿ�����ǰʹ�ÿ��ĩβ������Ҫ����̨��ѯ��һ������
	{
		startRecordIndex = currBlockIndex * MAXSCREENLINES * MAXMEMORYPAGES + 1;
		strEvent = "NEXTPAGE";
		easyQuery( strSQL, startRecordIndex );
	}
	else
	{
		currPageIndex = currPageIndex + 1;
		if( currBlockIndex == totalBlock && currPageIndex == currPageCount )
		{
			currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
		}
		getPageData( currPageIndex );

		// ��ʾ��ѯ���
		displayEasyResult( arrScreenResult );
	}
}

/*********************************************************************
 *  ������һҳ����������Ҫ��Բ�ѯ��ʾ��һҳ��ť���¼�
 *  ����  ��  ��
 *  ����ֵ��  ���ִ�гɹ������ʹ�ò�ѯ�ĵ�ǰ��ʾ���ֽ������arrScreenResult��
 *          ���ִ�в��ɹ����򷵻�false
 *********************************************************************
 */
function getPreviousPage()
{
//alert("totalBlock:"+totalBlock+";currBlockIndex:"+currBlockIndex+";currPageIndex:"+currPageIndex);
//alert("currPageCount:"+currPageCount+";currRecordCount:"+currRecordCount+";currBlockRecord:"+currBlockRecord);
	// У���Ƿ��ѯ
	if( totalBlock == 0 )
	{
		strError = "���Ȳ�ѯ!";
		alert( strError );
		return false;
	}
	// У���Ƿ񵽴���ҳ
	if( currBlockIndex == 1 && currPageIndex == 1 )
	{
		strError = "�Ѿ�������ҳ!";
		alert( strError );
		return false;
	}

	if( currPageIndex == 1 )		// �Ѿ�����ǰʹ�ÿ����ҳ������Ҫ����̨��ѯ��һ������
	{
		startRecordIndex = ( currBlockIndex - 2 ) * MAXSCREENLINES * MAXMEMORYPAGES + 1;
		strEvent = "PREVIOUSPAGE";
		easyQuery( strSQL, startRecordIndex );
	}
	else
	{
		currPageIndex = currPageIndex - 1;
		currRecordCount = MAXSCREENLINES;
		getPageData( currPageIndex );

		// ��ʾ��ѯ���
		displayEasyResult( arrScreenResult );
	}
}

/*********************************************************************
 *  ������ҳ����������Ҫ��Բ�ѯ��ʾ��ҳ��ť���¼�
 *  ����  ��  ��
 *  ����ֵ��  ���ִ�гɹ������ʹ�ò�ѯ�ĵ�ǰ��ʾ���ֽ������arrScreenResult��
 *          ���ִ�в��ɹ����򷵻�false
 *********************************************************************
 */
function getFirstPage()
{
	// У���Ƿ��ѯ
	if( totalBlock == 0 )
	{
		strError = "���Ȳ�ѯ!";
		alert( strError );
		return false;
	}
	// У���Ƿ񵽴���ҳ
	if( currBlockIndex == 1 && currPageIndex == 1 )
	{
		strError = "�Ѿ�������ҳ!";
		alert( strError );
		return false;
	}

	if( currBlockIndex != 1 )		// ��ǰʹ�ÿ鲻�ǵ�һ�飬����Ҫ����̨��ѯ��һ������
	{
		startRecordIndex = 1;
		strEvent = "FIRSTPAGE";
		easyQuery( strSQL, startRecordIndex );
	}
	else
	{
		currPageIndex = 1;
		if( currPageCount == 1 )
		{
			currRecordCount = currBlockRecord;
		}
		else
		{
			currRecordCount = MAXSCREENLINES;
		}
		getPageData( currPageIndex );

		// ��ʾ��ѯ���
		displayEasyResult( arrScreenResult );
	}
}

/*********************************************************************
 *  ����βҳ����������Ҫ��Բ�ѯ��ʾβҳ��ť���¼�
 *  ����  ��  ��
 *  ����ֵ��  ���ִ�гɹ������ʹ�ò�ѯ�ĵ�ǰ��ʾ���ֽ������arrScreenResult��
 *          ���ִ�в��ɹ����򷵻�false
 *********************************************************************
 */
function getLastPage()
{
	// У���Ƿ��ѯ
	if( totalBlock == 0 )
	{
		strError = "���Ȳ�ѯ!";
		alert( strError );
		return false;
	}
	// У���Ƿ񵽴�βҳ
	if( currBlockIndex == totalBlock && currPageIndex == currPageCount )
	{
		strError = "�Ѿ�����βҳ!";
		alert( strError );
		return false;
	}

	if( currBlockIndex != totalBlock )		// ��ǰʹ�ÿ鲻�����һ�飬����Ҫ����̨��ѯ���һ������
	{
		startRecordIndex = ( totalBlock - 1 ) * MAXSCREENLINES * MAXMEMORYPAGES + 1;
		strEvent = "LASTPAGE";
		easyQuery( strSQL, startRecordIndex );
	}
	else
	{
		currPageIndex = currPageCount;
		currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
		getPageData( currPageIndex );

		// ��ʾ��ѯ���
		displayEasyResult( arrScreenResult );
	}
}

/*************************************************************
 *  ȡ���ڲ��洢���е�ָ��ҳ�Ĳ�ѯ���
 *  ����  ��  pageIndex -- ҳ�ڿ��е�����
 *  ����ֵ��  ���ִ�гɹ������ʹ�ò�ѯ�������arrPageResult�����ִ�в��ɹ����򷵻�false
 *************************************************************
 */
function getPageData( pageIndex )
{
	var startIndex;
	var endIndex;
	var arrRecord;
	var i, j, n, k;
    arrScreenResult = new Array();	  // ��ŵ�ǰҳ����Ҫ�Ĳ�ѯ�������

	if( pageIndex < 1 || pageIndex > MAXMEMORYPAGES )
	{
		strError = "��ѯҳ����pageIndexָ������!";
		return false;
	}
	else
	{
		startIndex = ( pageIndex - 1 ) * MAXSCREENLINES;
		endIndex = ( pageIndex * MAXSCREENLINES ) - 1;
		if( endIndex > startIndex + currRecordCount - 1 )
			endIndex = startIndex + currRecordCount - 1;

		k = 0
		for( i = startIndex; i <= endIndex; i++ )
		{
			arrScreenResult[k] = new Array();
			n = arrEasyQuery[i].length;
			for( j = 0; j < n; j++ )
			{
				arrScreenResult[k][j] = arrEasyQuery[i][j];
			} // end of for
			k++;
		} // end of for
	} // end of if
}

/*************************************************************
 *  ������ȷ��SQL��䣬���ز�ѯ�������
 *  ����  ��  strSQL����ȷ��SQL���
 *  ����ֵ��  ���ִ�гɹ������ʹ�ò�ѯ�������arrEasyQuery�����ִ�в��ɹ����򷵻�false
 *************************************************************
 */
function easyQuery( sql, start )
{
	if( sql == null || sql == "" ) return false;
	//if( frameName == null || frameName == "" ) frameName = parent.fraInterface.name;

	// ����̨��ѯ����
	parent.EX.fm.all( 'txtSQL' ).value = sql;
	parent.EX.fm.all( 'mOperate' ).value = "EASYQUERY";
	parent.EX.fm.all( 'startIndex' ).value = start;
	//parent.EX.fm.all( 'txtFrameName' ).value = frameName;
	parent.EX.fm.submit();

}

/*************************************************************
 *  ��ѯ�������ֵ���ѯ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *************************************************************
 */
function decodeResult()
{
	var strResult = "";
	var arrRow = new Array();
	var arrField = new Array();
	var n, m, i, j;
	var recordCount;

	strResult = parent.EX.fm.all('txtQueryResult').value;

	if( strResult == "" || strResult.substring(0, strResult.indexOf("|")) == "100")
	{
		//alert( "û���ҵ���ص�����!" );
		arrEasyQuery = null;
	}
	else
	{
		arrEasyQuery = new Array();

		// ��ֲ�ѯ������õ��������
		arrRow = strResult.split( RECORDDELIMITER );
		recordCount = arrRow[0].substring(arrRow[0].indexOf(FIELDDELIMITER) + 1);	;

		n = arrRow.length - 1;

		for( i = 1; i <= n; i++ )
		{
			arrField = arrRow[i].split( FIELDDELIMITER );
			m = arrField.length;
			arrEasyQuery[i-1] = new Array();
			for( j = 0; j < m; j++ )
			{
				arrEasyQuery[i-1][j] = arrField[j];
			}
		}

		// ���ÿ���Ϣ
		totalBlock = Math.ceil( recordCount / ( MAXSCREENLINES * MAXMEMORYPAGES ));
		currBlockRecord = n;
		currPageCount = Math.ceil( currBlockRecord / MAXSCREENLINES );
	}
}

/*************************************************************
 *  ���ݲ�ͬ�İ�ť�¼��õ���ͬ��ҳ����Ҫ������
 *  ����  ��  ��
 *  ����ֵ��  arrScreenResult
 *************************************************************
 */
function getPageArray()
{
	// ��ѯ��ť����ҳ��ť
	if( strEvent == "EASYQUERY" || strEvent == "FIRSTPAGE" )
	{
		currBlockIndex = 1;
		currPageIndex = 1;
		if( currPageCount == 1 )
			currRecordCount = currBlockRecord;
		else
			currRecordCount = MAXSCREENLINES;
	}

	// ��һҳ��ť
	if( strEvent == "NEXTPAGE" )
	{
		currBlockIndex = currBlockIndex + 1;
		currPageIndex = 1;
		if( currPageCount == 1 )
			currRecordCount = currBlockRecord;
		else
			currRecordCount = MAXSCREENLINES;
	}

	// ��һҳ��ť
	if( strEvent == "PREVIOUSPAGE" )
	{
		currBlockIndex = currBlockIndex - 1;
		currPageIndex = currPageCount;
		currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
	}

	// βҳ��ť
	if( strEvent == "LASTPAGE" )
	{
		currBlockIndex = totalBlock;
		currPageIndex = currPageCount;
		currRecordCount = currBlockRecord - ( currPageIndex - 1 ) * MAXSCREENLINES;
	}

	// �õ���ʾ������
	getPageData( currPageIndex );
}

/*************************************************************
 *  ��ѯ�ύ��ĸ��ִ���
 *  ����  ��  ������Ϣ
 *  ����ֵ��  ��
 *************************************************************
 */
function afterEasyQuery( strError )
{
	var arrResult = new Array();

	if( strError != "" )
	{
		alert( strError );
		return false;
	}

	decodeResult();
	getPageArray();

	// ��ʾ��ѯ���
	displayEasyResult( arrScreenResult );
}

/*************************************************************
 *  ��ѯƴ������
 *  ����  ��  fieldName -- �ֶ�����
 *			 controlName -- �ؼ�����
 *			 strOperate -- ������
 *			 type -- �ֶ�����( 0:�ַ��͡�1:������ )
 *  ����ֵ��  ƴ�õĴ�
 *************************************************************
 */
function getWherePart( fieldName, controlName, strOperate, fieldType )
{
	var strWherePart = "";
	var value = "";
	if( controlName == "" || controlName == null ) controlName = fieldName;
	//XinYQ modified on 2006-05-09 : �����������ͬ���ؼ����޷���ȡ�κ�ֵ,�޸�Ϊȡ�׸�ͬ���ؼ���ֵ
	//OLD : value = eval( "fm." + trim( controlName ) + ".value" );
	value = window.document.getElementsByName(trim(controlName))[0].value;
	if( value == "" || value == null ) return strWherePart;
	if( fieldType == null || fieldType == "" ) fieldType = "0";
	if( strOperate == "" || strOperate == null ) strOperate = "=";
	if( fieldType == "0" )	// 0:�ַ���
	{
		if(strOperate == "like")
		{
			strWherePart = " and " + trim( fieldName ) + " like '" + trim( value ) + "%' ";
		}
		else
		{
			strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + "'" + trim( value ) + "' ";
		}
	}
	if( fieldType == "1" )	// 1:������
	{
		strWherePart = " and " + trim( fieldName ) + trim( strOperate ) + trim( value ) + " ";
	}
	return strWherePart;
}


