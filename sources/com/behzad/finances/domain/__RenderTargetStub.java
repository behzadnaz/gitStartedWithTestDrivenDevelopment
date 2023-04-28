package com.behzad.finances.domain;
import com.behzad.finances.ui.RenderTarget;

import javax.swing.*;
import java.awt.*;

 class __RenderTargetStub implements RenderTarget {
        public Icon icon;
        public String text;
        public Color foregroundColor;
        public String toolTipText;

        public void setText(String text){this.text = text;}
        public void setIcon(Icon icon, String toolTipText){
               this.icon = icon;
               this.toolTipText = toolTipText;
        }
        public void setForeGroundColor(Color color){this.foregroundColor = color;}
}

