using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class JavePluginMonoBehaviour : MonoBehaviour {
	
	private AndroidJavaObject toastExample = null;
	private AndroidJavaObject activityContext = null;

	[SerializeField] private Image m_step1,m_step2,m_step3,m_step4,m_step5,m_step6, m_step7;
	[SerializeField] private Text m_text;
	
	[SerializeField] private Button m_onButton, m_offButton, m_getListButton, m_connectButton;

	[SerializeField] private Dropdown m_dropDown;

	// Use this for initialization
	void Start () {
		m_step1.color = Color.red;
		#if UNITY_ANDROID
		if(toastExample == null)
		{
			using(AndroidJavaClass activityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer")) {
				activityContext = activityClass.GetStatic<AndroidJavaObject>("currentActivity");
			}
			
			using(AndroidJavaClass pluginClass = new AndroidJavaClass("com.amiskovic.test.btandroidlib.BluetoothProxy")) {
				if(pluginClass != null) {
					toastExample = pluginClass.CallStatic<AndroidJavaObject>("instance");
					toastExample.Call("setActivity", activityContext);
					activityContext.Call("runOnUiThread", new AndroidJavaRunnable(() => {
						toastExample.Call("showMessage", "This is a Toast message");
						m_step1.color = Color.green;
					}));
				}
			}
		}
		#endif
		
		m_onButton.onClick.AddListener(On);
		m_offButton.onClick.AddListener(Off);
		m_getListButton.onClick.AddListener(GetDeviceList);
		m_connectButton.onClick.AddListener(OnConnectClick);
	}

	private void OnConnectClick()
	{
		Dropdown.OptionData data = m_dropDown.options[m_dropDown.value];
		
		toastExample.Call("connect", data.text );
	}


	private void Awake()
	{
		
	}

	private int i = 0;
	void Update()
	{
#if UNITY_ANDROID
//		var value = toastExample.Get<int>("value");
//		var v2 =  toastExample.Get<int>("getValue");
//
//		m_text.text = v2.ToString();
//		m_text.text = ++i +" "+ value.ToString();
//		toastExample.Set("value", v2+1);
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
		toastExample.Call("on");
#endif
		m_step2.color = Color.green;
		
	}
	
	
	void Off()
	{
#if UNITY_ANDROID
		toastExample.Call("off");
#endif
		m_step2.color = Color.red;
		
	}
	
	private void GetDeviceList()
	{
		#if UNITY_ANDROID
		string[] nameArray  = toastExample.Call<string[]>("list");

		if (nameArray != null)
		{
			List<Dropdown.OptionData> l = new List<Dropdown.OptionData>();

			foreach (var name in nameArray)
			{
				l.Add(new Dropdown.OptionData(name));
			}

			m_dropDown.options = l;
		}
		else
		{
			Debug.LogError("no name array");
		}
#endif
	}

}
