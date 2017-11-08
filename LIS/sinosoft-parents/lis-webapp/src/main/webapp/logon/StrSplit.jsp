<%@page import="java.util.*"%>
<%//用法和效果同JAVASCRIPT中的SPLIT一样，参数使用不同%>
<%! 
public String[] StrSplit(String strMain, String strDelimiters) {
  int i;
  int intIndex = 0;                    //记录分隔符位置，以取出子串
  int intCount = 0;                    //记录子串个数
  Vector resultVector = new Vector();  //存储子串的数组
  String strSub = "";                  //存放子串的中间变量

  if (strMain.length()<strDelimiters.length()) {  //若主字符串比分隔符串还要短的话,则返回空字符串
    //resultVecter = null;
    //return strArrResult;
  };
  
  intIndex = strMain.indexOf(strDelimiters);  //取出第一个分隔符在主串中的位置
  
  if (intIndex == -1) {                       //在主串中找不到分隔符
    //resultVecter = null;
    //return strArrResult;
  };
  
  while (intIndex != -1) {                    //分割主串到数组中
    strSub = strMain.substring(0,intIndex);
    if (intIndex != 0) {
      resultVector.add(intCount, strSub.trim());
      intCount++;
    };
    
    strMain = strMain.substring(intIndex + 1);
    intIndex = strMain.indexOf(strDelimiters);
  };
  
  if (strMain != "") {			      //如果最末不是分隔符，取最后的字符串
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
