using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class JavePluginMonoBehaviour : MonoBehaviour {
	
	private AndroidJavaObject toastExample = null;
	private AndroidJavaObject activityContext = null;

	[SerializeField] private Image m_step1,m_step2,m_step3,m_step4,m_step5,m_step6, m_step7;
	[SerializeField] private Text m_text;
	
	[SerializeField] private Button m_onButton, m_offButton;

	// Use this for initialization
	void Start () {
		
		#if UNITY_ANDROID
		if(toastExample == null)
		{
			using(AndroidJavaClass activityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer")) {
				activityContext = activityClass.GetStatic<AndroidJavaObject>("currentActivity");
			}
			
			using(AndroidJavaClass pluginClass = new AndroidJavaClass("com.amiskovic.test.javapluginlib.ToastExample")) {
				if(pluginClass != null) {
					toastExample = pluginClass.CallStatic<AndroidJavaObject>("instance");
					toastExample.Call("setContext", activityContext);
					activityContext.Call("runOnUiThread", new AndroidJavaRunnable(() => {
						toastExample.Call("showMessage", "This is a Toast message");
					}));
				}
			}
		}
		#endif
		
		m_onButton.onClick.AddListener(On);
		m_offButton.onClick.AddListener(Off);
	}

	private void Awake()
	{
		
	}

	private int i = 0;
	void Update()
	{
#if UNITY_ANDROID
		var value = toastExample.Get<int>("value");
		m_text.text = ++i +" "+ value.ToString();
		toastExample.Set("value", value+1);
	#endif

//		if (500 == i)
//		{
//			activityContext.Call("runOnUiThread", new AndroidJavaRunnable(() => {
//				toastExample.Call("showMessage", "This is a Toast message 500");
//				m_step6.color = Color.green;
//			}));
//		}
	}

	void On()
	{
#if UNITY_ANDROID
		toastExample.Call("showMessage", "On");
		toastExample.Call("on");
#endif
		m_step1.color = Color.green;
		
	}
	
	
	void Off()
	{
#if UNITY_ANDROID
		toastExample.Call("showMessage", "OFF");
		toastExample.Call("off");
#endif
		m_step1.color = Color.red;
		
	}

}
