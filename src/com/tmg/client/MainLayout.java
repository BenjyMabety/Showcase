package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tmg.client.Resources.Resources;

public class MainLayout extends Composite {

	private static MainLayoutUiBinder uiBinder = GWT.create(MainLayoutUiBinder.class);
	private final FileServiceAsync fileService = GWT.create(FileService.class);

	Resources resources = GWT.create(Resources.class);

	interface MainLayoutUiBinder extends UiBinder<Widget, MainLayout> {
	}

	@UiField
	VerticalPanel mainPanel;
	@UiField
	VerticalPanel canvasPanel;
	@UiField
	TextArea taCanvas;
	@UiField
	Label headerLabel;
	@UiField
	TextBox tbDocument;
	@UiField
	VerticalPanel buttonPanel;

	Image editImage = new Image(resources.edit());
	Image loadImage = new Image(resources.load());
	Image saveImage = new Image(resources.save());
	Image newImage = new Image(resources.new_file());

	PushButton editButton = new PushButton(editImage);
	PushButton loadButton = new PushButton(loadImage);
	PushButton saveButton = new PushButton(saveImage);
	PushButton newButton = new PushButton(newImage);

	PushButton pbLogin;
	PushButton pbGuess;
	PushButton pbFileViewer;

	Login login = new Login();
	GuessingGame gg = new GuessingGame();
	FileViewer viewer = new FileViewer();

	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));
		pbLogin = new PushButton("Login Widget");
		pbGuess = new PushButton("Guessing Game");
		pbFileViewer = new PushButton("File Viewer");

		login = new Login();
		headerLabel.getElement().setAttribute("style", "font-weight: bold");
		mainPanel.add(pbLogin);
		mainPanel.add(pbGuess);
		mainPanel.add(pbFileViewer);

		editButton.setTitle("Edit");
		saveButton.setTitle("Save");
		loadButton.setTitle("Open");

		buttonPanel.add(newButton);
		buttonPanel.add(editButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);

		pbLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				login.center();
				login.setGlassEnabled(true);
				taCanvas.setVisible(false);
				tbDocument.setVisible(false);
				buttonPanel.setVisible(false);
				tbDocument.setText("");
				tbDocument.setReadOnly(true);

			}
		});
		pbGuess.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				gg.center();
				gg.setGlassEnabled(true);
				taCanvas.setVisible(false);
				tbDocument.setVisible(false);
				buttonPanel.setVisible(false);
				tbDocument.setText("");
				tbDocument.setReadOnly(true);
			}
		});
		ClickHandler openHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taCanvas.setValue("");
				taCanvas.setVisible(true);
				tbDocument.setVisible(true);
				viewer.openButton.setEnabled(true);
				buttonPanel.setVisible(true);
				viewer.center();
				viewer.setGlassEnabled(true);
				tbDocument.setReadOnly(true);
			}
		};
		pbFileViewer.addClickHandler(openHandler);
		loadButton.addClickHandler(openHandler);
		viewer.closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taCanvas.setValue(viewer.getData());
				tbDocument.setVisible(true);
				tbDocument.setValue(viewer.upload.getFilename());
				tbDocument.setReadOnly(true);
				viewer.hide();
			}
		});
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				tbDocument.setReadOnly(false);
				if (viewer.upload.getFilename().isBlank()) {
					tbDocument.setValue("Enter path here");
				}
				taCanvas.setValue("");

			}
		});
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taCanvas.setReadOnly(false);
				tbDocument.setReadOnly(true);
			}
		});
		saveButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				fileService.saveFile(tbDocument.getValue(), taCanvas.getValue(), new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getLocalizedMessage());
					}

					@Override
					public void onSuccess(String result) {
						taCanvas.setReadOnly(true);
						tbDocument.setReadOnly(true);
						Window.alert(result);
					}
				});

			}
		});
	}

}
