package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class Stringtools {
private static Logger logger = Logger.getLogger(Stringtools.class);
    static String  realpath=new String("D:/bea/wlserver6.1/config/mydomain/applications/DefaultWebApp/jsp/");

    public static String createspace(int num)
    {
    String str="";
    if (num>=1)
    {
        for (int i=1;i<=num;i++)
        {
        str=str+" ";

        }

    }
    else
       {
           logger.debug("err: 不能生成指定个数的空格");
           str="";
       }
       return str;
    }
    /***********************************将String 转成 int********************/
    public  static int stringtoint(String x)
    {
        if (x==null)
        {
        x="0";
        }
        if (x.trim().length()==0)
        {
        x="0";
        }
            int y;
            y=Integer.parseInt(x);
            return y;
    }
    public  static long stringtolong(String x)
    {
            long y;
            y=Long.parseLong(x);
            return y;
    }
    /********************************将String 转成 Double******************/
    public  static double stringtodouble(String x)
    {
          double y;

          if (x==null)
          {
            y=0.00;
            return y;
            }
            else
            {
                if (x.trim().length()==0)
                {
                    y=0.00;
                    return y;
                }
                else
                {
                    Double mydouble=new Double(x);
                    y=mydouble.doubleValue();
                }
            }
          return y;
    }

    /********************************将double 转成 String******************/
    public  static String doubletostring(double x)
    {
            String y=String.valueOf(x);
            return y;

    }
    public  static String longtostring(long x)
    {
            String y=String.valueOf(x);
            return y;

    }
    public static String inttostring(int x)
    {
            String y=String.valueOf(x);
            return y;
            }

    /*******************************小写字母转换为大写**********************/
    public  static String lowertoupper(String x)
    {
       String y;
       y=x.toUpperCase();
       return y;
    }

    /**********************************trim尾部空格*************************/
    public  static String trimend(String x)
    {
            String y;
            y=x.trim();
            return y;
    }

    /******************将文本框中的ISO String转成 GB2312String**************/
   public static String convert( String str )
   {
      try
       {
        byte[] bytesStr=str.getBytes( "ISO-8859-1" ) ;
        return new String( bytesStr, "GB2312" ) ;
        //return str;
       }
     catch( Exception ex)
       {
            return str ;
       }
   }
   //将一个字符串（utf-8）转成gb2312
    public static String convertutftogb2312( String str )
   {
      try
       {
         byte[] bytesStr=str.getBytes("UTF-8") ;
         return new String( bytesStr,"iso-8859-1") ;
         }
     catch( Exception ex)
       {
            return str ;
       }
   }
   //字符串转成字符串
   public  static String stringtostring(String x,int i)
  {
      if (x==null)
      {
          x="0.00";
      }
      if (x.trim().length()==0)
      {
          x="0.00";
      }
    String str3="";
    String str1="";
    String str2="";
    if (x.indexOf(".")<0)   //没有小数点!!
      {
            String str=".";
            for (int k=1;k<=i;k++)
            {
            str=str+"0";
            }
             return x+str;
     }
    else
      {

            str1=x.substring(0,(x.indexOf(".")))+".";
            str2=x.substring((x.indexOf(".")+1),x.length());
            if (str2.length()==i)    //小数位数长于要求的位数
            {
                    str3=str1+str2;
            }
            else
            {
                    if (str2.length()<i)
                    {
                            int j=i-str2.length();
                            String str="";
                            for (int k=1;k<=j;k++)
                               {
                                    str=str+"0";
                                }
                        str3=str1+str2+str;
                    }
                    else
                    {
                            Stringtools  mydouble=new Stringtools();
                            double value=mydouble.stringtodouble(x);
                            NumberFormat value1 = NumberFormat.getNumberInstance();
                            value1.setMaximumFractionDigits(i);
                            String s=value1.format(value);
                            str3=new String(s);
                    }
            }
            //如果数据小于1，前面补0，即   .28------>0.28
            if (str3.indexOf(".")==0)
            {
                str3 = "0" + str3;
            }
            return str3;
      }

  }//本方法的

/*********************************************************************************/
//文件读取(1.返回值为"Y"表示成功,其它均表示失败)
//(要上传的文件名)
    public static String fileread(String uploadfilename)
    {
            uploadfilename=new String (Stringtools.convert(uploadfilename));
            String content="";
            int rowCount = 0;
            int  columns=0;

            String yn="";
            if (uploadfilename.length()==0)
                   {yn="请输入有效的文件名!!  Please Enter filename!!";

                    }
                    File workfile = new File(uploadfilename);

if (!workfile.exists())
{
 yn="源文件不存在!!source file is not exists!!(TXT)";
 }
 else
 {
    if (workfile != null)
     {
            try {
            BufferedReader reader = new BufferedReader(new FileReader(workfile));
            String inLine = reader.readLine();
                    while (inLine != null)
                           {
                            if (inLine.length() + 1 > columns)
                            columns = inLine.length() + 1;
                            content += (inLine + System.getProperty("line.separator"));
                            inLine = reader.readLine();
                            rowCount++;
                            }
                    yn=content;
        reader.close();


                    //logger.debug(content);//输出得到的文件的内容
            }//try
            catch (IOException e)
            {
                    yn="异常错误!!  Other Errors!!";
                    return yn;
            }


  }
  }

            return yn;
    }//本方法的括号
    public static String filewrite(String content,String sourcepath,String touserid)
    {
      logger.debug("content:"+content);
      String writefilename=(new Stringtools()).pathtopath(sourcepath,touserid);
      logger.debug("writefilename:"+writefilename);

      //将读取的文件内容转成GB2312
      content=new String(content);
      writefilename=new String(Stringtools.convert(writefilename));
      String yn="";



      //路径转换结束

                         //开始写入文件
            try
    {
            File f = new File(writefilename);
            PrintWriter out = new PrintWriter(new FileWriter(f));
            //out.print(content + "\n");
            out.print(content);
            out.close();
            yn="WY";
    }
    catch (IOException e)
    {
            yn="写入文件错误!! Write file error!!"+e.getMessage();
    }
            //写入文件结束


            return yn;
            }//本方法的()
    /***************************写文件的方法**********************/
    //写入文件(文件内容,源文件路径)目的文件写在同一路径下
    public static String filewritesamepath(String content,String path)
    {
      String writefilename="";
      writefilename=Stringtools.pathtopath(path,"");
       //将读取的文件内容转成GB2312
      content=new String(content);
      writefilename=new String(Stringtools.convert(writefilename));
      String yn="";
      //路径转换结束
     //开始写入文件
            try
        {
            File f = new File(writefilename);
            FileWriter mywriter=new FileWriter(f);
    PrintWriter out = new PrintWriter(mywriter);
            //out.print(content + "\n");
            //out.print(content);
    mywriter.close();
            out.close();
            yn="WY";

    }
    catch (IOException e)
    {
            yn="写入文件错误!! Write file error!!";
    }
            //写入文件结束


            return yn;
            }//本方法的()
   /*****************路径转换*************************/
   //功能:取得要写入的文件的完整path(并转义成\\,如果目录不存在,要建立目录!!)
    public static String pathtopath(String localfilename,String writedir)
    {
            realpath=new String(Stringtools.convert(realpath));
            localfilename=new String(localfilename);
            logger.debug("localfilename:"+localfilename);
            writedir=new String(writedir);
            String filename1="";
            String writepathfilename="";
            String writedir1=writedir.trim();
                    int num1=localfilename.lastIndexOf("\\".toString());//最后一个"\"的位置
                    if (num1==-1)
                        {filename1=localfilename.trim();
                         writedir1=writedir1.trim()+"/";
                        }
                     else
                     {
                    logger.debug("num1:"+num1);
                    filename1=localfilename.substring(num1,(localfilename.length()));//取真正的文件名
                    }
                    //判断目录是否存在,如果不存在,就建立一个
            String	tmp_filename=new String(realpath+writedir1);
            File tmp_path=new File(tmp_filename);
            if (!tmp_path.isDirectory())
                {boolean isdir=tmp_path.mkdirs();}

                //创建目录结束
                    writepathfilename=new String(realpath+writedir1+filename1);  //要写入文件的真实路径(dos格式)
                    return writepathfilename;

            }//本方法的{}


/****************************************************************************************/
/*****************8按安节读取文件********************************************************/
/****************************************************************************************/
//按字节读取文件内容                 (源文件路径,写给某一位用户)
  public static String uploadbyte(String sourcepath,String touserid)
    {
            String uploadfilename=Stringtools.convert(sourcepath);
            //String uploadfilename=sourcepath.trim();
            String writefilename=(new Stringtools()).pathtopath(sourcepath,touserid);
            writefilename=Stringtools.convert(writefilename);
            byte bytearray[]=new byte[10000000];

            int rowCount = 0;
            int  columns=0;
            String yn="";
            if (uploadfilename.length()==0)
                   {
                    yn="请输入有效的文件名!!  Please Enter filename!!";
                   }
                    File workfile = new File(uploadfilename);

if (!workfile.exists())
{
 yn="源文件不存在!!source file is not exists!!(byte)";
 }
 else
 {

    if (workfile != null)
     {
            //以字节形式读取文件
            try{
            FileInputStream fileinputstream1 = new FileInputStream(uploadfilename);
            int filesize = fileinputstream1.available();
            //bytearray[]=new byte[filesize];
            int n=fileinputstream1.read(bytearray);
            fileinputstream1.close();
             }
            catch (IOException e)
            {
                    yn="File read Error!!";
            }
            //读取文件结束


            //路径转换开始
            int num2=sourcepath.lastIndexOf("\\".toString());//最后一个"\"的位置
            String	filename2=sourcepath.substring(num2+1,(sourcepath.length()));

            writefilename=Stringtools.convert(("C:/Program Files/Apache Group/Tomcat 4.1/webapps/yindai/jsp/")+touserid.trim()+"/"+filename2);
            //路径转换结束

            File tmp_filename=new File(writefilename);
    if (!tmp_filename.exists())
               {//以字节形式一次性写成文件

            try{
            logger.debug("new filepath1:"+writefilename);
            FileOutputStream fileoutputstream1=new FileOutputStream(writefilename);

            //FileOutputStream fileoutputstream1=new FileOutputStream("C:/Apache Group/Tomcat 4.1/webapps/myjsp/susu0801.doc");
;

            fileoutputstream1.write(bytearray);
            fileoutputstream1.close();
             }
            catch (IOException e)
            {
                    yn="File write Error!!(byte)";
                    }
            //写入文件结束
            yn="WY";
      }
       else
   {
    yn="同名文件已经存在,请改名后再上传!!File is exists,please Rename!";

    }

   }
  }

    return yn;
}//本方法的括号

//判断字符串相等
    public static boolean stringequals(String str1,String str2)
    {
           if (str1==null)
           {
               str1="";
           }
           if (str2==null)
           {
               str2="";
           }
            Object obj1=str1.trim().toString();
            Object obj2=str2.trim().toString();
            boolean tf;
            if (obj1.equals(obj2))
               {tf=true;
               }
            else
            {
                    tf=false;
                    }

            return tf;
    }//本方法的()

    //从路径中找出真正的文件名(完整的路径)
    public static String filename(String tmp_locapath)
    {
            String tmp_filename;
            int num1=tmp_locapath.lastIndexOf("\\".toString());//最后一个"\"的位置
            tmp_filename=tmp_locapath.substring(num1+1,(tmp_locapath.length()));//取真正的文件名
                    //判断目录是否存在,如果不
            return tmp_filename;

    }//本方法的()

    //删除指定的文件(真正的文件名,目录)
    public static String delefile(String filename,String touserid)
    {
            String yn=new String("");
            String pathfilename=realpath+"/"+touserid.trim()+"/"+filename;
            logger.debug(pathfilename);
            File workfile=new File(pathfilename);
            if (workfile.exists())
            {
                    boolean yn1=workfile.delete();
                    if (yn1)
                    {
                    yn="DY";}
                    else
                    {yn="删除文件错误(Delete file error!!)";}
                    }
           else
           {
            yn="文件不存在!!File is not exists!!";

            }
            return yn;
    }//本方法的()
    public static String pad(String str)
    {
            String password=Stringtools.inttostring(str.hashCode());
            return password;

    }//本方法的()

    //向文本文件中追加数据
    public static String writeappend(String filename,String str)
    {
            String filepath=realpath+filename;
            try{
            FileWriter f=new FileWriter(filepath,true);
            PrintWriter out=new PrintWriter(f);
            //out.print(str+"\n");
            out.print(str);
            out.close();
            f.close();
            return "ok";
            }
            catch(IOException e)
            {
                    return e.toString();
                    }

    }//本方法的()

    //依据身份证计算出生日期及性别
    public static String idtobirthday(String id)
    {
            String birthdaysex="";
            String birthday,c_sex;
            id=id.trim();
            int lenid=id.length();
            if (lenid==18)  //18位身份证
            {
                            birthday=id.substring(6,14);
                            String sex=id.substring(16,17);
                            //out.println(birthday+"<br>"+sex);

                            int intsex=Stringtools.stringtoint(sex);
                            c_sex="";
                            if (intsex%2==0) //偶数
                            {
                                    c_sex="女";
                            }
                            else
                            {
                                    c_sex="男";
                            }
                            //out.println(c_sex);

            }
            else
            {
                     if (lenid==15)  //15位身份证
                     {
                            birthday="19"+id.substring(6,12);
                            String sex=id.substring(14,15);
                            //out.println(birthday+"<br>");

                            int intsex=Stringtools.stringtoint(sex);
                            c_sex="";
                            if (intsex%2==0) //偶数
                            {
                                    c_sex="女";
                            }
                            else
                            {
                                    c_sex="男";
                            }
                            //out.println(c_sex);

                     }
                     else
                     {
                      //错误的身份证位数
                       birthday="";
                       c_sex="";
                       birthdaysex=new String("N");
                     }
            }
            String tmpyndate=yndate(birthday);
            if (stringequals(tmpyndate,"N"))
                {
                    birthdaysex=new String("N");
                }
            else
            {
                  birthdaysex=new String(birthday+c_sex);
            }
            return birthdaysex;

    }  //本方法的（）
    //判断日期的合法性，如果合法返回"Y",不合法返回"N"
    public static String yndate(String tmp)
    {
java.util.Calendar cal=java.util.Calendar.getInstance();
    int  year1=stringtoint(tmp.substring(0,4));
int  month1=stringtoint(tmp.substring(4,6));
int  day1=stringtoint(tmp.substring(6,8));
    cal.set(year1, month1-1, day1);
Date mydate=cal.getTime();
    String stringdate=Stringtools.datetostr(mydate);
    //out.println(""+tmp_date);
    String yn="";
    if (Stringtools.stringequals(tmp,stringdate))
    {
     yn="Y";
    }
    else
    {
      yn="N";
    }
    return yn;

    }//本方法的（）
    public static boolean yndate1(String tmp)
    {
java.util.Calendar cal=java.util.Calendar.getInstance();
    int  year1=stringtoint(tmp.substring(0,4));
int  month1=stringtoint(tmp.substring(4,6));
int  day1=stringtoint(tmp.substring(6,8));
    cal.set(year1, month1-1, day1);
Date mydate=cal.getTime();
    String stringdate=Stringtools.datetostr(mydate);
    //out.println(""+tmp_date);
    String yn="";
    if (Stringtools.stringequals(tmp,stringdate))
    {
     return true;
    }
    else
    {
      return false;
    }

            }//本方法的（）

            //补足空格(字符串，总位数，左边还是右边)
    public static String fixedstring(String str,int num,String rl)
    {
            int length=str.length();
            int k=num-length;
            String strspace="";
            while (k>0)
            {
                    k=k-1;
                    //out.println(k+"<br>");
                    strspace=new String(strspace+" ");
            }
            //空格加在右边
            if (Stringtools.stringequals(rl,"right"))
                    {
                    str=new String(str+strspace);
                    }
            //空格加在右边
            if (Stringtools.stringequals(rl,"left"))
                    {
                    str=new String(strspace+str);
                    }
            return str;

        }//本方法的（）
            public static String deletechar(String str,String str1)
            {
                    String strout="";
                    int lastindex=str.length()-str.lastIndexOf(str1);
                    int firstindex=1;
                    while (str.length()>=lastindex)
                    {
                            firstindex=str.indexOf(str1);
                            strout=strout+str.substring(0,firstindex);
                            str=str.substring(firstindex+1,str.length());

                    }
            String strout1=new String(strout+str);
            return strout1;

                    }//本方法的{}
    public static String convert1( String str )
   {
      try
       {
         byte[] bytesStr=str.getBytes( "ISO-8859-1" ) ;
        return new String( bytesStr, "gb2312" ) ;

       }
     catch( Exception ex)
       {
            return str ;
       }
   }
//服务器路径文件copy(源泉文件，目标文件)
public static boolean filecopy(String filename1,String filename2)
    {
            String uploadfilename=filename1;
            //String uploadfilename=sourcepath.trim();
            String writefilename=filename2;
            writefilename=Stringtools.convert(writefilename);
            byte bytearray[]=new byte[10000000];

            int rowCount = 0;
            int  columns=0;
        boolean yn=false;
            if (uploadfilename.length()==0)
                   {
                    yn=false;
                   }
                    File workfile = new File(uploadfilename);

if (!workfile.exists())
{
 yn=false;
 }
 else
 {

    if (workfile != null)
     {
            //以字节形式读取文件
            try{
            FileInputStream fileinputstream1 = new FileInputStream(uploadfilename);
            int filesize = fileinputstream1.available();
            //bytearray[]=new byte[filesize];
            int n=fileinputstream1.read(bytearray);
            fileinputstream1.close();
             }
            catch (IOException e)
            {
                    yn=false;
            }
            //读取文件结束
            //开始写文件
            File tmp_filename=new File(writefilename);
    if (!tmp_filename.exists())
               {//以字节形式一次性写成文件

            try{
            logger.debug("new filepath1:"+writefilename);
            FileOutputStream fileoutputstream1=new FileOutputStream(writefilename);

            //FileOutputStream fileoutputstream1=new FileOutputStream("C:/Apache Group/Tomcat 4.1/webapps/myjsp/susu0801.doc");
;

            fileoutputstream1.write(bytearray);
            fileoutputstream1.close();
             }
            catch (IOException e)
            {
                    yn=false;
                    }
            //写入文件结束
            yn=true;
      }
       else
   {
    yn=false;

    }

   }
  }

    return yn;
}//本方法的括号
//将一个字符串中的所有字符串替换成一个新的串
public static String replaceallstring(String content,String oldstr,String newstr)
{
String newcontent="";
int i=0;
while (i!=-1)
{
i=content.indexOf(oldstr);
if (i==-1)
{
    break;
    }
content=content.substring(0,i)+newstr+content.substring(i+oldstr.length(),content.length());
//utf-8    gbk
}
newcontent=new String(content);
return newcontent;
}

/*
//上个月的最后一天(年月)例如("200311"),结果为上个月的最后一天"20031031"
public static String lastdate(String  stryearmonth)
{
String tmp_date="";
//java.util.Date mydate=new java.util.Date();
java.sql.Date mydate=new java.sql.Date();
try
{
    mydate=java.sql.Date.valueOf(stryearmonth+"31");
 }
catch(java.lang.IllegalArgumentException ex)
{
     try
    {
        mydate=java.sql.Date.valueOf(stryearmonth+"30");
     }
     catch(java.lang.IllegalArgumentException ex1)
     {
        try
        {
            mydate=java.sql.Date.valueOf(stryearmonth+"29");
            }
            catch(java.lang.IllegalArgumentException ex2)
            {
                try
                {
                    mydate=java.sql.Date.valueOf(stryearmonth+"28");
                }
                catch(java.lang.IllegalArgumentException ex3)
                {
                    logger.debug("0:"+ex3.getMessage());
                    }

                }

        }

}
tmp_date=inttostring(((mydate.getYear()+1900)*10000)+mydate.getMonth()*100+mydate.getDay());
return tmp_date;
}
*/


//将指定message写入文件,文件名为如:(/bea/conn,"这是一个测试")
//文件存储的真正文件为:/bea/conn日期.txt
public static void  diary(String serverpath,String msg)
{
java.util.Date mydate=new java.util.Date();
String strdate=Stringtools.datetostr(mydate);
try{
 PrintWriter diary = new PrintWriter(new FileWriter(serverpath+strdate+".txt", true), true);
 diary.println(new Date() + ": " + msg);
 diary.close();
}catch(java.io.IOException exio){
logger.debug("写日志错误！！/Write diary error!!");
}
}

    //本方法是计算两个日期相减,差多少天("20040101","20041010")
     public int date_minus(String sdate,String edate)
     {
            int year1=stringtoint(sdate.substring(0,4));
            int month1=stringtoint(sdate.substring(4,6));
            int day1=stringtoint(sdate.substring(6,8));
            int year2=stringtoint(edate.substring(0,4));
            int month2=stringtoint(edate.substring(4,6));
            int day2=stringtoint(edate.substring(6,8));

            Calendar cal = Calendar.getInstance();
            cal.set(year2, month2-1, day2);
            Date mydate2=cal.getTime();
            cal.set(year1,month1-1,day1);
            Date mydate1=cal.getTime();
            //logger.debug("mydate1:"+mydate1+"  mydate2:"+mydate2);
            long tmptime = 1000*3600*24; //A day in milliseconds
            long  num=mydate2.getTime()-mydate1.getTime();
            long jieguo=num/tmptime;
            Long mylong =new Long(jieguo);
            int intjieguo=mylong.intValue();

            return intjieguo;

     }
     //本方法是计算日期加/减一个整数后的日期("20040101",10)
     public String date_p_m(String sdate,int tmpnum)
     {
            int year1=stringtoint(sdate.substring(0,4));
            int month1=stringtoint(sdate.substring(4,6));
            int day1=stringtoint(sdate.substring(6,8));
            GregorianCalendar oneDay = new GregorianCalendar(year1, month1-1, day1);
            oneDay.add(GregorianCalendar.DATE, tmpnum);
            Date mydate = oneDay.getTime();
            String strdate=Stringtools.datetostr(mydate);

            return strdate;
     }
     //取星期几（星期日为0,星期一为 1）
     public int weekday()
     {
            Calendar cal=Calendar.getInstance();
            int weekday=cal.get(cal.DAY_OF_WEEK)-1;
            return weekday;
     }
      public static String today()
     {
                      Calendar my=Calendar.getInstance();
                              int myyear=my.get(Calendar.YEAR);
                              int mymonth=my.get(Calendar.MONTH)+1;
                              int myday=my.get(Calendar.DAY_OF_MONTH);
                              String str=inttostring(myyear*10000+mymonth*100+myday);
                              return str;



     }
            public static String datetostr(java.util.Date strdate)
           {
            DateFormat df = DateFormat.getDateInstance();
                    String s = df.format(strdate);
                    int myyear=stringtoint(s.substring(0,s.indexOf("-")))*10000;
                    s=s.substring(s.indexOf("-")+1,s.length());
                    int mymonth=stringtoint(s.substring(0,s.indexOf("-")))*100;
                    s=s.substring(s.indexOf("-")+1,s.length());
                    int myday=Stringtools.stringtoint(s);
                    String str=inttostring(myyear+mymonth+myday);
                     return str;

    }
    //判断是否为闰年
    public static boolean ynrunnian(String strdate)
    {
            Calendar  cal  =  Calendar.getInstance();
            boolean leapYear  =  (  (GregorianCalendar)cal  ).isLeapYear(Stringtools.stringtoint(strdate.substring(0,4)));
            return leapYear;
    }
    public static int gethour()
    {
              java.util.Calendar cal=java.util.Calendar.getInstance();
               return cal.get(cal.HOUR);
     }
     public static int getminute()
    {
              java.util.Calendar cal=java.util.Calendar.getInstance();
               return cal.get(cal.MINUTE);
     }
     public static int getsecond()
    {
              java.util.Calendar cal=java.util.Calendar.getInstance();
               return cal.get(cal.SECOND);
     }
     public static String gettime()
    {
              java.util.Calendar cal=java.util.Calendar.getInstance();
              String  hour="00"+inttostring(cal.get(cal.HOUR));
              String  minute="00"+cal.get(cal.MINUTE);
              String  second="00"+cal.get(cal.SECOND);
              String strtime=hour.substring(hour.length()-2,hour.length())+minute.substring(minute.length()-2,minute.length())+second.substring(second.length()-2,second.length());
              return  strtime;

     }
     public static String gethour(String tmp)
    {
              java.util.Calendar cal=java.util.Calendar.getInstance();
              String  hour="00"+inttostring(cal.get(cal.HOUR));

               return hour.substring(hour.length()-2,hour.length());
     }
     public static String getminute(String tmp)
    {
              java.util.Calendar cal=java.util.Calendar.getInstance();
              String  minute="00"+cal.get(cal.MINUTE);
              return minute.substring(minute.length()-2,minute.length());
     }
     public static String getsecond(String tmp)
    {
              java.util.Calendar cal=java.util.Calendar.getInstance();
              String  second="00"+cal.get(cal.SECOND);

               return second.substring(second.length()-2,second.length());
     }

     public static void  log(String serverpath,String msg,String clientip)
     {
         /*
         if (serverpath==null)
         {
         serverpath="";
         }
         if (clientip==null)
         {
         clientip="";
         }
         if (serverpath.trim().length()==0)
         {
             serverpath="d:\\";
         }
         if (clientip.trim().length()==0)
         {
             clientip="";
         }
     java.util.Date mydate=new java.util.Date();
     String strdate=com.sinosoft.lis.ebaprt.endorprt.Stringtools.datetostr(mydate);
     try{
      PrintWriter diary = new PrintWriter(new FileWriter(serverpath+strdate+".txt", true), true);
      diary.println(new Date() + ": "+clientip+":" + msg);
      diary.close();
     }catch(java.io.IOException exio){
     logger.debug("写日志错误！！/Write diary error!!"+exio.getMessage());
     }
         */
     }
   public static String longtoday()
   {
                            Calendar my=Calendar.getInstance();
                             int myyear=my.get(Calendar.YEAR);
                             int mymonth=my.get(Calendar.MONTH)+1;
                             int myday=my.get(Calendar.DAY_OF_MONTH);
                             String str=inttostring(myyear)+"年"+inttostring(mymonth)+"月"+inttostring(myday)+"日";
       return str;
   }
   public static String longtoday(String split)
   {
                            Calendar my=Calendar.getInstance();
                             int myyear=my.get(Calendar.YEAR);
                             int mymonth=my.get(Calendar.MONTH)+1;
                             int myday=my.get(Calendar.DAY_OF_MONTH);
                             String str=inttostring(myyear)+split+inttostring(mymonth)+split+inttostring(myday);
       return str;
   }
   //分隔符字符串分解                       源串,   分隔符，   取第几个
  public static String StringSplit(String str,String str1,int num)
  {
      String str2="";
      StringTokenizer parameter = new StringTokenizer(str,str1);
      int count=parameter.countTokens();
      String[] parameters=new String[count];
      int i=0;
      while(parameter.hasMoreTokens())
              {
                  parameters[i]=parameter.nextToken().trim();
                  if (parameters[i]==null)
                  {
                  parameters[i]="";
                  }
                  i++;
              }
       str2=parameters[num-1];
       return str2;
  }
  public static String toUtf8String(String s)
  {
      StringBuffer sb = new StringBuffer();
      for (int i=0;i<s.length();i++)
      {
          char c = s.charAt(i);
          if (c >= 0 && c <= 255)
          {
              sb.append(c);
          }
          else
          {
              byte[] b;
              try {
                  b = Character.toString(c).getBytes("utf-8");
              } catch (Exception ex) {
                  logger.debug(ex);
                  b = new byte[0];
              }
              for (int j = 0; j < b.length; j++) {
                  int k = b[j];
                  if (k < 0) k += 256;
                  sb.append("%" + Integer.toHexString(k).
                            toUpperCase());
              }
          }
      }
      return sb.toString();
  }

}
