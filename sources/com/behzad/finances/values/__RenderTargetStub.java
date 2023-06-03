package com.behzad.finances.values;
import com.behzad.finances.ui.RenderTarget;
import com.behzad.finances.ui.Resources;
import com.behzad.finances.util.UnreachableCodeException;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

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

