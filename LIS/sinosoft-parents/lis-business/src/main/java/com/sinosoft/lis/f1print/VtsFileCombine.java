package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

import com.f1j.ss.BookModelImpl;
import com.f1j.ss.Constants;
import com.f1j.ss.FindReplaceInfo;
import com.f1j.ss.GRObject;
import com.f1j.ss.GRObjectPos;
import com.f1j.ss.RangeRef;
import com.f1j.ss.ReadParams;
import com.f1j.ss.WriteParams;
import com.f1j.util.F1Exception;

/**
 * VtsFileCombine vts文件合并
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: vts数据文件合并 vts模板文件合并
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author chenxy
 * @version 1.0
 * 
 * *修改记录** 20050819 by chenxy 根据addPagebreak标志添加分页符
 */

public class VtsFileCombine {
private static Logger logger = Logger.getLogger(VtsFileCombine.class);
	// true则添加分页符
	private boolean addPagebreak;

	// 默认为true，即添加分页符
	public VtsFileCombine() {
		addPagebreak = true;
	}

	// true则添加分页符
	public VtsFileCombine(boolean addPagebreak) {
		this.addPagebreak = addPagebreak;
	}

	/**
	 * 执行模板vts合并操作 将b1的内容合并到b2中,通过查找取得b2 b1的结束标识符"$/",并取得它们的坐标.
	 * 然后利用这两个坐标进行定位,执行区域复制.由于无法确定模板中内容的范围,所以只能是自定义一个比模板
	 * 大得多的搜索范围.如果合并的模板过多,可能需要更改这个搜索范围.
	 * 
	 * @param b1
	 *            BookModelImpl 合并到b2的内容
	 * @param b2
	 *            BookModelImpl
	 * @return BookModelImpl 合并后的vts
	 * @throws F1Exception
	 * @throws IOException
	 */
	public BookModelImpl templateCombine(BookModelImpl b1, BookModelImpl b2)
			throws F1Exception, IOException {
		int searcheRows = 500;
		int copyCols = 100;
		FindReplaceInfo friB2 = b2.findFirst(0, 0, searcheRows, 0, "$/",
				FindReplaceInfo.kByRows | FindReplaceInfo.kInValues);
		FindReplaceInfo friB1 = b1.findFirst(0, 0, searcheRows, 0, "$/",
				FindReplaceInfo.kByRows | FindReplaceInfo.kInValues);

		/*
		 * 将b1的内容(不包括图像,因为区域复制对图像无效,需对图像特殊处理,模板中的处理与数据中的处理又 不同)追加到b2的内容之后
		 */
		b2.copyRange(friB2.getRow(), friB2.getCol(), friB2.getRow()
				+ friB1.getRow(), copyCols, b1, 0, 0, friB1.getRow(), copyCols);
		for (int k = 1; k <= 10; k++) {
			GRObject gr = b1.getObject("BarCode" + k);
			if (gr != null) {
				GRObjectPos pos = gr.getPos();
				b2.addPicture(pos.getX1(), pos.getY1() + friB2.getRow(), pos
						.getX2(), pos.getY2() + friB2.getRow(), gr.getImage());
			}
		}
		b2.objectBringToFront();

		return b2;
	};

	/**
	 * 执行模板vts合并操作 将b1的内容合并到b2中 以vts路径为参数
	 * 
	 * @param path1
	 *            String 合并到b2的内容的路径
	 * @param path2
	 *            String b2的路径
	 * @return BookModelImpl 合并后的vts
	 * @throws F1Exception
	 * @throws IOException
	 */
	public BookModelImpl templateCombine(String path1, String path2)
			throws F1Exception, IOException {
		BookModelImpl b1 = null;
		BookModelImpl b2 = null;
		try {
			b1 = new BookModelImpl();
			b1.read(path1, new ReadParams());
			b2 = new BookModelImpl();
			b2.read(path2, new ReadParams());

			return dataCombine(b1, b2);

		} catch (F1Exception f1ex) {
			f1ex.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b2;

	};

	/**
	 * 将BookModelImpl对象的内容存为vts文件
	 * 
	 * @param b
	 *            BookModelImpl vts文件
	 * @param path
	 *            String vts文件存储路径
	 * @throws F1Exception
	 * @throws IOException
	 */
	public void write(BookModelImpl b, String path) throws F1Exception,
			IOException {
		b.write(path, new WriteParams(Constants.eFileCurrentFormat));
	}

	/**
	 * 将BookModelImpl对象的内容希写入数据输出流
	 * 
	 * @param b
	 *            BookModelImpl vts文件
	 * @param out
	 *            OutputStream 输出流
	 * @throws F1Exception
	 * @throws IOException
	 */
	public void write(BookModelImpl b, OutputStream out) throws F1Exception,
			IOException {
		b.write(out, new WriteParams(Constants.eFileCurrentFormat));
	}

	/**
	 * 执行两个数据vts合并操作,两个vts之间加了一分页符 通过捕获各个vts的选区,达到定位的目的,然后执行区域复制
	 * 
	 * @param tb1
	 *            BookModelImpl 合并到tb2的数据vts
	 * @param tb2
	 *            BookModelImpl
	 * @return BookModelImpl 合并后的vts
	 * 
	 */
	public BookModelImpl dataCombine(BookModelImpl tb1, BookModelImpl tb2) {
		try {
			RangeRef rangeRef1 = tb1.formulaToRangeRef(tb1.getSelection());
			RangeRef rangeRef2 = tb2.formulaToRangeRef(tb2.getSelection());
			int colWidth = rangeRef1.getCol2() > rangeRef2.getCol2() ? rangeRef1
					.getCol2()
					: rangeRef2.getCol2();
			// colWidth = 200;
			logger.debug(rangeRef1.getRow2() + ":" + rangeRef1.getCol2()
					+ "copy to:" + rangeRef2.getRow2() + ":"
					+ rangeRef2.getCol2());
			// logger.debug(rangeRef2.getCol2());
			// logger.debug(colWidth);
			if (addPagebreak) {
				tb2.addRowPageBreak(rangeRef2.getRow2() + 1);
			}
			/* 将tb1的内容(不包括图像,因为区域复制对图像无效,需对图像特殊处理)追加到tb2的内容之后 */
			tb2.copyRange(rangeRef2.getRow2() + 2, 0, rangeRef2.getRow2() + 2
					+ rangeRef1.getRow2(), colWidth, tb1, 0, 0, rangeRef1
					.getRow2(), colWidth);
			// 对图像特殊处理(F1PrintParser 只是针对文字和条形码进行处理,同时由于GRObject)
			// 因为无法得到vts中GRObject的个数,所以目前限制为1000个
			// for (int k = 0; k <= 1000; k++) {
			for (int k = 0; k <= 50; k++) {
				// GRObject gr = tb1.getObject("BarCode" + k);
				GRObject gr = tb1.getObject(k);
				// logger.debug(gr);
				if (gr != null) {
					GRObjectPos pos = gr.getPos();
					// tb2.setActiveCell(rangeRef2.getRow2() + 2, 0);
					try {
						tb2.addPicture(pos.getX1(), pos.getY1()
								+ rangeRef2.getRow2() + 2, pos.getX2(), pos
								.getY2()
								+ rangeRef2.getRow2() + 2, gr.getImage());
					} catch (Exception f) {
						tb2.addObject(gr.getType(), pos.getX1(), pos.getY1()
								+ rangeRef2.getRow2() + 2, pos.getX2(), pos
								.getY2()
								+ rangeRef2.getRow2() + 2);
					}
				}
			}
			tb2.objectBringToFront();

			tb2.setSelection(0, 0, rangeRef2.getRow2() + 2
					+ rangeRef1.getRow2(), colWidth);
			tb2.setPrintArea();
			tb2.saveViewInfo();

			//System.gc();
		} catch (F1Exception f1ex) {
			f1ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tb2;
	}

	/**
	 * 执行两个数据vts合并操作,以vts的路径作为参数
	 * 
	 * @param path1
	 *            String 合并到tb2的数据vts
	 * @param path2
	 *            String
	 * @return BookModelImpl 合并后的vts
	 */
	public BookModelImpl dataCombine(String path1, String path2) {
		BookModelImpl tb1 = null;
		BookModelImpl tb2 = null;
		try {
			tb1 = new BookModelImpl();
			tb1.read(path1, new ReadParams());
			tb2 = new BookModelImpl();
			tb2.read(path2, new ReadParams());

			dataCombine(tb1, tb2);

			tb1.destroy();
			// return tb2;

		} catch (F1Exception f1ex) {
			f1ex.printStackTrace();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tb2;
	}

	/**
	 * 执行多个数据vts合并操作 以vts的路径为参数
	 * 
	 * @param paths
	 *            String[]
	 * @return BookModelImpl
	 */
	// public BookModelImpl dataCombine(String[] paths) {
	// int len = paths.length;
	// BookModelImpl[] tbs = new BookModelImpl[len];
	// try{
	// for (int i = 0; i < len; i++) {
	// tbs[i] = new BookModelImpl();
	// tbs[i].read(paths[i], new ReadParams());
	// }
	// }catch(Exception ex){ex.printStackTrace();}
	//
	// return dataCombine(tbs);
	// }
	public BookModelImpl dataCombine(String[] paths) {
		int len = paths.length;
		BookModelImpl front = new BookModelImpl();
		BookModelImpl end; // = new BookModelImpl();

		try {
			front.read(paths[0], new ReadParams());
			for (int i = 1; i < len; i++) {
				logger.debug(i + " read..." + paths[i]);
				end = new BookModelImpl();
				end.read(paths[i], new ReadParams());
				front = dataCombine(end, front); // 是把第一个参数合并到第二个。
				end.destroy();
				logger.debug("sucess combine");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return front;
	}

	/**
	 * 执行多个数据vts合并操作 将tbs中的元素(tb[i],i>0)逐个追加到tb[0]中.
	 * 
	 * @param tbs
	 *            BookModelImpl[]
	 * @return BookModelImpl
	 */
	public BookModelImpl dataCombine(BookModelImpl[] tbs) {
		BookModelImpl tb = null;
		tb = tbs[0];
		int len = tbs.length;
		for (int i = 1; i < len; i++) {
			if (tbs[i] != null) {
				tb = dataCombine(tbs[i], tb);
			}
		}
		return tb;
	}

	public static void main(String[] args) {
		try {
			VtsFileCombine vtsfilecombine = new VtsFileCombine();
			BookModelImpl tb = null;

			// BookModelImpl tb1 = new BookModelImpl();
			// tb1.read("f:/try1.vts", new ReadParams());
			// BookModelImpl tb2 = new BookModelImpl();
			// tb2.read("f:/try2.vts", new ReadParams());
			// BookModelImpl tb3 = new BookModelImpl();
			// tb3.read("f:/try3.vts", new ReadParams());
			// BookModelImpl[] tbs = {tb1,tb2,tb3};

			String[] paths = { "f:/try1.vts", "f:/try2.vts", "f:/try3.vts" };

			tb = vtsfilecombine.dataCombine(paths);
			// tb = vtsfilecombine.dataCombine("f:/try3.vts",
			// "f:/new.vts");
			// BookModelImpl template = vtsfilecombine.templateCombine(tb1,
			// tb2);
			vtsfilecombine.write(tb, "f:/new2.vts");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (F1Exception f1e) {
			f1e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
