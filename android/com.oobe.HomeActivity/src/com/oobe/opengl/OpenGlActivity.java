package com.oobe.opengl;

import com.oobe.common.OpenGlRenderer;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public class OpenGlActivity extends Activity {
	
	private Renderer render=new OpenGlRenderer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		GLSurfaceView view = new GLSurfaceView(this);
		view.setRenderer(render);
		setContentView(view);
	}

}
