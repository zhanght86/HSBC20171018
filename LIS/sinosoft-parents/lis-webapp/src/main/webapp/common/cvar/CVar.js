/*****************************************************************
 *               Program NAME: ��CVar
 *                 programmer:
 *                Create DATE:
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *         ������ɱ����ķ��䣬��ȡ���޸ģ��ͷŵȲ�����
 *
 *****************************************************************
 */
function CVar(cArrayLength,cArrayVars,cErrorString)
{
	this.marrayLength= cArrayLength || -1;	//��ŵ�ǰ����ĳ���
	this.arrayVars   = cArrayVars || new Array();	//������б���������,���������������ͣ�����ֵ
	this._CVar_mErrorString = cErrorString || "";	//�ñ���Ϊ��ִ�з�������ʱ����ʾ�Ĵ�����Ϣ

	this.initVar = _CVar_InitVar;
	this.addVar = _CVar_AddVar;
	this.updateVar = _CVar_UpdateVar;
	this.deleteVar = _CVar_DeleteVar;
	this.getVar = _CVar_GetVar;
	this.getIndex = _CVar_GetIndex;
	this.getError = _CVar_GetError;

	//������ҳ�滺�����д���ѡ�����������ά���鲻��ֱ�ӿ�ҳ�汣�������
	this.addArrVar = _CVar_AddArrVar;
}

/*************************************************************
 *			����һ���������
 *  ����  ��	cVarName	:������
 *				cVarType	:��������
 *				cVarValue	:����ֵ
 *  ����ֵ��  	���ִ�гɹ�������true,���򷵻�false
 *************************************************************
 */
function _CVar_AddArrVar(cVarName,cVarType,cVarValue)
{
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//����������ڣ�����������
		if (i!=-1)
		{
			//this._CVar_mErrorString="CVarOperate.js-->AddVar:����"+cVarName+"���ڣ�����������."
			return false;
		}
		i=this.marrayLength+1;
		this.marrayLength=i;
		this.arrayVars[i]=new Array();
		this.arrayVars[i][0]=cVarName;
		this.arrayVars[i][1]=cVarType;
		this.arrayVars[i][2] = new Array();
		var tArr = new Array();
		for (m=0; m<cVarValue.length; m++)
		{
			tArr[m] = new Array();
			for (n=0; n<cVarValue[m].length; n++)
			{
				tArr[m][n] = cVarValue[m][n];
			}
		}
		this.arrayVars[i][2] = tArr;
	}
    catch(ex)
	{
		alert("_CVar_AddArrVar throw Error");
		return false;
	}
	return true;
}

/*************************************************************
 *			����һ������
 *  ����  ��	cVarName	:������
 *				cVarType	:��������
 *				cVarValue	:����ֵ
 *  ����ֵ��  	���ִ�гɹ�������true,���򷵻�false
 *************************************************************
 */
function _CVar_AddVar(cVarName,cVarType,cVarValue)
{
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//����������ڣ�����������
		if (i!=-1)
		{
			//this._CVar_mErrorString="CVarOperate.js-->AddVar:����"+cVarName+"���ڣ�����������."
			return false;
		}
		i=this.marrayLength+1;
		this.marrayLength=i;
		this.arrayVars[i]=new Array();
		this.arrayVars[i][0]=cVarName;
		this.arrayVars[i][1]=cVarType;
		this.arrayVars[i][2]=cVarValue
	}
	catch(ex)
	{
		return false;
	}
	return true;
}

/*************************************************************
 *			�޸�һ������
 *  ����  ��	cVarName	:������
 *				cVarType	:��������
 *				cVarValue	:����ֵ
 *  ����ֵ��  	û��
 *************************************************************
 */
function _CVar_UpdateVar(cVarName,cVarType,cVarValue)
{
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//�õ�������λ������
		if(i==-1)
		{
			//���������û���ҵ����򷵻�false
		//	this._CVar_mErrorString="CVarOperate.js-->UpdateVar:������"+cVarName+"û���ҵ�."
			return false;
		}
		this.arrayVars[i][0]=cVarName;
		this.arrayVars[i][1]=cVarType;
		this.arrayVars[i][2]=cVarValue
	}
	catch(ex)
	{
		return false;
	}
	return true;
}

/*************************************************************
 *			����һ������
 *  ����  ��		cVarName	:������
 *  ����ֵ��  	���ظñ����ڱ����������е�λ��
 *************************************************************
 */
function _CVar_GetIndex(cVarName,cObj)
{
	var i;
	var j;
	if (cObj==null)
	{
		cObj=this;
	}
	try
	{
		i=cObj.marrayLength;
		for (j=0;j<=i;j++)
		{
			if(cObj.arrayVars[j][0]==cVarName)
			{
				return j;
			}
		}
	//	cObj._CVar_mErrorString="CVarOperate.js-->GetIndex():û���ҵ�����"+cVarName+"������.";
		return -1;
	}
	catch(ex)
	{
	//	cObj._CVar_mErrorString="CVarOperate.js-->GetIndex():�ڲ�������ʱ����.";
		return -1;
	}
}

/*************************************************************
 *			�õ�һ��������ֵ
 *  ����  ��		cVarName	:������
 *  ����ֵ��  	�������,���û�з��ָñ������򷵻�false
 *************************************************************
 */
function _CVar_GetVar(cVarName)
{
	var tReturn;
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//�õ�������λ������
		if(i==-1)
		{
			//���������û���ҵ����򷵻�false
			return false;
		}
		tReturn=this.arrayVars[i][2];
	}
	catch(ex)
	{
		return false;
	}
	return tReturn;
}

/*************************************************************
 *			ɾ��(�ͷ�)һ������,����ñ����������һ����
 *			�򽫺���ı���ǰ�ƣ����Ұ����ı����ͷš�
 *  ����  ��		cVarName	:������
 *  ����ֵ��  	û��
 *************************************************************
 */
function _CVar_DeleteVar(cVarName)
{
	var i;
	var j;
	var k;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//�õ�������λ������
		if(i==-1)
		{
			//���������û���ҵ����򷵻�false
			return false;
		}
		j=this.marrayLength;
		if (j > 0)
		{
			for (k=i;k<j;k++)
			{
				this.arrayVars[k][0]=this.arrayVars[k+1][0];
				this.arrayVars[k][1]=this.arrayVars[k+1][1];
				this.arrayVars[k][2]=this.arrayVars[k+1][2];
			}
		}
		this.marrayLength=j-1;
		delete this.arrayVars[j];
	}
	catch(ex)
	{
		return false;
	}
	return true;
}

/*************************************************************
 *			�����ִ�к�Ĵ�����Ϣ��ȡ����
 *  ����  ��
 *  ����ֵ��  	���ص�ǰ�Ĵ�����Ϣ�ַ���
 *************************************************************
 */
function _CVar_GetError()
{
	return this._CVar_mErrorString;
}

/*************************************************************
 *			��Ϣ���ʼ��
 *  ����  ��	��
 *  ����ֵ��  	��
 *************************************************************
 */
function _CVar_InitVar()
{
	this.marrayLength= -1;	//��ŵ�ǰ����ĳ���
	this.arrayVars =  new Array();	//������б���������,���������������ͣ�����ֵ
	this._CVar_mErrorString =  "";	//�ñ���Ϊ��ִ�з�������ʱ����ʾ�Ĵ�����Ϣ
}