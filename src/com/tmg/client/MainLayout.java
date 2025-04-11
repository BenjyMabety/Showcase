package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.tmg.client.SpaceForce.Asteroid;
import com.tmg.client.SpaceForce.Bullet;
import com.tmg.client.SpaceForce.SpaceForce;
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
	SpaceForce sf;
	Timer t;
	Asteroid asteroid;

	FileUploader upload = new FileUploader();

	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));
		headerLabel.getElement().setAttribute("style", "font-weight: bold;font-size:100px;text-align:center;");
		login = new Login();
		gg = new GuessingGame();
		fileEditor = new FileEditor();
		ball = new Ball();
		sf = new SpaceForce();

		mainPanel.add(login.getPbLogin());
		mainPanel.add(gg.getPbGuess());
		mainPanel.add(fileEditor.getPbFileEditor());
		mainPanel.add(ball.getPbBall());
		mainPanel.add(sf.getPbSpaceForce());

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
				mainCanvas.remove(ball.getImage());
				// ball.setLive(false);
				controlPanel.setVisible(false);
				setEnabled(false, true);
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
				mainCanvas.remove(ball.getImage());
				// ball.setLive(false);
				controlPanel.setVisible(false);
				setEnabled(false, true);
			}
		});

		ball.getPbBall().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (t != null) {
					t.cancel();
				}
				canvasPanel.setVisible(false);
				buttonPanel.setVisible(false);
				setupControlPanel(true);
				controlPanel.setVisible(true);
				mainCanvas.add(ball.getImage().asWidget());
				// ball.setLive(true);
				mainCanvas.remove(sf.getImage());
				setEnabled(true, true);
				mainCanvas.getParent().getElement().setAttribute("style",
						"position: absolute; inset: 0px;background-position:center;background-repeat:no-repeat");
			}
		});
		sf.getPbSpaceForce().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				canvasPanel.setVisible(false);
				buttonPanel.setVisible(false);
				controlPanel.setVisible(true);
				setupControlPanel(false);
				mainCanvas.remove(ball.getImage());
				// ball.setLive(false);
				setEnabled(true, false);
				mainCanvas.add(sf.getImage().asWidget());
				asteroid = new Asteroid();
				mainCanvas.add(asteroid.getImage().asWidget());

				sf.setDistance(mainCanvas.getAbsoluteLeft());
				asteroid.setRightStep(735);
				// asteroid.setTopStep(195);
				// left:735px;top:0px;
				// left:735px;top:195px;
				// left:735px;top:375px;

				t = new Timer() {

					@Override
					public void run() {
						asteroid.getImage().setVisible(true);
						redraw();
					}

					private void redraw() {

						mainCanvas.getParent().getElement().setAttribute("style",
								"position: absolute; inset: 0px;background-position:" + sf.getDistance()
										+ "px;background-repeat:repeat-x;");
						sf.setDistance(sf.getDistance() - physics.getSpace());

						asteroid.moveX(-physics.getForce(asteroid.getMass(), false, false));
						// asteroid.moveX(-1);
						if (physics.checkCollision(sf, asteroid)) {
							sf.getImage().removeFromParent();
						}

					}
				};
				t.scheduleRepeating(1000);

			}
		});
		setUpEditorButtonHandlers();

	}

	/**
	 * 
	 */
	private void setupControlPanel(boolean isBall) {
		controlPanel.clear();
		if (isBall) {
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
		} else {
			controlPanel.add(sf.getUpButton());
			HorizontalPanel hp = new HorizontalPanel();
			hp.add(sf.getLeftButton());
			hp.add(sf.getDownButton());
			hp.add(sf.getRightButton());
			if (!sf.getUpButton().isEnabled() && !sf.getDownButton().isEnabled()) {
				sf.getUpButton().setEnabled(true);
				sf.getDownButton().setEnabled(true);
			}
			sf.getLeftButton().setEnabled(false);
			sf.getRightButton().setEnabled(false);
			controlPanel.add(hp);
			controlPanel.add(sf.getPbKeyboard());
			sf.getPbKeyboard().addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					/*
					 * if (event.getNativeKeyCode() == KeyCodes.KEY_RIGHT) {
					 * sf.moveX(physics.getForce(sf.getMass(), false, false)); } if
					 * (event.getNativeKeyCode() == KeyCodes.KEY_LEFT) {
					 * sf.moveX(-physics.getForce(sf.getMass(), false, false)); }
					 */
					if (event.getNativeKeyCode() == KeyCodes.KEY_UP) {
						sf.moveY(-physics.getForce(sf.getMass(), false, false));
					}
					if (event.getNativeKeyCode() == KeyCodes.KEY_DOWN) {
						sf.moveY(physics.getForce(sf.getMass(), false, false));
					}
					if (event.getNativeKeyCode() == KeyCodes.KEY_SPACE) {
						Bullet bullet = new Bullet();
						bullet.setTopStep(sf.getTopStep());
						mainCanvas.add(bullet.getImage().asWidget());
						Timer t = new Timer() {

							@Override
							public void run() {
								bullet.moveX(physics.getForce(bullet.getMass(), false, false));
								// Window.alert("call physics");
								if (physics.checkCollision(bullet, asteroid)) {
									// Window.alert("impact");

									cancel();
									destroy();
									asteroid.setHits(1);
									if (asteroid.getHits() == asteroid.getMass()) {
										int top = asteroid.getImage().getAbsoluteTop();
										int left = asteroid.getImage().getAbsoluteLeft();
										asteroid.getImage().removeFromParent();
										Timer tFire = new Timer() {

											@Override
											public void run() {
												// TODO Auto-generated method stub
												Image fire = new Image(resources.explosion());
												fire.getElement().getStyle().setPosition(Position.ABSOLUTE);
												fire.getElement().getStyle().setLeft(left, Unit.PX);
												fire.getElement().getStyle().setTop(top, Unit.PX);
												mainCanvas.add(fire);

											}

										};
										tFire.schedule(1000);

									}
								}
								if (bullet.getImage().getAbsoluteLeft() > 1200) {
									cancel();
									destroy();
								}

							}

							private void destroy() {
								bullet.getImage().removeFromParent();
							}
						};
						t.scheduleRepeating(50);
					}
				}
			});

			controlPanel.setCellHorizontalAlignment(hp, HasHorizontalAlignment.ALIGN_CENTER);
			controlPanel.setCellHorizontalAlignment(sf.getUpButton(), HasHorizontalAlignment.ALIGN_CENTER);

		}

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
				mainCanvas.remove(ball.getImage());
				controlPanel.setVisible(false);
				setEnabled(false, true);
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
	 * @param enabled
	 */
	void setEnabled(boolean enabled, boolean ball) {
		if (ball) {
			getElement().addClassName(enabled ? style.enabledBall() : style.disabled());
			getElement().addClassName(enabled ? style.enabledBall() : style.enabledSpace());
			getElement().removeClassName(enabled ? style.disabled() : style.enabledBall());
			getElement().removeClassName(enabled ? style.enabledSpace() : style.enabledBall());
		}

		else {
			getElement().addClassName(enabled ? style.enabledSpace() : style.disabled());
			getElement().addClassName(enabled ? style.enabledSpace() : style.enabledBall());
			getElement().removeClassName(enabled ? style.disabled() : style.enabledSpace());
			getElement().removeClassName(enabled ? style.enabledBall() : style.enabledSpace());
		}

	}

}
