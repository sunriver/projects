/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.funnyplayer;

import com.funnyplayer.util.ViewUtil;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;

public class SearchActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initActionBar();
	}
	
    /**
     * Set the ActionBar title
     */
    private void initActionBar() {
    	ActionBar actionBar = getActionBar();
    	
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar);
    	
    	actionBar.setDisplayUseLogoEnabled(true);
//    	actionBar.setDisplayShowTitleEnabled(false);
    }
}
