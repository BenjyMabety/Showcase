package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tmg.client.FileEditor.FileEditor;
import com.tmg.client.FileEditor.FileService;
import com.tmg.client.FileEditor.FileServiceAsync;
import com.tmg.client.FileEditor.FileUploader;
import com.tmg.client.Login.Login;
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
	FileEditor fileEditor;
	Login login;
	GuessingGame gg;
	Ball ball;

	FileUploader upload = new FileUploader();

	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));
		headerLabel.getElement().setAttribute("style", "font-weight: bold;font-size:100px;text-align:center;");
		login = new Login();
		gg = new GuessingGame();
		fileEditor = new FileEditor();
		ball = new Ball();
		setupControlPanel();

		mainPanel.add(login.getPbLogin());
		mainPanel.add(gg.getPbGuess());
		mainPanel.add(fileEditor.getPbFileEditor());
		mainPanel.add(ball.getPbBall());

		login.getPbLogin().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				login.center();
				login.setGlassEnabled(true);
				taCanvas.setVisible(false);
				tbDocument.setVisible(false);
				buttonPanel.setVisible(false);
				tbDocument.setText("");
				tbDocument.setReadOnly(true);
				mainCanvas.remove(ball.getBall());
				controlPanel.setVisible(false);
				setEnabled(false);
			}
		});
		gg.getPbGuess().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				gg.center();
				gg.setGlassEnabled(true);
				taCanvas.setVisible(false);
				tbDocument.setVisible(false);
				buttonPanel.setVisible(false);
				tbDocument.setText("");
				tbDocument.setReadOnly(true);
				mainCanvas.remove(ball.getBall());
				controlPanel.setVisible(false);
				setEnabled(false);
			}
		});
		setUpEditorButtonHandlers();
		setUpBallButtonHandlers();

	}

	/**
	 * 
	 */
	private void setUpBallButtonHandlers() {
		ball.getPbBall().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				canvasPanel.setVisible(false);
				buttonPanel.setVisible(false);
				controlPanel.setVisible(true);
				mainCanvas.add(ball.getBall().asWidget());
				setEnabled(true);
			}
		});
		ball.getRightButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				moveX(physics.getForce(ball.getMass(), false, false));

			}
		});

		ball.getLeftButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				moveX(-physics.getForce(ball.getMass(), false, false));

			}
		});
		ball.getUpButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				doUpLogic();
			}

		});

		ball.getDownButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				doDownLogic();
				if (ball.getUpButton().isEnabled() && ball.getDownButton().isEnabled() && !ball.isStationary()) {
					ball.getUpButton().setEnabled(false);
					ball.getDownButton().setEnabled(false);
				}
				if (!ball.isStationary()) {
					if (ball.isSuspended()) {
						ball.setMoving(true);
					}
					if (ball.isSuspended() && ball.isMoving()) {
						Timer t = new Timer() {

							@Override
							public void run() {
								if (ball.isSuspended()) {
									moveY(physics.getForce(ball.getMass(), ball.isMoving(), false), isRunning());
								} else {
									if (ball.getBall().getParent().getAbsoluteTop() >= getArc()) {
										moveY(physics.getForce(ball.getMass(), ball.isMoving(), true), isRunning());
									} else {
										ball.setMoving(true);
										ball.setSuspended(true);
									}
								}
							}

							private int getArc() {
								// Hard coded edge to 181 based on BG image used
								int bounce = 181 + ball.getDistance();
								if (physics.isFriction()) {
									ball.setRunningDistance(ball.getMass());
								}
								if (ball.getDistance() == 330) { // arbitrary bounce frequency value
									ball.setMoving(false);
									ball.setSuspended(false);
									ball.setStationary(true);
									ball.getUpButton().setEnabled(true);
									ball.getDownButton().setEnabled(true);
									cancel();
								}
								return bounce;
							}
						};

						t.scheduleRepeating(physics.getForce(ball.getMass(), ball.isMoving(), false));
					} else {
						moveY(physics.getForce(ball.getMass(), ball.isMoving(), false), false);
					}

				}
			}
		});
		ball.getPbKeyboard().addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
					moveX(physics.getForce(ball.getMass(), false, false));
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
					moveX(-physics.getForce(ball.getMass(), false, false));
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_UP) {
					if (!ball.isMoving()) {
						doUpLogic();
					}
					if (ball.isStationary()) {
						doUpLogic();
					}
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN) {
					if (!ball.isMoving()) {
						doDownLogic();
					}
				}
			}
		});

	}

	/**
	 * 
	 */
	protected void doDownLogic() {
		if (ball.getUpButton().isEnabled() && ball.getDownButton().isEnabled() && !ball.isStationary()) {
			ball.getUpButton().setEnabled(false);
			ball.getDownButton().setEnabled(false);
		}
		if (!ball.isStationary()) {
			if (ball.isSuspended()) {
				ball.setMoving(true);
			}
			if (ball.isSuspended() && ball.isMoving()) {
				Timer t = new Timer() {

					@Override
					public void run() {
						if (ball.isSuspended()) {
							moveY(physics.getForce(ball.getMass(), ball.isMoving(), false), isRunning());
						} else {
							if (ball.getBall().getParent().getAbsoluteTop() >= getArc()) {
								moveY(physics.getForce(ball.getMass(), ball.isMoving(), true), isRunning());
							} else {
								ball.setMoving(true);
								ball.setSuspended(true);
							}
						}
					}

					private int getArc() {
						// Hard coded edge to 181 based on BG image used
						int bounce = 181 + ball.getDistance();
						if (physics.isFriction()) {
							ball.setRunningDistance(ball.getMass());
						}
						if (ball.getDistance() == 330) { // arbitrary bounce frequency value
							ball.setMoving(false);
							ball.setSuspended(false);
							ball.setStationary(true);
							ball.getUpButton().setEnabled(true);
							ball.getDownButton().setEnabled(true);
							cancel();
						}
						return bounce;
					}
				};

				t.scheduleRepeating(physics.getForce(ball.getMass(), ball.isMoving(), false));
			} else {
				moveY(physics.getForce(ball.getMass(), ball.isMoving(), false), false);
			}

		}

	}

	/**
	 * 
	 */
	protected void doUpLogic() {
		ball.setSuspended(true);
		ball.setMoving(false);
		ball.setStationary(false);
		ball.setDistance(0);
		moveY(-physics.getForce(ball.getMass(), false, false), false);

	}

	/**
	 * 
	 */
	private void setupControlPanel() {
		ball.getTbFriction().addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				physics.setFriction(event.getValue());

			}
		});

		controlPanel.add(ball.getUpButton());

		HorizontalPanel hp = new HorizontalPanel();
		hp.add(ball.getLeftButton());
		hp.add(ball.getDownButton());
		hp.add(ball.getRightButton());
		controlPanel.add(hp);
		controlPanel.add(ball.getPbKeyboard());
		controlPanel.add(ball.getTbFriction());
		controlPanel.setCellHorizontalAlignment(hp, HasHorizontalAlignment.ALIGN_CENTER);
		controlPanel.setCellHorizontalAlignment(ball.getUpButton(), HasHorizontalAlignment.ALIGN_CENTER);

	}

	/**
	 * 
	 */
	private void setUpEditorButtonHandlers() {

		buttonPanel.add(fileEditor.getNewButton());
		buttonPanel.add(fileEditor.getEditButton());
		buttonPanel.add(fileEditor.getSaveButton());
		buttonPanel.add(fileEditor.getLoadButton());

		ClickHandler openHandler = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taCanvas.setValue("");
				taCanvas.setVisible(true);
				tbDocument.setVisible(true);
				upload.getOpenButton().setEnabled(true);
				canvasPanel.setVisible(true);
				buttonPanel.setVisible(true);
				upload.center();
				upload.setGlassEnabled(true);
				tbDocument.setReadOnly(true);
				mainCanvas.remove(ball.getBall());
				controlPanel.setVisible(false);
				setEnabled(false);
			}
		};
		fileEditor.getPbFileEditor().addClickHandler(openHandler);
		fileEditor.getLoadButton().addClickHandler(openHandler);
		upload.getCloseButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taCanvas.setValue(upload.getData());
				tbDocument.setVisible(true);
				tbDocument.setValue(upload.getUpload().getFilename());
				tbDocument.setReadOnly(true);
				upload.hide();
			}
		});
		fileEditor.getNewButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				tbDocument.setReadOnly(false);
				if (upload.getUpload().getFilename().isBlank()) {
					tbDocument.setValue("Enter path here");
				}
				taCanvas.setValue("");

			}
		});
		fileEditor.getEditButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				taCanvas.setReadOnly(false);
				tbDocument.setReadOnly(true);
			}
		});
		fileEditor.getSaveButton().addClickHandler(new ClickHandler() {

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

	/**
	 * @param value
	 */
	protected void moveX(int value) {

		ball.getBall().getParent().getElement().getStyle().setLeft(ball.getRightStep() + value, Unit.PX);
		ball.setRightStep(ball.getRightStep() + value);

	}

	/**
	 * @param value
	 * @param running
	 */
	protected void moveY(int value, boolean running) {

		if (running) {
			if (ball.isSuspended()) {
				if (ball.getBall().getParent().getAbsoluteTop() <= 450) {// checks bottom edge of background Image
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
			ball.getBall().getParent().getElement().getStyle().setTop(ball.getTopStep() + value, Unit.PX);
			ball.setTopStep(ball.getTopStep() + value);
		}

	}

	/**
	 * @param enabled
	 */
	void setEnabled(boolean enabled) {
		getElement().addClassName(enabled ? style.enabled() : style.disabled());
		getElement().removeClassName(enabled ? style.disabled() : style.enabled());
	}

}
