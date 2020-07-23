package org.vaadin.uploadbutton;

import org.vaadin.uploadbutton.client.UploadButtonState;

import com.vaadin.server.Resource;
import com.vaadin.ui.Upload.Receiver;

/**
 * UploadButton is version of Upload component which behaves more like Button
 * 
 * - Icon is correctly placed in submit button
 *    See: https://github.com/vaadin/framework/issues/11120
 * - Button caption correctly supports HTML when setCaptionAsHtml(true)
 * - Adds setIconAlternateText(..) method
 * - Add setIcon(Resource icon, String iconAltText) method
 * 
 * @author TatuLund
 *
 */
public class UploadButton extends com.vaadin.ui.Upload {
	
    public UploadButton(String caption, Receiver uploadReceiver) {
        super(caption, uploadReceiver);
    }

	public UploadButton() {
		super();
	}

	// We must override getState() to cast the state to UploadIconState
    @Override
    protected UploadButtonState getState() {
        return (UploadButtonState) super.getState();
    }
    
    /**
     * Sets the component's icon and alt text.
     * <p>
     * An alt text is shown when an image could not be loaded, and read by
     * assistive devices.
     *
     * @param icon
     *            the icon to be shown with the component's caption.
     * @param iconAltText
     *            String to use as alt text
     */
    public void setIcon(Resource icon, String iconAltText) {
        super.setIcon(icon);
        getState().iconAltText = iconAltText == null ? "" : iconAltText;
    }

    /**
     * Returns the icon's alt text.
     *
     * @return String with the alt text
     */
    public String getIconAlternateText() {
        return getState().iconAltText;
    }

    public void setIconAlternateText(String iconAltText) {
        getState().iconAltText = iconAltText;
    }
}
