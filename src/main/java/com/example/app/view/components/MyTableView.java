package com.example.app.view.components;

import javafx.scene.AccessibleAttribute;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

@Component
public class MyTableView<S> extends TableView<S> {

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        // 隐藏滚动条
        ScrollBar scrollBar1 = (ScrollBar) queryAccessibleAttribute(AccessibleAttribute.HORIZONTAL_SCROLLBAR);
        ScrollBar scrollBar2 = (ScrollBar) queryAccessibleAttribute(AccessibleAttribute.VERTICAL_SCROLLBAR);
        if (scrollBar1 != null && scrollBar1.isVisible()) {
            scrollBar1.setPrefHeight(0);
            scrollBar1.setMaxHeight(0);
            scrollBar1.setOpacity(1);
            scrollBar1.setVisible(false);
        }
        if (scrollBar2 != null && scrollBar2.isVisible()) {
            scrollBar2.setPrefHeight(0);
            scrollBar2.setMaxHeight(0);
            scrollBar2.setOpacity(1);
            scrollBar2.setVisible(false);
        }
    }
}