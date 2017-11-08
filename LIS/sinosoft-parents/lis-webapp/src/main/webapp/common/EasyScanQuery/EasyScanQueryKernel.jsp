<%
//�������ƣ�EasyScanQueryKernel.jsp
//�����ܣ�EasyScanQuery��ѯ���ܵĺ��ĺ���
//�������ڣ�2002-11-07
//������  ������
//���£���ǿ 2005-03-27 �޸Ĳ�ѯ�ӿ�
//���¼�¼��  ������    ��������     ����ԭ��/����       
%>

<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>


<%!

//************************************************
//����DocIDֱ�Ӳ��ҹ�����Ӱ���ļ�
//����EasyScanQueryUI�����ύ�����ݿ���ң����ؽ���ַ���strResult
//************************************************
public String[] easyScanQueryKernel0(String DocID,
				     String clientUrl) { 
  String[] arrResult;
  
  if (DocID == null || DocID.equals("") || DocID.equals("undefined")) {
    loggerDebug("EasyScanQueryKernel","EasyScanQuery don't accept DocID!");
    return null;
  }

  EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
  VData tVData = new VData();
  VData tVData1 = new VData();
  tVData.add(DocID);
  tVData.add(clientUrl);
  tEasyScanQueryUI.submitData(tVData, "QUERY||0");

  if(tEasyScanQueryUI.mErrors.needDealError()) {
	  loggerDebug("EasyScanQueryKernel","EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
    arrResult = new String[tVData1.size()];
    for (int i=0; i<tVData1.size(); i++) {
      arrResult[i] = (String)tVData1.get(i);
      //loggerDebug("EasyScanQueryKernel",arrResult[i]);
    }  
  }
  return arrResult;
}

//************************************************
//����ҵ����������������Ӱ���ļ��Ĳ���
// ����EasyScanQueryUI�����ύ�����ݿ���ң����ؽ���ַ���strResult
//************************************************
public String[] easyScanQueryKernel1(String BussNo,
				    String BussNoType,
				    String BussType,
				    String SubType,
				    String clientUrl) { 
  String[] arrResult;
  
  loggerDebug("EasyScanQueryKernel",BussNo);
  loggerDebug("EasyScanQueryKernel",BussNoType);
  loggerDebug("EasyScanQueryKernel",BussType);
  loggerDebug("EasyScanQueryKernel",SubType);
  if (BussNo == null || BussNo.equals("") || BussNo.equals("undefined") || BussNo.equals("null")) {
    loggerDebug("EasyScanQueryKernel","EasyScanQuery don't accept BussNo!");
    return null;
  }

  EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
  VData tVData = new VData();
  VData tVData1 = new VData();
  tVData.add(BussNo);
  tVData.add(BussNoType);
  tVData.add(BussType);
  tVData.add(SubType);
  tVData.add(clientUrl);
  
  tEasyScanQueryUI.submitData(tVData, "QUERY||1");

  if(tEasyScanQueryUI.mErrors.needDealError()) {
	  loggerDebug("EasyScanQueryKernel","EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
    arrResult = new String[tVData1.size()];
    for (int i=0; i<tVData1.size(); i++) {
      arrResult[i] = (String)tVData1.get(i);
      //loggerDebug("EasyScanQueryKernel",arrResult[i]);
    }
	  
  }
  
  return arrResult;
}

//************************************************
//����һ�ֲ�Ѱ����:����ҵ��������ʱ�Ĳ�ѯ  
//����EasyScanQueryUI�����ύ�����ݿ���ң����ؽ���ַ���strResult
//************************************************
public String[] easyScanQueryKernel2(String BussNo,
				     String BussNoType,
				     String BussNo2,
				     String BussNoType2,
				     String BussType,
				     String SubType,
				     String clientUrl) { 
  String[] arrResult;
  
  if (BussNo == null || BussNo.equals("") || BussNo.equals("undefined")) {
    loggerDebug("EasyScanQueryKernel","EasyScanQuery don't accept BussNo!");
    return null;
  }

  EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
  VData tVData = new VData();
  tVData.add(BussNo);
  tVData.add(BussNoType);
  tVData.add(BussNo2);
  tVData.add(BussNoType2);
  tVData.add(BussType);
  tVData.add(SubType);
  tVData.add(clientUrl);
  tEasyScanQueryUI.submitData(tVData, "QUERY||2");

  if(tEasyScanQueryUI.mErrors.needDealError()) {
	  loggerDebug("EasyScanQueryKernel","EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult();
	  VData mData = new VData();
	  mData = (VData)tVData.get(0);
    arrResult = new String[mData.size()];
    for (int i=0; i<mData.size(); i++) {
      arrResult[i] = (String)mData.get(i);
      //loggerDebug("EasyScanQueryKernel",arrResult[i]);
    }
	  
  }
  
  return arrResult;
}
//************************************************
//����ҵ����������������Ӱ���ļ��Ĳ���
//����EasyScanQueryUI�����ύ�����ݿ���ң����ؽ���ַ���strResult
//************************************************
public String[] easyScanQueryKernel3(String BussNo,
		String BussType, String clientUrl) { 
String[] arrResult;

loggerDebug("EasyScanQueryKernel",BussNo);
if (BussNo == null || BussNo.equals("") || BussNo.equals("undefined")) {
  loggerDebug("EasyScanQueryKernel","EasyScanQuery don't accept BussNo!");
  return null;
}

EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
VData tVData = new VData();
VData tVData1 = new VData();
tVData.add(BussNo);
tVData.add(BussType);
tVData.add(clientUrl);

tEasyScanQueryUI.submitData(tVData, "QUERY||3");

if(tEasyScanQueryUI.mErrors.needDealError()) {
	  loggerDebug("EasyScanQueryKernel","EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
} else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
  arrResult = new String[tVData1.size()];
  for (int i=0; i<tVData1.size(); i++) {
    arrResult[i] = (String)tVData1.get(i);
    //loggerDebug("EasyScanQueryKernel",arrResult[i]);
  }
	  
}

return arrResult;
}
//************************************************
//Added by niuzj 20060926,��ʷ��֤ɨ��Ӱ���ѯ
//����DocIDֱ�Ӳ��ҹ�����Ӱ���ļ�
//����EasyScanQueryOldDataUI�����ύ�����ݿ���ң����ؽ���ַ���strResult
//************************************************
public String[] easyScanQueryKernel9999(String DocID,
				     String clientUrl) { 
  String[] arrResult;
  
  if (DocID == null || DocID.equals("") || DocID.equals("undefined")) {
    loggerDebug("EasyScanQueryKernel","EasyScanQueryOldData don't accept DocID!");
    return null;
  }

  EasyScanQueryOldDataUI tEasyScanQueryOldDataUI = new EasyScanQueryOldDataUI();
  VData tVData = new VData();
  VData tVData1 = new VData();
  tVData.add(DocID);
  tVData.add(clientUrl);
  tEasyScanQueryOldDataUI.submitData(tVData, "QUERY||9999");

  if(tEasyScanQueryOldDataUI.mErrors.needDealError()) {
	  loggerDebug("EasyScanQueryKernel","tEasyScanQueryOldDataUI throw errors:" + tEasyScanQueryOldDataUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryOldDataUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
    arrResult = new String[tVData1.size()];
    for (int i=0; i<tVData1.size(); i++) {
      arrResult[i] = (String)tVData1.get(i);
      //loggerDebug("EasyScanQueryKernel",arrResult[i]);
    }  
  }
  return arrResult;
}


//************************************************
//����ҵ����������������Ӱ���ļ��Ĳ���
// ����EasyScanQueryUI�����ύ�����ݿ���ң����ؽ���ַ���strResult
//************************************************
public String[] easyScanQueryKernel9(String BussNo,
				    String BussNoType,
				    String BussType,
				    String SubType,
				    String clientUrl) { 
  String[] arrResult;
  
  loggerDebug("EasyScanQueryKernel",BussNo);
  loggerDebug("EasyScanQueryKernel",BussNoType);
  loggerDebug("EasyScanQueryKernel",BussType);
  loggerDebug("EasyScanQueryKernel",SubType);
  if (BussNo == null || BussNo.equals("") || BussNo.equals("undefined") || BussNo.equals("null")) {
    loggerDebug("EasyScanQueryKernel","EasyScanQuery don't accept BussNo!");
    return null;
  }

  EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
  VData tVData = new VData();
  VData tVData1 = new VData();
  tVData.add(BussNo);
  tVData.add(BussNoType);
  tVData.add(BussType);
  tVData.add(SubType);
  tVData.add(clientUrl);
  
  tEasyScanQueryUI.submitData(tVData, "QUERY||9");

  if(tEasyScanQueryUI.mErrors.needDealError()) {
	  loggerDebug("EasyScanQueryKernel","EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
    arrResult = new String[tVData1.size()];
    for (int i=0; i<tVData1.size(); i++) {
      arrResult[i] = (String)tVData1.get(i);
      //loggerDebug("EasyScanQueryKernel",arrResult[i]);
    }
	  
  }
  
  return arrResult;
}

%>


