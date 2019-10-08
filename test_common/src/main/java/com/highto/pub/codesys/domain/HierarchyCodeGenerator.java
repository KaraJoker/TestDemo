package com.highto.pub.codesys.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.highto.framework.tree.Tree;
import com.highto.framework.tree.TreeNode;

/**
 * 层级结构编码生成器
 * 
 * @author wang xp
 * @date 2015-07-21
 */
public class HierarchyCodeGenerator {

	private String id;

	private String name;

	private Tree tree;

	private int[] struc;

	private int depth;

	private int digit;

	public HierarchyCodeGenerator(String name, int[] struc, Tree tree) {
		this.name = name;
		this.struc = struc;
		this.tree = tree;
		this.depth = struc.length;

		int z = 0;
		for (int i : struc) {
			z = z + i;
		}
		this.digit = z;
	}

	/**
	 * 可变层级，如4级，传4个参数，每个参数值代表每位位数
	 * 
	 * @param structure
	 */
	public HierarchyCodeGenerator(String name, int... structure) {
		int z = 0;
		for (int i : structure) {
			z = z + i;
		}
		String str = String.format("%0" + z + "d", 0);
		HierarchyCodeNode tn = new HierarchyCodeNode();
		tn.setCode(str);
		tn.setCodeValue(0);
		List<HierarchyCodeNode> list = new ArrayList<HierarchyCodeNode>();
		list.add(tn);
		Tree tree = new Tree(list);
		this.setTree(tree);

		this.name = name;
		struc = structure;
		depth = structure.length;
		digit = z;
	}

	/**
	 * 根据当前code生成下一层级code
	 * 
	 * @param currentCode
	 *            当前编码
	 * @param isSave
	 *            是否保存到数据库
	 * @return
	 * @throws Throwable
	 */
	public synchronized HierarchyCodeNode getHierarchyCode(String currentCode, boolean isSave) throws Throwable {
		if (currentCode == null) {
			currentCode = getRootNodeStr();
		}
		TreeNode n = tree.getNodeById(currentCode);
		List<TreeNode> children = n.getChildren();

		List<HierarchyCodeNode> result = new ArrayList<HierarchyCodeNode>();
		for (TreeNode tn : children) {
			HierarchyCodeNode cn = (HierarchyCodeNode) tn;
			result.add(cn);
		}

		// 对result排序
		Collections.sort(result, new Comparator<HierarchyCodeNode>() {
			@Override
			public int compare(HierarchyCodeNode m1, HierarchyCodeNode m2) {
				if (m1.getCodeValue() < m2.getCodeValue()) {
					return -1;
				} else if (m1.getCodeValue() > m2.getCodeValue()) {
					return 1;
				} else {
					return 0;
				}
			}
		});

		HierarchyCodeNode cn = new HierarchyCodeNode();

		String str = currentCode;
		int s = result.size(); // 子节点个数
		int d = n.getNodeDepth(); // 当前节点深度，根为0
		int z = struc[d]; // 获取新生成节点的位数
		int w = 0; // 当前节点前缀位数
		for (int i = 0; i < d; i++) {
			w = w + struc[i];
		}

		int a = (s + 1) / (int) Math.pow(10, z);
		if (a > 0) {
			throw new Exception("超出最大节点数");
		}
		String prefix = "";
		if (!n.isRoot()) {
			prefix = str.substring(0, w);
		}

		if (s == 0) {
			str = prefix + String.format("%0" + z + "d", 1) + appendZero(w + z, digit);
			cn.setCode(str);
			cn.setCodeValue(1);
		} else {
			// 找出缺失节点并添加
			for (int i = 1; i <= s; i++) {
				HierarchyCodeNode e = result.get(i - 1);
				int x = e.getCodeValue();

				// 如果没有缺失节点则新增
				if (i == s && s == x) {
					str = prefix + String.format("%0" + z + "d", i + 1) + appendZero(w + z, digit);
					cn.setCode(str);
					cn.setCodeValue(i + 1);
				} else if (i < x) { // 有缺失节点则补入
					str = prefix + String.format("%0" + z + "d", i) + appendZero(w + z, digit);
					cn.setCode(str);
					cn.setCodeValue(i);
					break;
				}
			}
		}
		cn.setDepth(n.getNodeDepth() + 1);
		if (isSave) {
			tree.addChildNode(n, cn);
		}
		return cn;
	}

	/**
	 * 删除当前code
	 * 
	 * @param currentCode
	 * @throws Throwable
	 */
	public void deleteHierarchyCode(String currentCode) throws Throwable {
		TreeNode n = tree.getNodeById(currentCode);
		if (n == null) {
			throw new Exception("没有找到当前节点");
		}
		tree.deleteNode(n);
	}

	/**
	 * 生成跟节点
	 * 
	 * @return
	 */
	public String getRootNodeStr() {
		String str = "";
		for (int i = 0; i < digit; i++) {
			str = str + "0";
		}
		return str;
	}

	/**
	 * 字符串补0
	 * 
	 * @param g
	 *            前缀长度
	 * @param d
	 *            总位数
	 * @return
	 */
	private String appendZero(int g, int d) {
		String str = "";
		int x = d - g;
		for (int i = 0; i < x; i++) {
			str = str + "0";
		}
		return str;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tree getTree() {
		return tree;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	public int[] getStruc() {
		return struc;
	}

	public void setStruc(int[] struc) {
		this.struc = struc;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}
}
