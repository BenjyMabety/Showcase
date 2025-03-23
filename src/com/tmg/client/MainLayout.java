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
	PushButton pb;
	Login login = new Login();
	public MainLayout() {
		initWidget(uiBinder.createAndBindUi(this));		
		pb = new PushButton("Login Widget");
		login = new Login();
		headerLabel.getElement().setAttribute("style", "font-weight: bold");
		mainPanel.add(pb);

		pb.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			 login.center();
			 login.setGlassEnabled(true);
			}});
	}
	
}
