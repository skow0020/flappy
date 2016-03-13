package com.mygdx.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.flappy;
import com.google.android.gms.ads.*;

public class AndroidLauncher extends AndroidApplication {
	private AdView adView;
	private AdRequest adRequest;
	private static final String AD_UNIT_ID_BANNER = "ca-app-pub-3940256099942544/6300978111";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new flappy(), config);

		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID_BANNER);
		adRequest = new AdRequest.Builder().build();

		adView.loadAd(adRequest);
		String x = "";
	}
}
