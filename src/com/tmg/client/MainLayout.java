package com.tmg.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.tmg.shared.FieldVerifier;

public class MainLayout extends Composite{

	private static MainLayoutUiBinder uiBinder = GWT.create(MainLayoutUiBinder.class);

	interface MainLayoutUiBinder extends UiBinder<Widget, MainLayout> {
	}
	@UiField
	VerticalPanel mainPanel;
	@UiField
	Label headerLabel;
	PushButton pbLogin;
	PushButton pbGuess;
	Login login = new Login();
	GuessingGame gg = new GuessingGame("GWT User");
	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));		
		pbLogin = new PushButton("Login Widget");
		pbGuess = new PushButton("Guessing Game");
		login = new Login();
		headerLabel.getElement().setAttribute("style", "font-weight: bold");
		mainPanel.add(pbLogin);
		mainPanel.add(pbGuess);

		pbLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			 login.center();
			 login.setGlassEnabled(true);
			}});
		pbGuess.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				gg.center();
				gg.setGlassEnabled(true);
			}});
	}
	
}
