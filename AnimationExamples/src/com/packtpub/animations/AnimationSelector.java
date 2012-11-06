package com.packtpub.animations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AnimationSelector extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ((Button)findViewById(R.id.news_feed)).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
		case R.id.news_feed:
			startActivity(new Intent(this, NewsFeedActivity.class));
			break;
		default:
			break;
		}
	}
}
