<%@page import="java.util.*"%>
<%//�÷���Ч��ͬJAVASCRIPT�е�SPLITһ��������ʹ�ò�ͬ%>
<%! 
public String[] StrSplit(String strMain, String strDelimiters) {
  int i;
  int intIndex = 0;                    //��¼�ָ���λ�ã���ȡ���Ӵ�
  int intCount = 0;                    //��¼�Ӵ�����
  Vector resultVector = new Vector();  //�洢�Ӵ�������
  String strSub = "";                  //����Ӵ����м����

  if (strMain.length()<strDelimiters.length()) {  //�����ַ����ȷָ�������Ҫ�̵Ļ�,�򷵻ؿ��ַ���
    //resultVecter = null;
    //return strArrResult;
  };
  
  intIndex = strMain.indexOf(strDelimiters);  //ȡ����һ���ָ����������е�λ��
  
  if (intIndex == -1) {                       //���������Ҳ����ָ���
    //resultVecter = null;
    //return strArrResult;
  };
  
  while (intIndex != -1) {                    //�ָ�������������
    strSub = strMain.substring(0,intIndex);
    if (intIndex != 0) {
      resultVector.add(intCount, strSub.trim());
      intCount++;
    };
    
    strMain = strMain.substring(intIndex + 1);
    intIndex = strMain.indexOf(strDelimiters);
  };
  
  if (strMain != "") {			      //�����ĩ���Ƿָ�����ȡ�����ַ���
    resultVector.add(intCount, strMain.trim());
  }

  String[] tArrResult = new String[resultVector.size()];
  for (i=0; i<resultVector.size(); i++) {
    tArrResult[i] = (String)resultVector.get(i);
  }

  return tArrResult;
};

%>

<%

//String tStr = "aa|bb|cc|";
//String[] tResult = StrSplit(tStr, "|");

//for (int i=0; i<java.lang.reflect.Array.getLength(tResult); i++) {
//  out.println(tResult[i] + " : ");
//}

%>
