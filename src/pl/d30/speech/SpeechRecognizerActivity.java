package pl.d30.speech;

import java.util.ArrayList;

import com.SpeechRecognizer.R;

import android.app.Activity;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class SpeechRecognizerActivity extends Activity {
	
	SpeechRecognizer sr;
	TextView tv;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        class MyRecognitionListener implements RecognitionListener {

			@Override
			public void onBeginningOfSpeech() {				
				Log.d("Speech", "onBeginningOfSpeech");
			}

			@Override
			public void onBufferReceived(byte[] buffer) {
				Log.d("Speech", "onBufferReceived");
			}

			@Override
			public void onEndOfSpeech() {
				Log.d("Speech", "onEndOfSpeech");
			}

			@Override
			public void onError(int error) {
				Log.d("Speech", "onError");
				sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
			}

			@Override
			public void onEvent(int eventType, Bundle params) {
				Log.d("Speech", "onEvent");
			}

			@Override
			public void onPartialResults(Bundle partialResults) {
				Log.d("Speech", "onPartialResults");
			}

			@Override
			public void onReadyForSpeech(Bundle params) {
				Log.d("Speech", "onReadyForSpeech");
			}
			

			@Override
			public void onResults(Bundle results) {
				Log.d("Speech","==== onResults: ====");
				ArrayList<String> strlist = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);				
				tv.append("\nPossible matches:");
				
				for (int i = 0; i < strlist.size();i++ ) {
					Log.d("Speech", strlist.get(i));
					tv.append("\n> " + strlist.get(i));
				}
				Log.d("Speech","");
				tv.append("\n");
				
				final int s = tv.getLayout().getLineTop(tv.getLineCount()) - tv.getHeight();
				
				if(s>0) tv.scrollTo(0, s);
				else tv.scrollTo(0, 0);
				sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
				
			}

			@Override
			public void onRmsChanged(float rmsdB) {
				//Log.d("Speech", "onRmsChanged");
			}
        	
        }
        tv = (TextView) findViewById(R.id.logger);
        tv.setMovementMethod(new ScrollingMovementMethod());
        
        sr = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
        MyRecognitionListener listener = new MyRecognitionListener();
        sr.setRecognitionListener(listener);
        
     }
    
    @Override
    public void onResume() {
    	Log.d("Speech", "APP.onResume()");
    	super.onResume();
    	sr.startListening(RecognizerIntent.getVoiceDetailsIntent(getApplicationContext()));
    }
    
    @Override
    public void onPause() {
    	Log.d("Speech", "APP.onPause()");
    	super.onPause();
    	sr.stopListening();
    	sr.cancel();
    	
    }
    
}