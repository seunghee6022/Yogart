<template>
  <div class="home">

    <Loading v-if="initSuccess"/>
    <AI v-if="initSuccess" @send-initSuccess="initSuccess"/>
    <p>{{initSuccess}}</p>
    
    

  </div>
</template>

<script>
// @ is an alias to /src
import AI from '@/components/AI.vue'
import Loading from '@/components/Loading.vue'


export default {
  name: 'Home',
  components: {
    AI,
    Loading,
   
  },
  props: {
    initSuccess : Boolean,
  }
}

    

</script>
