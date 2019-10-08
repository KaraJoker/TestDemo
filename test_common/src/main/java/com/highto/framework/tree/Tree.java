package com.highto.framework.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 树容器
 * 
 * @author wang xp
 * @date 2015-06-15
 */
public class Tree {

	/**
	 * 根节点
	 */
	private TreeNode rootNode;

	private Map<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();

	/**
	 * 构建树
	 * 
	 * @param list
	 */
	public Tree(List<? extends TreeNode> list) {
		for (TreeNode node : list) {
			nodeMap.put(node.getNodeId(), node);
		}

		for (TreeNode node : list) {
			if (node.isRoot()) {
				rootNode = node;
			} else {
				String nodeId = node.getParentNodeId();
				TreeNode pNode = nodeMap.get(nodeId);
				if (pNode != null) {
					pNode.addChildNode(node);
					node.setParentNode(pNode);
				}
			}
		}
	}

	/**
	 * 根据nodeId获取TreeNode
	 * 
	 * @param nodeId
	 * @return
	 */
	public TreeNode getNodeById(String nodeId) {
		TreeNode node = nodeMap.get(nodeId);
		return node;
	}

	/**
	 * 添加节点
	 */
	public void addChildNode(TreeNode parentNode, TreeNode node) {
		nodeMap.put(node.getNodeId(), node);
		parentNode.addChildNode(node);
	}

	/**
	 * 删除节点
	 * 
	 * @param node
	 */
	public void deleteNode(TreeNode node) {
		if (!node.isRoot()) {
			node.getParentNode().deleteChildNode(node);
		}
		List<TreeNode> depthList = new ArrayList<TreeNode>();
		node.dealDepthList(depthList);
		for (TreeNode tn : depthList) {
			nodeMap.remove(tn.getNodeId());
		}
	}

	/**
	 * 深度遍历tree
	 */
	public List<TreeNode> getDepthList() {
		List<TreeNode> depthList = new ArrayList<TreeNode>();
		rootNode.dealDepthList(depthList);
		return depthList;
	}

	/**
	 * 生成和此树同根的包含给定节点的最小子树的所有节点。
	 * 
	 * @param nodes
	 * @return
	 */
	public Set<TreeNode> generateMinSubTreeForNodes(List<TreeNode> nodes) {
		Set<TreeNode> nodesToReturn = new HashSet<TreeNode>();
		nodesToReturn.addAll(nodes);
		for (TreeNode node : nodes) {
			TreeNode currentNode = node;
			while (!currentNode.isRoot()) {
				TreeNode parentNode = nodeMap.get(currentNode.getParentNodeId());
				nodesToReturn.add(parentNode);
				currentNode = parentNode;
			}
		}
		return nodesToReturn;
	}

	public TreeNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TreeNode rootNode) {
		this.rootNode = rootNode;
	}

	public void deleteNodeById(String id) {
		deleteNode(getNodeById(id));
	}

	/**
	 * 只是返回普通的所有节点列表，不需要符合任何遍历规则
	 * 
	 * @return
	 */
	public Collection<TreeNode> getPlanNodeList() {
		return nodeMap.values();
	}
}
