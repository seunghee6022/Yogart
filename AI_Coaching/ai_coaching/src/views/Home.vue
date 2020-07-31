<template>
  <div class="home">

    <Loading/>
    <AI/>
    <TM/>
    
    

  </div>
</template>

<script>
// @ is an alias to /src
import AI from '@/components/AI.vue'
import Loading from '@/components/Loading.vue'
import TM from '@/components/TM.vue'

export default {
  name: 'Home',
  components: {
    AI,
    Loading,
    TM
  }
}

    

</script>
