

package com.sinosoft.productdef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.f1j.ss.BookModelImpl;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;

/**
 * 把导入的精算数据Excel文件解析成XML文件
 * 对应XML模板文件为：JSDataImport.xml
 * @author niuzj,2007-03-28
 *
 */

public class PDDataParserXML 
{
    public CErrors mErrors = new CErrors();

    private String m_strBatchNo = "";

    //保存磁盘投保文件的文件名
    private String m_strFileName = "";
    private String m_strPathName = "";

    //保存列名元素名映射的模板文件的文件名
    private String m_strConfigFileName = "";
    
    //保存Sheet相关的信息，如最大行，最大列，当前处理到的行等
    private Hashtable[] m_hashInfo = null;

    //常量定义
    private static String PROP_MAX_ROW = "max_row";
    private static String PROP_MAX_COL = "max_col";
    private static String PROP_CUR_ROW = "cur_row";
    private static String PROP_COL_NAME = "col_name";

    //excel文件的sheet数量
    private static int SHEET_COUNT = 1;
    
    // sheet序列号,以及索引所在的列号
    private int SHEET_1 = 0;
    private int STRID_COL_SHEET1 = 0;
    private int SHEET_2 = 1;
    private int STRID_COL_SHEET2 = 0;
    private int SHEET_3 = 2;
    private int STRID_COL_SHEET3 = 0;
    private int SHEET_4 = 3;
    private int STRID_COL_SHEET4 = 0;
    
    //一个文件中处理的行数
    private static int ROW_LIMIT = 1000;
    private int contIndex = 0;

    private BookModelImpl m_book = new BookModelImpl();

    //一个excel文件可能被解析成多个XML文件
    //用来保存每一个小部分生成的XML文件名
    private List m_listFiles = new ArrayList();


    /**
     * 构造函数
     *
     */
    public PDDataParserXML(int sheetCount)
    {
    	SHEET_COUNT = sheetCount;
    	
        //根据Excel文件的Sheet的个数，生成一个哈希表
        m_hashInfo = new Hashtable[SHEET_COUNT];

        for (int i = 0; i < SHEET_COUNT; i++)
        {
            m_hashInfo[i] = new Hashtable();
            m_hashInfo[i].put(PROP_CUR_ROW, "1");
            // 从第一行开始解析
        }
    }
    
    /**
     * 将磁盘投保文件转换成指定格式的XML文件
     * @return boolean
     */
    public boolean transform()
    {
        String strFileName = "";
        int nCount = 0;

        try
        {
        	int nCurRow = getPropNumber(0, PROP_CUR_ROW);
            int nMaxRow = getPropNumber(0, PROP_MAX_ROW);
            System.out.println("=befor==verify== nCurRow ==" + nCurRow);
            System.out.println("=befor==verify== nMaxRow ==" + nMaxRow);
            
        	//转换Excel文件
            verify();
            
            int nCurRow2 = getPropNumber(0, PROP_CUR_ROW);
            int nMaxRow2 = getPropNumber(0, PROP_MAX_ROW);
            System.out.println("=after==verify== nCurRow ==" + nCurRow2);
            System.out.println("=after==verify== nMaxRow ==" + nMaxRow2);
            
            //判断转换是否完毕
//            while (hasMoreData())
//            {
//                strFileName = m_strPathName + File.separator + m_strBatchNo + String.valueOf(nCount++) + ".xml";
//                System.out.println("-----准备生成的Xml文件名:" + strFileName);
//                genPart(strFileName);
//
//                //用来保存每一个小部分生成的XML文件名
//                m_listFiles.add(strFileName);
//            }
            
            strFileName = m_strPathName + File.separator + m_strBatchNo + ".xml";
            genPart(strFileName);
            m_listFiles.add(strFileName);
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

        ele = new Element("SHEET1");
        root.addContent(ele);
        genXMLPart(ele, this.SHEET_1);
        
//        ele = new Element("SHEET2");
//        root.addContent(ele);
//        genXMLPart(ele, this.SHEET_2);
//        
//        ele = new Element("SHEET3");
//        root.addContent(ele);
//        genXMLPart(ele, this.SHEET_3);
//        
//        ele = new Element("SHEET4");
//        root.addContent(ele);
//        genXMLPart(ele, this.SHEET_4);
        
        XMLOutputter xo = new XMLOutputter();
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
           // strID = m_book.getText(sheetNo, nRow, this.STRID_COL_LFJSDATE);
           // System.out.println("strID=="+strID);
            //导入模板中的信息
            Element eleRow = new Element("ROW");
            eleParent.addContent(eleRow);

            for (int nCol = 0; nCol < nMaxCol; nCol++) 
            {
               Element ele = new Element(strColName[nCol]);
               ele.setText(m_book.getText(sheetNo, nRow, nCol));
               eleRow.addContent(ele);
            }
        }
        setPropNumber(sheetNo, PROP_CUR_ROW, nRow);
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
     * 判断是否还有数据没有处理
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
        if (m_book.getNumSheets() < SHEET_COUNT)
        {
            throw new Exception("磁盘投保文件不完整，缺少Sheet。");
        }
        
        int nMaxID = -1;
        int nID = -1;
        String strID = "";
        // 检查数据是否是按“唯一号”排序的
//        for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++)
//        {
//
//            nMaxID = -1;
//
//            for (int nRow = 1; nRow < getMaxRow(nIndex); nRow++)
//            {
//                strID = m_book.getText(nIndex, nRow, 0);
//                nID = Integer.parseInt(strID);
//                if (nID > nMaxID)
//                {
//                    nMaxID = nID;
//                }
//                else if (nID == nMaxID)
//                {
//                    // do nothing
//                }
//                else
//                {
//                    throw new Exception("投保文件中的数据不是按照唯一号从小到大排序的");
//                }
//            }
//        }

        // 将第一行中元素的值设为对应的XML元素的名字
        // 如在Sheet0中，每行的第一列对应的XML元素名字为ID。
        
    	org.w3c.dom.Document document = null; 
		 try 
	       { 
	            DocumentBuilderFactory   factory = DocumentBuilderFactory.newInstance();    
	            DocumentBuilder builder=factory.newDocumentBuilder();    
	            document=builder.parse(new FileInputStream(m_strConfigFileName));    
	            document.normalize(); 
	       } 
	      catch (Exception ex){ 
	           ex.printStackTrace(); 
	       }  
	      Document  doc=new DOMBuilder().build(document);
        
//        DOMBuilder db = new DOMBuilder();
//        Document doc = db.build(new FileInputStream(m_strConfigFileName));
        Element eleRoot = doc.getRootElement();
        Element ele = null;
        String strColName = "";

        for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++)
        {
            String sheetId = "Sheet" + String.valueOf(nIndex + 1);
            System.out.println("正在生成的Sheet页是=="+sheetId);
            try
            {
                ele = eleRoot.getChild(sheetId);
                int nMaxCol = getMaxCol(nIndex);
                String[] strArr = new String[nMaxCol];
                //读取列名映射模板文件中的列名
                for (int nCol = 0; nCol < nMaxCol; nCol++)
                {
                    //strColName = ele.getChildText("COL" + String.valueOf(nCol));
                	strColName = ele.getChildText("COL" + String.valueOf(nCol));
                    strArr[nCol] = strColName;
                }

                setPropArray(nIndex, PROP_COL_NAME, strArr);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();

                throw new Exception("找不到对应的配置信息，Sheet" + String.valueOf(nIndex + 1));
            }
        }
    }
    
    
    /**
     * 设置要解析的文件名
     */
    public boolean setFileName(String pstrFileName)
    {
        File file = new File(pstrFileName);
        if (!file.exists())
        {
            buildError("setFileName", "指定的文件["+pstrFileName+"]不存在！");
            return false;
        }

        m_strFileName = pstrFileName;
        m_strPathName = file.getParent();

        int nPos = pstrFileName.lastIndexOf('.');

        if (nPos == -1)
        {
            nPos = pstrFileName.length();
        }

        m_strBatchNo = pstrFileName.substring(pstrFileName.lastIndexOf("/") + 1, nPos);

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
    public boolean setConfigFileName(String pstrConfigFileName)
    {
        File file = new File(pstrConfigFileName);
        if (!file.exists())
        {
            buildError("setFileName", "指定的配置文件["+pstrConfigFileName+"]不存在！");
            return false;
        }

        m_strConfigFileName = pstrConfigFileName;
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
    private int getPropNumber(int nSheetIndex,String strPropName)
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
    private void setPropNumber(int nSheetIndex,String strPropName,int nValue)
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
    private void setPropArray(int nSheetIndex,String strPropName,String[] strArr)
    {
        Hashtable hash = m_hashInfo[nSheetIndex];
        hash.put(strPropName, strArr);
    }

    /**
     * 创建错误信息
     * @param szFunc String 函数名称
     * @param szErrMsg String 错误信息
     */
    private void buildError(String szFunc,String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "GrpCustomerVTSParser";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
