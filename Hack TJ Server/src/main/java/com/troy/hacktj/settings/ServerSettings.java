package com.troy.hacktj.settings;

import com.troy.hacjtj.base.util.Settings;

public class ServerSettings extends Settings {
	
	public ServerSettings() {
		putSetting(CryptoSettings.class, CryptoSettings.defaults());
	}

}
