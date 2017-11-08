package com.sinosoft.print.func;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * 动态生成临时vts文件
 * <p>Description: </p>
 * 用来存储打印数据流信息
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: SINOSOFT</p>
 * @author 朱向峰
 * @version 1.0
 */
public class PrintQueue
{
private static Logger logger = Logger.getLogger(PrintQueue.class);

    private static int nHead = 0;
    private static int nTotal = 0;

    static
    {
        nTotal = 10;

//        String tFilePath = "/tempfile/";

//        String tRealPath = "c:/";
    }

    public PrintQueue()
    {
    }

    public static synchronized int getFileName()
    {
        int nTHead = nHead;
        if (nHead >= (nTotal - 1))
        {
            nHead = 0;
        }
        else
        {
            nHead++;
        }
        return nTHead;
    }
}
