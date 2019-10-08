package com.highto.framework.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * TreeNode抽象
 * 
 * @author wang xp
 * @date 2015-06-15
 */
public abstract class TreeNode {

	protected TreeNode parentNode;

	/**
	 * 子节点
	 */
	protected List<TreeNode> children = new ArrayList<TreeNode>();

	/**
	 * 获取nodeId
	 * 
	 * @return TreeNode
	 */
	public abstract String getNodeId();

	/**
	 * 是否根节点
	 * 
	 * @return
	 */
	public boolean isRoot() {
		return getParentNode() == null;
	}

	/**
	 * 获取节点名称
	 * 
	 * @return String
	 */
	public abstract String getName();

	/**
	 * 添加子节点
	 * 
	 * @return
	 */
	public void addChildNode(TreeNode node) {
		children.add(node);
		node.setParentNode(this);
	}

	/**
	 * 删除子节点
	 * 
	 * @return
	 */
	public void deleteChildNode(TreeNode node) {
		children.remove(node);
		node.setParentNode(null);
	}

	/**
	 * 得到当前节点深度
	 * 
	 * @return
	 */
	public int getNodeDepth() {
		if (isRoot()) {
			return 0;
		} else {
			return parentNode.getNodeDepth() + 1;
		}
	}

	/**
	 * @param depthList
	 *            存放遍历的结果
	 */
	protected void dealDepthList(List<TreeNode> depthList) {
		depthList.add(this);
		for (TreeNode childNode : children) {
			childNode.dealDepthList(depthList);
		}
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public abstract String getParentNodeId();

	public TreeNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}

	public boolean equals(Object o) {
		return ((TreeNode) o).getNodeId().equals(getNodeId());
	}

	public int hashCode() {
		return getNodeId().hashCode();
	}

}
