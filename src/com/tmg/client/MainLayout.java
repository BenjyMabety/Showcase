package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tmg.client.Resources.Resources;
import com.tmg.shared.MyFoo.MyStyle;
import com.tmg.shared.Physics;

public class MainLayout extends Composite {

	private static MainLayoutUiBinder uiBinder = GWT.create(MainLayoutUiBinder.class);
	private final FileServiceAsync fileService = GWT.create(FileService.class);

	Resources resources = GWT.create(Resources.class);
	Physics physics = new Physics();

	interface MainLayoutUiBinder extends UiBinder<Widget, MainLayout> {
	}

	@UiField
	VerticalPanel mainPanel;
	@UiField
	HorizontalPanel mainCanvas;
	@UiField
	VerticalPanel controlPanel;
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
	@UiField
	MyStyle style;

	Image editImage = new Image(resources.edit());
	Image loadImage = new Image(resources.load());
	Image saveImage = new Image(resources.save());
	Image newImage = new Image(resources.new_file());
	Image hardWood = new Image(resources.hardwood1());

	PushButton editButton = new PushButton(editImage);
	PushButton loadButton = new PushButton(loadImage);
	PushButton saveButton = new PushButton(saveImage);
	PushButton newButton = new PushButton(newImage);

	Image up = new Image(resources.up());
	Image down = new Image(resources.down());
	Image left = new Image(resources.left());
	Image right = new Image(resources.right());

	PushButton upButton = new PushButton(up);
	PushButton downButton = new PushButton(down);
	PushButton leftButton = new PushButton(left);
	PushButton rightButton = new PushButton(right);

	PushButton pbLogin;
	PushButton pbGuess;
	PushButton pbFileViewer;
	PushButton pbBall;
	ToggleButton tbFriction;

	Login login = new Login();
	GuessingGame gg = new GuessingGame();
	FileViewer viewer = new FileViewer();
	Ball ball = new Ball();

	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));
		pbLogin = new PushButton("Login Widget");
		pbGuess = new PushButton("Guessing Game");
		pbFileViewer = new PushButton("File Viewer");
		pbBall = new PushButton("Ball (Physics)");
		tbFriction = new ToggleButton("Friction");
		tbFriction.setValue(true);
		tbFriction.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				// TODO Auto-generated method stub
				physics.setFriction(event.getValue());

			}
		});
		// Disabling for Github purposes
		// pbBall.setEnabled(false);

		login = new Login();
		headerLabel.getElement().setAttribute("style", "font-weight: bold");
		mainPanel.add(pbLogin);
		mainPanel.add(pbGuess);
		mainPanel.add(pbFileViewer);
		mainPanel.add(pbBall);

		editButton.setTitle("Edit");
		saveButton.setTitle("Save");
		loadButton.setTitle("Open");

		buttonPanel.add(newButton);
		buttonPanel.add(editButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);

		controlPanel.add(upButton);

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(leftButton);
		hp.add(downButton);
		hp.add(rightButton);
		controlPanel.add(hp);
		controlPanel.add(tbFriction);

		controlPanel.setCellHorizontalAlignment(upButton, HasHorizontalAlignment.ALIGN_CENTER);

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
				mainCanvas.remove(ball.getLife());
				controlPanel.setVisible(false);
				setEnabled(false);
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
				mainCanvas.remove(ball.getLife());
				controlPanel.setVisible(false);
				setEnabled(false);
			}
		});
		ClickHandler openHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taCanvas.setValue("");
				taCanvas.setVisible(true);
				tbDocument.setVisible(true);
				viewer.openButton.setEnabled(true);
				canvasPanel.setVisible(true);
				buttonPanel.setVisible(true);
				viewer.center();
				viewer.setGlassEnabled(true);
				tbDocument.setReadOnly(true);
				mainCanvas.remove(ball.getLife());
				controlPanel.setVisible(false);
				setEnabled(false);
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
		pbBall.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				canvasPanel.setVisible(false);
				buttonPanel.setVisible(false);
				controlPanel.setVisible(true);
				mainCanvas.add(ball.setLife().asWidget());
				setEnabled(true);
			}
		});
		rightButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				moveX(physics.getForce(ball.getMass(), false, false));

			}
		});

		leftButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				moveX(-physics.getForce(ball.getMass(), false, false));

			}
		});
		upButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				ball.setSuspended(true);
				ball.setFalling(false);
				ball.setStationery(false);
				ball.setGap(0);
				moveY(-physics.getForce(ball.getMass(), false, false), false);

			}
		});

		downButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (upButton.isEnabled() && downButton.isEnabled() && !ball.isStationery()) {
					upButton.setEnabled(false);
					downButton.setEnabled(false);
				}
				if (!ball.isStationery()) {
					if (ball.isSuspended()) {
						ball.setFalling(true);
					}
					if (ball.isSuspended() && ball.isFalling()) {
						ball.setFalling(true);
						Timer t = new Timer() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (ball.isSuspended()) {
									moveY(physics.getForce(ball.getMass(), ball.isFalling(), false), isRunning());
								} else {
									// Window.alert(ball.getBall().getParent().getAbsoluteTop() + "");
									if (ball.getBall().getParent().getAbsoluteTop() >= getArc()) {
										moveY(physics.getForce(ball.getMass(), ball.isFalling(), true), isRunning());
									} else {
										ball.setFalling(true);
										ball.setSuspended(true);
									}
								}
							}

							private int getArc() {
								// TODO Auto-generated method stub
								// Window.alert( + "");
								int bounce = 181 + ball.getGap();
								if (physics.isFriction()) {
									ball.setRunningGap(ball.getMass());
								}
								if (ball.getGap() == 330) {
									ball.setFalling(false);
									ball.setSuspended(false);
									ball.setStationery(true);
									upButton.setEnabled(true);
									downButton.setEnabled(true);
									cancel();
								}
								return bounce;
							}
						};

						t.scheduleRepeating(physics.getForce(ball.getMass(), ball.isFalling(), false));
					} else {
						moveY(physics.getForce(ball.getMass(), ball.isFalling(), false), false);
					}

				}
			}
		});

	}

	/**
	 * @param value
	 */
	protected void moveX(int value) {

		ball.getBall().getParent().getElement().getStyle().setLeft(ball.getRightStep() + value, Unit.PX);
		ball.setRightStep(ball.getRightStep() + value);

	}

	protected void moveY(int value, boolean running) {

		if (running) {
			if (ball.isSuspended()) {
				if (ball.getBall().getParent().getAbsoluteTop() <= 450) {
					ball.getBall().getParent().getElement().getStyle().setTop(ball.getTopStep() + value, Unit.PX);
					ball.setTopStep(ball.getTopStep() + value);
				} else {

					ball.setSuspended(false);
				}
			} else {

				ball.getBall().getParent().getElement().getStyle().setTop(ball.getTopStep() + value, Unit.PX);
				ball.setTopStep(ball.getTopStep() + value);
			}
		} else {
			// Window.alert("not running, logic here..suspended:" + ball.isSuspended() +
			// "falling:" + ball.isFalling());
			ball.getBall().getParent().getElement().getStyle().setTop(ball.getTopStep() + value, Unit.PX);
			ball.setTopStep(ball.getTopStep() + value);
		}

	}

	void setEnabled(boolean enabled) {
		getElement().addClassName(enabled ? style.enabled() : style.disabled());
		getElement().removeClassName(enabled ? style.disabled() : style.enabled());
	}

}
