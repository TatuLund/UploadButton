package org.vaadin.uploadbutton.demo;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

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
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("UploadButton Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI implements SucceededListener, FailedListener, ProgressListener {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    // Dummy receiver
    public class Uploader implements Receiver {

    	@Override
    	public OutputStream receiveUpload(String filename,
                                       String mimeType) {
    		System.out.println(filename+" "+mimeType);
         	return new ByteArrayOutputStream(10240);
     	}

    };

	@Override
 	public void uploadSucceeded(SucceededEvent event) {
 		System.out.println(event.getFilename()+" "+event.getLength()+" bytes");
 	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {
		System.out.println("Status: "+readBytes+"/"+contentLength);
		
	}

	@Override
	public void uploadFailed(FailedEvent event) {
		System.out.println("Failed");
	}

    @Override
    protected void init(VaadinRequest request) {

    	Uploader receiver = new Uploader();

        UploadButton upload1 = new UploadButton("My Upload", receiver);
        upload1.setIcon(VaadinIcons.UPLOAD);
        upload1.addProgressListener(this);
        upload1.addSucceededListener(this);
        upload1.addFailedListener(this);
        
        UploadButton upload2 = new UploadButton("My Upload", receiver);
        upload2.setCaptionAsHtml(true);
        upload2.setButtonCaption(VaadinIcons.UPLOAD.getHtml()+" My Upload");
        upload2.addProgressListener(this);
        upload2.addSucceededListener(this);
        upload2.addFailedListener(this);
        
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
