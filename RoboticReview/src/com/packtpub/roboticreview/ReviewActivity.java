package com.packtpub.roboticreview;

import android.app.TabActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;

public class ReviewActivity extends TabActivity implements
		ViewSwitcher.ViewFactory, Runnable, AdapterView.OnItemSelectedListener {

	public class ImageSwitcherFactory implements ViewSwitcher.ViewFactory {

		@Override
		public View makeView() {
			return new ImageView(ReviewActivity.this);
		}
		
	} 
	
	private final Handler switchCommentHandler = new Handler();
	private TextSwitcher switcher;
	private String[] comments;
	private int commentIndex = 0;
	private ImageSwitcher photo;
	private Gallery photos;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TabHost tabs = getTabHost();
		getLayoutInflater().inflate(R.layout.main, tabs.getTabContentView(),
				true);
		Resources resources = getResources();
		TabHost.TabSpec details = tabs
				.newTabSpec("review")
				.setContent(R.id.review)
				.setIndicator(getString(R.string.review),
						resources.getDrawable(R.drawable.review));

		TabHost.TabSpec gallery = tabs
				.newTabSpec("photos")
				.setContent(R.id.photos)
				.setIndicator(getString(R.string.gallery),
						resources.getDrawable(R.drawable.photos));

		TabHost.TabSpec reservation = tabs
				.newTabSpec("review")
				.setContent(R.id.reservation)
				.setIndicator(getString(R.string.reservation),
						resources.getDrawable(R.drawable.reservation));

		tabs.addTab(details);
		tabs.addTab(gallery);
		tabs.addTab(reservation);

		comments = resources.getStringArray(R.array.comments);
		switcher = (TextSwitcher) findViewById(R.id.reviews);
		switcher.setFactory(this);
		
		photo = (ImageSwitcher)findViewById(R.id.photo);
		photo.setFactory(new ImageSwitcherFactory());
		
		photos = ((Gallery)findViewById(R.id.gallery));
		photos.setAdapter(new GalleryAdapter());
		photos.setOnItemSelectedListener(this);
	}

	public View makeView() {
		return getLayoutInflater().inflate(R.layout.review_comment, null);
	}

	protected void onStart() {
		super.onStart();
		switchCommentHandler.postDelayed(this, 5 * 1000l);
	}

	protected void onStop() {
		super.onStop();
		switchCommentHandler.removeCallbacks(this);
	}

	public void run() {
		try {
			switcher.setText(comments[commentIndex++]);
			if (commentIndex >= comments.length) {
				commentIndex = 0;
			}
		} finally {
			switchCommentHandler.postDelayed(this, 5 * 1000l);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		photo.setImageDrawable(Utils.LoadImageFromWebOperations((String)photos.getAdapter().getItem(position)));
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
