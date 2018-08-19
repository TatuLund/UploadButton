package org.vaadin.uploadbutton.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.uploadbutton.UploadButton;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("UploadButton Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {

        UploadButton upload1 = new UploadButton("My Upload",
                (filename, mimeType)-> { return null; });
        upload1.setIcon(VaadinIcons.UPLOAD);

        UploadButton upload2 = new UploadButton("My Upload",
                (filename, mimeType)-> { return null; });
        upload2.setCaptionAsHtml(true);
        upload2.setButtonCaption(VaadinIcons.UPLOAD.getHtml()+" My Upload");
        
        Button button = new Button("Fake Upload");
        button.setIcon(VaadinIcons.UPLOAD_ALT);
        
        // Show it in the middle of the screen
        final VerticalLayout layout = new VerticalLayout();
        layout.setStyleName("demoContentLayout");
        layout.setSizeFull();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.addComponents(upload1,upload2,button);
        layout.setComponentAlignment(upload1, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(upload2, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
        setContent(layout);
    }
}
