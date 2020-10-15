<template>
  <div class="box">
        <div id="kakao-login-btn"></div>
  </div>
</template>

<script>
const JavaScriptApiKey = process.env.VUE_APP_KAKAO_JS_KEY
import axios from "axios";
export default {
    name : 'kakaoLogin',
    data() {
        return {
            JavaScriptApiKey : JavaScriptApiKey,
        }
    },
    created() {
        Kakao.init('688de69414ec5331cee58badb1cad1ea');
    },
    mounted() {
        document.addEventListener("DOMContentLoaded", function() {

        // @details 카카오톡 Developer API 사이트에서 발급받은 JavaScript Key
      

        // @breif 카카오 로그인 버튼을 생성합니다.

        Kakao.Auth.createLoginButton({
            container : "#kakao-login-btn",
            success : function( authObj ) {
                 axios
                .post("http://localhost:8000/api/users/kakaoLogin", {
                    access_token: authObj.access_token,
                    expires_in: authObj.expires_in,
                    refresh_token: authObj.refresh_token,
                    refresh_token_expires_in: authObj.refresh_token_expires_in,
                    token_type: authObj.token_type
                    })
                .then(res1 => {
                this.$router.push("/");
                })
                .catch(error => {
                console.error(error);
                });
                
                Kakao.API.request({
                      url : "/v2/user/me"
                    , success : function( res ) {
                    }, fail : function( error ) {
                        alert( JSON.stringify( error ) );
                    }
                });
            }
            , fail : function( error ) {
                alert( JSON.stringify( error ));

            }

        });

    });
    },
}
</script>

<style>

</style>
