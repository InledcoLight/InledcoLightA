package com.inledco.light.bean;

/**
 * 列表模型抽象基类
 */
public abstract class ListItem {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_LIGHT = 1;

    abstract public int getType();
}
