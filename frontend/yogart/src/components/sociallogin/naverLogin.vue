<template>

        <div id="naverIdLogin"></div>  
  
</template>

<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
<script>
import axios from'axios'

const naverClientId = '4eWVX86Cci4TFZzNvA3b'

export default {
    name:'naverLogin',
    data() {
      return {
          // 로그인 버튼 이미지
          repo: {name: 'loginBtn', src: require('../../assets/naverLoginBtn/naver_login_green.png')},
          //  FIXME state 값 random string 으로 변경
          state: 123,
          naverLoginURL: 'https://nid.naver.com/oauth2.0/authorize?response_type=code',
          SERVER_URL: this.$store.state.SERVER_URL,
          LOCAL_URL: this.$store.state.LOCAL_URL,
        }
    },
    mounted (){
        const axiosVue = this
        var naverLogin = new naver.LoginWithNaverId(
            {
                clientId: naverClientId,
                callbackUrl: this.LOCAL_URL + '/naver/callback',
                isPopup: false, /* 팝업을 통한 연동처리 여부 */
                loginButton: {color: "green", type: 3, height: 48} /* 로그인 버튼의 타입을 지정 */
            }
        );
        
        /* 설정정보를 초기화하고 연동을 준비 */
        naverLogin.init();
        /* (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close) */
		if (naverLogin.accessToken !== null) {
			naverLogin.getLoginStatus(function (status) {
				if (status) {
					/* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
					var email = naverLogin.user.getEmail();
					if( email == undefined || email == null) {
						alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
						/* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */
						naverLogin.reprompt();
						return;
                    }
                    let accessToken = String(naverLogin.accessToken.accessToken);
                    axios.post(`http://i3d202.p.ssafy.io:8000/api/users/naverLogin`, 
                    {
                        accessToken: accessToken
                    })
                    .then(res => {
                        axiosVue.$emit('loginComplete', res.data)
                    })
                    .catch(err => console.error(err))
				} else {
					console.log("callback 처리에 실패하였습니다.");
				}
			});
        }

    }
}
</script>