package com.tmg.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>FileService</code>.
 */
public interface FileServiceAsync {
	void openFile(String fileName, AsyncCallback<String> callback) throws IllegalArgumentException;
}
