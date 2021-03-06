package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.f1j.ss.BookModelImpl;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;


public class LCTempCustomerVTSParser
{
private static Logger logger = Logger.getLogger(LCTempCustomerVTSParser.class);

    public CErrors mErrors = new CErrors();

    private String m_strBatchNo = "";

    // 保存磁盘投保文件的文件名
    private String m_strFileName = "";
    private String m_strPathName = "";

    // 保存列名元素名映射的模板文件的文件名
    private String m_strConfigFileName = "";

    // 保存Sheet相关的信息，如最大行，最大列，当前处理到的行等
    private Hashtable[] m_hashInfo = null;

    // 常量定义
    private static String PROP_MAX_ROW = "max_row";
    private static String PROP_MAX_COL = "max_col";
    private static String PROP_CUR_ROW = "cur_row";
    private static String PROP_COL_NAME = "col_name";

    // excel文件的sheet数量
    private static int SHEET_COUNT = 1;

    // sheet序列号,以及索引所在的列号
    private int SHEET_LLCASE = 0;
    private int STRID_COL_LLCASE = 0;
    // 一个文件中处理的行数
    private static int ROW_LIMIT = 1000000;
    private BookModelImpl m_book = new BookModelImpl();

    // 一个excel文件可能被解析成多个XML文件
    // 用来保存每一个小部分生成的XML文件名
    private List m_listFiles = new ArrayList();


    public LCTempCustomerVTSParser()
    {
        m_hashInfo = new Hashtable[SHEET_COUNT];

        for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++)
        {
            m_hashInfo[nIndex] = new Hashtable();
            m_hashInfo[nIndex].put(PROP_CUR_ROW, "1");
            // 从第一行开始解析
        }

    }

    /**
     *  将磁盘投保文件转换成指定格式的XML文件。
     * @return boolean
     */
    public boolean transform()
    {

        String strFileName = "";
        int nCount = 0;

        try
        {
            verify();

            while (hasMoreData())
            {
                strFileName = m_strPathName + File.separator
                              + m_strBatchNo + String.valueOf(nCount++)
                              + ".xml";
                logger.debug("strFileName:" + strFileName);
                genPart(strFileName);

                m_listFiles.add(strFileName);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("transfrom", ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 执行一次生成，将ROW_LIMIT行数的VTS数据生成一个Document
     * @param strFileName String
     * @throws Exception
     */
    private void genPart(String strFileName) throws Exception
    {
        Element root = new Element("DATASET");
        Document doc = new Document(root);

        Element ele = new Element("BATCHID");
        ele.setText(m_strBatchNo);
        root.addContent(ele);

        ele = new Element("LLCASETABLE");
        root.addContent(ele);
        genXMLPart(ele, this.SHEET_LLCASE);

        ele = new Element("LLFEEMAINTABLE");
        root.addContent(ele);
        //genXMLPart(ele, this.SHEET_LLFEEMAIN);

        XMLOutputter xo = new XMLOutputter("  ", true, "GB2312");
        xo.output(doc, new FileOutputStream(strFileName));
    }

    /**
     * 生成xml主体
     * @param eleParent Element
     * @param sheetNo int
     */
    private void genXMLPart(Element eleParent,
                            int sheetNo) throws Exception
    {

        // 保单信息存放在第一个Sheet中。
        logger.debug("sheetNo:"+sheetNo);
        logger.debug("PROP_CUR_ROW:"+PROP_CUR_ROW);
        int nCurRow = getPropNumber(sheetNo, PROP_CUR_ROW);
        int nMaxRow = getMaxRow(sheetNo);
        int nMaxCol = getMaxCol(sheetNo);
        //获取保存的列名数组
        String[] strColName = getPropArray(sheetNo, PROP_COL_NAME);

        String strID = "";

        int nRow = 0;
        int nCount = 0;

        for (nRow = nCurRow, nCount = 0; nRow < nMaxRow; nRow++)
        {

            strID = m_book.getText(sheetNo, nRow, this.STRID_COL_LLCASE);
            // 案件信息
            Element eleRow = new Element("ROW");
            eleParent.addContent(eleRow);
            for (int nCol = 0; nCol < nMaxCol; nCol++)
            {
                logger.debug("===============");
                logger.debug("Will You Be There "+strColName[nCol]);
            }
            for (int nCol = 0; nCol < nMaxCol; nCol++)
            {
                Element ele = new Element(strColName[nCol]);
                ele.setText(m_book.getText(sheetNo, nRow, nCol));
                eleRow.addContent(ele);
            }
//               //增加事件子表
//               Element eleSub = new Element("LLSUBREPORTTABLE");
//               eleRow.addContent(eleSub);
//               genPartSubTable(eleSub, this.SHEET_LLSUBREPORT,
//                               strID, this.STRID_COL_LLSUBREPORT);
//               //增加申请材料子表
//               Element eleAffix = new Element("LLAFFIXTABLE");
//               eleRow.addContent(eleAffix);
//               genPartSubTable(eleAffix, this.SHEET_LLAFFIX,
//                               strID, this.STRID_COL_LLAFFIX);
            //增加账单子表
//               Element eleFeeMain = new Element("LLFEEMAINTABLE");
//               eleRow.addContent(eleFeeMain);
//               genPartSubTable(eleFeeMain, this.SHEET_LLFEEMAIN,
//                               strID, this.STRID_COL_LLFEEMAIN);

        }
        setPropNumber(sheetNo, PROP_CUR_ROW, nRow);
    }

    /**
     * 处理子表
     * 因为在Sheet的数据中，是按照“唯一号”ID排序的，所以对子表的数据，
     * 我们也只需要扫描一次就可以了。
     * @param eleParent       父元素
     * @param nSheetIndex     sheet序列号
     * @param strID           合同号
     * @param strIDColIndex   索引列号-合同号所在列号
     * @throws Exception
     */
    private void genPartSubTable(Element eleParent,
                                 int nSheetIndex,
                                 String strID,
                                 int strIDColIndex) throws Exception
    {
        int nCurRow = getPropNumber(nSheetIndex, PROP_CUR_ROW);
        int nMaxRow = getMaxRow(nSheetIndex);
        int nMaxCol = getMaxCol(nSheetIndex);
        //获取保存的列名数组
        String[] strColName = getPropArray(nSheetIndex, PROP_COL_NAME);
        int nRow = 0;
        for (nRow = nCurRow; nRow < nMaxRow; nRow++)
        {
            //不是同一案件跳出
            if (!strID.equals(m_book.getText(nSheetIndex,
                                             nRow, strIDColIndex)))
            {
                break;
            }

            Element eleRow = new Element("ROW");
            eleParent.addContent(eleRow);

            for (int nCol = 0; nCol < nMaxCol; nCol++)
            {
                Element ele = new Element(strColName[nCol]);
                ele.setText(m_book.getText(nSheetIndex, nRow, nCol));
                eleRow.addContent(ele);
            }
            //如果是账单主表则增加账单项目明细子表
//            if(nSheetIndex==3)
//            {
//              //增加账单明细子表
//              Element eleCaseReceipt = new Element("LLCASERECEIPT");
//              eleRow.addContent(eleCaseReceipt);
//              String feeMainID = m_book.getText(nSheetIndex, nRow, 0);
//              genPartSubTable(eleCaseReceipt, this.SHEET_LLCASERECEIPT,
//                              feeMainID, this.STRID_COL_LLCASERECEIPT);
//
//            }




//            //险种保单页签
//            if (nSheetIndex == this.SHEET_LCPOL)
//            {
//                // 取得唯一号信息 - 险种ID
//                String polId = m_book.getText(nSheetIndex, nRow,
//                                              this.STRID_POL);
//
//                // 责任信息挂在险种保单下边
//                Element eleDuty = new Element("SUBDUTYTABLE");
//                eleRow.removeChild("SUBDUTYTABLE");
//                eleRow.addContent(eleDuty);
//
//                genPartSubTable(eleDuty, this.SHEET_DUTY, polId,
//                                this.STRID_COL_DUTY);
//            }
        } //end for

        setPropNumber(nSheetIndex, PROP_CUR_ROW, nRow);
    }

    /**
     * 获取指定sheet相关参数数组
     * @param nSheetIndex int
     * @param strPropName String
     * @return String[]
     */
    private String[] getPropArray(int nSheetIndex,
                                  String strPropName)
    {
        Hashtable hash = m_hashInfo[nSheetIndex];
        String[] strArr = (String[]) hash.get(strPropName);
        return strArr;
    }

    /**
     * 判断是否还有数据没有处理-以险种保单sheet为准
     * 如果在存放保单信息的Sheet中，已经处理到了最大行，返回false；
     * 否则，返回true;
     * @return
     */
    private boolean hasMoreData()
    {
        int nCurRow = getPropNumber(0, PROP_CUR_ROW);
        int nMaxRow = getPropNumber(0, PROP_MAX_ROW);

        if (nCurRow >= nMaxRow)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    /**
     * 校验磁盘投保excel文件格式
     * 要求磁盘投保文件中的数据按照唯一标识，从小到大排列
     */
    private void verify() throws Exception
    {
        if (m_strFileName.equals(""))
        {
            throw new Exception("请先调用setFileName函数设置要处理的文件名。");
        }

        m_book.read(m_strFileName, new com.f1j.ss.ReadParams());
        logger.debug("m_book.getNumSheets():" + m_book.getNumSheets());
        if (m_book.getNumSheets() < SHEET_COUNT)
        {
            throw new Exception("磁盘投保文件不完整，缺少Sheet。");
        }
        int nMaxID = -1;
        int nID = -1;
        String strID = "";
        // 检查数据是否是按“唯一号”排序的
        for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++)
        {

            nMaxID = -1;

            for (int nRow = 1; nRow < getMaxRow(nIndex); nRow++)
            {
                strID = m_book.getText(nIndex, nRow, 0);
                nID = Integer.parseInt(strID);
                if (nID > nMaxID)
                {
                    nMaxID = nID;
                }
                else if (nID == nMaxID)
                {
                    // do nothing
                }
                else
                {
                    throw new Exception("投保文件中的数据不是按照唯一号从小到大排序的");
                }
            }
        }

        // 将第一行中元素的值设为对应的XML元素的名字
        // 如在Sheet0中，每行的第一列对应的XML元素名字为ID。
        DOMBuilder db = new DOMBuilder();
        Document doc = db.build(new FileInputStream(m_strConfigFileName));
        Element eleRoot = doc.getRootElement();
        Element ele = null;
        String strColName = "";

        for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++)
        {
            String sheetId = "Sheet" + String.valueOf(nIndex + 1);
            try
            {
                ele = eleRoot.getChild(sheetId);
                int nMaxCol = getMaxCol(nIndex);
                String[] strArr = new String[nMaxCol];
                //读取列名映射模板文件中的列名
                for (int nCol = 0; nCol < nMaxCol; nCol++)
                {
                    strColName = ele.getChildText("COL" + String.valueOf(nCol));
                    strArr[nCol] = strColName;
                }

                setPropArray(nIndex, PROP_COL_NAME, strArr);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();

                throw new Exception("找不到对应的配置信息，Sheet" +
                                    String.valueOf(nIndex + 1));
            }

        }
    }

    /**
     * 设置要解析的文件名
     */
    public boolean setFileName(String strFileName)
    {
        File file = new File(strFileName);
        if (!file.exists())
        {
            buildError("setFileName", "指定的文件不存在！");
            return false;
        }

        m_strFileName = strFileName;
        m_strPathName = file.getParent();

        int nPos = strFileName.lastIndexOf('.');

        if (nPos == -1)
        {
            nPos = strFileName.length();
        }

        m_strBatchNo = strFileName.substring(
                strFileName.lastIndexOf("/") + 1, nPos);

        nPos = m_strBatchNo.lastIndexOf('\\');

        if (nPos != -1)
        {
            m_strBatchNo = m_strBatchNo.substring(nPos + 1);
        }

        nPos = m_strBatchNo.lastIndexOf('/');

        if (nPos != -1)
        {
            m_strBatchNo = m_strBatchNo.substring(nPos + 1);
        }

        return true;
    }

    /**
     * 设置列名映射模板文件名
     */
    public boolean setConfigFileName(String strConfigFileName)
    {
        File file = new File(strConfigFileName);
        if (!file.exists())
        {
            buildError("setFileName", "指定的文件不存在！");
            return false;
        }

        m_strConfigFileName = strConfigFileName;
        return true;
    }

    /**
     * 取得生成的XML文件名列表
     */
    public String[] getDataFiles()
    {
        String[] files = new String[m_listFiles.size()];

        for (int nIndex = 0; nIndex < m_listFiles.size(); nIndex++)
        {
            files[nIndex] = (String) m_listFiles.get(nIndex);
        }

        return files;
    }


    /**
     * 获取指定sheet的最大行数
     * @param nSheetIndex int
     */
    private int getMaxRow(int nSheetIndex) throws Exception
    {
        String str = "";
        int nMaxRow = getPropNumber(nSheetIndex, PROP_MAX_ROW);

        if (nMaxRow == -1)
        {
            for (nMaxRow = 0; nMaxRow < m_book.getMaxRow(); nMaxRow++)
            {
                str = m_book.getText(nSheetIndex, nMaxRow, 0);
                if (str == null || str.trim().equals(""))
                {
                    break;
                }
            }
            setPropNumber(nSheetIndex, PROP_MAX_ROW, nMaxRow);
        }

        return nMaxRow;
    }

    /**
     * 获取指定sheet的最大列数
     * @param nSheetIndex int
     */
    private int getMaxCol(int nSheetIndex) throws Exception
    {
        String str = "";
        int nMaxCol = getPropNumber(nSheetIndex, PROP_MAX_COL);

        if (nMaxCol == -1)
        {
            for (nMaxCol = 0; nMaxCol < m_book.getMaxRow(); nMaxCol++)
            {
                str = m_book.getText(nSheetIndex, 0, nMaxCol);
                if (str == null || str.trim().equals(""))
                {
                    break;
                }
            }

            setPropNumber(nSheetIndex, PROP_MAX_COL, nMaxCol);
        }
        return nMaxCol;
    }

    /**
     * 获取指定sheet相关参数
     * @param nSheetIndex int
     * @param strPropName String
     */
    private int getPropNumber(int nSheetIndex,
                              String strPropName)
    {
        Hashtable hash = m_hashInfo[nSheetIndex];
        String str = (String) hash.get(strPropName);
        if (str != null && !str.equals(""))
        {
            return Integer.parseInt(str);
        }
        else
        {
            return -1;
        }
    }

    /**
     * 设置指定sheet相关参数
     * @param nSheetIndex int
     * @param strPropName String
     * @param nValue int
     */
    private void setPropNumber(int nSheetIndex,
                               String strPropName,
                               int nValue)
    {
        Hashtable hash = m_hashInfo[nSheetIndex];
        hash.put(strPropName, String.valueOf(nValue));
    }

    /**
     * 设置指定sheet相关参数
     * @param nSheetIndex int
     * @param strPropName String
     * @param strArr String[]
     */
    private void setPropArray(int nSheetIndex,
                              String strPropName,
                              String[] strArr)
    {
        Hashtable hash = m_hashInfo[nSheetIndex];
        hash.put(strPropName, strArr);
    }

    /**
     * 创建错误信息
     * @param szFunc String 函数名称
     * @param szErrMsg String 错误信息
     */
    private void buildError(String szFunc,
                            String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "GrpCustomerVTSParser";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
