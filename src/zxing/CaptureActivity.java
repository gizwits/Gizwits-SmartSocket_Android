package zxing;

import java.io.IOException;
import java.util.Vector;

import zxing.camera.CameraManager;
import zxing.decoding.CaptureActivityHandler;
import zxing.decoding.InactivityTimer;
import zxing.view.ViewfinderView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.powersocket.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;

public class CaptureActivity extends BaseActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	// private MediaPlayer mediaPlayer;
//	private boolean playBeep;
//	private static final float BEEP_VOLUME = 0.10f;
//	private boolean vibrate;
	private String product_key, passcode, did;
	
	private Button btnCancel;
	private ImageView ivReturn;

	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		START_BIND,

		SUCCESS,

		FAILED,

	}

	/**
	 * The handler.
	 */
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {

			case START_BIND:
				startBind(passcode, did);
				break;

			case SUCCESS:
				ToastUtils.showShort(CaptureActivity.this, "添加成功");
				IntentUtils.getInstance().startActivity(CaptureActivity.this,
						DeviceListActivity.class);
				finish();
				break;
			case FAILED:
				ToastUtils.showShort(CaptureActivity.this, "添加失败，请返回重试");
				finish();
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zxing_layout);
		
		btnCancel=(Button) findViewById(R.id.btn_cancel);
		ivReturn=(ImageView) findViewById(R.id.iv_return);
		OnClickListener myClick=new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CaptureActivity.this.finish();
			}
		};
		btnCancel.setOnClickListener(myClick);
		ivReturn.setOnClickListener(myClick);
		
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

//		playBeep = true;
//		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
//		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
//			playBeep = false;
//		}
//		initBeepSound();
//		vibrate = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	private void startBind(final String passcode, final String did) {

		mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(), did,
				passcode, "");

	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		String text = obj.getText();
		Log.i("test", text);
		if (text.contains("product_key=") & text.contains("did=")
				&& text.contains("passcode=")) {

			inactivityTimer.onActivity();
			viewfinderView.drawResultBitmap(barcode);
			// playBeepSoundAndVibrate();
			product_key = getParamFomeUrl(text, "product_key");
			did = getParamFomeUrl(text, "did");
			passcode = getParamFomeUrl(text, "passcode");
			Log.i("passcode product_key did", passcode + " " + product_key
					+ " " + did);
			ToastUtils.showShort(this, "扫码成功");
			mHandler.sendEmptyMessage(handler_key.START_BIND.ordinal());
			// Intent it = new Intent();
			// it.setClass(this, NewDeviceControlActivity.class);
			// it.putExtra("passcode", passcode);
			// it.putExtra("product_key", product_key);
			// it.putExtra("did", did);
			// startActivity(it);
			// finish();

		} else {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}

	}

	private String getParamFomeUrl(String url, String param) {
		String product_key = "";
		int startindex = url.indexOf(param + "=");
		startindex += (param.length() + 1);
		String subString = url.substring(startindex);
		int endindex = subString.indexOf("&");
		if (endindex == -1) {
			product_key = subString;
		} else {
			product_key = subString.substring(0, endindex);
		}
		return product_key;
	}

//	private void initBeepSound() {
//		 if (playBeep && mediaPlayer == null) {
//		 // The volume on STREAM_SYSTEM is not adjustable, and users found it
//		 // too loud,
//		 // so we now play on the music stream.
//		 setVolumeControlStream(AudioManager.STREAM_MUSIC);
//		 mediaPlayer = new MediaPlayer();
//		 mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//		 mediaPlayer.setOnCompletionListener(beepListener);
//		
//		 AssetFileDescriptor file = getResources().openRawResourceFd(
//		 R.raw.beep);
//		 try {
//		 mediaPlayer.setDataSource(file.getFileDescriptor(),
//		 file.getStartOffset(), file.getLength());
//		 file.close();
//		 mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
//		 mediaPlayer.prepare();
//		 } catch (IOException e) {
//		 mediaPlayer = null;
//		 }
//		 }
//	}

//	private static final long VIBRATE_DURATION = 200L;

	// private void playBeepSoundAndVibrate() {
	// // if (playBeep && mediaPlayer != null) {
	// // mediaPlayer.start();
	// // }
	// if (vibrate) {
	// Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
	// vibrator.vibrate(VIBRATE_DURATION);
	// }
	// }

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
//	private final OnCompletionListener beepListener = new OnCompletionListener() {
//		public void onCompletion(MediaPlayer mediaPlayer) {
//			mediaPlayer.seekTo(0);
//		}
//	};

	@Override
	protected void didBindDevice(int error, String errorMessage, String did) {
		Log.d("扫描结果", "error=" + error + ";errorMessage=" + errorMessage
				+ ";did=" + did);
		if (error == 0) {
			mHandler.sendEmptyMessage(handler_key.SUCCESS.ordinal());
		} else {
			Message message = new Message();
			message.what = handler_key.FAILED.ordinal();
			message.obj = errorMessage;
			mHandler.sendMessage(message);
		}
	}

}