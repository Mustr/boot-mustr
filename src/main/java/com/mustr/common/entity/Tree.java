package com.mustr.common.entity;

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {

    private String id;
    private String title;
    private List<Tree<T>> children = new ArrayList<Tree<T>>();
    private String parentId;
    private boolean hasParent = false;
    private boolean hasChildren = false;
    private T data;
    // 所有的子节点
    private List<Tree<T>> nodes = new ArrayList<Tree<T>>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isHasParent() {
        return hasParent;
    }

    public void setHasParent(boolean hasParent) {
        this.hasParent = hasParent;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<Tree<T>> getNodes() {
        return nodes;
    }

    public void setNodes(List<Tree<T>> nodes) {
        this.nodes = nodes;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
