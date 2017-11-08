package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class MultiComparator implements Comparator {
private static Logger logger = Logger.getLogger(MultiComparator.class);
	Vector cols = new Vector();
	Vector orders = new Vector();

	public MultiComparator(int colindex, boolean isasc) {
		addComparator(colindex, isasc);
	}

	public void addComparator(int colindex, boolean isasc) {
		cols.add(new Integer(colindex));
		orders.add(new Boolean(isasc));
	}

	public void removeComparator(int index) {
		cols.remove(index);
		orders.remove(index);
	}

	public int compare(Object o1, Object o2) {
		List list1 = (List) o1;
		List list2 = (List) o2;
		int result = 0;
		for (int i = 0; i < cols.size(); i++) {
			int colindex = ((Integer) cols.get(i)).intValue();
			boolean isasc = ((Boolean) orders.get(i)).booleanValue();
			Comparable data1 = (Comparable) list1.get(colindex);
			Comparable data2 = (Comparable) list2.get(colindex);
			int r = data1.compareTo(data2);
			if (r != 0) {
				if (isasc)
					result = r;
				else
					result = -r;
				break;
			}

		}
		return result;
	}

	public static void main(String[] args) {
		Vector v1 = new Vector();
		v1.add(new Integer(3));
		v1.add(new Integer(2));
		v1.add(new Integer(1));
		v1.add("v1");
		Vector v2 = new Vector();
		v2.add(new Integer(1));
		v2.add(new Integer(2));
		v2.add(new Integer(3));
		v2.add("v2");

		Vector list = new Vector();
		list.add(v1);
		list.add(v2);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Vector element = (Vector) iter.next();
			logger.debug(element.get(3));
		}
		MultiComparator c = new MultiComparator(1, true);
		c.addComparator(2, false);
		Collections.sort(list, c);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Vector element = (Vector) iter.next();
			logger.debug(element.get(3));
		}
	}

}
