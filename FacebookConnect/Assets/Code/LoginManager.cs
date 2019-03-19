using UnityEngine;

public class LoginManager : MonoBehaviour
{
//    [SerializeField] private Button m_loginButton;
//    [SerializeField] private Button m_logoutButton;
//    [SerializeField] private Button m_emailButton;
//    
//    private ILoginResult m_tokenResult;
//
//
//    private void Start()
//    {
//        FB.Init(OnFacebookInitialized);
//    }
//
//    private void OnFacebookInitialized()
//    {
//        if(m_loginButton && m_loginButton.gameObject)
//            m_loginButton.gameObject.SetActive(true);
//    }
//
//    private void OnEnable()
//    {
//        if(FB.IsInitialized)
//            m_loginButton.gameObject.SetActive(true);
//        
//        m_loginButton.onClick.AddListener(OnLogin);
//        
//        m_logoutButton.onClick.AddListener(OnLogout);
//        
//        m_emailButton.onClick.AddListener(OnEmailRequest);
//    }
//
//    private void OnEmailRequest()
//    {
//        FB.API("/me?fields=id,name,email", HttpMethod.GET, GetMe);
//    }
//
//    private void GetMe(IGraphResult result)
//    {
//        
//        
//        Debug.Log(result.RawResult);
//    }
//
//    private void OnLogout()
//    {
//        FB.LogOut();
//        if (FB.IsInitialized)
//        {
//            m_loginButton.gameObject.SetActive(true);
//            m_logoutButton.gameObject.SetActive(false);
//        }
//    }
//
//    private void OnLogin()
//    {
//        FB.LogInWithReadPermissions(
//            new List<string>(){"public_profile", "email"},
//            AuthCallback);
//    }
//
//    private void AuthCallback(ILoginResult result)
//    {
//        if (result == null)
//        {
//            return;
//        }
//
//        if (!string.IsNullOrEmpty(result.Error))
//        {
//            Debug.LogError("error while login :"+result.Error);
//            return;
//        }
//
//        if (result.Cancelled)
//        {
//            Debug.Log("login Cancelled");
//        }
//        
//        Debug.Log("Token : "+ result.RawResult);
//        
//        m_loginButton.gameObject.SetActive(false);
//        m_logoutButton.gameObject.SetActive(true);
//
//        m_tokenResult = result;
//
//    }
//
//    // Update is called once per frame
//    void Update()
//    {
//        
//    }
}