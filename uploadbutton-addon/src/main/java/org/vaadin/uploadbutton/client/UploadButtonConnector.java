package org.vaadin.uploadbutton.client;

import org.vaadin.uploadbutton.UploadButton;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ApplicationConnection;
import com.vaadin.client.MouseEventDetailsBuilder;
import com.vaadin.client.UIDL;
import com.vaadin.client.VCaption;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.client.ui.Icon;
import com.vaadin.client.ui.VUpload;
import com.vaadin.client.ui.upload.UploadConnector;
import com.vaadin.shared.MouseEventDetails;
import com.vaadin.shared.ui.Connect;
import com.vaadin.shared.ui.upload.UploadState;

// Connector binds client-side widget class to server-side component class
// Connector lives in the client and the @Connect annotation specifies the
// corresponding server-side component
@Connect(UploadButton.class)
public class UploadButtonConnector extends UploadConnector {

	Icon icon;
	
    @Override
    public boolean delegateCaptionHandling() {
        return false;
    }

    public UploadButtonConnector() {
        super();
    }

    @Override
    public void init() {
        super.init();
    }
    
    @OnStateChange("resources")
    void onResourceChange() {
        if (getWidget().submitButton.icon != null) {
            getWidget().submitButton.wrapper.removeChild(getWidget().submitButton.icon.getElement());
            getWidget().submitButton.icon = null;
        }
        Icon icon = getIcon();
        if (icon != null) {
            getWidget().submitButton.icon = icon;
            icon.setAlternateText(getState().iconAltText);
            getWidget().submitButton.wrapper.insertBefore(icon.getElement(),
                    getWidget().submitButton.captionElement);
        }
    }

    @OnStateChange("iconAltText")
    void setIconAltText() {
        if (getWidget().submitButton.icon != null) {
            getWidget().submitButton.icon.setAlternateText(getState().iconAltText);
        }
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        if (!isRealUpdate(uidl)) {
            return;
        }
        VUpload upload = getWidget();
        if (uidl.hasAttribute("notStarted")) {
            upload.t.schedule(400);
            return;
        }
        upload.setImmediateMode(getState().immediateMode);
        upload.client = client;
        upload.paintableId = uidl.getId();
        upload.nextUploadId = uidl.getIntAttribute("nextid");
        final String action = client
                .translateVaadinUri(uidl.getStringVariable("action"));
        upload.element.setAction(action);

        upload.fu.setName(upload.paintableId + "_file");

        if (!isEnabled()) {
            upload.disableUpload();
        } else if (!uidl.getBooleanAttribute("state")) {
            // Enable the button only if an upload is not in progress
            upload.enableUpload();
            upload.ensureTargetFrame();
        }
    }
    
    // We must implement getWidget() to cast to correct type 
    // (this will automatically create the correct widget type)
    @Override
    public VUpload getWidget() {
        return (VUpload) super.getWidget();
    }

    // We must implement getState() to cast to correct type
    @Override
    public UploadButtonState getState() {
        return (UploadButtonState) super.getState();
    }
}
