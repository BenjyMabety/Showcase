package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FileViewer extends DialogBox {

	private static FileViewerUiBinder uiBinder = GWT.create(FileViewerUiBinder.class);

	private final FileServiceAsync fileService = GWT.create(FileService.class);

	interface FileViewerUiBinder extends UiBinder<Widget, FileViewer> {
	}

	Button closeButton = new Button("Close");
	Button openButton = new Button("Open");
	VerticalPanel vp = new VerticalPanel();
	HorizontalPanel hp = new HorizontalPanel();

	FileUpload upload = new FileUpload();

	private String data;

	public FileViewer() {
		setWidget(uiBinder.createAndBindUi(this));
		addButtons();
		this.setWidget(vp);

		openButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				try {
					if (!upload.getFilename().isBlank()) {
						openFile();
					} else {
						Window.alert("Please select a text file");
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Add form Buttons
	 */
	private void addButtons() {
		vp.add(upload);
		hp.add(openButton);
		hp.add(closeButton);
		vp.add(hp);
	}

	/**
	 * @throws IllegalArgumentException
	 */
	public void openFile() throws IllegalArgumentException {
		fileService.openFile(upload.getFilename(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				data = result;
				openButton.setEnabled(false);
			}
		});
	}

	/**
	 * @return data
	 */
	public String getData() {
		return data;
	}

}
