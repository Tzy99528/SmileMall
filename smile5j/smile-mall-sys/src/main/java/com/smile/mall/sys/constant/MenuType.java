package com.smile.mall.sys.constant;

/**
 * 枚举类 MenuType，它定义了三种菜单类型：目录、菜单和按钮。每种类型都有一个整数值与之对应
 * @author lanhai
 */
public enum MenuType {
    /**
     * 目录
     */
    CATALOG(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);

    private int value;

    MenuType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
