<template>
    <div id="header" v-if="isHeader">
        <h1>
            <router-link v-bind:to="{name:constants.URL_TYPE.POST.MAIN}">
                SS_log
            </router-link>
        </h1>
        <div class="right">
            <div class="search-input">
                <i class="fas fa-search"></i>
                <input v-model="keyword" type="text"/>
            </div> 
            
            <router-link v-bind:to="{name:constants.URL_TYPE.USER.LOGIN}" class="login-btn">
                로그인 
            </router-link>  
            
        </div>  
        
    </div>
</template>   

<script> 
    import constants from '../../lib/constants'

    export default {
        name: 'Header',
        components: { 

        },
        props: ['isHeader'],
        computed:{
        },
        watch: {
        },
        created() {
        },
        methods : {
        },
        data: function() {
           return {
               constants,
               keyword : "",
           }
        },

    }
</script>
